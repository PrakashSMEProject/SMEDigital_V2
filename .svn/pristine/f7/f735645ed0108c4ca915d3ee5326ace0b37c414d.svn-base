/**
 * 
 */
package com.rsaame.pas.renewals.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.kaizen.framework.util.DateUtil;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnMail;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.RenewalResultsVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1019193 *
 *RH class which used to email all/specific renewal notices, 
 *It also checks whether the selected renewal notices are mailed or not  - Home/Travel-Phase3
 */
public class EmailRenewalNoticeCommonRH implements IRequestHandler{
	
	private final static Logger LOGGER = Logger.getLogger(EmailRenewalNoticeRH.class);
	private final static String SEND_NOT_SENT = "SEND_NOT_SENT";
	private final static String CHECK_FOR_RESEND = "CHECK_FOR_RESEND";
	private final static String GET_RENEWAL_NOTICE_NOT_SENT = "GET_RENEWAL_NOTICE_NOT_SENT";
	private final static String SAVE_RENEWAL_NOTICE = "SAVE_RENEWAL_NOTICE";
	private final static String RESEND = "RESEND";
	private final static String CHECK_RESEND = "CHECK_RESEND";
	//private String[] nameOfMonth = { "January","February", "March","April","May", "June", "July", "August", "September", "October","November", "December" };
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse responseObj) {
		Response response = new Response();
		// get all selected records
		Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy").create();
		RenewalResultsVO[] emailNoticeList = gson.fromJson(request.getParameter("selectedRows"), RenewalResultsVO[].class);		
		List<TTrnMail> tTrnList = new ArrayList<TTrnMail>();
		List<String> transIdList = new ArrayList<String>();
		int sendStatus = 0;
		String LOB = null;
		String classCode = null;
		boolean isResend = false;
		String action = request.getParameter("action");		
		Long[] policyIdList = gson.fromJson(request.getParameter("policyIdList"), Long[].class);
		DataHolderVO<Object> inputData = new DataHolderVO<Object>();
		inputData.setData(policyIdList);
		String lob = request.getParameter( "lob" );
		if (action.equals(CHECK_RESEND)) {			
			DataHolderVO<Boolean> output =  (DataHolderVO<Boolean>) TaskExecutor.executeTasks(CHECK_FOR_RESEND, inputData);
			if(output.getData().booleanValue()) {
				response.setData(RESEND);
				isResend =  true;
			}
		}
		
		if (!isResend) {	
			if (action.equals(SEND_NOT_SENT)) {
				DataHolderVO<Object[]> prnInput = new DataHolderVO<Object[]>();
				Object[] input = { emailNoticeList,policyIdList };
				prnInput.setData(input);				
				//fetch 4m the database based on the status, passing all the selected record policyID
				prnInput = (DataHolderVO<Object[]>)TaskExecutor.executeTasks(GET_RENEWAL_NOTICE_NOT_SENT, prnInput);					
				emailNoticeList =  (RenewalResultsVO[]) prnInput.getData();							
			}		

			UserProfile userProfile = AppUtils.getUserDetailsFromSession(request);
			String fromAddress = null;
			String ccAddress[]=Utils.getSingleValueAppConfig( "EMAIL_CC_RENL_NOTICE" ).split(",");
			List<String> emailErrorList = new ArrayList<String>();
			PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
			PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
			if (!Utils.isEmpty(userProfile.getRsaUser().getEmail())) {
				//fromAddress = userProfile.getRsaUser().getEmail();
				fromAddress =  Utils.getSingleValueAppConfig( "EMAIL_FROM_RENL_NOTICE" ) ;
			} else {
				BusinessException businessExcp=new BusinessException("mail.user.noemail", null, "User does not have mail id updated in user profile");
				LOGGER.error("PASEmailUtil:FromAddress Error:User does not have mail id updated in user profile");					
				throw businessExcp;
			}
			//if all the selected renewal notices have already been sent, then eMailNotieLst will be null
			if (!Utils.isEmpty(emailNoticeList)){
				String toAddress;
				String fileNames[]=new String[1];
				int emailCount = 0;
				for (RenewalResultsVO renNotice : emailNoticeList) {	      
					toAddress = renNotice.getEmailId();
					Long quoteNo = renNotice.getRenQuoteNo();
					Long polNo = renNotice.getPolicyNo();
					classCode = renNotice.getClassCode();
					Date polEffectiveDate = null;
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					try{
						polEffectiveDate = formatter.parse( renNotice.getPolEffectiveDate() ) ;
					}
					catch( ParseException e ){						
						LOGGER.debug("Exception ocuured during conversation of String to date - "+e.getMessage());
					}
					Integer locCode = renNotice.getPolLocCode();					
					HashMap <String,String>reportParams=new HashMap <String,String>();
					/*reportParams.put("quoteNo", quoteNo.toString());
					reportParams.put("endtId",  renNotice.getEndtId().toString());*/
					reportParams.put("policyId", String.valueOf(renNotice.getPolicyId()));
					reportParams.put("endoresementId", String.valueOf(renNotice.getEndtId()));
					reportParams.put("polValStartDate",renNotice.getPolValidityStartDate());
					reportParams.put("language", "E");
					//added for abudhabi/baharain
					reportParams.put("locationCode", Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
					
					if( lob.equals( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_POL_TYPE ) ) ){						
						reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE,ReportTemplateSet._HOME.toString());	
						LOB = "home";
					}
					else if ( lob.equals( Utils.getSingleValueAppConfig( "TRAVEL_POLICY_TYPE" )  ) ){
						reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE,ReportTemplateSet._TRAVEL.toString());						
						LOB = "travel";
					}
					else if(lob.equals( Utils.getSingleValueAppConfig( "WC_POL_TYPE" ) ) ) {
						reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE,ReportTemplateSet._WC.toString());						
						LOB = "WC";
					}
					//LOGGER.debug("**********PolicyId,PolLinkingId,EndtId & VSD = " + renNotice.getPolicyId() + " " + renNotice.getPolLinkingId() + " "+ renNotice.getEndtId() + " " + renNotice.getPolValidityStartDate());
					LOGGER.debug("*********Renewal Quote No : "+quoteNo);
					MailVO mailVO=new MailVO();					
					if (!Utils.isEmpty(toAddress)) {
						StringBuffer str = new StringBuffer();
						String emailStr = null;
						emailCount++;
						LOGGER.debug("******toAddress****=" + toAddress);
						//mailVO.setSignature( "RSA" );
						mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) );
						
						mailVO.setToAddress(toAddress);
						mailVO.setFromAddress(fromAddress);
						mailVO.setCcAddress(ccAddress);
						CommonVO commonVO = new CommonVO();
						commonVO.setEndtId( renNotice.getEndtId() );
						commonVO.setIsQuote( true );						
						commonVO.setPolEffectiveDate(  polEffectiveDate  );						
						commonVO.setPolicyNo( polNo );
						commonVO.setQuoteNo( quoteNo );
						commonVO.setLocCode( locCode );
						PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
						
						Integer tariffCode = policyDataVO.getScheme().getTariffCode();	
						Integer brCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						String month = new SimpleDateFormat("MMMM").format((DateUtil.addDate(polEffectiveDate, -1)));
						Calendar cal = Calendar.getInstance();
						cal.setTime(polEffectiveDate);
						Integer year = cal.get(Calendar.YEAR);
						System.out.println("month" + month);
						System.out.println("month" + year);
						
						StringBuilder urlBuilder = null;
						if( lob.equals( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_POL_TYPE ) ) ){
							urlBuilder = new StringBuilder(Utils.getSingleValueAppConfig( "B2C_RENEWAL_EMAIL_PATH_HOME" ));							
							emailStr = AppConstants.RENEWAL_HOME_CONTENT;
						}
						else{
							urlBuilder = new StringBuilder(Utils.getSingleValueAppConfig( "B2C_RENEWAL_EMAIL_PATH_TRAVEL" ));							
							emailStr = AppConstants.RENEWAL_TRAVEL_CONTENT;
						}
						
						if (!Utils.isEmpty( urlBuilder )) {
							
							String templateName = null;
							StringBuffer temp = new StringBuffer("REN_NOTICE_TEMPLATE_"); //"REN_NOTICE_TEMPLATE_HOME_20";
							templateName = temp.append(LOB.toUpperCase()).append("_").append(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)).toString();
							System.out.println("-----------------Slecting the template ---------------------"+templateName);
							/*// For Abudhabi use different template
							if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
									Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")){
								templateName = AppConstants.RENEWAL_NOTICE_TEMPLATE_LEGACY;
								System.out.println("-----------------Abudhabi template ---------------------");
							}
							else {
								templateName = AppConstants.RENEWAL_NOTICE_TEMPLATE;
							}*/
							templateName = Utils.getSingleValueAppConfig(templateName);
							LOGGER.debug("Going to read the file contents of file name - "+templateName);
							String emailContent = "";							
							String tag = Utils.getSingleValueAppConfig( "RENEWAL_NOTICE_CLICK_HERE_TAG" );							
							String fileName = request.getSession().getServletContext().getRealPath( templateName );							 
							FileReader fileReader = null;
							String line = "";
							try {
								//Oman D2C Email template change - Start
								if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
										&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
									String pmmId = AppConstants.OMAN_D2C_TRAVEL;
									List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.RENEWAL_NOTICE_OMAN_D2C, pmmId);
									if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
										emailContent =  resultSet.get( 0 );
										LOGGER.debug("emailContent for renewal notice is coming from DB for Oman D2C: "+emailContent);
									}
								}else{
									File file = new File(fileName);							
									fileReader = new FileReader(file);
									BufferedReader reader = new BufferedReader(fileReader);
									while ((line = reader.readLine()) != null) {
										
										emailContent =str.append(line).append("\n").toString();
										//emailContent += line + "\n";
									}
								}
							} catch (FileNotFoundException e) {
								LOGGER.debug("Exception ocuured while reading the Jsp file content - "+templateName);
							} catch (IOException e) {
								LOGGER.debug("Exception ocuured while reading the Jsp filFree content - "+e.getMessage());
							}
							
							finally{  //SONARFIX - 20-apr-2018 -- added finally block
								try{
								if(fileReader!=null){
									fileReader.close();
								  }
								}
								catch(Exception e){
									e.getMessage();
								 }
								}
							
							if (!Utils.isEmpty(emailContent)) {
								if( lob.equals( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_POL_TYPE ) ) ){
									mailVO.setSubjectText(AppConstants.RENEWAL_HOME_NOTICE_EMAIL_SUBJECT + " -#" +polNo );
								}
								else if(lob.equals( Utils.getSingleValueAppConfig( "WC_POL_TYPE" ) ) ) {
									mailVO.setSubjectText(AppConstants.RENEWAL_WC_NOTICE_EMAIL_SUBJECT + " -#" +polNo );
								} else {
									mailVO.setSubjectText(AppConstants.RENEWAL_TRAVEL_NOTICE_EMAIL_SUBJECT + " -#" +polNo );
								}
							}
							//StringBuilder urlBuilder = new StringBuilder(Utils.getSingleValueAppConfig( "B2C_RENEWAL_EMAIL_PATH" ));
							String encryptedQuoteNo = AppUtils.encryptAndDecryptData( String.valueOf(quoteNo), Boolean.TRUE );
							if (lob.equals( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_POL_TYPE ) )) {
								urlBuilder.append( Utils.getSingleValueAppConfig( "B2C_RENEWAL_HOME_CONTROLLER" ) ).append( "?renQuote=" +encryptedQuoteNo);
								
							} else {
								urlBuilder.append( Utils.getSingleValueAppConfig( "B2C_RENEWAL_TRAVEL_CONTROLLER" ) ).append( "?renQuote=" +encryptedQuoteNo );
							}
							tag = tag.replace( Utils.getSingleValueAppConfig( "B2C_TAG_IDF_URL" ), urlBuilder.toString() );							
							
							emailContent = emailContent.replace(AppConstants.RENEWAL_NOTICE_EMAIL_INSURED_NAME, WordUtils.capitalizeFully(renNotice.getInsuredName()));
							emailContent = emailContent.replace( AppConstants.RENEWAL_NOTICE_PRODUCT, LOB.toUpperCase() );
							emailContent = emailContent.replace(AppConstants.RENEWAL_NOTICE_LOB, LOB);
							emailContent = emailContent.replace( AppConstants.HOME_TRAVEL_EMAIL_CONTENT, emailStr );
							
							if(!tariffCode.equals( AppConstants.PACKAGRED_HOME_TARIFF ) && !tariffCode.equals( AppConstants.STANDARD_HOME_TARIFF ) 
									&& !tariffCode.equals(Integer.valueOf( Utils.getSingleValueAppConfig("EMIRATES_HOME_TARIFF") ))  
									&& lob.equals( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_POL_TYPE ) )){
								emailContent = emailContent.replace( AppConstants.EMAIL_HOME_PACKAGED_TEXT,Utils.getSingleValueAppConfig("OTHER_THAN_HOME_PACKAGED_TEXT_CONTENT") );
							} else if(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals(Integer.valueOf(Utils.getSingleValueAppConfig("DIST_CHANNEL_BROKER")))){
								emailContent = emailContent.replace( AppConstants.EMAIL_HOME_PACKAGED_TEXT,Utils.getSingleValueAppConfig("OTHER_THAN_HOME_PACKAGED_TEXT_CONTENT") );
							}
							else{
								emailContent = emailContent.replace( AppConstants.EMAIL_HOME_PACKAGED_TEXT,Utils.getSingleValueAppConfig("EMAIL_HOME_PACKAGED_TEXT_CONTENT") );								
							}
							
							emailContent = emailContent.replace( "%MON%", month );
							emailContent = emailContent.replace( "%YEAR%", year.toString() );
							emailContent = emailContent.replace( Utils.getSingleValueAppConfig( "RENEWAL_NOTICE_CLICK_HERE_TAG_IDF" ), tag );
							mailVO.setSignature( "RSA" );
							mailVO.setMailType( AppConstants.MAIL_TYPE_HTML );
							mailVO.setMailContent( new StringBuilder(emailContent) );
						}	
						
						//fileNames[0]=Utils.getSingleValueAppConfig("QUOTE_DOC_RPTDESIGN_RENEWAL_LOC") + quoteNo + "-Renewal quote.pdf";
						//fileNames[0] = Utils.getSingleValueAppConfig("QUOTE_DOC_PROPOSAL_LOC") + quoteNo + "-Quote.pdf";	
						//added Quote document file as Renewal Email to have quote document as attachment instead of the renewal notice
						fileNames[0] = Utils.getSingleValueAppConfig("QUOTE_DOC_PROPOSAL_LOC") + quoteNo + "-Renewal quote.pdf";						
						
						mailVO.setFileNames(fileNames);
						mailVO.setDocParameter(reportParams);
						LOGGER.debug("*******file name = " + fileNames[0]);
						// Create the document
						boolean docSuccess = true;
						try {
							mailVO = (MailVO) docCreator.invokeMethod("createDocument", mailVO);
							LOGGER.debug("*********createDocument called**********");
						} catch (Exception e) {
							e.printStackTrace();						
							emailErrorList.add(com.Constant.CONST_QUOTE_NO_END + quoteNo +com.Constant.CONST_EMAIL_ID_END+ toAddress +com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
							docSuccess = false;
						}
	
						if (docSuccess) {
							if (!Utils.isEmpty(mailVO.getDocCreationStatus())) {
								if ("failure".equals(mailVO.getDocCreationStatus())) {
									emailErrorList.add(com.Constant.CONST_QUOTE_NO_END + quoteNo + com.Constant.CONST_EMAIL_ID_END + toAddress + com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
									docSuccess = false;
								}	
							} else {
								emailErrorList.add(com.Constant.CONST_QUOTE_NO_END + quoteNo + com.Constant.CONST_EMAIL_ID_END+ toAddress + com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
								docSuccess = false;
							}
						}	
						if (docSuccess) {
							try {	
								LOGGER.debug("calling sendMail");
								//mailVO = (MailVO) mailer.invokeMethod("sendMail", mailVO);								
								mailVO = (MailVO) mailer.invokeMethod("sendEmailWithImage",mailVO, "RENEWAL_NOTICE");
																
								sendStatus = 1;					
								LOGGER.debug("sendMail called");
							} catch (Exception e)
							{
								e.printStackTrace();
								emailErrorList.add(polNo + ": Error while sending email");
							}
							if (!Utils.isEmpty(mailVO.getMailStatus()) && mailVO.getMailStatus().equals("fail")) {								
									emailErrorList.add(com.Constant.CONST_QUOTE_NO_END + quoteNo + com.Constant.CONST_EMAIL_ID_END + toAddress +" Error : " + mailVO.getError());								
									sendStatus = 0;														
							}
						
							TTrnMail tTrnMail = new TTrnMail();
							String transactionId = renNotice.getPolicyId() + "|"+renNotice.getEndtId() + "|"+renNotice.getPolDocumentCode();
							tTrnMail.setMailType(Utils.getSingleValueAppConfig("RENEWAL_NOTICE"));
							tTrnMail.setRecipientMailId(toAddress);
							tTrnMail.setStatus((byte) sendStatus);
							tTrnMail.setTransactionId(transactionId);
							tTrnMail.setSentDate(new Timestamp( System.currentTimeMillis()));
							tTrnMail.setMalClassCode(Short.parseShort(classCode));
							transIdList.add(transactionId);
							tTrnList.add(tTrnMail);
						}
					}	
				} // End of loop
			
				if (emailCount == 0) {
					emailErrorList.add("None of the selected policies have the email id");
				} else if ( tTrnList.size() > Integer.parseInt(AppConstants.zeroVal ) ){
					Object[] renewalNoticeData = { tTrnList, transIdList };
					inputData.setData(renewalNoticeData);
					TaskExecutor.executeTasks(SAVE_RENEWAL_NOTICE, inputData);
				}			
				
			} else {
				emailErrorList.add("Renewal notice has already got mailed for all the selected policies");
			}
			
			if (!Utils.isEmpty(emailErrorList)) {
				response.setData(emailErrorList);
			}	
			
		}
		return response;
	}

}
