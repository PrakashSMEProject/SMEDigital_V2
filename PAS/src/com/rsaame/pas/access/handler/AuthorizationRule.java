/**
 * 
 */
package com.rsaame.pas.access.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule;
import com.mindtree.ruc.mvc.tags.util.RuleResultScope;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.kaizen.framework.model.SecurityContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;


/**
 * @author m1014644
 *
 *Checks for the authorization model for the input elements.
 */
public class AuthorizationRule extends BaseVisibilityRule{

	
	private static final Logger logger = Logger.getLogger(AuthorizationRule.class);
	HttpServletRequest request;
	
	@Override
	protected VisibilityLevel calculateVisibilityLevel(
			HttpServletRequest httprequest, Map<String, String> input) {
		
		this.request = httprequest;
		/*Gets the visibility level of the a field depending on the rule configured in DB
		 * the last parameter determines weather the status of the quote/policy is required
		 * to be considered for ACL determination  */
		GetPrivilegeForUserUtil getPrivilegeForUserUtil = (GetPrivilegeForUserUtil) Utils.getBean( "getPrivilege" );
		VisibilityLevel level = getPrivilegeForUserUtil.getVisibility( httprequest, input, null );
		request.setAttribute( AppConstants.CASCADEVISIBILITY, level );
		return level;
	}

	@Override
	protected String getCaseIdentifier(Map<String, String> input) {
		
		String caseIdentifier = "";
		for(Map.Entry<String , String> identifier : input.entrySet())
		{
			caseIdentifier = Utils.concat("A_"+identifier.getKey(),identifier.getValue());
			break;
		}
		return caseIdentifier;
	}

	@Override
	protected RuleResultScope getRuleResultScope() {
		
		RuleResultScope visibilityScope =(RuleResultScope) request.getAttribute(AppConstants.RULE_RESULT_SCOPE);
		
		return visibilityScope;
	}
	
	 
}
