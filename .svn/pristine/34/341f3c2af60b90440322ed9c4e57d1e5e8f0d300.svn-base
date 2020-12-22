package com.rsaame.pas.b2c.lookup.ui;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;

public class LookupRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response responsedata = new Response();
		try
		{
			String identifier=(request.getParameter( "param1" ));
			String level1=(request.getParameter( "param2" ));
			String level2=(request.getParameter( "param3" ));
			BigDecimal code = null;
			if(!Utils.isEmpty(request.getAttribute( "code" )) && !Utils.isEmpty(request.getAttribute( "code" ).toString(),true))
			{
				 code=new BigDecimal(request.getAttribute( "code" ).toString());
			}
			
			String operationType=request.getParameter( "opType" );
			LookUpVO lookUpVO=new LookUpVO();
			// Oman multibranching. Set the current  location in the service context for the new task
			if(!Utils.isEmpty(request.getParameter(com.Constant.CONST_ACTION))){
				ServiceContext.setLocation(request.getParameter(com.Constant.CONST_ACTION));
				request.getSession().setAttribute(AppConstants.CTX_LOCATION,request.getParameter(com.Constant.CONST_ACTION));
			}
			
			BaseVO baseVO=null;
			if(Utils.isEmpty( code ))
			{
				lookUpVO.setCategory(identifier);
				lookUpVO.setLevel1(level1);
				lookUpVO.setLevel2(level2);
				baseVO= TaskExecutor.executeTasks(operationType, lookUpVO);
			}
			else
			{
				lookUpVO.setCategory( "GOVT_TAX" );
				lookUpVO.setLevel1( "ALL" );
				lookUpVO.setLevel2( "ALL" );
				lookUpVO.setCode( code );
				baseVO= TaskExecutor.executeTasks("LOOKUP_VALUE", lookUpVO);
			}
			
			if(baseVO instanceof LookUpListVO){
				baseVO = DropDownRendererHepler.getLookFilteredList((LookUpListVO) baseVO,request.getSession(false));
			}
			
			if(!Utils.isEmpty( baseVO)){
				responsedata.setSuccess(true);
				responsedata.setData(baseVO);
			}

		
	}catch(SystemException ex){
		ex.printStackTrace();
		
	}catch(BusinessException be){
		be.printStackTrace();
		}
	return responsedata;
	}

}
