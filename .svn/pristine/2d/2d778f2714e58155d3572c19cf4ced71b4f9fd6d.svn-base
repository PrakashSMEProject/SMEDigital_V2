package com.rsaame.pas.renewals.ui;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.RenewalSearchSummaryVO;

/**
 * @author M1019836
 *  This RH class process the search criteria and returns the search results
 */
public class PrintRenewalSearchRH implements IRequestHandler {

	private final static Logger logger = Logger.getLogger( PrintRenewalSearchRH.class );
	private final static String EMPTY_STRING = "";
	
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();
		String identifier = null;

		// Identifier will be used further in TaskExecutor to identify
		// request processing
		identifier = request.getParameter( "opType" );
			
		if( logger.isDebug() ){
			logger.debug( "opType-->" + identifier );
			logger.debug( "Inside printRenewalSearchRH" );
			logger.debug( "transClazz: " + request.getParameter( "transClazz" ) );
			logger.debug( "transPolicyNo: " + request.getParameter( "transPolicyNo" ) );
			logger.debug( "transactionFrom: " + request.getParameter( "transTransactionFrom" ) );
			logger.debug( "transactionTo: " + request.getParameter( "transTransactionTo" ) );
			logger.debug( "transBrokerName: " + request.getParameter( "transBrokerName" ) );
			logger.debug( "transInsuredName: " + request.getParameter( "transInsuredName" ) );
			logger.debug( "transScheme: " + request.getParameter( "transScheme" ) );
			logger.debug( "transAllDirect: " + request.getParameter( "transAllDirect" ) );		
			logger.debug( "transBranch: " + request.getParameter( "transBranch" ) );
			logger.debug( "transWithEmailID: " + request.getParameter( "transWithEmailID" ) );
			logger.debug( "notYetPrinted: " + request.getParameter( "notYetPrinted" ) );
			logger.debug("renewalTerm"+request.getParameter("renewalTerm"));
		}
		
		/*
		 * As part of processing convert the HTTP request object obtained to
		 * required VO by using request to bean mapper available as part of
		 * framework
		 */

		PrintRenewalsSearchCriteriaVO renCriteriaVO = BeanMapper.map( request, PrintRenewalsSearchCriteriaVO.class );

		if( null != renCriteriaVO.getClazz()
				&& ( EMPTY_STRING.equals( renCriteriaVO.getClazz() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getClazz() ) ) ){
			renCriteriaVO.setClazz( null );
		}

		if( null != renCriteriaVO.getPolicyNo() 
				&& ( EMPTY_STRING.equals( renCriteriaVO.getPolicyNo() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getPolicyNo() ) ) ){
			renCriteriaVO.setPolicyNo(null);
		}

		if( null != renCriteriaVO.getInsuredName()
				&& ( EMPTY_STRING.equals( renCriteriaVO.getInsuredName()) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getInsuredName()) ) ){
			renCriteriaVO.setInsuredName( null );
		}

		if( null != renCriteriaVO.getBrokerName()
				&& ( EMPTY_STRING.equals( renCriteriaVO.getBrokerName()) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getBrokerName()) ) ){
			renCriteriaVO.setBrokerName( null );
		}
		
		if( null != renCriteriaVO.getScheme()
				&& ( EMPTY_STRING.equals( renCriteriaVO.getScheme() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getScheme() ) ) ){
			renCriteriaVO.setScheme( null );
		}
		
		if( null != renCriteriaVO.getBranch()
				&& ( EMPTY_STRING.equals( renCriteriaVO.getBranch() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getBranch() ) ) ){
			renCriteriaVO.setBranch( null );
		}
		
		if( null != renCriteriaVO.getRenewalTerm()
				&& ( EMPTY_STRING.equals( renCriteriaVO.getRenewalTerm() ) || ( "All" ).equalsIgnoreCase( renCriteriaVO.getRenewalTerm() ) ) ){
			renCriteriaVO.setRenewalTerm( null );
		}


		BaseVO baseVO = TaskExecutor.executeTasks( identifier, renCriteriaVO );
		logger.debug( "*****Executed taskExecutor*****" );

		RenewalSearchSummaryVO  summaryVO = (RenewalSearchSummaryVO ) baseVO;

		if ( Utils.isEmpty( summaryVO ) || Utils.isEmpty( summaryVO.getRenPolList() ))
			throw new BusinessException( "pas.renewal.noRecordsFound", null, "No records found for given search criteria" );
		
		if( !Utils.isEmpty( summaryVO ) ){
			response.setSuccess( true );
			response.setData( summaryVO );
		}

		return response;

	}

}
