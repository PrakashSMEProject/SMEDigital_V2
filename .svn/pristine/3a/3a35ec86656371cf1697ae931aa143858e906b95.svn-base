package com.rsaame.pas.partnermanagement.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;

/**
 * This request handler will be invoked while loading the screen
 * 
 * @author M1020859
 *
 */
public class AdminRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( AdminRH.class );
	final static Redirection promotionalCodePage = new Redirection( "/jsp/promotionalCodeConfig.jsp", Type.TO_JSP );
	final static Redirection partnerMgmtPage = new Redirection( "/jsp/partnerManagement.jsp", Type.TO_JSP );
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();
		String action = request.getParameter( "action" );
		logger.debug( "AdminRH --------> Processing to load the page" );
		logger.debug( "AdminRH --------> Loading opType ---> "+request.getParameter( "opType" ) );
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		AppUtils.setUserProfileDetsToRequest( request, userProfile );
		request.setAttribute( AppConstants.USER_ID, userProfile.getRsaUser().getUserId().toString() );
		if("PROMOPAGE".equals( action )){
			
			logger.debug( "AdminRH --------> Navigating to promotionalCodeConfig.jsp" );
			response.setRedirection( promotionalCodePage );
			
		}else if("PARTNERMGMT".equals( action )){
			
			logger.debug( "AdminRH --------> Navigating to partnerManagement.jsp" );
			response.setRedirection( partnerMgmtPage );
			
		}
		
		return response;
	}

}
