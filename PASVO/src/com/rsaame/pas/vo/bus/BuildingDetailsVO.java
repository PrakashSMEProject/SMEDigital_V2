/**
 * Holds details of Building.
 * 
 *
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

/**
 * @author M1014644
 * @since Phase 3
 */
public class BuildingDetailsVO extends PremiumVO{

	private static final long serialVersionUID = 1L;

	private String emirates;
	private String area;
	private String buildingname;
	private String otherDetails;
	private String flatVillaNo;
	// Changes Home Revamp requirement 4.1 */
	//Two fields are added Home Revamp 4.1 B2B and B2C
	private Short bldTotalNoRooms;
	private Short bldTotalNoFloors;
	private String mortgageeName;
	private Short ownershipStatus;
	private Short typeOfProperty;
	private Short geoAreaCode;
	private Date vsd;
	private Integer riRskCode;
	//Added for Informap changes
	private Double latitude;
	private Double longitude;
	private String address;
	private String inforMapStatus;
	// 11645 - Home Digital API
	private Short totalNoFloors;
	private Short totalNoRooms;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the riRskCode
	 */
	public Integer getRiRskCode(){
		return riRskCode;
	}

	/**
	 * @param riRskCode the riRskCode to set
	 */
	public void setRiRskCode( Integer riRskCode ){
		this.riRskCode = riRskCode;
	}

	/**
	 * @return the typeOfProperty
	 */
	public Short getTypeOfProperty(){
		return typeOfProperty;
	}

	/**
	 * @param typeOfProperty the typeOfProperty to set
	 */
	public void setTypeOfProperty( Short typeOfProperty ){
		this.typeOfProperty = typeOfProperty;
	}

	/**
	 * @return the ownershipStatus
	 */
	public Short getOwnershipStatus(){
		return ownershipStatus;
	}

	/**
	 * @param ownershipStatus the ownershipStatus to set
	 */
	public void setOwnershipStatus( Short ownershipStatus ){
		this.ownershipStatus = ownershipStatus;
	}

	private CoverVO coverCodes;
	private RiskVO riskCodes;
	private SumInsuredVO sumInsured;

	/**
	 * @return String
	 */
	public String getEmirates(){
		return emirates;
	}

	/**
	 * @param emirates
	 */
	public void setEmirates( String emirates ){
		this.emirates = emirates;
	}

	/**
	 * @return String
	 */
	public String getArea(){
		return area;
	}

	/**
	 * @param area
	 */
	public void setArea( String area ){
		this.area = area;
	}

	
	/**
	 * @return String
	 */
	public String getBuildingname(){
		return buildingname;
	}

	/**
	 * @param buildingname
	 */
	public void setBuildingname( String buildingname ){
		this.buildingname = buildingname;
	}

	/**
	 * @return String
	 */
	public String getOtherDetails(){
		return otherDetails;
	}

	/**
	 * @param otherDetails
	 */
	public void setOtherDetails( String otherDetails ){
		this.otherDetails = otherDetails;
	}

	/**
	 * @return String
	 */
	public String getFlatVillaNo(){
		return flatVillaNo;
	}

	/**
	 * @param flatVillaNo
	 */
	public void setFlatVillaNo( String flatVillaNo ){
		this.flatVillaNo = flatVillaNo;
	}
	
	// Changes Home Revamp requirement 4.1 */
	public Short getBldTotalNoRooms() {
		return bldTotalNoRooms;
	}

	public void setBldTotalNoRooms(Short bldTotalNoRooms) {
		this.bldTotalNoRooms = bldTotalNoRooms;
	}

	public Short getBldTotalNoFloors() {
		return bldTotalNoFloors;
	}

	public void setBldTotalNoFloors(Short bldTotalNoFloors) {
		this.bldTotalNoFloors = bldTotalNoFloors;
	}
	// Changes Home Revamp requirement 4.1 */

	/**
	 * @return String
	 */
	public String getMortgageeName(){
		return mortgageeName;
	}

	/**
	 * @param mortgageeName
	 */
	public void setMortgageeName( String mortgageeName ){
		this.mortgageeName = mortgageeName;
	}

	public CoverVO getCoverCodes(){
		return coverCodes;
	}

	public void setCoverCodes( CoverVO coverCodes ){
		this.coverCodes = coverCodes;
	}

	public RiskVO getRiskCodes(){
		return riskCodes;
	}

	public void setRiskCodes( RiskVO riskCodes ){
		this.riskCodes = riskCodes;
	}

	public SumInsuredVO getSumInsured(){
		return sumInsured;
	}

	public void setSumInsured( SumInsuredVO sumInsured ){
		this.sumInsured = sumInsured;
	}

	
	/**
	 * @return
	 */
	public Short getGeoAreaCode(){
		return geoAreaCode;
	}

	/**
	 * @param geoAreaCode
	 */
	public void setGeoAreaCode( Short geoAreaCode ){
		this.geoAreaCode = geoAreaCode;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 * returns Object
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "emirates".equals( fieldName ) ) fieldValue = getEmirates();
		if( "area".equals( fieldName ) ) fieldValue = getArea();
		if( "buildingname".equals( fieldName ) ) fieldValue = getBuildingname();
		if( "ownershipStatus".equals( fieldName ) ) fieldValue = getOwnershipStatus();
		if( "otherDetails".equals( fieldName ) ) fieldValue = getOtherDetails();
		if( "flatVillaNo".equals( fieldName ) ) fieldValue = getFlatVillaNo();
		if( "mortgageeName".equals( fieldName ) ) fieldValue = getMortgageeName();
		if( "coverCodes".equals( fieldName ) ) fieldValue = getCoverCodes();
		if( "riskCodes".equals( fieldName ) ) fieldValue = getRiskCodes();
		if( "sumInsured".equals( fieldName ) ) fieldValue = getSumInsured();
		if( "typeOfProperty".equals( fieldName ) ) fieldValue = getTypeOfProperty();
		if( "vsd".equals( fieldName ) ) fieldValue = getVsd();
		if( "geoAreaCode".equals( fieldName )){
			fieldValue = getGeoAreaCode();
		}if( "totalNoRooms".equals( fieldName )){
			fieldValue = getTotalNoRooms();
		}
		if( "totalNoFloors".equals( fieldName )){
			fieldValue = getTotalNoFloors();
		}
		
		return fieldValue;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}

	public Date getVsd(){
		return vsd;
	}
	
	//Added for Informap changes
	public String getInforMapStatus() {
		return inforMapStatus;
	}

	public void setInforMapStatus(String inforMapStatus) {
		this.inforMapStatus = inforMapStatus;
	}
	public Short getTotalNoFloors() {
		return totalNoFloors;
	}

	public void setTotalNoFloors(Short totalNoFloors) {
		this.totalNoFloors = totalNoFloors;
	}

	public Short getTotalNoRooms() {
		return totalNoRooms;
	}

	public void setTotalNoRooms(Short totalNoRooms) {
		this.totalNoRooms = totalNoRooms;
	}
}
