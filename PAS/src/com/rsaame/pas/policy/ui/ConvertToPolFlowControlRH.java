package com.rsaame.pas.policy.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ConvertToPolFlowControlRH implements IRequestHandler{

	private static final String CONVERT_TO_POLICY = "CONVERT_TO_POLICY";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse resp ){

		Response response = new Response();

		/**
		 * a, Get user profile from user details  from session 
		 * b, For broker skip payment collection mode
		 * c, for RSA user provide option for payment collection mode.
		 */

		/* a */
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		String profile = null;
		if( !Utils.isEmpty( userProfile ) ){
			profile = userProfile.getRsaUser().getProfile();
		}

		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = context.getPolicyDetails();
		
		Redirection redirection = new Redirection();
		if( !Utils.isEmpty( profile ) ){

			//TODO: Once Payment mode is done... This needs to be uncommented.
			if( profile.equalsIgnoreCase( "Broker" ) || AppUtils.isRSAUserWithBrokerDistChannel( profile, policyVO ) ){
				
				/*
				 * In case of Broker users the payment facility will not be there.
				 * Flow for Broker User:
				 * 1. Convert to policy
				 * 2. Show the Print Policy Document 
				 * But DAO call should Happen for Broker User also,
				 * The PaymentOptionDAO will be called only when if there is a PaymentVO in the Session
				 * So, for Broker user, to call the DAO set The empty payment object into the session
				 * with paymentDone option set as False. so the DAO will be called and the for broker 
				 * user also data will be stored into debit notes. 
				 *
				 */
				
				PaymentVO detailsVO = new PaymentVO();
				detailsVO.setPaymentDone( false );
				request.getSession( false ).setAttribute( AppConstants.GET_PAYMENT_DETS, detailsVO );
		
				redirection = new Redirection( CONVERT_TO_POLICY, Redirection.Type.TO_NEW_OPERATION );
			}

			else if( profile.equalsIgnoreCase( "EMPLOYEE" ) ){
				redirection = new Redirection( "/jsp/policy/processPremiumCollection.jsp", Type.TO_JSP );
			}

		}
		response.setRedirection( redirection );
		return response;

	}
}
