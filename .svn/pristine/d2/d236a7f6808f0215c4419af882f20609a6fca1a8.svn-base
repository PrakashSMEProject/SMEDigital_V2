/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author m1019703
 *
 */
public class BIVO extends RiskGroupDetails {

	private static final long serialVersionUID = -3057564132280391484L;
	private	java.util.List<SumInsuredVO> sumInsuredDets; 
	//private IndemnityPeriodVO indemnityPeriodVO; // 
	
	
	private Integer indemnityPeriod;
	private long deductible;
	private Double estimatedGrossIncome;
	private Double workingLimit;
	private Double rentRecievable;
	private boolean isWorkingLimitCommited;
	private Long biColId;
	private Long biCwsAcwlId;
	private Long biCwsRentId;
	private Long biCwsEGIncomeId;
	private boolean isPremiumCommited;
	private Integer hazardLevel;
	//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
	/*private Double annualRent;
	private Long biCwsAnnualRentId;*/
	
	public void setHazardLevel(Integer hazardLevel) {
		this.hazardLevel = hazardLevel;
	}
	public Integer getIndemnityPeriod() {
		return indemnityPeriod;
	}
	public void setIndemnityPeriod(Integer indemnityPeriod) {
		this.indemnityPeriod = indemnityPeriod;
	}
	
	public java.util.List<SumInsuredVO> getSumInsuredDets() {
		return sumInsuredDets;
	}
	public void setSumInsuredDets(java.util.List<SumInsuredVO> sumInsuredDets) {
		this.sumInsuredDets = sumInsuredDets;
	}
	@Override
	public String toString()
	{
		return "BIVO [indemnityPeriod=" + indemnityPeriod + ", deductible=" + deductible + "]";
	}
	public Double getEstimatedGrossIncome() {
		return estimatedGrossIncome;
	}
	public void setEstimatedGrossIncome(Double estimatedGrossIncome) {
		this.estimatedGrossIncome = estimatedGrossIncome;
	}
	public Double getWorkingLimit() {
		return workingLimit;
	}
	public void setWorkingLimit(Double workingLimit) {
		this.workingLimit = workingLimit;
	}
	public Double getRentRecievable() {
		return rentRecievable;
	}
	public void setRentRecievable(Double rentRecievable) {
		this.rentRecievable = rentRecievable;
	}
	
	
	public boolean isWorkingLimitCommited() {
		return isWorkingLimitCommited;
	}
	public void setWorkingLimitCommited(boolean isWorkingLimitCommited) {
		this.isWorkingLimitCommited = isWorkingLimitCommited;
	}
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "indemnityPeriod".equals( fieldName ) ) fieldValue = getIndemnityPeriod();
		if( "deductible".equals( fieldName ) ) fieldValue = getDeductible();
		if( "estimatedGrossIncome".equals( fieldName ) ) fieldValue = getEstimatedGrossIncome();
		if( "workingLimit".equals( fieldName ) ) fieldValue = getWorkingLimit();
		if( "rentRecievable".equals( fieldName ) ) fieldValue = getRentRecievable();
		//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
		//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
		/*if( "annualRent".equals( fieldName ) ) fieldValue = getAnnualRent();*/
		//if( "cashResidencePremium".equals( fieldName ) ) fieldValue = getCashResidencePremium();
		

		return fieldValue;
	}
	public long getDeductible() {
		return deductible;
	}
	public void setDeductible(long deductible) {
		this.deductible = deductible;
	}
	public Long getBiColId() {
		return biColId;
	}
	public void setBiColId(Long biColId) {
		this.biColId = biColId;
	}
	public Long getBiCwsAcwlId() {
		return biCwsAcwlId;
	}
	public void setBiCwsAcwlId(Long biCwsAcwlId) {
		this.biCwsAcwlId = biCwsAcwlId;
	}
	public Long getBiCwsRentId() {
		return biCwsRentId;
	}
	public void setBiCwsRentId(Long biCwsRentId) {
		this.biCwsRentId = biCwsRentId;
	}
	public boolean isPremiumCommited(){
		return isPremiumCommited;
	}
	public void setPremiumCommited( boolean isPremiumCommited ){
		this.isPremiumCommited = isPremiumCommited;
	}
	public Long getBiCwsEGIncomeId(){
		return biCwsEGIncomeId;
	}
	public void setBiCwsEGIncomeId( Long biCwsEGIncomeId ){
		this.biCwsEGIncomeId = biCwsEGIncomeId;
	}
	public Integer getHazardLevel() {
		return hazardLevel;
	}
	//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
	/*public Double getAnnualRent() {
		return annualRent;
	}
	public void setAnnualRent(Double annualRent) {
		this.annualRent = annualRent;
	}
	public Long getBiCwsAnnualRentId() {
		return biCwsAnnualRentId;
	}
	public void setBiCwsAnnualRentId(Long biCwsAnnualRentId) {
		this.biCwsAnnualRentId = biCwsAnnualRentId;
	}*/
	//Added END for Adventnet Id:103286;
	
}

	
	
	
