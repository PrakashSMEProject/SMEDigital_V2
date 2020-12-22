/**
 * 
 */
package com.rsaame.pas.customer.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.vo.bus.CustomerSearchVO;

/**
 * @author M1016284
 *
 */
public class CustomerSearchResultsRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( CustomerSearchResultsRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		
		
        
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		if( logger.isDebug() ){
			logger.debug( "opType-->" + identifier );
			logger.debug( "Inside SearchTransactionRH" );
			logger.debug( "transQuoteNo: " + request.getParameter( "customernameName" ) );
			logger.debug( "transTransactionFrom: " + request.getParameter( "customerIDName" ) );
			logger.debug( "transTransactionTo: " + request.getParameter( "policyQuoteNo" ) );
			logger.debug( "transSearchQuote: " + request.getParameter( "phoneNumberName" ) );
			logger.debug( "transSearchPolicy: " + request.getParameter( "emailIdName" ) );
			logger.debug( "transLastTransaction: " + request.getParameter( "mobileNumberName" ) );
			logger.debug( "transExactSearch: " + request.getParameter( "poboxName" ) );
		
		}

		/*
		 * As part of processing, convert the HTTP request object obtained to required VO 
		 * by using request to bean mapper available as part of framework.
		 */
		CustomerSearchVO customerVO = BeanMapper.map( request, CustomerSearchVO.class );
		logger.debug( "Mapping done. Bean Mapper created." );
		
		logger.debug( "*****Inside SearchTransactionScreenRH*****" );
		/*request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, "SEARCH_QUO" );
		request.setAttribute( AppConstants.SCREEN_NAME, "SBS_SEARCH_TRANSACTION" );*/

		BaseVO baseVO = TaskExecutor.executeTasks( identifier, customerVO );
		logger.debug( "Executed taskExecutor" );
		logger.debug( "baseVO -->" + baseVO );

		if( !Utils.isEmpty( baseVO ) ){
			response.setSuccess( true );
			response.setData( baseVO );
		}

		return response;
	}
	
}
