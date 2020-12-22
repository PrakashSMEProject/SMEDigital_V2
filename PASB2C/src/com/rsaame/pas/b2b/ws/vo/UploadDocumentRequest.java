/**
 * 
 */
package com.rsaame.pas.b2b.ws.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsaame.pas.b2b.ws.vo.request.DocumentContent;

/**
 * @author M1037404
 *
 */
public class UploadDocumentRequest {
	
	@JsonProperty("documentContent")
	private DocumentContent documentContent;

	public DocumentContent getDocumentContent() {
		return documentContent;
	}
	public void setDocumentContent(DocumentContent documentContent) {
		this.documentContent = documentContent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentContent == null) ? 0 : documentContent.hashCode());
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
		UploadDocumentRequest other = (UploadDocumentRequest) obj;
		if (documentContent == null) {
			if (other.documentContent != null)
				return false;
		} else if (!documentContent.equals(other.documentContent))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UploadDocumentRequest [documentContent=" + documentContent + "]";
	}
	
}
