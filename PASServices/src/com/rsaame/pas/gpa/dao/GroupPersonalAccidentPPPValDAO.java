package com.rsaame.pas.gpa.dao;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * @author m1019834
 *
 */
public class GroupPersonalAccidentPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO{
/** 
* (non-Javadoc).
* @see com.rsaame.pas.vo.bus.RiskGroupDetails
* @param vMasPasFetchBasicInfoList value
* @param vMasPasFetchBasicDtlsList value
* @return RiskGroupDetails value
*/
	
public RiskGroupDetails getRiskDetails(List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList, List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {
		
GroupPersonalAccidentVO groupPersonalAccidentVO = new GroupPersonalAccidentVO();
final java.util.List<GPAUnnammedEmpVO> gpaUnnammedEmpVO =  new com.mindtree.ruc.cmn.utils.List<GPAUnnammedEmpVO>(GPAUnnammedEmpVO.class);

/**
 * Loop to get number of rows and the field values to be populated in prepack.		
 */
for(VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList){
			
	GPAUnnammedEmpVO gpaUnnammedEmpVODts = new GPAUnnammedEmpVO();
			SumInsuredVO sumInsuredVO = new SumInsuredVO(); 
			

			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess() ) ){
				groupPersonalAccidentVO.setGpaDeductible( vMasPasFetchBasicDtls.getId().getPrCompulsoryExcess().doubleValue() );
			}
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrSumInsured() ) ){
				groupPersonalAccidentVO.setAggregateLimit(Double.valueOf(vMasPasFetchBasicDtls.getId().getPrAggLimit().toString()) );
				gpaUnnammedEmpVODts.setUnnammedAnnualSalary(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue());
			}
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrSumInsured() ) ){
				sumInsuredVO.setSumInsured(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue());
			}
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrRtCode() ) ){
				gpaUnnammedEmpVODts.setUnnammedEmployeeType( vMasPasFetchBasicDtls.getId().getPrLimit().intValue());
			}
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getSecNumPersons() ) )
			{
				gpaUnnammedEmpVODts.setUnnammedNumberOfEmloyee(( vMasPasFetchBasicDtls.getId().getSecNumPersons().intValue()) );
			}
					
			gpaUnnammedEmpVODts.setSumInsuredDetails(sumInsuredVO );
			gpaUnnammedEmpVO.add( gpaUnnammedEmpVODts );
}

		groupPersonalAccidentVO.setGpaUnnammedEmpVO( gpaUnnammedEmpVO );
				
		return groupPersonalAccidentVO;
		
}

/** 
* (non-Javadoc.
* @see com.rsaame.pas.vo.bus.RiskGroup
* @param vMasPasFetchBasicInfoList value
* @return RiskGroup value
*/	
public RiskGroup getRiskGroup( List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList ){
		return new LocationVO();
}


}
