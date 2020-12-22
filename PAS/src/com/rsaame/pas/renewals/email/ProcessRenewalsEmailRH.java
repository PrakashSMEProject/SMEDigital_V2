package com.rsaame.pas.renewals.email;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.RenewalResultsVO;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.WordUtils;

public class ProcessRenewalsEmailRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger(ProcessRenewalsEmailRH.class);
	private static final String EMAIL_NOTIFICATION_JSP = "/jsp/quote/emailBrokerNotification.jsp";
	/*Commented as check for status is removed, here user is not required to have access to any LOB, 
	 * based on new role REN_BRK_EMAIL_CC mail email will be picked to include in CC list for those user*/
	 /* public static final String LEVEL3_RSA_USER = "SELECT user_email_id FROM T_MAS_USER WHERE STATUS_ID = 1" +
			" AND user_id IN (SELECT user_id_fk FROM t_trn_user_role_map WHERE role_fk = 'REN_BRK_EMAIL_CC' )";*/
	public static final String LEVEL3_RSA_USER = "SELECT user_email_id FROM T_MAS_USER WHERE " +
			" user_id IN (SELECT user_id_fk FROM t_trn_user_role_map WHERE role_fk = 'REN_BRK_EMAIL_CC' )";
	
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		Response response = new Response();
		// get all selected records
		Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy").create();
		RenewalResultsVO[] emailNoticeList = gson.fromJson(request.getParameter("selectedRows"), RenewalResultsVO[].class);
		String emailTemplateType = request.getParameter("emailTemplateType");
		String transBrokerName = request.getParameter("transBrokerName"); 
		/*String transLOB = request.getParameter(com.Constant.CONST_TRANSLOB);*/
		String transBranch = request.getParameter("transBranch");
		String transClazz = request.getParameter(com.Constant.CONST_TRANSCLAZZ);
		
		String transPolicyNo = request.getParameter("transPolicyNo");
		String ccAddress="";
		
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		String fromAddress = "";
		List<String> emailErrorList = new ArrayList<String>();
		Redirection redirection = null;
		String operation = request.getParameter( "operation" );
		//PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
		PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
		String userProfileName = !Utils.isEmpty(userProfile.getRsaUser().getUserName())?userProfile.getRsaUser().getUserName():"";
		String userMobileNum =userProfile.getRsaUser().getMobileNumber();
		
		String toAddress = "";
		/*String fileNames[]=new String[1];*/
		int emailCount = 0;
		String policyNum ="";
		if(!Utils.isEmpty(userProfile.getRsaUser().getEmail())){
			ccAddress =  userProfile.getRsaUser().getEmail();
		}
		if(!Utils.isEmpty( operation ) && operation.equalsIgnoreCase( "sendMail" )){
			fromAddress = request.getParameter("userEmailId");
			
			toAddress = request.getParameter( "emailTo" );
			String toAddressArray[] = toAddress.split(",");
			ccAddress = request.getParameter( "emailCC" );
			String subject = request.getParameter( "emailSubject" );
			String content = request.getParameter( "content" );
			//AdventId:142505; Modified to implement PTS Enhancement
			String mailHeader= Utils.getSingleValueAppConfig("EMAIL_HEADER_"+request.getParameter(com.Constant.CONST_TEMPLATETYPE));
			content = content.replace("<div class=\"MsoNoSpacing\"></div>", mailHeader);
			/*String transBroker =request.getParameter ("transBroker");*/
			StringBuilder mailContent = new StringBuilder( content);
			MailVO mailvo=new MailVO();
			
			//fileNames[ 0 ] = Utils.getSingleValueAppConfig( "QUOTE_PRINT_RPTDESIGN_RENEWAL_LOC" ) +brokerOrInSuredName+".xls";
			if( !Utils.isEmpty( ccAddress ) ){
				//ccAddress += "," + userProfile.getRsaUser().getEmail();
				String[] ccAddressess = ccAddress.split( "," );
				mailvo.setCcAddress( ccAddressess );
			}
			
				if(!Utils.isEmpty(toAddress)){
				System.out.println("toAddress "+toAddress);
				mailvo.setToAddresses(toAddressArray);
				mailvo.setFromAddress(fromAddress);
				mailvo.setSubjectText( subject );
				mailvo.setMailType( SvcConstants.MAIL_TYPE_HTML );
				mailvo.setDirect(true);
				mailvo.setMailContent( mailContent );
				mailvo.setPolicyType("50");
				//mailvo.setFileNames(fileNames);
				//mailvo.setDocParameter(reportParams);
				//System.out.println("file name "+fileNames[0]);
				// Create the document
				boolean docSuccess = true;
				try{
					mailvo = createDocument(mailvo,request,userProfile);
					System.out.println("createDocument called");
				} catch(Exception e){
					e.printStackTrace();
					
					emailErrorList.add(com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
					docSuccess = false;
				}

				if(docSuccess){
					if(!Utils.isEmpty(mailvo.getDocCreationStatus())){
						if(mailvo.getDocCreationStatus().equals("failure")){
							emailErrorList.add(com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
							docSuccess = false;
						}	
					}else{
						emailErrorList.add(com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
						docSuccess = false;
					}
				}	
				if(docSuccess){
				try{	
					System.out.println("calling sendMail");
					mailvo=(MailVO)mailer.invokeMethod( "sendMailImage", mailvo, request.getParameter(com.Constant.CONST_TEMPLATETYPE));
					System.out.println("sendMail called");
				} catch(Exception e)
				{
					e.printStackTrace();
					emailErrorList.add(": Error while sending email");
				}
					if(!Utils.isEmpty(mailvo.getMailStatus())){
						if(mailvo.getMailStatus().equals("fail")){
							emailErrorList.add("Email id : "+toAddress+" Error : "+mailvo.getError());
							response.setData("RenewMailFailure");
						}else{
							emailCount=1;
							response.setData("RenewMailSuccess");
						}
					}
				}
			}
		if(emailCount==0){
			emailErrorList.add("None of the selected policies have the email id");
		}
		if(!Utils.isEmpty( emailErrorList )){
			response.setData( emailErrorList );
		}
	}else{
		String transFromDate = request.getParameter("transTransactionFrom");
		String transToDate = request.getParameter("transTransactionTo");
		String transInsuredName = request.getParameter(com.Constant.CONST_TRANSINSUREDNAME);
		String transStatus[] = null!= request.getParameterValues(com.Constant.CONST_TRANSSTATUS)?request.getParameterValues(com.Constant.CONST_TRANSSTATUS):null;
		String transLOB = request.getParameter(com.Constant.CONST_TRANSLOB);
		
		if((com.Constant.CONST_NO_9999999).equals(transBrokerName)  || Utils.isEmpty(transBrokerName)){
			if(!Utils.isEmpty(userProfile.getRsaUser().getEmail())){
				fromAddress = userProfile.getRsaUser().getEmail();
			}else{
				BusinessException businessExcp=new BusinessException( "mail.user.noemail", null, "User does not have mail id updated in user profile" );
				logger.error("PASEmailUtil:FromAddress Error:User does not have mail id updated in user profile");
				//data="Quote emailed successfully";
				throw businessExcp;
			}
			
		}else{
			List<String> resultSet = DAOUtils.getSqlResultForPasString("select usr_tl_email_id from t_mas_user where user_id="
									+userProfile.getRsaUser().getUserId());
			if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
				for( String result : resultSet ){
					if(null != result)
						fromAddress = result;
				}
			}
			if(Utils.isEmpty(fromAddress)){
				BusinessException businessExcp=new BusinessException( "mail.user.notlemail", null, "From Email Id is not available." );
				logger.error("PASEmailUtil:FromAddress Error:User does not have TL mail id updated in user profile");
				//data="Quote emailed successfully";
				throw businessExcp;
			}
		}

		String brokerOrInSuredName ="";
		String dispDate="";
		String appendPolTosubject="";
		String polLinkingId = "";
		String endtId ="";
		String policyId =  "";
		String polStartDate = "";
		boolean isDateRangeSelected= true;
		String renQuoteNo = "";
		String brAccKeyManager = "";
		String brAccTradingManager = "";
		
		for( RenewalResultsVO renNotice : emailNoticeList ){
			// toAddress = renNotice.getEmailId();
		   /*  Long quoteNo = renNotice.getRenQuoteNo();
		     Long polNo = renNotice.getPolicyNo();*/
		     policyNum = policyNum +( policyNum.equals("")?"":"|")+renNotice.getPolicyNo();
		     if(Utils.isEmpty(transFromDate) && Utils.isEmpty(dispDate)){
		    	 dispDate = renNotice.getPolEffectiveDate();
		    	 isDateRangeSelected= false;
		     }
		     if(!Utils.isEmpty(transBrokerName) && !(com.Constant.CONST_NO_9999999).equals(transBrokerName) && Utils.isEmpty(brokerOrInSuredName)){
		    	 if(!Utils.isEmpty(renNotice.getBrEmailId()))
		    		 toAddress= toAddress+ (toAddress.equals("")?"":",")+ (null == renNotice.getBrEmailId() ?"":renNotice.getBrEmailId()) ;
		    		 brokerOrInSuredName = renNotice.getBrokerName();
		    	 if(!Utils.isEmpty(renNotice.getBrAccountExeEmail())){
				     ccAddress = ccAddress+ (ccAddress.equals("")?"":",")+( null == renNotice.getBrAccountExeEmail()? "":renNotice.getBrAccountExeEmail());
				     }
		    	 List<String> resultSet = DAOUtils.getSqlResultForPasString(LEVEL3_RSA_USER);
		 		 if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
		 			for( String result : resultSet ){
		 				if(null != result)
		 					ccAddress = ccAddress+(ccAddress.equals("")?"":",")+ result;
		 			}
		 		 }
		 		 if(Utils.isEmpty(brAccKeyManager)){
		 			brAccKeyManager = WordUtils.capitalize(renNotice.getBrAccountKeyManagerName().split("-")[0])+"-"+renNotice.getBrAccountKeyManagerNum();
		 		 }
		 		 if(Utils.isEmpty(brAccTradingManager)){
		 			brAccTradingManager = WordUtils.capitalize(renNotice.getBrRemarks());
		 		 }
		 		 
		     }else if((com.Constant.CONST_NO_9999999).equals(transBrokerName) || Utils.isEmpty(transBrokerName)){
		    	 if(!Utils.isEmpty(renNotice.getEmailId()))
		    		 toAddress= toAddress+ (toAddress.equals("")?"":",")+ (null == renNotice.getEmailId() ?"":renNotice.getEmailId()) ;
		    	 if(Utils.isEmpty(brokerOrInSuredName) && emailNoticeList.length == 1){
		    		 brokerOrInSuredName = renNotice.getInsuredName();
		    		 appendPolTosubject= "-Policy no-"+policyNum;
		    		 polLinkingId = renNotice.getPolLinkingId().toString();
		    		 endtId = renNotice.getEndtId().toString();
		    		 policyId = renNotice.getPolicyId().toString();
		    		 polStartDate = renNotice.getPolValidityStartDate().toString();
		    		 renQuoteNo = renNotice.getRenQuoteNo().toString();
		    	 }
		    	 
		     }
		     
		     if(Utils.isEmpty(transLOB)){
		    	 transLOB = renNotice.getPolicyType();
		     }
		}
		if((com.Constant.CONST_NO_9999999).equals(transBrokerName) || Utils.isEmpty(transBrokerName)){
	    		ccAddress = ccAddress +(ccAddress.equals("")?"":",") +Utils.getSingleValueAppConfig("DEFAULT_RSA_USER_EMAILID");
	     }
		Date parsetransTransactionFrom = new Date();
		Date parsetransTransactionTo = new Date();
		SimpleDateFormat sdf;
		if(isDateRangeSelected){
			 sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		else{
			 sdf = new SimpleDateFormat("dd/MM/yyyy");
		}
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			if(Utils.isEmpty( transFromDate )){
				parsetransTransactionFrom = sdf.parse(dispDate);
			}else{
				parsetransTransactionFrom = sdf.parse(transFromDate);
				transFromDate= dateFormat.format(parsetransTransactionFrom);
				parsetransTransactionTo = sdf.parse(transToDate);
				transToDate = dateFormat.format(parsetransTransactionTo);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");
	    //System.out.println("DAY "+simpleDateFormat1.format(transTransactionFrom).toUpperCase());
	    simpleDateFormat1 = new SimpleDateFormat("MMM");
	    String transFromEmailDispDate= simpleDateFormat1.format(parsetransTransactionFrom).toUpperCase();
	   // String subject = Utils.getSingleValueAppConfig(emailTemplateType+"_SUB")+'-'+transFromDate;
	    String transToEmailDispDate = simpleDateFormat1.format(parsetransTransactionTo).toUpperCase();

	    simpleDateFormat1 = new SimpleDateFormat("yyyy");
	    System.out.println("YEAR "+simpleDateFormat1.format(parsetransTransactionFrom).toUpperCase());
	    request.setAttribute( "transYear", simpleDateFormat1.format(parsetransTransactionFrom).toUpperCase());
	    transFromEmailDispDate+=" "+ simpleDateFormat1.format(parsetransTransactionFrom).toUpperCase();
	    transToEmailDispDate+=" "+ simpleDateFormat1.format(parsetransTransactionTo).toUpperCase();
	    String subject = Utils.getSingleValueAppConfig(emailTemplateType+"_SUB")+" "+transFromEmailDispDate;
	    if(!Utils.isEmpty( brokerOrInSuredName ) && !"DIRECT".equals(brokerOrInSuredName) ){
	    	subject = subject + "- "+brokerOrInSuredName+appendPolTosubject;
	    }
		request.setAttribute( "transBranch", transBranch);
		request.setAttribute( "transPolicyNo", transPolicyNo);
		request.setAttribute( com.Constant.CONST_TRANSINSUREDNAME, transInsuredName);
		request.setAttribute( com.Constant.CONST_TRANSLOB, transLOB);
		request.setAttribute( com.Constant.CONST_TRANSCLAZZ, transClazz);
		request.setAttribute( "transStatusList", Arrays.toString(transStatus));
		request.setAttribute( "emailLocCode", Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		request.setAttribute( "template", Utils.getSingleValueAppConfig(emailTemplateType));
		request.setAttribute( "toAddress", toAddress);
		request.setAttribute( "subject", subject);
		request.setAttribute( "ccAddress", ccAddress );
		request.setAttribute( "transBroker", transBrokerName );
		request.setAttribute( "transToDate", transToDate );
		request.setAttribute( "transFromDate", transFromDate );
		request.setAttribute( "transFromEmailDispDate", transFromEmailDispDate );
		request.setAttribute( "transToEmailDispDate", transToEmailDispDate );
		request.setAttribute( "userProfileName", WordUtils.capitalize(userProfileName) );
		request.setAttribute( "selPolicyNumbers", policyNum );
		request.setAttribute( com.Constant.CONST_BROKERORINSUREDNAME, brokerOrInSuredName );
		request.setAttribute( "userMobileNum", userMobileNum );
		request.setAttribute( "userEmailId", fromAddress );
		String brokerPath ="/direct";
		if(!Utils.isEmpty(transBrokerName)	&& !(com.Constant.CONST_NO_9999999).equals(transBrokerName) && !("999999").equals(transBrokerName))
			brokerPath="/broker";
		request.setAttribute( "brokerPath", brokerPath );
		
		// AdventId:142505; Parameters Required to be passed for Proposal form
		request.setAttribute( com.Constant.CONST_POLLINKINGID, polLinkingId);
		request.setAttribute( "endtId", endtId);
		request.setAttribute( "policyId", policyId);
		request.setAttribute("displayProposalCheckbox", request.getParameter("displayProposalCheckbox"));
		request.setAttribute(com.Constant.CONST_TEMPLATETYPE, emailTemplateType);
		request.setAttribute(com.Constant.CONST_POLVALSTARTDATE, polStartDate);
		request.setAttribute("renQuote", renQuoteNo);
		request.setAttribute("brAccKeyManager", brAccKeyManager);
		request.setAttribute("brAccTradingManager", brAccTradingManager);
		redirection = new Redirection( EMAIL_NOTIFICATION_JSP, Type.TO_JSP );
		response.setRedirection( redirection );
	
	}
		return response;
	}	
	
	/**
	 * @param mailvo
	 * @param request
	 * @param userProfile
	 * @return
	 * @throws Exception
	 * AdventId:142505; Generate the documents to be attached to the mail
	 */

	public MailVO createDocument(MailVO mailvo, HttpServletRequest request,UserProfile userProfile) throws Exception
	{
		String isProposalFormAttachementReq = request.getParameter("flagToAttachProposalForm");
		PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
		
		mailvo = createReportParamForXlsDoc(mailvo,request,userProfile);
		try{
			mailvo=(MailVO)docCreator.invokeMethod("createRenewalDocument", mailvo);
			String fileName[] = mailvo.getFileNames();
			//1: Indicate user has checked in front end to attached proposal form
			if(("on").equals(isProposalFormAttachementReq) && !( mailvo.getDocCreationStatus().equals("") 
					|| mailvo.getDocCreationStatus().equals("failure"))){
				mailvo = createReportParamForPDFDoc(mailvo,request,userProfile);
				mailvo=(MailVO)docCreator.invokeMethod("createDocument", mailvo);
				//fileName[fileName.length] = mailvo.getf
				mailvo.setFileNames((String[]) ArrayUtils.addAll(fileName, mailvo.getFileNames()));
			}
		}catch(Exception e){
			throw e;
		}
		
		return mailvo;
	}
	
	/**
	 * @param mailvo
	 * @param request
	 * @param userProfile
	 * @return mailvo
	 * AdventId:142505; Construct Report Param for the ProposalForm
	 */
	
	public MailVO createReportParamForPDFDoc(MailVO mailvo, HttpServletRequest request,UserProfile userProfile){
		
		HashMap<String, String> reportParams = new HashMap<String, String>();
		reportParams.put("endoresementId", request.getParameter("endtID"));
		String fileNames[] = new String[1];
		String LOB;
		String d= request.getParameter(com.Constant.CONST_POLVALSTARTDATE);
		reportParams.put(com.Constant.CONST_POLVALSTARTDATE,
				d);
		reportParams.put("language", "E");
		reportParams.put("locationCode",
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		if (request.getParameter("policyType").equals("50")) {
			reportParams.put(com.Constant.CONST_POLLINKINGID,
					request.getParameter(com.Constant.CONST_POLLINKINGID));
			reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE,
					ReportTemplateSet._SBS.toString());
		} else {
			reportParams.put("policyId", request.getParameter(com.Constant.CONST_POLLINKINGID));
			reportParams.put(com.Constant.CONST_REPORTTEMPLATESTYPE,
					ReportTemplateSet._WC.toString());
			LOB = "WC";
			
		}
		fileNames[0] = Utils.getSingleValueAppConfig("QUOTE_DOC_PROPOSAL_LOC") + request.getParameter("renQuote") + "-Renewal quote.pdf";
		mailvo.setFileNames(fileNames);
		mailvo.setDocParameter(reportParams);
		return mailvo;
	}
	
	/**
	 * @param mailvo
	 * @param request
	 * @param userProfile
	 * @return mailvo
	 * AdventId:142505; Set Report param to generate XLS with Quote Details 
	 */
	
	public MailVO createReportParamForXlsDoc(MailVO mailvo, HttpServletRequest request,UserProfile userProfile){
		
		String selPolicyNumbers = request.getParameter("selPolicyNumbers");
		String template = request.getParameter (com.Constant.CONST_TEMPLATETYPE);
		String brokerOrInSuredName = request.getParameter(com.Constant.CONST_BROKERORINSUREDNAME);
		if(Utils.isEmpty(request.getParameter(com.Constant.CONST_BROKERORINSUREDNAME)) && !Utils.isEmpty(template)){
			brokerOrInSuredName = template.replace("Template.jsp", "");
		}else if(Utils.isEmpty(request.getParameter(com.Constant.CONST_BROKERORINSUREDNAME))) {
			brokerOrInSuredName="Renewals";
		}
		if(!Utils.isEmpty(selPolicyNumbers)){
			selPolicyNumbers= selPolicyNumbers.replace('|', ',');
		}
		String transClazz = request.getParameter(com.Constant.CONST_TRANSCLAZZ);
		String transFromDate = request.getParameter("transTransactionFrom");
		String transToDate = request.getParameter("transTransactionTo");
		String transInsuredName = request.getParameter(com.Constant.CONST_TRANSINSUREDNAME);
		String transBroker =request.getParameter ("transBroker");
		String transStatus[] = null!= request.getParameterValues(com.Constant.CONST_TRANSSTATUS)?request.getParameterValues(com.Constant.CONST_TRANSSTATUS):null;
		String transLOB = request.getParameter(com.Constant.CONST_TRANSLOB);
		String fileNames[]=new String[1];
		fileNames[ 0 ] = Utils.getSingleValueAppConfig( "QUOTE_PRINT_RPTDESIGN_RENEWAL_LOC" ) +brokerOrInSuredName+".xls";
		if(Utils.isEmpty(transClazz)){
			transClazz="0";
		}
		//transInsuredName = transInsuredName.equals("")?null:transInsuredName;
		//transLOB = transLOB.equals("")?null:transLOB;
		HashMap<String, String> reportParams = new HashMap<String, String>();
		 reportParams.put("locationCode",  Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		 reportParams.put("fromDate",transFromDate);
		 reportParams.put("toDate", transToDate);
		 reportParams.put("policyNo", selPolicyNumbers);
		 reportParams.put("expiryDays", null);
		 reportParams.put("schemeCode", null);
		 reportParams.put("quotationNo", null);
		 reportParams.put("userId", userProfile.getRsaUser().getUserId().toString());
		 reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, "RenewalStatusReportEmail" );
		 reportParams.put("classCode", transClazz);
		 if(Utils.isEmpty(transInsuredName)){
			 reportParams.put("insuredName",  null);
		 }else{
			 reportParams.put("insuredName",  transInsuredName);
		 }
		 if(Utils.isEmpty(transBroker)){
			 reportParams.put("brokerCode",  null);
		 }else{
			 reportParams.put("brokerCode", transBroker);
		 }
		 if(Utils.isEmpty(transStatus)){
			 reportParams.put("status",  null);
		 }else{
			 reportParams.put("status", Arrays.toString(transStatus));
		 }
		 if(Utils.isEmpty(transLOB)){
			 reportParams.put("lob",  null);
		 }else{
			 reportParams.put("lob",  transLOB);
		 }
		mailvo.setFileNames(fileNames);
		mailvo.setDocParameter(reportParams);
			//System.out.println("file name "+fileNames[0]);
		 return mailvo;
	}

}
