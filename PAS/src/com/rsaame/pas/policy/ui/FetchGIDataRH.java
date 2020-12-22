package com.rsaame.pas.policy.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * This request handler picks up the GeneralInfoVO instance from the current PolicyContext and sends it back to the browser
 * as JSON.
 */
public class FetchGIDataRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( FetchGIDataRH.class );
	private static final String VIEW_POLICY_RH = "FetchGIDataRH";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		if( logger.isDebug() ) logger.debug( "Enetring execute method of " + VIEW_POLICY_RH );
		//START: SIT BUG FIX: Added to Initializing the isPrepackaged flag in case of view policy
		TaskExecutor.executeTasks( AppConstants.SET_PRE_PACKAGE_FLAG, PolicyContextUtil.getPolicyContext( request ).getPolicyDetails() );
		//END SIT BUG FIX: Added to Initializing the isPrepackaged flag in case of view policy
		
		PolicyVO policy = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();
		
		/* The default response for this request handler is JSON JSP. However, if we don't have General Info values, this may set the
		 * screen with blank values. Hence, in such a case, respond with JSONRF with no data. */
		if( Utils.isEmpty( policy ) || Utils.isEmpty( policy.getGeneralInfo() ) ){
			Response resp = new Response( Response.Type.JSON, null, null );
			resp.setSuccess( false );
			
			return resp;
		}
		
		request.setAttribute( "policyDetails", PolicyContextUtil.getPolicyContext( request ).getPolicyDetails() );

		return null;
	}
}
