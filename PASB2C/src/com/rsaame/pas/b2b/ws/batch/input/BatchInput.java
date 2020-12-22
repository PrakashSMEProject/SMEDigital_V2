package com.rsaame.pas.b2b.ws.batch.input;

import java.io.Serializable;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.vo.bus.PolicyVO;

public class BatchInput implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final long quoteNo;
	private final long endorsementId;
	private final long policyId;
	private final long polLinkingId;
	private final boolean isAttachment;
	private final DataHolderVO<List<BaseVO>> dataHolderVO;
	private final CreateSBSPolicyResponse createSBSPolicyResponse;
	
	
	public static class Builder{
		//Required parameters
		private final long quoteNo;
		private final long endorsementId;
		//Optional parameters
		private long policyId=0;
		private long polLinkingId=0;
		private boolean isAttachment= false;
		private CreateSBSPolicyResponse createSBSPolicyResponse;
		private DataHolderVO<List<BaseVO>> dataHolderVO;
		public Builder(long quoteNo,long endorsementId) {	
			this.quoteNo=quoteNo;
			this.endorsementId=endorsementId;
		}
		
		public Builder policyId(long policyId) {
			this.policyId=policyId;
			return this;
		}
		
		public Builder createSBSPolicyResponse(CreateSBSPolicyResponse createSBSPolicyResponse) {
			this.createSBSPolicyResponse=createSBSPolicyResponse;
			return this;
		}
		public Builder isAttachment(boolean isAttachment) {
			this.isAttachment=isAttachment;
			return this;
		}
		public Builder dataHolderVO(DataHolderVO<List<BaseVO>> dataHolderVO) {
			this.dataHolderVO=dataHolderVO;
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
		this.isAttachment = builder.isAttachment;
		this.createSBSPolicyResponse=builder.createSBSPolicyResponse;
		this.dataHolderVO=builder.dataHolderVO;
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
	public boolean getisAttachment() {
		return this.isAttachment;
	}
	public CreateSBSPolicyResponse getCreateSBSPolicyResponse() {
		return this.createSBSPolicyResponse;
	}
	public DataHolderVO<List<BaseVO>> getdataHolderVO() {
		return this.dataHolderVO;
	}
	
}
