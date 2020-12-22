package com.rsaame.pas.mb.ui;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.money.ui.MoneyLoadRH;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * Loads the MB Section
 * @author m1014438
 *
 */
public class MBLoadRH extends LoadSectionRH implements IRequestHandler{
	private final static Logger LOGGER = Logger.getLogger( MoneyLoadRH.class );

	@Override
	protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", String.valueOf( sectionId ) ) ) );
	}

	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){

		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_MB );
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
		if( !Utils.isEmpty( riskGroupDetails ) ){
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
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "MACHINERY_TYPE",policyVO.getScheme().getSchemeCode().toString(),policyVO.getScheme().getTariffCode().toString() );
		if(!Utils.isEmpty( listVO ) &&  !Utils.isEmpty( listVO.getLookUpList() )){
			java.util.List<LookUpVO> list = listVO.getLookUpList();
			request.setAttribute( "machineryType",list );
		}
		
	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
}
