package com.rsaame.pas.reports.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.app.SmsListVO;
import com.rsaame.pas.vo.bus.SmsVO;

/**
 * @author m1019193
 *Request Handler - It is executed to display the grid of sms list
 */
public class SmsListRH implements IRequestHandler {

	private final static Logger logger = Logger.getLogger( SmsListRH.class );
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse responseObj) {
		
		logger.debug( "*****Inside SmsReports*****" );
		String action = request.getParameter( "opType" );
		SmsVO smsVO = new SmsVO();
		SmsListVO smsListVO = new SmsListVO();
		
		smsListVO.setCurrentPage(1);
		
		smsListVO = (SmsListVO) TaskExecutor.executeTasks( action, smsVO );			
		
		Response response = new Response();	
		if( !Utils.isEmpty( smsListVO ) ){
			response.setSuccess( true );
			response.setData( smsListVO );
		}		
		return response;
		}

}