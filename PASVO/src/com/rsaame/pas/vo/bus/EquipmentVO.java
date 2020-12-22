package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
 
public class EquipmentVO extends RiskGroupDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5516471181436279177L;

	private String equipmentType;
	private String equipmentDesc="";
	private String yearOfMake;
	private String serialNumber;
	private Integer quantity;
	private SumInsuredVO sumInsuredDetails;
	private Long contentId;
	private Integer index;

	public SumInsuredVO getSumInsuredDetails(){
		return sumInsuredDetails;
	}

	public void setSumInsuredDetails( SumInsuredVO sumInsuredDetails ){
		this.sumInsuredDetails = sumInsuredDetails;
	}

	public String getEquipmentType(){
		return equipmentType;
	}

	public void setEquipmentType( String equipmentType ){
		this.equipmentType = equipmentType;
	}

	public String getEquipmentDesc(){
		return equipmentDesc;
	}

	public void setEquipmentDesc( String equipmentDesc ){
		this.equipmentDesc = equipmentDesc;
	}

	public String getSerialNumber(){
		return serialNumber;
	}

	public void setSerialNumber( String serialNumber ){
		this.serialNumber = serialNumber;
	}

	public Integer getQuantity(){
		return quantity;
	}

	public void setQuantity( Integer quantity ){
		this.quantity = quantity;
	}

	public String getYearOfMake(){
		return yearOfMake;
	}

	public void setYearOfMake( String yearOfMake ){
		this.yearOfMake = yearOfMake;
	}

	public Long getContentId(){
		return contentId;
	}

	public void setContentId( Long contentId ){
		this.contentId = contentId;
	}

	public Integer getIndex(){
		return index;
	}

	public void setIndex( Integer index ){
		this.index = index;
	}

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "equipmentType".equals( fieldName ) ) fieldValue = getEquipmentType();
		if( "equipmentDesc".equals( fieldName ) ) fieldValue = getEquipmentDesc();
		if( "yearOfMake".equals( fieldName ) ) fieldValue = getYearOfMake();
		if( "serialNumber".equals( fieldName ) ) fieldValue = getSerialNumber();
		if( "quantity".equals( fieldName ) ) fieldValue = getQuantity();
		if( "sumInsuredDetails".equals( fieldName ) ) fieldValue = getSumInsuredDetails();
		if( "contentId".equals( fieldName ) ) fieldValue = getContentId();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();

		return fieldValue;
	}

	@Override
	public String toString(){
		return "EquipmentVO [equipmentType=" + equipmentType + ", equipmentDesc=" + equipmentDesc + ", yearOfMake=" + yearOfMake + ", serialNumber=" + serialNumber + ", quantity="
				+ quantity + ", sumInsuredDetails=" + sumInsuredDetails + ", contentId=" + contentId + "]";
	}

	@Override
	public boolean equals( Object obj ){
		boolean equal = false;

		if( Utils.isEmpty( obj ) || !( obj instanceof EquipmentVO ) ) return false;

		if( Utils.isEmpty( this.getContentId() ) && Utils.isEmpty( ( (EquipmentVO) obj ).getContentId() ) ) return true;

		if( !Utils.isEmpty( ( (EquipmentVO) obj ).getContentId() ) ){

			if( this.getContentId().toString().equalsIgnoreCase( ( (EquipmentVO) obj ).getContentId().toString() ) ){
				return true;
			}
		}

		return equal;
	}

	public int hashCode(){

		return 1;
	}

}
