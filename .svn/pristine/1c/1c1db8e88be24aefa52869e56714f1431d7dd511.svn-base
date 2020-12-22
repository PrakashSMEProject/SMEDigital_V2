
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
    "phoneType",
    "internationalFullNumber"
})
public class PhoneContact_ {

    @JsonProperty("phoneType")
    private String phoneType;
    @JsonProperty("internationalFullNumber")
    private String internationalFullNumber;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("phoneType")
    public String getPhoneType() {
        return phoneType;
    }

    @JsonProperty("phoneType")
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public PhoneContact_ withPhoneType(String phoneType) {
        this.phoneType = phoneType;
        return this;
    }

    @JsonProperty("internationalFullNumber")
    public String getInternationalFullNumber() {
        return internationalFullNumber;
    }

    @JsonProperty("internationalFullNumber")
    public void setInternationalFullNumber(String internationalFullNumber) {
        this.internationalFullNumber = internationalFullNumber;
    }

    public PhoneContact_ withInternationalFullNumber(String internationalFullNumber) {
        this.internationalFullNumber = internationalFullNumber;
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
