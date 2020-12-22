package com.rsaame.pas.dao.model;

import java.math.BigDecimal;
import java.sql.Date;

public class TMasCurrency {
	
	Integer code;
	String arabicDesc;
	String engDesc;
	String arabicShortDesc;
	String engShortDesc;
	Integer cty;
	BigDecimal exchangeRate;
	String minEDesc;
	String minADesc;
	Integer minConvFactor;
	Date preparedDt;
	Date modifiedDt;
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getArabicDesc() {
		return arabicDesc;
	}
	public void setArabicDesc(String arabicDesc) {
		this.arabicDesc = arabicDesc;
	}
	public String getEngDesc() {
		return engDesc;
	}
	public void setEngDesc(String engDesc) {
		this.engDesc = engDesc;
	}
	public String getArabicShortDesc() {
		return arabicShortDesc;
	}
	public void setArabicShortDesc(String arabicShortDesc) {
		this.arabicShortDesc = arabicShortDesc;
	}
	public String getEngShortDesc() {
		return engShortDesc;
	}
	public void setEngShortDesc(String engShortDesc) {
		this.engShortDesc = engShortDesc;
	}
	public Integer getCty() {
		return cty;
	}
	public void setCty(Integer cty) {
		this.cty = cty;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getMinEDesc() {
		return minEDesc;
	}
	public void setMinEDesc(String minEDesc) {
		this.minEDesc = minEDesc;
	}
	public String getMinADesc() {
		return minADesc;
	}
	public void setMinADesc(String minADesc) {
		this.minADesc = minADesc;
	}
	public Integer getMinConvFactor() {
		return minConvFactor;
	}
	public void setMinConvFactor(Integer minConvFactor) {
		this.minConvFactor = minConvFactor;
	}
	public Date getPreparedDt() {
		return preparedDt;
	}
	public void setPreparedDt(Date preparedDt) {
		this.preparedDt = preparedDt;
	}
	public Date getModifiedDt() {
		return modifiedDt;
	}
	public void setModifiedDt(Date modifiedDt) {
		this.modifiedDt = modifiedDt;
	}
	
	

}
