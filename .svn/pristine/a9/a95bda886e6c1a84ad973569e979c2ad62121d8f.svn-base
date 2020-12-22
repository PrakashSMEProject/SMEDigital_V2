/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.AmendPolicySvc;
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RiskDetails;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * @author M1016284
 *
 */
public class TravelRiskRuleMapper implements RuleMapper{

	private final static Logger logger = Logger.getLogger( TravelDetailsRuleMapper.class );
	private static final String RISK_NAME = "TRAVEL";
	private static final double ZERO = 0.0;
	private static final List<String> NON_VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.NON_VERSION_STATUS ) );
	@SuppressWarnings( "unused" )
	private static final List<String> VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.VERSION_STATUS ) );
	private static final String YES = "Y";

	@Override
	public RuleInfo createRiskDetails( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){

		logger.debug( "TravelRiskRuleMapper -------------> Mapping the PolicyDataVO object for rules invocation" );
		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();
		RiskDetails riskDetails = new RiskDetails();
		riskDetails.setRiskName( RISK_NAME );
		riskDetails.setRiskId( new Integer( "9999" ) );
		List<Fact> factList = riskDetails.getFact();

		Fact userFact = new Fact();
		userFact.setFactName( RulesConstants.FACT_USER );
		DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT );

		Characteristics userCharacteristics = new Characteristics();
		userCharacteristics.setName( "role" );
		userCharacteristics.setValue( roleType );
		userFact.getCharacteristics().add( userCharacteristics );

		//String extraPremium = new String( "N" );
		String refundPremium;
		String nilEndorsement;
		String discountPerc = new String( "0.0" );
		String commissionPerc = new String( "N" );
		String oSPremium ;
		String priorClaim;
		String endorseAfterRenewal;
		BigDecimal discountPercentage;
		BigDecimal extraPremium ;
		
		/*Fact generalInfoFact = new Fact();
		generalInfoFact.setFactName( RulesConstants.FACT_GENERAL );*/

		Fact travelFact = new Fact();
		travelFact.setFactName( RulesConstants.TRAVEL_FACT_NAME );

		PolicyDataVO policyDataVO = (PolicyDataVO) policyBaseVO;
		boolean isPopulateOperation = policyDataVO.isPopulateOperation();
		
		if( !Utils.isEmpty( policyDataVO.getEndorsmentVO() ) && !policyDataVO.getCommonVO().getIsQuote() ){

			if(!isPopulateOperation){
				EndorsmentVO endtVO = policyDataVO.getEndorsmentVO().get( SvcConstants.zeroVal );
				if( endtVO.getPayablePremium() > ZERO ){
					extraPremium = new BigDecimal( decForm.format( endtVO.getPayablePremium() ) );
					Characteristics extraPremiumCharacteristics = new Characteristics();
					extraPremiumCharacteristics.setName( "extraPremium" );
					extraPremiumCharacteristics.setValue( String.valueOf( extraPremium ) );
					travelFact.getCharacteristics().add( extraPremiumCharacteristics );
				}
				else if( endtVO.getPayablePremium() < ZERO ){
	
					refundPremium = new String( "Y" );
	
					Characteristics refundPremiumCharacteristics = new Characteristics();
					refundPremiumCharacteristics.setName( "refundPremium" );
					refundPremiumCharacteristics.setValue( refundPremium );
					travelFact.getCharacteristics().add( refundPremiumCharacteristics );
				}
				else{
					nilEndorsement = new String( "Y" );
	
					Characteristics nilEndorsementCharacteristics = new Characteristics();
					nilEndorsementCharacteristics.setName( "nilEndorsement" );
					nilEndorsementCharacteristics.setValue( nilEndorsement );
					travelFact.getCharacteristics().add( nilEndorsementCharacteristics );
	
				}
			}

		}

		if( !Utils.isEmpty( policyDataVO.getPremiumVO() ) && !Utils.isEmpty( policyDataVO.getPremiumVO().getDiscOrLoadPerc() ) ){

			/* Get the discount */
			if( policyDataVO.getPremiumVO().getDiscOrLoadPerc() != 0 ){
				//discountPercentage = new BigDecimal( decForm.format( policyDataVO.getPremiumVO().getDiscOrLoadPerc() ) );
				//discountPerc = String.valueOf( discountPercentage.multiply( new BigDecimal( -1 ) ) );
				discountPerc = String.valueOf( Math.abs( policyDataVO.getPremiumVO().getDiscOrLoadPerc() ) );
			}

			Characteristics discountPercCharacteristics = new Characteristics();
			discountPercCharacteristics.setName( "discountPercentage" );
			discountPercCharacteristics.setValue( discountPerc );
			travelFact.getCharacteristics().add( discountPercCharacteristics );
		}

		if( !Utils.isEmpty( policyDataVO.getCommission() ) ){

			Short configuredCommission = null;

			if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getSchemeCode() ) ){
				configuredCommission = SvcUtils.getLookUpCodeForLOneLTwo( "PAS_COMMISSION", String.valueOf( policyDataVO.getScheme().getSchemeCode() ),
						SvcUtils.getKeyForCommisionCacheObj( policyDataVO ) );
			}

			if( !Utils.isEmpty( configuredCommission ) ){
				Double diffCommission = policyDataVO.getCommission() - configuredCommission;
				if( diffCommission != 0.0 ){
					commissionPerc = new String( "Y" );
				}
				else{
					commissionPerc = new String( "N" );
				}
			}
		}
		
		if( !Utils.isEmpty( policyDataVO.getRenewals()) &&  !Utils.isEmpty(policyDataVO.getRenewals().getEndorsmentList()) && policyDataVO.getRenewals().getEndorsmentList().size() > 0 ) {
			
			endorseAfterRenewal = new String("Y");
			Characteristics endorsementCharacteristics = new Characteristics();
			endorsementCharacteristics.setName("endorsementAfterRenewalQuote");
			endorsementCharacteristics.setValue(endorseAfterRenewal);
			travelFact.getCharacteristics().add(endorsementCharacteristics);
		}			
		
		
		if( !Utils.isEmpty( policyDataVO.getRenewals() ) && policyDataVO.getRenewals().getClaimCount() > 0 ){
			
			priorClaim = new String("Y");								
			Characteristics claimExistCharacteristics = new Characteristics();
			claimExistCharacteristics.setName( "priorYearClaimAfterRenewalQuote" );
			claimExistCharacteristics.setValue( priorClaim );
			travelFact.getCharacteristics().add( claimExistCharacteristics );
		}
		
		if( !Utils.isEmpty( policyDataVO.getRenewals() ) && policyDataVO.getRenewals().getClaimCount() > 0 ){

			String claimCount = policyDataVO.getRenewals().getClaimCount().toString();					
			Characteristics claimCountCharacteristics = new Characteristics();
			claimCountCharacteristics.setName( "claimCount" );
			claimCountCharacteristics.setValue( claimCount );
			travelFact.getCharacteristics().add( claimCountCharacteristics );
		}
		
		if( !Utils.isEmpty( policyDataVO.getRenewals() ) && policyDataVO.getRenewals().getOsPremium() > 0 ){

			oSPremium = new String( "Y" );

			Characteristics oSPremiumCharacteristics = new Characteristics();
			oSPremiumCharacteristics.setName( "isPriorYearPremiumOutstanding" );
			oSPremiumCharacteristics.setValue( oSPremium );
			travelFact.getCharacteristics().add( oSPremiumCharacteristics );				
		}

		Characteristics commissionPercCharacteristics = new Characteristics();
		commissionPercCharacteristics.setName( "commissionPercentage" );
		commissionPercCharacteristics.setValue( commissionPerc );
		travelFact.getCharacteristics().add( commissionPercCharacteristics );
		
		/*
		 * Rule for endorsements  with claims
		 */
		TravelInsuranceVO travelInsuranceDetailsVo = (TravelInsuranceVO) policyDataVO;
		ClaimsSvc claimsService = (ClaimsSvc) Utils.getBean( "claimsSvc" );
		if( !policyDataVO.getCommonVO().getIsQuote() && claimsService.checkClaimsExistForPolicyNumber( policyDataVO.getCommonVO().getPolicyNo() ) ){

			/*
			 * Tariff Change check
			 */

			boolean trariffChanged = travelTariffChangeCheck( travelFact, travelInsuranceDetailsVo, policyDataVO.getCommonVO() );

			/*
			 * Removal of optional coverage Winter Sport loading or Golf Loading 
			 * If tariff is changed, then no need to check for optional coverage
			 */
			if( !trariffChanged ){
				travelAddtlCoverCheck( travelFact, travelInsuranceDetailsVo, policyDataVO.getCommonVO() );
			}

		}

		if(!isPopulateOperation){
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) policyDataVO;
			
			/*Integer licensedBy = null;
			
			if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getAuthenticationInfoVO() ) ){
				licensedBy = travelInsuranceVO.getAuthenticationInfoVO().getLicensedBy();
			}
			 Filter the rule if the licensed by selected is the CREDIT_MANAGER role 
			if( !SvcUtils.checkCreditLimitRule( licensedBy ) ){*/
			if( ( !Utils.isEmpty( (UserProfile) travelInsuranceVO.getLoggedInUser() ) && ( ( !Utils.isEmpty( ( (UserProfile) travelInsuranceVO.getLoggedInUser() ).getRsaUser() ) ) && ( (UserProfile) travelInsuranceVO
					.getLoggedInUser() ).getRsaUser().getUserId() != 991 ) ) ){
				setBrokerCreditLimit( decForm, travelInsuranceVO, riskDetailList, roleType );
			}
			//}
		}
		
		factList.add( userFact );
		factList.add( travelFact );
		riskDetailList.add( riskDetails );
		return ruleInfo;
	}


	private void travelAddtlCoverCheck( Fact travelFact, TravelInsuranceVO travelInsuranceDetailsVo, CommonVO commonVO ){

		String condition = null;
		if( NON_VERSION_STATUS.contains( commonVO.getStatus().toString() ) ){
			condition = "<";
		}
		else{
			condition = "=";
		}
		String[] addtlCover = { "18", "19" };

		boolean addtlCoverDeselected = false;
		for( String coverCode : addtlCover ){
			boolean addtlCoverPresent = false;
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPASNewSession( " SELECT prm_cov_code FROM T_TRN_PREMIUM WHERE prm_policy_id = "
					+ commonVO.getPolicyId() + " and PRM_ENDT_ID " + condition + commonVO.getEndtId() + " and PRM_COV_CODE = " + coverCode + " order BY PRM_ENDT_ID desc" );
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				addtlCoverPresent = true;
			}
			if( addtlCoverPresent ){
				if( Utils.isEmpty( travelInsuranceDetailsVo.getSelectedPackage().getCoverUsingCoverCodes( Short.valueOf( coverCode ), null, null ) ) ){
					addtlCoverDeselected = true;
					break;
				}
			}
		}

		if( addtlCoverDeselected ){
			Characteristics addtlCoverDeselectedCharacteristics = new Characteristics();
			addtlCoverDeselectedCharacteristics.setName( "addtlCoverDeselectedCheck" );
			addtlCoverDeselectedCharacteristics.setValue( YES );
			travelFact.getCharacteristics().add( addtlCoverDeselectedCharacteristics );
		}
	}

	private boolean travelTariffChangeCheck( Fact travelFact, TravelInsuranceVO travelInsuranceDetailsVo, CommonVO commonVO ){

		String condition = null;
		boolean trariffChanged = false;
		if( NON_VERSION_STATUS.contains( commonVO.getStatus().toString() ) ){
			condition = "<";
		}
		else{
			condition = "=";
		}
		/*
		 * non zero in <tarChanged> means the tariff has changed
		 */
		Integer tarChanged = 0;
		java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPASNewSession( "select pol_Tar_Code - " + travelInsuranceDetailsVo.getScheme().getTariffCode()
				+ " from t_trn_policy where pol_policy_id = " + commonVO.getPolicyId() + " and Pol_Endt_Id" + condition + commonVO.getEndtId() + " order BY Pol_Endt_Id desc" );
		if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
			tarChanged = ( (BigDecimal) valueHolder.get( 0 ) ).intValue();
		}
		if( tarChanged != 0 ){
			Characteristics tarChangedCharacteristics = new Characteristics();
			tarChangedCharacteristics.setName( "travelTariffChangeCheck" );
			tarChangedCharacteristics.setValue( YES );
			travelFact.getCharacteristics().add( tarChangedCharacteristics );
			trariffChanged = true;
		}

		return trariffChanged;
	}
	
	/**
	 * Method to get the the broker credit limit
	 * 
	 * @param decForm
	 * @param travelInsuranceVO
	 */
	private void setBrokerCreditLimit( DecimalFormat decForm, TravelInsuranceVO travelInsuranceVO,List<RiskDetails> riskDetails,String roleType ){
		double premium = 0;
		AmendPolicySvc amendPolicySvc;
		
		double brokerCreditLimit = 0;
		
		boolean callCreditRule = true;
		
		if( travelInsuranceVO.getCommonVO().getIsQuote() ){
			amendPolicySvc = (AmendPolicySvc) Utils.getBean( "amendPolicySvc" );
		}
		else{
			amendPolicySvc = (AmendPolicySvc) Utils.getBean( "amendPolicySvc_POL" );
		}
		
		PolicyDataVO policyDataVO = (PolicyDataVO) amendPolicySvc.invokeMethod( "retrievePolicyDataVO", travelInsuranceVO.getCommonVO() );
		
		Integer licensedBy = null;
		
		if( !Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO() ) ){
			licensedBy = policyDataVO.getAuthenticationInfoVO().getLicensedBy();
		}
		 /*Filter the rule if the licensed by selected is the CREDIT_MANAGER role*/ 
		if( SvcUtils.checkCreditLimitRule( licensedBy ) && travelInsuranceVO.getCommonVO().getAppFlow().equals( Flow.RESOLVE_REFERAL )
				&& !Utils.isEmpty( travelInsuranceVO.getCommonVO().getTaskDetails() ) && travelInsuranceVO.getCommonVO().getTaskDetails().getStatus().equals( String.valueOf( 1 ) ) ){
			callCreditRule = false;
		}
		
		if( callCreditRule ){
		
			RiskDetails riskDetailGen = new RiskDetails();
			riskDetailGen.setRiskName( "GEN" );
	
			riskDetailGen.setRiskId( new Integer( 1001 ) );
			List<Fact> factList = riskDetailGen.getFact();
			
			/* Create user fact */
			Fact userFact = new Fact();
			userFact.setFactName( RulesConstants.FACT_USER );
	
			List<Characteristics> userCharacteristicList = userFact.getCharacteristics();
	
			Characteristics userCharacteristics = new Characteristics();
			userCharacteristics.setName( "role" );
			userCharacteristics.setValue( roleType );
			userCharacteristicList.add( userCharacteristics );
			
			/*Create general info fact */
			Fact genFact = new Fact();
			genFact.setFactName( RulesConstants.FACT_VALIDATION );
	
			List<Characteristics> genCharacteristicList = genFact.getCharacteristics();
			
			
			/*
			 * If it is endorsement pick the premium endorsement else pick the premium from policy data
			 */
			if( ( !Utils.isEmpty( travelInsuranceVO.getEndorsmentVO() ) && !travelInsuranceVO.getCommonVO().getIsQuote() ) ){
				EndorsmentVO endtVO = travelInsuranceVO.getEndorsmentVO().get( SvcConstants.zeroVal );
	
				if( endtVO.getPayablePremium() != 0 && endtVO.getPayablePremium() > 0 ){
					premium = endtVO.getPayablePremium();
				}
			}
			else if( !Utils.isEmpty( travelInsuranceVO.getCommonVO() ) && travelInsuranceVO.getCommonVO().getAppFlow().equals(Flow.RESOLVE_REFERAL)  
					&& !Utils.isEmpty( travelInsuranceVO.getCommonVO().getTaskDetails() ) && travelInsuranceVO.getCommonVO().getTaskDetails().getStatus().equals(String.valueOf(1)) ){
				
				PremiumVO premiumVO = travelInsuranceVO.getPremiumVO();
	
				if( !Utils.isEmpty( premiumVO ) ){
					premium = premiumVO.getPremiumAmt();
				}
			}
					
			
			
			GeneralInfoVO generalInfoVO = null;
			
			if(!Utils.isEmpty( policyDataVO )) {
				generalInfoVO = policyDataVO.getGeneralInfo();
			}
	
			if( !Utils.isEmpty( generalInfoVO ) ){
				SourceOfBusinessVO sourceOfBusinessVO = generalInfoVO.getSourceOfBus();
	
				if( !Utils.isEmpty( sourceOfBusinessVO ) ){
					Integer brokerName = sourceOfBusinessVO.getBrokerName();
	
					if( !Utils.isEmpty( brokerName ) && premium > 0){
						brokerCreditLimit = SvcUtils.getBrokerCreditLimitPercentage( premium, brokerName );
					}
				}
			}
				
			
				
			BigDecimal brokerLimit =  new BigDecimal(Currency.getFormattedCurrency( brokerCreditLimit ) ) ;
			
			if(brokerLimit.compareTo( new BigDecimal( 100 ) ) == -1 || brokerLimit.compareTo( new BigDecimal( 100 ) ) == 0){
				Characteristics brokerMinCreditLimit = new Characteristics();
				brokerMinCreditLimit.setName( "brokerMinCreditLimit" );
				brokerMinCreditLimit.setValue( String.valueOf(brokerLimit) );
				
				genCharacteristicList.add( brokerMinCreditLimit );
			}
			
			Characteristics brokerMaxCreditLimit = new Characteristics();
			brokerMaxCreditLimit.setName( "brokerMaxCreditLimit" );
			brokerMaxCreditLimit.setValue( String.valueOf(brokerLimit) );
			genCharacteristicList.add( brokerMaxCreditLimit );
			
			factList.add( genFact );
			factList.add( userFact );
			riskDetails.add(riskDetailGen);
		}
		
	}

}
