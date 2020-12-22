/**
 * 
 */
package com.rsaame.pas.quote.val;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author m1016303
 *
 */
public class BrokerAccBlockValidator implements IBeanValidator {

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.validation.IBeanValidator#validate(java.lang.Object, java.util.Map, java.util.List)
	 */
	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) {
		boolean success = true;
		
		PolicyVO policyVO = (PolicyVO)bean;
		DataHolderVO<Object[]> brBlockedInput = new DataHolderVO<Object[]>();
		Object quoteDetails[] = new Object[2];
		quoteDetails[0] = null;
		quoteDetails[1] = null;
		
		
		
		/*
		 * While general info save use broke code for fetching status else use quote no while renewal.
		 */
		if(!Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() )){
			quoteDetails[0] = policyVO.getGeneralInfo().getSourceOfBus().getBrokerName();
		}
		
		if(null == quoteDetails[0]){
			quoteDetails[0] = ((UserProfile) policyVO.getLoggedInUser()).getRsaUser().getBrokerId();
		}
		if(!Utils.isEmpty( policyVO.getQuoteNo() )){
			quoteDetails[1] = policyVO.getQuoteNo();
		}
		
		brBlockedInput.setData( quoteDetails  );
		
		try {
			
			DataHolderVO<Byte> brBlockedOutput  = (DataHolderVO<Byte>) TaskExecutor.executeTasks( "FETCH_BR_ACC_STATUS",brBlockedInput  );
			if(!Utils.isEmpty(brBlockedOutput) && !Utils.isEmpty(brBlockedOutput.getData()) &&  brBlockedOutput.getData() == AppConstants.BLOCKED_STATUS ){
				errorKeys.add( "cmn.brkblocked.cl" );
				success = false;
			}
			
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException( "cmn.unknownError", null, "Quotation no is null while convert to policy flow" );
		}
		
		
	
		return success;
	}

}
