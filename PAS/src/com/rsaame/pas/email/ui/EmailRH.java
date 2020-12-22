package com.rsaame.pas.email.ui;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.PolicyDetailsHolder;
import com.rsaame.pas.vo.app.PolicyDetailsVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.CreditNoteDetailsVO;
import com.rsaame.pas.vo.bus.DebitNoteDetailsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReceiptDetailsVO;

public class EmailRH implements IRequestHandler {
	private final static Logger logger = Logger.getLogger(EmailRH.class);

	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse response) {
		Response responseObj = new Response();
		request.setAttribute(com.Constant.CONST_MAILTYPE, request.getParameter(com.Constant.CONST_MAILTYPE));

		String files = request.getParameter("filenames");
		String mailType = request.getParameter(com.Constant.CONST_MAILTYPE);
		String isPolicyScheduleClausesUAE = request.getParameter("clauseParam");

		request.setAttribute("fileNames", files);
		if (!Utils.isEmpty(mailType)) {

			// START: MAIL_PROCESS_EMAIL_QUOTE Document configuration
			if (mailType.equals((SvcConstants.MAIL_PROCESS_EMAIL_QUOTE))) {
				logger.debug("mailType:"+ SvcConstants.MAIL_PROCESS_EMAIL_QUOTE);
				Format format;
				String identifier = "ISSUE_QUOTE_PROPOSAL_FORM";

				PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
				PolicyVO policyVO = policyContext.getPolicyDetails();
				BaseVO baseVO = TaskExecutor.executeTasks(identifier, policyVO);
				DataHolderVO<Object[]> data = (DataHolderVO<Object[]>) baseVO;
				Object quoteDetails[] = data.getData();

				format = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
				Date date = (Date) quoteDetails[2];

				String sdate = format.format(date);

				// DataHolderVO <HashMap <String,String>> reportDetails= new
				// DataHolderVO <HashMap <String,String>> ();
				HashMap<String, String> reportParams = new HashMap<String, String>();
				reportParams.put("polLinkingId", quoteDetails[0].toString());
				logger.debug("polLinkingId:" + quoteDetails[0].toString());
				reportParams.put("endoresementId", quoteDetails[1].toString());
				logger.debug("endoresementId:" + quoteDetails[1].toString());
				reportParams.put("polValStartDate", sdate);
				logger.debug("polValStartDate:" + sdate);
				reportParams.put("language", "E");
				reportParams.put(com.Constant.CONST_LOCATIONCODE, Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
				// FGB changes
				if( (!Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() )
						&& policyVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_CODE" ) ))
						|| (!Utils.isEmpty( policyVO.getScheme()  )  && policyVO.getScheme().getSchemeCode().toString().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_DIRECT_SCHEME_CODE" )))){
					reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._FGB_SBS.toString() );
				}
				else{
					reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._SBS.toString() );
				}

				HttpSession session = request.getSession();
				session.setAttribute("reportParams", reportParams);
			}// END: MAIL_PROCESS_EMAIL_QUOTE Document configuration
				// START: MAIL_PROCESS_CONVERT_TO_POLICY Document configuration
			else if (mailType.equals((SvcConstants.MAIL_PROCESS_CONVERT_TO_POLICY))) {
				logger.debug("mailType:" + SvcConstants.MAIL_PROCESS_CONVERT_TO_POLICY);
				Format format;

				PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
				PolicyVO policyVO = policyContext.getPolicyDetails();
				HashMap<String, String> reportParams = new HashMap<String, String>();

				String policyLinkingId = "";
				format = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
				String sdate = "";

				String identifier = "PRINT_POLICY_DOC";

				PolicyDetailsVO policyDtlVO = new PolicyDetailsVO();
				policyDtlVO.setPolicyNo(policyVO.getPolicyNo());
				// policyDtlVO.setEndtId((policyVO.getEndtId()).toString());

				policyDtlVO.setEndtId(SvcUtils.getLatestEndtId(policyVO).toString());
				//Needed to differentiate between renewal policy and original policy
				String expDate = SvcUtils.getYearFromDate(policyVO.getPolExpiryDate())+"-"+SvcUtils.getMonthFromDate(policyVO.getPolExpiryDate())+"-"+SvcUtils.getDayFromDate(policyVO.getPolExpiryDate());
				policyDtlVO.setPolExpiryDate( expDate);
				
				policyDtlVO.setPolConcPolicyNo( policyVO.getConcatPolicyNo().toString() );

				logger.debug("ConcatPolicyNumber:" + policyVO.getConcatPolicyNo().toString());

				BaseVO baseVO = TaskExecutor.executeTasks(identifier,policyDtlVO);
				PolicyDetailsHolder policyDtls = (PolicyDetailsHolder) baseVO;
				List<PolicyDetailsVO> policyList = policyDtls.getPolicyDtlList();

				policyLinkingId = policyList.get(policyList.size() - 1).getPolicyLinkingId();
				sdate = policyList.get(policyList.size() - 1).getStartDate();
// FGB changes
				if( !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() )
						&& policyVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_CODE" ) ) 
						|| (!Utils.isEmpty( policyVO.getScheme()  )  && policyVO.getScheme().getSchemeCode().toString().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_DIRECT_SCHEME_CODE" )))){
					reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._FGB_SBS.toString() );
				}
				else{
					reportParams.put( com.Constant.CONST_REPORTTEMPLATESTYPE, ReportTemplateSet._SBS.toString() );
				}
				reportParams.put("polLinkingId", policyLinkingId);
				
				if (!Utils.isEmpty((policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT: SvcUtils.getLatestEndtId(policyVO)).toString())) {
				     reportParams.put("endorsementId",(policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT	: SvcUtils.getLatestEndtId(policyVO)).toString());
					 logger.debug("endoresementId:"	+ ((policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : SvcUtils.getLatestEndtId(policyVO)).toString()));
				} 
				reportParams.put("validityStartDate", sdate);
				logger.debug("validityStartDate:" + sdate);
				reportParams.put("language", "E");
				//added for abudhabi/baharain
				reportParams.put(com.Constant.CONST_LOCATIONCODE, Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));

				StringTokenizer st = new StringTokenizer(files, ",");
				// String [] fileNames=new String[st.countTokens()];
				int numbDocs = st.countTokens();
				String isPolicySchedule = com.Constant.CONST_FALSE;
				String isCreditNote = com.Constant.CONST_FALSE;
				String isGrossCreditNote = com.Constant.CONST_FALSE;
				String isDebitNote = com.Constant.CONST_FALSE;
				String isGrossDebitNote = com.Constant.CONST_FALSE;
				String isRecipt = com.Constant.CONST_FALSE;
				String isEndtSchedule = com.Constant.CONST_FALSE;

				// START: Report/File Specific for loop
				for (int i = 0; i < numbDocs; i++) {
					String fileName = st.nextToken();
					if (fileName.equals("policyScheduleUAE")) {

						isPolicySchedule = "true";
				} 
				else if (fileName.equals("endScheduleUAE")) {

						isEndtSchedule = "true";

					}
					 else if (fileName.equals("printReceipt")) {
						identifier = "RECEIPT_DOC";
						CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
						DataHolderVO<Long> policyIdHolder = null;
						ReceiptDetailsVO rcptDetsVO = new ReceiptDetailsVO();
						
						policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( com.Constant.CONST_GETPOLICYIDFORPOLICY, policyVO );
						Long policyId = null;
						
						if(!Utils.isEmpty( policyIdHolder ) && !Utils.isEmpty( policyIdHolder.getData() )){
							policyId = policyIdHolder.getData();
							rcptDetsVO.setRcdPolicyId( policyId );
						}
						
						rcptDetsVO.setRcdPolicyNo(policyVO.getPolicyNo());
						rcptDetsVO.setRcdEndtId((policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : SvcUtils.getLatestEndtId(policyVO)));

						BaseVO resultVO = TaskExecutor.executeTasks(identifier,	rcptDetsVO);
						if (!Utils.isEmpty(resultVO)) {
							rcptDetsVO = (ReceiptDetailsVO) resultVO;
							if (!Utils.isEmpty(rcptDetsVO.getRcdReceiptNo())) {
								reportParams.put("receiptNo", rcptDetsVO.getRcdReceiptNo().toString());
								logger.debug("receiptNo:" + rcptDetsVO.getRcdReceiptNo().toString());
							}
							if (!Utils.isEmpty(rcptDetsVO.getRcdReceiptDate())) {

								String reciptDateString = rcptDetsVO.getRcdReceiptDate();
								Date reciptDate = convertStringToDate(reciptDateString);
								format = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
								String receiptDate = format.format(reciptDate);
								reportParams.put("receiptDate", receiptDate);
								logger.debug("receiptDate:" + receiptDate);
							}
						}

						isRecipt = "true";

					}
					 else if (fileName.equals("creditNote") || fileName.equals( "grossCreditNote" )) {
						identifier = "CREDIT_NOTE_DOC";
						CreditNoteDetailsVO crDetsVO = new CreditNoteDetailsVO();
						crDetsVO.setCndPolicyNo(policyVO.getPolicyNo());
						crDetsVO.setCndEndtId((policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT	: SvcUtils.getLatestEndtId(policyVO)));
						crDetsVO.setCndPolicyYear( SvcUtils.getYearFromDate( policyVO.getScheme().getEffDate() ));
						DataHolderVO<Long> policyIdHolder = null;
						CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
						policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( com.Constant.CONST_GETPOLICYIDFORPOLICY, policyVO );
						
						if(!Utils.isEmpty( policyIdHolder ) && !Utils.isEmpty( policyIdHolder.getData() )){
							crDetsVO.setCndPolicyId(policyIdHolder.getData());
						}
						
						BaseVO resultVO = TaskExecutor.executeTasks(identifier,	crDetsVO);
						if (!Utils.isEmpty(resultVO)) {
							crDetsVO = (CreditNoteDetailsVO) resultVO;
							if (!Utils.isEmpty(crDetsVO.getCndCreditNoteNo())) {
								reportParams.put("creditNoteNo", crDetsVO.getCndCreditNoteNo().toString());
								logger.debug("creditNoteNo:" + crDetsVO.getCndCreditNoteNo().toString());
							}
							if (!Utils.isEmpty(crDetsVO.getCndCreditNoteDate())) {

								String creditNoteDateString = crDetsVO.getCndCreditNoteDate();
								Date creditNoteDate = convertStringToDate(creditNoteDateString);
								format = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
								String credNoteDate = format.format(creditNoteDate);
								
								reportParams.put("creditNoteDate", credNoteDate);
								logger.debug("creditNoteDate:" + credNoteDate);
							}
						}
						
						if(fileName.equals("creditNote")){
							isCreditNote = "true";
						}
						else{
							isGrossCreditNote = "true";
						}
						
						/*
						 * Changed to send mail for gross debit note
						 */
					}
					 else if (fileName.equals("debitNote") || fileName.equals("grossDebitNote")) {
						identifier = "DEBIT_NOTE_DOC";
						DebitNoteDetailsVO drNoteDetsVO = new DebitNoteDetailsVO();
						drNoteDetsVO.setDndPolicyNo(policyVO.getPolicyNo());
						DataHolderVO<Long> policyIdHolder = null;
						/*
						* AMS - Fix for release 2.1
						*/
						drNoteDetsVO.setDndEndtId((policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT	: SvcUtils.getLatestEndtId(policyVO)));
						drNoteDetsVO.setDndPolicyYear( SvcUtils.getYearFromDate( policyVO.getScheme().getEffDate()) );
						
						CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
						policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( com.Constant.CONST_GETPOLICYIDFORPOLICY, policyVO );
						
						if(!Utils.isEmpty( policyIdHolder ) && !Utils.isEmpty( policyIdHolder.getData() )){
							drNoteDetsVO.setDndPolicyId(  policyIdHolder.getData());
						}
						
						BaseVO resultVO = TaskExecutor.executeTasks(identifier,	drNoteDetsVO);

						if (!Utils.isEmpty(resultVO)) {
							drNoteDetsVO = (DebitNoteDetailsVO) resultVO;
							if (!Utils.isEmpty(drNoteDetsVO.getDndDebitNoteNo())) {
							reportParams.put("debitNoteNo", drNoteDetsVO.getDndDebitNoteNo().toString());
							logger.debug("debitNoteNo:"	+ drNoteDetsVO.getDndDebitNoteNo().toString());
							}
							if (!Utils.isEmpty(drNoteDetsVO.getDndDebitNoteDate())) {

								String debitNoteDateString = drNoteDetsVO.getDndDebitNoteDate();
								Date creditNoteDate = convertStringToDate(debitNoteDateString);
								format = new SimpleDateFormat(com.Constant.CONST_DD_MMM_YYYY);
								String debitNoteDate = format.format(creditNoteDate);
							
								reportParams.put("debitNoteDate", debitNoteDate);
								logger.debug("debitNoteDate:" + debitNoteDate);
							}
						}
						
						if(fileName.equals("debitNote")){
							isDebitNote = "true";
						}
						else{
							isGrossDebitNote = "true";
						}
						
					} 
					else if (fileName.equals("freeZone")) {
						// TODO if any special parameters required add to
						// reportParams here
						logger.debug("file name"+fileName);
					}

				}// END: Report/File Specific for loop

				reportParams.put("CreditNoteReport", isCreditNote);
				reportParams.put("GrossCreditNoteReport", isGrossCreditNote);
				reportParams.put("DebitNoteReport", isDebitNote);
				reportParams.put("GrossDebitNoteReport", isGrossDebitNote);
				reportParams.put("Receipt", isRecipt);
				reportParams.put("PolicySchedule", isPolicySchedule);
				reportParams.put("EndScheduleReport", isEndtSchedule);
				reportParams.put("policyScheduleClauses",isPolicyScheduleClausesUAE);
				//added for abudhabi/baharain
				reportParams.put(com.Constant.CONST_LOCATIONCODE, Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION));
				HttpSession session = request.getSession();
				session.setAttribute("reportParams", reportParams);

			} // END: MAIL_PROCESS_CONVERT_TO_POLICY Document configuration

		}

		return responseObj;
	}

	private Date convertStringToDate(String dateString) {

		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat("dd/MMM/yyyy");
		try {
			date = (Date) formatter.parse(dateString);
		}	
		 catch (ParseException e) {
			BusinessException businessExcp = new BusinessException(	"mail.error", null, e.getMessage());
			throw businessExcp;

		}

		return date;
	}

}
