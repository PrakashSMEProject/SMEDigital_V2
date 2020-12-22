package com.rsaame.pas.b2c.ws.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.rsaame.pas.b2c.wsException.ValidationError;

public class RetrieveHomeOptionalCoversResponse {
	@JsonProperty("Status")
	private Boolean status;
	@JsonProperty("Message")
	private String message;
	@JsonProperty("OptionalCovers")
	private List<OptionalCovers> optionalCovers;
	@JsonProperty("Errors")
	private List<ValidationError> errors;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<OptionalCovers> getOptionalCovers() {
		return optionalCovers;
	}

	public void setOptionalCovers(List<OptionalCovers> optionalCovers) {
		this.optionalCovers = optionalCovers;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((optionalCovers == null) ? 0 : optionalCovers.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		RetrieveHomeOptionalCoversResponse other = (RetrieveHomeOptionalCoversResponse) obj;
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (optionalCovers == null) {
			if (other.optionalCovers != null)
				return false;
		} else if (!optionalCovers.equals(other.optionalCovers))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetrieveHomeOptionalCoversResponse [status=" + status + ", message=" + message + ", optionalCovers="
				+ optionalCovers + ", errors=" + errors + "]";
	}

}
