package com.rsaame.pas.renewals.dao;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPrintBatchPas;
import com.rsaame.pas.dao.model.TTrnRenewalBatchEplatform;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.model.VRenewalStatusEmailReportPas;
import com.rsaame.pas.dao.model.VRenewalStatusReportPas;
import com.rsaame.pas.dao.model.VRenewalStatusReportPasHT;
import com.rsaame.pas.dao.model.VTrnRenewalPoliciesSbs;
import com.rsaame.pas.dao.model.VTrnRenewalQuotationsSbs;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.policy.svc.CaptureCommentsService;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.app.PrintDocVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RenewalReportsVO;
import com.rsaame.pas.vo.bus.RenewalResultsVO;
import com.rsaame.pas.vo.bus.RenewalSearchSummaryVO;
import com.rsaame.pas.vo.bus.RenewalVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.WCVO;

/**
 * @author m1006438
 * This class is DAO class for Renewals
 */

public class RenewalsDAO extends BaseDBDAO implements IRenewalsDAO{
	//PRINT BATCH SEQUENCE
	private final static Logger LOGGER = Logger.getLogger( RenewalsDAO.class );
	private final static String PRINT_BATCH_ID = "SEQ_PAS_PRINT_BATCH_ID";
	private final static String PRINT_LOC = "PRINT_LOC";
	private static final String SOFT_STOP ="softStop";
	
	final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	//PAR
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	//BI
	private final static Integer BI_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_SECTION" ) );
	private static final short BI_PRM_COVER_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_COVER_CODE" ) );
	private static final short BI_COVER_TYPE_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_COVER_TYPE_CODE" ) );
	private static final short BI_PRM_CST_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_CST_CODE" ) );

	//PL
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	//EE
	private final static Integer EE_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "EE_SECTION" ) );
	//WC
	private final static Integer WC_SECTION = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" ) );
	private static final short WC_BASIC_COVER = Short.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) );
	private static final short WC_COVER_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_TYPE" ) );
	private static final short WC_COVER_SUB_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_SUB_TYPE" ) );
	//Money
	private final static Integer MONEY_SECTION = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_SECTION" ) );
	//TB 
	private final static Integer TB_SECTION = Integer.valueOf( Utils.getSingleValueAppConfig( "TB_SECTION" ) );
	private final static Integer TB_RISK_CODE = 27;
	// GIT
	private final static Integer GIT_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_SECTION" ) );
	//MB:
	private final static Integer MB_SECTION = Integer.valueOf( Utils.getSingleValueAppConfig( "MB_SECTION" ) );
	private static final String MB_COVER_TYPE = "MB_COVER_TYPE";
	private static final String MB_COVER_SUB_TYPE = "MB_COVER_SUB_TYPE";

	//TB
	private static final short TB_BASIC_COVER = Short.valueOf( "1" );
	private static final short TB_COVER_TYPE = Short.valueOf( "0" );
	private static final short TB_COVER_SUB_TYPE = Short.valueOf( "0" );
	//GPA
	private final static Integer GPA_SECTION = Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_SECTION" ) );
	private final static Short GROUP_PERSONAL_ACCIDENT_COVER_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_COVER_TYPE" ) );
	private final static Short GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE" ) );

	//DOS
	private final static Short DOS_SECTION = Short.valueOf( Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_SECTION" ) );

	//FIDELITY
	private final static Short FIDELITY_SECTION = Short.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION" ) );
	private final static Integer FIDELITY_COVER_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_COVER_TYPE" ) );
	private final static Integer FIDELITY_COVER_SUB_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_COVER_SUB_TYPE" ) );

	private final static int ZERO_VAL = 0;

	private final static Integer QUOTE_ACTIVE = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) );

	private final static Integer POL_POLICY_TYPE = 50;

	public static final String CLASS_CODE = "FIRE";

	public static final String QRY_RENEWAL_SEARCH_OBJ = " renPols";
	
	public static final String QRY_RENEWAL_QUOTATION_SEARCH_OBJ = " renQuotations";

	public static final String QRY_RENEWAL_SEARCH_BASE = "select renPols FROM VTrnRenewalPoliciesSbs " + QRY_RENEWAL_SEARCH_OBJ;
	
	public static final String QRY_RENEWAL_QUOTATONS_SEARCH_BASE = "select renQuotations FROM VTrnRenewalQuotationsSbs " + QRY_RENEWAL_QUOTATION_SEARCH_OBJ;

	public static final String QRY_GET_ALREADY_PRINTED = "select id.polLinkingId from TTrnPrintBatchPas where prnStatus = 'Y'";
	
	public static final String QRY_PRT_WHERE = " WHERE ";

	public static final String QRY_PRT_AND = " AND ";

	public static final String QRY_PRT_DOT = ".";

	public static final String QRY_PRT_EQUL = "=:";

	public static final String QRY_PRT_NOT_EQUL = "!=";

	public static final String QRY_PRT_EQUL_OR_GREATER = ">=:";

	public static final String QRY_PRT_EQUL_OR_LESS = "<=:";
	
	public static final String QRY_PRT_IS = " IS ";
	
	public static final String QRY_PRT_NOT = " NOT ";
	
	public static final String QRY_PRT_NOT_IN = " NOT IN ";
	
	public static final String QRY_PRT_NULL = " NULL ";

	public static final String BLANK_SPACE = "";
	public static final Short BROKER_CHANNEL = 4;
	public static final String PARAM_DC_CODE = "polDctCode";
	public static final String PARAM_DISTRIBUTION_CHNL = "polDistributionChnl";
	
	public static final String PARAM_EMAIL_ID = "cshEEmailId";
	
	public static final String PARAM_LINKING_ID = "id.polLinkingId";
	
	public static final String PARAM_CLASS_CODE = "polClassCode";

	public static final String PARAM_POL_NUM = "polPolicyNo";
	
	public static final String PARAM_QUO_NUM = "polQuotationNo";

	public static final String PARAM_ENDT_ID = "endtId";

	public static final String PARAM_BROKER_CODE = "polBrCode";

	public static final String PARAM_SCHEME_CODE = "polCoverNoteHour";

	public static final String PARAM_FROM_DATE = "polEffectiveDate";

	public static final String PARAM_POL_EXP_DATE = "polExpiryDate";

	public static final String PARAM_LOCATION_CODE = "polLocationCode";

	public static final String PARAM_FIRST_NAME = "cshEName1";

	public static final String PARAM_NAME = "cshEName1";

	public static final String LIKE_OPERATOR = " LIKE ";

	public static final String PERCENTAGE = "%";

	public static final String SINGLE_QUOTE = "'";

	public static final String COLON = ":";

	public static final String COMMA = ",";

	public static final String QRY_INSURED_OBJ = "customer";

	public static final String QRY_INSURED = ", " + "TMasCashCustomerQuo " + QRY_INSURED_OBJ;

	public static final String TO_UPPER_CASE = "upper(";

	public static final String CLOSE_BRACKET = ")";
	
	public static final String OPEN_BRACKET = "(";

	public static final String QRY_BASIC_CONDITION = QRY_PRT_WHERE + "renPols.polInsuredId=customer.cshInsuredId ";
	
	public static final String QRY_BASIC_CONDITION1 = "AND renPols.polPolicyId=customer.id.cshPolicyId ";

	public static final String PRINT_QRY_BASIC_CONDITION = QRY_PRT_WHERE + "renQuotations.polInsuredId=customer.cshInsuredId ";
	public static final String PRINT_QRY_BASIC_CONDITION1 = "AND renQuotations.polPolicyId=customer.id.cshPolicyId ";

	// private static final String CTX_RENEWAL_BATCH_PRINT_REQ =  "printRequestedBy()";

	// private static final String CTX_RENEWAL_BATCH_PRINT_DISTRIBUTION_CHANNEL = "distributionChannelForBatchPrint()";

	//private static final Integer QUOTE_ACCEPT = null;
	
	/*
	 * added to specify the sort order for renewal results by insured name
	 */
	public static final String PARAM_INSURED_NAME="polinsuredName";
	
	public static final String QRY_RENEWAL_SEARCH_SORT=" order by "+"TRIM("+QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_INSURED_NAME+")";
	
	public static final String DEPLOYED_LOCATION="DEPLOYED_LOCATION";
	public static final String DEFAULT_USER_CODE = "17";
	
	
	public static final String ALL_OPTION = "999999";
	public static final String DIRECT_OPTION = "9999999";
	//public static final String QRY_RENEWAL_QUOTATONS_EMAIL_SEARCH_BASE = "select * FROM VRenewalStatusEmailReportPas " + QRY_RENEWAL_QUOTATION_SEARCH_OBJ;
	public static final String QRY_RENEWAL_QUOTATONS_EMAIL_SEARCH_BASE = "select renQuotations FROM VRenewalStatusEmailReportPas " + QRY_RENEWAL_QUOTATION_SEARCH_OBJ;

	/**
	 * @param criteria
	 * @return BaseVO
	 * This method returns the list of policies to be renewed based on search criteria
	 */
	@Override
	public BaseVO getPoliciesToBeRenewed( BaseVO criteria ){
		GenerateRenewalsSearchCriteriaVO renewalsVO = (GenerateRenewalsSearchCriteriaVO) criteria;
		Session session = null;
		List<VTrnRenewalPoliciesSbs> renPolicies = null; // task
		List<RenewalResultsVO> policyList = new com.mindtree.ruc.cmn.utils.List<RenewalResultsVO>( RenewalResultsVO.class ); //tasklist

		try{
			
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			
			StringBuffer queryString = new StringBuffer( 128 );
			// CALCULATE THE DATES FOR THE DAYS RANGE
			if( renewalsVO.getNoOfDays() != null ){
				Calendar cal = Calendar.getInstance();
				renewalsVO.setTransactionFrom( cal.getTime() );
				LOGGER.debug( "From Date :" + renewalsVO.getTransactionFrom() );
				cal.add( Calendar.DATE, Integer.parseInt( renewalsVO.getNoOfDays() ) );
				renewalsVO.setTransactionTo( cal.getTime() );
				LOGGER.debug( "To Date :" + renewalsVO.getTransactionTo() );
			}
			// ADD THE BASE QUERY
			queryString.append( QRY_RENEWAL_SEARCH_BASE );
			boolean appendWhere = true;
			LOGGER.debug( "queryString _1" + queryString.toString() );
			if( renewalsVO.getInsuredName() != null ){
				queryString.append( QRY_INSURED + QRY_BASIC_CONDITION + QRY_BASIC_CONDITION1 );
				appendWhere = false;
			}
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			
			if( renewalsVO.getClazz() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL + PARAM_CLASS_CODE );
				paramsMap.put( PARAM_CLASS_CODE, Short.parseShort( renewalsVO.getClazz() ) );
			}
			LOGGER.debug( "queryString _2" + queryString.toString() );
			if( renewalsVO.getPolicyNo() != null ){

				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_NUM + QRY_PRT_EQUL + PARAM_POL_NUM );
				paramsMap.put( PARAM_POL_NUM, Long.parseLong( renewalsVO.getPolicyNo() ) );

			}
			LOGGER.debug( "queryString _3" + queryString.toString() );
			if( renewalsVO.getTransactionFrom() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_EXP_DATE + QRY_PRT_EQUL_OR_GREATER + PARAM_FROM_DATE );
				paramsMap.put( PARAM_FROM_DATE, renewalsVO.getTransactionFrom() );

			}
			LOGGER.debug( "queryString _4" + queryString.toString() );
			if( renewalsVO.getTransactionTo() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_EXP_DATE + QRY_PRT_EQUL_OR_LESS + PARAM_POL_EXP_DATE );
				paramsMap.put( PARAM_POL_EXP_DATE, renewalsVO.getTransactionTo() );

			}
			LOGGER.debug( "queryString _5" + queryString.toString() );
			// Release 4.0 Oman Changes passing location code in search query
			if( renewalsVO.getBranch() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + PARAM_LOCATION_CODE );
				paramsMap.put( PARAM_LOCATION_CODE, Integer.parseInt( renewalsVO.getBranch() ) );
			}
			// Release 4.0 Oman Changes
			LOGGER.debug( "queryString _6" + queryString.toString() );
			if( renewalsVO.getBrokerName() != null ){

				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL + PARAM_BROKER_CODE );
				paramsMap.put( PARAM_BROKER_CODE, Short.parseShort( renewalsVO.getBrokerName() ) );

			}
			LOGGER.debug( "queryString _7" + queryString.toString() );
			if( renewalsVO.getScheme() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL + PARAM_SCHEME_CODE );
				paramsMap.put( PARAM_SCHEME_CODE, Integer.parseInt( renewalsVO.getScheme() ) );

			}

			LOGGER.debug( "queryString _8" + queryString.toString() );
			if( renewalsVO.getInsuredName() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				String PARAM = PERCENTAGE + ( renewalsVO.getInsuredName() ).toUpperCase() + PERCENTAGE;
				queryString.append( TO_UPPER_CASE + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_FIRST_NAME + CLOSE_BRACKET + LIKE_OPERATOR + SINGLE_QUOTE + PARAM + SINGLE_QUOTE );

			}
			LOGGER.debug( "queryString _9" + queryString.toString() );
			if( renewalsVO.getAllDirect() ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DISTRIBUTION_CHNL + QRY_PRT_NOT_EQUL + BROKER_CHANNEL );

			}
			/*
			 *  Search Criteria :- Search Criteria based on Quotation No.  
			 */
			LOGGER.debug( "queryString _10" + queryString.toString() );
			if( renewalsVO.getQuoteNo() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_QUO_NUM + QRY_PRT_EQUL + PARAM_QUO_NUM );
				paramsMap.put( PARAM_QUO_NUM, Long.parseLong( renewalsVO.getQuoteNo() ) );
			}
			/*
			 * Adding the order by clause to sort the renewal result by insured name 
			 */
			queryString.append(QRY_RENEWAL_SEARCH_SORT);
			LOGGER.debug( "queryString _11" + queryString.toString() );
			Query query = session.createQuery( queryString.toString() );
			// ADD QUERY PARAMS
			Iterator<String> iterParams = paramsMap.keySet().iterator();
			while( iterParams.hasNext() ){
				String paramName = iterParams.next().toString();
				query.setParameter( paramName, paramsMap.get( paramName ) );
				LOGGER.debug( "SetParam :_1" + paramName + "/" + paramsMap.get( paramName ) );
			}
			
			renPolicies = query.list();

		}
		catch( HibernateException hibernateException ){
			hibernateException.printStackTrace();
			throw new BusinessException( "pas.renewal.exceptionInDataFetch", null, "Exception Occurred while fetching the dat_1" );
		}
		RenewalSearchSummaryVO renPolDetails = new RenewalSearchSummaryVO();

		for( VTrnRenewalPoliciesSbs renewalPolVO : renPolicies ){
			if( !Utils.isEmpty( renewalPolVO ) ){
				RenewalResultsVO renResults = new RenewalResultsVO();
				renResults.setPolLinkingId( renewalPolVO.getId().getPolLinkingId() );
				renResults.setEndtId( renewalPolVO.getPolEndtId() );
				renResults.setEndtNo( renewalPolVO.getPolEndtNo() );
				renResults.setConcatPolicyNo( renewalPolVO.getPolConcPolicyNo() );
				renResults.setPolicyNo( renewalPolVO.getPolPolicyNo() );
				renResults.setPolExpiryDate( renewalPolVO.getPolExpiryDate1() );
				renResults.setPolEffectiveDate( renewalPolVO.getPolEffectiveDate1() );
				//renResults.setClassCode( CLASS_CODE );
				/*Added to display the Base Class in generate renewal search result - BugZilla: 4188*/
				renResults.setClassCode(renewalPolVO.getPolBaseClass());
				renResults.setPolicyYear( renewalPolVO.getPolPolicyYear() );
				/*
				 * added for renewal report additional fields
				 */
				renResults.setInsuredName( renewalPolVO.getPolinsuredName() );
				renResults.setBrokerName( renewalPolVO.getPolbrokerName() );
				renResults.setBranchName( renewalPolVO.getPolbranchName() );
				renResults.setSchemaName( renewalPolVO.getPolschemaName());
				
				policyList.add( renResults );
				renPolDetails.setRenPolList( policyList );
			}
		}

		return renPolDetails;
	}

	/**
	 * @param baseVO
	 * This method saves the records in the batch table for generating renewals in batch mode
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public void savePoliciesForBatchRenewal( BaseVO baseVO ){
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		TTrnRenewalBatchEplatform[] polForRenewal = (TTrnRenewalBatchEplatform[]) data[ 0 ];
		for( TTrnRenewalBatchEplatform renewalPolicy : polForRenewal ){
			renewalPolicy.setRequesterId( ServiceContext.getUser().getUserId() );
			renewalPolicy.setRequestDate( new Date() );
			if(Utils.isEmpty( renewalPolicy.getRenQuotationStatus() )){
				renewalPolicy.setRenQuotationStatus( "N" );			
			}
			if(Utils.isEmpty( renewalPolicy.getLastExecutedStep() )){
				renewalPolicy.setLastExecutedStep( Short.valueOf(String.valueOf( ZERO_VAL )) )	;	
			}
			getHibernateTemplate().save( renewalPolicy );			
		}
		//DAOUtils.sendMailForBatchSubmit(SvcConstants.SBS_Policy_Type);
		DAOUtils.sendMailForBatchSubmit(SvcConstants.SBS_POL_TYPE);
		
	}

	/**
	 * Generate renewal quote, set status , copy trade license document
	 */
	public BaseVO generateRenewal( BaseVO baseVO ){
		/*
		 * 1. Call Stored procedure to generate the renewal quote
		 */
		DataHolderVO<Object[]> inputVO = (DataHolderVO<Object[]>)baseVO;
		Object inputData[] = inputVO.getData();
		Object quoteDetails[] = new Object[4];
		DataHolderVO<Object []> renewalData = new DataHolderVO<Object[]>();
		Integer userID = (Integer)inputData[1]; 
		
		@SuppressWarnings("unchecked")
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>)generateOnlineRenewal (baseVO);
		Object[] renData = holderVO.getData();
		Long quotationNo = (Long)renData[0];
		Long renLinkingId = (Long)renData[1];
		Long originalLinkingId = (Long)renData[2];
		
		LOGGER.info( "Renewal quote generated successfully" );
		
		/*
		 * Copying trade license  from original quotation to renewed  quote
		 */
		File orginalTLfile = new File(Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_ROOT)+"//"+Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER)+"//"+originalLinkingId);
		File renewedFile = new File(Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_ROOT)+"//"+Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER)+"//"+renLinkingId);
		LOGGER.debug("--------------- Copy Trade License for renewed quote --------------------"+quotationNo);
		LOGGER.debug("---------------------------------------Old file------------------------"+orginalTLfile.getPath());
		LOGGER.debug("---New file is writable---"+renewedFile.canWrite()+"--------path of new file-------"+renewedFile.getPath());
		/*
		 * Added Java IO copy method instead of FileUtils API
		 */
		try{
			SvcUtils.copyTradeLicense(orginalTLfile,renewedFile);
		}catch (IOException e) {
			e.printStackTrace();
			LOGGER.debug("--------------------------------------Files Copied failure------------------------");
			LOGGER.trace( "Copying of trade licence failed." );
        }
		
		/*try{
		
			forceMkdir - Makes a directory If renewedFile directory is already not created - Makes a directory, including any necessary but nonexistent parent directories. 
			 If there already exists a file with specified name or the directory cannot be created then an exception is thrown.
			FileUtils.forceMkdir(renewedFile);
			FileUtils.copyDirectory(orginalTLfile,renewedFile);
			LOGGER.debug("--------------------------------------Files Copied successfully------------------------");
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			LOGGER.debug("--------------------------------------Files Copied failure------------------------");
			LOGGER.trace( "Copying of trade licence failed." );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.debug("--------------------------------------Files Copied failure------------------------");
			LOGGER.trace( "Copying of trade licence failed." );
		}*/
		
		/*
		 * 2. Call the SP to get the claim details for the given linking id
		 */
		
		DataHolderVO<Object[]> claimInput =  new DataHolderVO<Object[]>();
		Object claimInputData[] = new Object[3];
		claimInputData[0] = (Long)inputData[0]; //Original policy linking id
		claimInputData[1] = null;
		claimInput.setData(claimInputData);
		
		@SuppressWarnings("unchecked")
		DataHolderVO<Long> claimOutput = (DataHolderVO<Long>) getClaimCount( claimInput );
		Long claimCount = claimOutput.getData();
		
		/*
		 * 3. Check if Outstanding premium exists
		 */
		DataHolderVO<Long> outstandingPrminput = new DataHolderVO<Long>();
		outstandingPrminput.setData( quotationNo );
		DataHolderVO<BigDecimal> prmOutput = (DataHolderVO<BigDecimal>) getOSPremium(outstandingPrminput );
		Boolean osPrmPresent = false;
		
		/*
		 * 4. Check if Discount loading percentage >= 30%
		 */
		DataHolderVO<Long> disLoadingInput = new DataHolderVO<Long>();
		disLoadingInput.setData( quotationNo );
		DataHolderVO<Map<String,BigDecimal>> disLoadOutput = (DataHolderVO<Map<String,BigDecimal>>) getDisLoadPer( disLoadingInput );
		Boolean disLoadPresent = false;
		
		/*
		 * 5. Check if broker account blocked
		 */
		DataHolderVO<Long> brBlockedInput = new DataHolderVO<Long>();
		brBlockedInput.setData( quotationNo );
		DataHolderVO<Byte> brBlockedOutput = (DataHolderVO<Byte>) getBrAccStatus( brBlockedInput );
		Byte brStatus = null;
		
		if(!Utils.isEmpty(brBlockedOutput)){
			brStatus = brBlockedOutput.getData();
		}
		
		PolicyVO  polVO= new PolicyVO();
		polVO.setPolLinkingId( renLinkingId );
		polVO.setEndtId( (long) 0 );
		polVO.setQuoteNo(quotationNo);
		polVO.setIsQuote(true);
		/*
		 * Set renewals object to set claim count, outstanding premium, discount/Loading percentage
		 */
		RenewalVO renVo = polVO.getRenewals();
		if(Utils.isEmpty(renVo)){
			renVo = new RenewalVO();
		}
		renVo.setClaimCount(claimCount);
		renVo.setBrokerStatus(brStatus);
		
		if( Utils.isEmpty( prmOutput.getData() ) ){
			renVo.setOsPremium( Double.valueOf( ZERO_VAL ) );
		}
		else{
			renVo.setOsPremium( Double.valueOf( prmOutput.getData().toString() ) );
			if(renVo.getOsPremium() != 0 ){
				osPrmPresent = true;
				LOGGER.debug( "OS premium for policy exists." );
			}
		}
		/*
		 * Check discount/Loading percentage >= 30%
		 */
		if(!Utils.isEmpty( disLoadOutput.getData() )){
			Map<String,BigDecimal> percentages = disLoadOutput.getData() ;
			BigDecimal discPercentage = BigDecimal.ZERO;
			BigDecimal loadPercentage = BigDecimal.ZERO;
			if(!Utils.isEmpty(percentages.get(SvcConstants.DISCOUNT_PER)) ){ 
				discPercentage = percentages.get(SvcConstants.DISCOUNT_PER);
			}
			if(!Utils.isEmpty(percentages.get(SvcConstants.LOADING_PER)) ){ 
				loadPercentage = percentages.get(SvcConstants.LOADING_PER);
			}
			
			//SONARFIX -- changed the variable from EQUALS to EQUALS_C -- 25-apr-2018
			if ( discPercentage.compareTo(SvcConstants.DIS_LOAD_PERCENTAGE_LIMIT) == SvcConstants.EQUALS_C ||
					discPercentage.compareTo(SvcConstants.DIS_LOAD_PERCENTAGE_LIMIT) == SvcConstants.FIRST_RECORD_GREATER){
						disLoadPresent = true;
						renVo.setDiscountLoadingPer(discPercentage);
						LOGGER.debug( "Discount>=30 per for policy exists." );
			}
		// CR 108048 : Removing the loading check if the renewal policy has loading of >= 30%.
		/*	if ( loadPercentage.compareTo(SvcConstants.DIS_LOAD_PERCENTAGE_LIMIT) == SvcConstants.EQUALS ||
					loadPercentage.compareTo(SvcConstants.DIS_LOAD_PERCENTAGE_LIMIT) == SvcConstants.FIRST_RECORD_GREATER){
						disLoadPresent = true;
						renVo.setDiscountLoadingPer(loadPercentage);
						LOGGER.debug( "Loading>=30 per for policy exists." );
			} */	
		}
		Object renInputData[] = new Object[2];
		DataHolderVO<Object[]> input =  new DataHolderVO<Object[]>();
		if(claimCount>0 || osPrmPresent || disLoadPresent || ( !Utils.isEmpty(brStatus) && brStatus == SvcConstants.BLOCKED_STATUS)){
			renInputData[0] = polVO;//link endt
			renInputData[1] = Utils.getSingleValueAppConfig( "QUOTE_SOFT_STOP" );
			input.setData(renInputData);
			LOGGER.debug( "Softstop status has to be set for renewed quotation." );
			CommentsVO comments = 	new CommentsVO();
			comments.setIsQuote(true);
			saveReasonForSoftStop(renLinkingId ,(UserProfile)inputData[2], claimCount, osPrmPresent,
					disLoadPresent, brStatus,renInputData[1],comments);
			insertQuoteToTaskList((UserProfile)inputData[2],polVO,userID,comments);
			updateQuotationStatus( input ); 
		}
		
		
		/*
		 * if there is no softstop status. set renewal terms.
		 */
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
			if(claimCount == 0 && !osPrmPresent && !disLoadPresent && ( Utils.isEmpty(brStatus) || (!Utils.isEmpty(brStatus) && brStatus != SvcConstants.BLOCKED_STATUS) )){
				polVO.setRenewalBasis(Integer.valueOf(Utils.getSingleValueAppConfig("JLT_RENEWAL_BASIS_EXPIRING_TERMS")));
				renInputData[0] = polVO;
				renInputData[1] = Utils.getSingleValueAppConfig( "RENEWAL_TERMS" );
				input.setData(renInputData);
				LOGGER.debug( "Update renewal terms to "+ renInputData[1]);
				updateRenewalTerms( input ); 
			} else {
				/*
				 * if there  soft stop renewal terms.
				 */
				polVO.setRenewalBasis(null);
				renInputData[0] = polVO;
				renInputData[1] = null;//Utils.getSingleValueAppConfig( "RENEWAL_TERMS_SOFT_STOP" );
				LOGGER.debug( "Update renewal terms to "+ renInputData[1]);
				input.setData(renInputData);
				updateRenewalTerms( input ); 
			}
			//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts

		
		quoteDetails[0] = quotationNo;
		quoteDetails[1] = renLinkingId;
		quoteDetails[2] = originalLinkingId;
		quoteDetails[3] = polVO;
		renewalData.setData( quoteDetails );
		LOGGER.debug( "Exiting the generateRenewal method of RenewalDAO");
		/*try{
		Integer i = 8/0;
		} catch ( Exception e){
			throw new BusinessException( "cmn.storedproc.error", e, "Unexpected exception occurred. Please contact Administrator." );
		}*/
		//TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		return renewalData;
		
	}
	
	private void insertQuoteToTaskList(UserProfile profile,PolicyVO policyVO,Integer userID,CommentsVO comments)
	{
		TaskVO taskVO = new TaskVO();
		if(!Utils.isEmpty(profile)){
			taskVO.setLoggedInUser(profile);	
		}
		Calendar currentDate = Calendar.getInstance();
		
		if(!DEFAULT_USER_CODE.equals(( (UserProfile) taskVO.getLoggedInUser() ).getRsaUser().getUserId().toString()))
		{
			taskVO.setAssignedTo( ( (UserProfile) taskVO.getLoggedInUser() ).getRsaUser().getUserId().toString() );
			taskVO.setAssignedBy( ( (UserProfile) taskVO.getLoggedInUser() ).getRsaUser().getUserId().toString() );
		}
		else
		{
			taskVO.setAssignedTo(userID.toString());
			taskVO.setAssignedBy(userID.toString());
		}
		
		taskVO.setDesc("Soft Stop Quote");
		if(!Utils.isEmpty(comments) && !Utils.isEmpty(comments.getComment()))
		{
			taskVO.setDesc("Soft Stop Quote [Reason: " +comments.getComment() +" ]");
		}
		taskVO.setCategory( Utils.getSingleValueAppConfig( "TASK_REFERRAL_CATEGORY" ) );
		taskVO.setCreatedBy( ( (UserProfile) taskVO.getLoggedInUser() ).getRsaUser().getUserId().toString() );
		taskVO.setCreatedOn( policyVO.getCreatedOn() );
		taskVO.setCreatedDate( currentDate.getTime() );
		currentDate.add( Calendar.DAY_OF_MONTH, 30 );
		taskVO.setDueDate( currentDate.getTime() );
		taskVO.setLoggedInUser( ( policyVO.getLoggedInUser() ) );
		taskVO.setPolicyType( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) );
		taskVO.setPolEndId((long) 0);
		taskVO.setPolLinkingId( policyVO.getPolLinkingId() );
		//taskVO.setPolicyNo( policyVO.getPolicyNo() );
		taskVO.setQuoteNo( policyVO.getQuoteNo() );
		taskVO.setPriority( Utils.getSingleValueAppConfig( "TASK_DEFAULT_PRIORITY" ) );
		taskVO.setStatus( Utils.getSingleValueAppConfig( "TASK_DEFAULT_STATUS" ) );
		taskVO.setTaskName( policyVO.getQuoteNo() + " is referred" );
		taskVO.setQuote( policyVO.getIsQuote() );
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
	
	
	private void saveReasonForSoftStop(Long renLinkingId, UserProfile userProfile, Long claimCount,Boolean osPrmPresent, Boolean disLoadPresent, Byte brStatus, Object renInputData,CommentsVO comments) {
		//CommentsVO comments = 	new CommentsVO();
		PolicyCommentsHolder polComHolder = new PolicyCommentsHolder();
		
		comments.setComment(prepareReasonStringForSoftStop(claimCount,
				osPrmPresent, disLoadPresent, brStatus).toString());
					
		if( !Utils.isEmpty( renLinkingId )){
			comments.setPocPolicyId( renLinkingId );
		}
		comments.setPocEndtId( DAOUtils.getMaxPolicyEndtIdFromLinkingId(getHibernateTemplate(), renLinkingId));
		comments.setLob(LOB.SBS);
		comments.setPolicyStatus(Byte.valueOf(renInputData.toString()));
		
		if( !Utils.isEmpty( userProfile ) ){
			comments.setLoggedInUser( userProfile );
		}
		
		/*UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		if( !Utils.isEmpty( userProfile ) ){
			comments.setLoggedInUser( loggedInUser);
		}*/
		
		if(!Utils.isEmpty( comments ))
		{
			polComHolder.setComments( comments );
		}
		
		//polComHolder.setPolicyDetails( policyVO );
		
		CaptureCommentsService captureComments =  (CaptureCommentsService) Utils.getBean( "captureComments" );
		captureComments.invokeMethod( "storeComments", polComHolder.getComments() );
	}

	private StringBuilder prepareReasonStringForSoftStop(Long claimCount,Boolean osPrmPresent, Boolean disLoadPresent, Byte brStatus) {
		List<String> reasons = new ArrayList<String>();
		
		if(claimCount > 0){
			reasons.add(Utils.getSingleValueAppConfig("SOFT_STOP_REASON_CLAIM"));
		}
		
		if(osPrmPresent){
			reasons.add(Utils.getSingleValueAppConfig("SOFT_STOP_REASON_OSPRM"));
		}

		if(disLoadPresent){
			reasons.add(Utils.getSingleValueAppConfig("SOFT_STOP_REASON_DISCLOAD"));
		}
		
		if( !Utils.isEmpty(brStatus) && brStatus == SvcConstants.BLOCKED_STATUS){
			reasons.add(Utils.getSingleValueAppConfig("SOFT_STOP_REASON_BLOCKEDBRO"));
		}
		
		StringBuilder sb = new StringBuilder("");
		
		sb.append(reasons.get(ZERO_VAL));
		
		if(reasons.size()>1){
			
			for (int index =1 ;index<reasons.size();index++) {
				
				sb.append(","+reasons.get(index));
				
			}
		}
		return sb;
	}
	
	
	/**
	 * @param baseVO
	 * @return DataHolderVO (having quotation no and linking id)
	 * This method generates the renewal quotation by calling stored procedure
	 * Also find the linking id of the renewal quotation.
	 */
	@Override
	public BaseVO generateOnlineRenewal( BaseVO baseVO ){
		LOGGER.info(" Entering the generateOnlineRenewal method of RenewalsDAO class");	
		@SuppressWarnings( "unchecked" )
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] renData = holderVO.getData();
		Long polLinkingId = (Long)renData[0];
		Integer userID = (Integer)renData[1]; 
		PASStoredProcedure sp =(PASStoredProcedure) Utils.getBean("generateRenewalQuote");	
		Object quoteDetails[] = new Object[3];
		DataHolderVO<Object []> renewalData = new DataHolderVO<Object[]>();
		Long renquoteNo = null;
		try
		{
			LOGGER.debug( "Calling procedure to generate renewal quote." );
			Map<String, Object> results = sp.call( polLinkingId ,POL_POLICY_TYPE ,userID);
			renquoteNo = Long.valueOf(results.get("AO_QUOTE_NO").toString());
		} catch (DataAccessException e){
			throw new BusinessException( "cmn.storedproc.error", e, "Unexpected exception occurred. Please contact Administrator." );
		}
		// Added PolIssueHour =3 for 3.8 Bahrain fix by Vishwa
		TTrnPolicyQuo tTrnPolicy = (TTrnPolicyQuo) getHibernateTemplate().find( "from TTrnPolicyQuo where polQuotationNo= ? and polIssueHour = 3", renquoteNo).get( 0 );
		
		/*
		 * Added code to get linking id of original quotation 
		 * Modified the below query by adding the POL_POLICY_TYPE and Pol_issue_hour check so that query always returns  unique records 
		 * Changed by Vishwa as a part of  3.8 fix.
		 * The below was modified again as this issue existed for Bahrain and pol_linking_id <> 0 by Vishwa.
		 */
		
		Long originalLinkingId = null;	
		String orginalLinkingIdQuery = "select distinct pol_linking_id from t_trn_policy_quo where pol_quotation_no in" +
				"(select pol_quotation_no from t_trn_policy where pol_issue_hour = 3 and pol_policy_type = " + POL_POLICY_TYPE +
				" and pol_linking_id = "+polLinkingId+") and pol_issue_hour = 3  and pol_policy_type = " + POL_POLICY_TYPE +
				" and pol_linking_id <> 0";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(orginalLinkingIdQuery);
		originalLinkingId=Long.valueOf(query.uniqueResult().toString());
		LOGGER.debug( "Linking id of original policy ::" +originalLinkingId);
		quoteDetails[0] = renquoteNo;
		quoteDetails[1] = tTrnPolicy.getPolLinkingId();
		quoteDetails[2] = originalLinkingId;
		
		renewalData.setData( quoteDetails );
		LOGGER.info(" Exiting the generateOnlineRenewal method of RenewalsDAO class");	
		return renewalData;
	}


	/**
	 * @param baseVO
	 * This method updates the premium for renewal quotation
	 */
	@Override
	public void updateRenewalPremium( BaseVO baseVO ){
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] renData = holderVO.getData();
		PolicyVO policyVO = (PolicyVO)renData[0];
		Integer userID = (Integer)renData[1];
		LOGGER.debug( "Inside updateRenewalPremium method for renewal quotation." );
		SectionVO sectionVO = null;
		Long parPolicyId = null;
		List<SectionVO> sectionVOList = null;
		sectionVOList = policyVO.getRiskDetails();
		Iterator<SectionVO> sectionListItr = null;
		if( !Utils.isEmpty( sectionVOList ) ){
			sectionListItr = sectionVOList.iterator();
			while( sectionListItr.hasNext() ){
				sectionVO = (SectionVO) sectionListItr.next();
				LocationVO locationDetails = null;
				//LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = sectionVO.getRiskGroupDetails();
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails.entrySet() ){
					TTrnPremiumQuo existingRecord = null;
					locationDetails = (LocationVO) locationEntry.getKey();
					if( !Utils.isEmpty( sectionVO ) && ( PAR_SECTION_ID == ( sectionVO.getSectionId() ).intValue() ) ){
						ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						parPolicyId = parDetails.getPolicyId();
						if( parDetails.getBuilCovered().intValue() != ZERO_VAL ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( parDetails.getBasicRiskId() ),
									BigDecimal.valueOf( parDetails.getBasicRiskId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
									Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_SUB_TYPE" ) ) );
							
							/**
							 * Oman multibranching: PAR building is not available for Oman.  
							 */
							if(!Utils.isEmpty(parDetails.getBldPremium()) && !Utils.isEmpty(parDetails.getBldPremium().getPremiumAmt()) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( parDetails.getBldPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( parDetails.getBldPremium().getPremiumAmt() ) );
							} else {
								existingRecord.setPrmPremium( BigDecimal.valueOf( 0.0 ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( 0.0 ));
							}
												
							
							update( existingRecord );
						}
						//getHibernateTemplate().evict( existingRecord );
						RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						if( !Utils.isEmpty( parDetails.getCovers() ) && !Utils.isEmpty( parDetails.getCovers().getPropertyCoversDetails() ) ){
							java.util.List<PropertyRiskDetails> propertyCoversDetails = parDetails.getCovers().getPropertyCoversDetails();
							for( PropertyRiskDetails riskDetails : propertyCoversDetails ){
								if( !Utils.isEmpty( riskDetails.getCoverCode() ) && riskDetails.getCoverCode().intValue() != SvcConstants.APP_BASE_COVER_CODE ){
									continue;
								}
								if( !Utils.isEmpty( riskDetails.getCoverId() ) && riskDetails.getCoverId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
									riskDetails.setCoverId( null );
								}
								if( Utils.isEmpty( riskDetails.getCoverId() ) && ( Utils.isEmpty( riskDetails.getCover() ) || riskDetails.getCover() == 0.0 ) ){
									continue;
								}
								existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( riskDetails.getCoverId() ),
										new BigDecimal( locationDetails.getRiskGroupId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
										Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER_SUB_TYPE" ) ) );

								if(policyVO.getIsPrepackaged()){
									if( riskDetails.getRiskType().intValue() == SvcConstants.APP_BASIC_CONTENT_CODE ){
										if( !Utils.isEmpty( rgd.getPremium() ) ){
											existingRecord.setPrmPremium( BigDecimal.valueOf( rgd.getPremium().getPremiumAmt() ) );
											existingRecord.setPrmPremiumActual( BigDecimal.valueOf( rgd.getPremium().getPremiumAmt() ) );
										} else {
											existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
											existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
										}
									} else {
										existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
										existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
									}
								} else { //Flexi
									if( Utils.isEmpty( riskDetails.getPremium() ) ){
										existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
										existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
									}
									else{
										existingRecord.setPrmPremium( BigDecimal.valueOf( riskDetails.getPremium().getPremiumAmt() ) );
										existingRecord.setPrmPremiumActual( BigDecimal.valueOf( riskDetails.getPremium().getPremiumAmt() ) );
									}
								}
								update( existingRecord );
							}
						}
						//RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						List<Contents> ADDTL_COVER_CNT_LIST = constructAddtlCoverCntListForCurrRGD( rgd );
						if( !Utils.isEmpty( ADDTL_COVER_CNT_LIST ) && ADDTL_COVER_CNT_LIST.size() > ZERO_VAL ){
							for( Contents content : ADDTL_COVER_CNT_LIST ){

								if( content.getCoverOpted().intValue() == SvcConstants.APP_ADDITIONAL_COVER_OPTED ){
									existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( parDetails.getBasicRiskId() ),
											BigDecimal.valueOf( parDetails.getBasicRiskId() ), content.getCoverCode().shortValue(), content.getCoverType().shortValue(), content
													.getCoverSubType().shortValue() );
									if( Utils.isEmpty( content.getPremium() ) ){
										existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
										existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf(ZERO_VAL) ) );
									}
									else{
										existingRecord.setPrmPremium( BigDecimal.valueOf( content.getPremium().getPremiumAmt() ) );
										existingRecord.setPrmPremiumActual( BigDecimal.valueOf( content.getPremium().getPremiumAmt() ) );
									}
									update( existingRecord );
								}
							}

						}

					}
					else if( !Utils.isEmpty( sectionVO ) && ( BI_SECTION_ID == ( sectionVO.getSectionId() ).intValue() ) ){
						BIVO biVO = (BIVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, SvcConstants.IS_TABLE_QUERY_HBM, policyVO.getAppFlow(), sectionVO.getPolicyId(),
								BigDecimal.valueOf( biVO.getBasicRiskId() ), BigDecimal.valueOf( biVO.getBasicRiskId() ),BI_PRM_COVER_CODE, BI_COVER_TYPE_CODE, BI_PRM_CST_CODE  );
						if( Utils.isEmpty( biVO.getPremium() ) ){
							existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
						}
						else{
							existingRecord.setPrmPremium( BigDecimal.valueOf( biVO.getPremium().getPremiumAmt() ) );
							existingRecord.setPrmPremiumActual( BigDecimal.valueOf( biVO.getPremium().getPremiumAmt() ) );
						}

						update( existingRecord );
					}
					else if( !Utils.isEmpty( sectionVO ) && ( PL_SECTION_ID == ( sectionVO.getSectionId() ).intValue() ) ){
						PublicLiabilityVO plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						TTrnWctplPremiseQuo premiseQuo = null;
						TTrnBuildingQuo buildingQuo = null;
						if( !Utils.isEmpty( parPolicyId ) ){ // If PAR is the base section
							try{
								buildingQuo = (TTrnBuildingQuo) DAOUtils.getExistingValidStateRecord( "T_TRN_BUILDING_POLICY", policyVO.getAppFlow(), getHibernateTemplate(), false,
										null, Long.valueOf( parPolicyId ), Long.valueOf( locationDetails.getRiskGroupId() ) );
							}
							catch( BusinessException be ){
								/* Not a PAR-added building. Not an exceptional scenario. We have to check for WCTPL Premise now. */
								LOGGER.debug( "BusinessException _1" + be.getMessage()); /* Added logger statement - sonar violation fix */
							}
						}
						/* a. If building record doesn't exist */
						if( !Utils.isEmpty( buildingQuo ) ){
							try{
								premiseQuo = (TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord( "T_TRN_WCTPL_PREMISE_BLDID_RENEWALS", policyVO.getAppFlow(),
										getHibernateTemplate(), false, null, Long.valueOf( sectionVO.getPolicyId() ), Long.valueOf( locationDetails.getRiskGroupId() ) );
							}
							catch( BusinessException e ){
								/* Not yet created in T_TRN_WCTPL_PREMISE(_QUO). */
								LOGGER.debug( "BusinessException" + e.getMessage()); /* Added logger statement - sonar violation fix */
							}

						}
						else{
							try{
								premiseQuo = (TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord( "T_TRN_WCTPL_PREMISE_RENEWALS", policyVO.getAppFlow(),
										getHibernateTemplate(), false, null, Long.valueOf( sectionVO.getPolicyId() ), Long.valueOf( locationDetails.getRiskGroupId() ) );
							}
							catch( BusinessException e ){
								/* Not yet created in T_TRN_WCTPL_PREMISE(_QUO). This should never occur because the riskGroupId is a number. However, this is
								 * being allowed for now to allow other sections to become the base section in future. */
								LOGGER.debug( "BusinessException _3" + e.getMessage()); /* Added logger statement - sonar violation fix */
							}
						}
						existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(),
								BigDecimal.valueOf( Long.valueOf( premiseQuo.getId().getWbdId() ) ), BigDecimal.valueOf( Long.valueOf( premiseQuo.getId().getWbdId() ) ),
								Short.valueOf( "1" ), Short.valueOf( "0" ), Short.valueOf( "0" ) );
						if( Utils.isEmpty( plDetails.getPremium() ) ){
							existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
						}
						else{
							existingRecord.setPrmPremium( BigDecimal.valueOf( plDetails.getPremium().getPremiumAmt() ) );
							existingRecord.setPrmPremiumActual( BigDecimal.valueOf( plDetails.getPremium().getPremiumAmt() ) );
						}
						update( existingRecord );
					}
					else if( !Utils.isEmpty( sectionVO ) && ( EE_SECTION_ID == ( sectionVO.getSectionId() ).intValue() ) ){
						EEVO eeDetails = (EEVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						for( EquipmentVO equipmentVO : eeDetails.getEquipmentDtls() ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( equipmentVO.getContentId() ),
									BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Short.valueOf( Utils.getSingleValueAppConfig( "EE_BASIC_COVER" ) ),
									Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_SUB_TYPE" ) ) );
							if( Utils.isEmpty( equipmentVO.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( equipmentVO.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( equipmentVO.getPremium().getPremiumAmt() ) );
							}

							update( existingRecord );
						}
					}
					else if( !Utils.isEmpty( sectionVO ) && ( WC_SECTION == ( sectionVO.getSectionId() ).intValue() ) ){
						WCVO wcDetails = (WCVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						List<EmpTypeDetailsVO> empTypeDetsList = wcDetails.getEmpTypeDetails();
						for( EmpTypeDetailsVO empTypeDetailsVO : empTypeDetsList ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( empTypeDetailsVO.getRiskId() ),
									BigDecimal.valueOf( empTypeDetailsVO.getRiskId() ), WC_BASIC_COVER, WC_COVER_TYPE, WC_COVER_SUB_TYPE );
							if( Utils.isEmpty( empTypeDetailsVO.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( empTypeDetailsVO.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( empTypeDetailsVO.getPremium().getPremiumAmt() ) );
							}

							update( existingRecord );
						}
					}
					else if( !Utils.isEmpty( sectionVO ) && ( MONEY_SECTION == ( sectionVO.getSectionId() ).intValue() ) ){
						MoneyVO moneyDetails = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						java.util.List<Contents> contentsList = moneyDetails.getContentsList();
						for( Contents content : contentsList ){
							if(Utils.isEmpty( content.getRiskId())){
								continue;
							}
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( content.getRiskId() ),
										BigDecimal.valueOf( moneyDetails.getBasicRiskId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
										Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_COVER_SUB_TYPE" ) ) );
								
							if( Utils.isEmpty( content.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( content.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( content.getPremium().getPremiumAmt() ) );
							}

							update( existingRecord );
						}
					}
					else if( !Utils.isEmpty( sectionVO ) && ( TB_SECTION == ( sectionVO.getSectionId() ).intValue() ) ){
						
						List<TTrnPremiumQuo> tTrnPremiumQuoList = null;			
						TravelBaggageVO tbDetails = (TravelBaggageVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						tTrnPremiumQuoList = (List<TTrnPremiumQuo>) getHibernateTemplate().find( "from TTrnPremiumQuo prm where prm.id.prmPolicyId= ? and prm.id.prmBasicRskId = ? and prm.id.prmRskCode = ? and prm.prmEndtId = ? and prm.prmStatus <> 4 ", sectionVO.getPolicyId(),
								new BigDecimal(locationDetails.getRiskGroupId()),TB_RISK_CODE,Long.valueOf( ZERO_VAL ));
					  existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), Long.valueOf( sectionVO.getPolicyId() ),
							tTrnPremiumQuoList.get( 0 ).getId().getPrmRskId(),BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), 
							TB_BASIC_COVER, TB_COVER_TYPE, TB_COVER_SUB_TYPE );
					  
						  if( Utils.isEmpty( tbDetails.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( tbDetails.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( tbDetails.getPremium().getPremiumAmt() ) );
							}
						  update( existingRecord );
					}
					else if( !Utils.isEmpty( sectionVO ) && ( GIT_SECTION_ID == ( sectionVO.getSectionId() ).intValue() ) ){
						GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						for( CommodityDetailsVO commDetailsVO : goodsInTransitVO.getCommodityDtls() ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( commDetailsVO.getCommodityId() ),
									BigDecimal.valueOf( Long.valueOf( goodsInTransitVO.getDeclarationId() ) ), Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER" ) ),
									Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_SUB_TYPE" ) ) );

							if( Utils.isEmpty( commDetailsVO.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( commDetailsVO.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( commDetailsVO.getPremium().getPremiumAmt() ) );
							}

							update( existingRecord );
						}
					}
					else if( !Utils.isEmpty( sectionVO ) && ( MB_SECTION == ( sectionVO.getSectionId() ).intValue() ) ){
						MBVO mbDetails = (MBVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						java.util.List<MachineDetailsVO> machineryDetails = mbDetails.getMachineryDetails();
						for( MachineDetailsVO machineDetails : machineryDetails ){
							Contents content = machineDetails.getContents();
							if( !Utils.isEmpty( content.getCoverCode() ) && content.getCoverCode().intValue() != SvcConstants.APP_BASE_COVER_CODE ) continue;

							if( !Utils.isEmpty( content.getCoverId() ) && content.getCoverId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
								content.setCoverId( null );
							}
							if( Utils.isEmpty( content.getCoverId() ) && content.getCover().doubleValue() == 0.0 ){
								continue;
							}
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( content.getCoverId() ),
									BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
									Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_TYPE ) ), Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_SUB_TYPE ) ) );
							if( Utils.isEmpty( machineDetails.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( machineDetails.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( machineDetails.getPremium().getPremiumAmt() ) );
							}
							update( existingRecord );
						}
					}
					else if( !Utils.isEmpty( sectionVO ) && ( GPA_SECTION == ( sectionVO.getSectionId() ).intValue() ) ){
						GroupPersonalAccidentVO groupPersonalAccidentVO = (GroupPersonalAccidentVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						java.util.List<GPAUnnammedEmpVO> unnammedEmployeeVO = groupPersonalAccidentVO.getGpaUnnammedEmpVO();
						for( GPAUnnammedEmpVO unnammedEmployeeVOrec : unnammedEmployeeVO ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), new BigDecimal( unnammedEmployeeVOrec.getGupId() ),
									new BigDecimal(locationDetails.getRiskGroupId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
									GROUP_PERSONAL_ACCIDENT_COVER_TYPE, GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE );

							if( Utils.isEmpty( unnammedEmployeeVOrec.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( unnammedEmployeeVOrec.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( unnammedEmployeeVOrec.getPremium().getPremiumAmt() ) );
							}
							update( existingRecord );

						}
						java.util.List<GPANammedEmpVO> nammedEmployeeVO = groupPersonalAccidentVO.getGpaNammedEmpVO();

						for( GPANammedEmpVO nammedEmployeeVOrec : nammedEmployeeVO ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), new BigDecimal( nammedEmployeeVOrec.getGprId() ),
									new BigDecimal( locationDetails.getRiskGroupId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
									GROUP_PERSONAL_ACCIDENT_COVER_TYPE, GROUP_PERSONAL_ACCIDENT_COVER_SUB_TYPE );

							if( Utils.isEmpty( nammedEmployeeVOrec.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( nammedEmployeeVOrec.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( nammedEmployeeVOrec.getPremium().getPremiumAmt() ) );
							}
							update( existingRecord );
						}
					}
					else if( !Utils.isEmpty( sectionVO ) && ( DOS_SECTION == ( sectionVO.getSectionId() ).intValue() ) ){
						DeteriorationOfStockVO dosVO = (DeteriorationOfStockVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						for( DeteriorationOfStockDetailsVO stockDetailVO : dosVO.getDeteriorationOfStockDetails() ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( stockDetailVO.getContentId() ),
									BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Short.valueOf( Utils.getSingleValueAppConfig( "DOS_BASIC_COVER" ) ),
									Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_SUB_TYPE" ) ) );
							if( Utils.isEmpty( stockDetailVO.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( stockDetailVO.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( stockDetailVO.getPremium().getPremiumAmt() ) );
							}
							update( existingRecord );
						}
					}
					else if( !Utils.isEmpty( sectionVO ) && ( FIDELITY_SECTION == ( sectionVO.getSectionId() ).intValue() ) ){
						FidelityVO fidelityVO = (FidelityVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
						List<FidelityUnnammedEmployeeVO> unnammedEmployeeList = fidelityVO.getUnnammedEmployeeDetails();
						for( FidelityUnnammedEmployeeVO unnammedEmployeeVO : unnammedEmployeeList ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(),
									BigDecimal.valueOf( unnammedEmployeeVO.getGupFidelityId() ), BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ),
									 Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE )
											.shortValue(), FIDELITY_COVER_TYPE.shortValue(), FIDELITY_COVER_SUB_TYPE.shortValue() );
							
							if( Utils.isEmpty( unnammedEmployeeVO.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( unnammedEmployeeVO.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( unnammedEmployeeVO.getPremium().getPremiumAmt() ) );
							}
							
							update( existingRecord );
						}

						java.util.List<FidelityNammedEmployeeDetailsVO> nammedEmployeeDetailsVO = fidelityVO.getFidelityEmployeeDetails();
						for( FidelityNammedEmployeeDetailsVO employeeDetailsVO : nammedEmployeeDetailsVO ){
							existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM, false, policyVO.getAppFlow(), sectionVO.getPolicyId(), BigDecimal.valueOf( employeeDetailsVO.getGprFidelityId() ),
									BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
									FIDELITY_COVER_TYPE.shortValue(), FIDELITY_COVER_SUB_TYPE.shortValue() );
								
							if( Utils.isEmpty( employeeDetailsVO.getPremium() ) ){
								existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							}
							else{
								existingRecord.setPrmPremium( BigDecimal.valueOf( employeeDetailsVO.getPremium().getPremiumAmt() ) );
								existingRecord.setPrmPremiumActual( BigDecimal.valueOf( employeeDetailsVO.getPremium().getPremiumAmt() ) );
							}
							
							update( existingRecord );
						}
						
						// Aggregate limit premium
						existingRecord = handlePremiumRecord(SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE ,false, policyVO.getAppFlow(), sectionVO.getPolicyId(),
								BigDecimal.valueOf( SvcConstants.FID_BASIC_RISK_CODE_FOR_AGGREGATE ),
								BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
								FIDELITY_COVER_TYPE.shortValue(), FIDELITY_COVER_SUB_TYPE.shortValue() );				

						if(!Utils.isEmpty(fidelityVO.getFidAggregateBasePremium())){
							existingRecord.setPrmPremium( new BigDecimal( fidelityVO.getFidAggregateBasePremium()));
							existingRecord.setPrmPremiumActual( new BigDecimal( fidelityVO.getFidAggregateBasePremium()));
						}else{
							existingRecord.setPrmPremium( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
							existingRecord.setPrmPremiumActual( BigDecimal.valueOf( Long.valueOf( ZERO_VAL ) ) );
						}
						update( existingRecord );
					}

				}
				//Update discount/loading values.
				updateSpecialCoverPrm(sectionVO.getPolicyId(), sectionVO.getClassCode(), policyVO);
			}
		}
		// To update the premium in the policy table
		getHibernateTemplate().flush();
		updatePolicy( policyVO,userID );
		LOGGER.debug( "Exiting updateRenewalPremium method for renewal quotation." );
	}

	/**
	 * Update the discount/loading premium values
	 * @param policyId
	 * @param classCode
	 * @param policyVO
	 */
	private void updateSpecialCoverPrm( Long policyId, Integer classCode, PolicyVO policyVO ){
		
		TTrnPolicyQuo policyQuo = new TTrnPolicyQuo();	
		TTrnPolicyQuoId id = new TTrnPolicyQuoId();
		policyQuo.setId( id );
		policyQuo.getId().setPolPolicyId( policyId );
		policyQuo.getId().setPolEndtId( policyVO.getEndtId() );
		policyQuo.setPolClassCode( Short.valueOf( classCode.toString() ));		
		PremiumHelper.updateSpecialCovers( policyVO, policyQuo, getHibernateTemplate() );
		
	}

	/**
	 * @param <T>
	 * @param sql
	 * @param flow
	 * @param tableKeys
	 * This method gets the existing valid state record from t_trn_premium table depending on arguments passed in tableKeyes
	 * @return
	 */
	protected <T extends POJO> T handlePremiumRecord(String tableId, boolean sql, Flow flow, Object... tableKeys ){
		POJO existingRecord = DAOUtils.getExistingValidStateRecord( tableId, flow, getHibernateTemplate(), sql, null, tableKeys );
		return (T) existingRecord;
	}

	/**
	 * @param policyVO
	 * This method updates t_trn_policy table for renewal quote with policy premium and other details
	 */
	private void updatePolicy( PolicyVO policyVO,Integer userID ){
		// While generating the renewal quotation status is set as Active
		Integer polStatus = QUOTE_ACTIVE;
		//PolicyCommentsHolder polComHolder = (PolicyCommentsHolder) baseVO;
		//PolicyVO policyVO = polComHolder.getPolicyDetails();

		Long endtId = SvcUtils.getLatestEndtId( policyVO );
		// Added PolIssueHour for 3.8 Bahrain fix by Vishwa
		List<TTrnPolicyQuo> policyQuoList = getHibernateTemplate()
				.find( "from TTrnPolicyQuo policyQuo where policyQuo.polLinkingId=? and policyQuo.polIssueHour = 3 and (policyQuo.id.polEndtId=? or (policyQuo.id.polEndtId<? and policyQuo.polValidityExpiryDate=?)) and policyQuo.polPolicyType=?",
						policyVO.getPolLinkingId(), endtId, endtId, SvcConstants.EXP_DATE, Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ) );

		Double policyFees = ( policyVO.getPremiumVO().getPolicyFees() ) / policyQuoList.size();

		com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

		for( TTrnPolicyQuo policyQuo : policyQuoList ){
			policyQuo.setPolGovernmentTax( converter.getAFromB( policyVO.getPremiumVO().getGovtTax() ) );
			policyQuo.setPolPolicyFees( converter.getAFromB( policyFees ) );
			//policyQuo.setPolModifiedBy( userID );
			policyQuo.setPolApprovedInd( "Y" ); // The status of the quote is approved and the indicator is set to Y
			policyQuo.setPolBusinessType( (short) 0 );
			//Closing date :
			//policyQuo.setPolModifiedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
			policyQuo.setPolModifiedDt( getSysDate() );

			policyQuo.setPolPrintDate( null );

			/* If Quote status is active then we need not update Approved By,Licensed By id's and approved date */
			if( !( !Utils.isEmpty( policyQuo.getPolStatus()) && policyQuo.getPolStatus() == QUOTE_ACTIVE ) && !Utils.isEmpty( userID ) ){
				policyQuo.setPolApprovedBy( userID );
				policyQuo.setPolUserId( userID );
				//policyQuo.setPolApprovalDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
				policyQuo.setPolApprovalDate( getSysDate() );
			}

			/*
			 * POL_PREMIUM needs to be updated with class level premium. This method will fetch the premium for a class from premium table and update the 
			 * policy table 
			 */
			//Oman rollout - to calculate Govt tax and discount/loading
			PremiumHelper.updateSpecialCovers(policyVO,policyQuo,getHibernateTemplate());
			PremiumHelper.updateGovtTax( policyVO, policyQuo, getHibernateTemplate() );
			
			
			/*VAT:Batch Renewal SBS*/
			if(Utils.isEmpty(policyQuo.getPolvatCode()) || 0==policyQuo.getPolvatCode()){
				/*
				 * default code for SBS
				 */
				policyVO.setPolVATCode(DAOUtils.checkVATDefaultCode());
				policyQuo.setPolvatCode(policyVO.getPolVATCode());
				policyQuo.setPolVatTaxPerc(DAOUtils.getVATRateSBS(policyVO.getPolVATCode(), null));
			}
			PremiumHelper.updateVatTax(policyVO, policyQuo, getHibernateTemplate());
			if( polStatus == QUOTE_ACTIVE ){
				PremiumHelper.updatePolicyPremium( policyVO, policyQuo, getHibernateTemplate() );
			}

			com.rsaame.pas.cmn.converter.IntegerByteConverter byteConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			//policyQuo.setPolStatus( byteConverter.getBFromA( polStatus ) );
			getHibernateTemplate().merge( policyQuo );

			LOGGER.debug( "Policy Id :" + policyQuo.getId().getPolPolicyId() );
		}
	}

	/* 
	 *  This method updates the policy status as Soft Stop, in case of claims
	 */
	@Override
	public void updateQuotationStatus( BaseVO baseVO ){
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		PolicyVO policyVO = (PolicyVO)data[0];
		String status = (String)data[1]; 
		//PolicyVO policyVO = (PolicyVO) baseVO;
		// Added PolIssueHour for 3.8 Bahrain fix by Vishwa
		List<TTrnPolicyQuo> policyQuoList = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polLinkingId=? and ttrnPol.id.polEndtId=? and ttrnPol.polIssueHour = 3",
				policyVO.getPolLinkingId(), SvcUtils.getLatestEndtId( policyVO ) );
		if( !policyQuoList.isEmpty() ){
			for( TTrnPolicyQuo policyQuo : policyQuoList ){
				policyQuo.setPolStatus( Byte.valueOf( status) );
				getHibernateTemplate().update( policyQuo );
				LOGGER.debug( "Update status to softstop :"+status +" for policy" );
			}
			getHibernateTemplate().flush();
		}

	}

	/* 
	 *  This method updates the policy renewal terms if there are no claims/Outstanding premium
	 */
	public void updateRenewalTerms( BaseVO baseVO ){
		@SuppressWarnings("unchecked")
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		PolicyVO policyVO = (PolicyVO)data[0];
		String renewalTerms = (String)data[1]; 
		//PolicyVO policyVO = (PolicyVO) baseVO;
		@SuppressWarnings("unchecked")
		List<TTrnPolicyQuo> policyQuoList = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polLinkingId=? and ttrnPol.id.polEndtId=? and ttrnPol.polIssueHour = 3",
				policyVO.getPolLinkingId(), SvcUtils.getLatestEndtId( policyVO ) );
		if( !policyQuoList.isEmpty() ){
			for( TTrnPolicyQuo policyQuo : policyQuoList ){
				policyQuo.setPolRenTermTxt(renewalTerms);
				if(!Utils.isEmpty(policyVO.getRenewalBasis())){
					policyQuo.setPolRenewalBasis(policyVO.getRenewalBasis());
				}else if(policyVO.getStatus() == Integer.valueOf(Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP"))){
					policyQuo.setPolRenewalBasis(null);
				}
				//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
				getHibernateTemplate().merge( policyQuo );
			}
			getHibernateTemplate().flush();
		}

	}
	
	private boolean isWorkingLimitTobeUpdated( BIVO biVO ){
		boolean isTobeUpdated = false;
		if( !Utils.isEmpty( biVO.getWorkingLimit() ) ){
			isTobeUpdated = true;
		}
		else if( !Utils.isEmpty( biVO.getBiCwsAcwlId() ) ){
			isTobeUpdated = true;
		}

		return isTobeUpdated;
	}

	private boolean isRentReceivableTobeUpdated( BIVO biVO ){
		boolean isTobeUpdated = false;
		if( !Utils.isEmpty( biVO.getRentRecievable() ) ){
			isTobeUpdated = true;
		}
		else if( !Utils.isEmpty( biVO.getBiCwsRentId() ) ){
			isTobeUpdated = true;
		}

		return isTobeUpdated;
	}

	/**
	 * @param rgd
	 * @return
	 * This method constructs the list of additional covers if any
	 */
	public com.mindtree.ruc.cmn.utils.List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails rgd ){

		List<Contents> ADDTL_COVERS_LIST = new com.mindtree.ruc.cmn.utils.List<Contents>( Contents.class );
		ParVO rgdOfPAR = (ParVO) rgd;

		if( !Utils.isEmpty( ( rgdOfPAR ) ) && !Utils.isEmpty( rgdOfPAR.getCovers().getPropertyCoversDetails() ) ){
			java.util.List<PropertyRiskDetails> propertyCoversDetails = rgdOfPAR.getCovers().getPropertyCoversDetails();

			for( PropertyRiskDetails riskDetails : propertyCoversDetails ){

				if( !Utils.isEmpty( riskDetails.getCoverCode() ) && riskDetails.getCoverCode().intValue() == SvcConstants.APP_BASE_COVER_CODE ) continue;

				if( !Utils.isEmpty( riskDetails.getCoverId() ) && riskDetails.getCoverId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
					riskDetails.setCoverId( null );
				}
				if( Utils.isEmpty( riskDetails.getCoverCode() ) ) continue;
				//Radar fix
				Contents content = null; //new Contents();
				content = BeanMapper.map( riskDetails, Contents.class );
				ADDTL_COVERS_LIST.add( content );
			}
		}
		return (com.mindtree.ruc.cmn.utils.List<Contents>) ADDTL_COVERS_LIST;
	}

	/**
	 * @param rgd
	 * @return
	 * This method Call the stored procedure to get the claim count
	*/
	@Override
	public BaseVO getClaimCount( BaseVO baseVO ) {

		@SuppressWarnings( "unchecked" )
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] claimData = holderVO.getData();
		Long linkingId = (Long) claimData[ 0 ];			
		Long renQuote = (Long) claimData[ 1 ];
		LOGGER.info("Fetching the claim count on the policy.");
		/*
		 *  While converting the renewal quote to policy we have to get the linking id of original policy using the renewal quote number
		 *  Added the PolIssueHour to below query for 3.8 Bahrain fix by Vishwa
		 */
		if(!Utils.isEmpty( renQuote )){
			String linkingIdQuery = "select distinct pol_linking_id from t_trn_policy where pol_issue_hour = 3 and pol_policy_id in(select pol_ref_policy_id from t_trn_policy_quo where pol_issue_hour = 3 and pol_quotation_no = "+renQuote+")";
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(linkingIdQuery);
			linkingId=Long.valueOf(query.uniqueResult().toString());
		}	
		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "getClaimCount" );
		Long claimCount = null;
		try{
			Map<String, Object> results = sp.call( linkingId, renQuote );
			claimCount = Long.valueOf( results.get( "AO_CLAIM_COUNT" ).toString() );
			LOGGER.debug("Claim count on policy:: "+claimCount);
		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.storedproc.error", e, "Unexpected exception occurred. Please contact Administrator." );
		}
		DataHolderVO<Long> claimOutput = new DataHolderVO<Long>();
		claimOutput.setData( claimCount );
		LOGGER.info("Exiting the claim count on the policy.");
		return claimOutput;

	}

	/**
	 * This method will fetch the Policies from Renewal Batch table.
	 * 
	 * @return <List>renewalBatchList </List>
	 * @throws <code>DataAccessException</code>
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRenewalBatch() throws BusinessException {
		List<TTrnRenewalBatchEplatform> renewalBatchList = null;
		try {
			LOGGER.debug("Begin getRenewalBatch  of RenewalsDAO class" );
			renewalBatchList = getHibernateTemplate().find("from TTrnRenewalBatchEplatform renewalBatch where renewalBatch.renQuotationStatus <> 'Y'");
			LOGGER.debug("renewalBatchList :"+renewalBatchList);
			LOGGER.debug("Exit getRenewalBatch  of RenewalsDAO class");
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("pas.renewal.exceptionInDataFetch",	null, "Exception Occurred while fetching the dat_2");
		}
		return renewalBatchList;
	}
	
	/**
	 * This method will fetch the Policies from Renewal Batch table.
	 * 
	 * @return <List>renewalBatchList </List>
	 * @throws <code>DataAccessException</code>
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TTrnPrintBatchPas> getRenewalBatchPrint() throws BusinessException {
		List<TTrnPrintBatchPas> tTrnPrintBatchPas = null;
		try {
			LOGGER.debug("Begin getRenewalBatchPrint() of RenewalsDAO class" );
			tTrnPrintBatchPas = getHibernateTemplate().find("from TTrnPrintBatchPas printBatchPas where printBatchPas.prnStatus <> 'Y' ");
			LOGGER.debug("getRenewalBatchPrint List :"+tTrnPrintBatchPas);
			LOGGER.debug("Exit getRenewalBatchPrint() of RenewalsDAO class");
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("pas.renewal.exceptionInDataFetch",	null, "Exception Occurred while fetching the dat_3");
		}
		return tTrnPrintBatchPas;
	}
	
	@Override
	public void updateBatchPrintStatus(PrintDocVO printDocVo){
		HashMap <String, String> docParam = printDocVo.getDocParameter();
		List<TTrnPrintBatchPas> printBatchList = null;
		
		if(docParam.containsKey("polLinkingId")){
			printBatchList= getHibernateTemplate().find( "from TTrnPrintBatchPas batch where batch.id.polLinkingId=? and batch.polEndtId=? and batch.prnStatus = 'N'",
					Long.parseLong( docParam.get("polLinkingId")), Long.parseLong( docParam.get("endoresementId") ));
		}
		/*else if(docParam.containsKey("policyId")){
			printBatchList= getHibernateTemplate().find( "from TTrnPrintBatchPas batch where batch.id.policyId=? and batch.polEndtId=? and batch.prnStatus = 'N'",
					Long.parseLong( docParam.get("policyId")), Long.parseLong( docParam.get("endoresementId") ));
		}		*/
		else if(docParam.containsKey("quoteNo")){
			printBatchList= getHibernateTemplate().find( "from TTrnPrintBatchPas batch where batch.renQuoNo=? and batch.prnStatus = 'N'",
					Long.parseLong( docParam.get("quoteNo") ) );
		}
		if(!Utils.isEmpty( printBatchList ))
		for(TTrnPrintBatchPas printBatch:printBatchList){
			printBatch.setPrnStatus( "Y" );
			getHibernateTemplate().merge( printBatch );
		}
		
	/*	try{
			String GET_SECTION_QUERY = "UPDATE T_TRN_PRINT_BATCH_PAS SET PRN_STATUS ='Y' where POL_LINKING_ID =? " +
			"and POL_ENDT_ID=? " ;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query sqlQuery = session.createSQLQuery(GET_SECTION_QUERY);
			sqlQuery.setParameter(0, Integer.parseInt(docParam.get("polLinkingId")));
			sqlQuery.setParameter(1, Integer.parseInt(docParam.get("endoresementId")));
			
			logger.debug("polValStartDate:"+ docParam.get("polValStartDate"));
			//sqlQuery.setParameter(3, "TO_DATE('"+preprdDate+"','YYYY-MON-dd HH24:MI:SS')");
			//sqlQuery.setParameter(3, preprdDate);
			
			logger.debug("sqlQuery.getQueryString()"+sqlQuery.getQueryString());
			int result = sqlQuery.executeUpdate();
			logger.debug("Updated PRN_STATUS for REM_SRL_NO:"+ docParam.get("polLinkingId") +"to N");
			}catch(Exception e){
				logger.error("Error in updating reminder:"+e.getMessage());
			}*/

	}
	
	@Override
	public BaseVO getEndorsementData( BaseVO baseVO ){
		java.util.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>(EndorsmentVO.class);
		DataHolderVO<List<EndorsmentVO>>endorsementData = new DataHolderVO<List<EndorsmentVO>>();
		EndorsmentVO endorsmentVO = null;
		
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long renQuoteNo  =holderVO.getData();
		// Added PolIssueHour for 3.8 Bahrain fix by Vishwa
		TTrnPolicyQuo policyQuo = (TTrnPolicyQuo) getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polQuotationNo=? and ttrnPol.polIssueHour = 3",renQuoteNo ).get( 0 );
		
		//String policySql = "select count(1) from t_trn_policy where To_Char(Pol_Modified_Dt,'dd-MON-yyyy hh:mi:ss') > to_char("+
		//	policyQuo.getPolModifiedDt()+", 'dd-MON-yyyy hh:mi:ss') and pol_validity_expiry_date ='31-DEC-49' and  pol_policy_no ="+policyQuo.getPolPolicyNo();
		
		/*String policySql = "select EDL_TEXT from T_TRN_ENDORSEMENT_DETAIL where(edl_policy_id, edl_endorsement_id) in (" +
		"select pol_policy_id,Pol_Endt_Id from t_trn_policy where To_Char(Pol_Modified_Dt,'dd-MON-yyyy hh:mi:ss') > "+
		"(select To_Char(pol_prepared_dt ,'dd-MON-yyyy hh:mi:ss') from t_trn_policy_quo where pol_quotation_no = "+renQuoteNo+" and pol_endt_id =0 and rownum=1)"+
		// Oman Fix: Added pol issue hour check
	     " and  pol_policy_no ="+policyQuo.getPolPolicyNo()+" and pol_status =1 and pol_issue_hour = "+Utils.getSingleValueAppConfig("SBS_POLICY_ISSUE_HOUR")+" ) order by edl_endorsement_id";*/
		
		/*
		 * Adding is not null condition in query 
		 * as fix for 96449 service id
		 * */
		String policySql = "Select Edl_Text From T_Trn_Endorsement_Detail Where(Edl_Policy_Id, Edl_Endorsement_Id) "+
		"In (Select max(Pol_Policy_Id),Pol_Endt_Id From T_Trn_Policy Where To_Timestamp(To_Char(Pol_Modified_Dt,'dd-MON-yyyy hh24:mi:ss'),'dd-MON-yyyy hh24:mi:ss') > "+
		"(select to_Timestamp(To_Char(pol_prepared_dt ,'dd-MON-yyyy hh24:mi:ss'),'dd-MON-yyyy hh24:mi:ss') from t_trn_policy_quo where pol_quotation_no = "
		+renQuoteNo+" and pol_endt_id =0 and rownum=1) and pol_policy_no = "+policyQuo.getPolPolicyNo()+"  and pol_status =1 and pol_issue_hour = "
		+Utils.getSingleValueAppConfig("SBS_POLICY_ISSUE_HOUR")+" group by Pol_Endt_Id ) and edl_text is not null Order By Edl_Endorsement_Id";

		
	    Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(policySql);
		List<Object> result = query.list();
		if( !Utils.isEmpty( result ) ){
			Iterator<Object> itr = result.iterator();
			while(itr.hasNext()){
				endorsmentVO = new EndorsmentVO();
				endorsmentVO.setEndText(itr.next().toString());
				endorsements.add(endorsmentVO );
			}
			endorsementData.setData( endorsements );
			
		}
		return endorsementData;
		
	}
	
	@Override
	public BaseVO getOSPremium( BaseVO baseVO ){ 
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long renQuoteNo  =holderVO.getData();
		BigDecimal osPremiumSum ;
		LOGGER.debug("Fetching the OS premium for policy");
		//String osPremiumQuery = "select sum(sum_amount) outstanding_premium from t_trn_gl_unmatched_aic ,t_trn_policy where t_trn_gl_unmatched_aic.ref_tran_type in (1,2) and "+
		//"t_trn_gl_unmatched_aic.ref_tran_id=t_trn_policy.pol_ref_policy_id and t_trn_policy.pol_policy_id in(select pol_ref_policy_id from t_trn_policy_quo where pol_quotation_no = " +renQuoteNo+")";

		/*
		 * Modified the below query by adding Pol_issue_hour check
		 * Changed by Vishwa as a part of  3.8 fix.
		 */
		String osPremiumQuery = "select sum(sum_amount) outstanding_premium from t_trn_gl_unmatched_aic where "
		+"t_trn_gl_unmatched_aic.ref_tran_type in (1,2) and t_trn_gl_unmatched_aic.policy_id in ("
		+"select pol_ref_policy_id from t_Trn_policy_quo where pol_issue_hour = 3 and pol_quotation_no = "+renQuoteNo+")";
		 Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(osPremiumQuery);
			osPremiumSum=(BigDecimal)query.uniqueResult();
			DataHolderVO<BigDecimal> prmVO =  new DataHolderVO<BigDecimal>();
			LOGGER.debug("OS premium for policy is::"+osPremiumSum);
			prmVO.setData( osPremiumSum );
			return prmVO;
		}
	/*
	 * (non-Javadoc)
	 * @see com.rsaame.pas.renewals.dao.IRenewalsDAO#getDisLoadPer(com.mindtree.ruc.cmn.base.BaseVO)
	 * Method to check if discount loading percentage >= 30%
	 */
	@Override
	public BaseVO getDisLoadPer( BaseVO baseVO ){ 
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long renQuoteNo  =holderVO.getData();
		BigDecimal discountPer = BigDecimal.ZERO;
		BigDecimal LoadingPer = BigDecimal.ZERO;
		
		
		/*String disLoadPerQuery = "select max(abs(prm_rate)), prm_cov_code from t_trn_premium where prm_policy_id in " +
				 "(select pol_policy_id from t_trn_policy where pol_linking_id= "+ polForRenLinkingId +")" +
				 "and prm_cov_code in ("+SvcConstants.SC_PRM_COVER_DISCOUNT+","+SvcConstants.SC_PRM_COVER_LOADING+
				 ") and trunc(prm_validity_expiry_date) = trunc(to_date('31-DEC-2049','dd-MON-yyyy'))"+
				 " group by prm_cov_code;";*/
		/*
		 * Modified the below query by adding Pol_issue_hour check
		 * Changed by Vishwa as a part of  3.8 fix.
		 */
		String disLoadPerQuery = "select max(abs(prm_rate)), prm_cov_code from t_trn_premium where prm_policy_id in " +
				"(select pol_ref_policy_id from t_Trn_policy_quo where  pol_issue_hour = 3 and pol_quotation_no = "+renQuoteNo+")"+
				"and prm_cov_code in ("+SvcConstants.SC_PRM_COVER_DISCOUNT+","+SvcConstants.SC_PRM_COVER_LOADING+")"+
				" and trunc(prm_validity_expiry_date) = trunc(to_date('31-DEC-2049','dd-MON-yyyy'))"+
				" group by prm_cov_code";
		
		 Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		 Query query = session.createSQLQuery(disLoadPerQuery);
		 List<Object> result = query.list();
		 if( !Utils.isEmpty( result ) ){
			 Iterator<Object> itr = result.iterator();
				while(itr.hasNext()){
					Object[] prmRecord =  (Object[]) itr.next();
					/*
					 * if cover code is 51 i.e Discount then set discount percentage or else set loading percentage 
					 */
					if(prmRecord[1].toString().equals(SvcConstants.SC_PRM_COVER_DISCOUNT.toString())){
						discountPer =  (BigDecimal) prmRecord[0];
					}else {
						LoadingPer = (BigDecimal) prmRecord[0];
					}
					
				}
		 }
			DataHolderVO<Map<String,BigDecimal>> disLoadPer=  new DataHolderVO<Map<String,BigDecimal>>();
			Map<String,BigDecimal> percentages = new HashMap<String,BigDecimal>();
			
			percentages.put(SvcConstants.DISCOUNT_PER,discountPer );
			percentages.put(SvcConstants.LOADING_PER,LoadingPer );
			
			disLoadPer.setData(percentages);
			return disLoadPer;
		}

	/*
	 * (non-Javadoc)
	 * @see com.rsaame.pas.renewals.dao.IRenewalsDAO#getDisLoadPer(com.mindtree.ruc.cmn.base.BaseVO)
	 * Method to check if broker account is blocked.
	 */
	@Override
	public BaseVO getBrAccStatus( BaseVO baseVO ){ 
		DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long renQuoteNo  =(Long)holderVO.getData();
		LOGGER.debug("Fetching broker account status of the RenewalsDAO class.");
		/*
		 * Modified the below query by adding Pol_issue_hour check so that query always returns  broker code records 
		 * Changed by Vishwa as a part of  3.8 fix.
		 */
		
		/*String brAccStatusQuery = "select distinct b.br_status  from t_mas_broker b,t_trn_policy p where p.pol_br_code = b.br_code " +
				"and p.pol_policy_no = (select distinct quo.pol_ref_policy_no from t_trn_policy_quo quo " +
				"where quo.pol_quotation_no = "+renQuoteNo+" and  quo.pol_issue_hour = 3 ) and p.pol_issue_hour = 3 and p.pol_validity_expiry_date = to_date('31-DEC-2049','DD-MON-YYYY')"; */
		
		//changed to fix defect id #139969-reverted as suggested by satadal
		String brAccStatusQuery = "select distinct b.br_status  from t_mas_broker b,t_trn_policy_quo p where p.pol_br_code = b.br_code and p.pol_quotation_no = "+renQuoteNo+"  and p.pol_issue_hour = 3 and p.pol_validity_expiry_date = to_date('31-DEC-2049','DD-MON-YYYY')";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(brAccStatusQuery);
		Byte brAccStatus = null;
		if(!Utils.isEmpty(query.uniqueResult())){
			brAccStatus = (( BigDecimal )query.uniqueResult()).byteValue();
		}
		LOGGER.debug("FBroker account status of the RenewalsDAO class is ::: "+brAccStatus);
	    DataHolderVO<Byte> statusVO =  new DataHolderVO<Byte>();
	    statusVO.setData( brAccStatus );
		return statusVO;
	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void savePoliciesForBatchPrint( BaseVO baseVO ){
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		Long[] linkingIdList = (Long[]) data[1];
		TTrnPrintBatchPas[] quoteForPrint = (TTrnPrintBatchPas[]) data[0];
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(
		"select batch.id.polLinkingId from TTrnPrintBatchPas batch where batch.id.polLinkingId in (:IdList) and batch.prnStatus = 'N'" );
		query.setParameterList("IdList", linkingIdList, new LongType());
		
		List<Long> printList = query.list();
		
		long printBatchId = NextSequenceValue.getNextSequence( PRINT_BATCH_ID, null,null, getHibernateTemplate() );
		for (TTrnPrintBatchPas quote : quoteForPrint) {
			if(!printList.contains(quote.getId().getPolLinkingId())){
			quote.getId().setPrnBatchReqId( printBatchId );
			quote.setRequesterId(ServiceContext.getUser().getUserId());
			quote.setRequestDate(new Date());
			quote.setPrnStatus( "N" );
			quote.setPrnLocation( SvcUtils.getLookUpCodeForLOneLTwo(PRINT_LOC,quote.getPrnLocation(), "ALL").toString());// Set the prn_loc
			getHibernateTemplate().save(quote );
			}
			}

	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public void updatePrintRecord(BaseVO baseVO) {
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		Long linkingId = (Long) data[0];
		Long newQuoteNo = (Long) data[1];
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from TTrnPrintBatchPas batch where batch.id.polLinkingId =:linkingId and batch.prnStatus = 'N'");
		query.setParameter("linkingId", linkingId);
		List<TTrnPrintBatchPas> printList = query.list();
		for (TTrnPrintBatchPas quote : printList) {
			quote.setRenQuoNo(newQuoteNo);
			getHibernateTemplate().merge(quote);
		}
	}
	
	 
	@SuppressWarnings("unchecked")
	@Override
	public BaseVO checkForReprint( BaseVO baseVO ){

		DataHolderVO<Object> holderVO = (DataHolderVO<Object>) baseVO;
		Long[] linkingIdList = (Long[]) holderVO.getData();
		//Long[] linkingIdList = (Long[]) data[0];
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		//List<TTrnPrintBatchPas> printList = session.createQuery(
		//"from TTrnPrintBatchPas batch where batch.id.polLinkingId in (:IdList) and batch.prnStatus = :prnSatus" )
		//.setParameterList("IdList", linkingIdList, new LongType()).setParameter("prnSatus", "Y" ).list();
		//String status = "Y";
		Query query = session.createQuery(
		//"from TTrnPrintBatchPas batch where batch.id.polLinkingId in (:IdList) and batch.prnStatus= :prnSatus" );
		"from TTrnPrintBatchPas batch where batch.id.polLinkingId in (:IdList) and batch.prnStatus = 'Y' " );
		query.setParameterList("IdList", linkingIdList, new LongType());
		//query.setParameter( "prnSatus", status );
		List<TTrnPrintBatchPas> printList = query.list();
		DataHolderVO<Boolean> prnStatus = new DataHolderVO<Boolean>();
		if(!Utils.isEmpty( printList )){
			prnStatus.setData( new Boolean(true)) ;
		} else{
			prnStatus.setData( new Boolean(false)) ;
		}
		return prnStatus;
	}

	/**
	 * @param criteria
	 * @return BaseVO
	 * This method returns the list of policies based on search criteria along with quotation numbers generated.
	 */
	@Override
	public BaseVO getRenewalQuotations( BaseVO criteria ){
		PrintRenewalsSearchCriteriaVO renewalsVO = (PrintRenewalsSearchCriteriaVO) criteria;
		Session session = null;
		List<VTrnRenewalQuotationsSbs> renPolicies = null; // task
		List<RenewalResultsVO> resultList = new com.mindtree.ruc.cmn.utils.List<RenewalResultsVO>( RenewalResultsVO.class ); //tasklist
		
		LOGGER.info( "************ Entering getRenewalQuotations ******************"  );
		try{
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			StringBuffer queryString = new StringBuffer( 128 );
			// CALCULATE THE DATES FOR THE DAYS RANGE
			// ADD THE BASE QUERY
			queryString.append( QRY_RENEWAL_QUOTATONS_SEARCH_BASE );
			boolean appendWhere = true;
			LOGGER.debug( "queryString 1:" + queryString.toString() );
			if( renewalsVO.getInsuredName() != null ){
				queryString.append( QRY_INSURED + PRINT_QRY_BASIC_CONDITION + PRINT_QRY_BASIC_CONDITION1 );
				appendWhere = false;
			}
			Map<String, Object> paramsMap = new HashMap<String, Object>();

			if( renewalsVO.getClazz() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_CLASS_CODE + QRY_PRT_EQUL + PARAM_CLASS_CODE );
				paramsMap.put( PARAM_CLASS_CODE, Short.parseShort( renewalsVO.getClazz() ) );
			}
			LOGGER.debug( "queryString 2:" + queryString.toString() );
			if( renewalsVO.getPolicyNo() != null ){

				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_NUM + QRY_PRT_EQUL + PARAM_POL_NUM );
				paramsMap.put( PARAM_POL_NUM, Long.parseLong( renewalsVO.getPolicyNo() ) );

			}
			LOGGER.debug( "queryString 3:" + queryString.toString() );
			if( renewalsVO.getTransactionFrom() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_GREATER + PARAM_FROM_DATE );
				paramsMap.put( PARAM_FROM_DATE, renewalsVO.getTransactionFrom() );

			}
			LOGGER.debug( "queryString 4:" + queryString.toString() );
			if( renewalsVO.getTransactionTo() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_LESS + PARAM_POL_EXP_DATE );
				paramsMap.put( PARAM_POL_EXP_DATE, renewalsVO.getTransactionTo() );

			}
			LOGGER.debug( "queryString 5:" + queryString.toString() );
			// Release 4.0 Oman Changes passing location code in search query
			if( renewalsVO.getBranch() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + PARAM_LOCATION_CODE );
				paramsMap.put( PARAM_LOCATION_CODE, Integer.parseInt( renewalsVO.getBranch() ) );
			}
			// Release 4.0 Oman Changes
			LOGGER.debug( "queryString 6:" + queryString.toString() );
			if( renewalsVO.getBrokerName() != null ){

				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL + PARAM_BROKER_CODE );
				paramsMap.put( PARAM_BROKER_CODE, Short.parseShort( renewalsVO.getBrokerName() ) );

			}
			LOGGER.debug( "queryString 7:" + queryString.toString() );
			if( renewalsVO.getScheme() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL + PARAM_SCHEME_CODE );
				paramsMap.put( PARAM_SCHEME_CODE, Integer.parseInt( renewalsVO.getScheme() ) );

			}
			LOGGER.debug( "queryString 8:" + queryString.toString() );
			if( renewalsVO.getInsuredName() != null ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				String PARAM = PERCENTAGE + ( renewalsVO.getInsuredName() ).toUpperCase() + PERCENTAGE;
				queryString.append( TO_UPPER_CASE + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_FIRST_NAME + CLOSE_BRACKET + LIKE_OPERATOR + SINGLE_QUOTE + PARAM + SINGLE_QUOTE );

			}
			LOGGER.debug( "queryString 9:" + queryString.toString() );
			if( renewalsVO.getAllDirect() ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DISTRIBUTION_CHNL + QRY_PRT_NOT_EQUL + BROKER_CHANNEL );

			}
			LOGGER.debug( "queryString 10:" + queryString.toString() );
			if( renewalsVO.getWithEmailID() ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_EMAIL_ID + QRY_PRT_IS + QRY_PRT_NOT +QRY_PRT_NULL );

			}
			LOGGER.debug( "queryString 11:" + queryString.toString() );
			if( renewalsVO.getNotYetPrinted() ){
				queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
				appendWhere = false;
				queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LINKING_ID + QRY_PRT_NOT_IN + OPEN_BRACKET+ QRY_GET_ALREADY_PRINTED +CLOSE_BRACKET );
			}

			LOGGER.debug( "queryString 12:" + queryString.toString() );
			Query query = session.createQuery( queryString.toString() );
			// ADD QUERY PARAMS
			Iterator<String> iterParams = paramsMap.keySet().iterator();
			while( iterParams.hasNext() ){
				String paramName = iterParams.next().toString();
				query.setParameter( paramName, paramsMap.get( paramName ) );
				LOGGER.debug( "SetParam :_2" + paramName + "/" + paramsMap.get( paramName ) );
			}
			LOGGER.debug( "queryString 13:" + queryString.toString() );
			renPolicies = query.list();

		}
		catch( HibernateException hibernateException ){
			hibernateException.printStackTrace();
			throw new BusinessException( "pas.renewal.exceptionInDataFetch", null, "Exception Occurred while fetching the dat_4" );
		}
		RenewalSearchSummaryVO renPolDetails = new RenewalSearchSummaryVO();

		for( VTrnRenewalQuotationsSbs renewalPolVO : renPolicies ){
			if( !Utils.isEmpty( renewalPolVO ) ){
				RenewalResultsVO renResults = new RenewalResultsVO();
				renResults.setPolLinkingId( renewalPolVO.getId().getPolLinkingId() );
				renResults.setEndtId( renewalPolVO.getPolEndtId() );
				renResults.setEndtNo( renewalPolVO.getPolEndtNo() );
				renResults.setConcatPolicyNo( renewalPolVO.getPolConcPolicyNo() );
				renResults.setPolicyNo( renewalPolVO.getPolPolicyNo() );
				renResults.setRenQuoteNo( renewalPolVO.getPolQuotationNo() ); 
				renResults.setPolEffectiveDate( renewalPolVO.getPolEffectiveDate1());
				renResults.setPolExpiryDate( renewalPolVO.getPolExpiryDate1() );
				renResults.setClassCode( CLASS_CODE );
				renResults.setPolicyYear( renewalPolVO.getPolPolicyYear() );
				renResults.setPolValidityStartDate( renewalPolVO.getPolValidityStartDate() );
				renResults.setEmailId( renewalPolVO.getCshEEmailId() );
				renResults.setPolLocCode(renewalPolVO.getPolLocationCode());
				resultList.add( renResults );
				renPolDetails.setRenPolList( resultList );
			}
		}
		LOGGER.info( "******************* Exiting getRenewalQuotations ******************"  );
		return renPolDetails;
	}

	@Override
	public BaseVO fetchPolicyExpDate(BaseVO baseVO) {
		
		PolicyVO policyVO  =(PolicyVO)baseVO;
		Date expiryDate = null ;
		/*
		 * Modified the below query by adding Pol_issue_hour check 
		 * Changed by Vishwa as a part of  3.8 fix.
		 */
		String expDateQuery = "select distinct pol_expiry_date from" +
				" t_trn_policy where pol_policy_no ="+policyVO.getPolicyNo()+" and" +
				" pol_validity_expiry_date = to_date('31-DEC-2049','DD-MON-YYYY') " +
				" and pol_issue_hour = 3 and pol_conc_policy_no = '"+ policyVO.getConcatPolicyNo()+"'";
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(expDateQuery);
			expiryDate=(Date)query.uniqueResult();
			DataHolderVO<Date> expiryDateVO =  new DataHolderVO<Date>();
			expiryDateVO.setData( expiryDate );
			return expiryDateVO;
	}

	@Override
	public BaseVO fetchRenewalStatusReport(BaseVO baseVO) {

		GenerateRenewalsSearchCriteriaVO searchCriteria = (GenerateRenewalsSearchCriteriaVO) baseVO;
		List<VRenewalStatusReportPas> renewedPolsData = null; // task
		List<VRenewalStatusReportPasHT> renewedPolsDataHT = null;
		
		List<RenewalReportsVO> renewalPolList = new  com.mindtree.ruc.cmn.utils.List<RenewalReportsVO>(RenewalReportsVO.class);
		Integer polType = Integer.valueOf( searchCriteria.getLob() );
		StringBuffer queryString = new StringBuffer();
		Criteria c = null;
		boolean isSBSApplication = polType.equals(POL_POLICY_TYPE) ? true : false;
		
		if(isSBSApplication)
		{
			queryString.append( "from VRenewalStatusReportPas v where v.id.polClassCode = ? and v.polLocationCode = ? " );
			c = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria( VRenewalStatusReportPas.class );
		}
		else
		{
			queryString.append( "from VRenewalStatusReportPasHT v where v.id.polClassCode = ? and v.polLocationCode = ? " );
			c = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria( VRenewalStatusReportPasHT.class );
		}
		
		if( !Utils.isEmpty( searchCriteria.getClazz() )){
			if( Integer.valueOf(searchCriteria.getClazz()).equals(SvcConstants.zeroVal)  ){
				Object[] classCodes = fetchClassCodes(searchCriteria.getLoggedInUser());
				c.add( Restrictions.in( "id.polClassCode" ,classCodes));
			}else{
			   c.add( Restrictions.eq( "id.polClassCode", Integer.valueOf( searchCriteria.getClazz() )) );
			}
		}
		
		if( !Utils.isEmpty( searchCriteria.getBranch() )){
			c.add( Restrictions.eq( "polLocationCode", Integer.valueOf( searchCriteria.getBranch() )) );
		}
		if( !Utils.isEmpty( searchCriteria.getPolicyNo()  ) ){
			c.add( Restrictions.eq( "id.policyNo", Long.valueOf( searchCriteria.getPolicyNo() ) ) );
			queryString.append( "and v.id.policyNo = ? " );
		}
			
		if( !Utils.isEmpty( searchCriteria.getScheme() )){
			c.add( Restrictions.eq( "polSchemeCode", Integer.valueOf( searchCriteria.getScheme() ) ) );
			queryString.append( "and v.polSchemeCode = ? " );
		}
		if( !Utils.isEmpty( searchCriteria.getNoOfDays() )){
			Date fromDate = new Date();
			Calendar cal= Calendar.getInstance();
			cal.setTime( fromDate );
			cal.add( Calendar.DATE, Integer.valueOf( searchCriteria.getNoOfDays()) );
			Date toDate = cal.getTime();
			
			/*if(isSBSApplication)
			{
				c.add( Restrictions.between( "quoExpiryDate", fromDate, toDate ) );
				queryString.append( "and v.quoExpiryDate between current_date() and  current_date() + ? " );
			
			} else {
				c.add( Restrictions.between( "polExpiryDate", fromDate, toDate ) );
				queryString.append( "and v.polExpiryDate between current_date() and  current_date() + ? " );
			}*/
			if(isSBSApplication)
			{
				c.add( Restrictions.between( com.Constant.CONST_EFFECTIVEDATE, fromDate, toDate ) );
				queryString.append( "and v.effectiveDate between current_date() and  current_date() + ? " );
			
			} else {
				c.add( Restrictions.between( com.Constant.CONST_EFFECTIVEDATE, fromDate, toDate ) );
				queryString.append( "and v.effectiveDate between current_date() and  current_date() + ? " );
			}
			
		}
		if( !Utils.isEmpty( searchCriteria.getInsuredName() )){
			c.add( Restrictions.like( "insuredName", "%"+ searchCriteria.getInsuredName() +"%" ).ignoreCase() );
			queryString.append( "and upper(v.insuredName) like '%?%' " );
		}
		if( !Utils.isEmpty( searchCriteria.getBrokerName() ) && !searchCriteria.getBrokerName().equalsIgnoreCase(ALL_OPTION)){
			
			if(searchCriteria.getBrokerName().equalsIgnoreCase(DIRECT_OPTION)){
				c.add(Restrictions.isNull(com.Constant.CONST_BRCODE));		/*	142563- For fetching record by Direct Broker */
				queryString.append( "and v.brCode is null " );
			}else{
				c.add( Restrictions.eq( com.Constant.CONST_BRCODE, Integer.valueOf( searchCriteria.getBrokerName() ) ) );
				queryString.append( "and v.brCode= ? " );
			}
		}
		if( !Utils.isEmpty( searchCriteria.getAllDirect() ) && searchCriteria.getAllDirect()){
			c.add( Restrictions.ne("polDistributionChnl",Integer.valueOf( BROKER_CHANNEL.toString() )) );
			queryString.append( "and v.polDistributionChnl= 9 " );
		}
		if( !Utils.isEmpty( searchCriteria.getStatusId() )){
			c.add( Restrictions.eq( com.Constant.CONST_POLSTATUS, Integer.valueOf( searchCriteria.getStatusId() ) ) );
			queryString.append( "and v.polStatus= ? " );
		}
		if( !Utils.isEmpty( searchCriteria.getLob())){
			polType = Integer.valueOf( searchCriteria.getLob() );
			List<Integer> polTypes = new ArrayList<Integer>();
			
			if(polType == 888){
				polTypes.add(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE));
				polTypes.add(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE));
				c.add( Restrictions.in( com.Constant.CONST_POLPOLICYTYPE, polTypes ) );
			
			}else{
				c.add( Restrictions.eq( com.Constant.CONST_POLPOLICYTYPE, Integer.valueOf( searchCriteria.getLob() ) ) );
			}

		}
		
		if(isSBSApplication)
		{
			if( !Utils.isEmpty( searchCriteria.getTransactionFrom() ) && !Utils.isEmpty( searchCriteria.getTransactionTo() ))
			{
				c.add( Restrictions.between( com.Constant.CONST_EFFECTIVEDATE,searchCriteria.getTransactionFrom() , searchCriteria.getTransactionTo() ) );
				queryString.append( "and (v.effectiveDate between ? and ? )" );
			}
			renewedPolsData = c.list();
			for( VRenewalStatusReportPas renPolInfo : renewedPolsData )
			{
				RenewalReportsVO renPol = new RenewalReportsVO();
				BeanMapper.map( renPolInfo, renPol );
				renewalPolList.add(renPol);
			}
		}
		else
		{
			if( !Utils.isEmpty( searchCriteria.getTransactionFrom() ) && !Utils.isEmpty( searchCriteria.getTransactionTo() ))
			{
				c.add( Restrictions.between( com.Constant.CONST_EFFECTIVEDATE,searchCriteria.getTransactionFrom() , searchCriteria.getTransactionTo() ) );
				queryString.append( "and (v.effectiveDate between ? and ? )" );
				
				/*c.add( Restrictions.between( "polExpiryDate", searchCriteria.getTransactionFrom(), searchCriteria.getTransactionTo() ) );
				queryString.append( "and v.polExpiryDate between ? and ? " );*/
			}
			renewedPolsDataHT = c.list();
			for( VRenewalStatusReportPasHT renPolInfo : renewedPolsDataHT )
			{
				RenewalReportsVO renPol = new RenewalReportsVO();
				BeanMapper.map( renPolInfo, renPol );
				renewalPolList.add(renPol);
			}
		}
		DataHolderVO<List> holder = new DataHolderVO<List>();
		holder.setData( renewalPolList );
		return holder;
		
	}
		private Object[] fetchClassCodes(User userProfile) {
			
			LookUpVO lookUpVO = null;
			
			
			lookUpVO = new LookUpVO();
			lookUpVO.setCategory( "CLASS" );
			lookUpVO.setLevel1( SvcConstants.LOOKUP_LEVEL1 );
			lookUpVO.setLevel2( ((UserProfile) userProfile).getRsaUser().getUserId().toString() );
			LookUpService lookupSvc = (LookUpService) Utils.getBean( "lookUpService" );
			LookUpListVO lookUpL  = (LookUpListVO) lookupSvc.invokeMethod( "getListOfDescription", lookUpVO );
			
			int count = 0;
			Object[]  classCodes = new Object[lookUpL.getLookUpList().size()] ;
			for( LookUpVO lkv : lookUpL.getLookUpList() ){
				if( !Utils.isEmpty( lkv ) ){
					if( !Utils.isEmpty( lkv.getCode() ) ){
						classCodes[count++] = (  lkv.getCode().intValue()  );
					}
			    }
			}
			
			return classCodes;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.rsaame.pas.renewals.dao.IRenewalsDAO#getDisLoadPer(com.mindtree.ruc.cmn.base.BaseVO)
		 * Method to check if discount loading percentage >= 30%
		 */
		@Override
		public BaseVO getDisLoadPerFromQuo( BaseVO baseVO ){ 
			DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
			Long renQuoteNo  =holderVO.getData();
			BigDecimal discountPer = BigDecimal.ZERO;
			BigDecimal LoadingPer = BigDecimal.ZERO;
			
			
			/*String disLoadPerQuery = "select max(abs(prm_rate)), prm_cov_code from t_trn_premium where prm_policy_id in " +
					 "(select pol_policy_id from t_trn_policy where pol_linking_id= "+ polForRenLinkingId +")" +
					 "and prm_cov_code in ("+SvcConstants.SC_PRM_COVER_DISCOUNT+","+SvcConstants.SC_PRM_COVER_LOADING+
					 ") and trunc(prm_validity_expiry_date) = trunc(to_date('31-DEC-2049','dd-MON-yyyy'))"+
					 " group by prm_cov_code;";*/
			/*
			 * Modified the below query by adding Pol_issue_hour check  
			 * Changed by Vishwa as a part of  3.8 fix.
			 */
			String disLoadPerQuery = "select max(abs(prm_rate)), prm_cov_code from t_trn_premium_quo where prm_policy_id in " +
					"(select pol_policy_id from t_Trn_policy_quo where pol_issue_hour = 3 and pol_quotation_no = "+renQuoteNo+")"+
					"and prm_cov_code in ("+SvcConstants.SC_PRM_COVER_DISCOUNT+","+SvcConstants.SC_PRM_COVER_LOADING+")"+
					" and trunc(prm_validity_expiry_date) = trunc(to_date('31-DEC-2049','dd-MON-yyyy'))"+
					" group by prm_cov_code";
			
			 Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			 Query query = session.createSQLQuery(disLoadPerQuery);
			 List<Object> result = query.list();
			 if( !Utils.isEmpty( result ) ){
				 Iterator<Object> itr = result.iterator();
					while(itr.hasNext()){
						Object[] prmRecord =  (Object[]) itr.next();
						/*
						 * if cover code is 51 i.e Discount then set discount percentage or else set loading percentage 
						 */
						if(prmRecord[1].toString().equals(SvcConstants.SC_PRM_COVER_DISCOUNT.toString())){
							discountPer =  (BigDecimal) prmRecord[0];
						}else {
							LoadingPer = (BigDecimal) prmRecord[0];
						}
						
					}
			 }
				DataHolderVO<Map<String,BigDecimal>> disLoadPer=  new DataHolderVO<Map<String,BigDecimal>>();
				Map<String,BigDecimal> percentages = new HashMap<String,BigDecimal>();
				
				percentages.put(SvcConstants.DISCOUNT_PER,discountPer );
				percentages.put(SvcConstants.LOADING_PER,LoadingPer );
				
				disLoadPer.setData(percentages);
				return disLoadPer;
			}

		/*Added for Renewals - check if trade license is being copied during generating renewal quote*/
		@Override
		public void checkRenewalTradeLicense(BaseVO baseVO) {
			
			PolicyVO policyVO = (PolicyVO) baseVO;
			String path = Utils.concat( Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_ROOT ) , "/" , Utils.getSingleValueAppConfig( SvcConstants.FILE_UPLOAD_TRADE_LICENCE_FOLDER ) , "/"
					, policyVO.getPolLinkingId().toString() );
			
			/*
			 * Get the list of files under the directory "path".
			 * If the list is not empty, that means the user has 
			 * uploaded at least one trade license file already. 
			 * */
			File theFile = new File( path );
			
			if( Utils.isEmpty( theFile.list() )){
				Long originalLinkingId = null;
				/*
				 * Modified the below query by adding Pol_issue_hour check  
				 * Changed by Vishwa as a part of  3.8 fix.
				 */
				/*String orginalLinkingIdQuery = "select distinct pol_linking_id from t_trn_policy_quo where  pol_issue_hour = 3 and pol_policy_no in" +
						"(select pol_ref_policy_no from t_trn_policy_quo where pol_quotation_no = "+policyVO.getQuoteNo()+" and pol_issue_hour =3 ) and pol_ref_policy_no is null";*/
				String orginalLinkingIdQuery = "SELECT pol_linking_id FROM t_trn_policy_quo WHERE pol_issue_hour = "+ SvcConstants.POL_ISSUE_HOUR +
						" AND (pol_policy_no,pol_policy_year) IN (SELECT pol_ref_policy_no,pol_ref_policy_year FROM t_trn_policy_quo "+
						"WHERE pol_quotation_no = "+policyVO.getQuoteNo()+" AND pol_policy_type = "+ SvcConstants.SBS_POL_TYPE +
						" AND pol_issue_hour = "+ SvcConstants.POL_ISSUE_HOUR + ") AND pol_policy_type = "+ SvcConstants.SBS_POL_TYPE +
						" GROUP BY pol_linking_id";
				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery(orginalLinkingIdQuery);
				Object result = null;
				try {
					result = query.uniqueResult();
					originalLinkingId=Long.valueOf(result.toString());
				}
				catch(Exception e){
					throw new BusinessException( "pas.tradeLicense.notUploaded", null, "Please upload the Trade license for this quote" );  
				} 
				/*
				 * Copying trade license  from original quotation to renewed  quote
				 */
				File orginalTLfile = new File(Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_ROOT)+"//"+Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER)+"//"+originalLinkingId);
				File renewedFile = new File(Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_ROOT)+"//"+Utils.getSingleValueAppConfig(com.Constant.CONST_FILE_UPLOAD_TRADE_LICENCE_FOLDER)+"//"+policyVO.getPolLinkingId());
				
				
				LOGGER.debug("--------------- Copy Trade License for renewed quote --------------------"+policyVO.getQuoteNo());
				LOGGER.debug("---------------------------------------Old file------------------------"+orginalTLfile.getPath());
				LOGGER.debug("---New file is writable---"+renewedFile.canWrite()+"--------path of new file-------"+renewedFile.getPath());
				
				/*
				 * Added Java IO copy method instead of FileUtils API
				 */
				try{
					SvcUtils.copyTradeLicense(orginalTLfile,renewedFile);
				}catch (IOException e) {
					e.printStackTrace();
					LOGGER.debug("--------------------------------------Files Copied failure------------------------");
					LOGGER.trace( "Copying of trade licence failed." );
		        }
				
				/*try{
				
					Makes a directory If renewedFile directory is already not created - Makes a directory, including any necessary but nonexistent parent directories. 
					 If there already exists a file with specified name or the directory cannot be created then an exception is thrown.
					FileUtils.forceMkdir(renewedFile);
					FileUtils.copyDirectory(orginalTLfile,renewedFile);
					LOGGER.debug("--------------------------------------Files Copied successfully------------------------");
					
				} 
				catch (IOException e)
				{
					e.printStackTrace();
					LOGGER.debug("--------------------------------------Files Copied failure------------------------");
					LOGGER.trace( "Copying of trade licence failed." );
				}
				catch (Exception e)
				{
					e.printStackTrace();
					LOGGER.debug("--------------------------------------Files Copied failure------------------------");
					LOGGER.trace( "Copying of trade licence failed." );
				}*/
			}				
		}
		
		public BaseVO getRenewalQuotationsForEmail( BaseVO criteria ){
			PrintRenewalsSearchCriteriaVO renewalsVO = (PrintRenewalsSearchCriteriaVO) criteria;
			Session session = null;
			List<VRenewalStatusEmailReportPas> renPolicies = null; // task
			List<RenewalResultsVO> resultList = new com.mindtree.ruc.cmn.utils.List<RenewalResultsVO>( RenewalResultsVO.class ); //tasklist
			
			LOGGER.info( "************ Entering getRenewalQuotations ******************"  );
			try{
				session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				StringBuffer queryString = new StringBuffer( 128 );
				// CALCULATE THE DATES FOR THE DAYS RANGE
				// ADD THE BASE QUERY
				queryString.append( QRY_RENEWAL_QUOTATONS_EMAIL_SEARCH_BASE );
				boolean appendWhere = true;
				/*LOGGER.debug( "queryString 1:" + queryString.toString() );
				if( renewalsVO.getInsuredName() != null ){
					queryString.append( QRY_INSURED + PRINT_QRY_BASIC_CONDITION + PRINT_QRY_BASIC_CONDITION1 );
					appendWhere = false;
				}*/
				Map<String, Object> paramsMap = new HashMap<String, Object>();

				if( renewalsVO.getClazz() != null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + "id."+PARAM_CLASS_CODE + QRY_PRT_EQUL + PARAM_CLASS_CODE );
					paramsMap.put( PARAM_CLASS_CODE, Integer.valueOf( renewalsVO.getClazz() ) );
				}
				LOGGER.debug( "queryString 2:" + queryString.toString() );
				if( renewalsVO.getPolicyNo() != null ){

					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + "id.policyNo" + QRY_PRT_EQUL + PARAM_POL_NUM );
					paramsMap.put( PARAM_POL_NUM, Long.parseLong( renewalsVO.getPolicyNo() ) );

				}
				LOGGER.debug( "queryString 3:" + queryString.toString() );
				if( renewalsVO.getTransactionFrom() != null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + com.Constant.CONST_EFFECTIVEDATE + QRY_PRT_EQUL_OR_GREATER + PARAM_FROM_DATE );
					paramsMap.put( PARAM_FROM_DATE, renewalsVO.getTransactionFrom() );

				}
				LOGGER.debug( "queryString 4:" + queryString.toString() );
				if( renewalsVO.getTransactionTo() != null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + com.Constant.CONST_EFFECTIVEDATE + QRY_PRT_EQUL_OR_LESS + PARAM_POL_EXP_DATE );
					paramsMap.put( PARAM_POL_EXP_DATE, renewalsVO.getTransactionTo() );

				}
				LOGGER.debug( "queryString 5:" + queryString.toString() );
				// Release 4.0 Oman Changes passing location code in search query
				if( renewalsVO.getBranch() != null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + PARAM_LOCATION_CODE );
					paramsMap.put( PARAM_LOCATION_CODE, Integer.parseInt( renewalsVO.getBranch() ) );
				}
				// Release 4.0 Oman Changes
				LOGGER.debug( "queryString 6:" + queryString.toString() );
				if(!Utils.isEmpty( renewalsVO.getBrokerName() ) && !renewalsVO.getBrokerName().equalsIgnoreCase(ALL_OPTION)){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					if(renewalsVO.getBrokerName().equalsIgnoreCase(DIRECT_OPTION)){
						queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + "brCode is null " );
					}else{
						queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + com.Constant.CONST_BRCODE + QRY_PRT_EQUL + PARAM_BROKER_CODE );
						paramsMap.put( PARAM_BROKER_CODE, Integer.valueOf(( renewalsVO.getBrokerName() ) ));
					}
				}
				LOGGER.debug( "queryString 7:" + queryString.toString() );
				if( renewalsVO.getScheme() != null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL + PARAM_SCHEME_CODE );
					paramsMap.put( PARAM_SCHEME_CODE, Integer.parseInt( renewalsVO.getScheme() ) );

				}
				LOGGER.debug( "queryString 8:" + queryString.toString() );
				if( renewalsVO.getInsuredName() != null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					String PARAM = PERCENTAGE + ( renewalsVO.getInsuredName() ).toUpperCase() + PERCENTAGE;
					queryString.append( TO_UPPER_CASE + QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + "insuredName" + CLOSE_BRACKET + LIKE_OPERATOR + SINGLE_QUOTE + PARAM + SINGLE_QUOTE );

				}
				LOGGER.debug( "queryString 9:" + queryString.toString() );
				if( renewalsVO.getAllDirect() ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DISTRIBUTION_CHNL + QRY_PRT_NOT_EQUL + BROKER_CHANNEL );

				}
				LOGGER.debug( "queryString 10:" + queryString.toString() );
				if( renewalsVO.getWithEmailID() ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_EMAIL_ID + QRY_PRT_IS + QRY_PRT_NOT +QRY_PRT_NULL );

				}
				LOGGER.debug( "queryString 11:" + queryString.toString() );
				if( renewalsVO.getNotYetPrinted() ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LINKING_ID + QRY_PRT_NOT_IN + OPEN_BRACKET+ QRY_GET_ALREADY_PRINTED +CLOSE_BRACKET );
				}
				LOGGER.debug( "queryString 12:" + queryString.toString() );
				if( renewalsVO.getRenewalTerm() != null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					String PARAM =  ( renewalsVO.getRenewalTerm() ).toUpperCase() ;
					queryString.append( TO_UPPER_CASE + QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + "polRenTermTxt" + CLOSE_BRACKET + "=" + SINGLE_QUOTE + PARAM + SINGLE_QUOTE );

				}
				if( renewalsVO.getLob()!= null ){
					queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
					appendWhere = false;
					queryString.append( QRY_RENEWAL_QUOTATION_SEARCH_OBJ + QRY_PRT_DOT + com.Constant.CONST_POLPOLICYTYPE + QRY_PRT_EQUL + com.Constant.CONST_POLPOLICYTYPE );
					paramsMap.put( com.Constant.CONST_POLPOLICYTYPE, Integer.parseInt( renewalsVO.getLob() ) );
				}
				
				if (null != renewalsVO.getStatusIdList()) {
					boolean isStatusSelected = true;
					String statusList[] = renewalsVO.getStatusIdList();
					for (String status : statusList) {
						if ("99999".equals(status)) {
							isStatusSelected = false;
							break;
						}
					}
					if (isStatusSelected) {
						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append(QRY_RENEWAL_QUOTATION_SEARCH_OBJ+ QRY_PRT_DOT + "polStatus in (:polStatus)"
								+ CLOSE_BRACKET);
						// queryString.append(" statusId := " +
						// PARAM_POL_POLICY_STATUS);
						paramsMap.put(com.Constant.CONST_POLSTATUS,renewalsVO.getStatusIdList());
					}
				}

			LOGGER.debug( "queryString 13:" + queryString.toString() );
			Query query = session.createQuery( queryString.toString() );
			// ADD QUERY PARAMS
			Iterator<String> iterParams = paramsMap.keySet().iterator();
			while( iterParams.hasNext() ){
				String paramName = iterParams.next().toString();
				if(com.Constant.CONST_POLSTATUS.equals(paramName)){
					query.setParameterList(com.Constant.CONST_POLSTATUS,renewalsVO.getStatusIdList());
				}else{
					query.setParameter(paramName, paramsMap.get(paramName));
				}
				LOGGER.debug( "SetParam :_3" + paramName + "/" + paramsMap.get( paramName ) );
			}
			LOGGER.debug( "queryString 14:" + queryString.toString() );
			renPolicies = query.list();

		}
		catch( HibernateException hibernateException ){
			hibernateException.printStackTrace();
			throw new BusinessException( "pas.renewal.exceptionInDataFetch", null, "Exception Occurred while fetching the dat_5" );
		}
		RenewalSearchSummaryVO renPolDetails = new RenewalSearchSummaryVO();
		Date date= new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//AdventId:142505; Modified to change the date format and add extra required parameter 
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");

		for( VRenewalStatusEmailReportPas renewalPolVO : renPolicies ){
			if( !Utils.isEmpty( renewalPolVO ) ){
				RenewalResultsVO renResults = new RenewalResultsVO();
				renResults.setPolLinkingId( renewalPolVO.getPolLinkingId());
				renResults.setEndtId( renewalPolVO.getEndtId());
				renResults.setPolicyId( renewalPolVO.getPolicyId() );
				/*renResults.setConcatPolicyNo( renewalPolVO.getPolConc );*/
				renResults.setPolicyNo( renewalPolVO.getId().getPolicyNo() );
				renResults.setRenQuoteNo( renewalPolVO.getQuotationNo() ); 
				date = renewalPolVO.getEffectiveDate();
				renResults.setPolEffectiveDate(dateFormat.format(date).toString());
				//enResults.setPolExpiryDate( renewalPolVO.getPolExpiryDate1() );
				renResults.setClassCode( renewalPolVO.getClEDesc() );
				//renResults.setPolicyYear( renewalPolVO.getPolPolicyYear() );
				date = renewalPolVO.getPolVSD();
				renResults.setPolValidityStartDate(dateFormat1.format(date).toString() );
				//renResults.setEmailId( renewalPolVO.getCshEEmailId() );
				renResults.setPolLocCode(renewalPolVO.getPolLocationCode());
				renResults.setInsuredName(renewalPolVO.getInsuredName());
				renResults.setProductDescription(renewalPolVO.getPtEDesc());
				renResults.setRenewedQuotePremium(renewalPolVO.getNewTotalPremium());
				if(!Utils.isEmpty(renewalPolVO.getPolRenTermTxt()) && renewalPolVO.getPolRenTermTxt().length() >200){
					renResults.setRenewalTerm(renewalPolVO.getPolRenTermTxt().trim().substring(0, 200));
				}else{
					renResults.setRenewalTerm(renewalPolVO.getPolRenTermTxt());
				}
				renResults.setRenewalTermCompleteTxt(renewalPolVO.getPolRenTermTxt());
				renResults.setBrokerName(renewalPolVO.getBrName());
				renResults.setEmailId(renewalPolVO.getCustEmailId());
				renResults.setBrEmailId(renewalPolVO.getBrEmail());
				renResults.setBrAccountExeEmail(renewalPolVO.getBrAccountExeEmail());
				renResults.setCuEInterests(renewalPolVO.getCuEInterests());
				renResults.setPolicyType(renewalPolVO.getPolPolicyType().toString());
				renResults.setBrRemarks(renewalPolVO.getBrRemarks());
				renResults.setBrAccountKeyManagerName(renewalPolVO.getBrAccountKeyManagerName());
				renResults.setBrAccountKeyManagerNum(renewalPolVO.getBrAccountKeyManagerNum());
				resultList.add( renResults );
				renPolDetails.setRenPolList( resultList );
			}
		}
		LOGGER.info( "******************* Exiting getRenewalQuotationsForEmail ******************"  );
		return renPolDetails;
}

}

