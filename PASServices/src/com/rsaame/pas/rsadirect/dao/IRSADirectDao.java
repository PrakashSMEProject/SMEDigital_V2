/**
 * 
 */
package com.rsaame.pas.rsadirect.dao;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.model.TTrnMakeClaim;

/**
 * @author Sarath Varier
 * @since Phase 3 - Make a Claim migration
 *
 */
public interface IRSADirectDao {
	
	public TTrnMakeClaim submitClaim(TTrnMakeClaim ttrnMakeClaim);	

	public BaseVO getLocation(BaseVO baseVO);	

}
