package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "opIdentifier",
    "travelInsuranceDetails"
})
@XmlRootElement(name = "TravelCreateModifyQuoteRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class TravelCreateModifyQuoteRequest {
	
	@XmlElement(name="opIdentifier", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private String opIdentifier;
	@XmlElement(name="travelInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private TravelInsuranceDetails travelInsuranceDetails;
	
	public String getOpIdentifier() {
		return opIdentifier;
	}
	public void setOpIdentifier(String opIdentifier) {
		this.opIdentifier = opIdentifier;
	}
	public TravelInsuranceDetails getTravelInsuranceDetails() {
		return travelInsuranceDetails;
	}
	public void setTravelInsuranceDetails(
			TravelInsuranceDetails travelInsuranceDetails) {
		this.travelInsuranceDetails = travelInsuranceDetails;
	}

}
