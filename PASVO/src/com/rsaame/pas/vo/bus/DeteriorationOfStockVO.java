package com.rsaame.pas.vo.bus;

import java.util.ArrayList;
import java.util.List;

public class DeteriorationOfStockVO extends RiskGroupDetails{
	private List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetails = new com.mindtree.ruc.cmn.utils.List<DeteriorationOfStockDetailsVO>(DeteriorationOfStockDetailsVO.class);
	private DeteriorationOfStockUWDetailsVO deteriorationOfStockUWDetails = new DeteriorationOfStockUWDetailsVO();
	
	public List<DeteriorationOfStockDetailsVO> getDeteriorationOfStockDetails(){
		return deteriorationOfStockDetails;
	}
	public void setDeteriorationOfStockDetails( List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetails ){
		this.deteriorationOfStockDetails = deteriorationOfStockDetails;
	}
	public DeteriorationOfStockUWDetailsVO getDeteriorationOfStockUWDetails(){
		return deteriorationOfStockUWDetails;
	}
	public void setDeteriorationOfStockUWDetails( DeteriorationOfStockUWDetailsVO deteriorationOfStockUWDetails ){
		this.deteriorationOfStockUWDetails = deteriorationOfStockUWDetails;
	}
	@Override
	public String toString(){
		return "DeteriorationOfStockVO [deteriorationOfStockDetails=" + deteriorationOfStockDetails + ", deteriorationOfStockUWDetails=" + deteriorationOfStockUWDetails + "]";
	}
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "deteriorationOfStockDetails".equals( fieldName ) ) fieldValue = getDeteriorationOfStockDetails();
		if( "deteriorationOfStockUWDetails".equals( fieldName ) ) fieldValue = getDeteriorationOfStockUWDetails();
		return fieldValue;
	}
}
