package com.rsaame.pas.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;

public class LoginPageRH implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) {
		
		Response response = new Response();
		Redirection redirection = new Redirection("/jsp/login/login.jsp",Redirection.Type.TO_JSP);
		response.setRedirection( redirection );
		
		return response;
	}

}
