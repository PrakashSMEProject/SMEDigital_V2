/**
 * 
 */
package com.rsaame.pas.renewals.svc;

import java.util.HashMap;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;

/**
 * @author Sarath Varier
 *
 */
public class WCRenewalMatrix implements IRenewalMatrix {

	@Override
	public BaseVO executeRenMatrix(BaseVO polData, Long policyId) {
		/**
		 * Renewal matrix not required for WC
		 * Class created as a placeholder and to demo renewal framework
		 */
		
		DataHolderVO<HashMap<String, Boolean>> result = new DataHolderVO<HashMap<String,Boolean>>();
		HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
		/*
		 * If there is a pending premium or if we need to put the renewal quote on hard stop, set below flag
		 * resultMap.put("HardStop", true);
		 */
		 
		/*
		 * if there is claim policy, we apply loading on renewal quote. In this case set the below flag
		 * resultMap.put("RenewalMatrixApplied", true);
		 * 
		 */
		result.setData(resultMap);
		
		return result;
	}

}
