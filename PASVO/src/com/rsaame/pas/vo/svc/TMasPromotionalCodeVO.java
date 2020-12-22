package com.rsaame.pas.vo.svc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mindtree.ruc.cmn.base.BaseVO;


public class TMasPromotionalCodeVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
//	private Set<TMasPromoDiscCoverVO> tMasPromoDiscCoverId = new HashSet<TMasPromoDiscCoverVO>();
	
	
	
	

	/**
	 * 
	 */
	public TMasPromotionalCodeVO(){
		
	}

	/**
	 * @param proCode
	 * @param proType
	 * @param proPtCode
	 * @param proClassCode
	 * @param proDiscPerc
	 * @param proADesc
	 * @param proEDesc
	 * @param proStartDate
	 * @param proEndDate
	 * @param proPreparedBy
	 * @param proPreparedDate
	 * @param proModifiedBy
	 * @param proModifiedDate
	 * @param tMasPromoDiscCoverId
	 */
	public TMasPromotionalCodeVO( String proCode, String proType, BigDecimal proPtCode, BigDecimal proClassCode, BigDecimal proDiscPerc, String proADesc, String proEDesc,
			Date proStartDate, Date proEndDate, Integer proPreparedBy, Date proPreparedDate, Integer proModifiedBy, Date proModifiedDate,
			Set<TMasPromoDiscCoverVO> tMasPromoDiscCoverId ){
		super();
		this.proCode = proCode;
		this.proType = proType;
		this.proPtCode = proPtCode;
		this.proClassCode = proClassCode;
		this.proDiscPerc = proDiscPerc;
		this.proADesc = proADesc;
		this.proEDesc = proEDesc;
		this.proStartDate = proStartDate;
		this.proEndDate = proEndDate;
		this.proPreparedBy = proPreparedBy;
		this.proPreparedDate = proPreparedDate;
		this.proModifiedBy = proModifiedBy;
		this.proModifiedDate = proModifiedDate;
	//	this.tMasPromoDiscCoverId = tMasPromoDiscCoverId;
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

	@Override
	public Object getFieldValue( String fieldName ) {
		return null;
	}

	/**
	 * @return the tMasPromoDiscCoverId
	 */
	/*public Set<TMasPromoDiscCoverVO> gettMasPromoDiscCoverId() {
		return tMasPromoDiscCoverId;
	}

	*//**
	 * @param tMasPromoDiscCoverId the tMasPromoDiscCoverId to set
	 *//*
	public void settMasPromoDiscCoverId( Set<TMasPromoDiscCoverVO> tMasPromoDiscCoverId ) {
		this.tMasPromoDiscCoverId = tMasPromoDiscCoverId;
	}*/

	

}
