package com.rsaame.pas.tariff.dao;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface ITariffDAO {

	public String getTarLocation(String tarCode) throws DataAccessException;
	
}
