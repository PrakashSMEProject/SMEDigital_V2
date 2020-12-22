/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author Sarath Varier
 * @since Generalization
 *
 */
public class PersonDetailsVO extends PremiumVO implements IFieldValue,Serializable {
	
	private static final long serialVersionUID = 145765L;
	
	private String name;
	private Integer type;
	private Integer category;
	private BigDecimal annualIncome;
	private SumInsuredVO sumInsuredVO;
	private Character gender;
	private Date dateOfBirth;
	private String personID;
	private Integer age;
	private Integer relationShip;
	private Date joiningDate;
	private Date endDate;
	private String nomineeName;
	private Integer gprId;
	private Date validityStartDate;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}

	/**
	 * @return the annualIncome
	 */
	public BigDecimal getAnnualIncome() {
		return annualIncome;
	}

	/**
	 * @param annualIncome the annualIncome to set
	 */
	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	/**
	 * @return the sumInsuredVO
	 */
	public SumInsuredVO getSumInsuredVO() {
		return sumInsuredVO;
	}

	/**
	 * @param sumInsuredVO the sumInsuredVO to set
	 */
	public void setSumInsuredVO(SumInsuredVO sumInsuredVO) {
		this.sumInsuredVO = sumInsuredVO;
	}

	/**
	 * @return the gender
	 */
	public Character getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Character gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the personID
	 */
	public String getPersonID() {
		return personID;
	}

	/**
	 * @param personID the personID to set
	 */
	public void setPersonID(String personID) {
		this.personID = personID;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the relationShip
	 */
	public Integer getRelationShip() {
		return relationShip;
	}

	/**
	 * @param relationShip the relationShip to set
	 */
	public void setRelationShip(Integer relationShip) {
		this.relationShip = relationShip;
	}

	/**
	 * @return the joiningDate
	 */
	public Date getJoiningDate() {
		return joiningDate;
	}

	/**
	 * @param joiningDate the joiningDate to set
	 */
	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the nomineeName
	 */
	public String getNomineeName() {
		return nomineeName;
	}

	/**
	 * @param nomineeName the nomineeName to set
	 */
	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}


	public void setGprId(Integer gprId) {
		this.gprId = gprId;
	}


	public Integer getGprId() {
		return gprId;
	}


	/**
	 * @param validityStartDate the validityStartDate to set
	 */
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}


	/**
	 * @return the validityStartDate
	 */
	public Date getValidityStartDate() {
		return validityStartDate;
	}

}
