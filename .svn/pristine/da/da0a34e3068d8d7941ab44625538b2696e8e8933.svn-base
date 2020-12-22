/**
 * 
 */
package com.rsaame.pas.bi.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author   M1019703
 *
 */
public class BILoadRH extends LoadSectionRH implements IRequestHandler {

	/**
	 * 
	 */
	public BILoadRH() {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.ui.cmn.LoadSectionRH#getSectionClassCode(java.lang.Integer)
	 */
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
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_BI);
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
		//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
		//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
				//AppUtils.isAnnualRentAddedInPAR( request, policyContext);

	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
