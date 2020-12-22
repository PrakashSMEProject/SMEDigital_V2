
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
    "name",
    "numberOfEmployee",
    "companyVATRegistrationNumber",
    "natureOfBusiness",
    "revenue"
})
public class Company {

    @JsonProperty("name")
    private String name;
    @JsonProperty("numberOfEmployee")
    private Integer numberOfEmployee;
    @JsonProperty("companyVATRegistrationNumber")
    private String companyVATRegistrationNumber;
    @JsonProperty("natureOfBusiness")
    private NatureOfBusiness natureOfBusiness;
    @JsonProperty("revenue")
    private Revenue revenue;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Company withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("numberOfEmployee")
    public Integer getNumberOfEmployee() {
        return numberOfEmployee;
    }

    @JsonProperty("numberOfEmployee")
    public void setNumberOfEmployee(Integer numberOfEmployee) {
        this.numberOfEmployee = numberOfEmployee;
    }

    public Company withNumberOfEmployee(Integer numberOfEmployee) {
        this.numberOfEmployee = numberOfEmployee;
        return this;
    }

    @JsonProperty("companyVATRegistrationNumber")
    public String getCompanyVATRegistrationNumber() {
        return companyVATRegistrationNumber;
    }

    @JsonProperty("companyVATRegistrationNumber")
    public void setCompanyVATRegistrationNumber(String companyVATRegistrationNumber) {
        this.companyVATRegistrationNumber = companyVATRegistrationNumber;
    }

    public Company withCompanyVATRegistrationNumber(String companyVATRegistrationNumber) {
        this.companyVATRegistrationNumber = companyVATRegistrationNumber;
        return this;
    }

    @JsonProperty("natureOfBusiness")
    public NatureOfBusiness getNatureOfBusiness() {
        return natureOfBusiness;
    }

    @JsonProperty("natureOfBusiness")
    public void setNatureOfBusiness(NatureOfBusiness natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public Company withNatureOfBusiness(NatureOfBusiness natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
        return this;
    }

    @JsonProperty("revenue")
    public Revenue getRevenue() {
        return revenue;
    }

    @JsonProperty("revenue")
    public void setRevenue(Revenue revenue) {
        this.revenue = revenue;
    }

    public Company withRevenue(Revenue revenue) {
        this.revenue = revenue;
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
