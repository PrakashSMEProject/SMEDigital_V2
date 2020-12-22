/**
 * Holds all the information related to Home Insurance.
 */
package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author M1014644
 * @since Phase 3
 *
 */
public class HomeInsuranceVO extends PolicyDataVO implements IFieldValue{

	private static final long serialVersionUID = 1L;

	private java.util.List<CoverDetailsVO> covers = new com.mindtree.ruc.cmn.utils.List<CoverDetailsVO>( CoverDetailsVO.class );
	private java.util.List<StaffDetailsVO> staffDetails = new com.mindtree.ruc.cmn.utils.List<StaffDetailsVO>( StaffDetailsVO.class );
	private BuildingDetailsVO buildingDetails;

	public List<CoverDetailsVO> getCovers(){
		return covers;
	}

	public void setCovers( List<CoverDetailsVO> covers ){
		this.covers = covers;
	}

	public BuildingDetailsVO getBuildingDetails(){
		return buildingDetails;
	}

	public void setBuildingDetails( BuildingDetailsVO buildingDetails ){
		this.buildingDetails = buildingDetails;
	}
	
	public java.util.List<StaffDetailsVO> getStaffDetails() {
		return staffDetails;
	}

	public void setStaffDetails(java.util.List<StaffDetailsVO> staffDetailsVO) {
		this.staffDetails = staffDetailsVO;
	}

	@Override
	public String toString(){
		return "HomeInsuranceVO [covers=" + covers + ", buildingDetails=" + buildingDetails + "]";
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "covers".equals( fieldName ) ) fieldValue = getCovers();
		if( "buildingDetails".equals( fieldName ) ) fieldValue = getBuildingDetails();

		return fieldValue;
	}

}
