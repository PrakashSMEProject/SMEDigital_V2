package com.rsaame.pas.tb.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface ITravelBaggageDAO{
	
	public abstract BaseVO loadTBSection(BaseVO baseVO);
	public abstract BaseVO saveTBSection(BaseVO baseVO);

}
