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
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;

public class GenerateRenewalsRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger(GenerateRenewalsRH.class);
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		/*Response response = new Response();
		//Gson gson = new Gson(); 
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		TTrnRenewalBatchEplatform[] polForRenewal = gson.fromJson(request.getParameter("selectedRows"), TTrnRenewalBatchEplatform[].class);
		DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
		Object[] input = { polForRenewal };
		inputData.setData( input );
		TaskExecutor.executeTasks("GENERATE_RENEWALS", inputData );	
		return response;*/		
		
		Response response = new Response();
		//Gson gson = new Gson(); 
		String opType = request.getParameter("opType");
		LOGGER.debug( ":::::::::::OpType in GenerateRenewalsRH :" + opType );
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		TTrnRenewalBatchEplatform[] polForRenewal = gson.fromJson(request.getParameter("selectedRows"), TTrnRenewalBatchEplatform[].class);
		DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
		Object[] input = { polForRenewal };
		inputData.setData( input );
		TaskExecutor.executeTasks(opType, inputData );	
		return response;
	}

}
