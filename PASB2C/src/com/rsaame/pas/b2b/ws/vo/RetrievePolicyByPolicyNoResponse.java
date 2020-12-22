package com.rsaame.pas.b2b.ws.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.rsaame.pas.b2b.ws.exception.SBSWSValidationException;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.policy.request.Payment;
import com.rsaame.pas.b2b.ws.vo.response.PolicyHolder;
import com.rsaame.pas.b2b.ws.vo.response.PolicySchedule;
import com.rsaame.pas.b2b.ws.vo.response.SelectedPlan;

public class RetrievePolicyByPolicyNoResponse {

	@JsonProperty("policyId")
	private String policyId;
	@JsonProperty("internalReferenceNumber")
	private String internalReferenceNumber;
	/*@JsonProperty("policyStatus")
	private String policyStatus;*/
	@JsonProperty("policySchedule")
	private PolicySchedule policySchedule;
	@JsonProperty("policyHolder")
	private PolicyHolder policyHolder;
    @JsonProperty("payment")
    private Payment payment;
	@JsonProperty("selectedPlan")
	private SelectedPlan selectedPlan;
	@JsonProperty("SBSWSValidators")
	private List<SBSWSValidators> sbswsValidators;
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@JsonProperty("SBSWSValidationException")
	private SBSWSValidationException sbswsValidationException;
	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}



	public String getInternalReferenceNumber() {
		return internalReferenceNumber;
	}

	public void setInternalReferenceNumber(String internalReferenceNumber) {
		this.internalReferenceNumber = internalReferenceNumber;
	}

	public PolicySchedule getPolicySchedule() {
		return policySchedule;
	}

	public void setPolicySchedule(PolicySchedule policySchedule) {
		this.policySchedule = policySchedule;
	}

	public PolicyHolder getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(PolicyHolder policyHolder) {
		this.policyHolder = policyHolder;
	}

	public SelectedPlan getSelectedPlan() {
		return selectedPlan;
	}

	public void setSelectedPlan(SelectedPlan selectedPlan) {
		this.selectedPlan = selectedPlan;
	}

	public List<SBSWSValidators> getSbswsValidators() {
		return sbswsValidators;
	}

	public void setSbswsValidators(List<SBSWSValidators> sbswsValidators) {
		this.sbswsValidators = sbswsValidators;
	}

	public SBSWSValidationException getSbswsValidationException() {
		return sbswsValidationException;
	}

	public void setSbswsValidationException(SBSWSValidationException sbswsValidationException) {
		this.sbswsValidationException = sbswsValidationException;
	}


}
