
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
    "id",
    "name",
    "isIncludedInPremium",
    "deductible",
    "coverPremium",
    "sumInsured"
})
public class Cover {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("isIncludedInPremium")
    private String isIncludedInPremium;
    @JsonProperty("deductible")
    private String deductible;
    @JsonProperty("coverPremium")
    private CoverPremium coverPremium;
    @JsonProperty("sumInsured")
    private SumInsured sumInsured;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Cover withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("deductible")
    public String getDeductible() {
		return deductible;
	}

    @JsonProperty("deductible")
	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Cover withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("isIncludedInPremium")
    public String getIsIncludedInPremium() {
        return isIncludedInPremium;
    }

    @JsonProperty("isIncludedInPremium")
    public void setIsIncludedInPremium(String isIncludedInPremium) {
        this.isIncludedInPremium = isIncludedInPremium;
    }

    public Cover withIsIncludedInPremium(String isIncludedInPremium) {
        this.isIncludedInPremium = isIncludedInPremium;
        return this;
    }

    @JsonProperty("coverPremium")
    public CoverPremium getCoverPremium() {
        return coverPremium;
    }

    @JsonProperty("coverPremium")
    public void setCoverPremium(CoverPremium coverPremium) {
        this.coverPremium = coverPremium;
    }

    public Cover withCoverPremium(CoverPremium coverPremium) {
        this.coverPremium = coverPremium;
        return this;
    }

    @JsonProperty("sumInsured")
    public SumInsured getSumInsured() {
        return sumInsured;
    }

    @JsonProperty("sumInsured")
    public void setSumInsured(SumInsured sumInsured) {
        this.sumInsured = sumInsured;
    }

    public Cover withSumInsured(SumInsured sumInsured) {
        this.sumInsured = sumInsured;
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
