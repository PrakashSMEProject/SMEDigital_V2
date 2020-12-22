package com.rsaame.pas.quote.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface ICheckIfInsuredExistsDAO {
	
	public abstract BaseVO checkIfInsuredExists(BaseVO baseVO);
	public abstract BaseVO commonCheckIfInsuredExists(BaseVO baseVO);
	}
