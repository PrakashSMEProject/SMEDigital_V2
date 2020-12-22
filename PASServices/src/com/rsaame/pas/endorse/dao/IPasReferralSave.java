package com.rsaame.pas.endorse.dao;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;

/**
 * @author M1020859
 *
 */
public interface IPasReferralSave{
	
	public abstract TTrnPasReferralDetails savePasReferralData(BaseVO baseVO);
	
	public Boolean removeReferralData( BaseVO baseVO, List<String> factList );
}
