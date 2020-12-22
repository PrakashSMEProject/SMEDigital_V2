package com.rsaame.pas.dao.model;

public class VRenewalStatusEmailReportPasId  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer polClassCode;
	private Long policyNo;

	public VRenewalStatusEmailReportPasId( ){
	}
	
	/**
	 * @param quotationNo
	 * @param policyId
	 * @param polEndtId
	 * @param polClassCode
	 * @param polRefPolicyId
	 */
	public VRenewalStatusEmailReportPasId( Integer polClassCode,Long policyNo){
		super();
		
		this.polClassCode = polClassCode;
		this.policyNo = policyNo;
	
	}

	public Long getPolicyNo(){
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo( Long policyNo ){
		this.policyNo = policyNo;
	}

	/**
	 * @return the polClassCode
	 */
	public Integer getPolClassCode(){
		return polClassCode;
	}

	/**
	 * @param polClassCode the polClassCode to set
	 */
	public void setPolClassCode( Integer polClassCode ){
		this.polClassCode = polClassCode;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( polClassCode == null ) ? 0 : polClassCode.hashCode() );
		result = prime * result + ( ( policyNo == null ) ? 0 : policyNo.hashCode() );
		
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
		VRenewalStatusEmailReportPasId other = (VRenewalStatusEmailReportPasId) obj;
		if( polClassCode == null ){
			if( other.polClassCode != null ) return false;
		}
		else if( !polClassCode.equals( other.polClassCode ) ) return false;
		else if( !policyNo.equals( other.policyNo ) ) return false;
		/*if( polRefPolicyId == null ){
			if( other.polRefPolicyId != null ) return false;
		}
		else if( !polRefPolicyId.equals( other.polRefPolicyId ) ) return false;
		if( policyId == null ){
			if( other.policyId != null ) return false;
		}*/
		/*else if( !policyId.equals( other.policyId ) ) return false;
		if( quotationNo == null ){
			if( other.quotationNo != null ) return false;
		}
		else if( !quotationNo.equals( other.quotationNo ) ) return false;*/
		return true;
	}

}
