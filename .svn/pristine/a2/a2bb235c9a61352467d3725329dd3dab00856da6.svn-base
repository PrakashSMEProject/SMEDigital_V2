package com.rsaame.pas.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.utils.ServiceContextInit;
import com.rsaame.pas.util.AppConstants;

/**
 * 
 * @author m1020637
 * 
 *         For Audit logging
 */
public class PASAuditLogger implements Filter {

	
	FilterConfig config = null;

	ServletContext ctx = null;

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		this.config = filterconfig;
		this.ctx = config.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) servletrequest;

		
		//audit Log
				UserProfile userProfile = (UserProfile) req.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
				String userId = "";
				if(!Utils.isEmpty(userProfile) &&!Utils.isEmpty(userProfile.getRsaUser().getUserId())){
					userId = userProfile.getRsaUser().getUserId().toString();
				}
				String activity = "";
				if(!Utils.isEmpty(req.getParameter( "action" ))){
					activity = req.getParameter( "action" );
				}
					
		       	AuditLogger.info(userId,activity , "activityDetail",req);
		       	
		//audit Log

		filterchain.doFilter(servletrequest, servletresponse);

	}

	/*private void setServiceContext(HttpServletRequest request) {
		
		ServiceContextInit svcInit = new ServiceContextInit();
		svcInit.setServiceContext(request);
	}*/


	@Override
	public void destroy() {
		this.config = null;
	}

}
