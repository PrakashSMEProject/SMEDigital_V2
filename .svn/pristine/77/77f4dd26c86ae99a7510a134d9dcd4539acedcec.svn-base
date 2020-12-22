
/**
 * Holds the common data related to the policy/quote.
 */
package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.List;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.RuleContext;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1016284
 * @since Phase 3
 *
 */
public class PolicyDataVO extends RiskGroupDetails{

	private static final long serialVersionUID = 1L;
	
	private	java.util.List<StandardClause> standardClauses;
	private	GeneralInfoVO generalInfo;
	private	Long policyNo;
	
	//private Long policyId; 
	private	Long quoteNo;
	private	Long endtId;
	private	Long endtNo;
	private	Boolean isQuote;
	private	Date processedDate;
	private	Integer policyTerm;
	private	Integer policyClassCode;
	private	PremiumVO premiumVO;
	private double govtTaxPerc;
	private	RenewalVO renewals;
	private	Long polLinkingId;
	private	Integer status;
	private	Date created;
	private	java.util.List<NonStandardClause> nonStandardClauses;
	private	Date validityStartDate;
	private	Date startDate;
	private	Date endDate;
	private	Date polExpiryDate;
	private	Date endStartDate;
	private	Date endEffectiveDate;
	private	Long polCustomerId;
	private	PaymentVO paymentVO;
	private	PaymentDetailsVO onlinePaymentDetailsVO;
	private	RuleContext ruleContext;
	private Short sectionId;
	private String polDocumentId;
	private SchemeVO scheme;
	private AuthenticationInfoVO authenticationInfoVO;
	private Flow appFlow;
	private CommonVO commonVO;
	private Integer policyType;
	private BigDecimal polExchangeRate;
	private List<EndorsmentVO> endorsmentVO;
	private boolean isInsuredChanged;
	private ReferralListVO referralVOList;
	private boolean isPolicyExtended;	
	private Long policyExtensionPeriod;	
	/** B2C related field not to be used for B2B OR SBS */
	private String errorMessage;
	private boolean isPopulateOperation;
	
	private boolean isPolicyCancel; // Added as part of policy canellation date restricting to sameday
	private String polRenTermTxt;
	private Integer vatCode; // 142244:Vat Implemenation
	private double vatTaxPerc; // 142244:Vat Implemenation 
	private double vatablePrm;
	
	

	public String getPolRenTermTxt() {
		return polRenTermTxt;
	}


	public void setPolRenTermTxt(String polRenTermTxt) {
		this.polRenTermTxt = polRenTermTxt;
	}


	public boolean isPolicyCancel() {
		return isPolicyCancel;
	}


	public void setPolicyCancel(boolean isPolicyCancel) {
		this.isPolicyCancel = isPolicyCancel;
	}


	public java.util.List<StandardClause> getStandardClauses() {
		return standardClauses;
	}


	public void setStandardClauses(java.util.List<StandardClause> standardClauses) {
		this.standardClauses = standardClauses;
	}


	public java.util.List<NonStandardClause> getNonStandardClauses() {
		return nonStandardClauses;
	}


	public void setNonStandardClauses(
			java.util.List<NonStandardClause> nonStandardClauses) {
		this.nonStandardClauses = nonStandardClauses;
	}


	public Integer getVatCode() {
		return vatCode;
	}


	public void setVatCode(Integer vatCode) {
		this.vatCode = vatCode;
	}


	/**
	 * @return GeneralInfoVO
	 */
	public GeneralInfoVO getGeneralInfo() {
		return generalInfo;
	}


	/**
	 * @param generalInfo
	 */
	public void setGeneralInfo(GeneralInfoVO generalInfo) {
		this.generalInfo = generalInfo;
	}


	/**
	 * @return Long
	 */
	public Long getPolicyNo() {
		return policyNo;
	}


	/**
	 * @param policyNo
	 */
	public void setPolicyNo(Long policyNo) {
		this.policyNo = policyNo;
	}


	/**
	 * @return Long
	 */
	public Long getQuoteNo() {
		return quoteNo;
	}


	/**
	 * @param quoteNo
	 */
	public void setQuoteNo(Long quoteNo) {
		this.quoteNo = quoteNo;
	}


	/**
	 * @return Long
	 */
	public Long getEndtId() {
		return endtId;
	}


	/**
	 * @param endtId
	 */
	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}


	/**
	 * @return Long
	 */
	public Long getEndtNo() {
		return endtNo;
	}

	/**
	 * 
	 * @param endtNo
	 */
	public void setEndtNo(Long endtNo) {
		this.endtNo = endtNo;
	}


	/**
	 * @return Boolean
	 */
	public Boolean getIsQuote() {
		return isQuote;
	}


	/**
	 * @param isQuote
	 */
	public void setIsQuote(Boolean isQuote) {
		this.isQuote = isQuote;
	}


	/**
	 * @return Date
	 */
	public Date getProcessedDate() {
		return processedDate;
	}


	/**
	 * @param processedDate
	 */
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}


	/**
	 * @return Integer
	 */
	public Integer getPolicyTerm() {
		return policyTerm;
	}


	/**
	 * @param policyTerm
	 */
	public void setPolicyTerm(Integer policyTerm) {
		this.policyTerm = policyTerm;
	}


	/**
	 * @return PremiumVO
	 */
	public PremiumVO getPremiumVO() {
		return premiumVO;
	}


	/**
	 * @param premiumVO
	 */
	public void setPremiumVO(PremiumVO premiumVO) {
		this.premiumVO = premiumVO;
	}


	/**
	 * @return RenewalVO
	 */
	public RenewalVO getRenewals() {
		return renewals;
	}


	/**
	 * @param renewals
	 */
	public void setRenewals(RenewalVO renewals) {
		this.renewals = renewals;
	}


	/**
	 * @return Long
	 */
	public Long getPolLinkingId() {
		return polLinkingId;
	}


	/**
	 * @param polLinkingId
	 */
	public void setPolLinkingId(Long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}


	/**
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}


	/**
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}


	/**
	 * @return Date
	 */
	public Date getCreated() {
		return created;
	}


	/**
	 * @param created
	 */
	public void setCreatedOn(Date created) {
		this.created = created;
	}


	/**
	 * @return Date
	 */
	public Date getValidityStartDate() {
		return validityStartDate;
	}


	/**
	 * @param validityStartDate
	 */
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}


	/**
	 * @return Date
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return Date
	 */
	public Date getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return Date
	 */
	public Date getPolExpiryDate() {
		return polExpiryDate;
	}


	/**
	 * @param polExpiryDate
	 */
	public void setPolExpiryDate(Date polExpiryDate) {
		this.polExpiryDate = polExpiryDate;
	}


	/**
	 * @return Date
	 */
	public Date getEndStartDate() {
		return endStartDate;
	}


	/**
	 * @param endStartDate
	 */
	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}


	/**
	 * @return Date
	 */
	public Date getEndEffectiveDate() {
		return endEffectiveDate;
	}


	/**
	 * @param endEffectiveDate
	 */
	public void setEndEffectiveDate(Date endEffectiveDate) {
		this.endEffectiveDate = endEffectiveDate;
	}


	/**
	 * @return Long
	 */
	public Long getPolCustomerId() {
		return polCustomerId;
	}


	/**
	 * @param polCustomerId
	 */
	public void setPolCustomerId(Long polCustomerId) {
		this.polCustomerId = polCustomerId;
	}


	/**
	 * @return
	 */
	public PaymentVO getPaymentVO() {
		return paymentVO;
	}


	/**
	 * @param paymentVO
	 */
	public void setPaymentVO(PaymentVO paymentVO) {
		this.paymentVO = paymentVO;
	}


	/**
	 * @return RuleContext
	 */
	public RuleContext getRuleContext() {
		return ruleContext;
	}


	/**
	 * @param ruleContext
	 */
	public void setRuleContext(RuleContext ruleContext) {
		this.ruleContext = ruleContext;
	}

	public double getGovtTaxPerc() {
		return govtTaxPerc;
	}

	public void setGovtTaxPerc(double govtTaxPerc) {
		this.govtTaxPerc = govtTaxPerc;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 * returns Object
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if( "standardClauses".equals( fieldName ) ) fieldValue = getStandardClauses();
		if( "generalInfo".equals( fieldName ) ) fieldValue = getGeneralInfo();
		if( "policyNo".equals( fieldName ) ) fieldValue = getPolicyNo();
		if( "quoteNo".equals( fieldName ) ) fieldValue = getQuoteNo();
		if( "endtId".equals( fieldName ) ) fieldValue = getEndtId();
		if( "endtNo".equals( fieldName ) ) fieldValue = getEndtNo();
		if( "isQuote".equals( fieldName ) ) fieldValue = getIsQuote();
		if( "processedDate".equals( fieldName ) ) fieldValue = getProcessedDate();
		if( "policyTerm".equals( fieldName ) ) fieldValue = getPolicyTerm();
		if( "policyClassCode".equals( fieldName ) ) fieldValue = getPolicyClassCode();
		if( "premiumVO".equals( fieldName ) ) fieldValue = getPremiumVO();
		if( "renewals".equals( fieldName ) ) fieldValue = getRenewals();
		if( "polLinkingId".equals( fieldName ) ) fieldValue = getPolLinkingId();
		if( "status".equals( fieldName ) ) fieldValue = getStatus();
		if( "created".equals( fieldName ) ) fieldValue = getCreated();
		if( "nonStandardClause".equals( fieldName ) ) fieldValue = getNonStandardClauses();
		if( "validityStartDate".equals( fieldName ) ) fieldValue = getValidityStartDate();
		if( "startDate".equals( fieldName ) ) fieldValue = getStartDate();
		if( "endDate".equals( fieldName ) ) fieldValue = getEndDate();
		if( "polExpiryDate".equals( fieldName ) ) fieldValue = getPolExpiryDate();
		if( "endStartDate".equals( fieldName ) ) fieldValue = getEndStartDate();
		if( "endEffectiveDate".equals( fieldName ) ) fieldValue = getEndEffectiveDate();
		if( "polCustomerId".equals( fieldName ) ) fieldValue = getPolCustomerId();
		if( "paymentVO".equals( fieldName ) ) fieldValue = getPaymentVO();
		if( "policyId".equals( fieldName ) ) fieldValue = getPolicyId();
		if( "sectionId".equals( fieldName ) ) fieldValue = getSectionId();
		if( "scheme".equals( fieldName ) ) fieldValue = getScheme();
		if( "polDocumentId".equals( fieldName ) ) fieldValue = getPolDocumentId();
		if( "authenticationInfoVO".equals(fieldName)) fieldValue = getAuthenticationInfoVO();
		if( "policyExtensionPeriod".equals(fieldName)) fieldValue = getPolicyExtensionPeriod();
		if( "govtTaxPerc".equals(fieldName)) fieldValue = getGovtTaxPerc();
		if( "polRenTermTxt".equals(fieldName)) fieldValue = getPolRenTermTxt();
		if( "vatCode".equals(fieldName)) fieldValue = getVatCode();
		if( "vatTaxPerc".equals(fieldName)) fieldValue = getVatTaxPerc();
		
		return fieldValue;
	}


	/**
	 * @param policyClassCode
	 */
	public void setPolicyClassCode(Integer policyClassCode) {
		this.policyClassCode = policyClassCode;
	}


	/**
	 * @return Integer
	 */
	public Integer getPolicyClassCode() {
		return policyClassCode;
	}


	/**
	 * @param policyId
	 */
	/*public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}


	*//**
	 * @return String
	 *//*
	public Long getPolicyId() {
		return policyId;
	}*/


	/**
	 * @param sectionId
	 */
	public void setSectionId(Short sectionId) {
		this.sectionId = sectionId;
	}


	/**
	 * @return String
	 */
	public Short getSectionId() {
		return sectionId;
	}


	/**
	 * @param polDocumentId
	 */
	public void setPolDocumentId(String polDocumentId) {
		this.polDocumentId = polDocumentId;
	}


	/**
	 * @return String
	 */
	public String getPolDocumentId() {
		return polDocumentId;
	}


	/**
	 * @param scheme
	 */
	public void setScheme(SchemeVO scheme) {
		this.scheme = scheme;
	}


	/**
	 * @return SchemeVO
	 */
	public SchemeVO getScheme() {
		return scheme;
	}


	/**
	 * @return the authenticationInfoVO
	 */
	public AuthenticationInfoVO getAuthenticationInfoVO() {
		return authenticationInfoVO;
	}


	/**
	 * @param authenticationInfoVO the authenticationInfoVO to set
	 */
	public void setAuthenticationInfoVO(AuthenticationInfoVO authenticationInfoVO) {
		this.authenticationInfoVO = authenticationInfoVO;
	}


	/**
	 * @return the appFlow
	 */
	public Flow getAppFlow(){
		return appFlow;
	}


	/**
	 * @param appFlow the appFlow to set
	 */
	public void setAppFlow( Flow appFlow ){
		this.appFlow = appFlow;
	}


	public void setCommonVO( CommonVO commonVO ){
		this.commonVO = commonVO;
	}


	public CommonVO getCommonVO(){
		return commonVO;
	}


	/**
	 * @param policyType
	 */
	public void setPolicyType( Integer policyType ){
		this.policyType = policyType;
	}


	/**
	 * @return Integer
	 */
	public Integer getPolicyType(){
		return policyType;
	}


	/**
	 * @return
	 */
	public BigDecimal getPolExchangeRate(){
		return polExchangeRate;
	}


	/**
	 * @param polExchangeRate
	 */
	public void setPolExchangeRate( BigDecimal polExchangeRate ){
		this.polExchangeRate = polExchangeRate;
	}


	/**
	 * @param isInsuredChanged the isInsuredChanged to set
	 */
	public void setInsuredChanged( boolean isInsuredChanged ){
		this.isInsuredChanged = isInsuredChanged;
	}


	/**
	 * @return the isInsuredChanged
	 */
	public boolean isInsuredChanged(){
		return isInsuredChanged;
	}
	
	
	public List<EndorsmentVO> getEndorsmentVO(){
		return endorsmentVO;
	}


	public void setEndorsmentVO( List<EndorsmentVO> endorsmentVO ){
		this.endorsmentVO = endorsmentVO;
	}


	public void setReferralVOList(ReferralListVO referralVOList) {
		this.referralVOList = referralVOList;
	}


	public ReferralListVO getReferralVOList() {
		return referralVOList;
	}


	/**
	 * @return the isPolicyExtended
	 */
	public boolean isPolicyExtended() {
		return isPolicyExtended;
	}


	/**
	 * @param isPolicyExtended the isPolicyExtended to set
	 */
	public void setPolicyExtended(boolean isPolicyExtended) {
		this.isPolicyExtended = isPolicyExtended;
	}


	/**
	 * @return no. of days by which policy is extended.
	 */
	public Long getPolicyExtensionPeriod(){
		return policyExtensionPeriod;
	}


	/**
	 * @param policyExtensionPeriod
	 */
	public void setPolicyExtensionPeriod( Long policyExtensionPeriod ){
		this.policyExtensionPeriod = policyExtensionPeriod;
	}

	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * @return the isPopulateOperation
	 */
	public boolean isPopulateOperation(){
		return isPopulateOperation;
	}


	/**
	 * @param isPopulateOperation the isPopulateOperation to set
	 */
	public void setPopulateOperation( boolean isPopulateOperation ){
		this.isPopulateOperation = isPopulateOperation;
	}

	public PaymentDetailsVO getOnlinePaymentDetailsVO(){
		return onlinePaymentDetailsVO;
	}

	public void setOnlinePaymentDetailsVO( PaymentDetailsVO onlinePaymentDetailsVO ){
		this.onlinePaymentDetailsVO = onlinePaymentDetailsVO;
	}
	
	public double getVatTaxPerc() {
		return vatTaxPerc;
	}


	public void setVatTaxPerc(double vatTaxPerc) {
		this.vatTaxPerc = vatTaxPerc;
	}


	public double getVatablePrm() {
		return vatablePrm;
	}


	public void setVatablePrm(double vatablePrm) {
		this.vatablePrm = vatablePrm;
	}
	
	
	
}
