/**
 * 
 */
package com.rsaame.pas.git.dao;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;

/**
 * @author m1016996
 * 
 *  This class is used to populate the pre defined details into the VO's from the Database, for Pre-Package Flow.
 */
public class GoodsInTransitFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{

	@Override
	public RiskGroupDetails getRiskDetails( List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList, List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		GoodsInTransitVO goodsInTransitVO = new GoodsInTransitVO();
		List<CommodityDetailsVO> commodityDetailsVOs = new com.mindtree.ruc.cmn.utils.List<CommodityDetailsVO>( CommodityDetailsVO.class );
		VMasPasFetchBasicDtls vMaBasicDtls = vMasPasFetchBasicDtlsList.get( 0 );
		Integer modeOfTransit = null;

		if( !Utils.isEmpty( vMaBasicDtls.getId().getPrLimit() ) ){
			goodsInTransitVO.setSingleTransitLimit( vMaBasicDtls.getId().getPrLimit().longValue() );
		}
		if( !Utils.isEmpty( vMaBasicDtls.getId().getPrAggLimit() ) ){
			goodsInTransitVO.setEstimatedAnnualCarryValue( vMaBasicDtls.getId().getPrAggLimit().longValue() );
		}
		boolean isDeductibleSet = false;
		for( VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList ){
			
			if(!Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrRtCode() ) && vMasPasFetchBasicDtls.getId().getPrRtCode() != 100 ){
				CommodityDetailsVO commodityDetailsVO = new CommodityDetailsVO();
				
				if(!isDeductibleSet){
				    if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess() ) ){
					//commodityDetailsVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );
					goodsInTransitVO.setDeductible(vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue());
				    }
				    isDeductibleSet = true;
				}
				

				if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrRtCode() ) ){
					commodityDetailsVO.setCommodityType( vMasPasFetchBasicDtls.getId().getPrRtCode() );
				}

				if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPcrEDesc() ) ){
					commodityDetailsVO.setGoodsDescription( vMasPasFetchBasicDtls.getId().getPcrEDesc() );
				}
				
				/*if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess() ) ){
					commodityDetailsVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );
				}*/
				
				commodityDetailsVOs.add( commodityDetailsVO );
			}
			else if(!Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrRtCode() ) && vMasPasFetchBasicDtls.getId().getPrRtCode() == 100){
				
				modeOfTransit = vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().intValue();
				
			}
			
		}
		
		for(CommodityDetailsVO commodityDetailsVO : commodityDetailsVOs){
			if(!Utils.isEmpty( modeOfTransit )){
				commodityDetailsVO.setModeOfTransit( modeOfTransit.shortValue() );
			}
		}
		
		goodsInTransitVO.setCommodityDtls( commodityDetailsVOs );
		return goodsInTransitVO;
	}

	@Override
	public RiskGroup getRiskGroup( List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		
		LocationVO locationVO = new LocationVO();
		return locationVO;
		
	}

}
