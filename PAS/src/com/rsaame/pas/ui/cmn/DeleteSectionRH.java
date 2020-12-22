package com.rsaame.pas.ui.cmn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

public class DeleteSectionRH implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Response resp = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		Integer sectionId = null;
		String currAction = (String) request.getAttribute( AppConstants.REQ_ATTR_CURR_ACTION );
		
		/* The preference order for the place from where sectionId to be loaded will be picked up is:
		 * (a) The request parameter "jumpToSectionId" 
		 * (b) The request attribute "CURR_ACTION"
		 * (c) The current sectionId in the PolicyContext */
		String jumpToSectionId = request.getParameter( AppConstants.REQ_PARAM_JUMP_TO_SECTION_ID );
		if( !Utils.isEmpty( jumpToSectionId ) && Utils.isNumber( jumpToSectionId ) ){
			sectionId = Integer.valueOf( jumpToSectionId );
		}
		else if( currAction.equals( Action.NEXT.name() ) ){
			sectionId = policyContext.getNextScreen().getSectionId();
		}
		else if( currAction.equals( Action.PREVIOUS.name() ) ){
			sectionId = policyContext.getPrevScreen().getSectionId();
		}
		else{
			sectionId = policyContext.getCurrentSectionId();
		}
		
		SectionVO sectionVO = new SectionVO(RiskGroupingLevel.LOCATION);
		sectionVO.setSectionId(sectionId);
		sectionVO.setPolicyId(policyContext.getPolicyDetails().getPolLinkingId());
		
		DataHolderVO<Object[]> holderVO = new DataHolderVO<Object[]>();
		Object[] input = { policyContext.getPolicyDetails(), sectionId };
		holderVO.setData( input );

		String action = (String) request.getAttribute( "opType" );
		/* Below line is needed to leverage hibernate template switching to handle quote and policy flows 
		 * Template switching could not be achieved through existing approach because of VO being used for delete risk section 
		 * operation */
		if( !policyContext.getPolicyDetails().getIsQuote().booleanValue() ) action = action + "_POL";
		TaskExecutor.executeTasks( action,holderVO);
		
		if(policyContext.getPolicyDetails().getRiskDetails().contains(sectionVO)){
			policyContext.getPolicyDetails().getRiskDetails().remove(sectionVO);
		}
		policyContext.removeSection(sectionVO.getSectionId());
	return resp;
	}
}
