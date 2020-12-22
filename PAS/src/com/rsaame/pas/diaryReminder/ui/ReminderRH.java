package com.rsaame.pas.diaryReminder.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.mapping.Array;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dairy.svc.DairySvc;
import com.rsaame.pas.dao.model.TTrnReminder;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.ReminderListVO;
import com.rsaame.pas.vo.bus.ReminderVO;

public class ReminderRH implements IRequestHandler{
/***
 *  action = showReminder , will fetch reminders for the logged in user from current time to next 15mins
 *  action = dismissReminder, will update the status of the reminder
 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response resp = new Response();
		String action = request.getParameter( "action" );
		UserProfile user = AppUtils.getUserDetailsFromSession( request );
		DairySvc dairySvc = (DairySvc) Utils.getBean( "dairySvc" );
		if( action.equals( "showReminder" ) ){
			DataHolderVO<List<TTrnReminder>> data = (DataHolderVO<List<TTrnReminder>>) dairySvc.invokeMethod( "getDairyItemsForReminder", user.getRsaUser().getUserId().toString() );
			com.mindtree.ruc.cmn.utils.List<ReminderVO> list = new com.mindtree.ruc.cmn.utils.List<ReminderVO>( ReminderVO.class );
			ReminderVO reminderVO;
			for( TTrnReminder reminder : data.getData() ){
				reminderVO = new ReminderVO();
				if( reminder.getRemDescription().indexOf( SvcConstants.REMAINDER_SUBJECT_DELIMITER ) == -1 ){
					reminderVO.setSubject( "" );
				}
				else{
					StringTokenizer st = new StringTokenizer( reminder.getRemDescription(), SvcConstants.REMAINDER_SUBJECT_DELIMITER );
					reminderVO.setSubject( st.nextToken() );
					reminderVO.setComments(new StringBuilder(st.nextToken()).toString());
				}
				Calendar calendar = new GregorianCalendar();
				calendar.setTime( reminder.getRemTargetDt() );

				reminderVO.setTargetdate( getDateFormatted( calendar.get( calendar.DAY_OF_MONTH ) ) + "-" + getDateFormatted( calendar.get( calendar.MONTH ) + 1 ) + "-"
						+ calendar.get( calendar.YEAR ) );
				reminderVO.setRemTime( getDateFormatted( calendar.get( calendar.HOUR_OF_DAY ) ) + ":" + getDateFormatted( calendar.get( calendar.MINUTE ) ) + ":"
						+ getDateFormatted( calendar.get( calendar.SECOND ) ) );
				reminderVO.setsLNumber(Integer.valueOf( reminder.getId().getRemSrlNo() ) );
				reminderVO.setRemTypeId( String.valueOf( reminder.getId().getRemTypeId() ) );
				reminderVO.setPreparedDate(  reminder.getId().getRemPreparedDt()  );
				reminderVO.setPreparedBy( String.valueOf( reminder.getId().getRemPreparedBy() ));
				//Radar fix
				//SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
				
				reminderVO.setPreparedDateString(new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format(  reminder.getId().getRemPreparedDt() ));
				list.add( reminderVO );
			}
			ReminderListVO reminderListVO = new ReminderListVO();
			reminderListVO.setReminderListVO( list );
			resp.setData( reminderListVO );
		}
		if( action.equals( "dismissReminder" ) ){
			String remPreparedDateArray = request.getParameter( "remPreparedDateArray" );
			List<String> preparedDateList=new ArrayList<String>();
			String remTypeIdArray = request.getParameter( "remTypeIdArray" );
			List<String> remTypeIdList=new ArrayList<String>();
			String remSlrNoArray = request.getParameter( "remSlrNoArray" );
			List<String> remSlNoList = new ArrayList<String>();
			String preparedDate,reminderTypeId,reminderSlrNo;
			StringTokenizer st = new StringTokenizer(remPreparedDateArray, ",");
			StringTokenizer st1 = new StringTokenizer(remTypeIdArray, ",");
			StringTokenizer st2= new StringTokenizer(remSlrNoArray, ",");
			int numbDocs = st.countTokens();
			for(int j=0;j<numbDocs;j++){
				preparedDateList.add(st.nextToken() );
				remTypeIdList.add( st1.nextToken() );
				remSlNoList.add( st2.nextToken() );
			}
			
			for(int i=0;i<preparedDateList.size();i++){
			/*SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
			Date prepDate = null;*/
			preparedDate=preparedDateList.get( i );
			reminderTypeId=remTypeIdList.get( i );
			reminderSlrNo=remSlNoList.get( i );
			/*try{
				prepDate = sdf.parse( preparedDate );
			}
			catch( ParseException e ){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			/*preparedDate = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss").format( prepDate );*/
			
				dairySvc.invokeMethod( "updateDiaryItemsForReminder", reminderSlrNo, user.getRsaUser().getUserId().toString(), preparedDate, reminderTypeId );
			}
		}
		return resp;
	}

	private String getDateFormatted( int i ){
		if( String.valueOf( i ).length() == 1 ){
			return "0" + i;
		}
		return String.valueOf( i );
	}

}
