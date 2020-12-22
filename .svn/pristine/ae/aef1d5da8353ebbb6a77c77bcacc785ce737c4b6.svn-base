/**
 * 
 */
package com.rsaame.pas.fidelity.dao;

import java.util.List;

import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;

/**
 * @author m1016303
 *
 */
public class FidelityFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{
	@Override
	public RiskGroupDetails getRiskDetails( List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList, List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){

		FidelityVO fidelityVO = new FidelityVO();
		List<FidelityNammedEmployeeDetailsVO> employeeDetailsVOs = null;
		List<FidelityUnnammedEmployeeVO> unnammedEmployeeVOs = null;

		employeeDetailsVOs = new com.mindtree.ruc.cmn.utils.List<FidelityNammedEmployeeDetailsVO>( FidelityNammedEmployeeDetailsVO.class );
		unnammedEmployeeVOs = new com.mindtree.ruc.cmn.utils.List<FidelityUnnammedEmployeeVO>( FidelityUnnammedEmployeeVO.class );
		for( VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList ){

			FidelityNammedEmployeeDetailsVO namedEmployee = new FidelityNammedEmployeeDetailsVO();
			FidelityUnnammedEmployeeVO unNamedEmployee = new FidelityUnnammedEmployeeVO();

			fidelityVO.setAggregateLimit( vMasPasFetchBasicDtls.getId().getPrAggLimit().doubleValue() );
			fidelityVO.setDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );

			//namedEmployee.setLimitPerPerson( vMasPasFetchBasicDtls.getId().getPrLimit().doubleValue() );
			//namedEmployee.setEmpType(vMasPasFetchBasicDtls.getId().getPrRtCode());
			unNamedEmployee.setTotalNumberOfEmployee( vMasPasFetchBasicDtls.getId().getSecNumPersons().intValue());
			unNamedEmployee.setLimitPerPerson( vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue() );
			unNamedEmployee.setEmpType( vMasPasFetchBasicDtls.getId().getPrLimit().intValue());
			
			employeeDetailsVOs.add( namedEmployee );
			unnammedEmployeeVOs.add( unNamedEmployee );
		}

		//fidelityVO.setFidelityEmployeeDetails( employeeDetailsVOs );
		fidelityVO.setUnnammedEmployeeDetails( unnammedEmployeeVOs );
		return fidelityVO;
	}

	@Override
	public RiskGroup getRiskGroup( List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){

		LocationVO locationVO = new LocationVO();
		return locationVO;
	}
}
