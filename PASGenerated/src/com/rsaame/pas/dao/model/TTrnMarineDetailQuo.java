package com.rsaame.pas.dao.model;

// Generated Jul 10, 2012 6:17:09 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * TTrnMarineDetailQuo generated by hbm2java
 */
public class TTrnMarineDetailQuo extends POJO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private TTrnMarineDetailQuoId id;
    private Integer mdCommodityGroupCode;
    private Integer mdCommodityCode;
    private String mdACommodityDesc;
    private String mdECommodityDesc;
    private BigDecimal mdSumInsured;
    private BigDecimal mdFcSumInsured;
    private BigDecimal mdSumInsuredLoading;
    private BigDecimal mdAddlLoading;
    private Integer mdPackingType;
    private String mdAPackingDesc;
    private String mdEPackingDesc;
    private String mdPackingNo;
    private Integer mdNoOfPackages;
    private String mdAContainerNo;
    private String mdEContainerNo;
    private Long mdMiInvoiceId;
    private Short mdGeoCode;
    private Integer mdBasicRiskCode;
    private Integer mdRiskCode;
    private Integer mdRiRskCode;
    private Date mdValidityExpiryDate;
    private Byte mdStatus;
    private Byte mdValuationBasis;
    private Integer mdPreparedBy;
    private Date mdPreparedDt;
    private Integer mdModifiedBy;
    private Date mdModifiedDt;
    private Date mdStartDate;
    private Date mdEndDate;
    private String mdCargoType;
    private Long mdEstAnnualSi;
    private Integer mdSiIndicator;

    public TTrnMarineDetailQuo() {
    }

    public Integer getMdSiIndicator() {
        return mdSiIndicator;
    }

    public void setMdSiIndicator(Integer mdSiIndicator) {
        this.mdSiIndicator = mdSiIndicator;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public TTrnMarineDetailQuo(TTrnMarineDetailQuoId id, long mdCommodityId,
	    Date mdValidityStartDate, Date mdValidityExpiryDate) {
	this.id = id;
	this.mdValidityExpiryDate = mdValidityExpiryDate;
    }

    public TTrnMarineDetailQuo(TTrnMarineDetailQuoId id, long mdCommodityId,
	    Integer mdCommodityGroupCode, Integer mdCommodityCode,
	    String mdACommodityDesc, String mdECommodityDesc,
	    BigDecimal mdSumInsured, BigDecimal mdFcSumInsured,
	    BigDecimal mdSumInsuredLoading, BigDecimal mdAddlLoading,
	    Integer mdPackingType, String mdAPackingDesc,
	    String mdEPackingDesc, String mdPackingNo, Integer mdNoOfPackages,
	    String mdAContainerNo, String mdEContainerNo, Long mdMiInvoiceId,
	    Short mdGeoCode, Integer mdBasicRiskCode, Integer mdRiskCode,
	    Integer mdRiRskCode, Date mdValidityStartDate,
	    Date mdValidityExpiryDate, Byte mdStatus, Byte mdValuationBasis,
	    Integer mdPreparedBy, Date mdPreparedDt, Integer mdModifiedBy,
	    Date mdModifiedDt, Date mdStartDate, Date mdEndDate,
	    String mdCargoType, Long mdEstAnnualSi, Integer mdSiIndicator) {
	this.id = id;
	this.mdCommodityGroupCode = mdCommodityGroupCode;
	this.mdCommodityCode = mdCommodityCode;
	this.mdACommodityDesc = mdACommodityDesc;
	this.mdECommodityDesc = mdECommodityDesc;
	this.mdSumInsured = mdSumInsured;
	this.mdFcSumInsured = mdFcSumInsured;
	this.mdSumInsuredLoading = mdSumInsuredLoading;
	this.mdAddlLoading = mdAddlLoading;
	this.mdPackingType = mdPackingType;
	this.mdAPackingDesc = mdAPackingDesc;
	this.mdEPackingDesc = mdEPackingDesc;
	this.mdPackingNo = mdPackingNo;
	this.mdNoOfPackages = mdNoOfPackages;
	this.mdAContainerNo = mdAContainerNo;
	this.mdEContainerNo = mdEContainerNo;
	this.mdMiInvoiceId = mdMiInvoiceId;
	this.mdGeoCode = mdGeoCode;
	this.mdBasicRiskCode = mdBasicRiskCode;
	this.mdRiskCode = mdRiskCode;
	this.mdRiRskCode = mdRiRskCode;
	this.mdValidityExpiryDate = mdValidityExpiryDate;
	this.mdStatus = mdStatus;
	this.mdValuationBasis = mdValuationBasis;
	this.mdPreparedBy = mdPreparedBy;
	this.mdPreparedDt = mdPreparedDt;
	this.mdModifiedBy = mdModifiedBy;
	this.mdModifiedDt = mdModifiedDt;
	this.mdStartDate = mdStartDate;
	this.mdEndDate = mdEndDate;
	this.mdCargoType = mdCargoType;
	this.mdEstAnnualSi = mdEstAnnualSi;
	this.mdSiIndicator = mdSiIndicator;
    }

    public TTrnMarineDetailQuoId getId() {
	return this.id;
    }

    public void setId(TTrnMarineDetailQuoId id) {
	this.id = id;
    }

    public Integer getMdCommodityGroupCode() {
	return this.mdCommodityGroupCode;
    }

    public void setMdCommodityGroupCode(Integer mdCommodityGroupCode) {
	this.mdCommodityGroupCode = mdCommodityGroupCode;
    }

    public Integer getMdCommodityCode() {
	return this.mdCommodityCode;
    }

    public void setMdCommodityCode(Integer mdCommodityCode) {
	this.mdCommodityCode = mdCommodityCode;
    }

    public String getMdACommodityDesc() {
	return this.mdACommodityDesc;
    }

    public void setMdACommodityDesc(String mdACommodityDesc) {
	this.mdACommodityDesc = mdACommodityDesc;
    }

    public String getMdECommodityDesc() {
	return this.mdECommodityDesc;
    }

    public void setMdECommodityDesc(String mdECommodityDesc) {
	this.mdECommodityDesc = mdECommodityDesc;
    }

    public BigDecimal getMdSumInsured() {
	return this.mdSumInsured;
    }

    public void setMdSumInsured(BigDecimal mdSumInsured) {
	this.mdSumInsured = mdSumInsured;
    }

    public BigDecimal getMdFcSumInsured() {
	return this.mdFcSumInsured;
    }

    public void setMdFcSumInsured(BigDecimal mdFcSumInsured) {
	this.mdFcSumInsured = mdFcSumInsured;
    }

    public BigDecimal getMdSumInsuredLoading() {
	return this.mdSumInsuredLoading;
    }

    public void setMdSumInsuredLoading(BigDecimal mdSumInsuredLoading) {
	this.mdSumInsuredLoading = mdSumInsuredLoading;
    }

    public BigDecimal getMdAddlLoading() {
	return this.mdAddlLoading;
    }

    public void setMdAddlLoading(BigDecimal mdAddlLoading) {
	this.mdAddlLoading = mdAddlLoading;
    }

    public Integer getMdPackingType() {
	return this.mdPackingType;
    }

    public void setMdPackingType(Integer mdPackingType) {
	this.mdPackingType = mdPackingType;
    }

    public String getMdAPackingDesc() {
	return this.mdAPackingDesc;
    }

    public void setMdAPackingDesc(String mdAPackingDesc) {
	this.mdAPackingDesc = mdAPackingDesc;
    }

    public String getMdEPackingDesc() {
	return this.mdEPackingDesc;
    }

    public void setMdEPackingDesc(String mdEPackingDesc) {
	this.mdEPackingDesc = mdEPackingDesc;
    }

    public String getMdPackingNo() {
	return this.mdPackingNo;
    }

    public void setMdPackingNo(String mdPackingNo) {
	this.mdPackingNo = mdPackingNo;
    }

    public Integer getMdNoOfPackages() {
	return this.mdNoOfPackages;
    }

    public void setMdNoOfPackages(Integer mdNoOfPackages) {
	this.mdNoOfPackages = mdNoOfPackages;
    }

    public String getMdAContainerNo() {
	return this.mdAContainerNo;
    }

    public void setMdAContainerNo(String mdAContainerNo) {
	this.mdAContainerNo = mdAContainerNo;
    }

    public String getMdEContainerNo() {
	return this.mdEContainerNo;
    }

    public void setMdEContainerNo(String mdEContainerNo) {
	this.mdEContainerNo = mdEContainerNo;
    }

    public Long getMdMiInvoiceId() {
	return this.mdMiInvoiceId;
    }

    public void setMdMiInvoiceId(Long mdMiInvoiceId) {
	this.mdMiInvoiceId = mdMiInvoiceId;
    }

    public Short getMdGeoCode() {
	return this.mdGeoCode;
    }

    public void setMdGeoCode(Short mdGeoCode) {
	this.mdGeoCode = mdGeoCode;
    }

    public Integer getMdBasicRiskCode() {
	return this.mdBasicRiskCode;
    }

    public void setMdBasicRiskCode(Integer mdBasicRiskCode) {
	this.mdBasicRiskCode = mdBasicRiskCode;
    }

    public Integer getMdRiskCode() {
	return this.mdRiskCode;
    }

    public void setMdRiskCode(Integer mdRiskCode) {
	this.mdRiskCode = mdRiskCode;
    }

    public Integer getMdRiRskCode() {
	return this.mdRiRskCode;
    }

    public void setMdRiRskCode(Integer mdRiRskCode) {
	this.mdRiRskCode = mdRiRskCode;
    }

    public Date getMdValidityExpiryDate() {
	return this.mdValidityExpiryDate;
    }

    public void setMdValidityExpiryDate(Date mdValidityExpiryDate) {
	this.mdValidityExpiryDate = mdValidityExpiryDate;
    }

    public Byte getMdStatus() {
	return this.mdStatus;
    }

    public void setMdStatus(Byte mdStatus) {
	this.mdStatus = mdStatus;
    }

    public Byte getMdValuationBasis() {
	return this.mdValuationBasis;
    }

    public void setMdValuationBasis(Byte mdValuationBasis) {
	this.mdValuationBasis = mdValuationBasis;
    }

    public Integer getMdPreparedBy() {
	return this.mdPreparedBy;
    }

    public void setMdPreparedBy(Integer mdPreparedBy) {
	this.mdPreparedBy = mdPreparedBy;
    }

    public Date getMdPreparedDt() {
	return this.mdPreparedDt;
    }

    public void setMdPreparedDt(Date mdPreparedDt) {
	this.mdPreparedDt = mdPreparedDt;
    }

    public Integer getMdModifiedBy() {
	return this.mdModifiedBy;
    }

    public void setMdModifiedBy(Integer mdModifiedBy) {
	this.mdModifiedBy = mdModifiedBy;
    }

    public Date getMdModifiedDt() {
	return this.mdModifiedDt;
    }

    public void setMdModifiedDt(Date mdModifiedDt) {
	this.mdModifiedDt = mdModifiedDt;
    }

    public Date getMdStartDate() {
	return this.mdStartDate;
    }

    public void setMdStartDate(Date mdStartDate) {
	this.mdStartDate = mdStartDate;
    }

    public Date getMdEndDate() {
	return this.mdEndDate;
    }

    public void setMdEndDate(Date mdEndDate) {
	this.mdEndDate = mdEndDate;
    }

    public String getMdCargoType() {
	return this.mdCargoType;
    }

    public void setMdCargoType(String mdCargoType) {
	this.mdCargoType = mdCargoType;
    }

    public Long getMdEstAnnualSi() {
	return this.mdEstAnnualSi;
    }

    public void setMdEstAnnualSi(Long mdEstAnnualSi) {
	this.mdEstAnnualSi = mdEstAnnualSi;
    }

    @Override
	public POJOId getPOJOId(){
		return this.getId();
	}

    @Override
    public void setPOJOId(POJOId id) {
	setId((TTrnMarineDetailQuoId) id);
    }

    @Override
    public int getStatus() {

	return getMdStatus();
    }

    @Override
    public void setStatus(Integer status) {
	setMdStatus(status.byteValue());
    }

    @Override
    public void setEndtId(Long endtId) {
	if (!Utils.isEmpty(getId()))
	    getId().setMdEndtId(endtId);
    }

    @Override
    public void setEndtNo(Long endtNo) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
    }

    @Override
    public void setValidityStartDate(Date vsd) {
	getId().setMdValidityStartDate(vsd);
    }

    @Override
    public void setValidityExpiryDate(Date ved) {
	setMdValidityExpiryDate(ved);
    }

    @Override
	public void setPreparedDate( Date preparedDate ){
		setMdPreparedDt( preparedDate );
	}
    
    @Override
	public Date getPreparedDate(){
		return getMdPreparedDt();
	}
}