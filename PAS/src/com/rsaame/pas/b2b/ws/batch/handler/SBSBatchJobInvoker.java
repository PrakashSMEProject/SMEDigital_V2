package com.rsaame.pas.b2b.ws.batch.handler;

import javax.servlet.http.HttpServletRequest;

import com.rsaame.pas.b2b.ws.batch.input.BatchInput;
import com.rsaame.pas.b2b.ws.batch.input.SBSQuoteEventVO;

public class SBSBatchJobInvoker {

	private SBSEventHandler eventPublisher;
	
	public void createQuote(BatchInput batchInput) {
		SBSQuoteEventVO evt = new SBSQuoteEventVO(this, SBSQuoteEventVO.AsyncEventType.CREATE_QUOTE, batchInput);
		eventPublisher.triggerEvent(evt);
	}
	public void updateQuote(BatchInput batchInput) {
		SBSQuoteEventVO evt = new SBSQuoteEventVO(this, SBSQuoteEventVO.AsyncEventType.UPDATE_QUOTE, batchInput);
		eventPublisher.triggerEvent(evt);
	}
	public void converQuoteToPolicy(BatchInput batchInput) {
		SBSQuoteEventVO evt = new SBSQuoteEventVO(this, SBSQuoteEventVO.AsyncEventType.CONVERT_TO_POLICY, batchInput);
		eventPublisher.triggerEvent(evt);
	}
	public SBSEventHandler getEventPublisher() {
		return eventPublisher;
	}
	public void setEventPublisher(SBSEventHandler eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	

}
