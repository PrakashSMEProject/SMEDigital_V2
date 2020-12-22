package com.rsaame.pas.vo.svc;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;


/**
 * @author M1020859
 * This class is a intermediate mapper vo between vo and TMasPartnerMgmt pojo
 * 
 */
public class TMasPartnerMgmtVOHolder extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal pmmId;
	private String proCode;
	private String proType;
	private BigDecimal proPtCode;
	private BigDecimal proClassCode;
	private BigDecimal proDiscPerc;
	private String proADesc;
	private String proEDesc;
	private Date proStartDate;
	private Date proEndDate;
	private Integer proPreparedBy;
	private Date proPreparedDate;
	private Integer proModifiedBy;
	private Date proModifiedDate;
	private String pmmName;
	private String pmmDesc;
	private String pmmUrl;
	private BigDecimal pmmCustSrc;
	private BigDecimal pmmPtCode;
	private BigDecimal pmmClassCode;
	private BigDecimal pmmStatus;
	private Integer pmmPreparedBy;
	private Date pmmPreparedDate;
	private Integer pmmModifiedBy;
	private Date pmmModifiedDate;

	@Override
	public Object getFieldValue( String fieldName ) {
		return null;
	}

	/**
	 * @return the pmmId
	 */
	public BigDecimal getPmmId() {
		return pmmId;
	}

	/**
	 * @param pmmId the pmmId to set
	 */
	public void setPmmId( BigDecimal pmmId ) {
		this.pmmId = pmmId;
	}

	/**
	 * @return the proCode
	 */
	public String getProCode() {
		return proCode;
	}

	/**
	 * @param proCode the proCode to set
	 */
	public void setProCode( String proCode ) {
		this.proCode = proCode;
	}

	/**
	 * @return the proType
	 */
	public String getProType() {
		return proType;
	}

	/**
	 * @param proType the proType to set
	 */
	public void setProType( String proType ) {
		this.proType = proType;
	}

	/**
	 * @return the proPtCode
	 */
	public BigDecimal getProPtCode() {
		return proPtCode;
	}

	/**
	 * @param proPtCode the proPtCode to set
	 */
	public void setProPtCode( BigDecimal proPtCode ) {
		this.proPtCode = proPtCode;
	}

	/**
	 * @return the proClassCode
	 */
	public BigDecimal getProClassCode() {
		return proClassCode;
	}

	/**
	 * @param proClassCode the proClassCode to set
	 */
	public void setProClassCode( BigDecimal proClassCode ) {
		this.proClassCode = proClassCode;
	}

	/**
	 * @return the proDiscPerc
	 */
	public BigDecimal getProDiscPerc() {
		return proDiscPerc;
	}

	/**
	 * @param proDiscPerc the proDiscPerc to set
	 */
	public void setProDiscPerc( BigDecimal proDiscPerc ) {
		this.proDiscPerc = proDiscPerc;
	}

	/**
	 * @return the proADesc
	 */
	public String getProADesc() {
		return proADesc;
	}

	/**
	 * @param proADesc the proADesc to set
	 */
	public void setProADesc( String proADesc ) {
		this.proADesc = proADesc;
	}

	/**
	 * @return the proEDesc
	 */
	public String getProEDesc() {
		return proEDesc;
	}

	/**
	 * @param proEDesc the proEDesc to set
	 */
	public void setProEDesc( String proEDesc ) {
		this.proEDesc = proEDesc;
	}

	/**
	 * @return the proStartDate
	 */
	public Date getProStartDate() {
		return proStartDate;
	}

	/**
	 * @param proStartDate the proStartDate to set
	 */
	public void setProStartDate( Date proStartDate ) {
		this.proStartDate = proStartDate;
	}

	/**
	 * @return the proEndDate
	 */
	public Date getProEndDate() {
		return proEndDate;
	}

	/**
	 * @param proEndDate the proEndDate to set
	 */
	public void setProEndDate( Date proEndDate ) {
		this.proEndDate = proEndDate;
	}

	/**
	 * @return the proPreparedBy
	 */
	public Integer getProPreparedBy() {
		return proPreparedBy;
	}

	/**
	 * @param proPreparedBy the proPreparedBy to set
	 */
	public void setProPreparedBy( Integer proPreparedBy ) {
		this.proPreparedBy = proPreparedBy;
	}

	/**
	 * @return the proPreparedDate
	 */
	public Date getProPreparedDate() {
		return proPreparedDate;
	}

	/**
	 * @param proPreparedDate the proPreparedDate to set
	 */
	public void setProPreparedDate( Date proPreparedDate ) {
		this.proPreparedDate = proPreparedDate;
	}

	/**
	 * @return the proModifiedBy
	 */
	public Integer getProModifiedBy() {
		return proModifiedBy;
	}

	/**
	 * @param proModifiedBy the proModifiedBy to set
	 */
	public void setProModifiedBy( Integer proModifiedBy ) {
		this.proModifiedBy = proModifiedBy;
	}

	/**
	 * @return the proModifiedDate
	 */
	public Date getProModifiedDate() {
		return proModifiedDate;
	}

	/**
	 * @param proModifiedDate the proModifiedDate to set
	 */
	public void setProModifiedDate( Date proModifiedDate ) {
		this.proModifiedDate = proModifiedDate;
	}

	/**
	 * @return the pmmName
	 */
	public String getPmmName() {
		return pmmName;
	}

	/**
	 * @param pmmName the pmmName to set
	 */
	public void setPmmName( String pmmName ) {
		this.pmmName = pmmName;
	}

	/**
	 * @return the pmmDesc
	 */
	public String getPmmDesc() {
		return pmmDesc;
	}

	/**
	 * @param pmmDesc the pmmDesc to set
	 */
	public void setPmmDesc( String pmmDesc ) {
		this.pmmDesc = pmmDesc;
	}

	/**
	 * @return the pmmUrl
	 */
	public String getPmmUrl() {
		return pmmUrl;
	}

	/**
	 * @param pmmUrl the pmmUrl to set
	 */
	public void setPmmUrl( String pmmUrl ) {
		this.pmmUrl = pmmUrl;
	}

	/**
	 * @return the pmmCustSrc
	 */
	public BigDecimal getPmmCustSrc() {
		return pmmCustSrc;
	}

	/**
	 * @param pmmCustSrc the pmmCustSrc to set
	 */
	public void setPmmCustSrc( BigDecimal pmmCustSrc ) {
		this.pmmCustSrc = pmmCustSrc;
	}

	/**
	 * @return the pmmPtCode
	 */
	public BigDecimal getPmmPtCode() {
		return pmmPtCode;
	}

	/**
	 * @param pmmPtCode the pmmPtCode to set
	 */
	public void setPmmPtCode( BigDecimal pmmPtCode ) {
		this.pmmPtCode = pmmPtCode;
	}

	/**
	 * @return the pmmClassCode
	 */
	public BigDecimal getPmmClassCode() {
		return pmmClassCode;
	}

	/**
	 * @param pmmClassCode the pmmClassCode to set
	 */
	public void setPmmClassCode( BigDecimal pmmClassCode ) {
		this.pmmClassCode = pmmClassCode;
	}

	/**
	 * @return the pmmStatus
	 */
	public BigDecimal getPmmStatus() {
		return pmmStatus;
	}

	/**
	 * @param pmmStatus the pmmStatus to set
	 */
	public void setPmmStatus( BigDecimal pmmStatus ) {
		this.pmmStatus = pmmStatus;
	}

	/**
	 * @return the pmmPreparedBy
	 */
	public Integer getPmmPreparedBy() {
		return pmmPreparedBy;
	}

	/**
	 * @param pmmPreparedBy the pmmPreparedBy to set
	 */
	public void setPmmPreparedBy( Integer pmmPreparedBy ) {
		this.pmmPreparedBy = pmmPreparedBy;
	}

	/**
	 * @return the pmmPreparedDate
	 */
	public Date getPmmPreparedDate() {
		return pmmPreparedDate;
	}

	/**
	 * @param pmmPreparedDate the pmmPreparedDate to set
	 */
	public void setPmmPreparedDate( Date pmmPreparedDate ) {
		this.pmmPreparedDate = pmmPreparedDate;
	}

	/**
	 * @return the pmmModifiedBy
	 */
	public Integer getPmmModifiedBy() {
		return pmmModifiedBy;
	}

	/**
	 * @param pmmModifiedBy the pmmModifiedBy to set
	 */
	public void setPmmModifiedBy( Integer pmmModifiedBy ) {
		this.pmmModifiedBy = pmmModifiedBy;
	}

	/**
	 * @return the pmmModifiedDate
	 */
	public Date getPmmModifiedDate() {
		return pmmModifiedDate;
	}

	/**
	 * @param pmmModifiedDate the pmmModifiedDate to set
	 */
	public void setPmmModifiedDate( Date pmmModifiedDate ) {
		this.pmmModifiedDate = pmmModifiedDate;
	}

}
