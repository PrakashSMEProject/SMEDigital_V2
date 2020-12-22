
package com.rsaame.pas.b2b.ws.vo;
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

import com.rsaame.pas.b2b.ws.vo.policy.request.AdditionalLiabilityDetails;
import com.rsaame.pas.b2b.ws.vo.policy.request.Branch;
import com.rsaame.pas.b2b.ws.vo.policy.request.Clause;
import com.rsaame.pas.b2b.ws.vo.policy.request.CompanyRevenue;
import com.rsaame.pas.b2b.ws.vo.policy.request.Occupation;
import com.rsaame.pas.b2b.ws.vo.policy.request.Payment;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "quoteId",
    "companyRegistrationNumber",
    "accountExecutiveId",
    "companyRevenue",
    "companyCountryOfIncorporation",
    "additionalLiabilityDetails",
    "branch",
    "occupation",
    "payment",
    "clauses"
})
public class CreateSBSPolicyRequest {

    @JsonProperty("quoteId")
    private String quoteId;
    @JsonProperty("companyRegistrationNumber")
    private String companyRegistrationNumber;
    @JsonProperty("accountExecutiveId")
    private String accountExecutiveId;
    @JsonProperty("companyRevenue")
    private CompanyRevenue companyRevenue;
    @JsonProperty("companyCountryOfIncorporation")
    private String companyCountryOfIncorporation;
    @JsonProperty("additionalLiabilityDetails")
    private AdditionalLiabilityDetails additionalLiabilityDetails;
    @JsonProperty("branch")
    private Branch branch;
    @JsonProperty("occupation")
    private Occupation occupation;
    @JsonProperty("payment")
    private Payment payment;
    @JsonProperty("clauses")
    private List<Clause> clauses = new ArrayList<Clause>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("quoteId")
    public String getQuoteId() {
        return quoteId;
    }

    @JsonProperty("quoteId")
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public CreateSBSPolicyRequest withQuoteId(String quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    @JsonProperty("companyRegistrationNumber")
    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    @JsonProperty("companyRegistrationNumber")
    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public CreateSBSPolicyRequest withCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
        return this;
    }

    @JsonProperty("accountExecutiveId")
    public String getAccountExecutiveId() {
        return accountExecutiveId;
    }

    @JsonProperty("accountExecutiveId")
    public void setAccountExecutiveId(String accountExecutiveId) {
        this.accountExecutiveId = accountExecutiveId;
    }

    public CreateSBSPolicyRequest withAccountExecutiveId(String accountExecutiveId) {
        this.accountExecutiveId = accountExecutiveId;
        return this;
    }

    @JsonProperty("companyRevenue")
    public CompanyRevenue getCompanyRevenue() {
        return companyRevenue;
    }

    @JsonProperty("companyRevenue")
    public void setCompanyRevenue(CompanyRevenue companyRevenue) {
        this.companyRevenue = companyRevenue;
    }

    public CreateSBSPolicyRequest withCompanyRevenue(CompanyRevenue companyRevenue) {
        this.companyRevenue = companyRevenue;
        return this;
    }

    @JsonProperty("companyCountryOfIncorporation")
    public String getCompanyCountryOfIncorporation() {
        return companyCountryOfIncorporation;
    }

    @JsonProperty("companyCountryOfIncorporation")
    public void setCompanyCountryOfIncorporation(String companyCountryOfIncorporation) {
        this.companyCountryOfIncorporation = companyCountryOfIncorporation;
    }

    public CreateSBSPolicyRequest withCompanyCountryOfIncorporation(String companyCountryOfIncorporation) {
        this.companyCountryOfIncorporation = companyCountryOfIncorporation;
        return this;
    }

    @JsonProperty("additionalLiabilityDetails")
    public AdditionalLiabilityDetails getAdditionalLiabilityDetails() {
        return additionalLiabilityDetails;
    }

    @JsonProperty("additionalLiabilityDetails")
    public void setAdditionalLiabilityDetails(AdditionalLiabilityDetails additionalLiabilityDetails) {
        this.additionalLiabilityDetails = additionalLiabilityDetails;
    }

    public CreateSBSPolicyRequest withAdditionalLiabilityDetails(AdditionalLiabilityDetails additionalLiabilityDetails) {
        this.additionalLiabilityDetails = additionalLiabilityDetails;
        return this;
    }

    @JsonProperty("branch")
    public Branch getBranch() {
        return branch;
    }

    @JsonProperty("branch")
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public CreateSBSPolicyRequest withBranch(Branch branch) {
        this.branch = branch;
        return this;
    }

    @JsonProperty("occupation")
    public Occupation getOccupation() {
        return occupation;
    }

    @JsonProperty("occupation")
    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public CreateSBSPolicyRequest withOccupation(Occupation occupation) {
        this.occupation = occupation;
        return this;
    }

    @JsonProperty("payment")
    public Payment getPayment() {
        return payment;
    }

    @JsonProperty("payment")
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public CreateSBSPolicyRequest withPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    @JsonProperty("clauses")
    public List<Clause> getClauses() {
        return clauses;
    }

    @JsonProperty("clauses")
    public void setClauses(List<Clause> clauses) {
        this.clauses = clauses;
    }

    public CreateSBSPolicyRequest withClauses(List<Clause> clauses) {
        this.clauses = clauses;
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
