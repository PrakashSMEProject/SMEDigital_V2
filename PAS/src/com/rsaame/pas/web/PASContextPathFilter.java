package com.rsaame.pas.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;

/**
 * Servlet Filter implementation class PASContextPathFilter
 */
public class PASContextPathFilter implements Filter{

	/**
	 * Default constructor. 
	 */
	public PASContextPathFilter(){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy(){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException{

		PASServiceContext.setPasreportsCtxPath( Utils.getSingleValueAppConfig( com.Constant.CONST_PAS_REPORTS_APP_CONTEXT_PATH ) );
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		req.getSession().setAttribute( com.Constant.CONST_PAS_REPORTS_APP_CONTEXT_PATH, Utils.getSingleValueAppConfig( com.Constant.CONST_PAS_REPORTS_APP_CONTEXT_PATH ) );
		
		chain.doFilter( request, response );
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init( FilterConfig fConfig ) throws ServletException{
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

}
