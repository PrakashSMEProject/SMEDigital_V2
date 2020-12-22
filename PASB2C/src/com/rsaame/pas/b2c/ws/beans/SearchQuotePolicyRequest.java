package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "searchType",
    "emailId",
    "idNumber"
})
@XmlRootElement(name = "SearchQuotePolicyRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class SearchQuotePolicyRequest {

	@XmlElement(name="searchType", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private String searchType;
	
	@XmlElement(name="emailId", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private String emailId;
	
	@XmlElement(name="idNumber", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Long idNumber;
	
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}
	
}
