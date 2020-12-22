/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.clauses.svc.ViewClausesSvc;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.StandardClause;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RiskDetails;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * @author M1016284
 *
 */
public class ValidationRuleMapper implements RuleMapper{

	private final static Logger logger = Logger.getLogger( ValidationRuleMapper.class );
	private static final String RISK_NAME = "GEN";
	private static final int DAYDIVIDER = ( 1000 * 60 * 60 * 24 );
	
	
	private static final int CREDIT_MODE = SvcConstants.CREDIT_MODE;
	private static final int CLAIMS_CHECK = SvcConstants.CLAIMS_CHECK;
	private static final int CANCEL_DISCOUNT_CHECK = SvcConstants.CANCEL_DISCOUNT_CHECK;
	private static final int STANDARD_CLAUSES = SvcConstants.STANDARD_CLAUSES;
	private static final int AMEND_POLICY_VALIDATION = SvcConstants.AMEND_POLICY_VALIDATION;
	private static final int CANCEL_POLICY_VALIDATION = SvcConstants.CANCEL_POLICY_VALIDATION;
	private static final int CANCEL_POST_DATE = SvcConstants.CANCEL_POST_DATE;
	private static final int POLICY_CONVERSION_RULE = SvcConstants.POLICY_CONVERSION_RULE;
	
	
	@Override
	public RuleInfo createRiskDetails( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){

		logger.debug( "ValidationRuleMapper -------------> Mapping the PolicyDataVO object for rules invocation" );
		logger.debug( "Selected Section for Rule IS: "+ section);

		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();
		RiskDetails riskDetails = new RiskDetails();
		riskDetails.setRiskName( RISK_NAME );
		riskDetails.setRiskId( new Integer( "9999" ) );
		List<Fact> factList = riskDetails.getFact();

		Fact userFact = new Fact();
		userFact.setFactName( RulesConstants.FACT_USER );

		Fact validationFact = new Fact();
		validationFact.setFactName( RulesConstants.FACT_VALIDATION );

		Characteristics userCharacteristics = new Characteristics();
		userCharacteristics.setName( "role" );
		userCharacteristics.setValue( roleType );
		userFact.getCharacteristics().add( userCharacteristics );

		PolicyDataVO policyData = (PolicyDataVO) policyBaseVO;
		String quotePremium = new String( "0" );

		/*if( !Utils.isEmpty( policyData ) && !Utils.isEmpty( policyData.getPremiumVO() ) && !Utils.isEmpty( policyData.getPremiumVO().getPremiumAmt() ) ){
			quotePremium = String.valueOf( policyData.getPremiumVO().getPremiumAmt() );
		}*/
		/* In case of convert to policy the Premium Amount from policyData.PremiumVO.premiumAmt will be available in session which is set in endorsementVO.*/
		if( !Utils.isEmpty( policyData ) && !Utils.isEmpty( policyData.getEndorsmentVO() ) ){
			quotePremium = String.valueOf( policyData.getEndorsmentVO().get( SvcConstants.zeroVal ).getPayablePremium() );
		}

		Characteristics quotePremiumCharacteristics = new Characteristics();
		Characteristics claimsCheckCharacteristics = new Characteristics();
		
		Characteristics standardConditionCharacteristics = new Characteristics();
		Characteristics amendPolicyValidation = new Characteristics();
		Characteristics cancelPolicyValidation = new Characteristics();
		Characteristics cancellationDatePostdating = new Characteristics();
		Characteristics cancellationDateBackdating = new Characteristics();  //newly added

		Characteristics brkAccBlocked = new Characteristics();
		
		CommonVO commonVO = policyData.getCommonVO();
		Fact generalFact = new Fact();
		switch( section ){
			case CREDIT_MODE:

				quotePremiumCharacteristics.setName( "quotePremium" );
				quotePremiumCharacteristics.setValue( quotePremium );
				validationFact.getCharacteristics().add( quotePremiumCharacteristics );
		
				break;
			case CLAIMS_CHECK:

				ClaimsSvc claimsService = (ClaimsSvc) Utils.getBean( "claimsSvc" );
				if( claimsService.checkClaimsExistForPolicyNumber( policyData.getCommonVO().getPolicyNo() ) ){

					claimsCheckCharacteristics.setName( "claimsExist" );
					claimsCheckCharacteristics.setValue( "Y" );
					validationFact.getCharacteristics().add( claimsCheckCharacteristics );
				}
				break;
			case CANCEL_DISCOUNT_CHECK:

				claimsCheckCharacteristics.setName( "cancelDiscPercentage" );
				claimsCheckCharacteristics.setValue( Double.valueOf( Math.abs( policyData.getPremiumVO().getDiscOrLoadPerc() ) ).toString() );
				validationFact.getCharacteristics().add( claimsCheckCharacteristics );
				break;
			case STANDARD_CLAUSES:

				setDefaultStandardChangedCharacteristics( policyData, standardConditionCharacteristics, validationFact );
				break;
			case AMEND_POLICY_VALIDATION:
				setAmendPolicyValidation(policyData,amendPolicyValidation,validationFact);
				break;
			case CANCEL_POLICY_VALIDATION:
				setCancelPolicyValidation(policyData,cancelPolicyValidation,validationFact);
				break;
			case CANCEL_POST_DATE:
				
				if(!Utils.isEmpty( commonVO )){
					generalFact.setFactName(  LOB.HOME.equals( LOB.valueOf( commonVO.getLob().toString() ) )?"GeneralHome":"GeneralTravel");
				}
				
				if(LOB.WC.equals( LOB.valueOf( commonVO.getLob().toString() ))){
				setCancelBackDateFact(policyData,cancellationDateBackdating,generalFact);  // added for 139106 WC cancellation
				}
				else{
					setCancelPostDateFact(policyData,cancellationDatePostdating,generalFact);
				}


				break;
			case POLICY_CONVERSION_RULE:
				
				
				if((LOB.WC.equals( LOB.valueOf( commonVO.getLob().toString() ))) ){  // added for 139106 WC CTP scenario 139106
					logger.debug( "INSIDE WC LOB condition for CTP and roletype is : " + roleType);
					setEffectiveBackDateValidationFact(policyData,cancellationDateBackdating,validationFact);  
					setEffectivePostDateValidationFact(policyData,cancellationDatePostdating,validationFact);  

					}
				
				setbrkAccBlockedFact(policyData,brkAccBlocked,validationFact);
				
				
				break;
			default:
				break;
		}

		factList.add( userFact );
		
		if( !Utils.isEmpty( generalFact ) && !Utils.isEmpty( generalFact.getFactName() ) ){
			factList.add( generalFact );
		}
		else{
			factList.add( validationFact );
		}
		
		riskDetailList.add( riskDetails );
		return ruleInfo;
	}

	private void setbrkAccBlockedFact( PolicyDataVO policyData, Characteristics brkAccBlocked, Fact validationFact ){

		Integer brkCode = policyData.getGeneralInfo().getSourceOfBus().getBrokerName();
		
		double brokerCreditLimit = 0;
		//DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT );
		
		if( !Utils.isEmpty( brkCode ) && (policyData.getCommonVO().getLob().equals(LOB.HOME) || policyData.getCommonVO().getLob().equals(LOB.TRAVEL))){

			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_BROKER_ACC_STATUS,brkCode );
			BigDecimal bkrStatus = null;
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				bkrStatus = ( (BigDecimal) valueHolder.get( 0 ) );
			}
			brkAccBlocked.setName( "brkAccBlockedValidation" );
			if( !Utils.isEmpty( bkrStatus ) ){
				if( bkrStatus.compareTo( BigDecimal.ZERO ) == 0 ){
					brkAccBlocked.setValue( "Y" );
				}
				else{
					brkAccBlocked.setValue( "N" );
				}
				validationFact.getCharacteristics().add( brkAccBlocked );
			}
			else{
				brkAccBlocked.setValue( "N" );
				validationFact.getCharacteristics().add( brkAccBlocked );
			}
			
			double premium = 0;
			
			/*
			 * If it is endorsement pick the premium endorsement else pick the premium from policy data
			 */
			if( !Utils.isEmpty( policyData.getEndorsmentVO() ) && !policyData.getIsQuote() ){
				EndorsmentVO endtVO = policyData.getEndorsmentVO().get( SvcConstants.zeroVal );

				if( endtVO.getPayablePremium() != 0 && endtVO.getPayablePremium() > 0 ){
					premium = endtVO.getPayablePremium();
				}
			}
			else{
				PremiumVO premiumVO = policyData.getPremiumVO();

				if( !Utils.isEmpty( premiumVO ) ){
					premium = premiumVO.getPremiumAmt();
				}
			}
				
			
			
			GeneralInfoVO generalInfoVO = policyData.getGeneralInfo();

			if( !Utils.isEmpty( generalInfoVO ) ){
				SourceOfBusinessVO sourceOfBusinessVO = generalInfoVO.getSourceOfBus();

				if( !Utils.isEmpty( sourceOfBusinessVO ) ){
					Integer brokerName = sourceOfBusinessVO.getBrokerName();

					if( !Utils.isEmpty( brokerName ) ){
						brokerCreditLimit = SvcUtils.getBrokerCreditLimitPercentage( premium, brokerName );
					}
				}
			}
			
			BigDecimal brokerLimit =  new BigDecimal(Currency.getFormattedCurrency( brokerCreditLimit ).replace(",", "") ) ;
			
			if(brokerLimit.compareTo( new BigDecimal( 100 ) ) == -1 || brokerLimit.compareTo( new BigDecimal( 100 ) ) == 0){
				Characteristics brokerMinCreditLimit = new Characteristics();
				brokerMinCreditLimit.setName( "brokerMinCreditLimit" );
				brokerMinCreditLimit.setValue( String.valueOf(brokerLimit) );
				
				validationFact.getCharacteristics().add( brokerMinCreditLimit );
			}
			
			Characteristics brokerMaxCreditLimit = new Characteristics();
			brokerMaxCreditLimit.setName( "brokerMaxCreditLimit" );
			brokerMaxCreditLimit.setValue( String.valueOf(brokerLimit) );
			validationFact.getCharacteristics().add( brokerMaxCreditLimit );
		}
		else{
			if((policyData.getCommonVO().getLob().equals(LOB.HOME) || policyData.getCommonVO().getLob().equals(LOB.TRAVEL)))
					{
						throw new BusinessException( "cmn.unknownError", null, "This rule needs to be called only for brokers" );
					}
		}
	}

	/**
	 * 
	 * @param policyData
	 * @param cancellationDatePostdating
	 * @param generalFact
	 */
	private void setCancelPostDateFact( PolicyDataVO policyData, Characteristics cancellationDatePostdatingCharacteristics, Fact generalFact ){
		CommonVO commonVO = policyData.getCommonVO();
		
		Date endtEffectiveDate = policyData.getEndEffectiveDate();
		
		if( !Utils.isEmpty( commonVO ) && !commonVO.getIsQuote() ){
			
			//List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_DATE, commonVO.getPolicyId(), commonVO.getEndtId() );
			Date todayDate = getTodayDate();
			
			String cancellationDatePostdating = new String( "0" );
			/* Backdate check */
			/*if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){

				Date inceptionDate = null;
				for( Object[] result : resultSet ){
					if( Utils.isEmpty( result[ 1 ] ) ){
						inceptionDate = (Date) result[ 0 ];
					}
					else{
						inceptionDate = (Date) result[ 1 ];
					}
				}*/

				if(Utils.isEmpty( endtEffectiveDate )){
					endtEffectiveDate = commonVO.getEndtEffectiveDate();
				}
				
				/*if(!Utils.isEmpty( endtEffectiveDate ) && !Utils.isEmpty( inceptionDate ) && !Utils.isEmpty( todayDate )){
					if( todayDate.after( endtEffectiveDate ) ){
						cancellationDatePostdating = String.valueOf( getDateDifference( endtEffectiveDate,inceptionDate ) );
					}else{*/
				/* Commenting the above code as the postdating days has to be calculated based on the current date */
				if( endtEffectiveDate.after( todayDate ) ){
						cancellationDatePostdating = String.valueOf( getDateDifference( endtEffectiveDate,todayDate ) );
				}
					//}
				//}
			//}
			
			/*Characteristics endorsementDateBackdatingCharacteristics = new Characteristics();
			endorsementDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
			endorsementDateBackdatingCharacteristics.setValue( endorsementDateBackdating );
			generalInfoCharacteristicList.add( endorsementDateBackdatingCharacteristics );*/
			
			cancellationDatePostdatingCharacteristics.setName( "cancellationDatePostdating" );
			cancellationDatePostdatingCharacteristics.setValue( cancellationDatePostdating );
			
			generalFact.getCharacteristics().add(cancellationDatePostdatingCharacteristics);
			
		}
		
	}

	/**
	 * Method to set the cancel policy validation fact
	 * 
	 * @param policyData
	 * @param cancelPolicyValidation
	 * @param validationFact
	 */
	private void setCancelPolicyValidation( PolicyDataVO policyData, Characteristics cancelPolicyValidation, Fact validationFact ){

		SchemeVO schemeVO = policyData.getScheme();
		
		if( !Utils.isEmpty( schemeVO ) ){
			Date expiryDate = schemeVO.getExpiryDate();

			if( !Utils.isEmpty( expiryDate ) ){
				if( expiryDate.compareTo( new Date() ) == -1 ){
					cancelPolicyValidation.setName( "cancelPolicyValidation" );
					cancelPolicyValidation.setValue( "Y" );
					validationFact.getCharacteristics().add( cancelPolicyValidation );
				}
			}			
			if(LOB.TRAVEL.equals(policyData.getCommonVO().getLob()) && //(policyData.getCommonVO().getLocCode().equals(30))
					SvcConstants.OMAN.toString().equalsIgnoreCase(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)))
			{
				cancelPolicyValidation = new Characteristics();
				cancelPolicyValidation.setName("cancellationAllowedTariff");
				cancelPolicyValidation.setValue(schemeVO.getTariffCode().toString());
				validationFact.getCharacteristics().add( cancelPolicyValidation );
			}
		}
	}

	/**
	 * Method to set the amend policy validation fact
	 * 
	 * @param policyData
	 * @param amendPolicyValidation
	 * @param validationFact
	 */
	private void setAmendPolicyValidation( PolicyDataVO policyData, Characteristics amendPolicyValidation, Fact validationFact ){
		SchemeVO schemeVO = policyData.getScheme();
		
		if( !Utils.isEmpty( schemeVO ) ){
			Date expiryDate = schemeVO.getExpiryDate();

			if( !Utils.isEmpty( expiryDate ) ){ 
				// Request 131381: To pass endt on Policy ExpiryDate getTodayDate() 
				if( expiryDate.compareTo(getTodayDate() ) == -1 ){ 
					amendPolicyValidation.setName( "amendPolicyValidation" );
					amendPolicyValidation.setValue( "Y" );
					validationFact.getCharacteristics().add( amendPolicyValidation );
				}
			}
		}
		
	}

	/**
	 * Method to check if the default clauses is changed
	 * 
	 * @param policyData
	 * @param standardConditionCharacteristics 
	 * @param validationFact 
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private void setDefaultStandardChangedCharacteristics( PolicyDataVO policyData, Characteristics standardConditionCharacteristics, Fact validationFact ){
		CommonVO commonVO = policyData.getCommonVO();

		ViewClausesSvc viewClausesSvc = null;

		if( !Utils.isEmpty( commonVO ) ){

			if( SvcUtils.isQuote( commonVO ) ){
				viewClausesSvc = (ViewClausesSvc) Utils.getBean( "viewClauseSvc" );
			}
			else{
				viewClausesSvc = (ViewClausesSvc) Utils.getBean( "viewClauseSvc_POL" );
			}

			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			Object[] input = { commonVO, Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_SEC_ID" ) ), false, true };
			inputData.setData( input );

			DataHolderVO<List<StandardClause>> holderVO = (DataHolderVO<List<StandardClause>>) viewClausesSvc.invokeMethod( "getClauses", inputData );

			List<StandardClause> masterStdClauseList = holderVO.getData();

			List<StandardClause> stdClauseList = policyData.getStandardClauses();

			if( !Utils.isEmpty( stdClauseList ) && stdClauseList.size() > 0 ){
				if( !Utils.isEmpty( masterStdClauseList ) && masterStdClauseList.size() > 0 ){

					if(policyData.getCommonVO().getLob().toString().equalsIgnoreCase("HOME") ||  policyData.getCommonVO().getLob().toString().equalsIgnoreCase("TRAVEL") )
					{
						for( StandardClause masteStandardClause : masterStdClauseList ){
							for( StandardClause standardClause : stdClauseList ){

							if( masteStandardClause.getClauseCode().longValue() == standardClause.getClauseCode().longValue() ){
								if( masteStandardClause.isSelected() && !standardClause.isSelected() && masteStandardClause.getIsDefault().intValue() == 2 ){
									standardConditionCharacteristics.setName( "standardCondition" );
									standardConditionCharacteristics.setValue( String.valueOf( standardClause.getClauseCode() ) );
									validationFact.getCharacteristics().add( standardConditionCharacteristics );
									}
								}
							}
						}
					}
					else
					{
						Boolean stdconditionChanged = Boolean.FALSE, conditionChanged = Boolean.FALSE, 
								stdExcluChanged = Boolean.FALSE, stdWarrenChanged = Boolean.FALSE;
						for( StandardClause masteStandardClause : masterStdClauseList )
						{
							for( StandardClause standardClause : stdClauseList )
							{
								if(standardClause.getClauseType().equalsIgnoreCase("C"))
								{
									if( masteStandardClause.isSelected() && !standardClause.isSelected() 
											&& masteStandardClause.getClauseCode().equals(standardClause.getClauseCode()) && !stdconditionChanged )
									{
										standardConditionCharacteristics = new Characteristics();
										standardConditionCharacteristics.setName( "deselectStndrdCondition" );
										standardConditionCharacteristics.setValue( "Y" );
										validationFact.getCharacteristics().add( standardConditionCharacteristics );
										stdconditionChanged = Boolean.TRUE;
									}
									else if( !masteStandardClause.isSelected() && standardClause.isSelected() 
											&& masteStandardClause.getClauseCode().equals(standardClause.getClauseCode()) && !conditionChanged)
									{
										standardConditionCharacteristics = new Characteristics();
										standardConditionCharacteristics.setName( "conditionsChange" );
										standardConditionCharacteristics.setValue( "Y" );
										validationFact.getCharacteristics().add( standardConditionCharacteristics );
										conditionChanged = Boolean.TRUE;
									}
								}
								else if(standardClause.getClauseType().equalsIgnoreCase("E") && !stdExcluChanged )
								{
									if( masteStandardClause.isSelected() && !standardClause.isSelected() 
											&& masteStandardClause.getClauseCode().equals(standardClause.getClauseCode()) )
									{
										standardConditionCharacteristics = new Characteristics();
										standardConditionCharacteristics.setName( "deselectStndrdExclusions" );
										standardConditionCharacteristics.setValue( "Y" );
										validationFact.getCharacteristics().add( standardConditionCharacteristics );
										stdExcluChanged = Boolean.TRUE;
									}
								}
								else if(standardClause.getClauseType().equalsIgnoreCase("W") && !stdWarrenChanged)
								{
									if( masteStandardClause.isSelected() && !standardClause.isSelected() 
											&& masteStandardClause.getClauseCode().equals(standardClause.getClauseCode()))
									{
										standardConditionCharacteristics = new Characteristics();
										standardConditionCharacteristics.setName( "deselectStndrdWarranties" );
										standardConditionCharacteristics.setValue( "Y" );
										validationFact.getCharacteristics().add( standardConditionCharacteristics );
										stdWarrenChanged = Boolean.TRUE;
									}
								}
							}						
								
						}
						if(!stdconditionChanged){
							standardConditionCharacteristics = new Characteristics();
							standardConditionCharacteristics.setName( "deselectStndrdCondition" );
							standardConditionCharacteristics.setValue( "N" );
							validationFact.getCharacteristics().add( standardConditionCharacteristics );
						}
						if(!conditionChanged){
							standardConditionCharacteristics = new Characteristics();
							standardConditionCharacteristics.setName( "conditionsChange" );
							standardConditionCharacteristics.setValue( "N" );
							validationFact.getCharacteristics().add( standardConditionCharacteristics );
						}
						if(!stdExcluChanged){
							standardConditionCharacteristics = new Characteristics();
							standardConditionCharacteristics.setName( "deselectStndrdExclusions" );
							standardConditionCharacteristics.setValue( "N" );
							validationFact.getCharacteristics().add( standardConditionCharacteristics );
						}
						if(!stdWarrenChanged){
							standardConditionCharacteristics = new Characteristics();
							standardConditionCharacteristics.setName( "deselectStndrdWarranties" );
							standardConditionCharacteristics.setValue( "N" );
							validationFact.getCharacteristics().add( standardConditionCharacteristics );
						}

					}
				}
			}
		}
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
 * 
 * @param policyData
 * @param cancellationDatePostdating
 * @param generalFact
 */
private void setCancelBackDateFact( PolicyDataVO policyData, Characteristics cancellationDateBackdatingCharacteristics, Fact generalFact ){ //added for 139106 WC cancellation
	CommonVO commonVO = policyData.getCommonVO();
	
	Date endtEffectiveDate = policyData.getEndEffectiveDate();
	
	


	
	
	if( !Utils.isEmpty( commonVO ) && !commonVO.getIsQuote() ){
		
		//List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_DATE, commonVO.getPolicyId(), commonVO.getEndtId() );
		Date todayDate = getTodayDate();
		
		String cancellationDateBackdating = new String( "0" );
		/* Backdate check */
		/*if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){

			Date inceptionDate = null;
			for( Object[] result : resultSet ){
				if( Utils.isEmpty( result[ 1 ] ) ){
					inceptionDate = (Date) result[ 0 ];
				}
				else{
					inceptionDate = (Date) result[ 1 ];
				}
			}*/

			if(Utils.isEmpty( endtEffectiveDate )){
				endtEffectiveDate = commonVO.getEndtEffectiveDate();
			}
			
			/*if(!Utils.isEmpty( endtEffectiveDate ) && !Utils.isEmpty( inceptionDate ) && !Utils.isEmpty( todayDate )){
				if( todayDate.after( endtEffectiveDate ) ){
					cancellationDatePostdating = String.valueOf( getDateDifference( endtEffectiveDate,inceptionDate ) );
				}else{*/
			/* Commenting the above code as the postdating days has to be calculated based on the current date */
			if( endtEffectiveDate.after( todayDate ) ){
				

					if (getDateDifference( endtEffectiveDate,todayDate ) < 0){
					
					cancellationDateBackdating = String.valueOf( Math.abs(getDateDifference( endtEffectiveDate,todayDate )) );
				}
				else{
					
					cancellationDateBackdating = "0";
				}			}
			
			if( endtEffectiveDate.before( todayDate ) ){
				

				//cancellationDateBackdating = String.valueOf( Math.abs(getDateDifference( endtEffectiveDate,todayDate )) );
				
				if (getDateDifference( endtEffectiveDate,todayDate ) < 0){
					
					cancellationDateBackdating = String.valueOf( Math.abs(getDateDifference( endtEffectiveDate,todayDate )) );
				}
				else{
					
					cancellationDateBackdating = "0";
				}
				

			}
				//}
			//}
		//}
		
		/*Characteristics endorsementDateBackdatingCharacteristics = new Characteristics();
		endorsementDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
		endorsementDateBackdatingCharacteristics.setValue( endorsementDateBackdating );
		generalInfoCharacteristicList.add( endorsementDateBackdatingCharacteristics );*/
		
			cancellationDateBackdatingCharacteristics.setName( "cancellationDateBackdating" );
			cancellationDateBackdatingCharacteristics.setValue( cancellationDateBackdating );
		
		generalFact.getCharacteristics().add(cancellationDateBackdatingCharacteristics);
		
	}
	
}


private void setEffectiveBackDateValidationFact( PolicyDataVO policyData, Characteristics cancellationDateBackdatingCharacteristics, Fact validationFact ){ //added for 139106 WC cancellation
	CommonVO commonVO = policyData.getCommonVO();
	
	Date endtEffectiveDate = policyData.getEndEffectiveDate();
	
	


	
	
	if( !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() ){
		
		//List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_DATE, commonVO.getPolicyId(), commonVO.getEndtId() );
		Date todayDate = getTodayDate();
		
		String cancellationDateBackdating = new String( "0" );
		/* Backdate check */
		/*if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){

			Date inceptionDate = null;
			for( Object[] result : resultSet ){
				if( Utils.isEmpty( result[ 1 ] ) ){
					inceptionDate = (Date) result[ 0 ];
				}
				else{
					inceptionDate = (Date) result[ 1 ];
				}
			}*/

			if(Utils.isEmpty( endtEffectiveDate )){
				endtEffectiveDate = commonVO.getPolEffectiveDate();
			}
			
			/*if(!Utils.isEmpty( endtEffectiveDate ) && !Utils.isEmpty( inceptionDate ) && !Utils.isEmpty( todayDate )){
				if( todayDate.after( endtEffectiveDate ) ){
					cancellationDatePostdating = String.valueOf( getDateDifference( endtEffectiveDate,inceptionDate ) );
				}else{*/
			/* Commenting the above code as the postdating days has to be calculated based on the current date */
			
			
			if( endtEffectiveDate.before( todayDate ) ){
				

				//cancellationDateBackdating = String.valueOf( Math.abs(getDateDifference( endtEffectiveDate,todayDate )) );
				
				if (getDateDifference( endtEffectiveDate,todayDate ) < 0){
					
					cancellationDateBackdating = String.valueOf( Math.abs(getDateDifference( endtEffectiveDate,todayDate )) );
				}
				else{
					
					cancellationDateBackdating = "0";
				}
				

			}
				//}
			//}
		//}
		
		/*Characteristics endorsementDateBackdatingCharacteristics = new Characteristics();
		endorsementDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
		endorsementDateBackdatingCharacteristics.setValue( endorsementDateBackdating );
		generalInfoCharacteristicList.add( endorsementDateBackdatingCharacteristics );*/
		
			cancellationDateBackdatingCharacteristics.setName( "effDateBackDating" );
			cancellationDateBackdatingCharacteristics.setValue( cancellationDateBackdating );
		
			validationFact.getCharacteristics().add(cancellationDateBackdatingCharacteristics);
		
	}
	
}

private void setEffectivePostDateValidationFact( PolicyDataVO policyData, Characteristics cancellationDatePostdatingCharacteristics, Fact validationFact ){ //added for 139106 WC cancellation
	CommonVO commonVO = policyData.getCommonVO();
	
	Date endtEffectiveDate = policyData.getEndEffectiveDate();
	
	


	
	
	if( !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() ){
		
		//List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_DATE, commonVO.getPolicyId(), commonVO.getEndtId() );
		Date todayDate = getTodayDate();
		
		String cancellationDateBackdating = new String( "0" );
		/* Backdate check */
		/*if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){

			Date inceptionDate = null;
			for( Object[] result : resultSet ){
				if( Utils.isEmpty( result[ 1 ] ) ){
					inceptionDate = (Date) result[ 0 ];
				}
				else{
					inceptionDate = (Date) result[ 1 ];
				}
			}*/

			if(Utils.isEmpty( endtEffectiveDate )){
				endtEffectiveDate = commonVO.getPolEffectiveDate();
			}
			
			/*if(!Utils.isEmpty( endtEffectiveDate ) && !Utils.isEmpty( inceptionDate ) && !Utils.isEmpty( todayDate )){
				if( todayDate.after( endtEffectiveDate ) ){
					cancellationDatePostdating = String.valueOf( getDateDifference( endtEffectiveDate,inceptionDate ) );
				}else{*/
			/* Commenting the above code as the postdating days has to be calculated based on the current date */
			if( endtEffectiveDate.after( todayDate ) ){
				

					if (getDateDifference( endtEffectiveDate,todayDate ) > 0){
					
					cancellationDateBackdating = String.valueOf( Math.abs(getDateDifference( endtEffectiveDate,todayDate )) );
				}
				else{
					
					cancellationDateBackdating = "0";
				}			}
			
		
				//}
			//}
		//}
		
		/*Characteristics endorsementDateBackdatingCharacteristics = new Characteristics();
		endorsementDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
		endorsementDateBackdatingCharacteristics.setValue( endorsementDateBackdating );
		generalInfoCharacteristicList.add( endorsementDateBackdatingCharacteristics );*/
		
			cancellationDatePostdatingCharacteristics.setName( "effDatePostDating" );
			cancellationDatePostdatingCharacteristics.setValue( cancellationDateBackdating );
		
			validationFact.getCharacteristics().add(cancellationDatePostdatingCharacteristics);
		
	}
	
}


}