package com.rsaame.pas.access.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule;
import com.mindtree.ruc.mvc.tags.util.RuleResultScope;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.util.AppConstants;

/**
 * 
 * @author m1014644
 *Checks for the authorization model for the whole page
 */

public class DisplayModeRule extends BaseVisibilityRule  {

	
	private static final Logger logger = Logger.getLogger(DisplayModeRule.class);
	private HttpServletRequest request;
	
	@Override
	protected VisibilityLevel calculateVisibilityLevel(
			HttpServletRequest httprequest, Map<String, String> input) {
		if(logger.isTrace()) logger.trace("Entering calculateVisibilityLevel for page display mode");
		
		this.request = httprequest;
		VisibilityLevel mode = (VisibilityLevel) request.getAttribute(AppConstants.MODE);
		VisibilityLevel visibilityLevel = VisibilityLevel.EDITABLE;
		
		if(!Utils.isEmpty(mode)&& mode.equals(VisibilityLevel.READONLY))
		{
			request.setAttribute(AppConstants.RULE_RESULT_SCOPE, RuleResultScope.SCREEN);
			visibilityLevel = VisibilityLevel.READONLY;
		}
		else
		{
			request.setAttribute(AppConstants.RULE_RESULT_SCOPE, RuleResultScope.TAG_INSTANCE);
		}
		
		if(logger.isTrace()) logger.trace("exiting calculateVisibilityLevel for page display mode");
		return visibilityLevel;
	}

	@Override
	protected String getCaseIdentifier(Map<String, String> input) {
		
		String caseIdentifier = "";
		for(Map.Entry<String , String> identifier : input.entrySet())
		{
			caseIdentifier = Utils.concat("D_"+identifier.getKey(),identifier.getValue());
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
