/**
 * 
 */
package com.rsaame.pas.quote.val;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author m1016303
 *
 */
public class RenQuoEffDateValidator implements IBeanValidator {

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.validation.IBeanValidator#validate(java.lang.Object, java.util.Map, java.util.List)
	 */
	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) {
		boolean success = true;
		
		PolicyVO policyVO = (PolicyVO)bean;
		Date quoEffectiveDate = null ; 
				
		/*
		 *   validate function called only for Renewals Quotation to validate Effective date is greater than Expiry date of Original policy
		 */
		if(!Utils.isEmpty(policyVO) && !Utils.isEmpty(policyVO.getScheme()) && !Utils.isEmpty(policyVO.getScheme().getEffDate())){
			quoEffectiveDate = policyVO.getScheme().getEffDate();
		} 
		/*
		 * set effective date as sysdate, the day when we convert to policy that day becomes effective date for policy
		 */
		DataHolderVO<Date> polExpDateVO = (DataHolderVO<Date>) TaskExecutor.executeTasks( "FETCH_POL_EXP_DATE", policyVO );
		Date polExpDate = polExpDateVO.getData();  
		DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date expiryDate = outputFormatter.parse(outputFormatter.format(polExpDate));
			if(!Utils.isEmpty(quoEffectiveDate) && expiryDate.after(quoEffectiveDate)){
				errorKeys.add( "pas.gi.effectiveDate" );
				success = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException( "cmn.unknownError", null, "\"expiry date\" is null while renewal flow" );
		}
		
		
	
		return success;
	}

}
