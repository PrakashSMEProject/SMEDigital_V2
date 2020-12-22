/**
 * 
 */
package com.rsaame.pas.reports.dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.model.TMasSms;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.BrReportSearchVO;
import com.rsaame.pas.vo.app.BrokerAcctResultVO;
import com.rsaame.pas.vo.app.ClassWisePrmResultVO;
import com.rsaame.pas.vo.app.LivePoliciesRptResultVO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.PaymentReportVO;
import com.rsaame.pas.vo.app.PromotionalCodeReportVO;
import com.rsaame.pas.vo.app.QuoteRportVO;
import com.rsaame.pas.vo.app.RenewalPaymentReportVO;
import com.rsaame.pas.vo.app.ReportsResultsHolder;
import com.rsaame.pas.vo.app.ReportsSearchVO;
import com.rsaame.pas.vo.app.SmsListVO;
import com.rsaame.pas.vo.app.TransactionalReportsDetailVO;
import com.rsaame.pas.vo.app.TransactionalReportsVO;
import com.rsaame.pas.vo.bus.SmsVO;

/**
 * @author M1014957
 *
 */
public class ReportsDAO extends BaseDBDAO implements IReportsDAO {
	
	private final static Logger LOGGER = Logger.getLogger( ReportsDAO.class );
	private final static SimpleDateFormat format = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_SLASH);
	//Added for Bahrain 3 decimal - Starts
	java.text.DecimalFormat decFormBahrain = new  java.text.DecimalFormat(com.rsaame.pas.svc.constants.SvcConstants.DECIMAL_FORMAT_BAHRAIN); 
	//Added for Bahrain 3 decimal - Ends
	private final static String BRK_ACCOUNT_SEARCH_SELECT_ALL = "SELECT tab.brokername ,tab.address,tab.fax, tab.phone, tab.loc_desc,tab.customercategory,tab.insuredname,tab.transactiontype,tab.voucherno,tab.voucherdate,tab.policyno,ABS(NVL(SUM(tab.gross),0)) gross,NVL(SUM(tab.gross),0)+NVL(SUM(tab.COMAMOUNT),0) NET,ABS(NVL(SUM(tab.COMAMOUNT),0)) comamount FROM  (select distinct * from (SELECT T_MAS_BROKER.BR_E_NAME BROKERNAME,    ( T_MAS_BROKER.BR_E_ADDRESS1    || ' '    || T_MAS_BROKER.BR_E_ADDRESS2    || ' '    || T_MAS_BROKER.BR_E_ADDRESS3) ADDRESS,    T_MAS_BROKER.BR_FAX FAX,    T_MAS_BROKER.BR_TELEPHONE PHONE,    (SELECT LOC_E_DESC    FROM t_mas_location    WHERE t_mas_location.loc_code = V_TRN_GL_VOUCHER_WISE.LOC_CODE    ) LOC_DESC,    T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY,    V_TRN_GL_VOUCHER_WISE.DTL_DESCRIPTION INSUREDNAME,    T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE,    V_TRN_GL_VOUCHER_WISE.VOUCHER_NO VOUCHERNO,    V_TRN_GL_VOUCHER_WISE.VOUCHER_DATE VOUCHERDATE,    V_TRN_GL_VOUCHER_WISE.POLICY_NO POLICYNO,    V_TRN_GL_VOUCHER_WISE.AMOUNT GROSS,    (V_TRN_GL_VOUCHER_WISE.AMOUNT+V_TRN_GL_VOUCHER_WISE.COM_AMOUNT) NET,    V_TRN_GL_VOUCHER_WISE.COM_AMOUNT COMAMOUNT    ";
	
	private final static String BRK_ACCOUNT_SEARCH_FROM_ALL = "  FROM V_TRN_GL_VOUCHER_WISE,    V_TRN_TB,    T_MAS_CLASS,    T_MAS_TRANSACTION,    T_TRN_POLICY,    T_MAS_BROKER,    T_MAS_CURRENCY,    T_MAS_CUSTOMER_CATEGORY ";
		
	private final static String BRK_ACCOUNT_SEARCH_WHERE_ALL = "  WHERE ( V_TRN_GL_VOUCHER_WISE.POLICY_ID                = T_TRN_POLICY.POL_POLICY_ID)   AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID                     = T_TRN_POLICY.POL_ENDT_ID (+))  AND ( T_TRN_POLICY.POL_CLASS_CODE                      = t_mas_class.cl_code )  AND ( V_TRN_TB.GL_CODE                                 = V_TRN_GL_VOUCHER_WISE.GL_CODE )  AND ( V_TRN_TB.GL_CTY_CODE                             = V_TRN_GL_VOUCHER_WISE.CTY_CODE )  AND ( V_TRN_TB.GL_REG_CODE                             = V_TRN_GL_VOUCHER_WISE.REG_CODE )  AND ( V_TRN_TB.GL_LOC_CODE                             = V_TRN_GL_VOUCHER_WISE.LOC_CODE )  AND ( V_TRN_TB.GL_CC_CODE                              = V_TRN_GL_VOUCHER_WISE.CC_CODE )  AND ( v_trn_tb.gl_tot_acc_code                         = v_trn_gl_voucher_wise.tot_acc_code )  AND ( v_trn_tb.gl_year                                 = :toYear)  AND ( V_TRN_TB.GL_MONTH                                = :toMonth)  AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE           = T_MAS_TRANSACTION.TR_CODE )  AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR    ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) >= :fromYear    ||LPAD(:fromMonth,2,0) )  AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR    ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) <= :toYear    ||LPAD(:toMonth,2,0) )  AND V_TRN_GL_VOUCHER_WISE.GL_CODE = DECODE(:br_code,9999,V_TRN_GL_VOUCHER_WISE.GL_CODE,:br_code)     AND V_TRN_GL_VOUCHER_WISE.GL_CODE      = T_MAS_BROKER.BR_CODE  AND CUR_CODE                           = 1  AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE = 1420  AND TRIM(NVL(BR_FACIN_IND,'N'))        = 'N'  AND V_TRN_GL_VOUCHER_WISE.LOC_CODE     = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code)    AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+) = T_TRN_POLICY.POL_CCG_CODE  AND T_TRN_POLICY.POL_POLICY_TYPE       IN (:ptCode)  AND T_TRN_POLICY.POL_ISSUE_HOUR         = 3  )) tab GROUP BY tab.brokername, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory, tab.insuredname, tab.transactiontype, tab.voucherno, tab.voucherdate, tab.policyno";
	
	private final static String BRK_ACCOUNT_SEARCH_WHERE_ALL_WITHOUT_DATE = " WHERE ( V_TRN_GL_VOUCHER_WISE.POLICY_ID      = T_TRN_POLICY.POL_POLICY_ID)  " +
			" AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID           = T_TRN_POLICY.POL_ENDT_ID (+)) " +
			" AND ( T_TRN_POLICY.POL_CLASS_CODE            = t_mas_class.cl_code ) " +
			" AND ( V_TRN_TB.GL_CODE                       = V_TRN_GL_VOUCHER_WISE.GL_CODE )  AND ( V_TRN_TB.GL_CTY_CODE                   = V_TRN_GL_VOUCHER_WISE.CTY_CODE )  AND ( V_TRN_TB.GL_REG_CODE                   = V_TRN_GL_VOUCHER_WISE.REG_CODE )  AND ( V_TRN_TB.GL_LOC_CODE                   = V_TRN_GL_VOUCHER_WISE.LOC_CODE )  AND ( V_TRN_TB.GL_CC_CODE                    = V_TRN_GL_VOUCHER_WISE.CC_CODE )  AND ( v_trn_tb.gl_tot_acc_code               = v_trn_gl_voucher_wise.tot_acc_code )  AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )  AND V_TRN_GL_VOUCHER_WISE.GL_CODE            = DECODE(:br_code,9999,V_TRN_GL_VOUCHER_WISE.GL_CODE,:br_code)  AND V_TRN_GL_VOUCHER_WISE.GL_CODE      = T_MAS_BROKER.BR_CODE  AND CUR_CODE                           = 1  AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE = 1420  AND TRIM(NVL(BR_FACIN_IND,'N'))        = 'N'  AND V_TRN_GL_VOUCHER_WISE.LOC_CODE     = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code)   AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+) = T_TRN_POLICY.POL_CCG_CODE  AND T_TRN_POLICY.POL_POLICY_TYPE       IN (:ptCode)  AND T_TRN_POLICY.POL_ISSUE_HOUR         = 3  )) tab GROUP BY tab.brokername, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory, tab.insuredname, tab.transactiontype, tab.voucherno, tab.voucherdate, tab.policyno";
	
	
	private final static String BRK_ACCOUNT_SEARCH_POL_NO = " where POLICYNO = :polNo";
	private final static String UNMATCHED_BRK_ACCOUNT_SEARCH_POL_NO = " where POLICY_NO = :polNo";
	
	private final static String ACCOUNT_SEARCH_SELECT_UNMATCHED = "SELECT tab.brokername,tab.address,tab.fax,tab.phone,tab.loc_desc,tab.customercategory,tab.insuredname,tab.transactiontype,tab.voucher_no,tab.voucher_date,tab.policy_no,ABS(NVL(SUM(tab.gross),0)) gross,NVL(SUM(tab.gross),0)+NVL(SUM(tab.COM_AMOUNT),0) NET,ABS(NVL(SUM(tab.COM_AMOUNT),0)) com_amount FROM (SELECT  T_MAS_BROKER.BR_E_NAME BROKERNAME , 																 (T_MAS_BROKER.BR_E_ADDRESS1 || ' ' || T_MAS_BROKER.BR_E_ADDRESS2 || ' ' || T_MAS_BROKER.BR_E_ADDRESS3) ADDRESS, 																  br_fax FAX,																  br_telephone PHONE , 																 (SELECT  LOC_E_DESC from t_mas_location where t_mas_location.loc_code = V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE) loc_desc, 																 T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, V_TRN_GL_UNMATCHED_AIC_BRK.DTL_DESCRIPTION   INSUREDNAME,																 T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE,																 V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_NO VOUCHER_NO,   																 V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_DATE VOUCHER_DATE,   																 V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_NO POLICY_NO,    																 V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT GROSS,  																 (V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT + V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT) NET,																 V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT COM_AMOUNT ";
	
	
	private final static String ACCOUNT_SEARCH_FROM_UNMATCHED = "FROM V_TRN_GL_UNMATCHED_AIC_BRK,   	T_MAS_CLASS,   T_MAS_TRANSACTION,		T_TRN_POLICY  A, 	T_MAS_BROKER , 	T_MAS_CURRENCY ,		T_MAS_CUSTOMER_CATEGORY";
	
	
	
	private final static String ACCOUNT_SEARCH_WHERE_UNMATCHED = "  WHERE ( v_trn_gl_unmatched_aic_brk.class_code = t_mas_class.cl_code (+))   AND( V_TRN_GL_UNMATCHED_AIC_BRK.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )   AND ( V_TRN_GL_UNMATCHED_AIC_BRK.DOC_YEAR||LPAD(V_TRN_GL_UNMATCHED_AIC_BRK.DOC_MONTH,2,0) <= (select TO_CHAR(clo_date_closed,'yyyy') from t_trn_closing  where clo_tran_code = 2)||LPAD((select TO_CHAR(clo_date_closed,'MM') from t_trn_closing where clo_tran_code = 2),2,0) )    AND V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_ID  = A.POL_POLICY_ID(+)  AND V_TRN_GL_UNMATCHED_AIC_BRK.ENDT_ID = A.POL_ENDT_ID(+) AND V_TRN_GL_UNMATCHED_AIC_BRK.GL_CODE = DECODE(:br_code,9999,V_TRN_GL_UNMATCHED_AIC_BRK.GL_CODE,:br_code) AND V_TRN_GL_UNMATCHED_AIC_BRK.GL_CODE = T_MAS_BROKER.BR_CODE AND CUR_CODE = 1  AND V_TRN_GL_UNMATCHED_AIC_BRK.TOT_ACC_CODE = 1420 																 AND TRIM(NVL(BR_FACIN_IND,'N')) = 'N' 																 AND V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE,:branch_code) 																																 AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+)   = A.POL_CCG_CODE 																 AND A.POL_POLICY_TYPE in (:ptCode)																 AND A.POL_ISSUE_HOUR = 3) tab GROUP BY tab.brokername, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory, tab.insuredname, tab.transactiontype, tab.voucher_no, tab.voucher_date, tab.policy_no";
	
	

	
	private final static String LIVE_POLICIES_SEARCH_POLINFO = " (SELECT DISTINCT POL.pol_policy_no PolicyNo,BR.BR_E_NAME BrokerCompanyName,"
															   +" (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || INS.ins_e_last_name) InsuredName, "
															   +" POL.pol_conc_policy_no ConcPolicyNo,POL.pol_issue_date PolicyIssueDate, trunc(POL.pol_effective_date) PolicyEffectiveDate, "
															   +" trunc(POL.pol_expiry_date) PolicyExpiryDate, SUM(PRM.prm_premium)  ," 
															   		//+"SUM((POL.pol_commision_id*decode(PRM.prm_cov_code,101,0,PRM.prm_premium)/100)) Commision, "
															   +" SCH.sch_desc SchemeName, USR.login_id CreatedByUser " 
															   +" FROM t_trn_policy POL, T_MAS_INSURED INS, t_mas_scheme SCH, t_trn_premium PRM, T_Mas_Broker BR, t_mas_entity_map EM , t_mas_user USR  "
															   +" WHERE POL.pol_br_code  = INS.ins_br_code AND POL.pol_br_code = BR.BR_CODE AND POL.pol_insured_code = INS.ins_insured_code "
															   +" AND POL.pol_cover_note_hour = SCH.sch_code AND pol.pol_status = '1' AND pol.pol_endt_no = 0 "
															   +" AND pol.pol_policy_type in (:ptCode) AND pol.pol_ref_policy_id is null AND POL.pol_prepared_by = USR.user_id  AND pol_location_code = :locCode"
															   +" AND BR.br_code = EM.em_entity_id AND BR.br_reg_code = EM.em_region AND EM.em_branch = pol.pol_location_code";


	private final static String LIVE_POLICIES_SEARCH_WITH_BR = "  AND POL.pol_br_code in (:br_code)  AND (trunc(pol.POL_PRINT_DATE) between trunc(:start_date) AND trunc(:end_date))  AND PRM.prm_policy_id = POL.pol_policy_id AND PRM.prm_endt_id = POL.pol_endt_id "
															   +" group by POL.pol_policy_no, BR.BR_E_NAME, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || INS.ins_e_last_name), "
															   +" POL.pol_conc_policy_no, POL.pol_issue_date, POL.pol_effective_date, POL.pol_expiry_date, POL.pol_br_code, SCH.sch_desc, USR.login_id)polInfo, ";

	private final static String LIVE_POLICIES_SEARCH_WITH_ALL_BR = " AND (trunc(pol.POL_PRINT_DATE) between trunc(:start_date) AND trunc(:end_date))  AND PRM.prm_policy_id = POL.pol_policy_id AND PRM.prm_endt_id = POL.pol_endt_id "
		   +" group by POL.pol_policy_no, BR.BR_E_NAME, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || INS.ins_e_last_name), "
		   +" POL.pol_conc_policy_no, POL.pol_issue_date, POL.pol_effective_date, POL.pol_expiry_date, POL.pol_br_code, SCH.sch_desc)polInfo, ";

	private final static String LIVE_POLICIES_SEARCH_DEBITINFO =" (select max(dnd_debit_note_no) dnd_debit_note_no,dnd_policy_no,Sum(Dnd_Amount) Commision from t_trn_dtl_debit_note "
																+" where  dnd_document_code =1 and  dnd_desc like 'Less Commission' group by dnd_policy_no) debitInfo ";
	
	private final static String LIVE_POLICIES_SEARCH_PRM = "( select sum(prm_premium) PolicyPremium,pol_policy_no policy_no from t_trn_premium,t_trn_policy " +
			com.Constant.CONST_WHERE_PRM_POLICY_ID_POL_POLICY_ID_AND_PRM_ENDT_ID_POL_ENDT_ID_AND_POL_DOCUMENT_CODE_IN_1_3_AND_TRUNC_PRM_VALIDITY_EXPIRY_DATE_31_DEC_2049_GROUP_BY_POL_POLICY_NO_PREMIUM;
	//private final static String LIVE_POL_QUERY_FINAL_WITH_ALL ="select * from "+LIVE_POLICIES_SEARCH_POLINFO+LIVE_POLICIES_SEARCH_WITH_ALL_BR+LIVE_POLICIES_SEARCH_DEBITINFO+" where debitInfo.dnd_policy_no = polInfo.PolicyNo";
	
	private final static String LIVE_POL_QUERY_FINAL_WITH_ALL = "select PolicyNo,BrokerCompanyName,InsuredName, ConcPolicyNo,to_char(PolicyIssueDate,'Mon DD YYYY hh12:mi am'),to_char(PolicyEffectiveDate,'Mon DD YYYY hh12:mi am'),to_char(Policyexpirydate,'Mon DD YYYY hh12:mi am'),premium.Policypremium," +
			"Commision,Schemename,Createdbyuser,Dnd_Debit_Note_No,Dnd_Policy_No " +
			"from (SELECT DISTINCT POL.pol_policy_no PolicyNo,BR.BR_E_NAME BrokerCompanyName, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || "+
		"INS.ins_e_last_name) InsuredName,  POL.pol_conc_policy_no ConcPolicyNo, POL.pol_issue_date PolicyIssueDate, trunc(POL.pol_effective_date) PolicyEffectiveDate, trunc(POL.pol_expiry_date) PolicyExpiryDate, SUM(PRM.prm_premium)  , "+
		//"SUM((POL.pol_commision_id*decode(PRM.prm_cov_code,101,0,PRM.prm_premium)/100)) Commision," +
		" SCH.sch_desc SchemeName, USR.login_id CreatedByUser " +
		"FROM t_trn_policy POL, T_MAS_INSURED Ins, T_Mas_Scheme Sch, T_Trn_Premium Prm, T_Mas_Broker Br, t_mas_entity_map EM , t_mas_user USR" +
		" Where Pol.Pol_Br_Code  = Ins.Ins_Br_Code "+
		"AND POL.pol_br_code = BR.BR_CODE AND POL.pol_insured_code = INS.ins_insured_code  AND Pol.Pol_Cover_Note_Hour = Sch.Sch_Code And Pol.Pol_Status = '1' And Pol.Pol_Endt_No = 0 And Pol.Pol_Policy_Type in (:ptCode) And Pol.Pol_Ref_Policy_Id Is Null and pol_location_code = :locCode "+
		"AND (trunc(pol.POL_PRINT_DATE) between trunc(:start_date) AND trunc(:end_date)) And Prm.Prm_Policy_Id = Pol.Pol_Policy_Id And Prm.Prm_Endt_Id = Pol.Pol_Endt_Id AND BR.br_code = EM.em_entity_id AND BR.br_reg_code = EM.em_region AND EM.em_branch = pol.pol_location_code AND POL.pol_prepared_by = USR.user_id " +
		"group by POL.pol_policy_no, BR.BR_E_NAME, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || INS.ins_e_last_name), "+
		" POL.pol_conc_policy_no, POL.pol_issue_date, POL.pol_effective_date, POL.pol_expiry_date, POL.pol_br_code, SCH.sch_desc, USR.login_id)polInfo," +
		"(select max(dnd_debit_note_no) dnd_debit_note_no,dnd_policy_no,Sum(Dnd_Amount) Commision  from t_trn_dtl_debit_note  where "+
		"Dnd_Document_Code =1  and  dnd_desc like 'Less Commission' Group By Dnd_Policy_No) Debitinfo," +
		" ( select sum(prm_premium) PolicyPremium,pol_policy_no policy_no from t_trn_premium,t_trn_policy " +
		com.Constant.CONST_WHERE_PRM_POLICY_ID_POL_POLICY_ID_AND_PRM_ENDT_ID_POL_ENDT_ID_AND_POL_DOCUMENT_CODE_IN_1_3_AND_TRUNC_PRM_VALIDITY_EXPIRY_DATE_31_DEC_2049_GROUP_BY_POL_POLICY_NO_PREMIUM +
		"  Where Debitinfo.Dnd_Policy_No(+)  = Polinfo.Policyno   and premium.policy_no(+) =polInfo.PolicyNo " +
		"Union " +
		"Select PolicyNo,BrokerCompanyName,InsuredName, ConcPolicyNo,to_char(PolicyIssueDate,'Mon DD YYYY hh12:mi am'),to_char(PolicyEffectiveDate,'Mon DD YYYY hh12:mi am'),to_char(Policyexpirydate,'Mon DD YYYY hh12:mi am'),premium.Policypremium," +
		"Commision,SchemeName,Createdbyuser,Dnd_Debit_Note_No,Dnd_Policy_No " +
		"From (SELECT DISTINCT POL.pol_policy_no PolicyNo,'Direct' BrokerCompanyName, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || INS.ins_e_last_name) InsuredName,  POL.pol_conc_policy_no ConcPolicyNo,POL.pol_issue_date PolicyIssueDate, " +
		" trunc(POL.pol_effective_date) PolicyEffectiveDate, trunc(POL.pol_expiry_date) PolicyExpiryDate, SUM(PRM.prm_premium)  , " +
		//"SUM((POL.pol_commision_id*decode(PRM.prm_cov_code,101,0,PRM.prm_premium)/100)) Commision, " +
		"SCH.sch_desc SchemeName , USR.login_id CreatedByUser FROM t_trn_policy POL, T_MAS_INSURED "+
		"Ins, T_Mas_Scheme Sch, T_Trn_Premium Prm, t_mas_user USR Where  Pol.Pol_Insured_Code = Ins.Ins_Insured_Code AND POL.pol_br_code is null AND Pol.Pol_Cover_Note_Hour = Sch.Sch_Code And Pol.Pol_Status = '1' And Pol.Pol_Endt_No = 0 And Pol.Pol_Policy_Type in (:ptCode) " +
		"And Pol.Pol_Ref_Policy_Id Is Null and pol_location_code = :locCode AND (trunc(pol.POL_PRINT_DATE) between trunc(:start_date) AND trunc(:end_date)) And Prm.Prm_Policy_Id = Pol.Pol_Policy_Id And Prm.Prm_Endt_Id = Pol.Pol_Endt_Id AND POL.pol_prepared_by = USR.user_id GROUP BY POL.pol_policy_no, null, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || "+
		"INS.ins_e_last_name), POL.pol_conc_policy_no, POL.pol_issue_date, POL.pol_effective_date, POL.pol_expiry_date, SCH.sch_desc, USR.login_id)polInfo,  " +
		"(select max(dnd_debit_note_no) dnd_debit_note_no,dnd_policy_no,0 Commision  from t_trn_dtl_debit_note,T_TRN_POLICY  where  "+
		"dnd_policy_id = pol_policy_id and dnd_endt_id = pol_endt_id and Dnd_Document_Code =1  and POL_BR_CODE IS NULL Group By Dnd_Policy_No) debitInfo," +
	" ( select sum(prm_premium) PolicyPremium,pol_policy_no policy_no from t_trn_premium,t_trn_policy " +
	com.Constant.CONST_WHERE_PRM_POLICY_ID_POL_POLICY_ID_AND_PRM_ENDT_ID_POL_ENDT_ID_AND_POL_DOCUMENT_CODE_IN_1_3_AND_TRUNC_PRM_VALIDITY_EXPIRY_DATE_31_DEC_2049_GROUP_BY_POL_POLICY_NO_PREMIUM +
	"  where debitInfo.dnd_policy_no(+)  = polInfo.PolicyNo and premium.policy_no(+) =polInfo.PolicyNo ";

	private final static String LIVE_POL_FUNC = "SELECT * from table(PKG_PAS_QUO_POL_HOME.FN_GET_LIVE_POL_RPT (:BrokerCodearray,:BrokerCode,:start_date,:end_date,:locCode,:ptCode))";
	
	private final static String LIVE_POL_QUERY_WITH_DIRECT = "Select PolicyNo,BrokerCompanyName,InsuredName, ConcPolicyNo,to_char(PolicyIssueDate,'Mon DD YYYY hh12:mi am') ,to_char(PolicyEffectiveDate,'Mon DD YYYY hh12:mi am'),to_char(Policyexpirydate,'Mon DD YYYY hh12:mi am'),premium.Policypremium," +
	"Commision,SchemeName,Createdbyuser,Dnd_Debit_Note_No,Dnd_Policy_No " +
	"From (SELECT DISTINCT POL.pol_policy_no PolicyNo,decode('DIRECT','DIRECT','DIRECT',null)  BrokerCompanyName, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || INS.ins_e_last_name) InsuredName,  POL.pol_conc_policy_no ConcPolicyNo,POL.pol_issue_date PolicyIssueDate, " +
	" trunc(POL.pol_effective_date) PolicyEffectiveDate, trunc(POL.pol_expiry_date) PolicyExpiryDate, SUM(PRM.prm_premium)  , " +
	//"SUM((POL.pol_commision_id*decode(PRM.prm_cov_code,101,0,PRM.prm_premium)/100)) Commision, " +
	"SCH.sch_desc SchemeName , USR.login_id CreatedByUser FROM t_trn_policy POL, T_MAS_INSURED "+
	"Ins, T_Mas_Scheme Sch, T_Trn_Premium Prm, t_mas_user USR Where  Pol.Pol_Insured_Code = Ins.Ins_Insured_Code AND POL.pol_br_code is null AND Pol.Pol_Cover_Note_Hour = Sch.Sch_Code And Pol.Pol_Status = '1' And Pol.Pol_Endt_No = 0 And Pol.Pol_Policy_Type in (:ptCode) " +
	"And Pol.Pol_Ref_Policy_Id Is Null and pol_location_code = :locCode AND (trunc(pol.POL_PRINT_DATE) between trunc(:start_date) AND trunc(:end_date)) And Prm.Prm_Policy_Id = Pol.Pol_Policy_Id And Prm.Prm_Endt_Id = Pol.Pol_Endt_Id AND POL.pol_prepared_by = USR.user_id GROUP BY POL.pol_policy_no, null, (INS.ins_e_first_name || ' ' || INS.ins_e_middle_name || ' ' || "+
	"INS.ins_e_last_name), POL.pol_conc_policy_no, POL.pol_issue_date, POL.pol_effective_date, POL.pol_expiry_date, SCH.sch_desc, USR.login_id)polInfo,  " +
	"(select max(dnd_debit_note_no) dnd_debit_note_no,dnd_policy_no,0 Commision  from t_trn_dtl_debit_note,T_TRN_POLICY  where  "+
	"dnd_policy_id = pol_policy_id and dnd_endt_id = pol_endt_id and Dnd_Document_Code =1  and POL_BR_CODE IS NULL Group By Dnd_Policy_No) debitInfo," +
	"  ( select sum(prm_premium) PolicyPremium,pol_policy_no policy_no from t_trn_premium,t_trn_policy " +
	com.Constant.CONST_WHERE_PRM_POLICY_ID_POL_POLICY_ID_AND_PRM_ENDT_ID_POL_ENDT_ID_AND_POL_DOCUMENT_CODE_IN_1_3_AND_TRUNC_PRM_VALIDITY_EXPIRY_DATE_31_DEC_2049_GROUP_BY_POL_POLICY_NO_PREMIUM +
	"  where debitInfo.dnd_policy_no(+)  = polInfo.PolicyNo and premium.policy_no(+) =polInfo.PolicyNo"+
	" Union " +
	"select PolicyNo,BrokerCompanyName,InsuredName, ConcPolicyNo,to_char(PolicyIssueDate,'Mon DD YYYY hh12:mi am'),to_char(PolicyEffectiveDate,'Mon DD YYYY hh12:mi am'),to_char(Policyexpirydate,'Mon DD YYYY hh12:mi am'),Policypremium," +
	"Commision,Schemename,Createdbyuser,Dnd_Debit_Note_No,Dnd_Policy_No  from "+LIVE_POLICIES_SEARCH_POLINFO+LIVE_POLICIES_SEARCH_WITH_BR+LIVE_POLICIES_SEARCH_DEBITINFO+" , "+LIVE_POLICIES_SEARCH_PRM+
	" where debitInfo.dnd_policy_no (+)= polInfo.PolicyNo and premium.policy_no(+) =polInfo.PolicyNo";
		
		
	private final static String LIVE_POL_QUERY_FINAL_WITH_BR ="select PolicyNo,BrokerCompanyName,InsuredName, ConcPolicyNo,to_char(PolicyIssueDate,'Mon DD YYYY hh12:mi am'),to_char(PolicyEffectiveDate,'Mon DD YYYY hh12:mi am'),to_char(Policyexpirydate,'Mon DD YYYY hh12:mi am'),Policypremium," +
			"Commision,Schemename,Createdbyuser,Dnd_Debit_Note_No,Dnd_Policy_No  from "+LIVE_POLICIES_SEARCH_POLINFO+LIVE_POLICIES_SEARCH_WITH_BR+LIVE_POLICIES_SEARCH_DEBITINFO+" , "+LIVE_POLICIES_SEARCH_PRM+
			" where debitInfo.dnd_policy_no (+)= polInfo.PolicyNo and premium.policy_no(+) =polInfo.PolicyNo";
	
	private final static String CLAS_WISE_PRM_INFO = "select INS_E_FIRST_NAME, POL_POLICY_TYPE, POL_POLICY_NO, POL_PREMIUM, " 
												+" CSH_E_NAME_1, nvl(COMMISSION,0) COMMISSION, TO_CHAR(POL_ISSUE_DATE,'dd/MM/YYYY'), ISSUE_MONTH, ISSUE_YEAR, TO_CHAR(POL_EFFECTIVE_DATE,'dd/MM/YYYY'), " 
												+"  PRM_SUM_INSURED, POL_PREPARED_BY,(SELECT cdm_code_desc FROM T_MAS_CODE_MASTER WHERE CDM_entity_desc='PAS_LOBMAP' and cdm_code=ptcode),CL_E_DESC  from V_TRN_CLS_PRM_SI_PAS " 
												+" where  trunc(POL_PRINT_DATE) between trunc(:start_date) and trunc(:end_date) and POL_LOCATION_CODE = :locCode and PTCODE in (:ptCode)";
	
private final static String CLAS_WISE_PRM_INFO_OMN = "select INS_E_FIRST_NAME,  POL_POLICY_TYPE, POL_POLICY_NO, POL_PREMIUM, " 
		+" CSH_E_NAME_1, nvl(COMMISSION,0) COMMISSION, TO_CHAR(POL_ISSUE_DATE,'dd/MM/YYYY'), ISSUE_MONTH, ISSUE_YEAR, TO_CHAR(POL_EFFECTIVE_DATE,'dd/MM/YYYY'), " 
		+"  PRM_SUM_INSURED, POL_PREPARED_BY,(SELECT cdm_code_desc FROM T_MAS_CODE_MASTER WHERE CDM_entity_desc='PAS_LOBMAP' and cdm_code=ptcode) ,POL_POLICY_ID,CL_E_DESC  from V_TRN_CLS_PRM_SI_PAS " 
		+" where  trunc(POL_PRINT_DATE) between trunc(:start_date) and trunc(:end_date) and POL_LOCATION_CODE = :locCode and PTCODE in (:ptCode)";

	private final static String CLAS_WISE_PRM_BR_CODE = " and  POL_BR_CODE in (:br_code)";
	
	private final static String CLAS_WISE_PRM_DIRECT = " and  ( POL_BR_CODE is null or POL_BR_CODE in (:br_code) )";
	
	private final static String UNMATCHED_ACCOUNT_SEARCH_FOR_ALL_POLICY_QRY = "select tab.name" +
			"	,	tab.address" +
			"	,	tab.fax" +
			"	,	tab.phone" +
			"	,	tab.loc_desc" +
			"	,	tab.customercategory" +
			",	tab.insuredname" +
			",	tab.transactiontype" +
			"	,	tab.voucher_no" +
			",	tab.voucher_date" +
			"	,	tab.policy_no" +
			"	,	" +
			"	ABS(NVL(SUM(tab.gross),0)) gross," +
			"	NVL(SUM(tab.gross),0)+NVL(SUM(tab.COM_AMOUNT),0) NET," +
			"	ABS(NVL(SUM(tab.COM_AMOUNT),0)) com_amount" +
			"	from " +
			"	(( SELECT T_MAS_BROKER.BR_E_NAME NAME ," +
			"	(T_MAS_BROKER.BR_E_ADDRESS1 || ' ' || T_MAS_BROKER.BR_E_ADDRESS2 || ' ' || T_MAS_BROKER.BR_E_ADDRESS3) ADDRESS," +
			"	br_fax FAX," +
			"	br_telephone PHONE ," +
			"	(SELECT  LOC_E_DESC from t_mas_location where t_mas_location.loc_code = V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE) loc_desc," +
			"	T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, V_TRN_GL_UNMATCHED_AIC_BRK.DTL_DESCRIPTION   INSUREDNAME," +
			"	T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE," +
			"	V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_NO VOUCHER_NO," +
			"	V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_DATE VOUCHER_DATE," +
			"	V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_NO POLICY_NO," +
			"	V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT GROSS," +
//			"	--(V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT + V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT) NET,  " +
			"	V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT COM_AMOUNT" +
			"	FROM" +
			"	 V_TRN_GL_UNMATCHED_AIC_BRK," +
			"	T_MAS_CLASS,	T_MAS_TRANSACTION," +
			"		T_TRN_POLICY  POL,   T_MAS_BROKER ," +
			"	T_MAS_CURRENCY ,	T_MAS_CUSTOMER_CATEGORY" +
			"	WHERE ( v_trn_gl_unmatched_aic_brk.class_code = t_mas_class.cl_code )" +
			"	AND( V_TRN_GL_UNMATCHED_AIC_BRK.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )" +
			"	AND ( V_TRN_GL_UNMATCHED_AIC_BRK.DOC_YEAR||LPAD(V_TRN_GL_UNMATCHED_AIC_BRK.DOC_MONTH,2,0) <=" +
			"	(select TO_CHAR(clo_date_closed,'yyyy') from t_trn_closing  where clo_tran_code = 2)||LPAD((select TO_CHAR(clo_date_closed,'MM') " +
			"	from t_trn_closing where clo_tran_code = 2),2,0) )" +
			"	AND V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_ID  = POL.POL_POLICY_ID" +
			"	AND V_TRN_GL_UNMATCHED_AIC_BRK.ENDT_ID = POL.POL_ENDT_ID" +
			"	AND V_TRN_GL_UNMATCHED_AIC_BRK.GL_CODE = T_MAS_BROKER.BR_CODE" +
			"	AND CUR_CODE = 1" +
			"	AND V_TRN_GL_UNMATCHED_AIC_BRK.TOT_ACC_CODE = 1420	" +
			"	AND TRIM(NVL(BR_FACIN_IND,'N')) = 'N'" +
			"	AND V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE,:branch_code)" +
			"	AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE   = POL.POL_CCG_CODE" +
			"	AND POL.POL_POLICY_TYPE in (:ptCode)" +
			"	AND POL.POL_ISSUE_HOUR = 3 )" +
			"				" +
			" 	UNION" +
			"		select * from (" +
			"	SELECT decode('DIRECT','DIRECT','DIRECT',null) NAME ," +
			"                  '' ADDRESS," +
			"               null FAX," +
			"               null PHONE ," +
			"               (SELECT  LOC_E_DESC from t_mas_location where t_mas_location.loc_code = V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE) loc_desc," +
			"               T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, V_TRN_GL_UNMATCHED_AIC_BRK.DTL_DESCRIPTION   INSUREDNAME," +
			"               T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE," +
			"               V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_NO VOUCHER_NO," +
			"               V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_DATE VOUCHER_DATE," +
			"               V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_NO POLICY_NO," +
			"               V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT GROSS," +
//			"               --(V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT + V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT) NET," +
			"	               V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT COM_AMOUNT" +
			"            FROM V_TRN_GL_UNMATCHED_AIC_BRK," +
			"                 T_MAS_CLASS," +
			"                 T_MAS_TRANSACTION," +
			"                 T_TRN_POLICY  POL," +
			"                 T_MAS_CURRENCY ," +
			"                 T_MAS_CUSTOMER_CATEGORY" +
			"       WHERE " +
			"	( v_trn_gl_unmatched_aic_brk.class_code = t_mas_class.cl_code )" +
			"	AND( V_TRN_GL_UNMATCHED_AIC_BRK.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )" +
			"     AND ( V_TRN_GL_UNMATCHED_AIC_BRK.DOC_YEAR||LPAD(V_TRN_GL_UNMATCHED_AIC_BRK.DOC_MONTH,2,0) <=" +
			"               (select TO_CHAR(clo_date_closed,'yyyy') from t_trn_closing  " +
			"	where clo_tran_code = 2)||LPAD((select TO_CHAR(clo_date_closed,'MM') from t_trn_closing where clo_tran_code = 2),2,0) )" +
			"               AND V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_ID  = POL.POL_POLICY_ID" +
			"               AND V_TRN_GL_UNMATCHED_AIC_BRK.ENDT_ID = POL.POL_ENDT_ID" +
			"               AND CUR_CODE = 1" +
			"               AND V_TRN_GL_UNMATCHED_AIC_BRK.TOT_ACC_CODE = 1410" +
			"               AND V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE,:branch_code)" +
			"               AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE   = POL.POL_CCG_CODE" +
			"               AND POL.POL_POLICY_TYPE in (:ptCode)" +
			"               AND POL.POL_ISSUE_HOUR = 3" +
			"             AND POL.POL_BR_CODE is null" +
			"	))tab" +
			"		GROUP BY " +
			"	tab.name," +
			"	tab.address," +
			"	tab.fax," +
			"	tab.phone," +
			"	tab.loc_desc," +
			"	tab.customercategory," +
			"	tab.insuredname," +
			"	tab.transactiontype," +
			"	tab.voucher_no," +
			"	tab.voucher_date," +
			"	tab.policy_no" ;
	
	/*Changes made for CR 92593 by Tuhir
	CR 92593: Account Statement Report in ePlatform */
	
	
	
	private final static String UNMATCHED_ACCOUNT_SEARCH_FOR_DIRECT_POLICY_QRY =  "SELECT  tab.name,tab.address,tab.fax,tab.phone,tab.loc_desc,tab.customercategory,tab.insuredname,tab.transactiontype,tab.voucher_no,tab.voucher_date,tab.policy_no,ABS(NVL(SUM(tab.gross),0)) gross,NVL(SUM(tab.gross),0)+NVL(SUM(tab.COM_AMOUNT),0) NET,ABS(NVL(SUM(tab.COM_AMOUNT),0)) com_amount FROM (SELECT decode('DIRECT','DIRECT','DIRECT',null) NAME ,  			 '' ADDRESS,  			 null FAX, 			 null PHONE ,  			 (SELECT  LOC_E_DESC from t_mas_location where t_mas_location.loc_code = V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE) loc_desc,  			 T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, V_TRN_GL_UNMATCHED_AIC_BRK.DTL_DESCRIPTION   INSUREDNAME,			 T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE, 			 V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_NO VOUCHER_NO,    			 V_TRN_GL_UNMATCHED_AIC_BRK.VOUCHER_DATE VOUCHER_DATE,    			 V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_NO POLICY_NO,     			 V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT GROSS,   			 (V_TRN_GL_UNMATCHED_AIC_BRK.SUM_AMOUNT + V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT) NET, 			 V_TRN_GL_UNMATCHED_AIC_BRK.COM_AMOUNT COM_AMOUNT 		 FROM V_TRN_GL_UNMATCHED_AIC_BRK,    			  T_MAS_CLASS,    			  T_MAS_TRANSACTION, 			  T_TRN_POLICY  POL,   			  T_MAS_CURRENCY , 			  T_MAS_CUSTOMER_CATEGORY 		 WHERE ( v_trn_gl_unmatched_aic_brk.class_code = t_mas_class.cl_code )    			AND( V_TRN_GL_UNMATCHED_AIC_BRK.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )    			AND ( V_TRN_GL_UNMATCHED_AIC_BRK.DOC_YEAR||LPAD(V_TRN_GL_UNMATCHED_AIC_BRK.DOC_MONTH,2,0) <=  			(select TO_CHAR(clo_date_closed,'yyyy') from t_trn_closing  where clo_tran_code = 2)||LPAD((select TO_CHAR(clo_date_closed,'MM') from t_trn_closing where clo_tran_code = 2),2,0) )     			AND V_TRN_GL_UNMATCHED_AIC_BRK.POLICY_ID  = POL.POL_POLICY_ID   			AND V_TRN_GL_UNMATCHED_AIC_BRK.ENDT_ID = POL.POL_ENDT_ID 			AND CUR_CODE = 1   			AND V_TRN_GL_UNMATCHED_AIC_BRK.TOT_ACC_CODE = 1410  			AND V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_UNMATCHED_AIC_BRK.LOC_CODE,:branch_code) 			AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE   = POL.POL_CCG_CODE 			AND POL.POL_POLICY_TYPE in (:ptCode) 			AND POL.POL_ISSUE_HOUR = 3 		 AND POL.POL_BR_CODE is null     ) tab GROUP BY tab.name, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory, tab.insuredname, tab.transactiontype, tab.voucher_no, tab.voucher_date, tab.policy_no";
	
	
	/*Changes made for CR 92593 by Tuhir
	CR 92593: Account Statement Report in ePlatform */
	
	private final static String  ALL_ACCOUNT_SEARCH_FOR_ALL_POLICY_QRY =   "SELECT  tab.brokername ," +
			"tab.address,tab.fax,tab.phone, tab.loc_desc,tab.customercategory,tab.insuredname,tab.transactiontype,tab.voucherno," +
			"tab.voucherdate,tab.policyno,ABS(NVL(SUM(tab.gross),0)) gross," +
			"NVL(SUM(tab.gross),0)+NVL(SUM(tab.COMAMOUNT),0) NET,ABS(NVL(SUM(tab.COMAMOUNT),0)) comamount " +
			"FROM (SELECT * FROM  (SELECT T_MAS_BROKER.BR_E_NAME BROKERNAME,    " +
			"( T_MAS_BROKER.BR_E_ADDRESS1    || ' '    || T_MAS_BROKER.BR_E_ADDRESS2    || ' '    || T_MAS_BROKER.BR_E_ADDRESS3) ADDRESS," +
			"    T_MAS_BROKER.BR_FAX FAX,    T_MAS_BROKER.BR_TELEPHONE PHONE,  " +
			"  (SELECT LOC_E_DESC    FROM t_mas_location   " +
			" WHERE t_mas_location.loc_code = V_TRN_GL_VOUCHER_WISE.LOC_CODE    ) LOC_DESC, " +
			"   T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY,    V_TRN_GL_VOUCHER_WISE.DTL_DESCRIPTION INSUREDNAME, " +
			"   T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE,   V_TRN_GL_VOUCHER_WISE.VOUCHER_NO VOUCHERNO, " +
			"   V_TRN_GL_VOUCHER_WISE.VOUCHER_DATE VOUCHERDATE,    V_TRN_GL_VOUCHER_WISE.POLICY_NO POLICYNO, " +
			"   V_TRN_GL_VOUCHER_WISE.AMOUNT GROSS,    (V_TRN_GL_VOUCHER_WISE.AMOUNT+V_TRN_GL_VOUCHER_WISE.COM_AMOUNT) NET, " +
			"   V_TRN_GL_VOUCHER_WISE.COM_AMOUNT COMAMOUNT  FROM V_TRN_GL_VOUCHER_WISE,    V_TRN_TB,    T_MAS_CLASS,    T_MAS_TRANSACTION,  " +
			"  T_TRN_POLICY,    T_MAS_BROKER,    T_MAS_CURRENCY,    T_MAS_CUSTOMER_CATEGORY   WHERE ( V_TRN_GL_VOUCHER_WISE.POLICY_ID      = T_TRN_POLICY.POL_POLICY_ID) " +
			" AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID           = T_TRN_POLICY.POL_ENDT_ID (+))  AND ( T_TRN_POLICY.POL_CLASS_CODE            = t_mas_class.cl_code ) " +
			" AND ( V_TRN_TB.GL_CODE                       = V_TRN_GL_VOUCHER_WISE.GL_CODE )  AND ( V_TRN_TB.GL_CTY_CODE                   = V_TRN_GL_VOUCHER_WISE.CTY_CODE ) " +
			" AND ( V_TRN_TB.GL_REG_CODE                   = V_TRN_GL_VOUCHER_WISE.REG_CODE ) " +
			" AND ( V_TRN_TB.GL_LOC_CODE                   = V_TRN_GL_VOUCHER_WISE.LOC_CODE ) " +
			" AND ( V_TRN_TB.GL_CC_CODE                    = V_TRN_GL_VOUCHER_WISE.CC_CODE )  AND ( v_trn_tb.gl_tot_acc_code               = v_trn_gl_voucher_wise.tot_acc_code ) " +
			" AND v_trn_tb.gl_year                         = :toYear  AND V_TRN_TB.GL_MONTH                        = :toMonth " +
			"  AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE ) " +
			" AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR    ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) >= :fromYear    ||LPAD(:fromMonth,2,0) ) " +
			" AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR    ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) <= :toYear    ||LPAD(:toMonth,2,0) ) " +
			" AND V_TRN_GL_VOUCHER_WISE.GL_CODE       = T_MAS_BROKER.BR_CODE  AND CUR_CODE                            = 1  AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE  = 1420 " +
			" AND TRIM(NVL(BR_FACIN_IND,'N'))         = 'N'  AND V_TRN_GL_VOUCHER_WISE.LOC_CODE      = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code) " +
			" AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+) = T_TRN_POLICY.POL_CCG_CODE  AND T_TRN_POLICY.POL_POLICY_TYPE       IN (:ptCode)  AND T_TRN_POLICY.POL_ISSUE_HOUR         = 3  ) " +
			"UNION SELECT * FROM  (SELECT DECODE('DIRECT','DIRECT','DIRECT',NULL) BROKERNAME,    '' ADDRESS," +
			"  NULL FAX,     NULL PHONE,    (SELECT LOC_E_DESC    FROM t_mas_location    WHERE t_mas_location.loc_code = V_TRN_GL_VOUCHER_WISE.LOC_CODE    ) LOC_DESC, " +
			" T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY,    V_TRN_GL_VOUCHER_WISE.DTL_DESCRIPTION INSUREDNAME,    T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE,  " +
			"  V_TRN_GL_VOUCHER_WISE.VOUCHER_NO VOUCHERNO,    V_TRN_GL_VOUCHER_WISE.VOUCHER_DATE VOUCHERDATE,   " +
			" V_TRN_GL_VOUCHER_WISE.POLICY_NO POLICYNO,    V_TRN_GL_VOUCHER_WISE.AMOUNT GROSS,    (V_TRN_GL_VOUCHER_WISE.AMOUNT+V_TRN_GL_VOUCHER_WISE.COM_AMOUNT) NET,  " +
			"  V_TRN_GL_VOUCHER_WISE.COM_AMOUNT COMAMOUNT " +
			" FROM V_TRN_GL_VOUCHER_WISE,    V_TRN_TB,    T_MAS_CLASS,    T_MAS_TRANSACTION,    T_TRN_POLICY,  " +
			"  T_MAS_CURRENCY,    T_MAS_CUSTOMER_CATEGORY  WHERE ( V_TRN_GL_VOUCHER_WISE.POLICY_ID      = T_TRN_POLICY.POL_POLICY_ID) " +
			" AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID           = T_TRN_POLICY.POL_ENDT_ID (+))   AND ( T_TRN_POLICY.POL_CLASS_CODE            = t_mas_class.cl_code ) " +
			" AND ( V_TRN_TB.GL_CODE                       = V_TRN_GL_VOUCHER_WISE.GL_CODE )  AND ( V_TRN_TB.GL_CTY_CODE                   = V_TRN_GL_VOUCHER_WISE.CTY_CODE ) " +
			" AND ( V_TRN_TB.GL_REG_CODE                   = V_TRN_GL_VOUCHER_WISE.REG_CODE ) AND ( V_TRN_TB.GL_LOC_CODE                   = V_TRN_GL_VOUCHER_WISE.LOC_CODE ) " +
			" AND ( V_TRN_TB.GL_CC_CODE                    = V_TRN_GL_VOUCHER_WISE.CC_CODE )   AND ( v_trn_tb.gl_tot_acc_code               = v_trn_gl_voucher_wise.tot_acc_code ) " +
			" AND v_trn_tb.gl_year                         = :toYear  AND V_TRN_TB.GL_MONTH                        = :toMonth " +
			" AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE ) " +
			" AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR    ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) >= :fromYear   ||LPAD(:fromMonth,2,0)) " +
			" AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR    ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) <= :toYear    ||LPAD(:toMonth,2,0) )  " +
			" AND CUR_CODE                            = 1  AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE  = 1410 " +
			" AND V_TRN_GL_VOUCHER_WISE.LOC_CODE      = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code) " +
			" AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+) = T_TRN_POLICY.POL_CCG_CODE  AND T_TRN_POLICY.POL_POLICY_TYPE       IN (:ptCode) " +
			" AND T_TRN_POLICY.POL_ISSUE_HOUR         = 3  AND T_TRN_POLICY.POL_BR_CODE           IS NULL   )   ) tab " +
			" GROUP BY tab.brokername, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory," +
			" tab.insuredname, tab.transactiontype, tab.voucherno, tab.voucherdate, tab.policyno";
	
	/*Changes made for CR 92593 by Tuhir
	CR 92593: Account Statement Report in ePlatform */
	
	private final static String ALL_ACCOUNT_SEARCH_FOR_ALL_POLICY_QRY_WITHOUT_DATE =  "SELECT  tab.brokername,tab.address,tab.fax,tab.phone,tab.loc_desc,tab.customercategory,tab.insuredname,tab.transactiontype,tab.voucherno,tab.voucherdate,tab.policyno,ABS(NVL(SUM(tab.gross),0)) gross,NVL(SUM(tab.gross),0)+NVL(SUM(tab.COMAMOUNT),0) NET,ABS(NVL(SUM(tab.COMAMOUNT),0)) comamount FROM  ( select * from ( 			  SELECT distinct T_MAS_BROKER.BR_E_NAME BROKERNAME, ( T_MAS_BROKER.BR_E_ADDRESS1 || ' ' || T_MAS_BROKER.BR_E_ADDRESS2 || ' ' || T_MAS_BROKER.BR_E_ADDRESS3) ADDRESS,   	T_MAS_BROKER.BR_FAX FAX,  T_MAS_BROKER.BR_TELEPHONE PHONE,   			      (SELECT LOC_E_DESC FROM t_mas_location WHERE t_mas_location.loc_code = V_TRN_GL_VOUCHER_WISE.LOC_CODE) LOC_DESC,   			      T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, 			     V_TRN_GL_VOUCHER_WISE.DTL_DESCRIPTION   INSUREDNAME,       			      	T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE,  			      	V_TRN_GL_VOUCHER_WISE.VOUCHER_NO VOUCHERNO,  			      	V_TRN_GL_VOUCHER_WISE.VOUCHER_DATE VOUCHERDATE,             			      	V_TRN_GL_VOUCHER_WISE.POLICY_NO POLICYNO,   			      	V_TRN_GL_VOUCHER_WISE.AMOUNT GROSS,  			      	(V_TRN_GL_VOUCHER_WISE.AMOUNT+V_TRN_GL_VOUCHER_WISE.COM_AMOUNT) NET,      			      	V_TRN_GL_VOUCHER_WISE.COM_AMOUNT COMAMOUNT  			    	FROM V_TRN_GL_VOUCHER_WISE,  V_TRN_TB, T_MAS_TRANSACTION,      T_TRN_POLICY,  T_MAS_BROKER,   T_MAS_CUSTOMER_CATEGORY 			    	WHERE ( V_TRN_GL_VOUCHER_WISE.POLICY_ID = T_TRN_POLICY.POL_POLICY_ID)   			    	AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID = T_TRN_POLICY.POL_ENDT_ID (+))   			    	AND ( V_TRN_TB.GL_CODE                       = V_TRN_GL_VOUCHER_WISE.GL_CODE )   			    	AND ( V_TRN_TB.GL_CTY_CODE                   = V_TRN_GL_VOUCHER_WISE.CTY_CODE )  			    	AND ( V_TRN_TB.GL_REG_CODE                   = V_TRN_GL_VOUCHER_WISE.REG_CODE )  			    	AND ( V_TRN_TB.GL_LOC_CODE                   = V_TRN_GL_VOUCHER_WISE.LOC_CODE )  			    	AND ( V_TRN_TB.GL_CC_CODE                    = V_TRN_GL_VOUCHER_WISE.CC_CODE ) 			    	AND ( v_trn_tb.gl_tot_acc_code               = v_trn_gl_voucher_wise.tot_acc_code )   			    	AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )  			    	AND V_TRN_GL_VOUCHER_WISE.GL_CODE      = T_MAS_BROKER.BR_CODE  			    	AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE= 1420                    			    	AND TRIM(NVL(BR_FACIN_IND,'N')) = 'N'       			    	AND V_TRN_GL_VOUCHER_WISE.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code)  			    	AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+)   = T_TRN_POLICY.POL_CCG_CODE          			    	AND T_TRN_POLICY.POL_POLICY_TYPE in (:ptCode)  			    	AND T_TRN_POLICY.POL_ISSUE_HOUR = 3 ) 			    						    	UNION	 			      					    	select * from ( 			    	SELECT distinct decode('DIRECT','DIRECT','DIRECT',null) BROKERNAME,  '' ADDRESS,          			    	null FAX, 			           null PHONE,  			    	(SELECT LOC_E_DESC FROM t_mas_location WHERE t_mas_location.loc_code = V_TRN_GL_VOUCHER_WISE.LOC_CODE) LOC_DESC,  			    	T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, V_TRN_GL_VOUCHER_WISE.DTL_DESCRIPTION   INSUREDNAME,             			    	T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE,  			    	V_TRN_GL_VOUCHER_WISE.VOUCHER_NO VOUCHERNO,V_TRN_GL_VOUCHER_WISE.VOUCHER_DATE VOUCHERDATE, V_TRN_GL_VOUCHER_WISE.POLICY_NO POLICYNO, 			    	V_TRN_GL_VOUCHER_WISE.AMOUNT GROSS,(V_TRN_GL_VOUCHER_WISE.AMOUNT+V_TRN_GL_VOUCHER_WISE.COM_AMOUNT) NET,V_TRN_GL_VOUCHER_WISE.COM_AMOUNT COMAMOUNT  			    	FROM V_TRN_GL_VOUCHER_WISE,        			    	V_TRN_TB, T_MAS_TRANSACTION, T_TRN_POLICY,  T_MAS_CUSTOMER_CATEGORY 			    	WHERE ( V_TRN_GL_VOUCHER_WISE.POLICY_ID = T_TRN_POLICY.POL_POLICY_ID)   			    	AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID = T_TRN_POLICY.POL_ENDT_ID (+))   			    	AND ( V_TRN_TB.GL_CODE                       = V_TRN_GL_VOUCHER_WISE.GL_CODE )   			    	AND ( V_TRN_TB.GL_CTY_CODE                   = V_TRN_GL_VOUCHER_WISE.CTY_CODE )  			    	AND ( V_TRN_TB.GL_REG_CODE                   = V_TRN_GL_VOUCHER_WISE.REG_CODE ) 			    	AND ( V_TRN_TB.GL_LOC_CODE                   = V_TRN_GL_VOUCHER_WISE.LOC_CODE ) 			    	AND ( V_TRN_TB.GL_CC_CODE                    = V_TRN_GL_VOUCHER_WISE.CC_CODE )   			    	AND ( v_trn_tb.gl_tot_acc_code               = v_trn_gl_voucher_wise.tot_acc_code )     			    		AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE ) 			    		AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE = 1410          			    		AND V_TRN_GL_VOUCHER_WISE.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code)  			    		AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+)   = T_TRN_POLICY.POL_CCG_CODE        			    		AND T_TRN_POLICY.POL_POLICY_TYPE in (:ptCode)  			    		AND T_TRN_POLICY.POL_ISSUE_HOUR = 3          			    		AND T_TRN_POLICY.POL_BR_CODE is null  )			    		) tab GROUP BY tab.brokername, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory, tab.insuredname, tab.transactiontype, tab.voucherno, tab.voucherdate, tab.policyno";
	
	private final static String ALL_ACCOUNT_SEARCH_FOR_DIRECT_POLICY_QRY = "SELECT tab.brokername,tab.address,tab.fax,tab.phone, tab.loc_desc,tab.customercategory,tab.insuredname,tab.transactiontype,tab.voucherno,tab.voucherdate,tab.policyno,ABS(NVL(SUM(tab.gross),0)) gross,NVL(SUM(tab.gross),0)+NVL(SUM(tab.COMAMOUNT),0) NET,ABS(NVL(SUM(tab.COMAMOUNT),0)) comamount FROM (SELECT decode('DIRECT','DIRECT','DIRECT',null) BROKERNAME, 	'' ADDRESS, null FAX, null PHONE, (SELECT LOC_E_DESC FROM t_mas_location WHERE t_mas_location.loc_code = V_TRN_GL_VOUCHER_WISE.LOC_CODE) LOC_DESC, T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, V_TRN_GL_VOUCHER_WISE.DTL_DESCRIPTION   INSUREDNAME, T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE, V_TRN_GL_VOUCHER_WISE.VOUCHER_NO VOUCHERNO, V_TRN_GL_VOUCHER_WISE.VOUCHER_DATE VOUCHERDATE, V_TRN_GL_VOUCHER_WISE.POLICY_NO POLICYNO, V_TRN_GL_VOUCHER_WISE.AMOUNT GROSS, (V_TRN_GL_VOUCHER_WISE.AMOUNT+V_TRN_GL_VOUCHER_WISE.COM_AMOUNT) NET, V_TRN_GL_VOUCHER_WISE.COM_AMOUNT COMAMOUNT  FROM V_TRN_GL_VOUCHER_WISE, V_TRN_TB,T_MAS_CLASS, T_MAS_TRANSACTION, T_TRN_POLICY, T_MAS_CURRENCY,  T_MAS_CUSTOMER_CATEGORY WHERE ( V_TRN_GL_VOUCHER_WISE.POLICY_ID = T_TRN_POLICY.POL_POLICY_ID)  AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID = T_TRN_POLICY.POL_ENDT_ID (+))  AND ( T_TRN_POLICY.POL_CLASS_CODE       = t_mas_class.cl_code )  AND ( V_TRN_TB.GL_CODE                       = V_TRN_GL_VOUCHER_WISE.GL_CODE )  AND ( V_TRN_TB.GL_CTY_CODE                   = V_TRN_GL_VOUCHER_WISE.CTY_CODE )  AND ( V_TRN_TB.GL_REG_CODE                   = V_TRN_GL_VOUCHER_WISE.REG_CODE )  AND ( V_TRN_TB.GL_LOC_CODE                   = V_TRN_GL_VOUCHER_WISE.LOC_CODE )  AND ( V_TRN_TB.GL_CC_CODE                    = V_TRN_GL_VOUCHER_WISE.CC_CODE )  AND ( v_trn_tb.gl_tot_acc_code               = v_trn_gl_voucher_wise.tot_acc_code )  AND ( v_trn_tb.gl_year                       = :toYear)  AND ( V_TRN_TB.GL_MONTH                      = :toMonth)  AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )  AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) >= :fromYear ||LPAD(:fromMonth,2,0) )  AND ( V_TRN_GL_VOUCHER_WISE.DOC_YEAR ||LPAD(V_TRN_GL_VOUCHER_WISE.DOC_MONTH,2,0) <= :toYear ||LPAD(:toMonth,2,0) )  AND CUR_CODE                           = 1  AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE = 1410  AND V_TRN_GL_VOUCHER_WISE.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code) AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+)   = T_TRN_POLICY.POL_CCG_CODE AND T_TRN_POLICY.POL_POLICY_TYPE in (:ptCode) AND T_TRN_POLICY.POL_ISSUE_HOUR = 3 AND T_TRN_POLICY.POL_BR_CODE is null) tab GROUP BY tab.brokername, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory, tab.insuredname, tab.transactiontype, tab.voucherno, tab.voucherdate, tab.policyno";

	
	/*Changes made for CR 92593 by Tuhir
	CR 92593: Account Statement Report in ePlatform */
	
	private final static String ALL_ACCOUNT_SEARCH_FOR_DIRECT_POLICY_QRY_WITHOUT_DATE = "SELECT tab.brokername ,  tab.address ,  tab.fax ,  tab.phone ,  tab.loc_desc ,  tab.customercategory ,  tab.insuredname ,  tab.transactiontype ,  tab.voucherno ,  tab.voucherdate ,  tab.policyno ,  ABS(NVL(SUM(tab.gross),0)) gross ,  NVL(SUM(tab.gross),0)+NVL(SUM(tab.COMAMOUNT),0) NET ,  ABS(NVL(SUM(tab.COMAMOUNT),0)) comamount FROM (                                                 SELECT distinct decode('DIRECT','DIRECT','DIRECT',null) BROKERNAME,  '' ADDRESS,         null FAX,       null PHONE,                                    (SELECT LOC_E_DESC FROM t_mas_location WHERE t_mas_location.loc_code = V_TRN_GL_VOUCHER_WISE.LOC_CODE) LOC_DESC,                                         T_MAS_CUSTOMER_CATEGORY.CCG_E_DESC CUSTOMERCATEGORY, V_TRN_GL_VOUCHER_WISE.DTL_DESCRIPTION   INSUREDNAME,                                               T_MAS_TRANSACTION.TR_E_DESC TRANSACTIONTYPE,                                          V_TRN_GL_VOUCHER_WISE.VOUCHER_NO VOUCHERNO,                                                                  V_TRN_GL_VOUCHER_WISE.VOUCHER_DATE VOUCHERDATE,                                                                  V_TRN_GL_VOUCHER_WISE.POLICY_NO POLICYNO,                                                              V_TRN_GL_VOUCHER_WISE.AMOUNT GROSS,                                                      (V_TRN_GL_VOUCHER_WISE.AMOUNT+V_TRN_GL_VOUCHER_WISE.COM_AMOUNT) NET,                                                      V_TRN_GL_VOUCHER_WISE.COM_AMOUNT COMAMOUNT                                                      FROM V_TRN_GL_VOUCHER_WISE,                                           V_TRN_TB, T_MAS_TRANSACTION, T_TRN_POLICY,  T_MAS_CUSTOMER_CATEGORY                                                            WHERE                                                 ( V_TRN_GL_VOUCHER_WISE.POLICY_ID = T_TRN_POLICY.POL_POLICY_ID)                                                                                                        AND (V_TRN_GL_VOUCHER_WISE.ENDT_ID = T_TRN_POLICY.POL_ENDT_ID (+))                                                                                             AND ( V_TRN_TB.GL_CODE                       = V_TRN_GL_VOUCHER_WISE.GL_CODE )                                                                                                            AND ( V_TRN_TB.GL_CTY_CODE                   = V_TRN_GL_VOUCHER_WISE.CTY_CODE )                                                                                                AND ( V_TRN_TB.GL_REG_CODE                   = V_TRN_GL_VOUCHER_WISE.REG_CODE )                                                                                                                   AND ( V_TRN_TB.GL_LOC_CODE                   = V_TRN_GL_VOUCHER_WISE.LOC_CODE )                                                                                              AND ( V_TRN_TB.GL_CC_CODE                    = V_TRN_GL_VOUCHER_WISE.CC_CODE )                                                                                        AND ( v_trn_tb.gl_tot_acc_code               = v_trn_gl_voucher_wise.tot_acc_code )                                                                           AND ( V_TRN_GL_VOUCHER_WISE.TRANSACTION_TYPE = T_MAS_TRANSACTION.TR_CODE )                                                                                               AND V_TRN_GL_VOUCHER_WISE.TOT_ACC_CODE = 1410                                                                                            AND V_TRN_GL_VOUCHER_WISE.LOC_CODE = DECODE(:branch_code,99999,V_TRN_GL_VOUCHER_WISE.LOC_CODE,:branch_code)                                                                    AND T_MAS_CUSTOMER_CATEGORY.CCG_CODE(+)   = T_TRN_POLICY.POL_CCG_CODE                                                                                                 AND T_TRN_POLICY.POL_POLICY_TYPE in (:ptCode)                                                                                         AND T_TRN_POLICY.POL_ISSUE_HOUR = 3                                                                                          AND T_TRN_POLICY.POL_BR_CODE is null  ) tab               GROUP BY tab.brokername, tab.address, tab.fax, tab.phone, tab.loc_desc, tab.customercategory, tab.insuredname, tab.transactiontype, tab.voucherno, tab.voucherdate, tab.policyno";
	
	private final static String GET_SMS_MASTER_DETAILS = "from TMasSms";

	private static final String GET_SMS_DETAIL = "from TMasSms sms where sms.smsID=?";
	/* (non-Javadoc)
	 * @see com.rsaame.pas.reports.dao.IReportsDAO#acctSearch(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	
	private final static String GET_PAYMENT_RECORD = "Select insurance_type, pol_issue_date, policy_number, effective_date," +
			"pol_expiry_date, name, pay_date, amount, receipt_number, transaction_number, status, cast(quotation_status as VARCHAR2(12))" +
			" From V_PAYMENT_REPORT_PAS Where trunc(Pol_Issue_Date)>=:Start_date "
		+" and trunc(Pol_Issue_Date)<=:end_date";
	
//	private static final String FETCH_NRI_DETAILS_PROC = "{call PKG_PAS_QUO_POL_HOME.PRO_TRANSACTION_RPT(?,?,?,?)}";
	
	private final static String GET_RENEWAL_PAYMENT_RECORD = "Select insurance_type, pol_policy_no, csh_e_name, pol_effective_date, pdl_trans_date, " +
			"amount, rcd_receipt_no, pdl_trans_id, pdl_trans_status, cast(quotation_status as VARCHAR2(12)), CSH_A_TELEX_NO " +
			"From V_Renewal_Payment_Report_Pas Where trunc(Pol_Issue_Date)>=:Start_date "
		+" and trunc(Pol_Issue_Date)<=:end_date";
	
	/*
	 * 81120 New Quote Report
	 * as part of 3.2 release
	 */
	private final static String GET_QUOTE_REPORT="select BrokerName,INSUREDNAME,QuoteType,QuoteMonth,QuoteYear,quoteno,to_char(TRUNC(QuoteCreationDate),'dd/MM/yyyy') QuoteCreationDate,status,"+
	 "PolicyNO,UserName,sum(Premium) Premium,sum(Commission) Commission,to_char(TRUNC(effetcivedate),'dd/MM/yyyy') PolicyEffectiveDate,to_char(TRUNC(expirydate),'dd/MM/yyyy') PolicyExpiryDate,linkingid "+
			" from ( SELECT   DECODE(b.br_e_name,null,'DIRECT',b.br_e_name) As BrokerName,i.ins_e_first_name AS INSUREDNAME,doc.doc_e_desc as QuoteType,"+
	 "to_char(q.pol_prepared_dt,'MON') as QuoteMonth,to_char(q.pol_prepared_dt,'yyyy') as QuoteYear,q.pol_quotation_no quoteno,"+
			"q.pol_prepared_dt as QuoteCreationDate,st.sta_e_desc status,q.pol_policy_no AS PolicyNO,u.user_e_name UserName,"+
	 "q.pol_premium Premium,round(((q.pol_premium*q.pol_commision_id)/100),2) as Commission,p.pol_effective_date effetcivedate,p.pol_expiry_date expirydate,"+
			"p.pol_linking_id linkingid FROM t_trn_policy_quo q, t_mas_broker b ,t_mas_insured i,t_mas_document doc, t_mas_status st,t_mas_user u,t_trn_policy p "+
	 "where  to_date(TO_CHAR(q.pol_prepared_dt, 'dd-mm-yyyy'), 'dd-mm-yyyy') BETWEEN :startDate AND :endDate and q.pol_issue_hour=3 "+
			"and q.pol_policy_type=50 and q.pol_insured_code=i.ins_insured_code and doc.doc_code=q.pol_document_code and q.pol_br_code=b.br_code(+) and st.sta_code=q.pol_status "+
	 "and q.pol_prepared_by = u.user_id and q.pol_validity_expiry_date='31-DEC-49' and p.pol_policy_no(+)=q.pol_policy_no and p.pol_class_code(+) =q.pol_class_code "+
			"and (q.pol_policy_no is null or  p.pol_validity_expiry_date='31-DEC-49') and doc.doc_code=:quotationType ";
	private final static String GET_QUOTE_REPORT_AND="and ";
			private final static String GET_QUOTE_REPORT_NO_DIRECT_BROKER="q.pol_br_code in (:br_code)";
			private final static String GET_QUOTE_REPORT_CLOSE=")";
			private final static String GET_QUOTE_REPORT_DIRECT="(q.pol_br_code is null or ";
			private final static String GET_QUOTE_REPORT_GROUPBY=") GROUP BY BrokerName, INSUREDNAME, QuoteType, QuoteMonth, QuoteYear, quoteno, trunc(QuoteCreationDate), status, PolicyNO, UserName, trunc(effetcivedate), trunc(expirydate), linkingid";
	
	
	
	
	
	private final static String GET_TRANSACTION_RECORD= "select Pol_type, "+
			"'Premium~'||sum(TOTAL_QUOTATIONS_PRM)||'~Count~'||sum(TOTAL_QUOTATIONS_CNT) TOTAL_QUOTATIONS,"+
			"'Premium~'||sum(ABONDONED_QUOTATIONS_PRM)||'~Count~'||sum(ABONDONED_QUOTATIONS_CNT) ABONDONED_QUOTATIONS,"+
			"'Premium~'||sum(SAVED_QUOTATIONS_PRM)||'~Count~'||sum(SAVED_QUOTATIONS_CNT) SAVED_QUOTATIONS,"+
			"'Premium~'||sum(PAYMENT_RECEIVED_PRM)||'~Count~'||sum(PAYMENT_RECEIVED_CNT) PAYMENT_RECEIVED,"+
			"'Premium~'||sum(REFERAL_PENDING_PRM)||'~Count~'||sum(REFERAL_PENDING_CNT) REFERAL_PENDING,"+
			"'Premium~'||sum(Accepted_Referrals_PRM)||'~Count~'||sum(Accepted_Referrals_CNT) Accepted_Referrals,"+
			"'Premium~'||sum(CONVERTED_REFERRALS_PRM)||'~Count~'||sum(CONVERTED_REFERRALS_CNT) CONVERTED_REFERRALS,"+
			"'Premium~'||sum(REJECTED_REFERRALS_PRM)||'~Count~'||sum(REJECTED_REFERRALS_CNT) REJECTED_REFERRALS,"+
			"'Premium~'||sum(Converted_To_Policy_PRM)||'~Count~'||sum(Converted_To_Policy_CNT) Converted_To_Policy,"+
			"'Premium~'||sum(Assisted_Quote_PRM)||'~Count~'||sum(Assisted_Quote_Count) ASSISTED_QUOTATIONS"+
			" from ( "+
			      "select  DECODE(TPQ.POL_POLICY_TYPE,7,'HOME','TRAVEL') Pol_type, "+
			                  " NVL(SUM(PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID)),0), "+
			                  " NVL(SUM(PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID)),0) TOTAL_QUOTATIONS_PRM, COUNT(TPQ.POL_STATUS) TOTAL_QUOTATIONS_CNT, "+
			                  " NVL(SUM(DECODE(TPQ.POL_STATUS,6,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID))),0) ABONDONED_QUOTATIONS_PRM, COUNT(DECODE(TPQ.POL_STATUS,6,TPQ.POL_STATUS)) ABONDONED_QUOTATIONS_CNT, "+
			                  " NVL(SUM(DECODE(TPQ.POL_STATUS,1,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID))),0) SAVED_QUOTATIONS_PRM ,COUNT(DECODE(TPQ.POL_STATUS,1,TPQ.POL_STATUS)) SAVED_QUOTATIONS_CNT, "+
			                  " 0 PAYMENT_RECEIVED_PRM,0 PAYMENT_RECEIVED_CNT,"+
			                  " NVL(SUM(DECODE(TPQ.POL_STATUS,20,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID))),0) REFERAL_PENDING_PRM, COUNT(DECODE(TPQ.POL_STATUS,20,TPQ.POL_STATUS)) REFERAL_PENDING_CNT, "+
			                  " Nvl(Sum(Decode(Tpq.Pol_Status,23,PKG_PAS_QUO_POL_HOME.get_prm_for_quo(Tpq.Pol_Policy_Id, tpq.pol_endt_id))),0) Accepted_Referrals_Prm, Count(Decode(Tpq.Pol_Status,23,Tpq.Pol_Status)) Accepted_Referrals_CNT, "+
			                  " sum((Select Nvl(Sum(PKG_PAS_QUO_POL_HOME.get_prm_for_quo(Tpq.Pol_Policy_Id, tpq.pol_endt_id)),0) From  T_Trn_Task Tt "+
			                   " Where Tpq.Pol_Quotation_No = Decode(Instr(Tt.Tsk_Document_Id,'-',1,2)+1,1,0,To_Number(Trim(Substr(Tt.Tsk_Document_Id,Instr(Tt.Tsk_Document_Id,'-',1,2)+1)))) "+
			                   " And Tpq.Pol_Status = 7 and tt.tsk_document_id is not null and tt.Tsk_Document_Id <> '0')) CONVERTED_REFERRALS_PRM, count((Select Count(*) From  T_Trn_Task Tt "+
			                   " where TPQ.POL_QUOTATION_NO = DECODE(INSTR(TT.TSK_DOCUMENT_ID,'-',1,2)+1,1,0,TO_NUMBER(TRIM(SUBSTR(TT.TSK_DOCUMENT_ID,INSTR(TT.TSK_DOCUMENT_ID,'-',1,2)+1)))) "+
			                   " and TPQ.POL_STATUS = 7 and tt.tsk_document_id is not null"+
			                   " and tt.Tsk_Document_Id <> '0' "+
			                   " group by TPQ.POL_POLICY_TYPE)) CONVERTED_REFERRALS_CNT, "+
			                   " NVL(SUM(DECODE(TPQ.POL_STATUS,22,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID))),0) REJECTED_REFERRALS_PRM, COUNT(DECODE(TPQ.POL_STATUS,22,TPQ.POL_STATUS)) REJECTED_REFERRALS_CNT,"+
			                   " 0 Converted_To_Policy_PRM, 0 Converted_To_Policy_CNT, "+	
			                   " 0 Assisted_Quote_PRM, 0 Assisted_Quote_Count"+
			                  /* " Nvl(Sum(Decode(Tpq.POL_PREPARED_BY,991,Decode(Tpq.POL_ACCEXEC_CODE,991,0,Decode(Tpq.Pol_Status,7,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(Tpq.Pol_Policy_Id, tpq.pol_endt_id))))),0) Assisted_Quote_PRM,"+
			                   " Count(Decode(Tpq.POL_PREPARED_BY,991,Decode(Tpq.POL_ACCEXEC_CODE,991,null,Decode(Tpq.Pol_Status,7,Tpq.Pol_Status)))) Assisted_Quote_Count "+*/		                 		                  
			 	    " from (select distinct TPQ.POL_POLICY_ID,TPQ.POL_POLICY_TYPE,TPQ.POL_STATUS,"+
									 "TPQ.POL_PREMIUM,tpq.pol_quotation_no,tpq.pol_endt_id,tpq.POL_DISTRIBUTION_CHNL,tpq.POL_ACCEXEC_CODE,tpq.POL_PREPARED_BY"+
									  " from t_trn_policy_quo tpq,t_mas_cash_customer_quo ,t_mas_user tu"+
									  " WHERE Tpq.Pol_Policy_Id = Csh_Policy_Id "+
									   " and CSH_ENDT_ID      = TPQ.POL_ENDT_ID"+
									   " and tpq.pol_policy_type in (7,6,31) "+
									   " and TPQ.POL_ISSUE_HOUR = 3  "+
									   " and TRUNC(TPQ.POL_PREPARED_DT) >=:startDate "+
									   " And Trunc(Tpq.Pol_prepared_Dt) <=:endDate "+
									   " AND tpq.pol_endt_id = "+
													   "(SELECT MAX (q2.pol_endt_id) "+
													   " FROM t_trn_policy_quo q2 "+
													   " Where Tpq.Pol_Quotation_No = Q2.Pol_Quotation_No "+
													   ")"+
									    " AND tpq.POL_CLASS_CODE        ="+
													"(SELECT POL_CLASS_CODE"+
													" From T_Trn_Policy_Quo"+
													" WHERE pol_quotation_no = tpq.pol_quotation_no"+
													" AND pol_issue_hour     =3"+
													" AND POL_ENDT_ID        ="+
																  "(SELECT MAX(POL_ENDT_ID)"+
																  " From T_Trn_Policy_Quo"+
																  " WHERE pol_quotation_no = tpq.pol_quotation_no"+
																  " AND pol_issue_hour     =3"+
																  ")"+
													" And Rownum = 1"+
													")"+
									  " AND tpq.POL_POLICY_TYPE IN"+
												"(SELECT cdm_code"+
												com.Constant.CONST_FROM_T_MAS_CODE_MASTER+
												com.Constant.CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOBMAP+
												com.Constant.CONST_AND_CDM_CODE_IN+
															  "(SELECT Cdm_Code1"+
															  com.Constant.CONST_FROM_T_MAS_CODE_MASTER+
															  com.Constant.CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOB+
															  com.Constant.CONST_AND_CDM_ENTITY_DESC_EQLS_TOCHAR+
															  ")"+
												")"+
			          	"and 'true' = "+
			               "case   "+
			                 "when :P_DISTRIBUTION_CHNL is null then  "+
			                 com.Constant.CONST_TRUE+
			                 "when :P_DISTRIBUTION_CHNL is not null and tpq.POL_DISTRIBUTION_CHNL IN (SELECT CDM_CODE1 FROM T_MAS_CODE_MASTER   "+
			                 " WHERE CDM_ENTITY_TYPE='PAS_APPMAP'   "+
			                 " AND CDM_CODE2=:P_DISTRIBUTION_CHNL) then  "+
			                 com.Constant.CONST_TRUE+
			                 com.Constant.CONST_ELSE+
			                 com.Constant.CONST_FALSE_QUERY+
			               "end "+
			            ") TPQ"+
			" group by DECODE(TPQ.POL_POLICY_TYPE,7,'HOME','TRAVEL')"+
			" union "+
			" select  DECODE(TPQ.POL_POLICY_TYPE,7,'HOME','TRAVEL'), "+
						" NVL(SUM(PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_ENDT(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID)),0), "+
							"0,0,"+
						"0,0,"+
						"0,0,"+
						"sum(nvl((select nvl(pdl_trans_amount,0) from  t_trn_payment_dtl  where  pdl_quote_no = tpq.pol_quotation_no"+
			      " and PDL_TRANS_STATUS not in ('ERROR','DECLINE')),0)) PAYMENT_RECEIVED_PRM, COUNT((select distinct PDL_POLICY_ID from  t_trn_payment_dtl  where  pdl_quote_no = tpq.pol_quotation_no and PDL_TRANS_STATUS not in ('ERROR','DECLINE'))) PAYMENT_RECEIVED_CNT,"+
						"0,0,"+
						"0,0,"+
						"0 ,0,"+
						"0,0,"+
						"Nvl(Sum(Decode(Tpq.Pol_Status,1,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_ENDT(Tpq.Pol_Policy_Id, tpq.pol_endt_id))),0) Converted_To_Policy_PRM, Count(Decode(Tpq.Pol_Status,1,Tpq.Pol_Status)) Converted_To_Policy_CNT,"+
						" 0 Assisted_Quote_PRM, 0 Assisted_Quote_Count"+
						 /*" Nvl(Sum(Decode(Tpq.POL_PREPARED_BY,991,Decode(Tpq.POL_ACCEXEC_CODE,991,0,Decode(Tpq.Pol_Status,7,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_ENDT(Tpq.Pol_Policy_Id, Tpq.pol_endt_id))))),0) Assisted_Quote_PRM,"+
		                   " Count(Decode(Tpq.POL_PREPARED_BY,991,Decode(Tpq.POL_ACCEXEC_CODE,991,null,Decode(Tpq.Pol_Status,7,Tpq.Pol_Status)))) Assisted_Quote_Count "+*/
				" from (select distinct policy.pol_policy_id,policy.pol_policy_type,policy.pol_status,"+
								 "POLICY.Pol_Premium,POLICY.pol_quotation_no,POLICY.pol_endt_id,q.POL_POLICY_ID pol_id"+
							 " FROM t_trn_policy POLICY,"+
									"t_trn_policy_quo q,"+
									"t_mas_cash_customer tmc,"+
									"T_MAS_USER TU	"+
								  " WHERE POLICY.pol_endt_id ="+
									"(SELECT MAX (p.pol_endt_id)"+
									" FROM t_trn_policy p"+
									" WHERE p.pol_policy_id    = policy.pol_policy_id"+
									  " and P.POL_POLICY_YEAR         ="+
										"(SELECT MAX (tp.pol_policy_year)"+
										" FROM t_trn_policy tp"+
										" WHERE tp.pol_policy_no    = p.pol_policy_no"+
										" AND tp.pol_effective_date = p.pol_effective_date"+
										")"+
									  ")"+
								 " and tmc.csh_validity_start_date <= POLICY.pol_validity_start_date"+
								" AND tmc.csh_validity_expiry_date   > POLICY.pol_validity_start_date"+
								" AND POLICY.pol_policy_id       = tmc.csh_policy_id"+
								" and policy.POL_ENDT_ID      = TMC.CSH_ENDT_ID"+
								  " and Q.POL_QUOTATION_NO           =policy.POL_QUOTATION_NO"+
								  " AND POLICY.POL_POLICY_TYPE IN"+
									" (SELECT cdm_code"+
									com.Constant.CONST_FROM_T_MAS_CODE_MASTER+
									com.Constant.CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOBMAP+
									com.Constant.CONST_AND_CDM_CODE_IN+
									  " (SELECT Cdm_Code1"+
									  " from t_mas_code_master"+
									  com.Constant.CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOB+
									  com.Constant.CONST_AND_CDM_ENTITY_DESC_EQLS_TOCHAR+
									  ")"+
									")"+
							" and 'true' = "+
			               " case   "+
			               " when :P_DISTRIBUTION_CHNL is null then  "+
			               com.Constant.CONST_TRUE+
			               "when :P_DISTRIBUTION_CHNL is not null and policy.POL_DISTRIBUTION_CHNL IN (SELECT CDM_CODE1 FROM T_MAS_CODE_MASTER   "+
			               " WHERE CDM_ENTITY_TYPE='PAS_APPMAP'   "+
			               " AND CDM_CODE2=:P_DISTRIBUTION_CHNL) then  "+
			               com.Constant.CONST_TRUE+
			               com.Constant.CONST_ELSE+
			               com.Constant.CONST_FALSE_QUERY+
			               "end"+
			         " and TRUNC(policy.POL_PREPARED_DT) >=:startDate  "+
									   " And Trunc(policy.Pol_prepared_Dt) <=:endDate"+
			        " and policy.pol_policy_type in (7,6,31) "+
							" and policy.pol_issue_hour = 3  ) tpq"+
			   " GROUP BY DECODE(TPQ.POL_POLICY_TYPE,7,'HOME','TRAVEL')"+
				
				" union "+
				" select  DECODE(TPQ.POL_POLICY_TYPE,7,'HOME','TRAVEL'), "+
				" NVL(SUM(PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_ENDT(TPQ.POL_POLICY_ID, TPQ.POL_ENDT_ID)),0), "+
				"0,0,"+
				"0,0,"+
				"0,0,"+
				"0,0,"+
				"0,0,"+
				"0,0,"+
				"0 ,0,"+
				"0,0,"+
				"0,0,"+
				//" Nvl(Sum(Decode(Tpq.QUOTE_PREPARED_BY,991,Decode(Tpq.POLICY_PREPARED_BY,991,0,Decode(Tpq.Pol_Status,7,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_QUO(Tpq.Pol_Policy_Id, tpq.pol_endt_id))))),0) Assisted_Quote_PRM,"+
				//" Count(Decode(Tpq.QUOTE_PREPARED_BY,991,Decode(Tpq.POLICY_PREPARED_BY,991,0,Decode(Tpq.Pol_Status,7,Tpq.Pol_Status)))) Assisted_Quote_Count "+
				" Nvl(Sum(Decode(Tpq.QUOTE_PREPARED_BY,991,Decode(Tpq.POLICY_PREPARED_BY,991,0,Decode(Tpq.Pol_Status,1,PKG_PAS_QUO_POL_HOME.GET_PRM_FOR_ENDT(Tpq.Pol_Policy_Id, tpq.pol_endt_id))))),0) Assisted_Quote_PRM,"+
				" Count(Decode(Tpq.QUOTE_PREPARED_BY,991,Decode(Tpq.POLICY_PREPARED_BY,991,null,Decode(Tpq.Pol_Status,1,Tpq.Pol_Status)))) Assisted_Quote_Count "+
				" from (select distinct policy.POL_POLICY_ID,policy.POL_POLICY_TYPE,policy.POL_STATUS,"+
						 "policy.POL_PREMIUM,policy.pol_quotation_no,policy.pol_endt_id,tpq.POL_PREPARED_BY QUOTE_PREPARED_BY,policy.POL_PREPARED_BY POLICY_PREPARED_BY "+
						  " from t_trn_policy_quo tpq,t_trn_policy policy,t_mas_cash_customer tmc ,t_mas_user tu"+						  
						  " WHERE policy.pol_endt_id ="+
						  "(SELECT MAX (p.pol_endt_id) FROM t_trn_policy p"+
						  " WHERE p.pol_policy_id    = policy.pol_policy_id"+
						  " and P.POL_POLICY_YEAR         ="+
								"(SELECT MAX (tp.pol_policy_year)"+
								" FROM t_trn_policy tp"+
								" WHERE tp.pol_policy_no    = p.pol_policy_no"+
								" AND tp.pol_effective_date = p.pol_effective_date"+
								")"+
							")"+
							" and tmc.csh_validity_start_date <= policy.pol_validity_start_date"+
							" AND tmc.csh_validity_expiry_date   > policy.pol_validity_start_date"+
							" AND policy.pol_policy_id       = tmc.csh_policy_id"+
							" and policy.POL_ENDT_ID      = tmc.CSH_ENDT_ID"+
							" and tpq.POL_QUOTATION_NO           =policy.POL_QUOTATION_NO"+
							" AND policy.POL_POLICY_TYPE IN"+					
							" (SELECT cdm_code"+
							com.Constant.CONST_FROM_T_MAS_CODE_MASTER+
							com.Constant.CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOBMAP+
							com.Constant.CONST_AND_CDM_CODE_IN+
								" (SELECT Cdm_Code1 from t_mas_code_master"+
								com.Constant.CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOB+
								com.Constant.CONST_AND_CDM_ENTITY_DESC_EQLS_TOCHAR+
								")"+
							")"+
							"and 'true' = "+
						        "case   "+
						        "when :P_DISTRIBUTION_CHNL is null then  "+
						        com.Constant.CONST_TRUE+
						        "when :P_DISTRIBUTION_CHNL is not null and :P_DISTRIBUTION_CHNL=9  then "+						              
						        	com.Constant.CONST_TRUE+
						        com.Constant.CONST_ELSE+
						            com.Constant.CONST_FALSE_QUERY+
						         "end "+
						    " and TRUNC(policy.POL_PREPARED_DT) >=:startDate  "+
						    " And Trunc(policy.Pol_prepared_Dt) <=:endDate"+
						    " and policy.pol_policy_type in (7,6,31) "+
							" and policy.pol_issue_hour = 3 ) TPQ"+
				 " group by DECODE(TPQ.POL_POLICY_TYPE,7,'HOME','TRAVEL'))"+				
			   " group by pol_type";
	
/*	private final static String GET_TRANSACTION_RECORD = "select decode(tpq.pol_policy_type,7,'HOME','TRAVEL'), " +
			"Sum(Tpq.Pol_Premium), " +
			"'Premium~'||Nvl(Sum(Tpq.Pol_Premium),0)||'~Count~'||Count(Tpq.Pol_Status), " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,6,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,6,Tpq.Pol_Status)), " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,1,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,1,Tpq.Pol_Status)), " +
			"'Premium~'||Nvl(Sum(Decode(Pdl_policy_Id,Null,0,Tpq.Pol_Premium)),0)||'~Count~'||Count(Pdl_policy_Id), " +
		//	"--,Count(Decode(Tpq.Pol_Status,20,Tpq.Pol_Status)) As Referral_Pending, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,20,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,20,Tpq.Pol_Status)), " +
		//	"--,Count(Decode(Tpq.Pol_Status,23,Tpq.Pol_Status)) As Accepted_Referral, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,23,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,23,Tpq.Pol_Status)), " +
			"'Premium~'||sum((Select Nvl(Sum(Tpq.Pol_Premium),0) From  T_Trn_Task Tt " +
			" Where Tpq.Pol_Quotation_No = Decode(Instr(Tt.Tsk_Document_Id,'-',1,2)+1,1,0,To_Number(Trim(Substr(Tt.Tsk_Document_Id,Instr(Tt.Tsk_Document_Id,'-',1,2)+1)))) " +
			" And Tpq.Pol_Status = 7))||'~Count~'||count((Select Count(*) From  T_Trn_Task Tt " +
			" Where Tpq.Pol_Quotation_No = Decode(Instr(Tt.Tsk_Document_Id,'-',1,2)+1,1,0,To_Number(Trim(Substr(Tt.Tsk_Document_Id,Instr(Tt.Tsk_Document_Id,'-',1,2)+1)))) " +
			" And Tpq.Pol_Status = 7 Group By Tpq.Pol_Policy_Type)), " +
	//		"--,Count(Decode(Pol_Status,22,Pol_Status)) As Rejected_Referral, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,22,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,22,Tpq.Pol_Status)), " +
	//		"--,Count(Decode(Pol_Status,7,Pol_Status)) As Converted_To_Policy, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,7,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,7,Tpq.Pol_Status)) " +
			" FROM t_trn_policy_quo tpq,t_trn_payment_dtl tpd, t_mas_cash_customer_quo WHERE " +
			//" trunc(tpq.pol_validity_expiry_date) = '31-DEC-2049' " +
			" tpq.pol_policy_id = csh_policy_id " +
			" and  tpq.pol_policy_id = tpd.pdl_policy_id(+) " +
			" and tpq.pol_policy_type in (7,6,31) " +
			" AND tpq.pol_issue_hour = 3  " +
			//" and trunc(Csh_Validity_Expiry_Date) = '31-DEC-2049' " +
			" AND TRUNC (csh_validity_start_date) <= TRUNC (pol_validity_start_date) " +
		    " AND TRUNC (csh_validity_expiry_date) > TRUNC (pol_validity_start_date) " +
			" and trunc(tpq.Pol_prepared_Dt) >=:Start_date " +
			" And Trunc(Tpq.Pol_prepared_Dt) <=:end_date " + 
			"  AND tpq.pol_endt_id = " +
			" (SELECT MAX (q2.pol_endt_id) " +
			" FROM t_trn_policy_quo q2 " +
			" WHERE tpq.pol_quotation_no = q2.pol_quotation_no " +
			" ) " +	
			//" AND POL_DISTRIBUTION_CHNL IN (SELECT CDM_CODE1 FROM T_MAS_CODE_MASTER WHERE CDM_ENTITY_TYPE='PAS_APPMAP' AND Cdm_Code2 in ( select Decode(:P_DISTRIBUTION_CHNL,Null,Pol_Distribution_Chnl,:P_DISTRIBUTION_CHNL) from dual))";
			"and 'true' = "+
			" case " + 
			" when :P_DISTRIBUTION_CHNL is null then " +
			" 'true' " + 
			" when :P_DISTRIBUTION_CHNL is not null and tpq.POL_DISTRIBUTION_CHNL IN (SELECT CDM_CODE1 FROM T_MAS_CODE_MASTER " + 
			" WHERE CDM_ENTITY_TYPE='PAS_APPMAP' " + 
			" AND CDM_CODE2=:P_DISTRIBUTION_CHNL) then " +
			" 'true' " +
			" else " +
			" 'false' " +
			" end "; */
	//private final static String GET_TRANSACTION_RECORD_GROUP_BY = " group by decode(tpq.pol_policy_type,7,'HOME','TRAVEL')";
	
	private final static String GET_DETAILED_TRANSACTION_RECORD = "SELECT * FROM " +
			"table(PKG_PAS_QUO_POL_HOME.FN_GET_TRANSACTION_RPT" +
			"(:date1, :date2, :poltype, :polstatus, :statusdescription))";
	
	
	/*
	 * 81120 New report required for quote changes
	 */
	
	private final static String GET_QUOTE_REPORT_RPT = "SELECT * from table(PKG_PAS_QUO_POL_HOME.FN_GET_QUOTE_RPT (:BrokerCodearray,:Start_date,:End_date,:pol_type,:isAllSelected,:isBrkSelected,:isDrtSelected))";
	
	private final static String GET_PROMOCODE_RECORD = "select POL_PROMO_CODE as Promotional_code, " +
			"Sum(Tpq.Pol_Premium) Premium, " +
			"'Premium~'||Nvl(Sum(Tpq.Pol_Premium),0)||'~Count~'||Count(Tpq.Pol_Status) As Total_Quotations, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,6,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,6,Tpq.Pol_Status)) As Abondoned_Quotations, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,1,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,1,Tpq.Pol_Status)) As Saved_Quotations, " +
			"'Premium~'||Nvl(Sum(Decode(Pdl_policy_Id,Null,0,Tpq.Pol_Premium)),0)||'~Count~'||Count(Pdl_policy_Id) As Payment_Received, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,20,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,20,Tpq.Pol_Status)) As Referral_Pending, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,23,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,23,Tpq.Pol_Status)) As Accepted_Referral, " +
			"'Premium~'||sum((Select Nvl(Sum(Tpq.Pol_Premium),0) From  T_Trn_Task Tt " +
			"Where Tpq.Pol_Quotation_No = Decode(Instr(Tt.Tsk_Document_Id,'-',1,2)+1,1,0,To_Number(Trim(Substr(Tt.Tsk_Document_Id,Instr(Tt.Tsk_Document_Id,'-',1,2)+1)))) " +
			"And Tpq.Pol_Status = 7))||'~Count~'||count((Select Count(*) From  T_Trn_Task Tt " +
			"Where Tpq.Pol_Quotation_No = Decode(Instr(Tt.Tsk_Document_Id,'-',1,2)+1,1,0,To_Number(Trim(Substr(Tt.Tsk_Document_Id,Instr(Tt.Tsk_Document_Id,'-',1,2)+1)))) " +
			"And Tpq.Pol_Status = 7 Group By Tpq.Pol_Policy_Type)) As Converted_Referral, " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,22,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,22,Tpq.Pol_Status)) As Rejected_Referral , " +
			"'Premium~'||Nvl(Sum(Decode(Tpq.Pol_Status,7,Tpq.Pol_Premium)),0)||'~Count~'||Count(Decode(Tpq.Pol_Status,7,Tpq.Pol_Status)) As Converted_To_Policy " +
			"FROM t_trn_policy_quo tpq,t_trn_payment_dtl tpd,t_mas_cash_customer_quo where " +
			//" WHERE trunc(tpq.pol_validity_expiry_date) = '31-DEC-2049' " +
			" tpq.pol_policy_id = csh_policy_id " +
			" and  tpq.pol_policy_id = tpd.Pdl_policy_Id(+) " +
			" and tpq.pol_policy_type in (7,6,31) " +
			" AND tpq.pol_issue_hour = 3 " +
			//" and trunc(Csh_Validity_Expiry_Date) = '31-DEC-2049' " +
			" AND TRUNC (csh_validity_start_date) <= TRUNC (pol_validity_start_date) " +
		    " AND TRUNC (csh_validity_expiry_date) > TRUNC (pol_validity_start_date) " +
			" and tpq.pol_promo_code = DECODE(:p_promo_code,null,tpq.pol_promo_code,:p_promo_code) " +
			" and trunc(tpq.Pol_prepared_Dt) >= trunc(DECODE(:P_START_DATE,null,tpq.Pol_prepared_Dt,:P_START_DATE)) " +
			" AND trunc(tpq.Pol_prepared_Dt) <= trunc(DECODE(:P_END_DATE,null,tpq.Pol_prepared_Dt,:P_END_DATE)) " +
			" AND tpq.pol_endt_id = " +
			" (SELECT MAX (q2.pol_endt_id) " +
			" FROM t_trn_policy_quo q2 " +
			" WHERE tpq.pol_quotation_no = q2.pol_quotation_no " +
			" ) " +	
			//" AND POL_DISTRIBUTION_CHNL IN (SELECT CDM_CODE1 FROM T_MAS_CODE_MASTER WHERE CDM_ENTITY_TYPE='PAS_APPMAP' AND Cdm_Code2 in ( select Decode(:P_DISTRIBUTION_CHNL,Null,Pol_Distribution_Chnl,:P_DISTRIBUTION_CHNL) from dual))";
			" and 'true' = "+
			" case " + 
			" when :P_DISTRIBUTION_CHNL is null then " +
			" 'true' " + 
			" when :P_DISTRIBUTION_CHNL is not null and tpq.POL_DISTRIBUTION_CHNL IN (SELECT CDM_CODE1 FROM T_MAS_CODE_MASTER " + 
			" WHERE CDM_ENTITY_TYPE='PAS_APPMAP' " + 
			" AND CDM_CODE2=:P_DISTRIBUTION_CHNL) then " +
			" 'true' " +
			" else " +
			" 'false' " +
			" end "; 
	private final static String GET_PROMOCODE_RECORD_GROUP_BY = " group by POL_PROMO_CODE";
	
	private final static String POL_DISTRIBUTION_CHANNEL_B2C = " and POL_DISTRIBUTION_CHNL = "+ Utils.getSingleValueAppConfig( com.Constant.CONST_B2C_DEFAULT_DIST_CHANNEL );
	
	private final static String POL_DISTRIBUTION_CHANNEL_B2B = " and POL_DISTRIBUTION_CHNL != "+ Utils.getSingleValueAppConfig( com.Constant.CONST_B2C_DEFAULT_DIST_CHANNEL );

	@Override
	public BaseVO acctSearch(BaseVO baseVO) {
		if( LOGGER.isInfo() ) LOGGER.info( "Entering acctSearch menthod" );

		if( LOGGER.isInfo() ) LOGGER.info( " Fetch data for broker account search" );
		BrReportSearchVO searchBrVO = (BrReportSearchVO) baseVO;
		Integer brokerCode = null;
		if("code".equals(searchBrVO.getByNameOrCode())){
			brokerCode = searchBrVO.getBrokerCode();
		}else if("name".equals(searchBrVO.getByNameOrCode())){
			brokerCode = searchBrVO.getBrokerName_Code();
		}
		
		Integer branchCode = searchBrVO.getBranchCode();
		boolean all = "all".equals(searchBrVO.getAllOrUnmatched());
		boolean unmatched = "unmatched".equals(searchBrVO.getAllOrUnmatched());
		
		String policyNo = searchBrVO.getPolicyNo();
		
		Date sdate = null;
		Date edate = null;
		int toYear = 0;
		int toMonth = 0 ;
		int fromYear = 0;
		int fromMonth = 0;
		
		/*Changes made for CR 92593 by Tuhir
		CR 92593: Account Statement Report in ePlatform */
		if(searchBrVO.getStartDate() != null)
			sdate = searchBrVO.getStartDate();
		
		if(searchBrVO.getEndDate() != null)
			edate = searchBrVO.getEndDate();
	
		/*java.sql.Date sqlDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);*/
		
		LOGGER.debug( "Policy No-->" + policyNo);
		String lob = searchBrVO.getLob();
		
		//int lobs = Integer.parseInt( searchBrVO.getLob() );
		
		if(lob.equals(Utils.getSingleValueAppConfig( "TRAVEL_POLICY_TYPE" )) ){
			lob = Utils.getSingleValueAppConfig( "SHORT_TRAVEL_POL_TYPE" ) + "," + Utils.getSingleValueAppConfig( "LONG_TRAVEL_POL_TYPE" );
			//lobs = 888;
		}
		
		List<String> lobList = new ArrayList<String>(Arrays.asList(lob.split(",")));
		LOGGER.debug( "ALL-->" + all );
		LOGGER.debug( "UNMATCHED-->" + unmatched );
		LOGGER.debug( "sdate-->" + sdate );
		LOGGER.debug( "edate-->" + edate );
		List <Object> result = null;
		Iterator <Object>itr =null;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		try{
		if(all){
			if(sdate != null && edate != null)
			{
				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				cal1.setTime( sdate );
				cal2.setTime( edate );
				toYear = cal2.get(Calendar.YEAR);
				toMonth = cal2.get(Calendar.MONTH) + 1 ;
				fromYear = cal1.get(Calendar.YEAR);
				fromMonth = cal1.get(Calendar.MONTH) + 1;
			}
			Query query = null ;
			String accQuery = "";
			/*Changes made for CR 92593 by Tuhir
			CR 92593: Account Statement Report in ePlatform */
			if( brokerCode == 999999 ){
				if(sdate != null && edate != null){
					accQuery = ALL_ACCOUNT_SEARCH_FOR_ALL_POLICY_QRY ;
					
				}
				else{
					accQuery = ALL_ACCOUNT_SEARCH_FOR_ALL_POLICY_QRY_WITHOUT_DATE;
					
				}
				if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
					accQuery =com.Constant.CONST_SELECT_FROM_END+ accQuery + ")"+BRK_ACCOUNT_SEARCH_POL_NO;
				}
				query = session.createSQLQuery( accQuery );				
				//String sqlQuery = "SELECT PKG_PAS_UTILS.FN_GET_ACCT_STMT_RPT("+ sqlDate + "," + sqlEDate + ","+ lobs + ","+ branchCode + " ) FROM DUAL ";
				//query = session.createSQLQuery(sqlQuery);
			} 
			else if (brokerCode == 9999999  ) {
				if(sdate != null && edate != null)
					{
						accQuery = ALL_ACCOUNT_SEARCH_FOR_DIRECT_POLICY_QRY;
						
					}
				else
					{
						accQuery = ALL_ACCOUNT_SEARCH_FOR_DIRECT_POLICY_QRY_WITHOUT_DATE;
					}
				if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
					accQuery = com.Constant.CONST_SELECT_FROM_END+ accQuery +" ) " +BRK_ACCOUNT_SEARCH_POL_NO;
				}
				query = session.createSQLQuery( accQuery );
				
			} 
			else{
				if(sdate != null && edate != null)
					{
						accQuery = BRK_ACCOUNT_SEARCH_SELECT_ALL+BRK_ACCOUNT_SEARCH_FROM_ALL+BRK_ACCOUNT_SEARCH_WHERE_ALL;
						
					}
				else
					{
						accQuery = BRK_ACCOUNT_SEARCH_SELECT_ALL+BRK_ACCOUNT_SEARCH_FROM_ALL+BRK_ACCOUNT_SEARCH_WHERE_ALL_WITHOUT_DATE;
					}
				
				if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
					accQuery = com.Constant.CONST_SELECT_FROM_END+accQuery +" )"+BRK_ACCOUNT_SEARCH_POL_NO;
					}
				
				query = session.createSQLQuery(accQuery);
				query.setInteger(com.Constant.CONST_BR_CODE, brokerCode);
			}
			/*
			 * Added as part of 3.3 Release
			 */
			if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
				query.setString("polNo", searchBrVO.getPolicyNo());
			}
			query.setParameterList(com.Constant.CONST_PTCODE, lobList );
			/*query.setDate("to_date", sqlEDate);
			query.setDate("from_date", sqlDate );*/
			if(sdate != null && edate != null)
			{
				/*Changes made for CR 92593 by Tuhir
				CR 92593: Account Statement Report in ePlatform */
				query.setInteger( "toYear", toYear );
				query.setInteger( "toMonth", toMonth);
				query.setInteger( "fromYear", fromYear );
				query.setInteger( "fromMonth", fromMonth );
			}
			query.setInteger("branch_code", branchCode);
			
			//System.out.println("Query: " + query.getQueryString());
			//System.out.println("Params: " + query.getNamedParameters());
			
			result = query.list();
			
			
			
			LOGGER.debug( "Size of resultset is" + result.size());
		}
		else if(unmatched){
			
			Query query = null;
			String accQuery = "";
			if( brokerCode == 999999 ){

					accQuery = UNMATCHED_ACCOUNT_SEARCH_FOR_ALL_POLICY_QRY ;
				
				if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
					accQuery = com.Constant.CONST_SELECT_FROM_END+ accQuery + ")"+UNMATCHED_BRK_ACCOUNT_SEARCH_POL_NO;
				}
				query = session.createSQLQuery( accQuery );
				
			}else if (brokerCode == 9999999  ) {
					accQuery = UNMATCHED_ACCOUNT_SEARCH_FOR_DIRECT_POLICY_QRY ;

					if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
					accQuery = com.Constant.CONST_SELECT_FROM_END+accQuery +" ) "+UNMATCHED_BRK_ACCOUNT_SEARCH_POL_NO;
				}
				query = session.createSQLQuery( accQuery );
			} else{
					accQuery = ACCOUNT_SEARCH_SELECT_UNMATCHED + ACCOUNT_SEARCH_FROM_UNMATCHED + ACCOUNT_SEARCH_WHERE_UNMATCHED;

					
					if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
					accQuery = com.Constant.CONST_SELECT_FROM_END+accQuery+" ) " +UNMATCHED_BRK_ACCOUNT_SEARCH_POL_NO;
				}
				query = session.createSQLQuery(accQuery);
				query.setInteger(com.Constant.CONST_BR_CODE, brokerCode);
			}
			
			/*
			 * Added as part of 3.3 Release
			 */
			if(!Utils.isEmpty(searchBrVO.getPolicyNo())){
				query.setString("polNo", searchBrVO.getPolicyNo());
			}
			query.setInteger("branch_code", branchCode);
			query.setParameterList( com.Constant.CONST_PTCODE, lobList );
			result = query.list();
			
			LOGGER.debug( "Size of resultset is" + result.size());
			}
				
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		
		if(result != null)			/* Added if condition (result check for null) - sonar violation fix */
		itr = result.iterator();
		Object[] row =null;
		com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
		com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter2 = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
		ReportsResultsHolder acctSearchHolder =  new ReportsResultsHolder();
		List<BrokerAcctResultVO> brkAcctResultList =  new com.mindtree.ruc.cmn.utils.List<BrokerAcctResultVO>(BrokerAcctResultVO.class);
		if(itr != null){			/* Added if condition (itr check for null) - sonar violation fix */
		while(itr.hasNext()){
			row = (Object[])itr.next();
			BrokerAcctResultVO acctSearchVO = new BrokerAcctResultVO();
			acctSearchVO.setBROKERNAME(String.valueOf(row[0]));
			acctSearchVO.setADDRESS(String.valueOf(row[1]));
			acctSearchVO.setFAX(converter.getTypeOfB().cast(converter.getBFromA(row[2])));
			acctSearchVO.setPHONE(converter.getTypeOfB().cast(converter.getBFromA(row[3])));
			acctSearchVO.setLOC_DESC(String.valueOf(row[4]));
			acctSearchVO.setCUSTOMERCATEGORY(String.valueOf(row[5]));
			acctSearchVO.setINSUREDNAME(String.valueOf(row[6]));
			acctSearchVO.setTRANSACTIONTYPE(String.valueOf(row[7]));
			acctSearchVO.setVOUCHERNO(String.valueOf(row[8]));
			acctSearchVO.setVOUCHERDATE(String.valueOf(row[9]));
			//acctSearchVO.setPOLICYNO((converter.getTypeOfB().cast(converter.getBFromA(row[10]))));
			acctSearchVO.setPOLICYNO(converter2.getTypeOfB().cast(converter2.getBFromA(row[10]) ));
			acctSearchVO.setGROSS(converter.getTypeOfA().cast((row[11])));
			acctSearchVO.setNET(converter.getTypeOfA().cast((row[12])));
			acctSearchVO.setCOMAMOUNT(converter.getTypeOfA().cast(row[13]));
		
			brkAcctResultList.add(acctSearchVO);
			
		}
		}
		
		
		acctSearchHolder.setBrokerAcct(brkAcctResultList);
		return acctSearchHolder;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.reports.dao.IReportsDAO#livePolSearch(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO livePolSearch(BaseVO baseVO) {
		ReportsSearchVO searchVO = (ReportsSearchVO) baseVO;
		//Integer brokerCode = searchVO.getBrokerCode();
		Date sdate = searchVO.getStartDate();
		Date edate = searchVO.getEndDate();
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);
		String lob = searchVO.getLob();
        Integer[] brCodes = searchVO.getBrokerCodeList();
		Integer branchCode = searchVO.getBranchCode();
		//Commenting below lines as we should pass 888 for Travel as common policy type. Passing both 6 & 31 will result into wrong number of params exception from SQL.
		//From searchVO we get it as 888. Pass on the same.
		/*if(lob.equals(Utils.getSingleValueAppConfig( "TRAVEL_POLICY_TYPE" )) ){
			lob = Utils.getSingleValueAppConfig( "SHORT_TRAVEL_POL_TYPE" ) + "," + Utils.getSingleValueAppConfig( "LONG_TRAVEL_POL_TYPE" );
		}*/
		List<String> lobList = new ArrayList<String>(Arrays.asList(lob.split(",")));
		
		List <Object> result = null;
		Iterator <Object>itr =null;
		List<String> brList = new ArrayList<String>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		for(int i=0;i<brCodes.length;i++){
			brList.add( brCodes[i].toString() );
		}
		String[] str =  (String[]) brList.toArray(new String[brList.size()]);
		String brCodesList = "";
		String brCode = "1";
		
		for(int i=0;i < str.length; i++){
			if(i != 0 && !str[i].equalsIgnoreCase(com.Constant.CONST_NO_9999999) && !brCodesList.equalsIgnoreCase("")){
				brCodesList += "-";
			}
			if(str[i].equalsIgnoreCase("999999")){
				brCode = "999999";
				brCodesList = "1";
				break;
			} 
			if(str[i].equalsIgnoreCase(com.Constant.CONST_NO_9999999)){
				brCode = com.Constant.CONST_NO_9999999;
				System.out.println("length : "+str.length );
				if(i==0 && str.length < 2){
					brCodesList = "0";
					
				}
			}else {
			    brCodesList = brCodesList + str[i];
			}
		}
		System.out.println("brCodesList : "+brCodesList+"   brCode : "+brCode);
		try{
			Query query;
			query = session.createSQLQuery( LIVE_POL_FUNC );
			query.setString( "BrokerCodearray", brCodesList );
			query.setParameter( "BrokerCode", brCode  );
				
			query.setParameterList( com.Constant.CONST_PTCODE, lobList );
            query.setInteger("locCode", branchCode);
			query.setDate(com.Constant.CONST_START_DATE, sqlSDate);
			query.setDate(com.Constant.CONST_END_DATE, sqlEDate);
			
			result = query.list();
					
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		if( Utils.isEmpty( result ) ){
			throw new BusinessException( "pas.src.Empty", null, "No records foun_1" );
		}
		itr = result.iterator();
		Object[] row =null;
		com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
		//com.rsaame.pas.cmn.converter.BigDecimalStringConverter converterStr = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
		ReportsResultsHolder acctSearchHolder =  new ReportsResultsHolder();
		List<LivePoliciesRptResultVO> livePolResultList =  new com.mindtree.ruc.cmn.utils.List<LivePoliciesRptResultVO>(LivePoliciesRptResultVO.class);
		while(itr.hasNext()){
			row = (Object[])itr.next();
			LivePoliciesRptResultVO resultVO = new LivePoliciesRptResultVO();
			// Formatting effective date,expiry date,issue date to format like Dec 26,2013 12:10 pm
			resultVO.setBrCompanyName(String.valueOf(row[1]));
			resultVO.setInsuredName(String.valueOf(row[2]));
			resultVO.setPolicyNo((String.valueOf(row[3])));
			
			resultVO.setPolicyIssueDate(String.valueOf(row[4]));
			resultVO.setPolicyExpDate(String.valueOf(row[6]));
			resultVO.setPolicyEffDate(String.valueOf(row[5]));
			/*//Formatting the PolicyEffDate to display only the Month Name
			resultVO.setPolicyEffDate( new SimpleDateFormat( "MMMM" ).format( row[ 5 ] ) );*/
			resultVO.setPolicyPremium(converter.getTypeOfA().cast(row[8]));
			if( Utils.isEmpty( row[9] )){
				resultVO.setPolicyCommision(BigDecimal.ZERO);
			}
			else{
				resultVO.setPolicyCommision((converter.getTypeOfA().cast(row[9])));
			}
			resultVO.setSchemeName(String.valueOf(row[10]));
			resultVO.setCreatedBy( String.valueOf( row[11] ));
			if( !Utils.isEmpty( row[12] )){
				resultVO.setDebitNoteNo( ( converter.getTypeOfA().cast( row[ 12 ] ) ).intValue() );
			}
			resultVO.setTransactionType( String.valueOf( row[13] ) );
			/*
			 * Add new record policy type here 
			 */
			if(!Utils.isEmpty( row[14] ))
			resultVO.setTypeOfPolicy(String.valueOf( row[14] ) );
			
			if(!Utils.isEmpty( row[15] ))
				resultVO.setAccExecName(String.valueOf( row[15] ) );
			
			if(!Utils.isEmpty( row[16] ))
			resultVO.setEndtNo( ( converter.getTypeOfA().cast( row[ 16 ] ) ).intValue() );
			
			if(!Utils.isEmpty( row[17] ))
			resultVO.setPolicyStatus(String.valueOf( row[17] ) );
			
			if(!Utils.isEmpty( row[18] ))
			resultVO.setDistributionChannel(String.valueOf( row[18] ) );
			
			if(!Utils.isEmpty( row[19] ))
			resultVO.setApprovedBy(String.valueOf( row[19] ) );
			
			if(!Utils.isEmpty( row[20] ))
			resultVO.setLicensedBy(String.valueOf( row[20] ) );
			
			if(!Utils.isEmpty( row[21] ))
			resultVO.setNationality(String.valueOf( row[21] ) );
			
			if(!Utils.isEmpty( row[22] ))
				resultVO.setDiscountPremium( ( converter.getTypeOfA().cast( row[ 22 ] ) ) );
			
			if(!Utils.isEmpty( row[23] ))
				resultVO.setPrintDate(String.valueOf( row[23] ) );
			
			if(!Utils.isEmpty( row[24] ))
				resultVO.setTravelDays( ( converter.getTypeOfA().cast( row[ 24 ] ) ).intValue() );
			
			if(!Utils.isEmpty( row[25] ))
				resultVO.setTravelLocation(String.valueOf( row[25] ) );
			
			if(!Utils.isEmpty( row[26] ))
				resultVO.setPaymentMode(String.valueOf( row[26] ) );
			
			
			livePolResultList.add(resultVO);
		}
		acctSearchHolder.setLivePolicie(livePolResultList);
		return acctSearchHolder;
	}
	public BaseVO classPrmSearch(BaseVO baseVO){
		ReportsSearchVO searchVO = (ReportsSearchVO) baseVO;
		
		boolean isAllSelected = false;
		boolean isDirectSelected = false;
		
		Date sdate = searchVO.getStartDate();
		Date edate = searchVO.getEndDate();
        Integer branchCode = searchVO.getBranchCode();
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);
		Integer[] brCode = searchVO.getBrokerCodeList();
		List <Object> result = null;
		Iterator <Object>itr =null;
		String premium=null;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String lob = searchVO.getLob();
		if(lob.equals(Utils.getSingleValueAppConfig( "TRAVEL_POLICY_TYPE" )) ){
			lob = Utils.getSingleValueAppConfig( "SHORT_TRAVEL_POL_TYPE" ) + "," + Utils.getSingleValueAppConfig( "LONG_TRAVEL_POL_TYPE" );
		}
		List<String> lobList = new ArrayList<String>(Arrays.asList(lob.split(",")));
		for(int i=0;i<brCode.length;i++){
			if(brCode[i] == 999999){
				isAllSelected = true;
			}
			if(brCode[i] == 9999999){
				isDirectSelected = true;
			}
		}
		try{
			Query query = null;
			if(isAllSelected){
				/*if(branchCode == 30){
					query = session.createSQLQuery(CLAS_WISE_PRM_INFO_OMN);
				}else {*/
					query = session.createSQLQuery(CLAS_WISE_PRM_INFO);
				//}
				
			    query.setDate(com.Constant.CONST_START_DATE, sqlSDate);
				query.setDate(com.Constant.CONST_END_DATE, sqlEDate);
			}else if(isDirectSelected){
				/*if(branchCode == 30){
					query = session.createSQLQuery(CLAS_WISE_PRM_INFO_OMN+CLAS_WISE_PRM_DIRECT);
				} else {*/
					query = session.createSQLQuery(CLAS_WISE_PRM_INFO+CLAS_WISE_PRM_DIRECT);
				//}
				
	            query.setParameterList(com.Constant.CONST_BR_CODE, brCode);
				query.setDate(com.Constant.CONST_START_DATE, sqlSDate);
				query.setDate(com.Constant.CONST_END_DATE, sqlEDate);
			}else{
				/*if(branchCode == 30){
					query = session.createSQLQuery(CLAS_WISE_PRM_INFO_OMN+CLAS_WISE_PRM_BR_CODE);
				
				} else {*/
					query = session.createSQLQuery(CLAS_WISE_PRM_INFO+CLAS_WISE_PRM_BR_CODE);
			/*	}*/
				
				query.setParameterList(com.Constant.CONST_BR_CODE, brCode);
				query.setDate(com.Constant.CONST_START_DATE, sqlSDate);
				query.setDate(com.Constant.CONST_END_DATE, sqlEDate);
			}
	        query.setInteger("locCode", branchCode);
			query.setParameterList( com.Constant.CONST_PTCODE, lobList );
			result = query.list();
					
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		
		if(result != null)			/* Added if condition (result check for null) - sonar violation fix */
		itr = result.iterator();
		Object[] row =null;
		//com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
		ReportsResultsHolder acctSearchHolder =  new ReportsResultsHolder();
		List<ClassWisePrmResultVO> classPrmResultList =  new com.mindtree.ruc.cmn.utils.List<ClassWisePrmResultVO>(ClassWisePrmResultVO.class);
		if(itr != null){			/* Added if condition (itr check for null) - sonar violation fix */
		while(itr.hasNext()){
			row = (Object[])itr.next();
			ClassWisePrmResultVO resultVO = new ClassWisePrmResultVO();
			resultVO.setInsuredName(String.valueOf(row[0]));

			resultVO.setPolicyType(String.valueOf(row[1]));
			resultVO.setPolicyNo(String.valueOf(row[2]));
			//Added for Bahrain 3 decimal - Starts
			//resultVO.setTotalPremium(String.valueOf(row[3]));
            if (Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("50")
                    && lob.equalsIgnoreCase((Utils.getSingleValueAppConfig("SBS_Policy_Type")))) {
             premium = null;
             premium = decFormBahrain.format(Double.valueOf((String.valueOf(row[3]))));
             resultVO.setTotalPremium(String.valueOf(premium));
		      } else {
		             resultVO.setTotalPremium(String.valueOf(row[3]));
		
		      }
          //Added for Bahrain 3 decimal - Ends
			resultVO.setCustomerName(String.valueOf(row[4]));
			resultVO.setDirectionCommission(String.valueOf(row[5]));
			resultVO.setPolicyIssueDate(String.valueOf(row[6]));
			resultVO.setIssueMonth(String.valueOf(row[7]));
			resultVO.setIssueYear(String.valueOf(row[8]));
			resultVO.setPolicyEffDate(String.valueOf(row[9]));
			resultVO.setSumInsured(String.valueOf(row[10]));
			resultVO.setCreatedBy(String.valueOf(row[11]));
			resultVO.setClassName( String.valueOf( row[12] ) );
			/*if(branchCode == 30){
				resultVO.setPolicyID(String.valueOf(row[13]));
				resultVO.setClassDescription(String.valueOf(row[14]));
			}*/
			resultVO.setClassDescription(String.valueOf(row[13]));
			resultVO.setReportDate(com.mindtree.ruc.cmn.utils.Utils.getDateAsString(new Date(), com.Constant.CONST_DATE_FORMAT_SLASH));
			
			classPrmResultList.add(resultVO);
		}
		}
		acctSearchHolder.setClassWisePrm(classPrmResultList);
		return acctSearchHolder;
	}
	public static java.sql.Date UtilDateToSqlDate(java.util.Date utilDate) {  
		java.sql.Date sqlDate = null;  
		if (utilDate != null)  
			sqlDate = new java.sql.Date(utilDate.getTime());  
		return sqlDate; 
	}
	
	/* (non-Javadoc)
	 * @see com.rsaame.pas.reports.dao.IReportsDAO#getViewSmsList(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaseVO getViewSmsList(BaseVO baseVO) {
		LOGGER.debug("*****In getViewSmSList method****");
		if (!Utils.isEmpty(baseVO)) {
			if (baseVO instanceof SmsVO) {
			
				List<TMasSms> smsList = null;
				try {
					smsList = getHibernateTemplate().find(
							GET_SMS_MASTER_DETAILS);					
				} catch (org.springframework.dao.DataAccessException exception) {
					throw new BusinessException("sms.details.noaccess",
							exception, "Unable to get data from the database");
				}

				List<SmsVO> smsLists = new com.mindtree.ruc.cmn.utils.List<SmsVO>(
						SmsVO.class);
				
				SmsListVO smsListDetails = new SmsListVO();

				for (TMasSms smsVO : smsList) {
					if (!Utils.isEmpty(smsVO)) {
						SmsVO smsdetails = BeanMapper.map(smsVO, SmsVO.class);										
						if(smsdetails.getSmsMode().equals( smsdetails.getSmsMode().equals(SvcConstants.SMS_AUTO)))
							smsdetails.setSmsMode(SvcConstants.AUTO_MODE);	
						else if(smsdetails.getSmsMode().equals(smsdetails.getSmsMode().equals(SvcConstants.SMS_MANUAL)))
							smsdetails.setSmsMode(SvcConstants.MANUAL_MODE);							
						if(smsdetails.getSmsStatus().equals(SvcConstants.SMS_STATUS_ACTIVE_CODE))
							smsdetails.setSmsStatus(SvcConstants.SMS_STATUS_ACTIVE);
						else if(smsdetails.getSmsStatus().equals(SvcConstants.SMS_STATUS_INACTIVE_CODE))
							smsdetails.setSmsStatus(SvcConstants.SMS_STATUS_INACTIVE);										
						smsLists.add(smsdetails);
						smsListDetails.setSmsVO(smsLists);
					}
				}
				return smsListDetails;
			}
		}
		return baseVO;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.reports.dao.IReportsDAO#saveNewSms(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO saveNewSms(BaseVO baseVO) {
		LOGGER.debug("*****In saveSmS method****");
		if (!Utils.isEmpty(baseVO)) {
			if (baseVO instanceof SmsVO) {
				SmsVO smsDetail = (SmsVO) baseVO;
				TMasSms sms = null;

				/* If new SMS then new SMS Id generated */
				if (Utils.isEmpty(smsDetail.getSmsID())) {
					sms = getSmsPojo(smsDetail);
					/*sms.setSmsID(NextSequenceValue.getNextSequence(
							"SEQ_SMS_ID", null, null, getHibernateTemplate()));*/
					
				} else {
					/* If Sms Id present then its an update operation */
					sms = (TMasSms) getHibernateTemplate().find(GET_SMS_DETAIL,
							smsDetail.getSmsID()).get(0);
					sms = getsmsPojoForUpdate(smsDetail, sms);
				}
				
				/* save update the details */
				saveOrUpdate(sms);

				return smsDetail;

			}
		}
		return baseVO;
	}

	/**
	 * @param smsDetail
	 * @param smsPojo
	 * @return
	 */
	private TMasSms getsmsPojoForUpdate(SmsVO smsDetail, TMasSms smsPojo) {
		LOGGER.debug("*****In getSmsPojoForUpdate method****");
		smsPojo = BeanMapper.map(smsDetail, smsPojo);	
		return smsPojo;
	}

	/**
	 * @param smsDetail
	 * @return
	 */
	private TMasSms getSmsPojo(SmsVO smsDetail) {	
		LOGGER.debug("*****In getSmsPojo method****");
		TMasSms sms = BeanMapper.map(smsDetail, TMasSms.class);					
		return sms;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.reports.dao.IReportsDAO#deleteSms(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO deleteSms(BaseVO baseVO) {
		LOGGER.debug("*****In deleteSms method****");
		DataHolderVO<Object> holderVO = (DataHolderVO<Object>) baseVO;
		Long[] smsIdList = (Long[]) holderVO.getData();			
				Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( "update TMasSms set smsStatus=:smsStatus where smsID in (:smsIdList)");
				query.setParameterList("smsIdList", smsIdList, new LongType());
				query.setParameter("smsStatus", 0, new IntegerType());				
				query.executeUpdate();					
				return baseVO;		
	}
	
	/**
	 * @param category
	 * @return
	 */
	private Map<Integer, String> getCodeDescMap( String category ){

		Map<Integer, String> codeMap = new HashMap<Integer, String>();

		if( !Utils.isEmpty( category ) ){
			LookUpListVO luList = SvcUtils.getLookUpCodesList( category, "ALL", "ALL" );

			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory
					.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );

			List<LookUpVO> luVOList = luList.getLookUpList();
			for( LookUpVO lu : luVOList ){
				if( !Utils.isEmpty( category ) ){
					Integer code = converter.getTypeOfB().cast( converter.getBFromA( lu.getCode() ) );
					if( !Utils.isEmpty( code ) ){
						codeMap.put( code, lu.getDescription() );
					}
				}

			}
		}
		return codeMap;
	}

	@Override
	public BaseVO paymentSearch(BaseVO baseVO) {
		
		ReportsSearchVO searchVO = (ReportsSearchVO) baseVO;
		Date sdate = searchVO.getStartDate();
		Date edate = searchVO.getEndDate();
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);
		String businessLine = "";
		if( !Utils.isEmpty( searchVO.getdistributionChannel() ) ){
			if( searchVO.getdistributionChannel().toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_B2C_DEFAULT_DIST_CHANNEL ) ) ){
				businessLine = POL_DISTRIBUTION_CHANNEL_B2C;
			}
			else{
				businessLine = POL_DISTRIBUTION_CHANNEL_B2B;
			}
		}
		List <Object> result = null;
		Iterator <Object>itr =null;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		try{
			Query query = null;
				query = session.createSQLQuery(GET_PAYMENT_RECORD+businessLine);
				LOGGER.debug("QUERY >>_1"+query);
			    query.setDate(com.Constant.CONST_START_DATE, sqlSDate);
				query.setDate(com.Constant.CONST_END_DATE, sqlEDate);
				result = query.list();
			}catch (HibernateException e) {
				e.printStackTrace();
			}
		if(result != null)			/* Added if condition (result check for null) - sonar violation fix */
		itr = result.iterator();
		Object[] row =null;
		ReportsResultsHolder paymentholder = new ReportsResultsHolder();
		List<PaymentReportVO> paymentReportList = new com.mindtree.ruc.cmn.utils.List<PaymentReportVO>(PaymentReportVO.class);
		if(itr != null){			/* Added if condition (itr check for null) - sonar violation fix */
		while(itr.hasNext()){
			row = (Object[])itr.next();
			try {
			PaymentReportVO resultVO = new PaymentReportVO();
			resultVO.setInsuranceType(String.valueOf(row[0]));
//			resultVO.setCreatedOn(String.valueOf(row[1]));
			resultVO.setPolicyNumber(new Long(row[2].toString()));
			SimpleDateFormat sdf = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_SLASH);
			resultVO.setEffDate(sdf.parse(String.valueOf(row[3])));
			SimpleDateFormat sdfExp = new SimpleDateFormat("yyyy-MM-dd");
			resultVO.setExpDate(sdfExp.parse(String.valueOf(row[4])));
			resultVO.setName(String.valueOf(row[5]));
			resultVO.setPayDate(sdf.parse(String.valueOf(row[6])));
			resultVO.setAmount(new Double(String.valueOf(row[7])));
			resultVO.setRecieptNumber(new Long(String.valueOf(row[8])));
			resultVO.setTransactionNumber(String.valueOf(row[9]));
			resultVO.setStatus(String.valueOf(row[10]));
			resultVO.setQuotationStatus(String.valueOf(row[11]));
			paymentReportList.add(resultVO);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		}
		paymentholder.setPayment(paymentReportList);
		return paymentholder;
	}

	@Override
	public BaseVO renewalPaymentSearch(BaseVO baseVO) {
		ReportsSearchVO searchVO = (ReportsSearchVO) baseVO;
		Date sdate = searchVO.getStartDate();
		Date edate = searchVO.getEndDate();
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);
		String businessLine = "";
		if( !Utils.isEmpty( searchVO.getdistributionChannel() ) ){
			if( searchVO.getdistributionChannel().toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_B2C_DEFAULT_DIST_CHANNEL ) ) ){
				businessLine= POL_DISTRIBUTION_CHANNEL_B2C ;
			}
			else{
				businessLine= POL_DISTRIBUTION_CHANNEL_B2B ;
			}
		}
		List <Object> result = null;
		Iterator <Object>itr =null;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		try{
			Query query = null;
				query = session.createSQLQuery(GET_RENEWAL_PAYMENT_RECORD+businessLine);
				LOGGER.debug("QUERY >>_2"+query);
			    query.setDate(com.Constant.CONST_START_DATE, sqlSDate);
				query.setDate(com.Constant.CONST_END_DATE, sqlEDate);
				result = query.list();
			}catch (HibernateException e) {
				e.printStackTrace();
			}
		if(result != null)			/* Added if condition (result check for null) - sonar violation fix */
		itr = result.iterator();
		Object[] row =null;
		ReportsResultsHolder paymentholder = new ReportsResultsHolder();
		List<RenewalPaymentReportVO> renewalPaymentReportList = new com.mindtree.ruc.cmn.utils.List<RenewalPaymentReportVO>(RenewalPaymentReportVO.class);
		if(itr != null){			/* Added if condition (itr check for null) - sonar violation fix */
		while(itr.hasNext()){
			row = (Object[])itr.next();
			try {
			RenewalPaymentReportVO resultVO = new RenewalPaymentReportVO();
			resultVO.setInsuranceType(String.valueOf(row[0]));
			resultVO.setPolicyNumber(new Long(row[1].toString()));
			resultVO.setName(String.valueOf(row[2]));
			SimpleDateFormat sdf = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_SLASH);
			resultVO.setRenewalDate(sdf.parse(String.valueOf(row[3])));
			resultVO.setPayDate(sdf.parse(String.valueOf(row[4])));
			resultVO.setAmount(new Double(String.valueOf(row[5])));
			resultVO.setRecieptNumber(new Long(String.valueOf(row[6])));
			resultVO.setTransactionNumber(String.valueOf(row[7]));
			resultVO.setStatus(String.valueOf(row[8]));
			resultVO.setQuotationStatus(String.valueOf(row[9]));
			if( !Utils.isEmpty( row[10] ))
				resultVO.setAirmiles( String.valueOf( row[10] ) );
			renewalPaymentReportList.add(resultVO);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		}
		paymentholder.setRenewalpayment(renewalPaymentReportList);
		return paymentholder;
	}
	
	@Override
	public BaseVO transReportsSearch(BaseVO baseVO) {
		
		ReportsSearchVO searchVO = (ReportsSearchVO) baseVO;
		Date sdate = searchVO.getStartDate();
		Date edate = searchVO.getEndDate();
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);
		String businessLine = null;
		if( !Utils.isEmpty( searchVO.getdistributionChannel() ) ){
			businessLine = searchVO.getdistributionChannel().toString() ;
		}
		
		List <Object> result = null;
		Iterator <Object>itr =null;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		try{
			Query query = null;
				query = session.createSQLQuery(GET_TRANSACTION_RECORD);
				LOGGER.debug("QUERY >>_3"+query);
			    query.setDate("startDate", sqlSDate);
				query.setDate("endDate", sqlEDate);
				query.setString( "P_DISTRIBUTION_CHNL", businessLine );
				result = query.list();
			}catch (HibernateException e) {
				e.printStackTrace();
			}
		if( Utils.isEmpty( result ) ){
			throw new BusinessException( "pas.src.Empty", null, "No records foun_2" );
		}
		itr = result.iterator();
		Object[] row =null;
		ReportsResultsHolder transReportSearchHolder =  new ReportsResultsHolder();
		List<TransactionalReportsVO> transactionalReportsVOs = new ArrayList<TransactionalReportsVO>();
		int i = 0;
		while(itr.hasNext()){
			TransactionalReportsVO reportsVO = new TransactionalReportsVO();
			row = (Object[])itr.next();
			
			try {
				reportsVO.setInsuranceType(String.valueOf(row[0]));
				String row2 = String.valueOf(row[1]);
				String[] param2 = row2.split("~");
				reportsVO.setTotalQuotePrem(new BigDecimal( param2[1]));
				reportsVO.setTotalQuoteCount(new Integer(param2[3]));
				String row3 = String.valueOf(row[2]);
				String[] param3 = row3.split("~");
				reportsVO.setAbondonedQuoteCount(new Integer(param3[3]));
				reportsVO.setAbondonedQuotePrem(new BigDecimal(param3[1]));
				String row4 = String.valueOf(row[3]);
				String[] param4 = row4.split("~");
				reportsVO.setSavedQuoteCount(new Integer(param4[3]));
				reportsVO.setSavedQuotePrem(new BigDecimal( param4[1]));
				String row5 = String.valueOf(row[4]);
				String[] param5 = row5.split("~");
				reportsVO.setPaymentReceivedCount(new Integer(param5[3]));
				reportsVO.setPaymentReceivedPrem(new BigDecimal( param5[1]));
				String row6 = String.valueOf(row[5]);
				String[] param6 = row6.split("~");
				reportsVO.setReferalPendingCount(new Integer(param6[3]));
				reportsVO.setReferalPendingPrem(new BigDecimal( param6[1]));
				String row7 = String.valueOf(row[6]);
				String[] param7 = row7.split("~");
				reportsVO.setAcceptedReferalCount(new Integer(param7[3]));
				reportsVO.setAcceptedReferalPrem(new BigDecimal( param7[1]));
				String row8 = String.valueOf(row[7]);
				String[] param8 = row8.split("~");
				reportsVO.setConvertedReferalCount(new Integer(param8[3]));
				reportsVO.setConvertedReferalPrem(new BigDecimal( param8[1]));
				String row9 = String.valueOf(row[8]);
				String[] param9 = row9.split("~");
				reportsVO.setRejectedReferalCount(new Integer(param9[3]));
				reportsVO.setRejectedReferalPrem(new BigDecimal( param9[1]));
				String row10 = String.valueOf(row[9]);
				String[] param10 = row10.split("~");
				reportsVO.setConvertedToPolicyCount(new Integer(param10[3]));
				reportsVO.setConvertedToPolicyPrem(new BigDecimal( param10[1]));
				String row11 = String.valueOf(row[10]);					
				String[] param11 = row11.split("~");
				reportsVO.setAssistedQuotationCount(new Integer(param11[3]));
				reportsVO.setAssistedQuotationPrem(new BigDecimal(param11[1]));
				Double quotrate;
				if(new Integer(param2[3]) == 0){
					quotrate = 0.0;
				}else {
				quotrate = new Double((new Double(param5[3])/new Double(param2[3]))*100);
				quotrate = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( quotrate ) ) );
				}
				reportsVO.setQuotationStrikeRate(quotrate);
				Double refrate;
				if( new Integer( param2[ 3 ] ) - new Integer( param3[ 3 ] ) == 0 ){
					refrate = 0.0;
				}else {
					refrate = new Double( ( new Double( param6[ 3 ] ) + new Double( param7[ 3 ] ) + new Double( param8[ 3 ] ) + new Double( param9[ 3 ] ) )
							/ ( new Double( param2[ 3 ] ) - new Double( param3[ 3 ] ) ) ) * 100;
					refrate = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( refrate ) ) );
				}
				reportsVO.setReferalPercentage(refrate);
				Double refconvrate;
				if(new Integer(param7[3]) == 0){
					refconvrate = 0.0;
				}else{
				refconvrate = new Double((new Double(param8[3])/new Double(param7[3]))*100);
				refconvrate = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( refconvrate ) ) );
				
				}
				reportsVO.setReferalConvRate(refconvrate);
				reportsVO.setReferalConvRate(refconvrate);
				}catch (HibernateException e) {
					e.printStackTrace();
				}
			transactionalReportsVOs.add(reportsVO);
		}
		transReportSearchHolder.setTransReports(transactionalReportsVOs);
		return transReportSearchHolder;
	}

	@Override
	public BaseVO transReportsDetailSearch(BaseVO baseVO) {
		DataHolderVO dataHolderVO = (DataHolderVO) baseVO;
		List<String> paramList = (List<String>) dataHolderVO.getData();
		Date sdate = null;
		Date edate = null;
		com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MM/yyyy" );
		sdate = converter.getTypeOfA().cast( converter.getAFromB(paramList.get(0)));
		edate = converter.getTypeOfA().cast( converter.getAFromB(paramList.get(1)));
	//		sdate = sdf.parse(paramList.get(0));
	//		edate= sdf.parse(paramList.get(1));
		String poltype = paramList.get(2);
		String polstatus = paramList.get(3);
		String desc = paramList.get(4);
		String businessLine = paramList.get( 5 );
		if(Utils.isEmpty( businessLine )){
			businessLine = null;
		}
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);
		List<Object[]> valueHolder = null;
		Iterator <Object[]>itr =null;
		try{
			LOGGER.debug("date1 >> "+sqlSDate);
			LOGGER.debug("date2 >> "+sqlEDate);
			LOGGER.debug("poltype >> "+poltype);
			LOGGER.debug("polstatus >> "+polstatus);
			LOGGER.debug("statusdescription >> "+desc);
			LOGGER.debug("businessLine >> "+businessLine);
			valueHolder = DAOUtils.getSqlResult( "SELECT * FROM table(PKG_PAS_QUO_POL_HOME.FN_GET_TRANSACTION_RPT(to_date(\'"+format.format(sdate)+"\',\'DD/MM/YYYY\'), to_date(\'"+format.format(edate)+"\',\'DD/MM/YYYY\'), "+poltype+" , "+polstatus+" , '"+desc+"', "+businessLine+") )", getHibernateTemplate() );
			
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		if( Utils.isEmpty( valueHolder ) ){
			throw new BusinessException( "pas.src.Empty", null, "No records foun_3" );
		}
		itr = valueHolder.iterator();
		Object[] row =null;	
		ReportsResultsHolder transReportDetailHolder =  new ReportsResultsHolder();
		List<TransactionalReportsDetailVO> transactionalReportsDetailVOs = new ArrayList<TransactionalReportsDetailVO>();
		int i = 0;
		while(itr.hasNext()){
			TransactionalReportsDetailVO detailVO = new TransactionalReportsDetailVO();
			row = (Object[])itr.next();
			try {
				if(!Utils.isEmpty( row[0] ))
					detailVO.setRefNumber(new Long(String.valueOf(row[0])));
				detailVO.setName(String.valueOf(row[1]));
				detailVO.setEmailId(String.valueOf(row[2]));
				detailVO.setMobileNo(String.valueOf(row[3]));
				detailVO.setEmirates(String.valueOf(row[4]));
				detailVO.setNationality(String.valueOf(row[5]));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				/*java.sql.Date datex = (java.sql.Date) row[6];
				Date regdate = new Date( (datex).getTime());*/
				try {
					detailVO.setRegDate(sdf.parse(String.valueOf(row[6])));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				detailVO.setStatus(String.valueOf(row[7]));
				detailVO.setAmount(new BigDecimal(String.valueOf(row[8])));
                detailVO.setCreatedBy( String.valueOf( row[9] ) );
				detailVO.setScheme( String.valueOf( row[10] ) );
				detailVO.setBrokerName(String.valueOf(row[11]));
				detailVO.setInternalACExe(String.valueOf(row[13]));
				detailVO.setTransaction(String.valueOf(row[12]));
			}catch (HibernateException e) {
				e.printStackTrace();
			}
			transactionalReportsDetailVOs.add(detailVO);
		}
		transReportDetailHolder.setTransReportsdetails(transactionalReportsDetailVOs);
		return transReportDetailHolder;
	}

	@Override
	public BaseVO promoCodeReportSearch(BaseVO baseVO) {
		ReportsSearchVO searchVO = (ReportsSearchVO) baseVO;
		Date sdate = searchVO.getStartDate();
		Date edate = searchVO.getEndDate();
		String pcode = searchVO.getPromoCode();
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
		java.sql.Date sqlEDate = UtilDateToSqlDate(edate);
		String businessLine = null;
		if( !Utils.isEmpty( searchVO.getdistributionChannel() ) ){
			businessLine = searchVO.getdistributionChannel().toString() ;
		}
		List <Object> result = null;
		Iterator <Object>itr =null;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		try{
			Query query = null;
				query = session.createSQLQuery(GET_PROMOCODE_RECORD + GET_PROMOCODE_RECORD_GROUP_BY);
				LOGGER.debug("QUERY >>_4"+query);
			    query.setDate("P_START_DATE", sqlSDate);
				query.setDate("P_END_DATE", sqlEDate);
				query.setString("p_promo_code", pcode);
				query.setString( "P_DISTRIBUTION_CHNL", businessLine );
				result = query.list();
			}catch (HibernateException e) {
				e.printStackTrace();
			}
		if(result != null)			/* Added if condition (result check for null) - sonar violation fix */
		itr = result.iterator();
		Object[] row =null;
		ReportsResultsHolder promoCodeReportSearchHolder =  new ReportsResultsHolder();
		List<PromotionalCodeReportVO> promotionalCodeReportVO = new ArrayList<PromotionalCodeReportVO>();
		int i = 0;
		if(itr != null){			/* Added if condition (itr check for null) - sonar violation fix */
		while(itr.hasNext()){
			PromotionalCodeReportVO reportsVO = new PromotionalCodeReportVO();
			row = (Object[])itr.next();
			
			try {
				reportsVO.setPromoCode(String.valueOf(row[0]));
				String row2 = String.valueOf(row[2]);
				String[] param2 = row2.split("~");
				reportsVO.setTotalQuotePrem(new BigDecimal( param2[1]));
				reportsVO.setTotalQuoteCount(new Integer(param2[3]));
				String row3 = String.valueOf(row[3]);
				String[] param3 = row3.split("~");
				reportsVO.setAbondonedQuoteCount(new Integer(param3[3]));
				reportsVO.setAbondonedQuotePrem(new BigDecimal(param3[1]));
				String row4 = String.valueOf(row[4]);
				String[] param4 = row4.split("~");
				reportsVO.setSavedQuoteCount(new Integer(param4[3]));
				reportsVO.setSavedQuotePrem(new BigDecimal( param4[1]));
				String row5 = String.valueOf(row[5]);
				String[] param5 = row5.split("~");
				reportsVO.setPaymentReceivedCount(new Integer(param5[3]));
				reportsVO.setPaymentReceivedPrem(new BigDecimal( param5[1]));
				String row6 = String.valueOf(row[6]);
				String[] param6 = row6.split("~");
				reportsVO.setReferalPendingCount(new Integer(param6[3]));
				reportsVO.setReferalPendingPrem(new BigDecimal( param6[1]));
				String row7 = String.valueOf(row[7]);
				String[] param7 = row7.split("~");
				reportsVO.setAcceptedReferalCount(new Integer(param7[3]));
				reportsVO.setAcceptedReferalPrem(new BigDecimal( param7[1]));
				String row8 = String.valueOf(row[8]);
				String[] param8 = row8.split("~");
				reportsVO.setConvertedReferalCount(new Integer(param8[3]));
				reportsVO.setConvertedReferalPrem(new BigDecimal( param8[1]));
				String row9 = String.valueOf(row[9]);
				String[] param9 = row9.split("~");
				reportsVO.setRejectedReferalCount(new Integer(param9[3]));
				reportsVO.setRejectedReferalPrem(new BigDecimal( param9[1]));
				String row10 = String.valueOf(row[10]);
				String[] param10 = row10.split("~");
				reportsVO.setConvertedToPolicyCount(new Integer(param10[3]));
				reportsVO.setConvertedToPolicyPrem(new BigDecimal( param10[1]));
				Double quotrate;
				if( new Integer( param2[ 3 ] ) == 0 ){
					quotrate = 0.0;
				}
				else{
					quotrate = new Double( ( new Double( param5[ 3 ] ) / new Double( param2[ 3 ] ) ) * 100 );
					quotrate = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( quotrate ) ) );
				}
				reportsVO.setQuotationStrikeRate( quotrate );
				Double refrate;
				if( new Integer( param2[ 3 ] ) - new Integer( param3[ 3 ] ) == 0 ){
					refrate = 0.0;
				}
				else{
					refrate = new Double( ( new Double( param6[ 3 ] ) + new Double( param7[ 3 ] ) + new Double( param8[ 3 ] ) + new Double( param9[ 3 ] ) )
							/ ( new Double( param2[ 3 ] ) - new Double( param3[ 3 ] ) ) ) * 100;
					refrate = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( refrate ) ) );
				}
				reportsVO.setReferalPercentage( refrate );
				Double refconvrate;
				if( new Integer( param7[ 3 ] ) == 0 ){
					refconvrate = 0.0;
				}
				else{
					refconvrate = new Double( ( new Double( param8[ 3 ] ) / new Double( param7[ 3 ] ) ) * 100 );
					refconvrate = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( refconvrate ) ) );

				}
				reportsVO.setReferalConvRate(refconvrate);
				}catch (HibernateException e) {
					e.printStackTrace(); 
				}
				promotionalCodeReportVO.add(reportsVO);
		}
		}
		promoCodeReportSearchHolder.setPromocode(promotionalCodeReportVO);
		return promoCodeReportSearchHolder;
	}
 /*
  * (non-Javadoc)
  * @see com.rsaame.pas.reports.dao.IReportsDAO#quoteSearch(com.mindtree.ruc.cmn.base.BaseVO)
  * 81120 3.2 release new report on quotes
  */
	@Override
	
	public BaseVO quoteSearch(BaseVO baseVO) {
		ReportsSearchVO searchVO = (ReportsSearchVO) baseVO;
		Date sdate = searchVO.getStartDate();
		Date edate = searchVO.getEndDate();
		//SimpleDateFormat dateFormat=new SimpleDateFormat(com.Constant.CONST_DD_MM_YYYY);
		String quotationType=searchVO.getLob();
		Integer[] brokerCode=searchVO.getBrokerCodeList();
		java.sql.Date sqlSDate = UtilDateToSqlDate(sdate);
	    java.sql.Date sqlEDate = UtilDateToSqlDate(edate);   
/*	String sqlSDate = dateFormat.format(sdate);
	    String sqlEDate =dateFormat.format(edate);   */
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		List <Object> result = null;
		String brcode=new String();
		//Added Stringbuffer to fix sonar violation on 13-9-2017
		StringBuffer brCodeBuffer=new StringBuffer();
		Iterator <Object>itr =null;
		String isAllSelected=new String();
		String isDirectSelected=new String();
		String isBrokerSelected=new String();
		
		  for(int i=0;i<brokerCode.length;i++){
				if(brokerCode.length==1 || i==brokerCode.length-1){
					brcode=brCodeBuffer.append(brokerCode[i]).toString();
					//brcode+=brokerCode[i];
				}
				else {
					//brcode+=brokerCode[i]+"-";
					brcode=brCodeBuffer.append(brokerCode[i]).append("-").toString();
				}
				if(brokerCode[i]==999999)
					isAllSelected="true";
				if(brokerCode[i]==9999999)
					isDirectSelected="true";	
				if(brokerCode[i]!=999999 && brokerCode[i]!=9999999 &&  isBrokerSelected!="true"){
					isBrokerSelected="true";
				}
			}
		 
	/*	boolean isAllSelected=false;
		boolean isDirectselected=false;
	
		for(int i=0;i<brokerCode.length;i++){
			if(brokerCode[i]==999999)
				isAllSelected=true;
			if(brokerCode[i]==9999999)
				isDirectselected=true;	
			
		} */
		
		  if(isAllSelected=="true"){
				isDirectSelected="";
				isBrokerSelected="";
			}
		
		try{
			Query query = null;
			
				query = session.createSQLQuery(GET_QUOTE_REPORT_RPT);
		//	LIVE_POL_FUNC = "SELECT * from table(PKG_PAS_QUO_POL_HOME.FN_GET_LIVE_POL_RPT (:BrokerCodearray,:BrokerCode,:start_date,:end_date,:locCode,:ptCode))";
		//	query = session.createSQLQuery(LIVE_POL_FUNC);
			
			LOGGER.debug("QUERY >>_5"+query);
				//:BrokerCodearray,:start_date,:end_date,:pol_type,:isAllSelected,:isBrkSelected,:isDrtSelected
			 System.out.println("Broker "+brcode);
			 query.setString("BrokerCodearray", brcode);
			    query.setDate(com.Constant.CONST_START_DATE, sqlSDate);
			     //System.out.println("Date "+dateFormat.format(sqlSDate));
			   
				query.setDate("End_date", sqlEDate);
		//		System.out.println("Date "+dateFormat.format(sqlEDate));
				
		
				query.setInteger("pol_type",Integer.parseInt(quotationType));
				System.out.println("type "+Integer.parseInt(quotationType));
				query.setString("isAllSelected",isAllSelected);
				System.out.println("allSelected "+isAllSelected);
				query.setString("isBrkSelected", isBrokerSelected);
				System.out.println("BrkSelected "+isBrokerSelected);
				query.setString("isDrtSelected",isDirectSelected);
				System.out.println("Direct "+isDirectSelected);
				LOGGER.debug("QUERY After>> "+query);  
				result = query.list();
			}catch (HibernateException e) {
				e.printStackTrace();
			}
		if( Utils.isEmpty( result ) ){
			throw new BusinessException( "pas.src.Empty", null, "No records foun_4" );
		}
		itr = result.iterator();
		Object[] row =null;
		ReportsResultsHolder quoteReportHolder = new ReportsResultsHolder();
		List<QuoteRportVO> quoteReportList = new com.mindtree.ruc.cmn.utils.List<QuoteRportVO>(QuoteRportVO.class);
		while(itr.hasNext()){
			row = (Object[])itr.next();
			try {
				QuoteRportVO resultVO = new QuoteRportVO();
		    resultVO.setBrokerName(String.valueOf(row[0]));
		    resultVO.setInsuredName(String.valueOf(row[1]));
		    resultVO.setQuoteType(String.valueOf(row[2]));
		    resultVO.setQuoteIssueMonth(String.valueOf(row[3]));
		    resultVO.setQuoteIssueYear(String.valueOf(row[4]));
		    resultVO.setQuotationNumber(new Long(String.valueOf(row[5])));
		    SimpleDateFormat sdf = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_SLASH);
		    if(row[6]!=null)
		    resultVO.setQuoteCreationDate(sdf.parse(String.valueOf(row[6])));
		    resultVO.setQuotationStatus(String.valueOf(row[7]));
		    if(row[8]!=null)
		    resultVO.setPolicyNumber(new Long(String.valueOf(row[8])));
		    if(row[9]!=null)
		    resultVO.setPolicyEffectiveDate(sdf.parse(String.valueOf(row[9])));
		    if(row[10]!=null)
		    resultVO.setPolicyExpiryDate(sdf.parse(String.valueOf(row[10])));
		    if(row[11]!=null)
		    resultVO.setQuotationPremium(new Double(String.valueOf(row[11])));
		    if(row[12]!=null)
		    resultVO.setCommission(new Double(String.valueOf(row[12])));
		    resultVO.setUserName(String.valueOf(row[13]));
		  
		
			quoteReportList.add(resultVO); 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		quoteReportHolder.setQuoteReportVO(quoteReportList);
		
	
		return quoteReportHolder;
		
	} 

	

}
