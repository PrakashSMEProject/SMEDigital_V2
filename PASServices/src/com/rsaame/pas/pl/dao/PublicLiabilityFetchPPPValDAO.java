package com.rsaame.pas.pl.dao;

import java.util.List;


import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PLUWDetails;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;

public class PublicLiabilityFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO {

	/* LookUp code defined for Any one occurrence and unlimited in the aggregate
	 * */
	private final static Integer AOO_UNLIMITED 	= Integer.valueOf(2);
	/* LookUp code defined for Any one occurrence and in the aggregate
	 * */
	private final static Integer AOO_LIMITED 	= Integer.valueOf(1);
	private static Long turnOver = null;
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO#getRiskDetails(java.util.List, java.util.List)
	 */
	@Override
	public RiskGroupDetails getRiskDetails(
			List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {
		
		PublicLiabilityVO plVO = new PublicLiabilityVO();
		SumInsuredVO siVO = new SumInsuredVO();
		LookUpVO lookUpVO = null;
		LookUpVO updatedLookUpVO = null;
		/*
		 * VMasPasFetchBasicInfo view will hold one record for each section , class and
		 * tariff combination
		 */
		VMasPasFetchBasicInfo vMasPasFetchBasicInfo = vMasPasFetchBasicInfoList.get(0);
		

		
//		plVO.setCommission(vMasPasFetchBasicInfo.getId().getCommission().doubleValue());
		PLUWDetails plUWDetails = new PLUWDetails();
		plUWDetails.setHazardLevel(vMasPasFetchBasicInfo.getId().getHazardLevel());
		plUWDetails.setCategoryRI(vMasPasFetchBasicInfo.getId().getRiCategory());
		plVO.setUwDetails(plUWDetails);
		
		/*
		 * For PublicLiablity section there will be no concept of different risk types configured
		 * as it is the case with PAR where in different contents within the building are configured
		 * as different risk types against basic cover code. 
		 * Assumption : a. vMasPasFetchBasicDtlsList will hold one value within it
		 */
		
		VMasPasFetchBasicDtls vMasPasFetchBasicDtls = vMasPasFetchBasicDtlsList.get(0);
		String desc = vMasPasFetchBasicDtls.getId().getPrLimit().toString();

		/* Fetching the code for the description fetched from basic details */
		lookUpVO = new LookUpVO();
		lookUpVO.setCategory( SvcConstants.PL_LIMIT );
		lookUpVO.setDescription( desc );
		//lookUpVO.setLevel1( SvcConstants.LOOKUP_LEVEL1 );
		lookUpVO.setLevel1(vMasPasFetchBasicDtls.getId().getPrTariff().toString());
		lookUpVO.setLevel2( SvcConstants.LOOKUP_LEVEL2 );
		LookUpService lookupSvc = (LookUpService) Utils.getBean( "lookUpService" );
		updatedLookUpVO = (LookUpVO) lookupSvc.invokeMethod( "getCode", lookUpVO );
		
		plVO.setIndemnityAmtLimit(updatedLookUpVO.getCode().intValue());
		
		if(Utils.isEmpty(ThreadLevelContext.get(SvcConstants.ANNUAL_TURN_OVER)))
		{
			siVO.setSumInsured(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue());
		}
		else
		{
			getTunoverValue(Long.parseLong((String) ThreadLevelContext.get(SvcConstants.ANNUAL_TURN_OVER)));
			siVO.setSumInsured(turnOver.doubleValue());
			turnOver = null;
		}
		siVO.setDeductible(vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue());
		plVO.setSumInsuredDets(siVO);
		
		/*
		 * Sum Insured Basis value will be decided using Limit and aggregate limit value configured.
		 * If aggregate limit value is not null or 0 then then Sum Insured Basis will be 
		 * Any one occurrence and in the aggregate(lookup code - 1) else it will be 
		 * Any one occurrence and unlimited in the aggregate (lookup code - 2) 
		 */
		
		if(Utils.isEmpty(vMasPasFetchBasicDtls.getId().getPrAggLimit())){
			plVO.setSumInsuredBasis(AOO_UNLIMITED);
		}else{
			plVO.setSumInsuredBasis(AOO_LIMITED);
		}
		
		return plVO;
	}

	@Override
	public RiskGroup getRiskGroup(
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {
		
		LocationVO locationVO = new LocationVO();		
		//locationVO.setOccTradeGroup(vMasPasFetchBasicInfoList.get(0).getId().getTradeGrp());
		
		return locationVO;
	}
	
	@Override
	protected void sectionLoadPreProcessing( PolicyVO polVO )
	{
		/* This is getting used in WCFetchPPPValDAO to fetch the employee type for selected business type */
		if(!Utils.isEmpty(polVO.getGeneralInfo()) && !Utils.isEmpty(polVO.getGeneralInfo().getInsured()) &&   
				!Utils.isEmpty(polVO.getGeneralInfo().getInsured().getTurnover()))
		{
			turnOver = polVO.getGeneralInfo().getInsured().getTurnover();
			
		}
	}
	
	
	private synchronized static void setTunOver(Long turnOver) {
		PublicLiabilityFetchPPPValDAO.turnOver = turnOver;
	}

	public void getTunoverValue(Long turnOver ) {
		setTunOver(turnOver);
	}
}
