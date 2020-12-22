package com.rsaame.pas.dao.model;

// Generated Feb 10, 2012 12:39:36 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;

/**
 * TTrnPremiumQuo generated by hbm2java
 */
public class TTrnPremiumQuo  extends POJOWrapper implements java.io.Serializable {

	private TTrnPremiumQuoId id;
	private short prmClCode;
	private short prmPtCode;
	private int prmRtCode;
	private int prmRcCode;
	private int prmRscCode;
	private BigDecimal prmSumInsured;
	private BigDecimal prmRate;
	private BigDecimal prmPremium;
	private BigDecimal prmCompulsoryExcess;
	private BigDecimal prmVoluntaryExcess;
	private Date prmValidityExpiryDate;
	private long prmEndtId;
	private BigDecimal prmExcessRate;
	private Integer prmRiRskCode;
	private Boolean prmSiInd;
	private Byte prmStatus;
	private Date prmEffectiveDate;
	private Date prmExpiryDate;
	private BigDecimal prmPolSumInsured;
	private Byte prmSitypeCode;
	private Byte prmFnCode;
	private Byte prmSumInsuredCurr;
	private Byte prmPremiumCurr;
	private Integer prmPreparedBy;
	private Date prmPreparedDt;
	private Integer prmModifiedBy;
	private Date prmModifiedDt;
	private Integer prmRiLocCode;
	private Byte prmRateType;
	private BigDecimal prmOldPremium;
	private BigDecimal prmOldSumInsured;
	private BigDecimal prmPremiumActual;
	private BigDecimal prmExpPeriodPrm;
	private BigDecimal prmRenewalLoading;
	private BigDecimal prmLoadDisc;
	private BigDecimal prmValue1;

	public TTrnPremiumQuo() {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	public TTrnPremiumQuo(TTrnPremiumQuoId id, short prmClCode,
			short prmPtCode, int prmRtCode, int prmRcCode, int prmRscCode,
			Date prmValidityExpiryDate, long prmEndtId) {
		this.id = id;
		this.prmClCode = prmClCode;
		this.prmPtCode = prmPtCode;
		this.prmRtCode = prmRtCode;
		this.prmRcCode = prmRcCode;
		this.prmRscCode = prmRscCode;
		this.prmValidityExpiryDate = prmValidityExpiryDate;
		this.prmEndtId = prmEndtId;
	}

	public TTrnPremiumQuo(TTrnPremiumQuoId id, short prmClCode,
			short prmPtCode, int prmRtCode, int prmRcCode, int prmRscCode,
			BigDecimal prmSumInsured, BigDecimal prmRate,
			BigDecimal prmPremium, BigDecimal prmCompulsoryExcess,
			BigDecimal prmVoluntaryExcess, Date prmValidityExpiryDate,
			long prmEndtId, BigDecimal prmExcessRate, Integer prmRiRskCode,
			Boolean prmSiInd, Byte prmStatus, Date prmEffectiveDate,
			Date prmExpiryDate, BigDecimal prmPolSumInsured,
			Byte prmSitypeCode, Byte prmFnCode, Byte prmSumInsuredCurr,
			Byte prmPremiumCurr, Integer prmPreparedBy, Date prmPreparedDt,
			Integer prmModifiedBy, Date prmModifiedDt, Integer prmRiLocCode,
			Byte prmRateType, BigDecimal prmOldPremium,
			BigDecimal prmOldSumInsured, BigDecimal prmPremiumActual,
			BigDecimal prmExpPeriodPrm, BigDecimal prmRenewalLoading, BigDecimal prmLoadDisc, BigDecimal prmValue1) {
		this.id = id;
		this.prmClCode = prmClCode;
		this.prmPtCode = prmPtCode;
		this.prmRtCode = prmRtCode;
		this.prmRcCode = prmRcCode;
		this.prmRscCode = prmRscCode;
		this.prmSumInsured = prmSumInsured;
		this.prmRate = prmRate;
		this.prmPremium = prmPremium;
		this.prmCompulsoryExcess = prmCompulsoryExcess;
		this.prmVoluntaryExcess = prmVoluntaryExcess;
		this.prmValidityExpiryDate = prmValidityExpiryDate;
		this.prmEndtId = prmEndtId;
		this.prmExcessRate = prmExcessRate;
		this.prmRiRskCode = prmRiRskCode;
		this.prmSiInd = prmSiInd;
		this.prmStatus = prmStatus;
		this.prmEffectiveDate = prmEffectiveDate;
		this.prmExpiryDate = prmExpiryDate;
		this.prmPolSumInsured = prmPolSumInsured;
		this.prmSitypeCode = prmSitypeCode;
		this.prmFnCode = prmFnCode;
		this.prmSumInsuredCurr = prmSumInsuredCurr;
		this.prmPremiumCurr = prmPremiumCurr;
		this.prmPreparedBy = prmPreparedBy;
		this.prmPreparedDt = prmPreparedDt;
		this.prmModifiedBy = prmModifiedBy;
		this.prmModifiedDt = prmModifiedDt;
		this.prmRiLocCode = prmRiLocCode;
		this.prmRateType = prmRateType;
		this.prmOldPremium = prmOldPremium;
		this.prmOldSumInsured = prmOldSumInsured;
		this.prmPremiumActual = prmPremiumActual;
		this.prmExpPeriodPrm = prmExpPeriodPrm;
		this.prmRenewalLoading = prmRenewalLoading;
		this.prmLoadDisc = prmLoadDisc;
		this.prmValue1 = prmValue1;
	}

	public TTrnPremiumQuoId getId() {
		return this.id;
	}

	public void setId(TTrnPremiumQuoId id) {
		this.id = id;
	}

	public short getPrmClCode() {
		return this.prmClCode;
	}

	public void setPrmClCode(short prmClCode) {
		this.prmClCode = prmClCode;
	}

	public short getPrmPtCode() {
		return this.prmPtCode;
	}

	public void setPrmPtCode(short prmPtCode) {
		this.prmPtCode = prmPtCode;
	}

	public int getPrmRtCode() {
		return this.prmRtCode;
	}

	public void setPrmRtCode(int prmRtCode) {
		this.prmRtCode = prmRtCode;
	}

	public int getPrmRcCode() {
		return this.prmRcCode;
	}

	public void setPrmRcCode(int prmRcCode) {
		this.prmRcCode = prmRcCode;
	}

	public int getPrmRscCode() {
		return this.prmRscCode;
	}

	public void setPrmRscCode(int prmRscCode) {
		this.prmRscCode = prmRscCode;
	}

	public BigDecimal getPrmSumInsured() {
		return this.prmSumInsured;
	}

	public void setPrmSumInsured(BigDecimal prmSumInsured) {
		this.prmSumInsured = prmSumInsured;
	}

	public BigDecimal getPrmRate() {
		return this.prmRate;
	}

	public void setPrmRate(BigDecimal prmRate) {
		this.prmRate = prmRate;
	}

	public BigDecimal getPrmPremium() {
		return this.prmPremium;
	}

	public void setPrmPremium(BigDecimal prmPremium) {
		if( !Utils.isEmpty( prmPremium ) ){
			prmPremium = BigDecimal.valueOf(Double.valueOf(Currency.getUnformattedScaledCurrency(prmPremium)));// prmPremium.setScale( Currency.getScale() , BigDecimal.ROUND_HALF_DOWN );
		}
		
		this.prmPremium = prmPremium;
	}

	public BigDecimal getPrmCompulsoryExcess() {
		return this.prmCompulsoryExcess;
	}

	public void setPrmCompulsoryExcess(BigDecimal prmCompulsoryExcess) {
		this.prmCompulsoryExcess = prmCompulsoryExcess;
	}

	public BigDecimal getPrmVoluntaryExcess() {
		return this.prmVoluntaryExcess;
	}

	public void setPrmVoluntaryExcess(BigDecimal prmVoluntaryExcess) {
		this.prmVoluntaryExcess = prmVoluntaryExcess;
	}

	public Date getPrmValidityExpiryDate() {
		return this.prmValidityExpiryDate;
	}

	public void setPrmValidityExpiryDate(Date prmValidityExpiryDate) {
		this.prmValidityExpiryDate = prmValidityExpiryDate;
	}

	public long getPrmEndtId() {
		return this.prmEndtId;
	}

	public void setPrmEndtId(long prmEndtId) {
		this.prmEndtId = prmEndtId;
	}

	public BigDecimal getPrmExcessRate() {
		return this.prmExcessRate;
	}

	public void setPrmExcessRate(BigDecimal prmExcessRate) {
		this.prmExcessRate = prmExcessRate;
	}

	public Integer getPrmRiRskCode() {
		return this.prmRiRskCode;
	}

	public void setPrmRiRskCode(Integer prmRiRskCode) {
		this.prmRiRskCode = prmRiRskCode;
	}

	public Boolean getPrmSiInd() {
		return this.prmSiInd;
	}

	public void setPrmSiInd(Boolean prmSiInd) {
		this.prmSiInd = prmSiInd;
	}

	public Byte getPrmStatus() {
		return this.prmStatus;
	}

	public void setPrmStatus(Byte prmStatus) {
		this.prmStatus = prmStatus;
	}

	public Date getPrmEffectiveDate() {
		return this.prmEffectiveDate;
	}

	public void setPrmEffectiveDate(Date prmEffectiveDate) {
		this.prmEffectiveDate = prmEffectiveDate;
	}

	public Date getPrmExpiryDate() {
		return this.prmExpiryDate;
	}

	public void setPrmExpiryDate(Date prmExpiryDate) {
		this.prmExpiryDate = prmExpiryDate;
	}

	public BigDecimal getPrmPolSumInsured() {
		return this.prmPolSumInsured;
	}

	public void setPrmPolSumInsured(BigDecimal prmPolSumInsured) {
		this.prmPolSumInsured = prmPolSumInsured;
	}

	public Byte getPrmSitypeCode() {
		return this.prmSitypeCode;
	}

	public void setPrmSitypeCode(Byte prmSitypeCode) {
		this.prmSitypeCode = prmSitypeCode;
	}

	public Byte getPrmFnCode() {
		return this.prmFnCode;
	}

	public void setPrmFnCode(Byte prmFnCode) {
		this.prmFnCode = prmFnCode;
	}

	public Byte getPrmSumInsuredCurr() {
		return this.prmSumInsuredCurr;
	}

	public void setPrmSumInsuredCurr(Byte prmSumInsuredCurr) {
		this.prmSumInsuredCurr = prmSumInsuredCurr;
	}

	public Byte getPrmPremiumCurr() {
		return this.prmPremiumCurr;
	}

	public void setPrmPremiumCurr(Byte prmPremiumCurr) {
		this.prmPremiumCurr = prmPremiumCurr;
	}

	public Integer getPrmPreparedBy() {
		return this.prmPreparedBy;
	}

	public void setPrmPreparedBy(Integer prmPreparedBy) {
		this.prmPreparedBy = prmPreparedBy;
	}

	public Date getPrmPreparedDt() {
		return this.prmPreparedDt;
	}

	public void setPrmPreparedDt(Date prmPreparedDt) {
		this.prmPreparedDt = prmPreparedDt;
	}

	public Integer getPrmModifiedBy() {
		return this.prmModifiedBy;
	}

	public void setPrmModifiedBy(Integer prmModifiedBy) {
		this.prmModifiedBy = prmModifiedBy;
	}

	public Date getPrmModifiedDt() {
		return this.prmModifiedDt;
	}

	public void setPrmModifiedDt(Date prmModifiedDt) {
		this.prmModifiedDt = prmModifiedDt;
	}

	public Integer getPrmRiLocCode() {
		return this.prmRiLocCode;
	}

	public void setPrmRiLocCode(Integer prmRiLocCode) {
		this.prmRiLocCode = prmRiLocCode;
	}

	public Byte getPrmRateType() {
		return this.prmRateType;
	}

	public void setPrmRateType(Byte prmRateType) {
		this.prmRateType = prmRateType;
	}

	public BigDecimal getPrmOldPremium() {
		return this.prmOldPremium;
	}

	public void setPrmOldPremium(BigDecimal prmOldPremium) {
		this.prmOldPremium = prmOldPremium;
	}

	public BigDecimal getPrmOldSumInsured() {
		return this.prmOldSumInsured;
	}

	public void setPrmOldSumInsured(BigDecimal prmOldSumInsured) {
		this.prmOldSumInsured = prmOldSumInsured;
	}

	public BigDecimal getPrmPremiumActual() {
		return this.prmPremiumActual;
	}

	public void setPrmPremiumActual(BigDecimal prmPremiumActual) {
		this.prmPremiumActual = prmPremiumActual;
	}

	public BigDecimal getPrmExpPeriodPrm() {
		return this.prmExpPeriodPrm;
	}

	public void setPrmExpPeriodPrm(BigDecimal prmExpPeriodPrm) {
		this.prmExpPeriodPrm = prmExpPeriodPrm;
	}

	public BigDecimal getPrmRenewalLoading() {
		return this.prmRenewalLoading;
	}

	public void setPrmRenewalLoading(BigDecimal prmRenewalLoading) {
		this.prmRenewalLoading = prmRenewalLoading;
	}

	public BigDecimal getPrmLoadDisc(){
		return prmLoadDisc;
	}

	public void setPrmLoadDisc( BigDecimal prmLoadDisc ){
		this.prmLoadDisc = prmLoadDisc;
	}

	public BigDecimal getPrmValue1(){
		return prmValue1;
	}

	public void setPrmValue1( BigDecimal prmValue1 ){
		this.prmValue1 = prmValue1;
	}
	
	@Override
	public POJOId getPOJOId(){
		return this.getId();
	}

	@Override
	public void setPOJOId( POJOId id ){
		
		setId( (TTrnPremiumQuoId)id );
	}

	@Override
	public int getStatus(){

		return getPrmStatus();
	}

	@Override
	public void setStatus( Integer status ){
		if(!Utils.isEmpty( status ))
		setPrmStatus( Byte.valueOf( status.toString() ) );
	}

	@Override
	public void setEndtId( Long endtId ){
		
		setPrmEndtId( endtId );
	}

	@Override
	public void setValidityExpiryDate( Date ved ){
		
		setPrmValidityExpiryDate( ved );
	}

	@Override
	public String toString(){
		return "TTrnPremiumQuo [id=" + id + ", prmClCode=" + prmClCode + ", prmPtCode=" + prmPtCode + ", prmRtCode=" + prmRtCode + ", prmRcCode=" + prmRcCode + ", prmRscCode="
				+ prmRscCode + ", prmSumInsured=" + prmSumInsured + ", prmRate=" + prmRate + ", prmPremium=" + prmPremium + ", prmCompulsoryExcess=" + prmCompulsoryExcess
				+ ", prmVoluntaryExcess=" + prmVoluntaryExcess + ", prmValidityExpiryDate=" + prmValidityExpiryDate + ", prmEndtId=" + prmEndtId + ", prmExcessRate="
				+ prmExcessRate + ", prmRiRskCode=" + prmRiRskCode + ", prmSiInd=" + prmSiInd + ", prmStatus=" + prmStatus + ", prmEffectiveDate=" + prmEffectiveDate
				+ ", prmExpiryDate=" + prmExpiryDate + ", prmPolSumInsured=" + prmPolSumInsured + ", prmSitypeCode=" + prmSitypeCode + ", prmFnCode=" + prmFnCode
				+ ", prmSumInsuredCurr=" + prmSumInsuredCurr + ", prmPremiumCurr=" + prmPremiumCurr + ", prmPreparedBy=" + prmPreparedBy + ", prmPreparedDt=" + prmPreparedDt
				+ ", prmModifiedBy=" + prmModifiedBy + ", prmModifiedDt=" + prmModifiedDt + ", prmRiLocCode=" + prmRiLocCode + ", prmRateType=" + prmRateType + ", prmOldPremium="
				+ prmOldPremium + ", prmOldSumInsured=" + prmOldSumInsured + ", prmPremiumActual=" + prmPremiumActual + ", prmExpPeriodPrm=" + prmExpPeriodPrm
				+ ", prmRenewalLoading=" + prmRenewalLoading + ", prmLoadDisc=" + prmLoadDisc + ", prmValue1=" + prmValue1 + "]";
	}

	/*Method not applicable.*/
	@Override
	public void setEndtNo(Long endtNo) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}

	/*Method not applicable.*/
	@Override
	public void setValidityStartDate(Date vsd) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD 
	}

	@Override
	public void setPreparedBy(Integer preparedBy) {
		setPrmPreparedBy(preparedBy);
	}

	@Override
	public void setPreparedDt(Date preparedDt) {
		setPrmPreparedDt(preparedDt);
	}

	@Override
	public void setModifiedBy(Integer modifiedBy) {
		setPrmModifiedBy(modifiedBy);
	}

	@Override
	public void setModifiedDt(Date modifiedDt) {
		setPrmModifiedDt( modifiedDt );
	}

	/* Set TTrnPremiumQuoId.*/
	@Override
	public void setPOJOWrapperId( POJOWrapperId id ){
		setId( (TTrnPremiumQuoId)id );
	}

	/* Get TTrnPremiumQuoId.*/
	@Override
	public POJOWrapperId getPOJOWrapperId(){
		return getId();
	}

	@Override
	public void setStatus( int status ){
		setPrmStatus( Integer.valueOf( status ).byteValue() );
	}

	@Override
	public Integer getPreparedBy(){
		return getPrmPreparedBy();
	}

	@Override
	public Date getPreparedDt(){
		return getPrmPreparedDt();
	}

	@Override
	public Integer getModifiedBy(){
		return getPrmModifiedBy();
	}

	@Override
	public Date getModifiedDt(){
		return getPrmModifiedDt();
	}

	
	@Override
	public void setPreparedDate( Date preparedDate ){
		setPrmPreparedDt( preparedDate );
	}

	@Override
	public Date getPreparedDate(){
		return getPrmPreparedDt();
	}
}
