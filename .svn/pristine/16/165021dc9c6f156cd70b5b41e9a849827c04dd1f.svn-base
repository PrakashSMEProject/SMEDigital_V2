package com.rsaame.pas.ui.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;

public class LaunchInforMapRH implements IRequestHandler {
	private final static Logger logger = Logger.getLogger(LaunchInforMapRH.class);

	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse responseObj) {
		logger.debug( " In method LaunchInforMapRH-execute");
		Response response = new Response();
		String lob=request.getParameter("lob");
		request.setAttribute(AppConstants.CURRENT_LOB, lob);
		String informapUrl=Utils.getSingleValueAppConfig( "INFORMAP_SERVICE_URL" );
		request.setAttribute(AppConstants.INFORMAP_URL, informapUrl);
		
		return response;
		
	}

}
