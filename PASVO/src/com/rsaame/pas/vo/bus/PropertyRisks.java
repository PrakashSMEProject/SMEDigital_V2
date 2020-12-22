package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 *  
 * @author m1014644
 *
 */

public class PropertyRisks extends BaseVO {

	/*private Double building;
	private Double furAndFix;
	private Double stockDisplay;
	private Double rent;
	private Double stockStorage;
	private Double otherContents;
	private Double deductibles;*/
	
	private static final long serialVersionUID = 1L;
	private java.util.List<PropertyRiskDetails> propertyCoversDetails =  new com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails>(PropertyRiskDetails.class);

	/*public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "building".equals( fieldName ) ) fieldValue = getBuilding();
		if( "furAndFix".equals( fieldName ) ) fieldValue = getFurAndFix();
		if( "stockDisplay".equals( fieldName ) ) fieldValue = getStockDisplay();
		if( "rent".equals( fieldName ) ) fieldValue = getRent();
		if( "stockStorage".equals( fieldName ) ) fieldValue = getStockStorage();
		if( "otherContents".equals( fieldName ) ) fieldValue = getOtherContents();
		if( "deductibles".equals( fieldName ) ) fieldValue = getDeductibles();

		return fieldValue;
	}*/
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if( "propertyCoversDetails".equals( fieldName ) ) fieldValue = getPropertyCoversDetails();
		return fieldValue;
	}

	/**
	 * @return the propertyCoversDetails
	 */
	public java.util.List<PropertyRiskDetails> getPropertyCoversDetails() {
		return propertyCoversDetails;
	}

	/**
	 * @param propertyCoversDetails the propertyCoversDetails to set
	 */
	public void setPropertyCoversDetails(
			java.util.List<PropertyRiskDetails> propertyCoversDetails) {
		this.propertyCoversDetails = propertyCoversDetails;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropertyRisks [propertyCoversDetails=" + propertyCoversDetails
				+ "]";
	}

	


	

	/*public Double getBuilding() {
		return building;
	}


	public void setBuilding(Double building) {
		this.building = building;
	}


	public Double getFurAndFix() {
		return furAndFix;
	}


	public void setFurAndFix(Double furAndFix) {
		this.furAndFix = furAndFix;
	}


	public Double getStockDisplay() {
		return stockDisplay;
	}


	public void setStockDisplay(Double stockDisplay) {
		this.stockDisplay = stockDisplay;
	}


	public Double getRent() {
		return rent;
	}


	public void setRent(Double rent) {
		this.rent = rent;
	}


	public Double getStockStorage() {
		return stockStorage;
	}


	public void setStockStorage(Double stockStorage) {
		this.stockStorage = stockStorage;
	}


	public Double getOtherContents() {
		return otherContents;
	}


	public void setOtherContents(Double otherContents) {
		this.otherContents = otherContents;
	}


	public Double getDeductibles(){
		return deductibles;
	}
	public void setDeductibles( Double deductibles ){
		this.deductibles = deductibles;
	}*/
	
	
}
