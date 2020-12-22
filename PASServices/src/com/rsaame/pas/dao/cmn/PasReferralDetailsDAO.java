package com.rsaame.pas.dao.cmn;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.model.TTrnPasReferralDetailsId;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;



public class PasReferralDetailsDAO extends BaseDBDAO implements IReferralDetailsDAO{
	private final static String RENEWAL_CLAIM_EXISTS = "renewalClaimExist";
	private final static String RENEWAL_OS_PREMIUM_EXISTS = "outstandingPriorPremium";
	private final static String RENEWAL_END_AFTER_REN_QUOTE = "allowEndorsementAfterRenewal";
	private final static String DISCOUNT_LOADING = "discountLoading";
	private final static String BR_ACC_BLOCKED = "brAccBlockedStatus";
	
	public void insertReferalData(  BaseVO baseVO ){		
		if(Utils.isEmpty( baseVO ))
		{
			return;
		}
		ReferralVO referralVO = (ReferralVO) baseVO;
		TTrnPasReferralDetails pasReferaalDetails = new TTrnPasReferralDetails();
		Map <String, String> refDataMap = referralVO.getReferalDataMap();
		TTrnPasReferralDetailsId id = new TTrnPasReferralDetailsId();
		id.setPolLinkingId(referralVO.getPolLinkingId());
		id.setLocationId( Long.valueOf( referralVO.getRiskGroupId() ) );
		id.setSecId( referralVO.getSectionId().shortValue() );
		// Added the condition to set all the composite-Id TTrnPasReferralDetailsId in SBS flow - For issue 77934
		
		// Add comment to avoid empty if block(Sonar defect ) 12-9-2017
		/*if(referralVO.getPolicyTypeCode()==50);
		{*/
			id.setRefPolicyId(referralVO.getPolLinkingId());
			id.setRefEndId(Long.valueOf( 0 )); // Radar fix
		/*}*/
		for( Map.Entry<String, String> data : refDataMap.entrySet() ){
			id.setRefField( data.getKey());
			id.setRefValue (data.getValue());
			pasReferaalDetails.setId( id );
			try{
				/* Merge works here not saveOrUpdate as we are creating new Id object every time and in case save operation is performed
				 * more than once on a section then there is possibility of getting different object with same identifier already exists
				 * in the session exception from hibernate */
				getHibernateTemplate().merge( pasReferaalDetails );
				getHibernateTemplate().flush();
				getHibernateTemplate().clear();
				
			}
			catch( DataAccessException e ){
				throw new SystemException( "wc.tempInsert.fail", e, "Error while updating data to TempPasReferral table" );
			}
		}
		

	}
	
	@Override
	/*
	 * To check whether Referral is needed for different params
	 */
	public BaseVO isReferralNeeded( BaseVO baseVO ){
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] refData = holderVO.getData();
		Long linkingId = (Long) refData[ 0 ];
		Short secId = (Short) refData[ 1 ];
		Long locId = (Long) refData[ 2 ];
	//	DataHolderVO<Boolean> refNeeded = new DataHolderVO<Boolean>();
		
		DataHolderVO<Object[]> refNeeded =  new DataHolderVO<Object[]>();
		Object refDetails[] = new Object[5];
		List<TTrnPasReferralDetails> refList;		
		
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_POLLINKINGID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,linkingId,secId,locId, RENEWAL_CLAIM_EXISTS,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[0] = true;
		} else {
			refDetails[0] = false;
		}
		
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_POLLINKINGID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,linkingId,secId,locId,RENEWAL_OS_PREMIUM_EXISTS,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[1] = true;
		} else {
			refDetails[1] = false;
		}
		// Rule Yet to be implemented 
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_POLLINKINGID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,linkingId,secId,locId,RENEWAL_END_AFTER_REN_QUOTE,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[2] = true;
		} else {
			refDetails[2] = false;
		}
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_POLLINKINGID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,linkingId,secId,locId,DISCOUNT_LOADING,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[3] = true;
		} else {
			refDetails[3] = false;
		}
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_POLLINKINGID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,linkingId,secId,locId,BR_ACC_BLOCKED,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[4] = true;
		} else {
			refDetails[4] = false;
		}
		refNeeded.setData( refDetails );
		return refNeeded;
	} 
	
	/*
	 * To store the renewal referrals to T_TRN_TEMP_PAS_REFERRAL table
	 */

	public void storeRenewalReferrals( BaseVO baseVO ){
		PolicyVO policyVO = (PolicyVO)baseVO;
		DAOUtils.deleteReferralRecordsForKey( policyVO.getPolLinkingId(), Long.valueOf(  Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_RENEWAL )  ) , Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.REN ) ) , getHibernateTemplate()  );
		TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean( "tempPasReferralDAO" );
		ReferralVO referralVO = DAOUtils.getRenewalReferralVO( policyVO );		
		insertTempPasReferalDao.insertReferal( referralVO );
	}
	
	/*
	 * To delete the renewal referrals from T_TRN_TEMP_PAS_REFERRAL table
	 */
	public void deleteRenewalReferral(BaseVO baseVO){
		PolicyVO policyVO = (PolicyVO)baseVO;
		DAOUtils.deleteReferralRecordsForKey( policyVO.getPolLinkingId(), Long.valueOf(  Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_RENEWAL )  ) , Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.REN ) ) , getHibernateTemplate()  );
	}
	
	/**
	 * 
	 * @param polLinkingId
	 * @param newEndtId
	 * @return
	 */
	public BaseVO getEndorsementText(){
		/*java.util.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>(EndorsmentVO.class);
		List<VTrnPasGetEndtText> pasGetEndtTextList = (List<VTrnPasGetEndtText>) getHibernateTemplate().find(
				" from VTrnPasGetEndtText where id.polLinkingId = ? ", Long.valueOf(531613));
		DataHolderVO<List<EndorsmentVO>>endorsementData = new DataHolderVO<List<EndorsmentVO>>();
		String endText = "";
		EndorsmentVO endorsmentVO = null;
		if( !Utils.isEmpty( pasGetEndtTextList ) ){

			for( VTrnPasGetEndtText vTrnPasGetEndtText : pasGetEndtTextList ){
				endorsmentVO = new EndorsmentVO();
				endorsmentVO.setEndText(vTrnPasGetEndtText.getEndtText());
				endorsements.add(endorsmentVO );
			}
		}
		endorsementData.setData( endorsements );
		
		return endorsementData;*/
		java.util.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>(EndorsmentVO.class);
		DataHolderVO<List<EndorsmentVO>>endorsementData = new DataHolderVO<List<EndorsmentVO>>();
		EndorsmentVO endorsmentVO = null;
		
		//DataHolderVO<Long> holderVO = (DataHolderVO<Long>) baseVO;
		Long renQuoteNo  = Long.valueOf( "2416636" );//(Long)holderVO.getData();
		
		TTrnPolicyQuo policyQuo = (TTrnPolicyQuo) getHibernateTemplate().find( "from TTrnPolicyQuo ttrnPol where ttrnPol.polQuotationNo=?",renQuoteNo ).get( 0 );
		//String policySql = "select count(1) from t_trn_policy where To_Char(Pol_Modified_Dt,'dd-MON-yyyy hh:mi:ss') > to_char("+
		//	policyQuo.getPolModifiedDt()+", 'dd-MON-yyyy hh:mi:ss') and pol_validity_expiry_date ='31-DEC-49' and  pol_policy_no ="+policyQuo.getPolPolicyNo();
		
//		String policySql = "select EDL_TEXT from T_TRN_ENDORSEMENT_DETAIL where(edl_policy_id, edl_endorsement_id) in (" +
//		"select pol_policy_id,Pol_Endt_Id from t_trn_policy where To_Char(Pol_Modified_Dt,'dd-MON-yyyy hh:mi:ss') > to_char("+
//		policyQuo.getPolModifiedDt()+", 'dd-MON-yyyy hh:mi:ss') and pol_validity_expiry_date ='31-DEC-49' and  pol_policy_no ="+policyQuo.getPolPolicyNo()+")";
		
		String policySql = "select EDL_TEXT from T_TRN_ENDORSEMENT_DETAIL where(edl_policy_id, edl_endorsement_id) in (" +
		"select pol_policy_id,Pol_Endt_Id from t_trn_policy where To_Char(Pol_Modified_Dt,'dd-MON-yyyy hh:mi:ss') > "+
		"(select To_Char(pol_prepared_dt ,'dd-MON-yyyy hh:mi:ss') from t_trn_policy_quo where pol_quotation_no = "+renQuoteNo+" and pol_endt_id =0)"+
	     " and  pol_policy_no ="+policyQuo.getPolPolicyNo()+") order by edl_endorsement_id";
		
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		Double osPremiumSum ;
		String osPremiumQuery = "select sum(sum_amount) outstanding_premium from t_trn_gl_unmatched_aic ,t_trn_policy where t_trn_gl_unmatched_aic.ref_tran_type in (1,2) and "+
		"t_trn_gl_unmatched_aic.ref_tran_id=t_trn_policy.pol_ref_policy_id and t_trn_policy.pol_policy_id in(select pol_ref_policy_id from t_trn_policy_quo where pol_quotation_no = " +renQuoteNo+")";
		
		Query query = session.createSQLQuery(osPremiumQuery);
		osPremiumSum=(Double)query.uniqueResult();
		DataHolderVO<Double> prmVO =  new DataHolderVO<Double>();
		prmVO.setData( osPremiumSum );
		
		query = session.createSQLQuery(policySql);
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
		//List<Object>result = query.e
		return endorsementData;

	}
	
	@Override
	/*
	 * To check whether Referral is needed for different params
	 */
	public BaseVO isReferralNeededForHomeAndTravel( BaseVO baseVO ){
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] refData = holderVO.getData();
		Long policyId = (Long) refData[ 0 ];
		Short secId = (Short) refData[ 1 ];
		Long locId = (Long) refData[ 2 ];
	//	DataHolderVO<Boolean> refNeeded = new DataHolderVO<Boolean>();
		
		DataHolderVO<Object[]> refNeeded =  new DataHolderVO<Object[]>();
		Object refDetails[] = new Object[3];
		List<TTrnPasReferralDetails> refList;		
		
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_REFPOLICYID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,policyId,secId,locId, RENEWAL_CLAIM_EXISTS,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[0] = true;
		} else {
			refDetails[0] = false;
		}
		
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_REFPOLICYID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,policyId,secId,locId,RENEWAL_OS_PREMIUM_EXISTS,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[1] = true;
		} else {
			refDetails[1] = false;
		}
		// Rule Yet to be implemented 
		refList = getHibernateTemplate().find(com.Constant.CONST_FROM_TTRNPASREFERRALDETAILS_WHERE_ID_REFPOLICYID_AND_ID_SECID_AND_ID_LOCATIONID_AND_ID_REFFIELD_AND_REFSTATUS_END,policyId,secId,locId,RENEWAL_END_AFTER_REN_QUOTE,Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ));
		if(Utils.isEmpty( refList )){
			refDetails[2] = true;
		} else {
			refDetails[2] = false;
		}
		refNeeded.setData( refDetails );
		return refNeeded;
}

	@Override
	public void insertReferalDataDisc(PolicyVO policyVO) {
		
		TTrnPasReferralDetails pasReferaalDetails = new TTrnPasReferralDetails();
		
		TTrnPasReferralDetailsId id = new TTrnPasReferralDetailsId();
		id.setPolLinkingId(policyVO.getPolLinkingId());
		id.setLocationId(Long.valueOf(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) );
		id.setSecId( policyVO.getRiskDetails().get(0).getSectionId().shortValue() );
		// Added the condition to set all the composite-Id TTrnPasReferralDetailsId in SBS flow - For issue 77934
		
			id.setRefPolicyId(policyVO.getPolLinkingId());
			id.setRefEndId(Long.valueOf( policyVO.getEndtId() )); 
			pasReferaalDetails.setRefStatus(policyVO.getStatus().toString());
		
			id.setRefField(DISCOUNT_LOADING );
			id.setRefValue (policyVO.getPremiumVO().getDiscOrLoadPerc().toString()); 
			pasReferaalDetails.setId( id );
			try{
				/* Merge works here not saveOrUpdate as we are creating new Id object every time and in case save operation is performed
				 * more than once on a section then there is possibility of getting different object with same identifier already exists
				 * in the session exception from hibernate */
				getHibernateTemplate().merge( pasReferaalDetails );
				getHibernateTemplate().flush();
				getHibernateTemplate().clear();
				
			}
			catch( DataAccessException e ){
				throw new SystemException( "wc.tempInsert.fail", e, "Error while updating data to TempPasReferral table" );
			}
				
		
	} 
	public BaseVO deleteReferral(BaseVO baseVO){
		PolicyVO policyVO = (PolicyVO)baseVO;
		DAOUtils.deleteReferral( policyVO.getPolLinkingId() , getHibernateTemplate()  );
		
		RuleHandler ruleHandler=new RuleHandler();
		ruleHandler.callRulesForAllSection(baseVO);
						
		return policyVO;
	}
}
