/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author Sarath Varier
 * @since Phase 3 - RSA Direct - Make a claim migration
 *
 */

public class MotorClaimVO extends ClaimsVO {

	private static final long serialVersionUID = -7705563375305527702L;
	
	private InsuredVO insuredVO;
	private VehicleVO vehicleVO;
	
	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if("insuredVO".equals(fieldName))fieldValue = getInsuredVO();
		if("vehicleVO".equals(fieldName))fieldValue = getVehicleVO();
		return fieldValue;
	}
	
	/**
	 * @return the insuredVO
	 */
	public InsuredVO getInsuredVO() {
		return insuredVO;
	}
	/**
	 * @param insuredVO the insuredVO to set
	 */
	public void setInsuredVO(InsuredVO insuredVO) {
		this.insuredVO = insuredVO;
	}
	/**
	 * @return the vehicleVO
	 */
	public VehicleVO getVehicleVO() {
		return vehicleVO;
	}
	/**
	 * @param vehicleVO the vehicleVO to set
	 */
	public void setVehicleVO(VehicleVO vehicleVO) {
		this.vehicleVO = vehicleVO;
	}
	
	@Override
	public String toString(){
		return "MotorClaimVO [ ClaimsVO=" + super.toString() + ", insuredVO=" +insuredVO.toString() + ", vehicleVO=" + vehicleVO.toString() + " ]";
	}

}
