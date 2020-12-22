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
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author m1016303
 *
 */
public class PrmCreatedDtEffDtValidator implements IBeanValidator {

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
		 *  Oman : validate function called only for Oman to validate created date and effective date
		 */
		if(!Utils.isEmpty(policyVO) && !Utils.isEmpty(policyVO.getScheme()) && !Utils.isEmpty(policyVO.getScheme().getEffDate())){
			quoEffectiveDate = policyVO.getScheme().getEffDate();
		} 
		/*
		 * set effective date as sysdate, the day when we convert to policy that day becomes effective date for policy
		 */
		Date sysDate = new Date();  
		DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date effectiveDate = outputFormatter.parse(outputFormatter.format(sysDate));
			if(!Utils.isEmpty(quoEffectiveDate) && effectiveDate.after(quoEffectiveDate)){
				errorKeys.add( "pas.convertToPolicy.createdDtEffectiveDt" );
				success = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException( "cmn.unknownError", null, "\"effective date\" is null while convert to policy flow" );
		}
		
		
	
		return success;
	}

}
