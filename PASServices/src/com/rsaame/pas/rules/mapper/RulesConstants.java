/**
 * 
 */
package com.rsaame.pas.rules.mapper;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1014739
 *
 */
public class RulesConstants{

	/** Constant for identifying the Rules Engine URL from properties file */
	public static final String RULES_SERVICE_URL = "RULES_SERVICE_URL";

	/** Three letter codes defined in Rules Engine */
	public static final String GENERAL = "GEN";
	public static final String PROP_ALL_RISK = "PAR";
	public static final String MONEY = "MON";
	public static final String PUBLIC_LIAB = "PLI";
	public static final String WORK_COMP = "WCO";
	public static final String ENDORSEMENT = "END";
	public static final String RECEIPT = "RCT";
	public static final String PREMIUM_PAGE = "PRP";
	public static final String DISCOUNT_LOADING = "DISC";
	public static final String MACHINE_BD = "MBD";
	public static final String ELEC_EQUIP = "EEQ";
	public static final String TRAVEL_BAGGAGE = "TRL";
	public static final String BUS_INT = "BIN";
	public static final String GOODS_IN_TRANSIT = "GIT";
	public static final String GROUP_PA = "GPA";
	public static final String DOS = "DOS";
	public static final String FID = "FID";
	public static final String REN = "REN";
	public static final String QUO = "QUO";
	public static final String CHK_EFF_DATE = "CTP";
	public static final String CREDIT_LIMIT = "CRL";
	public static final String RIS = "RIS";
	
	/** Constants for identifying the Key in Rules Engine */
	public static final String COUNTRY = "UAE";
	public static final String LOCATION = "LOC";
	public static final String CLAZZ = "CLASS02";
	public static final String POLICY_TYPE = "PT";
	public static final String TARIFF = "TARIFF";
	public static final String CLASS = "CLASS"; //PHASE -3 Added
	public static final String TRAVEL_TARIFF_CODE = Utils.getSingleValueAppConfig("TRAVEL_TARIFF_CODE");
	public static final String RESTFUL_RULE_DEPLOYED_LOCATION = Utils.getSingleValueAppConfig("RULE_DEPLOYED_LOC");

	/** A constant representing a configuration to skip rules execution. */
	public static final String RULES_EXEC_NOT_REQ = "NOT_REQUIRED";
	/** Constant for defining date format */
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	/** Constant for defining decimal format */
	public static final String DECIMAL_FORMAT = "0.00";

	/** Fact names defined in Rules Engine */
	public static final String FACT_USER = "User";
	public static final String FACT_PROPERTY = "Property";
	public static final String FACT_SI = "SI";
	public static final String FACT_MONEY = "Money";
	public static final String FACT_PL = "PL";
	public static final String FACT_WC = "WC";
	public static final String FACT_GENERAL = "General";
	public static final String FACT_GENERAL_HOME = "GeneralHome";
	public static final String FACT_GENERAL_TRAVEL = "GeneralTravel";
	public static final String FACT_VALIDATION = "Validation";
	public static final String FACT_COMMON = "Common";
	public static final String FACT_ENDORSEMENT = "Endorsement";
	public static final String FACT_RECEIPT = "Receipt";
	public static final String FACT_PREMIUMPAGE = "PremiumPage";
	public static final String FACT_PREMIUMPAGEOBJ = "PremiumPageObject";
	public static final String FACT_EXTRA = "Extra";
	public static final String FACT_REFUND = "Refund";
	public static final String FACT_NIL = "Nil";
	public static final String FACT_ENDORSEMENT_DATING = "EndorsementDating";	
	public static final String FACT_CANCELLATION_DATING = "CancellationDating";	
	public static final String FACT_CANCEL_ENDORSEMENT = "CancelEndorsement";
	public static final String FACT_DISCOUNTLOADING = "DiscountLoading";
	public static final String FACT_MB = "MB";
	public static final String FACT_EEQ = "EEQ";
	public static final String FACT_TRAVEL = "Travel";
	public static final String FACT_BI = "BI";
	public static final String FACT_GIT = "GIT";
	public static final String FACT_GPA = "GPA";
	public static final String FACT_DOS = "DOS";
	public static final String FACT_REN = "Renewal";
	public static final String FACT_FID = "FID";
	public static final String FACT_PAR_UWQUESTION = "PARUWQuestion";
	public static final String FACT_PL_UWQUESTION = "PLUWQuestion";
	public static final String FACT_FG_UW_Question = "FGUWQuestion";
	public static final String FACT_DOS_UW_Question = "DOSUWQuestion";
	public static final String FACT_MB_UW_Question = "MBUWQuestion";
	public static final String UWQUESTION_ATTR_NAME_PREFIX = "UWQ_SEC";
	public static final String FACT_EDIT_QUOTE = "EditQuote";
	public static final String FACT_CLAIMS_ENDORSEMENT = "ClaimsEndorsement";	
	public static final String FACT_QUOTE_PREMIUM = "QuotePremium";
	public static final String CONV_TO_POLICY = "ConvToPolicy";
	public static final String FACT_CREDIT_LIMIT = "CreditLimit";
	public static final String ISSUE_QUO_REF = "IssueQuoteRef";
	//Added for JLT
	public static final String FACT_CUMULATIVE_LOSS_QUANTUM = "CumulativeLossQuantum";
	
	/**Constants for PL Section */
	public static final String SUM_INSURED_BASIS_UNLTD = "PL_SUM_INSURED_BASIS_UNLIMITED_CODE";

	/**Constants for General Section */
	public static final String CITY = "CITY";
	public static final String NATIONALITY = "NATIONALITY";

	/** Constants for calling commission service */
	public static final String GET_COMMISSION = "GET_COMMISSION";

	/** Constants for fetching class codes from appconfig.properties */
	public static final String PAR_CLASS = "PAR_CLASS";
	public static final String MONEY_CLASS = "MONEY_CLASS";
	public static final String WC_CLASS = "WC_CLASS";
	public static final String PL_CLASS = "PL_CLASS";
	public static final String BI_CLASS = "BI_CLASS";
	public static final String MB_CLASS = "MB_CLASS";
	public static final String EE_CLASS = "EE_CLASS";
	public static final String TB_CLASS = "TB_CLASS";
	public static final String GPA_CLASS = "GPA_CLASS";
	public static final String GIT_CLASS = "GIT_CLASS";
	public static final String DOS_CLASS = "DOS_CLASS";
	public static final String FID_CLASS = "FIDELITY_CLASS";
	public static final String PAR_SECTION_ID = Utils.getSingleValueAppConfig( "PAR_SECTION" );
	public static final String PL_SECTION_ID = Utils.getSingleValueAppConfig( "PL_SECTION" );
	public static final String WC_SECTION_ID = Utils.getSingleValueAppConfig( "WC_SECTION" );
	public static final String MONEY_SECTION_ID = Utils.getSingleValueAppConfig( "MONEY_SECTION" );
	public static final String FID_SECTION_ID = Utils.getSingleValueAppConfig( "FIDELITY_SECTION" );
	public static final String MB_SECTION_ID = Utils.getSingleValueAppConfig( "MB_SECTION" );
	public static final String DOS_SECTION_ID = Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_SECTION" );
	
	
	

	/** Constants for lookup */
	public static final String LOOKUP_LEVEL1 = "ALL";
	public static final String LOOKUP_LEVEL2 = "ALL";
	public static final String WC_PL_LIMIT = "WC_PL_LIMIT";
	public static final String PAS_LIMIND = "PAS_LIMIND";
	public static final String UWQ_REF_MSG_LKP_CATEGORY = Utils.getSingleValueAppConfig( "UWQ_REF_MSG_LKP_CATEGORY" );
	public static final String POLICY_FEE = "POLICY_FEE";
	
	public static final String RISK_ID_GENERAL = "RISK_ID_GENERAL";
	public static final String RISK_ID_ENDORSEMENT = "RISK_ID_ENDORSEMENT";
	public static final String RISK_ID_RECEIPT = "RISK_ID_RECEIPT";
	public static final String RISK_ID_PREMIUMPAGE = "RISK_ID_PREMIUMPAGE";
	public static final String RISK_ID_DISC_LOAD = "RISK_ID_DISC_LOAD";
	public static final String RISK_ID_RENEWAL = "RISK_ID_RENEWAL";
	public static final Integer REN_TRAN_TYPE = 6;
	
		public static final String RISK_ID_EDIT_QUOTE = "RISK_ID_EDIT_QUOTE";
	/* Imp Note : This flag is added as temporary fix and this has to be removed when actual fix is implemented */
	public static final String UWQ_RULES_ENABLE = Utils.getSingleValueAppConfig( "UWQ_RULES_ENABLE" );
	public static final String TRAVEL_FACT_NAME = "PersonalTravel";
	
	public static final String COND = "COND";
	public static final String CONDITIONS = "Conditions";
}
