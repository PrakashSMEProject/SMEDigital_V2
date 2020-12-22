/**
 * 
 */
package com.rsaame.pas.insured.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;

/**
 * @author M1014739
 *
 */
public class SearchInsuredScreenRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( SearchInsuredScreenRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();	
		logger.debug( "*****Inside SearchInsuredScreenRH*****" );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, "SEARCH_QUO" );
		request.setAttribute( AppConstants.SCREEN_NAME, "SBS_SEARCH_INSURED" );
		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_CURLOB ) ))
			request.setAttribute( com.Constant.CONST_CURLOB, (request.getParameter( com.Constant.CONST_CURLOB )));
		else
			request.setAttribute( com.Constant.CONST_CURLOB, (request.getAttribute( com.Constant.CONST_CURLOB )));
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		String branch = "";
		if( !Utils.isEmpty( userProfile ) && !Utils.isEmpty( request.getParameter( com.Constant.CONST_CURLOB ) ) ){
			branch= userProfile.getRsaUser().getBranchCode().toString();
		}
		request.setAttribute( "branch", branch);
		return response;
	}
}
