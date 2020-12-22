package com.rsaame.pas.tasks.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.tasks.dao.ITaskDAO;

public class TaskSvc extends BaseService{

	ITaskDAO taskDAO; 
	
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "getViewTaskList".equals( methodName ) ){
			returnValue = getTaskListSvc( (BaseVO) args[ 0 ] );
		}
		if( "getTaskDetailsSvc".equals( methodName ) ){
			returnValue = getTaskDetailsSvc( (BaseVO) args[ 0 ] );
		}
		if( "saveTaskDetailsSvc".equals( methodName ) ){
			returnValue = saveTaskDetailsSvc( (BaseVO) args[ 0 ] );
		}
		if( "updateTaskDetailsSvc".equals( methodName ) ){
			returnValue = updateTaskDetailsSvc( (BaseVO) args[ 0 ] );
		}
		if( "getReferalList".equals( methodName ) ){
			returnValue = getReferalList( (BaseVO) args[ 0 ] );
		}
		if( "saveOrUpdateTaskSvc".equals( methodName ) ){
			returnValue = saveOrUpdateTaskSvc( (BaseVO) args[ 0 ] );
		}
		if( "getTaskStatusUser".equals( methodName ) ){
			returnValue = getTaskStatusUser( (BaseVO) args[ 0 ] );
		}
		/** PHASE-3 Referral Flow Start */
		if("saveRefferalTask".equals( methodName )) {
			returnValue = saveConsolidatedReferral((BaseVO) args[ 0 ]);
		}
		/**Monoline Populating TaskVO */
		if("populateReferralTaskDets".equals( methodName )) {
			returnValue = populateReferralTaskDets((BaseVO) args[ 0 ]);
		}
		
		/** PHASE-3 End Flow End*/
		return returnValue;
	}

	private BaseVO populateReferralTaskDets(BaseVO baseVO) {
		return taskDAO.populateReferralTaskDets(baseVO);
	}

	private BaseVO getTaskStatusUser(BaseVO baseVO) {
		return taskDAO.getTaskStatusUser(baseVO);
	}

	private BaseVO updateTaskDetailsSvc(BaseVO baseVO) {
		
		// TODO Auto-generated method stub
		return null;
	}
	private BaseVO saveOrUpdateTaskSvc(BaseVO baseVO) {
		return taskDAO.saveOrUpdateTaskDetails(baseVO);
	}
	private BaseVO saveTaskDetailsSvc(BaseVO baseVO) {
		
		return taskDAO.saveRefTskDetails(baseVO);
	}

	private BaseVO getTaskDetailsSvc(BaseVO baseVO) {
		return taskDAO.getTaskDetails( baseVO );
	}

	private BaseVO getTaskListSvc(BaseVO baseVO) {
		
		return taskDAO.getViewTaskList(baseVO);
	}
	
	private BaseVO getReferalList(BaseVO baseVO)
	{
		return taskDAO.getReferalList( baseVO );
	}
	
	public void setTaskDAO( ITaskDAO taskDAO ){
		this.taskDAO = taskDAO;
	}
	
	/** PHASE-3 Start */
	private BaseVO saveConsolidatedReferral(BaseVO baseVO) {
		return taskDAO.saveConsolidatedReferral(baseVO);
	}
	/** PHASE-3 End */
}
