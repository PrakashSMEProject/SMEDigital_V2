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
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyDataVOMapper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1020204
 * This Request Handler is invoked when endorsing home/travel policy
 */
public class CommonAmendPolicyRH implements IRequestHandler{

	private static final Logger LOGGER = Logger.getLogger( CommonAmendPolicyRH.class );

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		LOGGER.info( "CommonAmendPolicyRH execution started" );
		
		//Initializing response
		Response response = new Response();
		Redirection redirection = null;
		//Getting action from javascript function
		String action = request.getParameter( com.Constant.CONST_ACTION );		
		
		String shortTerm = request.getParameter( "shortTerm" );
		
		//retrieve policyDataVO using baseLoadOperation
		BaseVO baseVO = null;
		CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
		baseVO = TaskExecutor.executeTasks("POLICY_DATAVO_FROM_COMMONVO", commonVO );
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		//setting commonVO to the retrieved PolicyDataVO
		policyDataVO.setCommonVO( commonVO );
		
		//Set user profile
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if(!Utils.isEmpty(userProfile)){
			policyDataVO.setLoggedInUser(userProfile);	
		}
		
		
		if( "POLICY_AMEND_STATUS_CHECK".equals( action ) ){
			Integer[] amendPolicyValidation = { Integer.valueOf( Utils.getSingleValueAppConfig( "AMEND_POLICY_VALIDATION" ) ) };
			/*Check for if the policy is expired if so HardStop referral */
			if( !SectionRHUtils.executeReferralTaskValidation( policyDataVO, "", " AMEND POLICY", amendPolicyValidation, request ) ){
				redirection = AppUtils.prepareRedirection( request, responseObj, response, policyDataVO );
				response.setRedirection( redirection );
			}
			else{
				// Get the latest endorsed endtId and endtNo.If they are not equal, endorsement cannot be done
				DataHolderVO<Long[]> dataHolder = (DataHolderVO<Long[]>) TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( "GET_LATEST_ENDORSED_RECORD" ), policyDataVO );
				Long[] endtInfo = dataHolder.getData();
				if( !Utils.isEmpty( endtInfo ) ){
					// endtInfo[0] has endtNo and it is not used here as commonVO is not populated with endtNo
					if( endtInfo[ 1 ].equals( commonVO.getEndtId() ) ){
						response.setData( policyDataVO );
					}
					else{
						response.setData( false );
					}
				}
				else{
					response.setData( false );
				}
				response.setResponseType( Response.Type.JSON );
				responseObj.setHeader( "isJSON", "true" );
			}
			return response;

		}
		else if( "COMMON_AMEND_EFFECTIVE_DATE_CHECK".equals( action ) ){
			LOGGER.info( "inside COMMON_AMEND_EFFECTIVE_DATE_CHECK" );
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
					com.Constant.CONST_FORMAT_YYYY_MM_DD );
			policyDataVO.setEndEffectiveDate( converter.getTypeOfA().cast( converter.getAFromB( request.getParameter( "effDate" ) ) ) );
			//Adding the below code to check the flow is when cancel button is clicked
			if( !Utils.isEmpty(request.getParameter( "cancelPol" ))){
				policyDataVO.setPolicyCancel(request.getParameter( "cancelPol" ).equalsIgnoreCase("true")?true:false);
			}
			DataHolderVO dataHolderVO = (DataHolderVO) TaskExecutor.executeTasks( action, policyDataVO );
			Object[] data = (Object[]) dataHolderVO.getData();
			if(data[1].equals( "" ) && !Utils.isEmpty( shortTerm ) && "true".equals( shortTerm ) ){
				dataHolderVO = (DataHolderVO) TaskExecutor.executeTasks( "SHORT_TERM_CANCEL_EFFECTIVE_DATE_CHECK", policyDataVO );
				data = (Object[]) dataHolderVO.getData();
			}

			//if there is any error, it means data[1]!=""
			if( data[ 1 ].equals( "" ) ){
				response.setData( baseVO );
			}
			else{
				String reason = data[ 1 ].toString();
				response.setData( reason );
			}
		}else if("AMEND_POLICY_DELETE".equals( action )){
			baseVO = TaskExecutor.executeTasks( action, policyDataVO );
			response.setData( baseVO );
			policyDataVO.getCommonVO().setStatus( AppConstants.QUOTE_ACTIVE );
			AppUtils.addErrorMessage( request, "pas.pending.endorsement.deleted" );
		}
		else if( "POLICY_CANCEL_CHECK".equals( action ) ){
			
			Integer[] cancelPolicyValidation = { Integer.valueOf( Utils.getSingleValueAppConfig( "CANCEL_POLICY_VALIDATION" ) ) };
			/*Check for if the policy is expired if so HardStop referral */
			if( !SectionRHUtils.executeReferralTaskValidation( policyDataVO, "", " AMEND POLICY", cancelPolicyValidation, request ) ){
				redirection = AppUtils.prepareRedirection( request, responseObj, response, policyDataVO );
				response.setRedirection( redirection );
			}else{
			
				// Get the latest endorsed endtId and endtNo.If they are not equal, endorsement cannot be done
				DataHolderVO<Long[]> dataHolder = (DataHolderVO<Long[]>) TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( "GET_LATEST_ENDORSED_RECORD" ), policyDataVO );
				Long[] endtInfo = dataHolder.getData();
				policyDataVO.setStatus( commonVO.getStatus() );
				if( !Utils.isEmpty( endtInfo ) ){
					// endtInfo[0] has endtNo and endtInfo[1] has endtId
					if( endtInfo[ 0 ].equals( commonVO.getEndtNo() ) &&  endtInfo[ 1 ].equals( commonVO.getEndtId() ) ){
						response.setData( policyDataVO );
					}
					else{
						response.setData( false );
					}
				}
				else{
					response.setData( false );
				}
				response.setResponseType( Response.Type.JSON );
				responseObj.setHeader( "isJSON", "true" );
			}

			return response;
		}
		
		else if(action.equals("GET_COMMOM_REFUND_PREMIUM")) {
			LOGGER.info( "inside GET_REFUND_PREMIUM" );
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
			com.Constant.CONST_FORMAT_YYYY_MM_DD );
			Integer[] cancelPostDateValidation = { Integer.valueOf( Utils.getSingleValueAppConfig( "CANCEL_POST_DATE_VALIDATION" ) ) };
			policyDataVO.setEndEffectiveDate( converter.getAFromB( request.getParameter( com.Constant.CONST_ENDEFFDATE ) ) );
			if( !SectionRHUtils.executeReferralTaskValidation( policyDataVO, "", " CANCEL POLICY POST DATE", cancelPostDateValidation, request ) ){
				redirection = AppUtils.prepareRedirection( request, responseObj, response, policyDataVO );
				response.setRedirection( redirection );
			}else{
			
				
				//Defect fix : During Cancel Policy flow, keeping the flow as VIEW_POL and not as AMEND_POL as risk page has to be rendered in read-only mode
				//policyDataVO.setAppFlow( Flow.AMEND_POL );
				policyDataVO.setPolExpiryDate(policyDataVO.getScheme().getExpiryDate());
				policyDataVO.setStartDate( policyDataVO.getScheme().getEffDate() );
				request.setAttribute( "amendAction", "GET_REFUND_PREMIUM" );
				
				//call service				
				/*Added for Short term Cancellation - OMAN*/
				if(!Utils.isEmpty( shortTerm ) && "true".equals( shortTerm ) ){
					//baseVO = TaskExecutor.executeTasks("SHORT_TERM_CANCELLATION_REFUND", policyDataVO );					
					com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = policyDataVO.getEndorsmentVO();
					if(!Utils.isEmpty( endorsements)){
						policyDataVO.getEndorsmentVO().get( 0 ).setShortTermCancellation(true);					
					}
					else{
						endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
						EndorsmentVO endorsmentVO = new EndorsmentVO();
						endorsmentVO.setShortTermCancellation( true );
						endorsements.add( endorsmentVO );
						policyDataVO.setEndorsmentVO( endorsements );
					}
				}
				baseVO =  TaskExecutor.executeTasks(action, policyDataVO );				
				
				policyDataVO = (PolicyDataVO)baseVO;
				policyDataVO.getEndorsmentVO().get( 0 ).setEndEffDate( policyDataVO.getEndEffectiveDate() );
				policyDataVO.getEndorsmentVO().get( 0 ).setEndDate( policyDataVO.getPolExpiryDate() );
				request.setAttribute( "cancelDetails", policyDataVO );
				
				
				if(policyDataVO.getCommonVO().getLob().equals( LOB.TRAVEL )){
					if (!policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE))) {
						request.setAttribute( "policyType", "SHORT_TERM" );
					}
					redirection = new Redirection( "LOAD_RISK_PAGE", Redirection.Type.TO_NEW_OPERATION );
				}else if( LOB.HOME.equals( policyDataVO.getCommonVO().getLob() ) ){
					redirection = new Redirection( "HOME_INSURANCE_PAGE&action=LOAD_DATA", Redirection.Type.TO_NEW_OPERATION );
				}
				else
				{
					redirection = new Redirection( "COMMON_FUNCTIONALITY&appFlow=VIEW_QUO&navigation=LOAD&lob="+commonVO.getLob(), Redirection.Type.TO_NEW_OPERATION );
				}
			}
			response.setRedirection( redirection );
			
		}else if(action.equals("PROCESS_CANCEL_POLICY")) {
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
					com.Constant.CONST_FORMAT_YYYY_MM_DD );
			String endtText = null;
			/*if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_ENDTTEXT ) ) ){
				endtText =  request.getParameter( com.Constant.CONST_ENDTTEXT ) ;
			}*/
			
			if(!Utils.isEmpty( request.getSession().getAttribute( com.Constant.CONST_ENDTTEXT ) ) ){
				endtText =   (String)request.getSession().getAttribute( com.Constant.CONST_ENDTTEXT )  ;
				request.getSession().removeAttribute( com.Constant.CONST_ENDTTEXT );
			}
			
			policyDataVO.setEndEffectiveDate( converter.getAFromB( request.getParameter( com.Constant.CONST_ENDEFFDATE ) ) );
			policyDataVO.getCommonVO().setEndtEffectiveDate( converter.getAFromB( request.getParameter( com.Constant.CONST_ENDEFFDATE ) ) );
			policyDataVO.setPolExpiryDate(policyDataVO.getScheme().getExpiryDate());
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
			
			baseVO = TaskExecutor.executeTasks( "GET_ENDT_VO", policyDataVO );
			//Setting EndVO to policyDataVO
			EndorsmentVO endorsementVO =  (EndorsmentVO) baseVO ;
			PremiumVO canPremiumVo = null;
			endorsements.add(endorsementVO);
			if(!Utils.isEmpty(request.getSession().getAttribute( "cancelTotalPremium" )) ) {
				canPremiumVo = new PremiumVO();
				Double canPremium = (Double) request.getSession().getAttribute( "cancelTotalPremium" ) ;
				canPremiumVo.setPremiumAmt( canPremium );
				endorsementVO.getPremiumVO().setPremiumAmt(canPremium);
				endorsementVO.setCanPremiumVO( canPremiumVo );
			}
			//142244 Vat Amount POL VAT Amount
			if(!Utils.isEmpty(request.getSession().getAttribute( "vatAmount_cancel" )) ) {
				canPremiumVo = endorsementVO.getCanPremiumVO();
				if(Utils.isEmpty( canPremiumVo )) canPremiumVo = new PremiumVO();
				Double vatAmount = (Double) request.getSession().getAttribute( "vatAmount_cancel" ) ;
				canPremiumVo.setVatTax( vatAmount );
				endorsementVO.setCanPremiumVO( canPremiumVo );
				if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
						policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE))
						|| policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.WC_POLICY_TYPE)))
				{
				policyDataVO.getPremiumVO().setVatTax( vatAmount );
				}
				if(!Utils.isEmpty(request.getSession().getAttribute( com.Constant.CONST_VATABLEPRM )) && 
						policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.WC_POLICY_TYPE))) {
					canPremiumVo.setVatablePrm((Double) request.getSession().getAttribute( com.Constant.CONST_VATABLEPRM ) );
					policyDataVO.getPremiumVO().setVatablePrm( (Double) request.getSession().getAttribute( com.Constant.CONST_VATABLEPRM ) );
				}
				
			}
			if(!Utils.isEmpty(request.getSession().getAttribute( "discountAmount_cancel" )) ) {
				canPremiumVo = endorsementVO.getCanPremiumVO();
				if(Utils.isEmpty( canPremiumVo )) canPremiumVo = new PremiumVO();
				Double discAmount = (Double) request.getSession().getAttribute( "discountAmount_cancel" ) ;
				canPremiumVo.setDiscOrLoadAmt(BigDecimal.valueOf( discAmount ));
				endorsementVO.setCanPremiumVO( canPremiumVo );
				
			}
			
			policyDataVO.setEndorsmentVO( endorsements );
			policyDataVO.getEndorsmentVO().get( 0 ).setEndText( endtText );
			policyDataVO.getPremiumVO().setDiscOrLoadPerc( commonVO.getPremiumVO().getDiscOrLoadPerc() );
			
			baseVO =  TaskExecutor.executeTasks("PROCESS_COMMON_CANCEL_POLICY", policyDataVO );

			if( !Utils.isEmpty( policyDataVO.getEndtId() ) ){
				commonVO.setEndtId( policyDataVO.getEndtId() );
			}
			commonVO.setStatus( SvcConstants.POL_STATUS_DELETED );
			commonVO.setDocCode( AppConstants.ENDORSEMENT_DOC_CODE );
			commonVO.setVsd( policyDataVO.getCommonVO().getVsd() );
			//commonVO.getPremiumVO().setDiscOrLoadPerc( 0.0 );
			response.setData( baseVO );
		}
		else if(action.equals( "COMMON_CANCEL_POLICY" )){
			String act = request.getParameter( com.Constant.CONST_ACTION );
			if( !Utils.isEmpty( act ) ){
				request.setAttribute( com.Constant.CONST_ACTION, act );
			}

			CommentsVO commentsVO = BeanMapper.map( request, CommentsVO.class );
			if( !Utils.isEmpty( policyDataVO.getPolicyId() ) && !Utils.isEmpty( policyDataVO.getEndtId() ) ){
				commentsVO.setPocPolicyId( policyDataVO.getPolicyId() );
				commentsVO.setPocEndtId( policyDataVO.getEndtId() );
			}

			if( !Utils.isEmpty( userProfile ) ){
				commentsVO.setLoggedInUser( userProfile );
			}
			if(Utils.isEmpty( commonVO.getLob())){
				throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
			}
			commentsVO.setLob( commonVO.getLob());
			//Save comments
			PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
			polComHolder.setComments( commentsVO );
			polComHolder.setCommonDetails( commonVO );
			TaskExecutor.executeTasks( AppConstants.STORE_POL_COMMENTS, polComHolder );
		}
		else if(action.equals( "CONFIRM_ENDT_HOME_TRAVEL" )){
			policyDataVO.setCommonVO( commonVO );
			BaseBeanToBeanMapper<HttpServletRequest, PolicyDataVO> requestBeanMapper = BeanMapperFactory
					.getMapperInstance( RequestToPolicyDataVOMapper.class );
			policyDataVO = requestBeanMapper.mapBean( request, policyDataVO );
			policyDataVO.setEndtId( commonVO.getEndtId() );
			policyDataVO.setPolExpiryDate( policyDataVO.getScheme().getExpiryDate() );
			policyDataVO.setStartDate( policyDataVO.getScheme().getEffDate() );
			
			LOGGER.info( "Endorsement Update Status Procedure called" );
			DAOUtils.callUpdateStatusProcedureForHomeTravel( policyDataVO );
			LOGGER.info( "Endorsement Update Status Procedure executed successfully" );
			commonVO.setStatus( SvcConstants.POL_STATUS_ACTIVE );
			
			response.setData( policyDataVO );
		}
		else if(action.equals( "COMMON_AMEND_POLICY" )){
			String act = request.getParameter( com.Constant.CONST_ACTION );
			String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
			if( !Utils.isEmpty( act ) ){
				request.setAttribute( com.Constant.CONST_ACTION, act );
			}

			CommentsVO commentsVO = BeanMapper.map( request, CommentsVO.class );
			if(Utils.isEmpty( commonVO.getLob())){
				throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
			}
			commentsVO.setLob( commonVO.getLob());
			if( !Utils.isEmpty( policyDataVO.getPolicyId() ) && !Utils.isEmpty( policyDataVO.getEndtId() ) ){
				commentsVO.setPocPolicyId( policyDataVO.getPolicyId() );
				commentsVO.setPocEndtId( policyDataVO.getEndtId() );
			}

			if( !Utils.isEmpty( userProfile ) ){
				commentsVO.setLoggedInUser( userProfile );
			}
			//commentsVO.setPolicyStatus( policyDataVO.getStatus().byteValue() );
			request.getSession( false ).setAttribute( AppConstants.GET_COMMENTS, commentsVO );
			policyDataVO.setCommonVO( commonVO );
			BaseBeanToBeanMapper<HttpServletRequest, PolicyDataVO> requestBeanMapper = BeanMapperFactory
					.getMapperInstance( RequestToPolicyDataVOMapper.class );
			policyDataVO = requestBeanMapper.mapBean( request, policyDataVO );
			policyDataVO.setEndtId( commonVO.getEndtId() );
			policyDataVO.setPolExpiryDate( policyDataVO.getScheme().getExpiryDate() );
			policyDataVO.setStartDate( policyDataVO.getScheme().getEffDate() );
			//Save comments
			PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
			polComHolder.setComments( commentsVO );
			polComHolder.setCommonDetails( commonVO );
			if(!loggenInLoc.equals("30"))
			TaskExecutor.executeTasks( AppConstants.STORE_POL_COMMENTS, polComHolder );
			
			//Saving payment details
			PaymentVO paymentvo = null;
			if( !Utils.isEmpty( request.getSession( false ).getAttribute( AppConstants.GET_PAYMENT_DETS ) ) ){
				paymentvo = (PaymentVO) request.getSession( false ).getAttribute( AppConstants.GET_PAYMENT_DETS );
			}
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
			 
				TaskExecutor.executeTasks( AppConstants.SAVE_PAYMENT_DETAILS, dataHolderVO );
			
			
			LOGGER.info( "Endorsement Update Status Procedure called" );
			
			if( !Utils.isEmpty( policyDataVO.getCommonVO() ) && !Utils.isEmpty( policyDataVO.getCommonVO().getLob() ) && ( policyDataVO.getCommonVO().getLob().equals( LOB.HOME ) ||  policyDataVO.getCommonVO().getLob().equals( LOB.TRAVEL ))){
				DAOUtils.callUpdateStatusProcedureForHomeTravel( policyDataVO );
			}else{
				DAOUtils.callUpdateStatusProcedureForIssueQuote(policyDataVO);
			}
			
			LOGGER.info( "Endorsement Update Status Procedure executed successfully" );
			commonVO.setStatus( SvcConstants.POL_STATUS_ACTIVE );
			PolicyContextUtil.getPolicyContext( request ).getCommonDetails().setStatus( AppConstants.QUOTE_ACTIVE );
			PolicyContextUtil.getPolicyContext( request ).getCommonDetails().setAppFlow( Flow.VIEW_POL );
			request.getSession().removeAttribute( AppConstants.GET_COMMENTS );
			request.getSession().removeAttribute( AppConstants.GET_PAYMENT_DETS );
		}
		return response;
		
	}
}
