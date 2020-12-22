package com.rsaame.pas.dao.premium;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.bus.LOB;

/**
 * @author m1014644
 * 
 */
public interface IMinPremiumDAO extends IPremium{

	/**
	 * 
	 * @param tariff
	 * @param sectionId
	 * @param lob
	 * @return
	 * 
	 * 	returns the configured min premium for a section for based on tariff and sectionId
	 * The implementing method will decide the table and other details based on LOB
	 */
	BigDecimal getConfiguredMinPremium( int tariff, short sectionId, int policyType, LOB lob );

	/**
	 * 
	 * @param baseVo
	 * Applies the minimum premium for a LOB
	 */
	void applyMinPremium( BaseVO baseVo, LOB lob );

	/**
	 * 
	 * @param baseVo
	 * @return
	 * Returns the mimium premium applied for a quote/policy
	 */
	BigDecimal getAppliedMiniumPremium( BaseVO baseVo, LOB lob  );

	/**
	 * 
	 * @param baseVo
	 * @return
	 * Returns the mimium premium to be applied for a quote/policy,
	 * If the quote/Policy premium is greater than minimum premium zero will be returned
	 */
	BigDecimal getMiniumPremiumToApply( BaseVO baseVo, LOB lob  );
}
