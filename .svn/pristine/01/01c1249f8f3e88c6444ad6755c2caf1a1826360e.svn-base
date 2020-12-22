/*
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Mark’s Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.kaizen.quote.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.admin.dao.LocationDAO;
import com.rsaame.kaizen.admin.model.CommisionType;
import com.rsaame.kaizen.admin.model.Document;
import com.rsaame.kaizen.admin.model.ExternalExecutive;
import com.rsaame.kaizen.admin.model.GlAccInterface;
import com.rsaame.kaizen.admin.model.GlAccMaster;
import com.rsaame.kaizen.admin.model.Status;
import com.rsaame.kaizen.admin.model.VehicleModel;
import com.rsaame.kaizen.authorization.model.User;
import com.rsaame.kaizen.customer.model.DistributionChannel;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.constants.AMEPolicyConstants;
import com.rsaame.kaizen.framework.dao.AMEBaseDAO;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.dao.impl.AMEBaseDAOImpl;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.framework.model.ApplicationContext;
import com.rsaame.kaizen.framework.model.Message;
import com.rsaame.kaizen.framework.model.PaginatedResult;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.util.AMEUtil;
import com.rsaame.kaizen.framework.util.BeanFactory;
import com.rsaame.kaizen.policy.model.Policy;
import com.rsaame.kaizen.policy.model.PolicyType;
import com.rsaame.kaizen.policy.model.TTrnGlUnmatchedAic;
import com.rsaame.kaizen.policy.model.Transaction;
import com.rsaame.kaizen.quote.dao.QuoteDAO;
import com.rsaame.kaizen.quote.model.CashCustomerQuo;
import com.rsaame.kaizen.quote.model.CoinsuranceQuo;
import com.rsaame.kaizen.quote.model.MotorPolicyRating;
import com.rsaame.kaizen.quote.model.PolicyComment;
import com.rsaame.kaizen.quote.model.PolicyPKQuo;
import com.rsaame.kaizen.quote.model.PolicyQuo;
import com.rsaame.kaizen.quote.model.PremiumQuo;
import com.rsaame.kaizen.quote.model.ReasonCode;
import com.rsaame.kaizen.quote.model.TTrnReferralStatus;
import com.rsaame.kaizen.quote.model.VehicleQuo;
import com.rsaame.kaizen.quote.model.ViewQuoteBO;
import com.rsaame.kaizen.renewal.model.BatchPrint;
import com.rsaame.kaizen.renewal.model.BatchPrintPK;
import com.rsaame.kaizen.renewal.model.RenewalPolicy;
import com.rsaame.kaizen.renewal.model.TranType;
import com.rsaame.kaizen.renewal.model.TransactionSearchCriteria;
import com.rsaame.pas.cmn.currency.Currency;


/**
 * QuoteDAOImpl class
 * 
 * @version 1.0 Sep 24, 2007
 * @author Cognizant Technology Solutions
 */

public class QuoteDAOImpl extends AMEBaseDAOImpl implements QuoteDAO {

    /**
     * query to get depreciation percent
     */
    private static final String QRY_GET_DEPRE_PERCENT = "getDepreciationPercent";

    private static final String OUTSTANDING_PREMIUM = "outstanding_premium";

    private static final String CTX_FTH_QUOTE_BY_ID = "fetchQuoteByQuoteId()";

    private static final String VIEW_QUOTE = "viewQuote(PolicyQuo policyQuo)";

    private static final String CTX_GET_DEPRECIATION_PERCENT = "CTX_GET_DEPRECIATION_PERCENT";

    private static final String RISKCATAGORY = "riskCatagory";

    private static final String CTX_FETCH_QUOTE_DETAILS = "fetchQuoteDetailsByQuoteId(PoilcyQuo)";

    private static final String SCHEME = "scheme";

    private static final String RISKTYPE = "riskType";

    private static final String RISKCODE = "riskCode";

    private static final String VEHICLEAGE = "vehicleAge";

    private static final String SCHEME_CODE = "schemeCode";

    private static final String RISK_CATEGORY = "riskCategory";

    private static final String STRING_CHECK = "-";

    private static final String CTX_POL_TYP_FOR_POL = "getTypeForQuote()";

    private static final String CTX_GET_RISK_CAT = "getRiskCat(poilcyQuo)";

    private static final String CTX_GET_POL_CMT = "getPolicyComments()";
    
    private static final String CTX_GET_QUO_BY_STATUS = "getQuotationDetailsByStatus()";
    
    private static final String CTX_NXT_SEQ = "getNextSequenceNumber()"; //ADM 01.10.2010  Release 4.0 - Oman MultiBranch Transactions within Single DB Schema

    /**
     * Logger instance
     */
    private static final AMELogger logger = AMELoggerFactory.getInstance().getLogger(QuoteDAOImpl.class);

    /** Context for method getPolicyQuoForPolicyQuo. */
    private static final String CONTEXT_GET_POLICY_FOR_POLICY_QUO = "getPolicyQuoForPolicyQuo(PolicyQuo policyQuo)";

    /** Context for method getOutstandingPremiumForPolicyQuo. */
    private static final String CONTEXT_GET_OUTSTANDING_PREMIUM_POLICY_QUO = "getOutstandingPremiumForPolicyQuo(PolicyQuo policyQuo)";

    /** Query key to get Outstanding premium. */
    private static final String QRY_OUTSTANDING_PREMIUM_FOR_POLICY = "selectOutstandingPremium";

    private static final String CTX_GET_COUNT = "getCountOfRefPolicyIdInQuote";

    public static final String CTX_LAPSE_RENEWAL_QUOTATION = "updateQuotationStatusByPK(PolicyQuo policyQuo)";

    public static final String CTX_APPROVE_AUTHORIZATION_REFERRAL = "approveAuthorizationRefferal(PolicyQuo policyQuo)";

    public static final String CTX_UPDATE_PREMIUM_DETAILS = "updatePremiumDetailsForQuote(PolicyQuo policyQuo)";
    
    private static final String QRY_LAPSE_RENEWAL_QUOTATION = "updateStatusForPolicyQuo";

    private static final String CONTEXT_GET_POLICY_FOR_POLICY = "getPermiumDetailsByPk(Policy policy)";

    private static final String QRY_GET_REFERRAL = "getReferral";

    private static final String CTX_DEL_REN_QUOTE = "deleteRenewalQuote";

    private static final String CTX_LAZY_ATTRIBUTES = "initializeLazyAttributesToNull(PolicyQuo policyQuo)";

     // Added for the Cap & Collar i.e CR-66
	private static final String CTX_CALL_PROCEDURE = "callUpdateProcedure(Long refPolicyId,Long refEndId,Long quotationId,Long capCollerGradient)";
	
	//ADM 24.09.2009 : Added for CR04 : Quote versioning
	private static final String CTX_CALL_PROCEDURE_VERSION = "callUpdateProcedureVersion(Long policyId,Long endtId, Long srcNumber)";
	
	private static final String CTX_CALL_PROCEDURE_VEHCARD = "callUpdateProcedureVersion(Long policyId,Long endtId, Long srcNumber)";

	// Added for the Cap & Collar i.e CR-66
	private static final String CTX_EXP_POL_FINAL_PREMIUM = "getExpPolFinalPremium(Long policyId,Long endtId)";
	
	//OMAN CR - ORANGE CARD
	private static final String CTX_CALL_PROCEDURE_VEHICLE_CARD = "CallInsertVehicleCardToPolicyProc(Long policyId, Long quotationPolicyId)";

	
    /**
     * Static TreeMap variable
     *  
     */

    public static final String SEQ_MOTOR_QUO_NO = "SEQ_MOTOR_QUO_NO";

    public static final String SEQ_QUO_ID = "SEQ_QUOTATION_ID";

    public static final String CTX_SAVE_RENEWALS = "saveRenewalTermsForQuote()";

    private static final String CTS_GET_REFERRAL = "getReferral(PolicyQuo)";

    private static TreeMap quoteHashMap = new TreeMap();

    private static TreeMap policyHashMap = new TreeMap();
    
    private static TreeMap viewCommentsHashMap = new TreeMap();

    private static final String CTX_GET_QUOTATIONS_TO_PRINT = "getQuotationsToBePrinted(TransactionSearchCriteria criteria)";

    private static final String CTX_SAVE_QUOTATIONS = "saveQuotations(List selectedQuotations)";

    private static final String CTX_GET_REN_QUOTES = "getRenewalQuotations()";

    private static final String CTX_GET_NEW_DATE = "getNewValidityStartDate()";
    
    private static final String CTX_UPDATE_SUSPEND_TRAN = "updateSuspendTransaction()";
    
    private static final String CTX_TRANS_HIST = "getTransactionHistory()";
    
    private static final String CTX_GET_POL_ID = "getPolicyId()";
    
    private static final String CTX_FETCH_OUT_STANDING_AMT = "fetchOutStandingAmount()";

    private static final String CTX_FETCH_APPROVED_QUOTE_VERSION = "getApprovedVersionOfQuote()";

	//ADM 15.08.2009 : CR04 Quote versioning start
    private static final String GET_MAX_ENDORESEMENT_NO_FOR_QUO = "getMaxEndorsementNoForQuo(Long policyId)";
    
    private static final String QRY_MAX_ENDT_NO_FOR_QUO = "getMaxEndorsementNoQuote";
    
    private static final String QRY_MAX_ENDT_ID_FOR_QUO = "getMaxEndtIdForQuote";
    
    private static final String QRY_GET_OLD_QUO = "getOldQuoteDetails";    
    
    private static final String GET_LAST_ACTIVE_QUO = "getLastActiveQuote(PolicyQuo policyQuo)";
    
    private static final String GET_QUO_DETAILS = "getQuoteDetails(PolicyQuo policyQuo)";
    
    private static final String FETCH_ADDITIONAL_INFO = "fetchAdditionalInfoForQuote(PolicyQuo policyQuo)";
    
    private static final String GET_NEW_POLICY_NO = "getNextSequenceNumberForID( CountryCode,  regionCOde, branchCode, classCode , transactionType,  policyTypeCode)";
        
	//ADM 15.08.2009 : CR04 Quote versioning END
    
    //ADM 11.03.2010 CR16203 Gross up
    private static final String QRY_TOTAL_PREMIUM_QUOTE = "getTotalPremiumQuo";
    
    public static final String QRY_MIN_MAX_CURR = "getMinAndMaxCurr";
    
    private static String BROKER_CODE_OVERRIDDEN = " UPPER( BR_E_NAME ) = UPPER(':brokerName:')";
    //Multi branch handling. Location filter
	private static String CTX_TRANSACTION_LOC_CODE = "LocationCode";
	private static String POLICY_LOCATION_CODE = "POL_LOCATION_CODE = ':locationCode:'";
	//LOB Filter
	private static String CTX_TRANSACTION_POLICY_TYPE = "TransactionPolicyType";
	private static String POLICY_TYPE = "PT_CODE IN (Select cdm_code From T_Mas_Code_Master Where Cdm_Entity_Type='PAS_LOBMAP' and Cdm_Code1 in (Select code From Ss_V_Mas_Lookup Where Category='PAS_LOB' And Code=':policyType:'))";
	
	// Added as part of 3.1.1 release legacy renewal quote display
	private static String  CTX_TRANSACTION_MOBILE_NO = "MobileNo";
	private static String POLICY_MOBILE_NO = "MOBILE_NO=':mobileNo:'";
	private static String  CTX_TRANSACTION_PHONE_NO = "PhoneNo";
	private static String POLICY_PHONE_NO = "PHONE_NO=':phoneNo:'";
	private static String  CTX_TRANSACTION_POL_REF_NO = "PolReferenceNo";
	private static String POLICY_POL_REF_NO = "POL_REF_POLICY_NO=':polReferenceNo:'";
	
	
  //START Change for PAS : Search transaction views change
    public static String POLICY_QUO_SEARCH_QUERY = "SELECT CSH_E_NAME,POL_POLICY_ID,POL_ENDT_NO,POL_MODIFIED_BY_USER,POL_QUOTATION_NO,PT_E_DESC, DOC_E_DESC,POL_MODIFIED_DATE,POL_TOTAL_PREMIUM,STA_E_DESC,POL_EXPIRY_DATE_1,POL_EFFECTIVE_DATE_1,POL_ENDT_ID,VEHICLE_IEV,VEH_MODEL_E_DESC,POL_STATUS,POL_SUSPEND_TRANSACTION,POL_TAR_CODE,POL_ISSUE_HOUR,POL_RENEWAL_COUNT,REF_USER,CSH_COMPANY_NAME,POL_EFFECTIVE_DATE, POL_LOCATION_CODE,BR_E_NAME,SCH_DESC,POL_REF_POLICY_NO,POL_ISSUE_DATE,COMMENTS  FROM V_TRN_POLICY_QUO_SEARCH_PAS WHERE  ";
	
	//public static String POLICY_SEARCH_QUERY = "SELECT CSH_E_NAME, POL_POLICY_ID, POL_ENDT_NO, POL_MODIFIED_BY_USER, POL_POLICY_NO, PT_E_DESC, DOC_E_DESC, TO_CHAR(POL_PREPARED_DT, 'DD/MM/YYYY hh24:mi:ss'), POL_TOTAL_PREMIUM, STA_E_DESC, POL_EXPIRY_DATE_1, POL_EFFECTIVE_DATE_1, POL_ENDT_ID, VEHICLE_IEV, VEH_MODEL_E_DESC, POL_STATUS, POL_SUSPEND_TRANSACTION, POL_TAR_CODE, POL_ISSUE_HOUR, POL_RENEWAL_COUNT, REF_USER,CSH_COMPANY_NAME, POL_EFFECTIVE_DATE, POL_LOCATION_CODE,BR_E_NAME,SCH_DESC,POL_QUOTATION_NO FROM V_TRN_POLICY_SEARCH_PAS WHERE  ";
    public static String POLICY_SEARCH_QUERY = "SELECT CSH_E_NAME, POL_POLICY_ID, POL_ENDT_NO, POL_MODIFIED_BY_USER, POL_POLICY_NO, PT_E_DESC, DOC_E_DESC, POL_MODIFIED_DATE, POL_TOTAL_PREMIUM, STA_E_DESC, POL_EXPIRY_DATE_1, POL_EFFECTIVE_DATE_1, POL_ENDT_ID, VEHICLE_IEV, VEH_MODEL_E_DESC, POL_STATUS, POL_SUSPEND_TRANSACTION, POL_TAR_CODE, POL_ISSUE_HOUR, POL_RENEWAL_COUNT, REF_USER,CSH_COMPANY_NAME, POL_EFFECTIVE_DATE, POL_LOCATION_CODE,BR_E_NAME,SCH_DESC,POL_QUOTATION_NO,POL_ISSUE_DATE,COMMENTS FROM V_TRN_POLICY_SEARCH_PAS WHERE  ";
    
    public static String VIEW_COMMENTS_POLICY_HISTORY=" SELECT CSH_E_NAME,POL_POLICY_ID,POL_ENDT_NO,POL_MODIFIED_BY_USER,POL_QUOTATION_NO,PT_E_DESC, DOC_E_DESC,POL_MODIFIED_DATE,POL_TOTAL_PREMIUM,STA_E_DESC,POL_EXPIRY_DATE_1,POL_EFFECTIVE_DATE_1,POL_ENDT_ID,VEHICLE_IEV,VEH_MODEL_E_DESC,POL_STATUS,POL_SUSPEND_TRANSACTION,POL_TAR_CODE,POL_ISSUE_HOUR,POL_RENEWAL_COUNT,REF_USER,CSH_COMPANY_NAME,POL_EFFECTIVE_DATE, POL_LOCATION_CODE,BR_E_NAME,SCH_DESC,POL_REF_POLICY_NO,POL_ISSUE_DATE,COMMENTS from v_policy_history_comment where";
    
    public static String VIEW_COMMENTS_QUO_HISTORY="SELECT CSH_E_NAME,POL_POLICY_ID,POL_ENDT_NO,POL_MODIFIED_BY_USER,POL_QUOTATION_NO,PT_E_DESC, DOC_E_DESC,POL_MODIFIED_DATE,POL_TOTAL_PREMIUM,STA_E_DESC,POL_EXPIRY_DATE_1,POL_EFFECTIVE_DATE_1,POL_ENDT_ID,VEHICLE_IEV,VEH_MODEL_E_DESC,POL_STATUS,POL_SUSPEND_TRANSACTION,POL_TAR_CODE,POL_ISSUE_HOUR,POL_RENEWAL_COUNT,REF_USER,CSH_COMPANY_NAME,POL_EFFECTIVE_DATE, POL_LOCATION_CODE,BR_E_NAME,SCH_DESC,POL_REF_POLICY_NO,POL_ISSUE_DATE,COMMENTS from v_quotation_history_comment where";
	
	public static String POLICY_QUO_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION ="SELECT CSH_E_NAME,POL_POLICY_ID,POL_ENDT_NO,POL_MODIFIED_BY_USER,POL_QUOTATION_NO,PT_E_DESC, DOC_E_DESC, POL_MODIFIED_DATE, POL_TOTAL_PREMIUM,STA_E_DESC,POL_EXPIRY_DATE_1,POL_EFFECTIVE_DATE_1,POL_ENDT_ID,VEHICLE_IEV,VEH_MODEL_E_DESC,POL_STATUS,POL_SUSPEND_TRANSACTION,POL_TAR_CODE,POL_ISSUE_HOUR,POL_RENEWAL_COUNT,REF_USER,CSH_COMPANY_NAME,POL_EFFECTIVE_DATE, POL_LOCATION_CODE,BR_E_NAME,SCH_DESC,POL_REF_POLICY_NO,POL_ISSUE_DATE,COMMENTS  FROM V_TRN_POLICY_QUO_SRCH_LAST_PAS WHERE  ";
			
	//public static String POLICY_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION = "SELECT CSH_E_NAME, POL_POLICY_ID, POL_ENDT_NO, POL_MODIFIED_BY_USER, POL_POLICY_NO, PT_E_DESC, DOC_E_DESC, TO_CHAR(POL_PREPARED_DT, 'DD/MM/YYYY hh24:mi:ss'), POL_TOTAL_PREMIUM, STA_E_DESC, POL_EXPIRY_DATE_1, POL_EFFECTIVE_DATE_1, POL_ENDT_ID, VEHICLE_IEV, VEH_MODEL_E_DESC, POL_STATUS, POL_SUSPEND_TRANSACTION, POL_TAR_CODE, POL_ISSUE_HOUR, POL_RENEWAL_COUNT, REF_USER,CSH_COMPANY_NAME, POL_EFFECTIVE_DATE, POL_LOCATION_CODE ,BR_E_NAME,SCH_DESC,POL_QUOTATION_NO FROM V_TRN_POLICY_SEARCH_LAST_PAS WHERE  ";
	
	public static String POLICY_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION = "SELECT CSH_E_NAME, POL_POLICY_ID, POL_ENDT_NO, POL_MODIFIED_BY_USER, POL_POLICY_NO, PT_E_DESC, DOC_E_DESC, POL_MODIFIED_DATE, POL_TOTAL_PREMIUM, STA_E_DESC, POL_EXPIRY_DATE_1, POL_EFFECTIVE_DATE_1, POL_ENDT_ID, VEHICLE_IEV, VEH_MODEL_E_DESC, POL_STATUS, POL_SUSPEND_TRANSACTION, POL_TAR_CODE, POL_ISSUE_HOUR, POL_RENEWAL_COUNT, REF_USER,CSH_COMPANY_NAME, POL_EFFECTIVE_DATE, POL_LOCATION_CODE ,BR_E_NAME,SCH_DESC,POL_QUOTATION_NO,POL_ISSUE_DATE,COMMENTS FROM V_TRN_POLICY_SEARCH_LAST_PAS WHERE  ";
  //END Change for PAS : Search transaction views change
    
	/* Adding order by Endt*/
	//public static String SORT_BY_EFF_DATE_ENDT_NO = AMEPolicyConstants.DEFAULT_SORT + ",POL_ENDT_NO ";
	
	public static String SORT_BY_EFF_DATE_ENDT_NO = " ORDER BY POL_ISSUE_DATE DESC, POL_ENDT_NO DESC";
	
    /**
     * Method viewQuote contains switch statement
     * 
     * @param viewQuoteBO
     * @return viewQuoteBO
     * @throws DataAccessException
     */
    public ViewQuoteBO viewQuote(ViewQuoteBO viewQuoteBO) throws DataAccessException {
        //logger.debug(AMEConstants.CTX_VIEW_QUOTE, com.Constant.CONST_METHOD_START);
        int pageNo = viewQuoteBO.getPageNo();

        switch (pageNo) {
            case 1:
                viewQuoteBO = viewQuotePageOne(viewQuoteBO);
                break;
            case 2:
                viewQuoteBO = viewQuotePageTwo(viewQuoteBO);
                break;
            case 3:
                viewQuoteBO = viewQuotePageThree(viewQuoteBO);
                break;
              //sonar fix
			default:
				break;
        }
        //logger.debug(AMEConstants.CTX_VIEW_QUOTE, com.Constant.CONST_END_METHOD);
        return viewQuoteBO;
    }

    /**
     * Method viewQuotePageOne() for the First page in UI
     * 
     * @param viewQuoteBO
     * @return viewQuoteBO
     * @throws DataAccessException
     */

    private ViewQuoteBO viewQuotePageOne(ViewQuoteBO viewQuoteBO) throws DataAccessException {
        logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_ONE, "Method Star_1");

        Session session = null;

        try {
            session = getSession();

            String finalQuery = getQuery(AMEConstants.CASH_CUSTOMER_VIEW_QUERY);
            logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_ONE, "Query:_1" + finalQuery);

            //	Run the query to get the required List

            Query query = session.createQuery(finalQuery);
            query.setLong(com.Constant.CONST_POLICYID, viewQuoteBO.getPolicyId().longValue());
            query.setLong(com.Constant.CONST_ENDTID, viewQuoteBO.getEndtId().longValue());

            Object obj = query.uniqueResult();
            viewQuoteBO.setCustomerInformation((CashCustomerQuo) obj);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_ONE, "End Metho_1");
        return viewQuoteBO;

    }

    /**
     * Method viewQuotePageTwo() for the First page in UI
     * 
     * @param viewQuoteBO
     * @return viewQuoteBO
     * @throws DataAccessException
     * @throws DataAccessException
     */

    private ViewQuoteBO viewQuotePageTwo(ViewQuoteBO viewQuoteBO) throws DataAccessException {
        //	logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_TWO, com.Constant.CONST_METHOD_START);

        Session session = null;

        Long policyId = viewQuoteBO.getPolicyId();
        Long endtId = viewQuoteBO.getEndtId();

        try {
            session = getSession();
            String finalQueryVehicleDetails = "SELECT vehicleQuo "
                    + "FROM com.rsaame.kaizen.quote.model.VehicleQuo vehicleQuo "
                    + "WHERE vehicleQuo.policy.comp_id.policyId = " + policyId
                    + " AND  vehicleQuo.policy.comp_id.endtId = " + endtId;

            VehicleQuo resultVehicle = (VehicleQuo) session.createQuery(finalQueryVehicleDetails).uniqueResult();
            viewQuoteBO.setVehicledetails(resultVehicle);

            String finalQueryDriverDetails = "SELECT driverQuo" + " FROM DriverQuo driverQuo"
                    + " WHERE driverQuo.comp_id.policy.comp_id.policyId = " + policyId
                    + " AND driverQuo.comp_id.policy.comp_id.endtId = " + endtId;

            List resultListDriver = session.createQuery(finalQueryDriverDetails).list();
            viewQuoteBO.setDriverDetails(resultListDriver);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        //	logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_TWO, com.Constant.CONST_END_METHOD);
        return viewQuoteBO;
    }

    /**
     * Method viewQuotePageThree() for the First page in UI
     * 
     * @param viewQuoteBO
     * @return viewQuoteBONew
     * @throws DataAccessException
     */
    private ViewQuoteBO viewQuotePageThree(ViewQuoteBO viewQuoteBO) throws DataAccessException {
        //	logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_THREE, com.Constant.CONST_METHOD_START);

        Session session = null;

        ViewQuoteBO viewQuoteBONew;
        try {
            session = getSession();
            Long policyId = viewQuoteBO.getPolicyId();
            Long endtId = viewQuoteBO.getEndtId();
            Integer policyTypeCode = viewQuoteBO.getPolicytypeCode();
            Integer riskCode = viewQuoteBO.getRiskCode();
            Integer classCode = viewQuoteBO.getClassCode();
            Integer riskTypeCode = viewQuoteBO.getRiskTypeCode();
            Integer riskCategoryCode = viewQuoteBO.getRiskCategoryCode();
            Integer riskSubCategoryCode = viewQuoteBO.getRiskSubCategoryCode();

            viewQuoteBONew = new ViewQuoteBO();

            //query for discounts
            String finalQueryDiscount = "SELECT policyQuo FROM PolicyQuo policyQuo  WHERE policyQuo.comp_id.policyId = "
                    + policyId + " AND  policyQuo.comp_id.endtId = " + endtId;
            PolicyQuo policyQuo = (PolicyQuo) session.createQuery(finalQueryDiscount).uniqueResult();
            viewQuoteBONew.setPolicyQuo(policyQuo);

            //query for terms and conditions
            String finalQueryCondition = "SELECT policyCondition"
                    + " FROM PolicyCondition policyCondition,PolicyHeader policyHeader "
                    + " WHERE policyCondition.policyHeader.code =  policyHeader.code "
                    + "AND policyCondition.comp_id.cover= " + 0 + "AND policyCondition.comp_id.policyType = "
                    + policyTypeCode + "AND policyCondition.comp_id.policyClass= " + 1;
            List resultQueryCondition = session.createQuery(finalQueryCondition).list();
            viewQuoteBONew.setPolicyCondition(resultQueryCondition);

            String finalQueryRatingAllCovers = "SELECT  " + "motorPolicyRating"
                    + " FROM MotorPolicyRating motorPolicyRating " + " WHERE motorPolicyRating.classCode =" + classCode
                    + " AND motorPolicyRating.riskCode= " + riskCode + " AND motorPolicyRating.policyTypeCode ="
                    + policyTypeCode + " AND motorPolicyRating.riskTypeCode= " + riskTypeCode
                    + " AND motorPolicyRating.riskCategoryCode= " + riskCategoryCode
                    + " AND motorPolicyRating.riskSubCategoryCode= " + riskSubCategoryCode;

            List resultListAllCovers = session.createQuery(finalQueryRatingAllCovers).list();
            Iterator itrAllCovers = resultListAllCovers.iterator();

            String finalQueryRatingCheckedCovers = "select distinct  b.cover,b.cover_type,b.cover_sub_type, "
                    + " PRM_POL_SUM_INSURED,PRM_PREMIUM,PRM_COMPULSORY_EXCESS "
                    + " FROM t_trn_premium a join ss_v_mas_motor_policy_rating b on a.prm_cl_code = b.class_code "
                    + "AND a.prm_pt_code = b.policy_type_code AND a.prm_rsk_code = b.risk_code "
                    + "AND a.prm_rt_code = b.risk_type_code " + "AND a.PRM_RC_CODE = b.risk_category_code "
                    + "AND a.prm_rsc_code = b.risk_sub_category_code " + "AND a.PRM_COV_CODE = b.cover_code "
                    + "AND a.PRM_Ct_CODE = b.cover_type_code " + "AND a.PRM_CST_CODE = b.cover_sub_type_code "
                    + "WHERE prm_policy_id =" + policyId + " and prm_cl_code = 1 and prm_pt_code = " + policyTypeCode;

            List resultListCheckedCovers = session.createSQLQuery(finalQueryRatingCheckedCovers).list();
            Iterator itrCheckedCovers = resultListCheckedCovers.iterator();

            Object rowCheckedCovers[] = null;
            PremiumQuo premiumQuo = new PremiumQuo();

            MotorPolicyRating motorPolicyRating = null;
            String covDesc = null;
            String covTypeDesc = null;
            BigDecimal rowCheckedCoversSum;
            BigDecimal rowCheckedCoversPrm;
            BigDecimal rowCheckedCoversComEx;
            List finalList = new ArrayList();
            while (itrAllCovers.hasNext()) {
                motorPolicyRating = (MotorPolicyRating) itrAllCovers.next();
                covDesc = motorPolicyRating.getCover();
                covTypeDesc = motorPolicyRating.getCoverType();
                //			logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_THREE,
                // "covDesc::"
                //					+ covDesc);
                //			logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_THREE,
                //					"covTypeDesc::" + covTypeDesc);
                itrCheckedCovers = resultListCheckedCovers.iterator();
                while (itrCheckedCovers.hasNext()) {
                    rowCheckedCovers = (Object[]) itrCheckedCovers.next();
                    if (covDesc.equalsIgnoreCase(rowCheckedCovers[0].toString().trim())
                            && covTypeDesc.equalsIgnoreCase(rowCheckedCovers[1].toString().trim())) {
                        motorPolicyRating.setIsChecked(new Boolean(true));
                        if (rowCheckedCovers[3] == null) {
                            rowCheckedCoversSum = new BigDecimal("0");
                        } else {
                            rowCheckedCoversSum = new BigDecimal((String) rowCheckedCovers[3]);
                        }
                        premiumQuo.setSumInsured(rowCheckedCoversSum);
                        if (rowCheckedCovers[4] == null) {
                            rowCheckedCoversPrm = new BigDecimal("0");
                        } else {
                            rowCheckedCoversPrm = new BigDecimal(rowCheckedCovers[4].toString());
                        }
                        premiumQuo.setPremium(rowCheckedCoversPrm);
                        if (rowCheckedCovers[5] == null) {
                            rowCheckedCoversComEx = new BigDecimal("0");
                        } else {
                            rowCheckedCoversComEx = new BigDecimal(rowCheckedCovers[5].toString());
                        }
                        premiumQuo.setCompulsoryExcess(rowCheckedCoversComEx);
                        motorPolicyRating.setPremiumQuo(premiumQuo);
                    }

                    else {
                        PremiumQuo premiumQuoUnchecked = new PremiumQuo();
                        motorPolicyRating.setIsChecked(new Boolean(false));
                        motorPolicyRating.setPremiumQuo(premiumQuoUnchecked);
                    }
                }
                finalList.add(motorPolicyRating);

            }

            viewQuoteBONew.setMotorPolicyRating(finalList);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        //	logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_THREE, com.Constant.CONST_END_METHOD);
        return viewQuoteBONew;

    }

    /**
     * Method getQuoteListForSearchTransaction() returns list of transaction
     * objects
     * 
     * @param transactionObject
     * @return transactionList
     * @throws DataAccessException
     */
    public PaginatedResult getQuoteListForSearchTransaction(Transaction transactionObject) throws DataAccessException {

        logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, "Method Star_2");
        String selectQuoteQuery = null;
        String selectPolicyQuery = null;
        String viewCommentPolicyQuetry=null;
        String viewCommentQuoQuetry=null;

        if(transactionObject.getTransactionCustomerName()!=null){
        String customerName = transactionObject.getTransactionCustomerName();
        customerName = customerName.replaceAll("[']","''");
        transactionObject.setTransactionCustomerName(customerName);
        }
       
        PaginatedResult paginatedResult = new PaginatedResult();

        //Create the SessionFactory, Session
        Session session = null;
        List transactionList = null;
        String finalPolicyQuery;
        String policyQuery;
        
        String tranType = transactionObject.getQuotePolicy();
        logger.debug("from tranType setting tranType" , tranType);
        String ForHistoryView= transactionObject.getForHistoryView();
        
        logger.debug("ForHistoryView" , ForHistoryView);
       
        boolean isQuoteEntered = false;
        boolean isPolicyEntered = false;
        boolean isSearchExact = transactionObject.isExactSearch();
        if(transactionObject.getQuoteEntered() != null 
        		&& ((com.Constant.CONST_QUOTE).equalsIgnoreCase(transactionObject.getQuoteEntered()))){
        	isQuoteEntered = true;
        }
        if(transactionObject.getPolicyEntered() != null 
        		&& (("Policy").equalsIgnoreCase(transactionObject.getPolicyEntered()))){
        	isPolicyEntered = true;
        }
        try {
            session = getSession();
            policyHashMap.put(AMEConstants.CTX_USER_ID,AMEPolicyConstants.USER_ID);
            if (transactionObject.isLastTransaction() == false) {
            	//START Change for PAS : Search transaction views change
            	selectQuoteQuery = POLICY_QUO_SEARCH_QUERY;
            	selectPolicyQuery = POLICY_SEARCH_QUERY;
            	viewCommentPolicyQuetry=VIEW_COMMENTS_POLICY_HISTORY;
            	viewCommentQuoQuetry=VIEW_COMMENTS_QUO_HISTORY;
            	
            	/*	selectQuoteQuery = AMEPolicyConstants.POLICY_QUO_SEARCH_QUERY;
                	selectPolicyQuery = AMEPolicyConstants.POLICY_SEARCH_QUERY;
                */
               //END Change for PAS : Search transaction views change
                
            	//Map entries which has to be dynamically added to where clause
                // for policy
                
                if(isQuoteEntered == true && isPolicyEntered == false){
                	if(isSearchExact){
                		policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_EXACT_NO);
                	}else{
                		policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_NO);
                	}
            	}else if(isQuoteEntered == false && isPolicyEntered == true){
            		if(isSearchExact){
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_EXACT_NO);
            		}else{
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_NO);
            		}
            	}else if(isQuoteEntered == true && isPolicyEntered == true){
            		if(tranType!=null && !((com.Constant.CONST_TRANSACTION).equalsIgnoreCase(tranType))){
            			if(isSearchExact){
            				policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_EXACT_NO);
            			}else{
            				policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_NO);
            			}
                	}
            		if(isSearchExact){
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_EXACT_NO);
            		}else{
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_NO);
            		}
            	}
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_BROKER_NAME, BROKER_CODE_OVERRIDDEN); // search using upper()
                //Instead of BR_Code ,BR_E_Name has been passed to the map as a change of CR 65
                // ADM 03.03.2010 Agent Profile (Release 2.5.2)
                //policyHashMap.put(AMEConstants.CTX_TRANSACTION_AGENT_NAME, AMEPolicyConstants.POLICY_AGENT_CODE);
                /*policyHashMap.put(AMEConstants.CTX_TRANSACTION_CUSTOMER_NAME,
                        AMEPolicyConstants.POLICY_CASH_CUSTOMER_NAME);*/
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_DISTRIBUTION_CODE,
                        AMEPolicyConstants.POLICY_DISTRIBUTION_CHANNEL);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_FROM, AMEPolicyConstants.POLICY_PREPARED_DT_FROM);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_TO, AMEPolicyConstants.POLICY_PREPARED_DT_TO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO_FROM,
                        AMEPolicyConstants.POLICY_CERT_NUM_FROM);
                policyHashMap
                        .put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO_TO, AMEPolicyConstants.POLICY_CERT_NUM_TO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_SCHEME, AMEPolicyConstants.POLICY_SCHEME);//Instead of Sch_code, sch_name has been passed to the map as a change of CR 65
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_EFFECTIVE_DATE, AMEPolicyConstants.POLICY_EFF_DATE);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_EXPIRY_DATE, AMEPolicyConstants.POLICY_EXPIRY_DT);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_LAST_MODIFIED_BY, AMEPolicyConstants.POLICY_MODIFIED_BY);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CREATED_BY, AMEPolicyConstants.POLICY_PREPARED_BY);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_STATUS, AMEPolicyConstants.POLICY_STATUS);                
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CLASS, AMEPolicyConstants.POLICY_CLASS_CODE);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_ENGINE_NO, AMEPolicyConstants.POLICY_ENGINE_NO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CHASSIS_NO, AMEPolicyConstants.POLICY_CHASSIS_NO);
                //policyHashMap.put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO, AMEPolicyConstants.POLICY_CERTIFICATE_NO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_REG_NO, AMEPolicyConstants.POLICY_REG_NO);
                //Multi branch handling. Location filter
                policyHashMap.put(CTX_TRANSACTION_LOC_CODE ,POLICY_LOCATION_CODE);
                policyHashMap.put(CTX_TRANSACTION_POLICY_TYPE, POLICY_TYPE); 
                
                
                //if(isPolicyEntered == true){
                	policyHashMap.put(CTX_TRANSACTION_MOBILE_NO, POLICY_MOBILE_NO); 
                	policyHashMap.put(CTX_TRANSACTION_POL_REF_NO, POLICY_POL_REF_NO); 
              // }
                	
                /* Added by ADM 07.08.2009 for Ticket # 8185 to Add Company name */
                //policyHashMap.put(AMEConstants.CTX_TRANSACTION_COMPANY_NAME,AMEPolicyConstants.POLICY_CASH_COMPANY_NAME_LAST_TRANSACTION);
               // policyHashMap.put(AMEConstants.CTX_TRANSACTION_REFERRED_TO,AMEPolicyConstants.POLICY_REFERRED_TO);
                finalPolicyQuery = AMEUtil.completeQuery(selectPolicyQuery, policyHashMap, transactionObject);
                
                //Added for CR 54 - Fuzzy Search
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                		"Policy query uncheck-->"+finalPolicyQuery);
                String POLICY_CASH_CUSTOMER_NAME_1 = "UPPER(CSH_E_NAME) like UPPER('%";
                String POLICY_CASH_CUSTOMER_NAME_2 = "%')";
                String customerEnteredNamePolicy = transactionObject.getTransactionCustomerName();
                String formingNamePartPolicy = "";
                int orFlagPolicy = 0;
                if(customerEnteredNamePolicy != null && !(("").equals(customerEnteredNamePolicy))){
                	String[] nameParts = customerEnteredNamePolicy.split("\\s");
                	for (int x=0; x<nameParts.length; x++){
                		if(orFlagPolicy == 0){
                		    formingNamePartPolicy = new StringBuffer().append(formingNamePartPolicy)
    	                     .append(com.Constant.CONST_AND_END).toString();
                		}
                		if(orFlagPolicy == 1){
                		    formingNamePartPolicy = new StringBuffer().append(formingNamePartPolicy)
    	                     .append(" OR ").toString();
                		}
                		formingNamePartPolicy = new StringBuffer().append(formingNamePartPolicy)
                    		.append(POLICY_CASH_CUSTOMER_NAME_1 + nameParts[x] + POLICY_CASH_CUSTOMER_NAME_2).toString();
                		orFlagPolicy = 1;
                	}
                	formingNamePartPolicy = new StringBuffer().append(formingNamePartPolicy)
                    	.append(" ) ").toString();
                	logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                    		"formingNamePartPolicy-->"+formingNamePartPolicy);
                	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery)
                    	.append(formingNamePartPolicy).toString();
                }
                
                
                
                String POLICY_CASH_COMPANY_NAME_1= com.Constant.CONST_UPPER_CSH_COMPANY_NAME_LIKE_UPPER_PERC_END;
                String POLICY_CASH_COMPANY_NAME_2 = "%')";
                String customerEnteredCompNamePolicy = transactionObject.getTransactionCompanyName();
                String formingCompNamePartPolicy = "";
                int orFlag1 = 0;
                if(customerEnteredCompNamePolicy != null && !(("").equals(customerEnteredCompNamePolicy))){
                	String[] nameParts = customerEnteredCompNamePolicy.split("\\s");
                	for (int x=0; x<nameParts.length; x++){
                		if(orFlag1 == 0){
                			formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
    	                     .append(com.Constant.CONST_AND_END).toString();
                		}
                		if(orFlag1 == 1){
                			formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
    	                     .append(" OR ").toString();
                		}
                		formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
                    		.append(POLICY_CASH_COMPANY_NAME_1 + nameParts[x] + POLICY_CASH_COMPANY_NAME_2).toString();
                		orFlag1 = 1;
                	}
                	formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
                    	.append(" ) ").toString();
                	logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                    		"formingCompNamePartPolicy-->"+formingCompNamePartPolicy);
                	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery)
                    	.append(formingCompNamePartPolicy).toString();
                }
                
                
                
               /* String POLICY_BROKER_NAME_1= "UPPER(BR_E_NAME) like UPPER('%";
                String POLICY_BROKER_NAME_2 = "%')";
                String customerEnteredBrokerNamePolicy = transactionObject.getBrokerName();
                String formingBrokerNamePartPolicy = "";
                int orFlagBro1 = 0;
                if(formingBrokerNamePartPolicy != null && !(("").equals(formingBrokerNamePartPolicy))){
                	String[] nameParts = customerEnteredBrokerNamePolicy.split("\\s");
                	for (int x=0; x<nameParts.length; x++){
                		if(orFlagBro1 == 0){
                			formingBrokerNamePartPolicy = new StringBuffer().append(formingBrokerNamePartPolicy)
    	                     .append(com.Constant.CONST_AND_END).toString();
                		}
                		if(orFlagBro1 == 1){
                			formingBrokerNamePartPolicy = new StringBuffer().append(formingBrokerNamePartPolicy)
    	                     .append(" OR ").toString();
                		}
                		formingBrokerNamePartPolicy = new StringBuffer().append(formingBrokerNamePartPolicy)
                    		.append(POLICY_BROKER_NAME_1 + nameParts[x] + POLICY_BROKER_NAME_2).toString();
                		orFlagBro1 = 1;
                	}
                	formingBrokerNamePartPolicy = new StringBuffer().append(formingBrokerNamePartPolicy)
                    	.append(" ) ").toString();
                	logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                    		"formingBrokerNamePartPolicy-->"+formingBrokerNamePartPolicy);
                	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery)
                    	.append(formingBrokerNamePartPolicy).toString();
                }
                */
                
                
                
                
                
                
            } else {
            	//START Change for PAS : Search transaction views change
            	 selectQuoteQuery = POLICY_QUO_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION;
                 selectPolicyQuery = POLICY_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION;
                /*
                  	selectQuoteQuery = AMEPolicyConstants.POLICY_QUO_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION;                 
                	selectPolicyQuery = AMEPolicyConstants.POLICY_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION;
                */
              //END Change for PAS : Search transaction views change
                policyHashMap.clear();
                //Map entries which has to be dynamically added to where clause
                // for policy
                
                policyHashMap.put(AMEConstants.CTX_USER_ID,AMEPolicyConstants.USER_ID);
                          
                
                if(isQuoteEntered == true && isPolicyEntered == false){
                	if(isSearchExact){
                		policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_EXACT_NO);
                	}else{
                		policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_NO);
                	}
            	}else if(isQuoteEntered == false && isPolicyEntered == true){
            		if(isSearchExact){
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_EXACT_NO);
            		}else{
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_NO);
            		}
            	}else if(isQuoteEntered == true && isPolicyEntered == true){
            		if(tranType!=null && !((com.Constant.CONST_TRANSACTION).equalsIgnoreCase(tranType))){
            			if(isSearchExact){
            				policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_EXACT_NO);
            			}else{
            				policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.POLICY_QUO_NO);
            			}
                	}
            		if(isSearchExact){
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_EXACT_NO);
            		}else{
            			policyHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.POLICY_NO);
            		}
            	}
            	policyHashMap.put(AMEConstants.CTX_TRANSACTION_BROKER_NAME, BROKER_CODE_OVERRIDDEN); // search using upper()
            	//Instead of BR_Code BR_E_Name has been passed to the map as a change of CR 65
            	// ADM 03.03.2010 Agent Profile (Release 2.5.2)
            	//policyHashMap.put(AMEConstants.CTX_TRANSACTION_AGENT_NAME, AMEPolicyConstants.POLICY_AGENT_CODE);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_DISTRIBUTION_CODE,
                        AMEPolicyConstants.POLICY_DISTRIBUTION_CHANNEL);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_FROM, AMEPolicyConstants.POLICY_PREPARED_DT_FROM);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_TO, AMEPolicyConstants.POLICY_PREPARED_DT_TO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO_FROM,
                        AMEPolicyConstants.POLICY_CERT_NUM_FROM);
                policyHashMap
                        .put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO_TO, AMEPolicyConstants.POLICY_CERT_NUM_TO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_SCHEME, AMEPolicyConstants.POLICY_SCHEME);//Instead of Sch_code, sch_name has been passed to the map as a change of CR 65
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_EFFECTIVE_DATE, AMEPolicyConstants.POLICY_EFF_DATE);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_EXPIRY_DATE, AMEPolicyConstants.POLICY_EXPIRY_DT);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CLASS, AMEPolicyConstants.POLICY_CLASS_CODE);

               // String intermediatePolicyQuery = AMEUtil.completeQuery(selectPolicyQuery, policyHashMap,
               //         transactionObject);
                /*intermediatePolicyQuery = intermediatePolicyQuery
                        + AMEPolicyConstants.INTERMEDIATE_POLICY_SEARCH_QUERY_BASED_ON_LAST_TRANSACTION;*/
                //policyHashMap.clear();
                //policyHashMap.put(AMEConstants.CTX_TRANSACTION_CUSTOMER_NAME, AMEPolicyConstants.POLICY_CASH_CUSTOMER);     
                /* Added by ADM 07.08.2009 for Ticket # 8185 to Add Company name */
            	//policyHashMap.put(AMEConstants.CTX_TRANSACTION_COMPANY_NAME,AMEPolicyConstants.POLICY_CASH_COMPANY_NAME);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_LAST_MODIFIED_BY, AMEPolicyConstants.POLICY_MODIFIED_BY_CHECKED);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CREATED_BY, AMEPolicyConstants.POLICY_PREPARED_BY_CHECKED);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_STATUS, AMEPolicyConstants.POLICY_STATUS_CHECKED);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_ENGINE_NO, AMEPolicyConstants.POLICY_ENGINE_NO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_CHASSIS_NO, AMEPolicyConstants.POLICY_CHASSIS_NO);
                //policyHashMap.put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO, AMEPolicyConstants.POLICY_CERTIFICATE_NO);
                policyHashMap.put(AMEConstants.CTX_TRANSACTION_REG_NO, AMEPolicyConstants.POLICY_REG_NO);
                //Multi branch handling. Location filter
                policyHashMap.put(CTX_TRANSACTION_LOC_CODE ,POLICY_LOCATION_CODE);
                policyHashMap.put(CTX_TRANSACTION_POLICY_TYPE, POLICY_TYPE); 
                
                
               // if(isPolicyEntered == true){
                	policyHashMap.put(CTX_TRANSACTION_MOBILE_NO, POLICY_MOBILE_NO); 
                	policyHashMap.put(CTX_TRANSACTION_POL_REF_NO, POLICY_POL_REF_NO); 
               //}
                //policyHashMap.put(AMEConstants.CTX_TRANSACTION_REFERRED_TO,AMEPolicyConstants.POLICY_REFERRED_TO);
                //finalPolicyQuery = AMEUtil.completeQuery(intermediatePolicyQuery, policyHashMap, transactionObject);
                finalPolicyQuery = AMEUtil.completeQuery(selectPolicyQuery, policyHashMap, transactionObject);
                
                //Added for CR 54 - Fuzzy Search
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                		"Policy query check-->"+finalPolicyQuery);
                String POLICY_CASH_CUSTOMER_NAME_3 = "UPPER(CSH_E_NAME) like UPPER('%";
                String POLICY_CASH_CUSTOMER_NAME_4 = "%')";
                String customerEnteredNamePolicy1 = transactionObject.getTransactionCustomerName();
                String formingNamePartPolicy1 = "";
                int orFlag = 0;
                if(customerEnteredNamePolicy1 != null && !(("").equals(customerEnteredNamePolicy1))){
                	String[] nameParts = customerEnteredNamePolicy1.split("\\s");
                	for (int x=0; x<nameParts.length; x++){
                		if(orFlag == 0){
                		    formingNamePartPolicy1 = new StringBuffer().append(formingNamePartPolicy1)
    	                     .append(com.Constant.CONST_AND_END).toString();
                		}
                		if(orFlag == 1){
                		    formingNamePartPolicy1 = new StringBuffer().append(formingNamePartPolicy1)
    	                     .append(" OR ").toString();
                		}
                		formingNamePartPolicy1 = new StringBuffer().append(formingNamePartPolicy1)
                    		.append(POLICY_CASH_CUSTOMER_NAME_3 + nameParts[x] + POLICY_CASH_CUSTOMER_NAME_4).toString();
                		orFlag = 1;
                	}
                	formingNamePartPolicy1 = new StringBuffer().append(formingNamePartPolicy1)
                    	.append(" ) ").toString();
                	logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                    		"formingNamePartPolicy1-->"+formingNamePartPolicy1);
                	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery)
                    	.append(formingNamePartPolicy1).toString();
                }
                
                
                
                String POLICY_CASH_COMPANY_NAME_3= com.Constant.CONST_UPPER_CSH_COMPANY_NAME_LIKE_UPPER_PERC_END;
                String POLICY_CASH_COMPANY_NAME_4 = "%')";
                String customerEnteredCompNamePolicy1 = transactionObject.getTransactionCompanyName();
                String formingCompNamePartPolicy1 = "";
                int orFlag2 = 0;
                if(customerEnteredCompNamePolicy1 != null && !(("").equals(customerEnteredCompNamePolicy1))){
                	String[] nameParts = customerEnteredCompNamePolicy1.split("\\s");
                	for (int x=0; x<nameParts.length; x++){
                		if(orFlag2 == 0){
                			formingCompNamePartPolicy1 = new StringBuffer().append(formingCompNamePartPolicy1)
    	                     .append(com.Constant.CONST_AND_END).toString();
                		}
                		if(orFlag2 == 1){
                			formingCompNamePartPolicy1 = new StringBuffer().append(formingCompNamePartPolicy1)
    	                     .append(" OR ").toString();
                		}
                		formingCompNamePartPolicy1 = new StringBuffer().append(formingCompNamePartPolicy1)
                    		.append(POLICY_CASH_COMPANY_NAME_3 + nameParts[x] + POLICY_CASH_COMPANY_NAME_4).toString();
                		orFlag2 = 1;
                	}
                	formingCompNamePartPolicy1 = new StringBuffer().append(formingCompNamePartPolicy1)
                    	.append(" ) ").toString();
                	logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                    		"formingCompNamePartPolicy1-->"+formingCompNamePartPolicy1);
                	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery)
                    	.append(formingCompNamePartPolicy1).toString();
                }
                  
                ///
            }

            Integer currentPage = transactionObject.getCurrentPage();
            List resultQuoteList = null;
            List finalTransactionList = null;

            resultQuoteList = new ArrayList();
            transactionList = new ArrayList();
            AMEUtil util = new AMEUtil();

            quoteHashMap.clear();
            //Map entries which has to be dynamically added to where clause for
            // quote
            if(isQuoteEntered == true && isPolicyEntered == false){
            	if(isSearchExact){
            		quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.QUOTE_QUOTATION_EXACT_NO);
            	}else{
            		quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.QUOTE_QUOTATION_NO);
            	}
        	}else if(isQuoteEntered == false && isPolicyEntered == true){
        		if(isSearchExact){
        			quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.QUOTE_REF_POLICY_EXACT_NO);
        		}else{
        			quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.QUOTE_REF_POLICY_NO);
        		}
        	}else if(isQuoteEntered == true && isPolicyEntered == true){
        		if(isSearchExact){
        			quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.QUOTE_QUOTATION_EXACT_NO);
        		}else{
        			quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER, AMEPolicyConstants.QUOTE_QUOTATION_NO);
        		}
        		if(tranType!=null && !((com.Constant.CONST_TRANSACTION).equalsIgnoreCase(tranType))){
        			if(isSearchExact){
        				quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.QUOTE_REF_POLICY_EXACT_NO);
        			}else{
        				quoteHashMap.put(AMEConstants.CTX_TRANSACTION_NUMBER_POLICY, AMEPolicyConstants.QUOTE_REF_POLICY_NO);
        			}
        		}
        	}
            if( (isQuoteEntered == true && 
            		(Utils.isEmpty(transactionObject.getPolReferenceNo()) && Utils.isEmpty(transactionObject.getMobileNo())))
            		|| (isPolicyEntered == true
            				&& 
            				(Utils.isEmpty(transactionObject.getPolReferenceNo()) && Utils.isEmpty(transactionObject.getMobileNo()))) ){
                   	quoteHashMap.put(AMEConstants.CTX_USER_ID,AMEPolicyConstants.USER_ID);
            }
        	
        	quoteHashMap.put(AMEConstants.CTX_TRANSACTION_BROKER_NAME, BROKER_CODE_OVERRIDDEN); // search using upper()
        	//Instead of BR_Code, BR_E_Name has  passed to the map as a change of CR 65 
        	// ADM 03.03.2010 Agent Profile (Release 2.5.2)
        	//quoteHashMap.put(AMEConstants.CTX_TRANSACTION_AGENT_NAME, AMEPolicyConstants.QUOTE_AGENT_CODE);
            //quoteHashMap.put(AMEConstants.CTX_TRANSACTION_CUSTOMER_NAME, AMEPolicyConstants.QUOTE_CASH_CUSTOMER_NAME);
        	/* Added by ADM 07.08.2009 for Ticket # 8185 to Add Company name */
        	//quoteHashMap.put(AMEConstants.CTX_TRANSACTION_COMPANY_NAME, AMEPolicyConstants.QUOTE_CASH_COMPANY_NAME);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_DISTRIBUTION_CODE,
                    AMEPolicyConstants.QUOTE_DISTRIBUTION_CHANNEL);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_FROM, AMEPolicyConstants.QUOTE_PREPARED_DT_FROM);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_TO, AMEPolicyConstants.QUOTE_PREPARED_DT_TO);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO_FROM, AMEPolicyConstants.QUOTE_CERT_NUM_FROM);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO_TO, AMEPolicyConstants.QUOTE_CERT_NUM_TO);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_SCHEME, AMEPolicyConstants.QUOTE_SCHEME);//Instead of Sch_code, sch_name has been passed to the map as a change of CR 65
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_EFFECTIVE_DATE, AMEPolicyConstants.QUOTE_EFF_DATE);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_EXPIRY_DATE, AMEPolicyConstants.QUOTE_EXPIRY_DT);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_LAST_MODIFIED_BY, AMEPolicyConstants.QUOTE_MODIFIED_BY);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_CREATED_BY, AMEPolicyConstants.QUOTE_PREPARED_BY);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_STATUS, AMEPolicyConstants.QUOTE_STATUS);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_CLASS, AMEPolicyConstants.QUOTE_CLASS_CODE);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_ENGINE_NO, AMEPolicyConstants.QUOTE_ENGINE_NO);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_CHASSIS_NO, AMEPolicyConstants.QUOTE_CHASSIS_NO);
            //quoteHashMap.put(AMEConstants.CTX_TRANSACTION_CERTIFICATE_NO, AMEPolicyConstants.QUOTE_CERTIFICATE_NO);
            quoteHashMap.put(AMEConstants.CTX_TRANSACTION_REG_NO, AMEPolicyConstants.QUOTE_REG_NO);
            //Multi branch handling. Location filter
            quoteHashMap.put(CTX_TRANSACTION_LOC_CODE ,POLICY_LOCATION_CODE);
            quoteHashMap.put(CTX_TRANSACTION_POLICY_TYPE, POLICY_TYPE); 
            
           // if(isQuoteEntered == true){
            	quoteHashMap.put(CTX_TRANSACTION_MOBILE_NO, POLICY_MOBILE_NO); 
            	quoteHashMap.put(CTX_TRANSACTION_POL_REF_NO, POLICY_POL_REF_NO); 
           //}
           	
            String finalQuoteQuery = AMEUtil.completeQuery(selectQuoteQuery, quoteHashMap, transactionObject);
            
            //Added for CR 54 - Fuzzy Search
            logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
            		"Quote Query b4-->"+finalQuoteQuery);
            //String QUOTE_CASH_CUSTOMER_NAME_1 = "UPPER(CSH_E_NAME) like UPPER('%";
            
            /* Comparing for exact name of insured rather than using 'like' operator. */
            String QUOTE_CASH_CUSTOMER_NAME_1 = " trim(CSH_E_NAME) = '";
            String QUOTE_CASH_CUSTOMER_NAME_2 = "'";
            String customerEnteredName = transactionObject.getTransactionCustomerName();
            String formingNamePart = "";
            int orFlag = 0;
            if(customerEnteredName != null && !(("").equals(customerEnteredName))){
            	/* Start : search customer in transaction search */
            	/*
            	String[] nameParts = customerEnteredName.split("\\s");
            	for (int x=0; x<nameParts.length; x++){
            		if(orFlag == 0){
            			formingNamePart = new StringBuffer().append(formingNamePart)
	                     .append(com.Constant.CONST_AND_END).toString();
            		}
            		if(orFlag == 1){
            			formingNamePart = new StringBuffer().append(formingNamePart)
	                     .append(" OR ").toString();
            		}
            		formingNamePart = new StringBuffer().append(formingNamePart)
                		.append(QUOTE_CASH_CUSTOMER_NAME_1 + nameParts[x] + QUOTE_CASH_CUSTOMER_NAME_2).toString();
            		orFlag = 1;
            	}
            	formingNamePart = new StringBuffer().append(formingNamePart)
                	.append(" ) ").toString();
            	logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                		"formingNamePart-->"+formingNamePart);
            	finalQuoteQuery = new StringBuffer().append(finalQuoteQuery)
                	.append(formingNamePart).toString();
                	*/
            	formingNamePart = new StringBuffer().append(formingNamePart).append(" AND ").append(QUOTE_CASH_CUSTOMER_NAME_1 + customerEnteredName + QUOTE_CASH_CUSTOMER_NAME_2).toString();
            	finalQuoteQuery = new StringBuffer().append(finalQuoteQuery)
            	.append(formingNamePart).toString();
            	/* End : search customer in transaction search */
            }
            String QUOTE_CASH_COMPANY_NAME_1= com.Constant.CONST_UPPER_CSH_COMPANY_NAME_LIKE_UPPER_PERC_END;
            String QUOTE_CASH_COMPANY_NAME_2 = "%')";
            String customerEnteredCompNamePolicy = transactionObject.getTransactionCompanyName();
            String formingCompNamePartPolicy = "";
            int orFlag2 = 0;
            if(customerEnteredCompNamePolicy != null && !(("").equals(customerEnteredCompNamePolicy))){
            	String[] nameParts = customerEnteredCompNamePolicy.split("\\s");
            	for (int x=0; x<nameParts.length; x++){
            		if(orFlag2 == 0){
            			formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
	                     .append(com.Constant.CONST_AND_END).toString();
            		}
            		if(orFlag2 == 1){
            			formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
	                     .append(" OR ").toString();
            		}
            		formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
                		.append(QUOTE_CASH_COMPANY_NAME_1 + nameParts[x] + QUOTE_CASH_COMPANY_NAME_2).toString();
            		orFlag2 = 1;
            	}
            	formingCompNamePartPolicy = new StringBuffer().append(formingCompNamePartPolicy)
                	.append(" ) ").toString();
            	logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                		"formingNamePartPolicy1-->"+formingCompNamePartPolicy);
            	finalQuoteQuery = new StringBuffer().append(finalQuoteQuery)
                	.append(formingCompNamePartPolicy).toString();
            }           
            
            Query query = null;
            Query count= null;//Added to get result size.
            String combinedQueryFinal = "";
            String combinedQueryFinalForViewHistory = "";

            /** Added for Transaction Type drop down functionalitt* */

            logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                    "*********** TRANSACTION SEARCH *****************");
            logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, "TRANSACTION TYPE ----> "
                    + transactionObject.getQuotePolicy());
            
            
            /**
             *  E_PLATFORM Logic starts here
             * This code is added for E_PLATFORM to filter the result for policies/quote's created in PAS SBS
             */
            finalQuoteQuery = com.rsaame.kaizen.pas.logicoverride.KaizenLogicOverride.quoteListForSrcTranOverride(finalQuoteQuery,ServiceContext.getUser().getUserId(),ServiceContext.getUser().getProfile());
            finalPolicyQuery = com.rsaame.kaizen.pas.logicoverride.KaizenLogicOverride.quoteListForSrcTranOverride(finalPolicyQuery,ServiceContext.getUser().getUserId(),ServiceContext.getUser().getProfile());
            
            // adding USER_ID clause to restrict PL/CL user data 
            if( isQuoteEntered == true && 
            		!Utils.isEmpty(transactionObject.getPolReferenceNo()) || !Utils.isEmpty(transactionObject.getMobileNo()) ){
            	 finalQuoteQuery = new StringBuffer().append(finalQuoteQuery).append(" AND").toString();
                 finalQuoteQuery = new StringBuffer().append(finalQuoteQuery).append(" (USER_ID="+ServiceContext.getUser().getUserId()).toString();
                 finalQuoteQuery = new StringBuffer().append(finalQuoteQuery).append(" OR").toString();
                 finalQuoteQuery = new StringBuffer().append(finalQuoteQuery).append(" USER_ID= "+Utils.getSingleValueAppConfig("MISLIVE_USER")+")").toString();
                
            }else {
            	finalQuoteQuery = new StringBuffer().append(finalQuoteQuery).append(" AND").toString();
            	finalQuoteQuery = new StringBuffer().append(finalQuoteQuery).append(" USER_ID="+ServiceContext.getUser().getUserId()).toString();
            
            }
            
            
            if( isQuoteEntered == true && 
            		!Utils.isEmpty(transactionObject.getPolReferenceNo()) || !Utils.isEmpty(transactionObject.getMobileNo()) ){
            	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery).append(" AND").toString();
            	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery).append(" (USER_ID="+ServiceContext.getUser().getUserId()).toString();
            	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery).append(" OR").toString();
            	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery).append(" USER_ID= "+Utils.getSingleValueAppConfig("MISLIVE_USER")+")").toString();
                
            }else {
            	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery).append(" AND").toString();
            	finalPolicyQuery = new StringBuffer().append(finalPolicyQuery).append(" USER_ID="+ServiceContext.getUser().getUserId()).toString();
            	
            }
            /*
             * E_PLATFORM Logic ends here
             */
            // Added Logic For View comment history CR ID 108302
            if(transactionObject.getForHistoryView()!=null && transactionObject.getForHistoryView().equals("viewHistory")){
            	
            
            	System.out.println("IN ViewPOLICYHistoryquery-- >**********");
            	viewCommentPolicyQuetry=new StringBuffer().append(viewCommentPolicyQuetry).append(" POL_POLICY_NO="+transactionObject.getTransactionNumberPolicy()).toString();
            	 
            	System.out.println("M7 ViewPOLICYHistoryquery-- >"+viewCommentPolicyQuetry);
            	    
            
            	System.out.println("IN ViewQUOHistoryquery-- >**********");
                viewCommentQuoQuetry=new StringBuffer().append(viewCommentQuoQuetry).append(" POL_QUOTATION_NO="+transactionObject.getTransactionNumber()).toString();
        
                System.out.println("M7 ViewQUOHistoryquery-- >"+viewCommentQuoQuetry);
                	  
         
            	
                combinedQueryFinalForViewHistory = viewCommentQuoQuetry + com.Constant.CONST_UNION_ALL + " " + viewCommentPolicyQuetry;
                   query = session.createSQLQuery(combinedQueryFinalForViewHistory);
                   System.out.println("M7 finalPolicyQuery-- _1"+query);
                   count = session.createSQLQuery(com.Constant.CONST_SELECT_COUNT_1_FROM_END + viewCommentQuoQuetry + "UNION ALL" + " "
                           + viewCommentPolicyQuetry + ")");
            		
            	}//Ended logic For View comment history 
            
             else if (transactionObject.getQuotePolicy() != null
                    && (com.Constant.CONST_QUOTE).equalsIgnoreCase(transactionObject.getQuotePolicy())) {
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, " b ---> "
                        + finalQuoteQuery);
                query = session.createSQLQuery(finalQuoteQuery+SORT_BY_EFF_DATE_ENDT_NO);
                System.out.println("M7 finalPolicyQuery-- _2"+query);
                count = session.createSQLQuery(com.Constant.CONST_SELECT_COUNT_1_FROM_END + finalQuoteQuery + ")");
            } else if (transactionObject.getQuotePolicy() != null
                    && ("Policy").equalsIgnoreCase(transactionObject.getQuotePolicy())) {
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, " FinalPolicyQuery ---> "
                        + finalPolicyQuery);
                query = session.createSQLQuery(finalPolicyQuery+SORT_BY_EFF_DATE_ENDT_NO);
                System.out.println("M7 finalPolicyQuery-- _3"+query);
                count = session.createSQLQuery(com.Constant.CONST_SELECT_COUNT_1_FROM_END + finalPolicyQuery + ")");
            } else {
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, " FinalQuery ---> " + finalQuoteQuery
                        + " UNION AL_1" + " " + finalPolicyQuery);
                combinedQueryFinal = finalQuoteQuery + com.Constant.CONST_UNION_ALL + " " + finalPolicyQuery;
                query = session.createSQLQuery(combinedQueryFinal + SORT_BY_EFF_DATE_ENDT_NO);
                System.out.println("M7 finalPolicyQuery-- _4"+query);
                count = session.createSQLQuery(com.Constant.CONST_SELECT_COUNT_1_FROM_END + finalQuoteQuery + "UNION ALL" + " "
                        + finalPolicyQuery + ")");
            }

            finalTransactionList = new ArrayList();
            int size=0;
            BigDecimal resultCount;

            logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,
                    "transactionObject.getNumberOfRecords().intValue() :"
                            + transactionObject.getNumberOfRecords().intValue());

            if (transactionObject.getNumberOfRecords().intValue() == 0) {
                resultCount = (BigDecimal) count.uniqueResult();
                size = resultCount.intValue();

                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, "Size of Result::  " + size);

                transactionObject.setNumberOfRecords(Integer.valueOf(size));
                getPaginatedResult(query, transactionObject.getCurrentPage(), size, paginatedResult);
                
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,"SEARCH TYPE      ---->"+ tranType+"<---_1");
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,"EXACT SEARCH     ----->"+isSearchExact+"<---_2");
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,"LAST TRANSACTION ----->"+transactionObject.isLastTransaction()+"<---_3");
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,"Quote Entered    ----->"+isQuoteEntered+"<----QUOTE NUMBER="+transactionObject.getTransactionNumber()+"<---_4");
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,"Policy Entered   ----->"+isPolicyEntered+"<----POLICY NUMBER="+transactionObject.getTransactionNumberPolicy()+"<---_5");       
                
                resultQuoteList = query.list();
                System.out.println("M7 query-- >"+query);
                logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION,"DUPLICATE CHECK ---- > "+query +" Phase 2");
                if (size > paginatedResult.getRecordsPerPage().intValue()) {
                    /**
                     * If the size of the list is greater than the no. of
                     * records per page finalTransactionList will contain the
                     * no.of records to be displayed in the selected page
                     */
                    int firstPageRecords = (paginatedResult.getRecordsPerPage().intValue() * paginatedResult
                            .getCurrentPage().intValue())
                            - paginatedResult.getRecordsPerPage().intValue();
                    for (int i = firstPageRecords; i < firstPageRecords
                            + paginatedResult.getRecordsPerPage().intValue(); i++) {
                        Object object = resultQuoteList.get(i);
                        finalTransactionList.add(object);
                    }
                } else {
                    //if the size is less than the total no of records per page
                    //display the whole list
                    Iterator iterator = resultQuoteList.iterator();
                    while (iterator.hasNext()) {
                        Object object = (Object) iterator.next();
                        finalTransactionList.add(object);
                    }
                }
            } else {
                size = transactionObject.getNumberOfRecords().intValue();
                getPaginatedResult(query, transactionObject.getCurrentPage(), size, paginatedResult);
                finalTransactionList = query.list();
            }
            List resultList = new ArrayList();
            
            	 resultList = getResultQuoteList(finalTransactionList,tranType);
          
            paginatedResult.setObjectArray(resultList.toArray());

        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } catch (DataAccessException dataAccessException) {
            throw new DataAccessException(dataAccessException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, "Length of list:" + transactionList.size());
        logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION, "Method En_1");
        return paginatedResult;

    }

    /**
     * Method getResultQuoteList() returns list of transaction objects
     * 
     * @param transactionObject
     * @return transactionList
     * @throws DataAccessException
     */
    private List getResultQuoteList(List resultQuoteList ,String tranType) throws DataAccessException {

        logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION + AMEConstants.DOT
                + AMEConstants.CTX_GET_RESULT_QUOTE_LIST, "Method Star_3");
        List transactionList = new ArrayList();
        Iterator itr = resultQuoteList.iterator();
        Object[] row = null;

        while (itr.hasNext()) {
            Transaction transaction = new Transaction();
            row = (Object[]) itr.next();
            //row[0] refers to CompleteName
            transaction.setCompleteName(AMEUtil.ObjectToString(row[0]));
            
            /* Mapping the row[0] to transactionCustomerName as it is referred as transactionCustomerName in TransactionVO of PAS */
            transaction.setTransactionCustomerName(AMEUtil.ObjectToString(row[0]));
            
            transaction.setBrokerName(AMEUtil.ObjectToString(row[24]));// set Broker Name search code for CR 65
            
            transaction.setTransactionScheme(AMEUtil.ObjectToString(row[25]));//Set Scheme Name search for CR 65
            
            transaction.setTransactionCompanyName(AMEUtil.ObjectToString(row[21]));
                        
            //row[1] refers to TransactionNumber
            transaction.setTransactionNumber(AMEUtil.ObjectToStringSearch(row[1]));

            //row[2] refers to EndorsementNumber
            transaction.setTransactionEndorsementNumber(AMEUtil.ObjectToStringSearch(row[2]));
            //row[3] refers to LastModifiedBy
            transaction.setTransactionLastModifiedBy(AMEUtil.ObjectToString(row[3]));
            //row[4] refers to PolicyNumber
            transaction.setTransactionPolicyNumber(AMEUtil.ObjectToStringSearch(row[4]));
            //row[5] refers to PolicyType
            transaction.setTransactionPolicyType(AMEUtil.ObjectToString(row[5]));
            //row[6] refers to TransactionType
            transaction.setTransactionType(AMEUtil.ObjectToString(row[6]));
            //row[7] refers to TransactionDateTime
            transaction.setTransactionDateTime(AMEUtil.ObjectToStringSearch(row[7]));
            //row[8] refers to FinalPremium
            if (row[8] != null) {
            	//transaction.setTransactionFinalPremium(KaizenLogicOverride.round(new BigDecimal((row[8].toString())), 2, false));//Modified for CurrencyHandler
            	transaction.setTransactionFinalPremium(new BigDecimal( Currency.getUnformattedScaledCurrency(new BigDecimal(row[8].toString()))));//Modified for CurrencyHandler
            } else {
                transaction.setTransactionFinalPremium(new BigDecimal(.00));
            }
            //row[9] refers to Status
            transaction.setTransactionStatus(AMEUtil.ObjectToString(row[9]));
            //row[10] refers to ExpiryDate
            transaction.setExpiryDate(AMEUtil.ObjectToStringSearch(row[10]));
            //row[11] refers to EffectiveDate
            transaction.setEffectiveDate(AMEUtil.ObjectToStringSearch(row[11]));

            //row[12] refers to EndtId
            transaction.setTransactionEndtId(AMEUtil.ObjectToString(row[12].toString()));
            //row[13] refers to SumInsured
            if (row[13] != null) {
                //transaction.setTransactionSumInsured(KaizenLogicOverride.round(new BigDecimal((row[13].toString())), 2, false));//Modified for CurrencyHandler
            	 transaction.setTransactionSumInsured(new BigDecimal( Currency.getUnformattedScaledCurrency(new BigDecimal(row[13].toString()))));//Modified for CurrencyHandler
            } else {
                transaction.setTransactionSumInsured(new BigDecimal(.00));
            }
            //row[14] refers to Risk
            if (row[14] != null) {
                transaction.setTransactionRisk(AMEUtil.ObjectToString(row[14].toString()));
            } else {
                transaction.setTransactionRisk(STRING_CHECK);
            }
            //row[15] refers to TransactionTypeCode
            transaction.setTransactionTypeCode(AMEUtil.ObjectToString(row[15].toString()));
            //row[16] refers to SuspendTransactionIndcator
            if (row[16] != null) {
            	transaction.setSuspendTransactionInd(AMEUtil.ObjectToString(row[16].toString()));
            } else {
                transaction.setSuspendTransactionInd("");
            }
            //row[17] refers to Tariff Code
            if (row[17] != null) {
            	transaction.setPolicyTariffCode(AMEUtil.ObjectToString(row[17].toString()));
            }else {
            	transaction.setPolicyTariffCode(STRING_CHECK); 
            }
            //row[18] refers to Policy issue Hour
            if (row[18] != null) {
            	transaction.setPolicyIssueHour(AMEUtil.ObjectToString(row[18].toString())); 
            } else {
            	transaction.setPolicyIssueHour(STRING_CHECK); 
            }	
            //row[19] refers to Policy renewal counter - CR 33
            if (row[19] != null) {
            	transaction.setPolRenewalCounter(AMEUtil.ObjectToString(row[4].toString())+"/"+AMEUtil.ObjectToString(row[19].toString())); 
            } else {
            	transaction.setPolRenewalCounter(AMEUtil.ObjectToString(row[4].toString())); 
            }
            //row[20] refers Policy Approver
         
            if (row[20] != null && tranType.equalsIgnoreCase(com.Constant.CONST_QUOTE)) {
            	logger.debug("from query setting approver" , AMEUtil.ObjectToString(row[20]));
            	transaction.setPolicyApprover(AMEUtil.ObjectToString(row[20]));          
            }
            
            if(row.length == 29){
	            if(row[28]!=null){
	            	transaction.setComments(AMEUtil.ObjectToString(row[28]));
	            }
            }
            // set new location code for CR 65
            BeanFactory beanFactory = BeanFactory.getInstance();
            LocationDAO locationDAO= (LocationDAO) beanFactory.getBean("locationDAO");
            AMEBaseDAO ameBaseDAO = (AMEBaseDAO) beanFactory.getBean("ameBaseDAO");
            if(row[23] != null){
            	try{
            		logger.debug("row selected from location" , AMEUtil.ObjectToString(row[23]));
            		transaction.setLocationCode(Integer.valueOf(AMEUtil.ObjectToString(row[23])));
            		Integer omanbdrInd = ameBaseDAO.getOmanBorderInd(transaction.getLocationCode());
            		transaction.setBorderInd(omanbdrInd);
            	} catch(Exception exp){
            	    logger.debug("Getting error while setting location code", exp);
            	}
            }
            if(row[23] != null){
            	try{
            		logger.debug("row selected from location" , AMEUtil.ObjectToString(row[23]));
            		String loc = AMEUtil.ObjectToString(row[23]);
            		/*BeanFactory beanFactory = BeanFactory.getInstance();
            		LocationDAO locationDAO = (LocationDAO) beanFactory.getBean("locationDAO");
            		SessionFactory sessionFactory = null;
                    Session session = null;*/
            		locationDAO.getLocationDesc(loc);
            		transaction.setLocationName(locationDAO.getLocationDesc(loc));
            		/*if(loc.equals("20") || loc == "20"){
            			transaction.setLocationName("Dubai");
            		}else if(loc.equals("22") || loc == "22"){
            			transaction.setLocationName("Sharjah");
             		}*/
            	} catch(Exception exp){
            		exp.printStackTrace();
            	}
            }

                        
            transactionList.add(transaction);
        }
        logger.debug(AMEConstants.CTX_GET_LIST_FOR_SEARCH_TRANSACTION + AMEConstants.DOT
                + AMEConstants.CTX_GET_RESULT_QUOTE_LIST, "End Metho_2");
        return transactionList;
    }

    /**
     * param gatCode return GlAccInterface
     */
    public GlAccInterface getglAccountInfo(Integer gatCode) throws DataAccessException {
        logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "Method Entere_1");
        Session session = null;
        Query receiptQuery = null;
        GlAccInterface glAccInterface = new GlAccInterface(); 
       
        try {
        	
            session = getSession();
            Integer countryCode = ApplicationContext.getCountryCode();
            Integer regionCode = ApplicationContext.getRegionCode();
            
            Integer branchCode = new Integer(ServiceContext.getLocation());
            String finalQuery = com.Constant.CONST_FROM_GLACCINTERFACE_GLACCINTERFACE_WHERE_GLACCINTERFACE_GLACCTYPE_END + gatCode +com.Constant.CONST_AND_DECODE_GLI_CTY_CODE_1_END+countryCode+com.Constant.CONST_GLI_CTY_CODE_END+countryCode +com.Constant.CONST_AND_DECODE_GLI_REG_CODE_1_END+regionCode+com.Constant.CONST_GLI_REG_CODE_END+regionCode+com.Constant.CONST_AND_DECODE_GLI_LOC_CODE_1_END+branchCode+com.Constant.CONST_GLI_LOC_CODE_END+branchCode;
       
              glAccInterface = (GlAccInterface) session.createQuery(finalQuery).uniqueResult();
       
        
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "Method exite_1");
        return glAccInterface;
    }
    
//	 Release 4.0 Oman Rollout Changes (Bank name specific to Processing Branch For Receipt Header) 
    
    public GlAccInterface getglAccountInfo(Integer gatCode , PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "Method Entere_2");
        Session session = null;
        Query receiptQuery = null;
        GlAccInterface glAccInterface = new GlAccInterface(); 
       
        try {
        	
            session = getSession();
            Integer countryCode = ApplicationContext.getCountryCode();
            Integer regionCode = ApplicationContext.getRegionCode();
            
          //   Integer branchCode = new Integer(ServiceContext.getLocation());
            Integer procBranchCode = policyQuo.getPolicyBranchCode();
            String finalQuery = com.Constant.CONST_FROM_GLACCINTERFACE_GLACCINTERFACE_WHERE_GLACCINTERFACE_GLACCTYPE_END + gatCode +com.Constant.CONST_AND_DECODE_GLI_CTY_CODE_1_END+countryCode+com.Constant.CONST_GLI_CTY_CODE_END+countryCode +com.Constant.CONST_AND_DECODE_GLI_REG_CODE_1_END+regionCode+com.Constant.CONST_GLI_REG_CODE_END+regionCode+com.Constant.CONST_AND_DECODE_GLI_LOC_CODE_1_END+procBranchCode+com.Constant.CONST_GLI_LOC_CODE_END+procBranchCode;
       
              glAccInterface = (GlAccInterface) session.createQuery(finalQuery).uniqueResult();
       
        
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "Method exite_2");
        return glAccInterface;
    }

    
    
    public GlAccInterface getglAccountInfo(Integer gatCode , Policy policy) throws DataAccessException {
        logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "Method Entere_3");
        Session session = null;
        Query receiptQuery = null;
        GlAccInterface glAccInterface = new GlAccInterface(); 
        String finalQuery = null;
       
        try {
        	
            session = getSession();
            Integer countryCode = ApplicationContext.getCountryCode();
            Integer regionCode = ApplicationContext.getRegionCode();
            
          //   Integer branchCode = new Integer(ServiceContext.getLocation());
            if(policy.getPolicyBranchCode()!=null){
            Integer procBranchCode = policy.getPolicyBranchCode();
             finalQuery = com.Constant.CONST_FROM_GLACCINTERFACE_GLACCINTERFACE_WHERE_GLACCINTERFACE_GLACCTYPE_END + gatCode +com.Constant.CONST_AND_DECODE_GLI_CTY_CODE_1_END+countryCode+com.Constant.CONST_GLI_CTY_CODE_END+countryCode +com.Constant.CONST_AND_DECODE_GLI_REG_CODE_1_END+regionCode+com.Constant.CONST_GLI_REG_CODE_END+regionCode+com.Constant.CONST_AND_DECODE_GLI_LOC_CODE_1_END+procBranchCode+com.Constant.CONST_GLI_LOC_CODE_END+procBranchCode;
            }
              glAccInterface = (GlAccInterface) session.createQuery(finalQuery).uniqueResult();
       
        
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "Method exite_3");
        return glAccInterface;
    }
    /**
     * param countryCode param regionCode param locationCode param ccCode param
     * tot_Acc_Code param gl_code return String
     * 
     * @throws DataAccessException
     */
    public String getGlDescription(Integer countryCode, Integer regionCode, Integer locationCode, Integer ccCode,
            BigDecimal tolAccCode, Long glCode) throws DataAccessException {
        logger.debug(AMEConstants.CTX_IS_DESCRIPTION, "Method Entere_4");
        Session session = null;

        session = getSession();

        String glDescription;
        try {
            String getGLDesc = getQuery(AMEConstants.GET_GL_DESCRIPTION);
            logger.debug(AMEConstants.CTX_IS_DESCRIPTION, "Query:_2" + getGLDesc);
            Query query = session.createQuery(getGLDesc);
            query.setInteger("countryCode", countryCode.intValue());
            query.setInteger("regionCode", regionCode.intValue());
            query.setInteger("locationCode", locationCode.intValue());
            query.setInteger("ccCode", ccCode.intValue());
            query.setInteger("tolAccCode", tolAccCode.intValue());
            query.setLong(com.Constant.CONST_GLCODE, glCode.intValue());
            glDescription = (String) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_IS_DESCRIPTION, "Method Exite_1");
        return glDescription;
    }

    /**
     * param countryCode param regionCode param locationCode param ccCode param
     * tot_Acc_Code param gl_code return boolean
     * 
     * @throws DataAccessException
     */
    public boolean isGlCodePresent(Integer countryCode, Integer regionCode, Integer locationCode, Integer ccCode,
            BigDecimal tolAccCode, Long glCode) throws DataAccessException {
        logger.debug(AMEConstants.CTX_IS_CODE_PRESENT, "Method Entere_5");
        boolean isGlCodePresent = false;
        Session session = null;

        session = getSession();

        try {
            String glCodePresent = getQuery(AMEConstants.IS_GL_CODE_PRESENT);
            logger.debug(AMEConstants.CTX_IS_CODE_PRESENT, "Query:_3" + glCodePresent);
            Query query = session.createQuery(glCodePresent);
            query.setInteger("countryCode", countryCode.intValue());
            query.setInteger("regionCode", regionCode.intValue());
            query.setInteger("locationCode", locationCode.intValue());
            query.setInteger("ccCode", ccCode.intValue());
            query.setInteger("tolAccCode", tolAccCode.intValue());
            query.setLong(com.Constant.CONST_GLCODE, glCode.intValue());
            List glAccMaster = (List) query.list();
            if (glAccMaster != null && glAccMaster.size() > 0) {
                isGlCodePresent = true;
            }
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_IS_CODE_PRESENT, "Method Exite_2");
        return isGlCodePresent;
    }

    //	/**
    //	 * param
    //	 * return int
    //	 */
    //	public int getRoleCount()
    //	{
    //		logger.debug(AMEConstants.CTX_ROLE_COUNT, com.Constant.CONST_METHOD_ENTERED);
    //		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
    //		Session session = getSession();
    //		String finalQuery = "select count(*) from user_role_privs";
    //		logger.debug(AMEConstants.CTX_ROLE_COUNT, com.Constant.CONST_QUERY_END + finalQuery);
    //		Integer roleCount =
    // (Integer)session.createQuery(finalQuery).uniqueResult();
    //		logger.debug(AMEConstants.CTX_ROLE_COUNT, com.Constant.CONST_METHOD_EXITED);
    //		return roleCount.intValue();
    //	}

    //	/**
    //	 * param roleNAme
    //	 * param costCode
    //	 * param totAccCode
    //	 * param glCode
    //	 * param amount
    //	 * return boolean
    //	 */
    //	public boolean getRoleName(String roleName,Integer costCode,BigDecimal
    // totAccCode,Long glcode,BigDecimal amount)
    //	{
    //		logger.debug(AMEConstants.CTX_ROLE_NAME, com.Constant.CONST_METHOD_ENTERED);
    //		boolean isRoleNameExist = false;
    //		Long amt = new Long(amount.toString());
    //		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
    //		Session session = getSession();
    //		String getRoleName =
    // queryProperties.getProperty(AMEConstants.GET_ROLE_NAME);
    //		logger.debug(AMEConstants.CTX_ROLE_NAME, com.Constant.CONST_QUERY_END + getRoleName);
    //		List roleCount = session.createQuery(getRoleName).list();
    //		if(roleCount!=null)
    //		{
    //			isRoleNameExist = true;
    //		}
    //		logger.debug(AMEConstants.CTX_ROLE_NAME, com.Constant.CONST_METHOD_EXITED);
    //		return isRoleNameExist;
    //	}
    //	
    //	public Integer getBrokerCode(String userid,String password)
    //	{
    //		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
    //		Session session = getSession();
    //		return new Integer(0);
    //	}
    /**
     * param policyQuo return boolean
     */
    public PolicyQuo saveQuotationDetails(PolicyQuo quote) throws DataAccessException {
        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Entere_6");
        Session session = null;

        session = getSession();

        try {
        	session.flush();//ADM 16.06.2011 Release 4.2 Renewal Process Automation
            this.hibernateTemplate.saveOrUpdate(quote);
            session.flush();//ADM 16.06.2011 Release 4.2 Renewal Process Automation
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Exite_3");
        return quote;
    }

    /**
     * param policyQuo return boolean
     */
    public PolicyQuo saveEditQuotationDetails(PolicyQuo quote) throws DataAccessException {
        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Entere_7");

        try {
            this.hibernateTemplate.merge(quote);

        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Exite_4");
        return quote;
    }

    /**
     * param policyQuo return boolean
     */
    public PolicyQuo saveQuotationDetailsForCopyQuote(PolicyQuo quote) throws DataAccessException {
        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Entere_8");

        try {
            this.hibernateTemplate.merge(quote);

        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Exite_5");
        return quote;
    }
    
    public BigDecimal getNextSequenceNumber(String seq) throws DataAccessException {
        Session session = null;
        BigDecimal nextSequenceNumber = null;

        session = getSession();

        try {
            Query query = session.createSQLQuery(SEQ_PART1 + seq + SEQ_PART2);
            nextSequenceNumber = (BigDecimal) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        return nextSequenceNumber;
    }
    
    //ADM 01.10.2010  Release 4.0 - Oman MultiBranch Transactions within Single DB Schema
    
    public BigDecimal getNextSequenceNumber(Integer CountryCode, Integer regionCode,Integer branchCode,Integer classCode ,String transactionType, Integer policyTypeCode) throws DataAccessException {
        BigDecimal nextSequenceNumber = null;
        
        
        
        CallableStatement proc = null;
		Session session = null;
		String str = null;
		List list = null;
		Object selObj[] = null;
		Map sequencedetails = new HashMap();
		 
		try {
			session = getSession();
			 logger.debug(CTX_NXT_SEQ, "Procedure to be started after open connection_1");
			 logger.debug(CTX_NXT_SEQ, "CountryCode ::" + CountryCode);
			 logger.debug(CTX_NXT_SEQ, "regionCOde ::" + regionCode);
			 logger.debug(CTX_NXT_SEQ, "branchCode ::" + branchCode);
			 logger.debug(CTX_NXT_SEQ, "classCode ::" + classCode);
			 logger.debug(CTX_NXT_SEQ, "transactionType ::" + transactionType);
			 logger.debug(CTX_NXT_SEQ, "policyTypeCode ::" + policyTypeCode);
			
			
			proc = session.connection().prepareCall("{ call Pro_Get_Next_Seq(?,?,?,?,?,?,?,?) }");			
		    proc.setLong(1,CountryCode.longValue());
		    proc.setLong(2,regionCode.longValue());
		    proc.setLong(3,branchCode.longValue());
		    proc.setLong(4,classCode.longValue());
		    proc.setString(5,transactionType);
		    proc.setLong(6,policyTypeCode.longValue());
		    /*proc.setLong(7,new Integer(1).longValue());
		    proc.setString(8,"abc");
		    proc.execute();*/
		    proc.registerOutParameter(7, Types.INTEGER);
			proc.registerOutParameter(8, Types.VARCHAR);
			proc.executeQuery();
			logger.debug(CTX_NXT_SEQ, "Procedure finishe_1");
		    logger.debug(CTX_NXT_SEQ, "Procedure finishe_2");
		    
		   /* str = new String("SELECT SEQ_NEXT_VAL FROM t_trn_sequence WHERE seq_country=CountryCode AND seq_region =regionCode and seq_branch =branchCode and seq_cl_code =classCode and seq_pol_type =policyTypeCode and seq_trn_type  =transactionType");
			str = str.replaceAll("CountryCode",""+CountryCode);
			str = str.replaceAll("regionCode",""+regionCode);
			str = str.replaceAll("branchCode",""+branchCode);
			str = str.replaceAll("classCode",""+classCode);
			str = str.replaceAll("transactionType","'"+transactionType+"'");
			str = str.replaceAll("policyTypeCode",""+policyTypeCode);
			Query query = session.createSQLQuery(str);
			query = session.createSQLQuery(str); 
			nextSequenceNumber = (BigDecimal)query.uniqueResult();*/
			if(proc.getBigDecimal(7)!=null)
			{
			sequencedetails.put(com.Constant.CONST_NEXTCOUNT,proc.getBigDecimal(7));
			}
			if(proc.getBigDecimal(8)!=null)
			{
			sequencedetails.put("errormsg",(proc.getString(8)));
			}
	
		if(sequencedetails!=null){
         System.out.println(sequencedetails.size());
         nextSequenceNumber= (BigDecimal)sequencedetails.get(com.Constant.CONST_NEXTCOUNT);
        }
    
		} catch (SQLException sqlException) {
			logger.error(CTX_NXT_SEQ, sqlException.getMessage());
			throw new DataAccessException(sqlException);			
		}finally{
			//Sonax fix for db close on 20-9-2017
			if(proc!=null){
			 try
		      {
		         proc.close();
		      }
		      catch (SQLException e) {
				    logger.debug( "An error occurred while calling the procedure.",e );

		      }
			}
		}
        
        
//        if (sequenceName != null) {
//            Session session = null;
//            try {
//                session = this.hibernateTemplate.getSessionFactory().getCurrentSession();
//                Query query = session.createSQLQuery(SEQ_PART1 + sequenceName + SEQ_PART2);
//                logger.debug(CTX_NXT_SEQ, "Sequence query ::" + query.toString());
//                nextSequenceNumber = (BigDecimal) query.uniqueResult();
//                logger.debug(CTX_NXT_SEQ, "Sequence number ::" + nextSequenceNumber);
//            } catch (HibernateException hibernateException) {
//
//                throw new DataAccessException(hibernateException);
//
//            } finally {
//
//                closeSession(session);
//
//            }
//        }
        return nextSequenceNumber;
    }
    
    

    /**
     * This method will generate the new Quotation no for the Motor class.
     * 
     * @return BigDecimal
     * @throws <code>DataAccessException</code>
     */
    public BigDecimal getNextMotorQuoteNumber() throws DataAccessException {
        return getNextSequenceNumber(SEQ_MOTOR_QUO_NO);
    }

    /**
     * This method will generate the new Quotation Id.
     * 
     * @return BigDecimal
     * @throws <code>DataAccessException</code>
     */
    public BigDecimal getNextQuoteId() throws DataAccessException {
        return getNextSequenceNumber(SEQ_QUO_ID);
    }

    public BigDecimal getNextSequenceNumberForID(String seq) throws DataAccessException {
        Session session = null;
        BigDecimal nextSequenceNumber = null;

        try {
            session = getSession();
            Query query = session.createSQLQuery(SEQ_PART1 + seq + SEQ_PART2);
            nextSequenceNumber = (BigDecimal) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        return nextSequenceNumber;
    }
    
    // Release 4.0 changes to get policy No
 public BigDecimal getNextSequenceNumberForID(Integer CountryCode,
			Integer regionCode, Integer branchCode, Integer classCode,
			String transactionType, Integer policyTypeCode)
			throws DataAccessException {
		BigDecimal nextSequenceNumber = null;

		CallableStatement proc = null;
		Session session = null;
		String str = null;
		List list = null;
		Object selObj[] = null;
		Map sequencedetails = new HashMap();
		try {
			session = getSession();
			logger.debug(GET_NEW_POLICY_NO,
					"Procedure to be started after open connection_2");
			logger.debug(GET_NEW_POLICY_NO, "CountryCode ::" + CountryCode);
			logger.debug(GET_NEW_POLICY_NO, "regionCOde ::" + regionCode);
			logger.debug(GET_NEW_POLICY_NO, "branchCode ::" + branchCode);
			logger.debug(GET_NEW_POLICY_NO, "classCode ::" + classCode);
			logger.debug(GET_NEW_POLICY_NO, "transactionType ::" + transactionType);
			logger.debug(GET_NEW_POLICY_NO, "policyTypeCode ::" + policyTypeCode);

			proc = session.connection().prepareCall(
					"{ call Pro_Get_Next_Seq(?,?,?,?,?,?,?,?) }");
			proc.setLong(1, CountryCode.longValue());
			proc.setLong(2, regionCode.longValue());
			proc.setLong(3, branchCode.longValue());
			proc.setLong(4, classCode.longValue());
			proc.setString(5, transactionType);
			proc.setLong(6, policyTypeCode.longValue());
			/*proc.setLong(7, new Integer(1).longValue());
			proc.setString(8, "abc");*/
			/*proc.execute();
			logger.debug(GET_NEW_POLICY_NO, "Procedure finishe_3");*/
			proc.registerOutParameter(7, Types.INTEGER);
			proc.registerOutParameter(8, Types.VARCHAR);
			proc.executeQuery();
			logger.debug(CTX_NXT_SEQ, "Procedure finishe_4");
			if(proc.getBigDecimal(7)!=null)
			{
			sequencedetails.put(com.Constant.CONST_NEXTCOUNT,proc.getBigDecimal(7));
			}
			if(proc.getBigDecimal(8)!=null)
			{
			sequencedetails.put("errormsg",(proc.getString(8)));
			}
	
		if(sequencedetails!=null){
         System.out.println(sequencedetails.size());
         nextSequenceNumber= (BigDecimal)sequencedetails.get(com.Constant.CONST_NEXTCOUNT);
        }

			/*str = new String(
					"SELECT SEQ_NEXT_VAL FROM t_trn_sequence WHERE seq_country=CountryCode AND seq_region =regionCode and seq_branch =branchCode and seq_cl_code =classCode and seq_pol_type =policyTypeCode and seq_trn_type  =transactionType");
			str = str.replaceAll("CountryCode", "" + CountryCode);
			str = str.replaceAll("regionCode", "" + regionCode);
			str = str.replaceAll("branchCode", "" + branchCode);
			str = str.replaceAll("classCode", "" + classCode);
			str = str
					.replaceAll("transactionType", "'" + transactionType + "'");
			str = str.replaceAll("policyTypeCode", "" + policyTypeCode);
			Query query = session.createSQLQuery(str);
			query = session.createSQLQuery(str);
			nextSequenceNumber = (BigDecimal) query.uniqueResult();*/

		} catch (SQLException sqlException) {
			logger.error(GET_NEW_POLICY_NO, sqlException.getMessage());
			throw new DataAccessException(sqlException);
		}finally{
			//Sonax fix for db close on 20-9-2017
			if(proc!=null){
			 try
		      {
		         proc.close();
		      }
		      catch (SQLException e) {
			     logger.debug( "An error occurred while calling the procedure.",e );
		      }
			}
			closeSession( session );
		}

		return nextSequenceNumber;
	}
 // Release 4.0 changes to get policy No
    public PolicyQuo getCion() throws DataAccessException {
        CoinsuranceQuo[] coinArray = null;
        //List coinList = new ArrayList();
        ArrayList coinList = null;

        Session session = null;
        PolicyQuo policyQuo = null;
        try {
            session = null;

            session = getSession();
            Query query = session.createQuery("FROM CoinsuranceQuo where rownum < 5");

            logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "TEST Query ::" + query.getQueryString());
            coinList = (ArrayList) query.list();
            logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "TEST colorList ::" + coinList);

            //					if (coinList != null) {
            //						logger.debug(AMEConstants.CTX_GET_GL_ACCOUNT_INFO, "TEST Number
            // of
            // records found ::"
            //								+ coinList.size());
            //						coinArray = new CoinsuranceQuo[coinList.size()];
            //						coinList.toArray(coinArray);
            //					}

            policyQuo = new PolicyQuo();

            //coinsurancesCollectionDTO.setCoinsurance(coinArray);
            policyQuo.setCoinsurances(coinList);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        return policyQuo;
    }

    /**
     * Method to get PolicyQuo for the PolicyQuo's composite primary Keys given.
     * 
     * @param PolicyQuo
     * @return PolicyQuo
     * @throws <code>DataAccessException</code>
     */
    public PolicyQuo getPolicyQuoByPK(PolicyQuo policyQuo) throws DataAccessException {
        PolicyQuo returnPolicyQuo = null;
        Query query = null;
        Session session = null;

        logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Method Entere_9");
        List resultList = null;

        if (policyQuo != null && policyQuo.getComp_id() != null) {

            try {

                session = getSession();

                query = session.createQuery(QRY_POLICY_QUO_FOR_POLICY);
                query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
                query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());

                logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Query to get Policy_1" + query.getQueryString());

                resultList = query.list();
                if ( !Utils.isEmpty(resultList) && resultList.size() > 0)
                    returnPolicyQuo = (PolicyQuo) resultList.get(0);

                if ( !Utils.isEmpty(returnPolicyQuo)) {
                    logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Query to get Policy_2" + query.getQueryString());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerSource :_1" + returnPolicyQuo.getCustomerSource());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerCategory :_1" + returnPolicyQuo.getCustomerCategory());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerType :_1" + returnPolicyQuo.getCustomerType());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "PolicyType :_1" + returnPolicyQuo.getPolicyType());
                    // TODO: TO BE REVISITED
                    returnPolicyQuo.setCoinsurances(null);
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "Coinsurances ::" + returnPolicyQuo.getCoinsurances());
                    logger.debug("returnPolicyQuo.getPolicyTexts() ", returnPolicyQuo.getPolicyTexts());
                    logger.debug("returnPolicyQuo.getAgent() ", returnPolicyQuo.getAgent());
                    logger.debug("returnPolicyQuo.getCashCustomerQuo() ", returnPolicyQuo.getCashCustomerQuo());
                    logger.debug("returnPolicyQuo.getCompany() ", returnPolicyQuo.getCompany());
                    logger.debug("returnPolicyQuo.getTarCode() ", returnPolicyQuo.getTarCode());
                    logger.debug("returnPolicyQuo.getOtherInsurerCode() ", returnPolicyQuo.getOtherInsurerCode());
                    logger.debug("returnPolicyQuo.getCustomerType() ", returnPolicyQuo.getCustomerType());
                    logger.debug("returnPolicyQuo.getCurrency() ", returnPolicyQuo.getCurrency());
                    logger.debug("returnPolicyQuo.getReasonCode() ", returnPolicyQuo.getReasonCode());
                    logger.debug("returnPolicyQuo.getCompany() ", returnPolicyQuo.getCompany());
                    logger.debug("returnPolicyQuo.getDeclarationType() ", returnPolicyQuo.getDeclarationType());
                    logger.debug("returnPolicyQuo.getAgent() ", returnPolicyQuo.getAgent());
                    logger.debug("returnPolicyQuo.getPolicyComments() ", returnPolicyQuo.getPolicyComments());
                    logger.debug("returnPolicyQuo.getNonStdTexts() ", returnPolicyQuo.getNonStdTexts());
                    logger.debug("returnPolicyQuo.getPolicyImages() ", returnPolicyQuo.getPolicyImages());
                    logger.debug("returnPolicyQuo.getPolicyType() ", returnPolicyQuo.getPolicyType());
                    logger.debug("returnPolicyQuo.getDistributionChannel() ", returnPolicyQuo.getDistributionChannel());
                    logger.debug("returnPolicyQuo.getCustomerId() ", returnPolicyQuo.getCustomerId());
                    returnPolicyQuo.setMotorPersonalDetailsForAcc(null);
                    returnPolicyQuo.setMotorPersonalEffectDetailsForAcc(null);
                    returnPolicyQuo.setEndorsementDetails(null);
                    returnPolicyQuo.setVehicles(null);
                    returnPolicyQuo.setDrivers(null);
                    returnPolicyQuo.setPremiums(null);
                    returnPolicyQuo.setConditions(null);
                    returnPolicyQuo.setWarranties(null);
                    returnPolicyQuo.setExclusions(null);
                }
            } catch (HibernateException hibernateException) {
                logger.error(CONTEXT_GET_POLICY_FOR_POLICY_QUO, hibernateException);
                throw new DataAccessException(hibernateException);
            } finally {
                closeSession(session);
            }
        }
        logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Method Exited_1");

        return returnPolicyQuo;
    }

    /**
     * Method returns outstanding Premium for the renewal Quotation..
     * 
     * @param PolicyQuo
     * @return BigDecimal
     * @throws <code>DataAccessException</code>
     */
    public BigDecimal getOutstandingPremiumForPolicyId(PolicyQuo policyQuo) throws DataAccessException {
        BigDecimal outstandingPremium = null;
        Query hibernateQuery = null;
        String baseQuery = null;
        Session session = null;
        List resultList = null;
        logger.debug(CONTEXT_GET_OUTSTANDING_PREMIUM_POLICY_QUO, "Method Entere_10");

        if (policyQuo == null || policyQuo.getComp_id() == null || policyQuo.getComp_id().getPolicyId() == null) {
            logger.error(CONTEXT_GET_OUTSTANDING_PREMIUM_POLICY_QUO, "policyQuo or policyQuo.getComp_id() or "
                    + "policyQuo.getComp_id().getPolicyId() is/are null");
            throw new DataAccessException("Mandatory parameters are null");
        }

        try {
            session = getSession();

            baseQuery = getQuery(QRY_OUTSTANDING_PREMIUM_FOR_POLICY);
            baseQuery = baseQuery.replaceFirst(POLICYID, String.valueOf(policyQuo.getComp_id().getPolicyId()));

            hibernateQuery = session.createSQLQuery(baseQuery).addScalar(OUTSTANDING_PREMIUM, Hibernate.BIG_DECIMAL);

            logger.debug(CONTEXT_GET_OUTSTANDING_PREMIUM_POLICY_QUO, "Query to get Outstanding Policy "
                    + hibernateQuery.getQueryString());
            resultList = hibernateQuery.list();
            if (resultList != null && resultList.size() > 0)
                outstandingPremium = (BigDecimal) resultList.get(0);
        } catch (HibernateException hibernateException) {
            logger.error(CONTEXT_GET_OUTSTANDING_PREMIUM_POLICY_QUO, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(CONTEXT_GET_OUTSTANDING_PREMIUM_POLICY_QUO, "Method Exited_2");
        return outstandingPremium;
    }

    /**
     * Method returns outstanding Premium for the renewal Quotation..
     * 
     * @param PolicyQuo
     * @return PolicyQuo
     * @throws <code>DataAccessException</code>
     */

    public Boolean updateQuotationDetails(PolicyQuo quote) throws DataAccessException {
        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Entere_11");

        //  Create the SessionFactory, Session
        Session sess=getSession();
        Boolean isUpdated=Boolean.FALSE;
        try {
            /** updating quote */
        	
            Long endtId=quote.getComp_id().getEndtId();
            if(quote.getComp_id().getEndtId()==null){
            	endtId=Long.valueOf(0);
            }
            String updateStatus="update PolicyQuo obj set obj.status.code="+Integer.valueOf(1)+" where obj.comp_id.policyId="+quote.getComp_id().getPolicyId()+"and obj.comp_id.endtId="+endtId;
            Query query=sess.createQuery(updateStatus);
            int status=query.executeUpdate();
            if(status==1)
            {
            	isUpdated=Boolean.TRUE;
            }
            logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS,"status Updated:"+status);
            //this.hibernateTemplate.saveOrUpdate(quote);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            // closeSession(session);
        	 logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Exite_6");
        }

        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Exite_7");
        return isUpdated;
    }



    /***************************************************************************
     * This method returns list of customer status return List
     *  
     */
    public List getCustomerStatus() throws DataAccessException {
        List status = null;
        Session session = null;

        try {
            session = getSession();
            Query query = session.createQuery("select status.code from Status status");
            status = (ArrayList) query.list();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        return status;
    }

    /**
     * Method returns PolicyQuo with premium details like premium, government
     * tax and policy Fees being populated.
     * 
     * @param policyQuo
     * @return PolicyQuo with premium details populated.
     * @throws DataAccessException
     */
    //	ADM 11.08.2010 RELEASE 3.2 Ticket 14673 Claim Check After Renewal
    public Long getClaimDetailsByPk(Long policyId,Long endtId) throws DataAccessException {
    	
       //PolicyQuo returnPolicyQuo = null;
        Query query = null;
        Session session = null;
        Object[] claimArr = null;
        List resultList = null;
        Long oldValue = Long.valueOf(0);
        logger.debug("getClaimDetailsByPk(PolicyQuo policyQuo)", "Method Entere_12");

        try {
            session = getSession();       
            System.out.println("getClaimDetailsByPk::::::getpolicyId::::::"+ policyId);
			System.out.println("getClaimDetailsByPk::::::getEndtId::::::"+endtId);
            query = session.createQuery(getQuery(QRY_QUO_CLAIM_FOR_PK));
            query.setParameter(POLICYID, policyId);
            query.setParameter(ENDORSEMENTID, endtId);

            logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Query to get Policy_3" + query.getQueryString());

            resultList = query.list();
          //  if (resultList != null && resultList.size() > 0)
           // 	claimArr = (Object[]) resultList.get(0);

           // returnPolicyQuo = new PolicyQuo();
            //if (claimArr[0] != null)
            //    returnPolicyQuo.setPolicyClaimCount((Long) claimArr[0]);
            //if (claimArr[1] != null)
            //    returnPolicyQuo.setGovernmentTax((BigDecimal) claimArr[1]);
           // if (claimArr[2] != null)
            //    returnPolicyQuo.setPolicyFees((BigDecimal) claimArr[2]);
            
            
            if(resultList != null && resultList.size() > 0) {
            	 //returnPolicyQuo.setPolicyClaimCount((Long)resultList.get(0));
            	 oldValue = (Long)resultList.get(0);
            	 }
        } catch (HibernateException hibernateException) {
            logger.error(CONTEXT_GET_POLICY_FOR_POLICY_QUO, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Method Exited_3");

        return oldValue;

    }
    /**
     * Method returns PolicyQuo with claim details like claim count being populated.
     * 
     * @param policyQuo
     * @return PolicyQuo with claim details populated.
     * @throws DataAccessException
     */  
    
    
    public PolicyQuo getPermiumDetailsByPk(PolicyQuo policyQuo) throws DataAccessException {
        PolicyQuo returnPolicyQuo = null;
        Query query = null;
        Session session = null;
        Object[] premiumArr = null;
        List resultList = null;
        logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Method Entere_13");

        if (Utils.isEmpty( policyQuo )&& Utils.isEmpty(policyQuo.getComp_id())) {
            logger.error(CONTEXT_GET_POLICY_FOR_POLICY_QUO, " Composite Primary Keys are Null.");
            throw new DataAccessException("Mandatory Composite Primary Keys are Null.");
        }

        try {
            session = getSession();
            query = session.createQuery(getQuery(QRY_QUO_PREMIUM_FOR_PK));
            query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());

            logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Query to get Policy_4" + query.getQueryString());

            resultList = query.list();
            if (resultList != null && resultList.size() > 0)
                premiumArr = (Object[]) resultList.get(0);

            returnPolicyQuo = new PolicyQuo();
            try{
            	if (premiumArr[0] != null)
                    returnPolicyQuo.setPremium((BigDecimal) premiumArr[0]);
            	if (premiumArr[1] != null)
                    returnPolicyQuo.setGovernmentTax((BigDecimal) premiumArr[1]);
                if (premiumArr[2] != null)
                    returnPolicyQuo.setPolicyFees((BigDecimal) premiumArr[2]);
            }catch (NullPointerException e) {
				logger.debug("Null pointer exception ", e);
			}
                        
        } catch (HibernateException hibernateException) {
            logger.error(CONTEXT_GET_POLICY_FOR_POLICY_QUO, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(CONTEXT_GET_POLICY_FOR_POLICY_QUO, "Method Exited_4");

        return returnPolicyQuo;

    }

    /**
     * Method saves the Renewal terms and texts for the QuoteId.
     * 
     * @param policyQuo
     * @throws DataAccessException
     */
    public void saveRenewalTermsForQuote(PolicyQuo policyQuo) throws DataAccessException {
        try {
            logger.debug(CTX_SAVE_RENEWALS, "Method Entere_14");
            this.hibernateTemplate.saveOrUpdate(policyQuo);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
        	logger.debug(CTX_SAVE_RENEWALS, "Method Exite_8");
        }

    }

    /**
     * Method counts the records where RefPolicyId in Quote is equal to
     * PolicyId.
     * 
     * @param policy <code>Policy</code>
     * @return Long
     * @throws DataAccessException
     */
    public Long getCountOfRefPolicyIdInQuote(Policy policy) throws DataAccessException {
        Session session = null;
        Long count = Long.valueOf(0);
        logger.debug(CTX_GET_COUNT, "Initial count is::" + count);
        try {
            logger.debug(CTX_GET_COUNT, "Entered CountOfRefPolicyIdInQuote::" + policy);
            session = getSession();
            Query query = session.createQuery(QRY_GET_COUNT_OF_REF_POL_ID);
            query.setParameter(POLICYID, policy.getComp_id().getPolicyId());
            count = (Long) query.uniqueResult();
            logger.debug(CTX_GET_COUNT, "No of count is::" + count);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return count;

    }

    /**
     * This method will fetch the <code>PolicyQuo</code> for the given quoteId
     * 
     * @param quoteId <code>Long</code>
     * @param endtId <code>Long</code>
     * @return <code>PolicyQuo</code>
     * @throws <code>DataAccessException</code>
     */
    public PolicyQuo fetchQuoteByQuoteId(Long quoteId, Long endtId) throws DataAccessException {
        PolicyQuo policyQuo = null;
        String fullname = null;
        Long maxEndtId = null;
        logger.debug(CTX_FTH_QUOTE_BY_ID, "Entered with :_1" + quoteId);
        if (quoteId != null) {
            Session session = getSession();
				
				logger.debug(CTX_FTH_QUOTE_BY_ID, "Entered with :_2" + quoteId);
			/*For fetching the quote details for edit and view purpose it has to fetch the details from the DB
			 * Anyhow only the latest record is allowed to be edited so fetching the row corresponds to the max endt id
			 * should work fine. 
			 * But for the transaction history records, it has to consider the passed endtId so that we can fetch 
			 * the corresponding version records
			 * Why we can't use same approach for both is due the prob in edit mode. In case of first edit in second page
			 * system won't fetch the latest cashcust & other first page details if we pass the passed endt id* */
				//ADM 24.08.2009:CR04 : Removing the null check as it is fetching the old record 
			if(endtId==null){
				Criteria criteriaMaxEndtId = session.createCriteria(PolicyQuo.class);
				criteriaMaxEndtId.add(Restrictions.eq(com.Constant.CONST_COMP_ID_POLICYID, quoteId));
				criteriaMaxEndtId.setProjection(Projections.max(com.Constant.CONST_COMP_ID_ENDTID));
				maxEndtId = (Long) criteriaMaxEndtId.uniqueResult();
				logger.debug(CTX_FTH_QUOTE_BY_ID, "maxEndtId is ::" + maxEndtId);
				endtId = maxEndtId; 
			}
				
            Criteria criteria = session.createCriteria(PolicyQuo.class);
            criteria.add(Restrictions.eq(com.Constant.CONST_COMP_ID_POLICYID, quoteId));
            
//          CR04:added for fetching the versions for new quote
            if(endtId!=null){
            	criteria.add(Restrictions.eq(com.Constant.CONST_COMP_ID_ENDTID, endtId));
            }
            policyQuo = (PolicyQuo) criteria.uniqueResult();
            logger.debug(CTX_FTH_QUOTE_BY_ID, "Got the quote ::" + policyQuo);
            
//          GET THE DATA FOR LAZY INIT
            if (policyQuo != null) {
                logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerSource :_2" + policyQuo.getCustomerSource());
                logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerCategory :_2" + policyQuo.getCustomerCategory());
                logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerType :_2" + policyQuo.getCustomerType());
                logger.debug(CTX_FTH_QUOTE_BY_ID, "PolicyType :_2" + policyQuo.getPolicyType());
                logger.debug(CTX_FTH_QUOTE_BY_ID, "Agent Fetched ::" + policyQuo.getAgent());
                // TODO: TO BE REVISITED
                policyQuo.setCoinsurances(null);
                logger.debug(CTX_FTH_QUOTE_BY_ID, "Coinsurances ::" + policyQuo.getCoinsurances());
                policyQuo.setNonStdTexts(null);
                policyQuo.setPolicyImages(null);
                policyQuo.setMotorPersonalDetailsForAcc(null);
                policyQuo.setMotorPersonalEffectDetailsForAcc(null);
                policyQuo.setPolicyTexts(null);
                policyQuo.setEndorsementDetails(null);
                policyQuo.setVehicles(null);
                policyQuo.setDrivers(null);
                policyQuo.setPremiums(null);
                policyQuo.setConditions(null);
                policyQuo.setWarranties(null);
                policyQuo.setExclusions(null);
                policyQuo.setOtherInsurerCode(null);
                //policyQuo.setCurrency(null);
                policyQuo.setReasonCode(null);
                policyQuo.setCompany(null);
                policyQuo.setDeclarationType(null);
                policyQuo.setPolicyComments(null);
            }
            closeSession(session);
        }
        logger.debug(CTX_FTH_QUOTE_BY_ID, "Method En_2");
        return policyQuo;
    }

    public PolicyQuo fetchAdditionalInfoForQuote(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(VIEW_QUOTE, "Method Star_4");
        Query query = null;

        Session session = null;
        //ADM 15.08.2009 : CR04 Quote versioning start
        List coinsurenceList = null;
        Criteria coinsuranceCrit;

        session = getSession();

	    /*if (policyQuo.getAgent() != null) {
            Long agentCode = policyQuo.getAgent().getAgentCode();
            Agent agent = (Agent) session.createCriteria(Agent.class).add(Restrictions.like("agentCode", agentCode))
                    .uniqueResult();
            logger.debug(VIEW_QUOTE, "AGENT SAVED ::" + agent);
            policyQuo.setAgent(agent);
            logger.debug(VIEW_QUOTE, "POLICY QUO OBJECT ::" + policyQuo);
            
        }*/
    
        Long policyId = policyQuo.getComp_id().getPolicyId();
        Long endtId = policyQuo.getComp_id().getEndtId();

        logger.debug(FETCH_ADDITIONAL_INFO, "policyId is -->"+policyId);
        logger.debug(FETCH_ADDITIONAL_INFO, "endtId is -->"+endtId);
//        List coinsurenceList = session.createCriteria(CoinsuranceQuo.class).add(
//                Restrictions.eq(com.Constant.CONST_COMP_ID_POLICYID, policyId)).list();
        
        coinsuranceCrit = session.createCriteria(CoinsuranceQuo.class);
        coinsuranceCrit.add(Restrictions.eq(com.Constant.CONST_COMP_ID_POLICYID, policyId));
        coinsuranceCrit.add(Restrictions.eq(com.Constant.CONST_COMP_ID_ENDTID, endtId));       
        coinsurenceList = coinsuranceCrit.list();
        
       if(!Utils.isEmpty(coinsurenceList)){
    	   logger.debug(VIEW_QUOTE, "coinsurenceList size ::" + coinsurenceList.size());  
       }else{
    	   logger.debug(VIEW_QUOTE, "coinsurenceList size is null" );
       }
        	
                       
        if (coinsurenceList != null && coinsurenceList.size() > 0) {
            policyQuo.setCoinsurances(coinsurenceList);
        }

        closeSession(session);

        logger.debug(VIEW_QUOTE, "Method En_3");
        return policyQuo;
    }

    /**
     * @param policy <code>Policy</code>
     * @return <code>Policy[]</code>
     * @throws <code>DataAccessException</code>
     */
    public void updateQuotationStatusByPK(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(CTX_LAPSE_RENEWAL_QUOTATION, "Method Entered _1");
        Query hibernateQuery = null;
        String baseQuery = null;
        Session session = null;
        logger.debug(CTX_LAPSE_RENEWAL_QUOTATION, "Method Entere_15");

        if (policyQuo == null || policyQuo.getComp_id() == null || policyQuo.getComp_id().getPolicyId() == null
                || policyQuo.getStatus() == null) {
            logger.error(CTX_LAPSE_RENEWAL_QUOTATION, "policy or policy.getComp_id() or_1"
                    + "policy.getComp_id().policy() is/are nul_1");
            throw new DataAccessException("Mandatory parameters are null");

        }
        try {
            session = getSession();

            logger.debug(CTX_LAPSE_RENEWAL_QUOTATION, "Before Query _1");
            hibernateQuery = session.createQuery(getQuery(QRY_LAPSE_RENEWAL_QUOTATION));

            logger.debug(CTX_LAPSE_RENEWAL_QUOTATION, "Query :" + baseQuery);
            logger.debug(CTX_LAPSE_RENEWAL_QUOTATION, "After Query :");

            hibernateQuery.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            hibernateQuery.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());
            hibernateQuery.setParameter(POL_DOCUMENT_CODE, policyQuo.getDocument().getCode());
            hibernateQuery.setParameter(POL_STATUS, policyQuo.getStatus().getCode());

            hibernateQuery.executeUpdate();
        } catch (HibernateException hibernateException) {
            logger.error(CTX_LAPSE_RENEWAL_QUOTATION, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(CTX_LAPSE_RENEWAL_QUOTATION, "Method Exited _1");
    }

    /**
     * This method will return depreciation percentage for the risk
     * 
     * @param vehicleAge
     * @param riskCode
     * @param scheme
     * @return
     * @throws DataAccessException
     */

    public Integer getDepreciationPrecent(Integer vehicleAge, Integer riskCode, Integer riskType, Integer schemeCode,
            Integer riskCategory) throws DataAccessException {
        logger.debug(CTX_GET_DEPRECIATION_PERCENT, "Method Entere_16");
        Session session = null;

        Integer depreciationPercent = null;
        try {
            session = getSession();
            Query query = session.createQuery(getQuery(QRY_GET_DEPRE_PERCENT));
            query.setParameter(VEHICLEAGE, vehicleAge);
            query.setParameter(RISKCODE, riskCode);
            query.setParameter(RISKTYPE, riskType);
            query.setParameter(SCHEME_CODE, schemeCode);
            query.setParameter(RISK_CATEGORY, riskCategory);
            logger.debug(CTX_GET_DEPRECIATION_PERCENT, query);
            depreciationPercent = (Integer) query.uniqueResult();
            logger.debug(CTX_GET_DEPRECIATION_PERCENT, depreciationPercent);
        } catch (HibernateException hibernateException) {
            logger.error(CONTEXT_GET_POLICY_FOR_POLICY, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(CTX_GET_DEPRECIATION_PERCENT, "Method Exite_9");
        return depreciationPercent;
    }

    /**
     * The method is used to get the risk category for a vehicle make and model.
     * 
     * @param vehicleQuo <code> VehicleQuo </code>
     * @return vehicleModel <code> VehicleModel </code>
     * @throws DataAccessException
     */
    public VehicleModel getRiskCat(VehicleQuo vehicleQuo) throws DataAccessException {
        Session session = null;
        Criteria criteria = null;
        VehicleModel vehicleModel = null;

        logger.debug(CTX_GET_RISK_CAT, "Method entered");
        try {
            session = getSession();
            criteria = session.createCriteria(VehicleModel.class);
            criteria.add(Restrictions.like("comp_id.code", vehicleQuo.getVehicleModel().getComp_id().getCode())).add(
                    Restrictions.like("comp_id.vehicleMake.code", vehicleQuo.getVehicleModel().getComp_id()
                            .getVehicleMake().getCode()));

            vehicleModel = (VehicleModel) criteria.uniqueResult();
            logger.debug(CTX_GET_RISK_CAT, "Result in getRiskCat : " + vehicleModel);
        } catch (HibernateException hibernateException) {
            logger.error(CTX_GET_RISK_CAT, "Method exite_4");
            throw new DataAccessException(hibernateException);
        }
        return vehicleModel;
    }

    public TTrnReferralStatus getReferral(PolicyQuo policyQuo) throws DataAccessException {
        Session session = null;
        Query referralQuery = null;
        TTrnReferralStatus ttrnReferralStatus = null;
        List referralStatusList;
        try {
            session = getSession();
            Query query = session.createQuery(getQuery(QRY_GET_REFERRAL));
            query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());
            //query.setParameter(REFERRAL_TYPE, policyQuo.getRefType());
            
            referralStatusList=query.list();
            if(referralStatusList!=null&&!referralStatusList.isEmpty()){
                ttrnReferralStatus = (TTrnReferralStatus) referralStatusList.get(0);	
            }
            return ttrnReferralStatus;
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        finally {
            closeSession(session);
        }
    }

    /**
     * This method will delete the existing renewal quote.
     * 
     * @param renewalPolicy <code>RenewalPolicy</code>
     * @return void
     * @throws DataAccessException
     */
    public void deleteRenewalQuote(RenewalPolicy renewalPolicy) throws DataAccessException {
        logger.debug(CTX_DEL_REN_QUOTE, "Entered in delete renewal quote with::" + renewalPolicy.getRefPolicyId());
        Session session = null;
        try {
            session = getSession();
            Query query = session.createQuery(QRY_DEL_REN_QUOTE);
            query.setParameter(POLICYID, renewalPolicy.getRefPolicyId());
            query.executeUpdate();
            logger.debug(CTX_DEL_REN_QUOTE, "Exiting:_1");
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
    }

    /**
     * This method will fetch PolicyType for the given policy.
     * 
     * @param policy <code>PolicyQuo</code>
     * @return <code>PolicyType</code>
     * @throws <code>DataAccessException</code>
     */
    public PolicyType getTypeForQuote(PolicyQuo policy) throws DataAccessException {
       
        // PRFFIXSTRT
        // Policy Type code is fetched with policyquo

        /*logger.debug(CTX_POL_TYP_FOR_POL, com.Constant.CONST_ENTERED_WITH_END + policy);
        PolicyType policyType = null;
        String QRY_POL_TYP_FRM_POL = "SELECT policy.policyType FROM PolicyQuo policy WHERE policy.comp_id.policyId=:policyId AND policy.comp_id.endtId=:endtId";
        Session session = null;
        if (policy != null) {
            session = getSession();
            Query query = session.createQuery(QRY_POL_TYP_FRM_POL);
            query.setParameter(POLICYID, policy.getComp_id().getPolicyId());
            query.setParameter(ENDORSEMENTID, new Long(0));
            policyType = (PolicyType) query.uniqueResult();
        }
        logger.debug(CTX_POL_TYP_FOR_POL, "Returning policyType ::" + policyType);
        return policyType;*/
        
        return policy.getPolicyType();
        // PRFFIXEND
        
    }

    /**
     *  
     */
    public List getPolicyComments(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(CTX_GET_POL_CMT, "Entered with policy::" + policyQuo);
        List policyCommentsList =new ArrayList();
        logger.debug(CTX_GET_POL_CMT, "Initial policy comments list size:::" + policyCommentsList.size());
        List finalPolCommentsList = new ArrayList();
        Session session = null;
        Long policyId = policyQuo.getComp_id().getPolicyId();
        Long endtId = policyQuo.getComp_id().getEndtId();

        logger.debug(CTX_GET_POL_CMT, "PolicyID ::" + policyId);
        logger.debug(CTX_GET_POL_CMT, "ENDT ID ::" + endtId);
        if (policyId != null && endtId != null) {
            try {
                session = getSession();
                Query query = session.createSQLQuery(QRY_POLICY_COMMENT);
                query.setLong(POLICYID, policyId.longValue());
                query.setLong(ENDORSEMENTID, endtId.longValue());
                logger.debug(CTX_GET_POL_CMT, "Policy comments list size:_1" + query);

                policyCommentsList = (ArrayList) query.list();
                if(!Utils.isEmpty(policyCommentsList)){
                	 logger.debug(CTX_GET_POL_CMT, "Policy comments list size:_2" + policyCommentsList.size());
                }else{
                	 logger.debug(CTX_GET_POL_CMT, "Policy comments list size is null" );
                }
                	
               
                logger.debug(CTX_GET_POL_CMT, "Policy comments list size:_3" + query);
                // THIS BLOCK IS REQUIRED AS THE REASON CODE IS NOT SET TO FETCH
                // EAGER AND ITS LAZY
                if (policyCommentsList != null) {
                	/*Added comment for if block to avoid Redundant null check on 9-5-2017 */
                	//if (policyCommentsList != null) {
                        Iterator itr = policyCommentsList.iterator();
                        Object[] row = null;
                        while (itr.hasNext()) {
                        	finalPolCommentsList.add(parsePolicyComments((Object[]) itr.next()));
                      //  }
                   
                	}
                }
                Iterator iterPolComments = finalPolCommentsList.iterator();
                while (iterPolComments.hasNext()) {
                    PolicyComment policyComment = (PolicyComment) iterPolComments.next();
                    logger.debug(CTX_GET_POL_CMT, "PolicyComment ::" + policyComment);
                    logger.debug(CTX_GET_POL_CMT, "Reason code ::" + policyComment.getReasonCode());
                    logger.debug(CTX_GET_POL_CMT, "Policy referredTo ::" + policyComment.getPolicyReferredTo());
                }
           
            } catch (HibernateException e) {
                throw new DataAccessException(e);
            } finally {
                closeSession(session);
            }
        }
        return finalPolCommentsList;
    }
        
    private PolicyComment parsePolicyComments(Object[] row) {
        PolicyComment policyComm = new PolicyComment();
        System.out.println();
        
        //row[0] refers to policy comments
        if(row[0]!=null)
        policyComm.setPocComments(AMEUtil.ObjectToString(row[0]));
        
        //row[1] refers to reason code
        if(row[1]!=null){
        	System.out.println("Reason Code::"+row[1]+(AMEUtil.ObjectToString(row[1])));
        ReasonCode resCode = new ReasonCode();
        resCode.setCode(new Integer(AMEUtil.ObjectToString(row[1])));
        policyComm.setReasonCode(resCode);
        }
        //row[2] refers to policy status
        if(row[2]!=null)
        policyComm.setPocPolicyStatus(new Integer(AMEUtil.ObjectToString(row[2])));
        System.out.println("Reason Code::"+row[1]+(AMEUtil.ObjectToString(row[2])));
        
        //row[3] refers to comments created date
        if(row[3]!=null)
        policyComm.setPocDate((Date) row[3]);
        //row[3] refers to endtNo
        if (row[4] != null ) {
        	System.out.println("Reason Code Eng Desc::"+row[4]);
        	
        	ReasonCode resCode = new ReasonCode();
        	//resCode.setCode(policyComm.getReasonCode());
        	resCode.setEngDesc(AMEUtil.ObjectToString(row[4]));
        	policyComm.setReasonCode(resCode);
        	}
        if (row[5] != null ) {
        	policyComm.setPolicyReferredTo((AMEUtil.ObjectToString(row[5])));
        	System.out.println("set Policy ReferredTo::"+row[5]+(AMEUtil.ObjectToString(row[5])));
        }
        
        return policyComm;
    }


    /**
     * @param policy <code>Policy</code>
     * @return <code>Policy[]</code>
     * @throws <code>DataAccessException</code>
     */
    public Boolean approveAuthorizationRefferal(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(CTX_APPROVE_AUTHORIZATION_REFERRAL, "Method Entered _2");
        Query hibernateQuery = null;
        String baseQuery = null;
        Session session = null;

        Boolean isReferralApproved = Boolean.TRUE;


        //setting userID
        Integer userId = ServiceContext.getUser().getUserId();

        if (policyQuo == null || policyQuo.getComp_id() == null || policyQuo.getComp_id().getPolicyId() == null
                || policyQuo.getStatus() == null) {
            logger.error(CTX_APPROVE_AUTHORIZATION_REFERRAL, "policy or policy.getComp_id() or_2"
                    + "policy.getComp_id().policy() is/are nul_2");
            throw new DataAccessException("Mandatory parameters are null");

        }
        try {
            session = getSession();

            logger.debug(CTX_APPROVE_AUTHORIZATION_REFERRAL, "Before Query _2");
            hibernateQuery = session.createQuery(QRY_APPROVE_QUOTE);

            hibernateQuery.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            hibernateQuery.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());
            hibernateQuery.setParameter(POL_DOCUMENT_CODE, policyQuo.getDocument().getCode());
            hibernateQuery.setInteger(POL_STATUS,policyQuo.getStatus().getCode().intValue());

            hibernateQuery.setParameter(POL_APPROVED_BY, userId);
            hibernateQuery.setParameter(POL_APPROVED_DATE, new Date());
            hibernateQuery.setParameter(POL_MODFIFIED_BY, userId);
            hibernateQuery.setParameter(POL_MODFIFIED_DATE, new Date());

            hibernateQuery.executeUpdate();
        } catch (HibernateException hibernateException) {
            logger.error(CTX_LAPSE_RENEWAL_QUOTATION, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(CTX_APPROVE_AUTHORIZATION_REFERRAL, "Method Exited _2");
        return isReferralApproved;

    }
    //ADM 29.03.2010 Rel 2.5.2 CR#16203 Gross up: To update premium and policy fee in Quote table
    public void updateQuotePremium(PolicyQuo policyQuo) throws DataAccessException {
    	logger.debug("updateQuotePremium():: ", "Method Entere_17");
    	Session session = null;
    	Query query = null;
    	
    	try {
            session = getSession();
            query = session.createQuery(QRY_UPDATE_QUOTE_PREMIUM);
            query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());
            query.setParameter(POL_PREMIUM, policyQuo.getPremium());
            query.setParameter(POL_FEES, policyQuo.getPolicyFees());
            query.executeUpdate();
            logger.debug("updateQuotePremium():: Query :: ", QRY_UPDATE_QUOTE_PREMIUM);
            
    	} catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug("updateQuotePremium():: ", "Method Exite_10");
    }

    public Boolean updatePremiumDetailsForQuote(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(CTX_UPDATE_PREMIUM_DETAILS, "Method Entered _3");
        Query hibernateQuery = null;
        String baseQuery = null;
        Session session = null;

        Boolean isUpdated = null;
        isUpdated=Boolean.FALSE;

        //setting userID
        Integer userId = ServiceContext.getUser().getUserId();

        if (policyQuo == null || policyQuo.getComp_id() == null || policyQuo.getComp_id().getPolicyId() == null) {
            logger.error(CTX_LAPSE_RENEWAL_QUOTATION, "policy or policy.getComp_id() or_3"
                    + "policy.getComp_id().policy() is/are nul_3");
            throw new DataAccessException("Mandatory parameters are null");
        }
        try {
            session = getSession();

            logger.debug(CTX_UPDATE_PREMIUM_DETAILS, "Before Query _3");
            hibernateQuery = session.createQuery(QRY_UPDATE_QUOTE_FEES_TAXES);

            logger.debug(CTX_UPDATE_PREMIUM_DETAILS , "Query :" + QRY_UPDATE_QUOTE_FEES_TAXES);
            logger.debug(CTX_UPDATE_PREMIUM_DETAILS , "After Query :");
            
            hibernateQuery.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            hibernateQuery.setParameter(POL_STATUS, policyQuo.getStatus().getCode());
            hibernateQuery.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());
            hibernateQuery.setParameter(POL_COMMISSION_ID, policyQuo.getCommisionId());
            hibernateQuery.setParameter(POL_PREMIUM, policyQuo.getPremium());
            hibernateQuery.setParameter(POL_GOVT_TAX, policyQuo.getGovernmentTax());
            hibernateQuery.setParameter(POL_FEES, policyQuo.getPolicyFees());
            hibernateQuery.setParameter(POL_MODFIFIED_BY, userId);
            hibernateQuery.setParameter(POL_MODFIFIED_DATE, new Date());

            hibernateQuery.executeUpdate();
            isUpdated = Boolean.TRUE;

        } catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(CTX_UPDATE_PREMIUM_DETAILS, "Method Exited _3");
        return isUpdated;

    }

    private void initializeLazyAttributesToNull(PolicyQuo policyQuo) {
        if (policyQuo != null) {
            logger.debug(CTX_LAZY_ATTRIBUTES, "PolicyType :_3" + policyQuo.getPolicyType());
            // TODO: TO BE REVISITED
            policyQuo.setCustomerSource(null);
            policyQuo.setCustomerCategory(null);
            policyQuo.setCustomerType(null);
            policyQuo.setCoinsurances(null);
            policyQuo.setNonStdTexts(null);
            policyQuo.setPolicyImages(null);
            policyQuo.setMotorPersonalDetailsForAcc(null);
            policyQuo.setMotorPersonalEffectDetailsForAcc(null);
            policyQuo.setPolicyTexts(null);
            policyQuo.setEndorsementDetails(null);
            policyQuo.setVehicles(null);
            policyQuo.setDrivers(null);
            policyQuo.setPremiums(null);
            policyQuo.setConditions(null);
            policyQuo.setWarranties(null);
            policyQuo.setExclusions(null);
            policyQuo.setOtherInsurerCode(null);
            policyQuo.setCurrency(null);
            policyQuo.setReasonCode(null);
            policyQuo.setCompany(null);
            policyQuo.setDeclarationType(null);
            policyQuo.setAgent(null);
            policyQuo.setPolicyComments(null);
        }

    }

    /**
     * This method will get the List of Quotations Search Criteria.
     * 
     * @param TransactionSearchCriteria criteria
     * @return List <code>List</code>
     * @throws <code>DataAccessException</code>
     */
    public List getQuotationsToBePrinted(TransactionSearchCriteria criteria) throws DataAccessException {

        ArrayList quotationList = null;
        Session session = null;
        try {

            logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "Entered with_1" + criteria);
            if(criteria.getTransactionNumber()!=null){
            	  logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "transNumber_1" + criteria.getTransactionNumber());
            }else{
            	  logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "transNumber: is nul_1" );
            }
          
            
            logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "Broker_1" + criteria.getBroker());
            logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "Scheme_1" + criteria.getScheme());
            logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "Days_1" + criteria.getDays());
            logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "classCode_1" + criteria.getTranClass());

            boolean isPolicyIdOnlyCriteria = true;

            /*Null check for sonar violation fix on 9-10-2017 */
            //if (criteria != null) {
               if (!Utils.isEmpty(criteria)) {
                session = getSession();
                StringBuffer queryString = new StringBuffer(128);

                // TODO: TO BE REMOVED
                Long tranNum = criteria.getTransactionNumber();
                criteria.setTransactionNumber((tranNum != null && tranNum.longValue() > 0 ? tranNum : null));
                Integer tranCls = criteria.getTranClass();
                criteria.setTranClass((tranCls != null && tranCls.intValue() > 0 ? tranCls : null));

                // CALCULATE THE DATES FOR THE DAYS RANGE
                if (criteria.getDays() != null && criteria.getDays().intValue() != 0) {
                    Calendar cal = Calendar.getInstance();
                    criteria.setFromDate(cal.getTime());
                    logger.debug("Fromdate:", criteria.getFromDate());
                    cal.add(Calendar.DATE, criteria.getDays().intValue());
                    criteria.setToDate(cal.getTime());
                    logger.debug("Todate:", criteria.getToDate());
                    isPolicyIdOnlyCriteria = false;
                }
                // ADD THE BASE QUERY
                queryString.append(QRY_QUOTATION_SEARCH_BASE);
                Map paramsMap = new HashMap();
                boolean appendWhere = true;
                if (criteria.getTranClass() != null && criteria.getTranClass().intValue() != 0) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = true ? false : false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL + 
                            PARAM_CLASS_CODE);
                    paramsMap.put(PARAM_CLASS_CODE, criteria.getTranClass());
                }
                if (criteria.getTransactionNumber() != null && criteria.getTransactionNumber().longValue() != 0) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_TRAN_NUM + QRY_PRT_EQUL
                            + PARAM_TRAN_NUM);
                    paramsMap.put(PARAM_TRAN_NUM, criteria.getTransactionNumber());

                }

                if (criteria.getFromDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_TO_DATE + QRY_PRT_EQUL_OR_GREATER
                            + PARAM_FROM_DATE);
                    paramsMap.put(PARAM_FROM_DATE, criteria.getFromDate());
                    isPolicyIdOnlyCriteria = false;
                }

                if (criteria.getToDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_TO_DATE + QRY_PRT_EQUL_OR_LESS
                            + PARAM_TO_DATE);
                    paramsMap.put(PARAM_TO_DATE, criteria.getToDate());
                    isPolicyIdOnlyCriteria = false;
                }
                if (criteria.getBroker() != null && criteria.getBroker().intValue() != 0) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL
                            + PARAM_BROKER_CODE);
                    paramsMap.put(PARAM_BROKER_CODE, criteria.getBroker());

                }
                if (criteria.getScheme() != null && criteria.getScheme().longValue() != 0) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL
                            + PARAM_SCHEME_CODE);
                    paramsMap.put(PARAM_SCHEME_CODE, criteria.getScheme());

                }

                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getSourceOfBusiness())) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DOCUMENT + QRY_PRT_DOT
                            + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_SIX);
                    paramsMap.put(PARAM_NUM_SIX, Integer.valueOf(6));

                }

                // CREATE THE QUERY
                Query query = session.createQuery(queryString.toString());
                logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "Query_1" + query.toString());

                // ADD QUERY PARAMS
                Iterator iterParams = paramsMap.keySet().iterator();
                while (iterParams.hasNext()) {
                    String paramName = iterParams.next().toString();
                    query.setParameter(paramName, paramsMap.get(paramName));
                    logger.debug(CTX_GET_QUOTATIONS_TO_PRINT, "SetParam :_1" + paramName + "/"
                            + paramsMap.get(paramName));
                }
                quotationList = (ArrayList) query.list();
                if (quotationList != null) {
                    Iterator iterQuotations = quotationList.iterator();
                    while (iterQuotations.hasNext()) {
                        initializeLazyAttributesToNull((PolicyQuo) iterQuotations.next());
                    }
                }

            }
        } catch (HibernateException hibernateException) {

            throw new DataAccessException(hibernateException);

        } finally {

            closeSession(session);

        }

        return quotationList;
    }

    /**
     * This method will return a Booleann Value Search Criteria.
     * 
     * @param List selectedQuotations
     * @return Boolean <code>Boolean</code>
     * @throws <code>DataAccessException</code>
     */

    public Boolean saveRenewalQuotationsForReports(List selectedQuotations) throws DataAccessException {

        logger.debug(CTX_SAVE_QUOTATIONS, "Entered with :_3" + selectedQuotations);
        Boolean isSave = Boolean.TRUE;
        String status = "N";
        Session session = getSession();
        BatchPrint batchPrint = null;
        BatchPrintPK batchPrintPK = null;
        Date d = new Date();
        BigDecimal sequenceNum = getNextSequenceNumber(BATCH_SEQUENCE);
        Integer batchId = new Integer(sequenceNum.toString());
        Iterator itr = selectedQuotations.iterator();
        while (itr.hasNext()) {
            PolicyQuo policyQuo = (PolicyQuo) itr.next();
            batchPrint = new BatchPrint();
            batchPrintPK = new BatchPrintPK();
            batchPrintPK.setPrnBatchId(batchId);
            batchPrintPK.setPrnTranId(policyQuo.getComp_id().getPolicyId().longValue());
            batchPrint.setComp_id(batchPrintPK);
            batchPrint.setPrnClassCode(policyQuo.getClassCode());
            batchPrint.setPrnDate(d);
            batchPrint.setPrnEndtId(policyQuo.getComp_id().getEndtId().longValue());
            batchPrint.setPrnLocation(ApplicationContext.getBranchCode() != null ? ApplicationContext.getBranchCode()
                    .toString() : "");
            batchPrint.setPrnSts(status);
            batchPrint.setPrnReqBy(ServiceContext.getUser().getUsername());
            session.saveOrUpdate(batchPrint);
        }

        logger.debug(CTX_SAVE_QUOTATIONS, "Method Exite_11");
        return isSave;
    }

    /**
     * This method will get the Renewal of Quotations Search Criteria.
     * 
     * @param TransactionSearchCriteria criteria
     * @return criteria <code>TransactionSearchCriteria</code>
     * @throws <code>DataAccessException</code>
     */
    public PaginatedResult getRenewalQuotations(TransactionSearchCriteria criteria) throws DataAccessException {

        ArrayList quotationList = null;
        Session session = null;
        PaginatedResult paginatedResult = null;
        paginatedResult = new PaginatedResult();
        List finalQuoteList = new ArrayList();
        try {

            logger.debug(CTX_GET_REN_QUOTES, "Entered with_2" + criteria);
            if(criteria.getTransactionNumber()!=null){
                logger.debug(CTX_GET_REN_QUOTES, "transNumber_2" + criteria.getTransactionNumber());
            }else{
                logger.debug(CTX_GET_REN_QUOTES, "transNumber: is nul_2" );
            }
        
            logger.debug(CTX_GET_REN_QUOTES, "Broker_2" + criteria.getBroker());
            logger.debug(CTX_GET_REN_QUOTES, "Scheme_2" + criteria.getScheme());
            logger.debug(CTX_GET_REN_QUOTES, "Days_2" + criteria.getDays());
            logger.debug(CTX_GET_REN_QUOTES, "classCode_2" + criteria.getTranClass());

            boolean isPolicyIdOnlyCriteria = true;

            /*Null check for sonar violation fix on 9-10-2017 */
         //if (criteria != null) {
            if (!Utils.isEmpty(criteria)) {
                session = getSession();
                StringBuffer queryString = new StringBuffer(128);

                /*
                 * // TODO: TO BE REMOVED Long tranNum =
                 * criteria.getTransactionNumber();
                 * criteria.setTransactionNumber((tranNum != null &&
                 * tranNum.longValue() > 0 ? tranNum : null)); Integer tranCls =
                 * criteria.getTranClass(); criteria.setTranClass((tranCls !=
                 * null && tranCls.intValue() > 0 ? tranCls : null));
                 */

                // ADD THE BASE QUERY
                queryString.append(QRY_QUOTATION_SEARCH_BASE);
                boolean appendWhere = true;
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getInsuredType())) {
                    queryString.append(COMMA + QRY_INSURED);

                }
                if (criteria.getInsuredName() != null) {
                    queryString.append(QRY_CSH_CUSTOMER + QRY_BASIC_CONDITION);
                    appendWhere = false;
                }

                Map paramsMap = new HashMap();

                if (criteria.getTranClass() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = true ? false : false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL 
                            + PARAM_CLASS_CODE);
                    paramsMap.put(PARAM_CLASS_CODE, criteria.getTranClass());
                }
                if (criteria.getTransactionNumber() != null) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_TRAN_NUM + QRY_PRT_EQUL
                            + PARAM_TRAN_NUM);
                    paramsMap.put(PARAM_TRAN_NUM, criteria.getTransactionNumber());

                }

                if (criteria.getFromDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_GREATER
                            + PARAM_FROM_DATE);
                    paramsMap.put(PARAM_FROM_DATE, criteria.getFromDate());

                }

                if (criteria.getToDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_LESS
                            + PARAM_TO_DATE);
                    paramsMap.put(PARAM_TO_DATE, criteria.getToDate());

                }
                if (criteria.getBroker() != null && criteria.getBroker().intValue() != 0) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL
                            + PARAM_BROKER_CODE);
                    paramsMap.put(PARAM_BROKER_CODE, criteria.getBroker());

                }
                if (criteria.getScheme() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL
                            + PARAM_SCHEME_CODE);
                    paramsMap.put(PARAM_SCHEME_CODE, Integer.valueOf(criteria.getScheme().intValue()));

                }

                if (criteria.getInsuredName() != null) {
                	queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    String PARAM = PERCENTAGE + (criteria.getInsuredName()).toUpperCase() + PERCENTAGE;
                    queryString.append(OPEN_BRACKET + TO_UPPER_CASE +QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME1+PIPE+QRY_SUBSTRING+OPEN_BRACKET+SPACE+COMMA+QRY_ONE+COMMA+QRY_LENGTH+OPEN_BRACKET+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME2+CLOSE_BRACKET+CLOSE_BRACKET+PIPE+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME2+PIPE+SPACE+PIPE+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME3 +PIPE+SPACE+PIPE+ QRY_CASH_CUSTOMER_OBJ
                            + QRY_PRT_DOT + PARAM_NAME4 +PIPE+SPACE+PIPE+ QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT
                            + PARAM_NAME5 + CLOSE_BRACKET+ LIKE_OPERATOR +TO_UPPER_CASE + SINGLE_QUOTE + PARAM + SINGLE_QUOTE
                            + CLOSE_BRACKET+CLOSE_BRACKET

                    );

                }
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getSourceOfBusiness())) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString
                            .append(OPEN_BRACKET + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL
                                    + QRY_PRT_DOT + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_EIGHT + QRY_PRT_OR
                                    + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL + QRY_PRT_DOT
                                    + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_NINE + QRY_PRT_OR
                                    + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL + QRY_PRT_DOT
                                    + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_TEN + CLOSE_BRACKET);
                    paramsMap.put(PARAM_NUM_EIGHT, Integer.valueOf(8));
                    paramsMap.put(PARAM_NUM_NINE, Integer.valueOf(9));
                    paramsMap.put(PARAM_NUM_TEN, Integer.valueOf(10));

                }
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getInsuredType())) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_INS_CODE + EQUAL_TO
                            + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_INS_CODE + QRY_PRT_AND + QRY_INSURED_OBJ
                            + QRY_PRT_DOT + PARAM_INS_CUSTOMER_TYPE + EQUAL_TO + PARAM_TWO);

                }
                //ADM 07.08.2009 CR04 QUOTE VERSIONING - REPORT CHANGES
                queryString.append(QRY_LATEST_QUOTE_BY_ENDID + QRY_PRT_AND + OPEN_BRACKET + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_STATUS
                        + QRY_PRT_DOT + PARAM_CODE + EQUAL_TO + PARAM_NUM_ONE + QRY_PRT_OR + QRY_QUOTATION_SEARCH_OBJ
                        + QRY_PRT_DOT + PARAM_STATUS + QRY_PRT_DOT + PARAM_CODE + EQUAL_TO + PARAM_NINTY_NINE
                        + CLOSE_BRACKET);
                queryString.append(QRY_PRT_AND + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DOCUMENT + QRY_PRT_DOT
                        + PARAM_CODE + EQUAL_TO + PARAM_NUM_SIX);
                // CREATE THE QUERY
                Query query = session.createQuery(queryString.toString());
                logger.debug(CTX_GET_REN_QUOTES, "Query_2" + query.toString());

                // ADD QUERY PARAMS
                Iterator iterParams = paramsMap.keySet().iterator();
                while (iterParams.hasNext()) {
                    String paramName = iterParams.next().toString();
                    query.setParameter(paramName, paramsMap.get(paramName));
                    logger.debug(CTX_GET_REN_QUOTES, "SetParam :_2" + paramName + "/" + paramsMap.get(paramName));
                }
                quotationList = (ArrayList) query.list();
                if(!Utils.isEmpty(quotationList)){
                	 logger.debug(CTX_GET_REN_QUOTES,"No of records :_1" +quotationList.size());
                }else{
                	 logger.debug(CTX_GET_REN_QUOTES,"No of records :: is null" );
                }
               
                if(quotationList != null){
                    Iterator iter = quotationList.iterator();
                    Object[] row = null;
                    while(iter.hasNext()){
                        finalQuoteList.add(parseRenewalQuotes((Object[]) iter.next()));
                    }
                    
                }
                List tempList = new ArrayList();
                List finalList = new ArrayList();
                int size;
                if (criteria.getNumberOfRecords().intValue() == 0) {
                    tempList = finalQuoteList;
                    size = finalQuoteList.size();
                    getPaginatedResult(query, criteria.getCurrentPage(), size, paginatedResult);
                    if (size > paginatedResult.getRecordsPerPage().intValue()) {
                        //if the size of the list is greater than the no. of
                        // records per page
                        //finalCustomerList will contain the no.of records to
                        // be displayed
                        //in the selected page
                        int firstPageRecords = (paginatedResult.getRecordsPerPage().intValue() * paginatedResult
                                .getCurrentPage().intValue())
                                - paginatedResult.getRecordsPerPage().intValue();
                        for (int i = firstPageRecords; i < firstPageRecords
                                + paginatedResult.getRecordsPerPage().intValue(); i++) {
                            Object object = finalQuoteList.get(i);
                            finalList.add(object);
                        }
                    } else {
                        //if the size is less than the total no of records per
                        // page
                        //display the whole list
                        Iterator iterator = finalQuoteList.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            finalList.add(object);
                        }
                    }
                } else {
                    size = criteria.getNumberOfRecords().intValue();
                    getPaginatedResult(query, criteria.getCurrentPage(), size, paginatedResult);
                    finalList = finalQuoteList;
                }
                paginatedResult.setObjectArray(finalList.toArray());

            }
        } catch (HibernateException hibernateException) {

            logger.debug(CTX_GET_REN_QUOTES, "Exception occurre_1");
            DataAccessException exception = new DataAccessException(hibernateException);
            Message message = new Message("234", com.Constant.CONST_OBJECT_NOT_FOUND_EXCEPTION);
            exception.addMessage(message);
            throw exception;
            //throw new DataAccessException(hibernateException);

        } finally {

            closeSession(session);

        }

        return paginatedResult;
    }

    
    
    /**
     * This method will get the Renewal of Quotations Search Criteria.
     * 
     * @param TransactionSearchCriteria criteria
     * @return criteria <code>TransactionSearchCriteria</code>
     * @throws <code>DataAccessException</code>
     */
    public PaginatedResult getRenewalQuotationsRN(TransactionSearchCriteria criteria) throws DataAccessException {

        ArrayList quotationList = null;
        Session session = null;
        PaginatedResult paginatedResult = null;
        paginatedResult = new PaginatedResult();
        List finalQuoteList = new ArrayList();
        try {

            logger.debug(CTX_GET_REN_QUOTES, "Entered with_3" + criteria);
            if(criteria.getTransactionNumber()!=null){
            	 logger.debug(CTX_GET_REN_QUOTES, "transNumber_3" + criteria.getTransactionNumber());
            }else{
            	 logger.debug(CTX_GET_REN_QUOTES, "transNumber: is nul_3" );
            }
           
            logger.debug(CTX_GET_REN_QUOTES, "Broker_3" + criteria.getBroker());
            logger.debug(CTX_GET_REN_QUOTES, "Scheme_3" + criteria.getScheme());
            logger.debug(CTX_GET_REN_QUOTES, "Days_3" + criteria.getDays());
            logger.debug(CTX_GET_REN_QUOTES, "classCode_3" + criteria.getTranClass());
            logger.debug(CTX_GET_REN_QUOTES, "locationCode:" + criteria.getLocationCode());

            boolean isPolicyIdOnlyCriteria = true;

            if (criteria != null) {
                session = getSession();
                StringBuffer queryString = new StringBuffer(128);

                /*
                 * // TODO: TO BE REMOVED Long tranNum =
                 * criteria.getTransactionNumber();
                 * criteria.setTransactionNumber((tranNum != null &&
                 * tranNum.longValue() > 0 ? tranNum : null)); Integer tranCls =
                 * criteria.getTranClass(); criteria.setTranClass((tranCls !=
                 * null && tranCls.intValue() > 0 ? tranCls : null));
                 */

                // ADD THE BASE QUERY
                queryString.append(QRY_QUOTATION_SEARCH_BASE);
                boolean appendWhere = true;
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getInsuredType())) {
                    queryString.append(COMMA + QRY_INSURED);

                }
                if (criteria.getInsuredName() != null) {
                    queryString.append(QRY_CSH_CUSTOMER + QRY_BASIC_CONDITION);
                    appendWhere = false;
                }

                Map paramsMap = new HashMap();

                if (criteria.getTranClass() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = true ? false : false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL 
                            + PARAM_CLASS_CODE);
                    paramsMap.put(PARAM_CLASS_CODE, criteria.getTranClass());
                }
                if (criteria.getTransactionNumber() != null) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_TRAN_NUMBER + QRY_PRT_EQUL
                            + PARAM_TRAN_NUMBER);
                    paramsMap.put(PARAM_TRAN_NUMBER, criteria.getTransactionNumber());

                }

                if (criteria.getFromDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_GREATER
                            + PARAM_FROM_DATE);
                    paramsMap.put(PARAM_FROM_DATE, criteria.getFromDate());

                }

                if (criteria.getToDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_LESS
                            + PARAM_TO_DATE);
                    paramsMap.put(PARAM_TO_DATE, criteria.getToDate());

                }
                // Release 4.0 Oman Changes passing location code in search query
                if (criteria.getLocationCode() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + 
                    		PARAM_LOCATION_CODE_1);
                    paramsMap.put(PARAM_LOCATION_CODE_1, criteria.getLocationCode());
                }
                // Release 4.0 Oman Changes
                
                if (criteria.getBroker() != null && criteria.getBroker().intValue() != 0) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL
                            + PARAM_BROKER_CODE);
                    paramsMap.put(PARAM_BROKER_CODE, criteria.getBroker());

                }
                if (criteria.getScheme() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL
                            + PARAM_SCHEME_CODE);
                    paramsMap.put(PARAM_SCHEME_CODE, Integer.valueOf(criteria.getScheme().intValue()));

                }

                if (criteria.getInsuredName() != null) {
                	queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    String PARAM = PERCENTAGE + (criteria.getInsuredName()).toUpperCase() + PERCENTAGE;
                    queryString.append(OPEN_BRACKET + TO_UPPER_CASE +QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME1+PIPE+QRY_SUBSTRING+OPEN_BRACKET+SPACE+COMMA+QRY_ONE+COMMA+QRY_LENGTH+OPEN_BRACKET+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME2+CLOSE_BRACKET+CLOSE_BRACKET+PIPE+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME2+PIPE+SPACE+PIPE+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME3 +PIPE+SPACE+PIPE+ QRY_CASH_CUSTOMER_OBJ
                            + QRY_PRT_DOT + PARAM_NAME4 +PIPE+SPACE+PIPE+ QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT
                            + PARAM_NAME5 + CLOSE_BRACKET+ LIKE_OPERATOR +TO_UPPER_CASE + SINGLE_QUOTE + PARAM + SINGLE_QUOTE
                            + CLOSE_BRACKET+CLOSE_BRACKET

                    );

                }
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getSourceOfBusiness())) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString
                            .append(OPEN_BRACKET + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL
                                    + QRY_PRT_DOT + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_EIGHT + QRY_PRT_OR
                                    + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL + QRY_PRT_DOT
                                    + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_NINE + QRY_PRT_OR
                                    + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL + QRY_PRT_DOT
                                    + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_TEN + CLOSE_BRACKET);
                    paramsMap.put(PARAM_NUM_EIGHT, Integer.valueOf(8));
                    paramsMap.put(PARAM_NUM_NINE, Integer.valueOf(9));
                    paramsMap.put(PARAM_NUM_TEN, Integer.valueOf(10));

                }
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getInsuredType())) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_INS_CODE + EQUAL_TO
                            + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_INS_CODE + QRY_PRT_AND + QRY_INSURED_OBJ
                            + QRY_PRT_DOT + PARAM_INS_CUSTOMER_TYPE + EQUAL_TO + PARAM_TWO);

                }
                //ADM 07.08.2009 CR04 QUOTE VERSIONING - REPORT CHANGES
                queryString.append(QRY_LATEST_QUOTE_BY_ENDID + QRY_PRT_AND + OPEN_BRACKET + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_STATUS
                        + QRY_PRT_DOT + PARAM_CODE + EQUAL_TO + PARAM_NUM_ONE + QRY_PRT_OR + QRY_QUOTATION_SEARCH_OBJ
                        + QRY_PRT_DOT + PARAM_STATUS + QRY_PRT_DOT + PARAM_CODE + EQUAL_TO + PARAM_NINTY_NINE
                        + CLOSE_BRACKET);
                queryString.append(QRY_PRT_AND + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DOCUMENT + QRY_PRT_DOT
                        + PARAM_CODE + EQUAL_TO + PARAM_NUM_SIX);
                // CREATE THE QUERY
                Query query = session.createQuery(queryString.toString());
                logger.debug(CTX_GET_REN_QUOTES, "Query_3" + query.toString());

                // ADD QUERY PARAMS
                Iterator iterParams = paramsMap.keySet().iterator();
                while (iterParams.hasNext()) {
                    String paramName = iterParams.next().toString();
                    query.setParameter(paramName, paramsMap.get(paramName));
                    logger.debug(CTX_GET_REN_QUOTES, "SetParam :_3" + paramName + "/" + paramsMap.get(paramName));
                }
                quotationList = (ArrayList) query.list();
                if(!Utils.isEmpty(quotationList)){
                	  logger.debug(CTX_GET_REN_QUOTES,"No of records :_2" +quotationList.size());
                }else{
                	  logger.debug(CTX_GET_REN_QUOTES,"No of records :: is null");
                }
              
               
                if(quotationList != null){
                    Iterator iter = quotationList.iterator();
                    Object[] row = null;
                    while(iter.hasNext()){
                        finalQuoteList.add(parseRenewalQuotes((Object[]) iter.next()));
                    }
                    
                }
                List tempList = new ArrayList();
                List finalList = new ArrayList();
                int size;
                if (criteria.getNumberOfRecords().intValue() == 0) {
                    tempList = finalQuoteList;
                    logger.debug(CTX_GET_REN_QUOTES,"No of records :_3" +tempList.size());
                    size = finalQuoteList.size();
                    getPaginatedResult(query, criteria.getCurrentPage(), size, paginatedResult);
                    if (size > paginatedResult.getRecordsPerPage().intValue()) {
                        //if the size of the list is greater than the no. of
                        // records per page
                        //finalCustomerList will contain the no.of records to
                        // be displayed
                        //in the selected page
                        int firstPageRecords = (paginatedResult.getRecordsPerPage().intValue() * paginatedResult
                                .getCurrentPage().intValue())
                                - paginatedResult.getRecordsPerPage().intValue();
                        for (int i = firstPageRecords; i < firstPageRecords
                                + paginatedResult.getRecordsPerPage().intValue(); i++) {
                            Object object = finalQuoteList.get(i);
                            finalList.add(object);
                        }
                    } else {
                        //if the size is less than the total no of records per
                        // page
                        //display the whole list
                        Iterator iterator = finalQuoteList.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            finalList.add(object);
                        }
                    }
                } else {
                    size = criteria.getNumberOfRecords().intValue();
                    getPaginatedResult(query, criteria.getCurrentPage(), size, paginatedResult);
                    finalList = finalQuoteList;
                }
                paginatedResult.setObjectArray(finalList.toArray());

            }
        } catch (HibernateException hibernateException) {

            logger.debug(CTX_GET_REN_QUOTES, "Exception occurre_2");
            DataAccessException exception = new DataAccessException(hibernateException);
            Message message = new Message("234", com.Constant.CONST_OBJECT_NOT_FOUND_EXCEPTION);
            exception.addMessage(message);
            throw exception;
            //throw new DataAccessException(hibernateException);

        } finally {

            closeSession(session);

        }

        return paginatedResult;
    }
    
    private PolicyQuo parseRenewalQuotes(Object[] row) {
        PolicyQuo policyQuo = new PolicyQuo();
        PolicyPKQuo policyPkQuo = new PolicyPKQuo();
        // Added this variable for the ticket#: 8090    
        DistributionChannel distributionChannel = new DistributionChannel();
        //row[0] refers to policy id
        policyPkQuo.setPolicyId(new Long(AMEUtil.ObjectToString(row[0])));
        //row[1] refers to endt id
        policyPkQuo.setEndtId(new Long(AMEUtil.ObjectToString(row[1])));
        policyQuo.setComp_id(policyPkQuo);
        // Added this variable for the ticket#: 8090.
        // row[2] refers to Distribution Channel Code
        distributionChannel.setCode(new Integer(AMEUtil.ObjectToString(row[2])));
        policyQuo.setDistributionChannel(distributionChannel);
        // row[3] refers to endtNo
        if (row[2] != null) {
            policyQuo.setEndtNo(new Long(AMEUtil.ObjectToString(row[3])));
        }        
        // row[4] refers to policyNo
        policyQuo.setQuotationNo(new Long(AMEUtil.ObjectToString(row[4])));
        // row[5] refers to effectiveDate
        policyQuo.setEffectiveDate((Date) (row[5]));
        // row[6] refers to expiryDate
        policyQuo.setExpiryDate((Date) (row[6]));
        // row[7] refers to policyType
        policyQuo.setPolicyTypeCode((Integer)row[7]);
        // row[8] refers to status
        policyQuo.setStatus((Status) (row[8]));
        // row[9] refers to Document
        policyQuo.setDocument((Document) (row[9]));
        // row[10] refers to Document
        if((row[9]) != null)
        policyQuo.setConcPolicyNo(AMEUtil.ObjectToString(row[10]));
        // row[11] refers to Document
        policyQuo.setClassCode((Integer) row[11]);
        return policyQuo;
    }
    
    /**
     * This method will get the List of Quotations Search Criteria.
     * 
     * @param TransactionSearchCriteria criteria
     * @return List <code>List</code>
     * @throws <code>DataAccessException</code>
     */
    public PaginatedResult getQuotationDetailsByStatus(TransactionSearchCriteria criteria) throws DataAccessException {

        ArrayList quotationList = null;
        Session session = null;
        PaginatedResult paginatedResult = null;
        paginatedResult = new PaginatedResult();
        List finalQuoteList = new ArrayList();
        try {

            logger.debug(CTX_GET_REN_QUOTES, "Entered with_4" + criteria);
            if(criteria.getTransactionNumber()!=null){
            	  logger.debug(CTX_GET_REN_QUOTES, "transNumber_4" + criteria.getTransactionNumber());
            }else{
            	  logger.debug(CTX_GET_REN_QUOTES, "transNumber: is nul_4" );
            }
          
            logger.debug(CTX_GET_REN_QUOTES, "Broker_4" + criteria.getBroker());
            logger.debug(CTX_GET_REN_QUOTES, "Scheme_4" + criteria.getScheme());
            logger.debug(CTX_GET_REN_QUOTES, "Days_4" + criteria.getDays());
            logger.debug(CTX_GET_REN_QUOTES, "classCode_4" + criteria.getTranClass());
            logger.debug(CTX_GET_REN_QUOTES, "classCode_5" + criteria.getLocationCode());

            boolean isPolicyIdOnlyCriteria = true;

            if (criteria != null) {
                session = getSession();
                StringBuffer queryString = new StringBuffer(128);

                /*
                 * // TODO: TO BE REMOVED Long tranNum =
                 * criteria.getTransactionNumber();
                 * criteria.setTransactionNumber((tranNum != null &&
                 * tranNum.longValue() > 0 ? tranNum : null)); Integer tranCls =
                 * criteria.getTranClass(); criteria.setTranClass((tranCls !=
                 * null && tranCls.intValue() > 0 ? tranCls : null));
                 */

                // ADD THE BASE QUERY
                queryString.append(QRY_QUOTATION_SEARCH_BASE);
                boolean appendWhere = true;
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getInsuredType())) {
                    queryString.append(COMMA + QRY_INSURED);

                }
                if (criteria.getInsuredName() != null) {
                    queryString.append(QRY_CSH_CUSTOMER + QRY_BASIC_CONDITION);
                    appendWhere = false;
                }

                Map paramsMap = new HashMap();

                if (criteria.getTranClass() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = true ? false : false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL + 
                            PARAM_CLASS_CODE);
                    paramsMap.put(PARAM_CLASS_CODE, criteria.getTranClass());
                }
                if (criteria.getTransactionNumber() != null) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_TRAN_NUM + QRY_PRT_EQUL
                            + PARAM_TRAN_NUM);
                    paramsMap.put(PARAM_TRAN_NUM, criteria.getTransactionNumber());

                }
                // Release 4.0 Oman Changes
                if (criteria.getLocationCode() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + 
                    		PARAM_LOCATION_CODE_1);
                    paramsMap.put(PARAM_LOCATION_CODE_1, criteria.getLocationCode());
                }
                // Release 4.0 Oman Changes
                if (criteria.getFromDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_GREATER
                            + PARAM_FROM_DATE);
                    paramsMap.put(PARAM_FROM_DATE, criteria.getFromDate());

                }

                if (criteria.getToDate() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_LESS
                            + PARAM_TO_DATE);
                    paramsMap.put(PARAM_TO_DATE, criteria.getToDate());

                }
                if (criteria.getBroker() != null && criteria.getBroker().intValue() != 0) {

                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL
                            + PARAM_BROKER_CODE);
                    paramsMap.put(PARAM_BROKER_CODE, criteria.getBroker());

                }
                if (criteria.getScheme() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL
                            + PARAM_SCHEME_CODE);
                    paramsMap.put(PARAM_SCHEME_CODE, Integer.valueOf(criteria.getScheme().intValue()));

                }

                if (criteria.getInsuredName() != null) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    String PARAM = PERCENTAGE + (criteria.getInsuredName()).toUpperCase() + PERCENTAGE;
                    queryString.append(OPEN_BRACKET + TO_UPPER_CASE +QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME1+PIPE+QRY_SUBSTRING+OPEN_BRACKET+SPACE+COMMA+QRY_ONE+COMMA+QRY_LENGTH+OPEN_BRACKET+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME2+CLOSE_BRACKET+CLOSE_BRACKET+PIPE+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME2+PIPE+SPACE+PIPE+QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT + PARAM_NAME3 +PIPE+SPACE+PIPE+ QRY_CASH_CUSTOMER_OBJ
                            + QRY_PRT_DOT + PARAM_NAME4 +PIPE+SPACE+PIPE+ QRY_CASH_CUSTOMER_OBJ + QRY_PRT_DOT
                            + PARAM_NAME5 + CLOSE_BRACKET+ LIKE_OPERATOR +TO_UPPER_CASE + SINGLE_QUOTE + PARAM + SINGLE_QUOTE
                            + CLOSE_BRACKET+CLOSE_BRACKET

                    );
 }
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getSourceOfBusiness())) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString
                            .append(OPEN_BRACKET + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL
                                    + QRY_PRT_DOT + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_EIGHT + QRY_PRT_OR
                                    + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL + QRY_PRT_DOT
                                    + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_NINE + QRY_PRT_OR
                                    + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DIST_CHNL + QRY_PRT_DOT
                                    + PARAM_CODE + QRY_PRT_EQUL + PARAM_NUM_TEN + CLOSE_BRACKET);
                    paramsMap.put(PARAM_NUM_EIGHT, Integer.valueOf(8));
                    paramsMap.put(PARAM_NUM_NINE, Integer.valueOf(9));
                    paramsMap.put(PARAM_NUM_TEN, Integer.valueOf(10));

                }
                if ((PARAM_CONST_TRUE).equalsIgnoreCase(criteria.getInsuredType())) {
                    queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
                    appendWhere = false;
                    queryString.append(QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_INS_CODE + EQUAL_TO
                            + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_INS_CODE + QRY_PRT_AND + QRY_INSURED_OBJ
                            + QRY_PRT_DOT + PARAM_INS_CUSTOMER_TYPE + EQUAL_TO + PARAM_TWO);

                }
                //ADM 07.08.2009 CR04 QUOTE VERSIONING - REPORT CHANGES
                queryString.append(QRY_LATEST_QUOTE_BY_ENDID + QRY_PRT_AND + OPEN_BRACKET + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_STATUS
                        + QRY_PRT_DOT + PARAM_CODE + EQUAL_TO + PARAM_NINTY_EIGHT + CLOSE_BRACKET);
                queryString.append(QRY_PRT_AND + QRY_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DOCUMENT + QRY_PRT_DOT
                        + PARAM_CODE + EQUAL_TO + PARAM_NUM_SIX);
                // CREATE THE QUERY
                Query query = session.createQuery(queryString.toString());
                logger.debug(CTX_GET_REN_QUOTES, "Query_4" + query.toString());

                // ADD QUERY PARAMS
                Iterator iterParams = paramsMap.keySet().iterator();
                while (iterParams.hasNext()) {
                    String paramName = iterParams.next().toString();
                    query.setParameter(paramName, paramsMap.get(paramName));
                    logger.debug(CTX_GET_REN_QUOTES, "SetParam :_4" + paramName + "/" + paramsMap.get(paramName));
                }
                quotationList = (ArrayList) query.list();
                if(quotationList != null){
                    Iterator iter = quotationList.iterator();
                    Object[] row = null;
                    while(iter.hasNext()){
                        finalQuoteList.add(parseRenewalQuotes((Object[]) iter.next()));
                    }
                    
                }
                List tempList = new ArrayList();
                List finalList = new ArrayList();
                int size;
                if (criteria.getNumberOfRecords().intValue() == 0) {
                    tempList = finalQuoteList;
                    size = finalQuoteList.size();
                    getPaginatedResult(query, criteria.getCurrentPage(), size, paginatedResult);
                    if (size > paginatedResult.getRecordsPerPage().intValue()) {
                        //if the size of the list is greater than the no. of
                        // records per page
                        //finalCustomerList will contain the no.of records to
                        // be displayed
                        //in the selected page
                        int firstPageRecords = (paginatedResult.getRecordsPerPage().intValue() * paginatedResult
                                .getCurrentPage().intValue())
                                - paginatedResult.getRecordsPerPage().intValue();
                        for (int i = firstPageRecords; i < firstPageRecords
                                + paginatedResult.getRecordsPerPage().intValue(); i++) {
                            Object object = finalQuoteList.get(i);
                            finalList.add(object);
                        }
                    } else {
                        //if the size is less than the total no of records per
                        // page
                        //display the whole list
                        Iterator iterator = finalQuoteList.iterator();
                        while (iterator.hasNext()) {
                            Object object = (Object) iterator.next();
                            finalList.add(object);
                        }
                    }
                } else {
                    size = criteria.getNumberOfRecords().intValue();
                    getPaginatedResult(query, criteria.getCurrentPage(), size, paginatedResult);
                    finalList = finalQuoteList;
                }
                paginatedResult.setObjectArray(finalList.toArray());

            }
        } catch (HibernateException hibernateException) {

            logger.debug(CTX_GET_REN_QUOTES, "Exception occurre_3");
            DataAccessException exception = new DataAccessException(hibernateException);
            Message message = new Message("234", com.Constant.CONST_OBJECT_NOT_FOUND_EXCEPTION);
            exception.addMessage(message);
            throw exception;
            //throw new DataAccessException(hibernateException);

        } finally {

            closeSession(session);

        }

        return paginatedResult;
    }


    public BigDecimal getNextInsuredID(String seq) throws DataAccessException {
        Session session = null;
        BigDecimal nextSequenceNumber = null;

        try {
            session = getSession();
            Query query = session.createSQLQuery(SEQ_PART1 + seq + SEQ_PART2);
            nextSequenceNumber = (BigDecimal) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        return nextSequenceNumber;
    }

    /**
     * This method will return a Boolean Value Search Criteria.
     * 
     * @param List selectedQuotations
     * @return Boolean <code>Boolean</code>
     * @throws <code>DataAccessException</code>
     */

    public Boolean saveRenewalQuotationsForReports(List selectedQuotations, String reportType)
            throws DataAccessException {

        logger.debug(CTX_SAVE_QUOTATIONS, "Entered with :_4" + selectedQuotations);
        Boolean isSave = Boolean.TRUE;
        Session session = getSession();
        BatchPrint batchPrint = null;
        BatchPrintPK batchPrintPK = null;
        Date d = new Date();
        BigDecimal sequenceNum = getNextSequenceNumber(BATCH_SEQUENCE);
        Integer batchId = new Integer(sequenceNum.toString());
        Iterator itr = selectedQuotations.iterator();
        TranType tranType = new TranType();
        tranType.setTrnType(reportType);
        try {
            while (itr.hasNext()) {
                PolicyQuo policyQuo = (PolicyQuo) itr.next();
                batchPrint = new BatchPrint();
                batchPrintPK = new BatchPrintPK();
                batchPrintPK.setPrnBatchId(batchId);
                batchPrintPK.setPrnTranId(policyQuo.getComp_id().getPolicyId().longValue());
                batchPrint.setComp_id(batchPrintPK);
                batchPrint.setPrnClassCode(policyQuo.getClassCode());
                batchPrint.setPrnDate(d);
                batchPrint.setPrnEndtId(policyQuo.getComp_id().getEndtId().longValue());
                batchPrint.setPrnLocation(ApplicationContext.getBranchCode() != null ? ApplicationContext
                        .getBranchCode().toString() : "");
                batchPrint.setPrnSts(PRINT_STATUS);
                batchPrint.setPrnReqBy(ServiceContext.getUser().getUsername());
                batchPrint.setPrnType(tranType);
                session.saveOrUpdate(batchPrint);

            }

        } catch (HibernateException e) {
            logger.debug(CTX_SAVE_QUOTATIONS, "Exception Occurred:" + e);
            throw new DataAccessException(e);
        }

        finally {
            closeSession(session);
        }
        logger.debug(CTX_SAVE_QUOTATIONS, "Method Exite_12");
        return isSave;
    }

    /**
     * The business method is used to fetch the quote details by QuoteId.
     * 
     * @param quoteNo <code>Long</code>
     * @return policyQuo <code> PolicyQuo </code>
     * @throws DataAccessException
     */
    public PolicyQuo fetchPolicyQuoDetailsByQuoteId(Long quoteNo) throws DataAccessException {
        PolicyQuo policyQuo = null;
        logger.debug(CTX_FETCH_QUOTE_DETAILS, "Entered with :_5" + quoteNo);
        Long maxEndtId = null;
        
        if (quoteNo != null) {
            try {
                Session session = getSession();
                
                //ADM 28.08.2009 : added the max endtId for CR04 to fetch the latest version record
                Criteria criteriaMaxEndtId = session.createCriteria(PolicyQuo.class);
				criteriaMaxEndtId.add(Restrictions.eq(com.Constant.CONST_COMP_ID_POLICYID, quoteNo));//ADM 01.10.2010  Release 4.0 - Oman MultiBranch Transactions within Single DB Schema
				criteriaMaxEndtId.setProjection(Projections.max(com.Constant.CONST_COMP_ID_ENDTID));
				maxEndtId = (Long) criteriaMaxEndtId.uniqueResult();
				logger.debug(CTX_FTH_QUOTE_BY_ID, "maxEndtId is ::" + maxEndtId);
				
                Criteria criteria = session.createCriteria(PolicyQuo.class);
                criteria.add(Restrictions.eq(com.Constant.CONST_COMP_ID_POLICYID, quoteNo));//ADM 01.10.2010  Release 4.0 - Oman MultiBranch Transactions within Single DB Schema
                criteria.add(Restrictions.eq(com.Constant.CONST_COMP_ID_ENDTID, maxEndtId));
                policyQuo = (PolicyQuo) criteria.uniqueResult();
            
                logger.debug(CTX_FTH_QUOTE_BY_ID, "Got the quote ::" + policyQuo);
            } catch (HibernateException hibernateException) {
                logger.error(CTX_FETCH_QUOTE_DETAILS, hibernateException.getMessage());
                throw new DataAccessException(hibernateException);
            }
        }
        logger.debug(CTX_FETCH_QUOTE_DETAILS, "Method exite_5");
        return policyQuo;

    }

    /**
     * The business method is used to get the new validity start date.
     * 
     * @param <code></code>
     * @return date <code>java.util.Date</code>
     */
    public Date getNewValidityStartDate() {
        logger.debug(CTX_GET_NEW_DATE, "for new date");
        return getCurrentTimestamp();

    }

    /**
     * This method fetches the broker commission
     * 
     * @param policyQuo <code>PolicyQuo</code>
     * @return CommisionType
     */
    public CommisionType getCommisionTypes(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(AMEConstants.CTX_GET_COMMISION_TYPES, "Method Star_5");
        List commisionList = null;
        Session session = null;
        CommisionType resultCommision = null;
        Query finalCommisionQuery = null;
        String queryStr;
        try {
            session = getSession();
            queryStr = getQuery(AMEConstants.QRY_COMMISION_TYPE);
            
            finalCommisionQuery = session.createQuery(queryStr);
            finalCommisionQuery.setInteger("clCode",policyQuo.getPolicyType().getComp_id()
                    .getPolicyClass().getCode().intValue());
            finalCommisionQuery.setInteger("ptCode",policyQuo.getPolicyType().getComp_id().getCode().intValue());
            finalCommisionQuery.setInteger("schCode",policyQuo.getCoverNoteHour().intValue());
            finalCommisionQuery.setInteger("docCode",policyQuo.getDocument().getCode().intValue());
            
            List result = finalCommisionQuery.list();
            Iterator commissionItr = result.iterator();

            logger.debug(AMEConstants.CTX_GET_COMMISION_TYPES, "The result has :" + result.size()
                    + " CommisionType objects");
            if (commissionItr.hasNext()) {
                resultCommision = (CommisionType) commissionItr.next();
            }
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(AMEConstants.CTX_GET_COMMISION_TYPES, "The commision type is :" + resultCommision);
        return resultCommision;
    }
    
    /**
     * This method fetches the broker commission
     * 
     * @param policy <code>Policy</code>
     * @return CommisionType
     */
    public CommisionType getCommisionTypes(Policy policy) throws DataAccessException {
        logger.debug(AMEConstants.CTX_GET_COMMISION_TYPES, "Method Star_6");
        List commisionList = null;
        Session session = null;
        CommisionType resultCommision = null;
        Query finalCommisionQuery = null;
        String queryStr;
        try {
            session = getSession();
            queryStr = getQuery(AMEConstants.QRY_COMMISION_TYPE);
            
            finalCommisionQuery = session.createQuery(queryStr);
            finalCommisionQuery.setInteger("clCode",policy.getPolicyType().getComp_id()
                    .getPolicyClass().getCode().intValue());
            finalCommisionQuery.setInteger("ptCode",policy.getPolicyType().getComp_id().getCode().intValue());
            finalCommisionQuery.setInteger("schCode",policy.getCoverNoteHour().intValue());
            finalCommisionQuery.setInteger("docCode",policy.getDocument().getCode().intValue());
            
            List result = finalCommisionQuery.list();
            Iterator commissionItr = result.iterator();

            logger.debug(AMEConstants.CTX_GET_COMMISION_TYPES, "The result has :" + result.size()
                    + " CommisionType objects");
            if (commissionItr.hasNext()) {
                resultCommision = (CommisionType) commissionItr.next();
            }
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(AMEConstants.CTX_GET_COMMISION_TYPES, "The commision type is :" + resultCommision);
        return resultCommision;
    }

    
    public BigDecimal getCommisionPercentage(Long policyId,Long endtId)
			throws DataAccessException {
		logger.debug(AMEConstants.CTX_GET_COMMISION_PERCENTAGE, "Method Star_7");
		Session session = null;
		BigDecimal commPercentage = new BigDecimal(0);
		try {
			session = getSession();

			String hql = "Select commisionId from PolicyQuo where comp_id.policyId = :policyId and comp_id.endtId = :endtId";
			Query query = session.createQuery(hql);
			query.setParameter(com.Constant.CONST_POLICYID,policyId);
			query.setParameter(com.Constant.CONST_ENDTID,endtId);
			Object commissionId = query.uniqueResult();
			if (commissionId != null) {
				commPercentage = new BigDecimal(commissionId.toString());
			}
			logger.debug(AMEConstants.CTX_GET_COMMISION_TYPES, "commPercentage"
					+ commPercentage);
		} catch (HibernateException hibernateException) {
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}
		return commPercentage;
	}

    
    
    /**
	 * The method is used to update the Rejected Quote Details.
	 * 
	 * @param policyQuo
	 *            <code> PolicyQuo </code>
	 * @throws DataAccessException
	 */
    public void updateQuoteDetails(PolicyQuo policyQuo) throws DataAccessException {
        Session session = getSession();
        Long policyId = null;
        Long endtId = null;
        Integer statusCode = null;
        Integer reasonCode = null;
        Integer otherInsurerCode = null;

        if (policyQuo.getComp_id().getPolicyId() != null) {
            policyId = policyQuo.getComp_id().getPolicyId();
            endtId = policyQuo.getComp_id().getEndtId();
            if (policyQuo.getStatus() != null) {
                statusCode = policyQuo.getStatus().getCode();
            }
            if (policyQuo.getOtherInsurerCode() != null) {
                otherInsurerCode = policyQuo.getOtherInsurerCode().getCode();
            }
        }
        //ADM 10.09.2009 CR04 Quote Version : Latest vesion record alone should get updated
        String hql = "update PolicyQuo set status.code = :stCode, otherInsurerCode.code= :code " +
        		"where comp_id.policyId = :policyId and comp_id.endtId =:endtId";
        Query query = session.createQuery(hql);
        query.setParameter("stCode", statusCode);
        //query.setParameter("reasonCode", reasonCode);
        //query.setParameter("comments",comments);
        query.setParameter("code", otherInsurerCode);
        query.setParameter(com.Constant.CONST_POLICYID, policyId);
        query.setParameter(com.Constant.CONST_ENDTID, endtId);
        int rowCount = query.executeUpdate();
        System.out.println("Rows affected: " + rowCount);

    }

    /**
     * Updates quote by PK with the given status.
     * 
     * @param policyQuo
     */
    public void updateQuoteStatusByPK(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug("updateQuoteStatusByPK(PolicyQuo_1", "Method Entere_18");
        Session session = null;
        Query query = null;
        if (policyQuo != null && policyQuo.getComp_id() != null && policyQuo.getStatus() != null) {
            try {
                session = getSession();

                query = session.createQuery(getQuery(QUERY_UPDATE_QUOTE_STATUS));
                logger.debug("updateQuoteStatusByPK(PolicyQuo_2", "Query " + String.valueOf(query));

                logger.debug("updateQuoteStatusByPK(PolicyQuo_3", "Policy Id " + policyQuo.getComp_id().getPolicyId());
                query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());

                logger.debug("updateQuoteStatusByPK(PolicyQuo_4", policyQuo.getComp_id().getEndtId());
                query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());

                logger.debug("updateQuoteStatusByPK(PolicyQuo_5", "Status " + policyQuo.getStatus().getCode());
                query.setParameter(POL_STATUS, policyQuo.getStatus().getCode());

                query.executeUpdate();
            } catch (HibernateException hibernateException) {
                logger.error("updateQuoteStatusByPK(PolicyQuo_6", hibernateException.getMessage());
                throw new DataAccessException(hibernateException);
            } finally {
                closeSession(session);
            }
        }
        logger.debug("updateQuoteStatusByPK(PolicyQuo_7", "Method Exite_13");
    }

    /**
     * This function gives the PolicyQuo object
     * 
     * @param policyId
     * @param endtId
     * @return policyQuo
     * @throws DataAccessException
     */
    public PolicyQuo getQuoteByPolicyId(Long policyId, Long endtId) throws DataAccessException {
        PolicyQuo policyQuo = null;
        String fullname = null;
        logger.debug(CTX_FTH_QUOTE_BY_ID, "Entered with :_6" + policyId + endtId);
        if ((policyId != null)) {
            Session session = getSession();
            Query query = session.createQuery(QRY_QUOTE_RETRIEVE_DETAILS);
            query.setParameter(POLICYID, policyId);
            query.setParameter(ENDORSEMENTID, endtId);
            List result = (List) query.list();
            if (result != null && result.size() > 0) {
                Iterator iterator = result.iterator();
                policyQuo = (PolicyQuo) iterator.next();

                logger.debug(CTX_FTH_QUOTE_BY_ID, "Got the Policy ::" + policyQuo);
                // GET THE DATA FOR LAZY INIT
                if (policyQuo != null) {
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerSource :_3" + policyQuo.getCustomerSource());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerCategory :_3" + policyQuo.getCustomerCategory());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "CustomerType :_3" + policyQuo.getCustomerType());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "PolicyType :_4" + policyQuo.getPolicyType());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, policyQuo.getModifiedBy());
                    //policyQuo.setVehicles(null);

                    // TODO: TO BE REVISITED
                    //policyQuo.setMotorPersonalDetailsForAcc(null);
                    // policyQuo.setMotorPersonalEffectDetailsForAcc(null);
                    /*
                     * logger.debug(CTX_FTH_QUOTE_BY_ID, "Coinsurances ::" +
                     * policyQuo.getCoinsurances());
                     */
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "Vehicles ::" + policyQuo.getVehicles());
                    /*
                     * logger.debug(CTX_FTH_QUOTE_BY_ID, "Coinsurances ::" +
                     * policyQuo.getDrivers());
                     */

                    /*
                     * logger.debug(CTX_FTH_QUOTE_BY_ID, "Declaration Type ::" +
                     * policyQuo.getDeclarationType());
                     */
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "Company ::" + policyQuo.getCompany());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "Agent ::" + policyQuo.getAgent());
                    logger.debug(CTX_FTH_QUOTE_BY_ID, "endorsement ::" + policyQuo.getEndorsementDetails());
                }
            } else
                logger.debug(CTX_FTH_QUOTE_BY_ID, "NO POLICY RETRIEVED ::" + policyQuo);
            closeSession(session);

        }
        logger.debug(CONTEXT_GET_POLICY_FOR_POLICY, "Method En_4");
        return policyQuo;
    }
    
    /**
     * This updates the suspend transaction in policyQuotation table
     * 
     * @param policyQuo
     * @throws DataAccessException
     */
    public void updateSuspendTransaction(PolicyQuo policyQuo) throws DataAccessException{
        
        Long policyId = policyQuo.getComp_id().getPolicyId();
        Long endtId = policyQuo.getComp_id().getEndtId();
        Session session = null;
        logger.debug(CTX_GET_POL_CMT, "PolicyID ::" + policyId);
        logger.debug(CTX_GET_POL_CMT, "ENDT ID ::" + endtId);
        if (policyId != null && endtId != null) {
            try {
                session = getSession();
        policyQuo.setPolSuspendTransaction("Y");
        session.update(policyQuo);
        }catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
    }
    }
    
    public void deletePolicyTextForQuo(RenewalPolicy renewalPolicy) throws DataAccessException{
        logger.debug(CTX_DEL_REN_QUOTE, "Entered in delete renewal quote with::" + renewalPolicy.getRefPolicyId());
        Session session = null;
        try {
            session = getSession();
            Query query = session.createQuery(QRY_DELETE_POL_TXT_FOR_QUO);
            query.setParameter(POLICYID, renewalPolicy.getRefPolicyId());
            query.executeUpdate();
            logger.debug(CTX_DEL_REN_QUOTE, "Exiting:_2");
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
    }
    /**
     * Lapses any renewal quote present for the given policy id.
     * @param policyId
     * @throws DataAccessException
     */
    public void lapseRenewalQuote(Long policyId) throws DataAccessException{
        logger.debug("lapseRenewalQuote(Long_1", "Method Entere_19");
        Session session = null;
        Query query = null; 
        try {
            session = getSession();
            logger.debug("lapseRenewalQuote(Long_2", "Reference Policy id: "+policyId);
            query = session.createQuery(getQuery(QRY_LAPSE_RENEWAL_QUOTE));
            query.setParameter(POLICYID,policyId);
            query.setParameter(POL_STATUS,AMEConstants.LAPSE_STATUS_CODE);
            query.executeUpdate();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug("lapseRenewalQuote(Long_3", "Method Exite_14");
    }
    
    /**
     * This method will fetch the QuoteId for the given reference policyId.
     * @param referencePolicyId <code>Integer</code>
     * @return <code>Long</code>
     * @throws <code>DataAccessException</code>
     */
    public Long getQuoteIdForReferencePolicyId(Integer referencePolicyId) throws DataAccessException {
        Long quoteId = null;
        if(referencePolicyId != null) {
            Query query = getSession().createQuery(QRY_QUOTEID_FOR_REF_POLICYID);
            query.setLong(PARAM_REF_POLICY_ID, referencePolicyId.longValue());
            List quotes = query.list();
            if(quotes != null && quotes.size() > 0) {
                quoteId = (Long)quotes.get(0);
            }
        }
        return quoteId;
    }

    public BigDecimal getGlMaster(Integer ctyCode, Integer regCode,
			Integer locCode, Integer ccCode) throws DataAccessException {
		BigDecimal totAccCode = null;
		List result = null;
		Session session = null;
		Query query = null;
		try {
			session = getSession();
			query = session.createQuery(getQuery("getGlMaster"));
			query.setParameter(com.Constant.CONST_CTYCODE, ctyCode);
			query.setParameter(com.Constant.CONST_REGCODE, regCode);
			query.setParameter(com.Constant.CONST_LOCCODE, locCode);
			query.setParameter("cc", ccCode);
			result = query.list();
			if (result != null && !result.isEmpty()) {
				totAccCode = (BigDecimal) result.get(0);
			}
		} catch (HibernateException hibernateException) {
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}
		return totAccCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rsaame.kaizen.quote.dao.QuoteDAO#getGlAccInterface(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public GlAccMaster getGlAccInterface(Integer ctyCode, Integer regCode,
			Integer locCode, Integer ccCode, BigDecimal totAccCode)
			throws DataAccessException {
		GlAccMaster glAccMaster = null;
		List result = null;
		Session session = null;
		Query query = null;
		try {
			session = getSession();
			query = session.createQuery(getQuery("getGlAccMaster"));
			query.setParameter(com.Constant.CONST_CTYCODE, ctyCode);
			query.setParameter(com.Constant.CONST_REGCODE, regCode);
			query.setParameter(com.Constant.CONST_LOCCODE, locCode);
			query.setParameter("cc", ccCode);
			query.setParameter("totAccCode", totAccCode);
			result = query.list();
			
			if (result != null && !result.isEmpty()) {
				glAccMaster = (GlAccMaster) result.get(0);
			}
		} catch (HibernateException hibernateException) {
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}
		return glAccMaster;
	}
	
	/**
	 * 
	 */
	public DistributionChannel getDistributionChannel(Integer distChannelCode) throws DataAccessException{
        DistributionChannel distributionChannel = null;
        Session session = null;
        Criteria criteria = null;
        session = getSession();
        criteria = session.createCriteria(DistributionChannel.class);
        criteria.add(Restrictions.eq("code",distChannelCode));
        distributionChannel = (DistributionChannel) criteria.uniqueResult();
        return distributionChannel;
    }
	

    public PaginatedResult getTransactionHistory(PolicyQuo policyQuo) throws DataAccessException {
        List transactionHistoryList = new ArrayList();
        List finalTransactionHistoryList = new ArrayList();
        PaginatedResult paginatedResult = null;
        com.rsaame.kaizen.policy.model.Transaction historyBO = new com.rsaame.kaizen.policy.model.Transaction();
        Session session = null;
        int size = 0;
        Iterator iterator = null;
        Object[] object = null;

        logger.debug(CTX_TRANS_HIST, "Entered with :_7" + policyQuo);

        if (policyQuo != null) {
            try {
                session = getSession();
                BigDecimal policyId = null;
                Long pol_quoteid=null;
                Query query = null;
                logger.debug(CTX_TRANS_HIST,"before -policyQuo.getRefPolicyId()"+policyQuo.getRefPolicyId());
                
                //CR04 Quote Versioning (Release 2.5)               
                if(policyQuo.getDocument().getCode().intValue()== 5){                	
                	pol_quoteid=policyQuo.getComp_id().getPolicyId();
                	query = session.createSQLQuery(QRY_TRANS_HIST_NEWQUOTE);                	
                	query.setParameter("pol_quoteid", policyQuo.getComp_id().getPolicyId());	               
	                query.setParameter(AMEConstants.CLASS_CODE, AMEConstants.CL_CODE_VALUE);
	                query.setParameter(AMEConstants.ENDT_ID, policyQuo.getComp_id().getEndtId());                
                }
                
                if(policyQuo.getRefPolicyId() != null){
	                Query polQuery = session.createSQLQuery(QRY_REF_POL_ID);
	                logger.debug(CTX_TRANS_HIST,"policyQuo.getRefPolicyId()"+policyQuo.getRefPolicyId());
	                polQuery.setParameter("REF_POL_ID", policyQuo.getRefPolicyId());
	                policyId = (BigDecimal) polQuery.uniqueResult();
                
                }
                if(policyQuo.getDocumentCode().intValue()!= 5){
	                if(policyId != null){
	                    query = session.createSQLQuery(QRY_TRANS_HIST);
	                
		                logger.debug(CTX_TRANS_HIST,"QUOTE::"+policyQuo.getQuotationNo());
		                logger.debug(CTX_TRANS_HIST,"ISSUE DATE::"+policyQuo.getIssueDate());
		                logger.debug(CTX_TRANS_HIST,"Endt ID"+policyQuo.getComp_id().getEndtId());
		                logger.debug(CTX_TRANS_HIST,"POLICYID"+policyQuo.getComp_id().getPolicyId());
		                logger.debug(CTX_TRANS_HIST,"POLICYID"+policyId);
		                logger.debug(CTX_TRANS_HIST,"POLICY NO::"+policyQuo.getPolicyNo());
		                
		                //query.setParameter("QUO_NO", policyQuo.getQuotationNo());
		                query.setParameter(com.Constant.CONST_POLICYID, policyQuo.getComp_id().getPolicyId());
		                query.setParameter(AMEConstants.POLICY_NO, policyQuo.getPolicyNo());
		                //query.setParameter(AMEConstants.ISSUE_DATE, policyQuo.getIssueDate());
		                query.setParameter(AMEConstants.CLASS_CODE, AMEConstants.CL_CODE_VALUE);
		                //query.setParameter(AMEConstants.POLICY_ID, policyQuo.getComp_id().getPolicyId());
		                query.setParameter(AMEConstants.ENDT_ID, policyQuo.getComp_id().getEndtId());   
	                }
                }
                paginatedResult = new PaginatedResult();

                if (policyQuo.getNumberOfRecords().intValue() == 0) {
                    if(policyId != null || pol_quoteid!=null){
                        transactionHistoryList = query.list();
                    }
                    
                    logger.debug(CTX_TRANS_HIST, "SIZE OF LIST::" + transactionHistoryList.size());
                    size = transactionHistoryList.size();
                    policyQuo.setNumberOfRecords(Integer.valueOf(size));
                    logger.debug(CTX_TRANS_HIST,"policy.getCurrentPage() "+policyQuo.getCurrentPage());
                    if(policyQuo.getCurrentPage() == null){
                    	policyQuo.setCurrentPage(Integer.valueOf(1));
                    }
                    logger.debug(CTX_TRANS_HIST,"policy.getCurrentPage() "+policyQuo.getCurrentPage());
                    getPaginatedResult(query, policyQuo.getCurrentPage(), size, paginatedResult);
                    if (size > paginatedResult.getRecordsPerPage().intValue()) {
                        /*
                         * if the size of the list is greater than the no. of
                         * records per page historyList will contain the no.of
                         * records to be displayed in the selected page
                         */
                        int firstPageRecords = (paginatedResult.getRecordsPerPage().intValue() * paginatedResult
                                .getCurrentPage().intValue())
                                - paginatedResult.getRecordsPerPage().intValue();
                        for (int i = firstPageRecords; i < firstPageRecords
                                + paginatedResult.getRecordsPerPage().intValue(); i++) {
                            Object obj = transactionHistoryList.get(i);
                            finalTransactionHistoryList.add(obj);
                        }
                    } else {
                        //if the size is less than the total no of records per
                        // page
                        //display the whole list
                        iterator = transactionHistoryList.iterator();
                        while (iterator.hasNext()) {
                            Object obj = (Object) iterator.next();
                            finalTransactionHistoryList.add(obj);
                        }
                    }
                } else {
                    if (policyQuo.getNumberOfRecords() != null) {
                        size = policyQuo.getNumberOfRecords().intValue();
                    }
                    getPaginatedResult(query, policyQuo.getCurrentPage(), size, paginatedResult);
                    finalTransactionHistoryList = query.list();
                }
                iterator = finalTransactionHistoryList.iterator();
                List historyList = new ArrayList();
                while (iterator.hasNext()) {
                    historyBO = new com.rsaame.kaizen.policy.model.Transaction();
                    object = (Object[]) iterator.next();

                    if (object[0] != null) {
                        historyBO.setTransactionLastModifiedBy(AMEUtil.ObjectToString(object[0]));
                    }
                    if (object[1] != null) {
                        historyBO.setTransactionDateTime(AMEUtil.ObjectToString(object[1]));
                    }
                    if (object[2] != null) {
                        historyBO.setTransactionType(AMEUtil.ObjectToString(object[2]));
                    }
                    if (object[3] != null) { 
	                    historyBO.setComments(AMEUtil.ObjectToString(object[3])); 
	                }
                    if (object[4] != null) {
                        historyBO.setEffectiveDate(AMEUtil.ObjectToString(object[4]));
                    }
                    if (object[5] != null) {
                        historyBO.setExpiryDate(AMEUtil.ObjectToString(object[5]));
                    }
                    if (object[6] != null) {
                        historyBO.setTransactionPremium(AMEUtil.ObjectToString(object[6]));
                    }
                    if (object[7] != null) {
                        historyBO.setTransactionStatus(AMEUtil.ObjectToString(object[7]));
                    }
                    if (object[8] != null) {
                        historyBO.setTransactionPolicyNumber(AMEUtil.ObjectToString(object[8]));
                    }
                    if (object[9] != null) {
                        historyBO.setTransactionNumber(AMEUtil.ObjectToString(object[9]));
                    }
                    if (object[10] != null) {
                        historyBO.setTransactionEndorsementNumber(AMEUtil.ObjectToString(object[10]));
                    }
                    if (object[11] != null) {
                        historyBO.setQuotationNo(AMEUtil.ObjectToString(object[11]));
                    }
                    if (object[12] != null) {
                        historyBO.setIssueDate(AMEUtil.ObjectToString(object[12]));
                    }

                    historyList.add(historyBO);

                    logger.debug("User ID: ", historyBO.getTransactionLastModifiedBy());
                    logger.debug("Transaction date and time: ", historyBO.getTransactionDateTime());
                    logger.debug("Transaction Type: ", historyBO.getTransactionType());
                    logger.debug("Comments: ", historyBO.getComments());
                    logger.debug("Inception Date: ", historyBO.getEffectiveDate());
                    logger.debug("Expiry Date: ", historyBO.getExpiryDate());
                    logger.debug("Transaction Premium: ", historyBO.getTransactionPremium());
                    logger.debug("Status: ", historyBO.getTransactionStatus());
                    logger.debug("PolicyNo: ", historyBO.getTransactionPolicyNumber());
                    logger.debug("PolicyId: ", historyBO.getTransactionNumber());
                    logger.debug("EndtId: ", historyBO.getTransactionEndorsementNumber());
                    logger.debug("QuotationNo: ", historyBO.getQuotationNo());

                    System.out.println("****************************************************************************");
                }

                if (historyList != null) {
                    paginatedResult.setObjectArray(historyList.toArray());
                }
               
            } catch (HibernateException hibernateException) {
                throw new DataAccessException(hibernateException);
            } finally {
                closeSession(session);
            }
        }
        logger.debug(CTX_TRANS_HIST, "Returning transactionHistoryList :: " + paginatedResult);
        return paginatedResult;
    }
    
    public PolicyQuo fetchQuoteByPK(Long policyId, Long endtId) throws DataAccessException{
    	PolicyQuo policyQuo = null;
    	logger.debug("fetchQuoteByPK(Long policyId, Long endtId)","Method entered");
    	Criteria criteria = null;
    	try {
			Session session = getSession();
			criteria = session.createCriteria(PolicyQuo.class);
			criteria.add(Restrictions.eq(com.Constant.CONST_COMP_ID_POLICYID,policyId));
			criteria.add(Restrictions.eq(com.Constant.CONST_COMP_ID_ENDTID,endtId));
			policyQuo = (PolicyQuo) criteria.uniqueResult();
		} catch (HibernateException hibernateException) {
			throw new DataAccessException(hibernateException);
		}
    	logger.debug("fetchQuoteByPK(Long policyId, Long endtId)","Method exited "+policyQuo);
    	return policyQuo;
    }
    
    public PolicyQuo getQuote(Long quoteNo) throws DataAccessException{
        logger.debug(CTX_GET_POL_ID,"entered in getPolicyId with quote::"+ quoteNo);
        Session session = null;
        List quoteList= null;
        List finalQuoteList = null;
        PolicyQuo quote = null;
        try{
            session = getSession();
            Query query =  session.createQuery(QRY_GET_QUOTE_BY_QUOTE_NO);
            query.setParameter(QUOTATION_NO,quoteNo);
            quoteList = query.list();
            logger.debug(CTX_GET_POL_ID,"Quote list is::"+quoteList);
            if (quoteList != null) {
                finalQuoteList = new ArrayList();
                Iterator itr = quoteList.iterator();
                Object[] row = null;
                while (itr.hasNext()) {
                    finalQuoteList.add(parseQuotes((Object[]) itr.next()));
                }
                if(finalQuoteList != null){
                    quote = (PolicyQuo)finalQuoteList.get(0);
                }
            }
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return quote;
    }
    
    
    private PolicyQuo parseQuotes(Object[] row) {
        PolicyQuo policyQuo = new PolicyQuo();
        PolicyPKQuo policyPkQuo = new PolicyPKQuo();
        //row[0] refers to policy id
        policyPkQuo.setPolicyId(new Long(AMEUtil.ObjectToString(row[0])));
        // row[1] refers to effectiveDate
        if (row[1] != null) {
            policyQuo.setEffectiveDate((Date) (row[1]));
        }
        policyQuo.setComp_id(policyPkQuo);
        return policyQuo;
    }

    
    
    public String getCustomerCategoryDescription(Integer custCategory) throws DataAccessException {
        Session session = null;
        String custCategDesc=null;
        try {
            session = getSession();
            Query query = session.createQuery(getQuery(GET_CUSTOMER_CATEG_DESC_QRY));
            query.setParameter("custCode", custCategory);
            custCategDesc = (String) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        return custCategDesc;
    }

    
    public String getPolicyTypeDescription(Integer policyTypeCode, Integer classCode) throws DataAccessException {
        Session session = null;
        String policyTypeDesc=null;
        try {
            session = getSession();
            Query query = session.createQuery(getQuery(GET_POLICY_TYPE_DESC_QRY));
            query.setParameter("polTypeCode", policyTypeCode);
            query.setParameter("classCode", classCode);
            policyTypeDesc = (String) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        return policyTypeDesc;
    }

    
    public Long getMaxEndtId(Long policyId) throws DataAccessException{
        Session session = null;
        Long endtId = null;
        try{
            session = getSession();
            Query query =  session.createQuery(getQuery(MAX_ENDT_ID_QRY));
            query.setParameter(POLICYID,policyId);
            endtId = (Long)query.uniqueResult();
            logger.debug(CTX_GET_POL_ID,"Max endtId is::"+endtId);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return endtId;
    }
    
   //	ADM 11.08.2010 RELEASE 3.2 Ticket 14673 Claim Check After Renewal 
    public Long getMinEndtId(Long policyId) throws DataAccessException{
        Session session = null;
        Long endtId = null;
        try{
            session = getSession();
            Query query =  session.createQuery(getQuery(MIN_ENDT_ID_QRY));
            query.setParameter(POLICYID,policyId);
            endtId = (Long)query.uniqueResult();
            logger.debug(CTX_GET_POL_ID,"Max endtId is::"+endtId);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return endtId;
    }
    
    
    
    //ADM 11.03.2010 Rel 2.5.2 CR#16203 Gross Up : Get Maximum Endt Id for Quote
    public Long getMaxEndtIdQuo(Long policyId) throws DataAccessException{
        Session session = null;
        Long endtId = null;
        try{
            session = getSession();
            Query query =  session.createQuery(getQuery(QRY_MAX_ENDT_ID_FOR_QUO));
            System.out.println("getpolicyId::::::"+policyId);
            query.setParameter(POLICYID,policyId);
            endtId = (Long)query.uniqueResult();
            
            logger.debug("getMaxEndtIdQuo() ::","Max Quote endtId is::"+endtId);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return endtId;
    }
    
    //ADM 11.03.2010 Rel 2.5.2 CR#16203 Gross Up : Get Total Premium for Quote
    public List getTotalPremiumQuo(Long policyId, Long endtId) throws DataAccessException{
    	List totalPremium = new ArrayList();
    	Session session = null;
    	
    	try{
    		session = getSession();
    		Query query =  session.createQuery(getQuery(QRY_TOTAL_PREMIUM_QUOTE));
    		query.setParameter(POLICYID, policyId);
    		query.setParameter(ENDORSEMENTID, endtId);
    		totalPremium = query.list();
    		logger.debug("totalPremium::",totalPremium);
    	} catch (HibernateException hibernateException){
    		throw new DataAccessException(hibernateException);
    	} finally {
            closeSession(session);
        }
    	return totalPremium;
    }
    
    public BigDecimal fetchOutStandingAmount(BigDecimal ctyCode, BigDecimal regCode,
    		BigDecimal locCode, BigDecimal totAccCode, BigDecimal glCode)
			throws DataAccessException{
    	BigDecimal outStandingAmount = null;
    	Session session = null;
    	Criteria criteria = null;
    	logger.debug(CTX_FETCH_OUT_STANDING_AMT, "Method entered ");
    	logger.debug(CTX_FETCH_OUT_STANDING_AMT,"Cty code : "+ctyCode );
    	logger.debug(CTX_FETCH_OUT_STANDING_AMT,"Reg Code " +regCode);
    	logger.debug(CTX_FETCH_OUT_STANDING_AMT,"Loc Code "+locCode);
    	logger.debug(CTX_FETCH_OUT_STANDING_AMT,"Tot Acc Code "+totAccCode);
    	logger.debug(CTX_FETCH_OUT_STANDING_AMT,"GL Code "+glCode);
    	try{
            session = getSession();
            criteria = session.createCriteria(TTrnGlUnmatchedAic.class);
            
			criteria.add(Restrictions.eq(com.Constant.CONST_CTYCODE,ctyCode));
            criteria.add(Restrictions.eq(com.Constant.CONST_REGCODE,regCode));
            criteria.add(Restrictions.eq(com.Constant.CONST_LOCCODE,locCode));
            criteria.add(Restrictions.eq("totAccCode",totAccCode));
            criteria.add(Restrictions.eq(com.Constant.CONST_GLCODE,glCode));
            
            ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("sumAmount"));
            criteria.setProjection(projList);
            
            outStandingAmount = (BigDecimal)criteria.uniqueResult();
            logger.debug(CTX_FETCH_OUT_STANDING_AMT,"Out Standing Amount is::"+outStandingAmount);
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(CTX_FETCH_OUT_STANDING_AMT, "Method exited "+outStandingAmount);
    	return outStandingAmount;
    }
    
    public Long getMarketingFeeForCopyQuote(Integer extAccCd) throws DataAccessException{
    	ExternalExecutive externalExecutive= null;
        Session session = null;
        Criteria criteria = null;
        Long returnval= Long.valueOf(0);
        try{
	        session = getSession();
	        logger.debug("getMarketingFeeForCopyQuote(Integer intAccCd_1", "extAccCd " + extAccCd);
	        
	        criteria = session.createCriteria(ExternalExecutive.class);
	        criteria.add(Restrictions.eq("no",extAccCd));
	        externalExecutive = (ExternalExecutive) criteria.uniqueResult();
	        
	        if(externalExecutive.getMarketingFee()!=null){
	        	  logger.debug("getMarketingFeeForCopyQuote(Integer intAccCd_2", "marketingFee----"+ externalExecutive.getMarketingFee());
	        }else{
	        	  logger.debug("getMarketingFeeForCopyQuote(Integer intAccCd_3", "marketingFee---- is null");
	        }
	      
	        
	        if(externalExecutive != null && externalExecutive.getMarketingFee() != null){
	            returnval= Long.valueOf(externalExecutive.getMarketingFee().longValue());
	        }
	        logger.debug("getMarketingFeeForCopyQuote(Integer intAccCd_4", "returnval----"+ returnval);
	    } catch (HibernateException hibernateException) {
	    	logger.error(CTX_FETCH_QUOTE_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        }
	    logger.error(CTX_GET_RISK_CAT, "Method exite_6");
        return returnval;
    }

	/**
	 * @param policyQuo
	 */
	public Integer getStateTariff(Integer tarCode)throws DataAccessException {
		
		 List list = null;
    	 Session session = null;
    	 Integer stateCode = null;
		
		if(!Utils.isEmpty(tarCode)){
			try {
				 session = getSession();
		            Query query =  session.createQuery(QRY_GET_LOC_CODE_TARIFF);
		            query.setParameter(TARIFF_CODE, tarCode);
				
				
				logger.debug(CTX_GET_LOC_CODE, "Policy comments list size:_4" + query);
         list=query.list();
         
			} catch (HibernateException hibernateException) {
				// TODO Auto-generated catch block
				 throw new DataAccessException(hibernateException);
			}

			
		}
		/*Added try catch block to avoid null value , sonar violation fix on 5-10-2017 */
		try{
			if(!Utils.isEmpty( list.get(0))){
	    		stateCode = (Integer) list.get(0);
	    	}  
		}catch (NullPointerException e) {
			logger.debug("Null pointer exception", e);
		}
		 	
			
		return stateCode;
		
	}

	/** @param policyQuo
	 * @see com.rsaame.kaizen.quote.dao.QuoteDAO#getStateTariff(com.rsaame.kaizen.policy.model.Policy)
	 * Added for fetches the Tariff code from Tariff master for Erater
	 */
	public Integer getStateTariff(Policy policy) throws DataAccessException { List list = null;
	 Session session = null;
	 Integer stateCode = null;
	
	if(!Utils.isEmpty(policy.getTarCode())){
		try {
			 session = getSession();
	            Query query =  session.createQuery(QRY_GET_LOC_CODE_TARIFF);
	            query.setParameter(TARIFF_CODE, policy.getTarCode());
			
			logger.debug(CTX_GET_LOC_CODE, "Location Code for Erater::" + query);
			list=query.list();
     
		} catch (HibernateException hibernateException) {
			// TODO Auto-generated catch block
			 throw new DataAccessException(hibernateException);
		}
		
	}
	try{
			if(!Utils.isEmpty( list.get(0))){
		        stateCode = (Integer) list.get(0);		
			}   	
		}catch (NullPointerException e) {
		    logger.debug("Null pointer exception", e);
		}
		
	return stateCode;
	}
	
	//ADM 02.11.2010 Rating reconfiguration - To get Indicator if Configuration is done
	public Integer getConfigInd(Integer tarCode) throws DataAccessException {
		Session session = null;
		Query query = null;
		Integer configInd = null;
		
		if (tarCode != null){
			try {
				session = getSession();
				query = session.createQuery(QRY_GET_TARIFF_CONFIG_IND);
				query.setParameter(TARIFF_CODE, tarCode);
				configInd = (Integer) query.uniqueResult();
				logger.debug("getConfigInd() ::", "Tariff Config Indicator::" + configInd);
			} catch (HibernateException hibernateException){
				throw new DataAccessException(hibernateException);
			}
		}
		return configInd;
	}
	
	//  ADM 02.04.2009 Request Id 7483
	//	Fix to view the proposalform name with quote number  -Start
	/* (non-Javadoc)
	 * @see com.rsaame.kaizen.quote.dao.QuoteDAO#retriveQuoteNo(com.rsaame.kaizen.quote.model.PolicyQuo)
	 */
	
	 public Long retriveQuoteNo(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(AMEConstants.CTX_VIEW_QUOTE_NUMBER, "Method Star_8");

        Session session = null;
        Long quotatuion_No = null;

        try {
            session = getSession();

            String finalQuery = getQuery("quotationNO");
            logger.debug(AMEConstants.CTX_VIEW_QUOTE_NUMBER, "Query:_4" + finalQuery);

            //	Run the query to get the required List
            Query query = session.createQuery(finalQuery);
            query.setLong(com.Constant.CONST_POLICYID, policyQuo.getComp_id().getPolicyId().longValue());
            List QuotationNo = query.list();
            
            if (QuotationNo != null) {
               
               Iterator iterPolicy = QuotationNo.iterator();
                int policyIndex = 0;
                while (iterPolicy.hasNext()) {
                	quotatuion_No = (Long)iterPolicy.next();
                }
                logger.debug(AMEConstants.CTX_VIEW_QUOTE_NUMBER, "No. of Quotation Number22 =" + quotatuion_No);
            }
           
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_ONE, "End Metho_3");
        return quotatuion_No;

    }	 

	/**
	 * ADM 19.08.2009 CR66 Cap & collar (Release 2.5) – This method for getting
	 * the final premium of the policy
	 * 
	 * @param policyId
	 * @param endId
	 * 
	 * @throws SQLException
	 */
	public float getExpPolFinalPremium(Long policyId, Long endtId)
			throws DataAccessException {

		float finalPremiumValue = 0;
		logger.debug("CTX_EXP_POL_FINAL_PREMIUM", "Method Started");

		Session session = null;
		try {
			session = getSession();

			String GET_EXP_POL_FINAL_PRM = "select PKG_RENEWAL_PROCESS.GET_EXP_POL_FINAL_PRM ("
					+ policyId + "," + endtId + ") from dual";
			Query query = session.createSQLQuery(GET_EXP_POL_FINAL_PRM);
			
			finalPremiumValue = Float.valueOf(query.uniqueResult().toString()).longValue();
		} catch (HibernateException hibernateException) {
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}
		logger.debug(CTX_EXP_POL_FINAL_PREMIUM, "Method Ended");

		return finalPremiumValue;
	}

	/**
	 * ADM 19.08.2009 CR66 Cap & collar (Release 2.5) – Method to call 
	 * the procedure for calculating the premium for renewal Quote
	 * 
	 * @param refPolicyId
	 * @param refEndId
	 * @param quotationId
	 * @param capCollerGradient
	 * 
	 * @throws SQLException
	 */
	public void callUpdateProcedure(Long refPolicyId, Long refEndId,
			Long quotationId, Float capCollarGradient)
			throws DataAccessException {

		CallableStatement proc = null;
		Session session = null;

		try {
			session = getSession();
			logger.debug(CTX_CALL_PROCEDURE, "Procedure to be started");

			session.flush();
			proc = session.connection().prepareCall("{ call PKG_RENEWAL_PROCESS.PRO_PROCESS_RENEWALS(?,?,?,?) }");
			proc.setLong(1, refPolicyId.longValue());
			proc.setLong(2, refEndId.longValue());
			proc.setLong(3, quotationId.longValue());
			proc.setFloat(4, capCollarGradient.floatValue());
						
			logger.debug(CTX_CALL_PROCEDURE, "((((((procprocprocproc)))))"
					+ proc);
			proc.execute();
			logger.debug(CTX_CALL_PROCEDURE, "Procedure finishe_5");
			session.flush();
		} catch (SQLException sqlException) {
			logger.error(CTX_CALL_PROCEDURE, sqlException.getMessage());
			throw new DataAccessException(sqlException);
		}
		finally
		{
			closeSession( session );
		}
	}
	 //	Fix to view the proposalform name with quote number -End
//ADM 25.08.2009 CR35 Transaction details (Release 2.5)

/*	    public PolicyQuo saveMovedQuotationDetails(PolicyQuo quote) throws DataAccessException {
	        logger.debug("saveMovedQuotationDetails", "Method Entere_20");
	        Session session = null;
	        try {	        	
				 session = getSession();
				 Query hibernateQuery = null;
		            hibernateQuery = session.createQuery("update PolicyQuo policyQuo set policyQuo.coverNoteHour =:coverNoteHour ,policyQuo.tarCode =:tarCode, "
		        			+ "policyQuo.policyTypeCode =:policyTypeCode,policyQuo.policyTerm =:policyTerm , policyQuo.effectiveDate =:effectiveDate, "
							+ "policyQuo.expiryDate =:expiryDate where policyQuo.quotationNo=:quotationNo");

		            hibernateQuery.setParameter("coverNoteHour", quote.getCoverNoteHour());
		            hibernateQuery.setParameter("tarCode", quote.getTarCode());
		            hibernateQuery.setParameter("policyTypeCode", quote.getPolicyTypeCode());
		            
		            hibernateQuery.setParameter("policyTerm",quote.getPolicyTerm());
		            hibernateQuery.setParameter("effectiveDate",quote.getEffectiveDate());
		            hibernateQuery.setParameter("expiryDate", quote.getExpiryDate());
		            hibernateQuery.setParameter("quotationNo", quote.getQuotationNo());
		            System.out.println(quote.getQuotationNo()+"   Quotation no "+hibernateQuery.toString());
		            hibernateQuery.executeUpdate();            

	        } catch (HibernateException hibernateException) {
	            throw new DataAccessException(hibernateException);
	        }

	        logger.debug("saveMovedQuotationDetails", "Method Exite_15");
	        return quote;
	    }*/
	 
	    public PolicyQuo saveMovedQuotationDetails(PolicyQuo quote) throws DataAccessException {
	        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Entere_21");

	        try {
	            this.hibernateTemplate.merge(quote);	            

	        } catch (HibernateException hibernateException) {
	            throw new DataAccessException(hibernateException);
	        }

	        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Exite_16");
	        return quote;
	    }

/* 
	 * ADM 30.07.2009 CR63 Refferal Flow (Release 2.5) - Fetch Approved Version of Quote 
	 */
	public PolicyQuo getApprovedVersionOfQuote(PolicyQuo policyQuo) throws DataAccessException {
	 	PolicyQuo approvedQuo = new PolicyQuo();
	 	PolicyPKQuo quoPK = new PolicyPKQuo();
	 	Session session = null;
	 	Query query = null;
	 	Long approvedEndtId = null;
	 	Long currentEndtId = policyQuo.getComp_id().getEndtId();
	 	Long quoteId = policyQuo.getComp_id().getPolicyId();
	 	
	 	try{
	 		// For null value check to avoid sonar violation on 10-3-2017
		 //	if ( policyQuo == null || policyQuo.getComp_id() == null || policyQuo.getComp_id().getPolicyId() == null) {
	 		if ( Utils.isEmpty(policyQuo) || Utils.isEmpty(policyQuo.getComp_id()) || Utils.isEmpty(policyQuo.getComp_id().getPolicyId())) {
	            throw new DataAccessException(AMEConstants.POLICY_NOT_AVAILABLE);
	        }
		 	//Fetch Approved Quote Version
		 	session = getSession();
		 	session.evict(policyQuo);
		 	if (policyQuo.getIsVersionSaved() != null 
		 			&& policyQuo.getIsVersionSaved().equalsIgnoreCase(AMEConstants.VERSION_SAVED_IND_YES)){
			 	query = session.createQuery(QRY_APPROVED_VERSION);
			 	query.setParameter(POLICYID,quoteId);
			 	query.setParameter(ENDORSEMENTID,currentEndtId);
			 	logger.debug(CTX_FETCH_APPROVED_QUOTE_VERSION, "Query ::" + query);
			 	approvedEndtId = (Long) query.uniqueResult();
		 	} else {
		 		query = session.createQuery(QRY_APPROVED_VERSION_NOTSAVED);
			 	query.setParameter(POLICYID,quoteId);
			 	logger.debug(CTX_FETCH_APPROVED_QUOTE_VERSION, "Query ::" + query);
			 	approvedEndtId = (Long) query.uniqueResult();
		 	}
		 	logger.debug(CTX_FETCH_APPROVED_QUOTE_VERSION, "Approved Quote Version Endt Id ::" + approvedEndtId);
		 	
		 	//Return Approved Version Quote
		 	if (approvedEndtId != null ){
			 	quoPK.setPolicyId(quoteId);
			 	quoPK.setEndtId(approvedEndtId);
			 	approvedQuo.setComp_id(quoPK);
			 	approvedQuo = getQuoteByPolicyId(quoteId, approvedEndtId);
		 	} else {
		 		approvedQuo = null;
		 	}
	 	} catch (HibernateException hibernateException) {
	 		logger.error(CTX_FETCH_APPROVED_QUOTE_VERSION, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
	 	} finally {
            closeSession(session);
        }
	 	
	 	logger.debug(CTX_FETCH_APPROVED_QUOTE_VERSION, "Method exite_7");
	 	return approvedQuo;
	 }
	
	/* 
	 * ADM 13.08.2009 CR63 Refferal Flow (Release 2.5) - Update Approved Indicator
	 */
	public void updateApprovedIndicator(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug("updateApprovedIndicator(PolicyQuo_1", "Method Entere_22");
        Session session = null;
        Query query = null;
        if (policyQuo != null && policyQuo.getComp_id() != null && policyQuo.getComp_id().getPolicyId() != null) {
            try {
                session = getSession();

                query = session.createQuery(QRY_UPDATE_APPROVED_IND);
                logger.debug("updateQuoteStatusByPK(PolicyQuo_8", "Query " + String.valueOf(query));

                logger.debug("updateQuoteStatusByPK(PolicyQuo_9", "Policy Id " + policyQuo.getComp_id().getPolicyId());
                query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());

                logger.debug("updateQuoteStatusByPK(PolicyQuo_10", policyQuo.getComp_id().getEndtId());
                query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());

                logger.debug("updateQuoteStatusByPK(PolicyQuo_11", "Status " + policyQuo.getApprovedInd());
                query.setParameter(APPROVED_IND, policyQuo.getApprovedInd());

                query.executeUpdate();
            } catch (HibernateException hibernateException) {
                logger.error("updateApprovedIndicator(PolicyQuo_2", hibernateException.getMessage());
                throw new DataAccessException(hibernateException);
            } finally {
                closeSession(session);
            }
        }
        logger.debug("updateApprovedIndicator(PolicyQuo_3", "Method Exite_17");
    }	

   //ADM 15.08.2009 : CR04 Quote versioning start
	    /**
	     * Method to getMaxEndorsementNoForQuo
	     * 
	     * @param policyId
	     * @return Integer
	     * @throws DataAccessException
	     */
	public Integer getMaxEndorsementNoForQuo(Long policyId) throws DataAccessException {
        logger.debug(GET_MAX_ENDORESEMENT_NO_FOR_QUO, "Method Entere_23");
        boolean flag = false;
        // Create the SessionFactory, Session
        SessionFactory sessionFactory = null;
        Session session = null;
        Query query = null;
        Long endorsementNumber = null;
        try {
            /*sessionFactory = hibernateTemplate.getSessionFactory();*/
            session = getSession();
            query = session.createQuery(getQuery(QRY_MAX_ENDT_NO_FOR_QUO));
            query.setParameter(POLICYID, policyId);
            logger.debug(GET_MAX_ENDORESEMENT_NO_FOR_QUO, query);
            endorsementNumber = (Long) query.uniqueResult();
            logger.debug(GET_MAX_ENDORESEMENT_NO_FOR_QUO, "endorsementNumber: " + endorsementNumber);
        } catch (HibernateException hibernateException) {
            logger.error(GET_MAX_ENDORESEMENT_NO_FOR_QUO, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(GET_MAX_ENDORESEMENT_NO_FOR_QUO, "***** Method Exited ******");
        return new Integer(endorsementNumber.toString());
    }
	 
	 /**
     * This method will fetch Last Active Quotation
     * 
     * @param PolicyQuo
     * @return PolicyQuo
	 * @throws DataAccessException
	 */
public PolicyQuo getLastActiveQuote(PolicyQuo policyQuo) throws DataAccessException {
        Session session = null;
        PolicyQuo quo = new PolicyQuo();
        Long entId = null;
        PolicyPKQuo quoPk = new PolicyPKQuo();
        logger.debug(GET_LAST_ACTIVE_QUO, "entering Method");
        try {
            if (policyQuo == null || 
            		(policyQuo!=null && ( policyQuo.getComp_id() == null || policyQuo.getComp_id().getPolicyId() == null)) ){
                throw new DataAccessException(AMEConstants.QUO_NOT_AVAILABLE);
            }
              
            logger.debug(GET_LAST_ACTIVE_QUO, "ss");
            Query query =null;
            if(!Utils.isEmpty( session )){
            	query = session.createQuery(getQuery(QRY_MAX_ENDT_ID_FOR_QUO));
            }
            if(!Utils.isEmpty( query )){
            	query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            	//  	query.setParameter(POL_STATUS, statusCode);
            	logger.debug(QRY_MAX_ENDT_ID_FOR_QUO, query);
            	entId = (Long) query.uniqueResult();
            }	
            quoPk.setPolicyId(policyQuo.getComp_id().getPolicyId());
            quoPk.setEndtId(entId);

            quo.setComp_id(quoPk);
            quo = getQuoteDetails(quo);
            
        } catch (HibernateException hibernateException) {
            logger.error(GET_LAST_ACTIVE_QUO, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(GET_LAST_ACTIVE_QUO, "Exiting Method");
        return quo;
    }
    
    /**
     * This method will fetch the policy details
     * 
     * @param PolicyQuo
     * @return PolicyQuo
     * @throws DataAccessException								
     */
    public PolicyQuo getQuoteDetails(PolicyQuo quo) throws DataAccessException {
        PolicyQuo policyQuo = null;
        CashCustomerQuo cashCustomerQuo = null;
       /* BeanFactory beanFactory = BeanFactory.getInstance();*/
        //CashCustomerDAO cashCustomerDAO = (CashCustomerDAO) beanFactory.getBean("cashCustomerDAO");
        SessionFactory sessionFactory = null;
        Session session = null;
        logger.debug(GET_QUO_DETAILS, "Entered with :_8" + quo.getComp_id().getPolicyId()+"&"+quo.getComp_id().getEndtId());
        try {
            session = getSession();
            Query query = session.createQuery(getQuery(QRY_GET_OLD_QUO));
            query.setParameter(POLICYID, quo.getComp_id().getPolicyId());            
            query.setParameter(ENDORSEMENTID, quo.getComp_id().getEndtId());
            logger.debug(GET_QUO_DETAILS, "quo.getComp_id().getPolicyId()" +quo.getComp_id().getPolicyId());
            logger.debug(GET_QUO_DETAILS, "quo.getComp_id().getEndtId()" +quo.getComp_id().getEndtId());
            policyQuo = (PolicyQuo) query.uniqueResult();
            if (!Utils.isEmpty( policyQuo)) {
                logger.debug(GET_QUO_DETAILS, policyQuo.getLocation());
                logger.debug(GET_QUO_DETAILS, policyQuo.getCurrency());
                logger.debug(GET_QUO_DETAILS, policyQuo.getCustomerCategory());
                logger.debug(GET_QUO_DETAILS, policyQuo.getCompany());
                logger.debug(GET_QUO_DETAILS, policyQuo.getCustomerType());
                logger.debug(GET_QUO_DETAILS, policyQuo.getVehicles());
                logger.debug(GET_QUO_DETAILS, policyQuo.getCoinsurances());
                logger.debug(GET_QUO_DETAILS, policyQuo.getSourceOfBusiness());
                logger.debug(GET_QUO_DETAILS, policyQuo.getPolicyTypeCode());
                logger.debug(GET_QUO_DETAILS, policyQuo.getCoinsuranceType());
                logger.debug(GET_QUO_DETAILS, policyQuo.getLocation());
                logger.debug(GET_QUO_DETAILS, policyQuo.getAgent());
                logger.debug(GET_QUO_DETAILS, policyQuo.getPolicyNo());
                logger.debug(GET_QUO_DETAILS, policyQuo.getComp_id().getEndtId());
                logger.debug(GET_QUO_DETAILS, policyQuo.getPolicyTypeCode());
                logger.debug(GET_QUO_DETAILS, policyQuo.getDistributionChannel());
                logger.debug(GET_QUO_DETAILS, policyQuo.getCashCustomerQuo());
            }
//            cashCustomerQuo = cashCustomerDAO.getCashCustomerForQuoWithEndId(policyQuo);
//            if (cashCustomerQuo != null && policyQuo != null) {
//            	policyQuo.setCashCustomerQuo(cashCustomerQuo);
//            } else {
//            	policyQuo.setCashCustomerQuo(null);
//            }
            if(!Utils.isEmpty( policyQuo)){
            	logger.debug(GET_QUO_DETAILS, policyQuo.toString());
            }	
        } catch (HibernateException hibernateException) {
            DataAccessException exception = new DataAccessException(hibernateException);
            Message message = new Message("Message", com.Constant.CONST_OBJECT_NOT_FOUND_EXCEPTION);
            exception.addMessage(message);
            throw exception;
        } finally {
            closeSession(session);
        }
        return policyQuo;
    }  
    
    public CashCustomerQuo fetchCashCustomerForQuo(CashCustomerQuo cashCustomerQuo) throws DataAccessException {
        logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_ONE, "Method Star_9");

        Session session = null;
        CashCustomerQuo oldCashCustomerQuo = null;

        try {
            session = getSession();

            String finalQuery = getQuery(AMEConstants.CASH_CUSTOMER_VIEW_QUERY);
            logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_ONE, "Query:_5" + finalQuery);

            Query query = session.createQuery(finalQuery);
            query.setLong(com.Constant.CONST_POLICYID, cashCustomerQuo.getComp_id().getPolicyId().longValue());
            query.setLong(com.Constant.CONST_ENDTID, cashCustomerQuo.getEndtId().longValue());

            oldCashCustomerQuo = (CashCustomerQuo) query.uniqueResult();
            
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }

        logger.debug(AMEConstants.CTX_VIEW_QUOTE_PAGE_ONE, "End Metho_4");
        return oldCashCustomerQuo;
    }  

	public PolicyQuo insertQuotationDetails(PolicyQuo quote) throws DataAccessException {
        logger.debug(AMEConstants.CTX_INSERT_QUOTATION_DETAILS, "Method Entere_24");

        try {
            this.hibernateTemplate.save(quote);
            this.hibernateTemplate.flush();

        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        logger.debug(AMEConstants.CTX_SAVE_QUOTATION_DETAILS, "Method Exite_18");
        return quote;
    }
	 
	 /**
	 * @param policyId
	 * @throws SQLException
	 */
	public void callUpdateProcedureVersion(Long policyId,Long endtId, Long srcNumber)
		throws DataAccessException {
	
		CallableStatement proc = null;
		Session session = null;
		try {
			session = getSession();
			logger.debug(CTX_CALL_PROCEDURE_VERSION, "Procedure to be started after open connection_3");
			logger.debug(CTX_CALL_PROCEDURE_VERSION, "policyId ::" + policyId.longValue());
			logger.debug(CTX_CALL_PROCEDURE_VERSION, "endtId ::" + endtId.longValue());
			logger.debug(CTX_CALL_PROCEDURE_VERSION, "srcNumber ::" + srcNumber.longValue());
			proc = session.connection().prepareCall("{ call PRO_INSERT_QUO_DATA(?,?,?) }");			
		    proc.setLong(1,policyId.longValue());
		    proc.setLong(2,endtId.longValue());
		    proc.setLong(3,srcNumber.longValue());
		    proc.execute();
		    
		    logger.debug(CTX_CALL_PROCEDURE, "Procedure finishe_6");
		    
		    
		} catch (SQLException sqlException) {
			logger.error(CTX_CALL_PROCEDURE, sqlException.getMessage());
			throw new DataAccessException(sqlException);			
		}finally{
			//Sonax fix for db close on 20-9-2017
			if(proc!=null){
				try{
					proc.close();
				}catch (SQLException sqlException) {
				logger.debug( "An error occurred while calling procedre.",sqlException);
				}
				
			}
			closeSession( session );
		}
	 }
	
	public PolicyQuo removePolicyQuo(PolicyQuo quote) throws DataAccessException {
        logger.debug(AMEConstants.CTX_REMOVE_QUOTATION_DETAILS, "Method Entere_25");

        try {
            this.hibernateTemplate.evict(quote);

        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        logger.debug(AMEConstants.CTX_REMOVE_QUOTATION_DETAILS, "Method Exite_19");
        return quote;
    }

	public void updateExpiryDtForQuote(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(AMEConstants.CTX_UPDT_EXPIRY_DT_QUOTE, "Entered in policy quote with::"
                + policyQuo.getComp_id().getPolicyId());
        Session session = null;
        try {
            session = getSession();
            Query query = session.createQuery(QRY_UPDT_OLD_QUO_DETAILS);
            //ADM 27.10.2009 : UAT Issue fix : Previous version record's expiry dt = current version's valstartdt  
            query.setParameter("date", policyQuo.getValidityStartDate());
            query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            query.setParameter(PARAM_VALIDITY_EXPIRY, new Date("12/31/2049"));
            query.setParameter(ENDTID, policyQuo.getComp_id().getEndtId());

            logger.debug(AMEConstants.CTX_UPDT_EXPIRY_DT_QUOTE, "Query:: " + query.toString());
            query.executeUpdate();
            logger.debug(AMEConstants.CTX_UPDT_EXPIRY_DT_QUOTE, "Exiting:_3");
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } 
    }	
	
	
	
   //	ADM 11.08.2010 RELEASE 3.2 Ticket 14673 Claim Check After Renewal
    public void updatePolicyClaimCount(PolicyQuo policyQuo) throws DataAccessException {
    	logger.debug("updatePolicyClaimCount()::_1", "Method Entere_26");
    	Session session = null;
    	Query query = null;
    	
    	try {
    		logger.debug("updatePolicyClaimCount()::_2", policyQuo.getComp_id().getPolicyId());
    		logger.debug("updatePolicyClaimCount()::_3", policyQuo.getComp_id().getEndtId());
    		logger.debug("updatePolicyClaimCount()::_4",policyQuo.getPolicyClaimCount());
            session = getSession();
            query = session.createQuery(QRY_UPDATE_CLAIM_COUNT);
            query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());
            query.setParameter(POL_CLAIM_COUNT, policyQuo.getPolicyClaimCount());
            query.executeUpdate();
            logger.debug("updatePolicyClaimCount():: Query :: ", QRY_UPDATE_CLAIM_COUNT);
            
    	} catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug("updatePolicyClaimCount()::_5", "Method Exite_20");
    }
    //ADM 01.10.2010  Release 4.0 - Oman MultiBranch Transactions within Single DB Schema - Starts
    
   /* public String getBrokerDesc(String brkCode,String brLocation) throws DataAccessException {
    	logger.debug("getBrokerDesc(String brkCode,String brLocation) ", "Method Entere_27");

    	Session session = null;
    	String brokerDesc = null;
    	Query query = null;
    	try {
    	session = getSession();
    	query = session.createQuery(QRY_GET_BRK_DESC_FROM_CODE);
    	query.setParameter(BR_CODE, new Integer(brkCode));
     	query.setParameter(BR_LOCATION, new Integer(brLocation));
     	brokerDesc = query.uniqueResult().toString();
    	
    	} catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug("getBrokerDesc(String brkCode,String brLocation):: ", "Method Exite_21");
    	return brokerDesc;

    }*/
    
    public boolean checkIfBrokerExists(String brokerCode,String location)throws DataAccessException {
    	logger.debug("checkIfBrokerExists(String broker_Name,String location):: ", "Method Entere_28");
    	boolean isBrokerOrAgentExists = false;
    	Session session = null;
    	try {
    	session = getSession();
    	Query query = session.createSQLQuery(QRY_GET_BRK_DESC);
    	query.setParameter(BR_CODE,brokerCode);
    	query.setParameter(BR_LOCATION, new Integer(location));
    	List brokerDesc = (List) query.list();
    	
    	if(brokerDesc != null && brokerDesc.size() > 0){
			isBrokerOrAgentExists = true;					
		}
    	} catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
    	logger.debug("checkIfBrokerExists(String broker_Name,String location):: ", "Method Exite_22");
    	return isBrokerOrAgentExists;
    }
    
    
    
  /*  public String getAgentDesc(String agentCode,String agentLocation) throws DataAccessException {
    	logger.debug("getAgentDesc(String agentCode,String agentLocation) ", "Method Entere_29");

    	Session session = null;
    	String agentDesc = null;
    	Query query = null;
    	try {
    	session = getSession();
    	query = session.createQuery(QRY_GET_AGENT_DESC_FROM_CODE);
    	query.setParameter(AGT_AGENT_CODE, new Long(agentCode));
     	query.setParameter(AGT_LOC_CODE, new Integer(agentLocation));
     	agentDesc = query.uniqueResult().toString();
    	
    	} catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug("getAgentDesc(String agentCode,String agentLocation):: ", "Method Exite_23");
    	return agentDesc;

    }*/
    
  /*  public boolean checkIfAgentExists(String agentName,String location)throws DataAccessException {
    	logger.debug("checkIfAgentExists(String agentName,String location):: ", "Method Entere_30");
    	boolean isBrokerOrAgentExists = false;
    	Session session = null;
    	try {
    	session = getSession();
    	Query query = session.createQuery(QRY_GET_AGENT_DESC);
    	query.setParameter(AGT_E_NAME_1,agentName);
    	query.setParameter(AGT_LOC_CODE, new Integer(location));
    	List agentDesc = (List) query.list();
    	
    	if(agentDesc != null && agentDesc.size() > 0){
			isBrokerOrAgentExists = true;					
		}
    	} catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
    	logger.debug("checkIfAgentExists(String agentName,String location):: ", "Method Exite_24");
    	return isBrokerOrAgentExists;
    }*/
    
    
    public boolean checkIfAgentExists(String agentCode,String location)throws DataAccessException {
    	logger.debug("checkIfAgentExists(String agentName,String location):: ", "Method Entere_31");
    	boolean isBrokerOrAgentExists = false;
    	Session session = null;
    	try {
    	session = getSession();
    	Query query = session.createSQLQuery(QRY_GET_AGENT_DESC);
    	query.setParameter(AGT_AGENT_CODE, new Long(agentCode));
    	query.setParameter(AGT_LOC_CODE, new Integer(location));
    	List agentDesc = (List) query.list();
    	
    	if(agentDesc != null && agentDesc.size() > 0){
			isBrokerOrAgentExists = true;					
		}
    	} catch (HibernateException hibernateException) {
            logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException.getMessage());
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
    	logger.debug("checkIfAgentExists(String agentName,String location):: ", "Method Exite_25");
    	return isBrokerOrAgentExists;
    }
    //ADM 01.10.2010  Release 4.0 - Oman MultiBranch Transactions within Single DB Schema - Ends
    /**
     * @param policyId of Policy
     * @param policyId of Quotation
     * @param OMAN CR - ORANGE CARD
     * @throws DataAccessException
     */
	public void CallInsertVehicleCardToPolicyProc(Long quotationPolicyId,Long policyId,Date  polvalStrtDate)
		throws DataAccessException {
	
		CallableStatement proc = null;
		Session session = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			session = getSession();
			logger.debug(CTX_CALL_PROCEDURE_VEHICLE_CARD, "Procedure to be started after open connection_4");
			logger.debug(CTX_CALL_PROCEDURE_VEHICLE_CARD, "policyId ::" + policyId.longValue());
			logger.debug(CTX_CALL_PROCEDURE_VEHICLE_CARD, "quotationpolicyid ::" + quotationPolicyId.longValue());
			logger.debug(CTX_CALL_PROCEDURE_VEHICLE_CARD, "date coming in ::" + polvalStrtDate);
			//logger.debug(CTX_CALL_PROCEDURE_VEHICLE_CARD, "date after conversion" + dateFormat.format(polvalStrtDate));
			proc = session.connection().prepareCall("{ call PRO_INS_VEH_CARD_TO_POLICY(?,?,?) }");			
		   
		   proc.setLong(1,quotationPolicyId.longValue());
		   proc.setLong(2,policyId.longValue());
		   String datetoProc = dateFormat.format(polvalStrtDate);
		   proc.setString(3,datetoProc);
		    
		    proc.execute();
  
		    logger.debug(CTX_CALL_PROCEDURE, "Procedure finishe_7");
		    
		    
		} catch (SQLException sqlException) {
			logger.error(CTX_CALL_PROCEDURE, sqlException.getMessage());
			throw new DataAccessException(sqlException);			
		} finally {
			//Sonax fix for db close on 20-9-2017
			if(proc!=null){
				try{
					proc.close();
				}catch (Exception e) {
					logger.debug("Getting exception while calling the procedure", e);
				}
			}
			closeSession( session );
		}
	 }
    
//	 ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0) - Added
	public String getPolLocation(String policyId,String endtId)throws DataAccessException{
		Session session = getSession();
		String locationDesc=null;
	   Query query = session.createSQLQuery("SELECT POL_LOCATION_CODE FROM T_TRN_POLICY_QUO WHERE POL_POLICY_ID ="
		+ policyId + " AND POL_ENDT_ID =" + endtId);
	   //query.setParameter(com.Constant.CONST_POLICYID,policyId);
	   //query.setParameter(com.Constant.CONST_ENDTID,endtId);
	   locationDesc = query.uniqueResult().toString();	   
		return locationDesc;	
	}	
	 
	    public void updateQuotePrmDetails(PolicyQuo policyQuo)
			throws DataAccessException {
		logger.debug("updateQuotePrmDetails(PolicyQuo policyQuo):: ",
				"Method Entere_32");
		Session session = null;
		Query query = null;
		Long policyId = null;
		Long endtId = null;
		Integer currencyCode = null;
		BigDecimal foreignCur = null;
		BigDecimal exchangeRate = null;
		if (policyQuo == null || policyQuo.getComp_id() == null
				|| policyQuo.getComp_id().getPolicyId() == null) {
			logger.error(CTX_LAPSE_RENEWAL_QUOTATION,
					"policy or policy.getComp_id() or_4"
							+ "policy.getComp_id().policy() is/are nul_4");
			throw new DataAccessException("Mandatory parameters are null");
		}
		try {
			session = getSession();

			logger.debug(CTX_UPDATE_PREMIUM_DETAILS, "Before Query _4");

			if (policyQuo.getComp_id().getPolicyId() != null) {
				/*policyId = policyQuo.getComp_id().getPolicyId();*/
				endtId = policyQuo.getComp_id().getEndtId();
				if (policyQuo.getFgnCurrPrm() != null) {
					foreignCur = policyQuo.getFgnCurrPrm();
				}
				if (policyQuo.getExchangeRate() != null) {
					exchangeRate = policyQuo.getExchangeRate();
				}
				if(policyQuo.getCurrency()!=null){
				if (policyQuo.getCurrency().getCode() != null) {
					currencyCode = policyQuo.getCurrency().getCode();
				}
				}
			
			//ADM 10.09.2009 CR04 Quote Version : Latest vesion record alone should get updated
			String hql = "update PolicyQuo policyQuo set policyQuo.currency.code =:currencyCode,policyQuo.fgnCurrPrm =:foreignCur,policyQuo.exchangeRate =:exchangeRate"
					+ " where policyQuo.comp_id.policyId=:policyId and policyQuo.comp_id.endtId =:endtId ";
			query = session.createQuery(hql);

			query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
			query.setParameter(ENDORSEMENTID, policyQuo.getComp_id()
					.getEndtId());
			query.setParameter("currencyCode", currencyCode);
			query.setParameter("foreignCur", foreignCur);
			query.setParameter("exchangeRate", exchangeRate);
			query.executeUpdate();
			//System.out.println("Rows affected: " + rowCount);
			}

		} catch (HibernateException hibernateException) {
			logger.error(CTX_UPDATE_PREMIUM_DETAILS, hibernateException
					.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}
	}

	public User retriveMinAndMaxCurr(Integer UserId) throws DataAccessException {
		logger.info("QuoteDAOImp_1", " retriveMinAndMaxCurr() - Start.. ");
		User quo = new User();
		Query query = null;
		Session session = getSession();
		try {
			query = session.createQuery(getQuery(QRY_MIN_MAX_CURR));
			query.setInteger("userId", UserId.intValue());
			quo = (User) query.uniqueResult();
		} catch (HibernateException hibernateException) {
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}
		logger.info("QuoteDAOImp_2", " User - "+quo);
		logger.info("QuoteDAOImp_3", " retriveMinAndMaxCurr() - End.. ");
		return quo;
	}
	
	
}
