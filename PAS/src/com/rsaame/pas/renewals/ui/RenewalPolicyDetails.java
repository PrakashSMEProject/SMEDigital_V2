package com.rsaame.pas.renewals.ui;

import java.util.Arrays;
import java.util.Map;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1006438
 * This class builds the policyVO for renewal quote
 */

public class RenewalPolicyDetails{

	/**
	 * @param linkingId
	 * @return PolicyVO
	 * This method created the policyVO for renewal quote
	 */
	public PolicyVO createPolicyObject( Long linkingId ){
		PolicyContext renPolContext = new PolicyContext( Flow.RENEWAL );
		PolicyContextUtil.loadAllSectionsForPolicyType( null, renPolContext );
		PolicyVO policy = new PolicyVO();
		renPolContext.setPolicyDetails( policy );
		PolicyVO policyDetails = null;
		LoadExistingInputVO existingInputVO = new LoadExistingInputVO();
		//existingInputVO.setPolLinkingId( Long.valueOf( linkingId ) );
		existingInputVO.setPolLinkingId( linkingId );
		existingInputVO.setEndtId( (long) 0 );
		existingInputVO.setQuote( true );
		existingInputVO.setAppFlow( Flow.RENEWAL );
		existingInputVO.setPolicyStatus( Integer.parseInt( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) ) );
		existingInputVO.setSectionId( AppConstants.SECTION_ID_GEN_INFO );
		
		policyDetails = (PolicyVO) TaskExecutor.executeTasks( AppConstants.GENERAL_INFO_FETCH, existingInputVO );
		policyDetails.setAppFlow( Flow.RENEWAL );
		policyDetails.setIsQuote( true );
		policyDetails = (PolicyVO) TaskExecutor.executeTasks( AppConstants.SET_PRE_PACKAGE_FLAG, policyDetails );
		
		//policyDetails.setPolLinkingId( new Long( linkingId ) );
		policyDetails.setPolLinkingId( linkingId );
		policyDetails.setEndtId( (long) 0 );
		renPolContext.setPolicyDetails( policyDetails );
		existingInputVO.setPolicyStatus( policyDetails.getStatus() );
		SectionList output = (SectionList) TaskExecutor.executeTasks( AppConstants.FETCH_SELECTED_SECTIONS, existingInputVO );

		/* Set the selected sections to the policy context. */
		if( !Utils.isEmpty( output ) && !Utils.isEmpty( output.getSelectedSec() ) ){
			Integer sectionIds[] = CopyUtils.toArray( output.getSelectedSec() );
			Arrays.sort( sectionIds );
			renPolContext.populateSelectedSec( sectionIds );
		}

		/**
		 * START :Changes made to create sectionVOS for selected risk sections. This has been done so that SectionVOs are available as soon as 
		 * general info is loaded in cases where quote/policy is revisited.
		 */
		/* SectionVOs for all selected sections and add to the PolicyVO. */
		createSectionsInThePolicy( renPolContext );
		fetchSectionDetails(renPolContext);
		System.out.println( "end" );
		UserProfile usreProfile= new UserProfile();
		renPolContext.getPolicyDetails().setLoggedInUser( usreProfile );
		return renPolContext.getPolicyDetails();
	}

	public void  fetchSectionDetails(PolicyContext renPolContext){
		//Integer basicSectionId = renPolContext.getBasicSectionId();
		Integer[] sectionIds = renPolContext.getAllSelectedSections();
		for( Integer sectionId : sectionIds ){
			LoadExistingInputVO input = constructInput(renPolContext, sectionId );
			SectionVO section = loadSectionData( input );
			replaceSection( renPolContext, section );
		}
	}


	/**
	 * @param policyContext
	 * @param sectionId
	 * @return
	 * This method creates the VO to be used to load the existing data form the database for given linking Id
	 */
	private LoadExistingInputVO constructInput(PolicyContext policyContext, Integer sectionId ){
		LoadExistingInputVO existingInputVO = new LoadExistingInputVO();
		
		if( !Utils.isEmpty( policyContext ) ){
			/* If a section Id has been passed, that gets the highest priority. Then to "jumpToSectionId" passed in the request and
			 * finally, to the current section Id from the Policy Context. */
			existingInputVO.setSectionId( sectionId );
			
			if( !Utils.isEmpty( policyContext.getPolicyDetails() ) ){
				existingInputVO.setPolLinkingId( policyContext.getPolicyDetails().getPolLinkingId() );
				existingInputVO.setEndtId( policyContext.getPolicyDetails().getEndtId() );
				existingInputVO.setQuote( policyContext.getPolicyDetails().getIsQuote() );
				existingInputVO.setIsPrepackaged( policyContext.getPolicyDetails().getIsPrepackaged() );
				existingInputVO.setBasicSectionId( com.rsaame.pas.svc.utils.PolicyUtils.getBasicSectionId( policyContext.getPolicyDetails() ) );
				existingInputVO.setPolicyStatus( policyContext.getPolicyDetails().getStatus() );
				existingInputVO.setTariffCode(policyContext.getPolicyDetails().getScheme().getTariffCode());
				existingInputVO.setAppFlow(Flow.RENEWAL);
			}
		}
		return existingInputVO;
	}
	
	/**
	 * Calls SectionSvc.loadSectionData() to get the populated SectionVO for the section with all the saved location data.
	 * 
	 * @param input
	 * @return 
	 */
	private SectionVO loadSectionData( LoadExistingInputVO input ){

		/* Calling loadSectionData method of SectionSvc */
		SectionVO section = (SectionVO) TaskExecutor.executeTasks( AppConstants.LOADSECTIONSDATA, input );
		Map <? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap=section.getRiskGroupDetails();
		for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet()) {
			RiskGroupDetails riskDetails = entry.getValue();
			if(!Utils.isEmpty(riskDetails)){
				riskDetails.setPremium( null );
			}	
		}
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
	 * Creates SectionVO instances for all selected sections.
	 * @param pc
	 */
	private void createSectionsInThePolicy( PolicyContext pc ){
		//if( !pc.isSectionVOsCreationDone() ){
			Integer[] sectionIds = pc.getAllSelectedSections();

			for( Integer sectionId : sectionIds ){
				SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION ); /* TODO This may not be correct because there can be a 
																					 * RiskGroupLevel other than LOCATION. */
				section.setSectionId( sectionId );
				section.setSectionName( SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", sectionId ) );

				pc.addSection( section );
			}

			/* Set the flag to indicate that SectionVO creation for all sections is complete. */
			//pc.setSectionVOsCreationDone( true );
		//}
	}

}
