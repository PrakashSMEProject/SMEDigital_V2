/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author M1037404
 *
 */
public class DocsDetails {
	@JsonProperty("DebitNote")
	private Boolean debitNote;
	@JsonProperty("CreditNote")
	private Boolean creditNote;
	@JsonProperty("PolicySchedule")
	private Boolean policySchedule;
	@JsonProperty("Receipt")
	private Boolean receipt;
	@JsonProperty("LetterToBank")
	private Boolean letterToBank;
	public Boolean getDebitNote() {
		return debitNote;
	}
	public Boolean getCreditNote() {
		return creditNote;
	}
	public Boolean getPolicySchedule() {
		return policySchedule;
	}
	public Boolean getReceipt() {
		return receipt;
	}
	public void setDebitNote(Boolean debitNote) {
		this.debitNote = debitNote;
	}
	public void setCreditNote(Boolean creditNote) {
		this.creditNote = creditNote;
	}
	public void setPolicySchedule(Boolean policySchedule) {
		this.policySchedule = policySchedule;
	}
	public void setReceipt(Boolean receipt) {
		this.receipt = receipt;
	}
	public Boolean getLetterToBank() {
		return letterToBank;
	}
	public void setLetterToBank(Boolean letterToBank) {
		this.letterToBank = letterToBank;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((letterToBank == null) ? 0 : letterToBank.hashCode());
		result = prime * result + ((creditNote == null) ? 0 : creditNote.hashCode());
		result = prime * result + ((debitNote == null) ? 0 : debitNote.hashCode());
		result = prime * result + ((policySchedule == null) ? 0 : policySchedule.hashCode());
		result = prime * result + ((receipt == null) ? 0 : receipt.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocsDetails other = (DocsDetails) obj;
		if (letterToBank == null) {
			if (other.letterToBank != null)
				return false;
		} else if (!letterToBank.equals(other.letterToBank))
			return false;
		if (creditNote == null) {
			if (other.creditNote != null)
				return false;
		} else if (!creditNote.equals(other.creditNote))
			return false;
		if (debitNote == null) {
			if (other.debitNote != null)
				return false;
		} else if (!debitNote.equals(other.debitNote))
			return false;
		if (policySchedule == null) {
			if (other.policySchedule != null)
				return false;
		} else if (!policySchedule.equals(other.policySchedule))
			return false;
		if (receipt == null) {
			if (other.receipt != null)
				return false;
		} else if (!receipt.equals(other.receipt))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DocsDetails [debiteNote=" + debitNote + ", creditNote=" + creditNote + ", policySchedule="
				+ policySchedule + ", receipt=" + receipt + ", bankLetter=" + letterToBank + "]";
	}
	
	
}
