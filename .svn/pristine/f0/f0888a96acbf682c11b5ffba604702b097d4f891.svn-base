/**
 * 
 */
package com.rsaame.pas.vo.cmn;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1014644
 *
 */
public class TableData<T extends BaseVO > extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long contentID;
	private Date contentVsd;
	private CoverVO coverCodes;
	private RiskVO riskCodes;
	private T tableData;
	private boolean toBeDeleted;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		return null;
	}
	
	public Short getCoverCode(){
		return coverCodes.getCovCode();
	}
	
	
	/**
	 * @return the contentID
	 */
	public Long getContentID(){
		return contentID;
	}
	/**
	 * @param contentID the contentID to set
	 */
	public void setContentID( Long contentID ){
		this.contentID = contentID;
	}
	/**
	 * @return the contentVsd
	 */
	public Date getContentVsd(){
		return contentVsd;
	}
	/**
	 * @param contentVsd the contentVsd to set
	 */
	public void setContentVsd( Date contentVsd ){
		this.contentVsd = contentVsd;
	}
	/**
	 * @return the coverCodes
	 */
	public CoverVO getCoverCodes(){
		return coverCodes;
	}
	/**
	 * @param coverCodes the coverCodes to set
	 */
	public void setCoverCodes( CoverVO coverCodes ){
		this.coverCodes = coverCodes;
	}
	/**
	 * @return the riskCodes
	 */
	public RiskVO getRiskCodes(){
		return riskCodes;
	}
	/**
	 * @param riskCodes the riskCodes to set
	 */
	public void setRiskCodes( RiskVO riskCodes ){
		this.riskCodes = riskCodes;
	}
	/**
	 * @return the tableData
	 */
	public T getTableData(){
		return tableData;
	}
	/**
	 * @param tableData the tableData to set
	 */
	public void setTableData( T tableData ){
		this.tableData = tableData;
	}
	
	
	
	/**
	 * @return the toBeDeleted
	 */
	public boolean isToBeDeleted(){
		return toBeDeleted;
	}

	/**
	 * @param toBeDeleted the toBeDeleted to set
	 */
	public void setToBeDeleted( boolean toBeDeleted ){
		this.toBeDeleted = toBeDeleted;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( contentID == null ) ? 0 : contentID.hashCode() );
		result = prime * result + ( ( contentVsd == null ) ? 0 : contentVsd.hashCode() );
		result = prime * result + ( ( coverCodes == null ) ? 0 : coverCodes.hashCode() );
		result = prime * result + ( ( riskCodes == null ) ? 0 : riskCodes.hashCode() );
		result = prime * result + ( ( tableData == null ) ? 0 : tableData.hashCode() );
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		TableData other = (TableData) obj;
		if( contentID == null ){
			if( other.contentID != null ) return false;
		}
		else if( !contentID.equals( other.contentID ) ) return false;
		if( contentVsd == null ){
			if( other.contentVsd != null ) return false;
		}
		else if( !contentVsd.equals( other.contentVsd ) ) return false;
		if( coverCodes == null ){
			if( other.coverCodes != null ) return false;
		}
		else if( !coverCodes.equals( other.coverCodes ) ) return false;
		if( riskCodes == null ){
			if( other.riskCodes != null ) return false;
		}
		else if( !riskCodes.equals( other.riskCodes ) ) return false;
		if( tableData == null ){
			if( other.tableData != null ) return false;
		}
		else if( !tableData.equals( other.tableData ) ) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "TableData [contentID=" + contentID + ", contentVsd=" + contentVsd + ", coverCodes=" + coverCodes + ", riskCodes=" + riskCodes + ", tableData=" + tableData
				+ ", toBeDeleted=" + toBeDeleted + "]";
	}

	
	
	
}
