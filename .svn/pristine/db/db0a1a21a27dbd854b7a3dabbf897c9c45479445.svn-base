package com.rsaame.pas.renewals.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.dao.model.TTrnPrintBatchPas;

/**
 * @author m1006438
 * This class is to print the renewal notice
 */
public class PrintRenewalNoticeRH implements IRequestHandler {

	public static final String CHECK_REPRINT = "CHECK_REPRINT";
	public static final String PRINT_RENEWAL_NOTICE = "PRINT_RENEWAL_NOTICE";
	public static final String REPRINT = "REPRINT";
	public static final String PRINT_SUBMIT = "PRINT_SUBMIT";
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		Response response = new Response();
		boolean isReprint = false;
		String action = request.getParameter( "action" );
		
		System.out.println("==-------->action = "+action);
		
		Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy").create();
		TTrnPrintBatchPas[] quoteForPrint = gson.fromJson(request.getParameter("selectedRows"), TTrnPrintBatchPas[].class);
		Long[] linkingIdList;
		//linkingIdList = gson.fromJson(request.getParameter("linkingIdList"), Long[].class);	
		linkingIdList = gson.fromJson(request.getParameter("idList"), Long[].class);	
		DataHolderVO<Object> inputData = new DataHolderVO<Object>();
		inputData.setData( linkingIdList );
		if(action.equals( CHECK_REPRINT )){
			DataHolderVO<Boolean> output =  (DataHolderVO<Boolean>) TaskExecutor.executeTasks(CHECK_REPRINT, inputData );	
			if(output.getData().booleanValue()){
				response.setData( REPRINT );
				isReprint =  true;
			}
		}
		if(!isReprint || action.equals( PRINT_RENEWAL_NOTICE )){
			DataHolderVO<Object[]> prnInput = new DataHolderVO<Object[]>();
			Object[] input = { quoteForPrint,linkingIdList };
			prnInput.setData(input);
			TaskExecutor.executeTasks(PRINT_RENEWAL_NOTICE, prnInput );
			response.setData(quoteForPrint.length);
		}
		return response;
	}

}
