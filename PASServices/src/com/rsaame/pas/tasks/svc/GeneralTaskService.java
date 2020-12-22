package com.rsaame.pas.tasks.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TaskVO;

public class GeneralTaskService extends BaseService{
	
	private PasReferralSaveCommonSvc pasReferralSaveCmnSvc; /* Referral Service */
	private TaskSvc taskSvc; /* Task service */

	/**
	 * @param pasReferralSaveCmnSvc the pasReferralSaveCmnSvc to set
	 */
	public void setPasReferralSaveCmnSvc( PasReferralSaveCommonSvc pasReferralSaveCmnSvc ){
		this.pasReferralSaveCmnSvc = pasReferralSaveCmnSvc;
	}

	/**
	 * @param taskSvc the taskSvc to set
	 */
	public void setTaskSvc( TaskSvc taskSvc ){
		this.taskSvc = taskSvc;
	}

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "saveRefAndTask".equals( methodName ) ){
			returnValue = saveRefAndTask( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	private BaseVO saveRefAndTask( BaseVO baseVO ){
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;

		policyDataVO.getReferralVOList().getReferrals().get( 0 ).setConsolidated( true );

		TaskVO taskVO = policyDataVO.getReferralVOList().getTaskVO();

		//Radar fix
		//TTrnPasReferralDetails pasReferralDetails = (TTrnPasReferralDetails) 
		pasReferralSaveCmnSvc.invokeMethod( "saveReferralData", policyDataVO );
		taskVO.setDesc( DAOUtils.getTaskDescription( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() ) );
		return (BaseVO) taskSvc.invokeMethod( "saveRefferalTask", taskVO );
	}

}
