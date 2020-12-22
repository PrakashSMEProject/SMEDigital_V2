package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "homeInsuranceDetails",
    "travelInsuranceDetails"
})
@XmlRootElement(name = "SearchQuotePolicyResponse", namespace = "http://com/rsaame/pas/b2c/ws")
public class SearchQuotePolicyResponse {
	
	@XmlElement(name="homeInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private HomeInsuranceDetails homeInsuranceDetails;
	@XmlElement(name="travelInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private TravelInsuranceDetails travelInsuranceDetails;


	public HomeInsuranceDetails getHomeInsuranceDetails() {
		return homeInsuranceDetails;
	}

	public void setHomeInsuranceDetails(HomeInsuranceDetails homeInsuranceDetails) {
		this.homeInsuranceDetails = homeInsuranceDetails;
	}

	public TravelInsuranceDetails getTravelInsuranceDetails() {
		return travelInsuranceDetails;
	}

	public void setTravelInsuranceDetails(
			TravelInsuranceDetails travelInsuranceDetails) {
		this.travelInsuranceDetails = travelInsuranceDetails;
	}

}
