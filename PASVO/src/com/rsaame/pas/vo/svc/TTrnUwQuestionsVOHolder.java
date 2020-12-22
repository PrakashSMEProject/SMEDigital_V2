package com.rsaame.pas.vo.svc;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * 
 * @author M1020859
 * @since Phase 3
 */
public class TTrnUwQuestionsVOHolder extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -461642709047049183L;

	private long uqtPolPolicyId;
	private long uqtPolEndtId;
	private short uqtUwqCode;
	private long uqtLocId;
	private String uqtUwqAnswer;
	private Date uqtValidityStartDate;
	private Date uqtValidityExpiryDate;
	private Integer uqtPreparedBy;
	private Date uqtPreparedDt;
	private Integer uqtModifiedBy;
	private Date uqtModifiedDt;
	private Byte uqtStatus;

	@Override
	public Object getFieldValue( String fieldName ){
		return null;
	}

	/**
	 * @return the uqtPolPolicyId
	 */
	public long getUqtPolPolicyId(){
		return uqtPolPolicyId;
	}

	/**
	 * @param uqtPolPolicyId the uqtPolPolicyId to set
	 */
	public void setUqtPolPolicyId( long uqtPolPolicyId ){
		this.uqtPolPolicyId = uqtPolPolicyId;
	}

	/**
	 * @return the uqtPolEndtId
	 */
	public long getUqtPolEndtId(){
		return uqtPolEndtId;
	}

	/**
	 * @param uqtPolEndtId the uqtPolEndtId to set
	 */
	public void setUqtPolEndtId( long uqtPolEndtId ){
		this.uqtPolEndtId = uqtPolEndtId;
	}

	/**
	 * @return the uqtUwqCode
	 */
	public short getUqtUwqCode(){
		return uqtUwqCode;
	}

	/**
	 * @param uqtUwqCode the uqtUwqCode to set
	 */
	public void setUqtUwqCode( short uqtUwqCode ){
		this.uqtUwqCode = uqtUwqCode;
	}

	/**
	 * @return the uqtLocId
	 */
	public long getUqtLocId(){
		return uqtLocId;
	}

	/**
	 * @param uqtLocId the uqtLocId to set
	 */
	public void setUqtLocId( long uqtLocId ){
		this.uqtLocId = uqtLocId;
	}

	/**
	 * @return the uqtUwqAnswer
	 */
	public String getUqtUwqAnswer(){
		return uqtUwqAnswer;
	}

	/**
	 * @param uqtUwqAnswer the uqtUwqAnswer to set
	 */
	public void setUqtUwqAnswer( String uqtUwqAnswer ){
		this.uqtUwqAnswer = uqtUwqAnswer;
	}

	/**
	 * @return the uqtValidityStartDate
	 */
	public Date getUqtValidityStartDate(){
		return uqtValidityStartDate;
	}

	/**
	 * @param uqtValidityStartDate the uqtValidityStartDate to set
	 */
	public void setUqtValidityStartDate( Date uqtValidityStartDate ){
		this.uqtValidityStartDate = uqtValidityStartDate;
	}

	/**
	 * @return the uqtValidityExpiryDate
	 */
	public Date getUqtValidityExpiryDate(){
		return uqtValidityExpiryDate;
	}

	/**
	 * @param uqtValidityExpiryDate the uqtValidityExpiryDate to set
	 */
	public void setUqtValidityExpiryDate( Date uqtValidityExpiryDate ){
		this.uqtValidityExpiryDate = uqtValidityExpiryDate;
	}

	/**
	 * @return the uqtPreparedBy
	 */
	public Integer getUqtPreparedBy(){
		return uqtPreparedBy;
	}

	/**
	 * @param uqtPreparedBy the uqtPreparedBy to set
	 */
	public void setUqtPreparedBy( Integer uqtPreparedBy ){
		this.uqtPreparedBy = uqtPreparedBy;
	}

	/**
	 * @return the uqtPreparedDt
	 */
	public Date getUqtPreparedDt(){
		return uqtPreparedDt;
	}

	/**
	 * @param uqtPreparedDt the uqtPreparedDt to set
	 */
	public void setUqtPreparedDt( Date uqtPreparedDt ){
		this.uqtPreparedDt = uqtPreparedDt;
	}

	/**
	 * @return the uqtModifiedBy
	 */
	public Integer getUqtModifiedBy(){
		return uqtModifiedBy;
	}

	/**
	 * @param uqtModifiedBy the uqtModifiedBy to set
	 */
	public void setUqtModifiedBy( Integer uqtModifiedBy ){
		this.uqtModifiedBy = uqtModifiedBy;
	}

	/**
	 * @return the uqtModifiedDt
	 */
	public Date getUqtModifiedDt(){
		return uqtModifiedDt;
	}

	/**
	 * @param uqtModifiedDt the uqtModifiedDt to set
	 */
	public void setUqtModifiedDt( Date uqtModifiedDt ){
		this.uqtModifiedDt = uqtModifiedDt;
	}

	/**
	 * @return the uqtStatus
	 */
	public Byte getUqtStatus(){
		return uqtStatus;
	}

	/**
	 * @param uqtStatus the uqtStatus to set
	 */
	public void setUqtStatus( Byte uqtStatus ){
		this.uqtStatus = uqtStatus;
	}

}
