package com.rsaame.pas.motor.ui;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.vo.bus.RiskGroup;
/**
 * 
 * @author m1014438
 *
 */

public class MotorFleetLoadRH extends LoadSectionRH implements IRequestHandler{

	@Override
	protected int getSectionClassCode( Integer sectionId ){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setContentsListToRequest( HttpServletRequest request, PolicyContext policyContext ){
		// TODO Auto-generated method stub

	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
