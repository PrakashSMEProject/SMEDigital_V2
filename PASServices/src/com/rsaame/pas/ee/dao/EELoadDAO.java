package com.rsaame.pas.ee.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.EEUWDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * Loads the EE Section
 * @author m1014438
 *
 */
public class EELoadDAO extends BaseSectionLoadDAO{
	private static final Logger logger = Logger.getLogger( EELoadDAO.class );
	@SuppressWarnings( "unchecked" )
	@Override
	/**
	 * Reads required data from DB , populates  EEVO and returns back.
	 */
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){
		logger.debug( "Inside getRiskDetails EELoadDao" );
		EEVO eeVO = null;
		List<EquipmentVO> equipmentDtls = null;
		List<TTrnContentQuo> tTrnContentQuoList = null;
		List<TTrnPremiumQuo> premiumQuoList = null;
		Double eeSI = new Double( 0 );
		Double totalLocPrm = 0.0;
		
		/* 
		 * Changed to pick the risk details based on validity start date - Fix identified for defect
		 * defect nummber 355 - Phase 2 RADAR defect number
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );

		if( lei.getAppFlow().equals( Flow.VIEW_POL ) || lei.getAppFlow().equals( Flow.VIEW_QUO ) )
		{
			/*if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo cnt where (cntEndtId, id.cntContentId, cntPolicyId, cntBasicRiskId,cntRiskDtl) in "
								+ "( select max ( cntEndtId ),id.cntContentId , cntPolicyId, cntBasicRiskId,cntRiskDtl from TTrnContentQuo where cntEndtId <= ? and cntPolicyId= ? "
								+ "and cntBasicRiskId= ? and cntRiskDtl=? group by cntPolicyId,  cntBasicRiskId,cnt_risk_dtl ,id.cntContentId )", endId,
						policyId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_DETAIL ) ) );
			}
			else
			{
				tTrnContentQuoList = getHibernateTemplate().find(
					"from TTrnContentQuo cnt where (cntEndtId, id.cntContentId, cntPolicyId, cntBasicRiskId,cntRiskDtl) in "
							+ "( select max ( cntEndtId ),id.cntContentId , cntPolicyId, cntBasicRiskId,cntRiskDtl from TTrnContentQuo where cntEndtId <= ? and cntPolicyId= ? "
							+ "and cntBasicRiskId= ? and cntRiskDtl=? group by cntPolicyId,  cntBasicRiskId,cnt_risk_dtl ,id.cntContentId )  and cnt.cntStatus <> 4", endId,
					policyId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_DETAIL ) ) );
			}

		}*/
		if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
		{
			tTrnContentQuoList = getHibernateTemplate().find(
					"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and cntEndtId = ? and cntBasicRiskId = ? and cntRiskDtl=?",
					policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_DETAIL ) ) );
		}
		else
		{
			tTrnContentQuoList = getHibernateTemplate().find(
					"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and " +
							" cntEndtId <= ? and cntStatus <> 4 and cntBasicRiskId = ? and cntRiskDtl=?", policyId,
					validityStartDate, validityStartDate, endId ,Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_DETAIL ) ));
		}
	}else{

			tTrnContentQuoList = (List<TTrnContentQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_CONTENT", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
					Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_DETAIL ) ) );
		}
		if( !Utils.isEmpty( tTrnContentQuoList ) ){

			eeVO = new EEVO();
			equipmentDtls = new com.mindtree.ruc.cmn.utils.List<EquipmentVO>( EquipmentVO.class );

			for( TTrnContentQuo tTrnContentQuo : tTrnContentQuoList ){

				EquipmentVO equipmentVO = new EquipmentVO();
				SumInsuredVO sumInsuredVO = new SumInsuredVO();
				BeanMapper.map( tTrnContentQuo, equipmentVO );
				equipmentVO.setContentId( tTrnContentQuo.getId().getCntContentId() );

				premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_PREMIUM", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
						new BigDecimal( tTrnContentQuo.getId().getCntContentId() ), new BigDecimal( tTrnContentQuo.getCntBasicRiskId() ),
						Short.valueOf( Utils.getSingleValueAppConfig( "EE_COV_CODE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_TYPE" ) ),
						Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_SUB_TYPE" ) ) );

				if( !Utils.isEmpty( premiumQuoList ) ){
					for( TTrnPremiumQuo premiumQuo : premiumQuoList ){
						if( !Utils.isEmpty( premiumQuo.getPrmCompulsoryExcess() ) ){
							sumInsuredVO.setDeductible( premiumQuo.getPrmCompulsoryExcess().doubleValue() );
						}
						if( !Utils.isEmpty( premiumQuo.getPrmSumInsured() ) ){
							sumInsuredVO.setSumInsured( ( premiumQuo.getPrmSumInsured().doubleValue() ) );
							eeSI = eeSI + premiumQuo.getPrmSumInsured().doubleValue();
						}

						if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
							PremiumVO prmVO = new PremiumVO();
							// Renewals will call the rating again to set the premium
							if(!lei.getAppFlow().equals( Flow.RENEWAL)){
								prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
							}
							totalLocPrm += premiumQuo.getPrmPremium().doubleValue();
							equipmentVO.setPremium( prmVO );
						}
					}

				}

				equipmentVO.setSumInsuredDetails( sumInsuredVO );
				equipmentDtls.add( equipmentVO );

			}

			/** Passing back the summed up location premium through machineDetails premium field */
			PremiumVO prmVO = new PremiumVO();
			//Added for Bahrain 3 decimal - Starts
			 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
					prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( totalLocPrm ) ) );
			 }else {
						 prmVO.setPremiumAmt( Double.valueOf( decForm.format( totalLocPrm ) ) ); 
			 }		 
			//Added for Bahrain 3 decimal - Ends
			eeVO.setPremium( prmVO );

			TTrnContentQuo tTrnContentQuo = tTrnContentQuoList.get( 0 );
			EEUWDetailsVO eeuwDetailsVO = new EEUWDetailsVO();
			BeanMapper.map( tTrnContentQuo, eeuwDetailsVO );
			eeVO.setUwDetails( eeuwDetailsVO );
			eeVO.setEquipmentDtls( equipmentDtls );
			eeVO.setSumInsured( eeSI );

		}
		logger.debug( "EELoadDAO :: EEVO --"+eeVO );
		return eeVO;
	}

}
