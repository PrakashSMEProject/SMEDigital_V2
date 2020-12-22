package com.rsaame.pas.web;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.MVCUtils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.servlet.BaseServlet;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;

/**
 * Servlet implementation class PASServlet
 */
public class PASServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PASServlet(){
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init( ServletConfig config ) throws ServletException{
		super.init( config );
	}

	/**
	 * Override this method, if the priority has to be different from configured redirection.
	 * @param request
	 * @param response
	 * @param handlerResponse
	 * @param redirection
	 * @return
	 */
	@Override
	protected int responsePriority( HttpServletRequest request, HttpServletResponse response, Response handlerResponse, Redirection redirection ){
		
		if(MVCUtils.respTypeNeedsRedirection( handlerResponse )){
			return REDIRECTION;
		}
		return CONTENT_TYPE;
	}
	
	/**
	 * Performs some destruction activities like cache manager shutdown.
	 */
	@Override
	public void destroy(){
		CacheManagerFactory.getCacheManager().shutdown();
		super.destroy();
	}

	/**
	 * Calls {@link PolicyContext}.endTransaction() if the request handler execution didn't throw any exception.
	 */
	@Override
	protected void postProcessing( HttpServletRequest request, HttpServletResponse response, boolean rhThrewException ){
		/* End PolicyContext transaction here. This is being done here to handle cases of exceptions being thrown after the transaction
		 * was started but before it could be committed. However, if there was no exception from the request handler, then we should 
		 * keep the transaction open, so that the next call can take the saved data. */
		if( rhThrewException ){
			PolicyContext pc = PolicyContextUtil.getPolicyContext( request );
			if( !Utils.isEmpty( pc ) ) pc.endTransaction();
		}
		
		/* Clear ThreadLevelContext completely. The purpose of ThreadLevelContext is for communication within a request. */
		ThreadLevelContext.clearAll();
	}
}
