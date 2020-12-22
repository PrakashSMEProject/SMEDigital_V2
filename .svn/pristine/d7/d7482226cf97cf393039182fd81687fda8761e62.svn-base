package com.rsaame.pas.b2b.ws.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;

public class UploadDocumentResponse {
	
	@JsonProperty("ResponseMessage")
	private String responseMessage;
	
	@JsonProperty("FileName")
	private String fileName;

	@JsonProperty("InternalReferenceNumber")
	private String internalReferenceNumber;
	
	@JsonProperty("SBSWSValidators")
   	private List<SBSWSValidators> sbswsValidators;
       
       public List<SBSWSValidators> getSbswsValidators() {
   		return sbswsValidators;
   	}

   	public void setSbswsValidators(List<SBSWSValidators> sbswsValidators) {
   		this.sbswsValidators = sbswsValidators;
   	}
	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getInternalReferenceNumber() {
		return internalReferenceNumber;
	}

	public void setInternalReferenceNumber(String internalReferenceNumber) {
		this.internalReferenceNumber = internalReferenceNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((internalReferenceNumber == null) ? 0 : internalReferenceNumber.hashCode());
		result = prime * result + ((responseMessage == null) ? 0 : responseMessage.hashCode());
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
		UploadDocumentResponse other = (UploadDocumentResponse) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (internalReferenceNumber == null) {
			if (other.internalReferenceNumber != null)
				return false;
		} else if (!internalReferenceNumber.equals(other.internalReferenceNumber))
			return false;
		if (responseMessage == null) {
			if (other.responseMessage != null)
				return false;
		} else if (!responseMessage.equals(other.responseMessage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UploadDocumentResponse [responseMessage=" + responseMessage + ", fileName=" + fileName
				+ ", internalReferenceNumber=" + internalReferenceNumber + "]";
	}

	
}
