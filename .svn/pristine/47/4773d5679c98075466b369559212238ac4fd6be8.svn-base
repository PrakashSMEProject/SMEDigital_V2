/**
 * 
 */
package com.rsaame.pas.rules.invoker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.rules.mapper.RESTfulRuleServiceRequestMapper;
import com.rsaame.pas.rules.mapper.RuleServiceRequestMapper;
import com.rsaame.pas.rules.mapper.RuleServiceResponseMapper;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.rulesengine.client.Fact;
import com.rsaame.rulesengine.client.RuleAttribute;
import com.rsaame.rulesengine.client.RuleExecutionRequest;
import com.rsaame.rulesengine.client.RuleExecutionResponse;
import com.rsaame.rulesengine.client.RuleInfo;
import com.rsaame.rulesengine.client.RuleKey;
import com.rsaame.rulesengine.client.RuleServiceImpl;
import com.rsaame.rulesengine.client.RuleServiceImplService;
import com.rsaame.rulesengine.client.RuleSet;
import com.rsame.rulesengine.restfulclient.request.ExecuteRuleRequest;
import com.rsame.rulesengine.restfulclient.response.ExecuteRuleResponse;

/**
 * @author M1014739
 *
 */
public class RuleServiceInvoker{

	private final static Logger logger = Logger.getLogger( RuleServiceInvoker.class );

	public BaseVO callRuleService( BaseVO policyBaseVO, Integer[] sectionArray, String roleType ){
		RuleServiceRequestMapper requestMapper = null;
		RuleServiceResponseMapper responseMapper = null;
		RuleExecutionResponse response = null;
		BaseVO output = null;
		try{
			logger.info( "Calling RuleServiceRequestMapper for creating the rules engine request*****" );
			requestMapper = new RuleServiceRequestMapper();
			RuleExecutionRequest request = requestMapper.createRuleRequest( policyBaseVO, sectionArray, roleType );

			// Printing request object to Rules engine
			if( logger.isDebug() ) printRequestObject( request );

			logger.info( "*****Invoking Rules Engine*****" );
			URL baseUrl = com.rsaame.rulesengine.client.RuleServiceImplService.class.getResource( "." );
			String rulesUrl = getURL( RulesConstants.RULES_SERVICE_URL );
			System.out.println("--------------------rulesUrl--------------------------"+rulesUrl);
			URL url = new URL( baseUrl, rulesUrl );
			RuleServiceImplService service = new RuleServiceImplService( url, new QName( "http://service.rulesengine.rsaame.com/", "RuleServiceImplService" ) );
			RuleServiceImpl proxy = service.getRuleServiceImplPort();

			response = proxy.executeRule( request );
			logger.info( response.toString() );

			logger.info( "Calling RuleServiceResponseMapper for mapping the rules engine response and generating the referral messages*****" );
			responseMapper = new RuleServiceResponseMapper();
			output = responseMapper.mapRuleServiceResponse( policyBaseVO, response );

			// Printing referral messages triggered by Rules engine from ReferralVO object 
			if( logger.isDebug() ) printReferralVOResponseObject( output );

		}
		catch( Exception e ){
			e.printStackTrace();
			throw new SystemException( Utils.getAppErrorMessage( "cmn.systemError" ), e, "Exception while calling rules engine." );
		}

		return output;
	}
	
	
	/**
	 * Creating the restful rule service
	 * 
	 * @param policyBaseVO
	 * @param sectionArray
	 * @param roleType
	 * @return
	 */
	public BaseVO callRestFulRuleService( BaseVO policyBaseVO, Integer[] sectionArray, String roleType ){
		
		RuleServiceResponseMapper responseMapper =new RuleServiceResponseMapper();
		RestTemplate restTemplate = new RestTemplate();
		RESTfulRuleServiceRequestMapper restfuleRuleReqMapper = new RESTfulRuleServiceRequestMapper();
		try{
			logger.info( "Calling RuleServiceRequestMapper for creating the rules engine request*****" );
			ExecuteRuleRequest request = restfuleRuleReqMapper.createRestFulRuleRequest( policyBaseVO, sectionArray, roleType );
			printXml( ExecuteRuleRequest.class, request );
			HttpEntity<ExecuteRuleRequest> entity = new HttpEntity<ExecuteRuleRequest>( request );
			logger.debug( "---------- invoking service ----------" );
			long startTime = System.currentTimeMillis();
			logger.debug( "Calling Rule Start time:"  + new Date(startTime)   );
			ResponseEntity<com.rsame.rulesengine.restfulclient.response.ExecuteRuleResponse> response = restTemplate.postForEntity( Utils.getSingleValueAppConfig("RESTFUL_RULE_SERVICE_URL"), entity, com.rsame.rulesengine.restfulclient.response.ExecuteRuleResponse.class );
			long endTime = System.currentTimeMillis();
			logger.debug( "Calling Rule End time:"  + new Date(endTime)   );
			logger.debug( "Time taken for rule"  + ( endTime - startTime )/1000  );
			ExecuteRuleResponse ruleEngineResponse = response.getBody();
			logger.debug( " --- Response received ---\n Details are : " + ruleEngineResponse );
			printXml( ExecuteRuleResponse.class, ruleEngineResponse );
			if(policyBaseVO instanceof HomeInsuranceVO || policyBaseVO instanceof TravelInsuranceVO || 
					( policyBaseVO instanceof PolicyDataVO && (((PolicyDataVO)policyBaseVO).getCommonVO().getLob() == LOB.HOME || 
							((PolicyDataVO)policyBaseVO).getCommonVO().getLob() == LOB.TRAVEL))){
				if(!Utils.isEmpty(ruleEngineResponse) &&  !Utils.isEmpty(ruleEngineResponse.getFault()) && (ruleEngineResponse.getFault().size() > 0)){
					throw new BusinessException( "", null, ruleEngineResponse.getFault().get(0).toString() );
				}
				policyBaseVO = responseMapper.mapRestFulRuleResponse(policyBaseVO,sectionArray,roleType,ruleEngineResponse,request);
				//policyBaseVO = responseMapper.mapRestFulRuleResponse(policyBaseVO,sectionArray,ruleEngineResponse,request);
			}
			else{
				policyBaseVO = responseMapper.mapRestFulRuleResponse(policyBaseVO,sectionArray,ruleEngineResponse,request);
			}
			

		}
		catch( BusinessException e ){
			throw e;
		}
		return policyBaseVO;
	}
	
	public static void printXml( Class<?> clazz, Object obj ){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance( clazz );
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			jaxbMarshaller.marshal( obj, System.out );
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 */
	private void printRequestObject( RuleExecutionRequest request ){
		RuleKey key = request.getRuleHeader().getRuleKey();
		RuleInfo info = request.getRuleInfo();

		logger.debug( "*****Printing key*****" );
		logger.debug( key.getCountry() + key.getLocation() + key.getClazz() + key.getPolicyType() + key.getTariff() + key.getSectionList().getSection().get( 0 ) );

		logger.debug( "*****Printing Info*****" );
		for( RuleSet set : info.getRuleSet() ){
			logger.debug( "Risk Id: " + set.getRiskId() );
			logger.debug( "RuleSet Name: " + set.getRuleSetName() );

			for( Fact fact : set.getFact() ){
				logger.debug( "Fact Name: " + fact.getFactName() );
				for( RuleAttribute att : fact.getRuleSetAttribute() ){
					logger.debug( "Attribute Details: " + att.getAttributeName() + " : " + att.getAttributeValue() );
				}
			}
		}
	}

	/**
	 * 
	 * @param baseVO
	 */
	private void printReferralVOResponseObject( BaseVO baseVO ){
		logger.debug( "*****Back in RuleServiceInvoker: printReferralVOResponseObject. Printing Referral Messages*****" );
		ArrayList<ReferralVO> referrals = null;

		ReferralListVO referralListVO = (ReferralListVO) baseVO;

		if( !Utils.isEmpty( referralListVO ) && !Utils.isEmpty( referralListVO.getReferrals() ) ){
			referrals = (ArrayList<ReferralVO>) referralListVO.getReferrals();
			for( ReferralVO referralVO : referrals ){
				logger.debug( "RiskID: " + referralVO.getRiskGroupId() );
				for( String referralText : referralVO.getReferralText() ){
					logger.debug( referralText );
				}
			}
		}
	}
	
	private String getURL(String key) {
		String result = new String();
		String thisLine = "";
		String thisLine1 = "";
		String value[] = new String[5];
		//String fname = "mkdbuser.rsa";// Should be in ur domain
		
		try(BufferedReader dbReader = new BufferedReader(new FileReader(Utils.getSingleValueAppConfig( "PASEnvFileLocation" )));) {
		
			while ((thisLine = dbReader.readLine()) != null) {
				thisLine1 = thisLine;
				if ((thisLine1.trim().indexOf("~``~") == -1)
						&& (thisLine1.trim().substring(0, thisLine1.indexOf("~`~")).contains(key))) {
					StringTokenizer str = new StringTokenizer(thisLine1, "~`~");
					int i = 0;
					while (str.hasMoreTokens()) {
						value[i] = str.nextToken();
						if (i == 1)
						{
							result = value[i];
						}
							i++;
					}
					dbReader.close();
					return result;
				}
			}
			dbReader.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
