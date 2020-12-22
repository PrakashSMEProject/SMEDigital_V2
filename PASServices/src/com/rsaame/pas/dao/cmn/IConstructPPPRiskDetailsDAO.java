package com.rsaame.pas.dao.cmn;

import java.util.List;

import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;

public interface IConstructPPPRiskDetailsDAO {

	/*
	 * Following method is to construct risk group details i.e. ParVO,PublicLiabilityVO and so on 
	 * in order to construct a SectionVO with RiskGroupDetails map consisting of one entry as
	 * RiskGroupDetails as the value with RiskGroup as the key for the same
	 */
	 public  RiskGroupDetails getRiskDetails(List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList);
	
	

	/*
	 * Following method is to construct RiskGroup i.e. LocationVO 
	 * in order to construct a SectionVO with RiskGroupDetails map consisting of one entry
	 * with RiskGroup as the key
	 */
	public  RiskGroup getRiskGroup(List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList);
	
}
