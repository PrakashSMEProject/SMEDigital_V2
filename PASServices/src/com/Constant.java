package com;
public class Constant{
	public static final String CONST_FROM_TTRNPREMIUMQUO_PRM_WHERE_PRM_ID_PRMPOLICYID_AND_PRM_ID_PRMBASICRSKID_AND_PRM_ID_PRMRSKCODE_AND_PRM_PRMENDTID_AND_PRM_PRMSTATUS_4_AND_PRM_PRMVALIDITYEXPIRYDATE_END = "from TTrnPremiumQuo prm where prm.id.prmPolicyId= ? and prm.id.prmBasicRskId = ? and prm.id.prmRskCode = ? and prm.prmEndtId <= ? and prm.prmStatus <> 4 and prm.prmValidityExpiryDate = ?";
	public static final String CONST_TASK_CATEGORY = "TASK_CATEGORY";
	public static final String CONST_SEQ_TASK_ID = "SEQ_TASK_ID";
	public static final String CONST_BASELOADOPERATION_POL = "baseLoadOperation_POL";
	public static final String CONST_BASELOADOPERATION = "baseLoadOperation";
	public static final String CONST_FROM_VMASPRODUCTCONFIGPAS_VIEW_WHERE_VIEW_PCSCHEME_END = " from VMasProductConfigPas view where view.pcScheme = ? ";
	public static final String CONST_VATBLEAMT = "vatbleAmt";
	public static final String CONST_POLICY_ACCEPT = "POLICY_ACCEPT";
	public static final String CONST_DD_MMM_YYYY = "dd-MMM-yyyy";
	public static final String CONST_PROVATTAX = "ProvatTax";
	public static final String CONST_DIFFINDAYS = "diffInDays";
	public static final String CONST_DEFAULT_DATE_FORMAT = "DEFAULT_DATE_FORMAT";
	public static final String CONST_D_END = "^\\d*\\.";
	public static final String CONST_DEFAULT_CURRENCY = "DEFAULT_CURRENCY";
	public static final String CONST_POLICYPERIODDAYS = "policyPeriodDays";
	public static final String CONST_VATTAX = "vatTax";
	public static final String CONST_LOOKUPDAO = "lookUpDAO";
	public static final String CONST_BI_RR_LABEL = "BI_RR_LABEL";
	public static final String CONST_APP_FLOW_PRE_SVC = "APP_FLOW_PRE_SVC";
	public static final String CONST_READ = "#READ";
	public static final String CONST_ALREADYREAD = "alreadyRead";
	public static final String CONST_ENDORSEMENTDATEBACKDATING = "endorsementDateBackdating";
	public static final String CONST_DEDUCTABLEMINVALUE = "deductableMinValue";
	public static final String CONST_SUMINSUREDPERLOCATION = "sumInsuredPerLocation";
	public static final String CONST_SUMINSUREDPERPOLICY = "sumInsuredPerPolicy";
	public static final String CONST_ENDORSEMENTDATEPOSTDATING = "endorsementDatePostdating";
	public static final String CONST_JLT_LIVEDATE = "JLT_LiveDate";
	public static final String CONST_JLT_SCHEMECODE = "JLT_SchemeCode";
	public static final String CONST_COMMISSIONDIFFERENCE = "commissionDifference";
	public static final String CONST_UW_ANSWER_TEXT_RESPONSE_TYPE = "UW_ANSWER_TEXT_RESPONSE_TYPE";
	public static final String CONST_FROM_T_MAS_CODE_MASTER = " FROM T_Mas_Code_Master";
	public static final String CONST_NO_9999999 = "9999999";
	public static final String CONST_START_DATE = "start_date";
	public static final String CONST_DATE_FORMAT_SLASH = "dd/MM/yyyy";
	public static final String CONST_WHERE_PRM_POLICY_ID_POL_POLICY_ID_AND_PRM_ENDT_ID_POL_ENDT_ID_AND_POL_DOCUMENT_CODE_IN_1_3_AND_TRUNC_PRM_VALIDITY_EXPIRY_DATE_31_DEC_2049_GROUP_BY_POL_POLICY_NO_PREMIUM = "where prm_policy_id= pol_policy_id and prm_endt_id = pol_endt_id and pol_document_code in (1,3) and trunc(prm_validity_expiry_date) = '31-DEC-2049' group by pol_policy_no ) premium";
	public static final String CONST_SELECT_FROM_END = "select * from ( ";
	public static final String CONST_BR_CODE = "br_code";
	public static final String CONST_END_DATE = "end_date";
	public static final String CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOB = " WHERE Cdm_Entity_Type='PAS_LOB'";
	public static final String CONST_B2C_DEFAULT_DIST_CHANNEL = "B2C_DEFAULT_DIST_CHANNEL";
	public static final String CONST_WHERE_CDM_ENTITY_TYPE_PAS_LOBMAP = " WHERE Cdm_Entity_Type='PAS_LOBMAP'";
	public static final String CONST_POLVATCODE = "polvatCode";
	public static final String CONST_POLPOLICYTYPE = "polPolicyType";
	public static final String CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER = "FILE_UPLOAD_TRADE_LICENCE_FOLDER";
	public static final String CONST_BRCODE = "brCode";
	public static final String CONST_EFFECTIVEDATE = "effectiveDate";
	public static final String CONST_FILE_UPLOAD_ROOT = "FILE_UPLOAD_ROOT";
	public static final String CONST_POLSTATUS = "polStatus";
	public static final String CONST_SELECT_FROM_T_TRN_POLICY_WHERE_POL_POLICY_ID_POLICYID = "SELECT * FROM T_TRN_POLICY WHERE POL_POLICY_ID = :policyId";
	public static final String CONST_MISLIVE_USER = "MISLIVE_USER";
	public static final String CONST_PAS_LOBMST = "PAS_LOBMST";
	public static final String CONST_PREMIUM_END = " Premium :";
	public static final String CONST_SCHEMA_IS_NOT_CONFIGURED = "Schema is not configured";
	public static final String CONST_PAR_CONT_END = "PAR_CONT_";
	public static final String CONST_EE_NUM_END = "EE_NUM:";
	public static final String CONST_PAR_CONT_TYPE = "PAR_CONT_TYPE";
	public static final String CONST_DOS_NUM_END = "DOS_NUM:";
	public static final String CONST_JAZFA_WC_MIN_PREMIUM = "JAZFA_WC_MIN_PREMIUM";
	public static final String CONST_GPA_NAMED_END = "GPA_NAMED:";
	public static final String CONST_GIT_NUM_END = "GIT_NUM:";
	public static final String CONST_GPA_UNNAMED_END = "GPA_UNNAMED:";
	public static final String CONST_EMP_TYPE_END = "EMP_TYPE:";
	public static final String CONST_MONEY_CONT_TYPE = "MONEY_CONT_TYPE";
	public static final String CONST_MB_NUM_END = "MB_NUM:";
	public static final String CONST_FID_NAMED_END = "FID_NAMED:";
	public static final String CONST_FID_UNNAMED_END = "FID_UNNAMED:";
	public static final String CONST_UWQ_CODE_PAR_ALARM_FLG = "UWQ_CODE_PAR_ALARM_FLG";
	public static final String CONST_RATING_PAR_ITM_NUM = "rating.par.itm.num";
	public static final String CONST_ITEM_SEQ_NUMBER_NOT_FOUND = "Item Seq Number Not Found";
	public static final String CONST_UWQ_CODE_PAR_SPRIKLERFLG = "UWQ_CODE_PAR_SPRIKLERFLG";
	public static final String CONST_RATING_BI_ITM_NUM = "rating.bi.itm.num";
	public static final String CONST_ITEM_SEQUENCE_NUMBER_NOT_SET_END = "Item sequence number not set.";
	public static final String CONST_UWQ_CODE_PAR_STORAGEFLG = "UWQ_CODE_PAR_STORAGEFLG";
	public static final String CONST_AND_PR_PRM_ENDT_ID_END = " ) and pr.prm_endt_id <= ? ";
	public static final String CONST_POLID = "polId";
	public static final String CONST_MESSAGE = "message";
	public static final String CONST_POLPROCLOCCODE = "polProcLocCode";
	public static final String CONST_POLLOCCODE = "polLocCode";
	public static final String CONST_QUOTE_PDF = "-Quote.pdf";
	public static final String CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_PR_WHERE_PR_PRM_CL_CODE_END = "Select sum(nvl(pr.PRM_PREMIUM,0)) From T_TRN_PREMIUM PR Where pr.prm_cl_code = ";
	public static final String CONST_AND_PR_PRM_POLICY_ID_END = " and pr.prm_policy_id = ";
	public static final String CONST_FROM_DUAL1 = "') from dual";
	public static final String CONST_TRUNC_PR_PRM_VALIDITY_EXPIRY_DATE_END = "trunc(pr.prm_validity_expiry_date) = '";
	public static final String CONST_SELECT_SUM_NVL_PR_PRM_PREMIUM_0_FROM_T_TRN_PREMIUM_QUO_PR_WHERE_PR_PRM_CL_CODE_END = "Select sum(nvl(pr.PRM_PREMIUM,0)) From T_TRN_PREMIUM_QUO PR Where pr.prm_cl_code = ";
	public static final String CONST_ENDING = "*************";
	public static final String CONST_AND_PR_PRM_BASIC_RSK_CODE_99999_AND_PR_PRM_COV_CODE_END = " and pr.prm_Basic_Rsk_Code = 99999 and pr.prm_cov_code <> ";
	public static final String CONST_AND_PR_PRM_STATUS_END = " and pr.prm_status <> ";
	public static final String CONST_FROM_TTRNPOLICYQUO_POLICYQUO_WHERE_POLICYQUO_POLLINKINGID_AND_POLICYQUO_ID_POLENDTID_OR_POLICYQUO_ID_POLENDTID_AND_POLICYQUO_POLVALIDITYEXPIRYDATE_AND_POLICYQUO_POLPOLICYTYPE_END = "from TTrnPolicyQuo policyQuo where policyQuo.polLinkingId=? and (policyQuo.id.polEndtId=? or (policyQuo.id.polEndtId<? and policyQuo.polValidityExpiryDate=?)) and policyQuo.polPolicyType=?";
	public static final String CONST_POLICY_TYPES = "POLICY_TYPES";
	public static final String CONST_DATE_FORMAT_HYPHEN = "dd-MM-yyyy";
	public static final String CONST_T_TRN_PREMIUM = "T_TRN_PREMIUM";
	public static final String CONST_PAR_COVER_TYPE = "PAR_COVER_TYPE";
	public static final String CONST_PAR_BASIC_RISK = "PAR_BASIC_RISK";
	public static final String CONST_PAR_COVER_SUB_TYPE = "PAR_COVER_SUB_TYPE";
	public static final String CONST_MONEY_BASIC_RISK_CODE = "MONEY_BASIC_RISK_CODE";
	public static final String CONST_MONEY_RISK_TYPES_7 = "MONEY_RISK_TYPES_7";
	public static final String CONST_MONEY_BASIC_RI_RSK_CODE = "MONEY_BASIC_RI_RSK_CODE";
	public static final String CONST_C_PASDOCUMENTS_SAMPLE_PDF = "C:/PASDocuments/Sample.pdf";
	public static final String CONST_SUCCESS = "success";
	public static final String CONST_FILE_NOT_FOUND_END = "File Not Found:";
	public static final String CONST_DOC_EMAIL_TESTING = "DOC_EMAIL_TESTING";
	public static final String CONST_ERROR_OCCURED_DURING_SENDING_MAIL = "Error occured during Sending mail";
	public static final String CONST_AND_POL_ISSUE_HOUR_3 = " AND POL_ISSUE_HOUR = 3";
	public static final String CONST_BUILDINGSAVESERVICE = "buildingSaveService";
	public static final String CONST_PAS_MORTGAGEE_NAME = "PAS_MORTGAGEE_NAME";
	public static final String CONST_PRM_TO_BE_DELETED = "PRM_TO_BE_DELETED";
	public static final String CONST_USER_END = "USER_";
	public static final String CONST_GECOMSVC = "geComSvc";
	public static final String CONST_GETNEXTENDORSEMENTID = "getNextEndorsementId";
	public static final String CONST_EE_RISK_CODE = "EE_RISK_CODE";
	public static final String CONST_EE_RISK_TYPE_CODE = "EE_RISK_TYPE_CODE";
	public static final String CONST_EE_RISK_DETAIL = "EE_RISK_DETAIL";
	public static final String CONST_DOS_RISK_TYPE_CODE = "DOS_RISK_TYPE_CODE";
	public static final String CONST_PRM_TO_BE_CREATED = "PRM_TO_BE_CREATED";
	public static final String CONST_DOS_RISK_CODE = "DOS_RISK_CODE";
	public static final String CONST_DOS_RISK_DETAIL = "DOS_RISK_DETAIL";
	public static final String CONST_POLICYSCHEDULECLAUSESUAE = "policyScheduleClausesUAE";
	public static final String CONST_REPORTTEMPLATESTYPE = "reportTemplatesType";
	public static final String CONST_POLICYSCHEDULE = "PolicySchedule";
	public static final String CONST_RECEIPT = "Receipt";
	public static final String CONST_ENDSCHEDULEREPORT = "EndScheduleReport";
	public static final String CONST_CREDITNOTEREPORT = "CreditNoteReport";
	public static final String CONST_REPORT_CREATION_INPUT_ERROR = "Report creation Input Error";
	public static final String CONST_FAILED = "failed";
	public static final String CONST_GROSSCREDITNOTEREPORT = "GrossCreditNoteReport";
	public static final String CONST_RENEWALSTATUSREPORTEMAIL = "RenewalStatusReportEmail";
	public static final String CONST_SHOWBANKLETTER = "ShowBankLetter";
	public static final String CONST_GROSSDEBITNOTEREPORT = "GrossDebitNoteReport";
	public static final String CONST_FAILURE = "failure";
	public static final String CONST_FALSE = "false";
	public static final String CONST_DEBITNOTEREPORT = "DebitNoteReport";
	public static final String CONST_FROM_END = " from ";
	public static final String CONST_TABLE_END = "TABLE_";
	public static final String CONST_WHERE_END = " where ";
	public static final String CONST_SELECT_END = "select ";
	public static final String CONST_QUERY_END = " = ? ";
	public static final String CONST_AND_END = " and ";
	public static final String CONST_PTCODE = "ptCode";
	public static final String CONST_SBS_POLICY_ISSUE_HOUR = "SBS_POLICY_ISSUE_HOUR";
	public static final String CONST_POL_POLICY_NO = "POL_POLICY_NO";
	public static final String CONST_COVERCODE = "coverCode";
	public static final String CONST_SELECT_DISTINCT_PR_PRM_RATE_FROM_T_TRN_PREMIUM_PR_T_TRN_POLICY_POL_WHERE_PR_PRM_POLICY_ID_POL_POL_POLICY_ID_AND_POL_POL_ISSUE_HOUR_END = "select DISTINCT(pr.prm_rate) from t_trn_premium pr, t_trn_policy pol where pr.prm_policy_id = pol.pol_policy_id and pol.pol_issue_hour = ";
	public static final String CONST_NUMBER = "number";
	public static final String CONST_AND_POL_ENDT_ID_END = " and pol_endt_id = ";
	public static final String CONST_ENDID = "endId";
	public static final String CONST_COMMENT = "comment";
	public static final String CONST_HIBERNATETEMPLATE = "hibernateTemplate";
	public static final String CONST_STATUS = "status";
	public static final String CONST_FROM_DUAL2 = " ) FROM DUAL";
	public static final String CONST_AND_POL_ISSUE_HOUR_END = " and pol_issue_hour = ";
	public static final String CONST_SELECT_PKG_ENDT_GEN_GET_PREV_ENDT_ID_END = "SELECT pkg_endt_gen.get_prev_endt_id( ";
	public static final String CONST_AND_POL_CLASS_CODE_END = " and pol_class_code = ";
	public static final String CONST_ENDTID = "endtId";
	public static final String CONST_VALSTARTDATE = "valStartDate";
	public static final String CONST_SELECT_PKG_ENDT_GEN_GET_BASE_SEC_POL_ID_END = "SELECT PKG_ENDT_GEN.get_base_sec_pol_id(";
	public static final String CONST_DOCCODE = "docCode";
	public static final String CONST_QUOTATIONNO = "quotationNo";
	public static final String CONST_T_TRN_POLICY_QUO = "t_trn_policy_quo";
	public static final String CONST_AND_PR_PRM_CL_CODE_END = " and pr.prm_cl_code = ";
	public static final String CONST_AND_POL_POL_POLICY_ID_END = " and pol.pol_policy_id = ";
	public static final String CONST_PREPAREDDT = "preparedDt";
	public static final String CONST_POLICYID = "policyId";
	public static final String CONST_VSDTIMESTAMP = "vsdTimeStamp";
	public static final String CONST_AND_PR_PRM_COV_CODE_END = " and pr.prm_cov_code = ";
	public static final String CONST_REASONCODE = "reasonCode";
	public static final String CONST_CLASSCODE = "classCode";
	public static final String CONST_SELECT_PKG_PAS_UTILS_GET_BASE_SEC_POL_ID_QUO_END = "SELECT PKG_PAS_UTILS.get_base_sec_pol_id_quo(";
	public static final String CONST_MODIFIEDDT = "modifiedDt";
	public static final String CONST_T_TRN_POLICY = "t_trn_policy";
	public static final String CONST_PO_VSD_DATE = "PO_VSD_DATE";
	public static final String CONST_TRAVEL_CLASS_CODE = "TRAVEL_CLASS_CODE";
	public static final String CONST_PREPAREDBY = "preparedBy";
	public static final String CONST_LINKINGID = "linkingId";
	public static final String CONST_POL_END_ID = "pol_end_id";
	public static final String CONST_ONE_OF_THE_PARAMETERS_OUT_OF_POLICYLINKINGID_END = "One of the parameters out of PolicyLinkingId: ";
	public static final String CONST_MODIFIEDBY = "modifiedBy";
	public static final String CONST_QUOTENO = "quoteNo";
	public static final String CONST_SELECT_PKG_PAS_UTILS_GET_PREV_ENDT_ID_QUO_END = "SELECT PKG_PAS_UTILS.get_prev_endt_id_quo( ";
	public static final String CONST_QUOTE_ACCEPT = "QUOTE_ACCEPT";
	public static final String CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_POLLINKINGID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END = "from TTrnPasReferralDetails where id.polLinkingId=? and id.secId=? and id.locationId=? and id.refField=? and refStatus =?";
	public static final String CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_REFPOLICYID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END = "from TTrnPasReferralDetails where id.refPolicyId=? and id.secId=? and id.locationId=? and id.refField=? and refStatus =?";
	public static final String CONST_RISK_GROUP_ID = "RISK_GROUP_ID";
	public static final String CONST_AND_POL_EXPIRY_DATE_END = " AND POL_EXPIRY_DATE > '";
	public static final String CONST_CMN_ERROR = "cmn.error";
	public static final String CONST_TARIFF = "TARIFF";
	public static final String CONST_SPECIAL_CODE = "SPECIAL_CODE";
	public static final String CONST_PO_ERR_TEXT = "PO_ERR_TEXT";
	public static final String CONST_DIRECTORATE = "DIRECTORATE";
	public static final String CONST_DD_MMM_YY_HH_MM_SS = "dd-MMM-yy HH:mm:ss";
	public static final String CONST_COMMONGENSVCBEAN_POL = "commonGenSvcBean_POL";
	public static final String CONST_ANA_TAR_CODE = "ANA_TAR_CODE";
	public static final String CONST_BASELOAD = "baseLoad";
	public static final String CONST_COMMONGENSVCBEAN = "commonGenSvcBean";
	public static final String CONST_BASESAVE = "baseSave";
	public static final String CONST_PC_CODE_EXCESS_LIST_ITEM = "PC_CODE_EXCESS_LIST_ITEM";
	public static final String CONST_RISK_CODE_END = " risk code ";
	public static final String CONST_COV_CODE_END = " cov code ";
	public static final String CONST_PRORATE_PREM_END = "PRORATE PREM ";
	public static final String CONST_SRC_OF_BUS_DIRECT = "SRC_OF_BUS_DIRECT";
	public static final String CONST_SEC_ID = "_SEC_ID";
	public static final String CONST_SELECT_FROM_T_TRN_TEMP_CON_EXC_WAR = "select * from T_TRN_TEMP_CON_EXC_WAR";
	public static final String CONST_SBS_POLICY_TYPE = "SBS_Policy_Type";
	public static final String CONST_SELECT_MAS_ID_PCCODE_FROM_TMASPOLICYCONDITION_MAS_WHERE_MAS_ID_PCSCHCODE_END = "(select mas.id.pcCode from TMasPolicyCondition mas where mas.id.pcSchCode = ?";
	public static final String CONST_SYSDATE = "SYSDATE";
	public static final String CONST_TO_DATE_END = ",to_date(\'";
	public static final String CONST_DD_MM_YYYY_END = "\',\'DD/MM/YYYY\'),";
	public static final String CONST_PRM_ENDT_ID_EQUALS = " and pr.prm_endt_id  = ";
	public static final String CONST_AND_CDM_CODE_IN = " AND Cdm_Code1       IN";
	public static final String CONST_AND_CDM_ENTITY_DESC_EQLS_TOCHAR = " AND Cdm_Entity_Desc  =TO_CHAR(tu.User_Id)";
	public static final String CONST_TRUE = "'true'   ";
	public static final String CONST_ELSE ="else  ";
	public static final String CONST_FALSE_QUERY = "'false'  ";
}