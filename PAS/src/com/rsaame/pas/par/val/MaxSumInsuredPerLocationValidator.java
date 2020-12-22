package com.rsaame.pas.par.val;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.mindtree.ruc.cmn.vo.DataHolderVO;

/**
 * @author m1016996
 * This method is used for validating the maximum insured per location in PAR, which should 
 * not exceeds the sum insured given in the general information
 * 
 */

public class MaxSumInsuredPerLocationValidator implements IBeanValidator {

	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) {
		
		boolean success = true;
		DataHolderVO<List<Double>> dataHolderVO = (DataHolderVO<List<Double>>) bean;
		Double maxSumInsuredPerLocGI = dataHolderVO.getData().get(0);
		Double maxSumInsuredPerLocPAR = dataHolderVO.getData().get(1);
		
		if( maxSumInsuredPerLocPAR > maxSumInsuredPerLocGI ){
			errorKeys.add( "par.Loc.SumInsuredPerLoc" );
			success = false;
		}
	
		return success;
	}

}
