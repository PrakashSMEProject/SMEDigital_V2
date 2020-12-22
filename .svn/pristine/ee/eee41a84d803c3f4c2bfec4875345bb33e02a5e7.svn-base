package com.rsaame.pas.ui.cmn;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.bus.ReminderVO;

public class ViewMyDairyItemRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		
		Response res = new Response();
		Redirection redirection ;

		String action = request.getParameter( "action" );
		
		if(action.equalsIgnoreCase( "EDIT_ITEM" )||action.equalsIgnoreCase( "LAPSED_ITEM" )){
			
		ReminderVO reminderVO = new ReminderVO();
		String dateString = "";
		
		if(!Utils.isEmpty((String)request.getParameter("forReset"))){
			reminderVO.setRemTime( ((ReminderVO)request.getSession().getAttribute( com.Constant.CONST_REMINDEROBJ)).getRemTime() );
			reminderVO.setTargetdate( ((ReminderVO)request.getSession().getAttribute( com.Constant.CONST_REMINDEROBJ)).getTargetdate() );
			reminderVO.setComments( ((ReminderVO)request.getSession().getAttribute( com.Constant.CONST_REMINDEROBJ)).getComments() );
			reminderVO.setSubject( ((ReminderVO)request.getSession().getAttribute( com.Constant.CONST_REMINDEROBJ)).getSubject() );
		}else
		{
			reminderVO.setRemTime( request.getParameter( "remTime" ) );
			dateString = request.getParameter( "targetdate" );
			reminderVO.setTargetdate( dateString );
			reminderVO.setComments( request.getParameter( "comment" ) );
			reminderVO.setSubject( request.getParameter( "subject" )  );
			
		}	
		

		com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_EMAILREQ ) )){
			 if(!Utils.isEmpty(converter.getAFromB(request.getParameter( com.Constant.CONST_EMAILREQ )))){
				 reminderVO.setEmail(converter.getAFromB(request.getParameter( com.Constant.CONST_EMAILREQ )));
			}
		}
		try{
			String dateTemp = request.getParameter( "preparedDate" );
			if(!Utils.isEmpty(request.getParameter( "forReset" ))){
				if(!Utils.isEmpty(dateTemp)){
					String [] dateTempArray = dateTemp.split( " " );
					dateString = dateTempArray[2];
					dateString = dateString +"-"+dateTempArray[1];
					dateString = dateString +"-"+dateTempArray[5];
					String tempHHMMSS [] = dateTempArray[3].split(":");
					String HH = tempHHMMSS[0];
					String MM = tempHHMMSS[1];
					String SS = tempHHMMSS[2];
					if(dateTemp.contains("PM")){
						HH = String.valueOf((Integer.valueOf(tempHHMMSS[0])+12));
					}
					String HHMMSS = HH+":"+MM+":"+SS;
					reminderVO.setPreparedDate(new SimpleDateFormat("dd-MMM-yy HH:mm:ss").parse(dateString+" "+HHMMSS));
				}
			}else
			{	
				dateTemp = dateTemp.replace(",","");
				String array [] =dateTemp.split(" ");
				String MMM = array[0];
				String dd= array[1];
				String yyyy = array[2];
				String tempHHMMSS [] = array[3].split(":");
				String HH = tempHHMMSS[0];
				String MM = tempHHMMSS[1];
				String SS = tempHHMMSS[2];
				if(dateTemp.contains("PM")){
					HH = String.valueOf((Integer.valueOf(tempHHMMSS[0])+12));
				}
				String HHMMSS = HH+":"+MM+":"+SS;
				dateTemp = dd+"-"+MMM+"-"+yyyy;
				reminderVO.setPreparedDate( new SimpleDateFormat("dd-MMM-yy HH:mm:ss").parse(dateTemp+" "+HHMMSS));
			}	
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		if(action.equalsIgnoreCase( "LAPSED_ITEM" )){
			reminderVO.setLapsedDairyItems( true );
		}else{
			reminderVO.setLapsedDairyItems( false );
		}
		
		request.setAttribute( com.Constant.CONST_REMINDEROBJ, reminderVO );
		
		redirection = new Redirection( "/jsp/editDairyItem.jsp", Type.TO_JSP );
		res.setRedirection( redirection );
		
		}else if(action.equalsIgnoreCase( "ADD_ITEM" )){
			
			redirection = new Redirection( "/jsp/newDairyItem.jsp", Type.TO_JSP );
			res.setRedirection( redirection );
		}
		
		return res;
	}

}
