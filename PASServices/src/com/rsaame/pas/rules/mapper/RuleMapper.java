/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * @author M1012799
 *
 */
public interface RuleMapper{

	public RuleInfo createRiskDetails( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo );
	
}
