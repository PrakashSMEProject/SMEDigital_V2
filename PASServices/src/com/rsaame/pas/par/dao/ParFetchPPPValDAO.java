package com.rsaame.pas.par.dao;

import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseFetchPPPValDAO;
import com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;

public class ParFetchPPPValDAO extends BaseFetchPPPValDAO implements IConstructPPPRiskDetailsDAO {
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO#getRiskDetails(java.util.List, java.util.List)
	 */
	@Override
	public RiskGroupDetails getRiskDetails(
			List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList,
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {
		
		ParVO parVO = new ParVO();
		parVO.setCovers(new PropertyRisks());
		
		java.util.List<PropertyRiskDetails> propertyCoversDetails =  new com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails>(PropertyRiskDetails.class);
		PropertyRiskDetails propertyRiskDetails = null;
		VMasPasFetchBasicInfo vMasPasFetchBasicInfo=null;
		
		/* Set information related to building covered */
		for(VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList){
			propertyRiskDetails = new PropertyRiskDetails();
			
			BeanMapper.map(vMasPasFetchBasicDtls, propertyRiskDetails);
			/*
			 * In case sum insured and limit both are defined then consider limit value
			 * as sum insured. This generally is the configuration in cases where a range is defined
			 * using sum insured and limit columns. Hence using this for Additional cover case handling
			 */
			if( propertyRiskDetails.getCoverCode().intValue() != SvcConstants.APP_BASE_COVER_CODE ){
				if( !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrSumInsured() ) && !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrLimit() )
						|| !Utils.isEmpty( vMasPasFetchBasicDtls.getId().getPrLimit() ) ){
					propertyRiskDetails.setCover(  vMasPasFetchBasicDtls.getId().getPrLimit().doubleValue() );
				}
			}	
			
			propertyCoversDetails.add(propertyRiskDetails);
		}
		parVO.getCovers().setPropertyCoversDetails(propertyCoversDetails);
		
		vMasPasFetchBasicInfo = vMasPasFetchBasicInfoList.get(0);
		
		if(!Utils.isEmpty(vMasPasFetchBasicInfo.getId().getBldCoveredFlag())){
			parVO.setBuilCovered(Integer.valueOf(vMasPasFetchBasicInfo.getId().getBldCoveredFlag()));
		}
		
		/* Set information related to UWDetails */
		parVO.setUwDetails(new PARUWDetailsVO());
		((PARUWDetailsVO)parVO.getUwDetails()).setHazardLevel(vMasPasFetchBasicInfo.getId().getHazardLevel());
		((PARUWDetailsVO)parVO.getUwDetails()).setCategoryRI(vMasPasFetchBasicInfo.getId().getRiCategory());
		
		
		return parVO;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.IConstructPPPRiskDetailsDAO#getRiskGroup(java.util.List)
	 */
	@Override
	public RiskGroup getRiskGroup(
			List<VMasPasFetchBasicInfo> vMasPasFetchBasicInfoList) {

		LocationVO locationVO = new LocationVO(); 
		
		/*
		for(VMasPasFetchBasicInfo vMasPasFetchBasicInfo : vMasPasFetchBasicInfoList){
			locationVO.setOccTradeGroup(vMasPasFetchBasicInfo.getId().getTradeGrp());
		}*/
	
		return locationVO;
	}



	
}
