/**
 * 
 */
package com.rsaame.pas.renewals.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.VTrnRenewalPoliciesSbs;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.renewals.svc.RenewalSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EplatformWsStagingVO;
import com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.OnDemandBatchSearchSummaryVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RenewalReportsVO;
import com.rsaame.pas.vo.bus.RenewalResultsVO;
import com.rsaame.pas.vo.bus.RenewalSearchSummaryVO;

/**
 * @author M1037404
 *
 */
public class OnDemandBatchSearchRH implements IRequestHandler {
	
	private final static Logger logger = Logger.getLogger( OnDemandBatchSearchRH.class );
	private final static String EMPTY_STRING = "";
	
	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse responseObj) {
		Response response = new Response();
		String identifier = null;

		// Identifier will be used further in TaskExecutor to identify
		// request processing
		identifier = request.getParameter( "opType" );
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
					
		if( logger.isDebug() ){
			logger.debug( "opType-->" + identifier );
			logger.debug( "Inside OnDemandBatchSearchRH" );
			logger.debug( "transClazz: " + request.getParameter( "transClazz" ) );
			logger.debug( "transQuoteNo: " + request.getParameter( "transQuoteNo" ) );
			logger.debug( "transactionFrom: " + request.getParameter( "transTransactionFrom" ) );
			logger.debug( "transactionTo: " + request.getParameter( "transTransactionTo" ) );

		}
		
		/*
		 * As part of processing convert the HTTP request object obtained to
		 * required VO by using request to bean mapper available as part of
		 * framework
		 */

//		GenerateRenewalsSearchCriteriaVO renCriteriaVO = BeanMapper.map( request, GenerateRenewalsSearchCriteriaVO.class );
		
		String submitAllRecords = request.getParameter("transTransactionFrom");
		
		
		
		/*if( !Utils.isEmpty(userProfile)){
			renCriteriaVO.setLoggedInUser(userProfile);
		}
		
		if( null != renCriteriaVO.getClazz()
				&& ( EMPTY_STRING.equals( renCriteriaVO.getClazz() ) || ( "select" ).equalsIgnoreCase( renCriteriaVO.getClazz() ) ) ){
			renCriteriaVO.setClazz( null );
		}
		
		 * Search Criteria :- Search Criteria based on Quotation No.  
		 
		if( null != renCriteriaVO.getQuoteNo() 
				&& ( EMPTY_STRING.equals( renCriteriaVO.getQuoteNo() ) || ( "select" ).equalsIgnoreCase( renCriteriaVO.getQuoteNo() ) ) ){
			renCriteriaVO.setQuoteNo(null);
		}
		
		BaseVO baseVO =null;*/
		   OnDemandBatchSearchSummaryVO demandBatchSearchSummaryVO=new OnDemandBatchSearchSummaryVO();
			ManualJobHandler handler = new ManualJobHandler();
			Long quoteNo = null;
			List<PolicyVO> policyVOs =null;
			PolicyVO policyVO = new PolicyVO();
			if(submitAllRecords.equals("Checked")) {
				policyVOs = DAOUtils.getPolicyVOSFromStaging();
				try {
					for (PolicyVO policyVO1 : policyVOs) {
					
						
						if(policyVO1.getAppFlow().equals(Flow.CREATE_QUO)) {
							handler.triggerBatchJob(policyVO1);
							policyVO= policyVO1; // for temp
						}else {
							handler.triggerBatchJobForUpdate(policyVO1);
							policyVO= policyVO1; // for temp
						}
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException("cmn.systemError", null, "Error Accured While Running Async");
				}
				AppUtils.addErrorMessage( request, "ondemand.submitquotes" );
			}
			else {
				quoteNo = Long.parseLong(request.getParameter( "transQuoteNo" ) );
				policyVO = DAOUtils.getPolicyVOFromStaging(quoteNo);
				
				
				  
				
				try {
					if(policyVO.getAppFlow().equals(Flow.CREATE_QUO)) {
					handler.triggerBatchJob(policyVO);
					}else {
						handler.triggerBatchJobForUpdate(policyVO);

					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BusinessException("cmn.systemError", null, "Error Accured While Running Async");
				}
				/*if(policyVO.getStatus()==1 || policyVO.getStatus()==20) {
					DAOUtils.batchResponse(policyVO);
					AppUtils.addErrorMessage( request, "pas.issueQuoteSuccessful" );
				}
				else {
					AppUtils.addErrorMessage( request, "cmn.systemError" );
				}*/
				
				
				AppUtils.addErrorMessage( request, "ondemand.quote" );

				
			}
			
			List<EplatformWsStagingVO> policyList = new com.mindtree.ruc.cmn.utils.List<EplatformWsStagingVO>( EplatformWsStagingVO.class ); //tasklist

			
			if( !Utils.isEmpty(policyVOs) && policyVOs.size()>0) {
				
				for (PolicyVO policyVO1 : policyVOs) {
					EplatformWsStagingVO eplatformWsStagingVO = new EplatformWsStagingVO();
					eplatformWsStagingVO.setPolQuotationNo(policyVO1.getQuoteNo());
					eplatformWsStagingVO.setPolEndtNo(policyVO1.getEndtNo());
					eplatformWsStagingVO.setPolStatus(policyVO1.getStatus().byteValue());
					policyList.add( eplatformWsStagingVO );
					demandBatchSearchSummaryVO.setWebServiceAudits( policyList );
				}
				
				response.setSuccess(false);
				response.setData(demandBatchSearchSummaryVO);
				
			}else {
				response.setSuccess(false);
				response.setData(policyVO);
			}
			
			
			
		/*	String message = "pas.issueQuoteSuccessful";
			AppUtils.addErrorMessage(request, message);
			return response;*/
		return response;
	}
}
