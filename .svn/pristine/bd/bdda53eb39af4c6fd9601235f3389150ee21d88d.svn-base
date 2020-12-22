
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
    "phoneContacts",
    "emailContact"
})
public class ContactMethods__ {

    @JsonProperty("phoneContacts")
    private List<PhoneContact__> phoneContacts = new ArrayList<PhoneContact__>();
    @JsonProperty("emailContact")
    private List<EmailContact__> emailContact = new ArrayList<EmailContact__>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("phoneContacts")
    public List<PhoneContact__> getPhoneContacts() {
        return phoneContacts;
    }

    @JsonProperty("phoneContacts")
    public void setPhoneContacts(List<PhoneContact__> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }

    public ContactMethods__ withPhoneContacts(List<PhoneContact__> phoneContacts) {
        this.phoneContacts = phoneContacts;
        return this;
    }

    @JsonProperty("emailContact")
    public List<EmailContact__> getEmailContact() {
        return emailContact;
    }

    @JsonProperty("emailContact")
    public void setEmailContact(List<EmailContact__> emailContact) {
        this.emailContact = emailContact;
    }

    public ContactMethods__ withEmailContact(List<EmailContact__> emailContact) {
        this.emailContact = emailContact;
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
