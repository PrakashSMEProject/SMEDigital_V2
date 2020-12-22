package com.rsaame.pas.reports.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import antlr.Utils;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author M1014957
 *
 */
public class ReportsRH implements IRequestHandler {

	private final static Logger logger = Logger.getLogger( ReportsRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();
		logger.debug( "*****Inside SearchReports*****" );
		UserProfile userProfile =  AppUtils.getUserDetailsFromSession( request );
		AppUtils.setUserProfileDetsToRequest(request,userProfile);	
		PolicyContext policyDetails=PolicyContextUtil.getPolicyContext(request);
		if(!com.mindtree.ruc.cmn.utils.Utils.isEmpty(policyDetails)){
			policyDetails = null;
			PolicyContextUtil.setPolicyContext(request, null);
		}
		return response;
	}
}

