package com.rsaame.pas.b2c.ws.vo;

public class QuoteDetails {
	private Integer quotationNo;
	private Integer endtId;
	private Integer endtNo;
	private Integer policyId;
	public Integer getQuotationNo() {
		return quotationNo;
	}
	public void setQuotationNo(Integer quotationNo) {
		this.quotationNo = quotationNo;
	}
	public Integer getEndtId() {
		return endtId;
	}
	public void setEndtId(Integer endtId) {
		this.endtId = endtId;
	}
	public Integer getEndtNo() {
		return endtNo;
	}
	public void setEndtNo(Integer endtNo) {
		this.endtNo = endtNo;
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endtId == null) ? 0 : endtId.hashCode());
		result = prime * result + ((endtNo == null) ? 0 : endtNo.hashCode());
		result = prime * result + ((policyId == null) ? 0 : policyId.hashCode());
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
		QuoteDetails other = (QuoteDetails) obj;
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
		if (policyId == null) {
			if (other.policyId != null)
				return false;
		} else if (!policyId.equals(other.policyId))
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
		return "QuoteDetails [quotationNo=" + quotationNo + ", endtId=" + endtId + ", endtNo=" + endtNo + ", policyId="
				+ policyId + "]";
	}
	
	
	
}
