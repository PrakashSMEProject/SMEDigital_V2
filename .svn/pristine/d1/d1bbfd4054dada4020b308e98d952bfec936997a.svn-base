
package com.rsaame.pas.b2b.ws.vo;

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
import com.rsaame.pas.b2b.ws.vo.request.*;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "policySchedule",
    "liabilityInformation",
    "policyHolder",
    "agent"
})
public class CreateSBSQuoteRequest {

    @JsonProperty("policySchedule")
    private PolicySchedule policySchedule;
    @JsonProperty("liabilityInformation")
    private LiabilityInformation liabilityInformation;
    @JsonProperty("policyHolder")
    private PolicyHolder policyHolder;
    @JsonProperty("agent")
    private Agent agent;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("policySchedule")
    public PolicySchedule getPolicySchedule() {
        return policySchedule;
    }

    @JsonProperty("policySchedule")
    public void setPolicySchedule(PolicySchedule policySchedule) {
        this.policySchedule = policySchedule;
    }

    public CreateSBSQuoteRequest withPolicySchedule(PolicySchedule policySchedule) {
        this.policySchedule = policySchedule;
        return this;
    }

    @JsonProperty("liabilityInformation")
    public LiabilityInformation getLiabilityInformation() {
        return liabilityInformation;
    }

    @JsonProperty("liabilityInformation")
    public void setLiabilityInformation(LiabilityInformation liabilityInformation) {
        this.liabilityInformation = liabilityInformation;
    }

    public CreateSBSQuoteRequest withLiabilityInformation(LiabilityInformation liabilityInformation) {
        this.liabilityInformation = liabilityInformation;
        return this;
    }

    @JsonProperty("policyHolder")
    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    @JsonProperty("policyHolder")
    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public CreateSBSQuoteRequest withPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
        return this;
    }

    @JsonProperty("agent")
    public Agent getAgent() {
        return agent;
    }

    @JsonProperty("agent")
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public CreateSBSQuoteRequest withAgent(Agent agent) {
        this.agent = agent;
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
