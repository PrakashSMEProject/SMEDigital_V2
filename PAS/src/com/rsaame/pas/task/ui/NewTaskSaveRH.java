package com.rsaame.pas.task.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;

import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.TaskVO;

public class NewTaskSaveRH implements IRequestHandler {

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String action = request.getParameter("action");
		Response res = new Response();
		System.out.println("In side RH save");
		
		TaskVO taskVO = BeanMapper.map( request, TaskVO.class );
		// Oman multibranching implementation
		taskVO.setLocation(PASServiceContext.getLocation());
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if(!Utils.isEmpty(userProfile)){
			taskVO.setLoggedInUser(userProfile);	
		}
		TaskExecutor.executeTasks( "NEW_TASK_SAVE", taskVO );
		System.out.println(taskVO.getDueDate());
		
		TaskExecutor.executeTasks( "SAVE_OR_UPDATE_TASK_INSVC", taskVO );
		if(!Utils.isEmpty(action))
		{
			if(action.equalsIgnoreCase("NEW_TASK"))
			{
				AppUtils.addErrorMessage( request, "task.new.save.success" );
			}
			else
			{
				AppUtils.addErrorMessage( request, "task.edit.save.success" );
			}
		}
		System.out.println(taskVO.getDesc());
		return res;
	
	}
}