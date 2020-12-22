/**
 * 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.mindtree.ruc.cmn.utils.List;

/**
 * @author Sarath Varier
 * @Since Phase4 - WC monoline
 *
 */
public class WorkmenCompVO extends PolicyDataVO implements IFieldValue {

	private static final long serialVersionUID = 3456725658592962496L;
	
	private List<EmpTypeDetailsVO> empTypeDetails = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>( EmpTypeDetailsVO.class );
	private java.util.List<WCNammedEmployeeVO> wcEmployeeDetails = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>( WCNammedEmployeeVO.class );
	private LocationVO locationVO;
	private WCCoversVO wcCovers;
	
	public java.util.List<WCNammedEmployeeVO> getWcEmployeeDetails() {
		return wcEmployeeDetails;
	}
	
	public void setWcEmployeeDetails(java.util.List<WCNammedEmployeeVO> wcEmployeeDetails) {
		this.wcEmployeeDetails = wcEmployeeDetails;
	}
	
	public List<EmpTypeDetailsVO> getEmpTypeDetails() {
		return empTypeDetails;
	}
	
	public void setEmpTypeDetails(List<EmpTypeDetailsVO> empTypeDetails) {
		this.empTypeDetails = empTypeDetails;
	}
	
	public LocationVO getLocationVO() {
		return locationVO;
	}

	public void setLocationVO(LocationVO locationVO) {
		this.locationVO = locationVO;
	}

	public WCCoversVO getWcCovers() {
		return wcCovers;
	}

	public void setWcCovers(WCCoversVO wcCovers) {
		this.wcCovers = wcCovers;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "empTypeDetails".equals( fieldName ) ) fieldValue = getEmpTypeDetails();
		if( "wcEmployeeDetails".equals( fieldName ) ) fieldValue = getWcEmployeeDetails();
		if( "locationVO".equals( fieldName ) ) fieldValue = getLocationVO();
		if( "wcCovers".equals( fieldName ) ) fieldValue = getWcCovers();
		
		return fieldValue;
	}
	
	@Override
	public String toString(){
		return "WorkmenCompVO [empTypeDetails=" + empTypeDetails + ", wcEmployeeDetails=" + wcEmployeeDetails 
				+ ", locationVO=" + locationVO + ", wcCovers=" + wcCovers +" ]";
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( empTypeDetails == null ) ? 0 : empTypeDetails.hashCode() );
		return result;
	}
	
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		WorkmenCompVO other = (WorkmenCompVO) obj;
		if( empTypeDetails == null ){
			if( other.empTypeDetails != null ) return false;
		}
		if( wcEmployeeDetails == null ){
			if( other.wcEmployeeDetails != null ) return false;
		}
		else if( !empTypeDetails.equals( other.empTypeDetails ) ) return false;
		else if( !wcEmployeeDetails.equals( other.wcEmployeeDetails ) ) return false;
		return true;
	}

}
