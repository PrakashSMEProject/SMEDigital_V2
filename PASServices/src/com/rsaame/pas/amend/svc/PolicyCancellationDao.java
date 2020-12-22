package com.rsaame.pas.amend.svc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.List;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;

/**
 * @author M1020278
 *
 */
public class PolicyCancellationDao extends BaseDBDAO implements IPolicyCancellationDao{
	
	private final static Logger LOGGER = Logger.getLogger( PolicyCancellationDao.class );
	private final static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public BaseVO getCancelPolRefundPremium( BaseVO baseVO ){

		// Makes the status of policy as 4 (canceled)  & calls procedure to get the refund premium amount.

		if( LOGGER.isInfo() ) LOGGER.info( "Enterning amend cancel policy method" );

		PolicyDataVO policyDataVO = null;

		try{
			if( !Utils.isEmpty( baseVO ) ){
				
					policyDataVO = (PolicyDataVO) baseVO;

					if( !Utils.isEmpty( policyDataVO ) ){
						List<EndorsmentVO> endorsements = null;

						/* Setting isPolicyToBeCancelled (true) 
						 * to check the flow [AMEND/CANCEL] for display of endorsement Summary in premium page.
						 * check done in PremiumRH */

						endorsements = getEndorsementsForCancelPolicy( policyDataVO );
						if( !Utils.isEmpty( endorsements ) ){
							endorsements.get( 0 ).setPolicyToBeCancelled( true );
						}
						policyDataVO.setEndorsmentVO( endorsements );

					}
				
			}

		}
		catch( DataAccessException accessException ){
			throw new SystemException( "", accessException, "Error while getting the premium refund amount." );
		}

		return policyDataVO;

	}
	
	/**
	 * @param policyDataVO
	 * @return
	 */
	@Override
	public List<EndorsmentVO> getEndorsementsForCancelPolicy( PolicyDataVO policyDataVO ){

		List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
		/* Update endt no and id before loading premium page*/

		
		Long endtId = policyDataVO.getEndtId();
		Long polId = policyDataVO.getPolicyId();
	
		Double refundAmount = 0.0;
		if( !Utils.isEmpty( endtId ) ){
			EndorsmentVO endorsmentVO;
			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();

			/*
			 * After cancellation of policy the premium will be 0, and since the need is to display the 
			 * refund amount before actually cancellation of policy new premium for calculation is taken as 0.0
			 */
			Double newPremiumAmt = 0.0;
			
			java.util.List<Object> valueHolder = null;

			DataHolderVO<HashMap<String, Double>> premiumHolder;
			/*
			 * Due to code fix done for the Ticket -
			 * 117462/117605/117549/120595; there was duplicate Blocks due to
			 * which we had critical Sonar Violation. Hence removed the
			 * duplicate blocks
			 */
			/*
			 * Caching Issue Ticket - 117462/117605/117549/120595 : BEGIN
			 */
			premiumHolder = PremiumHelper.getPremiumForProrate(polId, endtId,policyDataVO.getCommonVO().getLob());
			/*
			 * Caching Issue Ticket - 117462/117605/117549/120595 : END
			 */
			newPremiumAmt = premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM );

			Double currentPremiumAmt = premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM );
			
			double discLoad = 0.0;
			/*if (!policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE))) {//to check for complete refund
				if(!Utils.isEmpty( policyDataVO.getCommonVO().getPremiumVO() ) && !Utils.isEmpty( policyDataVO.getCommonVO().getPremiumVO().getDiscOrLoadPerc() ) && policyDataVO.getCommonVO().getPremiumVO().getDiscOrLoadPerc().compareTo( 0.0 ) !=0 ){
					discLoad = policyDataVO.getCommonVO().getPremiumVO().getDiscOrLoadPerc();
				}
				
				java.util.List<Object> valueHolder =  DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_QUO_POL_HOME.GET_CANCEL_PRORATED_PRM_DISP("+polId+","+endtId+com.Constant.CONST_TO_DATE_END+format.format( policyDataVO.getEndEffectiveDate() )+com.Constant.CONST_DD_MM_YYYY_END+discLoad+com.Constant.CONST_FROM_DUAL, getHibernateTemplate() );
				if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) )){
					refundAmount =  ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue() ;
				}
			}*/
			
			if(!Utils.isEmpty( policyDataVO.getCommonVO().getPremiumVO() ) && !Utils.isEmpty( policyDataVO.getCommonVO().getPremiumVO().getDiscOrLoadPerc() ) && policyDataVO.getCommonVO().getPremiumVO().getDiscOrLoadPerc().compareTo( 0.0 ) !=0 ){
				discLoad = policyDataVO.getCommonVO().getPremiumVO().getDiscOrLoadPerc();
			}
			
			/*Added for short term Cancellation - OMAN*/
			if(!Utils.isEmpty( policyDataVO.getEndorsmentVO() ) && policyDataVO.getEndorsmentVO().get( 0 ).isShortTermCancellation()){
				Double refundAmtPerc=0.0;
				Long policyId = policyDataVO.getCommonVO().getPolicyId();
				PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean("getClaimCountCommon");
				Integer claimCount = 0;
				try {			
					Map results = sp.call(policyId, null);			
					claimCount = Integer.valueOf(results.get("PO_CLAIM_COUNT").toString());
				}
				catch(DataAccessException e) {
					throw new BusinessException("cmn.storedproc.error", e, "Unexpected exception occurred. Please contact Administrator.");
				}
				if(claimCount == 0){
					refundAmtPerc = getRefundPerc(policyDataVO);						
					valueHolder =  DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_QUO_POL_HOME.GET_CANCEL_SHORT_TERM_PRM_DISP("+polId+","+endtId+com.Constant.CONST_TO_DATE_END+format.format( policyDataVO.getEndEffectiveDate() )+com.Constant.CONST_DD_MM_YYYY_END+discLoad+","+refundAmtPerc+com.Constant.CONST_FROM_DUAL2, getHibernateTemplate() );
				}
				policyDataVO.getEndorsmentVO().get( 0 ).setShortTermCancellationPerc( refundAmtPerc );
					
			}
			else if ((SvcConstants.OMAN.toString().equalsIgnoreCase(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))) 
					&& !policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE))) {//to check for complete refund
				//Added the block to retain Oman Logic as both for home and Long Term Travel same Pocedure is called
				if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) 
						|| policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
					valueHolder =  DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_QUO_POL_HOME.GET_CANCEL_PRORATED_PRM_DISP("+polId+","+endtId+com.Constant.CONST_TO_DATE_END+format.format( policyDataVO.getEndEffectiveDate() )+com.Constant.CONST_DD_MM_YYYY_END+discLoad+com.Constant.CONST_FROM_DUAL2, getHibernateTemplate() );
					else
						valueHolder =  DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_CANCEL_POLICY_MONOLINE.GET_CANCEL_PRORATED_PRM_DISP("+polId+","+endtId+com.Constant.CONST_TO_DATE_END+format.format( policyDataVO.getEndEffectiveDate() )+com.Constant.CONST_DD_MM_YYYY_END+discLoad+com.Constant.CONST_FROM_DUAL2, getHibernateTemplate() );	
				
			}else if ( !policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE))) {//to check for complete refund
				
				if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)))
					valueHolder =  DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_QUO_POL_HOME.GET_CANCEL_PRORATED_PRM_DISP("+polId+","+endtId+com.Constant.CONST_TO_DATE_END+format.format( policyDataVO.getEndEffectiveDate() )+com.Constant.CONST_DD_MM_YYYY_END+discLoad+com.Constant.CONST_FROM_DUAL2, getHibernateTemplate() );
					else
						valueHolder =  DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_CANCEL_POLICY_MONOLINE.GET_CANCEL_PRORATED_PRM_DISP("+polId+","+endtId+com.Constant.CONST_TO_DATE_END+format.format( policyDataVO.getEndEffectiveDate() )+com.Constant.CONST_DD_MM_YYYY_END+discLoad+com.Constant.CONST_FROM_DUAL2, getHibernateTemplate() );	
				
			}
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) )){
				refundAmount =  ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue() ;
			}
			if( refundAmount != 0.0 ){
				newPremiumAmt = refundAmount;
			}
			else{
				newPremiumAmt = 0.0;
			}

			newPremiumVO.setPremiumAmt( newPremiumAmt ); // New premium amount.
			oldPremiumVO.setPremiumAmt( currentPremiumAmt ); // Old premium amount.

			Map<?,?> results;
			if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
					policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
			results = DAOUtils.endFlowGeneralInfoHomeTravel( policyDataVO.getPolicyId(), policyDataVO.getCommonVO().getIsQuote() );
			else
				results = DAOUtils.endFlowGeneralInfoMonoline(policyDataVO.getPolicyId());
			endorsmentVO = constructEndorsmentVO( policyDataVO, oldPremiumVO, newPremiumVO, results );
			endorsements.add( endorsmentVO );

		}
		LOGGER.debug( "Refund Amount is - "+refundAmount );
		return endorsements;

	}
	
	/**
	 * 
	 * @param policyDataVO
	 * @param oldPremiumVO
	 * @param newPremiumVO
	 * @param results
	 * @return
	 */
	private EndorsmentVO constructEndorsmentVO(PolicyDataVO policyDataVO,
			PremiumVO oldPremiumVO, PremiumVO newPremiumVO, Map<?,?> results) {
		
		EndorsmentVO endorsmentVO = new EndorsmentVO();
		endorsmentVO.setOldPremiumVO( oldPremiumVO );
		endorsmentVO.setPremiumVO( newPremiumVO );
		endorsmentVO.setPolicyId(policyDataVO.getPolicyId());
		endorsmentVO.setEndtId(Long.valueOf(results.get("p_endt_id").toString()));
		endorsmentVO.setEndNo(Long.valueOf(results.get("p_endt_no").toString()));
		endorsmentVO.setSlNo( 1 );
		/*Added for short term Cancellation - OMAN*/
		if(!Utils.isEmpty( policyDataVO.getEndorsmentVO()) ){
			endorsmentVO.setShortTermCancellation(policyDataVO.getEndorsmentVO().get( 0 ).isShortTermCancellation());
			endorsmentVO.setShortTermCancellationPerc(policyDataVO.getEndorsmentVO().get( 0 ).getShortTermCancellationPerc());
		}
		
		return endorsmentVO;
	}
	
	
	
	@Override
	public BaseVO processCancelPolicy( BaseVO baseVO ){
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		Double newPremium = 0.0;
		PASStoredProcedure sp = null;
		Integer userId = SvcUtils.getUserId( policyDataVO );
		
		Double refPremium = 0.0;
		Double discPerc = 0.0;
		Double discAmount = 0.0;
		EndorsmentVO endorsementVO = null;
		//populate EndorsementVo
		//getCancelPolRefundPremium( policyDataVO );
		
		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		/*
		 * In case of an exception during save the ThreadLevelContext will not be cleared, to clear it the next time the user saves
		 * in the same session clear the ThreadLevelContextfirst and proceed
		 */
		//ThreadLevelContext.clearAll();
		fetchEndtId(policyDataVO);
				
		/*Below code added to get endorsement id for storing endorsement text during cancellation*/
		/* Commenting the call to fetchEndtId() as it is added in the method getEndorsementsForCancelPolicy()*/
		//DAOUtils.fetchEndtId( policyDataVO );
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
		sp = (PASStoredProcedure) Utils.getBean( "cancelPolicyHomeTravelProc" );
		else
			sp = (PASStoredProcedure) Utils.getBean( "cancelPolicyMonolineProc" );
		
		if( LOGGER.isInfo() ) LOGGER.info( "Invoking CANCEL_POLICY procedure with inputs {[" );
		/*Extra parameter added for cancellation procedure - EndorsementID*/
		/*
		 * For endt the signature is changed to take in endt start date. 
		 */
		/*
		 * For cancellation the the validity start date is sysdate date, because for cancellation we call the del location proc where vsd is passed from application.
		 * ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE)
		 * SvcUtils.getUserId( policyDataVO )
		 * 
		 */
		/*
		 * Validity start date is fetched from DB, which will take care of Month closing and accounting 
		 */
		
		
		//AMEND_POL is passed as the appFlow just to calculate the VSD. VSD is calculated only if the flow is AMEND_POL
		Date vsd = DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), Flow.AMEND_POL );
		//Oman ShortTerm Cancellation
		discPerc = policyDataVO.getPremiumVO().getDiscOrLoadPerc();
		//Added by Anveshan
		if(!Utils.isEmpty(policyDataVO.getEndorsmentVO()) && !Utils.isEmpty(policyDataVO.getEndorsmentVO().get( 0 ))){
			endorsementVO = policyDataVO.getEndorsmentVO().get( 0 );
			refPremium = endorsementVO.getOldPremiumVO().getPremiumAmt();
			
			if(!Utils.isEmpty(endorsementVO.getPremiumVO()) && !Utils.isEmpty(endorsementVO.getPremiumVO().getPremiumAmt())) {
				newPremium = endorsementVO.getPremiumVO().getPremiumAmt();
			}
			else if(!Utils.isEmpty(endorsementVO.getCanPremiumVO()) && !Utils.isEmpty(endorsementVO.getCanPremiumVO().getPremiumAmt())) {
				newPremium = endorsementVO.getCanPremiumVO().getPremiumAmt();
			}
			if(!Utils.isEmpty(endorsementVO.getPremiumVO()) && !Utils.isEmpty(endorsementVO.getPremiumVO().getDiscOrLoadAmt())) {
				discAmount = endorsementVO.getPremiumVO().getDiscOrLoadAmt().doubleValue();
			}
			else if(!Utils.isEmpty(endorsementVO.getCanPremiumVO()) && !Utils.isEmpty(endorsementVO.getCanPremiumVO().getDiscOrLoadAmt())) {
				discAmount = endorsementVO.getCanPremiumVO().getDiscOrLoadAmt().doubleValue();
			}
			discPerc = newPremium/(newPremium+refPremium) * 100;
			discPerc = Math.round(discPerc*1000000.0)/1000000.0;
			System.out.println(" This is cancellation premium added by Anveshan: newPremium :: "+newPremium +"  discAmount :: "+discAmount);
		}
		Double refundPerc= 999.0;
		if(!Utils.isEmpty(policyDataVO.getEndorsmentVO()) && policyDataVO.getEndorsmentVO().get( 0 ).isShortTermCancellation() ){
			refundPerc = policyDataVO.getEndorsmentVO().get( 0 ).getShortTermCancellationPerc();
		}
		
		Map results = null;
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
			results = sp.call( policyDataVO.getPolicyId(), vsd , newPremium, userId, policyDataVO.getEndtId() , policyDataVO.getEndEffectiveDate() , discPerc,discAmount,refundPerc);
		else
			results = sp.call( policyDataVO.getPolicyId(), vsd , newPremium, userId, policyDataVO.getEndtId() , policyDataVO.getEndEffectiveDate() , policyDataVO.getPremiumVO().getDiscOrLoadPerc(),refundPerc);
		
		
		/*
		 * Update the VSD to CommonVO, which will be used for page reload.
		 * 
		 */
		
		policyDataVO.getCommonVO().setVsd( vsd );
		
		long entId = (Long) getHibernateTemplate().find( "select max(pol.id.polEndtId) from TTrnPolicyQuo pol where pol.id.polPolicyId=?", policyDataVO.getPolicyId() ).get( 0 );
		java.util.List<TTrnPolicyQuo> cancledPolRecs = getHibernateTemplate().find( "from TTrnPolicyQuo pol where pol.id.polPolicyId=? and pol.id.polEndtId =? ", policyDataVO.getPolicyId(),entId); 
		
		for( TTrnPolicyQuo cancledPolRec : cancledPolRecs ){
			cancledPolRec.setPolModifiedDt( getSysDate() );
			cancledPolRec.setPolModifiedBy(userId);
			/*
			 * Licensed by /approved by fields should capture the whoever has endorsed the policy.
			 */
			//142244 Vat amount
			if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
					policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE))
					|| policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.WC_POLICY_TYPE)))
			{
				
				if( !Utils.isEmpty(policyDataVO.getPremiumVO().getVatTax() ) ){
					cancledPolRec.setPolVatTax((BigDecimal.valueOf(policyDataVO.getPremiumVO().getVatTax()))); 
				}
			}
			if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.WC_POLICY_TYPE))){
				cancledPolRec.setPolVattableAmt((BigDecimal.valueOf(policyDataVO.getPremiumVO().getVatablePrm())));
			}
			if( !SvcUtils.getUserId( policyDataVO).equals( cancledPolRec.getPolApprovedBy() ) ){
				cancledPolRec.setPolApprovedBy(userId); 
			}
			if( !SvcUtils.getUserId( policyDataVO).equals( cancledPolRec.getPolUserId())){
				cancledPolRec.setPolUserId(userId );
			}
			getHibernateTemplate().saveOrUpdate( cancledPolRec );

		}
		
		java.util.List<TMasCashCustomerQuo> cancledRecs =  getHibernateTemplate().find(
				"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId in (select distinct (pol.id.polPolicyId) from TTrnPolicyQuo pol where pol.id.polPolicyId = ? and pol.id.polEndtId = ? ) " +
				"and cashCustomerQuo.id.cshValidityStartDate = ( select distinct ( pol.polValidityStartDate ) from TTrnPolicyQuo pol where pol.id.polPolicyId = ? and pol.id.polEndtId = ? )",
				policyDataVO.getPolicyId(), entId,policyDataVO.getPolicyId(), entId);
		
		for(TMasCashCustomerQuo cancledRec: cancledRecs){
			cancledRec.setCshModifiedBy( userId );
			cancledRec.setCshModifiedDt( getSysDate() );
			getHibernateTemplate().saveOrUpdate( cancledRec );
		}
		
		if( LOGGER.isInfo() ) LOGGER.info( "Execution of CANCEL_POLICY completed [result =" + results );
		
		EndorsmentVO endorsmentVO = policyDataVO.getEndorsmentVO().get(0);
		/*
		 * In case of cancellation, the policy id will not be available in policy VO
		 */
		endorsmentVO.setPolicyId(policyDataVO.getPolicyId());
		
		endorsmentVO.setEndtId(entId);
		/* Set audit details in order to update the same within T_TRN_ENDORSEMENT_DETAIL table */
		endorsmentVO.setRecCreatedBy( userId );
		endorsmentVO.setRecCreaDate( getSysDate() );
		// Setting the endorsement text again after calling the CANCEL_POLICY procedure.
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
		sp = (PASStoredProcedure) Utils.getBean( "valExpDatesHomeTravel" );
		else
			sp = (PASStoredProcedure) Utils.getBean( "valExpDatesmonoline" );
		try{
			sp.call( policyDataVO.getPolicyId(), entId , getSysDate() ); 
		}catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc." );
		}
		
		getHibernateTemplate().flush();
		
		
		
		/* To insert record to T_TRN_ENDORSEMENT table  */
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
		sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSPHomeTravel");
		else
			sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSPMonoline");
		try{
			sp.call( policyDataVO.getPolicyId(), entId  , userId ,policyDataVO.getEndEffectiveDate()); 
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.ConfirmEndt.exception", e, "An exception occured while inserting into T_TRN_ENDORSEMENT." );
		}
		
		
		/*Procedure call added to insert credit details in case of cancellation*/
		
		PASStoredProcedure sproc;
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
		sproc = (PASStoredProcedure) Utils.getBean("insertPaymentDetailsSPHomeTravel");
		else
		sproc = (PASStoredProcedure) Utils.getBean("insertMonolinePaymentDetailsSP");
		
		if( LOGGER.isInfo() ) LOGGER.info( "Invoking insertPaymentDetailsSP procedure with inputs {[" );
		Double refundAmt = 0.0;
		if(policyDataVO.getEndorsmentVO().get( 0 ).isShortTermCancellation() ){			
			refundAmt = PremiumHelper.getRefundAmountForShortTerm(refundPerc,endorsmentVO.getOldPremiumVO().getPremiumAmt());
		}
		else{
			refundAmt = PremiumHelper.getRefundAmountForCancelPolicy(policyDataVO, endorsmentVO.getOldPremiumVO().getPremiumAmt());
		}
		
		if(!Utils.isEmpty( endorsmentVO.getPremiumVO() ) && !Utils.isEmpty( endorsmentVO.getPremiumVO().getPremiumAmt() )){
			refundAmt =  endorsmentVO.getOldPremiumVO().getPremiumAmt() - endorsmentVO.getPremiumVO().getPremiumAmt(); 
		}
		results = sproc.call(policyDataVO.getPolicyId() , entId, refundAmt, null,null,null,null,"N",null); //policyDataVO.getEndtId()
		if( LOGGER.isInfo() ) LOGGER.info( "Execution of insertPaymentDetailsSP completed [result =" + results );
		/* To insert record to T_TRN_ENDORSEMENT_DETAIL table  */
		TaskExecutor.executeTasks("CAPTURE_ENDORSEMENT_TEXT_UPDATE", policyDataVO );
		
		/* Update endtId obtained for processing to both fields within policyDataVO i.e. policyDataVO.endtId and policyDataVO.newEndtId in order
		 * to avoid discrepancies in reports immediately after endorsement confirmation */
		
		policyDataVO.setEndtId(entId);
		policyDataVO.getCommonVO().setEndtNo(endorsmentVO.getEndNo() );
		return policyDataVO;
		
	}
	
	/**
	 * Returns endorsementText which is generated by invoking the function pkg_endt_gen.get_endt_text_cancel_pol
	 * @param policyDataVO
	 * @return
	 */
	private String generateEndorsementTextForCancelPolicy(PolicyDataVO policyDataVO) {
		
		DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT ); 

		Double refundAmount = 0.0;

		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );

		String endExpiryDate = sdf.format(policyDataVO.getEndEffectiveDate());
		// Change made to get the new Endorsment ID to get the New and Old Annualized Premium.
		//Long endtIdToProcess = policyDataVO.getEndtId();
		//DAOUtils.getEndtToProcess(getHibernateTemplate(),policyDataVO);
		/*
		 * Caching Issue    
		 * Ticket - 117462/117605/117549/120595 : BEGIN
		 */
		DataHolderVO<HashMap<String, Double>> premiumHolder =  PremiumHelper.getPremiumForProrate(policyDataVO.getPolicyId(), policyDataVO.getEndtId(),policyDataVO.getCommonVO().getLob());
		/*
		 * Caching Issue    
		 * Ticket - 117462/117605/117549/120595 : END
		 */
		
		refundAmount =  premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM )  -   premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM ) ;
		refundAmount =   Math.abs( refundAmount ) ;

		String sqlQuery = "SELECT PKG_ENDT_GEN.get_endt_text_cancel_pol('" + endExpiryDate + "'," + decForm.format( refundAmount ) + ") FROM DUAL";

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		String endText = "";

		Query query = session.createSQLQuery( sqlQuery );

		java.util.List<Object> results =  query.list();

		if( !Utils.isEmpty( results ) ){

		endText = results.get(0).toString();
		if( !endText.equals( policyDataVO.getEndorsmentVO().get( 0 ).getEndText() ) ){
			endText = policyDataVO.getEndorsmentVO().get( 0 ).getEndText();
		}

		}

		return endText;

	}
	
	
	/**
	 * @param policyDataVO
	 */
	private void fetchEndtId(PolicyDataVO policyDataVO) {
		
		Map results = null;
		
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE)) || 
				policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) || policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE)))
			results = DAOUtils.endFlowGeneralInfoHomeTravel( policyDataVO.getPolicyId(), policyDataVO.getCommonVO().getIsQuote() );
		else
			results = DAOUtils.endFlowGeneralInfoMonoline( policyDataVO.getPolicyId());
			policyDataVO.setEndtId( Long.valueOf( results.get( "p_endt_id" ).toString() ) );
			
	}
	
	/*Added for short term Cancellation - OMAN: returns refund percentage*/
	public Double getRefundPerc( PolicyDataVO policyDataVO ){
		Double refundAmtPerc =null;
		int days =  (int) ( (policyDataVO.getEndEffectiveDate().getTime() - policyDataVO.getStartDate().getTime()) / (1000 * 60 * 60 * 24) );
		int month = 0;
		/*if(days < 0){
			month = 0;
		}*/
		//month = days>=0?(days>0?(days - 1)/30+1: 1): 0;
		month = days>0?(days - 1)/30+1: 0;
		if(month> 0 && month <= 2){
			month = 2;
		}
		if(month > 6){
			month = 999;
		}
		
		/*if(policyDataVO.getEndEffectiveDate().before( policyDataVO.getStartDate() )){
			//refundAmtPerc =  0.75;
			month = 0;
		}*/
		if(month == 0 || (policyDataVO.getPolicyType().equals( 31 ) && month > 0) ){
			String lookupStr = SvcUtils.getLookUpDescription( "ST_CANC_PERC", "ALL",( (UserProfile)policyDataVO.getCommonVO().getLoggedInUser()).getRsaUser().getHighestRole(), month );
			if(!Utils.isEmpty(lookupStr))
				refundAmtPerc = Double.valueOf( lookupStr );
			if(Utils.isEmpty(refundAmtPerc))
			{
				refundAmtPerc = Double.valueOf( SvcUtils.getLookUpDescription( "ST_CANC_PERC", "ALL", "ALL", month ) );
			}
		}
		return refundAmtPerc;
	}
	
}
