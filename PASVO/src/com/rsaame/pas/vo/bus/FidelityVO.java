/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author m1016303
 *
 */
public class FidelityVO extends RiskGroupDetails{

	private Double aggregateLimit;
	private Double deductible;
	private float fidAggregateBasePremium;
	private float fidAggregateTermPremium;
	private float fidAggregateMonthlyPremium;
	private float fidAggregateQuaterlyPremium;
	
	private java.util.List<FidelityUnnammedEmployeeVO> unnammedEmployeeDetails = new com.mindtree.ruc.cmn.utils.List<FidelityUnnammedEmployeeVO>( FidelityUnnammedEmployeeVO.class );
	private java.util.List<FidelityNammedEmployeeDetailsVO> fidelityEmployeeDetails = new com.mindtree.ruc.cmn.utils.List<FidelityNammedEmployeeDetailsVO>(
			FidelityNammedEmployeeDetailsVO.class );

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

	/**
	 * @return the unnammedEmployeeDetails
	 */
	public java.util.List<FidelityUnnammedEmployeeVO> getUnnammedEmployeeDetails(){
		return unnammedEmployeeDetails;
	}

	/**
	 * @param unnammedEmployeeDetails the unnammedEmployeeDetails to set
	 */
	public void setUnnammedEmployeeDetails( java.util.List<FidelityUnnammedEmployeeVO> unnammedEmployeeDetails ){
		this.unnammedEmployeeDetails = unnammedEmployeeDetails;
	}

	/**
	 * @return the fidelityEmployeeDetails
	 */
	public java.util.List<FidelityNammedEmployeeDetailsVO> getFidelityEmployeeDetails(){
		return fidelityEmployeeDetails;
	}

	/**
	 * @param fidelityEmployeeDetails the fidelityEmployeeDetails to set
	 */
	public void setFidelityEmployeeDetails( java.util.List<FidelityNammedEmployeeDetailsVO> fidelityEmployeeDetails ){
		this.fidelityEmployeeDetails = fidelityEmployeeDetails;
	}

	/**
	 * @return the deductible
	 */
	public Double getDeductible(){
		return deductible;
	}

	/**
	 * @param deductible the deductible to set
	 */
	public void setDeductible( Double deductible ){
		this.deductible = deductible;
	}



	public float getFidAggregateBasePremium() {
		return fidAggregateBasePremium;
	}

	public void setFidAggregateBasePremium(float fidAggregateBasePremium) {
		this.fidAggregateBasePremium = fidAggregateBasePremium;
	}

	public float getFidAggregateTermPremium() {
		return fidAggregateTermPremium;
	}

	public void setFidAggregateTermPremium(float fidAggregateTermPremium) {
		this.fidAggregateTermPremium = fidAggregateTermPremium;
	}

	public float getFidAggregateMonthlyPremium() {
		return fidAggregateMonthlyPremium;
	}

	public void setFidAggregateMonthlyPremium(float fidAggregateMonthlyPremium) {
		this.fidAggregateMonthlyPremium = fidAggregateMonthlyPremium;
	}

	public float getFidAggregateQuaterlyPremium() {
		return fidAggregateQuaterlyPremium;
	}

	public void setFidAggregateQuaterlyPremium(float fidAggregateQuaterlyPremium) {
		this.fidAggregateQuaterlyPremium = fidAggregateQuaterlyPremium;
	}

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "aggregateLimit".equals( fieldName ) ) fieldValue = getAggregateLimit();
		if( "deductible".equals( fieldName ) ) fieldValue = getDeductible();
		if( "unnammedEmployeeDetails".equals( fieldName ) ) fieldValue = getUnnammedEmployeeDetails();
		if( "fidelityEmployeeDetails".equals( fieldName ) ) fieldValue = getFidelityEmployeeDetails();

		return fieldValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "FidelityVO [aggregateLimit=" + aggregateLimit + ", deductible=" + deductible + ", unnammedEmployeeDetails=" + unnammedEmployeeDetails
				+ ", fidelityEmployeeDetails=" + fidelityEmployeeDetails + "]";
	}

}
