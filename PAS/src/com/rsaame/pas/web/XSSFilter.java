package com.rsaame.pas.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rsaame.pas.cmn.filter.XSSRequestWrapper;

public class XSSFilter implements Filter {
	
    private static final String HDR_CACHE_CONTROL = "Cache-Control";
    private static final String HDR_PRAGMA = "Pragma";
    private static final String HDR_EXPIRES = "Expires";
    private static final String HDR_CACHE_CONTROL_VALUE = "no-cache, no-store,post-check=1,pre-check=2,must-revalidate";
    private static final String HDR_PRAGMA_VALUE = "no-cache";
    private static final String HDR_EXPIRES_VALUE = "Tue, 03 Jul 2001 06:00:00 GMT";

	@Override
	public void destroy() {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse resp = (HttpServletResponse)response;
		resp.setHeader(HDR_CACHE_CONTROL, HDR_CACHE_CONTROL_VALUE);
        resp.setHeader(HDR_PRAGMA, HDR_PRAGMA_VALUE);
        resp.setHeader(HDR_EXPIRES, HDR_EXPIRES_VALUE);
        resp.setContentType ("text/html;charset=utf-8");
        resp.setDateHeader("Expires", 0);
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
		
		 chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}


}
