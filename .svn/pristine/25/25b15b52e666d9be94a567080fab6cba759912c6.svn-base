package com.rsaame.pas.vo.bus;

/**
 * @author m1019834
 * This class acts as an object reference for 
	 * 1. GPAUnnamedEmpVO
	 * 2. GPANamedEmpVO
	 * Since there will be multiple records for each dependent Classes 
 *
 */
public class GroupPersonalAccidentVO extends RiskGroupDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2351139078699029122L;
	
	private Double aggregateLimit;
	private Double gpaDeductible;
		
	

	private	java.util.List<GPAUnnammedEmpVO> gpaUnnammedEmpVO = new com.mindtree.ruc.cmn.utils.List<GPAUnnammedEmpVO>(GPAUnnammedEmpVO.class);
	private	java.util.List<GPANammedEmpVO> gpaNammedEmpVO = new com.mindtree.ruc.cmn.utils.List<GPANammedEmpVO>(GPANammedEmpVO.class);
	
	private String voStatusChk ;
	
	
	public String getVoStatusChk(){
		return voStatusChk;
	}

	/**
	 * @param voStatuschk the voStatuschk to set
	 */
	public void setVoStatusChk( String voStatusChk ){
		this.voStatusChk = voStatusChk;
	}
	
	/**
	 * @return the aggregateLimit
	 */
	public Double getAggregateLimit(){
		return aggregateLimit;
	}

	/**
	 * @param aggregateLimit the aggregateLimit to set
	 */
	public void setAggregateLimit( Double aggregateLimit ){
		this.aggregateLimit = aggregateLimit;
	}
	
	public Double getGpaDeductible(){
		return gpaDeductible;
	}

	/**
	 * @param aggregateLimit the aggregateLimit to set
	 */
	public void setGpaDeductible( Double gpaDeductible ){
		this.gpaDeductible = gpaDeductible;
	}
	// This is to get the GPA Unnamed Employee values 
	public java.util.List<GPAUnnammedEmpVO> getGpaUnnammedEmpVO(){
		return gpaUnnammedEmpVO;
	}
	
	public void setGpaUnnammedEmpVO( java.util.List<GPAUnnammedEmpVO> gpaUnnammedEmpVO ){
		this.gpaUnnammedEmpVO = gpaUnnammedEmpVO;
	}
	
	// This is to get the GPA Named Employee values 
	public java.util.List<GPANammedEmpVO> getGpaNammedEmpVO(){
		return gpaNammedEmpVO;
	}
	public void setGpaNammedEmpVO( java.util.List<GPANammedEmpVO> gpaNammedEmpVO ){
		this.gpaNammedEmpVO = gpaNammedEmpVO;
	}
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "aggregateLimit".equals( fieldName ) ) fieldValue = getAggregateLimit();
		if( "gpaDeductible".equals( fieldName ) ) fieldValue = getGpaDeductible();
		if( "gpaUnnammedEmpVO".equals( fieldName ) ) fieldValue = getGpaUnnammedEmpVO();
		if( "gpaNammedEmpVO".equals( fieldName ) ) fieldValue = getGpaNammedEmpVO();
		
		
		return fieldValue;
	}
	
	@Override
	public String toString() {
		return "GroupPersonalAccidentVO [aggregateLimit = "+aggregateLimit+"gpaDeductible"+gpaDeductible+"gpaUnnammedEmpVO=" + gpaUnnammedEmpVO + "gpaNammedEmpVO"+gpaNammedEmpVO+"]";
	}
	
 
}

	
	
	
