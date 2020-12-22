package com.rsaame.pas.policy.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;


public class CommonCapturePolicyStatusRH implements IRequestHandler {
	private static final String POLICY_ACTIONRH = "POLICY_ACTION_COMMON";
	private static final String CAPTURE_COMMENTS_RH = "captureCommentsRH";
	private static final Byte DECLINE_STATUS = Byte.valueOf( Utils.getSingleValueAppConfig( "QUOTE_DECLINE_CODE" ) );
	
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse resp ){
	Response responseObj = new Response();
	PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
	CommonVO commonVO = policyContext.getCommonDetails();
	String action = request.getParameter( "action" );
	String flowIdentifier = request.getParameter( "flowId" );
	Redirection redirection;
	CommentsVO commentsVO;
	commentsVO = BeanMapper.map( request, CommentsVO.class );
	commentsVO.setPocPolicyId( commonVO.getPolicyId() );
	commentsVO.setPocEndtId( commonVO.getEndtId() );
	
	resolveStatus(commentsVO,action) ;
	
	if(Utils.isEmpty( commonVO.getLob() )){
		throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
	}
	
	if(action.equalsIgnoreCase( "DECLINE_QUOTE_COMMON" ) || action.equalsIgnoreCase( "REJECT_QUOTE_COMMON" ) )
	{
		PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
		// Added equals() instead of == to avoid sonar violation on 25-9-2017
		if(commonVO.getIsQuote() && policyDataVO.getStatus().equals( Integer.valueOf( 7 )))
		{
			throw new BusinessException( "pas.quote.policy", null, "Quote already converted to policy" );
		}
	}
	commentsVO.setLob( commonVO.getLob() );
	UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
	
	if( !Utils.isEmpty( userProfile ) ){
		commentsVO.setLoggedInUser( userProfile );
		commonVO.setLoggedInUser( userProfile );
	}
	request.getSession().setAttribute( AppConstants.GET_COMMENTS, commentsVO );
	
	if( !Utils.isEmpty( flowIdentifier ) ){
		redirection = new Redirection( flowIdentifier, Redirection.Type.TO_NEW_OPERATION );
	}
	else{
		redirection = new Redirection( POLICY_ACTIONRH, Redirection.Type.TO_NEW_OPERATION );
	}
	responseObj.setRedirection( redirection );
	return responseObj;

	}

	private void resolveStatus( CommentsVO commentsVO, String action ){

		if( Utils.isEmpty( commentsVO.getPolicyStatus() )  && !Utils.isEmpty(action) ){
			if( action.equalsIgnoreCase( "DECLINE_QUOTE_COMMON" ) ){
				commentsVO.setPolicyStatus( Byte.valueOf( Utils.getSingleValueAppConfig( "QUOTE_DECLINED" ) ) );
			}
			else if( action.equalsIgnoreCase( "REJECT_QUOTE_COMMON" ) ){
				commentsVO.setPolicyStatus( Byte.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REJECT" ) ) );
			}
			else if( action.equalsIgnoreCase( "APPROVE_QUOTE_COMMON" ) ){
				commentsVO.setPolicyStatus((byte) AppConstants.QUOTE_ACCEPT);
			}
		}
	}

}
