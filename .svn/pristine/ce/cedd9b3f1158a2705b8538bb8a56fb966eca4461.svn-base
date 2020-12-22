package com.rsaame.pas.b2b.ws.batch.input;

import java.io.Serializable;

import org.springframework.context.ApplicationEvent;


/**
 * This class repesents a custom asynchronous event
 * We can put anything we want here
 * 
 * @author M1000147
 *
 */
public class SBSQuoteEventVO extends ApplicationEvent implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum AsyncEventType {
        CREATE_QUOTE,DELETE_QUOTE,UPDATE_QUOTE,CONVERT_TO_POLICY} ;
        
	private AsyncEventType eventType;
    private BatchInput batchInput;
    
    public SBSQuoteEventVO(Object source, AsyncEventType eType, BatchInput data) {
    	super(source); //source to be set to parent
        this.eventType = eType;
        this.batchInput = data;
    }
    
    public AsyncEventType getEventType() {
		return eventType;
	}

	public BatchInput getBatchInput() {
		return batchInput;
	}
	public String eventName() {
		return this.eventType.name();
	}
    

    
}