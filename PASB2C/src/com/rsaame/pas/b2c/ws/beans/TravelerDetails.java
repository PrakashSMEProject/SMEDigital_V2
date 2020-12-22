package com.rsaame.pas.b2c.ws.beans;

import java.math.BigDecimal;
import java.util.Date;

public class TravelerDetails {

	private String name;
	private Date dateOfBirth;
	private Byte relation;
	private Short nationality;
	private BigDecimal gprId;
	private Date vsd;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Byte getRelation() {
		return relation;
	}
	public void setRelation(Byte relation) {
		this.relation = relation;
	}
	public Short getNationality() {
		return nationality;
	}
	public void setNationality(Short nationality) {
		this.nationality = nationality;
	}
	public BigDecimal getGprId() {
		return gprId;
	}
	public void setGprId(BigDecimal gprId) {
		this.gprId = gprId;
	}
	public Date getVsd() {
		return vsd;
	}
	public void setVsd(Date vsd) {
		this.vsd = vsd;
	}
}
