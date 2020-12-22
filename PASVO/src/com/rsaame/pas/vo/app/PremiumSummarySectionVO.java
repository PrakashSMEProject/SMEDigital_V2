/**
 * 
 */
package com.rsaame.pas.vo.app;

import java.math.BigDecimal;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author M1014644
 *
 */
public class PremiumSummarySectionVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Short clazz;
	private Short secId;
	private String secName;
	private BigDecimal secSI;
	private BigDecimal secPrm;
	private BigDecimal secActualPrm;
	private Double secAvgCommPrec;
	private Double secAvgComm;
	private BigDecimal secAvgRate;
	private BigDecimal discountedPrm;
	private BigDecimal govtTaxAmt;
	private Double govtTax;
	
	/*VAT*/
	private BigDecimal vatTaxAmt;
	private Double vattTax; 
	
	public BigDecimal getVatTaxAmt() {
		vatTaxAmt = getSecPrm().multiply( BigDecimal.valueOf( getVattTax() ) ).divide( BigDecimal.valueOf( 100.00 ));
		return vatTaxAmt;
	}

	public void setVatTaxAmt(BigDecimal vatTaxAmt) {
		this.vatTaxAmt = vatTaxAmt;
	}

	public Double getVattTax() {
		return vattTax;
	}

	public void setVattTax(Double vattTax) {
		this.vattTax = vattTax;
	}

	private List <PremiumSummaryVO> prmSummarySec = new com.mindtree.ruc.cmn.utils.List<PremiumSummaryVO>(PremiumSummaryVO.class) ;
	
	
	
	/**
	 * @return the govtTaxAmt
	 */
	public final BigDecimal getGovtTaxAmt(){
		govtTaxAmt = getSecPrm().multiply( BigDecimal.valueOf( getGovtTax() ) ).divide( BigDecimal.valueOf( 100.00 ));
		return govtTaxAmt;
	}

	/**
	 * @param govtTaxAmt the govtTaxAmt to set
	 */
	public final void setGovtTaxAmt( BigDecimal govtTaxAmt ){
		this.govtTaxAmt = govtTaxAmt;
	}

	/**
	 * @return the govtTax
	 */
	public final Double getGovtTax(){
		if(govtTax==null)return 0.0;
		return govtTax;
	}

	/**
	 * @param govtTax the govtTax to set
	 */
	public final void setGovtTax( Double govtTax ){
		this.govtTax = govtTax;
	}

	public Short getClazz(){
		return clazz;
	}

	/*public void setClazz( Short clazz ){
		this.clazz = clazz;
	}
*/
	public Short getSecId(){
		return secId;
	}

	public void setSecId( Short secId ){
		this.secId = secId;
		this.clazz = Short.valueOf( Utils.getSingleValueAppConfig( Utils.concat( "SEC_", secId.toString() ) ) );
	}

	public String getSecName(){
		return secName;
	}

	public void setSecName( String secName ){
		this.secName = secName;
	}

	public BigDecimal getSecSI(){
		
		Double secSI =  0.0 ;
		for( PremiumSummaryVO prmSummaryLoc : prmSummarySec ){
			if(!Utils.isEmpty( prmSummaryLoc.getCoverSiAmt() )){
				secSI += prmSummaryLoc.getCoverSiAmt().doubleValue();
			}
		}
		this.secSI = BigDecimal.valueOf( secSI );
		return this.secSI ;
	}

	/*public void setSecSI( BigDecimal secSI ){
		this.secSI = secSI;
	}*/

	public BigDecimal getSecPrm(){
		
		Double secPrm =  0.0 ;
		for( PremiumSummaryVO prmSummaryLoc : prmSummarySec ){
			if(!Utils.isEmpty( prmSummaryLoc.getCoverPrmAmt())){
				secPrm += prmSummaryLoc.getCoverPrmAmt().doubleValue();
			}
		}
		this.secPrm = BigDecimal.valueOf( secPrm );
		return this.secPrm;
	}

	/*public void setSecPrm( BigDecimal secPrm ){
		this.secPrm = secPrm;
	}*/

	public BigDecimal getSecActualPrm(){
		return secActualPrm;
	}

	public void setSecActualPrm( BigDecimal secActualPrm ){
		this.secActualPrm = secActualPrm;
	}

	public List<PremiumSummaryVO> getPrmSummarySec(){
		return prmSummarySec;
	}

	public void setPrmSummarySec( List<PremiumSummaryVO> prmSummarySec ){
		this.prmSummarySec = prmSummarySec;
	}

	public Double getSecAvgCommPrec(){
		
		this.secAvgCommPrec = ( (!Utils.isEmpty( getSecPrm() )?getSecPrm().doubleValue():0.0 )  * ( !Utils.isEmpty( getSecAvgComm() )?getSecAvgComm():0.0 ) )/100;
		
		return this.secAvgCommPrec;
	}

	/*public void setSecAvgCommPrec( Double secAvgCommPrec ){
		this.secAvgCommPrec = secAvgCommPrec;
	}*/

	public Double getSecAvgComm(){
		
		Double avgComm = 0.0;
		int i = 0;
		for( PremiumSummaryVO prmSummarySection : this.prmSummarySec ){
			if(!Utils.isEmpty( prmSummarySection.getCommission() )){
					avgComm += prmSummarySection.getCommission().doubleValue();
					i++;
			}
		}
		if(i != 0){
			avgComm =avgComm/i;
		}
		this.secAvgComm = avgComm;
		
		return this.secAvgComm;
	}
	
	public BigDecimal getSecAvgRate(){
		
		BigDecimal secRate = BigDecimal.valueOf( 0.0 ) ;
		for( PremiumSummaryVO prmSummaryLoc : prmSummarySec ){
			if(!Utils.isEmpty( prmSummaryLoc.getRate(this.secId))){
				secRate = secRate.add( prmSummaryLoc.getRate(this.secId) );
			}
		}
		this.secAvgRate =  secRate ;
		return this.secAvgRate;
	}

	/*public void setSecAvgComm( Double secAvgComm ){
		this.secAvgComm = secAvgComm;
	}*/

	@Override
	public Object getFieldValue( String fieldName ){
		// TODO Auto-generated method stub
		return null;
	}
	

}
