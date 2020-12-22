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
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.ClaimsHistoryListVO;
import com.rsaame.pas.vo.bus.TransactionVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class ViewTransClaimsHistoryRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( ViewTransClaimsHistoryRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.info( "***** Inside ViewTransClaimsHistoryRH *****" );
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		String policyNo = request.getParameter( "policyNo" );
		String transactionType = request.getParameter( "transactionType" );
		
		if(!Utils.isEmpty( transactionType )){
			if(transactionType.equalsIgnoreCase( "RENEWAL QUOTATION" )){
				policyNo = AppUtils.getRenewalPolicyNumber(policyNo);
			}
		}

		TransactionVO transactionVO = new TransactionVO();
		transactionVO.setTransactionPolicyNumber( policyNo );
		
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, transactionVO );
		logger.debug( "*****Executed taskExecutor in ViewTransAccountHistoryRH*****" );

		ClaimsHistoryListVO claimsHistoryListVO = (ClaimsHistoryListVO) baseVO;

		if( Utils.isEmpty( claimsHistoryListVO ) || ( !Utils.isEmpty( claimsHistoryListVO ) && Utils.isEmpty( claimsHistoryListVO.getClaimsHistoryArray() ) ) ){
			throw new BusinessException( "", null, "No records found." );
		}

		request.setAttribute( "claimHist", baseVO );
		response.setData( baseVO );
		return response;
	}

}
