package com.rsaame.pas.par.ui;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.ui.cmn.LoadSectionRH;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;

public class PARPageRH extends LoadSectionRH implements IRequestHandler{

	private final static Logger LOGGER = Logger.getLogger( PARPageRH.class );	
	
	@Override
	protected int getSectionClassCode( Integer sectionId ){
		return Integer.valueOf(Utils.getSingleValueAppConfig(Utils.concat( "SEC_",String.valueOf( sectionId ) ) ) );
	}

	@Override
	protected RiskGroup setSectionLevelRiskGroupDetailsToRequest( HttpServletRequest request, PolicyContext policyContext ){
		
		LocationVO locationVO = null;
		SectionVO sectionVO = policyContext.getSectionDetails( AppConstants.SECTION_ID_PAR );
		java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
		if(!Utils.isEmpty( riskGroupDetails )) {
			for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
				
				locationVO = (LocationVO)locationEntry.getKey();
				
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
		 * For Prepackaged flow count of contents and its description is already fetched and set to
		 * session in LoadSectionDataRH hence no need for service task execution to fetch the contents
		 * explicitly
		 */
		PolicyVO policyVO = policyContext.getPolicyDetails();
		if(!Utils.isEmpty(policyVO.getIsPrepackaged()) && policyVO.getIsPrepackaged()){
			/*
			 * Fetch the contentsList from the session attribute 
			 */
			if(LOGGER.isInfo()){
				LOGGER.info("ContentsList obtained for PAR ["+request.getSession(false).getAttribute(AppConstants.SECTION_CONTENTS )+"]");
			}
			request.setAttribute( "parContents", request.getSession(false).getAttribute(AppConstants.SECTION_CONTENTS ));
			
		}else{
			BaseVO baseVO = TaskExecutor.executeTasks( request.getAttribute( "opType" ).toString(), policyVO );
			@SuppressWarnings( "unchecked" )
			DataHolderVO<List<Contents>> contentsList = (DataHolderVO<List<Contents>>) baseVO;
			request.setAttribute( "parContents", contentsList );
		}
		
		AppUtils.populatePOBoxToRequestAndLocationVO( request, policyContext);
		//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
		//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
		//AppUtils.isAnnualRentAddedInPAR( request, policyContext);
		
		//Added for Informap Changes
		if(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") || Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("21")) { 
  		Boolean informapAvailable=DAOUtils.isInformapAvailable();
		request.setAttribute("informapAvailable",informapAvailable);	
		}
	}

	@Override
	protected void getDefaultValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

}
