package com.rsaame.pas.endorse.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface IPromoCodeConfigDAO{
	
	public abstract void savePromotionalCd( BaseVO baseVO );

	public abstract void savePromotionalDiscCover(BaseVO baseVO);

	void savePromoDiscount(BaseVO baseVO);
}
