/**
 * 
 */
package com.rsaame.pas.policy.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1017029
 * since Phase3
 *
 */
public class CommonConvertToPolicyRH implements IRequestHandler{
	private static final int DISTRIBUTION_CHANNEL_BROKER = 4;
	private static final String PROCESS_PREMIUM_COLLECTION_JSP = "/jsp/policy/processPremiumCollection.jsp";
	private static final String POLICY_NUMBER_DISP_POP_UP_JSP = "/jsp/policy/policyNumberDispPopUp.jsp";
	private static final String CREDIT_CARD_TELE_MODE_POP_UP_JSP = "/jsp/policy/creditCardTeleModePopUp.jsp";
	private static final String CREDIT_CARD_SWIPE_MODE_POP_UP_JSP = "/jsp/policy/creditCardSwipeModePopUp.jsp";
	private static final String PAYABLE_PREMIUM_POP_UP_JSP = "/jsp/endtPremiumAmt.jsp";
	private static final String CHEQUE_MODE_POP_UP_JSP = "/jsp/policy/chequeModePopUp.jsp";
	private static final String CASH_MODE_POP_UP_JSP = "/jsp/policy/cashModePopUp.jsp";
	private static final String PAYMENT_MODE_POPUP_JSP = "/jsp/policy/payment-mode-popup.jsp";
	private static final String LOAD_COMMENTS = "LOAD_COMMENTS";
	private static final String CONVERT_TO_POLICY = "CONVERT_TO_POLICY";

	
	private static final Logger LOGGER = Logger.getLogger( CommonConvertToPolicyRH.class );
	private static final String REFERAL_ALERT_JSP = "/jsp/referalAlert.jsp";
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		String action = request.getParameter( "action" );
		Response resp = new Response();
		Redirection redirection = null;
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		PaymentVO paymentvo = new PaymentVO();
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
		LOGGER.debug("HHH***action is "+ action);
		
		
		if (commonVO.getLob().equals(LOB.WC)) {
			PolicyDataVO policydata1 = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
			Integer brkCode = policydata1.getGeneralInfo().getSourceOfBus().getBrokerName();
			if (!Utils.isEmpty(brkCode)) {

				java.util.List<Object> valueHolder = DAOUtils
						.getSqlResultSingleColumnPas(QueryConstants.GET_BROKER_ACC_STATUS, brkCode);
				BigDecimal bkrStatus = null;
				if (!Utils.isEmpty(valueHolder) && valueHolder.size() > 0
						&& !Utils.isEmpty(valueHolder.get(0))) {
					bkrStatus = ((BigDecimal) valueHolder.get(0));
				}
				if (!Utils.isEmpty(bkrStatus) && bkrStatus.compareTo(BigDecimal.ZERO) == 0) {
					throw new BusinessException("cmn.brkblocked.cl", null,
							"The Brk account is blocked");
				}
			}
		}
		
		switch( Action.valueOf( action ) ){
			case STORE_POL_COMMENTS:
			
				if(!Utils.isEmpty(commonVO))
				{
					TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( "LOB_CTP_VALIDATION_" + commonVO.getLob() ), commonVO );
				} 
				//To load comments page
				/*
				 * Check rules for convert to policy.
				 * 
				 */
				boolean isMessage = false;
				boolean isNotMessage = false;
				PolicyDataVO policydata = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
				policydata.setCommonVO( commonVO );
				String rules = request.getParameter( "rules" );
				if( commonVO.getIsQuote() && !Utils.isEmpty( policydata.getGeneralInfo().getSourceOfBus().getBrokerName() )
						&& !SectionRHUtils.executeReferralTaskValidation( policydata, action, "", new Integer[]{ 497 }, request )
						&& Utils.isEmpty( request.getParameter( "rules" ) ) ){

					Integer userRank = 999;
					
					if( !Utils.isEmpty( Utils.getSingleValueAppConfig( userProfile.getRsaUser().getHighestRole( commonVO.getLob().toString() ) ) ) ){

						userRank = Integer.valueOf( Utils.getSingleValueAppConfig( userProfile.getRsaUser().getHighestRole( commonVO.getLob().toString() ) ) );
					}
					
					if( !Utils.isEmpty( policydata.getReferralVOList() ) && !Utils.isEmpty( policydata.getReferralVOList().getReferrals() )
							&& !Utils.isEmpty( policydata.getReferralVOList().getReferrals().size() > 0 )
							&& !Utils.isEmpty( policydata.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){

						isMessage = policydata.getReferralVOList().getReferrals().get( 0 ).isMessage();
						isNotMessage = policydata.getReferralVOList().getReferrals().get( 0 ).isNotMessage();
						
					}
					
					if( (userRank <= 2 && !isNotMessage) || isMessage){
						if(isMessage){

							request.setAttribute( "displayMessage", policydata.getReferralVOList().getReferrals().get( 0 ).getReferralText().get(0) );
							request.getSession(false).setAttribute( com.Constant.CONST_REFERRALMAP, policydata.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
							redirection = new Redirection( REFERAL_ALERT_JSP, Type.TO_JSP );

						}
						
						else if (commonVO.getLob().toString().equalsIgnoreCase("WC")){
						request.setAttribute("referralListVO",policydata.getReferralVOList()); //HHH - added for WC rule issue
						request.setAttribute("LOB",commonVO.getLob().toString());
						request.setAttribute("isConsolidatedReferral","false");
						request.getSession(false).setAttribute( com.Constant.CONST_REFERRALMAP, policydata.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
						
						redirection = new Redirection( "/jsp/common/referralPopUp.jsp", Type.TO_JSP );
						}
					}
					else{

						request.setAttribute( "TASK_SAVE", "true" );
						redirection = AppUtils.prepareRedirection( request, response, resp, policydata );
					}
				}
				else if( commonVO.getIsQuote() && commonVO.getLob().toString().equalsIgnoreCase("WC")
						&& !SectionRHUtils.executeReferralTaskValidation( policydata, action, "", new Integer[]{ 497 }, request )
						 ){

							Integer userRank = 999;
							
							if( !Utils.isEmpty( Utils.getSingleValueAppConfig( userProfile.getRsaUser().getHighestRole( commonVO.getLob().toString() ) ) ) ){

								userRank = Integer.valueOf( Utils.getSingleValueAppConfig( userProfile.getRsaUser().getHighestRole( commonVO.getLob().toString() ) ) );
							}
							
							if( !Utils.isEmpty( policydata.getReferralVOList() ) && !Utils.isEmpty( policydata.getReferralVOList().getReferrals() )
									&& !Utils.isEmpty( policydata.getReferralVOList().getReferrals().size() > 0 )
									&& !Utils.isEmpty( policydata.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){

								isMessage = policydata.getReferralVOList().getReferrals().get( 0 ).isMessage();
								isNotMessage = policydata.getReferralVOList().getReferrals().get( 0 ).isNotMessage();
								
							}
							
							if( (userRank <= 2 && !isNotMessage) || isMessage){
								if(isMessage){

									request.setAttribute( "displayMessage", policydata.getReferralVOList().getReferrals().get( 0 ).getReferralText().get(0) );
									request.getSession(false).setAttribute( com.Constant.CONST_REFERRALMAP, policydata.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
									redirection = new Redirection( REFERAL_ALERT_JSP, Type.TO_JSP );

								}
								
								else if (commonVO.getLob().toString().equalsIgnoreCase("WC")){
								LOGGER.debug("HHH** Inside IF 3X");
								request.setAttribute("referralListVO",policydata.getReferralVOList()); //HHH - added for WC rule issue
								request.setAttribute("LOB",commonVO.getLob().toString());
								request.setAttribute("isConsolidatedReferral","false");
								request.getSession(false).setAttribute( com.Constant.CONST_REFERRALMAP, policydata.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
								
								redirection = new Redirection( "/jsp/common/referralPopUp.jsp", Type.TO_JSP );
								}
							}
							else{

								request.setAttribute( "TASK_SAVE", "true" );
								redirection = AppUtils.prepareRedirection( request, response, resp, policydata );
							}
				}
				else{

					if(!Utils.isEmpty( rules )){

						Map<String, Map<String, String>> referralMessage =  (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ;
						
						request.getSession(false).removeAttribute( com.Constant.CONST_REFERRALMAP );
						
						if(!Utils.isEmpty( referralMessage ) && referralMessage.containsKey( "brokerMinCreditLimit" )){

								policydata.setLoggedInUser( (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO) );
								AppUtils.sendCreditLimitMail( policydata, "MESSAGE_CREDIT_LIMIT", request );
							}
						}
					redirection = new Redirection( LOAD_COMMENTS, Type.TO_NEW_OPERATION );
				}
				if(!loggenInLoc.equals("30"))
				resp.setRedirection( redirection );
				else
					processComments( request, resp, policyContext, commonVO, paymentvo,response );
				break;
			case CAPTURE_COMMENT:
				//Storing the comments in a session
				processComments( request, resp, policyContext, commonVO, paymentvo,response );

				break;
			case PAYMENT_YES:
				
				if(Flow.AMEND_POL.equals( commonVO.getAppFlow() )){
					response.setHeader( "EndtFlow", "true" );
				}
				//For RSA user, if payment mode is selected as  'Yes',redirect to payment-mode-popup.jsp
				
				/*
				 * Payment is configured for few configured Brokers in Dubai.
				 * Getting the brokerId and passing it to get the configured Payment Mode. 
				 */
				String profile = null;
				String level1 = "ALL";
				if( !Utils.isEmpty( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )
						&& Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equalsIgnoreCase( "20" ) ){
					if( !Utils.isEmpty( userProfile ) ){
						profile = userProfile.getRsaUser().getProfile();
					}

					if( !Utils.isEmpty( profile ) ){
						if( profile.equalsIgnoreCase( com.Constant.CONST_BROKER ) ){
							level1 = userProfile.getRsaUser().getBrokerId().toString();
						}
					}
				}
				request.getSession().setAttribute( "LOB", commonVO.getLob().toString());
				request.getSession().setAttribute( "PAYMENT_BROKER_CODE", level1 );
				redirection = new Redirection( PAYMENT_MODE_POPUP_JSP, Type.TO_JSP );
				resp.setRedirection( redirection );
				break;
			case Cash:
				//If payment mode is selected as Cash
				setPaymentCodes( request );
				redirection = new Redirection( CASH_MODE_POP_UP_JSP, Type.TO_JSP );
				resp.setRedirection( redirection );
				break;
			case Cheque:
				setPaymentCodes( request );
				redirection = new Redirection( CHEQUE_MODE_POP_UP_JSP, Type.TO_JSP );
				resp.setRedirection( redirection );
				break;
			case Credit_Card_Swipe:
				setPaymentCodes( request );
				AppUtils.setDefaultTerminalId(request);
			
				redirection = new Redirection( CREDIT_CARD_SWIPE_MODE_POP_UP_JSP, Type.TO_JSP );
				resp.setRedirection( redirection );
				break;
			case Credit_Card_Telephone:
				setPaymentCodes( request );
				AppUtils.setDefaultTerminalId(request);
				redirection = new Redirection( CREDIT_CARD_TELE_MODE_POP_UP_JSP, Type.TO_JSP );
				resp.setRedirection( redirection );
				break;
			case STORE_PAYMENT_DETS:
				CommentsVO commentsVO;
				commentsVO = BeanMapper.map( request, CommentsVO.class );
				commentsVO.setPocPolicyId( commonVO.getPolicyId() );
				commentsVO.setPocEndtId( commonVO.getEndtId() );
				commentsVO.setPolicyStatus(commonVO.getStatus().byteValue());
				if(Utils.isEmpty( commonVO.getLob() )){
					throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
				}
				
				commentsVO.setLob( commonVO.getLob() );
				paymentvo = AppUtils.mapRequestToPaymentVO( request );
				request.getSession().setAttribute( AppConstants.GET_PAYMENT_DETS, paymentvo );
				if(!Flow.AMEND_POL.equals( commonVO.getAppFlow()) && !Flow.VIEW_POL.equals( commonVO.getAppFlow()) && Flow.VIEW_QUO.equals(AppUtils.getBasicFlowCommonResolveReferral( commonVO ))){
					covToPolicy( request, resp, policyContext, commentsVO );
				}else if(Flow.AMEND_POL.equals( commonVO.getAppFlow() ) || Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow() )){
					redirection = new Redirection( com.Constant.CONST_CAPTURE_ENDORSEMENT_TEXTACTION_COMMON_CAPTURE_AMEND_POLICY_ENDT_TEXT, Type.TO_NEW_OPERATION );
					resp.setRedirection( redirection );
				}
				break;
				
			case PAYMENT_NO:
				
				/*
				 * Invoke the rule for premium collection collection;
				 * PolicyDataVO should be not null, and null pointer should be thrown here if its null
				 */
				Integer[] processCollection = { Integer.valueOf( Utils.getSingleValueAppConfig( "CREDIT_MODE" ) ) };
				commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
				
				PolicyDataVO policyDataVO = null;
				
				if(commonVO.getLob().equals( LOB.HOME )){
					 policyDataVO = (HomeInsuranceVO) TaskExecutor.executeTasks( com.Constant.CONST_POLICY_DATAVO_FOR_LOB_FROM_COMMONVO, commonVO );
				}else if(commonVO.getLob().equals( LOB.TRAVEL )){
					policyDataVO = (TravelInsuranceVO) TaskExecutor.executeTasks( com.Constant.CONST_POLICY_DATAVO_FOR_LOB_FROM_COMMONVO, commonVO );
				} else{
					policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( com.Constant.CONST_POLICY_DATAVO_FOR_LOB_FROM_COMMONVO, commonVO );
				}
				
				if( !Utils.isEmpty( request.getSession().getAttribute( com.Constant.CONST_PAYABLE_PREMIUM ) ) ){
					com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsementVOList = null;
					EndorsmentVO endorsementVO = null;
					BigDecimal payablePrm = null;
					
					if( Utils.isEmpty( policyDataVO.getEndorsmentVO() ) ){
						endorsementVOList = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
						endorsementVO = new EndorsmentVO();
					}else{
						endorsementVOList = policyDataVO.getEndorsmentVO();
						endorsementVO = policyDataVO.getEndorsmentVO().get( SvcConstants.zeroVal );
					}
					
					if( !Utils.isEmpty( request.getSession().getAttribute( com.Constant.CONST_PAYABLE_PREMIUM ) )){
						payablePrm = new BigDecimal( request.getSession().getAttribute( com.Constant.CONST_PAYABLE_PREMIUM ).toString().replaceAll( "[,]", "" ) );
						//payablePrm was rounded Off but not assigned so value was sent without formatting to Rule Engine
						payablePrm = payablePrm.setScale( 2,BigDecimal.ROUND_UP );
						endorsementVO.setPayablePremium( payablePrm.doubleValue() );
						endorsementVOList.add( endorsementVO );
						policyDataVO.setEndorsmentVO( endorsementVOList );
					}
					
				}
				//setting commonVO to the retrieved PolicyDataVO
				policyDataVO.setCommonVO( commonVO );
				if( !SectionRHUtils.executeReferralTaskForTravel( policyDataVO, "", "Process Collection", processCollection , request ) ){
					if(policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase("HOME") ||  policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase("TRAVEL")){
						redirection = AppUtils.prepareRedirection( request, response, resp, policyDataVO );
						resp.setRedirection( redirection );
						return resp;
					}
					else
					{
						SectionRHUtils.redirectReferralForMonoline( policyDataVO, request, response, resp, redirection );
						return resp;
					}
				}
				
				
				//TODO: Check for credit limit
				paymentvo.setPaymentDone( false );
				paymentvo.setAmount( Double.valueOf( request.getSession().getAttribute( com.Constant.CONST_PAYABLE_PREMIUM ).toString().replaceAll( "[,]", "" ) ) );
				request.getSession().setAttribute( AppConstants.GET_PAYMENT_DETS, paymentvo );
				if(!Flow.AMEND_POL.equals( commonVO.getAppFlow()) && !Flow.VIEW_POL.equals( commonVO.getAppFlow()) && !Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow())){
					covToPolicy( request, resp, policyContext,null );
				}else if(Flow.AMEND_POL.equals( commonVO.getAppFlow() ) || Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow() )){
					redirection = new Redirection( com.Constant.CONST_CAPTURE_ENDORSEMENT_TEXTACTION_COMMON_CAPTURE_AMEND_POLICY_ENDT_TEXT, Type.TO_NEW_OPERATION );
					resp.setRedirection( redirection );
				}
				break;

			case CONVERT_TO_POLICY:
				if(!Flow.AMEND_POL.equals( commonVO.getAppFlow()) && !Flow.VIEW_POL.equals( commonVO.getAppFlow()) && !Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow())){
					covToPolicy( request, resp, policyContext,null );
				}
				break;
			
			case PAYABLE_PREMIUM:
				
					request.getSession().setAttribute( com.Constant.CONST_PAYABLE_PREMIUM, request.getParameter( "payablePremium" ) );
					redirection = new Redirection( PAYABLE_PREMIUM_POP_UP_JSP, Type.TO_JSP );
					resp.setRedirection( redirection );
					break;
		}

		return resp;
	}

	/**
	 * @param request
	 */
	private void setPaymentCodes( HttpServletRequest request ){
		request.getSession().setAttribute( "paymentCode", request.getParameter( "paymentMode" ) );
		request.getSession().setAttribute( "paymentModeCode", request.getParameter( "payModeCode" ) );
	}

	/**
	 * This method 1.commentsVO is created.
	 * 2. Broker user, RSA user are redirected accordingly
	 * @param request
	 * @param resp
	 * @param policyContext
	 * @param commonVO
	 * @param paymentvo
	 */
	private void processComments( HttpServletRequest request, Response resp, PolicyContext policyContext, CommonVO commonVO, PaymentVO paymentvo,HttpServletResponse response  ){
		Redirection redirection;
		CommentsVO commentsVO;
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
		commentsVO = BeanMapper.map( request, CommentsVO.class );
		commentsVO.setPocPolicyId( commonVO.getPolicyId() );
		commentsVO.setPocEndtId( commonVO.getEndtId() );
		
		if(Utils.isEmpty( commonVO.getLob() )){
			throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
		}
		
		commentsVO.setLob( commonVO.getLob() );
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		
		if( !Utils.isEmpty( userProfile ) ){
			commentsVO.setLoggedInUser( userProfile );
			commonVO.setLoggedInUser( userProfile );
		}
		if(!loggenInLoc.equals("30"))
		request.getSession().setAttribute( AppConstants.GET_COMMENTS, commentsVO );

		String profile = null;
		String brokerId = null;
		if( !Utils.isEmpty( userProfile ) ){
			profile = userProfile.getRsaUser().getProfile();
			brokerId = userProfile.getRsaUser().getBrokerId().toString();
		}
		if( !Utils.isEmpty( profile ) ){
			/***
			 * If logged in user is Broker only,
			 * then payment mode should not be displayed and only generated policy number pop-up should be shown
			 */
			boolean paymentRequired = false;
			String[] brokerCodes = Utils.getMultiValueAppConfig( "BROKER_CODES_FOR_PAYMENT" );
			/*
			 * Payment is configured for few Configured Brokers in DUBAI environment.
			 * Checking the configured broker list to allow payment option
			 */
			if( !Utils.isEmpty( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )
					&& Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equalsIgnoreCase( "20" ) && profile.equalsIgnoreCase( com.Constant.CONST_BROKER )
					&& !Utils.isEmpty( brokerCodes ) && Arrays.asList( brokerCodes ).contains( brokerId ) ){
				paymentRequired = true;
			}
			
			if( profile.equalsIgnoreCase( com.Constant.CONST_BROKER ) && !paymentRequired ){
				paymentvo.setPaymentDone( false );
				if(!Utils.isEmpty( commonVO.getPremiumVO() ) && !Utils.isEmpty(  commonVO.getPremiumVO().getPremiumAmt()  )){
					
					paymentvo.setAmount( commonVO.getPremiumVO().getPremiumAmt() );
				}else{
					paymentvo.setAmount( 0.0 );
				}
				request.getSession().setAttribute( AppConstants.GET_PAYMENT_DETS, paymentvo );
				
				LOGGER.info( request.getParameter( "endEffDate" ) );
				if(!Utils.isEmpty( request.getParameter( "endEffDate" ) ) || !Utils.isEmpty(commonVO.getEndtEffectiveDate())){					
						redirection = new Redirection( com.Constant.CONST_CAPTURE_ENDORSEMENT_TEXTACTION_COMMON_CAPTURE_AMEND_POLICY_ENDT_TEXT, Type.TO_NEW_OPERATION );
						resp.setRedirection( redirection );
					//}
				}else{
					covToPolicy( request, resp, policyContext,null);
				}
				
			}
				//If logged in user is an 'EMPLOYEE' , then he should be directed to premium collection
				
			else if( profile.equalsIgnoreCase( com.Constant.CONST_EMPLOYEE ) || ( profile.equalsIgnoreCase( com.Constant.CONST_BROKER ) && paymentRequired ) ){
				if((Flow.VIEW_POL.equals( commonVO.getAppFlow() )|| Flow.AMEND_POL.equals( commonVO.getAppFlow() )|| Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow() ) )&&(!Utils.isEmpty(request.getSession().getAttribute( com.Constant.CONST_PAYABLE_PREMIUM ))) && (Double.valueOf(request.getSession().getAttribute( com.Constant.CONST_PAYABLE_PREMIUM ).toString().replaceAll( "[,]", "" ))<=0.0)){
					paymentvo.setPaymentDone( false );
					if(!Utils.isEmpty( commonVO.getPremiumVO() ) && !Utils.isEmpty(  commonVO.getPremiumVO().getPremiumAmt()  )){
						
						paymentvo.setAmount( commonVO.getPremiumVO().getPremiumAmt() );
					}else{
						paymentvo.setAmount( 0.0 );
					}
					request.getSession().setAttribute( AppConstants.GET_PAYMENT_DETS, paymentvo );
					redirection = new Redirection( com.Constant.CONST_CAPTURE_ENDORSEMENT_TEXTACTION_COMMON_CAPTURE_AMEND_POLICY_ENDT_TEXT, Type.TO_NEW_OPERATION );
					resp.setRedirection( redirection );
			}
			else if(!Utils.isEmpty( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) )
					&& Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ).equalsIgnoreCase( "30" ))
			{
				PolicyDataVO policydata = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
				if(profile.equalsIgnoreCase(com.Constant.CONST_EMPLOYEE) && policydata.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals(4))
				{
					paymentvo.setPaymentDone( false );
					if(!Utils.isEmpty( commonVO.getPremiumVO() ) && !Utils.isEmpty(  commonVO.getPremiumVO().getPremiumAmt()  )){
						
						paymentvo.setAmount( commonVO.getPremiumVO().getPremiumAmt() );
					}else{
						paymentvo.setAmount( 0.0 );
					}
					request.getSession().setAttribute( AppConstants.GET_PAYMENT_DETS, paymentvo );
					//Added to Fix Endorsement Issue for OMAN
                    if(!Flow.VIEW_POL.equals(commonVO.getAppFlow()) && !Flow.AMEND_POL.equals(commonVO.getAppFlow())){
                        covToPolicy(request, resp, policyContext, null);
                    } else {
					redirection = new Redirection(
						com.Constant.CONST_CAPTURE_ENDORSEMENT_TEXTACTION_COMMON_CAPTURE_AMEND_POLICY_ENDT_TEXT,
									Type.TO_NEW_OPERATION);
							resp.setRedirection(redirection);
                    }
				}
				else
				{
					request.getSession().setAttribute( AppConstants.GET_PAYMENT_DETS, paymentvo );
					request.setAttribute( "policyType", "B2B" );
					redirection = new Redirection( PROCESS_PREMIUM_COLLECTION_JSP, Type.TO_JSP );
					resp.setRedirection( redirection );
				}
			}
			else{
				request.getSession().setAttribute( AppConstants.GET_PAYMENT_DETS, paymentvo );
				request.setAttribute( "policyType", "B2B" );
				redirection = new Redirection( PROCESS_PREMIUM_COLLECTION_JSP, Type.TO_JSP );
				resp.setRedirection( redirection );
			}
		  }

		}
	}

	/**
	 * Convert to policy, payment save and comments save are done here.
	 * @param request
	 * @param resp
	 * @param commentsVO
	 * @param policyContext
	 * @param cashComments 
	 * @param paymentvo
	 */
	private void covToPolicy( HttpServletRequest request, Response resp, PolicyContext policyContext, CommentsVO cashComments){
		Redirection redirection;
		CommonVO commonVO;
		PaymentVO paymentvo=null;
		CommentsVO commentsVO=null;
		CommentsVO cashComment = cashComments;
		if( !Utils.isEmpty( request.getSession( false ).getAttribute( AppConstants.GET_PAYMENT_DETS ) ) ){
			paymentvo = (PaymentVO) request.getSession( false ).getAttribute( AppConstants.GET_PAYMENT_DETS );
		}

		if( !Utils.isEmpty( request.getSession( false ).getAttribute( AppConstants.GET_COMMENTS ) ) ){
			commentsVO = (CommentsVO) request.getSession( false ).getAttribute( AppConstants.GET_COMMENTS );
		}
		commonVO = policyContext.getCommonDetails();

		/*
		 * After converting the policy, checking if the payment details present in session then save them in database againest the 
		 * generated policy number.
		 */
		policyContext.startTransaction();
		List<BaseVO> inputVoList = new ArrayList<BaseVO>();

		inputVoList.add( new PolicyVO() );

		if( !Utils.isEmpty( paymentvo ) ){
			inputVoList.add( paymentvo );
		}

		if( !Utils.isEmpty( commonVO ) ){
			inputVoList.add( commonVO );
		}
		else{
			inputVoList.add( new CommonVO() );
		}

		DataHolderVO<List<BaseVO>> dataHolderVO = new DataHolderVO<List<BaseVO>>();

		dataHolderVO.setData( inputVoList );
		
		if( !Utils.isEmpty( commentsVO ) ){

			PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
			polComHolder.setComments( commentsVO );
			TaskExecutor.executeTasks( AppConstants.STORE_POL_COMMENTS, polComHolder );
		}
		if( !Utils.isEmpty( cashComment ) ){

			PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
			
			polComHolder.setComments( cashComment );
			TaskExecutor.executeTasks( AppConstants.STORE_POL_COMMENTS, polComHolder );
		}


		commonVO = (CommonVO) TaskExecutor.executeTasks( CONVERT_TO_POLICY, dataHolderVO );

		/* For convert to policy,status will be always set to 7 i.e Converted to policy.  */
		commonVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( "CONV_TO_POL" ) ) );

		
		policyContext.commit();

		request.setAttribute( "policyNumber", commonVO.getConcatPolicyNo() );

		resp.setSuccess( true );
		resp.setData( commonVO );

		PolicyContextUtil.getPolicyContext( request ).setCommonDetails( commonVO );

		request.setAttribute( "status", commonVO.getStatus() );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.getSession().removeAttribute( AppConstants.GET_COMMENTS );
		request.getSession().removeAttribute( AppConstants.GET_PAYMENT_DETS );
		/*
		 * When converted to policy the flow will change to view quote
		 * This is because the quote converted to policy cannot be edited
		 */
		PolicyContextUtil.getPolicyContext( request ).setAppFlow( Flow.VIEW_QUO );
		request.setAttribute( AppConstants.FUNTION_NAME, Flow.VIEW_QUO.toString() );
		request.setAttribute( AppConstants.SCREEN_NAME, "PREMIUM" );
		redirection = new Redirection( POLICY_NUMBER_DISP_POP_UP_JSP, Type.TO_JSP );
		resp.setRedirection( redirection );
	}

	public boolean isRSAUserWithBrokerDistChannel( String profile, CommonVO commonVO ){
		Boolean isRSAUserWithBrokerChannel = Boolean.FALSE;
		Integer distributionChannel = null;

		PolicyDataVO policyDatavO = (PolicyDataVO) TaskExecutor.executeTasks( "COMMON_CONVERT_TO_POLICY", commonVO );

		if( profile.equalsIgnoreCase( com.Constant.CONST_EMPLOYEE ) && !Utils.isEmpty( policyDatavO.getGeneralInfo() ) && !Utils.isEmpty( policyDatavO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyDatavO.getGeneralInfo().getSourceOfBus().getDistributionChannel() ) ){

			distributionChannel = policyDatavO.getGeneralInfo().getSourceOfBus().getDistributionChannel();

			if( distributionChannel.equals( DISTRIBUTION_CHANNEL_BROKER ) || !Utils.isEmpty( policyDatavO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ){
				isRSAUserWithBrokerChannel = Boolean.TRUE;
			}

		}
		return isRSAUserWithBrokerChannel;
	}
	
	private enum Action{

		STORE_POL_COMMENTS, 
		STORE_PAYMENT_DETS, 
		CAPTURE_COMMENT, 
		CONVERT_TO_POLICY, 
		PAYMENT_NO, 
		PAYMENT_YES, 
		Cash, 
		Cheque, 
		Credit_Card_Swipe, 
		Credit_Card_Telephone,
		PAYABLE_PREMIUM
	}

}
