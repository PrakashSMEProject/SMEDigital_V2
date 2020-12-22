/**
 * TextBoxHtmlRenderer.java
 * Copyright (c) 2011 MindTree Ltd.
 */
package com.rsaame.pas.lookup.ui;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.jsp.JspWriter;
import org.springframework.dao.DataAccessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.LookUpVO;
/**
 * @author m1014594
 *
 */
public class TextBoxHtmlRenderer extends BaseHTMLRenderer {

	private static final String ATTR_TEMPLATE="%s='%s'";  
	private static final String DOJOTYPETEXT="dijit.form.ValidationTextBox";
	//private static final Logger logger=Logger.getLogger(TextBoxHtmlRenderer.class);
	/**
	 * Method to build html content for select list
	 * 
	 * @param AttributeList
	 * @return  
	 */
	public void buildHTMLContent(HashMap<String,Object> attributeList) throws IOException,DataAccessException {
		
		//if(logger.isInfo())logger.info("Entering buildHTMLContent method of TextBoxHtmlRenderer ");
		/*Service call along with other condition checks and logging  to be implemented*/
		if(!Utils.isEmpty(AppConstants.OUT))
		{
			/*Preparing HTML content for Text box*/	
			JspWriter out=(JspWriter)attributeList.get(AppConstants.OUT);
			StringBuffer responseString=new StringBuffer();
			responseString.append("<input ");  
			responseString.append(String.format(ATTR_TEMPLATE,"name",attributeList.get(AppConstants.TAGNAME).toString())); 
			if(!Utils.isEmpty(attributeList.get(AppConstants.TAGID)))
			{
			responseString.append(String.format(ATTR_TEMPLATE,"id",attributeList.get(AppConstants.TAGID).toString()));
			}
			responseString.append(String.format(ATTR_TEMPLATE,"dojoType",DOJOTYPETEXT));
			/*if(logger.isInfo())logger.info("Textbox building with tagName"+
					attributeList.get(AppConstants.TAGNAME).toString());*/
			/*Checking if following attribute has to be included in content */
			if(!Utils.isEmpty(attributeList.get(AppConstants.STYLE)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"style",attributeList.get(AppConstants.STYLE).
						toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.TAG_CLASS)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"class",attributeList.get(AppConstants.TAG_CLASS).
						toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.DEFAULTVALUE)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"value",attributeList.get(AppConstants.DEFAULTVALUE).
						toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.DISABLEDFLAG)))
			{
				if(attributeList.get( AppConstants.DISABLEDFLAG ).toString().equalsIgnoreCase( "true" ))
				{
					responseString.append(" disabled ");
				}
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.MANDATORYFLAG)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"required","true"));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.MISSINGMESSAGE)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"missingMessage",attributeList.get
						(AppConstants.MISSINGMESSAGE).toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.REGEXP)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"regExp",attributeList.get
						(AppConstants.REGEXP).toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.INVALIDMESSAGE)))
			{
				responseString.append(String.format(ATTR_TEMPLATE,"invalidMessage",attributeList.get
						(AppConstants.INVALIDMESSAGE).toString()));
			}
			if(!Utils.isEmpty(attributeList.get(AppConstants.CODE)))
			{	
				//if(logger.isInfo())logger.info("Textbox's attribute code value:"+AppConstants.CODE);
				/*Preparing for a database call to get the required description for code*/
				LookUpVO lookUpVO=new LookUpVO(); 
				lookUpVO.setCategory(attributeList.get(AppConstants.IDENTIFIER).toString());
				lookUpVO.setCode(new BigDecimal( Integer.parseInt(attributeList.get(AppConstants.CODE).toString())));
				
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
				
				LookUpService lookUpService= getLookUpService();
				LookUpVO lookUpL=(LookUpVO)lookUpService.getDescription(lookUpVO);
				if(!Utils.isEmpty( lookUpL ))
				{
					if(!Utils.isEmpty( lookUpL.getDescription() ))
					{
						responseString.append(String.format(ATTR_TEMPLATE,"value",lookUpL.getDescription()));
					}
					
				}
			}
			responseString.append("/>"); 
			out.print(responseString);
		}
		//if(logger.isInfo())logger.info("Exiting buildHTMLContent method of TextBoxHtmlRenderer ");
	}

	/**
	 * Method to get default html content for select list
	 * 
	 * @param out
	 * @return  
	 */
	public void buildEmptyControl(JspWriter out) throws IOException{
		//if(logger.isInfo())logger.info("Entering buildEmptyControl method of TextBoxHtmlRenderer ");
		out.print("<input ");  
		out.print(String.format(ATTR_TEMPLATE,"name","emptyText"));  
		out.print(String.format(ATTR_TEMPLATE,"dojoType",DOJOTYPETEXT));
		out.print("/>"); 
		//if(logger.isInfo())logger.info("Exiting buildEmptyControl method of TextBoxHtmlRenderer ");
		
	}

}
