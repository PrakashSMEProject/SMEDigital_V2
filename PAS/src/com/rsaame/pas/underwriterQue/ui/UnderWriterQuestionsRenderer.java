package com.rsaame.pas.underwriterQue.ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.lookup.ui.IHtmlRenderer;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.uwq.svc.UWQService;
import com.rsaame.pas.vo.app.UWQInputsVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 *  Class UnderWriterQuestionsRenderer implements IHtmlRenderer 
 * 
 * @version 1.0  Jan 2012
 * @author m1016303
 *
 */
public class UnderWriterQuestionsRenderer implements IHtmlRenderer {

	private static final String ATTR_TEMPLATE = "%s='%s'";
	private static final String RADIO_DOJO_TYPE = "dijit.form.RadioButton";
	private static final String TEXT_DOJO_TYPE = "dijit.form.TextBox";
	private static final String DISABLED = com.Constant.CONST_DISABLED;
	private static final String READONLY = com.Constant.CONST_READONLY;
	private static final String PL_UW_TEXT_BOX_DISABLE_FLAG = "PL_UW_TEXT_BOX_DISABLE_FLAG";
	private static String FLAG_TRUE = "TRUE";	
	private static String FLAG_FALSE = "FALSE";
	private static Date STUDENT_LIABILITY_EFF_DATE_FOR_COMPARE = null;
	
	@Override
	public void buildHTMLContent(HashMap<String, Object> attributeList)	throws IOException,DataAccessException {
		
		UWQuestionsVO questionL = null;
		ThreadLevelContext.clear(PL_UW_TEXT_BOX_DISABLE_FLAG);
		ThreadLevelContext.set(PL_UW_TEXT_BOX_DISABLE_FLAG, FLAG_FALSE );
		/*Service call along with other condition checks and logging to be implemented*/
		if(!Utils.isEmpty(attributeList.get(AppConstants.OUT)))
		{
			/*Preparing HTML content for under writing questions*/
			JspWriter out=(JspWriter)attributeList.get("Out");
			StringBuffer responseString=new StringBuffer();

			if(!Utils.isEmpty(attributeList.get(AppConstants.INPUTTYPE)))
			{	
				/*Preparing for a database call to get required values*/
				if(attributeList.get(AppConstants.INPUTTYPE).toString().equalsIgnoreCase("underWritingQuestions")){
				UWQService uwqService = new UWQService();
				UWQInputsVO uwqVO = new UWQInputsVO();
				uwqVO.setSectionId(Integer.parseInt(attributeList.get(AppConstants.SECTION_ID).toString()));
				uwqVO.setTarCode(Integer.parseInt(attributeList.get("tarrif").toString()));
				 questionL = (UWQuestionsVO)uwqService.invokeMethod("getListOfDescription",uwqVO);
				Map<String,String> questionsList = new LinkedHashMap<String,String>();
				int i =0;
				/*
				 * In case uw q's needs to be displayed in different section on the same page. The qRange should be set. 
				 * For mat will be like -> 
				 * 0-3,0 - display q's 1 to 4 with names starting uwA[0]
				 * 4-7,3 display q's 5 to 7 with names starting uwA[3]
				 */
				if( !Utils.isEmpty( (String) attributeList.get( AppConstants.RANGE ) ) ){
					String[] rangeValue = ( (String) attributeList.get( AppConstants.RANGE ) ).split( "-" );

					if( !Utils.isEmpty( rangeValue ) && rangeValue.length == 2 ){
						String[] index = rangeValue[ 1 ].split( "," );
						if( !Utils.isEmpty( index ) && index.length == 2 ){
							i = Integer.valueOf( index[ 1 ] );
						}

					}

				}
				if(!Utils.isEmpty( questionL ))
				{
					if(!Utils.isEmpty( questionL.getQuestions() ))
					{
						int manColumnCount=1;
						responseString.append("<tr>");
						Object disabledFlagText  = "";	
						for(UWQuestionVO uwqQuestVO:questionL.getQuestions()){
							if(!Utils.isEmpty(uwqQuestVO))
							{
								//Start Added by Mindtree on 01/09/2015 for CR:104256 - Student Liability CR
								
								
								
								if(!Utils.isEmpty(attributeList.get(AppConstants.POL_EFFECTIVE_DATE))&&((Date) attributeList.get(AppConstants.POL_EFFECTIVE_DATE)).compareTo((Date)uwqQuestVO.getPreparedDate())<0){
									continue;
								}
								//End Added by Mindtree on 01/09/2015 for CR:104256 - Student Liability CR
								
								if(!Utils.isEmpty(uwqQuestVO.getQDesc())){
										if( isQsInRange( (String)attributeList.get( AppConstants.RANGE ) , uwqQuestVO.getQId() ) ){
											questionsList.put(String.valueOf(uwqQuestVO.getQId()),uwqQuestVO.getQDesc()); 
											Object disabledFlag = attributeList.get(AppConstants.DISABLEDFLAG);											
											/**
											 * Fix for 202 : Text box is enabled even though question is selected as No in PL page 
											 */
											if( Integer.toString(uwqVO.getSectionId()).equals( String.valueOf( SvcConstants.SECTION_ID_PL ))  ){
												
												if(!Utils.isEmpty(disabledFlagText)){
													disabledFlag = disabledFlagText;
												}
												
												//Start Added by Mindtree on 02/07/2015 for CR:104256 - Student Liability CR
												if(!Utils.isEmpty(uwqQuestVO.getResponse()) && uwqQuestVO.getResponse().equalsIgnoreCase("no")){ 
													disabledFlagText = "true";
												} else {
													disabledFlagText = "";
												}
												//End Added by Mindtree on 02/07/2015 for CR:104256 - Student Liability CR
												
											}
											/**
											 * End:  Fix for 202 - Text box is enabled even though question is selected as No in PL page 
											 */
											
											responseString.append(createColum(manColumnCount,uwqQuestVO,i,disabledFlag,attributeList.get( AppConstants.POLICY_TYPE )));
											i++;
											if(manColumnCount == Integer.parseInt( attributeList.get(AppConstants.NUMBEROFCOLS).toString())){
												responseString.append("</tr><tr>");	
												manColumnCount=0;
												
											}
											manColumnCount++;	
										}
								}
							}
						}
						responseString.append(com.Constant.CONST_INPUT_END);
						responseString.append(String.format(ATTR_TEMPLATE,"name",("uwqCount")));
						responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
						responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,i));
						responseString.append(">");
						responseString.append(com.Constant.CONST_TD_END);
					}
				}
				responseString.append("</tr>"); 
				out.print(responseString);
			}
			}
		}
		ThreadLevelContext.clear(PL_UW_TEXT_BOX_DISABLE_FLAG);
	}

	/**
	 * @param string
	 * @param qId
	 * @return
	 */
	private boolean isQsInRange( String range, Short qId ){

		boolean dispFlag = true;
		if( !Utils.isEmpty( range ) ){
			String[] rangeIndexValue = range.split( "-" );
			String[] rangeValue = null;
			if( ( rangeIndexValue.length == 2 ) ){
				rangeValue = new String[ 2 ];
				rangeValue[ 0 ] = rangeIndexValue[ 0 ];
				String[] temp = rangeIndexValue[ 1 ].split( "," );
				if( !Utils.isEmpty( temp ) ){
					if( temp.length == 2 ){
						rangeValue[ 1 ] = temp[ 0 ];
					}else{
						rangeValue[ 1 ] = rangeIndexValue[ 1 ];
					}
				}

			}
			if( !Utils.isEmpty( rangeValue ) && ( rangeValue.length == 2 ) && ( Short.valueOf( rangeValue[ 0 ] ).compareTo( qId ) <= 0 && Short.valueOf( rangeValue[ 1 ] ).compareTo( qId ) >= 0 ) ){
				dispFlag = true;
			}
			else{
				dispFlag = false;
			}

		}
		else{
			dispFlag = true;
		}
		return dispFlag;
	}

	private StringBuffer createColum(int manColumnCount,UWQuestionVO uwqQuestVO, int i, Object attribute, Object policyType) {
		
		StringBuffer responseString=new StringBuffer();
		boolean textBoxDisableflag = false;
		    responseString.append("<td ");
		    
		    if(!(Utils.isEmpty( policyType )) && policyType instanceof String && policyType.toString().equals(  Utils.getSingleValueAppConfig( "SBS_POLICY_TYPE" ) )){
		    	responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,"95"));
		    }
		    else if(!(Utils.isEmpty( policyType )) && policyType instanceof String && ( (policyType.toString().equals( Utils.getSingleValueAppConfig( "HOME_POL_TYPE" ) ) ))){
		    	responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,"400"));
		    }
		    else if(!(Utils.isEmpty( policyType )) && policyType instanceof String && (policyType.toString().equals( Utils.getSingleValueAppConfig( "TRAVEL_LONG_TERM_POLICY_TYPE" )) || (policyType.toString().equals( Utils.getSingleValueAppConfig( "TRAVEL_SHORT_TERM_POLICY_TYPE" ) ) ) )){
		    	responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,"250"));
		    }
		    else{
		    	responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,"95"));
		    }
			responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_CLASS,"first  rsaTableLineHeight plFirstUnderWriting"));
			responseString.append(">");
			
			responseString.append("<label ");
			responseString.append(String.format(ATTR_TEMPLATE,"for",uwqQuestVO.getQId()));
			responseString.append(String.format(ATTR_TEMPLATE,"style","margin-left: 7%"));
			responseString.append("><sup>*</sup>"+uwqQuestVO.getQDesc());
			responseString.append("</label></td>");
		
			
			if(uwqQuestVO.getResponseType().toString().equalsIgnoreCase(com.Constant.CONST_RADIO)){
				
				
				responseString.append("<td colspan='2'");
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,""));
			    responseString.append(">");
			    responseString.append(com.Constant.CONST_INPUT_END);
			    responseString.append(String.format(ATTR_TEMPLATE,"name",("uwA["+i+"]")));
			    responseString.append(String.format(ATTR_TEMPLATE,"id",("uwA["+i+"].yes")));
			    responseString.append(String.format(ATTR_TEMPLATE,"onChange","toggleDisablePLUWTextBox(this)"));
				responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_RADIO));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_DOJOTYPE,RADIO_DOJO_TYPE));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_CLASS,"floatL"));
				
				if(!Utils.isEmpty( attribute )&& attribute instanceof String && attribute.toString().equalsIgnoreCase( "true" ) )
				{
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_DISABLED,DISABLED));
				}
				
				
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,"yes"));
				if(uwqQuestVO.getResponse().equalsIgnoreCase("yes")){
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_CHECKED,com.Constant.CONST_CHECKED));
				}
				responseString.append("/><label style='width:23px!important; float:left;'>Yes</label>");
				/*responseString.append(com.Constant.CONST_TD_END);
			
				responseString.append("<td ");
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,""));
			    responseString.append(">");*/
			    responseString.append(com.Constant.CONST_INPUT_END);
			    responseString.append(String.format(ATTR_TEMPLATE,"name",("uwA["+i+"]")));
			    responseString.append(String.format(ATTR_TEMPLATE,"id",("uwA["+i+"].no")));
				responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_RADIO));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_DOJOTYPE,RADIO_DOJO_TYPE));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_CLASS,"floatL"));
				
				if(!Utils.isEmpty( attribute )&& attribute instanceof String && attribute.toString().equalsIgnoreCase( "true" ) )
				{
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_DISABLED,DISABLED));
				}
				
				responseString.append(String.format(ATTR_TEMPLATE,"onChange","toggleDisablePLUWTextBox(this,"+i+")"));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,"no"));
				if(uwqQuestVO.getResponse().equalsIgnoreCase("no")){
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_CHECKED,com.Constant.CONST_CHECKED));
					String uwQuestionsToggle = Utils.getSingleValueAppConfig( "UWTOGGLEID" );
					List<String> qIds = CopyUtils.asList( uwQuestionsToggle.split( "," ) );
					if( qIds.contains(  uwqQuestVO.getQId().toString() )  ){
						ThreadLevelContext.set(PL_UW_TEXT_BOX_DISABLE_FLAG, FLAG_TRUE );
					}else{
						ThreadLevelContext.set(PL_UW_TEXT_BOX_DISABLE_FLAG, FLAG_FALSE );
					}
				}
				responseString.append("/><label style='width:23px!important;float:left;'>No</label>");
				
				responseString.append(com.Constant.CONST_INPUT_END);
				responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_UWCODES_END+i+"]")));
				responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_UWCODES_END+i+"]")));
				responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,uwqQuestVO.getQId()));
				 responseString.append(">");
				 
				 responseString.append(com.Constant.CONST_INPUT_END);
					responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_UWANSTYPE_END+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_UWANSTYPE_END+i+"]")));
					responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,com.Constant.CONST_RADIO));
					responseString.append("/>");
				
				responseString.append(com.Constant.CONST_TD_END);
				
			} else if(uwqQuestVO.getResponseType().toString().equalsIgnoreCase("text")){
				
			/*	responseString.append("<td ");
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,""));
			    responseString.append(">");
				responseString.append(com.Constant.CONST_TD_END);*/
			
				responseString.append("<td colspan='2'");
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_CLASS,"PlTd"));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_WIDTH,""));
			    responseString.append(">");
			    responseString.append(com.Constant.CONST_INPUT_END);
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_CLASS,"PlTextfield"));
			    responseString.append(String.format(ATTR_TEMPLATE,"name",("uwA["+i+"]")));
			    responseString.append(String.format(ATTR_TEMPLATE,"id",("uwA["+i+"].text")));
				responseString.append(String.format(ATTR_TEMPLATE,"type","text"));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_DOJOTYPE,TEXT_DOJO_TYPE));
				responseString.append(String.format(ATTR_TEMPLATE,"maxlength",AppConstants.MAXLENGTH));
				
				if(!Utils.isEmpty( attribute )&& attribute instanceof String && attribute.toString().equalsIgnoreCase( "true" ) )
				{
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_READONLY,READONLY));
				}
				String uwQuestionsToggle = Utils.getSingleValueAppConfig( "UWTOGGLEDTEXT" );
				List<String> qIds = CopyUtils.asList( uwQuestionsToggle.split( "," ) );
				if( qIds.contains( uwqQuestVO.getQId().toString() ) && ThreadLevelContext.get(PL_UW_TEXT_BOX_DISABLE_FLAG).equals(FLAG_TRUE) ){
					responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_READONLY,READONLY));
				}
				responseString.append("/>");
				
				responseString.append(com.Constant.CONST_INPUT_END);
				responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_UWCODES_END+i+"]")));
				responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_UWCODES_END+i+"]")));
				responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,uwqQuestVO.getQId()));
				responseString.append("/>");
				
				responseString.append(com.Constant.CONST_INPUT_END);
				responseString.append(String.format(ATTR_TEMPLATE,"name",(com.Constant.CONST_UWANSTYPE_END+i+"]")));
				responseString.append(String.format(ATTR_TEMPLATE,"id",(com.Constant.CONST_UWANSTYPE_END+i+"]")));
				responseString.append(String.format(ATTR_TEMPLATE,"type",com.Constant.CONST_HIDDEN));
				responseString.append(String.format(ATTR_TEMPLATE,com.Constant.CONST_VALUE,"text"));
				responseString.append("/>");
				
				
				responseString.append(com.Constant.CONST_TD_END);
			}
		
		return responseString;
	}

	private static Date getStudentLiabilityEffectiveDateStart() {
		if (STUDENT_LIABILITY_EFF_DATE_FOR_COMPARE == null ){
			SimpleDateFormat generalDateFormat = new SimpleDateFormat( "dd-MMM-yyyy" );
			try{
				STUDENT_LIABILITY_EFF_DATE_FOR_COMPARE = generalDateFormat.parse( Utils.getSingleValueAppConfig( "STUDENT_LIABILITY_EFF_DATE_FOR_COMPARE" ) );
			}
			catch( ParseException e ){
				throw new SystemException( "", null, "Error in Student Liability Expiration date: Critical error" );
			}
		} else {
			return STUDENT_LIABILITY_EFF_DATE_FOR_COMPARE;
		}
		return STUDENT_LIABILITY_EFF_DATE_FOR_COMPARE;
		
		
	}
	
	@Override
	public void buildEmptyControl(JspWriter out) throws IOException {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
}
