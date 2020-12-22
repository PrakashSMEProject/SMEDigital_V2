
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
    "prefixTitle",
    "contactMethods",
    "surName",
    "givenName"
})
public class Agent {

    @JsonProperty("prefixTitle")
    private PrefixTitle_ prefixTitle;
    @JsonProperty("contactMethods")
    private ContactMethods_ contactMethods;
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("givenName")
    private String givenName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("prefixTitle")
    public PrefixTitle_ getPrefixTitle() {
        return prefixTitle;
    }

    @JsonProperty("prefixTitle")
    public void setPrefixTitle(PrefixTitle_ prefixTitle) {
        this.prefixTitle = prefixTitle;
    }

    public Agent withPrefixTitle(PrefixTitle_ prefixTitle) {
        this.prefixTitle = prefixTitle;
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

    public Agent withContactMethods(ContactMethods_ contactMethods) {
        this.contactMethods = contactMethods;
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

    public Agent withSurName(String surName) {
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

    public Agent withGivenName(String givenName) {
        this.givenName = givenName;
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
