/**
 * 
 */
package com.rsaame.pas.taglib.dao;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author M1021201
 *
 */
public interface ILoadCoverDAO{

	public BaseVO getRiskCoverDetails(BaseVO baseVO);

	public BaseVO getSchemeDetails( BaseVO baseVO );

	public TravelInsuranceVO getPackages( CommonVO commonVO );

	public java.util.List<CoverDetailsVO> getTravelCovers( SchemeVO scheme );
	
	public java.util.List<TTrnPremiumVOHolder> getPremiumRecords( CommonVO commonVO );
	
	public List<TravelPackageVO> getTravelPackageList( BaseVO baseVO );
}
