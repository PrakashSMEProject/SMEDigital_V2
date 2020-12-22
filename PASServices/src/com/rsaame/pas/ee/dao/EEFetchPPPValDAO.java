package com.rsaame.pas.ee.dao;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * Loads the EE Section for prepack flow
 * @author m1014438
 *
 */
public class EEFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{
	/**
	 * Loads the configured values and populating to EEVo for the prepack flow
	 */
	@Override
	public RiskGroupDetails getRiskDetails( List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList, List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){

		EEVO eeVO = new EEVO();
		List<EquipmentVO> equipmentDtls = null;

		equipmentDtls = new com.mindtree.ruc.cmn.utils.List<EquipmentVO>( EquipmentVO.class );
		for( VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList ){

			EquipmentVO equipmentVO = new EquipmentVO();
			SumInsuredVO sumInsuredVO = new SumInsuredVO();

			equipmentVO.setEquipmentType( vMasPasFetchBasicDtls.getId().getPrRcCode().toString() );
			//equipmentVO.setEquipmentDesc( vMasPasFetchBasicDtls.getId().getPcrEDesc() );

			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess() ) ){
				sumInsuredVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );
			}
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrSumInsured() ) ){
				sumInsuredVO.setSumInsured( vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue() );

			}
			equipmentVO.setSumInsuredDetails( sumInsuredVO );
			equipmentDtls.add( equipmentVO );

		}

		eeVO.setEquipmentDtls( equipmentDtls );
		return eeVO;
	}

	@Override
	public RiskGroup getRiskGroup( List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){

		LocationVO locationVO = new LocationVO();
		return locationVO;
	}

}
