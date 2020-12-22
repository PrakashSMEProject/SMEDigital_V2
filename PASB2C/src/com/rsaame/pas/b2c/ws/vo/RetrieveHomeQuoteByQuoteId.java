/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author M1044786
 *
 */
public class RetrieveHomeQuoteByQuoteId {
	
	@JsonProperty("QuotationNo")
	private Integer quotationNo;
	@JsonProperty("EndtId")
	private Long endtId;
	@JsonProperty("EndtNo")
	private Long endtNo;
	@JsonProperty("EmailId")
	private String emailId;
	
	public Integer getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(Integer quotationNo) {
		this.quotationNo = quotationNo;
	}
	public Long getEndtId() {
		return endtId;
	}
	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}
	public Long getEndtNo() {
		return endtNo;
	}
	public void setEndtNo(Long endtNo) {
		this.endtNo = endtNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((endtId == null) ? 0 : endtId.hashCode());
		result = prime * result + ((endtNo == null) ? 0 : endtNo.hashCode());
		result = prime * result + ((quotationNo == null) ? 0 : quotationNo.hashCode());
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
		RetrieveHomeQuoteByQuoteId other = (RetrieveHomeQuoteByQuoteId) obj;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (endtId == null) {
			if (other.endtId != null)
				return false;
		} else if (!endtId.equals(other.endtId))
			return false;
		if (endtNo == null) {
			if (other.endtNo != null)
				return false;
		} else if (!endtNo.equals(other.endtNo))
			return false;
		if (quotationNo == null) {
			if (other.quotationNo != null)
				return false;
		} else if (!quotationNo.equals(other.quotationNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RetrieveHomeQuoteByQuoteId [quotationNo=" + quotationNo + ", endtId=" + endtId + ", endtNo=" + endtNo
				+ ", emailId=" + emailId + "]";
	}
}
