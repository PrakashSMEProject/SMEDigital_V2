package com.rsaame.pas.ee.ui;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;

public class EEPageRH extends LoadSectionRH implements IRequestHandler{

	@Override
	protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", String.valueOf( sectionId ) ) ) );
	}

	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setContentsListToRequest( HttpServletRequest request, PolicyContext policyContext ){

		PolicyVO policyVO = policyContext.getPolicyDetails();
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "ELECTRONIC_EQUIPMENT",policyVO.getScheme().getSchemeCode().toString(),policyVO.getScheme().getTariffCode().toString() );
		
		if(!Utils.isEmpty( listVO ) &&  !Utils.isEmpty( listVO.getLookUpList() )){
			java.util.List<LookUpVO> list = listVO.getLookUpList();
			request.setAttribute( "equipmentType",list );
		}
		
	
	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
