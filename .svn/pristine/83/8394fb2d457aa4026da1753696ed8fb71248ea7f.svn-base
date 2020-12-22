package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Extras {
	@JsonProperty("Expiry")
	private String expiry;
	@JsonProperty("Promotional_Message")
	private String promotional_Message;
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getPromotional_Message() {
		return promotional_Message;
	}
	public void setPromotional_Message(String promotional_Message) {
		this.promotional_Message = promotional_Message;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expiry == null) ? 0 : expiry.hashCode());
		result = prime * result + ((promotional_Message == null) ? 0 : promotional_Message.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Extras other = (Extras) obj;
		if (expiry == null) {
			if (other.expiry != null)
				return false;
		} else if (!expiry.equals(other.expiry))
			return false;
		if (promotional_Message == null) {
			if (other.promotional_Message != null)
				return false;
		} else if (!promotional_Message.equals(other.promotional_Message))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Extras [expiry=" + expiry + ", promotional_Message=" + promotional_Message + "]";
	}
	
}
