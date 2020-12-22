/**
 * 
 */
package com.rsaame.pas.fidelity.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.money.ui.MoneyLoadRH;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1016303
 *
 */
public class FidelityLoadRH extends LoadSectionRH {

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.LoadSectionRH#getSectionClassCode(java.lang.Integer)
	 */
	private final static Logger LOGGER = Logger.getLogger( FidelityLoadRH.class );
	
	@Override
	protected int getSectionClassCode(Integer sectionId) {
		return Integer.valueOf(Utils.getSingleValueAppConfig(Utils.concat( "SEC_",String.valueOf( sectionId ) ) ) );
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.LoadSectionRH#setSectionLevelRiskGroupDetailsToRequest(javax.servlet.http.HttpServletRequest, com.rsaame.pas.ui.cmn.PolicyContext)
	 */
	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest(
			HttpServletRequest request, PolicyContext policyContext) {
		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_FIDELITY);
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
		if(!Utils.isEmpty( riskGroupDetails )) {
			for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
				
				locationVO = (LocationVO) locationEntry.getKey();
				
				if( Utils.isEmpty( locationVO ) ){
					continue;
				}
				else{
					break;
				}
			}
		}
		return locationVO;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.LoadSectionRH#setContentsListToRequest(javax.servlet.http.HttpServletRequest, com.rsaame.pas.ui.cmn.PolicyContext)
	 */
	@Override
	protected void setContentsListToRequest(HttpServletRequest request,
			PolicyContext policyContext) {
		/*
		 * If it is a case of pre-package setting the contents to request attribute to be displayed on the screen on load
		 */
		PolicyVO policyVO = policyContext.getPolicyDetails();
		if(!Utils.isEmpty(policyVO.getIsPrepackaged()) && policyVO.getIsPrepackaged()){
			/*
			 * Fetch the contentsList from the session attribute 
			 */
			if(LOGGER.isInfo()){
				LOGGER.info("ContentsList obtained for Fidelity ["+request.getSession(false).getAttribute(AppConstants.SECTION_CONTENTS )+"]");
			}
			request.setAttribute( "fidelityContents", request.getSession(false).getAttribute(AppConstants.SECTION_CONTENTS ));
		}

	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
