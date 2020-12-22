/**
 * LookUpTagHandler.java
 * Copyright (c) 2011 MindTree Ltd.
 */
package com.rsaame.pas.b2c.lookup.ui;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;

/**
 * @author m1014594
 * 
 */
public class LookUpTagHandler extends SimpleTagSupport {
	private static Logger logger = Logger.getLogger( LookUpTagHandler.class );
	
	private String inputType;
	private String tagName;
	private String tagId;
	private String identifier;
	private String code;
	private String disabledFlag;
	private String defaultValue;
	private String style;
	private String mandatoryFlag;
	private String missingMessage;
	private String regExp;
	private String invalidMessage;
	private String autocomplete;
	private String onChange;
	private String level1;
	private String level2;
	private String format;
	private String tagClass;
	private String value;
	private String path; //Added for B2C
	private String defaultOption; //Added for B2C
	
	/*
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 * 
	 */
	public void doTag() throws IOException
	{

		PageContext pageContext=(PageContext)getJspContext(); 
		
		JspWriter out=pageContext.getOut();
		IHtmlRenderer htmlRenderer=null;
		
		HashMap<String,Object> attributeList=new HashMap<String,Object>(); 

		/*Pending - logging to be included*/
		try
		{
			if(!Utils.isEmpty(inputType))
			{	
				/*If input type is not empty set following attributes*/
				attributeList.put(AppConstants.INPUTTYPE,inputType);
				attributeList.put(AppConstants.TAGNAME,tagName);
				attributeList.put(AppConstants.TAGID,tagId);
				attributeList.put(AppConstants.IDENTIFIER,identifier);
				attributeList.put(AppConstants.DISABLEDFLAG,disabledFlag);
				attributeList.put(AppConstants.DEFAULTVALUE,defaultValue);
				attributeList.put(AppConstants.STYLE,style);
				attributeList.put(AppConstants.MANDATORYFLAG,mandatoryFlag);
				attributeList.put(AppConstants.MISSINGMESSAGE,missingMessage);
				attributeList.put(AppConstants.REGEXP,regExp);
				attributeList.put(AppConstants.INVALIDMESSAGE,invalidMessage);
				attributeList.put(AppConstants.AUTOCOMPLETE,autocomplete);
				attributeList.put(AppConstants.OUT,out);
				attributeList.put(AppConstants.CODE,code);
				attributeList.put(AppConstants.FORMAT,format);
				attributeList.put(AppConstants.ON_CHANGE_EVENT, onChange);
				attributeList.put("level1",level1);
				attributeList.put("level2", level2);
				attributeList.put("request", pageContext.getRequest());
				attributeList.put(AppConstants.VALUE,value);
				attributeList.put(AppConstants.TAG_CLASS,tagClass);
				attributeList.put(AppConstants.SESSIONDATA,pageContext.getSession());
				attributeList.put(Utils.getSingleValueAppConfig("PATH"),path);
				attributeList.put("defaultOption", defaultOption);
				
				/*Directing flow to prepare HTML content based on input type */
				if(inputType.equalsIgnoreCase(AppConstants.FILTER_DROPDOWN) || inputType.equalsIgnoreCase(AppConstants.HTML_DROPDOWN))
				{	
					/*If input type is drop down prepare Html content for drop down */
					htmlRenderer=new DropDownHTMLRenderer();
					htmlRenderer.buildHTMLContent(attributeList);
				}
				else if(inputType.equalsIgnoreCase(AppConstants.TEXTBOX))
				{
					/*If input type is Textbox prepare Html content for Textbox */
					htmlRenderer=new TextBoxHtmlRenderer();
					htmlRenderer.buildHTMLContent(attributeList);
					
				}else if(inputType.equalsIgnoreCase(AppConstants.CHECKBOX))
				{
					/*If input type is Textbox prepare Html content for Textbox */
					htmlRenderer=new RiskSelectionHTMLRenderer( pageContext.getServletContext().getContextPath() );
					htmlRenderer.buildHTMLContent(attributeList);
					if( logger.isDebug() ) logger.debug(" Inside LookUpTagHandler");
				}
			}
		}
		catch(Exception exception)
		{
			logger.error( exception, "Couldn't get the look up list for category (identifier) [", identifier, "]" );
			htmlRenderer.buildEmptyControl(out);
		}
		
	}
	
	/**
	 * @param mandatoryFlag the mandatoryFlag to set
	 */
	public void setMandatoryFlag(String mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}

	/**
	 * @param missingMessage the missingMessage to set
	 */
	public void setMissingMessage(String missingMessage) {
		this.missingMessage = missingMessage;
	}

	/**
	 * @param regExp the regExp to set
	 */
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	/**
	 * @param invalidMessage the invalidMessage to set
	 */
	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}

	/**
	 * @param autocomplete the autocomplete to set
	 */
	public void setAutocomplete(String autocomplete) {
		this.autocomplete = autocomplete;
	}

	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	/**
	 * @param tagId the tagId to set
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @param disabledFlag the disabledFlag to set
	 */
	public void setDisabledFlag(String disabledFlag) {
		this.disabledFlag = disabledFlag;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @param OnClick the OnChange event to set
	 */

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}
	
	public static void setLogger(Logger logger) {
		LookUpTagHandler.logger = logger;
	}

	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}

	/**
	 * 
	 * @param format to set format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	public void setValue(String value){
        this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDefaultOption() {
		return defaultOption;
	}

	public void setDefaultOption(String defaultOption) {
		this.defaultOption = defaultOption;
	}

	
}
