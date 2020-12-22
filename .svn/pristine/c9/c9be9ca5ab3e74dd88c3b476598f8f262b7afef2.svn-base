
package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "coverDetailsList",
    "travelPackage"
})
@XmlRootElement(name = "ProductDetailsResponse", namespace = "http://com/rsaame/pas/b2c/ws")
public class ProductDetailsResponse {
	
	@XmlElement(name="coverDetailsList", required = false, namespace = "http://com/rsaame/pas/b2c/ws")
	private CoverDetailsList coverDetailsList;
	@XmlElement(name="travelPackage", required = false, namespace = "http://com/rsaame/pas/b2c/ws")
	private TravelPackage travelPackage;
	
	
	public CoverDetailsList getCoverDetailsList() {
		return coverDetailsList;
	}
	public void setCoverDetailsList(CoverDetailsList coverDetailsList) {
		this.coverDetailsList = coverDetailsList;
	}
	public TravelPackage getTravelPackage() {
		return travelPackage;
	}
	public void setTravelPackage(TravelPackage travelPackage) {
		this.travelPackage = travelPackage;
	}
	
}


