package com.rsaame.pas.b2b.ws.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import java.util.Map;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.User;
import org.springframework.dao.DataAccessException;

import com.rsaame.pas.b2b.ws.batch.input.BatchInput;
import com.rsaame.pas.b2b.ws.constant.WSAppContants;
import com.rsaame.pas.b2b.ws.dao.vo.EplatformWsStaging;
import com.rsaame.pas.b2b.ws.handler.DocumentHandler;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyRequest;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.b2b.ws.vo.Document;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyResponse;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnPolicy;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.SearchInsuredVO;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class WSDAOUtils {
	private final static Logger LOGGER = Logger.getLogger(WSDAOUtils.class);
	private final static String QUOTE_SEQ_SBS = "SEQ_SBS_QUO_NO";
	private final static String INSUREDID_SEQ_SBS = "SEQ_INSURED_ID";
	private static String DEFAULT_ENDT_ID = "DEFAULT_ENDTID_QUOTE";

	public static PolicyVO checkIfInsuredExists(PolicyVO policyVO, String name, String zipCode, int brCode) {
		LOGGER.info("Enter checkIfInsuredExists ");
		String sqlQuery = null;
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		boolean insuredExists = false;
		try {
			Query query = session.createSQLQuery(
					"SELECT INS_INSURED_CODE FROM T_MAS_INSURED WHERE upper(INS_E_FIRST_NAME)=upper(:name) and upper(INS_E_ZIP_CODE)= upper(:zip) and INS_BR_CODE=:code");
			query.setParameter("name", name);
			query.setParameter("zip", zipCode);
			query.setParameter("code", brCode);

			/*
			 * sqlQuery = "SELECT INS_INSURED_CODE FROM T_MAS_INSURED WHERE
			 * upper(INS_E_FIRST_NAME)=upper(? + "'" + ") and " +
			 * " upper(INS_E_ZIP_CODE)='" + zipCode + "' and " + "INS_BR_CODE="
			 * + brCode;
			 */
			// Query query = session.createSQLQuery(sqlQuery);
			List<Object> results = query.list();
			if (!Utils.isEmpty(results) && results.size() >= 1) {
				try {
					if (getInsuredCheckForPLQuote(name, zipCode, "50")) {
						insuredExists = true;
					}
				} catch (IllegalAccessException e) {
					throw new BusinessException("cmn.compareError", e, "Error  in record retriving");
				}

			}

			PolicyVO baseVO = null;
			/*
			 * To set POL_BUSINESS_TYPE to 0 if renewal quote and 1 if new quote
			 * and 2 if new existing quote
			 */
			if (Flow.RENEWAL.equals(policyVO.getAppFlow())) {
				policyVO.getGeneralInfo().getInsured().setPolBusType(WSAppContants.BUS_TYPE_RENEWAL);
			} else if (Flow.CREATE_QUO.equals(policyVO.getAppFlow()) && (insuredExists)) {
				policyVO.getGeneralInfo().getInsured().setPolBusType(WSAppContants.BUS_TYPE_NEW_FOR_EXISTING);
			} else {
				policyVO.getGeneralInfo().getInsured().setPolBusType(WSAppContants.BUS_TYPE_NEW);
			}

			if (insuredExists) {

				SearchInsuredVO searchInsuredVO = new SearchInsuredVO();
				searchInsuredVO.setBrokerId(policyVO.getGeneralInfo().getSourceOfBus().getPartnerId());
				String ccgCode = getCcgCode(policyVO.getLoggedInUser());
				if (!Utils.isEmpty(ccgCode)) {
					searchInsuredVO.setCcgCode(ccgCode);
				}
				searchInsuredVO.setCompleteName(policyVO.getGeneralInfo().getInsured().getName());
				searchInsuredVO.setExactSearch(true);
				String insuredId = results.get(0).toString();
				if (insuredId != null) {
					policyVO.getGeneralInfo().getInsured().setInsuredId(Long.valueOf(insuredId));
					policyVO.getGeneralInfo().getInsured().setInsuredCode(Long.valueOf(insuredId));
					policyVO = (PolicyVO) TaskExecutor.executeTasks("COMPARE_MASTER", policyVO);
				}

				baseVO = (PolicyVO) TaskExecutor.executeTasks("SAVE_INSURED_DETAILS", policyVO);

			}
			if (policyVO.getAppFlow().equals(Flow.AMEND_POL) || policyVO.getAppFlow().equals(Flow.EDIT_QUO)
					|| (policyVO.getAppFlow().equals(Flow.RESOLVE_REFERAL))) {
				// call to check If any changes from TMasInsured
				// baseVO = (PolicyVO) TaskExecutor.executeTasks(
				// "AMEND_POLICY_STATUS_CHECK", policyVO );
				// baseVO.setInsuredChanged(true);
				// if( baseVO.isInsuredChanged() ){

				// baseVO = (PolicyVO) TaskExecutor.executeTasks(
				// SAVE_OPERATION_OP_TYPE, policyVO );
				baseVO = (PolicyVO) TaskExecutor.executeTasks("SAVE_INSURED_DETAILS", policyVO);
				baseVO = (PolicyVO) TaskExecutor.executeTasks("ENDORSE_GENINFO_SAVE_INVSVC", policyVO);
				// baseVO.setInsuredChanged(true);
				// }
			} else {

				// Setting Insured id from sequence

				Long insuredId = NextSequenceValue.getNextSequence(INSUREDID_SEQ_SBS, null, null, hibernateTemplate);
				if (!Utils.isEmpty(insuredId)) {
					policyVO.getGeneralInfo().getInsured().setInsuredCode(insuredId);
				}
			}

			if (policyVO.getAppFlow().equals(Flow.CREATE_QUO)) {
				// Set quote number
				Long quoteNo = NextSequenceValue.getNextSequence(QUOTE_SEQ_SBS,
						Utils.getSingleValueAppConfig("TRAN_TYPE_QUO"),
						policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc(), hibernateTemplate);
				if (!Utils.isEmpty(quoteNo)) {
					policyVO.setQuoteNo(quoteNo);
				}

				// Setting by default EndtId and EndtNo
				policyVO.setEndtId(Long.valueOf((String) Utils.getSingleValueAppConfig(DEFAULT_ENDT_ID)));
				policyVO.setEndtNo(Long.valueOf(SvcConstants.zeroVal));
			}

			if (policyVO.getNewEndtId() != null) {
				policyVO.setEndtId(policyVO.getNewEndtId());
			}

			if (policyVO.getNewEndtNo() != null) {
				policyVO.setEndtNo(policyVO.getNewEndtNo());
			}

			if (policyVO.getNewValidityStartDate() != null) {
				policyVO.setValidityStartDate(policyVO.getNewValidityStartDate());
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in checkIfInsuredExists " + e);
		} finally {
			session.close();
		}
		return policyVO;

	}

	public static Boolean getInsuredCheckForPLQuote(String insEFirstName, String insEZipCode, String polPolicyType)
			throws IllegalAccessException {
		LOGGER.info("Enter getInsuredCheckForPLQuote ");
		String isInsuredExists = null;
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		try {
			String sqlQuery = "SELECT PKG_PAS_UTILS.CHECK_IF_USER_HAS_PL_QUOTE('" + insEFirstName + "','" + insEZipCode
					+ "', " + polPolicyType + com.Constant.CONST_FROM_DUAL;
			Query query = session.createSQLQuery(sqlQuery);
			List<Object> results = query.list();
			if (!Utils.isEmpty(results)) {
				isInsuredExists = results.get(0).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getInsuredCheckForPLQuote" + e);
		} finally {
			session.close();
		}
		if (!Utils.isEmpty(isInsuredExists) && isInsuredExists.equalsIgnoreCase("yes")) {
			return true;
		} else {
			return false;
		}
	}

	public static Integer defaultAppover(PolicyVO policyVo) {
		// TODO Auto-generated method stub
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Object result = null;
		Integer defaultApprover = null;
		try {
			String sqlQuery = "select sch_default_approver  from T_mas_scheme  where SCH_CODE="
					+ policyVo.getScheme().getSchemeCode() + "   and SCH_BROKER_CODE="
					+ policyVo.getGeneralInfo().getSourceOfBus().getPartnerId();
			
			Query query = session.createSQLQuery(sqlQuery);
			result = (query.list()).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in default approver" + e);
		} finally {
			session.close();
		}
		if(!Utils.isEmpty(result)) {
			defaultApprover = ((BigDecimal) result).intValue();
		}
		return defaultApprover;

	}

	public static Long getLatestEndtId(PolicyVO policyVO) {
		Long endtId = null;
		try {
			if (!Utils.isEmpty(policyVO.getNewEndtId()) && policyVO.getEndtId() < policyVO.getNewEndtId()) {
				endtId = policyVO.getNewEndtId();
			} else {
				if (!Utils.isEmpty(policyVO.getEndtId())) {
					endtId = policyVO.getEndtId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getLatestEndtId" + e);

		}
		return endtId;
	}

	public static String getCcgCode(User user) {
		String ccgCode = null;
		try {
			List<Object> ccgCodeList = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.FETCH_USER_CCG_CODE,
					String.valueOf(user));

			if (!Utils.isEmpty(ccgCodeList) && !Utils.isEmpty(ccgCodeList.get(0))) {
				ccgCode = (String) ccgCodeList.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getCcgCode" + e);
		}
		return ccgCode;
	}

	public static List<TTrnPolicyQuo> getPolicyRecord(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<TTrnPolicyQuo> policy = null;
		try {
			/*
			 * fetch all valid policy records for nil endt.
			 */

			policy = ht.find(
					"from TTrnPolicyQuo where polQuotationNo = ? and  polValidityExpiryDate = ? and polIssueHour=3 and polPreparedBy = ? order by polClassCode",
					policyVO.getQuoteNo(), SvcConstants.EXP_DATE,
					Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getPolicyRecord" + e);
		} finally {
			session.close();
		}
		return policy;

	}

	public static List<EplatformWsStaging> getPolicyRecordFromStaging(Long polQuotationNo) {

		List<EplatformWsStaging> staging = null;

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		try {
			staging = ht.find("from EplatformWsStaging where polQuotationNo = ? ", polQuotationNo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("1. Error in getPolicyRecordFromStaging" + e);
		} finally {
			session.close();
		}
		return staging;

	}

	public static PolicyVO getBaseSecPolicyId(PolicyVO policyVO) {

		Long endtId = getLatestEndtId(policyVO);
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		String sqlQuery = "";
		try {
			if (policyVO.getIsQuote()) {
				sqlQuery = "SELECT PKG_PAS_UTILS.get_base_sec_pol_id_quo(" + policyVO.getPolLinkingId() + "," + endtId
						+ com.Constant.CONST_FROM_DUAL;
			} else {
				sqlQuery = "SELECT PKG_ENDT_GEN.get_base_sec_pol_id(" + policyVO.getPolLinkingId() + "," + endtId
						+ com.Constant.CONST_FROM_DUAL;
			}

			
			Integer policyId = null;
			Query query = session.createSQLQuery(sqlQuery);
			session.flush();
			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc)) {
				policyId = Integer.valueOf(resultsFunc.get(0).toString());
				if (Utils.isEmpty(policyId)) {
					throw new BusinessException("cmn.unknownError", null,
							"The policy no is 0 or null for inserting into endorsment details table ");
				}
			policyVO.getGeneralInfo().getAdditionalInfo().setPolicyId(policyId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getBaseSecPolicyId" + e);
		} finally {
			session.close();
		}
		return policyVO;
	}

	public static void updateReferralStatus(PolicyVO policyVO) {

		if (!policyVO.getTaskDetails().getPolicyType().equals("31")) {
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			/*
			 * Below query should fetch records basis linking id and VED in
			 * order to update status of current version which is in pending
			 * status
			 */
			/*
			 * List<TTrnPolicyQuo> trnPolicyQuos = getHibernateTemplate().find(
			 * "from TTrnPolicyQuo ttrnPol where ttrnPol.polLinkingId=?",
			 * taskDetails.getPolLinkingId() );
			 */
			/*
			 * Fetch only the latest endorsementId record to update the status.
			 */
			 //Changes-Adv#-10698 -JLT adding pol_issue_hour 
			List<TTrnPolicyQuo> polRecList = ht.find(
					"from TTrnPolicyQuo polTable where polTable.polLinkingId=? and polTable.polValidityExpiryDate=? and polTable.id.polEndtId=? and pol_issue_hour = 3",
					policyVO.getTaskDetails().getPolLinkingId(), SvcConstants.EXP_DATE,
					policyVO.getTaskDetails().getPolEndId());

			for (TTrnPolicyQuo tTrnPolicyQuo : polRecList) {
				tTrnPolicyQuo.setPolStatus(Byte.valueOf(Utils.getSingleValueAppConfig(SvcConstants.QUOTE_REFERRED)));
				tTrnPolicyQuo.setPolApprovedBy(null);
				tTrnPolicyQuo.setPolApprovalDate(null);
				ht.merge(tTrnPolicyQuo);
			}

		}

	}

	public static List<Object[]> getBuildingId(PolicyVO policyVo) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<Object[]> resultsFunc = null;
		try {
			int baseSecId = getBaseSecPolicy(policyVo);
			if (baseSecId == 0) {
				sqlQuery = "Select  Bld_Id ,bld_e_name  From T_Trn_Building_Quo   Where Bld_Policy_Id In (Select pol_policy_id From T_Trn_Policy_Quo Where Pol_Quotation_No ="
						+ policyVo.getQuoteNo() + "   and pol_endt_id <=" + policyVo.getEndtId()
						+ " AND  Pol_Validity_Expiry_Date = '31-DEC-2049')  and bld_endt_id <=  " + policyVo.getEndtId()
						+ " and bld_validity_expiry_date ='31-DEC-2049' and bld_status<>4  ";
			} else {

				sqlQuery = "SELECT wbd_id,wbd_e_name From T_Trn_Wctpl_Premise_Quo WHERE  wbd_policy_id IN "
						+ " (SELECT pol_policy_id  FROM T_Trn_Policy_Quo   WHERE Pol_Quotation_No ="
						+ policyVo.getQuoteNo() + " and pol_endt_id <=" + policyVo.getEndtId()
						+ " AND  Pol_Validity_Expiry_Date = '31-DEC-2049') " + " And Wbd_Endt_Id <= "
						+ policyVo.getEndtId() + " And wbd_Validity_Expiry_Date ='31-DEC-2049' AND wbd_status <>4";

			}

			
			Query query = session.createSQLQuery(sqlQuery);
			// Object result = (query.list()).get(0);
			resultsFunc = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getBuildingId" + e);
		} finally {
			session.close();
		}
		
		return resultsFunc;

	}

	// to get base section

	private static int getBaseSecPolicy(PolicyVO policyVO) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		String sqlQuery = "";
		Integer baseSection = null;
		try {
			if (policyVO.getIsQuote()) {
				sqlQuery = "SELECT PKG_PAS_UTILS.get_is_base(" + policyVO.getPolLinkingId() + " , 'Q'  ) FROM DUAL";
			} else {
				sqlQuery = "SELECT PKG_PAS_UTILS.get_is_base(" + policyVO.getPolLinkingId() + " , 'P'  ) FROM DUAL";

			}

			
			
			Query query = session.createSQLQuery(sqlQuery);
			session.flush();
			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc)) {
				baseSection = Integer.valueOf(resultsFunc.get(0).toString());
				if (Utils.isEmpty(baseSection)) {
					throw new BusinessException("cmn.unknownError", null, "Error in retriving base section record ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getBuildingId" + e);
		} finally {
			session.close();
		}
		return baseSection;
	}

	public static List<String> contentId(PolicyVO policyVO, int sectionId) {
		String sqlQuery = "";
		Integer contentId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<String> resultsFunc = null;
		try {
			if (sectionId == 5) {
				sqlQuery = " SELECT CNT_CONTENT_ID  FROM T_TRN_CONTENT_QUO   Where Cnt_Category = 21 AND CNT_POLICY_ID in (SELECT Pol_Policy_id from t_trn_policy_quo  where pol_quotation_no="
						+ policyVO.getQuoteNo() + com.Constant.CONST_POL_VALIDITY_EXPIRY_DATE_AND_POL_STATUS_4
						+ com.Constant.CONST_AND_TRUNC_CNT_VALIDITY_EXPIRY_DATE_31_DEC_49_END
						+ com.Constant.CONST_AND_CNT_ENDT_ID_LESS_THAN_EQUAL_TO + policyVO.getEndtId()
						+ " And Cnt_Risk_Sub_Dtl  in( 4,6)   AND CNT_STATUS <>4 ";
			} else if (sectionId == 3) {
				sqlQuery = " SELECT CNT_CONTENT_ID  FROM T_TRN_CONTENT_QUO   Where Cnt_Category = 19 AND CNT_POLICY_ID in (SELECT Pol_Policy_id from t_trn_policy_quo  where pol_quotation_no="
						+ policyVO.getQuoteNo() + com.Constant.CONST_POL_VALIDITY_EXPIRY_DATE_AND_POL_STATUS_4
						+ com.Constant.CONST_AND_TRUNC_CNT_VALIDITY_EXPIRY_DATE_31_DEC_49_END
						+ com.Constant.CONST_AND_CNT_ENDT_ID_LESS_THAN_EQUAL_TO + policyVO.getEndtId()
						+ " And Cnt_Risk_Sub_Dtl  = 3   AND CNT_STATUS <>4 ";
			} else if (sectionId == 4) {
				sqlQuery = " SELECT CNT_CONTENT_ID  FROM T_TRN_CONTENT_QUO   Where Cnt_Category = 20 AND CNT_POLICY_ID in (SELECT Pol_Policy_id from t_trn_policy_quo  where pol_quotation_no="
						+ policyVO.getQuoteNo() + com.Constant.CONST_POL_VALIDITY_EXPIRY_DATE_AND_POL_STATUS_4
						+ com.Constant.CONST_AND_TRUNC_CNT_VALIDITY_EXPIRY_DATE_31_DEC_49_END
						+ com.Constant.CONST_AND_CNT_ENDT_ID_LESS_THAN_EQUAL_TO + policyVO.getEndtId()
						+ " And Cnt_Risk_Sub_Dtl  = 1   AND CNT_STATUS <>4 ";
			}

			

			Query query = session.createSQLQuery(sqlQuery);
			resultsFunc = query.list();
			/*
			 * if (!Utils.isEmpty(resultsFunc)) { //contentId =
			 * Integer.valueOf(resultsFunc.get(0).toString()); if
			 * (Utils.isEmpty(contentId)) { throw new
			 * BusinessException("cmn.unknownError", null,
			 * "Error in retriving base section record "); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in contentId" + e);
		} finally {
			session.close();
		}
		return resultsFunc;
	}

	public static Long getPolicyIds(PolicyVO policyVo, int classCode) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long policyId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		 //Changes-Adv#-10698 -JLT adding pol_issue_hour
		try {
			sqlQuery = "Select Pol_Policy_Id From T_Trn_Policy_Quo Where Pol_Quotation_No =" + policyVo.getQuoteNo()
					+ " AND  Pol_Validity_Expiry_Date = '31-DEC-2049' AND POL_status <>4 AND pol_issue_hour = 3 AND  POL_CLASS_CODE="
					+ classCode;

			Query query = session.createSQLQuery(sqlQuery);
			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc)) {
				policyId = Long.valueOf(resultsFunc.get(0).toString());
				if (Utils.isEmpty(policyId)) {
					policyId=null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getPolicyIds" + e);
		} finally {
			session.close();
		}
		return policyId;

	}

	public static List<Object[]> getCoverDetails(PolicyVO policyVo, Long policyId,int riskType) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		List<Object[]> resultsFunc = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "select prm_basic_rsk_id,prm_rt_code,prm_rsk_id FROM  T_Trn_Premium_Quo  where PRM_POLICY_ID ="
				+ policyId + " and prm_endt_id<=" + policyVo.getEndtId()
				+ " and prm_validity_expiry_date='31-DEC-2049' AND PRM_STATUS<>4  AND PRM_COV_CODE=1 AND PRM_RT_CODE = "
				+ riskType;
		// + "(9,999,13)";
		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sqlQuery);
			resultsFunc = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getCoverDetails" + e);
		} finally {
			session.close();
		}
		return resultsFunc;

	}

	public static List<String> getunNameEMRiskId(PolicyVO policyVo, Long policyId) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long riskId = null;
		List<String> resultsFunc = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "Select  WUP_ID From T_TRN_WCTPL_UNNAMED_PERSON_QUO  Where Wup_Policy_Id =" + policyId
				+ " AND  Wup_Validity_Expiry_Date = '31-DEC-2049' AND Wup_Status <>4";

		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sqlQuery);
			resultsFunc = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getunNameEMRiskId" + e);
		} finally {
			session.close();
		}
		return resultsFunc;

	}

	public static List<String> getGprId(PolicyVO policyVo, Long policyId, int riskCode) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		ArrayList<String> resultsFunc = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "select GPR_ID  from T_TRN_GACC_PERSON_QUO  WHERE GPR_VALIDITY_EXPIRY_DATE='31-DEC-2049' AND GPR_STATUS <> 4 AND GPR_POLICY_ID="
				+ policyId + " and gpr_rsk_code=" + riskCode;

		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sqlQuery);
			resultsFunc = (ArrayList<String>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getGprId" + e);
		} finally {
			session.close();
		}
		return resultsFunc;

	}

	public static List<String> getGupFidelityId(PolicyVO policyVo, Long policyId, int riskCode) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		List<String> resultsFunc = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "select GPR_ID  from T_TRN_GACC_UNNAMED_PERSON_QUO  WHERE GUP_VALIDITY_EXPIRY_DATE='31-DEC-2049' AND GUP_STATUS <> 4 AND GUP_POLICY_ID="
				+ policyId + " and gup_rsk_code=" + riskCode;

		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sqlQuery);
			resultsFunc = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getGupFidelityId" + e);
		} finally {
			session.close();
		}
		return resultsFunc;

	}

	public static Long getGacchBldID(PolicyVO policyVo, Long policyId) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long riskId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "Select  Gbd_Id From T_Trn_Gacc_Building_Quo Where Gbd_Policy_Id =" + policyId
				+ " AND  Wpr_Validity_Expiry_Date = '31-DEC-2049' AND GBD_VALIDITY_EXPIRY_DATE <>4";

		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sqlQuery);
			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc)) {
				riskId = Long.valueOf(resultsFunc.get(0).toString());
				if (Utils.isEmpty(riskId)) {
					throw new BusinessException("cmn.unknownError", null, "Error in retriving base riskId records");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getGacchBldID" + e);
		} finally {
			session.close();
		}
		return riskId;

	}

	public static Long getGacchID(PolicyVO policyVo, Long policyId) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long riskId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "Select  Gbd_Id From T_Trn_Gacc_Building_Quo Where Gbd_Policy_Id =" + policyId
				+ " AND  GBD_Validity_Expiry_Date = '31-DEC-2049' AND GBD_STATUS <>4";

		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sqlQuery);
			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc)) {
				riskId = Long.valueOf(resultsFunc.get(0).toString());
				if (Utils.isEmpty(riskId)) {
					throw new BusinessException("cmn.unknownError", null, "Error in retriving base riskId record_2");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getGacchID" + e);
		} finally {
			session.close();
		}
		return riskId;

	}

	public static Long getGacchCahDetailsID(PolicyVO policyVo, Long policyId) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long riskId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "	Select GCD_ID  From  T_Trn_Gacc_Cash_Details_Quo Where Gcd_Pol_Policy_Id =" + policyId
				+ " AND  GCD_Validity_Expiry_Date = '31-DEC-2049' AND GCD_VALIDITY_EXPIRY_DATE <>4";

		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sqlQuery);
			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc)) {
				riskId = Long.valueOf(resultsFunc.get(0).toString());
				if (Utils.isEmpty(riskId)) {
					throw new BusinessException("cmn.unknownError", null, "Error in retriving base riskId record_3");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getGacchCahDetailsID" + e);
		} finally {
			session.close();
		}
		return riskId;

	}

	public static List<String> getGacchCahID(PolicyVO policyVo, Long policyId) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Query query = null;
		List<String> resultsFunc = null;

		// Estimated Annual Carryings

		sqlQuery = "SELECT GCH_ID  FROM T_TRN_GACC_CASH_QUO   WHERE GCH_POLICY_ID =" + policyId + " And Gch_Endt_Id <="
				+ policyVo.getEndtId()
				+ " AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49'  AND GCH_BASIC_RSK_CODE = 42   AND GCH_RSK_CODE = 25    AND GCH_RT_CODE in ( 1,3,8) AND GCH_RC_CODE in (1,2,0)  And Gch_Rsc_Code = 0  AND GCH_STATUS <>4";
		try {
			query = session.createSQLQuery(sqlQuery);
			resultsFunc = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getGacchCahID" + e);
		} finally {
			session.close();
		}
		return resultsFunc;

	}

	public static void updateVED(PolicyVO policyVO, int classCode, int sectionId) {
		PASStoredProcedure sp = null;
		if (!Utils.isEmpty(policyVO.getNewEndtId()) && policyVO.getNewEndtId() != 0L) {

			if (policyVO.getIsQuote()) {
				sp = (PASStoredProcedure) Utils.getBean("updateValExpDateSecQuoWeb");
			} 

			try {
				Map resultsVED = sp.call(policyVO.getPolLinkingId(), policyVO.getNewEndtId(),
						policyVO.getNewValidityStartDate(), classCode, sectionId);
				if (Utils.isEmpty(resultsVED)) {
					LOGGER.debug("The result returned by the stored procedure is empt_1");
				}
			} catch (DataAccessException e) {
				throw new BusinessException("pas.convertTopolicy.exception", e,
						"An exception occured while executing stored proc_1");
			}
		}

	}

	

	public static void setPremiumAmount(PolicyVO policyVO) {
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(
					"update T_TRN_POLICY_QUO set POL_PREMIUM=0,POL_VAT_AMOUNT =0, POL_VATABLE_PRM=0 where POL_QUOTATION_NO =:quotationNo and POL_ENDT_ID=:endtId and POL_VALIDITY_EXPIRY_DATE='31-DEC-2049' AND POL_STATUS<>4");
			query.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
			query.setParameter("endtId", policyVO.getEndtId());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in setPremiumAmount" + e);
		} finally {
			session.close();
		}
	}
	
public static void setPremiumVED(PolicyVO policyVO) {
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(
					"update T_TRN_PREMIUM_QUO set PRM_VALIDITY_EXPIRY_DATE =:prmValidityStartDate where   PRM_ENDT_ID<:endtId"
							+ " AND PRM_POLICY_ID IN (SELECT POl_POLICY_ID FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO=:quotationNo and POL_ISSUE_HOUR = 3) and PRM_VALIDITY_EXPIRY_DATE = '31-DEC-2049'");
			query.setParameter("prmValidityStartDate", policyVO.getValidityStartDate());
			
			query.setParameter("endtId", policyVO.getEndtId());
			query.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in setPremiumVED" + e);
		} finally {
			session.close();
		}
	}
	
	
	public static void updateStatus(PolicyVO policyVO) {
		PASStoredProcedure sp = null;
		if (!Utils.isEmpty(policyVO.getEndtId()) && !Utils.isEmpty(policyVO.getPolLinkingId())) {

			HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
			Session session = ht.getSessionFactory().openSession();
			if (policyVO.getIsQuote()) {
				sp = (PASStoredProcedure) Utils.getBean("updateQuoteTableStatus");
			} 

			try {
				Map resultsVED = sp.call(policyVO.getPolLinkingId(), policyVO.getEndtId());
				if (Utils.isEmpty(resultsVED)) {
					LOGGER.debug("The result returned by the stored procedure is empt_2");
				}
				session.flush();
			} catch (DataAccessException e) {
				throw new BusinessException("pas.convertTopolicy.exception", e,
						"An exception occured while executing stored proc_2");
			}
		}

	}
	public void getPolicyREquiredData(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		try {
			// Changes-Adv#-10698 -JLT adding pol_issue_hour
			Query endtIDTransquery = session.createSQLQuery(
					"SELECT MAX(pol_endt_id) FROM T_Trn_Policy_Quo where  pol_quotation_no=:quotationNo AND pol_issue_hour = 3 AND POL_PREPARED_BY=:preparedBy");
			endtIDTransquery.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
			endtIDTransquery.setParameter("preparedBy", Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
			List<Object> results = endtIDTransquery.list();
			String maxEndtIDFromTrans = null;
			if (!Utils.isEmpty(results)) {
				maxEndtIDFromTrans = String.valueOf(results.get(0).toString());
			}
			// Changes-Adv#-10698 -JLT adding pol_issue_hour
			Query statusTransquery = session.createSQLQuery(
					"SELECT distinct pol_status, pol_linking_id,pol_document_code FROM T_Trn_Policy_Quo where  pol_quotation_no=:quotationNo and  pol_endt_id=:maxEndtIDFromTrans AND pol_issue_hour = 3 and POL_PREPARED_BY=:preparedBy");
			statusTransquery.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
			statusTransquery.setParameter("maxEndtIDFromTrans", Long.parseLong(maxEndtIDFromTrans));
			statusTransquery.setParameter("preparedBy", Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
			List<Object> resultList = statusTransquery.list();
			Object[] obj = (Object[]) resultList.get(0);
			int polStatusFromTrans = ((BigDecimal) obj[0]).intValue();
			Long linkingId = ((BigDecimal) obj[1]).longValue();

			if (!Utils.isEmpty(policyVO.getAuthInfoVO())) {
				policyVO.getAuthInfoVO().setTxnType(((BigDecimal) obj[2]).intValue());
			} else {
				policyVO.setAuthInfoVO(new AuthenticationInfoVO());
				policyVO.getAuthInfoVO().setTxnType(((BigDecimal) obj[2]).intValue());
			}
			policyVO.setStatus(polStatusFromTrans);
			policyVO.setPolLinkingId(linkingId);
			policyVO.setEndtId(Long.parseLong(maxEndtIDFromTrans));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getPolicyREquiredData" + e);
		} finally {
			session.close();
		}
	}


	public boolean checkQuotaionNo(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<Object> results = null;
		try {
			// Changes-Adv#-10698 -JLT adding pol_issue_hour
			Query quoteNoTran = session.createSQLQuery(
					"SELECT pol_quotation_no FROM T_Trn_Policy_Quo where  pol_quotation_no=:quotationNo AND pol_issue_hour = 3 and POL_PREPARED_BY=:preparedBy");
			quoteNoTran.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
			quoteNoTran.setParameter("preparedBy", Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
			results = quoteNoTran.list();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in checkQuotaionNo" + e);
		} finally {
			session.close();
		}
		if (Utils.isEmpty(results)) {
			return true;
		}
		return false;
	}

	public Double getPremiumWithVat(Long quotationNo, Double premiumAmt) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Double premiumFromDB = 0.0;
		try {
			Query query = session.createSQLQuery("Select Pol_Premium, Pol_Vat_Amount From Table (Cast(GET_QUOTE_DATE("
					+ quotationNo + ") As Quo_Record_Obj_Array))");
			List<Object[]> resultList = query.list();
			LOGGER.debug("results : " + resultList);
			
			
			for(Object[] result:resultList){
			//Mapping Total Premium
			if((!Utils.isEmpty(result[0]) && ((BigDecimal)result[0]).doubleValue() != 0))
				premiumFromDB = premiumFromDB + (((BigDecimal)result[0]).doubleValue());
			
			//Mapping VAT
			if((!Utils.isEmpty(result[1]) && ((BigDecimal)result[1]).doubleValue() != 0))
				premiumFromDB = premiumFromDB + (((BigDecimal)result[1]).doubleValue());
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getPremiumWithVat" + e);
		} finally {
			session.close();
		}
		return premiumFromDB;
	}
	public static void deleteSections(PolicyVO policyVO) {
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session
					.createSQLQuery("Delete from t_trn_policy_sections_quo where Tps_linking_id=:linkingId  ");
			query.setParameter("linkingId", policyVO.getPolLinkingId());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in deleteSections" + e);
		} finally {
			session.close();
		}
	}
	
	public static void deletePreviuosReferralsForAPI(PolicyVO policyVO) {
		LOGGER.debug("Entered deletePreviuosReferralsForAPI() ");
		LOGGER.debug("prev version quote POL LINKING ID : " + policyVO.getPolLinkingId());
		LOGGER.debug("prev version quote POL ENDT ID : " + policyVO.getEndtId());
		LOGGER.debug("prev version quote QUOTE NO: " + policyVO.getQuoteNo());
		String tskDocIdTobeDeleted = policyVO.getPolLinkingId() + "-" + policyVO.getEndtId() + "-"
				+ policyVO.getQuoteNo();
		LOGGER.debug("tskDocIdTobeDeleted : " + tskDocIdTobeDeleted);
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		try {
			Query query1 = session
					.createSQLQuery("Delete from t_trn_task where tsk_document_id=:tskDocIdTobeDeleted  ");
			query1.setParameter("tskDocIdTobeDeleted", tskDocIdTobeDeleted);
			query1.executeUpdate();
			// Changes-Adv#-10698 -JLT adding pol_issue_hour
			Query query2 = session.createSQLQuery(
					"Delete from t_trn_policy_comments where POC_POLICY_ID in (select pol_policy_id from t_trn_policy_quo where pol_quotation_no =:quoteNo and pol_issue_hour=3) ");
			query2.setParameter("quoteNo", policyVO.getQuoteNo());
			query2.executeUpdate();

			Query query3 = session.createSQLQuery(
					"update t_trn_policy_quo set pol_status=1 where pol_quotation_no=:quoteNo and pol_linking_id=:polLinkingID and pol_endt_id=:polEndtID and pol_issue_hour=3 ");
			query3.setParameter("quoteNo", policyVO.getQuoteNo());
			query3.setParameter("polLinkingID", policyVO.getPolLinkingId());
			query3.setParameter("polEndtID", policyVO.getEndtId());

			query3.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in deleteSections" + e);
		} finally {
			session.close();
		}
	}
	
	public static void invalidateRecord(PolicyVO policyVO) {
		PASStoredProcedure sp = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
			if (policyVO.getIsQuote()) {
				sp = (PASStoredProcedure) Utils.getBean("updateValExpDateSecQuoWebCurrEndt");
			} 

			try {
				Map resultsVED = sp.call(policyVO.getPolLinkingId());
				if (Utils.isEmpty(resultsVED)) {
					LOGGER.debug("The result returned by the stored procedure is empt_3");
				}
			} catch (DataAccessException e) {
				session.flush();
				throw new BusinessException("pas.convertTopolicy.exception", e,
						"An exception occured while executing stored proc_3");
			}
			session.flush();
			session.close();
	}
	
	public static PolicyVO getPolicyVOFromStaging(Long polQuotationNo) {

		List<EplatformWsStaging> staging = null;
		PolicyVO  policyVO = new PolicyVO();
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		
		ObjectInputStream ois = null;
		try {
			staging = ht.find("from EplatformWsStaging where polQuotationNo = ? ", polQuotationNo);
			Blob blob = staging.get(0).getQuoIntrResponseAdd();
			ois = new ObjectInputStream(blob.getBinaryStream());
			policyVO = (PolicyVO) ois.readObject();
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			session.close();
		}
		return policyVO;
	}
	
	public static EplatformWsStaging getPolicyRecordFromStaging(Long polQuotationNo, Long polEndtId) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		EplatformWsStaging eplatformWsStaging = null;
		try {
			eplatformWsStaging = (EplatformWsStaging) session
					.createQuery("from EplatformWsStaging stg" + "   where stg.polQuotationNo =:quotation"
							+ " and stg.id.polEndtId =:endorsementNo")
					.setLong("quotation", polQuotationNo).setLong("endorsementNo", polEndtId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("2. Error in getPolicyRecordFromStaging" + e);
		} finally {
			session.close();
		}
		return eplatformWsStaging;	

	}

	
	public static Boolean checkIfPreviosBatchIsRunning(Long polQuotationNo) {
		Boolean flag = false;
		WebServiceAudit webServiceAudit = getRecordFromAuditTable(polQuotationNo, "In progress" );
		if(webServiceAudit !=null ) {
			flag = true;
		}	
		return flag;
	}
	
	
	public static WebServiceAudit getRecordFromAuditTable(Long polQuotationNo, String responseType) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		List<WebServiceAudit> webServiceAudits = ht.find("from WebServiceAudit where twa_quote_no = " + polQuotationNo
				+ " and twa_transaction_res_type = '" + responseType + "'");
		if (webServiceAudits != null && !Utils.isEmpty(webServiceAudits)) {
			/*
			 * for(WebServiceAudit webServiceAudit : webServiceAudits) {
			 * if(webServiceAudit.getTwa_transaction_res_type().
			 * equalsIgnoreCase("In progress")) { return webServiceAudit; } }
			 */
			return webServiceAudits.get(0);
		}
		return null;
		
	}
	
	public static Boolean checkPreviousRequestStatus(Long polQuotationNo) {
		Boolean flag = false;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		List<WebServiceAudit> webServiceAudits = ht.find("from WebServiceAudit where twa_quote_no = " + polQuotationNo
				+ " " + "and twa_transaction_req_type = 'CreateSBSPolicy' and twa_transaction_res_type = null");
		if (webServiceAudits != null && !Utils.isEmpty(webServiceAudits)) {
			flag = true;
		}
		return flag;
	}
	
	public static CreateSBSPolicyResponse fetchPolicyData(CreateSBSPolicyRequest createSBSPolicyRequest) {
		CreateSBSPolicyResponse createSBSPolicyResponse = new CreateSBSPolicyResponse();
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(
					"Select Pol_policy_id , pol_policy_no , pol_conc_policy_no,pol_policy_year from t_trn_policy where pol_quotation_no = "
							+ createSBSPolicyRequest.getQuoteId() + " and pol_issue_hour = 3");
			List<Object[]> resultList = query.list();
			createSBSPolicyResponse.setPolicyId(resultList.get(0)[1].toString());
			Query query1 = session
					.createSQLQuery("Select csh_customer_id from t_mas_cash_customer where csh_policy_id = "
							+ resultList.get(0)[0].toString());
			List<Object[]> resultList1 = query1.list();
			String s = "" + resultList1.get(0);
			createSBSPolicyResponse.setCustomerId(s);

			/*
			 * Added for JLT Renewal Scope #11424
			 */

			if (!Utils.isEmpty(resultList.get(0)[3])) {
				createSBSPolicyResponse.setPolicyYear(Integer.parseInt(resultList.get(0)[3].toString()));
			}
			PolicyVO policyVO = new PolicyVO();
			policyVO.setPolicyNo(Long.parseLong(resultList.get(0)[1].toString()));
			policyVO.setPolicyYear(Short.parseShort(resultList.get(0)[3].toString()));
			// Get Document List
			DocumentHandler documentHandler = new DocumentHandler();
			List<Document> documentList = documentHandler.getDocumentList(policyVO);
			createSBSPolicyResponse.setDocumentId(documentList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("3. Error in getPolicyRecordFromStaging" + e);
		} finally {
			session.close();
		}
		return createSBSPolicyResponse;
	}

	public static ArrayList<Double> getPremiumFromPolicyAndPremiumTable(Long polQuotationNo) {
		ArrayList<Double> premiums = new ArrayList<Double>();
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
		Session session = ht.getSessionFactory().openSession();
		try {
			// Changes-Adv#-10698 -JLT adding pol_issue_hour
			Query policyPrm = session.createSQLQuery(
					"SELECT pol_premium, pol_vat_amount, pol_policy_id FROM T_Trn_Policy_Quo where  pol_issue_hour = 3 and pol_validity_expiry_date = '31-DEC-49' and pol_quotation_no=:quotationNo ");
			policyPrm.setParameter("quotationNo", polQuotationNo);
			List<Object> resultList = policyPrm.list();
			Object[] obj = (Object[]) resultList.get(0);
			Double polPremium = ((BigDecimal) obj[0]).doubleValue();
			Double vatAmount = ((BigDecimal) obj[1]).doubleValue();
			Long policyId = ((BigDecimal) obj[2]).longValue();
			Double quotationTablePremium = polPremium + vatAmount;
			premiums.add(quotationTablePremium);

			Query premiumPrm = session.createSQLQuery(
					"SELECT sum(prm_premium) FROM T_Trn_Premium_Quo where  prm_policy_id =:prmPolicyId and prm_validity_expiry_date = '31-DEC-49' ");
			premiumPrm.setParameter("prmPolicyId", policyId);
			List<Object> resultList1 = premiumPrm.list();
			Double premiumQuoPremium = null;
			if (!Utils.isEmpty(resultList1)) {
				premiumQuoPremium = Double.parseDouble(String.valueOf(resultList1.get(0).toString()));
			} else {
				premiumQuoPremium = Double.parseDouble("0");
			}
			premiums.add(premiumQuoPremium);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("WSDAOUtils : error while fetching premium from policy and premium table " + e.getMessage());
			throw new BusinessException("SQL Error", null,
					"Error while fetching premium from policy and premium table. Please contact system administrator");
		} finally {
			session.close();
		}
		return premiums;
	}

	/*
	 * Added for JLT Renewal
	 */
	@SuppressWarnings("unchecked")
	public static List<TTrnPolicyQuo> getRenewalQuoteFromPolicyNo(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<TTrnPolicyQuo> policy = null;
		// expiryPolicyYear=(short) (expiryPolicyYear-1);
		// String.valueOf(SvcUtils.getYearFromDate(policy.getPolEffectiveDate()))

		try {

			policy = ht.find(
					"from TTrnPolicyQuo where polRefPolicyNo = ? and  polValidityExpiryDate = ? and polRefPolicyYear = ? and polIssueHour=3 and polPreparedBy=?",
					policyVO.getPolicyNo(), SvcConstants.EXP_DATE, policyVO.getPolicyYear(),
					Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getPolicyRecord" + e);
		} finally {
			session.close();
		}
		return policy;

	}

	@SuppressWarnings("unchecked")
	public static List<EplatformWsStaging> getMaxEndIdFromStaging(Long quotationNo) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<EplatformWsStaging> wsStaging = null;
		try {

			wsStaging = ht.find("from EplatformWsStaging where polQuotationNo = ? order by id desc", quotationNo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getPolicyRecord" + e);
		} finally {
			session.close();
		}
		return wsStaging;
	}

	@SuppressWarnings("unchecked")
	public static List<TTrnPolicyQuo> getPolicyDetails(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<TTrnPolicyQuo> policy = null;
		Long policyNo = new Long(0L);
		if (!Utils.isEmpty(policyVO.getAuthInfoVO()) && !Utils.isEmpty(policyVO.getAuthInfoVO().getRefPolicyNo())) {

			policyNo = policyVO.getAuthInfoVO().getRefPolicyNo();
		} else if (!Utils.isEmpty(policyVO.getPolicyNo())) {
			policyNo = policyVO.getPolicyNo();
		}
		try {

			String sql = "SELECT * FROM T_TRN_POLICY WHERE POL_POLICY_NO = :policyNo AND POL_VALIDITY_EXPIRY_DATE = :expiryDate AND POL_ISSUE_HOUR=3 AND POL_PREPARED_BY=:preparedBy ORDER BY POL_LINKING_ID DESC";
			SQLQuery query = session.createSQLQuery(sql);

			query.addEntity(TTrnPolicyQuo.class);
			query.setParameter("policyNo", policyNo);
			query.setParameter("expiryDate", SvcConstants.EXP_DATE);
			query.setParameter("preparedBy", Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
			policy = query.list();
			LOGGER.debug("results size = " + policy.size());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getting Policy Details" + e);
		} finally {
			session.close();
		}
		return policy;

	}

	@SuppressWarnings("unchecked")
	public static List<TMasCashCustomerQuo> getClaimInformation(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<TMasCashCustomerQuo> tMasCashCustomerQuos = null;

		try {

			tMasCashCustomerQuos = ht.find(
					"from TMasCashCustomerQuo where id.cshPolicyId = ? and  cshValidityExpiryDate = ?",
					policyVO.getBasePolicyId(), SvcConstants.EXP_DATE);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getting cah customer details" + e);
		} finally {
			session.close();
		}
		return tMasCashCustomerQuos;

	}

	public static List<TTrnPolicyQuo> isValidQuote(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<TTrnPolicyQuo> policy = null;
		policy = ht.find(
				"from TTrnPolicyQuo where polQuotationNo = ? and  polValidityExpiryDate = ?  and polPreparedBy=?  and polIssueHour=3 order by polClassCode",
				policyVO.getQuoteNo(), SvcConstants.EXP_DATE, Integer.parseInt(policyVO.getLoggedInUser().getUserId()));

		return policy;
	}

	@SuppressWarnings("unchecked")
	public static List<TTrnPolicyQuo> getPolicyDetailsByPolicyNoAndYear(PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		List<TTrnPolicyQuo> policy = null;
		// CTS - 13.10.2020 - JLT UAT Change - Get Document list for JLT quote
		// converted to policy in B2B - Starts
		boolean isJLTQuote = false;
		isJLTQuote = checkForJLTQuote(policyVO.getPolicyNo(), policyVO.getPolicyYear().toString());
		if (isJLTQuote) {
			// CTS - 13.10.2020 - JLT UAT Change - Get Document list for JLT
			// quote converted to policy in B2B - Ends
			try {

				String sql = "SELECT * FROM T_TRN_POLICY WHERE POL_POLICY_NO = :policyNo AND POL_POLICY_YEAR = :policyYear AND POL_VALIDITY_EXPIRY_DATE = :expiryDate AND POL_ISSUE_HOUR=3  ORDER BY POL_LINKING_ID DESC";
				SQLQuery query = session.createSQLQuery(sql);

				query.addEntity(TTrnPolicyQuo.class);
				query.setParameter("policyNo", policyVO.getPolicyNo());
				query.setParameter("policyYear", policyVO.getPolicyYear());
				query.setParameter("expiryDate", SvcConstants.EXP_DATE);

				policy = query.list();
				LOGGER.debug("results size = " + policy.size());
			}

			catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("Error in getting Policy Details" + e);
			} finally {
				session.close();
			}
		}
		return policy;
	}

	// CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the
	// change in name between base policy and renewal quote -- Starts
	@SuppressWarnings("unchecked")
	public static HashMap<String, List<String>> getnameflagdetails(Long policyNo, int riskcode, String policyYear) {
		Long PolicyId = getPolicyIdrenewal(policyNo, policyYear);
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		HashMap<String, List<String>> namedflag = new HashMap<String, List<String>>();
		try {

			String sql = "select gac.gpr_e_name as NEW_NAME,  "
					+ "(Select gcr.gpr_e_name from t_trn_gacc_person gcr where gcr.gpr_policy_id = :policyId and gcr.gpr_rsk_code= :riskcode and gcr.gpr_endt_id=0 and gac.gpr_id = gcr.gpr_id) as OLD_NAME, "
					+ " case" + " when gac.gpr_id not in (select gcr.gpr_id from t_trn_gacc_person gcr"
					+ " where gcr.gpr_policy_id= :policyId and gcr.gpr_rsk_code= :riskcode and gcr.gpr_endt_id=0) and"
					+ " gac.GPR_STATUS <> 4" + " then 'New'"
					+ " when (gac.gpr_id in (select gcr.gpr_id from t_trn_gacc_person gcr"
					+ " where gcr.gpr_policy_id= :policyId and gcr.gpr_rsk_code= :riskcode and gcr.gpr_endt_id=0 and"
					+ " upper(trim((gac.gpr_e_name))) = upper(trim((gcr.gpr_e_name))))  and" + " GPR_STATUS <> 4)"
					+ " then 'No'" + " when (gac.gpr_id in (select gcr.gpr_id from t_trn_gacc_person gcr"
					+ " where gcr.gpr_policy_id= :policyId and gcr.gpr_rsk_code= :riskcode and gcr.gpr_endt_id=0 and"
					+ " upper(trim((gac.gpr_e_name))) <> upper(trim((gcr.gpr_e_name)))  and" + " GPR_STATUS <> 4))"
					+ " then 'Yes'" + " when (gac.GPR_STATUS = 4)" + " then 'Remove'" + " end Nameflag"
					+ " from  t_trn_gacc_person gac where gpr_policy_id= :policyId and gpr_rsk_code= :riskcode"
					+ " and  gpr_validity_expiry_date =to_date('31-DEC-2049','DD-MON-YYYY') and GPR_STATUS = 1";

			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("policyId", PolicyId);
			query.setParameter("riskcode", riskcode);

			List<Object[]> result = query.list();
			if (!Utils.isEmpty(result)) {
				for (Object[] obj : result) {
					List<String> oldNameAndFlagList = new ArrayList<String>();
					if (obj[1] != null)
						oldNameAndFlagList.add(obj[1].toString().trim());
					else
						oldNameAndFlagList.add("");
					oldNameAndFlagList.add(obj[2].toString());
					namedflag.put(obj[0].toString().toUpperCase().trim(), oldNameAndFlagList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getting Policy Details" + e);
		} finally {
			session.close();
		}
		return namedflag;
	}

	public static Long getPolicyIdrenewal(Long policyNo, String policyYear) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long policyId = null;

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		String policyType = Utils.getSingleValueAppConfig("SBS_Policy_Type");
		String classCode = Utils.getSingleValueAppConfig("FIDELITY_CLASS"); // Class
																			// code
																			// same
																			// for
																			// Travel
																			// baggage,Fidelity
																			// and
																			// GPA
		try {
			sqlQuery = "Select Pol_Policy_Id From T_Trn_Policy Where pol_policy_no = :policyNo AND  Pol_Validity_Expiry_Date = '31-DEC-2049'"
					+ "  AND pol_issue_hour = 3 AND  POL_CLASS_CODE= :classCode AND POL_POLICY_YEAR = :policyYear AND POL_POLICY_TYPE = :policyType";

			Query query = session.createSQLQuery(sqlQuery);
			query.setParameter("policyNo", policyNo);
			query.setParameter("classCode", classCode);
			query.setParameter("policyYear", policyYear);
			query.setParameter("policyType", policyType);

			List<Object> resultsFunc = query.list();
			if (!Utils.isEmpty(resultsFunc)) {
				policyId = Long.valueOf(resultsFunc.get(0).toString());
				if (Utils.isEmpty(policyId)) {
					policyId = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getPolicyIds" + e);
		} finally {
			session.close();
		}
		return policyId;

	}
	// CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the
	// change in name between base policy and renewal quote -- Ends

	// CTS - 13.10.2020 - JLT UAT Change - Get Document list for JLT quote
	// converted to policy in B2B - Starts
	public static boolean checkForJLTQuote(Long policyNo, String policyYear) {
		boolean isJLTQuote = false;
		List<Object> polPreparedByList = DAOUtils.getSqlResultSingleColumnPas(QueryConstants.CHECK_JLT_QUOTE, policyNo,
				policyYear);
		if(!Utils.isEmpty(polPreparedByList)){
		String polPrepareBy = polPreparedByList.get(0).toString();
		if (polPrepareBy.equals(Utils.getSingleValueAppConfig("JLT_USER_ID").trim())) {
			isJLTQuote = true;
		}
		}

		return isJLTQuote;
	}
	// CTS - 13.10.2020 - JLT UAT Change - Get Document list for JLT quote
	// converted to policy in B2B - Ends

}
