
package com.rsaame.pas.b2b.ws.vo.request;

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
    "emailContact",
    "phoneContacts"
})
public class ContactMethods_ {

    @JsonProperty("emailContact")
    private List<EmailContact_> emailContact = new ArrayList<EmailContact_>();
    @JsonProperty("phoneContacts")
    private List<PhoneContact_> phoneContacts = new ArrayList<PhoneContact_>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("emailContact")
    public List<EmailContact_> getEmailContact() {
        return emailContact;
    }

    @JsonProperty("emailContact")
    public void setEmailContact(List<EmailContact_> emailContact) {
        this.emailContact = emailContact;
    }

    public ContactMethods_ withEmailContact(List<EmailContact_> emailContact) {
        this.emailContact = emailContact;
        return this;
    }

    @JsonProperty("phoneContacts")
    public List<PhoneContact_> getPhoneContacts() {
        return phoneContacts;
    }

    @JsonProperty("phoneContacts")
    public void setPhoneContacts(List<PhoneContact_> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }

    public ContactMethods_ withPhoneContacts(List<PhoneContact_> phoneContacts) {
        this.phoneContacts = phoneContacts;
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
