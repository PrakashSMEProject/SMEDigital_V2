
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
    "cashHandelingEmployeesCount",
    "cashHandelingInsured",
    "nonCashHandelingEmployeesCount",
    "nonCashHandelingInsured"
})
public class UnnamedEmployeesDetail {

    @JsonProperty("cashHandelingEmployeesCount")
    private Integer cashHandelingEmployeesCount;
    @JsonProperty("cashHandelingInsured")
    private Integer cashHandelingInsured;
    @JsonProperty("nonCashHandelingEmployeesCount")
    private Integer nonCashHandelingEmployeesCount;
    @JsonProperty("nonCashHandelingInsured")
    private Integer nonCashHandelingInsured;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cashHandelingEmployeesCount")
    public Integer getCashHandelingEmployeesCount() {
        return cashHandelingEmployeesCount;
    }

    @JsonProperty("cashHandelingEmployeesCount")
    public void setCashHandelingEmployeesCount(Integer cashHandelingEmployeesCount) {
        this.cashHandelingEmployeesCount = cashHandelingEmployeesCount;
    }

    public UnnamedEmployeesDetail withCashHandelingEmployeesCount(Integer cashHandelingEmployeesCount) {
        this.cashHandelingEmployeesCount = cashHandelingEmployeesCount;
        return this;
    }

    @JsonProperty("cashHandelingInsured")
    public Integer getCashHandelingInsured() {
        return cashHandelingInsured;
    }

    @JsonProperty("cashHandelingInsured")
    public void setCashHandelingInsured(Integer cashHandelingInsured) {
        this.cashHandelingInsured = cashHandelingInsured;
    }

    public UnnamedEmployeesDetail withCashHandelingInsured(Integer cashHandelingInsured) {
        this.cashHandelingInsured = cashHandelingInsured;
        return this;
    }

    @JsonProperty("nonCashHandelingEmployeesCount")
    public Integer getNonCashHandelingEmployeesCount() {
        return nonCashHandelingEmployeesCount;
    }

    @JsonProperty("nonCashHandelingEmployeesCount")
    public void setNonCashHandelingEmployeesCount(Integer nonCashHandelingEmployeesCount) {
        this.nonCashHandelingEmployeesCount = nonCashHandelingEmployeesCount;
    }

    public UnnamedEmployeesDetail withNonCashHandelingEmployeesCount(Integer nonCashHandelingEmployeesCount) {
        this.nonCashHandelingEmployeesCount = nonCashHandelingEmployeesCount;
        return this;
    }

    @JsonProperty("nonCashHandelingInsured")
    public Integer getNonCashHandelingInsured() {
        return nonCashHandelingInsured;
    }

    @JsonProperty("nonCashHandelingInsured")
    public void setNonCashHandelingInsured(Integer nonCashHandelingInsured) {
        this.nonCashHandelingInsured = nonCashHandelingInsured;
    }

    public UnnamedEmployeesDetail withNonCashHandelingInsured(Integer nonCashHandelingInsured) {
        this.nonCashHandelingInsured = nonCashHandelingInsured;
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
