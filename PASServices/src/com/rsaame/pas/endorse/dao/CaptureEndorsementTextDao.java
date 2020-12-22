/**
 * 
 */
package com.rsaame.pas.endorse.dao;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnEndorsementDetail;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1014241
 * 
 */
public class CaptureEndorsementTextDao extends BaseDBDAO implements
		ICaptureEndorsementTextDao {

	private final static Logger LOGGER = Logger
			.getLogger(CaptureEndorsementTextDao.class);
	private final static Integer QUOTE_ACTIVE = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) );
	private final static Integer QUOTE_PENDING = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_PENDING" ) );
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rsaame.pas.endorse.dao.ICaptureEndorsementTextDao#getEndorsementText
	 * (com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO getEndorsementText(BaseVO baseVO) {

		if (LOGGER.isInfo())
			LOGGER.info("Enterning get endoresment text method");

		PolicyVO policyVO = (PolicyVO) baseVO;
		
		/*Save Trade lic.*/
		/**
		 * Oman multi branching: For Oman there are  no trade license no.  present.
		 */
		if(Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.TRADE_LICENSE_CHECK))  ){
		
				DAOUtils.saveTradeLicNo(policyVO, getHibernateTemplate() );
		}		
		//Added by Anveshan  for Student Liability
		Long policyId = DAOUtils.getSectionPolicyId( policyVO, 6, getHibernateTemplate() );
		if(!Utils.isEmpty( policyId ) && policyId!=0 &&  policyVO.getAppFlow() == Flow.AMEND_POL && policyVO.getStandardClause() != null ){
			SectionVO basicSection = getSectionVO( policyVO, policyId );
			DAOUtils.updateStudentLiabilityExclusion(basicSection, policyId, policyVO);
		}
		
		/*Added to fix Endorsement Text CR defects */
		DAOUtils.addDeleteEndtHeaderText(policyVO,Boolean.TRUE,getHibernateTemplate());
		/*End - Changes */		
		
		// Get endorsement text from V_TRN_PAS_GET_ENDT_TEXT and than set into
		// policy VO.
		PremiumVO newPremium = new PremiumVO(); 
		PremiumVO oldPremium = new PremiumVO();
		String endtType = "";
		if(!Utils.isEmpty( policyVO.getEndorsements() )){
		
			newPremium = policyVO.getEndorsements().get(0).getPremiumVO();
			oldPremium = policyVO.getEndorsements().get(0).getOldPremiumVO();
			endtType = policyVO.getEndorsements().get(0).getEndType();
		}
		List<EndorsmentVO> endorsements = null;
		
		/*
		 * (if case   )In amend flow the latest endorsement id for that policy is available with policyVO.newEndtId
		 * (else case )In View mode flows policyVO.endtId will be having the latest endorsement id for that policy.
		 */
		Long endtId = !(Utils.isEmpty( policyVO.getNewEndtId() )) ? policyVO.getNewEndtId() : policyVO.getEndtId();
		
		endorsements = getEndorsementText(policyVO.getPolLinkingId(),endtId);		
		
		if(!Utils.isEmpty( endorsements )) {
			
			endorsements.get( 0 ).setPremiumVO( newPremium );
			endorsements.get( 0 ).setOldPremiumVO( oldPremium );
			endorsements.get( 0 ).setEndType( endtType );
			
		} else {
			
			EndorsmentVO endorsmentVO = new EndorsmentVO();
			String queryForPolicyId = "select pol_policy_id from t_trn_policy where pol_linking_id = ? and pol_endt_id = ?";
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( queryForPolicyId, getHibernateTemplate(), policyVO.getPolLinkingId(),policyVO.getEndtId() );
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				endorsmentVO.setPolicyId( ( (BigDecimal) valueHolder.get( 0 ) ).longValue() );
			}
			//endorsmentVO.setPolicyId( policyVO.getScheme().getId() );
			endorsmentVO.setEndtId( endtId );
			endorsmentVO.setEndNo( policyVO.getEndtNo() );
			endorsmentVO.setSlNo( QUOTE_ACTIVE );
			endorsmentVO.setPremiumVO( newPremium );
			endorsmentVO.setOldPremiumVO( oldPremium );
			endorsmentVO.setEndType( endtType );
			endorsements.add( endorsmentVO );
			
		}
		
		policyVO.setEndorsements(endorsements);

		return policyVO;

	}

	private  List<EndorsmentVO> getEndorsementText( long policyLinkingId, long endtId ){

		java.util.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );

		EndorsmentVO endorsmentVO = null;

		String endtSql = " select * from V_Trn_Pas_Get_Endt_Text where pol_Linking_Id = " + policyLinkingId + " and edl_endorsement_id = " + endtId ;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( endtSql );
		List<Object> result = query.list();

		Iterator<Object> itr = null;
		itr = result.iterator();
		Object[] row = null;
		while( itr.hasNext() ){
			row = (Object[]) itr.next();
			endorsmentVO = new EndorsmentVO();
			if( !Utils.isEmpty( row[ 0 ] ) ) endorsmentVO.setPolicyId( Long.valueOf( String.valueOf( row[ 0 ] ) ) );
			if( !Utils.isEmpty( row[ 1 ] ) ) endorsmentVO.setEndtId( Long.valueOf( String.valueOf( row[ 1 ] ) ) );
			if( !Utils.isEmpty( row[ 3 ] ) ) endorsmentVO.setEndNo( Long.valueOf( String.valueOf( row[ 3 ] ) ) );
			if( !Utils.isEmpty( row[ 4 ] ) ) endorsmentVO.setSlNo( Integer.valueOf( String.valueOf( row[ 4 ] ) ) );
			if( !Utils.isEmpty( row[ 5 ] ) ) endorsmentVO.setEndText( String.valueOf( row[ 5 ] ) );
			if( !Utils.isEmpty( row[ 6 ] ) ) endorsmentVO.setRecCreatedBy( Integer.valueOf( String.valueOf( row[ 6 ] ) ) );
			if( !Utils.isEmpty( row[ 7 ] ) ) endorsmentVO.setRecCreaDate( Timestamp.valueOf(String.valueOf( row[7] ) ));
			if( !Utils.isEmpty( row[ 8 ] ) ) endorsmentVO.setRecModifiedBy( Integer.valueOf( String.valueOf( row[ 8 ] ) ) );
			if( !Utils.isEmpty( row[ 9 ] ) ) endorsmentVO.setRecModifiedDt( Timestamp.valueOf(String.valueOf( row[9] ) ));
			if( !Utils.isEmpty( row[ 10 ] ) ) endorsmentVO.setEndSecId( Integer.valueOf( String.valueOf( row[ 10 ] ) ) );
			
			endorsements.add( endorsmentVO );
		}


		return endorsements;

	}


	@Override
	public BaseVO confirmEndtProcessing( BaseVO baseVO ){

		if( baseVO instanceof PolicyVO ){
			baseVO = confirmEndtSbs( baseVO );
		}
		else if( baseVO instanceof PolicyDataVO ){
			baseVO = confirmEndtHomeTravel( baseVO );
		}

		return baseVO;

	}
	@Override
	public BaseVO saveEndtProcessing( BaseVO baseVO ){

		if( baseVO instanceof PolicyVO ){
			baseVO = saveEndtSbs( baseVO );
		}
		/*else if( baseVO instanceof PolicyDataVO ){
			baseVO = saveEndtHomeTravel( baseVO );
		}*/

		return baseVO;

	}

	public BaseVO saveEndtText( BaseVO baseVO ){
		
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;

		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsmentVOs = policyDataVO.getEndorsmentVO();

		if( !Utils.isEmpty( endorsmentVOs ) ){
			if(endorsmentVOs.size() == 1 && Utils.isEmpty(endorsmentVOs.get( 0 ).getEndSecId())){
				endorsmentVOs.get( 0 ).setEndSecId( 0 );
			}
			for( EndorsmentVO endorsmentVO : endorsmentVOs ){

				if( !Utils.isEmpty( endorsmentVO ) ){

					if( !( Utils.isEmpty( endorsmentVO.getSlNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndtId() ) )
							&& !( Utils.isEmpty( endorsmentVO.getPolicyId() ) ) ){
						if( endorsmentVO.getSlNo() != -9999 && endorsmentVO.getEndNo() != -9999 && endorsmentVO.getEndtId() != -9999 && endorsmentVO.getPolicyId() != -9999 ){

							DAOUtils.saveOrUpdateEndorsementText( endorsmentVO, getHibernateTemplate() );
						}
					}
				}
			}
		}
		
		return baseVO;
	}
	
	
	
	private BaseVO confirmEndtHomeTravel( BaseVO baseVO ){
		if (LOGGER.isInfo())
		{
			LOGGER.info("Calling confirmEndtHomeTravel method of CaptureEndorsementTextDao");
		}
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;

		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsmentVOs = policyDataVO.getEndorsmentVO();

		if( !Utils.isEmpty( endorsmentVOs ) ){
			if(endorsmentVOs.size() == 1 && Utils.isEmpty(endorsmentVOs.get( 0 ).getEndSecId())){
				endorsmentVOs.get( 0 ).setEndSecId( 0 );
			}
			for( EndorsmentVO endorsmentVO : endorsmentVOs ){

				if( !Utils.isEmpty( endorsmentVO ) ){

					if( !( Utils.isEmpty( endorsmentVO.getSlNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndtId() ) )
							&& !( Utils.isEmpty( endorsmentVO.getPolicyId() ) ) ){
						if( endorsmentVO.getSlNo() != -9999 && endorsmentVO.getEndNo() != -9999 && endorsmentVO.getEndtId() != -9999 && endorsmentVO.getPolicyId() != -9999 ){

							DAOUtils.saveOrUpdateEndorsementText( endorsmentVO, getHibernateTemplate() );
						}
					}
				}
			}
		}
		
		/* To insert record to T_TRN_ENDORSEMENT table  */
		PASStoredProcedure sp;
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
			sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSPHomeTravel");
		else
			sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSPMonoline");
		long entId = (Long) getHibernateTemplate().find( "select max(pol.id.polEndtId) from TTrnPolicyQuo pol where pol.id.polPolicyId=?", policyDataVO.getPolicyId() ).get( 0 );
		try{
			Map results = sp.call( policyDataVO.getPolicyId(), entId, SvcUtils.getUserId( policyDataVO ), policyDataVO.getEndEffectiveDate() );
			if( !Utils.isEmpty( results ) && !Utils.isEmpty( results.get( "po_vsd_date" ) ) ){
				policyDataVO.getCommonVO().setVsd( (Date) results.get( "po_vsd_date" ) );
			}
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.confirmEndtHomeTravel.exception", e, "An exception occured while inserting into T_TRN_ENDORSEMENT." );
		}
		
		return policyDataVO;
	}

	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO confirmEndtSbs( BaseVO baseVO ){
		if (LOGGER.isInfo())
		{
			LOGGER.info("Calling confirmEndtSBS method of CaptureEndorsementTextDao");
		}
		updateClauses( baseVO );
		PolicyVO policyVO = (PolicyVO) baseVO;
		List<EndorsmentVO> endorsmentVOs = policyVO.getEndorsements();
		/*
		 * (if case   )In amend flow the latest endorsement id for that policy is available with policyVO.newEndtId
		 * (else case )In View mode flows policyVO.endtId will be having the latest endorsement id for that policy.
		 */
		Long endtId = DAOUtils.getEndtToProcess( getHibernateTemplate(), policyVO );
		
		/* Update endtId obtained for processing to both fields within PolicyVO i.e. policyVO.endtId and policyVO.newEndtId in order
		 * to avoid discrepancies in reports immediately after endorsement confirmation */
		DAOUtils.syncUpEndtIdPolicyVO(policyVO, endtId);
		/*
		 * Licensed by /approved by fields should capture the whoever has endorsed the policy.
		 */
		List<TTrnPolicyQuo> policy = DAOUtils.getPolicyRecord( getHibernateTemplate(), endtId, policyVO.getPolLinkingId() );
				
		if(!Utils.isEmpty( policy )){
			
			for(TTrnPolicyQuo pol : policy){
				
				if( ( pol.getPolStatus() == QUOTE_ACTIVE || pol.getPolStatus() == QUOTE_PENDING ) && Utils.isEmpty( pol.getPolApprovedBy() ) ){
					pol.setPolApprovedBy( SvcUtils.getUserId( policyVO) );
						pol.setPolApprovalDate( getSysDate() );
				}
	/*	if( !SvcUtils.getUserId( policyVO).equals( pol.getPolApprovedBy() ) ){
					pol.setPolApprovedBy( SvcUtils.getUserId( policyVO) );
				}
				
				if( !SvcUtils.getUserId( policyVO).equals( pol.getPolUserId())){
					pol.setPolUserId( SvcUtils.getUserId( policyVO) );
				}
				*/

				
				getHibernateTemplate().merge( pol );
			}
			
		}
		/*
		 * Merge function will lock the rows to update and will not commit  till transaction is completed
		 * hence below procedures will not update any t_trn_policy table records so call flush function to 
		 * update to the policy records.
		 */
		getHibernateTemplate().flush();
		
		PASStoredProcedure sp=null;
		
		if(!Utils.isEmpty( endorsmentVOs )) {
			for (EndorsmentVO endorsmentVO : endorsmentVOs) {
	
				if(!Utils.isEmpty( endorsmentVO )) {
					
					if( !(Utils.isEmpty(endorsmentVO.getSlNo()) )   && !(Utils.isEmpty(endorsmentVO.getEndNo())) && !(Utils.isEmpty(endorsmentVO.getEndtId())) && !(Utils.isEmpty(endorsmentVO.getPolicyId()) ) ) {
						if( endorsmentVO.getSlNo() != -9999  && endorsmentVO.getEndNo() != -9999   && endorsmentVO.getEndtId() != -9999   && endorsmentVO.getPolicyId() != -9999   ) {
								//if(endorsmentVO.getEndText().length()>0){
							/*
							 * removed length check because if endt text is deleted then it
							 * should be saved as null.
							 */
									DAOUtils.saveOrUpdateEndorsementText( endorsmentVO, getHibernateTemplate() );
								//}
						}
					}
				}
			}
		}
		
		/*
		 * The following procedure inserts a record in T_TRN_ENDORSEMENT which signifies the confirmation of an endorsement
		 * The proc is included here, as the data into T_TRN_ENDORSEMENT should be inserted on confirmation of the endt
		 */
		
		
		/* 1. PRO_UPD_VSD_VED procedure is called to update VSD of the current endorsement record in case there is need to 
		 * change which is usually when VSD passed to proc is greater than T_TRN_CLOSING table clo_closed_date value 
		 * 2. After updation of VSD this procedure updates same new VSD as expiry date to older records of current endt id */
		sp = (PASStoredProcedure) Utils.getBean("updateEndtVed"); 
		Date vsdToProcess = !Utils.isEmpty( policyVO.getNewValidityStartDate() )?policyVO.getNewValidityStartDate() :policyVO.getValidityStartDate();
		
		try{
			sp.call(policyVO.getPolLinkingId(),endtId,vsdToProcess);
		} catch (DataAccessException e){
			throw new BusinessException( "pas.ConfirmEndtSBS.exception", e, "An exception occured while executing stored proc updateEndtVed" );
		}
		
		/*
		 * Entry in t_trn_endorsment should be made only in case  endt ID is generated for the policy. In case of resolve referral
		 * the app flow is amend pol but new endt id is not generated, in this case skip getCreateEndtRecSP call
		 */
		if(!endtId.equals( Long.valueOf( SvcConstants.zeroVal ) )){ 
			sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSP");
			try{
				sp.call( policyVO.getPolLinkingId(), endtId, SvcUtils.getUserId( policyVO ) ,vsdToProcess );
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.ConfirmEndtSBS.exception", e, "An exception occured while executing stored proc getCreateEndtRecSP" );
			}
		}
		
		//update prev records status and Exp date

		sp = (PASStoredProcedure) Utils.getBean( "valExpDates" );
		try{
			sp.call( policyVO.getPolLinkingId(), endtId, vsdToProcess );
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc valExpDates" );
		}
		
		Long endtIdToProcess = DAOUtils.getEndtToProcess( getHibernateTemplate(), policyVO );
		
		/*
		 * After the policy is amended, the endt id's are swapped. 
		 */
		if( !Utils.isEmpty( policyVO.getEndtId() ) && !Utils.isEmpty( policyVO.getNewEndtId() ) ){
			policyVO.setEndtId( endtIdToProcess );
		}
		policyVO.setNewEndtId( null );
		//CTS - 14.08.2020 - UAT issue fix for transaction history status shows pending for endorsement - Starts
		policyVO.setStatus(Integer.valueOf(Utils.getSingleValueAppConfig("QUOTE_ACTIVE")));
		//CTS - 14.08.2020 - UAT issue fix for transaction history status shows pending for endorsement - Ends

		return policyVO;
	}
	private BaseVO saveEndtSbs( BaseVO baseVO ){
		if (LOGGER.isInfo()) LOGGER.info("Enterning save endoresment text method");

		PolicyVO policyVO = (PolicyVO) baseVO;
		List<EndorsmentVO> endorsmentVOs = policyVO.getEndorsements();
		
		/* * (if case   )In amend flow the latest endorsement id for that policy is available with policyVO.newEndtId
		 * (else case )In View mode flows policyVO.endtId will be having the latest endorsement id for that policy.
		 
		Long endtId = DAOUtils.getEndtToProcess( getHibernateTemplate(), policyVO );
		
		 Update endtId obtained for processing to both fields within PolicyVO i.e. policyVO.endtId and policyVO.newEndtId in order
		 * to avoid discrepancies in reports immediately after endorsement confirmation 
		DAOUtils.syncUpEndtIdPolicyVO(policyVO, endtId);
		
		 * Licensed by /approved by fields should capture the whoever has endorsed the policy.*/
		
		/*getHibernateTemplate().flush();
		
		PASStoredProcedure sp=null;*/
		
		if(!Utils.isEmpty( endorsmentVOs )) {
			for (EndorsmentVO endorsmentVO : endorsmentVOs) {
	
				if(!Utils.isEmpty( endorsmentVO )) {
					
					if( !(Utils.isEmpty(endorsmentVO.getSlNo()) )   && !(Utils.isEmpty(endorsmentVO.getEndNo())) && !(Utils.isEmpty(endorsmentVO.getEndtId())) && !(Utils.isEmpty(endorsmentVO.getPolicyId()) ) ) {
					//	if( endorsmentVO.getSlNo() != -9999  && endorsmentVO.getEndNo() != -9999   && endorsmentVO.getEndtId() != -9999   && endorsmentVO.getPolicyId() != -9999   ) {
							
									DAOUtils.saveOrUpdateEndorsementText( endorsmentVO, getHibernateTemplate() );
								//}
						}
					}
				}
			}
		
		
		/*
		 * The following procedure inserts a record in T_TRN_ENDORSEMENT which signifies the confirmation of an endorsement
		 * The proc is included here, as the data into T_TRN_ENDORSEMENT should be inserted on confirmation of the endt
		 */
		
		
		/* 1. PRO_UPD_VSD_VED procedure is called to update VSD of the current endorsement record in case there is need to 
		 * change which is usually when VSD passed to proc is greater than T_TRN_CLOSING table clo_closed_date value 
		 * 2. After updation of VSD this procedure updates same new VSD as expiry date to older records of current endt id */
		/*sp = (PASStoredProcedure) Utils.getBean("updateEndtVed"); 
		Date vsdToProcess = !Utils.isEmpty( policyVO.getNewValidityStartDate() )?policyVO.getNewValidityStartDate() :policyVO.getValidityStartDate();
		
		try{
			sp.call(policyVO.getPolLinkingId(),endtId,vsdToProcess);
		} catch (DataAccessException e){
			throw new BusinessException( "pas.ConfirmEndt.exception", e, "An exception occured while executing stored proc." );
		}
		
		
		 * Entry in t_trn_endorsment should be made only in case  endt ID is generated for the policy. In case of resolve referral
		 * the app flow is amend pol but new endt id is not generated, in this case skip getCreateEndtRecSP call
		 
		if(!endtId.equals( Long.valueOf( SvcConstants.zeroVal ) )){
			sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSP");
			try{
				sp.call( policyVO.getPolLinkingId(), endtId, SvcUtils.getUserId( policyVO ) ,vsdToProcess );
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.ConfirmEndt.exception", e, "An exception occured while inserting into T_TRN_ENDORSEMENT." );
			}
		}
		
		//update prev records status and Exp date

		sp = (PASStoredProcedure) Utils.getBean( "valExpDates" );
		try{
			sp.call( policyVO.getPolLinkingId(), endtId, vsdToProcess );
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc." );
		}
		
		Long endtIdToProcess = DAOUtils.getEndtToProcess( getHibernateTemplate(), policyVO );
		
		
		 * After the policy is amended, the endt id's are swapped. 
		 
		if( !Utils.isEmpty( policyVO.getEndtId() ) && !Utils.isEmpty( policyVO.getNewEndtId() ) ){
			policyVO.setEndtId( endtIdToProcess );
		}
		policyVO.setNewEndtId( null );*/
		

		return policyVO;
	}

/*	private BaseVO saveEndtHomeTravel( BaseVO baseVO ){
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;

		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsmentVOs = policyDataVO.getEndorsmentVO();

		if( !Utils.isEmpty( endorsmentVOs ) ){
			if(endorsmentVOs.size() == 1 && Utils.isEmpty(endorsmentVOs.get( 0 ).getEndSecId())){
				endorsmentVOs.get( 0 ).setEndSecId( 0 );
			}
			for( EndorsmentVO endorsmentVO : endorsmentVOs ){

				if( !Utils.isEmpty( endorsmentVO ) ){

					if( !( Utils.isEmpty( endorsmentVO.getSlNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndNo() ) ) && !( Utils.isEmpty( endorsmentVO.getEndtId() ) )
							&& !( Utils.isEmpty( endorsmentVO.getPolicyId() ) ) ){
						if( endorsmentVO.getSlNo() != -9999 && endorsmentVO.getEndNo() != -9999 && endorsmentVO.getEndtId() != -9999 && endorsmentVO.getPolicyId() != -9999 ){

							DAOUtils.saveOrUpdateEndorsementText( endorsmentVO, getHibernateTemplate() );
						}
					}
				}
			}
		}
		
		 To insert record to T_TRN_ENDORSEMENT table  
		PASStoredProcedure sp;
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
			sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSPHomeTravel");
		else
			sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSPMonoline");
		long entId = (Long) getHibernateTemplate().find( "select max(pol.id.polEndtId) from TTrnPolicyQuo pol where pol.id.polPolicyId=?", policyDataVO.getPolicyId() ).get( 0 );
		try{
			Map results = sp.call( policyDataVO.getPolicyId(), entId, SvcUtils.getUserId( policyDataVO ), policyDataVO.getEndEffectiveDate() );
			if( !Utils.isEmpty( results ) && !Utils.isEmpty( results.get( "po_vsd_date" ) ) ){
				policyDataVO.getCommonVO().setVsd( (Date) results.get( "po_vsd_date" ) );
			}
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.ConfirmEndt.exception", e, "An exception occured while inserting into T_TRN_ENDORSEMENT." );
		}
		
		return policyDataVO;
	}
	*/
	/**
	 * Sets generated endorsement text for CANCEL_POLICY flow
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO getEndorsementTextForCancelPolicy(BaseVO baseVO){
		String endText = generateEndorsementTextForCancelPolicy((PolicyVO) baseVO);
		PolicyVO policyVO = (PolicyVO) baseVO;
		policyVO.getEndorsements().get(0).setEndText(endText);
		return baseVO;
	}
	
	/**
	 * Returns endorsementText which is generated by invoking the function pkg_endt_gen.get_endt_text_cancel_pol
	 * @param policyVO
	 * @return
	 */
	private String generateEndorsementTextForCancelPolicy(PolicyVO policyVO) {

		if (LOGGER.isInfo())
		{
			LOGGER.info("Calling generateEndorsementTextForCancelPolicy method of CaptureEndorsementTextDao");
		}
		//DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT ); 
		
		Double refundAmount = 0.0; 

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String endExpiryDate = sdf.format(policyVO.getEndEffectiveDate());
		
		refundAmount =  Double.valueOf(  policyVO.getEndorsements().get( 0 ).getPremiumVO().getPremiumAmt()
				- policyVO.getEndorsements().get( 0 ).getOldPremiumVO().getPremiumAmt()  ) ;
		
		refundAmount =   Math.abs( refundAmount ) ;
		LOGGER.debug( "Refeund Amount - "+ refundAmount.toString() );
		// refundAmount = DAOUtils.getRefundAmountForCancelPolicy(policyVO,policyVO.getEndorsements().get(0).getOldPremiumVO().getPremiumAmt());

		String sqlQuery = "SELECT PKG_ENDT_GEN.get_endt_text_cancel_pol('"+ endExpiryDate +"',"+ Currency.getUnformttedScaledCurrency( refundAmount ) + com.Constant.CONST_FROM_DUAL2; 

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		String endText = "";

		Query query = session.createSQLQuery(sqlQuery);

		List<Object> results = query.list();

		if(!Utils.isEmpty(results)){

		endText = results.get(0).toString();

		}

		return endText;

	}

	@Override
	public BaseVO getCommonEndorsementTextForCancelPolicy( BaseVO baseVO ){
		String endText = generateCommonEndorsementTextForCancelPolicy((PolicyDataVO) baseVO);
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		policyDataVO.getEndorsmentVO().get(0).setEndText(endText);
		return baseVO;
	}
	
	/**
	 * Returns endorsementText which is generated by invoking the function pkg_endt_gen.get_endt_text_cancel_pol
	 * @param policyDataVO
	 * @return
	 */
	private String generateCommonEndorsementTextForCancelPolicy(PolicyDataVO policyDataVO) {
		if (LOGGER.isInfo())
		{
			LOGGER.info("Calling generateCommonEndorsementTextForCancelPolicy method of CaptureEndorsementTextDao");
		}
		DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT ); 

		Double refundAmount = 0.0; 

		Double refundVatAmount = 0.0; 
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String endExpiryDate = sdf.format(policyDataVO.getEndEffectiveDate());
		
		//142244 -- updated for WC VAT		
		if(policyDataVO.getPolicyType().equals(Integer.valueOf( SvcConstants.HOME_POL_TYPE ))){
			refundAmount= Double.valueOf(  policyDataVO.getEndorsmentVO().get( 0 ).getPremiumVO().getPremiumAmt()
					- policyDataVO.getEndorsmentVO().get( 0 ).getOldPremiumVO().getPremiumAmt()  ) ;	
			
			if(!Utils.isEmpty(policyDataVO.getPremiumVO().getVatTaxPerc())){
			refundVatAmount=(refundAmount*policyDataVO.getPremiumVO().getVatTaxPerc())/100;
			}
			if(!Utils.isEmpty(policyDataVO.getCommonVO().getIslegacyPolicy())){
				if(policyDataVO.getCommonVO().getIslegacyPolicy())refundVatAmount=0.0;
			}
			refundAmount=refundAmount+refundVatAmount;
	
		}
		else if((policyDataVO.getPolicyType().equals(Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE))) || 
				(policyDataVO.getPolicyType().equals(Integer.valueOf( SvcConstants.LONG_TRAVEL_POL_TYPE))) ||
				(policyDataVO.getPolicyType().equals(Integer.valueOf( SvcConstants.WC_POLICY_TYPE )))) {
				
			refundAmount =  Double.valueOf(  policyDataVO.getEndorsmentVO().get( 0 ).getPremiumVO().getPremiumAmt() -
								policyDataVO.getEndorsmentVO().get( 0 ).getOldPremiumVO().getPremiumAmt()) ;
			
			if(!Utils.isEmpty(policyDataVO.getPremiumVO().getVatTaxPerc())){
	            refundVatAmount=(refundAmount*policyDataVO.getPremiumVO().getVatTaxPerc())/100;
	            }
			if(!Utils.isEmpty(policyDataVO.getCommonVO().getIslegacyPolicy())){
                if(policyDataVO.getCommonVO().getIslegacyPolicy())refundVatAmount=0.0;
            }
			//CTS 17.08.2020 SAT#40973- Jupiter_UW_Co-existence Testing  - Start
            if (policyDataVO.getCommonVO().getLob().toString().equals("WC")
                    && policyDataVO.getCommonVO().getAppFlow().toString().equals("VIEW_POL")
                    && policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.WC_POLICY_TYPE)))                
                refundVatAmount = 0.0;       
            //CTS 17.08.2020 SAT#40973 - Jupiter_UW_Co-existence Testing  - End
			refundAmount=refundAmount+refundVatAmount;
		} else {
			
			refundAmount =  Double.valueOf(  policyDataVO.getEndorsmentVO().get( 0 ).getPremiumVO().getPremiumAmt()
					- policyDataVO.getEndorsmentVO().get( 0 ).getOldPremiumVO().getPremiumAmt()  ) ;			
		}
		
		
		/* Bug fix for refund premium with policy fees */
		if( !Utils.isEmpty( policyDataVO.getPremiumVO().getPolicyFees()))
		{
			if(refundAmount<0)
			{
				refundAmount = refundAmount + policyDataVO.getPremiumVO().getPolicyFees();
			}
			if(refundAmount>0)
			{
				refundAmount = refundAmount - policyDataVO.getPremiumVO().getPolicyFees();
			}
		}
		refundAmount =   Math.abs( refundAmount ) ;
		LOGGER.debug( "Refeund Amount - "+ refundAmount.toString() );
		// refundAmount = DAOUtils.getRefundAmountForCancelPolicy(policyVO,policyVO.getEndorsements().get(0).getOldPremiumVO().getPremiumAmt());
		
		if( policyDataVO.getPolicyType() == Integer.valueOf( SvcConstants.HOME_POL_TYPE ) ){
			policyDataVO.setSectionId( (short) 14 );
		}
		else if( ( policyDataVO.getPolicyType().equals( Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ) ) )
				|| (policyDataVO.getPolicyType().equals(  Integer.valueOf( SvcConstants.LONG_TRAVEL_POL_TYPE )) ) ){
			policyDataVO.setSectionId( (short) 15 );
		}else{		
			policyDataVO.setSectionId( new Short(Utils.getSingleValueAppConfig( policyDataVO.getCommonVO().getLob().toString()+"_SECTION_ID" )).shortValue());
		}
		String sqlQuery;
		// Added equals() instead of == to avoid sonar violation on 25-9-2017
		if(policyDataVO.getPolicyType().equals( Integer.valueOf( SvcConstants.HOME_POL_TYPE )) ||  ( policyDataVO.getPolicyType().equals( Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ) ) )
				|| (policyDataVO.getPolicyType().equals(  Integer.valueOf( SvcConstants.LONG_TRAVEL_POL_TYPE )) ) )
			sqlQuery = "SELECT PKG_PAS_QUO_POL_HOME.fn_get_endt_text_cancel_pol('"+ Integer.valueOf( policyDataVO.getSectionId().toString() ) +"','"+ endExpiryDate +"',"+ refundAmount + com.Constant.CONST_FROM_DUAL2; 
		else
			sqlQuery = "SELECT PKG_PAS_ENDT_GEN_MONOLINE.FN_GET_ENDT_TEXT_CANCEL_POL('"+ Integer.valueOf( policyDataVO.getSectionId().toString() ) +"','"+ endExpiryDate +"',"+ decForm.format( refundAmount ) + com.Constant.CONST_FROM_DUAL2;
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		String endText = "";

		Query query = session.createSQLQuery(sqlQuery);

		List<Object> results = query.list();

		if(!Utils.isEmpty(results)){

		endText = results.get(0).toString();

		}

		return endText;

	}

	@Override
	public BaseVO getCommonEndorsementTextForAmendPolicy( BaseVO baseVO ){
		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endText = generateCommonEndorsementTextForAmendPolicy((PolicyDataVO) baseVO);
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		policyDataVO.setEndorsmentVO( endText );
		
		return baseVO;
	}
	
	/**
	 * Returns endorsementText which is stored in TTrnEndorsementDetail
	 * @param policyDataVO
	 * @return
	 */
	private com.mindtree.ruc.cmn.utils.List<EndorsmentVO> generateCommonEndorsementTextForAmendPolicy(PolicyDataVO policyDataVO) {
		if (LOGGER.isInfo())
		{
			LOGGER.info("Calling generateCommonEndorsementTextForAmendPolicy method of CaptureEndorsementTextDao");
		}
		
		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endtList = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( null );
		List<TTrnEndorsementDetail> tTrnEndorsementDetails = getHibernateTemplate().find("from TTrnEndorsementDetail where id.edlPolicyId=? and id.edlEndorsementId=? and id.edlEndNo=?",policyDataVO.getCommonVO().getPolicyId(),policyDataVO.getCommonVO().getEndtId(),policyDataVO.getCommonVO().getEndtNo());
		
		for(TTrnEndorsementDetail endt : tTrnEndorsementDetails){
			EndorsmentVO endDetail = new EndorsmentVO();
			endDetail.setEndNo( endt.getId().getEdlEndNo() );
			endDetail.setEndtId( endt.getId().getEdlEndorsementId() );
			endDetail.setPolicyId( endt.getId().getEdlPolicyId() );
			endDetail.setSlNo( endt.getId().getEdlSerialNo() );
			endDetail.setEndSecId(endt.getId().getEdlSecId());
			endDetail.setEndText( endt.getEdlText() );
			endtList.add( endDetail );
		}
		return endtList;

	}
	
	/**
	 * This method is used to insert default clauses during the policy endorsement.
	 * @param baseVO
	 */
	private void updateClauses(BaseVO baseVO) {
		if (LOGGER.isInfo())
		{
			LOGGER.info("Calling updateClauses method of CaptureEndorsementTextDao");
		}
		
		//++For Oman Location clauses are not applicable. So calling of this procedure is not required. 
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) && 
				Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){
			return;
		}
		//--For Oman Location clauses are not applicable. So calling of this procedure is not required. 
		
		PolicyVO policyVO = (PolicyVO)baseVO;
		//List<SectionVO> sectionVOList;

		if( !Utils.isEmpty( policyVO ) )
		{

			PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "insertClauses" );
			//Commenting endt text generation for clauses from 3.3 P2 since it is not required as per comments from RSA.
			//PASStoredProcedure sp1 = (PASStoredProcedure) Utils.getBean( "clauseTextGenProc");
			//sectionVOList = policyVO.getRiskDetails();
			//List<Long> secPolicyId= new ArrayList<Long>();
			try{

				Long endtId = SvcUtils.getLatestEndtId( policyVO );
				
				Map<String, Object> results = sp.call( policyVO.getPolLinkingId(), endtId );
				
				if( Utils.isEmpty( results ) ){
					LOGGER.debug( "The result of the stored procedure insertClauses is null" );
				}
				
				/*for(SectionVO sectionvo:sectionVOList)	
				{
					if(!Utils.isEmpty(sectionvo.getPolicyId()) && !secPolicyId.contains( sectionvo.getPolicyId() ) )
					{
						DAOUtils.deletePrevEndtText( sectionvo.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ),Integer.valueOf( Utils.getSingleValueAppConfig( "CONDTIONS_SECTION_ID" ) ), Long.valueOf( "0" ) );

						Map results1 = sp1.call( sectionvo.getPolicyId(), policyVO.getNewEndtId(), SvcUtils.getUserId( policyVO ),
								Integer.valueOf( Utils.getSingleValueAppConfig( "CONDTIONS_SECTION_ID" ) ), Long.valueOf( "0" ) );
						secPolicyId.add(sectionvo.getPolicyId());
						if( Utils.isEmpty( results1 ) )
						{
							LOGGER.debug( "The result of the stored procedure clauseTextGenProc is null" );
						}
					}
					
				}*/
				
			}
			catch( DataAccessException e ){
				throw new BusinessException( "pas.insClauses.exception", e, "An exception occured while executing ins clauses stored proc." );
			}
		}
		
	}

	
	private SectionVO getSectionVO( PolicyVO policyVO, Long policyId ){

		SectionVO basicSection = new SectionVO( RiskGroupingLevel.LOCATION );
		basicSection.setPolicyId( policyId );
		basicSection.setSectionId(Integer.valueOf(SvcConstants.DEFAULT_SECTION_ID_PL ));
		return basicSection;
	}
}
