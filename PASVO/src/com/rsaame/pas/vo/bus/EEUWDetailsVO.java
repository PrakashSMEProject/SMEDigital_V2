package com.rsaame.pas.vo.bus;

public class EEUWDetailsVO extends UWDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8174460265583240083L;
	/**
	 * 
	 */

	private Double emlPrc;
	private Double emlSI;
	private Double emlBIPrc;
	private Double emlBI;
	private SimpleUWDetailsVO uwMinDetails;

	public Double getEmlPrc() {
		return emlPrc;
	}

	public void setEmlPrc(Double emlPrc) {
		this.emlPrc = emlPrc;
	}

	public Double getEmlSI() {
		return emlSI;
	}

	public void setEmlSI(Double emlSI) {
		this.emlSI = emlSI;
	}

	public Double getEmlBIPrc() {
		return emlBIPrc;
	}

	public void setEmlBIPrc(Double emlBIPrc) {
		this.emlBIPrc = emlBIPrc;
	}

	public Double getEmlBI() {
		return emlBI;
	}

	public void setEmlBI(Double emlBI) {
		this.emlBI = emlBI;
	}

	public SimpleUWDetailsVO getUwMinDetails() {
		return uwMinDetails;
	}

	public void setUwMinDetails(SimpleUWDetailsVO uwMinDetails) {
		this.uwMinDetails = uwMinDetails;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		
		if( "emlPrc".equals( fieldName ) ) fieldValue = getEmlPrc();
		if( "emlSI".equals( fieldName ) ) fieldValue = getEmlSI();
		if( "emlBIPrc".equals( fieldName ) ) fieldValue = getEmlBIPrc();
		if( "emlBI".equals( fieldName ) ) fieldValue = getEmlBI();
		
		return fieldValue;
	}

}
