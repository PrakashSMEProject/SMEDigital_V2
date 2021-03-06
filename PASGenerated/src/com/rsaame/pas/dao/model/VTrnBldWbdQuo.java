package com.rsaame.pas.dao.model;

import java.math.BigDecimal;
import java.util.Date;

// Generated Mar 20, 2012 5:22:58 PM by Hibernate Tools 3.4.0.CR1

/**
 * VTrnBldWbdQuo generated by hbm2java
 */
public class VTrnBldWbdQuo implements java.io.Serializable {

	private VTrnBldWbdQuoId key;
	
	private Long policyId;
	private BigDecimal occTradeGrp;
	private BigDecimal dirCode;
	private String freeZone;
	private String flatNo;
	private String floorNo;
	private String description;
	private String name;
	private String streetName;
	private BigDecimal terCode;
	private BigDecimal jurCode;
	private Date validityStartDate;
	private String flag;

	public VTrnBldWbdQuo() {
	}

	
	public VTrnBldWbdQuo( VTrnBldWbdQuoId key, BigDecimal id, Long policyId, BigDecimal occTradeGrp, BigDecimal dirCode, String freeZone, String flatNo, String floorNo,
			String description, String name, String streetName, BigDecimal terCode, BigDecimal jurCode, Date validityStartDate, String flag ){
		super();
		this.key = key;
		this.policyId = policyId;
		this.occTradeGrp = occTradeGrp;
		this.dirCode = dirCode;
		this.freeZone = freeZone;
		this.flatNo = flatNo;
		this.floorNo = floorNo;
		this.description = description;
		this.name = name;
		this.streetName = streetName;
		this.terCode = terCode;
		this.jurCode = jurCode;
		this.validityStartDate = validityStartDate;
		this.flag = flag;
	}


	public VTrnBldWbdQuoId getKey(){
		return key;
	}

	public void setKey( VTrnBldWbdQuoId key ){
		this.key = key;
	}




	public Long getPolicyId(){
		return policyId;
	}

	public void setPolicyId( Long policyId ){
		this.policyId = policyId;
	}

	public BigDecimal getOccTradeGrp(){
		return occTradeGrp;
	}

	public void setOccTradeGrp( BigDecimal occTradeGrp ){
		this.occTradeGrp = occTradeGrp;
	}

	public BigDecimal getDirCode(){
		return dirCode;
	}

	public void setDirCode( BigDecimal dirCode ){
		this.dirCode = dirCode;
	}

	public String getFreeZone(){
		return freeZone;
	}

	public void setFreeZone( String freeZone ){
		this.freeZone = freeZone;
	}

	public String getFlatNo(){
		return flatNo;
	}

	public void setFlatNo( String flatNo ){
		this.flatNo = flatNo;
	}

	public String getFloorNo(){
		return floorNo;
	}

	public void setFloorNo( String floorNo ){
		this.floorNo = floorNo;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription( String description ){
		this.description = description;
	}

	public String getName(){
		return name;
	}

	public void setName( String name ){
		this.name = name;
	}

	public String getStreetName(){
		return streetName;
	}

	public void setStreetName( String streetName ){
		this.streetName = streetName;
	}

	public BigDecimal getTerCode(){
		return terCode;
	}

	public void setTerCode( BigDecimal terCode ){
		this.terCode = terCode;
	}

	public BigDecimal getJurCode(){
		return jurCode;
	}

	public void setJurCode( BigDecimal jurCode ){
		this.jurCode = jurCode;
	}

	public Date getValidityStartDate(){
		return validityStartDate;
	}

	public void setValidityStartDate( Date validityStartDate ){
		this.validityStartDate = validityStartDate;
	}

	public String getFlag(){
		return flag;
	}

	public void setFlag( String flag ){
		this.flag = flag;
	}


	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( description == null ) ? 0 : description.hashCode() );
		result = prime * result + ( ( dirCode == null ) ? 0 : dirCode.hashCode() );
		result = prime * result + ( ( flag == null ) ? 0 : flag.hashCode() );
		result = prime * result + ( ( flatNo == null ) ? 0 : flatNo.hashCode() );
		result = prime * result + ( ( floorNo == null ) ? 0 : floorNo.hashCode() );
		result = prime * result + ( ( freeZone == null ) ? 0 : freeZone.hashCode() );
		result = prime * result + ( ( jurCode == null ) ? 0 : jurCode.hashCode() );
		result = prime * result + ( ( key == null ) ? 0 : key.hashCode() );
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
		result = prime * result + ( ( occTradeGrp == null ) ? 0 : occTradeGrp.hashCode() );
		result = prime * result + ( ( policyId == null ) ? 0 : policyId.hashCode() );
		result = prime * result + ( ( streetName == null ) ? 0 : streetName.hashCode() );
		result = prime * result + ( ( terCode == null ) ? 0 : terCode.hashCode() );
		result = prime * result + ( ( validityStartDate == null ) ? 0 : validityStartDate.hashCode() );
		return result;
	}


	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		VTrnBldWbdQuo other = (VTrnBldWbdQuo) obj;
		if( description == null ){
			if( other.description != null ) return false;
		}
		else if( !description.equals( other.description ) ) return false;
		if( dirCode == null ){
			if( other.dirCode != null ) return false;
		}
		else if( !dirCode.equals( other.dirCode ) ) return false;
		if( flag == null ){
			if( other.flag != null ) return false;
		}
		else if( !flag.equals( other.flag ) ) return false;
		if( flatNo == null ){
			if( other.flatNo != null ) return false;
		}
		else if( !flatNo.equals( other.flatNo ) ) return false;
		if( floorNo == null ){
			if( other.floorNo != null ) return false;
		}
		else if( !floorNo.equals( other.floorNo ) ) return false;
		if( freeZone == null ){
			if( other.freeZone != null ) return false;
		}

		else if( !jurCode.equals( other.jurCode ) ) return false;
		if( key == null ){
			if( other.key != null ) return false;
		}
		else if( !key.equals( other.key ) ) return false;
		if( name == null ){
			if( other.name != null ) return false;
		}
		else if( !name.equals( other.name ) ) return false;
		if( occTradeGrp == null ){
			if( other.occTradeGrp != null ) return false;
		}
		else if( !occTradeGrp.equals( other.occTradeGrp ) ) return false;
		if( policyId == null ){
			if( other.policyId != null ) return false;
		}
		else if( !policyId.equals( other.policyId ) ) return false;
		if( streetName == null ){
			if( other.streetName != null ) return false;
		}
		else if( !streetName.equals( other.streetName ) ) return false;
		if( terCode == null ){
			if( other.terCode != null ) return false;
		}
		else if( !terCode.equals( other.terCode ) ) return false;
		if( validityStartDate == null ){
			if( other.validityStartDate != null ) return false;
		}
		else if( !validityStartDate.equals( other.validityStartDate ) ) return false;
		return true;
	}



}
