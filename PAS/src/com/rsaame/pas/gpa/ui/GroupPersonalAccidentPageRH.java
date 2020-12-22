package com.rsaame.pas.gpa.ui;



import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.vo.bus.RiskGroup;

/**
 * 
 * @author m1019834
 *
 */
public class GroupPersonalAccidentPageRH extends LoadSectionRH {
/**
* Getting section class code from appconfig.properties file.
* @param sectionId required
* @return the int
*/
protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", String.valueOf( sectionId ) ) ) );
}
/** (non-Javadoc).
 * @param request is required
 * @param policyContext is required
 * @return the null
*/
protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
			return null;
	}
/** (non-Javadoc).
 * @param request is required 
 * @param policyContext is required 
 * @return
 */
protected void setContentsListToRequest( HttpServletRequest request, PolicyContext policyContext ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
}
@Override
protected void getDefaultValues(HttpServletRequest request) {
	// TODO Auto-generated method stub
	
}

}
