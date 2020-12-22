/**
 * 
 */
package com.rsaame.pas.reports.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;

/**
 * @author m1019193
 *
 */
public class DeleteSmsRH implements IRequestHandler {
	
	private final static Logger LOGGER = Logger.getLogger( DeleteSmsRH.class );
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.debug( "*****Inside DeleteSmsRH******" );
		Response responseObj = new Response();		
		String action = request.getParameter( "opType" );		
		Gson gson = new Gson();
		Long[] smsIdList;
		smsIdList = gson.fromJson(request.getParameter("selectedRows"), Long[].class);	
		DataHolderVO<Object> inputData = new DataHolderVO<Object>();
		inputData.setData( smsIdList );
		
		TaskExecutor.executeTasks( action, inputData );		
		return responseObj;			
	}

}

