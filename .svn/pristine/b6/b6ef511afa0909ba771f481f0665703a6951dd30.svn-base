
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
    "numberOfClaims",
    "valueOfClaims",
    "remarks"
})
public class ClaimInformation {

    @JsonProperty("numberOfClaims")
    private Integer numberOfClaims;
    @JsonProperty("valueOfClaims")
    private Integer valueOfClaims;
    @JsonProperty("remarks")
    private String remarks;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("numberOfClaims")
    public Integer getNumberOfClaims() {
        return numberOfClaims;
    }

    @JsonProperty("numberOfClaims")
    public void setNumberOfClaims(Integer numberOfClaims) {
        this.numberOfClaims = numberOfClaims;
    }

    public ClaimInformation withNumberOfClaims(Integer numberOfClaims) {
        this.numberOfClaims = numberOfClaims;
        return this;
    }

    @JsonProperty("valueOfClaims")
    public Integer getValueOfClaims() {
        return valueOfClaims;
    }

    @JsonProperty("valueOfClaims")
    public void setValueOfClaims(Integer valueOfClaims) {
        this.valueOfClaims = valueOfClaims;
    }

    public ClaimInformation withValueOfClaims(Integer valueOfClaims) {
        this.valueOfClaims = valueOfClaims;
        return this;
    }

    @JsonProperty("remarks")
    public String getRemarks() {
        return remarks;
    }

    @JsonProperty("remarks")
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ClaimInformation withRemarks(String remarks) {
        this.remarks = remarks;
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
