package com.rsaame.pas.policyAction.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1006438
 * 
 * This class is used to map the comments to the CommentsVO and set to session/ call appropriate RH to store the comments in Home/Travel
 *
 */
public class CommonPolicyActionRH implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Response responseObj = new Response();
		String action = request.getParameter( "action" );
		CommentsVO comments = 	(CommentsVO) request.getSession(false).getAttribute(AppConstants.GET_COMMENTS);
		PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		
		resolveStatus(comments,action) ;
		
		if(!Utils.isEmpty( comments ))
		{
			polComHolder.setComments( comments );
		}
		polComHolder.setCommonDetails(commonVO);
		//Radar fix
		TaskExecutor.executeTasks( action, polComHolder );
		request.getSession(false).removeAttribute( AppConstants.GET_COMMENTS );
		
		String message ="";
		
		PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
		policyDataVO.setCommonVO( commonVO );
		if(action.equals( "REJECT_QUOTE_COMMON" )){
			commonVO.setStatus( AppConstants.QUOTE_REJECT);
			message="pas.rejectQuoteSuccessful";
		}
		if(action.equals( "DECLINE_QUOTE_COMMON" )){
			commonVO.setStatus( AppConstants.QUOTE_DECLINED);
			message = commonVO.getIsQuote()?"pas.declineQuoteSuccessful":"pas.declinePolicySuccessful";
			AppUtils.sendMail( policyDataVO , "DECLINE" );
		}
		else if( action.equalsIgnoreCase( "APPROVE_QUOTE_COMMON" ) ){
			commonVO.setStatus( AppConstants.QUOTE_ACCEPT);
			message = commonVO.getIsQuote()?"pas.approveQuoteSuccessful":"pas.approvePolicySuccessful";
			//AppUtils.sendMail( policyDataVO , "APPROVE" );
		}
		AppUtils.addErrorMessage( request, message );
		return responseObj;

	}
	
	private void resolveStatus( CommentsVO commentsVO, String action ){

		if( Utils.isEmpty( commentsVO.getPolicyStatus() ) && !Utils.isEmpty(action) ){
			if( action.equalsIgnoreCase( "DECLINE_QUOTE" ) || action.equalsIgnoreCase( "DECLINE_POLICY" ) ){
				commentsVO.setPolicyStatus( Byte.valueOf( Utils.getSingleValueAppConfig( "QUOTE_DECLINED" ) ) );
			}else if( action.equalsIgnoreCase( "REJECT_QUOTE_COMMON" ) ){
				commentsVO.setPolicyStatus( Byte.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REJECT" ) ) );
			}else if( action.equalsIgnoreCase( "APPROVE_QUOTE_COMMON" ) ){
				commentsVO.setPolicyStatus((byte) AppConstants.QUOTE_ACCEPT);
			}
		}
	}

}
