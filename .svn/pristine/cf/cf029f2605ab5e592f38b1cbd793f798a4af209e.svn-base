/*
 * Change History:
 * ----------------------
 * Ticket: 142260, Author: M1037404 , Date Modified: 06/22/2017
 * Comment : Changing the FETCH_LATEST_ACTIVE_POLICY value for eplatform policy.
 *  
 */


package com.rsaame.pas.query.constants;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.svc.constants.SvcConstants;
/*
 * Change History:
 * ----------------------
 * Ticket: 142260, Author: M1037404 , Date Modified: 06/22/2017
 * Comment : Changing the FETCH_LATEST_ACTIVE_POLICY value for eplatform policy.
 *  
 */

/**
 * @author M1020204
 *
 * This class will have all the queries being invoked in derived classes
 *
 */


/**
 * @author M1020204
 *
 * This class will have all the queries being invoked in derived classes
 *
 */
public class QueryConstants{
	
    public static final String FETCH_GPR_FOR_POLICY_ID = "select gpr_id, gpr_validity_start_date from t_trn_gacc_person_quo where gpr_policy_id=?";
	
	public static final String FETCH_LATEST_ENDORESED_RECORD = " from TTrnPolicyQuo t where t.id.polPolicyId = ? order by t.id.polEndtId desc ";
	
	public static final String FETCH_LATEST_ENDORSED_DATE = "select trunc(max(polEndtEffectiveDate)) from TTrnPolicyQuo policy where  policy.id.polPolicyId = ?";
	
	/*
	 * Changes for #142260 - for checking eplatform policies
	 */
	public static final String FETCH_LATEST_ACTIVE_POLICY = "from TTrnPolicyQuo pol where pol.polPolicyNo=? and pol.polStatus <> 6 and pol.polIssueHour=3 order by pol.id.polEndtId desc ";
	
	public static final String FETCH_SPECIAL_CODES_TRAVEL = "Select Sitype_Code, Function_Code, Rate_Type From V_Mas_Gacc_Policy_Rating Where Class_Code = :classCode " +
			" And Policy_Type_Code = :policyType And Risk_Code = :riskCode And cover_code = :coverCode and risk_type_code = :riskTypeCode ";
	
/*	Changes for Ticket 76367 - SI_TYPE issue
	public static final String FETCH_SPECIAL_CODES_TRAVEL_ADDTL = "Select Sitype_Code, Function_Code, Rate_Type From V_Mas_Gacc_Policy_Rating Where Class_Code = :classCode " +
			" And Policy_Type_Code = :policyType And Risk_Code = :riskCode And cover_code <> :coverCode and risk_type_code = :riskTypeCode ";
*/	
	public static final String FETCH_SPECIAL_CODES_HOME = "select sitype_code, function_code, rate_type From V_Mas_Fire_Policy_Rating Where class_code = :classCode " +
	" and policy_type_code = :policyType and risk_code = :riskCode and cover_code = :coverCode and risk_type_code = :riskTypeCode ";
	
	public static final String FETCH_SPECIAL_CODES_WCTPL = "select sitype_code, function_code, rate_type From V_Mas_WCTPL_Policy_Rating Where class_code = :classCode " +
			" and policy_type_code = :policyType and risk_code = :riskCode and cover_code = :coverCode and risk_type_code = :riskTypeCode ";
	
/*	Changes for Ticket 76367 - SI_TYPE issue
	public static final String FETCH_SPECIAL_CODES_HOME_ADDTL = "select sitype_code, function_code, rate_type From V_Mas_Fire_Policy_Rating Where class_code = :classCode " +
			" and policy_type_code = :policyType and risk_code = :riskCode and cover_code <> :coverCode ";
*/	
	public static final String FETCH_DIRECTORATE_CODES = "select DIR_MUN_CODE from T_MAS_DIRECTORATE where DIR_CODE = ?";
	
	public static final String FETCH_T_MAS_CODE_MASTER_CONFIG = "select cdm_code_desc from t_mas_code_master tmcm where tmcm.cdm_entity_type = 'PAS_PC_MAP' and tmcm.cdm_code = ?";
	
	 /*
		 * adding validity start date as part of query as part of fix given
		 * in 3.2 release for underwriting questions. 
		 */
	public static final String FETCH_LATEST_ENDT_ID_QUO = "select uqt_pol_endt_id, uqt_pol_policy_id, uqt_uwq_code from t_trn_uw_questions_quo where uqt_pol_policy_id=? and uqt_uwq_code=? and " +
						"uqt_loc_id=?  and uqt_validity_expiry_date ='31-DEC-2049' order by uqt_pol_endt_id DESC";
	
	 /*
	 * adding validity start date as part of query as part of fix given
	 * in 3.2 release for underwriting questions. 
	 */
	public static final String FETCH_LATEST_ENDT_ID_POL = "select uqt_pol_endt_id, uqt_pol_policy_id, uqt_uwq_code from t_trn_uw_questions where uqt_pol_policy_id=? and uqt_uwq_code=? and " +
			"uqt_loc_id=? and uqt_validity_expiry_date ='31-DEC-2049' order by uqt_pol_endt_id DESC";
	

	public static final String FETCH_COV_TYPE_CODE ="select pc_ct_code from v_mas_product_config_pas where pc_scheme=? and pc_e_desc=?";
	
	public static final String FETCH_PENDING_FACT_NAME = "SELECT REF_FIELD , REF_VALUE  FROM T_TRN_PAS_REFERRAL_DETAILS WHERE REF_POLICY_ID = ? AND REF_ENDT_ID <= ? AND REF_STATUS = ?";
	
	public static final String FETCH_DATE = "SELECT POL_EFFECTIVE_DATE,POL_ENDT_EFFECTIVE_DATE FROM T_TRN_POLICY WHERE POL_POLICY_ID = ? AND POL_ENDT_ID <= ? AND POL_STATUS = 1 ORDER BY POL_ENDT_ID DESC";

	public static final String POLICY_EXT = "select pol_exp_days_diff, pol_policy_no from V_TRN_POLICY_DIFF_EXP_DAYS where pol_policy_id = ? and pol_endt_id =  ?";
	
	public static final String FETCH_PENDING_FACT_NAME_FILTER = "SELECT REF_FIELD , REF_VALUE  FROM T_TRN_PAS_REFERRAL_DETAILS WHERE ( REF_POLICY_ID = ? or REF_POLICY_ID = ? )  AND ( REF_ENDT_ID <= ? OR REF_ENDT_ID <= ? ) AND REF_STATUS = ? ORDER BY REF_MODIFIED_DATE DESC";

	public static final String FETCH_QUOTE_POLICY_ENDT_ID = "SELECT  pol_policy_id, max( pol_endt_id ) FROM t_trn_policy_quo WHERE pol_issue_hour = 3 and  pol_quotation_no = ? GROUP BY pol_policy_id ";
	
	public static final String SQL_FETCH_LATEST_ACTIVE_POLICY = "select pol_expiry_date, pol_policy_id from t_trn_policy where pol_policy_no = ? and pol_status <> 6 and pol_policy_id=? order by pol_endt_id desc";
	
	public static final String FETCH_LATEST_ENDT = "SELECT  pol_policy_id,pol_class_code, max( pol_endt_id ),max(pol_endt_no) FROM t_trn_policy_quo WHERE pol_issue_hour = 3 and  pol_quotation_no = ? GROUP BY pol_policy_id, pol_class_code";
	
	public static final String BROKER_ADDRESS = "SELECT BR_E_NAME,BR_E_ADDRESS1,BR_E_ADDRESS2,BR_E_ADDRESS3 FROM T_MAS_BROKER WHERE BR_CODE = ?";
	
	public static final String GET_QUOTE_STATUS = "SELECT POL_DOCUMENT_CODE,POL_STATUS FROM T_TRN_POLICY_QUO WHERE POL_ENDT_ID = ? AND POL_POLICY_ID = ? AND POL_ISSUE_HOUR = 3";
	//07.08.2020 CTS  CR#11645 - Home Digital API - error  message need to display, if trying to update the quote, if already converted into policy- start
	public static final String GET_QUOTE_STATUS_BY_QUO_NO = "SELECT POL_DOCUMENT_CODE,POL_STATUS FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO = ? AND POL_ISSUE_HOUR = 3 AND trunc(POL_VALIDITY_EXPIRY_DATE) =  '"+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )+"'"; 
	//07.08.2020 CTS  CR#11645 ENDS
	/*
	 * added By raman For Business type change in Renewal amendment
	 */
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	public static final String FETCH_CSH_CUS_EMAIL_ID = "SELECT CSH_E_EMAIL_ID FROM T_MAS_CASH_CUSTOMER_QUO WHERE (CSH_POLICY_ID, CSH_ENDT_ID) IN   (SELECT POL_POLICY_ID, POL_ENDT_ID FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO = ? AND POL_ISSUE_HOUR = 3 AND trunc(POL_VALIDITY_EXPIRY_DATE) =  '"+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )+"')"; 
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
	
	public static final String GET_BUSINESS_TYPE = "SELECT POL_BUSINESS_TYPE FROM T_TRN_POLICY WHERE POL_ENDT_ID = 0 AND POL_POLICY_ID = ?  AND POL_POLICY_NO = ? AND POL_ISSUE_HOUR = 3";
	
	
	public static final String GET_SCALE_FOR_POLICY_TYPE = "SELECT pt_code,PT_DECIMAL_VALUE FROM T_MAS_POLICY_TYPE WHERE PT_CODE IN (6,31) AND PT_CL_CODE IN (5)"
			+ "union SELECT pt_code,PT_DECIMAL_VALUE FROM T_MAS_POLICY_TYPE WHERE PT_CODE IN (7)AND PT_CL_CODE IN (2)"+
			"union SELECT pt_code,PT_DECIMAL_VALUE FROM T_MAS_POLICY_TYPE WHERE PT_CODE IN (50)AND PT_CL_CODE IN (2)"+
			"union SELECT pt_code,PT_DECIMAL_VALUE FROM T_MAS_POLICY_TYPE WHERE PT_CODE IN (1)AND PT_CL_CODE IN (7)";
	
	public static final String GET_POLICY_STATUS = "SELECT MAX(POL_ENDT_ID) FROM T_TRN_POLICY WHERE POL_POLICY_NO = ? AND POL_POLICY_ID = ? AND POL_ISSUE_HOUR = 3";

	public static final String GET_CURR_DISC_LOAD_SUM = " Select sum(nvl(pr.PRM_PREMIUM,0))  From T_TRN_PREMIUM PR Where trunc(pr.prm_validity_expiry_date) =  '"+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) +"'  and pr.prm_policy_id =  ? and pr.prm_Basic_Rsk_Code = 99999 and pr.prm_cov_code in ( " + SvcConstants.SC_PRM_COVER_LOADING +","+ SvcConstants.SC_PRM_COVER_DISCOUNT+ com.Constant.CONST_AND_PR_PRM_ENDT_ID_END;
	
	public static final String QUERY_GET_SUM_INSURED_CURRENCY = "SELECT pc_sum_insured_curr FROM v_mas_product_config_pas WHERE pc_scheme = ? AND pc_tariff = ? AND pc_rt_code = ? " +
			"AND pc_cov_code = ?";
	
	public static final String GET_POLICY_ID = "select pol_policy_id from t_trn_policy where pol_policy_no = ? and POL_CONC_POLICY_NO = ?";
	
	public static final String GET_INSURED_NAME = "select ins_e_full_name from t_mas_insured where ins_insured_code = ?";
	
	public static final String GET_CURR_PROMO_DISC_SUM = " Select  sum(nvl(pr.PRM_PREMIUM,0))  From T_TRN_PREMIUM  PR  Where trunc(pr.prm_validity_expiry_date) =  '"+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) +"' and pr.prm_policy_id =  ? and pr.prm_Basic_Rsk_Code = 99999 and pr.prm_cov_code in (  " + SvcConstants.SC_PRM_COVER_PROMO_DISC + com.Constant.CONST_AND_PR_PRM_ENDT_ID_END;
	
	public static final String GET_TERMINAL_ID = "SELECT USR_TERMINAL_ID FROM T_MAS_USER WHERE USER_ID = ?";
	
	public static final String FETCH_MIN_PREMIUM = " select SEC_MIN_PRM from T_MAS_PKG_POL_SECTION where sec_id = ? and SEC_PT_CODE = ? and SEC_TAR_CODE = ? ";
	
	public static final String FETCH_TOTAL_QUOTE_PRM = "select sum (premium) from V_TRN_PREMIUM_QUO_SUMMARY_PAS where QUOTATION_NO = ? ";
	
	public static final String FETCH_TOTAL_POLICY_PRM = "select sum(nvl(pr.PRM_PREMIUM,0)) from T_TRN_PREMIUM PR where trunc(pr.prm_validity_expiry_date) =  '"
			+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )
			+ "' and pr.prm_policy_id =  ?  and pr.prm_endt_id <= ? and pr.prm_Basic_Rsk_Code <> 99999  and pr.prm_cov_code not in ("+SvcConstants.SC_PRM_COVER_POLICY_FEE +","+SvcConstants.SC_PRM_COVER_GOVT_TAX +")";
	
	public static final String FETCH_TOTAL_PRM_QUOTE = "select sum(nvl(pr.PRM_PREMIUM,0)) from T_TRN_PREMIUM_QUO PR where trunc(pr.prm_validity_expiry_date) =  '"
			+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )
			+ "' and pr.prm_policy_id =  ?  and pr.prm_endt_id <= ? and pr.prm_Basic_Rsk_Code <> 99999  and pr.prm_cov_code not in ("+SvcConstants.SC_PRM_COVER_POLICY_FEE +","+SvcConstants.SC_PRM_COVER_GOVT_TAX +")";
	
	/*public static final String FETCH_HOME_BASIC_COVERS = "from TTrnPremiumQuo WHERE id.prmCovCode = " + SvcConstants.DEFAULT_HOME_COVER_CODE + " AND id.prmRskCode IN ( "
			+ SvcConstants.BUILDING_RISK_CODE + " ," + SvcConstants.CONTENT_RISK_CODE + ") AND prmRtCode IN (" + SvcConstants.BUILDING_RISK_TYPE_CODE + ","
			+ SvcConstants.CONTENT_RISK_TYPE_CODE + ") and TRUNC(PRM_VALIDITY_EXPIRY_DATE) = '" + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )
			+ "'  AND prmStatus <> 4 AND id.prmPolicyId = ? and prm_endt_id <= ? order by prm_rt_code ";*/
	public static final String FETCH_MIN_PRM_COVERS = "from TTrnPremiumQuo WHERE id.prmCovCode = " + SvcConstants.SPECIAL_COVER_MIN_PRM + " AND id.prmRskCode = "
			+ SvcConstants.SPECIAL_CODE + " AND TRUNC(PRM_VALIDITY_EXPIRY_DATE) = '" + Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )
			+ "'  AND prmStatus <> 4 AND id.prmPolicyId = ? and prm_endt_id = ?";
	
	public static final String UPDATE_PRM = "UPDATE t_trn_policy pol SET pol.pol_premium = ? WHERE pol.pol_endt_id = ? AND pol.POL_POLICY_ID = ? AND pol_issue_hour = 3";
	
	public static final String UPDATE_PRM_QUO = "UPDATE t_trn_policy_quo pol SET pol.pol_premium = ? WHERE pol.pol_endt_id = ? AND pol.POL_POLICY_ID = ? AND pol_issue_hour = 3";
	
	public static final String RENEWAL_FETCH_PREVIOUS_EXPIRY_DATE = "select pol.pol_expiry_date from t_trn_policy_quo quo,t_trn_policy pol where quo.pol_ref_policy_id = pol.pol_policy_id and quo.pol_quotation_no = ? and pol.pol_policy_no = ? order by pol.pol_endt_no desc";
	
	public static final String FETCH_USER_CCG_CODE = "select rtrim(xmlagg (xmlelement (e, (CCG_CODE) || ',')).extract ('//text()'), ',') CCG_CODE from (select distinct CDM_CODE2 CCG_CODE from T_Mas_Code_Master  WHERE Cdm_Entity_Type='PAS_LOB' and Cdm_Entity_Desc = ?)";
	
	public static final String GET_BROKER_ACC_STATUS = "select distinct b.br_status  from t_mas_broker b where  b.br_code=?";

	public static final String GET_MIN_PREM_FROM_PRM_TABLE = " Select sum(nvl(pr.PRM_PREMIUM,0)) From T_TRN_PREMIUM PR Where trunc(pr.prm_validity_expiry_date) =  '"+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) +"' and pr.prm_policy_id =  ? and pr.prm_Basic_Rsk_Code = 99999 and pr.prm_cov_code in ( " + SvcConstants.SPECIAL_COVER_MIN_PRM + com.Constant.CONST_AND_PR_PRM_ENDT_ID_END;
	
	public static final String FETCH_RENEWAL_POLICY_NUMBER = "SELECT POL_POLICY_NO FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO = ? AND POL_DOCUMENT_CODE = 6";
	
	public static final String FETCH_EFFECTIVE_DATE_DOC_CODE = "SELECT Pol_Document_Code,Pol_Effective_Date FROM t_trn_policy_quo WHERE Pol_Conc_Policy_No =?";
	
	public static final String FETCH_UNDERWRITER_EMAIL = "SELECT USER_EMAIL_ID FROM T_MAS_USER WHERE USER_ID = ?";
	
	public static final String FETCH_SCHEME_NAME = "SELECT SCH_DESC FROM T_MAS_SCHEME WHERE SCH_CODE = ?";
		
	public static final String GET_COVER_PRM_RATE_POLICY = "select PRM_PREMIUM, PRM_LOAD_DISC, PRM_MODIFIED_BY, prm_prepared_by from t_trn_premium where prm_policy_id = ? and prm_endt_id <= ? and PRM_COV_CODE in (?,?) and prm_Rsk_Code = "+SvcConstants.SPECIAL_CODE +" AND  prm_Status <> 4 and ( PRM_LOAD_DISC <> 0 or   PRM_LOAD_DISC is not null )  order by  prm_endt_id desc";
	
	public static final String GET_COVER_PRM_RATE_QUOTE = "select PRM_PREMIUM, PRM_LOAD_DISC, PRM_MODIFIED_BY, prm_prepared_by from t_trn_premium_quo where prm_policy_id = ? and prm_endt_id <= ? and PRM_COV_CODE in (?,?) and prm_Rsk_Code = "+SvcConstants.SPECIAL_CODE +" and  prm_Status <> 4  and ( PRM_LOAD_DISC <> 0 or   PRM_LOAD_DISC is not null )  order by  prm_endt_id desc";
	
	public static final String GET_USER_ROLE = "select ROLE_FK from T_TRN_USER_ROLE_MAP where USER_ID_FK =  ?";
	
	public static final String FETCH_TOTAL_POLICY_PRM_TOTAL = " select  sum(nvl(pr.PRM_PREMIUM,0))  from T_TRN_PREMIUM PR where trunc(pr.prm_validity_expiry_date) =  '"
			+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )
			+ "' and pr.prm_policy_id =  ?  and pr.prm_endt_id <= ?  and pr.prm_cov_code not in ("+SvcConstants.SC_PRM_COVER_POLICY_FEE +","+SvcConstants.SC_PRM_COVER_GOVT_TAX +","+SvcConstants.SC_PRM_COVER_VAT_TAX+")";
	
	public static final String FETCH_TOTAL_POLICY_PRM_QUO_TOTAL = "select sum(nvl(pr.PRM_PREMIUM,0)) from T_TRN_PREMIUM_QUO PR where trunc(pr.prm_validity_expiry_date) =  '"
		+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )
		+ "' and pr.prm_policy_id =  ?  and pr.prm_endt_id <= ?  and pr.prm_cov_code not in ("+SvcConstants.SC_PRM_COVER_POLICY_FEE +","+SvcConstants.SC_PRM_COVER_GOVT_TAX +","+SvcConstants.SC_PRM_COVER_VAT_TAX+")";
	
	public static final String GET_LEGACY_POLICIES = "select quote_no from "+Utils.getSingleValueAppConfig( "VIEW_LEGACY_HOME" )+" where quote_no = ?";

	public static final String GET_USER_EMAIL_ID = "select user_email_id from t_mas_user where user_id = ?";
	
	public static final String PAR_FREE_ZONE_CERTIFICATE = "select bld_free_zone "+
														"from t_trn_building "+
														"where bld_policy_id = ? "+
														"and bld_validity_start_date <= ? "+
														"and bld_validity_expiry_date > ? "+
														"and bld_free_zone is not null "+
														"and bld_free_zone <> 55999";
	
	//public static final String FETCH_PAR_POLICY_ID = "select pol_policy_id,to_char(pol_validity_start_date,'DD-MON-YY HH24:MI:SS') from t_trn_policy where pol_policy_no = ? and pol_endt_id = ? and pol_issue_hour = 3 and pol_class_code =2";
	//public static final String FETCH_PAR_POLICY_ID = "from TTrnPolicyQuo where polPolicyNo = ? and id.polEndtId = ? and polIssueHour = 3 and polClassCode =2";
	public static final String FETCH_PAR_POLICY_ID = "from TTrnPolicyQuo where polPolicyNo = ? and polQuotationNo = ? and  id.polEndtId = ? and polIssueHour = 3 and polClassCode =2";
	
	
	public static final String PL_FREE_ZONE_CERTIFICATE = "select wbd_free_zone "+
			"from t_trn_wctpl_premise "+
			"where wbd_policy_id = ? "+
			"and wbd_validity_start_date <= ? "+
			"and wbd_validity_expiry_date > ? "+
			"and wbd_free_zone is not null "+
			"and wbd_free_zone <> 55999";
	
	//public static final String FETCH_PL_POLICY_ID = "from TTrnPolicyQuo where polPolicyNo = ? and id.polEndtId = ? and polIssueHour = 3 and polClassCode =7";
	public static final String FETCH_PL_POLICY_ID = "from TTrnPolicyQuo where polPolicyNo = ? and polQuotationNo = ? and id.polEndtId = ? and polIssueHour = 3 and polClassCode =7";
	
	public static final String FETCH_T_MAS_GROUP_UP = "select grs_gross_up_flag,grs_gross_up_value from t_mas_gross_up where grs_sch_code=?";
	
	public static final String FETCH_LEGACY_RECORD = "select application FROM T_Trn_Renewal_Batch_Eplatform WHERE pol_policy_No = ? and pol_policy_id=? and application is not null";

	public static final String GET_TASKS = "select tsk_description, tsk_document_id from t_trn_task where tsk_document_id like (?)";

	public static final String GET_APPROVED_QUO_RECS_FOR_ENDT = "select count(pol_approval_date) from t_trn_policy_quo where pol_quotation_no=? and pol_endt_id=? and trunc(pol_approval_date)=trunc(sysdate)";
	
	public static final String GET_APPROVED_QUO_RECS_FOR_ENDT_WITHOUT_SYSDATE = "select count(pol_approval_date) from t_trn_policy_quo where pol_quotation_no=? and pol_endt_id=?";
	
	public static final String FETCH_DOMESTIC_STAFF_DETAILS_POLICY_ID = "select GPR_E_NAME, GPR_DATE_OF_BIRTH from t_trn_gacc_person_quo where gpr_policy_id=? and GPR_ENDT_ID=? and GPR_STATUS <> 4";
	
	public static final String GET_CURRENT_OUTSTANDING_BROKER = "SELECT SUM(SUM_AMOUNT) FROM V_CURRENT_OUTSTANDING_AMT_BRK WHERE GL_CODE = ? AND TOT_ACC_CODE = "+SvcConstants.BR_TOT_ACC_CODE ;
	
	public static final String GET_BROKER_CREDIT_LIMIT = "SELECT BR_CREDIT_AMT FROM T_MAS_BROKER WHERE BR_CODE = ?";
	
	public static final String GET_ROLE_LICENSED_BY = "SELECT ROLE_FK FROM T_TRN_USER_ROLE_MAP WHERE USER_ID_FK = ? AND ROLE_FK = 'CREDIT_MANAGER'";
	
//	public static final String GET_LATEST_POLICY_ID_ENDT_ID = "select pol_endt_id, pol_policy_id from (select * from t_trn_policy where pol_policy_no = ? and pol_class_code = 1 order by pol_endt_id desc) where rownum = 1";
	
	public static final String QRY_GET_LOCATION_CODE = "select POL_LOCATION_CODE FROM V_TRN_RENEWAL_SEARCH_LAST_PAS where POL_POLICY_NO = ? AND CSH_DOB = ?";	
	

	public static final String GET_APPROVED_POL_RECS_FOR_ENDT = "select count(pol_approval_date) from t_trn_policy where pol_policy_no=? and pol_endt_id=? and trunc(pol_approval_date)=trunc(sysdate)";
	
	public static final String GET_APPROVED_POL_RECS_FOR_ENDT_WITHOUT_SYSDATE = "select count(pol_approval_date) from t_trn_policy where pol_policy_no=? and pol_endt_id=?";
	
	public static final String CREDIT_LIMIT_EMAIL_ID = "SELECT CDM_CODE_DESC FROM T_MAS_CODE_MASTER WHERE CDM_ENTITY_TYPE = 'PAS_CRD_LM' AND CDM_ENTITY_DESC = ?";
	
	public static final String FETCH_EMAIL_ID = "SELECT CSH_E_EMAIL_ID,GPR_NTY_E_DESC FROM T_TRN_POLICY_QUO TPQ,T_MAS_CASH_CUSTOMER_QUO TCC,T_TRN_GACC_PERSON_QUO TGP  WHERE POL_QUOTATION_NO = ? AND POL_VALIDITY_EXPIRY_DATE ='31-DEC-2049'  AND POL_POLICY_ID = CSH_POLICY_ID AND POL_ENDT_ID = CSH_ENDT_ID  AND POL_POLICY_ID = GPR_POLICY_ID " +
			"AND GPR_VALIDITY_EXPIRY_DATE = '31-DEC-2049' AND GPR_RELATION = 1 AND GPR_DATE_OF_BIRTH = ?";
			
	public static final String GET_APPROVED_QUO_RECS_FOR_ISSUE_QUOTE = "select count(pol_approval_date) from t_trn_policy_quo where pol_quotation_no=? and pol_status=23 and trunc(pol_validity_expiry_date) =  '"+ Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )+"'";
	
	public static final String FETCH_MONOLINE_POLICY_ID = "from TTrnPolicyQuo where polPolicyNo = ? and polQuotationNo = ? and id.polEndtId = ? and polIssueHour = 3 and polClassCode =?";

	public static final String WC_FREE_ZONE_CERTIFICATE = "select wup_free_zone "+
			"from T_TRN_WCTPL_UNNAMED_PERSON "+
			"where wup_policy_id = ? "+
			"and wup_validity_start_date <= ? "+
			"and wup_validity_expiry_date > ? "+
			"and wup_free_zone is not null "+
			"and wup_free_zone <> 55999";
	
	public static final String FETCH_REF_DETAILS = "FROM TTrnPasReferralDetails WHERE ref_status = ? AND ref_policy_id = ? AND ref_endt_id = ?";
	
	//Query is modified for the Advnet Request ID : 119495
	public static final String GET_REFFERED_DISC = "SELECT * FROM(select REF_VALUE from T_TRN_PAS_REFERRAL_DETAILS where POL_LINKING_ID = ? and REF_ENDT_ID <= ? and REF_STATUS=23 order by REF_ENDT_ID desc)WHERE rownum = 1";
	
	
	public static final String GET_REFFERED_DISC_HOME_TRAVEL = "SELECT * FROM(select REF_VALUE from T_TRN_PAS_REFERRAL_DETAILS where REF_POLICY_ID = ? and REF_ENDT_ID <= ? and REF_STATUS=23 order by REF_ENDT_ID desc)WHERE rownum = 1";

	
	//156578 : Added by Pushkar for Nexus Home B2C eMail signature
	
	public static final String NEX_BROKER_SFL = "select T1.PMM_SFL2_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_COVER_NOTE_HOUR=?)";
	public static final String NEX_BROKER_CTP = "select T1.PMM_POL_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_COVER_NOTE_HOUR=?)";
	
	// Ticket 165419 : Added by DileepMS to Fetch Email Templates from DB start
	public static final String B2C_HOME_EMAIL_TEMPLATE_ONLINE = "select T1.PMM_SFL2_MAIL_BODY,T1.PMM_POL_MAIL_BODY,T1.PMM_24HRS_MAIL_BODY,T1.PMM_15DYS_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_PMM_ID='ONLINE_HOME')";

	public static final String B2C_TRAVEL_EMAIL_TEMPLATE_ONLINE = "select T1.PMM_SFL2_MAIL_BODY,T1.PMM_POL_MAIL_BODY,T1.PMM_24HRS_MAIL_BODY,T1.PMM_15DYS_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_PMM_ID='ONLINE_TRAVEL')";
	
	public static final String B2C_HOME_TRAVEL_EMAIL_TEMPLATE_DIRECT = "select T1.PMM_SFL2_MAIL_BODY,T1.PMM_POL_MAIL_BODY,T1.PMM_24HRS_MAIL_BODY,T1.PMM_15DYS_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_PMM_ID=?)";
	
	public static final String TRAVEL_NEX_BROKER = "select T1.PMM_SFL2_MAIL_BODY,T1.PMM_POL_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_COVER_NOTE_HOUR=?)";
	
	public static final String B2C_HOME_CTP_EXPIRY_REMINDER = "select T1.PMM_SFL3_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_PMM_ID='ONLINE_HOME')";
	
	public static final String B2C_TRAVEL_CTP_EXPIRY_REMINDER = "select T1.PMM_SFL3_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_PMM_ID='ONLINE_TRAVEL')";
	
	public static final String B2C_REF_APPROVE_TEMPLATE = "select PMM_APPROVE_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID=?";
	public static final String B2C_REF_APPROVE_TEMPLATE_HOME = "select PMM_APPROVE_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID='ONLINE_HOME'";
	public static final String B2C_REF_APPROVE_TEMPLATE_TRAVEL = "select PMM_APPROVE_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID='ONLINE_TRAVEL'";
	// Fetch Email Templates from DB end
	
	//Added by Vishwa for the  Advnet Request ID : 123969
	public static final String FGB_BROKER_SFL = "select T1.PMM_SFL2_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_COVER_NOTE_HOUR=?)";
	public static final String FGB_BROKER_CTP = "select T1.PMM_POL_MAIL_BODY from T_MAS_PARTNER_MGMT T1, T_MAS_DEFAULT_VALUES T2 where T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID in (select DFT_PMM_ID from T_MAS_DEFAULT_VALUES where DFT_COVER_NOTE_HOUR=?)";
	public static final String BROKER_SFL = "select PMM_SFL2_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_PARTNER_CODE = ? and PMM_CLASS_CODE = ?";
	public static final String BROKER_CTP = "select PMM_POL_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_PARTNER_CODE = ? and PMM_CLASS_CODE = ?";
	public static final String BROKER_24H = "select PMM_24HRS_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_PARTNER_CODE = ? and PMM_CLASS_CODE = ?";
	public static final String BROKER_15D = "select PMM_15DYS_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_PARTNER_CODE = ? and PMM_CLASS_CODE = ?";
	public static final String PACKAGE_CODE="select TAR_E_DESC from T_MAS_TARIFF where TAR_CODE = ?";
	
	//Added by Vishwa for the  Advnet Request ID : 129135
	public static final String FGB_STAFF_SCHEME_CODE = "select SCH_CODE from T_MAS_SCHEME where SCH_DESC = 'FGB Travel Staff Scheme'";
	
	//Added by Vishwa for the  Advnet Request ID : 128926
	public static final String PARTNER_PROMO_CODE = "select CDM_ENTITY_TYPE from T_MAS_CODE_MASTER where CDM_ENTITY_TYPE='B2C_PROFLG' and CDM_CODE_DESC = ?";

	//Added by Vishwa for the  Advnet Request ID : 129135
	public static final String UPDATE_ROYALTY_ID_QUO = "Update T_MAS_CASH_CUSTOMER set CSH_AFFINITY_REF_NO=? where CSH_POLICY_ID IN ( select POL_POLICY_ID from T_TRN_POLICY_QUO  where POL_QUOTATION_NO=?)";
	public static final String UPDATE_ROYALTY_ID_POL = "Update T_MAS_CASH_CUSTOMER set CSH_AFFINITY_REF_NO=? where CSH_POLICY_ID IN ( select POL_POLICY_ID from T_TRN_POLICY where POL_POLICY_NO=?)";
	
	//Added by Vishwa for the  Advnet Request ID : 130669	
	public static final String REF_APP_PARTNER = "select PMM_APPROVE_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_PARTNER_CODE = ? and PMM_CLASS_CODE = ?";

	//Added For Oman D2C
	public static final String LOCATION_CODE = "30";
	public static final String ACCIDENT_TYPE_POLICE_REPORT = "ROP";
	public static final String ACCIDENT_TYPE_MRTA_FORM = "MRTA";
	//Abani - Oman D2C Email Template
	public static final String REF_APP_OMAN= "select PMM_APPROVE_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String SFL_OMAN_D2C= "select PMM_SFL2_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String POLICY_OMAN_D2C= "select PMM_POL_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String REMINDER_15DAYS_OMAN_D2C= "select PMM_15DYS_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String HOME_LEAD_SUBMIT_OMAN_D2C= "select PMM_HLS_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String HOME_LEAD_NOTIFICATION_OMAN_D2C= "select PMM_HLN_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String FNOL_SUBMIT_OMAN_D2C= "select PMM_FNOLS_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String FNOL_NOTIFICATION_OMAN_D2C= "select PMM_FNOLN_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String RENEWAL_NOTICE_OMAN_D2C= "select PMM_RENEWAL_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	public static final String FETCH_VAT_RATE_AND_CODE="select pr_vat_code from t_mas_policy_rating where pr_cl_code=:classCode and pr_pt_code=:ptCode and pr_cov_code=:coverCode ";

	/*VAT*/
	public static final String FETCH_VAT_START_DATE="select CDM_CODE2 from T_MAS_CODE_MASTER where  CDM_ENTITY_TYPE = 'VAT_LIVEDT'";
	
	//Home Revamp requirement 1.1
	public static final String FETCH_COV_START_DATE="select CDM_CODE2 from T_MAS_CODE_MASTER where  CDM_ENTITY_TYPE = 'PAS_HRLVDT'";
	
	public static final String FETCH_POL_PREPARED_DATE="select pol_prepared_dt from T_TRN_POLICY_QUO where pol_endt_id=0 and pol_issue_hour=3 and  POL_QUOTATION_NO = ?";

	public static final String FETCH_SYS_DATE="select sysdate from dual";
	//Email-template for JLT
	public static final String POLICY_JLT_SBS= "select PMM_POL_MAIL_BODY from T_MAS_PARTNER_MGMT where PMM_ID = ?";
	//changes-HomeRevamp#7.1
		public static final String FETCH_AED_START_DATE="select CDM_CODE2 from T_MAS_CODE_MASTER where CDM_ENTITY_TYPE = 'PAS_HRLVDT'";
		//changes-HomeRevamp#7.1
		// changes-HomeRevamp#7.1
		//Fetch Ownership status
		//public static final String Fetch_Ownership_status="select bld_ownership_status from t_trn_building where  bld_policy_id=? and bld_validity_expiry_date= to_date('31-DEC-2049','DD-MON-YYYY')";
		public static final String Fetch_Ownership_status="select distinct b.bld_ownership_status from t_trn_building b ,t_trn_policy p where  p.pol_policy_no=? and p.pol_validity_expiry_date=to_date('31-DEC-2049','DD-MON-YYYY') and p.pol_issue_hour=3 and p.pol_policy_id=b.bld_policy_id and b.bld_validity_expiry_date= to_date('31-DEC-2049','DD-MON-YYYY')";
		// changes-HomeRevamp#7.1
		
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
	public static final String FETCH_KYC_START_DATE="select CDM_CODE2, CDM_CODE from T_MAS_CODE_MASTER where CDM_ENTITY_TYPE = 'PAS_IALVDT'";
	//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
	
	//HomeRevampRequirement-4.1
	public static final String HomeRevamp_pol_prepareDt = "select POL_PREPARED_DT from t_trn_policy_quo where POL_QUOTATION_NO=? and POL_ENDT_ID=0 and POL_ISSUE_HOUR=3";
	
	public static final String HOMEREVAMP_POL_PREPAREDDT_DOCCODE = "select POL_PREPARED_DT,POL_DOCUMENT_CODE from t_trn_policy_quo where POL_QUOTATION_NO=? and POL_ENDT_ID=0 and POL_ISSUE_HOUR=3";
	
	//Fetch Transaction Details for Wunderman
	public static final String FETCH_TRANSACTION_DETAILS = "SELECT T2.DFT_CLASS,  T2.DFT_POLICY_TYPE,  T2.DFT_POLICY_TERM,  T2.DFT_COVER_NOTE_HOUR,  T2.DFT_TAR_CODE,  T2.DFT_DISTRIBUTION_CHNL,  T2.DFT_SOC_CUSTOMER,  T1.Pmm_Partner_Code,T1.PMM_E_NAME,T2.DFT_INTERNAL_EXEC FROM T_MAS_PARTNER_MGMT T1,  T_MAS_DEFAULT_VALUES T2 WHERE T2.DFT_PMM_ID = T1.PMM_ID AND T2.DFT_PMM_ID  IN  (SELECT DFT_PMM_ID FROM  T_MAS_DEFAULT_VALUES WHERE DFT_PMM_ID=?)";

	public static final String FETCH_COMMENT_DETAILS = "select PMM_E_NAME from T_MAS_PARTNER_MGMT where Pmm_Partner_Code = ?";
	
	// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-start
	public static final String FETCH_LATEST_ENDT_POL = "SELECT  pol_policy_id,pol_class_code, max( pol_endt_id ),max(pol_endt_no),pol_status FROM t_trn_policy WHERE pol_issue_hour = 3 and  pol_policy_no = ? and pol_quotation_no=? and pol_validity_expiry_date=to_date('31-DEC-2049','DD-MON-YYYY') GROUP BY pol_policy_id, pol_class_code,pol_status"; //added
	// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-end
	
	//CTS 20.08.2020 - SAT#40972 VAT amount not showing properly in Endorsement schedule
	public static final String FETCH_OLD_PREMIUM_FOR_VAT = "Select PRM_PREMIUM FROM T_TRN_PREMIUM WHERE PRM_POLICY_ID = ? AND PRM_COV_CODE = 151 AND PRM_ENDT_ID = (SELECT MAX(PRM_ENDT_ID) FROM T_TRN_PREMIUM WHERE PRM_POLICY_ID = ? AND PRM_COV_CODE = 151 )";
	//CTS 20.08.2020 - SAT#40972 VAT amount not showing properly in Endorsement schedule
	
	//CTS - 15.09.2020 - JLT UAT Change - Enable editing for JLT users even with zero SI for PAR section - Starts
	public static final String GET_POL_PREPARED_BY = "SELECT POL_PREPARED_BY FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO = ? AND POL_ENDT_ID=0 AND POL_ISSUE_HOUR = 3";
	//CTS - 15.09.2020 - JLT UAT Change - Enable editing for JLT users even with zero SI for PAR section - Ends
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
	public static final String CHECK_JLT_QUOTE =  "SELECT DISTINCT NVL(POL_PREPARED_BY,0) FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO IN (SELECT DISTINCT POL_QUOTATION_NO FROM T_TRN_POLICY "
												 + "WHERE POL_POLICY_NO= ? AND POL_POLICY_YEAR = ? AND  POL_CLASS_CODE   IN (2,5,7) AND POL_POLICY_TYPE = 50 AND POL_ISSUE_HOUR   = 3 AND POL_ENDT_ID = 0) "
												 + "AND POL_ENDT_ID    = 0 AND POL_CLASS_CODE   IN (2,5,7) AND POL_POLICY_TYPE = 50 AND POL_ISSUE_HOUR = 3";
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
 //CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts   
   public static final String FETCH_INSURED_CODE="select distinct pol_insured_code from t_trn_policy_quo where pol_quotation_no=?";
   //CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
}
