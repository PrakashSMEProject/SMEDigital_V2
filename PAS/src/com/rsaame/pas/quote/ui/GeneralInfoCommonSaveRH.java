package com.rsaame.pas.quote.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.request.vo.mapper.RequestToTravelInsuranceVOMapper;
import com.rsaame.pas.request.vo.mapper.RequestToUWQVOMapper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1020204
 * Request Handler - It is executed when saving the Travel or Home General Info page
 */
public class GeneralInfoCommonSaveRH implements IRequestHandler{

	
	private static final Logger LOGGER = Logger.getLogger( GeneralInfoCommonSaveRH.class );
	private static final String ZERO = "0";
	public static final String COPY_QUOTE = "COPY_QUOTE";
	private static final String REFERAL_COMMON_POPUP_JSP = "/jsp/quote/referralCommon.jsp";
	private static final String CONSOLIDATED_REFERAL_POPUP_JSP = "/jsp/quote/consolidatedReferralCommon.jsp";
	private static final String NAVIGATE_TO_HOME_PAGE = "/jsp/homePage_content.jsp";

	
	@SuppressWarnings("unchecked")
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		
		LOGGER.info( "Entering GeneralInfoCommonSaveRH" );

		/* Fetch commonVO from PolicyContext.*/
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		String lob = commonVO.getLob().toString();
		PolicyDataVO policyData = null;
		//UserProfile userProfile = null;
		Response responseObj = new Response();
		/* Identifier will be used further in TaskExecutor to identify request processing */
		String identifier = request.getParameter( "opType" );
		String custExists = request.getParameter( "quote_cust_exists" );
		/* Referral related parameters start for TRAVEL/HOME*/
		//Radar fix
		//String referalTxt = null;
		String assignTO = null;
		String referalLoc = null;
		String referalComments = null;
		/* Referral related parameters data setting start for TRAVEL */
		//Radar fix
		/*if (!Utils.isEmpty( request.getParameter( "refMessage" ) )) {
			referalTxt = request.getParameter( "refMessage" );
		}*/
		if (!Utils.isEmpty( request.getParameter( "assignto" ) )) {
			assignTO = request.getParameter( "assignto" );
		}
	  /*	if (!Utils.isEmpty( request.getParameter( "referalLoc" ) )) {
			referalLoc = request.getParameter( "referalLoc" );
		}*/
		referalLoc = PASServiceContext.getLocation();
		if (!Utils.isEmpty( request.getParameter( "referalComments" ) )) {
			referalComments = request.getParameter( "referalComments" );
		}
		/* Referral related parameters data setting end for TRAVEL */
		String nextaction = request.getParameter("onClickAction");
		Boolean saveOnNext = AppConstants.NEXT.equalsIgnoreCase( nextaction ) ? true : false;
		
		//Mapping General info content to PolicyDataVO object
		policyData = SvcUtils.mapGeneralInfoVO( lob, request ,commonVO );
		
		/*Map the old expiry date to get the policy extension period.*/
		/*if( !Utils.isEmpty( request.getParameter( "quote_saved_expdt" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			Date oldExpDate = converter.getTypeOfA().cast( converter.getAFromB( request.getParameter( "quote_saved_expdt" ) ) );
			if( !Utils.isEmpty( oldExpDate ) && !Utils.isEmpty( policyData.getScheme().getExpiryDate() ) && policyData.getScheme().getExpiryDate().after( oldExpDate ) ){
				policyData.setPolicyExtended( true );
				policyData.setPolicyExtensionPeriod( AppUtils.getDateDifference( policyData.getScheme().getExpiryDate() , oldExpDate ));
			}
		}*/
		
		Integer brkCode = policyData.getGeneralInfo().getSourceOfBus().getBrokerName();

		if( !Utils.isEmpty( brkCode ) ){

			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_BROKER_ACC_STATUS, brkCode );
			BigDecimal bkrStatus = null;
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				bkrStatus = ( (BigDecimal) valueHolder.get( 0 ) );
			}
			if( !Utils.isEmpty( bkrStatus ) && bkrStatus.compareTo( BigDecimal.ZERO ) == 0 ){
				throw new BusinessException( "cmn.brkblocked.pl", null, "The Brk account is blocked" );
			}
		}
		
		if( Flow.RENEWAL.equals( commonVO.getAppFlow() ) ){
			policyData.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_RENEWAL );
		}
		else if( !Utils.isEmpty( custExists ) && "true".equalsIgnoreCase( custExists ) ){
			policyData.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_NEW_FOR_EXISTING );
		}
		else{
			/*
			 * Write a private Method to check if the POl_BUSINESS_TYPE is 0 in AMEND_FLOW
			 * 
			 */
			boolean isRenewalPolicyAmend=false;
			if(Flow.AMEND_POL.equals( commonVO.getAppFlow() ) ){
				isRenewalPolicyAmend=SvcUtils.isRenewalPolicyAmend(commonVO.getPolicyId(),commonVO.getPolicyNo());
				
			}
			if(	!Utils.isEmpty(commonVO) &&	!Utils.isEmpty(commonVO.getDocCode()) && (commonVO.getDocCode() == 6 || isRenewalPolicyAmend ))
			{
				policyData.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_RENEWAL );
			}
			else
			{
				
				policyData.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_NEW );
			}
		}
		
		Gson gson = new Gson();
		boolean ignoreMappedData =false;
		// param1 is the PolicyDataVO mapped from the request in the previous insureCheck flow
		if(!Utils.isEmpty( request.getParameter(com.Constant.CONST_PARAM1) )){
			ignoreMappedData =  true;
			if(lob.equalsIgnoreCase( LOB.TRAVEL.toString() )){
				policyData = gson.fromJson(request.getParameter(com.Constant.CONST_PARAM1), TravelInsuranceVO.class);
			} else {
				policyData = gson.fromJson(request.getParameter(com.Constant.CONST_PARAM1), HomeInsuranceVO.class);
			}
			
		}
		policyData.setCommonVO( commonVO );
		String action = request.getParameter( "action" );
		
		// Ticket 152096 : Code added to check and update the Int.Acc Code for Broker login user.
		/*userProfile = AppUtils.getUserDetailsFromSession( request );
		if(userProfile.getRsaUser().getProfile().equalsIgnoreCase("Broker") && Utils.isEmpty(policyData.getGeneralInfo().getIntAccExecCode())){
			policyData.getGeneralInfo().setIntAccExecCode(Integer.parseInt(commonVO.getCreatedBy()));
		 }*/
		/* To check if the request is directly coming in from UI if so use action from request as
		 * parameter else use it from request attribute
		 */
		if( Utils.isEmpty( action ) ){
			action = (String) request.getAttribute( "action" );
		}
		
		boolean isValidationNeeded = true;
		if(!Utils.isEmpty( action ) && action.equals(COPY_QUOTE)){
			isValidationNeeded = false;
		}
		
		
		if( "INSURECHK".equalsIgnoreCase(action))
		{
			policyData = (PolicyDataVO) TaskExecutor.executeTasks( identifier, policyData );
			/* After the insure check if user opt for the master data update insured details will be updated in t_mas_insured.
			 * But Policy data/ Cash customer data will be stored irrespective of insured details changes or not/user opt for master data update or not 
			 * PolicyData set in the response( i.e responseObj.setData( policyData )) will be returned back as 'param1' in next flow and will be stored in the database.
			 * So map the UWQ for travel details also now only. It will be get saved in next flow
			*/
			if(LOB.TRAVEL.equals( LOB.valueOf( lob ))){
				/* Map the UWQuestionsVO  for travel from request */
				BaseBeanToBeanMapper<HttpServletRequest, UWQuestionsVO> requestToUWQuestionsMapper = BeanMapperFactory.getMapperInstance( RequestToUWQVOMapper.class );
				UWQuestionsVO uwQuestionsVO = null;
				uwQuestionsVO = requestToUWQuestionsMapper.mapBean(request,uwQuestionsVO);
				policyData.setUwQuestions( uwQuestionsVO );
				
				if(Flow.AMEND_POL.equals( Flow.valueOf( request.getParameter( com.Constant.CONST_APPFLOW ) ))) {
					request.setAttribute(com.Constant.CONST_AMENDPOLVATCODE, request.getParameter( com.Constant.CONST_AMENDPOLVATCODEGI ));
				}
				
			}
			responseObj.setData( policyData );			
			return responseObj;
		}
		policyData.getGeneralInfo().getInsured().setUpdateMaster( true );
		if(request.getParameter( "mastInsure" ) != null && request.getParameter( "mastInsure" ).equalsIgnoreCase( "n" )){
			if(!Utils.isEmpty( policyData.getGeneralInfo()) && !Utils.isEmpty( policyData.getGeneralInfo().getInsured()) ){
				policyData.getGeneralInfo().getInsured().setUpdateMaster( false );
			}
		}

		//Getting application flow if it is create quote or edit quote
		String appFlow = Utils.isEmpty( request.getParameter( com.Constant.CONST_APPFLOW ) ) ? commonVO.getAppFlow().toString() : request.getParameter( com.Constant.CONST_APPFLOW );

		if( !Utils.isEmpty( appFlow ) ) { 
			policyData.setAppFlow( Flow.valueOf( appFlow ) );
		}
			
			
		if( Flow.CREATE_QUO.toString().equals( appFlow ) ){
			policyData.setIsQuote( Boolean.TRUE );
		}

		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if(!Utils.isEmpty(userProfile)){
			
			policyData.setLoggedInUser(userProfile);
			//commonVO.setLoggedInUser( userProfile );
		}
		
		
		
		if(Utils.isEmpty( policyData.getStatus() )){
			policyData.setStatus( AppConstants.QUOTE_PENDING );
		}
		/* Validation for General Info sections common to Home and Travel LOBs.*/
		TaskExecutor.executeTasks( "VALIDATE_COMON", policyData );

		if( LOB.HOME.equals( LOB.valueOf( lob ) ) ){
			/* Set policy type, policy term and class code for home.*/
			policyData.setPolicyType( AppConstants.HOME_POLICY_TYPE );
			policyData.setPolicyTerm( AppConstants.HOME_POLICY_TERM );
			policyData.setPolicyClassCode( AppConstants.HOME_CLASS_CODE );
			//142244
			if(!Utils.isEmpty(policyData.getVatCode())){
				
				policyData.setVatCode(policyData.getVatCode());
			}

			/* Validate Home scheme details. */
			TaskExecutor.executeTasks( "VALIDATE_HOME", policyData );
		}
		else if(LOB.TRAVEL.equals( LOB.valueOf( lob ) )){
			
			if(!Utils.isEmpty(request.getParameter(com.Constant.CONST_AMENDPOLVATCODEGI))) {
				Integer amendPolVatCode = Integer.valueOf(request.getParameter(com.Constant.CONST_AMENDPOLVATCODEGI));
				policyData.setVatCode(amendPolVatCode);
			}
			
			if(!Utils.isEmpty(request.getAttribute(com.Constant.CONST_AMENDPOLVATCODE))) {
				Integer amendPolVatCode = Integer.valueOf((String)request.getAttribute(com.Constant.CONST_AMENDPOLVATCODE));
				policyData.setVatCode(amendPolVatCode);
			}
			
			/* Map the travel insurance VO from request.*/
			BaseBeanToBeanMapper<HttpServletRequest, TravelInsuranceVO> requestTravelInsuranceBeanMapper = BeanMapperFactory
					.getMapperInstance( RequestToTravelInsuranceVOMapper.class );
			TravelInsuranceVO travelInsuranceDetailsVo = null;
			travelInsuranceDetailsVo = requestTravelInsuranceBeanMapper.mapBean( request, travelInsuranceDetailsVo );
			
			if(ignoreMappedData){
				travelInsuranceDetailsVo = ((TravelInsuranceVO)policyData);
			}

			/* Map the UWQuestionsVO from request */
			BaseBeanToBeanMapper<HttpServletRequest, UWQuestionsVO> requestToUWQuestionsMapper = BeanMapperFactory.getMapperInstance( RequestToUWQVOMapper.class );
			UWQuestionsVO uwQuestionsVO = null;
			uwQuestionsVO = requestToUWQuestionsMapper.mapBean(request,uwQuestionsVO);
			
			if(ignoreMappedData){
				uwQuestionsVO = travelInsuranceDetailsVo.getUwQuestions();
			}
			
			policyData.setUwQuestions( uwQuestionsVO );

			/* Validate travel details. */
			if(isValidationNeeded) {
				if(Flow.AMEND_POL.equals( Flow.valueOf( appFlow ))){
					TaskExecutor.executeTasks( "VALIDATE_TRAVEL_DETAIL_AMEND", travelInsuranceDetailsVo );
				}else{
					TaskExecutor.executeTasks( "VALIDATE_TRAVEL_DETAIL", travelInsuranceDetailsVo );
				}
			}

			/* Validate traveler details. */
			List<TravelerDetailsVO> travelerList = travelInsuranceDetailsVo.getTravelDetailsVO().getTravelerDetailsList();

			for( TravelerDetailsVO travelerdetails : travelerList ){
				if(isValidationNeeded) {
					TaskExecutor.executeTasks( "VALIDATE_TRAVELER_DETAIL", travelerdetails );
				}
			}
			/*IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );*/
			/*Set class code and policy type for travel.*/
			policyData.setPolicyClassCode( AppConstants.TRAVEL_CLASS_CODE );
			// In Copy to New insured case travelers details will not be available on the screen
			if(!Utils.isEmpty( travelInsuranceDetailsVo.getPolicyTerm() )){
				if( travelInsuranceDetailsVo.getPolicyTerm() > AppConstants.LONG_TERM_TRAVEL_DAYS ){
					policyData.setPolicyType( AppConstants.TRAVEL_LONG_TERM_POLICY_TYPE );
				}
				else{
					policyData.setPolicyType( AppConstants.TRAVEL_SHORT_TERM_POLICY_TYPE );
				}
			}
			/*if( !Utils.isEmpty( Currency.getPolicyTypeScaleMap() ) && !Utils.isEmpty( Currency.getPolicyTypeScaleMap().get( converter.getBFromA( policyData.getPolicyType() ) ) ) ){
				Currency.setScale( Currency.getPolicyTypeScaleMap().get( converter.getBFromA( policyData.getPolicyType() ) ) );
			}*/
		}
		
		/** REFERRAL FLOW START */
		Redirection redirection = null;
		PolicyDataVO policyDataVO = null;
		String refIndicator = "N";
		String refParam = request.getParameter("refIndicator");
		boolean isConsolidated = false;
		if (!Utils.isEmpty(refParam)) {
			refIndicator = request.getParameter("refIndicator");
		}
		if (!Utils.isEmpty(refIndicator) && !refIndicator.equals("Y")) {
			if (SectionRHUtils.executeReferralTask(policyData, "","General Info",request)) {
				/* Save all details in General Info. */	
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
						!(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)).toString().equalsIgnoreCase("30"))
				{
					policyData.getGeneralInfo().getAdditionalInfo().setProcessingLoc( Integer.valueOf( PASServiceContext.getLocation() ));
				}
				policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_SAVE", policyData);
			} else {
				ReferralListVO refListVO = policyData.getReferralVOList();
				if (!Utils.isEmpty(policyData.getReferralVOList().getReferrals().get(0).getRefDataTextField())) {
					HttpSession session = request.getSession();
					/* Holding the referral messages map in session */
					session.setAttribute(com.Constant.CONST_REFERRALMAP, policyData.getReferralVOList().getReferrals().get(0).getRefDataTextField());
				}
				request.setAttribute("nextAction", nextaction);
				request.setAttribute("referralListVO", refListVO);
				response.setHeader("isRef", "true");
				if (policyData instanceof HomeInsuranceVO) {
					redirection = new Redirection(REFERAL_COMMON_POPUP_JSP, Type.TO_JSP);
				} else if (policyData instanceof TravelInsuranceVO) {
					isConsolidated = true;
					HttpSession session = request.getSession();
					session.setAttribute(com.Constant.CONST_CONSOLIDATE_IND, isConsolidated);
					redirection = new Redirection(CONSOLIDATED_REFERAL_POPUP_JSP, Type.TO_JSP);
				}
				responseObj.setRedirection(redirection);
				return responseObj;
			}

		} else {
			HttpSession session = request.getSession();
			boolean flag = false;
			if (!Utils.isEmpty(session.getAttribute(com.Constant.CONST_CONSOLIDATE_IND))) {
				flag = (Boolean) session.getAttribute(com.Constant.CONST_CONSOLIDATE_IND);
			}
			if (!Utils.isEmpty((Map<String, Map<String, String>>) session.getAttribute(com.Constant.CONST_REFERRALMAP))) {
				ReferralListVO referralListVO = new ReferralListVO();
				ReferralVO referralVO = new ReferralVO();
				List<ReferralVO> refVOList = new ArrayList<ReferralVO>();
				referralVO.setRefDataTextField((Map<String, Map<String, String>>) session.getAttribute(com.Constant.CONST_REFERRALMAP));
				referralVO.setLocationCode( PASServiceContext.getLocation()  );
				session.removeAttribute(com.Constant.CONST_REFERRALMAP);
				session.removeAttribute(com.Constant.CONST_CONSOLIDATE_IND);
				refVOList.add(referralVO);
				referralListVO.setReferrals(refVOList);
				policyData.setReferralVOList(referralListVO);
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
						!(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)).toString().equalsIgnoreCase("30"))
				{
					policyData.getGeneralInfo().getAdditionalInfo().setProcessingLoc( Integer.valueOf( PASServiceContext.getLocation() ));
				}
				if (flag) {
					policyData = populateTaskVO(assignTO, referalLoc, referalComments, policyData,commonVO,request);
				}
			}
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_SAVE", policyData);
			if (!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getCommonVO())) {
				AppUtils.addErrorMessage(request, "pas.saveSuccessful");
			}
		}
		/** REFERRAL FLOW END */
		
		LOGGER.info( "Exiting GeneralInfoCommonSaveRH" );

		if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getCommonVO() ) ){
			if ( !saveOnNext) {AppUtils.addErrorMessage( request, "pas.saveSuccessful" );}
			responseObj.setData(policyDataVO.getCommonVO());
		}
		// Used in copy quote flow
		if(!Utils.isEmpty( action ) && action.equals( "COPY_QUOTE" )){
			response.setHeader( "actionIdentifier", action );
		}
		return responseObj;
	}
	
	/**
	 * This method will populate the TaskVO in-case of Consolidated Referral
	 * 
	 * @param assignedTo
	 * @param referralLoc
	 * @param refComments
	 * @param policyDataVO
	 * @return
	 */
	private PolicyDataVO populateTaskVO(String assignedTo, String referralLoc, String refComments, PolicyDataVO policyData,CommonVO commonVO ,HttpServletRequest request) {
		TaskVO taskVO = null;
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		if(!Utils.isEmpty( commonVO.getTaskDetails() )){
			taskVO = commonVO.getTaskDetails();
			taskVO.setLoggedInUser( userProfile );
			taskVO.setAssignedBy( String.valueOf( userProfile.getRsaUser().getUserId() ) );
			if( !Utils.isEmpty( assignedTo ) ){
				taskVO.setAssignedTo( assignedTo );
			}
		}else{
			taskVO = (TaskVO)Utils.newInstance("com.rsaame.pas.vo.bus.TaskVO");
			if (!Utils.isEmpty(assignedTo) && !Utils.isEmpty(referralLoc) && !Utils.isEmpty(refComments)) {
				taskVO.setAssignedBy(SvcUtils.getUserId(policyData).toString());
				taskVO.setAssignedTo(assignedTo);
				taskVO.setCreatedBy(String.valueOf(SvcUtils.getUserId(policyData)));
				taskVO.setLoggedInUser( userProfile );
				taskVO.setDesc(refComments);
				taskVO.setLocation(referralLoc);
				taskVO.setPriority(Utils.getSingleValueAppConfig("DEFAULT_TASK_PRIORITY"));
			}
		}
		policyData.getReferralVOList().setTaskVO(taskVO);
		policyData.getReferralVOList().getReferrals().get(0).setLocationCode(referralLoc);
		policyData.getReferralVOList().getReferrals().get(0).setConsolidated(true);
		return policyData;
	}
}
