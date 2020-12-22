package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;

public class RefreshCacheRH implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) 
	{
		// TODO Auto-generated method stub
		DataHolderVO<Object[]> input =  new DataHolderVO<Object[]>();
		Object keyData[] = new Object[3];
		String key = request.getParameter("KEY");
		keyData[0] = key;
		/*renInputData[0] = polForRenewal[0].getPolLinkingId();
		renInputData[1] = ServiceContext.getUser().getUserId();
        renInputData[2] = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		input.setData(renInputData);
		*/
		input.setData(keyData);
		//DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "REFRESH_CACHE", input );
		TaskExecutor.executeTasks( "REFRESH_CACHE", input );
		
		Response responseObj = new Response();
		return responseObj;
	}

}
