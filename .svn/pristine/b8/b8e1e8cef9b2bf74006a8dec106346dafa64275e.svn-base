package com.rsaame.pas.money.dao;

import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;

public class MoneyFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO {

	@Override
	public RiskGroupDetails getRiskDetails(
			List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {
		
		MoneyVO moneyVO = new MoneyVO();
		
		List<Contents> contentsList = new com.mindtree.ruc.cmn.utils.List<Contents>(Contents.class);
		
		/* Populate Contents value i.e. risk code , risk type , sum insured value, deductibles 
		 * present Contents VO. The same values will be persisted to database
		 * 
		 */
		for(VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList){
			Contents contents = BeanMapper.map(vMasPasFetchBasicDtls, Contents.class);
			contentsList.add(contents);		
		}
		moneyVO.setContentsList(contentsList);
		
		return moneyVO;
	}

	@Override
	public RiskGroup getRiskGroup(
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {
		
		/*
		 * To check if any values to be returned as part of LocationVO currently will return 
		 * a locationVO object
		 */
		
		return new LocationVO();
	}
}
