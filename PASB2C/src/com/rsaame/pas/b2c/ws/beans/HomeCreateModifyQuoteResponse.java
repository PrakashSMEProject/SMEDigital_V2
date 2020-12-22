package com.rsaame.pas.b2c.ws.beans;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "quoteId",
    "premiumValue"
})
@XmlRootElement(name = "HomeCreateModifyQuoteResponse", namespace = "http://com/rsaame/pas/b2c/ws")
public class HomeCreateModifyQuoteResponse {

	@XmlElement(name="quoteId", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Long quoteId;
	@XmlElement(name="premiumValue", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private BigDecimal premiumValue;
	
	public Long getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}
	public BigDecimal getPremiumValue() {
		return premiumValue;
	}
	public void setPremiumValue(BigDecimal premiumValue) {
		this.premiumValue = premiumValue;
	}


}
