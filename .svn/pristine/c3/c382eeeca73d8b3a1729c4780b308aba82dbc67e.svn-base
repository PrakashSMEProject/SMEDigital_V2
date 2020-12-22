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
public class FinancierVO implements IFieldValue,Serializable {
	
	private static final long serialVersionUID = 2857355492801579804L;
	
	private Integer id;
	private String name;
	private Integer typeOfFinance;
	private Double amount;
	private Date expiryDate;
	private Date validityStartDate;
	
	@Override
	public Object getFieldValue(String fieldName) {
		
		Object value = null;
		if ("id".equals(fieldName)) value = getId();
		if ("name".equals(fieldName)) value = getName();
		if ("typeOfFinance".equals(fieldName)) value = getTypeOfFinance();
		if ("amount".equals(fieldName)) value = getAmount();
		if ("expiryDate".equals(fieldName)) value = getExpiryDate();
		if ("validityStartDate".equals(fieldName))value = getValidityStartDate();
		return value;
	}

	public Date getValidityStartDate() {
		return validityStartDate;
	}

	public void setValidityStartDate(Date hpValidityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
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
	 * @return the typeOfFinance
	 */
	public Integer getTypeOfFinance() {
		return typeOfFinance;
	}

	/**
	 * @param typeOfFinance the typeOfFinance to set
	 */
	public void setTypeOfFinance(Integer typeOfFinance) {
		this.typeOfFinance = typeOfFinance;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString(){
		
		return "Financier [id = " + id + ", name = " + name + ", typeOfFinance = " + typeOfFinance + ", amount = " + amount + ", expiryDate = " + expiryDate + ", validityStartDate = "+validityStartDate+"]";
	}
}
