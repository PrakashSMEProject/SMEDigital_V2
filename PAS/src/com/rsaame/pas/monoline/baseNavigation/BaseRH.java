/**
 * 
 */
package com.rsaame.pas.monoline.baseNavigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.MVCUtils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.lookup.ui.DropDownRendererHepler;
import com.rsaame.pas.rating.svc.RatingServiceInvoker;
import com.rsaame.pas.rules.invoker.RuleServiceInvoker;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.tasks.svc.TaskSvc;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1014644
 * All the request for navigation will land here, The function performed are
 * 1) Redirect to load or save RH
 * 2) Call Rating
 * 3) Call Rule
 * 4) Refferal pop up resolution
 *
 */
public class BaseRH implements IRequestHandler{
	private static final Logger LOGGER = Logger.getLogger( BaseRH.class);
	private static final String POLICYDATA = "POLICYDATA";
	public static final Logger log = Logger.getLogger( BaseRH.class );
	private static final String RISKPAGE = "riskPage";
	private static final String GENINFO = "genInfo";
	public static final String CHECK_CUST_EXISTS_COMMON = "CHECK_CUST_EXISTS_COMMON";
	private PasReferralSaveCommonSvc pasReferralSaveCmnSvc;
	private TaskSvc taskSvc; /* Task service */
	private CommonOpSvc commonOpSvc;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	
	
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		/*
		 * Check for navigation, ie  
		 * 1) Check if button clicked is for new quote
		 * 2) check for button clicked is next, save or previous on risk page
		 * 3) check if button clicked is yes , no or ok (referral alert) on referral pop up
		 * 4) check if button clicked is close
		 * 5)  Form search screens
		 */

		// Have to make sure that appFlow and LOB is not null in request. In case of normal flow set the app flow as hidden field from commonvo
		CommonVO commonVO = null;
		PolicyDataVO policyDataVO = null;
		PolicyContext policyContext = null;
		String appFlow = request.getParameter( "appFlow" );
		String lob = request.getParameter( "lob" );
		String navigation = request.getParameter( com.Constant.CONST_NAVIGATION );
		
		

		/*
		 * The page type will be available in the request which will be used to resolve the page
		 * request.getParameter(com.Constant.CONST_PAGETYPE) can be GENINFO or RISKPAGE
		 */
		
		// Check if new customer flag
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_ISNEWCUST ) )){
			request.setAttribute(com.Constant.CONST_ISNEWCUST, (String)request.getParameter( com.Constant.CONST_ISNEWCUST ) );
		}

		if( appFlow.equalsIgnoreCase( Flow.CREATE_QUO.toString() ) && Utils.isEmpty(navigation)){
			commonVO = new CommonVO();
			commonVO.setLob( LOB.valueOf( lob ) );
			populateCommonVOForCreateQuote( commonVO );
			commonVO.setAppFlow( Flow.CREATE_QUO );
			PolicyContextUtil.createPolicyContext( request, appFlow, LOB.valueOf( lob ) );
	        setAuthdetails(request);
			policyContext = PolicyContextUtil.getPolicyContext( request );

			UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );

			/* Set the profile type and broker id to request since for broker login scheme drop down needs to populated 
			 * on load of GeneralInfoContent rather than the basis of distribution channel and broker code selection */
			AppUtils.setUserProfileDetsToRequest( request, userProfile );
			
			commonVO.setLoggedInUser( userProfile );
			commonVO.setCreatedBy( userProfile.getRsaUser().getUserId().toString() );
			
			request.setAttribute( "currentLob", lob );
			request.setAttribute( AppConstants.USER_ID, userProfile.getRsaUser().getUserId().toString() );
			
			policyContext.setCommonDetails( commonVO );
			request.setAttribute( "policyType",  Utils.getSingleValueAppConfig( commonVO.getLob() + "_POL_TYPE" ) );
			request.setAttribute( "classCode",  Utils.getSingleValueAppConfig( commonVO.getLob() + "_CLASS_CODE" ) );
			
			setCountry(request);	
			
            setDefaultIntAccExeToReq(request);
			setDefaultEmirateToReq(request);
			
			// 142244 WC VAT Code
			
				if(policyContext.getAppFlow().equals(Flow.CREATE_QUO)) {
					Integer policyClassCode = null;
					Integer wcPolicyTypeCode = null;
					List<Object> vatData = Collections.emptyList();
					 if(LOB.WC.equals(LOB.valueOf(lob))) {
						policyClassCode = Integer.valueOf( Utils.getSingleValueAppConfig("WC_CLASS_CODE"));
						wcPolicyTypeCode = Integer.valueOf( Utils.getSingleValueAppConfig("WC_POLICY_TYPE"));				
						vatData=  DAOUtils.VatCodeAndVatRate(policyClassCode, wcPolicyTypeCode,
																								SvcConstants.SC_PRM_COVER_VAT_TAX);
					}
					
					if(!Utils.isEmpty(vatData)) {					
						if(!Utils.isEmpty( vatData.get(1))) {		
							request.setAttribute("vatCodeonGI", vatData.get(1));
							LOGGER.debug( "**BASE RH On Load()**  vat Code" + vatData.get(1));					
						}
					}
				}
			
			
			// 142244 WC VAT Code end
			
			if( !Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getDocCode() ) && commonVO.getIsQuote() && commonVO.getDocCode() == 6 ){
				AppUtils.setExpiryDateForRenewal( commonVO, request );
			}
			
			PolicyDataVO value = (PolicyDataVO) request.getAttribute( "policyDetails" );
			
			request.setAttribute("value",value); 
			
			request.setAttribute( "isAddToQuote", (Boolean)request.getAttribute( "isAddToQuote" ) ); 
			
			Response mtrucResponse = new Response();
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_GENINFO_END + commonVO.getLob() ), Type.TO_JSP ) );
			mtrucResponse.setSuccess( true );
			return mtrucResponse;
		}
		else if(appFlow.equalsIgnoreCase( Flow.RENEWAL.toString() )){
			commonVO = new CommonVO();
			commonVO.setLob( LOB.valueOf( request.getParameter( "product" ).toString() ) );
			populateCommonVOForCreateQuote( commonVO );
			commonVO.setAppFlow( Flow.RENEWAL );
			PolicyContextUtil.createPolicyContext( request, appFlow, LOB.valueOf( request.getParameter( "product" ).toString() ) );
	        setAuthdetails(request);
			policyContext = PolicyContextUtil.getPolicyContext( request );
			UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
			AppUtils.setUserProfileDetsToRequest( request, userProfile );
			commonVO.setLoggedInUser( userProfile );
			commonVO.setCreatedBy( userProfile.getRsaUser().getUserId().toString() );
			policyContext.setCommonDetails( commonVO );
		}
		
		/*
		 * Combination of 3 flags will be used to determine the generic navigation for Monoline pages
		 * The assumption for monoline is that there will be only 2 pages , general info and risk page. But provision for multiple pages is incorporated 
		 * Flags used:
		 * a, navigation b, pageType (generalinfo or riskpage) c, refferalPageType (normal, alert, consolidate)
		 * 
		 */
		
		policyContext = PolicyContextUtil.getPolicyContext( request );
		commonVO = policyContext.getCommonDetails();

		if( appFlow.equalsIgnoreCase( "TRANS_SEARCH" ) && !Utils.isEmpty( commonVO.getDocCode() ) ){
				navigation = NAVIGATION.LOAD.toString();
				request.setAttribute( com.Constant.CONST_NAVIGATION , navigation);
		}
			
		/*
		 * If navigation is Save or Next then rules to be triggered. So navigation list should add Rules validation first then
		 * navigation mentioned in request.
		 */
		
		Response mtrucResponse = null;
		IRHHelper helper =  (IRHHelper) Utils.getBean( "LOB_RH_"+commonVO.getLob() );
		request.setAttribute(AppConstants.FUNTION_NAME,  policyContext.getAppFlow().toString() );
		
		switch( NAVIGATION.valueOf( navigation ) ){

			case SAVE:
				mtrucResponse =  handleSaveOperation(request,response,commonVO,helper);
				if(Utils.isEmpty(mtrucResponse)){
					mtrucResponse = resolveNavigation( request, response,mtrucResponse );	
				}
							
				break;

			case PREVIOUS:
				mtrucResponse = handleNavigationForPrevious(request,response,commonVO,helper);
				break;

			case REFERAL_YES:
				/*
				 * If Referral yes is clicked
				 * a, Referral data needs to be saved and same page needs to be reloaded if save is clicked
				 * b, Referral data needs to be save and next page needs to be loaded in case next is clicked
				 * c, Referral data needs to be save and home page needs to be loaded in case its risk page or last page
				 */
				mtrucResponse =  handleSaveOperation(request,response,commonVO,helper);
				if(Utils.isEmpty(mtrucResponse)){
					mtrucResponse = resolveNavigation( request, response,mtrucResponse );
				}
				break;

			case REFERAL_NO:
				/*
				 * Clear the referral related session data or any other operation
				 * Sarath : As per new referral pop up, nothing to do here.
				 */

				break;

			case NEXT:
				mtrucResponse =  handleSaveOperation(request,response,commonVO,helper);
				if(Utils.isEmpty(mtrucResponse)){
			    	mtrucResponse = resolveNavigation( request, response , mtrucResponse);
				}
				break;
			case PREMIUM_POPULATION:
				policyDataVO = (PolicyDataVO) helper.mapRequestToVO( request, response, commonVO );
				invokeRating(policyDataVO, request, helper);
				helper.ratingPostProcessing(  request,   policyDataVO  );
				ThreadLevelContext.set( POLICYDATA,policyDataVO);
				mtrucResponse = resolveNavigation( request, response , mtrucResponse);
				break;
				
			case LOAD:
				ThreadLevelContext.set( POLICYDATA, handleRiskPageLoad( request, response, commonVO, helper ) );
				mtrucResponse = resolveNavigation( request, response, mtrucResponse );
				break;
			case RULE_VALIDATION:
				policyDataVO = (PolicyDataVO) helper.mapRequestToVO( request, response, commonVO );
				mtrucResponse = invokeRule(request, response, policyDataVO, helper);
				break;
			case VIEW_CONDITIONS:
				mtrucResponse = resolveNavigation( request, response, mtrucResponse );
				break;
			case EDIT_QUOTE:				
				handleEditQuote(request,response,commonVO,helper);				
				mtrucResponse = resolveNavigation( request, response, mtrucResponse );
				break;
			
			case AMEND_POL:				
				handleAmendPol(request,response,commonVO,helper);				
				mtrucResponse = resolveNavigation( request, response, mtrucResponse );
				break;
				
			case GENERATE_ONLINE_RENEWALS:
				handleGenerateOnlineRenewals(request, response, mtrucResponse);
				break;
				
			case GENERATE_BATCH_RENEWALS:
				handleGenerateBatchRenewals(request, response, mtrucResponse);
				break;
				
			default:
				break;
			}
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		// Set user details to request.
		AppUtils.setUserProfileDetsToRequest( request, userProfile );
		return mtrucResponse;
	}

	private Response handleNavigationForPrevious( HttpServletRequest request, HttpServletResponse response, CommonVO commonVO, IRHHelper helper ){
		BaseVO baseVO = helper.mapRequestToVO( request, response, commonVO );
		((PolicyDataVO)baseVO).setCommonVO(commonVO);
		ThreadLevelContext.set( POLICYDATA, baseVO);
		helper.mapVOTORequest(request, response, baseVO);
		Response mtrucResponse = new Response();
		mtrucResponse = resolveNavigation( request, response, mtrucResponse );
		return mtrucResponse;

}

	private void setDefaultIntAccExeToReq(HttpServletRequest request) {
		PolicyDataVO polVo = (PolicyDataVO) request.getAttribute(AppConstants.PAGE_VALUE);
		CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
		
		/*
		 * For quotes, internal executive is the user who issues the quote.
		 * For renewal quotes, internal executive is the user who first issues the quote.
		 */
		if(!Utils.isEmpty( commonVO.getDocCode() )&& commonVO.getDocCode() == Integer.parseInt( Utils.getSingleValueAppConfig( "NEW_RENEWAL_QUOTATION" ) )){
			if(commonVO.getEndtNo() == 0 && (Flow.EDIT_QUO.equals( commonVO.getAppFlow()))){
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, Integer.parseInt( Utils.getSingleValueAppConfig( "DEF_ACC_EXE_RENEWEL" )) );
				 
			}else{
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, polVo.getGeneralInfo().getIntAccExecCode() );
			}
		}
		else if (Flow.CREATE_QUO.equals( commonVO.getAppFlow() )){
			request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, SvcUtils.getUserId( commonVO) );
		}
		else if( !Utils.isEmpty(polVo.getGeneralInfo()) && !Utils.isEmpty(polVo.getGeneralInfo().getIntAccExecCode())) { // case of reload
			request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, polVo.getGeneralInfo().getIntAccExecCode() );
		}else{
			request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, SvcUtils.getUserId( commonVO) );// If any case where generalInfo could be null
		}
	}
	
	private void setDefaultEmirateToReq( HttpServletRequest request ){
		CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
		String emirateDesc = SvcUtils.getLookUpDescription( "BRANCH", SvcUtils.getUserId( commonVO ).toString(), "ALL", Integer.parseInt( loggenInLoc ));
		Integer emirate=  SvcUtils.getLookUpCode("CITY", "ALL", "ALL", emirateDesc);
		PolicyDataVO polVo = (PolicyDataVO) request.getAttribute(AppConstants.PAGE_VALUE); 
		if(Utils.isEmpty( polVo )){//case of initial load
			request.setAttribute( AppConstants.REQ_ATTR_EMIRATE_DEFAULT_VAL, emirate );
		}else if(!Utils.isEmpty( polVo )){
			if(!Utils.isEmpty( polVo.getGeneralInfo() ) && !Utils.isEmpty( polVo.getGeneralInfo().getInsured().getCity() )){//case of reload
				request.setAttribute( AppConstants.REQ_ATTR_EMIRATE_DEFAULT_VAL,  polVo.getGeneralInfo().getInsured().getCity()  );
			}else{//if on some case,polVo is null
				request.setAttribute( AppConstants.REQ_ATTR_EMIRATE_DEFAULT_VAL, emirate );
			}
		}
		
	}
	
	private void setAuthdetails( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );
	}
	private Response handleSaveOperation( HttpServletRequest request, HttpServletResponse response, CommonVO commonVO, IRHHelper helper ){
		/*
		 * Save operation involves
		 * 1,mapping request to vo
		 * 2,Server side validation if any
		 */
		Response mtrucResponse = null;

		// Mapping request parameter to vo
		BaseVO baseVO = helper.mapRequestToVO( request, response, commonVO );
		((PolicyDataVO)baseVO).setCommonVO(commonVO);
		String pageType = request.getParameter( com.Constant.CONST_PAGETYPE );
		String identifier = request.getParameter( "opType" );
		Boolean proceedWithReferral = Utils.isEmpty(request.getParameter( "proceedWithReferral" )) ? 
				false : Boolean.valueOf( request.getParameter( "proceedWithReferral" ) );
		
		// Validation task  if required, else the validation can be left blank or based on condition
		if( !Utils.isEmpty( Utils.getSingleValueAppConfig( "LOB_VALIDATION_" + pageType +"_"+commonVO.getLob() ) ) ){
			TaskExecutor.executeTasks( Utils.getSingleValueAppConfig( "LOB_VALIDATION_" + pageType +"_"+ commonVO.getLob() ), baseVO );
		}
		String checkIfInsuredExistsFlag =  request.getParameter( "checkIfInsuredExistsFlag" );
		String mastInsure =  request.getParameter( "mastInsure" );
		
		/*
		 * check if customer exists
		 */
		if(Utils.isEmpty(checkIfInsuredExistsFlag)){
			checkIfInsuredExistsFlag = "true";
		}
		
		if(!Utils.isEmpty(baseVO) && !Utils.isEmpty(((PolicyDataVO)baseVO).getCommonVO())
				&& Utils.isEmpty(((PolicyDataVO)baseVO).getCommonVO().getQuoteNo()) && checkIfInsuredExistsFlag.equalsIgnoreCase("true") 
				&& pageType.equalsIgnoreCase(GENINFO) && !proceedWithReferral){
			Redirection redirection = null;
			request.setAttribute("checkIfInsuredExistsFlag",checkIfInsuredExistsFlag) ;
			redirection = new Redirection( CHECK_CUST_EXISTS_COMMON, Type.TO_NEW_OPERATION );
			if( Utils.isEmpty( mtrucResponse ) ){
				mtrucResponse = new Response();
			}
			/*Added try and catch block to avoid null pointer , sonar violation fix on 9-10-2017*/
			try{
				mtrucResponse.setRedirection( redirection );
			}catch (NullPointerException e) {
				LOGGER.debug("Null pointer exception ");
			}
			
			return mtrucResponse;
		} else {
		
			if( checkIfInsuredExistsFlag.equalsIgnoreCase("NA") && GENINFO.equalsIgnoreCase(pageType) && Utils.isEmpty( mastInsure ) ){
				baseVO = (PolicyDataVO) TaskExecutor.executeTasks(identifier,
						baseVO);
				if( !Utils.isEmpty(baseVO) && ((PolicyDataVO)baseVO).isInsuredChanged() ){
					if (Utils.isEmpty(mtrucResponse)) {
						mtrucResponse = new Response();
					}
					response.setHeader( "isInsuredChanged", "true" );
					//mtrucResponse.setData(baseVO);
					return mtrucResponse;
				}
			}
		
		}
		if(!proceedWithReferral){
			mtrucResponse = invokeRule( request, response, (PolicyDataVO)baseVO, helper );

			if( !Utils.isEmpty( mtrucResponse ) ){
				request.setAttribute("mastInsure", Utils.isEmpty( mastInsure ) ? "" : mastInsure);
				return mtrucResponse;
			}
		}
		if ( !Utils.isEmpty( request.getParameter( com.Constant.CONST_PAGETYPE ) ) && !request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO) ){
			invokeRating( baseVO, request, helper );
		}
		else
		{
			if( Flow.RENEWAL.equals( commonVO.getAppFlow() ) ){
				((PolicyDataVO)baseVO).getGeneralInfo().getInsured().setPolBusType( AppConstants.BUS_TYPE_RENEWAL );
			}
			else if( !Utils.isEmpty( checkIfInsuredExistsFlag ) && "true".equalsIgnoreCase( checkIfInsuredExistsFlag ) ){
				((PolicyDataVO)baseVO).getGeneralInfo().getInsured().setPolBusType( AppConstants.BUS_TYPE_NEW_FOR_EXISTING );
			}
			else{
				if(	!Utils.isEmpty(commonVO) &&	!Utils.isEmpty(commonVO.getDocCode()) && commonVO.getDocCode() == 6 )
				{
					((PolicyDataVO)baseVO).getGeneralInfo().getInsured().setPolBusType( AppConstants.BUS_TYPE_RENEWAL );
				}
				else
				{
					((PolicyDataVO)baseVO).getGeneralInfo().getInsured().setPolBusType( AppConstants.BUS_TYPE_NEW );
				}
			}
		}
		
		/*
		 * For referral flow, Approve Quote/Policy
		 */
		
		String operation = request.getParameter( "operation" );
		if(!Utils.isEmpty(operation) && operation.equals( AppConstants.APPROVE_QUO )){
			helper.referralAprrove( request, response, baseVO);
			
			String message = "";
			if(commonVO.getIsQuote()){
				message="pas.approveQuoteSuccessful";
			} else {
				 message="pas.approvePolicySuccessful";
			}
			commonVO.setStatus( AppConstants.QUOTE_ACCEPT );
			helper.postSaveProcessing( request, response, mtrucResponse, baseVO );
			response.setHeader( "isApprove", "true" );
			if (Utils.isEmpty(mtrucResponse)) {
				mtrucResponse = new Response();
			}
			mtrucResponse.setResponseType( Response.Type.JSON );
			AppUtils.addErrorMessage( request, message );
			AppUtils.sendMail( (PolicyDataVO)baseVO, "APPROVE" );
			return mtrucResponse;
		}
		
		
	
		if(proceedWithReferral){
			if( ( helper.isConsolidatedReferralScreen(request, (PolicyDataVO)baseVO)) ){
				saveReferralData(request, baseVO ,helper);
				commonVO.setStatus( SvcConstants.POL_STATUS_REFERRED );
			}
		}
		baseVO = helper.saveData( request, response, mtrucResponse, baseVO );
		if(proceedWithReferral && !( helper.isConsolidatedReferralScreen(request, (PolicyDataVO)baseVO))  ){
			saveReferralData(request, baseVO ,helper);
		}
		ThreadLevelContext.set( POLICYDATA, baseVO );
		if(((PolicyDataVO)baseVO).getCommonVO().getStatus() == SvcConstants.POL_STATUS_ACTIVE){
			((PolicyDataVO)baseVO).getCommonVO().setTaskDetails(null);
		}
		helper.postSaveProcessing( request, response, mtrucResponse, baseVO );
		baseVO = helper.loadData(request, response, baseVO);
		helper.mapVOTORequest( request, response, baseVO );
		return mtrucResponse;
	}
	
	private Response resolveNavigation( HttpServletRequest request, HttpServletResponse response, Response mtrucResponse ){
		String navigation = request.getParameter( com.Constant.CONST_NAVIGATION );
		String baseNavigation = request.getParameter( "baseNavigation" );
		String pageType = request.getParameter( com.Constant.CONST_PAGETYPE );
		/*
		 * Below if block should e executed only for Resolve referral flow.
		 * So we add check for page type GENINFO as during resolve referral page type comes as GENINFO
		 * but during transaction search page type will be null that time we need to load WC page.  
		 */
		if(Utils.isEmpty(navigation) && !Utils.isEmpty(request.getAttribute( com.Constant.CONST_NAVIGATION )) && !Utils.isEmpty( pageType ) &&  pageType.equalsIgnoreCase( GENINFO )){
			navigation = request.getAttribute( com.Constant.CONST_NAVIGATION ).toString();
		}
		
		BaseVO  baseVO =  (BaseVO) ThreadLevelContext.get( POLICYDATA );
		PolicyDataVO dataVO = (PolicyDataVO) baseVO;
		
		// 142244 Added VatCode for report Generation purpose
		if(!Utils.isEmpty( dataVO) && !Utils.isEmpty( dataVO.getCommonVO()) && !Utils.isEmpty(dataVO.getVatCode())) {
				dataVO.getCommonVO().setVatCode(dataVO.getVatCode());
			dataVO.getCommonVO().setPolExpiryDate(dataVO.getPolExpiryDate());
		}
		//dataVO.getCommonVO().getPremiumVO().setVatCode(dataVO.getPremium().getVatCode());
	
		
		if( Utils.isEmpty( mtrucResponse ) ){
			mtrucResponse = new Response();
		}
		
		if ( (Utils.isEmpty( navigation ) ||NAVIGATION.valueOf( navigation ).equals( NAVIGATION.LOAD ))  && Utils.isEmpty( pageType ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_RISKPAGE_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.PREVIOUS ) && pageType.equalsIgnoreCase( RISKPAGE ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_GENINFO_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.NEXT ) && pageType.equalsIgnoreCase( GENINFO ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_RISKPAGE_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.SAVE ) && pageType.equalsIgnoreCase( RISKPAGE ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_RISKPAGE_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.SAVE ) && pageType.equalsIgnoreCase( GENINFO ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_GENINFO_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.VIEW_CONDITIONS )){
			mtrucResponse.setRedirection(new Redirection(  "VIEW_CONDITIONS_HOME_TRAVEL" , Redirection.Type.TO_NEW_OPERATION ));
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.LOAD ) && pageType.equalsIgnoreCase( RISKPAGE ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_RISKPAGE_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if ( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.LOAD )  && pageType.equalsIgnoreCase( RISKPAGE ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_RISKPAGE_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if ( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.LOAD )  && pageType.equalsIgnoreCase( GENINFO ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_GENINFO_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.EDIT_QUOTE ) ||  NAVIGATION.valueOf( navigation ).equals( NAVIGATION.AMEND_POL )  )
		{
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_GENINFO_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.PREMIUM_POPULATION ) && pageType.equalsIgnoreCase( RISKPAGE ) )
		{
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_RISKPAGE_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.REFERAL_YES ) && pageType.equalsIgnoreCase( RISKPAGE ) )
		{
			mtrucResponse.setRedirection( new Redirection("/jsp/homePage_content.jsp",Redirection.Type.TO_JSP) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.REFERAL_YES ) && pageType.equalsIgnoreCase( GENINFO ) && NAVIGATION.valueOf( baseNavigation ).equals( NAVIGATION.SAVE ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_GENINFO_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		else if( NAVIGATION.valueOf( navigation ).equals( NAVIGATION.REFERAL_YES ) && pageType.equalsIgnoreCase( GENINFO ) && NAVIGATION.valueOf( baseNavigation ).equals( NAVIGATION.NEXT ) ){
			mtrucResponse.setRedirection( new Redirection( Utils.getSingleValueAppConfig( com.Constant.CONST_LOB_RISKPAGE_END + dataVO.getCommonVO().getLob() ), Type.TO_JSP ) );
		}
		return mtrucResponse;
	}

	
	/*
	 * For invoking rating for LOB the abstract class RatingServiceInvoker needs to be extended and the abstract method implemented
	 */
	private void invokeRating( BaseVO baseVO, HttpServletRequest request, IRHHelper helper ){
		PolicyDataVO dataVO = (PolicyDataVO) baseVO;
		RatingServiceInvoker ratingService = (RatingServiceInvoker) Utils.getBean( "LOB_RATING_" + dataVO.getCommonVO().getLob() );
		//baseVO = ratingService.invokeRating( baseVO );
		ratingService.invokeRating( baseVO );
	}
	
	private Response invokeRule( HttpServletRequest request, HttpServletResponse response, BaseVO baseVO, IRHHelper helper ){
		
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		Response mtrucResponse = null;
		String pageType = request.getParameter( com.Constant.CONST_PAGETYPE );
		String navigation = request.getParameter( com.Constant.CONST_NAVIGATION );
		if( Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) ) ){

			ReferralListVO listReferralVO = null;
			String [] sectionIDValues = Utils.getMultiValueAppConfig(policyDataVO.getCommonVO().getLob().toString() + "_SECTION_ID");
			Integer[] sectionID = new Integer[sectionIDValues.length];

			if(pageType.equalsIgnoreCase(com.Constant.CONST_GENINFO)){
				sectionID[0] = Integer.valueOf(AppConstants.zeroVal);
			} else {
				for (int i = 0; i < sectionIDValues.length; i++){
					sectionID[i] = Integer.valueOf(sectionIDValues[i]);
				}
			}
			if(!(navigation.equals(NAVIGATION.RULE_VALIDATION.name()) && policyDataVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL))){
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils.getBean( "ruleServiceInvoker" );
				UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
				AppUtils.setEndorsementVO( request, policyDataVO );
				ruleServiceInvoker.callRestFulRuleService( policyDataVO, sectionID, userProfile.getRsaUser().getHighestRole( policyDataVO.getCommonVO().getLob().toString() ) );
			}
		}

		/*
		 * Resetting to null after the rule call since it will not be used during save
		 */
		if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getEndorsmentVO() )){
			policyDataVO.setEndorsmentVO( null );
		}
		mtrucResponse = getRuleResponseAction(request,response, policyDataVO, helper,pageType);

		return mtrucResponse;
	}

	/**
	 * Get the response screen in case rule fails
	 * @param request
	 * @param response
	 * @param policyDataVO
	 * @param helper
	 * @param pageType
	 * @return
	 */
	private Response getRuleResponseAction(HttpServletRequest request, HttpServletResponse response, PolicyDataVO policyDataVO, IRHHelper helper, String pageType) {
		
		Response mtrucResponse = null;
		HttpSession session = request.getSession();
		session.setAttribute(com.Constant.CONST_REFERRALMAP, null);
		
		Map<String, String> referralMessages =  DAOUtils.getReferralMessages( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId(),
				     (((UserProfile)policyDataVO.getCommonVO().getLoggedInUser() ).getRsaUser().getUserId() ) );
		if(!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getReferralVOList())
				&& !Utils.isEmpty(policyDataVO.getReferralVOList().getReferalType()) ){
			if(!policyDataVO.getReferralVOList().getReferalType().equals("Pass")){
				mtrucResponse = new Response();
				Redirection redirection = new Redirection("/jsp/common/referralPopUp.jsp",Redirection.Type.TO_JSP);
				mtrucResponse.setRedirection( redirection );
				request.setAttribute("referralListVO", policyDataVO.getReferralVOList());
				request.setAttribute("LOB", policyDataVO.getCommonVO().getLob());
				request.setAttribute("isConsolidatedReferral", helper.isConsolidatedReferralScreen(request, policyDataVO));
				Map<String, Map<String, String>> referralMsgMap = new HashMap<String, Map<String, String>>();
				for(ReferralVO referralVO : policyDataVO.getReferralVOList().getReferrals()){
					referralMsgMap.putAll(referralVO.getRefDataTextField());
				}
				
				session.setAttribute(com.Constant.CONST_REFERRALMAP, referralMsgMap);
					
				request.setAttribute("referralDetails", MVCUtils.getAsJsonString(referralMsgMap));
			
			}else if(!pageType.equalsIgnoreCase(com.Constant.CONST_GENINFO) && (!Utils.isEmpty(request.getParameter( com.Constant.CONST_NAVIGATION )) && !request.getParameter( com.Constant.CONST_NAVIGATION ).equalsIgnoreCase("RULE_VALIDATION"))
					 && !Utils.isEmpty(referralMessages)){
				/* This clock to be called only on Save Quote/ Confirm Endorsement. i.e check for RULE_VALIDATION is added because Rule validation will be done
				 * tab out of parameters of risk page.
				 */
				
				Redirection redirection = null;
				mtrucResponse = redirectReferral(policyDataVO,request,response,mtrucResponse,redirection,helper);
			}
		}
		return mtrucResponse;
	}

	/**
	 * Method to redirect to referral
	 * @param homeInsuranceVO
	 * @param request
	 * @param response
	 * @param responseObj
	 * @param redirection
	 * @param helper 
	 */
	private Response redirectReferral(PolicyDataVO policyDataVO, HttpServletRequest request, HttpServletResponse response, Response responseObj, Redirection redirection, IRHHelper helper ){
		@SuppressWarnings( "unchecked" )
		Map<String,Map<String, String>> displayedReferral = (Map<String,Map<String, String>>)request.getSession().getAttribute( "displayedReferral" );
		Map<String,Map<String, String>> ruleReferral = null;
		
		if( !Utils.isEmpty( policyDataVO.getReferralVOList() ) && !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals() )
						&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().size() > 0 )
						&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
			ruleReferral = policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField();
		}
		
		boolean popUpDisplay = true;
		
		List<String> removeReferralList = new ArrayList<String>(0);
		
		/* To filter already shown referrals */
		if( !Utils.isEmpty( displayedReferral ) && !Utils.isEmpty( ruleReferral )){
			for( String displayedReferralFieldName : displayedReferral.keySet() ){
				Map<String, String> displayedReferralValues = displayedReferral.get( displayedReferralFieldName );

				for( String fieldName : ruleReferral.keySet() ){
					Map<String, String> referralValues = ruleReferral.get( fieldName );

					for( String displayedValue : displayedReferralValues.keySet() ){
						for( String referralValue : referralValues.keySet() ){
							
							if( displayedReferralFieldName.equalsIgnoreCase( fieldName ) && displayedValue.equalsIgnoreCase( referralValue ) ){
								removeReferralList.add( fieldName );
							}
						}
					}
				}
			}
			
			for( String fieldName : ruleReferral.keySet() ){
				
				if( !removeReferralList.contains( fieldName ) ){
					popUpDisplay = true;
					break;
				}else{
					popUpDisplay = false;
				}
			}
		}
		
		if(popUpDisplay){
			List<String> messageList = new ArrayList<String>();
			ReferralListVO refListVO = policyDataVO.getReferralVOList();
			if( !Utils.isEmpty( refListVO ) ){
				refListVO.setReferalType("Fail");
				if(!Utils.isEmpty( refListVO.getReferrals() )){
					
					for (ReferralVO refVO : refListVO.getReferrals()) {
						for (String refText : refVO.getReferralText()) {
							String message = new String(refText);
							messageList.add(message);
						}
					}
				}
			}
	
			String nextaction =  request.getParameter( com.Constant.CONST_NAVIGATION );
			Map<String, String> referralMessages =  DAOUtils.getReferralMessages( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId(),
				     (((UserProfile)policyDataVO.getCommonVO().getLoggedInUser() ).getRsaUser().getUserId() ) );
			
			if( !Utils.isEmpty( policyDataVO.getReferralVOList() ) && !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals() )
					&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().size() > 0 )
					&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
				HttpSession session = request.getSession();
				//Holding the referral messages map in session
				session.setAttribute( com.Constant.CONST_REFERRALMAP, policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
				request.getSession( false ).removeAttribute( "displayedReferral" );
			} else if(!Utils.isEmpty(referralMessages)){
				HttpSession session = request.getSession();
							
			}
			
			request.setAttribute( "nextAction", nextaction );
			request.setAttribute( "referralListVO", refListVO );
			request.setAttribute( "LOB",request.getParameter( "lob" ));
			request.setAttribute("isConsolidatedReferral", (helper.isConsolidatedReferralScreen(request, policyDataVO)).toString());
			
			response.setHeader( "isRef", "true" );
			redirection = new Redirection( "/jsp/common/referralPopUp.jsp", Type.TO_JSP );
			responseObj = new Response();
			responseObj.setRedirection( redirection );
		}
		return responseObj;
	}
	
	private  BaseVO handleRiskPageLoad( HttpServletRequest request, HttpServletResponse response, CommonVO commonVO, IRHHelper helper ){

		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		commonVO = policyContext.getCommonDetails();
		PolicyDataVO policyDataVO = new PolicyDataVO();
		policyDataVO.setCommonVO(commonVO);
		BaseVO baseVO = helper.loadData( request, response, policyDataVO );
		helper.mapVOTORequest( request, response, baseVO );
		return baseVO;
	}
	

	/**
	 * @param request
	 * @param commonVO
	 * @return
	 */
	/*private void populateCommonVO( HttpServletRequest request, CommonVO commonVO ){
		Fetch the required params from request and set them into CommonVO.
		Long polQuoNo = Long.valueOf( request.getParameter( "polQuoNo" ) );
		Long endtId = Long.valueOf( request.getParameter( "endID" ) );
		String branch = request.getParameter( "branch" );
		String transType = request.getParameter( "tranType" );
		String lob = request.getParameter( "LOB" );
		SimpleDateFormat generalDateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
		String transDate = request.getParameter( "polEffectiveDate" );
		Boolean isQuote = Boolean.valueOf( request.getParameter( "isQuote" ) );
		String appFlow = request.getParameter( "appflow" );
		if( !Utils.isEmpty( transDate ) ){
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
		Integer brCode = SvcUtils.getLookUpCode( "BRANCH", userProfile.getRsaUser().getUserId().toString(), "ALL", branch );
		if( !Utils.isEmpty( brCode ) ){
			commonVO.setLocCode( brCode );
		}
		else{
			commonVO.setLocCode( Integer.valueOf( branch ) ); //Temporary Fix, need to be removed from future
		}
		if( !Utils.isEmpty( transType ) ){
			commonVO.setDocCode( SvcUtils.getLookUpCode( "DOC_TYPE_ALL", "ALL", "ALL", transType ).shortValue() );
		}
		// Set the branch code to service context after transaction search
		if( !Utils.isEmpty( brCode ) ){
			PASServiceContext.setLocation( brCode.toString() );
			request.getSession().setAttribute( AppConstants.CTX_LOCATION, brCode.toString() );
		}

		String[] quoDocumentCodes = Utils.getMultiValueAppConfig( AppConstants.QUOTE_DOC_CODES, "," );
		String[] polDocumentCodes = Utils.getMultiValueAppConfig( AppConstants.POLICY_DOC_CODES, "," );
		if( !Utils.isEmpty( commonVO.getDocCode() ) ){
			if( !Utils.isEmpty( quoDocumentCodes ) && quoDocumentCodes.length > 0 && CopyUtils.asList( quoDocumentCodes ).contains( commonVO.getDocCode().toString() ) ){

				commonVO.setQuoteNo( polQuoNo );
				commonVO.setIsQuote( Boolean.TRUE );
				commonVO.setAppFlow( Flow.VIEW_QUO );

			}
			else if( !Utils.isEmpty( polDocumentCodes ) && polDocumentCodes.length > 0 && CopyUtils.asList( polDocumentCodes ).contains( commonVO.getDocCode().toString() ) ){

				commonVO.setPolicyNo( polQuoNo );
				commonVO.setIsQuote( Boolean.FALSE );
				commonVO.setAppFlow( Flow.VIEW_POL );

			}
		}
		// case of loading the general Info in resolve referral case
		if( !Utils.isEmpty( appFlow ) && appFlow.equalsIgnoreCase( Flow.RESOLVE_REFERAL.toString() ) ){
			if( isQuote.booleanValue() ){
				commonVO.setQuoteNo( polQuoNo );
				commonVO.setIsQuote( Boolean.TRUE );
				commonVO.setAppFlow( Flow.valueOf( appFlow ) );
			}
			else{
				commonVO.setPolicyNo( polQuoNo );
				commonVO.setIsQuote( Boolean.FALSE );
				commonVO.setAppFlow( Flow.valueOf( appFlow ) );
			}
		}
		//	commonVO = (CommonVO)TaskExecutor.executeTasks( action , commonVO );

		PolicyContextUtil.createPolicyContext( request, commonVO.getAppFlow().toString(), commonVO.getLob() );
		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		commonVO.setLoggedInUser( userProfile );
		commonVO.setCreatedBy( userProfile.getRsaUser().getUserId().toString() );
		context.setCommonDetails( commonVO );
	}*/

	/**
	 * 
	 * @param commonVO
	 */
	private void populateCommonVOForCreateQuote( CommonVO commonVO ){
		commonVO.setIsQuote( Boolean.TRUE );
		commonVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
		commonVO.setEndtId( Long.valueOf( SvcConstants.zeroVal ) );
		commonVO.setEndtNo( Long.valueOf( SvcConstants.zeroVal ) );
		commonVO.setLocCode( Integer.valueOf( PASServiceContext.getLocation() ) );
		commonVO.setIsQuote( true );

	}

	private enum NAVIGATION{
		SAVE, NEXT, PREVIOUS, REFERAL_NO, REFERAL_YES, PREMIUM_POPULATION,LOAD, RULE_VALIDATION, VIEW_CONDITIONS, EDIT_QUOTE, AMEND_POL,
		GENERATE_ONLINE_RENEWALS, GENERATE_BATCH_RENEWALS
	};

	/**
	 * Method to identify the appFlow
	 * @param request
	 * @return
	 */
/*	private String identifyTransFlow( HttpServletRequest request ){
		Boolean quoteFlag = isQuote( request );
		return ( quoteFlag ) ? "VIEW_QUO" : "VIEW_POL";
	}*/

	/*private Boolean isQuote( HttpServletRequest request ){
		if( !Utils.isEmpty( request.getParameter( "appFlow" ) ) && ( request.getParameter( "appFlow" ).equalsIgnoreCase( Flow.RESOLVE_REFERAL.toString() ) ) ){
			String taskType = request.getParameter( "taskType" );
			if( !Utils.isEmpty( taskType ) ){
				if( taskType.equalsIgnoreCase( Utils.getSingleValueAppConfig( "TASK_TRAN_TYPE_ENDORSEMENT" ) ) ){
					return false;
				}
				else{
					return true;
				}
			}
			else{
				return false;
			}
		}
		else{

			String quoteFlag = Utils.isEmpty( request.getParameter( "PolQuoteFlow" ), true ) ? (String) request.getAttribute( "PolQuoteFlow" ) : request
					.getParameter( "PolQuoteFlow" );
			*//** Setting the flag to search for quote in case the flag is null *//*
			return ( !Utils.isEmpty( quoteFlag, true ) && "Policy".equals( quoteFlag ) ) ? false : true;
		}
	}*/

	private void saveReferralData(HttpServletRequest request, BaseVO baseVO, IRHHelper helper) {
		
		String assignTO = null;
		String referalLoc = null;
		String referalComments = null;
		PolicyDataVO policyDataVO = (PolicyDataVO)baseVO;
		//remove below if 
		//if( !Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ) ){

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
			referralVO.setRefDataTextField((Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ));
			request.getSession().removeAttribute( com.Constant.CONST_REFERRALMAP );
			referralVO.setLocationCode( PASServiceContext.getLocation() );
			refVOList.add( referralVO );
			referralListVO.setReferrals( refVOList );
			TaskVO taskVO = AppUtils.populateTaskVO( assignTO, referalLoc, referalComments, policyDataVO, request, policyDataVO.getCommonVO() );
			referralListVO.setTaskVO( taskVO );
			policyDataVO.setReferralVOList( referralListVO );
			UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
			policyDataVO.setLoggedInUser( userProfile );
			
			saveReferralAndTaskData(request,policyDataVO,helper);
			
	}
	
	private void saveReferralAndTaskData(HttpServletRequest request,
			PolicyDataVO policyDataVO, IRHHelper helper) {

		log.debug("BaseRH -----> Going to save referral related data in TTrnPasReferral");
		
		//Newly added to save task list
		if (!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getReferralVOList()) &&
				!Utils.isEmpty(policyDataVO.getReferralVOList().getReferrals())){
			log.debug("BaseRH -----> Going to call service to save referral data in TTrnPasReferral");
			/** Service call for persist the data in T_TRN_PAS_REFERRAL_DETAILS */
			if(policyDataVO.getCommonVO().getIsQuote())
				pasReferralSaveCmnSvc = (PasReferralSaveCommonSvc) Utils.getBean( "pasReferralCmnSvc" );
			else
				pasReferralSaveCmnSvc = (PasReferralSaveCommonSvc) Utils.getBean( "pasReferralCmnSvc_POL" );
			
			TTrnPasReferralDetails pasReferralDetails = (TTrnPasReferralDetails) pasReferralSaveCmnSvc.invokeMethod("saveReferralData", policyDataVO);
			//end if
		}
			//remove below if
		//	if (!Utils.isEmpty(pasReferralDetails)) {
		if (!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getReferralVOList()) 
				&& !Utils.isEmpty(policyDataVO.getReferralVOList().getTaskVO())){
				/* Start saving the TTrnTask table related data in case of CONSOLIDATED REFERRAL */
				log.debug("BaseRH -----> Going to make entry in TTrnTask");
				TaskVO taskVO = policyDataVO.getReferralVOList().getTaskVO();
				/** If this flag is set save consolidated referral */
				if ( (helper.isConsolidatedReferralScreen(request, policyDataVO)) && !Utils.isEmpty(taskVO.getAssignedTo())) {
											
					log.debug("BaseRH -----> Going to save TTrnTask table related data");
					if(policyDataVO.getCommonVO().getIsQuote())
						taskSvc = (TaskSvc) Utils.getBean( "taskService" );
					else
						taskSvc = (TaskSvc) Utils.getBean( "taskService_POL" );
					taskVO = (TaskVO)taskSvc.invokeMethod("populateReferralTaskDets", policyDataVO);
					taskSvc.invokeMethod("saveRefferalTask", taskVO);
					
					DataHolderVO<Object[]> dataHolderVO = new DataHolderVO<Object[]>();
					Object[] data = {policyDataVO,"REFERRAL_MAIL_TRIGGER"};
					dataHolderVO.setData( data );
					if(policyDataVO.getCommonVO().getIsQuote())
						commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
					else
						commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc_POL" );
					commonOpSvc.invokeMethod( "sendReferralMail", dataHolderVO );
				}
			}
	}
	
	/**
	 * This method will be always called in case of consolidated
	 * referrals
	 * @param policyDataVO
	 * @return
	 */
	/*private TaskVO populateReferralTaskDets(PolicyDataVO policyDataVO, TaskVO taskVO) {
		 To populate the messages corresponding to T_TRN_PAS_REFERRAL_DETAILS 
		
		List<TTrnPasReferralDetails> referralsList = DAOUtils.getReferralDetails( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
		if (!Utils.isEmpty(referralsList)) {
			StringBuilder messageBuilder = new StringBuilder();
			 Populating the message from T_TRN_PAS_REFERRAL_DETAILS 
			int counter = 0;
			for (TTrnPasReferralDetails pasReferralDetails : referralsList) {
				if (counter == 0) {
					messageBuilder.append(pasReferralDetails.getRefText());
				} else {
					messageBuilder.append("/n").append(pasReferralDetails.getRefText());
				}
			}
			taskVO.setPolicyType(String.valueOf(policyDataVO.getPolicyType()));
			taskVO.setPolLinkingId(policyDataVO.getPolicyId());
			taskVO.setDesc(messageBuilder.toString());
			if (!Utils.isEmpty(policyDataVO.getCommonVO().getEndtId())) {
				taskVO.setPolEndId(policyDataVO.getCommonVO().getEndtId());
			} else {
				taskVO.setPolEndId(new Long(0));
			}
			if(!Utils.isEmpty(policyDataVO.getCommonVO()) && policyDataVO.getCommonVO().getIsQuote()){
				taskVO.setTaskType(Utils.getSingleValueAppConfig("QUOTE_REFRRAL_TASK_TYPE"));
				taskVO.setQuote(true);
				taskVO.setQuoteNo(policyDataVO.getCommonVO().getQuoteNo());
				taskVO.setTaskName("Transaction "+policyDataVO.getCommonVO().getQuoteNo()+" is referred.");
			} else {
				taskVO.setTaskType(Utils.getSingleValueAppConfig("POLICY_REFRRAL_TASK_TYPE"));
				taskVO.setQuote(false);
				taskVO.setPolicyNo(policyDataVO.getCommonVO().getPolicyNo());
				taskVO.setTaskName("Transaction "+policyDataVO.getCommonVO().getPolicyNo()+" is referred.");
			}
			taskVO.setPriority(Utils.getSingleValueAppConfig("DEFAULT_TASK_PRIORITY"));
			taskVO.setClassCode(Byte.valueOf(Utils.getSingleValueAppConfig("TRAVEL_CLASS_CODE")));
			taskVO.setCategory(String.valueOf(policyDataVO.getPolicyType()));
			taskVO.setIsOpen(true);  Default value while assigning 
			taskVO.setLob(policyDataVO.getCommonVO().getLob().toString());
		}
		return taskVO;	
	}*/

	private void setCountry(HttpServletRequest request){
		
		BaseVO baseLookUpVO=null;
		LookUpVO lookUpVO=new LookUpVO();
		lookUpVO.setCategory("COUNTRY");
		lookUpVO.setLevel1("ALL");
		lookUpVO.setLevel2("ALL");
		baseLookUpVO= TaskExecutor.executeTasks("LOOKUP_INFO", lookUpVO);
		LookUpListVO lookUpList = new LookUpListVO();
		if(baseLookUpVO instanceof LookUpListVO){
			lookUpList = DropDownRendererHepler.getLookFilteredList((LookUpListVO) baseLookUpVO,request.getSession(false));
			
		}
		request.setAttribute(AppConstants.COUNTRY_LOOKUP_VAL, lookUpList.getLookUpList().get(0).getCode());
	}
	
	/**
	 * This method id used to load the general info details into the baseVO.
	 * @param request
	 * @param response
	 * @param commonVO
	 * @param helper
	 */
	private void loadGeneralInfoForQuote( HttpServletRequest request, HttpServletResponse response, CommonVO commonVO, IRHHelper helper ){
		BaseVO baseVO = helper.mapRequestToVO( request, response, commonVO );
		((PolicyDataVO)baseVO).setCommonVO(commonVO);
		baseVO = helper.loadData( request, response, baseVO );
		ThreadLevelContext.set( POLICYDATA, baseVO);
		helper.mapVOTORequest(request, response, baseVO);
	}
	
	/**
	 * This method is used to load general info and set policy context with required object.
	 * @param request
	 * @param response
	 * @param commonVO
	 * @param helper
	 */
	private void handleEditQuote(HttpServletRequest request, HttpServletResponse response, CommonVO commonVO, IRHHelper helper)
	{
		request.setAttribute(AppConstants.FUNTION_NAME,  Flow.EDIT_QUO.toString() );
		commonVO.setAppFlow(Flow.EDIT_QUO);
		loadGeneralInfoForQuote(request,response,commonVO,helper);		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		policyContext.setAppFlow( commonVO.getAppFlow() );
		policyContext.setCommonDetails(commonVO);
		
	}
	
	private void handleAmendPol(HttpServletRequest request, HttpServletResponse response, CommonVO commonVO, IRHHelper helper)
	{
		request.setAttribute(AppConstants.FUNTION_NAME,  Flow.AMEND_POL.toString() );
		commonVO.setAppFlow(Flow.AMEND_POL);
		commonVO.setIsQuote( false );
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_POLICYID ) )){
			commonVO.setPolicyId( Long.valueOf( request.getParameter( com.Constant.CONST_POLICYID ) ) );
		}
		if(!Utils.isEmpty( request.getParameter( "endtId" ) )){
			commonVO.setEndtId( Long.valueOf( request.getParameter( "endtId" ) ) );
		}
		if(!Utils.isEmpty( request.getParameter( "policyNo" ) )){
			commonVO.setPolicyNo( Long.valueOf(request.getParameter( "policyNo" )) );
		}
		if(!Utils.isEmpty( request.getParameter( "effDate" ) )){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy/MM/dd" );
			commonVO.setEndtEffectiveDate( converter.getAFromB(request.getParameter( "effDate" )) );
		}
		
		loadGeneralInfoForQuote(request,response,commonVO,helper);		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		policyContext.setAppFlow( commonVO.getAppFlow() );
		policyContext.setCommonDetails(commonVO);
	}
	
	private Response handleGenerateOnlineRenewals(HttpServletRequest request, HttpServletResponse response, Response responseObj){

		log.debug("**** Request for online renewal *****");
		
		String policyType = request.getParameter("lob");
		String clazz = request.getParameter( "clazz" );
		log.debug("lob in GenerateOnlineRenewals = " + policyType + ", Class code in GenerateOnlineRenewals = " + clazz);
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		TTrnRenewalBatchEplatform[] polForRenewal = gson.fromJson(request.getParameter("selectedRows"), TTrnRenewalBatchEplatform[].class);
		// Call Stored procedure to generate the renewal quote
		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		Object renInputData[] = new Object[5];
		renInputData[0] = polForRenewal[0].getPolicyId();
		renInputData[1] = ServiceContext.getUser().getUserId();
		renInputData[2] = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		renInputData[3] = policyType;
		renInputData[4] = clazz;
		input.setData(renInputData);
		
		@SuppressWarnings("unchecked")
		DataHolderVO<HashMap<String, Object>> holderVO = (DataHolderVO<HashMap<String, Object>>) TaskExecutor.executeTasks("GENERATE_RENEWALS_MONOLINE", input);
		HashMap<String, Object> renewalData = holderVO.getData();		
		Long renQuoteNo = (Long) renewalData.get( "renewalQuoteNo" );
		log.debug("***********renQuotationNo = "+renQuoteNo);		
		log.debug("***********renPolicyId = "+renewalData.get(com.Constant.CONST_POLICYID) );	
		response.setHeader("renewalQuoteNo", String.valueOf(renQuoteNo));
		log.debug("Renewal quote generated successfully");
	
		return responseObj;
	
	}
	
/*	private Response checkForRenewalMessages(HttpServletRequest request,PolicyDataVO policyDataVO){
		
		Response responseObj = new Response();
		RenewalVO renVo = policyDataVO.getRenewals();
		if (Utils.isEmpty(renVo)){
			renVo = new RenewalVO();				
			DataHolderVO<Object[]> claimInput = new DataHolderVO<Object[]>();
			Object [] claimInputData = new Object[2];
			claimInputData[0] = null; 
			claimInputData[1] = policyDataVO.getQuoteNo();
			claimInput.setData(claimInputData);
			DataHolderVO<Long> claimOutput = (DataHolderVO<Long>) TaskExecutor.executeTasks("GET_CLAIM_COUNT_COMMON", claimInput);
			renVo.setClaimCount(claimOutput.getData());

			DataHolderVO<Long> input = new DataHolderVO<Long>();
			input.setData(policyDataVO.getQuoteNo());
			DataHolderVO<BigDecimal> prmOutput = (DataHolderVO<BigDecimal>) TaskExecutor.executeTasks("GET_OS_PREMIUM", input);
			if (Utils.isEmpty(prmOutput.getData())) {
				renVo.setOsPremium(Double.valueOf(AppConstants.zeroVal));
			}
			else {
				renVo.setOsPremium(Double.valueOf(prmOutput.getData().toString()));
			}
			DataHolderVO<List<EndorsmentVO>> endorsementData = (DataHolderVO<List<EndorsmentVO>>) TaskExecutor.executeTasks("GET_ENDORSMENT_AFTER_REN", input);
			renVo.setEndorsmentList(endorsementData.getData());
			policyDataVO.setRenewals(renVo);
			//request.getSession().setAttribute("renewalsVO",renVo);
			
		}
		if (policyDataVO.getRenewals().getClaimCount() != 0 || policyDataVO.getRenewals().getOsPremium() != 0 || !Utils.isEmpty(policyDataVO.getRenewals().getEndorsmentList())) {
			DataHolderVO<Object[]> refInput = new DataHolderVO<Object[]>();
			request.getSession().setAttribute("renewalsVO",renVo);
			Object refInputData[] = new Object[3];				
			refInputData[ 0 ] = policyDataVO.getPolicyId();
			refInputData[ 1 ] = Short.parseShort(Utils.getSingleValueAppConfig(RulesConstants.REN));			
			refInputData[ 2 ] = Long.valueOf(Utils.getSingleValueAppConfig(RulesConstants.RISK_ID_RENEWAL));	
			refInput.setData(refInputData);
			DataHolderVO<Object[]> needReferral = (DataHolderVO<Object[]>) TaskExecutor.executeTasks("CHECK_REFERRAL_NEEDED_COMMON", refInput);
			Object refDetails[] = needReferral.getData();
			boolean claimChkNeeded = (Boolean) refDetails[ 0 ];
			boolean osPrmChkNeeded = (Boolean) refDetails[ 1 ];
			boolean endCheckNeeded = (Boolean) refDetails[ 2 ];

			if (claimChkNeeded || osPrmChkNeeded || endCheckNeeded) {
				if (!claimChkNeeded) {
					renVo.setClaimCount(Long.valueOf(AppConstants.zeroVal));
				}
				if (!osPrmChkNeeded){
					renVo.setOsPremium(Double.valueOf(AppConstants.zeroVal));
				}
				if (!endCheckNeeded){
					renVo.setEndorsmentList(null);
				}

				if (policyDataVO.getRenewals().getClaimCount() != 0 || policyDataVO.getRenewals().getOsPremium() != 0 || !Utils.isEmpty(policyDataVO.getRenewals().getEndorsmentList())) {

					Redirection redirection = new Redirection("/jsp/reports/showRenewalMessages.jsp", Type.TO_JSP);

					request.setAttribute("renewalsVO", policyDataVO.getRenewals());
					request.setAttribute("renendorsments", policyDataVO.getRenewals().getEndorsmentList());
					
					responseObj.setRedirection(redirection);						
					
				}

			}
		}
		return responseObj;
	
	}*/
	
	private Response handleGenerateBatchRenewals(HttpServletRequest request, HttpServletResponse response, Response responseObj) {

		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		TTrnRenewalBatchEplatform[] polForRenewal = gson.fromJson( request.getParameter("selectedRows"), TTrnRenewalBatchEplatform[].class);
		DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
		Object[] input = { polForRenewal };
		inputData.setData(input);
		TaskExecutor.executeTasks("GENERATE_RENEWALS_PAS", inputData);
		return responseObj;
	}
	
	/**
	 * @return the pasReferralSaveCmnSvc
	 */
	public PasReferralSaveCommonSvc getPasReferralSaveCmnSvc() {
		return pasReferralSaveCmnSvc;
	}

	/**
	 * @param pasReferralSaveCmnSvc the pasReferralSaveCmnSvc to set
	 */
	public void setPasReferralSaveCmnSvc(
			PasReferralSaveCommonSvc pasReferralSaveCmnSvc) {
		this.pasReferralSaveCmnSvc = pasReferralSaveCmnSvc;
	}

	public TaskSvc getTaskSvc() {
		return taskSvc;
	}

	public void setTaskSvc(TaskSvc taskSvc) {
		this.taskSvc = taskSvc;
	}

	/**
	 * @return the commonOpSvc
	 */
	public CommonOpSvc getCommonOpSvc(){
		return commonOpSvc;
	}

	/**
	 * @param commonOpSvc the commonOpSvc to set
	 */
	public void setCommonOpSvc( CommonOpSvc commonOpSvc ){
		this.commonOpSvc = commonOpSvc;
	}

}
