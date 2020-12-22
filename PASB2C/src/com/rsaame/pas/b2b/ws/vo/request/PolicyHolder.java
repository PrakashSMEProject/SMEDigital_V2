
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
    "primaryContact",
    "company",
    "contactMethods"
})
public class PolicyHolder {

    @JsonProperty("primaryContact")
    private PrimaryContact primaryContact;
    @JsonProperty("company")
    private Company company;
    @JsonProperty("contactMethods")
    private ContactMethods contactMethods;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("primaryContact")
    public PrimaryContact getPrimaryContact() {
        return primaryContact;
    }

    @JsonProperty("primaryContact")
    public void setPrimaryContact(PrimaryContact primaryContact) {
        this.primaryContact = primaryContact;
    }

    public PolicyHolder withPrimaryContact(PrimaryContact primaryContact) {
        this.primaryContact = primaryContact;
        return this;
    }

    @JsonProperty("company")
    public Company getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(Company company) {
        this.company = company;
    }

    public PolicyHolder withCompany(Company company) {
        this.company = company;
        return this;
    }

    @JsonProperty("contactMethods")
    public ContactMethods getContactMethods() {
        return contactMethods;
    }

    @JsonProperty("contactMethods")
    public void setContactMethods(ContactMethods contactMethods) {
        this.contactMethods = contactMethods;
    }

    public PolicyHolder withContactMethods(ContactMethods contactMethods) {
        this.contactMethods = contactMethods;
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
