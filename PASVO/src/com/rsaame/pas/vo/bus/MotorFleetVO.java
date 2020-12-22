package com.rsaame.pas.vo.bus;

/**
 * 
 * @author m1014438
 *
 */
public class MotorFleetVO extends RiskGroupDetails{

	private java.util.List<VehicleDetailsVO> vehicleDetails = new com.mindtree.ruc.cmn.utils.List<VehicleDetailsVO>( VehicleDetailsVO.class );
	private java.util.List<CovrageDetailsVO> covrageDetails = new com.mindtree.ruc.cmn.utils.List<CovrageDetailsVO>( CovrageDetailsVO.class );
	private java.util.List<AdditionalVehicleDetailsVO> additionalVehicleDetails = new com.mindtree.ruc.cmn.utils.List<AdditionalVehicleDetailsVO>( AdditionalVehicleDetailsVO.class );
	public java.util.List<VehicleDetailsVO> getVehicleDetails() {
		return vehicleDetails;
	}
	public void setVehicleDetails(java.util.List<VehicleDetailsVO> vehicleDetails) {
		this.vehicleDetails = vehicleDetails;
	}
	public java.util.List<CovrageDetailsVO> getCovrageDetails() {
		return covrageDetails;
	}
	public void setCovrageDetails(java.util.List<CovrageDetailsVO> covrageDetails) {
		this.covrageDetails = covrageDetails;
	}
	public java.util.List<AdditionalVehicleDetailsVO> getAdditionalVehicleDetails() {
		return additionalVehicleDetails;
	}
	public void setAdditionalVehicleDetails(
			java.util.List<AdditionalVehicleDetailsVO> additionalVehicleDetails) {
		this.additionalVehicleDetails = additionalVehicleDetails;
	}

	
	
}
