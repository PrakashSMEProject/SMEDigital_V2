package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "homeInsuranceDetails",
    "isCreate"
})
@XmlRootElement(name = "HomeConvertToPolicyRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class HomeConvertToPolicyRequest {

	@XmlElement(name="homeInsuranceDetails", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private HomeInsuranceDetails homeInsuranceDetails;
	
	public HomeInsuranceDetails getHomeInsuranceDetails() {
		return homeInsuranceDetails;
	}

	public void setHomeInsuranceDetails(HomeInsuranceDetails homeInsuranceDetails) {
		this.homeInsuranceDetails = homeInsuranceDetails;
	}

	public Boolean getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Boolean isCreate) {
		this.isCreate = isCreate;
	}

	@XmlElement(name="isCreate", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Boolean isCreate;

	
}
