package com.rsaame.pas.lookup.dao;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface ILookUpDAO{

	public BaseVO getListOfDescription(BaseVO baseVO) throws DataAccessException;
	public BaseVO getDescription(BaseVO baseVO) throws DataAccessException;
	public BaseVO getCode(BaseVO baseVO) throws DataAccessException;
	public void refreshCache(BaseVO baseVO) throws DataAccessException;
}
