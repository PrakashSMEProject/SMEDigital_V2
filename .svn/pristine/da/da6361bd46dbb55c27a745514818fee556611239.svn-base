package com.rsaame.pas.sms.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.VTrnRenewalQuotesHtPas;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.RenewalResultsSmsVO;
import com.rsaame.pas.vo.app.SearchTransactionVO;
import com.rsaame.pas.vo.bus.RenewalSmsSearchSummaryVO;

/**
 * @author M1020278
 * 
 */
public class SmsDao extends BaseDBDAO implements ISmsDao{

	private final static Logger logger = Logger.getLogger( SmsDao.class );
	public static final String QRY_PRT_WHERE = " WHERE ";
	public static final String QRY_PRT_AND = " AND ";

	public static final String QRY_PRT_DOT = ".";

	public static final String QRY_PRT_EQUL = "=:";
	public static final String QRY_PRT_IN = " in (:";

	public static final String QRY_PRT_NOT_EQUL = "!=";

	public static final String QRY_PRT_EQUL_OR_GREATER = ">=:";

	public static final String QRY_PRT_EQUL_OR_LESS = "<=:";

	public static final String QRY_PRT_IS = " IS ";

	public static final String QRY_PRT_NOT = " NOT ";

	public static final String QRY_PRT_NOT_IN = " NOT IN ";

	public static final String QRY_PRT_NULL = " NULL ";

	public static final String BLANK_SPACE = "";

	public static final String PARAM_CLASS_CODE = "polClassCode";

	public static final String PARAM_POL_NUM = "polPolicyNo";

	public static final String PARAM_QUOT_NUM = "polQuotationNo";

	public static final String PARAM_FROM_DATE = "polEffectiveDate";

	public static final String PARAM_POL_EXP_DATE = "polExpiryDate";

	public static final String PARAM_BROKER_CODE = "polBrCode";

	public static final String PARAM_SCHEME_CODE = "polCoverNoteHour";

	public static final String PARAM_LOCATION_CODE = "polLocationCode";

	public static final String PARAM_FIRST_NAME = "insEFirstName";

	public static final String PARAM_POL_POLICY_TYPE = "polPolicyType";

	public static final String PARAM_AGENT = "polAgentId";

	public static final String PARAM_NATIONALITY = "nationality";

	public static final String ID = "id";

	public static final String LIKE_OPERATOR = " LIKE ";

	public static final String PERCENTAGE = "%";

	public static final String SINGLE_QUOTE = "'";

	public static final String COLON = ":";

	public static final String COMMA = ",";

	public static final String TO_UPPER_CASE = "upper(";

	public static final String CLOSE_BRACKET = ")";

	public static final String CLASS_CODE = "FIRE";

	private final static Integer POL_POLICY_TYPE = 50;

	public static final Short BROKER_CHANNEL = 4;
	public static final String PARAM_DC_CODE = "polDctCode";
	public static final String PARAM_DISTRIBUTION_CHNL = "polDistributionChnl";

	public static final String QRY_RENEWAL_SEARCH_OBJ = " renPols";
	public static final String QRY_RENEWAL_SEARCH_BASE = "select renPols FROM VTrnRenewalQuotesHtPas " + QRY_RENEWAL_SEARCH_OBJ;

	public static final String QRY_INSURED_OBJ = "insured";
	public static final String QRY_INSURED = ", " + "TMasInsured " + QRY_INSURED_OBJ;

	public static final String QRY_BASIC_CONDITION = QRY_PRT_WHERE + "renPols.polInsuredCode=insured.insInsuredCode ";

	@Override
	public BaseVO searchTransactionSms( BaseVO baseVO ){
		// TODO Auto-generated method stub
		logger.info( "********** Inside searchTransactionSMS <SmsDao>**********" );
		RenewalSmsSearchSummaryVO renPolDetails = new RenewalSmsSearchSummaryVO();
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof SearchTransactionVO ){
				SearchTransactionVO transVO = (SearchTransactionVO) baseVO;
				logger.debug( "Insured Name--> " + transVO.getInsuredName() );
				logger.debug( "Quotation No.--> " + transVO.getTransaction().getQuoteNo() );
				logger.debug( "Policy No.--> " + transVO.getTransaction().getPolicyNo() );
				logger.debug( "Scheme--> " + transVO.getTransaction().getScheme() );
				logger.debug( "Broker--> " + transVO.getTransaction().getBrokerName() );
				logger.debug( "Agent--> " + transVO.getAgent() );
				logger.debug( "Nationality--> " + transVO.getNationality() );
				logger.debug( "All Direct--> " + transVO.isAllDirect() );
				logger.debug( "Call Status--> " + transVO.getCallStatus() );
				logger.debug( "Branch--> " + transVO.getTransaction().getBranch() );
				logger.debug( "Transaction From Date--> " + transVO.getTransaction().getTransactionFrom() );
				logger.debug( "Transaction To Date--> " + transVO.getTransaction().getTransactionTo() );

				Session session = null;
				List<VTrnRenewalQuotesHtPas> renPolicies = null;
				List<RenewalResultsSmsVO> policyList = new com.mindtree.ruc.cmn.utils.List<RenewalResultsSmsVO>( RenewalResultsSmsVO.class );
				try{
					session = getHibernateTemplate().getSessionFactory().getCurrentSession();
					StringBuffer queryString = new StringBuffer( 128 );
					// ADD THE BASE QUERY
					queryString.append( QRY_RENEWAL_SEARCH_BASE );
					boolean appendWhere = true;

					if( transVO.getInsuredName() != null ){
						queryString.append( QRY_INSURED + QRY_BASIC_CONDITION );
						appendWhere = false;
					}
					Map paramsMap = new HashMap();
					if( transVO.getInsuredName() != null ){
						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						String PARAM = PERCENTAGE + ( transVO.getInsuredName() ).toUpperCase() + PERCENTAGE;
						queryString.append( TO_UPPER_CASE + QRY_INSURED_OBJ + QRY_PRT_DOT + PARAM_FIRST_NAME + CLOSE_BRACKET + LIKE_OPERATOR + SINGLE_QUOTE + PARAM + SINGLE_QUOTE );

					}
					if( transVO.getTransaction().getQuoteNo() != null ){

						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_QUOT_NUM + QRY_PRT_EQUL + PARAM_QUOT_NUM );
						paramsMap.put( PARAM_QUOT_NUM, new BigDecimal( transVO.getTransaction().getQuoteNo() ) );

					}
					if( transVO.getTransaction().getPolicyNo() != null ){

						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_NUM + QRY_PRT_EQUL + PARAM_POL_NUM );
						paramsMap.put( PARAM_POL_NUM, new BigDecimal( transVO.getTransaction().getPolicyNo() ) );

					}
					if( transVO.getTransaction().getScheme() != null ){
						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_SCHEME_CODE + QRY_PRT_EQUL + PARAM_SCHEME_CODE );
						paramsMap.put( PARAM_SCHEME_CODE, new BigDecimal( transVO.getTransaction().getScheme() ) );

					}
					if( transVO.getTransaction().getBrokerName() != null ){

						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_BROKER_CODE + QRY_PRT_EQUL + PARAM_BROKER_CODE );
						paramsMap.put( PARAM_BROKER_CODE, new BigDecimal( transVO.getTransaction().getBrokerName() ) );

					}
					if( transVO.getAgent() != null ){

						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_AGENT + QRY_PRT_EQUL + PARAM_AGENT );
						paramsMap.put( PARAM_AGENT, new BigDecimal( transVO.getAgent() ) );

					}
					if( transVO.getNationality() != null ){

						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_NATIONALITY + QRY_PRT_EQUL + PARAM_NATIONALITY );
						paramsMap.put( PARAM_NATIONALITY, new BigDecimal( transVO.getNationality() ) );

					}
					if( transVO.isAllDirect() ){
						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_DISTRIBUTION_CHNL + QRY_PRT_NOT_EQUL + BROKER_CHANNEL );

					}
					if( transVO.getTransaction().getBranch() != null ){
						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_LOCATION_CODE + QRY_PRT_EQUL + PARAM_LOCATION_CODE );
						paramsMap.put( PARAM_LOCATION_CODE, new BigDecimal( transVO.getTransaction().getBranch() ) );
					}
					if( transVO.getTransaction().getTransactionFrom() != null ){
						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_FROM_DATE + QRY_PRT_EQUL_OR_GREATER + PARAM_FROM_DATE );
						paramsMap.put( PARAM_FROM_DATE, transVO.getTransaction().getTransactionFrom() );

					}
					if( transVO.getTransaction().getTransactionTo() != null ){
						queryString.append( appendWhere ? QRY_PRT_WHERE : QRY_PRT_AND );
						appendWhere = false;
						queryString.append( QRY_RENEWAL_SEARCH_OBJ + QRY_PRT_DOT + PARAM_POL_EXP_DATE + QRY_PRT_EQUL_OR_LESS + PARAM_POL_EXP_DATE );
						paramsMap.put( PARAM_POL_EXP_DATE, transVO.getTransaction().getTransactionTo() );

					}
					logger.debug( "queryString :" + queryString.toString() );
					Query query = session.createQuery( queryString.toString() );
					// ADD QUERY PARAMS
					Iterator iterParams = paramsMap.keySet().iterator();
					logger.debug( "--->paramsMap.keySet().size = " + paramsMap.keySet().size() );
					logger.debug( "--->paramsMap.keySet() = " + paramsMap.keySet() );
					while( iterParams.hasNext() ){
						String paramName = iterParams.next().toString();
						query.setParameter( paramName, paramsMap.get( paramName ) );
						logger.debug( "SetParam ::" + paramName + "/" + paramsMap.get( paramName ) );
					}
					renPolicies = query.list();

					logger.debug( "result Size-------->" + renPolicies.size() );
				}
				catch( HibernateException hibernateException ){
					hibernateException.printStackTrace();
					throw new BusinessException( "pas.renewal.exceptionInDataFetch", null, "Exception Occurred while fetching the data" );
				}

				for( VTrnRenewalQuotesHtPas renewalPolVO : renPolicies ){
					if( !Utils.isEmpty( renewalPolVO ) ){
						RenewalResultsSmsVO renResults = new RenewalResultsSmsVO();
						renResults.setConcatPolicyNo( renewalPolVO.getPolConcPolicyNo() );
						renResults.setQuotationNo( renewalPolVO.getPolQuotationNo() );
						if(!Utils.isEmpty( renewalPolVO.getPolCoverNoteHour() ))
							renResults.setScheme( SvcUtils.getLookUpDescription( "ALL_SCHEME", "ALL", 
									( (UserProfile) ( transVO ).getLoggedInUser() ).getRsaUser().getUserId().toString(),
									Integer.parseInt(renewalPolVO.getPolCoverNoteHour().toString() ) ) );
						if(!Utils.isEmpty( renewalPolVO.getNationality() ))
							renResults.setNationality( SvcUtils.getLookUpDescription( "NATIONALITY", "ALL", "ALL", Integer.parseInt(renewalPolVO.getNationality().toString() ) ) );
						if(!Utils.isEmpty( renewalPolVO.getPolLocationCode() ))
							renResults.setBranch( SvcUtils.getLookUpDescription( "BRANCH", ( (UserProfile) ( transVO ).getLoggedInUser() ).getRsaUser().getUserId().toString(), "ALL", Integer.parseInt(renewalPolVO.getPolLocationCode().toString() ) ) ); 
						renResults.setSmsLevel( renewalPolVO.getTrnSmsLevel() );
						renResults.setSmsMode( renewalPolVO.getTrnSmsMode() );
						renResults.setSmsStatus(DAOUtils.getSmsStatusDesc( renewalPolVO.getTrnSmsStatus()) );
						renResults.setPolicyId( renewalPolVO.getId().getPolPolicyId() );
						renResults.setEndtId( renewalPolVO.getId().getPolEndtId() );
						renResults.setPolLocationCode( renewalPolVO.getPolLocationCode() );
						policyList.add( renResults );
					}
				}
				renPolDetails.setRenPolList( policyList );
				renPolDetails.setNumberOfRecords( policyList.size() );
			}
		}
		return renPolDetails;
	}

	@Override
	public BaseVO sendSms( BaseVO baseVO ){
		RenewalResultsSmsVO sms = (RenewalResultsSmsVO)baseVO;
		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		Object[] renData = new Object[12];
		Long renquoteNo = Long.valueOf(sms.getQuotationNo().toString());
		List<TTrnPolicyQuo> tTrnPolicyQuo =  (getHibernateTemplate() ).find("from TTrnPolicyQuo where polQuotationNo= ? and polIssueHour =?" , renquoteNo,SvcConstants.POL_ISSUE_HOUR);
		renData[0] = renquoteNo;
		if(!Utils.isEmpty(tTrnPolicyQuo) && tTrnPolicyQuo.size() > 0) {
			renData[1] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getId().getPolicyId();
			renData[2] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolLocationCode());
			renData[3] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolStatus());
			renData[4] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolEffectiveDate();
			renData[5] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolEndtNo();
			renData[6] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolDocumentCode());
			renData[7] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolValidityStartDate();
			renData[8] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolPolicyType().toString();
			renData[9] = String.valueOf(tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolUserId());
			renData[10] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolPolicyNo();
			renData[11] = tTrnPolicyQuo.get(SvcConstants.zeroVal).getPolConcPolicyNo();
		}else {
			throw new BusinessException("", null, "No record quotation record found for renewed policy.");
		}
		input.setData( renData );
		return input;
	}
}
