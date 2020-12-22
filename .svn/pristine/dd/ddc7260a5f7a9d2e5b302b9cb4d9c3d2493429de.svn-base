/**
 * 
 */
package com.rsaame.pas.task.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.TaskListVO;
import com.rsaame.pas.vo.bus.TaskVO;

/**
 * @author m1014644
 *
 */
public class ViewTaskListRH implements IRequestHandler{

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		String action = request.getParameter( "opType" );
		TaskVO taskVO = new TaskVO();

		TaskListVO taskListVO = new TaskListVO();
		taskListVO.setCurrentPage( 1 );

		String taskStatus = request.getParameter( "taskStatus" );
		String taskCategory = request.getParameter( "taskCategory" );
		String taskLob = request.getParameter( "taskLOB" );

		if( Utils.isEmpty( taskStatus ) || taskStatus.equalsIgnoreCase( com.Constant.CONST_SELECT ) ){
			taskVO.setStatus( Utils.getSingleValueAppConfig( "POLICY_STATUS" ) );

		}
		else{
			taskVO.setStatus( taskStatus );

		}

		if( Utils.isEmpty( taskCategory ) || taskCategory.equalsIgnoreCase( com.Constant.CONST_SELECT ) ){
			taskVO.setCategory( Utils.getSingleValueAppConfig( "POLICY_CATEGORY" ) );

		}
		else{
			taskVO.setCategory( taskCategory );
		}
		if( Utils.isEmpty( taskLob ) || taskLob.equalsIgnoreCase( com.Constant.CONST_SELECT ) ){
			taskVO.setLob( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) );
		}
		else{
			taskVO.setLob( taskLob );
		}

		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		if( !Utils.isEmpty( userProfile ) ){
			taskVO.setLoggedInUser( userProfile );
		}

		taskListVO = (TaskListVO) TaskExecutor.executeTasks( action, taskVO );

		Response res = new Response();

		if( !Utils.isEmpty( taskListVO ) ){
			res.setSuccess( true );
			res.setData( taskListVO );
		}
		return res;

	}

}
