/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author Sarath Varier
 * @since Phase 3 - RSA Direct - Make a claim migration
 *
 */
public class ClaimsVO extends BaseVO {
	
	private static final long serialVersionUID = 8768571996631160148L;
	private Long claimId;
	private String policyNo;
	private Date dateOfAccident;
	private String lossDescription;
	private MultipartFile policeReport;
	private String policeReportFilePath;
	private String remarks;
	private String errorMessage;
	private Date preparedDate;
	private MultipartFile claimForm;
	private String claimFormFilePath;
	private String accidentType;
	private MultipartFile mrtaForm;
	private String mrtaFormFilePath;
	private String thirdParty;
	private String tpName;

	public String getClaimFormFilePath() {
		return claimFormFilePath;
	}

	public void setClaimFormFilePath(String claimFormFilePath) {
		this.claimFormFilePath = claimFormFilePath;
	}

	public String getMrtaFormFilePath() {
		return mrtaFormFilePath;
	}

	public void setMrtaFormFilePath(String mrtaFormFilePath) {
		this.mrtaFormFilePath = mrtaFormFilePath;
	}

	public String getTpName() {
		return tpName;
	}

	public void setTpName(String tpName) {
		this.tpName = tpName;
	}

	public String getThirdParty() {
		return thirdParty;
	}

	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	public MultipartFile getMrtaForm() {
		return mrtaForm;
	}

	public void setMrtaForm(MultipartFile mrtaForm) {
		this.mrtaForm = mrtaForm;
	}

	public String getAccidentType() {
		return accidentType;
	}

	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}

	public MultipartFile getClaimForm() {
		return claimForm;
	}

	public void setClaimForm(MultipartFile claimForm) {
		this.claimForm = claimForm;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if("claimId".equals(fieldName)) fieldValue = getClaimId();
		if("policyNo".equals(fieldName)) fieldValue = getPolicyNo();
		if("dateOfAccident".equals(fieldName)) fieldValue = getDateOfAccident();
		if("lossDescription".equals(fieldName)) fieldValue = getLossDescription();
		if("policeReport".equals(fieldName)) fieldValue = getPoliceReport();
		if("remarks".equals(fieldName)) fieldValue = getRemarks();
		if("policeReportFilePath".equals(fieldName)) fieldValue = getPoliceReportFilePath();
		if("preparedDate".equals(fieldName)) fieldValue = getPreparedDate();
		if("claimForm".equals(fieldName)) fieldValue = getClaimForm();
		if("accidentType".equals(fieldName)) fieldValue = getAccidentType();
		if("mrtaForm".equals(fieldName)) fieldValue = getMrtaForm();
		if("thirdParty".equals(fieldName)) fieldValue = getThirdParty();
		if("tpName".equals(fieldName)) fieldValue = getTpName();
		if("claimFormFilePath".equals(fieldName)) fieldValue = getClaimFormFilePath();
		if("mrtaFormFilePath".equals(fieldName)) fieldValue = getMrtaFormFilePath();
		
		return fieldValue;
	}

	/**
	 * @return the claimId
	 */
	public Long getClaimId() {
		return claimId;
	}

	/**
	 * @param claimId the claimId to set
	 */
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	/**
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * @return the dateOfAccident
	 */
	public Date getDateOfAccident() {
		return dateOfAccident;
	}

	/**
	 * @param dateOfAccident the dateOfAccident to set
	 */
	public void setDateOfAccident(Date dateOfAccident) {
		this.dateOfAccident = dateOfAccident;
	}

	/**
	 * @return the lossDescription
	 */
	public String getLossDescription() {
		return lossDescription;
	}

	/**
	 * @param lossDescription the lossDescription to set
	 */
	public void setLossDescription(String lossDescription) {
		this.lossDescription = lossDescription;
	}

	/**
	 * @return the policeReport
	 */
	public MultipartFile getPoliceReport() {
		return policeReport;
	}

	/**
	 * @param policeReport the policeReport to set
	 */
	public void setPoliceReport(MultipartFile policeReport) {
		this.policeReport = policeReport;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * @return the policeReportFilePath
	 */
	public String getPoliceReportFilePath() {
		return policeReportFilePath;
	}

	/**
	 * @param policeReportFilePath the policeReportFilePath to set
	 */
	public void setPoliceReportFilePath(String policeReportFilePath) {
		this.policeReportFilePath = policeReportFilePath;
	}

	public Date getPreparedDate() {
		return preparedDate;
	}

	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	@Override
	public String toString(){
		
		return "ClaimsVO [ claimId=" + claimId + ", policyNo=" + policyNo + ", dateOfAccident=" +dateOfAccident + ", lossDescription=" + lossDescription 
				+ ", policeReport=" + policeReport.getName() +", remarks=" +remarks + ", preparedDate=" + preparedDate + ", claimForm=" + claimForm.getName() 
				+ ", accidentType=" + accidentType + ", mrtaForm=" + mrtaForm.getName() + ", thirdParty=" + thirdParty + ", tpName=" + tpName +" ]";
	}
}
