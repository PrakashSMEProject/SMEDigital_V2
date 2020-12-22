/**
 * 
 */
package com.rsaame.pas.vo.bus;

import org.springframework.web.multipart.MultipartFile;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author Sarath Varier
 * @since Phase 3 - RSA Direct - Make a claim migration
 *
 */

public class VehicleVO extends BaseVO {
	
	private static final long serialVersionUID = 7196651721618066904L;
	private Integer makeCode;
	private Integer modelCode;
	private String registrationNo;
	private MultipartFile registartionCard;
	private String registartionCardFilePath;
	private MultipartFile driverLicence;
	private String driverLicenceFilePath;
	private String location;
	private String driverName;
	private String dlNumber;
	
	

	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if("makeCode".equals(fieldName))fieldValue = getMakeCode();
		if("modelCode".equals(fieldName))fieldValue = getModelCode();
		if("registrationNo".equals(fieldName))fieldValue = getRegistrationNo();
		if("registartionCard".equals(fieldName))fieldValue = getRegistartionCard();
		if("driverLicence".equals(fieldName))fieldValue = getDriverLicence();
		if("location".equals(fieldName))fieldValue = getLocation();
		if("registartionCardFilePath".equals(fieldName))fieldValue = getRegistartionCardFilePath();
		if("driverLicenceFilePath".equals(fieldName))fieldValue = getDriverLicenceFilePath();
		if("driverName".equals(fieldName))fieldValue = getDriverName();
		if("dlNumber".equals(fieldName))fieldValue = getDlNumber();
		
		return fieldValue;
	}


	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getDlNumber() {
		return dlNumber;
	}


	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}


	/**
	 * @return the makeCode
	 */
	public Integer getMakeCode() {
		return makeCode;
	}


	/**
	 * @param makeCode the makeCode to set
	 */
	public void setMakeCode(Integer makeCode) {
		this.makeCode = makeCode;
	}


	/**
	 * @return the modelCode
	 */
	public Integer getModelCode() {
		return modelCode;
	}


	/**
	 * @param modelCode the modelCode to set
	 */
	public void setModelCode(Integer modelCode) {
		this.modelCode = modelCode;
	}


	/**
	 * @return the registrationNo
	 */
	public String getRegistrationNo() {
		return registrationNo;
	}


	/**
	 * @param registrationNo the registrationNo to set
	 */
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}


	/**
	 * @return the registartionCard
	 */
	public MultipartFile getRegistartionCard() {
		return registartionCard;
	}


	/**
	 * @param registartionCard the registartionCard to set
	 */
	public void setRegistartionCard(MultipartFile registartionCard) {
		this.registartionCard = registartionCard;
	}


	/**
	 * @return the driverLicence
	 */
	public MultipartFile getDriverLicence() {
		return driverLicence;
	}


	/**
	 * @param driverLicence the driverLicence to set
	 */
	public void setDriverLicence(MultipartFile driverLicence) {
		this.driverLicence = driverLicence;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * @return the registartionCardFilePath
	 */
	public String getRegistartionCardFilePath() {
		return registartionCardFilePath;
	}


	/**
	 * @param registartionCardFilePath the registartionCardFilePath to set
	 */
	public void setRegistartionCardFilePath(String registartionCardFilePath) {
		this.registartionCardFilePath = registartionCardFilePath;
	}


	/**
	 * @return the driverLicenceFilePath
	 */
	public String getDriverLicenceFilePath() {
		return driverLicenceFilePath;
	}


	/**
	 * @param driverLicenceFilePath the driverLicenceFilePath to set
	 */
	public void setDriverLicenceFilePath(String driverLicenceFilePath) {
		this.driverLicenceFilePath = driverLicenceFilePath;
	}


	@Override
	public String toString(){
		return "VehicleVO [ makeCode="+ makeCode + ", modelCode=" + modelCode + ", registrationNo=" + registrationNo 
				+ ", registartionCard=" + registartionCard.getName() + ", driverLicence=" + driverLicence.getName() 
				+ ", location=" + location + ", driverName=" + driverName + ", dlNumber=" + dlNumber + "]";
	}

}
