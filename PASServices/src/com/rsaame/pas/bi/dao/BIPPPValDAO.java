/**
 * 
 */
package com.rsaame.pas.bi.dao;

import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.WCVO;

/**
 * @author m1019703
 *
 */
public class BIPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{
	

	@Override
	public RiskGroupDetails getRiskDetails(
			List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {
		
		BIVO biVO = new BIVO();
		String eDesc = null;
		
		/* Set information related to building covered */
		for(VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList)
		{
			eDesc = vMasPasFetchBasicDtls.getId().getPcrEDesc();
			if(!Utils.isEmpty(eDesc) && eDesc.equals(SvcConstants.BI_INCR_COST_WORKING_LIMIT ))
			{
				biVO.setWorkingLimit(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue());
				biVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().longValue());
			}	
			else if(!Utils.isEmpty(eDesc) && eDesc.equals(SvcConstants.BI_PRE_RENT_RECEIVABLES ))
			{
				biVO.setRentRecievable(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue());
				biVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().longValue());
			}
			else if(!Utils.isEmpty(eDesc) && eDesc.equals(SvcConstants.BI_INDEMNITY_PERIOD ))
			{
				biVO.setIndemnityPeriod(vMasPasFetchBasicDtls.getId().getPrSumInsured().intValue());
				biVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().longValue());
			}
			else if(!Utils.isEmpty(eDesc) && eDesc.equals(SvcConstants.BI_ESTIMATED_GROSS_INCOME ))
			{
				biVO.setEstimatedGrossIncome(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue());
				biVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().longValue());
			}	
			//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
			//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
			/*else if(!Utils.isEmpty(eDesc) && eDesc.equals(SvcConstants.BI_PRE_ANNUAL_RENT ))
			{
				biVO.setAnnualRent(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue());
				biVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().longValue());
			}*/
		}
		return biVO;
	}

	@Override
	public RiskGroup getRiskGroup(
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {


		LocationVO locationVO = new LocationVO();	
		locationVO.setOccTradeGroup(vMasPasFetchBasicInfoList.get(0).getId().getTradeGrp());
		
		return locationVO;
	}


}
