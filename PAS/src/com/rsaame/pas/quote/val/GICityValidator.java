/**
 * 
 */
package com.rsaame.pas.quote.val;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author m1016303
 *
 */
public class GICityValidator implements IBeanValidator {

	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) {
		
		boolean success = true;
		
		PolicyVO policyVO = (PolicyVO)bean;
		
		if( !Utils.isEmpty( policyVO) && !Utils.isEmpty(policyVO.getGeneralInfo().getInsured()) &&
				!Utils.isEmpty(policyVO.getGeneralInfo().getInsured()) && !Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getCity())){
			if(policyVO.getGeneralInfo().getInsured().getCity() != Integer.parseInt(AppConstants.GI_CITY_CODE)){
				errorKeys.add( "pas.gi.city" );
				success = false;
			}
			
		}
	
		return success;
	
	}

}
