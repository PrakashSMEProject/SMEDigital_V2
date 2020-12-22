package com.rsaame.pas.kaizen.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;

import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;

/**
 * @author m1014644
 *
 * Set the user details cached in acegi into service context.
 */
public class ServiceContextInit{

	private static final Logger logger = Logger.getLogger( ServiceContextInit.class );
	private static final String ACEGI_SECURITY_CONTEXT = "ACEGI_SECURITY_CONTEXT";
	private static final String SESSION_USER = "USER";
	private static final String CTX_LOCATION = "LOCATION";

	public void setServiceContext( HttpServletRequest request ){
		if( logger.isInfo() ) logger.info( "Entering setServiceContext method" );
		HttpSession session = request.getSession( false );
		SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute( ACEGI_SECURITY_CONTEXT );
		if( securityContext != null ){
			RSAUser user = (RSAUser) session.getAttribute( SESSION_USER );
			if( user == null ){
				if( logger.isDebug() ) logger.debug( "Setting user detials to session from acegi" );
				user = getUser( securityContext );
				session.setAttribute( SESSION_USER, user );
			}
			// PUT USER IN THREADLOCAL
			ServiceContext.setUser( user );
			if( ServiceContext.getMessage() != null ){
				ServiceContext.setMessage( null );
			}

			//PUT LOCATION DATA IN THREADLOCAL
			if( session.getAttribute( CTX_LOCATION ) != null ){
				ServiceContext.setLocation( (String) session.getAttribute( CTX_LOCATION ) );
			}
		}
		if( logger.isInfo() ) logger.info( "Exiting setServiceContext method" );
	}

	private static synchronized RSAUser getUser( SecurityContextImpl securityContext ){
		Authentication authentication;
		if( securityContext != null ){
			authentication = securityContext.getAuthentication();
		}
		else{
			authentication = SecurityContextHolder.getContext().getAuthentication();
		}
		return authentication.getPrincipal() instanceof RSAUser ? (RSAUser) authentication.getPrincipal() : null;
	}

}
