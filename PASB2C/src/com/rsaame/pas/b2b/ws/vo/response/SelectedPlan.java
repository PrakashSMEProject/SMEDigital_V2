
package com.rsaame.pas.b2b.ws.vo.response;

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

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "product",
    "covers",
    "premium"
})
public class SelectedPlan {

    @JsonProperty("product")
    private Product product;
    @JsonProperty("covers")
    private List<Cover> covers = new ArrayList<Cover>();
    @JsonProperty("premium")
    private Premium premium;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("product")
    public Product getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(Product product) {
        this.product = product;
    }

    public SelectedPlan withProduct(Product product) {
        this.product = product;
        return this;
    }

    @JsonProperty("covers")
    public List<Cover> getCovers() {
        return covers;
    }

    @JsonProperty("covers")
    public void setCovers(List<Cover> covers) {
        this.covers = covers;
    }

    public SelectedPlan withCovers(List<Cover> covers) {
        this.covers = covers;
        return this;
    }

    @JsonProperty("premium")
    public Premium getPremium() {
        return premium;
    }

    @JsonProperty("premium")
    public void setPremium(Premium premium) {
        this.premium = premium;
    }

    public SelectedPlan withPremium(Premium premium) {
        this.premium = premium;
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
