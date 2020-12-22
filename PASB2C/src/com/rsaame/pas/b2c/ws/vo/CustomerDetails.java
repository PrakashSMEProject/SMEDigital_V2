package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerDetails {
	
	@JsonProperty("EmailId")
	private String emailId;
	@JsonProperty("MobileNo")
	private String mobileNo;
	@JsonProperty("InsuredId")
	private Long insuredId;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("PoBox")
	private String poBox;
	@JsonProperty("City")
	private Integer city;
	@JsonProperty("Nationality")
	private Integer nationality;
	@JsonProperty("NationalID")
	private String nationalID;
	@JsonProperty("VatRegNo")
	private String vatRegNo;
	@JsonProperty("RewardProgrammeType")
	private Integer rewardProgrammeType;
	@JsonProperty("RewardCardNumber")
	private String rewardCardNumber;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Long getInsuredId() {
		return insuredId;
	}
	public void setInsuredId(Long insuredId) {
		this.insuredId = insuredId;
	}
	public String getPoBox() {
		return poBox;
	}
	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getNationality() {
		return nationality;
	}
	public void setNationality(Integer nationality) {
		this.nationality = nationality;
	}
	public String getNationalID() {
		return nationalID;
	}
	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}
	public String getVatRegNo() {
		return vatRegNo;
	}
	public void setVatRegNo(String vatRegNo) {
		this.vatRegNo = vatRegNo;
	}
	public Integer getRewardProgrammeType() {
		return rewardProgrammeType;
	}
	public void setRewardProgrammeType(Integer rewardProgrammeType) {
		this.rewardProgrammeType = rewardProgrammeType;
	}
	public String getRewardCardNumber() {
		return rewardCardNumber;
	}
	public void setRewardCardNumber(String rewardCardNumber) {
		this.rewardCardNumber = rewardCardNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((insuredId == null) ? 0 : insuredId.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((mobileNo == null) ? 0 : mobileNo.hashCode());
		result = prime * result + ((nationalID == null) ? 0 : nationalID.hashCode());
		result = prime * result + ((nationality == null) ? 0 : nationality.hashCode());
		result = prime * result + ((poBox == null) ? 0 : poBox.hashCode());
		result = prime * result + ((rewardCardNumber == null) ? 0 : rewardCardNumber.hashCode());
		result = prime * result + ((rewardProgrammeType == null) ? 0 : rewardProgrammeType.hashCode());
		result = prime * result + ((vatRegNo == null) ? 0 : vatRegNo.hashCode());
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
		CustomerDetails other = (CustomerDetails) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (insuredId == null) {
			if (other.insuredId != null)
				return false;
		} else if (!insuredId.equals(other.insuredId))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobileNo == null) {
			if (other.mobileNo != null)
				return false;
		} else if (!mobileNo.equals(other.mobileNo))
			return false;
		if (nationalID == null) {
			if (other.nationalID != null)
				return false;
		} else if (!nationalID.equals(other.nationalID))
			return false;
		if (nationality == null) {
			if (other.nationality != null)
				return false;
		} else if (!nationality.equals(other.nationality))
			return false;
		if (poBox == null) {
			if (other.poBox != null)
				return false;
		} else if (!poBox.equals(other.poBox))
			return false;
		if (rewardCardNumber == null) {
			if (other.rewardCardNumber != null)
				return false;
		} else if (!rewardCardNumber.equals(other.rewardCardNumber))
			return false;
		if (rewardProgrammeType == null) {
			if (other.rewardProgrammeType != null)
				return false;
		} else if (!rewardProgrammeType.equals(other.rewardProgrammeType))
			return false;
		if (vatRegNo == null) {
			if (other.vatRegNo != null)
				return false;
		} else if (!vatRegNo.equals(other.vatRegNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CustomerDetails [emailId=" + emailId + ", mobileNo=" + mobileNo + ", insuredId=" + insuredId
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", poBox=" + poBox + ", city=" + city
				+ ", nationality=" + nationality + ", nationalID=" + nationalID + ", vatRegNo=" + vatRegNo
				+ ", rewardProgrammeType=" + rewardProgrammeType + ", rewardCardNumber=" + rewardCardNumber + "]";
	}
	
	
	
}
