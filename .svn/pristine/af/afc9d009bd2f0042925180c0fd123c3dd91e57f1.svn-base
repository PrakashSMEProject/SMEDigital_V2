package com.rsaame.pas.ui.cmn;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.MVCUtils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.constants.Constants;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * This request handler is a generic handler for all actions on a risk section screen including
 * screen load. The class takes the help of the request handlers written for the specific sections
 * to display the screens with their data.<br/><br/>
 * 
 * Most of the opType configuration is based on section Ids.
 */
public class SectionRH implements IRequestHandler{
	
	private final static Logger logger = Logger.getLogger(SectionRH.class);
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		Response responseObj = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		
		if( Utils.isEmpty( policyContext ) ){
			throw new SystemException( "pas.cmn.policyContextUnavailable", null, "PolicyContext has not been initialised" );
		}

		/* Get the action from the request. If it has not been set, default to "LOAD_SCREEN". */
		String actionAttr = request.getParameter( AppConstants.ACTION );
		actionAttr = Utils.isEmpty( actionAttr ) ? "LOAD_SCREEN" : actionAttr;
		Action action = Action.valueOf( actionAttr );
		
		
		/* THIS IS AN IMPORTANT STEP: PLEASE READ.
		 * 
		 * If this navigation was reached through a referral save action (REFERRAL_SAVE), then simply load the screen in the case
		 * of SAVE. That is because if the user had clicked on SAVE on the section and referrals were shown, then the save service
		 * for the section would have been called by REFERRAL_SAVE flow itself. 
		 * 
		 * However, in the case of referrals being shown and saved during NEXT or PREVIOUS, the handler methods for these actions
		 * take care of skipping the call to the SAVE request handler in this flow. */
		String referralAction = (String) request.getAttribute( AppConstants.REQ_ATTR_REFERRAL_ACTION );
		if( !Utils.isEmpty( referralAction ) && referralAction.equalsIgnoreCase( AppConstants.REQ_ATTR_REFERRAL_ACTION ) ){
			action = action.equals( Action.SAVE ) ? Action.LOAD_SCREEN : action;
		}
		
		/* Set the current action to the request for use in the called request handlers.
		 * 
		 * IMPORTANT: This attribute must not be reset anywhere. In SectionRH, we invoke one handleXXX() method from another when required.
		 * The purpose of this attribute is to indicate the original action requested. */
		request.setAttribute( AppConstants.REQ_ATTR_CURR_ACTION, action.name() );
		
		switch( action ){
			case NEXT:
				responseObj = handleNextPreviousAction( request, response, policyContext, Boolean.TRUE );
				break;
			case SAVE:
				responseObj = handleSaveAction( request, response, policyContext );
				break;
			case PREVIOUS:
				responseObj = handleNextPreviousAction( request, response, policyContext, Boolean.FALSE );
				break;
			case ADD_LOCATION:
				handleAddLocationAction( request, response, policyContext );
				break;
			case CALCULATE_PREMIUM:
				responseObj = handleCalculatePremiumAction( request, response, policyContext );
				break;
			case DELETE_LOCATION:
				responseObj = handleDeleteLocationAction( request, response, policyContext );
				break;
			case LOAD_SCREEN:
				responseObj = handleLoadScreenAction( request, response, policyContext );
				break;
			case LOAD_DATA:
				responseObj = handleLoadDataAction( request, response, policyContext );
				break;
			case FETCH_PP_DATA:
				responseObj = handleFetchPPDataAction( request, response, policyContext );
				break;
			case ADD_SECTION:
				responseObj =handleAddSectionAction( request, response, policyContext );
				break;
		    case DELETE_SECTION:	
		    	responseObj = handleDeleteSectionAction( request, response, policyContext );
		    	break;
			default:
				break;
		}

		ThreadLevelContext.clear( "RISK_GROUP_ID" );

		return responseObj;
	}

	private Response handleDeleteSectionAction(HttpServletRequest request,
			HttpServletResponse response, PolicyContext policyContext) {
		
		executeRHForAction( request, response, policyContext, Action.DELETE_SECTION );
		return null;
	}

	private Response handleAddSectionAction(HttpServletRequest request,
			HttpServletResponse response, PolicyContext policyContext) {
		
		java.util.Map results=null;
		PASStoredProcedure sp=null;
		final Integer ZERO = 0;
		SectionVO sectionVO = new SectionVO(RiskGroupingLevel.LOCATION);
		Integer sectionId = retrieveSectionId( request, response, policyContext );
		sectionVO.setSectionId(sectionId);
		sectionVO.setSectionName(SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", sectionId ) );
		if(policyContext.getAllSelectedSections().length!=ZERO){
			java.util.List<RiskGroup> riskGroups = (java.util.List<RiskGroup> ) policyContext.getRiskGroups(policyContext.getBasicSectionId());
			java.util.LinkedHashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = new LinkedHashMap<RiskGroup, RiskGroupDetails>();
			if(!Utils.isEmpty(riskGroups)){
				for(RiskGroup riskGroup :riskGroups){
					/*Copying location from basic section before putting it in to risk group details of added section  */
					LocationVO locationVO=CopyUtils.copySerializableObject( (LocationVO)riskGroup );
					locationVO.setActiveStatus( null );
					riskGroupDetails.put(locationVO,null);
				}
			}
			sectionVO.setRiskGroupDetails(riskGroupDetails);
		}
		executeRHForAction( request, response, policyContext, Action.ADD_SECTION );
		policyContext.addSection(sectionVO); 
		return null;
	}

	private Response handleFetchPPDataAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		/* Call the request handler to fetch the pre-population data in a SectionVO instance for the section. */
		return executeRHForAction( request, response, policyContext, Action.FETCH_PP_DATA );
	}

	private Response handleLoadDataAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		/* Logic for section data load:
		 * The PolicyContext would have been created when General Info was loaded. Hence, we need to focus on the loading of the
		 * section data alone. The steps are:
		 * (a) Retrieve the sectionId to be loaded.
		 * (b) If this is the first section in the policy, create new SectionVO instances for all sections and add to the PolicyVO
		 * here.
		 * (c) Call the request handler to get the populated SectionVO for the section with all the saved location data. Since the data
		 * is expected to be set directly into the PolicyContext, there is no need to handle the Response instance from the request
		 * handler.
		 * (d) Call LOAD_SCREEN action. */
		
		/* (a) Retrieve the sectionId to be loaded. */
		if(policyContext.getAllSelectedSections().length==0){
			throw new BusinessException( "pas.cmn.sectionsNotAdded", null, "No risk sections have been added for this policy" );
		}
		Integer sectionId = retrieveSectionId( request, response, policyContext );
		
		/*Start of ticket 137704 */
		Integer curSectionId = null;		
		if(!Utils.isEmpty(request.getParameter( "currentSectionId" ))){
		    curSectionId = Integer.valueOf( request.getParameter( "currentSectionId" ) );
		}
		request.setAttribute("termpCurrentSecId", curSectionId);
		/*End of ticket 137704 */
		

		policyContext.startTransaction();
		
		policyContext.setAsCurrentSection( sectionId );
		
		/* (b) Create SectionVOs for all selected sections and add to the PolicyVO. */
		createSectionsInThePolicy( policyContext );
		
		if(!Utils.isEmpty( policyContext ) && policyContext.isPolicyCancelled()){
			policyContext.setAppFlow( Flow.VIEW_POL );
			policyContext.getPolicyDetails().setAppFlow( Flow.VIEW_POL );			
		}
				
		/* (c) Call the request handler to get the populated SectionVO for the section with all the saved location data. */
		executeRHForAction( request, response, policyContext, Action.LOAD_DATA );
		
		/* (d) Call LOAD_SCREEN action. */
		Response resp = handleLoadScreenAction( request, response, policyContext );
		
		
		Integer currentSectionId = policyContext.getCurrentSectionId();
		if(!currentSectionId.equals( AppConstants.SECTION_ID_PREMIUM )){
			request.getSession().removeAttribute( "premiumSectionId" );
		}
		
		policyContext.commit();
		
		return resp;
	}

	private Integer retrieveSectionId( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		Integer sectionId = null;
		String currAction = (String) request.getAttribute( AppConstants.REQ_ATTR_CURR_ACTION );
		
		/* The preference order for the place from where sectionId to be loaded will be picked up is:
		 * (a) The request parameter "jumpToSectionId" 
		 * (b) The request attribute "CURR_ACTION"
		 * (c) The current sectionId in the PolicyContext */
		String jumpToSectionId = request.getParameter( AppConstants.REQ_PARAM_JUMP_TO_SECTION_ID );
		if( !Utils.isEmpty( jumpToSectionId ) && Utils.isNumber( jumpToSectionId ) ){
			sectionId = Integer.valueOf( jumpToSectionId );
		}
		else if( currAction.equals( Action.NEXT.name() ) ){
			sectionId = policyContext.getNextScreen().getSectionId();
		}
		else if( currAction.equals( Action.PREVIOUS.name() ) ){
			sectionId = policyContext.getPrevScreen().getSectionId();
		}
		else{
			sectionId = policyContext.getCurrentSectionId();
		}
		
		return sectionId;
	}

	/**
	 * Creates SectionVO instances for all selected sections.
	 * @param pc
	 */
	private void createSectionsInThePolicy( PolicyContext pc ){
		if( !pc.isSectionVOsCreationDone() ){
			Integer[] sectionIds = pc.getAllSelectedSections();
			
			for( Integer sectionId : sectionIds ){
				SectionVO section = new SectionVO( AppUtils.getRiskGroupLevel(sectionId) ); 
				section.setSectionId( sectionId );
				section.setSectionName( SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", sectionId ) );
				
				pc.addSection( section );
			}
			
			/* Set the flag to indicate that SectionVO creation for all sections is complete. */
			pc.setSectionVOsCreationDone( true );
		}
	}

	private Response handleLoadScreenAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		/* Logic for section load:
		 * (a) Figure out the section that is to be loaded from PolicyContext.
		 * (b) Based on the section Id returned by PolicyContext, determine the opType for the
		 *     section Id and LOAD action combination.
		 * (c) Execute the request handler configured for the opType.
		 * (d) Get the Redirection instance from the response or the configuration for the opType
		 *     to know the JSP that has to be included in the section screen.
		 * (e) From the Redirection instance, pick up the URL and set to the request attribute "sectionContent". This
		 * 	   will be used in the section component JSP as an "included" JSP.
		 * (f) Create the Redirection URL as the section component's container screen JSP's URL (the one
		 *     inside which the section's JSP will be included).
		 */

		/* (a) Get the current section Id */
		Integer currentSectionId = policyContext.getCurrentSectionId();

		/* (b, c) */
		Response resp = executeRHForAction( request, response, policyContext, Action.LOAD_SCREEN );

		/*(d)*/
		if( !Utils.isEmpty( resp ) ){
			Redirection sectionRedirection = getRedirection( resp, getOpType( Action.LOAD_SCREEN.name(), currentSectionId.toString() ), request );
			/*(e)*/

			/*(f)*/
			Redirection redirection = null;
			if( currentSectionId == AppConstants.SECTION_ID_PREMIUM ){
				/* TODO This is premium specific logic. This should not be present in SectionRH. */
				/*request.setAttribute( "code", "101" );

				Response respTemp = executeRequestHandler( "LOOKUP_INFO", request, response );

				double govtTaxPerc = 0;
				if( !Utils.isEmpty( respTemp ) ){
					govtTaxPerc = Integer.parseInt( ( (LookUpVO) respTemp.getData() ).getDescription() );
				}

				if( Utils.isEmpty( policyContext.getPolicyDetails().getPremiumVO() ) ){
					PremiumVO premium = new PremiumVO();
					premium.setGovtTax( govtTaxPerc );
					policyContext.getPolicyDetails().setPremiumVO( premium );
				}*/

				redirection = sectionRedirection;
			}
			else if( useSectionRedirection( request, policyContext ) ){
				redirection = sectionRedirection;
			}
			else{
				request.setAttribute( "sectionContent", sectionRedirection.getUrl() );
				redirection = new Redirection( "jsp/loc_content.jsp", Type.TO_JSP );
			}

			if( !Utils.isEmpty( redirection ) ){
				resp.setRedirection( redirection );
			}
		}
		
		return resp;
	}

	private Response handleDeleteLocationAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		/* Logic for location delete:
		 * (a) Figure out the section from PolicyContext whose location has to be deleted.
		 * (b) If the number of locations in the section is only one, do not allow the deletion.
		 * (c) Based on the section Id returned by PolicyContext, determine the opType for the
		 *     section Id and DELETE action combination.
		 * (d) Execute the request handler configured for the opType.
		 * (e) Get the first risk group details for the first risk group in the map for the section and send back
		 *     as JSON.
		 */

		/*(a)*/
		Integer currentSectionId = policyContext.getCurrentSectionId();
		
		/*(b)*/
		java.util.List<? extends RiskGroup> riskGroups = policyContext.getRiskGroups( currentSectionId );
		if( Utils.isEmpty( riskGroups ) || riskGroups.size() == 1 ){
			throw new BusinessException( "pas.cmn.cannotDeleteLastLocation", null, "Cannot delete the last location. There should be atleast one location in the section." );
		}
		
		/*(c, d)*/
		Response resp = executeRHForAction( request, response, policyContext, Action.DELETE_LOCATION );
		
		/* (e) is being handled in DelLocationRH. */
		
		return resp;
	}

	private Response handleCalculatePremiumAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		/* Logic for section save, if save button is clicked from section screen:
		 * (a) Based on the section Id returned by PolicyContext, determine the opType for the
		 *     section Id and RATING action combination.
		 */
		/*(a)*/
		Response resp = executeRHForAction( request, response, policyContext, Action.CALCULATE_PREMIUM );

		return resp;
	}
	
	
	private void handleAddLocationAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param policyContext
	 */
	private Response handleNextPreviousAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext, boolean next ){
		/* Logic for NEXT:
		 * (a) Get the opType for the section and action combination. 
		 * (b) Execute the request handler for the configured opType, if it is not a referral save flow.
		 * (c) On successful execution, call PolicyContext.changeState().
		 * (d) Figure out the next section Id from PolicyContext.
		 * (e) Redirect to LOAD_SCREEN opType with next section Id. (Ignore the Redirection instance 
		 * 	   inside the Response object from the request handler and the configured redirection for the
		 *     opType.) 
		 *     */
	
		Integer currentSectionId = policyContext.getCurrentSectionId();
		Response resp = null;
		
		/* (a) Get the opType for the section and action combination.
		 * (b) Execute the request handler for the configured opType, if it is not a referral save flow. */
		
		Boolean isQuote=true;
		if(!Utils.isEmpty( policyContext.getPolicyDetails() )){
			isQuote=policyContext.getPolicyDetails().getIsQuote();
			if(Utils.isEmpty( isQuote )){
				isQuote=true;
				policyContext.getPolicyDetails().setIsQuote( isQuote );
			}
		}
		logger.debug( " Is Quote ? - "+isQuote );
		
		/**Below condition to check whether to execute the request handler will require a  re-look as in future any other request handler 
		 * can be configured for NEXT . Today this condition will work as in case of NEXT we know that only
		 * SAVE operation happens for previous section.
		 * */ 
		
		/* (a) Get the opType for the section and action combination.
		 * (b) Execute the request handler for the configured opType, if it is not a referral save flow. */
		
		//TODO: APP FLOW condition to be introduced to check whether Request Handler configured  for NEXT Action to be executed or not
		/* Below check is void as SaveSectionRH is already handling readOnlyMode based on AppFlow hence removing the below 
		 * check */
		//if(policyContext.getPolicyDetails().getIsQuote() ){
			
		if (Utils.isEmpty(request
				.getAttribute(AppConstants.REQ_ATTR_REFERRAL_ACTION)) && next) {
			resp = executeRHForAction(request, response, policyContext,
					Action.NEXT);
		}
		//}
		/*  Added for Referral Component -
		 *  a. To check if the response obtained from SAVE Request handler execution is not null
		 *  b. To check if the response obtained has contentType set as JSON which is a possibility in 
		 *  case of rules failure (referrals)
		 */ 
		if( Utils.isEmpty( resp ) ) resp = new Response();

		boolean needsRedirection = MVCUtils.respTypeNeedsRedirection( resp );
		if( needsRedirection && !( next && currentSectionId.equals( AppConstants.SECTION_ID_PREMIUM ) ) ){
			resp = handleLoadDataAction( request, response, policyContext );
		}
		
		if(!currentSectionId.equals( AppConstants.SECTION_ID_PREMIUM )){
			request.getSession().removeAttribute( "premiumSectionId" );
		}
		
		return resp;
	}

	private Response handleSaveAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext ){
		/* Logic for section save, if save button is clicked from section screen:
		 * (a) Figure out the section that is to be saved from PolicyContext.
		 * (b) Based on the section Id returned by PolicyContext, determine the opType for the
		 *     section Id and SAVE action combination.
		 * (c) Execute the request handler configured for the opType.
		 * (d) Get the response from request handler and set the Redirection if not set already.
		 * 
		 */

		/*(a) Get the current section Id. */
		Integer currentSectionId = policyContext.getCurrentSectionId();
		
		/** SK : Changes *
		 * Temporary Fix : To check if the current sectionId is of Premium Page i.e. 999
		 * then proceed with save operation of previous section.
		 * This scenario will be happening when user clicks gets consolidated referral window
		 * on Last Section and opts to click "No". After click of No user proceeds with click
		 * of "SAVE" on last section.
		 * The above mentioned flow will be encountered only when there is no referral triggered
		 * on last section as referral cases are handled through ReferralRH 
		 */
		
		if( currentSectionId.equals( AppConstants.SECTION_ID_PREMIUM ) ) policyContext.setAsCurrentSection( policyContext.getPrevScreen().getSectionId() ); 
		
		/*(b, c) Figure out the opType configured for this action-section combination and execute the RH.*/
		Response resp = executeRHForAction( request, response, policyContext, Action.SAVE );
		
		/*(d) Set the redirection. */
		addRedirection( request, getOpType( Action.SAVE.name(), policyContext.getCurrentSectionId().toString() ), resp );
		
		return resp;
	}

	/**
	 * Checks if there is a redirection set in the passed Response instance. Else, creates one from the appconfig
	 * configuration for the passed opType.
	 * @param request 
	 * @param opType 
	 * @param resp
	 */
	private void addRedirection( HttpServletRequest request, String opType, Response resp ){
		if( Utils.isEmpty( resp.getRedirection() ) && MVCUtils.respTypeNeedsRedirection( resp ) ){
			resp.setRedirection( MVCUtils.getConfiguredRedirection( MVCUtils.getAppSpecificOpType( opType ), request ) );
		}
	}

	/**
	 * This method is to form the opType on the basis of action and section
	 * 
	 * @param actionName
	 * @param sectionId
	 * @return
	 */
	private String getOpType( String actionName, String sectionId ){
		String opTypeKey = Utils.concat( "OPTYPE_", actionName, "_", sectionId );
		String opType = Utils.getSingleValueAppConfig( opTypeKey );
		return opType;
	}

	/**
	 * This method is to get the redirection for opType and set it in response
	 * 
	 * @param response
	 * @param opType
	 * @return
	 */
	private Redirection getRedirection( Response response, String opType, HttpServletRequest request ){
		Redirection redirection = response.getRedirection();
		if( Utils.isEmpty( redirection ) ){
			redirection = MVCUtils.getConfiguredRedirection( MVCUtils.getAppSpecificOpType( opType ), request );
		}

		return redirection;
	}

	/**
	 * This is the method where various conditions to decide if the section's redirection should be used is decided.
	 * 
	 * @param request
	 * @param policyContext
	 * @return
	 */
	private boolean useSectionRedirection( HttpServletRequest request, PolicyContext policyContext ){
		Boolean reloadLocCase = (Boolean) request.getAttribute( AppConstants.REQ_RELOAD_LOC_CASE );
		
		if( policyContext.getCurrentSectionId() == 999 || 
			( !Utils.isEmpty( reloadLocCase ) && reloadLocCase ) )
		{
			return true;
		}
			
		return false;
	}

	/**
	 * Executes the request handler configured for the action-section section combination.
	 * 
	 * @param request
	 * @param response
	 * @param policyContext
	 * @param action
	 * @return
	 */
	private Response executeRHForAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext, Action action ){
		Integer currentSectionId = policyContext.getCurrentSectionId();
		
		/*This is required so that section can be added or deleted from general info page in case of edit quote flow*/
		if(Utils.isEmpty(currentSectionId))
			if((action.name().equalsIgnoreCase(Action.DELETE_SECTION.name())) || (action.name().equalsIgnoreCase(Action.ADD_SECTION.name())))
					currentSectionId=AppConstants.SECTION_ID_GEN_INFO;
		
		Response resp = null;
		if( !Utils.isEmpty( currentSectionId ) ){
			/* Get the opType configured for this action for the section. */
			String opType = getOpType( action.name(), currentSectionId.toString() );
			
			/* Set the opType to the HTTP request and execute the request handler. */
			if( !Utils.isEmpty( opType ) ){
				request.setAttribute( AppConstants.OPERATIONTYPE, opType );
				request.setAttribute( AppConstants.ACTIONNAME, action.name());
				resp = executeRequestHandler( opType, request, response );
			}
		}
	
		return resp;
	}
	

	/**
	 * This method is used to execute Request Handler for a given opType
	 * 
	 * @param opType
	 * @param request
	 * @param response
	 * @return
	 */
	private Response executeRequestHandler( String opType, HttpServletRequest request, HttpServletResponse response ){
		/*
		 * (a) Based on the passed UIOperationType, get the class name for the IRequestHandler implementation
		 *     from app config.
		 * (b) Load the class and execute the execute() method.
		 * (c) Return the object returned by the call directly. 
		 */
		String rhBeanName = Utils.getSingleValueAppConfig( Constants.APP_CONFIG_REQ_HANDLER_PREFIX + opType );
		IRequestHandler rh = null;
		if( !Utils.isEmpty( rhBeanName ) ){
			rh = (IRequestHandler) Utils.getBean( rhBeanName );
		}

		return Utils.isEmpty( rh ) ? null : rh.execute( request, response );
	}

}
