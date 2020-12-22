package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.DelLocationInputVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.SectionVO;

public class DelLocationRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		DelLocationInputVO delLocationInputVO = new DelLocationInputVO();

		/* Get "sectionId" from HTTP request and populate DelLocationInputVO.sectionId.*/
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		Integer sectionId = policyContext.getCurrentSectionId();
		if( !Utils.isEmpty( sectionId ) ){
			delLocationInputVO.setSectionId( sectionId );
		}

		/* Get "locationId" from HTTP request and populate DelLocationInputVO.buildingId */
		String rgIdParam = request.getParameter( AppConstants.REQ_PARAM_RISK_GROUP_ID );
		if( Utils.isEmpty( rgIdParam ) ){
			throw new BusinessException( "pas.locComp.riskGroupIdNotPassed", null, "riskGroupId is mandatory in the request for Delete Location" );
		}

		Integer rgId = ( Integer.valueOf( rgIdParam ) );
		if( !Utils.isEmpty( rgId ) ){
			delLocationInputVO.setBuildingId( rgId );
		}

		/* Get PolicyDetails and set into DelLocationInputVO */
		PolicyVO policy = policyContext.getPolicyDetails();
		if( !Utils.isEmpty( policy ) ){
			delLocationInputVO.setPolicy( policy );
		}

		/* Set the cascading option */
		Integer currentSectionId = policyContext.getCurrentSectionId();
		/*if( !Utils.isEmpty( currentSectionId ) ){
			if( currentSectionId == AppConstants.SECTION_ID_PAR ){
				delLocationInputVO.setCascade( true );
			}
			else if( !( policyContext.isSectionPresent( AppConstants.SECTION_ID_PAR ) )
					&& ( currentSectionId ==  AppConstants.SECTION_ID_PL ) ){
				delLocationInputVO.setCascade( true );
			}
		}*/
		
		/* If current Section is basic section then enable DeleteLocationInputVO.setCascade to true in order
		 * to delete from other sections also.
		 */
		Integer basicSectionId = com.rsaame.pas.svc.utils.PolicyUtils.getBasicSectionId( policy );

		if( delLocationInputVO.getSectionId().intValue() == basicSectionId.intValue() ) delLocationInputVO.setCascade( true );

		/* Now, execute the tasks. The opType will be used as the identifier for the tasks. */
		String action = (String) request.getAttribute( "opType" );
		
		/* Set isQuote value before hitting the service in order to leverage option of getting
		 * hibernate template switched to perform hibernate operations */
		delLocationInputVO.setQuote( policy.getIsQuote() );
		TaskExecutor.executeTasks( action, delLocationInputVO );

		/* Remove the risk group details from the policy. */
		policyContext.removeRiskGroupDetails( currentSectionId, String.valueOf( rgId ) );

		/* Now set the values for the display of the next location. */
		RiskGroup firstRGInSection = policyContext.getRiskGroups( currentSectionId ).get( 0 );
		//Radar fix
		//double policyPremium=0;
		if(!Utils.isEmpty(policyContext.getPolicyDetails())){
			if(!Utils.isEmpty(policyContext.getPolicyDetails().getPremiumVO())){
				if(!Utils.isEmpty(policyContext.getPolicyDetails().getPremiumVO().getPremiumAmt()))
				{
					policyContext.getPolicyDetails().getPremiumVO().getPremiumAmt();

				}
			}
		}
		
		
		AppUtils.setSectionPageDataForJSON( request, 
				policyContext.getCurrentSection(), 
				firstRGInSection, /* The first risk group */
				policyContext.getRiskGroupDetails( currentSectionId, firstRGInSection ), policyContext.getPolicyDetails() );
		
		/* Also, set the deleted riskGroupId to the request. */
		request.setAttribute( "deletedRiskGroupId", rgId );
		
		/*Setting request attribute to be true so that we can make difference between delete and de-select location on UI  */
		setRemoveLocationStatus(request,rgId);
		
		/* Get the path and name of the JSON JSP that will be used to send the details for the location. */
		String locationReloadJSP = SectionRHUtils.getLocationReloadJSP( currentSectionId, true );
		
		Response resp = new Response();
		resp.setRedirection( new Redirection( locationReloadJSP, Redirection.Type.TO_JSP ) );
		
		

		return resp;
	}

	
	/**
	 * 
	 * @param request
	 * @param rgId
	 */
	private void setRemoveLocationStatus( HttpServletRequest request, Integer rgId ){
		PolicyContext policyContext=PolicyContextUtil.getPolicyContext( request );
		if( policyContext.isCurrentSectionBasic()){
			request.setAttribute( "removeLocFlag", "true" );
			AppUtils.addErrorMessage( request, "pas.locComp.deleteSuccessful" );
			return;
		}
		if(!policyContext.isCurrentSectionBasic()){
			SectionVO sectionVO=policyContext.getSectionDetails(policyContext.getBasicSectionId() );
			if(!Utils.isEmpty( sectionVO.getRiskGroupDetails())){
				LocationVO locationVO=new LocationVO();
				locationVO.setRiskGroupId( rgId.toString());
				if(!sectionVO.getRiskGroupDetails().containsKey( locationVO )){
					request.setAttribute( "removeLocFlag", "true" );
					AppUtils.addErrorMessage( request, "pas.locComp.deleteSuccessful" );
				}
				else{
					AppUtils.addErrorMessage( request, "pas.locComp.deselectSuccessful" );
				}
			}
			
		}
		
		
	}
}
