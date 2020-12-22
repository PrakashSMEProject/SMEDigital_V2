package com.rsaame.pas.ui.cmn;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCVO;

/**
 * 
 * This abstract class is base class which is extended by 
 * Load Request Handlers of each section.
 *
 */
public abstract class LoadSectionRH implements IRequestHandler {
	private static final String UNSAVED_LOCATION_ID_PREFIX = "Location";
	private static final Integer BI_SECTION_ID = 2;
	private static final Integer TB_SECTION_ID = 10;
	private static final Integer GROUP_PERSONAL_ACCIDENT_SECTION_ID = 12;
	private static final Integer WC_SECTION_ID = 7;
	private static final Integer FIDELITY_SECTION_ID = 9;
	
	/*
	 * (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * This method has the core logic for LOAD_SCREEN action when invoked through SectionRH.
	 * Following scenarios are handled -
	 * a. First time loading of the any section through redirection to corresponding sections content jsp
	 * b. Loading of corresponding sections json jsp in case of add location functionality
	 * c. Seggregating the list of saved and unsaved locations and setting the same to request attribute's
	 * 	  "savedLocations" and "unsavedLocations" correspondingly.
	 * d. Populating "currRG", "currRGD" and "currSectionVO" request attributes which are used within json jsp's to populate the data
	 * e. Merging of LocationVO with Prepackaged data in case of create quote and precedence is given to prepack data
	 *    while merging of prepack locationVO with current risk group for the section.
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response responseObj = new Response();

		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		if(policyVO != null){		/*Added if condition for null check of policyVO - sonar violation fix */
		policyVO.setPolicyTypeCode(Integer.valueOf(  Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ) );
		}
		/* Set request values for authorisation. */
		setAuthRequestValues( request, policyContext);
		
		/* Set tariff code and scheme code to request in order to use them in the JSP to get filtered occupancy codes
		 * via LOOKUP */
		setReqAttrForOcpFilter( request, policyContext );
		if( policyVO != null && policyVO.getPolicyNo()!= null && !policyVO.getIsQuote()){
			request.setAttribute( com.Constant.CONST_TRANSACTIONNO, "Policy No : "+policyVO.getConcatPolicyNo() );
		}else{
			if(policyVO != null && policyVO.getQuoteNo()!= null){
				if( policyVO.getPolRefPolicyNo() != null && !policyVO.getPolRefPolicyNo().toString().equals("0")) {
						request.setAttribute( com.Constant.CONST_TRANSACTIONNO, "Renewal Policy No.: "+ policyVO.getPolRefPolicyNo()+" : "+"Quotation : "+policyVO.getQuoteNo() );
				}else{
						request.setAttribute( com.Constant.CONST_TRANSACTIONNO,"Quotation : "+policyVO.getQuoteNo() );
				}
			}
			
		}
		
		boolean isCaseOfReload = false;
		
		dataPopulation: {
			/* Check if it is a case of loading an already saved location data. If yes, return the data for that location. */
			
			/* If the form is not being submitted from the same section, then it is a case of clicking "Next" on the previous
			 * section. There is a hidden FORM field named "sectionId" in every section form. */
			String sectionId = (String) request.getParameter( "sectionId" );
			String riskGroupId = getCurrentRiskGroupId( request, policyContext );
			LocationVO locationVO = null;
			RiskGroupDetails rgd = null;
			
			if(policyContext.getCurrentSectionId() == 7 )
			{
				request.getSession().setAttribute("mappedwcVOSession", new WCVO() );
			}
			/*
			 * Below values are required irrespective of the checks added below
			 * a. If the section and location is added for the first time
			 * b. If the section and location is reloaded again
			 */
			
			locationVO = (LocationVO) policyContext.getRiskGroup( policyContext.getCurrentSectionId(), riskGroupId );
			rgd = (RiskGroupDetails)policyContext.getRiskGroupDetails( policyContext.getCurrentSectionId(), locationVO );
			
			/* Check if it is case of direct walkin where in commision field need not be displayed to the user */
			setCommisionApplFlagToReq( request, policyContext ); 
			
			/* Check if additional covers are configured for the tariff code selected by the user in General Info Screen */
			setAddtlCoverApplFlagToReq( request, policyContext );
			
			/* Set the default commission which is added to commission map */
			AppUtils.setDefaultCommission( request, response, getSectionClassCode(policyContext.getCurrentSectionId()) );
			
			/*
			 * a. RiskGroup details for the current risk group is empty then Location is considered to be not saved.
			 * This logic is mostly useful in sections where the location info is copied from PAR or if PAR is not
			 * present then from PL.
			 * b. If the risk group details haven't been saved for this risk group yet, let us return the pre-population values too.
			 */
			if( !policyContext.isRiskGroupDetailsSaved( policyContext.getCurrentSectionId(), riskGroupId ) ){
				/* Prepare LocationVO instance first. */
				SectionVO sectionVO = (SectionVO) request.getSession( false ).getAttribute( AppConstants.SECTION_PPP_DATA );
				if( !Utils.isEmpty( sectionVO ) ){
					java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
					for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
						locationVO = (LocationVO) locationEntry.getKey();
						/*
						 * LocationVO.copy() is used to create a new object out of locationVO reference obtained from the 
						 * risk group details map in order to avoid data retention across newly added locations.
						 */
						locationVO = locationVO.copy();
						/*
						 * LocationVO  which is populated with prepackaged values is given the precedance in order to 
						 * retain prepack values in case of copied locations. Data will be updated only in case prepack
						 * value is not available for a particular field
						 */
						locationVO.merge( (LocationVO) policyContext.getRiskGroup( policyContext.getCurrentSectionId(), riskGroupId ) );
						/* Now prepare RiskGroupDetails instance. */
						rgd = locationEntry.getValue();
					}
				}
			}
		
			/*
			 * This block assumes that the section and location combination is already saved
			 */
			if( !isANewLocation( riskGroupId ) && 
					( !Utils.isEmpty( sectionId ) && Integer.parseInt( sectionId ) == policyContext.getCurrentSectionId() ) )
			{
				/*
				 * In case of risk group details is already saved then obtain the Current RiskGroup and Current RiskGroupDetails
				 * from PolicyContext 
				 */
				if( Utils.isEmpty( locationVO ) ) locationVO = (LocationVO) policyContext.getRiskGroup( policyContext.getCurrentSectionId(), riskGroupId );
				if( Utils.isEmpty(rgd)) rgd = policyContext.getRiskGroupDetails( policyContext.getCurrentSectionId(), locationVO );
				
				isCaseOfReload = true;
				request.setAttribute( AppConstants.REQ_RELOAD_LOC_CASE, isCaseOfReload );
			}
		
			if( !isCaseOfReload ){
				/* Form the Maps for saved and unsaved locations. */
				SectionRHUtils.createSavedAndUnsavedLocList( request, policyContext );
			}
			//Radar fix
			/*double policyPremium=0;
			if(!Utils.isEmpty(policyContext.getPolicyDetails())){
				if(!Utils.isEmpty(policyContext.getPolicyDetails().getPremiumVO())){
					if(!Utils.isEmpty(policyContext.getPolicyDetails().getPremiumVO().getPremiumAmt()))
					{
						policyPremium=policyContext.getPolicyDetails().getPremiumVO().getPremiumAmt();

					}
				}
			}*/
			
			AppUtils.setSectionPageDataForJSON( request, 
					policyContext.getCurrentSection(), 
					locationVO,
					rgd, policyContext.getPolicyDetails());
			if(!Utils.isEmpty(locationVO)){
				if(!Utils.isEmpty(locationVO.getRiskGroupId())){
					AppUtils.isLocationAddedInCurrentSection(request,Integer.valueOf( locationVO.getRiskGroupId() ));
				}
			}
			/*
			 * contents fetched for the screen FORM will be displayed from request set as attribute within the below methods implementation for each section
			 */
			setContentsListToRequest(request, policyContext);

			if( !isANewLocation( riskGroupId ) ) break dataPopulation;

			/* If it is not a case of a location reload, it is a case of section screen load. In that case, the main JSP with the
			 * screen FORM will be loaded. For that, we have to set some values in the request to show on the screen. */
			setLocationDetailsToRequest( request, policyContext, locationVO );
			
			
		}

		getDefaultValues(request);
		/* Set up the redirection: If it is not a case of reload of a location data, then it is a case of a fresh screen load. In 
		 * that case, the <Section>Contents JSP needs to be processed. Else, the JSON JSP needs to be processed. */
		Redirection redirection = null;
		if( isCaseOfReload ) redirection = new Redirection( SectionRHUtils.getLocationReloadJSP( policyContext.getCurrentSectionId(), true ), 
															Redirection.Type.TO_JSP );
		
		responseObj.setRedirection( redirection );
		
		return responseObj;
	}
	
	/**
	 * Returns the section's class code.
	 * @return
	 */
	protected abstract int getSectionClassCode(Integer sectionId);

	/**
	 * Sets the request values required by acl tag.
	 * @param request
	 * @param policyContext 
	 */
	protected void setAuthRequestValues( HttpServletRequest request, PolicyContext policyContext )
	{
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );
	}
	

	private String getCurrentRiskGroupId( HttpServletRequest request, PolicyContext pc ){
		String riskGroupId = null;
		
		/* (f) Special case for dynamic rows display in case of of switching locations */
		/* the parameter forDynamicRows will only come as part of TB section 
		 * and is used to avoid loading of data for each location switch in case of non- new quotes*/
		String forDynamicRows = (String)request.getParameter( "forDynamicRows" );
		if(!Utils.isEmpty( forDynamicRows ) && Boolean.valueOf( forDynamicRows )){
			riskGroupId = (String)request.getParameter( "riskGroupId" );
			return riskGroupId;
		}
		rgIdDetermination: {
			/* If the section Id from the request is not the same as the current section from PolicyContext, then it
			 * is a case of NEXT or PREVIOUS, which means that the entire section FORM is being loaded and not just the
			 * data for a location. In that case, just take the first location from the sections location list. */
			String sectionId = request.getParameter( "sectionId" );
			if( Utils.isEmpty( sectionId ) || !Integer.valueOf( sectionId ).equals( pc.getCurrentSectionId() ) ){
				java.util.List<Integer> rgIds = pc.getRiskGroupIds( pc.getCurrentSectionId() );
				if( !Utils.isEmpty( rgIds )  && (pc.getCurrentSectionId() == BI_SECTION_ID || pc.getCurrentSectionId() == TB_SECTION_ID || pc.getCurrentSectionId() == GROUP_PERSONAL_ACCIDENT_SECTION_ID || pc.getCurrentSectionId() == FIDELITY_SECTION_ID || (pc.getCurrentSectionId()== WC_SECTION_ID && !Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30"))))
				{
					riskGroupId = getRiskGroupIdForBI(pc.getCurrentSectionId().toString(),rgIds,pc);
					break rgIdDetermination;
				}
				else if( !Utils.isEmpty( rgIds ) ){
					riskGroupId = rgIds.get( 0 ).toString();
					break rgIdDetermination;
				}
			}
			
			/* If the section doesn't have any risk group Ids, then take the one present in the request. Ideally, this
			 * should lead to an error. However, the decision of whether it is really an exceptional scenario should be
			 * left out of scope for this method. */
			riskGroupId = request.getParameter( "riskGroupId" );
			
			if( Utils.isEmpty( riskGroupId ) ){
				riskGroupId = (String) ThreadLevelContext.get( "RISK_GROUP_ID" );
			}
		}
		
		/** SKN : Changes
		String riskGroupId = request.getParameter( "riskGroupId" );
		
		if( Utils.isEmpty( riskGroupId ) ){
			riskGroupId = (String) ThreadLevelContext.get( "RISK_GROUP_ID" );
		}
		*/
		return riskGroupId;
	}

	/*
	 * This method is an exceptional case for BI and TB section as only one location is available to save 
	 * and the same should be returned in case of repaeated traversal
	 */
	private String getRiskGroupIdForBI(String secId,java.util.List<Integer> rgIds, PolicyContext pc)
	{
		String riskGroupId = rgIds.get( 0 ).toString();
		Iterator<Integer> rgIter = rgIds.iterator();
		Integer rgInt;
		LocationVO locationVO = null;
		RiskGroupDetails rgd = null;
		while(rgIter.hasNext())
		{
			rgInt = rgIter.next();
			locationVO = (LocationVO) pc.getRiskGroup( pc.getCurrentSectionId(), rgInt.toString() );
			rgd = (RiskGroupDetails)pc.getRiskGroupDetails( Integer.parseInt( secId ), locationVO );
			if(null != rgd)
			{
				if( pc.getCurrentSectionId() == TB_SECTION_ID){
					return locationVO.getRiskGroupId();
				}
				if( Integer.parseInt( secId ) == GROUP_PERSONAL_ACCIDENT_SECTION_ID){
					return locationVO.getRiskGroupId();
				}
				if( Integer.parseInt( secId ) == FIDELITY_SECTION_ID){
					return locationVO.getRiskGroupId();
				}
				if( Integer.parseInt( secId ) == WC_SECTION_ID){
					return locationVO.getRiskGroupId();
				}
				return rgd.getBasicRiskId().toString();
			}
		}
		
		return riskGroupId;
	}
	/**
	 * Gets the location details from the Risk Details Map basis the section id
	 * and the sets the same to "currRG" request attribute
	 * @param request
	 * @param policyContext
	 * @param rg
	 */
	private void setLocationDetailsToRequest( HttpServletRequest request, PolicyContext policyContext, RiskGroup rg ){
		
		
		rg = setSectionLevelRiskGroupDetailsToRequest( request, policyContext);
		
		/*
		 * IMP please read:
		 * Scenario - 
		 * (a). Not a new quote process and the current section is being loaded for the first time
		 * (b). Not a saved location block gets executed because risk group id before the first time 
		 * 	  	section content load is null. 
		 * (c). Hence rg obtained passed to this method will be null hence risk group id 
		 * 		doesn't get updated and subsequently clickLoc() function doesn't get triggered 
		 */
		/*
		if(Utils.isEmpty( rg )){
			setCurrentRiskGroupToRequest( request, locationVO );
		}else{
			setCurrentRiskGroupToRequest( request, rg );
		}*/
		setCurrentRiskGroupToRequest( request, rg );
	}
	
	/**
	 * Gets the location details from the Risk Details Map basis the section id
	 * @param request
	 * @param policyContext
	 */
	protected abstract RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext );

	/**
	 * Sets the current risk group i.e. LocationVO to "currRG" request attribute to
	 * display the same on UI
	 * @param request
	 * @param rg
	 */
	private void setCurrentRiskGroupToRequest( HttpServletRequest request, RiskGroup rg ){
		request.setAttribute( AppConstants.REQ_ATTR_CURR_RG, rg );
	}

	/**
	 * Checks if the location has already been saved once. If it has been, then the riskGroupId will be a number. If it has not been
	 * saved, it will be either empty or start with "Location".
	 * For Location copied from PAR or PL riskGroupId will be a number but still if the location info is save can only be
	 * checked if the riskGroupDetails exist for the Location riskGroupId key
	 * @param riskGroupId
	 * @return
	 */
	private boolean isANewLocation( String riskGroupId ){
		return Utils.isEmpty( riskGroupId ) || riskGroupId.startsWith( UNSAVED_LOCATION_ID_PREFIX );
	}
	
	/**
	 * This method will be implemented if there are contents to displayed dynamically on load_screen action
	 * in the UI which is the case with PAR and Money sections currently.
	 * If not then an emtpy implementation will be added
	 * @param request
	 * @param policyContext
	 */
	protected abstract void setContentsListToRequest(HttpServletRequest request, PolicyContext policyContext);
	
	/**
	 * Set country to default value based on logged in location.
	 */
	protected abstract void getDefaultValues(HttpServletRequest request);
	
    /**
     * Sets isCommissionApplicable request attribute with true or false value depending on the LooK Up
     * result for category "IS_COMM_APPL". This is basically used to identify case of direct walkin's 
     * @param request
     * @param policyContext
     */
	private void setCommisionApplFlagToReq(HttpServletRequest request,  PolicyContext policyContext){
    	
    	PolicyVO policyDetails = policyContext.getPolicyDetails();
    	String distChannel = null;
    	if( !Utils.isEmpty( policyDetails ) && !Utils.isEmpty( policyDetails.getGeneralInfo() ) && !Utils.isEmpty( policyDetails.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( policyDetails.getGeneralInfo().getSourceOfBus().getDistributionChannel() ) ){
    		distChannel = policyDetails.getGeneralInfo().getSourceOfBus().getDistributionChannel().toString();
    	}
    	
    	LookUpListVO listVO = SvcUtils.getLookUpCodesList( "IS_COMM_APPL", distChannel, "ALL");
    	
    	String isCommissionApplicable = "false";
    	
    	if( !Utils.isEmpty( listVO ) &&  !Utils.isEmpty( listVO.getLookUpList() ) &&  !Utils.isEmpty( listVO.getLookUpList().get( 0 ) ) ){
    		isCommissionApplicable = listVO.getLookUpList().get( 0 ).getDescription();
    	}
    	
    	request.setAttribute( AppConstants.IS_COMMISSION_APPLICABLE, isCommissionApplicable );
    }
    
	/**
	 * Sets IS_ADDTL_COVER_APPL request attribute with true or false boolean value depending on the look up
	 * result for category IS_ADDTL_COVER_APPL. This is basically used to identify if additional cover is
	 * configured for the tariff code selected in GeneralInfo Screen
	 * @param request
	 * @param policyContext
	 */
	private void setAddtlCoverApplFlagToReq( HttpServletRequest request, PolicyContext policyContext ){
		PolicyVO policyDetails = policyContext.getPolicyDetails();
		Integer polTariffCode = policyDetails.getScheme().getTariffCode();
		Boolean isAddtlCoverAvailable = false;
		SectionVO currSection = com.rsaame.pas.svc.utils.PolicyUtils.getSectionVO( policyDetails, policyContext.getCurrentSectionId() );

		String ADDTL_COVER_AVAILABLE = SvcUtils.getLookUpDescription( AppConstants.IS_ADDTL_COVER_APPL, policyContext.getCurrentSectionId().toString(), currSection.getClassCode()
				.toString(), polTariffCode );

		if( !Utils.isEmpty( ADDTL_COVER_AVAILABLE ) ) isAddtlCoverAvailable = true;

		request.setAttribute( AppConstants.IS_ADDTL_COVER_APPL, isAddtlCoverAvailable );
	}
    
	/**
	 * Sets Tariff code and scheme code as request attribute's in order to use them on JSP for filtering occupancy code
	 * basis these values
	 * @param request
	 * @param policyContext
	 */
	private void setReqAttrForOcpFilter(HttpServletRequest request, PolicyContext policyContext){
		PolicyVO policyDetails = policyContext.getPolicyDetails();
		if( Utils.isEmpty( policyDetails ) ){
			throw new BusinessException( "cmn.unknownError", null, "PolicyVO is NULL within setValuesToReqOccFilter hence cannot populate occupancy codes" );
		}
		request.setAttribute( AppConstants.REQ_ATTR_SEL_TARIFF_CODE, policyDetails.getScheme().getTariffCode());
		request.setAttribute( AppConstants.REQ_ATTR_SEL_SCHEME_CODE, policyDetails.getScheme().getSchemeCode());
		
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){ 
			request.setAttribute( AppConstants.REQ_ATTR_SEL_BUS_DESC_CODE, policyDetails.getGeneralInfo().getInsured().getBusDescription());
			/*
			 * set occupancy code based on business description for Oman.
			 */
			LookUpListVO listVO = SvcUtils.getLookUpCodesList( "OCCUPANCY", policyDetails.getGeneralInfo().getInsured().getBusDescription() ,policyDetails.getScheme().getTariffCode().toString());
	    	
	    	String ocpCode = null ;
	    	
	    	if( !Utils.isEmpty( listVO ) &&  !Utils.isEmpty( listVO.getLookUpList() ) &&  !Utils.isEmpty( listVO.getLookUpList().get( 0 ) ) ){
	    		ocpCode = listVO.getLookUpList().get( 0 ).getCode().toString();
	    	}
	    	
	    	request.setAttribute( AppConstants.REQ_ATTR_SEL_OCP_CODE, ocpCode );
		}
		
		
	}
}
