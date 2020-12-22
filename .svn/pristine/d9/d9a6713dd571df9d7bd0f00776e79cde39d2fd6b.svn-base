package com.rsaame.pas.dos.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.Lookup;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public class DeteriorationOfStockLoadRH extends LoadSectionRH implements IRequestHandler{

	@Override
	protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf(Utils.getSingleValueAppConfig(Utils.concat( "SEC_",String.valueOf( sectionId ) ) ) );
	}

	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_DETERIORATION_OF_STOCK );
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
		PolicyVO policyVO = policyContext.getPolicyDetails();
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "STOCK_TYPE",policyVO.getScheme().getSchemeCode().toString(),policyVO.getScheme().getTariffCode().toString() );
		
		if(!Utils.isEmpty( listVO ) &&  !Utils.isEmpty( listVO.getLookUpList() )){
			java.util.List<LookUpVO> list = listVO.getLookUpList();
			request.setAttribute( "dosStockType",list );
		}
		
	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
