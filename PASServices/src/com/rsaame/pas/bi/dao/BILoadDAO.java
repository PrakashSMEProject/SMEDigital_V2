/**
 * 
 */
package com.rsaame.pas.bi.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnColWorkSheetQuo;
import com.rsaame.pas.dao.model.TTrnColWorkSheetQuoId;
import com.rsaame.pas.dao.model.TTrnConsequentialLossQuo;
import com.rsaame.pas.dao.model.TTrnConsequentialLossQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.BIUWDetailsVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1019703
 *
 */
public class BILoadDAO extends BaseSectionLoadDAO implements IBISectionDAO{

	public BILoadDAO(){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

	}

	private static final short BI_CWS_ITEM_CODE_WORK_LIMIT = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_WORK_LIMIT" ) );
	private static final short BI_CWS_ITEM_CODE_GROSS_INCOME = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_GROSS_INCOME" ) );
	private static final short BI_CWS_ITEM_CODE_RENT_RECIEVABLE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_RENT_RECIEVABLE" ) );
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7 Adventnet Id:103286
	//private static final short BI_CWS_ITEM_CODE_ANNUAL_RENT = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_ANNUAL_RENT" ) );
	private static final short BI_PRM_COVER_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_COVER_CODE" ) );
	private static final int BI_UW_CODE1 = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_UW_CODE1" ) );
	private static final int BI_UW_CODE2 = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_UW_CODE2" ) );
	/* (non-Javadoc)
	 * @see com.rsaame.pas.bi.dao.IBISectionDAO#loadBISection(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO loadBISection( BaseVO baseVO ){
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.bi.dao.IBISectionDAO#saveBISection(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO saveBISection( BaseVO baseVO ){
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionLoadDAO#getRiskDetails(com.rsaame.pas.vo.bus.RiskGroup, java.lang.Long, java.lang.Long, com.rsaame.pas.vo.app.LoadExistingInputVO)
	 */
	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){
		BIVO biVO = null;
		BIUWDetailsVO uWdetailsVO = null;
		List<TTrnConsequentialLossQuo> conseqLossQuote = null;
		List<TTrnColWorkSheetQuo> colWorkSheetQuote = null;
		Double deductibles = null;
		TTrnColWorkSheetQuoId colWorkSheetQuoteId = null;
		TTrnConsequentialLossQuoId conseqLossQuoteId = null;
		double estGrossIncome;
		
		conseqLossQuote = (List<TTrnConsequentialLossQuo>)DAOUtils.getTableSnapshotQuery("T_TRN_CONSEQUENTIAL_LOSS", lei.getAppFlow(),getHibernateTemplate(),false,endId,Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()),policyId);

		/*  Populate values from TTrnConsequentialLossQuo */
		if( !Utils.isEmpty( conseqLossQuote ) )
		{
			if( !Utils.isEmpty( conseqLossQuote.get( 0 ).getColIndemnityPeriod() ) )
			{
				biVO = new BIVO();
				biVO.setIndemnityPeriod( (int) conseqLossQuote.get( 0 ).getColIndemnityPeriod() );
			}
			if( !Utils.isEmpty( conseqLossQuote.get( 0 ).getColGpSumInsured() ) )
			{
				estGrossIncome =  (double) conseqLossQuote.get( 0 ).getColGpSumInsured();
				if(estGrossIncome != 0.0)
				{
					if(!Utils.isEmpty( biVO )){
						biVO.setSumInsured( estGrossIncome );
					}	
				}
			}
			if( !Utils.isEmpty( conseqLossQuote.get( 0 ).getColEmlPerc() ) )
			{
				uWdetailsVO = new BIUWDetailsVO();
				uWdetailsVO.setEmlPrc( ( conseqLossQuote.get( 0 ).getColEmlPerc() ).doubleValue() );
			}
			if( !Utils.isEmpty( conseqLossQuote.get( 0 ).getColEmlSi() ) )
			{
				uWdetailsVO.setEmlSI( conseqLossQuote.get( 0 ).getColEmlSi().doubleValue() );
				if(!Utils.isEmpty( biVO )){
					biVO.setUwDetails( uWdetailsVO );
				}
			}
			if( !Utils.isEmpty( conseqLossQuote.get( 0 ).getId() ) )
			{
				conseqLossQuoteId = conseqLossQuote.get( 0 ).getId();
				if( !Utils.isEmpty( conseqLossQuoteId.getColPolicyId() ) )
				{
					if(!Utils.isEmpty( biVO )){
						biVO.setBasicRiskId( conseqLossQuote.get( 0 ).getColBldId() );
					}	
				}
			}
			/*if( !Utils.isEmpty( conseqLossQuote.get( 0 ).getId().getColEndtId() ) && conseqLossQuote.get( 0 ).getId().getColEndtId() == 0 )
			{
				biVO.setBasicRiskId( conseqLossQuote.get( 0 ).getColBldId() );
			}*/
		}

		/*Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );
		
		if( !Utils.isEmpty( validityStartDate ) ){

			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){
				colWorkSheetQuote = getHibernateTemplate().find(
						"from TTrnColWorkSheetQuo where id.cwsPolicyId = ? and id.cwsValidityStartDate <= ? and " + " cwsValidityExpiryDate > ? and cwsEndtId = ? ",
						policyId, validityStartDate, validityStartDate, endId);
			}
			else{
				colWorkSheetQuote = getHibernateTemplate().find(
						"from TTrnColWorkSheetQuo where id.cwsPolicyId = ? and id.cwsValidityStartDate <= ? and " + " cwsValidityExpiryDate > ? and " +
								" cwsEndtId <= ? and cwsStatus <> 4 ", policyId,validityStartDate, validityStartDate, endId );

			}
		}*/
		
		colWorkSheetQuote = (List<TTrnColWorkSheetQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_COL_WORK_SHEET_LOAD", lei.getAppFlow(),getHibernateTemplate(),false,endId,policyId);

		if( !Utils.isEmpty( colWorkSheetQuote ) )
		{
			for( TTrnColWorkSheetQuo colWorkSheetItem : colWorkSheetQuote )
			{
				colWorkSheetQuoteId = colWorkSheetItem.getId();
				if( !Utils.isEmpty( colWorkSheetItem.getCwsItemAmount() ) && !Utils.isEmpty( colWorkSheetQuoteId )
						&& BI_CWS_ITEM_CODE_RENT_RECIEVABLE == colWorkSheetQuoteId.getCwsItemCode() )
				{
					if(!Utils.isEmpty( biVO )){
						biVO.setRentRecievable( ( colWorkSheetItem.getCwsItemAmount() ).doubleValue() );
						biVO.setBiCwsRentId( colWorkSheetItem.getId().getCwsPolicyId() );	
					}
				}
				else if( !Utils.isEmpty( colWorkSheetItem.getCwsItemAmount() ) && !Utils.isEmpty( colWorkSheetQuoteId )
						&& BI_CWS_ITEM_CODE_WORK_LIMIT == colWorkSheetQuoteId.getCwsItemCode() )
				{
					if(!Utils.isEmpty( biVO )){
						biVO.setWorkingLimit( ( colWorkSheetItem.getCwsItemAmount() ).doubleValue() );
						biVO.setBiCwsAcwlId( colWorkSheetItem.getId().getCwsPolicyId() );
					}	
					
				}
				else if( !Utils.isEmpty( colWorkSheetItem.getCwsItemAmount() ) && !Utils.isEmpty( colWorkSheetQuoteId )
						&& BI_CWS_ITEM_CODE_GROSS_INCOME == colWorkSheetQuoteId.getCwsItemCode() )
				{
					if(!Utils.isEmpty( biVO )){
						biVO.setEstimatedGrossIncome(( colWorkSheetItem.getCwsItemAmount() ).doubleValue() );
						biVO.setBiCwsEGIncomeId(( colWorkSheetItem.getId().getCwsPolicyId()));
					}	
					
				}
				//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
				//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
				/*else if( !Utils.isEmpty( colWorkSheetItem.getCwsItemAmount() ) && !Utils.isEmpty( colWorkSheetQuoteId )
						&& BI_CWS_ITEM_CODE_ANNUAL_RENT == colWorkSheetQuoteId.getCwsItemCode() )
				{
					if(!Utils.isEmpty( biVO )){
						biVO.setAnnualRent( ( colWorkSheetItem.getCwsItemAmount() ).doubleValue() );
						biVO.setBiCwsAnnualRentId( colWorkSheetItem.getId().getCwsPolicyId() );	
					}
				}*/
				
			}
		}

		/* Fetching the questions */
		UWQuestionsVO uWQuestionsVO = new UWQuestionsVO();
		List<TTrnUwQuestionsQuo> questionsQuo = null;
		List<UWQuestionVO> uwQuestionList = new ArrayList<UWQuestionVO>();
		List<TTrnPremiumQuo> premiumQuoList = null;
		if( ( lei.getAppFlow() == Flow.AMEND_POL ) || ( lei.getAppFlow() == Flow.RESOLVE_REFERAL ) ){
			questionsQuo = getHibernateTemplate().find(
					"from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? " + "and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityExpiryDate = ?", Long.valueOf( policyId ),
					Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), SvcConstants.EXP_DATE );
		}
		else{
			questionsQuo = (List<TTrnUwQuestionsQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_UW_QUESTIONS", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
					Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ) );
		}

		int code = 0;
		for( TTrnUwQuestionsQuo questionsVOs : questionsQuo ){
			code = questionsVOs.getId().getUqtUwqCode();
			if( code == BI_UW_CODE1 || code == BI_UW_CODE2 ){
				UWQuestionVO uwQuestion = new UWQuestionVO();
				uwQuestion.setQId( questionsVOs.getId().getUqtUwqCode() );
				uwQuestion.setResponse( questionsVOs.getUqtUwqAnswer() );
				uwQuestionList.add( uwQuestion );
			}

		}
		uWQuestionsVO.setQuestions( uwQuestionList );
		if(!Utils.isEmpty( biVO )){
			biVO.setUwQuestions( uWQuestionsVO );
		}	

		//premiumQuoList = getHibernateTemplate().find( "from TTrnPremiumQuo prmQuo where prmQuo.prmEndtId=? and prmQuo.id.prmPolicyId=? and prmQuo.id.prmRskId = ?", endId,policyId,
				//new BigDecimal( conseqLossQuote.get( 0 ).getColBldId() ) );
		
		premiumQuoList = (List<TTrnPremiumQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_PREMIUM", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
				new BigDecimal( conseqLossQuote.get( 0 ).getColBldId() ), new BigDecimal( conseqLossQuote.get( 0 ).getColBldId() ), Integer.valueOf(BI_PRM_COVER_CODE)
						.shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( "BI_COVER_TYPE_CODE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_CST_CODE" ) ) );
		
		if( !Utils.isEmpty( premiumQuoList ) ){
			for( TTrnPremiumQuo premiumQuo : premiumQuoList )
			{	
				if( !Utils.isEmpty( premiumQuo.getPrmCompulsoryExcess() ) && biVO.getDeductible() == 0 )
				{
					deductibles = premiumQuo.getPrmCompulsoryExcess().doubleValue();
					if(!Utils.isEmpty( biVO )){
						biVO.setDeductible( deductibles.longValue() );
					}	
				}
				if( !Utils.isEmpty( premiumQuo.getPrmPremium()))
				{
					PremiumVO prmVO = new PremiumVO();
					prmVO.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
					if(!Utils.isEmpty( biVO )){
						biVO.setPremium( prmVO );
					}	
				}
			}
		}
		return biVO;
	}

}
