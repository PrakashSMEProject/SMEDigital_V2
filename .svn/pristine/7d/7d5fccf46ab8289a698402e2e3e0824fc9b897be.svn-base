/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.rsaame.pas.b2c.wsException.ValidationError;

/**
 * @author M1037404
 *
 */
public class RetrieveTravelQuoteByPolicyResponse {
	@JsonProperty("Quotes")
	private List<UpdateTravelQuoteResponse> quotes;
	@JsonProperty("Errors")
	private List<ValidationError> errors;
	
	public List<UpdateTravelQuoteResponse> getQuotes() {
		return quotes;
	}
	public List<ValidationError> getErrors() {
		return errors;
	}
	public void setQuotes(List<UpdateTravelQuoteResponse> quotes) {
		this.quotes = quotes;
	}
	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((quotes == null) ? 0 : quotes.hashCode());
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
		RetrieveTravelQuoteByPolicyResponse other = (RetrieveTravelQuoteByPolicyResponse) obj;
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (quotes == null) {
			if (other.quotes != null)
				return false;
		} else if (!quotes.equals(other.quotes))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RetrieveTravelQuoteByPolicyResponse [quotes=" + quotes + ", errors=" + errors + "]";
	}
	
	
	
}
