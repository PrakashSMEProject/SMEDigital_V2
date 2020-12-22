package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "homeInsuranceDetails"
})
@XmlRootElement(name = "HomeConvertToPolicyResponse", namespace = "http://com/rsaame/pas/b2c/ws")
public class HomeConvertToPolicyResponse {

	@XmlElement(name="homeInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	HomeInsuranceDetails homeInsuranceDetails;

	public HomeInsuranceDetails getHomeInsuranceDetails() {
		return homeInsuranceDetails;
	}

	public void setHomeInsuranceDetails(HomeInsuranceDetails homeInsuranceDetails) {
		this.homeInsuranceDetails = homeInsuranceDetails;
	}
}