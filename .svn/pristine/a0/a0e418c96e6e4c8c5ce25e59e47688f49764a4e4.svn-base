/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RuleResponseType;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.rulesengine.client.RuleAttribute;
import com.rsaame.rulesengine.client.RuleExecutionResponse;
import com.rsaame.rulesengine.client.RuleFault;
import com.rsaame.rulesengine.client.RuleSetResponse;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.ExecuteRuleRequest;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RiskDetails;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * @author M1014739
 * 
 */
public class RuleServiceResponseMapper{

	private static final String BROKER_MAX_CREDIT_LIMIT = "brokerMaxCreditLimit";

	private static final String BROKER_MIN_CREDIT_LIMIT = "brokerMinCreditLimit";

	private static final int VALID_INDEX = -1;

	private final static Logger logger = Logger.getLogger( RuleServiceResponseMapper.class );

	private final String GRADIENT = "Gradient";
	private final String FALSE = "false";
	private final String ROLE = "role";
	
	public BaseVO mapRuleServiceResponse( BaseVO policyBaseVO, RuleExecutionResponse response ){
		logger.debug( "Inside mapRuleServiceResponse method of RuleServiceResponseMapper." );

		List<RuleSetResponse> ruleSetRespList = null;
		Iterator<RuleSetResponse> ruleSetRespListItr = null;
		RuleSetResponse ruleSetResponse = null;
		String riskId = null;
		List<RuleAttribute> ruleRespAttList = null;
		Iterator<RuleAttribute> ruleRespAttListItr = null;
		RuleAttribute ruleAttribute = null;
		Map<String, String> gradientMap = null;
		Map<String, String> valueMap = null;
		String attName = null;
		String attValue = null;
		ReferralListVO referralListVO = null;
		ReferralVO referralVO = null;
		List<ReferralVO> referralList = new ArrayList<ReferralVO>( 1 );
		String sectionName = null;

		
		//Check if there is any error message from Rules Engine
		checkForRulesEngineError( response );

		PolicyVO policyVO = (PolicyVO) policyBaseVO;
		
		ruleSetRespList = response.getRuleSetResponse();

		if( !Utils.isEmpty( ruleSetRespList ) ){

			// Iterating over the list for multiple locations
			ruleSetRespListItr = ruleSetRespList.iterator();
			while( ruleSetRespListItr.hasNext() ){
				ruleSetResponse = ruleSetRespListItr.next();
				riskId = ruleSetResponse.getRiskId();
				sectionName = ruleSetResponse.getRuleSetName();
				ruleRespAttList = ruleSetResponse.getRuleResponseAttribute();

				if( !Utils.isEmpty( ruleRespAttList ) ){
					gradientMap = new HashMap<String, String>();
					valueMap = new HashMap<String, String>();

					/**
					 * Iterating over the rules response to segregate
					 * the actual value and the Rules Engine response
					 */
					ruleRespAttListItr = ruleRespAttList.iterator();
					while( ruleRespAttListItr.hasNext() ){
						ruleAttribute = ruleRespAttListItr.next();
						attName = ruleAttribute.getAttributeName();
						attValue = ruleAttribute.getAttributeValue();

						if( attName.contains( GRADIENT ) ){
							gradientMap.put( attName, attValue );
						}
						else{
							valueMap.put( attName, attValue );
						}
					}
					/**
					 * Removing the role attribute as it is not required
					 * for generating the referral messages
					 */
					valueMap.remove( ROLE );
				}

				valueMap = createMapForReferrals( valueMap, gradientMap );

				referralVO = (ReferralVO) createReferralMessages( valueMap, policyVO );

				if( !Utils.isEmpty( referralVO ) ){
					referralVO.setRiskGroupId( riskId );
					referralVO.setSectionId( Integer.valueOf( Utils.getSingleValueAppConfig( sectionName ) ) );
					referralList.add( referralVO );
					/** Commented as there is an approach change to return the Referral VO from PASRulesTask */
					//setReferralVOInPolicyVO( referralVO, policyVO, sectionName );
				}
			}
			if( !Utils.isEmpty( referralList ) ){
				referralListVO = new ReferralListVO();
				referralListVO.setReferrals( referralList );
			}
		}

		return referralListVO;
	}

	// for response mapping of rest rules
	public BaseVO mapRestFulRuleResponse( BaseVO policyBaseVO, Integer[] sectionArray, String roleType,
			com.rsame.rulesengine.restfulclient.response.ExecuteRuleResponse ruleEngineResponse, ExecuteRuleRequest ruleEngineRequest ){

		List<Characteristics> allRequestCharacteristicsList = new ArrayList<Characteristics>();
		RuleInfo ruleInfo = ruleEngineRequest.getRuleInfo();
		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();
		Map<String, Map<String, String>> referralMsgMap = new HashMap<String, Map<String, String>>();
		Map<String, String> refValAndRefTxtMap = null;
		List<String> passedFactList = new ArrayList<String>();

		List<Fact> factorList = null;
		for( RiskDetails riskDetails : riskDetailList ){
			factorList = riskDetails.getFact();
			for( Fact fact : factorList ){
				allRequestCharacteristicsList.addAll( fact.getCharacteristics() );
			}
		}

		java.lang.reflect.Method[] methods = ruleEngineResponse.getClass().getDeclaredMethods();

		for( java.lang.reflect.Method method : methods ){

			System.out.println( method.getName() );

		}

		List<com.rsame.rulesengine.restfulclient.response.RuleSetResponse> responseSet = ruleEngineResponse.getRuleSetResponseForMapping( "map" );

		removeApprovedRules( responseSet, allRequestCharacteristicsList, ( (PolicyDataVO) policyBaseVO ).getCommonVO() );

		List<com.rsame.rulesengine.restfulclient.response.Characteristics> allResponsecharacteristics = new ArrayList<com.rsame.rulesengine.restfulclient.response.Characteristics>();
		for( com.rsame.rulesengine.restfulclient.response.RuleSetResponse ruleSetResponse : responseSet ){

			allResponsecharacteristics.addAll( ruleSetResponse.getCharacteristics() );

		}

		removeInvalidReferrals( allResponsecharacteristics );

		/* Assumption is that the validation rules fact is configured as the first fact-key so that as soon as we get to know it has failed, 
		 * we can throw exception and show hard stop referral message without checking for other fact-keys.*/
		String[] factKeys = Utils.getMultiValueAppConfig( "RULE_PRIORITY" );
		ReferralListVO referralListVO = null;
		List<ReferralVO> referralList = null;
		boolean isRuleFailed = false;
		boolean isValidationReferral = false;
		for( String factKey : factKeys ){

			String[] allFacts = Utils.getMultiValueAppConfig( factKey );
			referralList = new ArrayList<ReferralVO>();
			List<String> referralText = new ArrayList<String>();

			for( String fact : allFacts ){
				com.rsame.rulesengine.restfulclient.response.Characteristics finderResponseCharacteristics = new com.rsame.rulesengine.restfulclient.response.Characteristics();
				finderResponseCharacteristics.setName( fact );

				int factOccurance = Collections.frequency( allResponsecharacteristics, finderResponseCharacteristics );

				for( int eachOccurance = 0; eachOccurance < factOccurance; eachOccurance++ ){

					if( allResponsecharacteristics.indexOf( finderResponseCharacteristics ) > -1 ){
						com.rsame.rulesengine.restfulclient.response.Characteristics responseCharacteristics = allResponsecharacteristics.get( allResponsecharacteristics
								.indexOf( finderResponseCharacteristics ) );

						Characteristics finderRequestCharacteristics = new Characteristics();
						Characteristics requestCharacteristics = new Characteristics();
						finderRequestCharacteristics.setName( fact );
						if( allRequestCharacteristicsList.indexOf( finderRequestCharacteristics ) > -1 ){
							requestCharacteristics = allRequestCharacteristicsList.get( allRequestCharacteristicsList.indexOf( finderRequestCharacteristics ) );
						}
						String refferalMsg = null;
						System.out.println( "Response:" + responseCharacteristics.getName() );
						System.out.println( "Value:" + responseCharacteristics.getValue() );
						
						
						if( responseCharacteristics.getValue().equalsIgnoreCase( FALSE )){
							refferalMsg = isRuleFailed( responseCharacteristics.getName(), requestCharacteristics.getValue() );
							System.out.println( "Referral Message:" + refferalMsg );
							if( !Utils.isEmpty( refferalMsg ) ){
								referralText.add( refferalMsg );
								refValAndRefTxtMap = new HashMap<String, String>();
								refValAndRefTxtMap.put( requestCharacteristics.getValue(), refferalMsg );
								referralMsgMap.put( fact, refValAndRefTxtMap );
							}
							isRuleFailed = true;
						}
						else{
							passedFactList.add(responseCharacteristics.getName());
						}

						responseCharacteristics.setName( responseCharacteristics.getName() + com.Constant.CONST_ALREADYREAD );
						requestCharacteristics.setName( requestCharacteristics.getName() + com.Constant.CONST_ALREADYREAD );
					}
				}

			}

			referralText.removeAll( Collections.singleton( null ) );

			ReferralVO referralVO = null;
			if( !Utils.isEmpty( referralText ) ){
				System.out.println( "HHH - freferralText is: " + referralText);

				referralVO = new ReferralVO();
				referralVO.setReferralText( referralText );
				referralVO.setRefDataTextField( referralMsgMap );
				
				if(referralMsgMap.containsKey( BROKER_MIN_CREDIT_LIMIT ) && !referralMsgMap.containsKey( BROKER_MAX_CREDIT_LIMIT ) ){
					referralVO.setMessage( true );
				}
				
				if(referralMsgMap.containsKey( BROKER_MAX_CREDIT_LIMIT )){
					referralVO.setNotMessage( true );
					referralText.remove( Utils.getSingleValueAppConfig( BROKER_MIN_CREDIT_LIMIT ) );
					referralMsgMap.remove( BROKER_MIN_CREDIT_LIMIT );
				}
			}

			if( !Utils.isEmpty( referralVO ) ){
				referralList.add( referralVO );
				System.out.println( "HHH - factKey is: " + factKey);

				if( factKey.equalsIgnoreCase( Utils.getSingleValueAppConfig( "VALIDATION_RULES_FACT_KEY" ) ) ){
					isValidationReferral = true;
					break; //Exit for loop once the hard stop rules fail.
				}
			}
		}

		/*
		 * After rule validation, remove the referral data already saved in DB if the rule has passed for the same fact
		 */
		if( !Utils.isEmpty( ( (PolicyDataVO) policyBaseVO ).getCommonVO().getPolicyId() ) 
				&& !Utils.isEmpty(passedFactList)
				&& !( (PolicyDataVO) policyBaseVO ).getCommonVO().getAppFlow().equals(Flow.RESOLVE_REFERAL) ){
			DataHolderVO<Object []> dataHolder = new DataHolderVO<Object []>();
			Object [] inputData = {( (PolicyDataVO) policyBaseVO ).getCommonVO(), passedFactList };
			dataHolder.setData(inputData);
			TaskExecutor.executeTasks("REMOVE_PAS_REFERRAL", dataHolder);
		}
		
		if( !Utils.isEmpty( referralList ) ){
			referralListVO = new ReferralListVO();
			referralListVO.setReferrals( referralList );
			referralListVO.setIsValidationReferral( isValidationReferral );
		}
		if( isRuleFailed ){
			BusinessException exception = new BusinessException( "", null, "" );
			exception.setExceptionData( referralListVO );
			throw exception;
		}

		return referralListVO;
	}
	
	
	/**
	 * Method to capture the rule response with different types of results ie Pass, Fail, Info & HardStop
	 * @param policyBaseVO
	 * @param sectionArray
	 * @param ruleEngineResponse
	 * @param ruleEngineRequest
	 * @return
	 */
	public BaseVO mapRestFulRuleResponse( BaseVO policyBaseVO, Integer[] sectionArray, 
			com.rsame.rulesengine.restfulclient.response.ExecuteRuleResponse ruleEngineResponse, ExecuteRuleRequest ruleEngineRequest ){

		List<Characteristics> allRequestCharacteristicsList = new ArrayList<Characteristics>();
		RuleInfo ruleInfo = ruleEngineRequest.getRuleInfo();
		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();
		Map<String, Map<String, String>> referralMsgMap ;
		Map<String, String> refValAndRefTxtMap = null;
		RuleResponseType responseType = RuleResponseType.Pass;
		List<String> passedFactList = new ArrayList<String>();

		List<Fact> factorList = null;
		for( RiskDetails riskDetails : riskDetailList ){
			factorList = riskDetails.getFact();
			for( Fact fact : factorList ){
				allRequestCharacteristicsList.addAll( fact.getCharacteristics() );
			}
		}

		List<com.rsame.rulesengine.restfulclient.response.RuleSetResponse> responseSet = ruleEngineResponse.getRuleSetResponseForMapping( "map" );

		removeApprovedRules( responseSet, allRequestCharacteristicsList, ( (PolicyDataVO) policyBaseVO ).getCommonVO() );

		List<com.rsame.rulesengine.restfulclient.response.Characteristics> allResponsecharacteristics = new ArrayList<com.rsame.rulesengine.restfulclient.response.Characteristics>();
		for( com.rsame.rulesengine.restfulclient.response.RuleSetResponse ruleSetResponse : responseSet ){

			allResponsecharacteristics.addAll( ruleSetResponse.getCharacteristics() );

		}

		removeInvalidReferrals( allResponsecharacteristics );

		/* Assumption is that the validation rules fact is configured as the first fact-key so that as soon as we get to know it has failed, 
		 * we can throw exception and show hard stop referral message without checking for other fact-keys.*/
		ReferralListVO referralListVO = new ReferralListVO();//null;
		List<ReferralVO> referralList = null;

		referralList = new ArrayList<ReferralVO>();
		List<String> referralText ;

		for (com.rsame.rulesengine.restfulclient.response.Characteristics responseChar : allResponsecharacteristics){
				
				String fact = responseChar.getName();
				ReferralVO referralVO = null;
				referralText = new ArrayList<String>();
				referralMsgMap = new HashMap<String, Map<String, String>>();
				
				com.rsame.rulesengine.restfulclient.response.Characteristics finderResponseCharacteristics = new com.rsame.rulesengine.restfulclient.response.Characteristics();
				finderResponseCharacteristics.setName( fact );

				int factOccurance = Collections.frequency( allResponsecharacteristics, finderResponseCharacteristics );

				for( int eachOccurance = 0; eachOccurance < factOccurance; eachOccurance++ ){

					if( allResponsecharacteristics.indexOf( finderResponseCharacteristics ) > -1 ){
						com.rsame.rulesengine.restfulclient.response.Characteristics responseCharacteristics = allResponsecharacteristics.get( allResponsecharacteristics
								.indexOf( finderResponseCharacteristics ) );

						Characteristics finderRequestCharacteristics = new Characteristics();
						Characteristics requestCharacteristics = new Characteristics();
						finderRequestCharacteristics.setName( fact );
						if( allRequestCharacteristicsList.indexOf( finderRequestCharacteristics ) > -1 ){
							requestCharacteristics = allRequestCharacteristicsList.get( allRequestCharacteristicsList.indexOf( finderRequestCharacteristics ) );
						}
						String refferalMsg = null;
						System.out.println( "Response:" + responseCharacteristics.getName() );
						System.out.println( "Value:" + responseCharacteristics.getValue() );
						
						
						if(responseCharacteristics.getValue().equalsIgnoreCase("false") ){  //added for 139106  - WC cancellation 139106
							logger.debug("HHH - Setting the  responseCharacteristics value to Fail");
							responseCharacteristics.setValue(RuleResponseType.Fail.toString());

						}
						
						//FOR WC CTP scenario 139106
						if(responseCharacteristics.getValue().equalsIgnoreCase("fail") && (responseCharacteristics.getName().equalsIgnoreCase("effDateBackDating") || (responseCharacteristics.getName().equalsIgnoreCase("effDatePostDating"))) ){  //added for 139106  - WC cancellation
							logger.debug("HHH - Setting the  responseCharacteristics value to HardStop");
							responseCharacteristics.setValue(RuleResponseType.HardStop.toString());

						}
						
						
						else if(!RuleResponseType.contains(responseCharacteristics.getValue())){
							responseCharacteristics.setValue(RuleResponseType.Pass.toString());
						}
						
						
						if( ( responseCharacteristics.getValue().equalsIgnoreCase( RuleResponseType.Fail.toString() ) ||
								responseCharacteristics.getValue().equalsIgnoreCase( RuleResponseType.Info.toString() ) ||
								responseCharacteristics.getValue().equalsIgnoreCase( RuleResponseType.HardStop.toString() ) ) &&
								RuleResponseType.valueOf( responseCharacteristics.getValue() ).getPrecedence() >= responseType.getPrecedence() ){
							refferalMsg = isRuleFailed( responseCharacteristics.getName(), requestCharacteristics.getValue() );
							System.out.println( "Referral Message:" + refferalMsg );
							if( !Utils.isEmpty( refferalMsg ) ){
								referralText.add( refferalMsg );
								refValAndRefTxtMap = new HashMap<String, String>();
								refValAndRefTxtMap.put( requestCharacteristics.getValue(), refferalMsg );
								referralMsgMap.put( fact, refValAndRefTxtMap );
								
								referralText.removeAll( Collections.singleton( null ) );

								referralVO = null;
								if( (!Utils.isEmpty( referralText )) ){
									referralVO = new ReferralVO();
									referralVO.setReferralText( referralText );
									referralVO.setRefDataTextField( referralMsgMap );
									referralVO.setActionIdentifier(responseCharacteristics.getValue());
								}
							}
							//isRuleFailed = true;
						}
						else if(responseCharacteristics.getValue().equalsIgnoreCase( RuleResponseType.Pass.toString() )){
							passedFactList.add(responseCharacteristics.getName());
						}

						responseCharacteristics.setName( responseCharacteristics.getName() + com.Constant.CONST_ALREADYREAD );
						requestCharacteristics.setName( requestCharacteristics.getName() + com.Constant.CONST_ALREADYREAD );
						
						if(RuleResponseType.valueOf( responseCharacteristics.getValue() ).getPrecedence() > responseType.getPrecedence()){
							responseType = RuleResponseType.valueOf( responseCharacteristics.getValue() );
						}
					}
				}
				if( !Utils.isEmpty( referralVO ) ){
					referralList.add( referralVO );

					if( responseType.equals(RuleResponseType.HardStop) ){
						break; //Exit for loop once the hard stop rules fail.
					}
				}
			}

		if( !Utils.isEmpty( referralList ) ){
			for ( int i = 0; i < referralList.size(); i++ ){
				if( RuleResponseType.valueOf(referralList.get(i).getActionIdentifier()).getPrecedence() != responseType.getPrecedence()){
					referralList.set(i, null);// = null;
				}
			}
			referralList.removeAll( Collections.singleton( null ) );
			
			referralListVO.setReferrals( referralList );
			referralListVO.setReferalType(responseType.toString()); //added for 139106 WC cancellation
			logger.debug("HHH -IN THE IF PART --setting the refferal type to : " + responseType.toString());

			referralListVO.setIsValidationReferral(true); //added for WC cancellation scenario 139106 
			
		}
		else{
			
			//For WC Rule Issue139106
			logger.debug("HHH -IN THE ELSE PART --setting the refferal type to : " + responseType.toString());
			referralListVO.setReferalType( responseType.toString() );

		}
		
		/*
		 * After rule validation, remove the referral data already saved in DB if the rule has passed for the same fact
		 */
		if( !Utils.isEmpty( ( (PolicyDataVO) policyBaseVO ).getCommonVO().getPolicyId() ) 
				&& !Utils.isEmpty(passedFactList) 
				&& !( (PolicyDataVO) policyBaseVO ).getCommonVO().getAppFlow().equals(Flow.RESOLVE_REFERAL) ){
			DataHolderVO<Object []> dataHolder = new DataHolderVO<Object []>();
			Object [] inputData = {( (PolicyDataVO) policyBaseVO ).getCommonVO(), passedFactList };
			dataHolder.setData(inputData);
			TaskExecutor.executeTasks("REMOVE_PAS_REFERRAL", dataHolder);
		}
		((PolicyDataVO)policyBaseVO).setReferralVOList(referralListVO);
		return policyBaseVO;
	}

	/**
	 * Method to handle which showing referral messages in case of dependent rules.
	 * @param allResponsecharacteristics
	 */
	private void removeInvalidReferrals( List<com.rsame.rulesengine.restfulclient.response.Characteristics> allResponsecharacteristics ){

		String[] dependentFacts = Utils.getMultiValueAppConfig( "DEPENDENT_FACTS" );

		if( !Utils.isEmpty( dependentFacts ) ){

			for( String fact : dependentFacts ){

				String dependentFact = Utils.getSingleValueAppConfig( "DEPENDENT_CHAR_" + fact );

				com.rsame.rulesengine.restfulclient.response.Characteristics characteristics = new com.rsame.rulesengine.restfulclient.response.Characteristics();
				characteristics.setName( fact );

				com.rsame.rulesengine.restfulclient.response.Characteristics dependentCharacteristics = new com.rsame.rulesengine.restfulclient.response.Characteristics();
				dependentCharacteristics.setName( dependentFact );

				/* Remove the characteristic if both characteristic and dependent characteristic is false 
				 * Remove both characteristic and dependent characteristic if either characteristic and dependent characteristic is false and characteristic is 
				 * present
				 * */
				if( allResponsecharacteristics.contains( characteristics ) || allResponsecharacteristics.contains( dependentCharacteristics ) ){

					if( validAndFailedChar( allResponsecharacteristics, characteristics ) && validAndFailedChar( allResponsecharacteristics, dependentCharacteristics ) ){
						allResponsecharacteristics.remove( characteristics );
					}
					else if( !validAndFailedChar( allResponsecharacteristics, characteristics ) || !validAndFailedChar( allResponsecharacteristics, dependentCharacteristics ) ){
						if( allResponsecharacteristics.indexOf( characteristics ) > VALID_INDEX ){
							allResponsecharacteristics.remove( characteristics );
							allResponsecharacteristics.remove( dependentCharacteristics );
						}
					}
					else if( validAndFailedChar( allResponsecharacteristics, characteristics ) && !validAndFailedChar( allResponsecharacteristics, dependentCharacteristics ) ){
						allResponsecharacteristics.remove( characteristics );
					}

				}
			}
		}

	}

	private boolean validAndFailedChar( List<com.rsame.rulesengine.restfulclient.response.Characteristics> allResponsecharacteristics,
			com.rsame.rulesengine.restfulclient.response.Characteristics characteristics ){
		return ( allResponsecharacteristics.indexOf( characteristics ) > VALID_INDEX && allResponsecharacteristics.get( allResponsecharacteristics.indexOf( characteristics ) ).getValue().equalsIgnoreCase( FALSE ));
	}

	/**
	 * This method deletes the already approved refferals from the response list. 
	 * Prerequisite is that com.rsame.rulesengine.restfulclient.response.RuleSetResponse and com.rsame.rulesengine.restfulclient.request.Characteristics must override equals on factname
	 * for each response we get the fact from the DB and remove from the response after comparing the value from the request
	 * @param responseSet
	 * @param allRequestCharacteristicsList
	 * @param polId
	 * @param endtId
	 */
	private void removeApprovedRules( List<com.rsame.rulesengine.restfulclient.response.RuleSetResponse> responseSet, List<Characteristics> allRequestCharacteristicsList,
			CommonVO commonVO ){

		if( Utils.isEmpty( commonVO.getPolicyId() ) || Utils.isEmpty( commonVO.getEndtId() ) || Utils.isEmpty( responseSet ) ) return;

		List<Object[]> quotePolEndtNos = null;
		Long quotePolicyId = commonVO.getPolicyId();
		Long quoteEndtId = commonVO.getEndtId();
		if( !commonVO.getIsQuote() ){
			quotePolEndtNos = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_QUOTE_POLICY_ENDT_ID, commonVO.getQuoteNo() );
			for( Object[] quotePolEndtNo : quotePolEndtNos ){
				quotePolicyId = ( (BigDecimal) quotePolEndtNo[ 0 ] ).longValue();
				quoteEndtId = ( (BigDecimal) quotePolEndtNo[ 1 ] ).longValue();
			}
		}

		List<Object[]> facts = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_PENDING_FACT_NAME_FILTER, commonVO.getPolicyId(), quotePolicyId, commonVO.getEndtId(),
				quoteEndtId, SvcConstants.POL_STATUS_ACCEPT.toString() );
		List<Integer> approvedRuleList = new ArrayList<Integer>();
		if( !Utils.isEmpty( facts ) && facts.size() > 0 ){
			List<com.rsame.rulesengine.restfulclient.response.Characteristics> approvedRules = new ArrayList<com.rsame.rulesengine.restfulclient.response.Characteristics>();
			for( Object[] fact : facts ){
				String factName = (String) fact[ 0 ];
				com.rsame.rulesengine.restfulclient.response.Characteristics approvedRule = new com.rsame.rulesengine.restfulclient.response.Characteristics();
				approvedRule.setName( factName );
				
				/* Identify the approved rule */
				for( com.rsame.rulesengine.restfulclient.response.RuleSetResponse response : responseSet ){
					
					List<com.rsame.rulesengine.restfulclient.response.Characteristics> responseCharacteristics = response.getCharacteristics();
					int approvedRuleOccurance = Collections.frequency( responseCharacteristics, approvedRule );

					
					for( int eachOccurance = 0; eachOccurance < approvedRuleOccurance; eachOccurance++ ){
						
						int loc = response.getCharacteristics().indexOf( approvedRule );
						
						if( loc > VALID_INDEX ){
							com.rsame.rulesengine.restfulclient.response.Characteristics resCharacteristic = response.getCharacteristics().get( loc );
							Characteristics finderRequestCharacteristics = new Characteristics();
							Characteristics requestCharacteristics = new Characteristics();
							finderRequestCharacteristics.setName( factName );

							if( allRequestCharacteristicsList.indexOf( finderRequestCharacteristics ) > VALID_INDEX ){
								requestCharacteristics = allRequestCharacteristicsList.get( allRequestCharacteristicsList.indexOf( finderRequestCharacteristics ) );
								if( requestCharacteristics.getValue().equalsIgnoreCase( (String) fact[ 1 ] ) ){
									if( resCharacteristic.getValue().equalsIgnoreCase( FALSE ) || resCharacteristic.getValue().equalsIgnoreCase( "Fail" )){
										approvedRuleList.add( loc );
									}
								}
								/* Mark the response characteristic as READ*/
								resCharacteristic.setName( resCharacteristic.getName() + com.Constant.CONST_READ );
							}
								/* Mark the request characteristic as READ*/
								requestCharacteristics.setName( requestCharacteristics.getName() + com.Constant.CONST_READ );
							}
						}

					}
				}
			}

			/* remove the marked request characteristic #READ */
			for( Characteristics requestCharacteristics : allRequestCharacteristicsList ){
				if( requestCharacteristics.getName().contains( com.Constant.CONST_READ ) ){
					requestCharacteristics.setName( requestCharacteristics.getName().replace( com.Constant.CONST_READ, "" ).trim() );
				}
			}

			/* remove the marked response characteristic #READ */
			for( com.rsame.rulesengine.restfulclient.response.RuleSetResponse response : responseSet ){
				List<com.rsame.rulesengine.restfulclient.response.Characteristics> resCharacteristicsList = response.getCharacteristics();
				for( com.rsame.rulesengine.restfulclient.response.Characteristics resCharacteristics : resCharacteristicsList ){
					if( resCharacteristics.getName().contains( com.Constant.CONST_READ ) ){
						resCharacteristics.setName( resCharacteristics.getName().replace( com.Constant.CONST_READ, "" ).trim() );
					}
					
				}
			}
			
			/*Remove the approved rules from list */
			for( com.rsame.rulesengine.restfulclient.response.RuleSetResponse response : responseSet ){
				
				List<com.rsame.rulesengine.restfulclient.response.Characteristics> resCharacteristicsList = response.getCharacteristics();
				int decreaseIndex = 0;
				Collections.sort( approvedRuleList );
				if(!Utils.isEmpty(resCharacteristicsList))
				{
					for(Integer approveRule:approvedRuleList)
					{//Added for Mirror Site.
						if(resCharacteristicsList.size() >  approveRule.intValue() + decreaseIndex){
							resCharacteristicsList.remove( approveRule.intValue() + decreaseIndex );
						}
						decreaseIndex--;
					}
				}
			}
	}

	private String isRuleFailed( String attrName, String entryVal ){

		if(SvcConstants.DEPLOYED_LOCATION=="30" && attrName.equals("travellerMaxAge")){
			attrName = "travellerMaxAge_OMAN";
		}
		
		return populateValueInReferralMessage( Utils.getSingleValueAppConfig(attrName), entryVal );
	}

	private List<Integer> getIndicesOfFailedAges( String ageString ){
		List<Integer> indices = null;
		int i = 0, j = 0;
		StringTokenizer st = null;
		String tokenElement = null;
		st = new StringTokenizer( ageString, ":" );
		while( st.hasMoreTokens() ){
			tokenElement = st.nextToken();

			if( "FALSE".equalsIgnoreCase( tokenElement ) ){
				if( Utils.isEmpty( indices ) ){
					indices = new ArrayList<Integer>();
				}
				indices.add( j );
				i++;
			}
			j++;

		}

		return indices;
	}

	private String getModifiedValues( String ages, List<Integer> indices ){
		StringBuilder modifiedValue = null;
		String finalValue = null;

		if( !Utils.isEmpty( ages ) && !Utils.isEmpty( indices ) ){
			modifiedValue = new StringBuilder();
			String[] agees = ages.split( ":" );
			for( int i = 0; i < indices.size(); i++ ){
				modifiedValue.append( agees[ indices.get( i ) ] );
				modifiedValue.append( "," );
			}
			finalValue = modifiedValue.toString();
			return finalValue.substring( 0, finalValue.lastIndexOf( "," ) );
		}
		return null;
	}

	/**
	 * 
	 * @param valueMap
	 * @param gradientMap
	 * @return
	 */
	private Map<String, String> createMapForReferrals( Map<String, String> valueMap, Map<String, String> gradientMap ){
		String gradientName = null;
		String gradientValue = null;
		String value = null;
		List<Integer> agesArray = null;
		/**
		 * Iterating over the maps and retaining only those attributes 
		 * in valueMap where corresponding value is false
		 */
		for( Map.Entry<String, String> gradientEntry : gradientMap.entrySet() ){
			gradientName = gradientEntry.getKey();
			if( gradientName.contains( "Arrey" ) ){
				gradientValue = gradientEntry.getValue();
				value = valueMap.get( gradientName.substring( 0, gradientName.indexOf( GRADIENT ) ) );
				valueMap.remove( gradientName.substring( 0, gradientName.indexOf( GRADIENT ) ) );
				agesArray = getIndicesOfFailedAges( gradientValue );
				value = getModifiedValues( value, agesArray );
				if( !Utils.isEmpty( value ) ){
					valueMap.put( gradientName.substring( 0, gradientName.indexOf( GRADIENT ) ), value );
				}
			}
			else if( !FALSE.equalsIgnoreCase( gradientEntry.getValue() ) ){
				valueMap.remove( gradientName.substring( 0, gradientName.indexOf( GRADIENT ) ) );
			}
		}
		return valueMap;
	}

	/**
	 * 
	 * @param valueMap
	 * @return
	 */
	private BaseVO createReferralMessages( Map<String, String> valueMap, PolicyVO policyVO ){

		ReferralVO referralVO = null;
		String staticReferralMessage = null;
		String referralMessage = null;
		List<String> referralText = null;
		String entryKey = null;
		String entryVal = null;
		Map<Integer, String> commodityMap = null;
		String brokerCrLimtStatus=DAOUtils.getBrActionCL(policyVO.getGeneralInfo().getSourceOfBus().getBrokerName());
		
		for( Map.Entry<String, String> valueEntry : valueMap.entrySet() ){
			entryKey = valueEntry.getKey();
			entryVal = valueEntry.getValue();

			if( RulesConstants.CITY.equalsIgnoreCase( entryKey ) || RulesConstants.NATIONALITY.equalsIgnoreCase( entryKey ) ){
				/** Fetching the City and Nationality description based on the code */
				entryVal = SvcUtils.getLookUpDescription( entryKey.toUpperCase(), RulesConstants.LOOKUP_LEVEL1, RulesConstants.LOOKUP_LEVEL2, Integer.valueOf( entryVal ) );
			}

			/** Fetching the referral messages for underwriting questions from lookup, configured with category = "UWQ_REF_MSG", level1 = "ALL", level2 = "SECTION_ID" and code = UWQuestionCode */
			if( entryKey.contains( RulesConstants.UWQUESTION_ATTR_NAME_PREFIX ) ){
				String lkpParams[] = entryKey.toString().split( "UWQ_SEC", 2 )[ 1 ].split( "_" );
				referralMessage = SvcUtils.getLookUpDescription( RulesConstants.UWQ_REF_MSG_LKP_CATEGORY, RulesConstants.LOOKUP_LEVEL1, lkpParams[ 0 ],
						Integer.valueOf( lkpParams[ 1 ] ) );
			}
			else{
				staticReferralMessage = Utils.getSingleValueAppConfig( entryKey );
				if( "commodityTypeArrey".equals( entryKey ) ){
					commodityMap = policyVO.getCommodityMap();
					if( !Utils.isEmpty( commodityMap ) ){
						String[] strTypes = entryVal.split( "," );
						StringBuffer strBuff = new StringBuffer();
						int cType;
						for( int i = 0; i < strTypes.length; i++ ){
							cType = Integer.parseInt( strTypes[ i ] );
							strBuff.append( commodityMap.get( cType ) + "," );
							entryVal = strBuff.toString();
							entryVal = entryVal.substring( 0, entryVal.lastIndexOf( "," ) );
						}
					}
				}
				referralMessage = populateValueInReferralMessage( staticReferralMessage, entryVal );
			}

			if( Utils.isEmpty( referralText ) ){
				referralText = new ArrayList<String>( 1 );
			}
			referralText.add( referralMessage );
		}

		if( !Utils.isEmpty( referralText ) ){
			referralVO = new ReferralVO();
			
			referralVO.setReferralText( referralText );
			
			
			if(valueMap.containsKey( BROKER_MIN_CREDIT_LIMIT ) && !valueMap.containsKey( BROKER_MAX_CREDIT_LIMIT ) ){
				referralVO.setMessage( true );
				
			}
			if(valueMap.containsKey( BROKER_MIN_CREDIT_LIMIT ) && valueMap.containsKey( BROKER_MAX_CREDIT_LIMIT ) ){
				referralText.remove( Utils.getSingleValueAppConfig( BROKER_MIN_CREDIT_LIMIT ) );
			}
			if(valueMap.containsKey( BROKER_MAX_CREDIT_LIMIT )){
				if(!Utils.isEmpty(brokerCrLimtStatus)&& brokerCrLimtStatus.equals("WARNING")){
					referralVO.setMessage( true );
					referralVO.setReferral(false);
				}else{
					referralVO.setMessage( false );
					referralVO.setReferral(false);
				}
				
			}
			
			
			
		}
		if( !Utils.isEmpty( referralVO ) ){
			referralVO.setReferalDataMap( valueMap );
		}
		return referralVO;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param val
	 * @return
	 */
	private String populateValueInReferralMessage( String staticReferralMessage, String val ){
		return staticReferralMessage.replace( "%VAR%", val );
	}

	/**
	 * 
	 * @param ruleFaultList
	 */
	private void checkForRulesEngineError( RuleExecutionResponse response ){
		List<RuleFault> ruleFaultList = null;
		Iterator<RuleFault> ruleFaultListItr = null;
		RuleFault ruleFault = null;
		String faultCategory = null;
		String faultCode = null;
		String faultMessage = null;
		String faultDescription = null;
		String[] errorMessages = null;
		int i = 0;

		String[] successCodesArr = Utils.getMultiValueAppConfig( "RULES_ERROR_CODES_CONSIDERED_SUCCESS" );
		Utils.trimAllEntries( successCodesArr );

		List<String> successCodes = CopyUtils.asList( successCodesArr );

		ruleFaultList = response.getFault();
		if( !Utils.isEmpty( ruleFaultList ) ){
			logger.error( "Error while processing the request with Rules Engine." );
			errorMessages = new String[ ruleFaultList.size() ];

			boolean atleastOneError = false;

			ruleFaultListItr = ruleFaultList.iterator();
			while( ruleFaultListItr.hasNext() ){
				ruleFault = ruleFaultListItr.next();
				faultCategory = ruleFault.getFaultCategory();
				faultCode = ruleFault.getFaultCode();
				faultMessage = ruleFault.getFaultMessage();
				faultDescription = ruleFault.getFaultDescription();

				String errorMessage = Utils.concat( "Error message from rules engine: ", faultCategory, " : ", faultCode, " : ", faultMessage, " : ", faultDescription );

				/* Check if this faule code can be considered a success. */
				if( successCodes.contains( faultCode ) ){
					if( logger.isInfo() ) logger.info( errorMessage );
					continue;
				}
				else{
					if( logger.isError() ) logger.error( errorMessage );
				}

				atleastOneError = true;

				if( !Utils.isEmpty( faultDescription, true ) )
					errorMessages[ i ] = faultMessage.concat( faultDescription );
				else
					errorMessages[ i ] = faultMessage;

				i++;
			}

			/* If there is atleast one error code that has to be considered as error, throw a SystemException. */
			if( atleastOneError ) throw new SystemException( Utils.getAppErrorMessage( "cmn.systemError" ), null, errorMessages );
		}

		return;
	}

	/**
	 * 
	 * @param referralVO
	 * @param policyVO
	 */
	private void setReferralVOInPolicyVO( ReferralVO referralVO, PolicyVO policyVO, String sectionName ){
		List<SectionVO> sectionVOList = null;
		Iterator<SectionVO> sectionListItr = null;
		SectionVO sectionVO = null;
		HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
		LocationVO location = null;
		RiskGroupDetails riskDetail = null;
		String policyRiskId = null;

		String referralRiskId = referralVO.getRiskGroupId();

		logger.debug( "Setting referral message(s) for " + referralRiskId + " in PolicyVO object." );
		logger.debug( "*****Printing referral messages for : " + referralRiskId );
		for( String referralText : referralVO.getReferralText() ){
			logger.debug( referralText );
		}

		sectionVOList = policyVO.getRiskDetails();
		if( null != sectionVOList ){
			sectionListItr = sectionVOList.iterator();

			while( sectionListItr.hasNext() ){
				sectionVO = sectionListItr.next();
				if( Integer.valueOf( Utils.getSingleValueAppConfig( sectionName ) ) == sectionVO.getSectionId().intValue() ){
					riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();

					if( !Utils.isEmpty( riskGroupDetails ) ){
						for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){

							location = (LocationVO) riskGroupDetailsEntry.getKey();
							riskDetail = riskGroupDetailsEntry.getValue();

							if( null != location ){
								policyRiskId = location.getRiskGroupId();
							}

							if( referralRiskId.equalsIgnoreCase( policyRiskId ) ){
								riskDetail.setReferralVO( referralVO );
							}
						}
					}
				}
			}
		}
	}
	
	
	
}
