package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.bus.IPolQuoType;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * An input wrapper VO for delete location functionality.
 */
public class DelLocationInputVO extends BaseVO implements IPolQuoType{
	private static final long serialVersionUID = 1L;
	
	private PolicyVO policy;
	private Integer sectionId;
	private Integer buildingId;
	private boolean cascade;
	private Boolean isQuote;

	public PolicyVO getPolicy(){
		return policy;
	}

	public void setPolicy( PolicyVO policy ){
		this.policy = policy;
	}

	public Integer getSectionId(){
		return sectionId;
	}

	public void setSectionId( Integer sectionId ){
		this.sectionId = sectionId;
	}

	public Integer getBuildingId(){
		return buildingId;
	}

	public void setBuildingId( Integer buildingId ){
		this.buildingId = buildingId;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		block: {
			if( "policy".equals( fieldName ) ){ fieldValue = getPolicy(); break block; }
			if( "sectionId".equals( fieldName ) ){ fieldValue = getSectionId(); break block; }
			if( "buildingId".equals( fieldName ) ){ fieldValue = getBuildingId(); break block; }
			if( "cascade".equals( fieldName ) ){ fieldValue = isCascade(); break block; }
		}

		return fieldValue;
	}

	public boolean isCascade() {
		return cascade;
	}

	public void setCascade(boolean cascade) {
		this.cascade = cascade;
	}

	@Override
	public Boolean isQuote(){
		return isQuote;
	}

	@Override
	public void setQuote( Boolean isQuote ){
		this.isQuote = isQuote;
	}

}
