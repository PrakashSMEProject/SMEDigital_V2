package com.rsaame.pas.vo.svc;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class TMasPromoDiscCoverVO extends BaseVO {

	
	private static final long serialVersionUID = 1L;
	
	private String pdcProCode;
	private BigDecimal pdcSchemeCode;
	private BigDecimal pdcPerc;
	private BigDecimal pdcCovCode;
	private BigDecimal pdcCtCode;
	private Integer pdcPreparedBy;
	private Date pdcPreparedDate;
	private Integer pdcModifiedBy;
	private Date pdcModifiedDate;
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
	

	@Override
	public Object getFieldValue( String fieldName ) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @return the pdcProCode
	 */
	public String getPdcProCode() {
		return pdcProCode;
	}


	/**
	 * @param pdcProCode the pdcProCode to set
	 */
	public void setPdcProCode( String pdcProCode ) {
		this.pdcProCode = pdcProCode;
	}


	/**
	 * @return the pdcSchemeCode
	 */
	public BigDecimal getPdcSchemeCode() {
		return pdcSchemeCode;
	}


	/**
	 * @param pdcSchemeCode the pdcSchemeCode to set
	 */
	public void setPdcSchemeCode( BigDecimal pdcSchemeCode ) {
		this.pdcSchemeCode = pdcSchemeCode;
	}


	/**
	 * @return the pdcPerc
	 */
	public BigDecimal getPdcPerc() {
		return pdcPerc;
	}


	/**
	 * @param pdcPerc the pdcPerc to set
	 */
	public void setPdcPerc( BigDecimal pdcPerc ) {
		this.pdcPerc = pdcPerc;
	}


	/**
	 * @return the pdcCovCode
	 */
	public BigDecimal getPdcCovCode() {
		return pdcCovCode;
	}


	/**
	 * @param pdcCovCode the pdcCovCode to set
	 */
	public void setPdcCovCode( BigDecimal pdcCovCode ) {
		this.pdcCovCode = pdcCovCode;
	}


	/**
	 * @return the pdcCtCode
	 */
	public BigDecimal getPdcCtCode() {
		return pdcCtCode;
	}


	/**
	 * @param pdcCtCode the pdcCtCode to set
	 */
	public void setPdcCtCode( BigDecimal pdcCtCode ) {
		this.pdcCtCode = pdcCtCode;
	}


	/**
	 * @return the pdcPreparedBy
	 */
	public Integer getPdcPreparedBy() {
		return pdcPreparedBy;
	}


	/**
	 * @param pdcPreparedBy the pdcPreparedBy to set
	 */
	public void setPdcPreparedBy( Integer pdcPreparedBy ) {
		this.pdcPreparedBy = pdcPreparedBy;
	}


	/**
	 * @return the pdcPreparedDate
	 */
	public Date getPdcPreparedDate() {
		return pdcPreparedDate;
	}


	/**
	 * @param pdcPreparedDate the pdcPreparedDate to set
	 */
	public void setPdcPreparedDate( Date pdcPreparedDate ) {
		this.pdcPreparedDate = pdcPreparedDate;
	}


	/**
	 * @return the pdcModifiedBy
	 */
	public Integer getPdcModifiedBy() {
		return pdcModifiedBy;
	}


	/**
	 * @param pdcModifiedBy the pdcModifiedBy to set
	 */
	public void setPdcModifiedBy( Integer pdcModifiedBy ) {
		this.pdcModifiedBy = pdcModifiedBy;
	}


	/**
	 * @return the pdcModifiedDate
	 */
	public Date getPdcModifiedDate() {
		return pdcModifiedDate;
	}


	/**
	 * @param pdcModifiedDate the pdcModifiedDate to set
	 */
	public void setPdcModifiedDate( Date pdcModifiedDate ) {
		this.pdcModifiedDate = pdcModifiedDate;
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

}
