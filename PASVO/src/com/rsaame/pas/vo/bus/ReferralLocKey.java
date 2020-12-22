package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;

public class ReferralLocKey extends BaseVO{
	private static final long serialVersionUID = 1L;

	private Integer sectionId;
		
	private String riskGroupId;

	
	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public String getRiskGroupId() {
		return riskGroupId;
	}

	public void setRiskGroupId(String riskGroupId) {
		this.riskGroupId = riskGroupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode())
				+ ((riskGroupId == null) ? 0 : riskGroupId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		boolean equal = false;

		equalsCheck: {
			if( Utils.isEmpty( obj ) || !( obj instanceof ReferralLocKey ) ) break equalsCheck;

			ReferralLocKey theOtherVO = (ReferralLocKey) obj;
			if( Utils.isEmpty( this.getRiskGroupId() ) && Utils.isEmpty( theOtherVO.getRiskGroupId() ) ){
				equal = true;
				break equalsCheck;
			}
			
			if( !Utils.isEmpty( this.getRiskGroupId() ) ){
				equal = this.riskGroupId.equalsIgnoreCase( theOtherVO.getRiskGroupId()) && this.sectionId.equals(theOtherVO.getSectionId());
			}
		}

		return equal;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "sectionId".equals( fieldName ) ) fieldValue = getSectionId();
		if( "riskGroupId".equals( fieldName ) ) fieldValue = getSectionId();
		
		return fieldValue;
	}
	
}
