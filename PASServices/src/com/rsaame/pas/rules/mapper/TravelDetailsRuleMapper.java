package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * 
 * This class will map the details from TravelInsuranceVO object to rule
 * engine's Fact class
 * 
 * @author M1020859
 * 
 */
public class TravelDetailsRuleMapper implements RuleMapper{

	private static final String YES = "Y";

	private final static Logger logger = Logger.getLogger( TravelDetailsRuleMapper.class );

	private static final int DAYDIVIDER = ( 1000 * 60 * 60 * 24 );
	/** Rule Facts and Risk Name declaration Start */
	private static final String RISK_NAME = "TRAVEL";
	private static final String TRAVEL_FACT_NAME = "PersonalTravel";
	private static final String CHAR_GENERAL_QUESTION = "generalQuestion";
	private static final String CHAR_GENERAL_QUESTION_COUNTRY = "generalQuestionCountry"; //Travel Scope 131378
	private static final String CHAR_TRAVELLER_MAX_AGE = "travellerMaxAge";
	private static final String CHAR_TARVELLER_MIN_AGE = "travellerMinAge";
	private static final String CHAR_TARVELLER_MAX_NUMBER = "travellerMaxNumber";
	private static final String CHAR_TRAVEL_PERIOD = "travelPeriod";
	private static final String CHAR_TRAVEL_RELATION = "relation";
	private static final String CHAR_TRAVEL_LOCATION = "travelLocation";
	private final static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private static final List<String> NON_VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.NON_VERSION_STATUS ) );
	private static final List<String> VERSION_STATUS = Arrays.asList( Utils.getMultiValueAppConfig( SvcConstants.VERSION_STATUS ) );

	/** Rule Facts and Risk Name declaration End */

	@Override
	public RuleInfo createRiskDetails( BaseVO travelInsVO, int section, String roleType, com.rsame.rulesengine.restfulclient.request.RuleInfo ruleInformation ){

		logger.debug( "TravelDetailsRuleMapper -------------> Start mapping the TravelInsuranceVO object for rules invocation" );
		List<com.rsame.rulesengine.restfulclient.request.RiskDetails> riskDetailList = ruleInformation.getRiskDetails();
		com.rsame.rulesengine.restfulclient.request.RiskDetails riskDetails = new com.rsame.rulesengine.restfulclient.request.RiskDetails();
		riskDetails.setRiskName( RISK_NAME );
		riskDetails.setRiskId( new Integer( "9999" ) );
		List<com.rsame.rulesengine.restfulclient.request.Fact> factList = riskDetails.getFact();
		String travellerMaxAge = new String( "0" );
		String travellerMinAge = new String( "0" );
		String travellerMaxNumber ;
		String travelPrd ;
		String travelLoc ;
		String relaton ;
		com.rsame.rulesengine.restfulclient.request.Fact userFact = new com.rsame.rulesengine.restfulclient.request.Fact();
		userFact.setFactName( RulesConstants.FACT_USER );
		com.rsame.rulesengine.restfulclient.request.Fact traveldetailsFact = null;
		com.rsame.rulesengine.restfulclient.request.Fact generalInfoFact = new com.rsame.rulesengine.restfulclient.request.Fact();
		generalInfoFact.setFactName( RulesConstants.FACT_GENERAL_TRAVEL );
		com.rsame.rulesengine.restfulclient.request.Characteristics userCharacteristics = new com.rsame.rulesengine.restfulclient.request.Characteristics();
		userCharacteristics.setName( "role" );
		userCharacteristics.setValue( roleType );
		userFact.getCharacteristics().add( userCharacteristics );
		factList.add( userFact );
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) travelInsVO;
		TravelDetailsVO travelDetailsVO = travelInsuranceVO.getTravelDetailsVO();

		/** Common Info set Start */
		travelLoc = travelDetailsVO.getTravelLocation();
		travelPrd = String.valueOf( travelInsuranceVO.getPolicyTerm() ); 
				//getDateDifference( travelInsuranceVO.getScheme().getExpiryDate(), travelInsuranceVO.getScheme().getEffDate() ).toString();
		travellerMaxNumber = String.valueOf( travelDetailsVO.getTravelerDetailsList().size() );
		/** Common Info set End */
		/** Setting the underwriter questions if present */
		logger.debug( "TravelDetailsRuleMapper -------------> Start mapping the underwriter questions for rules invocation" );
		
		//Commented variables uwQuestionsValue,uwQuestionsAnsList to avoid Dead store to local variable sonar violation 0n 19-9-2017
		//String uwQuestionsValue = new String( "0" );
		//List<String> uwQuestionsAnsList = new ArrayList<String>();
		Map<Short,String> map = new HashMap<Short,String>();//Travel Scope 131378

		/** START CREATING FACTS FOR GENERAL INFO */

		String effectiveDateBackdating = new String( "0" );
		String effectiveDatePostdating = new String( "0" );
		String issueDateBackdating = new String( "0" );
		String issueDatePostDating = new String( "0" );
		
		String renEffectiveDateBackdating = "0";
		String renEffectiveDatePostdating = "0";
		
		String renIssueDateBackdating = "0";
		String renIssueDatePostDating = "0";
		
		String city = null;
		String nationality = null;

		String endorsementDateBackdating = new String( "0" );
		String endorsementDatePostdating = new String( "0" );

		Date todayDate = getTodayDate();
		
		if( !Utils.isEmpty( travelInsVO ) ){
			PolicyDataVO policyDataVO = (PolicyDataVO) travelInsVO;
			CommonVO commonVO1 = policyDataVO.getCommonVO();
			//policyDataVO.setIsQuote( true );
			if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getEffDate() ) ){
				Date effectiveDate = policyDataVO.getScheme().getEffDate();
				if( !Utils.isEmpty( effectiveDate ) && !Utils.isEmpty( commonVO1 ) && commonVO1.getIsQuote() &&  
						!Utils.isEmpty( commonVO1.getDocCode() ) && commonVO1.getDocCode() == 6) {
					if( todayDate.after( effectiveDate ) )
						renEffectiveDateBackdating = String.valueOf( getDateDifference( todayDate, effectiveDate ) );
					else
						renEffectiveDatePostdating = String.valueOf( getDateDifference( effectiveDate, todayDate ) );
				}else if( !Utils.isEmpty( effectiveDate ) && !Utils.isEmpty( commonVO1 ) && commonVO1.getIsQuote() ){
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
				
				if( !Utils.isEmpty( accountingDate ) && !Utils.isEmpty( commonVO1 ) && commonVO1.getIsQuote() &&  
						!Utils.isEmpty( commonVO1.getDocCode() ) && commonVO1.getDocCode() == 6) 
				{
					if( todayDate.after( accountingDate ) )
						renIssueDateBackdating = String.valueOf( getDateDifference( todayDate, accountingDate ) );
					else
						renIssueDatePostDating = String.valueOf( getDateDifference( accountingDate, todayDate ) );
				}
				
				else if( !Utils.isEmpty( accountingDate ) && !Utils.isEmpty( commonVO1 ) && commonVO1.getIsQuote() )
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
			
			if( !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getCity() ) ){
				city = String.valueOf( policyDataVO.getGeneralInfo().getInsured().getCity() );
			}

			if( !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getNationality() ) ){
				nationality = String.valueOf( policyDataVO.getGeneralInfo().getInsured().getNationality() );
			}

			Characteristics effectiveDateBackdatingCharacteristics = new Characteristics();
			effectiveDateBackdatingCharacteristics.setName( "effectiveDateBackdating" );
			effectiveDateBackdatingCharacteristics.setValue( effectiveDateBackdating );
			generalInfoFact.getCharacteristics().add( effectiveDateBackdatingCharacteristics );

			Characteristics effectiveDatePostdatingCharacteristics = new Characteristics();
			effectiveDatePostdatingCharacteristics.setName( "effectiveDatePostdating" );
			effectiveDatePostdatingCharacteristics.setValue( effectiveDatePostdating );
			generalInfoFact.getCharacteristics().add( effectiveDatePostdatingCharacteristics );

			Characteristics issueDateBackdatingCharacteristics = new Characteristics();
			issueDateBackdatingCharacteristics.setName( "issueDateBackdating" );
			issueDateBackdatingCharacteristics.setValue( issueDateBackdating );
			generalInfoFact.getCharacteristics().add( issueDateBackdatingCharacteristics );

			Characteristics issueDatePostdatingCharacteristics = new Characteristics();
			issueDatePostdatingCharacteristics.setName( "issueDatePostdating" );
			issueDatePostdatingCharacteristics.setValue( issueDatePostDating );
			generalInfoFact.getCharacteristics().add( issueDatePostdatingCharacteristics );
			
			Characteristics renIssueDateBackdatingCharacteristics = new Characteristics();
			renIssueDateBackdatingCharacteristics.setName( "renIssueDateBackdating" );
			renIssueDateBackdatingCharacteristics.setValue( renIssueDateBackdating );
			generalInfoFact.getCharacteristics().add( renIssueDateBackdatingCharacteristics );
			
			Characteristics renIssueDatePostDatingCharacteristics = new Characteristics();
			renIssueDatePostDatingCharacteristics.setName( "renIssueDatePostDating" );
			renIssueDatePostDatingCharacteristics.setValue( renIssueDatePostDating );
			generalInfoFact.getCharacteristics().add( renIssueDatePostDatingCharacteristics );
			
			
			Characteristics renEffectiveDateBackdatingCharacteristics = new Characteristics();
			renEffectiveDateBackdatingCharacteristics.setName( "renEffectiveDateBackdating" );
			renEffectiveDateBackdatingCharacteristics.setValue( renEffectiveDateBackdating );
			generalInfoFact.getCharacteristics().add( renEffectiveDateBackdatingCharacteristics );
			
			Characteristics renEffectiveDatePostdatingCharacteristics = new Characteristics();
			renEffectiveDatePostdatingCharacteristics.setName( "renEffectiveDatePostdating" );
			renEffectiveDatePostdatingCharacteristics.setValue( renEffectiveDatePostdating );
			generalInfoFact.getCharacteristics().add( renEffectiveDatePostdatingCharacteristics );

			if( !Utils.isEmpty( city ) ){
				Characteristics cityCharacteristics = new Characteristics();
				cityCharacteristics.setName( "city" );
				cityCharacteristics.setValue( city );
				generalInfoFact.getCharacteristics().add( cityCharacteristics );
			}

			if( !Utils.isEmpty( nationality ) ){
				Characteristics nationalityCharacteristics = new Characteristics();
				nationalityCharacteristics.setName( "nationality" );
				nationalityCharacteristics.setValue( nationality );
				generalInfoFact.getCharacteristics().add( nationalityCharacteristics );
			}
			
			/*
			 * Travel : Endorsement should be role based if the travel is already started.
			 */
			if( SvcUtils.getBasicFlowCommonResolveReferral( policyDataVO.getCommonVO() ).equals( Flow.AMEND_POL ) ){
				Characteristics hasTravelStartedCharacteristics = new Characteristics();
				hasTravelStartedCharacteristics.setName( "hasTravelStarted" );

				if( hasTravelStarted( policyDataVO ) ){
					hasTravelStartedCharacteristics.setValue( YES );
				}
				else{
					hasTravelStartedCharacteristics.setValue( "N" );
				}

				generalInfoFact.getCharacteristics().add( hasTravelStartedCharacteristics );
			}
			
			/* Endorsement related mappers */
			if( !Utils.isEmpty( travelInsuranceVO ) ){
				PolicyDataVO policyData = travelInsuranceVO;
				CommonVO commonVO = policyData.getCommonVO();

				Date endtEffectiveDate = policyData.getEndEffectiveDate();

				if( !Utils.isEmpty( commonVO ) && !commonVO.getIsQuote() ){
					List<Object[]> resultSet = DAOUtils
							.getSqlResultForPas( QueryConstants.FETCH_DATE, policyData.getCommonVO().getPolicyId(), policyData.getCommonVO().getEndtId() );

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

						if( Utils.isEmpty( endtEffectiveDate ) ){
							endtEffectiveDate = commonVO.getEndtEffectiveDate();
						}

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

					Characteristics endorsementDateBackdatingCharacteristics = new Characteristics();
					endorsementDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
					endorsementDateBackdatingCharacteristics.setValue( endorsementDateBackdating );
					generalInfoFact.getCharacteristics().add( endorsementDateBackdatingCharacteristics );

					Characteristics endorsementDatePostDatingCharacteristics = new Characteristics();
					endorsementDatePostDatingCharacteristics.setName( "endorsementDatePostdating" );
					endorsementDatePostDatingCharacteristics.setValue( endorsementDatePostdating );
					generalInfoFact.getCharacteristics().add( endorsementDatePostDatingCharacteristics );
					
					ClaimsSvc claimsService = (ClaimsSvc) Utils.getBean( "claimsSvc" );
					if(!commonVO.getIsQuote() && claimsService.checkClaimsExistForPolicyNumber( commonVO.getPolicyNo() ) ){
						travelPeriodreductionFact(generalInfoFact, policyData, commonVO);
					}
					
				}
			}

			factList.add( generalInfoFact );
			/** END CREATING FACTS FOR GENERAL INFO*/

			/** START CREATING FOR TRAVELLER */
			if( !Utils.isEmpty( travelInsuranceVO.getUwQuestions() ) && !Utils.isEmpty( travelInsuranceVO.getUwQuestions().getQuestions() ) ){
				for( UWQuestionVO uwQuesVO : travelInsuranceVO.getUwQuestions().getQuestions() ){

					if( !Utils.isEmpty( uwQuesVO.getResponse() ) && !Utils.isEmpty( uwQuesVO.getQId() )){
						map.put(uwQuesVO.getQId(),uwQuesVO.getResponse());
						//uwQuestionsValue = uwQuesVO.getResponse();
					}
					/*if( !Utils.isEmpty( uwQuestionsValue ) ){
						uwQuestionsAnsList.add( uwQuestionsValue );
					}*/
				}
			}
			logger.debug( "TravelDetailsRuleMapper -------------> Start iterarting the travelerDetailsVO object" );
			for( TravelerDetailsVO travelerDets : travelDetailsVO.getTravelerDetailsList() ){
				traveldetailsFact = new com.rsame.rulesengine.restfulclient.request.Fact();
				traveldetailsFact.setFactName( TRAVEL_FACT_NAME ); //Setting for every traveler
				/** Setting the underwriter questions start */
		/*		if( !Utils.isEmpty( uwQuestionsAnsList ) ){
					for( String uwQuesValue : uwQuestionsAnsList ){
						Characteristics genQuestions = new Characteristics();
						genQuestions.setName( CHAR_GENERAL_QUESTION );
						genQuestions.setValue( uwQuesValue );
						traveldetailsFact.getCharacteristics().add( genQuestions );
					}
				}*/
				/*
				 * Travel Scope Changes ,Commented the earlier logic and added Key value Pair  for UW Questions
				 * 131378
				 */
				for (Entry<Short, String> entry : map.entrySet()) {
				    Short key = entry.getKey();
				    String value = entry.getValue();
				    logger.debug( "TravelDetailsRuleMapper **** UW Question Key -------------> "+key);
				    logger.debug( "TravelDetailsRuleMapper **** UW Question Value -------------> "+value);
				    
				      if(key==SvcConstants.TRAVEL_GENERAL_QUESTION ){
				    	  
				    	  Characteristics genQuestions = new Characteristics();
							genQuestions.setName( CHAR_GENERAL_QUESTION );
							genQuestions.setValue( value );
							traveldetailsFact.getCharacteristics().add( genQuestions );				    	  
				      }
				      if(key==SvcConstants.TRAVEL_GENERAL_QUESTION_COUNTRY ){
				    	  
				    	  Characteristics genQuestions = new Characteristics();
							genQuestions.setName( CHAR_GENERAL_QUESTION_COUNTRY );
							genQuestions.setValue( value );
							traveldetailsFact.getCharacteristics().add( genQuestions );
				    	  
				      }
				}
				
				/** Setting the underwriter questions end */
				relaton = travelerDets.getRelation().toString();
				if( !Utils.isEmpty( policyDataVO.getScheme() ) && !Utils.isEmpty( policyDataVO.getScheme().getEffDate() ) ){
					travellerMaxAge = SvcUtils.getAge( travelerDets.getDateOfBirth(),  policyDataVO.getScheme().getExpiryDate() ).toString();
					
					if(!Utils.isEmpty( travelerDets ) && !Utils.isEmpty( travelerDets.getRelation() ) && travelerDets.getRelation() == 1){
						travellerMinAge = SvcUtils.getAge( travelerDets.getDateOfBirth(),  policyDataVO.getScheme().getEffDate()  ).toString();
					}else{
						travellerMinAge = new String("99");
					}
				}

				/** Setting the TRAVEL Related Characteristics start */
				Characteristics travelerMaxAge = new Characteristics();
				travelerMaxAge.setName( CHAR_TRAVELLER_MAX_AGE );
				travelerMaxAge.setValue( travellerMaxAge );
				traveldetailsFact.getCharacteristics().add( travelerMaxAge );
				
				Characteristics travelerMinAge = new Characteristics();
				travelerMinAge.setName( CHAR_TARVELLER_MIN_AGE );
				travelerMinAge.setValue( travellerMinAge );
				traveldetailsFact.getCharacteristics().add( travelerMinAge );
				

				Characteristics travelerMaxNumber = new Characteristics();
				travelerMaxNumber.setName( CHAR_TARVELLER_MAX_NUMBER );
				travelerMaxNumber.setValue( travellerMaxNumber );
				traveldetailsFact.getCharacteristics().add( travelerMaxNumber );

				Characteristics travelPeriod = new Characteristics();
				travelPeriod.setName( CHAR_TRAVEL_PERIOD );
				travelPeriod.setValue( travelPrd );
				traveldetailsFact.getCharacteristics().add( travelPeriod );

				Characteristics travelLocation = new Characteristics();
				travelLocation.setName( CHAR_TRAVEL_LOCATION );
				travelLocation.setValue( travelLoc );
				traveldetailsFact.getCharacteristics().add( travelLocation );

				Characteristics relation = new Characteristics();
				relation.setName( CHAR_TRAVEL_RELATION );
				relation.setValue( relaton );
				traveldetailsFact.getCharacteristics().add( relation );

				factList.add( traveldetailsFact );
			}
			riskDetailList.add( riskDetails );
			logger.debug( "TravelDetailsRuleMapper -------------> Creation of travel related characteristics completed" );
		}

		return ruleInformation;
	}

	private void travelPeriodreductionFact( Fact generalInfoFact, PolicyDataVO policyData, CommonVO commonVO ){

		String condition = null;
		if( NON_VERSION_STATUS.contains( commonVO.getStatus().toString() ) ){
			condition = "<";
		}
		else{
			condition = "=";
		}
		Integer policyReduced = 0;
		java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPASNewSession( "select pol_expiry_date - to_date(\'" + format.format( policyData.getScheme().getExpiryDate() )
				+ "\',\'DD/MM/YYYY\')  from t_trn_policy where pol_policy_id = " + commonVO.getPolicyId() + " and Pol_Endt_Id" + condition + commonVO.getEndtId() +" order BY Pol_Endt_Id desc" );
		if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
			policyReduced = ( (BigDecimal) valueHolder.get( 0 ) ).intValue();
		}
		if( policyReduced > 0 ){
			Characteristics policyPeriodReduced = new Characteristics();
			policyPeriodReduced.setName( "policyPeriodReduced" );
			policyPeriodReduced.setValue( YES );
			generalInfoFact.getCharacteristics().add( policyPeriodReduced );
		}
	}
	

	private boolean hasTravelStarted( PolicyDataVO policyDataVO ){
		
		boolean hasTravelStarted = false;	
		if( policyDataVO.getScheme().getEffDate().compareTo( new Date() ) <= 0 ){
			hasTravelStarted = true;
		}
		return hasTravelStarted;
	}

	/**
	 * Method to get the date difference
	 * 
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
		//cal.add( Calendar.DAY_OF_MONTH, 1 );
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );
		Date today = cal.getTime();
		return today;
	}

}
