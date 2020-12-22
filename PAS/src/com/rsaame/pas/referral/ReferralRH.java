package com.rsaame.pas.referral;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.constants.Constants;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.StandardClause;

public class ReferralRH implements IRequestHandler{
	private static final Logger log = Logger.getLogger( ReferralRH.class );

	private static final String OPTYPE_SECTION_RH = "SECTION";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		Response responseObj = new Response();

		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		
		/* IMPORTANT: We have the PolicyContext now. However, we will not start a transaction here since there has been a referral 
		 * and the referral data (not referral text, but the actual risk data that caused the referral) is still required. A fresh
		 * transaction will clear that data off. */

		PolicyVO policyVO = policyContext.getPolicyDetails();
		String rgId=request.getParameter( "rgId" );
		boolean isNewLocation = Utils.isEmpty( rgId) || rgId.startsWith( "L" );
		ReferralVO referalVO = null; /** To handle last section referral flow */
		
		Integer sectionId=null;
		// When comes from geninfo section id will be zero
		if( Integer.valueOf(request.getParameter( "sectionId" ))==AppConstants.SECTION_ID_GEN_INFO)
        {
              sectionId=AppConstants.SECTION_ID_GEN_INFO;
        }
		else if( Integer.valueOf(request.getParameter( "sectionId" ))==AppConstants.SECTION_ID_COND)
		{
			sectionId=AppConstants.SECTION_ID_COND;
		}
        else{
              /* Get the section Id and log it at INFO level because this was sometimes being received as "undefined". */
              sectionId = Utils.isEmpty( policyContext.getCurrentSectionId() ) ? 0 : policyContext.getCurrentSectionId(); /*request.getParameter( "sectionId" );*/ 
        }

		if( log.isInfo() ) log.info( "Section Id retrieved from request [", sectionId.toString(), "]" );

		String svcIdentifier = (String) Utils.getSingleValueAppConfig( Utils.concat( "SVC_IDENTIFIER_", sectionId.toString() ) );

		String action = request.getParameter( "action" );

		/* Invoke service identifier to perform save operation of data/referral data */
		PolicyVO result = null;
			
			/*
			 * Below TaskExecutor call is being made to update prepackage flag to PolicyVO in case of referral flow
			 * as SaveGeneralRH is not invoked in case of referral flow. 
			 */
		policyVO =(PolicyVO)TaskExecutor.executeTasks(AppConstants.SET_PRE_PACKAGE_FLAG, policyVO);
		
		if(sectionId.intValue() == 0 && (policyVO.getAppFlow().equals( Flow.AMEND_POL ) || policyVO.getAppFlow().equals( Flow.EDIT_QUO )  || policyVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ))){
		 		result = (PolicyVO) TaskExecutor.executeTasks("ENDORSE_GENINFO_SAVE_INVSVC", policyVO );
		 		

		}
		else if(sectionId.intValue() == 992 && (policyVO.getAppFlow().equals( Flow.AMEND_POL ) || policyVO.getAppFlow().equals( Flow.CREATE_QUO ) || policyVO.getAppFlow().equals( Flow.EDIT_QUO ) || policyVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ))){
	 		result = (PolicyVO) TaskExecutor.executeTasks("SAVE_COND_REFERRAL", policyVO );
	 		SectionVO sectionvo = PolicyUtils.getSectionVO( policyVO, policyVO.getSelectedSectionId() );
	 		/**
			 * Fetch standard clauses from session. 
			 * They will be used during endorsement for matching with previous clauses and endorsed clauses.
			 */
			List<StandardClause> stdClausesList = 	(List<StandardClause>) request.getSession(false).getAttribute(AppConstants.GET_STD_CLAUSES);
			
			
	 		Object[] input = { sectionvo, policyVO, stdClausesList };
	 		DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			inputData.setData( input );
			policyVO = (PolicyVO) TaskExecutor.executeTasks( "SAVE_CLAUSES", inputData );
	 		

	}else{
			result = (PolicyVO) TaskExecutor.executeTasks( svcIdentifier, policyVO );
			
			
			/*The below call is added to handle referral messages save in temporary table. * Earlier the referral
			 * messages save was handled by respective section saveDAO. But it has been moved from there because 
			 * referral message save was not happening  in second edit scenario due to transaction roll back.
			 Here , Referral save handling is not required for General Info as it is taken care specifically*/
			
			if(!Utils.isEmpty( sectionId) && sectionId.intValue()!=AppConstants.SECTION_ID_GEN_INFO){
				/*Start of ticket 137704 */

				UserProfile userProfile = (UserProfile) request.getSession()
						.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
				int userID=0;
				String role=null;
				if (!Utils.isEmpty(userProfile)) {
					 userID=userProfile.getRsaUser().getUserId();
					role=DAOUtils.getUserRole(policyVO);

				}
				
				DataHolderVO<List> inputData=prepareInputForReferalSave(result,sectionId,userID, role);
				/*End of ticket 137704 */

				TaskExecutor.executeTasks("HANDLE_REFERRAL_MESSAGES", inputData );
				
				/*Below code has been added to set the save flag of location to false after successful save. Earlier the 
				 *same was achieved in BaseSectionSaveDAO . But it was removed from BaseSectionSaveDAO as part of 
				 *consolidated referral Fix*/
				SectionVO section = PolicyUtils.getSectionVO( policyVO,sectionId );
				LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
				locationDetails.setToSave( false );
			}
			
			
		}
		
		/* No exception was thrown. Hence, the processing was successful. Set the success message. This is necessary
		 * because we are not calling the Section Save RH to save the data but are directly making a call to TaskExecutor
		 * to execute the Save services. Hence, the error message, which is being set in the Save RHs, will not be set
		 * after the TaskExecutor call. */
		AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
		/* (b) Cascade this location to all sections, if this is a new location.
		 *     The reason why  "rg" is not being directly passed here is that the risk group Id would have been updated in the 
		 *     DAO and there is no necessity that the same RiskGroup instance, which was mapped in this RH, has been returned. */
	
		String currRGId = (String) com.rsaame.pas.cmn.context.ThreadLevelContext.get( "RISK_GROUP_ID" );
		policyContext.commit();
		if( isNewLocation ){
			policyContext.cascadeNewRiskGroupToSections( policyContext.getRiskGroup( sectionId, currRGId ) );
		} else {
			if(policyVO.getAppFlow().equals( Flow.CREATE_QUO )){
				policyContext.updateBaseSectionChanges( policyContext.getRiskGroup( sectionId, currRGId ) );
			}
		}

		//}

		/* Before forwarding to SectionRH, get the current risk group Id from the ThreadLevelContext. This is a generic way 
		 * of getting the risk group Id without the risk of getting the temporary "L#" ids because this is set in the section
		 * DAO calls. However, this has to be retrieved from ThreadLevelContext before calling SectionRH because SectionRH
		 * clears the value at the end of its execution. */
		
		
		/* Forward the request to SectionRH to do redirection if in case the action is "NEXT" or "PREVIOUS".
		 * This handling need not be done for General Info Save */

		/** SK : Changes */
		/** Changes below are incorporated to handle consolidate referral message on the last
		 *  section in order to avoid updation of currentSectionId within policy context as
		 *  in case the user clicks on "No" in consolidated referral window and tries to do 
		 *  click to "save" or "next" on the last section results in exception flow and 
		 *  current section id within policy context is changed to premium page i.e. 999 
		 *  */
		
		
		if( !Utils.isEmpty( action ) && ( action.equalsIgnoreCase( "NEXT" ) || action.equalsIgnoreCase( "PREVIOUS" ) || action.equalsIgnoreCase( "SAVE" ) )
				&& !sectionId.equals( AppConstants.SECTION_ID_GEN_INFO ) && !policyContext.isLastSection()){

			/* a. Pass operation type corresponding to SectionRH
			 * b. additional request attribute is passed on to SECTION_RH to identify 
			 * redirection to be performed for referral cases 
			 * */
			request.setAttribute( AppConstants.REQ_ATTR_REFERRAL_ACTION, "REFERRAL_ACTION" );
			responseObj = executeRequestHandler( OPTYPE_SECTION_RH, request, response );
		}
		
		
		/**
		 *  Temporary Fix :  LOAD_SCREEN action to be triggered in case  
		 *  a. Section is the last section also as json response needs to be rendered back to retain 
		 *     entered values
		 */
		if(!Utils.isEmpty( action ) && ( action.equalsIgnoreCase( "SAVE" ) 
				&& !sectionId.equals( AppConstants.SECTION_ID_GEN_INFO ) && policyContext.isLastSection())){
			/* a. Pass operation type corresponding to SectionRH
			 * b. additional request attribute is passed on to SECTION_RH to identify 
			 * redirection to be performed for referral cases 
			 * */
			request.setAttribute( AppConstants.REQ_ATTR_REFERRAL_ACTION, "REFERRAL_ACTION" );
			responseObj = executeRequestHandler( OPTYPE_SECTION_RH, request, response );
		}
		
		/**
		 * a. If the flow is for Getting Consolidated referral in the last section
		 * b. Additional checks has been added in case referralRH is invoked from  
		 * 	  "YES" click on consolidated referral window in order to avoid execution
		 * 	  of below block
		 * c. Checking if section id is not equal to General Info Section Id i.e. o
		 * 	  as policyContext.isLastSection() will not consider general info as one
		 *    of one of the sections.
		 * d. Also check if the action performed by the user is "NEXT" as on save 
		 * 	  only section referal work flow should get triggered
		 */
		
		if(log.isInfo()) log.info( "action within referalRH is ["+ action.toString()+"]");
		
		if(action.equalsIgnoreCase( "NEXT" ) && !sectionId.equals( AppConstants.SECTION_ID_GEN_INFO ) && policyContext.isLastSection()){
			if(log.isInfo()) log.info( "within fetch consolidated referral data");
			referalVO = new ReferralVO();
			referalVO.setPolLinkingId( policyContext.getPolicyDetails().getPolLinkingId() );
			
			
			// Update the discount premium. 
			/*
			 * In the last section before navigating to the consolidated referral pop - up
			 * update the discount or loading premium. 
			 */
			
			policyVO = (PolicyVO) TaskExecutor.executeTasks( "UPDATE_SPECIAL_COVER", policyVO );
			
			ReferralListVO referralListVO = (ReferralListVO) TaskExecutor.executeTasks( "PREMIUM_PAGE", referalVO );
			
			if( !Utils.isEmpty( referralListVO ) && !Utils.isEmpty( referralListVO.getReferrals() ) ){
				String consolidatedReferralMessage = "";
				/*
				 * Added string buffer to avoid "+" for sonar violation
				 * on 14-9-2017
				 */
				StringBuffer consolidatedReferralMessageBuffer=new StringBuffer();
				 //Iterating all the referrals to get consolidated message 
				List<ReferralVO> referralVOs = referralListVO.getReferrals();
				for( ReferralVO voTemp : referralVOs ){
					if( !Utils.isEmpty( voTemp ) ){
						//consolidatedReferralMessage += voTemp.getSectionName() + " : " + voTemp.getReferralText() + "\n";
						consolidatedReferralMessage=consolidatedReferralMessageBuffer.append(voTemp.getSectionName()).append(" : ").append(voTemp.getReferralText()).append("\n").toString();
					}
				}
				policyContext.getPolicyDetails().setConCatRefMsgs( consolidatedReferralMessage );
				responseObj.setResponseType( com.mindtree.ruc.mvc.Response.Type.JSON );
				responseObj.setData( referralListVO );
		   }
			
	    }
		/* Check if section id is 0 i.e. General Information Save if so
		 * update the result i.e. policyVO to response in order to display quote details on the 
		 * screen
		 */
		if( sectionId.equals( AppConstants.SECTION_ID_GEN_INFO ) ){
			responseObj.setData( result );
		}
		
		/* Now that the processing of the referral data was successful, let us commit the PolicyContext transaction. */
		
		/* To pass back the action identifier as part of response header */
		response.setHeader( "actionIdentifier", action );
		
		/*Check to get the information whether location has been added in current section or it is coming from basic section*/
		if(!sectionId.equals( AppConstants.SECTION_ID_GEN_INFO) && !sectionId.equals( AppConstants.SECTION_ID_COND)){
			AppUtils.isLocationAddedInCurrentSection( request, Integer.valueOf( currRGId ));
		}
		return responseObj;
	}

	/**
	 * This is a workaround to know the current location. This is required for the section screens to load the JSON JSP for the 
	 * current section.
	 * @param policyVO
	 * @return
	 *//*
	private String getCurrentRiskGroupId( PolicyVO policyVO ){
		RiskGroup currRG = null;

		if( !Utils.isEmpty( policyVO.getRiskDetails() ) ){
			for( SectionVO section : policyVO.getRiskDetails() ){
				java.util.Set<? extends RiskGroup> rgs = Utils.isEmpty( section.getRiskGroupDetails() ) ? null : section.getRiskGroupDetails().keySet();

				if( Utils.isEmpty( rgs ) ) continue;

				for( RiskGroup rg : rgs ){
					LocationVO l = (LocationVO) rg;
					if( Boolean.TRUE.equals( l.getToSave() ) ){
						currRG = rg;
						break;
					}
				}

				if( !Utils.isEmpty( currRG ) ) break;
			}
		}

		return Utils.isEmpty( currRG ) ? null : currRG.getRiskGroupId();
	}*/

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
