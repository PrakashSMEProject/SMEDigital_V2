/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.ClaimsSummaryVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RiskDetails;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * @author M1012799
 *
 */
public class GeneralInfoRuleMapperMonoline implements RuleMapper{

	private static final int DAYDIVIDER = ( 1000 * 60 * 60 * 24 );
	private static final long POLICY_PERIOD = 365L;
	private final static Logger logger = Logger.getLogger( GeneralInfoRuleMapperMonoline.class );
	

	/* (non-Javadoc)
	 * @see com.rsaame.pas.rules.mapper.RuleMapper#createRiskDetails(com.mindtree.ruc.cmn.base.BaseVO, int, java.lang.String)
	 */
	@Override
	public RuleInfo createRiskDetails( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){

		BigDecimal lossExpQuantum = null;
		/** Setting up the default value to N */
		String lossExperienceQuantum = "N";
		ClaimsSummaryVO claimsSummaryVO = null;
		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();
		String renewalQuoteExist = "N";
		DataHolderVO<Boolean> renQuoteExists = null;

		RiskDetails riskDetails = new RiskDetails();
		riskDetails.setRiskName( RulesConstants.GENERAL );
		
		riskDetails.setRiskId( new Integer( 11111 ) );
		List<Fact> factList = riskDetails.getFact();

		String effectiveDateBackdating = new String( "0" );
		String effectiveDatePostdating = new String( "0" );
		String issueDateBackdating = new String( "0" );
		String issueDatePostDating = new String( "0" );
		
		//String renEffectiveDateBackdating = "0";
		String renEffectiveDatePostdating = "0";
		
		//String renIssueDateBackdating = "0";
		//String renIssueDatePostDating = "0";
		
		String city = null;
		String nationality = null;

		String endorsementDateBackdating = new String( "0" );
		String endorsementDatePostdating = new String( "0" );
		String policyExtnPeriod ;
		
		String busDescription = null;
		String schCode = null;
		

		Date todayDate = getTodayDate();
		Date effectiveDate=null;
		
		/* Create user fact */
		Fact userFact = new Fact();
		userFact.setFactName( RulesConstants.FACT_USER );

		List<Characteristics> userCharacteristicList = userFact.getCharacteristics();

		Characteristics userCharacteristics = new Characteristics();
		userCharacteristics.setName( "role" );
		userCharacteristics.setValue( roleType );
		userCharacteristicList.add( userCharacteristics );

		if( !Utils.isEmpty( policyBaseVO ) ){
			PolicyDataVO policyDataVO = (PolicyDataVO) policyBaseVO;
			CommonVO commonVO = policyDataVO.getCommonVO();
			
			if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getEffDate() ) )
			{
				effectiveDate = policyDataVO.getScheme().getEffDate();
				if( !Utils.isEmpty( effectiveDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() &&  
						!Utils.isEmpty( commonVO.getDocCode() ) && commonVO.getDocCode() == 6) 
				{
					if(! todayDate.after( effectiveDate ) )
						/*//renEffectiveDateBackdating = String.valueOf( getDateDifference( todayDate, effectiveDate ) );
					else*/
						renEffectiveDatePostdating = String.valueOf( getDateDifference( effectiveDate, todayDate ) );
				}
				else if( !Utils.isEmpty( effectiveDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() ){
					if( todayDate.after( effectiveDate ) )
						effectiveDateBackdating = String.valueOf( getDateDifference( todayDate, effectiveDate ) );
					else
						effectiveDatePostdating = String.valueOf( getDateDifference( effectiveDate, todayDate ) );
					// The below characteristic to be set during renewal quotation flow.
				} 
			}

			if( !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO() ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO().getAccountingDate() ) )
			{
				Date accountingDate = policyDataVO.getAuthenticationInfoVO().getAccountingDate();
				
				if( !Utils.isEmpty( accountingDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() &&  
						!Utils.isEmpty( commonVO.getDocCode() ) && commonVO.getDocCode() == 6) 
				{
					/*if( todayDate.after( accountingDate ) )
						renIssueDateBackdating = String.valueOf( getDateDifference( todayDate, accountingDate ) );
					else
						renIssueDatePostDating = String.valueOf( getDateDifference( accountingDate, todayDate ) );*/
					logger.debug( "Inside If condition"); /* Added debug statement - sonar violation fix */
				}
				
				else if( !Utils.isEmpty( accountingDate ) && !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() )
				{
					if( todayDate.after( accountingDate ) )
						issueDateBackdating = String.valueOf( getDateDifference( todayDate, accountingDate ) );
					else
						issueDatePostDating = String.valueOf( getDateDifference( accountingDate, todayDate ) );
				}
			}

			/*if( !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO() ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO().getAccountingDate() ) ){
				Date accountingDate = policyDataVO.getAuthenticationInfoVO().getAccountingDate();
				if( !Utils.isEmpty( accountingDate ) && policyDataVO.getIsQuote() ){
					if( todayDate.after( accountingDate ) )
						effectiveDateBackdating = String.valueOf( getDateDifference( todayDate, accountingDate ) );
					else
						effectiveDatePostdating = String.valueOf( getDateDifference( accountingDate, todayDate ) );
				}
			}*/

			if(!Utils.isEmpty( policyDataVO.getGeneralInfo() ))
			{
				claimsSummaryVO = policyDataVO.getGeneralInfo().getClaimsHistory();
				if( !Utils.isEmpty( claimsSummaryVO ) )
				{
					lossExpQuantum = claimsSummaryVO.getLossExpQuantum();
					logger.debug( "claimsSummaryVO.getLossExpQuantum():  " + lossExpQuantum );
					if( !Utils.isEmpty( lossExpQuantum ) && lossExpQuantum.doubleValue() > 0 ) lossExperienceQuantum = "Y";
				}
			}
			if( !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() )
					&& !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getCity() ) ){
				city = String.valueOf( policyDataVO.getGeneralInfo().getInsured().getCity() );
			}

			if( !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() )
					&& !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getNationality() ) ){
				nationality = String.valueOf( policyDataVO.getGeneralInfo().getInsured().getNationality() );
			}

			long extnPeriod = (getDateDifference( policyDataVO.getScheme().getExpiryDate(), policyDataVO.getScheme().getEffDate() ).longValue() + 1L ) - POLICY_PERIOD;
			if( !policyDataVO.getCommonVO().getIsQuote() && extnPeriod > SvcConstants.zeroVal ){ // && !Utils.isEmpty( policyDataVO.getPolicyExtensionPeriod() )
				policyExtnPeriod = Long.valueOf( extnPeriod ).toString() ;
			}
			
			if(!commonVO.getIsQuote())
			{
				renQuoteExists =  (DataHolderVO<Boolean>) TaskExecutor.executeTasks("AMEND_POLICY_IS_RENEWAL_QUOTE_EXISTS",policyDataVO.getCommonVO());
			}
			if(!Utils.isEmpty( renQuoteExists )   && renQuoteExists.getData().booleanValue())
			{
				 renewalQuoteExist = "Y";
			}			
			
			if( !Utils.isEmpty( policyDataVO.getGeneralInfo() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured() )
					&& !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getBusDescription() ) ){
				busDescription = String.valueOf( policyDataVO.getGeneralInfo().getInsured().getBusDescription());
			}
			
			if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getSchemeCode() ) ){
				schCode = String.valueOf( policyDataVO.getScheme().getSchemeCode());
			}
		}		

		/*Create general info fact */
		Fact generalInfoFact = new Fact();
		generalInfoFact.setFactName( RulesConstants.FACT_GENERAL );

		List<Characteristics> generalInfoCharacteristicList = generalInfoFact.getCharacteristics();
		
		Characteristics businessDescCharacteristics = new Characteristics();
		businessDescCharacteristics.setName( "businessDesc" );
		businessDescCharacteristics.setValue(busDescription);
		generalInfoCharacteristicList.add( businessDescCharacteristics );
		
		Characteristics schemeCodeCharacteristics = new Characteristics();
		schemeCodeCharacteristics.setName( "schemeCode" );
		schemeCodeCharacteristics.setValue(schCode);
		generalInfoCharacteristicList.add( schemeCodeCharacteristics );
		
		

		Characteristics effectiveDateBackdatingCharacteristics = new Characteristics();
		effectiveDateBackdatingCharacteristics.setName( "effectiveDateBackdating" );
		effectiveDateBackdatingCharacteristics.setValue( effectiveDateBackdating );
		generalInfoCharacteristicList.add( effectiveDateBackdatingCharacteristics );

		Characteristics effectiveDatePostdatingCharacteristics = new Characteristics();
		effectiveDatePostdatingCharacteristics.setName( "effectiveDatePostdating" );
		effectiveDatePostdatingCharacteristics.setValue( effectiveDatePostdating );
		generalInfoCharacteristicList.add( effectiveDatePostdatingCharacteristics );

		Characteristics issueDateBackdatingCharacteristics = new Characteristics();
		issueDateBackdatingCharacteristics.setName( "issueDateBackdating" );
		issueDateBackdatingCharacteristics.setValue( "0" );        //changed from issueDateBackdatingCharacteristics.setValue( issueDateBackdating ) - 08-may-18 -- 21493 
		generalInfoCharacteristicList.add( issueDateBackdatingCharacteristics );

		Characteristics issueDatePostdatingCharacteristics = new Characteristics();
		issueDatePostdatingCharacteristics.setName( "issueDatePostdating" );
		issueDatePostdatingCharacteristics.setValue( issueDatePostDating );
		generalInfoCharacteristicList.add( issueDatePostdatingCharacteristics );
		
		/*Characteristics renIssueDateBackdatingCharacteristics = new Characteristics();
		renIssueDateBackdatingCharacteristics.setName( "renIssueDateBackdating" );
		renIssueDateBackdatingCharacteristics.setValue( renIssueDateBackdating );
		generalInfoCharacteristicList.add( renIssueDateBackdatingCharacteristics );
		
		Characteristics renIssueDatePostDatingCharacteristics = new Characteristics();
		renIssueDatePostDatingCharacteristics.setName( "renIssueDatePostDating" );
		renIssueDatePostDatingCharacteristics.setValue( renIssueDatePostDating );
		generalInfoCharacteristicList.add( renIssueDatePostDatingCharacteristics );
		
		
		Characteristics renEffectiveDateBackdatingCharacteristics = new Characteristics();
		renEffectiveDateBackdatingCharacteristics.setName( "renEffectiveDateBackdating" );
		renEffectiveDateBackdatingCharacteristics.setValue( renEffectiveDateBackdating );
		generalInfoCharacteristicList.add( renEffectiveDateBackdatingCharacteristics );*/
			
		Characteristics renEffectiveDatePostdatingCharacteristics = new Characteristics();
		renEffectiveDatePostdatingCharacteristics.setName( "renEffectiveDatePostdating" );
		renEffectiveDatePostdatingCharacteristics.setValue( renEffectiveDatePostdating );
		generalInfoCharacteristicList.add( renEffectiveDatePostdatingCharacteristics );

		if( !Utils.isEmpty( city ) ){
			Characteristics cityCharacteristics = new Characteristics();
			cityCharacteristics.setName( "city" );
			cityCharacteristics.setValue( city );
			generalInfoCharacteristicList.add( cityCharacteristics );
		}

		if( !Utils.isEmpty( nationality ) ){
			Characteristics nationalityCharacteristics = new Characteristics();
			nationalityCharacteristics.setName( "nationality" );
			nationalityCharacteristics.setValue( nationality );
			generalInfoCharacteristicList.add( nationalityCharacteristics );
		}
		
		Characteristics renewalQuoteAlreadyCreatedCharacteristics = new Characteristics();
		renewalQuoteAlreadyCreatedCharacteristics.setName( "renewalQuoteAlreadyCreated" );
		renewalQuoteAlreadyCreatedCharacteristics.setValue(renewalQuoteExist);
		generalInfoCharacteristicList.add( renewalQuoteAlreadyCreatedCharacteristics );
		
		Characteristics lossExperienceQuantumCharacteristics = new Characteristics();
		lossExperienceQuantumCharacteristics.setName( "lossExperienceQuantum" );
		lossExperienceQuantumCharacteristics.setValue( lossExperienceQuantum );
		generalInfoCharacteristicList.add( lossExperienceQuantumCharacteristics );

		/* Endorsement related mappers */
		if( !Utils.isEmpty( policyBaseVO ) ){
			PolicyDataVO policyDataVO = (PolicyDataVO) policyBaseVO;
			CommonVO commonVO = policyDataVO.getCommonVO();

			Date endtEffectiveDate = policyDataVO.getEndEffectiveDate();

			
			if( !Utils.isEmpty( commonVO ) && !commonVO.getIsQuote() ){
				List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_DATE, policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );

				/* Backdate check */
				if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){

					Date inceptionDate = null;
					for( Object[] result : resultSet ){
						if( Utils.isEmpty( result[ 1 ] ) ){
							inceptionDate = (Date) result[ 0 ];
						}
						else{
							inceptionDate = (Date) result[ 1 ];
						}
					}

					if(Utils.isEmpty( endtEffectiveDate )){
						endtEffectiveDate = commonVO.getEndtEffectiveDate();
					}
					
					if( !Utils.isEmpty( endtEffectiveDate ) && !Utils.isEmpty( inceptionDate ) && !Utils.isEmpty( todayDate ) ){
						if( todayDate.after( endtEffectiveDate ) ){
							if( !Utils.isEmpty( roleType ) && roleType.contains( "BROKER" ) ){
								endorsementDateBackdating = String.valueOf( getDateDifference( todayDate, endtEffectiveDate ) );
							}
							else{
								endorsementDateBackdating = String.valueOf( getDateDifference( inceptionDate, endtEffectiveDate ) );
							}
						}
						else{
							endorsementDatePostdating = String.valueOf( getDateDifference( endtEffectiveDate, todayDate ) );
						}
					}
				}
				
				Characteristics endorsementDateBackdatingCharacteristics = new Characteristics();
				endorsementDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
				endorsementDateBackdatingCharacteristics.setValue( endorsementDateBackdating );
				generalInfoCharacteristicList.add( endorsementDateBackdatingCharacteristics );
				
				Characteristics endorsementDatePostDatingCharacteristics = new Characteristics();
				endorsementDatePostDatingCharacteristics.setName( "endorsementDatePostdating" );
				endorsementDatePostDatingCharacteristics.setValue( endorsementDatePostdating );
				generalInfoCharacteristicList.add( endorsementDatePostDatingCharacteristics );
			}
		}

		/*Characteristics policyExtnPeriodCharacteristics = new Characteristics();
		policyExtnPeriodCharacteristics.setName( "policyExtnPeriod" );
		policyExtnPeriodCharacteristics.setValue( policyExtnPeriod );
		generalInfoCharacteristicList.add(policyExtnPeriodCharacteristics);*/

		factList.add( userFact );
		factList.add( generalInfoFact );
		riskDetailList.add( riskDetails );
		//return ruleInfo;
		return ruleInfo;
	}

	/**
	 * Method to get the date differnce 
	 * @param latestDate
	 * @param previousDate
	 * @return
	 */
	private Long getDateDifference( Date latestDate, Date previousDate ){
		return ( ( ( latestDate.getTime() - previousDate.getTime() ) / DAYDIVIDER ) );
	}

	/**
	 * Method to return the current date
	 * 
	 * @return
	 */
	private Date getTodayDate(){
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DAY_OF_MONTH,1);
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );

		Date today = cal.getTime();

		return today;
	}

}
