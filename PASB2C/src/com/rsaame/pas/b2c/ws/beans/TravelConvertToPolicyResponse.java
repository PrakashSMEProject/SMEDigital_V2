package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "travelInsuranceDetails"
})
@XmlRootElement(name = "TravelConvertToPolicyResponse", namespace = "http://com/rsaame/pas/b2c/ws")
public class TravelConvertToPolicyResponse {

	@XmlElement(name="travelInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	TravelInsuranceDetails travelInsuranceDetails;

	public TravelInsuranceDetails getTravelInsuranceDetails() {
		return travelInsuranceDetails;
	}

	public void setTravelInsuranceDetails(
			TravelInsuranceDetails travelInsuranceDetails) {
		this.travelInsuranceDetails = travelInsuranceDetails;
	}
	
}
