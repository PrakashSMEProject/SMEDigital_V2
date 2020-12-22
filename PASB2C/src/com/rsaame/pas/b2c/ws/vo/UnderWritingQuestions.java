/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1037404
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UnderWritingQuestions {
	@JsonProperty("HomeUnattended")
	private Boolean homeUnattended;
	@JsonProperty("Claim")
	private Boolean claim;
	@JsonProperty("InclUsaCa")
	private Boolean inclUsaCa;
	@JsonProperty("WarZone")
	private Boolean warZone;

	public Boolean getHomeUnattended() {
		return homeUnattended;
	}

	public void setHomeUnattended(Boolean homeUnattended) {
		this.homeUnattended = homeUnattended;
	}

	public Boolean getClaim() {
		return claim;
	}

	public void setClaim(Boolean claim) {
		this.claim = claim;
	}

	public Boolean getInclUsaCa() {
		return inclUsaCa;
	}

	public void setInclUsaCa(Boolean inclUsaCa) {
		this.inclUsaCa = inclUsaCa;
	}

	public Boolean getWarZone() {
		return warZone;
	}

	public void setWarZone(Boolean warZone) {
		this.warZone = warZone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claim == null) ? 0 : claim.hashCode());
		result = prime * result + ((homeUnattended == null) ? 0 : homeUnattended.hashCode());
		result = prime * result + ((inclUsaCa == null) ? 0 : inclUsaCa.hashCode());
		result = prime * result + ((warZone == null) ? 0 : warZone.hashCode());
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
		UnderWritingQuestions other = (UnderWritingQuestions) obj;
		if (claim == null) {
			if (other.claim != null)
				return false;
		} else if (!claim.equals(other.claim))
			return false;
		if (homeUnattended == null) {
			if (other.homeUnattended != null)
				return false;
		} else if (!homeUnattended.equals(other.homeUnattended))
			return false;
		if (inclUsaCa == null) {
			if (other.inclUsaCa != null)
				return false;
		} else if (!inclUsaCa.equals(other.inclUsaCa))
			return false;
		if (warZone == null) {
			if (other.warZone != null)
				return false;
		} else if (!warZone.equals(other.warZone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UnderWritingQuestions [homeUnattended=" + homeUnattended + ", claim=" + claim + ", inclUsaCa="
				+ inclUsaCa + ", warZone=" + warZone + "]";
	}

}
