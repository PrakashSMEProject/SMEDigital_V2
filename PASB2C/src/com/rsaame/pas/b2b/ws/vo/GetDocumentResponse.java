/**
 * 
 */
package com.rsaame.pas.b2b.ws.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.response.DocumentDownload;

/**
 * @author M1037404
 *
 */
public class GetDocumentResponse {
	@JsonProperty("documentDownload")
	private DocumentDownload documentDownload;
	
    @JsonProperty("internalReference")
    private String internalReference;
    
    @JsonProperty("SBSWSValidators")
	private List<SBSWSValidators> sbswsValidators;
    
  	public List<SBSWSValidators> getSbswsValidators() {
		return sbswsValidators;
	}
	public void setSbswsValidators(List<SBSWSValidators> sbswsValidators) {
		this.sbswsValidators = sbswsValidators;
	}
	public DocumentDownload getDocumentDownload() {
		return documentDownload;
	}
	public void setDocumentDownload(DocumentDownload documentDownload) {
		this.documentDownload = documentDownload;
	}
	public String getInternalReference() {
		return internalReference;
	}
	public void setInternalReference(String internalReference) {
		this.internalReference = internalReference;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentDownload == null) ? 0 : documentDownload.hashCode());
		result = prime * result + ((internalReference == null) ? 0 : internalReference.hashCode());
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
		GetDocumentResponse other = (GetDocumentResponse) obj;
		if (documentDownload == null) {
			if (other.documentDownload != null)
				return false;
		} else if (!documentDownload.equals(other.documentDownload))
			return false;
		if (internalReference == null) {
			if (other.internalReference != null)
				return false;
		} else if (!internalReference.equals(other.internalReference))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GetDocumentResponse [documentDownload=" + documentDownload + ", internalReference=" + internalReference
				+ "]";
	}
}
