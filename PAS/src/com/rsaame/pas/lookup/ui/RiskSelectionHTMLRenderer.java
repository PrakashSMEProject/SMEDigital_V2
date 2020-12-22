/**
 * 
 */
package com.rsaame.pas.lookup.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;

/**
 * @author M1016284
 * 
 */
public class RiskSelectionHTMLRenderer extends BaseHTMLRenderer {

	private static final String ATTR_TEMPLATE = "%s='%s'";

	/**
	 * Application context path
	 */
	private String contextPath = "";
	
	private Boolean isDisabled = true;
	
	/**
	 * 
	 */
	public RiskSelectionHTMLRenderer() {
		super();		
	}
	
	/**
	 * @param contextPath
	 */
	public RiskSelectionHTMLRenderer(String contextPath) {
		super();
		this.contextPath = contextPath;
	}
	
	
	public void buildHTMLContent(HashMap<String, Object> attributeList)
			throws IOException, DataAccessException {
		System.out.println("Entered CheckBOX renderer");
		if (!Utils.isEmpty(attributeList.get(AppConstants.OUT))) {
			JspWriter out = (JspWriter) attributeList.get("Out");
			StringBuffer responseString = new StringBuffer();
			System.out.println(responseString);

			responseString.append("<table ");
			responseString.append(String.format(ATTR_TEMPLATE, "border", "0"));
			responseString.append(String.format(ATTR_TEMPLATE, "cellpadding",
					"0"));
			responseString.append(String.format(ATTR_TEMPLATE, "cellspacing",
					"0"));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_WIDTH, "98%"));
			responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_ALIGN,
					"center"));

			responseString.append(">");
			responseString.append("<tr><td>");// Main outer Table row and column
												// Starting

			if (!Utils.isEmpty(attributeList.get(AppConstants.IDENTIFIER))) {
				LookUpVO lookUpVO = new LookUpVO();
				lookUpVO.setCategory(attributeList.get(AppConstants.IDENTIFIER)
						.toString());
				lookUpVO.setLevel1("ALL");
				lookUpVO.setLevel2("ALL");
				LookUpService lookUpService = getLookUpService();
				LookUpListVO lookUpListVo = (LookUpListVO) lookUpService
						.getListOfDescription(lookUpVO);

				/*
				 * PolicyContext to be populated with available risk sections
				 * i.e. both mandatory non mandatory sections
				 */
				
				Integer[] sections = new Integer[13];
				int i = 0;
				if (!Utils.isEmpty(lookUpListVo)) {
					if (!Utils.isEmpty(lookUpListVo.getLookUpList())) {
						List<RiskSectionDetails> mandatorySections = new ArrayList<RiskSectionDetails>();
						List<RiskSectionDetails> optionalSections = new ArrayList<RiskSectionDetails>();

						for (LookUpVO lookUpVOfromDB : lookUpListVo
								.getLookUpList()) {
							if (!Utils.isEmpty(lookUpVOfromDB)) {

								sections[i] = lookUpVOfromDB.getCode().intValue();
										
								i = i + 1;
								if (!Utils.isEmpty(lookUpVOfromDB
										.getDescription())) {

									if ((lookUpVOfromDB.getDescription()
											.equalsIgnoreCase(SvcUtils.getLookUpDescription("SBS_SECTION_PAR", "ALL", "ALL", AppConstants.SECTION_ID_PAR)))
											|| (lookUpVOfromDB.getDescription()
													.equalsIgnoreCase(SvcUtils.getLookUpDescription("SBS_SECTION_PL", "ALL", "ALL", AppConstants.SECTION_ID_PL)))) {
										RiskSectionDetails riskSectionDetails = new RiskSectionDetails();
										riskSectionDetails
												.setFieldDescription(lookUpVOfromDB
														.getDescription());
										riskSectionDetails
												.setFieldID(lookUpVOfromDB
														.getCode().intValue());
										mandatorySections
												.add(riskSectionDetails);
									} else {
										RiskSectionDetails riskSectionDetails = new RiskSectionDetails();
										riskSectionDetails
												.setFieldDescription(lookUpVOfromDB
														.getDescription());
										riskSectionDetails
												.setFieldID(lookUpVOfromDB
														.getCode().intValue());
										optionalSections
												.add(riskSectionDetails);
									}

								}
							}
						}
						
						/*Ticket Id 154341 - enable GIT section at scheme level */ 
						HttpServletRequest request = (HttpServletRequest)attributeList.get("request");
						PolicyContext polContext = PolicyContextUtil.getPolicyContext(request);
						
						//System.out.println("polContext -->"+polContext);
												
						Map<Integer, String> sectionMap = new HashMap<Integer, String>();
						
						if(!Utils.isEmpty(polContext.getPolicyDetails().getScheme()) 
								&& !Utils.isEmpty(polContext.getPolicyDetails().getScheme().getSchemeCode())){
							sectionMap = DAOUtils.getSectionVisibility(polContext.getPolicyDetails().getScheme().getSchemeCode());
							if(!Utils.isEmpty(sectionMap) && sectionMap.size()> 0){
								isDisabled = false;
							}
							polContext.getPolicyDetails().getScheme().setIsSecDisabled(isDisabled);
						}
						
						//polContext.populateAllAvailableSec(sections);
					
						if (!mandatorySections.isEmpty()) {
							responseString.append("<div ");
							responseString.append( String.format(ATTR_TEMPLATE, "id", "baseSectionsDivId") );
							responseString.append(">");
							responseString.append("<table "); // Mandatory Table
																// row and
																// column
																// Starting
							responseString.append(String.format(ATTR_TEMPLATE,
									"border", "0"));
							responseString.append(String.format(ATTR_TEMPLATE,
									"cellpadding", "0"));
							responseString.append(String.format(ATTR_TEMPLATE,
									"cellspacing", "0"));
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_WIDTH, "98%"));
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_ALIGN, "center"));
							responseString.append(">");

							responseString.append("<tr>");
							int manColumnCount = 1;
							for (RiskSectionDetails riskSectionDetails : mandatorySections) {

								/** SK : Changes */
								/*
								* Pass on the index as well during creation of mandatory risk
								* sections 
								*/
								responseString
										.append(createColum(riskSectionDetails));
								if (manColumnCount == 4) {
									responseString.append("</tr><tr>");
									manColumnCount = 0;
								}
								manColumnCount++;
							}

							while (manColumnCount < 5) {
								responseString.append("<td ");
								responseString.append(String.format(
										ATTR_TEMPLATE, com.Constant.CONST_HEIGHT, "22px"));
								responseString.append(String.format(
										ATTR_TEMPLATE, com.Constant.CONST_WIDTH, com.Constant.CONST_NO_150PX)); // Related
																			// to
																			// alignment
								responseString.append(">");
								responseString.append("<label>");
								responseString.append(" ");
								responseString.append(com.Constant.CONST_LABEL_CLOSING);
								responseString.append(com.Constant.CONST_TD_END);
								manColumnCount++;
							}
							responseString.append(com.Constant.CONST_TR_END);
						}

						if (!optionalSections.isEmpty()) {
	
							responseString.append("<tr>");

							responseString.append("<td ");
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_HEIGHT, "22px"));
							responseString.append("><span ");
							responseString.append(String.format(ATTR_TEMPLATE,
									com.Constant.CONST_STYLE, "font-size: 9pt"));
							responseString.append(">");
							responseString
									.append("<b>" + "Additional Section");
							responseString.append("</b></span>");
							responseString.append(com.Constant.CONST_TD_END);
							responseString.append(com.Constant.CONST_TR_END);
							/** SK : Changes */
							/*
							 * A div has been introduced during creating html
							 * content for optional section id as now name attribute of 
							 * all the section check boxes rendered are made as section id itself
							 * Hence to iterate the optional section check boxes this div has been 
							 * introduced
							 */
							responseString.append(String.format(com.Constant.CONST_TABLE_END));
							responseString.append("</div>");
							responseString.append("<div ");
							responseString.append( String.format(ATTR_TEMPLATE, "id", "optionalSectionsDivId") );
							responseString.append(">");
							responseString.append( "<table>" );
							responseString.append("<tr>");

							int optColumnCount = 1;
							for (RiskSectionDetails riskSectionDetails : optionalSections) {

								responseString
										.append( createDisabledColumn(riskSectionDetails) );
								if (optColumnCount == 4) {
									responseString.append("</tr><tr>");
									optColumnCount = 0;

								}
								optColumnCount++;
							}
							while (optColumnCount < 5) {
								responseString.append("<td ");
								responseString.append(String.format(
										ATTR_TEMPLATE, com.Constant.CONST_HEIGHT, "22px"));
								responseString.append(String.format(
										ATTR_TEMPLATE, com.Constant.CONST_WIDTH, com.Constant.CONST_NO_150PX)); // Related
																			// to
																			// alignment
								responseString.append(">");
								responseString.append("<label>");
								responseString.append(" ");
								responseString.append(com.Constant.CONST_LABEL_CLOSING);
								responseString.append(com.Constant.CONST_TD_END);
								optColumnCount++;
							}
							responseString.append(com.Constant.CONST_TR_END);
							//responseString.append(String.format(com.Constant.CONST_TR_END)); // Optional
																			// Table
																			// row
																			// and
																			// column
																			// closing
							responseString.append(com.Constant.CONST_TABLE_END);
							responseString.append("</div>");
							
						}

					}
					responseString.append("<tr>");
					responseString.append("<td ");
					responseString.append(String.format(ATTR_TEMPLATE,
							"colspan", "4"));
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_ALIGN,
							"right"));
					responseString.append(">");

					
					responseString.append("<button ");
					if( AppUtils.isRiskSelectionEnabled() ){
						responseString.append(String.format(ATTR_TEMPLATE,
								com.Constant.CONST_DISABLED, "true"));
					}
					
					responseString.append(String.format(ATTR_TEMPLATE, "name",
							"next"));
					responseString.append(String.format(ATTR_TEMPLATE, "id",
							"nextpop"));
					responseString.append(String.format(ATTR_TEMPLATE,
							"onclick", "moveToPar();"));
					responseString.append(String.format(ATTR_TEMPLATE,"dojoType","dijit.form.Button"));
					responseString.append( String.format(ATTR_TEMPLATE,"type","button") );
					responseString.append(String.format(ATTR_TEMPLATE, com.Constant.CONST_STYLE,
							"margin-right: 30px;background: url("
									+ contextPath + "/static/img/buttonBg.jpg"
									+ ") repeat-x; height:20px;color:white;"));
					responseString.append(">Next</button>");
					
				}
			}

			responseString.append(String.format("</td></tr>")); // Main outer
																// Table row and
																// column
																// closing
			responseString.append(String.format(com.Constant.CONST_TABLE_END));
			out.print(responseString);
			System.out.println(responseString);
		}
	}

	private StringBuffer createColum( RiskSectionDetails riskSectionDetails ){
		StringBuffer responseString = new StringBuffer();
		responseString.append( "<td " );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_HEIGHT, "22px" ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_WIDTH, com.Constant.CONST_NO_150PX ) ); // Related
																					// to
																					// alignment
		responseString.append( ">" );
		responseString.append( com.Constant.CONST_INPUT_END );
		
		if(	AppUtils.isRiskSelectionEnabled() ){
			responseString.append( String.format( ATTR_TEMPLATE, "id", riskSectionDetails.getFieldID() ) );// "checkBox"+
			responseString.append( String.format( ATTR_TEMPLATE, "name", riskSectionDetails.getFieldID() ) );
		}
		responseString.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
		responseString.append( String.format( ATTR_TEMPLATE, "dojoType", "dijit.form.CheckBox" ) );

		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, "" ) );

		/** SK : Changes */
		/*
		 * Name attribute is also populated as section id itself
		 */
		//responseString.append(String.format(ATTR_TEMPLATE, "name", "mandatory["+index+"]"));
		
		responseString.append( String.format( ATTR_TEMPLATE, "onClick", "toggleCheckBoxesAndButton("+isDisabled+");" ) );
		
		//If location Oman display mandatory sections also as checked and disabled. 
		if( !AppUtils.isRiskSelectionEnabled() ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DISABLED, "true" ) );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED ) );
		}
		
		responseString.append( "/>" );
		//If location Oman display mandatory sections also as checked and disabled so submit hidden variable values. 
		if(	!AppUtils.isRiskSelectionEnabled() ){
			
			responseString.append( com.Constant.CONST_INPUT_END );
			responseString.append( String.format( ATTR_TEMPLATE, "type", "hidden" ) );
			responseString.append( String.format( ATTR_TEMPLATE, "id", riskSectionDetails.getFieldID() ) );// "checkBox"+
			responseString.append( String.format( ATTR_TEMPLATE, "name", riskSectionDetails.getFieldID() ) );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE,  riskSectionDetails.getFieldID()) );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE,  "visibility: hidden" ));
			responseString.append( "/>" );
		}
		
		responseString.append( " <label " );
		responseString.append( String.format( ATTR_TEMPLATE, "for", riskSectionDetails.getFieldID() ) );
		responseString.append( ">" + riskSectionDetails.getFieldDescription() );
		responseString.append( com.Constant.CONST_LABEL_CLOSING );

		responseString.append( com.Constant.CONST_TD_END );
		return responseString;
	}

	private StringBuffer createDisabledColumn( RiskSectionDetails riskSectionDetails ){
		/** SK : Changes */
		/*
		 * Name attribute is also populated as section id itself
		 */
		StringBuffer responseString = new StringBuffer();
		responseString.append( "<td " );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_HEIGHT, "22px" ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_WIDTH, com.Constant.CONST_NO_150PX ) ); // Related
																					// to
																					// alignment
		responseString.append( ">" );
		responseString.append( com.Constant.CONST_INPUT_END );
		responseString.append( String.format( ATTR_TEMPLATE, "type", "checkbox" ) );
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE, "" ) );
		
		if(	AppUtils.isRiskSelectionEnabled() ){
			responseString.append( String.format( ATTR_TEMPLATE, "id", riskSectionDetails.getFieldID() ) ); // "checkBox"+
			responseString.append( String.format( ATTR_TEMPLATE, "name", riskSectionDetails.getFieldID() ) );
		}
		
		responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_DISABLED, "true" ) );
		responseString.append( String.format( ATTR_TEMPLATE, "onClick", "enableDOS();" ) );
		//If location Oman display non mandatory sections also as checked. 
		if(	!AppUtils.isRiskSelectionEnabled() ){
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_CHECKED, com.Constant.CONST_CHECKED ) );
		}
		responseString.append( "/>" );
		//If location Oman display mandatory sections also as checked and disabled so submit hidden variable values. 
		if(	!AppUtils.isRiskSelectionEnabled() ){
			
			responseString.append( com.Constant.CONST_INPUT_END );
			responseString.append( String.format( ATTR_TEMPLATE, "type", "hidden" ) );
			responseString.append( String.format( ATTR_TEMPLATE, "id", riskSectionDetails.getFieldID() ) );// "checkBox"+
			responseString.append( String.format( ATTR_TEMPLATE, "name", riskSectionDetails.getFieldID() ) );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_VALUE,  riskSectionDetails.getFieldID()) );
			responseString.append( String.format( ATTR_TEMPLATE, com.Constant.CONST_STYLE,  "visibility: hidden" ));
			responseString.append( "/>" );
		}
		responseString.append( " <label " );
		responseString.append( String.format( ATTR_TEMPLATE, "for", riskSectionDetails.getFieldDescription() ) );
		responseString.append( ">" + riskSectionDetails.getFieldDescription() );
		responseString.append( com.Constant.CONST_LABEL_CLOSING );
		responseString.append( com.Constant.CONST_TD_END );

		return responseString;
	}

	public void buildEmptyControl(JspWriter out) throws IOException {
		out.print("<p>An exception occurred!!! Please contact your administrator.</p>");
	}

}
