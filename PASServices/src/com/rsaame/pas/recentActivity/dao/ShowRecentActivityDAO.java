package com.rsaame.pas.recentActivity.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.RecentActivityVO;

public class ShowRecentActivityDAO extends BaseDBDAO implements IShowRecentActivityDAO{

	private static final Logger logger = Logger.getLogger( ShowRecentActivityDAO.class );

	private static final Integer SBS_POLICY_TYPE = Integer.valueOf( 3 );
	private static final Short SBS_RENEWAL_QUOTE_DOC_TYPE = new Short( "6" );
	private static final Short NEW_DOC_TYPE = new Short( "1" );
	private static final Short ENDT_DOC_TYPE = new Short( "3" );
	private static final short REN_DOC_TYPE = new Short( "2" );
	private static final String SHOW_RECENT_ENDORSEMENTS = "SHOW_RECENT_ENDORSEMENTS";
	private static final String SHOW_RECENT_QUOTES = "SHOW_RECENT_QUOTES";
	private static final String SHOW_RECENT_NEWBUSINESS = "SHOW_RECENT_NEWBUSINESS";
	private static final String SHOW_RECENT_RENEWALS = "SHOW_RECENT_RENEWALS";
	private static final String SHOW_RENEWAL_QUOTES = "SHOW_RENEWAL_QUOTES";
	

/*	RecentActivityVO recentActivityVO;
	DataHolderVO<List<RecentActivityVO>> dataHolderVO;
	List<RecentActivityVO> recentActivityVOs;*/
	
      // commented for request 143985 : Moved members to method level
	/**
	 * This method is used to get recent endorsements done by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentEndorsements( BaseVO baseVO ){

		RecentActivityVO recentActivityVO;
		DataHolderVO<List<RecentActivityVO>> dataHolderVO;
		List<RecentActivityVO> recentActivityVOs;
		
		recentActivityVO = (RecentActivityVO) baseVO;
		dataHolderVO = new DataHolderVO<List<RecentActivityVO>>();

		Date fromDate = getFromDate();
		recentActivityVOs=new ArrayList<RecentActivityVO>();
		List<TTrnPolicyQuo> ttrnPolicyQuolIst=null;
		logger.debug("********IN  showRecentEndorsements ******** userid "+recentActivityVO.getUserId());
		
		logger.debug("********IN  showRecentEndorsements ********recentActivityVOs    "+System.identityHashCode(recentActivityVOs));
		
		logger.debug("********IN  showRecentEndorsements ********dataHolderVO    "+System.identityHashCode(dataHolderVO));
		
		/*Fetching the recent activity records for Endorsements */
		if(!Utils.isEmpty( recentActivityVO )){
			if(!Utils.isEmpty( recentActivityVO.getUserId())){
				ttrnPolicyQuolIst = getHibernateTemplate().find(
						"from TTrnPolicyQuo policy where ( policy.polModifiedBy=? or policy.polUserId=? ) and " +
						"policy.polValidityStartDate > ? and policy.polIssueHour=? and " +
						"policy.polValidityExpiryDate > ? and policy.polDocumentCode=? ORDER BY policy.polIssueDate DESC, policy.polEndtNo DESC", 
						recentActivityVO.getUserId(),recentActivityVO.getUserId(), fromDate, SBS_POLICY_TYPE, fromDate,ENDT_DOC_TYPE );
				
				if(!Utils.isEmpty( ttrnPolicyQuolIst )){
					/*Mapping ttrnPolicyQuolIst to recentActivityVOs for recent endorsements*/
					mapPolicyQuoListToRecentActivityVOList( ttrnPolicyQuolIst, recentActivityVOs, SHOW_RECENT_ENDORSEMENTS );
				}
				dataHolderVO.setData( recentActivityVOs );
			}
		}
		return dataHolderVO;
	}

	/**
	 * This method is used to get recent quotes created by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentQuotes( BaseVO baseVO ){

		RecentActivityVO recentActivityVO;
		DataHolderVO<List<RecentActivityVO>> dataHolderVO;
		List<RecentActivityVO> recentActivityVOs;
		recentActivityVO = (RecentActivityVO) baseVO;
		dataHolderVO = new DataHolderVO<List<RecentActivityVO>>();

		Date fromDate = getFromDate();

		Date validityExpiryDate = SvcConstants.EXP_DATE;

		recentActivityVOs = new ArrayList<RecentActivityVO>();
		List<TTrnPolicyQuo> ttrnPolicyQuolIst = null;
		logger.debug("********IN  showRecentQuotes *******userid*"+recentActivityVO.getUserId());
		
		logger.debug("********IN  showRecentQuotes ********recentActivityVOs    "+System.identityHashCode(recentActivityVOs));
		
		logger.debug("********IN  showRecentQuotes ********dataHolderVO    "+System.identityHashCode(dataHolderVO));
		
		
		/*Fetching the recent activity records for Endorsements */
		if( !Utils.isEmpty( recentActivityVO ) ){
			if( !Utils.isEmpty( recentActivityVO.getUserId() ) ){
				ttrnPolicyQuolIst = getHibernateTemplate()
						.find( "from TTrnPolicyQuo policy where (policy.polModifiedBy=? or policy.polUserId=? ) and policy.polValidityStartDate > ? "
								+ "and policy.polIssueHour=? and policy.polValidityExpiryDate=? and policy.polStatus<>? and policy.polDocumentCode <>? and id.polEndtId in (select max(id.polEndtId) from TTrnPolicyQuo pol where pol.polQuotationNo=policy.polQuotationNo) ORDER BY policy.polIssueDate DESC, policy.polEndtNo DESC",
								recentActivityVO.getUserId(), recentActivityVO.getUserId(), fromDate, SBS_POLICY_TYPE, validityExpiryDate,
								Byte.valueOf( Utils.getSingleValueAppConfig( "CONV_TO_POL" ) ),SBS_RENEWAL_QUOTE_DOC_TYPE );
				if( !Utils.isEmpty( ttrnPolicyQuolIst ) ){
					/*Mapping ttrnPolicyQuolIst to recentActivityVOs for recent quotes*/
					mapPolicyQuoListToRecentActivityVOList( ttrnPolicyQuolIst, recentActivityVOs, SHOW_RECENT_QUOTES );
				}
				dataHolderVO.setData( recentActivityVOs );
			}
		}
		return dataHolderVO;
	}

	/**
	 * This method is used to get recent renewals done by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentRenewals( BaseVO baseVO ){

		RecentActivityVO recentActivityVO;
		DataHolderVO<List<RecentActivityVO>> dataHolderVO;
		List<RecentActivityVO> recentActivityVOs;
		
		recentActivityVO = (RecentActivityVO) baseVO;
		dataHolderVO = new DataHolderVO<List<RecentActivityVO>>();

		Date fromDate = getFromDateForRenewal();
		recentActivityVOs = new ArrayList<RecentActivityVO>();
		List<TTrnPolicyQuo> ttrnPolicyQuolIst = null;
		logger.debug("********IN  showRecentRenewals *******userid*"+recentActivityVO.getUserId());
		
		logger.debug("********IN  showRecentRenewals ********recentActivityVOs    "+System.identityHashCode(recentActivityVOs));
		
		logger.debug("********IN  showRecentRenewals ********dataHolderVO    "+System.identityHashCode(dataHolderVO));
		/*Fetching the recent activity records for Endorsements */
		if( !Utils.isEmpty( recentActivityVO ) ){
			if( !Utils.isEmpty( recentActivityVO.getUserId() ) ){
				ttrnPolicyQuolIst = getHibernateTemplate().find(
						"from TTrnPolicyQuo policy where ( policy.polModifiedBy=? or policy.polUserId=? ) and " + "policy.polValidityStartDate > ? and policy.polIssueHour=? and "
								+ "policy.polValidityExpiryDate > ? and policy.polDocumentCode=? ORDER BY policy.polIssueDate DESC, policy.polEndtNo DESC", recentActivityVO.getUserId(), recentActivityVO.getUserId(), fromDate,
						SBS_POLICY_TYPE, fromDate, REN_DOC_TYPE );

				if( !Utils.isEmpty( ttrnPolicyQuolIst ) ){
					/*Mapping ttrnPolicyQuolIst to recentActivityVOs for recent renewals*/
					mapPolicyQuoListToRecentActivityVOList( ttrnPolicyQuolIst, recentActivityVOs, SHOW_RECENT_RENEWALS );
				}
				dataHolderVO.setData( recentActivityVOs );
			}
		}
		return dataHolderVO;
	}

	/**
	 * This method is used to get recent quotes created by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRenewalQuotes( BaseVO baseVO ){

		
		RecentActivityVO recentActivityVO;
		DataHolderVO<List<RecentActivityVO>> dataHolderVO;
		List<RecentActivityVO> recentActivityVOs;
		recentActivityVO = (RecentActivityVO) baseVO;
		dataHolderVO = new DataHolderVO<List<RecentActivityVO>>();

		//Date fromDate = getFromDate();

		Date validityExpiryDate = SvcConstants.EXP_DATE;

		recentActivityVOs = new ArrayList<RecentActivityVO>();
		List<TTrnPolicyQuo> ttrnPolicyQuolIst = null;
		logger.debug("********IN  showRenewalQuotes ******userid**"+recentActivityVO.getUserId());
		
		logger.debug("********IN  showRenewalQuotes ********recentActivityVOs    "+System.identityHashCode(recentActivityVOs));
		
		logger.debug("********IN  showRenewalQuotes ********dataHolderVO    "+System.identityHashCode(dataHolderVO));
		
		
		/*Fetching the recent activity records for Endorsements */
		if( !Utils.isEmpty( recentActivityVO ) ){
			if( !Utils.isEmpty( recentActivityVO.getUserId() ) ){
				ttrnPolicyQuolIst = getHibernateTemplate()
						.find( "from TTrnPolicyQuo policy where (policy.polModifiedBy=? or policy.polUserId=? ) and policy.polDocumentCode = ? "
								+ "and policy.polIssueHour=? and policy.polValidityExpiryDate=? and policy.polStatus<>? and id.polEndtId in (select max(id.polEndtId) from TTrnPolicyQuo pol where pol.polQuotationNo=policy.polQuotationNo) ORDER BY policy.polIssueDate DESC, policy.polEndtNo DESC",
								recentActivityVO.getUserId(), recentActivityVO.getUserId(), SBS_RENEWAL_QUOTE_DOC_TYPE, SBS_POLICY_TYPE, validityExpiryDate,
								Byte.valueOf( Utils.getSingleValueAppConfig( "CONV_TO_POL" ) ) );
				if( !Utils.isEmpty( ttrnPolicyQuolIst ) ){
					/*Mapping ttrnPolicyQuolIst to recentActivityVOs for recent quotes*/
					mapPolicyQuoListToRecentActivityVOList( ttrnPolicyQuolIst, recentActivityVOs, SHOW_RENEWAL_QUOTES );
				}
				dataHolderVO.setData( recentActivityVOs );
			}
		}
		return dataHolderVO;
	}
	
	/**
	 * This method is used to get recent policy created by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentNewBusiness( BaseVO baseVO ){
		RecentActivityVO recentActivityVO;
		DataHolderVO<List<RecentActivityVO>> dataHolderVO;
		List<RecentActivityVO> recentActivityVOs;
		recentActivityVO = (RecentActivityVO) baseVO;
		dataHolderVO = new DataHolderVO<List<RecentActivityVO>>();

		Date fromDate = getFromDate();
		//Date validityExpiryDate = SvcConstants.EXP_DATE;

		recentActivityVOs = new ArrayList<RecentActivityVO>();
		List<TTrnPolicyQuo> ttrnPolicyQuolIst = null;
		
		
		
		logger.debug("********IN  showRecentNewBusiness *****userid***"+recentActivityVO.getUserId());
		
		logger.debug("********IN  showRecentNewBusiness ********recentActivityVOs    "+System.identityHashCode(recentActivityVOs));
		
		logger.debug("********IN  showRecentNewBusiness ********dataHolderVO    "+System.identityHashCode(dataHolderVO));
		
		/*Fetching the recent activity records for Endorsements */
		if( !Utils.isEmpty( recentActivityVO ) ){
			if( !Utils.isEmpty( recentActivityVO.getUserId() ) ){

				ttrnPolicyQuolIst = getHibernateTemplate().find(
						"from TTrnPolicyQuo policy where (policy.polModifiedBy=? or policy.polUserId=?) and "
								+ "policy.polValidityStartDate > ? and policy.polIssueHour=? and policy.polDocumentCode=? ORDER BY policy.polIssueDate DESC, policy.polEndtNo DESC", recentActivityVO.getUserId(),
						recentActivityVO.getUserId(), fromDate, SBS_POLICY_TYPE, NEW_DOC_TYPE );

				if( !Utils.isEmpty( ttrnPolicyQuolIst ) ){

					/*Mapping ttrnPolicyQuolIst to recentActivityVOs for recent policies*/
					mapPolicyQuoListToRecentActivityVOList( ttrnPolicyQuolIst, recentActivityVOs, SHOW_RECENT_NEWBUSINESS );
				}
				dataHolderVO.setData( recentActivityVOs );

			}
		}
		return dataHolderVO;
	}

	/**
	 * This method is used to map policyQuoList to RecentAvtivityVO LIst 
	 * @param ttrnPolicyQuolIst 
	 * @param recentActivityVOs 
	 * @param recentActiTransName 
	 */
	private void mapPolicyQuoListToRecentActivityVOList( List<TTrnPolicyQuo> ttrnPolicyQuolIst, List<RecentActivityVO> recentActivityVOs, String recentActiTransName ){

		/*Iterating through all the policyQuo results for Recent Activities*/
		for( TTrnPolicyQuo policyQuo : ttrnPolicyQuolIst ){
			if( !Utils.isEmpty( policyQuo ) ){

				/*This flag is used to indicate whether current recent activity record information has already been captured in 
				 * recentActivityVOs list*/
				boolean flag = false;
				RecentActivityVO activityVO = new RecentActivityVO();
				String lobDesc = null;
				if( recentActiTransName.equals( SHOW_RECENT_QUOTES ) )
				{
					flag = checkIfInfoCaptured( policyQuo.getPolQuotationNo(), policyQuo.getId().getPolEndtId(), recentActivityVOs, policyQuo );
					activityVO.setQuotePolicyNumber( policyQuo.getPolQuotationNo() );
					
					String policyType = policyQuo.getPolPolicyType().toString();
					if(policyType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )){
						policyType = SvcConstants.SHORT_TRAVEL_POL_TYPE;
					}
					lobDesc = SvcUtils.getLookUpDescription( com.Constant.CONST_PAS_LOBMST, "ALL", "ALL", Integer.parseInt( policyType ) );
					activityVO.setLob( lobDesc );
				}
				else if( recentActiTransName.equals( SHOW_RECENT_NEWBUSINESS ) ){
					flag = checkIfInfoCaptured( policyQuo.getPolPolicyNo(), policyQuo.getId().getPolEndtId(), recentActivityVOs, policyQuo );
					activityVO.setQuotePolicyNumber( policyQuo.getPolPolicyNo() );
					
					String policyType = policyQuo.getPolPolicyType().toString();
					if(policyType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )){
						policyType = SvcConstants.SHORT_TRAVEL_POL_TYPE;
					}
					lobDesc = SvcUtils.getLookUpDescription( com.Constant.CONST_PAS_LOBMST, "ALL", "ALL", Integer.parseInt( policyType ) );
					activityVO.setLob( lobDesc );
				}
				else if( recentActiTransName.equals( SHOW_RECENT_ENDORSEMENTS ) ){
					flag = checkIfInfoCaptured( policyQuo.getPolPolicyNo(), policyQuo.getId().getPolEndtId(), recentActivityVOs, policyQuo );
					activityVO.setQuotePolicyNumber( policyQuo.getPolPolicyNo() );

					String policyType = policyQuo.getPolPolicyType().toString();
					if(policyType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )){
						policyType = SvcConstants.SHORT_TRAVEL_POL_TYPE;
					}
					lobDesc = SvcUtils.getLookUpDescription( com.Constant.CONST_PAS_LOBMST, "ALL", "ALL", Integer.parseInt( policyType ) );
					activityVO.setLob( lobDesc );
				}
				else if( recentActiTransName.equals( SHOW_RECENT_RENEWALS ) ){
					flag = checkIfInfoCaptured( policyQuo.getPolPolicyNo(), policyQuo.getId().getPolEndtId(), recentActivityVOs, policyQuo );
					
					activityVO.setQuotePolicyNumber( policyQuo.getPolPolicyNo() );

					String policyType = policyQuo.getPolPolicyType().toString();
					if(policyType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )){
						policyType = SvcConstants.SHORT_TRAVEL_POL_TYPE;
					}
					lobDesc = SvcUtils.getLookUpDescription( com.Constant.CONST_PAS_LOBMST, "ALL", "ALL", Integer.parseInt( policyType ) );
					activityVO.setLob( lobDesc );
				}
				else if( recentActiTransName.equals( SHOW_RENEWAL_QUOTES ) )
				{
					flag = checkIfInfoCaptured( policyQuo.getPolQuotationNo(), policyQuo.getId().getPolEndtId(), recentActivityVOs, policyQuo );
					activityVO.setQuotePolicyNumber( policyQuo.getPolQuotationNo() );
					
					String policyType = policyQuo.getPolPolicyType().toString();
					if(policyType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )){
						policyType = SvcConstants.SHORT_TRAVEL_POL_TYPE;
					}
					lobDesc = SvcUtils.getLookUpDescription( com.Constant.CONST_PAS_LOBMST, "ALL", "ALL", Integer.parseInt( policyType ) );
					activityVO.setLob( lobDesc );
				}
				if( flag ) 
				{
					continue;
				}

				activityVO.setInsuredCode( policyQuo.getPolInsuredCode() );

				/*Getting scheme information and setting it to Recent Activity record*/
				if( !Utils.isEmpty( policyQuo.getPolCoverNoteHour() ) ){
					String schemeName = SvcUtils.getLookUpDescription( "SCHEME", "ALL", "ALL", Integer.valueOf( policyQuo.getPolCoverNoteHour() ) );
					activityVO.setSchemeCode( policyQuo.getPolCoverNoteHour() );
					activityVO.setSchemeName( schemeName );
				}
				activityVO.setSchemeCode( policyQuo.getPolCoverNoteHour() );
				activityVO.setUserId( policyQuo.getPolUserId() );

				/*Getting policy status setting it to Recent Activity record*/
				if( !Utils.isEmpty( policyQuo.getPolStatus() ) ){
					String status = SvcUtils.getLookUpDescription( "STATUS", "ALL", "ALL", Integer.valueOf( policyQuo.getPolStatus() ) );
					activityVO.setStatusCode( policyQuo.getPolStatus() );
					activityVO.setStatusDesc( status );
				}
				
				/*Getting policy premium setting it to Recent Activity record*/
				if( !Utils.isEmpty( policyQuo.getPolPremium() ) ){
					String policyType = policyQuo.getPolPolicyType().toString();
					if( policyType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE ) ){
						policyType = SvcConstants.SHORT_TRAVEL_POL_TYPE;
					}
					lobDesc = SvcUtils.getLookUpDescription( com.Constant.CONST_PAS_LOBMST, "ALL", "ALL", Integer.parseInt( policyType ) );
					BigDecimal totalPremium = policyQuo.getPolPremium();
					if( !Utils.isEmpty( policyQuo.getPolGovernmentTax() ) ){
						totalPremium = totalPremium.add( policyQuo.getPolGovernmentTax() );
					}
					if( !Utils.isEmpty( policyQuo.getPolPolicyFees() ) ){
						totalPremium = totalPremium.add( policyQuo.getPolPolicyFees() );
					}
					
					/*VAT*/
					if( !Utils.isEmpty( policyQuo.getPolVatTax() ) ){
						totalPremium = totalPremium.add( policyQuo.getPolVatTax() );
					}
					activityVO.setPolPremium( Currency.getFormattedCurrency( totalPremium , "SBS" ) );
				}
				
				SimpleDateFormat generalDateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
				activityVO.setEffectiveDate( generalDateFormat.format(  policyQuo.getPolEffectiveDate() ) );
				activityVO.setExpiryDate( policyQuo.getPolExpiryDate() );

				activityVO.setEndtID(policyQuo.getId().getPolEndtId());
				// Added for Oman multibranching implementation
				activityVO.setDocCode(policyQuo.getPolDocumentCode());
				activityVO.setPolLocCode(policyQuo.getPolLocationCode());
				//if( !recentActiTransName.equals(SHOW_RECENT_NEWBUSINESS) ) 
				//Fix : Bug 696-Low	Add insured name column in new business tab - Recent activity
					setInsuredName(activityVO,policyQuo);
				
				recentActivityVOs.add( activityVO );

			}
		}
	}

	/**
	 * This method is used to get the insured name and set it to recentActivityVO
	 * @param activityVO
	 * @param policyQuo
	 */
	private void setInsuredName( RecentActivityVO activityVO, TTrnPolicyQuo policyQuo ){
		String cacheKey = String.valueOf( policyQuo.getPolInsuredCode() );
		String insuredName = (String) CacheManagerFactory.getCacheManager().get( PASCache.INSURED, cacheKey );

		if( Utils.isEmpty( insuredName ) && !CacheManagerFactory.getCacheManager().hasCachedData( PASCache.INSURED, cacheKey ) ){
			List<TMasCashCustomerQuo> cashCustomerQuos = getHibernateTemplate().find(
					"from TMasCashCustomerQuo cshCustomer " + "where cshCustomer.id.cshPolicyId=? and cshCustomer.id.cshValidityStartDate=? and cshCustomer.cshInsuredId=?",
					policyQuo.getId().getPolPolicyId(), policyQuo.getPolValidityStartDate(), policyQuo.getPolInsuredId() );
			if( !Utils.isEmpty( cashCustomerQuos ) ){
				TMasCashCustomerQuo cashCustomerQuo = cashCustomerQuos.get( 0 );
				activityVO.setInsuredName( cashCustomerQuo.getCshEName1() );
			}

			CacheManagerFactory.getCacheManager().put( PASCache.INSURED, cacheKey, activityVO.getInsuredName() );
		}
		else{
			activityVO.setInsuredName( insuredName );
		}
	}

	/**
	 * This method is used to check if recent activity information has already been captured 
	 * @param quotePOlicyNumber
	 * @param l 
	 * @param recentActivityVOs
	 * @return
	 */
	private boolean checkIfInfoCaptured( Long quotePOlicyNumber, Long endtId, List<RecentActivityVO> recentActivityVOs,TTrnPolicyQuo policyQuo ){
		boolean flag = false;
		for( RecentActivityVO activityVO : recentActivityVOs ){
			if( quotePOlicyNumber.equals( activityVO.getQuotePolicyNumber() ) && endtId.equals( activityVO.getEndtID() ) )
			{
				if(!Utils.isEmpty(activityVO.getPolPremium()) && !Utils.isEmpty(policyQuo.getPolPremium()))
				{
					BigDecimal totPremium = new BigDecimal( activityVO.getPolPremium().replaceAll( "[,]", "" ) ).add( policyQuo.getPolPremium() );
					if( !Utils.isEmpty( policyQuo.getPolGovernmentTax() ) ){
						totPremium = totPremium.add( policyQuo.getPolGovernmentTax() );
					}
					/*VAT*/
					if( !Utils.isEmpty( policyQuo.getPolVatTax() ) ){
						totPremium = totPremium.add( policyQuo.getPolVatTax() );
					}
					if( !Utils.isEmpty( policyQuo.getPolPolicyFees() ) ){
						totPremium = totPremium.add( policyQuo.getPolPolicyFees() );
					}
					activityVO.setPolPremium( totPremium.toString() );
				}
				flag = true;
				break;
			}
		}
		return flag;

	}
	
	

	/**
	 * This method is used to form From Date for recent activity on the basis of recent activity criteria
	 * @return Date
	 */
	private Date getFromDate(){
		Integer recentActivityCriteria = new Integer( Utils.getSingleValueAppConfig( "Recent_Activity_Criteria" ) );

		Calendar calFromDate = Calendar.getInstance();
		calFromDate.add( Calendar.DATE, -recentActivityCriteria );
		Date fromDate = calFromDate.getTime();

		return fromDate;
	}

	/**
	 * This method is used to form From Date for recent activity on the basis of recent activity criteria
	 * @return Date
	 */
	private Date getFromDateForRenewal(){
		Integer recentActivityCriteria = new Integer( Utils.getSingleValueAppConfig( "Renewal_Activity_Criteria" ) );

		Calendar calFromDate = Calendar.getInstance();
		calFromDate.add( Calendar.DATE, -recentActivityCriteria );
		Date fromDate = calFromDate.getTime();

		return fromDate;
	}
}
