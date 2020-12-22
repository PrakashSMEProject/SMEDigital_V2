package com.rsaame.pas.vo.app;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

public class InsuredCommentVO extends BaseVO implements IFieldValue{

	private static final long serialVersionUID = 1L;

	private Long pocPolicyId;
	private String quotePolicyNo;
	private String pocComments;
	private String documentDesc;
	private String commentDate;
	private Integer pocPreparedBy;

	private Long pocEndtId;
	private Integer pocDocumentCode;
	private Integer pocPolicyStatus;
	private Date pocDate;
	private Date pocPreparedDt;
	private Integer pocModifiedBy;
	private Date pocModifiedDt;
	private String policyReferredTo;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "pocPolicyId".equals( fieldName ) ) fieldValue = getPocPolicyId();
		if( "quotePolicyNo".equals( fieldName ) ) fieldValue = getQuotePolicyNo();
		if( "pocComments".equals( fieldName ) ) fieldValue = getPocComments();
		if( "documentDesc".equals( fieldName ) ) fieldValue = getDocumentDesc();
		if( "commentDate".equals( fieldName ) ) fieldValue = getCommentDate();
		if( "pocPreparedBy".equals( fieldName ) ) fieldValue = getPocPreparedBy();
		if( "pocEndtId".equals( fieldName ) ) fieldValue = getPocEndtId();
		if( "pocDocumentCode".equals( fieldName ) ) fieldValue = getPocDocumentCode();
		if( "pocPolicyStatus".equals( fieldName ) ) fieldValue = getPocPolicyStatus();
		if( "pocDate".equals( fieldName ) ) fieldValue = getPocDate();
		if( "pocPreparedDt".equals( fieldName ) ) fieldValue = getPocPreparedDt();
		if( "pocModifiedBy".equals( fieldName ) ) fieldValue = getPocModifiedBy();
		if( "pocModifiedDt".equals( fieldName ) ) fieldValue = getPocModifiedDt();
		if( "policyReferredTo".equals( fieldName ) ) fieldValue = getPolicyReferredTo();

		return fieldValue;
	}

	/**
	 * @return the pocPolicyId
	 */
	public Long getPocPolicyId(){
		return pocPolicyId;
	}

	/**
	 * @param pocPolicyId the pocPolicyId to set
	 */
	public void setPocPolicyId( Long pocPolicyId ){
		this.pocPolicyId = pocPolicyId;
	}

	/**
	 * @return the quotePolicyNo
	 */
	public String getQuotePolicyNo(){
		return quotePolicyNo;
	}

	/**
	 * @param quotePolicyNo the quotePolicyNo to set
	 */
	public void setQuotePolicyNo( String quotePolicyNo ){
		this.quotePolicyNo = quotePolicyNo;
	}

	/**
	 * @return the pocComments
	 */
	public String getPocComments(){
		return pocComments;
	}

	/**
	 * @param pocComments the pocComments to set
	 */
	public void setPocComments( String pocComments ){
		this.pocComments = pocComments;
	}

	/**
	 * @return the documentDesc
	 */
	public String getDocumentDesc(){
		return documentDesc;
	}

	/**
	 * @param documentDesc the documentDesc to set
	 */
	public void setDocumentDesc( String documentDesc ){
		this.documentDesc = documentDesc;
	}

	/**
	 * @return the commentDate
	 */
	public String getCommentDate(){
		return commentDate;
	}

	/**
	 * @param commentDate the commentDate to set
	 */
	public void setCommentDate( String commentDate ){
		this.commentDate = commentDate;
	}

	/**
	 * @return the pocPreparedBy
	 */
	public Integer getPocPreparedBy(){
		return pocPreparedBy;
	}

	/**
	 * @param pocPreparedBy the pocPreparedBy to set
	 */
	public void setPocPreparedBy( Integer pocPreparedBy ){
		this.pocPreparedBy = pocPreparedBy;
	}

	/**
	 * @return the pocEndtId
	 */
	public Long getPocEndtId(){
		return pocEndtId;
	}

	/**
	 * @param pocEndtId the pocEndtId to set
	 */
	public void setPocEndtId( Long pocEndtId ){
		this.pocEndtId = pocEndtId;
	}

	/**
	 * @return the pocDocumentCode
	 */
	public Integer getPocDocumentCode(){
		return pocDocumentCode;
	}

	/**
	 * @param pocDocumentCode the pocDocumentCode to set
	 */
	public void setPocDocumentCode( Integer pocDocumentCode ){
		this.pocDocumentCode = pocDocumentCode;
	}

	/**
	 * @return the pocPolicyStatus
	 */
	public Integer getPocPolicyStatus(){
		return pocPolicyStatus;
	}

	/**
	 * @param pocPolicyStatus the pocPolicyStatus to set
	 */
	public void setPocPolicyStatus( Integer pocPolicyStatus ){
		this.pocPolicyStatus = pocPolicyStatus;
	}

	/**
	 * @return the pocDate
	 */
	public Date getPocDate(){
		return pocDate;
	}

	/**
	 * @param pocDate the pocDate to set
	 */
	public void setPocDate( Date pocDate ){
		this.pocDate = pocDate;
	}

	/**
	 * @return the pocPreparedDt
	 */
	public Date getPocPreparedDt(){
		return pocPreparedDt;
	}

	/**
	 * @param pocPreparedDt the pocPreparedDt to set
	 */
	public void setPocPreparedDt( Date pocPreparedDt ){
		this.pocPreparedDt = pocPreparedDt;
	}

	/**
	 * @return the pocModifiedBy
	 */
	public Integer getPocModifiedBy(){
		return pocModifiedBy;
	}

	/**
	 * @param pocModifiedBy the pocModifiedBy to set
	 */
	public void setPocModifiedBy( Integer pocModifiedBy ){
		this.pocModifiedBy = pocModifiedBy;
	}

	/**
	 * @return the pocModifiedDt
	 */
	public Date getPocModifiedDt(){
		return pocModifiedDt;
	}

	/**
	 * @param pocModifiedDt the pocModifiedDt to set
	 */
	public void setPocModifiedDt( Date pocModifiedDt ){
		this.pocModifiedDt = pocModifiedDt;
	}

	/**
	 * @return the policyReferredTo
	 */
	public String getPolicyReferredTo(){
		return policyReferredTo;
	}

	/**
	 * @param policyReferredTo the policyReferredTo to set
	 */
	public void setPolicyReferredTo( String policyReferredTo ){
		this.policyReferredTo = policyReferredTo;
	}

}
