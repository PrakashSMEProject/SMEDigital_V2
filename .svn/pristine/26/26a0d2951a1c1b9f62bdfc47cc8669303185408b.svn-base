package com.rsaame.pas.b2c.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "type",
    "idNumber",
    "lob",
    "triggerName"
})
@XmlRootElement(name = "SendNotificationMailRequest", namespace = "http://com/rsaame/pas/b2c/ws")
public class SendNotificationMailRequest {
	
	@XmlElement(name="type", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private String type;
	@XmlElement(name="idNumber", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private Long idNumber;
	@XmlElement(name="lob", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private String lob;
	@XmlElement(name="triggerName", required = true, namespace = "http://com/rsaame/pas/b2c/ws")
	private String triggerName;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

}
