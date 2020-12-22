package com.rsaame.pas.b2b.ws.util;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.rsaame.pas.b2b.ws.vo.SBSQuoteEventVO;

/**
 * This class is the application event listener for JLT service related async processing
 * 
 * @author M1000147
 *
 */
@Component
public class SBSEventListener implements ApplicationListener<SBSQuoteEventVO> {

	/**
	 * Handles the event.
	 * 
	 * This function should just call whichever service or handler is needed
	 * to be called based on the Event Type 
	 * 
	 * Example if Type is CREATE_QUOTE then call the batch which processes
	 * 
	 *  The event has a getPolicy() to get data required for processing
	 * 
	 */
	@Override
	public void onApplicationEvent(SBSQuoteEventVO evt) {
		// TODO Auto-generated method stub
		
	}

}
