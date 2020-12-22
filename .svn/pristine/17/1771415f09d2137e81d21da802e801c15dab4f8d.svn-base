package com.rsaame.pas.b2c.ws.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Staff {
	@JsonProperty("StaffName")
	private String staffName;
	@JsonProperty("StaffDob")
	private Date staffDob;
	@JsonProperty("StaffId")
	private Integer staffId;
	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getStaffDob() {
		return staffDob;
	}
	@JsonDeserialize(using=CustomDateDeSerializer.class)
	public void setStaffDob(Date staffDob) {
		this.staffDob = staffDob;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((staffDob == null) ? 0 : staffDob.hashCode());
//		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
		result = prime * result + ((staffName == null) ? 0 : staffName.hashCode());
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
		Staff other = (Staff) obj;
		if (staffDob == null) {
			if (other.staffDob != null)
				return false;
		} else if (!staffDob.equals(other.staffDob))
			return false;
	/*	if (staffId == null) {
			if (other.staffId != null)
				return false;
		} else if (!staffId.equals(other.staffId))
			return false;*/
		if (staffName == null) {
			if (other.staffName != null)
				return false;
		} else if (!staffName.equals(other.staffName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Staff [staffName=" + staffName + ", staffDob=" + staffDob + "]";
	}
	public Staff() {
		// TODO Auto-generated constructor stub
	}
	public Staff(String staffName, Date staffDob, Integer staffId) {
		super();
		this.staffName = staffName;
		this.staffDob = staffDob;
		this.staffId = staffId;
	}
	
}
