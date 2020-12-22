package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyRequest;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.RetrievePolicyByPolicyNoResponse;
import com.rsaame.pas.b2b.ws.vo.UploadDocumentRequest;
import com.rsaame.pas.b2c.ws.mapper.BaseRequestVOMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.handler.WebServiceAuditHandler;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.HeaderInfo;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;
import com.rsaame.pas.b2c.ws.vo.CreateHomeQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.CreateHomeQuoteResponse;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyRequest;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyResponse;
import com.rsaame.pas.b2c.ws.vo.Customer;
import com.rsaame.pas.b2c.ws.vo.Quote;
import com.rsaame.pas.b2c.ws.vo.RetrieveHomeQuoteByPolicyResponse;
import com.rsaame.pas.b2c.ws.vo.RetrievePolicyByPolicyNo;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByPolicyRequest;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByQuoteId;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteResponse;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteResponse;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

public class WebServiceAuditMapper implements BaseRequestVOMapper{

	WebServiceAudit webServiceAudit = new WebServiceAudit();
	WebServiceAuditHandler webServiceAuditHandler = new WebServiceAuditHandler();
	@Override
	public void mapRequestToVO(Object requestObj, Object reponseObj) throws Exception {
		if (requestObj instanceof CreateHomeQuoteRequest
				&& reponseObj instanceof CreateHomeQuoteResponse) {
			CreateHomeQuoteRequest createHomeQuoteRequest = (CreateHomeQuoteRequest) requestObj;
			CreateHomeQuoteResponse createHomeQuoteResponse = (CreateHomeQuoteResponse) reponseObj;
			//mapCreateHomeQuoteToAudit(createHomeQuoteRequest,createHomeQuoteResponse);
		}else if(requestObj instanceof RetrieveQuoteByQuoteId
				&& reponseObj instanceof UpdateHomeQuoteResponse){
			RetrieveQuoteByQuoteId retrieveQuoteByQuoteId = (RetrieveQuoteByQuoteId) requestObj;
			UpdateHomeQuoteResponse updateHomeQuoteResponse = (UpdateHomeQuoteResponse) reponseObj;
			//mapRetrieveHomeQuoteToAudit( retrieveQuoteByQuoteId, updateHomeQuoteResponse);
		}else if(requestObj instanceof RetrieveQuoteByQuoteId
				&& reponseObj instanceof UpdateTravelQuoteResponse){
			RetrieveQuoteByQuoteId retrieveQuoteByQuoteId = (RetrieveQuoteByQuoteId) requestObj;
			UpdateTravelQuoteResponse updateTravelQuoteResponse = (UpdateTravelQuoteResponse) reponseObj;
			mapRetrieveTravelQuoteToAudit( retrieveQuoteByQuoteId, updateTravelQuoteResponse);
		}else if(requestObj instanceof Customer
				&& reponseObj instanceof Quote){
			Customer customer = (Customer) requestObj;
			Quote quote = (Quote) reponseObj;
			mapCreateTravelQuoteToAudit( customer, quote);
		}else if(requestObj instanceof UpdateTravelQuoteRequest
				&& reponseObj instanceof UpdateTravelQuoteResponse){
			UpdateTravelQuoteRequest updateTravelQuoteRequest = (UpdateTravelQuoteRequest) requestObj;
			UpdateTravelQuoteResponse updateTravelQuoteResponse = (UpdateTravelQuoteResponse) reponseObj;
			mapUpdateTravelQuoteToAudit( updateTravelQuoteRequest, updateTravelQuoteResponse);
		}else if(requestObj instanceof UpdateHomeQuoteRequest
				&& reponseObj instanceof UpdateHomeQuoteResponse){
			UpdateHomeQuoteRequest updateHomeQuoteRequest = (UpdateHomeQuoteRequest) requestObj;
			UpdateHomeQuoteResponse updateHomeQuoteResponse = (UpdateHomeQuoteResponse) reponseObj;
			//mapUpdateHomeQuoteToAudit(updateHomeQuoteRequest, updateHomeQuoteResponse);
		}
		
	}
	
	public void mapRequestAndReponseToAuditVO(Object vOObject,Object requestObj, Object reponseObj, HeaderInfo headerInfo) throws Exception {
		
		CreatePolicyResponse createPolicyResponse = (CreatePolicyResponse) reponseObj;
		if (requestObj instanceof RetrievePolicyByPolicyNo
				&& reponseObj instanceof CreatePolicyResponse){
			if(vOObject instanceof HomeInsuranceVO){
				HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) vOObject;
				RetrievePolicyByPolicyNo retrievePolicyByPolicyNo = (RetrievePolicyByPolicyNo) requestObj;
				mapRetrieveHomePolicyToAudit(homeInsuranceVO,retrievePolicyByPolicyNo,createPolicyResponse,headerInfo);
			}else{
				TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) vOObject;
				RetrievePolicyByPolicyNo retrievePolicyByPolicyNo = (RetrievePolicyByPolicyNo) requestObj;
				mapRetrieveTravelPolicyToAudit(travelInsuranceVO,retrievePolicyByPolicyNo,createPolicyResponse);
			}
			
		}
	}
	
	
	/**
	 * @param headerInfo 
	 * @param homeInsuranceVO 
	 * @description : Request Object = CreateHomeQuoteRequest
	 * 				: Response Object = CreateHomeQuoteResponse
	 * @API : Create Home Quote 
	 */
	public void mapCreateHomeQuoteToAudit(CreateHomeQuoteRequest createHomeQuoteRequest,CreateHomeQuoteResponse createHomeQuoteResponse, HeaderInfo headerInfo, HomeInsuranceVO homeInsuranceVO){
		webServiceAudit.setTwa_class_code(homeInsuranceVO.getClassCode());
		webServiceAudit.setTwa_created_date(createHomeQuoteResponse.getTransactionDetails().getEffectiveDate());
		webServiceAudit.setTwa_endt_id(createHomeQuoteResponse.getEndtId());
		webServiceAudit.setTwa_policy_id(createHomeQuoteResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(homeInsuranceVO.getPolicyType());
		webServiceAudit.setTwa_quote_no(createHomeQuoteResponse.getQuotationNo());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createHomeQuoteRequest)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createHomeQuoteResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("CreateHomeQuote");
		if(!Utils.isEmpty(createHomeQuoteResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(homeInsuranceVO.getCommonVO().getLoggedInUser().getUserId()); // 993
		
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(endTime);
		long diffInMs = webServiceAudit.getTwa_req_start_time().getTime() - webServiceAudit.getTwa_res_end_time().getTime();
		webServiceAudit.setTwa_time_diff_in_ms(Math.abs((int)diffInMs));
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}

	/**
	 * @param homeInsuranceVO 
	 * @param headerInfo 
	 * @description : Request Object = RetrieveQuoteByQuoteId
	 * 				: Response Object = UpdateHomeQuoteResponse
	 * @API : Retrieve Home Quote 
	 */
	public void mapRetrieveHomeQuoteToAudit(RetrieveQuoteByQuoteId retrieveQuoteByQuoteId,UpdateHomeQuoteResponse updateHomeQuoteResponse, HeaderInfo headerInfo, HomeInsuranceVO homeInsuranceVO){
		webServiceAudit.setTwa_class_code(homeInsuranceVO.getClassCode());
		webServiceAudit.setTwa_created_date(updateHomeQuoteResponse.getTransactionDetails().getEffectiveDate());
		webServiceAudit.setTwa_endt_id(updateHomeQuoteResponse.getEndtId());
		webServiceAudit.setTwa_policy_id(updateHomeQuoteResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(homeInsuranceVO.getPolicyType());
		webServiceAudit.setTwa_quote_no(updateHomeQuoteResponse.getQuotationNo().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrieveQuoteByQuoteId)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(updateHomeQuoteResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("RetrieveHomeQuote");
		if(!Utils.isEmpty(updateHomeQuoteResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(homeInsuranceVO.getCommonVO().getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(endTime);
		long diffInMs = webServiceAudit.getTwa_req_start_time().getTime() - webServiceAudit.getTwa_res_end_time().getTime();
		webServiceAudit.setTwa_time_diff_in_ms(Math.abs((int)diffInMs));
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @description : Request Object = RetrieveQuoteByQuoteId
	 * 				: Response Object = UpdateTravelQuoteResponse
	 * @API : Retrieve Travel Quote 
	 */
	private void mapRetrieveTravelQuoteToAudit(RetrieveQuoteByQuoteId retrieveQuoteByQuoteId,UpdateTravelQuoteResponse updateTravelQuoteResponse){
		webServiceAudit.setTwa_class_code(updateTravelQuoteResponse.getTransactionDetails().getClassCode());
		webServiceAudit.setTwa_created_date(updateTravelQuoteResponse.getTransactionDetails().getEffectiveDate());
		webServiceAudit.setTwa_endt_id(updateTravelQuoteResponse.getEndtId());
		webServiceAudit.setTwa_partner(com.Constant.CONST_MINDTREE);
		webServiceAudit.setTwa_policy_id(updateTravelQuoteResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(updateTravelQuoteResponse.getTransactionDetails().getPolicyTypeCode());
		webServiceAudit.setTwa_quote_no(updateTravelQuoteResponse.getQuotationNo().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrieveQuoteByQuoteId)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(updateTravelQuoteResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("RetrieveTravelQuote");
		if(!Utils.isEmpty(updateTravelQuoteResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(com.Constant.CONST_MINDTREE);
		
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @param headerInfo 
	 * @description : Request Object = RetrieveQuoteByQuoteId
	 * 				: Response Object = UpdateTravelQuoteResponse
	 * 				: Vo Object = HomeInsuranceVO
	 * @API : Retrieve Home/Travel Policy 
	 */
	private void mapRetrieveHomePolicyToAudit(HomeInsuranceVO homeInsuranceVO,
			RetrievePolicyByPolicyNo retrievePolicyByPolicyNo,CreatePolicyResponse createPolicyResponse, HeaderInfo headerInfo){
		webServiceAudit.setTwa_class_code(homeInsuranceVO.getClassCode());
		webServiceAudit.setTwa_created_date(homeInsuranceVO.getScheme().getEffDate());
		webServiceAudit.setTwa_endt_id(createPolicyResponse.getEndtId());
		webServiceAudit.setTwa_policy_id(createPolicyResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(new Long(createPolicyResponse.getPolicyNumber()));
		webServiceAudit.setTwa_policy_type(homeInsuranceVO.getPolicyType());
		webServiceAudit.setTwa_quote_no(homeInsuranceVO.getCommonVO().getQuoteNo().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrievePolicyByPolicyNo)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createPolicyResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("RetrieveHomePolicy");
		if(!Utils.isEmpty(createPolicyResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(homeInsuranceVO.getCommonVO().getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(endTime);
		long diffInMs = webServiceAudit.getTwa_req_start_time().getTime() - webServiceAudit.getTwa_res_end_time().getTime();
		webServiceAudit.setTwa_time_diff_in_ms(Math.abs((int)diffInMs));
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @description : Request Object = RetrieveQuoteByQuoteId
	 * 				: Response Object = UpdateTravelQuoteResponse
	 * 				: Vo Object = TravelInsuranceVO
	 * @API : Retrieve Home/Travel Policy 
	 */
	private void mapRetrieveTravelPolicyToAudit(TravelInsuranceVO travelInsuranceVO,
			RetrievePolicyByPolicyNo retrievePolicyByPolicyNo,CreatePolicyResponse createPolicyResponse){
		webServiceAudit.setTwa_class_code(travelInsuranceVO.getClassCode());
		webServiceAudit.setTwa_created_date(travelInsuranceVO.getScheme().getEffDate());
		webServiceAudit.setTwa_endt_id(createPolicyResponse.getEndtId());
		webServiceAudit.setTwa_partner(com.Constant.CONST_MINDTREE);
		webServiceAudit.setTwa_policy_id(createPolicyResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(new Long(createPolicyResponse.getPolicyNumber()));
		webServiceAudit.setTwa_policy_type(travelInsuranceVO.getPolicyType());
		webServiceAudit.setTwa_quote_no(travelInsuranceVO.getCommonVO().getQuoteNo().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrievePolicyByPolicyNo)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createPolicyResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("RetrieveTravelQuote");
		if(!Utils.isEmpty(createPolicyResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(com.Constant.CONST_MINDTREE);
		
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	

	/**
	 * @description : Request Object = Customer
	 * 				: Response Object = Quote
	 * @API : Create Travel Quote 
	 */
	private void mapCreateTravelQuoteToAudit(Customer customer,Quote quote){
		webServiceAudit.setTwa_class_code(quote.getTransactionDetails().getClassCode());
		webServiceAudit.setTwa_created_date(quote.getTransactionDetails().getEffectiveDate());
		webServiceAudit.setTwa_endt_id(quote.getEndtId());
		webServiceAudit.setTwa_partner(com.Constant.CONST_MINDTREE);
		webServiceAudit.setTwa_policy_id(quote.getPolicyId());
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(quote.getTransactionDetails().getPolicyTypeCode());
		if(quote.getQid()!=null ){
			webServiceAudit.setTwa_quote_no(quote.getQid().intValue());	
		}else{
			webServiceAudit.setTwa_quote_no(null);
		}	
		webServiceAudit.setTwa_quote_no(quote.getQid().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(customer)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(quote)));		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("CreateTravelQuote");
		if(!Utils.isEmpty(quote.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(com.Constant.CONST_MINDTREE);
		
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @description : Request Object = UpdateTravelQuoteRequest
	 * 				: Response Object = UpdateTravelQuoteResponse
	 * @API : Update Travel Quote 
	 */
	private void mapUpdateTravelQuoteToAudit(UpdateTravelQuoteRequest updateTravelQuoteRequest, UpdateTravelQuoteResponse updateTravelQuoteResponse){
		webServiceAudit.setTwa_class_code(updateTravelQuoteResponse.getTransactionDetails().getClassCode());
		webServiceAudit.setTwa_created_date(updateTravelQuoteResponse.getTransactionDetails().getEffectiveDate());
		webServiceAudit.setTwa_endt_id(updateTravelQuoteResponse.getEndtId());
		webServiceAudit.setTwa_partner(com.Constant.CONST_MINDTREE);
		webServiceAudit.setTwa_policy_id(updateTravelQuoteResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(updateTravelQuoteResponse.getTransactionDetails().getPolicyTypeCode());
		webServiceAudit.setTwa_quote_no(updateTravelQuoteResponse.getQuotationNo().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(updateTravelQuoteRequest)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(updateTravelQuoteResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("UpdateTravelQuote");
		if(!Utils.isEmpty(updateTravelQuoteResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(com.Constant.CONST_MINDTREE);
		
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @param headerInfo 
	 * @param homeInsuranceVO 
	 * @description : Request Object = UpdateHomeQuoteRequest
	 * 				: Response Object = UpdateHomeQuoteResponse
	 * @API : Update Home Quote 
	 */
	public void mapUpdateHomeQuoteToAudit(UpdateHomeQuoteRequest updateHomeQuoteRequest, UpdateHomeQuoteResponse updateHomeQuoteResponse, HeaderInfo headerInfo, HomeInsuranceVO homeInsuranceVO){
		webServiceAudit.setTwa_class_code(homeInsuranceVO.getClassCode());
		webServiceAudit.setTwa_created_date(updateHomeQuoteResponse.getTransactionDetails().getEffectiveDate());
		webServiceAudit.setTwa_endt_id(updateHomeQuoteResponse.getEndtId());
		webServiceAudit.setTwa_policy_id(updateHomeQuoteResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(homeInsuranceVO.getPolicyType());
		webServiceAudit.setTwa_quote_no(updateHomeQuoteResponse.getQuotationNo().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(updateHomeQuoteRequest)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(updateHomeQuoteResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("UpdateHomeQuote");
		if(!Utils.isEmpty(updateHomeQuoteResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(homeInsuranceVO.getCommonVO().getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(endTime);
		long diffInMs = webServiceAudit.getTwa_req_start_time().getTime() - webServiceAudit.getTwa_res_end_time().getTime();
		webServiceAudit.setTwa_time_diff_in_ms(Math.abs((int)diffInMs));
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @param headerInfo 
	 * @description : Request Object = CreatePolicyRequest
	 * 				: Response Object = CreatePolicyResponse
	 * @API : Create Home / Travel Policy 
	 */
	public void mapCreatePolicyToAudit(Object voObject, CreatePolicyRequest createPolicyRequest, 
			CreatePolicyResponse createPolicyResponse, HeaderInfo headerInfo){
		if ( voObject instanceof HomeInsuranceVO){
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) voObject;
			webServiceAudit.setTwa_class_code(homeInsuranceVO.getClassCode());
			webServiceAudit.setTwa_created_date(homeInsuranceVO.getScheme().getEffDate());
			webServiceAudit.setTwa_policy_type(homeInsuranceVO.getPolicyType());
			webServiceAudit.setTwa_quote_no(homeInsuranceVO.getCommonVO().getQuoteNo().intValue());
			webServiceAudit.setTwa_transaction_req_type("CreateHomePolicy");
			webServiceAudit.setTwa_user_name(homeInsuranceVO.getCommonVO().getLoggedInUser().getUserId());
		}else {
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) voObject;
			webServiceAudit.setTwa_class_code(travelInsuranceVO.getClassCode());
			webServiceAudit.setTwa_created_date(travelInsuranceVO.getScheme().getEffDate());
			webServiceAudit.setTwa_policy_type(travelInsuranceVO.getPolicyType());
			webServiceAudit.setTwa_quote_no(travelInsuranceVO.getCommonVO().getQuoteNo().intValue());
			webServiceAudit.setTwa_transaction_req_type("CreateTravelPolicy");
			webServiceAudit.setTwa_partner(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId());
			webServiceAudit.setTwa_user_name(travelInsuranceVO.getCommonVO().getLoggedInUser().getUserId());
		}
		webServiceAudit.setTwa_endt_id(createPolicyResponse.getEndtId());
		
		webServiceAudit.setTwa_policy_id(createPolicyResponse.getPolicyId());
		webServiceAudit.setTwa_policy_no(new Long(createPolicyResponse.getPolicyNumber()));
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createPolicyRequest)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createPolicyResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		
		if(!Utils.isEmpty(createPolicyResponse.getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(endTime);
		long diffInMs = webServiceAudit.getTwa_req_start_time().getTime() - webServiceAudit.getTwa_res_end_time().getTime();
		webServiceAudit.setTwa_time_diff_in_ms(Math.abs((int)diffInMs));
		
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	

	/**
	 * @description : Request Object = CreateSBSQuoteRequest
	 * 				: Response Object = CreateSBSQuoteResponse
	 * @API : CreateSBSQuote 
	 */
	
	public WebServiceAudit mapcreateSBSQuoteToAudit(PolicyVO policyVO , CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse , HeaderInfo headerInfo) throws ParseException{
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		Date creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(createSBSQuoteRequest.getPolicySchedule().getCreationDate());
		//added to set request start time-m1043116
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_req_start_time(timestamp);
		//m1043116
		webServiceAudit.setTwa_created_date(creationDate);
		webServiceAudit.setTwa_endt_id(policyVO.getEndtId());
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(policyVO.getPolLinkingId());
		webServiceAudit.setTwa_policy_no(policyVO.getPolicyNo());
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		if(createSBSQuoteResponse!=null && createSBSQuoteResponse.getQuoteId()!=null) {
			webServiceAudit.setTwa_quote_no(Integer.parseInt(createSBSQuoteResponse.getQuoteId()));
		}
		else
		{
			webServiceAudit.setTwa_quote_no(null);
		}
		/*if(createSBSQuoteResponse!= null && createSBSQuoteResponse.getSbswsValidators()!=null  && !createSBSQuoteResponse.getSbswsValidators().isEmpty()) {
			if(createSBSQuoteResponse.getSbswsValidators().size()>0 && createSBSQuoteResponse.getSbswsValidators().get(0).getCategory().equals("ERROR")){
				webServiceAudit.setTwa_transaction_res_type(" ");
			}else{
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
			}
		}
		else {
			webServiceAudit.setTwa_transaction_res_type(" ");
		}*/
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSQuoteRequest)));
		webServiceAudit.setTwa_response_xml(null);
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_IN_PROGRESS);
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type("CreateSBSQuote");
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @description : Request Object = CreateSBSQuoteRequest
	 * 				: Response Object = CreateSBSQuoteResponse
	 * @API : UpdateSBSQuote 
	 */
	
	public WebServiceAudit mapUpdateSBSQuoteToAudit(PolicyVO policyVO , CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse , HeaderInfo headerInfo , String quoteNo) throws ParseException{
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		Date date = new Date();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_req_start_time(timestamp);
		webServiceAudit.setTwa_created_date(date);
		webServiceAudit.setTwa_endt_id(policyVO.getEndtId());
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(policyVO.getPolLinkingId());
		webServiceAudit.setTwa_policy_no(policyVO.getPolicyNo());
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSQuoteRequest)));
		webServiceAudit.setTwa_quote_no(Integer.parseInt(quoteNo));
		if(createSBSQuoteResponse!=null && !Utils.isEmpty(createSBSQuoteResponse)) {
			webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSQuoteResponse)));
			if(createSBSQuoteResponse.getQuoteId()!=null) {
				webServiceAudit.setTwa_quote_no(Integer.parseInt(createSBSQuoteResponse.getQuoteId()));
			}
			if(createSBSQuoteResponse.getSbswsValidators()!=null && !Utils.isEmpty(createSBSQuoteResponse.getSbswsValidators()) && createSBSQuoteResponse.getSbswsValidators().get(0).getCategory().equals("ERROR")) {
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
			}
			else
			{
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_IN_PROGRESS);
			}
		}
		else {
			webServiceAudit.setTwa_response_xml(null);
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_IN_PROGRESS);
		}
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type("UpdateSBSQuote");
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}

	/**
	 * @description : Request Object = policyNo
	 * @API : GetDocumentsList 
	 */
	
	public WebServiceAudit mapGetDocumentsListToAudit(PolicyVO policyVO, String opType, HeaderInfo headerInfo) throws ParseException{
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		Date date = new Date();
		//added to set request start time-m1043116
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_req_start_time(timestamp);
		//m1043116
		webServiceAudit.setTwa_created_date(date);
		webServiceAudit.setTwa_endt_id(null);
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(null);
		webServiceAudit.setTwa_policy_no(policyVO.getPolicyNo());
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		webServiceAudit.setTwa_quote_no(null);
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint("Policy No: "+policyVO.getPolicyNo()+" Policy Year : "+policyVO.getPolicyYear())));
		webServiceAudit.setTwa_response_xml(null);
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type(opType);
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @description : Request Object = policyNo , documentId
	 * @API : GetDocument 
	 */
	
	public WebServiceAudit mapGetDocumentToAudit(String opType , String documentId , HeaderInfo headerInfo,PolicyVO policyVO) throws ParseException{
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		Date date = new Date();
		//added to set request start time-m1043116
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    webServiceAudit.setTwa_req_start_time(timestamp);
		//m1043116
		webServiceAudit.setTwa_created_date(date);
		webServiceAudit.setTwa_endt_id(null);
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(null);
		String[] policyInfo = documentId.split(":");
		System.out.println(policyInfo[0]);
		try {
			webServiceAudit.setTwa_policy_no(Long.parseLong(policyInfo[0]));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			webServiceAudit.setTwa_policy_no(null);
		}
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		webServiceAudit.setTwa_quote_no(null);
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(documentId)));
		webServiceAudit.setTwa_response_xml(null);
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type(opType);
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	
	/**
	 * @description : Request Object = quotationNo
	 * 				: Response Object = StagingTableDBHandler
	 * @API : GetSBSQuote 
	 */
	public WebServiceAudit mapGetSBSQuoteToAudit(PolicyVO policyVO , String quotationNo, String policyNo, String expiryPolicyYear , String opType , HeaderInfo headerInfo) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		Date date = new Date();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_req_start_time(timestamp);
		webServiceAudit.setTwa_created_date(date);
		webServiceAudit.setTwa_endt_id(null);
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(null);
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		if(!Utils.isEmpty(quotationNo)) {
			webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint("Quotation no: "+quotationNo)));
			try {
				webServiceAudit.setTwa_quote_no(Integer.parseInt(quotationNo));
			}
			catch( NumberFormatException e) {
				webServiceAudit.setTwa_quote_no(null);
			}
		}
		if(!Utils.isEmpty(policyNo) || !Utils.isEmpty(expiryPolicyYear)) {
			webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(" Policy No: " + policyNo+" Expiry Policy Year: "+expiryPolicyYear)));
			try {
				webServiceAudit.setTwa_policy_no(Long.parseLong(policyNo));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				webServiceAudit.setTwa_policy_no(null);
			}
		}
		webServiceAudit.setTwa_response_xml(null);
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type(opType);
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
		
	}
	
	/**
	 * @description : Request Object = CreateSBSPolicyRequest
	 * 				: Response Object = CreateSBSPolicyResponse
	 * @API : CreateSBSPolicy
	 */
	public WebServiceAudit mapCreateSBSPolicyToAudit(CreateSBSPolicyRequest createSBSPolicyRequest,CreateSBSPolicyResponse createSBSPolicyResponse,String opType,HeaderInfo headerInfo, PolicyVO policyVO) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		Date date = new Date();
		//added for request start time -m1043116
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_req_start_time(timestamp);
		//m1043116
		webServiceAudit.setTwa_created_date(date);
		webServiceAudit.setTwa_endt_id(null);
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(null);
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSPolicyRequest)));
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type(opType);
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		try {
			webServiceAudit.setTwa_quote_no(Integer.parseInt(createSBSPolicyRequest.getQuoteId()));
		}
		catch( NumberFormatException e) {
			webServiceAudit.setTwa_quote_no(null);
		}
		if(createSBSPolicyResponse!= null && createSBSPolicyResponse.getSbswsValidators()!=null  && !createSBSPolicyResponse.getSbswsValidators().isEmpty()) {
			webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSPolicyResponse)));
			if(createSBSPolicyResponse.getSbswsValidators().size()>0 && createSBSPolicyResponse.getSbswsValidators().get(0).getCategory().equals("ERROR")){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
			}else{
			webServiceAudit.setTwa_transaction_res_type(null);
			}
		}
		else {
			webServiceAudit.setTwa_response_xml(null);
			webServiceAudit.setTwa_transaction_res_type(null);
		}
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	/**
	 * @description : Request Object = policyNo
	 * 				: Response Object = RetrievePolicyByPolicyNoResponse
	 * @API : GetSBSPolicy
	 */
	public WebServiceAudit mapGetSBSPolicyToAudit(String policyNo,Short policyYear,RetrievePolicyByPolicyNoResponse retrievePolicyByPolicyNoResponse,String opType,HeaderInfo headerInfo, PolicyVO policyVO) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		//added for request start time -m1043116
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_req_start_time(timestamp);
		//m1043116
		Date date = new Date();
		webServiceAudit.setTwa_created_date(date);
		webServiceAudit.setTwa_endt_id(null);
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(null);
		try {
			webServiceAudit.setTwa_policy_no(Long.parseLong(policyNo));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			webServiceAudit.setTwa_policy_no(null);
		}
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint("Policy No : "+ policyNo+" Policy Year : "+policyYear)));
		webServiceAudit.setTwa_response_xml(null);
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type(opType);
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		webServiceAudit.setTwa_quote_no(null);
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	
	public WebServiceAudit mapUploadDocumentToAudit(String quoteId,String opType,UploadDocumentRequest uploadDocumentRequest,HeaderInfo headerInfo, PolicyVO policyVO) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_APPCONFIG);
		webServiceAudit.setTwa_class_code(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_CLASS_CODE)));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println("m1043116+start time"+timestamp);
		webServiceAudit.setTwa_req_start_time(timestamp);
		Date date = new Date();
		webServiceAudit.setTwa_created_date(date);
		webServiceAudit.setTwa_endt_id(null);
		webServiceAudit.setTwa_partner(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
		webServiceAudit.setTwa_policy_id(null);
		webServiceAudit.setTwa_policy_no(null);
		webServiceAudit.setTwa_policy_type(Integer.parseInt(resourceBundle.getString(com.Constant.CONST_SBS_POLICY_TYPE)));
		/*if(createSBSQuoteResponse!= null && createSBSQuoteResponse.getSbswsValidators()!=null  && !createSBSQuoteResponse.getSbswsValidators().isEmpty()) {
			if(createSBSQuoteResponse.getSbswsValidators().size()>0 && createSBSQuoteResponse.getSbswsValidators().get(0).getCategory().equals("ERROR")){
				webServiceAudit.setTwa_transaction_res_type("");
			}else{
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
			}
		}
		else {
			webServiceAudit.setTwa_transaction_res_type("");
		}*/
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(uploadDocumentRequest)));
		webServiceAudit.setTwa_response_xml(null);
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_soap_request_xml(null);
		webServiceAudit.setTwa_transaction_req_type(opType);
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(policyVO.getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		try {
			webServiceAudit.setTwa_quote_no(Integer.parseInt(quoteId));
		}
		catch( NumberFormatException e) {
			webServiceAudit.setTwa_quote_no(null);
		}
		return webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}

	public void mapStartTimeForAudit(String pmmId) {
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_req_start_time(startTime);
		webServiceAudit.setTwa_partner(pmmId);
	}
	
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	public void mapRetrieveHomeQuoteByPolicyEmailToAudit(RetrieveQuoteByPolicyRequest retrieveQuoteByPolicyRequest,RetrieveHomeQuoteByPolicyResponse retrieveHomeQuoteByPolicyResponse, HeaderInfo headerInfo, HomeInsuranceVO homeInsuranceVO){
		webServiceAudit.setTwa_class_code(homeInsuranceVO.getClassCode());
		webServiceAudit.setTwa_created_date(retrieveHomeQuoteByPolicyResponse.getQuotes().get(0).getTransactionDetails().getEffectiveDate());
		webServiceAudit.setTwa_endt_id(retrieveHomeQuoteByPolicyResponse.getQuotes().get(0).getEndtId());
		webServiceAudit.setTwa_policy_id(homeInsuranceVO.getCommonVO().getPolicyId());
		webServiceAudit.setTwa_policy_no(Long.valueOf(retrieveQuoteByPolicyRequest.getTransactionNumber()));
		webServiceAudit.setTwa_policy_type(homeInsuranceVO.getPolicyType());
		webServiceAudit.setTwa_quote_no(retrieveHomeQuoteByPolicyResponse.getQuotes().get(0).getQuotationNo().intValue());
		webServiceAudit.setTwa_request_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrieveQuoteByPolicyRequest)));
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrieveHomeQuoteByPolicyResponse)));
		
		webServiceAudit.setTwa_transaction_ref_no(null);
		webServiceAudit.setTwa_transaction_req_type("RetrieveHomeQuotebyPolNoEmailId");
		if(!Utils.isEmpty(retrieveHomeQuoteByPolicyResponse.getQuotes().get(0).getErrors())){
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		webServiceAudit.setTwa_transaction_serreq_no(null);
		webServiceAudit.setTwa_user_name(homeInsuranceVO.getCommonVO().getLoggedInUser().getUserId());
		webServiceAudit.setTwa_header_info(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(headerInfo)));
		webServiceAudit.setTwa_client_ip(headerInfo.getHeaderInfo().get(com.Constant.CONST_IPADDRESS));
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(endTime);
		long diffInMs = webServiceAudit.getTwa_req_start_time().getTime() - webServiceAudit.getTwa_res_end_time().getTime();
		webServiceAudit.setTwa_time_diff_in_ms(Math.abs((int)diffInMs));
		webServiceAuditHandler.saveToWebServiceAudit(webServiceAudit);
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start

}
