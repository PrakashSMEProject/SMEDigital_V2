/**
 * 
 */
package com.rsaame.pas.quote.ui;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO;
import com.rsaame.pas.vo.bus.TransactionSummaryVO;
import com.rsaame.pas.vo.bus.TransactionVO;

/**
 * @author M1016284
 * 
 */
public class SearchTransactionRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( SearchTransactionRH.class );
	java.text.DecimalFormat decFormBahrain = new  java.text.DecimalFormat(com.rsaame.pas.svc.constants.SvcConstants.DECIMAL_FORMAT_BAHRAIN);
	private final static String EMPTY_STRING = "";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){

		Response response = new Response();
		String defaultSearch = null;
		String identifier = null;
		String viewHistory=null;

		// Identifier will be used further in TaskExecutor to identify
		// request processing
		identifier = request.getParameter( "opType" );
		defaultSearch = request.getParameter( "DEFAULT_SEARCH" );
		viewHistory=request.getParameter( com.Constant.CONST_VIEWHISTORY )==null?"normal":request.getParameter( com.Constant.CONST_VIEWHISTORY );

		if( logger.isDebug() ){
			logger.debug( "opType-->" + identifier );
			logger.debug( "Inside SearchTransactionRH" );
			logger.debug( "transQuoteNo: " + request.getParameter( "transQuoteNo" ) );
			logger.debug( "transTransactionFrom: " + request.getParameter( "transTransactionFrom" ) );
			logger.debug( "transTransactionTo: " + request.getParameter( "transTransactionTo" ) );
			logger.debug( "transSearchQuote: " + request.getParameter( "transSearchQuote" ) );
			logger.debug( "transSearchPolicy: " + request.getParameter( "transSearchPolicy" ) );
			logger.debug( "transLastTransaction: " + request.getParameter( "transLastTransaction" ) );
			logger.debug( "transExactSearch: " + request.getParameter( "transExactSearch" ) );
			logger.debug( "transStatus: " + request.getParameter( "transStatus" ) );
			logger.debug( "transBranch: " + request.getParameter( "transBranch" ) );
			logger.debug( "LOB: " + request.getParameter( "LOB" ) );
			logger.debug( "viewHistory: " + request.getParameter( "viewHistory" ) );
			logger.debug( "transPolicyNo: " + request.getParameter( "transPolicyNo" ) );
		}
		
		/*
		 * As part of processing convert the HTTP request object obtained to
		 * required VO by using request to bean mapper available as part of
		 * framework
		 */
		SearchTransactionCriteriaVO criteriaVO = BeanMapper.map( request, SearchTransactionCriteriaVO.class );

		logger.debug( "Bean Mapper created." );
		logger.debug( "Mapping done." );

		if( !Utils.isEmpty( defaultSearch, true ) && "Y".equals( defaultSearch ) ){
			logger.debug( "*****Making Default Search****" );
			Calendar cal = Calendar.getInstance();
			cal.add( Calendar.DATE, -7 );
			Date fromDate = cal.getTime();
			Date toDate = new Date();
			fromDate = cal.getTime();

			criteriaVO.setSearchQuote( true );
			
			TransactionVO transactionVO = new TransactionVO();
			
			transactionVO.setTransactionFrom( fromDate );
			transactionVO.setTransactionTo( toDate );
			transactionVO.setStatus( "1" );
			criteriaVO.setTransaction( transactionVO );
		}

		
		if( null != criteriaVO.getTransaction().getStatus()
				&& ( EMPTY_STRING.equals( criteriaVO.getTransaction().getStatus() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( criteriaVO.getTransaction().getStatus() ) ) ){
			criteriaVO.getTransaction().setStatus( null );
		}

		if( null != criteriaVO.getTransaction().getClazz()
				&& ( EMPTY_STRING.equals( criteriaVO.getTransaction().getClazz() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( criteriaVO.getTransaction().getClazz() ) ) ){
			criteriaVO.getTransaction().setClazz( null );
		}

		if( null != criteriaVO.getTransaction().getCompanyName() && EMPTY_STRING.equals( criteriaVO.getTransaction().getCompanyName() ) ){
			criteriaVO.getTransaction().setCompanyName( null );
		}

		if( null != criteriaVO.getTransaction().getCustomerName() && EMPTY_STRING.equals( criteriaVO.getTransaction().getCustomerName() ) ){
			criteriaVO.getTransaction().setCustomerName( null );
		}

		if( null != criteriaVO.getTransaction().getBrokerName()
				&& ( EMPTY_STRING.equals( criteriaVO.getTransaction().getBrokerName() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( criteriaVO.getTransaction().getBrokerName() ) ) ){
			criteriaVO.getTransaction().setBrokerName( null );
		}

		if( null != criteriaVO.getTransaction().getScheme()
				&& ( EMPTY_STRING.equals( criteriaVO.getTransaction().getScheme() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( criteriaVO.getTransaction().getScheme() ) ) ){
			criteriaVO.getTransaction().setScheme( null );
		}

		if( null != criteriaVO.getTransaction().getCreatedBy()
				&& ( EMPTY_STRING.equals( criteriaVO.getTransaction().getCreatedBy() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( criteriaVO.getTransaction().getCreatedBy() ) ) ){
			criteriaVO.getTransaction().setCreatedBy( null );
		}

		if( null != criteriaVO.getTransaction().getLastModifiedBy()
				&& ( EMPTY_STRING.equals( criteriaVO.getTransaction().getLastModifiedBy() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( criteriaVO.getTransaction().getLastModifiedBy() ) ) ){
			criteriaVO.getTransaction().setLastModifiedBy( null );
		}

		if( null != criteriaVO.getTransaction().getDistributionCode()
				&& ( EMPTY_STRING.equals( criteriaVO.getTransaction().getDistributionCode() ) || ( com.Constant.CONST_SELECT ).equalsIgnoreCase( criteriaVO.getTransaction().getDistributionCode() ) ) ){
			criteriaVO.getTransaction().setDistributionCode( null );
		}
		
		
		
		/**
		 * Criteria will be considered to search policy only when Policy check box selected and policy nomber value is provided
		 */
		if( criteriaVO.getSearchPolicy() || !Utils.isEmpty( criteriaVO.getTransaction().getPolicyNo())){
			criteriaVO.setPolicyEntered( "Policy" );
		}

		/**
		 * Criteria will be considered to search quotation only when Quote check box selected and Quotation nomber value is provided
		 */
		if( criteriaVO.getSearchQuote() || !Utils.isEmpty( criteriaVO.getTransaction().getQuoteNo() )){
			criteriaVO.setQuoteEntered( "Quote" );
		}

		if( criteriaVO.getSearchPolicy() && criteriaVO.getSearchQuote() ){
			criteriaVO.setQuotePolicy( "QuotePolicy" );
		}

		if( criteriaVO.getSearchPolicy() && !criteriaVO.getSearchQuote() ){
			criteriaVO.setQuotePolicy( "Policy" );
		}

		if( !criteriaVO.getSearchPolicy() && criteriaVO.getSearchQuote() ){
			criteriaVO.setQuotePolicy( "Quote" );
		}
		
		if(viewHistory!=null){
			
			criteriaVO.setForHistoryView(viewHistory);
		}

		if( logger.isDebug() ){
			logger.debug( "search policy--> " + criteriaVO.getSearchPolicy() );
			logger.debug( "search quote-->" + criteriaVO.getSearchQuote() );
			logger.debug( "Exact search-->" + criteriaVO.getExactSearch() );
			logger.debug( "last transaction-->" + criteriaVO.getLastTransaction() );
			logger.debug( "Quote no-->" + criteriaVO.getTransaction().getQuoteNo() );
			logger.debug( "Policy no--> " + criteriaVO.getTransaction().getPolicyNo() );
			logger.debug( "Transaction From-->" + criteriaVO.getTransaction().getTransactionFrom() );
			logger.debug( "Transaction To-->" + criteriaVO.getTransaction().getTransactionTo() );
			logger.debug( "Status-->" + criteriaVO.getTransaction().getStatus() );
			logger.debug( "Class-->" + criteriaVO.getTransaction().getClazz() );
			logger.debug( "Company name-->" + criteriaVO.getTransaction().getCompanyName() );
			logger.debug( "Customer Name-->" + criteriaVO.getTransaction().getCustomerName() );
			logger.debug( "Broker-->" + criteriaVO.getTransaction().getBrokerName() );
			logger.debug( "Scheme-->" + criteriaVO.getTransaction().getScheme() );
			logger.debug( "Effective Date-->" + criteriaVO.getTransaction().getEffectiveDate() );
			logger.debug( "Expiry Date-->" + criteriaVO.getTransaction().getExpiryDate() );
			logger.debug( "Created by-->" + criteriaVO.getTransaction().getCreatedBy() );
			logger.debug( "Last modified by-->" + criteriaVO.getTransaction().getLastModifiedBy() );
			logger.debug( "Distribution code-->" + criteriaVO.getTransaction().getDistributionCode() );
			logger.debug( "Quote Entered-->" + criteriaVO.getQuoteEntered() );
			logger.debug( "Policy Entered-->" + criteriaVO.getPolicyEntered() );
			logger.debug( "QuotePolicy-->" + criteriaVO.getQuotePolicy() );
			logger.debug( "Mobile No-->" + criteriaVO.getTransaction().getMobileNo() );
			logger.debug( "Reference Policy no.-->" + criteriaVO.getTransaction().getPolReferenceNo() );
			
		}

		/*
		 * Once the VO is instantiated and populated with request values,
		 * call task executor to perform tasks to be performed
		 * 
		 */

		// Fetching the Broker desc based on the code passed in SearchTransactionCriteriaVO
		if( !Utils.isEmpty( criteriaVO ) && !Utils.isEmpty( criteriaVO.getTransaction() ) && !Utils.isEmpty( criteriaVO.getTransaction().getBrokerName() ) ){
			String description = SvcUtils.getLookUpDescription( "BROKER_NAME", "ALL", "ALL", Integer.parseInt(criteriaVO.getTransaction().getBrokerName()));
			if( !Utils.isEmpty( description ) ){
				criteriaVO.getTransaction().setBrokerName( description);
			}
		}
		
		
		//Fetching the Scheme desc based on the code passed in SearchTransactionCriteriaVO
		if( !Utils.isEmpty( criteriaVO ) && !Utils.isEmpty( criteriaVO.getTransaction() ) && !Utils.isEmpty( criteriaVO.getTransaction().getScheme() ) ){
			String schemeName = SvcUtils.getLookUpDescription( "SCHEME", "ALL", "ALL", Integer.parseInt(criteriaVO.getTransaction().getScheme()));
			if( !Utils.isEmpty( schemeName ) ){
				criteriaVO.getTransaction().setScheme( schemeName);
			}
		}
		
		
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, criteriaVO );
		logger.debug( "*****Executed taskExecutor*****" );

		TransactionSummaryVO summaryVO = (TransactionSummaryVO) baseVO;
		
		Double premiumAmt=0.0;
        String premium=null;

        if ( Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)
          .equalsIgnoreCase("50") &&  !Utils.isEmpty( summaryVO.getTransactionArray() )) {
               for(int i=0;i<summaryVO.getTransactionArray().length;i++) {
                     premiumAmt=0.0;
                     premiumAmt = SvcUtils.getRoundingOffBah(Double.valueOf(summaryVO.getTransactionArray()[i].getTransactionPremium()));
                     
                     premium = String.valueOf(decFormBahrain.format((premiumAmt)));
                     
                     summaryVO.getTransactionArray()[i].setTransactionPremium(premium);
               }
               
        }

		

		if ( Utils.isEmpty( summaryVO ) || Utils.isEmpty( summaryVO.getTransactionArray() ))
			throw new BusinessException( "pas.src.Empty", null, "No records found for the user" );
		
/*		TransactionVO[] transactionArray = summaryVO.getTransactionArray();	
 		Set<TransactionVO> transactionVOSet = CopyUtils.asSet( transactionArray );

		logger.debug( "*****Generated unique results: transactionVOSet*****" );

		if( !Utils.isEmpty( transactionVOSet ) ){
			int numOfUniqueRecords = transactionVOSet.size();
			TransactionVO[] uniqueTransactionArray = new TransactionVO[ numOfUniqueRecords ];
			summaryVO.setTransactionArray( transactionVOSet.toArray( uniqueTransactionArray ) );
			summaryVO.setNumberOfRecords( numOfUniqueRecords );
		}
		else{
			throw new BusinessException( "pas.src.Empty", null, "No records found for the user" );
		}
		logger.debug( "*****After setting unique results*****" );
*/
		if( !Utils.isEmpty( summaryVO ) ){
			response.setSuccess( true );
			response.setData( summaryVO );
		}

		return response;

	}
	
	
	
	

}
