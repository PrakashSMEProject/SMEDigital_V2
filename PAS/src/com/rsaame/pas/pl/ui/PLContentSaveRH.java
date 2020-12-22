/**
 * 
 */
package com.rsaame.pas.pl.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.ui.cmn.SaveSectionRH;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PLUWDetails;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWDetails;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1017935
 *
 */
public class PLContentSaveRH extends SaveSectionRH {
	
	/*
	 * Method performing the validation
	 * 
	 */
	private void validate(RiskGroup rg,PublicLiabilityVO publicLiabilityVO, UWDetails uwDetails,
			UWQuestionsVO questionsVO, SectionVO sectionVO) {
		
		TaskExecutor.executeTasks( "PL_PAGE_LOCATION", (LocationVO) rg );
		TaskExecutor.executeTasks("PL_PAGE_CORE", publicLiabilityVO);
		TaskExecutor.executeTasks( "PL_PAGE_COMMISION", sectionVO );		
	}

	@Override
	protected void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section, BaseVO baseVO ){
		PublicLiabilityVO plVO = (PublicLiabilityVO) rgd;
		validate( rg, plVO, plVO.getUwDetails(), plVO.getUwQuestions(), section );
	}

	@Override
	protected RiskGroup mapRiskGroup( HttpServletRequest request ){	
		return BeanMapper.map( request, LocationVO.class );
	}

	@Override
	protected RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request ){
		PublicLiabilityVO publicLiabilityVO = BeanMapper.map( request, PublicLiabilityVO.class );
		PLUWDetails detailsVO = BeanMapper.map( request, PLUWDetails.class );
		UWQuestionsVO questionsVO = BeanMapper.map( request, UWQuestionsVO.class );
		
		//publicLiabilityVO.setPremium( getPremiumVO( request ) );
		publicLiabilityVO.setUwDetails(detailsVO);
		publicLiabilityVO.setUwQuestions(questionsVO);
		
		return publicLiabilityVO;
	}
	
	/*
	 * Oman Customization 
	 * (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.SaveSectionRH#executeSaveTasks(java.lang.Integer, com.rsaame.pas.ui.cmn.PolicyContext)
	 * This method is overwritten since the the implementation is different for Oman, incase LOI is changed for one location the same to be cascaded to other locations also
	 * 
	 */
	@Override
	protected PolicyVO executeSaveTasks( Integer sectionId, PolicyContext policyContext ){

		if( Utils.getSingleValueAppConfig( AppConstants.CASCADELIMIT ).equalsIgnoreCase( AppConstants.FALSE ) ){

			super.executeSaveTasks( sectionId, policyContext );
		}
		else{
			String svcIdentifier = SectionRHUtils.getSectionSaveSvcIdentifier( sectionId, policyContext );
			SectionVO section = PolicyUtils.getSectionVO( policyContext.getPolicyDetails(), SvcConstants.SECTION_ID_PL );
			//Get the location to be saved.
			LocationVO locationToProcess = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
			//Get the risk group to be saved
			PublicLiabilityVO plToProcess = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetailsForProcessing( policyContext.getPolicyDetails(), SvcConstants.SECTION_ID_PL );
			//Save the current location
			TaskExecutor.executeTasks( svcIdentifier, policyContext.getPolicyDetails() );
			Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = section.getRiskGroupDetails();
			LocationVO locationDetails = null;
			/*
			 * Loop through all the location
			 */
			for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
				locationDetails = (LocationVO) locationEntry.getKey();
				PublicLiabilityVO plVo = (PublicLiabilityVO) locationEntry.getValue();
				//If the location is not the current location and if the LOI is changed save the details. This is required Only for Oman
				if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
						Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30"))
				{
					if( hasPlLocDataChanged(locationToProcess, plToProcess,locationDetails, plVo) ){
						/*
						 * The following logic makes sure that the other location will get save and current location referral and page reload remains unchanged
						 */
						plVo.setIndemnityAmtLimit( plToProcess.getIndemnityAmtLimit() );
						plVo.getSumInsuredDets().setDeductible(plToProcess.getSumInsuredDets().getDeductible());
						plVo.setSumInsuredBasis(plToProcess.getSumInsuredBasis());
						
						if( !Utils.isEmpty( plVo.getPremium() ) ){
							plVo.getPremium().setPremiumAmt( 0.0 );
						}
						else{
							plVo.setPremium( new PremiumVO() );
							plVo.getPremium().setPremiumAmt( 0.0 );
						}
						locationToProcess.setToSave( false );
						locationDetails.setToSave( true );
						TaskExecutor.executeTasks( svcIdentifier, policyContext.getPolicyDetails() );
						locationDetails.setToSave( false );
						locationToProcess.setToSave( true );
					}
				}
			}

		}
		
		return policyContext.getPolicyDetails();

	}

	private boolean hasPlLocDataChanged(LocationVO locationToProcess,
			PublicLiabilityVO plToProcess, LocationVO locationDetails,
			PublicLiabilityVO plVo) {
		return ( !Utils.isEmpty( locationDetails ) && !Utils.isEmpty( plVo ) && !Utils.isEmpty( locationDetails.getRiskGroupId() )
				&& !Utils.isEmpty( locationToProcess.getRiskGroupId() ) && !Utils.isEmpty( plToProcess.getIndemnityAmtLimit() ) && !Utils.isEmpty( plVo.getIndemnityAmtLimit() ) 
				&& !Utils.isEmpty( plToProcess.getSumInsuredDets().getSumInsured() ) && !Utils.isEmpty( plVo.getSumInsuredDets().getSumInsured() ) 
				&& !Utils.isEmpty( plToProcess.getSumInsuredDets().getDeductible() ) && !Utils.isEmpty( plVo.getSumInsuredDets().getDeductible() )
				&& !Utils.isEmpty( plToProcess.getSumInsuredBasis() ) && !Utils.isEmpty( plVo.getSumInsuredBasis() ) )
				&& ( !locationDetails.getRiskGroupId().equals( locationToProcess.getRiskGroupId() )
						&& (!plToProcess.getIndemnityAmtLimit().equals(plVo.getIndemnityAmtLimit()  )
						//|| !plToProcess.getSumInsuredDets().getSumInsured().equals(plVo.getSumInsuredDets().getSumInsured() )
						|| !plToProcess.getSumInsuredDets().getDeductible().equals(plVo.getSumInsuredDets().getDeductible() )
						|| !plToProcess.getSumInsuredBasis().equals(plVo.getSumInsuredBasis() )) );
	}

	@Override
	protected void sectionLogic( PolicyContext policyContext ){
		/*PolicyVO policyVO = policyContext.getPolicyDetails();
		UWQuestionsVO uwQuestionsVO = new UWQuestionsVO();
		
		SectionVO parSectionVO = com.rsaame.pas.svc.utils.PolicyUtils.getSectionVO( policyVO, Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) ) );
		RiskGroup currRG = com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupForProcessing( parSectionVO );
		ParVO currRGD = (ParVO) com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( currRG, parSectionVO );
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> rgdMap = parSectionVO.getRiskGroupDetails();
		
		UWQuestionsVO currentUWQ = currRGD.getUwQuestions();
		if(!Utils.isEmpty( currentUWQ )){
		if(currentUWQ.isCascaded()){
		   for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : rgdMap.entrySet() ){
				ParVO parVO = (ParVO) locationEntry.getValue();
				if( !locationEntry.getKey().equals( (LocationVO) currRG )){
					if( !Utils.isEmpty( parVO )){
						uwQuestionsVO  = CopyUtils.copySerializableObject( currentUWQ );
						uwQuestionsVO.setCascaded( false );
						parVO.setUwQuestions( uwQuestionsVO );
					}
					
				}
			}
		}
		
		log.info( " PolicyVO after update ["+ policyVO+"]" );*/
		
	}
}
