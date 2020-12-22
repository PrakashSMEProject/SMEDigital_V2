package com.rsaame.pas.pl.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PLUWDetails;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class PublicLiabilityLoadDAO extends BaseSectionLoadDAO{


	@Override
	public RiskGroupDetails getRiskDetails(RiskGroup riskGroup,
			Long policyId, Long endId,LoadExistingInputVO lei) {

		List<TTrnPremiumQuo> premiumQuo = null;
		List<TTrnUwQuestionsQuo> questionsQuo = null;
		PublicLiabilityVO publicLiabilityVO = null;
		UWQuestionsVO uWQuestionsVO = new UWQuestionsVO();
		List<UWQuestionVO> uwquestionList = new ArrayList<UWQuestionVO>();
		List<TTrnWctplPremiseQuo> tTrnWctplPremiseQuo = null;
		
		/** SK : Changes */
		/** Query with wbd_id or wbd_bld_id condition will fetch the result required to proceed further */
		
		/* TODO : Change it to SnapShotQuery till then temporary fix is added */
		//tTrnWctplPremiseQuo = getHibernateTemplate().find("from TTrnWctplPremiseQuo tWpuo where tWpuo.wbdPolicyId = ? and tWpuo.id.wbdId = ? or  tWpuo.wbdBldId = ? and tWpuo.wbdEndtId = ?",Long.valueOf(policyId),Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()), Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()), endId);
		
		//tTrnWctplPremiseQuo = getHibernateTemplate().find("from TTrnWctplPremiseQuo tWpuo where tWpuo.wbdPolicyId = ? and (tWpuo.id.wbdId = ? or  tWpuo.wbdBldId = ?) and tWpuo.wbdValidityExpiryDate = ?",Long.valueOf(policyId),Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()), Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()), SvcConstants.EXP_DATE);

		
		
		
		/* 
		 * Premise record is fetched based on validity start date and bld id. 
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(),  endId,lei.getPolLinkingId(),lei.isQuote() );
		
		tTrnWctplPremiseQuo = getEndtStatePremise(validityStartDate,policyId,endId,riskGroup,lei);
		

		if( Utils.isEmpty( tTrnWctplPremiseQuo ) ){
			/** SK : Changes */
			/** a. Changes in the above query to premise table is sufficient to get the record 
			 *  b. System Exception is being thrown in case data retrieval results in premise list being null */
			throw new SystemException( "", null, "Premise table data retrieval failed for [" + riskGroup.getRiskGroupId() + "] risk group id " );
		}
		
		publicLiabilityVO = new PublicLiabilityVO();
		publicLiabilityVO.setSumInsuredDets( new SumInsuredVO() );

		if( !Utils.isEmpty( tTrnWctplPremiseQuo.get( 0 ).getWbdSumInsured() ) ){
			publicLiabilityVO.getSumInsuredDets().setSumInsured( tTrnWctplPremiseQuo.get( 0 ).getWbdSumInsured().doubleValue() );
			//To avoid SI is getting displayed as 0 in Premium page
			publicLiabilityVO.setSumInsured( tTrnWctplPremiseQuo.get( 0 ).getWbdSumInsured().doubleValue() );
		}
		if( !Utils.isEmpty( tTrnWctplPremiseQuo.get( 0 ).getWbdAName() ) ){
			publicLiabilityVO.setSumInsuredBasis( Integer.valueOf( tTrnWctplPremiseQuo.get( 0 ).getWbdAName() ) );
		}
		if( !Utils.isEmpty( tTrnWctplPremiseQuo.get( 0 ).getWbdIndemnityLimitAmt() ) ){
			publicLiabilityVO.setIndemnityAmtLimit( tTrnWctplPremiseQuo.get( 0 ).getWbdIndemnityLimitAmt() );
		}

		/** SK : Changes */
		/** Data Fix : Usage of riskGroup.getRiskGroupId() to get data from premium table has been
		 *  changed to usage of tTrnWctplPremiseQuo.getWbdId  as wbd_id is inserted as prmrskid and
		 *  prmbasicrskid */

		List<TTrnPremiumQuo> premiumQuoList=null;
		/* Fetch premium to be set to publicLiabilityVO.getSumInsuredDets().getDeductible() */
		/*premiumQuo = getHibernateTemplate().find( "from TTrnPremiumQuo tPquo where tPquo.id.prmPolicyId = ? and tPquo.id.prmRskId =? and tPquo.prmEndtId = ?",
				Long.valueOf( policyId ), new BigDecimal( tTrnWctplPremiseQuo.get( 0 ).getId().getWbdId() ), endId );*/

		premiumQuo = (List<TTrnPremiumQuo>)DAOUtils.getTableSnapshotQuery( "T_TRN_PREMIUM", lei.getAppFlow(), getHibernateTemplate(), false, endId, policyId,
				new BigDecimal( tTrnWctplPremiseQuo.get( 0 ).getId().getWbdId() ), new BigDecimal( tTrnWctplPremiseQuo.get( 0 ).getId().getWbdId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE )
						.shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_SUB_TYPE" ) ) );
		/** SK : Changes */
		/** Throwing system exception in case data obtained from premium table is null */
		if( Utils.isEmpty( premiumQuo ) ){
			throw new SystemException( Utils.getAppErrorMessage( "cmn.systemError" ), null, "Premium record obtained is null for [ "
					+ tTrnWctplPremiseQuo.get( 0 ).getId().getWbdId() + " ] premise id" );
		}
		
		if( !Utils.isEmpty( premiumQuo.get( 0 ).getPrmCompulsoryExcess() ) ){
			publicLiabilityVO.getSumInsuredDets().setDeductible( premiumQuo.get( 0 ).getPrmCompulsoryExcess().doubleValue() );
		}

		/** SK : Changes */
		/** Setting the location level premium to publicliabilityvo */
		if( !Utils.isEmpty( premiumQuo.get( 0 ).getPrmPremium() ) ){
			PremiumVO prmVO = new PremiumVO();
			 //Added for Bahrain 3 decimal - Starts
			 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
				 prmVO.setPremiumAmt( Double.valueOf( decFormBahrain.format( premiumQuo.get( 0 ).getPrmPremium().doubleValue() ) ) );
			 }
			 else {
				 prmVO.setPremiumAmt( Double.valueOf( decForm.format( premiumQuo.get( 0 ).getPrmPremium().doubleValue() ) ) );
			 }
			 //Added for Bahrain 3 decimal - Ends
			publicLiabilityVO.setPremium( prmVO );
		}

		/** SK : Changes */
		/** Retrieve saved underwriting question responses */
		/*if((lei.getAppFlow()==Flow.AMEND_POL) || (lei.getAppFlow()==Flow.RESOLVE_REFERAL))
		{
			questionsQuo = getHibernateTemplate().find("from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? " +
					"and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityExpiryDate = ?",Long.valueOf(policyId),Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()),SvcConstants.EXP_DATE);
		}
		else
		{
			questionsQuo = (List<TTrnUwQuestionsQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_UW_QUESTIONS", lei.getAppFlow(),getHibernateTemplate(),false,endId,policyId,Long.valueOf(((LocationVO)riskGroup).getRiskGroupId()));
		}*/
		
		/** 
		 * Changed the fetching logic. Using validity start date and expiry date for fetching the valid record.
		 * 
		 */

		questionsQuo = DAOUtils.getEndtStateUWQ(getHibernateTemplate(),validityStartDate,policyId,endId,riskGroup,lei);
		
		
		/** SK : Changes */
		/** Throw a SystemException in case questions/Quo obtained is null */
	/*	if( Utils.isEmpty( questionsQuo ) ){ 
			throw new SystemException( Utils.getAppErrorMessage( "cmn.systemError" ), null, "Underwriting question responses record obtained is null for [ "
					+ riskGroup.getRiskGroupId() + " ] risk group id" );
		}
	*/
			for( TTrnUwQuestionsQuo questionsVOs : questionsQuo ){
				UWQuestionVO uwquestion = new UWQuestionVO();
				uwquestion.setQId( questionsVOs.getId().getUqtUwqCode() );
				uwquestion.setResponse( questionsVOs.getUqtUwqAnswer() );
				uwquestionList.add( uwquestion );
			}
			uWQuestionsVO.setQuestions( uwquestionList );
			publicLiabilityVO.setUwQuestions( uWQuestionsVO );
		
		/** SK : Changes */
		/** Commenting below premiseQuo table query as it seems unnecessary to fetch data from the same table twice */

		/**
		premiseQuoList = getHibernateTemplate().find( "from TTrnWctplPremiseQuo tQuo where " + "tQuo.wbdPolicyId = ? and tQuo.wbdBldId =? and tQuo.wbdEndtId = ?",
				Long.valueOf( policyId ), Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), endId );
		*/
		/**
		if( !Utils.isEmpty( premiseQuoList ) ){

			PLUWDetails plUWDetails = new PLUWDetails();
			TTrnWctplPremiseQuo premiseQuo = premiseQuoList.get( 0 );

			publicLiabilityVO = BeanMapper.map( premiseQuo, publicLiabilityVO );
			plUWDetails = BeanMapper.map( premiseQuo, plUWDetails );

			if( !Utils.isEmpty( publicLiabilityVO ) ){
				publicLiabilityVO.setUwDetails( plUWDetails );
			}
		}**/
		PLUWDetails plUWDetails = new PLUWDetails();
		TTrnWctplPremiseQuo premiseQuo = tTrnWctplPremiseQuo.get( 0 );
		/** Map premise Details to PublicLiabilityVO */
		publicLiabilityVO = BeanMapper.map( premiseQuo, publicLiabilityVO );
		/** Map premise Details to PLUWDetails */
		plUWDetails = BeanMapper.map( premiseQuo, plUWDetails );
		/** Update PublicLiabilityVO with the UWDetails */
		publicLiabilityVO.setUwDetails( plUWDetails );
		return publicLiabilityVO;
    }

	private List<TTrnWctplPremiseQuo> getEndtStatePremise( Date validityStartDate, Long policyId, Long endId, RiskGroup riskGroup, LoadExistingInputVO lei ){

		List<TTrnWctplPremiseQuo> tTrnWctplPremiseQuo = null;
		
		if( !Utils.isEmpty( validityStartDate ) ){
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) ){

				tTrnWctplPremiseQuo = getHibernateTemplate().find(
						"from TTrnWctplPremiseQuo tWpuo where tWpuo.wbdPolicyId = ? and (tWpuo.id.wbdId = ? or  tWpuo.wbdBldId = ?) and "
								+ " tWpuo.id.wbdValidityStartDate <= ? and tWpuo.wbdValidityExpiryDate > ? and "
								+ " tWpuo.wbdEndtId = ?", Long.valueOf( policyId ),
						Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), validityStartDate,
						validityStartDate, endId );
				
			}else{
				
				tTrnWctplPremiseQuo = getHibernateTemplate().find(
						"from TTrnWctplPremiseQuo tWpuo where tWpuo.wbdPolicyId = ? and (tWpuo.id.wbdId = ? or  tWpuo.wbdBldId = ?) and "
								+ " tWpuo.id.wbdValidityStartDate <= ? and tWpuo.wbdValidityExpiryDate > ? and "
								+ " tWpuo.wbdEndtId <= ? and tWpuo.wbdStatus<>4", Long.valueOf( policyId ),
						Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ), validityStartDate,
						validityStartDate, endId );
				
			}

		}
		return tTrnWctplPremiseQuo;
	}
	
	 
}