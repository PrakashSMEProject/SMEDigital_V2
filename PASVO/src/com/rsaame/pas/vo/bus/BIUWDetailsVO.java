/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author m1019703
 *
 */
public class BIUWDetailsVO extends UWDetails {

	private	Double emlPrc;
	private Double emlSI;
	private	SimpleUWDetailsVO uwMinDetails; // // is it required ?
	private static final long serialVersionUID = 1L;
	
	
	public Double getEmlPrc(){
		return emlPrc;
	}
	public void setEmlPrc( Double emlPrc ){
		this.emlPrc = emlPrc;
	}
	public Double getEmlSI(){
		return emlSI;
	}
	public void setEmlSI( Double emlSI ){
		this.emlSI = emlSI;
	}
	
	public SimpleUWDetailsVO getUwMinDetails(){
		return uwMinDetails;
	}
	public void setUwMinDetails( SimpleUWDetailsVO uwMinDetails ){
		this.uwMinDetails = uwMinDetails;
	}
	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
