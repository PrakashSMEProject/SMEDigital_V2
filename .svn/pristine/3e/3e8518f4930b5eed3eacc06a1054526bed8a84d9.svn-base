package com.rsaame.pas.pl.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.lookup.ui.DropDownRendererHepler;
import com.rsaame.pas.money.ui.MoneyLoadRH;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

public class PLContentLoadRH extends LoadSectionRH{
	private final static Logger LOGGER = Logger.getLogger( PLContentLoadRH.class );

	@Override
	protected int getSectionClassCode(Integer sectionId){
		
		return Integer.valueOf(Utils.getSingleValueAppConfig(Utils.concat( "SEC_",String.valueOf( sectionId ) ) ) );
	}

	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
		
		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_PL );
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
		 * No contents are to be displayed dynamically hence an emtpy implementation is added
		 */	
		
			AppUtils.populatePOBoxToRequestAndLocationVO( request, policyContext);
		
		/* To set Annual turn over in PL to GI's annual turnover*/
		AppUtils.populateTurnoverToRequest( request, policyContext);
	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		/**
		 * Set country to default value based on logged in location.
		 */
		BaseVO baseVO=null;
		BaseVO jurBaseVO=null;
		LookUpVO lookUpVO=new LookUpVO();
		LookUpVO jurLookUpVO=new LookUpVO();
		lookUpVO.setCategory("COUNTRY");
		lookUpVO.setLevel1("ALL");
		lookUpVO.setLevel2("ALL");
		baseVO= TaskExecutor.executeTasks("LOOKUP_INFO", lookUpVO);
		LookUpListVO lookUpList = new LookUpListVO();
		if(baseVO instanceof LookUpListVO){
			lookUpList = DropDownRendererHepler.getLookFilteredList((LookUpListVO) baseVO,request.getSession(false));
			
		}
		request.setAttribute(AppConstants.COUNTRY_LOOKUP_VAL, lookUpList.getLookUpList().get(0).getCode());
		
		/**
		 * Set jurisdiction to default value based on logged in location.
		 */
		jurLookUpVO.setCategory("JURISDICTION");
		jurLookUpVO.setLevel1("ALL");
		jurLookUpVO.setLevel2("ALL");
		jurBaseVO= TaskExecutor.executeTasks("LOOKUP_INFO", jurLookUpVO);
		LookUpListVO jurLookUpList = new LookUpListVO();
		if(jurBaseVO instanceof LookUpListVO){
			jurLookUpList = DropDownRendererHepler.getLookFilteredList((LookUpListVO) jurBaseVO,request.getSession(false));
			
		}
		
		request.setAttribute(AppConstants.JURISDICTION_LOOKUP_VAL, jurLookUpList.getLookUpList().get(0).getCode());
		
		
	}
}
