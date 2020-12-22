package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class SurveyDetails extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer surveyRequired;
	private Date surveyDt;
	private Integer bldConstYear;
	private Double latitude;
	private Double longitude;
	private Integer noOfFloor;
	private Integer lowestFloor;
	private Integer occupiedFloors;
	private Date dispDate;
	private String resurveyPeriod;
	private Date resurveyReqDt;
	private Character dispensationAgreed;
	private Date specResurveyReqDt;
	private Integer surveyorOpinion;
	private Date SRFDate;
	private Integer pointSource;
	private Date rcpSentDt;
	private Date rcpConfirmDt;
	private Integer rcpStatus;
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "surveyRequired".equals( fieldName ) ) fieldValue = getSurveyRequired();
		if( "surveyDt".equals( fieldName ) ) fieldValue = getSurveyDt();
		if( "bldConstYear".equals( fieldName ) ) fieldValue = getBldConstYear();
		if( "latitude".equals( fieldName ) ) fieldValue = getLatitude();
		if( "longitude".equals( fieldName ) ) fieldValue = getLongitude();
		if( "noOfFloor".equals( fieldName ) ) fieldValue = getNoOfFloor();
		if( "lowestFloor".equals( fieldName ) ) fieldValue = getLowestFloor();
		if( "occupiedFloors".equals( fieldName ) ) fieldValue = getOccupiedFloors();
		if( "dispDate".equals( fieldName ) ) fieldValue = getDispDate();
		if( "resurveyPeriod".equals( fieldName ) ) fieldValue = getResurveyPeriod();
		if( "resurveyReqDt".equals( fieldName ) ) fieldValue = getResurveyReqDt();
		if( "dispensationAgreed".equals( fieldName ) ) fieldValue = getDispensationAgreed();
		if( "specResurveyReqDt".equals( fieldName ) ) fieldValue = getSpecResurveyReqDt();
		if( "surveyorOpinion".equals( fieldName ) ) fieldValue = getSurveyorOpinion();
		if( "SRFDate".equals( fieldName ) ) fieldValue = getSRFDate();
		if( "pointSource".equals( fieldName ) ) fieldValue = getPointSource();
		if( "rcpSentDt".equals( fieldName ) ) fieldValue = getRcpSentDt();
		if( "rcpConfirmDt".equals( fieldName ) ) fieldValue = getRcpConfirmDt();
		if( "rcpStatus".equals( fieldName ) ) fieldValue = getRcpStatus();
		
		return fieldValue;
	}
	
	public Integer getSurveyRequired(){
		
		return surveyRequired;
	}
	public void setSurveyRequired( Integer surveyRequired ){
		this.surveyRequired = surveyRequired;
	}
	public Date getSurveyDt(){
		return surveyDt;
	}
	public void setSurveyDt( Date surveyDt ){
		this.surveyDt = surveyDt;
	}
	public Integer getBldConstYear(){
		return bldConstYear;
	}
	public void setBldConstYear( Integer bldConstYear ){
		this.bldConstYear = bldConstYear;
	}
	public Double getLatitude(){
		return latitude;
	}
	public void setLatitude( Double latitude ){
		this.latitude = latitude;
	}
	public Double getLongitude(){
		return longitude;
	}
	public void setLongitude( Double longitude ){
		this.longitude = longitude;
	}
	public Integer getNoOfFloor(){
		return noOfFloor;
	}
	public void setNoOfFloor( Integer noOfFloor ){
		this.noOfFloor = noOfFloor;
	}
	public Integer getLowestFloor(){
		return lowestFloor;
	}
	public void setLowestFloor( Integer lowestFloor ){
		this.lowestFloor = lowestFloor;
	}
	public Integer getOccupiedFloors(){
		return occupiedFloors;
	}
	public void setOccupiedFloors( Integer occupiedFloors ){
		this.occupiedFloors = occupiedFloors;
	}
	public Date getDispDate(){
		return dispDate;
	}
	public void setDispDate( Date dispDate ){
		this.dispDate = dispDate;
	}
	public String getResurveyPeriod(){
		return resurveyPeriod;
	}
	public void setResurveyPeriod( String resurveyPeriod ){
		this.resurveyPeriod = resurveyPeriod;
	}
	public Date getResurveyReqDt(){
		return resurveyReqDt;
	}
	public void setResurveyReqDt( Date resurveyReqDt ){
		this.resurveyReqDt = resurveyReqDt;
	}
	public Character getDispensationAgreed(){
		return dispensationAgreed;
	}
	public void setDispensationAgreed( Character dispensationAgreed ){
		this.dispensationAgreed = dispensationAgreed;
	}
	public Date getSpecResurveyReqDt(){
		return specResurveyReqDt;
	}
	public void setSpecResurveyReqDt( Date specResurveyReqDt ){
		this.specResurveyReqDt = specResurveyReqDt;
	}
	public Integer getSurveyorOpinion(){
		return surveyorOpinion;
	}
	public void setSurveyorOpinion( Integer surveyorOpinion ){
		this.surveyorOpinion = surveyorOpinion;
	}
	public Date getSRFDate(){
		return SRFDate;
	}
	public void setSRFDate( Date sRFDate ){
		SRFDate = sRFDate;
	}
	public Integer getPointSource(){
		return pointSource;
	}
	public void setPointSource( Integer pointSource ){
		this.pointSource = pointSource;
	}
	public Date getRcpSentDt(){
		return rcpSentDt;
	}
	public void setRcpSentDt( Date rcpSentDt ){
		this.rcpSentDt = rcpSentDt;
	}
	public Date getRcpConfirmDt(){
		return rcpConfirmDt;
	}
	public void setRcpConfirmDt( Date rcpConfirmDt ){
		this.rcpConfirmDt = rcpConfirmDt;
	}

	public void setRcpStatus(Integer rcpStatus) {
		this.rcpStatus = rcpStatus;
	}

	public Integer getRcpStatus() {
		return rcpStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "SurveyDetails [surveyRequired=" + surveyRequired + ", surveyDt=" + surveyDt + ", bldConstYear=" + bldConstYear + ", latitude=" + latitude + ", longitude="
				+ longitude + ", noOfFloor=" + noOfFloor + ", lowestFloor=" + lowestFloor + ", occupiedFloors=" + occupiedFloors + ", dispDate=" + dispDate + ", resurveyPeriod="
				+ resurveyPeriod + ", resurveyReqDt=" + resurveyReqDt + ", dispensationAgreed=" + dispensationAgreed + ", specResurveyReqDt=" + specResurveyReqDt
				+ ", surveyorOpinion=" + surveyorOpinion + ", SRFDate=" + SRFDate + ", pointSource=" + pointSource + ", rcpSentDt=" + rcpSentDt + ", rcpConfirmDt=" + rcpConfirmDt
				+ ", rcpStatus=" + rcpStatus + "]";
	}
	
	
}
