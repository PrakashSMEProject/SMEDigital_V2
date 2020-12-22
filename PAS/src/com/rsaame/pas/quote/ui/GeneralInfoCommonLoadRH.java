package com.rsaame.pas.quote.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.converter.IntegerShortConverter;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.lookup.ui.DropDownRendererHepler;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class GeneralInfoCommonLoadRH implements IRequestHandler{

	private final static Logger LOGGER = Logger.getLogger( GeneralInfoCommonLoadRH.class );
	/**
	 *  Request Handler for Navigating to General Home / Travel Info Page
	 */

	private final static Redirection GENERALINFOCOMMONPAGE = new Redirection( "/jsp/quote/GeneralInfoCommonContent.jsp", Type.TO_JSP );

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * 
	 * This method executed when home/travel general info page need to be loaded
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		LOGGER.info( "Entering to GenInfoCommonLoadRH" );
		String lob = (String) request.getParameter( "LOB" );
		String appFlow = (String) request.getParameter( com.Constant.CONST_APPFLOW );

		// Check if new customer flag
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_ISNEWCUST ) )){
			request.setAttribute(com.Constant.CONST_ISNEWCUST, (String)request.getParameter( com.Constant.CONST_ISNEWCUST ) );
		}

		// Non UI requests will set lob in request attribute. Ex: Copy quote flow
		if(Utils.isEmpty( lob )){
			lob = (String) request.getAttribute( "LOB" );
		}
		// Non UI requests will set appFlow in request attribute. Ex: Copy quote flow
		if(Utils.isEmpty( appFlow )){
			appFlow = (String) request.getAttribute( com.Constant.CONST_APPFLOW );
		}

		String location = PASServiceContext.getLocation();
		LOGGER.info( "Entering to GenInfoCommonLoadRH----------location" +location);
		
		LOGGER.info( "Entering to GenInfoCommonLoadRH----------appFlow" +appFlow);
		// check for creating policy context has been done here. for example, from VIEW_QUO to EDIT_QUO, policy context need not to be created always.  
		boolean isNavigationFlow = Utils.toDefaultFalseBoolean( request.getParameter( AppConstants.REQ_PARAM_FOR_NAV ) );


		PolicyDataVO value = (PolicyDataVO) request.getAttribute( "policyDetails" );
		
		request.setAttribute("value",value); 
		
		request.setAttribute( "isAddToQuote", (Boolean)request.getAttribute( "isAddToQuote" ) ); 
		
		/**
		 * Set country to default value based on logged in location.
		 */
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
		
		//LOGGER.debug("*******Country*******"+lookUpList.getLookUpList().get(0).getCode());
		request.setAttribute(AppConstants.COUNTRY_LOOKUP_VAL, lookUpList.getLookUpList().get(0).getCode());


		//String insured=request.getParameter( arg0 )

		String tranType = request.getParameter( "tranType" );

		/* Get the appFlow if it is not present in the request paramter and decide the app flow based on document code */
		if( !Utils.isEmpty( appFlow ) && appFlow.equalsIgnoreCase( "TRANS_SEARCH" ) && !Utils.isEmpty( tranType ) ){
			Integer docCode = SvcUtils.getLookUpCode( "DOC_TYPE_ALL", "ALL", "ALL", tranType );
			
			if( docCode == AppConstants.NEW_QUOTATION || docCode == AppConstants.NEW_RENEWAL_QUOTATION ){
				appFlow = identifyTransFlow( request );
			}
		}

		if(isNavigationFlow){
			PolicyContextUtil.getPolicyContext( request ).getCommonDetails().setAppFlow( Flow.valueOf( appFlow ) );
		}else{
			/* Create policy context */
			if( !Utils.isEmpty( appFlow )  && appFlow.equals( Flow.CREATE_QUO.toString() )){
				/* App Flow and LOB will be set while creating Policy Context. Hence we need not set them in particular. */
				PolicyContextUtil.createPolicyContext( request, appFlow, LOB.valueOf( lob ) );
			}
		}
		

		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		
		
		/*if(!isNavigationFlow){
			commonVO.setIsQuote( Boolean.TRUE );
			commonVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
			commonVO.setEndtId( Long.valueOf( SvcConstants.zeroVal ) );
			commonVO.setEndtNo( Long.valueOf( SvcConstants.zeroVal ) );
		}*/
		
		request.setAttribute(AppConstants.FUNTION_NAME,  policyContext.getAppFlow().toString() );
		/* Create the common vo if the flow is view quote */
		if( !Utils.isEmpty( policyContext.getAppFlow() ) ){
			switch( policyContext.getAppFlow() ){
				case VIEW_QUO:
					commonVO.setLocCode( Integer.valueOf( location ) );
					commonVO.setIsQuote( true );
					break;
				case CREATE_QUO:
					commonVO.setIsQuote( Boolean.TRUE );
					commonVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
					commonVO.setEndtId( Long.valueOf( SvcConstants.zeroVal ) );
					commonVO.setEndtNo( Long.valueOf( SvcConstants.zeroVal ) );
					commonVO.setLocCode( Integer.valueOf( location ) );
					commonVO.setIsQuote( true );
					break;
				case AMEND_POL:
					commonVO.setLocCode( Integer.valueOf( location ) );
					commonVO.setIsQuote( false );
					if(!Utils.isEmpty( request.getParameter( "policyId" ) )){
						commonVO.setPolicyId( Long.valueOf( request.getParameter( "policyId" ) ) );
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
					break;
				case EDIT_QUO:
					break;
				case VIEW_POL:
					break;
				default:
					break;
			}
		}
		
		if(!Utils.isEmpty( request.getAttribute( "copyQuoteResult" ) )){
			commonVO = (CommonVO) request.getAttribute("copyQuoteResult");
		}
		
		/*if(!Utils.isEmpty( request.getAttribute( "copyQuoteData" ) )){
			CopyQuoteHelperVO copyQuoteData = (CopyQuoteHelperVO) request.getAttribute( "copyQuoteData" );
		}
		
		if(!Utils.isEmpty( request.getParameter( "reloadAfterSave" ) )){
			Gson gson = new Gson();
			CopyQuoteHelperVO copyQuoteData = gson.fromJson(request.getParameter("copyQuoteData"), CopyQuoteHelperVO.class);
			request.setAttribute( "copyQuoteData", copyQuoteData );
		}*/
		
		/* Set the values required to control screen elements display (authorization). */
		setAuthdetails( request );
		
		/* User profile will always be available , in case of navigation flow it is always assumed to be present and 
		 * will result into exception scenario if not present */
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		
		LOGGER.debug("User  ID "+userProfile.getRsaUser().getUserId());

		/* Set the profile type and broker id to request since for broker login scheme drop down needs to populated 
		 * on load of GeneralInfoContent rather than the basis of distribution channel and broker code selection */
		AppUtils.setUserProfileDetsToRequest( request, userProfile );
		
		/* Populate loggedInUser in commonVO which can be used in baseSaveOperation.*/
		commonVO.setLoggedInUser( userProfile );
		commonVO.setCreatedBy( userProfile.getRsaUser().getUserId().toString() );
		
		policyContext.setCommonDetails( commonVO );

		request.setAttribute( "currentLob", lob );
		request.setAttribute( AppConstants.USER_ID, userProfile.getRsaUser().getUserId().toString() );
		
		LOGGER.debug(" ****User  ID ********"+userProfile.getRsaUser().getUserId().toString());
		
		
		Response response = new Response();
		/*IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
		if( LOB.HOME.equals( LOB.valueOf( lob ) ) ){
			if(!Utils.isEmpty( Currency.getPolicyTypeScaleMap() ) && !Utils.isEmpty( Currency.getPolicyTypeScaleMap().get(converter.getBFromA( AppConstants.HOME_POLICY_TYPE )))){
				Currency.setScale( Currency.getPolicyTypeScaleMap().get(converter.getBFromA( AppConstants.HOME_POLICY_TYPE )) );
			}
		}
		else if(LOB.TRAVEL.equals( LOB.valueOf( lob ) ) && !Utils.isEmpty( value )){
			if(!Utils.isEmpty( Currency.getPolicyTypeScaleMap() ) && !Utils.isEmpty( Currency.getPolicyTypeScaleMap().get(converter.getBFromA( value.getPolicyType() )) )){
				Currency.setScale( Currency.getPolicyTypeScaleMap().get(converter.getBFromA( value.getPolicyType() )) );
			}
		}*/
		
		//142244
		if( LOB.HOME.equals( LOB.valueOf( lob ) ) ){
		if(policyContext.getAppFlow().equals(Flow.CREATE_QUO) || policyContext.getAppFlow().equals(Flow.RESOLVE_REFERAL)){
			List<Object> vatData=  DAOUtils.VatCodeAndVatRate(Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_CLASS_CODE" ) ),Integer.valueOf( SvcConstants.HOME_POL_TYPE),SvcConstants.SC_PRM_COVER_VAT_TAX);
			if( !Utils.isEmpty( vatData ) ){
				
				if( !Utils.isEmpty( vatData.get( 1 ) ) ){		
					request.setAttribute(com.Constant.CONST_VATCODEONGI, vatData.get( 1 ));
					LOGGER.debug( "**On Load()**  vat Code" + vatData.get( 1 ));
				
				}
				}
			}
		}
		
		
		if( isValidCommonVO( commonVO ) ){
			if( LOB.HOME.equals( LOB.valueOf( lob ) ) ){
				/* Set CommonVO to HomeInsuranceVO for home LOB.*/
				HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
				homeInsuranceVO.setCommonVO( commonVO );
				homeInsuranceVO = (HomeInsuranceVO) TaskExecutor.executeTasks( "GEN_INFO_COMMON_LOAD", homeInsuranceVO );

				if( !Utils.isEmpty( homeInsuranceVO ) && !homeInsuranceVO.getCommonVO().getIsQuote() && !Utils.isEmpty( homeInsuranceVO.getPolicyNo() ) ){
					request.setAttribute( AppConstants.TRANSACTION_NO, "Policy number : " + homeInsuranceVO.getPolicyNo() );
				}
				else if( !Utils.isEmpty( homeInsuranceVO ) && homeInsuranceVO.getCommonVO().getIsQuote() && !Utils.isEmpty( homeInsuranceVO.getQuoteNo() ) ){
					request.setAttribute( AppConstants.TRANSACTION_NO, "Quotation number : " + homeInsuranceVO.getQuoteNo() );
					//
				}
				if( Flow.AMEND_POL.equals( commonVO.getAppFlow() ) && Utils.isEmpty( homeInsuranceVO.getPolExpiryDate() )){
					homeInsuranceVO.setPolExpiryDate( homeInsuranceVO.getScheme().getExpiryDate() );
				}
				if(!Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getVatCode())){
					request.setAttribute("quote_type_code",homeInsuranceVO.getVatCode());
					request.setAttribute(com.Constant.CONST_VATCODEONGI, homeInsuranceVO.getVatCode());
					
				}
					
						
				
				
				request.setAttribute( AppConstants.PAGE_VALUE, homeInsuranceVO );
				setDefaultAndCommonValues(request,homeInsuranceVO,commonVO);

			}
			else if(LOB.TRAVEL.equals( LOB.valueOf( lob ) )){
				
				/* Set CommonVO to TravelInsuranceVO for home LOB.*/
				TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
				travelInsuranceVO.setCommonVO( commonVO );
				travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "GEN_INFO_COMMON_LOAD", travelInsuranceVO );
				if( Flow.AMEND_POL.equals( commonVO.getAppFlow() ) && Utils.isEmpty( travelInsuranceVO.getPolExpiryDate() )){
					travelInsuranceVO.setPolExpiryDate( travelInsuranceVO.getScheme().getExpiryDate() );
				}
				request.setAttribute( AppConstants.PAGE_VALUE, travelInsuranceVO );
				if( !Utils.isEmpty(travelInsuranceVO)) {
					request.setAttribute(com.Constant.CONST_VATCODEONGI, travelInsuranceVO.getVatCode());
				}

				if( !Utils.isEmpty( travelInsuranceVO ) && !travelInsuranceVO.getCommonVO().getIsQuote() && !Utils.isEmpty( travelInsuranceVO.getPolicyNo() ) ){
					request.setAttribute( AppConstants.TRANSACTION_NO, "Policy number : " + travelInsuranceVO.getPolicyNo() );
				}
				else if( !Utils.isEmpty( travelInsuranceVO ) && travelInsuranceVO.getCommonVO().getIsQuote() && !Utils.isEmpty( travelInsuranceVO.getQuoteNo() ) ){
					request.setAttribute( AppConstants.TRANSACTION_NO, "Quotation number : " + travelInsuranceVO.getQuoteNo() );
				}
				
			/* Setting Underwriting questions to response header */
				UWQuestionsVO uwQuestionsVO = travelInsuranceVO.getUwQuestions();
				if( !Utils.isEmpty( uwQuestionsVO ) && !Utils.isEmpty( uwQuestionsVO.getQuestions() ) ){
					String uwResponseSequence = SvcUtils.getUWResponseSequence( uwQuestionsVO );
					LOGGER.info( "UW Response Sequence " + uwResponseSequence + " generated" );
					responseObj.setHeader( "underWriterQuestions", uwResponseSequence );
				}
			}

		}
		
		// VATCode Impl - 142244
		if(!isValidCommonVO(commonVO)) {
			if(policyContext.getAppFlow().equals(Flow.CREATE_QUO)) {
				Integer policyClassCode = null;
				Integer homePolicyTypeCode = null,  travelShortPolicyTypeCode = null, travelLongPolicyTypeCode= null;
				List<Object> vatData = Collections.emptyList();
				 if(LOB.TRAVEL.equals(LOB.valueOf(lob))) {
					policyClassCode = Integer.valueOf( Utils.getSingleValueAppConfig("TRAVEL_CLASS_CODE"));
					travelShortPolicyTypeCode = Integer.valueOf( Utils.getSingleValueAppConfig("TRAVEL_SHORT_TERM_POLICY_TYPE"));
					travelLongPolicyTypeCode = Integer.valueOf( Utils.getSingleValueAppConfig("TRAVEL_LONG_TERM_POLICY_TYPE"));					
					vatData=  DAOUtils.VatCodeAndVatRateForTravel(policyClassCode, travelShortPolicyTypeCode, travelLongPolicyTypeCode, 
																							SvcConstants.SC_PRM_COVER_VAT_TAX);
				}
				
				Integer vatCode = (Integer)request.getAttribute("vatCodeToNewCust");
				if(!Utils.isEmpty(vatCode)) {
					request.setAttribute(com.Constant.CONST_VATCODEONGI, vatCode);
					LOGGER.debug( "**On Load() : Vat Code getting from CopyQuoteCommomRh.java **  vat Code:: " + vatCode);
				} else if(!Utils.isEmpty(vatData)) {					
					if(!Utils.isEmpty( vatData.get(1))) {		
						request.setAttribute(com.Constant.CONST_VATCODEONGI, vatData.get(1));
						LOGGER.debug( "**On Load()**  vat Code" + vatData.get(1));					
					}
				}
			}
		}
		setDefaultIntAccExeToReq(request);
		setDefaultEmirateToReq(request);
		/*
		 * Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change
		 * To check for vatEnabled for Home/Travel
		 */
		DAOUtils.checkVatEnabled(request);
		response.setSuccess( true );
		
		if( !Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getDocCode() ) && commonVO.getIsQuote() && commonVO.getDocCode() == 6 ){
			AppUtils.setExpiryDateForRenewal( commonVO, request );
		}
		response.setRedirection( GENERALINFOCOMMONPAGE );

		return response;
	}

	/***
	 * On initial load of GeneralInfo,setting the default value of emirate field to the logged-in location
	 * @param request
	 */
	private void setDefaultEmirateToReq( HttpServletRequest request ){
		CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
		String loggenInLoc = Utils.getSingleValueAppConfig(com.rsaame.pas.util.AppConstants.DEPLOYED_LOCATION);
		
		
		LOGGER.debug("*****in setDefaultEmirateToReq*****loggenInLoc***********"+loggenInLoc);
		
		String emirateDesc = SvcUtils.getLookUpDescription( "BRANCH", SvcUtils.getUserId( commonVO ).toString(), "ALL", Integer.parseInt( loggenInLoc ));
	//	LOGGER.debug("**********emirateDesc***********"+emirateDesc);
		
		Integer emirate=  SvcUtils.getLookUpCode("CITY", "ALL", "ALL", emirateDesc);
		
	//	LOGGER.debug("**********emirate***********"+emirate);
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


	/**
	 * @param request
	 * This function is used to set the values to the UI fields
	 * @param policyDataVO 
	 * @param commonVO 
	 */
	private void setDefaultAndCommonValues(HttpServletRequest request, PolicyDataVO policyDataVO, CommonVO commonVO){
		
		if(!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getPolicyNo()) && !(Flow.EDIT_QUO.equals(policyDataVO.getCommonVO().getAppFlow())) 
				&& !(Flow.VIEW_QUO.equals(policyDataVO.getCommonVO().getAppFlow()))){
			
			List<Object[]> policyDataList = DAOUtils.getSqlResultForPas( QueryConstants.SQL_FETCH_LATEST_ACTIVE_POLICY, policyDataVO.getPolicyNo(),policyDataVO.getPolicyId());
			
			if(!Utils.isEmpty(policyDataList)){
				Object[] latestActivePolicyObject = policyDataList.get( 0 );
			
				SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
				try{
					request.setAttribute( "ACTIVE_EXPIRY_DATE", format.parse( latestActivePolicyObject[0].toString() ) );
				}
				catch( ParseException e ){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		
		
		
		LOGGER.info( "Exiting setDefaultAndCommonValues Method" );
	
		
	}


	/**
	 * @param commonVO
	 * @return
	 * 
	 *  This method checks for quote number if it is a quote, else policy number if it is policy. if data is not present, it is invalid
	 */
	private boolean isValidCommonVO( CommonVO commonVO ){
		
		if(Utils.isEmpty( commonVO )){
			return false;
		}
		else{
			if(commonVO.getIsQuote() && Utils.isEmpty( commonVO.getQuoteNo() )){
				return false;
			}
			else if(!commonVO.getIsQuote() && Utils.isEmpty( commonVO.getPolicyNo() )){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Method to identify the appFlow
	 * @param request
	 * @return
	 */
	private String identifyTransFlow( HttpServletRequest request ){
		Boolean quoteFlag = isQuote( request );
		return ( quoteFlag ) ? "VIEW_QUO" : "VIEW_POL";
	}
	
	private Boolean isQuote( HttpServletRequest request ){
		if( !Utils.isEmpty( request.getParameter( com.Constant.CONST_APPFLOW ) ) && ( request.getParameter( com.Constant.CONST_APPFLOW ).equalsIgnoreCase( Flow.RESOLVE_REFERAL.toString() ) ) ){
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

			String quoteFlag = Utils.isEmpty( request.getParameter( com.Constant.CONST_POLQUOTEFLOW ), true ) ? (String) request.getAttribute( com.Constant.CONST_POLQUOTEFLOW ) : request
					.getParameter( com.Constant.CONST_POLQUOTEFLOW );
			/** Setting the flag to search for quote in case the flag is null */
			return ( !Utils.isEmpty( quoteFlag, true ) && "Policy".equals( quoteFlag ) ) ? false : true;
		}
	}
	
	/*
	 * Auth details for general info page is set here
	 */
	private void setAuthdetails( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );
	}
	/**
	 * Sets default internal accounting executive value to request from logged in user
	 * @param request
	 */
	private void setDefaultIntAccExeToReq(HttpServletRequest request) {
		PolicyDataVO polVo = (PolicyDataVO) request.getAttribute(AppConstants.PAGE_VALUE);
		CommonVO commonVO = PolicyContextUtil.getPolicyContext( request ).getCommonDetails();
		
		/*
		 * For quotes, internal executive is the user who issues the quote.
		 * For renewal quotes, internal executive is the user who first issues the quote.
		 */
		if(!Utils.isEmpty( commonVO.getDocCode() )&& commonVO.getDocCode() == Integer.parseInt( Utils.getSingleValueAppConfig( "NEW_RENEWAL_QUOTATION" ) )){
			if(commonVO.getEndtNo() == 0 && (Flow.EDIT_QUO.equals( commonVO.getAppFlow()))){
				//request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, SvcUtils.getUserId( commonVO) );
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, Integer.parseInt( Utils.getSingleValueAppConfig( "DEF_ACC_EXE_RENEWEL" )) );
				 
			}else{
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, polVo.getGeneralInfo().getIntAccExecCode() );
			}
		}/*else if(Flow.EDIT_QUO.equals( commonVO.getAppFlow() ) || Flow.CREATE_QUO.equals( commonVO.getAppFlow() )){
			if(!Utils.isEmpty( request.getParameter( "clickAction" ) )){
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, polVo.getGeneralInfo().getIntAccExecCode() );
			}else{
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, SvcUtils.getUserId( commonVO) );
			}
		} else if(!Utils.isEmpty(polVo.getGeneralInfo())){*/
		else if (Flow.CREATE_QUO.equals( commonVO.getAppFlow() )){
			request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, SvcUtils.getUserId( commonVO) );
		}
		else if( !Utils.isEmpty(polVo.getGeneralInfo()) && !Utils.isEmpty(polVo.getGeneralInfo().getIntAccExecCode())) { // case of reload
			request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, polVo.getGeneralInfo().getIntAccExecCode() );
		}else{
			request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, SvcUtils.getUserId( commonVO) );// If any case where generalInfo could be null
		}
	//	}
		
		/*Integer exeCode=  SvcUtils.getLookUpCode(AppConstants.INT_ACC_EXE_CATEGORY, AppConstants.INT_ACC_EXE_DEFAULT, AppConstants.INT_ACC_EXE_DEFAULT, AppConstants.INT_ACC_EXE_DEFAULT_DESC);
		if(Utils.isEmpty(polVo)){ // Case of initial load
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, SvcUtils.getUserId( commonVO) );
		} else if (!Utils.isEmpty(polVo.getGeneralInfo())){
			if( !Utils.isEmpty(polVo.getGeneralInfo().getIntAccExecCode())) { // case of reload
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, polVo.getGeneralInfo().getIntAccExecCode() );
			}	
		else {
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, commonVO.getLoggedInUser().getUserId() );// If any case where generalInfo could be null
			}
		}	*/

	}

}
