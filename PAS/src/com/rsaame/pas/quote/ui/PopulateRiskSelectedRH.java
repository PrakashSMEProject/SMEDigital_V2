package com.rsaame.pas.quote.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.SectionList;

public class PopulateRiskSelectedRH implements IRequestHandler {

	private static final Logger logger = Logger.getLogger( PopulateRiskSelectedRH.class );
	private static String POPULATE_RISK_SELECTEDRH = "PopulateRiskSelectedRH";
	
	
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Response responseObj = new Response();
		
		/* Populate Policy Context with Selected Risk Sections */
		PolicyContext polContext = PolicyContextUtil.getPolicyContext(request);
		
		/**
		 * SK : Changes
		 * Fetch the request parameters whose name signifies the section id selected by the user.
		 * Hence the number of parameters obtained from request will signify the number of 
		 * sections selected by the user
		 */
		if( Utils.isEmpty( polContext.getAllSelectedSections() ) || Utils.isEmpty( polContext.getCurrentSectionId() ) ) populateSecIdWithinPolicyContext( request, polContext );
		
		Redirection redirection = new Redirection("GET_COMMISSION",Redirection.Type.TO_NEW_OPERATION);
		
		responseObj.setRedirection(redirection);
		return responseObj;
	}
	
	/** SK : Changes
	 * This method will populate the section id selected by the user to policy context
	 * @param request
	 * @param polContext
	 */
	private void populateSecIdWithinPolicyContext( HttpServletRequest request, PolicyContext polContext ){

		/*
		 * Obtain the keyset from request to fetch the parameters submitted from 
		 * form except section id
		 */
		Set<String> s = request.getParameterMap().keySet();
		String key1 = "sectionId";
		/** This key has to be left out of the list being created for number of sections selected */
		String key2 = "opType";
		/** This key has to be left out of the list being created for number of sections selected */

		List<Integer> parameterNames = new ArrayList<Integer>();

		/*
		 * Let out the parameter "sectionId" from the list being created for section id's selected by the user
		 */
		for( String str : s ){
			
			if( !Utils.isEmpty(str) && !str.startsWith( key1 ) && !str.startsWith( key2 ) && isNumeric(str) ) 
				parameterNames.add( Integer.valueOf( str ) );
		}
		Collections.sort( parameterNames );

		/*The below code is used to sort the sections is such an order so that PAR or PL 
		(basic sections always appears before other section on UI)*/
		parameterNames=AppUtils.sortSections(parameterNames);

		if( parameterNames.size() > 0 ){
			Integer sectionIds[] = CopyUtils.toArray( parameterNames );
			polContext.populateSelectedSec( sectionIds );
			/*Added to save selected risk sections list in to DB so that the selected risk information is not lost in case
			 *  of user is not saving all the selected sections */
			SectionList sectionList=new SectionList();
			sectionList.setSelectedSec( Arrays.asList(sectionIds) );
			if(!Utils.isEmpty( polContext.getPolicyDetails() )){
			sectionList.setPolicyLinkingId( polContext.getPolicyDetails().getPolLinkingId() );
			//TODO: Confirm where to get Endorsement number from
			sectionList.setEndorsementNo( polContext.getPolicyDetails().getEndtId() );
			}
			TaskExecutor.executeTasks( AppConstants.SAVE_SELECTED_SECTIONS,sectionList);
		}

	}

	private  boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}
}