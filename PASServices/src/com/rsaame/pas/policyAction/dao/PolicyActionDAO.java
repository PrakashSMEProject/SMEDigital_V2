/**
 * 
 */
package com.rsaame.pas.policyAction.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnTask;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.renewals.dao.RenewalsDAO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.cmn.CommonVO;


/**
 * @author m1014644
 *
 */
public class PolicyActionDAO extends BaseDBDAO implements IPolicyActionDAO{
	private static final Logger LOGGER = Logger.getLogger( PolicyActionDAO.class );
	private final static Byte COMPLETED_STATUS = 2;
	private final static Integer QUOTE_ACCEPT = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACCEPT" ) );
	private final static Integer QUOTE_REJECT = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REJECT" ) );
	private final static Integer QUOTE_DECLINED = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_DECLINED" ) );
	private final static Integer QUOTE_ACTIVE = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) );

	/* (non-Javadoc)
	 * @see com.rsaame.pas.policyAction.dao.IPolicyActionDAO#declineQuote(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO declineQuote( BaseVO baseVO ){
		updatePolicyRecAuthDetails( baseVO , QUOTE_DECLINED );
		updateTask( baseVO );
		referalDetails(baseVO , QUOTE_DECLINED );
		return baseVO;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.policyAction.dao.IPolicyActionDAO#rejectQuote(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO rejectQuote( BaseVO baseVO ){
		updatePolicyRecAuthDetails( baseVO , QUOTE_REJECT );
		updateTask( baseVO );
		return baseVO;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.policyAction.dao.IPolicyActionDAO#approveQuote(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO approveQuote( BaseVO baseVO ){
		updatePolicyRecAuthDetails( baseVO , QUOTE_ACCEPT  );
		updateTask( baseVO );
		referalDetails(baseVO , QUOTE_ACCEPT );
		
		PolicyCommentsHolder commentsHolder = (PolicyCommentsHolder)baseVO;
		DAOUtils.saveTradeLicNo( commentsHolder.getPolicyDetails() , getHibernateTemplate());
		
		return baseVO;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.policyAction.dao.IPolicyActionDAO#issueQuote(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO issueQuote( BaseVO baseVO ){
		
		updateClauses( baseVO );
		PolicyCommentsHolder polComHolder = (PolicyCommentsHolder) baseVO;
		PolicyVO policyVO = polComHolder.getPolicyDetails();
		policyVO.setStatus( QUOTE_ACTIVE );
				
		//handleDiscLoadChange( policyVO , SvcUtils.getLatestEndtId(policyVO) );
		
		DAOUtils.saveTradeLicNo( policyVO , getHibernateTemplate() );
		
		
		//handleDiscLoadChange( policyVO , SvcUtils.getLatestEndtId(policyVO) );
		
		DAOUtils.saveTradeLicNo( policyVO , getHibernateTemplate() );
		
		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean("updateQuoteTableStatus");
		try{	
			Map results = sp.call(policyVO.getPolLinkingId() , SvcUtils.getLatestEndtId( policyVO ) );
			if (Utils.isEmpty(results)) {
				LOGGER.debug("The result of the stored procedure is null");
			}
		} catch (DataAccessException e){
			throw new BusinessException("pas.updateQuoteTableStatus.exception",e, "An exception occured while executing stored proc.");
		}
			
		/* 1. PRO_UPD_VSD_VED procedure is called to update VSD of the current endorsement record in case there is need to 
		 * 	  change which is usually when VSD passed to proc is greater than T_TRN_CLOSING table clo_closed_date value 
		 * 2. After updating VSD this procedure updates same new VSD as expiry date to older records of current endt id */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow()) );
		
		sp = (PASStoredProcedure) Utils.getBean( "updateVSDVEDQuote" );
		try{
			sp.call( policyVO.getPolLinkingId(), SvcUtils.getLatestEndtId( policyVO ), ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ));
		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.storedproc.error", e, "An error occured while executing UpdateVSDVEDQuote procedure" );
		}
		updatePolicyRecAuthDetails( baseVO , QUOTE_ACTIVE );
		
		Long endtIdToProcess = DAOUtils.getEndtToProcess( getHibernateTemplate(), policyVO );
		
		/*
		 * After the quote is issued, the endt id's are swapped and status is chnaged in policy vo. 
		 */
		if( !Utils.isEmpty( policyVO.getEndtId() ) && !Utils.isEmpty( policyVO.getNewEndtId() ) ){
			policyVO.setEndtId( endtIdToProcess );
		}
		policyVO.setNewEndtId( null );
		
		return baseVO;
	}


	private void updateClauses(BaseVO baseVO) {
		
		PolicyCommentsHolder polComHolder = (PolicyCommentsHolder) baseVO;
		PolicyVO policyVO = polComHolder.getPolicyDetails();
		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean("insClauses");
		
		try{
			
			Long endtId;
			endtId = SvcUtils.getLatestEndtId( policyVO );
			Map results = sp.call(policyVO.getPolLinkingId() , endtId );
			if (Utils.isEmpty(results)) {
				LOGGER.debug("The result of the stored procedure is null");
			}
		} catch (DataAccessException e){
			throw new BusinessException("pas.insClauses.exception",e, "An exception occured while executing ins clauses stored proc.");
		}
		
		
		
	}
	
	private short getBaseClassCode(List<TTrnPolicyQuo> policyQuoList)
	{
		short baseClassCode = 7;
		for( TTrnPolicyQuo policyQuo : policyQuoList )
		{
			if(policyQuo.getPolClassCode() == 2)
			{
				baseClassCode = 2;
				break;
			}
		}
		return baseClassCode;
	}
	/**
	 * Updates policy table authorization details such as modified date and updates premium value for each of the class codes in
	 * T_TRN_POLICY_QUO table 
	 * @param baseVO
	 * @param polStatus
	 */
	private void updatePolicyRecAuthDetails( BaseVO baseVO, Integer polStatus ){

		/* Clear all values that have been added to the ThreadLevelContext */
		/*
		 * In case of an exception during save the ThreadLevelContext will not be cleared, to clear it the next time the user saves
		 * in the same session clear the ThreadLevelContextfirst and proceed
		 */
		//clearThreadLevelContext();
		
		/* Let us get the system date right now and use from here on for this transaction. */
		//ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, getSysDate() );
		
		PolicyCommentsHolder polComHolder = (PolicyCommentsHolder) baseVO;
		PolicyVO policyVO = polComHolder.getPolicyDetails();
		boolean policyFeeTobeSet = false;
		short baseClassCode = 0;

		Long endtId = SvcUtils.getLatestEndtId( policyVO );
		String[] statusResReferral = Utils.getMultiValueAppConfig("STATUS_RESOLVE_REFERRAL", ",");
		
		List<TTrnPolicyQuo> policyQuoList ;
		
		if( !Utils.isEmpty( statusResReferral ) && statusResReferral.length > 0
				&& CopyUtils.asList( statusResReferral ).contains( polStatus.toString() )){
		
			/* Fetch only the latest endorsementId records to update status */
			policyQuoList = getHibernateTemplate().find(
					"from TTrnPolicyQuo policyQuo where policyQuo.polLinkingId=? and policyQuo.id.polEndtId=? and policyQuo.polValidityExpiryDate=? and policyQuo.polPolicyType=?", policyVO.getPolLinkingId(),
					endtId,SvcConstants.EXP_DATE, Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_TYPES ) ) );
		
		}else{
			policyQuoList = getHibernateTemplate().find(
					com.Constant.CONST_FROM_TTRNPOLICYQUO_POLICYQUO_WHERE_POLICYQUO_POLLINKINGID_AND_POLICYQUO_ID_POLENDTID_OR_POLICYQUO_ID_POLENDTID_AND_POLICYQUO_POLVALIDITYEXPIRYDATE_AND_POLICYQUO_POLPOLICYTYPE_END, policyVO.getPolLinkingId(),
					endtId,endtId,SvcConstants.EXP_DATE, Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_TYPES ) ) );

		}
		//Double policyFees = ( policyVO.getPremiumVO().getPolicyFees() ) / policyQuoList.size();
		Double policyFees = ( policyVO.getPremiumVO().getPolicyFees() );
		AuthenticationInfoVO authVO = policyVO.getAuthInfoVO();
		com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
		baseClassCode = getBaseClassCode( policyQuoList);
		for( TTrnPolicyQuo policyQuo : policyQuoList ){
			
			
			
			/*VAT*/
			// Ticket : 156700
			policyQuo.setPolvatCode(policyVO.getPolVATCode());
			policyQuo.setPolVatTaxPerc(DAOUtils.getVATRateSBS(policyVO.getPolVATCode(), null));
			policyQuo.setPolVatTax(new BigDecimal(policyVO.getPremiumVO().getVatTax()));
			
			policyQuo.setPolGovernmentTax( converter.getAFromB( policyVO.getPremiumVO().getGovtTax() ) );
			//policyQuo.setPolPolicyFees( converter.getAFromB( policyFees ) );
			policyQuo.setPolModifiedBy(SvcUtils.getUserId(policyVO));
			policyQuo.setPolApprovedInd("Y"); // The status of the quote is approved and the indicator is set to Y
			//Closing date :
			//policyQuo.setPolModifiedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
			policyQuo.setPolModifiedDt( getSysDate() );
			
			policyQuo.setPolPrintDate( null );
			
			/* If Quote status is active then we need not update Approved By,Licensed By id's and approved date */
			 
			if( polStatus == QUOTE_ACTIVE ){
				
				//TODO - To be revisted based on oman changes.
				if(Utils.isEmpty( policyQuo.getPolApprovedBy())){
					policyQuo.setPolApprovedBy( SvcUtils.getUserId( policyVO ) );
					
					if( !Utils.isEmpty( policyVO.getAuthInfoVO() ) && !Utils.isEmpty( policyVO.getAuthInfoVO().getLicensedBy() ) ){
						policyQuo.setPolUserId( policyVO.getAuthInfoVO().getLicensedBy() );
					}
					else{
						policyQuo.setPolUserId( SvcUtils.getUserId( policyVO ) );
					}
				//policyQuo.setPolApprovalDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
				}
				
				if( Utils.isEmpty( policyQuo.getPolApprovalDate() ) ){
					policyQuo.setPolApprovalDate( getSysDate() );
				}
				
				
			}
			/*
			 * Licensed by /approved by fields should capture the whoever has issued the quote.
			 */
					
			if( polStatus == QUOTE_ACCEPT ){
				policyQuo.setPolApprovedBy( SvcUtils.getUserId( policyVO) );
			
				policyQuo.setPolApprovalDate( getSysDate() );
			}
					
				
			getHibernateTemplate().flush();
			//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
			if(!Utils.isEmpty(policyVO.getAuthInfoVO().getRefPolicyNo())){
			if(polStatus == QUOTE_REJECT){
				String rejectComments = null;
				policyQuo.setPolRenewalBasis(Integer.valueOf(Utils.getSingleValueAppConfig("JLT_RENEWAL_BASIS_DECLINED")));
				if(!Utils.isEmpty(polComHolder.getComments().getReasonCode())){
					rejectComments = DAOUtils.getPolicyCommentFromReasonCode(polComHolder.getComments().getReasonCode());
					policyQuo.setPolRenTermTxt(rejectComments);
				}else{
					policyQuo.setPolRenTermTxt(polComHolder.getComments().getComment());
				}
			}else if(polStatus == QUOTE_DECLINED){
				policyQuo.setPolRenewalBasis(Integer.valueOf(Utils.getSingleValueAppConfig("JLT_RENEWAL_BASIS_DECLINED")));
				if(!Utils.isEmpty(polComHolder.getComments().getComment())){
					policyQuo.setPolRenTermTxt(polComHolder.getComments().getComment());
				}
				
			}
			}
			
			//  CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
			if(policyQuo.getPolClassCode() == baseClassCode)
			{
				PremiumHelper.updatePolicyFee(policyVO,policyQuo,getHibernateTemplate());
				policyQuo.setPolPolicyFees( converter.getAFromB( policyFees ) );
			}
			
			PremiumHelper.updateSpecialCovers(policyVO,policyQuo,getHibernateTemplate());
			PremiumHelper.updateGovtTax( policyVO, policyQuo, getHibernateTemplate() );
			//142244:Vat Implementation 
			PremiumHelper.updateVatTax( policyVO, policyQuo, getHibernateTemplate() );
			//Oman Change. Check for minimum premium
			
			minPrmValidation(policyVO);
			
			/*
			 * POL_PREMIUM needs to be updated with class level premium. This method will fetch the premium for a class from premium table and update the 
			 * policy table 
			 */
			PremiumHelper.updatePolicyPremium(policyVO,policyQuo,getHibernateTemplate() );
			
			
			com.rsaame.pas.cmn.converter.IntegerByteConverter byteConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			if(!Utils.isEmpty( polComHolder)&& !Utils.isEmpty( polComHolder.getComments())&&!Utils.isEmpty( polComHolder.getComments().getPolicyStatus()) ){
				policyQuo.setPolStatus(polComHolder.getComments().getPolicyStatus());
			}
			else{
				policyQuo.setPolStatus( byteConverter.getBFromA( polStatus ));
			}
			//policyQuo.setPolValidityStartDate((Date)ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE));
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)) && 
					Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION).equalsIgnoreCase("20") && policyQuo.getPolPolicyType() == 50)
			{
				policyQuo.setPolAutoCession('Y');
			}
			getHibernateTemplate().merge( policyQuo );
			
			authVO.setAccountingDate( policyQuo.getPolIssueDate() );
			
			LOGGER.info( "Policy Id :"+policyQuo.getId().getPolPolicyId() );
		}
		
		/* Clear all values that have been added to the ThreadLevelContext */
		//clearThreadLevelContext();

	}

	//Oman Chnage for min premium validation
	private void minPrmValidation( PolicyVO policyVO ){

		if( !Utils.isEmpty( Utils.getSingleValueAppConfig( SvcConstants.MIN_PRM_CHECK_REQ ) )
				&& Utils.getSingleValueAppConfig( SvcConstants.MIN_PRM_CHECK_REQ ).equalsIgnoreCase( "true" ) ){
			BigDecimal totalPrm = PremiumHelper.getPrm( PremiumHelper.getLatestTotalPrmQuery( policyVO ) , getHibernateTemplate().getSessionFactory().getCurrentSession() );// BigDecimal.valueOf( Long.valueOf( PremiumHelper.getLatestTotalPrmQuery( policyVO ) ) );

			BigDecimal discOrLoadAmt = SvcUtils.getNonNull( BigDecimal
					.valueOf( ( SvcUtils.getNonNull( totalPrm ).doubleValue() * policyVO.getPremiumVO().getDiscOrLoadPerc() ) / 100 ) );
			totalPrm = totalPrm.add( discOrLoadAmt );
			if( !Utils.isEmpty( Utils.getSingleValueAppConfig( SvcConstants.MIN_PREMIUM ) ) ){
				//BigDecimal minPrm = BigDecimal.valueOf( Long.valueOf( Utils.getSingleValueAppConfig( SvcConstants.MIN_PREMIUM ) ) );
				BigDecimal minPrm = BigDecimal.ZERO;
				LookUpListVO minprmList = SvcUtils.getLookUpCodesList( "MIN_PRM", policyVO.getScheme().getTariffCode().toString(), SvcConstants.LOOKUP_LEVEL2 ) ;
				List<String> secList = new ArrayList<String>();
				for (SectionVO sectionVO: policyVO.getRiskDetails()) {
					secList.add(sectionVO.getSectionId().toString());
				}
				
				for(LookUpVO minPrmSec : minprmList.getLookUpList()  ){
					if(secList.contains(minPrmSec.getDescription())){
						minPrm = minPrm.add( minPrmSec.getCode() );
					}
				}
				
				if( totalPrm.compareTo( minPrm ) < 0 ){
					
					throw new BusinessException("pas.min.prm", null , "Total payable premium is less than minimum premium");
				}
			}

		}

	}

	private void handleDiscLoadChange( PolicyVO policyVO, Long endtId ){

		HibernateTemplate ht = getHibernateTemplate();

		List<TTrnPolicyQuo> policyQuoList = ht
				.find( com.Constant.CONST_FROM_TTRNPOLICYQUO_POLICYQUO_WHERE_POLICYQUO_POLLINKINGID_AND_POLICYQUO_ID_POLENDTID_OR_POLICYQUO_ID_POLENDTID_AND_POLICYQUO_POLVALIDITYEXPIRYDATE_AND_POLICYQUO_POLPOLICYTYPE_END,
						policyVO.getPolLinkingId(), endtId, endtId, SvcConstants.EXP_DATE, Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_TYPES ) ) );

		short discLoadCover = policyVO.getPremiumVO().getDiscOrLoadPerc() > 0.0 ? SvcConstants.SC_PRM_COVER_LOADING : SvcConstants.SC_PRM_COVER_DISCOUNT;

		/* Set all required data into policyVO and ThreadLevelContent before calling the procedure to fetch endtId.*/
		/*ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow() ) );
		policyVO.setNewValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		policyVO.setValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, policyVO.getNewValidityStartDate() );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID, policyVO.getNewEndtId() );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_NO, policyVO.getNewEndtNo() );*/

		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow()) );
		if (Utils.isEmpty(policyVO.getNewEndtId() ) ){
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
			}
			else{
			ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, policyVO.getNewValidityStartDate() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID, policyVO.getNewEndtId() );
		}
		

		for( TTrnPolicyQuo policyQuo : policyQuoList ){

			List<TTrnPremiumQuo> premiumQuos = ht
					.find( "from TTrnPremiumQuo prm where prm.prmValidityExpiryDate= ? and prm.id.prmPolicyId = ? and prm.prmEndtId <= ? and prm.prmClCode = ? and prm.id.prmCovCode = ? and prm.id.prmBasicRskCode=? order by prm.prmEndtId desc",
							SvcConstants.EXP_DATE, policyQuo.getId().getPolPolicyId(), endtId, policyQuo.getPolClassCode(), discLoadCover,
							Integer.valueOf( Utils.getSingleValueAppConfig( "SPECIAL_CODE" ) ) );

			if( !Utils.isEmpty( premiumQuos ) ){
				TTrnPremiumQuo premiumQuo = premiumQuos.get( 0 );

				/* Capture version if the discount/loading has changed. */
				if( premiumQuo.getId().getPrmCovCode() == discLoadCover
						&& endtId != 0
						&& premiumQuo.getPrmRate().setScale( 0, BigDecimal.ROUND_CEILING )
								.compareTo( BigDecimal.valueOf( policyVO.getPremiumVO().getDiscOrLoadPerc() ).setScale( 0, BigDecimal.ROUND_CEILING ) ) != 0 ){
					/* Discount/ Loading has been changed. */

					if( !endtId.equals( policyQuo.getId().getPolEndtId() ) ){
						/* Create policy records for the present endt.*/
						/* Hard coding section id to 0 so that records are not created in Section details table in case of only discount loading change.*/

						DAOUtils.createPolicyRecord( policyVO, Integer.valueOf( policyQuo.getPolClassCode() ), 0 );

						List<TTrnPolicyQuo> newPolRecords = (List<TTrnPolicyQuo>) getHibernateTemplate().find(
								"from TTrnPolicyQuo pol where pol.id.polPolicyId = ? and pol.id.polEndtId = ?", policyQuo.getId().getPolPolicyId(), policyVO.getNewEndtId() );

						//DAOUtils.createSpecialPrmRec( newPolRecords.get( 0 ), getHibernateTemplate() );

						Short secId = null;
						if( !Utils.isEmpty( policyQuo.getId().getPolPolicyId() ) && !Utils.isEmpty( policyQuo.getPolClassCode() ) ){
							secId = DAOUtils.getSectionIdForPolicyId( getHibernateTemplate(), policyQuo.getPolClassCode(), policyQuo.getId().getPolPolicyId(),
									policyVO.getIsQuote() );
						}
						else{
							throw new BusinessException( "cmn.unknownError", null, "The policy Id null in getSectionIdForPolicyId " );
						}

						if( !Utils.isEmpty( secId ) ){
							/* Update the VED for the previous Endt_Id's Policy and Cash Customer records. */
							DAOUtils.updateVED( policyVO, policyQuo.getPolClassCode(), secId );
						}

					}
				}

				ht.evict( premiumQuo );
			}

			ht.evict( policyQuo );
		}

	}

	private void updateTask( BaseVO baseVO ){
		PolicyCommentsHolder polComHolder = (PolicyCommentsHolder) baseVO;
		PolicyVO policyVO = polComHolder.getPolicyDetails();
		String polQuoNumber = null;
		if( policyVO.getIsQuote() ){
			polQuoNumber = policyVO.getQuoteNo().toString();
		}else{
			polQuoNumber = policyVO.getPolicyNo().toString();
		}
		String documentId = policyVO.getPolLinkingId() + "-" + policyVO.getEndtId() + "-" + polQuoNumber ;
		List<TTrnTask> taskList = getHibernateTemplate().find("from TTrnTask where tskDocumentId=?",documentId);
		for(TTrnTask task:taskList){
			task.setTskStatus( COMPLETED_STATUS );
			getHibernateTemplate().merge( task );
		}
	}
	
	/**
	 * @param baseVO
	 * @param status
	 * Method used to update the referral status in T_TRN_PAS_REFERRAL_DETAILS table
	 */
	private void referalDetails( BaseVO baseVO, Integer status){
		PolicyCommentsHolder polComHolder = (PolicyCommentsHolder) baseVO;
		PolicyVO policyVO = polComHolder.getPolicyDetails();
		List<TTrnPasReferralDetails> refList = getHibernateTemplate().find("from TTrnPasReferralDetails tprd where tprd.id.polLinkingId=? and tprd.refStatus is null",policyVO.getPolLinkingId());
		for(TTrnPasReferralDetails ref : refList){
			//if(!Utils.isEmpty( ref )&& !Utils.isEmpty( ref.getRefStatus() )){ - For issue 77934
				ref.setRefStatus(status.toString());
				getHibernateTemplate().merge( ref );
			//}
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.rsaame.pas.renewals.dao.IRenewalsDAO#getDisLoadPer(com.mindtree.ruc.cmn.base.BaseVO)
	 * Method to check if broker account is blocked.
	 */
	public BaseVO getBrAccStatus( BaseVO baseVO ){ 
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object quoteDetails[] = holderVO.getData();
		String brAccStatusQuery = "" ;
		/*
		 * On general info broker check using broker id
		 */
		if(!Utils.isEmpty( quoteDetails[0] )){
			 brAccStatusQuery = brAccStatusQuery+ "select distinct b.br_status  from t_mas_broker b where  b.br_code= " + (Integer)quoteDetails[0];
		} else if(!Utils.isEmpty( quoteDetails[1] )){
			/*
			 * In renewals broker check using quote no
			 */
			brAccStatusQuery = brAccStatusQuery + "select distinct b.br_status  from t_mas_broker b,t_trn_policy_quo p where p.pol_br_code = b.br_code " +
				"and p.pol_Quotation_no = "+quoteDetails[1];
		}
		
		LOGGER.debug("Fetching broker account status.");
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(brAccStatusQuery);
		Byte brAccStatus = null;
		if(!Utils.isEmpty(query.uniqueResult())){
			brAccStatus = (( BigDecimal )query.uniqueResult()).byteValue();
		}
		LOGGER.debug("Broker account status is ::"+brAccStatus);
	    DataHolderVO<Byte> statusVO =  new DataHolderVO<Byte>();
	    statusVO.setData( brAccStatus );
		return statusVO;
	}
	
	/* Clears off contextMap values for the current thread */
	private void clearThreadLevelContext(){
		ThreadLevelContext.clearAll();
	}
	
	
	/**
	 * Updates policy table and premium table during on demand referral
	 * @param baseVO
	 */
	public  void updateDiscOnDemandReferral( BaseVO baseVO ){

		PolicyVO policyVO = (PolicyVO)baseVO;
		//short baseClassCode = 0;

		Long endtId = SvcUtils.getLatestEndtId( policyVO );
		//String[] statusResReferral = Utils.getMultiValueAppConfig("STATUS_RESOLVE_REFERRAL", ",");	/* commented the variable declaration as were not in use - sonar violation fix */
		
		List<TTrnPolicyQuo> policyQuoList ;
		
		policyQuoList = getHibernateTemplate().find(
					com.Constant.CONST_FROM_TTRNPOLICYQUO_POLICYQUO_WHERE_POLICYQUO_POLLINKINGID_AND_POLICYQUO_ID_POLENDTID_OR_POLICYQUO_ID_POLENDTID_AND_POLICYQUO_POLVALIDITYEXPIRYDATE_AND_POLICYQUO_POLPOLICYTYPE_END, policyVO.getPolLinkingId(),
					endtId,endtId,SvcConstants.EXP_DATE, Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_POLICY_TYPES ) ) );

		/* commented variable 'converter' and 'baseClassCode' declaration as were not in use - sonar violation fix */
		//com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
		//baseClassCode = getBaseClassCode( policyQuoList);
		for( TTrnPolicyQuo policyQuo : policyQuoList ){
			policyQuo.setPolModifiedBy(SvcUtils.getUserId(policyVO));
			policyQuo.setPolApprovedInd("Y"); // The status of the quote is approved and the indicator is set to Y
			policyQuo.setPolModifiedDt( getSysDate() );
			
			getHibernateTemplate().flush();
			
			PremiumHelper.updateSpecialCovers(policyVO,policyQuo,getHibernateTemplate());
			/*
			 * POL_PREMIUM needs to be updated with class level premium. This method will fetch the premium for a class from premium table and update the 
			 * policy table 
			 */
			PremiumHelper.updatePolicyPremium(policyVO,policyQuo,getHibernateTemplate() );
			
			getHibernateTemplate().merge( policyQuo );
			
		}
		
	}
	
}
