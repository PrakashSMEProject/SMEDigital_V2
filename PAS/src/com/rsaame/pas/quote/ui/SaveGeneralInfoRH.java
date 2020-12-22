package com.rsaame.pas.quote.ui;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.policy.svc.CaptureCommentsService;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyVOMapper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class SaveGeneralInfoRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( SaveGeneralInfoRH.class );
	private static String SAVE_GENERAL_INFO_RH = "SaveGeneralInfoRH";

	private static String SAVE_OPERATION_OP_TYPE = "QUOTE_SAVE_INVSVC";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		/* Response object which will be used by framework after request processing is completed */
		Response response = new Response();

		/* Identifier will be used further in TaskExecutor to identify request processing */
		String identifier = request.getParameter( "opType" );

		/* As part of processing convert the HTTP request object
		 * obtained to required VO by using request to bean mapper available as part of framework
		 */

		PolicyContext polContext = PolicyContextUtil.getPolicyContext( request );
		logger.debug( SAVE_GENERAL_INFO_RH, "polContext obtained" + polContext );
		
		String customerExists = request.getParameter("quote_name_hidden_new_cust");			

		PolicyVO policyVO = polContext.getPolicyDetails();
		
		String poBoxVal = null;
		
		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getInsured() ) && !Utils.isEmpty(  policyVO.getGeneralInfo().getInsured().getAddress() ) )
			poBoxVal = policyVO.getGeneralInfo().getInsured().getAddress().getPoBox();

		BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = (BaseBeanToBeanMapper) BeanMapperFactory.getMapperInstance( RequestToPolicyVOMapper.class );
		
		Date oldPolExpiryDate = policyVO.getPolExpiryDate();
		
		policyVO = (PolicyVO) requestBeanMapper.mapBean( request, policyVO );
		
		//Oman Changes : Setting policy level under writing questions
		UWQuestionsVO uwQuestions = BeanMapper.map( request, UWQuestionsVO.class );
		if( !Utils.isEmpty( uwQuestions ) && !Utils.isEmpty( policyVO.getGeneralInfo() ) ){
			policyVO.getGeneralInfo().setQuestionsVO( uwQuestions );
		}
		
		if( !Utils.isEmpty( policyVO.getPremiumVO() ) && oldPolExpiryDate.compareTo( policyVO.getPolExpiryDate() ) != 0 ){
			policyVO.getPremiumVO().setPolExtenUpdateRequired( Boolean.TRUE );
		}
				
		/*
		 * Request To VO mapper doesn't set null values. Hence the fields that are null has to set manually
		 * TODO: Need to chack all the fields that are nullable during edit and amend
		 */
		mapNullFields(request,policyVO);
		
		/*Below code added to set correct default class code. Default class code is identified as basic section's class code.*/
		setDefaultClassCode(PolicyContextUtil.getPolicyContext(request),policyVO);

		/** SK : Changes */
		/* Commented the below line and moved to PolicyVOToPolicyQuo mapper as this RH 
		 * is not invoked during referral flow */
		//ServiceContext.setgetSESSION_OMANBORDER_IND( new Integer( 2 ) );

		/* For all mandatory values not provided through user input, fill default values. */
		fillDefaultValues( request, polContext, policyVO );

		/* Once the VO is instantiated and populated with request values, call task executor to perform tasks to be performed */
		//	BaseVO baseVO = TaskExecutor.executeTasks(identifier, policyVO);
		//	System.out.println("baseVO -->"+baseVO);
		if(policyVO.getIsQuote()){
			String insuredId = request.getParameter( AppConstants.REQ_ATTR_INSURED_CODE );
			
			if(!Utils.isEmpty(insuredId)){
				ClaimsSvc claimsService = (ClaimsSvc) Utils.getBean( "claimsSvc" );
				if(claimsService.checkClaimsExistForInsured(insuredId)){
					//Call Rule				
					if(!AppUtils.allowQuoteCreation(request,response,policyVO)){
						return response;
					}				
				}		
			}
		}
		 
			validate(policyVO);
		
		
		String action = request.getParameter( "action" );

		/* To check if the request is directly coming in from UI if so use action from request as
		 * parameter else use it from request attribute
		 */
		if( Utils.isEmpty( action ) ){
			action = (String) request.getAttribute( "action" );
		}
		
		policyVO = (PolicyVO) TaskExecutor.executeTasks( AppConstants.SET_PRE_PACKAGE_FLAG, polContext.getPolicyDetails() );
		PolicyVO baseVO = null;
		
		
		// Ticket : 156700
		policyVO.setPolVatRate(DAOUtils.getVATRateSBS(policyVO.getPolVATCode(), null));
		/*
		 * To set POL_BUSINESS_TYPE to 0 if renewal quote and 1 if new quote and 2 if new existing quote
		 */
		if( Flow.RENEWAL.equals( policyVO.getAppFlow() ) ){
			policyVO.getGeneralInfo().getInsured().setPolBusType( AppConstants.BUS_TYPE_RENEWAL );
		}
		else if(Flow.CREATE_QUO.equals(  policyVO.getAppFlow()  ) && !Utils.isEmpty(customerExists) && customerExists.equalsIgnoreCase("N") ){
			policyVO.getGeneralInfo().getInsured().setPolBusType( AppConstants.BUS_TYPE_NEW_FOR_EXISTING );
		}		
		else{
			policyVO.getGeneralInfo().getInsured().setPolBusType( AppConstants.BUS_TYPE_NEW );
		}
		if(action.equalsIgnoreCase( "INSURECHK" ))
		{
			baseVO = (PolicyVO) TaskExecutor.executeTasks( identifier, policyVO );
		}
		else
		{
			/* If master table has to be updated for insured details then set thread level context object as Y else it will be
			 * set as N.
			 * */
			if(!Utils.isEmpty( policyVO.getGeneralInfo()) && !Utils.isEmpty( policyVO.getGeneralInfo().getInsured()) ){
				policyVO.getGeneralInfo().getInsured().setUpdateMaster( true );
			}
			
			if( !SectionRHUtils.isReadOnlyMode( polContext, request ) ){
				if( !SectionRHUtils.executeReferralTask( response, identifier, policyVO, action ) ){ //!!Please check. The action was hardcoded to "PAR_PAGE_SAVE" before merge.
					return response;
				}
				
				if(request.getParameter( com.Constant.CONST_MASTINSURE ) != null && request.getParameter( com.Constant.CONST_MASTINSURE ).equalsIgnoreCase( "y" )){
					
					baseVO = (PolicyVO) TaskExecutor.executeTasks( "SAVE_INSURED_DETAILS", policyVO );
					
				}
				if(request.getParameter( com.Constant.CONST_MASTINSURE ) != null && request.getParameter( com.Constant.CONST_MASTINSURE ).equalsIgnoreCase( "n" )){
					if(!Utils.isEmpty( policyVO.getGeneralInfo()) && !Utils.isEmpty( policyVO.getGeneralInfo().getInsured()) ){
						policyVO.getGeneralInfo().getInsured().setUpdateMaster( false );
					}
				}
				
				if( policyVO.getAppFlow().equals( Flow.AMEND_POL ) || policyVO.getAppFlow().equals( Flow.EDIT_QUO )  || ( policyVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ) ){
					// call to check If any changes from TMasInsured
					// 	baseVO = (PolicyVO) TaskExecutor.executeTasks( "AMEND_POLICY_STATUS_CHECK", policyVO );
					//	baseVO.setInsuredChanged(true);
					//	if( baseVO.isInsuredChanged() ){
						baseVO = (PolicyVO) TaskExecutor.executeTasks( "ENDORSE_GENINFO_SAVE_INVSVC", policyVO );
						//baseVO.setInsuredChanged(true);
					//}
				}
				else{
					
					baseVO = (PolicyVO) TaskExecutor.executeTasks( SAVE_OPERATION_OP_TYPE, policyVO );					
				}
				
				//Policy extension changes
				if( !Utils.isEmpty( policyVO.getPremiumVO() ) && policyVO.getPremiumVO().isPolExtenUpdateRequired() ){
					TaskExecutor.executeTasks( "EXTEND_POLICY_SVC", policyVO );
					policyVO.setPolicyExtended(true);
				}
				
			}
			else{
				baseVO = policyVO;
			}
		}
		
		
		//Added for JLT
		String comment=null;
		SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DD_MM_YYYY);
		String d2 = Utils.getSingleValueAppConfig("JLT_LiveDate");
		Date JLTLiveDate = null;
		try {
			JLTLiveDate = s2.parse(d2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date preparedDate = new Date();
		if (!Utils.isEmpty(policyVO.getCreated())) {
			preparedDate = policyVO.getCreated();
		}
		
		Date date = new Date();
		SimpleDateFormat s3 = new SimpleDateFormat(com.Constant.CONST_DD_MM_YYYY);
		Date modifiedDate = new Date();

	    String strDateFormat = com.Constant.CONST_DD_MM_YYYY;
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	     try {
			modifiedDate= s3.parse(dateFormat.format(date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		if (Integer.valueOf(Utils.getSingleValueAppConfig("JLT_SchemeCode")).equals(policyVO.getScheme().getSchemeCode())
				 && SvcConstants.DUBAI == Integer
						.parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) &&
						(JLTLiveDate.compareTo(preparedDate) <= 0 ||  JLTLiveDate.compareTo(modifiedDate) <= 0)) {
			logger.info("Enter Saving remarks for JLT");
			if(!Utils.isEmpty( policyVO) && !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo().getRemarks()) ) {
				 comment = policyVO.getGeneralInfo().getAdditionalInfo().getRemarks();
				 DAOUtils.storeComments(policyVO,comment);
			}
			
			logger.info("Exit Saving remarks for JLT");
			
		}
		
		

		/* Added to check if the FGBPM is returning exception or error as the response as part of process call */
		String ctxMessage = ServiceContext.getMessage();
		logger.debug( SAVE_GENERAL_INFO_RH, "ctxMessage ::" + ctxMessage );
		System.out.println( "SAVE_GENERAL_INFO_RH context message -->" + ctxMessage );

		if( ctxMessage != null && ServiceContext.MSG_EXCEPTION.equalsIgnoreCase( ctxMessage ) ){
			responseObj.setHeader( "SaveGeneralInfoResponse", "Exception" );
		}
		else if( ctxMessage != null && ServiceContext.MSG_ERROR.equalsIgnoreCase( ctxMessage ) ){
			responseObj.setHeader( "SaveGeneralInfoResponse", "Error" );
		}

		if( !Utils.isEmpty( baseVO ) ){

			AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
			response.setSuccess( true );
			response.setData( baseVO );

			/* Set the response obtained to Policy Context so that next sections can obtain the value using policy context */

			polContext.setPolicyDetails( (PolicyVO) baseVO );
			//polContext.setCurrentSection(AppConstants.GENERAL_INFO_SECTION);

		}
		policyVO.getGeneralInfo().getInsured().getAddress().setGIPoBoxChanged(false);
		//if(!policyVO.getGeneralInfo().getInsured().getAddress().isGIPoBoxChanged()){
		//	policyVO.getGeneralInfo().getInsured().getAddress().setGIPoBoxChanged(hasPOBoxChanged(policyVO , poBoxVal));
		//}
		//Updated for AdventId:65098
		if( Utils.isEmpty( request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ))){
			request.getSession(false).setAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED, hasPOBoxChanged(policyVO , poBoxVal) );
		}
		if( !Utils.isEmpty(policyVO.getNewEndtId()) && request.getSession( false ).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ).toString().equals( "true" )){
				TaskExecutor.executeTasks( "POBOX_AMEND_SVC", policyVO );
				CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
				DataHolderVO<String> dataHolderVO = (DataHolderVO<String>) commonOpSvc.invokeMethod("getUpdatedPoBox", policyVO);
				if(!Utils.isEmpty(dataHolderVO) && !Utils.isEmpty(dataHolderVO.getData())){
					String poBox = dataHolderVO.getData().toString();
				String riskGroupId = AppUtils.getCurrentRiskGroupId( request, polContext );
				LocationVO locationVO = (LocationVO) polContext.getRiskGroup( polContext.getCurrentSectionId(), riskGroupId );
				//if( !Utils.isEmpty( request.getSession( false ).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ) )
						//&& request.getSession( false ).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ).toString().equals( "true" ) ){
				if( Utils.isEmpty( locationVO ) || Utils.isEmpty( locationVO.getAddress()) && Utils.isEmpty(locationVO.getAddress().getPoBox()) 
						|| ( !Utils.isEmpty( locationVO.getAddress()) && !Utils.isEmpty(locationVO.getAddress().getPoBox()) 
						&&  !poBox.equals(locationVO.getAddress().getPoBox()))){
						AppUtils.setPoxBoxDetailsFromGI( policyVO, locationVO );
				}	
				}
				//AppUtils.updateEndtPOBox(request);
			
		}
		/* To pass back the action identifier as part of response header */ 
		responseObj.setHeader( "actionIdentifier", action );

		return response;
	}



	private void validate(PolicyVO policyVO) {
		
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
			TaskExecutor.executeTasks("GI_PAGE_CITY_VAL", policyVO);
		}
		/*
		 * check for renewals
		 */
		if(!Utils.isEmpty(policyVO)&&!Utils.isEmpty(policyVO.getAuthInfoVO()) && !Utils.isEmpty(policyVO.getAuthInfoVO().getTxnType())){
			
			if(policyVO.getAuthInfoVO().getTxnType().equals(AppConstants.RENEWEL_QUOTATION)){
				TaskExecutor.executeTasks("RENEWAL_EFF_DATE_VAL", policyVO);
			}
			
		}
		
		TaskExecutor.executeTasks("BROKER_ACC_STATUS_VAL", policyVO);
		
	}
	/**
	 * 
	 * @param policyVO
	 * @param poBoxVal
	 * @return
	 */
	private boolean hasPOBoxChanged( PolicyVO policyVO, String poBoxVal ){
		
		if( !policyVO.getGeneralInfo().getInsured().getAddress().getPoBox().equals( poBoxVal )){
			return true;
		}else{
			return false;
		}
		
	}

	private void mapNullFields( HttpServletRequest request, PolicyVO policyVO ){
		
		if( Utils.isEmpty( request.getParameter( "quote_name_territory" ) ) ){
			 policyVO.getGeneralInfo().getInsured().getAddress().setTerritory( null );
		}
	}

	/**
	 * Fills in default values for all mandatory fields not supplied by the user.
	 * 
	 * @param request
	 * @param polContext
	 * @param policyVO
	 */
	private void fillDefaultValues( HttpServletRequest request, PolicyContext polContext, PolicyVO policyVO ){
		Date date = new Date();
		Timestamp creationDate = new Timestamp( date.getTime() );

		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );

		if( !Utils.isEmpty( policyVO.getAuthInfoVO() ) && Utils.isEmpty( policyVO.getAuthInfoVO().getCreatedBy() ) && !Utils.isEmpty( userProfile )
				&& !Utils.isEmpty( userProfile.getRsaUser() ) && !Utils.isEmpty( userProfile.getRsaUser().getUserId() ) ){
			policyVO.getAuthInfoVO().setCreatedBy( userProfile.getRsaUser().getUserId().toString() );
		}

		if( !Utils.isEmpty( policyVO.getAuthInfoVO() ) && Utils.isEmpty( policyVO.getAuthInfoVO().getCreatedOn() ) ){
			policyVO.getAuthInfoVO().setCreatedOn( creationDate );
		}

		if( !Utils.isEmpty( policyVO.getAuthInfoVO() ) && Utils.isEmpty( policyVO.getAuthInfoVO().getLicensedBy() ) && !Utils.isEmpty( userProfile )
				&& !Utils.isEmpty( userProfile.getRsaUser() ) && !Utils.isEmpty( userProfile.getRsaUser().getUserId() ) ){
			policyVO.getAuthInfoVO().setLicensedBy( Integer.valueOf( userProfile.getRsaUser().getUserId() ) );
		}

		if( !Utils.isEmpty( policyVO.getAuthInfoVO() ) && Utils.isEmpty( policyVO.getAuthInfoVO().getTxnType() ) ){
			policyVO.getAuthInfoVO().setTxnType( 5 );
		}

		if( !Utils.isEmpty( policyVO.getAuthInfoVO() ) && Utils.isEmpty( policyVO.getAuthInfoVO().getApprovedBy() ) && !Utils.isEmpty( userProfile )
				&& !Utils.isEmpty( userProfile.getRsaUser() ) && !Utils.isEmpty( userProfile.getRsaUser().getUserId() ) ){
		//	policyVO.getAuthInfoVO().setApprovedBy( Integer.valueOf( userProfile.getRsaUser().getUserId() ) ); //commented for bugzilla bug 489
			policyVO.getAuthInfoVO().setApprovedBy( null);
		}

		if( !Utils.isEmpty( policyVO.getAuthInfoVO() ) && Utils.isEmpty( policyVO.getAuthInfoVO().getApprovedDt() ) ){
			policyVO.getAuthInfoVO().setApprovedDt( null);
		}

		if( Utils.isEmpty( policyVO.getStatus() ) ){
			policyVO.setStatus( 6 );
		}

		/* 
		 * TODO : Populate PolicyId which is currently set to policyNo field within PolicyVO.
		 * For New Quote Creation process policyId should be 0 before hitting 
		 * FGBPM service
		 */
		if( !Utils.isEmpty( policyVO ) && Utils.isEmpty( policyVO.getPolicyNo() ) ){
			policyVO.setPolicyNo( Long.valueOf( 0 ) );
		}

		/* To check if EndtId is populated from screen if not then default populate it to 0
		 * for quote creation process
		 */
		if( Utils.isEmpty( policyVO.getEndtId() ) ){
			String defaultEndtId = (String) Utils.getSingleValueAppConfig( AppConstants.DEFAULT_ENDT_ID );
			if( null != defaultEndtId ){
				policyVO.setEndtId( Long.valueOf( defaultEndtId ) );
			}

		}

		/* Get default values from LookUpSvc and fill in for all mandatory fields not supplied. */
		/* TODO Implementation pending */

		/** Changes for Create Quote - Existing Customer Flow
		 * Generating the Quotation number and Insured Id.
		 * For New Customer Flow - both the Ids are being set in CheckIfInsuredExistsDAO 
		 */
		if( Utils.isEmpty( policyVO.getQuoteNo() ) ){
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
			DataHolderVO<Long> dataHolderVO = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "generateQuotationNo", policyVO );
			if( !Utils.isEmpty( dataHolderVO ) ){
				policyVO.setQuoteNo( dataHolderVO.getData() );
			}
		}

		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getInsured() )
				&& Utils.isEmpty( policyVO.getGeneralInfo().getInsured().getInsuredCode() ) ){
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
			DataHolderVO<Long> dataHolderVO = (DataHolderVO<Long>) commonOpSvc.invokeMethod( "generateInsuredId", policyVO );
			if( !Utils.isEmpty( dataHolderVO ) ){
				policyVO.getGeneralInfo().getInsured().setInsuredCode( dataHolderVO.getData() );
			}
		}

	}
	
	/*
	 * */
	private void setDefaultClassCode(PolicyContext policyContext, PolicyVO policyVO) {
		
		if(!Utils.isEmpty(policyContext.getAllSelectedSections()))
			if(!Utils.isEmpty(policyContext.getBasicSection()) && !Utils.isEmpty(policyVO))
				policyVO.setDefaultClassCode(policyContext.getBasicSection().getClassCode());
			
		
	}

}
