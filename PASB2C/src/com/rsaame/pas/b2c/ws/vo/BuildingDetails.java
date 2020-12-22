package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author M1044786
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BuildingDetails {

	@JsonProperty("OwnershipStatus")
	private Integer ownershipStatus;
	@JsonProperty("Emirate")
	private String emirate;
	@JsonProperty("Area")
	private String area;
	@JsonProperty("AreaOthers")
	private String areaOthers;
	@JsonProperty("PropertyType")
	private Integer propertyType;
	@JsonProperty("BuildingName")
	private String buildingName;
	@JsonProperty("FlatVillaNo")
	private String flatVillaNo;
	@JsonProperty("MortgageeCode")
	private Integer mortgageeCode;
	@JsonProperty("PoBox")
	private String poBox;
	@JsonProperty("MortgageeOthers")
	private String mortgageeOthers;
	@JsonProperty("NumberOfFloors")
	private Short totalFloors;
	@JsonProperty("NumberOfBedrooms")
	private Short totalRooms;
	@JsonProperty("Latitude")
	private Double latitude;
	@JsonProperty("Longitude")
	private Double longitude;
	@JsonProperty("Street")
	private String street;
	@JsonProperty("Zone")
	private String zone;
	@JsonProperty("GRL")
	private String grl;
	@JsonProperty("InfoMapStatus")
	private String infoMapStatus;

	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	public String getEmirate() {
		return emirate;
	}

	public void setEmirate(String emirate) {
		this.emirate = emirate;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Integer propertyType) {
		this.propertyType = propertyType;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getFlatVillaNo() {
		return flatVillaNo;
	}

	public void setFlatVillaNo(String flatVillaNo) {
		this.flatVillaNo = flatVillaNo;
	}

	public Integer getOwnershipStatus() {
		return ownershipStatus;
	}

	public void setOwnershipStatus(Integer ownershipStatus) {
		this.ownershipStatus = ownershipStatus;
	}

	public String getAreaOthers() {
		return areaOthers;
	}

	public void setAreaOthers(String areaOthers) {
		this.areaOthers = areaOthers;
	}

	public Integer getMortgageeCode() {
		return mortgageeCode;
	}

	public void setMortgageeCode(Integer mortgageeCode) {
		this.mortgageeCode = mortgageeCode;
	}

	public String getMortgageeOthers() {
		return mortgageeOthers;
	}

	public void setMortgageeOthers(String mortgageeOthers) {
		this.mortgageeOthers = mortgageeOthers;
	}

	public Short getTotalFloors() {
		return totalFloors;
	}

	public void setTotalFloors(Short totalFloors) {
		this.totalFloors = totalFloors;
	}

	public Short getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(Short totalRooms) {
		this.totalRooms = totalRooms;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getGrl() {
		return grl;
	}

	public void setGrl(String grl) {
		this.grl = grl;
	}

	public String getInfoMapStatus() {
		return infoMapStatus;
	}

	public void setInfoMapStatus(String infoMapStatus) {
		this.infoMapStatus = infoMapStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((areaOthers == null) ? 0 : areaOthers.hashCode());
		result = prime * result + ((buildingName == null) ? 0 : buildingName.hashCode());
		result = prime * result + ((emirate == null) ? 0 : emirate.hashCode());
		result = prime * result + ((flatVillaNo == null) ? 0 : flatVillaNo.hashCode());
		result = prime * result + ((grl == null) ? 0 : grl.hashCode());
		result = prime * result + ((infoMapStatus == null) ? 0 : infoMapStatus.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((mortgageeCode == null) ? 0 : mortgageeCode.hashCode());
		result = prime * result + ((mortgageeOthers == null) ? 0 : mortgageeOthers.hashCode());
		result = prime * result + ((ownershipStatus == null) ? 0 : ownershipStatus.hashCode());
		result = prime * result + ((poBox == null) ? 0 : poBox.hashCode());
		result = prime * result + ((propertyType == null) ? 0 : propertyType.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((totalFloors == null) ? 0 : totalFloors.hashCode());
		result = prime * result + ((totalRooms == null) ? 0 : totalRooms.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
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
		BuildingDetails other = (BuildingDetails) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (areaOthers == null) {
			if (other.areaOthers != null)
				return false;
		} else if (!areaOthers.equals(other.areaOthers))
			return false;
		if (buildingName == null) {
			if (other.buildingName != null)
				return false;
		} else if (!buildingName.equals(other.buildingName))
			return false;
		if (emirate == null) {
			if (other.emirate != null)
				return false;
		} else if (!emirate.equals(other.emirate))
			return false;
		if (flatVillaNo == null) {
			if (other.flatVillaNo != null)
				return false;
		} else if (!flatVillaNo.equals(other.flatVillaNo))
			return false;
		if (grl == null) {
			if (other.grl != null)
				return false;
		} else if (!grl.equals(other.grl))
			return false;
		if (infoMapStatus == null) {
			if (other.infoMapStatus != null)
				return false;
		} else if (!infoMapStatus.equals(other.infoMapStatus))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (mortgageeCode == null) {
			if (other.mortgageeCode != null)
				return false;
		} else if (!mortgageeCode.equals(other.mortgageeCode))
			return false;
		if (mortgageeOthers == null) {
			if (other.mortgageeOthers != null)
				return false;
		} else if (!mortgageeOthers.equals(other.mortgageeOthers))
			return false;
		if (ownershipStatus == null) {
			if (other.ownershipStatus != null)
				return false;
		} else if (!ownershipStatus.equals(other.ownershipStatus))
			return false;
		if (poBox == null) {
			if (other.poBox != null)
				return false;
		} else if (!poBox.equals(other.poBox))
			return false;
		if (propertyType == null) {
			if (other.propertyType != null)
				return false;
		} else if (!propertyType.equals(other.propertyType))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (totalFloors == null) {
			if (other.totalFloors != null)
				return false;
		} else if (!totalFloors.equals(other.totalFloors))
			return false;
		if (totalRooms == null) {
			if (other.totalRooms != null)
				return false;
		} else if (!totalRooms.equals(other.totalRooms))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BuildingDetails [ownershipStatus=" + ownershipStatus + ", emirate=" + emirate + ", area=" + area
				+ ", areaOthers=" + areaOthers + ", propertyType=" + propertyType + ", buildingName=" + buildingName
				+ ", flatVillaNo=" + flatVillaNo + ", mortgageeCode=" + mortgageeCode + ", poBox=" + poBox
				+ ", mortgageeOthers=" + mortgageeOthers + ", totalFloors=" + totalFloors + ", totalRooms=" + totalRooms
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", street=" + street + ", zone=" + zone
				+ ", grl=" + grl + ", infoMapStatus=" + infoMapStatus + "]";
	}

}
