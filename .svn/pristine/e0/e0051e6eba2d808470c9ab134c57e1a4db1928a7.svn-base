package com.rsaame.pas.b2b.ws.vo;
import java.util.ArrayList;
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
import com.rsaame.pas.b2b.ws.vo.policy.response.DocumantId;

import com.rsaame.pas.b2b.ws.exception.SBSWSValidationException;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "policyId",
    "customerId",
    "policyYear",
    "policyInternalReference",
    "documentId"
})
public class CreateSBSPolicyResponse {

    @JsonProperty("policyId")
    private String policyId;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("documentId")
    private List<Document> documentId = new ArrayList<Document>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @JsonProperty("SBSWSValidators")
	private List<SBSWSValidators> sbswsValidators;

	@JsonProperty("SBSWSValidationException")
	private SBSWSValidationException sbswsValidationException;
	
	@JsonProperty("policyInternalReference")
	private String policyInternalReference;  

	@JsonProperty("policyYear")
	private Integer policyYear;
	
	@JsonProperty("policyYear")
	public Integer getPolicyYear() {
		return policyYear;
	}

	@JsonProperty("policyYear")
	public void setPolicyYear(Integer policyYear) {
		this.policyYear = policyYear;
	}

	@JsonProperty("policyInternalReference")
    public String getPolicyInternalReference() {
		return policyInternalReference;
	}

	@JsonProperty("policyInternalReference")
	public void setPolicyInternalReference(String policyInternalReference) {
		this.policyInternalReference = policyInternalReference;
	}
	
	public CreateSBSPolicyResponse withPolicyInternalReference(String policyInternalReference) {
		this.policyInternalReference = policyInternalReference;
		return this;
	}

	@JsonProperty("policyId")
    public String getPolicyId() {
        return policyId;
    }

    @JsonProperty("policyId")
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public CreateSBSPolicyResponse withPolicyId(String policyId) {
        this.policyId = policyId;
        return this;
    }

    @JsonProperty("customerId")
    public String getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public CreateSBSPolicyResponse withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    @JsonProperty("documentId")
    public List<Document> getDocumentId() {
        return documentId;
    }

    @JsonProperty("documentId")
    public void setDocumentId(List<Document> documentId) {
        this.documentId = documentId;
    }

    public CreateSBSPolicyResponse withDocumentId(List<Document> documentId) {
        this.documentId = documentId;
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

}