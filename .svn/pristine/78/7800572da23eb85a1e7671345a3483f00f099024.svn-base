/*
 * Holds all Travel related information 
 *  List<TravelerDetailsVO> - List of traveler details
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * 
 * @author m1017029
 * @since Phase3
 */
public class TravelDetailsVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5477393929126392759L;
	private String travelLocation;
	private Integer travelPeriod;
	private Integer finalDestination;
	private Date startDate;
	private Date endDate;
	private List<TravelerDetailsVO> travelerDetailsList = new com.mindtree.ruc.cmn.utils.List<TravelerDetailsVO>(
			TravelerDetailsVO.class);
	
	
	/**
	 * @return String
	 */
	public String getTravelLocation() {
		return travelLocation;
	}

	/**
	 * @param travelLocation
	 */
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

	/**
	 * @return List<TravelerDetailsVO>
	 */
	public List<TravelerDetailsVO> getTravelerDetailsList() {
		return travelerDetailsList;
	}

	/**
	 * @param travelerDetailsList
	 */
	public void setTravelerDetailsList(List<TravelerDetailsVO> travelerDetailsList) {
		this.travelerDetailsList = travelerDetailsList;
	}
	
	public Integer getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(Integer finalDestination) {
		this.finalDestination = finalDestination;
	}

	@Override
	public Object getFieldValue( String fieldName ){

			if("travelPeriod".equals(fieldName)) return getTravelPeriod();
			if("travelLocation".equals(fieldName))return getTravelLocation();
			if("startDate".equals(fieldName)) return getStartDate();
			if("endDate".equals(fieldName)) return getEndDate();
			if("travelerDetailsList".equals(fieldName)) return getTravelerDetailsList();
			if("finalDestination".equals(fieldName)) return getFinalDestination();
		return null;
	}

}
