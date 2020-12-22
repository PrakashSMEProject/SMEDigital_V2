package com.rsaame.pas.b2c.ws.beans;

import java.util.Date;
import java.util.List;

public class TravelDetails {

	private String travelLocation;
	private Integer travelPeriod;
	private Date startDate;
	private Date endDate;
	private List<TravelerDetails> travelerDetailsList = null;
	
	public String getTravelLocation() {
		return travelLocation;
	}
	public void setTravelLocation(String travelLocation) {
		this.travelLocation = travelLocation;
	}
	public Integer getTravelPeriod() {
		return travelPeriod;
	}
	public void setTravelPeriod(Integer travelPeriod) {
		this.travelPeriod = travelPeriod;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<TravelerDetails> getTravelerDetailsList() {
		return travelerDetailsList;
	}
	public void setTravelerDetailsList(List<TravelerDetails> travelerDetailsList) {
		this.travelerDetailsList = travelerDetailsList;
	}
	
}
