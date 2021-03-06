//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : PolicyVO.java
//  @ Date : 12/20/2011
//  @ Author : i
//
//



package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.RuleContext;


public class PolicyVO extends BaseVO {
	private static final long serialVersionUID = 1L;
	private GeneralInfoVO generalInfo;
	private Long policyNo;
	private String concatPolicyNo;
	private Long quoteNo;
	private Long endtId;
	private Long endtNo;
	private Boolean isQuote; 
	private SchemeVO scheme;
	private Date processedDate;
	private Integer policyTerm;
	private AuthenticationInfoVO authInfoVO;
	private Integer defaultClassCode;
	private PremiumVO premiumVO;
	private RenewalVO renewals ;
	private Long polLinkingId;
	private Integer status; // Holds the policy/quote 
	private Date created;
	private List<NonStandardClause> nonStandardClause = new com.mindtree.ruc.cmn.utils.List<NonStandardClause>(NonStandardClause.class); // Non- standard clause is at policy level
	
	private Date validityStartDate;
	private Date startDate;
	private Date endDate;
	
	private Boolean isPrepackaged;
	
	private String conCatRefMsgs;
	
	private boolean isInsuredChanged;
	private Long newEndtId;
	private Date newValidityStartDate;
	private Long newEndtNo;
	private Date polEffectiveDate;
	private Date polExpiryDate;
	private Date endStartDate;
	private Date endEffectiveDate;
	private Long polCustomerId;
	
	private PaymentVO paymentVO;
	
	private Long prevEndtId;
	private Date endtExpiryDate;
	
	private RuleContext ruleContext; 
	
	private List<Integer> savedSections;
	
	private boolean isPolicyExtended;
	//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
	//private boolean isAnnualRentAddedInPAR;
	
	private List<StandardClause> standardClause;

	private Long polRefPolicyNo;
	
	/*Vat Tax*/
 	private BigDecimal polVatRate;  //142244 Vat implementation;
 	private BigDecimal polVatAmt;
	private String polVatRegNo;
	private int polVATCode;
	private BigDecimal polVattableAmt;
	// Added for JLT API Renewal
	private Short policyYear;
	private Boolean isRenewal;
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
	private Integer renewalBasis;
	
	public Integer getRenewalBasis() {
		return renewalBasis;
	}

	public void setRenewalBasis(Integer renewalBasis) {
		this.renewalBasis = renewalBasis;
	}
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends

	public Boolean getIsRenewal() {
		return isRenewal;
	}

	public void setIsRenewal(Boolean isRenewal) {
		this.isRenewal = isRenewal;
	}

	public Short getPolicyYear() {
		return policyYear;
	}

	public void setPolicyYear(Short policyYear) {
		this.policyYear = policyYear;
	}
	
	public BigDecimal getPolVattableAmt() {
		return polVattableAmt;
	}

	public void setPolVattableAmt(BigDecimal polVattableAmt) {
		this.polVattableAmt = polVattableAmt;
	}

	public int getPolVATCode() {
		return polVATCode;
	}

	public void setPolVATCode(int polVATCode) {
		this.polVATCode = polVATCode;
	}

	public BigDecimal getPolVatRate() {
		return polVatRate;
	}

	public void setPolVatRate(BigDecimal polVatRate) {
		this.polVatRate = polVatRate;
	}

	public BigDecimal getPolVatAmt() {
		return polVatAmt;
	}

	public void setPolVatAmt(BigDecimal polVatAmt) {
		this.polVatAmt = polVatAmt;
	}

	public String getPolVatRegNo() {
		return polVatRegNo;
	}

	public void setPolVatRegNo(String polVatRegNo) {
		this.polVatRegNo = polVatRegNo;
	}

	public List<Integer> getSavedSections() {
		return savedSections;
	}

	public void setSavedSections(List<Integer> savedSections) {
		this.savedSections = savedSections;
	}
	public Integer getSelectedSectionId(){
		return selectedSectionId;
	}

	public void setSelectedSectionId( Integer selectedSectionId ){
		this.selectedSectionId = selectedSectionId;
	}

	private Integer selectedSectionId;
	
	public RuleContext getRuleContext() {
		return ruleContext;
	}

	public void setRuleContext(RuleContext ruleContext) {
		this.ruleContext = ruleContext;
	}

	private boolean claimsHistoryExistInMissippi=false;	
	
	public boolean isClaimsHistoryExistInMissippi() {
		return claimsHistoryExistInMissippi;
	}

	public void setClaimsHistoryExistInMissippi(boolean claimsHistoryExistInMissippi) {
		this.claimsHistoryExistInMissippi = claimsHistoryExistInMissippi;
	}
	
	
	private Long basePolicyId;
	
	private Map<Integer,String> commodityMap;
	
	public Long getBasePolicyId(){
		return basePolicyId;
	}

	public void setBasePolicyId( Long basePolicyId ){
		this.basePolicyId = basePolicyId;
	}

	/** The task (referral), if any, that is linked to this instance of PolicyVO. */
	private TaskVO taskDetails;
	
	public Long getPrevEndtId() {
		return prevEndtId;
	}

	public Date getEndtExpiryDate() {
		return endtExpiryDate;
	}

	public void setEndtExpiryDate(Date endtExpiryDate) {
		this.endtExpiryDate = endtExpiryDate;
	}

	public void setPrevEndtId(Long prevEndtId) {
		this.prevEndtId = prevEndtId;
	}

	public boolean isInsuredChanged() {
		return isInsuredChanged;
	}

	public void setInsuredChanged(boolean isInsuredChanged) {
		this.isInsuredChanged = isInsuredChanged;
	}

	public Long getNewEndtId() {
		return newEndtId;
	}

	public void setNewEndtId(Long newEndtId) {
		this.newEndtId = newEndtId;
	}

	public Long getNewEndtNo() {
		return newEndtNo;
	}

	public void setNewEndtNo(Long newEndtNo) {
		this.newEndtNo = newEndtNo;
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


	public Map<Integer, String> getCommodityMap(){
		return commodityMap;
	}

	public void setCommodityMap( Map<Integer, String> commodityMap ){
		this.commodityMap = commodityMap;
	}

	/**
	 * The app flow needs to be available in DAO. The app flow will be set from policy context to 
	 * policyVO here. This will be taken care by policy context hence explicit setting of flow is not 
	 * required
	 *
	 */

	private Flow appFlow;

	public String getConCatRefMsgs() {
		return conCatRefMsgs;
	}

	public void setConCatRefMsgs(String conCatRefMsgs) {
		this.conCatRefMsgs = conCatRefMsgs;
	}


	private java.util.Map< ReferralLocKey , ReferralVO> mapReferralVO = new com.mindtree.ruc.cmn.utils.Map<ReferralLocKey,ReferralVO>(ReferralLocKey.class,ReferralVO.class);
	
	public java.util.Map<ReferralLocKey, ReferralVO> getMapReferralVO() {
		return mapReferralVO;
	}

	public void setMapReferralVO(
			java.util.Map<ReferralLocKey, ReferralVO> mapReferralVO) {
		this.mapReferralVO = mapReferralVO;
	}

	public void  setReferral(ReferralVO referralVO){
		ReferralLocKey referralLocKey=new ReferralLocKey();
		referralLocKey.setRiskGroupId(referralVO.getRiskGroupId());
		referralLocKey.setSectionId(referralVO.getSectionId());
		
		this.mapReferralVO.put(referralLocKey, referralVO);
	}
	
	
	public void  setReferrals(List<ReferralVO> listreferralVO){
		for (ReferralVO referralVO : listreferralVO) {
			ReferralLocKey referralLocKey=new ReferralLocKey();
			referralLocKey.setRiskGroupId(referralVO.getRiskGroupId());
			referralLocKey.setSectionId(referralVO.getSectionId());
			
			this.mapReferralVO.put(referralLocKey, referralVO);
		}
		
	}
	
	public void  removeReferral(ReferralVO referralVO){
		ReferralLocKey referralLocKey=new ReferralLocKey();
		referralLocKey.setRiskGroupId(referralVO.getRiskGroupId());
		referralLocKey.setSectionId(referralVO.getSectionId());
		this.mapReferralVO.remove(referralLocKey);
	}
	
	public void  emptyReferral(){
		this.mapReferralVO=new com.mindtree.ruc.cmn.utils.Map<ReferralLocKey,ReferralVO>(ReferralLocKey.class,ReferralVO.class);
	}
	
	
	private Integer policyTypeCode;
	
	private java.util.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>(EndorsmentVO.class);
	//private java.util.List<RenewalVO> renewals = new com.mindtree.ruc.cmn.utils.List<RenewalVO>(RenewalVO.class);
	private java.util.List<SectionVO> riskDetails = new com.mindtree.ruc.cmn.utils.List<SectionVO>(SectionVO.class);
	
	
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "generalInfo".equals( fieldName ) ) fieldValue = getGeneralInfo();
		if( "policyNo".equals( fieldName ) ) fieldValue = getPolicyNo();
		if( "quoteNo".equals( fieldName ) ) fieldValue = getQuoteNo();
		if( "isQuote".equals( fieldName ) ) fieldValue = getIsQuote();
		if( "scheme".equals( fieldName ) ) fieldValue = getScheme();
		if( "processedDate".equals( fieldName ) ) fieldValue = getProcessedDate();
		if( "policyTerm".equals( fieldName ) ) fieldValue = getPolicyTerm();
		if( "endorsements".equals( fieldName ) ) fieldValue = getEndorsements();
		if( "renewals".equals( fieldName ) ) fieldValue = getRenewals();
		if( "riskDetails".equals( fieldName ) ) fieldValue = getRiskDetails();
		if("authInfoVO".equals(fieldName)) fieldValue = getAuthInfoVO();
		if("defaultClassCode".equals(fieldName)) fieldValue = getDefaultClassCode();
		if("validityStartDate".equals(fieldName)) fieldValue = getValidityStartDate();
		if("isPrepackaged".equals(fieldName)) fieldValue = getIsPrepackaged();
		if( "comments".equals( fieldName ) ) fieldValue = getPolLinkingId();
		if( "endtId".equals( fieldName ) ) fieldValue = getEndtId();
		if( "status".equals( fieldName ) ) fieldValue = getStatus();
		if( "nonStandardClause".equals( fieldName ) ) fieldValue = getNonStandardClause();
		if( "created".equals( fieldName ) ) fieldValue = getCreated();
		if( "paymentVO".equals( fieldName ) ) fieldValue = getPaymentVO();
		if( "endEffectiveDate".equals( fieldName ) ) fieldValue = getEndEffectiveDate();
		if( "endtExpiryDate".equals( fieldName ) ) fieldValue = getEndtExpiryDate();
		if( "basePolicyId".equals( fieldName ) ) fieldValue = getBasePolicyId();
		
		return fieldValue;
	}
	
	public Long getPolicyNo(){
		return policyNo;
	}
	public void setPolicyNo( Long policyNo ){
		this.policyNo = policyNo;
	}
	public Long getQuoteNo(){
		return quoteNo;
	}
	public void setQuoteNo( Long quoteNo ){
		this.quoteNo = quoteNo;
	}
	public Long getEndtId() {
		return endtId;
	}

	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}

	public Boolean getIsQuote(){
		return isQuote;
	}
	public void setIsQuote( Boolean isQuote ){
		this.isQuote = isQuote;
	}
	public SchemeVO getScheme(){
		return scheme;
	}
	public void setScheme( SchemeVO scheme ){
		this.scheme = scheme;
	}
	public Date getProcessedDate(){
		return processedDate;
	}
	public void setProcessedDate( Date processedDate ){
		this.processedDate = processedDate;
	}
	public Integer getPolicyTerm(){
		return policyTerm;
	}
	public void setPolicyTerm( Integer policyTerm ){
		this.policyTerm = policyTerm;
	}
	public List<EndorsmentVO> getEndorsements()
	{
		EndorsmentVO validEndRecord = null;
		if(!Utils.isEmpty(endorsements) && endorsements.size() > 1)
		{
			Iterator<EndorsmentVO> endorseItr = endorsements.iterator();
			while(endorseItr.hasNext())
			{
				validEndRecord = endorseItr.next();
				if(!Utils.isEmpty(validEndRecord.getEndType()))
				{
					endorsements.remove(validEndRecord);
					break;
				}
			}
			if(!Utils.isEmpty(validEndRecord))
			{
				endorsements.add(0, validEndRecord);
			}
		}
		return endorsements;
	}
	public void setEndorsements( List<EndorsmentVO> endorsements ){
		this.endorsements = endorsements;
	}
	public RenewalVO getRenewals(){
		
		
		return renewals;
	}
	public void setRenewals( RenewalVO renewals ){
		this.renewals = renewals;
	}
	public List<SectionVO> getRiskDetails(){
		return riskDetails;
	}
	public void setRiskDetails( List<SectionVO> riskDetails ){
		this.riskDetails = riskDetails;
	}

	public GeneralInfoVO getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(GeneralInfoVO generalInfo) {
		this.generalInfo = generalInfo;
	}

	/**
	 * @return the policyTypeCode
	 */
	public Integer getPolicyTypeCode() {
		return policyTypeCode;
	}

	/**
	 * @param policyTypeCode the policyTypeCode to set
	 */
	public void setPolicyTypeCode(Integer policyTypeCode) {
		this.policyTypeCode = policyTypeCode;
	}
	
	public AuthenticationInfoVO getAuthInfoVO() {
		return authInfoVO;
	}

	public void setAuthInfoVO(AuthenticationInfoVO authInfoVO) {
		this.authInfoVO = authInfoVO;
	}

	public Integer getDefaultClassCode() {
		return defaultClassCode;
	}

	public void setDefaultClassCode(Integer defaultClassCode) {
		this.defaultClassCode = defaultClassCode;
	}
	public Date getValidityStartDate() {
		return validityStartDate;
	}

	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	
	/**
	 * @return the premiumVO
	 */
	public PremiumVO getPremiumVO(){
		return premiumVO;
	}

	/**
	 * @param premiumVO the premiumVO to set
	 */
	public void setPremiumVO( PremiumVO premiumVO ){

		/*
		 *In amend flow make sure disc perc is set once and not over written when we set new PremiumVO. which is done when calculate premium is clicked
		 */
		Double discPerc = null;
		Double policyFee = null;
		BigDecimal discAmt = null;
		if( !Utils.isEmpty( this.premiumVO ) ){
			if( !Utils.isEmpty( this.premiumVO.getDiscOrLoadPerc() ) ){
				discPerc = this.premiumVO.getDiscOrLoadPerc();
				discAmt = this.premiumVO.getDiscOrLoadAmt();
				policyFee = this.premiumVO.getPolicyFees();
			}
			
			/*VAT*/
			if( !Utils.isEmpty( this.premiumVO.getVatTax() ) ){
				polVatAmt = new BigDecimal(this.premiumVO.getVatTax());
				
			}
			if( !Utils.isEmpty( this.premiumVO.getVatTaxPerc() ) ){
			polVatRate = new BigDecimal(this.premiumVO.getVatTaxPerc());
				
			}
		}
		this.premiumVO = premiumVO;
		if( !Utils.isEmpty( this.premiumVO )  ){
			if( !Utils.isEmpty( discPerc ) ){
				this.premiumVO.setDiscOrLoadPerc( discPerc );
				this.premiumVO.setDiscOrLoadAmt( discAmt );
				this.premiumVO.setPolicyFees(policyFee);
			}

		}
	}

	/**
	 * @return the isPrepackaged
	 */
	public Boolean getIsPrepackaged() {
		return isPrepackaged;
	}

	/**
	 * @param isPrepackaged the isPrepackaged to set
	 */
	public void setIsPrepackaged(Boolean isPrepackaged) {
		this.isPrepackaged = isPrepackaged;
	}

	/**
	 * @return the polLinkingId
	 */
	public Long getPolLinkingId() {
		return polLinkingId;
	}

	/**
	 * @param polLinkingId the polLinkingId to set
	 */
	public void setPolLinkingId(Long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}

	
	/**
	 * @return the status
	 */
	public Integer getStatus(){
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( Integer status ){
		this.status = status;
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

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}	

	/**
	 * @return the nonStandardClause
	 */
	public List<NonStandardClause> getNonStandardClause(){
		return nonStandardClause;
	}

	/**
	 * @param nonStandardClause the nonStandardClause to set
	 */
	public void setNonStandardClause( List<NonStandardClause> nonStandardClause ){
		this.nonStandardClause = nonStandardClause;
	}
	
	

	public Flow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(Flow appFlow) {
		this.appFlow = appFlow;
	}

	/**
	 * @return the caoncatPolicyNo
	 */
	public String getConcatPolicyNo(){
		return concatPolicyNo;
	}

	/**
	 * @param caoncatPolicyNo the caoncatPolicyNo to set
	 */
	public void setConcatPolicyNo( String concatPolicyNo ){
		this.concatPolicyNo = concatPolicyNo;
	}

	/**
	 * @return the paymentVO
	 */
	public PaymentVO getPaymentVO(){
		return paymentVO;
	}

	/**
	 * @param paymentVO the paymentVO to set
	 */
	public void setPaymentVO( PaymentVO paymentVO ){
		this.paymentVO = paymentVO;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PolicyVO [generalInfo=" + generalInfo + ", policyNo="
		+ policyNo + ", quoteNo=" + quoteNo + ", isQuote=" + isQuote
		+ ", scheme=" + scheme + ", processedDate=" + processedDate
		+ ", policyTerm=" + policyTerm + ", authInfoVO=" + authInfoVO
		+ ", defaultClassCode=" + defaultClassCode
		+ ", validityStartDate=" + validityStartDate + ", premiumVO="
		+ premiumVO + ", polLinkingId=" + polLinkingId
		+ ", isPrepackaged=" + isPrepackaged + ", policyTypeCode="
		+ policyTypeCode + ", endorsements=" + endorsements
		+ ", renewals=" + renewals + ", riskDetails=" + riskDetails
		+ ", status=" + status
		+ ", endtExpiryDate=" + endtExpiryDate +  "]";
}

	public Date getNewValidityStartDate(){
		return newValidityStartDate;
	}

	public void setNewValidityStartDate( Date newValidityStartDate ){
		this.newValidityStartDate = newValidityStartDate;
	}

	public TaskVO getTaskDetails(){
		return taskDetails;
	}

	public void setTaskDetails( TaskVO taskDetails ){
		this.taskDetails = taskDetails;
	}

	public void setEndtNo( Long endtNo ){
		this.endtNo = endtNo;
	}

	public Long getEndtNo(){
		return endtNo;
	}

	/**
	 * @return the isPolicyExtended
	 */
	public boolean isPolicyExtended(){
		return isPolicyExtended;
	}

	/**
	 * @param isPolicyExtended the isPolicyExtended to set
	 */
	public void setPolicyExtended( boolean isPolicyExtended ){
		this.isPolicyExtended = isPolicyExtended;
	}
	
	//Added for Adventnet Id:103286;To Move BI Section from PAR to BI 
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
	/*public boolean isAnnualRentAddedInPAR() {
		return isAnnualRentAddedInPAR;
	}

	public void setAnnualRentAddedInPAR(boolean isAnnualRentAddedInPAR) {
		this.isAnnualRentAddedInPAR = isAnnualRentAddedInPAR;
	}*/

	public void setStandardClause( List<StandardClause> standardClause ){
		this.standardClause = standardClause;
	}

	public List<StandardClause> getStandardClause(){
		return standardClause;
	}

	public Date getPolEffectiveDate() {
		return polEffectiveDate;
	}

	public void setPolEffectiveDate(Date polEffectiveDate) {
		this.polEffectiveDate = polEffectiveDate;
	}	
	
	public Long getPolRefPolicyNo() {
		return polRefPolicyNo;
	}

	public void setPolRefPolicyNo(Long polRefPolicyNo) {
		this.polRefPolicyNo = polRefPolicyNo;
	}
}
