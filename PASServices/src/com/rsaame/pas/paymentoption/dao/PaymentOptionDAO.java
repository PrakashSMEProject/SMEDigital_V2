package com.rsaame.pas.paymentoption.dao;

/**
 * 
 */

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author M1014594
 *
 */
public class PaymentOptionDAO extends BaseDBDAO implements IPaymentOptionDAO{

	private final static String RECEIPTNO_SEQ_SBS = "SEQ_RECEIPT_NO";
	private static final Logger logger = Logger.getLogger( PaymentOptionDAO.class );
	
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final int THIRD = 2;

	/**
	 * This method is used to save payment related information i.e.. debit , credit and receipt datails in database.
	 * 
	 */
	public BaseVO savePaymentDetails( BaseVO baseVO ){
		if( logger.isInfo() ){
			logger.info( "Entering PaymentOptionDAO.savePaymentDetails method store payment details" );
		}

		List<BaseVO> inputData = null;

		PolicyVO policyVO = null;
		PaymentVO paymentVO = null;
		CommonVO commonVO = null;

		DataHolderVO<List<BaseVO>> dataHolderVO = (DataHolderVO<List<BaseVO>>) baseVO;

		if( !Utils.isEmpty( dataHolderVO ) ){
			inputData = dataHolderVO.getData();

			if( !Utils.isEmpty( inputData ) ){
				
				policyVO = (PolicyVO) inputData.get( FIRST );
				paymentVO = (PaymentVO) inputData.get( SECOND );
				commonVO = (CommonVO) inputData.get( THIRD );
				
				/*
				 *Below 'if' condition is used to call a LOB specific Payment procedures 
				 */
				if( !Utils.isEmpty( commonVO.getLob() ) && !(commonVO.getLob().equals( LOB.SBS))){
					logger.info( "Before calling method PaymentOptionDAO:savePaymentDetailsMonoline method" );
					return savePaymentDetailsMonoline( commonVO, paymentVO );
				}
				else {
					logger.info( "Before calling method PaymentOptionDAO:saveSBSPaymentDetails method" );
					return saveSBSPaymentDetails( policyVO, paymentVO );
				}
				
			}
		}

		return null;
	}


	/**
	 * Returns instance of {@link CommonVO}
	 * Method used to insert debit/credit note and receipt entries for a policy. 
	 * Since Phase 3
	 * @param commonVO
	 * @param paymentVO
	 * @return
	 */
	private BaseVO savePaymentDetailsMonoline( CommonVO commonVO, PaymentVO paymentVO ){

		if( logger.isDebug() ){
			logger.debug( "Procedure invocation with [ " + paymentVO.toString() + " ] paymentVO " );
		}

		if( !Utils.isEmpty( commonVO ) && !Utils.isEmpty( paymentVO ) ){
			PASStoredProcedure sp = null;

			if( logger.isInfo() ){
				logger.info( "********PaymentOptionDAO.savePaymentDetailsMonoline, Procedure call start here*********" );
			}
			if( !Utils.isEmpty( commonVO.getLob() ) && (( commonVO.getLob().equals( LOB.HOME )) || commonVO.getLob().equals( LOB.TRAVEL )) ){
				sp = (PASStoredProcedure) Utils.getBean( "insertHomeTravelPaymentDetailsSP" );
			}
			else if(!Utils.isEmpty( commonVO.getLob() ))
			{
				sp = (PASStoredProcedure) Utils.getBean( "insertMonolinePaymentDetailsSP" );
			}

			try{

				Integer payMode = null;
				//Radar fix
				//Map results = null;

				Long endtId = getEndtIdForProcessingHomeTravel( commonVO );

				if( !Utils.isEmpty( paymentVO.getPayModeCode() ) ){
					payMode = paymentVO.getPayModeCode().intValue();
				}

				String payStatus = paymentVO.isPaymentDone() ? "Y" : "N";
				if(Utils.isEmpty( commonVO.getPolicyNo() ) && Utils.isEmpty( commonVO.getConcatPolicyNo() )){
					throw new BusinessException( "cmn.unknownError", null, "Error while generating policy number" );
				}								
				List<Object> result = DAOUtils.getSqlResultSingleColumnPASFor( QueryConstants.GET_POLICY_ID, commonVO.getPolicyNo(),commonVO.getConcatPolicyNo() );
				Long policyId = ((BigDecimal) result.get( 0 )).longValue();
				
				logger.info("PaymentOptionDAO.savePaymentDetailsMonoline method, \npolicyId:"+policyId
						+", endtId:"+endtId+", Amount:"+paymentVO.getAmount()+", \nChequeDt:"+paymentVO.getChequeDt()
						+", ChequeNo:"+paymentVO.getChequeNo()+", BankCode:"+paymentVO.getBankCode()
						+", \npaymentMode:"+payMode+", payStatus:"+payStatus+", TerminalId:"+paymentVO.getTerminalId());
						
				sp.call( policyId, endtId, paymentVO.getAmount(), paymentVO.getChequeDt(), paymentVO.getChequeNo(), paymentVO.getBankCode(), payMode,
						payStatus,paymentVO.getTerminalId() );

				logger.debug( "Inside insertHomeTravel&MonolinePaymentDetailsSP" );

				if( logger.isInfo() ){
					logger.info( "********Procedure has been executed successfully*********" );
				}

			}
			catch( DataAccessException e ){
				if( logger.isError() ){
					logger.info( "There is an exception while executing procedure" );
				}
				throw new BusinessException( "pas.InsertPaymentOption.exception", e, "An exception occured while executing stored proc." );
			}
		}
		logger.info( "Exiting PaymentOptionDAO.savePaymentDetailsMonoline method." );

		return commonVO;
	}

	/**
	 * Returns instance of {@link PolicyVO}
	 * Method used to insert debit/credit note and receipt entries for a policy. Specifically used for Phase1/2.
	 * @param inputData
	 * @return
	 */
	private PolicyVO saveSBSPaymentDetails( PolicyVO policyVO, PaymentVO paymentVO ){

		Long policyLinkingId;

		if( logger.isDebug() ){
			logger.debug( "PaymentOptionDAO.saveSBSPaymentDetails method, Procedure invocation with [ " + paymentVO.toString() + " ] paymentVO " );
		}
		if( !Utils.isEmpty( policyVO ) && !Utils.isEmpty( paymentVO ) ){
			String expDate = SvcUtils.getYearFromDate( policyVO.getPolExpiryDate() ) + "-" + SvcUtils.getMonthFromDate( policyVO.getPolExpiryDate() ) + "-"
					+ SvcUtils.getDayFromDate( policyVO.getPolExpiryDate() );
			policyLinkingId = Long.valueOf( fetchPolicyLinkingID( policyVO.getPolicyNo(), expDate ) );
			if( logger.isInfo() ){
				logger.info( "Policy Linking Id in savePaymentDetails is--->" + policyLinkingId );
			}
			if( logger.isInfo() ){
				logger.info( "********Procedure call start here*********" );
			}
			PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "insertPaymentDetailsSP" );
			try{
				Integer payMode = null;
				if( !Utils.isEmpty( paymentVO.getPayModeCode() ) ){
					payMode = paymentVO.getPayModeCode().intValue();
				}
				/*Executing a procedure call to store payment information*/
				Map results = null;
				/* During RESOLVE_REFERAL flow (FLOW during AMEND POLICY for broker users ), policyVO.newEndtId will not be populated hence using policyVO.endtId */
				/*Long endtId = !Utils.isEmpty( policyVO.getEndorsements() ) ? ( policyVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ? policyVO.getEndtId() : policyVO
						.getNewEndtId() ) : SvcUtils.getLatestEndtId(policyVO);*/

				Long endtId = getEndtIdForProcessing( policyVO );
				
				String spLogger = "PaymentOptionDAO.saveSBSPaymentDetails method, \nIsPaymentDone:"+paymentVO.isPaymentDone()+", policyLinkingId:"+policyLinkingId
						+", endtId:"+endtId+", Amount:"+paymentVO.getAmount()+", \nChequeDt:"+paymentVO.getChequeDt()+", ChequeNo:"+paymentVO.getChequeNo()+", bankCode:"+paymentVO.getBankCode()
						+", \npayMode:"+payMode+", terminalId:"+paymentVO.getTerminalId();

				if( paymentVO.isPaymentDone() ){
					/*Endorsement id passed to be changed */
					
					logger.info(spLogger);
					results = sp.call( policyLinkingId, endtId, paymentVO.getAmount(), paymentVO.getChequeDt(), paymentVO.getChequeNo(), paymentVO.getBankCode(), payMode, "Y",paymentVO.getTerminalId() );
					logger.debug( "Inside insertPaymentDetailsSP" );
				}
				else{
					logger.info(spLogger);
					results = sp.call( policyLinkingId, endtId, paymentVO.getAmount(), paymentVO.getChequeDt(), paymentVO.getChequeNo(), paymentVO.getBankCode(), payMode, "N",paymentVO.getTerminalId() );
				}
				if( logger.isInfo() ){
					logger.info( "********Procedure has been executed successfully*********" );
				}
				logger.debug( "Procedure Resulted values - " + results.values() );
			}
			catch( DataAccessException e ){
				if( logger.isError() ){
					logger.info( "There is an exception while executing procedure" );
				}
				throw new BusinessException( "pas.InsertPaymentOption.exception", e, "An exception occured while executing stored proc." );
			}
		}
		logger.info( "Exiting PaymentOptionDAO.saveSBSPaymentDetails method" );
		return policyVO;
	}

	/**
	 * 
	 *  This method is to fetch the policy linking id for print policy documents. 
	*/
	private String fetchPolicyLinkingID(Long policyNo, String polExpDate){
		if(logger.isInfo())	logger.info( "Enetring fetchPolicyDetails method to get the policy linking id" );
		String policyLinkinID=null;
		String policySql = "SELECT  P.POL_LINKING_ID, P.POL_ENDT_ID FROM T_TRN_POLICY P WHERE  P.POL_ISSUE_HOUR = "+Utils.getSingleValueAppConfig( "SBS_POLICY_ISSUE_HOUR" )+"  and P.POL_POLICY_NO = "+policyNo+" and to_char(pol_expiry_date, 'yyyy-mm-dd') = '"+polExpDate+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( policySql );
		List<Object> result = query.list();
		Iterator<Object> itr = null;
		itr = result.iterator();
		Object[] row = null;

		row = (Object[]) itr.next();
		if( !Utils.isEmpty( row ) ){
			policyLinkinID = ( String.valueOf( row[ 0 ] ) );
		}
		if( logger.isInfo() ){
			logger.info( "Exiting fetchPolicyDetails method to get the policy linking id" );
		}
		return policyLinkinID;
	}

	/**
	 * 
	 * @param policyVO
	 * @return
	 */

	private Long getEndtIdForProcessing( PolicyVO policyVO ){
		/*String sqlQuery = "select max (pol_endt_id) from t_trn_policy where pol_linking_id = "
		            + policyVO.getPolLinkingId();
		*/
		String sqlQuery = "select max (pol_endt_id) from t_trn_policy where pol_issue_hour = 3 and pol_quotation_no = " + policyVO.getQuoteNo();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( sqlQuery );
		List<Object> result = query.list();
		if( Utils.isEmpty( result ) ){

			throw new BusinessException( "cmn.unknownError", null, "For linking id " + policyVO.getPolLinkingId() + " Endorsment id " + policyVO.getNewEndtId() + " or "
					+ policyVO.getEndtId() + "is unavilable in policy table table" );
		}
		Long endtId = ( (BigDecimal) result.get( 0 ) ).longValue();

		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && !Utils.isEmpty( policyVO.getEndtId() ) ){
			//    validate wether the endt id in policy vo are valied for the passes linking id
			if( !( policyVO.getNewEndtId().equals( endtId ) || policyVO.getEndtId().equals( endtId ) ) ){
				throw new BusinessException( "cmn.unknownError", null, "For linking id " + policyVO.getPolLinkingId() + " Endorsment id " + policyVO.getNewEndtId() + " or "
						+ policyVO.getEndtId() + "the endt ID does not match" );
			}
		}
		else{
			return endtId;
		}

		return endtId;
	}
	
	/**
	 * Returns endorsement id to process(recent end id), which we will fetch from the T_TRN_POLICY table based on the quotation no
	 * @param commonVO
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private Long getEndtIdForProcessingHomeTravel(CommonVO commonVO){
		
		Long endtId;
		
		String sqlQuery = "select max (pol_endt_id) from t_trn_policy where pol_issue_hour = 3 and pol_quotation_no = " + commonVO.getQuoteNo();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( sqlQuery );
		
		List<Object> result = query.list();
		
		endtId = ( (BigDecimal) result.get( 0 ) ).longValue();
		
		return endtId;
	}

}
