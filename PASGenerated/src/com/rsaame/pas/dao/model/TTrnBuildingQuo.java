package com.rsaame.pas.dao.model;

// Generated Jan 18, 2012 5:30:03 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;

/**
 * TTrnBuildingQuo generated by hbm2java
 */
public class TTrnBuildingQuo extends POJOWrapper implements java.io.Serializable {

	private TTrnBuildingQuoId id;
	private Long bldPolicyId;
	private String bldNo;
	private Integer bldRskCode;
	private Short bldOccupancyCode;
	private Short bldConstructionCode;
	private Short bldZoneCode;
	private Short bldGeoareaCode;
	private Date bldStartDate;
	private Date bldEndDate;
	private String bldAAddress1;
	private String bldEAddress1;
	private String bldAAddress2;
	private String bldEAddress2;
	private String bldAAddress3;
	private String bldEAddress3;
	private String bldZip;
	private BigDecimal bldSumInsured;
	private BigDecimal bldMplFire;
	private BigDecimal bldMplFlood;
	private Short bldHazardCode;
	private Byte bldStatus;
	private Date bldValidityExpiryDate;
	private Integer bldDeclNo;
	private Date bldDeclDate;
	private String bldDesc;
	private Integer bldRskType;
	private Long bldEndtId;
	private String bldEName;
	private String bldAName;
	private Long bldWayNo;
	private Long bldBlockNo;
	private Integer bldDirCode;
	private Integer bldMunCode;
	private String bldEStreetName;
	private String bldAStreetName;
	private Integer bldRiRskCode;
	private Short bldWallType;
	private Short bldRoofType;
	private BigDecimal bldMplFirePerc;
	private BigDecimal bldMplFloodPerc;
	private Byte bldCoverIndicator;
	private Integer bldPreparedBy;
	private Date bldPreparedDt;
	private Integer bldModifiedBy;
	private Date bldModifiedDt;
	private Integer bldFixturesInd;
	private Short bldConstYr;
	private BigDecimal bldLatitude;
	private BigDecimal bldLongitude;
	private Short bldTotalNoFloors;
	
	private Short bldLowestFloor;
	private Short bldNoOccFloors;
	private Date bldDispDate;
	private String bldResurveyPeriod;
	private Date bldRoutineResurveyDt;
	private Date bldSpecificResurveyDt;
	private Character bldDispensationAgreed;
	private Date bldSrfDt;
	private Short bldSurveyorOpinion;
	private Short bldPointScore;
	private Date bldRcpDt;
	private Short bldRcpStatus;
	private Date bldRcpConfirmationDt;
	private String bldFreeZone;
	private Short bldOwnershipStatus;
	private String bldMorgatgeeName;
	//Added for Informap changes
	private String bldInforMapStatus;

	// 11645 - Home Digital API
	private Short bldTotalNoRooms;

	public TTrnBuildingQuo() {
	}

	public TTrnBuildingQuo(TTrnBuildingQuoId id, Date bldValidityExpiryDate) {
		this.id = id;
		this.bldValidityExpiryDate = bldValidityExpiryDate;
	}

	public TTrnBuildingQuo(TTrnBuildingQuoId id, Long bldPolicyId,
			String bldNo, Integer bldRskCode, Short bldOccupancyCode,
			Short bldConstructionCode, Short bldZoneCode, Short bldGeoareaCode,
			Date bldStartDate, Date bldEndDate, String bldAAddress1,
			String bldEAddress1, String bldAAddress2, String bldEAddress2,
			String bldAAddress3, String bldEAddress3, String bldZip,
			BigDecimal bldSumInsured, BigDecimal bldMplFire,
			BigDecimal bldMplFlood, Short bldHazardCode, Byte bldStatus,
			Date bldValidityExpiryDate, Integer bldDeclNo, Date bldDeclDate,
			String bldDesc, Integer bldRskType, Long bldEndtId,
			String bldEName, String bldAName, Long bldWayNo, Long bldBlockNo,
			Integer bldDirCode, Integer bldMunCode, String bldEStreetName,
			String bldAStreetName, Integer bldRiRskCode, Short bldWallType,
			Short bldRoofType, BigDecimal bldMplFirePerc,
			BigDecimal bldMplFloodPerc, Byte bldCoverIndicator,
			Integer bldPreparedBy, Date bldPreparedDt, Integer bldModifiedBy,
			Date bldModifiedDt, Integer bldFixturesInd, Short bldConstYr,
			BigDecimal bldLatitude, BigDecimal bldLongitude,
			Short bldTotalNoFloors, Short bldLowestFloor, Short bldNoOccFloors,
			Date bldDispDate, String bldResurveyPeriod,
			Date bldRoutineResurveyDt, Date bldSpecificResurveyDt,
			Character bldDispensationAgreed, Date bldSrfDt,
			Short bldSurveyorOpinion, Short bldPointScore, Date bldRcpDt,
			Short bldRcpStatus, Date bldRcpConfirmationDt, String bldFreeZone,Short bldTotalNoRooms) {
		this.id = id;
		this.bldPolicyId = bldPolicyId;
		this.bldNo = bldNo;
		this.bldRskCode = bldRskCode;
		this.bldOccupancyCode = bldOccupancyCode;
		this.bldConstructionCode = bldConstructionCode;
		this.bldZoneCode = bldZoneCode;
		this.bldGeoareaCode = bldGeoareaCode;
		this.bldStartDate = bldStartDate;
		this.bldEndDate = bldEndDate;
		this.bldAAddress1 = bldAAddress1;
		this.bldEAddress1 = bldEAddress1;
		this.bldAAddress2 = bldAAddress2;
		this.bldEAddress2 = bldEAddress2;
		this.bldAAddress3 = bldAAddress3;
		this.bldEAddress3 = bldEAddress3;
		this.bldZip = bldZip;
		this.bldSumInsured = bldSumInsured;
		this.bldMplFire = bldMplFire;
		this.bldMplFlood = bldMplFlood;
		this.bldHazardCode = bldHazardCode;
		this.bldStatus = bldStatus;
		this.bldValidityExpiryDate = bldValidityExpiryDate;
		this.bldDeclNo = bldDeclNo;
		this.bldDeclDate = bldDeclDate;
		this.bldDesc = bldDesc;
		this.bldRskType = bldRskType;
		this.bldEndtId = bldEndtId;
		this.bldEName = bldEName;
		this.bldAName = bldAName;
		this.bldWayNo = bldWayNo;
		this.bldBlockNo = bldBlockNo;
		this.bldDirCode = bldDirCode;
		this.bldMunCode = bldMunCode;
		this.bldEStreetName = bldEStreetName;
		this.bldAStreetName = bldAStreetName;
		this.bldRiRskCode = bldRiRskCode;
		this.bldWallType = bldWallType;
		this.bldRoofType = bldRoofType;
		this.bldMplFirePerc = bldMplFirePerc;
		this.bldMplFloodPerc = bldMplFloodPerc;
		this.bldCoverIndicator = bldCoverIndicator;
		this.bldPreparedBy = bldPreparedBy;
		this.bldPreparedDt = bldPreparedDt;
		this.bldModifiedBy = bldModifiedBy;
		this.bldModifiedDt = bldModifiedDt;
		this.bldFixturesInd = bldFixturesInd;
		this.bldConstYr = bldConstYr;
		this.bldLatitude = bldLatitude;
		this.bldLongitude = bldLongitude;
		this.bldTotalNoFloors = bldTotalNoFloors;
		this.bldLowestFloor = bldLowestFloor;
		this.bldNoOccFloors = bldNoOccFloors;
		this.bldDispDate = bldDispDate;
		this.bldResurveyPeriod = bldResurveyPeriod;
		this.bldRoutineResurveyDt = bldRoutineResurveyDt;
		this.bldSpecificResurveyDt = bldSpecificResurveyDt;
		this.bldDispensationAgreed = bldDispensationAgreed;
		this.bldSrfDt = bldSrfDt;
		this.bldSurveyorOpinion = bldSurveyorOpinion;
		this.bldPointScore = bldPointScore;
		this.bldRcpDt = bldRcpDt;
		this.bldRcpStatus = bldRcpStatus;
		this.bldRcpConfirmationDt = bldRcpConfirmationDt;
		this.bldFreeZone = bldFreeZone;
		this.bldTotalNoRooms=bldTotalNoRooms;
	}

	
	
	

	public TTrnBuildingQuoId getId() {
		return this.id;
	}

	public void setId(TTrnBuildingQuoId id) {
		this.id = id;
	}

	public Long getBldPolicyId() {
		return this.bldPolicyId;
	}

	public void setBldPolicyId(Long bldPolicyId) {
		this.bldPolicyId = bldPolicyId;
	}

	public String getBldNo() {
		return this.bldNo;
	}

	public void setBldNo(String bldNo) {
		this.bldNo = bldNo;
	}

	public Integer getBldRskCode() {
		return this.bldRskCode;
	}

	public void setBldRskCode(Integer bldRskCode) {
		this.bldRskCode = bldRskCode;
	}

	public Short getBldOccupancyCode() {
		return this.bldOccupancyCode;
	}

	public void setBldOccupancyCode(Short bldOccupancyCode) {
		this.bldOccupancyCode = bldOccupancyCode;
	}

	public Short getBldConstructionCode() {
		return this.bldConstructionCode;
	}

	public void setBldConstructionCode(Short bldConstructionCode) {
		this.bldConstructionCode = bldConstructionCode;
	}

	public Short getBldZoneCode() {
		return this.bldZoneCode;
	}

	public void setBldZoneCode(Short bldZoneCode) {
		this.bldZoneCode = bldZoneCode;
	}

	public Short getBldGeoareaCode() {
		return this.bldGeoareaCode;
	}

	public void setBldGeoareaCode(Short bldGeoareaCode) {
		this.bldGeoareaCode = bldGeoareaCode;
	}

	public Date getBldStartDate() {
		return this.bldStartDate;
	}

	public void setBldStartDate(Date bldStartDate) {
		this.bldStartDate = bldStartDate;
	}

	public Date getBldEndDate() {
		return this.bldEndDate;
	}

	public void setBldEndDate(Date bldEndDate) {
		this.bldEndDate = bldEndDate;
	}

	public String getBldAAddress1() {
		return this.bldAAddress1;
	}

	public void setBldAAddress1(String bldAAddress1) {
		this.bldAAddress1 = bldAAddress1;
	}

	public String getBldEAddress1() {
		return this.bldEAddress1;
	}

	public void setBldEAddress1(String bldEAddress1) {
		this.bldEAddress1 = bldEAddress1;
	}

	public String getBldAAddress2() {
		return this.bldAAddress2;
	}

	public void setBldAAddress2(String bldAAddress2) {
		this.bldAAddress2 = bldAAddress2;
	}

	public String getBldEAddress2() {
		return this.bldEAddress2;
	}

	public void setBldEAddress2(String bldEAddress2) {
		this.bldEAddress2 = bldEAddress2;
	}

	public String getBldAAddress3() {
		return this.bldAAddress3;
	}

	public void setBldAAddress3(String bldAAddress3) {
		this.bldAAddress3 = bldAAddress3;
	}

	public String getBldEAddress3() {
		return this.bldEAddress3;
	}

	public void setBldEAddress3(String bldEAddress3) {
		this.bldEAddress3 = bldEAddress3;
	}

	public String getBldZip() {
		return this.bldZip;
	}

	public void setBldZip(String bldZip) {
		this.bldZip = bldZip;
	}

	public BigDecimal getBldSumInsured() {
		return this.bldSumInsured;
	}

	public void setBldSumInsured(BigDecimal bldSumInsured) {
		this.bldSumInsured = bldSumInsured;
	}

	public BigDecimal getBldMplFire() {
		return this.bldMplFire;
	}

	public void setBldMplFire(BigDecimal bldMplFire) {
		this.bldMplFire = bldMplFire;
	}

	public BigDecimal getBldMplFlood() {
		return this.bldMplFlood;
	}

	public void setBldMplFlood(BigDecimal bldMplFlood) {
		this.bldMplFlood = bldMplFlood;
	}

	public Short getBldHazardCode() {
		return this.bldHazardCode;
	}

	public void setBldHazardCode(Short bldHazardCode) {
		this.bldHazardCode = bldHazardCode;
	}

	public Byte getBldStatus() {
		return this.bldStatus;
	}

	public void setBldStatus(Byte bldStatus) {
		this.bldStatus = bldStatus;
	}

	public Date getBldValidityExpiryDate() {
		return this.bldValidityExpiryDate;
	}

	public void setBldValidityExpiryDate(Date bldValidityExpiryDate) {
		this.bldValidityExpiryDate = bldValidityExpiryDate;
	}

	public Integer getBldDeclNo() {
		return this.bldDeclNo;
	}

	public void setBldDeclNo(Integer bldDeclNo) {
		this.bldDeclNo = bldDeclNo;
	}

	public Date getBldDeclDate() {
		return this.bldDeclDate;
	}

	public void setBldDeclDate(Date bldDeclDate) {
		this.bldDeclDate = bldDeclDate;
	}

	public String getBldDesc() {
		return this.bldDesc;
	}

	public void setBldDesc(String bldDesc) {
		this.bldDesc = bldDesc;
	}

	public Integer getBldRskType() {
		return this.bldRskType;
	}

	public void setBldRskType(Integer bldRskType) {
		this.bldRskType = bldRskType;
	}

	public Long getBldEndtId() {
		return this.bldEndtId;
	}

	public void setBldEndtId(Long bldEndtId) {
		this.bldEndtId = bldEndtId;
	}

	public String getBldEName() {
		return this.bldEName;
	}

	public void setBldEName(String bldEName) {
		this.bldEName = bldEName;
	}

	public String getBldAName() {
		return this.bldAName;
	}

	public void setBldAName(String bldAName) {
		this.bldAName = bldAName;
	}

	public Long getBldWayNo() {
		return this.bldWayNo;
	}

	public void setBldWayNo(Long bldWayNo) {
		this.bldWayNo = bldWayNo;
	}

	public Long getBldBlockNo() {
		return this.bldBlockNo;
	}

	public void setBldBlockNo(Long bldBlockNo) {
		this.bldBlockNo = bldBlockNo;
	}

	public Integer getBldDirCode() {
		return this.bldDirCode;
	}

	public void setBldDirCode(Integer bldDirCode) {
		this.bldDirCode = bldDirCode;
	}

	public Integer getBldMunCode() {
		return this.bldMunCode;
	}

	public void setBldMunCode(Integer bldMunCode) {
		this.bldMunCode = bldMunCode;
	}

	public String getBldEStreetName() {
		return this.bldEStreetName;
	}

	public void setBldEStreetName(String bldEStreetName) {
		this.bldEStreetName = bldEStreetName;
	}

	public String getBldAStreetName() {
		return this.bldAStreetName;
	}

	public void setBldAStreetName(String bldAStreetName) {
		this.bldAStreetName = bldAStreetName;
	}

	public Integer getBldRiRskCode() {
		return this.bldRiRskCode;
	}

	public void setBldRiRskCode(Integer bldRiRskCode) {
		this.bldRiRskCode = bldRiRskCode;
	}

	public Short getBldWallType() {
		return this.bldWallType;
	}

	public void setBldWallType(Short bldWallType) {
		this.bldWallType = bldWallType;
	}

	public Short getBldRoofType() {
		return this.bldRoofType;
	}

	public void setBldRoofType(Short bldRoofType) {
		this.bldRoofType = bldRoofType;
	}

	public BigDecimal getBldMplFirePerc() {
		return this.bldMplFirePerc;
	}

	public void setBldMplFirePerc(BigDecimal bldMplFirePerc) {
		this.bldMplFirePerc = bldMplFirePerc;
	}

	public BigDecimal getBldMplFloodPerc() {
		return this.bldMplFloodPerc;
	}

	public void setBldMplFloodPerc(BigDecimal bldMplFloodPerc) {
		this.bldMplFloodPerc = bldMplFloodPerc;
	}

	public Byte getBldCoverIndicator() {
		return this.bldCoverIndicator;
	}

	public void setBldCoverIndicator(Byte bldCoverIndicator) {
		this.bldCoverIndicator = bldCoverIndicator;
	}

	public Integer getBldPreparedBy() {
		return this.bldPreparedBy;
	}

	public void setBldPreparedBy(Integer bldPreparedBy) {
		this.bldPreparedBy = bldPreparedBy;
	}

	public Date getBldPreparedDt() {
		return this.bldPreparedDt;
	}

	public void setBldPreparedDt(Date bldPreparedDt) {
		this.bldPreparedDt = bldPreparedDt;
	}

	public Integer getBldModifiedBy() {
		return this.bldModifiedBy;
	}

	public void setBldModifiedBy(Integer bldModifiedBy) {
		this.bldModifiedBy = bldModifiedBy;
	}

	public Date getBldModifiedDt() {
		return this.bldModifiedDt;
	}

	public void setBldModifiedDt(Date bldModifiedDt) {
		this.bldModifiedDt = bldModifiedDt;
	}

	public Integer getBldFixturesInd() {
		return this.bldFixturesInd;
	}

	public void setBldFixturesInd(Integer bldFixturesInd) {
		this.bldFixturesInd = bldFixturesInd;
	}

	public Short getBldConstYr() {
		return this.bldConstYr;
	}

	public void setBldConstYr(Short bldConstYr) {
		this.bldConstYr = bldConstYr;
	}

	public BigDecimal getBldLatitude() {
		return this.bldLatitude;
	}

	public void setBldLatitude(BigDecimal bldLatitude) {
		this.bldLatitude = bldLatitude;
	}

	public BigDecimal getBldLongitude() {
		return this.bldLongitude;
	}

	public void setBldLongitude(BigDecimal bldLongitude) {
		this.bldLongitude = bldLongitude;
	}

	public Short getBldTotalNoFloors() {
		return this.bldTotalNoFloors;
	}

	public void setBldTotalNoFloors(Short bldTotalNoFloors) {
		this.bldTotalNoFloors = bldTotalNoFloors;
	}
	
	
	public Short getBldLowestFloor() {
		return this.bldLowestFloor;
	}

	public void setBldLowestFloor(Short bldLowestFloor) {
		this.bldLowestFloor = bldLowestFloor;
	}

	public Short getBldNoOccFloors() {
		return this.bldNoOccFloors;
	}

	public void setBldNoOccFloors(Short bldNoOccFloors) {
		this.bldNoOccFloors = bldNoOccFloors;
	}

	public Date getBldDispDate() {
		return this.bldDispDate;
	}

	public void setBldDispDate(Date bldDispDate) {
		this.bldDispDate = bldDispDate;
	}

	public String getBldResurveyPeriod() {
		return this.bldResurveyPeriod;
	}

	public void setBldResurveyPeriod(String bldResurveyPeriod) {
		this.bldResurveyPeriod = bldResurveyPeriod;
	}

	public Date getBldRoutineResurveyDt() {
		return this.bldRoutineResurveyDt;
	}

	public void setBldRoutineResurveyDt(Date bldRoutineResurveyDt) {
		this.bldRoutineResurveyDt = bldRoutineResurveyDt;
	}

	public Date getBldSpecificResurveyDt() {
		return this.bldSpecificResurveyDt;
	}

	public void setBldSpecificResurveyDt(Date bldSpecificResurveyDt) {
		this.bldSpecificResurveyDt = bldSpecificResurveyDt;
	}

	public Character getBldDispensationAgreed() {
		return this.bldDispensationAgreed;
	}

	public void setBldDispensationAgreed(Character bldDispensationAgreed) {
		this.bldDispensationAgreed = bldDispensationAgreed;
	}

	public Date getBldSrfDt() {
		return this.bldSrfDt;
	}

	public void setBldSrfDt(Date bldSrfDt) {
		this.bldSrfDt = bldSrfDt;
	}

	public Short getBldSurveyorOpinion() {
		return this.bldSurveyorOpinion;
	}

	public void setBldSurveyorOpinion(Short bldSurveyorOpinion) {
		this.bldSurveyorOpinion = bldSurveyorOpinion;
	}

	public Short getBldPointScore() {
		return this.bldPointScore;
	}

	public void setBldPointScore(Short bldPointScore) {
		this.bldPointScore = bldPointScore;
	}

	public Date getBldRcpDt() {
		return this.bldRcpDt;
	}

	public void setBldRcpDt(Date bldRcpDt) {
		this.bldRcpDt = bldRcpDt;
	}

	public Short getBldRcpStatus() {
		return this.bldRcpStatus;
	}

	public void setBldRcpStatus(Short bldRcpStatus) {
		this.bldRcpStatus = bldRcpStatus;
	}

	public Date getBldRcpConfirmationDt() {
		return this.bldRcpConfirmationDt;
	}

	public void setBldRcpConfirmationDt(Date bldRcpConfirmationDt) {
		this.bldRcpConfirmationDt = bldRcpConfirmationDt;
	}

	public String getBldFreeZone() {
		return this.bldFreeZone;
	}

	public void setBldFreeZone(String bldFreeZone) {
		this.bldFreeZone = bldFreeZone;
	}

	@Override
	public int getStatus(){
		return getBldStatus();
	}
	
	@Override
	public void setEndtId( Long endtId ){
		setBldEndtId( endtId );
	}
	
	@Override
	public POJOId getPOJOId(){
		return this.getId();
	}
	
	/* Set the id from POJOId.*/
	@Override
	public void setPOJOId( POJOId id ){
		setId( (TTrnBuildingQuoId) id );
	}
	
	@Override
	public void setValidityExpiryDate( Date ved ){
		setBldValidityExpiryDate( ved );
	}
	
	@Override
	public void setValidityStartDate( Date vsd ){
		//setBldStartDate( vsd );
		//Commented above line and added new line -- Fix for building start date going wrongly during versioning
		if(!Utils.isEmpty(getId())) getId().setBldValidityStartDate(vsd);
		
	}
	
	@Override
	public void setStatus(Integer status) {
		setBldStatus( Integer.valueOf( status ).byteValue() );
	}

	/* Method not applicable. */
	@Override
	public void setEndtNo(Long endtNo) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	@Override
	public void setPreparedBy(Integer preparedBy) {
		setBldPreparedBy(preparedBy);
	}

	@Override
	public void setPreparedDt(Date preparedDt) {
		setBldPreparedDt(preparedDt);
	}

	@Override
	public void setModifiedBy(Integer modifiedBy) {
		setBldModifiedBy(modifiedBy);
	}

	@Override
	public void setModifiedDt(Date modifiedDt) {
		setBldModifiedDt(modifiedDt);		
	}

	/* Set TTrnBuildingQuoId from POJOWrapperId.*/
	@Override
	public void setPOJOWrapperId( POJOWrapperId id ){
		setId( (TTrnBuildingQuoId) id );
	}

	/* Return TTrnBuildingQuoId.*/
	@Override
	public POJOWrapperId getPOJOWrapperId(){
		return getId();
	}

	@Override
	public void setStatus( int status ){
		setBldStatus( Integer.valueOf( status ).byteValue() );
	}

	public String getBldMorgatgeeName(){
		return bldMorgatgeeName;
	}

	public void setBldMorgatgeeName( String bldMorgatgeeName ){
		this.bldMorgatgeeName = bldMorgatgeeName;
	}
	
	/**
	 * @return the bldOwnershipStatus
	 */
	public Short getBldOwnershipStatus(){
		return bldOwnershipStatus;
	}

	/**
	 * @param bldOwnershipStatus the bldOwnershipStatus to set
	 */
	public void setBldOwnershipStatus( Short bldOwnershipStatus ){
		this.bldOwnershipStatus = bldOwnershipStatus;
	}

	@Override
	public Integer getPreparedBy(){
		return getBldPreparedBy();
	}

	@Override
	public Date getPreparedDt(){
		return getBldPreparedDt();
	}

	@Override
	public Integer getModifiedBy(){
		return getBldModifiedBy();
	}

	@Override
	public Date getModifiedDt(){
		return getBldModifiedDt();
	}

	
	
	@Override
	public void setPreparedDate( Date preparedDate ){
		setBldPreparedDt( preparedDate );
	}
	
	@Override
	public Date getPreparedDate(){
		return getBldPreparedDt();
	}
	
	//Added for Informap changes
	
	public String getBldInforMapStatus() {
		return bldInforMapStatus;
	}

	public void setBldInforMapStatus(String bldInforMapStatus) {
		this.bldInforMapStatus = bldInforMapStatus;
	}
	public Short getBldTotalNoRooms() {
		return bldTotalNoRooms;
	}

	public void setBldTotalNoRooms(Short bldTotalNoRooms) {
		this.bldTotalNoRooms = bldTotalNoRooms;
	}
}
