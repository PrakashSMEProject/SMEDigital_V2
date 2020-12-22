/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.util.EncryptionUtil;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TMasBroadcastMsg;
import com.rsaame.pas.dao.model.TMasCommisionType;
import com.rsaame.pas.dao.model.TMasPromoDiscCover;
import com.rsaame.pas.dao.model.TMasPromotionalCode;
import com.rsaame.pas.dao.model.TMasTariff;
import com.rsaame.pas.dao.model.TMasUser;
import com.rsaame.pas.dao.model.TMasUserDetail;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnPolicyCondition;
import com.rsaame.pas.dao.model.TTrnPolicyConditionQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.CommissionVO;
import com.rsaame.pas.vo.app.CommissionVOList;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.ForgotPwdDetailsVO;
import com.rsaame.pas.vo.app.NoticeBoardVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author m1014644
 *
 */
public class CommonOpDAO extends BaseDBDAO implements ICommonOpDAO{

	private final static Logger LOGGER = Logger.getLogger( CommonOpDAO.class );
	private final static String PAR_COMMISSION_Q = "from TMasCommisionType comType where comType.comPtCode = 50 and comType.comScheme=? and comType.comChannelName=? ";
	private final static Integer ZERO = 0;
	private final static String QUOTE_SEQ_SBS = "SEQ_SBS_QUO_NO";
	private final static String INSUREDID_SEQ_SBS = "SEQ_INSURED_ID";
	
	private static final String GET_USER_DETAILS = "from TMasUser user where user.userId=?";
	private static final String GET_REFERAL_TEXT = "from TTrnTempPasReferral referal WHERE referal.id.tprPolLinkingId=? and referal.id.tprSecId= ?";
	private static final String MAX_NUMBER_LOC_REF_TEXT=Utils.getSingleValueAppConfig("numberOfLocation");
	
	private static final String FETCH_REC_FOR_QUOTE = " from TTrnPolicyQuo polQuo where polQuo.polQuotationNo = ? and polQuo.polLocationCode = ? and  polQuo.id.polEndtId =? and polQuo.polIssueHour = ?";
	private static final String FETCH_REC_FOR_POLICY = " from TTrnPolicyQuo polQuo where polQuo.polPolicyNo = ? and polQuo.polLocationCode = ? and polQuo.id.polEndtId =? and polQuo.polIssueHour = ?";
	private static final String FETCH_REC_DATA_EXTENDED_DOC_CODE = " and polQuo.polDocumentCode=?";
	private static final String FETCH_REC_DATA_EXTENDED_EFF_DATE = " and pol_effective_date = ?";
	private static final String REN_QUOTE_RETRIEVEL_MSG1=Utils.getSingleValueAppConfig("REN_QUOTE_RETRIEVEL_MSG1");
	private static final String REN_QUOTE_RETRIEVEL_MSG2=Utils.getSingleValueAppConfig("REN_QUOTE_RETRIEVEL_MSG2");
	private static final String REN_QUOTE_RETRIEVEL_MSG3=Utils.getSingleValueAppConfig("REN_QUOTE_RETRIEVEL_MSG3");



	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.ICommonOpDAO#getCommision(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO getCommision( BaseVO baseVO ){
		if( LOGGER.isInfo() ) LOGGER.info( "Entering  getCommision to get the commission info" );
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){

				PolicyVO policyVO = (PolicyVO) baseVO;
				Short schemeCode = null;

				CommissionVOList commissionVOList = new CommissionVOList();

				/* Obtain channel name from the below method which will either be null/Broker Id/Agent Id */
				String channelNameKey = getKeyForCommisionCacheObj( policyVO );

				/* Below method will be returning cache object if at all cache holds commission object in 
				 * case the key passed is already stored to cache */

				Object cacheObj = getCachedCommisionObject( policyVO );

				if( !Utils.isEmpty( cacheObj ) ){
					commissionVOList = (CommissionVOList) cacheObj;
				}
				else{

					if( !Utils.isEmpty( policyVO.getScheme() ) && !Utils.isEmpty( policyVO.getScheme().getSchemeCode() ) ){
						schemeCode = policyVO.getScheme().getSchemeCode().shortValue(); // commission is fetched based on scheme
					}
					else{
						throw new BusinessException( "pas.schemeCode.null", null, "Scheme code is null" );
					}

					/* Obtain commision value from table basis scheme and channel name which will be broker id/agent id */
					getCommisionFromCommisionType( policyVO, commissionVOList, channelNameKey );

					/* Set the commisionList obtained to cache */
					putCommsionListToCache( commissionVOList, policyVO );

				}
				if( LOGGER.isDebug() ) LOGGER.debug( "The commission for scheme " + schemeCode + " is" + commissionVOList );
				if( LOGGER.isInfo() ) LOGGER.info( "Exiting  getCommision to get the commission info" );
				return commissionVOList;
			}
		}
		return baseVO;
	}

	@Override
	public BaseVO setPrepackedFlag( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){

				PolicyVO policyVO = (PolicyVO) baseVO;
				List<TMasTariff> tMasTariff = null;
				if( !Utils.isEmpty( policyVO.getScheme() ) ){

					/* fetch the details for the given tariff code and update the corresponding flag in PolicyVO */
					if( !Utils.isEmpty( policyVO.getScheme().getTariffCode() ) ){
						tMasTariff = getHibernateTemplate().find( "from TMasTariff trf where trf.tarCode = ?",
								Short.valueOf( String.valueOf( ( policyVO.getScheme().getTariffCode() ) ) ) );
					}
					if( !Utils.isEmpty( tMasTariff ) ){

						String tariffFlag = tMasTariff.get( ZERO ).getTarPrePkgFlag();
						if( !Utils.isEmpty( tariffFlag ) ){
							if( tariffFlag.equalsIgnoreCase( "Y" ) ){
								policyVO.setIsPrepackaged( true );
							}
						}
						/** SK : Changes */
						/**
						 * if else is not added here then if the user after selecting prepackage tariff
						 * switches to flexi pack tariff then policyVO which was updated with prepackaged
						 * flag as "Y" will continue to retain that value and proceed further will 
						 * result in exception
						 */
						else{
							policyVO.setIsPrepackaged( false );
						}
					}
				}
				return policyVO;
			}
		}
		return baseVO;
	}

	@Override
	public BaseVO getPolLinkID( BaseVO baseVO ){
		DataHolderVO<Object[]> data = (DataHolderVO<Object[]>) baseVO;
		Object[] LinkIdSrcCre = data.getData();
		Long quoPolDetails = (Long) LinkIdSrcCre[ 0 ];
		Long endID = (Long) LinkIdSrcCre[ 1 ];
		String polEffectiveDate = (String) LinkIdSrcCre[ 2 ];
		String polExpiryDate = (String) LinkIdSrcCre[ 3];
		Integer docCode= (Integer)LinkIdSrcCre[ 4];
		// If date is not in dd/MM/yyyy format, then need to convert it to dd/MM/yyyy format
		Date formatDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
		//Sonar Fix for "indexOf" should not be for positive numbers
		if(polEffectiveDate.indexOf( "," )>-1){
			  formatDate = new Date(polEffectiveDate);
			  polEffectiveDate =  sdf.format(formatDate);
		}
		//Sonar Fix for "indexOf" should not be for positive numbers
		if(polExpiryDate.indexOf( "," )>-1){
			 formatDate = new Date(polExpiryDate);
			 polExpiryDate= sdf.format(formatDate);
		} 

		String polLinkingId = null;
		String GET_LINKING_ID = "select POL_LINKING_ID from t_trn_policy_quo  where POL_QUOTATION_NO=" + quoPolDetails + " and POL_ENDT_ID=" + endID+
		" and pol_document_code = "+docCode+
		" and to_char(pol_effective_date, 'dd/mm/yyyy') = '"+polEffectiveDate+
		//" and NVL(TO_CHAR (pol_endt_effective_date, 'DD/MM/YYYY'),TO_CHAR (pol_effective_date, 'DD/MM/YYYY'))= '"+polEffectiveDate+
		"' and to_char(pol_expiry_date, 'dd/mm/yyyy')= '"+polExpiryDate+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( GET_LINKING_ID );
		List<Object> result = query.list();
		String quotePolFlag = SvcConstants.QUOTE_FLOW;
		/*If Result is Null , check in Policy table whether Linking Id is present.
		 * Reason is that we do not know at this point whether its a policy flow or quote flow*/
		if( Utils.isEmpty( result ) ){
			GET_LINKING_ID = "select POL_LINKING_ID from t_trn_policy  where POL_POLICY_NO=" + quoPolDetails + " and POL_ENDT_ID=" + endID+
			//" and to_char(pol_effective_date, 'dd/mm/yyyy') = '"+polEffectiveDate+
			//" and NVL(TO_CHAR (pol_endt_effective_date, 'DD/MM/YYYY'),TO_CHAR (pol_effective_date, 'DD/MM/YYYY'))= '"+polEffectiveDate+
			" and pol_document_code = "+docCode+
			" and (to_char(pol_effective_date, 'dd/mm/yyyy') = '"+polEffectiveDate+"' or to_char(pol_endt_effective_date, 'dd/mm/yyyy') = '"+polEffectiveDate+"')"+
			" and to_char(pol_expiry_date, 'dd/mm/yyyy')= '"+polExpiryDate+"'";
			query = session.createSQLQuery( GET_LINKING_ID );
			result = query.list();
			quotePolFlag = SvcConstants.POL_FLOW;
		}
		DataHolderVO<List<String>> linkingID = new DataHolderVO<List<String>>();
		List<String> dataList = new ArrayList<String>();
		if( !Utils.isEmpty( result ) ){
			Iterator<Object> itr = result.iterator();
			while( itr.hasNext() ){
				polLinkingId = itr.next().toString();
				break;
			}
			//List<TTrnPolicyQuo> trnPolicyQuos = getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polQuotationNo=? and ttrnPol.id.polEndtId=?", quoPolDetails,endID );

			if( !Utils.isEmpty( polLinkingId ) ){
				/* TODO When Kaizen is merged into PAS, this may have to given a relook. In Kaizen, policies are monoline
				 * and, hence, policy linking Id will be 0 there. */
				if( polLinkingId.equalsIgnoreCase( "0" ) ){
					throw new BusinessException( "pas.cmn.polLinkindIs0", null, "Policy Linking ID is found to be 0 for the quote" );
				}
				dataList.add( polLinkingId );
				dataList.add( quotePolFlag );
				linkingID.setData( dataList );
			}
		}
		else{
			throw new BusinessException( "", null, "Policy Linking ID was not available for the quo /policy" );
		}

		return linkingID;
	}
	
	/**
	 * This method will return cached Commision Object if it exists for the key
	 * @param policyVO
	 * @return
	 */
	public Object getCachedCommisionObject( PolicyVO policyVO ){
		String brokerId = null;
		String agentId = null;
		String tariffCode = null;
		Object cachedObject = null;

		/*
		 * Added new criteria while fetching the commission value. This criteria is required as different broker/Agent can
		 * have different commission configured for the same scheme and policy type combination
		 */
		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ){
			brokerId = policyVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString();
		}

		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ) ){
			agentId = policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().toString();
		}

		if( !Utils.isEmpty( policyVO.getScheme() ) && !Utils.isEmpty( policyVO.getScheme().getTariffCode() ) ){
			tariffCode = policyVO.getScheme().getTariffCode().toString();
		}
		
		/* Caching the commission value will be basis the key which will something like below 
		 * - COMMISSIONS_BROKER_<broker_id>_TARIFF_<tariffCode> or COMMISSIONS_BROKER_<agent_id>_TARIFF_<tariffCode> in case broker id exists
		 * 	 and in case agent id exists respectively
		 * */

		if( !Utils.isEmpty( brokerId ) && !Utils.isEmpty( tariffCode ) ){
			cachedObject = CacheManagerFactory.getCacheManager().get( PASCache.GENERAL, Utils.concat( SvcConstants.COMMISSIONS, "_", "BROKER", "_", brokerId, "_", com.Constant.CONST_TARIFF, tariffCode ) );
			//if( LOGGER.isDebug() ) LOGGER.debug( "Cached Commission Object for broker  [ " + brokerId + " ] id is [ " + cachedObject.toString() + " ]" );
		}

		if( !Utils.isEmpty( agentId ) && !Utils.isEmpty( tariffCode ) ){
			cachedObject = CacheManagerFactory.getCacheManager().get( PASCache.GENERAL, Utils.concat( SvcConstants.COMMISSIONS, "_", "AGENT", "_", agentId, "_", com.Constant.CONST_TARIFF, tariffCode ) );
			//if( LOGGER.isDebug() ) LOGGER.debug( "Cached Commission Object for Agent  [ " + agentId + " ] id is [ " + cachedObject.toString() + " ]" );
		}

		return cachedObject;
	}
	
	/**
	 * 
	 * @param policyVO
	 * @return
	 */
	public CommissionVOList getCommisionFromCommisionType(PolicyVO policyVO, CommissionVOList commisionVOList, String channelNameKeyVal){
		String brokerId = null;
		String agentId = null;
		
		List<CommissionVO> commissionVOs = new com.mindtree.ruc.cmn.utils.List<CommissionVO>( CommissionVO.class );
		
		
		List<TMasCommisionType> commision = getHibernateTemplate().find( PAR_COMMISSION_Q, Integer.valueOf( policyVO.getScheme().getSchemeCode().shortValue() ), channelNameKeyVal );

		for( TMasCommisionType commissionType : commision ){
			CommissionVO commisionVO = new CommissionVO();
			commisionVO.setComCode( commissionType.getId().getComCode() );
			commisionVO.setClassCode( commissionType.getId().getComClCode() );
			commisionVO.setComPrec( Double.valueOf( commissionType.getComPerc().toPlainString() ) );
			commisionVO.setComDescription( commissionType.getComEDesc() );
			commisionVO.setComADescription( commissionType.getComADesc() );
			commissionVOs.add( commisionVO );
		}

		commisionVOList.setCommision( commissionVOs );
		
		return commisionVOList;
	}
	
	/**
	 * 
	 * @param commisionVOList
	 */
	public void putCommsionListToCache(CommissionVOList commisionVOList, PolicyVO policyVO){
		
		String brokerId = null;
		String agentId = null;
		String tariffCode = null;
		Object cachedObject = null;

		/*
		 * Added new criteria while fetching the commission value. This criteria is required as different broker/Agent can
		 * have different commission configured for the same scheme and policy type combination
		 */
		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ){
			brokerId = policyVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString();
		}

		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ) ){
			agentId = policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().toString();
		}

		if( !Utils.isEmpty( policyVO.getScheme() ) && !Utils.isEmpty( policyVO.getScheme().getTariffCode() ) ){
			tariffCode = policyVO.getScheme().getTariffCode().toString();
		}
		
		/* Caching the commission value will be basis the key which will something like below 
		 * - COMMISSIONS_BROKER_<broker_id>_TARIFF_<tariffCode> or COMMISSIONS_BROKER_<agent_id>_TARIFF_<tariffCode> in case broker id exists
		 * 	 and in case agent id exists respectively
		 * */
	
		if( !Utils.isEmpty( brokerId ) && !Utils.isEmpty( tariffCode ) ){
			CacheManagerFactory.getCacheManager().put( PASCache.GENERAL, Utils.concat( SvcConstants.COMMISSIONS, "_", "BROKER", "_", brokerId, "_", com.Constant.CONST_TARIFF, tariffCode ), commisionVOList);
		}

		if( !Utils.isEmpty( agentId ) && !Utils.isEmpty( tariffCode ) ){
			CacheManagerFactory.getCacheManager().put( PASCache.GENERAL, Utils.concat( SvcConstants.COMMISSIONS, "_", "AGENT", "_", agentId, "_", com.Constant.CONST_TARIFF, tariffCode ), commisionVOList );
		}
		
		CacheManagerFactory.getCacheManager().put( PASCache.GENERAL, SvcConstants.COMMISSIONS, commisionVOList );
	}
	
	/**
	 * 
	 * @param policyVO
	 * @return
	 */
	public String getKeyForCommisionCacheObj(PolicyVO policyVO){
		
		String brokerId = null;
		String agentId = null;

		/*
		 * Added new criteria while fetching the commission value. This criteria is required as different broker/Agent can
		 * have different commission configured for the same scheme and policy type combination
		 */
		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ){
			brokerId = policyVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString();
		}

		if( !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ) ){
			agentId = policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().toString();
		}
		
		String channelNameKeyVal = !Utils.isEmpty( brokerId )?brokerId:agentId ;
		
		return channelNameKeyVal;
	}
	
	/**
	 * Method to generate a quotation number using sequence
	 */
	@Override
	public BaseVO generateQuotationNo( BaseVO baseVO ){
		PolicyVO polVo = (PolicyVO)baseVO;
		Integer issueLoc  = polVo.getGeneralInfo().getAdditionalInfo().getIssueLoc();
		DataHolderVO<Long> data = new DataHolderVO<Long>();
		//data.setData( NextSequenceValue.getNextSequence( QUOTE_SEQ_SBS, getHibernateTemplate() ) );
		data.setData( NextSequenceValue.getNextSequence( QUOTE_SEQ_SBS, Utils.getSingleValueAppConfig( "TRAN_TYPE_QUO" ),issueLoc,getHibernateTemplate() ) );
		return data;
	}

	/**
	 * Method to generate a Insured Id using sequence
	 */
	@Override
	public BaseVO generateInsuredId( BaseVO baseVO ){
		DataHolderVO<Long> data = new DataHolderVO<Long>();
		//data.setData( NextSequenceValue.getNextSequence( INSUREDID_SEQ_SBS, getHibernateTemplate() ) );
		data.setData( NextSequenceValue.getNextSequence (INSUREDID_SEQ_SBS,null,null,getHibernateTemplate() ) );
		return data;
	}
	
	/**
	 * Method to get user details using a user_id
	 */
	@Override
	public BaseVO getUserDetails( Integer userId ){
		List<TMasUser> userList = getHibernateTemplate().find( GET_USER_DETAILS, userId );
		TMasUser userDetails = new TMasUser();
		if( !Utils.isEmpty( userList ) ){
			userDetails = userList.get( 0 );
		}
		DataHolderVO<TMasUser> userData = new DataHolderVO<TMasUser>();
		userData.setData( userDetails );
		return userData;
	}

	/**
	 * Method to update the status of quote to pending status if the expired quote is edited.
	 */
	@Override
	public BaseVO editQuoteUpdateStatusToPending( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){

				PolicyVO policyVO = (PolicyVO) baseVO;
				if( LOGGER.isInfo() ) LOGGER.info( "Executing proc proc_edit_quote_update_status for updating staus of quote : START" );
				PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "editQuoteUpdateStatusProc" );
				Map results = sp.call( policyVO.getPolLinkingId(), policyVO.getEndtId() );
				if( Utils.isEmpty( results ) ){
					LOGGER.debug( "The result of the stored procedure is null" );
				}
				String[] editQuoteAllowedStatus = Utils.getMultiValueAppConfig( "EDIT_QUOTE_ALLOWED_STATUS", "," );

				/* Edit quote allowed in status 5,1,21,22,23
				 * */
				if( !Utils.isEmpty( editQuoteAllowedStatus ) && editQuoteAllowedStatus.length > 0
						&& CopyUtils.asList( editQuoteAllowedStatus ).contains( policyVO.getStatus().toString() ) ){
					policyVO.setStatus( SvcConstants.POL_STATUS_PENDING );
				}

				if( LOGGER.isInfo() ) LOGGER.info( "Executing proc_edit_quote_update_status for updating staus of quote : DONE" );
				return policyVO;
			}
		}
		return baseVO;
	}
	
	/**
	 * Checks if there are referral messages to be processed. If there are, then the existing records for the section-location
	 * are cleared and the referral data available in <code>policyVO</code> are inserted.
	 * 
	 * @param policyVO
	 */
	@Override
	public BaseVO handleReferralMessages(BaseVO baseVO){
		
		DataHolderVO<List> dataVO=null;
		if(!Utils.isEmpty(baseVO))
			dataVO=(DataHolderVO<List>)baseVO;
		List dataList=null;
		SectionVO section = null;
		if(!Utils.isEmpty( dataVO )){
			 dataList=dataVO.getData();
		}	
		Integer sectionId=null;
		PolicyVO policyVO=null;
		Integer userId=null;
		String role=null;
		if(!Utils.isEmpty(dataList)){
			policyVO=(PolicyVO)dataList.get(0);
			sectionId=(Integer)dataList.get(1);
			
			/*Start of ticket 137704 */
			userId=(Integer)dataList.get(2);
			role=(String)dataList.get(3);
			/*End of ticket 137704 */

			
		}
		if(!Utils.isEmpty(sectionId))
			section = PolicyUtils.getSectionVO( policyVO, sectionId );
		if(!(Utils.isEmpty(section))){ // SONARFIX -- if false then only execute the block -- return null stmt removed;
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		
		/* To delete the existing records from temp referral table before actually checking if the referral is
		 * triggered for this insert/update operation which is done by checking is policyVO.getMapReferralVO is not
		 * null  */
	    DAOUtils.deleteReferralRecordsForKey( policyVO.getPolLinkingId(), Long.valueOf( locationDetails.getRiskGroupId() ) , sectionId , getHibernateTemplate()  );
		//HibernateTemplate ht = getHibernateTemplate();			
		//DAOUtils.deleteReferralRecords( policyVO.getPolLinkingId(), sectionId , ht );
	
		
		/*
		 * The risk group id is required in RefferalRH for identifying the location for which the referral was invoked
		 * since, after save operation the location processing id is cleared, setting the risk ID in ThreadLevelContext
		 * which is used in ReferralRH to identify the location for which referral was invoked 
		 */
		
		com.rsaame.pas.cmn.context.ThreadLevelContext.set( "RISK_GROUP_ID" , locationDetails.getRiskGroupId());
		
		if( !Utils.isEmpty( policyVO.getMapReferralVO() ) ){
			for( Entry<ReferralLocKey, ReferralVO> mapRefEntry : policyVO.getMapReferralVO().entrySet() ){
				if(Integer.valueOf( sectionId ).equals( mapRefEntry.getKey().getSectionId() ) ){
					ReferralVO locreferralVO = mapRefEntry.getValue();
					locreferralVO.setPolLinkingId( policyVO.getPolLinkingId() );
					locreferralVO.setRiskGroupId( locationDetails.getRiskGroupId() );
					
					/*Start of ticket 137704 */
					locreferralVO.setTprUserId(userId);
					locreferralVO.setTprUserRole(role);
					/*End of ticket 137704 */

					if( !Utils.isEmpty( locreferralVO ) 	){
						TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean( "tempPasReferralDAO" );
						insertTempPasReferalDao.insertReferal( locreferralVO );
					}
				}
			}
		}
		DAOUtils.handleCommonReferrals( policyVO.getPolLinkingId(), sectionId, Long.valueOf( locationDetails.getRiskGroupId() ), getHibernateTemplate() );
		
	}//END SONARFIX block
		return null;
	}

	
	/**
	 * 
	 * @param baseVO
	 * @return BaseVO
	 */
	@Override
	public BaseVO isEndorsementRecordExist(BaseVO baseVO){
		
		DataHolderVO<Boolean> dataHolderVO=new DataHolderVO<Boolean>();
		if(!Utils.isEmpty(baseVO)){
			Boolean isEndorsementRecordExist=false;
			PolicyVO input=(PolicyVO)baseVO;
			if(!Utils.isEmpty(input) && !Utils.isEmpty(input.getNewEndtId())) {
				List<TTrnPolicyQuo> polQuoRecordList= getHibernateTemplate()
						.find( "from TTrnPolicyQuo polQuo where polQuo.id.polEndtId = ? and  polQuo.polLinkingId= ?", input.getNewEndtId(),input.getPolLinkingId() );
				if( !Utils.isEmpty( polQuoRecordList ) ) isEndorsementRecordExist=true;
			}
			dataHolderVO.setData(isEndorsementRecordExist);
		}
		return dataHolderVO;
	}
	
	/**
	 * This method is used to fetch the Notice board items and their details configured in the database.
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO getNoticeBoardItems( BaseVO baseVO ){
		
		NoticeBoardVO noticeBoardVO = (NoticeBoardVO)baseVO;
		HashMap<String,List<String>> noticeBoardItems = new HashMap<String, List<String>>();
		List<TMasBroadcastMsg> tMasBroadcastMsgs ;
		
		UserProfile userProfile = (UserProfile)noticeBoardVO.getLoggedInUser();
				
		String subQuery = getLoggedUserQueryStr(userProfile);
		
		/*
			Filtering Items based on Location. 
			To Display only the items configured for the deployed location 
		*/
		String locQuery = "";
		if( !Utils.isEmpty( Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION ) ) ){
			locQuery = " and tMasBroadcastMsg.brdLocCode = " + Utils.getSingleValueAppConfig( SvcConstants.DEPLOYED_LOCATION );
		}
		
		tMasBroadcastMsgs = getHibernateTemplate().find( getQueryForNoticeBoardItems()+locQuery+subQuery);
		
		Set<String> itemSet = new HashSet<String>();
		for(TMasBroadcastMsg  tMasBroadcastMsg: tMasBroadcastMsgs ){
			
			itemSet.add( SvcUtils.getLookUpDescription("PAS_BRDMSG","ALL", "ALL", Integer.valueOf( tMasBroadcastMsg.getBrdMsgCategory()) ));
		}
		
		for(String str : itemSet){
			
			List<String> items = new ArrayList<String>();
			
			for( TMasBroadcastMsg  tMasBroadcastMsg: tMasBroadcastMsgs ){
				
				if( str.equals(SvcUtils.getLookUpDescription("PAS_BRDMSG","ALL", "ALL", Integer.valueOf( tMasBroadcastMsg.getBrdMsgCategory()) ))){
					items.add(tMasBroadcastMsg.getBrdMsgText());
				}
			}
			noticeBoardItems.put(str, items);
		}
		
		noticeBoardVO.setNoticeBoardItems(noticeBoardItems);
		return noticeBoardVO;
	}

	private String getQueryForNoticeBoardItems() {
		return "from TMasBroadcastMsg tMasBroadcastMsg where TRUNC(SYSDATE) between TRUNC(tMasBroadcastMsg.brdMsgEffDate) AND TRUNC(tMasBroadcastMsg.brdMsgExpDate)";
		
		//tMasBroadcastMsg.brdMsgExpDate = ? and trunc(tMasBroadcastMsg.brdMsgEffDate) <= trunc(sysdate) ";
	}
	

	private String getLoggedUserQueryStr(UserProfile userProfile) {
		
		String query = "";
		if(("broker").equalsIgnoreCase( userProfile.getRsaUser().getProfile() )){
			
			query += getBrokerQuery(userProfile);
			
		}else if(("employee").equalsIgnoreCase( userProfile.getRsaUser().getProfile() )){
			
			query += getRsaUserQuery(userProfile);
			
		}else if(("agent").equalsIgnoreCase( userProfile.getRsaUser().getProfile()) ){
			
			query += getAgentQuery(userProfile);
			
		}
		
		return query + " ORDER BY brdMsgId";
	}

	private String getAgentQuery(UserProfile userProfile) {
		return " and tMasBroadcastMsg.brdMsgAgntId in ("+Integer.valueOf(SvcConstants.zeroVal)+","+userProfile.getRsaUser().getAgentId()+") and upper(tMasBroadcastMsg.brdRsaUsersFlag) = 'N'";
	}

	private String getRsaUserQuery(UserProfile userProfile) {
		return " and tMasBroadcastMsg.brdMsgUsrId in ("+Integer.valueOf(SvcConstants.zeroVal)+","+userProfile.getRsaUser().getUserId()+") and upper(tMasBroadcastMsg.brdRsaUsersFlag) = 'Y'";
	}

	private String getBrokerQuery(UserProfile userProfile) {
		return " and tMasBroadcastMsg.brdMsgBrkId in ("+Integer.valueOf(SvcConstants.zeroVal)+","+userProfile.getRsaUser().getBrokerId()+") and upper(tMasBroadcastMsg.brdRsaUsersFlag) = 'N'";
	}

	@Override
	public BaseVO upadteTradeLicNo(BaseVO baseVO) {
		
		PolicyVO policyVO = (PolicyVO)baseVO;
		
		DAOUtils.saveTradeLicNo( policyVO, getHibernateTemplate() );
		
		return policyVO;
	}

	
	/* 
	 * This method populates the commonVO after transaction search.
	 * Added as part of Phase 3.
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public BaseVO populateCommonDetails( BaseVO baseVO ){
		LOGGER.info("Entered CommonOpDAO.populateCommonDetails method.");
		return DAOUtils.populateCommonDetails( baseVO, getHibernateTemplate() );
	}

	@Override
	public BaseVO fetchPolicyRecord( BaseVO baseVO ){
		CommonVO commonVO = (CommonVO) baseVO;
		TTrnPolicyQuo trnPolicyQuo = DAOUtils.getLatestPolicyRecord( commonVO.getEndtId(), commonVO.getPolicyId(), getHibernateTemplate() );
		return BeanMapper.map( trnPolicyQuo, PolicyDataVO.class );
	}

	/* 
	 * fetches the special cover recs for premium.
	 */
	@Override
	public TTrnPremiumVOHolder getPremiumSpecialCoverRecs( CommonVO commonVO ){
		
		TTrnPremiumVOHolder premiumVOHolder = new TTrnPremiumVOHolder();
		/*Get latest policy record and map it to premium.*/
		TTrnPolicyQuo policyRec = DAOUtils.getLatestPolicyRecord( commonVO.getEndtId(), commonVO.getPolicyId(), getHibernateTemplate() );
		premiumVOHolder = BeanMapper.map( policyRec, premiumVOHolder );

		if( !Utils.isEmpty( premiumVOHolder ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );

			premiumVOHolder.setPrmBasicRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
			premiumVOHolder.setPrmBasicRskId( converter.getAFromB( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
			premiumVOHolder.setPrmCtCode( SvcConstants.SC_PRM_CT_CODE );
			premiumVOHolder.setPrmCstCode( SvcConstants.SC_PRM_CT_CODE );
			premiumVOHolder.setPrmRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
			premiumVOHolder.setPrmRskId( converter.getAFromB( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
			premiumVOHolder.setPrmPtCode( policyRec.getPolPolicyType() );
			premiumVOHolder.setPrmRtCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
			premiumVOHolder.setPrmRcCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
			premiumVOHolder.setPrmRscCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_SPECIAL_CODE ) ) );
			premiumVOHolder.setPrmSumInsured( BigDecimal.valueOf( SvcConstants.SC_PRM_SUM_INSURED ) );
			premiumVOHolder.setPrmPremium( BigDecimal.valueOf( SvcConstants.SC_PRM_SUM_INSURED ) );
			premiumVOHolder.setPrmPremiumActual( BigDecimal.valueOf( SvcConstants.SC_PRM_SUM_INSURED ) );
			premiumVOHolder.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
			premiumVOHolder.setPrmSitypeCode( SvcConstants.SC_PRM_SI_TYPE );
			premiumVOHolder.setPrmSumInsuredCurr( SvcConstants.SC_PRM_SI_CURR );
			premiumVOHolder.setPrmRateType( SvcConstants.SC_PRM_RATE_TYPE );
			premiumVOHolder.setPrmOldPremium( BigDecimal.valueOf( SvcConstants.SC_PRM_OLD_PRM ) );
			premiumVOHolder.setPrmRiLocCode( SvcConstants.APP_PRM_RI_LOC_CODE );
			
			if( premiumVOHolder.getPrmPtCode() == Short.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE) || premiumVOHolder.getPrmPtCode() == Short.valueOf( SvcConstants.LONG_TRAVEL_POL_TYPE) ){
				premiumVOHolder.setPrmRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.GPR_RI_RSK_CODE_TRAVEL ) ) );
			}else if( premiumVOHolder.getPrmPtCode() == Short.valueOf( SvcConstants.HOME_POL_TYPE) ){
				premiumVOHolder.setPrmRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_LESSER" ) ) );
			}
		}

		return premiumVOHolder;
	}
	/* 
	 * This method calls the Issue Quote SP to activate the code
	 * Added as part of Phase 3.
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public void activateQuote( BaseVO baseVO ){
		  PolicyDataVO policyDataVo = (PolicyDataVO) baseVO;
		 DAOUtils.callUpdateStatusProcedureForHomeTravel(policyDataVo );
	}

	/**
	 * @param ht
	 * @param prmRecord
	 * @return Object[]
	 * fetches the codes from the view V_Mas_Gacc_Policy_Rating to be set into TTrnPremiumQuo record.
	 */
	@Override
	public Object[] getSpecialCodes( TTrnPremiumVOHolder prmRecord ){
		
		HibernateTemplate ht = getHibernateTemplate();
		Query query = null;
		
		if(prmRecord.getPrmClCode() == Short.valueOf( Utils.getSingleValueAppConfig( "HOME_CLASS_CODE" ) ) ){
			
			/*Changes for Ticket 76367 - SI_TYPE issue
			if( prmRecord.getPrmCovCode() == SvcConstants.oneVal ){
				query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_SPECIAL_CODES_HOME );
			}else{
				query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_SPECIAL_CODES_HOME_ADDTL );
			}
			query.setParameter( "policyType", prmRecord.getPrmPtCode() );
			query.setParameter( "riskTypeCode", prmRecord.getPrmRtCode() );*/
			
			query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_SPECIAL_CODES_HOME );
			
		}
		else if(prmRecord.getPrmClCode() == Short.valueOf( Utils.getSingleValueAppConfig( "TRAVEL_CLASS_CODE" ) ) ){
			
			/*Changes for Ticket 76367 - SI_TYPE issue
			if( prmRecord.getPrmCovCode() == SvcConstants.oneVal ){
				query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_SPECIAL_CODES_TRAVEL );
			}else{
				query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_SPECIAL_CODES_TRAVEL_ADDTL );
			}
			query.setParameter( "policyType", prmRecord.getPrmPtCode() );
			query.setParameter( "riskTypeCode", prmRecord.getPrmRtCode() );*/
			query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_SPECIAL_CODES_TRAVEL );
		}
		else if(prmRecord.getPrmClCode() == Short.valueOf( Utils.getSingleValueAppConfig( "WC_CLASS_CODE" ) ) ){
			query = ht.getSessionFactory().getCurrentSession().createSQLQuery( QueryConstants.FETCH_SPECIAL_CODES_WCTPL );
		}
		if(!Utils.isEmpty( query )){
			query.setParameter( "policyType", prmRecord.getPrmPtCode() );
			query.setParameter( "riskTypeCode", prmRecord.getPrmRtCode() );
			
			query.setParameter( "classCode", prmRecord.getPrmClCode() );
			query.setParameter( "riskCode", prmRecord.getPrmRskCode() );
			query.setParameter( "coverCode", prmRecord.getPrmCovCode());// SvcConstants.SPL_COV_CODE );

			List<Object[]> result = query.list();
			if( !Utils.isEmpty( result ) ){
				return result.get( 0 );
			}
		}
		
		return null;
	}
	@Override
	public BaseVO getNextEndorsementId( BaseVO baseVO ){
		//Call NextSequenceValue to get the next endorsement id
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		Long newEndtId = NextSequenceValue.getNextSequence( policyDataVO.getCommonVO().getIsQuote() ? SvcConstants.SEQ_QUO_ENDORSEMENT_ID : SvcConstants.SEQ_ENDORSEMENT_ID,
				policyDataVO.getPolicyType(), policyDataVO.getPolicyClassCode(), null, null, getHibernateTemplate() );
		policyDataVO.getCommonVO().setEndtId( newEndtId );
		policyDataVO.getCommonVO().setEndtNo( policyDataVO.getCommonVO().getEndtNo()+1 );
		policyDataVO.setEndtNo(policyDataVO.getCommonVO().getEndtNo());
		policyDataVO.setEndtId( newEndtId );
		return policyDataVO;
	}
	
	
	@Override
	public void callTariffChangeProcedure( BaseVO baseVO ){
		PASStoredProcedure sp = null;
		PolicyDataVO polData = (PolicyDataVO) baseVO;
		
		Date vsd = null;
		/* Set new VSD if status is equal to 1 */
		if(polData.getCommonVO().getStatus() != SvcConstants.POL_STATUS_ACTIVE ){
			vsd = polData.getCommonVO().getVsd();
		}else{
			vsd = DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), polData.getCommonVO().getAppFlow() );
			
		}
		
		if(Utils.isEmpty( polData.getCommonVO() ) || Utils.isEmpty( polData.getCommonVO().getLob() )){
			throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
		}

		sp = getTarChangeProc( polData );

		try{
			Map resultsVED = sp.call( polData.getCommonVO().getPolicyId(), polData.getCommonVO().getEndtId(), vsd,
					Integer.parseInt( Utils.getSingleValueAppConfig( polData.getCommonVO().getLob().toString() + "_CLASS_CODE" ) ),
					Integer.parseInt( Utils.getSingleValueAppConfig( polData.getCommonVO().getLob().toString() + "_SEC_ID" ) ) );

			if( !Utils.isEmpty( resultsVED ) && resultsVED.containsKey( com.Constant.CONST_PO_ERR_TEXT ) && !Utils.isEmpty( resultsVED.get( com.Constant.CONST_PO_ERR_TEXT ) ) ){
				LOGGER.debug( "Error occured while executing stored procedure" );
				throw new BusinessException( "cmn.storedproc.error", null, resultsVED.get( "PO_ERR_TEXT" ).toString() );
			}
			polData.getCommonVO().setVsd( vsd );
			polData.setValidityStartDate( vsd );
		}
		catch( DataAccessException e ){

			throw new BusinessException( "cmn.storedproc.error", e, "An exception occured while executing stored proc." );
		}
	}

	private PASStoredProcedure getTarChangeProc( PolicyDataVO polData ){
		PASStoredProcedure sp;

		String postFix = ( Flow.AMEND_POL.equals( polData.getCommonVO().getAppFlow() ) || ( Flow.RESOLVE_REFERAL.equals( polData.getCommonVO().getAppFlow() ) && !polData.getCommonVO().getIsQuote() ) ) ? "_POL" : "";
		String procName = "";
		if(polData.getCommonVO().getLob().equals( LOB.HOME )){
			procName = "procTariffChangeHome";
		}else if(polData.getCommonVO().getLob().equals( LOB.TRAVEL )){
			procName = "procTariffChangeTravel";
		}else{
			procName = "procTariffChangeMonoline";
		}
		sp = (PASStoredProcedure) Utils.getBean( procName + postFix );
		return sp;
	}
	
	@Override
	public BaseVO isMortgageeExists( BaseVO baseVO ){

		CommonVO commonVO = (CommonVO) baseVO;

		String BUILDING_QUERY = "SELECT bld_mortgage_name FROM t_trn_building " + "WHERE bld_policy_id =(select distinct pol_policy_id from T_TRn_POLICY "
				+ "where pol_policy_no = :policyNo1 and pol_policy_year=:polYear1)  and bld_endt_id = (select max(bld_endt_id) from t_trn_building "
				+ "where bld_policy_id = (select distinct pol_policy_id from T_TRn_POLICY "
				+ "where pol_policy_no = :policyNo2 and pol_policy_year=:polYear2) and bld_endt_id <= :endtId)";
		//JLT_SAT_Bank letter issue fix
		if(!Utils.isEmpty( commonVO ) && commonVO.getPolEffectiveDate()==null)
		{
		 BUILDING_QUERY = "SELECT bld_mortgage_name FROM t_trn_building " + "WHERE bld_policy_id =(select distinct pol_policy_id from T_TRn_POLICY "
				+ "where pol_policy_no = :policyNo1 and pol_quotation_no=:polquotation1)  and bld_endt_id = (select max(bld_endt_id) from t_trn_building "
				+ "where bld_policy_id = (select distinct pol_policy_id from T_TRn_POLICY "
				+ "where pol_policy_no = :policyNo2 and pol_quotation_no=:polquotation2) and bld_endt_id <= :endtId)";
		}
		//JLT_SAT_Bank letter issue fix
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery( BUILDING_QUERY );

		if( !Utils.isEmpty( commonVO ) ){
			query.setParameter( "policyNo1", commonVO.getPolicyNo() );
			query.setParameter( "policyNo2", commonVO.getPolicyNo() );
			//JLT_SAT_Bank letter issue fix
			if(commonVO.getPolEffectiveDate()==null){
			query.setParameter("polquotation1", commonVO.getQuoteNo());
			query.setParameter("polquotation2", commonVO.getQuoteNo());
			}else{
			query.setParameter( "polYear1",SvcUtils.getYearFromDate( commonVO.getPolEffectiveDate() ));
			query.setParameter( "polYear2", SvcUtils.getYearFromDate( commonVO.getPolEffectiveDate() ));
			}
			//JLT_SAT_Bank letter issue fix
			query.setParameter( "endtId", commonVO.getIsQuote() ? 0L : commonVO.getEndtId() );
		}

		Object result = ( query.list() ).get( 0 );
		DataHolderVO<Boolean> data = new DataHolderVO<Boolean>();

		if( !Utils.isEmpty( result ) ){
			data.setData( Boolean.TRUE );
		}
		else{
			data.setData( Boolean.FALSE );
		}

		return data;
	}
	
	@Override
	public BaseVO getClauseForCurrentEndtId( BaseVO baseVO,BaseVO baseVO2){
		
		CommonVO commonVO = (CommonVO) baseVO;
		PolicyDataVO policyDataVO = (PolicyDataVO)baseVO2;
		List<TTrnPolicyConditionQuo> ttrnConditionsQou = null;
		List<TTrnPolicyCondition> ttrnConditions = null;
		DataHolderVO<List<TTrnPolicyConditionQuo>> dataForQuote = new DataHolderVO<List<TTrnPolicyConditionQuo>>();
		DataHolderVO<List<TTrnPolicyCondition>> dataForPolicy = new DataHolderVO<List<TTrnPolicyCondition>>();
		if( !Utils.isEmpty( commonVO.getPolicyId() ) ){
			if( commonVO.getIsQuote() ){
				ttrnConditionsQou = getHibernateTemplate().find(
						"from TTrnPolicyConditionQuo quoCon where quoCon.id.tpcPolicyId=? and (quoCon.id.tpcEndtId = ? or "
								+ "(quoCon.id.tpcEndtId < ? and trunc(quoCon.tpcValidityEndDate) = ?)) and quoCon.id.tpcCode in "
								+ "(select mas.id.pcCode from TMasPolicyCondition mas where mas.id.pcSchCode = ?" + " and mas.id.pcClCode = ? and mas.pcPhrCode = ?) and quoCon.tpcStatus = 4", commonVO.getPolicyId(),
						commonVO.getEndtId(), commonVO.getEndtId(), SvcConstants.EXP_DATE, policyDataVO.getScheme().getSchemeCode().intValue(), Short.valueOf( Utils.getSingleValueAppConfig( "SEC_14" ) ), 73);
				dataForQuote.setData(ttrnConditionsQou);
				
				return dataForQuote;
			}
			else{
				ttrnConditions = getHibernateTemplate().find(
						"from TTrnPolicyCondition polCon where polCon.id.tpcPolicyId=? and (polCon.id.tpcEndtId = ? or "
								+ "(polCon.id.tpcEndtId < ? and trunc(polCon.tpcValidityEndDate) = ?)) and polCon.id.tpcCode in "
								+ "(select mas.id.pcCode from TMasPolicyCondition mas where mas.id.pcSchCode = ?" + " and mas.id.pcClCode = ? and mas.pcPhrCode = ?) and polCon.tpcStatus = 4", commonVO.getPolicyId(),
								commonVO.getEndtId(), commonVO.getEndtId(), SvcConstants.EXP_DATE,  policyDataVO.getScheme().getSchemeCode().intValue(), Short.valueOf( Utils.getSingleValueAppConfig( "SEC_14" ) ), 73 );
				dataForPolicy.setData(ttrnConditions);
				
				return dataForPolicy;
			}
		}
		
		return null;
	}

	/*
	 * Method to check if the promo code entered in the UI is valid. 
	 */
	@Override
	public BaseVO validatePromoCode( BaseVO baseVO ){
		
		boolean flag = false;
		String promoCode = null;
		Date effDate = null;
		PolicyDataVO policyDataVO = (PolicyDataVO)baseVO;
		List<TMasPromotionalCode> promoCodeList ;
		
		if( !Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getPromoCode() ) 
				&& !Utils.isEmpty( policyDataVO.getScheme().getEffDate() )	&& !Utils.isEmpty( policyDataVO.getScheme().getExpiryDate() ) && !Utils.isEmpty( policyDataVO.getPolicyType() ) ){
			
			promoCode = policyDataVO.getGeneralInfo().getSourceOfBus().getPromoCode();
			//effDate = policyDataVO.getScheme().getEffDate(); 
			effDate = getSysDate();
			
			BigDecimal polType = BigDecimal.valueOf( Double.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ));
			
			if( policyDataVO.getCommonVO().getLob().equals( LOB.HOME ) ){
				polType = BigDecimal.valueOf( Double.valueOf( SvcConstants.HOME_POL_TYPE ) );
				//effDate = getSysDate();
			}
			
			promoCodeList = getHibernateTemplate().find( "from TMasPromotionalCode tmpc where tmpc.proCode = ? and ? between tmpc.proStartDate and tmpc.proEndDate and tmpc.proPtCode = ? ",
					promoCode, effDate, polType ); // policy type set as 6 because in database it is stored as 6 by default
			
			if( !Utils.isEmpty( promoCodeList ) ){
				List<TMasPromoDiscCover> promoDiscCoverList = getHibernateTemplate().find( "from TMasPromoDiscCover tmpdc where tmpdc.id.pdcProCode = ?", promoCode );
				if( !Utils.isEmpty( promoDiscCoverList ) ){
					flag = true;
				}
			}
		}
		
		if( !flag ){
			throw new BusinessException( "error", null, "Promotional Code is invalid." );
		}
		
		return baseVO;
	}

	@Override
	public BaseVO getPrevEndtIdForPendingPolicy(BaseVO baseVO) {
		
		CommonVO commonVO = (CommonVO) baseVO;
		DataHolderVO<Long> holderVO = new DataHolderVO<Long>();
		String ENDT_QUERY = "select max(pol_endt_id) from T_TRN_POLICY where pol_policy_id = :policyId and pol_endt_id < :polEndtId";
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery( ENDT_QUERY );
		
		if( !Utils.isEmpty( commonVO ) ){
			query.setParameter( "policyId", commonVO.getPolicyId());
			query.setParameter( "polEndtId",commonVO.getEndtId());
		}
		
		Object result = ( query.list() ).get( 0 );
		
		if( !Utils.isEmpty( result ) ){
			holderVO.setData(Long.valueOf(result.toString()));
		}
		
		return holderVO;
	}

	@Override
	public BaseVO getPolicyIdForPolicy( BaseVO baseVO ){

		PolicyVO policyVO = null;
		CommonVO commonVO = null;
		Long endtId = 0L;
		String query = "";
		DataHolderVO<Long> policyIdHolder = new DataHolderVO<Long>();
		if( baseVO instanceof PolicyVO ){
			policyVO = (PolicyVO) baseVO;
			endtId = policyVO.getIsQuote() ? 0L : policyVO.getEndtId();
			/*query = "select prm_policy_id from t_trn_premium " + "where Prm_Policy_Id In ( Select distinct Pol_Policy_Id From T_Trn_Policy where pol_policy_no = "
			+ policyVO.getPolicyNo() + "and pol_quotation_no=" + policyVO.getQuoteNo() + ") and prm_premium <> 0 and prm_cov_code not in (20,51,101) and prm_endt_id = " + endtId; */
			query = " SELECT  POL_POLICY_ID " +
					"      FROM v_trn_policy_diff_premium_pas WHERE pol_linking_id in (" +
					"      Select distinct pol_linking_id From T_Trn_Policy " +
					"      where pol_policy_no = "+ policyVO.getPolicyNo() + " and pol_issue_hour = 3 and pol_quotation_no=" + policyVO.getQuoteNo() + ")" +
				    "      AND pol_endt_id = "+endtId+" and pol_issue_hour  = 3 and (POL_PREMIUM <>0 OR POL_VAT_AMOUNT <> 0) ";
		}
		if( baseVO instanceof CommonVO ){
			commonVO = (CommonVO) baseVO;
			endtId = commonVO.getIsQuote() ? 0L : commonVO.getEndtId();
			/*query = "select prm_policy_id from t_trn_premium " + "where Prm_Policy_Id In ( Select distinct Pol_Policy_Id From T_Trn_Policy where pol_policy_no = "
					+ commonVO.getPolicyNo() + "and pol_quotation_no=" + commonVO.getQuoteNo() + ") and prm_premium <> 0 and prm_cov_code not in (20,51,101) and prm_endt_id = " + endtId;*/
			query = " SELECT  POL_POLICY_ID " +
			"      FROM v_trn_policy_diff_premium_pas WHERE pol_policy_no in (" +
			"      Select distinct pol_policy_no From T_Trn_Policy " +
			"      where pol_policy_no = "+ commonVO.getPolicyNo() + " and pol_issue_hour = 3 and pol_quotation_no=" + commonVO.getQuoteNo() + ")" +
		    "      AND pol_endt_id = "+endtId+" and ( POL_PREMIUM <>0 OR POL_VAT_AMOUNT <> 0 ) and pol_issue_hour  = 3 and pol_quotation_no=" +commonVO.getQuoteNo();
			
			/*query = " SELECT  distinct POL_POLICY_ID " +
			"      FROM t_trn_policy WHERE pol_policy_no =" +commonVO.getPolicyNo() + " and pol_quotation_no=" + commonVO.getQuoteNo() +
		    "      AND pol_endt_id = "+endtId+" and POL_PREMIUM <>0 ";
			System.out.println(query);*/
		}

		//String query = "select pol_policy_id from t_trn_policy WHERE pol_policy_no = "+policyVO.getPolicyNo()+" and pol_quotation_no="+policyVO.getQuoteNo();

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery( query );
		List<Object> result = sqlQuery.list();
		
		if( !Utils.isEmpty( result ) ){
			policyIdHolder.setData( Long.valueOf( result.get( 0 ).toString() ) );
			return policyIdHolder;
		}
		return null;
	}

	/*
	 * This method is used to set precision for premium, for Home and Travel 
	 * @see com.rsaame.pas.dao.cmn.ICommonOpDAO#getPolicyTypeCurrencyScaleMap()
	 */
	@Override
	public BaseVO getPolicyTypeCurrencyScaleMap(){
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery( QueryConstants.GET_SCALE_FOR_POLICY_TYPE );
		List<Object> result = sqlQuery.list();
		Map<Short, Integer> policyTypeScaleMap = new HashMap<Short, Integer>();
		if( !Utils.isEmpty( result ) ){
			for( int i = 0; i < result.size(); i++ ){
				Object[] obj = (Object[]) result.get( i );
				if(!Utils.isEmpty( obj[ 1 ])){
					policyTypeScaleMap.put( ( (BigDecimal) obj[ 0 ] ).shortValue(), ( (BigDecimal) obj[ 1 ] ).intValue() );
				}
			}
		}
		DataHolderVO<Map<Short, Integer>> currencyMapHolder = new DataHolderVO<Map<Short, Integer>>();
		currencyMapHolder.setData( policyTypeScaleMap );
		return currencyMapHolder;}
	
	@Override
	public BaseVO getLegacyPolicies(BaseVO baseVo){
		DataHolderVO<Long> policyIdHolder = (DataHolderVO<Long>)baseVo;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery( QueryConstants.GET_LEGACY_POLICIES );
		sqlQuery.setLong( 0, policyIdHolder.getData() );
		List<Object> result = sqlQuery.list();
		if (Utils.isEmpty( result )){
			return null;
		}
		DataHolderVO<List<Object>> dataHolder = new DataHolderVO<List<Object>>();
		dataHolder.setData( result );
		return dataHolder;
		
	}

	public BaseVO getRenQuoteForPolicy(BaseVO baseVo)
	{
		CommonVO commonVO = null;
		Long policyNo = 0L;
		String query = "";
		String errorMsg= null;
		//String date = "18-jan-1974";
		
	if( baseVo instanceof CommonVO )
	{
		commonVO = (CommonVO) baseVo;
		policyNo = commonVO.getPolicyNo();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		String sysDate = sdf.format( getSysDate() );
		
		if(!Utils.isEmpty(commonVO.getConcatPolicyNo()))
		{
			query = "SELECT distinct POL_QUOTATION_NO FROM T_TRN_POLICY_QUO WHERE POL_REF_POLICY_NO = " + policyNo + 
					com.Constant.CONST_AND_POL_EXPIRY_DATE_END + sysDate + "' AND POL_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND POL_STATUS in (1,6,23) " +
					" AND POL_DISTRIBUTION_CHNL in (8,9,10) AND POL_ISSUE_HOUR = 3 AND POL_CLASS_CODE = 2 AND POL_POLICY_TYPE = 7 " +
					"AND pol_policy_id in (select csh_policy_id from t_mas_cash_customer_quo where CSH_E_EMAIL_ID = '" + commonVO.getConcatPolicyNo() + "')";
					
			/*query = " SELECT  distinct POL_QUOTATION_NO " + " FROM T_TRN_POLICY_QUO WHERE pol_REF_policy_no =" +policyNo +
			" AND POL_POLICY_YEAR = "+Calendar.getInstance().get(Calendar.YEAR) 
			+" and pol_status in (1,6,23)  and (pol_cover_note_hour in(1201,1205,1210)) and pol_issue_hour = 3 and pol_policy_id in (select csh_policy_id from t_mas_cash_customer_quo where CSH_E_EMAIL_ID = '"+commonVO.getConcatPolicyNo() +"')";
			*/
			errorMsg = REN_QUOTE_RETRIEVEL_MSG1;
		}
		else if(!Utils.isEmpty(commonVO.getCreatedBy()))
		{
			query = "SELECT distinct POL_QUOTATION_NO FROM T_TRN_POLICY_QUO WHERE pol_ref_policy_no = " +policyNo 
					+ com.Constant.CONST_AND_POL_EXPIRY_DATE_END+ sysDate + "' AND POL_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND pol_status in (1,6,23) "
					+ "AND POL_DISTRIBUTION_CHNL in (8,9,10) AND pol_issue_hour = 3 AND POL_CLASS_CODE = 5 AND POL_POLICY_TYPE in (6,31)"
					+ " AND pol_policy_id in (select gpr_policy_id from t_trn_gacc_person_quo where GPR_RELATION = 1 and trunc(gpr_date_of_birth) = '"
					+ commonVO.getCreatedBy() +"')";
			
			/*query = " SELECT  distinct POL_QUOTATION_NO " + " FROM T_TRN_POLICY_QUO WHERE pol_REF_policy_no =" +policyNo +
			" AND POL_POLICY_YEAR = "+Calendar.getInstance().get(Calendar.YEAR) 
			+" and pol_status in (1,6,23) and (pol_cover_note_hour in (1208,1209)) and pol_issue_hour = 3 and pol_policy_id in (select gpr_policy_id from t_trn_gacc_person_quo where GPR_RELATION = 1 and trunc(gpr_date_of_birth) = '"+commonVO.getCreatedBy() +"')";
			*/
			errorMsg = REN_QUOTE_RETRIEVEL_MSG3;
		}
		
	}
	
	Session session = getHibernateTemplate().getSessionFactory().openSession();
	Query sqlQuery = session.createSQLQuery( query );
	List<Object> result = sqlQuery.list();
	DataHolderVO policyIdHolder = new DataHolderVO();
	if( Utils.isEmpty( result ))
	{
		policyIdHolder.setData("There is no renewal Quote for this Policy");
		//throw new BusinessException( com.Constant.CONST_CMN_ERROR, null, errorMsg +" :"+policyNo );
		throw new BusinessException( "cmn.error", null, errorMsg );
	}
	
	else if( !Utils.isEmpty( result ) && result.size() > 1)
	{
		policyIdHolder.setData( Long.valueOf( result.get( 0 ).toString() ) );
		throw new BusinessException( "cmn.error", null, REN_QUOTE_RETRIEVEL_MSG2+" :"+policyNo );
	}
	else if( !Utils.isEmpty( result ) && result.size() == 1)
	{
		policyIdHolder.setData( Long.valueOf( result.get( 0 ).toString() ) );
		return policyIdHolder;
	}
	session.close();
	return null;
	}
@Override
	public BaseVO getforgotPassword( BaseVO baseVO ){
		
		baseVO = checkUserExists(baseVO);
		checkAndInsertIntoUserDetails(baseVO);
		generateRandomPassword(baseVO);
		return baseVO;
	}

	private void generateRandomPassword(BaseVO baseVO) {
		RandomStringUtils r = new RandomStringUtils();
		String password = r.randomAlphanumeric(9);
		System.out.println("Password : "+password);
		ForgotPwdDetailsVO detailsVO = (ForgotPwdDetailsVO)baseVO;
		detailsVO.setRandomPassword(password);
		detailsVO.setResetOn( getSysDate() );
		updatePassword(detailsVO);
	}

	private void checkAndInsertIntoUserDetails(BaseVO baseVO) {
		ForgotPwdDetailsVO detailsVO = (ForgotPwdDetailsVO)baseVO;
		String query = "select USER_ID,LOGIN_ID,DOB,LAST_NAME  from T_MAS_USER_DETAIL  where USER_ID = '"+detailsVO.getUserId().toString()+"'";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query selectSqlQuery = session.createSQLQuery( query );
		List<Object> result = selectSqlQuery.list();
		
		
		if( Utils.isEmpty( result ))
		{
			//TMasUserDetail userDetails = getTMasUSerDetailsPojo(detailsVO);
			//saveOrUpdate(userDetails);
			SQLQuery sqlQuery = null;
			
			sqlQuery = session.createSQLQuery("insert into T_MAS_USER_DETAIL(USER_ID,LOGIN_ID,DOB,LAST_NAME)" +
			" values(:userId,:loginId,:dob,:lastName)");
	
			sqlQuery.setInteger("userId", detailsVO.getUserId());
			sqlQuery.setString("loginId", detailsVO.getUserLoginName());
			sqlQuery.setDate("dob", detailsVO.getDateOfBirth());
			sqlQuery.setString("lastName", detailsVO.getLastName());
			
			sqlQuery.executeUpdate();
			//throw new BusinessException( com.Constant.CONST_CMN_ERROR, null, "User does not exists!" );
		}else if(!Utils.isEmpty( result ) && result.size() == 1){
			Object[] obj = (Object[]) result.get( 0 );
			validateUserDetails(obj,detailsVO);
		}
		
	}
	
	private void validateUserDetails(Object[] obj, ForgotPwdDetailsVO detailsVO) {
		
		 //String userId = obj[ 0 ].toString();
		 String loginId =  obj[ 1 ].toString();
		 Date dob =  (Date)obj[ 2 ];
		 String lastName = obj[ 3 ].toString();
		 
		 System.out.println("DB dob_1"+dob.getTime()+" UI dob_1"+detailsVO.getDateOfBirth().getTime());
		 
		 if(!loginId.equalsIgnoreCase(detailsVO.getUserLoginName())){
			 throw new BusinessException( "cmn.loginError", null, "Login Id does not match!" );
		 } else if(dob.compareTo(detailsVO.getDateOfBirth()) != 0){
			 System.out.println("DB dob_2"+dob.getTime()+" UI dob_2"+detailsVO.getDateOfBirth().getTime());
			 System.out.println("DB dob_3"+dob+" UI dob_3"+detailsVO.getDateOfBirth());
			 throw new BusinessException( "cmn.dobError", null, "DOB does not match!" );
		 } else if(!lastName.equalsIgnoreCase(detailsVO.getLastName())){
			 throw new BusinessException( "cmn.lastNameError", null, "Last Name does not match!" );
		 }
		 
	}

	private TMasUserDetail getTMasUSerDetailsPojo(ForgotPwdDetailsVO detailsVO) {
		TMasUserDetail userDetails = new TMasUserDetail();
		
		userDetails.setUserId(Integer.valueOf(detailsVO.getUserId()));
		userDetails.setDob(detailsVO.getDateOfBirth());
		userDetails.setLastName(detailsVO.getLastName());
		userDetails.setLoginId(detailsVO.getUserLoginName());
		
		return userDetails;
	}

	
	/**
	 * This method will save or update based on object exist or not.
	 * @param entity
	 * @throws SystemException
	 */
	public void saveOrUpdate( Object entity ) throws SystemException{
		try{
			getHibernateTemplate().saveOrUpdate( entity );
		}
		catch( Exception e ){
			LOGGER.error( e, "Forgot paasword saveOrUpdate(): Error when trying to save/update entity of type [", entity.getClass().getName(), "]" );
			throw new SystemException( CommonErrorKeys.COULD_NOT_SAVE_RECORD, e, "BaseDAO.saveOrUpdate(): Error when trying to save/update entity of type [", entity.getClass().getName(), "]" );
		}
	}


	private BaseVO checkUserExists(BaseVO baseVO) {
		
		ForgotPwdDetailsVO detailsVO = (ForgotPwdDetailsVO)baseVO;
		String query = "select USER_ID, USER_EMAIL_ID,USER_E_NAME  from T_MAS_USER where login_id = '"+detailsVO.getUserLoginName()+"'";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query sqlQuery = session.createSQLQuery( query );
		List<Object> result = sqlQuery.list();
		
		if( Utils.isEmpty( result ))
		{
			throw new BusinessException( "cmn.userError", null, "User does not exists!" );
		}
		else if( !Utils.isEmpty( result ) && result.size() == 1)
		{
			Object[] obj = (Object[]) result.get( 0 );
			detailsVO.setUserId(  Integer.valueOf(obj[ 0 ].toString() ) );
			detailsVO.setEmailAddress(  obj[ 1 ].toString()  );
			detailsVO.setUserEName( obj[ 2 ].toString()  );
		}
		
		
		return detailsVO;
	}
	
	@Override
	public BaseVO updatePassword( BaseVO baseVO ){

		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		try{
			ForgotPwdDetailsVO pwdDetailsVO = (ForgotPwdDetailsVO) baseVO;

			if( !Utils.isEmpty( pwdDetailsVO.getUserId() ) && !Utils.isEmpty( pwdDetailsVO.getRandomPassword() ) ){

				List<TMasUser> userRecs = (List<TMasUser>) hibernateTemplate.find( "from TMasUser where userId = ?", pwdDetailsVO.getUserId() );

				if( userRecs.size() == 1 ){

					TMasUser userRec = userRecs.get( SvcConstants.zeroVal );
					userRec.setStatusId(Byte.parseByte("1"));
					userRec.setLoginAttempts(false);
					
					/*Encrypt the password and save it to DB.*/
					userRec.setPassword( EncryptionUtil.encrypt( pwdDetailsVO.getRandomPassword() ) );
					
					/* Password expiry date is saved in the column PASSWORD_MODIFIED_DT. It should be set as,
					 * 40 days from system date in case of "Change password" after login, ( it will be empty in ForgotPwdDetailsVO ) 
					 * system date in case of "Reset Password" from Login page. ( it will be updated as sysdate in generateRandomPassword() above )
					 * */
					
					if( Utils.isEmpty( pwdDetailsVO.getResetOn() ) ){
						/* Add 40 days as it will be empty only in case of change password.*/
						Calendar cal = Calendar.getInstance();
						cal.setTime( getSysDate() );
						cal.add( Calendar.DAY_OF_MONTH, 40 );
						pwdDetailsVO.setResetOn( cal.getTime() );
					}
					
					userRec.setPasswordModifiedDt( pwdDetailsVO.getResetOn() );
					hibernateTemplate.update( userRec );
				}
				else{
					throw new BusinessException( "", null, "Improper user entries." );
				}
			}
			else{
				throw new BusinessException( "", null, "Invalid values passed." );
			}
		}
		catch( Exception e ){
			throw new BusinessException( "", null, "An exception occurred while saving new password." );
		}
		return baseVO;
	}
	
	/**
	 * Method to fetch the user roles for a specific user id
	 * @param baseVO
	 * @return
	 * @since Mirror site for B2C
	 */
	@Override
	public BaseVO getUserRoles( BaseVO baseVO ){
		
		List<String> role = new ArrayList<String>();
		DataHolderVO<Object> output = new DataHolderVO<Object>();
		Object [] outData =  new Object[1];
		DataHolderVO<Object> data = (DataHolderVO<Object>) baseVO;
		Object[] input = (Object[]) data.getData();
		
		Query userRoleQuery = getHibernateTemplate().getSessionFactory()
				.openSession().createSQLQuery("SELECT ROLE_FK FROM T_TRN_USER_ROLE_MAP WHERE USER_ID_FK = :userId");
		userRoleQuery.setParameter("userId", input[0]);
		List<Object> result = userRoleQuery.list();
		
		if(!Utils.isEmpty(result)){
			List<String> userRoles = Arrays.asList( Utils.getMultiValueAppConfig("ALL_ROLES") );
			List<String> userProfiles = Arrays.asList( Utils.getMultiValueAppConfig("ALL_PROFILES") );

			for(int i=0; i< result.size(); i++){
				if(userRoles.contains(result.get(i).toString()) || userProfiles.contains(result.get(i).toString())){
					role.add(result.get(i).toString());
				}
			}
		}
		outData[0] = role;
		output.setData(outData);
		
		return output;
	}
	
	public BaseVO getUpdatedPoBox(BaseVO baseVO) {
			PolicyVO policyVO = (PolicyVO) baseVO;
			DataHolderVO<String> holderVO = new DataHolderVO<String>();
			String query="";
			// Underlying code is commented for Sonar Critical issue
			//if(policyVO.getIsQuote()){
				query = "select distinct(bld.bldZip) from TTrnBuildingQuo bld where bld.bldPolicyId in" +
					" ( select distinct(pol.id.polPolicyId) from TTrnPolicyQuo pol where pol.polLinkingId = ? ) and "
					+ "bld.bldEndtId = ?";
		//	}else{
				/*query = "select distinct(bld.bldZip) from TTrnBuildingQuo bld where bld.bldPolicyId in" +
						" ( select distinct(pol.id.polPolicyId) from TTrnPolicyQuo pol where pol.polLinkingId = ? ) and "
						+ "bld.bldEndtId = ?";*/
			//}

			List<String> bldZipCodeList = getHibernateTemplate().find( query, policyVO.getPolLinkingId(), policyVO.getNewEndtId() );

			if( !Utils.isEmpty( bldZipCodeList ) && bldZipCodeList.size() > 0 ){
				holderVO.setData(bldZipCodeList.get( 0 ));
			}
			else if( Utils.isEmpty( bldZipCodeList ) ){
				query = "select distinct(wbd.wbdZip) from TTrnWctplPremiseQuo wbd where wbd.wbdPolicyId in" +
						" ( select distinct(pol.id.polPolicyId) from TTrnPolicyQuo pol where pol.polLinkingId = ? ) and "
						+ "wbd.wbdEndtId = ?";
				List<String> wctplZipCodeList = getHibernateTemplate().find( query, policyVO.getPolLinkingId(), policyVO.getNewEndtId() );
				if( !Utils.isEmpty( wctplZipCodeList ) && wctplZipCodeList.size() > 0 ){
					holderVO.setData(wctplZipCodeList.get( 0 ));
				}
			}
			/*SQLQuery query = session.createSQLQuery(ENDT_QUERY);
			if (!Utils.isEmpty(policyVO)) {
				query.setParameter("policyNo", policyVO.getPolicyNo());
				query.setParameter("polEndtId", policyVO.getNewEndtId());
			}
			Object result = (query.list()).get(0);
			if (!Utils.isEmpty(result)) {
				holderVO.setData(result.toString());
			}*/
			return holderVO;
		}
	
	//VAT New screen
	
	@SuppressWarnings("unchecked")
	/**
	 * Method to update the VAT reference number for policy
	 * @param baseVO
	 * @return
	 * @since Mirror site for B2C
	 */
	@Override
	public BaseVO updateVATRegNo( BaseVO baseVO ){
		
		DataHolderVO<Object> output = new DataHolderVO<Object>();
		Object [] outData =  new Object[2];
		DataHolderVO<Object> data = (DataHolderVO<Object>) baseVO;
		Object[] input = (Object[]) data.getData();
		String lob = (String) input[3];
		
		List<Integer> polTypeLIst = new ArrayList<Integer>();
		
		
		if( lob.equals( LOB.TRAVEL.toString() )){
			polTypeLIst.add( Integer.parseInt( SvcConstants.SHORT_TRAVEL_POL_TYPE ));
			polTypeLIst.add( Integer.parseInt( SvcConstants.LONG_TRAVEL_POL_TYPE ));			
		}
		else{			
			if( lob.equals( LOB.SBS.toString()) ){
				polTypeLIst.add(Integer.parseInt( SvcConstants.SBS_POL_TYPE ));
			}else if( lob.equals( LOB.WC.toString()) ){
				polTypeLIst.add(SvcConstants.WC_POLICY_TYPE);
			}else if( lob.equals( LOB.HOME.toString()) ){
				polTypeLIst.add(Integer.parseInt(SvcConstants.HOME_POL_TYPE));
			}
		}		
		
		List<Integer> resultCount = DAOUtils.updateVATRegnoForPolicy(input[0].toString(), (Long)input[1],(Long)input[2],polTypeLIst);		
		
		outData[0] = resultCount.get(0);
		outData[1] = resultCount.get(1);
		output.setData(outData);
		
		return output;
	}

	/*Wunderman WebServices fix*/
	@Override
	public BaseVO getQuoteForPolicy(BaseVO baseVO) {
		CommonVO commonVO = null;
		Long policyNo = 0L;
		String query = "";
		String errorMsg= null;
		
	if( baseVO instanceof CommonVO )
	{
		commonVO = (CommonVO) baseVO;
		policyNo = commonVO.getPolicyNo();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		String sysDate = sdf.format( getSysDate() );
		
		if(!Utils.isEmpty(commonVO.getConcatPolicyNo()))
		{
			// CTS - 18.09.2020 - HOME API UAT ISSUE - Retrieve Policy by Policy Number  API - Starts
			query = "SELECT distinct POL_QUOTATION_NO FROM T_TRN_POLICY_QUO WHERE POL_POLICY_NO = " + policyNo +
					" AND POL_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND POL_STATUS in (7) " +
					" AND POL_DISTRIBUTION_CHNL in (8,9,10) AND POL_ISSUE_HOUR = 3 AND POL_CLASS_CODE = 2 AND POL_POLICY_TYPE = 7 " +
					"AND pol_policy_id in (select csh_policy_id from t_mas_cash_customer_quo where CSH_E_EMAIL_ID = '" + commonVO.getConcatPolicyNo() + "')";
				
			// CTS - 18.09.2020 - HOME API UAT ISSUE - Retrieve Policy by Policy Number  API - Ends
					
			/*query = " SELECT  distinct POL_QUOTATION_NO " + " FROM T_TRN_POLICY_QUO WHERE pol_REF_policy_no =" +policyNo +
			" AND POL_POLICY_YEAR = "+Calendar.getInstance().get(Calendar.YEAR) 
			+" and pol_status in (1,6,23)  and (pol_cover_note_hour in(1201,1205,1210)) and pol_issue_hour = 3 and pol_policy_id in (select csh_policy_id from t_mas_cash_customer_quo where CSH_E_EMAIL_ID = '"+commonVO.getConcatPolicyNo() +"')";
			*/
			errorMsg = REN_QUOTE_RETRIEVEL_MSG1;
		}
		else if(!Utils.isEmpty(commonVO.getCreatedBy()))
		{
			query = "SELECT distinct POL_QUOTATION_NO FROM T_TRN_POLICY_QUO WHERE pol_policy_no = " +policyNo 
					+ com.Constant.CONST_AND_POL_EXPIRY_DATE_END+ sysDate + "' AND POL_VALIDITY_EXPIRY_DATE = '31-DEC-49' AND pol_status in (7) "
					+ "AND POL_DISTRIBUTION_CHNL in (8,9,10) AND pol_issue_hour = 3 AND POL_CLASS_CODE = 5 AND POL_POLICY_TYPE in (6,31)"
					+ " AND pol_policy_id in (select gpr_policy_id from t_trn_gacc_person_quo where GPR_RELATION = 1 and trunc(gpr_date_of_birth) = '"
					+ commonVO.getCreatedBy() +"')";
			
			/*query = " SELECT  distinct POL_QUOTATION_NO " + " FROM T_TRN_POLICY_QUO WHERE pol_REF_policy_no =" +policyNo +
			" AND POL_POLICY_YEAR = "+Calendar.getInstance().get(Calendar.YEAR) 
			+" and pol_status in (1,6,23) and (pol_cover_note_hour in (1208,1209)) and pol_issue_hour = 3 and pol_policy_id in (select gpr_policy_id from t_trn_gacc_person_quo where GPR_RELATION = 1 and trunc(gpr_date_of_birth) = '"+commonVO.getCreatedBy() +"')";
			*/
			errorMsg = REN_QUOTE_RETRIEVEL_MSG3;
		}
		
	}
	
	Session session = getHibernateTemplate().getSessionFactory().openSession();
	Query sqlQuery = session.createSQLQuery( query );
	List<Object> result = sqlQuery.list();
	DataHolderVO policyIdHolder = new DataHolderVO();
	if( Utils.isEmpty( result ))
	{
		policyIdHolder.setData("There is no Quote for this Policy");
		//throw new BusinessException( com.Constant.CONST_CMN_ERROR, null, errorMsg +" :"+policyNo );
		throw new BusinessException( "cmn.error", null, errorMsg );
	}
	
	else if( !Utils.isEmpty( result ) && result.size() > 1)
	{
		policyIdHolder.setData( Long.valueOf( result.get( 0 ).toString() ) );
		throw new BusinessException( "cmn.error", null, REN_QUOTE_RETRIEVEL_MSG2+" :"+policyNo );
	}
	else if( !Utils.isEmpty( result ) && result.size() == 1)
	{
		policyIdHolder.setData( Long.valueOf( result.get( 0 ).toString() ) );
		return policyIdHolder;
	}
	session.close();
	return null;
	}
	
	
	}
