package com.rsaame.pas.tb.dao;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;

/**
 * 
 * @author m1017935
 * This Dao load for the prepackage
 *
 */
public class TravelBaggageFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{

	@Override
	public RiskGroupDetails getRiskDetails( List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList, 
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		
		TravelBaggageVO travelBaggageVO = new TravelBaggageVO();
		Integer index=0;
		java.util.List<TravellingEmployeeVO> travellingEmpDets =  new com.mindtree.ruc.cmn.utils.List<TravellingEmployeeVO>(TravellingEmployeeVO.class);
		
		for(VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList){
			
			TravellingEmployeeVO travellingEmployeeVO = new TravellingEmployeeVO();
			
			SumInsuredVO sumInsuredVO = new SumInsuredVO(); 
			
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess() ) ){
				sumInsuredVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );
			}
			
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrLimit() ) ){
				sumInsuredVO.setSumInsured( vMasPasFetchBasicDtls.getId().getPrLimit().doubleValue() );
			}
			travellingEmployeeVO.setSumInsuredDtl( sumInsuredVO );
			travellingEmployeeVO.setIndex( index );
			travellingEmployeeVO.setGprId( "");
			travellingEmployeeVO.setName( "" );
			travellingEmpDets.add( travellingEmployeeVO );
			index=index+1;
		}
		travelBaggageVO.setTravellingEmpDets( travellingEmpDets );
		travelBaggageVO.setIndex( index );
		
		return travelBaggageVO;
	}

	@Override
	public RiskGroup getRiskGroup( List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		return new LocationVO();
	}

}
