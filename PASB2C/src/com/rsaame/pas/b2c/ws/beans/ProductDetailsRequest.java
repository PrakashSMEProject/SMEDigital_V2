//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.25 at 08:05:43 PM IST 
//


package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "lob",
    "schemeCd",
    "tariffCd"
    
})
@XmlRootElement(name = "ProductDetailsRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class ProductDetailsRequest {

    @XmlElement(name="lob", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
    protected String lob;
    
	@XmlElement(name="schemeCd", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Long schemeCd;
    
	@XmlElement(name="tariffCd", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Long tariffCd;
	
	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public Long getSchemeCd() {
		return schemeCd;
	}

	public void setSchemeCd(Long schemeCd) {
		this.schemeCd = schemeCd;
	}

	public Long getTariffCd() {
		return tariffCd;
	}

	public void setTariffCd(Long tariffCd) {
		this.tariffCd = tariffCd;
	}

   

}