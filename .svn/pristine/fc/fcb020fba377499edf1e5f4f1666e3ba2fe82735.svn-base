/**
 * 
 */
package com.rsaame.pas.dao.premium;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.vo.bus.LOB;

/**
 * @author m1014644
 * Any Lob in ePlatform will extent this class to implement the minimum premium
 */
public abstract class AbstractMinPremiumDAO extends BaseDBDAO implements IMinPremiumDAO{

	private final String GET_MIN_PREMIUM = QueryConstants.FETCH_MIN_PREMIUM;

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.premium.IMinPremiumDAO#getConfiguredMinPremium(int, short, int, com.rsaame.pas.vo.bus.LOB)
	 */
	@Override
	public BigDecimal getConfiguredMinPremium( int tariff, short sectionId, int policyType, LOB lob ){

		BigDecimal miniumPremium = BigDecimal.ZERO;
		java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( GET_MIN_PREMIUM, getHibernateTemplate(), sectionId, policyType, tariff );

		if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
			miniumPremium = ( (BigDecimal) valueHolder.get( 0 ) );
		}
		return miniumPremium;
	}
	
	protected BigDecimal getMiniumPremiumToApply(  int tariff, short sectionId, int policyType,BigDecimal currentPremium,  LOB lob ){

		BigDecimal miniumPremium = BigDecimal.ZERO;
		java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( GET_MIN_PREMIUM, getHibernateTemplate(), tariff, sectionId, policyType );

		if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
			miniumPremium = ( (BigDecimal) valueHolder.get( 0 ) );
		}
		return miniumPremium;
	}

}
