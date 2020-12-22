package com.rsaame.pas.ui.cmn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.UIOperationType;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.cmn.CommonVO;


/**
 * This request handler is a common request handler which handles common request
 * which is not very specific to any action or a request which can potentially be 
 * made through multiple actions.
 * 
 */
public class CommonActionRH implements IRequestHandler {

	final static Redirection branchSelect = new Redirection( "/jsp/branchSelect.jsp", Type.TO_JSP );
	final static Redirection productSelect = new Redirection( "/jsp/productSelect.jsp", Type.TO_JSP );
	final static String LOAD_GEN_INFO = "loadGenInfo";
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Response resp=new Response();
		//Commented for man
		/*PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );	
		if( Utils.isEmpty( policyContext ) ){
			throw new SystemException( "pas.cmn.policyContextUnavailable", null, "PolicyContext has not been initialised" );
		}*/
		
		/* Get the action from the request. If request parameter is null, set default it to NO_ACTION */
		String actionAttr = request.getParameter( AppConstants.ACTION );
		actionAttr = Utils.isEmpty( actionAttr ) ? "NO_ACTION" : actionAttr;
		String page =  request.getParameter( AppConstants.PAGE );
		request.setAttribute(AppConstants.PAGE , page);
		CommonActions action = CommonActions.valueOf( actionAttr );
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		switch(action){
			case REFERRAL_NO:
				handleReferralNoClickedAction(request);
				break;
			case NO_ACTION:	
				setNoAction(request); 
				request.getSession().removeAttribute( com.Constant.CONST_REFERRALMAP );
				request.getSession().removeAttribute( "referralMessage" );
				break;
			case REN_REFERRAL_NO:
				deleteRenewalRefData(request);
				break;
			case BRANCH_SELECT:
				setRedirection(request,response,resp,userProfile);
				break;
			case SET_BRANCH:
				setBranch(request,response,userProfile);
				break;
			case PRODUCT_SELECT:
				setRedirectionProduct(request,response,resp,userProfile);
				break;
			case POPULATE_COMMON_DETAILS:
				resp = populateCommonDetails( request , resp, actionAttr );
				break;
			case SAVE_REFFERAL_ONLY:
				resp = saveRefDetails( request , resp, actionAttr );
				break;

			case DEL_REFERRAL_NO:
				 deleteRefData( request );
				break;
				
			default:
				break;
		}
		
		return resp;
	}

	private Response saveRefDetails( HttpServletRequest request, Response resp, String actionAttr ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		String assignTO = null;
		String referalLoc = null;
		String referalComments = null;
		if( !Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ) ){
			CommonVO commonVO = policyContext.getCommonDetails();
			PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
			policyDataVO.setCommonVO( commonVO );
			if( !Utils.isEmpty( request.getParameter( "assignto" ) ) ){
				assignTO = request.getParameter( "assignto" );
			}
			referalLoc = PASServiceContext.getLocation();
			if( !Utils.isEmpty( request.getParameter( "referalComments" ) ) ){
				referalComments = request.getParameter( "referalComments" );
			}
			ReferralListVO referralListVO = new ReferralListVO();
			ReferralVO referralVO = new ReferralVO();
			List<ReferralVO> refVOList = new ArrayList<ReferralVO>();

			referralVO.setRefDataTextField( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) );
			Map<String, Map<String, String>> referralMessage =  (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ;
			
			request.getSession().removeAttribute( com.Constant.CONST_REFERRALMAP );
			
			referralVO.setLocationCode( PASServiceContext.getLocation() );
			refVOList.add( referralVO );
			referralListVO.setReferrals( refVOList );
			TaskVO taskVO = AppUtils.populateTaskVO( assignTO, referalLoc, referalComments, policyDataVO, request, commonVO );
			referralListVO.setTaskVO( taskVO );
			policyDataVO.setReferralVOList( referralListVO );
			UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
			policyDataVO.setLoggedInUser( userProfile );
			TaskExecutor.executeTasks( "SAVE_REFFERAL" , policyDataVO );
			
			if(!Utils.isEmpty( referralMessage ) && referralMessage.containsKey( "brokerMaxCreditLimit" )){
				policyDataVO.setLoggedInUser( (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO) );	
				AppUtils.sendCreditLimitMail( policyDataVO, "REFERRAL_CREDIT_LIMIT", request );
				
			}
			
			Redirection redirection = new Redirection( UIOperationType.HOME_PAGE_CONTENT.toString() , Type.TO_NEW_OPERATION );
			resp.setRedirection( redirection );
		}
		
		return resp;
	}

	/**
	 * Method to set the values to session when user clicks on No
	 * @param request
	 */
	private void setNoAction( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		if(!Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getLob() )){
			String lob = commonVO.getLob().toString();			
			if( LOB.HOME.equals( LOB.valueOf( lob ) ) && !Utils.isEmpty(request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ))){
				request.getSession().setAttribute( "displayedReferral", request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) );
			}
		}
		
	}

	private Response populateCommonDetails( HttpServletRequest request , Response resp, String action ){
		
		CommonVO commonVO = new CommonVO();
		/*Fetch the required params from request and set them into CommonVO.*/
		Long polQuoNo = Long.valueOf( request.getParameter( "polQuoNo" ) );
		Long endtId = Long.valueOf( request.getParameter( "endID" ) );
		String branch = request.getParameter( "branch" );
		String transType = request.getParameter( "tranType" );
		String lob = request.getParameter( "LOB" );
		SimpleDateFormat generalDateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
		String transDate = request.getParameter( "polEffectiveDate" );
		Boolean isQuote =  Boolean.valueOf(request.getParameter( "isQuote" ));		
		String appFlow = request.getParameter( "appflow" );
		String loadPage = request.getParameter( "loadPage" );
		String pageType = request.getParameter( "pageType" );
		String url = "";
		
		if(!Utils.isEmpty( transDate )){
			try{	
				commonVO.setPolEffectiveDate( generalDateFormat.parse( transDate ) );
			}
			catch( ParseException e ){
				throw new SystemException( "", null, "Error in parsing Transaction Date: Critical error" );
			}
		}
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		
		commonVO.setEndtId( endtId );
		commonVO.setLob( LOB.valueOf( lob ) );
		//Integer brCode =  SvcUtils.getLookUpCode( "PAS_BRANCH_CODE", userProfile.getRsaUser().getUserId().toString(), "ALL", branch );
		//Temporary Fix, following need to be removed need to be removed later
		Integer brCode =  SvcUtils.getLookUpCode( "BRANCH", userProfile.getRsaUser().getUserId().toString(), "ALL", branch );
		if(!Utils.isEmpty(brCode)){
			commonVO.setLocCode( brCode);
		}else{
			commonVO.setLocCode( Integer.valueOf(branch ) ); //Temporary Fix, need to be removed from future
		} 
		if(!Utils.isEmpty(transType)){
			commonVO.setDocCode( SvcUtils.getLookUpCode( "DOC_TYPE_ALL", "ALL", "ALL", transType ).shortValue() );
		}
		// Set the branch code to service context after transaction search
		if(!Utils.isEmpty(brCode)){
			PASServiceContext.setLocation(brCode.toString());
			request.getSession().setAttribute(AppConstants.CTX_LOCATION,brCode.toString());
		}
		
		String[] quoDocumentCodes = Utils.getMultiValueAppConfig( AppConstants.QUOTE_DOC_CODES, ",");
		String[] polDocumentCodes = Utils.getMultiValueAppConfig( AppConstants.POLICY_DOC_CODES, ",");
		if(!Utils.isEmpty(commonVO.getDocCode())){
			if( !Utils.isEmpty( quoDocumentCodes ) && quoDocumentCodes.length > 0
					&& CopyUtils.asList( quoDocumentCodes ).contains( commonVO.getDocCode().toString() )){
				
				commonVO.setQuoteNo( polQuoNo );
				commonVO.setIsQuote( Boolean.TRUE );
				commonVO.setAppFlow( Flow.VIEW_QUO );
			
			}else if(!Utils.isEmpty( polDocumentCodes ) && polDocumentCodes.length > 0
					&& CopyUtils.asList( polDocumentCodes ).contains( commonVO.getDocCode().toString() )){
			
				commonVO.setPolicyNo( polQuoNo );
				commonVO.setIsQuote( Boolean.FALSE );
				commonVO.setAppFlow( Flow.VIEW_POL );
			
			}
		}
		// case of loading the general Info in resolve referral case
		if(!Utils.isEmpty(appFlow) && appFlow.equalsIgnoreCase(Flow.RESOLVE_REFERAL.toString())){
			if(isQuote.booleanValue()){
				commonVO.setQuoteNo( polQuoNo );
				commonVO.setIsQuote( Boolean.TRUE );
				commonVO.setAppFlow(  Flow.valueOf(appFlow) );
				appFlow = request.getParameter( "appFlowMonoline" );
			} else {
				commonVO.setPolicyNo( polQuoNo );
				commonVO.setIsQuote( Boolean.FALSE );
				commonVO.setAppFlow(  Flow.valueOf(appFlow) );
				appFlow = request.getParameter( "appFlowMonoline" );
			}
		}
		commonVO = (CommonVO)TaskExecutor.executeTasks( action , commonVO );
		
		PolicyContextUtil.createPolicyContext( request, commonVO.getAppFlow().toString(), commonVO.getLob() );
		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		commonVO.setLoggedInUser( userProfile );
		commonVO.setCreatedBy( userProfile.getRsaUser().getUserId().toString() );
		context.setCommonDetails( commonVO );
		
		Redirection redirection = null;
		if(!Utils.isEmpty(loadPage) && loadPage.equals(LOAD_GEN_INFO)){
			redirection = new Redirection( "LOAD_COMMON_GENERAL_INFO_PAGE&commonVOloaded=Y", Redirection.Type.TO_NEW_OPERATION );
		} else {
			if( LOB.TRAVEL.equals( commonVO.getLob() ) ){
				redirection = new Redirection( "LOAD_RISK_PAGE", Redirection.Type.TO_NEW_OPERATION );
			}else if( LOB.HOME.equals( commonVO.getLob() ) ){
				redirection = new Redirection( "HOME_INSURANCE_PAGE&action=LOAD_DATA", Redirection.Type.TO_NEW_OPERATION );
			}else {
				url = "COMMON_FUNCTIONALITY&appFlow="+appFlow+"&lob="+commonVO.getLob();
				if(!Utils.isEmpty(pageType)){
					url = url+"&pageType="+pageType;
				}
				
				redirection = new Redirection( url, Redirection.Type.TO_NEW_OPERATION );
			}
		}
		resp.setRedirection( redirection );
		
		return resp;
	}

	private void setRedirectionProduct( HttpServletRequest request, HttpServletResponse response, Response resp, UserProfile userProfile ){
		resp.setRedirection( productSelect );
		request.setAttribute( AppConstants.USER_ID, userProfile.getRsaUser().getUserId().toString() );
		
	}

	private void setBranch( HttpServletRequest request, HttpServletResponse response, UserProfile userProfile ){
		String branchCode = request.getParameter( AppConstants.BRANCH_CODE );
		if( !Utils.isEmpty( branchCode ) ){
			
			request.getSession().setAttribute("LOCATION", branchCode );
			
			if(!Utils.isEmpty(request.getParameter( AppConstants.INSURED_ID ))){
				String insuredId = request.getParameter( AppConstants.INSURED_ID );
				request.setAttribute( AppConstants.INSURED_ID  , insuredId);
			}
			
			PASServiceContext.setLocation( branchCode );
			request.getSession().setAttribute(AppConstants.CTX_LOCATION,branchCode);
		}
		else{
			throw new SystemException( "pas.cmn.branchCodeNull", null, "Branch code is null in the request" );
		}

	}

	private void setRedirection( HttpServletRequest request, HttpServletResponse response, Response resp, UserProfile userProfile ){
		resp.setRedirection( branchSelect );
		request.setAttribute( AppConstants.USER_ID, userProfile.getRsaUser().getUserId().toString() );
		request.setAttribute( AppConstants.USER_BRANCH,  userProfile.getRsaUser().getBranchCode().toString() );
	}

	/**
	 * This method is used to handle case when no has been clicked from Referral pop-up.
	 * On click of NO , transaction is ended which was started on click of SAVE 
	 * for respective current section during that instant. 
	 * 
	 * @param request
	 */
	private void handleReferralNoClickedAction(HttpServletRequest request) {
		PolicyContext policyContext = validatePolicyContext(request); //Added for Oman 
		policyContext.endTransaction();
	}
	
	private void deleteRenewalRefData(HttpServletRequest request){
		PolicyContext policyContext = validatePolicyContext(request);//Added for Oman 
		TaskExecutor.executeTasks( "DELETE_RENEWAL_REFERRAL", policyContext.getPolicyDetails());
	}

	/*
	 * Added for Oman 
	 */
	private PolicyContext validatePolicyContext(HttpServletRequest request) {
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );	
		if( Utils.isEmpty( policyContext ) ){
			throw new SystemException( "pas.cmn.policyContextUnavailable", null, "PolicyContext has not been initialised" );
		};
		return policyContext;
	}

	private void deleteRefData(HttpServletRequest request){
		PolicyContext policyContext = validatePolicyContext(request);
		TaskExecutor.executeTasks( "DELETE_REFERRAL", policyContext.getPolicyDetails());
	}
	
}
