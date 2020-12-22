package com.rsaame.pas.par.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * 
 * @author m1014644
 *
 */
public interface IParDAO {

	public abstract BaseVO parLoad(BaseVO baseVO);

	public abstract BaseVO parSaveDAO(BaseVO baseVO);
	
	public abstract BaseVO parLoadBlds(BaseVO baseVO);
	
	
}