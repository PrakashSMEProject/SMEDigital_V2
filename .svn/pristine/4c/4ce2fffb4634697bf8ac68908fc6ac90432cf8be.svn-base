package com.rsaame.pas.endorsement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class ConfirmEndorsementRH implements IRequestHandler{
	private final static Logger LOGGER = Logger.getLogger( ConfirmEndorsementRH.class );
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		//Service call

			Response responseObj = new Response();
			PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
			PolicyVO policyVO = policyContext.getPolicyDetails();
			PaymentVO paymentVO = (PaymentVO) request.getSession( false ).getAttribute( AppConstants.GET_PAYMENT_DETS );
			List<EndorsmentVO> endorsmentVOs = policyVO.getEndorsements();
			if( LOGGER.isInfo() ) LOGGER.info( " endorsmentVOs list size : " + endorsmentVOs.size() );
			
			//BaseVO baseVO =  TaskExecutor.executeTasks("CAPTURE_ENDORSEMENT_TEXT_UPDATE", policyVO );
			//CTS - 14.08.2020 - UAT issue fix for transaction history status shows pending for endorsement - Starts
			policyVO = (PolicyVO) TaskExecutor.executeTasks("CAPTURE_ENDORSEMENT_TEXT_UPDATE", policyVO );
			//CTS - 14.08.2020 - UAT issue fix for transaction history status shows pending for endorsement - Ends

			
			if(!endorsmentVOs.get( 0 ).getEndType().equalsIgnoreCase( "NIL" )){
				if( !Utils.isEmpty( paymentVO ) && !Utils.isEmpty( policyVO ) ){
					List inputVoList = new ArrayList();
					inputVoList.add( policyVO );
					inputVoList.add( paymentVO );
					inputVoList.add(new CommonVO());
					DataHolderVO<List> dataHolderVO = new DataHolderVO<List>();
					dataHolderVO.setData( inputVoList );
					policyVO = (PolicyVO) TaskExecutor.executeTasks( AppConstants.SAVE_PAYMENT_DETAILS, dataHolderVO );
				}
			}			
		
			CommentsVO commentsVO = BeanMapper.map( request, CommentsVO.class );
			if( !Utils.isEmpty( policyVO.getPolLinkingId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
				commentsVO.setPocPolicyId( policyVO.getPolLinkingId() ); // TODO: need to check with DB
				commentsVO.setPocEndtId( policyVO.getEndtId() );
			}
			/* If NewEndtId is available, update pocEndtId as NewEndtId*/
			if( !Utils.isEmpty( policyVO.getNewEndtId() ) ){
				commentsVO.setPocEndtId( policyVO.getNewEndtId() );
			}
			
			UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
			if( !Utils.isEmpty( userProfile ) ){
				commentsVO.setLoggedInUser( userProfile );
			}

			commentsVO.setPolicyStatus( policyVO.getStatus().byteValue() );
			
			//request.getSession( false ).setAttribute( AppConstants.GET_COMMENTS, commentsVO );
			
			PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
			//CommentsVO commentsVO = (CommentsVO) request.getSession( false ).getAttribute( AppConstants.GET_COMMENTS );
			polComHolder.setComments( commentsVO );
			polComHolder.setPolicyDetails( policyVO );
			TaskExecutor.executeTasks( AppConstants.STORE_POL_COMMENTS, polComHolder );
			
			request.getSession( false ). removeAttribute(AppConstants.GET_PAYMENT_DETS );
			
		//AppUtils.addErrorMessage( request, "pas.endorsementSuccessMsg" );
			responseObj.setData( "Endorsement Confirmed successfully" );
		return responseObj;
	}

}
