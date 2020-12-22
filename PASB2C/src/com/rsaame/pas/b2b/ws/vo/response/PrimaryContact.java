
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
    "prefixTitle",
    "surName",
    "givenName",
    "professionName",
    "contactMethods"
})
public class PrimaryContact {

    @JsonProperty("prefixTitle")
    private PrefixTitle prefixTitle;
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("givenName")
    private String givenName;
    @JsonProperty("professionName")
    private ProfessionName professionName;
    @JsonProperty("contactMethods")
    private ContactMethods_ contactMethods;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("prefixTitle")
    public PrefixTitle getPrefixTitle() {
        return prefixTitle;
    }

    @JsonProperty("prefixTitle")
    public void setPrefixTitle(PrefixTitle prefixTitle) {
        this.prefixTitle = prefixTitle;
    }

    public PrimaryContact withPrefixTitle(PrefixTitle prefixTitle) {
        this.prefixTitle = prefixTitle;
        return this;
    }

    @JsonProperty("surName")
    public String getSurName() {
        return surName;
    }

    @JsonProperty("surName")
    public void setSurName(String surName) {
        this.surName = surName;
    }

    public PrimaryContact withSurName(String surName) {
        this.surName = surName;
        return this;
    }

    @JsonProperty("givenName")
    public String getGivenName() {
        return givenName;
    }

    @JsonProperty("givenName")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public PrimaryContact withGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    @JsonProperty("professionName")
    public ProfessionName getProfessionName() {
        return professionName;
    }

    @JsonProperty("professionName")
    public void setProfessionName(ProfessionName professionName) {
        this.professionName = professionName;
    }

    public PrimaryContact withProfessionName(ProfessionName professionName) {
        this.professionName = professionName;
        return this;
    }

    @JsonProperty("contactMethods")
    public ContactMethods_ getContactMethods() {
        return contactMethods;
    }

    @JsonProperty("contactMethods")
    public void setContactMethods(ContactMethods_ contactMethods) {
        this.contactMethods = contactMethods;
    }

    public PrimaryContact withContactMethods(ContactMethods_ contactMethods) {
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
