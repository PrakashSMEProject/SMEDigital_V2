/**
 * 
 */
package com.rsaame.pas.b2b.ws.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.pas.b2b.ws.vo.SBSQuoteEventVO;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * 
 * Just a utility to decouple the application or MVC part from the 
 * asynchronous events 
 * 
 * Use to trigger asynchronous events
 * 
 * @author M1000147
 *
 */public class AsyncHandler {
	
	private static final Logger LOGGER = Logger.getLogger(AsyncHandler.class);
	
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    /**
     * This method asynchronously triggers an event for post-processing
     * creation of quote.
     * 
     * Refer the class SBSEventListener for how the event is processed.
     * 
     * @param text
     */
	public void triggerCreateQuoteEvent(PolicyVO policy) {
		
		System.out.println("triggerCreateQuoteEvent in " + policy.getQuoteNo());
		SBSQuoteEventVO evt = new SBSQuoteEventVO(this, SBSQuoteEventVO.AsyncEventType.CREATE_QUOTE, policy);
		eventPublisher.publishEvent(evt);
		System.out.println("triggerCreateQuoteEvent out." );
		
	}
		
}
