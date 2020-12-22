package com.rsaame.pas.policy.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PaymentVO;

public class ProcessCollectionRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse resp ){

		Response response = new Response();
		Redirection redirection = null;
		String action = request.getParameter( "action" );
		String flowIdentifier = request.getParameter( "flowId" );
		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		/**
		 *  If the RSA user clicks yes for payment process
		 */
		if( action.equalsIgnoreCase( "PAYMENT_YES" ) ){
			redirection = new Redirection( "/jsp/policy/payment-mode-popup.jsp", Type.TO_JSP );
			/*
			 * PAYABLE_PREMIUM and PAYABLE_PREMIUM_TOTAL both the attributes must be set to some values. In case of issue quote and convert to policy in one flow PAYABLE_PREMIUM
			 * is set to value without discount so needs to be updated.
			 */
			if( (context.getAppFlow().equals( Flow.VIEW_QUO )||context.getAppFlow().equals( Flow.EDIT_QUO ) ) && context.getPolicyDetails().getIsQuote() ){ //&& Utils.isEmpty( request.getSession(false).getAttribute( "PAYABLE_PREMIUM" )) ){
				
				Double payablePrm =  (Double) request.getSession().getAttribute( "PAYABLE_PREMIUM_TOTAL" );
				request.getSession( false ).setAttribute( "PAYABLE_PREMIUM", payablePrm);
				
			}
		}
		/**
		 *  If the RSA user clicks on for payment process proceed to reports
		 */
		else if( action.equalsIgnoreCase( "PAYMENT_NO" ) ){

			PaymentVO detailsVO = new PaymentVO();
			detailsVO.setPaymentDone( false );
			/*
			 * Captured payment detail from here are carried through the session to ConvertToPolicyRH.
			 * After successful conversion of the policy the payment details will be saved against the 
			 * issued policy. 			
			 */
			request.getSession( false ).setAttribute( AppConstants.GET_PAYMENT_DETS, detailsVO );

			if( !Utils.isEmpty( flowIdentifier ) ){
				
				if( !context.getAppFlow().equals( Flow.AMEND_POL )
						&& /* IF appFLow is RESOLVE_REFERRAL then is quote check will be useful */context.getPolicyDetails().getIsQuote() )
					redirection = new Redirection( flowIdentifier, Redirection.Type.TO_NEW_OPERATION );
				else{
					//redirection = new Redirection( "CONFIRM_ENDORSEMENT", Redirection.Type.TO_NEW_OPERATION );
					redirection = new Redirection( "LOAD_COMMENTS&action=SAVE_ENDORSMENT_COMMENTS", Redirection.Type.TO_NEW_OPERATION );
				}
			}
		}
		/**
		 *  If the RSA user selects cash option capture cash details
		 */
		else if( action.equalsIgnoreCase( "Cash" ) ){

			request.getSession().setAttribute( com.Constant.CONST_PAYMENTCODE, request.getParameter( com.Constant.CONST_PAYMENTMODE ) );
			request.getSession().setAttribute( com.Constant.CONST_PAYMENTMODECODE, request.getParameter( com.Constant.CONST_PAYMODECODE ) );
			redirection = new Redirection( "/jsp/policy/cashModePopUp.jsp", Type.TO_JSP );

		}
		/**
		 *  If the RSA user selects Cheque option capture Cheque details
		 */
		else if( action.equalsIgnoreCase( "Cheque" ) ){
			request.getSession().setAttribute( com.Constant.CONST_PAYMENTCODE, request.getParameter( com.Constant.CONST_PAYMENTMODE ) );
			request.getSession().setAttribute( com.Constant.CONST_PAYMENTMODECODE, request.getParameter( com.Constant.CONST_PAYMODECODE ) );
			redirection = new Redirection( "/jsp/policy/chequeModePopUp.jsp", Type.TO_JSP );

		}
		/**
		 *  If the RSA user selects Credit Card  option capture Credit Card  details
		 */
		else if( action.equalsIgnoreCase( "Credit Card Swipe" ) ){

			request.getSession().setAttribute( com.Constant.CONST_PAYMENTCODE, request.getParameter( com.Constant.CONST_PAYMENTMODE ) );
			request.getSession().setAttribute( com.Constant.CONST_PAYMENTMODECODE, request.getParameter( com.Constant.CONST_PAYMODECODE ) );
			AppUtils.setDefaultTerminalId(request);
			redirection = new Redirection( "/jsp/policy/creditCardSwipeModePopUp.jsp", Type.TO_JSP );

		}
		/**
		 *  If the RSA user selects Credit Card Telephone option capture Credit Card Telephone details
		 */
		else if( action.equalsIgnoreCase( "Credit Card Telephone" ) ){

			request.getSession().setAttribute( com.Constant.CONST_PAYMENTCODE, request.getParameter( com.Constant.CONST_PAYMENTMODE ) );
			request.getSession().setAttribute( com.Constant.CONST_PAYMENTMODECODE, request.getParameter( com.Constant.CONST_PAYMODECODE ) );
			AppUtils.setDefaultTerminalId(request);
			redirection = new Redirection( "/jsp/policy/creditCardTeleModePopUp.jsp", Type.TO_JSP );

		}

		else if( action.equalsIgnoreCase( "STORE_PAYMENT_DETS" ) ){
			/*Request to VO mapping*/
			PaymentVO detailsVO = AppUtils.mapRequestToPaymentVO( request );
			/*
			 * Captured payment detail from here are carried through the session to ConvertToPolicyRH.
			 * After successful conversion of the policy the payment details will be saved against the issued policy. 			
			 */
			request.getSession( false ).setAttribute( AppConstants.GET_PAYMENT_DETS, detailsVO );

			if( !Utils.isEmpty( flowIdentifier ) ){
				if( !context.getAppFlow().equals( Flow.AMEND_POL )
						&& /* IF appFLow is RESOLVE_REFERRAL then is quote check will be useful */context.getPolicyDetails().getIsQuote() )
					redirection = new Redirection( flowIdentifier, Redirection.Type.TO_NEW_OPERATION );
				else{
					//redirection = new Redirection( "CONFIRM_ENDORSEMENT", Redirection.Type.TO_NEW_OPERATION );
					redirection = new Redirection( "LOAD_COMMENTS&action=SAVE_ENDORSMENT_COMMENTS", Redirection.Type.TO_NEW_OPERATION );
				}
			}

		}

		response.setRedirection( redirection );
		return response;
	}

	

}
