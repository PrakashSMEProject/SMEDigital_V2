package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.referral.ReferralHandler;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TaskVO;

/**
 * This class will handle saving consolidated referral and 
 * redirection to Home Page on click of "YES" on consolidated referral window
 *
 */
public class LoadHomePageRefFlowRH implements IRequestHandler{

	private static final Logger log = Logger.getLogger( LoadHomePageRefFlowRH.class );
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
	
		Response responseObj = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		Integer sectionId= Integer.valueOf( AppConstants.SECTION_ID_PREMIUM);
		String svcIdentifier = (String) Utils.getSingleValueAppConfig( Utils.concat( "SVC_IDENTIFIER_", sectionId.toString() ) );	
		TaskVO taskVO = new TaskVO();
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if(!Utils.isEmpty(userProfile)){
			taskVO.setLoggedInUser(userProfile);	
		}
		taskVO = new ReferralHandler().map( request, taskVO, policyVO, "allReferrals" );
		// Oman multibranching implementation
		taskVO.setLocation(PASServiceContext.getLocation());
		TaskExecutor.executeTasks( svcIdentifier, taskVO );
		DataHolderVO<Long>  polLinkingId = new DataHolderVO<Long>();
		polLinkingId.setData(  policyVO.getPolLinkingId());	
		// Update the referral details in T_TRN_PAS_REFERRAL_DETAILS table in case of renewal quotes
		TaskExecutor.executeTasks( "UPDATE_REFERRAL_DETAILS", DAOUtils.getRenewalReferralVO(policyVO));
		
		// chnages by Anveshan for storing comments
		CommentsVO commentsVO = new CommentsVO(); 
		if(policyVO.getIsQuote()) commentsVO.setIsQuote(true);
		if(!Utils.isEmpty(  request.getParameter( com.Constant.CONST_REFERRALCOMMENTID )  )){
			//taskVO.setDesc(taskVO.getDesc().concat( "  "+  request.getParameter( com.Constant.CONST_REFERRALCOMMENTID )   ));
			commentsVO.setComment(   request.getParameter( com.Constant.CONST_REFERRALCOMMENTID )   );
		}
		if( !Utils.isEmpty( userProfile ) ){
			commentsVO.setLoggedInUser( userProfile );
		}
		if( !Utils.isEmpty( policyVO.getPolLinkingId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
			commentsVO.setPocPolicyId( policyVO.getPolLinkingId() ); // TODO: need to check with DB
			commentsVO.setPocEndtId( policyVO.getEndtId() );
		}
		commentsVO.setPolicyStatus( Byte.valueOf( Utils.getSingleValueAppConfig( "POLICY_REFERRED" ) ) );
		PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
		polComHolder.setComments( commentsVO );
		polComHolder.setPolicyDetails( policyVO );
		polComHolder = (PolicyCommentsHolder) TaskExecutor.executeTasks( "STORE_POL_COMMENTS", polComHolder );
		
		taskVO.setDesc(taskVO.getDesc().concat( "&#13;&#10; User Comments : "+     request.getParameter( com.Constant.CONST_REFERRALCOMMENTID )      ));
		// changes by Anveshan
		
		/* Send mail in case of referral.*/
		policyVO.setTaskDetails( taskVO );
		
		AppUtils.sendMail( policyVO, "REFERRAL" );
		
		if( !Utils.isEmpty( policyVO.getTaskDetails() ) && !Utils.isEmpty( policyVO.getTaskDetails().getDesc() )
				&& policyVO.getTaskDetails().getDesc().contains( Utils.getSingleValueAppConfig( "BROKER_CREDIT_LIMIT_APPROVAL" ) ) ){
			AppUtils.sendCreditLimitMail( policyVO, "REFERRAL_CREDIT_LIMIT", request );
		}
		
		
		/* TODO ReferralRH: Check this redirection, in the case of Premium Page */
		Redirection redirection = new Redirection( "/jsp/homePage_content.jsp", Type.TO_JSP );
		responseObj.setRedirection( redirection );
		return responseObj;
	}
	
}
