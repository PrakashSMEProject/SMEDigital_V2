package com.rsaame.pas.svc.constants;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.svc.utils.SvcUtils;

/**
 * All the service constants will be added here.
 * 
 * @author Jagmohan
 */
public class SvcConstants{
	public static final String APP_CONFIG_KEY_METHOD_AUTHORIZATION_ENABLED = "METHOD_AUTHORIZATION_ENABLED";
	public static final String APP_CONFIG_SERVICE_NOT_REQ = "NOT_REQUIRED";
	public static String DELIMITER = "-";
	public final static Date EXP_DATE = SvcUtils.getVED(); //new Date(new  Long("2524501800000")); //  2524507200000 - Dubai time 2524501800000 - Indian time the millisecond value for 31/12/2049 00:00:00:000, to create date instance
	public static String RATING_PAR_SPRINKLER_FACTOR = "Sprinkler Flag";
	public static String RATING_PAR_STORAGE_FACTOR = "Storage Exposure Flag";
	public static String RATING_PAR_ALARM_FACTOR = "Alarm Flag";
	public static String RATING_PAR_HAZARD_LEVEL_FACTOR = "Hazard level";
	public static String RATING_PAR_CONSTRUCT_TYPE_FACTOR = "Construction Type";
	public static String RATING_PAR_DEDUCTIBLE_FACTOR = "Deductible";
	public static String RATING_PAR_SI_FACTOR = "Building SI";
	public static String RATING_CONTENT_SI_FACTOR = "Contents SI";
	public static String RATING_CONTENT_CAT_FACTOR = "Content Cat";
	public static String RATING_BUILD_ID_FACTOR = "Building ID";
	public static String RATING_ITEM_SEQ_NO_FACTOR = "Item Seq Number";
	public static String RATING_BUILD_ITEM_CODE ="20001";
	public static String RATING_BUILD_CONTENT_ITEM_CODE ="20002";
	public static String RATING_PL_ITEM_CODE ="20031";
	public static String RATING_MONEY_ITEM_CODE ="20042";
	public static String RATING_WC_ITEM_CODE ="20038";
	public static String RATING_TRADE_GROUP_FACTOR="Trade Group";
	public static String RATING_PL_TENANT_FLG_FACTOR ="Tenant Flag";
	public static String RATING_PL_DEDUCTIBLE_FACTOR ="PL Deductible";
	public static String RATING_PL_LIB_LIMIT_FACTOR="PL Liability Limit";
	public static String RATING_PL_WORK_AWY_FLG_FACTOR="Workaway Flag";
	public static String RATING_PL_FOOD_DRINK_FLG_FACTOR="Food And Drink Flag";
	public static String RATING_MONEY_CAT_FACTOR ="Money Category";
	public static String RATING_MONEY_DEDUCTIBLE_FACTOR ="Money Deductible";
	public static String RATING_MONEY_SI_FACTOR="Money SI";
	public static String RATING_WC_DEDUCTIBLE_FACTOR="WC Deductible";
	public static String RATING_WC_NO_OF_EMP_FACTOR="WC No of Employees";
	public static String RATING_WC_PA_FLG_FACTOR="PA Flag";
	public static String RATING_WC_LIBLIMIT_FACTOR="WC Liability Limit";
	public static String RATING_WC_EMP_TYP_FACTOR="Employee Type";
	public static String RATING_WC_ANNUAL_WAGE_ROLL_FACTOR="Annual Wage roll";
	public static String RATING_EE5_BUS_TYPE_FACTOR = "EE5 Business Type";
	public static String RATING_EE10_BUS_TYPE_FACTOR = "EE10 Business Type";
	public static String RATING_PL_STUDENT_LIABILITY_FACTOR="Student Liability";
	
	
	//Adding constants for Phase 2a
	public static String RATING_BI_ITEM_CODE ="20131";
	public static String RATING_MB_ITEM_CODE ="20019";
	public static String RATING_EEQ_ITEM_CODE ="20021";
	public static String RATING_TB_ITEM_CODE ="20027";
	public static String RATING_GIT_ITEM_CODE ="20045";
	public static String RATING_GPA_ITEM_CODE ="20028";
	public static String RATING_DOS_ITEM_CODE ="20020";
	public static String RATING_FID_ITEM_CODE ="20024";
		
	public static String RATING_BI_INDEMNIY_PERIOD = "BI Indemnity period";
	public static String RATING_BI_INDEMPRD_DEFAULT = "6";
	public static String RATING_BI_AVG_PRM_PAR = "BI AVG PRM PAR";
	public static String RATING_BI_AVG_PRM_PAR_DEFAULT = "0.0075";
	public static String RATING_BI_DEDUCTIBLE = "BI Deductible";
	public static String RATING_BI_DEDUCTIBLE_DEFAULT = "3";
	public static String RATING_BI_SI = "BI SI";
	public static String RATING_BI_SI_DEFAULT = "0";
	
	public static String RATING_MB_SI = "MB SI";
	public static String RATING_MB_SI_DEFAULT = "0";
	public static String RATING_MB_RATE = "MB rate";
	public static String RATING_MB_RATE_DEFAULT = "";
	public static String RATING_MB_CATEGORY = "MB Cat";
	public static String RATING_MB_CATEGORY_DEFAULT = "1";
	
	public static String RATING_EE_SI = "EE SI";
	public static String RATING_EE_SI_DEFAULT = "0";
	public static String RATING_EE_CATEGORY = "EE Cat";
	public static String RATING_EE_CATEGORY_DEFAULT = "1";
	public static String RATING_EE_DEDUCTIBLE = "EE Deductible";
	public static String RATING_EE_DEDUCTIBLE_DEFAULT = "250";
	public static String RATING_EE_DEDUCTIBLE_BAHRAIN_DEFAULT = "25";
	public static String RATING_EE_ITEMS = "EE Items";
	public static String RATING_EE_ITEMS_DEFAULT = "1";
	
	public static String RATING_TB_CAT = "TravelB Cat";
	public static String RATING_TB_CAT_DEFAULT = "1";
	public static String RATING_TB_LIMIT_SI = "TB Limit SI";
	public static String RATING_TB_LIMIT_SI_DEFAULT = "0";
	public static String RATING_TB_NO_OF_PEOPLE = "TB No of People";
	public static String RATING_TB_NO_OF_PEOPLE_DEFAULT = "0";
	/*
	 * adding this change to include default no of people in travel baggage  ticket id 69851
	 */
	public static String RATING_TB_NO_OF_PEOPLE_DEFAULT_NEW = "1";
	public static String RATING_TB_DEDUCTIBLE = "TB Deductible";
	public static String RATING_TB_DEDUCTIBLE_DEFAULT = "500";
	public static String RATING_TB_DEDUCTIBLE_BAHRAIN_DEFAULT = "50";
	
	public static String RATING_GPA_TYPE_OF_EMP = "GPA Type of employee";
	public static String RATING_GPA_TYPE_OF_EMP_DEFAULT = "8";
	public static String RATING_GPA_CAPITAL_SI = "GPA Capital Sum insured";
	public static String RATING_GPA_CAPITAL_SI_DEFAULT = "0";
	
	public static String RATING_GPA_NO_OF_EMP = "GPA number of employees";  
	public static String RATING_GPA_NO_OF_EMP_DEFAULT = "1";
	
	public static String RATING_GIT_SINGLE_TRUCK_LIMIT = "GIT single truck limit";
	public static String RATING_GIT_SINGLE_TRUCK_LIMIT_DEFAULT = "50000";
	public static String RATING_GIT_COMMODITY_TYPE = "GIT Commodity type";
	public static String RATING_GIT_COMMODITY_TYPE_DEFAULT = "1";
	public static String RATING_GIT_DEDUCTIBLE = "GIT deductible";
	public static String RATING_GIT_DEDUCTIBLE_DEFAULT = "250";
	public static String RATING_GIT_DEDUCTIBLE_BAHRAIN_DEFAULT = "25";
	public static String RATING_GIT_EST_ANNUAL_TURNOVER = "Estimated annual turnover";
	public static String RATING_GIT_EST_ANNUAL_TURNOVER_DEFAULT = "0";
	
	public static String RATING_DOS_CAPITAL_SI = "DOS SI";
	public static String RATING_DOS_CAPITAL_SI_DEFAULT = "0";
	public static String RATING_DOS_CAT_TYPE = "DOS Cat";
	public static String RATING_DOS_CAT_TYPE_DEFAULT = "1";
	
	public static String RATING_FID_NO_OF_EMP ="FG No Employee";
	public static String RATING_FID_NO_OF_EMP_DEFAULT = "0";
	public static String RATING_FID_BASIS_OF_COVER ="FG Basis of Cover Type";
	public static String RATING_FID_BASIS_OF_COVER_DEFAULT = "1";
	public static String RATING_FID_DEDUCTIBLE ="FG Deductible";
	public static String RATING_FID_DEDUCTIBLE_DEFAULT = "1500";
	public static String RATING_FID_DEDUCTIBLE_BAHRAIN_DEFAULT = "150";
	public static String RATING_FID_TRADE_GROUP="FG Trade group";
	public static String RATING_FID_TRADE_GROUP_DEFAULT="0";
	public static String RATING_FID_AGGREGATE="FG Aggregate";
	public static String RATING_FID_AGGREGATE_DEFAULT= "0";
	
	public static String PAR_NAME = "Property All Risk";
	public static String PL_NAME = "Public Liability";
	public static String EE_NAME = "Electronic Equipments";
	public static String WC_NAME = "Workmens Compensation";
	public static String BI_NAME = "Business Interruption";
	public static String GPA_NAME = "Group Personal Accident";
	public static String FID_NAME = "Fidelity";
	public static String MONEY_NAME = "Money";
	public static String PL_BASIC_RISK_CODE="PL_RISK_CODE";
	public static String QUOTE_FLOW="Quote";
	public static String POL_FLOW="Policy";
	public static String COMMISSIONS="COMMISSIONS";
	public static final String DECIMAL_FORMAT = "0.00";
	public static final String LOOKUP_LEVEL1 = "ALL";
	public static final String LOOKUP_LEVEL2 = "ALL";
	public static final String WC_PL_LIMIT = "WC_PL_LIMIT";
	public static final String PL_LIMIT = "PL_LIMIT";
	public static final String WC_LIMIT = "WC_LIMIT";
	
	
	public static final String MAIL_TYPE_HTML = "HTML";
	public static final String MAIL_TYPE_PLAINTXT = "Plain Text";
	public static final String MAIL_PROCESS_EMAIL_QUOTE = "emailQuote";
	public static final String MAIL_PROCESS_CONVERT_TO_POLICY = "Convert_to_policy";
	public static final String MAIL_PRINT_RENEWAL_NOTICES="Print Renewal Notices";
	public static final String REMAINDER_SUBJECT_DELIMITER="###";
	
	public static final Integer POL_STATUS_ACTIVE = 1;
	public static final Integer POL_STATUS_PENDING = 6;
	public static final Integer POL_STATUS_DELETED = 4;
	public static final Long ZERO = 0l;
	
	/* ThreadLevelConstant keys. */
	public static final String TLC_KEY_ENDT_ID = "ENDT_ID";
	public static final String TLC_KEY_ENDT_NO = "ENDT_NO";
	public static final String TLC_KEY_VSD = "VSD";
	public static final String TLC_KEY_SYSDATE = "SYSDATE";
	public static final String TLC_KEY_DATA_HAS_CHANGED = "DATA_HAS_CHANGED";
	public static final String TLC_TOTAL_SI = "TLC_TOTAL_SI";
	public static final String TLC_KEY_PRM_TO_BE_CREATED = "PRM_TO_BE_CREATED";
	public static final String TLC_KEY_PRM_TO_BE_DELETED = "PRM_TO_BE_DELETED";
	
	public static final String ENDORSE_STATUS_NO="6";
	public static final String ENDORSE_STATUS_DEL_NO="4";
	public static final String ENDORSE_POL_DOC_CODE="3";
	public static final String STATUS_ACTIVE="Y";
	public static final String STATUS_INACTIVE="N";
	
	public static final String TASK_TRAN_TYPE_ENDORSEMENT="2";
	
	/* Table Ids */
	public static final String TABLE_ID_T_TRN_BUILDING = "T_TRN_BUILDING";
	public static final String TABLE_ID_T_TRN_POLICY = "T_TRN_POLICY";
	public static final String TABLE_ID_T_MAS_CASH_CUSTOMER = "T_MAS_CASH_CUSTOMER";
	public static final String TABLE_ID_T_TRN_PREMIUM = "T_TRN_PREMIUM";
	public static final String TABLE_ID_T_TRN_PREMIUM_AGGREGATE = "T_TRN_PREMIUM_AGGREGATE";
	public static final String TABLE_ID_T_TRN_CONTENT = "T_TRN_CONTENT_CNTID";
	public static final String TABLE_ID_T_TRN_UW_QUESTIONS = "T_TRN_UW_QUESTIONS";
	public static final String TABLE_ID_T_TRN_WCTPL_PREMISE = "T_TRN_WCTPL_PREMISE";
	public static final String TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID = "T_TRN_WCTPL_PREMISE_BLDID";
	public static final String TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON = "T_TRN_WCTPL_UNNAMED_PERSON";
	public static final String TABLE_ID_T_TRN_GACC_BUILDING = "T_TRN_GACC_BUILDING";
	public static final String TABLE_ID_T_TRN_GACC_CASH = "T_TRN_GACC_CASH";
	public static final String TABLE_ID_T_TRN_GACC_CASH_CODES = "T_TRN_GACC_CASH_CODES";
	public static final String TABLE_ID_T_TRN_GACC_CASH_DETAILS = "T_TRN_GACC_CASH_DETAILS";
	public static final String TABLE_ID_T_TRN_SECTION_LOCATION = "T_TRN_SECTION_LOCATION";
	public static final String TABLE_ID_T_TRN_SECTION_LOCATION_ENDT = "T_TRN_SECTION_LOCATION_ENDT";
	public static final String TABLE_ID_T_TRN_UW_QUESTIONS_CREATE = "T_TRN_UW_QUESTIONS_CREATE";
	public static final String TABLE_ID_T_TRN_SECTION_DETAILS_CREATE = "T_TRN_SECTION_DETAILS_CREATE";
	public static final String TABLE_ID_T_TRN_POLICY_FOR_GI="T_TRN_POLICY_FOR_GI";
	public static final String TABLE_ID_T_TRN_NON_STD_TXT="T_TRN_NON_STD_TEXT";
	public static final String TABLE_ID_T_TRN_GACC_PERSON = "T_TRN_GACC_PERSON";
	public static final String TABLE_ID_T_TRN_GACC_PERSON_GPA_LOAD = "T_TRN_GACC_PERSON_GPA_LOAD";
	public static final String TABLE_ID_T_TRN_GACC_PERSON_LOAD = "T_TRN_GACC_PERSON_LOAD";
	public static final String TABLE_ID_T_TRN_GACC_UNNAMED_PERSON = "T_TRN_GACC_UNNAMED_PERSON";
	public static final String TABLE_ID_T_TRN_GACC_UNNAMED_PERSON_LOAD = "T_TRN_GACC_UNNAMED_PERSON_LOAD";
	public static final String TABLE_ID_T_TRN_MARINE_TRANSIT = "T_TRN_MARINE_TRANSIT";
	public static final String TABLE_ID_T_TRN_MARINE_HEADER = "T_TRN_MARINE_HEADER";
	public static final String TABLE_ID_T_TRN_MARINE_DETAIL = "T_TRN_MARINE_DETAIL";
	public static final String TABLE_ID_T_TRN_VEHICLE = "T_TRN_VEHICLE";
	public static final String TABLE_ID_T_TRN_VEHICLE_LOAD = "T_TRN_VEHICLE_LOAD";
	public static final String TABLE_ID_T_TRN_WCTPL_PERSON = "T_TRN_WCTPL_PERSON";
	
	/* Start: Configured section ids*/
	public static final int SECTION_ID_GEN_INFO = Integer.valueOf( Utils.getSingleValueAppConfig( "GENERAL_SECTION", "0" ) );
	public static final int SECTION_ID_PAR = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	public static final int SECTION_ID_PL = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	public static final int SECTION_ID_WC = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" ) );
	public static final int SECTION_ID_MONEY = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_SECTION" ) );
	public static final int SECTION_ID_EE = Integer.valueOf( Utils.getSingleValueAppConfig( "EE_SECTION" ) );
	public static final int SECTION_ID_MB = Integer.valueOf( Utils.getSingleValueAppConfig( "MB_SECTION" ) );
	public static final int SECTION_ID_FIDELITY = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION" ) );
	public static final int SECTION_ID_PREMIUM = Integer.valueOf( Utils.getSingleValueAppConfig( "PREMIUM_PAGE", "999" ) );
	public static final int SECTION_ID_TB = Integer.valueOf( Utils.getSingleValueAppConfig( "TB_SECTION") );
	public static final int SECTION_ID_GROUP_PERSONAL_ACCIDENT = Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_SECTION") );
	public static final int SECTION_ID_GIT = Integer.valueOf( Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_SECTION") );
	public static final int SECTION_ID_GPA = Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_SECTION") );
	public static final int SECTION_ID_DOS = Integer.valueOf( Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_SECTION") );
	public static final int SECTION_ID_FID = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION") );
	public static final int SECTION_ID_MOTOR = Integer.valueOf( Utils.getSingleValueAppConfig( "MOTORFLEET_SECTION" ) );
	
	/* End: Configured section ids*/
	
	/* Start: Configured class ids*/
	public static final int CLASS_ID_PL = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_CLASS" ) );
	public static final int CLASS_ID_WC = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_CLASS" ) );
	public static final int CLASS_ID_MB = Integer.valueOf( Utils.getSingleValueAppConfig( "MB_CLASS" ) );
	public static final int CLASS_ID_MONEY = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_CLASS" ) );
	public static final int CLASS_ID_PAR = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_CLASS", "2" ) );
	public static final int CLASS_ID_EE = Integer.valueOf( Utils.getSingleValueAppConfig( "EE_CLASS") );
	public static final int CLASS_ID_GIT = Integer.valueOf( Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_CLASS") );
	public static final int CLASS_ID_GPA = Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_CLASS") );
	public static final int CLASS_ID_DOS = Integer.valueOf( Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_CLASS") );
	public static final int CLASS_ID_FID = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_CLASS") );
	public static final int CLASS_ID_MOTOR = Integer.valueOf( Utils.getSingleValueAppConfig( "MOTORFLEET_CLASS" ) );
	/* End: Configured class ids*/
	
	/* Table query type - HBM or SQL */
	public static final boolean IS_TABLE_QUERY_HBM = false;
	public static final boolean IS_TABLE_QUERY_SQL = true;
	public static final String VALIDITY_EXP_DATE =  "@Validity@Expiry@Date= ?";
	
	/* handleTableRecord input constants */
	public static final POJO[] NO_DEP_POJO = null;
	public static final BaseVO[] NO_DEP_VO = null;
	

	public static final int APP_BASE_COVER_CODE=1;
	public static final int APP_BASIC_CONTENT_CODE=999;
	public static final int APP_ADDITIONAL_COVER_OPTED=1;
	public static final int APP_ADDITIONAL_COVER_NOT_OPTED=0;
	public static Integer FID_BASIC_RISK_CODE_FOR_AGGREGATE =20024;
	
	public static final String PRM_TO_BE_CREATED = "PRM_TO_BE_CREATED";
	public static final String PRM_TO_BE_DELETED = "PRM_TO_BE_DELETED";
	public static final String TLC_KEY_BASIC_RISK_ID = "BASIC_RISK_ID";
	public static final BigDecimal P_IS_NEW_TRUE = new BigDecimal( 1 );
	public static final BigDecimal P_IS_NEW_FALSE = new BigDecimal( 0 );
	public static final String POLICY_STATUS_CODE = "POLICY_STATUS_CODE";
	
	public static final String OLD_ANNUALIZED_PREMIUM = "p_old_annualized_premium";
	public static final String NEW_ANNUALIZED_PREMIUM = "p_new_annualized_premium";
	public static final String ANNUALIZED_LOC_PREMIUM = "P_ANNUALIZED_PREMIUM";
	public static final byte DEL_SEC_LOC_STATUS = Byte.valueOf( Utils.getSingleValueAppConfig("DEL_SEC_LOC_STATUS") );
	public static final byte PRM_SITYPE_CODE_BASE_COVER = 1;
	public static final byte PRM_SITYPE_CODE_NON_BASE_COVER = 2;
	public static final byte PRM_FN_CODE = 7;
	public static final int zeroVal = 0;
	public static final String FILE_UPLOAD_ROOT = "FILE_UPLOAD_ROOT";
	public static final String FILE_UPLOAD_TRADE_LICENCE_FOLDER = "FILE_UPLOAD_TRADE_LICENCE_FOLDER";
	public static final String TRADE_LICENCE_FILE_UPLOAD_ALLOWED_EXTNS = "TRADE_LICENCE_FILE_UPLOAD_ALLOWED_EXTNS";
	public static final String TRADE_LIC = "TRADE_LIC_";
	public static final int NO_OF_DAYS_LEAP_YEAR = 366;
	public static final int NO_OF_DAYS_YEAR = 365;
	public static final String FIRST_ENDT = "0";
	public static final String QUOTE_REFERRED = "QUOTE_REFERRED";
	public static final String QUOTE_SOFT_STOP = "QUOTE_SOFT_STOP";
	public static final String QUOTE_PENDING = "QUOTE_PENDING";
	public static final String QUOTE_EXPIRED = "QUOTE_EXPIRED";
	public static final String CONV_TO_POL = "CONV_TO_POL";
	public static final String QUOTE_ACTIVE = "QUOTE_ACTIVE";
	public static final String POLICY_CANCELLED = "POLICY_CANCELLED";
	
	//Special Covers details for premium table
	public final static Short SC_PRM_CT_CODE = 0;
	public final static Double SC_PRM_SUM_INSURED = 0d;
	public final static Byte SC_PRM_SI_TYPE = 4;
	public final static Byte SC_PRM_SI_CURR = 1;
	public final static Byte SC_PRM_RATE_TYPE = 1;
	public final static Double SC_PRM_OLD_PRM = 0d;
	/*
	 * FIX: As part of backend defect, RI_RSK_CODE should be 202 on 22-JUL-2012
	 * public final static Integer SC_PRM_RI_RSK_CODE = 201;
	 */
	public final static Integer SC_PRM_RI_RSK_CODE = 202;
	public final static Short SC_PRM_FN_CODE = 0;
	public final static Short SC_PRM_FN_CODE_FLAT_AMT = 2;
	public final static Short SC_PRM_COVER_GOVT_TAX = 101;
	public final static Short SC_PRM_COVER_POLICY_FEE = 100;
	public final static Short SC_PRM_COVER_SPECIAL_PAY = 101;
	public final static Short SC_PRM_COVER_DISCOUNT = 51;
	public final static Short SC_PRM_COVER_LOADING = 20;
	public final static Short SC_PRM_MIN_PREM_LOADING = 40;
	public static final String HAS_PREMIUM_CHNAGED = "HAS_PREMIUM_CHNAGED";
	
	public static final String PAR_RI_RSK_CODE = "PAR_RI_RSK_CODE";
	public static final String PL_RI_RSK_CODE = "PL_RI_RSK_CODE";
	public static final String WC_RI_RSK_CODE = "WC_RI_RSK_CODE";
	public static final String OLD_REC = "OLD_REC";
	public static final String NEW_REC = "NEW_REC";

	public static final Integer DUBAI = 20;
	
	public static final int WC_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CODE" ) );
	public static final Integer POL_STATUS_ACCEPT = Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_ACCEPT" ) );
	public static final Integer POL_STATUS_REFERRED = Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_REFERRED" ) );
	public static final Integer POL_STATUS_DECLINED = Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_DECLINED" ) );
	public static final Integer POL_STATUS_REJECT = Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_REJECT" ) );
	public static final Integer POLICY_TERM = Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_TERM" ) );
	public static final String DEFAULT_CURRENCY_FORMAT = Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY_FORMAT" );
	public static final int EXCLUDE_CURRENT_DAY = -1;
	public static final Integer APP_PRM_RI_LOC_CODE=Integer.valueOf(Utils.getSingleValueAppConfig( "APP_PRM_RI_LOC_CODE" ));

	public static final Integer PAR_CNT_PORTABLE_EQUIPMENT_RSKTYPE=Integer.valueOf(Utils.getSingleValueAppConfig( "PAR_CNT_PORTABLE_EQUIPMENT_RSKTYPE" ));
	public static final String PAR_CNT_DEFAULT_HAZARD_LEVEL_PORTABLE_EQPMT=Utils.getSingleValueAppConfig( "PAR_CNT_DEFAULT_HAZARD_LEVEL_PORTABLE_EQUIPMENT" );

	public static final String PAR_BLD_GEO_CD_LKP_CATEGORY = Utils.getSingleValueAppConfig( "PAR_BLD_GEO_CD_LKP_CATEGORY" );
	
	public static final String PPP_BUSSTYPE_WC_THREADVAR = "PPP_BUSTYPE_WC";	
	public static final String WC_EMP_TYPE_LKP_CATEGORY = "WC_EMPTYPE";
	public static final String SC_PRM_RATE_TYPE_DISC = "-1";
	public static final String DEFAULT_POL_VALIDITY_EXPIRY_DATE = "DEFAULT_POL_VALIDITY_EXPIRY_DATE";
	public static final Double MIN_PERCENTAGE = -100.00;
	public static final Double MAX_PERCENTAGE = 100.00;
	
	
	public static final int VSD_INCREMENT_SEC = Integer.valueOf( Utils.getSingleValueAppConfig( "VSD_INCREMENT_SEC" ) );
	//constants for BI
	public static final int SECTION_ID_BI = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_SECTION" ) );
	public static final String TABLE_ID_T_TRN_CONSEQUENTIAL_LOSS = "T_TRN_CONSEQUENTIAL_LOSS";
	public static final String TABLE_ID_T_TRN_COL_WORK_SHEET= "T_TRN_COL_WORK_SHEET";
	public static final String BI_NULL_VALUE= null;
	public static final String BI_RENT_RECEIVABLE= Utils.getSingleValueAppConfig( com.Constant.CONST_BI_RR_LABEL );
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7 Adventnet Id:103286
	//public static final String BI_ANNUAL_RENT= Utils.getSingleValueAppConfig( "BI_ANNUAL_RENT_LABEL" );
	//public static final String BI_ESTIMATED_GROSS_INCOME= "Estimated gross income of your business during the next 12  months";
	//public static final String BI_INCR_COST_WORKING_LIMIT= "Increased cost of working limit";
	//public static final String BI_PRE_RENT_RECEIVABLES= "Rent receivables";
	public static final String BI_INDEMNITY_PERIOD =  Utils.getSingleValueAppConfig( "BI_INDPER_LABEL" );
	public static final String BI_ESTIMATED_GROSS_INCOME =  Utils.getSingleValueAppConfig( "BI_EGI_LABEL" );
	public static final String BI_INCR_COST_WORKING_LIMIT =  Utils.getSingleValueAppConfig( "BI_ICWL_LABEL" );
	public static final String BI_PRE_RENT_RECEIVABLES =  Utils.getSingleValueAppConfig( com.Constant.CONST_BI_RR_LABEL );
	public static final String BI_PRE_ANNUAL_RENT =  Utils.getSingleValueAppConfig( com.Constant.CONST_BI_RR_LABEL );
	
	public static final String GIT_SERIAL_NO = "Detail_Table_SerialNo";
	public static final String GOVT_TAX = "GOVTTAX";
	public static final String RULE_DEPLOYED_LOC = "RULE_DEPLOYED_LOC";
	public static final String RATE_DEPLOYED_LOC = "RATE_DEPLOYED_LOC";
	//Printer Configuration
	public static String PRINTER_NAME= Utils.getSingleValueAppConfig( "PRINTER_NAME" );;
	
	public static final String RULES_ALL_SECTIONS = "RULES_ALL_SECTIONS";
	public static final String CALL_RULES_FOR_ALL_SEC_LOC = "CALL_RULES_FOR_ALL_SEC_LOC";
	public static final String TRADE_LICENSE_CHECK = "TRADE_LICENSE_CHECK";
	public static final String ISUWRULESREQUIRED = "ISUWRULESREQUIRED";
	public static final String MIN_PRM_CHECK_REQ = "MIN_PRM_CHECK_REQ";
	public static final String MIN_PREMIUM = "MIN_PREMIUM";
	
	public static final String COMMONDATA = "COMMONDATA";
	public static final String TABLE_NAME = "TABLE_NAME";
	public static final String TABLE_NAME_ID = "TABLE_NAME_ID";
	public static final String TLC_KEY_POLICY_ID = "TLC_KEY_POLICY_ID";
	public static final String NON_VERSION_STATUS = "NON_VERSION_STATUS";
	public static final String VERSION_STATUS = "VERSION_STATUS";
	public static final String SEQ_POLICY_ID = "SEQ_POLICY_ID";
	public static final String SEQ_SBS_POL_NO = "SEQ_SBS_POL_NO";
	public static final String SEQ_QUOTATION_ID = "SEQ_QUOTATION_ID";
	public static final String SEQ_QUO_ENDORSEMENT_ID = "SEQ_QUO_ENDORSEMENT_ID";
	public static final String SEQ_ENDORSEMENT_ID = "SEQ_ENDORSEMENT_ID";
	public static final String SEQ_SUBMIT_CLAIM_ID = "SEQ_SUBMIT_CLAIM_ID";
	public static final String POL_SAVE_CASE = "POL_SAVE_CASE";
	public static final String POLDATA = "POLDATA";
	public static final String PREMIUM_INFO = "PREMIUM_INFO";
	public static final String TABLE_DATA = "TABLE_DATA";
	public static final String TLC_KEY_DOC_ID = "TLC_KEY_DOC_ID";
	public static String QUOTE_DOC_ID= Utils.getSingleValueAppConfig( "QUOTE_DOC_ID" );
	public static String ENDT_DOC_ID= Utils.getSingleValueAppConfig( "ENDT_DOC_ID" );
	public static final String T_TRN_POLICY = "T_TRN_POLICY";
	public static final String SAVE_CASH_CUST_DATA = "SAVE_CASH_CUST_DATA";
	public static final String T_TRN_NON_STD_TXT = "T_TRN_NON_STD_TXT";
	public static final String VO = "VO_";
	public static final String Mapper = "Mapper_";
	
	
	//constant value read from property file for T_TRN_GACC_PERSON table
	public static final String GPR_RSK_CODE_TRAVEL = "GPR_RSK_CODE_TRAVEL";
	public static final String GPR_RC_CODE_TRAVEL = "GPR_RC_CODE_TRAVEL";
	public static final String GPR_RI_RSK_CODE_TRAVEL = "GPR_RI_RSK_CODE_TRAVEL";
	public static final String GPR_BASIC_RSK_CODE_TRAVEL = "GPR_BASIC_RSK_CODE_TRAVEL";
	
	//Table name constants -- passed to BaseSave/LoadOperation
	public static final String T_TRN_GACC_PERSON = "GACC_PERSON";
	public static final String T_TRN_PREMIUM = "T_TRN_PREMIUM_QUO";
	public static final String SAVE_GEN_INFO = "saveGeneralInfo";
	public static final String SAVE_INSURED = "saveOrUpdateTmasInsured";
	public static final String INSUREDID_SEQ_SBS = "SEQ_INSURED_ID";
	public static final String SBS_Policy_Type = "SBS_Policy_Type";
	public static final String SEQ_QUOTE_NO = "SEQ_QUOTATION_NO";
	public static final String TLC_LOB = "LOB";
	public static final String CLASS_CODE = "_CLASS_CODE";
	public static final String POLICY_TYPE = "_POLICY_TYPE";
	public static final String T_TRN_BUILDING_QUO = "T_TRN_BUILDING_QUO";
	public static final String LOAD_GEN_INFO = "loadGenInfo";
	public static final String SAVE_GACC_PERSON = "travelSaveService";
	public static final String LOAD_GACC_PERSON = "travelLoadService";
	public static final String SAVE_POLICY_DATA="savePolicyData";
	public static final Integer DIRECT_BROKER_CODE = 900;
	public static final Integer POL_ISSUE_HOUR = 3;
	
	//method names in HomeInsuranceSaveService
	public static final String SAVE_HOME_INSURANCE = "saveHomeInsurance";
	public static final String APPROVE_QUO_HOME_INSURANCE = "homeInsuranceApproveQuo";
	public static final String LOAD_HOME_INSURANCE = "homeLoadService";
	public static final String  GET_PROMO_CODES = "getPromotionalCodes";
	public static final String SAVE_HOME_RISK_COVER_DETAILS = "saveHomeRiskCoverDetails";
	public static final String SAVE_HOME_INSURED_DETAILS = "saveHomeInsuredDetails";
	public static final String LOAD_HOME_INSURANCE_DETAILS = "loadHomeInsuranceDetails";
	public static final int DEFAULT_COVER_CODE = 1;
	public static final int DEFAULT_COVER_RSK_CAT= 1;
	public static final int CONTENT_RSK_TYPE_CODE = 31;
	public static final String T_TRN_CONTENT = "T_TRN_CONTENT_QUO";
	public static final String SAVE_CONTENTS = "saveContents";
	public static final String SAVE_PREMIUM = "saveOrUpdateTtrnPrmTable";	
	
	//Premium Common SVC constants
	public static final String TABLE_ID_T_MAS_CASH_CUSTOMER_QUO = "T_MAS_CASH_CUSTOMER_QUO";
	public static final String TABLE_ID_T_TRN_POLICY_QUO = "T_TRN_POLICY_QUO";
	public static final String TABLE_ID_T_TRN_PREMIUM_QUO = "T_TRN_PREMIUM_QUO";
	public static final String LOAD_PREMIUM = "loadPremiumDetails";
	
	// Added by Pallav Start
	public static final String SAVE_UW_QUES_ANS = "saveUWQuestionsAns";
	public static final String TABLE_ID_T_TRN_UW_QUESTIONS_QUO = "T_TRN_UW_QUESTIONS_QUO";
	// Added by Pallav End
	public static final String USER = "USER";
	public static final String POLICY_ID = "POLICY_ID";
	public static final String ENDT_ID = "ENDT_ID";
	public static final String SCHEME = "SCHEME";
	public static final Integer TRAVEL_POLICY_TYPE_FOR_LOOKUP = Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_SHORT_TERM_POLICY_TYPE" ) );
	public static final String TARIFF = "TARIFF";
	// Risk type & Cover codes for Home details
	public static final short DEFAULT_HOME_COVER_CODE = 1;
	public static final short LOD_COVER_CODE = 2;
	public static final short EL_COVER_CODE = 3;
	public static final short TLL_COVER_CODE = 4;
	public static final Integer BUILDING_RISK_TYPE_CODE = Integer.valueOf( 1 );
	public static final Integer CONTENT_RISK_TYPE_CODE = Integer.valueOf( 31 );
	public static final Integer PP_RSK_TYPE_CODE = Integer.valueOf(32);
	public static final Integer CONTENT_SUB_RISK_CATEGORY = Integer.valueOf( 2 );
	public static final Integer CONTENT_SUB_RISK_CATEGORY_THREE = Integer.valueOf( 3 );
	public static final Integer PP_SUB_RISK_CATEGORY = Integer.valueOf( 2 );
	public static final Integer DEFAULT_RISK_CATEGORY = Integer.valueOf( 0 );
	public static final Integer CONTENT_MAIN_RISK_CATEGORY = Integer.valueOf( 1 );
	public static final Integer PP_MAIN_RISK_CATEGORY = Integer.valueOf( 1 );
	public static final String RATING_SERVICE_GET_RATE = "getRates";
	public static final String CLIENT_ID = "AIC";
	public static final String SHORT_TRAVEL_POL_TYPE = Utils.getSingleValueAppConfig( "SHORT_TRAVEL_POL_TYPE" );
	public static final String LONG_TRAVEL_POL_TYPE = Utils.getSingleValueAppConfig( "LONG_TRAVEL_POL_TYPE" );
	public static final String HOME_POL_TYPE = Utils.getSingleValueAppConfig( "HOME_POL_TYPE" );
	public static final String TRAVEL_POL_TYPE = Utils.getSingleValueAppConfig( "TRAVEL_POL_TYPE" );
	public static final String MOTOR_POL_TYPE = Utils.getSingleValueAppConfig( "MOTOR_POL_TYPE" );
	public static final String loadData = "loadData";
	public static final Short CCG_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "CCG_CODE" ) );
	
	//SMS Master Constants Start	
	public static final String SMS_AUTO = "A";
	public static final String SMS_MANUAL = "M";
	public static final String AUTO_MODE = "Auto";
	public static final String MANUAL_MODE = "Manual";
	public static final String SMS_LANGUAGE = "E";
	
	public static final String SMS_STATUS_ACTIVE_CODE = "1";
	public static final String SMS_STATUS_INACTIVE_CODE = "0";
	public static final String SMS_STATUS_ACTIVE = "Active";
	public static final String SMS_STATUS_INACTIVE = "Inactive";
	////SMS Master Constants End
	
	public static final Integer TERM = Integer.valueOf( Utils.getSingleValueAppConfig( "TERM" ) );
	public static final String COMPANY = Utils.getSingleValueAppConfig( "COMPANY" );
	public static final String ADULT_FLAG = Utils.getSingleValueAppConfig( "ADULT_FLAG" );
	public static final String SENIOR_ADULT_FLAG = Utils.getSingleValueAppConfig( "SENIOR_ADULT_FLAG" );
	public static final String CHILD_FLAG = Utils.getSingleValueAppConfig( "CHILD_FLAG" );
	public static final Integer CHILD_AGE = Integer.valueOf( Utils.getSingleValueAppConfig( "CHILD_AGE" ) );
	public static final Integer SENIOR_ADULT_AGE = Integer.valueOf( Utils.getSingleValueAppConfig( "SENIOR_ADULT_AGE" ) );
	public static final Byte RELATION_OTHER = Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_OTHER" ) );
	public static final Byte RELATION_SELF = Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_SELF" ) );
	public static final Byte RELATION_MOTHER = Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_MOTHER" ) );
	public static final Byte RELATION_FATHER = Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_FATHER" ) );
	public static final Byte RELATION_SPOUSE = Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_SPOUSE" ) );
	public static final Byte RELATION_CHILD = Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_CHILD" ) );
	public static final String[] FAMILY_RELATION = Utils.getMultiValueAppConfig( "FAMILY_RELATION" );
	public static final String[] SPOUSE_RELATION = Utils.getMultiValueAppConfig( "SPOUSE_RELATION" );
	public static final String FAMILY_FLAG = Utils.getSingleValueAppConfig( "FAMILY_FLAG" );
	public static final String SPOUSE_FLAG = Utils.getSingleValueAppConfig( "SPOUSE_FLAG" );
	public static final String NONE_FLAG = Utils.getSingleValueAppConfig( "NONE_FLAG" );
	public static final String GROUP_FLAG = Utils.getSingleValueAppConfig( "GROUP_FLAG" );
	public static final Integer GROUP_TRAVELLER = Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_TRAVELLER" ) );
	public static final String WORLDWIDE = "Worldwide including USA and Canada";
	public static final String WORLDWIDE_EX_US_CAN = "Worldwide excluding USA and Canada";
	public static final String WORLDWIDE_EX_US_CAN_TRAVEL_TYPE = "1";
	public static final String WORLDWIDE_TRAVEL_TYPE = "2";
	public static final Short WINTER_SPORT_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig( "WINTER_SPORT_COVER_CODE" ));
	public static final Short GOLF_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig( "GOLF_COVER_CODE" ));
	public static final Short MEDICAL_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig( "MEDICAL_COVER_CODE" ));
	public static final Short BAGG_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig( "BAGG_COVER_CODE" ));
	public static final Short PERSONAL_ACCIDENT_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig( "PA_COVER_CODE" ));
	public static final String COVER_VALUE = "Y";
	public static final String COVER_VALUE_NEGATIVE = "N";
	public static final Integer TRAVEL_TARIFF_CODE_RATING = Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_TARIFF_CODE_RATING" ) );
	public static final String SEQ_HOME_QUOTATION_NO = "SEQ_HOME_QUO_NO";
	public static final String SEQ_TRAVEL_QUOTATION_NO = "SEQ_TRAVEL_QUO_NO";
	public static final String SEQ_HOME_POLICY_NO = "SEQ_HOME_POL_QUO_NO";
	public static final String SEQ_TRAVEL_POLICY_NO = "SEQ_TRAVEL_POL_QUO_NO";
	public static final String SPL_COV_CODE =  "1" ;
	public static final Short CASH_OC_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "CASH_OC_CODE" ) );
	public static final String TCL_KEY_HAS_DATA_CHANGED = "TCL_KEY_HAS_DATA_CHANGED";
	public static final String TCL_KEY_ENDT_TABLE_LIST = "TCL_KEY_ENDT_TABLE_LIST";
	public static final String SAVE_PARTNER_MANAGEMENT = "savePartnerManagement";
	public static final String SAVE_TRAVEL_PREMIUM = "saveOrUpdateTravelPrmTable";
	public static final String APPROVE_TRAVEL_QUOTE = "approveTravelQuote";
	public static final String GET_PACKAGE_PREMIUM = "getPackagePremium";
	public static final Integer DIST_CHANNEL_BROKER = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_BROKER" ) );
	public static final Integer DIST_CHANNEL_DIRECT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT" ) );
	public static final Integer DIST_CHANNEL_DIRECT_WEB = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT_WEB" ) );
	public static final Integer DIST_CHANNEL_DIRECT_CALL_CENTER = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT_CALL_CENTER" ) );
	public static final Integer DIST_CHANNEL_AGENT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_AGENT" ) );
	public static final Integer DIST_CHANNEL_AFFINITY_DIRECT_AGENT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_AFFINITY_DIRECT_AGENT" ) );
	public static final Integer DIST_CHANNEL_AFFINITY_AGENT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_AFFINITY_AGENT" ) );
	public static final String APPROVED_IND_NO = "N";
	public static short oneVal = 1;
	public static String AS_AGREED_PAYMENT_FREQUENCY = "1";
	public static final String TLC_KEY_POLICY_REC = "TLC_KEY_POLICY_REC";
	public static final int RT_CODE_HOME_CONTENTS =  Integer.valueOf( Utils.getSingleValueAppConfig( "RT_CODE_HOME_CONTENTS" ) ).intValue();
	public static final String TLC_PRM_RATE = "TLC_PRM_RATE";
	public static final String TLC_PRM_SC_CHANGED = "TLC_PRM_SC_CHANGED";
	public static String TLC_KEY_TABLE_DATA_CHANGED = "TLC_KEY_TABLE_DATA_CHANGED";
	public static String SC_PRM_COVER_PROMO_DISC = Utils.getSingleValueAppConfig( "SPECIAL_COVER_CODES_PROMO_DISC" );
	public static String SPECIAL_COVER_MIN_PRM = Utils.getSingleValueAppConfig( "SPECIAL_COVER_MIN_PRM" );
	public static String SAVE_TRAVEL_INSURANCE = "saveTravelInsurance";
	public static final String TCL_POL_STATUS = "TCL_POL_STATUS";
	//Sms
	public static final String SMS_VECTRAMIND_URL = "SMS_VECTRAMIND_URL";
	public static final BigDecimal SPECIAL_CODE = BigDecimal.valueOf( Long.valueOf( Utils.getSingleValueAppConfig( "SPECIAL_CODE" ) ) );
	public static final double CONTENTS_SI_COMPARE_VAL = Double.valueOf( Utils.getSingleValueAppConfig( "CONTENTS_SI_COMPARE_VAL" ) );
	public static final double CONTENT_SI_MULTIPLIER = Double.valueOf( Utils.getSingleValueAppConfig( "CONTENT_SI_MULTIPLIER" ) );
	public static final Integer GARGASH_FIXED_SI_SCHEME = Integer.valueOf( Utils.getSingleValueAppConfig( "GARGASH_FIXED_SI_SCHEME" ) );
	public static final Integer EMIRATES_HOME_TARIFF = Integer.valueOf( Utils.getSingleValueAppConfig( "EMIRATES_HOME_TARIFF" ) );
	public static final Integer GARGASH_TARIFF = Integer.valueOf( Utils.getSingleValueAppConfig( "GARGASH_TARIFF" ) );
	public static final Integer STANDARD_TARIFF = Integer.valueOf( Utils.getSingleValueAppConfig( "STANDARD_TARIFF" ) );
	public static final Integer ANA_HOME_TARIFF = Integer.valueOf( Utils.getSingleValueAppConfig( "ANA_HOME_TARIFF" ) );
	public static final double PERSONAL_SI_COMPARE_VAL = Double.valueOf( Utils.getSingleValueAppConfig( "PERSONAL_SI_COMPARE_VAL" ) );
	public static final double PERSONAL_SI_MULTIPLIER = Double.valueOf( Utils.getSingleValueAppConfig( "PERSONAL_SI_MULTIPLIER" ) );
	
	//Renewal
	public static final String SAVE_RENEWAL_REFERRAL = "saveRenewalReferral";
	public static final Integer TRAVEL_POLICY = 888;
	public static final String STATUS_ON = "on";
	
	//CCG Code for SBS
	public static final Integer CCG_CODE_SBS = 11;
	
	public static final Integer HOME_BUILDING_RISK_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_BUILDING_RISK_TYPE" ) );
	public static final Integer HOME_CONTENT_RISK_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_CONTENT_RISK_TYPE" ) );
	public static final Integer HOME_PERSONAL_POS_RISK_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_PERSONAL_POS_RISK_TYPE" ) );
	
	public static final Integer RENEWAL_POL_DOC_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "RENEWAL_POL_DOC_CODE" ) );
	
	public static final short UW_GENERAL_QUESTION_CLAIM = Short.valueOf( Utils.getSingleValueAppConfig( "UW_GENERAL_QUESTION_CLAIM" ) );
	public static final short UW_GENERAL_QUESTION_HOUSEHOLD = Short.valueOf( Utils.getSingleValueAppConfig( "UW_GENERAL_QUESTION_HOUSEHOLD" ) );
	public static final short UW_GENERAL_QUESTION_CONCRETE = Short.valueOf( Utils.getSingleValueAppConfig( "UW_GENERAL_QUESTION_CONCRETE" ) );
	public static final short UW_GENERAL_QUESTION_TERMS = Short.valueOf( Utils.getSingleValueAppConfig( "UW_GENERAL_QUESTION_TERMS" ) );
	public static final short UW_GENERAL_QUESTION_STUDENT_LIABILITY = Short.valueOf( Utils.getSingleValueAppConfig( "UW_GENERAL_QUESTION_STUDENT_LIABILITY" ) );
	public static final short UW_GENERAL_QUESTION_RCC_CONSTRUCTION = Short.valueOf( Utils.getSingleValueAppConfig( "UW_GENERAL_QUESTION_RCC_CONSTRUCTION" ) );
	
	// FOR B2C ONLY
	public static final int B2C_DEFAULT_DIST_CHANNEL = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_DEFAULT_DIST_CHANNEL"));
	public static final String APPROVED_IND_YES = "Y";
	
	public static final int STANDARD_CLAUSES= 493;
	public static final int CREDIT_MODE = 490;
	public static final int CLAIMS_CHECK = 491;
	public static final int CANCEL_DISCOUNT_CHECK = 492;
	public static final int AMEND_POLICY_VALIDATION = 494;
	public static final int CANCEL_POLICY_VALIDATION = 495;
	public static final int CANCEL_POST_DATE = 496;
	public static final int QUOTE_ACTIVE_STATUS = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) );
	
	public static final String DEPLOYED_LOCATION = "DEPLOYED_LOCATION";
	
	public static final String ANNUAL_TURN_OVER = "ANNUAL_TURN_OVER";
	public static final String DISCOUNT_PER = "discountPer";
	public static final String LOADING_PER = "loadingPer";
	public static final BigDecimal DIS_LOAD_PERCENTAGE_LIMIT = new BigDecimal( Utils.getSingleValueAppConfig( "DIS_LOAD_PERCENTAGE_LIMIT" ) );
	public static final int EQUALS_C = 0;  //SONARFIX -- changed the variable from EQUALS to EQUALS_C -- 25-apr-2018
	public static final int FIRST_RECORD_GREATER = 1;
	public static final byte BLOCKED_STATUS = Byte.valueOf( Utils.getSingleValueAppConfig("BLOCKED_STATUS") );
	
	public static final String GENERAL_INFO_FETCH="GENERAL_INFO_FETCH";
	public static final String SET_PRE_PACKAGE_FLAG="SET_PRE_PACKAGE_FLAG";
	public static final String LOADSECTIONSDATA="LOAD_SECTION_DATA";
	
	//renewal
	public static final String INVOKE_HOME_RENEWAL_RATING = "invokeHomeRenewalRating";
	public static final Integer HOME_LIST_ITEM_RISK_CATEGORY = Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_LIST_ITEM_RISK_CATEGORY" ) );
	
	public static final String APPLY_MIN_PRM_HOME = "applyMinPrmHome";
	public static final String GET_MIN_PRM_TO_APPLY_HOME = "getMiniumPremiumToApply";
	public static final String GET_CONFIG_MIN_PRM = "getConfiguredMinPremium";
	public static final String GET_MIN_PRM_TO_APPLY = "getMiniumPremiumToApply";
	public static final String SAVE_RENEWAL_HOME_INSURANCE_DETAILS = "saveRenewalHomeInsuranceDetails";
	public static final double LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO = 25.0;
	public static final double NON_MEDICAL_CLAIM_AMT = 5000;
	
	public static final String TLC_CURRENT_DATE = "CURRENT_DATE";
	public static final int POLICY_CONVERSION_RULE = 497;
	
	public static final Integer MISLIVE_USER = 17;
	public static final String FINAL_DEST_LOOKUP_CAT = "FINAL_DEST";
	public static final String PA_OPTION1 = "1";
	public static final String PA_OPTION2 = "2";
	
	public static final double PA_OPTION1_SI = Double.valueOf( Utils.getSingleValueAppConfig("PA_OPTION1_SI"));
	public static final double PA_OPTION2_SI = Double.valueOf( Utils.getSingleValueAppConfig("PA_OPTION2_SI"));
	public static final String SBS_POL_TYPE = Utils.getSingleValueAppConfig("SBS_Policy_Type");
	
	public static final Integer EMIRATES_SCH_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "EMIRATES_SCH_CODE" ) );
	public static final String B2C_EMAIL_PATH_HOME = Utils.getSingleValueAppConfig( "B2C_RENEWAL_EMAIL_PATH_HOME" );
	
	public static final Integer BROKER_CREDIT_LIMIT = 498;
	public static final Integer BUS_TYPE_RENEWAL = Integer.valueOf( Utils.getSingleValueAppConfig( "BUS_TYPE_RENEWAL" ) );
	
	public static final String SAVE_PERSONAL_ACCIDENT = "savePersonalAccidentDetails";
	public static final String LOAD_PERSONAL_ACCIDENT = "loadPersonalAccidentDetails";
	public static final String SAVE_PERSONAL_ACCIDENT_INFO = "savePersonalAccidentDetails";
	public static final String SAVE_PA_PREMIUM= "savePersonalAccidentPremiumDetails";
	public static final String SAVE_CO_INSURANCE_INFO = "saveCoInsuranceDetails";
	public static final String SAVE_FINANCIER_INFO = "saveFinancierDetails";
	public static final String SAVE_PREVIOUS_INSURANCE_DETAILS_INFO = "savePreviousInsuranceDetailsDetails";
	public static final String T_TRN_COINSURANCE_QUO = "T_TRN_COINSURANCE_QUO";
	public static final String T_TRN_HIRE_PURCHASE_QUO = "T_TRN_HIRE_PURCHASE_QUO";
	/*Start : WC-MonoLine related entries*/
	public static final Integer WC_POLICY_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_POLICY_TYPE" ) );
//	public static final String SEQ_WC_QUO_NO = "SEQ_WC_QUO_NO";
	public static final String SAVE_WC_INSURANCE = "saveWCInsurance";
	public static final String APPROVE_QUO_WC_INSURANCE = "WCInsuranceApproveQuo";
	public static final String LOAD_WC_INSURANCE = "loadWCInsurance";
	public static final String PRORATE_PREMIUM_WC_INSURANCE = "proratePremium";
	public static final String LOAD_WORKMEN_DETAILS = "loadWorkmenDetails";
	public static final String SAVE_WORKMEN_DETAILS = "saveWorkmenDetails";
	public static final String TABLE_ID_T_TRN_WCTPL_PERSON_QUO = "T_TRN_WCTPL_PERSON_QUO";
	public static final String TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO = "T_TRN_WCTPL_UNNAMED_PERSON_QUO";
	public static final Short WC_CCG_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "WC_CCG_CODE" ) );
	public static final String LOB_CCG_CODE = "_CCG_CODE";
    public static final String LOAD_WC_RENEWAL_INSURANCE = "load_WC_1";
    public static final String SAVE_WC_RENEWAL_INSURANCE = "save_WC_1";
    public static final String WC_RENEWAL_MATRIX = "renmatx_WC_1";
    public static final String GET_RENEWAL_CLAIM_DETAILS = "GET_CLAIM_DETAILS";
    public static final String CHECK_WC_RENEWAL_MATRIX ="renmatx_WC_1";
    public final static String CHECK_FOR_FRAUD_CLAIM = "CHECK_FOR_FRAUD_CLAIM";
    public final static String UPDATE_RENEWAL_QUOTE_STATUS_COMMON = "UPDATE_RENEWAL_QUOTE_STATUS_COMMON";

	/*End : WC-MonoLine related entries*/
	public static final Long SECTION_ID_HOME = Long.valueOf(Utils.getSingleValueAppConfig("HOME_SEC_ID"));
	public static final String COMMON_FLOW = "COMMON FLOW";
	public static final Short WUP_ADMIN_EMPLOYMENT_TYPE = 8;
	
	public final static String SAVE_RENEWAL_QUOTE = "SAVE_RENEWAL_QUOTE";
	public final static String LOAD_RENEWAL_QUOTE = "LOAD_RENEWAL_QUOTE";
	public final static List<String> SEND_RENEWAL_SMS_LOBS = Arrays.asList(Utils.getMultiValueAppConfig("SEND_RENEWAL_SMS_LOB"));
	public final static List<String> COPY_TL_RENEWAL_LOBS = Arrays.asList(Utils.getMultiValueAppConfig("COPY_TL_FOR_RENEWAL_LOB"));
	public static final String BR_TOT_ACC_CODE = Utils.getSingleValueAppConfig( "BR_TOT_ACC_CODE" );
	public static final Integer OMAN = 30;
	public static final String LOAD_PARTNER_MGMT = "loadpartnermgmt";
	
	public static final String DEFAULT_SECTION_ID_PL = "6";
	public static final String STUDENT_LIABILITY_PC_CODE = Utils.getSingleValueAppConfig("STUDENT_LIABILITY_PC_CODE");
	public static final short STUDENT_LIABILITY_PEX_CODE_SHORT_1 = Short.valueOf(Utils.getSingleValueAppConfig("STUDENT_LIABILITY_PEX_CODE_1"));
	public static final short STUDENT_LIABILITY_PEX_CODE_SHORT_2 = Short.valueOf(Utils.getSingleValueAppConfig("STUDENT_LIABILITY_PEX_CODE_2"));
	
	/*
	 * FGB Staff Scheme Code for TRavel
	 * Added by Vishwa for the Advenet id:129135
	 */
	 /*Commented to make ravel rating configurable - ticket Id 145686*/
	//public static final Integer TRAVEL_TARIFF_CODE_RATING_FGB_SCHEME = Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_TARIFF_CODE_RATING_FGB_SCHEME" ) );
	public static final String DFAULT_VED = "31-Dec-49";
	
	/*
	 * Travel Scope: New Covers
	 * 131378
	 */
	
	public static final short TRAVEL_GENERAL_QUESTION = Short.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_GENERAL_QUESTION" ) );
	public static final short TRAVEL_GENERAL_QUESTION_COUNTRY = Short.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_GENERAL_QUESTION_COUNTRY" ) );
	public static final Short CRUISE_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig( "CRUISE_COVER_CODE" ));
	public static final Short TERRORISM_COVER_CODE = Short.valueOf(Utils.getSingleValueAppConfig( "TERRORISM_COVER_CODE" ));
	
	public static final String B2B_POLICYWORDING_DIRECT = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_DIRECT" );
	public static final String B2B_POLICYWORDING_NEW_DIRECT = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_NEW_DIRECT" );
	public static final String B2B_POLICYWORDING_BROKER = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_BROKER" );
	public static final String B2B_POLICYWORDING_NEW_BROKER = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_NEW_BROKER" );
	public static final String B2B_POLICYWORDING_DIRECT_BAH = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_DIRECT_BAH" );
	public static final String B2B_POLICYWORDING_BROKER_BAH = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_BROKER_BAH" );
	
	public static final String B2B_POLICYWORDING_DIRECT_FILENAME = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_DIRECT_FILENAME" );
	public static final String B2B_POLICYWORDING_DIRECT_NEW_FILENAME = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_DIRECT_NEW_FILENAME" );
	public static final String B2B_POLICYWORDING_BROKER_FILENAME = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_BROKER_FILENAME" );
	public static final String B2B_POLICYWORDING_BROKER_NEW_FILENAME = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_BROKER_NEW_FILENAME" );
	public static final String B2B_POLICYWORDING_DIRECT_BROKER_FILENAME_BAH = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_DIRECT_BROKER_FILENAME_BAH" );
	
	
	public static final int RI_CATEGORY_ONE = Integer.valueOf( Utils.getSingleValueAppConfig( "RI_CATEGORY_ONE") );
	public static final int RI_CATEGORY_TWO = Integer.valueOf( Utils.getSingleValueAppConfig( "RI_CATEGORY_TWO") );
	public static final int RI_CATEGORY_THREE = Integer.valueOf( Utils.getSingleValueAppConfig( "RI_CATEGORY_THREE") );	
	public static final int RI_CATEGORY_FOUR = Integer.valueOf( Utils.getSingleValueAppConfig( "RI_CATEGORY_FOUR") );
	public static final int RI_CATEGORY_FIVE = Integer.valueOf( Utils.getSingleValueAppConfig( "RI_CATEGORY_FIVE") );
	public static final int RI_CATEGORY_SIX = Integer.valueOf( Utils.getSingleValueAppConfig( "RI_CATEGORY_SIX") );
	public static final int RI_CATEGORY_SEVEN = Integer.valueOf( Utils.getSingleValueAppConfig( "RI_CATEGORY_SEVEN") );
	
	
	//Added for Batch renewal submission email implementation
	public static final String BATCH_RENEWAL_EMAIL_CONTENT = Utils.getSingleValueAppConfig("BATCH_RENEWAL_EMAIL_CONTENT");
	public static final String BATCH_RENEWAL_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("BATCH_RENEWAL_EMAIL_SUBJECT");
	public static final String BATCH_RENEWAL_EMAIL_TO = Utils.getSingleValueAppConfig("BATCH_RENEWAL_EMAIL_TO");
	public static final String BATCH_RENEWAL_EMAIL_CC = Utils.getSingleValueAppConfig("BATCH_RENEWAL_EMAIL_CC");
	public static final String SBS_PRODUCT = "SBS";
	
	public final static Short SC_PRM_COVER_VAT_TAX = 151;// 142244:Vat Implementation 
	public static final String VAT_TAX = "VATTAX";
	
	public static final String LOAD_HOME_VAT_RATE_AND_CODE = "fetchVatRateAndCode";
	public static final String LOAD_TRAVEL_VAT_RATE_AND_CODE = "fetchVatRateForVatCode";
	public static final String LOAD_WC_VAT_RATE_AND_CODE = "wcfetchVatRateForVatCode";
	
	public static final String QUOTE_ACCEPT = Utils.getSingleValueAppConfig( "QUOTE_ACCEPT" ); 
	 //Added for Bahrain 3 decimal - Starts
	public static final String DECIMAL_FORMAT_BAHRAIN = "0.000";
	 //Added for Bahrain 3 decimal - Ends
	public static final String DELETED_UW_QUESTION = "DELETED_UW_QUESTION";
	
	//changes-HomeRevamp#7.1
		public static final String B2B_POLICYWORDING_HOME_DIRECT = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_HOME_DIRECT" );
		public static final String B2B_POLICYWORDING_HOME_DIRECT_OWNER = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_HOME_DIRECT_OWNER" );
		public static final String B2B_POLICYWORDING_HOME_DIRECT_RENTAL = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_HOME_DIRECT_RENTAL" );
		public static final String B2B_POLICYWORDING_HOME_EMIRATES = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_HOME_EMIRATES" );
		public static final String B2B_POLICYWORDING_HOME_BROKER = Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_HOME_BROKER" );
		public static final String B2B_POLICYWORDING_FILEPATH=Utils.getSingleValueAppConfig( "B2B_POLICYWORDING_FILEPATH" );
		//changes-HomeRevamp#7.1
}

