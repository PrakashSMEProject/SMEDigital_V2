package com.rsaame.pas.ui.cmn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class PolicyContext implements Serializable{
	private static final long serialVersionUID = 1L;

	private static final int SECTION_ID_PAR = Integer.parseInt( Utils.getSingleValueAppConfig( "SECTION_ID_PAR", "1" ) );
	private static final int SECTION_ID_PL = Integer.parseInt( Utils.getSingleValueAppConfig( "SECTION_ID_PL", "6" ) );
	private static final int SECTION_ID_WC = Integer.parseInt( Utils.getSingleValueAppConfig( "SECTION_ID_WC", "7" ) );
	private static final int SECTION_ID_MONEY = Integer.parseInt( Utils.getSingleValueAppConfig( "SECTION_ID_MONEY", "8" ) );
	private static final int SECTION_ID_BI = Integer.parseInt( Utils.getSingleValueAppConfig( "SECTION_ID_BI", "2" ) );
	private static final int SECTION_ID_GPA = Integer.parseInt( Utils.getSingleValueAppConfig( "SECTION_ID_GPA", "12" ) );

	/** 
	 * Policy/Quote information
	 */
	private PolicyVO policyDetails = null;
	
	/* Common details related to Home / travel insurance. */
	private CommonVO commonDetails = null;
	
	private CoverDetails coverDetails = null;
	
	/**
	 * A copy of the existing PolicyVO for rolling back changes.
	 */
	private PolicyVO rbPolicyDetails = null;
	
	/**
	 * A copy of the existing CommonVO for rolling back changes.
	 */
	private CommonVO rbCommonDetails = null;
	
	private boolean aTransactionIsOn = false;

	/** A list to maintain the order of the sections. IMP: This field is purely for internal use
	 * within the class. Please don't create getter and setter methods for this. */
	private transient Map<Integer, UISection> sectionsMap;

	/** Indicates the current screen using its Id. 
	 * 0 - General Info
	 * 1 - Risk section
	 * 2 - Premium Screen
	 * */

	/**
	 * Indicates the current step, ie, General Info, Sections or Premium Page.
	 */
	private Integer currentStep;

	/**
	 * Indicates the current section in view.
	 */
	private Integer currentSectionId;
	
	/**
	 * A rollback copy of <code>currentSectionId</code> to enable rolling back NEXT or PREVIOUS actions.
	 */
	private Integer rbCurrentSectionId;

	private static final String SECTION_APPEND = "Section_";
	
	private boolean newQuote = true;
	
	
	/*
	 * This variable identify the flow of the application. 
	 */
	private Flow appFlow;
	
	/**
	 * A flag to indicate if the section loading (in the case of existing quote/policy) has been done, so that we can avoid a 
	 * reload.
	 */
	private boolean sectionVOsCreationDone;
	
	/*
	 * TaskVO is added here to identify the referral flow operation and authorization. 
	 * Since its not part of a policy its not placed in policyVO, if the flow is not referral flow the taskVO will be null
	 */
	
	TaskVO taskDetails;
	
	/** 
	 * PHASE-3 Added map to hold consolidated referral messages
	 *   
	 */
	private Map<String, Map<String,String>> refDataTextField;
	
	private boolean isPolicyCancelled;	
	
	private List<StaffDetailsVO> staffDetailsVO;
	
	
	/**
	 * @return the isPolicyCancelled
	 */
	public boolean isPolicyCancelled(){
		return isPolicyCancelled;
	}



	/**
	 * @param isPolicyCancelled the isPolicyCancelled to set
	 */
	public void setPolicyCancelled( boolean isPolicyCancelled ){
		this.isPolicyCancelled = isPolicyCancelled;
	}



	/**
	 * The policy context will be created only if the application flow is available.
	 * This flow will be used further for authorization, and to identify the operations or functionality that a quote/policy can perform
	 */
	public PolicyContext(Flow appFlow) {
		this.appFlow = appFlow;
		disableService(appFlow.toString());
	}

	
	
	private void disableService(String string) {
		ThreadLevelContext.set( "APPFLOW", string);
	}


	/*This flag has been added to check if current section id value has already been set or not*/
	private boolean setCurrentSectionIdDone=true;

	/**
	 * Starts a fresh transaction for this operation. This call makes a safe copy of the existing <code>policyDetails</code>
	 * and operates only on the rollback copy. Please note that any open transaction will be discarded here. Hence, if there 
	 * is a transaction that needs to continue over multiple requests from the browser, please do not call this method.<br/><br/>
	 * 
	 * The same is 
	 */
	public void startTransaction(){
		if(!Utils.isEmpty( policyDetails )) {
			rbPolicyDetails = CopyUtils.copySerializableObject( policyDetails );
		}
		if (!Utils.isEmpty( commonDetails )){
			rbCommonDetails = CopyUtils.copySerializableObject( commonDetails );
		}
		rbCurrentSectionId = Utils.isEmpty( currentSectionId ) ? null : Integer.valueOf( currentSectionId.intValue() );
		aTransactionIsOn = true;
	}
	
	/**
	 * If a transaction is on, this method makes the copy (for rollback) being worked upon currently as the main PolicyVO object 
	 * and <b><u>shuns</u></b> the existing copy.
	 */
	public void commit(){
		if( aTransactionIsOn ){
			this.policyDetails = this.rbPolicyDetails; /* Make the rollback copy the main copy and shun the existing one. */
			this.commonDetails = this.rbCommonDetails;  /* Make the rollback copy the main copy and shun the existing one. */
			this.currentSectionId = this.rbCurrentSectionId; /* Make the rollback current section Id as the main. */
			endTransaction();
		}
	}
	
	/**
	 * This method shuns the changes since the start of the transaction and brings back the original state of <code>policyDetails</code>.
	 */
	public void endTransaction(){
		aTransactionIsOn = false; /* Close the transaction. */
		this.rbPolicyDetails = null; /* Set rbPolicyDetails to null, so that the object can be garbage-collected. */
		this.rbCommonDetails = null; /* Set rbCommonDetails to null, so that the object can be garbage-collected. */
		this.rbCurrentSectionId = null;
	}
	
	final public UISection getNextScreen(){
		UISection uiSection = null;
		if( !Utils.isEmpty( this.currentSectionId ) ){
			uiSection = getNextScreen( this.currentSectionId );
		}
		else{
			uiSection = getNextScreen( null );
		}
		return uiSection;
	}

	final public UISection getPrevScreen(){
		UISection uiSection = null;
		if( !Utils.isEmpty( this.currentSectionId ) ){
			uiSection = getPrevScreen( this.currentSectionId );
		}
		else{
			uiSection = getPrevScreen( null );
		}
		return uiSection;
	}

	/**
	 * 
	 * Pass the section ID of the current section to get the previous screen 
	 * @param currentSectionId
	 * @return
	 */
	final public UISection getPrevScreen( final Integer sectionId ){

		final List<Integer> sectionlist = getSelectedSectionList();
		UISection prevScreen = null;
		if( !Utils.isEmpty( sectionId ) ){

			final int loc = getPrevId( sectionlist, sectionId );
			if( loc >= 0 && loc < sectionlist.size() ){
				final int prevId = sectionlist.get( loc );
				prevScreen = sectionsMap.get( prevId );
				
				//setAsCurrentSection( prevScreen.getSectionId() );
				this.currentStep = 1;
			}
			/* Don't handle else, so that the screen remains the same. */
		}
		else{
			final int prevId = sectionlist.get( sectionlist.size() - 1 );
			prevScreen = sectionsMap.get( prevId );
			
			//setAsCurrentSection( prevScreen.getSectionId() );
			this.currentStep = 1;
		}

		return prevScreen;
	}

	/**
	 * Returns the UISection instance representing the current section/screen.
	 * @return
	 */
	final public UISection getCurrentScreen(){
		return getScreen( getCurrentSectionId() );
	}

	/**
	 * Returns the UISection instance pertaining to the passed section Id.
	 * @param currentSectionId
	 * @return
	 */
	final public UISection getScreen( final Integer sectionId ){
		return sectionsMap.get( sectionId );
	}

	/**
	 * @return the currentSectionId
	 */
	public Integer getCurrentSectionId(){
		return aTransactionIsOn ? rbCurrentSectionId : currentSectionId;
	}

	/**
	 * Returns the SectionVO for the current section. This method will be useful in JSPs. 
	 * @return the currentSectionId
	 */
	public SectionVO getCurrentSection(){
		return getSectionDetails( getCurrentSectionId() );
	}
	
	/**
	 * This method sets the passed sectionId as the current section.
	 * 
	 * @param sectionId
	 * @throws BusinessException If the section with the passed sectionId has not been selected for the policy
	 */
	public void setAsCurrentSection( int sectionId ){
		List<Integer> selSections = CopyUtils.asList( getAllSelectedSections() );
		if( ( Utils.isEmpty( selSections ) || !selSections.contains( sectionId ) ) && 
			sectionId != AppConstants.SECTION_ID_PREMIUM && 
			sectionId != AppConstants.SECTION_ID_GEN_INFO )
		{
			throw new BusinessException( "pas.cmn.sectionNotAdded", null, "Section [" + sectionId + "] is not present in policy context" );
		}
		
		if( aTransactionIsOn ) this.rbCurrentSectionId = sectionId;
		else this.currentSectionId = sectionId;
	}
	
	/**
	 * Checks and returns if the current section is the basic section for the policy.
	 * @return
	 */
	public boolean isCurrentSectionBasic(){
		boolean isPARPresent = isSectionPresent( AppConstants.SECTION_ID_PAR );
		
		Integer currentSectionId = getCurrentSectionId();
		
		/*
		 * CurrentSectionId will be null for General Information
		 */
		if(Utils.isEmpty( currentSectionId ) ) return false;
		
		/* No handling required for PAR. The locations for PAR will be added during "Add Location" flow. */
		if( ( currentSectionId == AppConstants.SECTION_ID_PAR ) ||
			( currentSectionId == AppConstants.SECTION_ID_PL && !isPARPresent )
		){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the basic section Id for the policy
	 * @return
	 */
	public Integer getBasicSectionId(){
		boolean isPARPresent = isSectionPresent( AppConstants.SECTION_ID_PAR );
		boolean isPLPresent = isSectionPresent( AppConstants.SECTION_ID_PL );
		
		if( isPARPresent ) return AppConstants.SECTION_ID_PAR;
		else if( isPLPresent ) return AppConstants.SECTION_ID_PL;
		else{
			throw new BusinessException( "pas.cmn.basicSectionNotSelected", null, "Basic section Id has not been set for this policy" );
		}
	}

	/**
	 * Returns the SectionVO instance for the basic section for the policy
	 * @return
	 */
	public SectionVO getBasicSection(){
		Integer basicSectionId = getBasicSectionId();
		return getSectionDetails( basicSectionId );
	}

	/**
	 * Returns the current step, ie, General Info, Sections or Premium Page.
	 * @return
	 */
	public Integer getCurrentStep(){
		return currentStep;
	}

	final public void populateAllAvailableSec( Integer[] sectionIds ){
		
		
		/*The below code is used to sort the sections is such an order so that PAR or PL 
		(basic sections always appears before other section on UI)*/
		List<Integer> sectionAvailable = CopyUtils.asList(sectionIds);
		sectionAvailable=AppUtils.sortSections(sectionAvailable);
		sectionIds=CopyUtils.toArray(sectionAvailable);
		
		
		//first time the sectionsMap will be empty. All the Available sections should be populated for that particular 
		//This can be used in case of edit policy, amend and renewal
		if( Utils.isEmpty( sectionsMap ) ){
			sectionsMap = Collections.synchronizedMap( new LinkedHashMap<Integer, UISection>( sectionIds.length ) );
			for( Integer sectionId : sectionIds ){
				if( !Utils.isEmpty( sectionId ) ){
					final UISection uiSection = new UISection();
					uiSection.setJspPage( Utils.getSingleValueAppConfig( Utils.concat( SECTION_APPEND, Integer.toString( sectionId ) ) ) );
					uiSection.setSectionId( sectionId );
					sectionsMap.put( sectionId, uiSection );
				}

			}
		}
	}
	
	/**
	 * Returns all available sections for this policy. This would have been set using a call to populateAllAvailableSec( Integer[] ).
	 * @return
	 */
	final public Integer[] getAllAvailableSections(){
		return sectionsMap.keySet().toArray( new Integer[ 0 ] );
	}
	
	/**
	 * Returns an array of all sections that were selected for this policy.
	 * @return
	 */
	public Integer[] getAllSelectedSections(){
		List<Integer> selectedSectionIdsList = new ArrayList<Integer>();
		java.util.Set<Map.Entry<Integer, UISection>> entries = sectionsMap.entrySet();
		
		for( Map.Entry<Integer, UISection> entry : entries ){
			if( entry.getValue().isSectionSelected() ){
				selectedSectionIdsList.add( entry.getKey() );
			}
		}
		
		return selectedSectionIdsList.toArray( new Integer[ 0 ] );
	}

	/**
	 * Populates the selected sections for the policy and sets the first section Id in the list as the current section. <br/><br/>
	 * 
	 * Please ensure that the sections are passed in their order of display.
	 * 
	 * @param sectionIds
	 */
	final public void populateSelectedSec( final Integer[] sectionIds ){
		// After all the sections are populated, the selected sections are set

		if( !Utils.isEmpty( sectionsMap ) ){
			//boolean first = true;
			for( Integer sectionId : sectionIds ){
				setSectionMapandCurrentSecID(sectionId);
			}

		}
	}

	/*
	 * Populate the selected section for the policy 
	 * 
	 * @param sectionId , first
	 */
	private void setSectionMapandCurrentSecID(Integer sectionId) {
		if( !Utils.isEmpty( sectionId ) ){
			final UISection uiSection = sectionsMap.get( sectionId );
			uiSection.setSectionSelected( true );
			sectionsMap.put( sectionId, uiSection );
		}

		/* Set the first section in the list as the current section. */
		if( setCurrentSectionIdDone ){
			if( aTransactionIsOn ) this.rbCurrentSectionId = sectionId.intValue();
			else this.currentSectionId = sectionId.intValue();
			
			setCurrentSectionIdDone = false;
		}
		
	}
	



	/**
	 * Figures out all the different risk group instances for the specified level and returns
	 * a list containing them. This can be used to construct UI elements like tabs for each
	 * location, which is a risk group instance.
	 * 
	 * @param level
	 * @return
	 */
	public SectionVO getSectionDetails( final Integer sectionId ){

		SectionVO sectionVO = null;
		if( !Utils.isEmpty( getPolicyDetails() ) && !Utils.isEmpty( sectionsMap ) ){
			UISection uiSection = sectionsMap.get( sectionId );
			if( !Utils.isEmpty( uiSection ) && uiSection.isSectionSelected() ){
				sectionVO = getSectionVOFromPolicyVO( sectionId );
			}
		}

		return sectionVO;
	}

	/**
	 * Adds the section to the PolicyVO in the context, if it is not already present. Also, adds the section as selected
	 * to the <code>sectionsMap</code> in the context.
	 * @param section
	 */
	final public void addSection( SectionVO section ){
		final Integer sectionId = section.getSectionId();
		if( !Utils.isEmpty( sectionId ) ){
			/* First, set the section to the PolicyVO, if doesn't have it already. */
			addSectionToPolicyVO( section );

			/* Next, update the sectionsMap in the context. */
			setSectionMapandCurrentSecID(sectionId);
		}
	}

	final public void removeSection( final Integer sectionId ){
		/* TODO Remove the section from PolicyVO */

		if( !Utils.isEmpty( sectionId ) ){
			final UISection uiSection = sectionsMap.get( sectionId );
			uiSection.setSectionSelected( false );
			uiSection.setStatusValues( null );
			sectionsMap.put( sectionId, uiSection );
		}
	}

	/**
	 * This method should be called after a successful execution of an action. The method will change
	 * the state of the context to pre-defined values based on the action.
	 * 
	 * @param action The <code>PolicyContext.Action</code> that represents the action
	 * @param currentSectionId The Id of the section which was acted upon
	 * @param riskGroupId The riskGroupId of the risk level instance. Pass <code>0</code>, if the risk group
	 * level is "section".
	 */
	public void changeState( final Action action, final int sectionId, final int riskGroupId ){

		switch( action ){

			case SAVE:
				saveLocation( sectionId, riskGroupId );
				System.out.print( "Change of state in case of SAVE ");
				break;
			case NEXT:
				saveLocation( sectionId, riskGroupId );
				System.out.print( "Change of state in case of NEXT ");
				break;
			case DELETE_LOCATION:
				deleteLocation( sectionId, riskGroupId );
				break;
			case ADD_LOCATION:
				addLocation( sectionId, riskGroupId );
				break;
			case CALCULATE_PREMIUM:
				premiumCalculated( sectionId, riskGroupId );
				break;
			default:
				break;

		}
	}

	public List<Integer> isAllLocSaved( int sectionId ){
		final UISection uiSection = sectionsMap.get( sectionId );
		List<Integer> riskID = new ArrayList<Integer>();
		Map<Integer, UIRiskGroupStatus> map = uiSection.getStatusValues();

		for( Entry<Integer, UIRiskGroupStatus> entry : map.entrySet() ){
			if( !( entry.getValue().getIsSaved() ) ){
				riskID.add( entry.getKey() );
			}
		}
		return riskID;
	}

	public List<Integer> getRiskGroupIds( Integer sectionId ){
		SectionVO section = getSectionDetails( sectionId );

		List<Integer> riskGroupIds = null;
		if( !Utils.isEmpty( section ) && !Utils.isEmpty( section.getRiskGroupDetails() ) ){
			riskGroupIds = new com.mindtree.ruc.cmn.utils.List<Integer>( Integer.class );
			
			for( RiskGroup key : section.getRiskGroupDetails().keySet() ){
				if( isInteger( key.getRiskGroupId() ) )
				riskGroupIds.add( Integer.valueOf( key.getRiskGroupId() ) );
			}
		}

		return riskGroupIds;
	}

	private boolean isInteger( String s ){
		boolean isInteger = true;
		try{
			Integer.valueOf( s );
		}
		catch( NumberFormatException e ){
			isInteger = false;
		}
		return isInteger;
	}

	/**
	 * Returns the list of RiskGroup instances that the section has. This can be used to get the list of locations for
	 * a section.
	 * 
	 * @param currentSectionId
	 * @return
	 */
	public List<? extends RiskGroup> getRiskGroups( Integer sectionId ){
		SectionVO section = getSectionDetails( sectionId );

		List<? extends RiskGroup> riskGroups = null;
		if( !Utils.isEmpty( section ) && !Utils.isEmpty( section.getRiskGroupDetails() ) ){
			riskGroups = CopyUtils.asList( section.getRiskGroupDetails().keySet() );
		}

		return riskGroups;
	}

	/**
	 * Returns the matching RiskGroup instance. This can be used to get the location details for a particular location
	 * in a section. This will work only if the locations have been set to the section itself. Hence, a call to 
	 * <code>addSection( SectionVO )</code> may be necessary before this method is called.
	 * 
	 * @param currentSectionId
	 * @return
	 */
	public RiskGroup getRiskGroup( int sectionId, String riskGroupId ){
		RiskGroup riskGroup = null;
		List<? extends RiskGroup> riskGroups = getRiskGroups( sectionId );

		/* In the list of RiskGroups check the one that has a matching riskGroupId. */
		if( !Utils.isEmpty( riskGroups ) && riskGroupId != null ){
			for( RiskGroup rg : riskGroups ){
				if( riskGroupId.equals( rg.getRiskGroupId() ) ){
					riskGroup = rg;
					break;
				}
			}
		}

		return riskGroup;
	}
	
	/**
	 * Checks if there are risk group details available for the passed risk group Id.
	 * @param sectionId
	 * @param riskGroupId
	 * @return
	 */
	public boolean isRiskGroupDetailsSaved( int sectionId, String riskGroupId ){
		boolean isSaved = false;
		if( Utils.isEmpty( riskGroupId ) ) return isSaved;
		
		SectionVO section = getSectionDetails( sectionId );
		
		/* The section should be available and the risk group details map shouldn't be empty. */
		if( !Utils.isEmpty( section ) && !Utils.isEmpty( section.getRiskGroupDetails() ) ){
			/* Prepare a dummy LocationVO instance as a key. */
			LocationVO locKey = new LocationVO();
			locKey.setRiskGroupId( riskGroupId );
			
			if( !Utils.isEmpty( section.getRiskGroupDetails().get( locKey ) ) ){
				isSaved = true;
			}
		}
		
		return isSaved;
	}

	/**
	 * Returns the RiskGroupDetails instance for the passed RiskGroup instance for the specified section.
	 * 
	 * @param currentSectionId
	 * @param riskGroup
	 * @return
	 */
	public RiskGroupDetails getRiskGroupDetails( int sectionId, RiskGroup riskGroup ){
		RiskGroupDetails rgd = null;

		SectionVO section = getSectionDetails( sectionId );
		if( !Utils.isEmpty( section ) && ( !Utils.isEmpty( section.getRiskGroupDetails() ) ) )
		{
			rgd = section.getRiskGroupDetails().get( riskGroup );
			if(!Utils.isEmpty(section.getCommission())  && !Utils.isEmpty(rgd))
			{
				rgd.setCommission(section.getCommission());
			}
		}

		return rgd;
	}

	/**
	 * Adds the risk group details against the risk group under the specified section. If the risk group (say location) has 
	 * already been added to the map as a key, then the risk group details being supplied now are replaced and the old values
	 * are lost. However, this will happen properly only if the RiskGroup implementation class, say, LocationVO, overrides
	 * the .equals() and .hashcode() methods appropriately.
	 *  
	 * @param currentSectionId
	 * @param rg
	 * @param rgDetails
	 */
	@SuppressWarnings( "unchecked" )
	public void addRiskGroupDetails( int sectionId, RiskGroup rg, RiskGroupDetails rgDetails ){
		SectionVO section = getSectionDetails( sectionId );

		if( !Utils.isEmpty( section ) ){
			Map rgMap = getRiskGroupMap( section, rg, rgDetails );

			/* Set an Id to the risk group, if doesn't have one. */
			setIdToRiskGroup( rg );

			/* If there are some values to be updated to the key, do them here. */
			updateKeyValues( rgMap, rg );
			
			rgMap.put( rg, rgDetails ); /* This means that the implementation of RiskGroup, being used as the key class,
										 * must override .equals() and .hashcode() methods. */
		}
		else{
			throw new BusinessException( "pas.cmn.sectionsNotAdded", null, "Section with id [" + sectionId, "] not added yet." );
		}
	}
	
	/**
	 * Removes the RiskGroup and RiskGroupDetails entries from the riskGroupDetails map in the specified section. If the current
	 * section is the basic section, then this method will remove the risk group from the other sections too. This has to be done
	 * in tandem with the database operations.
	 * 
	 * @param sectionId Id of the section from which the risk group details have to be removed
	 * @param riskGroupId Id of the RiskGroup instance (say, location) which identifies the key in the <code>riskGroupDetails</code>
	 * map.
	 */
	public void removeRiskGroupDetails( int sectionId, String riskGroupId ){
		/* Validate the availability of both SectionVO and the RiskGroup instance. */
		SectionVO section = getSectionDetails( sectionId );
		if( Utils.isEmpty( section ) ){
			throw new SystemException( "pas.cmn.sectionNotAdded", null, "Section [" + sectionId + "] is not present in policy contex_2" );
		}
		
		RiskGroup rg = getRiskGroup( sectionId, riskGroupId );
		if( Utils.isEmpty( rg ) ){
			throw new SystemException( "pas.cmn.riskGroupNotAdded", null, "Risk group [", riskGroupId, "] is not present in policy contex_3" );
		}

		/* Remove from the riskGroupDetails map. */
		Map rgMap = getRiskGroupMap( section, null, null );
		RiskGroupDetails details=null;
		if( !Utils.isEmpty( rgMap ) ){

			/*If current section is basic remove the riskgroup details from riskgroupdetails map*/
			if( isCurrentSectionBasic() ) rgMap.remove( rg );

			else if(!isCurrentSectionBasic()){
				Map rgMapBasic = getRiskGroupMap( getSectionDetails( getBasicSectionId()), null, null );
				/*If risk group exists in basic section do not remove it from risk group details map , otherwise remove it.*/
				if(!rgMapBasic.containsKey( rg ))rgMap.remove( rg );
				else {
					rg.setActiveStatus( AppConstants.LOC_STATUS_INACTIVE );
					rgMap.put( rg, null );
				}
			}
			
			/*else {
				rg.setActiveStatus( AppConstants.LOC_STATUS_INACTIVE );
				rgMap.put( rg, null );
			}*/
		}
		
		/* If the current section is the basic section for this policy, then remove the risk group details entry from other sections too. */
		if( isCurrentSectionBasic() ) removeRiskGroupDetailsFromOtherSections( sectionId, riskGroupId );
	}
	
	



	/**
	 * Removes the entry with risk group as key from all sections other than the passed one. This method is expected to be 
	 * used as an assistant to <code>removeRiskGroupDetails()</code> method whose job is to remove the entry from the current 
	 * (or passed) section.
	 * 
	 * @param sectionId
	 * @param riskGroupId
	 */
	private void removeRiskGroupDetailsFromOtherSections( int sectionId, String riskGroupId ){
		List<SectionVO> sections = getPolicyDetails().getRiskDetails();
		
		if( Utils.isEmpty( sections ) ) return;
		
		for( SectionVO section : sections ){
			/* Skip the passed section. We are doing this only for the other sections. */
			if( section.getSectionId().equals( sectionId ) ) continue;
			
			/* Get the risk group. It is possible that the risk group is not present in the section. In that case, "null" will
			 * be returned. */
			RiskGroup rg = getRiskGroup( section.getSectionId(), riskGroupId );
			
			/* Get the riskGroupDetails map and remove the entry from it. */
			Map rgMap = getRiskGroupMap( section, null, null );
			if( !Utils.isEmpty( rgMap )  ){
				rgMap.remove( rg );
			}
		}
	}

	public void cascadeNewRiskGroupToSections( RiskGroup rg ){
		//boolean isPARPresent = isSectionPresent( AppConstants.SECTION_ID_PAR );		/* commented unused variable - sonar violation fix */
		
		/* We have to run this logic only if the current section is the basic section. */
		if( isCurrentSectionBasic() ){
			List<SectionVO> sections = getPolicyDetails().getRiskDetails();
			
			/* Copy the risk group into all sections, except the current one. This will take care of even
			 * the scenario of the risk group being added in PL and accidentally set to PAR, because PAR
			 * will anyhow not be there. */
			for( SectionVO section : sections ){
				if( section.getSectionId().equals( getCurrentSectionId() ) ){
					continue;
				}
				
				Map rgMap = section.getRiskGroupDetails();
				if( rgMap == null ){
					rgMap = new com.mindtree.ruc.cmn.utils.Map<RiskGroup, RiskGroupDetails>( RiskGroup.class, RiskGroupDetails.class );
				}
				
				RiskGroupDetails rgd = null;
				
				rgMap.put( rg.copy(), rgd );
				section.setRiskGroupDetails( rgMap );
			}
		}
	}
	/**
	 * If the base section location details modified then update the locations changes in other sections also
	 * @param rg
	 */
	public void updateBaseSectionChanges( RiskGroup rg ){
		
		/* We have to run this logic only if the current section is the basic section. */
		if( isCurrentSectionBasic() ){
			List<SectionVO> sections = getPolicyDetails().getRiskDetails();
			
			/* Copy the risk group into all sections, except the current one. This will take care of even
			 * the scenario of the risk group being added in PL and accidentally set to PAR, because PAR
			 * will anyhow not be there. */
			for( SectionVO section : sections ){
				if( section.getSectionId().equals( getCurrentSectionId() ) ){
					continue;
				}
				
				Map rgMap = section.getRiskGroupDetails();
				if( rgMap == null ){
					rgMap = new com.mindtree.ruc.cmn.utils.Map<RiskGroup, RiskGroupDetails>( RiskGroup.class, RiskGroupDetails.class );
				}
				if(rgMap.containsKey( rg )){
					updateLocDetails(rgMap, rg);
				} 
			}
		}
	}
	
	private void updateLocDetails( Map rgMap, RiskGroup rg ){
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> rgMapTemp = (java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails>)rgMap;
		for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : rgMapTemp.entrySet() ){
			 LocationVO rgKey = (LocationVO) locationEntry.getKey();
			if( rgKey.equals( rg ) ){
				// Mapping for any change in location information 	 
				//rgKey.setToSave( ( (LocationVO) rg ).getToSave() );
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getBuildingName())){
					rgKey.getAddress().setBuildingName(((LocationVO) rg ).getAddress().getBuildingName());
				}
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getOfficeShopNo())){
					rgKey.getAddress().setOfficeShopNo(((LocationVO) rg ).getAddress().getOfficeShopNo());
				}
					if(!Utils.isEmpty(((LocationVO) rg ).getDirectorate())){
					rgKey.setDirectorate(((LocationVO) rg ).getDirectorate());
				}
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getFloor())){
					rgKey.getAddress().setFloor(((LocationVO) rg ).getAddress().getFloor());
				}	
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getStreetName())){
					rgKey.getAddress().setStreetName(((LocationVO) rg ).getAddress().getStreetName());
				}	
				
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getPoBox())){
					rgKey.getAddress().setPoBox(((LocationVO) rg ).getAddress().getPoBox());
				}		

				/* if(!Utils.isEmpty(((LocationVO) rg ).isModified())){
                    rgKey.setModified(((LocationVO) rg ).isModified());
                } */
				if(!Utils.isEmpty(((LocationVO) rg ).getRiskGroupName())){
					rgKey.setRiskGroupName(((LocationVO) rg ).getRiskGroupName());
				}
				break;
			}	
		}
	
	}

	private void setIdToRiskGroup( RiskGroup rg ){
		/* If there is no risk group Id set, add a temporary on generated using a running sequence number for
		 * the section. Be careful not to override the risk group Id if it was already present because if it is
		 * already present, it may be the Id from the database table. */
		if( Utils.isEmpty( rg.getRiskGroupId() ) ){
			rg.setRiskGroupId( "L" + String.valueOf( getCurrentScreen().getNextRiskGroupSequence() ) );
		}

	}

	private void updateKeyValues( Map rgMap, RiskGroup rg ){
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> rgMapTemp = (java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails>)rgMap;
		for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : rgMapTemp.entrySet() ){
			 LocationVO rgKey = (LocationVO) locationEntry.getKey();
			if( rgKey.equals( rg ) ){
				// Mapping for any change in location information 	 
				rgKey.setToSave( ( (LocationVO) rg ).getToSave() );
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getBuildingName())){
					rgKey.getAddress().setBuildingName(((LocationVO) rg ).getAddress().getBuildingName());
				}
			//	if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getOfficeShopNo())){
					rgKey.getAddress().setOfficeShopNo(((LocationVO) rg ).getAddress().getOfficeShopNo());
			//	}
				if(!Utils.isEmpty(((LocationVO) rg ).getDirectorate())){
					rgKey.setDirectorate(((LocationVO) rg ).getDirectorate());
				}
				if(!Utils.isEmpty(((LocationVO) rg ).getOccTradeGroup())){
					rgKey.setOccTradeGroup(((LocationVO) rg ).getOccTradeGroup());
				}
				if(!Utils.isEmpty(((LocationVO) rg ).getFreeZone())){
					rgKey.setFreeZone(((LocationVO) rg ).getFreeZone());
				}
				//if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getFloor())){
					rgKey.getAddress().setFloor(((LocationVO) rg ).getAddress().getFloor());
			//	}	
				//if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getStreetName())){
					rgKey.getAddress().setStreetName(((LocationVO) rg ).getAddress().getStreetName());
				//}	
				
			//	if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getPoBox())){
					rgKey.getAddress().setPoBox(((LocationVO) rg ).getAddress().getPoBox());
			//	}		
                if(!Utils.isEmpty(((LocationVO) rg ).isModified())){
                    rgKey.setModified(((LocationVO) rg ).isModified());
                }

				//Specific handling for PL section as LocationVO is updated with jurisdiction and territory
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getLocOverrideJur())){
					rgKey.getAddress().setLocOverrideJur(((LocationVO) rg ).getAddress().getLocOverrideJur());
				}
				if(!Utils.isEmpty(((LocationVO) rg ).getAddress().getLocOverrideTer())){
					rgKey.getAddress().setLocOverrideTer(((LocationVO) rg ).getAddress().getLocOverrideTer());
				}
				if(!Utils.isEmpty(((LocationVO) rg ).getRiskGroupName())){
					rgKey.setRiskGroupName(((LocationVO) rg ).getRiskGroupName());
				}
				if(!Utils.isEmpty(((LocationVO) rg ).getActiveStatus())){
					rgKey.setActiveStatus(((LocationVO) rg ).getActiveStatus());
				}
				//Added for Informap changes
				rgKey.getAddress().setLatitude(((LocationVO) rg ).getAddress().getLatitude());
				rgKey.getAddress().setLongitude(((LocationVO) rg ).getAddress().getLongitude());
				rgKey.getAddress().setAddressInDetail(((LocationVO) rg ).getAddress().getAddressInDetail());
				rgKey.getAddress().setInforMapStatus(((LocationVO) rg ).getAddress().getInforMapStatus());
				break;
			}	
		}
	}

	/**
	 * Gets the riskGroupDetails map from the SectionVO and returns. If the map inside SectionVO is null, instantiates it before
	 * returning.
	 *  
	 * @param section
	 * @param rg The non-null RiskGroup instance for the creation of a new instance of MTCommon Map if the riskGroupDetails Map is null
	 * @param rgDetails The non-null RiskGroupDetails instance for the creation of a new instance of MTCommon Map if the riskGroupDetails 
	 * Map is null
	 * 
	 * @return
	 */
	private Map getRiskGroupMap( SectionVO section, RiskGroup rg, RiskGroupDetails rgDetails ){
		Map rgMap = section.getRiskGroupDetails();

		if( rgMap == null && !Utils.isEmpty( rg ) && !Utils.isEmpty( rgDetails ) ){
			rgMap = new com.mindtree.ruc.cmn.utils.Map( rg.getClass(), rgDetails.getClass() );
			section.setRiskGroupDetails( rgMap );
		}

		return rgMap;
	}

	/**
	 * Returns the PolicyVO instance representing the current state of the policy with all its sections and risk information.
	 * @return
	 */
	public PolicyVO getPolicyDetails(){
		return aTransactionIsOn ? rbPolicyDetails : policyDetails;
	}

	/**
	 * Sets the PolicyVO instance passed as the current state of the policy. The onus is on the caller to ensure the accuracy
	 * of the data inside PolicyVO instance.
	 * @param policyDetails
	 */
	public void setPolicyDetails( PolicyVO policyDetails ){
		if( !Utils.isEmpty( policyDetails ) ){
			policyDetails.setAppFlow( appFlow );

			//TODO Need to check how to handle in case app flow is resolve refferal 
			if( policyDetails.getAppFlow() == Flow.CREATE_QUO || policyDetails.getAppFlow() == Flow.EDIT_QUO || policyDetails.getAppFlow() == Flow.VIEW_QUO ){
				policyDetails.setIsQuote( true );
			}
			else if(policyDetails.getAppFlow() == Flow.VIEW_POL || policyDetails.getAppFlow() == Flow.AMEND_POL){
				policyDetails.setIsQuote( false );
			}
			if( aTransactionIsOn ){
				this.rbPolicyDetails = policyDetails;
			}
			else{
				this.policyDetails = policyDetails;
			}
	}
	}

	/**
	 * Finds the SectionVO instance in PolicyVO.riskDetails list and returns it.
	 * 
	 * @param currentSectionId
	 * @return
	 */
	private SectionVO getSectionVOFromPolicyVO( int sectionId ){
		List<SectionVO> sectionVOs = getPolicyDetails().getRiskDetails();

		SectionVO finderSection = new SectionVO( RiskGroupingLevel.LOCATION );
		finderSection.setSectionId( sectionId );

		int indexOfSection = sectionVOs.indexOf( finderSection );
		return indexOfSection >= 0 ? sectionVOs.get( sectionVOs.indexOf( finderSection ) ) : null;
	}

	/**
	 * 
	 * Pass the section ID of the current section to get the next 
	 * @param currentSectionId
	 * @return
	 */
	final private UISection getNextScreen( final Integer sectionId ){
		final List<Integer> sectionlist = getSelectedSectionList();
		UISection nextPage;
		if( !Utils.isEmpty( sectionId ) ){

			final int loc = getNextId( sectionlist, sectionId );
			if( loc < sectionlist.size() ){
				final int nextId = sectionlist.get( loc );
				nextPage = sectionsMap.get( nextId );
				//setAsCurrentSection( nextPage.getSectionId() );
				this.currentStep = 1;
			}
			else{
				nextPage = new UISection();
				nextPage.setJspPage( "999" );
				nextPage.setSectionId( 999 );
				//setAsCurrentSection( 999 );
				this.currentStep = 2;
			}

		}
		else{
			final int nextId = sectionlist.get( 0 );
			nextPage = sectionsMap.get( nextId );
			//setAsCurrentSection( nextPage.getSectionId() );
			this.currentStep = 1;
		}

		return nextPage;

	}

	private List<Integer> getSelectedSectionList(){
		final List<Integer> sectionIds = new ArrayList<Integer>();
		for( Map.Entry<Integer, UISection> entry : sectionsMap.entrySet() ){
			final UISection uiSection = entry.getValue();
			if( uiSection.isSectionSelected() ){
				sectionIds.add( entry.getKey() );
			}

		}

		return sectionIds;
	}

	private int getNextId( final List<Integer> sectionlist, final Integer sectionId ){
		int i = 0;
		for( Integer section : sectionlist ){
			if( section.equals( sectionId ) ){
				break;
			}
			i++;
		}

		if( i == sectionlist.size() - 1 || i > sectionlist.size() - 1 ){
			i = 999;
		}
		return i + 1;
	}

	private int getPrevId( final List<Integer> sectionlist, final Integer sectionId ){
		int i = 0;
		for( Integer section : sectionlist ){
			if( section.equals( sectionId ) ){
				break;
			}
			i++;
		}

		if( i <= 0 ){
			i = -1;
		}
		return i - 1;
	}

	/**
	 * This method adds the section to the PolicyVO in the context. The <code>policyDetails</code> is expected to be non-null, ie,
	 * already populated.
	 * @param section
	 */
	private void addSectionToPolicyVO( SectionVO section ){
		List<SectionVO> riskDetailsList = getPolicyDetails().getRiskDetails();

		/* If the sections list is empty, create a new one here. */
		if( Utils.isEmpty( riskDetailsList ) ){
			riskDetailsList = new com.mindtree.ruc.cmn.utils.List<SectionVO>( SectionVO.class );
			getPolicyDetails().setRiskDetails( riskDetailsList );
		}

		/* If the sections list is not empty, then add this section if it is not already present. */
		if( !riskDetailsList.contains( section ) ){
			//fillLocationsIntoSection( section );
			riskDetailsList.add( section );
		}

		/* This setting back to policyDetails is necessary if the list was instantiated. */
		getPolicyDetails().setRiskDetails( riskDetailsList );
	}

/*	*//**
	 * This method picks up locations from PAR or PL and fills in the riskGroupDetails map in the section. 
	 * @param section
	 *//*
	private void fillLocationsIntoSection( SectionVO section ){
		
		 Do not initialize the SectionVO with locations from PAR or PL, if this is an
		 * Edit Quote (or Resolving Referrals) flow, or any case where the quote was already
		 * created earlier and we are recreating the PolicyContext with data from the DB. 
		 * The reason is that in this case, the locations for the section will already be
		 * available in the database and would have been loaded. 
		if( !isNewQuote() ) return;
		
		int sectionId = section.getSectionId();

		boolean isPARPresent = isSectionPresent( SECTION_ID_PAR );
		boolean isPLPresent = isSectionPresent( SECTION_ID_PL );

		 No handling required for PAR. The locations for PAR will be added during "Add Location" flow. 
		if( sectionId == SECTION_ID_PL ){
			 If PAR is present, then fill with LocationVO instances from PAR. Else, don't do anything. 
			if( isPARPresent ){
				initializeSectionWithLocationsFrom( SECTION_ID_PAR, section );
			}
		}
		else if( sectionId == SECTION_ID_MONEY || sectionId == SECTION_ID_WC  || sectionId == SECTION_ID_BI || sectionId == SECTION_ID_GPA){ // modified by Anveshan
			 If PAR is present, then fill with LocationVO instances from PAR. 
			if( isPARPresent ){
				initializeSectionWithLocationsFrom( SECTION_ID_PAR, section );
			}
			 Else If PL is present, then fill with LocationVO instances from PL. 
			else if( isPLPresent ){
				initializeSectionWithLocationsFrom( SECTION_ID_PL, section );
			}
		}
	}*/

	/**
	 * This method gets the SectionVO instance for the passed <code>fromSectionId</code> so that the list of LocationVOs
	 * can be retrieved and used to initialise <code>toSection</code>.
	 * @param fromSectionId
	 * @param toSection
	 *//*
	private void initializeSectionWithLocationsFrom( int fromSectionId, SectionVO toSection ){
		SectionVO fromSection = getSectionDetails( fromSectionId );

		initializeSectionWithLocationsFrom( fromSection, toSection );
	}*/

	/**
	 * Copies locations from <code>fromSection</code>'s riskDetails Map to <code>toSection</code>'s riskDetails Map. The values
	 * in the map are initialized to null.
	 *  
	 * @param fromSection
	 * @param toSection
	 *//*
	private void initializeSectionWithLocationsFrom( SectionVO fromSection, SectionVO toSection ){
		java.util.Set<RiskGroup> locations = (java.util.Set<RiskGroup>) fromSection.getRiskGroupDetails().keySet();

		 Add all the locations to the section. 
		Map<RiskGroup, RiskGroupDetails> riskGroupDetails = new com.mindtree.ruc.cmn.utils.Map<RiskGroup, RiskGroupDetails>( RiskGroup.class, RiskGroupDetails.class );
		java.util.Iterator<RiskGroup> iter = locations.iterator();
		while( iter.hasNext() ){
			RiskGroup rg = iter.next();
			RiskGroupDetails rgd = null;

			 Making a copy of the RiskGroup instance so that a change to it in the toSection handling doesn't affect
			 * the fromSection RiskGroup instance. 
			riskGroupDetails.put( rg.copy(), rgd );
		}

		toSection.setRiskGroupDetails( riskGroupDetails );
	}*/
	
	/**
	 * Returns the first selected section.
	 * @return
	 */
	public Integer firstSection(){
		List<Integer> sections = getSelectedSectionList();
		if( !Utils.isEmpty( sections ) ){
			return sections.get( 0 );
		}
		else{
			throw new BusinessException( "pas.cmn.sectionNotSelected", null, "Attempt made to get the first section before sections have been selected" );
		}
	}

	/**
	 * Checks if the current section is the first section.
	 * @return true, if the current section is the first section; false, otherwise
	 */
	public boolean isFirstSection(){
		List<Integer> sections = getSelectedSectionList();
		if( !Utils.isEmpty( sections ) && getCurrentSectionId().equals( sections.get( 0 ) ) ){
			return true;
		}
		
		else return false;
	}
	
	/**
	 * Returns the last selected section
	 * @return
	 */
	public Integer lastSection(){
		List<Integer> sections = getSelectedSectionList();
		if( !Utils.isEmpty( sections ) ){
			return sections.get( sections.size() - 1 );
		}
		else{
			throw new BusinessException( "pas.cmn.sectionNotSelected", null, "Attempt made to get the first section before sections have been selected" );
		}
	}
	
	/**
	 * Checks if the current section is the last section.
	 * @return true, if the current section is the last section; false, otherwise
	 */
	public boolean isLastSection(){
		List<Integer> sections = getSelectedSectionList();
		if( getCurrentSectionId().equals( sections.get( sections.size() - 1 ) ) ){
			return true;
		}
		
		else return false;
	}
	
	/**
	 * Checks if the section represented by the currentSectionId is present in the PolicyVO.riskDetails list.
	 * @param currentSectionId
	 * @return
	 */
	public boolean isSectionPresent( int sectionId ){
		List<Integer> selSections = getSelectedSectionList();
		
		return Utils.isEmpty( selSections ) ? false : selSections.contains( sectionId );
	}

	/**
	 * Checks if this section is allowed to add a location independently. To allow a section to add locations, add the section
	 * Id to appconfig.properties against the <code>ADD_LOCATION_ALLOWED_SECTIONS</code> property.
	 * @return
	 */
	public boolean isAddLocationAllowed(){
		boolean allowed = false;
		String[] sections = Utils.getMultiValueAppConfig( AppConstants.APP_CONFIG_ADD_LOC_ALLOWED_SEC );
		if( !Utils.isEmpty( sections ) ){
			for( String section : sections ){
				if( getCurrentSectionId().toString().equals( section ) ){
					allowed = true;
					break;
				}
			}
		}
		
		return allowed;
	}

	private void saveLocation( final int sectionId, final int riskGroupId ){

		final UISection uiSection = sectionsMap.get( sectionId );
		Map<Integer, UIRiskGroupStatus> map = uiSection.getStatusValues();
		UIRiskGroupStatus uiRiskGroupStatus;
		if( Utils.isEmpty( map ) ){
			map = new LinkedHashMap<Integer, UIRiskGroupStatus>();
		}

		if( map.containsKey( riskGroupId ) ){
			uiRiskGroupStatus = map.get( riskGroupId );
			uiRiskGroupStatus.setIsSaved( true );
			uiRiskGroupStatus.setRulesExe( true );
			map.put( riskGroupId, uiRiskGroupStatus );
		}
		else{
			uiRiskGroupStatus = new UIRiskGroupStatus();
			uiRiskGroupStatus.setIsSaved( true );
			uiRiskGroupStatus.setRulesExe( true );
			map.put( riskGroupId, uiRiskGroupStatus );
		}

		uiSection.setStatusValues( map );
		sectionsMap.put( sectionId, uiSection );
	}

	private void addLocation( int sectionId, int riskGroupId ){

		final UISection uiSection = sectionsMap.get( sectionId );
		Map<Integer, UIRiskGroupStatus> map = uiSection.getStatusValues();
		if( Utils.isEmpty( map ) ){
			map = new LinkedHashMap<Integer, UIRiskGroupStatus>();
		}

		if( !map.containsKey( riskGroupId ) ){
			final UIRiskGroupStatus uiRiskGroupStatus = new UIRiskGroupStatus();
			uiRiskGroupStatus.setIsSaved( false );
			uiRiskGroupStatus.setPremiumCalculated( false );
			uiRiskGroupStatus.setRulesExe( false );
			map.put( riskGroupId, uiRiskGroupStatus );
		}
		uiSection.setStatusValues( map );
		sectionsMap.put( sectionId, uiSection );
	}

	private void deleteLocation( int sectionId, int riskGroupId ){

		final UISection uiSection = sectionsMap.get( sectionId );

		Map<Integer, UIRiskGroupStatus> map = uiSection.getStatusValues();

		if( !Utils.isEmpty( map ) && map.containsKey( riskGroupId ) ){
			map.remove( riskGroupId );
			uiSection.setStatusValues( map );
			sectionsMap.put( sectionId, uiSection );
		}
	}

	private void premiumCalculated( int sectionId, int riskGroupId ){
		final UISection uiSection = sectionsMap.get( sectionId );

		Map<Integer, UIRiskGroupStatus> map = uiSection.getStatusValues();

		if( !Utils.isEmpty( map ) && map.containsKey( riskGroupId ) ){
			final UIRiskGroupStatus uiRiskGroupStatus = map.get( riskGroupId );
			uiRiskGroupStatus.setPremiumCalculated( true );
			map.put( riskGroupId, uiRiskGroupStatus );
		}
	}

	/**
	 * Indicates if this is a new quote flow.
	 * @return <code>true</code> if it is a new quote flow; <code>false</code> otherwise
	 */
	public boolean isNewQuote(){
		return newQuote;
	}

	/**
	 * Set the flag that this flow is for a new quote generation
	 * @param newQuote <code>true</code> if it is a new quote flow; <code>false</code> otherwise
	 */
	public void setNewQuote( boolean newQuote ){
		this.newQuote = newQuote;
	}

	public Flow getAppFlow() {
		if( !Utils.isEmpty( this.getPolicyDetails() ) && !this.getPolicyDetails().getAppFlow().equals( this.appFlow )){
			this.appFlow = this.getPolicyDetails().getAppFlow();
		}else if( !Utils.isEmpty( this.getCommonDetails() ) && !this.getCommonDetails().getAppFlow().equals( this.appFlow )){
			this.appFlow = this.getCommonDetails().getAppFlow();
		}
		return appFlow;
	}
	
	public Integer getStatus() {
		Integer status = AppConstants.QUOTE_PENDING;
		if( !Utils.isEmpty( this.getPolicyDetails() ) ){
			status = this.getPolicyDetails().getStatus();
		}else if( !Utils.isEmpty( this.getCommonDetails() )){
			status = this.getCommonDetails().getStatus();
		}
		return status;
	}
	
	public LOB getLOB() {
		LOB lob = LOB.SBS;
		if( !Utils.isEmpty( this.getCommonDetails() )){
			lob = this.getCommonDetails().getLob();
		}
		return lob;
	}

	public void setAppFlow(Flow appFlow) {
		this.appFlow = appFlow;
		
		if( !Utils.isEmpty( this.getPolicyDetails() ) ){
			this.getPolicyDetails().setAppFlow( appFlow );
		}
	}



	public boolean isSectionVOsCreationDone(){
		return sectionVOsCreationDone;
	}



	public void setSectionVOsCreationDone( boolean sectionVOsCreationDone ){
		this.sectionVOsCreationDone = sectionVOsCreationDone;
	}



	/**
	 * @return the taskDetails
	 */
	public TaskVO getTaskDetails(){
		// SBS
		if(!Utils.isEmpty( policyDetails )){
			return getPolicyDetails().getTaskDetails();
		}	
		//Home/Travel
		if(!Utils.isEmpty( commonDetails )){
			return getCommonDetails().getTaskDetails();
		}
		return null;
	}

	/**
	 * @param taskDetails the taskDetails to set
	 */
	public void setTaskDetails( TaskVO taskDetails ){
		// For home/travel
		if(!Utils.isEmpty( getCommonDetails() )){
			getCommonDetails().setTaskDetails( taskDetails );
		}
		//For SBS
		if(!Utils.isEmpty( getPolicyDetails())){
			getPolicyDetails().setTaskDetails( taskDetails );
		}
	}


	

	/**
	 * This method to set Common VO from the session
	 * @param commonVO
	 */
	public void setCommonDetails( CommonVO commonVO ){

		if( !Utils.isEmpty( commonVO ) ){
			commonVO.setAppFlow( appFlow );

			//TODO Need to check how to handle in case app flow is resolve refferal 
			if( commonVO.getAppFlow() == Flow.CREATE_QUO || commonVO.getAppFlow() == Flow.EDIT_QUO || commonVO.getAppFlow() == Flow.VIEW_QUO ){
				commonVO.setIsQuote( true );
			}
			else if(commonVO.getAppFlow() == Flow.VIEW_POL || commonVO.getAppFlow() == Flow.AMEND_POL){
				commonVO.setIsQuote( false );
			}
			if( aTransactionIsOn ){
				this.rbCommonDetails = commonVO;
			}
			else{
				this.commonDetails = commonVO;
			}
		}
	
	}
	
	/**
	 * This method returns common VO based on transaction is alive or not.
	 * @return
	 */
	public CommonVO getCommonDetails(){
		return aTransactionIsOn ? rbCommonDetails : commonDetails;
	}



	public void setCoverDetails( CoverDetails coverDetails ){
		this.coverDetails = coverDetails;
	}



	public CoverDetails getCoverDetails(){
		return coverDetails;
	}

	
	//PHASE 3 CHANGE START
	/**
	 * @return the refDataTextField
	 */
	public Map<String, Map<String, String>> getRefDataTextField() {
		return refDataTextField;
	}

	/**
	 * @param refDataTextField the refDataTextField to set
	 */
	public void setRefDataTextField(
			Map<String, Map<String, String>> refDataTextField) {
		this.refDataTextField = refDataTextField;
	}
	//PHASE 3 CHANGE END



	/**
	 * @return the staffDetailsVO
	 */
	public List<StaffDetailsVO> getStaffDetailsVO() {
		return staffDetailsVO;
	}



	/**
	 * @param list the staffDetailsVO to set
	 */
	public void setStaffDetailsVO(List<StaffDetailsVO> list) {
		this.staffDetailsVO = list;
	}
}
