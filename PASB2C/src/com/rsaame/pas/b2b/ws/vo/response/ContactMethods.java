
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
    "postMailContact",
    "phoneContacts",
    "emailContact"
})
public class ContactMethods {

    @JsonProperty("postMailContact")
    private List<PostMailContact> postMailContact = new ArrayList<PostMailContact>();
    @JsonProperty("phoneContacts")
    private List<PhoneContact> phoneContacts = new ArrayList<PhoneContact>();
    @JsonProperty("emailContact")
    private List<EmailContact> emailContact = new ArrayList<EmailContact>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("postMailContact")
    public List<PostMailContact> getPostMailContact() {
        return postMailContact;
    }

    @JsonProperty("postMailContact")
    public void setPostMailContact(List<PostMailContact> postMailContact) {
        this.postMailContact = postMailContact;
    }

    public ContactMethods withPostMailContact(List<PostMailContact> postMailContact) {
        this.postMailContact = postMailContact;
        return this;
    }

    @JsonProperty("phoneContacts")
    public List<PhoneContact> getPhoneContacts() {
        return phoneContacts;
    }

    @JsonProperty("phoneContacts")
    public void setPhoneContacts(List<PhoneContact> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }

    public ContactMethods withPhoneContacts(List<PhoneContact> phoneContacts) {
        this.phoneContacts = phoneContacts;
        return this;
    }

    @JsonProperty("emailContact")
    public List<EmailContact> getEmailContact() {
        return emailContact;
    }

    @JsonProperty("emailContact")
    public void setEmailContact(List<EmailContact> emailContact) {
        this.emailContact = emailContact;
    }

    public ContactMethods withEmailContact(List<EmailContact> emailContact) {
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
