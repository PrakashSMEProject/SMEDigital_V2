package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface IFetchPPPValSvc {

	/* 
	 * Following service method will be implemented to fetch 
	 * prepackage details by invoking BaseFetchPPPValDAO
	 * 
	 */
	public BaseVO fetchPPPVal(BaseVO input);
}
