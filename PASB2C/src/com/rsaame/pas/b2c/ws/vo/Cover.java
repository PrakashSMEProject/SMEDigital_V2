package com.rsaame.pas.b2c.ws.vo;

public class Cover {
	
	private Integer cover_Id;
	private Long value;
	private Integer classCode;
	private Integer policyType;
	private Integer coverCode;
	private Integer coverType;
	private Integer coverSubType;
	public Integer getCover_Id() {
		return cover_Id;
	}
	public void setCover_Id(Integer cover_Id) {
		this.cover_Id = cover_Id;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Integer getClassCode() {
		return classCode;
	}
	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}
	public Integer getPolicyType() {
		return policyType;
	}
	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}
	public Integer getCoverCode() {
		return coverCode;
	}
	public void setCoverCode(Integer coverCode) {
		this.coverCode = coverCode;
	}
	public Integer getCoverType() {
		return coverType;
	}
	public void setCoverType(Integer coverType) {
		this.coverType = coverType;
	}
	public Integer getCoverSubType() {
		return coverSubType;
	}
	public void setCoverSubType(Integer coverSubType) {
		this.coverSubType = coverSubType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classCode == null) ? 0 : classCode.hashCode());
		result = prime * result + ((coverCode == null) ? 0 : coverCode.hashCode());
		result = prime * result + ((coverSubType == null) ? 0 : coverSubType.hashCode());
		result = prime * result + ((coverType == null) ? 0 : coverType.hashCode());
		result = prime * result + ((cover_Id == null) ? 0 : cover_Id.hashCode());
		result = prime * result + ((policyType == null) ? 0 : policyType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Cover other = (Cover) obj;
		if (classCode == null) {
			if (other.classCode != null)
				return false;
		} else if (!classCode.equals(other.classCode))
			return false;
		if (coverCode == null) {
			if (other.coverCode != null)
				return false;
		} else if (!coverCode.equals(other.coverCode))
			return false;
		if (coverSubType == null) {
			if (other.coverSubType != null)
				return false;
		} else if (!coverSubType.equals(other.coverSubType))
			return false;
		if (coverType == null) {
			if (other.coverType != null)
				return false;
		} else if (!coverType.equals(other.coverType))
			return false;
		if (cover_Id == null) {
			if (other.cover_Id != null)
				return false;
		} else if (!cover_Id.equals(other.cover_Id))
			return false;
		if (policyType == null) {
			if (other.policyType != null)
				return false;
		} else if (!policyType.equals(other.policyType))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Cover [cover_Id=" + cover_Id + ", value=" + value + ", classCode=" + classCode + ", policyType="
				+ policyType + ", coverCode=" + coverCode + ", coverType=" + coverType + ", coverSubType="
				+ coverSubType + "]";
	}
	
}
