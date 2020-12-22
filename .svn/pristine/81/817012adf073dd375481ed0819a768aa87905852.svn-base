/**
 * 
 */
package com.rsaame.pas.quote.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;

/**
 * @author M1016284
 *
 */
public class SearchTransactionScreenRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( SearchTransactionScreenRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();

		logger.debug( "*****Inside SearchTransactionScreenRH*****" );
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, "SEARCH_QUO" );
		request.setAttribute( AppConstants.SCREEN_NAME, "SBS_SEARCH_TRANSACTION" );

		/* User profile will always be available , in case of navigation flow it is always assumed to be present and 
		 * will result into exception scenario if not present */
		UserProfile userProfile =  AppUtils.getUserDetailsFromSession( request );
		AppUtils.setUserProfileDetsToRequest(request,userProfile);
		return response;
	}

}
