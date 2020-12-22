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
import com.rsaame.pas.util.AppConstants;

/**
 * @author m1014644
 *
 */
public class PrePackageVisibility extends BaseVisibilityRule{

	private static final Logger logger = Logger.getLogger( PrePackageVisibility.class );
	HttpServletRequest request;

	/**
	 * This visibility handler will make the fields disabled for prepackaged scheme
	 */
	@Override
	protected VisibilityLevel calculateVisibilityLevel( HttpServletRequest request, Map<String, String> input ){

		this.request = request;
		String sectionName = input.get( AppConstants.SECTION_NAME );
		String prevType = input.get( AppConstants.PREV_TYPE ); 
		VisibilityLevel visibilityLevel = VisibilityLevel.EDITABLE;
		/*
		 * if the section is pre packaged then check for pre packaged flag
		 * if packaged make the field read - only
		 * else - return the visibility of the previous visibility rule
		 */
		if( sectionName.equalsIgnoreCase( "PRE_PACKAGE" ) ){
			com.rsaame.pas.ui.cmn.PolicyContext pc = com.rsaame.pas.util.PolicyContextUtil.getPolicyContext( request );
			if( !com.mindtree.ruc.cmn.utils.Utils.isEmpty( pc ) && !com.mindtree.ruc.cmn.utils.Utils.isEmpty( pc.getPolicyDetails() )
					&& !com.mindtree.ruc.cmn.utils.Utils.isEmpty( pc.getPolicyDetails().getIsPrepackaged() ) ){
				if( pc.getPolicyDetails().getIsPrepackaged() ){
					if( !Utils.isEmpty(prevType) && prevType.equals(VisibilityLevel.HIDDEN.toString()) ){
						visibilityLevel = VisibilityLevel.HIDDEN;
					}
					else if (!Utils.isEmpty(prevType) && prevType.equals(VisibilityLevel.EDITABLE.toString())){
						visibilityLevel = VisibilityLevel.EDITABLE;
					}
					else{
						visibilityLevel = VisibilityLevel.READONLY;
					}
				}
			}
		}
		else
		{
			visibilityLevel = (VisibilityLevel) request.getAttribute( AppConstants.CASCADEVISIBILITY);
		}
		return visibilityLevel;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule#getCaseIdentifier(java.util.Map)
	 */
	@Override
	protected String getCaseIdentifier( Map<String, String> input ){
		String caseIdentifier = "";
		for( Map.Entry<String, String> identifier : input.entrySet() ){
			caseIdentifier = Utils.concat( "P_" + identifier.getKey(), identifier.getValue() );
			break;
		}
		return caseIdentifier;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule#getRuleResultScope()
	 */
	@Override
	protected RuleResultScope getRuleResultScope(){
		RuleResultScope visibilityScope = (RuleResultScope) request.getAttribute( AppConstants.RULE_RESULT_SCOPE );

		return visibilityScope;
	}

}
