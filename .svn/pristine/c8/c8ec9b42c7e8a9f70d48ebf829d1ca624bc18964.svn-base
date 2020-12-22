/**
 * 
 */
package com.rsaame.pas.reports.ui;
import com.rsaame.pas.util.AppConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.bus.SmsVO;

/**
 * @author m1019193
 *Request Handler - It is executed to save the SMS
 */
public class NewSmsSaveRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( NewSmsSaveRH.class );
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse responseObj) {
		Response response = new Response();
		String action = request.getParameter( "opType" );
		
		if( logger.isDebug() ){
			logger.debug( "opType-->" + action );
			logger.debug( "Inside NewSmsSaveRh" );
			logger.debug( "-------SMS ID: " + request.getParameter( "smsID" ) );
			logger.debug( "-------SMS Mode: " + request.getParameter( "smsMode" ) );
			logger.debug( "-------SMS Level: " + request.getParameter( "smsLevel" ) );
			logger.debug( "-------SMS Frequency: " + request.getParameter( "smsFrequency" ) );
			logger.debug( "----------English Text: " + request.getParameter( "engText" ) );
			logger.debug( "----------arabic Text: " + request.getParameter( "arabicText" ) );
			logger.debug( "----------smsStatus: " + request.getParameter( "smsStatus" ) );
		}
				
		SmsVO smsVO = BeanMapper.map(request, SmsVO.class);
		if(smsVO.getSmsStatus().equalsIgnoreCase(AppConstants.SMS_STATUS_ON)){
			smsVO.setSmsStatus(AppConstants.SMS_STATUS_ACTIVE);
		}else {
			smsVO.setSmsStatus(AppConstants.SMS_STATUS_INACTIVE);
		}
		TaskExecutor.executeTasks( action, smsVO );
				
		return response;
	}

}
