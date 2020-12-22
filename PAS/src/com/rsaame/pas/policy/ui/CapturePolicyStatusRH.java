/**
 * 
 */
package com.rsaame.pas.policy.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.quote.ui.CheckIfCustomerExistsRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author m1016303
 * 
 */
public class CapturePolicyStatusRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( CapturePolicyStatusRH.class );
	private static final String POLICY_ACTIONRH = "POLICY_ACTION";
	private static final String CAPTURE_COMMENTS_RH = "captureCommentsRH";
	private static final Byte DECLINE_STATUS = Byte.valueOf( Utils.getSingleValueAppConfig( "QUOTE_DECLINE_CODE" ) );

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse resp ){

		Response responseObj = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		String action = request.getParameter( "action" );
		String flowIdentifier = request.getParameter( "flowId" );
		if( !Utils.isEmpty( action ) ){
			request.setAttribute( "action", action );
		}

		CommentsVO commentsVO = BeanMapper.map( request, CommentsVO.class );

		if( action.equals( "DECLINE_QUOTE" ) ){
			commentsVO.setPolicyStatus( DECLINE_STATUS );
		}
		if( !Utils.isEmpty( policyVO.getPolLinkingId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
			commentsVO.setPocPolicyId( policyVO.getPolLinkingId() ); // TODO: need to check with DB
			commentsVO.setPocEndtId( policyVO.getEndtId() );
			commentsVO.setIsQuote(policyVO.getIsQuote());
			
		}

		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		if( !Utils.isEmpty( userProfile ) ){
			commentsVO.setLoggedInUser( userProfile );
		}

		request.getSession( false ).setAttribute( AppConstants.GET_COMMENTS, commentsVO );

		Redirection redirection = null;
		/*
		 * Based on the 'flowIdentifier' parameter the comments will be stored to database using POLICY_ACTIONRH
		 * or the CommentsVo is added to session and control will be redirected to the RH in 'flowIdentifier' to 
		 * make use of CommentsVO further.
		 * If not flow identifier specified then default action is taken to store the comments in database.
		 */
		if( !Utils.isEmpty( flowIdentifier ) ){
			redirection = new Redirection( flowIdentifier, Redirection.Type.TO_NEW_OPERATION );
		}
		else{
			redirection = new Redirection( POLICY_ACTIONRH, Redirection.Type.TO_NEW_OPERATION );
		}
		responseObj.setRedirection( redirection );
		/* Set the response obtained to Policy Context so that next sections can obtain the value using policy context */

		logger.debug( CAPTURE_COMMENTS_RH, "set comments VO" + commentsVO );

		return responseObj;

	}

}
