package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.PPPCriteriaVO;
import com.rsaame.pas.vo.app.SectionPPPDataHolder;
import com.rsaame.pas.vo.bus.LocationAddressVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.app.Contents;



/**
 * Loads data for the current section, if this is not a new quote flow.
 */
public class LoadSectionDataRH implements IRequestHandler{
	private final static DecimalFormat decForm = new DecimalFormat(AppConstants.APP_DECIMAL_FORMAT);
	 //Added for Bahrain 3 decimal - Starts
	private final static DecimalFormat decFormBahrain = new DecimalFormat(SvcConstants.DECIMAL_FORMAT_BAHRAIN);
	 //Added for Bahrain 3 decimal - Ends
	public static final String[] FGB_SCHEME_CODE = Utils.getMultiValueAppConfig( "FGB_SCHEME_CODE" );
	
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		/* The PolicyContext would have been created when General Info was loaded. Hence, we need to focus on the loading of the
		 * section data alone. The steps are:
		 * (a) Check if this is a new quote flow. The logic for fetching saved data will be executed only if it is not.
		 * (b) If base section is not loaded, load that first. 
		 * (c) Load the section that is being requested.
		 * (d) Merge the risk group details map in the section to the ones in the base section.
		 * (e) Update policyvo.premium till the current section which will be used to display on each of the section 
		 * (f) Special case for dynamic rows display in case of of switching locations
		 */
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		
		/* (f) Special case for dynamic rows display in case of of switching locations */
		/* the parameter forDynamicRows will only come as part of TB section 
		 * and is used to avoid loading of data for each location switch in case of non- new quotes*/
		/*String forDynamicRows = (String)request.getParameter( "forDynamicRows" );
		if(!Utils.isEmpty( forDynamicRows ) && Boolean.valueOf( forDynamicRows )){
			// This is the case of location switch so no need to load the data again
			//return null;
		}*/
		
		/* (a) Check if this is a new quote flow. The logic will be executed only if it is not. */
		if( !policyContext.isNewQuote() ){
			/* (b) Load the basic section data, if not loaded already. */
			
			//TODO: Introduce a condition so that loading of basic section does not happen every time a section gets loaded.
				Integer basicSectionId = policyContext.getBasicSectionId();
				loadSectionData( request, policyContext, basicSectionId );
			
			
			/* (c) Now that we have ensured that the basic section's data is created, let us load the current section in question. */
			SectionVO section = null;
			Integer sectionIdForLoading = retrieveSectionId( request, policyContext );
			if( !Utils.isEmpty( sectionIdForLoading ) && !sectionIdForLoading.equals( policyContext.getBasicSectionId() ) ){
				section = loadSectionData( request, policyContext, sectionIdForLoading );
				
				/* (d) Merge the risk group details section to the policy. This is being done to allow the carrying over of any new 
				 * location added in the basic section for this policy before this section was loaded. */
				mergeNewRiskGroups( policyContext, section );
			}
			
			/* (e) Obtaining the total premium till the current section */
			updatePremiumTillCurrentSec(policyContext);
		}
		
		/* Identify the pre-packaged policy flow in order to fetch the different contents i.e. risk types configured for the basic 
		 * cover for the section based on the section id configured. */
		PolicyVO polVO = policyContext.getPolicyDetails();
		if(!Utils.isEmpty(polVO)){
			
			if(!Utils.isEmpty(polVO.getIsPrepackaged()) && polVO.getIsPrepackaged()){
				
				/* Construct InputVO to fetch applicable risk types*/
				DataHolderVO<List> pppInput = constructPPPInput(policyContext);
				
				/* Invoke FetchPPPValSvc.loadSectionPPPContents() to obtain SectionPPPDataHolder constructed */
				SectionPPPDataHolder secPPPData  = loadSectionPPPData(pppInput);
				
				/* Fetch SectionVO from SectionPPPDataHolder and set the same to session as a new attribute which can be from */
				SectionVO sectionVO = secPPPData.getSectionVO();
				
				sectionLoadPostProcessing(request, sectionVO);
				
				request.getSession(false).setAttribute(AppConstants.SECTION_PPP_DATA,sectionVO);
				
				/* Get Contents List using SectionPPPDataHolder which can be used to display the same on the UI using session attribute */
				List<Contents> contentsList = constructSectionContents(secPPPData);
				
				/* DataHolderVO is used as a holder for List<Contents>. The same will be set to session */
				DataHolderVO<List<Contents>> contentsListHolder = new DataHolderVO();
				contentsListHolder.setData(contentsList);
				
				/* Set the contents to session which can be used to display the same on the corresponding content JSP */
				request.getSession(false).setAttribute(AppConstants.SECTION_CONTENTS,contentsListHolder);		
			}
			
		}
		
		
		return null;
	}
	
	/**
	 * Method perform post processing tasks like setting request attributes, clearing ThreadLevelConext items etc..
	 * @param request
	 * @param sectionVO
	 */
	private void sectionLoadPostProcessing(HttpServletRequest request, SectionVO sectionVO) {
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO polVO = policyContext.getPolicyDetails();
		
		// Current Section is PAR and is Pre-pack than load  Additional cover text from look up.  
		if(sectionVO.getSectionId().equals(AppConstants.SECTION_ID_PAR)  && !Utils.isEmpty( policyContext.getPolicyDetails().getIsPrepackaged() ) && policyContext.getPolicyDetails().getIsPrepackaged()) {
			request.setAttribute("additionalCoverText",  SvcUtils.getLookUpDescription("PREPACK_PAR_UI_TEXT", "20", "ALL",20 )) ;
			
		}
		//Updated for AdventId:65098; POBox Changes
		/* To inherited the PO Box value provided in general information screen to base section in AMEND_POLICY , CREATE_QUOTE and EDIT_QUOTE flows*/
		/*if(policyContext.isCurrentSectionBasic() && !policyContext.getPolicyDetails().getAppFlow().equals( Flow.VIEW_POL ) && !policyContext.getPolicyDetails().getAppFlow().equals( Flow.VIEW_QUO )) {
			String poBoxRequired = ( !Utils.isEmpty( request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ) ) ? request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ).toString() : null );
			setPoxBoxDetailsFromGI(policyContext.getPolicyDetails(), sectionVO, poBoxRequired);			
			if( !Utils.isEmpty( request.getSession(false).getAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED ) ) ){
				request.getSession(false).removeAttribute( AppConstants.SESSION_ATTR_HAS_POBOX_CHANGED  );
			}
			
		}*/
		
		/* For prepackage the employee type on WC is depends on the business type selected in GI, so to fetch the employye types from lookup
		 * level1 business type is required so setting the same to request */
		if( sectionVO.getSectionId().equals( AppConstants.SECTION_ID_WC ) )
		{
			if( !Utils.isEmpty( policyContext.getPolicyDetails().getIsPrepackaged() ) &&  policyContext.getPolicyDetails().getIsPrepackaged() && !isPolicyFGB(polVO))
			{
				request.setAttribute(com.Constant.CONST_EMPTYPELEVEL1, policyContext.getPolicyDetails().getGeneralInfo().getInsured().getBusType().toString() ) ;
				request.setAttribute("empTypeLevel2",  AppConstants.LOOKUP_LEVEL2);
			}
			else
			{
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){	
							request.setAttribute(com.Constant.CONST_EMPTYPELEVEL1, policyContext.getPolicyDetails().getGeneralInfo().getInsured().getBusType().toString() ) ;
				} else {
					       request.setAttribute(com.Constant.CONST_EMPTYPELEVEL1,  polVO.getScheme().getTariffCode().toString());
				}
				
				request.setAttribute("empTypeLevel2",  AppConstants.LOOKUP_LEVEL2 );
			}
		}
		else if( sectionVO.getSectionId().equals( AppConstants.SECTION_ID_PL ) &&  isPolicyFGB(polVO))
		{
			if(!Utils.isEmpty(polVO.getGeneralInfo()) && !Utils.isEmpty(polVO.getGeneralInfo().getInsured()) &&   !Utils.isEmpty(polVO.getGeneralInfo().getInsured().getTurnover()))
			{
				request.setAttribute("FGBTurnOver",  AppConstants.LOOKUP_LEVEL2 );
			}
			
		}
		
	}

	private boolean isPolicyFGB(PolicyVO polVO)
	{
		boolean isPolicyFGB = false;
		
		if(!Utils.isEmpty(polVO.getScheme()))
		{
			for(int i = 0; i<FGB_SCHEME_CODE.length;i++)
			{
				if(polVO.getScheme().getSchemeCode().toString().equals(FGB_SCHEME_CODE[i]))
				{
					isPolicyFGB = true;
					break;
				}
			}
		}
		
		return isPolicyFGB;
	}
	/**
	 * Method added to set the PO Box from GI to Location VO.
	 * @param policyVO
	 * @param sectionVO
	 */
	private void setPoxBoxDetailsFromGI(PolicyVO policyVO , SectionVO sectionVO, String poBoxRequired){
		
		LocationVO locationVO = null;
			if( (!Utils.isEmpty( policyVO.getGeneralInfo().getInsured().getAddress() )) && (!Utils.isEmpty( policyVO.getGeneralInfo().getInsured().getAddress().getPoBox()))  ) {
				
				String poBox = policyVO.getGeneralInfo().getInsured().getAddress().getPoBox();
				java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
			    if(!Utils.isEmpty( riskGroupDetails )) {
			    	for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
						locationVO = (LocationVO)locationEntry.getKey();					
						if( !Utils.isEmpty( locationVO ) ||  Utils.isEmpty( locationVO.getAddress() ) || (  !Utils.isEmpty( poBoxRequired ) && poBoxRequired.equals( "true" ) ) ) {
							LocationAddressVO locationAddressVO = new LocationAddressVO();
							locationAddressVO.setPoBox(poBox);
							locationVO.setAddress(locationAddressVO);						
						}
					
			    	}
			    }
			}		
	}
	
	private SectionVO loadSectionData( HttpServletRequest request, PolicyContext policyContext, Integer sectionId ){
		/* (a) Construct LoadExistingInputVO. */
		LoadExistingInputVO input = constructInput( request, policyContext, sectionId );
		
		/* (b) Call SectionSvc.loadSectionData() to get the populated SectionVO for the section with all the saved location data. */
		SectionVO section = loadSectionData( input );
		
		/* (c) Now "section" has all the data (loaded from DB and new risk groups) in it. Hence, replace the currently available 
		 * SectionVO with this newly loaded SectionVO. */
		replaceSection( policyContext, section );
		
		return section;
	}

	private void replaceSection( PolicyContext policyContext, SectionVO section ){
		java.util.List<SectionVO> sections = policyContext.getPolicyDetails().getRiskDetails();
		
		/* The list of sections will NOT be null because the list would have been created in SectionRH.handleLoadDataAction()
		 * before this RH was called. */
		int sectionIndex = sections.indexOf( section );
		
		if( sectionIndex < 0 ) policyContext.addSection( section );
		else sections.set( sectionIndex, section );
	}

	/**
	 * Gets all the risk groups that are present in the base section and not present in the loaded section and adds them
	 * to the loaded section with <code>null</code> as value.
	 * @param pc
	 * @param section
	 */
	private void mergeNewRiskGroups( PolicyContext pc, SectionVO section ){
		SectionVO pcSection = pc.getSectionDetails( section.getSectionId() );

		java.util.Map rgDetails = pcSection.getRiskGroupDetails();
		if( !Utils.isEmpty( rgDetails ) ) section.getRiskGroupDetails().putAll( rgDetails );

		SectionVO baseSection = pc.getBasicSection();

		/* Get the risk group details map for the base section. */
		java.util.Map<RiskGroup, RiskGroupDetails> baseSectionRGDMap = (java.util.Map<RiskGroup, RiskGroupDetails>) baseSection.getRiskGroupDetails();

		/* Get the risk group details map for the current section. If it is null, initialize it. */
		java.util.Map<RiskGroup, RiskGroupDetails> currSectionRGDMap = (java.util.Map<RiskGroup, RiskGroupDetails>) section.getRiskGroupDetails();
		if( currSectionRGDMap == null ){
			currSectionRGDMap = new com.mindtree.ruc.cmn.utils.Map<RiskGroup, RiskGroupDetails>( RiskGroup.class, RiskGroupDetails.class );
			section.setRiskGroupDetails( currSectionRGDMap );
		}
		
		/* Now, for every entry in the base section map, that is not present in the current section map, add a new entry into a temp map
		 * with a copy of the base section RiskGroup instance as the key. The temp map will represent all such missing entries. */
		java.util.Map rgDetailTemp = new java.util.HashMap();
		if( !Utils.isEmpty( baseSectionRGDMap ) ){
			
			for( Map.Entry<RiskGroup, RiskGroupDetails> baseSectionRiskGroup : baseSectionRGDMap.entrySet() ){
				RiskGroup baseSectionRG = (RiskGroup) baseSectionRiskGroup.getKey();
				if( currSectionRGDMap.containsKey( baseSectionRG ) ){
					continue;
				}
				else{
					rgDetailTemp.put( baseSectionRG.copy(), null );
				}
			}
		}
		
		/* If there are missing risk groups, add them to the current section. */
		if( !rgDetailTemp.isEmpty() ){
			section.getRiskGroupDetails().putAll( rgDetailTemp );
		}
	}
	
	/**
	 * Gets the pre-population values based on scheme and tariff.
	 * @return
	 *//*
	private SectionVO loadPPData(){
		 TODO Implementation pending 
		return null;
	}*/

	/**
	 * Calls SectionSvc.loadSectionData() to get the populated SectionVO for the section with all the saved location data.
	 * 
	 * @param input
	 * @return 
	 */
	private SectionVO loadSectionData( LoadExistingInputVO input ){

		/* Calling loadSectionData method of SectionSvc */
		SectionVO section = (SectionVO) TaskExecutor.executeTasks( AppConstants.LOADSECTIONSDATA, input );
		return section;
	}

	/**
	 * Sets the following into LoadExistingInputVO and returns it:
	 * (a) Current section Id (available in PolicyContext)
	 * (b) Policy Linking Id (available in PolicyContext.policyDetails)
	 * (c) Endorsement Id (available in PolicyContext.policyDetails)
	 * @param request 
	 * 
	 * @param policyContext
	 * @return
	 */
	private LoadExistingInputVO constructInput( HttpServletRequest request, PolicyContext policyContext, Integer sectionId ){
		LoadExistingInputVO existingInputVO = new LoadExistingInputVO();
		
		if( !Utils.isEmpty( policyContext ) ){
			/* If a section Id has been passed, that gets the highest priority. Then to "jumpToSectionId" passed in the request and
			 * finally, to the current section Id from the Policy Context. */
			existingInputVO.setSectionId( sectionId );
			
			/*
			 * Fetch always latest endt Id and store in existing VO
			 */
			Long endtId = com.rsaame.pas.util.AppUtils.getLatestEndtId( policyContext.getPolicyDetails() );
			
			if( !Utils.isEmpty( policyContext.getPolicyDetails() ) ){
				existingInputVO.setPolLinkingId( policyContext.getPolicyDetails().getPolLinkingId() );
				existingInputVO.setEndtId( endtId );
				existingInputVO.setQuote( policyContext.getPolicyDetails().getIsQuote() );
				existingInputVO.setIsPrepackaged( policyContext.getPolicyDetails().getIsPrepackaged() );
				existingInputVO.setBasicSectionId( com.rsaame.pas.svc.utils.PolicyUtils.getBasicSectionId( policyContext.getPolicyDetails() ) );
				existingInputVO.setPolicyStatus( policyContext.getPolicyDetails().getStatus() );
				existingInputVO.setTariffCode(policyContext.getPolicyDetails().getScheme().getTariffCode());
				setAppFlow(existingInputVO,policyContext,request);//existingInputVO.setAppFlow(policyContext.getAppFlow());
			}
		}
		return existingInputVO;
	}
	
	
	
	
	private void setAppFlow( LoadExistingInputVO existingInputVO, PolicyContext policyContext, HttpServletRequest request ){
		
		PolicyVO p = policyContext.getPolicyDetails();
		TaskVO taskDetails = policyContext.getTaskDetails();
		existingInputVO.setAppFlow( p.getAppFlow() );
		
		/* In the case of referrals, we need to send one of EDIT_QUO, VIEW_QUO, AMEND_POL or VIEW_POL to the service layer based
		 * on whether the logged in user is the initiator of the */
		if( p.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ){
			UserProfile user = AppUtils.getUserDetailsFromSession( request );
			
			if( p.getIsQuote() ){
				if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) ) ){
					existingInputVO.setAppFlow( Flow.EDIT_QUO );
				}
				else{
					existingInputVO.setAppFlow( Flow.VIEW_QUO );
				}
			}
			else{
				existingInputVO.setAppFlow( Flow.VIEW_POL );
				if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
						&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_REFERRED" ) ) ) ){
					existingInputVO.setAppFlow( Flow.AMEND_POL );
				}
				else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() ) )
						&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_ACCEPT" ) ) ) ){
					existingInputVO.setAppFlow( Flow.VIEW_POL );
				}
				else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
						&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_ACCEPT" ) ) ) ){
					existingInputVO.setAppFlow( Flow.AMEND_POL );
				}
				else if( !Utils.isEmpty( taskDetails ) && user.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) )
						&& p.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_REFERRED" ) ) ) ){
					existingInputVO.setAppFlow( Flow.VIEW_POL );
				}
			}
		}
		
	}

	private Integer retrieveSectionId( HttpServletRequest request, PolicyContext policyContext ){
		/* If there is a "jumpToSectionId" parameter in the request, we will use that section Id. This parameter is sent by
		 * navigation functionalities like left navigation. */
		String jumpToSectionId = request.getParameter( AppConstants.REQ_PARAM_JUMP_TO_SECTION_ID );
		return Utils.isEmpty( jumpToSectionId ) ? policyContext.getCurrentSectionId() : Integer.valueOf( jumpToSectionId );
	}
	
	
	/*
	 *  Construct PPPCriteriaVO to query VMasPasFetchBasicDtls view to 
	 *  get the number of contents and description to display the same
	 *  on screen
	 */
	private DataHolderVO  constructPPPInput(PolicyContext policyContext){
		PPPCriteriaVO pppCriteriaVO = new PPPCriteriaVO();
		
		DataHolderVO<List> pppInput = new DataHolderVO<List>();
		List inputList = new ArrayList();
		
		if(!Utils.isEmpty(policyContext)){
			if(!Utils.isEmpty( policyContext.getCurrentSectionId()))
				pppCriteriaVO.setSectionId( policyContext.getCurrentSectionId() );
			
			if(!Utils.isEmpty( policyContext.getPolicyDetails()))
			{
				pppCriteriaVO.setTariffCode(policyContext.getPolicyDetails().getScheme().getTariffCode());	
				pppCriteriaVO.setClassCode(Integer.parseInt(Utils.getSingleValueAppConfig(Utils.concat("SEC_",policyContext.getCurrentSectionId().toString()))));
			}
		}
		
		inputList.add(pppCriteriaVO);
		inputList.add(policyContext.getPolicyDetails());
		pppInput.setData(inputList);
		return pppInput;
	}
	
	/*
	 *  Following method will be used to invoke FetchPPPValSvc.fetchPPPVal() method
	 *  
	 */
	private  SectionPPPDataHolder loadSectionPPPData(DataHolderVO<List> pppInput){
		
		/* Calling loadSectionPPPData method of SectionSVC */
		SectionPPPDataHolder secPPPData=(SectionPPPDataHolder) TaskExecutor.executeTasks(AppConstants.PRE_PACKAGED_SVC_IDENTIFIER, pppInput);
		
		return secPPPData;
	}
	
	
	private List<Contents> constructSectionContents(SectionPPPDataHolder sectionPPPData){		
		
		return sectionPPPData.getContentsList();
	}
	
	/*
	 * This method will update PolicyVO.premium which will always signify the total premium till the current section  
	 */
	private void updatePremiumTillCurrentSec(PolicyContext policyContext){
		Double totalPrm = 0.0;
		PolicyVO policyVO = policyContext.getPolicyDetails();
		List<SectionVO> secList = policyVO.getRiskDetails();
		for(SectionVO section:secList){
			totalPrm += SvcUtils.getSectionLevelPremium(section);
		}
		PremiumVO prmVO = new PremiumVO();
		// Added for Bahrain 3 decimal - Starts
		if (isSBSBahrainPolicy(policyVO)) {
			prmVO.setPremiumAmt(Double.valueOf(decFormBahrain.format(totalPrm)));
		} else {
			prmVO.setPremiumAmt(Double.valueOf(decForm.format(totalPrm)));

		}
		// Added for Bahrain 3 decimal - Ends
		policyVO.setPremiumVO(prmVO);
	}
	// Added for Bahrain 3 decimal - Starts
		 private boolean isSBSBahrainPolicy(PolicyVO policy) {
		    	
		    	Integer policyType=0;
		        policyType = Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) );
		        if (policyType == Integer.valueOf(policy.getScheme().getPolicyType())
		                && Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)
		                .equalsIgnoreCase("50")) {
		        	return true;
		        }
		        return false;
		    }
		// Added for Bahrain 3 decimal - Ends
}
