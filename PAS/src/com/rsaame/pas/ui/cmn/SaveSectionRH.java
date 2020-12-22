package com.rsaame.pas.ui.cmn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.AppTypePrem;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public abstract class SaveSectionRH implements IRequestHandler{
	public static final Logger log = Logger.getLogger( SaveSectionRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response responseObj = new Response();
		
		
		/* Get the PolicyContext and start the transaction. */
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );

		/*
		 * First, create the VOs needed for the processing of the SAVE
		 * operation.
		 */

		RiskGroup rg = mapRiskGroup( request );
		RiskGroupDetails rgd = null;
		policyContext.startTransaction();
		PolicyVO policyVO = policyContext.getPolicyDetails();
		int currentSectionId = policyContext.getCurrentSectionId();
		/*
		 * Set the commission value to the section. This should have got mapped
		 * to the risk group details.
		 */
		SectionVO currentSection = policyContext.getSectionDetails( currentSectionId );

		/* If data has not changed do no save it again */
		String actionName = (String)request.getAttribute( AppConstants.ACTIONNAME);
		if(!Utils.isEmpty( actionName ) && !actionName.equalsIgnoreCase( "NEXT" )){
			if( !isDataChanged( rg, rgd, currentSection, policyContext ) ){
					currentSection.setCommission( rgd.getCommission() );
					String permiumJson = getSectionPremium( policyContext, rg.getRiskGroupId() );
					AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
					responseObj.setData( permiumJson );
					return responseObj;
			}
		}
			/* check if any data(rows) have been deleted and set the corresponding flags, 
		 * Since the deleted records do not come as part of request we will construct them as part of rgd
		 * here to be sent to DAO layer for actual deletion.
		 */
		if( !SectionRHUtils.isReadOnlyMode( policyContext, request ) ){

			 rgd = mapRiskGroupDetails( request );
			 setRowToBeDeletedFlag( rg, rgd, currentSection, policyContext );	
			/*
			 * We need to identify here if this location is a new one. The reason is
			 * that we will need to cascade this location, after saving, if this is
			 * the basic section for the policy.
			 */
			boolean isNewLocation = Utils.isEmpty( rg.getRiskGroupId() );

			( (LocationVO) rg ).setModified( false );

			rg.setToSave( true );
			rg.setActiveStatus( AppConstants.LOC_STATUS_ACTIVE );

			/* Start the transaction on policy Context */

			/* check if the location has been modified */
			if( !isNewLocation ){
				String currentLocationVO = SvcUtils.populateTotFieldDetails( (LocationVO) rg, AppTypePrem.BUILDING, currentSectionId );

				LocationVO locationVO = (LocationVO) policyContext.getRiskGroup( currentSectionId, rg.getRiskGroupId() );

				if( !Utils.isEmpty( locationVO ) ){
					String contextLocationVO = SvcUtils.populateTotFieldDetails( locationVO, AppTypePrem.BUILDING, currentSectionId );

					if( !currentLocationVO.equals( contextLocationVO ) ){

						( (LocationVO) rg ).setModified( true );
						/* set the location modified flag here */
					}
				}
			}

			policyContext.addRiskGroupDetails( currentSectionId, rg, rgd );

			currentSection.setCommission( rgd.getCommission() );

			/* Run all validations here. */
			validate( rg, rgd, currentSection ,policyVO);
			setReloadData(request, 
					currentSection, 
					policyContext.getRiskGroup( policyContext.getCurrentSectionId(), rg.getRiskGroupId() ),
					policyContext.getRiskGroupDetails( policyContext.getCurrentSectionId(), rg ), 
					policyContext.getPolicyDetails(),policyContext);
			
			/* Cascade UW Questions response for current section in case 
			 * a. UW Questions is enabled for the section
			 * b. Cascaded UW questions is selected by the user */
			cascadeUWQuestionsForCurrSection( policyContext );

			/*
			 * Now, execute the tasks. The opType will be used as the identifier for
			 * the tasks.
			 */
			String action = (String) request.getAttribute( "opType" );
			String actionIdentifier = (String) request.getAttribute( AppConstants.REQ_ATTR_CURR_ACTION );

			if( actionIdentifier.equalsIgnoreCase( "CALCULATE_PREMIUM" ) ){
				SectionRHUtils.executeRatingTask( action, policyVO );
			}
			else{
				/* First, execute the referral tasks. */

				if( !SectionRHUtils.executeReferralTask( responseObj, action, policyVO, actionIdentifier ) ){ 
					return responseObj;
				}
			}
			/* Since referrals are not there, execute the SAVE operation. */
			policyVO = executeSaveTasks( currentSectionId, policyContext );
			
			/*The below call is added to handle referral messages save in temporary table. * Earlier the referral
			 * messages save was handled by respective section saveDAO. But it has been moved from there because 
			 * referral message save was not happening  in second edit scenario due to transaction roll back.
			 Here , Referral save handling is not required for General Info as it is taken care specifically*/
			
			if(!Utils.isEmpty( currentSectionId) && currentSectionId!=AppConstants.SECTION_ID_GEN_INFO){
				
				/*Start of ticket 137704 */
				UserProfile userProfile = (UserProfile) request.getSession()
						.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
				int userID=0;
				String role=null;
				if (!Utils.isEmpty(userProfile)) {
					 userID=userProfile.getRsaUser().getUserId();
						role=DAOUtils.getUserRole(policyVO);
				}
				DataHolderVO<List> inputData=prepareInputForReferalSave(policyVO,currentSectionId,userID, role);
				/*End of ticket 137704 */
				TaskExecutor.executeTasks("HANDLE_REFERRAL_MESSAGES", inputData );
				
				/*Below code has been added to set the save flag of location to false after successful save. Earlier the 
				 *same was achieved in BaseSectionSaveDAO . But it was removed from BaseSectionSaveDAO as part of 
				 *consolidated referral Fix*/
				SectionVO section = PolicyUtils.getSectionVO( policyVO,currentSectionId );
				LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
				locationDetails.setToSave( false );
			}
			
			
			
			/*
			 * The SAVE operation is successful. Hence, (a) Set the resultant
			 * PolicyVO to the PolicyContext and commit the changes to
			 * PolicyContext.
			 */
			policyContext.setPolicyDetails( policyVO );

			/*
			 * delete any records from context 
			 */
			removeDeletedRowsFromContext( rg, rgd, currentSection, policyContext );
			/*
			 * This will perform any section specific logic if necessary, else an empty implementation will be present
			 */
			sectionLogic( policyContext );
			
			/*
			 * (b) Cascade this location to all sections, if this is a new location.
			 * The reason why "rg" is not being directly passed here is that the
			 * risk group Id would have been updated in the DAO and there is no
			 * necessity that the same RiskGroup instance, which was mapped in this
			 * RH, has been returned.
			 */
			if( isNewLocation ){
				policyContext.cascadeNewRiskGroupToSections( policyContext.getRiskGroup( currentSectionId, (String) ThreadLevelContext.get( "RISK_GROUP_ID" ) ) );
			}
			else{
				if( policyVO.getAppFlow().equals( Flow.CREATE_QUO ) || policyVO.getAppFlow().equals( Flow.EDIT_QUO ) ){
					policyContext.updateBaseSectionChanges( policyContext.getRiskGroup( currentSectionId, (String) ThreadLevelContext.get( "RISK_GROUP_ID" ) ) );
				}
			}

			/*
			 * (c) No exception was thrown. Hence, the processing was successful.
			 * Set the success message.
			 */
		}
		else{
			policyContext.getRiskGroupDetails( currentSectionId, rg );
			setReloadData(request, 
					currentSection, 
					rg,
					policyContext.getRiskGroupDetails( policyContext.getCurrentSectionId(), rg ), 
					policyContext.getPolicyDetails(),policyContext);
		}

		policyContext.commit();

		String permiumJson = getSectionPremium( policyContext, rg.getRiskGroupId() );

		if( !Utils.isEmpty( rg.getRiskGroupId() ) ){
			AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
			/*Identifying if location is added in current section or added in basic section*/
			AppUtils.isLocationAddedInCurrentSection( request, Integer.valueOf( rg.getRiskGroupId() ) );
		}

		responseObj.setData( permiumJson );

		return responseObj;

	}

	/*
	 * To be overridden in the subclasses with specific logic
	 * 
	 */
	protected void removeDeletedRowsFromContext( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		// TODO Auto-generated method stub	
	}

	/*
	 * To be overridden in the subclasses with specific logic
	 * 
	 */
	protected void setRowToBeDeletedFlag( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){
		log.debug( "setRowToBeDeletedFlag is done" );
	}

	/*
	 * This method will be overridded by the respective section
	 * (This has been added as part of performance
	 */
	protected boolean isDataChanged( RiskGroup rg, RiskGroupDetails rgd, SectionVO currentSection, PolicyContext policyContext ){

		return true;
	}

	private void setReloadData( HttpServletRequest request, SectionVO currentSection, RiskGroup riskGroup, RiskGroupDetails riskGroupDetails, PolicyVO policyDetails,
			PolicyContext policyContext ){
		AppUtils.setSectionPageDataForJSON( request, 
				currentSection, 
				policyContext.getRiskGroup( policyContext.getCurrentSectionId(), riskGroup.getRiskGroupId() ),
				policyContext.getRiskGroupDetails( policyContext.getCurrentSectionId(), riskGroup ), 
				policyContext.getPolicyDetails() );
		
	}

	private String getSectionPremium( PolicyContext policyContext, String riskGroupId ){
		Integer sectionId = policyContext.getCurrentSectionId();
		RiskGroupDetails rgd = policyContext.getRiskGroupDetails( sectionId, policyContext.getRiskGroup( sectionId, riskGroupId ) );
		String premiumJson = "";
		if( !Utils.isEmpty( rgd ) && !Utils.isEmpty( rgd.getPremium() ) && !Utils.isEmpty( rgd.getPremium().getPremiumAmt() ) ){
			premiumJson = "{\"par_txt_Premium\":\"" + rgd.getPremium().getPremiumAmt() + "\"}";
		}
		return premiumJson;
	}

	/**
	 * Gets the service task identifier from appconfig.properties and executes
	 * the task.
	 * 
	 * @param sectionId
	 * @param policyContext
	 * @return
	 */
	protected PolicyVO executeSaveTasks( Integer sectionId, PolicyContext policyContext ){
		String svcIdentifier = SectionRHUtils.getSectionSaveSvcIdentifier( sectionId,policyContext );
		return (PolicyVO) TaskExecutor.executeTasks( svcIdentifier, policyContext.getPolicyDetails() );
	}

	/**
	 * Dummy method to create PremiumVO. To be used only till Rating Engine
	 * integration is ready.
	 * 
	 * @param request
	 * @return
	 */
	protected PremiumVO getPremiumVO( HttpServletRequest request ){
		// TODO remove premium hard coding 
		PremiumVO premiumVO = new PremiumVO();
		premiumVO.setPremiumAmt( 0.0 );
		premiumVO.setCurrency( Currency.getUnitName() );

		return premiumVO;
	}

	/**
	 * Validates the 3 major input VOs - RiskGroup, RiskGroupDetails and
	 * SectionVO for this SAVE action.
	 * 
	 * @param rg
	 * @param rgd
	 * @param section
	 */
	protected abstract void validate( RiskGroup rg, RiskGroupDetails rgd, SectionVO section ,BaseVO baseVO);

	/**
	 * Maps the request parameters to RiskGroup and returns. This method has
	 * been made abstract because there can be other RiskGroup implementations
	 * than LocationVO, though at the time of creation of this class
	 * (1/April/2012), only LocationVO implements RiskGroup.
	 * 
	 * @param request
	 * @return
	 */
	protected abstract RiskGroup mapRiskGroup( HttpServletRequest request );

	/**
	 * Maps the request parameters to the RiskGroupDetails implementation
	 * instance for this section and returns.
	 * 
	 * @param request
	 * @return
	 */
	protected abstract RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request );

	/*This method implements the section specific logic, If there is no logic  there will be empty implementation */
	protected abstract void sectionLogic( PolicyContext policyContext );

	/**
	 * This is a concrete method which will CascadeUWQuestions response if UW questions are present for a section
	 * and cascade option is provided for the same.
	 * @param policyContext
	 */
	private void cascadeUWQuestionsForCurrSection( PolicyContext policyContext ){
		PolicyVO policyVO = policyContext.getPolicyDetails();
		//Radar fix
		UWQuestionsVO uwQuestionsVO = null; //new UWQuestionsVO();

		SectionVO currSection = com.rsaame.pas.svc.utils.PolicyUtils.getSectionVO( policyVO, policyContext.getCurrentSectionId() );

		RiskGroup currRG = com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupForProcessing( currSection );

		RiskGroupDetails currRGD = com.rsaame.pas.svc.utils.PolicyUtils.getRiskGroupDetails( currRG, currSection );
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> rgdMap = currSection.getRiskGroupDetails();

		UWQuestionsVO currentUWQ = currRGD.getUwQuestions();

		/* If UWQuestions is not applicable for the section then just return  */
		if( Utils.isEmpty( currentUWQ ) ) return;

		if( currentUWQ.isCascaded() ){
			for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : rgdMap.entrySet() ){
				RiskGroupDetails rgd = locationEntry.getValue();
				if( !locationEntry.getKey().equals( (LocationVO) currRG ) ){
					if( !Utils.isEmpty( rgd ) ){
						uwQuestionsVO = CopyUtils.copySerializableObject( currentUWQ );
						uwQuestionsVO.setCascaded( false );
						rgd.setUwQuestions( uwQuestionsVO );
					}
				}
			}
		}
	}

private DataHolderVO<List> prepareInputForReferalSave(PolicyVO result , Integer sectionId ,Integer userId,String role ){
		
		DataHolderVO<List> inputData=new DataHolderVO<List>();
		List dataList=new ArrayList();
		dataList.add(result);
		dataList.add(sectionId);
		/*Start of ticket 137704 */
		dataList.add(userId);
		dataList.add(role);
		/*End of ticket 137704 */

		inputData.setData(dataList);
		
		return inputData;
	}
}
