package com.rsaame.pas.web;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.CommonUtils;
import com.rsaame.pas.cmn.vo.UserProfile;

/**
 * 
 * @author m1014644
 * 
 *         Sets the user profile into session during user login.
 *         
 */
public class PASUserProfileInit implements HandlerInterceptor{

	private static final Logger logger = Logger.getLogger( PASUserProfileInit.class );
	FilterConfig config = null;
	ServletContext ctx = null;

	@Override
	public void afterCompletion( HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3 ) throws Exception{
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	@Override
	public void postHandle( HttpServletRequest req, HttpServletResponse arg1, Object arg2, ModelAndView arg3 ) throws Exception{

		String name = "HOME_PAGE";
		if( name.equalsIgnoreCase( "HOME_PAGE" ) ){
			if( logger.isDebugEnabled() ) logger.debug( "Request from login page" );
			
			if( CommonUtils.isEmpty( req.getSession( false ).getAttribute( AppConstants.SESSION_USER_PROFILE_VO ) ) ){
				if( logger.isDebugEnabled() ) logger.debug( "Setting userprofile into session: SESSION_USER_PROFILE_VO" );
				UserProfile userProfile = new UserProfile();
				req.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
			}
		}
	}

	@Override
	public boolean preHandle( HttpServletRequest arg0, HttpServletResponse arg1, Object arg2 ) throws Exception{
		return true;
	}
}
