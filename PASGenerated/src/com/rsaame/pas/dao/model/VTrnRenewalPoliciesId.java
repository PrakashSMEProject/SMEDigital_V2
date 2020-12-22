/**
 * 
 */
package com.rsaame.pas.dao.model;

/**
 * @author m1019193
 *
 */
public class VTrnRenewalPoliciesId implements java.io.Serializable {

	private long policyId;

	public VTrnRenewalPoliciesId() {
		
	}

	public VTrnRenewalPoliciesId(long policyId) {		
		this.policyId = policyId;
	}

	/**
	 * @return the policyId
	 */
	public long getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (policyId ^ (policyId >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VTrnRenewalPoliciesId other = (VTrnRenewalPoliciesId) obj;
		if (policyId != other.policyId)
			return false;
		return true;
	}
	
	
	
}
