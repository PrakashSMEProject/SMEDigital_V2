package com.rsaame.pas.renewals.ui;

import java.util.ArrayList;
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
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.RenewalResultsVO;

public class EmailRenewalNoticeRH implements IRequestHandler{
	private final static Logger logger = Logger.getLogger(EmailRenewalNoticeRH.class);
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		Response response = new Response();
		// get all selected records
		Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy").create();
		RenewalResultsVO[] emailNoticeList = gson.fromJson(request.getParameter("selectedRows"), RenewalResultsVO[].class);
		UserProfile userProfile = null;
		userProfile = AppUtils.getUserDetailsFromSession( request );
		String fromAddress = "";
		List<String> emailErrorList = new ArrayList<String>();
		PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
		PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
		if(!Utils.isEmpty(userProfile.getRsaUser().getEmail())){
			fromAddress = userProfile.getRsaUser().getEmail();
		}else{
			BusinessException businessExcp=new BusinessException( "mail.user.noemail", null, "User does not have mail id updated in user profile" );
			logger.error("PASEmailUtil:FromAddress Error:User does not have mail id updated in user profile");
			//data="Quote emailed successfully";
			throw businessExcp;
		}
		String toAddress = "";
		String fileNames[]=new String[1];
		int emailCount = 0;
		for( RenewalResultsVO renNotice : emailNoticeList ){
      
			 toAddress = renNotice.getEmailId();
		     Long quoteNo = renNotice.getRenQuoteNo();
		     Long polNo = renNotice.getPolicyNo();
		     
		    HashMap <String,String>reportParams=new HashMap <String,String>(); 
			 reportParams.put("polLinkingId", renNotice.getPolLinkingId().toString());
			 reportParams.put("endoresementId",  renNotice.getEndtId().toString());
			 reportParams.put("polValStartDate",  renNotice.getPolValidityStartDate());
			 reportParams.put("language", "E");
			 //added for abudhabi/baharain
			 reportParams.put("locationCode", Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
			 
			 //Start Added by Mindtree on 28/07/2015 for Bugzilla id#4909. Print Renewal notice
			 reportParams.put( "reportTemplatesType", ReportTemplateSet._SBS.toString() );
			 //End Added by Mindtree on 28/07/2015 for Bugzilla id#4909. Print Renewal notice
			 
			 System.out.println(renNotice.getPolLinkingId() + " "+renNotice.getEndtId() + " "+renNotice.getPolValidityStartDate() );
			MailVO mailvo=new MailVO();
			String data="";
			if(!Utils.isEmpty(toAddress)){
				emailCount++;
				System.out.println("toAddress "+toAddress);
				mailvo.setToAddress(toAddress);
				mailvo.setFromAddress(fromAddress);
				mailvo.setMailContent(new StringBuilder(Utils.getSingleValueAppConfig( "EMAIL_RENEWAL_NOTICE_TEXT")));
				mailvo.setSubjectText(Utils.getSingleValueAppConfig( "EMAIL_RENEWAL_NOTICE_TEXT_SUB"));
				mailvo.setMailType(SvcConstants.MAIL_TYPE_PLAINTXT);
					// Currently use proposal form as renewal notice
				fileNames[0]=Utils.getSingleValueAppConfig( "QUOTE_DOC_PROPOSAL_LOC")+quoteNo+"-Renewal quote.pdf";
				mailvo.setFileNames(fileNames);
				mailvo.setDocParameter(reportParams);
				System.out.println("file name "+fileNames[0]);
				// Create the document
				boolean docSuccess = true;
				try{
					mailvo=(MailVO)docCreator.invokeMethod("createDocument", mailvo);
					System.out.println("createDocument called");
				} catch(Exception e){
					e.printStackTrace();
					
					emailErrorList.add(com.Constant.CONST_QUOTE_NO_END +quoteNo+com.Constant.CONST_EMAIL_ID_END+toAddress+com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
					docSuccess = false;
				}

				if(docSuccess){
					if(!Utils.isEmpty(mailvo.getDocCreationStatus())){
						if(mailvo.getDocCreationStatus().equals("failure")){
							emailErrorList.add(com.Constant.CONST_QUOTE_NO_END +quoteNo+com.Constant.CONST_EMAIL_ID_END+toAddress+com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
							docSuccess = false;
						}	
					}else{
						emailErrorList.add(com.Constant.CONST_QUOTE_NO_END +quoteNo+com.Constant.CONST_EMAIL_ID_END+toAddress+com.Constant.CONST_ERROR_DOCUMENT_CREATION_ERROR);
						docSuccess = false;
					}
				}	
				if(docSuccess){
				try{	
					System.out.println("calling sendMail");
					mailvo=(MailVO)mailer.invokeMethod("sendMail", mailvo);
					System.out.println("sendMail called");
				} catch(Exception e)
				{
					e.printStackTrace();
					emailErrorList.add(polNo+": Error while sending email");
				}
					if(!Utils.isEmpty(mailvo.getMailStatus())){
						if(mailvo.getMailStatus().equals("fail")){
							emailErrorList.add(com.Constant.CONST_QUOTE_NO_END +quoteNo+com.Constant.CONST_EMAIL_ID_END+toAddress+" Error : "+mailvo.getError());
						}
						
					}
				}
			}	
		}// End of loop
		if(emailCount==0){
			emailErrorList.add("None of the selected policies have the email id");
		}
		if(!Utils.isEmpty( emailErrorList )){
			response.setData( emailErrorList );
		}		
		return response;
	}	
}
