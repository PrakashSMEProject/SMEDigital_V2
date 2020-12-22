package com.rsaame.pas.b2b.ws.batch.tasks;


import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.b2b.ws.batch.input.SBSQuoteEventVO;
import com.rsaame.pas.b2b.ws.batch.retriever.InputPolicyVoHelper;
import com.rsaame.pas.b2b.ws.dao.vo.EplatformWsStaging;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.handler.WebServiceAuditHandler;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ScheduledJob extends SBSJob implements Job{
	private InputPolicyVoHelper policyVoHelper;

	@Override
	public void trigger(SBSQuoteEventVO event) {
		EplatformWsStaging eplatformWsStaging=policyVoHelper.retrievePolicyVO(event.getBatchInput());

			PolicyVO policyVO=formInputPolicyVO(eplatformWsStaging);
			WebServiceAuditHandler webServiceAuditHandler = new WebServiceAuditHandler();
			try {
				BaseVO baseVO=createExecutor(event.eventName()).process(policyVO);
				policyVoHelper.savePolicyVO(batchResponse(eplatformWsStaging,baseVO));
			} catch (Exception e) {
					WSDAOUtils.invalidateRecord(policyVO);
					eplatformWsStaging.setBatchStatus(new Integer(0).byteValue());
					policyVoHelper.savePolicyVO(eplatformWsStaging);
					e.printStackTrace();
			}
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
				}
				else {
					if(webServiceAudit!=null) {
						webServiceAudit.setTwa_transaction_res_type("Success");
						webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
					}
				}
			
			}
	}

	public InputPolicyVoHelper getPolicyVoHelper() {
		return policyVoHelper;
	}

	public void setPolicyVoHelper(InputPolicyVoHelper policyVoHelper) {
		this.policyVoHelper = policyVoHelper;
	}
	
	public void triggerJob() {
		System.out.println("*******************Triggering from scheduled job************");
		//trigger(null);
	}
	

}
