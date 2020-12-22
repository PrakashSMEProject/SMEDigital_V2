/**
 * 
 */
package com.rsaame.pas.b2b.ws.batch.handler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.rsaame.pas.b2b.ws.batch.input.SBSQuoteEventVO;

/**
 * 
 * Just a utility to decouple the application or MVC part from the 
 * asynchronous events 
 * 
 * Use to trigger asynchronous events
 * 
 * @author M1000147
 *
 */public class SBSEventHandler implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;
    
    /**
     * This method asynchronously triggers an event for post-processing
     * creation of quote.
     * 
     * Refer the class SBSEventListener for how the event is processed.
     * 
     * @param text
     */
	public void triggerEvent(SBSQuoteEventVO evt) {
		eventPublisher.publishEvent(evt);				
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
		this.eventPublisher=arg0;		
	}
		
}
