
package com.rsaame.pas.quote.ui;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.access.handler.GetPrivilegeForUserUtil;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.converter.IntegerShortConverter;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.lookup.ui.DropDownRendererHepler;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.RuleContext;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TaskVO;

public class GeneralInfoRH implements IRequestHandler{
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		
		/* Check if we need to recreate the PolicyContext and reload the policy details, or just use whatever has been loaded. In
		 * the case of left navigation and other navigational movement, a reload may not be necessary. In that case, such flow requests
		 * should be pass this request parameter. */
		boolean isNavigationFlow = Utils.toDefaultFalseBoolean( request.getParameter( AppConstants.REQ_PARAM_FOR_NAV ) );

		PolicyVO policyDetails = null;

		/* User profile will always be available , in case of navigation flow it is always assumed to be present and 
		 * will result into exception scenario if not present */
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );

		/*
		 * Clear Section PPP data from session
		 */
		AppUtils.clearSectionPPPDataFromSession( request );

		/* Check if com.Constant.CONST_APPFLOW has been sent in the request. This value will be required to create the PolicyContext or, in the case
		 * of a simple navigation, to set it to the PolicyContext. */

		String appFlow = Utils.isEmpty( request.getParameter( com.Constant.CONST_APPFLOW ), true ) ? (String) request.getAttribute( com.Constant.CONST_APPFLOW ) : request.getParameter( com.Constant.CONST_APPFLOW );
		if( !Utils.isEmpty( appFlow ) ){
			if( "TRANS_SEARCH".equalsIgnoreCase( appFlow ) ) appFlow = identifyTransFlow( request );
			if( "INS_SEARCH".equalsIgnoreCase( appFlow ) ) appFlow = identifyInsSearchFlow( request );
		}
		
		String policyStatus = request.getParameter( "polStatus" );

		if( isNavigationFlow ){
				/* The following values are passed as request attributes for authorisation purposes. */
				if( !Utils.isEmpty( appFlow ) ){
					PolicyContextUtil.getPolicyContext( request ).setAppFlow( Flow.valueOf( appFlow ) );
					//if(Flow.valueOf( appFlow ).equals( Flow.RESOLVE_REFERAL )) setTaskInContext( request );
				}
				
				if( PolicyContextUtil.getPolicyContext( request ).isPolicyCancelled() ){
					PolicyContextUtil.getPolicyContext( request ).setAppFlow( Flow.VIEW_POL );
					PolicyContextUtil.getPolicyContext( request ).getPolicyDetails().setAppFlow( Flow.VIEW_POL );
				}
				
				/* If the riskDetails are already saved, only then set the BasicSectionId as currentSectionId. */
				if( !Utils.isEmpty( request ) && !Utils.isEmpty( PolicyContextUtil.getPolicyContext( request ) ) && 
						!Utils.isEmpty( PolicyContextUtil.getPolicyContext( request ).getPolicyDetails() ) && 
						!Utils.isEmpty( PolicyContextUtil.getPolicyContext( request ).getPolicyDetails().getRiskDetails() ) && 
						PolicyContextUtil.getPolicyContext( request ).getPolicyDetails().getRiskDetails().size() != 0 ){
					
					Integer currSection = PolicyContextUtil.getPolicyContext( request ).getBasicSectionId();
					
					if( !Utils.isEmpty( currSection ) ){
						PolicyContextUtil.getPolicyContext( request ).setAsCurrentSection( currSection.intValue() );
					}
				}
		}
		else{
			/* Create the PolicyContext here. In any flow, this is valid. Since we are going to get into a new flow for a policy/quote,
			 * new or existing, it has to be represented by the PolicyContext instance. Hence, we have to create one here for the current
			 * one. */
			if( !Utils.isEmpty( appFlow ) ){
				PolicyContextUtil.createPolicyContext( request, appFlow );
				//if(Flow.valueOf( appFlow ).equals( Flow.RESOLVE_REFERAL )) setTaskInContext( request );
			}
			else{
				throw new BusinessException( "pas.auth.appFlowNotAvailable", null, "\"appFlow\" was not submitted in the request" );
			}

			PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );

			/* setting isQuote to true as default */
			policyContext.getPolicyDetails().setIsQuote( true );
			/*
			 * For viewing the cancelled policy status is required in DAO's to fetch the records with status 4.
			 */
			if(!Utils.isEmpty( policyStatus )){
				policyContext.getPolicyDetails().setStatus( policyStatus.equalsIgnoreCase( "Cancelled" ) ? 4 : null );
			}
			
			/* Populate the values of the existin policy, if this is an existing quote. At this stage, ie, General Info load, we will
			 * populate only general policy information like selected sections list and saved General Information data. */
			populateExistingPolicyValues( request, responseObj, policyContext );

			if( Utils.isEmpty( userProfile ) ){
				userProfile = new UserProfile(); /* TODO Check this!! There should be no need to create UserProfile. */
			}

			/* Set logged in user details in the PolicyVO instance. */
			policyDetails = policyContext.getPolicyDetails();
			policyDetails.setLoggedInUser( userProfile );
		}

		/* Set the values required to control screen elements display (authorization). */
		setAuthdetails( request );

		/* Set the profile type and broker id to request since for broker login scheme drop down needs to populated 
		 * on load of GeneralInfoContent rather than the basis of distribution channel and broker code selection */
		AppUtils.setUserProfileDetsToRequest( request, userProfile );
		
		/* Sets default internal accounting executive code to request attribute */
		setDefaultIntAccExeToReq( request );
		
		/* Fetch Default internal accounting code from lookup and  set the same to request */

		policyDetails = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails(); /* In case this flow didn't go into the above IF block. */
		//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
		if(!Utils.isEmpty(policyDetails.getRenewalBasis())){
			request.setAttribute(AppConstants.REQ_ATTR_POL_RENEWAL_BASIS_VAL, policyDetails.getRenewalBasis());
		}
		

		//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
		if( !Utils.isEmpty( appFlow ) && "AMEND_POL".equalsIgnoreCase( appFlow ) ){

			if( !Utils.isEmpty( request.getParameter( "effDate" ) ) ){

				com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
						"format=yyyy/MM/dd" );
				policyDetails.setEndEffectiveDate( converter.getTypeOfA().cast( converter.getAFromB( request.getParameter( "effDate" ) ) ) ) ;
			}

		}

		if( !Utils.isEmpty( policyDetails.getIsQuote() ) ) request.setAttribute( "isQuote", policyDetails.getIsQuote().toString() );
		request.setAttribute( AppConstants.USER_ID, userProfile.getRsaUser().getUserId().toString() );
		
		String branchCode = PASServiceContext.getLocation();
		if( !Utils.isEmpty( branchCode ) ){
			request.setAttribute("LOCATION", branchCode );
		}
		
		/**
		 * Set country to default value based on logged in location.
		 */
		getDefaultValues(request);
		/**
		 * Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change
		 * To check for vatEnabled and vatApplicable for SBS
		 */
		DAOUtils.checkVatEnabled(request);
		
		/** Setting action identifier to trace the flow of copy quote to enable the Copy Quote button on View Insured screen */
		if( !Utils.isEmpty( request.getSession().getAttribute( "copyFlow" ) )
				&& "COPY_TO_EXISTING_INSURED".equalsIgnoreCase( (String) request.getSession().getAttribute( "copyFlow" ) ) )
			responseObj.setHeader( "actionIdentifier", "COPY_TO_EXISTING_INSURED" );
	
		
		/*
		 * VAT Code Population
		 * 
		 */
		if(policyDetails.getAppFlow().equals(Flow.CREATE_QUO)   && policyDetails.getPolVATCode()==0 && Utils.isEmpty(request.getAttribute( "oldPolLinkingId"))){
		Integer vatCode = DAOUtils.checkVATDefaultCode();
		policyDetails.setPolVATCode(vatCode);
		}
		else if( policyDetails.getAppFlow().equals(Flow.CREATE_QUO)   &&  !Utils.isEmpty(request.getAttribute( "operation"))  &&  request.getAttribute( "operation").toString().equalsIgnoreCase("COPY_TO_NEW_INSURED")){
		policyDetails.setPolVATCode(Integer.parseInt(request.getAttribute("VATCodeForCopyNewCustomer").toString()));
		}
		return null;
	}
	

	private void populateExistingPolicyValues( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		/* Check if this is an existing quote or policy. If the policy linking Id has been passed in the request, we can consider it
		 * as a case of loading an existint quote or policy. If the parameter has not been passed, return from here.*/
		String policyLinkingId = Utils.isEmpty( request.getParameter( com.Constant.CONST_POLLINKINGID ), true ) ? (String) request.getAttribute( com.Constant.CONST_POLLINKINGID ) : request
				.getParameter( com.Constant.CONST_POLLINKINGID );

		if( Utils.isEmpty( policyLinkingId ) ){
			
		
			return;
		}

		/* Get endorsement Id from the request. Default to "0". */
		String endId = Utils.isEmpty( request.getParameter( com.Constant.CONST_ENDTID ), true ) ? (String) request.getAttribute( com.Constant.CONST_ENDTID ) : request.getParameter( com.Constant.CONST_ENDTID );
		endId = Utils.isEmpty( endId ) ? "0" : endId;

		Boolean isQuote = isQuote( request );

		/* Now, for the quote, we need to fetch the sections that were selected. */
		LoadExistingInputVO input = new LoadExistingInputVO();
		input.setPolLinkingId( Long.valueOf( policyLinkingId ) );
		input.setEndtId( Long.valueOf( endId ) );
		input.setQuote( isQuote );
		input.setAppFlow( policyContext.getPolicyDetails().getAppFlow() );
		input.setPolicyStatus(  policyContext.getPolicyDetails().getStatus() );

		Boolean isEndosement = isEndorsement( request );
		if( isEndosement ){
			input.setAppFlow( Flow.AMEND_POL );
			input.setQuote( false );
		}
		/* Fetch General Info Details */
		input.setSectionId( AppConstants.SECTION_ID_GEN_INFO );

		PolicyVO policyDetails = (PolicyVO) TaskExecutor.executeTasks( AppConstants.GENERAL_INFO_FETCH, input );

		policyDetails.setAppFlow( policyContext.getPolicyDetails().getAppFlow() );

		policyDetails.setIsQuote( isQuote );

		if( !policyContext.getPolicyDetails().getAppFlow().equals( Flow.CREATE_QUO ) )
			policyDetails = (PolicyVO) TaskExecutor.executeTasks( AppConstants.SET_PRE_PACKAGE_FLAG, policyDetails );

		policyDetails.setPolLinkingId( new Long( policyLinkingId ) );
		policyDetails.setEndtId( new Long( endId ) );

		policyContext.setPolicyDetails( policyDetails );
		
		// Set the policy status correctly now if it is set as null before
		input.setPolicyStatus(  policyContext.getPolicyDetails().getStatus() );
		SectionList output = (SectionList) TaskExecutor.executeTasks( AppConstants.FETCH_SELECTED_SECTIONS, input );

		/* Set the selected sections to the policy context. */
		if( !Utils.isEmpty( output ) && !Utils.isEmpty( output.getSelectedSec() ) ){
			Integer sectionIds[] = CopyUtils.toArray( output.getSelectedSec() );
			Arrays.sort( sectionIds );
			/* Added by Anveshan to fix SIT P-2 177*/
			AppUtils.sortSections( sectionIds );
			policyContext.populateSelectedSec( sectionIds );
		}

		/**
		 * START :Changes made to create sectionVOS for selected risk sections. This has been done so that SectionVOs are available as soon as 
		 * general info is loaded in cases where quote/policy is revisited.
		 */
		/* SectionVOs for all selected sections and add to the PolicyVO. */
		createSectionsInThePolicy( policyContext );
		
		/**
		 * END :Changes made to create sectionVOS for selected risk sections. This has been done so that SectionVOs are available as soon as 
		 * general info is loaded in cases where quote/policy is revisited.
		 * 
		 * 
		 */
		
		
	
	
		/* This is an existing quote. Hence, set the newQuote flag to false. */
		policyContext.setNewQuote( false );
	}

	private Boolean isQuote( HttpServletRequest request ){
		if( !Utils.isEmpty( request.getParameter( com.Constant.CONST_APPFLOW ) ) && ( request.getParameter( com.Constant.CONST_APPFLOW ).equalsIgnoreCase( Flow.RESOLVE_REFERAL.toString() ) ) ){
			String taskType = request.getParameter( "taskType" );
			if( !Utils.isEmpty( taskType ) ){
				if( taskType.equalsIgnoreCase( Utils.getSingleValueAppConfig( "TASK_TRAN_TYPE_ENDORSEMENT" ) ) )
					return false;
				else
					return true;
			}
			else
				return false;
		}
		else{

			String quoteFlag = Utils.isEmpty( request.getParameter( com.Constant.CONST_POLQUOTEFLOW ), true ) ? (String) request.getAttribute( com.Constant.CONST_POLQUOTEFLOW ) : request
					.getParameter( com.Constant.CONST_POLQUOTEFLOW );
			/** Setting the flag to search for quote in case the flag is null */
			return ( !Utils.isEmpty( quoteFlag, true ) && "Policy".equals( quoteFlag ) ) ? false : true;
		}
	}

	private String identifyTransFlow( HttpServletRequest request ){
		Boolean quoteFlag = isQuote( request );
		return ( quoteFlag ) ? "VIEW_QUO" : "VIEW_POL";
	}
	
	private String identifyInsSearchFlow( HttpServletRequest request ){
		GetPrivilegeForUserUtil getPrivilege = (GetPrivilegeForUserUtil) Utils.getBean( "getPrivilege" );
		//Oman UAT Defect no : 483 - Insured name should be editable after 'add to quote'
		VisibilityLevel level = getPrivilege.getPrivilegeForUser( Flow.CREATE_QUO.toString(), "General_Info", "F_INSURED_NAME", request );
		if(level == VisibilityLevel.READONLY)
			request.setAttribute( "insSearch", "insSearch" );
		return "CREATE_QUO";
	}
	/*
	 * Auth details for general info page is set here
	 */
	private void setAuthdetails( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );
	}

	/*
	 * In Referral flow the task details is set into policy context
	 * This will be used to check the visibility based on the task creator and assignor
	 
	private void setTaskInContext( HttpServletRequest request ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		TaskVO taskDetails = (TaskVO) ThreadLevelContext.get( "TASKDETAILS" );
		if( !Utils.isEmpty( taskDetails ) ){
			policyContext.setTaskDetails( taskDetails );
		}
		else{
			throw new BusinessException( "pas.auth.taskDetilsNotAvailable", null, "\"taskDetails\" is null in resoving referral flow" );
		}

	}*/

	/**
	 * This method is used to set Logged In user profile as a request attribute in order to use the
	 * same on GeneralInfoContent Screen
	 * @param request
	 * @param userProfile
	 *//*
	private void setUserProfileDetsToRequest( HttpServletRequest request, UserProfile userProfile ){
		request.setAttribute( AppConstants.BROKERCODE, userProfile.getRsaUser().getBrokerId() );
		request.setAttribute( AppConstants.PROFILE, userProfile.getRsaUser().getProfile() );
	}*/

	/**
	 * Creates SectionVO instances for all selected sections.
	 * @param pc
	 */
	private void createSectionsInThePolicy( PolicyContext pc ){
		if( !pc.isSectionVOsCreationDone() ){
			Integer[] sectionIds = pc.getAllSelectedSections();

			for( Integer sectionId : sectionIds ){
				SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION ); /* TODO This may not be correct because there can be a 
																					 * RiskGroupLevel other than LOCATION. */
				section.setSectionId( sectionId );
				section.setSectionName( SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", sectionId ) );

				pc.addSection( section );
			}

			/* Set the flag to indicate that SectionVO creation for all sections is complete. */
			pc.setSectionVOsCreationDone( true );
		}
	}

	private Boolean isEndorsement( HttpServletRequest request ){
		String quoteFlag = Utils.isEmpty( request.getParameter( com.Constant.CONST_POLQUOTEFLOW ), true ) ? (String) request.getAttribute( com.Constant.CONST_POLQUOTEFLOW ) : request.getParameter( com.Constant.CONST_POLQUOTEFLOW );
		/** Setting the flag to search for quote in case the flag is null */
		return ( !Utils.isEmpty( quoteFlag, true ) && "AMEND_POL".equals( quoteFlag ) ) ? true : false;
	}
	/**
	 * Sets default internal accounting executive value to request from lookup
	 * @param request
	 */
	private void setDefaultIntAccExeToReq(HttpServletRequest request){
		
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( AppConstants.INT_ACC_EXE_CATEGORY, AppConstants.INT_ACC_EXE_DEFAULT, AppConstants.INT_ACC_EXE_DEFAULT );
		if(!Utils.isEmpty( listVO )){
			if(!Utils.isEmpty( listVO.getLookUpList() ) ){
				LookUpVO lookUpVO = listVO.getLookUpList().get( 0 );
				request.setAttribute( AppConstants.REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL, lookUpVO.getCode() );
			}
		}
	}
	
	protected void getDefaultValues(HttpServletRequest request) {
		/**
		 * Set country to default value based on logged in location.
		 */
		BaseVO baseVO=null;
		LookUpVO lookUpVO=new LookUpVO();
		lookUpVO.setCategory("COUNTRY");
		lookUpVO.setLevel1("ALL");
		lookUpVO.setLevel2("ALL");
		baseVO= TaskExecutor.executeTasks("LOOKUP_INFO", lookUpVO);
		LookUpListVO lookUpList = new LookUpListVO();
		if(baseVO instanceof LookUpListVO){
			lookUpList = DropDownRendererHepler.getLookFilteredList((LookUpListVO) baseVO,request.getSession(false));
			
		}
		
		request.setAttribute(AppConstants.COUNTRY_LOOKUP_VAL, lookUpList.getLookUpList().get(0).getCode());
		
	}
}
