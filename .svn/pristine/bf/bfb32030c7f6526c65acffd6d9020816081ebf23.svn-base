package com.rsaame.pas.vo.bus;

import java.util.List;

public class MBVO extends RiskGroupDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long riskId;
	private List<MachineDetailsVO> machineryDetails = new com.mindtree.ruc.cmn.utils.List<MachineDetailsVO>(MachineDetailsVO.class);
	

	public List<MachineDetailsVO> getMachineryDetails() {
		return machineryDetails;
	}

	public void setMachineryDetails(List<MachineDetailsVO> machineryDetails) {
		this.machineryDetails = machineryDetails;
	}

	public Long getRiskId() {
		return riskId;
	}

	public void setRiskId(Long riskId) {
		this.riskId = riskId;
	}

	
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "machineryDetails".equals( fieldName ) ) fieldValue = getMachineryDetails();
		if( "riskId".equals( fieldName ) ) fieldValue = getRiskId();
		

		return fieldValue;
	}

	@Override
	public String toString(){
		return "MBVO [MBDets=" + machineryDetails + "]";
	}

}
