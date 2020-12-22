package com.rsaame.pas.vo.bus;

public class DeteriorationOfStockUWDetailsVO extends UWDetails{
	private Double emlPercentage;
	private Double emlSI;
	private Double emlBIPercentage;
	private Double emlBI;
	public Double getEmlPercentage(){
		return emlPercentage;
	}
	public void setEmlPercentage( Double emlPercentage ){
		this.emlPercentage = emlPercentage;
	}
	public Double getEmlSI(){
		return emlSI;
	}
	public void setEmlSI( Double emlSI ){
		this.emlSI = emlSI;
	}
	public Double getEmlBIPercentage(){
		return emlBIPercentage;
	}
	public void setEmlBIPercentage( Double emlBIPercentage ){
		this.emlBIPercentage = emlBIPercentage;
	}
	public Double getEmlBI(){
		return emlBI;
	}
	public void setEmlBI( Double emlBI ){
		this.emlBI = emlBI;
	}
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		
		if( "emlPercentage".equals( fieldName ) ) fieldValue = getEmlPercentage();
		if( "emlSI".equals( fieldName ) ) fieldValue = getEmlSI();
		if( "emlBIPercentage".equals( fieldName ) ) fieldValue = getEmlBIPercentage();
		if( "emlBI".equals( fieldName ) ) fieldValue = getEmlBI();
		
		return fieldValue;
	}
	
	
}
