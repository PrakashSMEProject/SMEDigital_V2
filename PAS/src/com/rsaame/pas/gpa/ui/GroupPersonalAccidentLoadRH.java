package com.rsaame.pas.gpa.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
/**
 * 
 * @author m1019834
 *
 */
public class GroupPersonalAccidentLoadRH extends LoadSectionRH{

/**
 * Getting section class code from appconfig.properties file.
 * @param sectionId to set  Integer
 * @return the integer
*/
protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf(Utils.getSingleValueAppConfig(Utils.concat( "SEC_",String.valueOf( sectionId ) ) ) );
	}
/**
 * Mapping Section Level RiskGroup Details To Request.
 * @param request is set HttpServletRequest
 * @param policyContext is set PolicyContext
 * @return the riskgroup
 */	
protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_GROUP_PERSONAL_ACCIDENT );
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

/** (non-Javadoc).
 * @param request to set HttpServletRequest
 * @param policyContext to set PolicyContext
 * @return the null
 */	
protected void setContentsListToRequest( HttpServletRequest request, PolicyContext policyContext ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
		    	    	
	}
@Override
protected void getDefaultValues(HttpServletRequest request) {
	// TODO Auto-generated method stub
	
}

}
