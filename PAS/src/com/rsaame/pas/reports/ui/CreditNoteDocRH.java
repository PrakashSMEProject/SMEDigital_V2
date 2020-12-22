package com.rsaame.pas.reports.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.CreditNoteDetailsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author m1017948
 *
 */

public class CreditNoteDocRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( CreditNoteDocRH.class );
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		
		
		Response responseObj = new Response();
		String identifier = request.getParameter( "opType" );

		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		CommonVO commonVO = policyContext.getCommonDetails();
		DataHolderVO<Long> policyIdHolder = null;
		CreditNoteDetailsVO crDetsVO = new CreditNoteDetailsVO();
		/*
		 * AMS - Fix for release 2.1
		 */
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
		if( !Utils.isEmpty( policyVO ) ){
			policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "getPolicyIdForPolicy", policyVO );
			//crDetsVO.setCndPolicyId( policyIdHolder.getData() );

			crDetsVO.setCndPolicyNo( policyVO.getPolicyNo() );
			crDetsVO.setCndPolicyYear( SvcUtils.getYearFromDate( policyVO.getScheme().getEffDate() ) );
			logger.info( "Policy No within CreditNoteDocRH to check if credit note exists -->" + policyVO.getPolicyNo() );
			//Long endtId = AppUtils.getLatestEndtId( policyVO );
			/* if in quote flow just after converting to policy then 0 should be sent as endt id for credit note search else latest endorsement id for the policy*/
			Long endtId = ( policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : SvcUtils.getLatestEndtId( policyVO ) );
		
			logger.info( "EndtId within CreditNoteDocRH to check if credit note exists -->" + endtId );
			if( Utils.isEmpty( endtId ) ){
				throw new BusinessException( "cmn.unknownError", null, "For linking id " + policyVO.getPolLinkingId() + " Endorsment id is null" );
			}
			logger.info( "HHH IN the SBS BLOCK");
			
			crDetsVO.setCndEndtId( endtId );
		}
		else if( !Utils.isEmpty( commonVO ) ){
			policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "getPolicyIdForPolicy", commonVO );
			//crDetsVO.setCndPolicyId( policyIdHolder.getData() );

			crDetsVO.setCndPolicyNo( commonVO.getPolicyNo() );
			crDetsVO.setCndPolicyYear( SvcUtils.getYearFromDate( commonVO.getPolEffectiveDate() ) );
			logger.info( "Policy No within CreditNoteDocRH to check if credit note exists -->" + commonVO.getPolicyNo() );
			
			
			//Long endtId = commonVO.getEndtId(); // commented for VAT fix
			//Added for VAT Fix
			Long endtId = commonVO.getIsQuote() ? 0L : commonVO.getEndtId(); 
			
			logger.info( "EndtId within CreditNoteDocRH to check if credit note exists -->" + endtId );
			if( Utils.isEmpty( endtId ) ){
				throw new BusinessException( "cmn.unknownError", null, "For policy id " + commonVO.getPolicyId() + " Endorsment id is null" );
			}
			logger.info( "HHH IN the HOME_TRAVEL_WC BLOCK");

			crDetsVO.setCndEndtId( endtId );
			
		}
		
		if(!Utils.isEmpty( policyIdHolder ) && !Utils.isEmpty( policyIdHolder.getData() )){
			crDetsVO.setCndPolicyId( policyIdHolder.getData());
		}

		BaseVO resultVO = TaskExecutor.executeTasks( identifier, crDetsVO );
		if( !Utils.isEmpty( resultVO ) ){
			responseObj.setSuccess( true );
			responseObj.setData( resultVO );
		}

		return responseObj;
	}
}