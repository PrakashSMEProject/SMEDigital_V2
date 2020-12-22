/**
 * 
 */
package com.rsaame.pas.dao.model;

import java.util.Date;

/**
 * @author m1019193
 *
 */
public class VTrnRenewalPoliciesHtPas implements java.io.Serializable {

	private VTrnRenewalPoliciesId id;
	private Integer polLocationCode;
	private Short polClassCode;
	private long polEndtId;
	private long polPolicyNo;
	private long polEndtNo;
	private Date polEffectiveDate;
	private String polEffectiveDate1;
	private String polExpiryDate1;
	private Date polExpiryDate;
	private String polConcPolicyNo;
	private Short polDctCode;
	private Short polPolicyYear;
	private Short polBrCode;
	private Integer polDistributionChnl;
	private Integer polCoverNoteHour;
	private Long polInsuredCode;
	private Integer polCcgCode;
	private Long polAgentId;
	private Date polPrintDate;
	private Short polPolicyType;
	
	public VTrnRenewalPoliciesHtPas() {
		
	}

	/**
	 * @param id
	 * @param polLocationCode
	 * @param polClassCode
	 * @param polEndtId
	 * @param polPolicyNo
	 * @param polEndtNo
	 * @param polEffectiveDate
	 * @param polEffectiveDate1
	 * @param polExpiryDate1
	 * @param polExpiryDate
	 * @param polConcPolicyNo
	 * @param polDctCode
	 * @param polPolicyYear
	 * @param polBrCode
	 * @param polDistributionChnl
	 * @param polCoverNoteHour
	 * @param polInsuredCode
	 * @param polCcgCode
	 * @param polAgentId
	 * @param polPrintDate
	 * @param polPolicyType
	 */
	public VTrnRenewalPoliciesHtPas(VTrnRenewalPoliciesId id,
			Integer polLocationCode, Short polClassCode, long polEndtId,
			long polPolicyNo, long polEndtNo, Date polEffectiveDate,
			String polEffectiveDate1, String polExpiryDate1,
			Date polExpiryDate, String polConcPolicyNo, Short polDctCode,
			Short polPolicyYear, Short polBrCode, Integer polDistributionChnl,
			Integer polCoverNoteHour, Long polInsuredCode, Integer polCcgCode,
			Long polAgentId, Date polPrintDate, Short polPolicyType) {
		this.id = id;
		this.polLocationCode = polLocationCode;
		this.polClassCode = polClassCode;
		this.polEndtId = polEndtId;
		this.polPolicyNo = polPolicyNo;
		this.polEndtNo = polEndtNo;
		this.polEffectiveDate = polEffectiveDate;
		this.polEffectiveDate1 = polEffectiveDate1;
		this.polExpiryDate1 = polExpiryDate1;
		this.polExpiryDate = polExpiryDate;
		this.polConcPolicyNo = polConcPolicyNo;
		this.polDctCode = polDctCode;
		this.polPolicyYear = polPolicyYear;
		this.polBrCode = polBrCode;
		this.polDistributionChnl = polDistributionChnl;
		this.polCoverNoteHour = polCoverNoteHour;
		this.polInsuredCode = polInsuredCode;
		this.polCcgCode = polCcgCode;
		this.polAgentId = polAgentId;
		this.polPrintDate = polPrintDate;
		this.polPolicyType = polPolicyType;
	}

	/**
	 * @return the id
	 */
	public VTrnRenewalPoliciesId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(VTrnRenewalPoliciesId id) {
		this.id = id;
	}

	/**
	 * @return the polLocationCode
	 */
	public Integer getPolLocationCode() {
		return polLocationCode;
	}

	/**
	 * @param polLocationCode the polLocationCode to set
	 */
	public void setPolLocationCode(Integer polLocationCode) {
		this.polLocationCode = polLocationCode;
	}

	/**
	 * @return the polClassCode
	 */
	public Short getPolClassCode() {
		return polClassCode;
	}

	/**
	 * @param polClassCode the polClassCode to set
	 */
	public void setPolClassCode(Short polClassCode) {
		this.polClassCode = polClassCode;
	}

	/**
	 * @return the polEndtId
	 */
	public long getPolEndtId() {
		return polEndtId;
	}

	/**
	 * @param polEndtId the polEndtId to set
	 */
	public void setPolEndtId(long polEndtId) {
		this.polEndtId = polEndtId;
	}

	/**
	 * @return the polPolicyNo
	 */
	public long getPolPolicyNo() {
		return polPolicyNo;
	}

	/**
	 * @param polPolicyNo the polPolicyNo to set
	 */
	public void setPolPolicyNo(long polPolicyNo) {
		this.polPolicyNo = polPolicyNo;
	}

	/**
	 * @return the polEndtNo
	 */
	public long getPolEndtNo() {
		return polEndtNo;
	}

	/**
	 * @param polEndtNo the polEndtNo to set
	 */
	public void setPolEndtNo(long polEndtNo) {
		this.polEndtNo = polEndtNo;
	}

	/**
	 * @return the polEffectiveDate
	 */
	public Date getPolEffectiveDate() {
		return polEffectiveDate;
	}

	/**
	 * @param polEffectiveDate the polEffectiveDate to set
	 */
	public void setPolEffectiveDate(Date polEffectiveDate) {
		this.polEffectiveDate = polEffectiveDate;
	}

	/**
	 * @return the polEffectiveDate1
	 */
	public String getPolEffectiveDate1() {
		return polEffectiveDate1;
	}

	/**
	 * @param polEffectiveDate1 the polEffectiveDate1 to set
	 */
	public void setPolEffectiveDate1(String polEffectiveDate1) {
		this.polEffectiveDate1 = polEffectiveDate1;
	}

	/**
	 * @return the polExpiryDate1
	 */
	public String getPolExpiryDate1() {
		return polExpiryDate1;
	}

	/**
	 * @param polExpiryDate1 the polExpiryDate1 to set
	 */
	public void setPolExpiryDate1(String polExpiryDate1) {
		this.polExpiryDate1 = polExpiryDate1;
	}

	/**
	 * @return the polExpiryDate
	 */
	public Date getPolExpiryDate() {
		return polExpiryDate;
	}

	/**
	 * @param polExpiryDate the polExpiryDate to set
	 */
	public void setPolExpiryDate(Date polExpiryDate) {
		this.polExpiryDate = polExpiryDate;
	}

	/**
	 * @return the polConcPolicyNo
	 */
	public String getPolConcPolicyNo() {
		return polConcPolicyNo;
	}

	/**
	 * @param polConcPolicyNo the polConcPolicyNo to set
	 */
	public void setPolConcPolicyNo(String polConcPolicyNo) {
		this.polConcPolicyNo = polConcPolicyNo;
	}

	/**
	 * @return the polDctCode
	 */
	public Short getPolDctCode() {
		return polDctCode;
	}

	/**
	 * @param polDctCode the polDctCode to set
	 */
	public void setPolDctCode(Short polDctCode) {
		this.polDctCode = polDctCode;
	}

	/**
	 * @return the polPolicyYear
	 */
	public Short getPolPolicyYear() {
		return polPolicyYear;
	}

	/**
	 * @param polPolicyYear the polPolicyYear to set
	 */
	public void setPolPolicyYear(Short polPolicyYear) {
		this.polPolicyYear = polPolicyYear;
	}

	/**
	 * @return the polBrCode
	 */
	public Short getPolBrCode() {
		return polBrCode;
	}

	/**
	 * @param polBrCode the polBrCode to set
	 */
	public void setPolBrCode(Short polBrCode) {
		this.polBrCode = polBrCode;
	}

	/**
	 * @return the polDistributionChnl
	 */
	public Integer getPolDistributionChnl() {
		return polDistributionChnl;
	}

	/**
	 * @param polDistributionChnl the polDistributionChnl to set
	 */
	public void setPolDistributionChnl(Integer polDistributionChnl) {
		this.polDistributionChnl = polDistributionChnl;
	}

	/**
	 * @return the polCoverNoteHour
	 */
	public Integer getPolCoverNoteHour() {
		return polCoverNoteHour;
	}

	/**
	 * @param polCoverNoteHour the polCoverNoteHour to set
	 */
	public void setPolCoverNoteHour(Integer polCoverNoteHour) {
		this.polCoverNoteHour = polCoverNoteHour;
	}

	/**
	 * @return the polInsuredCode
	 */
	public Long getPolInsuredCode() {
		return polInsuredCode;
	}

	/**
	 * @param polInsuredCode the polInsuredCode to set
	 */
	public void setPolInsuredCode(Long polInsuredCode) {
		this.polInsuredCode = polInsuredCode;
	}

	/**
	 * @return the polCcgCode
	 */
	public Integer getPolCcgCode() {
		return polCcgCode;
	}

	/**
	 * @param polCcgCode the polCcgCode to set
	 */
	public void setPolCcgCode(Integer polCcgCode) {
		this.polCcgCode = polCcgCode;
	}

	/**
	 * @return the polAgentId
	 */
	public Long getPolAgentId() {
		return polAgentId;
	}

	/**
	 * @param polAgentId the polAgentId to set
	 */
	public void setPolAgentId(Long polAgentId) {
		this.polAgentId = polAgentId;
	}

	/**
	 * @return the polPrintDate
	 */
	public Date getPolPrintDate() {
		return polPrintDate;
	}

	/**
	 * @param polPrintDate the polPrintDate to set
	 */
	public void setPolPrintDate(Date polPrintDate) {
		this.polPrintDate = polPrintDate;
	}

	/**
	 * @return the polPolicyType
	 */
	public Short getPolPolicyType() {
		return polPolicyType;
	}

	/**
	 * @param polPolicyType the polPolicyType to set
	 */
	public void setPolPolicyType(Short polPolicyType) {
		this.polPolicyType = polPolicyType;
	}
	
	
	
	
}
