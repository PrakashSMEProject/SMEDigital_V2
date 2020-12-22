package com.rsaame.pas.mb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBUWDetailsVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class MBLoadDAO extends BaseSectionLoadDAO{

	private static final String MB_RISK_DTL = "MB_RISK_DTL";
	private static final String MB_COVER_SUB_TYPE = "MB_COVER_SUB_TYPE";
	private static final String MB_COVER_TYPE = "MB_COVER_TYPE";
	private static final String T_TRN_CONTENT = "T_TRN_CONTENT";

	@SuppressWarnings( "unchecked" )
	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){

		MBVO mbvo = new MBVO();
		List<MachineDetailsVO> machineDetailsVOs = new ArrayList<MachineDetailsVO>();
		Double totalMBSi = new Double( 0 );
		List<TTrnContentQuo> tTrnContentQuoList = null;
		List<TTrnPremiumQuo> premiumQuoList = null;
		Double deductibles = null;
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
						policyId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( MB_RISK_DTL ) ) );
			}
			else
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo cnt where (cntEndtId, id.cntContentId, cntPolicyId, cntBasicRiskId,cntRiskDtl) in "
								+ "( select max ( cntEndtId ),id.cntContentId , cntPolicyId, cntBasicRiskId,cntRiskDtl from TTrnContentQuo where cntEndtId <= ? and cntPolicyId= ? "
								+ "and cntBasicRiskId= ? and cntRiskDtl=? group by cntPolicyId,  cntBasicRiskId,cnt_risk_dtl ,id.cntContentId )  and cnt.cntStatus <> 4", endId,
						policyId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( MB_RISK_DTL ) ) );
			}*/
			
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and cntEndtId = ? and cntBasicRiskId = ? and cntRiskDtl=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( MB_RISK_DTL ) ) );
			}
			else
			{
				tTrnContentQuoList = getHibernateTemplate().find(
						"from TTrnContentQuo where cntPolicyId = ? and id.cntValidityStartDate <= ? and " + " cntValidityExpiryDate > ? and " +
								" cntEndtId <= ? and cntStatus <> 4 and cntBasicRiskId = ? and cntRiskDtl=?", policyId,
						validityStartDate, validityStartDate, endId ,Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( Utils.getSingleValueAppConfig( MB_RISK_DTL ) ));
			}
		}
		else{
			tTrnContentQuoList = (List<TTrnContentQuo>) DAOUtils.getTableSnapshotQuery( T_TRN_CONTENT, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
					Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( MB_RISK_DTL ) ) );
		}

		if( !Utils.isEmpty( tTrnContentQuoList ) ){
			for( TTrnContentQuo tTrnContentQuo : tTrnContentQuoList ){

				MachineDetailsVO machineDetails = new MachineDetailsVO();
				SumInsuredVO insuredVO = new SumInsuredVO();
				machineDetails.setSumInsuredVO( insuredVO );
				Contents contents = new Contents();
				machineDetails.setContents( contents );

				machineDetails.getContents().setRiskId( tTrnContentQuo.getCntBasicRiskId() );
				machineDetails.getContents().setRiskCode( tTrnContentQuo.getCntRskCode() );
				machineDetails.getContents().setRiskType( tTrnContentQuo.getCntCategory() );
				machineDetails.getContents().setBasicRiskCode( tTrnContentQuo.getCntBasicRskCode() );
				machineDetails.getContents().setRiskDtl( tTrnContentQuo.getCntRiskDtl() );

				/*
				 * Check if the riskDetails obtained is null in order to throw BusinessException as we cannot
				 * proceed with the flow but however its not a ideal scenario
				 */
				if( Utils.isEmpty( machineDetails ) ){
					throw new BusinessException( "mb.contentCategoryIsNull", null, "Machine Details object with same content category " + "not found for [" + policyId
							+ " ] policy id and [" + endId + "] endt id" );
				}

				machineDetails.setMachineryType( tTrnContentQuo.getCntRiskSubDtl() );
				//machineDetails.setYearOfMake( tTrnContentQuo.getCntYearOfMan().intValue() );

				if( !Utils.isEmpty( tTrnContentQuo.getCntSumInsured() ) ){

					machineDetails.getSumInsuredVO().setSumInsured( tTrnContentQuo.getCntSumInsured().doubleValue() );
					totalMBSi = totalMBSi + tTrnContentQuo.getCntSumInsured().doubleValue();
				}
				if( !Utils.isEmpty( tTrnContentQuo.getCntDescription() ) ){
					machineDetails.setMachineDescription( tTrnContentQuo.getCntDescription() );
				}
				if( !Utils.isEmpty( tTrnContentQuo.getId() ) ){

					machineDetails.getContents().setSetValidityStartDate( tTrnContentQuo.getId().getCntValidityStartDate() );
					machineDetails.getContents().setCoverId( tTrnContentQuo.getId().getCntContentId() );
				}

				machineDetails.getContents().setRiskType( tTrnContentQuo.getCntCategory() );

				/*Setting Underwriting details to MBVO*/
				MBUWDetailsVO mbuwDetailsVO = new MBUWDetailsVO();
				if( !Utils.isEmpty( tTrnContentQuo.getCntMplFirePerc() ) ) mbuwDetailsVO.setEmlPrc( tTrnContentQuo.getCntMplFirePerc().doubleValue() );

				if( !Utils.isEmpty( tTrnContentQuo.getCntMplFire() ) ) mbuwDetailsVO.setEmlSI( tTrnContentQuo.getCntMplFire().doubleValue() );
				mbvo.setUwDetails( mbuwDetailsVO );

				premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
						new BigDecimal( tTrnContentQuo.getId().getCntContentId() ), new BigDecimal( tTrnContentQuo.getCntBasicRiskId() ),
						Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_TYPE ) ),
						Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_SUB_TYPE ) ) );

				if( !Utils.isEmpty( premiumQuoList ) ){
					for( TTrnPremiumQuo premiumQuo : premiumQuoList ){
						if( !Utils.isEmpty( premiumQuo.getPrmCompulsoryExcess() ) ){
							deductibles = premiumQuo.getPrmCompulsoryExcess().doubleValue();
							machineDetails.getSumInsuredVO().setDeductible( deductibles );
						}

						/** assigning individual content premium and adding up the same to obtain location level premium */
						if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
							PremiumVO prmVO = new PremiumVO();
							prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
							totalLocPrm += premiumQuo.getPrmPremium().doubleValue();
							if(!lei.getAppFlow().equals( Flow.RENEWAL)){
								machineDetails.setPremium( prmVO );
							}
						}
						// Assuming premiumQuoList will have single record
						if(lei.getAppFlow().equals( Flow.RENEWAL)){
							machineDetails.getContents().setCoverCode( Integer.valueOf( Short.toString(premiumQuo.getId().getPrmCovCode())) );
						}
					}
				}
				machineDetailsVOs.add( machineDetails );
			}
			mbvo.setMachineryDetails( machineDetailsVOs );
			mbvo.setSumInsured( totalMBSi );

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

			questionsQuo = DAOUtils.getQuestionListForCurrentSection( questionsQuo, SvcConstants.SECTION_ID_MB, lei.getTariffCode() );
			for( TTrnUwQuestionsQuo questionsVOs : questionsQuo ){
				UWQuestionVO uwQuestion = new UWQuestionVO();
				uwQuestion.setQId( questionsVOs.getId().getUqtUwqCode() );
				uwQuestion.setResponse( questionsVOs.getUqtUwqAnswer() );
				uwQuestionList.add( uwQuestion );
			}
			uWQuestionsVO.setQuestions( uwQuestionList );
			mbvo.setUwQuestions( uWQuestionsVO );
			mbvo.setPolicyId( policyId );

			/** Passing back the summed up location premium through machineDetails premium field */
			PremiumVO prmVO = new PremiumVO();
			 //Added for Bahrain 3 decimal - Starts
			 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
					prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( totalLocPrm ) ) );
			 }else {
						 prmVO.setPremiumAmt( Double.valueOf( decForm.format( totalLocPrm ) ) ); 
			 }		 
			//Added for Bahrain 3 decimal - Ends
			mbvo.setPremium( prmVO );

		}

		return mbvo;
	}

}
