package com.rsaame.pas.access.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.mindtree.ruc.mvc.tags.util.VisibilityRuleExecutor;
import com.rsaame.pas.util.AppConstants;

/**
 * 
 * @author m1014644
 *
 *Authorization tag handler.
 *Executes all configured rules for the decision of visibility of the item - input field, section, screen or any other. 
 *This executor is expected to be used by a JSP tag which can be custom defined with multiple attributes which cannot be predicted here. 
 *Hence, the input is being taken here as a Map of String key and String value.
 */
public class ACLTagHandler extends TagSupport  {
	
	private static final Logger logger = Logger.getLogger(ACLTagHandler.class);
	
	private String sectionName;
	private String privType;
	private VisibilityRuleExecutor vre = new VisibilityRuleExecutor();
	private VisibilityLevel visibilityLevel ;
	private HttpServletRequest request;
	private Map<String,String> accessAttribute;
	
	
	@Override
	public int doEndTag() throws JspException {
		
		vre.clearROList(request, accessAttribute);
		
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
	
		if(logger.isTrace())
		logger.trace("Entering the auth tag");
		
		int returnType = EVAL_PAGE;
		
		accessAttribute = new HashMap<String,String>();
		request = (HttpServletRequest) pageContext.getRequest();
		
		accessAttribute.put(AppConstants.SECTION_NAME, sectionName);
		if( !Utils.isEmpty(privType) ){
			accessAttribute.put( AppConstants.PREV_TYPE, privType);
		}
		
		visibilityLevel = vre.executeRules(request, accessAttribute);
		
	
		if(visibilityLevel.equals(VisibilityLevel.HIDDEN))
		{
			returnType = SKIP_BODY;
		}
		else if(visibilityLevel.equals(VisibilityLevel.READONLY))
		{
			returnType = EVAL_PAGE;
		}
		else if(visibilityLevel.equals(VisibilityLevel.EDITABLE))
		{
			returnType = EVAL_PAGE;
		}
		
		if(logger.isTrace())
		{
			logger.trace("Section Name:"+ sectionName);
			logger.trace("Visibilitiy level:"+ visibilityLevel);
			
		}
			
		
		if(logger.isTrace())
		logger.trace("Exiting the auth tag");
		return returnType;
		
	}

	
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}


	public String getPrivType() {
		return privType;
	}
	public void setPrivType(String sectionName) {
		this.privType = sectionName;
	}
	

	
}
