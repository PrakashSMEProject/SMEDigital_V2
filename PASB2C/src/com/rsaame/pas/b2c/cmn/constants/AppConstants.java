package com.rsaame.pas.b2c.cmn.constants;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;


/**
 * All the application constants will be added here.
 */
 public final class AppConstants{

		/* Session variables */
		public static final String SESSION_USER_PROFILE_VO = "LOGGED_IN_USER";

		/* General constants */

		/* Request parameter names */

		/* AppConfig properties */
		public static final String APP_CONFIG_SKIP_FILTER_PAGES = "SKIP_FILTER_PAGES";
		public static final String APP_CONFIG_SKIP_FILTER_OPTYPES = "SKIP_FILTER_OPTYPES";
		public static final String APP_CONFIG_CMS_LOGIN_SCREEN_URL = "CMS_LOGIN_SCREEN_URL";
		public static final String APP_CONFIG_ADD_LOC_ALLOWED_SEC = "ADD_LOCATION_ALLOWED_SECTIONS";
		public static final String APP_CONFIG_RULES_ENABLED = "RULES_ENABLED";
		public static String DEFAULT_ENDT_ID = "DEFAULT_ENDTID_QUOTE";
		
		public static String zeroVal = "0";
		public static final String ANCHOR_TAG = "a";
		/* Properties files reload component */ 
		public static final String PROP_LOADER_SUB_ACTIVITY = "subActivity";
		public static final String PROP_LOADER_INIT_PATH = "PROP_LOAD_INIT_PATH";
		public static final String PROP_LOADER_EXECUTE_PATH = "PROP_LOAD_EXECUTE_PATH";
		public static final String RELOADABLE_PROPERTIES_FILE = "propFile";	
		
		public static final String SYS_AUTHORIZATION_DAO = "authorizationDAO";
		
		public static final String DROPDOWN="dropdown";
		public static final String FILTER_DROPDOWN="dropdown";
		public static final String HTML_DROPDOWN="html_dropdown";
		
		public static final String NUMBEROFCOLS="NoOfCols";
		
		public static final String INPUTTYPE="InputType";
		
		public static final String TAGNAME="TagName";
		
		public static final String TAGID="TAgID";
		
		public static final String TEXTBOX="textbox";
		
		public static final int MAXLENGTH=150;
		
		public static final String ATTR_TEMPLATE="%s='%s'";  
		
		public static final String DOJOTYPETEXT="dijit.form.TextBox";
		
		public static final String OPTION_TEMPLATE="<option value='%1$'>%1$s</option>";
		
		public static final String IDENTIFIER="Identifier";
		
		public static final String DISABLEDFLAG="DisabledFlag";
		
		public static final String STYLE="Style";
		
		public static final String MANDATORYFLAG="MandatoryFlag";
		
		public static final String REGEXP="RegExp";
		
		public static final String VALUE="value"; 
		
		public static final String MISSINGMESSAGE="MissingMessage";
		
		public static final String INVALIDMESSAGE="InvalidMessage";
		
		public static final String AUTOCOMPLETE="AutoComplete";
		
		public static final String CODE="Code";
		
		public static final String DEFAULTVALUE="DefaultValue";
		
		public static final String OUT="Out";
		
		public static final String DOJOTYPESELECT="dijit.form.Select";
		
		public static final String FORMAT="format";
		public static final String CURRENCY="currency";
		
		public static final String PRODUCT = "SBS";
			
		public static final String AUTHORIZATION_DAO = "authorizationDAO";
		
		public static final String READ_MODE = "V";
		
		public static final String WRITE_MODE = "E";
		
		public static final String HIDDEN_MODE = "H";
		
		public static final String SECTION_ACCESS = "sectionAccess";
		
		public static final String FIELD_ACCESS = "fieldAccess";
		
		public static final String DELIMITER = "-";
		
		public static final String MODE = "mode";
		
		public static final String SECTION_NAME = "sectionName";
		
		public static final String SECTION_ID = "sectionID";
		
		public static final String FUNTION_NAME = "functionName";
		
		public static final String SCREEN_NAME = "ScreenName";
		
		public static final String PREV_TYPE = "privType";
		
		public static final String RULE_RESULT_SCOPE  = "Rule_Result_Scope";
		
		public static final String TEXT_INPUT = "text";
		
		public static final String CHECKBOX="checkbox";
		
		public static final String SELECT_INPUT = "select";
		
		public static final String POLICY_CONTEXT = "Policy_Context";
		
		// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
		//public static final Integer GENERAL_INFO_SECTION = new Integer(0);
		public static final Integer GENERAL_INFO_SECTION =Integer.valueOf( 0 );
		
		public static final String DEFAULT_CLASS_CODE = "DEFAULT_CLASS_CODE";
		
		public static final String NEXT="NEXT";
		
		public static final String DELETELOCATION="DELETELOCATION";
		
		public static final String ADDLOCATION="ADDLOCATION";
		
		public static final String SAVE="SAVE";
		
		public static final String PREVIOUS="PREVIOUS";
		
		public static final String ACTION="action";
		
		public static final String PAGE = "page";
		
		public static final String ACTIONNAME="actionName";
		
		public static final String PARSAVEREQUESTHANDLER="PAR_PAGE";
		
		public static final String PLREQUESTHANDLER="plPageRH";
		
		public static final String PARLOAD="TEST";
		
		public static final String PLLOAD="QUOTE_PAGE";
		
		public static final String OPERATIONTYPE="opType";
		
		public static final String PLSAVEREQUESTHANDLER="PAR_PAGE";
		
		public static final String ON_CHANGE_EVENT="onChange";
		
		public static final String TAG_CLASS="tagClass";
		
		public static final String GET_COMMISSION = "GET_COMMISSION";	
		
		public static final String LOADSECTIONSDATA="LOAD_SECTION_DATA";
		
		public static final String FETCH_SELECTED_SECTIONS="FETCH_SELECTED_SECTIONS";
		
		public static final String GENERAL_INFO_FETCH="GENERAL_INFO_FETCH";
		
		public static final String LOOKUP_INPUT="lookUpTag";
		
		public static final String SET_PRE_PACKAGE_FLAG="SET_PRE_PACKAGE_FLAG";
		
		public static final String GET_PAYMENT_DETS="Payment_details";
		
		public static final String STORE_POL_COMMENTS = "STORE_POL_COMMENTS";
		
		/* START: Default section Ids */
		public static final String DEFAULT_SECTION_ID_PAR = "1";
		public static final String DEFAULT_SECTION_ID_MB = "3";
		public static final String DEFAULT_SECTION_ID_EE = "5";
		public static final String DEFAULT_SECTION_ID_PL = "6";
		public static final String DEFAULT_SECTION_ID_BI = "2";
		public static final String DEFAULT_SECTION_ID_WC = "7";
		public static final String DEFAULT_SECTION_ID_MONEY = "8";
		public static final String DEFAULT_SECTION_ID_FIDELITY = "9";
		public static final String DEFAULT_SECTION_ID_TB = "10";
		public static final String DEFAULT_SECTION_ID_GPA = "12";
		/* END: Default section Ids */

		/* START: Request parameters and attribute name constants. */
		public static final String REQ_RELOAD_LOC_CASE = "RELOAD_LOC_CASE";
		public static final String REQ_PARAM_RISK_GROUP_ID = "riskGroupId";
		public static final String REQ_PARAM_JUMP_TO_SECTION_ID = "jumpToSectionId";
		public static final String REQ_PARAM_FOR_NAV = "forNav";
		public static final String REQ_ATTR_CURR_RG = "currRG";
		public static final String REQ_ATTR_CURR_RGD = "currRGD";
		public static final String REQ_ATTR_CURR_RG_ID = "currRGId";
		public static final String REQ_ATTR_CURR_SECTION = "currSectionVO";
		public static final String REQ_ATTR_CURR_ACTION = "CURR_ACTION";
		public static final String REQ_ATTR_REFERRAL_ACTION = "REFERRAL_ACTION";
		public static final String REQ_ATTR_POLICY = "policy";
		public static final String REQ_ATTR_INSURED_CODE = "quote_name_insuredcode";
		public static final String REQ_ATTR_CLAIMS_CHECK = "CLAIMS_CHECK";
		
		/* END: Request parameters and attribute name constants. */
		
		/* Start: Configured section ids*/
		public static final int SECTION_ID_GEN_INFO = Integer.valueOf( Utils.getSingleValueAppConfig( "GENERAL_SECTION", "0" ) );
		public static final int SECTION_ID_PAR = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
		public static final int SECTION_ID_MB = Integer.valueOf( Utils.getSingleValueAppConfig( "MB_SECTION" ) );
		public static final int SECTION_ID_PL = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
		public static final int SECTION_ID_WC = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" ) );
		public static final int SECTION_ID_EE = Integer.valueOf( Utils.getSingleValueAppConfig( "EE_SECTION" ) );
		public static final int SECTION_ID_TB = Integer.valueOf( Utils.getSingleValueAppConfig( "TB_SECTION" ) );
		public static final int SECTION_ID_GROUP_PERSONAL_ACCIDENT = Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_SECTION" ) );
		public static final int SECTION_ID_MONEY = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_SECTION" ) );
		public static final int SECTION_ID_FIDELITY = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION" ) );
		public static final int SECTION_ID_GIT = Integer.valueOf( Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_SECTION" ) );
		public static final int SECTION_ID_PREMIUM = Integer.valueOf( Utils.getSingleValueAppConfig( "PREMIUM_PAGE", "999" ) );
		public static final int SECTION_ID_BI = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_SECTION" ) );
		public static final int SECTION_ID_DETERIORATION_OF_STOCK = Integer.valueOf( Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_SECTION" ) );
		/* End: Configured section ids*/
		
		/* Start: Configured class ids*/
		public static final int CLASS_ID_PL = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_CLASS" ) );
		public static final int CLASS_ID_WC = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_CLASS" ) );
		public static final int CLASS_ID_MONEY = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_CLASS" ) );
		public static final int CLASS_ID_PAR = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_CLASS", "2" ) );
		public static final int CLASS_ID_MB = Integer.valueOf( Utils.getSingleValueAppConfig( "MB_CLASS", "2" ) );
		/* End: Configured class ids*/

		public static final String GET_COMMENTS = "Comments_details";
		public static final String GET_STD_CLAUSES = "StdClauses";
		public static final String SAVE_PAYMENT_DETAILS="SAVE_PAYMENT_DETAILS";

		public static final String SECTION_PPP_DATA = "Section_PPP_Data";
		
		public static final String SECTION_CONTENTS = "Section_Contents";
		
		public static final String PRE_PACKAGED_SVC_IDENTIFIER = "FETCH_PRE_PACKAGED_DATA";
		
		public static final int QUOTE_PENDING =Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_PENDING" ).trim() );
		public static final int QUOTE_ACCEPT =Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACCEPT" ) );
		public static final int QUOTE_ACTIVE = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) );
		public static final int QUOTE_REFERRED = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REFERRED" ) );
		public static final int QUOTE_DECLINED = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_DECLINED" ) );
		public static final int QUOTE_REJECT = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REJECT" ) );
		public static final int QUOTE_EXPIRED = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_EXPIRED" ) );
		
		public static final String APP_DECIMAL_FORMAT = "0.00";
		
		public static final String GET_PPP_SECTIONLIST="GET_PPP_SECTIONLIST";
		
		/*Auth constants*/
		public static final String POL_QUO_STATUS = "pol_quo_status";
		public static final String USER_POL_QUO_STATUS = "user_pol_quo_status";
		public static final String CASCADEVISIBILITY = "cascadedVisibliity";
		/*auth constants ends */

		public static final String BROKERCODE="brokercode";
		public static final String PROFILE="profile";
		public static final String IS_COMMISSION_APPLICABLE="isCommissionApplicable";
		public static final String SAVE_SELECTED_SECTIONS="SAVE_SELECTED_SECTIONS";
		
		public static final String GET_PREMIUM_DETAILS="GET_PREMIUM_DETAILS";
		public static final String LOC_STATUS_INACTIVE="N";
		public static final String LOC_STATUS_ACTIVE="Y";
		// Premium Disc Load Value Start
		public static final int LOSS_RATIO_PERC_RANGE_ONE = 30;
		public static final int LOSS_RATIO_PERC_RANGE_TWO = 60;
		public static final double LOSS_RATIO_PERC_VALUE_ONE = 0;
		public static final double LOSS_RATIO_PERC_VALUE_TWO = 0.1;
		public static final double LOSS_RATIO_PERC_VALUE_THREE = 0.25;
		
		public static final String REQ_PARAM_LOC_CURR_SEC="isLocAddedCurrSec" ;
		public static final String TRUE = "true";
		// Premium Disc Load Value End
		
		public static final int APP_BASE_COVER_CODE=1;
		public static final String IS_ADDTL_COVER_APPL = "IS_ADDTL_COVER_APPL";
		public static final String BASE_LOC = "baseLoc";
		public static final String NEW_LOC = "newLoc";
		
		public static final String APP_SERVER_DEPLOYMENT = "APP_SERVER_DEPLOYMENT";
		public static final String NON_APP_SERVER_DEPLOYMENT = "N";
		
		public static final String REQ_ATTR_SEL_TARIFF_CODE = "tariffCode";
		public static final String REQ_ATTR_SEL_SCHEME_CODE = "schemeCode";
		public static final String REQ_ATTR_SEL_BUS_DESC_CODE = "busDescCode";
		public static final String REQ_ATTR_SEL_OCP_CODE = "ocpCode";
		
		public static final String REFUND_TYPE="Refund";
		
		public static final String RISK_GROUP_NAME_WC="WC";
		
		public static final BigDecimal POLICY_SCHEDULE = new BigDecimal( Utils.getSingleValueAppConfig( "POLICY_SCHEDULE" ) );
		public static final BigDecimal ENDORSEMENT_SCHEDULE = new BigDecimal( Utils.getSingleValueAppConfig( "ENDORSEMENT_SCHEDULE" ) );
		public static final BigDecimal CREDIT_NOTE = new BigDecimal( Utils.getSingleValueAppConfig( "CREDIT_NOTE" ) );
		public static final BigDecimal DEBIT_NOTE = new BigDecimal( Utils.getSingleValueAppConfig( "DEBIT_NOTE" ) );
		public static final BigDecimal GROSS_DEBIT_NOTE = new BigDecimal( Utils.getSingleValueAppConfig( "GROSS_DEBIT_NOTE" ) );
		public static final BigDecimal GROSS_CREDIT_NOTE = new BigDecimal( Utils.getSingleValueAppConfig( "GROSS_CREDIT_NOTE" ) );
		public static final BigDecimal FREE_ZONE = new BigDecimal( Utils.getSingleValueAppConfig( "FREE_ZONE" ) );
		public static final BigDecimal RECEIPT = new BigDecimal( Utils.getSingleValueAppConfig( "RECEIPT" ) );
		public static final BigDecimal MAIL_BODY = new BigDecimal( Utils.getSingleValueAppConfig( "MAIL_BODY" ) );
		public static final byte DEL_SEC_LOC_STATUS = Byte.valueOf( Utils.getSingleValueAppConfig("DEL_SEC_LOC_STATUS") );
		public static final String INT_ACC_EXE_CATEGORY = "EMPLOYEE";
		public static final String INT_ACC_EXE_DEFAULT = "DEFAULT";
		public static final String INT_ACC_EXE_DEFAULT_DESC = "Other RSA Dubai";
		public static final String REQ_ATTR_INT_ACC_EXE_DEFAULT_VAL = "intaccexecodedefaultval";
		public static final String PREMIUM_PAGE_DETAILS = "PREMIUM_PAGE_DETAILS";
		public static final String DEFAULT_TERRITORY_CODE = "DEFAULT_TERRITORY_CODE";
		public static final String DEFAULT_JURISDICTION_CODE = "DEFAULT_JURISDICTION_CODE";
		
		public static final String PPPAR_DEFAULT_UWQ_VISIBILITY = "PPPAR_UWQ_VISIBILITY";
		public static final String PPPL_DEFAULT_UWQ_VISIBILITY = "PPPL_UWQ_VISIBILITY";
		public static final String PPPAR_BLD_COVERED_VISIBILITY = "PPPAR_BLD_COVERED_VISIBILITY";
		public static final String PPP_CALC_PRM_BTN_VISIBILITY = "PPP_CALC_PRM_BTN_VISIBILITY";
		public static final String PPP_ADD_LOC_TAB_VISIBILITY	= "PPP_ADD_LOC_TAB_VISIBILITY";
		public static final String PPWC_PA_COVER_VISIBILITY = "PPWC_PA_COVER_VISIBILITY";
		public static final String PPMONEY_SAFE_CIR_VISIBILITY = "PPMONEY_SAFE_CIR_VISIBILITY";
		
		public static final String LOOKUP_LEVEL1 = "ALL";
		public static final String LOOKUP_LEVEL2 = "ALL";
		
		public static final String COPY_QUO_RULES_EXECUTION="COPY_QUO_RULES_EXECUTION";
		
		public static final String REQUEST_ATTR_GI_POBOX = "GeneralInfoPOBoxVal";
		
		public static final String SESSION_ATTR_HAS_POBOX_CHANGED = "hasPOBoxChanged";

		public static final Long INTIAL_POL_ENDT = 0L;

		public static final String SESSIONDATA = "SESSIONDATA";
		
		public static final String EDIT_QUO_RULES_EXEC_CONFIG_KEY="EDIT_QUO_RULES_EXEC";

		public static final String PAS_GR_D_C = "PAS_GR_D_C";

		public static final String BRANCH_SELECT = "branchSelect";

		public static final String SET_BRANCH = "setBranch";

		public static final String USER_ID = "userid";
		
		public static final String USER_BRANCH = "userBranch";

		public static final String BRANCH_CODE = "branchcode";
		
		public static final String AUTH_DETAILS = "AUTH_DETAILS";
		
		public static final String INSURED_ID = "insuredId";
		
		public static final String CCG_CODE = "ccgCode";
		
		public static final String COUNTRY_LOOKUP_VAL = "CountryLookUp";
		
		public static final String JURISDICTION_LOOKUP_VAL = "JurisdictionLookUp";
		
		public static final String 	CTX_LOCATION = "LOCATION";		
		
		public static final String REQUEST_ATTR_GI_TURNOVER = "GeneralInfoTurnoverVal";
		
		public static final String DEPLOYED_LOCATION = "DEPLOYED_LOCATION";

		public static final String CONTENT_SI_FORMAT = "CONTENT_SI_FORMAT";
		
		public static final String UPDATE_MIN_PRM = "UPDATE_MIN_PRM";

		public static final String RANGE = "RANGE";

		public static final String CASCADELIMIT = "CASCADELIMIT";

		public static final String FALSE = "false";
		
		public static final String TRADE_LICENSE_CHECK = "TRADE_LICENSE_CHECK";
		
		public static final String NO_EMP_GI = "NO_EMP_GI";

		public static final String GI_ANN_TURNOVER = "GI_ANN_TURNOVER";

		public static final Integer HOME_POLICY_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_POLICY_TYPE" ) );
		public static final Integer HOME_CLASS_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_CLASS_CODE" ) );
		public static final Integer TRAVEL_CLASS_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_CLASS_CODE" ) );
		public static final Integer HOME_POLICY_TERM = Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_POLICY_TERM" ) );
		public static final Integer TRAVEL_POLICY_TYPE = Integer.valueOf(Utils.getSingleValueAppConfig("TRAVEL_POLICY_TYPE"));
		
		public static final Integer SBS_CLASS_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_CLASS_CODE" ) );
		public static final Integer CONVERTED_TO_POL_STATUS = Integer.valueOf( Utils.getSingleValueAppConfig( "CONV_TO_POL" ) );
		public static final Integer HOME_SUMINSURED =  Integer.parseInt( Utils.getSingleValueAppConfig( "HOME_SI_LIMIT" ) );
		public static final Integer LONG_TERM_TRAVEL_DAYS = Integer.valueOf( Utils.getSingleValueAppConfig( "LONG_TERM_TRAVEL_DAYS" ) );
		public static final Integer TRAVEL_LONG_TERM_POLICY_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_LONG_TERM_POLICY_TYPE" ) );
		public static final Integer TRAVEL_SHORT_TERM_POLICY_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_SHORT_TERM_POLICY_TYPE" ) );
		public static final Integer SBS_POLICY_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_POLICY_TYPE" ) );
		
		
		public static final Integer NEW_QUOTATION = Integer.valueOf( Utils.getSingleValueAppConfig( "NEW_QUOTATION" ));
		public static final Integer NEW_RENEWAL_QUOTATION = Integer.valueOf( Utils.getSingleValueAppConfig( "NEW_RENEWAL_QUOTATION" ));
		
		public static final String TRANSACTION_NO = "transactionNo";
		
		public static final String PAGE_VALUE = "value";
		public static final String PRODUCT_RENDERER = "productsRenderer";
		public static final String COVERS = "Covers";
		public static final String BUILDING = "buildingDetails";

		public static final Object ADDITIONAL_COVERS = "Additional Covers";

		public static final String QUOTE_DOC_CODES = "QUOTE_DOC_CODES";

		public static final String POLICY_DOC_CODES = "POLICY_DOC_CODES";

		public static final String COMMON_VO = "CommonVO";
		public static final String HOME_PERSONAL_POSSESSION_SI_TARIFF = "HOME_PERSONAL_POSSESSION_SI_TARIFF";
		
		public static final String	SMS_STATUS_ON ="on";
		public static final String	SMS_STATUS_ACTIVE = "active";
		public static final String  SMS_STATUS_INACTIVE = "inactive";

		public static final String	STATUS_ON ="on";
		
		public static final String POPULATE = "POPULATE";
		public static final String PREMIUM = "Premium";
		public static final String WC_NO_EMP_VAL = "WC_NO_EMP_VAL";

		public static final Integer BUS_TYPE_RENEWAL = Integer.valueOf( Utils.getSingleValueAppConfig( "BUS_TYPE_RENEWAL" ) );
		public static final Integer BUS_TYPE_NEW = Integer.valueOf( Utils.getSingleValueAppConfig( "BUS_TYPE_NEW" ) );
		public static final Integer BUS_TYPE_NEW_FOR_EXISTING = Integer.valueOf( Utils.getSingleValueAppConfig( "BUS_TYPE_NEW_FOR_EXISTING" ) );
		public static final String PROMOTIONAL_CODES = "promoCodes";
		public static final String PROMOTIONAL_DISC = "promoDiscount";
		
		
		public static final String POLICY_ID = "POLICY_ID";
		public static final String ENDT_ID = "ENDT_ID";
		public static final String REFERRAL_VO = "REFERRAL_VO";
		public static final Integer HOME_LIST_ITEM_RISK_CATEGORY = 2;
		public static final Integer HOME_COVER_RISK_CATEGORY = 1;
		public static final String APPROVE_QUO = "APPROVE_QUO";
		public static final Integer HOME_PERSONAL_POSSESSION_RISK_TYPE=32;
		
		/** Validation related constants start */
		public static final String EMAIL_FORMAT_VALIDATION_PATTERN=Utils.getSingleValueAppConfig("EMAIL_RE");
		public static final String ALPHABETS_VALIDATION_PATTERN=Utils.getSingleValueAppConfig("ALPHBETS_RE");
		public static final String NUMERIC_VALIDATION_PATTERN=Utils.getSingleValueAppConfig("NUMERIC_RE");
		public static final String ALPHANUMERIC_VALIDATION_PATTERN=Utils.getSingleValueAppConfig("ALPHANUMERIC_RE");
		public static final String ALPHA_WITH_SPACE_VALIDATION_PATTERN=Utils.getSingleValueAppConfig("ALPHA_WITH_WHITE_SPACE_RE");
		public static final String ALPHANUMERIC_WITH_SPACE_VALIDATION_PATTERN=Utils.getSingleValueAppConfig("ALPHANUMERIC_WITH_WHITE_SPACE_RE");
		/** Validation related constants end */
		
		/** B2C Travel And Home related default values start */
		public static final int B2C_DEFAULT_DIST_CHANNEL = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_DEFAULT_DIST_CHANNEL"));
		public static final Integer DIST_CHANNEL_BROKER = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_BROKER" ) );
		public static final Integer DIST_CHANNEL_DIRECT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT" ) );
		public static final Integer DIST_CHANNEL_DIRECT_WEB = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT_WEB" ) );
		public static final Integer DIST_CHANNEL_DIRECT_CALL_CENTER = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT_CALL_CENTER" ) );
		public static final Integer DIST_CHANNEL_AGENT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_AGENT" ) );
		public static final Integer DIST_CHANNEL_AFFINITY_DIRECT_AGENT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_AFFINITY_DIRECT_AGENT" ) );
		public static final Integer DIST_CHANNEL_AFFINITY_AGENT = Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_AFFINITY_AGENT" ) );
		public static final String B2C_DEFAULT_CUST_SRC = Utils.getSingleValueAppConfig("B2C_DEFAULT_CUST_SRC");
		public static final int B2C_DEFAULT_BROKER_NAME = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_DEFAULT_BROKER_NAME"));
		public static final int B2C_DEFAULT_SRC_OF_BUS = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_DEFAULT_SRC_OF_BUS"));
		public static final int B2C_DEFAULT_POL_STATUS = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_DEFAULT_POL_STATUS"));
		public static final int B2C_DEFAULT_DEPLOYED_LOCATION = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_DEPLOYED_LOCATION"));
		public static final int B2C_ALLOWED_MIN_MOB_NUM_LENGTH = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_ALLOWED_MIN_MOB_NUM_LENGTH"));
		public static final int B2C_ALLOWED_MAX_EMAILID_LENGTH = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_ALLOWED_EMAILID_LENGTH"));
		public static final Integer B2C_TRAVEL_DEFAULT_SCHEME_CODE = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_TRAVEL_DEFAULT_SCHEME_CODE"));
		public static final String B2C_HOME_DEFAULT_SCHEME = Utils.getSingleValueAppConfig("B2C_HOME_DEFAULT_SCHEME");
		public static final String B2C_HOME_DEFAULT_TARIFF = Utils.getSingleValueAppConfig("B2C_HOME_DEFAULT_TARIFF");
		/** B2C Travel And Home related default values end */
		
		/** B2C Email templates configurations start */
		
		//For travel insurance
		public static final String B2C_STEP1_ONLY_TRAVEL_TEMPLATE = Utils.getSingleValueAppConfig("B2C_STEP1_ONLY_TRAVEL_TEMPLATE");
		public static final String B2C_TRAVEL_SAVE_FOR_LATER_TEMPLATE = Utils.getSingleValueAppConfig("B2C_TRAVEL_SAVE_FOR_LATER_TEMP_NAME");
		public static final String B2C_TRAVEL_GI_REFERRAL_TEMPLATE = Utils.getSingleValueAppConfig("B2C_TRAVEL_GI_REFERRAL_TEMPLATE");
		public static final String B2C_TRAVEL_SUCCESS_POLICY_PURCHASE_TEMPLATE=Utils.getSingleValueAppConfig("B2C_TRAVEL_SUCCESS_POLICY_PURCHASE_TEMPLATE");
		public static final String B2C_TRAVEL_C2P_REMINDER_TEMPLATE = Utils.getSingleValueAppConfig("B2C_TRAVEL_C2P_REMINDER_TEMPLATE");
		public static final String B2C_TRAVEL_C2P_REMINDER_15DAYS_TEMPLATE = Utils.getSingleValueAppConfig("B2C_TRAVEL_C2P_REMINDER_15DAYS_TEMPLATE");
		
		//For home insurance
		public static final String B2C_STEP1_ONLY_HOME_TEMPLATE = Utils.getSingleValueAppConfig("B2C_STEP1_ONLY_HOME_TEMPLATE");
		public static final String B2C_HOME_SAVE_FOR_LATER_TEMP_NAME = Utils.getSingleValueAppConfig("B2C_HOME_SAVE_FOR_LATER_TEMP_NAME");
		public static final String B2C_HOME_GI_REFERRAL_TEMPLATE = Utils.getSingleValueAppConfig("B2C_HOME_GI_REFERRAL_TEMPLATE");
		public static final String B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE=Utils.getSingleValueAppConfig("B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE");
		public static final String B2C_HOME_C2P_REMINDER_TEMPLATE = Utils.getSingleValueAppConfig("B2C_HOME_C2P_REMINDER_TEMPLATE");
		public static final String B2C_HOME_C2P_REMINDER_15DAYS_TEMPLATE = Utils.getSingleValueAppConfig("B2C_HOME_C2P_REMINDER_15DAYS_TEMPLATE");
		
		//For FGB MS added by Vishwa
		public static final Integer B2C_FGB_BROKER_CODE=317;
		public static final String B2C_FGBTRAVEL="FGBTravel";
		public static final String B2C_FGBHOME="FGBHome";
		public static final String B2C_JLT_SBS="JLT_SBS";
		
		//commented as part of UAT feedback of 141906. FAB broker code is same as FGB Broker Code 317
		/*public static final Integer B2C_FAB_BROKER_CODE=Integer.parseInt(Utils.getSingleValueAppConfig("FAB_BROKER_CODE"));
		public static final String B2C_FABTRAVEL="FABTravel";
		public static final String B2C_FABHOME="FABHome";*/
		
		// For Nexus added by Vishwa
		public static final Integer B2C_NEX_BROKER_CODE=252;
		public static final String B2C_TRAVEL_SFL_NEXUS = Utils.getSingleValueAppConfig("B2C_TRAVEL_SFL_NEXUS");
		public static final String B2C_TRAVEL_C2P_NEXUS= Utils.getSingleValueAppConfig("B2C_TRAVEL_C2P_NEXUS");
		
		//For general templates
		public static final String B2C_PAYMENT_FAILURE_TEMPLATE = Utils.getSingleValueAppConfig("B2C_PAYMENT_FAILURE_TEMPLATE");
		public static final String B2C_ASYNC_FAIL_TEMPLATE = Utils.getSingleValueAppConfig("B2C_ASYNC_FAIL_TEMPLATE");
		public static final String B2C_PAYMENT_FAILURE_SUBJECT = Utils.getSingleValueAppConfig("B2C_PAYMENT_FAILURE_SUBJECT");
		public static final String B2C_PAYMENT_FAILURE_TOEMAILID = Utils.getSingleValueAppConfig("B2C_PAYMENT_FAILURE_TOEMAILID");
		public static final String B2C_ASYNC_FAILURE_TOEMAILID = Utils.getSingleValueAppConfig("B2C_ASYNC_FAILURE_TOEMAILID");
		public static final String B2C_REQUEST_CALL_BACK_TEMPLATE = Utils.getSingleValueAppConfig("B2C_REQUEST_CALL_BACK_TEMPLATE");
		public static final String B2C_CALL_BACK_REQ_EMAIL_SUB = Utils.getSingleValueAppConfig("B2C_CALL_BACK_REQ_EMAIL_SUB");
		public static final String B2C_PAYMENT_FAIL_TRIGGER_EMAIL_TO_ID = Utils.getSingleValueAppConfig("B2C_PAYMENT_FAIL_TRIGGER_EMAIL_TO_ID");
		
		//For make a claim templates
		public static final String B2C_MAKE_CLAIM_SUCCESS_TEMPLATE = Utils.getSingleValueAppConfig("B2C_MAKE_CLAIM_SUCCESS_TEMPLATE");
		public static final String B2C_MAKE_CLAIM_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_MAKE_CLAIM_EMAIL_SUBJECT");
		public static final String B2C_MAKE_CLAIM_NOTIFICATION_TEMPLATE = Utils.getSingleValueAppConfig("B2C_MAKE_CLAIM_NOTIFICATION_TEMPLATE");
		public static final String B2C_MAKE_CLAIM_NOTIFICATION_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_MAKE_CLAIM_NOTIFICATION_EMAIL_SUBJECT");
		
		//For Golf Insurance submit templates
		public static final String B2C_GOLF_INSURANCE_SUCCESS_TEMPLATE = Utils.getSingleValueAppConfig("B2C_GOLF_INSURANCE_SUCCESS_TEMPLATE");
		public static final String B2C_GOLF_INSURANCE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_GOLF_INSURANCE_EMAIL_SUBJECT");
		public static final String B2C_GOLF_INSURANCE_NOTIFICATION_TEMPLATE=Utils.getSingleValueAppConfig("B2C_GOLF_INSURANCE_NOTIFICATION_TEMPLATE");
		
		//For Personal Accident submit templates
		public static final String B2C_PERSONAL_ACCIDENT_INSURANCE_SUCCESS_TEMPLATE = Utils.getSingleValueAppConfig("B2C_PERSONAL_ACCIDENT_INSURANCE_SUCCESS_TEMPLATE");
		public static final String B2C_PERSONAL_ACCIDENT_INSURANCE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_PERSONAL_ACCIDENT_INSURANCE_EMAIL_SUBJECT");
		public static final String B2C_PERSONAL_ACCIDENT_INSURANCE_NOTIFICATION_TEMPLATE=Utils.getSingleValueAppConfig("B2C_PERSONAL_ACCIDENT_INSURANCE_NOTIFICATION_TEMPLATE");
		
		/** B2C Email templates configurations end */
		
		/** B2C Email related default values start */
		public static final String B2C_TRAVEL_QUOTE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_TRAVEL_QUOTE_EMAIL_SUBJECT");
		public static final String B2C_HOME_QUOTE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_HOME_QUOTE_EMAIL_SUBJECT");
		public static final String B2C_HOME_REFERRAL_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_HOME_REFERRAL_EMAIL_SUBJECT");
		public static final String B2C_TRAVEL_REFERRAL_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_TRAVEL_REFERRAL_EMAIL_SUBJECT");
		public static final String B2C_REFERRAL_TRIGGER_TO_MAILID = Utils.getSingleValueAppConfig("B2C_REFERRAL_TRIGGER_TO_MAILID");
		public static final String B2C_DEFAULT_CUST_NAME = Utils.getSingleValueAppConfig("B2C_DEFAULT_CUST_NAME");
		public static final String B2C_EMAIL_INSURED_NAME_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_INSURED_NAME_IDENTIFIER");
		public static final String B2C_EMAIL_INSURED_LAST_NAME_IDF = Utils.getSingleValueAppConfig("B2C_EMAIL_INSURED_LAST_NAME_IDF");
		public static final String B2C_EMAIL_PREMIUM_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_PREMIUM_IDENTIFIER");
		public static final String B2C_EMAIL_QUOTE_NO_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_QUOTE_NO_IDENTIFIER");
		public static final String B2C_EMAIL_REFERRAL_REASON_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_REFERRAL_REASON_IDENTIFIER");
		public static final String B2C_DEFAULT_FROM_EMAILID = Utils.getSingleValueAppConfig("B2C_DEFAULT_FROM_EMAILID");
		public static final String B2C_DEFAULT_CC_EMAILID = Utils.getSingleValueAppConfig("B2C_DEFAULT_CC_EMAILID");
		public static final String B2C_TRAVEL_POLICY_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_TRAVEL_POLICY_EMAIL_SUBJECT");
		public static final String B2C_HOME_POLICY_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("B2C_HOME_POLICY_EMAIL_SUBJECT");
		public static final String B2C_INSURANCENAME_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_INSURANCENAME_IDENTIFIER");
		public static final String B2C_EMAIL_DATE_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_DATE_IDENTIFIER");
		public static final String B2C_EMAIL_TIME_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_TIME_IDENTIFIER");
		public static final String B2C_EMAIL_CALL_BACK_NUMBER_IDF = Utils.getSingleValueAppConfig("B2C_EMAIL_CALL_BACK_NUMBER_IDF");
		public static final String B2C_REQUEST_CALL_BACK_EMAILID = Utils.getSingleValueAppConfig("B2C_REQUEST_CALL_BACK_TOEMAILID");
		
		public static final String B2C_EMAIL_POLICY_WORDING_TRAVEL = Utils.getSingleValueAppConfig("B2C_EMAIL_POLICY_WORDING_TRAVEL");
		public static final String B2C_EMAIL_POLICY_WORDING_HOME = Utils.getSingleValueAppConfig("B2C_EMAIL_POLICY_WORDING_HOME");
		
		public static final int B2C_POLICY_ATTACHMENT_SIZE = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_POLICY_ATTACHMENT_SIZE"));
		public static final int B2C_POLICY_ATTACHMENT_SIZE_MORTGAGE = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_POLICY_ATTACHMENT_SIZE_MORTGAGE"));
		
		public static final String B2C_EMAIL_CLICK_HERE_TAG_IDF = Utils.getSingleValueAppConfig("B2C_EMAIL_CLICK_HERE_TAG_IDF");
		public static final String B2C_EMAIL_CLICK_HERE_TAG = Utils.getSingleValueAppConfig("B2C_EMAIL_CLICK_HERE_TAG");
		public static final String HREF_URL_IDENTIFIER = Utils.getSingleValueAppConfig("HREF_URL_IDENTIFIER");
		public static final String B2C_FETCH_QUOTE_TRAVEL_METHOD = Utils.getSingleValueAppConfig("B2C_FETCH_QUOTE_TRAVEL_METHOD");
		public static final String B2C_FETCH_QUOTE_HOME_METHOD = Utils.getSingleValueAppConfig("B2C_FETCH_QUOTE_HOME_METHOD");
		public static final String QUOTE_NUM_REQ_PARAM = Utils.getSingleValueAppConfig("QUOTE_NUM_REQ_PARAM");
		public static final String EMAIL_REQ_PARAM = Utils.getSingleValueAppConfig("EMAIL_REQ_PARAM");
		public static final String MOBILE_NUM_PARAM = Utils.getSingleValueAppConfig("MOBILE_NUM_PARAM");
		public static final String DRUPAL_REQ_PARAM = Utils.getSingleValueAppConfig("DRUPLA_PARAM");
		public static final String EQUALS_SYMBOL = "=";
		public static final String QUESTION_MARK_SYMBOL = "?";
		public static final String AMPERSAND_SYMBOL = "&";
		public static final String B2C_POLICY_WORDING_TAG = Utils.getSingleValueAppConfig("B2C_POLICY_WORDING_TAG");
		public static final String B2C_FETCH_POL_WORDING_METHOD = Utils.getSingleValueAppConfig("B2C_FETCH_POL_WORDING_METHOD");
		public static final String B2C_FETCH_POL_WORDING_METHOD_OLD = Utils.getSingleValueAppConfig("B2C_FETCH_POL_WORDING_METHOD_OLD");
		public static final String B2C_POL_WORDING_TAG = Utils.getSingleValueAppConfig("B2C_POL_WORDING_TAG");
		public static final String B2C_EMAIL_CLAIM_ID_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_CLAIM_ID_IDENTIFIER");
		public static final String B2C_EMAIL_POLICY_NO_IDENTIFIER = Utils.getSingleValueAppConfig("B2C_EMAIL_POLICY_NO_IDENTIFIER");
		public static final String B2C_EMAIL_CLAIMS_EMAILID = Utils.getSingleValueAppConfig("B2C_EMAIL_CLAIMS_EMAILID");
		public static final String B2C_EMAIL_GOLF_INSURANCE_EMAILID = Utils.getSingleValueAppConfig("B2C_EMAIL_GOLF_INSURANCE_EMAILID");
		public static final String B2C_EMAIL_PERSONAL_ACCIDENT_INSURANCE_EMAILID = Utils.getSingleValueAppConfig("B2C_EMAIL_PERSONAL_ACCIDENT_INSURANCE_EMAILID");
		/** B2C Email related default values end */
		
		public static final Integer HOME_BUILDING_RISK_TYPE = 1;
		public static final Integer HOME_BUILDING_RISK_CODE = 1;
		public static final Integer HOME_CONTENTS_RISK_CODE = 2;		
		public static final String TRAVEL_SEC_ID = "TRAVEL_SEC_ID";
		public static final int DAYDIVIDER = ( 1000 * 60 * 60 * 24 );
		public static final Integer HOME_RISK_SECTION_ID = Integer.valueOf(Utils.getSingleValueAppConfig("HOME_RISK_SECTION_ID"));
		public static final Integer HOME_INSURED_SECTION_ID = Integer.valueOf(Utils.getSingleValueAppConfig("HOME_INSURED_SECTION_ID"));
		public static final Integer TRAVEL_INSURED_SECTION_ID = Integer.valueOf(Utils.getSingleValueAppConfig("TRAVEL_INSURED_SECTION_ID"));
		public static final String B2C_USER = Utils.getSingleValueAppConfig( "B2C_USER");
		public static final String DEFAULT_B2C_AGGISNGED_TO = Utils.getSingleValueAppConfig( "DEFAULT_B2C_AGGISNGED_TO" );
		public static final String REFERRAL_MESSAGE = Utils.getSingleValueAppConfig( "REFERRAL_MESSAGE" );
		public static final String [] PAYMENT_SUCCESS_CODES = Utils.getMultiValueAppConfig("SUCCESS_RESPONSE_CODES", ",");

		public static final Integer B2C_USER_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "USER_10" ) );
		public static final String INVALID_QUOTE = Utils.getSingleValueAppConfig( "INVALID_QUOTE" );
		public static final String INVALID_EMAIL = Utils.getSingleValueAppConfig( "INVALID_EMAIL" );
		public static final String NO_RESULT_QUOTE_AND_MOBILE_NUMBER = Utils.getSingleValueAppConfig( "NO_RESULT_QUOTE_AND_MOBILE_NUMBER" );
		public static final Integer QUOTE_VALID_DAYS = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_VALID_DAYS" ) );
		public static final Integer DEFAULT_CUSTOMER_CITY = Integer.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CUSTOMER_CITY" ) );
		public static final Integer DEFAULT_CUSTOMER_COUNTRY = Integer.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CUSTOMER_COUNTRY" ) );
		
		//B2C Renewal 
		public static final String COVER_RENDERER = "coversRenderer";
		public static final int B2C_ALLOWED_MAX_NAME_LENGTH = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_ALLOWED_MAX_NAME_LENGTH"));
		public static final int B2C_ALLOWED_POBOX_LENGTH = Integer.valueOf(Utils.getSingleValueAppConfig("B2C_ALLOWED_POBOX_LENGTH"));
		
		public static final int LOOKUP_DUBAI_OPTION_CODE = Integer.valueOf(Utils.getSingleValueAppConfig("LOOKUP_DUBAI_OPTION_CODE"));
		public static final String HOME_FIRST_NAME_DUMMY = Utils.getSingleValueAppConfig( "HOME_FIRST_NAME_DUMMY" );
		public static final String HOME_LAST_NAME_DUMMY = Utils.getSingleValueAppConfig( "HOME_LAST_NAME_DUMMY" );
		public static final String HOME_PO_BOX_DUMMY = Utils.getSingleValueAppConfig( "HOME_PO_BOX_DUMMY" ) ;
		public static final String TRAVEL_FIRST_NAME_DUMMY = Utils.getSingleValueAppConfig( "TRAVEL_FIRST_NAME_DUMMY" );
		public static final String B2C_FETCH_QUOTE_HOME_RENEWAL_METHOD = Utils.getSingleValueAppConfig("B2C_FETCH_QUOTE_HOME_RENEWAL_METHOD");
		public static final String B2C_FETCH_QUOTE_TRAVEL_RENEWAL_METHOD = Utils.getSingleValueAppConfig("B2C_FETCH_QUOTE_TRAVEL_RENEWAL_METHOD");
		public static final String REN_QUOTE_NUM_REQ_PARAM = Utils.getSingleValueAppConfig("REN_QUOTE_NUM_REQ_PARAM");
		public static final String MORTGAGE_OTHERS_CODE = Utils.getSingleValueAppConfig( "MORTGAGE_OTHERS_CODE" );
		public static final String CHAT_LINK = Utils.getSingleValueAppConfig( "CHAT_LINK" );
		public static final String NO_NAME_STRING = Utils.getSingleValueAppConfig( "NO_NAME_STRING" );
		public static List<String> QUOTE_EDITABLE_STATUS = Arrays.asList(Utils.getMultiValueAppConfig("QUOTE_EDITABLE", ",") );

		
		public static final Integer REN_QUOTE_VALID_DAYS = Integer.valueOf( Utils.getSingleValueAppConfig( "REN_QUOTE_VALID_DAYS" ) );
		
		public static final int NO_OF_DAYS_LEAP_YEAR = 366;
		public static final int NO_OF_DAYS_YEAR = 365;
		public static final Integer EMIRATES_HOME_TARIFF=Integer.valueOf( Utils.getSingleValueAppConfig("EMIRATES_SCH_CODE") );
		public static final String B2C_DEFAULT_BROKER_FROM_EMAILID = Utils.getSingleValueAppConfig("B2C_DEFAULT_BROKER_FROM_EMAILID");

		//Oman location code
		public static final String LOCATION_CODE = "30";
		public static final int TRAVEL_PERIOD = 181;
		public static final String SCHENGEN_TARIFF = "210";
		public static final String PERSONAL_ACCIDENT_COVER = "Personal Accident";
		public static final String TRAVEL_TYPE = "Worldwide excluding USA and Canada";
		public static final String ACCIDENT_TYPE_POLICE_REPORT = "ROP";
		public static final String ACCIDENT_TYPE_MRTA_FORM = "MRTA";
		public static final String ONLINE_MEDIA = "101";
		
		// Oman d2C: T_MAS_PARTNER_MGMT columns
		public static final String OMAN_D2C_TRAVEL = "OmanD2CTravel";
		public static final String D2C_OMAN_TRAVEL_QUOTE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("D2C_OMAN_TRAVEL_QUOTE_EMAIL_SUBJECT");
		public static final String D2C_HOME_INSURANCE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("D2C_HOME_INSURANCE_EMAIL_SUBJECT");
		public static final String D2C_FNOL_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("D2C_FNOL_EMAIL_SUBJECT");
		public static final String RENEWAL_QUOTE_NUM_REQ_PARAM = "renQuote";
		public static final String PROMO_CODE_REQ_PARAM = "promocode";
		public static final String D2C_OMAN_TRAVEL_POLICY_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("D2C_OMAN_TRAVEL_POLICY_EMAIL_SUBJECT");
		public static final String D2C_NOTIFICATION_MSG = "NOTIFIACATION_MSG_B2C";
		
		//Added CC for Oman D2C
		public static final String B2C_FNOL_CC_EMAILID = Utils.getSingleValueAppConfig("B2C_FNOL_CC_EMAILID");
		public static final String B2C_GOLF_INSURANCE_SUBMIT_CC_EMAILID = Utils.getSingleValueAppConfig("B2C_GOLF_INSURANCE_SUBMIT_CC_EMAILID");
		public static final String B2C_GOLF_INSURANCE_NOTIFICATION_CC_EMAILID = Utils.getSingleValueAppConfig("B2C_GOLF_INSURANCE_NOTIFICATION_CC_EMAILID");
		public static final String B2C_EMAIL_FNOL_TO_EMAIL_ID = Utils.getSingleValueAppConfig("B2C_EMAIL_FNOL_TO_EMAIL_ID");
		public static final String FNOL_DRUPAL = "fnol_drupal";
		public static final String HOME_LEAD_DRUPAL = "home_lead_drupal";
		public static final String B2C_EMAIL_CLAIMS_TO_EMAILID = Utils.getSingleValueAppConfig("B2C_EMAIL_CLAIMS_TO_EMAILID");
		public static final String B2C_EMAIL_CLAIMS_FROM_EMAILID = Utils.getSingleValueAppConfig("B2C_EMAIL_CLAIMS_FROM_EMAILID");
		public static final String B2C_REMINDER_CC_EMAILID = Utils.getSingleValueAppConfig("B2C_REMINDER_CC_EMAILID");
		
		
		public static final String B2C_TRAVEL_SCOPE_TRAVEL_STRING="Travel";
		public static final String B2C_TRAVEL_SCOPE_COVER_STRING="COVERS";
		public static final String B2C_TRAVEL_SCOPE_SELECT_STRING="Select";
		public static final String B2C_TRAVEL_SCOPE_RECOMMENDED_STRING="Recommended";
		public static final String B2C_TRAVEL_SCOPE_MORE_STRING="View More";
		public static final String HOLIDAYTRAVEL_TARIFF="212";
		public static final String EXECUTIVETRAVEL_TARIFF="211";
		
		//VAT
		public static final String LOCATION_CODE_DUABI = "20";
		public static final String B2C_EMAIL_VAT_INCL_TEXT = "(incl. VAT)";
		
		//WEB-Services
		public static final String DSC = "Domestic Staff Cover";
		public static final String LOD = "Loss of Documents";
		public static final String ATLC = "Additional Tenants Liability Cover";
		
		//Added for Email Renewal Notice : Ticket 165419
		public static final String RENEWAL_HOME_CONTENT = Utils.getSingleValueAppConfig( "RENEWAL_HOME_CONTENT" );
		public static final String RENEWAL_TRAVEL_CONTENT = Utils.getSingleValueAppConfig( "RENEWAL_TRAVEL_CONTENT" );
		public static final String RENEWAL_NOTICE_PRODUCT = Utils.getSingleValueAppConfig("RENEWAL_NOTICE_PRODUCT");
		public static final String RENEWAL_NOTICE_TEMPLATE=Utils.getSingleValueAppConfig("RENEWAL_NOTICE_TEMPLATE");
		public static final String RENEWAL_NOTICE_TEMPLATE_LEGACY=Utils.getSingleValueAppConfig("RENEWAL_NOTICE_TEMPLATE_LEGACY");
		public static final String RENEWAL_TRAVEL_NOTICE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("RENEWAL_TRAVEL_NOTICE_EMAIL_SUBJECT");
		public static final String RENEWAL_HOME_NOTICE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("RENEWAL_HOME_NOTICE_EMAIL_SUBJECT");
		public static final String RENEWAL_WC_NOTICE_EMAIL_SUBJECT = Utils.getSingleValueAppConfig("RENEWAL_WC_NOTICE_EMAIL_SUBJECT");
		public static final String RENEWAL_NOTICE_EMAIL_INSURED_NAME = Utils.getSingleValueAppConfig("RENEWAL_NOTICE_EMAIL_INSURED_NAME");
		public static final Integer PACKAGRED_HOME_TARIFF=Integer.valueOf( Utils.getSingleValueAppConfig("PACKAGRED_HOME_TARIFF") );
		public static final Integer STANDARD_HOME_TARIFF=Integer.valueOf( Utils.getSingleValueAppConfig("STANDARD_HOME_TARIFF") );
		public static final String RENEWAL_NOTICE_LOB = Utils.getSingleValueAppConfig("RENEWAL_NOTICE_LOB");
		public static final String EMAIL_HOME_PACKAGED_TEXT = Utils.getSingleValueAppConfig("EMAIL_HOME_PACKAGED_TEXT");
		public static final String EMAIL_FROM_RENL_NOTICE = Utils.getSingleValueAppConfig("EMAIL_FROM_RENL_NOTICE");
		public static final String HOME_TRAVEL_EMAIL_CONTENT = Utils.getSingleValueAppConfig("HOME_TRAVEL_EMAIL_CONTENT");
	}

