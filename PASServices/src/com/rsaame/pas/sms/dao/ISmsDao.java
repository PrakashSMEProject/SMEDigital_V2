package com.rsaame.pas.sms.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1020278
 * 
 */
public interface ISmsDao {
	public BaseVO searchTransactionSms(BaseVO baseVO);

	public BaseVO sendSms(BaseVO baseVO);
}
