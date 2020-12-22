package com.rsaame.pas.wc.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCCoversVO;
import com.rsaame.pas.vo.bus.WCVO;


public class WCPageRH implements IRequestHandler {

	private static final RiskGroupingLevel LOCATION = RiskGroupingLevel.LOCATION;

	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse resp) {
		double commission = 0;
		Boolean paCovers = null;
		Response response = new Response();
		
		/*PolicyContext policyContext  = PolicyContextUtil.getPolicyContext(request);*/
		PolicyContext policyContext = new PolicyContext(null);
		PolicyVO policyDetails = new PolicyVO();
		List<SectionVO> riskDetails = new ArrayList<SectionVO>(); 
		
		LocationVO location = new LocationVO();
		WCCoversVO wcCovers = new WCCoversVO();
		
		location.setRiskGroupId("7");
		location.setRiskGroupName( AppConstants.RISK_GROUP_NAME_WC );
		WCVO wc = new WCVO();
		wc.setCommission(15.0);
		wc.setClassCode( AppConstants.CLASS_ID_WC );		
		wcCovers.setPACover(false);
		wc.setWcCovers(wcCovers);
		
		//TODO: Functionality is to get the value from policy context and load it on screen but logic is absent to take it from PAR or PL
		Map<LocationVO, WCVO> riskGroupDetail = new HashMap<LocationVO, WCVO>();
		riskGroupDetail.put(location, wc);
		
		
			SectionVO section = new SectionVO(LOCATION);
			section.setSectionId(7);
			section.setSectionName("WC");
			section.setRiskGroupingLevel(LOCATION);
			
			section.setRiskGroupDetails(riskGroupDetail);
			riskDetails.add(section);
		
		policyDetails.setRiskDetails(riskDetails);
		policyDetails.setIsPrepackaged(true);
		policyContext.setPolicyDetails(policyDetails);
		/*Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetail = null;
		//riskGroupDetail.
		com.mindtree.ruc.cmn.utils.Map<LocationVO, WCVO> riskGroupDetailstoSave = new com.mindtree.ruc.cmn.utils.Map<LocationVO, WCVO>( LocationVO.class, WCVO.class );
		LocationVO locationVOs = new LocationVO();
		WCVO wcTest = new WCVO();
		WCCoversVO wcCovers = new WCCoversVO();
		double big = 3000000.00;
		for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetail.entrySet()) {
			locationVOs.setRiskGroupId("7");
			locationVOs.setRiskGroupName("WC");
			wcTest.setCommission(15.0);
			wcTest.setClassCode(7);
			wcTest.setDeductibles(1500.00);
			
			BigDecimal bigd = null;
			bigd.valueOf(big);
			wcTest.setLimit(bigd);
			wcTest.setEmpType(2);
			wcCovers.setPACover(false);
			wcTest.setWcCovers(wcCovers);
			
			
		}
		
		for (SectionVO sectionVO : riskDetails) {
			sectionVO.setSectionId(7);
			sectionVO.setSectionName("WC");
			
			sectionVO.setRiskGroupDetails(riskGroupDetail );
		}
		
		policyDetails.setRiskDetails(riskDetails );
		policyDetails.setIsPrepackaged(true);
		policyContext.setPolicyDetails(policyDetails);*/
		
		
		
		
		
		
		PolicyVO policyVO = policyContext.getPolicyDetails();
		Boolean checkIfPrepacked = policyVO.getIsPrepackaged();
		if(checkIfPrepacked)
		{
			//BaseVO baseVO = TaskExecutor.executeTasks(identifier,policyVO);
			//PolicyVO policy = (PolicyVO) baseVO;
			List<SectionVO> sectionVOs = policyVO.getRiskDetails(); 
			for (SectionVO sectionVO : sectionVOs) {
				if(sectionVO.getSectionId()==7){
					java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
					//com.mindtree.ruc.cmn.utils.Map<LocationVO, WCVO> riskGroupDetailsSaved = new com.mindtree.ruc.cmn.utils.Map<LocationVO, WCVO>( LocationVO.class, WCVO.class );

					for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
						WCVO wcVO = (WCVO) locationEntry.getValue();
						commission = wcVO.getCommission();
						//WCCoversVO wcCover = wcVO.getWcCovers();
						//paCovers = wcCover.getPACover();
						
				}
					
			}
			
			
		}
			request.setAttribute("commission",commission);	
		//	TODO: Set dynamic attribute for radio and dropdown
			/*if(paCovers.equals(true)){
				request.setAttribute("checked","checked");
			}else
			{
				request.setAttribute("checked","checked");
			}*/
			
		}	
		
		/*
		 * The following values are passed as request attributes for authorization purposes
		 */
		
		request.setAttribute(AppConstants.MODE, VisibilityLevel.EDITABLE);
        request.setAttribute(AppConstants.FUNTION_NAME, "CREATE_QUO");
        request.setAttribute(AppConstants.SCREEN_NAME, "SBS_WC");
        
		
		
		
		
		return response;
		
	}
	
	

}
