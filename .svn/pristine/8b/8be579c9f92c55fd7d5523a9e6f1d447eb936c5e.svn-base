/**
 * Holds the Default covers for Home/Travel.
 */
package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1016284
 * @since Phase3
 */
public class DefaultCoverDetailsVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	
	private	String coverName;
	private	String coverDesc;
	private	SumInsuredVO sumInsured;
	private	PremiumVO premium;
	private	List<SumInsuredVO> itemDetails;

	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 * returns Object
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;
		if( "coverName".equals( fieldName ) ) fieldValue = getCoverName();
		if( "coverDesc".equals( fieldName ) ) fieldValue = getCoverDesc();
		if( "sumInsured".equals( fieldName ) ) fieldValue = getSumInsured();
		if( "premium".equals( fieldName ) ) fieldValue = getPremium();
		if( "itemDetails".equals( fieldName ) ) fieldValue = getItemDetails();
		return fieldValue;
	}


	/**
	 * @return String
	 */
	public String getCoverName() {
		return coverName;
	}


	/**
	 * @param coverName
	 */
	public void setCoverName(String coverName) {
		this.coverName = coverName;
	}


	/**
	 * @return String
	 */
	public String getCoverDesc() {
		return coverDesc;
	}


	/**
	 * @param coverDesc
	 */
	public void setCoverDesc(String coverDesc) {
		this.coverDesc = coverDesc;
	}


	/**
	 * @return SumInsuredVO
	 */
	public SumInsuredVO getSumInsured() {
		return sumInsured;
	}


	/**
	 * @param sumInsured
	 */
	public void setSumInsured(SumInsuredVO sumInsured) {
		this.sumInsured = sumInsured;
	}


	/**
	 * @return PremiumVO
	 */
	public PremiumVO getPremium() {
		return premium;
	}


	/**
	 * @param premium
	 */
	public void setPremium(PremiumVO premium) {
		this.premium = premium;
	}


	/**
	 * @return List<SumInsuredVO>
	 */
	public List<SumInsuredVO> getItemDetails() {
		return itemDetails;
	}


	/**
	 * @param itemDetails
	 */
	public void setItemDetails(List<SumInsuredVO> itemDetails) {
		this.itemDetails = itemDetails;
	}


}
