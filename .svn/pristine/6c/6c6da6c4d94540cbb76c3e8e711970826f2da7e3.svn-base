/**
 * 
 */
package com.rsaame.pas.dao.premium;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;

/**
 * @author Sarath Varier
 *
 */
public class MonolineMinPremiumDAO extends AbstractMinPremiumDAO{

	@Override
	public void applyMinPremium( BaseVO baseVo, LOB lob ){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
	}
	
	@Override
	public BigDecimal getAppliedMiniumPremium( BaseVO baseVo, LOB lob ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getMiniumPremiumToApply( BaseVO baseVo, LOB lob ){

		PolicyDataVO policyDataVO = (PolicyDataVO) baseVo;
		double amount =  policyDataVO.getPremiumVO().getPremiumAmt() ;
		BigDecimal miniumPremiumToApply = BigDecimal.ZERO;
		BigDecimal miniumPremium = BigDecimal.ZERO;
		BigDecimal proratedMinPremium = BigDecimal.ZERO;
		miniumPremium = getConfiguredMinPremium( policyDataVO.getScheme().getTariffCode(), 
				Short.valueOf( Utils.getSingleValueAppConfig( policyDataVO.getCommonVO().getLob().toString() + "_SECTION_ID" ) ),
				Integer.valueOf( policyDataVO.getPolicyType() ), lob );
		proratedMinPremium = new BigDecimal(miniumPremium.toString());
		if(!policyDataVO.getPolicyTerm().equals(Integer.valueOf( 1 ))){
			proratedMinPremium = PremiumHelper.getProratedBasePremium(policyDataVO, miniumPremium);
		}
		miniumPremiumToApply = proratedMinPremium.subtract( BigDecimal.valueOf(amount) );
		return miniumPremiumToApply;//.compareTo( BigDecimal.ZERO ) > 0 ? miniumPremiumToApply : BigDecimal.ZERO;
	}

}
