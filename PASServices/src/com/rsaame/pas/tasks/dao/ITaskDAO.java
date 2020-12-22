package com.rsaame.pas.tasks.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface ITaskDAO{

	public abstract BaseVO getViewTaskList( BaseVO baseVO );

	public abstract BaseVO getTaskDetails( BaseVO baseVO );

	public abstract BaseVO getReferalList( BaseVO baseVO );
	
	public abstract BaseVO saveRefTskDetails( BaseVO baseVO );

	public abstract BaseVO saveOrUpdateTaskDetails(BaseVO baseVO);

	public abstract BaseVO getTaskStatusUser(BaseVO baseVO);
	
	/** PHASE-3 Start */
	public abstract BaseVO saveConsolidatedReferral(BaseVO baseVO);
	/** PHASE-3 End */

	public abstract BaseVO populateReferralTaskDets(BaseVO baseVO);
}