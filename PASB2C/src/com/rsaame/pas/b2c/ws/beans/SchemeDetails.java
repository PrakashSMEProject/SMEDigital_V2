package com.rsaame.pas.b2c.ws.beans;

import java.util.Date;

public class SchemeDetails {
	
	private Integer id;
	private Integer schemeCode;
	private String schemeName;
	private Integer tariffCode;
	private String tariffName;
	private String policyType;
	private Date effDate;
	private Date expiryDate;
	private String tariffRateType;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(Integer schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public Integer getTariffCode() {
		return tariffCode;
	}
	public void setTariffCode(Integer tariffCode) {
		this.tariffCode = tariffCode;
	}
	public String getTariffName() {
		return tariffName;
	}
	public void setTariffName(String tariffName) {
		this.tariffName = tariffName;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public Date getEffDate() {
		return effDate;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getTariffRateType() {
		return tariffRateType;
	}
	public void setTariffRateType(String tariffRateType) {
		this.tariffRateType = tariffRateType;
	}

}
