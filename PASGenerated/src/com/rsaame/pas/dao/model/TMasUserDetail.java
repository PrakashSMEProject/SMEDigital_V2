package com.rsaame.pas.dao.model;

// Generated Jun 11, 2014 11:57:55 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TMasUserDetail generated by hbm2java
 */
public class TMasUserDetail implements java.io.Serializable {

	private Integer userId;
	private String loginId;
	private Date dob;
	private String lastName;

	public TMasUserDetail() {
	}

	public TMasUserDetail(int userId) {
		this.userId = userId;
	}

	public TMasUserDetail(int userId, String loginId, Date dob, String lastName) {
		this.userId = userId;
		this.loginId = loginId;
		this.dob = dob;
		this.lastName = lastName;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
