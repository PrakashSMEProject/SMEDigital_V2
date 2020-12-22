/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1037404
 *
 */
public class OnDemandBatchVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	private String clazz;
	private String policyNo;
	private Date transactionFrom;
	private Date transactionTo;
	
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public Date getTransactionFrom() {
		return transactionFrom;
	}
	public void setTransactionFrom(Date transactionFrom) {
		this.transactionFrom = transactionFrom;
	}
	public Date getTransactionTo() {
		return transactionTo;
	}
	public void setTransactionTo(Date transactionTo) {
		this.transactionTo = transactionTo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((transactionFrom == null) ? 0 : transactionFrom.hashCode());
		result = prime * result + ((transactionTo == null) ? 0 : transactionTo.hashCode());
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
		OnDemandBatchVO other = (OnDemandBatchVO) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (transactionFrom == null) {
			if (other.transactionFrom != null)
				return false;
		} else if (!transactionFrom.equals(other.transactionFrom))
			return false;
		if (transactionTo == null) {
			if (other.transactionTo != null)
				return false;
		} else if (!transactionTo.equals(other.transactionTo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OnDemandBatchVO [clazz=" + clazz + ", policyNo=" + policyNo + ", transactionFrom=" + transactionFrom
				+ ", transactionTo=" + transactionTo + "]";
	}
	
}
