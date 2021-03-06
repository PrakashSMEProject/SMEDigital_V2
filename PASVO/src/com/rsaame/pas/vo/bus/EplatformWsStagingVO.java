package com.rsaame.pas.vo.bus;

//default package
//Generated Dec 7, 2018 7:48:29 PM by Hibernate Tools 5.3.0.Beta2

import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
* EplatformWsStaging generated by hbm2java
*/
public class EplatformWsStagingVO extends BaseVO implements java.io.Serializable {

	private Long policyId;
	private Long endtId;
	private Long polPolicyNo;
	private Long polEndtNo;
	private Byte polStatus;
	private Date polValidityStartDate;
	private Long polQuotationNo;
	private Long polLinkingId;
	private Long insInsuredCode;
	private Clob quoRequest;
	private Clob quoResponse;
	private Blob quoIntrResponseAdd; //manually added
	private Blob quoIntrBatchResponse;
	private Long twa_id;
	
	

	public Long getTwa_id() {
		return twa_id;
	}
	public void setTwa_id(Long twa_id) {
		this.twa_id = twa_id;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public Long getEndtId() {
		return endtId;
	}
	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}
	public Long getPolPolicyNo() {
		return polPolicyNo;
	}
	public void setPolPolicyNo(Long polPolicyNo) {
		this.polPolicyNo = polPolicyNo;
	}
	public Long getPolEndtNo() {
		return polEndtNo;
	}
	public void setPolEndtNo(Long polEndtNo) {
		this.polEndtNo = polEndtNo;
	}
	public Byte getPolStatus() {
		return polStatus;
	}
	public void setPolStatus(Byte polStatus) {
		this.polStatus = polStatus;
	}
	public Date getPolValidityStartDate() {
		return polValidityStartDate;
	}
	public void setPolValidityStartDate(Date polValidityStartDate) {
		this.polValidityStartDate = polValidityStartDate;
	}
	public Long getPolQuotationNo() {
		return polQuotationNo;
	}
	public void setPolQuotationNo(Long polQuotationNo) {
		this.polQuotationNo = polQuotationNo;
	}
	public Long getPolLinkingId() {
		return polLinkingId;
	}
	public void setPolLinkingId(Long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}
	public Long getInsInsuredCode() {
		return this.insInsuredCode;
	}

	public void setInsInsuredCode(Long insInsuredCode) {
		this.insInsuredCode = insInsuredCode;
	}

	public Clob getQuoRequest() {
		return quoRequest;
	}
	public void setQuoRequest(Clob quoRequest) {
		this.quoRequest = quoRequest;
	}
	public Clob getQuoResponse() {
		return quoResponse;
	}
	public void setQuoResponse(Clob quoResponse) {
		this.quoResponse = quoResponse;
	}
	public Blob getQuoIntrResponseAdd() {
		return quoIntrResponseAdd;
	}
	public void setQuoIntrResponseAdd(Blob quoIntrResponseAdd) {
		this.quoIntrResponseAdd = quoIntrResponseAdd;
	}
	public Blob getQuoIntrBatchResponse() {
		return quoIntrBatchResponse;
	}
	public void setQuoIntrBatchResponse(Blob quoIntrBatchResponse) {
		this.quoIntrBatchResponse = quoIntrBatchResponse;
	}
	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
