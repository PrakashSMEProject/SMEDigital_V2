package com.rsaame.pas.cmn.currency;

import java.util.Map;

public class CurrencyDetails {
	
	private  String pfxSymbol ; // Leading currency symbol
	private  String sfxSymbol ; // Trailing currency symbol
	private  char decimalPoint = '.'; // Character for fractions 
	private  char groupSeparator = ','; // Character for 1000nds 
	private  String unitName ; // Name of monetary unit 
	private  String fractionName ; // Name of fraction unit 
	
	private  int scale = 1000; // defaulted to 3

	private  String currencyEName;
	private  String currencyNativeName;
	
	private  Map<Short, Integer> policyTypeScaleMap;

	public String getPfxSymbol() {
		return pfxSymbol;
	}

	public void setPfxSymbol(String pfxSymbol) {
		this.pfxSymbol = pfxSymbol;
	}

	public String getSfxSymbol() {
		return sfxSymbol;
	}

	public void setSfxSymbol(String sfxSymbol) {
		this.sfxSymbol = sfxSymbol;
	}

	public char getDecimalPoint() {
		return decimalPoint;
	}

	public void setDecimalPoint(char decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	public char getGroupSeparator() {
		return groupSeparator;
	}

	public void setGroupSeparator(char groupSeparator) {
		this.groupSeparator = groupSeparator;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFractionName() {
		return fractionName;
	}

	public void setFractionName(String fractionName) {
		this.fractionName = fractionName;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getCurrencyEName() {
		return currencyEName;
	}

	public void setCurrencyEName(String currencyEName) {
		this.currencyEName = currencyEName;
	}

	public String getCurrencyNativeName() {
		return currencyNativeName;
	}

	public void setCurrencyNativeName(String currencyNativeName) {
		this.currencyNativeName = currencyNativeName;
	}

	

	public Map<Short, Integer> getPolicyTypeScaleMap() {
		return policyTypeScaleMap;
	}

	public void setPolicyTypeScaleMap(Map<Short, Integer> policyTypeScaleMap) {
		this.policyTypeScaleMap = policyTypeScaleMap;
	}
	
	

}
