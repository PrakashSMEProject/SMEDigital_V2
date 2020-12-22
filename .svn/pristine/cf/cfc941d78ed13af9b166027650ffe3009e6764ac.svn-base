package com.rsaame.pas.ui.cmn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReminderVO;
import com.rsaame.pas.vo.bus.TaskVO;

public class AddDairyItemRH implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {


		Response resp = new Response();
		String action = request.getParameter( "action" );
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		ReminderVO reminderVO = new ReminderVO();	
		reminderVO.setPreparedBy(((userProfile)).getRsaUser().getUserId().toString());
		
		String currentDate = null ;
		String  currentTime = null;
		
		if(action.equalsIgnoreCase( "ADD_ITEM" )){
			currentDate = (String)request.getParameter("targetDate");
			currentTime = (String)request.getParameter("targetTime");
			reminderVO.setComments((String)request.getParameter("comments"));
			reminderVO.setSubject((String)request.getParameter("subject"));
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_EMAIL ) ))
				reminderVO.setEmail(converter.getAFromB(request.getParameter( com.Constant.CONST_EMAIL )));
			reminderVO.setEdited(false);
			
		}else if(action.equalsIgnoreCase( "EDIT_ITEM" )){
			reminderVO = (ReminderVO) request.getSession().getAttribute( "reminderObj" );
			currentDate =(String)request.getParameter("targetDateEdit");
			currentTime =(String)request.getParameter("targetTimeEdit");
			reminderVO.setComments((String)request.getParameter("commentsEdit"));
			reminderVO.setSubject((String)request.getParameter("subjectEdit"));
			ReminderVO reminderVO1 = (ReminderVO)(request.getSession().getAttribute( "reminderObj"));
			/*Date preparedDate = new SimpleDateFormat().parse(reminderVO1.getPreparedDate());*/
			reminderVO.setTempPreparedDate(reminderVO1.getPreparedDate());
			/* getPrepared Date will always give prepared date so fetch and use this as furthur down its used to update in query's */
			
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy HH:mm");
			if(reminderVO.getTargetdate().split("/").length >= 1){
				format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			}
			try{
				Date date = format.parse(reminderVO.getTargetdate()+" "+reminderVO.getRemTime());
				reminderVO.setPreparedDate(date);
			}catch(ParseException e){
				e.printStackTrace();
			}
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_EMAIL ) ))
				reminderVO.setEmail(converter.getAFromB(request.getParameter( com.Constant.CONST_EMAIL )));
			else
				reminderVO.setEmail( false );
			reminderVO.setEdited(true);
		}
		if(!Utils.isEmpty( currentTime )){
			currentTime = currentTime.replaceAll("T", "");
		}	
		try{
				/*targetDate = formatTargetDate.parse(date+ " "+time);*/
				reminderVO.setTargetdate(currentDate+ " "+currentTime);
		}catch( Exception e ){

				e.printStackTrace();
		}
		
		reminderVO.setRemStatus(Utils.getSingleValueAppConfig( "REMAINDER_DEFAULT_STATUS"));
		reminderVO.setRemType(Utils.getSingleValueAppConfig( "REMAINDER_DEFAULT_TYPE"));
		reminderVO.setRemTypeId(String.valueOf("0"));
		reminderVO.setRemSrlNo(String.valueOf("0"));
		reminderVO.setPreparedBy(((userProfile)).getRsaUser().getUserId().toString());
		String opType = (String) request.getParameter( "opType" );
		TaskExecutor.executeTasks( opType,reminderVO);
		
		//resp.addErrorKey( "pas.dairy.add.successfull" );
		if( action.equalsIgnoreCase( "ADD_ITEM" ) ){
			AppUtils.addErrorMessage( request, "pas.dairyAdd.successfull" );
		}
		else if( action.equalsIgnoreCase( "EDIT_ITEM" ) ){
			AppUtils.addErrorMessage( request, "pas.dairyUpd.successfull" );
		}

		resp.setSuccess( true );
		return resp;
	}
}
