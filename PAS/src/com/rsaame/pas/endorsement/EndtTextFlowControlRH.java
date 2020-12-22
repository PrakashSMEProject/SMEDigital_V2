package com.rsaame.pas.endorsement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class EndtTextFlowControlRH implements IRequestHandler{
	private static final String CONFIRM_ENDORSEMENT = "CONFIRM_ENDORSEMENT";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response responseObj = new Response();
		//Radar fix
		Redirection redirection = null; //new Redirection();
		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policy = context.getPolicyDetails();
		
		boolean isPaymentRequired = isPaymentRequired( request, policy );
		/* The below line is added to remove payment option. To enable payment option again, avoid setting isPaymentRequired to false.*/
		
		/* Remove below code comments if payment option is to be disabled during convert to policy.*/
		/*isPaymentRequired = false;
		PaymentVO detailsVO = new PaymentVO();
		detailsVO.setPaymentDone( false );*/
		/*
		 * Captured payment detail from here are carried through the session to ConvertToPolicyRH.
		 * After successful conversion of the policy the payment details will be saved against the 
		 * issued policy. 			
		 */
		/*request.getSession( false ).setAttribute( AppConstants.GET_PAYMENT_DETS, detailsVO );*/
		/* Remove above code comments if payment option is to be disabled during convert to policy.*/
		
		if( isPaymentRequired ){
			redirection = new Redirection( "/jsp/policy/processPremiumCollection.jsp", Type.TO_JSP );
				}
		else{
			//redirection = new Redirection( CONFIRM_ENDORSEMENT, Redirection.Type.TO_NEW_OPERATION );
			redirection = new Redirection( "LOAD_COMMENTS&action=SAVE_ENDORSMENT_COMMENTS", Redirection.Type.TO_NEW_OPERATION );
			}
		responseObj.setRedirection( redirection );
		return responseObj;
	}

	private boolean isPaymentRequired( HttpServletRequest request, PolicyVO policy){
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		String profile = null;
		if( !Utils.isEmpty( userProfile ) ){
			profile = userProfile.getRsaUser().getProfile();
		}

		if( !Utils.isEmpty( profile ) ){

			if( profile.equalsIgnoreCase( "Broker" ) || AppUtils.isRSAUserWithBrokerDistChannel( profile, policy ) ){
				
				if( profile.equalsIgnoreCase("EMPLOYEE") ){
					
					PaymentVO detailsVO = new PaymentVO();
					detailsVO.setPaymentDone( false );
					request.getSession( false ).setAttribute( AppConstants.GET_PAYMENT_DETS, detailsVO );
				
				}
				return false;
			}

			else if( profile.equalsIgnoreCase( "EMPLOYEE" ) ){
				/*The below code is introduced to check if endorsement is of type Refund. This is required because in case of Refund Endorsement 
				 * we need not to show payment collection pop-up.*/
				Boolean refundEndorsementFlag = isEndorsementRefund(PolicyContextUtil.getPolicyContext(request).getPolicyDetails(),request);
				return !refundEndorsementFlag;
			}

		}
		return false;
	}

	
	/**
	 * This method is used to check whether current endorsement is of type Refund or not
	 * @param policyVO
	 * @param request 
	 * @return
	 */
	private Boolean isEndorsementRefund(PolicyVO policyVO, HttpServletRequest request) {
		Boolean isEndorsementRefund=false;
		List<EndorsmentVO> endorsmentList= policyVO.getEndorsements();
		if(!Utils.isEmpty(endorsmentList)){
			EndorsmentVO endorsment=endorsmentList.get(0);
			if(!Utils.isEmpty(endorsment)){
				if(endorsment.getEndType().equalsIgnoreCase(AppConstants.REFUND_TYPE)){
					PaymentVO detailsVO = new PaymentVO();
					detailsVO.setPaymentDone( false );
					/*
					 * Setting the payment done to false as receipt entry should not be available for Refund endorsement.
					 * 			
					 */
					request.getSession( false ).setAttribute( AppConstants.GET_PAYMENT_DETS, detailsVO );
					isEndorsementRefund=true;
				}
			}
		}
		return isEndorsementRefund;
	}
}
