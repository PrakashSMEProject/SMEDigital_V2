/**
 * 
 */
package com.rsaame.pas.promotionalcode.ui;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;

import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;

import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection.Type;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1017160
 *
 */
public class PromotionalCodeConfigLoadRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( PromotionalCodeConfigLoadRH.class );
	final static Redirection addCovers = new Redirection( "/jsp/addCovers.jsp", Type.TO_JSP );
	final static Redirection addDiscount = new Redirection( "/jsp/addDiscountForPromoCode.jsp", Type.TO_JSP );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		String action = request.getParameter( "action" );
		String prd = request.getParameter( "product" );
		String prCode = request.getParameter( "promoCode" );
		Response response = new Response();
		logger.debug( "PromotionalCodeLoadRH --------> Processing to load the page" );
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		AppUtils.setUserProfileDetsToRequest( request, userProfile );
		request.setAttribute( AppConstants.USER_ID, userProfile.getRsaUser().getUserId().toString() );
		logger.debug( "PromotionalCodeLoadRH --------> Navigating to promotionalCodeConfig.jsp" );

		if( "ADD_COVERS".equalsIgnoreCase( action ) ){

			response.setRedirection( addCovers );
			request.setAttribute( "lob", prd );
			request.setAttribute( "promo", prCode );

		}
		else if( "PRODUCT_CHANGE".equals( action ) ){
			request.getSession( ).removeAttribute( "FreeCovers" );
		}
		else if( "DISCOUNT".equalsIgnoreCase( action) ){
			LookUpListVO listVO = SvcUtils.getLookUpCodesList( "SCHEME","ALL",prd );
			
			if(!Utils.isEmpty( listVO ) &&  !Utils.isEmpty( listVO.getLookUpList() )){
				java.util.List<LookUpVO> list = listVO.getLookUpList();
				request.setAttribute( "schemesList",list );
			}
			
			
			request.setAttribute( "lob", prd );
			response.setRedirection( addDiscount );

		}

		return response;

	}

}
