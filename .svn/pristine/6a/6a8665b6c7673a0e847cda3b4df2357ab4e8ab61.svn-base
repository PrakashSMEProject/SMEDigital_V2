package com.rsaame.pas.dos.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockUWDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class DeteriorationOfStockLoadDAO extends BaseSectionLoadDAO implements IDeteriorationOfStockDao{

	@Override
	public BaseVO loadDeteriorationOfStockSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveDeteriorationOfStockSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){

		DeteriorationOfStockVO dosVo = new DeteriorationOfStockVO();
		List<DeteriorationOfStockDetailsVO> stockDetailsVOs = new ArrayList<DeteriorationOfStockDetailsVO>();
		Double totalDOSSi = new Double( 0 );
		List<TTrnContentQuo> tTrnContentQuoList = null;
		List<TTrnPremiumQuo> premiumQuoList = null;
		Double deductibles = null;
		Double totalLocPrm = 0.0;
		
		/* 
		 * Changed to pick the risk details based on validity start date - Fix identified for defect
		 * defect nummber 355 - Phase 2 RADAR defect number
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );
		if( lei.getAppFlow().equals( Flow.VIEW_POL ) || lei.getAppFlow().equals( Flow.VIEW_QUO ) ){

			/*if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo cnt where (cntEndtId, id.cntContentId, cntPolicyId, cntBasicRiskId,cntRiskDtl) in "
								+ "( select max ( cntEndtId ),id.cntContentId , cntPolicyId, cntBasicRiskId,cntRiskDtl from TTrnContentQuo where cntEndtId <= ? and cntPolicyId= ? "
								+ "and cntBasicRiskId= ? and cntRiskDtl=? group by cntPolicyId,  cntBasicRiskId,cnt_risk_dtl ,id.cntContentId ) ", endId,
						policyId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) ) );
			}
			else
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo cnt where (cntEndtId, id.cntContentId, cntPolicyId, cntBasicRiskId,cntRiskDtl) in "
								+ "( select max ( cntEndtId ),id.cntContentId , cntPolicyId, cntBasicRiskId,cntRiskDtl from TTrnContentQuo where cntEndtId <= ? and cntPolicyId= ? "
								+ "and cntBasicRiskId= ? and cntRiskDtl=? group by cntPolicyId,  cntBasicRiskId,cnt_risk_dtl ,id.cntContentId )  and cnt.cntStatus <> 4", endId,
						policyId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) ) );
			}*/
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and cntEndtId = ? and cntBasicRiskId = ? and cntRiskDtl=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) ) );
			}
			else
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and " +
								" cntEndtId <= ? and cntStatus <> 4 and cntBasicRiskId = ? and cntRiskDtl=?", policyId,
						validityStartDate, validityStartDate, endId ,Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) ));
			}
		}
		else{
			tTrnContentQuoList = (List<TTrnContentQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_CONTENT", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
					Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) ) );
		}

		if( !Utils.isEmpty( tTrnContentQuoList ) ){
			for( TTrnContentQuo tTrnContentQuo : tTrnContentQuoList ){

				DeteriorationOfStockDetailsVO stockDetails = new DeteriorationOfStockDetailsVO();
				SumInsuredVO insuredVO = new SumInsuredVO();
				stockDetails.setSumInsuredDetails( insuredVO );
				stockDetails.setDeteriorationOfStockType( tTrnContentQuo.getCntRiskSubDtl().toString() );
				/*stockDetails.setDeteriorationOfStockDesc( tTrnContentQuo.getCntDescription() );
				stockDetails.setDeteriorationOfStockQuantity( Integer.valueOf( tTrnContentQuo.getCntQty().intValue()) );*/
				stockDetails.setContentId( tTrnContentQuo.getId().getCntContentId() );
			
				/*
				 * Check if the riskDetails obtained is null in order to throw BusinessException as we cannot
				 * proceed with the flow but however its not a ideal scenario
				 */
				if( Utils.isEmpty( stockDetails ) ){
					throw new BusinessException( "mb.contentCategoryIsNull", null, "Machine Details object with same content category " + "not found for [" + policyId
							+ " ] policy id and [" + endId + "] endt id" );
				}

					if( !Utils.isEmpty( tTrnContentQuo.getCntSumInsured() ) ){

					stockDetails.getSumInsuredDetails().setSumInsured( tTrnContentQuo.getCntSumInsured().doubleValue() );
					totalDOSSi = totalDOSSi + tTrnContentQuo.getCntSumInsured().doubleValue();
				}
				premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
						new BigDecimal( tTrnContentQuo.getId().getCntContentId() ), new BigDecimal( tTrnContentQuo.getCntBasicRiskId() ),
						Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_TYPE" ) ),
						Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_SUB_TYPE" ) ) );

				if( !Utils.isEmpty( premiumQuoList ) ){
					for( TTrnPremiumQuo premiumQuo : premiumQuoList ){
						if( !Utils.isEmpty( premiumQuo.getPrmCompulsoryExcess() ) ){
							deductibles = premiumQuo.getPrmCompulsoryExcess().doubleValue();
							stockDetails.getSumInsuredDetails().setDeductible( deductibles );
						}

						/** assigning individual content premium and adding up the same to obtain location level premium */
						if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
							PremiumVO prmVO = new PremiumVO();
							prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
							totalLocPrm += premiumQuo.getPrmPremium().doubleValue();
							stockDetails.setPremium( prmVO );
						}
					}
				}
				stockDetailsVOs.add( stockDetails );
			}
			dosVo.setDeteriorationOfStockDetails( stockDetailsVOs );
			dosVo.setSumInsured( totalDOSSi );
		}
			/* Fetching the questions and setting it to MBVO */
			UWQuestionsVO uWQuestionsVO = new UWQuestionsVO();
			List<TTrnUwQuestionsQuo> questionsQuo = null;
			List<UWQuestionVO> uwQuestionList = new ArrayList<UWQuestionVO>();
			if( ( lei.getAppFlow() == Flow.AMEND_POL ) || ( lei.getAppFlow() == Flow.RESOLVE_REFERAL ) ){
				questionsQuo = getHibernateTemplate().find(
						"from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? " + "and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityExpiryDate = ?",
						Long.valueOf( policyId ), Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), SvcConstants.EXP_DATE );
			}
			else{
				questionsQuo = (List<TTrnUwQuestionsQuo>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
						Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ) );
			}

			questionsQuo = DAOUtils.getQuestionListForCurrentSection( questionsQuo, SvcConstants.SECTION_ID_DOS, lei.getTariffCode() );
			for( TTrnUwQuestionsQuo questionsVOs : questionsQuo ){
				UWQuestionVO uwQuestion = new UWQuestionVO();
				uwQuestion.setQId( questionsVOs.getId().getUqtUwqCode() );
				uwQuestion.setResponse( questionsVOs.getUqtUwqAnswer() );
				uwQuestionList.add( uwQuestion );
			}
			uWQuestionsVO.setQuestions( uwQuestionList );
			dosVo.setUwQuestions( uWQuestionsVO );
			dosVo.setPolicyId( policyId );

			/** Passing back the summed up location premium through machineDetails premium field */
			PremiumVO prmVO = new PremiumVO();
			//Added for Bahrain 3 decimal - Starts
			 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
					prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( totalLocPrm ) ) );
			 }else {
						 prmVO.setPremiumAmt( Double.valueOf( decForm.format( totalLocPrm ) ) ); 
			 }		 
			//Added for Bahrain 3 decimal - Ends
			dosVo.setPremium( prmVO );
			
			/*Setting Underwriting details to MBVO*/
			TTrnContentQuo tTrnContentQuo = tTrnContentQuoList.get( 0 );
			DeteriorationOfStockUWDetailsVO dosuwDetailsVO = new DeteriorationOfStockUWDetailsVO();
			if( !Utils.isEmpty( tTrnContentQuo.getCntMplFirePerc() ) ) dosuwDetailsVO.setEmlPercentage( tTrnContentQuo.getCntMplFirePerc().doubleValue() );

			if( !Utils.isEmpty( tTrnContentQuo.getCntMplFire() ) ) dosuwDetailsVO.setEmlSI( tTrnContentQuo.getCntMplFire().doubleValue() );
			dosVo.setDeteriorationOfStockUWDetails( dosuwDetailsVO );
			
		return dosVo;
	}

}
