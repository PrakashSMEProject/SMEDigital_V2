package com.rsaame.pas.quote.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Response.Type;

public class RiskSectionRH implements IRequestHandler {
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse responseObj) {

		System.out.println("Inside RiskSectionRH -->");
		Response response = new Response();
		return response;
	}

}
;