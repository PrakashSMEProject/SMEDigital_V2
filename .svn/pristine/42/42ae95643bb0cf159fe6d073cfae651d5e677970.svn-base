package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1042506
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TLLimit {
	@JsonProperty("CoverDesc")
	private String coverDesc;
	@JsonProperty("Selected")
	private Boolean selected;
	@JsonProperty("CoverageLimit")
	private BigDecimal coverageLimit;
	@JsonProperty("Premium")
	private BigDecimal premium;

	public TLLimit() {
		// TODO Auto-generated constructor stub
	}

	public TLLimit(String coverDesc, Boolean selected, BigDecimal coverageLimit, BigDecimal premium) {
		super();
		this.coverDesc = coverDesc;
		this.selected = selected;
		this.coverageLimit = coverageLimit;
		this.premium = premium;
	}

	public String getCoverDesc() {
		return coverDesc;
	}

	public void setCoverDesc(String coverDesc) {
		this.coverDesc = coverDesc;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public BigDecimal getCoverageLimit() {
		return coverageLimit;
	}

	public void setCoverageLimit(BigDecimal coverageLimit) {
		this.coverageLimit = coverageLimit;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coverDesc == null) ? 0 : coverDesc.hashCode());
		result = prime * result + ((coverageLimit == null) ? 0 : coverageLimit.hashCode());
		result = prime * result + ((premium == null) ? 0 : premium.hashCode());
		result = prime * result + ((selected == null) ? 0 : selected.hashCode());
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
		TLLimit other = (TLLimit) obj;
		if (coverDesc == null) {
			if (other.coverDesc != null)
				return false;
		} else if (!coverDesc.equals(other.coverDesc))
			return false;
		if (coverageLimit == null) {
			if (other.coverageLimit != null)
				return false;
		} else if (!coverageLimit.equals(other.coverageLimit))
			return false;
		if (premium == null) {
			if (other.premium != null)
				return false;
		} else if (!premium.equals(other.premium))
			return false;
		if (selected == null) {
			if (other.selected != null)
				return false;
		} else if (!selected.equals(other.selected))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TLLimit [coverDesc=" + coverDesc + ", selected=" + selected + ", coverageLimit=" + coverageLimit
				+ ", premium=" + premium + "]";
	}

}
