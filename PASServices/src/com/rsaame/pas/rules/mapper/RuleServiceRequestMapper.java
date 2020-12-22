/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.cmn.FetchAccumulationLimitStatusFunc;
import com.rsaame.pas.dao.cmn.GetPreviousPremiumFunc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.CommissionVO;
import com.rsaame.pas.vo.app.CommissionVOList;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.RuleContext;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.ClaimsSummaryVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RenewalVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.UWQuestionRespType;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.bus.WCVO;
import com.rsaame.rulesengine.client.Fact;
import com.rsaame.rulesengine.client.RuleAttribute;
import com.rsaame.rulesengine.client.RuleExecutionRequest;
import com.rsaame.rulesengine.client.RuleHeader;
import com.rsaame.rulesengine.client.RuleInfo;
import com.rsaame.rulesengine.client.RuleKey;
import com.rsaame.rulesengine.client.RuleSet;
import com.rsaame.rulesengine.client.Section;

/**
 * @author M1014739
 * 
 */
public class RuleServiceRequestMapper{

	private static final int DAYDIVIDER = ( 1000 * 60 * 60 * 24 );

	private final static Logger logger = Logger.getLogger( RuleServiceRequestMapper.class );

	private static final String STOCK_STORAGE = "Stocks";
	private final static double FIDELITY_SECTION_ID =  Double.valueOf ( Utils.getSingleValueAppConfig( "FIDELITY_LIMIT" ));
	DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT );
	private static final String BAHRAIN_LOC = "50";
	private static final String OMAN_LOC = "30";

	/**
	 * 
	 * @param baseVO
	 * @param sectionArray
	 * @param roleType
	 * @return
	 */
	public RuleExecutionRequest createRuleRequest( BaseVO policyBaseVO, Integer[] sectionArray, String roleType ){
		RuleExecutionRequest request = null;
		RuleInfo ruleInfo = null;
		RuleHeader ruleHeader = null;

		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		GeneralInfoVO generalInfoVO = (GeneralInfoVO) policyVO.getGeneralInfo();
		// Creating the rule header
		ruleHeader = createRuleHeader( generalInfoVO, policyVO, sectionArray );
		
		// Creating the rule Info
		for( int section : sectionArray ){
			if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GENERAL ) ).intValue() == section ){
				ruleInfo = createRuleInfoForGeneral( generalInfoVO, policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.PROP_ALL_RISK ) ).intValue() == section ){
				ruleInfo = createRuleInfoForPAR( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.MONEY ) ).intValue() == section ){
				ruleInfo = createRuleInfoForMoney( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.PUBLIC_LIAB ) ).intValue() == section ){
				ruleInfo = createRuleInfoForPL( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.WORK_COMP ) ).intValue() == section ){
				ruleInfo = createRuleInfoForWC( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.ENDORSEMENT ) ).intValue() == section ){
				ruleInfo = createRuleInfoForEndorsement( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.RECEIPT ) ).intValue() == section ){
				ruleInfo = createRuleInfoForReceipt( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.PREMIUM_PAGE ) ).intValue() == section ){
				ruleInfo = createRuleInfoForPremiumPage( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.DISCOUNT_LOADING ) ).intValue() == section ){
				ruleInfo = createRuleInfoForDiscountLoading( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.ELEC_EQUIP ) ).intValue() == section ){
				ruleInfo = createRuleInfoForEEQ( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.TRAVEL_BAGGAGE ) ).intValue() == section ){
				ruleInfo = createRuleInfoForTRL(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.MACHINE_BD ) ).intValue() == section ){
				ruleInfo = createRuleInfoForMB(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.BUS_INT ) ).intValue() == section ){
				ruleInfo = createRuleInfoForBI(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GOODS_IN_TRANSIT ) ).intValue() == section ){
				ruleInfo = createRuleInfoForGIT(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GROUP_PA ) ).intValue() == section ){
				ruleInfo = createRuleInfoForGPA(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.DOS ) ).intValue() == section ){
				ruleInfo = createRuleInfoForDOS(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.REN ) ).intValue() == section ){
				ruleInfo = createRuleInfoForREN(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.FID ) ).intValue() == section ){
				ruleInfo = createRuleInfoForFID(policyBaseVO, section, roleType, ruleInfo);
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.CHK_EFF_DATE ) ).intValue() == section ){
                ruleInfo = createRuleInfoForConvToPolicy( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.QUO ) ).intValue() == section ){
                ruleInfo = createRuleInfoForIssueOrEditQuote( policyVO, section, roleType, ruleInfo );
			}else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.CREDIT_LIMIT ) ).intValue() == section ){
				ruleInfo = createRuleInfoForCreditLimit( policyVO, section, roleType, ruleInfo );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.RIS ) ).intValue() == section ){
                ruleInfo = createRuleInfoForIssueQuoteForBroker( policyVO, section, roleType, ruleInfo );
			}
			else {
				Integer sectionInt = section;
				String Sectionstr = sectionInt.toString();
				Sectionstr = Sectionstr.substring(0,3 );
				if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.COND ) ).intValue() ==  Integer.valueOf(Sectionstr).intValue() )
                ruleInfo = createRuleInfoForConditions( policyVO, Integer.valueOf(((String)sectionInt.toString().substring( 3 ))).intValue(), roleType, ruleInfo );
			}
		}
		request = new RuleExecutionRequest();
		request.setRuleHeader( ruleHeader );
		
		/*
		 * Added for Oman Roll out, in case there are rules that needs to be ignored for any particular location
		 * tat rule will be filtered here.
		 */
		filterRule(ruleInfo,policyVO);
		
		request.setRuleInfo( ruleInfo );

		return request;
	}

	

	/*
	 * Method merged from dubai code, in case there are rules that needs to be ignored for any particular location
	 * tat rule will be filtered here. 
	 */
	private void filterRule( RuleInfo ruleInfo, PolicyVO policyVO ){

        List<RuleSet> rss = ruleInfo.getRuleSet();
        for( RuleSet rs : rss ){
               if( !Utils.isEmpty( Utils.getSingleValueAppConfig( rs.getRuleSetName() + "_RULE_SEC" ) ) ){
                     // Rules to be excluded will be configured in the PAS property file
                     String[] rulesToExclude = Utils.getSingleValueAppConfig( rs.getRuleSetName() + "_RULE_SEC" ).split( "," );
                     List<Fact> facts = rs.getFact(); // RuleSet doesnot have a setter method to set fact hence the logic to remove from the list
                     List<String> rulesToExcludeL = CopyUtils.asList( rulesToExclude );
                     for( Fact fact : facts ){
                            List<RuleAttribute> attributes = fact.getRuleSetAttribute();                              
                            List<RuleAttribute> attributsToRemove = new ArrayList<RuleAttribute>();
                            for( RuleAttribute attribute : attributes ){
                                   if( rulesToExcludeL.contains( attribute.getAttributeName() ) ){
                                          attributsToRemove.add( attribute );
                                   }
                            }
                            
                            attributes.removeAll( attributsToRemove );
                     }
               }
        }

	}
	
	private int mapTariff(int tariff){
		
		//Modified as a part of Ticket # 149213
		String RULE_TARIFF_MAP ="";
		if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION )) ){
			RULE_TARIFF_MAP =  Utils.getSingleValueAppConfig("RULE_TARIFF_MAP") ;
		}
		else{
			RULE_TARIFF_MAP =  Utils.getSingleValueAppConfig("RULE_TARIFF_MAP_SBS") ;
		}
		if(RULE_TARIFF_MAP.contains("$"+tariff)){
			StringTokenizer tok = new StringTokenizer(RULE_TARIFF_MAP,";");
			while(tok.hasMoreTokens()){
				String str = tok.nextToken();
				if(str.contains("$"+tariff)){
					tariff = Integer.valueOf(str.substring(1, str.indexOf("-")));
					break;
				}
			}
		}
		return tariff;
	}
	/**
	 * 
	 * @param baseVO
	 * @param sectionArray
	 * @return RuleHeader
	 */
	private RuleHeader createRuleHeader( BaseVO generalBaseVO, BaseVO policyBaseVO, Integer[] sectionArray ){
		RuleHeader ruleHeader = null;
		RuleKey ruleKey = null;
		int policyTypeCode = 0;
		int tariffCode = 0;
		String locationCode = null;
		Date effDate = null;
		String effDateStr = null;
		PolicyVO policyDetails = null;
		SchemeVO scheme = null;
		String locationStr = null;
		String policyTypeStr = null;
		String tariffStr = null;
		Section ruleSection = null;

		GeneralInfoVO generalInfo = (GeneralInfoVO) generalBaseVO;

		if( !Utils.isEmpty( generalInfo ) ){
			policyDetails = (PolicyVO) policyBaseVO;

			if( !Utils.isEmpty( policyDetails ) ){
				scheme = policyDetails.getScheme();
				if( !Utils.isEmpty( scheme ) ){
					if( !Utils.isEmpty( scheme.getTariffCode() ) ) tariffCode = scheme.getTariffCode();
					tariffCode = mapTariff(tariffCode);
					if( !Utils.isEmpty( scheme.getPolicyCode() ) ) policyTypeCode = scheme.getPolicyCode();
					effDate = scheme.getEffDate();
					SimpleDateFormat sdf = new SimpleDateFormat( RulesConstants.DATE_FORMAT );
					effDateStr = sdf.format( effDate );
				}
			}
		}

		locationCode = Utils.getSingleValueAppConfig(SvcConstants.RULE_DEPLOYED_LOC);
		//locationCode = "20";//ServiceContext.getLocation();
		logger.debug( "locationCode: " + locationCode + " policyTypeCode: " + policyTypeCode + " tariffCode: " + tariffCode );
		//TODO: Overriden locationCode value for testing purpose. This line needs to be removed and the value should come from above mapping.
		if( Utils.isEmpty( locationCode, true ) ) locationCode = new String( "20" );

		if( Utils.isEmpty( locationCode, true ) || policyTypeCode == 0 || tariffCode == 0 ){
			logger.error( "Either locationCode or policyTypeCode or  tariffCode is null." );
			throw new SystemException( Utils.getAppErrorMessage( "cmn.systemError" ), null,
					"Exception while calling rules engine. Either locationCode or policyTypeCode or  tariffCode is null." );
		}

		locationStr = RulesConstants.LOCATION.concat( locationCode );
		//policyTypeStr = RulesConstants.POLICY_TYPE.concat( new Integer( policyTypeCode ).toString() );
		policyTypeStr = RulesConstants.POLICY_TYPE.concat( Integer.valueOf( policyTypeCode ).toString() );
		//Radar fix
		//tariffStr = RulesConstants.TARIFF.concat( new Integer( tariffCode ).toString() );
		tariffStr = RulesConstants.TARIFF.concat( Integer.valueOf(tariffCode).toString());	

		ruleKey = new RuleKey();
		ruleKey.setCountry( RulesConstants.COUNTRY );
		ruleKey.setLocation( locationStr );
		ruleKey.setClazz( RulesConstants.CLAZZ );
		ruleKey.setPolicyType( policyTypeStr );
		ruleKey.setTariff( tariffStr );
		ruleKey.setNewBusinessEffectiveDate( effDateStr );
		// TODO: To be mapped as part of Release 2 during Renewals implementation
		// ruleKey.setRenewalEffectiveDate(value);
		//ruleKey.setCommonRuleSetName( "TO_BE_MAPPED" );

		ruleSection = new Section();
		ArrayList<String> sectionList = (ArrayList<String>) ruleSection.getSection();

		for( Integer section : sectionArray ){
			if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GENERAL ) ).intValue() == section ){
				sectionList.add( RulesConstants.GENERAL );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.PROP_ALL_RISK ) ).intValue() == section ){
				sectionList.add( RulesConstants.PROP_ALL_RISK );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.MONEY ) ).intValue() == section ){
				sectionList.add( RulesConstants.MONEY );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.PUBLIC_LIAB ) ).intValue() == section ){
				sectionList.add( RulesConstants.PUBLIC_LIAB );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.WORK_COMP ) ).intValue() == section ){
				sectionList.add( RulesConstants.WORK_COMP );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.ENDORSEMENT ) ).intValue() == section ){
				sectionList.add( RulesConstants.ENDORSEMENT );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.RECEIPT ) ).intValue() == section ){
				sectionList.add( RulesConstants.RECEIPT );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.PREMIUM_PAGE ) ).intValue() == section ){
				sectionList.add( RulesConstants.PREMIUM_PAGE );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.DISCOUNT_LOADING ) ).intValue() == section ){
				sectionList.add( RulesConstants.DISCOUNT_LOADING );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.ELEC_EQUIP ) ).intValue() == section ){
				sectionList.add( RulesConstants.ELEC_EQUIP );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.TRAVEL_BAGGAGE ) ).intValue() == section ){
				sectionList.add( RulesConstants.TRAVEL_BAGGAGE );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.MACHINE_BD ) ).intValue() == section ){
				sectionList.add( RulesConstants.MACHINE_BD );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.BUS_INT ) ).intValue() == section ){
				sectionList.add( RulesConstants.BUS_INT );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GOODS_IN_TRANSIT ) ).intValue() == section ){
				sectionList.add( RulesConstants.GOODS_IN_TRANSIT );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GROUP_PA ) ).intValue() == section ){
				sectionList.add( RulesConstants.GROUP_PA );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.REN ) ).intValue() == section ){
				sectionList.add( RulesConstants.REN );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.DOS ) ).intValue() == section ){
				sectionList.add( RulesConstants.DOS );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.FID ) ).intValue() == section ){
				sectionList.add( RulesConstants.FID );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.CHK_EFF_DATE ) ).intValue() == section ){
				sectionList.add( RulesConstants.CHK_EFF_DATE );
				//sectionList.add( RulesConstants.QUO );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.RIS ) ).intValue() == section ){
				sectionList.add( RulesConstants.RIS );
			}
			else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.QUO ) ).intValue() == section ){
                sectionList.add( RulesConstants.QUO );
			}else if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.CREDIT_LIMIT ) ).intValue() == section ){
				sectionList.add( RulesConstants.CREDIT_LIMIT );
			}
			else {
				Integer sectionInt = section;
				String Sectionstr = sectionInt.toString();
				Sectionstr = Sectionstr.substring(0,3 );
				if( Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.COND ) ).intValue() ==  Integer.valueOf(Sectionstr).intValue() )
				{
					sectionList.add( RulesConstants.COND );
				}
			}
		}
		ruleKey.setSectionList( ruleSection );

		ruleHeader = new RuleHeader();
		ruleHeader.setRuleKey( ruleKey );

		return ruleHeader;
	
	}
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return RuleInfo
	 */
	@SuppressWarnings( "unchecked" )
	private RuleInfo createRuleInfoForPAR( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){

		String riskId = null;
		int riCategory = 0;
		int hazardLevel = 0;
		int noOfLocations = 0;
		SectionVO sectionVO = null;
		List<SectionVO> sectionVOList = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		String coverDesc = null;
		double totalSumInsured = 0.0;
		double totalSumInsuredRICategory1 = 0.0;
		double totalSumInsuredRICategory2 = 0.0;
		double totalSumInsuredRICategory3 = 0.0;
		double totalSumInsuredRICategory4 = 0.0;
		double totalSumInsuredRICategory5 = 0.0;
		double totalSumInsuredRICategory6 = 0.0;
		double totalSumInsuredRICategory7 = 0.0;
		double stockStoragePercent = 0.0;
		String accumulationLimitExceeded = null;
		Integer directorate = 0;
		LocationVO location = null;
		ParVO locationDetail = null;
		PARUWDetailsVO uwDetails = null;
		Iterator<SectionVO> sectionListItr = null;
		boolean parSectionPresent = false;
		PropertyRisks propertyRisks = null;
		ArrayList<PropertyRiskDetails> propertyRiskDetails = null;
		Iterator<PropertyRiskDetails> propertyRiskDetailsItr = null;
		PropertyRiskDetails propertyRiskDetail = null;
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		int riskType = 0;
		double commissionDiff = 0.0;
		double minDeductible = 5000; //provide a high deductible value
		double sumInsuredPerLocPAR =0;
		double sumInsuredPerPAR = 0;
		ArrayList<RuleAttribute> propertyRuleAttributeList =null;
		RuleAttribute sumInsuredCheckPAR = null;
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			sectionVOList = policyVO.getRiskDetails();
			if( !Utils.isEmpty( sectionVOList ) ){
				sectionListItr = sectionVOList.iterator();

				while( sectionListItr.hasNext() ){
					sectionVO = (SectionVO) sectionListItr.next();
					if( !Utils.isEmpty( sectionVO ) && ( section == ( sectionVO.getSectionId() ).intValue() ) ){
						logger.info( "PAR section present." );
						parSectionPresent = true;
						break;
					}
				}

				double SumInsuredPerLoc = 0.00;
				double SumInsuredPerPolicy = 0.00;
				
				if( parSectionPresent ){
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();
					if( !Utils.isEmpty( sectionVO.getCommission() ) ) commissionDiff = getCommissionDifference( sectionVO.getCommission(), RulesConstants.PAR_CLASS, policyVO );

					for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
						double sumInsured = 0;
						double cover = 0;
						double stockStorage = 0;

						location = (LocationVO) riskGroupDetailsEntry.getKey();
						locationDetail = (ParVO) riskGroupDetailsEntry.getValue();

						riskId = retrieveRiskId( location );
						ruleSet = new RuleSet();

						if( !Utils.isEmpty( locationDetail ) ){
							uwDetails = (PARUWDetailsVO) locationDetail.getUwDetails();
							if( !Utils.isEmpty( uwDetails ) ){
								if( !Utils.isEmpty( uwDetails.getCategoryRI() ) )
									riCategory = uwDetails.getCategoryRI();
								else
									throw new BusinessException( "", null, "RI Category is null for: " + riskId );

								if( !Utils.isEmpty( uwDetails.getHazardLevel() ) )
									hazardLevel = uwDetails.getHazardLevel();
								else
									throw new BusinessException( "", null, "Hazard Level is null for: " + riskId );
							}
							noOfLocations++;
							
							//sumInsured = getParSI(locationDetail);
							
							propertyRisks = (PropertyRisks) locationDetail.getCovers();
							

							if( !Utils.isEmpty( locationDetail.getBldCover() ) ) sumInsured = locationDetail.getBldCover();

							propertyRisks = (PropertyRisks) locationDetail.getCovers();

							if( !Utils.isEmpty( propertyRisks ) ){
								propertyRiskDetails = (ArrayList<PropertyRiskDetails>) propertyRisks.getPropertyCoversDetails();
								propertyRiskDetailsItr = propertyRiskDetails.iterator();

								while( propertyRiskDetailsItr.hasNext() ){
									/** Resetting the values to null */
									riskType = 0;
									cover = 0;
									coverDesc = null;

									propertyRiskDetail = (PropertyRiskDetails) propertyRiskDetailsItr.next();
									coverDesc = propertyRiskDetail.getDesc();
									if( !Utils.isEmpty( propertyRiskDetail.getCover() ) ) cover = propertyRiskDetail.getCover();
									if( !Utils.isEmpty( propertyRiskDetail.getRiskType() ) ) riskType = propertyRiskDetail.getRiskType();

									sumInsured = sumInsured + cover;
									if( riskType == 9 || STOCK_STORAGE.equalsIgnoreCase( coverDesc ) ){
										stockStorage = cover;
									}
									
									//get min of deductible. deductible should not be 0
									if(!Utils.isEmpty(propertyRiskDetail.getDeductibles()) 
											&& propertyRiskDetail.getDeductibles().doubleValue() != 0.0
											&& minDeductible > propertyRiskDetail.getDeductibles().doubleValue()){
										minDeductible = propertyRiskDetail.getDeductibles().doubleValue();
									}
									

								}
							}

							/*if( !Utils.isEmpty( locationDetail.getBldCover() ) ) sumInsured = locationDetail.getBldCover();

							propertyRisks = (PropertyRisks) locationDetail.getCovers();

							if( !Utils.isEmpty( propertyRisks ) ){
								propertyRiskDetails = (ArrayList<PropertyRiskDetails>) propertyRisks.getPropertyCoversDetails();
								propertyRiskDetailsItr = propertyRiskDetails.iterator();

								while( propertyRiskDetailsItr.hasNext() ){
									*//** Resetting the values to null *//*
									riskType = 0;
									cover = 0;
									coverDesc = null;

									propertyRiskDetail = (PropertyRiskDetails) propertyRiskDetailsItr.next();
									coverDesc = propertyRiskDetail.getDesc();
									if( !Utils.isEmpty( propertyRiskDetail.getCover() ) ) cover = propertyRiskDetail.getCover();
									if( !Utils.isEmpty( propertyRiskDetail.getRiskType() ) ) riskType = propertyRiskDetail.getRiskType();

									sumInsured = sumInsured + cover;
									if( riskType == 9 || STOCK_STORAGE.equalsIgnoreCase( coverDesc ) ){
										stockStorage = cover;
									}

								}
							}*/
							
							SumInsuredPerLoc = SvcUtils.getTotalSumInsured( policyVO, locationDetail.getBasicRiskId() , true );
							SumInsuredPerPolicy = SvcUtils.getTotalSumInsured( policyVO, locationDetail.getBasicRiskId(), false );

							double comSiParBi = getCombinedSIForPARBI(policyVO,location);
							double biSum = getBISI(policyVO,location);
							
							sumInsuredPerLocPAR = sumInsured;
							
							sumInsuredPerPAR += getParSI(locationDetail);
							
							totalSumInsured = totalSumInsured + sumInsured+biSum;
							
							if( riCategory == SvcConstants.RI_CATEGORY_ONE ){
								totalSumInsuredRICategory1 = totalSumInsuredRICategory1 + comSiParBi;
							}
							else if( riCategory == SvcConstants.RI_CATEGORY_TWO ){
								totalSumInsuredRICategory2 = totalSumInsuredRICategory2 + comSiParBi;
							}
							else if( riCategory == SvcConstants.RI_CATEGORY_THREE  ){
								totalSumInsuredRICategory3 = totalSumInsuredRICategory3 + comSiParBi;
							}
							else if( riCategory == SvcConstants.RI_CATEGORY_FOUR ){
								totalSumInsuredRICategory4 = totalSumInsuredRICategory4 + comSiParBi;
							}
							else if( riCategory == SvcConstants.RI_CATEGORY_FIVE ){
								totalSumInsuredRICategory5 = totalSumInsuredRICategory5 + comSiParBi;
							}
							else if( riCategory == SvcConstants.RI_CATEGORY_SIX ){
								totalSumInsuredRICategory6 = totalSumInsuredRICategory6 + comSiParBi;
							}
							else if( riCategory == SvcConstants.RI_CATEGORY_SEVEN ){
								totalSumInsuredRICategory7 = totalSumInsuredRICategory7 + comSiParBi;
							}

							if( sumInsured != 0 ) stockStoragePercent = ( stockStorage / sumInsured ) * 100;

							FetchAccumulationLimitStatusFunc accLimitStatus = (FetchAccumulationLimitStatusFunc) Utils.getBean( "accLimitStatus" );
							directorate = location.getDirectorate();
							accumulationLimitExceeded = accLimitStatus.getAccLimitStatus( directorate, sumInsured );
							logger.debug( "directorate: " + directorate + " accumulationLimitExceeded: " + accumulationLimitExceeded );
							
							if( !location.getToSave() ){
								continue;
							}

							ruleSet.setRuleSetName( RulesConstants.PROP_ALL_RISK );
							ruleSet.setRiskId( riskId );

							ArrayList<Fact> factList = (ArrayList<Fact>) ruleSet.getFact();

							Fact userFactor = new Fact();
							userFactor.setFactName( RulesConstants.FACT_USER );
							ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

							RuleAttribute roleAttribute = new RuleAttribute();
							roleAttribute.setAttributeName( "role" );
							roleAttribute.setAttributeValue( roleType );
							userRuleAttributeList.add( roleAttribute );

							Fact propertyFactor = new Fact();
							propertyFactor.setFactName( RulesConstants.FACT_PROPERTY );
							propertyRuleAttributeList = (ArrayList<RuleAttribute>) propertyFactor.getRuleSetAttribute();

							// Added as a part of ticket id 149213
							if( BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ))){
				            	RuleAttribute occupancyAttribute = new RuleAttribute();
								occupancyAttribute.setAttributeName( "hazardLevel" );
									//occupancyAttribute.setAttributeValue( new Integer( hazardLevel ).toString() );
								occupancyAttribute.setAttributeValue( Integer.valueOf( hazardLevel ).toString() );
								propertyRuleAttributeList.add( occupancyAttribute );
				            }

							if( !Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( "30" ) ){ //Hanamappa's change
							RuleAttribute occupancyTradeAttribute = new RuleAttribute();
							occupancyTradeAttribute.setAttributeName( "occupancyTrade" );
							occupancyTradeAttribute.setAttributeValue( String.valueOf(location.getOccTradeGroup()) );
							propertyRuleAttributeList.add( occupancyTradeAttribute );
							}
									

							RuleAttribute accumulationLimitAttribute = new RuleAttribute();
							accumulationLimitAttribute.setAttributeName( "accumulationLimitExceeded" );
							accumulationLimitAttribute.setAttributeValue( accumulationLimitExceeded );
							propertyRuleAttributeList.add( accumulationLimitAttribute );

							RuleAttribute stockPercentAttribute = new RuleAttribute();
							stockPercentAttribute.setAttributeName( "stockInStoragePercent" );
							stockPercentAttribute.setAttributeValue( new BigDecimal( decForm.format( stockStoragePercent ) ).toString() );
							propertyRuleAttributeList.add( stockPercentAttribute );
							
							// Added as a part of ticket id 149213
							if(!  (BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION )) )){

								RuleAttribute sumInsuredperlocationPAR = new RuleAttribute();
			                	sumInsuredperlocationPAR.setAttributeName( "sumInsuredperlocationPAR" );
			                	sumInsuredperlocationPAR.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocPAR ) ).toString() );  // 149213 new rule Value need to set
			                	propertyRuleAttributeList.add( sumInsuredperlocationPAR );
								
			                }
							
							/*
							  *Area Rules are configured only for Bahrain
							*/
							if( Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC ).equals(BAHRAIN_LOC) ){
								RuleAttribute areaAttribute = new RuleAttribute();
								areaAttribute.setAttributeName( "riskArea" );
								areaAttribute.setAttributeValue( directorate.toString() );
								propertyRuleAttributeList.add( areaAttribute );
							}
							
							/*Deductible rule for Bahrain*/
							
							if( Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC ).equals(BAHRAIN_LOC) ){

								Double deductible = 0.0;
								if( !Utils.isEmpty( locationDetail.getBldDeductibles() ) ){
									deductible = locationDetail.getBldDeductibles();
								}
								else{
									deductible = minDeductible;
								}

								if( minDeductible < deductible ){
									deductible = minDeductible;
								}
								String minimumDeductible = String.valueOf(deductible.intValue());
								RuleAttribute deductibleAttribute = new RuleAttribute();
								deductibleAttribute.setAttributeName( com.Constant.CONST_DEDUCTABLEMINVALUE );
								deductibleAttribute.setAttributeValue(minimumDeductible);
								propertyRuleAttributeList.add( deductibleAttribute );
							}
							
							/*Deductible rule for Bahrain - END*/
							Fact siFactor = new Fact();
							siFactor.setFactName( RulesConstants.FACT_SI );
							

							// Added as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION )) ){
			                	Fact commonFactor = new Fact();
								commonFactor.setFactName( RulesConstants.FACT_COMMON );
								ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

								RuleAttribute commissionAttribute = new RuleAttribute();
								commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
								commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
								commonRuleAttributeList.add( commissionAttribute );
								
								factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );		
			                }
							

							
							
							/* Imp Note : This flag check is added as temporary fix and this has to be removed when actual fix is implemented */
                            //if( RulesConstants.UWQ_RULES_ENABLE.equalsIgnoreCase( "Y" )){
							if( !policyVO.getIsPrepackaged() && Utils.getSingleValueAppConfig(SvcConstants.ISUWRULESREQUIRED).equalsIgnoreCase("YES")){
							/* Underwriting questions rules request mapper */
                            	Fact uwQuestionFactor = getUWQFact( RulesConstants.FACT_PAR_UWQUESTION, RulesConstants.PAR_SECTION_ID, locationDetail );
                            	factList.add( uwQuestionFactor );
                            }
							factList.add( userFactor );
							factList.add( propertyFactor );
							factList.add( siFactor );
							
							ruleSetList.add( ruleSet );
						}
					}
					
					Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
					while( ruleSetItr.hasNext() ){
						RuleSet ruleSet1 = ruleSetItr.next();
						if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.PROP_ALL_RISK.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){

							ArrayList<Fact> factList = (ArrayList<Fact>) ruleSet1.getFact();

							Iterator<Fact> factListItr = factList.iterator();
							while( factListItr.hasNext() ){
								Fact fact1 = factListItr.next();

							if( RulesConstants.FACT_SI.equalsIgnoreCase( fact1.getFactName() ) ){
								
									
									ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();

									RuleAttribute sumInsuredRiCat1Attribute = new RuleAttribute();
									sumInsuredRiCat1Attribute.setAttributeName( "combinedSIRICat1" );
									sumInsuredRiCat1Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory1 ) ).toString() );
									ruleAttributeList1.add( sumInsuredRiCat1Attribute );

									RuleAttribute sumInsuredRiCat2Attribute = new RuleAttribute();
									sumInsuredRiCat2Attribute.setAttributeName( "combinedSIRICat2" );
									sumInsuredRiCat2Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory2 ) ).toString() );
									ruleAttributeList1.add( sumInsuredRiCat2Attribute );

									RuleAttribute sumInsuredRiCat3Attribute = new RuleAttribute();
									sumInsuredRiCat3Attribute.setAttributeName( "combinedSIRICat3" );
									sumInsuredRiCat3Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory3 ) ).toString() );
									ruleAttributeList1.add( sumInsuredRiCat3Attribute );

									RuleAttribute sumInsuredRiCat4Attribute = new RuleAttribute();
									sumInsuredRiCat4Attribute.setAttributeName( "combinedSIRICat4" );
									sumInsuredRiCat4Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory4 ) ).toString() );
									ruleAttributeList1.add( sumInsuredRiCat4Attribute );

									RuleAttribute sumInsuredRiCat5Attribute = new RuleAttribute();
									sumInsuredRiCat5Attribute.setAttributeName( "combinedSIRICat5" );
									sumInsuredRiCat5Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory5 ) ).toString() );
									ruleAttributeList1.add( sumInsuredRiCat5Attribute );

									RuleAttribute sumInsuredRiCat6Attribute = new RuleAttribute();
									sumInsuredRiCat6Attribute.setAttributeName( "combinedSIRICat6" );
									sumInsuredRiCat6Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory6 ) ).toString() );
									ruleAttributeList1.add( sumInsuredRiCat6Attribute );

									RuleAttribute sumInsuredRiCat7Attribute = new RuleAttribute();
									sumInsuredRiCat7Attribute.setAttributeName( "combinedSIRICat7" );
									sumInsuredRiCat7Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory7 ) ).toString() );
									ruleAttributeList1.add( sumInsuredRiCat7Attribute );

									RuleAttribute sumInsuredAttribute = new RuleAttribute();
									// Added as a part of ticket id 149213
									/*Changed attribute from sumInsuredPerPAR to totalSumInsured - UAT feedback - Ticket Id - 149213*/
									if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION )) )
										sumInsuredAttribute.setAttributeName( "combinedSI" );
									else
										sumInsuredAttribute.setAttributeName( "sumInsuredCheckPARAndBI" );									
									sumInsuredAttribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsured ) ).toString() );									
									ruleAttributeList1.add( sumInsuredAttribute );
									
									
									/*RuleAttribute maxSIPerLocAttribute = new RuleAttribute();
									maxSIPerLocAttribute.setAttributeName( "maxSIPerLoc" );
									maxSIPerLocAttribute.setAttributeValue( new BigDecimal( decForm.format( SumInsuredPerLoc ) ).toString() );
									ruleAttributeList1.add( maxSIPerLocAttribute );
									
									RuleAttribute maxSIPerPolicyAttribute = new RuleAttribute();
									maxSIPerPolicyAttribute.setAttributeName( "maxSIPerPolicy" );
									maxSIPerPolicyAttribute.setAttributeValue( new BigDecimal( decForm.format( SumInsuredPerPolicy ) ).toString() );
									ruleAttributeList1.add( maxSIPerPolicyAttribute );*/
								
							}
							if( RulesConstants.FACT_PROPERTY.equalsIgnoreCase( fact1.getFactName() ) ){
								ArrayList<RuleAttribute> ruleAttributeList2 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();

								RuleAttribute noOfLocationAttribute = new RuleAttribute();
								noOfLocationAttribute.setAttributeName( "numberOfLocation" );
									// Radar Fix
								noOfLocationAttribute.setAttributeValue( Integer.valueOf( noOfLocations ).toString() );
								ruleAttributeList2.add( noOfLocationAttribute );
								
								// Added as a part of ticket id 149213
								/*Commented - UAT feedback - Ticket Id - 149213*/
								/*if(! ( BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )) ) ){
									sumInsuredCheckPAR = new RuleAttribute();
									sumInsuredCheckPAR.setAttributeName( "sumInsuredCheckPAR" );
									Changed attribute from sumInsuredPerPAR to totalSumInsured - UAT feedback - Ticket Id - 149213
									sumInsuredCheckPAR.setAttributeValue( new BigDecimal( decForm.format( totalSumInsured ) ).toString() );   // 149213 new rule Value need to set
									ruleAttributeList2.add( sumInsuredCheckPAR );
								}*/
								
							}
							}
						}
						
					}
				}
			}
		}

		return ruleInfo;
	}

	private double getCombinedSIForPARBI( PolicyVO policyVO, LocationVO location ){
		double combinedSI = 0.0;
		double parSI = 0.0;
		double biSI = 0.0;
 
		if( !Utils.isEmpty( policyVO ) ){
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PAR );
			SectionVO biSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_BI );

			if( !Utils.isEmpty( parSection ) && !Utils.isEmpty( parSection.getRiskGroupDetails() ) &&  !Utils.isEmpty( parSection.getRiskGroupDetails().get( location ) ) ){
				ParVO parDetails = (ParVO) parSection.getRiskGroupDetails().get( location );
				parSI = getParSI( parDetails );
			}

			if( !Utils.isEmpty( biSection ) && !Utils.isEmpty( biSection.getRiskGroupDetails() ) && !Utils.isEmpty( biSection.getRiskGroupDetails().get( location ) ) ){
				BIVO biDetails = (BIVO) biSection.getRiskGroupDetails().get( location );
				biSI = getBISI( biDetails );
			}
			combinedSI = parSI + biSI;
		}
		return combinedSI;
	}

	private double getBISI( PolicyVO policyVO, LocationVO location ) {
		
		double biSI = 0.0;
		if( !Utils.isEmpty( policyVO ) ){
			/*SectionVO parSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PAR );*/
			SectionVO biSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_BI );

			if( !Utils.isEmpty( biSection ) && !Utils.isEmpty( biSection.getRiskGroupDetails() ) && !Utils.isEmpty( biSection.getRiskGroupDetails().get( location ) ) ){
				BIVO biDetails = (BIVO) biSection.getRiskGroupDetails().get( location );
				biSI = getBISI( biDetails );
			}
		}
		
		return biSI;
	}

	private double getParSI( ParVO locationDetail ){

		double sumInsured = 0.0;
		ArrayList<PropertyRiskDetails> propertyRiskDetails;
		if( !Utils.isEmpty( locationDetail.getBldCover() ) ) sumInsured = locationDetail.getBldCover();

		PropertyRisks propertyRisks = (PropertyRisks) locationDetail.getCovers();

		if( !Utils.isEmpty( propertyRisks ) ){
			propertyRiskDetails = (ArrayList<PropertyRiskDetails>) propertyRisks.getPropertyCoversDetails();
			Iterator<PropertyRiskDetails> propertyRiskDetailsItr = propertyRiskDetails.iterator();

			while( propertyRiskDetailsItr.hasNext() ){
				/** Resetting the values to null */
				double cover = 0;

				PropertyRiskDetails propertyRiskDetail = (PropertyRiskDetails) propertyRiskDetailsItr.next();
				if( !Utils.isEmpty( propertyRiskDetail.getCover() ) ) cover = propertyRiskDetail.getCover();
				sumInsured = sumInsured + cover;
			}
		}

		return sumInsured;

	}
	
	
	private boolean isStockInsuredInPar( ParVO parVO )
	{
		boolean isInsured = false;
		ArrayList<PropertyRiskDetails> propertyRiskDetails;
		PropertyRisks propertyRisks = (PropertyRisks) parVO.getCovers();

		if( !Utils.isEmpty( propertyRisks ) ){
			propertyRiskDetails = (ArrayList<PropertyRiskDetails>) propertyRisks.getPropertyCoversDetails();
			Iterator<PropertyRiskDetails> propertyRiskDetailsItr = propertyRiskDetails.iterator();

			while( propertyRiskDetailsItr.hasNext() )
			{
				PropertyRiskDetails propertyRiskDetail = (PropertyRiskDetails) propertyRiskDetailsItr.next();
				if( !Utils.isEmpty( propertyRiskDetail.getCover() ) && 
						!Utils.isEmpty(propertyRiskDetail.getRiskType()) && 
						propertyRiskDetail.getRiskType() == 9 && 
						!Utils.isEmpty( propertyRiskDetail.getDeductibles() ) &&
						 propertyRiskDetail.getDeductibles() != 0.0) 
				{
					isInsured = true;
				}
			}
		}

		return isInsured;

	}
	
	
	private boolean isSumInsuredIsLessThanStockInPar( Double dosSumInsured,ParVO parVO )
	{
		boolean sumInsuredIsLess = true;
		ArrayList<PropertyRiskDetails> propertyRiskDetails;
		PropertyRisks propertyRisks = (PropertyRisks) parVO.getCovers();
		Double stocksCover = 0.0;

		if( !Utils.isEmpty( propertyRisks ) ){
			propertyRiskDetails = (ArrayList<PropertyRiskDetails>) propertyRisks.getPropertyCoversDetails();
			Iterator<PropertyRiskDetails> propertyRiskDetailsItr = propertyRiskDetails.iterator();

			while( propertyRiskDetailsItr.hasNext() )
			{
				PropertyRiskDetails propertyRiskDetail = (PropertyRiskDetails) propertyRiskDetailsItr.next();
				if( !Utils.isEmpty( propertyRiskDetail.getCover() ) && 
						!Utils.isEmpty(propertyRiskDetail.getRiskType()) && 
						propertyRiskDetail.getRiskType() == 9 && 
						!Utils.isEmpty( propertyRiskDetail.getDeductibles() ) &&
						 propertyRiskDetail.getDeductibles() != 0.0
						) 
				{
					stocksCover =  propertyRiskDetail.getCover();
				}
			}
		}
		if( dosSumInsured > stocksCover)
		{
			sumInsuredIsLess = false;
		}
		return sumInsuredIsLess;

	}
	
	@SuppressWarnings( "unused" )
	private boolean isMBSILessThanContentsInPar( Double mbSumInsured,ParVO parVO )
	{
		boolean sumInsuredIsLess = true;
		ArrayList<PropertyRiskDetails> propertyRiskDetails;
		PropertyRisks propertyRisks = (PropertyRisks) parVO.getCovers();
		Double contentSI = 0.0;

		if( !Utils.isEmpty( propertyRisks ) ){
			propertyRiskDetails = (ArrayList<PropertyRiskDetails>) propertyRisks.getPropertyCoversDetails();
			Iterator<PropertyRiskDetails> propertyRiskDetailsItr = propertyRiskDetails.iterator();

			while( propertyRiskDetailsItr.hasNext() )
			{
				PropertyRiskDetails propertyRiskDetail = (PropertyRiskDetails) propertyRiskDetailsItr.next();
				if( !Utils.isEmpty( propertyRiskDetail.getCover() ) && 
						!Utils.isEmpty(propertyRiskDetail.getRiskType()) && 
						!Utils.isEmpty( propertyRiskDetail.getDeductibles() ) &&
						 propertyRiskDetail.getDeductibles() != 0.0
						) 
				{
					contentSI +=  propertyRiskDetail.getCover();
				}
			}
		}
		if( mbSumInsured > contentSI)
		{
			sumInsuredIsLess = false;
		}
		return sumInsuredIsLess;

	}
	
	private double getBISI(BIVO locationDetail) {
		
		double sumInsured = 0.0;
		if(!Utils.isEmpty(locationDetail))
			sumInsured = locationDetail.getSumInsured();
		
		return sumInsured;
		
	}

	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private RuleInfo createRuleInfoForMoney( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		String riskId = null;
		double estdAnnulCarry = -1;
		double singleTransit = -1;
		Boolean isCashInResidence = null;
		SectionVO sectionVO = null;
		MoneyVO moneyVO = null;
		List<SectionVO> sectionVOList = null;
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		Iterator<SectionVO> sectionListItr = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		boolean moneySectionPresent = false;
		LocationVO location = null;
		double cashInSafe = 0;
		double cashInDrawer = 0;
		double cashInSafeBusHours = 0;
		double cashInDrawerBusHours = 0;
		double cashInSafeNonBusHours = 0;
		double cashInDrawerNonBusHours = 0;
		double cashInResidence = 0;
		String singleTransitExceedsAnnualCarrying = null;
		List<SumInsuredVO> sumInsuredDets = null;
		double commissionDiff = 0.0;
		
		/* Added for storing totals for all location */
		double totalSingleTransit = 0;
		double totalCashInSafe = 0;
		double totalCashInDrawer = 0;
		double totalCashInResidence = 0;
		/*
		 * This list variable will be used in case of
		 * pre-packaged flow
		 */
		List<Contents> contentsList = null;

		ArrayList<CashResidenceVO> cashResDetails = null;
		Iterator<CashResidenceVO> cashResDetailsItr = null;
		CashResidenceVO cashResidenceVO = null;

		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			sectionVOList = policyVO.getRiskDetails();
			if( !Utils.isEmpty( sectionVOList ) ){
				sectionListItr = sectionVOList.iterator();

				while( sectionListItr.hasNext() ){
					sectionVO = (SectionVO) sectionListItr.next();
					if( !Utils.isEmpty( sectionVO ) && ( section == sectionVO.getSectionId().intValue() ) ){
						logger.info( "Money section present." );
						moneySectionPresent = true;
						break;
					}
				}

				if( moneySectionPresent ){
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();
					if( !Utils.isEmpty( sectionVO.getCommission() ) ) commissionDiff = getCommissionDifference( sectionVO.getCommission(), RulesConstants.MONEY_CLASS, policyVO );

					for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
						location = (LocationVO) riskGroupDetailsEntry.getKey();
						moneyVO = (MoneyVO) riskGroupDetailsEntry.getValue();

						riskId = retrieveRiskId( location );
						ruleSet = new RuleSet();

						if( !Utils.isEmpty( moneyVO ) ){
							estdAnnulCarry = -1;
							singleTransit = -1;
							cashInSafe = 0;
							cashInDrawer = 0;
							cashInSafeBusHours = 0;
							cashInDrawerBusHours = 0;
							cashInSafeNonBusHours = 0;
							cashInDrawerNonBusHours = 0;
							cashInResidence = 0;

							sumInsuredDets = moneyVO.getSumInsuredDets();

							/*
							 * Added a temporary fix for Rules Invocation in case of Prepackaged flow.
							 * This check has to be removed when MoneyContent.jsp contents load approach is
							 * changed to dynamic loading rather than hard-coded contents
							 */
							if( Utils.isEmpty( policyVO.getIsPrepackaged() ) || !policyVO.getIsPrepackaged().booleanValue() ){

								if( !Utils.isEmpty( sumInsuredDets ) ){
								/*
								 * size check is required to check because suminsured list may have only few contents entered as all
								 * are not mandatory. If only few contents are entered and we do sumInsuredDets.get( 5 ) will throw exception.
								 * so to control this added size check.
								 *  	
								 */
								 if(sumInsuredDets.size()>0){
									if( !Utils.isEmpty( sumInsuredDets.get( 0 ) ) && !Utils.isEmpty( sumInsuredDets.get( 0 ).getSumInsured() ) )
										estdAnnulCarry = sumInsuredDets.get( 0 ).getSumInsured();
								 }
								 if(sumInsuredDets.size()>1){
									if( !Utils.isEmpty( sumInsuredDets.get( 1 ) ) && !Utils.isEmpty( sumInsuredDets.get( 1 ).getSumInsured() ) )
										singleTransit = sumInsuredDets.get( 1 ).getSumInsured();
										totalSingleTransit += singleTransit;
								 }
								 if(sumInsuredDets.size()>2){
									if( !Utils.isEmpty( sumInsuredDets.get( 2 ) ) && !Utils.isEmpty( sumInsuredDets.get( 2 ).getSumInsured() ) )
										cashInSafeBusHours = sumInsuredDets.get( 2 ).getSumInsured();
								 }
								 if(sumInsuredDets.size()>3){
									if( !Utils.isEmpty( sumInsuredDets.get( 3 ) ) && !Utils.isEmpty( sumInsuredDets.get( 3 ).getSumInsured() ) )
										cashInDrawerBusHours = sumInsuredDets.get( 3 ).getSumInsured();
								 }
								 if(sumInsuredDets.size()>4){
									if( !Utils.isEmpty( sumInsuredDets.get( 4 ) ) && !Utils.isEmpty( sumInsuredDets.get( 4 ).getSumInsured() ) )
										cashInSafeNonBusHours = sumInsuredDets.get( 4 ).getSumInsured();
								 }
								 if(sumInsuredDets.size()>5){
									if( !Utils.isEmpty( sumInsuredDets.get( 5 ) ) && !Utils.isEmpty( sumInsuredDets.get( 5 ).getSumInsured() ) )
										cashInDrawerNonBusHours = sumInsuredDets.get( 5 ).getSumInsured();
								 }
								}
							}
							else{
								contentsList = moneyVO.getContentsList();
								if( !Utils.isEmpty( contentsList ) ){
									for( Contents content : contentsList ){

										/** for Estimated annual carrying */
										if( !Utils.isEmpty( content.getRiskType() ) && content.getRiskType() == 1 && !Utils.isEmpty( content.getRiskCat() )
												&& content.getRiskCat() == 1 ){
											estdAnnulCarry = content.getCover().doubleValue();
											continue;
										}

										/** for singleTransit LIMIT */
										if( !Utils.isEmpty( content.getRiskType() ) && content.getRiskType() == 1 && !Utils.isEmpty( content.getRiskCat() )
												&& content.getRiskCat() == 2 ){
											singleTransit = content.getCover().doubleValue();
											totalSingleTransit += singleTransit;
											continue;
										}

										/** Cash in safe during business hours */
										if( !Utils.isEmpty( content.getRiskType() ) && content.getRiskType() == 3 && !Utils.isEmpty( content.getRiskCat() )
												&& content.getRiskCat() == 0 ){
											cashInSafeBusHours = content.getCover().doubleValue();
											continue;
										}

										/** Cash in drawer during business hours */
										if( !Utils.isEmpty( content.getRiskType() ) && content.getRiskType() == 8 && !Utils.isEmpty( content.getRiskCat() )
												&& content.getRiskCat() == 0 ){
											cashInDrawerBusHours = content.getCover().doubleValue();
											continue;
										}

										/** Cash in safe after  business hours */
										if( !Utils.isEmpty( content.getRiskType() ) && content.getRiskType() == 2 && !Utils.isEmpty( content.getRiskCat() )
												&& content.getRiskCat() == 1 ){
											cashInSafeNonBusHours = content.getCover().doubleValue();
											continue;
										}

										/** Cash in drawer after business hours */
										if( !Utils.isEmpty( content.getRiskType() ) && content.getRiskType() == 2 && !Utils.isEmpty( content.getRiskCat() )
												&& content.getRiskCat() == 3 ){
											cashInDrawerNonBusHours = content.getCover().doubleValue();
											continue;
										}
									}
								}
							}
							/*
							 * This is a common block for both prepackaged and
							 * flexi pack hence added after above checks
							 * 
							 */
							isCashInResidence = moneyVO.getCashInResidence();
							cashResDetails = (ArrayList<CashResidenceVO>) moneyVO.getCashResDetails();
							
							/*if estimated annual carry and Single transit values are configured only then it should call for referral*/
							if(estdAnnulCarry > 0 && singleTransit > 0 )
								singleTransitExceedsAnnualCarrying = ( singleTransit - estdAnnulCarry > 0 ? "Y" : "N" );
							else
								singleTransitExceedsAnnualCarrying ="N";
								

							cashInSafe = cashInSafeBusHours + cashInSafeNonBusHours;
							totalCashInSafe += cashInSafe;
//							cashInDrawer = cashInDrawerBusHours + cashInDrawerNonBusHours;
							cashInDrawer = cashInDrawerBusHours ;
							totalCashInDrawer += cashInDrawer;
							if( !Utils.isEmpty( isCashInResidence ) && isCashInResidence && !Utils.isEmpty( cashResDetails ) ){
								cashResDetailsItr = cashResDetails.iterator();
								while( cashResDetailsItr.hasNext() ){
									cashResidenceVO = (CashResidenceVO) cashResDetailsItr.next();
									if( !Utils.isEmpty( cashResidenceVO ) && !Utils.isEmpty( cashResidenceVO.getSumInsuredDets() )
											&& !Utils.isEmpty( cashResidenceVO.getSumInsuredDets().getSumInsured() ) ){
										cashInResidence = cashInResidence + cashResidenceVO.getSumInsuredDets().getSumInsured();
										
									}
								}
								totalCashInResidence += cashInResidence;
							}

							if( logger.isDebug() ){
								logger.debug( "singleTransit: " + singleTransit );
								logger.debug( "estdAnnulCarry: " + estdAnnulCarry );
								logger.debug( "cashInSafeBusHours: " + cashInSafeBusHours );
								logger.debug( "cashInDrawerBusHours: " + cashInDrawerBusHours );
								logger.debug( "cashInSafeNonBusHours: " + cashInSafeNonBusHours );
								logger.debug( "cashInDrawerNonBusHours: " + cashInDrawerNonBusHours );
								logger.debug( "isCashInResidence: " + isCashInResidence );
								logger.debug( "singleTransitExceedsAnnualCarrying: " + singleTransitExceedsAnnualCarrying );
								logger.debug( "cashInResidence: " + cashInResidence );
								logger.debug( "totalSingleTransit: " + totalSingleTransit );
								logger.debug( "totalCashInDrawer: " + totalCashInDrawer );
								logger.debug( "totalCashInSafe: " + totalCashInSafe );
								logger.debug( "totalCashInResidence: " + totalCashInResidence );
							}

							if( !location.getToSave() ){
								continue;
							}

							ruleSet.setRuleSetName( RulesConstants.MONEY );
							ruleSet.setRiskId( riskId );

							ArrayList<Fact> factList = (ArrayList<Fact>) ruleSet.getFact();

							Fact userFactor = new Fact();
							userFactor.setFactName( RulesConstants.FACT_USER );
							ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

							RuleAttribute roleAttribute = new RuleAttribute();
							roleAttribute.setAttributeName( "role" );
							roleAttribute.setAttributeValue( roleType );
							userRuleAttributeList.add( roleAttribute );

							Fact moneyFactor = new Fact();
							moneyFactor.setFactName( RulesConstants.FACT_MONEY );
							ArrayList<RuleAttribute> moneyRuleAttributeList = (ArrayList<RuleAttribute>) moneyFactor.getRuleSetAttribute();

							RuleAttribute singleTransitAttribute = new RuleAttribute();
							singleTransitAttribute.setAttributeName( "cashInSingleTransit" );
							singleTransitAttribute.setAttributeValue( new BigDecimal( decForm.format( singleTransit ) ).toString() );
							moneyRuleAttributeList.add( singleTransitAttribute );

							RuleAttribute annualCarryingAttribute = new RuleAttribute();
							annualCarryingAttribute.setAttributeName( "cashInAnnualCarrying" );
							annualCarryingAttribute.setAttributeValue( new BigDecimal( decForm.format( estdAnnulCarry ) ).toString() );
							moneyRuleAttributeList.add( annualCarryingAttribute );

							RuleAttribute cashInSafeAttribute = new RuleAttribute();
							cashInSafeAttribute.setAttributeName( "cashLockedInSafe" );
							cashInSafeAttribute.setAttributeValue( new BigDecimal( decForm.format( cashInSafe ) ).toString() );
							moneyRuleAttributeList.add( cashInSafeAttribute );

							RuleAttribute cashInDrawerAttribute = new RuleAttribute();
							cashInDrawerAttribute.setAttributeName( "cashLockedInDrawer" );
							cashInDrawerAttribute.setAttributeValue( new BigDecimal( decForm.format( cashInDrawer ) ).toString() );
							moneyRuleAttributeList.add( cashInDrawerAttribute );
							
							// Added as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
			                	RuleAttribute cashInDrawerNonBusHoursAttribute = new RuleAttribute();
								cashInDrawerNonBusHoursAttribute.setAttributeName( "cashLockedInDrawerNonBusHours" );
								cashInDrawerNonBusHoursAttribute.setAttributeValue( new BigDecimal( decForm.format( cashInDrawerNonBusHours ) ).toString() );
								moneyRuleAttributeList.add( cashInDrawerNonBusHoursAttribute );
			                }

							RuleAttribute cashInResidenceAttribute = new RuleAttribute();
							cashInResidenceAttribute.setAttributeName( "cashLockedInEmpResidence" );
							cashInResidenceAttribute.setAttributeValue( new BigDecimal( decForm.format( cashInResidence ) ).toString() );
							moneyRuleAttributeList.add( cashInResidenceAttribute );

							RuleAttribute singleTransitExceedsAnnualCarryingAttribute = new RuleAttribute();
							singleTransitExceedsAnnualCarryingAttribute.setAttributeName( "singleTransitExceedsAnnualCarrying" );
							singleTransitExceedsAnnualCarryingAttribute.setAttributeValue( singleTransitExceedsAnnualCarrying );
							moneyRuleAttributeList.add( singleTransitExceedsAnnualCarryingAttribute );

							// Added as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )) {
			                	Fact commonFactor = new Fact();
								commonFactor.setFactName( RulesConstants.FACT_COMMON );
								ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

								RuleAttribute commissionAttribute = new RuleAttribute();
								commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
								commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
								commonRuleAttributeList.add( commissionAttribute );
								
								factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
			                }
							

							factList.add( userFactor );
							factList.add( moneyFactor );
							ruleSetList.add( ruleSet );
						}
					}
					// Added as a part of ticket id 149213
					if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )) {
	                	Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
						while( ruleSetItr.hasNext() ){
							RuleSet ruleSet1 = ruleSetItr.next();
							if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.MONEY.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){
								ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1.getFact();

								Iterator<Fact> factListItr = factList1.iterator();
								while( factListItr.hasNext() ){
									Fact fact1 = factListItr.next();

									if( RulesConstants.FACT_MONEY.equalsIgnoreCase( fact1.getFactName() ) ){
										ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();
						
										RuleAttribute totalSingleTransitAttribute = new RuleAttribute();
										totalSingleTransitAttribute.setAttributeName( "cashInSingleTransitPerPolicy" );
										totalSingleTransitAttribute.setAttributeValue( new BigDecimal( decForm.format( totalSingleTransit ) ).toString() );
										ruleAttributeList1.add( totalSingleTransitAttribute );
										
										RuleAttribute totalCashInSafeAttribute = new RuleAttribute();
										totalCashInSafeAttribute.setAttributeName( "cashInSafePerPolicy" );
										totalCashInSafeAttribute.setAttributeValue( new BigDecimal( decForm.format( totalCashInSafe ) ).toString() );
										ruleAttributeList1.add( totalCashInSafeAttribute );
										
										RuleAttribute totalCashInDrawerAttribute = new RuleAttribute();
										totalCashInDrawerAttribute.setAttributeName( "cashInDrawerPerPolicy" );
										totalCashInDrawerAttribute.setAttributeValue( new BigDecimal( decForm.format( totalCashInDrawer ) ).toString() );
										ruleAttributeList1.add( totalCashInDrawerAttribute );
										
										RuleAttribute totalCashInResidenceAttribute = new RuleAttribute();
										totalCashInResidenceAttribute.setAttributeName( "cashInEmpResidencePerPolicy" );
										totalCashInResidenceAttribute.setAttributeValue( new BigDecimal( decForm.format( totalCashInResidence ) ).toString() );
										ruleAttributeList1.add( totalCashInResidenceAttribute );
									}
								}
							}
						}
	                }
					
				}
			}
		}
		return ruleInfo;
	}

	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private RuleInfo createRuleInfoForPL( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		int indemnityAmtLimitCd = 0;
		String indemnityAmtLimitStr = null;
		double indemnityAmtLimit = 0;
		double totalIndemnityAmtLimit = 0;
		int sumInsuredBasisCd = 0;
		String sumInsuredBasisUnlimited = null;
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		List<SectionVO> sectionVOList = null;
		PublicLiabilityVO publicLiability = null;
		//For Oman
		int noOfLocations = 0;

		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		SectionVO sectionVO = null;
		ArrayList<Fact> factList = null;
		boolean plSectionPresent = false;
		Iterator<SectionVO> sectionListItr = null;
		LocationVO location = null;
		double commissionDiff = 0.0;
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		double sumInsuredPerLoc = 0;
		//double sumInsuredPerPolicy =0;
		
		
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			sectionVOList = policyVO.getRiskDetails();
			if( !Utils.isEmpty( sectionVOList ) ){
				sectionListItr = sectionVOList.iterator();

				while( sectionListItr.hasNext() ){
					sectionVO = (SectionVO) sectionListItr.next();
					if( !Utils.isEmpty( sectionVO ) && ( section == ( sectionVO.getSectionId() ).intValue() ) ){
						logger.info( "PL section present." );
						plSectionPresent = true;
						break;
					}
				}

				if( plSectionPresent ){
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();
					if( !Utils.isEmpty( sectionVO.getCommission() ) ) commissionDiff = getCommissionDifference( sectionVO.getCommission(), RulesConstants.PL_CLASS, policyVO );

					for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
						location = (LocationVO) riskGroupDetailsEntry.getKey();
						publicLiability = (PublicLiabilityVO) riskGroupDetailsEntry.getValue();
						
						ruleSet = new RuleSet();
						riskId = retrieveRiskId( location );

						if( !Utils.isEmpty( publicLiability ) ){
							if( !Utils.isEmpty( publicLiability.getIndemnityAmtLimit() ) ) indemnityAmtLimitCd = publicLiability.getIndemnityAmtLimit();
							if( !Utils.isEmpty( publicLiability.getSumInsuredBasis() ) ) sumInsuredBasisCd = publicLiability.getSumInsuredBasis();

							// Fetching the indemnityAmtLimit based on the code passed in PublicLiabilityVO
							String schemeCode="";
							Date preparedDate=new Date();
							if(!Utils.isEmpty(policyVO.getScheme())){
								schemeCode = policyVO.getScheme().getSchemeCode().toString();
							}
							if(!Utils.isEmpty(policyVO.getCreated())){
								preparedDate = policyVO.getCreated();
							}
							SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_HYPHEN);
							String d2 = Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_LIVEDATE);
							Date JLTLiveDate=null;
							try {
								JLTLiveDate = s2.parse(d2);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//							int userId = Integer.parseInt(Utils.getSingleValueAppConfig("JLT_USER_ID"));
							int quoteCreatedBy = 0;
							if(!Utils.isEmpty(policyVO.getCreatedBy())) {
								quoteCreatedBy = Integer.parseInt(policyVO.getCreatedBy());
							}
							/*
							 * Adding code change to get JLT web service limit for referral case
							 */
							boolean isValidSceheme= SvcUtils.isValidSchemeCode(policyVO);
							boolean isValidUser= SvcUtils.isValidUser(policyVO);
							if (isValidSceheme
									&& JLTLiveDate.compareTo(preparedDate) <= 0 && SvcConstants.DUBAI == Integer.parseInt(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )
									&& isValidUser) {
								if(indemnityAmtLimitCd==8) {
									indemnityAmtLimitStr = publicLiability.getLiabilityLimit().toString();
								}
								else {
									indemnityAmtLimitStr = SvcUtils.getLookUpDescription( "JLT_PL_LIM", policyVO.getScheme().getTariffCode().toString(), RulesConstants.LOOKUP_LEVEL2,
											indemnityAmtLimitCd );
								}

							}
							else {
								indemnityAmtLimitStr = SvcUtils.getLookUpDescription( "PL_LIMIT", policyVO.getScheme().getTariffCode().toString(), RulesConstants.LOOKUP_LEVEL2,
										indemnityAmtLimitCd );
							}

							if( !Utils.isEmpty( indemnityAmtLimitStr, true ) ) indemnityAmtLimit = new Double( indemnityAmtLimitStr );
							totalIndemnityAmtLimit += indemnityAmtLimit;

							if( sumInsuredBasisCd == Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.SUM_INSURED_BASIS_UNLTD ) ).intValue() )
								sumInsuredBasisUnlimited = "Y";
							else
								sumInsuredBasisUnlimited = "N";
							
							
							sumInsuredPerLoc = getPLSI(publicLiability, policyVO);
							
							//sumInsuredPerPolicy += sumInsuredPerLoc; //Commented as it's not used -  UAT feedback - Ticket Id 149213
							
							//For Oman
							noOfLocations++;
							
							if( !location.getToSave() ){
								continue;
							}
							
							
							
							ruleSet.setRuleSetName( RulesConstants.PUBLIC_LIAB );
							ruleSet.setRiskId( riskId );

							factList = (ArrayList<Fact>) ruleSet.getFact();

							Fact userFactor = new Fact();
							userFactor.setFactName( RulesConstants.FACT_USER );
							ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

							RuleAttribute roleAttribute = new RuleAttribute();
							roleAttribute.setAttributeName( "role" );
							roleAttribute.setAttributeValue( roleType );
							userRuleAttributeList.add( roleAttribute );

							Fact plFactor = new Fact();
							plFactor.setFactName( RulesConstants.FACT_PL );
							ArrayList<RuleAttribute> plRuleAttributeList = (ArrayList<RuleAttribute>) plFactor.getRuleSetAttribute();

							RuleAttribute indemnityAttribute = new RuleAttribute();
							indemnityAttribute.setAttributeName( "liabilityPerLocation" );
							indemnityAttribute.setAttributeValue( new BigDecimal( decForm.format( indemnityAmtLimit ) ).toString() );
							plRuleAttributeList.add( indemnityAttribute );

							RuleAttribute occupancyTradeAttribute = new RuleAttribute();
							occupancyTradeAttribute.setAttributeName( "occupancyTrade" );
							occupancyTradeAttribute.setAttributeValue( String.valueOf(location.getOccTradeGroup()) );
							plRuleAttributeList.add( occupancyTradeAttribute );
							
							// Added as a part of ticket id 149213
							if( ! (BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )) ){
			                	
								/*Commented - UAT feedback - Ticket Id - 149213*/
								/*RuleAttribute sumInsuredperlocationPL = new RuleAttribute();
			                	sumInsuredperlocationPL.setAttributeName( "sumInsuredperlocationPL" );
			                	sumInsuredperlocationPL.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLoc ) ).toString() );  // 149213 new rule Value need to set
			                	plRuleAttributeList.add( sumInsuredperlocationPL );*/
			                }

							// Added as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )) {
								
			                	RuleAttribute siBasisAttribute = new RuleAttribute();
								siBasisAttribute.setAttributeName( "sumInsuredBasisUnlimited" );
								siBasisAttribute.setAttributeValue( sumInsuredBasisUnlimited );
								plRuleAttributeList.add( siBasisAttribute );
								
			                }
							

							/* Imp Note : This flag check is added as temporary fix and this has to be removed when actual fix is implemented */
                            //if( RulesConstants.UWQ_RULES_ENABLE.equalsIgnoreCase( "Y" )){
							if( !policyVO.getIsPrepackaged() && Utils.getSingleValueAppConfig(SvcConstants.ISUWRULESREQUIRED).equalsIgnoreCase("YES")){
							 /* Underwriting questions rules request mapper */
                            	Fact uwQuestionFactor = getUWQFact( RulesConstants.FACT_PL_UWQUESTION, RulesConstants.PL_SECTION_ID, publicLiability);
                            	factList.add( uwQuestionFactor );
                            }
							
							/*
							 * Added only for JLT API 
							 */
							if (isValidSceheme
									&& JLTLiveDate.compareTo(preparedDate) <= 0 && SvcConstants.DUBAI == Integer
											.parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) 
											&& isValidUser) {

								for (Fact fact : factList) {
									if (fact.getFactName().equals(RulesConstants.FACT_PL_UWQUESTION)) {

										List<RuleAttribute> attributes = fact.getRuleSetAttribute();
										for (RuleAttribute ruleAttribute : attributes) {
											if (ruleAttribute.getAttributeName().equals("UWQ_SEC6_5")) {
												ruleAttribute.setAttributeValue("no");
											}
											/*
											 * Added for student liability count more than 200, it should not trigger the referral till 200 student count
											 * 
											 */
											else if (ruleAttribute.getAttributeName().equals("UWQ_SEC6_57")) {

												UWQuestionsVO uwQuestionsVO = publicLiability.getUwQuestions();

												if (!Utils.isEmpty(uwQuestionsVO)) {
													boolean isStudentLiabilityUQPresent = false;
													for (UWQuestionVO questionVO : uwQuestionsVO.getQuestions()) {
														
														if(questionVO.getQId() == SvcConstants.UW_GENERAL_QUESTION_STUDENT_LIABILITY && !Utils.isEmpty(questionVO.getResponseType())
																&& questionVO.getResponseType()
																.equals(UWQuestionRespType.RADIO) && questionVO.getResponse().equals("yes")) {
															isStudentLiabilityUQPresent = true;
														}
														
														if (questionVO
																.getQId() == 58
																&& !Utils.isEmpty(questionVO.getResponseType())
																&& questionVO.getResponseType()
																		.equals(UWQuestionRespType.TEXT) && isStudentLiabilityUQPresent) {
															if(Integer.parseInt(questionVO.getResponse())>200)
																ruleAttribute.setAttributeValue("yes");
															else 
																ruleAttribute.setAttributeValue("no");
														}
													}
												}
											}
										}
									}
								}
							}
							
							factList.add( userFactor );
							factList.add( plFactor );
							
							// Added as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
			                	Fact commonFactor = new Fact();
								commonFactor.setFactName( RulesConstants.FACT_COMMON );
								ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

								RuleAttribute commissionAttribute = new RuleAttribute();
								commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
								commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
								commonRuleAttributeList.add( commissionAttribute );
			                	factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
			                }
							ruleSetList.add( ruleSet );
						}
					}

					Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
					while( ruleSetItr.hasNext() ){
						RuleSet ruleSet1 = ruleSetItr.next();
						if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.PUBLIC_LIAB.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){
							ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1.getFact();

							Iterator<Fact> factListItr = factList1.iterator();
							while( factListItr.hasNext() ){
								Fact fact1 = factListItr.next();

								if( RulesConstants.FACT_PL.equalsIgnoreCase( fact1.getFactName() ) ){
									ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();
									
									// Added as a part of ticket id 149213
									if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
					                	RuleAttribute totalindemnityAttribute = new RuleAttribute();
										totalindemnityAttribute.setAttributeName( "liabilityPerPolicy" );
										totalindemnityAttribute.setAttributeValue( new BigDecimal( decForm.format( totalIndemnityAmtLimit ) ).toString() );
										ruleAttributeList1.add( totalindemnityAttribute );
					                }
									
									/*Commented - UAT feedback - Ticket Id - 149213*/
									/*else{
//										sumInsuredPerPolicy = sumInsuredPerPolicy + sumInsuredPerLoc;
										
										RuleAttribute sumInsuredCheckPL = new RuleAttribute();
										sumInsuredCheckPL.setAttributeName( "sumInsuredCheckPL" );
										sumInsuredCheckPL.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerPolicy ) ).toString() );   // 149213 new rule Value need to set
										ruleAttributeList1.add( sumInsuredCheckPL );
									}*/
									
									// FOr Oman PL loc rule
									RuleAttribute noOfLocationAttribute = new RuleAttribute();
									noOfLocationAttribute.setAttributeName( "numberOfLocation" );
									//noOfLocationAttribute.setAttributeValue( new Integer( noOfLocations ).toString() );
									noOfLocationAttribute.setAttributeValue( Integer.valueOf( noOfLocations ).toString() );
									ruleAttributeList1.add(noOfLocationAttribute);
								}
							}
						}
					}
				}
			}
		}
		return ruleInfo;
	}

	private Fact getCommonFactor( PolicyVO policyVO, String riskGroupId, Fact commonFactor, int sectionId ){
		
		ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();
		
		double sumInsuredPerLoc = getTotalSumInsured( policyVO, riskGroupId , sectionId, true );
		double sumInsuredPerPolicy = getTotalSumInsured( policyVO, riskGroupId, sectionId, false );

		RuleAttribute maxSIPerLocAttribute = new RuleAttribute();
		maxSIPerLocAttribute.setAttributeName( "maxSIPerLoc" );
		maxSIPerLocAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLoc ) ).toString() );
		commonRuleAttributeList.add( maxSIPerLocAttribute );
		
		RuleAttribute maxSIPerPolicyAttribute = new RuleAttribute();
		maxSIPerPolicyAttribute.setAttributeName( "maxSIPerPolicy" );
		maxSIPerPolicyAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerPolicy ) ).toString() );
		commonRuleAttributeList.add( maxSIPerPolicyAttribute );
		
		return commonFactor;
	}

	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForWC( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		int empType = 0;
		/* TODO: Defaulting the empTypeBlackListed to N as this rule is for future implementation and has to pass always for now. */
		String empTypeBlackListed = "N";
		int noOfEmp = 0;
		int liabPerLocCD = 0;
		String liabPerLocStr = null;
		BigDecimal liabPerLoc = new BigDecimal( 0.00 );
		BigDecimal liabPerPolicy = new BigDecimal( 0.00 );

		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		List<SectionVO> sectionVOList = null;
		WCVO workersCompensation = null;

		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		SectionVO sectionVO = null;
		ArrayList<Fact> factList = null;
		LocationVO location = null;
		Iterator<SectionVO> sectionListItr = null;
		boolean wcSectionPresent = false;
		double commissionDiff = 0.0;
		BigDecimal employeelimit =new BigDecimal( 0.00 );
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			sectionVOList = policyVO.getRiskDetails();
			if( !Utils.isEmpty( sectionVOList ) ){
				sectionListItr = sectionVOList.iterator();

				while( sectionListItr.hasNext() ){
					sectionVO = (SectionVO) sectionListItr.next();
					if( !Utils.isEmpty( sectionVO ) && ( section == ( sectionVO.getSectionId() ).intValue() ) ){
						logger.info( "WC section present." );
						wcSectionPresent = true;
						break;
					}
				}

				if( wcSectionPresent ){
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();
					if( !Utils.isEmpty( sectionVO.getCommission() ) ) commissionDiff = getCommissionDifference( sectionVO.getCommission(), RulesConstants.WC_CLASS, policyVO );

					for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
						location = (LocationVO) riskGroupDetailsEntry.getKey();
						workersCompensation = (WCVO) riskGroupDetailsEntry.getValue();

						ruleSet = new RuleSet();
						riskId = retrieveRiskId( location );

						if( !Utils.isEmpty( workersCompensation ) ){
							
							factList = (ArrayList<Fact>) ruleSet.getFact();
							
							if( !location.getToSave() ){
								continue;
							}
							
						   if(!Utils.isEmpty( workersCompensation.getEmpTypeDetails() )){
							   List<EmpTypeDetailsVO> empTypeDetsList = workersCompensation.getEmpTypeDetails();
							 
							   /* Take the cumulative values of all contents and send then to rule engine to check referrals */
							   for( EmpTypeDetailsVO empTypeDetailsVO : empTypeDetsList ){
									
								   //The below code is commented to resolve the Oman issue, Oman does not have Liability limit.
									
								   /* if( Utils.isEmpty( empTypeDetailsVO.getEmpType() )|| Utils.isEmpty( empTypeDetailsVO.getNoOfEmp()) || Utils.isEmpty( empTypeDetailsVO.getLimit() ) ){
										continue;
									}
										noOfEmp += empTypeDetailsVO.getNoOfEmp();
										liabPerLoc = liabPerLoc.add( empTypeDetailsVO.getLimit() );									
								   */
								   
								  //The below code is the replacement for above commented code.
								   
								   if( !Utils.isEmpty( empTypeDetailsVO.getEmpType() )){
									   
									   noOfEmp += (Utils.isEmpty( empTypeDetailsVO.getNoOfEmp()) || empTypeDetailsVO.getNoOfEmp() < 0)?SvcConstants.zeroVal : empTypeDetailsVO.getNoOfEmp();
									   /*
									    *  Removing limit code summing, as it was summing the code. for 2 employee types it was not getting any code for 3M,4M,5M,7.5M,10M
									    *  So to rule engine it was going code only not limit. 
									    *  If we added the limit for all summing code then it will start appearing in the B2B screen and for web service also it will accept these values.
									    *  
									    */
									   liabPerLoc = Utils.isEmpty(  empTypeDetailsVO.getLimit() )? liabPerLoc.add( BigDecimal.valueOf(SvcConstants.zeroVal )): empTypeDetailsVO.getLimit();
									   
									   /*
									    * Added for JLT Web Service if the limit is more 10M then to capture the limit and send it to 
									    */
									   employeelimit = Utils.isEmpty(  empTypeDetailsVO.getLiabilityLimit() )? liabPerLoc.add( BigDecimal.valueOf(SvcConstants.zeroVal )): new BigDecimal(  empTypeDetailsVO.getLiabilityLimit() ) ;
								   }
							   }
									if( !Utils.isEmpty( liabPerLoc ) ) liabPerLocCD = liabPerLoc.intValue();
		
									// Fetching the liabPerLoc based on the code passed in WCVO
									String schemeCode="";
									Date preparedDate=new Date();
									if(!Utils.isEmpty(policyVO.getScheme())){
										schemeCode = policyVO.getScheme().getSchemeCode().toString();
									}
									if(!Utils.isEmpty(policyVO.getCreated())){
										preparedDate = policyVO.getCreated();
									}
									SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_HYPHEN);
									String d2 = Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_LIVEDATE);
									Date JLTLiveDate=null;
									try {
										JLTLiveDate = s2.parse(d2);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//int userId = Integer.parseInt(Utils.getSingleValueAppConfig("JLT_USER_ID"));
									int quoteCreatedBy = 0;
									if(!Utils.isEmpty(policyVO.getCreatedBy())) {
										quoteCreatedBy = Integer.parseInt(policyVO.getCreatedBy());
									}
									/*
									 * Adding code change to get JLT web service limit for referral case
									 */
									boolean isValidSceheme= SvcUtils.isValidSchemeCode(policyVO);
									boolean isValidUser= SvcUtils.isValidUser(policyVO);
									if (isValidSceheme
											&& JLTLiveDate.compareTo(preparedDate) <= 0 && SvcConstants.DUBAI == Integer.parseInt(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )
											&& isValidUser) {
										
										if(liabPerLocCD==8) {
											liabPerLocStr = employeelimit.toString();
										}
										else {
											liabPerLocStr = SvcUtils.getLookUpDescription( "JLT_WC_LIM", policyVO.getScheme().getTariffCode().toString(), RulesConstants.LOOKUP_LEVEL2, liabPerLocCD );
										}
									}
									else {
										liabPerLocStr = SvcUtils.getLookUpDescription( "WC_LIMIT", policyVO.getScheme().getTariffCode().toString(), RulesConstants.LOOKUP_LEVEL2, liabPerLocCD );
									}
									
									//liabPerLocStr = SvcUtils.getLookUpDescription( RulesConstants.WC_PL_LIMIT, RulesConstants.LOOKUP_LEVEL1, RulesConstants.LOOKUP_LEVEL2, liabPerLocCD );
									
		
									if( !Utils.isEmpty( liabPerLocStr, true ) ){
										liabPerLoc = new BigDecimal( liabPerLocStr );
										liabPerPolicy = liabPerPolicy.add( liabPerLoc );
									}
		
									ruleSet.setRuleSetName( RulesConstants.WORK_COMP );
									ruleSet.setRiskId( riskId );
		
									Fact userFactor = new Fact();
									userFactor.setFactName( RulesConstants.FACT_USER );
									ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();
		
									RuleAttribute roleAttribute = new RuleAttribute();
									roleAttribute.setAttributeName( "role" );
									roleAttribute.setAttributeValue( roleType );
									userRuleAttributeList.add( roleAttribute );
		
									Fact wcFactor = new Fact();
									wcFactor.setFactName( RulesConstants.FACT_WC );
									ArrayList<RuleAttribute> wcRuleAttributeList = (ArrayList<RuleAttribute>) wcFactor.getRuleSetAttribute();
		
									RuleAttribute liabPerLocAttribute = new RuleAttribute();
									liabPerLocAttribute.setAttributeName( "liabilityPerLocation" );
									liabPerLocAttribute.setAttributeValue( new BigDecimal( decForm.format( liabPerLoc ) ).toString() );
									wcRuleAttributeList.add( liabPerLocAttribute );
		
									RuleAttribute noOfEmployeeAttribute = new RuleAttribute();
									noOfEmployeeAttribute.setAttributeName( "numberOfEmployee" );
									//noOfEmployeeAttribute.setAttributeValue( new Integer( noOfEmp ).toString() );
									noOfEmployeeAttribute.setAttributeValue( Integer.valueOf( noOfEmp ).toString() );
									wcRuleAttributeList.add( noOfEmployeeAttribute );
		
									RuleAttribute empTypeAttribute = new RuleAttribute();
									empTypeAttribute.setAttributeName( "empTypeBlacklisted" );
									empTypeAttribute.setAttributeValue( empTypeBlackListed );
									wcRuleAttributeList.add( empTypeAttribute );
									factList.add( userFactor );
									factList.add( wcFactor );	
							   
							   
						   }
						   ruleSetList.add( ruleSet );
						// Added as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
			                	Fact commonFactor = new Fact();
								commonFactor.setFactName( RulesConstants.FACT_COMMON );
								ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

								RuleAttribute commissionAttribute = new RuleAttribute();
								commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
								commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
								commonRuleAttributeList.add( commissionAttribute );
								
								factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
								
								
								
								Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
								while( ruleSetItr.hasNext() ){
									RuleSet ruleSet1 = ruleSetItr.next();
									if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.WORK_COMP.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){
										ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1.getFact();

										Iterator<Fact> factListItr = factList1.iterator();
										while( factListItr.hasNext() ){
											Fact fact1 = factListItr.next();

											if( RulesConstants.FACT_WC.equalsIgnoreCase( fact1.getFactName() ) ){
												ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();

												RuleAttribute liabPerPolicyAttribute = new RuleAttribute();
												liabPerPolicyAttribute.setAttributeName( "liabilityPerPolicy" );
												liabPerPolicyAttribute.setAttributeValue( new BigDecimal( decForm.format( liabPerPolicy ) ).toString() );
												ruleAttributeList1.add( liabPerPolicyAttribute );
											}
										}
									}
								}
								
			                }
							
							
						}
					}
				}
			}
		}

		return ruleInfo;
	}

	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForGeneral( BaseVO generalBaseVO, BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		GeneralInfoVO generalVO = (GeneralInfoVO) generalBaseVO;
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		InsuredVO insuredVO = null;
		ClaimsSummaryVO claimsSummaryVO = null;
		SchemeVO schemeVO = null;
		AuthenticationInfoVO authInfoVO = null;
		Date effDate = null;
		//Date accountingDate = null;
		long effectiveDateBackdating = 0;
		long effectiveDatePostdating = 0;
		long renEffectiveDatePostdating = 0;
		long issueDateBackdating = 0;
		long issueDatePostdating = 0;
		/*Added for Endorsement Backdating and postdating */
		Date endEffectiveDate = null;
		long endorsementDateBackdating = 0;
		long endorsementDatePostdating = 0;
		
		/** Setting dummy values of city and nationality to 99999.
		 * This is just to set the initial behavior of not to trigger referrals from Rules Engine.
		 */
		int city = 99999;
		int nationality = 99999;
		int noOfEmployees = 999;
		double maxSumInsredPerLoc = 0.0;
		BigDecimal lossExpQuantum = null;
		ArrayList<Fact> factList = null;
		/** Setting up the default value to N */
		String lossExperienceQuantum = "N";
		String renewalQuoteExist = "N";
		
		
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );

		Date today = cal.getTime();

		DataHolderVO<Boolean> renQuoteExists = null;
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}
		if(!policyVO.getIsQuote())
		{
			renQuoteExists =  (DataHolderVO<Boolean>) TaskExecutor.executeTasks("AMEND_POLICY_IS_RENEWAL_QUOTE_EXISTS",policyVO);
		}
		if(!Utils.isEmpty( renQuoteExists )   && renQuoteExists.getData().booleanValue())
		{
			 renewalQuoteExist = "Y";
		}
         
		if( !Utils.isEmpty( generalVO ) )
		{
			insuredVO = generalVO.getInsured();
			claimsSummaryVO = generalVO.getClaimsHistory();

			if( !Utils.isEmpty( insuredVO ) )
			{
				if( !Utils.isEmpty( insuredVO.getCity() ) ) city = insuredVO.getCity();
				if( !Utils.isEmpty( insuredVO.getNationality() ) ) nationality = insuredVO.getNationality();
				if( !Utils.isEmpty( insuredVO.getNoOfEmployees() ) ) noOfEmployees = insuredVO.getNoOfEmployees();
				if( !Utils.isEmpty( insuredVO.getTurnover() ) ) maxSumInsredPerLoc = insuredVO.getTurnover();
			
			}
			if( !Utils.isEmpty( claimsSummaryVO ) )
			{
				lossExpQuantum = claimsSummaryVO.getLossExpQuantum();
				logger.debug( "claimsSummaryVO.getLossExpQuantum():  " + lossExpQuantum );
				if( !Utils.isEmpty( lossExpQuantum ) && lossExpQuantum.doubleValue() > 0 ) lossExperienceQuantum = "Y";
				
				// Added condition for JLT broker

				if (SvcConstants.DUBAI == Integer
						.parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) ) {
					String schemeCode = "";
					Date preparedDate = new Date();
					Date modifiedDate = new Date();
					if (!Utils.isEmpty(policyVO.getScheme())) {
						schemeCode = policyVO.getScheme().getSchemeCode().toString();
					}
					if (!Utils.isEmpty(policyVO.getCreated())) {
						preparedDate = policyVO.getCreated();
					}
				
					   Date date = new Date();
						SimpleDateFormat s3 = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_HYPHEN);

					    String strDateFormat = com.Constant.CONST_DATE_FORMAT_HYPHEN;
					    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
					     try {
							modifiedDate= s3.parse(dateFormat.format(date));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
	
					SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_HYPHEN);
					String d2 = Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_LIVEDATE);
					Date JLTLiveDate = null;
					try {
						JLTLiveDate = s2.parse(d2);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					boolean isValidSceheme= SvcUtils.isValidSchemeCode(policyVO);
					if (isValidSceheme) {
						if(policyVO.getIsQuote() && (JLTLiveDate.compareTo(preparedDate) <= 0 || JLTLiveDate.compareTo(modifiedDate) <= 0)) {
							lossExperienceQuantum = null;
							lossExperienceQuantum = "N";
						}else {
							if(JLTLiveDate.compareTo(preparedDate) <= 0) {
								lossExperienceQuantum = null;
								lossExperienceQuantum = "N";
							}
						}
						
						
					}
				}
			}
		}
				
				
		

		if( !Utils.isEmpty( policyVO ) )
		{
			authInfoVO = policyVO.getAuthInfoVO();
			schemeVO = policyVO.getScheme();
			if( !Utils.isEmpty( schemeVO ) )
			{
				effDate = schemeVO.getEffDate();
			}
			/*if( !Utils.isEmpty( authInfoVO ) )
			{
				accountingDate = authInfoVO.getAccountingDate();
			}*/
			if( !Utils.isEmpty( effDate ) && policyVO.getIsQuote())
			{
				if( today.after( effDate ) )
					effectiveDateBackdating = getDateDifference(today, effDate);
				else
					effectiveDatePostdating = getDateDifference(effDate, today);
			}
			else
			{
				effectiveDatePostdating = 0;
			}
			
			issueDatePostdating = 0;
			issueDateBackdating = 0;
			/*Added for Endorsement Backdating and postdating */
			endEffectiveDate = policyVO.getEndEffectiveDate();
			
			if( !Utils.isEmpty( endEffectiveDate ) ){
				if( today.after( endEffectiveDate ) )
					endorsementDateBackdating = getDateDifference(today, endEffectiveDate); 
				else
					endorsementDatePostdating = getDateDifference(endEffectiveDate, today);  
			}
		}


		ArrayList<RuleSet> ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
		RuleSet ruleSet = null;

		ruleSet = new RuleSet();
		ruleSet.setRuleSetName( RulesConstants.GENERAL );
		/** There is no Risk Id in General Info section in PAS and 
		 * there will never be multiple locations for General Info section.
		 * Setting a dummy id as risk id is a mandatory field in Rules Engine
		 * for response mapping. 
		 */
		ruleSet.setRiskId( Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_GENERAL ) );

		factList = (ArrayList<Fact>) ruleSet.getFact();

		Fact userFactor = new Fact();
		userFactor.setFactName( RulesConstants.FACT_USER );
		ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

		RuleAttribute roleAttribute = new RuleAttribute();
		roleAttribute.setAttributeName( "role" );
		roleAttribute.setAttributeValue( roleType );
		userRuleAttributeList.add( roleAttribute );

		Fact generalFactor = new Fact();
		generalFactor.setFactName( RulesConstants.FACT_GENERAL );
		ArrayList<RuleAttribute> generalRuleAttributeList = (ArrayList<RuleAttribute>) generalFactor.getRuleSetAttribute();

		RuleAttribute effectiveDateBackdatingAttribute = new RuleAttribute();
		effectiveDateBackdatingAttribute.setAttributeName( "effectiveDateBackdating" );
		effectiveDateBackdatingAttribute.setAttributeValue( String.valueOf( effectiveDateBackdating ) );
		generalRuleAttributeList.add( effectiveDateBackdatingAttribute );

		if(!Utils.isEmpty(authInfoVO) && RulesConstants.REN_TRAN_TYPE.equals(authInfoVO.getTxnType()))
		{
			renEffectiveDatePostdating = effectiveDatePostdating;
			effectiveDatePostdating = 0;
		}
		RuleAttribute renewalEffectiveDatePostdatingAttribute = new RuleAttribute();
		renewalEffectiveDatePostdatingAttribute.setAttributeName( "renEffectiveDatePostdating" );
		renewalEffectiveDatePostdatingAttribute.setAttributeValue( String.valueOf( renEffectiveDatePostdating ) );	
		generalRuleAttributeList.add( renewalEffectiveDatePostdatingAttribute );

		
		RuleAttribute effectiveDatePostdatingAttribute = new RuleAttribute();
		effectiveDatePostdatingAttribute.setAttributeName( "effectiveDatePostdating" );
		effectiveDatePostdatingAttribute.setAttributeValue( String.valueOf( effectiveDatePostdating ) );
		generalRuleAttributeList.add( effectiveDatePostdatingAttribute );
		
		if( !Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( "30" ) ){	//Hanamappa's change
		RuleAttribute businessTypeAttribute = new RuleAttribute();
		businessTypeAttribute.setAttributeName( "businessType" );
		businessTypeAttribute.setAttributeValue( String.valueOf( policyVO.getGeneralInfo().getInsured().getBusType() ) );
		generalRuleAttributeList.add( businessTypeAttribute );
		
		RuleAttribute businessDescAttribute = new RuleAttribute();
		businessDescAttribute.setAttributeName( "businessDesc" );
		businessDescAttribute.setAttributeValue( String.valueOf( policyVO.getGeneralInfo().getInsured().getBusDescription() ) );
		generalRuleAttributeList.add( businessDescAttribute );
	}

		// Added as a part of ticket id 149213
		if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
			RuleAttribute issueDateBackdatingAttribute = new RuleAttribute();
			issueDateBackdatingAttribute.setAttributeName( "issueDateBackdating" );
			issueDateBackdatingAttribute.setAttributeValue( String.valueOf( issueDateBackdating ) );
			generalRuleAttributeList.add( issueDateBackdatingAttribute );

			RuleAttribute issueDatePostdatingAttribute = new RuleAttribute();
			issueDatePostdatingAttribute.setAttributeName( "issueDatePostdating" );
			issueDatePostdatingAttribute.setAttributeValue( String.valueOf( issueDatePostdating ) );
			generalRuleAttributeList.add( issueDatePostdatingAttribute );

			RuleAttribute cityAttribute = new RuleAttribute();
			cityAttribute.setAttributeName( "city" );
			//cityAttribute.setAttributeValue( new Integer( city ).toString() );
			cityAttribute.setAttributeValue( Integer.valueOf( city ).toString() );
			generalRuleAttributeList.add( cityAttribute );

			RuleAttribute nationalityAttribute = new RuleAttribute();
			nationalityAttribute.setAttributeName( "nationality" );
			nationalityAttribute.setAttributeValue( Integer.valueOf( nationality ).toString() );
			generalRuleAttributeList.add( nationalityAttribute );
		}

		RuleAttribute lossExperienceQuantumAttribute = new RuleAttribute();
		lossExperienceQuantumAttribute.setAttributeName( "lossExperienceQuantum" );
		lossExperienceQuantumAttribute.setAttributeValue( lossExperienceQuantum );
		generalRuleAttributeList.add( lossExperienceQuantumAttribute );
		
		RuleAttribute endDateBackdatingAttribute = new RuleAttribute();
		endDateBackdatingAttribute.setAttributeName( com.Constant.CONST_ENDORSEMENTDATEBACKDATING );
		endDateBackdatingAttribute.setAttributeValue( String.valueOf( endorsementDateBackdating ) );
		generalRuleAttributeList.add( endDateBackdatingAttribute );

		RuleAttribute endDatePostdatingAttribute = new RuleAttribute();
		endDatePostdatingAttribute.setAttributeName( com.Constant.CONST_ENDORSEMENTDATEPOSTDATING );
		endDatePostdatingAttribute.setAttributeValue( String.valueOf( endorsementDatePostdating ) );
		generalRuleAttributeList.add( endDatePostdatingAttribute );
		
		RuleAttribute renewalQuoteAlreadyCreated = new RuleAttribute();
		renewalQuoteAlreadyCreated.setAttributeName( "renewalQuoteAlreadyCreated" );
		renewalQuoteAlreadyCreated.setAttributeValue(renewalQuoteExist);
		generalRuleAttributeList.add( renewalQuoteAlreadyCreated );
		
		RuleAttribute noOfEmployeesAttribute = new RuleAttribute();
		noOfEmployeesAttribute.setAttributeName( "noOfEmployees" );
		noOfEmployeesAttribute.setAttributeValue(String.valueOf(noOfEmployees));
		generalRuleAttributeList.add( noOfEmployeesAttribute );

		RuleAttribute maxSumInsredPerLocAttribute = new RuleAttribute();
		maxSumInsredPerLocAttribute.setAttributeName( com.Constant.CONST_SUMINSUREDPERLOCATION );
		maxSumInsredPerLocAttribute.setAttributeValue( new BigDecimal( decForm.format( maxSumInsredPerLoc ) ).toString() );
		generalRuleAttributeList.add( maxSumInsredPerLocAttribute );
		
		
		
		Fact issueQuoteFactor = new Fact();
        issueQuoteFactor.setFactName( RulesConstants.FACT_CLAIMS_ENDORSEMENT );
        ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) issueQuoteFactor.getRuleSetAttribute();

        RuleAttribute issueQuoteAttr = new RuleAttribute();
        issueQuoteAttr.setAttributeName( "claimsExist" );
        issueQuoteAttr.setAttributeValue( ( policyVO.isClaimsHistoryExistInMissippi())? "Y" : "N" );
        rctRuleAttributeList.add( issueQuoteAttr );
        
        
		factList.add( userFactor );
		factList.add( generalFactor );
		factList.add( issueQuoteFactor );
		ruleSetList.add( ruleSet );

		return ruleInfo;
	}

	private long getDateDifference(Date latestDate, Date previousDate) {
		return (( latestDate.getTime() - previousDate.getTime() ) / DAYDIVIDER );
	}
	/**
	 * 
	 * @param policyVO
	 * @param section
	 * @param ruleInfo
	 * @return
	 */
	private String retrieveRiskId( RiskGroup location ){
		String riskId = null;
		LocationVO locationVO = (LocationVO) location;
		if( !Utils.isEmpty( locationVO ) ){
			riskId = locationVO.getRiskGroupId();
		}
		return riskId;
	}

	/**
	 * 
	 * @param commission
	 * @param classStr
	 * @param policyVO
	 * @return
	 */
	private double getCommissionDifference( double commission, String classStr, PolicyVO policyVO ){
		return ( commission - getConfiguredCommission( classStr, policyVO ) );
	}

	/**
	 * 
	 * @param classStr
	 * @param policyVO
	 * @return
	 */
	private double getConfiguredCommission( String classStr, PolicyVO policyVO ){
		Map<Integer, Double> classComMap = new HashMap<Integer, Double>();
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
		CommissionVOList commissionVOList = (CommissionVOList) commonOpSvc.invokeMethod( "getCommisionSvc", policyVO );

		java.util.List<CommissionVO> commisionList = commissionVOList.getCommision();
		for( CommissionVO commission : commisionList ){
			classComMap.put( commission.getClassCode(), Double.valueOf( commission.getComPrec() ) );
		}

		Integer classCode = Integer.valueOf( Utils.getSingleValueAppConfig( classStr ) );
		return classComMap.get( classCode );
	}

	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForEndorsement( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		String nilEndorsement = "N";
		String extraEndorsement = "N";
		String refundEndorsement = "N";
		double extraPremium = 0.0;
		double refundPremium = 0.0;
		Long polLinkingId;
		Long newEndtId;
		double newPremiumAmt = 0.0;
		double prevPremiumAmt = 0.0;
		Double prevPremAmountObj = null;
		Date endEffectiveDate = null;
		long endorsementDateBackdating = 0;
		long endorsementDatePostdating = 0;

		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		/** Initializing default risk id for endorsement flow as it is independent of any particular location */
		String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_ENDORSEMENT );

		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			polLinkingId = policyVO.getPolLinkingId();
			newEndtId = policyVO.getNewEndtId();
			endEffectiveDate = policyVO.getEndEffectiveDate();

			/** Check if newEndtId is present in PolicyVO. This will be present only when User has saved the first page */
			if( !Utils.isEmpty( newEndtId ) ){
				PremiumVO premiumVO = policyVO.getPremiumVO();
				if( !Utils.isEmpty( premiumVO ) ){
					newPremiumAmt = premiumVO.getPremiumAmt();
				}

				GetPreviousPremiumFunc getPrevPrem = (GetPreviousPremiumFunc) Utils.getBean( "getPreviousPrem" );
				prevPremAmountObj = getPrevPrem.getPreviousPremium( polLinkingId, newEndtId );
				prevPremiumAmt = Utils.isEmpty( prevPremAmountObj ) ? prevPremiumAmt : prevPremAmountObj;
				logger.debug( "polLinkingId: " + polLinkingId + " newEndtId: " + newEndtId + " prevPremiumAmt: " + prevPremiumAmt );

				if( newPremiumAmt == prevPremiumAmt ){
					nilEndorsement = "Y";
				}
				else if( newPremiumAmt > prevPremiumAmt ){
					extraEndorsement = "Y";
					extraPremium = newPremiumAmt - prevPremiumAmt;
				}
				else{
					refundEndorsement = "Y";
					refundPremium = prevPremiumAmt - newPremiumAmt;
				}
			}
			else{
				Calendar cal = Calendar.getInstance();
				cal.set( Calendar.HOUR_OF_DAY, 0 );
				cal.set( Calendar.MINUTE, 0 );
				cal.set( Calendar.SECOND, 0 );
				cal.set( Calendar.MILLISECOND, 0 );

				Date today = cal.getTime();

				if( !Utils.isEmpty( endEffectiveDate ) ){
					if( today.after( endEffectiveDate ) )
						endorsementDateBackdating = getDateDifference(today, endEffectiveDate);
					else
						endorsementDatePostdating = getDateDifference(endEffectiveDate, today); 
				}
			}

			ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
			ruleSet = new RuleSet();
			ruleSet.setRuleSetName( RulesConstants.ENDORSEMENT );
			ruleSet.setRiskId( riskId );

			factList = (ArrayList<Fact>) ruleSet.getFact();

			Fact userFactor = new Fact();
			userFactor.setFactName( RulesConstants.FACT_USER );
			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

			RuleAttribute roleAttribute = new RuleAttribute();
			roleAttribute.setAttributeName( "role" );
			roleAttribute.setAttributeValue( roleType );
			userRuleAttributeList.add( roleAttribute );

			Fact endFactor = new Fact();
			endFactor.setFactName( RulesConstants.FACT_ENDORSEMENT );
			ArrayList<RuleAttribute> endRuleAttributeList = (ArrayList<RuleAttribute>) endFactor.getRuleSetAttribute();

			RuleAttribute nilEndAttribute = new RuleAttribute();
			nilEndAttribute.setAttributeName( "nilEndorsement" );
			nilEndAttribute.setAttributeValue( nilEndorsement );
			endRuleAttributeList.add( nilEndAttribute );

			RuleAttribute extraEndAttribute = new RuleAttribute();
			extraEndAttribute.setAttributeName( "extraEndorsement" );
			extraEndAttribute.setAttributeValue( extraEndorsement );
			endRuleAttributeList.add( extraEndAttribute );

			RuleAttribute extraPremAttribute = new RuleAttribute();
			extraPremAttribute.setAttributeName( "extraPremium" );
			extraPremAttribute.setAttributeValue( new BigDecimal( decForm.format( extraPremium ) ).toString() );
			endRuleAttributeList.add( extraPremAttribute );

			RuleAttribute refundEndAttribute = new RuleAttribute();
			refundEndAttribute.setAttributeName( "refundEndorsement" );
			refundEndAttribute.setAttributeValue( refundEndorsement );
			endRuleAttributeList.add( refundEndAttribute );

			RuleAttribute refundPremAttribute = new RuleAttribute();
			refundPremAttribute.setAttributeName( "refundPremium" );
			refundPremAttribute.setAttributeValue( new BigDecimal( decForm.format( refundPremium ) ).toString() );
			endRuleAttributeList.add( refundPremAttribute );

			RuleAttribute endDateBackdatingAttribute = new RuleAttribute();
			endDateBackdatingAttribute.setAttributeName( com.Constant.CONST_ENDORSEMENTDATEBACKDATING );
			endDateBackdatingAttribute.setAttributeValue( String.valueOf( endorsementDateBackdating ) );
			endRuleAttributeList.add( endDateBackdatingAttribute );

			RuleAttribute endDatePostdatingAttribute = new RuleAttribute();
			endDatePostdatingAttribute.setAttributeName( com.Constant.CONST_ENDORSEMENTDATEPOSTDATING );
			endDatePostdatingAttribute.setAttributeValue( String.valueOf( endorsementDatePostdating ) );
			endRuleAttributeList.add( endDatePostdatingAttribute );

			factList.add( userFactor );
			factList.add( endFactor );
			ruleSetList.add( ruleSet );
		}
		return ruleInfo;
	}

	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForReceipt( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		double newPremium = 0.0;
		double prevPremium = 0.0;
		boolean isPaymentDone;
		Long polLinkingId;
		Long newEndtId;
		Double prevPremiumObj = null;
		double creditPremium = 0.0;

		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		/** Initializing default risk id for receipt flow as it is independent of any particular location */
		String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_RECEIPT );

		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			PaymentVO paymentVO = policyVO.getPaymentVO();
			polLinkingId = policyVO.getPolLinkingId();
			newEndtId = policyVO.getNewEndtId();

			if( !Utils.isEmpty( paymentVO ) ){
				newPremium = paymentVO.getPremium();
				isPaymentDone = paymentVO.isPaymentDone();

				/** Rule to be triggered only when payment is not done */
				if( !isPaymentDone ){
					/** Check if newEndtId is present in PaymentVO. This will be present only in case of endorsement */
					if( !Utils.isEmpty( newEndtId ) && !Utils.isEmpty( polLinkingId ) ){
						GetPreviousPremiumFunc getPrevPrem = (GetPreviousPremiumFunc) Utils.getBean( "getPreviousPrem" );
						prevPremiumObj = getPrevPrem.getPreviousPremium( polLinkingId, newEndtId );
						prevPremium = Utils.isEmpty( prevPremiumObj ) ? prevPremium : prevPremiumObj;
						logger.debug( "polLinkingId: " + polLinkingId + " newEndtId: " + newEndtId + " prevPremium: " + prevPremium );
					}

					creditPremium = newPremium - prevPremium;
				}

				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				ruleSet = new RuleSet();
				ruleSet.setRuleSetName( RulesConstants.RECEIPT );
				ruleSet.setRiskId( riskId );

				factList = (ArrayList<Fact>) ruleSet.getFact();

				Fact userFactor = new Fact();
				userFactor.setFactName( RulesConstants.FACT_USER );
				ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

				RuleAttribute roleAttribute = new RuleAttribute();
				roleAttribute.setAttributeName( "role" );
				roleAttribute.setAttributeValue( roleType );
				userRuleAttributeList.add( roleAttribute );

				Fact rctFactor = new Fact();
				rctFactor.setFactName( RulesConstants.FACT_RECEIPT );
				ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) rctFactor.getRuleSetAttribute();

				RuleAttribute creditModeAttribute = new RuleAttribute();
				creditModeAttribute.setAttributeName( "creditMode" );
				creditModeAttribute.setAttributeValue( new BigDecimal( decForm.format( creditPremium ) ).toString() );
				rctRuleAttributeList.add( creditModeAttribute );

				factList.add( userFactor );
				factList.add( rctFactor );
				ruleSetList.add( ruleSet );
			}
		}
		return ruleInfo;
	}
	
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForPremiumPage( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		//double lossRatio = 0.0;
		double payablePremium =0.0;
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		/** There is no Risk Id in Premium Page Info section in PAS and 
		 * there will never be multiple locations for General Info section.
		 * Setting a dummy id as risk id is a mandatory field in Rules Engine
		 * for response mapping. 
		 */
		String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_PREMIUMPAGE );
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			//PremiumVO premiumVO = policyVO.getPremiumVO();
			
			/*if(!Utils.isEmpty( premiumVO ) ){
				lossRatio = premiumVO.getLossRatio();
			}*/
						
			List<EndorsmentVO> endorsmentVOs = policyVO.getEndorsements();
			EndorsmentVO endorsmentVO = null;
			if(!Utils.isEmpty( endorsmentVOs ) && endorsmentVOs.size() > 0 ){
				endorsmentVO = endorsmentVOs.get( 0 );
			}			
			try{
			    payablePremium = calculatePayablePremium(endorsmentVO);
			}catch (NullPointerException e) {
				// TODO: handle exception
				logger.debug("Null pointer exception while calculating premium");
			}
			
			Calendar cal = Calendar.getInstance();
			cal.set( Calendar.HOUR_OF_DAY, 0 );
			cal.set( Calendar.MINUTE, 0 );
			cal.set( Calendar.SECOND, 0 );
			cal.set( Calendar.MILLISECOND, 0 );

			Date today = cal.getTime();
			Date endEffectiveDate = policyVO.getEndEffectiveDate();
			long endorsementDateBackdating = 0;
			long endorsementDatePostdating = 0;
			
			if( !Utils.isEmpty( endEffectiveDate ) ){
				if( today.after( endEffectiveDate ) )
					endorsementDateBackdating = getDateDifference(today, endEffectiveDate);
				else
					endorsementDatePostdating = getDateDifference(endEffectiveDate, today); 
			}
			
		
			ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
			ruleSet = new RuleSet();
			ruleSet.setRuleSetName( RulesConstants.PREMIUM_PAGE );
			ruleSet.setRiskId( riskId );

			factList = (ArrayList<Fact>) ruleSet.getFact();

			Fact userFactor = new Fact();
			userFactor.setFactName( RulesConstants.FACT_USER );
			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

			RuleAttribute roleAttribute = new RuleAttribute();
			roleAttribute.setAttributeName( "role" );
			roleAttribute.setAttributeValue( roleType );
			userRuleAttributeList.add( roleAttribute );			
			
			EndorsmentVO endorsementVo = (policyVO.getEndorsements()!=null) ? policyVO.getEndorsements().get(0):null;
			
			
			if(endorsementVo!=null && (!endorsementVo.isPolicyToBeCancelled())){
				Fact endorsementDatingFact = new Fact();
				endorsementDatingFact.setFactName( RulesConstants.FACT_ENDORSEMENT_DATING );
				ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) endorsementDatingFact.getRuleSetAttribute();
	
				RuleAttribute endDateBackdatingAttribute = new RuleAttribute();
				endDateBackdatingAttribute.setAttributeName( com.Constant.CONST_ENDORSEMENTDATEBACKDATING );
				endDateBackdatingAttribute.setAttributeValue( String.valueOf( endorsementDateBackdating ) );
				rctRuleAttributeList.add( endDateBackdatingAttribute );
		
				RuleAttribute endDatePostdatingAttribute = new RuleAttribute();
				endDatePostdatingAttribute.setAttributeName( com.Constant.CONST_ENDORSEMENTDATEPOSTDATING );
				endDatePostdatingAttribute.setAttributeValue( String.valueOf( endorsementDatePostdating ) );
				rctRuleAttributeList.add( endDatePostdatingAttribute );		
				
				factList.add( endorsementDatingFact );				
			}else{
				Fact cancellationDatingFact = new Fact();
				cancellationDatingFact.setFactName( RulesConstants.FACT_CANCELLATION_DATING );
				ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) cancellationDatingFact.getRuleSetAttribute();
	
				RuleAttribute cancellationDateBackdatingAttribute = new RuleAttribute();
				cancellationDateBackdatingAttribute.setAttributeName( "cancellationDateBackdating" );
				cancellationDateBackdatingAttribute.setAttributeValue( String.valueOf( endorsementDateBackdating ) );
				rctRuleAttributeList.add( cancellationDateBackdatingAttribute );
		
				RuleAttribute cancellationDatePostdatingAttribute = new RuleAttribute();
				cancellationDatePostdatingAttribute.setAttributeName( "cancellationDatePostdating" );
				cancellationDatePostdatingAttribute.setAttributeValue( String.valueOf( endorsementDatePostdating ) );
				rctRuleAttributeList.add( cancellationDatePostdatingAttribute );		
				
				factList.add( cancellationDatingFact );		
				
				Fact claimsExistForEndFactor = new Fact();
				claimsExistForEndFactor.setFactName( RulesConstants.FACT_CANCEL_ENDORSEMENT );
				ArrayList<RuleAttribute> claimsExistForEndFactorRuleAttributeList = (ArrayList<RuleAttribute>) claimsExistForEndFactor.getRuleSetAttribute();

				RuleAttribute claimsExistForEndFactorAttribute = new RuleAttribute();
				claimsExistForEndFactorAttribute.setAttributeName( "claimsExistForCancelEnd" );
				claimsExistForEndFactorAttribute.setAttributeValue(  ( policyVO.isClaimsHistoryExistInMissippi())? "Y" : "N"  );
				claimsExistForEndFactorRuleAttributeList.add( claimsExistForEndFactorAttribute );
				factList.add( claimsExistForEndFactor ); 
			}
			
			if(payablePremium > 0) {			
				Fact extraEndorsementFact = new Fact();
				extraEndorsementFact.setFactName( RulesConstants.FACT_EXTRA );
				ArrayList<RuleAttribute> extraFactRuleAttributeList = (ArrayList<RuleAttribute>) extraEndorsementFact.getRuleSetAttribute();
				
				RuleAttribute extraEndorsement = new RuleAttribute();
				extraEndorsement.setAttributeName( "extraEndorsement" );
				extraEndorsement.setAttributeValue( new BigDecimal( decForm.format( payablePremium ) ).toString());
				extraFactRuleAttributeList.add( extraEndorsement );	
				
				RuleAttribute extraPremium = new RuleAttribute();
				extraPremium.setAttributeName( "extraPremium" );
				extraPremium.setAttributeValue( new BigDecimal( decForm.format( payablePremium ) ).toString());
				extraFactRuleAttributeList.add( extraPremium );	
				
				factList.add( extraEndorsementFact );
			}else if (payablePremium == 0){
				Fact nilEndorsementFact = new Fact();
				nilEndorsementFact.setFactName( RulesConstants.FACT_NIL );
				ArrayList<RuleAttribute> nilFactRuleAttributeList = (ArrayList<RuleAttribute>) nilEndorsementFact.getRuleSetAttribute();
				
				RuleAttribute nilEndorsement = new RuleAttribute();
				nilEndorsement.setAttributeName( "nilEndorsement" );
				nilEndorsement.setAttributeValue( new BigDecimal( decForm.format( payablePremium ) ).toString());
				nilFactRuleAttributeList.add( nilEndorsement );	
				
				factList.add( nilEndorsementFact );
			}else {
				Fact refundEndorsementFact = new Fact();
				refundEndorsementFact.setFactName( RulesConstants.FACT_REFUND );
				ArrayList<RuleAttribute> refundFactRuleAttributeList = (ArrayList<RuleAttribute>) refundEndorsementFact.getRuleSetAttribute();
				
				RuleAttribute refundEndorsement = new RuleAttribute();
				refundEndorsement.setAttributeName( "refundEndorsement" );
				refundEndorsement.setAttributeValue( new BigDecimal( decForm.format( payablePremium ) ).toString());
				refundFactRuleAttributeList.add( refundEndorsement );	
				
				RuleAttribute refundPremium = new RuleAttribute();
				refundPremium.setAttributeName( "refundPremium" );
				refundPremium.setAttributeValue( new BigDecimal( decForm.format( payablePremium ) ).toString());
				refundFactRuleAttributeList.add( refundPremium );	
				
				factList.add( refundEndorsementFact );
			}
				
			factList.add( userFactor ); 
			
			ruleSetList.add( ruleSet );
		}
		
		return ruleInfo;
	}
	
	private double calculatePayablePremium(EndorsmentVO endorsmentVO) {
		return endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt();
	}
	
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForDiscountLoading( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		/** There is no Risk Id in Premium Page Info section in PAS and 
		 * there will never be multiple locations for General Info section.
		 * Setting a dummy id as risk id is a mandatory field in Rules Engine
		 * for response mapping. 
		 */
		String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_DISC_LOAD );
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
			ruleSet = new RuleSet();
			ruleSet.setRuleSetName( RulesConstants.DISCOUNT_LOADING );
			ruleSet.setRiskId( riskId );

			factList = (ArrayList<Fact>) ruleSet.getFact();

			Fact userFactor = new Fact();
			userFactor.setFactName( RulesConstants.FACT_USER );
			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

			RuleAttribute roleAttribute = new RuleAttribute();
			roleAttribute.setAttributeName( "role" );
			roleAttribute.setAttributeValue( roleType );
			userRuleAttributeList.add( roleAttribute );

			Fact prempageFactor = new Fact();
			prempageFactor.setFactName( RulesConstants.FACT_DISCOUNTLOADING );
			ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) prempageFactor.getRuleSetAttribute();

			RuleAttribute dicLoading = new RuleAttribute();
			dicLoading.setAttributeName( "discountOrLoadPercentage" );
			
			Double nonNullDiscLoad = Utils.isEmpty( getDisLoadPer( policyBaseVO ) ) ? SvcConstants.zeroVal : getDisLoadPer( policyBaseVO );
			dicLoading.setAttributeValue( nonNullDiscLoad.toString() );

			rctRuleAttributeList.add( dicLoading );		
				
			factList.add( userFactor );
			factList.add( prempageFactor );
			ruleSetList.add( ruleSet );
		}
		
		return ruleInfo;
	}
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForEEQ( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		double sumInsuredPerLocation = 0.0;
		double sumInsuredPerPolicy = 0.0;
		double commissionDiff = 0.0;
		double minDeduc = 5000;
		double deductible = 0.0;
		
		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		LocationVO location = null;
		Iterator<SectionVO> sectionListItr = null;
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		EEVO eevo = null;
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			
			SectionVO eeqSection = getSection(SvcConstants.SECTION_ID_EE, policyVO);
			
				
			if( !Utils.isEmpty( eeqSection ) ){
				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				riskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) eeqSection.getRiskGroupDetails();
				
				if( !Utils.isEmpty( eeqSection.getCommission() ) ) commissionDiff = getCommissionDifference( eeqSection.getCommission(), RulesConstants.EE_CLASS, policyVO );
				
				for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
					location = (LocationVO) riskGroupDetailsEntry.getKey();
					eevo = (EEVO) riskGroupDetailsEntry.getValue();
					sumInsuredPerLocation = 0.0;
					//ruleSet = new RuleSet();
					riskId = retrieveRiskId( location );

					if( !Utils.isEmpty( eevo ) ){
						
						List<EquipmentVO> equipmentVOs = eevo.getEquipmentDtls();
						if(!Utils.isEmpty( equipmentVOs )) {
							Iterator<EquipmentVO> equipDetsIter =  equipmentVOs.iterator();
							
							while(equipDetsIter.hasNext()){
								EquipmentVO equipmentVO = (EquipmentVO) equipDetsIter.next();
								if(!Utils.isEmpty(equipmentVO) && Utils.isEmpty(equipmentVO.getIsToBeDeleted()))
								{
									SumInsuredVO sumInsuredVO = equipmentVO.getSumInsuredDetails();
									if(!Utils.isEmpty(sumInsuredVO) )
									{
										sumInsuredPerLocation += sumInsuredVO.getSumInsured();

										deductible = sumInsuredVO.getDeductible().doubleValue();
										//get min dedcutible
										if(minDeduc > deductible){
											minDeduc = deductible;
										}
									}
								}
							}
						}
						sumInsuredPerPolicy += sumInsuredPerLocation ;
					
					}
					if( !location.getToSave() ){
						continue;
					}
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					ruleSet = new RuleSet();
					ruleSet.setRuleSetName( RulesConstants.ELEC_EQUIP );
					ruleSet.setRiskId( riskId );

					factList = (ArrayList<Fact>) ruleSet.getFact();

					Fact userFactor = new Fact();
					userFactor.setFactName( RulesConstants.FACT_USER );
					ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

					RuleAttribute roleAttribute = new RuleAttribute();
					roleAttribute.setAttributeName( "role" );
					roleAttribute.setAttributeValue( roleType );
					userRuleAttributeList.add( roleAttribute );

					Fact eeqFactor = new Fact();
					eeqFactor.setFactName( RulesConstants.FACT_EEQ );
					ArrayList<RuleAttribute> eeQRuleAttributeList = (ArrayList<RuleAttribute>) eeqFactor.getRuleSetAttribute();
					
					// Added as a part of ticket id 149213
					if(! (BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) )){
						
	                	RuleAttribute sumInsuredperlocationEE = new RuleAttribute();
	                	sumInsuredperlocationEE.setAttributeName( "sumInsuredperlocationEE" );
	                	sumInsuredperlocationEE.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocation ) ).toString() ); // 149213 rule name change
						eeQRuleAttributeList.add( sumInsuredperlocationEE );
	                }
	                else{
	                	RuleAttribute sumInsuredAttribute = new RuleAttribute();
						sumInsuredAttribute.setAttributeName( com.Constant.CONST_SUMINSUREDPERLOCATION );
						sumInsuredAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocation ) ).toString() );
						eeQRuleAttributeList.add( sumInsuredAttribute );
	                }
					
					Fact commonFactor = new Fact();
					commonFactor.setFactName( RulesConstants.FACT_COMMON );
					ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

					//Removed as a part of ticket id 149213
					if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
						RuleAttribute commissionAttribute = new RuleAttribute();
						commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
						commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
						commonRuleAttributeList.add( commissionAttribute );
						
					}
					
					if( BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) ){
						
						String minimumDeductible = String.valueOf(new Double(minDeduc).intValue());
						RuleAttribute deductibleAttribute = new RuleAttribute();
						deductibleAttribute.setAttributeName( com.Constant.CONST_DEDUCTABLEMINVALUE );
						deductibleAttribute.setAttributeValue(minimumDeductible);
						eeQRuleAttributeList.add( deductibleAttribute );
					}

					factList.add( userFactor );
					factList.add( eeqFactor );
					/*
					 * Modified as a part of ticket id 149213
					 */
					if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
						factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
					}
					
				}
				ruleSetList.add( ruleSet );
				//Modified as a part of ticket id 149213
				if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
					Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
					while( ruleSetItr.hasNext() ){
						RuleSet ruleSet1 = ruleSetItr.next();
						if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.ELEC_EQUIP.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){
							ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1.getFact();

							Iterator<Fact> factListItr = factList1.iterator();
							while( factListItr.hasNext() ){
								Fact fact1 = factListItr.next();

								if( RulesConstants.FACT_EEQ.equalsIgnoreCase( fact1.getFactName() ) ){
									ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();

									RuleAttribute sumInsuredAttribute = new RuleAttribute();
									sumInsuredAttribute.setAttributeName( com.Constant.CONST_SUMINSUREDPERPOLICY );
									sumInsuredAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerPolicy ) ).toString() );
									ruleAttributeList1.add( sumInsuredAttribute );
								}
							}
						}
					}
				}else{
					Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
					while( ruleSetItr.hasNext() ){
						RuleSet ruleSet1 = ruleSetItr.next();
						if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.ELEC_EQUIP.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){
							ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1.getFact();

							Iterator<Fact> factListItr = factList1.iterator();
							while( factListItr.hasNext() ){
								Fact fact1 = factListItr.next();

								if( RulesConstants.FACT_EEQ.equalsIgnoreCase( fact1.getFactName() ) ){
									ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();

									RuleAttribute sumInsuredCheckEE = new RuleAttribute();
				                	sumInsuredCheckEE.setAttributeName( "sumInsuredCheckEE" );   
				                	sumInsuredCheckEE.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerPolicy ) ).toString() );  // 149213 new rule value need to set
				                	ruleAttributeList1.add( sumInsuredCheckEE );
								}
							}
						}
					}
				}
				
			}
		}
		
		return ruleInfo;
	}
	
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	@SuppressWarnings("unused")
	private RuleInfo createRuleInfoForMB( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		//SectionVO sectionVO = null;
		List<SectionVO> sectionVOList = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		LocationVO location = null;
		MBVO mbVO = null;
		
		//Iterator<SectionVO> sectionListItr = null;
		String riskId = null;
		
		double sumInsuredPerLocation = 0.0;
		double sumInsuredPerPolicy = 0.0;
		String isContentsNotInPAR = "N";
		String isMBSIGreaterThanPARContents = "N";
		double commissionDiff = 0.0;
		double minDeduc = 5000.00; // set a high deductible
		double deductible = 0.0;
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		/*Finder Section */
	
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			sectionVOList = policyVO.getRiskDetails();
			if( !Utils.isEmpty( sectionVOList ) ){
				//sectionListItr = sectionVOList.iterator();

				SectionVO mbSection = getSection(SvcConstants.SECTION_ID_MB, policyVO);
				

				if( !Utils.isEmpty( mbSection )){
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) mbSection.getRiskGroupDetails();
					if( !Utils.isEmpty( mbSection.getCommission() ) ) commissionDiff = getCommissionDifference( mbSection.getCommission(), RulesConstants.MB_CLASS, policyVO );

					for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() )
					{
						
						double stockStorage = 0;
						sumInsuredPerLocation = 0.0;
						location = (LocationVO) riskGroupDetailsEntry.getKey();
						mbVO = (MBVO) riskGroupDetailsEntry.getValue();

						riskId = retrieveRiskId( location );
						//ruleSet = new RuleSet();

						if( !Utils.isEmpty( mbVO ) ){
							
							List<MachineDetailsVO> machineDetailsVOs = mbVO.getMachineryDetails();
							if(!Utils.isEmpty( machineDetailsVOs )) {
								Iterator<MachineDetailsVO> machineDetsIter =  machineDetailsVOs.iterator();
								
								while(machineDetsIter.hasNext()){
									MachineDetailsVO machineDetailsVO = (MachineDetailsVO) machineDetsIter.next();
									if(!Utils.isEmpty(machineDetailsVO) && Utils.isEmpty(machineDetailsVO.getIsToBeDeleted()))
									{
										SumInsuredVO sumInsuredVO = machineDetailsVO.getSumInsuredVO();
										if(!Utils.isEmpty(sumInsuredVO) )
										{
											sumInsuredPerLocation += sumInsuredVO.getSumInsured();
											
											//get min dedcutible
											deductible = sumInsuredVO.getDeductible().doubleValue();
											if(minDeduc > deductible){
												minDeduc = deductible;
											}
										}
									}
								}
							}
							
							sumInsuredPerPolicy += sumInsuredPerLocation;
							
							//for contents not insured in PAR
							if(isParSectionPresentinPolicy(policyVO))
							{
								SectionVO parSec =  getSection(SvcConstants.SECTION_ID_PAR, policyVO);
								ParVO parDetails =  (ParVO) parSec.getRiskGroupDetails().get(location);
								double contentSI = 0.0;
								if(!Utils.isEmpty( parDetails))
								{
									if(!Utils.isEmpty( parDetails.getCovers() )
											&& parDetails.getCovers().getPropertyCoversDetails().size() > 0 )
									{
										for( PropertyRiskDetails parCover : parDetails.getCovers().getPropertyCoversDetails())
										{
											if(!Utils.isEmpty( parCover )  && !Utils.isEmpty( parCover.getCover() ) )
											{
												contentSI += parCover.getCover();
											}
										}
									}
									if(contentSI > 0)
									{
										isContentsNotInPAR = "N";
										/* changed sumInsuredPerPolicy to sumInsuredPerLocation - UAT feedback - Ticket Id 149213*/										
										if(sumInsuredPerLocation > contentSI)
										{
											isMBSIGreaterThanPARContents = "Y";
										}
									}
									else
									{
										isContentsNotInPAR = "Y";
									}

								}
							}
							else
							{
								isContentsNotInPAR = "Y";
								//isMBSIGreaterThanPARContents = "N";
							}
							if( !location.getToSave() ){
								continue;
							}
							ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
							ruleSet = new RuleSet();
							ruleSet.setRuleSetName( RulesConstants.MACHINE_BD );
							ruleSet.setRiskId( riskId );

							factList = (ArrayList<Fact>) ruleSet.getFact();

							Fact userFactor = new Fact();
							userFactor.setFactName( RulesConstants.FACT_USER );
							ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

							RuleAttribute roleAttribute = new RuleAttribute();
							roleAttribute.setAttributeName( "role" );
							roleAttribute.setAttributeValue( roleType );
							userRuleAttributeList.add( roleAttribute );

							Fact mbFactor = new Fact();
							mbFactor.setFactName( RulesConstants.FACT_MB );
							ArrayList<RuleAttribute> mbQRuleAttributeList = (ArrayList<RuleAttribute>) mbFactor.getRuleSetAttribute();

							//Modified as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
		                    	RuleAttribute sumInsuredAttribute = new RuleAttribute();
								sumInsuredAttribute.setAttributeName( com.Constant.CONST_SUMINSUREDPERLOCATION );
								sumInsuredAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocation ) ).toString() );
								mbQRuleAttributeList.add( sumInsuredAttribute );
		                    }
		                    else{
		                    	RuleAttribute sumInsuredperlocationMB = new RuleAttribute();
		                    	sumInsuredperlocationMB.setAttributeName( "sumInsuredperlocationMB" );
		                    	sumInsuredperlocationMB.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocation ) ).toString() );  // 149213 rule name change
								mbQRuleAttributeList.add( sumInsuredperlocationMB );
		                    }
							
							RuleAttribute parContentsAttribute = new RuleAttribute();
							parContentsAttribute.setAttributeName( "contentsNotInsuredInPar" );
							parContentsAttribute.setAttributeValue( isContentsNotInPAR );
							mbQRuleAttributeList.add( parContentsAttribute );
							
							RuleAttribute mbSIGreaterThanPARContents = new RuleAttribute();
							mbSIGreaterThanPARContents.setAttributeName( "mbSIGreaterThanPARContents" );
							mbSIGreaterThanPARContents.setAttributeValue( isMBSIGreaterThanPARContents );
							mbQRuleAttributeList.add( mbSIGreaterThanPARContents );
							
							//for deductible rule
							
							if(  BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) ){
								
								String minimumDeductible = String.valueOf(new Double(minDeduc).intValue());
								
								RuleAttribute deductibleAttribute  = new RuleAttribute();
								deductibleAttribute .setAttributeName( com.Constant.CONST_DEDUCTABLEMINVALUE );
								deductibleAttribute .setAttributeValue( minimumDeductible );
								mbQRuleAttributeList.add( deductibleAttribute  );
							}
							
							//for deductible rule end 
							
							if( !policyVO.getIsPrepackaged())
							{
								 /* Underwriting questions rules request mapper */
		                       	Fact uwQuestionFactor = getUWQFact( RulesConstants.FACT_MB_UW_Question, RulesConstants.MB_SECTION_ID, mbVO);
		                       	if(!Utils.isEmpty( uwQuestionFactor.getRuleSetAttribute() ))
		                       	{
		                       		factList.add( uwQuestionFactor );
		                       	}
		                    }

							//Modified as a part of ticket id 149213
							if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
								Fact commonFactor = new Fact();
								commonFactor.setFactName( RulesConstants.FACT_COMMON );
								ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

								RuleAttribute commissionAttribute = new RuleAttribute();
								commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
								commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
								commonRuleAttributeList.add( commissionAttribute );
								
								factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
							}
							
							
							factList.add( userFactor );
							factList.add( mbFactor );
							ruleSetList.add( ruleSet );
							
						}
					}
				}
				//Modified as a part of ticket id 149213
				if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
					if (!Utils.isEmpty(ruleSetList)) {
						Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
						while (ruleSetItr.hasNext()) {
							RuleSet ruleSet1 = ruleSetItr.next();
							if (!Utils.isEmpty(ruleSet1)
									&& RulesConstants.MACHINE_BD
											.equalsIgnoreCase(ruleSet1
													.getRuleSetName())) {
								ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1
										.getFact();

								Iterator<Fact> factListItr = factList1
										.iterator();
								while (factListItr.hasNext()) {
									Fact fact1 = factListItr.next();

									if (RulesConstants.FACT_MB
											.equalsIgnoreCase(fact1
													.getFactName())) {
										ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1
												.getRuleSetAttribute();

										RuleAttribute sumInsuredAttribute = new RuleAttribute();
										sumInsuredAttribute
												.setAttributeName(com.Constant.CONST_SUMINSUREDPERPOLICY);
										sumInsuredAttribute
												.setAttributeValue(new BigDecimal(
														decForm.format(sumInsuredPerPolicy))
														.toString());
										ruleAttributeList1
												.add(sumInsuredAttribute);
									}
								}
							}
						}
					}
                }
                else{
					if (!Utils.isEmpty(ruleSetList)) {
						Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
						while (ruleSetItr.hasNext()) {
							RuleSet ruleSet1 = ruleSetItr.next();
							if (!Utils.isEmpty(ruleSet1)
									&& RulesConstants.MACHINE_BD
											.equalsIgnoreCase(ruleSet1
													.getRuleSetName())) {
								ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1
										.getFact();

								Iterator<Fact> factListItr = factList1
										.iterator();
								while (factListItr.hasNext()) {
									Fact fact1 = factListItr.next();

									if (RulesConstants.FACT_MB
											.equalsIgnoreCase(fact1
													.getFactName())) {
										ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1
												.getRuleSetAttribute();

										RuleAttribute sumInsuredCheckMB = new RuleAttribute();
										sumInsuredCheckMB
												.setAttributeName("sumInsuredCheckMB");
										sumInsuredCheckMB
												.setAttributeValue(new BigDecimal(
														decForm.format(sumInsuredPerPolicy))
														.toString());
										ruleAttributeList1
												.add(sumInsuredCheckMB);
									}
								}
							}
						}
					}
               }
			}
		}
		return ruleInfo;
	}
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForTRL( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		double sumInsuredPerLocation = 0.0;
		double sumInsuredPerPolicy = 0.0;
		double limitSI = 0.0;
		int noOfPersons = 0;
		double commissionDiff = 0.0;
		
		/* TODO: Defaulting the empTypeBlackListed to N as this rule is for future implementation and has to pass always for now. */
		String empTypeBlackListed = "N";
		
		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		LocationVO location = null;
		Iterator<SectionVO> sectionListItr = null;
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		TravelBaggageVO travelBaggageVO = null;
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			SectionVO travelSection = getSection(SvcConstants.SECTION_ID_TB,policyVO);
			
				
			if( !Utils.isEmpty( travelSection )){
				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				riskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) travelSection.getRiskGroupDetails();
				
				if( !Utils.isEmpty( travelSection.getCommission() ) ) commissionDiff = getCommissionDifference( travelSection.getCommission(), RulesConstants.TB_CLASS, policyVO );
				
				for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
					location = (LocationVO) riskGroupDetailsEntry.getKey();
					travelBaggageVO = (TravelBaggageVO) riskGroupDetailsEntry.getValue();

					//ruleSet = new RuleSet();
					riskId = retrieveRiskId( location );
					limitSI = 0.0;
					
					if( !Utils.isEmpty( travelBaggageVO ) ){
						List<TravellingEmployeeVO> employeeVOs = travelBaggageVO.getTravellingEmpDets();
						//noOfPersons = employeeVOs.size();
						Iterator<TravellingEmployeeVO> iterator = employeeVOs.iterator();
						for(TravellingEmployeeVO employeeVO : employeeVOs)
						{
							if(!Utils.isEmpty( employeeVO ) && Utils.isEmpty(employeeVO.getIsToBeDeleted()))
							{
								SumInsuredVO sumInsuredVO = employeeVO.getSumInsuredDtl();
								if(!Utils.isEmpty( sumInsuredVO )){
									limitSI += sumInsuredVO.getSumInsured() ;
									
								}
								noOfPersons++;
							}
							
						}
						sumInsuredPerLocation = limitSI;
						sumInsuredPerPolicy += limitSI ;
					
					}
					if( !location.getToSave() ){
						continue;
					}
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					ruleSet = new RuleSet();
					ruleSet.setRuleSetName( RulesConstants.TRAVEL_BAGGAGE );
					ruleSet.setRiskId( riskId );

					factList = (ArrayList<Fact>) ruleSet.getFact();

					Fact userFactor = new Fact();
					userFactor.setFactName( RulesConstants.FACT_USER );
					ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

					RuleAttribute roleAttribute = new RuleAttribute();
					roleAttribute.setAttributeName( "role" );
					roleAttribute.setAttributeValue( roleType );
					userRuleAttributeList.add( roleAttribute );

					Fact travelFactor = new Fact();
					travelFactor.setFactName( RulesConstants.FACT_TRAVEL );
					ArrayList<RuleAttribute> trlRuleAttributeList = (ArrayList<RuleAttribute>) travelFactor.getRuleSetAttribute();

					//Modified as a part of ticket id 149213
					if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
	                	RuleAttribute sumInsuredAttribute = new RuleAttribute();
						sumInsuredAttribute.setAttributeName( com.Constant.CONST_SUMINSUREDPERLOCATION );
						sumInsuredAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocation ) ).toString() );
						trlRuleAttributeList.add( sumInsuredAttribute );
	                }
	                else{
	                	RuleAttribute sumInsuredPerPerson = new RuleAttribute();
	                	/*/* changed SumInsuredPerPerson to travelSection - UAT feedback - Ticket Id 149213*/
	                	sumInsuredPerPerson.setAttributeName( "travelSection" );
	                	sumInsuredPerPerson.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocation ) ).toString() );
						trlRuleAttributeList.add( sumInsuredPerPerson );
	                }
					
					RuleAttribute noOfPersonsAttribute = new RuleAttribute();
					noOfPersonsAttribute.setAttributeName( "numOfPersons" );
					//noOfPersonsAttribute.setAttributeValue( new Integer( noOfPersons ).toString() );
					noOfPersonsAttribute.setAttributeValue(Integer.valueOf( noOfPersons ).toString() );
					trlRuleAttributeList.add( noOfPersonsAttribute );
					
					RuleAttribute empTypeAttribute = new RuleAttribute();
					empTypeAttribute.setAttributeName( "empTypeBlacklisted" );
					empTypeAttribute.setAttributeValue( empTypeBlackListed );
					trlRuleAttributeList.add( empTypeAttribute );
					
					//Modified as a part of ticket id 149213
					if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
						Fact commonFactor = new Fact();
						commonFactor.setFactName( RulesConstants.FACT_COMMON );
						ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

						RuleAttribute commissionAttribute = new RuleAttribute();
						commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
						commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
						commonRuleAttributeList.add( commissionAttribute );
						
						factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
					}
					
					factList.add( userFactor );
					factList.add( travelFactor );
					ruleSetList.add( ruleSet );
				}
				
				//Modified as a part of ticket id 149213
				if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
					Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
					while( ruleSetItr.hasNext() ){
						RuleSet ruleSet1 = ruleSetItr.next();
						if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.TRAVEL_BAGGAGE.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){
							ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1.getFact();

							Iterator<Fact> factListItr = factList1.iterator();
							while( factListItr.hasNext() ){
								Fact fact1 = factListItr.next();

								if( RulesConstants.FACT_TRAVEL .equalsIgnoreCase( fact1.getFactName() ) ){
									ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();

									RuleAttribute liabPerPolicyAttribute = new RuleAttribute();
									liabPerPolicyAttribute.setAttributeName( com.Constant.CONST_SUMINSUREDPERPOLICY );
									liabPerPolicyAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerPolicy ) ).toString() );
									ruleAttributeList1.add( liabPerPolicyAttribute );
								}
							}
						}
					}
				}
			}
		}
		
		return ruleInfo;
	}
	
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForBI( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		int indemnityPeriod = 0;
		double totalSumInsuredRICategory1 = 0.0;
		double totalSumInsuredRICategory2 = 0.0;
		double totalSumInsuredRICategory3 = 0.0;
		double totalSumInsuredRICategory4 = 0.0;
		double totalSumInsuredRICategory5 = 0.0;
		double totalSumInsuredRICategory6 = 0.0;
		double totalSumInsuredRICategory7 = 0.0;
		double totalSumInsured = 0.0;
		double commissionDiff = 0.0;
		int riCategory = 0;
		PARUWDetailsVO uwDetails = null;	
		
		
		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		HashMap<RiskGroup, RiskGroupDetails> parRiskGroupDetails = null;
		LocationVO location = null;
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		BIVO bivo = null;
		ParVO parVO = null;
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			
			SectionVO biSection =  getSection(SvcConstants.SECTION_ID_BI, policyVO);
			
			SectionVO parSection =  getSection(SvcConstants.SECTION_ID_PAR, policyVO);
			if(!Utils.isEmpty( parSection ) ){
				parRiskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) parSection.getRiskGroupDetails();
			}
			
			if( !Utils.isEmpty( biSection ) ){
				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				riskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) biSection.getRiskGroupDetails();
				
				if( !Utils.isEmpty( biSection.getCommission() ) ) commissionDiff = getCommissionDifference( biSection.getCommission(), RulesConstants.BI_CLASS, policyVO );
				
				for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
					location = (LocationVO) riskGroupDetailsEntry.getKey();
					bivo = (BIVO) riskGroupDetailsEntry.getValue();
					parVO = (ParVO) parRiskGroupDetails.get( location );
					
					//ruleSet = new RuleSet();
					riskId = retrieveRiskId( location );
					if(!Utils.isEmpty( parVO )) {
						uwDetails = (PARUWDetailsVO) parVO.getUwDetails();
						if( !Utils.isEmpty( uwDetails ) ){
							if( !Utils.isEmpty( uwDetails.getCategoryRI() ) )
								riCategory = uwDetails.getCategoryRI();
							else
								throw new BusinessException( "", null, "RI Category is null for: " + riskId );
						}
					}
					//Modified as a part of ticket id 149213
					//if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )) ){
					if( !Utils.isEmpty( bivo ) ){
							
						indemnityPeriod = bivo.getIndemnityPeriod();							
									
						double comSiParBi = getCombinedSIForPARBI(policyVO,location);
							
						totalSumInsured = totalSumInsured + comSiParBi;
							
						//Commenting for 149213 rule change
						if( riCategory == SvcConstants.RI_CATEGORY_ONE ){
							totalSumInsuredRICategory1 = totalSumInsuredRICategory1 + comSiParBi;
						}
						else if( riCategory == SvcConstants.RI_CATEGORY_TWO ){
							totalSumInsuredRICategory2 = totalSumInsuredRICategory2 + comSiParBi;
						}
						else if( riCategory == SvcConstants.RI_CATEGORY_THREE ){
							totalSumInsuredRICategory3 = totalSumInsuredRICategory3 + comSiParBi;
						}
						else if( riCategory == SvcConstants.RI_CATEGORY_FOUR ){
							totalSumInsuredRICategory4 = totalSumInsuredRICategory4 + comSiParBi;
						}
						else if( riCategory == SvcConstants.RI_CATEGORY_FIVE ){
							totalSumInsuredRICategory5 = totalSumInsuredRICategory5 + comSiParBi;
						}
						else if( riCategory == SvcConstants.RI_CATEGORY_SIX ){
							totalSumInsuredRICategory6 = totalSumInsuredRICategory6 + comSiParBi;
						}
						else if( riCategory == SvcConstants.RI_CATEGORY_SEVEN ){
							totalSumInsuredRICategory7 = totalSumInsuredRICategory7 + comSiParBi;
						}
					}
					else if( !Utils.isEmpty( parVO ) )
					{
						totalSumInsured = totalSumInsured + getParSI( parVO );
					}					
					if( !location.getToSave() ){
						continue;
					}
					
					
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					ruleSet = new RuleSet();
					ruleSet.setRuleSetName( RulesConstants.BUS_INT);
					ruleSet.setRiskId( riskId );

					factList = (ArrayList<Fact>) ruleSet.getFact();

					Fact userFactor = new Fact();
					userFactor.setFactName( RulesConstants.FACT_USER );
					ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

					RuleAttribute roleAttribute = new RuleAttribute();
					roleAttribute.setAttributeName( "role" );
					roleAttribute.setAttributeValue( roleType );
					userRuleAttributeList.add( roleAttribute );

					Fact biFactor = new Fact();
					biFactor.setFactName( RulesConstants.FACT_BI );
					ArrayList<RuleAttribute> biRuleAttributeList = (ArrayList<RuleAttribute>) biFactor.getRuleSetAttribute();

					RuleAttribute indemnityPeriodAttribute = new RuleAttribute();
					indemnityPeriodAttribute.setAttributeName( "indemnityPeriod" );
					//indemnityPeriodAttribute.setAttributeValue( new Integer( indemnityPeriod ).toString() );
					indemnityPeriodAttribute.setAttributeValue( Integer.valueOf( indemnityPeriod ).toString() );
					biRuleAttributeList.add( indemnityPeriodAttribute );
					
					//Modified as a part of ticket id 149213
					if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
						Fact commonFactor = new Fact();
						commonFactor.setFactName( RulesConstants.FACT_COMMON );
						ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

						RuleAttribute commissionAttribute = new RuleAttribute();
						commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
						commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
						commonRuleAttributeList.add( commissionAttribute );
						
						factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
					}
					
					
					factList.add( userFactor );
					factList.add( biFactor );
				

					ruleSetList.add( ruleSet );
				}
				
					
					Fact siFactor = new Fact();
					siFactor.setFactName( RulesConstants.FACT_SI );
					factList.add( siFactor );
					
					Iterator<RuleSet> ruleSetItr = ruleSetList.iterator();
					while( ruleSetItr.hasNext() ){
					RuleSet ruleSet1 = ruleSetItr.next();
					if( !Utils.isEmpty( ruleSet1 ) && RulesConstants.BUS_INT.equalsIgnoreCase( ruleSet1.getRuleSetName() ) ){
						ArrayList<Fact> factList1 = (ArrayList<Fact>) ruleSet1.getFact();

						Iterator<Fact> factListItr = factList1.iterator();
						while( factListItr.hasNext() ){
							Fact fact1 = factListItr.next();

							if( RulesConstants.FACT_SI.equalsIgnoreCase( fact1.getFactName() ) ){
								ArrayList<RuleAttribute> ruleAttributeList1 = (ArrayList<RuleAttribute>) fact1.getRuleSetAttribute();

								RuleAttribute sumInsuredRiCat1Attribute = new RuleAttribute();
								sumInsuredRiCat1Attribute.setAttributeName( "combinedSIRICat1" );
								sumInsuredRiCat1Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory1 ) ).toString() );
								ruleAttributeList1.add( sumInsuredRiCat1Attribute );

								RuleAttribute sumInsuredRiCat2Attribute = new RuleAttribute();
								sumInsuredRiCat2Attribute.setAttributeName( "combinedSIRICat2" );
								sumInsuredRiCat2Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory2 ) ).toString() );
								ruleAttributeList1.add( sumInsuredRiCat2Attribute );

								RuleAttribute sumInsuredRiCat3Attribute = new RuleAttribute();
								sumInsuredRiCat3Attribute.setAttributeName( "combinedSIRICat3" );
								sumInsuredRiCat3Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory3 ) ).toString() );
								ruleAttributeList1.add( sumInsuredRiCat3Attribute );

								RuleAttribute sumInsuredRiCat4Attribute = new RuleAttribute();
								sumInsuredRiCat4Attribute.setAttributeName( "combinedSIRICat4" );
								sumInsuredRiCat4Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory4 ) ).toString() );
								ruleAttributeList1.add( sumInsuredRiCat4Attribute );

								RuleAttribute sumInsuredRiCat5Attribute = new RuleAttribute();
								sumInsuredRiCat5Attribute.setAttributeName( "combinedSIRICat5" );
								sumInsuredRiCat5Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory5 ) ).toString() );
								ruleAttributeList1.add( sumInsuredRiCat5Attribute );

								RuleAttribute sumInsuredRiCat6Attribute = new RuleAttribute();
								sumInsuredRiCat6Attribute.setAttributeName( "combinedSIRICat6" );
								sumInsuredRiCat6Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory6 ) ).toString() );
								ruleAttributeList1.add( sumInsuredRiCat6Attribute );

								RuleAttribute sumInsuredRiCat7Attribute = new RuleAttribute();
								sumInsuredRiCat7Attribute.setAttributeName( "combinedSIRICat7" );
								sumInsuredRiCat7Attribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredRICategory7 ) ).toString() );
								ruleAttributeList1.add( sumInsuredRiCat7Attribute );
								
								RuleAttribute sumInsuredAttribute = new RuleAttribute();
								//Modified as a part of ticket id 149213
								if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) )
									sumInsuredAttribute.setAttributeName( "combinedSI" );
								
								else
									sumInsuredAttribute.setAttributeName( "sumInsuredCheckPARAndBI" );								
								sumInsuredAttribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsured ) ).toString() );
								ruleAttributeList1.add( sumInsuredAttribute );
								
							}
						}
					}
				}
			}
		}
		return ruleInfo;
	}
	
	private String getCommodityDesc(int cType,PolicyVO policyVO)
	{
		LookUpListVO listVO = SvcUtils
		.getLookUpCodesList( "COMMODITY_TYPE", policyVO.getScheme().getSchemeCode().toString(), policyVO.getScheme().getTariffCode().toString() );
		
		for( LookUpVO luVO : listVO.getLookUpList() )
		{
			if( !Utils.isEmpty( luVO.getCode() ) && (cType == luVO.getCode().intValue()))
			{
				return luVO.getDescription();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
private RuleInfo createRuleInfoForGIT( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		double annualCarryingLimitAllLocations = 0.0;
		double annualCarryingLimitCurrentLocation = 0.0;
		double singleTransitLimitAllLocations = 0.0;
		double singleTransitLimitCurrentLocation = 0.0;
		String singleTransitExceedsAnnualCarrying = "N";
		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		LocationVO location = null;
		//Iterator<SectionVO> sectionListItr = null;
		java.util.List<CommodityDetailsVO> commodityDtls = null;
		Iterator<CommodityDetailsVO> commodityItr = null;
		CommodityDetailsVO commodityDtlsVo = null;
		int commodityType ;
		String commodityDesc ;
		Map<Integer,String> commodityMap = null;
		StringBuffer commodityTypeStr = null;
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		GoodsInTransitVO goodsInTransitVO = null;
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			
			SectionVO gitSection = getSection(SvcConstants.SECTION_ID_GIT, policyVO);
			
				
			if( !Utils.isEmpty( gitSection ) ){
				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				riskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) gitSection.getRiskGroupDetails();
				
				for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() )
				{
					location = (LocationVO) riskGroupDetailsEntry.getKey();
					goodsInTransitVO = (GoodsInTransitVO) riskGroupDetailsEntry.getValue();
					annualCarryingLimitCurrentLocation = 0.0;
					//ruleSet = new RuleSet();
					riskId = retrieveRiskId( location );

					if( !Utils.isEmpty( goodsInTransitVO ) )
					{
						annualCarryingLimitCurrentLocation = goodsInTransitVO.getEstimatedAnnualCarryValue();
						annualCarryingLimitAllLocations += annualCarryingLimitCurrentLocation;
						singleTransitLimitCurrentLocation = goodsInTransitVO.getSingleTransitLimit();
						singleTransitLimitAllLocations += singleTransitLimitCurrentLocation;
						if( location.getToSave() )
						{
							if(singleTransitLimitCurrentLocation > annualCarryingLimitCurrentLocation)
							{
								singleTransitExceedsAnnualCarrying = "Y";
							}
							 commodityDtls =   goodsInTransitVO.getCommodityDtls();
							if(!Utils.isEmpty(commodityDtls) ) 
							{
								commodityItr = commodityDtls.iterator();
								while(commodityItr.hasNext())
								{
									commodityDtlsVo = commodityItr.next();
									commodityType = commodityDtlsVo.getCommodityType();
									commodityDesc = getCommodityDesc(commodityType,policyVO);
									if(Utils.isEmpty( commodityTypeStr ))
									{
										commodityTypeStr = new StringBuffer();
									}
									if(Utils.isEmpty( commodityMap ))
									{
										commodityMap = new HashMap<Integer, String>();
									}
									commodityMap.put( commodityType, commodityDesc );
									commodityTypeStr.append( commodityType );
									commodityTypeStr.append( ":" );
								}
								policyVO.setCommodityMap( commodityMap );
							}
							
						}
					}
					
				}
				
				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				ruleSet = new RuleSet();
				ruleSet.setRuleSetName( RulesConstants.GOODS_IN_TRANSIT );
				ruleSet.setRiskId( riskId );

				factList = (ArrayList<Fact>) ruleSet.getFact();

				Fact userFactor = new Fact();
				userFactor.setFactName( RulesConstants.FACT_USER );
				ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

				RuleAttribute roleAttribute = new RuleAttribute();
				roleAttribute.setAttributeName( "role" );
				roleAttribute.setAttributeValue( roleType );
				userRuleAttributeList.add( roleAttribute );

				Fact gitFactor = new Fact();
				gitFactor.setFactName( RulesConstants.FACT_GIT );
				ArrayList<RuleAttribute> gitRuleAttributeList = (ArrayList<RuleAttribute>) gitFactor.getRuleSetAttribute();

				//Modified as a part of ticket id 149213
				if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
					RuleAttribute annualCarryingAttribute = new RuleAttribute();
					annualCarryingAttribute.setAttributeName( "annualCarryingLimit" );
					annualCarryingAttribute.setAttributeValue( new BigDecimal( decForm.format( annualCarryingLimitAllLocations ) ).toString() );
					gitRuleAttributeList.add( annualCarryingAttribute );
					
					RuleAttribute commodityTypeAttribute = new RuleAttribute();
					commodityTypeAttribute.setAttributeName( "commodityTypeArrey" );
					commodityTypeAttribute.setAttributeValue(commodityTypeStr.toString());
					gitRuleAttributeList.add( commodityTypeAttribute );
					
					RuleAttribute singleTransitLimitAttribute = new RuleAttribute();
					singleTransitLimitAttribute.setAttributeName( "singleTransitLimit" );
					singleTransitLimitAttribute.setAttributeValue( new BigDecimal( decForm.format( singleTransitLimitAllLocations ) ).toString() );
					gitRuleAttributeList.add( singleTransitLimitAttribute );

					RuleAttribute singleTransitExceedsAnnualCarryingAtt = new RuleAttribute();
					singleTransitExceedsAnnualCarryingAtt.setAttributeName( "singleTransitExceedsAnnualCarry" );
					singleTransitExceedsAnnualCarryingAtt.setAttributeValue(  singleTransitExceedsAnnualCarrying  );
					gitRuleAttributeList.add( singleTransitExceedsAnnualCarryingAtt );
					
					double commissionDiff = 0.0;
					if( !Utils.isEmpty( gitSection.getCommission() ) ) commissionDiff = getCommissionDifference( gitSection.getCommission(), RulesConstants.GIT_CLASS, policyVO );
					
					Fact commonFactor = new Fact();
					commonFactor.setFactName( RulesConstants.FACT_COMMON );
					ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();
					
					RuleAttribute commissionAttribute = new RuleAttribute();
					commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
					commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
					commonRuleAttributeList.add( commissionAttribute );
					
					factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
				}
				
					
				factList.add( userFactor );
				factList.add( gitFactor );
				
				ruleSetList.add( ruleSet );
			}
		}
		
		return ruleInfo;
	}
	
	private int getCalculatedAge(Date dob)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dob);
		Calendar now = Calendar.getInstance();
		 
		int nowMonth = now.get(Calendar.MONTH)+1;
		int curMonth = cal.get(Calendar.MONTH)+1;
		int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		 
		if( curMonth > nowMonth ) {
		age--;
		}
		if( curMonth ==  nowMonth && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH) ) {
		age--;
		}
		 
		return age;

	}
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForGPA( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		double capitalSumInsurred = 0.0;
		double annualSumInsurred = 0.0,capsSI = 0.0;
		double maxCapitalSI = 0.0;
		StringBuffer annualSumInsuredArrey = new StringBuffer();
		StringBuffer capitalSumInsuredArrey = new StringBuffer();
		int noOfEmployees = 0;
		Integer ageOfEmployee = 0;
		StringBuffer arrayAgesString = null;
		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		LocationVO location = null;
		String dob = null;
		//Iterator<SectionVO> sectionListItr = null;
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		GroupPersonalAccidentVO groupPersonalAccidentVO = null;
		double sumInsuredPerLocGPA = 0;
		double sumInsuredPerGPA = 0;
		
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			
			SectionVO gpaSection = getSection(SvcConstants.SECTION_ID_GROUP_PERSONAL_ACCIDENT, policyVO);
			
				
			if( !Utils.isEmpty( gpaSection ) ){
				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				riskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) gpaSection.getRiskGroupDetails();
				
				for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
					location = (LocationVO) riskGroupDetailsEntry.getKey();
					groupPersonalAccidentVO = (GroupPersonalAccidentVO) riskGroupDetailsEntry.getValue();

					//ruleSet = new RuleSet();
					riskId = retrieveRiskId( location );

					if( !Utils.isEmpty( groupPersonalAccidentVO ) )
					{
						List<GPANammedEmpVO> gpaNammedEmpVOs = groupPersonalAccidentVO.getGpaNammedEmpVO();
						List<GPAUnnammedEmpVO> gpaUnnammedEmpVOs = groupPersonalAccidentVO.getGpaUnnammedEmpVO();
						
						for(GPANammedEmpVO gpaNammedEmpVO : gpaNammedEmpVOs){
							
							if(!(Utils.isEmpty( gpaNammedEmpVO.getSumInsuredDetails() )) && Utils.isEmpty(gpaNammedEmpVO.getIsToBeDeleted()))
							{
								capsSI = gpaNammedEmpVO.getSumInsuredDetails().getSumInsured();
								/*Added - Dileep*/
								if(capsSI > maxCapitalSI){
									maxCapitalSI = capsSI;
								}
								capitalSumInsurred += capsSI;
								dob = gpaNammedEmpVO.getNammedEmpDob();
								if(!Utils.isEmpty( dob ))
								{
									dob = dob.replace( "-", "/" );
									ageOfEmployee = getCalculatedAge(new Date(dob));
									if(Utils.isEmpty( arrayAgesString ))
									{
										arrayAgesString = new StringBuffer();
									}
									arrayAgesString.append(ageOfEmployee );
									arrayAgesString.append(":");
								}
								noOfEmployees ++;
								
								annualSumInsurred = gpaNammedEmpVO.getNammedEmpAnnualSalary();
								annualSumInsuredArrey.append( annualSumInsurred+":" );
								
								capitalSumInsuredArrey.append( capsSI+":" );
							}
							sumInsuredPerLocGPA = 	sumInsuredPerLocGPA + capsSI;
						}
						logger.debug( "Age of employee - "+ageOfEmployee );
						for(GPAUnnammedEmpVO gpaUnnammedEmpVO : gpaUnnammedEmpVOs)
						{
							if(!Utils.isEmpty( gpaUnnammedEmpVO.getSumInsuredDetails() ) && Utils.isEmpty(gpaUnnammedEmpVO.getIsToBeDeleted()))
							{
								capsSI = gpaUnnammedEmpVO.getSumInsuredDetails().getSumInsured();
								
								/*Added - Dileep*/
								if(capsSI > maxCapitalSI){
									maxCapitalSI = capsSI;
								}
								
								capitalSumInsurred += capsSI;
								capitalSumInsuredArrey.append( capsSI+":" );
								annualSumInsurred = gpaUnnammedEmpVO.getUnnammedAnnualSalary();
								annualSumInsuredArrey.append( annualSumInsurred+":" );
							}
							if(!Utils.isEmpty( gpaUnnammedEmpVO.getUnnammedNumberOfEmloyee()) && Utils.isEmpty(gpaUnnammedEmpVO.getIsToBeDeleted()))
							{
								noOfEmployees += gpaUnnammedEmpVO.getUnnammedNumberOfEmloyee();
							}
							
							sumInsuredPerLocGPA = 	sumInsuredPerLocGPA + capsSI;
						}
					
						sumInsuredPerGPA = 	sumInsuredPerGPA + sumInsuredPerLocGPA;
					}
					if( !location.getToSave() )
					{
						continue;
					}
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					ruleSet = new RuleSet();
					ruleSet.setRuleSetName( RulesConstants.GROUP_PA );
					ruleSet.setRiskId( riskId );

					factList = (ArrayList<Fact>) ruleSet.getFact();

					Fact userFactor = new Fact();
					userFactor.setFactName( RulesConstants.FACT_USER );
					ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

					RuleAttribute roleAttribute = new RuleAttribute();
					roleAttribute.setAttributeName( "role" );
					roleAttribute.setAttributeValue( roleType );
					userRuleAttributeList.add( roleAttribute );

					Fact gpaFactor = new Fact();
					gpaFactor.setFactName( RulesConstants.FACT_GPA );
					ArrayList<RuleAttribute> gpaRuleAttributeList = (ArrayList<RuleAttribute>) gpaFactor.getRuleSetAttribute();

					RuleAttribute capitalSumInsuredRule = new RuleAttribute();					 
					capitalSumInsuredRule.setAttributeName( "capitalSumInsured" );
					
					/*Changed attribute value from capitalSumInsurred to maxCapitalSI - UAT feedback - Ticket Id - 149213*/					  
					capitalSumInsuredRule.setAttributeValue( new BigDecimal( decForm.format( maxCapitalSI ) ).toString() );
					gpaRuleAttributeList.add( capitalSumInsuredRule );
					
					RuleAttribute annualSumInsuredArreyRule = new RuleAttribute();
					annualSumInsuredArreyRule.setAttributeName( "annualSumInsuredArrey" );
					annualSumInsuredArreyRule.setAttributeValue(annualSumInsuredArrey.toString());
					gpaRuleAttributeList.add( annualSumInsuredArreyRule );
					
					RuleAttribute capitalSumInsuredArreyRule = new RuleAttribute();
					capitalSumInsuredArreyRule.setAttributeName( "capitalSumInsuredArrey" );
					capitalSumInsuredArreyRule.setAttributeValue(capitalSumInsuredArrey.toString());
					gpaRuleAttributeList.add( capitalSumInsuredArreyRule );
					
					RuleAttribute noOfEmployeesAttribute = new RuleAttribute();
					noOfEmployeesAttribute.setAttributeName( "noOfEmployees" );
					//noOfEmployeesAttribute.setAttributeValue( new Integer(  noOfEmployees ).toString() );
					noOfEmployeesAttribute.setAttributeValue( Integer.valueOf(  noOfEmployees ).toString() );
					gpaRuleAttributeList.add( noOfEmployeesAttribute );
					
					RuleAttribute agesOfEmployees = null;
					/*if(Utils.isEmpty( arrayAgesString ))
					{
						arrayAgesString = new StringBuffer();
						arrayAgesString.append( 25 );
						arrayAgesString.append( ":" );
					}*/
					if(!Utils.isEmpty( arrayAgesString ))
					{
						agesOfEmployees = new RuleAttribute();
						agesOfEmployees.setAttributeName( "agesArreyOfEmployees" );
						//agesOfEmployees.setAttributeValue("10:23:45:18:89:15");
						agesOfEmployees.setAttributeValue(arrayAgesString.toString());
						gpaRuleAttributeList.add( agesOfEmployees );
					}
					
					//Modified as a part of ticket id 149213
					if(! (BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )  )){
						
						/*Commented - UAT feedback - Ticket Id - 149213*/
						/*RuleAttribute sumInsuredperlocationGPA = new RuleAttribute();
	                	sumInsuredperlocationGPA.setAttributeName( "sumInsuredperlocationGPA" );
	                	sumInsuredperlocationGPA.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocGPA ) ).toString() );  // 149213 new rule value need to set
						gpaRuleAttributeList.add( sumInsuredperlocationGPA );*/
						
						RuleAttribute sumInsuredCheckGPA = new RuleAttribute();
						sumInsuredCheckGPA.setAttributeName( "sumInsuredCheckGPA" );
						sumInsuredCheckGPA.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerGPA ) ).toString() );    // 149213 new rule value need to set
						gpaRuleAttributeList.add( sumInsuredCheckGPA );
	                }
					
	              //Modified as a part of ticket id 149213
	                if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
						double commissionDiff = 0.0;
						if( !Utils.isEmpty( gpaSection.getCommission() ) ) commissionDiff = getCommissionDifference( gpaSection.getCommission(), RulesConstants.GPA_CLASS, policyVO );
						
						Fact commonFactor = new Fact();
						commonFactor.setFactName( RulesConstants.FACT_COMMON );
						ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();
						
						RuleAttribute commissionAttribute = new RuleAttribute();
						commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
						commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
						commonRuleAttributeList.add( commissionAttribute );
						
						factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
					}
					
					factList.add( userFactor );
					factList.add( gpaFactor );

					ruleSetList.add( ruleSet );
				}
			}
		}
		
		return ruleInfo;
	}
	
	/**
	 * 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	
	private boolean isSectionPresent(int sectionId, PolicyVO policyDetails)
	{
		SectionVO section = new SectionVO(RiskGroupingLevel.LOCATION);
		section.setSectionId(sectionId);
		return policyDetails.getRiskDetails().contains(section);
	}
	
	private RuleInfo createRuleInfoForDOS( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){double totalSumInsuredAllLocations = 0.0;
    double totalSumInsuredCurrrentLocation = 0.0;
    double commissionDiff = 0.0;
    String riskId = null;
    HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
    LocationVO location = null;
    double minDeduc = 5000.00; // set a high deductible
    double deductible = 0.0;
   // Iterator<SectionVO> sectionListItr = null;
    String sumInsuredIsLess = "Y";
    String isStockInsuredInPar = "Y";
    ParVO parVO = null;
    SectionVO parSection = null;
    
    ArrayList<RuleSet> ruleSetList = null;
    RuleSet ruleSet = null;
    ArrayList<Fact> factList = null;
    DeteriorationOfStockVO deteriorationOfStockVO = null;
    
    PolicyVO policyVO = (PolicyVO) policyBaseVO;
    
    HashMap<RiskGroup, RiskGroupDetails> parRiskGroupDetails = null;
    if( Utils.isEmpty( ruleInfo ) )
    {
          ruleInfo = new RuleInfo();
    }
    if(isSectionPresent(SvcConstants.SECTION_ID_PAR, policyVO))
          parSection =  getSection(SvcConstants.SECTION_ID_PAR, policyVO);
    if(!Utils.isEmpty( parSection ) )
    {
          parRiskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) parSection.getRiskGroupDetails();
    }
    if( !Utils.isEmpty( policyVO ) )
    {
          SectionVO dosSection = getSection(SvcConstants.SECTION_ID_DOS, policyVO);
                
          if( !Utils.isEmpty( dosSection ) )
          {
                ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
                riskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) dosSection.getRiskGroupDetails();
                if( !Utils.isEmpty( dosSection.getCommission() ) ) commissionDiff = getCommissionDifference( dosSection.getCommission(), RulesConstants.BI_CLASS, policyVO );
                
                for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() )
                {
                      location = (LocationVO) riskGroupDetailsEntry.getKey();
                      deteriorationOfStockVO = (DeteriorationOfStockVO) riskGroupDetailsEntry.getValue();

                      //ruleSet = new RuleSet();
                      riskId = retrieveRiskId( location );

                      if( !Utils.isEmpty( deteriorationOfStockVO ))
                      {
                            totalSumInsuredCurrrentLocation = 0.0;
                            List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetailsVOs = deteriorationOfStockVO.getDeteriorationOfStockDetails();
                            for(DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO : deteriorationOfStockDetailsVOs)
                            {
                                  if(!Utils.isEmpty( deteriorationOfStockDetailsVO ))
                                  {
                                        if(!Utils.isEmpty( deteriorationOfStockDetailsVO.getSumInsuredDetails()) &&
                                                    !Utils.isEmpty( deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured() )  && Utils.isEmpty(deteriorationOfStockDetailsVO.getIsToBeDeleted())) 
                                        {
                                        	totalSumInsuredAllLocations += deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured();
                                        	totalSumInsuredCurrrentLocation += deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured();

                                        	//get min deductible
                                        	deductible = deteriorationOfStockDetailsVO.getSumInsuredDetails().getDeductible().doubleValue();
                                        	if(minDeduc > deductible){
                                        		minDeduc = deductible;
                                        	}
                                        }
                                  }
                            }
                      
                      if( location.getToSave() )
                      {
                            if(parSection == null)
                            {
                                  isStockInsuredInPar = "N";
                                  sumInsuredIsLess = "N";
                            }
                            else
                            {
                                  parVO = (ParVO) parRiskGroupDetails.get( location );
                                  if(isStockInsuredInPar(parVO))
                                  {
                                        isStockInsuredInPar = "N"; // if you send Y rule is triggered
                                  }
                                  else
                                  {
                                      sumInsuredIsLess = "N"; // if stock is not there ,so sumInsuredIsLess wont be triggered
                                  }
                                  if(isSumInsuredIsLessThanStockInPar(totalSumInsuredCurrrentLocation,parVO))
                                  {
                                        sumInsuredIsLess = "N";  // if you send Y rule is triggered
                                  }
                            }
                            
                      }
                      
                      ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
                      ruleSet = new RuleSet();
                      ruleSet.setRuleSetName( RulesConstants.DOS );
                      ruleSet.setRiskId( riskId );

                      factList = (ArrayList<Fact>) ruleSet.getFact();

                      Fact userFactor = new Fact();
                      userFactor.setFactName( RulesConstants.FACT_USER );
                      ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

                      RuleAttribute roleAttribute = new RuleAttribute();
                      roleAttribute.setAttributeName( "role" );
                      roleAttribute.setAttributeValue( roleType );
                      userRuleAttributeList.add( roleAttribute );

                      Fact gitFactor = new Fact();
                      gitFactor.setFactName( RulesConstants.FACT_DOS );
                      ArrayList<RuleAttribute> gitRuleAttributeList = (ArrayList<RuleAttribute>) gitFactor.getRuleSetAttribute();

                    //Modified as a part of ticket id 149213
  	                if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
                    	  RuleAttribute sumInsuredAttribute = new RuleAttribute();
                          sumInsuredAttribute.setAttributeName( "sumInsured" );
                          sumInsuredAttribute.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredAllLocations ) ).toString());
                          gitRuleAttributeList.add( sumInsuredAttribute );
                          
                          RuleAttribute storageSumInsured = new RuleAttribute();
                          storageSumInsured.setAttributeName( "storageSumInsured" );
                          storageSumInsured.setAttributeValue(sumInsuredIsLess);
                          gitRuleAttributeList.add( storageSumInsured );
                          
                          RuleAttribute parStorageSumInsured = new RuleAttribute();
                          parStorageSumInsured.setAttributeName( "parStorageSumInsured" );
                          parStorageSumInsured.setAttributeValue(isStockInsuredInPar);
                          gitRuleAttributeList.add( parStorageSumInsured );
                      }  
                      else{
                    	  RuleAttribute sumInsuredPerlocationDOS = new RuleAttribute();     
                    	  sumInsuredPerlocationDOS.setAttributeName( "sumInsuredPerlocationDOS" );
                    	  sumInsuredPerlocationDOS.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredCurrrentLocation ) ).toString()); // 149213 new rule value need to be set
                          gitRuleAttributeList.add( sumInsuredPerlocationDOS );
                          
                          RuleAttribute sumInsuredCheckDOS = new RuleAttribute();   //rule name change as a part of ticket id 149213
                          sumInsuredCheckDOS.setAttributeName( "sumInsuredCheckLocDOS" );
                          sumInsuredCheckDOS.setAttributeValue( new BigDecimal( decForm.format( totalSumInsuredAllLocations ) ).toString());
                          gitRuleAttributeList.add( sumInsuredCheckDOS );
                      }
                      
                      
                      //for bahrain
                      if( BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) ){
                    	  
                    	String minimumDeductible = String.valueOf(new Double(minDeduc).intValue());
                    	  
                    	RuleAttribute deductibleAttribute = new RuleAttribute();
                  		deductibleAttribute.setAttributeName( com.Constant.CONST_DEDUCTABLEMINVALUE );
                  		deductibleAttribute.setAttributeValue(minimumDeductible);
                  		gitRuleAttributeList.add( deductibleAttribute );
                      }
                      //for bahrain - end
                      
                      if( !policyVO.getIsPrepackaged())
                      {
							 /* Underwriting questions rules request mapper */
                    	Fact uwQuestionFactor = getUWQFact( RulesConstants.FACT_DOS_UW_Question, RulesConstants.DOS_SECTION_ID, deteriorationOfStockVO);
	                    if(!Utils.isEmpty( uwQuestionFactor.getRuleSetAttribute() ))
	                    {
	                       factList.add( uwQuestionFactor );
	                     }
	                  }
                    
                      //Added as part of ticket id 149213
                    
                      if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
                    	  Fact commonFactor = new Fact();
                          commonFactor.setFactName( RulesConstants.FACT_COMMON );
                          
                          ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();
                          RuleAttribute commissionAttribute = new RuleAttribute();
                          commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
                          commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
                          commonRuleAttributeList.add( commissionAttribute );
                         
                          factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
                      }
                     
                      factList.add( userFactor );
                      factList.add( gitFactor );
                      ruleSetList.add( ruleSet );
                      }
                }
          }
    }
    
    return ruleInfo;
}
	
	/**
	 * Renewal rules 
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return
	 */
	private RuleInfo createRuleInfoForREN( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		String claimExists;
		String endorsmentExists;
		String brAccBlocked = "N";
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		/** 
		 * There is no Risk Id for Renewals
		 * Setting a dummy id as risk id is a mandatory field in Rules Engine
		 * for response mapping. 
		 */
		String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_RENEWAL );
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;

		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			RenewalVO renewalVO = policyVO.getRenewals()	;
			ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
			ruleSet = new RuleSet();
			ruleSet.setRuleSetName( RulesConstants.REN );
			ruleSet.setRiskId( riskId );

			factList = (ArrayList<Fact>) ruleSet.getFact();

			Fact userFactor = new Fact();
			userFactor.setFactName( RulesConstants.FACT_USER );
			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

			RuleAttribute roleAttribute = new RuleAttribute();
			roleAttribute.setAttributeName( "role" );
			roleAttribute.setAttributeValue( roleType );
			userRuleAttributeList.add( roleAttribute );

			Fact renFactor = new Fact();
			renFactor.setFactName( RulesConstants.FACT_REN );
			ArrayList<RuleAttribute> renRuleAttributeList = (ArrayList<RuleAttribute>) renFactor.getRuleSetAttribute();

			RuleAttribute claimExistsAttribute = new RuleAttribute();
			
			claimExistsAttribute.setAttributeName("renewalClaimExist");
			//claimCount = renewalVO.getClaimCount();
			if(renewalVO.getClaimCount()>0){
				claimExists = "Y";
			} else {
				claimExists = "N";
			}
			claimExistsAttribute.setAttributeValue( claimExists );
			renRuleAttributeList.add( claimExistsAttribute );
			RuleAttribute osPriorPremiumAttribute = new RuleAttribute();
			osPriorPremiumAttribute.setAttributeName( "outstandingPriorPremium" );
			osPriorPremiumAttribute.setAttributeValue(decForm.format( renewalVO.getOsPremium() ).toString());
			renRuleAttributeList.add(osPriorPremiumAttribute);
			
			RuleAttribute endorsmentAttribute = new RuleAttribute();
			endorsmentAttribute.setAttributeName( "allowEndorsementAfterRenewal" );
			if(!Utils.isEmpty( renewalVO.getEndorsmentList() )){
				endorsmentExists = "Y";
			} else {
				endorsmentExists = "N";
			}
			endorsmentAttribute.setAttributeValue( endorsmentExists );
			renRuleAttributeList.add(endorsmentAttribute);
			
			RuleAttribute discountLoadingAttribute = new RuleAttribute();
			discountLoadingAttribute.setAttributeName( "discountLoading" );
			Double nonNullDiscLoad = Utils.isEmpty( renewalVO.getDiscountLoadingPer() ) ? SvcConstants.zeroVal :  renewalVO.getDiscountLoadingPer().doubleValue();
			discountLoadingAttribute.setAttributeValue( nonNullDiscLoad.toString() );
			renRuleAttributeList.add(discountLoadingAttribute);
			
			RuleAttribute brAccBlockedStatus = new RuleAttribute();
			brAccBlockedStatus.setAttributeName( "brAccBlockedStatus" );
			
			if(!Utils.isEmpty(renewalVO.getBrokerStatus()) && renewalVO.getBrokerStatus() == SvcConstants.BLOCKED_STATUS){
				brAccBlocked = "Y";
			} else {
				brAccBlocked = "N";
			}
			brAccBlockedStatus.setAttributeValue( brAccBlocked );
			renRuleAttributeList.add(brAccBlockedStatus);
			
			
			
			factList.add( userFactor );
			factList.add( renFactor );
			ruleSetList.add( ruleSet );
		}
		
		return ruleInfo;

	}
	
private RuleInfo createRuleInfoForFID( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){
		
		double limitPerPerson = 0.0;
		int noOfFidEmployees = 0;
		double commissionDiff = 0.0;
		String riskId = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		LocationVO location = null;
		Iterator<SectionVO> sectionListItr = null;
		
		ArrayList<RuleSet> ruleSetList = null;
		RuleSet ruleSet = null;
		ArrayList<Fact> factList = null;
		FidelityVO fidelityVO = null;
		
		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		double sumInsuredPerLocFID = 0 ;
		double sumInsuredFID =0 ;
		
		
		
		if( Utils.isEmpty( ruleInfo ) ){
			ruleInfo = new RuleInfo();
		}

		if( !Utils.isEmpty( policyVO ) ){
			
			
			SectionVO fidSection = getSection(SvcConstants.SECTION_ID_FID, policyVO);
			
				
			if( !Utils.isEmpty( fidSection ) )
			{
				if( !Utils.isEmpty( fidSection.getCommission())) 
				{
					commissionDiff = getCommissionDifference( fidSection.getCommission(), RulesConstants.FID_CLASS, policyVO );
				}
				ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
				riskGroupDetails = ( HashMap<RiskGroup, RiskGroupDetails>) fidSection.getRiskGroupDetails();
				
				for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() )
				{
					location = (LocationVO) riskGroupDetailsEntry.getKey();
					//ruleSet = new RuleSet();
					riskId = retrieveRiskId( location );

					if( !Utils.isEmpty( riskGroupDetailsEntry.getValue() ) )
					{
						fidelityVO = (FidelityVO) riskGroupDetailsEntry.getValue();
						List<FidelityNammedEmployeeDetailsVO> fidNammedEmpVOs = fidelityVO.getFidelityEmployeeDetails();
						List<FidelityUnnammedEmployeeVO> fidUnnammedEmpVOs = fidelityVO.getUnnammedEmployeeDetails();
						
						for(FidelityNammedEmployeeDetailsVO fidNammedEmpVO : fidNammedEmpVOs){
							
							if(!(Utils.isEmpty( fidNammedEmpVO.getLimitPerPerson() )) && ( location.getToSave() ) && 
									Utils.isEmpty(fidNammedEmpVO.getIsToBeDeleted()))
							{
								limitPerPerson += fidNammedEmpVO.getLimitPerPerson();
								noOfFidEmployees ++;
							}
							sumInsuredPerLocFID = limitPerPerson;
						}
						
						for(FidelityUnnammedEmployeeVO fidUnnammedEmpVO : fidUnnammedEmpVOs){
							if(!(Utils.isEmpty( fidUnnammedEmpVO.getLimitPerPerson())) && location.getToSave()&&Utils.isEmpty(fidUnnammedEmpVO.getIsToBeDeleted()))
									limitPerPerson += fidUnnammedEmpVO.getLimitPerPerson();
							
							if(!Utils.isEmpty( fidUnnammedEmpVO.getTotalNumberOfEmployee()) && 
									Utils.isEmpty(fidUnnammedEmpVO.getIsToBeDeleted()))
							{
								noOfFidEmployees += fidUnnammedEmpVO.getTotalNumberOfEmployee();
								/**
								 * Adding multiplication to number of employees to limit need confirmation 
								 */
								sumInsuredPerLocFID +=( fidUnnammedEmpVO.getTotalNumberOfEmployee()*  fidUnnammedEmpVO.getLimitPerPerson());
								
							}
//							sumInsuredPerLocFID = limitPerPerson;
							
						}
						sumInsuredFID = sumInsuredFID + sumInsuredPerLocFID;
					}
					
					
					
				}
					ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
					ruleSet = new RuleSet();
					ruleSet.setRuleSetName( RulesConstants.FID );
					ruleSet.setRiskId( riskId );

					factList = (ArrayList<Fact>) ruleSet.getFact();

					Fact userFactor = new Fact();
					userFactor.setFactName( RulesConstants.FACT_USER );
					ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

					RuleAttribute roleAttribute = new RuleAttribute();
					roleAttribute.setAttributeName( "role" );
					roleAttribute.setAttributeValue( roleType );
					userRuleAttributeList.add( roleAttribute );

					Fact fidFactor = new Fact();
					fidFactor.setFactName( RulesConstants.FACT_FID );
					ArrayList<RuleAttribute> fidRuleAttributeList = (ArrayList<RuleAttribute>) fidFactor.getRuleSetAttribute();

					RuleAttribute limitPerPersons = new RuleAttribute();
					limitPerPersons.setAttributeName( "limitPerPerson" );
					limitPerPersons.setAttributeValue( new BigDecimal( decForm.format( limitPerPerson ) ).toString() );
					fidRuleAttributeList.add( limitPerPersons );
					
					RuleAttribute noOfFidEmployeesAttribute = new RuleAttribute();
					noOfFidEmployeesAttribute.setAttributeName( "maxNumberOfEmp" );
					//noOfFidEmployeesAttribute.setAttributeValue( new Integer(  noOfFidEmployees ).toString() );
					noOfFidEmployeesAttribute.setAttributeValue( Integer.valueOf(  noOfFidEmployees ).toString() );
					fidRuleAttributeList.add( noOfFidEmployeesAttribute );

					//Modified as a part of ticket id 149213
	                if(! (BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ) ){

	                	/*Commented - UAT feedback - Ticket Id - 149213*/
	                	/*RuleAttribute sumInsuredperlocationFG = new RuleAttribute();
	                	sumInsuredperlocationFG.setAttributeName( "sumInsuredperlocationFG" );
	                	sumInsuredperlocationFG.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLocFID ) ).toString() );  // 149213 new rule value need to set
	                	fidRuleAttributeList.add( sumInsuredperlocationFG );*/
						
						RuleAttribute sumInsuredCheckFG = new RuleAttribute();
						sumInsuredCheckFG.setAttributeName( "sumInsuredCheckFG" );
						sumInsuredCheckFG.setAttributeValue( new BigDecimal( decForm.format( sumInsuredFID ) ).toString() );    // 149213 new rule value need to set
						fidRuleAttributeList.add( sumInsuredCheckFG );
	                }
					
	                //* Adding code change to get JLT web service limit for referral case
                    
                    String schemeCode="";
                    Date preparedDate=new Date();
                    if(!Utils.isEmpty(policyVO.getScheme())){
                          schemeCode = policyVO.getScheme().getSchemeCode().toString();
                    }
                    if(!Utils.isEmpty(policyVO.getCreated())){
                          preparedDate = policyVO.getCreated();
                    }
                    SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_HYPHEN);
                    String d2 = Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_LIVEDATE);
                    Date JLTLiveDate=null;
                    try {
                          JLTLiveDate = s2.parse(d2);
                    } catch (ParseException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                    }
                    boolean isValidSceheme= SvcUtils.isValidSchemeCode(policyVO);
                    if (isValidSceheme
                          && JLTLiveDate.compareTo(preparedDate) <= 0 && SvcConstants.DUBAI == Integer
                                 .parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))) {

                          double jlt_fidelity_limit = Double.valueOf(Utils.getSingleValueAppConfig("JLT_FIDELITY_LIMIT"));
                          
                          if (!policyVO.getIsPrepackaged() && limitPerPerson > jlt_fidelity_limit) {
                          // Underwriting questions rules request mapper 
                                 Fact uwQuestionFactor = getUWQFact(RulesConstants.FACT_FG_UW_Question,
                                        RulesConstants.FID_SECTION_ID, fidelityVO);
                                 if (!Utils.isEmpty(uwQuestionFactor.getRuleSetAttribute())) {
                                 factList.add(uwQuestionFactor);
                                 }
                          }
                    } else {
					
					if( !policyVO.getIsPrepackaged() && limitPerPerson > FIDELITY_SECTION_ID)
					{
						 /* Underwriting questions rules request mapper */
                      	Fact uwQuestionFactor = getUWQFact( RulesConstants.FACT_FG_UW_Question, RulesConstants.FID_SECTION_ID, fidelityVO);
                      	if(!Utils.isEmpty( uwQuestionFactor.getRuleSetAttribute() ))
                      	{
                      		factList.add( uwQuestionFactor );
                      	}
                     }
                    }
					
					
					factList.add( userFactor );
					factList.add( fidFactor );
					/*
					 * Added as a part of ticket id 149213
					 */
	                if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
						Fact commonFactor = new Fact();
						commonFactor.setFactName( RulesConstants.FACT_COMMON );
						ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();

						RuleAttribute commissionAttribute = new RuleAttribute();
						commissionAttribute.setAttributeName( com.Constant.CONST_COMMISSIONDIFFERENCE );
						commissionAttribute.setAttributeValue( new BigDecimal( decForm.format( commissionDiff ) ).toString() );
						commonRuleAttributeList.add( commissionAttribute );
						factList.add( getCommonFactor( policyVO, location.getRiskGroupId(), commonFactor, section ) );
					}
					ruleSetList.add( ruleSet );
			}
		}
		
		return ruleInfo;
	}
	
	private SectionVO getSection(int sectionId, PolicyVO policyVO) {
		
		SectionVO finderSection = new SectionVO(RiskGroupingLevel.LOCATION);
		finderSection.setSectionId(sectionId);
		List<SectionVO> sectionDetails = policyVO.getRiskDetails();
		SectionVO section =  sectionDetails.get(sectionDetails.indexOf(finderSection));
		
		return section;
	}
	
	private boolean isParSectionPresentinPolicy(PolicyVO policyVO)
	{
		boolean isPresent = true;
		SectionVO finderSection = new SectionVO(RiskGroupingLevel.LOCATION);
		finderSection.setSectionId(1);
		List<SectionVO> sectionDetails = policyVO.getRiskDetails();
		if(sectionDetails.indexOf(finderSection) == -1)
		{
			isPresent = false;
		}
		
		return isPresent;
	}

	
	
		private Double getDisLoadPer( BaseVO policyBaseVO ){
		return ( (PolicyVO) policyBaseVO ).getPremiumVO().getDiscOrLoadPerc();
	}
	
	
	/**
     * Underwriting questions rules request mapper <br> 
     * 1. Each question is passed as one rule attribute with<br> 
     *              a. Attribute Name  = <code> factName + sectionId + "_" + questionVO.getQId() </code><br> 
     *              b. Attribute Value = <code> questionVO.getResponse() </code><br> 
     * @param factName
     * @param sectionId
     * @param locationO
     * @return UWQuestions fact
     */
    private Fact getUWQFact(String factName, String sectionId, RiskGroupDetails locationVO){
    		
    		Fact uwQuestionFactor = new Fact();
            uwQuestionFactor.setFactName( factName );
            ArrayList<RuleAttribute> uwQuestionsRulesAttributeList = ( ArrayList<RuleAttribute> ) uwQuestionFactor.getRuleSetAttribute();
            UWQuestionsVO uwQuestionsVO = locationVO.getUwQuestions();                                                      
            
            if( !Utils.isEmpty( uwQuestionsVO )){
            		//Start Modified by Mindtree on 01/09/2015 for CR:104256 – Student Liability CR
            		boolean isStudentLiabilityUQPresent = false;
            		boolean isRCCConstructionUQPresent = false;
                    for( UWQuestionVO questionVO : uwQuestionsVO.getQuestions() )
                    {
	                    	if(String.valueOf( SvcConstants.SECTION_ID_PL ).equals(sectionId) && questionVO.getQId() ==  SvcConstants.UW_GENERAL_QUESTION_STUDENT_LIABILITY ){
	                    		isStudentLiabilityUQPresent = true;
	                    	}
	                    	
	                    	if(String.valueOf( SvcConstants.SECTION_ID_PAR ).equals(sectionId) && questionVO.getQId() ==  SvcConstants.UW_GENERAL_QUESTION_RCC_CONSTRUCTION ){
	                    		isRCCConstructionUQPresent = true;
	                    	}
	                    	
	                    	//For Ticket 149213
	                    	// Added as a part of ticket id 149213
	                    	if(String.valueOf( SvcConstants.SECTION_ID_PAR ).equals(sectionId) && questionVO.getQId()!=2 && !  (BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ) ){
	                    		RuleAttribute attribute = new RuleAttribute();
	                            attribute.setAttributeName( RulesConstants.UWQUESTION_ATTR_NAME_PREFIX + sectionId + "_" + questionVO.getQId().toString() );
	            				/* Skip the question for which is no answer*/
	                            if( !Utils.isEmpty( questionVO.getResponseType() ) && questionVO.getResponseType().equals( UWQuestionRespType.TEXT ) )
	                            {
	            					continue;
	            				}
	                            else if(!Utils.isEmpty( questionVO.getResponse() )  )
	                            {
	                            	attribute.setAttributeValue( questionVO.getResponse() );
	                                uwQuestionsRulesAttributeList.add( attribute );
	                            }
	                    	}
	                    	else if( (BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ) && String.valueOf( SvcConstants.SECTION_ID_PAR ).equals(sectionId) ){
	                    		RuleAttribute attribute = new RuleAttribute();
	                            attribute.setAttributeName( RulesConstants.UWQUESTION_ATTR_NAME_PREFIX + sectionId + "_" + questionVO.getQId().toString() );
	            				/* Skip the question for which is no answer*/
	                            if( !Utils.isEmpty( questionVO.getResponseType() ) && questionVO.getResponseType().equals( UWQuestionRespType.TEXT ) )
	                            {
	            					continue;
	            				}
	                            else if(!Utils.isEmpty( questionVO.getResponse() )  )
	                            {
	                            	attribute.setAttributeValue( questionVO.getResponse() );
	                                uwQuestionsRulesAttributeList.add( attribute );
	                            }
	                    	}
	                    	if(! String.valueOf( SvcConstants.SECTION_ID_PAR ).equals(sectionId)){
	                    		RuleAttribute attribute = new RuleAttribute();
	                            attribute.setAttributeName( RulesConstants.UWQUESTION_ATTR_NAME_PREFIX + sectionId + "_" + questionVO.getQId().toString() );
	            				/* Skip the question for which is no answer*/
	                            if( !Utils.isEmpty( questionVO.getResponseType() ) && questionVO.getResponseType().equals( UWQuestionRespType.TEXT ) )
	                            {
	            					continue;
	            				}
	                            else if(!Utils.isEmpty( questionVO.getResponse() )  )
	                            {
	                            	attribute.setAttributeValue( questionVO.getResponse() );
	                                uwQuestionsRulesAttributeList.add( attribute );
	                            }
	                    	}
	                    	
                            
                    }
                    
                    if(String.valueOf( SvcConstants.SECTION_ID_PL ).equals(sectionId) && !isStudentLiabilityUQPresent){
                    	 RuleAttribute attribute = new RuleAttribute();
                         attribute.setAttributeName( RulesConstants.UWQUESTION_ATTR_NAME_PREFIX + sectionId + "_57" );
                         attribute.setAttributeValue( "no");
                         uwQuestionsRulesAttributeList.add( attribute );
                    }
                    if(String.valueOf( SvcConstants.SECTION_ID_PAR ).equals(sectionId) && !isRCCConstructionUQPresent){
                   	 RuleAttribute attribute = new RuleAttribute();
                        attribute.setAttributeName( RulesConstants.UWQUESTION_ATTR_NAME_PREFIX + sectionId + "_7" );
                        attribute.setAttributeValue( "no");
                        uwQuestionsRulesAttributeList.add( attribute );
                   }
                    //End Modified by Mindtree on 01/09/2015 for CR:104256 – Student Liability CR
            }
            return uwQuestionFactor;

    }


    /**
     * 
      * @param baseVO
     * @param section
     * @param roleType
     * @param ruleInfo
     * @return
     */
     private RuleInfo createRuleInfoForIssueOrEditQuote( BaseVO baseVO, int section, String roleType, RuleInfo ruleInfo ){
           ArrayList<RuleSet> ruleSetList = null;
           RuleSet ruleSet = null;
           ArrayList<Fact> factList = null;
           /** There is no Risk Id for edit quote in PAS and 
            * there will never be multiple locations for General Info section.
           * Setting a dummy id as risk id is a mandatory field in Rules Engine
           * for response mapping. 
            */
           String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_DISC_LOAD );
           PolicyVO policyVO = (PolicyVO) baseVO;

           if( Utils.isEmpty( ruleInfo ) ){
                 ruleInfo = new RuleInfo();
           }

           if( !Utils.isEmpty( policyVO ) ){

                 ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
                 ruleSet = new RuleSet();
                 ruleSet.setRuleSetName( RulesConstants.QUO );
                 ruleSet.setRiskId( riskId );

                 factList = (ArrayList<Fact>) ruleSet.getFact();
                 
                 Fact userFactor = new Fact();
                 userFactor.setFactName( RulesConstants.FACT_USER );
                 ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();
                 /*	
                  * Rule change for Bahrain - 
                 	RSA User (irrespective of the license) should be able to issue Direct quote with out credit limit validation. 
                 	Only when broker policy is issued either by a broker user or by RSA user then this validation should fire.
                 */
                 if( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equals( "50" ) )
                 {
                	 if( roleType.contains( "RSA_USER" ) &&  !Utils.isEmpty( policyVO.getGeneralInfo() ) 
                      		 && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() ) && Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ){
                		 roleType = "RSA_USER_3";
                	 }
                 }
                 RuleAttribute roleAttribute = new RuleAttribute();
                 roleAttribute.setAttributeName( "role" );
                 roleAttribute.setAttributeValue( roleType );
                 userRuleAttributeList.add( roleAttribute ); 
                 
                 
                 
                 if( RuleContext.ISSUE_QUOTE.equals(policyVO.getRuleContext()))
                 {
	                 Fact issueQuoteFactor = new Fact();
	                 issueQuoteFactor.setFactName( RulesConstants.FACT_CLAIMS_ENDORSEMENT );
	                 ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) issueQuoteFactor.getRuleSetAttribute();
	                 
	               //Modified as a part of ticket id 149213
						if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
	                	 RuleAttribute issueQuoteAttr = new RuleAttribute();
	                	 issueQuoteAttr.setAttributeName( "claimsExist" );
	                	 issueQuoteAttr.setAttributeValue( ( policyVO.isClaimsHistoryExistInMissippi())? "Y" : "N" );
	
	                	 rctRuleAttributeList.add( issueQuoteAttr );
	                 }
	                 factList.add( issueQuoteFactor );
                 }
                 else if( RuleContext.QUOTE_PREMIUM.equals(policyVO.getRuleContext()))
                 {
	                 Fact quotePremiumFactor = new Fact();
	                 quotePremiumFactor.setFactName( RulesConstants.FACT_QUOTE_PREMIUM );
	                 ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) quotePremiumFactor.getRuleSetAttribute();
	
	                 RuleAttribute quotePremiumAttr = new RuleAttribute();
	                 quotePremiumAttr.setAttributeName( "quotePremium" );
	                 quotePremiumAttr.setAttributeValue( new BigDecimal( decForm.format(Math.round( SvcUtils.getTotalPremium( policyVO )*100.0)/100.0) ).toString());
	
	                 rctRuleAttributeList.add( quotePremiumAttr );
	                 factList.add( quotePremiumFactor );
                 }else if (RuleContext.CUMULATIVE_LOSS_QUANTUM.equals(policyVO.getRuleContext())) {
     				
     				//Added for JLT
     					
     					Fact cumulativeLossQuantumFactor = new Fact();
     					cumulativeLossQuantumFactor.setFactName(RulesConstants.FACT_CUMULATIVE_LOSS_QUANTUM);
     					ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) cumulativeLossQuantumFactor
     							.getRuleSetAttribute();

     						RuleAttribute cumulativeLossQuantumAttr = new RuleAttribute();
     						cumulativeLossQuantumAttr.setAttributeName("checkCumulativeLoss");
     						cumulativeLossQuantumAttr.setAttributeValue("Y");

     						rctRuleAttributeList.add(cumulativeLossQuantumAttr);
     						factList.add(cumulativeLossQuantumFactor);
     			}              
                 else
                 {
	                 Fact editQuoteFactor = new Fact();
	                 editQuoteFactor.setFactName( RulesConstants.FACT_EDIT_QUOTE );
	                 ArrayList<RuleAttribute> rctRuleAttributeList = (ArrayList<RuleAttribute>) editQuoteFactor.getRuleSetAttribute();
	
	               //Modified as a part of ticket id 149213
						if(BAHRAIN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC )) || OMAN_LOC.equals(Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
	 	            	RuleAttribute editQuoteAttr = new RuleAttribute();
		                 editQuoteAttr.setAttributeName( "allowEditQuote" );
		                 editQuoteAttr.setAttributeValue( "Y" );
		
		                 rctRuleAttributeList.add( editQuoteAttr );
		                 factList.add( editQuoteFactor );
	 	             }
                 }
                 policyVO.setRuleContext(null);
                 
                 factList.add( userFactor );
                 ruleSetList.add( ruleSet );                 
                 
           }

           return ruleInfo;
     }
	
     
     private RuleInfo createRuleInfoForIssueQuoteForBroker ( BaseVO baseVO, int section, String roleType, RuleInfo ruleInfo )
     {

         ArrayList<RuleSet> ruleSetList = null;
         RuleSet ruleSet = null;
         ArrayList<Fact> factList = null;
         SchemeVO schemeVO = null;
 		Date effDate = null;
 		Calendar cal = Calendar.getInstance();
 		cal.set( Calendar.HOUR_OF_DAY, 0 );
 		cal.set( Calendar.MINUTE, 0 );
 		cal.set( Calendar.SECOND, 0 );
 		cal.set( Calendar.MILLISECOND, 0 );
 		//Date today = cal.getTime();
         
 		
         String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_DISC_LOAD );

         PolicyVO policyVO = (PolicyVO) baseVO;
         Integer brCode = 0;
         if(!Utils.isEmpty(((UserProfile)policyVO.getLoggedInUser()).getRsaUser().getBrokerId())){
        	 brCode = ((UserProfile)policyVO.getLoggedInUser()).getRsaUser().getBrokerId();
         }
         
         if( Utils.isEmpty( ruleInfo ) ){
               ruleInfo = new RuleInfo();
         }

         if( !Utils.isEmpty( policyVO ) )
         {

            ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
            ruleSet = new RuleSet();
            ruleSet.setRuleSetName( RulesConstants.RIS );
            ruleSet.setRiskId( riskId );

            factList = (ArrayList<Fact>) ruleSet.getFact();
               
            Fact userFactor = new Fact();
   			userFactor.setFactName( RulesConstants.FACT_USER );
   			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

   			RuleAttribute roleAttribute = new RuleAttribute();
   			roleAttribute.setAttributeName( "role" );
   			roleAttribute.setAttributeValue( roleType );
   			userRuleAttributeList.add( roleAttribute );
   			
   			Fact issueQuoFactor = new Fact();
   			issueQuoFactor.setFactName( RulesConstants.ISSUE_QUO_REF );
 			ArrayList<RuleAttribute> issQuoAttributeList = (ArrayList<RuleAttribute>) issueQuoFactor.getRuleSetAttribute();
   			RuleAttribute brokerCodeAttribute = new RuleAttribute();
   			brokerCodeAttribute.setAttributeName( "brokerCode" );
   			brokerCodeAttribute.setAttributeValue( brCode.toString() );
   			issQuoAttributeList.add(brokerCodeAttribute);

   			factList.add( userFactor );
   			factList.add( issueQuoFactor );
   			ruleSetList.add( ruleSet );
               
         }

         return ruleInfo;
    	 
     }
    
    private RuleInfo createRuleInfoForConvToPolicy( BaseVO baseVO, int section, String roleType, RuleInfo ruleInfo )
    {
        ArrayList<RuleSet> ruleSetList = null;
        RuleSet ruleSet = null;
        ArrayList<Fact> factList = null;
        SchemeVO schemeVO = null;
		//AuthenticationInfoVO authInfoVO = null;
		Date effDate = null;
		long effectiveDateBackdating = 0;
		long effectiveDatePostdating = 0;
		double brokerCreditLimit = 0;
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );
		Date today = cal.getTime();
		
        String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_DISC_LOAD );

        PolicyVO policyVO = (PolicyVO) baseVO;

        if( Utils.isEmpty( ruleInfo ) ){
              ruleInfo = new RuleInfo();
        }

        if( !Utils.isEmpty( policyVO ) )
        {

            ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
            ruleSet = new RuleSet();
            ruleSet.setRuleSetName( RulesConstants.CHK_EFF_DATE );
            ruleSet.setRiskId( riskId );

            factList = (ArrayList<Fact>) ruleSet.getFact();
            //authInfoVO = policyVO.getAuthInfoVO();
            schemeVO = policyVO.getScheme();
            if( !Utils.isEmpty( schemeVO ) )
			{
				effDate = schemeVO.getEffDate();
			}

			if (!Utils.isEmpty(effDate)) {
				
				if (today.after(effDate)) {
					effectiveDateBackdating = getDateDifference(today, effDate);
				}

				if (effDate.after(today)) {
					effectiveDatePostdating = getDateDifference(effDate,today);
				}
			}
            
			/*PremiumVO premiumVo = policyVO.getPremiumVO();
			
			if(!Utils.isEmpty( premiumVo )){
				GeneralInfoVO generalInfoVO = policyVO.getGeneralInfo();
				
				if(!Utils.isEmpty( generalInfoVO )){
					SourceOfBusinessVO sourceOfBusinessVO = generalInfoVO.getSourceOfBus();
					
					if(!Utils.isEmpty( sourceOfBusinessVO )){
						Integer brokerName = sourceOfBusinessVO.getBrokerName();
						
						if(!Utils.isEmpty( brokerName )){
							brokerCreditLimit = SvcUtils.getBrokerCreditLimit(premiumVo.getPremiumAmt(),brokerName);
						}
					}
				}
			}*/
			
			
			
            Fact userFactor = new Fact();
  			userFactor.setFactName( RulesConstants.FACT_USER );
  			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

  			RuleAttribute roleAttribute = new RuleAttribute();
  			roleAttribute.setAttributeName( "role" );
  			roleAttribute.setAttributeValue( roleType );
  			userRuleAttributeList.add( roleAttribute );
  			
  			//Modified as a part of ticket id 149213
  			
  			
  			
            	 
            	Fact effDateFactor = new Fact();
            	effDateFactor.setFactName( RulesConstants.CONV_TO_POLICY );
      			
      			ArrayList<RuleAttribute> effDateAttributeList = (ArrayList<RuleAttribute>) effDateFactor.getRuleSetAttribute();
      			RuleAttribute effectiveDatePostdatingAttribute = new RuleAttribute();
            	
       			RuleAttribute effectiveDateBackdatingAttribute = new RuleAttribute();
       			effectiveDateBackdatingAttribute.setAttributeName( "effectiveDateBackdating" );
       			effectiveDateBackdatingAttribute.setAttributeValue( String.valueOf( effectiveDateBackdating ) );
       			effDateAttributeList.add(effectiveDateBackdatingAttribute);

       			
       			effectiveDatePostdatingAttribute.setAttributeName( "effectiveDatePostdating" );
       			effectiveDatePostdatingAttribute.setAttributeValue( String.valueOf( effectiveDatePostdating ) );
       			effDateAttributeList.add(effectiveDatePostdatingAttribute);
            
       			factList.add( effDateFactor );
       			
  			
            /*RuleAttribute brokerMinCreditLimitAttribute = new RuleAttribute();
   			brokerMinCreditLimitAttribute.setAttributeName( "brokerMinCreditLimit" );
   			brokerMinCreditLimitAttribute.setAttributeValue( String.valueOf( effectiveDatePostdating ) );
   			effDateAttributeList.add(effectiveDatePostdatingAttribute);
   			
   			RuleAttribute brokerMaxCreditLimitAttribute = new RuleAttribute();
   			brokerMaxCreditLimitAttribute.setAttributeName( "brokerMaxCreditLimit" );
   			brokerMaxCreditLimitAttribute.setAttributeValue( String.valueOf( effectiveDatePostdating ) );
   			effDateAttributeList.add(effectiveDatePostdatingAttribute);*/
            
  			factList.add( userFactor );
  			
  			ruleSetList.add( ruleSet );
              
        }

        return ruleInfo;
    }
    
    /**
     * Method to create Fact for credit limit rule.
     * 
     * @param baseVO
     * @param section
     * @param roleType
     * @param ruleInfo
     * @return
     */
    private RuleInfo createRuleInfoForCreditLimit( PolicyVO baseVO, int section, String roleType, RuleInfo ruleInfo ){

        ArrayList<RuleSet> ruleSetList = null;
        RuleSet ruleSet = null;
        ArrayList<Fact> factList = null;
        SchemeVO schemeVO = null;
		
		double brokerCreditLimit = 0;
		double premium = 0;
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );
		//Date today = cal.getTime();
        
		
        String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_DISC_LOAD );

        PolicyVO policyVO = (PolicyVO) baseVO;

        if( Utils.isEmpty( ruleInfo ) ){
              ruleInfo = new RuleInfo();
        }

        if( !Utils.isEmpty( policyVO ) )
        {

            ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
            ruleSet = new RuleSet();
            ruleSet.setRuleSetName( RulesConstants.CREDIT_LIMIT );
            ruleSet.setRiskId( riskId );

            factList = (ArrayList<Fact>) ruleSet.getFact();
           
            
			PremiumVO premiumVo = policyVO.getPremiumVO();
			
			List<EndorsmentVO> endorsements = policyVO.getEndorsements();
			
			if(!Utils.isEmpty( endorsements )){
				premium = endorsements.get( 0 ).getPremiumVO().getPremiumAmt() - endorsements.get( 0 ).getOldPremiumVO().getPremiumAmt();
			} else {
				if(!Utils.isEmpty( premiumVo )){
					premium = premiumVo.getPremiumAmt();
				}
			}
			
			
			GeneralInfoVO generalInfoVO = policyVO.getGeneralInfo();
			
			if(!Utils.isEmpty( generalInfoVO )){
				SourceOfBusinessVO sourceOfBusinessVO = generalInfoVO.getSourceOfBus();
				
				if(!Utils.isEmpty( sourceOfBusinessVO )){
					Integer brokerName = sourceOfBusinessVO.getBrokerName();
					
					if(!Utils.isEmpty( brokerName )){
						brokerCreditLimit = SvcUtils.getBrokerCreditLimitPercentage(premium,brokerName);
					}
				}
			}
			
			
			BigDecimal brokerLimit =  new BigDecimal(Currency.getFormattedCurrency( brokerCreditLimit ).replace(",", "") ) ;
			
			if(!Utils.isEmpty(brokerLimit)){
				String brokerLimitStr = brokerLimit.toString();
				if(brokerLimitStr.indexOf(".") == -1){
					brokerLimitStr = brokerLimitStr + ".00";
					brokerLimit = new BigDecimal(brokerLimitStr);
				}
			}
			
            Fact userFactor = new Fact();
  			userFactor.setFactName( RulesConstants.FACT_USER );
  			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

  			RuleAttribute roleAttribute = new RuleAttribute();
  			roleAttribute.setAttributeName( "role" );
  			roleAttribute.setAttributeValue( roleType );
  			userRuleAttributeList.add( roleAttribute );
  			
  			Fact effDateFactor = new Fact();
  			effDateFactor.setFactName( RulesConstants.FACT_CREDIT_LIMIT );
			ArrayList<RuleAttribute> effDateAttributeList = (ArrayList<RuleAttribute>) effDateFactor.getRuleSetAttribute();
			

  			
  			
  			RuleAttribute brokerMinCreditLimitAttribute = new RuleAttribute();
  			brokerMinCreditLimitAttribute.setAttributeName( "brokerMinCreditLimit" );
  			
  			if(brokerLimit.compareTo( new BigDecimal( 100 ) ) == -1 || brokerLimit.compareTo( new BigDecimal( 100 ) ) == 0) {
  				brokerMinCreditLimitAttribute.setAttributeValue( String.valueOf(brokerLimit) );
        	}else{
        		brokerMinCreditLimitAttribute.setAttributeValue( "0.00" );
        	}
  			
  			effDateAttributeList.add(brokerMinCreditLimitAttribute);
  			
  			RuleAttribute brokerMaxCreditLimitAttribute = new RuleAttribute();
  			brokerMaxCreditLimitAttribute.setAttributeName( "brokerMaxCreditLimit" );
  			brokerMaxCreditLimitAttribute.setAttributeValue( String.valueOf(brokerLimit) );
  			effDateAttributeList.add(brokerMaxCreditLimitAttribute);
  			
  			factList.add( userFactor );
  			factList.add( effDateFactor );
  			ruleSetList.add( ruleSet );
              
        }

        return ruleInfo;
    
	}
    
    //Commented the method to avoid Performance - Private method is never called sonar violation on 21-9-2017

   private Fact getCommonFactor( PolicyVO policyVO, String riskGroupId, Fact commonFactor ){
		
		ArrayList<RuleAttribute> commonRuleAttributeList = (ArrayList<RuleAttribute>) commonFactor.getRuleSetAttribute();
		
		double sumInsuredPerLoc = getTotalSumInsured( policyVO, riskGroupId , true );
		double sumInsuredPerPolicy = getTotalSumInsured( policyVO, riskGroupId, false );

		RuleAttribute maxSIPerLocAttribute = new RuleAttribute();
		maxSIPerLocAttribute.setAttributeName( "maxSIPerLoc" );
		maxSIPerLocAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerLoc ) ).toString() );
		commonRuleAttributeList.add( maxSIPerLocAttribute );
		
		RuleAttribute maxSIPerPolicyAttribute = new RuleAttribute();
		maxSIPerPolicyAttribute.setAttributeName( "maxSIPerPolicy" );
		maxSIPerPolicyAttribute.setAttributeValue( new BigDecimal( decForm.format( sumInsuredPerPolicy ) ).toString() );
		commonRuleAttributeList.add( maxSIPerPolicyAttribute );
		
		return commonFactor;
	}
    
    /*
	 * This Method is used to get the total SI per location across all sections.
	 */
	private Double getTotalSumInsured( PolicyVO policyVO, String basicRiskId, boolean isPerLoc ){

		double totalSIPerLoc = 0.0;
		double totalSIPerPolicy = 0.0;
		LocationVO location = null;
		RiskGroupDetails locationDetail = null;

		if( !Utils.isEmpty( policyVO ) ){
			List<SectionVO> sectionVOs = policyVO.getRiskDetails();

			for( SectionVO section : sectionVOs ){

				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = section.getRiskGroupDetails();
				if( !Utils.isEmpty( riskGroupDetails ) ){
					for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
						if( !Utils.isEmpty( riskGroupDetailsEntry ) ){
							location = (LocationVO) riskGroupDetailsEntry.getKey();
							locationDetail = riskGroupDetailsEntry.getValue();
							
							/*
							 * Calculate location SI only if basic risk ids match. ie if null is passed as value of basicRiskId, in policyvo also it should be null, else 
							 * both should be equal. In case of policy SI, need not match basic risk ids. 
							 * */
							if( isPerLoc && ( ( !Utils.isEmpty( basicRiskId )
									&& !Utils.isEmpty( location ) && !Utils.isEmpty( location.getRiskGroupId() ) && basicRiskId.equals( location.getRiskGroupId() ) ) 
									|| ( Utils.isEmpty( basicRiskId ) && !Utils.isEmpty( location ) && Utils.isEmpty( location.getRiskGroupId() ) ) ) ){
								totalSIPerLoc += getSIForLocation( locationDetail, section.getSectionId(), policyVO );
							}
							else if( !isPerLoc ){
								totalSIPerPolicy += getSIForLocation( locationDetail, section.getSectionId(), policyVO );
							}
						}
					}

				}
			}
		}

		if( isPerLoc ){
			return totalSIPerLoc;
		}
		else{
			return totalSIPerPolicy;
		}

	}

	/*
	 * This Method is used to get the total SI per location across all sections.
	 */
	private Double getTotalSumInsured( PolicyVO policyVO, String basicRiskId, int sectionId, boolean isPerLoc ){

		double totalSIPerLoc = 0.0;
		double totalSIPerPolicy = 0.0;
		LocationVO location = null;
		RiskGroupDetails locationDetail = null;

		if( !Utils.isEmpty( policyVO ) ){
			List<SectionVO> sectionVOs = policyVO.getRiskDetails();

			for( SectionVO section : sectionVOs ){

				/*if( sectionId == section.getSectionId() ){
					break;
				}*/
				
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = section.getRiskGroupDetails();
				if( !Utils.isEmpty( riskGroupDetails ) ){
					for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){
						if( !Utils.isEmpty( riskGroupDetailsEntry ) ){
							location = (LocationVO) riskGroupDetailsEntry.getKey();
							locationDetail = riskGroupDetailsEntry.getValue();
							
							/*
							 * Calculate location SI only if basic risk ids match. ie if null is passed as value of basicRiskId, in policyvo also it should be null, else 
							 * both should be equal. In case of policy SI, need not match basic risk ids. 
							 * */
							if( isPerLoc && ( ( !Utils.isEmpty( basicRiskId )
									&& !Utils.isEmpty( location ) && !Utils.isEmpty( location.getRiskGroupId() ) && basicRiskId.equals( location.getRiskGroupId() ) ) 
									|| ( Utils.isEmpty( basicRiskId ) && !Utils.isEmpty( location ) && Utils.isEmpty( location.getRiskGroupId() ) ) ) ){
								totalSIPerLoc += getSIForLocation( locationDetail, section.getSectionId(), policyVO );
							}
							else if( !isPerLoc ){
								totalSIPerPolicy += getSIForLocation( locationDetail, section.getSectionId(), policyVO );
							}
						}
					}

				}
			}
		}

		if( isPerLoc ){
			return totalSIPerLoc;
		}
		else{
			return totalSIPerPolicy;
		}

	}

	private double getSIForLocation( RiskGroupDetails locationDetail, Integer sectionId, PolicyVO policyVO ){

		double sumInsured = 0.0;

		switch( sectionId ){
			case 1:
				sumInsured = getParSI( (ParVO) locationDetail );
				break;
			
			case 2:
				sumInsured = getBISI( (BIVO)locationDetail );
				break;
				
			case 3:
				sumInsured = getMBSI((MBVO)locationDetail );
				break;
				
			case 4:
				sumInsured = getDosSI((DeteriorationOfStockVO) locationDetail);
				break;
				
			case 5:
				sumInsured = getEESI((EEVO)locationDetail );
				break;
				
			case 6:
				sumInsured = getPLSI((PublicLiabilityVO) locationDetail , policyVO );
				break;
			
			case 7:
				sumInsured = getWCSI((WCVO) locationDetail, sectionId, policyVO );
				break;
				
			case 8:
				sumInsured = getMoneySI( (MoneyVO) locationDetail, sectionId, policyVO );
				break;
				
			case 9:
				sumInsured = getFGSI((FidelityVO) locationDetail );
				break;
				
			case 10:
				sumInsured = getTBSI((TravelBaggageVO)locationDetail);
				break;
			 
			case 11:
				sumInsured = getGITSI((GoodsInTransitVO)locationDetail);
				break;
				
			case 12:
				sumInsured = getGPASI((GroupPersonalAccidentVO)locationDetail);
				break;
				
			default:
				break;
		}
		return sumInsured;
	}

	private double getGPASI( GroupPersonalAccidentVO groupPersonalAccidentVO ){
		double totalSI = 0;
		if( !Utils.isEmpty( groupPersonalAccidentVO ) ){
			List<GPANammedEmpVO> gpaNammedEmpVOs = groupPersonalAccidentVO.getGpaNammedEmpVO();
			List<GPAUnnammedEmpVO> gpaUnnammedEmpVOs = groupPersonalAccidentVO.getGpaUnnammedEmpVO();

			for( GPANammedEmpVO gpaNammedEmpVO : gpaNammedEmpVOs ){

				if( !( Utils.isEmpty( gpaNammedEmpVO.getSumInsuredDetails() ) ) && Utils.isEmpty( gpaNammedEmpVO.getIsToBeDeleted() ) ){
					totalSI = gpaNammedEmpVO.getSumInsuredDetails().getSumInsured();

				}

			}
			for( GPAUnnammedEmpVO gpaUnnammedEmpVO : gpaUnnammedEmpVOs ){
				if( !Utils.isEmpty( gpaUnnammedEmpVO.getSumInsuredDetails() ) && Utils.isEmpty( gpaUnnammedEmpVO.getIsToBeDeleted() ) ){
					totalSI = gpaUnnammedEmpVO.getSumInsuredDetails().getSumInsured();
				}
			}

		}
		return totalSI;
	}

	private double getGITSI( GoodsInTransitVO goodsInTransitVO ){
		double totalSI = 0;
		if( !Utils.isEmpty( goodsInTransitVO ) ){
			totalSI = goodsInTransitVO.getSingleTransitLimit();
		}
		return totalSI;
	}

	private double getFGSI( FidelityVO fidelityVO ){
		double totalSI = 0;

		if( !Utils.isEmpty( fidelityVO ) ){

			List<FidelityNammedEmployeeDetailsVO> fidNammedEmpVOs = fidelityVO.getFidelityEmployeeDetails();
			List<FidelityUnnammedEmployeeVO> fidUnnammedEmpVOs = fidelityVO.getUnnammedEmployeeDetails();

			for( FidelityNammedEmployeeDetailsVO fidNammedEmpVO : fidNammedEmpVOs ){

				if( !( Utils.isEmpty( fidNammedEmpVO.getLimitPerPerson() ) ) && Utils.isEmpty( fidNammedEmpVO.getIsToBeDeleted() ) ){
					totalSI += fidNammedEmpVO.getLimitPerPerson();
				}

			}

			for( FidelityUnnammedEmployeeVO fidUnnammedEmpVO : fidUnnammedEmpVOs ){
				if( !( Utils.isEmpty( fidUnnammedEmpVO.getLimitPerPerson() ) ) && Utils.isEmpty( fidUnnammedEmpVO.getIsToBeDeleted() ) )
					totalSI += fidUnnammedEmpVO.getLimitPerPerson();

			}

		}
		return totalSI;
	}

	private double getTBSI( TravelBaggageVO travelBaggageVO ){
		double totalSI = 0;
		if( !Utils.isEmpty( travelBaggageVO ) ){
			List<TravellingEmployeeVO> employeeVOs = travelBaggageVO.getTravellingEmpDets();
			//Iterator<TravellingEmployeeVO> iterator = employeeVOs.iterator();
			for( TravellingEmployeeVO employeeVO : employeeVOs ){
				if( !Utils.isEmpty( employeeVO ) && Utils.isEmpty( employeeVO.getIsToBeDeleted() ) ){
					SumInsuredVO sumInsuredVO = employeeVO.getSumInsuredDtl();
					if( !Utils.isEmpty( sumInsuredVO ) ){
						totalSI += sumInsuredVO.getSumInsured();

					}
				}

			}
		}
		return totalSI;
	}

	private double getEESI( EEVO eevo ){

		double totalSI = 0;

		if( !Utils.isEmpty( eevo ) ){

			List<EquipmentVO> equipmentVOs = eevo.getEquipmentDtls();
			if( !Utils.isEmpty( equipmentVOs ) ){
				Iterator<EquipmentVO> equipDetsIter = equipmentVOs.iterator();

				while( equipDetsIter.hasNext() ){
					EquipmentVO equipmentVO = (EquipmentVO) equipDetsIter.next();
					if( !Utils.isEmpty( equipmentVO ) && Utils.isEmpty( equipmentVO.getIsToBeDeleted() ) ){
						SumInsuredVO sumInsuredVO = equipmentVO.getSumInsuredDetails();
						if( !Utils.isEmpty( sumInsuredVO ) ){
							totalSI += sumInsuredVO.getSumInsured();
						}
					}
				}
			}
		}
		return totalSI;
	}

	private double getDosSI( DeteriorationOfStockVO dosVO ){

		double totalSI = 0.0;

		if( !Utils.isEmpty( dosVO ) ){

			List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetailsVOs = dosVO.getDeteriorationOfStockDetails();
			for( DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO : deteriorationOfStockDetailsVOs ){
				if( !Utils.isEmpty( deteriorationOfStockDetailsVO ) ){
					if( !Utils.isEmpty( deteriorationOfStockDetailsVO.getSumInsuredDetails() )
							&& !Utils.isEmpty( deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured() )
							&& Utils.isEmpty( deteriorationOfStockDetailsVO.getIsToBeDeleted() ) ){
						totalSI += deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured();
					}
				}
			}
		}
		return totalSI;
	}

	private double getMBSI( MBVO mbVO ){

		double totalSI = 0;
		if( !Utils.isEmpty( mbVO ) ){

			List<MachineDetailsVO> machineDetailsVOs = mbVO.getMachineryDetails();
			if( !Utils.isEmpty( machineDetailsVOs ) ){
				Iterator<MachineDetailsVO> machineDetsIter = machineDetailsVOs.iterator();

				while( machineDetsIter.hasNext() ){
					MachineDetailsVO machineDetailsVO = (MachineDetailsVO) machineDetsIter.next();
					if( !Utils.isEmpty( machineDetailsVO ) && Utils.isEmpty( machineDetailsVO.getIsToBeDeleted() ) ){
						SumInsuredVO sumInsuredVO = machineDetailsVO.getSumInsuredVO();
						if( !Utils.isEmpty( sumInsuredVO ) ){
							totalSI += sumInsuredVO.getSumInsured();
						}
					}
				}
			}
		}

		return totalSI;
	}

	private double getMoneySI( MoneyVO moneyVO, Integer sectionId, PolicyVO policyVO ){

		double totalSI = 0;

		if( !Utils.isEmpty( moneyVO ) ){

			List<SumInsuredVO> sumInsuredDets = moneyVO.getSumInsuredDets();

			if( Utils.isEmpty( policyVO.getIsPrepackaged() ) || !policyVO.getIsPrepackaged().booleanValue() ){

				if( !Utils.isEmpty( sumInsuredDets ) ){

					for( SumInsuredVO sumIns : sumInsuredDets ){
						if( !Utils.isEmpty( sumIns ) && !Utils.isEmpty( sumIns.getSumInsured() ) ) 
							totalSI += sumIns.getSumInsured();
					}
				}
			}
			else{
				List<Contents> contentsList = moneyVO.getContentsList();
				if( !Utils.isEmpty( contentsList ) ){
					for( Contents content : contentsList ){
						if( !Utils.isEmpty( content ) && !Utils.isEmpty( content.getCover() ) )
							totalSI += content.getCover().doubleValue();
					}
				}
			}
		}
		return totalSI;
	}

	private double getWCSI( WCVO wcVO, Integer sectionId, PolicyVO policyVO ){
		
		BigDecimal liabPerLoc = new BigDecimal( 0.00 );
		String liabPerLocStr = null;
		int liabPerLocCD = 0;
		
		if( !Utils.isEmpty( wcVO ) && !Utils.isEmpty( wcVO.getEmpTypeDetails() ) ){
			List<EmpTypeDetailsVO> empTypeDetsList = wcVO.getEmpTypeDetails();

			/* Take the cumulative values of all contents and send then to rule engine to check referrals */
			for( EmpTypeDetailsVO empTypeDetailsVO : empTypeDetsList ){

				if( !Utils.isEmpty( empTypeDetailsVO.getEmpType() ) ){
					liabPerLoc = Utils.isEmpty( empTypeDetailsVO.getLimit() ) ? liabPerLoc : liabPerLoc.add( empTypeDetailsVO.getLimit() );
				}
			}
			if( !Utils.isEmpty( liabPerLoc ) ) liabPerLocCD = liabPerLoc.intValue();

			liabPerLocStr = SvcUtils.getLookUpDescription( "WC_LIMIT", policyVO.getScheme().getTariffCode().toString(), RulesConstants.LOOKUP_LEVEL2, liabPerLocCD );

			if( !Utils.isEmpty( liabPerLocStr, true ) ){
				liabPerLoc = new BigDecimal( liabPerLocStr );
			}
			
		}
		return liabPerLoc.doubleValue();
	}

	private double getPLSI( PublicLiabilityVO plVO, PolicyVO policyVO ){

		int indemnityAmtLimitCode = 0;
		String indemnityAmtLimitStr = null;
		double indemnityAmtLimit = 0;

		if( !Utils.isEmpty( plVO ) ){
			if( !Utils.isEmpty( plVO.getIndemnityAmtLimit() ) ) indemnityAmtLimitCode = plVO.getIndemnityAmtLimit();
			indemnityAmtLimitStr = SvcUtils.getLookUpDescription( "PL_LIMIT", policyVO.getScheme().getTariffCode().toString(), RulesConstants.LOOKUP_LEVEL2, indemnityAmtLimitCode );
			if( !Utils.isEmpty( indemnityAmtLimitStr, true ) ) indemnityAmtLimit = new Double( indemnityAmtLimitStr );
		}
		
		return indemnityAmtLimit;
		
	}
	
	private RuleInfo createRuleInfoForConditions( BaseVO baseVO, int section, String roleType, RuleInfo ruleInfo )
    {
        ArrayList<RuleSet> ruleSetList = null;
        RuleSet ruleSet = null;
        ArrayList<Fact> factList = null;    
        String conditonsChanged="N";
		
        String riskId = Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_DISC_LOAD );

        PolicyVO policyVO = (PolicyVO) baseVO;

        if( Utils.isEmpty( ruleInfo ) ){
              ruleInfo = new RuleInfo();
        }

        if( !Utils.isEmpty( policyVO ) )
        {
        	
        	SectionVO sectionvo = PolicyUtils.getSectionVO( policyVO, section );
        	if(!Utils.isEmpty( sectionvo ))
        	{
        		
        	conditonsChanged = DAOUtils.checkIfConditionsChanged(sectionvo.getStandardClauses(),policyVO,sectionvo);
        	

            ruleSetList = (ArrayList<RuleSet>) ruleInfo.getRuleSet();
            ruleSet = new RuleSet();
            ruleSet.setRuleSetName( RulesConstants.COND );
            ruleSet.setRiskId( riskId );

            factList = (ArrayList<Fact>) ruleSet.getFact();
            //authInfoVO = policyVO.getAuthInfoVO();
          
            Fact userFactor = new Fact();
  			userFactor.setFactName( RulesConstants.FACT_USER );
  			ArrayList<RuleAttribute> userRuleAttributeList = (ArrayList<RuleAttribute>) userFactor.getRuleSetAttribute();

  			RuleAttribute roleAttribute = new RuleAttribute();
  			roleAttribute.setAttributeName( "role" );
  			roleAttribute.setAttributeValue( roleType );
  			userRuleAttributeList.add( roleAttribute );
  			
  			Fact conditionsFactor = new Fact();
  			conditionsFactor.setFactName(RulesConstants.CONDITIONS );
			ArrayList<RuleAttribute> conditionCheckAttributeList = (ArrayList<RuleAttribute>) conditionsFactor.getRuleSetAttribute();
			

  			RuleAttribute condiiotnCheckAttribute = new RuleAttribute();
  			condiiotnCheckAttribute.setAttributeName( "conditionsChange" );
  			condiiotnCheckAttribute.setAttributeValue( String.valueOf( conditonsChanged ) );
  			conditionCheckAttributeList.add(condiiotnCheckAttribute);

  			factList.add( userFactor );
  			factList.add( conditionsFactor );
  			ruleSetList.add( ruleSet );
              
        }
        }

        return ruleInfo;
        
    }
}
