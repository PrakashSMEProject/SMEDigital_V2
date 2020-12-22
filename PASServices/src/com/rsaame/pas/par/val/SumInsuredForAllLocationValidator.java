package com.rsaame.pas.par.val;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IBeanValidator;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public class SumInsuredForAllLocationValidator implements IBeanValidator{

	@Override
	public boolean validate( Object bean, Map<String, String> parameters, List<String> errorKeys ){
		boolean success = true;
		double sumPerLoc = 0;
		double sumPerAllLoc = 0;
		System.out.println( "In validate" );
		if( bean instanceof SectionVO ){

			SectionVO sectionVO = (SectionVO) bean;
			java.util.Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
			for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
				ParVO parVO = (ParVO) locationEntry.getValue();
				if( !Utils.isEmpty( parVO.getCovers() ) && !Utils.isEmpty( parVO.getCovers().getPropertyCoversDetails() ) ){
					java.util.List<PropertyRiskDetails> propertyCoversDetails = parVO.getCovers().getPropertyCoversDetails();
					sumPerLoc = 0;
					for( PropertyRiskDetails propertyRiskDetails : propertyCoversDetails ){
						if( !Utils.isEmpty( propertyRiskDetails.getCover() ) ){
							sumPerLoc += propertyRiskDetails.getCover();
						}
					}
					sumPerAllLoc += sumPerLoc;

				}
			}
			if( sumPerAllLoc > 50000000 ){
				errorKeys.add("par.Pol.Sum.Insured.Exceeds");
				success = false;
			}
		}
		return success;
	}

}
