package com.rsaame.pas.reports.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReceiptDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author m1017948
 *
 */

public class ReceiptDocRH implements IRequestHandler{

	public Response execute( HttpServletRequest request, HttpServletResponse response ){
	
		Response responseObj = new Response();

		String identifier = request.getParameter("opType");
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		PolicyVO policyVO = policyContext.getPolicyDetails();
		CommonVO commonVO = policyContext.getCommonDetails();
		Long policyId = null;
		DataHolderVO<Long> policyIdHolder = null;
		ReceiptDetailsVO rcptDetsVO = new ReceiptDetailsVO();
		/*
		* AMS - Fix for release 2.1
		*/
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
		if( !Utils.isEmpty( policyVO ) ){
			rcptDetsVO.setRcdPolicyNo( policyVO.getPolicyNo() );
			policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "getPolicyIdForPolicy", policyVO );

			/*
			* Receipt can be viewed only for policies. The only scenario where receipt is viewed for quote is after convert to policy,
			* during which endtId is always 0, so setting endtId to 0.
			*/
			if( !Utils.isEmpty( policyVO.getIsQuote() ) && policyVO.getIsQuote() ){
				rcptDetsVO.setRcdEndtId( 0L );
			}
			else{
				rcptDetsVO.setRcdEndtId( policyVO.getEndtId() );
			}
		}
		else if( !Utils.isEmpty( commonVO ) ){
			policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "getPolicyIdForPolicy", commonVO );
			rcptDetsVO.setRcdPolicyNo( commonVO.getPolicyNo() );
			if( !Utils.isEmpty( commonVO.getIsQuote() ) && commonVO.getIsQuote() ){
				rcptDetsVO.setRcdEndtId( 0L );
			}
			else{
				rcptDetsVO.setRcdEndtId( commonVO.getEndtId() );
			}
			/*
			* Receipt can be viewed only for policies. The only scenario where receipt is viewed for quote is after convert to policy,
			* during which endtId is always 0, so setting endtId to 0.
			*/
			if( !Utils.isEmpty( commonVO.getIsQuote() ) && commonVO.getIsQuote() ){
				rcptDetsVO.setRcdEndtId( 0L );
			}
			else{
				rcptDetsVO.setRcdEndtId( commonVO.getEndtId() );
			}
		}
		if(!Utils.isEmpty( policyIdHolder ) && !Utils.isEmpty( policyIdHolder.getData() )){
			
			policyId = policyIdHolder.getData();
			rcptDetsVO.setRcdPolicyId( policyId );
		}

		BaseVO resultVO = TaskExecutor.executeTasks( identifier, rcptDetsVO );

		if( !Utils.isEmpty( resultVO ) ){
			responseObj.setSuccess( true );
			responseObj.setData( resultVO );
		}
		
		return responseObj;
	}
}
