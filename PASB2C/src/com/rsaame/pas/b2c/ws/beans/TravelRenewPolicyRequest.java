package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "travelInsuranceDetails",
    "isModified"
})
@XmlRootElement(name = "TravelRenewPolicyRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class TravelRenewPolicyRequest {

	@XmlElement(name="travelInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private TravelInsuranceDetails travelInsuranceDetails;
	
	@XmlElement(name="isModified", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Boolean isModified;

	public TravelInsuranceDetails getTravelInsuranceDetails() {
		return travelInsuranceDetails;
	}

	public void setTravelInsuranceDetails(
			TravelInsuranceDetails travelInsuranceDetails) {
		this.travelInsuranceDetails = travelInsuranceDetails;
	}

	public Boolean getIsModified() {
		return isModified;
	}

	public void setIsModified(Boolean isModified) {
		this.isModified = isModified;
	}
}
