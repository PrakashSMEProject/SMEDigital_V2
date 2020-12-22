package com.rsaame.pas.b2b.ws.batch.tasks;

import java.util.Map;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.b2b.ws.batch.input.SBSQuoteEventVO;
import com.rsaame.pas.b2b.ws.batch.retriever.InputPolicyVoHelper;
import com.rsaame.pas.dao.model.EplatformWsStaging;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ManualJob extends SBSJob implements Job {
	private final static Logger LOGGER = Logger.getLogger(AsyncJob.class);
	private static final String DELETE_QUOTE = "DELETE_QUOTE";
	private static final String UPDATE_QUOTE = "UPDATE_QUOTE";
	private static final String CREATE_QUOTE = "CREATE_QUOTE";
	private InputPolicyVoHelper policyVoHelper;

	@Override
	public void trigger(SBSQuoteEventVO event) {
		BaseVO baseVO=null;
		EplatformWsStaging eplatformWsStaging=null;
		if(CREATE_QUOTE.equalsIgnoreCase(event.eventName())){
			eplatformWsStaging=policyVoHelper.retrievePolicyVO(event.getBatchInput());
			PolicyVO policyVO=formInputPolicyVO(eplatformWsStaging);
			try {
					baseVO=createExecutor(event.eventName()).process(policyVO);	
					policyVoHelper.savePolicyVO(batchResponse(eplatformWsStaging,baseVO));	
					LOGGER.info("Create Quote Completed sucessfully");
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else if(UPDATE_QUOTE.equalsIgnoreCase(event.eventName())) {
			Map<Long, EplatformWsStaging> stgBucket=policyVoHelper.retrieveCurrentAndPrevious(event.getBatchInput());
			PolicyVO policyVOToBeDeleted=sectionsToBeDeleted(stgBucket);
			PolicyVO policyVOToUpdated=sectionsToBeUpdated(stgBucket);
			try {
				baseVO=createExecutor(DELETE_QUOTE).process(policyVOToBeDeleted);
				baseVO=createExecutor(UPDATE_QUOTE).process(policyVOToUpdated);
				eplatformWsStaging=maxEndorsementRecord(stgBucket);
				policyVoHelper.savePolicyVO(batchResponse(eplatformWsStaging ,baseVO));
				LOGGER.info("Update Quote Completed sucessfully");
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}			
		}
		LOGGER.info("**** Saved from Staging Table to Quotation Transaction tables for [ QuoteNo=" + eplatformWsStaging.getPolQuotationNo() +" : EndtID=" + eplatformWsStaging.getId().getPolEndtId() + " : PolicyID=" + eplatformWsStaging.getId().getPolPolicyId() + " : PolLinkingId=" + eplatformWsStaging.getPolLinkingId() + " : Status=" + eplatformWsStaging.getPolStatus() + "] ****");
	}

	public InputPolicyVoHelper getPolicyVoHelper() {
		return policyVoHelper;
	}

	public void setPolicyVoHelper(InputPolicyVoHelper policyVoHelper) {
		this.policyVoHelper = policyVoHelper;
	}
	
	

}
