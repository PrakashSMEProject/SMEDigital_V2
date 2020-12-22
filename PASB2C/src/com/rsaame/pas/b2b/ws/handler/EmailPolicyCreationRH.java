package com.rsaame.pas.b2b.ws.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.b2b.ws.vo.Document;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.mail.svc.PASMailerService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class EmailPolicyCreationRH {
	private final static Logger logger = Logger.getLogger(EmailPolicyCreationRH.class);

	public Response emailToCustomerForPolicyCreation(DataHolderVO<List<BaseVO>> dataHolderVO, boolean isAttachment,
			CreateSBSPolicyResponse createSBSPolicyResponse) {
		Response response = new Response();

		List inputData = dataHolderVO.getData();
		PolicyVO policyVO = (PolicyVO) inputData.get(0);
		userDetailsForMail(policyVO);
		// WSAppUtils.getWSUserProfileVo(Utils.getSingleValueAppConfig("JLT_USER_NAME"));

		String fromAddress = "";
		List<String> emailErrorList = new ArrayList<String>();
		PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
		PASMailerService mailer = (PASMailerService) Utils.getBean("emailService");
		fromAddress = Utils.getSingleValueAppConfig("B2C_DEFAULT_FROM_EMAILID");
		logger.debug("FromAddress   : " + fromAddress);

		String toAddress = "";
		String fileNames[] = new String[4];
		int emailCount = 0;
		Long quoteNo = policyVO.getQuoteNo().longValue();
		Long policyNo = policyVO.getPolicyNo().longValue();

		if (!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getEmailId())) {
			toAddress = policyVO.getGeneralInfo().getInsured().getEmailId();
		} else {
			BusinessException businessExcp = new BusinessException("mail.user.noemail", null,
					"User does not have mail id updated for Quotation No : " + policyNo);
			logger.error(
					"PASEmailUtil:ToAddress Error:User does not have mail id updated for  Quotation No : " + policyNo);
			throw businessExcp;
		}

		HashMap<String, String> reportParams = new HashMap<String, String>();
		reportParams.put("language", "E");
		reportParams.put("locationCode", Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
		reportParams.put("reportTemplatesType", ReportTemplateSet._SBS.toString());

		String emailContent = "";
		String pmmId = AppConstants.B2C_JLT_SBS;
		List<String> resultSet = DAOUtils.getSqlResultForPasString(QueryConstants.POLICY_JLT_SBS, pmmId);
		if (!Utils.isEmpty(resultSet) && resultSet.size() > 0) {
			emailContent = resultSet.get(0);
			logger.debug("EmailContent for Policy notifiication is coming from DB for Oman D2C: " + emailContent);
		}

		MailVO mailvo = new MailVO();
		if (!Utils.isEmpty(toAddress)) {
			emailCount++;
			System.out.println("toAddress " + toAddress);
			mailvo.setToAddress(toAddress);
			mailvo.setFromAddress(fromAddress);
			emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,
					WordUtils.capitalizeFully(policyVO.getGeneralInfo().getInsured().getName()));
			emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_LAST_NAME_IDF,
					WordUtils.capitalizeFully(""));
			mailvo.setMailContent(new StringBuilder(emailContent));
			mailvo.setSubjectText(Utils.getSingleValueAppConfig("EMAIL_ACCEPT_QUOTE_TEXT_SUB"));
			mailvo.setMailType(SvcConstants.MAIL_TYPE_HTML);

			mailvo.setDocParameter(reportParams);

			// Create the document
			if (isAttachment) {
				String documentId = null;
				DocumentHandler documentHandler = new DocumentHandler();
				List<Document> documentList = createSBSPolicyResponse.getDocumentId();
				logger.debug("Response Before document creation : " + response.toString());
				for (Document list : documentList) {
					documentId = list.getDocid();
					try {
						documentHandler.getDocument(documentId);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
					

				// Sending all files through mail after CTP
				fileNames[0] = Utils.getSingleValueAppConfig("POL_DOC_FREEZONE_CERT_LOC") + policyNo + "-FreezoneCertificate.pdf";
				System.out.println("file name_1" + fileNames[0]);
				
				fileNames[1] = Utils.getSingleValueAppConfig("POL_DOC_POL_SCHED_LOC") + policyNo + "-PolicySchedule.pdf";
				System.out.println("file name_2" + fileNames[1]);
				
				fileNames[2] = Utils.getSingleValueAppConfig("POL_DOC_POL_SCHED_LOC") + policyNo + "-PolicyWording.pdf";
				System.out.println("file name_3" + fileNames[2]);
				
				fileNames[3] = Utils.getSingleValueAppConfig("POL_DOC_DEB_NOTE_LOC") + policyNo + "-DebitNote.pdf";
				System.out.println("file name_4" + fileNames[3]);
				
				mailvo.setFileNames(fileNames);
			}

			try {
				System.out.println("calling sendMail");
				mailvo = (MailVO) mailer.invokeMethod("sendMail", mailvo);
				System.out.println("sendMail called");
			} catch (Exception e) {
				e.printStackTrace();
				emailErrorList.add(policyNo + ": Error while sending email");
			}
			if (!Utils.isEmpty(mailvo.getMailStatus())) {
				if (mailvo.getMailStatus().equals("fail")) {
					emailErrorList.add(
							"Quote No : " + quoteNo + " Email id : " + toAddress + " Error : " + mailvo.getError());
				}

			}
		}
		// }// End of loop
		if (emailCount == 0) {
			emailErrorList.add("None of the selected policies have the email id");
		}
		if (!Utils.isEmpty(emailErrorList)) {
			response.setData(emailErrorList);
		}
		logger.debug("Response after document creation and mail sent  : " + response.toString());
		return response;
	}

	private void userDetailsForMail(PolicyVO policyVO) {
		GeneralInfoVO generalInfo = new GeneralInfoVO();
		generalInfo.setInsured(new InsuredVO());
		policyVO.setGeneralInfo(generalInfo);

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		Session session = ht.getSessionFactory().openSession();
		//Changes-Adv#-10698 -JLT adding pol_issue_hour
		Query query = session.createSQLQuery(
				"Select Max(Pol_Endt_Id),Pol_Policy_Id From T_Trn_Policy_Quo Where  Pol_Quotation_No=:quotationNo AND pol_issue_hour = 3 GROUP BY Pol_Policy_Id");
		query.setParameter("quotationNo", policyVO.getQuoteNo());
		List<Object[]> resultList = query.list();
		Object[] obj = resultList.get(0);
		String maxEndtID = ((BigDecimal) obj[0]).toString();
		String polPolicyId = ((BigDecimal) obj[1]).toString();

		Query custDetails = session.createSQLQuery(
				"Select  CSH_E_NAME_1, CSH_E_EMAIL_ID From T_MAS_CASH_CUSTOMER_QUO Where CSH_POLICY_ID=:policyId and Csh_Endt_Id=:endtId");
		custDetails.setParameter("policyId", Long.parseLong(polPolicyId));
		custDetails.setParameter("endtId", Long.parseLong(maxEndtID));
		List<Object[]> results = custDetails.list();
		for (Object[] result : results) {
			policyVO.getGeneralInfo().getInsured().setName(String.valueOf(result[0]));
			policyVO.getGeneralInfo().getInsured().setEmailId(String.valueOf(result[1]));
		}

	}

}
