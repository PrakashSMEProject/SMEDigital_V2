/**
 * 
 */
package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1014957
 *
 */
public class ClassWisePrmResultVO extends BaseVO{
	private String reportDate;
	private String insuredName;
	private String classDescription ;
	private String policyType ;
	private String policyNo ;
	private String totalPremium ;
	private String  customerName; 
	private String directionCommission;
	private String policyIssueDate;
	private String issueMonth ;
	private String issueYear ;
	private String policyEffDate;
	private String policyID;
	private String sumInsured;
	private String className;

	
	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getClassDescription() {
		return classDescription;
	}
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getTotalPremium() {
		return totalPremium;
	}
	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDirectionCommission() {
		return directionCommission;
	}
	public void setDirectionCommission(String directionCommission) {
		this.directionCommission = directionCommission;
	}
	public String getPolicyIssueDate() {
		return policyIssueDate;
	}
	public void setPolicyIssueDate(String policyIssueDate) {
		this.policyIssueDate = policyIssueDate;
	}
	public String getIssueMonth() {
		return issueMonth;
	}
	public void setIssueMonth(String issueMonth) {
		this.issueMonth = issueMonth;
	}
	public String getIssueYear() {
		return issueYear;
	}
	public void setIssueYear(String issueYear) {
		this.issueYear = issueYear;
	}
	public String getPolicyEffDate() {
		return policyEffDate;
	}
	public void setPolicyEffDate(String policyEffDate) {
		this.policyEffDate = policyEffDate;
	}
	public String getPolicyID() {
		return policyID;
	}
	public void setPolicyID(String policyID) {
		this.policyID = policyID;
	}
	public String getSumInsured() {
		return sumInsured;
	}
	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}
	/**
	 * @return the className
	 */
	public String getClassName(){
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName( String className ){
		this.className = className;
	}

	@Override
	public String toString() {
		return "ClassWisePrmResultVO [reportDate=" + reportDate
				+ ", insuredName=" + insuredName + ", classDescription="
				+ classDescription + ", policyType=" + policyType
				+ ", policyNo=" + policyNo + ", totalPremium=" + totalPremium
				+ ", customerName=" + customerName + ", directionCommission="
				+ directionCommission + ", policyIssueDate=" + policyIssueDate
				+ ", issueMonth=" + issueMonth + ", issueYear=" + issueYear
				+ ", policyEffDate=" + policyEffDate + ", policyID=" + policyID
				+ ", sumInsured=" + sumInsured + ", className=" + className + "]";
	}
	
	

}
