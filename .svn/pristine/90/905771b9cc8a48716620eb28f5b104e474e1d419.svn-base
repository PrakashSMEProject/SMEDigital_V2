package com.rsaame.pas.email.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import antlr.collections.List;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.reportgenerator.client.ReportRequest;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.MailVO;

public class PASMailRH implements IRequestHandler {
	private final static Logger logger = Logger.getLogger(PASMailRH.class);
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse responseObj) {
		
		Response response = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		policyContext.startTransaction();
		MailVO mailvo=new MailVO();
		String data="";
		//changed for CR - 94168
		//mailvo.setToAddress(request.getParameter("toAddress"));
		if( !Utils.isEmpty( request.getParameter("toAddress") ) ){
			mailvo.setToAddresses( request.getParameter("toAddress").split( "," ));
		}
		
		logger.debug("PASEmailUtil:ToAddress:"+mailvo.getToAddress());
		UserProfile userProfile = null;
		//Adv 14041
		String fromAddress= null;
		userProfile = AppUtils.getUserDetailsFromSession( request );
		if(!Utils.isEmpty(userProfile.getRsaUser().getEmail())){
			//mailvo.setFromAddress(userProfile.getRsaUser().getEmail());
			fromAddress=Utils.getSingleValueAppConfig( "EMAIL_FROM_SBS_WC" );
			mailvo.setFromAddress(fromAddress);
		}else{
			BusinessException businessExcp=new BusinessException( "mail.user.noemail", null, "User does not have mail id updated in user profile" );
			logger.error("PASEmailUtil:FromAddress Error:User does not have mail id updated in user profile");
			data="Quote emailed successfully";
			throw businessExcp;
		}
		String [] fileNames=null;
		String mailType=request.getParameter("mailType");
		logger.debug("PASEmailUtil:mailType:"+mailType);
		//String fileNames1=(String)request.getAttribute("filenames");
		
		
		
		if((mailType.equals((SvcConstants.MAIL_PROCESS_EMAIL_QUOTE)))){
			mailvo.setMailContent(new StringBuilder(Utils.getSingleValueAppConfig( "EMAIL_QUOTE_TXT")));
			mailvo.setSubjectText(Utils.getSingleValueAppConfig( "EMAIL_QUOTE_SUB"));
			mailvo.setMailType(SvcConstants.MAIL_TYPE_PLAINTXT);
			if(!Utils.isEmpty(policyContext.getPolicyDetails().getQuoteNo())){
				logger.debug("PASEmailUtil:Quote Number From Policy Context :"+policyContext.getPolicyDetails().getQuoteNo());
				fileNames=new String[1];
				fileNames[0]=Utils.getSingleValueAppConfig( "QUOTE_DOC_PROPOSAL_LOC")+policyContext.getPolicyDetails().getQuoteNo()+"-Quote.pdf";
				mailvo.setFileNames(fileNames);
				new HashMap <String, String>();
				
				/*docParameter.put("polLinkingId", (String)request.getAttribute("polLinkingId"));
				docParameter.put("polValStartDate", (String)request.getAttribute("valStrtDtForm"));
				docParameter.put("endoresementId", (String)request.getAttribute("endtId"));
				docParameter.put("language", "E");*/
				HttpSession session=request.getSession();
				HashMap <String, String> docParameter=(HashMap<String,String>)session.getAttribute("reportParams");
				mailvo.setDocParameter(docParameter);
				
				PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
				mailvo=(MailVO)docCreator.invokeMethod("createDocument", mailvo);
				if(!Utils.isEmpty(mailvo.getDocCreationStatus())){
					if(mailvo.getDocCreationStatus().equals("failure")){
						BusinessException businessExcp=new BusinessException( com.Constant.CONST_MAIL_ERROR,null, mailvo.getError());
						throw businessExcp;
					}	
				}else{
					BusinessException businessExcp=new BusinessException( com.Constant.CONST_MAIL_ERROR,null, "Quote Number is null");
					throw businessExcp;
				}
				
				
				
			}
			
		}else if((mailType.equals(SvcConstants.MAIL_PROCESS_CONVERT_TO_POLICY))){

			String fileNamesFromUI=request.getParameter("filenames");
			String mailSubject = "";
			String mailContent = "";
			LookUpListVO lookUpListVO = SvcUtils.getLookUpCodesList( "MAIL_CONFG", "ALL", "ALL" );
			java.util.List<LookUpVO> lookUps = lookUpListVO.getLookUpList();
			
			Map<BigDecimal, String> fileNamesMap = new HashMap<BigDecimal, String>();
			Iterator itr = lookUps.listIterator();
			
			while( itr.hasNext() ){
				
				LookUpVO lookUpVO = (LookUpVO)itr.next();
				BigDecimal key = lookUpVO.getCode();
				String value = lookUpVO.getDescription();
				fileNamesMap.put( key,value );
				
			}
			
			//String fileNamesFromUI="debitNote,policyScheduleUAE";
			logger.debug("PASEmailUtil:fileNamesFromUI:"+fileNamesFromUI);
			if(!Utils.isEmpty(fileNamesFromUI)){
				/*if(fileNamesFromUI.indexOf(",")==-1){
					fileNames=new String[1];
					fileNames[0]=fileNamesFromUI;
				}
				else{*/
					StringTokenizer st = new StringTokenizer(fileNamesFromUI, ",");
					fileNames=new String[st.countTokens()];
					int numbDocs=st.countTokens();
					logger.debug("PASEmailUtil:Policy Number From Policy Context :"+policyContext.getPolicyDetails().getPolicyNo());
					if(!Utils.isEmpty(policyContext.getPolicyDetails().getPolicyNo())){
					for(int i=0;i<numbDocs;i++){
						
						String fileName=st.nextToken();
						if( ( i != 0 ) && ( i != (numbDocs-1) ) ){
							mailSubject = mailSubject.concat( Utils.getSingleValueAppConfig( "COMMA" ));
							mailSubject = mailSubject.concat( " ");
						}else if( ( numbDocs > 1 ) && ( i == (numbDocs-1) ) ){
							mailSubject = mailSubject.concat( " ");
							mailSubject = mailSubject.concat( Utils.getSingleValueAppConfig( "AMPERSAND" ));
							mailSubject = mailSubject.concat( " ");
						}
						
						if(fileName.equals("policyScheduleUAE")){
						
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.POLICY_SCHEDULE ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 1 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_POL_SCHED_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-PolicySchedule.pdf";	
						
						}else if(fileName.equals("printReceipt")){
							
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.RECEIPT ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 6 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_POL_RECEIPTS_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-Receipt.pdf";
						
						}else if(fileName.equals("creditNote")){
						
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.CREDIT_NOTE ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 3 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_CRED_NOTE_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-CreditNote.pdf";
						
						}else if(fileName.equals("grossCreditNote")){
						
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.GROSS_CREDIT_NOTE ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 3 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_GROSS_CRED_NOTE_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-GrossCreditNote.pdf";
						
						}
						else if(fileName.equals("debitNote") ){
						
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.DEBIT_NOTE ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 4 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_DEB_NOTE_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-DebitNote.pdf";
						
						}else if(fileName.equals("grossDebitNote")){
						
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.GROSS_DEBIT_NOTE ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 4 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_GROSS_DEB_NOTE_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-GrossDebitNote.pdf";
						
						}
						else if(fileName.equals("freeZone")){
						
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.FREE_ZONE ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 5 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_FREEZONE_CERT_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-FreeZoneCert.pdf";
					
						}else if(fileName.equals("endScheduleUAE")){
							
							mailSubject = mailSubject.concat( fileNamesMap.get( AppConstants.ENDORSEMENT_SCHEDULE ) );
							//mailSubject = mailSubject.concat( SvcUtils.getLookUpDescription( "MAIL_CONFG", "ALL", "ALL", 2 ) );
							fileNames[i]=Utils.getSingleValueAppConfig( "POL_DOC_ENDT_SCHED_LOC")+policyContext.getPolicyDetails().getPolicyNo()+"-EndorsementSchedule.pdf";
						}


					}
					 
					if( numbDocs > 0){
						mailSubject = mailSubject.concat(" ");
						mailSubject = mailSubject.concat( Utils.getSingleValueAppConfig( "DETAILS" ));
					}
					
					}
					else{
						BusinessException businessExcp=new BusinessException( com.Constant.CONST_MAIL_ERROR,null, "Policy Number is Null");
						throw businessExcp;
						
					}


				//}
			} 
			
			if( !Utils.isEmpty( fileNamesMap.get( AppConstants.MAIL_BODY) )){
				mailvo.setMailContent( new StringBuilder( fileNamesMap.get( AppConstants.MAIL_BODY) ) );
			}else {
				mailvo.setSubjectText(Utils.getSingleValueAppConfig( "CONVERT_TO_POLICY_TXT"));
			}
			
			if( !Utils.isEmpty( mailSubject )){
				mailvo.setSubjectText( mailSubject );
			}else {
				mailvo.setSubjectText(Utils.getSingleValueAppConfig( "CONVERT_TO_POLICY_SUB"));
			}	
			mailvo.setMailType(SvcConstants.MAIL_TYPE_PLAINTXT);
			mailvo.setFileNames(fileNames);
			
			
			
			HttpSession session=request.getSession();
			HashMap <String, String> docParameter=(HashMap<String,String>)session.getAttribute("reportParams");
			mailvo.setDocParameter(docParameter);
			
			PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
			mailvo=(MailVO)docCreator.invokeMethod("createDocument", mailvo);
			if(!Utils.isEmpty(mailvo.getDocCreationStatus())){
				if(mailvo.getDocCreationStatus().equals("failure")){
					BusinessException businessExcp=new BusinessException( com.Constant.CONST_MAIL_ERROR,null, mailvo.getError());
					throw businessExcp;
				}	
			}else{
				BusinessException businessExcp=new BusinessException( com.Constant.CONST_MAIL_ERROR,null, mailvo.getError());
				throw businessExcp;
			}
			
			
			
		}else if((mailType.equals(Utils.getSingleValueAppConfig(SvcConstants.MAIL_PRINT_RENEWAL_NOTICES)))){
			mailvo.setMailContent(new StringBuilder(Utils.getSingleValueAppConfig( "PRINT_RENEWAL_NOTICES_TXT")));
			mailvo.setSubjectText(Utils.getSingleValueAppConfig( "PRINT_RENEWAL_NOTICES_SUB"));
			mailvo.setMailType(SvcConstants.MAIL_TYPE_PLAINTXT);
			/*fileNames=new String[1];
			fileNames[0]=Utils.getSingleValueAppConfig( "QUOTE_DOC_PROPOSAL_LOC")+policyContext.getPolicyDetails().getQuoteNo()+"-Quote.pdf";*/
			mailvo.setFileNames(fileNames);
		}
		/*fileNames=new String[3];
		
		fileNames[0]="C:/PASDocuments/sample1.pdf";
		fileNames[1]="C:/PASDocuments/sample2.pdf";
		fileNames[2]="C:/PASDocuments/sample3.pdf";*/
		
		PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
		//PASMailerService mailer = new PASMailerService();
		mailvo=(MailVO)mailer.invokeMethod("sendMail", mailvo);
		if(!Utils.isEmpty(mailvo.getMailStatus())){
			if(mailvo.getMailStatus().equals("fail")){
				BusinessException businessExcp=new BusinessException( com.Constant.CONST_MAIL_ERROR,null, mailvo.getError());
				throw businessExcp;
			}
			
		}
		//LocCode = quoteDAO.getStateTariff(tariffCode);
	//locCode=Integer.parseInt(tariffService.invokeMethod("getTarLocation",tariffCode.toString()).toString());
		
		policyContext.endTransaction();
		
		if((mailType.equals((SvcConstants.MAIL_PROCESS_EMAIL_QUOTE)))){
			data="Quote emailed successfully";
			
		}else if((mailType.equals(SvcConstants.MAIL_PROCESS_CONVERT_TO_POLICY))){
			data="Policy Documents emailed successfully";
		}else if((mailType.equals(Utils.getSingleValueAppConfig(SvcConstants.MAIL_PRINT_RENEWAL_NOTICES)))){
			data="Renewal Documents emailed successfully";
		}
		response.setData(data);
		return response;
	}

}
