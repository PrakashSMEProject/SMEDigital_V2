/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1021201
 *
 */
public class CoverDetails extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026162140810216214L;
	
	private java.util.List<CoverDetailsVO> coverDetails = new com.mindtree.ruc.cmn.utils.List<CoverDetailsVO>( CoverDetailsVO.class );
	private java.util.List<StaffDetailsVO> staffDetails = new com.mindtree.ruc.cmn.utils.List<StaffDetailsVO>( StaffDetailsVO.class );

	public void setCoverDetails( List<CoverDetailsVO> coverDetails ){
		this.coverDetails = coverDetails;
	}

	public List<CoverDetailsVO> getCoverDetails(){
		return coverDetails;
	}
	
	public java.util.List<StaffDetailsVO> getStaffDetails() {
		return staffDetails;
	}

	public void setStaffDetails(java.util.List<StaffDetailsVO> staffDetailsVO) {
		this.staffDetails = staffDetailsVO;
	}


	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if( "coverDetails".equals( fieldName ) ) fieldValue = getCoverDetails();
		return fieldValue;
	}

}
