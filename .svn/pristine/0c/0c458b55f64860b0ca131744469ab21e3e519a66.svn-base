/**
 * 
 */
package com.rsaame.pas.renewals.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1019193
 *RH class to check if the quote is a renewal quote before converting to policy for Home/Travel-Phase 3
 */
public class CheckForRenewalQuoteRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger(CheckForRenewalQuoteRH.class);
	private final static String CHECK_RENEWAL_QUOTE_COMMON ="CHECK_RENEWAL_QUOTE_COMMON";
	private final static String CHECK_TRADE_LICENSE_RENEWAL_COMMON ="CHECK_TRADE_LICENSE_RENEWAL_COMMON";
	@SuppressWarnings("unchecked")
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Response responseObj = new Response();
		DataHolderVO<Boolean> isRenQuote ;		
		LOGGER.debug("*****Inside CheckForRenewalQuote*****Checking for Renewal Quote*****");		
		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		CommonVO commonVO = policyContext.getCommonDetails();
		
		DataHolderVO<Long>  policyId = new DataHolderVO<Long>();
		policyId.setData(commonVO.getPolicyId());					
		isRenQuote  = (DataHolderVO<Boolean>) TaskExecutor.executeTasks(CHECK_RENEWAL_QUOTE_COMMON, policyId);
		
		if(isRenQuote.getData()){
			
			TaskExecutor.executeTasks( CHECK_TRADE_LICENSE_RENEWAL_COMMON, commonVO );
		}
		
		//responseObj.setSuccess(true);
		responseObj.setData(isRenQuote.getData());		
		return responseObj;
		
	}

}
