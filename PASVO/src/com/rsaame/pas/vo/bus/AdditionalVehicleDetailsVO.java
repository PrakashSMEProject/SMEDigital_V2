package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * 
 * @author m1014438
 *
 */
public class AdditionalVehicleDetailsVO extends BaseVO{

	String vehicleEngineNo;
	String vehicleChassisNo;
	String vehicleRegnTxt;
	Integer vehicleRegnNo;
	String vehicleTCFNo;
	Integer vehicleCylinder;
	Double vehicleOriginalCost;
	String vehicleDateofRegn;// needs to finalize String/Date
	Integer vehicleBody;
	Integer vehicleTonnage;
	Integer vehicleEnColor;
	String vehiclePlaceofRegn;
	Integer vehiclePA;
	Integer vehiclePL;
	String vehicleCertNo;
	String vehicleCertStartDate;// needs to finalize String/Date
	String vehicleCertEndDate;// needs to finalize String/Date
	String vehicleHirePurchase;
	String vehicleArColor;
	Integer vehicleUse;
	String vehicleArMakeandModel;// needs to finalize String/Integer

	/**
		 * @return the vehicleEngineNo
		 */
	public String getVehicleEngineNo(){
		return vehicleEngineNo;
	}

	/**
	 * @param vehicleEngineNo the vehicleEngineNo to set
	 */
	public void setVehicleEngineNo( String vehicleEngineNo ){
		this.vehicleEngineNo = vehicleEngineNo;
	}

	/**
	 * @return the vehicleChassisNo
	 */
	public String getVehicleChassisNo(){
		return vehicleChassisNo;
	}

	/**
	 * @param vehicleChassisNo the vehicleChassisNo to set
	 */
	public void setVehicleChassisNo( String vehicleChassisNo ){
		this.vehicleChassisNo = vehicleChassisNo;
	}

	/**
	 * @return the vehicleRegnTxt
	 */
	public String getVehicleRegnTxt(){
		return vehicleRegnTxt;
	}

	/**
	 * @param vehicleRegnTxt the vehicleRegnTxt to set
	 */
	public void setVehicleRegnTxt( String vehicleRegnTxt ){
		this.vehicleRegnTxt = vehicleRegnTxt;
	}

	/**
	 * @return the vehicleRegnNo
	 */
	public Integer getVehicleRegnNo(){
		return vehicleRegnNo;
	}

	/**
	 * @param vehicleRegnNo the vehicleRegnNo to set
	 */
	public void setVehicleRegnNo( Integer vehicleRegnNo ){
		this.vehicleRegnNo = vehicleRegnNo;
	}

	/**
	 * @return the vehicleTCFNo
	 */
	public String getVehicleTCFNo(){
		return vehicleTCFNo;
	}

	/**
	 * @param vehicleTCFNo the vehicleTCFNo to set
	 */
	public void setVehicleTCFNo( String vehicleTCFNo ){
		this.vehicleTCFNo = vehicleTCFNo;
	}

	/**
	 * @return the vehicleCylinder
	 */
	public Integer getVehicleCylinder(){
		return vehicleCylinder;
	}

	/**
	 * @param vehicleCylinder the vehicleCylinder to set
	 */
	public void setVehicleCylinder( Integer vehicleCylinder ){
		this.vehicleCylinder = vehicleCylinder;
	}

	/**
	 * @return the vehicleOriginalCost
	 */
	public Double getVehicleOriginalCost(){
		return vehicleOriginalCost;
	}

	/**
	 * @param vehicleOriginalCost the vehicleOriginalCost to set
	 */
	public void setVehicleOriginalCost( Double vehicleOriginalCost ){
		this.vehicleOriginalCost = vehicleOriginalCost;
	}

	/**
	 * @return the vehicleDateofRegn
	 */
	public String getVehicleDateofRegn(){
		return vehicleDateofRegn;
	}

	/**
	 * @param vehicleDateofRegn the vehicleDateofRegn to set
	 */
	public void setVehicleDateofRegn( String vehicleDateofRegn ){
		this.vehicleDateofRegn = vehicleDateofRegn;
	}

	/**
	 * @return the vehicleBody
	 */
	public Integer getVehicleBody(){
		return vehicleBody;
	}

	/**
	 * @param vehicleBody the vehicleBody to set
	 */
	public void setVehicleBody( Integer vehicleBody ){
		this.vehicleBody = vehicleBody;
	}

	/**
	 * @return the vehicleTonnage
	 */
	public Integer getVehicleTonnage(){
		return vehicleTonnage;
	}

	/**
	 * @param vehicleTonnage the vehicleTonnage to set
	 */
	public void setVehicleTonnage( Integer vehicleTonnage ){
		this.vehicleTonnage = vehicleTonnage;
	}

	/**
	 * @return the vehicleEnColor
	 */
	public Integer getVehicleEnColor(){
		return vehicleEnColor;
	}

	/**
	 * @param vehicleEnColor the vehicleEnColor to set
	 */
	public void setVehicleEnColor( Integer vehicleEnColor ){
		this.vehicleEnColor = vehicleEnColor;
	}

	/**
	 * @return the vehiclePlaceofRegn
	 */
	public String getVehiclePlaceofRegn(){
		return vehiclePlaceofRegn;
	}

	/**
	 * @param vehiclePlaceofRegn the vehiclePlaceofRegn to set
	 */
	public void setVehiclePlaceofRegn( String vehiclePlaceofRegn ){
		this.vehiclePlaceofRegn = vehiclePlaceofRegn;
	}

	/**
	 * @return the vehiclePA
	 */
	public Integer getVehiclePA(){
		return vehiclePA;
	}

	/**
	 * @param vehiclePA the vehiclePA to set
	 */
	public void setVehiclePA( Integer vehiclePA ){
		this.vehiclePA = vehiclePA;
	}

	/**
	 * @return the vehiclePL
	 */
	public Integer getVehiclePL(){
		return vehiclePL;
	}

	/**
	 * @param vehiclePL the vehiclePL to set
	 */
	public void setVehiclePL( Integer vehiclePL ){
		this.vehiclePL = vehiclePL;
	}

	/**
	 * @return the vehicleCertNo
	 */
	public String getVehicleCertNo(){
		return vehicleCertNo;
	}

	/**
	 * @param vehicleCertNo the vehicleCertNo to set
	 */
	public void setVehicleCertNo( String vehicleCertNo ){
		this.vehicleCertNo = vehicleCertNo;
	}

	/**
	 * @return the vehicleCertStartDate
	 */
	public String getVehicleCertStartDate(){
		return vehicleCertStartDate;
	}

	/**
	 * @param vehicleCertStartDate the vehicleCertStartDate to set
	 */
	public void setVehicleCertStartDate( String vehicleCertStartDate ){
		this.vehicleCertStartDate = vehicleCertStartDate;
	}

	/**
	 * @return the vehicleCertEndDate
	 */
	public String getVehicleCertEndDate(){
		return vehicleCertEndDate;
	}

	/**
	 * @param vehicleCertEndDate the vehicleCertEndDate to set
	 */
	public void setVehicleCertEndDate( String vehicleCertEndDate ){
		this.vehicleCertEndDate = vehicleCertEndDate;
	}

	/**
	 * @return the vehicleHirePurchase
	 */
	public String getVehicleHirePurchase(){
		return vehicleHirePurchase;
	}

	/**
	 * @param vehicleHirePurchase the vehicleHirePurchase to set
	 */
	public void setVehicleHirePurchase( String vehicleHirePurchase ){
		this.vehicleHirePurchase = vehicleHirePurchase;
	}

	/**
	 * @return the vehicleArColor
	 */
	public String getVehicleArColor(){
		return vehicleArColor;
	}

	/**
	 * @param vehicleArColor the vehicleArColor to set
	 */
	public void setVehicleArColor( String vehicleArColor ){
		this.vehicleArColor = vehicleArColor;
	}

	/**
	 * @return the vehicleUse
	 */
	public Integer getVehicleUse(){
		return vehicleUse;
	}

	/**
	 * @param vehicleUse the vehicleUse to set
	 */
	public void setVehicleUse( Integer vehicleUse ){
		this.vehicleUse = vehicleUse;
	}

	/**
	 * @return the vehicleArMakeandModel
	 */
	public String getVehicleArMakeandModel(){
		return vehicleArMakeandModel;
	}

	/**
	 * @param vehicleArMakeandModel the vehicleArMakeandModel to set
	 */
	public void setVehicleArMakeandModel( String vehicleArMakeandModel ){
		this.vehicleArMakeandModel = vehicleArMakeandModel;
	}

	@Override
	public Object getFieldValue( String fieldName ){

		Object fieldValue = null;

		if( "vehicleEngineNo".equals( fieldName ) ) fieldValue = getVehicleEngineNo();
		if( "vehicleChassisNo".equals( fieldName ) ) fieldValue = getVehicleChassisNo();
		if( "vehicleRegnTxt".equals( fieldName ) ) fieldValue = getVehicleRegnTxt();
		if( "vehicleRegnNo".equals( fieldName ) ) fieldValue = getVehicleRegnNo();
		if( "vehicleTCFNo".equals( fieldName ) ) fieldValue = getVehicleTCFNo();
		if( "vehicleCylinder".equals( fieldName ) ) fieldValue = getVehicleCylinder();
		if( "vehicleOriginalCost".equals( fieldName ) ) fieldValue = getVehicleOriginalCost();
		if( "vehicleDateofRegn".equals( fieldName ) ) fieldValue = getVehicleDateofRegn();
		if( "vehicleBody".equals( fieldName ) ) fieldValue = getVehicleBody();
		if( "vehicleTonnage".equals( fieldName ) ) fieldValue = getVehicleTonnage();
		if( "vehicleEnColor".equals( fieldName ) ) fieldValue = getVehicleEnColor();
		if( "vehiclePlaceofRegn".equals( fieldName ) ) fieldValue = getVehiclePlaceofRegn();
		if( "vehiclePA".equals( fieldName ) ) fieldValue = getVehiclePA();
		if( "vehiclePL".equals( fieldName ) ) fieldValue = getVehiclePL();
		if( "vehicleCertNo".equals( fieldName ) ) fieldValue = getVehicleCertNo();
		if( "vehicleCertStartDate".equals( fieldName ) ) fieldValue = getVehicleCertStartDate();
		if( "vehicleCertEndDate".equals( fieldName ) ) fieldValue = getVehicleCertEndDate();
		if( "vehicleHirePurchase".equals( fieldName ) ) fieldValue = getVehicleHirePurchase();
		if( "vehicleArColor".equals( fieldName ) ) fieldValue = getVehicleArColor();
		if( "vehicleUse".equals( fieldName ) ) fieldValue = getVehicleUse();
		if( "vehicleArMakeandModel".equals( fieldName ) ) fieldValue = getVehicleArMakeandModel();

		return fieldValue;
	}

}
