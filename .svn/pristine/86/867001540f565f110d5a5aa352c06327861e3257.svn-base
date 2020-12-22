package com.rsaame.pas.reports.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.cmn.CommonVO;

public class BankLetterDocRH implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		Response responseObj = new Response();
		String identifier = request.getParameter("opType");
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		
		CommonVO commonVO = policyContext.getCommonDetails();
		
		BaseVO baseVO = TaskExecutor.executeTasks(identifier, commonVO);
		
		@SuppressWarnings("unchecked")
		DataHolderVO<Boolean> resultVo =  (DataHolderVO<Boolean>)baseVO;
		if(!Utils.isEmpty(resultVo) && resultVo.getData()){
			responseObj.setSuccess(true);
			responseObj.setData("Success");
		}
			
		return responseObj;
	}

}
