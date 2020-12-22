
package com.rsaame.pas.b2b.ws.vo.policy.request;

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
    "liabilityRiskAddress",
    "constructionSubType",
    "propertyMortgageBankName"
})
public class AdditionalLiabilityDetails {

    @JsonProperty("liabilityRiskAddress")
    private List<LiabilityRiskAddres> liabilityRiskAddress = new ArrayList<LiabilityRiskAddres>();
    @JsonProperty("constructionSubType")
    private ConstructionSubType constructionSubType;
    @JsonProperty("propertyMortgageBankName")
    private PropertyMortgageBankName propertyMortgageBankName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("liabilityRiskAddress")
    public List<LiabilityRiskAddres> getLiabilityRiskAddress() {
        return liabilityRiskAddress;
    }

    @JsonProperty("liabilityRiskAddress")
    public void setLiabilityRiskAddress(List<LiabilityRiskAddres> liabilityRiskAddress) {
        this.liabilityRiskAddress = liabilityRiskAddress;
    }

    public AdditionalLiabilityDetails withLiabilityRiskAddress(List<LiabilityRiskAddres> liabilityRiskAddress) {
        this.liabilityRiskAddress = liabilityRiskAddress;
        return this;
    }

    @JsonProperty("constructionSubType")
    public ConstructionSubType getConstructionSubType() {
        return constructionSubType;
    }

    @JsonProperty("constructionSubType")
    public void setConstructionSubType(ConstructionSubType constructionSubType) {
        this.constructionSubType = constructionSubType;
    }

    public AdditionalLiabilityDetails withConstructionSubType(ConstructionSubType constructionSubType) {
        this.constructionSubType = constructionSubType;
        return this;
    }

    @JsonProperty("propertyMortgageBankName")
    public PropertyMortgageBankName getPropertyMortgageBankName() {
        return propertyMortgageBankName;
    }

    @JsonProperty("propertyMortgageBankName")
    public void setPropertyMortgageBankName(PropertyMortgageBankName propertyMortgageBankName) {
        this.propertyMortgageBankName = propertyMortgageBankName;
    }

    public AdditionalLiabilityDetails withPropertyMortgageBankName(PropertyMortgageBankName propertyMortgageBankName) {
        this.propertyMortgageBankName = propertyMortgageBankName;
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
