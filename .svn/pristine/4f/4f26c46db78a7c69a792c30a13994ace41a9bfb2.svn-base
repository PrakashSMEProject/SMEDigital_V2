package com.rsaame.pas.b2b.ws.batch.input;

import java.io.Serializable;

public class BatchInput implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final long quoteNo;
	private final long endorsementId;
	private final long policyId;
	private final long polLinkingId;
	
	
	public static class Builder{
		//Required parameters
		private final long quoteNo;
		private final long endorsementId;
		//Optional parameters
		private long policyId=0;
		private long polLinkingId=0;
		public Builder(long quoteNo,long endorsementId) {	
			this.quoteNo=quoteNo;
			this.endorsementId=endorsementId;
		}
		
		public Builder policyId(long policyId) {
			this.policyId=policyId;
			return this;
		}
		
		public Builder policyLinkingId(long policyLinkingId) {
			this.polLinkingId=policyLinkingId;
			return this;
		}
		public BatchInput build() {
			return new BatchInput(this);
		}
	}

	private BatchInput(Builder builder) {
		this.quoteNo=builder.quoteNo;
		this.endorsementId=builder.endorsementId;
		this.policyId=builder.policyId;
		this.polLinkingId=builder.polLinkingId;
	}

	public long getQuoteNo() {
		return this.quoteNo;
	}

	public long getEndorsementId() {
		return this.endorsementId;
	}

	public long getPolicyId() {
		return this.policyId;
	}

	public long getPolLinkingId() {
		return this.polLinkingId;
	}
	
	

}
