package com.rsaame.pas.vo.app;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author M1020278
 * 
 */
public class RenewalResultsSmsVO extends BaseVO implements IFieldValue {

	private static final long serialVersionUID = 1325559060552154319L;
	
	private String concatPolicyNo;
	private BigDecimal quotationNo;
	private String scheme;
	private String nationality;
	private String branch;
	private String smsMode;
	private BigDecimal smsLevel;
	private String smsStatus;
	private long policyId;
	private BigDecimal endtId;
	private BigDecimal polLocationCode;

	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	

	/**
	 * @return the policyId
	 */
	public long getPolicyId(){
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId( long policyId ){
		this.policyId = policyId;
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
	 * @return the quotationNo
	 */
	public BigDecimal getQuotationNo(){
		return quotationNo;
	}



	/**
	 * @param quotationNo the quotationNo to set
	 */
	public void setQuotationNo( BigDecimal quotationNo ){
		this.quotationNo = quotationNo;
	}



	/**
	 * @return the scheme
	 */
	public String getScheme(){
		return scheme;
	}



	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme( String scheme ){
		this.scheme = scheme;
	}



	/**
	 * @return the nationality
	 */
	public String getNationality(){
		return nationality;
	}



	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality( String nationality ){
		this.nationality = nationality;
	}



	/**
	 * @return the branch
	 */
	public String getBranch(){
		return branch;
	}



	/**
	 * @param branch the branch to set
	 */
	public void setBranch( String branch ){
		this.branch = branch;
	}



	/**
	 * @return the smsMode
	 */
	public String getSmsMode(){
		return smsMode;
	}



	/**
	 * @param smsMode the smsMode to set
	 */
	public void setSmsMode( String smsMode ){
		this.smsMode = smsMode;
	}



	/**
	 * @return the smsLevel
	 */
	public BigDecimal getSmsLevel(){
		return smsLevel;
	}



	/**
	 * @param smsLevel the smsLevel to set
	 */
	public void setSmsLevel( BigDecimal smsLevel ){
		this.smsLevel = smsLevel;
	}



	/**
	 * @return the smsStatus
	 */
	public String getSmsStatus(){
		return smsStatus;
	}



	/**
	 * @param string the smsStatus to set
	 */
	public void setSmsStatus( String string ){
		this.smsStatus = string;
	}



	/**
	 * @return the endtId
	 */
	public BigDecimal getEndtId(){
		return endtId;
	}



	/**
	 * @param endtId the endtId to set
	 */
	public void setEndtId( BigDecimal endtId ){
		this.endtId = endtId;
	}



	/**
	 * @return the polLocationCode
	 */
	public BigDecimal getPolLocationCode(){
		return polLocationCode;
	}



	/**
	 * @param polLocationCode the polLocationCode to set
	 */
	public void setPolLocationCode( BigDecimal polLocationCode ){
		this.polLocationCode = polLocationCode;
	}
	
	

}
