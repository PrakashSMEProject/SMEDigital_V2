package com.rsaame.pas.ui.cmn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.ReminderListVO;
import com.rsaame.pas.vo.bus.ReminderVO;

public class FetchLapsedDairyItemsRH implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Response resp = new Response();
		ReminderVO reminderVO = new ReminderVO();
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		reminderVO.setPreparedBy(((userProfile)).getRsaUser().getUserId().toString());
		reminderVO.setLapsedDairyItems(true);
		reminderVO.setRemType(Utils.getSingleValueAppConfig( "REMAINDER_DEFAULT_TYPE"));
		reminderVO.setRemSrlNo(String.valueOf("0"));
		reminderVO.setRemTypeId(String.valueOf("1"));
		
		/* Fetching Dairy Items based on due date(target date) i.e current date */
		String action = (String) request.getParameter( "opType" );
		ReminderListVO result = (ReminderListVO) TaskExecutor.executeTasks( action,reminderVO);
		request.setAttribute("dairyItemsList", result);
		resp.setData(result);
		return resp;

	}

}
