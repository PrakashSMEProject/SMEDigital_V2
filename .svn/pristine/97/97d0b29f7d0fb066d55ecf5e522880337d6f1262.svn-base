package com.rsaame.pas.wc.dao;

import java.math.BigDecimal;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.WCVO;

public class WCFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO {
	public static final String[] FGB_TARIFF_CODE = Utils.getMultiValueAppConfig( "FGB_TARIFF_CODE" );
	

	@Override
	public RiskGroupDetails getRiskDetails(
			List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {

			WCVO wCVO = new WCVO(); 
			
		/*
		 * For WC section there will different contents within the building are configured
		 * as different risk types against basic cover code and tariff code. 
		 * Assumption : a. vMasPasFetchBasicDtlsList will hold one value within it
		 */
		
		com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO> empTypeDetails = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>( EmpTypeDetailsVO.class );
		
		for( VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList ){
			EmpTypeDetailsVO empTypeDetailsVO = new EmpTypeDetailsVO();
			empTypeDetailsVO = BeanMapper.map(vMasPasFetchBasicDtls, empTypeDetailsVO);
			
			if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrLimit() )){
				String desc = vMasPasFetchBasicDtls.getId().getPrLimit().toString();
				BigDecimal limit = getWCLimitFromLookUp(desc,vMasPasFetchBasicDtls.getId().getPrTariff().toString());
				if(!Utils.isEmpty( limit )){
					empTypeDetailsVO.setLimit( limit );
				}
			}
			String tariff = vMasPasFetchBasicDtls.getId().getPrTariff().toString();
			Short empTypeCode = null;
			
			/* For prepack the empolyee type is based on the business type selected in General Information scree.*/
			if(isPolicyFGB(tariff))
			{
				empTypeCode = SvcUtils.getLookUpCodeForLOneLTwo( SvcConstants.WC_EMP_TYPE_LKP_CATEGORY, tariff, SvcConstants.LOOKUP_LEVEL1);
			}
			else if( !Utils.isEmpty( ThreadLevelContext.get( SvcConstants.PPP_BUSSTYPE_WC_THREADVAR )))
			{
				empTypeCode = SvcUtils.getLookUpCodeForLOneLTwo( SvcConstants.WC_EMP_TYPE_LKP_CATEGORY, ThreadLevelContext.get( SvcConstants.PPP_BUSSTYPE_WC_THREADVAR ).toString(), SvcConstants.LOOKUP_LEVEL1);
			}
			/* 
			 * Employee type value is being auto populate using risk type configured
			 */
			if( !Utils.isEmpty( empTypeCode )){
				empTypeDetailsVO.setEmpType( empTypeCode.intValue() );
			}else{
				empTypeDetailsVO.setEmpType( vMasPasFetchBasicDtls.getId().getPrRtCode() );
			}
			/*
			 * 81123 code changes for jafza scheme
			 */
			if(!SvcUtils.isCombinedTariff(tariff)){
			empTypeDetailsVO.setNoOfEmp(vMasPasFetchBasicDtls.getId().getSecNumPersons());
			empTypeDetailsVO.setWageroll(vMasPasFetchBasicDtls.getId().getPrSumInsured().doubleValue()); }
			empTypeDetails.add( empTypeDetailsVO );
		}
		wCVO.setEmpTypeDetails( empTypeDetails );
		
		return wCVO;	
	}

	@Override
	public RiskGroup getRiskGroup(
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {


		LocationVO locationVO = new LocationVO();	
		locationVO.setOccTradeGroup(vMasPasFetchBasicInfoList.get(0).getId().getTradeGrp());
		
		return locationVO;
	}
	
	/**
	 * Fetching the code for the description fetched from basic details
	 * @param desc
	 * @return
	 */
	private BigDecimal getWCLimitFromLookUp( String desc,String tariff ){
		
		LookUpVO lookUpVO = null;
		LookUpVO updatedLookUpVO = null;
		lookUpVO = new LookUpVO();
		lookUpVO.setCategory( SvcConstants.WC_LIMIT );
		lookUpVO.setDescription( desc );
		//lookUpVO.setLevel1( SvcConstants.LOOKUP_LEVEL1 );
		lookUpVO.setLevel1( tariff );
		lookUpVO.setLevel2( SvcConstants.LOOKUP_LEVEL2 );
		LookUpService lookupSvc = (LookUpService) Utils.getBean( "lookUpService" );
		updatedLookUpVO = (LookUpVO) lookupSvc.invokeMethod( "getCode", lookUpVO );
		if(!Utils.isEmpty( updatedLookUpVO )){
			return updatedLookUpVO.getCode();
		} else {
			return null;
		}
	}
	
	private boolean isPolicyFGB(String tariff)
	{
		boolean isPolicyFGB = false;
		
		if(!Utils.isEmpty(tariff))
		{
			for(int i = 0; i<FGB_TARIFF_CODE.length;i++)
			{
				if(tariff.equals(FGB_TARIFF_CODE[i]))
				{
					isPolicyFGB = true;
					break;
				}
			}
		}
		
		return isPolicyFGB;
	}
	

}
