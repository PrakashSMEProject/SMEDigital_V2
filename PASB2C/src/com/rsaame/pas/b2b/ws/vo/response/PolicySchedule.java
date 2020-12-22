
package com.rsaame.pas.b2b.ws.vo.response;

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
    "policyType",
    "policyYear",
    "creationDate",
    "effectiveDate",
    "expirationDate",
    "duration"
})
public class PolicySchedule {

    @JsonProperty("policyType")
    private String policyType;
    @JsonProperty("policyYear")
    private String policyYear;
    @JsonProperty("creationDate")
    private String creationDate;
    @JsonProperty("effectiveDate")
    private String effectiveDate;
    @JsonProperty("expirationDate")
    private String expirationDate;
    @JsonProperty("duration")
    private String duration;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("policyType")
    public String getPolicyType() {
        return policyType;
    }

    @JsonProperty("policyType")
    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }
    @JsonProperty("policyYear")
    public String getPolicyYear() {
		return policyYear;
	}
    @JsonProperty("policyYear")
	public void setPolicyYear(String policyYear) {
		this.policyYear = policyYear;
	}

	public PolicySchedule withPolicyType(String policyType) {
        this.policyType = policyType;
        return this;
    }

    @JsonProperty("creationDate")
    public String getCreationDate() {
        return creationDate;
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

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public PolicySchedule withDuration(String duration) {
        this.duration = duration;
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
