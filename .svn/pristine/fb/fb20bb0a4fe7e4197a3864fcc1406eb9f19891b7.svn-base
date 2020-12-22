package com.rsaame.pas.renewals.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.renewals.svc.RenewalSvc;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.RenewalReportsVO;
import com.rsaame.pas.vo.bus.RenewalSearchSummaryVO;

/**
 * @author M1006438
 *  This RH class process the search criteria and returns the search results
 */
public class GenerateRenewalsSearchRH implements IRequestHandler {

	private final static Logger logger = Logger.getLogger( GenerateRenewalsSearchRH.class );
	private final static String EMPTY_STRING = "";
	
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();
		String identifier = null;

		// Identifier will be used further in TaskExecutor to identify
		// request processing
		identifier = request.getParameter( "opType" );
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
					
		if( logger.isDebug() ){
			logger.debug( "opType-->" + identifier );
			logger.debug( "Inside GenerateRenewalsSearchRH" );
			logger.debug( "transClazz: " + request.getParameter( "transClazz" ) );
			logger.debug( "transPolicyNo: " + request.getParameter( "transPolicyNo" ) );
			logger.debug( "transactionFrom: " + request.getParameter( "transTransactionFrom" ) );
			logger.debug( "transactionTo: " + request.getParameter( "transTransactionTo" ) );
			logger.debug( "transBrokerName: " + request.getParameter( "transBrokerName" ) );
			logger.debug( "transInsuredName: " + request.getParameter( "transInsuredName" ) );
			logger.debug( "transScheme: " + request.getParameter( "transScheme" ) );
			logger.debug( "transAllDirect: " + request.getParameter( "transAllDirect" ) );		
			logger.debug( "transExpiresIn: " + request.getParameter( "transExpiresIn" ) );
			logger.debug( "transBranch: " + request.getParameter( "transBranch" ) );
			
			/*
			 *  Search Criteria :- Search Criteria based on Quotation No.  
			 */
			logger.debug( "transQuoteNo: " + request.getParameter( "transQuoteNo" ) );
			logger.debug( "transStatus: " + request.getParameter( "transStatus" ) );
		}
		
		/*
		 * As part of processing convert the HTTP request object obtained to
		 * required VO by using request to bean mapper available as part of
		 * framework
		 */

		GenerateRenewalsSearchCriteriaVO renCriteriaVO = BeanMapper.map( request, GenerateRenewalsSearchCriteriaVO.class );
		
		if( !Utils.isEmpty(userProfile)){
			renCriteriaVO.setLoggedInUser(userProfile);
		}
		
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
		/*
		 * Search Criteria :- Search Criteria based on Quotation No.  
		 */
		if( null != renCriteriaVO.getQuoteNo() 
				&& ( EMPTY_STRING.equals( renCriteriaVO.getQuoteNo() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getQuoteNo() ) ) ){
			renCriteriaVO.setQuoteNo(null);
		}
		/*
		 * Search Criteria :- Search Criteria based on status  
		 */
		if( null != renCriteriaVO.getStatusId() 
				&& ( EMPTY_STRING.equals( renCriteriaVO.getStatusId() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( renCriteriaVO.getStatusId() ) ) ){
			renCriteriaVO.setStatusId(null);
		}
		BaseVO baseVO =null;
		logger.debug( "*****Executed taskExecutor*****" );

		if ("RENEWAL_STATUS_REPORT_SEARCH".equals(identifier))
		{
			baseVO = TaskExecutor.executeTasks( identifier, renCriteriaVO );
			DataHolderVO<List> holder = (DataHolderVO ) baseVO;
			List<RenewalReportsVO> renewalPolList = holder.getData();
			if ( Utils.isEmpty( renewalPolList ))
				throw new BusinessException( "pas.renewal.noRecordsFound", null, "No records found for given search criteria" );
			
			if( !Utils.isEmpty( renewalPolList ) ){
				response.setSuccess( true );
				response.setData( renewalPolList );
			}
		}
		else
		{
			RenewalSvc renewalSvc = (RenewalSvc) Utils.getBean( "renewalSvc_POL" );
			
			baseVO = (BaseVO) renewalSvc.invokeMethod( "getPoliciesToBeRenewed", renCriteriaVO );//TaskExecutor.executeTasks( identifier, renCriteriaVO );
			
			RenewalSearchSummaryVO  summaryVO = (RenewalSearchSummaryVO ) baseVO;

			if ( Utils.isEmpty( summaryVO ) || Utils.isEmpty( summaryVO.getRenPolList() ))
				throw new BusinessException( "pas.renewal.noRecordsFound", null, "No records found for given search criteria" );
			
			if( !Utils.isEmpty( summaryVO ) ){
				response.setSuccess( true );
				response.setData( summaryVO );
			}
			
		}
		

		return response;

	}

}
