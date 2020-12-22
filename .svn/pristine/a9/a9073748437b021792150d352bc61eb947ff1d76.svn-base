package com.rsaame.pas.endorse.dao;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.cmn.TempPasReferralDAO;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.pojo.mapper.PolicyVOToTMasInsuredPOJO;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;

public class GeneralInfoSaveDAO extends BaseDBDAO implements IGeneralInfoSaveDao{
	private static final Logger logger = Logger.getLogger( GeneralInfoSaveDAO.class );
	private final static Integer GEN_SECTION_ID = 0;
	private final static Long LOC_ID = Long.valueOf( "0" );
	private final static String QUOTE_SEQ_SBS = "SEQ_SBS_QUO_NO";
	private  boolean issuinbBrChange;
	@Override  
	public BaseVO saveGeneralInfoDetails(BaseVO baseVO) {

		PolicyVO policyVO = (PolicyVO) baseVO;
		//TTrnPolicyQuo trnPolicy=null;
		PASStoredProcedure sp=null;
		
		clearThreadLevelContext();
		/* Let us get the system date right now and use from here on for this transaction. This date is not sysdate directly rather
		 * obtained by executing function get_revised_pol_issuedate to retrieve right issue_date/VSD for the transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow()) );
		if (Utils.isEmpty(policyVO.getNewEndtId()))
		{
			Map	results=DAOUtils.endFlowGeneralInfo(policyVO.getPolLinkingId(),policyVO.getIsQuote());
	
			policyVO.setNewEndtId(Long.valueOf(results.get("p_endt_id").toString()));
			policyVO.setNewEndtNo(Long.valueOf(results.get("p_endt_no").toString()));
			
			/* We need to set the validity start date for the endorsement as the current date-time, if the Endt_Id is new. If the Endt_Id is
			 * a continuing one, which happens if there is a pending endorsement for this policy, then we need to use the already existing
			 * validity start date in the policy. This would have been mapped to the PolicyVO when it was loaded. */
			int isNew = ( (Number) results.get( "p_is_new" ) ).intValue();
			if( isNew == 1 ){

				if( DAOUtils.validateVSD( policyVO, getHibernateTemplate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL ) ){
					Date vsd = DAOUtils.getValidityStartDate( getHibernateTemplate(), policyVO.getNewEndtId() , policyVO.getPolLinkingId(), policyVO.getIsQuote() );
					policyVO.setNewValidityStartDate( SvcUtils.incrementVSD( vsd ) );
					policyVO.setValidityStartDate( SvcUtils.incrementVSD( vsd ) );
				}
				else{
					policyVO.setNewValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
					policyVO.setValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
				}

			}
			
			ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, policyVO.getNewValidityStartDate() );
		}else{
			ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, policyVO.getNewValidityStartDate() );
		}
		
		/*
		 * After executing above if block endorsement id should be there in policyVO
		 */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID, policyVO.getNewEndtId() );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_NO, policyVO.getNewEndtNo() );
		
		/*
		 * Updating T_MAS_CUSTOMER table records for every class entries. 
		 */
			Long endtIdToProcess = DAOUtils.getEndtToProcess(getHibernateTemplate(),policyVO);
		
		List<TTrnPolicyQuo> policy = DAOUtils.getPolicyRecord( getHibernateTemplate(), endtIdToProcess, policyVO.getPolLinkingId());
		boolean ifExecuteOnce = true;
		for( TTrnPolicyQuo trnPolicy : policy ){

			if( Utils.isEmpty( trnPolicy ) ){
				throw new BusinessException( "cmn.systemError", null, "Could not find basic policy record during pol gen save" );
			}
			
			
			/* Starting of  SBS - Concurrent Transaction issue while editing the quote. Ticket : 158424 */

            PolicyVO polData = (PolicyVO) baseVO;
            
            // TODO: Logic to be moved to respective service
            trnPolicy.setEndtId(polData.getNewEndtId());

            /*
             * Try to get the existing policy record, in case a new record is
             * created the ved of the existing record has to be updated.
             */

            TTrnPolicyQuo existingPolicyData = null;
            if (!Utils.isEmpty(trnPolicy.getId())
                    && !Utils.isEmpty(trnPolicy.getId().getPolicyId())
                    && !Utils.isEmpty(trnPolicy.getId().getEndtId())) {
                if (trnPolicy.getId().getPolicyId() != 0) {// TODO It should
                    // not be mapped
                    // as 0. Check
                    // this.
                    existingPolicyData = getHibernateTemplate().get(
                            TTrnPolicyQuo.class, trnPolicy.getId());
                }
            }
            int modifiedBy=0;
            int loggedUserId=0;
            if (!Utils.isEmpty(polData.getValidityStartDate())
                    && !Utils.isEmpty(existingPolicyData)) {
                Boolean hasStatusChanged = polData.getStatus().byteValue() != existingPolicyData
                        .getPolStatus();
                if (hasStatusChanged
                        && !Utils.isEmpty(policyVO.getAppFlow())
                        && (policyVO.getAppFlow().equals(Flow.RESOLVE_REFERAL) || policyVO
                                .getAppFlow().equals(Flow.EDIT_QUO))) {
                    hasStatusChanged = polData.getStatus().byteValue() != existingPolicyData
                            .getPolStatus();
                }
                SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yy");
                loggedUserId=SvcUtils.getUserId( policyVO );
                String expDate = sd.format(existingPolicyData
                        .getPolValidityExpiryDate());
               
                if (existingPolicyData.getModifiedBy() != null) {
                    modifiedBy = existingPolicyData.getModifiedBy();

                    if (modifiedBy != loggedUserId) {
                        if (!polData.getValidityStartDate().equals(
                                existingPolicyData.getPolValidityStartDate())
                                || !(expDate).equals(SvcConstants.DFAULT_VED)) {

                            if (!Utils.isEmpty(SvcConstants.QUOTE_ACCEPT)) {
                                byte quoteAccStat = Integer.valueOf(
                                        SvcConstants.QUOTE_ACCEPT).byteValue();
                                if (!(existingPolicyData.getPolStatus() == quoteAccStat)) {
                                    throw new BusinessException(
                                            "pas.concurrent.transaction.by.multiple.users",
                                            null,
                                            "The same quote is edited by Another User please do transaction search once again to have the latest update.");
                                }
                            }
                        }
                    }
                }
            }
           
            /* End of  SBS - Concurrent Transaction issue while editing the quote. Ticket : 158424 */

			

			TMasInsured tMasInsured = fetchTmasInsured( trnPolicy.getPolInsuredCode() );
			//PolicyExtenstion Change: Removing below line to keep the expiry date coming from request 
			//policyVO.setEndDate( trnPolicy.getPolExpiryDate() );
			policyVO.setStartDate( trnPolicy.getPolIssueDate() );
			//PolicyExtenstion Change: Removing below line to keep the expiry date coming from request 
			//policyVO.setPolExpiryDate( trnPolicy.getPolExpiryDate() );
			policyVO.setPolCustomerId( trnPolicy.getPolCustomerId() );
			policyVO.getAuthInfoVO().setPrintedDate( null );
			TMasCashCustomerQuo tMasCashCustomerQuo = fetchCashCustomerId( trnPolicy, trnPolicy.getId().getPolEndtId(), policyVO );
			Long insuredIdFromCashCustomer = tMasCashCustomerQuo.getCshInsuredId();
			
			if( logger.isDebug() ) logger.debug( "--------------- UPDATING CUSTOMER DETAILS -------------------- " );
			checkIssuingBranchChange(policyVO, trnPolicy);

			if( isGenInfoChanged( policyVO, tMasCashCustomerQuo, trnPolicy ) || uwqChanged(policyVO,trnPolicy) ){
				
/*				if( !policyVO.getIsQuote() ){
					DAOUtils.validateVSD( policyVO, trnPolicy.getPolClassCode()  ,getHibernateTemplate() );
				}*/
				
				Long baseSecPolId = DAOUtils.getBaseSectionPolicyId( policyVO, getHibernateTemplate() );
				
				/* If there is no policy record for this endorsement, we have to create it first. */
				Long policyId = DAOUtils.createPolicyRecord( policyVO, trnPolicy.getPolClassCode(), SvcConstants.SECTION_ID_GEN_INFO );

				getHibernateTemplate().flush();
				/*
				 *update the previous policy record endorsement expiry date
				 *update the current policy record endorsement effective date
				 */
				updatePolicy( policyId, policyVO, trnPolicy.getPolClassCode(), insuredIdFromCashCustomer );

				updateCustomerDetails( policyVO, tMasInsured, trnPolicy );
				
				/*
				 * 
				 * Insert / update under writing details. In case there are policy level uw q's those needs to be saved, with location ID as 0
				 */
				getHibernateTemplate().flush();
				if( !Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getQuestionsVO() )&& !Utils.isEmpty(trnPolicy.getId().getPolicyId()) && trnPolicy.getId().getPolicyId().equals(baseSecPolId )){
					DAOUtils.saveOrUpdateUWQS( policyVO.getGeneralInfo().getQuestionsVO(), baseSecPolId, (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ),
							(Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ), getHibernateTemplate() );
				}
				
				/*
				 * Endorsed data will not be available to procedure to generate endorsement text using old and new data
				 * before the hibernate session is commited and hence flushing the session.
				 */
				getHibernateTemplate().flush();

				//updateEndtText( policyId, tMasCashCustomerQuo.getCshInsuredId(), policyVO );
				
				if( ifExecuteOnce ){
					DAOUtils.updateVED( policyVO, trnPolicy.getPolClassCode(), SvcConstants.SECTION_ID_GEN_INFO );
					updateEndtTextForBaseClass( policyVO, policyId, tMasCashCustomerQuo.getCshInsuredId(), Short.valueOf( String.valueOf( SvcConstants.SECTION_ID_GEN_INFO ) ) );
					ifExecuteOnce = false;
				}
				
				Short secId = null;
				if( !Utils.isEmpty( trnPolicy.getId().getPolPolicyId() ) && !Utils.isEmpty( trnPolicy.getPolClassCode() ) ){
					secId = DAOUtils.getSectionIdForPolicyId( getHibernateTemplate(), trnPolicy.getPolClassCode(),trnPolicy.getId().getPolPolicyId(), policyVO.getIsQuote() );
				}else{
					throw new BusinessException( "cmn.unknownError", null, "The policy Id null in getSectionIdForPolicyId " );
				}
				
				if( !Utils.isEmpty(secId)){
					/* Update the VED for the previous Endt_Id's Policy and Cash Customer records. */
					DAOUtils.updateVED( policyVO, trnPolicy.getPolClassCode(), secId );
					updateEndtTextForBaseClass(policyVO,policyId,tMasCashCustomerQuo.getCshInsuredId(),secId);
				}
				
			}

		}
		
			
		/* To delete the existing records from temp referral table before actually checking if the referral is
		 * triggered for this insert/update operation which is done by checking is policyVO.getMapReferralVO is not
		 * null  */
		DAOUtils.deleteReferralRecordsForKey( policyVO.getPolLinkingId(), Long.valueOf(  Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_GENERAL )  ) , 
		Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GENERAL ) ) , getHibernateTemplate()  );
		
		/*Start of ticket 137704 */
		int userId=0;
		String role=null;
		if(baseVO instanceof PolicyVO) {
			PolicyVO policyVO1 = (PolicyVO) baseVO;
			UserProfile userProfileVO = (UserProfile) policyVO1.getLoggedInUser();
			if (!Utils.isEmpty(userProfileVO)) {
			userId= userProfileVO.getRsaUser().getUserId();       
			role=DAOUtils.getUserRole(policyVO);
			}
		}
		
		/*End of ticket 137704 */

		TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean( "tempPasReferralDAO" );
		ReferralVO referralVO = DAOUtils.getReferralVOForSave( policyVO );	
		/*Start of ticket 137704 */
		if (!Utils.isEmpty(referralVO)) {
			referralVO.setTprUserId(userId);
			referralVO.setTprUserRole(role);
		}
		/*End of ticket 137704 */

		insertTempPasReferalDao.insertReferal( referralVO );
		
		
		return policyVO;
	}
	
	/*
	 * This method updates the previous policy records endorsement expiry date
	 * Sets the endorsement effective date for the newly created policy record
	 *  
	 */

	/**
	 * @param policyVO
	 * @param trnPolicy
	 * @return
	 */
	private boolean uwqChanged( PolicyVO policyVO, TTrnPolicyQuo trnPolicy ){

		List<UWQuestionVO> questionList = policyVO.getGeneralInfo().getQuestionsVO().getQuestions();

		for( UWQuestionVO questionVO : questionList ){

		/*	List<TTrnUwQuestionsQuo> questionsQuo = getHibernateTemplate().find("from TTrnUwQuestionsQuo tUwqs where tUwqs.id.uqtPolPolicyId = ? "
							+ "and tUwqs.id.uqtLocId = ? and tUwqs.uqtValidityStartDate <= ? and tUwqs.uqtValidityExpiryDate > ? and "
							+ "tUwqs.id.uqtPolEndtId <= ? and  tUwqs.uqtStatus <> 4 and tUwqs.uqtUwqAnswer = ? and tUwqs.id.uqtUwqCode = ?",
					DAOUtils.getBaseSectionPolicyId( policyVO, getHibernateTemplate() ),Long.valueOf( String.valueOf( SvcConstants.zeroVal ) ) , trnPolicy.getPolValidityStartDate(),
					trnPolicy.getPolValidityStartDate(), trnPolicy.getId().getPolEndtId(), questionVO.getResponse(), questionVO.getQId() );
			*/
			Criteria c  = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(TTrnUwQuestionsQuo.class);
			c.add(Restrictions.eq("id.uqtPolPolicyId", DAOUtils.getBaseSectionPolicyId( policyVO, getHibernateTemplate() )));
			c.add(Restrictions.eq("id.uqtLocId",Long.valueOf( String.valueOf( SvcConstants.zeroVal ) )) );
			c.add(Restrictions.le("uqtValidityStartDate", trnPolicy.getPolValidityStartDate()));
			c.add(Restrictions.gt("uqtValidityExpiryDate", trnPolicy.getPolValidityStartDate()) );
			c.add(Restrictions.le("id.uqtPolEndtId", trnPolicy.getId().getPolEndtId() ));
			c.add(Restrictions.ne("uqtStatus", SvcConstants.DEL_SEC_LOC_STATUS) );
			c.add(Utils.isEmpty(questionVO.getResponse())? Restrictions.isNull("uqtUwqAnswer") : Restrictions.eq("uqtUwqAnswer", questionVO.getResponse()));
			c.add(Restrictions.eq("id.uqtUwqCode",questionVO.getQId()));
			List<TTrnUwQuestionsQuo> questionsQuo  = c.list();
			
			getHibernateTemplate().evict( questionsQuo );
			
			if( Utils.isEmpty( questionsQuo ) || ( !Utils.isEmpty( questionsQuo ) && questionsQuo.size() == SvcConstants.zeroVal ) ){
				return true;
			}
		}
		return false;
	}

	private TTrnPolicyQuo fetchPolicyRecord(long policyId, long endtId) {
		TTrnPolicyQuo prevPolicyRec = null;
		try{
					prevPolicyRec=(TTrnPolicyQuo) getHibernateTemplate().find("from TTrnPolicyQuo pol where  pol.id.polPolicyId=? order by pol.id.polEndtId ",policyId ).get(0);
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT policy details from T_TRN_POLICY_QUO" );
		}
			
		return prevPolicyRec;
	}

	private void updatePolicy( Long policyId, PolicyVO policyVO, Short classCode, Long insuredIdFromCashCustomer ){
		
		Long prevEndtId = DAOUtils.getPrevEndtId( getHibernateTemplate(), policyVO.getIsQuote(), policyId, (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		
		
		/* Get the current Endt_Id-state record, if key is available. That is, the record with the Endt_Id got from the database function. */
		TTrnPolicyQuo prevPolicyRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), getHibernateTemplate(), false,
				prevEndtId, policyVO.getPolLinkingId(), classCode );

		if( !Utils.isEmpty( prevPolicyRecord ) ){
			
			/*In case of endorsement, update the previous record endorsement expiry date to endorsement effective 
			 date */
			if( !Utils.isEmpty( policyVO.getEndEffectiveDate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL  ) ){
			    prevPolicyRecord.setPolEndtExpiryDate( policyVO.getEndEffectiveDate() );
			    update( prevPolicyRecord );
			}
				
		}
		
		TTrnPolicyQuo currPolicyRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), getHibernateTemplate(), false, 
		policyVO.getNewEndtId(),policyVO.getPolLinkingId(), classCode );
		
		/*
         * The status of policy will pending as the endorsement is initiated until endorsement it confirmed/cancel
         */
		if ( (!policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_REFERRED )) ) ) 
		&& (!policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.CONV_TO_POL )) ) ) 
		&& (!policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_SOFT_STOP ) ) ) ) ){
			policyVO.setStatus( SvcConstants.POL_STATUS_PENDING );
		}
		
		currPolicyRecord.setPolEndtEffectiveDate( policyVO.getEndEffectiveDate() );
		currPolicyRecord.setPolExpiryDate( policyVO.getPolExpiryDate() );
		currPolicyRecord.setPolPolicyTerm( policyVO.getPolicyTerm() );
		//Advent Ticket: 104188; Added as created Date is overridden by Current date.
		if(!Utils.isEmpty(policyVO.getCreated())){
			currPolicyRecord.setPolPreparedDt(policyVO.getCreated());
		}else{
			currPolicyRecord.setPolPreparedDt((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ));
		}
		
		/* Setting modified date so that the same can be used for transaction date & time during transaction search. */
		currPolicyRecord.setPolModifiedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		currPolicyRecord.setPolModifiedBy( SvcUtils.getUserId( policyVO ) );
		
		if(!Utils.isEmpty(policyVO.getAuthInfoVO() ) && !Utils.isEmpty( policyVO.getAuthInfoVO().getLicensedBy() )){
			currPolicyRecord.setPolUserId( policyVO.getAuthInfoVO().getLicensedBy() );
		}

		/* Setting modified distribution channel details in copy quote flow. */
		currPolicyRecord.setPolBrCode( Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ? null : Short.valueOf( policyVO.getGeneralInfo()
				.getSourceOfBus().getBrokerName().toString() ) );
		
		currPolicyRecord.setPolCoverNoteHour( Utils.isEmpty( policyVO.getScheme().getSchemeCode() ) ? null : policyVO.getScheme().getSchemeCode() );
		
		if( !Utils.isEmpty(prevPolicyRecord) && !Utils.isEmpty(prevPolicyRecord.getPolPolicyFees()))
        {
              currPolicyRecord.setPolPolicyFees(prevPolicyRecord.getPolPolicyFees());
        }

		currPolicyRecord.setPolAgentId( Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ) ? null : Long.valueOf( policyVO.getGeneralInfo()
		.getSourceOfBus().getDirectSubAgent() ) );
				
		/*
		 * modified code to set issue location and processing location of quote. 
		 */
		if(!Utils.isEmpty( policyVO.getGeneralInfo()) && !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo()) 
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc())){
			currPolicyRecord.setPolLocationCode( policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc().shortValue());
		}
		if(!Utils.isEmpty( policyVO.getGeneralInfo()) && !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo()) 
				&& !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc())){
			currPolicyRecord.setPolProcLocCode( policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc());
		}
		// If the issuing branch changes for renewal quote then quotation number is also changes
		if(currPolicyRecord.getPolDocumentCode()==Short.valueOf(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))){
			currPolicyRecord.setPolQuotationNo(policyVO.getQuoteNo());
		}
		
		/*
		 * Set the start date and end date of the quotation.
		 *Start date should be the date the user selects. Hence update the copied 
		 *policy record
		 */
		SvcUtils.setStartEndDate( currPolicyRecord, policyVO.getScheme().getEffDate() );

		currPolicyRecord.setPolStatus( policyVO.getStatus().byteValue() );
		/*
		 * Incorporate a feature to capture Renewals Terms in General Info
		 */
		if(!Utils.isEmpty(policyVO.getAuthInfoVO())){
			currPolicyRecord.setPolRenTermTxt( policyVO.getAuthInfoVO().getRenewalTerms());
		}
		
	//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
			if(!Utils.isEmpty(policyVO.getRenewalBasis())){
				currPolicyRecord.setPolRenewalBasis(policyVO.getRenewalBasis());
			}
			//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
		
		if( !Utils.isEmpty( insuredIdFromCashCustomer ) ) currPolicyRecord.setPolInsuredId( insuredIdFromCashCustomer );

		currPolicyRecord.setPolPrintDate(null);
		
		/* 
		 * AdverNet Ticket:: Issue: 109230 ,CR: 124044
		 * Change of Broker on Record ,customer id  should be updated on table t_trn_policy_quo, t_mas_cash_customer_quo
		 */	
        Long customerId = DAOUtils.getCustoemrId(getHibernateTemplate(), policyVO.getScheme().getSchemeCode());
        
		currPolicyRecord.setPolCustomerId(Utils.isEmpty( customerId ) ? null : customerId);
		/*VAT*/
		currPolicyRecord.setPolvatCode(policyVO.getPolVATCode());
		currPolicyRecord.setPolVatRegNo(policyVO.getPolVatRegNo());
		update( currPolicyRecord );

	}

	private void clearThreadLevelContext(){
		ThreadLevelContext.clear( SvcConstants.TLC_KEY_SYSDATE );
		ThreadLevelContext.clear( SvcConstants.TLC_KEY_VSD );
		ThreadLevelContext.clear( SvcConstants.TLC_KEY_ENDT_ID );
		ThreadLevelContext.clear( SvcConstants.TLC_KEY_ENDT_NO );
		ThreadLevelContext.clearAll();

	}

	private boolean isGenInfoChanged( PolicyVO policyVO, TMasCashCustomerQuo tMasCashCustomerQuo, TTrnPolicyQuo prevPolicyRec ){

		boolean isTTrnPolicyChanged = false;
		TMasCashCustomerQuo tMasCashCustomerQuoNew = BeanMapper.map( policyVO, TMasCashCustomerQuo.class );
		/*The below change is required, so that we can update the policy and cash customer table even if user changes policy information
		 * without changing customer information*/
		TTrnPolicyQuo policyQuoNew = mapPolicyVOToTTrnPolicy( policyVO, prevPolicyRec );

		/*
		 * evicting the fetched rec from hibernate session to avoid concurrent modification of ID'd while updating the cash customer rec later   
		 */
		getHibernateTemplate().evict( tMasCashCustomerQuoNew );
		getHibernateTemplate().evict( tMasCashCustomerQuo );

		isTTrnPolicyChanged = SvcUtils.hasDataChanged( policyQuoNew, prevPolicyRec );
		Date prevPolicyRecEffecDt = getDateFromTimestamp( prevPolicyRec.getPolEffectiveDate() );
		if( !( prevPolicyRecEffecDt.equals( policyQuoNew.getPolEffectiveDate() ) ) ){
			isTTrnPolicyChanged = true;
		}
		return ( SvcUtils.hasDataChanged( tMasCashCustomerQuoNew, tMasCashCustomerQuo ) || isTTrnPolicyChanged );

	}
	
	// Oman multibranching : If issuing branch changed then generate new quotation no
	private void checkIssuingBranchChange(PolicyVO policyVO,TTrnPolicyQuo prevPolicyRec){
		if(prevPolicyRec.getPolDocumentCode() ==6){
			
		// Added Short.valueOf() to avoid sonar violation on 18-9-2017
		//	Short locCode = new Short(prevPolicyRec.getPolLocationCode());
			Short locCode = Short.valueOf(prevPolicyRec.getPolLocationCode());
			
			if(!locCode.toString().equals(policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc().toString())){
				Long newQuoteNo = NextSequenceValue.getNextSequence( QUOTE_SEQ_SBS, Utils.getSingleValueAppConfig( "TRAN_TYPE_QUO" ),policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc(),getHibernateTemplate() ) ;
				policyVO.setQuoteNo(newQuoteNo);
				issuinbBrChange =  true;
			}
		}
	}
	
	/**
	 * 	
	 * @param dt
	 * @return
	 */
	private Date getDateFromTimestamp(Date dt) {
		
		Calendar cal = Calendar.getInstance();   
		cal.setTime(dt);   
		  
		// Set time fields to zero   
		cal.set(Calendar.HOUR_OF_DAY, 0);   
		cal.set(Calendar.MINUTE, 0);   
		cal.set(Calendar.SECOND, 0);   
		cal.set(Calendar.MILLISECOND, 0);   
		  
		return cal.getTime();
	}

	private TTrnPolicyQuo mapPolicyVOToTTrnPolicy( PolicyVO policyVO, TTrnPolicyQuo prevPolicyRec ){
		TTrnPolicyQuo policyQuo = new TTrnPolicyQuo();
		policyQuo.setPolEffectiveDate( policyVO.getScheme().getEffDate() );
		policyQuo.setPolTarCode( policyVO.getScheme().getTariffCode().shortValue() );
		/*VAT*/
		policyQuo.setPolvatCode(policyVO.getPolVATCode());
		// Oman multibranching : issuing branch can be changed for renewal quotes
		policyQuo.setPolLocationCode(new Short (policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc().toString()));
		
		//Oman multibranching : processing branch can be changed during endorsements and quote versioning.
		policyQuo.setPolProcLocCode(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc());
		
		policyQuo.setPolBrCode( Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ? null : Short.valueOf( policyVO.getGeneralInfo().getSourceOfBus()
				.getBrokerName().toString() ) );

		policyQuo.setPolAgentId( Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ) ? null : Long.valueOf( policyVO.getGeneralInfo().getSourceOfBus()
				.getDirectSubAgent() ) );
		/*
		 * Incorporate a feature to capture Renewals Terms in General Info
		 */
		if(!Utils.isEmpty(policyVO.getAuthInfoVO())){
			policyQuo.setPolRenTermTxt( policyVO.getAuthInfoVO().getRenewalTerms() );
		}
		return policyQuo;
	}

	private void updateCustomerDetails(PolicyVO policyVO,TMasInsured tMasInsured,TTrnPolicyQuo trnPolicy) {
		
		SectionVO section =  new SectionVO( RiskGroupingLevel.LOCATION );
		section.setPolicyId( trnPolicy.getId().getPolPolicyId() );
		DAOUtils.updateCustomerDetails( policyVO, section, getHibernateTemplate() );
	}
 
    
    public TMasCashCustomerQuo fetchCashCustomerId(TTrnPolicyQuo trnPolicy ,Long endId, PolicyVO policyVO ){

		TMasCashCustomerQuo tMasCashCustomerQuo = null;
		try{
			tMasCashCustomerQuo=(TMasCashCustomerQuo) getHibernateTemplate().find(
					"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? order by cshEndtId desc ",trnPolicy.getId().getPolPolicyId() ).get(0);
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_1" );
		}
			
		return tMasCashCustomerQuo;
	}

    public TMasInsured fetchTmasInsured(Long InsuredCode ){
		TMasInsured tMasInsured = null;
			try{
				tMasInsured=(TMasInsured) getHibernateTemplate().find(" from TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ",InsuredCode ).get(0);
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_2" );
		}
			
		return tMasInsured;
	}

	@Override
	public BaseVO updateTmasInsured(BaseVO baseVO) {
		PolicyVO policyVO = (PolicyVO) baseVO;
		 Long insuredId=policyVO.getGeneralInfo().getInsured().getInsuredId();
		TMasInsured tMasInsured = null;
		try{
			tMasInsured=(TMasInsured) getHibernateTemplate().find("from TMasInsured  tMasInsured where  tMasInsured.insInsuredCode=? ",insuredId ).get(0);
			
			updateTmasInsured(tMasInsured,policyVO);
			getHibernateTemplate().merge(tMasInsured);
	}
	catch( HibernateException hibernateException ){
		throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_3" );
	}
		
	return policyVO;
	
	}

	private void updateTmasInsured( TMasInsured tMasInsured, PolicyVO policyVO ){

		if( !( Utils.isEmpty( policyVO ) && Utils.isEmpty( policyVO.getGeneralInfo() ) && Utils.isEmpty( policyVO.getGeneralInfo().getInsured() ) ) ){
			/*tMasInsured.setInsEFirstName(policyVO.getGeneralInfo().getInsured().getName());
			tMasInsured.setInsAFirstName(policyVO.getGeneralInfo().getInsured().getArabicName());
			
			tMasInsured.setInsEPhoneNo(policyVO.getGeneralInfo().getInsured().getPhoneNo());
			//tMasInsured.setInsNationality(policyVO.getGeneralInfo().getInsured().getNationality());
			tMasInsured.setInsBusiness(policyVO.getGeneralInfo().getInsured().getBusDescription());
			tMasInsured.setInsEMobileNo(policyVO.getGeneralInfo().getInsured().getMobileNo());*/
			
			BaseBeanToBeanMapper<PolicyVO,TMasInsured> polVOToIns = BeanMapperFactory.getMapperInstance( PolicyVOToTMasInsuredPOJO.class );
			tMasInsured = polVOToIns.mapBean( policyVO, tMasInsured );
			BeanMapper.map( policyVO,tMasInsured );
		
		}
	}
    
	
	private void updateEndtText(Long policyId,Long cshInsuredId, PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			
			DAOUtils.deletePrevEndtText(policyId, policyVO.getNewEndtId(),GEN_SECTION_ID, LOC_ID  );
			
			System.out.println( "call pro_endt_text_cash_cust_col" );
			DAOUtils.updateEndTextForGenInfo(policyId,cshInsuredId,policyVO );
			
			// Call to procedure for generating endt text for UW Questions changes.
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.TRADE_LICENSE_CHECK)) && Utils.getSingleValueAppConfig(SvcConstants.TRADE_LICENSE_CHECK).equalsIgnoreCase("N") ){
				System.out.println( "call UW changes change endo SP" );
				DAOUtils.updateUWQuestions( policyId, policyVO, GEN_SECTION_ID ,  LOC_ID );
				//DAOUtils.updateProcLoc( policyId, policyVO, GEN_SECTION_ID ,  LOC_ID );
			}
			
		
		}
	}

	@Override
	public BaseVO compareTmasInsured( BaseVO baseVO ){
		System.out.println("************** In compareTmasInsured ****************");
		PolicyVO policyVO = (PolicyVO) baseVO;
		Long insuredId=policyVO.getGeneralInfo().getInsured().getInsuredId();
		TMasInsured tMasInsured = null;
		try{
			tMasInsured = (TMasInsured) getHibernateTemplate().find("from TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ",insuredId ).get(0);

			policyVO.setInsuredChanged(compareTmasInsured(tMasInsured, policyVO));
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_4" );
		}

		return policyVO;
	}

	private boolean compareTmasInsured( TMasInsured tMasInsured, PolicyVO policyVO ){

		BaseBeanToBeanMapper<PolicyVO,TMasInsured> polVOToIns = BeanMapperFactory.getMapperInstance( PolicyVOToTMasInsuredPOJO.class );
		TMasInsured tMasInsuredNew = new TMasInsured();
		tMasInsuredNew = polVOToIns.mapBean( policyVO, tMasInsuredNew );
		/*tMasInsuredNew.setInsEFirstName(policyVO.getGeneralInfo().getInsured().getName());
		tMasInsuredNew.setInsAFirstName(policyVO.getGeneralInfo().getInsured().getArabicName());
		tMasInsuredNew.setInsEPhoneNo(policyVO.getGeneralInfo().getInsured().getPhoneNo());
		tMasInsuredNew.setInsBusiness(policyVO.getGeneralInfo().getInsured().getBusDescription());
		tMasInsuredNew.setInsEMobileNo(policyVO.getGeneralInfo().getInsured().getMobileNo());
		tMasInsuredNew.setInsEAddress(policyVO.getGeneralInfo().getInsured().getAddress().getAddress());
		tMasInsuredNew.setInsEZipCode(policyVO.getGeneralInfo().getInsured().getAddress().getPoBox());
		tMasInsuredNew.setInsEEmailId(policyVO.getGeneralInfo().getInsured().getEmailId());
		tMasInsuredNew.setInsCtyCode(policyVO.getGeneralInfo().getInsured().getCity());
		tMasInsuredNew.setInsCountry(policyVO.getGeneralInfo().getInsured().getAddress().getCountry().shortValue());
		tMasInsuredNew.setInsAffinityRefNo( policyVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() );
		tMasInsuredNew.setInsRemarks( policyVO.getGeneralInfo().getAdditionalInfo().getRemarks() );
		tMasInsuredNew.setInsDtCollectionKyc( policyVO.getGeneralInfo().getAdditionalInfo().getDateOfcollectionOfKYC() );
		tMasInsuredNew.setInsExpiryDate( policyVO.getGeneralInfo().getAdditionalInfo().getTradeLicenseExpDate() );
		tMasInsuredNew.setInsStatus( policyVO.getGeneralInfo().getAdditionalInfo().getInsuredStatus() );*/
		boolean hasDataChanged = SvcUtils.hasDataChanged( tMasInsuredNew, tMasInsured );
		return hasDataChanged;
	}
	
	/**
	 * 
	 * @param policyVO
	 * @param policyId
	 * @param cshInsuredId
	 * @param trnPolicy
	 * @param secId
	 * @param basicSecId 
	 */
	private void updateEndtTextForBaseClass( PolicyVO policyVO, Long policyId, Long cshInsuredId, Short secId ){

		if( policyVO.getAppFlow() == Flow.AMEND_POL ){

			if( PolicyUtils.getBasicSectionId( policyVO ).equals( Integer.valueOf( secId ) ) ){
				updateEndtText( policyId, cshInsuredId, policyVO );
			}
		}

	}
	
	
}
