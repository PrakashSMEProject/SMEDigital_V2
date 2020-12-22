/**
 * 
 */
package com.rsaame.pas.renewals.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.dao.model.TTrnPrintBatchPas;

/**
 * @author m1019193
 *This class is used to print policies for  which renewal quotes have been generated for Home/Travel-phase 3
 */
public class PrintRenewalNoticeCommonRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger(PrintRenewalNoticeCommonRH.class);
	public final static String CHECK_REPRINT = "CHECK_REPRINT";
	public final static String CHECK_FOR_REPRINT = "CHECK_FOR_REPRINT";
	public final static String PRINT_RENEWAL_NOTICE_COMMON = "PRINT_RENEWAL_NOTICE_PAS";
	public final static String PRINT_RENEWAL_NOTICE = "PRINT_RENEWAL_NOTICE";
	public final static String REPRINT = "REPRINT";
	public final static String PRINT_SUBMIT = "PRINT_SUBMIT";
	
	
	public PrintRenewalNoticeCommonRH() {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse responseObj) {
		Response response = new Response();
		LOGGER.debug("******Inside PrintRenewalNoticeCommonRH*******");
		boolean isReprint = false;
		String action = request.getParameter("action");		
		Gson gson = new GsonBuilder().setDateFormat("dd-MMM-yyyy").create();
		TTrnPrintBatchPas[] quoteForPrint = gson.fromJson(request.getParameter("selectedRows"), TTrnPrintBatchPas[].class);
		Long[] policyIdList;
		policyIdList = gson.fromJson(request.getParameter("idList"), Long[].class);	
		DataHolderVO<Object> inputData = new DataHolderVO<Object>();
		inputData.setData(policyIdList);
		if(action.equals(CHECK_REPRINT)) {
			DataHolderVO<Boolean> output =  (DataHolderVO<Boolean>) TaskExecutor.executeTasks(CHECK_FOR_REPRINT, inputData);	
			if(output.getData().booleanValue()) {
				response.setData(REPRINT);
				isReprint =  true;
			}
		}
		if(!isReprint || action.equals(PRINT_RENEWAL_NOTICE)){
			DataHolderVO<Object[]> prnInput = new DataHolderVO<Object[]>();
			Object[] input = { quoteForPrint,policyIdList };
			prnInput.setData(input);
			TaskExecutor.executeTasks(PRINT_RENEWAL_NOTICE_COMMON, prnInput);
			response.setData(quoteForPrint.length);
		}
		return response;
	}
}
