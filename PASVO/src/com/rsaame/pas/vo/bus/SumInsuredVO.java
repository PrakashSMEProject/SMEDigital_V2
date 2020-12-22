package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class SumInsuredVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Double sumInsured;
	private Double deductible;
	
	private Long cash_Id;
	private Date vsd;
	
	private String eDesc;
	private String aDesc;
	
	private boolean isPromoCover;
	
	
	/**
	 * 
	 * The identifier is to be used in case a section has multiple entries of SI and a differentiator is required 
	 */
	private Integer identifier;
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "sumInsured".equals( fieldName ) ) fieldValue = getSumInsured();
		if( "deductible".equals( fieldName ) ) fieldValue = getDeductible();
		if( "eDesc".equals( fieldName ) ) fieldValue = geteDesc();
		if( "aDesc".equals( fieldName ) ) fieldValue = getaDesc();
		if( "identifier".equals( fieldName ) ) fieldValue = getIdentifier();
		if( "cash_Id".equals( fieldName ) ) fieldValue = getIdentifier();
		if( "validityStartDate".equals( fieldName ) ) fieldValue = getVsd();
		
		return fieldValue;
	}
	
	@Override
	public String toString() {
		return "SumInsuredVO [sumInsured=" + sumInsured +"deductible="+deductible+"]";
	}	
	/**
	 * @return the sumInsured
	 */
	public Double getSumInsured() {
		return sumInsured;
	}

	/**
	 * @param sumInsured the sumInsured to set
	 */
	public void setSumInsured(Double sumInsured) {
		this.sumInsured = sumInsured;
	}

	/**
	 * @return the deductible
	 */
	public Double getDeductible() {
		return deductible;
	}

	/**
	 * @param deductible the deductible to set
	 */
	public void setDeductible(Double deductible) {
		this.deductible = deductible;
	}

	/**
	 * @return the eDesc
	 */
	public String geteDesc() {
		return eDesc;
	}

	/**
	 * @param eDesc the eDesc to set
	 */
	public void seteDesc(String eDesc) {
		this.eDesc = eDesc;
	}

	/**
	 * @return the aDesc
	 */
	public String getaDesc() {
		return aDesc;
	}

	/**
	 * @param aDesc the aDesc to set
	 */
	public void setaDesc(String aDesc) {
		this.aDesc = aDesc;
	}

	/**
	 * @return the identifier
	 */
	public Integer getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the cash_Id
	 */
	public Long getCash_Id(){
		return cash_Id;
	}

	/**
	 * @param cash_Id the cash_Id to set
	 */
	public void setCash_Id( Long cash_Id ){
		this.cash_Id = cash_Id;
	}

	public Date getVsd(){
		return vsd;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}

	public boolean isPromoCover() {
		return isPromoCover;
	}

	public void setPromoCover(boolean isPromoCover) {
		this.isPromoCover = isPromoCover;
	}
	
	
}
