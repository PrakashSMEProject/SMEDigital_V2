package com.rsaame.pas.home.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;

public class HomePageContentRH implements IRequestHandler {
	@Override
	public Response execute(HttpServletRequest httpservletrequest,
			HttpServletResponse responseObj) {
		
		Response response = new Response();
		Redirection redirection = new Redirection( "/jsp/homePage_content.jsp", Type.TO_JSP );
		response.setRedirection( redirection );
		return response; 
	}

}
