package com.rsaame.pas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.EplatformWsStaging;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

public class WSDAOUtils {
	private final static Logger LOGGER = Logger.getLogger(WSDAOUtils.class);
	private final static String QUOTE_SEQ_SBS = "SEQ_SBS_QUO_NO";
	private final static String INSUREDID_SEQ_SBS = "SEQ_INSURED_ID";
	private static String DEFAULT_ENDT_ID = "DEFAULT_ENDTID_QUOTE";

	public static Boolean getInsuredCheckForPLQuote(String insEFirstName, String insEZipCode, String polPolicyType)
			throws IllegalAccessException {
		LOGGER.info("Enter getInsuredCheckForPLQuote ");
		String isInsuredExists = null;
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		try {
			String sqlQuery = "SELECT PKG_PAS_UTILS.CHECK_IF_USER_HAS_PL_QUOTE('" + insEFirstName + "','" + insEZipCode
					+ "', " + polPolicyType + com.Constant.CONST_FROM_DUAL;

			Session session = hibernateTemplate.getSessionFactory().openSession();

			Query query = session.createSQLQuery(sqlQuery);
			List<Object> results = query.list();
			if (!Utils.isEmpty(results)) {
				isInsuredExists = results.get(0).toString();
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error in getInsuredCheckForPLQuote" + e);
		}

		if (isInsuredExists.equalsIgnoreCase("yes")) {
			return true;
		} else {
			return false;
		}
	}

	public static Integer defaultAppover(PolicyVO policyVo) {
		// TODO Auto-generated method stub

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		String sqlQuery = "select sch_default_approver  from T_mas_scheme  where SCH_CODE="
				+ policyVo.getScheme().getSchemeCode() + "   and SCH_BROKER_CODE="
				+ policyVo.getGeneralInfo().getSourceOfBus().getPartnerId();
		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sqlQuery);
		Object result = (query.list()).get(0);
		session.close();
		return ((BigDecimal) result).intValue();

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

	public static List<TTrnPolicyQuo> getPolicyRecord(Long polQuotationNo) {

		List<TTrnPolicyQuo> policy = null;
		/*
		 * fetch all valid policy records for nil endt.
		 */
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		policy = ht.find(
				"from TTrnPolicyQuo where polQuotationNo = ? and  polValidityExpiryDate = ? and polIssueHour=3 order by polClassCode",
				polQuotationNo, SvcConstants.EXP_DATE);
		session.close();
		return policy;

	}

	public static List<EplatformWsStaging> getPolicyRecordFromStaging(Long polQuotationNo) {

		List<EplatformWsStaging> staging = null;

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		staging = ht.find("from EplatformWsStaging where polQuotationNo = ? ", polQuotationNo);
		// session.close();

		return staging;

	}

	public static PolicyVO getBaseSecPolicyId(PolicyVO policyVO) {

		Long endtId = getLatestEndtId(policyVO);
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		String sqlQuery = "";

		if (policyVO.getIsQuote()) {
			sqlQuery = "SELECT PKG_PAS_UTILS.get_base_sec_pol_id_quo(" + policyVO.getPolLinkingId() + "," + endtId
					+ com.Constant.CONST_FROM_DUAL;
		} else {
			sqlQuery = "SELECT PKG_ENDT_GEN.get_base_sec_pol_id(" + policyVO.getPolLinkingId() + "," + endtId
					+ com.Constant.CONST_FROM_DUAL;
		}

		Session session = ht.getSessionFactory().openSession();
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
		}
		session.close();
		policyVO.getGeneralInfo().getAdditionalInfo().setPolicyId(policyId);
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
		int baseSecId = getBaseSecPolicy(policyVo);
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		if (baseSecId == 0) {
			sqlQuery = "Select  Bld_Id ,bld_e_name  From T_Trn_Building_Quo   Where Bld_Policy_Id In (Select pol_policy_id From T_Trn_Policy_Quo Where Pol_Quotation_No ="
					+ policyVo.getQuoteNo() + "   and pol_endt_id <=" + policyVo.getEndtId()
					+ " AND  Pol_Validity_Expiry_Date = '31-DEC-2049')  and bld_endt_id <=  " + policyVo.getEndtId()
					+ " and bld_validity_expiry_date ='31-DEC-2049' and bld_status<>4  ";
		} else {

			sqlQuery = "SELECT wbd_id,wbd_e_name From T_Trn_Wctpl_Premise_Quo WHERE  wbd_policy_id IN "
					+ " (SELECT pol_policy_id  FROM T_Trn_Policy_Quo   WHERE Pol_Quotation_No =" + policyVo.getQuoteNo()
					+ " and pol_endt_id <=" + policyVo.getEndtId() + " AND  Pol_Validity_Expiry_Date = '31-DEC-2049') "
					+ " And Wbd_Endt_Id <= " + policyVo.getEndtId()
					+ " And wbd_Validity_Expiry_Date ='31-DEC-2049' AND wbd_status <>4";

		}

		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sqlQuery);
		// Object result = (query.list()).get(0);
		List<Object[]> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			// result = Integer.valueOf(resultsFunc.get(0).toString());
		}
		session.close();
		return resultsFunc;

	}

	// to get base section

	private static int getBaseSecPolicy(PolicyVO policyVO) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		String sqlQuery = "";

		if (policyVO.getIsQuote()) {
			sqlQuery = "SELECT PKG_PAS_UTILS.get_is_base(" + policyVO.getPolLinkingId() + " , 'Q'  ) FROM DUAL";
		} else {
			sqlQuery = "SELECT PKG_PAS_UTILS.get_is_base(" + policyVO.getPolLinkingId() + " , 'P'  ) FROM DUAL";

		}

		Session session = ht.getSessionFactory().openSession();
		Integer baseSection = null;
		Query query = session.createSQLQuery(sqlQuery);
		session.flush();
		List<Object> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			baseSection = Integer.valueOf(resultsFunc.get(0).toString());
			if (Utils.isEmpty(baseSection)) {
				throw new BusinessException("cmn.unknownError", null, "Error in retriving base section record ");
			}
		}
		session.close();

		return baseSection;
	}

	public static List<String> contentId(PolicyVO policyVO, int sectionId) {
		String sqlQuery = "";
		Integer contentId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

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

		Session session = ht.getSessionFactory().openSession();

		Query query = session.createSQLQuery(sqlQuery);
		List<String> resultsFunc = query.list();
		/*
		 * if (!Utils.isEmpty(resultsFunc)) { //contentId =
		 * Integer.valueOf(resultsFunc.get(0).toString()); if
		 * (Utils.isEmpty(contentId)) { throw new
		 * BusinessException("cmn.unknownError", null,
		 * "Error in retriving base section record "); } }
		 */
		session.close();

		return resultsFunc;
	}

	public static Long getPolicyIds(PolicyVO policyVo, int classCode) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long policyId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "Select Pol_Policy_Id From T_Trn_Policy_Quo Where Pol_Quotation_No =" + policyVo.getQuoteNo()
				+ " AND  Pol_Validity_Expiry_Date = '31-DEC-2049' AND POL_status <>4 AND pol_issue_hour = 3 AND POL_CLASS_CODE="
				+ classCode;

		Session session = ht.getSessionFactory().openSession();

		Query query = session.createSQLQuery(sqlQuery);
		List<Object> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			policyId = Long.valueOf(resultsFunc.get(0).toString());
			if (Utils.isEmpty(policyId)) {
				policyId = null;
			}
		}
		session.close();

		return policyId;

	}

	public static List<Object[]> getCoverDetails(PolicyVO policyVo, Long policyId, int riskType) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "select prm_basic_rsk_id,prm_rt_code,prm_rsk_id FROM  T_Trn_Premium_Quo  where PRM_POLICY_ID ="
				+ policyId + " and prm_endt_id<=" + policyVo.getEndtId()
				+ " and prm_validity_expiry_date='31-DEC-2049' AND PRM_STATUS<>4  AND PRM_COV_CODE=1 AND PRM_RT_CODE = "
				+ riskType;
		// + "(9,999,13)";

		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> resultsFunc = query.list();

		session.close();
		return resultsFunc;

	}

	public static List<String> getunNameEMRiskId(PolicyVO policyVo, Long policyId) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		Long riskId = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "Select  WUP_ID From T_TRN_WCTPL_UNNAMED_PERSON_QUO  Where Wup_Policy_Id =" + policyId
				+ " AND  Wup_Validity_Expiry_Date = '31-DEC-2049' AND Wup_Status <>4";

		Session session = ht.getSessionFactory().openSession();

		Query query = session.createSQLQuery(sqlQuery);
		List<String> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			/*
			 * riskId = Long.valueOf(resultsFunc.get(0).toString()); if
			 * (Utils.isEmpty(riskId)) { throw new
			 * BusinessException("cmn.unknownError", null,
			 * "Error in retriving base policyId record "); }
			 */
		}
		session.close();

		return resultsFunc;

	}

	public static List<String> getGprId(PolicyVO policyVo, Long policyId, int riskCode) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "select GPR_ID  from T_TRN_GACC_PERSON_QUO  WHERE GPR_VALIDITY_EXPIRY_DATE='31-DEC-2049' AND GPR_STATUS <> 4 AND GPR_POLICY_ID="
				+ policyId + " and gpr_rsk_code=" + riskCode;

		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sqlQuery);
		ArrayList<String> resultsFunc = (ArrayList<String>) query.list();

		session.close();

		return resultsFunc;

	}

	public static List<String> getGupFidelityId(PolicyVO policyVo, Long policyId, int riskCode) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "select GPR_ID  from T_TRN_GACC_UNNAMED_PERSON_QUO  WHERE GUP_VALIDITY_EXPIRY_DATE='31-DEC-2049' AND GUP_STATUS <> 4 AND GUP_POLICY_ID="
				+ policyId + " and gup_rsk_code=" + riskCode;

		Session session = ht.getSessionFactory().openSession();

		Query query = session.createSQLQuery(sqlQuery);
		List<String> resultsFunc = query.list();

		session.close();

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

		Query query = session.createSQLQuery(sqlQuery);
		List<Object> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			riskId = Long.valueOf(resultsFunc.get(0).toString());
			if (Utils.isEmpty(riskId)) {
				throw new BusinessException("cmn.unknownError", null, "Error in retriving base riskId records");
			}
		}
		session.close();

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

		Query query = session.createSQLQuery(sqlQuery);
		List<Object> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			riskId = Long.valueOf(resultsFunc.get(0).toString());
			if (Utils.isEmpty(riskId)) {
				throw new BusinessException("cmn.unknownError", null, "Error in retriving base riskId record_2");
			}
		}
		session.close();

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

		Query query = session.createSQLQuery(sqlQuery);
		List<Object> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			riskId = Long.valueOf(resultsFunc.get(0).toString());
			if (Utils.isEmpty(riskId)) {
				throw new BusinessException("cmn.unknownError", null, "Error in retriving base riskId record_3");
			}
		}
		session.close();

		return riskId;

	}

	public static List<String> getGacchCahID(PolicyVO policyVo, Long policyId) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Query query = null;

		// Estimated Annual Carryings

		sqlQuery = "SELECT GCH_ID  FROM T_TRN_GACC_CASH_QUO   WHERE GCH_POLICY_ID =" + policyId + " And Gch_Endt_Id <="
				+ policyVo.getEndtId()
				+ " AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49'  AND GCH_BASIC_RSK_CODE = 42   AND GCH_RSK_CODE = 25    AND GCH_RT_CODE in ( 1,3,8) AND GCH_RC_CODE in (1,2,0)  And Gch_Rsc_Code = 0  AND GCH_STATUS <>4";

		query = session.createSQLQuery(sqlQuery);
		List<String> resultsFunc = query.list();

		/*
		 * // Cash in premises during business hours In locked safe sqlQuery =
		 * "SELECT GCH_ID  FROM T_TRN_GACC_CASH_QUO WHERE GCH_POLICY_ID = " +
		 * policyId + " And Gch_Endt_Id <=" + policyVo.getEndtId() +
		 * " AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49'  AND GCH_BASIC_RSK_CODE= 42  AND GCH_RSK_CODE= 25   AND GCH_RT_CODE = 3 "
		 * + " AND GCH_RC_CODE=0    AND GCH_RSC_CODE = 0   AND GCH_STATUS  <>4";
		 * query = session.createSQLQuery(sqlQuery); resultsFunc = query.list();
		 * 
		 * // Cash in premises during business hours In locked drawer sqlQuery =
		 * " SELECT GCH_ID   FROM T_TRN_GACC_CASH_QUO    WHERE GCH_POLICY_ID = "
		 * + policyId + " And Gch_Endt_Id <=" + policyVo.getEndtId() +
		 * "  AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND GCH_BASIC_RSK_CODE = 42   AND GCH_RSK_CODE = 25 "
		 * +
		 * "  AND GCH_RT_CODE = 8  AND GCH_RC_CODE   =0   AND GCH_RSC_CODE   = 0      AND GCH_STATUS  <>4"
		 * ; query = session.createSQLQuery(sqlQuery); resultsFunc =
		 * query.list();
		 * 
		 * // Cash in premises during business hours In locked safe sqlQuery =
		 * "  SELECT GCH_ID FROM T_TRN_GACC_CASH_QUO   WHERE GCH_POLICY_ID = " +
		 * policyId + " And Gch_Endt_Id <=" + policyVo.getEndtId() +
		 * "     AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49'  AND GCH_BASIC_RSK_CODE  = 42  AND GCH_RSK_CODE  = 25  AND GCH_RT_CODE  = 3  AND GCH_RC_CODE  =0     AND GCH_RSC_CODE= 0      AND GCH_STATUS <>4"
		 * ; query = session.createSQLQuery(sqlQuery); resultsFunc =
		 * query.list();
		 * 
		 * // Cash in premises during business hours In locked drawer sqlQuery =
		 * "  SELECT GCH_ID   FROM T_TRN_GACC_CASH_QUO   WHERE GCH_POLICY_ID= "
		 * + policyId + "  AND GCH_ENDT_ID             <= " +
		 * policyVo.getEndtId() +
		 * " AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49'   AND GCH_BASIC_RSK_CODE  = 42"
		 * + " AND GCH_RSK_CODE = 25   AND GCH_RT_CODE  = 8" +
		 * "  AND GCH_RC_CODE=0  AND GCH_RSC_CODE = 0  AND GCH_STATUS <>4";
		 * query = session.createSQLQuery(sqlQuery); resultsFunc = query.list();
		 * 
		 * // Cash in premises after business hours In locked safe sqlQuery =
		 * " SELECT GCH_ID   FROM T_TRN_GACC_CASH_QUO WHERE GCH_POLICY_ID =" +
		 * policyId + " And Gch_Endt_Id <=" + policyVo.getEndtId() +
		 * "  AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49'  AND GCH_BASIC_RSK_CODE  = 42  AND GCH_RSK_CODE   = 25"
		 * +
		 * "  AND GCH_RT_CODE  = 2  AND GCH_RC_CODE   =1  AND GCH_RSC_CODE  = 0 AND GCH_STATUS  <>4"
		 * ; query = session.createSQLQuery(sqlQuery); resultsFunc =
		 * query.list();
		 * 
		 * // Cash in premises after business hours In locked drawer sqlQuery =
		 * " SELECT GCH_ID   FROM T_TRN_GACC_CASH_QUO   WHERE GCH_POLICY_ID  ="
		 * + policyId + " AND GCH_ENDT_ID             <= " +
		 * policyVo.getEndtId() +
		 * " AND GCH_VALIDITY_EXPIRY_DATE = '31-DEC-49'    AND GCH_BASIC_RSK_CODE  = 42"
		 * +
		 * "  AND GCH_RSK_CODE   = 25     AND GCH_RT_CODE    = 2  AND GCH_RC_CODE    =3    AND GCH_RSC_CODE    = 0        AND GCH_STATUS       <>4"
		 * ;
		 */
		query = session.createSQLQuery(sqlQuery);
		resultsFunc = query.list();

		session.close();

		return resultsFunc;

	}

	public static void updateVED(PolicyVO policyVO, int classCode, int sectionId) {
		PASStoredProcedure sp = null;
		if (!Utils.isEmpty(policyVO.getNewEndtId()) && policyVO.getNewEndtId() != 0L) {

			if (policyVO.getIsQuote()) {
				sp = (PASStoredProcedure) Utils.getBean("updateValExpDateSecQuoOnDemand");
			}

			try {
				Map resultsVED = sp.call(policyVO.getPolLinkingId(), policyVO.getNewEndtId(),
						policyVO.getNewValidityStartDate(), classCode, sectionId);
				if (Utils.isEmpty(resultsVED)) {
					LOGGER.debug("The result returned by the stored procedure is empty");
				}
			} catch (DataAccessException e) {
				throw new BusinessException("pas.convertTopolicy.exception", e,
						"An exception occured while executing stored proc.");
			}
		}

	}

	public static void setPremiumAmount(PolicyVO policyVO) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery(
				"update T_TRN_POLICY_QUO set POL_PREMIUM=0,POL_VAT_AMOUNT =0, POL_VATABLE_PRM=0 where POL_QUOTATION_NO =:quotationNo and POL_ENDT_ID=:endtId and POL_VALIDITY_EXPIRY_DATE='31-DEC-2049' AND POL_STATUS<>4 AND pol_issue_hour = 3");
		query.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
		query.setParameter("endtId", policyVO.getEndtId());
		query.executeUpdate();
		session.close();
	}

	public static void setPremiumVED(PolicyVO policyVO) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery(
				"update T_TRN_PREMIUM_QUO set PRM_VALIDITY_EXPIRY_DATE =:prmValidityStartDate where   PRM_ENDT_ID<:endtId"
						+ " AND PRM_POLICY_ID IN (SELECT POl_POLICY_ID FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO=:quotationNo and POL_ISSUE_HOUR = 3) and PRM_VALIDITY_EXPIRY_DATE = '31-DEC-2049'");
		query.setParameter("prmValidityStartDate", policyVO.getValidityStartDate());
		query.setParameter("endtId", policyVO.getEndtId());
		query.setParameter(com.Constant.CONST_QUOTATIONNO, policyVO.getQuoteNo());
		query.executeUpdate();
		session.close();
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

	public void getPolicyREquiredData(Long quotationNo, PolicyVO policyVO) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();

		Query endtIDTransquery = session.createSQLQuery(
				"SELECT MAX(pol_endt_id) FROM T_Trn_Policy_Quo where  pol_quotation_no=:quotationNo AND pol_issue_hour = 3");
		endtIDTransquery.setParameter(com.Constant.CONST_QUOTATIONNO, quotationNo);
		List<Object> results = endtIDTransquery.list();
		String maxEndtIDFromTrans = null;
		if (!Utils.isEmpty(results)) {
			maxEndtIDFromTrans = String.valueOf(results.get(0).toString());
		}

		Query statusTransquery = session.createSQLQuery(
				"SELECT distinct pol_status, pol_linking_id FROM T_Trn_Policy_Quo where  pol_quotation_no=:quotationNo and  pol_endt_id=:maxEndtIDFromTrans AND pol_issue_hour = 3");
		statusTransquery.setParameter(com.Constant.CONST_QUOTATIONNO, quotationNo);
		statusTransquery.setParameter("maxEndtIDFromTrans", Long.parseLong(maxEndtIDFromTrans));
		List<Object> resultList = statusTransquery.list();
		Object[] obj = (Object[]) resultList.get(0);
		int polStatusFromTrans = ((BigDecimal) obj[0]).intValue();
		Long linkingId = ((BigDecimal) obj[1]).longValue();

		policyVO.setStatus(polStatusFromTrans);
		policyVO.setPolLinkingId(linkingId);
		policyVO.setEndtId(Long.parseLong(maxEndtIDFromTrans));
	}

	public boolean checkQuotaionNo(Long quotationNo) {
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();

		Query quoteNoTran = session.createSQLQuery(
				"SELECT pol_quotation_no FROM T_Trn_Policy_Quo where  pol_quotation_no=:quotationNo AND pol_issue_hour = 3");
		quoteNoTran.setParameter(com.Constant.CONST_QUOTATIONNO, quotationNo);
		List<Object> results = quoteNoTran.list();
		if (Utils.isEmpty(results)) {
			return true;
		}
		return false;
	}

	public Double getPremiumWithVat(Long quotationNo, Double premiumAmt) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createSQLQuery("Select Pol_Premium, Pol_Vat_Amount From Table (Cast(GET_QUOTE_DATE("
				+ quotationNo + ") As Quo_Record_Obj_Array))");
		List<Object[]> resultList = query.list();
		LOGGER.debug("results : " + resultList);

		Double premiumFromDB = 0.0;
		for (Object[] result : resultList) {
			// Mapping Total Premium
			if ((!Utils.isEmpty(result[0]) && ((BigDecimal) result[0]).doubleValue() != 0))
				premiumFromDB = premiumFromDB + (((BigDecimal) result[0]).doubleValue());

			// Mapping VAT
			if ((!Utils.isEmpty(result[1]) && ((BigDecimal) result[1]).doubleValue() != 0))
				premiumFromDB = premiumFromDB + (((BigDecimal) result[1]).doubleValue());

		}
		return premiumFromDB;
	}

	public static void deleteSections(PolicyVO policyVO) {

		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		Query query = session.createSQLQuery("Delete from t_trn_policy_sections_quo where Tps_linking_id=:linkingId  ");
		query.setParameter("linkingId", policyVO.getPolLinkingId());
		query.executeUpdate();
		session.close();
	}

	public static void invalidateRecord(PolicyVO policyVO) {
		PASStoredProcedure sp = null;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		if (policyVO.getIsQuote()) {
			sp = (PASStoredProcedure) Utils.getBean("updateValExpDateSecQuoOnDemandCurrEndt");
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
	}

	public static PolicyVO getPolicyVOFromStaging(Long polQuotationNo) {

		List<EplatformWsStaging> staging = null;
		PolicyVO policyVO = new PolicyVO();
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);
		Session session = ht.getSessionFactory().openSession();
		staging = ht.find("from EplatformWsStaging where polQuotationNo = ? ", polQuotationNo);

		Blob blob = staging.get(0).getQuoIntrResponseAdd();

		try (ObjectInputStream ois = new ObjectInputStream(blob.getBinaryStream());) {
			policyVO = (PolicyVO) ois.readObject();
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		session.close();
		return policyVO;
	}

	public static String getStringFromClob(Clob clb) throws IOException, SQLException {

		{
			if (clb == null)
				return "";

			StringBuffer str = new StringBuffer();
			String strng;

			BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

			while ((strng = bufferRead.readLine()) != null)
				str.append(strng);

			return str.toString();
		}
	}

	/*
	 * public static Object getObjectFromJsonStrin(String input, Class
	 * classname) {
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); Object obj =null; try {
	 * 
	 * obj = mapper.readValue(input,classname); // CreateSBSQuoteResponse
	 * createSBSQuoteResponse =
	 * mapper.readValue(input,CreateSBSQuoteResponse.class);
	 * //System.out.println(createSBSQuoteResponse); }
	 * 
	 * catch(Exception e) { e.printStackTrace(); }
	 * 
	 * return obj;
	 * 
	 * }
	 */

	/*
	 * public static byte[] getByteArrayFromObject(Object source, Class
	 * className) {
	 * 
	 * //PolicyVO policyVO = (PolicyVO) source; byte[] output = "".getBytes();
	 * ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out
	 * = null; try { out = new ObjectOutputStream(bos); out.writeObject(source);
	 * out.flush(); output = bos.toByteArray(); }
	 * 
	 * catch(IOException e) {
	 * 
	 * e.printStackTrace(); } finally { try { bos.close(); } catch (IOException
	 * ex) { // ignore close exception } }
	 * 
	 * return output; }
	 */

	/*
	 * public static String getJsonStringFromObjectPrettyPrint(Object obj) {
	 * 
	 * String jsonInStringPrettyPrint = ""; ObjectMapper objMapper = new
	 * ObjectMapper(); try { jsonInStringPrettyPrint =
	 * objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	 * //LOGGER.info("jsonInStringPrettyPrint is: ");
	 * //LOGGER.info(jsonInStringPrettyPrint);
	 * 
	 * } catch (JsonProcessingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * return jsonInStringPrettyPrint; }
	 * 
	 * }
	 */

	public static int getSectionCount(PolicyVO policyVo) {
		// TODO Auto-generated method stub
		String sqlQuery = null;
		int size = 0;
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean(com.Constant.CONST_HIBERNATETEMPLATE);

		sqlQuery = "Select  TPS_SEC_ID From T_TRN_POLICY_SECTIONS_QUO Where TPS_LINKING_ID ="
				+ policyVo.getPolLinkingId();

		Session session = ht.getSessionFactory().openSession();

		Query query = session.createSQLQuery(sqlQuery);
		List<Object> resultsFunc = query.list();
		if (!Utils.isEmpty(resultsFunc)) {
			size = resultsFunc.size();
		}
		session.close();

		return size;

	}


}
