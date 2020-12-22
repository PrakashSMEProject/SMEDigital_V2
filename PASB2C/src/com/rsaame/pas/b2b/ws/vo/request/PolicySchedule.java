
package com.rsaame.pas.b2b.ws.vo.request;

import java.util.HashMap;
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

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"policyYear",
    "creationDate",
    "effectiveDate",
    "expirationDate",
    "policyType"
})
public class PolicySchedule {

    @JsonProperty("creationDate")
    private String creationDate;
    @JsonProperty("effectiveDate")
    private String effectiveDate;
    @JsonProperty("expirationDate")
    private String expirationDate;
    @JsonProperty("policyType")
    private String policyType;
    private String policyYear;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("creationDate")
    public String getCreationDate() {
        return creationDate;
    }

    public String getPolicyYear() {
		return policyYear;
	}

	public void setPolicyYear(String policyYear) {
		this.policyYear = policyYear;
	}

	@JsonProperty("creationDate")
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public PolicySchedule withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    @JsonProperty("effectiveDate")
    public String getEffectiveDate() {
        return effectiveDate;
    }

    @JsonProperty("effectiveDate")
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public PolicySchedule withEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    @JsonProperty("expirationDate")
    public String getExpirationDate() {
        return expirationDate;
    }

    @JsonProperty("expirationDate")
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public PolicySchedule withExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @JsonProperty("policyType")
    public String getPolicyType() {
        return policyType;
    }

    @JsonProperty("policyType")
    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public PolicySchedule withPolicyType(String policyType) {
        this.policyType = policyType;
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

}
