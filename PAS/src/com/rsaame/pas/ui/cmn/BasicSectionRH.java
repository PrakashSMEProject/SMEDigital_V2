package com.rsaame.pas.ui.cmn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;

public class BasicSectionRH implements IRequestHandler{

	private static final String REQ_PARAM_SEC_ID="sectionId";
	private static final String REQ_PARAM_CHKBX_ID="checkBoxId";
	
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		Response resp = new Response();
		Boolean basicSectionFlag=false;
		final Integer ZERO = 0;
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		String secId =  request.getParameter( REQ_PARAM_SEC_ID );
		String chckBoxId=request.getParameter(REQ_PARAM_CHKBX_ID);
		
		/*If section id is empty for the current section to be added, throw a business exception*/

		if(Utils.isEmpty(secId))
			throw new BusinessException( "SectionID not found", null, "Section id is empty for the current add section flow." );
		
		Integer sectionId=new Integer(secId);
		Integer basicSectionId=null;
		if(policyContext.getAllSelectedSections().length!=ZERO){
			basicSectionId=policyContext.getBasicSectionId();
		}
		/*To check if basic section has not been selected.*/
		if(Utils.isEmpty(basicSectionId)){
			if((sectionId.equals(AppConstants.SECTION_ID_PAR) ) || 
					(sectionId.equals(AppConstants.SECTION_ID_PL) )){
				basicSectionFlag=true;
			}
		}
				
		/*Preparing the response data in a list */
		List<String> respValues = new ArrayList<String>();
		respValues.add(basicSectionFlag.toString());
		respValues.add(Utils.isEmpty(basicSectionId)?null:basicSectionId.toString());
		respValues.add(sectionId.toString());
		respValues.add(chckBoxId);
		
		resp.setData(respValues);
		return resp;
	}

}
