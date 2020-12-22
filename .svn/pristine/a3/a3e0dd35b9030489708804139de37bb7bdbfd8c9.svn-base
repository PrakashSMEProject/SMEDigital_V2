/**
 * 
 */
package com.rsaame.pas.money.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.par.ui.PARPageRH;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

public class MoneyLoadRH extends LoadSectionRH implements IRequestHandler{
	
	private final static Logger LOGGER = Logger.getLogger( MoneyLoadRH.class );
	
	@Override
	protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf(Utils.getSingleValueAppConfig(Utils.concat( "SEC_",String.valueOf( sectionId ) ) ) );
	}


	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext){
		
		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_MONEY);
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
		/*
		 * If it is a case of pre-package setting the contents to request attribute to be displayed on the screen on load
		 */
		PolicyVO policyVO = policyContext.getPolicyDetails();
		if(!Utils.isEmpty(policyVO.getIsPrepackaged()) && policyVO.getIsPrepackaged()){
			/*
			 * Fetch the contentsList from the session attribute 
			 */
			if(LOGGER.isInfo()){
				LOGGER.info("ContentsList obtained for Money ["+request.getSession(false).getAttribute(AppConstants.SECTION_CONTENTS )+"]");
			}
			request.setAttribute( "moneyContents", request.getSession(false).getAttribute(AppConstants.SECTION_CONTENTS ));
		}
		
	}


	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
