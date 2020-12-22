package com.rsaame.pas.b2b.ws.batch.tasks;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.rsaame.pas.b2b.ws.batch.input.SBSQuoteEventVO;
import com.rsaame.pas.b2b.ws.batch.retriever.InputPolicyVoHelper;
import com.rsaame.pas.dao.model.EplatformWsStaging;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.renewals.ui.ManualJobHandler;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ScheduledJob extends SBSJob implements Job{
	private InputPolicyVoHelper policyVoHelper;

	@Override
	public void trigger(SBSQuoteEventVO event) {
		
		
		
		/*EplatformWsStaging eplatformWsStaging=policyVoHelper.retrievePolicyVO(event.getBatchInput());

			PolicyVO policyVO=formInputPolicyVO(eplatformWsStaging);
			try {
				BaseVO baseVO=createExecutor(event.eventName()).process(policyVO);
				policyVoHelper.savePolicyVO(batchResponse(eplatformWsStaging,baseVO));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}*/
	}

	public InputPolicyVoHelper getPolicyVoHelper() {
		return policyVoHelper;
	}

	public void setPolicyVoHelper(InputPolicyVoHelper policyVoHelper) {
		this.policyVoHelper = policyVoHelper;
	}
	
	public void triggerJob() {
		System.out.println("*******************Triggering from scheduled job************");
		ManualJobHandler handler = new ManualJobHandler();
		PolicyVO policyVO = new PolicyVO();
		List<PolicyVO> policyVOs =null;
		
		policyVOs = DAOUtils.getPolicyVOSFromStaging();
		try {
			for (PolicyVO policyVO1 : policyVOs) {
			
				
				if(policyVO1.getAppFlow().equals(Flow.CREATE_QUO)) {
					handler.triggerBatchJob(policyVO1);
					policyVO= policyVO1; // for temp
					try {
					  Thread.sleep(1000);
					} catch (InterruptedException ex) {}
				}else {
					handler.triggerBatchJobForUpdate(policyVO1);
					policyVO= policyVO1; // for temp
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException("cmn.systemError", null, "Error Accured While Running Async");
		}
		
		
		
		
	}
	

}
