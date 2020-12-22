package com.rsaame.pas.dao.model;

// Generated Mar 14, 2012 11:33:03 AM by Hibernate Tools 3.4.0.CR1

/**
 * TTrnTempPasReferralId generated by hbm2java
 */
public class TTrnPasReferralDetailsId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long polLinkingId;
	private Long refPolicyId;
	private Long locationId;
	private String refField;
	private String refValue;
	private Short secId;
	private Long refEndId;

	public TTrnPasReferralDetailsId() {
	}

	/**
	 * @return the polLinkingId
	 */
	public Long getPolLinkingId() {
		return polLinkingId;
	}

	/**
	 * @param polLinkingId
	 *            the polLinkingId to set
	 */
	public void setPolLinkingId(Long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}

	/**
	 * @return the refPolicyId
	 */
	public Long getRefPolicyId() {
		return refPolicyId;
	}

	/**
	 * @param refPolicyId
	 *            the refPolicyId to set
	 */
	public void setRefPolicyId(Long refPolicyId) {
		this.refPolicyId = refPolicyId;
	}

	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the refField
	 */
	public String getRefField() {
		return refField;
	}

	/**
	 * @param refField
	 *            the refField to set
	 */
	public void setRefField(String refField) {
		this.refField = refField;
	}

	/**
	 * @return the refValue
	 */
	public String getRefValue() {
		return refValue;
	}

	/**
	 * @param refValue
	 *            the refValue to set
	 */
	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	/**
	 * @return the secId
	 */
	public Short getSecId() {
		return secId;
	}

	/**
	 * @param secId
	 *            the secId to set
	 */
	public void setSecId(Short secId) {
		this.secId = secId;
	}

	/**
	 * @return the refEndId
	 */
	public Long getRefEndId() {
		return refEndId;
	}

	/**
	 * @param refEndId
	 *            the refEndId to set
	 */
	public void setRefEndId(Long refEndId) {
		this.refEndId = refEndId;
	}

	public TTrnPasReferralDetailsId(Long polLinkingId, Short secId,
			Long locationId, String refField, String refValue,
			Long refPolicyId, Long refEndId, String refStatus) {
		this.polLinkingId = polLinkingId;
		this.secId = secId;
		this.locationId = locationId;
		this.refField = refField;
		this.refValue = refValue;
		this.refPolicyId = refPolicyId;
		this.refEndId = refEndId;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( locationId == null ) ? 0 : locationId.hashCode() );
		result = prime * result + ( ( polLinkingId == null ) ? 0 : polLinkingId.hashCode() );
		result = prime * result + ( ( refEndId == null ) ? 0 : refEndId.hashCode() );
		result = prime * result + ( ( refField == null ) ? 0 : refField.hashCode() );
		result = prime * result + ( ( refPolicyId == null ) ? 0 : refPolicyId.hashCode() );
		result = prime * result + ( ( refValue == null ) ? 0 : refValue.hashCode() );
		result = prime * result + ( ( secId == null ) ? 0 : secId.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		TTrnPasReferralDetailsId other = (TTrnPasReferralDetailsId) obj;
		if( locationId == null ){
			if( other.locationId != null ) return false;
		}
		else if( !locationId.equals( other.locationId ) ) return false;
		if( polLinkingId == null ){
			if( other.polLinkingId != null ) return false;
		}
		else if( !polLinkingId.equals( other.polLinkingId ) ) return false;
		if( refEndId == null ){
			if( other.refEndId != null ) return false;
		}
		else if( !refEndId.equals( other.refEndId ) ) return false;
		if( refField == null ){
			if( other.refField != null ) return false;
		}
		else if( !refField.equals( other.refField ) ) return false;
		if( refPolicyId == null ){
			if( other.refPolicyId != null ) return false;
		}
		else if( !refPolicyId.equals( other.refPolicyId ) ) return false;
		if( refValue == null ){
			if( other.refValue != null ) return false;
		}
		else if( !refValue.equals( other.refValue ) ) return false;
		if( secId == null ){
			if( other.secId != null ) return false;
		}
		else if( !secId.equals( other.secId ) ) return false;
		return true;
	}

}
