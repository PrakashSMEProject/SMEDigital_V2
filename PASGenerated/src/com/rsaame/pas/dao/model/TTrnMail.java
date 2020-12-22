/**
 * 
 */
package com.rsaame.pas.dao.model;

import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1019193
 *Pojo class created to fetch/save data from/into T_TRN_MAIL 
 */
public class TTrnMail implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long mailId;
	private String mailType;
	private String recipientMailId;
	private String transactionId;
	private Byte status;
	private Date sentDate;
	private Short malClassCode;
	
	/**
	 * 
	 */
	public TTrnMail() {
	
	}

	/**
	 * @param mailId
	 * @param mailType
	 * @param recipientMailId
	 * @param transactionId
	 * @param status
	 */
	public TTrnMail(Long mailId, String mailType, String recipientMailId,
			String transactionId, Byte status) {
		this.mailId = mailId;
		this.mailType = mailType;
		this.recipientMailId = recipientMailId;
		this.transactionId = transactionId;
		this.status = status;
	}

	/**
	 * @return the mailId
	 */
	public Long getMailId() {
		return mailId;
	}

	/**
	 * @param mailId the mailId to set
	 */
	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	/**
	 * @return the mailType
	 */
	public String getMailType() {
		return mailType;
	}

	/**
	 * @param mailType the mailType to set
	 */
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	/**
	 * @return the recipientMailId
	 */
	public String getRecipientMailId() {
		return recipientMailId;
	}

	/**
	 * @param recipientMailId the recipientMailId to set
	 */
	public void setRecipientMailId(String recipientMailId) {
		this.recipientMailId = recipientMailId;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the status
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mailId == null) ? 0 : mailId.hashCode());
		result = prime * result
				+ ((transactionId == null) ? 0 : transactionId.hashCode());
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
		TTrnMail other = (TTrnMail) obj;
		if (mailId == null) {
			if (other.mailId != null)
				return false;
		} else if (!mailId.equals(other.mailId))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public short getMalClassCode() {
		if(Utils.isEmpty(malClassCode)){
			return 0;
		}
		return this.malClassCode;
	}

	public void setMalClassCode(Short malClassCode) {
		this.malClassCode = malClassCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TTrnMail [mailId=" + mailId + ", mailType=" + mailType
				+ ", recipientMailId=" + recipientMailId + ", transactionId="
				+ transactionId + ", status=" + status + "]";
	}
	
	
	
	
	
}
