package com.rsaame.pas.web;

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

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.utils.ServiceContextInit;
import com.rsaame.pas.util.AppConstants;

/**
 * 
 * @author m1014644
 * 
 *         Sets the user profile into session during user login.
 *         Set the user details cached in acegi into service context.
 */
public class PASUserProfileInit implements Filter {

	private static final Logger logger = Logger
			.getLogger(PASUserProfileInit.class);
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

		if (logger.isDebug()) {
			logger.debug("Request Paramaters ::"
					+ servletrequest.getParameterNames());
			logger.debug("Path ::_1" + req.getServletPath());
			logger.debug("Path ::_2" + req.getContextPath());
		}

		if (logger.isInfo())
			logger.info("Path ::_3" + req.getPathInfo());

		Enumeration<String> paramNames = servletrequest.getParameterNames();

		while (paramNames.hasMoreElements()) {
			String pramName = (String) paramNames.nextElement();
			if (pramName.equalsIgnoreCase("opType")) {
				/**
				 * This method sets the user details in the thread local of the
				 * current logged in user's current thread.
				 * 
				 */
				if (logger.isDebug())
					logger.debug("Setting user details to serviceContext");
				setServiceContext(req);
				/*******************************/

				String name = servletrequest.getParameter(pramName);
				if (name.equalsIgnoreCase("HOME_PAGE")) {
					if (logger.isDebug())
						logger.debug("Request from login page");
					if (Utils.isEmpty(req.getSession(false).getAttribute(
							AppConstants.SESSION_USER_PROFILE_VO))) {
						if (logger.isDebug())
							logger.debug("Setting userprofile into session: SESSION_USER_PROFILE_VO");
						UserProfile userProfile = new UserProfile();
						req.getSession(false).setAttribute(
								AppConstants.SESSION_USER_PROFILE_VO,
								userProfile);
					}
				}
			}
		}

		filterchain.doFilter(servletrequest, servletresponse);

	}

	private void setServiceContext(HttpServletRequest request) {
		
		ServiceContextInit svcInit = new ServiceContextInit();
		svcInit.setServiceContext(request);
	}


	@Override
	public void destroy() {
		this.config = null;
	}

}
