package com.rsaame.pas.b2b.ws.vo;

import org.springframework.context.ApplicationEvent;

import com.rsaame.pas.vo.bus.PolicyVO;


/**
 * This class repesents a custom asynchronous event
 * We can put anything we want here
 * 
 * @author M1000147
 *
 */
public class SBSQuoteEventVO extends ApplicationEvent{

    public static enum AsyncEventType {
        CREATE_QUOTE, CONVERT_TO_POLICY} ;
        
	private AsyncEventType eventType;
    private PolicyVO policy;
    
    public SBSQuoteEventVO(Object source, AsyncEventType eType, PolicyVO data) {
        
    	super(source); //source to be set to parent
        
        this.eventType = eType;
        this.policy = data;
    }
    
    public AsyncEventType getEventType() {
		return eventType;
	}

	public PolicyVO getPolicy() {
		return policy;
	}
    

    
}