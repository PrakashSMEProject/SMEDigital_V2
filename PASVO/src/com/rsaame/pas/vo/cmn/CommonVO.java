/**
 * 
 */
package com.rsaame.pas.vo.cmn;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.TaskVO;

/**
 * @author M1014644
 *
 */
public class CommonVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	
	private Long policyNo;
	private String concatPolicyNo;
	private Long quoteNo;
	private Long endtId;
	private Long endtNo;
	private Boolean isQuote;
	private Flow appFlow;
	private Integer status;
	private Date vsd;
	private Integer locCode;
	private LOB lob;
	private Long policyId;
	private Short docCode;
	private PremiumVO premiumVO;
	private Date polEffectiveDate;
	private Date endtEffectiveDate;
	private Long locId; // The building Id of the building or the basic id. Null otherwise
	/** The task (referral), if any, that is linked to this instance of PolicyVO. */
	private TaskVO taskDetails;
	private boolean isOldContentPPCode;	
	private BusinessChannel channel;
	private Boolean isRenewals;
	private Boolean islegacyPolicy;
	private Date polExpiryDate;
	
	private Integer vatCode;/*VAT 142244*/
	//142244 WC Vat Implementation 
	private String vatRegNo;
	

	/**
	 * @return the vatCode
	 */
	public Integer getVatCode() {
		return vatCode;
	}

	/**
	 * @param vatCode the vatCode to set
	 */
	public void setVatCode(Integer vatCode) {
		this.vatCode = vatCode;
	}

	/**
	 * @return
	 */
	public PremiumVO getPremiumVO(){
		return premiumVO;
	}

	/**
	 * @param premiumVO
	 */
	public void setPremiumVO( PremiumVO premiumVO ){
		this.premiumVO = premiumVO;
	}
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * @return the vsd
	 */
	public Date getVsd(){
		return vsd;
	}


	/**
	 * @param vsd the vsd to set
	 */
	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}


	/**
	 * @return the locCode
	 */
	public Integer getLocCode(){
		return locCode;
	}


	/**
	 * @param locCode the locCode to set
	 */
	public void setLocCode( Integer locCode ){
		this.locCode = locCode;
	}

	/**
	 * @param lob
	 */
	public void setLob( LOB lob ){
		this.lob = lob;
	}


	/**
	 * @return LOB
	 */
	public LOB getLob(){
		return lob;
	}

	/**
	 * @param policyId
	 */
	public void setPolicyId( Long policyId ){
		this.policyId = policyId;
	}

	/**
	 * @return Long
	 */
	public Long getPolicyId(){
		return policyId;
	}


	/**
	 * @return the policyNo
	 */
	public Long getPolicyNo(){
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo( Long policyNo ){
		this.policyNo = policyNo;
	}

	/**
	 * @return the concatPolicyNo
	 */
	public String getConcatPolicyNo(){
		return concatPolicyNo;
	}

	/**
	 * @param concatPolicyNo the concatPolicyNo to set
	 */
	public void setConcatPolicyNo( String concatPolicyNo ){
		this.concatPolicyNo = concatPolicyNo;
	}

	/**
	 * @return the quoteNo
	 */
	public Long getQuoteNo(){
		return quoteNo;
	}

	/**
	 * @param quoteNo the quoteNo to set
	 */
	public void setQuoteNo( Long quoteNo ){
		this.quoteNo = quoteNo;
	}

	/**
	 * @return the isQuote
	 */
	public Boolean getIsQuote(){
		return isQuote;
	}

	/**
	 * @param isQuote the isQuote to set
	 */
	public void setIsQuote( Boolean isQuote ){
		this.isQuote = isQuote;
	}
	
	

	/**
	 * @return Flow
	 */
	public Flow getAppFlow(){
		return appFlow;
	}

	/**
	 * @param appFlow
	 */
	public void setAppFlow( Flow appFlow ){
		this.appFlow = appFlow;
	}

	
	/**
	 * @return the endtId
	 */
	public Long getEndtId(){
		return endtId;
	}

	/**
	 * @param endtId the endtId to set
	 */
	public void setEndtId( Long endtId ){
		this.endtId = endtId;
	}

	/**
	 * @return the endtNo
	 */
	public Long getEndtNo(){
		return endtNo;
	}

	/**
	 * @param endtNo the endtNo to set
	 */
	public void setEndtNo( Long endtNo ){
		this.endtNo = endtNo;
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


	/**
	 * @param docCode
	 */
	public void setDocCode( Short docCode ){
		this.docCode = docCode;
	}


	/**
	 * @return Integer
	 */
	public Short getDocCode(){
		return docCode;
	}
	
	public Date getPolEffectiveDate(){
		return polEffectiveDate;
	}

	public void setPolEffectiveDate( Date polEffectiveDate ){
		this.polEffectiveDate = polEffectiveDate;
	}
	
	

	public Long getLocId(){
		return locId;
	}

	public void setLocId( Long locId ){
		this.locId = locId;
	}

	
	/**
	 * @return the endtEffectiveDate
	 */
	public Date getEndtEffectiveDate(){
		return endtEffectiveDate;
	}

	/**
	 * @param endtEffectiveDate the endtEffectiveDate to set
	 */
	public void setEndtEffectiveDate( Date endtEffectiveDate ){
		this.endtEffectiveDate = endtEffectiveDate;
	}

	public TaskVO getTaskDetails(){
		return taskDetails;
	}

	public void setTaskDetails( TaskVO taskDetails ){
		this.taskDetails = taskDetails;
	}

	public BusinessChannel getChannel() {
		return channel;
	}

	public void setChannel(BusinessChannel channel) {
		this.channel = channel;
	}

	@Override
	public String toString(){
		return "CommonVO [policyNo=" + policyNo + ", concatPolicyNo=" + concatPolicyNo + ", quoteNo=" + quoteNo + ", endtId=" + endtId + ", endtNo=" + endtNo + ", isQuote="
				+ isQuote + ", appFlow=" + appFlow + ", status=" + status + ", vsd=" + vsd + ", locCode=" + locCode + ", lob=" + lob + ", policyId=" + policyId + ", docCode="
				+ docCode + ", premiumVO=" + premiumVO + ", polEffectiveDate=" + polEffectiveDate + ", endtEffectiveDate=" + endtEffectiveDate + ", locId=" + locId
				+ ", taskDetails=" + taskDetails + "]";
	}

	public boolean isOldContentPPCode() {
		return isOldContentPPCode;
	}

	public void setOldContentPPCode(boolean isOldContentPPCode) {
		this.isOldContentPPCode = isOldContentPPCode;
	}

	public Boolean getIsRenewals() {
		return isRenewals;
	}

	public void setIsRenewals(Boolean isRenewals) {
		this.isRenewals = isRenewals;
	}

	public String getVatRegNo() {
		return vatRegNo;
	}

	public void setVatRegNo(String vatRegNo) {
		this.vatRegNo = vatRegNo;
	}

	public Boolean getIslegacyPolicy() {
		return islegacyPolicy;
	}

	public void setIslegacyPolicy(Boolean islegacyPolicy) {
		this.islegacyPolicy = islegacyPolicy;
	}

	public Date getPolExpiryDate() {
		return polExpiryDate;
	}

	public void setPolExpiryDate(Date polExpiryDate) {
		this.polExpiryDate = polExpiryDate;
	}

}
