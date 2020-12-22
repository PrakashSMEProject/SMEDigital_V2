package com.rsaame.pas.ui.cmn;

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
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.ReminderVO;

public class DeleteDairyItemRH implements IRequestHandler{

	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {

		Response resp = new Response();
		
		ReminderVO reminderVO = new ReminderVO();
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		String date = null ;
		String  time = null;
		try{
			reminderVO = (ReminderVO) request.getSession().getAttribute( "reminderObj" );
			date =(String)request.getParameter("targetDateEdit");
			time =(String)request.getParameter("targetTimeEdit");
			reminderVO.setComments((String)request.getParameter("commentsEdit"));
			reminderVO.setSubject((String)request.getParameter("subjectEdit"));
			ReminderVO reminderVO1 = (ReminderVO)(request.getSession().getAttribute( "reminderObj"));
			/*Date preparedDate = new SimpleDateFormat().parse(reminderVO1.getPreparedDate());*/
			reminderVO.setPreparedDate(reminderVO1.getPreparedDate());
			reminderVO.setEdited(true);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dateTemp = format.parse(date);
			date = new SimpleDateFormat("dd-MMM-yy").format(dateTemp);
			date = date.toUpperCase();
			time = time.replaceAll("T", "");
			reminderVO.setTargetdate(date+ " "+time);
		}catch(Exception e){
			e.printStackTrace();
		}
		reminderVO.setPreparedBy(((userProfile)).getRsaUser().getUserId().toString());
		reminderVO.setRemStatus(Utils.getSingleValueAppConfig( "REMAINDER_DEFAULT_STATUS"));
		reminderVO.setRemType(Utils.getSingleValueAppConfig( "REMAINDER_DEFAULT_TYPE"));
		reminderVO.setRemTypeId(String.valueOf("0"));
		reminderVO.setRemSrlNo(String.valueOf("0"));
		String opType = (String) request.getParameter( "opType" );
		TaskExecutor.executeTasks( opType,reminderVO);
		
		//resp.addErrorKey( "pas.saveSuccessful" );
		AppUtils.addErrorMessage( request, "pas.dairyDelete.successfull" );
		resp.setSuccess( true );
		return resp;
	}

}
