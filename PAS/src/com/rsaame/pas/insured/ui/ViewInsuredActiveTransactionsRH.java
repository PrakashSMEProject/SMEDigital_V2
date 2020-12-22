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
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.TransactionSummaryVO;

public class ViewInsuredActiveTransactionsRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( ViewInsuredActiveTransactionsRH.class );
	java.text.DecimalFormat decFormBahrain = new  java.text.DecimalFormat(com.rsaame.pas.svc.constants.SvcConstants.DECIMAL_FORMAT_BAHRAIN);

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.info( "*****Inside ViewInsuredActiveTransactionsRH*****" );
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		String insuredId = request.getParameter( "insuredId" );
		String ccgCode = request.getParameter("ccgCode");
		
		InsuredVO insuredVO = new InsuredVO();
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if(!Utils.isEmpty(userProfile)){
			insuredVO.setLoggedInUser( userProfile );
		}
		insuredVO.setInsuredId( Long.valueOf( insuredId ) );
		insuredVO.setCcgCode( Integer.valueOf( ccgCode ) );
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, insuredVO );
		logger.debug( "*****Executed taskExecutor in ViewInsuredActiveTransactionsRH*****" );

		TransactionSummaryVO transactionSummaryVO = (TransactionSummaryVO) baseVO;
		
		Double premiumAmt=0.0;
        String premium=null;

        if ( Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)
          .equalsIgnoreCase("50") &&  !Utils.isEmpty( transactionSummaryVO.getTransactionArray() )) {
               for(int i=0;i<transactionSummaryVO.getTransactionArray().length;i++) {
                     premiumAmt=0.0;
                     premiumAmt = SvcUtils.getRoundingOffBah(Double.valueOf(transactionSummaryVO.getTransactionArray()[i].getTransactionPremium()));
                     premium = String.valueOf(decFormBahrain.format((premiumAmt)));
                      transactionSummaryVO.getTransactionArray()[i].setTransactionPremium(premium);
               }
               
        }


		if( Utils.isEmpty( transactionSummaryVO ) || ( !Utils.isEmpty( transactionSummaryVO ) && Utils.isEmpty( transactionSummaryVO.getTransactionArray() ) ) ){
			throw new BusinessException( "", null, "No records found." );
		}

		response.setData( baseVO );
		return response;
	}

}
