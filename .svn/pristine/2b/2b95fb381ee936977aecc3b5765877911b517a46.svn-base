/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.io.Serializable;
import java.util.Date;

import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author Sarath Varier
 * @since Ph4 PA implementation
 *
 */
public class InsurerVO implements IFieldValue,Serializable {
	
	private static final long serialVersionUID = 4606643330923541043L;
	
	private Integer id;
	private String companyName;
	private Double percentage;
	private Double adminCharge;
	private Long policyNo;
	private Double premium;
	private Boolean isLeader;
	private Date validityStartDate;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the percentage
	 */
	public Double getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return the adminCharge
	 */
	public Double getAdminCharge() {
		return adminCharge;
	}

	/**
	 * @param adminCharge the adminCharge to set
	 */
	public void setAdminCharge(Double adminCharge) {
		this.adminCharge = adminCharge;
	}

	/**
	 * @return the policyNo
	 */
	public Long getPolicyNo() {
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo(Long policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * @return the premium
	 */
	public Double getPremium() {
		return premium;
	}

	/**
	 * @param premium the premium to set
	 */
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	

	public Boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}


	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if (fieldName.equalsIgnoreCase("id")) fieldValue = getId();
		if (fieldName.equalsIgnoreCase("companyName")) fieldValue = getCompanyName();
		if (fieldName.equalsIgnoreCase("percentage")) fieldValue = getPercentage();
		if (fieldName.equalsIgnoreCase("adminCharge")) fieldValue = getAdminCharge();
		if (fieldName.equalsIgnoreCase("policyNo")) fieldValue = getPolicyNo();
		if (fieldName.equalsIgnoreCase("premium")) fieldValue = getPremium();
		if (fieldName.equalsIgnoreCase("isLeader")) fieldValue = getIsLeader();
		if (fieldName.equalsIgnoreCase("validityStartDate")) fieldValue = getValidityStartDate();
		
		return fieldValue;
	}
	
	public Date getValidityStartDate() {
		return validityStartDate;
	}

	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	@Override
	public String toString(){
		
		return "CoInsuranceVO [id = " + id + ", companyName = " + companyName +", percentage = " + percentage + ", adminCharge = "+ adminCharge 
				+ ", policyNo = " + policyNo + ", premium = " + premium + ",validityStartDate =" +validityStartDate+",isLeader ="+isLeader+"]";
		
	}
	
	@Override
	public int hashCode(){
		final int prime = 47;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		InsurerVO other = (InsurerVO) obj;
		if( id == null ){
			if( other.id != null ) return false;
		}
		else if( !id.equals( other.id ) ) return false;
		return true;
	}
}
