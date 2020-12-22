package com.rsaame.pas.b2c.ws.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TravelInsuranceDetails {

	private	GeneralInsuranceDetails generalInfo;
	private	Long policyNo;
	private	Long quoteNo;
	private	Long endtId;
	private	Long endtNo;
	private	Boolean isQuote;
	private	Date processedDate;
	private	Integer policyTerm;
	
	private	PremiumDetails premiumVO;
	/*private	RenewalVO renewals;*/
	//private	Long polLinkingId;
	//private	Integer status;
	private	Date created;
	/*private	java.util.List<NonStandardClause> nonStandardClauses;*/
	private	Date validityStartDate;
	private	Date startDate;
	private	Date endDate;
	private	Date polExpiryDate;
	private	Date endStartDate;
	private	Date endEffectiveDate;
	private	Long polCustomerId;
	private	PaymentDetails paymentVO;
	/*private	RuleContext ruleContext;*/
	private Short sectionId;
	private String polDocumentId;
	private SchemeDetails scheme;
	/*private AuthenticationInfoVO authenticationInfoVO;*/
	/*private Flow appFlow;*/
	/*private CommonVO commonVO;*/
	private Integer policyType;
	private BigDecimal polExchangeRate;
	/*private List<EndorsmentVO> endorsmentVO;*/
	//private boolean isInsuredChanged;
	/*private ReferralListVO referralVOList;*/
	
	private Long policyExtensionPeriod;	
	
	private Date vsd;
	
	private Date polEffectiveDate;
	private Date endtEffectiveDate;
	
	private List<TravelPackageDetails> travelPackageList;
	private TravelDetails travelDetailsVO;
	private Integer defaultTariff;
	private Long policyId;
	
	public GeneralInsuranceDetails getGeneralInfo() {
		return generalInfo;
	}
	public void setGeneralInfo(GeneralInsuranceDetails generalInfo) {
		this.generalInfo = generalInfo;
	}
	public Long getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(Long policyNo) {
		this.policyNo = policyNo;
	}
	public Long getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(Long quoteNo) {
		this.quoteNo = quoteNo;
	}
	public Long getEndtId() {
		return endtId;
	}
	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}
	public Long getEndtNo() {
		return endtNo;
	}
	public void setEndtNo(Long endtNo) {
		this.endtNo = endtNo;
	}
	public Boolean getIsQuote() {
		return isQuote;
	}
	public void setIsQuote(Boolean isQuote) {
		this.isQuote = isQuote;
	}
	public Date getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	public Integer getPolicyTerm() {
		return policyTerm;
	}
	public void setPolicyTerm(Integer policyTerm) {
		this.policyTerm = policyTerm;
	}
	
	public PremiumDetails getPremiumVO() {
		return premiumVO;
	}
	public void setPremiumVO(PremiumDetails premiumVO) {
		this.premiumVO = premiumVO;
	}
	/*public Long getPolLinkingId() {
		return polLinkingId;
	}
	public void setPolLinkingId(Long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}*/
	/*public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}*/
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getValidityStartDate() {
		return validityStartDate;
	}
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
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
	public Date getPolExpiryDate() {
		return polExpiryDate;
	}
	public void setPolExpiryDate(Date polExpiryDate) {
		this.polExpiryDate = polExpiryDate;
	}
	public Date getEndStartDate() {
		return endStartDate;
	}
	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}
	public Date getEndEffectiveDate() {
		return endEffectiveDate;
	}
	public void setEndEffectiveDate(Date endEffectiveDate) {
		this.endEffectiveDate = endEffectiveDate;
	}
	public Long getPolCustomerId() {
		return polCustomerId;
	}
	public void setPolCustomerId(Long polCustomerId) {
		this.polCustomerId = polCustomerId;
	}
	public PaymentDetails getPaymentVO() {
		return paymentVO;
	}
	public void setPaymentVO(PaymentDetails paymentVO) {
		this.paymentVO = paymentVO;
	}
	public Short getSectionId() {
		return sectionId;
	}
	public void setSectionId(Short sectionId) {
		this.sectionId = sectionId;
	}
	public String getPolDocumentId() {
		return polDocumentId;
	}
	public void setPolDocumentId(String polDocumentId) {
		this.polDocumentId = polDocumentId;
	}
	public SchemeDetails getScheme() {
		return scheme;
	}
	public void setScheme(SchemeDetails scheme) {
		this.scheme = scheme;
	}
	public Integer getPolicyType() {
		return policyType;
	}
	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}
	public BigDecimal getPolExchangeRate() {
		return polExchangeRate;
	}
	public void setPolExchangeRate(BigDecimal polExchangeRate) {
		this.polExchangeRate = polExchangeRate;
	}
	/*public boolean isInsuredChanged() {
		return isInsuredChanged;
	}
	public void setInsuredChanged(boolean isInsuredChanged) {
		this.isInsuredChanged = isInsuredChanged;
	}*/
	
	public Long getPolicyExtensionPeriod() {
		return policyExtensionPeriod;
	}
	public void setPolicyExtensionPeriod(Long policyExtensionPeriod) {
		this.policyExtensionPeriod = policyExtensionPeriod;
	}
	public Date getVsd() {
		return vsd;
	}
	public void setVsd(Date vsd) {
		this.vsd = vsd;
	}
	
	public Date getPolEffectiveDate() {
		return polEffectiveDate;
	}
	public void setPolEffectiveDate(Date polEffectiveDate) {
		this.polEffectiveDate = polEffectiveDate;
	}
	public Date getEndtEffectiveDate() {
		return endtEffectiveDate;
	}
	public void setEndtEffectiveDate(Date endtEffectiveDate) {
		this.endtEffectiveDate = endtEffectiveDate;
	}
	public List<TravelPackageDetails> getTravelPackageList() {
		return travelPackageList;
	}
	public void setTravelPackageList(List<TravelPackageDetails> travelPackageList) {
		this.travelPackageList = travelPackageList;
	}
	public TravelDetails getTravelDetailsVO() {
		return travelDetailsVO;
	}
	public void setTravelDetailsVO(TravelDetails travelDetailsVO) {
		this.travelDetailsVO = travelDetailsVO;
	}
	public Integer getDefaultTariff() {
		return defaultTariff;
	}
	public void setDefaultTariff(Integer defaultTariff) {
		this.defaultTariff = defaultTariff;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	
}
