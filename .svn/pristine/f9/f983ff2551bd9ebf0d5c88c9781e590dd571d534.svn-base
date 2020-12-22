/**
 * 
 */
package com.rsaame.pas.fidelity.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccUnnamedPersonQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1016303
 * 
 */
public class FidelityLoadDAO extends BaseSectionLoadDAO {
	private final static Integer FIDELITY_RISK_CODE = Integer.valueOf(Utils
			.getSingleValueAppConfig("FIDELITY_RISK_CODE"));
	private final static Integer FIDELITY_COV_CODE = Integer.valueOf(Utils
			.getSingleValueAppConfig("FIDELITY_COV_CODE"));
	private final static Integer FIDELITY_CT_CODE = Integer.valueOf(Utils
			.getSingleValueAppConfig("FIDELITY_CT_CODE"));
	private final static Integer FIDELITY_CST_CODE = Integer.valueOf(Utils
			.getSingleValueAppConfig("FIDELITY_CST_CODE"));
	private static final Logger LOGGER = Logger.getLogger(FidelityLoadDAO.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rsaame.pas.dao.cmn.BaseSectionLoadDAO#getRiskDetails(com.rsaame.pas
	 * .vo.bus.RiskGroup, java.lang.Long, java.lang.Long,
	 * com.rsaame.pas.vo.app.LoadExistingInputVO)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	protected RiskGroupDetails getRiskDetails(RiskGroup riskGroup,
			Long policyId, Long endId, LoadExistingInputVO lei) {

		FidelityVO fidelityVO = null;
		FidelityNammedEmployeeDetailsVO fidelityNammedEmployee = null;
		FidelityUnnammedEmployeeVO fidelityUnnammedEmployee = null;
		List<FidelityNammedEmployeeDetailsVO> fidelityNammedEmployeesList = null;
		List<FidelityUnnammedEmployeeVO> fidelityUnnammedEmployeesList = null;
		TTrnPremiumQuo premiumQuo = null;
		LocationVO locationVO = (LocationVO) riskGroup;
		List<TTrnGaccPersonQuo> tTrnGaccPersonQuoList = null;
		List<TTrnUwQuestionsQuo> questionsQuo = null;
		UWQuestionsVO uWQuestionsVO = new UWQuestionsVO();

		/* 
		 * Changed to pick the risk details based on validity start date - Fix identified for defect
		 * defect nummber 355 - Phase 2 RADAR defect number
		 */
		Date validityStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), endId, lei.getPolLinkingId(),lei.isQuote() );
		if (lei.getAppFlow().equals(Flow.VIEW_POL) || lei.getAppFlow().equals(Flow.VIEW_QUO)) 
		{

			/*tTrnGaccPersonQuoList = getHibernateTemplate().find("from TTrnGaccPersonQuo namedPerson where (gprEndtId, id.gprId, gprPolicyId, gprBldId,gprRskCode) in "
							+ "( select max ( gprEndtId ),id.gprId , gprPolicyId, gprBldId,gprRskCode from TTrnGaccPersonQuo where gprEndtId <= ? and gprPolicyId= ? "
							+ "and gprBldId= ? and gprRskCode=? group by gprPolicyId,  gprBldId,gprRskCode ,id.gprId )  and namedPerson.gprStatus <> 4",
							endId,policyId,Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()),FIDELITY_RISK_CODE.longValue());
							*/
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				/*tTrnGaccPersonQuoList = getHibernateTemplate().find("from TTrnGaccPersonQuo namedPerson where (gprEndtId, id.gprId, gprPolicyId, gprBldId,gprRskCode) in "
						+ "( select max ( gprEndtId ),id.gprId , gprPolicyId, gprBldId,gprRskCode from TTrnGaccPersonQuo where gprEndtId <= ? and gprPolicyId= ? "
						+ "and gprBldId= ? and gprRskCode=? group by gprPolicyId,  gprBldId,gprRskCode ,id.gprId )",
						endId,policyId,	Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()),TB_RISK_CODE.longValue());*/
				
				tTrnGaccPersonQuoList = getHibernateTemplate().find(
						"from TTrnGaccPersonQuo where gprPolicyId = ? and id.gprValidityStartDate <= ? and " + " gprValidityExpiryDate > ? and gprEndtId = ? and gprBldId = ? and gprRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( FIDELITY_RISK_CODE ) );
			} else {
				tTrnGaccPersonQuoList = getHibernateTemplate().find(
					"from TTrnGaccPersonQuo where gprPolicyId = ? and id.gprValidityStartDate <= ? and " + " gprValidityExpiryDate > ? and gprEndtId <= ? and gprStatus <> 4 and gprBldId = ? and gprRskCode=?",
					policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),Long.valueOf( FIDELITY_RISK_CODE ) );
			}
		} 
		else 
		{
			tTrnGaccPersonQuoList = (List<TTrnGaccPersonQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_GACC_PERSON_LOAD_FIDELITY",lei.getAppFlow(), getHibernateTemplate(), false,
							endId, policyId,Long.valueOf(locationVO.getRiskGroupId()),Long.valueOf(FIDELITY_RISK_CODE));
		}
		if (!Utils.isEmpty(tTrnGaccPersonQuoList)) 
		{
			fidelityNammedEmployeesList = new com.mindtree.ruc.cmn.utils.List<FidelityNammedEmployeeDetailsVO>(FidelityNammedEmployeeDetailsVO.class);
			for (TTrnGaccPersonQuo gaccPersonQuo : tTrnGaccPersonQuoList) 
			{
				fidelityVO = new FidelityVO();
				fidelityVO.setAggregateLimit(gaccPersonQuo.getGprAgrLmt().doubleValue());
				fidelityNammedEmployee = new FidelityNammedEmployeeDetailsVO();
				fidelityNammedEmployee.setEmpName(gaccPersonQuo.getGprEName());
				fidelityNammedEmployee.setEmpType(gaccPersonQuo.getGprRtCode().intValue());
				fidelityNammedEmployee.setEmpDesignation(gaccPersonQuo.getGprAName());
				fidelityNammedEmployee.setLimitPerPerson(gaccPersonQuo.getGprSumInsured().doubleValue());
				fidelityNammedEmployee.setGprFidelityId(gaccPersonQuo.getId().getGprId());

				/*
				 * fetch the premium record based on tTrnGaccPersonQuo.gprid and
				 * map to premium.basicriskId
				 */
				premiumQuo = (TTrnPremiumQuo) DAOUtils.getExistingValidStateRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(),
								false, endId, policyId, new BigDecimal(gaccPersonQuo.getId().getGprId()),new BigDecimal(locationVO.getRiskGroupId()),
								FIDELITY_COV_CODE.shortValue(),FIDELITY_CT_CODE.shortValue(),FIDELITY_CST_CODE.shortValue());
				fidelityVO.setDeductible(premiumQuo.getPrmCompulsoryExcess().doubleValue());
				fidelityNammedEmployeesList.add(fidelityNammedEmployee);
			}
			fidelityVO.setFidelityEmployeeDetails(fidelityNammedEmployeesList);

		}
		List<TTrnGaccUnnamedPersonQuo> gaccUnnamedPersonQuos = null;
		if (lei.getAppFlow().equals(Flow.VIEW_POL)|| lei.getAppFlow().equals(Flow.VIEW_QUO)) 
		{
			/*gaccUnnamedPersonQuos = getHibernateTemplate().find("from TTrnGaccUnnamedPersonQuo unamedPerson where (gupEndtId, id.gupId, gupPolicyId, gupBldId,gupRskCode) in "
							+ "( select max ( gupEndtId ),id.gupId , gupPolicyId, gupBldId,gupRskCode from TTrnGaccUnnamedPersonQuo where gupEndtId <= ? and gupPolicyId= ? "
							+ "and gupBldId= ? and gupRskCode=? group by gupPolicyId,  gupBldId,gupRskCode ,id.gupId )  and unamedPerson.gupStatus <> 4",
							endId,policyId,Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()), FIDELITY_RISK_CODE);*/
			if( lei.getPolicyStatus().equals( SvcConstants.POL_STATUS_DELETED ) )
			{
				/*tTrnGaccUnnamedPersonQuoList = getHibernateTemplate().find("from TTrnGaccUnnamedPersonQuo unamedPerson where (gupEndtId, id.gupId, gupPolicyId, gupBldId,gupRskCode) in "
						+ "( select max ( gupEndtId ),id.gupId , gupPolicyId, gupBldId,gupRskCode from TTrnGaccUnnamedPersonQuo where gupEndtId <= ? and gupPolicyId= ? "
						+ "and gupBldId= ? and gupRskCode=? group by gupPolicyId,  gupBldId,gupRskCode ,id.gupId ) ",
						endId,policyId,	Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()), GPA_RISK_CODE);*/
				
				gaccUnnamedPersonQuos = getHibernateTemplate().find(
						"from TTrnGaccUnnamedPersonQuo where gupPolicyId = ? and id.gupValidityStartDate <= ? and " + " gupValidityExpiryDate > ? and gupEndtId = ? and gupBldId = ? and gupRskCode=?",
						policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),FIDELITY_RISK_CODE );
			}
			else
			{
				gaccUnnamedPersonQuos = getHibernateTemplate().find(
					"from TTrnGaccUnnamedPersonQuo where gupPolicyId = ? and id.gupValidityStartDate <= ? and " + " gupValidityExpiryDate > ? and gupEndtId <= ? and gupStatus <> 4 and gupBldId = ? and gupRskCode=?",
					policyId, validityStartDate, validityStartDate, endId, Long.valueOf( ( (LocationVO) riskGroup ).getRiskGroupId() ),FIDELITY_RISK_CODE );
			}
		}
		else
		{
			gaccUnnamedPersonQuos = (List<TTrnGaccUnnamedPersonQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_GACC_UNNAMED_PERSON_LOAD_FIDELITY",
							lei.getAppFlow(), getHibernateTemplate(), false,endId, policyId,Long.valueOf(locationVO.getRiskGroupId()),FIDELITY_RISK_CODE);
		}
		if (!Utils.isEmpty(gaccUnnamedPersonQuos)) 
		{
			fidelityUnnammedEmployeesList = new com.mindtree.ruc.cmn.utils.List<FidelityUnnammedEmployeeVO>(FidelityUnnammedEmployeeVO.class);
			for (TTrnGaccUnnamedPersonQuo gaccUnnamedPersonQuo : gaccUnnamedPersonQuos) 
			{
				if (Utils.isEmpty(fidelityVO)) 
				{
					fidelityVO = new FidelityVO();
				}
				fidelityVO.setAggregateLimit(gaccUnnamedPersonQuo.getGupAgrLmt().doubleValue());
				fidelityUnnammedEmployee = new FidelityUnnammedEmployeeVO();

				fidelityUnnammedEmployee.setEmpType(gaccUnnamedPersonQuo.getGupOcCode());
				fidelityUnnammedEmployee.setTotalNumberOfEmployee(gaccUnnamedPersonQuo.getGupNoOfPerson());
				fidelityUnnammedEmployee.setLimitPerPerson(gaccUnnamedPersonQuo.getGupSumInsured().doubleValue());
				fidelityUnnammedEmployee.setGupFidelityId(gaccUnnamedPersonQuo.getId().getGupId());
				/*
				 * fetch the premium record based on tTrnGaccPersonQuo.gprid and
				 * map to premium.basicriskId
				 */
				premiumQuo = (TTrnPremiumQuo) DAOUtils.getExistingValidStateRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, lei.getAppFlow(), getHibernateTemplate(),
								false, endId, policyId,new BigDecimal(gaccUnnamedPersonQuo.getId().getGupId()),new BigDecimal(locationVO.getRiskGroupId()),
								FIDELITY_COV_CODE.shortValue(),FIDELITY_CT_CODE.shortValue(),FIDELITY_CST_CODE.shortValue());
				fidelityVO.setDeductible(premiumQuo.getPrmCompulsoryExcess().doubleValue());
				fidelityUnnammedEmployeesList.add(fidelityUnnammedEmployee);
			}
			fidelityVO.setUnnammedEmployeeDetails(fidelityUnnammedEmployeesList);

		}
		List<UWQuestionVO> uwQuestionList = new ArrayList<UWQuestionVO>();
		if ((lei.getAppFlow() == Flow.AMEND_POL)|| (lei.getAppFlow() == Flow.RESOLVE_REFERAL)) 
		{
			questionsQuo = getHibernateTemplate().find("from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? "
							+ "and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityExpiryDate = ?",Long.valueOf(policyId),Long.valueOf(((LocationVO) riskGroup)										.getRiskGroupId()),
							SvcConstants.EXP_DATE);
		}
		else
		{
			questionsQuo = (List<TTrnUwQuestionsQuo>) DAOUtils.getTableSnapshotQuery("T_TRN_UW_QUESTIONS", lei.getAppFlow(), getHibernateTemplate(), false,
							endId, policyId, Long.valueOf(((LocationVO) riskGroup).getRiskGroupId()));
		}
		int code = 0;
		for (TTrnUwQuestionsQuo questionsVOs : questionsQuo) 
		{
			code = questionsVOs.getId().getUqtUwqCode();
			UWQuestionVO uwQuestion = new UWQuestionVO();
			uwQuestion.setQId(questionsVOs.getId().getUqtUwqCode());
			uwQuestion.setResponse(questionsVOs.getUqtUwqAnswer());
			uwQuestionList.add(uwQuestion);

		}
		Collections.sort( uwQuestionList );
		LOGGER.debug( "Code is "+String.valueOf( code ) );
		uWQuestionsVO.setQuestions(uwQuestionList);
		if (!Utils.isEmpty(fidelityVO)) {
			fidelityVO.setUwQuestions(uWQuestionsVO);
		}
		
		PremiumVO prmVO = new PremiumVO();
		if (!Utils.isEmpty(premiumQuo)&& !Utils.isEmpty(premiumQuo.getPrmPremium())) 
		{
			//Added for Bahrain 3 decimal - Starts
			 if (isSBSBahrainPolicy( endId ,  lei , policyId )) {
				 prmVO.setPremiumAmt(Double.valueOf(decFormBahrain.format(premiumQuo.getPrmPremium().doubleValue())));
			 }
			 else {
				 prmVO.setPremiumAmt(Double.valueOf(decForm.format(premiumQuo.getPrmPremium().doubleValue())));
			 }
			//Added for Bahrain 3 decimal - Ends
		}
		if (!Utils.isEmpty(fidelityVO)) 
		{
			fidelityVO.setPremium(prmVO);
		}

		/*
		 * if ((lei.getAppFlow()==Flow.AMEND_POL)) { gaccBldList =
		 * getHibernateTemplate().find(
		 * "from TTrnGaccBuildingQuo ttrn where ttrn.gbdPolicyId=? and ttrn.gbdValidityExpiryDate=? and ttrn.gbdBldId=?"
		 * , policyId, SvcConstants.EXP_DATE, Long.valueOf(
		 * locationVO.getRiskGroupId() ) ); } else{ gaccBldList =
		 * getHibernateTemplate().find(
		 * "from TTrnGaccBuildingQuo where gbdPolicyId=? and gbdEndtId=? and gbdBldId=?"
		 * , policyId, endId, Long.valueOf( locationVO.getRiskGroupId() ) ); }
		 */

		return fidelityVO;

	}

}
