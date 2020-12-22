package com.rsaame.pas.b2c.cmn.handlers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.util.DateUtil;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.com.svc.ConvertToPolicyReminderCmnSvc;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.dao.model.TTrnMail;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * This scheduler will be called as per configured in applicationContext.xml. 
 * This scheduler will trigger the convert to policy reminder email
 * after 24 hours of quote creation and 15 days left for quote expiration. 
 * 
 * @author M1020859, M1033804
 *
 */
public class ConvertToPolicyReminderScheduler extends QuartzJobBean  {
	
	private static final Logger logger = Logger.getLogger(ConvertToPolicyReminderScheduler.class);
	private static ConvertToPolicyReminderCmnSvc reminderSvc;
	private static PremiumSaveCommonSvc prmSvc;
	private final static String SEND_NOT_SENT = "SEND_NOT_SENT";
	private final static String CHECK_FOR_RESEND = "CHECK_FOR_RESEND";
	private final static String GET_RENEWAL_NOTICE_NOT_SENT = "GET_RENEWAL_NOTICE_NOT_SENT";
	private final static String SAVE_RENEWAL_NOTICE = "SAVE_RENEWAL_NOTICE";
	private final static String RESEND = "RESEND";
	private final static String CHECK_RESEND = "CHECK_RESEND";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		List<PolicyDataVO> policyDataVOList = (List<PolicyDataVO>) reminderSvc.invokeMethod("getLastDaysPendingQuotes", new Object());
		List<PolicyDataVO> polDataVOList = (List<PolicyDataVO>) reminderSvc.invokeMethod("getQuoteExpiryReminderData", new Object());
		List<PolicyDataVO> dataVoListPolicy = (List<PolicyDataVO>) reminderSvc.invokeMethod("getPolicyExpiryListReminder", new Object());
		boolean isTwentyFourHrsInd = Boolean.FALSE;
		boolean isPolicyExpiry = Boolean.FALSE;
		if (!Utils.isEmpty(policyDataVOList)) {
			isTwentyFourHrsInd = Boolean.TRUE;
			ServiceContext.setLoginLocation( "20" );
			ServiceContext.setLocation( "20" );
			populateAndTriggerMail(policyDataVOList, isTwentyFourHrsInd,isPolicyExpiry);
		} 
		if (!Utils.isEmpty(polDataVOList)) {
			isTwentyFourHrsInd = Boolean.FALSE;
			ServiceContext.setLoginLocation( "20" );
			ServiceContext.setLocation( "20" );
			populateAndTriggerMail(polDataVOList, isTwentyFourHrsInd,isPolicyExpiry);
		} 
		if (!Utils.isEmpty(dataVoListPolicy)) {
			isPolicyExpiry = Boolean.TRUE;
			ServiceContext.setLoginLocation( "20" );
			ServiceContext.setLocation( "20" );
			populateAndTriggerMail(dataVoListPolicy, isTwentyFourHrsInd,isPolicyExpiry);
		} 
	}
	
	/**
	 * This method will populate the instance of MailVO and will pass the instance
	 * to the service layer to trigger the email
	 * 
	 * @param policyDataVOList
	 * @param isTwentyFourHrsInd
	 */
	private void populateAndTriggerMail(List<PolicyDataVO> policyDataVOList, boolean isTwentyFourHrsInd,boolean isPolicyExpiry) {
		
		MailVO mailVO = null;
		boolean mailStatus = false;
		for (PolicyDataVO policyDataVO:policyDataVOList){
			//Populating the mailVO for email trigger
			mailVO = (MailVO)Utils.getBean("mailVO");
			try{
				if(isPolicyExpiry) {
					sendRenewalEmailReminder(policyDataVOList,policyDataVO);
				}else {
					populateMailVO(mailVO, policyDataVO, isTwentyFourHrsInd);
				}
			} catch (Exception e){
				//logger.equals("Error while populating mail VO:"+e.getMessage());	/* commented this line and added below logger statement - sonar violation fix*/
				logger.error("Error while populating mail VO:"+e.getMessage());
			}
			if (!Utils.isEmpty(mailVO.getMailContent())) {
				mailStatus = CommonHandler.sendGeneralEmail(mailVO, "QUOTE");
				if (!mailStatus) {
					logger.debug("Email cannot be triggered for reminding for convert to policy from scheduler for quote number - "+policyDataVO.getQuoteNo());
				}
			}
		}
	}
	/**
	 * @author DileepMS
	 * Ticket 165419 : Implementation of sending Renewal notice Email reminder for 45 days prior to policy expiry with document and clickhere link.
	 * @param policyDataVOList
	 * @param policyDataVO
	 */
	private void sendRenewalEmailReminder(List<PolicyDataVO> policyDataVOList, PolicyDataVO policyDataVO) {
		
		List<TTrnMail> tTrnList = new ArrayList<TTrnMail>();
		List<String> transIdList = new ArrayList<String>();
		int sendStatus = 0;
		String LOB = null;
		Integer classCode = null;
		boolean isResend = false;
		String action = "CHECK_RESEND";	
		DataHolderVO<Object> inputData = new DataHolderVO<Object>();
		String lob = policyDataVO.getPolicyType().toString();
		/*if (action.equals(CHECK_RESEND)) {			
			DataHolderVO<Boolean> output =  (DataHolderVO<Boolean>) TaskExecutor.executeTasks(CHECK_FOR_RESEND, inputData);
			if(output.getData().booleanValue()) {
				isResend =  true;
			}
		}*/
		
		if (!isResend) {	
			/*if (action.equals(SEND_NOT_SENT)) {
				DataHolderVO<Object[]> prnInput = new DataHolderVO<Object[]>();
				Object[] input = { policyDataVO,policyDataVOList };
				prnInput.setData(input);				
				//fetch 4m the database based on the status, passing all the selected record policyID
				prnInput = (DataHolderVO<Object[]>)TaskExecutor.executeTasks(GET_RENEWAL_NOTICE_NOT_SENT, prnInput);					
				policyDataVO =  (PolicyDataVO) prnInput.getData();							
			}*/

			String fromAddress = null;
			String ccAddress[]=Utils.getSingleValueAppConfig( "EMAIL_CC_RENL_NOTICE" ).split(",");
			List<String> emailErrorList = new ArrayList<String>();
			PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
			PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
			if (Utils.isEmpty(fromAddress)) {
				fromAddress =  Utils.getSingleValueAppConfig( "EMAIL_FROM_RENL_NOTICE" ) ;
			} else {
				BusinessException businessExcp=new BusinessException("mail.user.noemail", null, "User does not have mail id updated in user profile");
				logger.error("PASEmailUtil:FromAddress Error:User does not have mail id updated in user profile");					
				throw businessExcp;
			}

			if (!Utils.isEmpty(policyDataVO)){
				String toAddress;
				String fileNames[]=new String[1];
				int emailCount = 0;
				for (PolicyDataVO renNotice : policyDataVOList) {	      
					toAddress = renNotice.getGeneralInfo().getInsured().getEmailId();
					Long quoteNo = renNotice.getQuoteNo();
					Long polNo = renNotice.getPolicyNo();
					classCode = renNotice.getPolicyClassCode();
					Date polEffectiveDate = renNotice.getCommonVO().getPolEffectiveDate();

					Integer locCode = renNotice.getCommonVO().getLocCode();					
					HashMap <String,String>reportParams=new HashMap <String,String>();
					String polValStartDate= new SimpleDateFormat("dd-MMM-yyyy").format(renNotice.getValidityStartDate());
					reportParams.put("quoteNo", quoteNo.toString());
					reportParams.put("endtId",  renNotice.getEndtId().toString());
					reportParams.put("policyId", String.valueOf(renNotice.getPolicyId()));
					reportParams.put("endoresementId", String.valueOf(renNotice.getEndtId()));
					reportParams.put("polValStartDate",polValStartDate);
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

					logger.debug("*********Renewal Quote No : "+quoteNo);
					MailVO mailVO=new MailVO();					
					if (!Utils.isEmpty(toAddress)) {
						String emailStr = null;
						emailCount++;
						logger.debug("******toAddress****=" + toAddress);
						//mailVO.setSignature( "RSA" );
						mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) );
						
						mailVO.setToAddress(toAddress);
						mailVO.setFromAddress(fromAddress);
						mailVO.setCcAddress(ccAddress);
						CommonVO commonVO = new CommonVO();
						commonVO.setEndtId( renNotice.getEndtId() );
						commonVO.setIsQuote( true );						
						commonVO.setPolEffectiveDate( polEffectiveDate   );						
						commonVO.setPolicyNo( polNo );
						commonVO.setQuoteNo( quoteNo );
						commonVO.setLocCode( locCode );
						policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
						
						Integer tariffCode = policyDataVO.getScheme().getTariffCode();	
						Integer brCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						String month = new SimpleDateFormat("MMMM").format((DateUtil.addDate(polEffectiveDate, -1)));
						Calendar cal = Calendar.getInstance();
						cal.setTime(polEffectiveDate);
						Integer year = cal.get(Calendar.YEAR);
						System.out.println("month " + month);
						System.out.println("month " + year);
						
						StringBuilder urlBuilder = null;
						if( lob.equals( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_POL_TYPE ) ) ){
							urlBuilder = new StringBuilder(Utils.getSingleValueAppConfig( "B2C_REQUEST_URL_HOME" ));							
							emailStr = AppConstants.RENEWAL_HOME_CONTENT;
						}
						else{
							urlBuilder = new StringBuilder(Utils.getSingleValueAppConfig( "B2C_REQUEST_URL_TRAVEL" ));							
							emailStr = AppConstants.RENEWAL_TRAVEL_CONTENT;
						}
						
						if (!Utils.isEmpty( urlBuilder )) {
							
							//
							List<String> resultList = new ArrayList();
							String emailContent = "";
							if (lob.equals(Utils.getSingleValueAppConfig(com.Constant.CONST_HOME_POL_TYPE))) {
								if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
									resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_CTP_EXPIRY_REMINDER);
									emailContent = getEmailTemplateResult(emailContent, resultList);
									logger.debug("HOME 45 days policy expiry emailContent Direct : " + emailContent);
								} else {
									resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_CTP_EXPIRY_REMINDER);
									emailContent = getEmailTemplateResult(emailContent, resultList);
									logger.debug("HOME 45 days policy expiry emailContent Partner: " + emailContent);
								}
							} else if (lob.equals(Utils.getSingleValueAppConfig("TRAVEL_POL_TYPE"))) {
								if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
									resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_TRAVEL_CTP_EXPIRY_REMINDER);
									emailContent = getEmailTemplateResult(emailContent, resultList);
									logger.debug("TRAVEL 45 days policy expiry emailContent Direct : " + emailContent);
								} else {
									resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_TRAVEL_CTP_EXPIRY_REMINDER);
									emailContent = getEmailTemplateResult(emailContent, resultList);
									logger.debug("TRAVEL 45 days policy expiry emailContent Partner: " + emailContent);
								}
							}
							
							String tag = Utils.getSingleValueAppConfig( "RENEWAL_NOTICE_CLICK_HERE_TAG" );							
							//Oman D2C Email template change - Start
							if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
									&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
								String pmmId = AppConstants.OMAN_D2C_TRAVEL;
								List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.RENEWAL_NOTICE_OMAN_D2C, pmmId);
								if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
									emailContent =  resultSet.get( 0 );
									logger.debug("emailContent for renewal notice is coming from DB for Oman D2C: "+emailContent);
								}
							}else{
								System.out.println("No Template");
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
								urlBuilder.append( Utils.getSingleValueAppConfig( "B2C_FETCH_QUOTE_HOME_RENEWAL_METHOD" ) ).append( "?renQuote=" +encryptedQuoteNo);
								
							} else {
								urlBuilder.append( Utils.getSingleValueAppConfig( "B2C_FETCH_QUOTE_TRAVEL_RENEWAL_METHOD" ) ).append( "?renQuote=" +encryptedQuoteNo );
							}
							tag = tag.replace( Utils.getSingleValueAppConfig( "B2C_TAG_IDF_URL" ), urlBuilder.toString() );							
							
							emailContent = emailContent.replace(AppConstants.RENEWAL_NOTICE_EMAIL_INSURED_NAME, 
									WordUtils.capitalizeFully( renNotice.getGeneralInfo().getInsured().getFirstName() + " " + renNotice.getGeneralInfo().getInsured().getLastName() ));
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
							mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );
							mailVO.setMailContent( new StringBuilder(emailContent) );
						}	
						
						//fileNames[0]=Utils.getSingleValueAppConfig("QUOTE_DOC_RPTDESIGN_RENEWAL_LOC") + quoteNo + "-Renewal quote.pdf";
						//fileNames[0] = Utils.getSingleValueAppConfig("QUOTE_DOC_PROPOSAL_LOC") + quoteNo + "-Quote.pdf";	
						//added Quote document file as Renewal Email to have quote document as attachment instead of the renewal notice
						fileNames[0] = Utils.getSingleValueAppConfig("QUOTE_DOC_PROPOSAL_LOC") + quoteNo + "-Renewal quote.pdf";						
						
						mailVO.setFileNames(fileNames);
						mailVO.setDocParameter(reportParams);
						logger.debug("*******file name = " + fileNames[0]);
						// Create the document
						boolean docSuccess = true;
						try {
							mailVO = (MailVO) docCreator.invokeMethod("createDocument", mailVO);
							logger.debug("*********createDocument called**********");
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
								logger.debug("calling sendMail");
								//mailVO = (MailVO) mailer.invokeMethod("sendMail", mailVO);								
								mailVO = (MailVO) mailer.invokeMethod("sendEmailWithImage",mailVO, "RENEWAL_NOTICE");
																
								sendStatus = 1;					
								logger.debug("sendMail called");
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
							String transactionId = renNotice.getPolicyId() + "|"+renNotice.getEndtId() + "|"+renNotice.getCommonVO().getDocCode();
							tTrnMail.setMailType(Utils.getSingleValueAppConfig("RENEWAL_NOTICE"));
							tTrnMail.setRecipientMailId(toAddress);
							tTrnMail.setStatus((byte) sendStatus);
							tTrnMail.setTransactionId(transactionId);
							tTrnMail.setSentDate(new Timestamp( System.currentTimeMillis()));
							tTrnMail.setMalClassCode(classCode.shortValue());
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
		}
	}
	
	/**
	 * This method will populate the MailVO object for triggering email
	 * CR:123969  
	 * Modified by Vishwa to make it more generic and remove physical template 
	 * @param mailVO
	 * @param policyDataVO
	 */
	private void populateMailVO(MailVO mailVO, PolicyDataVO policyDataVO, boolean isTwentyFourHrsInd) {
		
		logger.debug("Going to populate MailVO for convert to policy reminder mail");
		String emailContent = null;
		String clickHereLink = null;
		String schedulerUrl = null;
		
		if (!Utils.isEmpty(policyDataVO.getGeneralInfo())) {
			mailVO.setSignature( "RSA" );
			List resultList = new ArrayList();
			if (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
				if (isTwentyFourHrsInd) {
					if(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( SvcConstants.DIST_CHANNEL_BROKER ))
					{
						int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						int classCode = policyDataVO.getPolicyClassCode();
						if(!Utils.isEmpty(brokerCode))
						{
							logger.info(" Travel Twenty four Hours for Partner");
							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.BROKER_24H, brokerCode, classCode);
							if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
								emailContent =  resultSet.get( 0 );
								logger.debug("emailContent 24h for Travel Partner "+emailContent);
							}	
						}
					}
					else
					{
						//emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_C2P_REMINDER_TEMPLATE);
						/* Ticket 165419 */
						if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
							resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_TRAVEL_EMAIL_TEMPLATE_ONLINE);
							emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
							logger.debug("TRAVEL 24hrs Reminder Direct emailContent : " + emailContent);
						} else {
							resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
									policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
							emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
							logger.debug("TRAVEL 24hrs Reminder Direct--Mirror Site emailContent : " + emailContent);
						}
					}
				} else {
						if(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( SvcConstants.DIST_CHANNEL_BROKER ))
						{
							int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
							int classCode = policyDataVO.getPolicyClassCode();
							if(!Utils.isEmpty(brokerCode))
							{
								logger.info(" Travel fifteen days for Partner");
								List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.BROKER_15D, brokerCode, classCode);
								if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 )
								{
									emailContent =  resultSet.get( 0 );
									logger.debug("emailContent 15d for Travel Partner "+emailContent);
								}	
							}
					}
					else
					{	
						//Oman D2C Email template change - Start
						if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
								&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
							logger.info(" TRAVEL 3 DAYS REMINDER for Oman D2C");
							String pmmId = AppConstants.OMAN_D2C_TRAVEL;

							List<String> resultSet = DAOUtils
									.getSqlResultForPasString(
											QueryConstants.REMINDER_15DAYS_OMAN_D2C, pmmId);
							if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
								emailContent = resultSet.get(0);
								logger.debug("emailContent 3 DAYS REMINDER Oman D2C Travel "
										+ emailContent);
							}
							emailContent = emailContent.replace("TRAVELPAGE", Utils.getSingleValueAppConfig("D2C_OMAN_TRAVEL_STEP1"));
						}else{
							//emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_C2P_REMINDER_15DAYS_TEMPLATE);	
							/* Ticket 165419 */
							if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
								resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_TRAVEL_EMAIL_TEMPLATE_ONLINE);
								emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
								logger.debug("TRAVEL 15 days Reminder Direct emailContent : " + emailContent);
							} else {
								resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
										policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
								emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
								logger.debug("TRAVEL 15 days Reminder Direct Campaign emailContent : " + emailContent);
							}	
						}
						//Oman D2C Email template change - End
					}
				}
				mailVO.setSubjectText(AppConstants.B2C_TRAVEL_QUOTE_EMAIL_SUBJECT);
			} else {
				if (isTwentyFourHrsInd) {
					if(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( SvcConstants.DIST_CHANNEL_BROKER ))
					{
						int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						int classCode = policyDataVO.getPolicyClassCode();
						if(!Utils.isEmpty(brokerCode))
						{
							logger.info(" Home Twenty Four Hours for Partner");
							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.BROKER_24H, brokerCode, classCode);
							if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
								emailContent =  resultSet.get( 0 );
								logger.debug("emailContent 24h for Home Partner "+emailContent);
							}
						}
					}
					else{
					//emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_C2P_REMINDER_TEMPLATE);
						/* Ticket 165419 */
						if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
							resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_EMAIL_TEMPLATE_ONLINE);
							emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
							logger.debug("HOME 24hrs Reminder Direct emailContent : " + emailContent);
						} else {
							resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
									policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
							emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
							logger.debug("HOME 24hrs Reminder Direct Campaign emailContent : " + emailContent);
						}
					}
				} else {
					if(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( SvcConstants.DIST_CHANNEL_BROKER ))
					{
						int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
						int classCode = policyDataVO.getPolicyClassCode();
						if(!Utils.isEmpty(brokerCode))
						{
							logger.info(" Home Fifteen Hours for Partner");
							List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.BROKER_15D, brokerCode, classCode);
							if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
								emailContent =  resultSet.get( 0 );
								logger.debug("emailContent 15h for Home Partner "+emailContent);
							}
						}
					}
					else{
						//emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_C2P_REMINDER_15DAYS_TEMPLATE);
						/* Ticket 165419 */
						if (Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
							resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_EMAIL_TEMPLATE_ONLINE);
							emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
							logger.debug("HOME 15 days Reminder Direct emailContent : " + emailContent);
						} else {
							resultList = DAOUtils.getSqlResultForPasString(QueryConstants.B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT,
									policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
							emailContent = CommonHandler.returnQueryResultList(emailContent, resultList,policyDataVO,null,isTwentyFourHrsInd);
							logger.debug("HOME 15 days Reminder Direct Campaign emailContent : " + emailContent);
						}
					}
				}
				mailVO.setSubjectText(AppConstants.B2C_HOME_QUOTE_EMAIL_SUBJECT);
				if( Utils.isEmpty( policyDataVO.getScheme().getPolicyType() ) ){
					policyDataVO.getScheme().setPolicyType( AppConstants.HOME_POLICY_TYPE.toString() );
					policyDataVO.getScheme().setPolicyCode( AppConstants.HOME_POLICY_TYPE );
				}
				if (!Utils.isEmpty(policyDataVO.getPremiumVO()) && !Utils.isEmpty(policyDataVO.getPremiumVO().getPremiumAmt())) {
					Double onlineDiscPerc = 0.0;
					if(AppConstants.DIST_CHANNEL_DIRECT_WEB.equals(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().toString()))
						{
							onlineDiscPerc = Double.valueOf( Utils.getSingleValueAppConfig( "HOME_POLICY_LEVEL_DISCOUNT" ) );
						}
					else 
					{
						if(!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount()))
						{
							onlineDiscPerc = Double.valueOf( policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount() );
						}
					}
					double minPrmToApply = ( (BigDecimal) prmSvc.invokeMethod( SvcConstants.GET_MIN_PRM_TO_APPLY_HOME, (HomeInsuranceVO)policyDataVO ) ).doubleValue();
					if( minPrmToApply > 0 ){
						policyDataVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrmToApply ) );
						if( !( policyDataVO.getCommonVO().getIsQuote() && policyDataVO.getPremiumVO().getPremiumAmt() == 0 ) ){
							//policyDataVO.getPremiumVO().setPremiumAmt( ((100.00 + onlineDisc )/100.00) *( policyDataVO.getPremiumVO().getPremiumAmt() + minPrmToApply ) );
							policyDataVO.getPremiumVO().setPremiumAmt( policyDataVO.getPremiumVO().getPremiumAmt() + minPrmToApply );
						}
					}
					policyDataVO.getPremiumVO().setPremiumAmt( Double.parseDouble( Currency.getUnformttedScaledCurrency( policyDataVO.getPremiumVO().getPremiumAmt() , policyDataVO.getCommonVO().getLob().toString() ) ) );
					double discAmt = Double.parseDouble( Currency.getUnformttedScaledCurrency( ( policyDataVO.getPremiumVO().getPremiumAmt() * onlineDiscPerc )/100 , policyDataVO.getCommonVO().getLob().toString() ) );
					if(emailContent != null){
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_PREMIUM_IDENTIFIER, AppUtils.getFormattedNumberWithDecimals( policyDataVO.getPremiumVO().getPremiumAmt() + discAmt  , policyDataVO.getCommonVO().getLob().toString() ));
					}
				}
			}
			if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured()) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getFirstName() ) && 
					!Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getLastName() ) && !AppConstants.NO_NAME_STRING.equalsIgnoreCase( policyDataVO.getGeneralInfo().getInsured().getFirstName() ) && 
					!AppConstants.NO_NAME_STRING.equalsIgnoreCase( policyDataVO.getGeneralInfo().getInsured().getLastName() ) ) {
				if(emailContent != null){
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER, WordUtils.capitalizeFully( policyDataVO.getGeneralInfo().getInsured().getFirstName() + " " + policyDataVO.getGeneralInfo().getInsured().getLastName() ) );
				}
			}else if ( emailContent != null && !Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured()) && !Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getName()) && 
					!AppConstants.NO_NAME_STRING.equalsIgnoreCase( policyDataVO.getGeneralInfo().getInsured().getName())) {
				//if(emailContent != null){ // soanr blocker fixed
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER, WordUtils.capitalizeFully( policyDataVO.getGeneralInfo().getInsured().getName() ) );
				//}
			} else {
				if(emailContent != null){
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER, AppConstants.B2C_DEFAULT_CUST_NAME);
				}
			}
			if (!Utils.isEmpty(policyDataVO.getQuoteNo())) {
				if(emailContent != null){
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_QUOTE_NO_IDENTIFIER, String.valueOf(policyDataVO.getQuoteNo()));
				}
			}
			
			if(Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName()))
			{
				if(emailContent != null){
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER,Utils.getSingleValueAppConfig("DEFAULT_RSA_CALLCENTER_NUMBER"));
					emailContent = emailContent.replace("BROKER_SIGNATURE", " ");
				}
				if(policyDataVO.getCommonVO().getLob().equals(LOB.HOME))
				{
					emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1, Utils.getSingleValueAppConfig("BULLET_POLNT_1_Direct"));
					emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2, Utils.getSingleValueAppConfig("BULLET_POLNT_2_Direct"));
				}
				if(emailContent != null){
					emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, Utils.getSingleValueAppConfig("BULLET_POINT_DISC_DIRECT"));
					emailContent = emailContent.replace("DISC_PERCENT", Utils.getSingleValueAppConfig("BULLET_POINT_DISC_PERCENT_DIRECT"));
				}
				mailVO.setDirect( true );
				mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
				schedulerUrl = LOB.HOME.equals( policyDataVO.getCommonVO().getLob() ) ? Utils.getSingleValueAppConfig( "B2C_SCHEDULER_URL_HOME" ) : Utils.getSingleValueAppConfig( "B2C_SCHEDULER_URL_TRAVEL" );
			}
			else
			{
				mailVO.setFromAddress(policyDataVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
				if(!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getCcEmailId()))
				{
					String[] ccString = policyDataVO.getGeneralInfo().getSourceOfBus().getCcEmailId().split(";");
					mailVO.setCcAddress(ccString);
				}
				mailVO.setReplyToEmailID(policyDataVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
				schedulerUrl = LOB.HOME.equals( policyDataVO.getCommonVO().getLob() ) ? Utils.getSingleValueAppConfig( "B2C_SCHEDULER_URL_PARTNER_HOME" ) : Utils.getSingleValueAppConfig( "B2C_SCHEDULER_URL_PARTNER_TRAVEL" );
				String[] urlArray = schedulerUrl.split("/");
				int len = urlArray[urlArray.length-1].length();
				String applicationPath = schedulerUrl.substring(0, schedulerUrl.length() - len);
				schedulerUrl = applicationPath + policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName() + "/" + urlArray[urlArray.length-1];
				if(emailContent != null){
					emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER, policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());		
				}
				if(policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( SvcConstants.DIST_CHANNEL_BROKER ))
				{
				int brokerCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
				
					if(!Utils.isEmpty(brokerCode)){
						if(emailContent != null){
							emailContent = emailContent.replace(com.Constant.CONST_CALLCENTER_NUMBER, policyDataVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1, Utils.getSingleValueAppConfig("BULLET_POLNT_1_BROKER"));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2, Utils.getSingleValueAppConfig("BULLET_POLNT_2_BROKER"));
						}
						//emailContent = emailContent.replace("BROKER_SIGNATURE", Utils.getSingleValueAppConfig("EMAIL_SIGNATURE_"+brokerCode));
						mailVO.setDirect( false );
					}
				
				/*else{
						List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.BROKER_ADDRESS, brokerCode );
						if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
							StringBuffer brokerAddress = new StringBuffer();
							brokerAddress.append( "<br/>Yours sincerely, <br/><br/>" );
							brokerAddress.append( "The Customer Service Team <br/><br/>" );
							for( Object object : resultSet.get( 0 ) ){
								if( !Utils.isEmpty( object ) ){
									brokerAddress.append( String.valueOf( object ) ).append( "<br/>" );
								}
							}
							if(policyDataVO.getCommonVO().getLob().equals(LOB.HOME))
							{
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1, Utils.getSingleValueAppConfig("BULLET_POLNT_1_BROKER"));
								emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2, Utils.getSingleValueAppConfig("BULLET_POLNT_2_BROKER"));
							}
							emailContent = emailContent.replace("BROKER_SIGNATURE", brokerAddress.toString());
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC,"");
							mailVO.setDirect( false );
						}
					}*/
				}
				else
				{
					if(policyDataVO.getCommonVO().getLob().equals(LOB.HOME))
					{
						if(emailContent != null){
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_1, Utils.getSingleValueAppConfig("BULLET_POLNT_1_Direct"));
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POLNT_2, Utils.getSingleValueAppConfig("BULLET_POLNT_2_Direct"));
						}
					}
					if(!Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount())
							&& policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount().intValue()!=0)
					{
						if(emailContent != null){
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC, Utils.getSingleValueAppConfig("BULLET_POINT_DISC_DIRECT"));
							emailContent = emailContent.replace("DISC_PERCENT", Integer.valueOf(Math.abs(policyDataVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount().intValue())).toString());
						}
					}
					else
					{
						if(emailContent != null){
							emailContent = emailContent.replace(com.Constant.CONST_BULLET_POINT_DISC,"");
						}
					}
					
					if(emailContent != null){
						emailContent = emailContent.replace("BROKER_SIGNATURE", " ");
					}
					mailVO.setDirect( true );
				}
			}
			
			clickHereLink = AppUtils.constructClickHereURL(policyDataVO.getCommonVO().getQuoteNo(),
					policyDataVO.getGeneralInfo().getInsured().getEmailId(), schedulerUrl ,policyDataVO.getCommonVO().getLob(),null);
			if(emailContent != null){
				emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereLink);
			}
			mailVO.setToAddress(policyDataVO.getGeneralInfo().getInsured().getEmailId());
			if(emailContent != null)		/* Added if condition for emailContent null check - sonar violation fix */
			mailVO.setMailContent(new StringBuilder(emailContent));
			//Oman D2C Email template change - Start
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) 
					&& Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
				mailVO.setCcAddress(new String[]{AppConstants.B2C_REMINDER_CC_EMAILID});
				
			}
			
			mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) ); //Setting the current time stamp
			mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );
			
		} else {
			return;
		}
	}
	
	/* Ticket 165419 */
	private String getEmailTemplateResult(String emailContent, List<String> resultList) {
		try {
			if (!Utils.isEmpty(resultList) && resultList.size() > 0) {
				emailContent = resultList.get(0);
			} else if (Utils.isEmpty(emailContent)) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.debug("Error occured while reading email template " + e.getMessage());
			logger.error(e.getStackTrace().toString());
		}

		return emailContent;
	}

	public ConvertToPolicyReminderCmnSvc getReminderSvc() {
		return reminderSvc;
	}

	public synchronized static void setReminderSvc(ConvertToPolicyReminderCmnSvc reminderSvc) {
		ConvertToPolicyReminderScheduler.reminderSvc = reminderSvc;
	}

	/**
	 * @return the prmSvc
	 */
	public PremiumSaveCommonSvc getPrmSvc(){
		return prmSvc;
	}

	/**
	 * @param prmSvc the prmSvc to set
	 */
	public void setPrmSvc( PremiumSaveCommonSvc prmSvc ){
		//Sonar Fix for Instance methods should not write to static fields
		//this.prmSvc = prmSvc;
	}

	

}
