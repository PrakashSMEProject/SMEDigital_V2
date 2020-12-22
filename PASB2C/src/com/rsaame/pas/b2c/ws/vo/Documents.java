/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1037404
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Documents {
	@JsonProperty("DocsInResponse")
	private Boolean docsInResponse;
	@JsonProperty("DocsDetails")
	private DocsDetails docsDetails;
	
	
	@JsonProperty("PolicySchedule")
	private byte[] policySchedule;
	
	@JsonProperty("LetterToBank")
	private byte[] letterToBank;
	
	public Boolean getDocsInResponse() {
		return docsInResponse;
	}
	public DocsDetails getDocsDetails() {
		return docsDetails;
	}
	public void setDocsInResponse(Boolean docsInResponse) {
		this.docsInResponse = docsInResponse;
	}
	public void setDocsDetails(DocsDetails docsDetails) {
		this.docsDetails = docsDetails;
	}
	
	public byte[] getPolicySchedule() {
		return policySchedule;
	}
	
	public byte[] getLetterToBank() {
		return letterToBank;
	}
	public void setPolicySchedule(byte[] policySchedule) {
		this.policySchedule = policySchedule;
	}
	public void setLetterToBank(byte[] letterToBank) {
		this.letterToBank = letterToBank;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docsDetails == null) ? 0 : docsDetails.hashCode());
		result = prime * result + ((docsInResponse == null) ? 0 : docsInResponse.hashCode());
		result = prime * result + Arrays.hashCode(letterToBank);
		result = prime * result + Arrays.hashCode(policySchedule);
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
		Documents other = (Documents) obj;
		if (docsDetails == null) {
			if (other.docsDetails != null)
				return false;
		} else if (!docsDetails.equals(other.docsDetails))
			return false;
		if (docsInResponse == null) {
			if (other.docsInResponse != null)
				return false;
		} else if (!docsInResponse.equals(other.docsInResponse))
			return false;
		if (!Arrays.equals(letterToBank, other.letterToBank))
			return false;
		if (!Arrays.equals(policySchedule, other.policySchedule))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Documents [docsInResponse=" + docsInResponse + ", docsDetails=" + docsDetails + ", policySchedule="
				+ Arrays.toString(policySchedule) + ", letterToBank=" + Arrays.toString(letterToBank) + "]";
	}
	
}
