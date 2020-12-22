/*
 * AMEFilter.java
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Mark’s Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.kaizen.framework.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.cmn.context.ThreadLocationContext;


/**
 * @author Cognizant Technology Solutions
 */

public class AMEFilter implements Filter, AMEConstants {
    
    // LOGGER INSTANCE
    private static final AMELogger logger = AMELoggerFactory.getInstance().getLogger(AMEFilter.class);
    private final static Logger log = Logger.getLogger( AMEFilter.class );
    private static final String CTX_DOFLTR = "doFilter()";
    
    private static final String HDR_CACHE_CONTROL = "Cache-Control";
    private static final String HDR_PRAGMA = "Pragma";
    private static final String HDR_EXPIRES = "Expires";
    private static final String HDR_CACHE_CONTROL_VALUE = "no-cache, no-store,post-check=1,pre-check=2,must-revalidate";
    private static final String HDR_PRAGMA_VALUE = "no-cache";
    private static final String HDR_EXPIRES_VALUE = "Tue, 03 Jul 2001 06:00:00 GMT";
    private final int customSessionExpiredErrorCode = 901;
    private String mode = "SAMEORIGIN";
    FilterConfig config = null;

    ServletContext ctx = null;

    public AMEFilter() {
        super();
    }

    @Override
	public void init(FilterConfig arg0) throws ServletException {
        this.config = arg0;
        this.ctx = config.getServletContext();
        String configMode = config.getInitParameter("mode");
        if ( null!= configMode ) {
            mode = configMode;
    }
    }

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse)response;
        // code added for disabling browser cache starts here
        logger.info(CTX_DOFLTR, "Setting no caching parameter");
        resp.setHeader(HDR_CACHE_CONTROL, HDR_CACHE_CONTROL_VALUE);
        resp.setHeader(HDR_PRAGMA, HDR_PRAGMA_VALUE);
        resp.setHeader(HDR_EXPIRES, HDR_EXPIRES_VALUE);
        resp.setContentType ("text/html;charset=utf-8");
        resp.setDateHeader("Expires", 0);
        resp.setHeader("X-FRAME-OPTIONS", mode );
        resp.setHeader("Strict-Transport-Security", "max-age=31622400; includeSubDomains");
        resp.setHeader("Content-Security-Policy"," default-src *  data: blob: 'unsafe-inline' 'unsafe-eval';"
        				+ "script-src * data: blob: 'unsafe-inline' 'unsafe-eval'; "
        				+ "connect-src * data: blob: 'unsafe-inline'; "
        				+ "img-src * data: blob: 'unsafe-inline'; "
        				+ "frame-src * data: blob: ; "
        				+ "style-src * data: blob: 'unsafe-inline';"
        				+ "font-src * data: blob: 'unsafe-inline';");
        resp.setHeader("Set-Cookie", "key=value; secure; HttpOnly; SameSite=Strict"); 
        /*
         * Cross Frame Scripting - FIX
         * Browser support since: Opera 10.50, IE 8, Firefox 3.6.9, Chrome 4.1.249.1042, Safari 4
         */
        resp.setHeader("X-XSS-protection", "1; mode=block");
        // code added for disabling browser cache ends here       
        
        logger.debug(CTX_DOFLTR, "uId ::" + req.getParameterNames());
        Enumeration enum1 = req.getParameterNames();
        logger.debug(CTX_DOFLTR, "Path ::_1" + req.getServletPath());
        logger.info(CTX_DOFLTR, "Path ::_2" + req.getPathInfo());
        logger.debug(CTX_DOFLTR, "Path ::_3" + req.getContextPath());
        while(enum1.hasMoreElements()) {
            String name = (String)enum1.nextElement();
            logger.debug(CTX_DOFLTR, "Name :: " + name + req.getParameter(name));
        }
        boolean validatioRequired = true;
        logger.debug(CTX_DOFLTR, " ------------ SESSION ------- :: " + req.getSession(false));
        // CHECK IF THIS IS THE LOGIN REQUEST:
        // IF THIS IS LOGIN REQUEST POPULATE NEW SESSION WITH THE LOGIN INFO
        if(req.getParameter("j_username") != null) {
            HttpSession sess = req.getSession();
            sess.invalidate();
            sess = req.getSession(true);
            String randomToken =  generateAndSetToken(sess,request);
            validatioRequired = false;
            request.setAttribute( com.Constant.CONST_TOKEN, randomToken );
           	String uId = req.getParameter("j_username");
            String location = req.getParameter(CTX_LOCATION);
            logger.debug(CTX_DOFLTR, "Username ::" + uId);
            logger.debug(CTX_DOFLTR, "Location ::" + location);
           
            ServiceContext.setLocation(location);
            if(location != null){
            	//ThreadLevelContext.set( "THREAD_LOCATION" , location );
            	ThreadLocationContext.set(location);
            }
        
            // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0)
            ServiceContext.setLoginLocation(location);
            // IF THE LOCATION IS NOT THERE IN SESSION AND IS NOT NULL
            if (sess.getAttribute(CTX_LOCATION) == null && location != null) {
                sess.setAttribute(CTX_LOCATION, location);
            }
            // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0)
            if (sess.getAttribute(CTX_LOGIN_LOCATION) == null && location != null) {
                sess.setAttribute(CTX_LOGIN_LOCATION, location);
                  }
//          Release 4.2 25.07.2011 Oman Border Implementation -Arabic Interface  in Kaizen
            if(req.getParameter("LANGUAGE") != null) {  
            	 String Lang = req.getParameter(SESSION_LOGIN_LANGUAGE);
                 logger.debug(CTX_DOFLTR, "CTX_LOGIN_LANGUAGE ::" + Lang);
               //  logger.debug(CTX_DOFLTR, "Location ::" + location);
                 sess.setAttribute(SESSION_LOGIN_LANGUAGE,Lang);
                 ServiceContext.setSESSION_LOGIN_LANGUAGE(Lang);
            
            }
            else{
            	 sess.setAttribute(SESSION_LOGIN_LANGUAGE,DEFAULT_LANG);
                 ServiceContext.setSESSION_LOGIN_LANGUAGE(DEFAULT_LANG);
            }
        }
     // To avoid empty else block (Sonar defect) 12-9-2017
        else {
        	log.debug("Added logger to avoid empty else block");
            // COMMENTING AS THIS WILL ANYWAYS BE TAKEN CARE BY ACEGI----------
            // CHECK IF THE SESSION ALREADY EXIST FOR THE USER
           /* if(!(req.getSession(false) != null)) {
                // IF IT DOES NOT EXIST THIS IS A NEW REQUEST AND USER SHUD LOGIN FIRST
                resp.setHeader("cRIAteResponse", "LoginRequired");
                throw new ServletException("User not logged in..");
            }*/
        }
        //If Session expires then response is Redirected to Login page 
/*        if(req.getSession(false)==null &&   req.getPathInfo()!=null && req.getPathInfo().equalsIgnoreCase(ACEGI_LOG_OUT)){
        	    logger.debug(CTX_DOFLTR,"Session Expired");
        		resp.sendRedirect(LOG_OUT_PATH);
        		return;
			
        }*/

        HttpSession currentSession = req.getSession(false);
        
        currentSession = validateUserToken(currentSession,request,req,validatioRequired);

		if( currentSession == null ){
			String ajaxHeader = req.getHeader( "X-Requested-With" );

			if( "XMLHttpRequest".equals( ajaxHeader ) ){
				logger.info( "Ajax call detected, send {} error code", this.customSessionExpiredErrorCode );
				//resp.sendError(this.customSessionExpiredErrorCode);
				try{
					resp.setStatus( this.customSessionExpiredErrorCode );
					resp.flushBuffer();
				}
				catch( Exception e ){
					logger.error( "Exception Occured while session is expired", e.getMessage() );
				}

				return;
			}
			else if( ( req.getPathInfo() != null && req.getPathInfo().equalsIgnoreCase( ACEGI_LOG_OUT ) )
					|| ( req.getRequestURI() != null && req.getRequestURI().indexOf( ACEGI_LOG_OUT ) > 0 ) ){
				logger.debug( CTX_DOFLTR, "Session Expired" );
				resp.sendRedirect( req.getContextPath() + LOG_OUT_PATH );
				return;
			}else if(  req.getPathInfo() == null && req.getServletPath() != null &&  !req.getServletPath().endsWith( "login.jsp" ) && !validatioRequired  ) {
				logger.debug( CTX_DOFLTR, "Invalid url" );
				resp.sendRedirect( req.getContextPath() + "/jsp/login/login.jsp" );
				return;
			}
		}
		else{
			request.setAttribute( com.Constant.CONST_TOKEN, CacheManagerFactory.getCacheManager().get( PASCache.USER_TOKEN, currentSession.getId() ) );
		}
        
       	chain.doFilter(request, response);

    }

	private HttpSession validateUserToken( HttpSession currentSession, ServletRequest request, HttpServletRequest req, boolean validatioRequired ){
		//req.getRequestURI().contains( "PASServlet" )
		if( !Utils.isEmpty( CacheManagerFactory.getCacheManager() ) && currentSession != null && validatioRequired){
			Object cachedObject = CacheManagerFactory.getCacheManager().get( PASCache.USER_TOKEN, currentSession.getId() );
			if( cachedObject != null ){
				String randomToken = (String) cachedObject;
				String token = !Utils.isEmpty( request.getParameter( com.Constant.CONST_TOKEN )  )?request.getParameter( com.Constant.CONST_TOKEN ) : (String)request.getAttribute( com.Constant.CONST_TOKEN );
				if( !randomToken.equals(token) ){
					currentSession = null;
				}
			}
			else{
				currentSession = null;
			}
		}
		return currentSession;
	}

	private String generateAndSetToken( HttpSession sess, ServletRequest request ){
		String randomToken = null;
		if( CacheManagerFactory.getCacheManager().hasCachedData( PASCache.USER_TOKEN, sess.getId() ) ){
			randomToken = (String) CacheManagerFactory.getCacheManager().get( PASCache.USER_TOKEN, sess.getId() );
		}
		else{
			randomToken = RandomStringUtils.randomAlphanumeric( 50 );
			CacheManagerFactory.getCacheManager().put( PASCache.USER_TOKEN, sess.getId(), randomToken );
		}
		return randomToken;
	}

	@Override
	public void destroy() {
        this.config = null;
    }
}
