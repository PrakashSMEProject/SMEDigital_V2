package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "travelInsuranceDetails",
    "isCreate"
})
@XmlRootElement(name = "TravelConvertToPolicyRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class TravelConvertToPolicyRequest {

	@XmlElement(name="travelInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private TravelInsuranceDetails travelInsuranceDetails;
	
	@XmlElement(name="isCreate", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Boolean isCreate;
	
	public void setIsCreate(Boolean isCreate) {
		this.isCreate = isCreate;
	}
	public Boolean getIsCreate() {
		return isCreate;
	}
	public void setTravelInsuranceDetails(TravelInsuranceDetails travelInsuranceDetails) {
		this.travelInsuranceDetails = travelInsuranceDetails;
	}
	public TravelInsuranceDetails getTravelInsuranceDetails() {
		return travelInsuranceDetails;
	}
}
