package com.rsaame.pas.motor.dao;

import java.math.BigDecimal;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnColWorkSheetQuo;
import com.rsaame.pas.dao.model.TTrnColWorkSheetQuoId;
import com.rsaame.pas.dao.model.TTrnConsequentialLossQuoId;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnVehicleQuo;
import com.rsaame.pas.dao.model.TTrnVehicleQuoId;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.AdditionalVehicleDetailsVO;
import com.rsaame.pas.vo.bus.CovrageDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MotorFleetVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.VehicleDetailsVO;

/**
 * 
 * @author m1019703
 *
 */

public class MotorFleetLoadDAO extends BaseSectionLoadDAO implements IMotorFleetSectionDAO{

	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei )
	{
		MotorFleetVO motorFleetVo = null;
		
		VehicleDetailsVO vehicleVO = null;
		List<VehicleDetailsVO> vehiclesList = null;
		CovrageDetailsVO coverageVo = null;
		List<CovrageDetailsVO> coverageList = null;
		AdditionalVehicleDetailsVO addVehDetailsVo = null;
		List<AdditionalVehicleDetailsVO> addVehDetailList = null;
		
		
		
		Double deductibles = null;
		Double totalLocPrm = 0.0;
		List<TTrnPremiumQuo> premiumQuoList = null;
		
		
		TTrnVehicleQuo vehicleQuote = null;
		TTrnVehicleQuoId vehicleQuoteId = null;
		List<TTrnVehicleQuo> vehicleQuoteList = null;
		
		
		
		vehicleQuoteList = (List<TTrnVehicleQuo>)DAOUtils.getTableSnapshotQuery("t_trn_vehicle_quo", lei.getAppFlow(),
							getHibernateTemplate(),false,endId,Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()),policyId);
		
		if( !Utils.isEmpty( vehicleQuoteList ) )
		{
			motorFleetVo = new MotorFleetVO();
			vehiclesList = new com.mindtree.ruc.cmn.utils.List<VehicleDetailsVO>( VehicleDetailsVO.class );
			coverageList = new com.mindtree.ruc.cmn.utils.List<CovrageDetailsVO>( CovrageDetailsVO.class );
			addVehDetailList = new com.mindtree.ruc.cmn.utils.List<AdditionalVehicleDetailsVO>( AdditionalVehicleDetailsVO.class );
			
			for( TTrnVehicleQuo tTrnVehicleQuo : vehicleQuoteList )
			{
				vehicleVO = new VehicleDetailsVO();
				addVehDetailsVo = new AdditionalVehicleDetailsVO();
				coverageVo = new CovrageDetailsVO();
				
				BeanMapper.map( tTrnVehicleQuo, vehicleVO );
				BeanMapper.map( tTrnVehicleQuo, addVehDetailsVo );
				BeanMapper.map( tTrnVehicleQuo, coverageVo );
				
				premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_PREMIUM", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
						new BigDecimal( vehicleQuote.getId().getVehId() ), new BigDecimal( vehicleQuote.getVehBldId() ),
						Short.valueOf( Utils.getSingleValueAppConfig( "EE_COV_CODE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_TYPE" ) ),
						Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_SUB_TYPE" ) ) );
				
				if( !Utils.isEmpty( premiumQuoList ) )
				{
					for( TTrnPremiumQuo premiumQuo : premiumQuoList )
					{
						//SONAR FIX - Empty If Stmt 
						/*if( !Utils.isEmpty( premiumQuo.getPrmSumInsured() ) )
						{
							//vehicleVO.setVehicleIEV( ( premiumQuo.getPrmSumInsured().doubleValue()) );
						}*/
						if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) )
						{
							PremiumVO prmVO = new PremiumVO();
							if(!lei.getAppFlow().equals( Flow.RENEWAL))
							{
								prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
							}
							totalLocPrm += premiumQuo.getPrmPremium().doubleValue();
						}
					}

				}
				vehiclesList.add( vehicleVO );
				coverageList.add( coverageVo );
				addVehDetailList.add( addVehDetailsVo );
			}
			PremiumVO prmVO = new PremiumVO();
			prmVO.setPremiumAmt( Double.valueOf( decForm.format( totalLocPrm ) ) );
			motorFleetVo.setVehicleDetails( vehiclesList );
			motorFleetVo.setCovrageDetails( coverageList );
			motorFleetVo.setAdditionalVehicleDetails( addVehDetailList );
			motorFleetVo.setPremium( prmVO );
		}
		return null;
	}

	@Override
	public BaseVO loadMotorSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveMotorSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

}
