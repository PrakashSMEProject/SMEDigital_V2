package com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import org.hibernate.Hibernate;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.GetDocumentListResponse;
import com.rsaame.pas.b2b.ws.vo.GetDocumentResponse;
import com.rsaame.pas.b2b.ws.vo.RetrievePolicyByPolicyNoResponse;
import com.rsaame.pas.b2b.ws.vo.RetrieveSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.dao.WebServiceAuditDaoImp;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.handler.WebServiceAuditHandler;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;

public class UpdateWebServiceAuditMapper {
	WebServiceAuditHandler webServiceAuditHandler = new WebServiceAuditHandler();
	
	public Boolean mapcreateSBSQuoteToAudit(WebServiceAudit webServiceAudit , CreateSBSQuoteResponse createSBSQuoteResponse) throws ParseException{
		if(createSBSQuoteResponse!=null && !Utils.isEmpty(createSBSQuoteResponse)) {
			webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSQuoteResponse)));
			if(createSBSQuoteResponse.getQuoteId()!=null) {
				webServiceAudit.setTwa_quote_no(Integer.parseInt(createSBSQuoteResponse.getQuoteId()));
			}
			else {
				webServiceAudit.setTwa_quote_no(null);
			}
			if(createSBSQuoteResponse.getSbswsValidators()!=null && !Utils.isEmpty(createSBSQuoteResponse.getSbswsValidators()) && createSBSQuoteResponse.getSbswsValidators().get(0).getCategory().equals(com.Constant.CONST_ERROR1)) {
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
			}
			else
			{
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_IN_PROGRESS);
			}
		}
		else {
			webServiceAudit.setTwa_response_xml(null);
			webServiceAudit.setTwa_quote_no(null);
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_IN_PROGRESS);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(timestamp);
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	}
	
	public Boolean mapUpdateSBSQuoteToAudit(WebServiceAudit webServiceAudit , CreateSBSQuoteResponse createSBSQuoteResponse) throws ParseException{
		if(createSBSQuoteResponse!=null && !Utils.isEmpty(createSBSQuoteResponse)) {
			webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSQuoteResponse)));
			if(createSBSQuoteResponse.getQuoteId()!=null) {
				webServiceAudit.setTwa_quote_no(Integer.parseInt(createSBSQuoteResponse.getQuoteId()));
			}
			if(createSBSQuoteResponse.getSbswsValidators()!=null && !Utils.isEmpty(createSBSQuoteResponse.getSbswsValidators()) && createSBSQuoteResponse.getSbswsValidators().get(0).getCategory().equals(com.Constant.CONST_ERROR1)) {
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
			}
		}
		else {
			webServiceAudit.setTwa_response_xml(null);
			webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_IN_PROGRESS);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(timestamp);
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	}
	
	public Boolean mapGetDocumentsListToAudit(WebServiceAudit webServiceAudit,GetDocumentListResponse documentListResponse) throws ParseException{
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(documentListResponse)));
		//added to set  transaction response type and response end time-m1043116
				if(documentListResponse!= null) {
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
				}
				else{
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
				}
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				webServiceAudit.setTwa_res_end_time(timestamp);
				//end-m1043116
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	}
	
	public Boolean mapGetDocumentsListToAudit(WebServiceAudit webServiceAudit, GetDocumentResponse documentResponse) throws ParseException{
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(documentResponse)));
		//added to set  transaction response type and response end time-m1043116
		if(documentResponse!= null) {
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		else{
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(timestamp);
		//end-m1043116
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	}
	
	public Boolean mapUploadDocumentToAudit(WebServiceAudit webServiceAudit,UploadDocumentResponse uploadDocumentResponse) {
		//webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(uploadDocumentResponse)));
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
		
	}
	
	public Boolean mapUploadDocumentToAudit(Long twa_id ,UploadDocumentResponse uploadDocumentResponse) {
		return webServiceAuditHandler.updateWebServiceAuditForUploadDoc(twa_id, uploadDocumentResponse);
		
	}
	
	public Boolean mapGetSBSQuoteToAudit(WebServiceAudit webServiceAudit , CreateSBSQuoteResponse createSBSQuoteResponse) {
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSQuoteResponse)));
		if(createSBSQuoteResponse.getQuoteId()!=null) {
			webServiceAudit.setTwa_quote_no(Integer.parseInt(createSBSQuoteResponse.getQuoteId()));
		}
		else {
			webServiceAudit.setTwa_quote_no(null);
		}
		//added to set  transaction response type and response end time-m1043116
		if(createSBSQuoteResponse!= null && createSBSQuoteResponse.getSbswsValidators()!=null  && !createSBSQuoteResponse.getSbswsValidators().isEmpty()) {
		if(createSBSQuoteResponse.getSbswsValidators().size()>0 && createSBSQuoteResponse.getSbswsValidators().get(0).getCategory().equals(com.Constant.CONST_ERROR1)){
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		}
		else {
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(timestamp);
		//end-m1043116
		
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	}
	
	public Boolean mapGetSBSQuoteToAudit(WebServiceAudit webServiceAudit , RetrieveSBSQuoteResponse retrieveSBSQuoteResponse) {
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrieveSBSQuoteResponse)));
		if(retrieveSBSQuoteResponse.getQuoteId()!=null) {
			webServiceAudit.setTwa_quote_no(Integer.parseInt(retrieveSBSQuoteResponse.getQuoteId()));
		}
		else {
			webServiceAudit.setTwa_quote_no(null);
		}
		//added to set  transaction response type and response end time-m1043116
		if(retrieveSBSQuoteResponse!= null && retrieveSBSQuoteResponse.getSbswsValidators()!=null  && !retrieveSBSQuoteResponse.getSbswsValidators().isEmpty()) {
		if(retrieveSBSQuoteResponse.getSbswsValidators().size()>0 && retrieveSBSQuoteResponse.getSbswsValidators().get(0).getCategory().equals(com.Constant.CONST_ERROR1)){
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		}
		else {
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(timestamp);
		//end-m1043116
		
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	}
	
	public Boolean mapCreateSBSPolicyToAudit(WebServiceAudit webServiceAudit ,CreateSBSPolicyResponse createSBSPolicyResponse) {
		if(createSBSPolicyResponse.getPolicyId()!=null) {
			String policyString = createSBSPolicyResponse.getPolicyId();
			String[] policy = policyString.split("/");
			int length = policy.length;
			webServiceAudit.setTwa_policy_no(Long.parseLong(policy[length-1]));
		}
		else {
			webServiceAudit.setTwa_policy_no(null);
		}
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(createSBSPolicyResponse)));
		//added to set  transaction response type and response end time-m1043116
				if(createSBSPolicyResponse!= null && createSBSPolicyResponse.getSbswsValidators()!=null  && !createSBSPolicyResponse.getSbswsValidators().isEmpty()) {
				if(createSBSPolicyResponse.getSbswsValidators().size()>0 && createSBSPolicyResponse.getSbswsValidators().get(0).getCategory().equals(com.Constant.CONST_ERROR1)){
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
				}else{
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
				}
				}
				else {
				webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
				}
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				webServiceAudit.setTwa_res_end_time(timestamp);
				//end-m1043116
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	}
	
	public Boolean mapGetSBSPolicyToAudit(WebServiceAudit webServiceAudit,RetrievePolicyByPolicyNoResponse retrievePolicyByPolicyNoResponse) {
		if(retrievePolicyByPolicyNoResponse.getPolicyId()!=null && !retrievePolicyByPolicyNoResponse.getPolicyId().contains("No Policy Found")) {
			webServiceAudit.setTwa_policy_no(Long.parseLong(retrievePolicyByPolicyNoResponse.getPolicyId()));
		}
		else {
			webServiceAudit.setTwa_policy_no(null);
		}
		webServiceAudit.setTwa_response_xml(Hibernate.createClob(WSAppUtils.getJsonStringFromObjectPrettyPrint(retrievePolicyByPolicyNoResponse)));
		//added to set  transaction response type and response end time-m1043116
		if(retrievePolicyByPolicyNoResponse!= null && retrievePolicyByPolicyNoResponse.getSbswsValidators()!=null  && !retrievePolicyByPolicyNoResponse.getSbswsValidators().isEmpty()) {
		if(retrievePolicyByPolicyNoResponse.getSbswsValidators().size()>0 && retrievePolicyByPolicyNoResponse.getSbswsValidators().get(0).getCategory().equals(com.Constant.CONST_ERROR1)){
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_ERROR1);
		}else{
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		}
		else {
		webServiceAudit.setTwa_transaction_res_type(com.Constant.CONST_SUCCESS1);
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		webServiceAudit.setTwa_res_end_time(timestamp);
		//end-m1043116
		return webServiceAuditHandler.updateWebServiceAudit(webServiceAudit);
	} 
}
