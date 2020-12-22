/**
 * 
 */
package com.rsaame.pas.dao.premium;

import java.math.BigDecimal;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;

/**
 * @author m1014644
 *
 */
public class HomeMinPremiumDAO extends AbstractMinPremiumDAO{

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.premium.IMinPremiumDAO#applyMinPremium(com.mindtree.ruc.cmn.base.BaseVO, com.rsaame.pas.vo.bus.LOB)
	 */
	@Override
	public void applyMinPremium( BaseVO baseVo, LOB lob ){
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVo;
		
		if(homeInsuranceVO.getCommonVO().getIsQuote()){
			applyMinPremiumQuote(homeInsuranceVO);
		}else{
			applyMinPremiumPolicy(homeInsuranceVO);
		}

	}

	private void applyMinPremiumPolicy( HomeInsuranceVO homeInsuranceVO ){
		java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( QueryConstants.FETCH_TOTAL_POLICY_PRM, getHibernateTemplate(), homeInsuranceVO.getCommonVO()
				.getPolicyId(), homeInsuranceVO.getCommonVO().getEndtId() );
		double polPrm = 0.0;
		if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
			polPrm = ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue();
			homeInsuranceVO.getPremiumVO().setPremiumAmt( polPrm );
		}
		BigDecimal miniumPremiumToApply = getMiniumPremiumToApply( homeInsuranceVO, LOB.HOME );

		if( miniumPremiumToApply.compareTo( BigDecimal.ZERO ) > 0 ){
			List<TTrnPremiumQuo> minCoverPrmRec = getHibernateTemplate().find( QueryConstants.FETCH_MIN_PRM_COVERS, homeInsuranceVO.getCommonVO().getPolicyId(),
					homeInsuranceVO.getCommonVO().getEndtId() );
			if( !Utils.isEmpty( minCoverPrmRec ) ){
				TTrnPremiumQuo minPrmRec = minCoverPrmRec.get( 0 );
				minPrmRec.setPrmPremium(  miniumPremiumToApply  );
				update( minPrmRec );
				getHibernateTemplate().flush();
			}
		}

	}

	private void applyMinPremiumQuote( HomeInsuranceVO homeInsuranceVO ){
		java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( QueryConstants.FETCH_TOTAL_PRM_QUOTE, getHibernateTemplate(), homeInsuranceVO.getCommonVO()
				.getPolicyId(), homeInsuranceVO.getCommonVO().getEndtId() );
		double quotePrm = 0.0;
		if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
			quotePrm = ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue();
			homeInsuranceVO.getPremiumVO().setPremiumAmt( quotePrm );
		}
		BigDecimal miniumPremiumToApply = getMiniumPremiumToApply( homeInsuranceVO, LOB.HOME );

		if( miniumPremiumToApply.compareTo( BigDecimal.ZERO ) > 0 ){
			List<TTrnPremiumQuo> basicCoverPrmRecs = getHibernateTemplate().find( QueryConstants.FETCH_MIN_PRM_COVERS, homeInsuranceVO.getCommonVO().getPolicyId(),
					homeInsuranceVO.getCommonVO().getEndtId() );
			if( !Utils.isEmpty( basicCoverPrmRecs ) ){
				TTrnPremiumQuo basicCoverPrmRec = basicCoverPrmRecs.get( 0 );
				basicCoverPrmRec.setPrmPremium(  miniumPremiumToApply  );
				update( basicCoverPrmRec );
				getHibernateTemplate().flush();
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.premium.IMinPremiumDAO#getAppliedMiniumPremium(com.mindtree.ruc.cmn.base.BaseVO, com.rsaame.pas.vo.bus.LOB)
	 * Currently this method is not used.
	 */
	@Override
	public BigDecimal getAppliedMiniumPremium( BaseVO baseVo, LOB lob ){
		return null;
	}

	@Override
	public BigDecimal getMiniumPremiumToApply( BaseVO baseVo, LOB lob ){

		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVo;
		double amount =  homeInsuranceVO.getPremiumVO().getPremiumAmt() ;
		BigDecimal miniumPremiumToApply = BigDecimal.ZERO;
		miniumPremiumToApply = getConfiguredMinPremium( homeInsuranceVO.getScheme().getTariffCode(), Short.valueOf( Utils.getSingleValueAppConfig( "HOME_SEC_ID" ) ),
				Integer.valueOf( homeInsuranceVO.getScheme().getPolicyType() ), lob ).subtract( BigDecimal.valueOf(amount) );
		return miniumPremiumToApply.compareTo( BigDecimal.ZERO ) > 0 ? miniumPremiumToApply : BigDecimal.ZERO;

	}

}
