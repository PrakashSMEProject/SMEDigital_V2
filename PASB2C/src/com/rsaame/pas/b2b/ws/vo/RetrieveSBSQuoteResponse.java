package com.rsaame.pas.b2b.ws.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rsaame.pas.b2b.ws.constant.GetQuoteResponse;
import com.rsaame.pas.b2b.ws.exception.SBSWSValidationException;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.response.Agent;
import com.rsaame.pas.b2b.ws.vo.response.LiabilityInformation;
import com.rsaame.pas.b2b.ws.vo.response.PolicyHolder;
import com.rsaame.pas.b2b.ws.vo.response.PolicySchedule;
import com.rsaame.pas.b2b.ws.vo.response.SelectedPlan;

//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)

@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "quoteId", "quoteInternalReference", "quoteStatus","quoteExpiryDate","policyId","uwApprovalStatus", "policySchedule",
		"liabilityInformation", "policyHolder", "selectedPlan", "agent","renewalTermId","renewalTerms"})
public class RetrieveSBSQuoteResponse implements GetQuoteResponse{

	@JsonProperty("quoteId")
	private String quoteId;
	@JsonProperty("quoteInternalReference")
	private String quoteInternalReference;
	@JsonProperty("quoteStatus")
	private String quoteStatus;

	//@JsonSerialize(using= JsonDateSerializer.class)
	@JsonProperty("quoteExpiryDate")
	private String quoteExpiryDate;
	
	
	@JsonProperty("policyId")
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private String policyId;
	
	@JsonProperty("uwApprovalStatus")
	private String uwApprovalStatus;
	@JsonProperty("policySchedule")
	private PolicySchedule policySchedule;
	@JsonProperty("liabilityInformation")
	private LiabilityInformation liabilityInformation;
	@JsonProperty("policyHolder")
	private PolicyHolder policyHolder;
	@JsonProperty("selectedPlan")
	private SelectedPlan selectedPlan;
	@JsonProperty("agent")
	private Agent agent;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	@JsonProperty("renewalTerms")
	private String renewalTerms;
	//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
	@JsonProperty("renewalTermId")
	private String renewalTermId;
	//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
	
	/*@JsonProperty("SBSWSValidations")
	private SBSWSValidations sbsWSValidations;
*/
	@JsonProperty("SBSWSValidators")
	private List<SBSWSValidators> sbswsValidators;

	@JsonProperty("SBSWSValidationException")
	private SBSWSValidationException sbswsValidationException;

	@JsonProperty("quoteId")
	public String getQuoteId() {
		return quoteId;
	}

	@JsonProperty("quoteId")
	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public RetrieveSBSQuoteResponse withQuoteId(String quoteId) {
		this.quoteId = quoteId;
		return this;
	}

	@JsonProperty("quoteInternalReference")
	public String getQuoteInternalReference() {
		return quoteInternalReference;
	}

	@JsonProperty("quoteInternalReference")
	public void setQuoteInternalReference(String quoteInternalReference) {
		this.quoteInternalReference = quoteInternalReference;
	}

	public RetrieveSBSQuoteResponse withQuoteInternalReference(String quoteInternalReference) {
		this.quoteInternalReference = quoteInternalReference;
		return this;
	}

	@JsonProperty("quoteStatus")
	public String getQuoteStatus() {
		return quoteStatus;
	}

	@JsonProperty("quoteStatus")
	public void setQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

	public RetrieveSBSQuoteResponse withQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
		return this;
	}
	
	@JsonProperty("quoteExpiryDate")
	public String getquoteExpiryDate() {
		return quoteExpiryDate;
	}

	@JsonProperty("quoteExpiryDate")
	public void setquoteExpiryDate(String quoteExpiryDate) {
		this.quoteExpiryDate = quoteExpiryDate;
	}

	public RetrieveSBSQuoteResponse withquoteExpiryDate(String quoteExpiryDate) {
		this.quoteExpiryDate = quoteExpiryDate;
		return this;
	}

	@JsonProperty("uwApprovalStatus")
	public String getUwApprovalStatus() {
		return uwApprovalStatus;
	}

	@JsonProperty("uwApprovalStatus")
	public void setUwApprovalStatus(String uwApprovalStatus) {
		this.uwApprovalStatus = uwApprovalStatus;
	}

	public RetrieveSBSQuoteResponse withUwApprovalStatus(String uwApprovalStatus) {
		this.uwApprovalStatus = uwApprovalStatus;
		return this;
	}

	@JsonProperty("policySchedule")
	public PolicySchedule getPolicySchedule() {
		return policySchedule;
	}

	@JsonProperty("policySchedule")
	public void setPolicySchedule(PolicySchedule policySchedule) {
		this.policySchedule = policySchedule;
	}

	public RetrieveSBSQuoteResponse withPolicySchedule(PolicySchedule policySchedule) {
		this.policySchedule = policySchedule;
		return this;
	}

	@JsonProperty("liabilityInformation")
	public LiabilityInformation getLiabilityInformation() {
		return liabilityInformation;
	}

	@JsonProperty("liabilityInformation")
	public void setLiabilityInformation(LiabilityInformation liabilityInformation) {
		this.liabilityInformation = liabilityInformation;
	}

	public RetrieveSBSQuoteResponse withLiabilityInformation(
			LiabilityInformation liabilityInformation) {
		this.liabilityInformation = liabilityInformation;
		return this;
	}

	@JsonProperty("policyHolder")
	public PolicyHolder getPolicyHolder() {
		return policyHolder;
	}

	@JsonProperty("policyHolder")
	public void setPolicyHolder(PolicyHolder policyHolder) {
		this.policyHolder = policyHolder;
	}

	public RetrieveSBSQuoteResponse withPolicyHolder(PolicyHolder policyHolder) {
		//this.policyHolder = policyHolder;
		return this;
	}

	@JsonProperty("selectedPlan")
	public SelectedPlan getSelectedPlan() {
		return selectedPlan;
	}

	@JsonProperty("selectedPlan")
	public void setSelectedPlan(SelectedPlan selectedPlan) {
		this.selectedPlan = selectedPlan;
	}

	public RetrieveSBSQuoteResponse withSelectedPlan(SelectedPlan selectedPlan) {
		this.selectedPlan = selectedPlan;
		return this;
	}

	@JsonProperty("agent")
	public Agent getAgent() {
		return agent;
	}

	@JsonProperty("agent")
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public RetrieveSBSQuoteResponse withAgent(Agent agent) {
		this.agent = agent;
		return this;
	}
	
	@JsonProperty("renewalTerms")
	public String getRenewalTerms() {
		return renewalTerms;
	}
	
	@JsonProperty("renewalTerms")
	public void setRenewalTerms(String renewalTerms) {
		this.renewalTerms = renewalTerms;
	}
	
	public RetrieveSBSQuoteResponse withRenewalTerms(String renewalTerms) {
		this.renewalTerms = renewalTerms;
		return this;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	/*
	 * public SBSWSValidations getSbsWSValidations() { return sbsWSValidations;
	 * }
	 * 
	 * public void setSbsWSValidations(SBSWSValidations sbsWSValidations) {
	 * this.sbsWSValidations = sbsWSValidations; }
	 */
	public SBSWSValidationException getSbswsValidationException() {
		return sbswsValidationException;
	}

	public void setSbswsValidationException(SBSWSValidationException sbswsValidationException) {
		this.sbswsValidationException = sbswsValidationException;
	}

	public List<SBSWSValidators> getSbswsValidators() {
		return sbswsValidators;
	}

	public void setSbswsValidators(List<SBSWSValidators> sbswsValidators) {
		this.sbswsValidators = sbswsValidators;
	}
	
	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}


	//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
	public String getRenewalTermId() {
		return renewalTermId;
	}

	public void setRenewalTermId(String renewalTermId) {
		this.renewalTermId = renewalTermId;
	}
	//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
}
