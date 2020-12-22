/**
 * 
 */
package com.rsaame.pas.vo.bus;

/**
 * @author m1019703
 *
 */
public class IndemnityPeriodVO extends RiskGroupDetails {

	private Integer indemnityPeriod;
	private Double deductible;
	private static final long serialVersionUID = 1L;
	
	public Integer getIndemnityPeriod() {
		return indemnityPeriod;
	}
	public void setIndemnityPeriod(Integer indemnityPeriod) {
		this.indemnityPeriod = indemnityPeriod;
	}
	public Double getDeductible() {
		return deductible;
	}
	public void setDeductible(Double deductible) {
		this.deductible = deductible;
	}
	
	
	
}
