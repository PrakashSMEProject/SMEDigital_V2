package com.rsaame.pas.vo.app;

import java.math.BigDecimal;
import java.util.Date;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;


public class PremiumSummaryVO extends BaseVO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long linkingId;
	private Long policyId;
	private Long endtId;
	private Short class_;
	private Short secId;
	private String secName;
	private Long locationId;
	private String locationName;
	private BigDecimal coverId;
	private BigDecimal commission;
	private BigDecimal commissionAmt;
	private BigDecimal coverSiAmt;
	private BigDecimal coverPrmAmt;
	private Date valStartDate;
	private Date valExpDate;
	private Byte status;
	private Character polQuoFlag;
	
	
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "linkingId".equals( fieldName ) ) fieldValue = getLinkingId();
		if( "policyId".equals( fieldName ) ) fieldValue = getPolicyId();
		if( "endtId".equals( fieldName ) ) fieldValue = getEndtId();
		if( "class_".equals( fieldName ) ) fieldValue = getClass_();
		if( "secId".equals( fieldName ) ) fieldValue = getSecId();
		if( "secName".equals( fieldName ) ) fieldValue = getSecName();
		if( "locationId".equals( fieldName ) ) fieldValue = getLocationId();
		if( "locationName".equals( fieldName ) ) fieldValue = getLocationName();
		if( "coverId".equals( fieldName ) ) fieldValue = getCoverId();
		if( "commission".equals( fieldName ) ) fieldValue = getCommission();
		if( "coverSiAmt".equals(fieldName)) fieldValue = getCoverSiAmt();
		if( "coverPrmAmt".equals(fieldName)) fieldValue = getCoverPrmAmt();
		if( "valStartDate".equals(fieldName)) fieldValue = getValStartDate();
		if( "valExpDate".equals(fieldName)) fieldValue = getValExpDate();
		if( "status".equals( fieldName ) ) fieldValue = getStatus();
		if( "polQuoFlag".equals( fieldName ) ) fieldValue = getPolQuoFlag();
		
		return fieldValue;
	}
	/**
	 * @return the commission
	 */
	public BigDecimal getCommission(){
		return commission;
	}


	/**
	 * @return the coverSiAmt
	 */
	public BigDecimal getCoverSiAmt(){
		return coverSiAmt;
	}


	/**
	 * @return the coverPrmAmt
	 */
	public BigDecimal getCoverPrmAmt(){
		return coverPrmAmt;
	}


	/**
	 * @return the valStartDate
	 */
	public Date getValStartDate(){
		return valStartDate;
	}


	/**
	 * @return the valExpDate
	 */
	public Date getValExpDate(){
		return valExpDate;
	}


	/**
	 * @return the status
	 */
	public Byte getStatus(){
		return status;
	}


	/**
	 * @return the polQuoFlag
	 */
	public Character getPolQuoFlag(){
		return polQuoFlag;
	}


	/**
	 * @return the linkingId
	 */
	public Long getLinkingId(){
		return linkingId;
	}


	/**
	 * @return the policyId
	 */
	public Long getPolicyId(){
		return policyId;
	}


	/**
	 * @return the endtId
	 */
	public Long getEndtId(){
		return endtId;
	}


	/**
	 * @return the secId
	 */
	public Short getSecId(){
		return secId;
	}


	/**
	 * @return the secName
	 */
	public String getSecName(){
		return secName;
	}


	/**
	 * @return the locationId
	 */
	public Long getLocationId(){
		return locationId;
	}


	/**
	 * @return the locationName
	 */
	public String getLocationName(){
		return locationName;
	}


	/**
	 * @return the coverId
	 */
	public BigDecimal getCoverId(){
		return coverId;
	}


	/**
	 * @param commission the commission to set
	 */
	public void setCommission( BigDecimal commission ){
		this.commission = commission;
	}


	/**
	 * @param coverSiAmt the coverSiAmt to set
	 */
	public void setCoverSiAmt( BigDecimal coverSiAmt ){
		this.coverSiAmt = coverSiAmt;
	}


	/**
	 * @param coverPrmAmt the coverPrmAmt to set
	 */
	public void setCoverPrmAmt( BigDecimal coverPrmAmt ){
		this.coverPrmAmt = coverPrmAmt;
	}


	/**
	 * @param valStartDate the valStartDate to set
	 */
	public void setValStartDate( Date valStartDate ){
		this.valStartDate = valStartDate;
	}


	/**
	 * @param valExpDate the valExpDate to set
	 */
	public void setValExpDate( Date valExpDate ){
		this.valExpDate = valExpDate;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus( Byte status ){
		this.status = status;
	}


	/**
	 * @param polQuoFlag the polQuoFlag to set
	 */
	public void setPolQuoFlag( Character polQuoFlag ){
		this.polQuoFlag = polQuoFlag;
	}


	/**
	 * @param linkingId the linkingId to set
	 */
	public void setLinkingId( Long linkingId ){
		this.linkingId = linkingId;
	}


	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId( Long policyId ){
		this.policyId = policyId;
	}


	/**
	 * @param endtId the endtId to set
	 */
	public void setEndtId( Long endtId ){
		this.endtId = endtId;
	}


	/**
	 * @param secId the secId to set
	 */
	public void setSecId( Short secId ){
		this.secId = secId;
	}


	/**
	 * @param secName the secName to set
	 */
	public void setSecName( String secName ){
		this.secName = secName;
	}


	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId( Long locationId ){
		this.locationId = locationId;
	}


	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName( String locationName ){
		this.locationName = locationName;
	}


	/**
	 * @param coverId the coverId to set
	 */
	public void setCoverId( BigDecimal coverId ){
		this.coverId = coverId;
	}
	/**
	 * @return the class_
	 */
	public Short getClass_(){
		return class_;
	}
	/**
	 * @param class_ the class_ to set
	 */
	public void setClass_( Short class_ ){
		this.class_ = class_;
	}

	public BigDecimal getCommissionAmt(){
		this.commissionAmt = BigDecimal.valueOf( ( ( !Utils.isEmpty( getCommission() )? getCommission().doubleValue():0.0 ) *  ( !Utils.isEmpty( getCoverPrmAmt() )?getCoverPrmAmt().doubleValue():0.0 ) )/100 );
		return this.commissionAmt;
	}
	public BigDecimal getRate(Short secId){
		BigDecimal rate = BigDecimal.valueOf( 0 );
		if((!Utils.isEmpty( secId ))  && ( (!Utils.isEmpty( Short.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" )) ) && Short.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" )).equals( secId )) 
				|| (!Utils.isEmpty( Short.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" )) ) && Short.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" )).equals( secId )))){
			if( !Utils.isEmpty( this.coverPrmAmt ) &&  !( this.coverPrmAmt .compareTo( BigDecimal.valueOf( 0 ) ) <= 0 ) ){
			rate = this.coverPrmAmt;
			}
		} else{
			if(  !Utils.isEmpty( this.coverSiAmt ) && !Utils.isEmpty( this.coverPrmAmt ) &&  !( this.coverPrmAmt .compareTo( BigDecimal.valueOf( 0 ) ) <= 0 ) ){
				
				//rate = this.coverPrmAmt.divide( this.coverSiAmt ).setScale( 2 ).multiply( BigDecimal.valueOf( 100.00 ) );
				rate = BigDecimal.valueOf(this.coverPrmAmt.doubleValue()/ ( this.coverSiAmt.doubleValue() )).multiply( BigDecimal.valueOf( 100.00 ) );
			}
		}
		return rate;
	}
}
