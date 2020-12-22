package com.rsaame.pas.mb.dao;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * Loads the MB Section for prepack flow
 * @author m1014438
 *
 */
public class MBFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{

	@Override
	public RiskGroupDetails getRiskDetails( List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList, List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		MBVO mbVO = new MBVO();
		List<MachineDetailsVO> machineryDetails = null;

		machineryDetails = new com.mindtree.ruc.cmn.utils.List<MachineDetailsVO>( MachineDetailsVO.class );
		for( VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList ){

			MachineDetailsVO machineDetailsVo = new MachineDetailsVO();
			SumInsuredVO sumInsuredVO = new SumInsuredVO();

			machineDetailsVo.setMachineryType( vMasPasFetchBasicDtls.getId().getPrRcCode() );
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess() ) ){
				sumInsuredVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );
			}
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrSumInsured() ) ){
				sumInsuredVO.setSumInsured( vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue() );

			}
			machineDetailsVo.setSumInsuredVO( sumInsuredVO );
			machineryDetails.add( machineDetailsVo );

		}

		mbVO.setMachineryDetails( machineryDetails );
		return mbVO;
	}

	@Override
	public RiskGroup getRiskGroup( List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		LocationVO locationVO = new LocationVO();
		return locationVO;
	}

}
