package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Travellers {
	@JsonProperty("TravellerName")
	private String travellerName;
	@JsonProperty("TravellerDOB")
	private Date travellerDOB;
	@JsonProperty("Relation")
	private Byte relation;
	@JsonProperty("TravellerNationality")
	private Integer travellerNationality;
	@JsonProperty("TravellerId")
	private BigDecimal travellerId;

	public String getTravellerName() {
		return travellerName;
	}

	public void setTravellerName(String travellerName) {
		this.travellerName = travellerName;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getTravellerDOB() {
		return travellerDOB;
	}
	@JsonDeserialize(using=CustomDateDeSerializer.class)
	public void setTravellerDOB(Date travellerDOB) {
		this.travellerDOB = travellerDOB;
	}

	public Byte getRelation() {
		return relation;
	}

	public void setRelation(Byte relation) {
		this.relation = relation;
	}

	public Integer getTravellerNationality() {
		return travellerNationality;
	}

	public void setTravellerNationality(Integer travellerNationality) {
		this.travellerNationality = travellerNationality;
	}

	public BigDecimal getTravellerId() {
		return travellerId;
	}

	public void setTravellerId(BigDecimal travellerId) {
		this.travellerId = travellerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((relation == null) ? 0 : relation.hashCode());
		result = prime * result + ((travellerDOB == null) ? 0 : travellerDOB.hashCode());
		result = prime * result + ((travellerId == null) ? 0 : travellerId.hashCode());
		result = prime * result + ((travellerName == null) ? 0 : travellerName.hashCode());
		result = prime * result + ((travellerNationality == null) ? 0 : travellerNationality.hashCode());
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
		Travellers other = (Travellers) obj;
		if (relation == null) {
			if (other.relation != null)
				return false;
		} else if (!relation.equals(other.relation))
			return false;
		if (travellerDOB == null) {
			if (other.travellerDOB != null)
				return false;
		} else if (!travellerDOB.equals(other.travellerDOB))
			return false;
		if (travellerId == null) {
			if (other.travellerId != null)
				return false;
		} else if (!travellerId.equals(other.travellerId))
			return false;
		if (travellerName == null) {
			if (other.travellerName != null)
				return false;
		} else if (!travellerName.equals(other.travellerName))
			return false;
		if (travellerNationality == null) {
			if (other.travellerNationality != null)
				return false;
		} else if (!travellerNationality.equals(other.travellerNationality))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Travellers [travellerName=" + travellerName + ", travellerDOB=" + travellerDOB + ", relation="
				+ relation + ", travellerNationality=" + travellerNationality + ", travellerId=" + travellerId + "]";
	}

}
