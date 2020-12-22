/**
 * 
 */
package com.rsaame.pas.quote.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.PolicyVO;
/**
 * @author m1014320
 *
 */
public class EditQuoteRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		String opType = request.getParameter( AppConstants.OPERATIONTYPE );

		PolicyContext polContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = polContext.getPolicyDetails();
		Response responseObj = new Response();
		
		/* check if the user is trying to reevaluate the expired quote and has referral */
		if( policyVO.getStatus() == AppConstants.QUOTE_EXPIRED && hasEditQuoteReferral(request, responseObj, policyVO) ){
			return responseObj;
		}
		
		TaskExecutor.executeTasks( opType, polContext.getPolicyDetails() );

		//Response responseObj = new Response();
		Redirection redirection = new Redirection( "GENERAL_INFO_PAGE", Redirection.Type.TO_NEW_OPERATION );
		responseObj.setRedirection( redirection );

		return responseObj;
	}

	private boolean hasEditQuoteReferral( HttpServletRequest request, Response responseObj, PolicyVO policyVO ){
		
		boolean result = false;
		String actionIdentifier = AppConstants.EDIT_QUO_RULES_EXEC_CONFIG_KEY;
		if( !SectionRHUtils.executeReferralTask( responseObj, actionIdentifier, policyVO, actionIdentifier ) ){
			List<String>  referralText = AppUtils.getReferralTextListForActionId( policyVO, actionIdentifier );
			AppUtils.createHardStopReferralResponse( request, responseObj, referralText );
			result = true;
		}
		return result;
	}

}
