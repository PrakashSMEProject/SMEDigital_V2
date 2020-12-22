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
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.DebitNoteDetailsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author m1017948
 *
 */

public class DebitNoteDocRH implements IRequestHandler {

	private static final Logger logger = Logger.getLogger( DebitNoteDocRH.class );
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {

	Response responseObj = new Response();
		
	String identifier = request.getParameter("opType");
	PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
	PolicyVO policyVO = policyContext.getPolicyDetails();
	CommonVO commonVO = policyContext.getCommonDetails();
	DebitNoteDetailsVO drNoteDetsVO = new DebitNoteDetailsVO();
	DataHolderVO<Long> policyIdHolder = null;
		/*
		* AMS - Fix for release 2.1
		*/
	CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );

		if( !Utils.isEmpty( policyVO ) ){
			policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "getPolicyIdForPolicy", policyVO );
			//drNoteDetsVO.setDndPolicyId( policyIdHolder.getData() );

			drNoteDetsVO.setDndPolicyNo( policyVO.getPolicyNo() );
			drNoteDetsVO.setDndPolicyYear( SvcUtils.getYearFromDate( policyVO.getScheme().getEffDate() ) );
			logger.info( "Policy No within DebitNoteDocRH to check if debit note exists -->" + policyVO.getPolicyNo() );
			/* if in quote flow just after converting to policy then 0 should be sent as endt id for debit note search else latest endorsement id for the policy*/
			Long endtId = ( policyVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : SvcUtils.getLatestEndtId( policyVO ) );
			logger.info( "EndtId within DebitNoteDocRH to check if credit note exists -->" + endtId );
			if( Utils.isEmpty( endtId ) ){
				throw new BusinessException( "cmn.unknownError", null, "For linking id " + policyVO.getPolLinkingId() + " Endorsment id is null" );
			}
			drNoteDetsVO.setDndEndtId( endtId );
		}
		else if( !Utils.isEmpty( commonVO ) ){
			policyIdHolder = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "getPolicyIdForPolicy", commonVO );
			//drNoteDetsVO.setDndPolicyId( policyIdHolder.getData() );
			drNoteDetsVO.setDndPolicyNo( commonVO.getPolicyNo() );
			drNoteDetsVO.setDndPolicyYear( SvcUtils.getYearFromDate( commonVO.getPolEffectiveDate() ) );
			logger.info( "Policy No within DebitNoteDocRH to check if debit note exists -->" + commonVO.getPolicyNo() );
			/* if in quote flow just after converting to policy then 0 should be sent as endt id for debit note search else latest endorsement id for the policy*/
			//Long endtId = commonVO.getEndtId();
			Long endtId = ( commonVO.getIsQuote() ? AppConstants.INTIAL_POL_ENDT : commonVO.getEndtId() );
			logger.info( "EndtId within DebitNoteDocRH to check if credit note exists -->" + endtId );
			if( Utils.isEmpty( endtId ) ){
				throw new BusinessException( "cmn.unknownError", null, "For linking id " + commonVO.getPolicyId() + " Endorsment id is null" );
			}
			drNoteDetsVO.setDndEndtId( endtId );
		}
		
	if(!Utils.isEmpty( policyIdHolder ) && !Utils.isEmpty( policyIdHolder.getData() )){
		drNoteDetsVO.setDndPolicyId(  policyIdHolder.getData());
	}
		
	BaseVO resultVo = TaskExecutor.executeTasks(identifier, drNoteDetsVO);

	if(!Utils.isEmpty(resultVo)){
		responseObj.setSuccess(true);
		responseObj.setData(resultVo);
	}
		
	return responseObj;
	}
}
