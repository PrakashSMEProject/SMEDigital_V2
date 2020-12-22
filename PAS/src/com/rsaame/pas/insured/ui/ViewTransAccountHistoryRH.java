package com.rsaame.pas.insured.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.app.AccountHistoryListVO;
import com.rsaame.pas.vo.bus.TransactionVO;

public class ViewTransAccountHistoryRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( ViewTransAccountHistoryRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.info( "*****Inside ViewTransAccountHistoryRH*****" );
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		String policyNo = request.getParameter( "policyNo" );

		TransactionVO transactionVO = new TransactionVO();
		transactionVO.setTransactionPolicyNumber( policyNo );

		BaseVO baseVO = TaskExecutor.executeTasks( identifier, transactionVO );
		logger.debug( "*****Executed taskExecutor in ViewTransAccountHistoryRH*****" );

		AccountHistoryListVO accountHistoryListVO = (AccountHistoryListVO) baseVO;

		if( Utils.isEmpty( accountHistoryListVO ) || ( !Utils.isEmpty( accountHistoryListVO ) && Utils.isEmpty( accountHistoryListVO.getAccountHistoryList() ) ) ){
			throw new BusinessException( "", null, "No records found." );
		}

		request.setAttribute( "accountHist", baseVO );
		response.setData( baseVO );
		return response;
	}

}
