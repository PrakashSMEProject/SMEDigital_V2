/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author M1016303
 *
 */
public class WCNammedEmployeeVO extends RiskGroupDetails implements Comparable<WCNammedEmployeeVO> {
	
	
	private static final long serialVersionUID = 1L;
	
	private String empName;
	private Integer index;
	private Long wprWCId;
	private Date vsd;
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @return the wprWCId
	 */
	public Long getWprWCId() {
		return wprWCId;
	}

	/**
	 * @param wprWCId the wprWCId to set
	 */
	public void setWprWCId(Long wprWCId) {
		this.wprWCId = wprWCId;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Date getVsd() {
		return vsd;
	}

	public void setVsd(Date vsd) {
		this.vsd = vsd;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "empName".equals( fieldName ) ) fieldValue = getEmpName();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();
		if( "wprWCId".equals( fieldName ) ) fieldValue = getWprWCId();
		
		return fieldValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WCNammedEmployeeVO [empName=" + empName + ", index=" + index
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( empName == null ) ? 0 : empName.hashCode() );
		result = prime * result + ( ( wprWCId == null ) ? 0 : wprWCId.hashCode() );
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		
		if( this == obj ){
			return true;
		}
		if( obj == null ){
			return false;
		}
		if( !( obj instanceof WCNammedEmployeeVO ) ){
			return false;
		}
		WCNammedEmployeeVO other = (WCNammedEmployeeVO) obj;
		/*if( empName == null ){
			if( other.empName != null ){
				return false;
			}
		}
		else if( !empName.equals( other.empName ) ){
			return false;
		}*/
		if( wprWCId == null ){
			if( other.wprWCId != null ){
				return false;
			}
		}
		else if( !wprWCId.equals( other.wprWCId ) ){
			return false;
		}
		return true;
	}

	@Override
	public int compareTo( WCNammedEmployeeVO other ){
		if( Utils.isEmpty( this.getWprWCId() ) || Utils.isEmpty( other.getWprWCId() ) ){
			if( !Utils.isEmpty( this.getIndex() ) && !Utils.isEmpty( other.getIndex() ) ){
				return this.getIndex() - other.getIndex();
			}
		}else if( !Utils.isEmpty( this.getWprWCId() ) && !Utils.isEmpty( this.getWprWCId() ) ){
			return this.getWprWCId().intValue() - other.getWprWCId().intValue();
		}
		return 0;
	}


	
}
