
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
    "namedEmployeesDetail",
    "unnamedEmployeesDetail"
})
public class FidelityGuarantee {

    @JsonProperty("namedEmployeesDetail")
    private List<NamedEmployeesDetail__> namedEmployeesDetail = new ArrayList<NamedEmployeesDetail__>();
    @JsonProperty("unnamedEmployeesDetail")
    private UnnamedEmployeesDetail unnamedEmployeesDetail;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("namedEmployeesDetail")
    public List<NamedEmployeesDetail__> getNamedEmployeesDetail() {
        return namedEmployeesDetail;
    }

    @JsonProperty("namedEmployeesDetail")
    public void setNamedEmployeesDetail(List<NamedEmployeesDetail__> namedEmployeesDetail) {
        this.namedEmployeesDetail = namedEmployeesDetail;
    }

    public FidelityGuarantee withNamedEmployeesDetail(List<NamedEmployeesDetail__> namedEmployeesDetail) {
        this.namedEmployeesDetail = namedEmployeesDetail;
        return this;
    }

    @JsonProperty("unnamedEmployeesDetail")
    public UnnamedEmployeesDetail getUnnamedEmployeesDetail() {
        return unnamedEmployeesDetail;
    }

    @JsonProperty("unnamedEmployeesDetail")
    public void setUnnamedEmployeesDetail(UnnamedEmployeesDetail unnamedEmployeesDetail) {
        this.unnamedEmployeesDetail = unnamedEmployeesDetail;
    }

    public FidelityGuarantee withUnnamedEmployeesDetail(UnnamedEmployeesDetail unnamedEmployeesDetail) {
        this.unnamedEmployeesDetail = unnamedEmployeesDetail;
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
