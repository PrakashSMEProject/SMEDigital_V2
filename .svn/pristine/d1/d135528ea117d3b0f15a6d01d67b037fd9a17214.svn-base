/**
 * 
 */
package com.rsaame.pas.access.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule;
import com.mindtree.ruc.mvc.tags.util.RuleResultScope;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.util.AppConstants;

/**
 * @author m1014644
 *Renders the ACL based on the status of the quote of policy or quote and the status of the logged in user
 *This visibility rules appends the status of quote/policy to the section attribute and check for the visibility 
 *Any filed or button or input type that is based on the status of the quote or policy, ACL is determined here 
 */
public class LoggedInUserBasedACL extends BaseVisibilityRule{

	HttpServletRequest request;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule#calculateVisibilityLevel(javax.servlet.http.HttpServletRequest, java.util.Map)
	 */
	@Override
	protected VisibilityLevel calculateVisibilityLevel( HttpServletRequest request, Map<String, String> input ){
		
		this.request = request;
		/*Gets the visibility level of the a field depending on the rule configured in DB
		 * the last parameter determines weather the status of the quote/policy is required
		 * to be considered for ACL determination  */
		GetPrivilegeForUserUtil getPrivilegeForUserUtil = (GetPrivilegeForUserUtil) Utils.getBean( "getPrivilege" );
		VisibilityLevel statusVisibility = getPrivilegeForUserUtil.getVisibility( request, input, AppConstants.USER_POL_QUO_STATUS);	
		VisibilityLevel nonStatusVisibility= (VisibilityLevel) request.getAttribute( AppConstants.CASCADEVISIBILITY );
		
		if(!nonStatusVisibility.equals( statusVisibility ))
		{
			if(statusVisibility.equals( VisibilityLevel.HIDDEN ))
			return statusVisibility;
		}
		return nonStatusVisibility;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule#getCaseIdentifier(java.util.Map)
	 */
	@Override
	protected String getCaseIdentifier( Map<String, String> input ){
		String caseIdentifier = "";
		for( Map.Entry<String, String> identifier : input.entrySet() ){
			caseIdentifier = Utils.concat( "U_" + identifier.getKey(), identifier.getValue() );
			break;
		}
		return caseIdentifier;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule#getRuleResultScope()
	 */
	@Override
	protected RuleResultScope getRuleResultScope(){
		RuleResultScope visibilityScope =(RuleResultScope) request.getAttribute(AppConstants.RULE_RESULT_SCOPE);
		
		return visibilityScope;
	}

}
