package com.rsaame.pas.dos.dao;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;

public class DeteriorationOfStockFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{

	@Override
	public RiskGroupDetails getRiskDetails( List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList, List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		DeteriorationOfStockVO dosVO = new DeteriorationOfStockVO();
		List<DeteriorationOfStockDetailsVO> stockDetails = null;

		stockDetails = new com.mindtree.ruc.cmn.utils.List<DeteriorationOfStockDetailsVO>( DeteriorationOfStockDetailsVO.class );
		for( VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList ){

			DeteriorationOfStockDetailsVO stockDetailsVo = new DeteriorationOfStockDetailsVO();
			SumInsuredVO sumInsuredVO = new SumInsuredVO();

			stockDetailsVo.setDeteriorationOfStockType(vMasPasFetchBasicDtls.getId().getPrRcCode().toString());
			
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess() ) ){
				sumInsuredVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );
			}
			
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrSumInsured() ) ){
				sumInsuredVO.setSumInsured( vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue() );

			}
			
			stockDetailsVo.setSumInsuredDetails( sumInsuredVO );
			
			/*if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPcrEDesc() ) ){
				stockDetailsVo.setDeteriorationOfStockDesc( vMasPasFetchBasicDtls.getId().getPcrEDesc() );

			}*/
			
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrRcCode() ) ){
				stockDetailsVo.setDeteriorationOfStockType( vMasPasFetchBasicDtls.getId().getPrRcCode().toString() );

			}
			
			/*if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrLimit() ) ){
				stockDetailsVo.setDeteriorationOfStockQuantity( vMasPasFetchBasicDtls.getId().getPrLimit().intValue() );

			}*/
			
			stockDetails.add( stockDetailsVo );

		}

		dosVO.setDeteriorationOfStockDetails( stockDetails );
		return dosVO;
	}

	@Override
	public RiskGroup getRiskGroup( List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		LocationVO locationVO = new LocationVO();
		return locationVO;
	}

}
