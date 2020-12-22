/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1044786
 *
 */
public class RetrievePolicyByPolicyNo {
	
	@JsonProperty("PolicyNo")
	private Integer policyNo;
	@JsonProperty("EmailId")
	private String emailId;
	@JsonProperty("Dob")
	private Date dob;
	@JsonProperty("Documents")
	private Documents documents;
	public Integer getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(Integer policyNo) {
		this.policyNo = policyNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getDob() {
		return dob;
	}
	@JsonDeserialize(using=CustomDateDeSerializer.class)
	public void setDob(Date dob) {
		this.dob = dob;
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
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((documents == null) ? 0 : documents.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
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
		RetrievePolicyByPolicyNo other = (RetrievePolicyByPolicyNo) obj;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (documents == null) {
			if (other.documents != null)
				return false;
		} else if (!documents.equals(other.documents))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RetrievePolicyByPolicyNo [policyNo=" + policyNo + ", emailId=" + emailId + ", dob=" + dob
				+ ", documents=" + documents + "]";
	}
	
	
}
