package com.rsaame.pas.par.val;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public class SumInsuredPerLocationValidator implements IBeanValidator  {
	
	@Override
	public boolean validate(Object bean, Map<String, String> parameters,
			List<String> errorKeys) throws BusinessException{
		boolean success = true ;
		double sum = 0;
		if(bean instanceof SectionVO){
			
			SectionVO sectionVO = (SectionVO) bean;
			java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
		
			for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
				ParVO parVO = (ParVO) locationEntry.getValue();
				if(!Utils.isEmpty( parVO ) && !Utils.isEmpty(parVO.getCovers()) && !Utils.isEmpty(parVO.getCovers().getPropertyCoversDetails()))
				{
					java.util.List<PropertyRiskDetails> propertyCoversDetails = parVO.getCovers().getPropertyCoversDetails();
					sum = 0;
					for(PropertyRiskDetails propertyRiskDetails: propertyCoversDetails){
						if(!Utils.isEmpty(propertyRiskDetails.getCover()))
						{
							sum += propertyRiskDetails.getCover();
						}						
					}
					if(sum > 35000000){
						errorKeys.add( "par.Loc.Sum.Insured.Exceeds" );
						success = false;						
					}
				}				

			}

		}
		return success;
	}

}
