package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * Fetches and returns the pre-population values required for the section's form. Responds in
 * JSON using the section JSON JSP (not using JSONOutput).
 */
public class FetchSectionPPDataRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		/* (a) If the policy is pre-packaged, only then execute the logic.
		 * (b) Load the pre-population data for scheme tariff from the service.
		 * (c) Set the SectionVO, RiskGroup and RiskGroupDetails objects to the request.
		 * (d) Figure out the section JSON JSP and create a Redirection instance. */
		Response resp = new Response();
		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		
		/* Pre-population data for pre-packaged policies. Even other policies could have pre-population data. */
		if( Boolean.TRUE.equals( policyContext.getPolicyDetails().getIsPrepackaged() ) ){
			/* (b) Load pre-population data */
			SectionVO ppSectionVOTemplate = loadPPData( request,policyContext );
			
			/* (c) Set pre-population data to the request so that the section's JSON JSP can pick it up. */
			if( !Utils.isEmpty( ppSectionVOTemplate ) ){
				RiskGroup rg = getFirstRiskGroupFromSection( ppSectionVOTemplate );
				
				RiskGroupDetails rgd = getRiskGroupDetailsFromSection( ppSectionVOTemplate, rg );
				/*double policyPremium=0;
				if(!Utils.isEmpty(policyContext.getPolicyDetails())){
					if(!Utils.isEmpty(policyContext.getPolicyDetails().getPremiumVO())){
						if(!Utils.isEmpty(policyContext.getPolicyDetails().getPremiumVO().getPremiumAmt()))
						{
							policyPremium=policyContext.getPolicyDetails().getPremiumVO().getPremiumAmt();

						}
					}
				}*/
				
				AppUtils.setSectionPageDataForJSON( request, ppSectionVOTemplate, rg, rgd , policyContext.getPolicyDetails());
			}
			
			/* (d) Get the path and name of the JSON JSP that will be used to send the details for the location. */
			String locationReloadJSP = SectionRHUtils.getLocationReloadJSP( policyContext.getCurrentSectionId(), true );
			
			Redirection redirection = new Redirection( locationReloadJSP, Redirection.Type.TO_JSP );
			resp.setRedirection( redirection );
			resp.setResponseType( Response.Type.JSON );
		}
		
		return resp;
	}

	private RiskGroupDetails getRiskGroupDetailsFromSection( SectionVO ppSectionVOTemplate, RiskGroup rg ){
		RiskGroupDetails rgd = null;
		if( !Utils.isEmpty( ppSectionVOTemplate.getRiskGroupDetails() ) ){
			/* Get the risk group details for the passed RiskGroup */
			rgd = ppSectionVOTemplate.getRiskGroupDetails().get( rg );
		}
		
		return rgd;
	}

	private RiskGroup getFirstRiskGroupFromSection( SectionVO ppSectionVOTemplate ){
		RiskGroup rg = null;
		if( !Utils.isEmpty( ppSectionVOTemplate.getRiskGroupDetails() ) ){
			/* Get the first (probably only) entry's key */
			rg = ppSectionVOTemplate.getRiskGroupDetails().entrySet().iterator().next().getKey();
		}
		
		return rg;
	}

	/**
	 * Calls SectionSvc.loadPPData() to get the pre-population values for the section as a SectionVO instance with
	 * one entry in the riskGroupDetails map.
	 * @param policyContext 
	 * @return
	 */
	private SectionVO loadPPData(HttpServletRequest request,PolicyContext policyContext){
		/* Call the TaskExecutor for the service call and get the data as a SectionVO instance. */
		
		/*
		 * Fetch the sectionVO from session which consists of values to be prepopulated for PPP Flow
		 */
		
		SectionVO sectionVO = (SectionVO)request.getSession(false).getAttribute(AppConstants.SECTION_PPP_DATA);
		return sectionVO;
	}

}
