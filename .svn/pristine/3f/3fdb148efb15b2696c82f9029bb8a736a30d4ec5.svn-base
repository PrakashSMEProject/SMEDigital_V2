package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class ReminderVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	
	private String preparedBy;
	private Date preparedDate;
	private String targetdate;
	private String subject;
	private String comments;
	private String remStatus;
	private String remType;
	private String remTypeId;
	private String remSrlNo;
	private String remTime;
	private boolean lapsedDairyItems;
	private Integer sLNumber;
	private boolean edited;
	private boolean emailReq;
	private Date tempPreparedDate;
	private String preparedDateString;

	public String getPreparedDateString(){
		return preparedDateString;
	}

	public void setPreparedDateString( String preparedDateString ){
		this.preparedDateString = preparedDateString;
	}

	@Override
	public Object getFieldValue(String fieldName) {

		Object fieldValue = null;

		if( "preparedBy".equals( fieldName ) ) fieldValue = getPreparedBy();
		if( "preparedDate".equals( fieldName ) ) fieldValue = getPreparedDate();
		if( "targetdate".equals( fieldName ) ) fieldValue = getTargetdate();
		if( "subject".equals( fieldName ) ) fieldValue = getSubject();
		if( "comments".equals( fieldName ) ) fieldValue = getComments();
		if( "remStatus".equals( fieldName ) ) fieldValue = getRemStatus();
		if( "remType".equals( fieldName ) ) fieldValue = getRemType();
		if( "remTypeId".equals( fieldName ) ) fieldValue = getRemTypeId();
		if( "remSrlNo".equals( fieldName ) ) fieldValue = getRemSrlNo();
		if( "remTime".equals( fieldName ) ) fieldValue = getRemTime();
		if( "lapsedDairyItems".equals( fieldName ) ) fieldValue = isLapsedDairyItems();
		if( "sLNumber".equals( fieldName ) ) fieldValue = getsLNumber();
		if( "edited".equals( fieldName ) ) fieldValue = isEdited();
		if( "emailReq".equals( fieldName ) ) fieldValue = isEmail();
		if( "tempPreparedDate".equals( fieldName ) ) fieldValue = getTempPreparedDate();
		if( "preparedDateString".equals( fieldName ) ) fieldValue = getPreparedDateString();
		return fieldValue;
	}
	
	public Date getTempPreparedDate() {
		return tempPreparedDate;
	}


	public void setTempPreparedDate(Date tempPreparedDate) {
		this.tempPreparedDate = tempPreparedDate;
	}


	public Integer getsLNumber() {
		return sLNumber;
	}
	
	public void setsLNumber(Integer sLNumber) {
		this.sLNumber = sLNumber;
	}


	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public Date getPreparedDate() {
		return preparedDate;
	}

	public boolean isLapsedDairyItems() {
		return lapsedDairyItems;
	}

	public void setLapsedDairyItems(boolean lapsedDairyItems) {
		this.lapsedDairyItems = lapsedDairyItems;
	}
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	public String getTargetdate() {
		return targetdate;
	}

	public void setTargetdate(String targetdate) {
		this.targetdate = targetdate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRemStatus() {
		return remStatus;
	}

	public void setRemStatus(String remStatus) {
		this.remStatus = remStatus;
	}

	public String getRemType() {
		return remType;
	}

	public void setRemType(String remType) {
		this.remType = remType;
	}

	public String getRemTypeId() {
		return remTypeId;
	}

	public void setRemTypeId(String remTypeId) {
		this.remTypeId = remTypeId;
	}

	public String getRemSrlNo() {
		return remSrlNo;
	}

	public void setRemSrlNo(String remSrlNo) {
		this.remSrlNo = remSrlNo;
	}

	public String getRemTime() {
		return remTime;
	}

	public void setRemTime(String remTime) {
		this.remTime = remTime;
	}

	public boolean isEdited() {
		return edited;
	}


	public void setEdited(boolean edited) {
		this.edited = edited;
	}


	public boolean isEmail() {
		return emailReq;
	}


	public void setEmail(boolean emailReq) {
		this.emailReq = emailReq;
	}

}
