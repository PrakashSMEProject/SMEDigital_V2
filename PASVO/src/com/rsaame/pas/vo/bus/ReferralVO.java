package com.rsaame.pas.vo.bus;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseVO;

public class ReferralVO extends BaseVO{

	private static final long serialVersionUID = 1L;

	private boolean isReferral = true;
	
	private String  actionIdentifier;
	
	private boolean isConsolidated = false;

	private boolean isMessage = false;
	
	private boolean isNotMessage = false;
	
	public boolean isConsolidated() {
		return isConsolidated;
	}

	public void setConsolidated(boolean isConsolidated) {
		this.isConsolidated = isConsolidated;
	}

	private String riskGroupId;
	private List<String> referralText;
	private Map<String, String> referalDataMap;
	//PHASE 3 START
	private String locationCode; 
	private Map<String, Map<String,String>> refDataTextField;
	//PHASE 3 END
	
	private Integer  tprUserId;
	private String tprUserRole;
	private boolean tempReferral=false;
	
	public Map<String, String> getReferalDataMap(){
		return referalDataMap;
	}

	public void setReferalDataMap( Map<String, String> referalDataMap ){
		this.referalDataMap = referalDataMap;
	}

	private Integer sectionId;
	private Long polLinkingId;
	
	private String sectionName;
	
	private List<String> hardStopTextList;

	//Add to set the type of policy - For issue 77934
	private Integer policyTypeCode;

	public Integer getPolicyTypeCode() {
		return policyTypeCode;
	}

	public void setPolicyTypeCode(Integer policyTypeCode) {
		this.policyTypeCode = policyTypeCode;
	}

	public List<String> getHardStopTextList() {
		return hardStopTextList;
	}

	public void setHardStopTextList(List<String> hardStopTextList) {
		this.hardStopTextList = hardStopTextList;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "riskGroupId".equals( fieldName ) ) fieldValue = getRiskGroupId();
		if( "referralText".equals( fieldName ) ) fieldValue = getReferralText();
		if( "sectionId".equals( fieldName ) ) fieldValue = getSectionId();
		if( "polLinkingId".equals( fieldName ) ) fieldValue = getPolLinkingId();
		if( "sectionName".equals( fieldName ) ) fieldValue = getSectionName();
		if( "policyType".equals( fieldName ) ) fieldValue = getPolicyTypeCode();
		if( "tprUserId".equals( fieldName ) ) fieldValue = getTprUserId();
		if( "tprUserRole".equals( fieldName ) ) fieldValue = getTprUserRole();

		return fieldValue;
	}

	/**
	 * @return the riskGroupId
	 */
	public String getRiskGroupId(){
		return riskGroupId;
	}

	/**
	 * @param riskGroupId the riskGroupId to set
	 */
	public void setRiskGroupId( String riskGroupId ){
		this.riskGroupId = riskGroupId;
	}

	/**
	 * @return the referralText
	 */
	public List<String> getReferralText(){
		return referralText;
	}

	/**
	 * @param referralText the referralText to set
	 */
	public void setReferralText( List<String> referralText ){
		this.referralText = referralText;
	}

	/**
	 * @return the sectionId
	 */
	public Integer getSectionId(){
		return sectionId;
	}

	/**
	 * @param sectionId the sectionId to set
	 */
	public void setSectionId( Integer sectionId ){
		this.sectionId = sectionId;
	}

	/**
	 * @return the polLinkingId
	 */
	public Long getPolLinkingId(){
		return polLinkingId;
	}

	/**
	 * @param polLinkingId the polLinkingId to set
	 */
	public void setPolLinkingId( Long polLinkingId ){
		this.polLinkingId = polLinkingId;
	}

	public String getActionIdentifier() {
		return actionIdentifier;
	}

	public void setActionIdentifier(String actionIdentifier) {
		this.actionIdentifier = actionIdentifier;
	}
	
	//PHASE 3 START
	

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	/**
	 * @return the refDataTextField
	 */
	public Map<String, Map<String, String>> getRefDataTextField() {
		return refDataTextField;
	}

	/**
	 * @param refDataTextField the refDataTextField to set
	 */
	public void setRefDataTextField(
			Map<String, Map<String, String>> refDataTextField) {
		this.refDataTextField = refDataTextField;
	}
	
	/**
	 * @return the isReferral
	 */
	public boolean isReferral() {
		return isReferral;
	}

	/**
	 * @param isReferral the isReferral to set
	 */
	public void setReferral(boolean isReferral) {
		this.isReferral = isReferral;
	}
	//PHASE 3 END

	/**
	 * @return the isMessage
	 */
	public boolean isMessage(){
		return isMessage;
	}

	/**
	 * @param isMessage the isMessage to set
	 */
	public void setMessage( boolean isMessage ){
		this.isMessage = isMessage;
	}

	/**
	 * @return the isNotMessage
	 */
	public boolean isNotMessage(){
		return isNotMessage;
	}

	/**
	 * @param isNotMessage the isNotMessage to set
	 */
	public void setNotMessage( boolean isNotMessage ){
		this.isNotMessage = isNotMessage;
	}

	public Integer getTprUserId() {
		return tprUserId;
	}

	public void setTprUserId(Integer tprUserId) {
		this.tprUserId = tprUserId;
	}

	public String getTprUserRole() {
		return tprUserRole;
	}

	public void setTprUserRole(String tprUserRole) {
		this.tprUserRole = tprUserRole;
	}

	public boolean isTempReferral() {
		return tempReferral;
	}

	public void setTempReferral(boolean tempReferral) {
		this.tempReferral = tempReferral;
	}
	
	
}
