package com.rsaame.pas.dao.cmn;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnGaccBuildingQuo;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuo;
import com.rsaame.pas.dao.model.TTrnSectionLocationQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.TableSnapshotLevel;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;

/**
 * Base class for all section save DAOs.
 */
public abstract class BaseSectionSaveDAO extends BaseDBDAO{
	
	private static final Logger LOGGER = Logger.getLogger(BaseSectionSaveDAO.class);
	private final static int ZERO_VAL = 0;
	public BaseVO save( BaseVO input ){
		
		
		PolicyVO policyVO = (PolicyVO) input;
		//SvcUtils.writeObjToFile( policyVO );
		
		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		/*
		 * In case of an exception during save the ThreadLevelContext will not be cleared, to clear it the next time the user saves
		 * in the same session clear the ThreadLevelContextfirst and proceed
		 */
		clearThreadLevelContext();
		
		/* Let us get the system date right now and use from here on for this transaction. This date is not sysdate directly rather
		 * obtained by executing function get_revised_pol_issuedate to retrieve right issue_date/VSD for the transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow()  ) );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.FALSE );
		ThreadLevelContext.set( SvcConstants.HAS_PREMIUM_CHNAGED, Boolean.FALSE );
		
		/* Logic for VSD:
		 * (a) VSD and Endt_Id should go hand-in-hand. That is, for every Endt_Id, there should be only one VSD value used for the entire 
		 *     policy. Hence, it must be cascaded from the PolicyVO.
		 * (b) An "attempt" to generate a new Endt_Id is made when a change-to-be-persisted is detected, ie, a risk detail has changed. This 
		 *     is done by invoking the database function GET_ENDT_ID_NO().
		 * 	   (i)  This function will return the current Endt_Id, if the policy status is "Pending". In this case, the VSD in PolicyVO
		 * 		    should be used because there is no change in the Endt_Id.
		 *     (ii) This function will return the current Endt_Id, if the policy status is "Active". In this case, we have to use the current date 
		 *          as the VSD and this should be set in the PolicyVO and T_TRN_POLICY(_QUO), and this VSD should be used from then on for ALL
		 *          subsequent saves during this Endt_Id.
		 * (c) Hence, at the time of calling GET_ENDT_ID_NO(), we also need to set the value for VSD in ThreadLevelContext. Wherever VSD is needed,
		 *     it will be retrieved from ThreadLevelContext only.
		 */
		
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		//RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
		//RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, section );
		
		/* Let us first generate the Endt_Id. This will initiate values that will be required for the Pojo Id because VSD forms
		 * part of the key in most tables. */
		DAOUtils.fetchEndtId( policyVO , getHibernateTemplate());
		

	
		/*
		 *  Calling handle commission function if section has already being saved before
		 */
		Boolean hasCommissionChanged = false ;
		if(!Utils.isEmpty( section ) && !Utils.isEmpty( section.getPolicyId() )){
			LOGGER.debug( "Calling handleCommmission method to update commission for already saved section" );
			handleCommission( section,policyVO);
			hasCommissionChanged = true;
		}
		/* If the policy Id for this section's class code has not been created, we have to do it now. Both T_TRN_POLICY(_QUO) and
		 * T_MAS_CASH_CUSTOMER(_QUO) records have to be created. */
		
		createPolicyRecord( policyVO, section );
		
		/* Perform any section -pre-processing here. */
		sectionPreProcessing( policyVO );

		/* Invoke the section-specific save activities. */
		BaseVO output = saveSection( policyVO );
		
		/* Handle referral messages. */
		// Handling of Referral message save is now moved to CommonOpDAO. The current line of code will be removed once that is tested and verified.
		//handleReferralMessages( policyVO ); 
		
		/*
		 *  Calling handle commission function if we are saving section for first time
		 */
		if(!hasCommissionChanged){
			LOGGER.debug( "Calling handleCommmission method to update commission for already saved section" );
			handleCommission( section,policyVO);
		}
		
		/* Handle the T_TRN_SECTION_DETAILS(_QUO) and T_TRN_SECTION_LOCATION(_QUO) tables. */
		handleSectionAndLocation( section,policyVO );
		
		 

		
		/* Handle additional covers update to Premium Table */
		handleAdditionalCovers(section, policyVO);
		
	
		
		updateOrRollBackPolicyRec(policyVO);
		
		
		/* Any section-level post-processing activities can be done now. */
		sectionPostProcessing( policyVO );
		
		//Handle Conditions 
		 // Adding the below method as part of CR#116356 by M1026668 in 3.8 
		if(section.getSectionId() == 1){
		handleConditionsWarrantyExclusion(section,policyVO);
		}

		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		clearThreadLevelContext();
		
		return output;
	}
	
	 // Adding the below method as part of CR#116356 by M1026668 in 3.8 
	private Map handleConditionsWarrantyExclusion(SectionVO section, PolicyVO policyVO) {
		
	
		Map results = null;
		PASStoredProcedure sp = null;
		sp = (PASStoredProcedure) ( policyVO.getIsQuote() ? Utils.getBean( "InsertCondProc_QUO" ) : Utils.getBean( "InsertCondProc_POL" ) );

		results = sp
				.call( policyVO.getPolLinkingId(),section.getSectionId(),(Utils.isEmpty(policyVO.getNewEndtId())?policyVO.getEndtId():policyVO.getNewEndtId()),policyVO.getPolicyTypeCode());
		
		System.out.println("Procedure out"+ results.toString() +"test");
		Long policyId = DAOUtils.getSectionPolicyId( policyVO, 1, getHibernateTemplate() );
		if(policyVO.getAppFlow() == Flow.AMEND_POL ){
			DAOUtils.updateAdditionalCoversExclusionWarranty(section, policyId, policyVO);
		}
		return results;
		}


	/**
	 * Empty section pre-processor method. This method should be overridden in section DAOs where there is a need for some pre-processing
	 * like construction of some VOs, setting flags, etc.
	 * 
	 * @param policyVO
	 */
	protected void sectionPreProcessing( PolicyVO policyVO ){
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		PremiumHelper.logPremiumInfo( "***********************************************************************");
		PremiumHelper.logPremiumInfo( "Processing  premium for "+ (policyVO.getIsQuote()?"quoteno ":"policyno ")+
				(policyVO.getIsQuote()?policyVO.getQuoteNo():policyVO.getPolicyNo()));
		PremiumHelper.logPremiumInfo("Processing Location ID: " + locationDetails.getRiskGroupId());
		PremiumHelper.logPremiumInfo("Processing Section: " + section.getSectionId());
	}

	/**
	 * Override this method to perform section level post-processing activities. The section save DAO that overrides this method
	 * must make a call to super.sectionPostProcessing() after all its processing.
	 * 
	 * @param policyVO
	 */
	protected void sectionPostProcessing( PolicyVO policyVO ){
//		updateOrRollBackPolicyRec(policyVO);
		//setLocationSaveFalse( policyVO );
		
		/*The below code is added to set RISK_GROUP_ID in ThreadLevelContext. Earlier , it was done in handleReferalMessages which was always called 
		 * during section save. Now, handle referral message is called after save section transaction. So it is required to set riskGroupId in
		 * ThreadLevelContext so that it can be used in SaveSectionRH */
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		com.rsaame.pas.cmn.context.ThreadLevelContext.set( com.Constant.CONST_RISK_GROUP_ID , locationDetails.getRiskGroupId());
		
		PremiumHelper.logPremiumInfo( "***********************************************************************");
	}
	
	
	
	

	
	private void updateOrRollBackPolicyRec(PolicyVO policyVO) {
		
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		
		if( (Boolean) ThreadLevelContext.get( SvcConstants.TLC_KEY_DATA_HAS_CHANGED ) ){
			if ( (!policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_REFERRED )) ) ) &&
					( !policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_SOFT_STOP ) ) ) ) && 
					(!policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_EXPIRED )) ) ) && 
					(!policyVO.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.CONV_TO_POL )) ) ) ){
				/* If the data has changed in any sections, set the status to Pending in the cases other than Referred, Expired and Converted to Policy. */
				policyVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
			}
			updatePolicy( policyVO, section );
			updateCustomerDetails(policyVO, section );
			getHibernateTemplate().flush();
			/* Update the previous Endt_Id records' Validity_Expiry_Date to current date. */
			updateValidityExpiryDates( policyVO,section.getSectionId() );
			getHibernateTemplate().flush();
			
			// Call to the stored procedure to update the non base section building table if base section building table changed  
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
			// Only if any location has got changes 
			if(!Utils.isEmpty( locationDetails )){
				/*
				 * Checks if the location data has changed only then call the proc to update the location information to 
				 * other depended tables 
				 */
				if(SvcUtils.hasLocDataChanged()){
					PASStoredProcedure sp =null;		
					if(policyVO.getAppFlow()==Flow.AMEND_POL){
						sp = (PASStoredProcedure) Utils.getBean("cascadeBaseLocChangesPol");
					} else {
						sp = (PASStoredProcedure) Utils.getBean("cascadeBaseLocChangesQuo");
					}			
					try
					{
					if(!Utils.isEmpty(policyVO.getPremiumVO()) && !Utils.isEmpty(policyVO.getPremiumVO().getPremiumAmt()))
					{
						Map results = sp.call( policyVO.getPolLinkingId(), getSectionId(), locationDetails.getRiskGroupId(), (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ),
								policyVO.getPremiumVO().getPremiumAmt(), policyVO.getAuthInfoVO().getCreatedBy(), section.getCommission() );
						if (Utils.isEmpty(results)) {
							LOGGER.debug("The result of the stored procedure is null");
						}
					}
					} catch (DataAccessException e){
						throw new BusinessException( "pas.dao.cmn.unknownError", e, "An exception occured while executing stored proc." );
					}
				}
			}
		}else{
			/*
			 * If no data is changed, then roll back the record inserted into t_trn_policy(quo).
			 * This is required because we are inserting data into t_trn_policy at the beginning of the transaction to avoid foreign key
			 * Violation with content tables 
			 */
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
		}
	}

	/*private void setLocationSaveFalse( PolicyVO policyVO ){
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		locationDetails.setToSave( false );
	}*/
	
	/**
	 * Handle's T_TRN_SECTION_LOCATION(_QUO) and T_TRN_SECTION_DETAILS(_QUO) table record creation(_version)/updation/deletion 
	 * @param section
	 * @param policyVO
	 */
	private void handleSectionAndLocation( SectionVO section, PolicyVO policyVO ){
		/* T_TRN_SECTION_DETAILS(_QUO) record creation is handled through INS_ROWS_CSH_POL_ENDT(INS_ROWS_CSH_POL) procedure itself
		 * hence commenting below line */
		//handleSectionDetails( section, policyVO );
		LOGGER.debug( "Calling handleSectionLocation method to check if location details have been changed." );
		handleSectionLocation( section, policyVO );

	}
	
	/**
	 * Check T_TRN_SECTION_DETAILS(_QUO) table record for commission change
	 * @param section
	 * @param policyVO
	 */
	private void handleCommission( SectionVO section, PolicyVO policyVO ){
		
		/* Check if commission has changed */
		LOGGER.debug( "Calling method to check if commission has changed for Section Id : " + section.getSectionId() + " policy Id :" + section.getPolicyId());
		if( !checkIfCommissionHasChanged( section, policyVO ) ) return;
		ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.TRUE );
		LOGGER.debug( " End of method to check if commission has changed for Section Id : "+section.getSectionId() );
	}
	/*
	 * Fetch existing section details records and check whether commission has been changed.
	 */
	private boolean checkIfCommissionHasChanged( SectionVO section, PolicyVO policyVO ){
		boolean commChanged = false;
	
		LOGGER.debug( "Fetch sectiona details from table for section id : " + section.getSectionId() + " endt Id :" + ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ));
				
		Long endtId =  policyVO.getEndtId();
		if(!Utils.isEmpty( policyVO.getNewEndtId() )){
			endtId = policyVO.getNewEndtId();
		}
		
		/*List<TTrnSectionDetailsQuo> detailsQuos = getHibernateTemplate().find("from TTrnSectionDetailsQuo sec where  sec.id.secPolicyId = ? and sec.id.secSecId = ? and sec.secEndtId <= ? and sec.secStatus <> 4  order by sec.secEndtId desc ",
	              section.getPolicyId(), section.getSectionId().shortValue(), endtId);
		*/
		
		 List<TTrnSectionDetailsQuo> detailsQuos = getHibernateTemplate().find(
	             "from TTrnSectionDetailsQuo sec where sec.id.secPolicyId = ? and sec.id.secSecId = ? and sec.secEndtId <= ? and  sec.secStatus <> 4 " +
	             "order by sec.secEndtId desc",
	             section.getPolicyId(), section.getSectionId().shortValue() , endtId);
		 
		if( !Utils.isEmpty( detailsQuos ) && ( detailsQuos.size() > 0 ) && !Utils.isEmpty( detailsQuos.get( 0 ) ) ){
			
			/*if(!Utils.isEmpty( detailsQuos.get( 0 ).getSecCommVal() )){
				LOGGER.debug( "Checking if old commission matches with new commission for policy " );
				if( !Utils.isEmpty( section ) && !Utils.isEmpty( section.getCommission() )
						&& detailsQuos.get( 0 ).getSecCommVal().compareTo( BigDecimal.valueOf( section.getCommission() )) != SvcConstants.zeroVal) commChanged =true;
				
			}
			
		}*/			
			if(!Utils.isEmpty( section ) && ((Utils.isEmpty(detailsQuos.get( 0 ).getSecCommVal())?BigDecimal.ZERO:detailsQuos.get( 0 ).getSecCommVal()).
					compareTo( BigDecimal.valueOf( Utils.isEmpty(section.getCommission())?SvcConstants.zeroVal:section.getCommission())) != SvcConstants.zeroVal)){
				commChanged =true;
			}			
		}		
		LOGGER.debug( " Commission changed :" +commChanged);		
		return commChanged;
	}


	/**
	 * Handle's T_TRN_SECTION_LOCATION(_QUO) table record creation(_version)/updation/deletion
	 * @param section
	 * @param policyVO
	 */
	private void handleSectionLocation( SectionVO section, PolicyVO policyVO ){
		
		/* If Location Entry already exists do nothing */
		if( checkIfLocEntryExistsInSecLocation( section, policyVO ) ) return;
		getHibernateTemplate().evict( TTrnSectionLocationQuo.class );
		TTrnSectionLocationQuo sectionLoc = DAOUtils.setSecLocDetails( section, policyVO );
		saveOrUpdate( sectionLoc );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.TRUE );
		
	}

	/**
	 * Handle's T_TRN_SECTION_DETAILS(_QUO) table record creation(_version)/updation/deletion
	 * @param section
	 * @param policyVO
	 *//*
	private void handleSectionDetails( SectionVO section, PolicyVO policyVO ){

		  Retrieve the currently valid record. 
		 List<TTrnSectionDetailsQuo> detailsQuos = getHibernateTemplate().find(
		             "from TTrnSectionDetailsQuo sec where sec.secValidityExpiryDate= ? and sec.id.secPolicyId = ? and sec.id.secSecId = ? and sec.secStatus <> 4",
		             SvcConstants.EXP_DATE, section.getPolicyId(), section.getSectionId().shortValue() );

		 if( !Utils.isEmpty( detailsQuos ) && ( detailsQuos.size() > 0 ) && !Utils.isEmpty( detailsQuos.get( 0 ) ) ){
		        If the Endt_Id available in TLC is different from the one in the existing record, then a modification has happened to the policy. 
		       if( !Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) )
		                   && !detailsQuos.get( 0 ).getSecEndtId().equals( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ) ){
		             sectionDetails = CopyUtils.copySerializableObject( detailsQuos.get( 0 ) );
		             sectionDetails.setSecCommVal( Utils.isEmpty( section.getCommission() ) ? null : BigDecimal.valueOf( section.getCommission() ) );

		             sectionDetails.getId().setSecValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		             sectionDetails.setSecEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		       }
		       else{
		              No modification in the policy. Just set the new commission value. 
		             sectionDetails = detailsQuos.get( 0 );
		             sectionDetails.setSecCommVal( Utils.isEmpty( section.getCommission() ) ? null : BigDecimal.valueOf( section.getCommission() ) );
		       }
		 }
		 else{
		        There is no existing record for the section. Create it. 
		       sectionDetails = DAOUtils.getSection( section, policyVO );
		       sectionDetails.setSecEndtId( policyVO.getNewEndtId() );
		 }
		TTrnSectionDetailsQuo mappedSecDetsRecord = DAOUtils.getSection( section, policyVO );
		TTrnSectionDetailsQuo existingSecDetsRecord = checkIfSecEntryExistsInSecDets( section, policyVO );

		TTrnSectionDetailsQuo recordToPersist = null;
		 Entry exists then check if commission value has changed 
		if( !Utils.isEmpty( existingSecDetsRecord ) ){
			 Figure out if the values have changed. If they haven't, then we will not take any action on this table. 
			boolean hasDataChanged = SvcUtils.hasDataChanged( existingSecDetsRecord, mappedSecDetsRecord );
			if( hasDataChanged ){
				ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.TRUE );
				POJOId id = constructSecDetsPOJOId( existingSecDetsRecord );
				mappedSecDetsRecord.setId( (TTrnSectionDetailsQuoId) id );
				recordToPersist = mappedSecDetsRecord;

			}
			else{ Nothing has changed hence update existing record itself 
				recordToPersist = existingSecDetsRecord;
			}
		}
		else{ Record doesn't exist create a new record 
			recordToPersist = mappedSecDetsRecord;
		}
		getHibernateTemplate().evict( existingSecDetsRecord );  Remove existing record from hibernate session in order to avoid
		hibernate session id with same identifier already exists error 
		saveOrUpdate( recordToPersist );
	}
*/
	/**
	 * Checks if there are referral messages to be processed. If there are, then the existing records for the section-location
	 * are cleared and the referral data available in <code>policyVO</code> are inserted.
	 * 
	 * @param policyVO
	 *//*
	private void handleReferralMessages( PolicyVO policyVO ){
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		
		 To delete the existing records from temp referral table before actually checking if the referral is
		 * triggered for this insert/update operation which is done by checking is policyVO.getMapReferralVO is not
		 * null  
		DAOUtils.deleteReferralRecordsForKey( policyVO.getPolLinkingId(), Long.valueOf( locationDetails.getRiskGroupId() ) , getSectionId() , getHibernateTemplate()  );
		
		
		 * The risk group id is required in RefferalRH for identifying the location for which the referral was invoked
		 * since, after save operation the location processing id is cleared, setting the risk ID in ThreadLevelContext
		 * which is used in ReferralRH to identify the location for which referral was invoked 
		 
		
		com.rsaame.pas.cmn.context.ThreadLevelContext.set( com.Constant.CONST_RISK_GROUP_ID , locationDetails.getRiskGroupId());
		
		if( !Utils.isEmpty( policyVO.getMapReferralVO() ) ){
			for( Entry<ReferralLocKey, ReferralVO> mapRefEntry : policyVO.getMapReferralVO().entrySet() ){
				if(Integer.valueOf( getSectionId() ).equals( mapRefEntry.getKey().getSectionId() ) ){
					ReferralVO locreferralVO = mapRefEntry.getValue();
					locreferralVO.setPolLinkingId( policyVO.getPolLinkingId() );
					locreferralVO.setRiskGroupId( locationDetails.getRiskGroupId() );
					if( !Utils.isEmpty( locreferralVO ) ){
						TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean( "tempPasReferralDAO" );
						insertTempPasReferalDao.insertReferal( locreferralVO );
					}
				}
			}
		}
	}
*/
	/**
	 * Creates the T_TRN_POLICY(_QUO) and T_MAS_CASH_CUSTOMER(_QUO) records for this section.
	 * 
	 * @param policyVO
	 * @param section The section for which the T_TRN_POLICY(_QUO) and T_MAS_CASH_CUSTOMER(_QUO) records have to be created
	 */
	private void createPolicyRecord( PolicyVO policyVO, SectionVO section ){
		Long policyId = DAOUtils.createPolicyRecord( policyVO, getClassCode(), section.getSectionId());
		section.setPolicyId( policyId );
	}

	/**
	 * Returns the sectionId for the section this BaseSectionSaveDAO implementation handles.
	 * @return
	 */
	protected abstract int getSectionId();
	
	/**
	 * Returns the sectionId for the section this BaseSectionSaveDAO implementation handles.
	 * @return
	 */
	protected abstract int getClassCode();
	
	/**
	 * The core method that processes the save activities for this section. This method knows all the tables that are to be handled
	 * for this specific section. This is the method that will be called from the service.
	 * 
	 * @param input An instance of <code>PolicyVO</code>
	 * @return A processed instance of <code>PolicyVO</code> that has the updated keys
	 */
	protected abstract BaseVO saveSection( BaseVO input );
	protected abstract POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO );
	protected abstract boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO );
	protected abstract boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO );
	protected abstract void updateKeyValuesToVOs( String tableId,POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase );
	protected abstract void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO );
	protected abstract void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO );
	
	/**
	 * Constructs and returns a POJOId instance that represents the primary key for the table in which a new "business" record has to be CREATEd.
	 * The implementations of this method in the concrete classes should handle all the tables that the DAO is concerned with. The implementation
	 * methods should differentiate the tables based on the POJOId class being passed.
	 * 
	 * @param tableId The table Id that represents this table
	 * @param policyVO The <code>PolicyVO</code> instance
	 * @param mappedRecord 
	 * @return
	 */
	protected abstract POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord );

	/**
	 * Constructs and returns a POJOId instance that represents the primary key for the table in which a new "business" record has to be CREATEd.
	 * The implementations of this method in the concrete classes should handle all the tables that the DAO is concerned with. The implementation
	 * methods should differentiate the tables based on the POJOId class being passed.
	 * 
	 * @param <T> Any child of POJOId class
	 * @param tableId The table Id that represents this table
	 * @param policyVO The <code>PolicyVO</code> instance
	 * @param existingId The POJOId instance for the existing record
	 * @return
	 */
	protected abstract <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId );

	/**
	 * This method handles, in a generic manner, the saving of a record into a table specified by the <code>tableId</code>. This is expected to
	 * be called from the handleXXX() methods that are called within the <code>saveSection()</code> implementation.
	 * 
	 * @param tableId The <code>tableId</code> that can be used by <code>DAOUtils</code> to construct queries from the configuration for the tableId
	 * @param policyVO The <code>PolicyVO</code> instance representing the policy/quote
	 * @param depsPOJO Other dependencies that may be needed to handle the saving, like parent table record or other input that are not present
	 * in the <code>PolicyVO</code> instance.
	 * @param depsVO Other VO dependencies
	 * @param sql <i>Table snapshot query parameter</i>: Flag to indicate if the configuration is for SQL or HBM querying
	 * @param tableKeys <i>Table snapshot query parameter</i>: Values for the query parameters
	 * 
	 * @return The processed record, or the mapped record if no processing was done
	 */
	protected <T extends POJO > T handleTableRecord( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO, boolean sql, Object... tableKeys ){
		
		/* Construct the main Pojo instance with the other details. */
		/*
		 * KR: Mapping no only depends on the pojos but combination of pojo and vo in some cases
		 * eg: in premium tabe the PremiumVO is mapped from section directly, hence added BaseVO[] depsVO to mapVOToPOJO()
		 */
		POJO mappedRecord = mapVOToPOJO( tableId, policyVO, depsPOJO, depsVO );
		
		/* First, decide if the building is being deleted, created or changed. */
		/*
		 * KR: To determine where record is to be created or not dependent pojo from parent table or depended vo is required
		 * eg: in content when looping through each content,PropertyRiskDetails is required to determine weather its a creation,
		 * hence added POJO[] depsPOJO, BaseVO[] depsVO to isToBeCreated()
		 */
		boolean isToBeCreated = isToBeCreated( tableId, policyVO, depsPOJO, depsVO );
		boolean isToBeDeleted = isToBeCreated ? false : isToBeDeleted( tableId, policyVO, depsPOJO, depsVO);
		
		/* Get the current valid-state record, if key is available. That is, the record with VED as 31-Dec-2049. */
		POJO existingRecord = null;
		if( !isToBeCreated ){
			existingRecord = DAOUtils.getExistingValidStateRecord( tableId, policyVO.getAppFlow(), getHibernateTemplate(), sql, null, tableKeys );
		}
		
		/* We need to calculate the pro-rated premium and set it in the PolicyVO and TTrnPremium record. This has to be done at this
		 * stage because even if we are not going to process the TTrnPremiumQuo record, we will have to update the PolicyVO because
		 * the rating engine call would have updated the premium values with annualized premium. */
		
		/*
		 * Bugzilla 4199 : Making prorated premium calculation before determining save case, since it was inserting duplicate premium records for nil endt in leap year
		 */
		updatePremiumRec( tableId, mappedRecord, policyVO, tableKeys );
		
		/* Now, we have to figure out the case of the save, that is, whether it is a fresh risk (CREATE), the risk needs to be removed from
		 * the policy/quote (DELETE) or the risk details are being changed for an already pending endorsement/version (CHANGE_WITH_EXISTING_REC) 
		 * or for an active policy/quote with no pending endorsement/version (CHANGE_WITH_NEW_REC). */ 
		SaveCase saveCase = getSaveCase( isToBeCreated, isToBeDeleted, 
										 Utils.isEmpty( existingRecord ) ? CommonConstants.DEFAULT_LOW_INTEGER : existingRecord.getStatus(), 
										 existingRecord, mappedRecord );
		
		
		/* If no action needs to be taken, usually when nothing has changed, return the existing record for use in other risk tables. */
		if( Utils.isEmpty( saveCase ) ){
			return (T) existingRecord;
		}
		
		updatePrmSumInsured( tableId, mappedRecord, policyVO, saveCase, tableKeys );
		
		POJO recordToPersist = null;
		
		/* Only if some action has to be taken, we will continue. Else, return. */
		if( !Utils.isEmpty( saveCase ) ){
			/* In cases of DELETE and CHANGE, there must be an existing record. */
			validateExistingRecordAvailability( existingRecord, saveCase );
			
			/* Construct the Id. */
			POJOId id = constructId( tableId, policyVO, Utils.isEmpty( existingRecord ) ? null : existingRecord.getPOJOId(),mappedRecord, saveCase );
			
			/*
			 * If it is a case of delete risk endorsement except for TTrnPremiumQuo POJO we have to us the existing pojo, update the keys and
			 * persist in DB, else use the mapped pojo
			 */
			recordToPersist = ( saveCase == SaveCase.DELETE && !( mappedRecord instanceof TTrnPremiumQuo ) ) ? existingRecord : mappedRecord;
			
			

			// The below code added to fix the defect-ID:137748, TestCase:22 -START
			if( !Utils.isEmpty( recordToPersist ) && ( recordToPersist instanceof TTrnGaccBuildingQuo ) ) {
				if( !Utils.isEmpty( recordToPersist.getPOJOId() ) ) { 
					//recordToPersist.setPOJOId(null);
					getHibernateTemplate().evict(recordToPersist);
				}				
			}
			// The below code added to fix the defect-ID:137748, TestCase:22 -END
			
			
			
			// The below code added to fix the defect-ID:144086, TestCase:04 -START
			boolean updateUwQuestion = false;
			if( !Utils.isEmpty( recordToPersist ) && ( recordToPersist instanceof TTrnUwQuestionsQuo )) {
				POJO deletedUwQuestionPojo = (POJO)ThreadLevelContext.get(SvcConstants.DELETED_UW_QUESTION);
							
				if(!Utils.isEmpty( deletedUwQuestionPojo )) {
					TTrnUwQuestionsQuo deletedUwQuestion = (TTrnUwQuestionsQuo) deletedUwQuestionPojo;
								
					String uqtVED = new SimpleDateFormat("dd-MMM-yy").format(deletedUwQuestion.getUqtValidityExpiryDate());
								
					if(!Utils.isEmpty(deletedUwQuestion.getId()) && deletedUwQuestion.getId().equals(recordToPersist.getPOJOId()) && 
						deletedUwQuestion.getStatus() == 4 && getSectionId() == 3 && uqtVED.equals(SvcConstants.DFAULT_VED)) {
									
						updateUwQuestion = true;
									
					}
				}
			}
			// The below code added to fix the defect-ID:144086, TestCase:04 -END
						
			
			
			
			/* Set the Id. */
			recordToPersist.setPOJOId( id );
			
			/* Set other important details like Endt_Id, Endt_No and VSD. */
			setVersionAndStatusDetails( recordToPersist, policyVO, saveCase );
			
			/* Persist the record. */
			tableRecPreSaveProcessing( tableId, recordToPersist, policyVO, saveCase, tableKeys );
			
			/*
			 * Update policy is called here as the risk tables (ttrbbuilding, ttrncontent etc) have foreign key relationship with ttrnpolicy
			 * table hence in case of change insert into ttrnpolicy.
			 * this method is called again to update the premium. when called for the second time it should always be an update
			 * This update is required only once for a session, hence POLICY_TABLE_UPDATED is set into thread context and is used to avoid multiple 
			 * updates of the same record
			 */
			/*if(((Boolean) ThreadLevelContext.get( SvcConstants.TLC_KEY_DATA_HAS_CHANGED )) &&
					Utils.isEmpty( ThreadLevelContext.get( "POLICY_TABLE_UPDATED") ))
			{
				SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
				updatePolicy( policyVO, section );
				ThreadLevelContext.set( "POLICY_TABLE_UPDATED",Boolean.TRUE);
			}*/
				
			
			if( saveCase == SaveCase.DELETE_PENDING_REC ){
				
				/*
				 * Before deleting the valid record, activating the previous expired record based on the endorsement id
				 */
				//Long policyId = PolicyUtils.getSectionVO( policyVO, getSectionId() ).getPolicyId();
				//Long prevEndId = DAOUtils.getPrevEndtId( getHibernateTemplate(), policyVO.getIsQuote(), policyId, policyVO.getNewEndtId() );				
				
				/* Commented this code to fix the defect number 355 - MB CR | Edit quote | Deleted machine type is getting displayed again when we do the transactions search */
				//activatePrevRecord(tableId, policyId,prevEndId, policyVO.getAppFlow(), tableKeys);
				delete( existingRecord );
			}
			else{
				
				/*
				 * To cascade the base location info to other risks building table
				 * the pojo of the base section building details are set into thread
				 * level context
				 */
				setBaseSectionTo(recordToPersist,existingRecord);
				
				
				getHibernateTemplate().evict( existingRecord );
				if( !Utils.isEmpty( existingRecord ) && !Utils.isEmpty( existingRecord.getPreparedDate() ) ){
					recordToPersist.setPreparedDate( existingRecord.getPreparedDate() );
				}
				
				// The below code added to fix the defect-ID:144086, TestCase:04 -START
				if(updateUwQuestion) {
					update( recordToPersist );
					updateUwQuestion = false;
				} else { // The below code added to fix the defect-ID:144086, TestCase:04 -END
					saveOrUpdate( recordToPersist );
				}
			}
			
			/* Update the key values into the application VOs. */
			updateKeyValuesToVOs( tableId,recordToPersist, policyVO,depsVO, saveCase);

			/* Call post-save processing to conduct any table specific activities. */
			tableRecPostSaveProcessing( tableId, recordToPersist, policyVO );
		}
		
		/* Call post-save processing to conduct any section specific activities that need to be done even there was
		 * no change to the table. */
		tableRecPostProcessing( tableId, recordToPersist, policyVO );
		
		return (T) recordToPersist;
	}
	
	private void updatePrmSumInsured( String tableId, POJO mappedRecord, PolicyVO policyVO, SaveCase saveCase, Object[] tableKeys ){
		
		if( Utils.isEmpty( mappedRecord ) || !( mappedRecord instanceof TTrnPremiumQuo ) ){
			return ;
		}
		
		if( mappedRecord instanceof TTrnPremiumQuo
				//	&& policyVO.getAppFlow() == Flow.AMEND_POL
			&& ( Utils.isEmpty( saveCase ) || saveCase == SaveCase.CREATE || saveCase == SaveCase.DELETE || saveCase == SaveCase.CHANGE_WITH_NEW_REC || saveCase == SaveCase.CHANGE_WITH_EXISTING_REC ) ){
				/* (a) Get the previous risk premium and calculate the 
				 * (b) pro-rated premium based on the previous premium */
				/* (a) */
			TTrnPremiumQuo previousPrm = PremiumHelper.getLastAnnualizedPrm( (Short) tableKeys[ 3 ],(Long) tableKeys[ 0 ], ( (BigDecimal) tableKeys[ 1 ] ).longValue(),
						( (BigDecimal) tableKeys[ 2 ] ).longValue(), getHibernateTemplate().getSessionFactory().getCurrentSession(), policyVO );

				
		
		if( mappedRecord instanceof TTrnPremiumQuo && saveCase == SaveCase.DELETE ){
			//proratedPremium = 0.0;
			//PremiumHelper.logPremiumInfo("Case of delete premium: setting premium to 0.0" );
			// Set the calculated prorated premium back to the TTrnPremium record and PolicyVO.
			/*
			 * In case of delete, the sum insured should be the previous SI
			 */
			( (TTrnPremiumQuo) mappedRecord ).setPrmSumInsured( SvcUtils.getNonNullPrmSI( previousPrm.getPrmSumInsured() ) );
		   }
		
		}
		/*
		 * In case of endorsement create a new record for the risk code 100 101,
		 * TO identify that, SvcConstants.HAS_PREMIUM_CHNAGED will be used as an 
		 * indicator 
		 */
		if(!Utils.isEmpty( saveCase )){
			ThreadLevelContext.set( SvcConstants.HAS_PREMIUM_CHNAGED, Boolean.TRUE );
		}
	}


	/**
	 * Any processing that needs to be done after all mappings have been done and save case decisions have been made and just before
	 * saving the record, can be done here. When this method is overridden, first call <code>super.tableRecPreSaveProcessing()</code> 
	 * and then implement the section-specific logic.
	 * 
	 * @param tableId
	 * @param recordToPersist
	 * @param policyVO
	 * @param saveCase
	 * @param tableKeys
	 */
	protected void tableRecPreSaveProcessing( String tableId, POJO recordToPersist, PolicyVO policyVO, SaveCase saveCase, Object[] tableKeys ){
		/*
		 * For premium updates, in amend flow, pro-rated premium needs to be calculates 
		 * The premium from rating engine needs to be updated and it is common for all the tables
		 */
		//updateProratedPremium( tableId, recordToPersist, policyVO, saveCase, tableKeys );
	}

	private boolean updatePremiumRec( String tableId, POJO mappedRecord, PolicyVO policyVO, Object[] tableKeys ){
		if(! (Utils.isEmpty( mappedRecord ) || !( mappedRecord instanceof TTrnPremiumQuo )) ){  //SONARFIX -- negated the if block accordingly to have only one return stmt
			//return true;
		//}
		//else{
			updateCommonPrmFields( policyVO, ( (TTrnPremiumQuo) mappedRecord ) );
		//}

		double proratedPremium = 0.0;

		PremiumHelper.logPremiumInfo( "Premium Value from Rating engine: " + ( (TTrnPremiumQuo) mappedRecord ).getPrmPremiumActual() );
		PremiumHelper.logPremiumInfo( "Setting premium for " + "Policy Id : " + ( (TTrnPremiumQuo) mappedRecord ).getId().getPrmPolicyId() + " " );
		PremiumHelper.logPremiumInfo( "Basic Risk Id: " + ( (TTrnPremiumQuo) mappedRecord ).getId().getPrmBasicRskId() + " " );
		PremiumHelper.logPremiumInfo( "Risk Id: " + ( (TTrnPremiumQuo) mappedRecord ).getId().getPrmRskId() + " " );
		PremiumHelper.logPremiumInfo( "Endt Id: " + ( (TTrnPremiumQuo) mappedRecord ).getPrmEndtId() + " " );

		if( mappedRecord instanceof TTrnPremiumQuo
			//	&& policyVO.getAppFlow() == Flow.AMEND_POL
			//	&& ( Utils.isEmpty( saveCase ) || saveCase == SaveCase.CREATE || saveCase == SaveCase.DELETE || saveCase == SaveCase.CHANGE_WITH_NEW_REC || saveCase == SaveCase.CHANGE_WITH_EXISTING_REC	) 
				){
			/* (a) Get the previous risk premium and calculate the 
			 * (b) pro-rated premium based on the previous premium */
			/* (a) */
			TTrnPremiumQuo previousPrm = PremiumHelper.getLastAnnualizedPrm( (Short) tableKeys[ 3 ],(Long) tableKeys[ 0 ], ( (BigDecimal) tableKeys[ 1 ] ).longValue(),
					( (BigDecimal) tableKeys[ 2 ] ).longValue(), getHibernateTemplate().getSessionFactory().getCurrentSession(), policyVO );

			if( !Utils.isEmpty( previousPrm ) ){
				PremiumHelper.logPremiumInfo( "Previous cover level premium: " + previousPrm.getPrmPremium() + " " );

				/*
				 * Update the current premium records history column with the previous records premium and SI
				 */

				( (TTrnPremiumQuo) mappedRecord ).setPrmOldPremium( SvcUtils.getNonNullPrmSI( previousPrm.getPrmPremium() ) );
				( (TTrnPremiumQuo) mappedRecord ).setPrmOldSumInsured( SvcUtils.getNonNullPrmSI( previousPrm.getPrmSumInsured() ) );
				
				ThreadLevelContext.set( "PRM_PREPARED_DATE", previousPrm.getPreparedDate() );

			}
			else{
				PremiumHelper.logPremiumInfo( "Previous cover level premium is null - first endorsment" );
			}

			if( policyVO.getAppFlow() == Flow.AMEND_POL )
			{
				/* (b) */
				proratedPremium = PremiumHelper.calculateProratedPremium( (TTrnPremiumQuo) mappedRecord, policyVO, previousPrm, getSectionId() );

				PremiumHelper.logPremiumInfo( "Prorated premium from util: " + proratedPremium );

				/* Set the calculated prorated premium back to the TTrnPremium record and PolicyVO. */
				( (TTrnPremiumQuo) mappedRecord ).setPrmPremium( BigDecimal.valueOf( proratedPremium ) );

				// Update the premium effective date. In case of endorsement the effective date will be the endorsement effective date
				( (TTrnPremiumQuo) mappedRecord ).setPrmEffectiveDate( policyVO.getEndEffectiveDate() );

				( (TTrnPremiumQuo) mappedRecord ).setPrmPreparedDt( (Date) ThreadLevelContext.get( "SYSDATE" ) );
				// In case of Delete (a) Set the premium amount to 0.
				/*
				 * Unlike mississippi, in case of delete the pro - rated premium should be saved in prm_premium
				 */
				
				/***
				 * Moving the below part of code to updatePrmSumInsured(). 
				 */
				//if( mappedRecord instanceof TTrnPremiumQuo && saveCase == SaveCase.DELETE ){
					//proratedPremium = 0.0;
					//PremiumHelper.logPremiumInfo("Case of delete premium: setting premium to 0.0" );
					// Set the calculated prorated premium back to the TTrnPremium record and PolicyVO.
					/*
					 * In case of delete, the sum insured should be the previous SI
					 */
				/*	( (TTrnPremiumQuo) mappedRecord ).setPrmSumInsured( SvcUtils.getNonNullPrmSI( previousPrm.getPrmSumInsured() ) );
				   }*/
				
			}
			else{

				/*proratedPremium = PremiumHelper.getQuotePremium( ( (TTrnPremiumQuo) mappedRecord ).getPrmPremiumActual(), policyVO.getScheme().getEffDate(),
						policyVO.getPolExpiryDate() ).doubleValue();
				*/
				//Bugzilla 4182 : UAT feedback - For leap year, premium to be calculated for 365days only  
				proratedPremium = PremiumHelper.getQuotePremium( ( (TTrnPremiumQuo) mappedRecord ).getPrmPremiumActual(), policyVO.getScheme().getEffDate(),
                        policyVO.getPolExpiryDate(),policyVO.getPolicyTerm() ).doubleValue();

				
				( (TTrnPremiumQuo) mappedRecord ).setPrmPremium( BigDecimal.valueOf( proratedPremium ) );
			}
			
			/*
			 * moving below part of code to updatePrmSumInsured() 
			 */
			/*if(!Utils.isEmpty( saveCase )){
				ThreadLevelContext.set( SvcConstants.HAS_PREMIUM_CHNAGED, Boolean.TRUE );
			}*/
			
		}
		
		} //SONARFIX -- end if block
		
		return true;
	} 

	/*
	 * Fields in premium table that are independent of the re
	 */
	private void updateCommonPrmFields( PolicyVO policyVO, TTrnPremiumQuo mappedRec ){
		
		mappedRec.setPrmClCode( Integer.valueOf( getClassCode() ).shortValue() );
		setRateTypeToPremiumPOJO( policyVO, mappedRec);
		SvcUtils.updateCommonPrmFields(  policyVO,  mappedRec );
		
	}

	private void updatePolicy( PolicyVO policyVO, SectionVO section ){
		/*
		 * If the endtId or EndNo is null that means there is no change in the any of the building or 
		 * content table, here there is no need to update the ttrn policy table. The reason for this assumption is that a new 
		 * Endt_Id is "attempted" to be generated only if we figure out that there is a change to a risk detail.
		 */
		if( Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ) || 
			Utils.isEmpty( ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ) ) )
		{
			return;
		}
			
		/* Get the current Endt_Id-state record, if key is available. That is, the record with the Endt_Id got from the database function. */
		TTrnPolicyQuo existingRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), getHibernateTemplate(), 
																			 false, (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), 
																			 policyVO.getPolLinkingId(), (short)getClassCode() );
		
		
		/* There is no record for the current (new) Endt_Id. This means that we will have to create a new record from the previous
		 * Endt_Id's record. */
		Long prevEndtId = DAOUtils.getPrevEndtId( getHibernateTemplate(), policyVO.getIsQuote(), section.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
			
		TTrnPolicyQuo oldRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY, policyVO.getAppFlow(), getHibernateTemplate(), 
					 												false, prevEndtId, policyVO.getPolLinkingId(), (short) getClassCode() );
			
		if( !Utils.isEmpty( oldRecord ) ){
				
			if( Utils.isEmpty( existingRecord ) ){
				existingRecord = oldRecord;
			}
			
			/*In case of endorsement, update the previous record endorsement expiry date to endorsement effective 
			 date */
			if( !Utils.isEmpty( policyVO.getEndEffectiveDate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL  ) ){
				oldRecord.setPolEndtExpiryDate( policyVO.getEndEffectiveDate() );
				/*if( !Utils.isEmpty(policyVO.getGeneralInfo())&& !Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()) &&
						!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc())){
					oldRecord.setPolProcLocCode(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc() );
				}*/
				
				update( oldRecord );
			}
				
		}
			
		TTrnPolicyQuo newRecord = CopyUtils.copySerializableObject( existingRecord );
		
		newRecord.getId().setPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		newRecord.setPolEndtNo( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ) );
		newRecord.setPolValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		newRecord.setPolValidityExpiryDate( SvcConstants.EXP_DATE );
		if( !Utils.isEmpty( oldRecord ) && !Utils.isEmpty( oldRecord.getPolPreparedDt() ) ){
			newRecord.setPolPreparedDt( oldRecord.getPolPreparedDt() );
		}
		
		/* Setting modified date so that the same can be used for transaction date & time during transaction search. */
		newRecord.setPolModifiedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE) );
		newRecord.setPolModifiedBy( SvcUtils.getUserId( policyVO) );
		
		if( !Utils.isEmpty(policyVO.getGeneralInfo())&& !Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()) &&
				!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc())){
			newRecord.setPolProcLocCode(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc() );
		}
		
		/*
		 * In case of endorsement, the endt expire date needs to be populated.
		 * First check if the new record is not for end no 0 i,e endID is not 0
		 * then update the endorsement expiry date to policy exp date
		 */
		if(!String.valueOf(newRecord.getId().getPolEndtId()).equalsIgnoreCase(SvcConstants.FIRST_ENDT) && policyVO.getAppFlow().equals( Flow.AMEND_POL  ) ){
			newRecord.setPolEndtExpiryDate(policyVO.getPolExpiryDate());
		}
		
		/*
		 * update the endt effective date, as the proc update cashpolicy will just copy the previous record and 
		 * the PolEndtEffectiveDate is not set.
		 */
		if(!Utils.isEmpty( policyVO.getEndEffectiveDate() ) && policyVO.getAppFlow().equals( Flow.AMEND_POL  ) ){
			newRecord.setPolEndtEffectiveDate( policyVO.getEndEffectiveDate() );
		}
		else 
		{
			newRecord.setPolEndtEffectiveDate(null);
			/*
			 * Set the start date and end date of the quotation.
			 *Start date should be the date the user selects. Hence update the copied 
			 *policy record
			 */
			SvcUtils.setStartEndDate( newRecord, policyVO.getScheme().getEffDate() );
			
		}
		
		/*
		 * Below line to set policy status as pending is commented, because it is resetting the status of the policy to pending, irrespective of the current status.
		 * Below line purpose to set the policy status to pending while creating the policy is achieved in procedure used to create policy.
		 */
//		newRecord.setPolStatus( SvcConstants.POL_STATUS_PENDING.byteValue() ); /* TODO Need to set this to 4 in case there is no section for this policyId. */
		if(!Utils.isEmpty( section.getCommission() ))
		{
			newRecord.setPolCommisionId( BigDecimal.valueOf( section.getCommission() ) );
		}
		getHibernateTemplate().evict( existingRecord );
		saveOrUpdate( newRecord );
		
		/*
		 * The method updates the special covers in t_trn_premium for the basic section
		 */
		updatePremiumSpecialCover( policyVO,  section , newRecord);
	}
	
	
	/*
	 * Updates the cash customer table with the auth details , other customer info can be updated here
	 */
	private void updateCustomerDetails( PolicyVO policyVO, SectionVO section ){

		//DAOUtils.updateCustomerDetails(policyVO, section, getHibernateTemplate());
		DAOUtils.updateCustomerAuthDetails( policyVO, section, getHibernateTemplate() );

	}
	
	

	private void updatePremiumSpecialCover( PolicyVO policyVO, SectionVO section, TTrnPolicyQuo newRecord ){

		/*
		 * The special cover record needs to be created only if the premium has changed, hence check HAS_PREMIUM_CHNAGED value to see if the premium has changed 
		 * in all flows not only AMEND_POL flow
		 */
		if( !Utils.isEmpty( ThreadLevelContext.get( SvcConstants.HAS_PREMIUM_CHNAGED ) ) 
				&& ( (Boolean) ThreadLevelContext.get( SvcConstants.HAS_PREMIUM_CHNAGED ) ).equals( Boolean.FALSE ) ) return;
		

		if( !Utils.isEmpty( newRecord ) ){

			String[] coverCodes = Utils.getMultiValueAppConfig( "SPECIAL_COVER_CODES", "," );

			/*
			 * Premium record has to be added to Premium table for each of the
			 * special cover codes.
			 */
			for( String coverCode : coverCodes ){

				/*
				 * For each special cover up date the id's of the basic cover
				 */
				TTrnPremiumQuo premiumQuo = SvcUtils.getPremiumQuoSpecialCovers( coverCode, newRecord );

				premiumQuo.getId().setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
				premiumQuo.setPrmEndtId( newRecord.getId().getPolEndtId() );
				premiumQuo.getId().setPrmPolicyId( newRecord.getId().getPolPolicyId() );
				premiumQuo.setPrmStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );

				List<TTrnPolicyQuo> existingPolRecs = getHibernateTemplate().find( "from TTrnPolicyQuo pol where pol.polLinkingId = ?", newRecord.getPolLinkingId() );

				if( !Utils.isEmpty( existingPolRecs ) && existingPolRecs.size() > 0 && !Utils.isEmpty( existingPolRecs.get( 0 ) ) ){
					for( TTrnPolicyQuo existingPolRec : existingPolRecs ){
						/*
						 * if there is an existing record for an endt then no need to insert new record
						 * 
						 * The special covers 101, 100 and 51 should be updated/ created at class code level
						 */
						List<TTrnPremiumQuo> existingRec = getHibernateTemplate().find(
								"from TTrnPremiumQuo prm where prm.prmValidityExpiryDate= ? and prm.id.prmPolicyId = ? "
										+ "and prm.id.prmBasicRskId = ? and prm.id.prmRskCode = ? and prm.id.prmCovCode = ? "
										+ "and prm.id.prmCtCode = ? and prm.id.prmCstCode = ? and prm.prmEndtId = ? and prm.prmClCode = ? ", SvcConstants.EXP_DATE,
								existingPolRec.getId().getPolPolicyId(), premiumQuo.getId().getPrmBasicRskId(), premiumQuo.getId().getPrmRskCode(),
								premiumQuo.getId().getPrmCovCode(), premiumQuo.getId().getPrmCtCode(), premiumQuo.getId().getPrmCstCode(), premiumQuo.getPrmEndtId(), newRecord.getPolClassCode() );

						if( !Utils.isEmpty( existingRec ) && existingRec.size() > 0 && !Utils.isEmpty( existingRec.get( ZERO_VAL ) ) ){
							return;
						}
					}
				}

				/*
				 * Fix for bug 1003: Set prepared date for special covers
				 */
				TTrnPremiumQuo previousPrm = PremiumHelper.getLastAnnualizedPrm( premiumQuo.getId().getPrmCovCode(), premiumQuo.getId().getPrmPolicyId(), ( premiumQuo.getId().getPrmBasicRskId() ).longValue(),
						( premiumQuo.getId().getPrmRskId() ).longValue(), getHibernateTemplate().getSessionFactory().getCurrentSession(), policyVO );
				
				if(!Utils.isEmpty( previousPrm ) && !Utils.isEmpty( previousPrm.getPrmPreparedDt() )){
					premiumQuo.setPrmPreparedDt( previousPrm.getPrmPreparedDt() );
				}
				/*
				 * if cover code is 100 then Fn code should be stored as 2 else
				 * it has to set as 0.
				 */
				/*if( covCode == SvcConstants.SC_PRM_COVER_GOVT_TAX ){
					premiumQuo.setPrmFnCode( SvcConstants.SC_PRM_FN_CODE_FLAT_AMT.byteValue() );
				}
				else{
					premiumQuo.setPrmFnCode( SvcConstants.SC_PRM_FN_CODE.byteValue() );
				}*/
				// FIX as part of back end verification
				premiumQuo.setPrmFnCode( SvcConstants.SC_PRM_FN_CODE_FLAT_AMT.byteValue() );
				
				/*
				 * in case of endorsement only cover code 100 and 101 needs to be inserted, cover code 51 will not change in any no of endorsement  
				 */
				// To check for vatEnabled for SBS and restrict 151 cover inserting into DB based on flag check :: Ticket 154656
				Boolean coverExists = checkSpecialCoverExistsForSBS(coverCode);
				if(coverExists.equals(Boolean.TRUE)){
					delete(premiumQuo);
					//System.out.println("Deleting 151 for SBS : Dileep---Policy ID/PRM_POLICY_ID: "+newRecord.getId().getPolPolicyId() +" PRM_CL_CODE: " +premiumQuo.getPrmClCode());
				}else{
					saveOrUpdate( premiumQuo );
				}

				//	getHibernateTemplate().flush();
				//	getHibernateTemplate().evict( premiumQuo );

			}
		}

	}
	/*
	 * Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change
	 */
	private Boolean checkSpecialCoverExistsForSBS(String coverCode) {
		LOGGER.debug("Entering checkSpecialCoverExistsForSBS");
		Boolean vatEnabledFlag = DAOUtils.isVatEnabled();
		//Boolean vatApplicableFlag = DAOUtils.isVatApplicable();
		if (Short.valueOf(coverCode).equals(SvcConstants.SC_PRM_COVER_VAT_TAX)
				&& vatEnabledFlag.equals(Boolean.TRUE)) {
			System.out.println("151 exists for SBS");
			return true;
		}
		return false;
	}
	
	/* Clears off contextMap values for the current thread */
	private void clearThreadLevelContext(){
		String riskId = getRiskIdFromTLC();
		Object appFlow=ThreadLevelContext.get( "APP_FLOW_PRE_SVC" );
		
		ThreadLevelContext.clearAll();
		
		if(!Utils.isEmpty( appFlow )){
			ThreadLevelContext.set( "APP_FLOW_PRE_SVC", appFlow );
		}
		setRiskIdTOTLC(riskId);
	}

	
	private void setRiskIdTOTLC( String riskId ){
		com.rsaame.pas.cmn.context.ThreadLevelContext.set( com.Constant.CONST_RISK_GROUP_ID , riskId);
	}

	private String getRiskIdFromTLC(){
		return (String)com.rsaame.pas.cmn.context.ThreadLevelContext.get( com.Constant.CONST_RISK_GROUP_ID );
	}

	
	private void updateValidityExpiryDates( PolicyVO policyVO ,int sectionId){
		/* Call the Stored Procedure that updates the previous Endt_Id records' Validity_Expiry_Date to current date. */
		/*if( policyVO.getAppFlow() == Flow.AMEND_POL ){*/
			DAOUtils.updateVED( policyVO, getClassCode(),sectionId );
		/*}*/
	}
	
	/**
	 * TODO Documentation needed on the actions decided here.
	 * 
	 * @param isToBeCreated
	 * @param isToBeDeleted
	 * @param existingRecord
	 * @param trnBuildingQuo
	 * @return
	 */
	protected SaveCase getSaveCase( boolean isToBeCreated, boolean isToBeDeleted, int existingRecStatus, POJO existingValues, POJO submittedValues )
	{
		SaveCase saveCase = null;
		
		if( isToBeCreated ){
			saveCase = SaveCase.CREATE;
			
			ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.TRUE );
		}
		else if( isToBeDeleted ){
			if( !Utils.isEmpty( existingValues ) && SvcConstants.POL_STATUS_PENDING == existingRecStatus ){
				saveCase = SaveCase.DELETE_PENDING_REC;
				
				ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.TRUE );
			}
			else{
				saveCase = SaveCase.DELETE;
				
				ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.TRUE );
			}
		}
		else{
			/* Figure out if the values have changed. If they haven't, then we will not take any action on this table. */
			boolean hasDataChanged = SvcUtils.hasDataChanged( existingValues, submittedValues );
			
			if( hasDataChanged ) ThreadLevelContext.set( SvcConstants.TLC_KEY_DATA_HAS_CHANGED, Boolean.TRUE );
			
			if( hasDataChanged && !Utils.isEmpty( existingValues ) && SvcConstants.POL_STATUS_ACTIVE == existingRecStatus ){
				saveCase = SaveCase.CHANGE_WITH_NEW_REC;
			}
			else if( hasDataChanged && !Utils.isEmpty( existingValues ) && SvcConstants.POL_STATUS_PENDING == existingRecStatus ){
				saveCase = SaveCase.CHANGE_WITH_EXISTING_REC;
			}
			else{
				/* No action is required as nothing has changed. */
				return null;
			}
		}

		return saveCase;
	}

	private void validateExistingRecordAvailability( POJO existingRecord, SaveCase saveCase ){
		switch( saveCase ){
			case DELETE:
			case CHANGE_WITH_EXISTING_REC:
			case CHANGE_WITH_NEW_REC:
			if( Utils.isEmpty( existingRecord ) ){
				throw new BusinessException( "pas.cmn.existingTableRecordNotFound", null, "Existing record for table not found for POJO [" + existingRecord.getClass(), "]" );
			}
			//Sonar Fix for End switch case with unconditional break,return or throw statement
			break;
			//sonar fix
			default:
				break;
		}
	}
	
	/**
	 * Constructs the Id for table based on the case of save.
	 * 
	 * @param existingId
	 * @param saveCase
	 * @return
	 */
	private <T extends POJOId> POJOId constructId( String tableId, PolicyVO policyVO, T existingId, POJO mappedRecord ,SaveCase saveCase ){
		POJOId id = null;
		
		switch( saveCase ){
			case CREATE:
				id = constructCreateRecordId( tableId, policyVO,mappedRecord );
				
				break;
				
			case DELETE:
			case CHANGE_WITH_NEW_REC:
				id = constructChangeRecordId( tableId, policyVO, existingId );
				
				break;
				
			case DELETE_PENDING_REC:
			case CHANGE_WITH_EXISTING_REC:
				id = CopyUtils.copySerializableObject( existingId );
				break;
				//sonar fix
			default:
				break;
		}
		
		return id;
	}

	/**
	 * Sets the versioning details of a record: Endt_Id and Validity_Start_Date.
	 * 
	 * @param pojo
	 * @param policyVO
	 * @param create
	 */
	private void setVersionAndStatusDetails( POJO pojo, PolicyVO policyVO, SaveCase saveCase ){
		if( !( saveCase == SaveCase.CHANGE_WITH_EXISTING_REC ) ){
			pojo.setValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		}
		// since end edit, end id is comin as null,moved to here
		pojo.setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		pojo.setEndtNo( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ) );
		
		/* Set the status for the record. */
		switch( saveCase ){
			case CREATE:
			case CHANGE_WITH_NEW_REC:
			case CHANGE_WITH_EXISTING_REC:
				pojo.setStatus( SvcConstants.POL_STATUS_PENDING );
				break;
			
			case DELETE:
				pojo.setStatus( SvcConstants.POL_STATUS_DELETED );
				//Sonar Fix for End switch case with unconditional break,return or throw statement
				break;
				//sonar fix
			default:
				break;
		}
	}
	
	/**
	 * 
	 * @param sectionVO
	 * @param policyVO
	 */
	protected void handleAdditionalCovers( SectionVO sectionVO, PolicyVO policyVO ){
		/* Following is the logic to handle additional covers for all the sections
		 * (a). Create Contents[] depending on the number of additional covers configured and handled in each
		 *      of the section. Hence this can be achieved through section specific implementation of abstract method
		 * (b). Insert/Update records to PremiumTable through handleTableRecord() method for each of the contents
		 */

		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( sectionVO );
		RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, sectionVO );

		List<Contents> ADDTL_COVER_CNT_LIST = constructAddtlCoverCntListForCurrRGD( PolicyUtils.getRiskGroupDetails( PolicyUtils.getRiskGroupForProcessing( sectionVO ), sectionVO ) );

		int zeroVal = 0;

		if(!Utils.isEmpty( ADDTL_COVER_CNT_LIST ) && ADDTL_COVER_CNT_LIST.size() > zeroVal ){
			/* Get Basic Risk Id of the section for Current RiskGroupDetails */
			Long basicRiskIdOfSection = getBasicRiskIdFromCurrRGD( rgd );

			POJO[] premiumDep = constructPOJOAForPrmTableMapping( policyVO, basicRiskIdOfSection );

			PropertyRiskDetails NIL_PRD_FOR_ADDTL_COVER_CNT = null;
			for( Contents content : ADDTL_COVER_CNT_LIST ){
				BaseVO[] bldDepVO = { content, NIL_PRD_FOR_ADDTL_COVER_CNT, content.getPremium() };
				if( handleTableRecordToBeCalled( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, premiumDep, bldDepVO ) ){
					handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, premiumDep, bldDepVO, false, sectionVO.getPolicyId(),
							BigDecimal.valueOf( basicRiskIdOfSection ), BigDecimal.valueOf( basicRiskIdOfSection ), content.getCoverCode().shortValue(), content.getCoverType()
									.shortValue(), content.getCoverSubType().shortValue() );
				}
			}
		}
	}
	
	/**
	 * This method is section specific implementation method to obtain the list of 
	 * @param currRgd
	 * @return
	 */
	public abstract List<Contents> constructAddtlCoverCntListForCurrRGD(RiskGroupDetails currRgd);
	
	/**
	 * This method is section specific implementation method to obtain BasicRiskId for the Current RiskGroupDetail
	 * @param rgd
	 * @return
	 */
	public abstract Long getBasicRiskIdFromCurrRGD(RiskGroupDetails rgd);
	
	/**
	 * This method is section specific implementation method to obtain POJO[] required for creating 
	 * Premium table mapping
	 * @param policyVO
	 * @param basicRiskIdOfCurrRGD
	 * @return
	 */
	public abstract POJO[] constructPOJOAForPrmTableMapping(PolicyVO policyVO, Long basicRiskIdOfCurrRGD);
	
	/** Identify if additional covers save operation is performed i.e. entries to premium
		 table is being invoked using Contents Object passed through BaseVO[] 
	 * 
	 * @param policyVO
	 * @param depsPOJO
	 * @param depsVO
	 * @return
	 */
	public boolean identifyCaseOfInsertForAddtlCovers( PolicyVO policyVO, POJO[] depsPOJO, Contents content ){
		boolean isCaseOfInsert = false;

		SectionVO currSection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( currSection );
		RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, currSection );

		Long basicRiskIdOfLocation = getBasicRiskIdFromCurrRGD( rgd );
		/* Get the current valid-state record, if key is available. That is, the record with VED as 31-DEC-2049. */	
		/*
		POJO existingRecord = DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(), getHibernateTemplate(), false, null, section
				.getPolicyId(), BigDecimal.valueOf( basicRiskIdOfLocation ), BigDecimal.valueOf( basicRiskIdOfLocation ), content.getCoverCode().shortValue(), content
				.getCoverType().shortValue(), content.getCoverSubType().shortValue() ); */
		
		
		List<POJO> existingRecordLst = (List<POJO>)DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(), TableSnapshotLevel.CURRENT_VALID_STATE, getHibernateTemplate(), false,
				( (Long)ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), currSection.getPolicyId(), BigDecimal.valueOf( basicRiskIdOfLocation ),
				BigDecimal.valueOf( basicRiskIdOfLocation ), content.getCoverCode().shortValue(), content.getCoverType().shortValue(), content.getCoverSubType().shortValue() );

		if( Utils.isEmpty( existingRecordLst ) && content.getCoverOpted().intValue() == SvcConstants.APP_ADDITIONAL_COVER_OPTED){
			isCaseOfInsert = true;
		}
		return isCaseOfInsert;
	}
	
	/**
	 * Return handleTableRecordToBeCalled as true or false within the overriden method for each of the section DAO if required.
	 * This is added in order to decide whether handleTableRecord needs to called for a table. One ex - of such scenario is 
	 * PAR screen building covered was "No" during initial save operation and during endorsement/edit quote, it is still
	 * kept as "No" , then it is case of isToBeCreated as false, isToBeDeleted as false in case of T_TRN_PREMIUM(_QUO) table. Hence
	 * if both are false then it will be exceptional case within handleTableRecord method.
	 * @param tableId
	 * @param policyVO
	 * @param depsPojo
	 * @param depsVO
	 * @return
	 */
	public boolean handleTableRecordToBeCalled(String tableId, PolicyVO policyVO, POJO[] depsPojo, BaseVO[] depsVO){		
	
		
		return true;
	}
	
	/**
	 * Sets 0 to premiumAmt and ActualPrmAmount within TTrnPremium(_Quo) pojo
	 * @param prmPOJO
	 */
	public void setZeroPrmValue(POJO prmPOJO){
		/* Setting premium values to 0 in order to avoid null checks during prorate premium calculation */
		
		TTrnPremiumQuo ttrnPrmPOJO = (TTrnPremiumQuo) prmPOJO;
		ttrnPrmPOJO.setPrmPremium( BigDecimal.valueOf( ZERO_VAL ) );
		ttrnPrmPOJO.setPrmPremiumActual( BigDecimal.valueOf( ZERO_VAL ) );
	}
	
	/*
	 * Fetches the record from TableId passed based on the passed policyId, prevEndId and tableKeys and update the validity expiry date to 31-DEC-2049.
	 */
	private void activatePrevRecord( String tableId, Long policyId, Long prevEndId, Flow appFlow, Object... tableKeys  ){
		
		POJO previousRecord = null;
		if(!Utils.isEmpty( prevEndId )){
			previousRecord = DAOUtils.getExistingEndtIdStateRecord( tableId,appFlow,getHibernateTemplate(), false, prevEndId, tableKeys );
		}
		if(!Utils.isEmpty( previousRecord )){
			previousRecord.setValidityExpiryDate( SvcConstants.EXP_DATE );
			update( previousRecord );
		}
		
	}
	
	/**
	 * Queries T_TRN_SECTION_LOCATION(_QUO) table to check if current location id exists (return's true if so)
	 * @param sectionVO
	 * @param policyVO
	 * @return
	 */
	private boolean checkIfLocEntryExistsInSecLocation( SectionVO sectionVO, PolicyVO policyVO ){

		boolean locEntryExists = false;
		RiskGroup currRG = PolicyUtils.getRiskGroupForProcessing( sectionVO );
		Long locationIdParam = Long.valueOf( currRG.getRiskGroupId() );
		Long polLinkingIdParam = policyVO.getPolLinkingId();
		Integer sectionIdParam = sectionVO.getSectionId();
		String activeLocationFlag = "Y";
		List<POJO> existingLocRecordList = null;

		/* Criteria for the table will be validityExpiryDate : 31-DEC-2049, policyLinkingId, sectionId and current Location Id */
		existingLocRecordList = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_SECTION_LOCATION, policyVO.getAppFlow(), getHibernateTemplate(), false,
				( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), polLinkingIdParam, sectionIdParam.shortValue(), locationIdParam, activeLocationFlag );

		if( !Utils.isEmpty( existingLocRecordList ) && existingLocRecordList.size() > ZERO_VAL ) locEntryExists = true;

		return locEntryExists;
	}
	
	/**
	 * Returns true in case a UW question response to be captured. This method to be overriden in child DAO's if required
	 * @param policyVO
	 * @param depsPojo
	 * @param depsVO
	 * @return
	 */
	protected boolean toCreateUWQuestionsRecord(PolicyVO policyVO, POJO[] depsPojo, BaseVO[] depsVO){
		
		boolean toCreate = true;
		
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
		//RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, section );
		UWQuestionVO oneQuestionResp = null;
		if( section.getSectionId().intValue() == SvcConstants.SECTION_ID_PAR )  oneQuestionResp = (UWQuestionVO)depsVO[0];
		if( section.getSectionId().intValue() == SvcConstants.SECTION_ID_PL)  oneQuestionResp = (UWQuestionVO)depsVO[1];
		if( section.getSectionId().intValue() == SvcConstants.SECTION_ID_BI)  oneQuestionResp = (UWQuestionVO)depsVO[0];
		if( section.getSectionId().intValue() == SvcConstants.SECTION_ID_MB)  oneQuestionResp = (UWQuestionVO)depsVO[2];
		if( section.getSectionId().intValue() == SvcConstants.SECTION_ID_DOS)  oneQuestionResp = (UWQuestionVO)depsVO[2];
		if( section.getSectionId().intValue() == SvcConstants.SECTION_ID_FIDELITY)  oneQuestionResp = (UWQuestionVO)depsVO[0];
		try{
			POJO existingRecord = DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE, policyVO.getAppFlow(), getHibernateTemplate(), false,
					(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), section.getPolicyId(), oneQuestionResp.getQId(), Long.valueOf( rg.getRiskGroupId() ),
					Utils.isEmpty( oneQuestionResp.getResponse() ) ? null : oneQuestionResp.getResponse() );
			if(!Utils.isEmpty( existingRecord ) && existingRecord.getStatus() != 4){
				toCreate = false;
			}
			
			
			// The below code added to fix the defect-ID:144086, TestCase:04 -START
			if(!Utils.isEmpty( existingRecord ) && existingRecord.getStatus() == 4){
				ThreadLevelContext.set(SvcConstants.DELETED_UW_QUESTION, existingRecord);
			}
			// The below code added to fix the defect-ID:144086, TestCase:04 -END 
			
		}catch(BusinessException be){
		    LOGGER.debug("Error while creating under writing question Record"+be);
			/* Record doesn't exist for this uwq_code, policy id, location id and response combination hence go ahead and create it */
		}
		
		return toCreate;
	}
	
	/**
	 * (1).Returns TTrnSectionDetailsQuo in case section record is present in T_TRN_SECTION_DETAILS(_QUO) table with following key values
	 * #policyId, #sectionId, #classCode, #policyType, #commissionValue, #validityExpDate='31-DEC-2049' and #status <> 4
	 * @param sectionVO
	 * @param policyVO
	 * @return
	 *//*
	private TTrnSectionDetailsQuo checkIfSecEntryExistsInSecDets( SectionVO sectionVO, PolicyVO policyVO){
		List<POJO> existingSecRecordList = null;
		TTrnSectionDetailsQuo existingSecDetsRecord = null;
		existingSecRecordList = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_SECTION_DETAILS_CREATE, policyVO.getAppFlow(), getHibernateTemplate(),
				false, ( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), sectionVO.getPolicyId(), sectionVO.getSectionId().shortValue(), sectionVO.getClassCode()
						.shortValue(), policyVO.getPolicyTypeCode().shortValue() );

		if( !Utils.isEmpty( existingSecRecordList ) && existingSecRecordList.size() > zeroVal ) {
			existingSecDetsRecord = ( (TTrnSectionDetailsQuo) existingSecRecordList.get( zeroVal ) );
		}
		return existingSecDetsRecord;
	}*/
	
	/**
	 * Returns POJOId for T_TRN_SECTION_DETAILS(_QUO) table record
	 * (1). If status within Passed POJO is PENDING, then returns a copy of existing record POJOId
	 * (2). If status is not PENDING , creates new POJOId and returns it
	 * @param existingSecDetsRecordPOJO
	 * @return
	 *//*
	private POJOId constructSecDetsPOJOId( POJO existingSecDetsRecordPOJO ){
		POJOId id = null;
		TTrnSectionDetailsQuoId secDetsRecordId = null;
		if( !Utils.isEmpty( existingSecDetsRecordPOJO ) && SvcConstants.POL_STATUS_PENDING == existingSecDetsRecordPOJO.getStatus() ){
			id = CopyUtils.copySerializableObject( existingSecDetsRecordPOJO.getId() );
		}
		else{
			secDetsRecordId = new TTrnSectionDetailsQuoId();
			secDetsRecordId.setSecValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			TTrnSectionDetailsQuo existSecDetsRecord = (TTrnSectionDetailsQuo) existingSecDetsRecordPOJO;
			secDetsRecordId.setSecPolicyId( existSecDetsRecord.getId().getSecPolicyId() );
			secDetsRecordId.setSecSecId( existSecDetsRecord.getId().getSecSecId() );
			id = secDetsRecordId;
		}

		return id;
	}*/
	/**
	 * Sets PRM_RATE_TYPE field value within TTrnPremiumQuo POJO
	 * @param policyVO
	 * @param prmPOJO
	 */
	protected void setRateTypeToPremiumPOJO(PolicyVO policyVO, POJO prmPOJO){
		if( Utils.isEmpty( policyVO.getScheme().getTariffRateType() ) ) DAOUtils.setRateTypeFromRatingTable( getHibernateTemplate(), policyVO );
		
		TTrnPremiumQuo tTrnPrmPOJO = (TTrnPremiumQuo)prmPOJO;
		tTrnPrmPOJO.setPrmRateType( policyVO.getScheme().getTariffRateType() );
	}
	
	/**
	 * This method sets the base sections building details to thread level
	 * context. This is needed to check if the location information has]
	 * changed.
	 */
	private void setBaseSectionTo(POJO recordToPersist, POJO existingRecord){
		
		if(!Utils.isEmpty( existingRecord )){
			if( recordToPersist instanceof TTrnBuildingQuo || recordToPersist instanceof TTrnWctplPremiseQuo){
				
				ThreadLevelContext.set( SvcConstants.OLD_REC,CopyUtils.copySerializableObject( existingRecord ));
				ThreadLevelContext.set( SvcConstants.NEW_REC,CopyUtils.copySerializableObject( recordToPersist ));
			}
		}
	}
	
	
	/*private void validate( PolicyVO policyVO ){
		
		
		 * Validate the quote/policy to check if endt VSD is greater than the previous endt VSD
		 
		if( !policyVO.getIsQuote() ){
			DAOUtils.validateVSD( policyVO,Integer.valueOf( getClassCode() ).shortValue()  ,getHibernateTemplate() );
		}
		
	}*/

}
