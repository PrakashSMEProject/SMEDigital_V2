package com.rsaame.pas.sample.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Response.Type;


public class TestRH implements IRequestHandler{

	@Override
	public Response execute(HttpServletRequest httpservletrequest,
			HttpServletResponse responseObj) {

		Response response = new Response();
		return response;
	}

}
