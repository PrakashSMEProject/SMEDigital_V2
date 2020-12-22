package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "opIdentifier",
    "homeInsuranceDetails"
})
@XmlRootElement(name = "HomeCreateModifyQuoteRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class HomeCreateModifyQuoteRequest {
	
	@XmlElement(name="opIdentifier", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private String opIdentifier;
	@XmlElement(name="homeInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private HomeInsuranceDetails homeInsuranceDetails;
	
	public String getOpIdentifier() {
		return opIdentifier;
	}
	public void setOpIdentifier(String opIdentifier) {
		this.opIdentifier = opIdentifier;
	}
	public HomeInsuranceDetails getHomeInsuranceDetails() {
		return homeInsuranceDetails;
	}
	public void setHomeInsuranceDetails(HomeInsuranceDetails homeInsuranceDetails) {
		this.homeInsuranceDetails = homeInsuranceDetails;
	}

}
