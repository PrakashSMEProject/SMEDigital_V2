/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import java.util.StringTokenizer;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.endorse.svc.GeneralInfoCommonSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsame.rulesengine.restfulclient.request.ExecuteRuleRequest;

/**
 * This class will map the restful rule requests
 * 
 * @author M1020859
 *
 */
public class RESTfulRuleServiceRequestMapper{

	/**
	 * Method to create the RestFul Rule Request
	 * 
	 * @param baseVO
	 * @param sectionArray
	 * @param roleType
	 * @return
	 */
	public ExecuteRuleRequest createRestFulRuleRequest( BaseVO policyBaseVO, Integer[] sectionArray, String roleType ){

		ExecuteRuleRequest request = new com.rsame.rulesengine.restfulclient.request.ExecuteRuleRequest();
		request = createGeneralRequest( policyBaseVO, request );
		com.rsame.rulesengine.restfulclient.request.RuleInfo ruleInformation = new com.rsame.rulesengine.restfulclient.request.RuleInfo();
		for( int section : sectionArray ){
			RuleMapper ruleMapper = (RuleMapper) Utils.getBean( "RULE_MAPPER_" + section );
			ruleInformation = ruleMapper.createRiskDetails( policyBaseVO, section, roleType, ruleInformation );
		}
		request.setRuleInfo( ruleInformation );
		return request;
	}

	/**
	 * Method to create restful general request 
	 * 
	 * @param policyBaseVO
	 * @param request
	 * @return
	 */
	private com.rsame.rulesengine.restfulclient.request.ExecuteRuleRequest createGeneralRequest( BaseVO policyBaseVO,
			com.rsame.rulesengine.restfulclient.request.ExecuteRuleRequest request ){

		String locationStr = null;
		String policyTypeStr = null;
		String tariffStr = null;
		com.rsame.rulesengine.restfulclient.request.RuleKey ruleKey = new com.rsame.rulesengine.restfulclient.request.RuleKey();
		Integer policyTypeCode = null;
		Integer tariffCode = null;
		Integer classCode = null;
		String locationCode = Utils.getSingleValueAppConfig("RULE_DEPLOYED_LOC");
		if( policyBaseVO instanceof PolicyDataVO ){

			PolicyDataVO policyData = (PolicyDataVO) policyBaseVO;

			if( !Utils.isEmpty( policyData.getCommonVO().getQuoteNo() ) ){
				GeneralInfoCommonSvc genLoadSvc = null;
				if( policyData.getCommonVO().getIsQuote() ){
					genLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( "commonGenSvc" );
				}
				else{
					genLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( "commonGenSvc_POL" );
				}
				PolicyDataVO pol = null;
				if( Utils.isEmpty( policyData.getScheme() )
						|| ( !Utils.isEmpty( policyData.getScheme() ) && ( Utils.isEmpty( policyData.getScheme().getTariffCode() ) || Utils.isEmpty( policyData.getScheme()
								.getPolicyCode() ) ) ) || Utils.isEmpty( policyData.getPolicyClassCode() ) ){
					pol = (PolicyDataVO) genLoadSvc.invokeMethod( SvcConstants.LOAD_GEN_INFO, policyBaseVO );
				}
				else{
					pol = policyData;
				}
				

				if( LOB.TRAVEL.equals( policyData.getCommonVO().getLob() ) ){
					policyTypeCode = Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE );
					tariffCode = Integer.valueOf( RulesConstants.TRAVEL_TARIFF_CODE );
				}
				else if( LOB.HOME.equals( policyData.getCommonVO().getLob() ) ){
					tariffCode = pol.getScheme().getTariffCode();
					policyTypeCode = pol.getScheme().getPolicyCode();
				}
				else
				{
					tariffCode = pol.getScheme().getTariffCode();
					policyTypeCode = pol.getScheme().getPolicyCode();
				}
				tariffCode = mapTariff(tariffCode);
				classCode = pol.getPolicyClassCode();
			}

			else{
				if( LOB.TRAVEL.equals( policyData.getCommonVO().getLob() ) ){
					policyTypeCode = Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE );
					tariffCode = Integer.valueOf( RulesConstants.TRAVEL_TARIFF_CODE );
				}
				else /*if( LOB.HOME.equals( policyData.getCommonVO().getLob() ) )*/{
					policyTypeCode = policyData.getScheme().getPolicyCode();
					tariffCode = policyData.getScheme().getTariffCode();
				}
				tariffCode = mapTariff(tariffCode);
				classCode = policyData.getPolicyClassCode();
			}
			locationStr = RulesConstants.LOCATION.concat( locationCode );
			policyTypeStr = RulesConstants.POLICY_TYPE.concat(Integer.valueOf( policyTypeCode ).toString() );
			// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
			//tariffStr = RulesConstants.TARIFF.concat( new Integer( tariffCode ).toString() );
			tariffStr = RulesConstants.TARIFF.concat(Integer.valueOf( tariffCode ).toString() );
			
			ruleKey.setClazz( RulesConstants.CLASS.concat( "0" ).concat( String.valueOf( classCode ) ) );

		}
		ruleKey.setCountry( RulesConstants.COUNTRY );
		ruleKey.setLocation( locationStr );
		ruleKey.setPolicyType( policyTypeStr );
		ruleKey.setTariff( tariffStr );
		request.setRuleKey( ruleKey );
		return request;
	}

	private int mapTariff(int tariff){
		String RULE_TARIFF_MAP =  Utils.getSingleValueAppConfig("RULE_TARIFF_MAP") ;
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
}
