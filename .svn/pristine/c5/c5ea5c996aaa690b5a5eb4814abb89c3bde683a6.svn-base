/**
 * 
 */
package com.rsaame.pas.b2b.ws.handler;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.vo.Document;
import com.rsaame.pas.b2b.ws.vo.UploadDocumentRequest;
import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2b.ws.vo.response.DocumentDownload;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.PolicyDetailsHolder;
import com.rsaame.pas.vo.app.PolicyDetailsVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.CreditNoteDetailsVO;
import com.rsaame.pas.vo.bus.DebitNoteDetailsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReceiptDetailsVO;

/**
 * @author M1037404
 *
 */
public class DocumentHandler {

	private final static Logger LOGGER = Logger.getLogger(DocumentHandler.class);
	private static final String CASH_CUSTOMER_QUO = "t_mas_cash_customer_quo";
	private static final String CASH_CUSTOMER = "t_mas_cash_customer";
	private static final String POLICY_QUO = "t_trn_policy_quo";
	private static final String POLICY = "t_trn_policy";
	
	public List<Document> getDocumentList(PolicyVO policyVO) {
		
		CreditNoteDetailsVO crDetsVO = new CreditNoteDetailsVO();
		DebitNoteDetailsVO drNoteDetsVO = new DebitNoteDetailsVO();
		ReceiptDetailsVO rcptDetsVO = new ReceiptDetailsVO();
		List<Document> documentList = new ArrayList<>();
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Starts
		Boolean creditNoteAvailable = false;
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Starts
		Boolean debitNoteAvailable = false;
		Boolean receiptAvailable = false;
		policyVO.setIsQuote(false);
		policyVO.setEndtId(0L); 	// As per the requirement only oth record documents we will give in resposne
		policyVO = WSAppUtils.getPolicyDetailsFromPolicyNo(policyVO);
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
		DataHolderVO<Long> policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "getPolicyIdForPolicy", policyVO );
		if(!Utils.isEmpty( policyIdHolder ) && !Utils.isEmpty( policyIdHolder.getData() )){
			policyVO.setBasePolicyId( policyIdHolder.getData() );
		}
		// getting documents policy schedule and freezone and wording
		documentList = getPolicyScheduleFreeZone(policyVO);
		
		
		String identifier = "";
		if( !Utils.isEmpty( policyVO ) ){
			crDetsVO.setCndPolicyNo( policyVO.getPolicyNo() );
			drNoteDetsVO.setDndPolicyNo( policyVO.getPolicyNo() );
			rcptDetsVO.setRcdPolicyNo( policyVO.getPolicyNo() );
			
			crDetsVO.setCndPolicyYear( SvcUtils.getYearFromDate(policyVO.getPolEffectiveDate()) );
			drNoteDetsVO.setDndPolicyYear( SvcUtils.getYearFromDate(policyVO.getPolEffectiveDate()) );
			
			LOGGER.info( "Policy No within CreditNoteDocRH to check if credit note exists -->" + policyVO.getPolicyNo() );
			Long endtId = 0L; //AppUtils.getLatestEndtId( policyVO );
			LOGGER.info( "EndtId within CreditNoteDocRH to check if credit note exists -->" + endtId );
			if( Utils.isEmpty( endtId ) ){
				throw new BusinessException( "cmn.unknownError", null, "For linking id " + policyVO.getPolLinkingId() + " Endorsment id is null" );
			}
			
			crDetsVO.setCndEndtId( endtId );
			drNoteDetsVO.setDndEndtId( endtId );
			rcptDetsVO.setRcdEndtId(endtId);
		}
		if(!Utils.isEmpty( policyVO.getBasePolicyId() ) ){
			crDetsVO.setCndPolicyId( policyVO.getBasePolicyId() );
			drNoteDetsVO.setDndPolicyId( policyVO.getBasePolicyId() );
			rcptDetsVO.setRcdPolicyId( policyVO.getBasePolicyId() );
		}
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Starts
		identifier="CREDIT_NOTE_DOC";
		CreditNoteDetailsVO resultCrDetsVO = (CreditNoteDetailsVO) TaskExecutor.executeTasks( identifier, crDetsVO );
		if( !Utils.isEmpty( resultCrDetsVO ) ){
			LOGGER.info( "Got the CreditNote:::"+resultCrDetsVO.getCndCreditNoteNo()+"::"+resultCrDetsVO.getCndCreditNoteDate());
			
			creditNoteAvailable = true;
		}else {
			LOGGER.info( "CreditNote not available:::");
		}
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Ends
		identifier="DEBIT_NOTE_DOC";
		DebitNoteDetailsVO resultDrNoteDetsVO = (DebitNoteDetailsVO) TaskExecutor.executeTasks( identifier, drNoteDetsVO );
		if (!Utils.isEmpty(resultDrNoteDetsVO)) {

			if (!Utils.isEmpty(resultDrNoteDetsVO.getDndDebitNoteNo())) {
				LOGGER.info("Got the DebitNote:::" + resultDrNoteDetsVO.getDndDebitNoteNo() + "::"
						+ resultDrNoteDetsVO.getDndDebitNoteDate());
				debitNoteAvailable = true;
			}
		}else {
			LOGGER.info( "DebitNote not available:::");
		}
		
		identifier="RECEIPT_DOC";
		ReceiptDetailsVO resultRcptDetsVO = (ReceiptDetailsVO) TaskExecutor.executeTasks( identifier, rcptDetsVO );
		if (!Utils.isEmpty(resultRcptDetsVO)) {

			if (!Utils.isEmpty(resultRcptDetsVO.getRcdReceiptNo())) {

				LOGGER.info("Got the Receipt:::" + resultRcptDetsVO.getRcdReceiptNo() + "::"
						+ resultRcptDetsVO.getRcdReceiptDate());
				receiptAvailable = true;
			}
		}else {
			LOGGER.info( "Receipt not available:::");
		}
		
		
		String vsdDate = formatDt(policyVO.getValidityStartDate().toString(), "yyyy-MM-dd HH:mm:ss", "dd-MMM-yyyy");

		// policyNo : linkingId : VSD : policyId : endtId : DebitNoteNo : DebitNoteDate 
		if (debitNoteAvailable) {
			Document document = new Document();
			String debitNoteDate = resultDrNoteDetsVO.getDndDebitNoteDate();
			debitNoteDate = debitNoteDate.replaceAll("/", "-");
			document.setDocid(policyVO.getPolicyNo()+":"+policyVO.getPolLinkingId()+":"+vsdDate+":"+ policyVO.getBasePolicyId() +":"+policyVO.getEndtId()+":"+resultDrNoteDetsVO.getDndDebitNoteNo()+":"+debitNoteDate+":DebitNote");
			document.setName("DebitNote");
			documentList.add(document);
		}
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Starts
		// policyNo : linkingId : VSD : policyId : endtId : CreditNoteNo : CreditNoteDate 
		if (creditNoteAvailable) {
			Document document = new Document();
			String creditNoteDate = resultCrDetsVO.getCndCreditNoteDate();
			creditNoteDate = creditNoteDate.replaceAll("/", "-");
			document.setDocid(policyVO.getPolicyNo()+":"+policyVO.getPolLinkingId()+":"+vsdDate+":"+ policyVO.getBasePolicyId() +":"+policyVO.getEndtId()+":"+resultCrDetsVO.getCndCreditNoteNo()+":"+creditNoteDate+":CreditNote");
			document.setName("CreditNote");
			documentList.add(document);
		}
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Ends
		// policyNo : linkingId : VSD : policyId : endtId : ReceiptNo : ReceiptDate 
		if(receiptAvailable) {
			Document document = new Document();
			String receiptDate = resultRcptDetsVO.getRcdReceiptDate();
			receiptDate = receiptDate.replaceAll("/", "-");
			document.setDocid(policyVO.getPolicyNo()+":"+policyVO.getPolLinkingId()+":"+vsdDate+":"+ policyVO.getBasePolicyId() +":"+policyVO.getEndtId()+":"+resultRcptDetsVO.getRcdReceiptNo()+":"+receiptDate+":PolicyReceipt");
			document.setName("PolicyReceipt");
			documentList.add(document);
		}
		
		
		return documentList;
	}
	/**
	 *  To get the the parameters required to generate the document refer PrintPolicyDocRH class
	 * @param baseVO
	 * @return
	 */
	private List<Document> getPolicyScheduleFreeZone(BaseVO baseVO) {

		PolicyVO policyVO = (PolicyVO) baseVO;
		// As per the requirement we will give 0th version of policy hard coding for
		// time being
		String freezone = null;
		String policySchedule = null;
		int vatCode = 0;
		int vatDateCond = 0;
		List<Document> documentList = new ArrayList<>();
		if (!Utils.isEmpty(policyVO)) {

			String parSecId = Utils.getSingleValueAppConfig("SECTION_ID_PAR", "1");
			String plSecId = Utils.getSingleValueAppConfig("SECTION_ID_PL", "6");
			String wcSecId = Utils.getSingleValueAppConfig("SECTION_ID_WC", "7");

			boolean parFlag = false;
			boolean plFlag = false;
			boolean wcFlag = false;

			String policyLinkingId = "";
			String polSecId = "";
			Boolean isDistributionBr = false;
			boolean endFlag = false;
			boolean renFlag = false;
			boolean isRecieptToBeShown = false;
			boolean isFreeZoneToBeShown = false;
			String parPolId = "";
			String plPolId = "";
			String wcPolId = "";
			String identifier = "PRINT_POLICY_DOC";

			PolicyDetailsVO policyDtlVO = new PolicyDetailsVO();
			policyDtlVO.setPolicyNo(policyVO.getPolicyNo());
			// Needed to differentiate between renewal policy and original policy
			String expDate = SvcUtils.getYearFromDate(policyVO.getPolExpiryDate()) + "-"
					+ SvcUtils.getMonthFromDate(policyVO.getPolExpiryDate()) + "-"
					+ SvcUtils.getDayFromDate(policyVO.getPolExpiryDate());
			policyDtlVO.setPolExpiryDate(expDate);
			policyDtlVO.setPolConcPolicyNo(policyVO.getConcatPolicyNo());
			LOGGER.info("Policy No within DocumentHandler for processing -->" + policyVO.getPolicyNo());
			// policyDtlVO.setEndtId((policyVO.getEndtId()).toString());
			/*
			 * if in quote flow just after converting to policy then 0 should be sent as
			 * endt id for debit note search else latest endorsement id for the policy
			 */
			policyDtlVO.setEndtId("0");
			LOGGER.info("Endt Id within DocumentHandler for processing -->" + SvcUtils.getLatestEndtId(policyVO));
			baseVO = TaskExecutor.executeTasks(identifier, policyDtlVO);

			PolicyDetailsHolder policyDtls = (PolicyDetailsHolder) baseVO;
			List<PolicyDetailsVO> policyList = policyDtls.getPolicyDtlList();

			Integer maxEndId = 0;
			// format = new SimpleDateFormat("dd-MMM-yyyy");
			String sdate = "";

			for (PolicyDetailsVO policyDetailsVO : policyList) {

				policyLinkingId = policyDetailsVO.getPolicyLinkingId();
				polSecId = policyDetailsVO.getSectionId();
				sdate = policyDetailsVO.getStartDate();
				if (Integer.valueOf(policyDetailsVO.getEndtId()) > maxEndId) {
					maxEndId = Integer.valueOf(policyDetailsVO.getEndtId());
				}
				if (Integer.valueOf(policyDetailsVO.getPolDocumentId()) == 3) {
					endFlag = true;
				} else if (Integer.valueOf(policyDetailsVO.getPolDocumentId()) == 2) {
					renFlag = true;
				}
				if (parSecId.equals(polSecId)) {
					parFlag = true;
					parPolId = policyDetailsVO.getPolicyId();
				} else if (plSecId.equals(polSecId)) {
					plFlag = true;
					plPolId = policyDetailsVO.getPolicyId();
				} else if (wcSecId.equals(polSecId)) {
					wcFlag = true;
					wcPolId = policyDetailsVO.getPolicyId();
				}

				if ((!Utils.isEmpty(policyDetailsVO.getPolBrCode())
						&& !policyDetailsVO.getPolBrCode().equalsIgnoreCase("null"))
						|| (!Utils.isEmpty(policyDetailsVO.getPolAgentId())
								&& !policyDetailsVO.getPolAgentId().equalsIgnoreCase("null"))) {
					isDistributionBr = true;
				}
			}

			if (!Utils.isEmpty(policyVO) && !Utils.isEmpty(policyVO.getPolicyNo())) {
				isFreeZoneToBeShown = WSAppUtils.isFreeZoneToBeShow(policyVO.getPolicyNo(), policyVO.getQuoteNo(),
						policyVO.getEndtId(), policyVO.getValidityStartDate(), policyVO.getIsQuote());
			}
			// FreeZone certificate data required
			// policyNo : endtId : VSD : parFlag : plFlag : wcFlag : parPolId : plPolId : wcPolId
			if(isFreeZoneToBeShown) {
				freezone = policyVO.getPolicyNo()+":"+ maxEndId + ":" + sdate + ":" + parFlag + ":" + plFlag + ":" + wcFlag + ":" + parPolId + ":"
						+ plPolId + ":" + wcPolId;
				Document document = new Document();
				document.setDocid(freezone+":FreeZoneCertificate");
				document.setName("FreeZoneCertificate");
				documentList.add(document);
			}

			
			policySchedule = policyVO.getPolicyNo()+":" +policyLinkingId  + ":" + maxEndId + ":" + sdate;

			if (policyVO.getPolVATCode() != 0) { // changed from 999 to 0 after confirmation for SBS
				LOGGER.info("HHH VAT Code for SBS-1: " + policyVO.getPolVATCode());
				vatCode = policyVO.getPolVATCode();

			}
			
			if (policyVO.getQuoteNo() != null && policyVO.getIsQuote() != null && policyVO.getEndtId() != null) {

				LOGGER.info("HHH QUOTE NUMBER for  SBS: " + policyVO.getQuoteNo());
				LOGGER.info("HHH IS_QUOTE for  SBS: " + policyVO.getIsQuote());
				LOGGER.info("HHH POL_ENDT_ID for  SBS: " + policyVO.getEndtId());
				LOGGER.info("HHH POL_ENDT_ID New for  SBS: " + SvcUtils.getLatestEndtId(policyVO));
				java.util.Date polIssueDate = null;

				if (policyVO.getIsQuote()) {

					LOGGER.info("HHH POL_LINKING_ID New for  SBS: " + policyVO.getPolLinkingId());
					LOGGER.info("HHH POLICY NUM New for  SBS: " + policyVO.getPolicyNo());
					polIssueDate = DAOUtils.getPolIssueDateForSBSQuo(policyVO.getQuoteNo(), policyVO.getIsQuote(),
							policyVO.getPolLinkingId(), policyVO.getPolicyNo());
				}

				else {
					polIssueDate = DAOUtils.getPolIssueDate(policyVO.getQuoteNo(), policyVO.getIsQuote(),
							policyVO.getEndtId());
				}
				if (polIssueDate != null && polIssueDate.compareTo(getVatLiveDate()) >= 0) {

					LOGGER.info("HHH POL ISSUE DATE for SBS: " + polIssueDate);
					vatDateCond = 1;
				} else {
					vatDateCond = 1;
				}
			} else {
				vatDateCond = 0;
			}
			if (isDistributionBr) {
				policySchedule = policySchedule + ":true";
			} else
				policySchedule = policySchedule + ":false";
		}
		if (vatCode != 0 && vatCode != 3 && vatCode != 4) {
			if (vatDateCond == 1) {
				policySchedule = policySchedule + ":true";
			} else {
				policySchedule = policySchedule + ":false";
			}
		}
		// policy No : linkingId : endtId : VSD : isBrokerDistribustion : vatCodeCond
		Document document = new Document();
		document.setDocid(policySchedule+":PolicySchedule");
		document.setName(com.Constant.CONST_POLICYSCHEDULE);
		documentList.add(document);
		
		Document policyWording = new Document();
		policyWording.setDocid(policySchedule+":PolicyScheduleWithWording");
		policyWording.setName("PolicyScheduleWithWording");
		documentList.add(policyWording);
		
		return documentList;
	}

	
	public DocumentDownload getDocument(String documentId) throws IOException {
		
		DocumentDownload documentDownload = new DocumentDownload();
		String [] fileNames= new String[1];
		String [] reportParameter=null;
		
		StringTokenizer st = new StringTokenizer(documentId, ":");
		reportParameter=new String[st.countTokens()];
		reportParameter = documentId.split(":");
		
		Long policyNo = Long.parseLong(reportParameter[0]);
		
		String reportTemplatesType = ReportTemplateSet._SBS.toString();
		String fileName = reportParameter[reportParameter.length-1];
		String rptfileName = null;
		MailVO mailVo=new MailVO();
		HashMap <String, String> docParameter= new HashMap<>();
		
		if(fileName.equalsIgnoreCase("FreeZoneCertificate")){
			// Get Document List Response format -> policyNo : endtId : VSD : parFlag : plFlag : wcFlag : parPolId : plPolId : wcPolId
			
			Boolean wcParFlag = false;
			Boolean wcPlFlag = false;
			Long plWCPolid  = 0L;
			if((reportParameter[6] != null) && (!reportParameter[6].equalsIgnoreCase("null")) && !reportParameter[6].equalsIgnoreCase("") && (reportParameter[8] != null) && (!reportParameter[8].equalsIgnoreCase("null")) && !reportParameter[8].equalsIgnoreCase("")){
				wcParFlag = true;
				plWCPolid = Long.valueOf(reportParameter[8]);
			}
			else if((reportParameter[7] != null) && (!reportParameter[7].equalsIgnoreCase("null")) && !reportParameter[7].equalsIgnoreCase("") && (reportParameter[8] != null) && (!reportParameter[8].equalsIgnoreCase("null")) && !reportParameter[8].equalsIgnoreCase("")){
				wcPlFlag = true;
				plWCPolid = Long.valueOf(reportParameter[7]);
				
			}else{
				wcParFlag = false;
				wcPlFlag = false;
				if((reportParameter[7] != null) && (!reportParameter[7].equalsIgnoreCase("null")) && !reportParameter[7].equalsIgnoreCase("")){
				plWCPolid = Long.valueOf(reportParameter[7]);
				}	
			}
			
			if(!Utils.isEmpty(plWCPolid))
				docParameter.put("PolId", plWCPolid.toString());
			
			docParameter.put("SDate", reportParameter[2]);
			docParameter.put("EndId", reportParameter[1]);
			
			if(!Utils.isEmpty(reportParameter[6]))
				docParameter.put("PARPolId", reportParameter[6]);
			
			docParameter.put("PARFlag", reportParameter[3]);
//			docParameter.put("PLFlag", reportParameter[4]);
			docParameter.put("PLWCFlag", wcPlFlag.toString());
			docParameter.put("PARWCFlag",wcParFlag.toString());
			docParameter.put(com.Constant.CONST_LANGUAGE, "E");
			docParameter.put(com.Constant.CONST_LOCATIONCODE,"20");
			docParameter.put(com.Constant.CONST_REPORTTEMPLATESTYPE, "_SBS");
			
			fileName = Utils.getSingleValueAppConfig( "POL_DOC_FREEZONE_CERT_LOC")+policyNo+"-FreeZoneCertificate.pdf";
		}
		else if(fileName.equalsIgnoreCase(com.Constant.CONST_POLICYSCHEDULE)) {
			
			// Get Document List Response format ->  policy No : linkingId : endtId : VSD : isBrokerDistribustion : vatCodeCond
		
			
			
			docParameter.put(com.Constant.CONST_POLLINKINGID, reportParameter[1]);
			docParameter.put(com.Constant.CONST_ENDORSEMENTID, reportParameter[2]);
			docParameter.put(com.Constant.CONST_VALIDITYSTARTDATE, reportParameter[3]);
			docParameter.put(com.Constant.CONST_LANGUAGE, "E");
			docParameter.put(com.Constant.CONST_LOCATIONCODE,"20");
			docParameter.put(com.Constant.CONST_REPORTTEMPLATESTYPE, "_SBS");
			docParameter.put("isBrokerCommission", reportParameter[4]);
			docParameter.put("isVatEnabled", reportParameter[5]);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULE, "true");
			docParameter.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, "true");
			
			// Optional Report should not generate 
			docParameter.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_RECEIPT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_ENDSCHEDULEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_DEBITNOTEREPORT,com.Constant.CONST_FALSE);
			
			
			fileName = Utils.getSingleValueAppConfig( "POL_DOC_POL_SCHED_LOC")+policyNo+"-PolicySchedule.pdf";
		}
		else if(fileName.equalsIgnoreCase("PolicyScheduleWithWording")) {
			
			// Get Document List Response format ->  policy No : linkingId : endtId : VSD : isBrokerDistribustion : vatCodeCond
			//As per new requirement changed policywording  to policy wording
		/*	docParameter.put(com.Constant.CONST_POLLINKINGID, reportParameter[1]);
			docParameter.put(com.Constant.CONST_ENDORSEMENTID, reportParameter[2]);
			docParameter.put(com.Constant.CONST_VALIDITYSTARTDATE, reportParameter[3]);
			docParameter.put(com.Constant.CONST_LANGUAGE, "E");
			docParameter.put(com.Constant.CONST_LOCATIONCODE,"20");
			docParameter.put(com.Constant.CONST_REPORTTEMPLATESTYPE, "_SBS");
			docParameter.put("isBrokerCommission", reportParameter[4]);
			docParameter.put("isVatEnabled", reportParameter[5]);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULE, "true");
			docParameter.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, "true");
			
			// Optional Report should not generate 
			docParameter.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_RECEIPT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_ENDSCHEDULEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_DEBITNOTEREPORT,com.Constant.CONST_FALSE);*/
			
			fileName = Utils.getSingleValueAppConfig( "POL_DOC_WORDING");
		}
		else if (fileName.equalsIgnoreCase("DebitNote")) {
			
			// Get Document List Response format -> policyNo : linkingId : VSD : policyId : endtId : DebitNoteNo : DebitNoteDate 
			// 
			docParameter.put(com.Constant.CONST_POLLINKINGID, reportParameter[1]);
			docParameter.put(com.Constant.CONST_VALIDITYSTARTDATE, reportParameter[2]);
			docParameter.put("policyId", reportParameter[3]);
			docParameter.put(com.Constant.CONST_ENDORSEMENTID, reportParameter[4]);
			docParameter.put(com.Constant.CONST_LANGUAGE, "E");
			docParameter.put(com.Constant.CONST_LOCATIONCODE, "20");
			docParameter.put(com.Constant.CONST_REPORTTEMPLATESTYPE, "_SBS");
			/*docParameter.put("isBrokerCommission", reportParameter[4]);
			docParameter.put("isVatEnabled", reportParameter[5]);*/
			docParameter.put(com.Constant.CONST_DEBITNOTEREPORT,"true");
			docParameter.put("debitNoteNo", reportParameter[5]);
			docParameter.put("debitNoteDate", reportParameter[6]);
			
			// Optional Report should not generate 
			docParameter.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_RECEIPT, com.Constant.CONST_FALSE); docParameter.put(com.Constant.CONST_ENDSCHEDULEREPORT,com.Constant.CONST_FALSE); 
			docParameter.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULE, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, com.Constant.CONST_FALSE);
			 
			fileName=Utils.getSingleValueAppConfig( "POL_DOC_DEB_NOTE_LOC")+policyNo+"-DebitNote.pdf";
		}		
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Starts
		else if (fileName.equalsIgnoreCase("CreditNote")) {
			
			// Get Document List Response format -> policyNo : linkingId : VSD : policyId : endtId : CreditNoteNo : CreditNoteDate 
			// 
			docParameter.put(com.Constant.CONST_POLLINKINGID, reportParameter[1]);
			docParameter.put(com.Constant.CONST_VALIDITYSTARTDATE, reportParameter[2]);
			docParameter.put("policyId", reportParameter[3]);
			docParameter.put(com.Constant.CONST_ENDORSEMENTID, reportParameter[4]);
			docParameter.put(com.Constant.CONST_LANGUAGE, "E");
			docParameter.put(com.Constant.CONST_LOCATIONCODE, "20");
			docParameter.put(com.Constant.CONST_REPORTTEMPLATESTYPE, "_SBS");
			/*docParameter.put("isBrokerCommission", reportParameter[4]);
			docParameter.put("isVatEnabled", reportParameter[5]);*/
			docParameter.put(com.Constant.CONST_CREDITNOTEREPORT,"true");
			docParameter.put("creditNoteNo", reportParameter[5]);
			docParameter.put("creditNoteDate", reportParameter[6]);
			
			// Optional Report should not generate 
			docParameter.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_RECEIPT, com.Constant.CONST_FALSE); docParameter.put(com.Constant.CONST_ENDSCHEDULEREPORT,com.Constant.CONST_FALSE); 
			docParameter.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_DEBITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULE, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, com.Constant.CONST_FALSE);
			 
			fileName=Utils.getSingleValueAppConfig("POL_DOC_CRED_NOTE_LOC")+policyNo+"-CreditNote.pdf";
		}
		//CTS - 11.09.2020 - JLT UAT CHANGE - Credit Note Enable - Ends
		else if (fileName.equalsIgnoreCase("PolicyReceipt")) {
			//
			// Get Document List Response format -> policyNo : linkingId : VSD : policyId :
			// endtId : ReceiptNo : ReceiptDate
			//
			docParameter.put(com.Constant.CONST_POLLINKINGID, reportParameter[1]);
			docParameter.put(com.Constant.CONST_VALIDITYSTARTDATE, reportParameter[2]);
			docParameter.put("policyId", reportParameter[3]);
			docParameter.put(com.Constant.CONST_ENDORSEMENTID, reportParameter[4]);
			docParameter.put(com.Constant.CONST_LANGUAGE, "E");
			docParameter.put(com.Constant.CONST_LOCATIONCODE, "20");
			docParameter.put(com.Constant.CONST_REPORTTEMPLATESTYPE, "_SBS");
			/*docParameter.put("isBrokerCommission", reportParameter[4]);
			docParameter.put("isVatEnabled", reportParameter[5]);*/
			docParameter.put(com.Constant.CONST_RECEIPT, "true");
			docParameter.put("receiptNo", reportParameter[5]);
			docParameter.put("receiptDate", reportParameter[6]);

			// Optional Report should not generate 
			
			docParameter.put(com.Constant.CONST_DEBITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_ENDSCHEDULEREPORT,com.Constant.CONST_FALSE); 
			docParameter.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULE, com.Constant.CONST_FALSE);
			docParameter.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, com.Constant.CONST_FALSE);
			 
			fileName = Utils.getSingleValueAppConfig( "POL_DOC_POL_RECEIPTS_LOC")+policyNo+"-PolicyReceipt.pdf";
		}
		fileNames[0] = fileName;
		mailVo.setFileNames(fileNames);
		
		mailVo.setDocParameter(docParameter);
		PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
		if (!(fileName.contains("PolicyWording"))) {
			mailVo = (MailVO) docCreator.invokeMethod("createDocument", mailVo);
			if (!Utils.isEmpty(mailVo.getDocCreationStatus())) {
				if (mailVo.getDocCreationStatus().equals("failure")) {
					BusinessException businessExcp = new BusinessException("mail.error", null, mailVo.getError());
					throw businessExcp;
				}
			} else {
				BusinessException businessExcp = new BusinessException("mail.error", null, mailVo.getError());
				throw businessExcp;
			}
		}
		
		
		
		String policyPropForm = CommonHandler.encodeToString(fileName);//passing path set earlier
		byte[] content = policyPropForm.getBytes();
		
		documentDownload.setName(reportParameter[reportParameter.length-1]);
		documentDownload.setMimeType("application/pdf");
		documentDownload.setContent(content);
		
		return documentDownload;
	}
	
	public String submitTradeLicenceDocument(UploadDocumentRequest uploadDocumentRequest,
			UploadDocumentResponse uploadDocumentResponse, PolicyVO policyVO) throws IOException {
		
		String response="";
		String rootPath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" );
		String modulePath = Utils.getSingleValueAppConfig( Utils.concat( "FILE_UPLOAD_" + com.Constant.CONST_TRADE_LICENCE + "_FOLDER" ) );
		String path = Utils.concat( rootPath, "/", Utils.isEmpty( modulePath ) ? "" : modulePath );
		Boolean isQuote = true;
		Long quoLinkingId = 0L;
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean("hibernateTemplate"); 
		quoLinkingId = getLinkingIdOfQuo(policyVO, hibernateTemplate, isQuote);
		path = path + "/" + quoLinkingId;
		
		File directory = new File( path );
		if( !directory.exists() ){
			boolean success = ( new File( path ) ).mkdirs();
			if(success){
				LOGGER.debug( "Directory created successfully "+ path);
			}
		}
		
		if(!Utils.isEmpty(uploadDocumentRequest.getDocumentContent())) {
			int size = uploadDocumentRequest.getDocumentContent().getContent().length;
			String fileName = null;
			String extension = null;
			boolean noError = true;
			boolean success = true;
			
			if(!Utils.isEmpty(uploadDocumentRequest.getDocumentContent().getFileName()))
				fileName = uploadDocumentRequest.getDocumentContent().getFileName();
			
			/*
			 * Get the extension of the file. 
			 */
			if(!Utils.isEmpty( fileName )){
					if( fileName.lastIndexOf( "." ) > 0 ){
						extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
					}
			}
			if(!Utils.isEmpty( extension )){
				if( !isAllowedExtn( extension.toLowerCase(), com.Constant.CONST_TRADE_LICENCE ) ){
					
				}else if(com.Constant.CONST_TRADE_LICENCE.equals(com.Constant.CONST_TRADE_LICENCE)) {
					 if(size > Double.valueOf( Utils.getSingleValueAppConfig( "TRADE_LICENCE_FILE_SIZE" )) ) {
						noError = false;
//						responseObj.addErrorKey( "pas.upload.tlc.fileSizeTooHigh" );
					 }
				}
			}
			List<Long> quoEndtList = null; 
			quoEndtList = getEndtIdOfQuoTL(policyVO,hibernateTemplate,isQuote);
			String fileNameExt = "TRADE_LICENCE_"+quoEndtList.get(0)+"."+extension;
			
			response = WSAppUtils.decodeToFile(path+"/"+fileNameExt, uploadDocumentRequest.getDocumentContent().getContent());
			policyVO.setIsQuote(true);
			policyVO.setPolLinkingId(quoLinkingId);
			policyVO.getGeneralInfo().getInsured().setTradeLicenseNo(uploadDocumentRequest.getDocumentContent().getTradeLicenseNo());
			updateCashCustomer(hibernateTemplate, policyVO);
			String name = "TRADE_LICENCE_"+quoEndtList.get(0);
			uploadDocumentResponse.setFileName(name);
		}
		return response;
		
	}
	
	public static List<Long> getEndtIdOfQuoTL( PolicyVO policyVO, HibernateTemplate hibernateTemplate,Boolean isQuote  ){
		String sqlQuery = "";
		//Changes-Adv#-10698 -JLT adding pol_issue_hour
		if(isQuote){
			sqlQuery = "select distinct pol_endt_id from t_trn_policy_quo where pol_issue_hour=3 AND pol_quotation_no = "+policyVO.getQuoteNo();
		}else {
			sqlQuery = "select distinct pol_endt_id from t_trn_policy where pol_policy_no = "+policyVO.getPolicyNo();
		}
				
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createSQLQuery( sqlQuery );
		List<Long> resultsFunc = query.list();
		
	    Collections.sort(resultsFunc);
	    Collections.reverse(resultsFunc);
	    session.close();
		return resultsFunc;
	}
	
	public static Long getLinkingIdOfQuo( PolicyVO policyVO, HibernateTemplate hibernateTemplate, Boolean isQuote ){
		
		 
		String sqlQuery = "";
		//Changes-Adv#-10698 -JLT adding pol_issue_hour
		if(isQuote){
			sqlQuery = "select max(pol_linking_id) from t_trn_policy_quo where pol_quotation_no = "+policyVO.getQuoteNo()
			+ " and pol_issue_hour=3 and pol_validity_expiry_date = '31-DEC-2049' and POL_PREPARED_BY="+Integer.parseInt(policyVO.getLoggedInUser().getUserId())+"" ;
		} else {
			sqlQuery = "select max(pol_linking_id) from t_trn_policy where pol_policy_no = "+policyVO.getPolicyNo()
			+ " and pol_validity_expiry_date = '31-DEC-2049' and pol_issue_hour=3 and POL_PREPARED_BY= "+Integer.parseInt(policyVO.getLoggedInUser().getUserId())+"" ;
		}
		Session session =  hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createSQLQuery( sqlQuery );
		Long linkingId = null;
		List<Object> resultsFunc = query.list();
		if( !Utils.isEmpty( resultsFunc ) ){
			if(!Utils.isEmpty( resultsFunc.get( 0 ))){
				linkingId = Long.valueOf( resultsFunc.get( 0 ).toString() );
			}
			
		}
		session.close();
		return linkingId;
	}
	
	private static void updateCashCustomer( HibernateTemplate ht, PolicyVO policyVO ){
		
		String cashCustomerTable = policyVO.getIsQuote() ? CASH_CUSTOMER_QUO : CASH_CUSTOMER;
		String policyQuoTable = policyVO.getIsQuote() ? POLICY_QUO : POLICY;

		String sqlQuery = "update " + cashCustomerTable + " set csh_e_co_regn_no = '" + policyVO.getGeneralInfo().getInsured().getTradeLicenseNo()
				+ "' where csh_policy_id in ( select pol_policy_id from " + policyQuoTable + " polQuo where polQuo.pol_linking_id = " + policyVO.getPolLinkingId()
				+ " and polQuo.pol_validity_expiry_date = '" + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' and polquo.pol_policy_type="
				+ Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ) + " and polQuo.pol_issue_hour = " + Utils.getSingleValueAppConfig( "SBS_POLICY_ISSUE_HOUR" )
				+ " ) and csh_validity_expiry_date= '" + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) + "' ";

		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery( sqlQuery );
		query.executeUpdate();
		session.close();

	}
	
	/**
	 * * This method returns true if the particular file type can be uploaded for particular section.
	 * 
	 * @param extension
	 * @param param
	 * @return 
	 */
	private boolean isAllowedExtn( String extension, String param ){
		boolean isAllowed = false;
		if( Utils.isEmpty( extension ) ){
			return isAllowed;
		}

		String[] extns = Utils.getMultiValueAppConfig( param + "_" + "FILE_UPLOAD_ALLOWED_EXTNS" );

		if( !Utils.isEmpty( extns ) ){
			Utils.trimAllEntries( extns );
			List<String> extnsList = CopyUtils.asList( extns );

			if( extnsList.contains( extension ) ) isAllowed = true;
		}

		return isAllowed;
	}
	
	public static Date getVatLiveDate() {

		String vatStartDate = null;
		Date vatLiveDate = null;

		if (Utils.isEmpty(vatStartDate)) {

			List<Object> resultSetVat = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_VAT_START_DATE);

			if (!Utils.isEmpty(resultSetVat) && resultSetVat.size() > 0) {

				String vatIncDate = null;
				vatIncDate = (String) resultSetVat.get(0);

				if (vatIncDate != null) {
					vatIncDate = formatDt(vatIncDate, "dd-MMM-yyyy", "MM/dd/yyyy");
					vatStartDate = vatIncDate;
				}
			}
			String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy
			try {
				vatLiveDate = new SimpleDateFormat(defaultDateFormat).parse(vatStartDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return vatLiveDate;
	}

	private static String formatDt(String vatIncDate, String srcFormat, String destFormat) {

		String dateStr = null;
		try {
			dateStr = vatIncDate;
			DateFormat srcDf = new SimpleDateFormat(srcFormat);
			// parse the date string into Date object
			Date date = srcDf.parse(dateStr);
			// 11/01/2017
			DateFormat destDf = new SimpleDateFormat(destFormat);
			// format the date into another format
			dateStr = destDf.format(date);
			System.out.println("Converted date is : " + dateStr);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
}
