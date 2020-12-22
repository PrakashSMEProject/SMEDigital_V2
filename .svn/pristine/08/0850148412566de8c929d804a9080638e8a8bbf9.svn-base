
package com.rsaame.pas.b2b.ws.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "document"
})
public class GetDocumentListResponse {

    @JsonProperty("document")
    private List<Document> document = new ArrayList<Document>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
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

	@JsonProperty("document")
    public List<Document> getDocument() {
        return document;
    }

    @JsonProperty("document")
    public void setDocument(List<Document> document) {
        this.document = document;
    }
    
    public GetDocumentListResponse withDocument(List<Document> document) {
        this.document = document;
        return this;
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
		result = prime * result + ((document == null) ? 0 : document.hashCode());
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
		GetDocumentListResponse other = (GetDocumentListResponse) obj;
		if (document == null) {
			if (other.document != null)
				return false;
		} else if (!document.equals(other.document))
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
		return "GetDocumentListResponse [document=" + document + ", internalReference=" + internalReference + "]";
	}

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
