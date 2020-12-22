/**
 * 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1019193
 *
 */
public class SmsVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long smsID;	
	private String smsMode;
	private Short smsLevel;
	private Short smsFrequency;
	private String smsEngBody;
	private String smsArabicBody;
	private String smsStatus;
	
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if( "smsID".equals( fieldName ) ) fieldValue = getSmsID();	
		if( "smsMode".equals( fieldName ) ) fieldValue = getSmsMode();
		if( "smsLevel".equals( fieldName ) ) fieldValue = getSmsLevel();
		if( "smsFrequency".equals( fieldName ) ) fieldValue = getSmsFrequency();
		if( "smsEngBody".equals( fieldName ) ) fieldValue = getSmsEngBody();
		if( "smsArabicBody".equals( fieldName ) ) fieldValue = getSmsArabicBody();
		if( "smsStatus".equals( fieldName ) ) fieldValue = getSmsStatus();
		
		return fieldValue;
	}

	/**
	 * 
	 */
	public SmsVO() {
		
	}

	/**
	 * @param smsID
	 * @param smsMode
	 * @param smsLevel
	 * @param smsFrequency
	 * @param smsEngBody
	 * @param smsArabicBody
	 * @param smsStatus
	 */
	public SmsVO(Long smsID, String smsMode, Short smsLevel,
			Short smsFrequency, String smsEngBody, String smsArabicBody,
			String smsStatus) {
		super();
		this.smsID = smsID;
		this.smsMode = smsMode;
		this.smsLevel = smsLevel;
		this.smsFrequency = smsFrequency;
		this.smsEngBody = smsEngBody;
		this.smsArabicBody = smsArabicBody;
		this.smsStatus = smsStatus;
	}



	/**
	 * @return the smsID
	 */
	public Long getSmsID() {
		return smsID;
	}


	/**
	 * @param smsID the smsID to set
	 */
	public void setSmsID(Long smsID) {
		this.smsID = smsID;
	}

	/**
	 * @return the smsMode
	 */
	public String getSmsMode() {
		return smsMode;
	}


	/**
	 * @param smsMode the smsMode to set
	 */
	public void setSmsMode(String smsMode) {
		this.smsMode = smsMode;
	}


	/**
	 * @return the smsLevel
	 */
	public Short getSmsLevel() {
		return smsLevel;
	}


	/**
	 * @param smsLevel the smsLevel to set
	 */
	public void setSmsLevel(Short smsLevel) {
		this.smsLevel = smsLevel;
	}


	/**
	 * @return the smsFrequency
	 */
	public Short getSmsFrequency() {
		return smsFrequency;
	}


	/**
	 * @param smsFrequency the smsFrequency to set
	 */
	public void setSmsFrequency(Short smsFrequency) {
		this.smsFrequency = smsFrequency;
	}


	/**
	 * @return the smsEngBody
	 */
	public String getSmsEngBody() {
		return smsEngBody;
	}


	/**
	 * @param smsEngBody the smsEngBody to set
	 */
	public void setSmsEngBody(String smsEngBody) {
		this.smsEngBody = smsEngBody;
	}


	/**
	 * @return the smsArabicBody
	 */
	public String getSmsArabicBody() {
		return smsArabicBody;
	}


	/**
	 * @param smsArabicBody the smsArabicBody to set
	 */
	public void setSmsArabicBody(String smsArabicBody) {
		this.smsArabicBody = smsArabicBody;
	}


	/**
	 * @return the smsStatus
	 */
	public String getSmsStatus() {
		return smsStatus;
	}


	/**
	 * @param smsStatus the smsStatus to set
	 */
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SmsVO [smsID=" + smsID + ", smsMode=" + smsMode + ", smsLevel="
				+ smsLevel + ", smsFrequency=" + smsFrequency + ", smsEngBody="
				+ smsEngBody + ", smsArabicBody=" + smsArabicBody
				+ ", smsStatus=" + smsStatus + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((smsID == null) ? 0 : smsID.hashCode());
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
		SmsVO other = (SmsVO) obj;
		if (smsID == null) {
			if (other.smsID != null)
				return false;
		} else if (!smsID.equals(other.smsID))
			return false;
		return true;
	}
	
	

}
