package com.rsaame.pas.renewals.ui;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RenewalVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1006438
 * This class is generates the renewal quotation online
 */

public class GenerateOnlineRenewalRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger( GenerateOnlineRenewalRH.class );
	private final static short RATING_EXECUTED =2;
	private final static short QUOTE_CREATED =1;
	private final static String QUOTE_STATUS ="SE";
	private static final String ZERO_VAL = "0";
	
	@Override
		public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		//String identifier = request.getParameter( "opType" );
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		TTrnRenewalBatchEplatform[] polForRenewal = gson.fromJson(request.getParameter("selectedRows"), TTrnRenewalBatchEplatform[].class);
		/*
		 * 1. Call Stored procedure to generate the renewal quote
		 */
		
		DataHolderVO<Object[]> input =  new DataHolderVO<Object[]>();
		Object renInputData[] = new Object[3];
		renInputData[0] = polForRenewal[0].getPolLinkingId();
		renInputData[1] = ServiceContext.getUser().getUserId();
        renInputData[2] = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		input.setData(renInputData);
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "GENERATE_ONLINE_RENEWALS", input );
		Object[] renData = holderVO.getData();
		Long quotationNo = (Long)renData[0];
		//Long renLinkingId = (Long)renData[1];
		//Long originalLinkingId = (Long)renData[2];
		//PolicyVO policyDetailsVO = (PolicyVO) renData[3];
		
		responseObj.setHeader( "renewalQuoteNo", String.valueOf( quotationNo) );
		LOGGER.debug( "Renewal quote generated successfully" );
		
			
	/*	RenewalPolicyDetails renPolicyDetails = new RenewalPolicyDetails();
		boolean ratingSuccess= false;
		PolicyVO  polVO= renPolicyDetails.createPolicyObject(renLinkingId);
		polVO.setRenewals( policyDetailsVO.getRenewals() );
		
		try{
			polVO = (PolicyVO)TaskExecutor.executeTasks( "RENEWAL_RATING_EXECUTION", polVO );
			ratingSuccess= true;
		} catch(BusinessException be){
			AppUtils.addErrorMessage( request, "pas.renewal.ratingfail" );
		}
		// This rating call is for BI
		try{
			SectionVO biSection = PolicyUtils.getSectionVO(  polVO, Integer.parseInt( Utils.getSingleValueAppConfig( "BI_SECTION" ) ) );
			if(!Utils.isEmpty(biSection)){
				if(!Utils.isEmpty(biSection.getRiskGroupDetails())){
					Map <? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap=biSection.getRiskGroupDetails();
						for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet()) {
								if(!Utils.isEmpty(entry)){
									RiskGroupDetails riskDetails = entry.getValue();
										if(!Utils.isEmpty(riskDetails)){
											riskDetails.setPremium( null);
										}
								}
					
						}
						ratingSuccess= false;
						polVO = (PolicyVO)TaskExecutor.executeTasks( "RENEWAL_RATING_EXECUTION", polVO );
						ratingSuccess= true;
				}
			}
		} catch(BusinessException be){
			AppUtils.addErrorMessage( request, "pas.renewal.ratingfail" );
		}
		
		// Call service to update the premium and policy records with latest premium;
		if(ratingSuccess){
			renInputData[0] = polVO;
			renInputData[1] = SvcUtils.getUserId( polVO );
			input.setData(renInputData);
			TaskExecutor.executeTasks( "UPDATE_RENEWAL_PREMIUM", input ); 
		}
		boolean rulesSuccess = false;
		// just for testing purpose... until rules has been set up
	/*	if(claimCount>0 || osPrmPresent || disLoadPresent || ( !Utils.isEmpty(brStatus) && brStatus == AppConstants.BLOCKED_STATUS)){
			renInputData[0] = polVO;
			renInputData[1] = Utils.getSingleValueAppConfig( "QUOTE_SOFT_STOP" );
			input.setData(renInputData);
			TaskExecutor.executeTasks( "UPDATE_RENEWAL_QUOTE_STATUS", input ); 
		}
		
		 * if there is no claims/ outstanding premium. set renewal terms.
		 
		if(claimCount == 0 || !osPrmPresent){
			renInputData[0] = polVO;
			renInputData[1] = Utils.getSingleValueAppConfig( "RENEWAL_TERMS" );
			input.setData(renInputData);
			TaskExecutor.executeTasks( "UPDATE_RENEWAL_TERMS", input ); 
		}*/
		
		// Invocation of rules engine if claims exists
		/*
		boolean referalExists = false;
		if(ratingSuccess){
			try{
				//OS premium TODO
				//renVo.setOsPremium((double) 0);
				
				polVO.setRenewals(renVo);
				TaskExecutor.executeTasks( "RENEWAL_RULES_EXECUTION", polVO ); 
				
			}catch( BusinessException e ){
				LOGGER.debug( "Referal Exists" );
				rulesSuccess = true;
				referalExists = true;
			} catch(Exception exp){
				// If anything other than business exception then there is some problem with rating invocation
				rulesSuccess = false;
				AppUtils.addErrorMessage( request, "pas.renewal.quoteprocessingfail" );
			}
		}*/
		/*if(referalExists){
			TaskExecutor.executeTasks( "UPDATE_RENEWAL_QUOTE_STATUS", polVO ); 
		}*/
		// End : Rules engine invocation. 
				
	/*	rulesSuccess=true;
		if(!ratingSuccess || !rulesSuccess){
		DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			renInputData[0] = polVO;
			renInputData[1] = Utils.getSingleValueAppConfig( "QUOTE_SYSTEM_ERROR" );
			input.setData(renInputData);
			TaskExecutor.executeTasks( "UPDATE_RENEWAL_QUOTE_STATUS", input ); 
			
			polForRenewal[0].setRenQuotationNo(  quotationNo  );
			if(!ratingSuccess){				
				polForRenewal[0].setLastExecutedStep( Short.valueOf( QUOTE_CREATED ) );
				polForRenewal[0].setRemarks( "Rating invocation failed" );
				LOGGER.trace( "Rating invocation failed, submitting for batch processing" );
			} else {
				polForRenewal[0].setLastExecutedStep( Short.valueOf( RATING_EXECUTED ) );
				polForRenewal[0].setRemarks( "Rules invocation failed" );
				LOGGER.trace( "Rules invocation failed, submitting for batch processing" );
			}
			polForRenewal[0].setRenQuotationStatus( QUOTE_STATUS );
			polForRenewal[0].setLastProcessedDate( new Date() );
			polForRenewal[0].setRenQuotationNo( quotationNo );
			Object[] rewalData = { polForRenewal };
			inputData.setData( rewalData );
			TaskExecutor.executeTasks("GENERATE_RENEWALS", inputData );	
		}*/
		Response response = new Response();
		return response;
	}

}
