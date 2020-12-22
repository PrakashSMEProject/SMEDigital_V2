package com.rsaame.pas.b2c.ws.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "travelInsuranceDetails",
    "isCreate"
})
@XmlRootElement(name = "TravelConvertToPolicyRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class TravelRenewPolicyResponse {

	private Long policyId;
	private List<CoverDetails> coverDetails;
	private PremiumDetails premiumDetails ;
	
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public List<CoverDetails> getCoverDetails() {
		return coverDetails;
	}
	public void setCoverDetails(List<CoverDetails> coverDetails) {
		this.coverDetails = coverDetails;
	}
	public PremiumDetails getPremiumDetails() {
		return premiumDetails;
	}
	public void setPremiumDetails(PremiumDetails premiumDetails) {
		this.premiumDetails = premiumDetails;
	}
	
}
