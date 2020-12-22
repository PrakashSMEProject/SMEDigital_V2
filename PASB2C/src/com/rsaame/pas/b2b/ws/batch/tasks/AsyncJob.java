package com.rsaame.pas.b2b.ws.batch.tasks;


import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.batch.input.SBSQuoteEventVO;
import com.rsaame.pas.b2b.ws.batch.retriever.InputPolicyVoHelper;
import com.rsaame.pas.b2b.ws.dao.vo.EplatformWsStaging;
import com.rsaame.pas.b2b.ws.handler.EmailPolicyCreationRH;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.handler.WebServiceAuditHandler;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.vo.bus.PolicyVO;

public class AsyncJob extends SBSJob implements Job {
	
	private final static Logger LOGGER = Logger.getLogger(AsyncJob.class);
	private static final String DELETE_QUOTE = "DELETE_QUOTE";
	private static final String UPDATE_QUOTE = "UPDATE_QUOTE";
	private static final String CREATE_QUOTE = "CREATE_QUOTE";
	private static final String CONVERT_TO_POLICY = "CONVERT_TO_POLICY";

	private InputPolicyVoHelper policyVoHelper;

	@Override
	public void trigger(SBSQuoteEventVO event) {
		BaseVO baseVO=null;
		int status=0;
		EplatformWsStaging eplatformWsStaging=null;
		
		if(CREATE_QUOTE.equalsIgnoreCase(event.eventName())){
			eplatformWsStaging=policyVoHelper.retrievePolicyVO(event.getBatchInput());
			PolicyVO policyVO=formInputPolicyVO(eplatformWsStaging);
			WebServiceAuditHandler webServiceAuditHandler = new WebServiceAuditHandler();
			List<TTrnPolicyQuo> staging = WSDAOUtils.getPolicyRecord(policyVO);
			try {
					baseVO=createExecutor(event.eventName()).process(policyVO);	
					policyVoHelper.savePolicyVO(batchResponse(eplatformWsStaging,baseVO));	
					LOGGER.info("Create Quote Completed sucessfully");
				} catch (Exception e) {
						WSDAOUtils.invalidateRecord(policyVO);
						eplatformWsStaging.setBatchStatus(new Integer(0).byteValue());
						policyVoHelper.savePolicyVO(eplatformWsStaging);
						e.printStackTrace();
				}
			//m1043116-added to trigger mail when async fails
			finally
			{
				WebServiceAudit webServiceAudit = WSDAOUtils.getRecordFromAuditTable(policyVO.getQuoteNo(),"In progress");
				EplatformWsStaging staging1 = WSDAOUtils.getPolicyRecordFromStaging(eplatformWsStaging.getPolQuotationNo(),eplatformWsStaging.getId().getPolEndtId());
				if(staging1.getBatchStatus()==0)
				{	
					if(webServiceAudit!=null) {
						webServiceAudit.setTwa_transaction_res_type("Batch failed");
						webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
					}
					
					try {
						CommonHandler.asyncFailEmail(policyVO);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					if(webServiceAudit!=null) {
						webServiceAudit.setTwa_transaction_res_type("Success");
						webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
					}
					
				}
				
				}
			LOGGER.info("**** Saved from Staging Table to Quotation Transaction tables for [ QuoteNo=" + eplatformWsStaging.getPolQuotationNo() +" : EndtID=" + eplatformWsStaging.getId().getPolEndtId() + " : PolicyID=" + eplatformWsStaging.getId().getPolPolicyId() + " : PolLinkingId=" + eplatformWsStaging.getPolLinkingId() + " : Status=" + eplatformWsStaging.getPolStatus() + "] ****");

		}
		else if(UPDATE_QUOTE.equalsIgnoreCase(event.eventName())) {
			Map<Long, EplatformWsStaging> stgBucket=policyVoHelper.retrieveCurrentAndPrevious(event.getBatchInput());
			PolicyVO policyVOToBeDeleted=sectionsToBeDeleted(stgBucket);
			PolicyVO policyVOToUpdated=sectionsToBeUpdated(stgBucket);
			WebServiceAuditHandler webServiceAuditHandler = new WebServiceAuditHandler();
			try {
				baseVO=createExecutor(DELETE_QUOTE).process(policyVOToBeDeleted);
				baseVO=createExecutor(UPDATE_QUOTE).process(policyVOToUpdated);
				eplatformWsStaging=maxEndorsementRecord(stgBucket);
				policyVoHelper.savePolicyVO(batchResponse(eplatformWsStaging ,baseVO));
				LOGGER.info("Update Quote Completed sucessfully");
				} catch (Exception e) {
						WSDAOUtils.invalidateRecord(policyVOToUpdated);
						eplatformWsStaging.setBatchStatus(new Integer(0).byteValue());
						policyVoHelper.savePolicyVO(eplatformWsStaging);
						e.printStackTrace();
				}	
			//m1043116-added to trigger mail when async fails
			finally
			{
				WebServiceAudit webServiceAudit = WSDAOUtils.getRecordFromAuditTable(policyVOToUpdated.getQuoteNo(),"In progress");
				EplatformWsStaging staging1 = WSDAOUtils.getPolicyRecordFromStaging(eplatformWsStaging.getPolQuotationNo(),eplatformWsStaging.getId().getPolEndtId());
				if(staging1.getBatchStatus()==0)
				{
					if(webServiceAudit!=null) {
						webServiceAudit.setTwa_transaction_res_type("Batch failed");
						webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
					}
					
					try {
						CommonHandler.asyncFailEmail(policyVOToUpdated);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					if(webServiceAudit!=null) {
						webServiceAudit.setTwa_transaction_res_type("Success");
						webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
					}
				}
				
			}
			LOGGER.info("**** Saved from Staging Table to Quotation Transaction tables for [ QuoteNo=" + eplatformWsStaging.getPolQuotationNo() +" : EndtID=" + eplatformWsStaging.getId().getPolEndtId() + " : PolicyID=" + eplatformWsStaging.getId().getPolPolicyId() + " : PolLinkingId=" + eplatformWsStaging.getPolLinkingId() + " : Status=" + eplatformWsStaging.getPolStatus() + "] ****");

		}

		//added to send mail in async after CTP
		else if(CONVERT_TO_POLICY.equalsIgnoreCase(event.eventName())) {
			
			
			boolean isAttachment=event.getBatchInput().getisAttachment();
			CreateSBSPolicyResponse createSBSPolicyResponse=event.getBatchInput().getCreateSBSPolicyResponse();
			DataHolderVO<List<BaseVO>> dataHolderVO=event.getBatchInput().getdataHolderVO();
			EmailPolicyCreationRH emailAfterPolicyCreation = new EmailPolicyCreationRH();
			emailAfterPolicyCreation.emailToCustomerForPolicyCreation(dataHolderVO, isAttachment, createSBSPolicyResponse);
			
			}
		
						

	}

	public InputPolicyVoHelper getPolicyVoHelper() {
		return policyVoHelper;
	}

	public void setPolicyVoHelper(InputPolicyVoHelper policyVoHelper) {
		this.policyVoHelper = policyVoHelper;
	}
	

}
