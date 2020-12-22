package com.rsaame.pas.dao.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.app.LoadExistingInputVO;

public interface ISectionLoadDAO {
	/**
	 * Loads data for the section as mentioned in the input and constructs a SectionVO for it.
	 * The input is expected to be of type LoadExistingInputVO.
	 * 
	 * @param input
	 * @return
	 */
	public BaseVO loadExistingData( BaseVO input );
}
