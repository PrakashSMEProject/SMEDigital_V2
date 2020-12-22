package com.rsaame.pas.reports.ui;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnSms;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.renewals.ui.RenewalPolicyDetailCommon;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.RenewalResultsSmsVO;
import com.rsaame.pas.vo.app.SearchTransactionVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.RenewalSmsSearchSummaryVO;

public class SendSmsRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( SendSmsRH.class );
	final static Redirection languageSelect = new Redirection( "/jsp/langSelect.jsp", Type.TO_JSP );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.debug( "~~~Inside SearchTransactionSms~~~" );
		Response response = new Response();

		// Identifier will be used further in TaskExecutor to identify
		// request processing
		String action = request.getParameter( "action" );

		if( action.equals( "SEARCH_RECORDS" ) ){
			SearchTransactionVO transVO = BeanMapper.map( request, SearchTransactionVO.class );
			logger.debug( "Bean Mapper created." );
			logger.debug( "Mapping done." );
			if( logger.isDebug() ){
				logger.debug( "Identifier ACTION--> " + action );
				logger.debug( "Insured Name--> " + transVO.getInsuredName() );
				logger.debug( "Quotation No.--> " + transVO.getTransaction().getQuoteNo() );
				logger.debug( "Policy No.--> " + transVO.getTransaction().getPolicyNo() );
				logger.debug( "Scheme--> " + transVO.getTransaction().getScheme() );
				logger.debug( "Broker--> " + transVO.getTransaction().getBrokerName() );
				logger.debug( "Agent--> " + transVO.getAgent() );
				logger.debug( "Nationality--> " + transVO.getNationality() );
				logger.debug( "All Direct--> " + transVO.isAllDirect() );
				logger.debug( "Call Status--> " + transVO.getCallStatus() );
				logger.debug( "Branch--> " + transVO.getTransaction().getBranch() );
				logger.debug( "Transaction From Date--> " + transVO.getTransaction().getTransactionFrom() );
				logger.debug( "Transaction To Date--> " + transVO.getTransaction().getTransactionTo() );
			}
			UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
			transVO.setLoggedInUser( userProfile );
			BaseVO baseVO = TaskExecutor.executeTasks( action, transVO );
			logger.debug( "*****Executed taskExecutor*****" );

			RenewalSmsSearchSummaryVO summaryVO = (RenewalSmsSearchSummaryVO) baseVO;

			if( Utils.isEmpty( summaryVO ) || Utils.isEmpty( summaryVO.getRenPolList() ) ) throw new BusinessException( "pas.src.Empty", null, "No records found for the user" );

			if( !Utils.isEmpty( summaryVO ) ){
				response.setSuccess( true );
				response.setData( summaryVO );
			}
		}
		else if( action.equals( "LANG_SELECT" ) ){
			logger.info( request.getParameter( "submitAll" ) );
			request.setAttribute( com.Constant.CONST_SUBMITALL,  request.getParameter( com.Constant.CONST_SUBMITALL )  );
			response.setRedirection( languageSelect );
		}
		else if( action.equals( "SET_LANG" ) ){

			String language = request.getParameter( "lang" );
			if( !Utils.isEmpty( language ) ){
				Boolean success = true;
				logger.debug( "Language Selected : " + language );
				logger.debug( "Selected Records : " + request.getParameter( "selectedRows" ) );
				request.getSession().setAttribute( "SMS_LANGUAGE", language );
				//Radar fix
				PolicyDataVO policyDataVO = null;//new PolicyDataVO();
				DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
				Gson gson = new GsonBuilder().create();
				RenewalResultsSmsVO[] polForRenewal = gson.fromJson( request.getParameter( "selectedRows" ), RenewalResultsSmsVO[].class );
				for( RenewalResultsSmsVO sms : polForRenewal ){
					logger.info( "" + sms.getPolicyId() );
					DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>)TaskExecutor.executeTasks( "SEND_SMS", sms );
					logger.debug( "*****Executed taskExecutor*****" );
					Object[] renData = holderVO.getData();
					Long quotationNo = (Long) renData[0];
					RenewalPolicyDetailCommon policyDetailCommon = new RenewalPolicyDetailCommon();
					policyDataVO = policyDetailCommon.createPolicyObjectforSMS(renData);
					TTrnSms tTrnSms = new TTrnSms();
					tTrnSms.setTrnSmsPolicyId(policyDataVO.getPolicyId());
					tTrnSms.setTrnSmsEndtId(policyDataVO.getEndtId());
					tTrnSms.setTrnSmsLocCode(policyDataVO.getCommonVO().getLocCode().shortValue());
					tTrnSms.setTrnSmsGsmNo(policyDataVO.getGeneralInfo().getInsured().getMobileNo());			
					tTrnSms.setTrnSmsStatus(DAOUtils.setSmsStatusCode("Submitted"));
					tTrnSms.setTrnSmsLangType(String.valueOf(language.charAt( 0 )));
					tTrnSms.setTrnSmsMode("M");
					//tTrnSms.setTrnSmsSubmittedBy(ServiceContext.getUser().getUserId().toString());
					tTrnSms.setTrnSmsSubmittedBy(policyDataVO.getCommonVO().getLoggedInUser().getUserId());
					//tTrnSms.setTrnSmsSentDate(new Date());
					tTrnSms.setTrnSmsSubDate(new Date());
					tTrnSms.setTrnSmsLevel((byte)(sms.getSmsLevel().byteValue()));
					tTrnSms.setTrnSmsRemarks( "sendSMS" );
					Object renewalSmsData[] = new Object[ 2 ];
					renewalSmsData[ 0 ] = tTrnSms;
					renewalSmsData[1] = policyDataVO.getCommonVO();
					input.setData( renewalSmsData );
					try{
						TaskExecutor.executeTasks( "SEND_RENEWAL_SMS", input );
					}
					catch (Exception e) {
						// TODO: handle exception
						success = false;
						responseObj.setHeader( com.Constant.CONST_SUCCESS, e.getMessage() );
					}
				}
				if(success)
					responseObj.setHeader( com.Constant.CONST_SUCCESS, com.Constant.CONST_SUCCESS );
				response.setRedirection( new Redirection( "SEARCH_TRANSACTION_SMS", Type.TO_NEW_OPERATION) );
			}
			else{
				throw new SystemException( "pas.cmn.languageNull", null, "Language is null in the request" );
			}
			
		}
		return response;
	}
}
