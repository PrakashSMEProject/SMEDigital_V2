package com.rsaame.pas.vo.bus;
/*
 * This class is used as VO for Risk Travel Baggage
 * 
 */
public class TravelBaggageVO extends RiskGroupDetails{

	private static final long serialVersionUID = -210317516607204530L;
	
	private TravellingEmployeeVO travellingEmployeeVO;
	private Integer index;
	private java.util.List<TravellingEmployeeVO> travellingEmpDets =  new com.mindtree.ruc.cmn.utils.List<TravellingEmployeeVO>(TravellingEmployeeVO.class);

	
	public TravellingEmployeeVO getTravellingEmployeeVO(){
		return travellingEmployeeVO;
	}

	public void setTravellingEmployeeVO( TravellingEmployeeVO travellingEmployeeVO ){
		this.travellingEmployeeVO = travellingEmployeeVO;
	}
	
	public java.util.List<TravellingEmployeeVO> getTravellingEmpDets(){
		return travellingEmpDets;
	}

	public void setTravellingEmpDets( java.util.List<TravellingEmployeeVO> travellingEmpDets ){
		this.travellingEmpDets = travellingEmpDets;
	}

	public Integer getIndex(){
		return index;
	}

	public void setIndex( Integer index ){
		this.index = index;
	}

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "travellingEmployeeVO".equals( fieldName ) ) fieldValue = getTravellingEmployeeVO();
		if( "travellingEmpDets".equals( fieldName ) ) fieldValue = getTravellingEmpDets();
		if("index".equals( fieldName )) fieldValue = getIndex();
		
		return fieldValue;
	}
	
	@Override
	public String toString() {
		return "TravelBaggageVO [travellingEmpDets=" + travellingEmpDets + "]";
	}	

}
