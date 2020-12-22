/**
 * 
 */
package com.rsaame.pas.policy.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1016303
 *
 */
public class ConvertToPolicyRH implements IRequestHandler{

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse resp ){

		Response response = new Response();
		String operationType = request.getParameter( "opType" );
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO pol = policyContext.getPolicyDetails();
		PaymentVO paymentVO = (PaymentVO) request.getSession( false ).getAttribute( AppConstants.GET_PAYMENT_DETS );
		CommentsVO commentsVO = (CommentsVO) request.getSession( false ).getAttribute( AppConstants.GET_COMMENTS );

		policyContext.startTransaction();

		PolicyVO policyVO = savePolicyPaymentDetails( operationType , pol, paymentVO );
		policyVO.setEndtId(commentsVO.getPocEndtId());// fix for vat shown in UI in of previous endorsement not for current endt after Convert to policy
		/* For convert to policy,status will be always set to 7 i.e Converted to policy.  */
		policyVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( "CONV_TO_POL" ) ) );
		/*
		 * Capture the convert to policy comments. If the comments are present in session taking that and saving to database.
		 */
		savePolicyComments( policyVO, commentsVO );

		/*
		 * After converting the policy, checking if the payment details present in session then save them in database againest the 
		 * generated policy number.
		 */
		

		policyContext.commit();

		request.setAttribute( "policyNumber", policyVO.getConcatPolicyNo() );

		if( !Utils.isEmpty( policyVO ) ){
			response.setSuccess( true );
			response.setData( policyVO );
		}

		PolicyContextUtil.getPolicyContext( request ).setPolicyDetails( policyVO );
		System.out.println( "In RH:" + policyVO.getPolicyNo() );

		request.setAttribute( "status", policyVO.getStatus() );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		/*
		 * When converted to policy the flow will change to view quote
		 * This is because the quote converted to policy cannot be edited
		 */
		PolicyContextUtil.getPolicyContext( request ).setAppFlow( Flow.VIEW_QUO );
		request.setAttribute( AppConstants.FUNTION_NAME, Flow.VIEW_QUO.toString() );
		request.setAttribute( AppConstants.SCREEN_NAME, "PREMIUM" );
		return response;

	}

	/*
	 * This method will call the PolicyActionSvc to save the comments to database
	 */
	private void savePolicyComments( PolicyVO policyVO, CommentsVO commentsVO ){

		if( !Utils.isEmpty( commentsVO ) ){
			PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
			polComHolder.setComments( commentsVO );
			polComHolder.setPolicyDetails( policyVO );
			TaskExecutor.executeTasks( AppConstants.STORE_POL_COMMENTS, polComHolder );
		}

	}

	/*
	 * This method will call the PaymentOptionSvc to save the payment details to database
	 */
	private PolicyVO savePolicyPaymentDetails( String operationType, PolicyVO policyVO, PaymentVO paymentVO ){

		List inputVoList = new ArrayList();

		inputVoList.add( policyVO );
		if( !Utils.isEmpty( paymentVO ) ){
			inputVoList.add( paymentVO );
		}
		inputVoList.add( new CommonVO() );
		
		DataHolderVO<List> dataHolderVO = new DataHolderVO<List>();

		dataHolderVO.setData( inputVoList );
		//policyVO = (PolicyVO) TaskExecutor.executeTasks( AppConstants.SAVE_PAYMENT_DETAILS, dataHolderVO );
		policyVO = (PolicyVO) TaskExecutor.executeTasks( operationType, dataHolderVO );
		return policyVO;
	}

}
