/**
 * 
 */
package com.rsaame.pas.renewals.dao;

import java.sql.Timestamp;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TMasSms;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnMail;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPrintBatchPas;
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;
import com.rsaame.pas.dao.model.TTrnSms;
import com.rsaame.pas.dao.model.VTrnRenewalPoliciesHtPas;
import com.rsaame.pas.dao.model.VTrnRenewalQuotesHtPas;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.policy.svc.CaptureCommentsService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.RenewalResultsVO;
import com.rsaame.pas.vo.bus.RenewalSearchSummaryVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.sun.mail.imap.AppendUID;

/**
 * @author m1019193
 *
 */
/**
 * DAO class which used for generate renewals and print renewals for Home/Travel
 *
 */
public class RenewalCommonDAO extends BaseDBDAO implements IRenewalCommonDAO {

	private final static Logger LOGGER = Logger.getLogger(RenewalCommonDAO.class);
	
	public static final String QRY_PRT_WHERE = " WHERE ";
	public static final String QRY_PRT_AND = " AND ";

	public static final String QRY_PRT_DOT = ".";
	
	public static final String QRY_PRT_ID_DOT = ".id.";

	public static final String QRY_PRT_EQUL = "=:";
	public static final String QRY_PRT_IN =" in (:";

	public static final String QRY_PRT_NOT_EQUL = "!=";

	public static final String QRY_PRT_EQUL_OR_GREATER = ">=:";

	public static final String QRY_PRT_EQUL_OR_LESS = "<=:";
	
	public static final String QRY_PRT_IS = " IS ";
	
	public static final String TRUNCATE ="trunc(";
	
	public static final String QRY_PRT_NOT = " NOT ";
	
	public static final String QRY_PRT_NOT_IN = " NOT IN ";
	
	public static final String QRY_PRT_NULL = " NULL ";

	public static final String BLANK_SPACE = "";
	
	public static final String PARAM_CLASS_CODE = "polClassCode";
	
	public static final String PARAM_POL_NUM = "polPolicyNo";
	
	public static final String PARAM_FROM_DATE = "polEffectiveDate";

	public static final String PARAM_POL_EXP_DATE = "polExpiryDate";
	
	public static final String PARAM_BROKER_CODE = "polBrCode";

	public static final String PARAM_SCHEME_CODE = "polCoverNoteHour";
	
	public static final String PARAM_LOCATION_CODE = "polLocationCode";	
	
	public static final String PARAM_POL_POLICY_STATUS = "polStatusId";

	public static final String PARAM_FIRST_NAME = "insEFirstName";
	
	public static final String PARAM_POL_POLICY_TYPE = "polPolicyType";

	public static final String LIKE_OPERATOR = " LIKE ";

	public static final String PERCENTAGE = "%";

	public static final String SINGLE_QUOTE = "'";

	public static final String COLON = ":";

	public static final String COMMA = ",";	

	public static final String TO_UPPER_CASE = "upper(";

	public static final String OPEN_BRACKET = "(";
	
	public static final String CLOSE_BRACKET = ")";
	
	public static final String CLASS_CODE = "FIRE";	
	
	private final static String TRAVEL_CODE="888";	
	
	private final static String PRINT_BATCH_ID = "SEQ_PAS_PRINT_BATCH_ID";	
	private final static String PRINT_LOC = "PRINT_LOC";
	
	public static final String PARAM_EMAIL_ID = "cshEEmailId";
	
	public static final String PARAM_POLICY_ID = "id.polPolicyId";
	
	public static final Short BROKER_CHANNEL = 4;
	public static final String PARAM_DC_CODE = "polDctCode";
	public static final String PARAM_DISTRIBUTION_CHNL = "polDistributionChnl";
	
	public static final String QRY_RENEWAL_SEARCH_OBJ = " renPols";
	public static final String QRY_RENEWAL_SEARCH_BASE = "select renPols FROM VTrnRenewalPoliciesHtPas " + QRY_RENEWAL_SEARCH_OBJ;
	
	public static final String QRY_INSURED_OBJ = "insured";
	public static final String QRY_INSURED = ", " + "TMasInsured " + QRY_INSURED_OBJ;
	
	public static final String QRY_BASIC_CONDITION = QRY_PRT_WHERE + "renPols.polInsuredCode=insured.insInsuredCode ";
	
	public static final String QRY_RENEWAL_QUOTATION_SEARCH_OBJ = " renQuotations";
	
	public static final String QRY_RENEWAL_QUOTATONS_SEARCH_BASE = "select renQuotations FROM VTrnRenewalQuotesHtPas " + QRY_RENEWAL_QUOTATION_SEARCH_OBJ;
	
	public static final String PRINT_QRY_BASIC_CONDITION = QRY_PRT_WHERE + "renQuotations.polInsuredCode=insured.insInsuredCode ";
	
	public static final String QRY_GET_ALREADY_PRINTED = "select id.policyId from TTrnPrintBatchPas where prnStatus = 'Y'";
	
	private final static String GET_POLICY_ID_QUERY = "select SUBSTR(MAL_TRANSACTION_ID, 0, INSTR(MAL_TRANSACTION_ID, '|')-1) from TTrnMail "
														+"where SUBSTR(MAL_TRANSACTION_ID, 0, INSTR(MAL_TRANSACTION_ID, '|')-1) in (:idList) and MAL_STATUS = 1";
	
	private final static Short REN_POLICY_CODE = 2;
	
	private final static String GET_HOME_CLAIM_DETAILS = "SELECT int_claim_id,(SELECT MIN(DECODE(ind_rsk_code,1,1,2,prm_rt_code)) "
														+" FROM T_TRN_CLM_INTIMATION_DETAILS,T_TRN_PREMIUM WHERE ind_claim_id = int_claim_id "
														+" AND prm_rsk_id = ind_rsk_id AND prm_policy_id = int_policy_id AND prm_cl_code=2 AND prm_cov_code=1) rsk_code, "
														+"SUM(claim_amount) from ST_V_TRN_CLAIM_REPORT WHERE int_policy_id = ? GROUP BY int_claim_id,int_policy_id";

	
	private final static String GET_FRAUD_CLAIM = "select * from t_trn_clm_intimation where nvl(int_clm_fraud_flag,0) <> 0 and int_policy_id = :homePolicyId";
	
	private final static String IS_MEDICAL_CLAIM_POLICY = "select * from st_v_trn_gl_clm_total where col_code = 5 and int_policy_id = :travel_policy_id"; 
	
	private final static String GET_TRAVEL_CLAIM_DETAILS = "select sum(claim_amount) from st_v_trn_gl_clm_total where int_policy_id = :travel_policy_id"; 

	private final static String MAIL_SEQ = "SEQ_MAL_MAIL_ID";
	
	public static final String DEPLOYED_LOCATION="DEPLOYED_LOCATION";
	private final static String UPDATE_RENEWAL_QUOTE_STATUS_COMMON = "UPDATE_RENEWAL_QUOTE_STATUS_COMMON";
	//private final static String UPDATE_RENEWAL_STATUS_FOR_SOFT_STOP = "UPDATE_RENEWAL_STATUS_FOR_SOFT_STOP";
	
	@SuppressWarnings( { "unchecked", "rawtypes" })
	
	/** 
	 *  This method returns the list of policies to be renewed based on search criteria for Home and Travel (Phase 3) 
	 * 
	 */ 
	@Override
	public BaseVO getPoliciesToBeRenewed(BaseVO criteria) {
		
		LOGGER.debug("****getPoliciesToBeRenewed method in RenewalCommonDao****");		
		GenerateRenewalsSearchCriteriaVO renewalsVO = (GenerateRenewalsSearchCriteriaVO) criteria;
		Session session = null;
		List<VTrnRenewalPoliciesHtPas> renPolicies = null; // task
		List<RenewalResultsVO> policyList = new com.mindtree.ruc.cmn.utils.List<RenewalResultsVO>(RenewalResultsVO.class); //tasklist

		com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance(com.rsaame.pas.cmn.converter.IntegerShortConverter.class,"","");
		LOGGER.debug("****LOB = " + renewalsVO.getLob() + "****");		
		if(renewalsVO.getLob().equals(TRAVEL_CODE)) {			
			renewalsVO.setLob(Utils.getSingleValueAppConfig("TRAVEL_LONG_TERM_POLICY_TYPE")) ;
		}
		try {
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			StringBuffer queryString = new StringBuffer(128);
			// CALCULATE THE DATES FOR THE DAYS RANGE
			if(renewalsVO.getNoOfDays() != null) {
				Calendar cal = Calendar.getInstance();
				renewalsVO.setTransactionFrom(cal.getTime());
				LOGGER.debug("From Date :" + renewalsVO.getTransactionFrom());
				cal.add(Calendar.DATE, Integer.parseInt(renewalsVO.getNoOfDays()));
				renewalsVO.setTransactionTo(cal.getTime());
				LOGGER.debug("To Date :" + renewalsVO.getTransactionTo());
			}
			// ADD THE BASE QUERY
			queryString.append(QRY_RENEWAL_SEARCH_BASE);
			boolean appendWhere = true;
			LOGGER.debug("queryString _1" + queryString.toString());
			
			if(renewalsVO.getInsuredName() != null) {
				queryString.append(QRY_INSURED + QRY_BASIC_CONDITION);
				appendWhere = false;
			}
			Map paramsMap = new HashMap();

			if(renewalsVO.getClazz() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL + PARAM_CLASS_CODE);
				paramsMap.put(PARAM_CLASS_CODE, Short.parseShort(renewalsVO.getClazz()));
			}
			LOGGER.debug("queryString _2" + queryString.toString());
			if(renewalsVO.getPolicyNo() != null) {

				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_NUM + QRY_PRT_EQUL + PARAM_POL_NUM);
				paramsMap.put(PARAM_POL_NUM, Long.parseLong(renewalsVO.getPolicyNo()));

			}
			LOGGER.debug("queryString _3" + queryString.toString());
			if(renewalsVO.getTransactionFrom() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(TRUNCATE + QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_EXP_DATE  + CLOSE_BRACKET +  QRY_PRT_EQUL_OR_GREATER + PARAM_FROM_DATE);
				paramsMap.put(PARAM_FROM_DATE, renewalsVO.getTransactionFrom());

			}
			LOGGER.debug("queryString _4" + queryString.toString());
			if (renewalsVO.getTransactionTo() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(TRUNCATE + QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_EXP_DATE +  CLOSE_BRACKET + QRY_PRT_EQUL_OR_LESS + PARAM_POL_EXP_DATE);
				paramsMap.put(PARAM_POL_EXP_DATE, renewalsVO.getTransactionTo());

			}
			LOGGER.debug("queryString _5" + queryString.toString());
			// Release 4.0 Oman Changes passing location code in search query
			if(renewalsVO.getBranch() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + PARAM_LOCATION_CODE);
				paramsMap.put(PARAM_LOCATION_CODE, Integer.parseInt(renewalsVO.getBranch()));
			}			
			LOGGER.debug("queryString _6" + queryString.toString());
			if (renewalsVO.getBrokerName() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL + PARAM_BROKER_CODE);
				paramsMap.put(PARAM_BROKER_CODE, Short.parseShort(renewalsVO.getBrokerName()));

			}
			LOGGER.debug("queryString _7" + queryString.toString());
			if (renewalsVO.getScheme() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL + PARAM_SCHEME_CODE);
				paramsMap.put(PARAM_SCHEME_CODE, Integer.parseInt(renewalsVO.getScheme()));

			}
			LOGGER.debug("queryString _8" + queryString.toString());

			LOGGER.debug("queryString _9" + queryString.toString());
			if (renewalsVO.getInsuredName() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				String PARAM = PERCENTAGE + (renewalsVO.getInsuredName()).toUpperCase() + PERCENTAGE;
				queryString.append(TO_UPPER_CASE + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_FIRST_NAME + CLOSE_BRACKET + LIKE_OPERATOR + SINGLE_QUOTE + PARAM + SINGLE_QUOTE);

			}
			LOGGER.debug("queryString _10" + queryString.toString());
			if (renewalsVO.getAllDirect()) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DISTRIBUTION_CHNL + QRY_PRT_NOT_EQUL + BROKER_CHANNEL);

			}			
			if (renewalsVO.getLob() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_POLICY_TYPE + QRY_PRT_EQUL + PARAM_POL_POLICY_TYPE);
				paramsMap.put(PARAM_POL_POLICY_TYPE, Short.parseShort(renewalsVO.getLob()));
			}
			
			LOGGER.debug("queryString _11" + queryString.toString());
			
			LOGGER.debug("LOB is -->" + renewalsVO.getLob());			
			
			Query query = session.createQuery(queryString.toString());
			// ADD QUERY PARAMS
			Iterator iterParams = paramsMap.keySet().iterator();			
			while (iterParams.hasNext()) {
				String paramName = iterParams.next().toString();				
				query.setParameter(paramName, paramsMap.get(paramName));
				LOGGER.debug("SetParam ::" + paramName + "/" + paramsMap.get(paramName));
			}
			renPolicies = query.list();

		}
		catch(HibernateException hibernateException) {
			hibernateException.printStackTrace();
			throw new BusinessException("pas.renewal.exceptionInDataFetch", null, "Exception Occurred while fetching the data");
		}
		RenewalSearchSummaryVO renPolDetails = new RenewalSearchSummaryVO();

		for (VTrnRenewalPoliciesHtPas renewalPolVO : renPolicies) {
			if (!Utils.isEmpty(renewalPolVO)) {
				RenewalResultsVO renResults = new RenewalResultsVO();				
				renResults.setPolicyId(renewalPolVO.getId().getPolicyId());
				renResults.setPolLinkingId(0L);
				renResults.setEndtId(renewalPolVO.getPolEndtId());
				renResults.setEndtNo(renewalPolVO.getPolEndtNo());
				renResults.setConcatPolicyNo(renewalPolVO.getPolConcPolicyNo());
				renResults.setPolicyNo(renewalPolVO.getPolPolicyNo());
				renResults.setPolExpiryDate(renewalPolVO.getPolExpiryDate1());
				renResults.setPolEffectiveDate(renewalPolVO.getPolEffectiveDate1());				 
				String insuredName = (String)DAOUtils.getSqlResultSingleColumnPASFor( QueryConstants.GET_INSURED_NAME, renewalPolVO.getPolInsuredCode() ).get( 0 ); 
				renResults.setInsuredName( insuredName );
				if(!Utils.isEmpty(  renewalPolVO.getPolBrCode() )){
					renResults.setBrokerName( SvcUtils.getLookUpDescription( "BROKER_NAME", "ALL", "ALL", Integer.valueOf( renewalPolVO.getPolBrCode().toString() ) ) );
				}
				renResults.setBranchName( SvcUtils.getLookUpDescription( "LOCATION", "ALL", "ALL", renewalPolVO.getPolLocationCode() ) );
				renResults.setSchemaName( SvcUtils.getLookUpDescription( "SCHEME", "ALL", "ALL", renewalPolVO.getPolCoverNoteHour() ) );
				String classDesc = SvcUtils.getLookUpDescription("CLASS", "ALL", ( (UserProfile) ( renewalsVO ).getLoggedInUser() ).getRsaUser().getUserId().toString(), converter.getTypeOfA().cast(converter.getAFromB(renewalPolVO.getPolClassCode())));
				renResults.setClassCode(classDesc);	
				renResults.setPolicyYear(renewalPolVO.getPolPolicyYear());
				policyList.add(renResults);
				renPolDetails.setRenPolList(policyList);
			}
		}

		return renPolDetails;
	}
	
	@SuppressWarnings( { "unchecked", "rawtypes" })
	@Override
	/**
	 * This method returns the list of policies based on search criteria along with quotation numbers generated for Home and Travel (Phase 3) .
	 * 
	 */ 
	
	public BaseVO getRenewalQuotations(BaseVO criteria) {
		

		PrintRenewalsSearchCriteriaVO renewalsVO = (PrintRenewalsSearchCriteriaVO) criteria;
		Session session = null;
		List<VTrnRenewalQuotesHtPas> renPolicies = null; 
		
		com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter1 = ConverterFactory.getInstance(com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "");
		com.rsaame.pas.cmn.converter.ShortStringConverter converter2 = ConverterFactory.getInstance(com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "");
		com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter3 = ConverterFactory.getInstance(com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "");
		com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter5 = ConverterFactory.getInstance(com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class,"","");
		
		List<RenewalResultsVO> resultList = new com.mindtree.ruc.cmn.utils.List<RenewalResultsVO>(RenewalResultsVO.class); 		
		LOGGER.debug("****LOB = " + renewalsVO.getLob() + "****");		
		if(renewalsVO.getLob().equals(TRAVEL_CODE)) {			
			renewalsVO.setLob(Utils.getSingleValueAppConfig("TRAVEL_LONG_TERM_POLICY_TYPE")) ;
		}		
		try {
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			StringBuffer queryString = new StringBuffer(128);
			// CALCULATE THE DATES FOR THE DAYS RANGE
			// ADD THE BASE QUERY
			queryString.append(QRY_RENEWAL_QUOTATONS_SEARCH_BASE);
			boolean appendWhere = true;
			LOGGER.debug("queryString _12" + queryString.toString());
			if(renewalsVO.getInsuredName() != null) {
				queryString.append(QRY_INSURED + PRINT_QRY_BASIC_CONDITION);
				appendWhere = false;
			}
			Map paramsMap = new HashMap();

			if(renewalsVO.getClazz() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL + PARAM_CLASS_CODE);
				paramsMap.put(PARAM_CLASS_CODE, converter5.getTypeOfA().cast(converter5.getAFromB(renewalsVO.getClazz())));
			}
			LOGGER.debug("queryString _13" + queryString.toString());
			if(renewalsVO.getPolicyNo() != null) {

				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_NUM + QRY_PRT_EQUL + PARAM_POL_NUM);
				paramsMap.put(PARAM_POL_NUM, converter5.getTypeOfA().cast(converter5.getAFromB(renewalsVO.getPolicyNo())));

			}
			LOGGER.debug("queryString _14" + queryString.toString());
			if(renewalsVO.getTransactionFrom() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_GREATER + PARAM_FROM_DATE);
				paramsMap.put(PARAM_FROM_DATE, renewalsVO.getTransactionFrom());

			}
			LOGGER.debug("queryString _15" + queryString.toString());
			if(renewalsVO.getTransactionTo() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_LESS + PARAM_POL_EXP_DATE);
				paramsMap.put(PARAM_POL_EXP_DATE, renewalsVO.getTransactionTo());

			}
			LOGGER.debug("queryString _16" + queryString.toString());
			// Release 4.0 Oman Changes passing location code in search query
			if(renewalsVO.getBranch() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + PARAM_LOCATION_CODE);
				paramsMap.put(PARAM_LOCATION_CODE, converter5.getTypeOfA().cast(converter5.getAFromB(renewalsVO.getBranch())));
			}
			// Release 4.0 Oman Changes
			LOGGER.debug("queryString _17" + queryString.toString());
			if(renewalsVO.getBrokerName() != null) {

				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL + PARAM_BROKER_CODE);
				paramsMap.put(PARAM_BROKER_CODE, converter5.getTypeOfA().cast(converter5.getAFromB(renewalsVO.getBrokerName())));

			}
			LOGGER.debug("queryString _18" + queryString.toString());
			if(renewalsVO.getScheme() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL + PARAM_SCHEME_CODE);
				paramsMap.put(PARAM_SCHEME_CODE, converter5.getTypeOfA().cast(converter5.getAFromB(renewalsVO.getScheme())));

			}
			LOGGER.debug("queryString _19" + queryString.toString());

			LOGGER.debug("queryString _20" + queryString.toString());
			if(renewalsVO.getInsuredName() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				String param = PERCENTAGE + (renewalsVO.getInsuredName()).toUpperCase() + PERCENTAGE;
				queryString.append(TO_UPPER_CASE + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_FIRST_NAME + CLOSE_BRACKET + LIKE_OPERATOR + SINGLE_QUOTE + param + SINGLE_QUOTE);

			}
			LOGGER.debug("queryString _21" + queryString.toString());
			if(renewalsVO.getAllDirect()) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DISTRIBUTION_CHNL + QRY_PRT_NOT_EQUL + BROKER_CHANNEL);

			}
			LOGGER.debug("queryString _22" + queryString.toString());
			if(renewalsVO.getWithEmailID()) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_EMAIL_ID + QRY_PRT_IS + QRY_PRT_NOT  + QRY_PRT_NULL);

			}
			LOGGER.debug("queryString _23" + queryString.toString());
			if(renewalsVO.getNotYetPrinted()) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POLICY_ID + QRY_PRT_NOT_IN + OPEN_BRACKET +  QRY_GET_ALREADY_PRINTED  + CLOSE_BRACKET);
			}

			LOGGER.debug("queryString _24" + queryString.toString());
			
			if(renewalsVO.getLob() != null) {
				queryString.append(appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND);
				appendWhere = false;				
				queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_POLICY_TYPE + QRY_PRT_EQUL + PARAM_POL_POLICY_TYPE);
				paramsMap.put(PARAM_POL_POLICY_TYPE, converter5.getTypeOfA().cast(converter5.getAFromB(renewalsVO.getLob())));			
			}
			
			LOGGER.debug("queryString _25" + queryString.toString());
			/* Search based on policy status starts */
			if (null != renewalsVO.getStatusIdList()) {
				boolean isStatusSelected = true;
				String statusList[] = renewalsVO.getStatusIdList();
				for (String status : statusList) {
					if ("99999".equals(status)) {
						isStatusSelected = false;
					}
				}
				if (isStatusSelected) {
					if (!Utils.isEmpty(renewalsVO.getStatusIdList())){		/* Added 'Arrays.toString' in logger statement and null check condition - sonar violation fix */
					LOGGER.debug("Status Id is : "+ Arrays.toString(renewalsVO.getStatusIdList()));
					}
					queryString.append(appendWhere ? QRY_PRT_WHERE: QRY_PRT_AND);
					appendWhere = false;
					queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ+ QRY_PRT_DOT + PARAM_POL_POLICY_STATUS+ QRY_PRT_IN + PARAM_POL_POLICY_STATUS
							+ CLOSE_BRACKET);
					// queryString.append(" statusId := " +
					// PARAM_POL_POLICY_STATUS);
					paramsMap.put(PARAM_POL_POLICY_STATUS,renewalsVO.getStatusIdList());
				}
				
			}
			/*Search based on policy status ends here*/
			
			LOGGER.debug("queryString _26" + queryString.toString());
			
			Query query = session.createQuery(queryString.toString());
			// ADD QUERY PARAMS			
			Iterator iterParams = paramsMap.keySet().iterator();
			while(iterParams.hasNext()) {
				String paramName = iterParams.next().toString();
				if(PARAM_POL_POLICY_STATUS.equals(paramName)){
					query.setParameterList(PARAM_POL_POLICY_STATUS,renewalsVO.getStatusIdList());
				}else{
					query.setParameter(paramName, paramsMap.get(paramName));
				}
				LOGGER.debug("SetParam ---::" + paramName + "/" + paramsMap.get(paramName));
			}
			LOGGER.debug("queryString _27" + queryString.toString());
			renPolicies = query.list();

		}
		catch(HibernateException hibernateException) {
			throw new BusinessException("pas.renewal.exceptionInDataFetch", null, "Exception Occurred while fetching the data");
		}
		RenewalSearchSummaryVO renPolDetails = new RenewalSearchSummaryVO();
		
		for(VTrnRenewalQuotesHtPas renewalPolVO : renPolicies) {
			if(!Utils.isEmpty(renewalPolVO)) {
				RenewalResultsVO renResults = new RenewalResultsVO();
				
				renResults.setPolicyId(renewalPolVO.getId().getPolPolicyId());
				renResults.setPolLinkingId(0L);
				renResults.setEndtId(converter1.getTypeOfB().cast(converter1.getBFromA(renewalPolVO.getId().getPolEndtId())));				
				renResults.setEndtNo(converter1.getTypeOfB().cast(converter1.getBFromA(renewalPolVO.getPolEndtNo())));
				renResults.setConcatPolicyNo(renewalPolVO.getPolConcPolicyNo());			
				renResults.setPolicyNo(converter1.getTypeOfB().cast(converter1.getBFromA(renewalPolVO.getPolPolicyNo()))) ;				
				renResults.setRenQuoteNo(converter1.getTypeOfB().cast(converter1.getBFromA(renewalPolVO.getPolQuotationNo()))); 
				renResults.setPolEffectiveDate(renewalPolVO.getPolEffectiveDate1());
				renResults.setPolExpiryDate(renewalPolVO.getPolExpiryDate1());
				String classDesc = SvcUtils.getLookUpDescription("CLASS", "ALL", ( (UserProfile) ( renewalsVO ).getLoggedInUser() ).getRsaUser().getUserId().toString(), converter3.getTypeOfB().cast(converter3.getBFromA(renewalPolVO.getPolClassCode())));
				renResults.setClassCode(classDesc);
				renResults.setPolicyYear(converter2.getTypeOfA().cast(converter2.getAFromB(converter5.getTypeOfB().cast(converter5.getBFromA(renewalPolVO.getPolPolicyYear())))));
				renResults.setPolValidityStartDate(renewalPolVO.getPolValidityStartDate());
				renResults.setEmailId(renewalPolVO.getCshEEmailId());				
				renResults.setPolLocCode(converter3.getTypeOfB().cast(converter3.getBFromA(renewalPolVO.getPolLocationCode())));
				String insuredName = (String)DAOUtils.getSqlResultSingleColumnPASFor( QueryConstants.GET_INSURED_NAME, renewalPolVO.getPolInsuredCode() ).get( 0 ); 
				renResults.setInsuredName( insuredName );
				renResults.setStatusDescripton(SvcUtils.getLookUpDescription("STATUS", "ALL", "ALL", Integer.valueOf(renewalPolVO.getPolStatusId())));
				resultList.add(renResults);
				renPolDetails.setRenPolList(resultList);
			}
		}

		return renPolDetails;
	
	}
	
	/**
	 * Method being invoked after clicking on the 'Generate online Renewal' button in the Generate Renewals section.
	 * It  generates the renewal quotation by calling stored procedure and returns the Renewal Quote Number
	 * Also find the policy details for  that renewal quotation by querying T_TRN_POLICY_QUO
	 * 
	 */ 
	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public BaseVO generateOnlineRenewal(BaseVO baseVO) {		
		 
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] renData = holderVO.getData();
		Long policyId = (Long)renData[0];
		Integer userID = (Integer)renData[1]; 
		PASStoredProcedure sp = null;
		
		String sql = com.Constant.CONST_SELECT_FROM_T_TRN_POLICY_WHERE_POL_POLICY_ID_POLICYID;		
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);	

		query.addEntity(TTrnPolicyQuo.class);
		query.setParameter(com.Constant.CONST_POLICYID, policyId);
		List result = query.list();
		LOGGER.debug("results size =_1" + result.size());
		TTrnPolicyQuo tTrnPolicy = (TTrnPolicyQuo) result.get(0);
		LOGGER.debug("policy =_1" + tTrnPolicy);		

		short policyType = tTrnPolicy.getPolPolicyType();
		if(policyType == Short.parseShort(SvcConstants.HOME_POL_TYPE)) {		
			sp =(PASStoredProcedure) Utils.getBean("generateRenewalQuoteForHome");
		}
		else {
			sp =(PASStoredProcedure) Utils.getBean("generateRenewalQuoteForTravel");
		}

		Object quoteDetails[] = new Object[16];
		DataHolderVO<Object []> renewalData = new DataHolderVO<Object[]>();
		Long renquoteNo = null;
		try
		 {
			Map results = sp.call(policyId ,userID);
			renquoteNo = Long.valueOf(results.get("PO_QUOTE_NO").toString());
		} catch (DataAccessException e) {
			throw new BusinessException("cmn.storedproc.error", e, "Unexpected exception occurred. Please contact Administrator_1");
		}
		List<TTrnPolicyQuo> tTrnPolicyQuo =  getHibernateTemplate().find("from TTrnPolicyQuo where polQuotationNo= ? and polIssueHour =?" , renquoteNo,SvcConstants.POL_ISSUE_HOUR);
		quoteDetails[0] = renquoteNo;
		if(!Utils.isEmpty(tTrnPolicyQuo) && tTrnPolicyQuo.size() > 0) {
			quoteDetails[1] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getId().getPolicyId();
			quoteDetails[2] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolLocationCode());
			quoteDetails[3] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolStatus());
			quoteDetails[4] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolEffectiveDate();
			quoteDetails[5] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolEndtNo();
			quoteDetails[6] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolDocumentCode());
			quoteDetails[7] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolValidityStartDate();
			quoteDetails[8] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolPolicyType().toString();
			quoteDetails[9] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolUserId());
			quoteDetails[10] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolPolicyNo();
			quoteDetails[11] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolConcPolicyNo();			
			quoteDetails[12] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolVatRegNo();
			quoteDetails[13] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolVatTaxPerc();
			quoteDetails[14] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolVatTax();
			quoteDetails[15] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolvatCode();
			
		}else {
			throw new BusinessException("", null, "No record quotation record found for renewed policy.");
		}
		renewalData.setData(quoteDetails);		
		return renewalData;		
	
	}

	/**
	 * Method being invoked after clicking on the 'Generate Renewals' button in the Generate Renewals section.
	 * This method saves the records in the batch table for generating renewals in batch mode       
	 * 
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public void savePoliciesForBatchRenewal(BaseVO baseVO) {
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		TTrnRenewalBatchEplatform[] polForRenewal = (TTrnRenewalBatchEplatform[]) data[ 0 ];
		for(TTrnRenewalBatchEplatform renewalPolicy : polForRenewal) {
			//renewalPolicy.setRequesterId(ServiceContext.getUser().getUserId());
			renewalPolicy.setRequesterId( SvcConstants.MISLIVE_USER );
			renewalPolicy.setRequestDate(new Date());
			if(Utils.isEmpty(renewalPolicy.getRenQuotationStatus())) {
				renewalPolicy.setRenQuotationStatus("N");			
			}
			if(Utils.isEmpty(renewalPolicy.getLastExecutedStep())) {
				renewalPolicy.setLastExecutedStep(Short.valueOf(String.valueOf(SvcConstants.zeroVal)))	;	
			}
			LOGGER.debug("renewal Policy = " + renewalPolicy);
			getHibernateTemplate().save(renewalPolicy);
		}
		DAOUtils.sendMailForBatchSubmit(null);
		
	}	

	
	/**
	 * This method is being invoked to count if any claim exists
	 * It calls the stored procedure to get the claim count
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public BaseVO getClaimCount(BaseVO baseVO) {
		
		@SuppressWarnings("unchecked")
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] claimData = holderVO.getData();		
		Long policyId = (Long) claimData[ 0 ];
		Long renQuote = (Long) claimData[ 1 ];
		LOB lob = (LOB) claimData[ 2 ];
		PASStoredProcedure sp = null;
		// While converting the renewal quote to policy we have to get the policy id of original policy using the renewal quote number
		if(!Utils.isEmpty(renQuote)) {
			String policyIdQuery = "select pol_ref_policy_id from t_trn_policy_quo where pol_quotation_no = " + renQuote + " and pol_issue_hour =" + SvcConstants.POL_ISSUE_HOUR + " and pol_endt_id ="+SvcConstants.zeroVal;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();			
			Query query = session.createSQLQuery(policyIdQuery);
			policyId = Long.valueOf(query.uniqueResult().toString());
		}
		if(lob.equals(LOB.HOME) || lob.equals(LOB.TRAVEL)){
			sp = (PASStoredProcedure) Utils.getBean("getClaimCountCommon");
		}
		else{
			sp = (PASStoredProcedure) Utils.getBean("getClaimCountMonoline");
		}
		Long claimCount = null;
		try {			
			Map results = sp.call(policyId, renQuote);			
			claimCount = Long.valueOf(results.get("PO_CLAIM_COUNT").toString());
		}
		catch(DataAccessException e) {
			throw new BusinessException("cmn.storedproc.error", e, "Unexpected exception occurred. Please contact Administrator_2");
		}
		DataHolderVO<Long> claimOutput = new DataHolderVO<Long>();
		claimOutput.setData(claimCount);
		return claimOutput;		
	}

	/**
	 * It checks if policy has been sent for reprint     
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaseVO checkForReprint(BaseVO baseVO) {
		DataHolderVO<Object> holderVO = (DataHolderVO<Object>) baseVO;		
		Long[] policyIdList = (Long[]) holderVO.getData();	
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
		"from TTrnPrintBatchPas batch where batch.id.policyId in (:IdList) and batch.prnStatus = 'Y' ");
		query.setParameterList("IdList", policyIdList, new LongType());
		List<TTrnPrintBatchPas> printList = query.list();
		DataHolderVO<Boolean> prnStatus = new DataHolderVO<Boolean>();
		if(!Utils.isEmpty(printList)) {
			prnStatus.setData(Boolean.TRUE) ;
		} else {
			prnStatus.setData(Boolean.FALSE) ;
		}
		return prnStatus;
	}

	/**
	 * Method being invoked after clicking on the 'Print Renewals' button in the Print Renewals notice section.
	 * It saves the all selected policies into TTrnPrintBatchPas table
	 * 
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public void savePoliciesForBatchPrint(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		Long[] policyIdList = (Long[]) data[1];
		TTrnPrintBatchPas[] quoteForPrint = (TTrnPrintBatchPas[]) data[0];
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
		"select batch.id.policyId from TTrnPrintBatchPas batch where batch.id.policyId in (:IdList) and batch.prnStatus = 'N'");		
		query.setParameterList("IdList", policyIdList, new LongType());
		
		List<Long> printList = query.list();
		
		long printBatchId = NextSequenceValue.getNextSequence(PRINT_BATCH_ID, null,null, getHibernateTemplate());
		for (TTrnPrintBatchPas quote : quoteForPrint) {
			if(!printList.contains(quote.getId().getPolicyId())) {
			quote.getId().setPrnBatchReqId(printBatchId);
			quote.setRequesterId(ServiceContext.getUser().getUserId());
			quote.setRequestDate(new Date());
			quote.setPrnStatus("N");	
			quote.setPrnLocation(String.valueOf(Math.round(SvcUtils.getLookUpCodeForLOneLTwo(PRINT_LOC,quote.getPrnLocation(), "ALL"))));
			//quote.setPrnLocation(SvcUtils.getLookUpCodeForLOneLTwo(PRINT_LOC,quote.getPrnLocation(), "ALL").toString());
			getHibernateTemplate().save(quote);
			}
		}
	}
	
	/**
	 * This method updates the policy status as Soft Stop, in case of claims
	 */

	@SuppressWarnings("unchecked")
	@Override
	public void updateQuotationStatus(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		PolicyDataVO policyVO = (PolicyDataVO)data[0];
		String status = (String)data[1]; 		
		List<TTrnPolicyQuo> policyQuoList = getHibernateTemplate().find("from TTrnPolicyQuo ttrnPol where ttrnPol.id.polPolicyId=? and ttrnPol.id.polEndtId=?",
				policyVO.getPolicyId(), policyVO.getCommonVO().getEndtId());
		if(!policyQuoList.isEmpty()) {
			for(TTrnPolicyQuo policyQuo : policyQuoList) {
				policyQuo.setPolStatus(Byte.valueOf(status));
				if(!Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getCommonVO() ) && !Utils.isEmpty( policyVO.getCommonVO().getVsd() )){
					policyQuo.setValidityStartDate( policyVO.getCommonVO().getVsd() );
				}
				getHibernateTemplate().merge(policyQuo);
			}
		}
		
	}
	
public void updateRenewalTerm(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		PolicyDataVO policyVO = (PolicyDataVO)data[0];
		String renewalTerm = (String)data[1]; 
		Long polref= policyVO.getAuthenticationInfoVO().getRefPolicyId();
		Long polno= policyVO.getAuthenticationInfoVO().getRefPolicyNo();
		List<TTrnPolicyQuo> policyQuoList = getHibernateTemplate().find("from TTrnPolicyQuo ttrnPol where ttrnPol.polRefPolicyId=? and ttrnPol.polRefPolicyNo=?  and ttrnPol.id.polEndtId=?",
				polref,polno, policyVO.getCommonVO().getEndtId());
		if(!policyQuoList.isEmpty()) {
			for(TTrnPolicyQuo policyQuo : policyQuoList) {
				policyQuo.setPolRenTermTxt(renewalTerm);
				if(!Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getCommonVO() ) && !Utils.isEmpty( policyVO.getCommonVO().getVsd() )){
					policyQuo.setValidityStartDate( policyVO.getCommonVO().getVsd() );
				}
				getHibernateTemplate().merge(policyQuo);
			}
		}
		
	}
	/*
	 * This method updates the prm table status for the travel soft stop quote to prm_status = 6
	 */
	public void updatePrmStatusForSoftStop(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		PolicyDataVO policyVO = (PolicyDataVO)data[0];
		String status = "6"; 		
		//and ttrnPrm.id.polEndtId=?"
		List<TTrnPremiumQuo> prmQuoList = getHibernateTemplate().find("from TTrnPremiumQuo ttrnPrm where ttrnPrm.id.prmPolicyId=? ",
				policyVO.getPolicyId());
		if(!prmQuoList.isEmpty()) {
			for(TTrnPremiumQuo prmQuo : prmQuoList) {
				prmQuo.setPrmStatus(Byte.valueOf(status));
				/*if(!Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getCommonVO() ) && !Utils.isEmpty( policyVO.getCommonVO().getVsd() )){
					prmQuo.setValidityStartDate( policyVO.getCommonVO().getVsd() );
				}*/
				getHibernateTemplate().merge(prmQuo);
			}
		}
		
	}
	
public void updateGprStatusForSoftStop(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		PolicyDataVO policyVO = (PolicyDataVO)data[0];
		String status = "6"; 		
		//and ttrnPrm.id.polEndtId=?"
		List<TTrnGaccPersonQuo> gprQuoList = getHibernateTemplate().find("from TTrnGaccPersonQuo ttrnGpr where ttrnGpr.gprPolicyId=? ",
				policyVO.getPolicyId());
		if(!gprQuoList.isEmpty()) {
			for(TTrnGaccPersonQuo gprQuo : gprQuoList) {
				gprQuo.setStatus(Byte.valueOf(status));
				/*if(!Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getCommonVO() ) && !Utils.isEmpty( policyVO.getCommonVO().getVsd() )){
					prmQuo.setValidityStartDate( policyVO.getCommonVO().getVsd() );
				}*/
				getHibernateTemplate().merge(gprQuo);
			}
		}
		
	}

	/**
	 * It checks if renewal notice has got mailed for some or all of the policies    
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaseVO checkForResendMail(BaseVO baseVO) {
		DataHolderVO<Object> holderVO = (DataHolderVO<Object>) baseVO;		
		Long[] policyIdList = (Long[]) holderVO.getData();
		DataHolderVO<Boolean> sendStatus = new DataHolderVO<Boolean>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		new ArrayList<Long>();
		Query query = session.createQuery(GET_POLICY_ID_QUERY);
		
		query.setParameterList("idList", policyIdList,new LongType());		
		List<String> mailIdList = query.list();		
		if(!Utils.isEmpty(mailIdList)) {
			sendStatus.setData(Boolean.TRUE) ;			
		} else {
			sendStatus.setData(Boolean.FALSE) ;			
		}		
		return sendStatus;		
	}
	
	
	/**
	 * It returns the renewal Quotations for which email has not been sent and takes the list of renewal quotations as the argument
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public BaseVO getRenewalNoticeNotSent(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		DataHolderVO<Object> output = new DataHolderVO<Object>();
		Object[] data = holderVO.getData();
		Long[] policyIdList = (Long[]) data[1];
		RenewalResultsVO[] quoteForMail = (RenewalResultsVO[]) data[0];
		List<RenewalResultsVO> noticeList = new ArrayList<RenewalResultsVO>();
		Arrays.asList(quoteForMail);		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		new ArrayList<Long>();
		Query query = session.createQuery(GET_POLICY_ID_QUERY);
		
		query.setParameterList("idList", policyIdList,new LongType());
		List<String> mailIdList = query.list();
		
		for(int i=0;i<quoteForMail.length;i++) {
			if(!mailIdList.contains(quoteForMail[i].getPolicyId().toString())) {
				noticeList.add(quoteForMail[i]);
			}
		}		
		quoteForMail = noticeList.toArray(new RenewalResultsVO[ noticeList.size() ]);
		output.setData(quoteForMail);
		return output;
	}

	
	/**
	 * It saves/updates the Renewal notice into the table T_TRN_MAIL after sending, 
	 * If renewal notice is already in the table, then it updates it's status
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveRenewalNotice(BaseVO baseVO) {
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;	
		//TTrnMail[] tTrnMailList = (TTrnMail[]) holderVO.getData();

		Object[] data = holderVO.getData();
		List<String> transIdList = (List<String>) data[1];
		List<TTrnMail> tTrnMailList = (List<TTrnMail>) data[0];
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();		
		Query query = session.createQuery("from TTrnMail batch where batch.transactionId in (:transIdList) ");
		
		//Query query = session.createQuery("select mailId,mailType,recipientMailId,transactionId,status,sentDate,coalesce(malClassCode,0) from TTrnMail batch where batch.transactionId in (:transIdList) ");
		query.setParameterList("transIdList", transIdList);		
		List<TTrnMail> trnMailList = query.list();			
		
		for (TTrnMail mail : tTrnMailList) {
			for(TTrnMail trnMail : trnMailList) {			
				if(trnMail.getTransactionId().equals(mail.getTransactionId())) {					
					mail.setMailId(trnMail.getMailId());
					System.out.println("mail id_1"+trnMail.getMailId() +" transaction_1  "+trnMail.getTransactionId());
					getHibernateTemplate().merge(mail);
					break;
				}
			}
			if(Utils.isEmpty(mail.getMailId())) {	
				System.out.println("mail id_2"+mail.getMailId() +" transaction_2  "+mail.getTransactionId());
				mail.setMailId(NextSequenceValue.getNextSequence( MAIL_SEQ,null,null, getHibernateTemplate())) ;
				getHibernateTemplate().save(mail);
				System.out.println("mail id_3"+mail.getMailId() +" transaction_3  "+mail.getTransactionId());
			}
		}	
	}
	
	/**
	 * It inserts a record in the T_TRN_SMS table after issuing the  renewal quote
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sendRenewalMessage (BaseVO baseVO) {
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();		
		TTrnSms tTrnSms =  (TTrnSms) data[0];
		CommonVO commonVO = (CommonVO) data[1];
		TMasSms tMasSms = null;

		String messageId = Utils.getSingleValueAppConfig( commonVO.getLob().toString().toUpperCase() + "_Renewal_SMS_ID");
		List<Object> result = getHibernateTemplate().find("from TMasSms sms where sms.smsID = " + messageId + " and sms.smsStatus = 1" );
		if(result.size() > 0){
			tMasSms = (TMasSms) result.get(0);
			//		}

			tTrnSms.setTrnSmsId(NextSequenceValue.getNextSequence("SEQ_SMS_ID", null, null, getHibernateTemplate()));
			Boolean levelCheck =true;
			if( !Utils.isEmpty( tTrnSms.getTrnSmsRemarks() ) && tTrnSms.getTrnSmsRemarks().equals( "sendSMS" ) ){
				/*if( Utils.isEmpty( tMasSms ) || tMasSms.getSmsStatus().intValue() == 0  ){
				throw new SystemException( "Master not configured for the Manual SMS", null, "Master not configured for the Manual SMS" );
			}*/
				if( Utils.isEmpty( tTrnSms.getTrnSmsLevel() ) ){
					tTrnSms.setTrnSmsLevel( (byte) 1 );
				}
				else if( !Utils.isEmpty( tMasSms ) && tTrnSms.getTrnSmsLevel() < tMasSms.getSmsLevel() ){
					tTrnSms.setTrnSmsLevel( (byte) ( tTrnSms.getTrnSmsLevel() + 1 ) );
				}
				else{
					//levelCheck = false;
					throw new SystemException( "Maximum SMS Level Reached", null, "Maximum Level Reached" );
				}
			}
			if( tTrnSms.getTrnSmsMode().equals( "A" ) ){
				tTrnSms.setTrnSmsAText( tMasSms.getSmsArabicBody() );
				tTrnSms.setTrnSmsEText( tMasSms.getSmsEngBody() );
				tTrnSms.setTrnSmsRemarks( "Renewal SMS sent" );
			}
			else{
				if(tTrnSms.getTrnSmsLangType().equals( "E" )){
					tTrnSms.setTrnSmsEText( tMasSms.getSmsEngBody() );
					tTrnSms.setTrnSmsAText( null );
				}
				else if(tTrnSms.getTrnSmsLangType().equals( "A" )){
					tTrnSms.setTrnSmsAText( tMasSms.getSmsArabicBody() );
					tTrnSms.setTrnSmsEText( null );
				}
				tTrnSms.setTrnSmsRemarks( "Renewal SMS submitted" );
			}
			//tTrnSms.setTrnSmsSubDate(trnSmsSubDate);	
			if( levelCheck ){
				getHibernateTemplate().save(tTrnSms);
			}
		}
		/*else if( Utils.isEmpty( tMasSms ) 
				|| ( !Utils.isEmpty( tMasSms ) && !Utils.isEmpty( tMasSms.getSmsStatus() ) && tMasSms.getSmsStatus().intValue() == 0 ) ){
			throw new BusinessException( "sms.details.missing", null, "T_MAS_SMS not configured for the Manual SMS" );
		}*/
	}
	
	
	/**
	 * This method is being invoked to count if any claim exists and the total claim count also for Home Policy
	 * 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public BaseVO getClaimDetails(BaseVO baseVO) {
		
		@SuppressWarnings("unchecked")
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] claimData = holderVO.getData();		
		DataHolderVO<Object> claimOutput = new DataHolderVO<Object>();
		//Object claimOutputData[] = new Object[2];
		//Double totalClaim = 0.0;
		Long policyId = (Long) claimData[ 0 ];
		Long renQuote = (Long) claimData[ 1 ];		
		// While converting the renewal quote to policy we have to get the policy id of original policy using the renewal quote number
		if(!Utils.isEmpty(renQuote)) {
			String policyIdQuery = "select pol_ref_policy_id from t_trn_policy_quo where pol_quotation_no = " + renQuote + " and pol_issue_hour =" + SvcConstants.POL_ISSUE_HOUR + " and pol_endt_id ="+SvcConstants.zeroVal;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();			
			Query query = session.createSQLQuery(policyIdQuery);
			policyId = Long.valueOf(query.uniqueResult().toString());
		}		
		//Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery( GET_HOME_CLAIM_DETAILS );
		List<Object[]> claimsList = DAOUtils.getSqlResult( GET_HOME_CLAIM_DETAILS, policyId );
		//query.setParameter( "homePolicyId",policyId );
		//List<Object[]> claimsList = query.list();		
		claimOutput.setData( claimsList );
		return claimOutput;		
	}
	
	/**
	 * This method checks if there is any fraud for Home Policy during generating the renewal quote
	 * 
	 */
	@Override
	public BaseVO getFraudClaim(BaseVO baseVO){
		
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;		
		Long policyId = holderVO.getData();
		DataHolderVO<Boolean> fraudClaimStatus = new DataHolderVO<Boolean>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();		
		Query query = session.createSQLQuery(GET_FRAUD_CLAIM);
		//query.setParameter( 1, policyId );
		query.setParameter("homePolicyId", policyId);		
		List<Object[]> fraudClaimList = query.list();		
		if(!Utils.isEmpty(fraudClaimList)) {
			fraudClaimStatus.setData(Boolean.TRUE) ;			
		} else {
			fraudClaimStatus.setData(Boolean.FALSE) ;			
		}		
		return fraudClaimStatus;
		
	}
	
	/**
	 * This method checks if travel policy has medical claim
	 * 
	 */
	@Override
	public BaseVO isMedicalClaimPolicy(Long policyId){		
		DataHolderVO<Boolean> medicalClaimStatus = new DataHolderVO<Boolean>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();		
		Query query = session.createSQLQuery(IS_MEDICAL_CLAIM_POLICY);		
		query.setParameter("travel_policy_id", policyId);		
		List<Object[]> medicalClaimList = query.list();		
		if(!Utils.isEmpty(medicalClaimList)) {
			medicalClaimStatus.setData(Boolean.TRUE) ;			
		} else {
			medicalClaimStatus.setData(Boolean.FALSE) ;			
		}		
		return medicalClaimStatus;		
	}
	
	/**
	 * This method returns the claim amount for travel Non-Medical claim
	 * 
	 */
	@Override
	public BaseVO getTravelClaimAmount(Long policyId){
		DataHolderVO<Double> claimAmount = new DataHolderVO<Double>();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Double claimsAmt = 0.0;
		Query query = session.createSQLQuery( GET_TRAVEL_CLAIM_DETAILS );
		query.setParameter( "travel_policy_id", policyId );
		Object result = query.uniqueResult();		
		if( !Utils.isEmpty( result) ){
			claimsAmt = Double.valueOf( result.toString());
		}
		claimAmount.setData( claimsAmt );
		return claimAmount;				
	}
	
	/**
	 * This method checks if a policy is the renewal policy
	 * 
	 */
	@Override
	public BaseVO isRenewalPolicyForHomeAndTravel(BaseVO baseVO) {
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long policyId = holderVO.getData();
		DataHolderVO<Boolean> renPolicy = new DataHolderVO<Boolean>();
		boolean isRenPolicy = false;
		String sql = com.Constant.CONST_SELECT_FROM_T_TRN_POLICY_WHERE_POL_POLICY_ID_POLICYID;
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);	
		query.addEntity(TTrnPolicyQuo.class);
		query.setParameter(com.Constant.CONST_POLICYID, policyId);
		List result = query.list();
		if(!Utils.isEmpty(result) && result.size() > 0) {
			LOGGER.debug("results size =_2" + result.size());
			TTrnPolicyQuo policy = (TTrnPolicyQuo) result.get(0);
			LOGGER.debug("policy =_2" + policy);
			if( policy.getPolDocumentCode() == REN_POLICY_CODE ){
				isRenPolicy = true;
			}		
		}		
		renPolicy.setData( isRenPolicy  );
		return renPolicy;
	}
	
	//added for monoline base- renewal implementation
	/**
	 * Method being invoked after clicking on the 'Generate online Renewal' button in the Generate Renewals section.
	 * It  generates the renewal quotation by calling stored procedure and returns the Renewal Quote Number
	 * Also find the policy details for  that renewal quotation by querying T_TRN_POLICY_QUO
	 * 
	 */ 
	@SuppressWarnings( { "rawtypes", "unchecked" })
	@Override
	public BaseVO generateOnlineRenewalMonoline(BaseVO baseVO) {		
		 
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] renData = holderVO.getData();
		Long policyId = (Long)renData[0];
		Integer userID = (Integer)renData[1]; 
		PASStoredProcedure sp = null;
		
		String sql = com.Constant.CONST_SELECT_FROM_T_TRN_POLICY_WHERE_POL_POLICY_ID_POLICYID;		
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);	

		query.addEntity(TTrnPolicyQuo.class);
		query.setParameter(com.Constant.CONST_POLICYID, policyId);
		List result = query.list();
		LOGGER.debug("results size =_3" + result.size());
		TTrnPolicyQuo tTrnPolicy = (TTrnPolicyQuo) result.get(0);
		LOGGER.debug("policy =_3" + tTrnPolicy);		

		sp =(PASStoredProcedure) Utils.getBean("generateRenewalQuoteMonoline");
	
		HashMap<String,Object> commonVoMap = new HashMap<String,Object>();
		
		DataHolderVO<HashMap<String, Object>> renewalData = new DataHolderVO<HashMap<String, Object>>();
		Long renquoteNo = null;
		try
		 {
			Map results = sp.call(policyId ,userID);
			renquoteNo = Long.valueOf(results.get("PO_QUOTE_NO").toString());
		} catch (DataAccessException e) {
			throw new BusinessException("cmn.storedproc.error", e, "Unexpected exception occurred. Please contact Administrator_3");
		}
		List<TTrnPolicyQuo> tTrnPolicyQuo =  getHibernateTemplate().find("from TTrnPolicyQuo where polQuotationNo= ? and polIssueHour =? and polPolicyType=1" , renquoteNo,SvcConstants.POL_ISSUE_HOUR);
		commonVoMap.put( "renewalQuoteNo",  renquoteNo );
		if(!Utils.isEmpty(tTrnPolicyQuo) && tTrnPolicyQuo.size() > 0) {	
			
			commonVoMap.put( com.Constant.CONST_POLICYID, tTrnPolicyQuo.get(SvcConstants.zeroVal).getId().getPolicyId() );
			commonVoMap.put( "locCode", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolLocationCode() );
			commonVoMap.put( "polStatus", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolStatus() );
			commonVoMap.put( "effectiveDate", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolEffectiveDate() );
			commonVoMap.put( "EndtNo", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolEndtNo() );
			commonVoMap.put( "polDocCode", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolDocumentCode() );
			commonVoMap.put( "polVSD", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolValidityStartDate() );
			commonVoMap.put( "policyType", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolPolicyType() );
			commonVoMap.put( "polUserId", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolUserId() );
			commonVoMap.put( "PolicyNo", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolPolicyNo() );
			commonVoMap.put( "cnocPolicyNo", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolConcPolicyNo() );
			commonVoMap.put( "polVatRegNo", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolVatRegNo());
			commonVoMap.put( "polVatTaxPerc", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolVatTaxPerc() );
			commonVoMap.put( "polVatTax", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolVatTax() );
			commonVoMap.put( "polvatCode", tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolvatCode() );
			
		}else {
			throw new BusinessException("", null, "No record quotation record found for renewed policy.");
		}
		renewalData.setData(commonVoMap);		
		return renewalData;		
	}
	
	/**
	 * Method to fetch the policy details based on criteria ie policyId
	 * @param input
	 * @return TTrnPolicyQuo
	 * @since WC Monoline
	 */
	@Override
	public TTrnPolicyQuo getQuoteDetails(Object [] input){
		
		TTrnPolicyQuo tTrnPolicy = null;
		
		String sql = com.Constant.CONST_SELECT_FROM_T_TRN_POLICY_WHERE_POL_POLICY_ID_POLICYID;		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);	
		query.addEntity(TTrnPolicyQuo.class);
		query.setParameter(com.Constant.CONST_POLICYID, input[0]);
		List<?> result = query.list();
		if(!Utils.isEmpty(result)){
			tTrnPolicy = (TTrnPolicyQuo) result.get(0);	
		}
		return tTrnPolicy;
	}
	
	public void softStopCommon(TravelInsuranceVO travelInsuranceVO)
	{
		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		Object claimInputData[] = new Object[2];
		claimInputData[0] = travelInsuranceVO;
		claimInputData[1] = Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP");
		input.setData(claimInputData);
		TaskExecutor.executeTasks(UPDATE_RENEWAL_QUOTE_STATUS_COMMON, input);
		TaskExecutor.executeTasks("UPDATE_RENEWAL_STATUS_FOR_SOFT_STOP", input);
		TaskExecutor.executeTasks("UPDATE_RENEWAL_GACC_PERSON_STATUS_FOR_SOFT_STOP", input);
		travelInsuranceVO.getCommonVO().setStatus( Integer.parseInt( Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP") ) );
		insertCommonToTaskList(travelInsuranceVO);
	}
	
	public void insertCommonToTaskList(TravelInsuranceVO travelInsuranceVO)
	{
		List<TravelerDetailsVO> travellers =  travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList();
		TaskVO taskVO = new TaskVO();
		if(!Utils.isEmpty(travelInsuranceVO)){
			taskVO.setLoggedInUser(travelInsuranceVO.getCommonVO().getLoggedInUser());	
		}
		Calendar currentDate = Calendar.getInstance();
		// refer it to the logged in user
		if( !Utils.isEmpty(travelInsuranceVO.getCommonVO().getLoggedInUser()) && !Utils.isEmpty(travelInsuranceVO.getCommonVO().getLoggedInUser().getUserId()))
		{		
			taskVO.setAssignedTo( travelInsuranceVO.getCommonVO().getLoggedInUser().getUserId().toString());
			taskVO.setAssignedBy( travelInsuranceVO.getCommonVO().getLoggedInUser().getUserId().toString());
		}
		else
		{
			taskVO.setAssignedTo( Utils.getSingleValueAppConfig( com.Constant.CONST_MISLIVE_USER ) );
			taskVO.setAssignedBy(  Utils.getSingleValueAppConfig( com.Constant.CONST_MISLIVE_USER ));
		}
			
		taskVO.setDesc("Soft Stop Quote");
		String reasonForSoftStop = reasonForSoftStopCommon(travellers,travelInsuranceVO);
		taskVO.setDesc("Soft Stop Quote [Reason: "  +reasonForSoftStop+" ]");

		taskVO.setCategory( Utils.getSingleValueAppConfig( "TASK_REFERRAL_CATEGORY" ) );
		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getLoggedInUser()) && !Utils.isEmpty(travelInsuranceVO.getCommonVO().getLoggedInUser().getUserId()))
			taskVO.setCreatedBy( travelInsuranceVO.getCommonVO().getLoggedInUser().getUserId().toString() );
		else
			taskVO.setCreatedBy(  Utils.getSingleValueAppConfig( com.Constant.CONST_MISLIVE_USER ) );
		
		Timestamp st = new Timestamp(currentDate.getTimeInMillis());
		taskVO.setCreatedOn( st);
		taskVO.setCreatedDate( currentDate.getTime() );
		currentDate.add( Calendar.DAY_OF_MONTH, 30 );
		taskVO.setDueDate( currentDate.getTime() );
		taskVO.setLoggedInUser( ( travelInsuranceVO.getCommonVO().getLoggedInUser() ) );
		taskVO.setPolicyType( travelInsuranceVO.getPolicyType().toString());
		taskVO.setPolEndId((long) 0);
		taskVO.setPolLinkingId( travelInsuranceVO.getPolicyId() ); // Pol linking Id will store Pol id in case of Travel and Home(refer TaskVo)
		//taskVO.setPolicyNo( policyVO.getPolicyNo() );
		taskVO.setQuoteNo(travelInsuranceVO.getCommonVO().getQuoteNo() );
		taskVO.setPriority( Utils.getSingleValueAppConfig( "TASK_DEFAULT_PRIORITY" ) );
		taskVO.setStatus( Utils.getSingleValueAppConfig( "TASK_DEFAULT_STATUS" ) );
		taskVO.setTaskName( travelInsuranceVO.getCommonVO().getQuoteNo() + " is referred" );
		taskVO.setQuote(travelInsuranceVO.getCommonVO().getIsQuote());
		taskVO.setTaskType( Utils.getSingleValueAppConfig( "TASK_TRAN_TYPE_QUOTE" ) );
		
		if(!Utils.isEmpty(ServiceContext.getLocation()))
		{
			taskVO.setLocation(ServiceContext.getLocation());
		}
		else
		{
			taskVO.setLocation(Utils.getSingleValueAppConfig(DEPLOYED_LOCATION));
		}
		
		TaskExecutor.executeTasks( "SAVE_ALL_REFERRALS_INSVC", taskVO );
	}	
	
	private String reasonForSoftStopCommon(List<TravelerDetailsVO> travellers,TravelInsuranceVO travelInsuranceVO)
	{
		CommentsVO comments = 	new CommentsVO();
		PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
		String reason = "";
		//Added stringbuffer  to avoid "+", fix sonar violation on 13-9-2017
		StringBuffer stringBuffer = new StringBuffer();
		if(travellers.size() == 1)
		{
			short age = SvcUtils.getAge(travellers.get(0).getDateOfBirth(), new Date());
			if(age <16)						
				//reason = reason + " " +"Age below 16";
				reason=stringBuffer.append("  Age below 16").toString();
		}
		for(TravelerDetailsVO tr : travellers)
		{
			short age = SvcUtils.getAge(tr.getDateOfBirth(), new Date());
			if(age>74)				
			{
				//reason = reason +" "+ "Age 75 or above";
				reason=stringBuffer.append("  Age 75 or above").toString();
				break;
				
			}  
		
		}
		comments.setComment(reason);//To insert soft stop comments ID 108302
		
		if( !Utils.isEmpty( travelInsuranceVO )){
			comments.setPocPolicyId( travelInsuranceVO.getPolicyId() );
			comments.setPocEndtId( travelInsuranceVO.getEndtId());
		}
		else{
			
			throw new BusinessException("", null, "No Travel details found");
		}
		comments.setLob(LOB.TRAVEL);
		comments.setPolicyStatus(Byte.valueOf(Utils.getSingleValueAppConfig( "TASK_DEFAULT_STATUS" )));
		
		if( !Utils.isEmpty(travelInsuranceVO.getCommonVO().getLoggedInUser()) ){
			comments.setLoggedInUser(  travelInsuranceVO.getCommonVO().getLoggedInUser()  );
		}
		
		if(!Utils.isEmpty( comments ))
		{
			polComHolder.setComments( comments );
		}
		CaptureCommentsService captureComments =  (CaptureCommentsService) Utils.getBean( "captureComments" );
		captureComments.invokeMethod( "storeComments", polComHolder.getComments() );
		return reason.toString();
	}

	@Override
	public BaseVO getPreviousYearQuoteNo(BaseVO baseVO) {		
				
		CommonVO commonVO = (CommonVO) baseVO;	
		DataHolderVO<Long> quoteOutput = new DataHolderVO<Long>();
		
		String sqlQuery = "select POL_QUOTATION_NO,POL_POLICY_TYPE from T_TRN_POLICY " +
					"where POL_ISSUE_HOUR = "+ SvcConstants.POL_ISSUE_HOUR + " and POL_POLICY_ID = " +
					"(Select POL_REF_POLICY_ID from T_TRN_POLICY_QUO " +
					"where POL_ISSUE_HOUR = "+ SvcConstants.POL_ISSUE_HOUR + 
					"and POL_VALIDITY_EXPIRY_DATE = '"+Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE ) +
					"' " +" and POL_QUOTATION_NO = ?) and POL_VALIDITY_EXPIRY_DATE = '"+Utils.getSingleValueAppConfig( SvcConstants.DEFAULT_POL_VALIDITY_EXPIRY_DATE )+"' " ;		
		
		if( !Utils.isEmpty( commonVO ) && commonVO.getIsQuote() ){
			
			Long quoteNo = commonVO.getQuoteNo();			
			List<Object[]> result = DAOUtils.getSqlResultForPas(sqlQuery, quoteNo);			
			Object[] object = (Object[]) result.get( SvcConstants.zeroVal );			
			if (Integer.valueOf(object[1].toString()) == SvcConstants.WC_POLICY_TYPE){
				quoteOutput.setData(Long.parseLong(object[0].toString()));
				return quoteOutput;
			}
		}		
		return quoteOutput;
	}	

}
