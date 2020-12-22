package com.rsaame.pas.task.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.TaskVO;

public class ViewTaskDetailRH implements IRequestHandler{

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response res = new Response();

		com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
		Long taskID = converter.getTypeOfA().cast( converter.getAFromB( request.getParameter( "value" ) ) );
		// Oman multibranching implementation. Set task location in service context
		String branch = request.getParameter( "branch" );
		PASServiceContext.setLocation( branch );
		request.getSession().setAttribute( AppConstants.CTX_LOCATION, branch );

		if( Utils.isEmpty( taskID ) ){
			throw new BusinessException( "task.taskId.Mandatory", null, "Task ID not present in the request" );
		}
		TaskVO task = new TaskVO();
		task.setTaskID( taskID );
		task.setLob( request.getParameter( "lobValue" ) );

		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		if( !Utils.isEmpty( userProfile ) ){
			task.setLoggedInUser( userProfile );
		}
		task = (TaskVO) TaskExecutor.executeTasks( "VIEW_TASK_DETAIL", task );

		request.setAttribute( "TaskDetails", task );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, "RESOLVE_REF" );
		request.setAttribute( AppConstants.SCREEN_NAME, "VIEW_TASK_DETAILS" );
		return res;

	}

}
