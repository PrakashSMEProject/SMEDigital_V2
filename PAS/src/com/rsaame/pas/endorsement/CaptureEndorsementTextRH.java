/**
 * 
 */
package com.rsaame.pas.endorsement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
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
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.lookup.ui.Constants;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyDataVOEndtMapper;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyDataVOMapper;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyVOEndtMapper;
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
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.StandardClause;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1014241
 *
 */
public class CaptureEndorsementTextRH implements IRequestHandler{

	private final static Logger LOGGER = Logger.getLogger( CaptureEndorsementTextRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		String shortTerm = request.getParameter( "shortTerm" );	
		
		Response responseObj = new Response();
		String action = request.getParameter( com.Constant.CONST_ACTION );
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		if( LOGGER.isInfo() ) LOGGER.info( " ACTION: " + action );
		
		
		
		

		
		if( "CAPTURE_ENDORSEMENT_TEXT_UPDATE".equals( action ) ){
			Redirection redirection = null;
			policyContext = PolicyContextUtil.getPolicyContext( request );
			PolicyVO policyVO = policyContext.getPolicyDetails();

			BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = BeanMapperFactory.getMapperInstance( RequestToPolicyVOEndtMapper.class );
			policyVO = requestBeanMapper.mapBean( request, policyVO );
			
			EndorsmentVO validEndRecord = null;
			List<EndorsmentVO> endorseList = policyVO.getEndorsements();
			
			if(policyVO.getEndorsements().get( 0 ).getEndType().equalsIgnoreCase( "NIL" )){
				request.setAttribute( com.Constant.CONST_ACTION, "SAVE_ENDORSMENT_COMMENTS" );
				redirection = new Redirection( "LOAD_COMMENTS&action=SAVE_ENDORSMENT_COMMENTS", Redirection.Type.TO_NEW_OPERATION );
			}
			else
				redirection = new Redirection( "jsp/endtPremiumAmt.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
		}
		
		else if( "CAPTURE_ENDORSEMENT_TEXT_SAVE".equals( action ) ){
			Redirection redirection = null;
			policyContext = PolicyContextUtil.getPolicyContext( request );
			PolicyVO policyVO = policyContext.getPolicyDetails();
			
			mapEndtText( request, policyVO );

			BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = BeanMapperFactory.getMapperInstance( RequestToPolicyVOEndtMapper.class );
			policyVO = requestBeanMapper.mapBean( request, policyVO );
			
			EndorsmentVO validEndRecord = null;
			List<EndorsmentVO> endorseList = policyVO.getEndorsements();
			
			/*if(policyVO.getEndorsements().get( 0 ).getEndType().equalsIgnoreCase( "NIL" )){*/
				request.setAttribute( com.Constant.CONST_ACTION, "SAVE_ENDORSMENT_COMMENTS" );
				redirection = new Redirection( "LOAD_COMMENTS&action=SAVE_ENDORSMENT_COMMENTS", Redirection.Type.TO_NEW_OPERATION );
			/*}
			else
				redirection = new Redirection( "jsp/endtPremiumAmt.jsp", Type.TO_JSP );*/
			responseObj.setRedirection( redirection );
		}
		else if( "CAPTURE_CANCEL_ENDORSEMENT_TEXT".equals( action ) ){

			PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();
			BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = BeanMapperFactory.getMapperInstance( RequestToPolicyVOEndtMapper.class );
			policyVO = requestBeanMapper.mapBean( request, policyVO );

			List<EndorsmentVO> endorsmentVOs = policyVO.getEndorsements();
			if( LOGGER.isInfo() ) LOGGER.info( " endorsmentVOs: " + endorsmentVOs.size() );

			//BaseVO baseVO =  TaskExecutor.executeTasks(action, policyVO );
			responseObj.setData( policyVO );

		}
		else if( "CAPTURE_ENDORSEMENT_TEXT".equals( action ) ){

			policyContext = PolicyContextUtil.getPolicyContext( request );
			PolicyVO policyVO = policyContext.getPolicyDetails();

			mapTradeLicNo( request, policyVO );

			/*CommentsVO commentsVO = BeanMapper.map( request, CommentsVO.class );
			if( !Utils.isEmpty( policyVO.getPolLinkingId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
				commentsVO.setPocPolicyId( policyVO.getPolLinkingId() ); // TODO: need to check with DB
				commentsVO.setPocEndtId( policyVO.getEndtId() );
			}

			UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
			if( !Utils.isEmpty( userProfile ) ){
				commentsVO.setLoggedInUser( userProfile );
			}

			request.getSession( false ).setAttribute( AppConstants.GET_COMMENTS, commentsVO );*/
			
			
			

			Integer brkCode =policyVO.getGeneralInfo().getSourceOfBus().getBrokerName();
	        if( !Utils.isEmpty( brkCode ) ){

	              java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_BROKER_ACC_STATUS, brkCode );
	              BigDecimal bkrStatus = null;
	              if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
	                     bkrStatus = ( (BigDecimal) valueHolder.get( 0 ) );
	              }
	              if( !Utils.isEmpty( bkrStatus ) && bkrStatus.compareTo( BigDecimal.ZERO ) == 0 ){
	            	     response.setHeader("isBrokerBlocked","true");
	                     throw new BusinessException( "cmn.brkblocked.cl", null, "The Brk account is blocked" );
	              }
	        }
			
			
			
			
			
			

			BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = BeanMapperFactory.getMapperInstance( RequestToPolicyVOEndtMapper.class );
			policyVO = requestBeanMapper.mapBean( request, policyVO );
			
			/*check if the user has refund endorsement referral*/
			if( hasRefundEndorsementReferral( request, responseObj, policyVO ) ){
				return responseObj;
			}
			
			String saveEndtDetails = request.getParameter( "saveEndDetails" );
			if(policyVO.getEndorsements().get( 0 ).getEndType().equalsIgnoreCase( "EXTRA" ) && Utils.isEmpty( saveEndtDetails )){
				if(Utils.getSingleValueAppConfig("ISCREDITCHKRULEREQ").equalsIgnoreCase("YES") && !SectionRHUtils.executeReferralTask( responseObj,"CONV_TO_POLICY_REF", policyVO, "CONV_TO_POLICY_REF" ) ){
					ReferralListVO referralListVO = (ReferralListVO) responseObj.getData();
					boolean allowToConvert = false;
					
					allowToConvert = AppUtils.checkForApprovedReferral( policyVO, referralListVO, allowToConvert , false );

					if( !allowToConvert ){
						if( !Utils.isEmpty( referralListVO ) && !Utils.isEmpty( referralListVO.getReferrals() ) ){
							//Used StringBuffer to avoid "+" to fix sonar violation 0n 13-9-2017
							StringBuffer consolidatedReferralMsgBuffer=new StringBuffer();
							String consolidatedReferralMessage = "";
							//Iterating all the referrals to get consolidated message 
							List<ReferralVO> referralVOs = referralListVO.getReferrals();
							for( ReferralVO voTemp : referralVOs ){
								if( !Utils.isEmpty( voTemp ) ){
									//consolidatedReferralMessage +=  voTemp.getReferralText() + "\n";
									consolidatedReferralMessage=consolidatedReferralMsgBuffer.append(voTemp.getReferralText()).append("\n").toString();
								}
							}
							policyContext.getPolicyDetails().setConCatRefMsgs( consolidatedReferralMessage );
							boolean isMessage = referralListVO.getReferrals().get( 0 ).isMessage();
							
							if(isMessage){
								response.setHeader("isMessage","true");
							}
							responseObj.setResponseType( Response.Type.JSON );
							response.setHeader("referral","true");
						}
						
						
						return responseObj;
					}
					else{
						responseObj = new Response();
					}
				}
			}
			// Below code is added by Anveshan. This is to handle Student Liability exclusion clauses
			if(policyContext.isSectionPresent( 6 )){
				DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
				Object[] input = { policyVO, 6 };
				inputData.setData( input );
					DataHolderVO<List<StandardClause>> holderVO = (DataHolderVO<List<StandardClause>>) TaskExecutor.executeTasks( "LOAD_FIRST_CLAUSE", inputData );
					policyVO.setStandardClause( holderVO.getData() );
			}
			BaseVO baseVO = TaskExecutor.executeTasks( action, policyVO );
			
			if(!Utils.isEmpty( saveEndtDetails )){
				if( !Utils.isEmpty( policyVO.getConCatRefMsgs() )
						&& policyVO.getConCatRefMsgs().contains( Utils.getSingleValueAppConfig( "BROKER_CREDIT_LIMIT_MESSAGE" ) ) ){
					AppUtils.sendCreditLimitMail( policyVO, "MESSAGE_CREDIT_LIMIT", request );
				}
			}
			
			policyVO = (PolicyVO) baseVO;
			if( LOGGER.isInfo() ) LOGGER.info( " endorsments size :  - " + policyVO.getEndorsements().size() );
			// Remove "To Be Provided"
			if(!Utils.isEmpty( policyVO.getEndorsements() )){
				for(EndorsmentVO endt:policyVO.getEndorsements()){
					if(!Utils.isEmpty( endt.getEndText() )){
						 endt.setEndText( endt.getEndText().trim().replace( "( To be provided )", "" ) );
						 endt.setEndText( endt.getEndText().trim().replace( "to To be provided", "" ) );
						 endt.setEndText( endt.getEndText().trim().replace( "from To be provided", "" ) );
						 endt.setEndText( endt.getEndText().trim().replace( "To be provided", "" ) );
					}
				}
			}
			
			request.setAttribute( com.Constant.CONST_ENDORSMENTS, policyVO.getEndorsements() );
			responseObj.setData( baseVO );
			Redirection redirection = null;
			request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
			request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );
			redirection = new Redirection( com.Constant.CONST_JSP_CAPTUREENDORSEMENTTEXT_JSP, Type.TO_JSP );
			response.setHeader( com.Constant.CONST_ENDORSEMENTTEXT, "true" );
			responseObj.setRedirection( redirection );

		}
		else if( "CAPTURE_CANCEL_POLICY_ENDT_TEXT".equals( action ) ){

			PolicyVO policyVO = policyContext.getPolicyDetails();

			EndorsmentVO endorsementVo = ( policyVO.getEndorsements() != null ) ? policyVO.getEndorsements().get( 0 ) : null;
			if( endorsementVo != null ) endorsementVo.setPolicyToBeCancelled( true );
			ClaimsSvc claimsService = (ClaimsSvc) Utils.getBean( "claimsSvc" );
			if( claimsService.checkClaimsExistForPolicyNumber( policyVO.getPolicyNo() ) ){
				policyVO.setClaimsHistoryExistInMissippi( true );
			}

			mapTradeLicNo( request, policyVO );

			/*check if the user has refund endorsement referral*/
			if( hasRefundEndorsementReferral( request, responseObj, policyVO ) ){
				return responseObj;
			}

			policyVO = (PolicyVO) TaskExecutor.executeTasks( action, policyVO );
			request.setAttribute( com.Constant.CONST_ENDORSMENTS, policyVO.getEndorsements() );
			request.setAttribute( com.Constant.CONST_AMENDFLOWTYPE, "CANCEL_POLICY" );
			responseObj.setData( policyVO );
			Redirection redirection = null;
			redirection = new Redirection( com.Constant.CONST_JSP_CAPTUREENDORSEMENTTEXT_JSP, Type.TO_JSP );
			response.setHeader( com.Constant.CONST_ENDORSEMENTTEXT, "true" );
			responseObj.setRedirection( redirection );
		}
		else if( "CHECK_CANCEL_REFFERAL".equals( action ) ){

			//Check discount loading rule for cancel policy. Kishore
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			Redirection redirection = null;
			PolicyDataVO policyDataVO = null;
			if( commonVO.getLob().equals( LOB.HOME ) ){
				policyDataVO = (HomeInsuranceVO) TaskExecutor.executeTasks( com.Constant.CONST_POLICY_DATAVO_FOR_LOB_FROM_COMMONVO, commonVO );
			}
			else if( commonVO.getLob().equals( LOB.TRAVEL ) ){
				policyDataVO = (TravelInsuranceVO) TaskExecutor.executeTasks( com.Constant.CONST_POLICY_DATAVO_FOR_LOB_FROM_COMMONVO, commonVO );
			}
			else
			{
      				policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( com.Constant.CONST_POLICY_DATAVO_FOR_LOB_FROM_COMMONVO, commonVO );
			}
			
			boolean isRuleRequired = false;
			if( !Utils.isEmpty( Double.valueOf( request.getParameter( com.Constant.CONST_CANCELDISCOUNT ) ) ) ){
				policyDataVO.getPremiumVO().setDiscOrLoadPerc( Double.valueOf( request.getParameter( com.Constant.CONST_CANCELDISCOUNT ) ) );
				if( Utils.isEmpty( commonVO.getPremiumVO() ) ){
					commonVO.setPremiumVO( new PremiumVO() );
				}
				commonVO.getPremiumVO().setDiscOrLoadPerc( Double.valueOf( request.getParameter( com.Constant.CONST_CANCELDISCOUNT ) ) );
				if(commonVO.getPremiumVO().getDiscOrLoadPerc().doubleValue() < 0.0 ){
					isRuleRequired = true;
				}
			}

			policyDataVO.setCommonVO( commonVO );
			Integer[] cancelDiscCheck = { Integer.valueOf( Utils.getSingleValueAppConfig( "CANCEL_DISCOUNT_CHECK" ) ) };
			if(  isRuleRequired &&  !SectionRHUtils.executeReferralTaskForTravel( policyDataVO, action, "CANCEL_DISCOUNT_CHECK", cancelDiscCheck, request ) ){
				redirection = AppUtils.prepareRedirection( request, response, responseObj, policyDataVO );
				response.setHeader( "refferalType", "hardStop" );
				responseObj.setRedirection( redirection );
			}else{
				action = "COMMON_CAPTURE_CANCEL_POLICY_ENDT_TEXT";
				captureCancelPolicyText( request, response, responseObj, action );
				response.setHeader( com.Constant.CONST_ENDORSEMENTTEXT, "true" );
			}
		}
		else if( "COMMON_CAPTURE_CANCEL_POLICY_ENDT_TEXT".equals( action ) ){
			captureCancelPolicyText( request, response, responseObj, action );
		}
		else if( "COMMON_CAPTURE_CANCEL_ENDORSEMENT_TEXT".equals( action ) ){
			//retrieve policyDataVO using baseLoadOperation
			BaseVO baseVO = null;
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();

			baseVO = TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_DATAVO_FROM_COMMONVO ), commonVO );
			//setting commonVO to the retrieved PolicyVO
			PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
			policyDataVO.setCommonVO( commonVO );
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
					com.Constant.CONST_FORMAT_YYYY_MM_DD );
			policyDataVO.setEndEffectiveDate( converter.getAFromB( request.getParameter( com.Constant.CONST_ENDEFFDATE ) ) );
			BaseBeanToBeanMapper<HttpServletRequest, PolicyDataVO> requestBeanMapper = BeanMapperFactory
					.getMapperInstance( RequestToPolicyDataVOMapper.class );
			policyDataVO = requestBeanMapper.mapBean( request, policyDataVO );
			policyDataVO.setEndtId( commonVO.getEndtId() );
			policyDataVO.setPolExpiryDate( policyDataVO.getScheme().getExpiryDate() );
			policyDataVO.setStartDate( policyDataVO.getScheme().getEffDate() );
			//Getting EndorsmentVO
			com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
			
			/*Added for Short Term  Cancellation - OMAN*/
			if(!Utils.isEmpty( shortTerm ) && "true".equals( shortTerm ) ){
				//baseVO = TaskExecutor.executeTasks( "GET_ENDT_VO_SHORT_TERM", policyDataVO );
				com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorseList = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
				EndorsmentVO endorsmentVO = new EndorsmentVO();
				endorsmentVO.setShortTermCancellation(true);
				endorseList.add(endorsmentVO);
				policyDataVO.setEndorsmentVO(endorseList);	
			}
			
			baseVO = TaskExecutor.executeTasks( com.Constant.CONST_GET_ENDT_VO, policyDataVO );
			//Setting EndVO to policyDataVO
			endorsements.add( (EndorsmentVO) baseVO );
			policyDataVO.setEndorsmentVO( endorsements );
	
			BaseBeanToBeanMapper<HttpServletRequest, PolicyDataVO> requestBeanMapperEndt = BeanMapperFactory.getMapperInstance( RequestToPolicyDataVOEndtMapper.class );
			policyDataVO = requestBeanMapperEndt.mapBean( request, policyDataVO );
			responseObj.setData( policyDataVO );
			//response.setHeader( "endtText", policyDataVO.getEndorsmentVO().get( 0 ).getEndText() );
			request.getSession().setAttribute( "endtText", policyDataVO.getEndorsmentVO().get( 0 ).getEndText() );
		}
		else if( "COMMON_CAPTURE_AMEND_POLICY_ENDT_TEXT".equals( action ) ){
			//retrieve policyDataVO using baseLoadOperation
			BaseVO baseVO = null;
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			
			
			
			
			if( !Utils.isEmpty( request.getParameter( com.Constant.CONST_ENDEFFDATE ) ) || !Utils.isEmpty( commonVO.getEndtEffectiveDate() ) || commonVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ){
				baseVO = TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_DATAVO_FROM_COMMONVO ), commonVO );
				//setting commonVO to the retrieved PolicyVO
				PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
				if(Utils.isEmpty( commonVO.getEndtEffectiveDate() )){
					commonVO.setEndtEffectiveDate( policyDataVO.getEndEffectiveDate() );
				}
				policyDataVO.setCommonVO( commonVO );
				UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
				policyDataVO.setLoggedInUser( userProfile );
				com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
						com.Constant.CONST_FORMAT_YYYY_MM_DD );
				policyDataVO.setEndEffectiveDate( commonVO.getEndtEffectiveDate() );
				EndorsmentVO endorsementVo = ( policyDataVO.getEndorsmentVO() != null ) ? policyDataVO.getEndorsmentVO().get( 0 ) : null;
				if( endorsementVo != null ) endorsementVo.setPolicyToBeCancelled( true );
				
				

				if (commonVO.getLob().equals(LOB.WC)) {
					Integer brkCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
					if (!Utils.isEmpty(brkCode)) {

						java.util.List<Object> valueHolder = DAOUtils
								.getSqlResultSingleColumnPas(QueryConstants.GET_BROKER_ACC_STATUS, brkCode);
						BigDecimal bkrStatus = null;
						if (!Utils.isEmpty(valueHolder) && valueHolder.size() > 0 && !Utils.isEmpty(valueHolder.get(0))) {
							bkrStatus = ((BigDecimal) valueHolder.get(0));
						}
						if (!Utils.isEmpty(bkrStatus) && bkrStatus.compareTo(BigDecimal.ZERO) == 0) {
							response.setHeader("isBrokerBlocked", "true");
							throw new BusinessException("cmn.brkblocked.cl", null, "The Brk account is blocked");
						}
					}
				}

				//policyDataVO =  (PolicyDataVO) TaskExecutor.executeTasks(action, policyDataVO );
				policyDataVO.setEndtId( commonVO.getEndtId() );
				policyDataVO.setPolExpiryDate( policyDataVO.getScheme().getExpiryDate() );
				policyDataVO.setStartDate( policyDataVO.getScheme().getEffDate() );
				//Getting EndorsmentVO
				com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
				baseVO = TaskExecutor.executeTasks( com.Constant.CONST_GET_ENDT_VO, policyDataVO );
				endorsements.add( (EndorsmentVO) baseVO );
				//Setting EndVO to policyDataVO
				policyDataVO.setEndorsmentVO( endorsements );
				request.setAttribute( com.Constant.CONST_ENDORSMENTS, endorsements );
				request.setAttribute( com.Constant.CONST_AMENDFLOWTYPE, "COMMON_AMEND_POLICY" );
				BaseVO baseVO1 = TaskExecutor.executeTasks( com.Constant.CONST_CAPTURE_AMEND_POLICY_ENDT_TEXT, policyDataVO );
				policyDataVO = (PolicyDataVO) baseVO1;
				if( !Utils.isEmpty( policyDataVO.getEndorsmentVO() ) && policyDataVO.getEndorsmentVO().size() > 0 ){
					request.setAttribute( com.Constant.CONST_ENDORSMENTS, policyDataVO.getEndorsmentVO() );
				}
				responseObj.setData( policyDataVO );
				if(!Utils.isEmpty( request.getSession().getAttribute(  "PAYABLE_PREMIUM"  ) ) &&  Double.valueOf((String)request.getSession().getAttribute(  "PAYABLE_PREMIUM"  )) > 0.0 ){
					//Save payment Comments
					CommentsVO commentsVO = BeanMapper.map( request, CommentsVO.class );
					if( Utils.isEmpty( commonVO.getLob() ) ){
						throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
					}
					commentsVO.setLob( commonVO.getLob() );
					if( !Utils.isEmpty( policyDataVO.getPolicyId() ) && !Utils.isEmpty( policyDataVO.getEndtId() ) ){
						commentsVO.setPocPolicyId( policyDataVO.getPolicyId() );
						commentsVO.setPocEndtId( policyDataVO.getEndtId() );
					}

					if( !Utils.isEmpty( userProfile ) ){
						commentsVO.setLoggedInUser( userProfile );
					}
					commentsVO.setPolicyStatus( policyDataVO.getStatus().byteValue() );
					//Save comments
					PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
					polComHolder.setComments( commentsVO );
					polComHolder.setCommonDetails( commonVO );
					TaskExecutor.executeTasks( AppConstants.STORE_POL_COMMENTS, polComHolder );
				}
				Redirection redirection = null;
				redirection = new Redirection( com.Constant.CONST_JSP_CAPTUREENDORSEMENTTEXT_JSP, Type.TO_JSP );
				responseObj.setRedirection( redirection );
				response.setHeader( com.Constant.CONST_ENDORSEMENTTEXT, "true" );
			}
		}
		else if( "SAVE_ENDT_TEXT".equals( action ) ){
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
			BaseBeanToBeanMapper<HttpServletRequest, PolicyDataVO> requestBeanMapper = BeanMapperFactory.getMapperInstance( RequestToPolicyDataVOEndtMapper.class );
			PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_DATAVO_FROM_COMMONVO ), commonVO );
			policyDataVO.setCommonVO( commonVO );
			TaskExecutor.executeTasks( com.Constant.CONST_CAPTURE_AMEND_POLICY_ENDT_TEXT, policyDataVO );
			
			if( Utils.isEmpty( policyDataVO.getEndorsmentVO() ) ){
				policyDataVO.setEndorsmentVO( new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class ) );
			}
			
			if( policyDataVO.getEndorsmentVO().size() == 0 ){
				policyDataVO.setEndorsmentVO( AppUtils.createDefaultEndtVO( commonVO, policyDataVO.getEndorsmentVO() ) );
			}
			
			policyDataVO = requestBeanMapper.mapBean( request, policyDataVO );
			//mapEndtText( request, policyDataVO );
			TaskExecutor.executeTasks("SAVE_ENDORSEMENT_TEXT", policyDataVO );
			commonVO.setVsd( policyDataVO.getCommonVO().getVsd() );
			
			//redirect to coments load jsp
		}
		else if( "COMMON_SAVE_ENDT_TXT".equals( action ) ){
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
			BaseBeanToBeanMapper<HttpServletRequest, PolicyDataVO> requestBeanMapper = BeanMapperFactory.getMapperInstance( RequestToPolicyDataVOEndtMapper.class );
			PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_DATAVO_FROM_COMMONVO ), commonVO );
			policyDataVO.setCommonVO( commonVO );
			TaskExecutor.executeTasks( com.Constant.CONST_CAPTURE_AMEND_POLICY_ENDT_TEXT, policyDataVO );
			
			if( Utils.isEmpty( policyDataVO.getEndorsmentVO() ) ){
				policyDataVO.setEndorsmentVO( new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class ) );
			}
			
			if( policyDataVO.getEndorsmentVO().size() == 0 ){
				policyDataVO.setEndorsmentVO( AppUtils.createDefaultEndtVO( commonVO, policyDataVO.getEndorsmentVO() ) );
			}
			
			policyDataVO = requestBeanMapper.mapBean( request, policyDataVO );
			TaskExecutor.executeTasks("CAPTURE_ENDORSEMENT_TEXT_UPDATE", policyDataVO );
			commonVO.setVsd( policyDataVO.getCommonVO().getVsd() );
			Redirection redirection;
			if(loggenInLoc.equals("30"))
			redirection = new Redirection( "COMMON_AMEND_POL&action=COMMON_AMEND_POLICY", Type.TO_NEW_OPERATION );
			else
			redirection = new Redirection( "LOAD_COMMENTS", Type.TO_NEW_OPERATION );
			request.setAttribute( com.Constant.CONST_ACTION, "COMMON_AMEND_POLICY" );
			responseObj.setRedirection( redirection );
			//redirect to coments load jsp
		}/*else if( "COMMON_SAVE_ENDT_TXT_BOX".equals( action ) ){
			CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
			String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
			BaseBeanToBeanMapper<HttpServletRequest, PolicyDataVO> requestBeanMapper = BeanMapperFactory.getMapperInstance( RequestToPolicyDataVOEndtMapper.class );
			PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_DATAVO_FROM_COMMONVO ), commonVO );
			policyDataVO.setCommonVO( commonVO );
			TaskExecutor.executeTasks( com.Constant.CONST_CAPTURE_AMEND_POLICY_ENDT_TEXT, policyDataVO );
			
			if( Utils.isEmpty( policyDataVO.getEndorsmentVO() ) ){
				policyDataVO.setEndorsmentVO( new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class ) );
			}
			
			if( policyDataVO.getEndorsmentVO().size() == 0 ){
				policyDataVO.setEndorsmentVO( AppUtils.createDefaultEndtVO( commonVO, policyDataVO.getEndorsmentVO() ) );
			}
			
			policyDataVO = requestBeanMapper.mapBean( request, policyDataVO );
			TaskExecutor.executeTasks("CAPTURE_ENDORSEMENT_TEXT_UPDATE", policyDataVO );
			commonVO.setVsd( policyDataVO.getCommonVO().getVsd() );
			Redirection redirection;
			if(loggenInLoc.equals("30"))
			redirection = new Redirection( "COMMON_AMEND_POL&action=COMMON_AMEND_POLICY", Type.TO_NEW_OPERATION );
			else
			redirection = new Redirection( "LOAD_COMMENTS", Type.TO_NEW_OPERATION );
			request.setAttribute( com.Constant.CONST_ACTION, "COMMON_AMEND_POLICY" );
			responseObj.setRedirection( redirection );
			//redirect to coments load jsp
		}*/

		return responseObj;
	}

	private void mapEndtText(HttpServletRequest request, PolicyVO policyVO) {
		
		int cnt = Integer.parseInt(request.getParameter("cnt"));
		for(int i =0;i<cnt;i++)
		{
			long polId = Long.parseLong(request.getParameter("policyId["+i+"]"));
			policyVO.getEndorsements().get(i).setPolicyId(polId);
			long endId = Long.parseLong(request.getParameter("endtId["+i+"]"));
			policyVO.getEndorsements().get(i).setEndtId(endId);
			long endNo = Long.parseLong(request.getParameter("endtNo["+i+"]"));
			policyVO.getEndorsements().get(i).setEndNo(endNo);
			Integer slNo = Integer.parseInt(request.getParameter("slNo["+i+"]"));
			policyVO.getEndorsements().get(i).setSlNo(slNo);
			
			
		}
		
	}

	
	// the method is not used sonar fix 22-9-2017
	/*private void mapEndtText(HttpServletRequest request, PolicyDataVO policyVO) {
		
		if( !Utils.isEmpty( request.getParameter( "endText" ) ) ){
			policyVO.getEndorsements().get(0).setEndText( request.getParameter( "endText" ) );
		}
		else{
			policyVO.getEndorsements().get(0).setEndText( null);
		}
		int cnt = Integer.parseInt(request.getParameter("cnt"));
		for(int i =0;i<cnt;i++)
		{
			long polId = Long.parseLong(request.getParameter("policyId["+i+"]"));
			policyVO.getEndorsmentVO().get(i).setPolicyId(polId);
			long endId = Long.parseLong(request.getParameter("endtId["+i+"]"));
			policyVO.getEndorsmentVO().get(i).setEndtId(endId);
			long endNo = Long.parseLong(request.getParameter("endtNo["+i+"]"));
			policyVO.getEndorsmentVO().get(i).setEndNo(endNo);
			Integer slNo = Integer.parseInt(request.getParameter("slNo["+i+"]"));
			policyVO.getEndorsmentVO().get(i).setSlNo(slNo);
			
			
		}
		
	}*/

	/**
	 * @param request
	 * @param response
	 * @param responseObj
	 * @param action
	 */
	private void captureCancelPolicyText( HttpServletRequest request, HttpServletResponse response, Response responseObj, String action ){
		//retrieve policyDataVO using baseLoadOperation
		BaseVO baseVO = null;
		String shortTerm = request.getParameter( "shortTerm" );
		
		CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();

		baseVO = TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_DATAVO_FROM_COMMONVO ), commonVO );
		//setting commonVO to the retrieved PolicyVO
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		policyDataVO.setCommonVO( commonVO );
		com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
				com.Constant.CONST_FORMAT_YYYY_MM_DD );
		policyDataVO.setEndEffectiveDate( converter.getAFromB( request.getParameter( com.Constant.CONST_ENDEFFDATE ) ) );
		EndorsmentVO endorsementVo = ( policyDataVO.getEndorsmentVO() != null ) ? policyDataVO.getEndorsmentVO().get( 0 ) : null;
		if( endorsementVo != null ) endorsementVo.setPolicyToBeCancelled( true );

		//policyDataVO =  (PolicyDataVO) TaskExecutor.executeTasks(action, policyDataVO );
		policyDataVO.setEndtId( commonVO.getEndtId() );
		policyDataVO.setPolExpiryDate( policyDataVO.getScheme().getExpiryDate() );
		policyDataVO.setStartDate( policyDataVO.getScheme().getEffDate() );
		//Getting EndorsmentVO
		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
		/*Added for Short Term Cancellation - OMAN*/		
		if(!Utils.isEmpty( shortTerm ) && "true".equals( shortTerm ) ){
			//baseVO = TaskExecutor.executeTasks( "GET_ENDT_VO_SHORT_TERM", policyDataVO );
			com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorseList = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
			if(Utils.isEmpty(endorsementVo)){
				endorsementVo = new EndorsmentVO();
			}			
			endorsementVo.setShortTermCancellation(true);
			endorseList.add(endorsementVo);
			policyDataVO.setEndorsmentVO(endorseList);	
		}
		
		baseVO = TaskExecutor.executeTasks( com.Constant.CONST_GET_ENDT_VO, policyDataVO );
		EndorsmentVO endorsementVO = (EndorsmentVO)baseVO;
		
		/* Fix to exclude Policy Fees for VAsco during cancellation */
		/*if(policyDataVO.getPolicyType().toString().equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) && endorsementVO.getPremiumVO().getPremiumAmt() == 0.0){*/	/* commented empty if condition (Not content inside if) - sonar violation fix */
			//endorsementVO.getPremiumVO().setPremiumAmt( policyDataVO.getPremiumVO().getPolicyFees() );
		//}
		
		 if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_CANCELTOTALPREMIUM ) ) ){
			 endorsementVO.getPremiumVO().setPremiumAmt(  Double.valueOf( request.getParameter( com.Constant.CONST_CANCELTOTALPREMIUM ) )  );
		 }
		 else {
			 request.getSession().setAttribute( com.Constant.CONST_CANCELTOTALPREMIUM,null);
		 }
		 if(Utils.isEmpty( request.getParameter( com.Constant.CONST_DISCOUNTAMOUNT_CANCEL ) ) ){
			 request.getSession().setAttribute( com.Constant.CONST_DISCOUNTAMOUNT_CANCEL,null);
		 }
		endorsements.add( endorsementVO );
		//Setting EndVO to policyDataVO
		policyDataVO.setEndorsmentVO( endorsements );
		request.setAttribute( com.Constant.CONST_ENDORSMENTS, endorsements );
		request.setAttribute( com.Constant.CONST_AMENDFLOWTYPE, "COMMON_CANCEL_POLICY" );
		request.setAttribute( com.Constant.CONST_CANCELDISCOUNT, commonVO.getPremiumVO().getDiscOrLoadPerc());
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_CANCELTOTALPREMIUM ) ) ){
			request.getSession().setAttribute( com.Constant.CONST_CANCELTOTALPREMIUM,   Double.valueOf( request.getParameter( com.Constant.CONST_CANCELTOTALPREMIUM ) )  );
		}
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_DISCOUNTAMOUNT_CANCEL ) ) ){
			request.getSession().setAttribute( com.Constant.CONST_DISCOUNTAMOUNT_CANCEL,   Double.valueOf( request.getParameter( com.Constant.CONST_DISCOUNTAMOUNT_CANCEL ) )  );
		}
		//142244
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_VATAMOUNT_CANCEL ) ) ){
			request.getSession().setAttribute( com.Constant.CONST_VATAMOUNT_CANCEL,   Double.valueOf( request.getParameter( com.Constant.CONST_VATAMOUNT_CANCEL ) )  );
			 endorsementVO.getPremiumVO().setVatTax((  Double.valueOf( request.getParameter( com.Constant.CONST_VATAMOUNT_CANCEL ) )) );

		}
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_VATABLEPRM ) ) ){
			request.getSession().setAttribute( com.Constant.CONST_VATABLEPRM,   Double.valueOf( request.getParameter( com.Constant.CONST_VATABLEPRM ) )  );
			 endorsementVO.getPremiumVO().setVatablePrm((  Double.valueOf( request.getParameter( com.Constant.CONST_VATABLEPRM ) )) );

		}
		policyDataVO.setIsToBeDeleted( true );
		BaseVO baseVO1 = TaskExecutor.executeTasks( action, policyDataVO );
		policyDataVO = (PolicyDataVO) baseVO1;
		responseObj.setData( policyDataVO );
		Redirection redirection = null;
		redirection = new Redirection( com.Constant.CONST_JSP_CAPTUREENDORSEMENTTEXT_JSP, Type.TO_JSP );
		response.setHeader( com.Constant.CONST_ENDORSEMENTTEXT, "true" );
		responseObj.setRedirection( redirection );
	}

	private void mapTradeLicNo( HttpServletRequest request, PolicyVO policyVO ){

		/* Mapping: "quote_name_tradelicno" -> "generalInfo.insured.tradeLicenseNo" */
		if( !Utils.isEmpty( request.getParameter( "tradeLicNo" ) ) ){
			policyVO.getGeneralInfo().getInsured().setTradeLicenseNo( request.getParameter( "tradeLicNo" ) );
		}
		else{
			policyVO.getGeneralInfo().getInsured().setTradeLicenseNo( null );
		}

	}

	private boolean hasRefundEndorsementReferral( HttpServletRequest request, Response responseObj, PolicyVO policyVO ){
		List<EndorsmentVO> endorsmentVOs = policyVO.getEndorsements();
		EndorsmentVO endorsmentVO = null;
		if( !Utils.isEmpty( endorsmentVOs ) && endorsmentVOs.size() > 0 ){
			endorsmentVO = endorsmentVOs.get( 0 );
		}
		boolean result = false;
		String actionIdentifier = "CONFIRM_ENDORSEMENT";

		if( !Utils.isEmpty( endorsmentVO ) ){

			//double payablePrm = calculatePayablePremium( endorsmentVO );
			//payablePrm = Math.abs( payablePrm );

			if( !SectionRHUtils.executeReferralTask( responseObj, "CONFIRM_ENDORSEMENT", policyVO, actionIdentifier ) ){
				List<String> referralText = AppUtils.getReferralTextListForActionId( policyVO, actionIdentifier );
				AppUtils.createHardStopReferralResponse( request, responseObj, referralText );
				result = true;
			}
		}
		return result;
	}

	private void createRefundReferralResponse( HttpServletRequest request, Response responseObj ){
		List<String> hardStopTextList = new ArrayList<String>( 10 );
		String message = Utils.getSingleValueAppConfig( "refundEndorsement" );
		hardStopTextList.add( ( message != null ) ? message : "Your role does not allow to have refund endorsement." );
		request.setAttribute( "hardStopTextList", hardStopTextList );
		Redirection redirection = new Redirection( "jsp/hardStopPopup.jsp", Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

	private void createRefundReferralResponse( HttpServletRequest request, Response responseObj, List<String> hardStopTextList ){
		request.setAttribute( "hardStopTextList", hardStopTextList );
		Redirection redirection = new Redirection( "jsp/hardStopPopup.jsp", Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

	public static double calculatePayablePremium( EndorsmentVO endorsmentVO ){
		return endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt();
	}

	public static Constants.ENDORSEMENT_TYPE getEndorsementType( double payablePrm ){
		Constants.ENDORSEMENT_TYPE endType;
		if( payablePrm > 0 ){
			endType = Constants.ENDORSEMENT_TYPE.EXTRA;
		}
		else if( payablePrm < 0 ){
			endType = Constants.ENDORSEMENT_TYPE.REFUND;
		}
		else{
			endType = Constants.ENDORSEMENT_TYPE.NIL;
		}
		return endType;
	}

}  

