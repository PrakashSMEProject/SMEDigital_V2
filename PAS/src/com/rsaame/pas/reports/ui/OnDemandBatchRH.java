package com.rsaame.pas.reports.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;

public class OnDemandBatchRH implements IRequestHandler{
	private final static Logger logger = Logger.getLogger( OnDemandBatchRH.class );

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
