/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author m1016303
 *
 */
public class StandardClause extends ClauseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean isSelected;
	private Long clauseCode;
	private String amount;
	private Long sectionID;
	private boolean isDeletedInCurrentEndt;
	private boolean existingSelection;
	/**
	 * @return the isSelected
	 */
	public boolean isSelected(){
		return isSelected;
	}
	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected( boolean isSelected ){
		this.isSelected = isSelected;
	}
	/**
	 * @return the clauseCode
	 */
	public Long getClauseCode() {
		return clauseCode;
	}
	/**
	 * @param clauseCode the clauseCode to set
	 */
	public void setClauseCode(Long clauseCode) {
		this.clauseCode = clauseCode;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the sectionID
	 */
	public Long getSectionID() {
		return sectionID;
	}
	/**
	 * @param sectionID the sectionID to set
	 */
	public void setSectionID(Long sectionID) {
		this.sectionID = sectionID;
	}
	


	public boolean isDeletedInCurrentEndt(){
		return isDeletedInCurrentEndt;
	}
	public void setDeletedInCurrentEndt( boolean isDeletedInCurrentEndt ){
		this.isDeletedInCurrentEndt = isDeletedInCurrentEndt;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StandardClause [isSelected=" + isSelected + ", sectionID=" + sectionID
				+ ", amount=" + amount + ", clauseCode="+ clauseCode +"]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( amount == null ) ? 0 : amount.hashCode() );
		result = prime * result + ( ( clauseCode == null ) ? 0 : clauseCode.hashCode() );
		result = prime * result + ( isSelected ? 1231 : 1237 );
		result = prime * result + ( ( sectionID == null ) ? 0 : sectionID.hashCode() );
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		StandardClause other = (StandardClause) obj;
		if( amount == null ){
			if( other.amount != null ) return false;
		}
		else if( !amount.equals( other.amount ) ) return false;
		if( clauseCode == null ){
			if( other.clauseCode != null ) return false;
		}
		else if( !clauseCode.equals( other.clauseCode ) ) return false;
		if( isSelected != other.isSelected ) return false;
		if( sectionID == null ){
			if( other.sectionID != null ) return false;
		}
		else if( !sectionID.equals( other.sectionID ) ) return false;
		return true;
	}
	
	public void setExistingSelection( boolean existingSelection ){
		this.existingSelection = existingSelection;
	}
	public boolean isExistingSelection(){
		return existingSelection;
	}

	// Comparator for sorting the list by ClauseCode for AdventNet Id: 101752
    public static Comparator<StandardClause> StdClauseCode = new Comparator<StandardClause>() {

	public int compare(StandardClause s1, StandardClause s2) {

	   Long clno1 = s1.getClauseCode();
	   Long clno2 = s2.getClauseCode();

	   //For ascending order
	   return (int) (clno1-clno2);

	   //For descending order
	   //clno2-clno1;
   }};
	
	
}
