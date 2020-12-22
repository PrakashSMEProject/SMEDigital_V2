package com.rsaame.pas.b2c.exception;

/**
 * @author m1020637
 *
 */
public class SystemException extends BaseException {
	private static final long serialVersionUID = 6876067120351211957L;

    /**
     * This constructor can be used if the error key is the default, the one for "Unknown error". 
     * 
     * @param cause The root cause 3rd party exception
     * @param messageParts The custom, scenario-specific message passed as parts (to avoid concatenation)
     */
    public SystemException( Throwable cause, String messageParts ){
    	super(  messageParts , cause );
    }

    public SystemException( String reason ){
    	super( reason );
    }
}
