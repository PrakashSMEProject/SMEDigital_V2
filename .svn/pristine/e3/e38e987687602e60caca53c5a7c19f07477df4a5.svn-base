/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.rsaame.pas.b2c.wsException.ValidationError;

/**
 * @author M1037404
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreatePolicyResponse {
	
	@JsonProperty("Status")
	private Boolean status;
	@JsonProperty("PolicyNumber")
	private String policyNumber;
	@JsonProperty("EffectiveDate")
	private Date effectiveDate;
	@JsonProperty("ExpiryDate")
	private Date expiryDate;
	@JsonProperty("PolicyId")
	private Long policyId;
	@JsonProperty("EndtId")
	private Long endtId;
	@JsonProperty("PaidAmount")
	private BigDecimal paidAmount;
	@JsonProperty("TransactionRefNo")
	private String transactionRefNo;
	@JsonProperty("Errors")
	private List<ValidationError> errors;
	@JsonProperty("Documents")
	private Documents documents;
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	@JsonDeserialize(using=CustomDateDeSerializer.class)
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getExpiryDate() {
		return expiryDate;
	}
	@JsonDeserialize(using=CustomDateDeSerializer.class)
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public Long getEndtId() {
		return endtId;
	}
	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getTransactionRefNo() {
		return transactionRefNo;
	}
	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}
	public List<ValidationError> getErrors() {
		return errors;
	}
	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
	public Documents getDocuments() {
		return documents;
	}
	public void setDocuments(Documents documents) {
		this.documents = documents;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documents == null) ? 0 : documents.hashCode());
		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((endtId == null) ? 0 : endtId.hashCode());
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((paidAmount == null) ? 0 : paidAmount.hashCode());
		result = prime * result + ((policyId == null) ? 0 : policyId.hashCode());
		result = prime * result + ((policyNumber == null) ? 0 : policyNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((transactionRefNo == null) ? 0 : transactionRefNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreatePolicyResponse other = (CreatePolicyResponse) obj;
		if (documents == null) {
			if (other.documents != null)
				return false;
		} else if (!documents.equals(other.documents))
			return false;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (endtId == null) {
			if (other.endtId != null)
				return false;
		} else if (!endtId.equals(other.endtId))
			return false;
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (paidAmount == null) {
			if (other.paidAmount != null)
				return false;
		} else if (!paidAmount.equals(other.paidAmount))
			return false;
		if (policyId == null) {
			if (other.policyId != null)
				return false;
		} else if (!policyId.equals(other.policyId))
			return false;
		if (policyNumber == null) {
			if (other.policyNumber != null)
				return false;
		} else if (!policyNumber.equals(other.policyNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (transactionRefNo == null) {
			if (other.transactionRefNo != null)
				return false;
		} else if (!transactionRefNo.equals(other.transactionRefNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CreatePolicyResponse [status=" + status + ", policyNumber=" + policyNumber + ", effectiveDate="
				+ effectiveDate + ", expiryDate=" + expiryDate + ", policyId=" + policyId + ", endtId=" + endtId
				+ ", paidAmount=" + paidAmount + ", transactionRefNo=" + transactionRefNo + ", errors=" + errors
				+ ", documents=" + documents + "]";
	}
	
}
