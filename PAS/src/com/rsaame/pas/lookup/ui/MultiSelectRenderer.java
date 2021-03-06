/**
 * DropDownHTMLRenderer.java
 * Copyright (c) 2011 MindTree Ltd.
 */
package com.rsaame.pas.lookup.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
/**
 * @author m1014594
 *
 */
public class MultiSelectRenderer extends BaseHTMLRenderer {

	private static final String ATTR_TEMPLATE="%s='%s'";  
	private static final String OPTION_TEMPLATE="<option value='%s' >%s</option>";
	private static final String HTML_OPTION_TEMPLATE="<option value='%s' title='%s'>%s</option>";
	private static final String OPTION_TEMPLATE_SELECTED="<option value='%s' selected=\"selected\">%s</option>";
	private static final String OPTION_SELECTED_VALUE = "<option selected value ='%s'>%s</option>";
	private static final String DOJOTYPEMULTISELECT="dijit.form.MultiSelect";
	private static final String COUNTRY="COUNTRY";
	private static final String NATIONALITY="NATIONALITY";
	private static final String DIRECTORATE="DIRECTORATE";
	private static final String DEDUCTIBLES="DEDUCTIBLES";
	private static final String USER="USER";
	/**
	 * Method to get built html content for text box
	 * 
	 * @param AttributeList
	 * @return  
	 */
	public void buildHTMLContent(HashMap<String,Object> attributeList) throws IOException ,DataAccessException{

		/*Service call along with other condition checks and logging to be implemented*/
		if(!Utils.isEmpty(attributeList.get(AppConstants.OUT)))
		{
			/*Preparing HTML content for Drop down*/
			JspWriter out=(JspWriter)attributeList.get("Out");
			StringBuffer responseString ;
			HashMap<String,Object> responseAttributeList ;
/*			responseString.append("<select ");  
			responseString.append(String.format(ATTR_TEMPLATE,"name",attributeList.get(AppConstants.TAGNAME).toString()));  
			if(!Utils.isEmpty(attributeList.get(AppConstants.TAGID)))
			{
			responseString.append(String.format(ATTR_TEMPLATE,"id",attributeList.get(AppConstants.TAGID).toString()));
			}
			
			if(!Utils.isEmpty(attributeList.get(AppConstants.INPUTTYPE)) && !attributeList.get(AppConstants.INPUTTYPE).toString().equalsIgnoreCase( AppConstants.HTML_DROPDOWN ))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"dojoType",DOJOTYPESELECT));				
			}
			
			
			Checking if following attribute has to be included in content 
			if(!Utils.isEmpty(attributeList.get(AppConstants.STYLE)))
			{	
				responseString.append(String.format(ATTR_TEMPLATE,"style",attributeList.get
						(AppConstants.STYLE).toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.TAG_CLASS)))
			{	
				responseString.append(String.format(ATTR_TEMPLATE,"class",attributeList.get
						(AppConstants.TAG_CLASS).toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.DISABLEDFLAG)))
			{
				if(attributeList.get( AppConstants.DISABLEDFLAG ).toString().equalsIgnoreCase( "true" ))
				{
					responseString.append(" disabled='disabled' ");
				}
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.VALUE))){
                responseString.append(String.format(OPTION_SELECTED_VALUE,attributeList.get(AppConstants.VALUE),attributeList.get(AppConstants.VALUE)));      
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.MANDATORYFLAG)))
			{
				if(attributeList.get( AppConstants.MANDATORYFLAG ).toString().equalsIgnoreCase( "false" ))
				{
					responseString.append(String.format(ATTR_TEMPLATE,"required","false"));
				}
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.AUTOCOMPLETE)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"autocomplete",attributeList.get
						(AppConstants.AUTOCOMPLETE).toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.ON_CHANGE_EVENT)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"onchange",attributeList.get
				(AppConstants.ON_CHANGE_EVENT).toString()));
			}
			responseString.append('>');  
			Setting default select option for select list
			String optionTag;
			optionTag=String.format(OPTION_TEMPLATE_SELECTED,"","Select");
			if(!defaultValuePresent(attributeList))
				responseString.append(optionTag);
			
			
			if(!Utils.isEmpty(attributeList.get(AppConstants.IDENTIFIER)))
			{	
		 		Preparing for a database call to get required values
				LookUpVO lookUpVO=new LookUpVO(); 
				lookUpVO.setCategory("COUNTRY");
				lookUpVO.setCategory(attributeList.get(AppConstants.IDENTIFIER).toString());
				if(!Utils.isEmpty(attributeList.get("level1")))
				{
					lookUpVO.setLevel1(attributeList.get("level1").toString() );
				}
				else
				{
					lookUpVO.setLevel1( "ALL" );
				}
				
				if(!Utils.isEmpty(attributeList.get("level2")))
				{
					lookUpVO.setLevel2(attributeList.get("level2").toString() );
				}
				else
				{
					lookUpVO.setLevel2( "ALL" );
				}
				LookUpService lookUpService=getLookUpService();
				LookUpListVO lookUpL=(LookUpListVO)lookUpService.getListOfDescription(lookUpVO);
				List<String> optionsList=new ArrayList<String>();  
				List<String> codeList=new ArrayList<String>(); 
				if(!Utils.isEmpty( lookUpL ))
				{
					if(!Utils.isEmpty( lookUpL.getLookUpList() ))
					{
						if( null != lookUpL.getLookUpList().get(0)){
							
							if(null != lookUpL.getLookUpList().get(0).getCategory()){
								
								if("INDEMNITY_PERIOD".equals(lookUpL.getLookUpList().get(0).getCategory()))
								{
									Collections.sort(lookUpL.getLookUpList(), new BIDeductComparator());
								}
								else
								{
									Collections.sort(lookUpL.getLookUpList());
								}
								
							}
						}
						HttpSession session =  (HttpSession) attributeList.get( AppConstants.SESSIONDATA );
						lookUpL = DropDownRendererHepler.getLookFilteredList(lookUpL,session);
						
						
						
						for(LookUpVO lkv : lookUpL.getLookUpList()){
							if(!Utils.isEmpty(lkv))
							{
								if(!Utils.isEmpty(lkv.getDescription()))optionsList.add(lkv.getDescription()); 
								if(!Utils.isEmpty(lkv.getCode()))codeList.add(String.valueOf(lkv.getCode()));
							}
						}

						Generate dynamic content in optionList
						for( int iterator = 0; iterator < optionsList.size(); iterator++ ){
							Adding options to select list
							String codeValue = codeList.get( iterator ).toString();
							String optionValue = optionsList.get( iterator ).toString();
							String displayValue = optionValue;
							if( !Utils.isEmpty( attributeList.get( AppConstants.FORMAT ) ) ){
								displayValue = getFormattedString( optionValue, attributeList.get( AppConstants.FORMAT ).toString() );
							}
							if( !Utils.isEmpty( attributeList.get( AppConstants.CODE ) ) ){
								Setting default selection in select list if code attribute is not empty
								if( attributeList.get( AppConstants.CODE ).toString().equalsIgnoreCase( codeList.get( iterator ) ) ){
									optionTag = String.format( OPTION_TEMPLATE_SELECTED, codeValue, displayValue );
									responseString.append( optionTag );
								}
								else{
									optionTag = String.format( OPTION_TEMPLATE, codeValue, displayValue );
									responseString.append( optionTag );
								}
							}
							else{

								optionTag = String.format( OPTION_TEMPLATE, codeValue, displayValue );
								responseString.append( optionTag );
							}
						}
					}
					
					if(attributeList.get(AppConstants.IDENTIFIER).toString().equalsIgnoreCase("directorate")){
						optionTag=String.format(OPTION_TEMPLATE,"0","Others");
						responseString.append(optionTag);
					}
					responseString.append("</select>"); 
					out.print(responseString);
				}
			}
			*/
			
			responseAttributeList = getDropdownValues(attributeList);
			if(responseAttributeList.get( "status" ).toString().equals( AppConstants.TRUE )){
				responseString = (StringBuffer)responseAttributeList.get( "responseString" );
				out.print(responseString);
			}
		}
	}

	
	private boolean defaultValuePresent( HashMap<String, Object> attributeList ){
		if(attributeList.get( AppConstants.IDENTIFIER ) == "PAYMENT_MODE")
			return true;
		return false;
	}


	/**
	 * Method to get default html content for select list
	 * 
	 * @param out
	 * @return  
	 */
	public void buildEmptyControl(JspWriter out) throws IOException{
		
		out.print("<select ");  
		out.print(String.format(ATTR_TEMPLATE,"name","emptyList"));  
		out.print(String.format(ATTR_TEMPLATE,"dojoType",DOJOTYPEMULTISELECT));
		out.print('>');
		String optionTag=String.format(OPTION_TEMPLATE,"","Select");  
		out.println(optionTag);
					
	}
	
	private String getFormattedString(String optionValue, String format ){
		String displayValue = "";
		if( format.equalsIgnoreCase( AppConstants.CURRENCY ) ){
			double doubleVal = Double.valueOf( optionValue );
			displayValue = AppUtils.getFormattedNumberWithDecimals( doubleVal, 2 );
		}
		return displayValue;
	}
	public HashMap<String, Object> getDropdownValues( HashMap<String, Object> attributeList ){

		/*Preparing HTML content for Drop down*/
		//String tagName = attributeList.get(AppConstants.TAGNAME ).toString();
		HashMap<String, Object> responseAttributeList = new HashMap<String, Object>();
		StringBuffer responseString = new StringBuffer();
		responseString.append( "<select " );
		responseString.append( String.format( ATTR_TEMPLATE, "name", attributeList.get( AppConstants.TAGNAME ).toString() ) );
		if( !Utils.isEmpty( attributeList.get( AppConstants.TAGID ) ) ){
			responseString.append( String.format( ATTR_TEMPLATE, "id", attributeList.get( AppConstants.TAGID ).toString() ) );
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.INPUTTYPE ) ) && !attributeList.get( AppConstants.INPUTTYPE ).toString().equalsIgnoreCase( AppConstants.HTML_DROPDOWN ) ){
			responseString.append( String.format( ATTR_TEMPLATE, "dojoType", DOJOTYPEMULTISELECT ) );
		} 
		/*Checking if following attribute has to be included in content */
		if( !Utils.isEmpty( attributeList.get( AppConstants.STYLE ) ) ){
			responseString.append( String.format( ATTR_TEMPLATE, "style", attributeList.get( AppConstants.STYLE ).toString() ) );
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.TAG_CLASS ) ) ){
			responseString.append( String.format( ATTR_TEMPLATE, "class", attributeList.get( AppConstants.TAG_CLASS ).toString() ) );
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.DISABLEDFLAG ) ) ){
			if( attributeList.get( AppConstants.DISABLEDFLAG ).toString().equalsIgnoreCase( "true" ) ){
				responseString.append( " disabled='disabled' " );
			}
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.VALUE ) ) ){
			responseString.append( String.format( OPTION_SELECTED_VALUE, attributeList.get( AppConstants.VALUE ), attributeList.get( AppConstants.VALUE ) ) );
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.MANDATORYFLAG ) ) ){
			if( attributeList.get( AppConstants.MANDATORYFLAG ).toString().equalsIgnoreCase( "false" ) ){
				responseString.append( String.format( ATTR_TEMPLATE, "required", "false" ) );
			}
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.AUTOCOMPLETE ) ) ){
			responseString.append( String.format( ATTR_TEMPLATE, "autocomplete", attributeList.get( AppConstants.AUTOCOMPLETE ).toString() ) );
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.ON_CHANGE_EVENT ) ) ){
			responseString.append( String.format( ATTR_TEMPLATE, "onchange", attributeList.get( AppConstants.ON_CHANGE_EVENT ).toString() ) );
		}
		if( !Utils.isEmpty( attributeList.get( AppConstants.ON_LOAD_EVENT ) ) ){
			responseString.append( String.format( ATTR_TEMPLATE, "onload", attributeList.get( AppConstants.ON_LOAD_EVENT ).toString() ) );
		}
		responseString.append( '>' );
		/*Setting default select option for select list*/
		String optionTag = null;
		//optionTag = String.format( OPTION_TEMPLATE_SELECTED, "", "Select" );
		if( !defaultValuePresent( attributeList ) ) responseString.append( optionTag );

		responseAttributeList.put( "responseFlag", AppConstants.FALSE );
		if( !Utils.isEmpty( attributeList.get( AppConstants.IDENTIFIER ) ) ){
			/*Preparing for a database call to get required values*/
			LookUpVO lookUpVO = new LookUpVO();
			/*lookUpVO.setCategory("COUNTRY");*/
			lookUpVO.setCategory( attributeList.get( AppConstants.IDENTIFIER ).toString() );
			if( !Utils.isEmpty( attributeList.get( "level1" ) ) ){
				lookUpVO.setLevel1( attributeList.get( "level1" ).toString() );
			}
			else{
				lookUpVO.setLevel1( "ALL" );
			}
			if( !Utils.isEmpty( attributeList.get( "level2" ) ) ){
				lookUpVO.setLevel2( attributeList.get( "level2" ).toString() );
			}
			else{
				lookUpVO.setLevel2( "ALL" );
			}
			LookUpService lookUpService = getLookUpService();
			LookUpListVO lookUpL = (LookUpListVO) lookUpService.getListOfDescription( lookUpVO );
			List<String> optionsList = new ArrayList<String>();
			List<String> codeList = new ArrayList<String>();
			if( !Utils.isEmpty( lookUpL ) ){
				if( !Utils.isEmpty( lookUpL.getLookUpList() ) ){
					if( null != lookUpL.getLookUpList().get( 0 ) ){

						if( null != lookUpL.getLookUpList().get( 0 ).getCategory() ){

							if( "INDEMNITY_PERIOD".equals( lookUpL.getLookUpList().get( 0 ).getCategory() ) ){
								Collections.sort( lookUpL.getLookUpList(), new BIDeductComparator() );
							}
							else{
								Collections.sort( lookUpL.getLookUpList() );
							}

						}
					}
					HttpSession session = (HttpSession) attributeList.get( AppConstants.SESSIONDATA );
					lookUpL = DropDownRendererHepler.getLookFilteredList( lookUpL, session );

					for( LookUpVO lkv : lookUpL.getLookUpList() ){
						if( !Utils.isEmpty( lkv ) ){
							if( !Utils.isEmpty( lkv.getDescription() ) ) optionsList.add( lkv.getDescription() );
							if( !Utils.isEmpty( lkv.getCode() ) ) codeList.add( String.valueOf( lkv.getCode() ) );
						}
					}

					/*Generate dynamic content in optionList*/
					for( int iterator = 0; iterator < optionsList.size(); iterator++ ){
						/*Adding options to select list*/
						String codeValue = codeList.get( iterator ).toString();
						String optionValue = optionsList.get( iterator ).toString();
						String displayValue = optionValue;
						if( !Utils.isEmpty( attributeList.get( AppConstants.FORMAT ) ) ){
							displayValue = getFormattedString( optionValue, attributeList.get( AppConstants.FORMAT ).toString() );
						}
						if( !Utils.isEmpty( attributeList.get( AppConstants.CODE ) ) ){
							/*Setting default selection in select list if code attribute is not empty*/
							if( attributeList.get( AppConstants.CODE ).toString().equalsIgnoreCase( codeList.get( iterator ) ) ){
								optionTag = String.format( OPTION_TEMPLATE_SELECTED, codeValue, displayValue );
								responseString.append( optionTag );
							}
							else{
								optionTag = String.format( OPTION_TEMPLATE, codeValue, displayValue );
								responseString.append( optionTag );
							}
						}
						else{
							optionTag = String.format( OPTION_TEMPLATE, codeValue, displayValue );
							responseString.append( optionTag );
						}
					}
				}
				
				//Below 4 lines are Commented as part of removing the duplicate 'Other' option from the Location drop down.
				
				/*if( attributeList.get( AppConstants.IDENTIFIER ).toString().equalsIgnoreCase( "directorate" ) ){
					optionTag = String.format( OPTION_TEMPLATE, "0", "Others" );
					responseString.append( optionTag );
				}*/
				
				responseString.append( "</select>" );
				responseAttributeList.put( "status", AppConstants.TRUE );
				responseAttributeList.put( "responseString", responseString );
				//out.print(responseString);
			}
		}
		return responseAttributeList;
	}
	
}

