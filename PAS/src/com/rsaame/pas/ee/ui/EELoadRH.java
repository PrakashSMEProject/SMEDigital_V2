package com.rsaame.pas.ee.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public class EELoadRH extends LoadSectionRH implements IRequestHandler{

	@Override
	protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf(Utils.getSingleValueAppConfig(Utils.concat( "SEC_",String.valueOf( sectionId ) ) ) );
	}

	@Override
protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
		
		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_EE );
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


	@Override
	protected void setContentsListToRequest( HttpServletRequest request, PolicyContext policyContext ){
		// TODO Auto-generated method stub

	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
