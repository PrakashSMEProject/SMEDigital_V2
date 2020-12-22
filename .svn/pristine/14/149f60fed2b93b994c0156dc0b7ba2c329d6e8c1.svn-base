package com.rsaame.kaizen.framework.constants;

import java.io.File;
import java.math.BigDecimal;


/**
 * This constants interface will define all the common constants. ********
 * 
 * @author Cognizant
 */
public interface AMEConstants {

	String ACEGI_SECURITY_CONTEXT = "ACEGI_SECURITY_CONTEXT";

	String CTX_LOCATION = "LOCATION";
	
	String CTX_CUSTOMER = "CUSTOMER"; // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0) - Added for Check Existing Customer 
	
		
	// ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0) - Added for defaulting
	String CTX_LOGIN_LOCATION = "LOGIN_LOCATION";

    String SESSION_USER = "USER";
    
    String SESSION_MULTIBRANCH_IND="MUL_BRNCH_IND";

    //Release 4.2 17.07.2011 Oman Border Implementation in Kaizen
    String SESSION_OMANBORDER_IND="OMN_BRD_IND";
    
    //Release 4.2 25.07.2011 Oman Border Implementation in Kaizen
    
    String SESSION_LOGIN_LANGUAGE = "LANGUAGE";
//  Release 4.2 25.07.2011 Oman Border Implementation in Kaizen
    String CTX_LANGUAGE = "A";

	char PATH_SEPARATOR = File.separatorChar;

	String PATH_MAPPING_FILES = "/config/castormappings/";

	String AME_BEANS_CONFIG = PATH_SEPARATOR + "config" + PATH_SEPARATOR
			+ "spring" + PATH_SEPARATOR + "ame-beans.xml";

	String MAIL_TIMER = "mail.timer";

	String HOUR = "hourOfDay";

	String MINUTE = "minute";

	String SECOND = "second";

	String NEW_LINE = "\n";

	String ACT_CREAT_POL = "POLICY";

	String SERVICE_CTX_USER = "USER";

	String QUERY_PROPERTIES = "/com/rsaame/kaizen/properties/Queries.properties";

	String QUOTE_SELECT_QUERY = "selectQuoteQuery";

	String POLICY_SELECT_QUERY = "selectPolicyQuery";

	String ACCOUNTS_QUERY = "accQuery";

	String ACTIVE_QUOTE_QUERY = "selectActiveQuoteQuery";

	String ACTIVE_POLICY_QUERY = "selectActivePolicyQuery";

	String CUSTOMER_SELECT_QUERY = "selectCustomerQuery";
	
	String CUSTOMER_SELECT_QUERY_QUOTE = "selectCustomerQueryForQuote";

	String CUSTOMER_VIEW_QUERY = "customerViewQuery";

	String AUTHENTICATION_QUERY = "authenticationQuery";

	String CASH_CUSTOMER_VIEW_QUERY = "cashCustomerViewQuery";

	String COVERS_QUERY = "coversQuery";
	
	static final String HIDDEN_COVERS_QUERY = "hiddenCoversQuery";
	
	String COVERS_QUERY_ENDT = "coversQueryEndt";

	String CATEGORY_QUERY = "categoryQuery";

	String GET_USER_DETAIL = "selectUserDetailQuery";

	String GET_PRIVILEGE = "selectPrivilegeQuery";

	String QUERY_MAIL_TYPE = "selectMailTypeQuery";

	String QUERY_UPDATE_STATUS = "updateStatus";

	String QUERY_DELETE_MAILS = "deleteMail";

	String QUERY_MAILS = "selectMailsQuery";

	String COLON_CRITERIA = ":";

	String QUERY_CRITERIA_AND = " AND ";

	String CONSTRUCT_GETTER = "get";

	String LETTER_A = "A";

	String LETTER_Y = "Y";
	
	String LETTER_N = "N";

	String LETTER_D = "D";

	String DOT = ".";
	
	String SPACE_ONE_CHAR = " ";

	String YEAR = "Year";
	
	String MAIL_TYPE_RENEWALS = "1";
	
	public static final int PARAM_TWELVE = 12;
	
	public static final int PARAM_FOUR = 4;

	static final String CTX_GET_CUSTOMER_DETAILS_VIEW = "getCustomerDetailsView(CustomerInsured customerInsured)";

	String CTX_CHECK_INSURED_CUSTOMER_DETAILS = "searchForExistingCustomer(CustomerInsured customerInsured)";

	String CTX_SEARCH_EXISTING_CUSTOMER = "searchForExistingCustomerWeb(CustomerInsured customerInsured)";

	String CUSTOMER_CHECK_QUERY = "customerCheck";

	final String SUCCESS = "Data Saved Successfully";

	String CUSTOMER_VERIFY_QUERY = "customerVerifyQuery";

	static final String FAILURE = "Failed to Save Data";

	static final String CTX_POLICY = "updatePolicy(Policy policy)";
	
	static final String CTX_SAVE_INSURED_CUSTOMER_DETAILS = "saveInsuredCustomerDetails(CustomerDetailsBO customerDetailsBO)";

	static final String CTX_SAVE_DETAILS = "saveDetails(CustomerDetailsBO customerDetailsBO)";

	static final String CTX_CUSTOMER_LIST_FOR_SEARCH = "getCustomerListForSearch(CustomerBO customerObject)";

	static final String CTX_CUSTOMER_LIST_FOR_VIEW = "getCustomerListForView(CustomerDetailsBO customerviewObject)";

	static final String CTX_SAVE_CASH_CUSTOMER_DETAILS = "saveCashCustomerDetails()";

	static final String CTX_VIEW_QUOTE = "viewQuote(ViewQuoteBO viewQuoteBO)";

	static final String CTX_GET_LIST_FOR_SEARCH_TRANSACTION = "getQuoteListForSearchTransaction(transactionObject)";

	static final String CTX_VIEW_QUOTE_PAGE_ONE = "viewQuotePageOne(ViewQuoteBO viewQuoteBO)";

	static final String CTX_VIEW_QUOTE_PAGE_TWO = "viewQuotePageTwo(ViewQuoteBO viewQuoteBO)";

	static final String CTX_VIEW_QUOTE_PAGE_THREE = "viewQuotePageThree(ViewQuoteBO viewQuoteBO)";

	static final String CTX_GET_RESULT_POLICY_LIST = "getResultPolicyList()";

	static final String CTX_GET_RESULT_QUOTE_LIST = "getResultQuoteList()";

	static final String CTX_VERIFY_CUSTOMER = "verifyCustomer(PolicyQuo policyQuo) ::";

	static final String DOES_EXCEED_FINANCIAL_LIMIT = "doesExceedFinancialLimit(Policy policy,GlAccInterface glAccInterface)";

	static final String SAVE_QUOTE_DETAILS = "saveQuoteDetails(PolicyQuo quote)";

	static final String CTX_GET_GL_ACCOUNT_INFO = "getglAccountInfo(Integer gatCode)";

	static final String CTX_IS_DESCRIPTION = "getGlDescription(Integer countryCode,Integer regionCode,Integer locationCode,Integer ccCode,BigDecimal tolAccCode,Long glCode)";

	static final String CTX_IS_CODE_PRESENT = "isGlCodePresent(Integer countryCode,Integer regionCode,Integer locationCode,Integer ccCode,BigDecimal tolAccCode,Long glCode)";

	static final String CTX_ROLE_COUNT = "getRoleCount()";

	static final String CTX_ROLE_NAME = "getRoleName(String roleName,Integer costCode,BigDecimal totAccCode,Long glcode,BigDecimal amount)";

	static final String CTX_SAVE_QUOTATION_DETAILS = "saveQuotationDetails(PolicyQuo quote)";

	static final String CTX_SAVE_POLICY_DETAILS = "savePolicyDetails(Policy policy)";

	static final String CTX_SAVE_COVERAGE_DETAILS = "saveCoverageDetails(MotorPolicyRating motorPolicyRating)";

	static final String GET_GL_DESCRIPTION = "getGlDescription";

	static final String BROKER_SELECT_QUERY = "getBrokerQuery";

	static final String IS_GL_CODE_PRESENT = "isGlCodePresent";

	String QUOTE_ERATER_CITY = "City";       	// Ticket 10215 - 13mnth free offer for New policies

	static final String GET_ROLE_NAME = "getRoleName";

	static final String CTX_IS_GL_ACCOUNT_AVAILABLE = "isGLAccountAvailable(PolicyQuo policyQuo)";

	String CITY_SELECT_QUERY = "citySelectQuery";

	static final String CTX_IS_VALID_GL_ACCOUNT = "isValidGLAccount(int gatCode,PolicyQuo policyQuo)";

	static final String CTX_IS_PREMIUM_CALCULATED = "isPremiumCalculated(Policy policy)";

	static final String CTX_SAVE_PREMIUM_DETAILS = "savePremiumDetails()";

	static final String CTX_IS_VALID_AUTHORIZATION = "isValidAuthorization()";

	static final String CTX_VALIDATE_SCHEME = "validateScheme(PolicyQuo policy)";

	static final String SAVE_PAYMENT_DETAILS = "savePaymentDetails(PolicyQuo quote)";

	static final String SCHEME_ADVANTAGEPLUS = "1";

	static final String GET_CHASSIS_NUMBER = "getChassisNumber";

	static final String CTX_COMPARE_COVERS_AND_SAVE_PREMIUM = "compareCoversAndSavePremium";

	String CTX_COMPARE_COVERS = "compareCovers";

	static final String CTX_WEB_CUSTOMER_ID = "1";

	static final String CTX_SAVE_PERSONAL_EFFECT_DETAILS = "savePersonalEffects(ArrayList personalEffectList)";

	static final String CTX_SAVE_PERSON_DETAILS = "savePerson(ArrayList PersonalDetailsForAcc)";

	static final String CTX_SAVE_OTHER_RISK = "saveOtherRisk(PolicyQuo policyQuo)";

	static final String WEB_REGN_MAIL_TYPE = "001";

	static final String WEB_REGN_TRN_ID = "RegWebCustomer";

	static final String CTX_INSERT_INTO_POLICY = "insertPolicyFromQuo";

	static final String CTY_CODE = "ctyCodeGl";

	static final String REG_CODE = "regCodeGl";

	static final String LOC_CODE = "locCodeGl";

	static final String CC_CODE = "ccCodeGl";

	static final String TOTAL_ACC = "totalAcc";

	static final String GL_CODE = "glCode";

	/** Constants Added for the generation of sequence number */
	static final String SEQ_DRIVER_ID = "SEQ_DRIVER_ID";

	static final String SEQ_VEHICLE_ID = "SEQ_VEHICLE_ID";

	static final String SEQ_POLICY_ID = "SEQ_POLICY_ID";

	static final String SEQ_QUOTATION_ID = "SEQ_QUOTATION_ID";

	static final String SEQ_CREDIT_NOTE_NO = "SEQ_CREDIT_NOTE_NO";

	static final String SEQ_DEBIT_NOTE_NO = "SEQ_DEBIT_NOTE_NO";
	
	/** constant related to IsValidAuthorizatinSave.java */

	/** constant for Discount Gradient */
	static final String DISCOUNT_VALUE_GRADIENT = "DiscountValueGradient";

	/** constant for Discount percent Gradient */
	static final String DISCOUNT_PERCENT_GRADENT = "DiscountPercentGradient";

	/** constant for commission Gradient */
	static final String COMMISSION_GRADIENT = "CommissionGradient";

	/** constant for Issue Date Gradient */
	static final String ISSUE_DATE_GRADIENT = "IssuedateGradient";

	/** constant for Effective Date Gradient */
	static final String EFFECTIVE_DATE_GRADIENT = "Effectivedategradient";

	/** constant for Authorization Save */
	static final String AUTHORIZATION_SAVE = "AuthorizationSave";

	/** constant for Approve Quote */
	//static final Integer APPROVE_QUOTE = new Integer(23);
	static final Integer APPROVE_QUOTE =Integer.valueOf(23);
	
	//sonar violation fix on 20-9-2017
	/** constant for Refer Quote */
	static final Integer REFERRED_QUOTE = Integer.valueOf(20);

	//sonar violation fix on 21-9-2017
	/** constant for Decline Quote */
	static final Integer DECLINE_QUOTE = Integer.valueOf(22);
	
	static final int ZERO = 0;
	
	static final String DEFAULT_TIME = "00";
	
	static final String BLANK_VALUE = "";

	
	/** constant for Class Code */
	static final String CLASS_CODE = "Classcode";

	/** constant for Discount Value */
	static final String DISCOUNT_VALUE = "DiscountValue";

	/** constant for Discount Percent */
	static final String DISCOUNT_PERCENT = "DiscountPercent";

	/** constant for Role */
	static final String ROLE = "Role";

	/** constant for commission */
	static final String COMMISSION = "Commission";
	
	//sonar violation fix on 26-9-2017
	static final Integer COMMISSION_VAL = Integer.valueOf(2);

	/** constant for Issue Date postponed by */
	static final String ISSUE_DATE_POSTPONED_BY = "issuedatepostdatedby";

	/** constant for Effective date postponed by */
	static final String EFFECTIVE_DATE_POSTPONED_BY = "effectivedatepostdatedby";

	/** constant for Effective back dated by */
	static final String EFFECTIVE_DATE_BAKDATED_BY = "effectivedatebackdatedby";

	/** constant for Difference factor percent */
	static final String DIFFERENCE_FACTOR_PERCENT = "DifferenceFactorPercent";

	/** constant for Difference factor Gradient */
	static final String DIFFERENCE_FACTOR_GRADIENT = "DifferenceFactorGradient";

	/***/
	static final String DOCUMENT_TYPE_CODE = "DocumentTypeCode";

	/** constant for boolean value TRUE */
	static final String TRUE = "true";

	/** constant for boolean value FALSE */
	static final String FALSE = "false";
	
	static final boolean DEFAULT_FALSE = false;
	
	static final boolean DEFAULT_TRUE = true;
	
	static int PNCD_COVER_CODE = 11;
	
	static final int STATUS_FOR_TASK = 1;
	
	//ADM CR63 Referral - Changing the default value for task from Custom to Referral
	//static final int CATEGORY_FOR_TASK = 2;
	static final int CATEGORY_FOR_TASK = 4;

	static final String SEQ_ENDORSEMENT_ID = "SEQ_ENDORSEMENT_ID";

	static final String SEQ_CUSTOMER_ID = "SEQ_CUSTOMER_ID";

	static final String SEQ_RECEIPT_NO = "SEQ_RECEIPT_NO";

	static final String SEQ_INSURED_ID = "SEQ_INSURED_ID";
	
	static final int DIRECT_CALL_CENTER = 8;
	
	static final int DIRECT_WALK_IN = 9;
	
	static final int DIRECT_OTHERS = 11;
	
	static final int AFFINITY_DIRECT = 6;
	
	static final int AGENCY_REPAIR = 4;
	
	static final int DAYS_DIFF = -60;
	
	static final String DEFAULT_LANG = "E";

	/** Constants for Erater */
	String QUOTE_LOB = "LOB";
	
	String QUOTE_LOB_VALUE = "LobValue";

	String QUOTE_STATE = "State";

	String QUOTE_COMPANY = "Company";

	String QUOTE_PLAN = "Plan";

	String QUOTE_PRODUCT = "Product";

	String QUOTE_SERVICE = "Service";

	String QUOTE_DEBUG_INDEX = "DebugIndex";

	String QUOTE_AVAILABLE_DATE = "AvailbaleDate";

	String QUOTE_EFFECTIVE_DATE = "EffectiveDate";

	String QUOTE_PASS = "Password";     //SONARFIX -- changed from QUOTE_PASSWORD to QUOTE_PASS

	String QUOTE_CALLER_ID = "CallerId";
	
	String QUOTE_SERVICE_ID = "getRates";
	
	String CALCULATE_PREMIUM = "calculatePremiumMethod";
	
	String SET_POLICY_FOR_PREMIUM = "setPolicyForPremiumMethod";
	
	String SET_POLICY_FOR_DETAILS = "setPolicyForDetailsMethod";
	
	String GET_PREMIUM = "getPremiumMethod";
	
	String GET_CLSPEKEY_UTIL = "getCLSPEKey";
	
	String QUOTE_SOURCE_REGION = "SourceRegion";

	

	/** End of erater constants */

	String QUOTE_CUSTOMER_CATEGORY = "Category";
	
    String QUOTE_TRANSACTION_TYPE = "TransactionType";
    
    String QUOTE_GET_DETAILS = "getdetails";
    
    String QUOTE_GET_SERVICE_NAME = "GetServiceName";
    
    String QUOTE_GET_SOURCE_REGION = "erater.sourceregion";
    
    String QUOTE_TRUE = "true";
    
    String QUOTE_DEBUG_INDEX_VALUE = "DebugIndexValue";
    
    String QUOTE_ERATER = "erater";
    
    String QUOTE_ERATER_PASS = "EraterPasswordValue"; //SONARFIX -- changed from QUOTE_ERATER_PASSWORD to QUOTE_ERATER_PASS
    
    String QUOTE_CALLER_ID_VALUE = "CallerId";
    
    
    String QUOTE_ERATER_TRANSACTION_VALUE_NEW_BUSINESS = "TransactionValue.NewBusiness";
    
    String QUOTE_ERATER_TRANSACTION_VALUE_RENEWALS = "TransactionValue.Renewals";
    
    String QUOTE_ERATER_GENDER = "GenderName";
    
    String QUOTE_ERATER_DRIVER_EXP = "Drv Exp Others";
    
    String QUOTE_ERATER_LICENSE_LOADING = "License Loading";
    
    String QUOTE_ERATER_DRIVER_EXP_CONV = "Drv Exp UAE Convertible";
    
    String QUOTE_ERATER_DRIVER_EXP_UAE = "Drv Exp UAE";
    
    String QUOTE_ERATER_NUMBER_CLAIMS = "Number of claims";
    
    String QUOTE_ERATER_VEHICLE_USAGE = "Vehicle Usage";
    
    String QUOTE_ERATER_USER = "Business Type";
    
    String QUOTE_ERATER_NATIONALITY = "Nationality";
    
    String QUOTE_ERATER_OCCUPATION = "Occupation";
    

	String QUOTE_NATIONALITY = "Nationality";
    /** End of erater constants */
    String CTX_GET_DETAILS_FOR_POLICY = "getDetailsForPolicy()";

	String QUOTE_FACTOR_TRANSACTION_TYPE = "Transaction Type";

	String GET_EXCLUSIONS_QUERY = "standardExclusionsList";
    
    String GET_TRAN_CONDITIONS_QUERY="trnConditionQuery";
    
    String GET_TRAN_WARRANTY_QUERY="trnWarrantyQuery";
    
	String GET_TRAN_EXCLUSION_QUERY = "trnExclusionQuery";

	String GET_TRAN_NON_STD_TEXT_QUERY = "nonstdtextQuery";
	
	String GET_EXTRA_CONDITIONS_QUERY = "extraConditionsQuery";

	String GET_TRAN_CONDITIONS_QUERY_POL="trnConditionQueryForPolicy";
	
	String GET_TRAN_WARRANTY_QUERY_POL="trnWarrantyQueryForPolicy";
	
	String GET_TRAN_EXCLUSION_QUERY_POL="trnExclusionQueryForPolicy";

	String QUOTE_VEHICLE_CATEGORY = "Vehicle Category";
	
	String QUOTE_VEHICLE_AGE = "Vehicle Age";

	String QUOTE_VEH_SUM_INSURED = "Sum Insured";
	
	String CLAIM_LOADING = "Claimloading";

	String QUOTE_NCD = "Ncd Years";
	
//	ADM 21858-Place of Registration added as a Rating factor.-3.1 Patch
	String PLACE_OF_REG="PlaceofReg";
//	 Release 3.2 CR 21862-ANA Cover changes
	String LOSS_GPA_SI="LossGapSi";
	
	String QUOTE_NUMBER_CLAIMS = "Number of claims";

	String QUOTE_NO_OF_PASSENGERS = "No. Of Passengers";
	
	String QUOTE_ERATER_VEHICLE_MAKE= "Vehicle Make";
	
	String QUOTE_ERATER_VEHICLE_MODEL= "Vehicle Model";
	
	String QUOTE_ERATER_PERSONAL_EFFECTS_SUM_INSURED= "Personal  Effects Sum Insured";
	
	String QUOTE_ERATER_TARRIF= "Tariff";
	
	String QUOTE_ERATER_POLICY_TYPE= "Policy Type";
	
	String QUOTE_ERATER_POLICY_TERM= "Policy Term";
	
	String QUOTE_ERATER_TRANSACTION_TYPE= "Transaction Type";
	
	String QUOTE_ERATER_SEATS= "Seats";
	
	String QUOTE_ERATER_DRIVER_AGE= "Driver Age";
	
	String VOLUNTARY_EXCESS = "Voluntary Excess"; //CR71 Voluntary Excess Rel2.5.1
	
	String CTX_GET_PREMIUM_FOR_POLICY = "getPremiumForPolicy()";

	/** End of erater constants */

	String GET_CONDITIONS_QUERY = "standardConditionList";

	String GET_WARRANTIES_QUERY = "standardWarrantiesList";

	static final String CTX_GET_COVERS_LIST = "getCoversList()";

	// Constants for Quotation Number sequence

	String CTX_TRANSACTION_NUMBER = "TransactionNumber";

	// ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0) 
	String CTX_USER_ID = "UserId";

	String CTX_TRANSACTION_NUMBER_POLICY = "TransactionNumberPolicy";

	String CTX_TRANSACTION_BROKER_NAME = "TransactionBrokerName";

	String CTX_TRANSACTION_CUSTOMER_NAME = "TransactionCustomerName";
	
	String CTX_TRANSACTION_COMPANY_NAME = "TransactionCompanyName"; //Added by ADM 07.08.2009 for Ticket # 8185 to Add Company name

	String CTX_TRANSACTION_DISTRIBUTION_CODE = "TransactionDistributionCode";

	// Constants for Quotation Number sequence

	String CTX_TRANSACTION_FROM = "TransactionFrom";

	String CTX_TRANSACTION_TO = "TransactionTo";

	String CTX_TRANSACTION_CERTIFICATE_NO_FROM = "TransactionCertificateNumberFrom";

	String CTX_TRANSACTION_CERTIFICATE_NO_TO = "TransactionCertificateNumberTo";

	String CTX_TRANSACTION_SCHEME = "TransactionScheme";

	String CTX_TRANSACTION_EFFECTIVE_DATE = "TransactionEffectiveDate";

	String BUSINESSTYPE_VALUE = "Business Type Value"; // Ticket 10215 - 13mnth free offer for New policies 

	String CTX_TRANSACTION_EXPIRY_DATE = "TransactionExpiryDate";

	String CTX_TRANSACTION_LAST_MODIFIED_BY = "TransactionLastModifiedBy";

	String CTX_TRANSACTION_CREATED_BY = "TransactionCreatedBy";

	String CTX_TRANSACTION_STATUS = "TransactionStatus";

	String CTX_TRANSACTION_CLASS = "TransactionClass";

	String CTX_TRANSACTION_ENGINE_NO = "TransactionEngineNo";
	
	String CTX_TRANSACTION_CHASSIS_NO = "TransactionChassisNo";
	
	//String CTX_TRANSACTION_CERTIFICATE_NO = "TransactionCertificateNo";
	
	String CTX_TRANSACTION_REG_NO = "TransactionRegNo";
	
	String TRN_CLOSE_DETAILS = "TrnClosingDetails";
	
	
	/** Constant to hold quote for renewal quote. */
	public static final Integer RENEWAL_QUOTE_DOCUMENT_CODE = new Integer("6");
	
	public static final Integer ENDORSEMENT_DOCUMENT_CODE = new Integer("3");

	/** Constant to hold Class Code for MOTOR. */
	public static final Integer POLICY_CLASS_CODE_FOR_MOTOR = new Integer("1");

	/** Constant to hold Policy Document code which is new. */
	public static final Integer POLICY_DOCUMENT_CODE_NEW = new Integer("1");
	
	static final Integer DEFAULT_USER = Integer.valueOf(0);
	
	static final Integer DEFAULT_INTEGER_ZERO =Integer.valueOf(0);
	
	//Added Long.valueOf() to avoid sonar violation on 18-9-2017
	//static final Long DEFAULT_LONG_ZERO = new Long(0);
	static final Long DEFAULT_LONG_ZERO = Long.valueOf(0);
	
	static final Integer DEFAULT_INTEGER_ONE =Integer.valueOf(1);
	
	static final BigDecimal DEFAULT_BIGDECIMAL_ZERO = new BigDecimal(0.0);
	
	static final String STATUS_COMPLETE_STOP = "Complete";
	
	static final String STATUS_SOFT_STOP = "Soft";
	
	static final int FOUR = 4;
	
	static final int EIGHT = 8;
	
	static final int MINUS_ONE = -1;
	
	static final int TWO = 2;
	
	static final String SEPARATOR = "||";
	
	public static final int SOFT_STOP_CODE = 99;
	
	public static final int COMPLETE_STOP_CODE = 98;

	/** Constants for the quote status */
	Integer QUOTE_STATUS_PENDING = new Integer("6");

	Integer QUOTE_STATUS_ACTIVE = new Integer("1");
	
	//sonar violation fix on 20-9-2017 
	//Long QUOTE_ENDT_ID = new Long(0);
	Long QUOTE_ENDT_ID = Long.valueOf(0);
	
	int RENEWAL_DOCUMENT_CODE = 6;

	// Constants for Quotation Number sequence

	/** constant for Motor quotation number */
	String SEQ_MOTOR_QUO_NO = "SEQ_MOTOR_QUO_NO";

	/** constant for fire quotation number */
	String SEQ_FIRE_QUO_NO = "SEQ_FIRE_QUO_NO";

	/** constant for marine hull quotation number */
	String SEQ_MARINE_HULL_QUO_NO = "SEQ_MARINE_HULL_QUO_NO";

	/** constant for marine cargo quotation number */
	String SEQ_MARINE_CARGO_QUO_NO = "SEQ_MARINE_CARGO_QUO_NO";

	/** constant for GAcc quotation number */
	String SEQ_GACC_QUO_NO = "SEQ_GACC_QUO_NO";

	/** constant for WC TPL quotation number */
	String SEQ_WC_TPL_QUO_NO = "SEQ_WC_TPL_QUO_NO";

	/** constant for engineering quotation number */
	String SEQ_ENGINEERING_QUO_NO = "SEQ_ENGINEERING_QUO_NO";

	/** constants pertaining to Different Modes of Payment */

	/** constant for Payment mode by Cash */
	int PAY_BY_CASH = 1;

	/** constant for Payment mode by Cheque */
	int PAY_BY_CHEQUE = 2;

	/** constant for Payment mode by Demand Draft */
	int PAY_BY_DEMAND_DRAFT = 3;

	/** constant for Payment mode by Credit/Debit Card */
	int PAY_BY_CARD = 4;

	/** constant for Payment mode by Debit Account */
	int PAY_BY_DEBIT_ACCOUNT = 7;

	/** constant for GET_FINAL_PREMIUM */
	String GET_FINAL_PREMIUM = "getFinalPremium";

	/** constant for Policy Id */
	public static final String POLICY_ID = "POLICY_ID";
	
	/** constant for Policy Id */
	public static final String POLICY_NO = "POLICY_NO";
	

	// Constants for Endorsements Starts
	public static final String ENDT_ID = "ENDT_ID";
	
	public static final String ISSUE_DATE = "ISSUE_DATE";
	
	public static final Integer COUNTRY_CODE_OMAN = new Integer(1);
	
	public static final Integer COUNTRY_CODE_KSA = new Integer(2);
	
	public static final Integer COUNTRY_CODE_DXB = new Integer(3);
	
	public static final Integer COUNTRY_CODE_EGYPT = new Integer(4);
	
	public static final Integer COUNTRY_CODE_BAHRAIN = new Integer(5);
	
	// Tolerance Defined for the country
	
	public static final BigDecimal TOLERANCE_FOR_OMAN = new BigDecimal(0.1);
	
	public static final BigDecimal TOLERANCE_FOR_KSA = new BigDecimal(1);
	
	public static final BigDecimal TOLERANCE_FOR_DXB = new BigDecimal(1);
	
	public static final BigDecimal TOLERANCE_FOR_EGYPT = new BigDecimal(1);
	
	public static final BigDecimal TOLERANCE_FOR_BAHRAIN = new BigDecimal(0.1);
	
	// Constants for Endorsements Ends

	/** Constant to hold Class Code for MOTOR. */
	//public static final Integer POLICY_CLASS_CODE_FOR_MOTOR = new Integer(
	//		"1");
	/** Constant to hold Policy Document code which is new. */
	//public static final Integer POLICY_DOCUMENT_CODE_NEW = new Integer("1");
	public static final String RECORDS_PER_PAGE = "30";
	
	//DEFAULT DRIVER AGE TO 30 YEARS
	public static final String DEFAULT_DRV_AGE = "30";
	
	public static final Integer DEFAULT_DRIVER_AGE = new Integer(30);

	public static final String CURRENT_PAGE = "1";

	String CTX_VALIDATE_AND_SAVE_PAS = "validateAndSavePassword(CustomerInsured customerInsured)"; //SONARFIX -- 26-apr-2018 -- renamed from CTX_VALIDATE_AND_SAVE_PASSWORD to CTX_VALIDATE_AND_SAVE_PAS

	String CTX_IS_PAS_VALID = "isPasswordValid(CustomerInsured customerInsured)"; //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_IS_PASSWORD_VALID to CTX_IS_PAS_VALID

	String CTX_SAVE_NEW_PAS = "saveNewPassword(CustomerInsured customerInsured)"; //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_SAVE_NEW_PASSWORD to CTX_SAVE_NEW_PAS

	String CTX_VALIDATE_EMAIL_ID = "validateEMailID(CustomerInsured customerInsured)";

	String CTX_FETCH_HINT_QUESTION = "fetchHintQuestion(CustomerInsured customerInsured)";

	/** Constants for Email Properties */

	String EMAIL_PROPERTIES = "/com/rsaame/kaizen/properties/EMail.properties";

	String EMAIL_ID = "forgot.password.email.id";

	String EMAIL_SUBJECT = "forgot.password.email.subject";

	String EMAIL_TEXT = "forgot.password.email.message";

	String PAS_NOT_SAVED = "New Password was not saved in Database.";  //SONARFIX -- 25-apr-2018 -- changed from PASSWORD_NOT_SAVED to PAS_NOT_SAVED

	String INVALID_USERID_PAS = "Not a Valid User ID / Password."; //SONARFIX -- 25-apr-2018 -- changed from INVALID_USERID_PASSWORD to INVALID_USERID_PAS

	String INVALID_EMAIL_ID = "Invalid Email ID";

	String INVALID_ANSWER = "Invalid Hint Answer";

	String CASH_CUSTOMER = "1";

	String CREDIT_CUSTOMER = "2";

	String AGENT_NOT_AVAILABLE = "Agent is not available.";

	String BROKER_NOT_AVAILABLE = "Broker code is not available.";

	String SOB_NOT_AVAILABLE = "Source of Business is not available.";

	String COIN_NOT_AVAILABLE = "Coinsurance code is not available.";

	String GLCODE_NOT_AVAILABLE = "GlCode in GLAccInterface is not available.";

	String GL_LOCATION_NOT_AVAILABLE = "Location in GLAccInterface is not available.";

	String GL_REG_NOT_AVAILABLE = "Region in GLAccInterface is not available.";

	String GL_COUNTRY_NOT_AVAILABLE = "Country in GLAccInterface is not available.";

	String GL_CC_NOT_AVAILABLE = "CostCenter in GLAccInterface is not available.";

	String GL_TOTAL_ACC_NOT_AVAILABLE = "Total Acc Code in GLAccInterface is not available.";

	String POLICY_QUOTATION_NOT_AVAILABLE = "Policy Quotation is not available.";
	
	String QUOTATION_NUMBER_NOT_AVAILABLE = "Quotation number is not available.";
	
	String POLICY_NOT_AVAILABLE = "Policy is not available.";

	String CLASS_CODE_NOT_AVAILABLE = "Class Code is not available.";

	String CUSTOMER_ID_NOT_AVAILABLE = "Customer ID is not available.";

	String CUSTOMER_IS_NOT_AVAILABLE = "Customer is not available.";
	
	String INSURED_ID_NOT_AVAILABLE = "Customer Insured ID is not available.";

	String COUNTRY_CODE_NOT_AVAILABLE = "Country Code is not available.";

	String REGION_CODE_NOT_AVAILABLE = "Region Code is not available.";

	String LOCATON_CODE_NOT_AVAILABLE = "Location Code is not available.";

	String GET_GL_ACC_CODE = "getGlAccCode";

	String GET_TRANSACTION = "getTransaction";

	String CANNOT_MAKE_PAYMENT = "User Cannot make Payment by Debit Account Mode.";

	String INVALID_GL_ACC = "Not a valid GL Account Code.";

	String GET_HEADER_RECEIPT_DETAILS = "getHeaderReceiptDeatils(PolicyQuo policyQuo,HeaderReceipt headerReceipt)";

	String GET_DETAIL_RECEIPT_DETAILS = "getDetailReceiptDeatils(PolicyQuo policyQuo,DetailReceipt detailReceipt)";
		
	 /** Constant for Currency Code */
	 public static Integer CURRENCY_CODE=new Integer(1);

	
	String DESC_MESSAGE_1 = "BEING THE PREMIUM COLLECTED ON ";

	String DESC_MESSAGE_2 = "  INSURANCE POLICY NO:";

	String DESC_MESSAGE_3 = " PREMIUM:";

	String RISK_CODE = "5";

	String PREMIUM_CLASS_CODE = "classCode";

	String PREMIUM_RISK_CODE = "riskCode";

	String PREMIUM_POLICY_TYPE = "policyTypeCode";

	String PREMIUM_RISK_TYPE_CODE = "riskTypeCode";

	String PREMIUM_RISK_CATEGORY_CODE = "riskCategoryCode";

	String PREMIUM_RISK_SUB_CATEGORY_CODE = "riskSubCategoryCode";
	
	String PREMIUM_TARIFF = "tariffCode";

	String PREMIUM_COVER_OR_DISCOUNT = "coverOrDiscount";

	String VEH_MODEL_CODE = "vmoCode";

	String PREMIUM_COVER_DESC = "NONE";

	/** String to hold user response as Yes */

	/** String to hold user response as Yes */
	public static final String UI_RESPONSE_YES = "Y";

	String GET_FACTOR_LIST_FOR_POLICY = "getFactorListForPolicy()";

	String GET_PREMIUM_ON_COVERAGE = "getPremiumOnCoverage()";

	/** Constant for Status Code */
	public static final Integer LAPSE_STATUS_CODE = new Integer("10");

	static final String POLICY_TYPE_CODE = "policyTypeCode";

	static final String VEHICLE_TYPE_CODE = "vehicleTypeCode";

	static final String VEHICLE_SUB_CATEGORY = "vehiclesubCategory";

	static final String VEHICLE_ID = "vehicleId";
	
	static final String MONTHS_LAPSED = "monthsElapsed";

	static final String VEHICLE_CLASS_CODE = "vehicleClassCode";

	static final String RISK_CODE_STR = "riskCode";

	//static final String POLICY_ID = "policyID";

	static final String COVER_CODE = "coverCode";

	static final String CL_CODE = "clCode";
	
	static final String COV_FLAG =  "covFlag";
	
	static final Integer COV_FLAG_VAL_FOR_PRM_COVERS = new Integer(0);

	static final Integer POLICY_COVER_CODE = new Integer(55);

	static final Integer CL_CODE_VALUE = new Integer(1);

	static final Integer POLICY_CLASS_CODE = new Integer(1);

	static final Integer POLICY_RISK_CODE = new Integer(5);

	static final String COV_CODE_VAL1 = "covCode1";

	static final Integer COV_CODE_VAL1_VALUE = new Integer(100);

	static final String COV_CODE_VAL2 = "covCode2";

	static final Integer COV_CODE_VAL2_VALUE = new Integer(101);

	static final String COV_CODE_VAL3 = "covCode3";

	static final Integer COV_CODE_VAL3_VALUE = new Integer(51);
	
	static final String COV_CODE_VAL4 = "covCode4";

	static final Integer COV_CODE_VAL4_VALUE = new Integer(20);
    //ADM 24.03.2010 :CR16203: Grossup functionality 
	static final String COV_CODE_VAL5 = "covCode5";

	static final Integer COV_CODE_VAL5_VALUE = new Integer(21);
	
	static final String VAL_START_DATE = "valStartDate1";

	String PREMIUM_STATUS = "Yes";
	
	String PREMIUM_STATUS_NO = "No";

	public static final String STATUS = "STATUS";
	
	/** Constant for Pending Status Code */
	public static final Integer STATUS_PENDING = new Integer(6);

	/** Constant Added for confirm Endorsement Details */
	static final Integer STATUS_ACTIVE = new Integer(1);

	public static final Integer INI_REF_DEFAULT_STATUS_CODE = new Integer(1);

	/** Constant for Premium Type */
	public static Integer NIL_ENDORSEMENT_TYPE_CODE = new Integer(1);

	/** Constant for Premium Type */
	public static Integer REFUND_ENDORSEMENT_TYPE_CODE = new Integer(2);
	
	/** Constant for Premium Type */
	 public static Integer EXTRA_ENDORSEMENT_TYPE_CODE=new Integer(3);
	
	 //sonar fix on 21-9-2017
	 public static Long POLICY_CONDITION_RISK_ID= Long.valueOf(0); 
	 //sonar fix on 26-9-2017
	 public static Long POLICY_WARRANTY_RISK_ID=Long.valueOf(0);
	 public static Long POLICY_EXCLUSION_RISK_ID=Long.valueOf(0);
		
	public static final String YES="Y";
	
	public static final String NO="N";
	
	public static final String DECLINE="D"; //ADM 08.10.2009 CR63 Referral (Release 2.5)

	public static Integer CONDITION_TYPE_CODE = new Integer(1);

	public static Integer WARRANTY_TYPE_CODE = new Integer(2);

	public static Integer EXCLUSION_TYPE_CODE = new Integer(3);

	/** Constants added for */
	public static final Integer OMAN = new Integer(1);

	public static final Integer KSA = new Integer(2);

	public static final Integer DUBAI = new Integer(3);

	public static final Integer EGYPT = new Integer(4);

	public static final Integer BAHRAIN = new Integer(5);

	/** constants for description in credit note */
	static final String STR1 = "BEING THE PREMIUM PAYABLE ON ";

	static final String STR1_1 = "BEING THE PREMIUM DUE ON ";

	static final String STR2 = " REFUND ENDORSEMENT NO: ";
	
	static final String STR2_2 = " ENDORSEMENT NO: ";

	static final String STR3 = " POLICY NO : ";

	static final String STR3_1 = " INSURANCE.";

	static final String STR4 = " PREMIUM : ";

	static final String STR5 = " PREMIUM TAX : ";

	static final String STR6 = " Less Commission @  ";

	static final String PERCENT_SYM = "%";
	
	String ENDORSEMENT_PROPERTIES="com.rsaame.kaizen.properties.Endorsement";
	
	// STATUS CODES
	public static final Integer REFERED_TO_RSA_STATUS = new Integer(44);
	
	public static final Integer ACTIVE_STATUS = new Integer(1);

	public static final Integer PENDING_TASK_STATUS = new Integer(2);

	public static final Integer PENDING_STATUS = new Integer(6); 
	
	/** Constant for Pending Endorsement Status Code */
	public static Integer CTX_PENDING_ENDORSEMENT_STATUS_CODE = new Integer(6);
		
	String VEH_MAKE_CODE = "makeCode";
	
	String VEH_VMAKE_CODE = "vmakeCode";
	
	String SAVE_MESSAGE = "Quote Saved Successfully";
	
	String CTX_GET_PREMIUM = "getPremium()";
	
	String CTX_GET_CURRENCY_DESCRIPTION = "getCurrencyDescription()";
	
	String CURRENCY_QUERY = "currencyQuery";
	
	String CURRENCY_QUERY_CODE = "currencyCode";
    
	String QUERY_SCREENS="selectScreen";
	
	String QUERY_SECTIONS="selectSection";
	
	String QUERY_PRIVILEGE="selectPrivilege";
	
	String QUERY_USER_ROLE="selectUserRole";
	
	public static final String POL_VALIDITY_STRT_DATE = "policy_val_strt_date";
	
	//constants for customer search query
	String CS_CUSTOMER_ID = "CustomerId";
	String CS_EMAIL_ID = "EmailId";
	String CS_PHONE_NO = "PhoneNo";
	String CS_MOBILE_NO = "MobileNo";
	String CS_FIRST_NAME = "FirstName";
	String CS_LAST_NAME = "LastName";
	String CS_MIDDLE_NAME = "MiddleName";
	String CS_DOB = "DateOfBirth";
	String CTX_LOCATION_CODE = "LocationCodeCreate";
	String CS_POB_NO = "PoBoxNo";
	String CS_CONTACT_NO = "ContactNo";
	String CS_BROKER_NAME = "BrokerName";
	String CS_COMPLETE_NAME = "CompleteName";
	String CS_POLICY_QUOTE_NO = "PolicyQuoteNo";
	String CS_BROKER_ID = "BrokerId";
	// ADM 03.03.2010 Agent Profile (Release 2.5.2) 
	String CS_AGENT_NAME = "AgentName";
	String CS_AGENT_ID = "AgentId";
	public static String CS_DISTRIBUTION_CHANNEL = "DistributionChannel";	
	public static final String BROKER_PROFILE = "Broker";
	// ADM 03.03.2010 Agent Profile (Release 2.5.2) 
	public static final String AGENT_PROFILE = "Agent";
	String CTX_GET_TRANSACTION_LIST = "getTransactionList()";


	String QUERY_RENEWAL_BATCH_PRINT = "renewalBatchPrint";
	
	String BATCH_PRINT_TIMER = "batchprint.timer";
	
	String QRY_COMMISION_TYPE = "retrieveBrokerCommission";
	
	String CTX_GET_COMMISION_TYPES = "getCommisionTypes()";
	
	String CTX_GET_COMMISION_PERCENTAGE = "getCommisionPercentage()";
	
	String CTX_GET_BROKER_COMMISION_TYPE = "getBrokerCommisionType()";
	
	/** Constant Added to Update the Rejected quote details */ 
	public static final String UPDATE_REJETED_QUOTE_DETAILS  = "updateRejectedQuoteDetails";
	
	String GET_NUMBER_OF_DAYS_FOR_QUOTE ="getNumberOfDaysForQuote()";
	
	String CTX_CALCULATE_PRORATE = "calculateProrate()";
	
	String CTX_GET_PREMIUM_RATE_FOR_SHORT_PERIOD = "getPremiumRateForShortPeriod()";
	
	String CTX_CALCULATE_SHORT_PERIOD = "calculateShortPeriod()";
	
	String CTX_SET_GOVT_TAX = "setGovtTax()";
	
	String CTX_FETCH_EXCESS = "fetchExcess()";
	
	String CTX_FETCH_EXCESS_AMOUNT= "fetchExcessAmount()";
	
	String CTX_SET_EXCESS_IN_PREMIUM = "setExcessInPremium()";
	
	String EDIT_MODE_INDICATOR = "EDIT";
	
	String LOCATION_CODE_DUBAI="locationcode.Dubai";
	
	String LOCATION_CODE_ABUDHABI="locationcode.Abudhabi";
	
	String LOCATION_CODE_SHARJA="locationcode.Sharjah";
	
	String LOCATION_CODE_JEDDAH="locationcode.Jeddah";
	
	String LOCATION_CODE_ALKOHVAH="locationcode.Alkohvah";
	
	String LOCATION_CODE_RIYADH="locationcode.Riyadh";
	
	String LOCATION_CODE_MUSCAT="locationcode.Muscat";
	
	String LOCATION_CODE_BRANCH1="locationcode.Branch1";
	
	String LOCATION_CODE_BRANCH2="locationcode.Branch2";
	
	String LOCATION_CODE_BRANCH3="locationcode.Branch3";
	
	String LOCATION_CODE_BRANCH4="locationcode.Branch4";
	
	String LOCATION_CODE_BRANCH5="locationcode.Branch5";
	
	String LOCATION_CODE_BRANCH6="locationcode.Branch6";
	
	String LOCATION_CODE_BRANCH7="locationcode.Branch7";
	
	String LOCATION_CODE_BRANCH8="locationcode.Branch8";
	
	String LOCATION_CODE_BAHRAIN="locationcode.Bahrain";
	
	public static final String ENDT_CANCEL_HDR = "At the request of the Insured, the within mentioned Policy is cancelled with effect from ";
	
	String CTX_DELETE_ALL_PREMIUM = "deleteAllPremium()";
	
	//Added for View Active Transactions
	String ACTIVE_TRANSACTION = "groupByQuery";
	String VIEW_ACTIVE_TRANSACTION = "InsuredId";
	
	String CTX_CHECK_AGENCY_REPAIR_COVER = "checkAgencyRepairCover()";
	
	//Added For Agreed Value Cover 
	String CTX_CHECK_AGREED_VALUE_COVER = "checkAgreedValueCover()";
	
	String CTX_IS_RECALCULATE_PREMIUM = "isRecalculatePremium()";
	
	String MAIL_RENEWAL_QUOTATION="mail.renewal.quotation";
	
	String MAIL_RENEWAL_POLICY="mail.renewal.policy";
	
	String MAIL_RENEWAL_NOTICE="mail.renewal.notice";
	
	String MAIL_REFERRAL="mail.referral";
	
	String  MAIL_REFFERAL_QUOTATION="mail.referral.quotation";
		
	String MAIL_LICENSED_BY = "mail.licensedBy";
	
	String RENEWAL_TYPE = "RE";
	
	//Constants for Renewals Mail
	String START_HTML_BODY_TAGS = "mail.htm.tagstart";
	
	String END_HTML_BODY_TAGS = "mail.htm.tagend";
	
	String MAIL_RENEWAL_CONTENT_FIRST = "mail.renewalcontent.first";
	
	String MAIL_RENEWAL_CONTENT_SECOND = "mail.renewalcontent.second";
	
	String MAIL_RENEWAL_CONTENT_THIRD = "mail.renewalcontent.third";
	
	String MAIL_RENEWAL_CONTENT_FOURTH = "mail.renewalcontent.fourth";
	
	String MAIL_RENEWAL_CONTENT_FIFTH = "mail.renewalcontent.fifth";
	
	String MAIL_REN_CONTENT_SECOND = "mail.rencontent.second";
	
	//ADM 26.07.2010 - Release 3.1 EmailContent Modified for Bahrain location
	
	String MAIL_REN_CONTENT_THIRD = "mail.rencontent.third";
	
	String MAIL_REN_CONTENT_FOURTH = "mail.rencontent.fourth";
	
	String MAIL_REN_CONTENT_FIFTH = "mail.rencontent.fifth";
	
	String MAIL_REN_CONTENT_SIXTH = "mail.rencontent.sixth";	
		
	String END_PARA_TAG = "mail.end.paratag";
	
	String COMMA = "mail.comma.symbol";
	
	String SPACE = "mail.space.symbol";
	
	static final int REFERRAL_MAIL_TYPE = 2;
	
	static final int RENEWAL_MAIL_TYPE = 1;
	
	static final int ONE = 1;
	
	static final int PNCD_COUNTS_TO_EXCLUDE = 3;
	
	static final int RENEWAL_POL_DOCUMENT_CODE = 2;

	
	static final String SINGLE_SEPARATOR = "|";
	
	static final String DEFAULT_USER_NAME = "SYSTEM";
	
	static final String ACEGI_LOG_OUT="/j_acegi_logout";
	
	static final String LOG_OUT_PATH="/jsp/login/logout.jsp";
	
	String WRITE_RULE_REGION = "writerule.sourceregion";
	
	public static final Integer DIST_CHL_BROKER = new Integer(4);
	
	public static final Integer DIST_CHL_AGENT = new Integer(5);
	
	public static final Integer DIST_CHL_AFFINITY_AGENT = new Integer(7);
	
	public static final Integer DIST_CHL_DIRECT_DEALERSHIP = new Integer(11);

	static final String VEH_CATEGORY = "VEH_CATEGORY";
	
	static final String SCHEME_CODE = "SCHEME_CODE";
	
	static final String SUM_INSURED = "SUM_INSURED";
	
	static final String DRV_AGE = "DRV_AGE";
	
	static final String COND_CCG_CODE = "CCG_CODE";
	
	static final String CHANNEL_INFO = "CHANNEL_INFO";
	
	static final String COV_INFO = "COV_INFO";
	
	static final String COND_CLASSCODE = "CLS_CODE";
	/* Request ID :7446 Criteria Based Conditions will be fetched only,if there is any chnage in condition based factors  */
	static final String COND_SI_CHANGED="SI_CHANGED";
	static final String COND_VEH_CAT_CHANGED="VEH_CAT_CHANGED";
	static final String COND_DRI_AGE_CHANGED="DRI_AGE_CHANGED";
	static final String COND_COVER_CHANGED="COVER_CHANGED";
	static final String COND_CUST_CAT_CHANGED="CUST_CAT_CHANGED";
	static final String COND_CHNN_CHANGED="CHNN_CHANGED";
	static final String COND_SCHEME_CHANGED="SCHEME_CHANGED";
	
	
	
	public static final Integer DEFAULT_INDICATOR_ONE = new Integer(1);  
	
	public static final Integer DEFAULT_INDICATOR_TWO = new Integer(2);
	
	//16.12.2008 CTS, Chennai - For CR53 
	String QRY_GET_CUST_COMMENTS = "getCommentsForCustomer";
	
	public static final String QUERY_CACHE="QueryCache";
	
	//ADM 02.04.2009 Request Id 7483 Fix to view the proposalform name with quote number
	static final String CTX_VIEW_QUOTE_NUMBER = "retriveQuoteNo(PolicyQuo policyQuo)";
	
	/*ADM 13.07.2009 : The new vehicle age calc should not affect the existing policies while we process the nil endorsement. 
	 * So has to retain the old calculation for any transactions(new or renewal) 
	 * with effective date < 17th Jul 09 and new calculation for effective date >= 17th Jul 09. */
	public static final String VEH_AGE_EFFECTIVE_DATE = "17/07/2009";
	
	//ADM 05.06.2009 : CR04 Quote versioning start
	static final String SEQ_QUO_ENDORSEMENT_ID = "SEQ_QUO_ENDORSEMENT_ID";
	
	String QUO_NOT_AVAILABLE = "Quote is not available.";
	
	static final String CTX_INSERT_QUOTATION_DETAILS = "insertQuotationDetails(PolicyQuo quote)";
	
	public static final String VERSION_SAVED_IND_YES="Y";
	
	public static final String VERSION_SAVED_IND_NO="N";
	
	public static final String VERSION_PRM_UPDT_IND="Y";
	
	static final String CTX_REMOVE_QUOTATION_DETAILS = "removePolicyQuo(PolicyQuo quote)";
	
	static final String CTX_UPDT_EXPIRY_DT_QUOTE = "updateExpiryDtForQuote(PolicyQuo quote)";
	
	static final Long PAGE_IND_1 = new Long("1");
	
	static final Long PAGE_IND_2 = new Long("2");
	
	static final Long PAGE_IND_3 = new Long("3");
	
	public static final String QUOTE_USE_MSG = "Quotation was modified by another user.Please select the latest Quotation.";
	
	//ADM 05.06.2009 : CR04 Quote versioning end
	
	//	ADM 23.10.2009 : Password Complexity start   //SONARFIX--25-apr-2018-- changed the variable name from PASSWORD_VAL_MSG to P_VAL_MSG as the variable is not used anywhere else
	public static final String P_VAL_MSG = "The Password supplied does not meet the minimum complexity requirements.Please select another password that has not been used in the previous 5 passwords";
	
	public static final String P_SUCCESS_MSG = "Password changed successfully"; 	//SONARFIX--25-apr-2018-- changed the variable name from PASSWORD_SUCCESS_MSG to P_SUCCESS_MSG as the variable is not used anywhere else
	//	ADM 23.10.2009 : Password Complexity end
	//ADM 03.12.2009 : ADM For CR68 starts
	public static final String TABLE_VALUE_CHANGED = "TableValueChanged";
	public static final String TABLE_VALUE_SAME = "TableValueSame";
	//ADM 03.12.2009 : ADM For CR68 Ends
	// ADM 03.03.2010 Agent Profile (Release 2.5.2) 
	String CTX_TRANSACTION_AGENT_NAME = "TransactionAgentName";
	//ADM 13.04.2010 : CR 16203: Grossup CR.
	public static final String SCH_CODE = "schemeCode";
	public static final String DOCUMENT_CODE = "documentCode";
	
	
	/*
	 * Certificate validation based on broker/Agent/Cust Code Release 3.2 patch
	 */
	
	public static final String BROKER_CODE = "bcdBrokerCode";
	
	public static final String AGENT_CODE = "bcdAgentCode";

	public static final String ERROR_KEY = "ERROR_KEY";
	
	public static final String VALIDATE_FLAG = "VALIDATE_FLAG";

	public static final String DIRECT_PROFILE = "Direct";	
	
//	09.08.2011 Added for DataUpload for other interface data
	String BATCH_FETCH_QUERY = "dataloadFetch";
	String BATCH_EXCEPTION = "E";
	String BATCH_SUCESS = "Y";
	 String DATALOAD_LOCATION = "locationcode.Muscat";
	
	// ## ADM 19.05.2011 -  - Renewal Report Access controls 
	
	String RENEWAL_TERM = "REN_TERM";
	
    String RENEWAL_STATUS = "REN_STS";
	
	String RENEWAL_TERM_TITLE = "Renewal Terms";
	
	String RENEWAL_STATUS_TITLE = "Renewal Status Report";
	
	// ## ADM 19.05.2011 -  - Renewal Report Access controls 
	
	
}