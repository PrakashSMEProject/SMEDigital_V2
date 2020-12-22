
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
    "paymentMode",
    "paymentAmount",
    "paymentRefNumber",
    "cardNo",
    "cardType",
    "merchantRefNo",
    "paymtTransactionDate",
    "customerName",
    "sendMail"
})
public class Payment {

    @JsonProperty("paymentMode")
    private String paymentMode;
    @JsonProperty("paymentAmount")
    private PaymentAmount paymentAmount;
    @JsonProperty("paymentRefNumber")
    private String paymentRefNumber;
    @JsonProperty("cardNo")
    private String cardNo;
    @JsonProperty("cardType")
    private String cardType;
    @JsonProperty("merchantRefNo")
    private String merchantRefNo;
    @JsonProperty("paymtTransactionDate")
    private String paymtTransactionDate;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("sendMail")
    private String policyEmailAttachment;

	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("paymentMode")
    public String getPaymentMode() {
        return paymentMode;
    }

    @JsonProperty("paymentMode")
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Payment withPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    @JsonProperty("paymentAmount")
    public PaymentAmount getPaymentAmount() {
        return paymentAmount;
    }

    @JsonProperty("paymentAmount")
    public void setPaymentAmount(PaymentAmount paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Payment withPaymentAmount(PaymentAmount paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    @JsonProperty("paymentRefNumber")
    public String getPaymentRefNumber() {
        return paymentRefNumber;
    }

    @JsonProperty("paymentRefNumber")
    public void setPaymentRefNumber(String paymentRefNumber) {
        this.paymentRefNumber = paymentRefNumber;
    }

    public Payment withPaymentRefNumber(String paymentRefNumber) {
        this.paymentRefNumber = paymentRefNumber;
        return this;
    }

    @JsonProperty("cardNo")
    public String getCardNo() {
		return cardNo;
	}

    @JsonProperty("cardNo")
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

    @JsonProperty("cardType")
	public String getCardType() {
		return cardType;
	}

    @JsonProperty("cardType")
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

    @JsonProperty("merchantRefNo")
    public String getMerchantRefNo() {
		return merchantRefNo;
	}

    @JsonProperty("merchantRefNo")
	public void setMerchantRefNo(String merchantRefNo) {
		this.merchantRefNo = merchantRefNo;
	}

	@JsonProperty("paymtTransactionDate")
	public String getPaymtTransactionDate() {
		return paymtTransactionDate;
	}

    @JsonProperty("paymtTransactionDate")
	public void setPaymtTransactionDate(String paymtTransactionDate) {
		this.paymtTransactionDate = paymtTransactionDate;
	}

    @JsonProperty("customerName")
	public String getCustomerName() {
		return customerName;
	}

    @JsonProperty("customerName")
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
    
    @JsonProperty("sendMail")
    public String getPolicyEmailAttachment() {
		return policyEmailAttachment;
	}

    @JsonProperty("sendMail")
	public void setPolicyEmailAttachment(String policyEmailAttachment) {
		this.policyEmailAttachment = policyEmailAttachment;
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
