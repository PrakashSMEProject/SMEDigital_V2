package com.rsaame.pas.b2c.ws.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * @author M1044786
 *
 */
public class StaffDetails {
	
	@JsonProperty("Staff")
	private List<Staff> staff;

	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((staff == null) ? 0 : staff.hashCode());
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
		StaffDetails other = (StaffDetails) obj;
		if (staff == null) {
			if (other.staff != null)
				return false;
		} else if (!staff.equals(other.staff))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StaffDetails [staff=" + staff + "]";
	}
	
}
