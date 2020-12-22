/**
 * 
 */
package com.rsaame.pas.renewals.ui;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;
import com.rsaame.pas.dao.model.TTrnSms;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1019193 
 * RH class which used to generate renewal quote, issue the renewal quote and convert that quote to policy for Home/Travel-Phase3
 * 
 */
public class GenerateOnlineRenewalsCommonRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger(GenerateOnlineRenewalsCommonRH.class);	
	private final static short QUOTE_CREATED = 1;
	private final static String QUOTE_STATUS = "SE";
	private final static String GET_CLAIM_COUNT_COMMON = "GET_CLAIM_COUNT_COMMON";
	private final static String UPDATE_RENEWAL_QUOTE_STATUS_COMMON = "UPDATE_RENEWAL_QUOTE_STATUS_COMMON";
	private final static String GENERATE_RENEWALS_PAS = "GENERATE_RENEWALS_PAS";	
	
	@SuppressWarnings("unchecked")
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {

		LOGGER.debug("****In GenerateOnlineRenewalsCommonRH*****");
		String identifier = request.getParameter("opType");
		String lob = request.getParameter("lob");
		LOGGER.debug("---->>lob in GenerateOnlineRenewalsPasRH = " + lob);
		PolicyDataVO policyDataVO = new PolicyDataVO();
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		TTrnRenewalBatchEplatform[] polForRenewal = gson.fromJson(
				request.getParameter("selectedRows"),
				TTrnRenewalBatchEplatform[].class);
		// Call Stored procedure to generate the renewal quote
		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		Object renInputData[] = new Object[2];
		renInputData[0] = polForRenewal[0].getPolicyId();
		renInputData[1] = ServiceContext.getUser().getUserId();
		input.setData(renInputData);
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) TaskExecutor.executeTasks(identifier, input);
		Object[] renData = holderVO.getData();
		Long quotationNo = (Long) renData[0];
		LOGGER.debug("******renQuotationNo = " + renData[0]+"******renPolicyId = "+ renData[1]);
		response.setHeader("renewalQuoteNo", String.valueOf(quotationNo));
		LOGGER.debug("Renewal quote generated successfully");
		Boolean ratingExecution =true;		
		RenewalPolicyDetailCommon policyDetailCommon = new RenewalPolicyDetailCommon();
		try {
			policyDataVO = policyDetailCommon.createPolicyObject(renData,(Long) renInputData[0],false,null);
			/*DataHolderVO<Object[]> claimInput = new DataHolderVO<Object[]>();
			Object claimInputData[] = new Object[3];
			claimInputData[0] = polForRenewal[0].getPolicyId();
			claimInputData[1] = null;
			// claimInputData[2] = new Date();
			claimInput.setData(claimInputData);
			DataHolderVO<Long> claimOutput = (DataHolderVO<Long>) TaskExecutor.executeTasks(GET_CLAIM_COUNT_COMMON, claimInput);
			Long claimCount = claimOutput.getData();

			if (claimCount > 0) {
				renInputData[0] = policyDataVO;
				renInputData[1] = Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP");
				input.setData(renInputData);
				TaskExecutor.executeTasks(UPDATE_RENEWAL_QUOTE_STATUS_COMMON, input);
			}			*/
			TTrnSms tTrnSms = new TTrnSms();
			tTrnSms.setTrnSmsPolicyId(policyDataVO.getPolicyId());
			tTrnSms.setTrnSmsEndtId(policyDataVO.getEndtId());
			tTrnSms.setTrnSmsLocCode(policyDataVO.getCommonVO().getLocCode().shortValue());
			tTrnSms.setTrnSmsGsmNo(policyDataVO.getGeneralInfo().getInsured().getMobileNo());			
			tTrnSms.setTrnSmsStatus(Byte.valueOf(Utils.getSingleValueAppConfig("SUBMITTED")));			
			tTrnSms.setTrnSmsLangType(AppConstants.SMS_LANGUAGE);
			tTrnSms.setTrnSmsMode(AppConstants.SMS_MODE);
			tTrnSms.setTrnSmsLevel((byte) 1);
			//tTrnSms.setTrnSmsSubmittedBy(ServiceContext.getUser().getUserId().toString());
			tTrnSms.setTrnSmsSubmittedBy(policyDataVO.getCommonVO().getLoggedInUser().getUserId());
			tTrnSms.setTrnSmsSentDate(new Date());
			tTrnSms.setTrnSmsSubDate(new Date());
			Object renewalSmsData[] = new Object[2];
			renewalSmsData[0] = tTrnSms;
			renewalSmsData[1] = policyDataVO.getCommonVO();
			input.setData(renewalSmsData);
			TaskExecutor.executeTasks("SEND_RENEWAL_SMS", input);		

		} catch (Exception e) {	
			LOGGER.debug("*************In Rating invocation Or Loading/Saving"+e.getMessage());
			
			if (!Utils.isEmpty(e.getMessage()) && e.getMessage().trim().equalsIgnoreCase("riskGroupDetailsMap.entrySet() is null")) {
				AppUtils.addErrorMessage(request, "pas.renewal.quoteprocessingfail");
				ratingExecution = false;
			} /*else if (!Utils.isEmpty(e.getMessage()) && e.getMessage().trim().contains("T_MAS_SMS not configured") ) {
				AppUtils.addErrorMessage(request, "sms.details.missing");
			}*/
			else {
				throw new BusinessException("cmn.unknownError", e, "error Occured during Renewal Quote generatation");				
			}
			
		}
		if (!ratingExecution) {			
			CommonVO commonVO = new CommonVO();					
			commonVO.setEndtId(Long.parseLong(AppConstants.zeroVal));
			commonVO.setIsQuote(true);
			policyDataVO.setPolicyId((Long) renData[1]);
			policyDataVO.setCommonVO(commonVO);
			//policyDataVO.setEndtId(ZEROVAL);
			Object renInput[] = new Object[2];
			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			renInput[0] = policyDataVO;
			renInput[1] = Utils.getSingleValueAppConfig("QUOTE_SYSTEM_ERROR");
			input.setData(renInput);
			TaskExecutor.executeTasks(UPDATE_RENEWAL_QUOTE_STATUS_COMMON, input); 			
			polForRenewal[0].setRenQuotationNo( quotationNo );								
			polForRenewal[0].setLastExecutedStep(Short.valueOf(QUOTE_CREATED));
			polForRenewal[0].setRemarks("Rating invocation failed");
			LOGGER.trace("Rating invocation failed, submitting for batch processing");				
			polForRenewal[0].setRenQuotationStatus(QUOTE_STATUS);
			polForRenewal[0].setLastProcessedDate(new Date());
			polForRenewal[0].setRenQuotationNo(quotationNo);
			Object[] renewalsData = { polForRenewal };
			inputData.setData(renewalsData);
			TaskExecutor.executeTasks(GENERATE_RENEWALS_PAS, inputData);			
		}			
				
		Response res = new Response();		
		return res;
	}

}
