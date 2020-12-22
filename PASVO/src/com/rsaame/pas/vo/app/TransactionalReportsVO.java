package com.rsaame.pas.vo.app;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1016996(Guruaraj Hallikeri)
 * This VO is used for holding the search results of "Transactional Report Search"
 */
public class TransactionalReportsVO extends BaseVO {

	/**
	 * Generated serial versionId 
	 */
	
	private static final long serialVersionUID = 4243597892854586458L;
	private String insuranceType;
	private Integer totalQuoteCount;
	private BigDecimal totalQuotePrem;
	private Integer abondonedQuoteCount;
	private BigDecimal abondonedQuotePrem;
	private Integer savedQuoteCount;
	private BigDecimal savedQuotePrem;
	private Integer paymentReceivedCount;
	private BigDecimal paymentReceivedPrem;
	private Integer referalPendingCount;
	private BigDecimal referalPendingPrem;
	private Integer acceptedReferalCount;
	private BigDecimal acceptedReferalPrem;
	private Integer convertedReferalCount;
	private BigDecimal convertedReferalPrem;
	private Integer rejectedReferalCount;
	private BigDecimal rejectedReferalPrem;
	private Integer convertedToPolicyCount;
	private BigDecimal convertedToPolicyPrem;
	private Double quotationStrikeRate;
	private Double referalPercentage;
	private Double referalConvRate;
	private Date startDate;
	private Date endDate;
	//added for 3.2 release
	private BigDecimal assistedQuotationPrem;
	private Integer assistedQuotationCount;
	
	public TransactionalReportsVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public Integer getTotalQuoteCount() {
		return totalQuoteCount;
	}

	public void setTotalQuoteCount(Integer totalQuoteCount) {
		this.totalQuoteCount = totalQuoteCount;
	}

	public BigDecimal getTotalQuotePrem() {
		return totalQuotePrem;
	}

	public void setTotalQuotePrem(BigDecimal totalQuotePrem) {
		this.totalQuotePrem = totalQuotePrem;
	}

	public Integer getSavedQuoteCount() {
		return savedQuoteCount;
	}

	public void setSavedQuoteCount(Integer savedQuoteCount) {
		this.savedQuoteCount = savedQuoteCount;
	}

	public BigDecimal getSavedQuotePrem() {
		return savedQuotePrem;
	}

	public void setSavedQuotePrem(BigDecimal savedQuotePrem) {
		this.savedQuotePrem = savedQuotePrem;
	}

	public Integer getPaymentReceivedCount() {
		return paymentReceivedCount;
	}

	public void setPaymentReceivedCount(Integer paymentReceivedCount) {
		this.paymentReceivedCount = paymentReceivedCount;
	}

	public BigDecimal getPaymentReceivedPrem() {
		return paymentReceivedPrem;
	}

	public void setPaymentReceivedPrem(BigDecimal paymentReceivedPrem) {
		this.paymentReceivedPrem = paymentReceivedPrem;
	}

	public Integer getReferalPendingCount() {
		return referalPendingCount;
	}

	public void setReferalPendingCount(Integer referalPendingCount) {
		this.referalPendingCount = referalPendingCount;
	}

	public BigDecimal getReferalPendingPrem() {
		return referalPendingPrem;
	}

	public void setReferalPendingPrem(BigDecimal referalPendingPrem) {
		this.referalPendingPrem = referalPendingPrem;
	}

	public Integer getAcceptedReferalCount() {
		return acceptedReferalCount;
	}

	public void setAcceptedReferalCount(Integer acceptedReferalCount) {
		this.acceptedReferalCount = acceptedReferalCount;
	}

	public BigDecimal getAcceptedReferalPrem() {
		return acceptedReferalPrem;
	}

	public void setAcceptedReferalPrem(BigDecimal acceptedReferalPrem) {
		this.acceptedReferalPrem = acceptedReferalPrem;
	}

	public Integer getConvertedReferalCount() {
		return convertedReferalCount;
	}

	public void setConvertedReferalCount(Integer convertedReferalCount) {
		this.convertedReferalCount = convertedReferalCount;
	}

	public BigDecimal getConvertedReferalPrem() {
		return convertedReferalPrem;
	}

	public void setConvertedReferalPrem(BigDecimal convertedReferalPrem) {
		this.convertedReferalPrem = convertedReferalPrem;
	}

	public Integer getRejectedReferalCount() {
		return rejectedReferalCount;
	}

	public void setRejectedReferalCount(Integer rejectedReferalCount) {
		this.rejectedReferalCount = rejectedReferalCount;
	}

	public BigDecimal getRejectedReferalPrem() {
		return rejectedReferalPrem;
	}

	public void setRejectedReferalPrem(BigDecimal rejectedReferalPrem) {
		this.rejectedReferalPrem = rejectedReferalPrem;
	}

	public Integer getConvertedToPolicyCount() {
		return convertedToPolicyCount;
	}

	public void setConvertedToPolicyCount(Integer convertedToPolicyCount) {
		this.convertedToPolicyCount = convertedToPolicyCount;
	}

	public BigDecimal getConvertedToPolicyPrem() {
		return convertedToPolicyPrem;
	}

	public void setConvertedToPolicyPrem(BigDecimal convertedToPolicyPrem) {
		this.convertedToPolicyPrem = convertedToPolicyPrem;
	}

	public Double getQuotationStrikeRate() {
		return quotationStrikeRate;
	}

	public void setQuotationStrikeRate(Double quotationStrikeRate) {
		this.quotationStrikeRate = quotationStrikeRate;
	}

	public Double getReferalPercentage() {
		return referalPercentage;
	}

	public void setReferalPercentage(Double referalPercentage) {
		this.referalPercentage = referalPercentage;
	}

	public Double getReferalConvRate() {
		return referalConvRate;
	}

	public void setReferalConvRate(Double referalConvRate) {
		this.referalConvRate = referalConvRate;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	

	@Override
	public String toString() {
		return "TransactionalReportsVO [insuranceType=" + insuranceType
				+ ", totalQuoteCount=" + totalQuoteCount + ", totalQuotePrem="
				+ totalQuotePrem + ", abondonedQuoteCount="
				+ abondonedQuoteCount + ", abondonedQuotePrem="
				+ abondonedQuotePrem + ", savedQuoteCount=" + savedQuoteCount
				+ ", savedQuotePrem=" + savedQuotePrem
				+ ", paymentReceivedCount=" + paymentReceivedCount
				+ ", paymentReceivedPrem=" + paymentReceivedPrem
				+ ", referalPendingCount=" + referalPendingCount
				+ ", referalPendingPrem=" + referalPendingPrem
				+ ", acceptedReferalCount=" + acceptedReferalCount
				+ ", acceptedReferalPrem=" + acceptedReferalPrem
				+ ", convertedReferalCount=" + convertedReferalCount
				+ ", convertedReferalPrem=" + convertedReferalPrem
				+ ", rejectedReferalCount=" + rejectedReferalCount
				+ ", rejectedReferalPrem=" + rejectedReferalPrem
				+ ", convertedToPolicyCount=" + convertedToPolicyCount
				+ ", convertedToPolicyPrem=" + convertedToPolicyPrem
				+ ", quotationStrikeRate=" + quotationStrikeRate
				+ ", referalPercentage=" + referalPercentage
				+ ", referalConvRate=" + referalConvRate + ", startDate="
				+ startDate + ", endDate=" + endDate  
				+ ", assistedQuotationCount=" + assistedQuotationCount
				+ ", assistedQuotationPrem=" + assistedQuotationPrem +"]";
	}

	public Integer getAbondonedQuoteCount() {
		return abondonedQuoteCount;
	}

	public void setAbondonedQuoteCount(Integer abondonedQuoteCount) {
		this.abondonedQuoteCount = abondonedQuoteCount;
	}

	public BigDecimal getAbondonedQuotePrem() {
		return abondonedQuotePrem;
	}

	public void setAbondonedQuotePrem(BigDecimal abondonedQuotePrem) {
		this.abondonedQuotePrem = abondonedQuotePrem;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;

		if( "insuranceType".equals( fieldName ) ) fieldValue = getInsuranceType();
		if( "totalQuoteCount".equals( fieldName ) ) fieldValue = getTotalQuoteCount();
		if( "totalQuotePrem".equals( fieldName ) ) fieldValue = getTotalQuotePrem();
		if( "savedQuoteCount".equals( fieldName ) ) fieldValue = getSavedQuoteCount();
		if( "savedQuotePrem".equals( fieldName ) ) fieldValue = getSavedQuotePrem();
		if( "paymentReceivedCount".equals( fieldName ) ) fieldValue = getPaymentReceivedCount();
		if( "paymentReceivedPrem".equals( fieldName ) ) fieldValue = getPaymentReceivedPrem();
		if( "referalPendingCount".equals( fieldName ) ) fieldValue = getReferalPendingCount();
		if( "referalPendingPrem".equals( fieldName ) ) fieldValue = getReferalPendingPrem();
		if( "acceptedReferalCount".equals( fieldName ) ) fieldValue = getAcceptedReferalCount();
		if( "acceptedReferalPrem".equals( fieldName ) ) fieldValue = getAcceptedReferalPrem();
		if( "convertedReferalCount".equals( fieldName ) ) fieldValue = getConvertedReferalCount();
		if( "convertedReferalPrem".equals( fieldName ) ) fieldValue = getReferalPendingPrem();
		if( "rejectedReferalCount".equals( fieldName ) ) fieldValue = getRejectedReferalCount();
		if( "rejectedReferalPrem".equals( fieldName ) ) fieldValue = getRejectedReferalPrem();
		if( "convertedToPolicyCount".equals( fieldName ) ) fieldValue = getConvertedToPolicyCount();
		if( "convertedToPolicyPrem".equals( fieldName ) ) fieldValue = getConvertedToPolicyPrem();
		if( "quotationStrikeRate".equals( fieldName ) ) fieldValue = getQuotationStrikeRate();
		if( "referalPercentage".equals( fieldName ) ) fieldValue = getReferalPercentage();
		if( "referalConvRate".equals( fieldName ) ) fieldValue = getReferalConvRate();
		if( "startDate".equals( fieldName ) ) fieldValue = getStartDate();
		if( "endDate".equals( fieldName ) ) fieldValue = getEndDate();
		//Added for 3.2 release
		if("assistedQuotationPrem".equals(fieldName)) fieldValue = getAssistedQuotationPrem();
		if("assistedQuotationCount".equals(fieldName)) fieldValue = getAssistedQuotationCount();
			
		return fieldValue;
	}
	//added for 3.2 Release
		public BigDecimal getAssistedQuotationPrem() {
			return assistedQuotationPrem;
		}

		public void setAssistedQuotationPrem(BigDecimal assistedQuotationPrem) {
			this.assistedQuotationPrem = assistedQuotationPrem;
		}

		public Integer getAssistedQuotationCount() {
			return assistedQuotationCount;
		}

		public void setAssistedQuotationCount(Integer assistedQuotationCount) {
			this.assistedQuotationCount = assistedQuotationCount;
		}

}
