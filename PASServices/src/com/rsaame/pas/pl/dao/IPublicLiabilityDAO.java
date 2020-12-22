/**
 * 
 */
package com.rsaame.pas.pl.dao;

import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;

/**
 * @author m1014644
 *
 */
public interface IPublicLiabilityDAO {

	public abstract BaseVO loadPLSection(BaseVO baseVO);
	public abstract BaseVO savePLSection(BaseVO baseVO);
	public abstract List<TTrnPolicyQuo> getQuote(Long quoteNo, Long endtId);
	public abstract Date getPreparedDate(Long quoteNo);
	
}
