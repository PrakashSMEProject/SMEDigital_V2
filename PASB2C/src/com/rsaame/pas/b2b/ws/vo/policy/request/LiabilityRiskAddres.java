
package com.rsaame.pas.b2b.ws.vo.policy.request;

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
    "addressLine1",
    "addressLine2",
    "streetName",
    "city",
    "houseNr",
    "landMark",
    "country",
    "postalCode"
})
public class LiabilityRiskAddres {

    @JsonProperty("addressLine1")
    private AddressLine1 addressLine1;
    @JsonProperty("addressLine2")
    private AddressLine2 addressLine2;
    @JsonProperty("streetName")
    private StreetName streetName;
    @JsonProperty("city")
    private City city;
    @JsonProperty("houseNr")
    private String houseNr;
    @JsonProperty("landMark")
    private String landMark;
    @JsonProperty("country")
    private String country;
    @JsonProperty("postalCode")
    private String postalCode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("addressLine1")
    public AddressLine1 getAddressLine1() {
        return addressLine1;
    }

    @JsonProperty("addressLine1")
    public void setAddressLine1(AddressLine1 addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public LiabilityRiskAddres withAddressLine1(AddressLine1 addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    @JsonProperty("addressLine2")
    public AddressLine2 getAddressLine2() {
        return addressLine2;
    }

    @JsonProperty("addressLine2")
    public void setAddressLine2(AddressLine2 addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public LiabilityRiskAddres withAddressLine2(AddressLine2 addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    @JsonProperty("streetName")
    public StreetName getStreetName() {
        return streetName;
    }

    @JsonProperty("streetName")
    public void setStreetName(StreetName streetName) {
        this.streetName = streetName;
    }

    public LiabilityRiskAddres withStreetName(StreetName streetName) {
        this.streetName = streetName;
        return this;
    }

    @JsonProperty("city")
    public City getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(City city) {
        this.city = city;
    }

    public LiabilityRiskAddres withCity(City city) {
        this.city = city;
        return this;
    }

    @JsonProperty("houseNr")
    public String getHouseNr() {
        return houseNr;
    }

    @JsonProperty("houseNr")
    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public LiabilityRiskAddres withHouseNr(String houseNr) {
        this.houseNr = houseNr;
        return this;
    }

    @JsonProperty("landMark")
    public String getLandMark() {
        return landMark;
    }

    @JsonProperty("landMark")
    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public LiabilityRiskAddres withLandMark(String landMark) {
        this.landMark = landMark;
        return this;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public LiabilityRiskAddres withCountry(String country) {
        this.country = country;
        return this;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LiabilityRiskAddres withPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
