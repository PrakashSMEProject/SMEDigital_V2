
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
    "grossPremium",
    "premium",
    "vatOnPremium"
})
public class Premium {

    @JsonProperty("grossPremium")
    private GrossPremium grossPremium;
    @JsonProperty("premium")
    private Premium_ premium;
    @JsonProperty("vatOnPremium")
    private VatOnPremium vatOnPremium;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("grossPremium")
    public GrossPremium getGrossPremium() {
        return grossPremium;
    }

    @JsonProperty("grossPremium")
    public void setGrossPremium(GrossPremium grossPremium) {
        this.grossPremium = grossPremium;
    }

    public Premium withGrossPremium(GrossPremium grossPremium) {
        this.grossPremium = grossPremium;
        return this;
    }

    @JsonProperty("premium")
    public Premium_ getPremium() {
        return premium;
    }

    @JsonProperty("premium")
    public void setPremium(Premium_ premium) {
        this.premium = premium;
    }

    public Premium withPremium(Premium_ premium) {
        this.premium = premium;
        return this;
    }

    @JsonProperty("vatOnPremium")
    public VatOnPremium getVatOnPremium() {
        return vatOnPremium;
    }

    @JsonProperty("vatOnPremium")
    public void setVatOnPremium(VatOnPremium vatOnPremium) {
        this.vatOnPremium = vatOnPremium;
    }

    public Premium withVatOnPremium(VatOnPremium vatOnPremium) {
        this.vatOnPremium = vatOnPremium;
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
