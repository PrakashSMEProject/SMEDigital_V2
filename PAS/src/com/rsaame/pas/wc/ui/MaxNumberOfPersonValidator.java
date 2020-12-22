package com.rsaame.pas.wc.ui;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.mindtree.ruc.cmn.vo.DataHolderVO;

/**
 * 
 * @author m1016996
 *
 */
public class MaxNumberOfPersonValidator implements IBeanValidator {

	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) {
		boolean success = true;
		DataHolderVO<List<Integer>> dataHolderVO = (DataHolderVO<List<Integer>>) bean;
		Integer maxNumberOfEmpGI = dataHolderVO.getData().get(0);
		Integer maxNumberOfEmpWC = dataHolderVO.getData().get(1);
		
		if( maxNumberOfEmpWC > maxNumberOfEmpGI ){
			errorKeys.add( "pas.wc.maxNumberOfPersons" );
			success = false;
		}
	
		return success;
	}

}
