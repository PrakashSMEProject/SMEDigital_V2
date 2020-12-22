/**
 * 
 */
package com.rsaame.pas.money.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1014644
 *
 */
public interface IMoneyDAO {

	public abstract BaseVO loadMoneySection(BaseVO baseVO);
	public abstract BaseVO saveMoneySection(BaseVO baseVO);
	
}
