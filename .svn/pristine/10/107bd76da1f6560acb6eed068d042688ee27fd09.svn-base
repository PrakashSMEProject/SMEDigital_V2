/**
 * 
 */
package com.rsaame.pas.vo.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author M1014644
 *
 */
public class PremiumSummary extends BaseVO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long linkingId;
	private Long endtId;
	private Character polQuoFlag;
	private Byte polQuoStatus;
	private Byte currencyCode;
	private Byte currencyDesc;
	/*
	 * Policy/ Quote level si and premium
	 */
	private BigDecimal si;
	private BigDecimal prm;
	private BigDecimal actualPrm;
	private Double avgCommPrec;
	private Double avgComm;
	private Double govtTax;
	private Double discLoad;
	private Double policyFee;
	private Double prmPayable;
	private BigDecimal discLoadAmt;
	private BigDecimal rate;
	private BigDecimal discountedPrm;
	private BigDecimal discountedRate;
	private BigDecimal discountedComm;
	private BigDecimal govtTaxAmt;
	private BigDecimal nonTaxPayablePrm ; 
	private Double totComm;
	
	
	private BigDecimal vatAmt;
	private BigDecimal vatPerc;
	

	public BigDecimal getVatAmt() {
		  BigDecimal vatAmt = BigDecimal.ZERO;
		   for (PremiumSummarySectionVO prmSummarySection : prmSummarySec) {
		    if (!Utils.isEmpty(prmSummarySection.getVatTaxAmt())) {
		       vatAmt = vatAmt.add(prmSummarySection.getVatTaxAmt());
		      }
		       }
		       return vatAmt;
	}

	public void setVatAmt(BigDecimal vatAmt) {
		this.vatAmt = vatAmt;
	}

	public BigDecimal getVatPerc() {
		return vatPerc;
	}

	public void setVatPerc(BigDecimal vatPerc) {
		this.vatPerc = vatPerc;
	}


	private List <PremiumSummarySectionVO> prmSummarySec = new com.mindtree.ruc.cmn.utils.List<PremiumSummarySectionVO>(PremiumSummarySectionVO.class) ;
	
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		return null;
	}

	public BigDecimal getNonTaxcomm() {
		return nonTaxPayablePrm;
	}


	public void setNonTaxcomm(BigDecimal nonTaxcomm) {
		this.nonTaxPayablePrm = nonTaxcomm;
	}


	public Long getLinkingId(){
		return linkingId;
	}


	public void setLinkingId( Long linkingId ){
		this.linkingId = linkingId;
	}


	public Long getEndtId(){
		return endtId;
	}


	/**
	 * @return the govtTaxAmt
	 */
	public final BigDecimal getGovtTaxAmt(){
		
		BigDecimal govtTaxAmt = BigDecimal.ZERO ;
		for( PremiumSummarySectionVO prmSummarySection : this.prmSummarySec ){
			if(!Utils.isEmpty( prmSummarySection.getGovtTaxAmt() )){
				govtTaxAmt = govtTaxAmt.add( prmSummarySection.getGovtTaxAmt() );
			}
		}
		return govtTaxAmt;
	}


	/**
	 * @param govtTaxAmt the govtTaxAmt to set
	 */
	public final void setGovtTaxAmt( BigDecimal govtTaxAmt ){
		this.govtTaxAmt = govtTaxAmt;
	}


	public void setEndtId( Long endtId ){
		this.endtId = endtId;
	}


	public Character getPolQuoFlag(){
		return polQuoFlag;
	}


	public void setPolQuoFlag( Character polQuoFlag ){
		this.polQuoFlag = polQuoFlag;
	}


	public Byte getPolQuoStatus(){
		return polQuoStatus;
	}


	public void setPolQuoStatus( Byte polQuoStatus ){
		this.polQuoStatus = polQuoStatus;
	}


	public BigDecimal getSi(){
		Double si =  0.0 ;
		for( PremiumSummarySectionVO prmSummarySection : this.prmSummarySec ){
			if(!Utils.isEmpty( prmSummarySection.getSecSI() )){
				si += prmSummarySection.getSecSI().doubleValue();
			}
		}
		this.si = BigDecimal.valueOf( si );
		return this.si ;
	}


	/*public void setSi( BigDecimal si ){
		this.si = si;
	}*/


	public BigDecimal getPrm(){
		
		Double prm =  0.0 ;
		for( PremiumSummarySectionVO prmSummarySection : this.prmSummarySec ){
			if(!Utils.isEmpty( prmSummarySection.getSecPrm() )){
				prm += prmSummarySection.getSecPrm().doubleValue();
			}
		}
		this.prm = BigDecimal.valueOf( prm );
		return this.prm;
	}


	/*public void setPrm( BigDecimal prm ){
		this.prm = prm;
	}*/


	public BigDecimal getActualPrm(){
		return actualPrm;
	}


	public void setActualPrm( BigDecimal actualPrm ){
		this.actualPrm = actualPrm;
	}

	public Double getAvgCommPrec(){
		this.avgCommPrec = ( getAvgComm() * getPrm().doubleValue() )/100;
		return this.avgCommPrec;
	}


	/*public void setAvgCommPrec( Double avgCommPrec ){
		this.avgCommPrec = avgCommPrec;
	}*/


	public Double getAvgComm(){
		Double avgComm = 0.0;
		int i = 0;
		for( PremiumSummarySectionVO prmSummarySection : this.prmSummarySec ){
			if(!Utils.isEmpty( prmSummarySection.getSecAvgComm() )){
				if( !prmSummarySection.getSecAvgComm().equals( 0.0 )){
					avgComm += prmSummarySection.getSecAvgComm();
					i++;
				}
				
				
			}
		}
		if(i != 0){
			avgComm =avgComm/i;
		}
		this.avgComm = avgComm;
		return this.avgComm;
	}
	/*
	 * Added rate field to Premium page.
	 */
	public BigDecimal getSecAvgRate(){
		
		BigDecimal secRate = BigDecimal.valueOf( 0.0 ) ;
		for( PremiumSummarySectionVO prmSummaryLoc : prmSummarySec ){
			if(!Utils.isEmpty( prmSummaryLoc.getSecAvgRate())){
				secRate = secRate.add( prmSummaryLoc.getSecAvgRate() );
			}
		}
		this.rate =  secRate ;
		return this.rate;
	}
	/*
	 * 
	 */
	public BigDecimal getdiscountedPrm(){
		BigDecimal discountedPrm = BigDecimal.valueOf( 0.0 ) ;
		for( PremiumSummarySectionVO prmSummaryLoc : prmSummarySec ){
			if(!Utils.isEmpty( prmSummaryLoc.getSecPrm())){
				discountedPrm = discountedPrm.add( prmSummaryLoc.getSecPrm() );
			}
		}
		this.discountedPrm =  (discountedPrm.multiply( discLoadAmt ).divide( BigDecimal.valueOf( 100d ),2, RoundingMode.DOWN  ) ).add( discountedPrm );
		return this.discountedPrm;
	}

	/*public void setAvgComm( Double avgComm ){
		this.avgComm = avgComm;
	}*/


	public List<PremiumSummarySectionVO> getPrmSummarySec(){
		return prmSummarySec;
	}


	public void setPrmSummarySec( List<PremiumSummarySectionVO> prmSummarySec ){
		this.prmSummarySec = prmSummarySec;
	}


	public Byte getCurrencyCode(){
		return currencyCode;
	}


	public void setCurrencyCode( Byte currencyCode ){
		this.currencyCode = currencyCode;
	}


	public Byte getCurrencyDesc(){
		return currencyDesc;
	}


	public void setCurrencyDesc( Byte currencyDesc ){
		this.currencyDesc = currencyDesc;
	}


	public Double getGovtTax(){
		if(Utils.isEmpty( this.govtTax )){
			this.govtTax = 0.0;
		}
		return this.govtTax;
	}


	public void setGovtTax( Double govtTax ){
		this.govtTax = govtTax;
	}


	public Double getDiscLoad(){
		if(Utils.isEmpty( this.discLoad )){
			this.discLoad = 0.0;
		}
		return this.discLoad;
	}


	public void setDiscLoad( Double discLoad ){
		this.discLoad = discLoad;
	}


	public Double getPolicyFee(){
		
		if(Utils.isEmpty( this.policyFee )){
			this.policyFee = 0.0;
		}
		return this.policyFee;
	}


	public void setPolicyFee( Double policyFee ){
		this.policyFee = policyFee;
	}


	public Double getPrmPayable(){
		
		this.prmPayable = getPrm().doubleValue() + getGovtTax();
		return this.prmPayable;
	}


	public void setPrmPayable( Double prmPayable ){
		this.prmPayable = prmPayable;
	}


	public BigDecimal getDiscLoadAmt(){
		return discLoadAmt;
	}


	public void setDiscLoadAmt( BigDecimal discLoadAmt ){
		this.discLoadAmt = discLoadAmt;
	}


	public void setSi( BigDecimal si ){
		this.si = si;
	}


	public void setPrm( BigDecimal prm ){
		this.prm = prm;
	}


	public void setAvgCommPrec( Double avgCommPrec ){
		this.avgCommPrec = avgCommPrec;
	}


	public void setAvgComm( Double avgComm ){
		this.avgComm = avgComm;
	}

	
	public Double getTotComm(){

		Double totComm = 0.0, secComm = 0.0;

		for( PremiumSummarySectionVO prmSummarySection : this.prmSummarySec ){

			if( !Utils.isEmpty( prmSummarySection.getSecAvgComm() ) ){

				if( !prmSummarySection.getSecAvgComm().equals( Double.valueOf( 0.0 ) ) ){
					secComm = ( prmSummarySection.getSecPrm().doubleValue() * prmSummarySection.getSecAvgComm() ) / 100;
					totComm += secComm;
				}
			}
		}
		this.totComm = totComm;
		return this.totComm;

	}


}
