package com.rsaame.pas.b2c.WsAuthentication.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.validators.ErrorResponse;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2c.WsAuthentication.BasicAuthenticationService;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class WebServiceFilter implements Filter {
	private final static Logger LOGGER = Logger.getLogger(WebServiceFilter.class);
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException, ValidationException {
		try {
			LOGGER.info("Starts Inside WebServiceFilter:doFilter");
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			BasicAuthenticationService basicAuthenticationService = new BasicAuthenticationService();
			String authorization = httpServletRequest.getHeader("Authorization");
			if(Utils.isEmpty(authorization)) {
				LOGGER.error("Authentication erro_1");
				sendError(response);
	            return;
				
			} else if (basicAuthenticationService.authenticateUser(authorization)) {
				chain.doFilter(request, response);
			} else {
				LOGGER.error("Authentication erro_2");
				sendError(response);

			}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
			LOGGER.error("Authentication erro_3");
			sendError(response);
		}

	}

	private void sendError(ServletResponse response) throws IOException {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(""+HttpServletResponse.SC_UNAUTHORIZED);
		errorResponse.setField("Authorization");
		errorResponse.setType("ERROR");
		errorResponse.setMessage("Unauthorized Access");
		errorResponse.setCategory("System");
		
		byte[] responseToSend = restResponseBytes(errorResponse);
		((HttpServletResponse) response).setHeader("Content-Type", "application/json");
		((HttpServletResponse) response).setStatus(401);
		response.getOutputStream().write(responseToSend);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
    private byte[] restResponseBytes(ErrorResponse errorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(errorResponse);
        return serialized.getBytes();
    }

}
