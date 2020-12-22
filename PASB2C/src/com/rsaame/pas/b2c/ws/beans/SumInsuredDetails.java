package com.rsaame.pas.b2c.ws.beans;

import java.util.Date;

public class SumInsuredDetails {

	private Double sumInsured;
	private Double deductible;
	private Long cash_Id;
	private Date vsd;
	/*private String eDesc;
	private String aDesc;*/
	private boolean isPromoCover;
	private Integer identifier;
	
	
	public Double getSumInsured() {
		return sumInsured;
	}
	public void setSumInsured(Double sumInsured) {
		this.sumInsured = sumInsured;
	}
	public Double getDeductible() {
		return deductible;
	}
	public void setDeductible(Double deductible) {
		this.deductible = deductible;
	}
	public Long getCash_Id() {
		return cash_Id;
	}
	public void setCash_Id(Long cash_Id) {
		this.cash_Id = cash_Id;
	}
	public Date getVsd() {
		return vsd;
	}
	public void setVsd(Date vsd) {
		this.vsd = vsd;
	}
	/*public String geteDesc() {
		return eDesc;
	}
	public void seteDesc(String eDesc) {
		this.eDesc = eDesc;
	}
	public String getaDesc() {
		return aDesc;
	}
	public void setaDesc(String aDesc) {
		this.aDesc = aDesc;
	}*/
	public boolean isPromoCover() {
		return isPromoCover;
	}
	public void setPromoCover(boolean isPromoCover) {
		this.isPromoCover = isPromoCover;
	}
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
}
