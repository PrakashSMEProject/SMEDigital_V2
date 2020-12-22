package com.rsaame.pas.ui.cmn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * 
 * @author m1017935
 * This RH is used add new sections
 *
 */
public class AddSectionRH implements IRequestHandler {

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
			
		List<Integer> selSections = CopyUtils.asList( policyContext.getAllSelectedSections() );
		if(Utils.isEmpty(selSections)){
			selSections=new ArrayList<Integer>();
		}
		selSections.add(sectionId);
		SectionList sectionList = new SectionList();
		sectionList.setPolicyLinkingId(policyContext.getPolicyDetails().getPolLinkingId());
		sectionList.setSelectedSec(selSections);
		sectionList.setEndorsementNo(Long.valueOf("0"));	
		
		String action = (String) request.getAttribute( "opType" );
		TaskExecutor.executeTasks( action,sectionList);
		/* we have the selected section ID*/
		return resp;
	}

}
