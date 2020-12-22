/*
 * Change History:
 * ----------------------
 * Ticket: 142260, Author: M1037404 , Date Modified: 06/22/2017
 * Comment : Code Added for while deleting pending endorsement in deletePendingPolicy method.
 *  
 */
package com.rsaame.pas.endorse.dao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TTrnEndorsementDetail;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.VTrnPasGetEndtText;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1014241
 *
 */
public class AmendPolicyDao extends BaseDBDAO implements IAmendPolicyDao{

	private final static Logger LOGGER = Logger.getLogger( AmendPolicyDao.class );

	/*private final static Short PL_CLASS_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "PL_CLASS" ) );*/
	private final static Integer SI_PRM_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "SI_PRM__SECTION_ID" ) );
	private final static Long LOC_ID = Long.valueOf( "0" );

	/* (non-Javadoc)
	 * @see com.rsaame.pas.amend.dao.IAmendPolicyDao#getAmendPolicyDetails(com.rsaame.pas.vo.bus.PolicyVO)
	 */
	@Override
	public BaseVO isEndorsePending( BaseVO baseVO ){
		if( LOGGER.isInfo() ) LOGGER.info( "Enterning Amend Policy status details method" );
		PolicyVO policyVO = null;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				policyVO = (PolicyVO) baseVO;

				TTrnPolicyQuo tTrnPoL = getTtrnPol( policyVO.getPolLinkingId() );

				if( !Utils.isEmpty( tTrnPoL ) ){
					if( !Utils.isEmpty( tTrnPoL.getPolLinkingId() ) ) policyVO.setPolLinkingId( tTrnPoL.getPolLinkingId() );
					if( !Utils.isEmpty( tTrnPoL.getPolStatus() ) ) policyVO.setStatus( Integer.valueOf( tTrnPoL.getPolStatus() ) );
					if( !Utils.isEmpty( tTrnPoL.getPolExpiryDate() ) ) policyVO.setPolExpiryDate( ( tTrnPoL.getPolExpiryDate() ) );
					if( !Utils.isEmpty( tTrnPoL.getPolEffectiveDate() ) ) policyVO.setPolEffectiveDate( tTrnPoL.getPolEffectiveDate() );
					if( !Utils.isEmpty( tTrnPoL.getPolPolicyNo() ) ) policyVO.setPolicyNo( tTrnPoL.getPolPolicyNo() );

				}

				if( LOGGER.isInfo() ) LOGGER.info( " POLICY NO --- : " + policyVO.getPolicyNo() + " STATUS---: " + policyVO.getStatus() );
				return policyVO;
			}
		}
		return baseVO;
	}

	public TTrnPolicyQuo getTtrnPol( Long policyLinkingId ){

		TTrnPolicyQuo tTrnPolicyQuo = null;
		List<TTrnPolicyQuo> listTTrnPolicyQuo = null;
		try{
			listTTrnPolicyQuo = getHibernateTemplate().find(
					"from TTrnPolicyQuo policy where  policy.polLinkingId = ? and policy.polValidityExpiryDate =? and policy.polStatus in (6,20,4) ", policyLinkingId,
					SvcConstants.EXP_DATE );
			if( !listTTrnPolicyQuo.isEmpty() )
				tTrnPolicyQuo = listTTrnPolicyQuo.get( 0 );
			else{
				listTTrnPolicyQuo = getHibernateTemplate().find( "from TTrnPolicyQuo policy where  policy.polLinkingId = ? and policy.polValidityExpiryDate =? ", policyLinkingId,
						SvcConstants.EXP_DATE );
				tTrnPolicyQuo = listTTrnPolicyQuo.get( 0 );
			}

		}
		catch( HibernateException e ){
			e.printStackTrace();
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", e, "Error while trying to SELECT policy details from T_TRN_POLIC_1" );
		}
		return tTrnPolicyQuo;
	}

	@Override
	public boolean getInsuredChangeDetails( BaseVO baseVO ){

		if( LOGGER.isInfo() ) LOGGER.info( "Enterning Insured change details menthod" );
		/*PolicyVO policyVO = (PolicyVO) baseVO;
		Long policyLinkingId = policyVO.getPolLinkingId();

		TMasCashCustomerQuo tMasCashCustomerQuo = getTMasCashCustomerDetails( policyVO.getPolLinkingId(), policyVO.getNewEndtId() );
		TMasInsured tMasInsured = getTMasInsuredDetails( policyLinkingId );*/

		return false;
	}

	@Override
	public BaseVO isInsuredChanged( BaseVO baseVO ){

		if( LOGGER.isInfo() ) LOGGER.info( "Enterning Insured change details menthod" );
		PolicyVO policyVO = null;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				policyVO = (PolicyVO) baseVO;
				Long policyLinkingId = policyVO.getPolLinkingId();
				//get policy id and endt id where policy linking id=getPolLinkingId()
				TTrnPolicyQuo tTrnPolicy = (TTrnPolicyQuo) getHibernateTemplate().find( "from TTrnPolicyQuo where polIssueHour = ? and polLinkingId = ? and id.polEndtId=?", Integer.valueOf( Utils.getSingleValueAppConfig( "SBS_POLICY_ISSUE_HOUR" ) ),
						policyLinkingId,policyVO.getEndtId() ).get( 0 );
				TMasCashCustomerQuo tMasCashCustomerQuo = getTMasCashCustomerDetails( tTrnPolicy.getId().getPolPolicyId(), policyVO.getEndtId() );
				//policyVO.getGeneralInfo().getInsured().getInsuredId();
				TMasInsured tMasInsured = getTMasInsuredDetails( policyVO.getGeneralInfo().getInsured().getInsuredId() );
				boolean isInsuredChangd = ischangeFromTmasInsured( tMasInsured, tMasCashCustomerQuo );
				policyVO.setInsuredChanged( isInsuredChangd );
				return policyVO;
			}
		}
		return baseVO;
	}

	private TMasInsured getTMasInsuredDetails( Long cshInsuredId ){

		try{
			return ( (TMasInsured) getHibernateTemplate().find( "from TMasInsured where insInsuredCode = ? ", cshInsuredId ).get( 0 ) );

		}
		catch( HibernateException e ){
			e.printStackTrace();
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", e, "Error while trying to SELECT policy details from T_TRN_POLIC_2" );
		}
	}

	private TMasCashCustomerQuo getTMasCashCustomerDetails( Long cshPolicyId, Long endtId ){
		try{
			return ( (TMasCashCustomerQuo) getHibernateTemplate().find( "from TMasCashCustomerQuo  where  id.cshPolicyId = ? and cshEndtId=? ", cshPolicyId, endtId ).get( 0 ) );

		}
		catch( HibernateException e ){
			e.printStackTrace();
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", e, "Error while trying to SELECT policy details from T_TRN_POLIC_3" );
		}
	}

	@Override
	public BaseVO getEndorsementSummary( BaseVO baseVO ){

		if( LOGGER.isInfo() ) LOGGER.info( "Enterning Endorsement Summary method" );

		PolicyVO policyVO = null;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				policyVO = (PolicyVO) baseVO;
				policyVO = (PolicyVO) baseVO;

				Long endtId = null;
				if( !Utils.isEmpty( policyVO.getNewEndtId() ) ){
					endtId = policyVO.getNewEndtId();
				}
				else{
					endtId = policyVO.getEndtId();
				}

				if( Flow.AMEND_POL.equals( policyVO.getAppFlow() ) ){
					updateEndtText( policyVO, endtId, SvcUtils.getUserId( policyVO ) );
				}
				//Ticket : 156700
				policyVO.setPolVatRate(DAOUtils.getVATRateSBS(policyVO.getPolVATCode(), null));
				
				List<EndorsmentVO> endorsements = getEndorsements( policyVO.getPolLinkingId(),endtId );
				policyVO.setEndorsements( endorsements );
				
				/*if("NIL".equals( endorsements.get( 0 ).getEndType())){
					DAOUtils.addDeleteEndtHeaderText(policyVO,Boolean.FALSE,getHibernateTemplate());
				}*/
				
				//EndorsmentVO endorsmentVO = getEndorsementText( policyVO.getPolLinkingId(), endtId );
				List<EndorsmentVO> endList = getEndorsementText(policyVO.getPolLinkingId(), endtId );
				/*
				 * Status not pending/Referred/Approved check to set endorsement text is required in order to avoid the endorsement text display in premium page before confirmation of endorsement.
				 */
				/*if( !Utils.isEmpty( endorsmentVO ) && !Utils.isEmpty( policyVO.getEndorsements() )  && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_PENDING ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_ACCEPT ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_REFERRED ) ){
					policyVO.getEndorsements().get( 0 ).setEndText( endorsmentVO.getEndText());
				}*/
				EndorsmentVO endVo0 =null;
				/*if(!Utils.isEmpty(policyVO.getEndorsements()) && !Utils.isEmpty(policyVO.getEndorsements().get(0)))
					{
						endL
					}*/
				EndorsmentVO endV =null;
				if( !Utils.isEmpty( endList ) && !Utils.isEmpty( policyVO.getEndorsements() )  && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_PENDING ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_ACCEPT ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_REFERRED ) ){
					//policyVO.getEndorsements().get( 0 ).setEndText( endorsmentVO.getEndText());
					for(int i = 0 ; i< endList.size();i++)
					{
						endList.get(i).setEndType(policyVO.getEndorsements().get(0).getEndType());
						endList.get(i).setOldPremiumVO(policyVO.getEndorsements().get(0).getOldPremiumVO());
						endList.get(i).setPremiumVO(policyVO.getEndorsements().get(0).getPremiumVO());
					}
					policyVO.setEndorsements(endList);
				}
				policyVO.setEndorsements(rearrangeEndorsments(policyVO.getEndorsements()));
				/* Commented the code as there was code redundancy. Added above code.
				 * TODO : Commented code to be removed in RC 15.
				 * In View mode flows policyVO.newEndtId will be null in that fetching the endorsement summary for policyVO.endtId
				 */
				/*if( !Utils.isEmpty( policyVO.getNewEndtId() ) ){
					List<EndorsmentVO> endorsements = getEndorsements( policyVO.getPolLinkingId(), policyVO.getNewEndtId() );
					policyVO.setEndorsements( endorsements );
					EndorsmentVO endorsmentVO = getEndorsementText( policyVO.getPolLinkingId(), policyVO.getNewEndtId() );
					
					 * Status not pending/Referred/Approved check to set endorsement text is required in order to avoid the endorsement text display in premium page before confirmation of endorsement.
					 
					if( !Utils.isEmpty( endorsmentVO ) && !Utils.isEmpty( policyVO.getEndorsements() ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_PENDING )
							&& !policyVO.getStatus().equals( SvcConstants.POL_STATUS_ACCEPT ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_REFERRED ) ){
						policyVO.getEndorsements().get( 0 ).setEndText( endorsmentVO.getEndText() );
					}
				}
				else if( !Utils.isEmpty( policyVO.getEndtId() ) ){
					List<EndorsmentVO> endorsements = getEndorsements( policyVO.getPolLinkingId(), policyVO.getEndtId() );
					policyVO.setEndorsements( endorsements );
					EndorsmentVO endorsmentVO = getEndorsementText( policyVO.getPolLinkingId(), policyVO.getEndtId() );
					
					 * Status not pending/Referred/Approved check to set endorsement text is required in order to avoid the endorsement text display in premium page before confirmation of endorsement.
					 
					if( !Utils.isEmpty( endorsmentVO ) && !Utils.isEmpty( policyVO.getEndorsements() ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_PENDING )
							&& !policyVO.getStatus().equals( SvcConstants.POL_STATUS_ACCEPT ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_REFERRED ) ){
						policyVO.getEndorsements().get( 0 ).setEndText( endorsmentVO.getEndText() );
					}
				}*/

				return policyVO;
			}
		}
		return baseVO;
	}
/*Moved to DAOUtils class */
	/*private void deleteEndtHeaderText(PolicyVO policyVO, Boolean flag){
		Long EndtID = null;
		
		if(Utils.isEmpty( policyVO.getNewEndtId() ))
		{
			EndtID = policyVO.getEndtId();
		}
		else
		{
			EndtID = policyVO.getNewEndtId();
		}
		List<Integer> policyIds = getHibernateTemplate().find( "select id.polPolicyId from TTrnPolicyQuo where polLinkingId = ? and id.polEndtId = ?",policyVO.getPolLinkingId(),EndtID );
		String[] paramNames ={"ids","endtId"};
		Object[] paramValues ={policyIds,EndtID};
		List<TTrnEndorsementDetail> endtTextList = getHibernateTemplate().findByNamedParam( "from TTrnEndorsementDetail where id.edlPolicyId in (:ids) and id.edlEndorsementId = :endtId ",paramNames,paramValues);
		int count = 0;
		
		for (TTrnEndorsementDetail endorsementDetail : endtTextList){
			//if(0 == endorsementDetail.getEdlSecId() || 977 == endorsementDetail.getEdlSecId()){
			if(0 == endorsementDetail.getEdlSecId() ){
				count++;
			}
			
		}
		
		
		
		//if (flag==2){
		if (count==1 && !flag){
			getHibernateTemplate().deleteAll( endtTextList );
		}
		else{
			
		}
		
	}*/

	private List<EndorsmentVO> rearrangeEndorsments(
			List<EndorsmentVO> endorsements) {
		List<EndorsmentVO> originalList = endorsements;
		List<EndorsmentVO> list1 = new ArrayList<EndorsmentVO>();

		for(EndorsmentVO endV1 : originalList)
		{
			if(!Utils.isEmpty(endV1.getEndText()) && !(endV1.getEndText().trim()).equals(""))
				list1.add(endV1);
		}
		
		List<EndorsmentVO> list2 = new ArrayList<EndorsmentVO>();
		for(EndorsmentVO endV2 : originalList)
		{
			if(Utils.isEmpty(endV2.getEndText()) || (endV2.getEndText().trim()).equals(""))
				list2.add(endV2);
		}
		
		List<EndorsmentVO> finalList = new ArrayList<EndorsmentVO>();
		finalList.addAll(list1);
		finalList.addAll(list2);
		list1=null;
		list2= null;
		originalList=null;
		
		return finalList;
	}

	/**
	 * 
	 * @param polLinkingId
	 * @param newEndtId
	 * @return
	 */
	private List<EndorsmentVO> getEndorsementText( Long polLinkingId, Long newEndtId ){
		List<EndorsmentVO> endList = new ArrayList<EndorsmentVO>();
		List<VTrnPasGetEndtText> pasGetEndtTextList = (List<VTrnPasGetEndtText>) getHibernateTemplate().find(
				" from VTrnPasGetEndtText where id.polLinkingId = ? and id.polEndtId = ?", polLinkingId, newEndtId );
		//EndorsmentVO endorsmentVO = new EndorsmentVO();  // Changed for Sonar critical issue.
		EndorsmentVO endorsmentVO = null;
	 if( pasGetEndtTextList.size()>0 && !Utils.isEmpty( pasGetEndtTextList.get(0))){ // RADAR BUG FIXES: 520465 - Dinesh
		for(VTrnPasGetEndtText vTrnPasGetEndtText : pasGetEndtTextList)
		{
			//String endText = "";
			//Integer endlSerialNo = 0;
			endorsmentVO = new EndorsmentVO();
			
				/*for( VTrnPasGetEndtText vTrnPasGetEndtText : pasGetEndtTextList ){*/

			if( !Utils.isEmpty( vTrnPasGetEndtText.getEndtText() ) ){
				endorsmentVO.setEndText(vTrnPasGetEndtText.getEndtText().trim());
				}
				
			if( !Utils.isEmpty( vTrnPasGetEndtText.getId().getEdlSerialNo() ) ){
				endorsmentVO.setSlNo(Integer.parseInt(vTrnPasGetEndtText.getId().getEdlSerialNo().toString()));
			}
			if(!Utils.isEmpty(vTrnPasGetEndtText.getId().getPolEndtId()))
				endorsmentVO.setEndtId(vTrnPasGetEndtText.getId().getPolEndtId());
			if(!Utils.isEmpty(vTrnPasGetEndtText.getId().getPolEndtNo()))
				endorsmentVO.setEndNo(vTrnPasGetEndtText.getId().getPolEndtNo());
			if(!Utils.isEmpty(vTrnPasGetEndtText.getId().getPolicyId()))
				endorsmentVO.setPolicyId(vTrnPasGetEndtText.getId().getPolicyId());
			if(!Utils.isEmpty(vTrnPasGetEndtText.getEdlSecId()))
				endorsmentVO.setEndSecId(vTrnPasGetEndtText.getEdlSecId());
			
			endList.add(endorsmentVO);
		}
	 }
		
		LOGGER.debug( "Endorsment Text List from getEndorsementText() of the class AmendPolicyDao: "+ endList);
		return endList;

	}

	private List<EndorsmentVO> getEndorsements( long polLinkingId, Long endtId ){

		java.util.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );

		if( !Utils.isEmpty( endtId ) ){
			
			PremiumHelper.logPremiumInfo( "**********Setting old and new premium************" );
			EndorsmentVO endorsmentVO = new EndorsmentVO();
			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();
			endorsmentVO.setOldPremiumVO( oldPremiumVO );
			endorsmentVO.setPremiumVO( newPremiumVO ); // New premium amount.

			/*Double currentPrm = getPremium( polLinkingId, "PKG_ENDT_GEN.GET_CURR_PRM" );
			Double previousPrm = getPremium( polLinkingId, endtId, "PKG_ENDT_GEN.GET_PREV_PRM" );*/

			/*endorsmentVO.getPremiumVO().setPremiumAmt( PremiumHelper.getCurrPremium( polLinkingId, 
					Utils.getSingleValueAppConfig( "GET_NEW_ANNUALIZED_PRM_FUNC" ), getHibernateTemplate() ) );// New premium amount.
			endorsmentVO.getOldPremiumVO().setPremiumAmt( PremiumHelper.getOldPremium( polLinkingId, endtId, 
					Utils.getSingleValueAppConfig( "GET_OLD_ANNUALIZED_PRM_FUNC" ), getHibernateTemplate() ) ); // Old premium amount.*/

			DataHolderVO<HashMap<String, Double>> premiumHolder = PremiumHelper.getOldNewPremium( polLinkingId, endtId );
			endorsmentVO.getOldPremiumVO().setPremiumAmt( premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM ) );
			endorsmentVO.getPremiumVO().setPremiumAmt( premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM ) );
			PremiumHelper.logPremiumInfo( "Payable premium is: " + ( newPremiumVO.getPremiumAmt() - oldPremiumVO.getPremiumAmt() ) );

			String endType;
			if( endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt() > 0 ){
				endType = "Extra";
			}
			else if( endorsmentVO.getPremiumVO().getPremiumAmt() - endorsmentVO.getOldPremiumVO().getPremiumAmt() < 0 ){
				endType = "Refund";
			}
			else{
				endType = "NIL";
			}
			endorsmentVO.setEndType( endType );

			endorsements.add( endorsmentVO );
		}

		return endorsements;

	}

	@Override
	public BaseVO getCancelPolRefundPremium( BaseVO baseVO ){

		// Makes the status of policy as 4 (canceled)  & calls procedure to get the refund premium amount.

		if( LOGGER.isInfo() ) LOGGER.info( "Enterning amend cancel policy method" );

		PolicyVO policyVO = null;

		try{
			if( !Utils.isEmpty( baseVO ) ){
				if( baseVO instanceof PolicyVO ){
					policyVO = (PolicyVO) baseVO;

					if( !Utils.isEmpty( policyVO ) ){
						List<EndorsmentVO> endorsements = null;

						/* Setting isPolicyToBeCancelled (true) 
						 * to check the flow [AMEND/CANCEL] for display of endorsement Summary in premium page.
						 * check done in PremiumRH */

						endorsements = getEndorsementsForCancelPolicy( policyVO );
						if( !Utils.isEmpty( endorsements ) ){
							endorsements.get( 0 ).setPolicyToBeCancelled( true );
						}
						policyVO.setEndorsements( endorsements );

					}
				}
			}

		}
		catch( DataAccessException accessException ){
			throw new SystemException( "", accessException, "Error while getting the premium refund amount." );
		}

		return policyVO;

	}

	private boolean ischangeFromTmasInsured( TMasInsured tMasInsured, TMasCashCustomerQuo tMasCashCustomerQuo ){

		if( tMasInsured.getInsCustomerId().equals( tMasCashCustomerQuo.getCshCustomerId() ) ){
			return compareWithTMasCashCust( tMasInsured, tMasCashCustomerQuo );

		}

		return false;
	}

	private boolean compareWithTMasCashCust( TMasInsured tMasInsured, TMasCashCustomerQuo tMasCashCustomerQuo ){
		boolean flag = false;
		long dateDiff = 0;
		if( ( tMasInsured.getInsModifiedDt() != null ) && ( tMasCashCustomerQuo.getCshModifiedDt() != null ) ){
			dateDiff = tMasInsured.getInsModifiedDt().getTime() - tMasCashCustomerQuo.getCshModifiedDt().getTime();
		}
		if( dateDiff > 0 ){
			if( ( ( tMasInsured.getInsEFirstName() != null ) && !tMasInsured.getInsEFirstName().equalsIgnoreCase( tMasCashCustomerQuo.getCshEName1() ) )
					|| ( ( tMasCashCustomerQuo.getCshEName1() != null ) && !tMasCashCustomerQuo.getCshEName1().equalsIgnoreCase( tMasInsured.getInsEFirstName() ) ) ){
				flag = true;
			}
			if( ( ( tMasInsured.getInsAFirstName() != null ) && !tMasInsured.getInsAFirstName().equalsIgnoreCase( tMasCashCustomerQuo.getCshAName1() ) )
					|| ( ( tMasCashCustomerQuo.getCshAName1() != null ) && !tMasCashCustomerQuo.getCshAName1().equalsIgnoreCase( tMasInsured.getInsAFirstName() ) ) ){
				flag = true;
			}
			if( ( ( tMasInsured.getInsEZipCode() != null ) && !tMasInsured.getInsEZipCode().equalsIgnoreCase( tMasCashCustomerQuo.getCshPoboxNo() ) )
					|| ( ( tMasCashCustomerQuo.getCshPoboxNo() != null ) && !tMasCashCustomerQuo.getCshPoboxNo().equalsIgnoreCase( tMasInsured.getInsEZipCode() ) ) ){
				flag = true;
			}
			if( ( ( tMasInsured.getInsCity() != null ) && !tMasInsured.getInsCity().equals( tMasCashCustomerQuo.getCshCity() ) )
					|| ( ( tMasCashCustomerQuo.getCshCity() != null ) && !tMasCashCustomerQuo.getCshCity().equals( tMasInsured.getInsCity() ) ) ){
				flag = true;
			}
			if( ( ( tMasInsured.getInsEEmailId() != null ) && !tMasInsured.getInsEEmailId().equalsIgnoreCase( tMasCashCustomerQuo.getCshEEmailId() ) )
					|| ( ( tMasCashCustomerQuo.getCshEEmailId() != null ) && !tMasCashCustomerQuo.getCshEEmailId().equalsIgnoreCase( tMasInsured.getInsEEmailId() ) ) ){
				flag = true;
			}
			if( ( ( tMasInsured.getInsEMobileNo() != null ) && !tMasInsured.getInsEMobileNo().equalsIgnoreCase( tMasCashCustomerQuo.getCshEGsmNo() ) )
					|| ( ( tMasCashCustomerQuo.getCshEGsmNo() != null ) && !tMasCashCustomerQuo.getCshEGsmNo().equalsIgnoreCase( tMasInsured.getInsEMobileNo() ) ) ){
				flag = true;
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.endorse.dao.IAmendPolicyDao#deletePendingPolicy(com.mindtree.ruc.cmn.base.BaseVO)
	 * 
	 *  This method is used to delete the pending policy(HOME/TRAVEL/SBS)
	 */
	@Override
	public BaseVO deletePendingPolicy( BaseVO baseVO ){

		if( LOGGER.isInfo() ){
			LOGGER.info( "Enterning Delete Policy  method" );
		}

		PolicyVO policyVO = null;
		PolicyDataVO policyDataVO = null;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				policyVO = (PolicyVO) baseVO;
				policyVO = (PolicyVO) baseVO;
				/* Request ID : 132122 changed by Rickdev Start */
				SchemeVO schemeVO = new SchemeVO();
				schemeVO = policyVO.getScheme();
				if(schemeVO.getPolicyType().equals("50") && policyVO.getStatus()==6){
					
					Long policyNo=policyVO.getPolicyNo();
					Long endtNo=policyVO.getEndtNo()-1;
					
					TTrnPolicyQuo policy1 = null;				
					Object[] param1 = { policyNo,endtNo };
					policy1 = (TTrnPolicyQuo) getHibernateTemplate().find( "from TTrnPolicyQuo pol where pol.polPolicyNo=? and pol.polEndtNo=? ",param1).get( 0 );
					
					TMasCashCustomerQuo tMasCashCustomer=null;
					Object[] param2 = { policy1.getId().getPolicyId(),policy1.getId().getEndtId() };
					tMasCashCustomer=(TMasCashCustomerQuo) getHibernateTemplate().find("from TMasCashCustomerQuo pol where pol.id.cshPolicyId=? and pol.cshEndtId=? ",param2).get(0);
					
					TMasInsured tMasInsured=null;
					Object[] param3 = { policy1.getPolInsuredCode()};
					tMasInsured=(TMasInsured)getHibernateTemplate().find("from TMasInsured pol where pol.insInsuredCode=? ",param3).get(0);
					
					tMasInsured.setInsCcgCode(tMasCashCustomer.getCshCcgCode());
					tMasInsured.setInsEFirstName(tMasCashCustomer.getCshEName1());
					tMasInsured.setInsELastName(tMasCashCustomer.getCshEName2());
					tMasInsured.setInsEZipCode(tMasCashCustomer.getCshPoboxNo());
					tMasInsured.setInsCtyCode(tMasCashCustomer.getCshCtyCode());
					tMasInsured.setInsEEmailId(tMasCashCustomer.getCshEEmailId());
					tMasInsured.setInsBusiness(tMasCashCustomer.getCshBusiness());
					tMasInsured.setInsEPhoneNo(tMasCashCustomer.getCshEPhoneNo());
					tMasInsured.setInsEAddress(tMasCashCustomer.getCshEAddress1());
					tMasInsured.setInsEFullName(tMasCashCustomer.getCshEName1());
					tMasInsured.setInsCity(tMasCashCustomer.getCshCity());
					tMasInsured.setInsNoOfEmp(tMasCashCustomer.getCshNoOfEmp());
					
					getHibernateTemplate().saveOrUpdate(tMasInsured);
				}
				/* Request ID : 132122 changed by Rickdev End */
				PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "delPendingPolicy" );
				try{
					Map results = sp.call( policyVO.getPolLinkingId(), policyVO.getEndtId() );
					if( Utils.isEmpty( results ) ){
						LOGGER.info( "The result of the stored procedure is null" );
					}
					//Modified for Bahrain VAT changes - Starts
					TTrnPolicyQuo policy = null;
					policy = (TTrnPolicyQuo) getHibernateTemplate().find( "from TTrnPolicyQuo pol where pol.polPolicyNo=? and pol.polIssueHour=3 and pol.polStatus <> 6 order by pol.id.polEndtId desc ",
							policyVO.getPolicyNo() ).get( 0 );
					policyVO.setEndtId( policy.getId().getPolEndtId() );
					//Modified for Bahrain VAT changes - Ends
				}
				catch( DataAccessException e ){
					throw new BusinessException( "PKG_ENDT_GEN.pro_revert_endt exception", e, "An exception occured while executing stored proc_1" );
				}

				return policyVO;
			}
			else if(baseVO instanceof PolicyDataVO){
				policyDataVO = (PolicyDataVO) baseVO;
				
				PASStoredProcedure sp = (PASStoredProcedure)getDelPendingProc( policyDataVO );
				try{
					Map results = sp.call( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
					if( Utils.isEmpty( results ) ){
						LOGGER.info( "The result of the stored procedure is null" );
					}
					TTrnPolicyQuo policy = null;
					policy = (TTrnPolicyQuo) getHibernateTemplate().find( QueryConstants.FETCH_LATEST_ACTIVE_POLICY,
							policyDataVO.getCommonVO().getPolicyNo() ).get( 0 );
					policyDataVO.setEndtId( policy.getId().getPolEndtId() );
					policyDataVO.getCommonVO().setEndtId( policy.getId().getEndtId() );
					policyDataVO.getCommonVO().setDocCode( policy.getPolDocumentCode() );
					policyDataVO.getCommonVO().setEndtNo( policy.getPolEndtNo() );		
					policyDataVO.getCommonVO().setVsd(policy.getPolValidityStartDate());			//Added by:M1037404 #142260-deleting pending endorsement
				}
				catch( DataAccessException e ){
					throw new BusinessException( "PKG_ENDT_GEN.pro_revert_endt exception", e, "An exception occured while executing stored proc_2" );
				}
				return policyDataVO;
			}
		}
		return baseVO;

	}

	private void updateEndtText( PolicyVO policyVO, long endtId, Integer userId ){

		DAOUtils.deletePrevEndtText( policyVO.getPolLinkingId(), endtId, SI_PRM_SECTION_ID, LOC_ID );
		//TODO check on what conditions these needs to be called

		System.out.println( "call pro_endt_text_si" );
		DAOUtils.updateSIforendorsementFlow( policyVO.getPolLinkingId(), endtId, userId );

		System.out.println( "call pro_endt_text_prm" );
		DAOUtils.updatePRMforendorsementFlow( policyVO.getPolLinkingId(), endtId, userId );
		
		//DAOUtils.updateFooterforendorsementFlow( policyVO.getPolLinkingId(), endtId, userId );
	}

	@Override
	public BaseVO updateInsurePol( BaseVO baseVO ){
		if( LOGGER.isInfo() ) LOGGER.info( "Enterning updateInsurePol" );

		PolicyVO policyVO = null;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				policyVO = (PolicyVO) baseVO;
				Long insuredId = policyVO.getGeneralInfo().getInsured().getInsuredId();
				TMasInsured tMasInsured = null;
				TMasCashCustomerQuo cashCustomerQuo = null;
				TTrnPolicyQuo tTrnPoL = null;
				try{
					tTrnPoL = getTtrnPol( policyVO.getPolLinkingId() );
					tMasInsured = (TMasInsured) getHibernateTemplate().find( "from TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ", insuredId ).get( 0 );

					List<TMasCashCustomerQuo> listtMasCashCustomerQuo = getHibernateTemplate().find(
							"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? and cshEndtId=? ", tTrnPoL.getId().getPolPolicyId(),
							policyVO.getEndtId() );
					cashCustomerQuo = listtMasCashCustomerQuo.get( 0 );
					updateCustomerDetails( cashCustomerQuo, tMasInsured );

				}
				catch( DataAccessException e ){
					throw new BusinessException( "updateInsurePol", e, "An exception occured while updatng Insure details to Policy table" );
				}

				return policyVO;
			}
		}
		return baseVO;
	}

	private void updateCustomerDetails( TMasCashCustomerQuo cashCustomerQuo, TMasInsured tMasInsured ){

		cashCustomerQuo.setCshEName1( tMasInsured.getInsEFirstName() );
		cashCustomerQuo.setCshAName1( tMasInsured.getInsAFirstName() );
		cashCustomerQuo.setCshEPhoneNo( tMasInsured.getInsEPhoneNo() );
		cashCustomerQuo.setCshBusiness( tMasInsured.getInsBusiness() );
		cashCustomerQuo.setCshEGsmNo( tMasInsured.getInsEMobileNo() );
		cashCustomerQuo.setCshEAddress1( tMasInsured.getInsEAddress() );
		cashCustomerQuo.setCshPoboxNo( tMasInsured.getInsEZipCode() );
		cashCustomerQuo.setCshEEmailId( tMasInsured.getInsEEmailId() );
		cashCustomerQuo.setCshCity( tMasInsured.getInsCity() );
		cashCustomerQuo.setCshCountry( tMasInsured.getInsCountry().intValue() );
		cashCustomerQuo.setCshModifiedDt( getSysDate() );
		getHibernateTemplate().merge( cashCustomerQuo );
	}

	private List<EndorsmentVO> getEndorsementsForCancelPolicy( PolicyVO policyVO ){

		java.util.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
		/* Update endt no and id before loading premium page*/
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow()) );
		DAOUtils.fetchEndtId( policyVO ,getHibernateTemplate());
		
		Long endtId = !(Utils.isEmpty( policyVO.getNewEndtId() )) ? policyVO.getNewEndtId() : policyVO.getEndtId();
		
		Long polLinkingId = policyVO.getPolLinkingId();
		Double refundAmount = 0.0;
		if( !Utils.isEmpty( endtId ) ){
			EndorsmentVO endorsmentVO ;
			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();

			/*
			 * After cancellation of policy the premium will be 0, and since the need is to display the 
			 * refund amount before actually cancellation of policy new premium for calculation is taken as 0.0
			 */
			Double newPremiumAmt = 0.0;

			DataHolderVO<HashMap<String, Double>> premiumHolder = PremiumHelper.getOldNewPremium( polLinkingId, endtId );
			newPremiumAmt = premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM );

			Double currentPremiumAmt = premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM );
			
			/*Added to calculate prorate premium(Rounded to 3 digits) for SBS */
			refundAmount = getProRateRefundAmt(policyVO,currentPremiumAmt);	

			//double refundAmount = PremiumHelper.getRefundAmountForCancelPolicy( policyVO, currentPremiumAmt );

			if( refundAmount != 0.0 ){
				// newPremiumAmt = currentPremiumAmt - refundAmount;
				newPremiumAmt = refundAmount;
			}
			else{
				newPremiumAmt = 0.0;
			}

			newPremiumVO.setPremiumAmt( newPremiumAmt ); // New premium amount.
			oldPremiumVO.setPremiumAmt( currentPremiumAmt ); // Old premium amount.

			Map results = DAOUtils.endFlowGeneralInfo( policyVO.getPolLinkingId(), policyVO.getIsQuote() );
			endorsmentVO = constructEndorsmentVO( policyVO, oldPremiumVO, newPremiumVO, results );
			endorsements.add( endorsmentVO );

		}
		LOGGER.debug( "Refund Amount is - "+refundAmount );
		return endorsements;

	}

	/**
	 * 
	 * @param policyVO
	 * @param oldPremiumVO
	 * @param newPremiumVO
	 * @param results
	 * @return
	 */
	private EndorsmentVO constructEndorsmentVO(PolicyVO policyVO,
			PremiumVO oldPremiumVO, PremiumVO newPremiumVO, Map results) {
		
		EndorsmentVO endorsmentVO = new EndorsmentVO();
		endorsmentVO.setOldPremiumVO( oldPremiumVO );
		endorsmentVO.setPremiumVO( newPremiumVO );
		endorsmentVO.setPolicyId( PolicyUtils.getBasicSectionVO( policyVO ).getPolicyId() );
		endorsmentVO.setEndtId(Long.valueOf(results.get("p_endt_id").toString()));
		endorsmentVO.setEndNo(Long.valueOf(results.get("p_endt_no").toString()));
		endorsmentVO.setSlNo( 1 );
		
		return endorsmentVO;
	}

	@Override
	public BaseVO processCancelPolicy( BaseVO baseVO ){
		PolicyVO policyVO = (PolicyVO) baseVO;
		Double newPremium = 0.0;
		PASStoredProcedure sp = null;
		
		/* Clear all values that have been added to the ThreadLevelContext for the section. */
		/*
		 * In case of an exception during save the ThreadLevelContext will not be cleared, to clear it the next time the user saves
		 * in the same session clear the ThreadLevelContextfirst and proceed
		 */
		ThreadLevelContext.clearAll();
		
		/* Let us get the system date right now and use from here on for this transaction. This date is not sysdate directly rather
		 * obtained by executing function get_revised_pol_issuedate to retrieve right issue_date/VSD for the transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), policyVO.getAppFlow()  ) );
		/* Endt id to be used for processing cancellation of policy */
		Long cancelationEndtId = SvcUtils.getLatestEndtId(policyVO) ;
		
		//++Start Changed by Mindtree On 08/09/2015 for SIT defect fix during cancel when PAR,Travel and GIT selected
		Long  basePolicyId = DAOUtils.getBaseSectionPolicyId ( policyVO,getHibernateTemplate());
		//++End Changed by Mindtree On 08/09/2015 for SIT defect fix during cancel when PAR,Travel and GIT selected
		 
		/*Below code added to get endorsement id for storing endorsement text during cancellation*/
		/* Commenting the call to fetchEndtId() as it is added in the method getEndorsementsForCancelPolicy()*/
		//DAOUtils.fetchEndtId( policyVO );
		sp = (PASStoredProcedure) Utils.getBean( "cancelPolicyProc" );
		
		if( LOGGER.isInfo() ) LOGGER.info( "Invoking CANCEL_POLICY procedure with inputs {[" );
		/*Extra parameter added for cancellation procedure - EndorsementID*/
		/*
		 * For endt the signature is changed to take in endt start date. 
		 */
		/*
		 * For cancellation the the validity start date is sysdate date, because for cancellation we call the del location proc where vsd is passed from application.
		 * 
		 */
		Map results = sp.call( policyVO.getPolLinkingId(), ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE) , newPremium, SvcUtils.getUserId( policyVO ), policyVO.getNewEndtId() , policyVO.getEndEffectiveDate());
		
		
		List<TTrnPolicyQuo> cancledPolRecs = getHibernateTemplate().find( "from TTrnPolicyQuo pol where pol.polLinkingId=? and pol.id.polEndtId =? ", policyVO.getPolLinkingId(),cancelationEndtId);
		
		for( TTrnPolicyQuo cancledPolRec : cancledPolRecs ){
			cancledPolRec.setPolModifiedDt( getSysDate() );
			cancledPolRec.setPolModifiedBy( SvcUtils.getUserId( policyVO ) );
			/*
			 * Licensed by /approved by fields should capture the whoever has endorsed the policy.
			 */
			if( !SvcUtils.getUserId( policyVO).equals( cancledPolRec.getPolApprovedBy() ) ){
				cancledPolRec.setPolApprovedBy( SvcUtils.getUserId( policyVO) );
			}
			if( !SvcUtils.getUserId( policyVO).equals( cancledPolRec.getPolUserId())){
				cancledPolRec.setPolUserId( SvcUtils.getUserId( policyVO) );
			}
			getHibernateTemplate().saveOrUpdate( cancledPolRec );

		}
		
		List<TMasCashCustomerQuo> cancledRecs =  getHibernateTemplate().find(
				"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId in (select distinct (pol.id.polPolicyId) from TTrnPolicyQuo pol where pol.polLinkingId = ? and pol.id.polEndtId = ? ) " +
				"and cashCustomerQuo.id.cshValidityStartDate = ( select distinct ( pol.polValidityStartDate ) from TTrnPolicyQuo pol where pol.polLinkingId = ? and pol.id.polEndtId = ? )",
				policyVO.getPolLinkingId(), cancelationEndtId,policyVO.getPolLinkingId(), cancelationEndtId);
		
		for(TMasCashCustomerQuo cancledRec: cancledRecs){
			cancledRec.setCshModifiedBy( SvcUtils.getUserId( policyVO) );
			cancledRec.setCshModifiedDt( getSysDate() );
			getHibernateTemplate().saveOrUpdate( cancledRec );
		}
		
		if( LOGGER.isInfo() ) LOGGER.info( "Execution of CANCEL_POLICY completed [result =" + results );
		
		EndorsmentVO endorsmentVO = policyVO.getEndorsements().get(0);
		/*
		 * In case of cancellation, the policy id will not be available in policy VO
		 */
	
		//++Start Changed by Mindtree On 08/09/2015 for SIT defect fix for error during cancel when PAR,Travel and GIT selected
		//Below code commented to avoid null pointer after cancel. This method DAOUtils.getBaseSectionPolicyId is moved to top
		//endorsmentVO.setPolicyId( DAOUtils.getBaseSectionPolicyId ( policyVO,getHibernateTemplate()) );
		endorsmentVO.setPolicyId(basePolicyId);
//++Start Changed by Mindtree On 08/09/2015 for SIT defect fix during cancel when PAR,Travel and GIT selected
		
		endorsmentVO.setEndtId( policyVO.getNewEndtId() );
		/* Set audit details in order to update the same within T_TRN_ENDORSEMENT_DETAIL table */
		endorsmentVO.setRecCreatedBy( SvcUtils.getUserId( policyVO ) );
		endorsmentVO.setRecCreaDate( getSysDate() );
		endorsmentVO.setEndSecId(0); // added for 90730 edit text for user level 3
		// Setting the endorsement text again after calling the CANCEL_POLICY procedure.
		//endorsmentVO.setEndText(generateEndorsementTextForCancelPolicy(policyVO)); // Premium getting set wrong (Need to test)
		//DAOUtils.saveOrUpdateEndorsementText( endorsmentVO, getHibernateTemplate() );
		
		sp = (PASStoredProcedure) Utils.getBean( "valExpDates" );
		try{
			sp.call( policyVO.getPolLinkingId(), policyVO.getNewEndtId(), (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
		}catch( DataAccessException e ){
			throw new BusinessException( "pas.convertTopolicy.exception", e, "An exception occured while executing stored proc_3" );
		}
		
		getHibernateTemplate().flush();
		if(!Utils.isEmpty(policyVO.getPremiumVO()) && !Utils.isEmpty(policyVO.getPremiumVO().getPolicyFees()))
		{
			policyVO.getPremiumVO().setPolicyFees(0.0);
		}
		updateSpecialPremium( policyVO ); 
		
		/* To insert record to T_TRN_ENDORSEMENT table  */
		sp = (PASStoredProcedure) Utils.getBean("getCreateEndtRecSP");
		try{
			sp.call( policyVO.getPolLinkingId(), policyVO.getNewEndtId() , SvcUtils.getUserId( policyVO ) ,ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE) );
		}
		catch( DataAccessException e ){
			throw new BusinessException( "pas.ConfirmEndt.exception", e, "An exception occured while inserting into T_TRN_ENDORSEMENT." );
		}
		
		
		/*Procedure call added to insert credit details in case of cancellation*/
		
		PASStoredProcedure sproc = (PASStoredProcedure) Utils.getBean("insertPaymentDetailsSP");
		if( LOGGER.isInfo() ) LOGGER.info( "Invoking insertPaymentDetailsSP procedure with inputs {[" );
		
		/*Added to calculate prorate premium(Rounded to 3 digits) for SBS */
		double refundAmt = getProRateRefundAmt(policyVO,endorsmentVO.getOldPremiumVO().getPremiumAmt());		
		results = sproc.call(policyVO.getPolLinkingId() , policyVO.getNewEndtId(), refundAmt ,null,null,null,null,"N",null);
		//results = sproc.call(policyVO.getPolLinkingId() , policyVO.getNewEndtId(), PremiumHelper.getRefundAmountForCancelPolicy(policyVO, endorsmentVO.getOldPremiumVO().getPremiumAmt()) ,null,null,null,null,"N",null);
		if( LOGGER.isInfo() ) LOGGER.info( "Execution of insertPaymentDetailsSP completed [result =" + results );
		// Commented for radar Id 47 existing issue
		//endorsmentVO.setEndText(generateEndorsementTextForCancelPolicy(policyVO)); // Premium getting set wrong (Need to test)
		DAOUtils.saveOrUpdateEndorsementText( endorsmentVO, getHibernateTemplate() );
		
		/* Update endtId obtained for processing to both fields within PolicyVO i.e. policyVO.endtId and policyVO.newEndtId in order
		 * to avoid discrepancies in reports immediately after endorsement confirmation */
		DAOUtils.syncUpEndtIdPolicyVO(policyVO, cancelationEndtId);
		
		ThreadLevelContext.clearAll();
		return policyVO;
		
	}

	private void updateSpecialPremium( BaseVO baseVO ){
		PolicyVO policyVO = (PolicyVO) baseVO;
		Long endtIdToProcess = SvcUtils.getLatestEndtId( policyVO );
		if(policyVO.getAppFlow().equals( Flow.AMEND_POL )){
			List<TTrnPolicyQuo> policyQuoList = getHibernateTemplate().find(
					"from TTrnPolicyQuo policyQuo where policyQuo.polLinkingId=? and policyQuo.id.polEndtId=? and policyQuo.polValidityExpiryDate=? and policyQuo.polPolicyType=?", policyVO.getPolLinkingId(),
					endtIdToProcess,SvcConstants.EXP_DATE, Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ) );
			short baseClassCode = getBaseClassCode( policyQuoList);
			for(TTrnPolicyQuo policyQuo : policyQuoList){
				PremiumHelper.updateSpecialCovers( policyVO, policyQuo, getHibernateTemplate() );
				if(policyQuo.getPolClassCode() == baseClassCode)
				{
					PremiumHelper.updatePolicyFee(policyVO,policyQuo,getHibernateTemplate());
				}
				PremiumHelper.updateGovtTax( policyVO, policyQuo, getHibernateTemplate() );
			}
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
	 * Returns endorsementText which is generated by invoking the function pkg_endt_gen.get_endt_text_cancel_pol
	 * @param policyVO
	 * @return
	 */
	private String generateEndorsementTextForCancelPolicy(PolicyVO policyVO) {
		
		//DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT ); 

		Double refundAmount = 0.0;

		SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );

		String endExpiryDate = sdf.format(policyVO.getEndEffectiveDate());
		// Change made to get the new Endorsment ID to get the New and Old Annualized Premium.
		Long endtIdToProcess = DAOUtils.getEndtToProcess(getHibernateTemplate(),policyVO);
		DataHolderVO<HashMap<String, Double>> premiumHolder =  PremiumHelper.getOldNewPremium(policyVO.getPolLinkingId(),endtIdToProcess);
		refundAmount =  premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM )  -   premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM ) ;
		refundAmount =   Math.abs( refundAmount ) ;

		//String sqlQuery = "SELECT PKG_ENDT_GEN.get_endt_text_cancel_pol('" + endExpiryDate + "'," + decForm.format( refundAmount ) + ") FROM DUAL";
		String sqlQuery = "SELECT PKG_ENDT_GEN.get_endt_text_cancel_pol('" + endExpiryDate + "'," + Currency.getUnformttedScaledCurrency(refundAmount) + ") FROM DUAL";		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		String endText = "";

		Query query = session.createSQLQuery( sqlQuery );

		List<Object> results = query.list();

		if( !Utils.isEmpty( results ) ){

		endText = results.get(0).toString();
		//Commented for Test Defect No : 37;Test Name:Release 3.7 
		//if( !endText.equals( policyVO.getEndorsements().get( 0 ).getEndText() ) ){
			//endText = policyVO.getEndorsements().get( 0 ).getEndText();
		//}

		}

		return endText;

	}

	@Override
	public BaseVO getActivePolicy( BaseVO baseVO ){

		TTrnPolicyQuo tTrnPolicyQuo = null;
		List<TTrnPolicyQuo> listTTrnPolicyQuo = null;
		PolicyVO policyVO = null;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				policyVO = (PolicyVO) baseVO;
				DataHolderVO<Long[]> outputHolder = new DataHolderVO<Long[]>();
				try{
					listTTrnPolicyQuo = getHibernateTemplate().find( "from TTrnPolicyQuo policy where  policy.polLinkingId = ?  order by policy.polEndtNo desc ",
							policyVO.getPolLinkingId() );
					if( !Utils.isEmpty( listTTrnPolicyQuo ) ) tTrnPolicyQuo = listTTrnPolicyQuo.get( 0 );
					if( !Utils.isEmpty( tTrnPolicyQuo ) ){
						Long[] endIdNo = new Long[ 2 ];
						if( !Utils.isEmpty( tTrnPolicyQuo.getPolEndtNo() ) ) endIdNo[ 0 ] = tTrnPolicyQuo.getPolEndtNo();
						if( !Utils.isEmpty( tTrnPolicyQuo.getId().getPolEndtId() ) ) endIdNo[ 1 ] = tTrnPolicyQuo.getId().getPolEndtId();
						outputHolder.setData( endIdNo );
					}

				}
				catch( HibernateException e ){
					e.printStackTrace();
					throw new BusinessException( "pas.gi.couldNotGetCustDetails", e, "Error while trying to SELECT policy details from T_TRN_POLIC_4" );
				}
				return outputHolder;
			}
		}
		return baseVO;

	}

	/**
	 * checkEndtEffectiveDate method is used to check wether entered endt effective date is less than previous endt effective date.
	 * 
	 */
	@Override
	public BaseVO isEndtEffectiveDateValid( BaseVO baseVO ){
		if( LOGGER.isInfo() ) LOGGER.info( "Entering Endt Effective date check method." );
		PolicyVO policyVO = null;
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				policyVO = (PolicyVO) baseVO;

				/*
				 * fetch max endt effective date for the given linking id.
				 */

				DataHolderVO<Object[]> holder = new DataHolderVO();
				Object[] data = { policyVO, "" };

				//Timestamp sysdate = getSysDate();

				/* First validate the user-entered endt effective date. Then check if it is greater than previous endt effective date. 
				 * if the below if-condition passes then do not let the user proceed till a valid date is selected which satisfies 
				 * month closing/opening criteria. 
				 * if the below if-condition fails, then user entered value is valid. then check with prev endt in the else part*/

				//Timestamp revisedStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), sysdate, Flow.AMEND_POL );

				//SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
				//Integer revisedStrtDate = Integer.valueOf( sdf.format( revisedStartDate ) );
				//Integer effDate = Integer.valueOf( sdf.format( policyVO.getEndEffectiveDate() ) );
				//Integer effDate = Integer.valueOf( sdf.format( policyVO.getEndStartDate() ) );

				// Phase 3 - This validation is not required.
				/*if( !( sysdate.equals( revisedStartDate ) ) && ( effDate < revisedStrtDate ) ){ //policyVO.getEndEffectiveDate().before(revisedStartDate) 
					 Current month is not open for business and the entered date is before the next month-opening date,
					then show an error message
					data[ 1 ] = "closingDate";
					LOGGER.info( "Amend::Closing date does not fall in the current business month." );

				}
				else{
*/
					Date endtEffectiveDate = (Date) getHibernateTemplate().find(
							"select trunc(max(polEndtEffectiveDate)) from TTrnPolicyQuo policy where  policy.polLinkingId = ?", policyVO.getPolLinkingId() ).get( 0 );
				
					if( !Utils.isEmpty( endtEffectiveDate ) ){
						if( endtEffectiveDate.after( policyVO.getEndEffectiveDate() ) ){

							/*   if previous endt effective date is more than entered endt effective date then set false.
							 * 
							 	*/

							//data[1] = false;
							data[ 1 ] = "effectiveDate";
							LOGGER.info( "Amend::if previous endt effective date is more than entered endt effective date then set false." );
						}
					}

				//}

				holder.setData( data );
				return holder;
			}
		}
		return baseVO;
	}
	

	@Override
	public BaseVO isRenewalQuoteExists( BaseVO baseVO ){
		DataHolderVO<Boolean> renQuoteExists= new DataHolderVO<Boolean>();
		//PolicyVO policyVO = (PolicyVO)baseVO;
		// Use sql query as we need to query the t_trn_policy_quo table
		String policySql = "select count(1) from t_trn_policy_quo where pol_ref_policy_no = ? and pol_ref_policy_year = ?";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(policySql);
		if(baseVO instanceof PolicyVO)
		{
			PolicyVO policyVO = (PolicyVO)baseVO;
			sqlQuery.setParameter( 0, policyVO.getPolicyNo() );
			sqlQuery.setParameter( 1, Integer.valueOf(SvcUtils.getYearFromDate(policyVO.getScheme().getEffDate())).shortValue() );
		}
		else if(baseVO instanceof CommonVO)
		{
			CommonVO commonVO = (CommonVO)baseVO;
			sqlQuery.setParameter( 0, commonVO.getPolicyNo() );
			// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
			//sqlQuery.setParameter( 1, new Integer(SvcUtils.getYearFromDate(commonVO.getPolEffectiveDate())).shortValue() );	
			sqlQuery.setParameter( 1, Integer.valueOf(SvcUtils.getYearFromDate(commonVO.getPolEffectiveDate())).shortValue() );	
		}
		Integer NoOfRec=Integer.valueOf(sqlQuery.uniqueResult().toString());
		if(NoOfRec>0) {
			renQuoteExists.setData( new Boolean(true)) ;
		} else {
			renQuoteExists.setData( new Boolean(false)) ;
		}
		return renQuoteExists;
}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.endorse.dao.IAmendPolicyDao#getLatestEndorsedPolicyDataVO(com.mindtree.ruc.cmn.base.BaseVO)
	 * 
	 *  This method return TTrnPolicyQuo for the give policy number
	 */
	@Override
	public TTrnPolicyQuo getLatestEndorsedPolicyDataVO( BaseVO baseVO ){
		
		if(baseVO instanceof PolicyDataVO){
			PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
			return (TTrnPolicyQuo) getHibernateTemplate().find( QueryConstants.FETCH_LATEST_ENDORESED_RECORD , policyDataVO.getCommonVO().getPolicyId() ).get( 0 );
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.endorse.dao.IAmendPolicyDao#checkForEffectiveDate(com.mindtree.ruc.cmn.base.BaseVO, java.lang.Object[])
	 * 
	 * This method checks for effective date entered for amendment is valid or not (for Home and Travel LOB)
	 */
	@Override
	public void checkForEffectiveDate( BaseVO baseVO, Object[] data ){
		
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		
		// Adding one day extra to Policy Expiry date
		boolean isCancelPol = false;
        if(!Utils.isEmpty(policyDataVO.isPolicyCancel())){
               isCancelPol = policyDataVO.isPolicyCancel(); 
         }
        if(!isCancelPol){
        Date polExpiryDate = policyDataVO.getScheme().getExpiryDate();
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(polExpiryDate); 
        cal.add(Calendar.DATE, 1);
        polExpiryDate = cal.getTime();
        policyDataVO.getScheme().setExpiryDate(polExpiryDate); 
        }
	

		
		//Timestamp sysdate = getSysDate();

		/* First validate the user-entered endt effective date. Then check if it is greater than previous endt effective date. 
		 * if the below if-condition passes then do not let the user proceed till a valid date is selected which satisfies 
		 * month closing/opening criteria. 
		 * if the below if-condition fails, then user entered value is valid. then check with prev endt in the else part*/

		//Timestamp revisedStartDate = DAOUtils.getValidityStartDate( getHibernateTemplate(), sysdate, Flow.AMEND_POL );

		//SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
		//Integer revisedStrtDate = Integer.valueOf( sdf.format( revisedStartDate ) );
		//Integer effDate = Integer.valueOf( sdf.format( policyDataVO.getEndEffectiveDate() ) );

		// Phase 3 - This validation is not required.
		/*if( !( sysdate.equals( revisedStartDate ) ) && ( effDate < revisedStrtDate ) ){ //policyVO.getEndEffectiveDate().before(revisedStartDate) 
			 Current month is not open for business and the entered date is before the next month-opening date,
			then show an error message
			data[ 1 ] = "closingDate";
			LOGGER.info( "Amend::Closing date does not fall in the current business month." );

		}
		 check for endtEffDate < policy start date is added here
		else*/ 
		if(policyDataVO.getScheme().getEffDate().after( policyDataVO.getEndEffectiveDate() )){
			data[1] = "beforePolicyStart";
			LOGGER.info( "Amend :: Endorsement effective date should not be less than policy start date " );
		}
		/* check for endtEffDate > policy end date is added here */
		else if(policyDataVO.getScheme().getExpiryDate().before( policyDataVO.getEndEffectiveDate() )){
			data[1] = "afterPolicyEnd";
			LOGGER.info( "Amend:: Endorsement effective date should not be greater than policy expiry date" );
		}
		else{

			Date endtEffectiveDate = (Date) getHibernateTemplate().find(
					QueryConstants.FETCH_LATEST_ENDORSED_DATE, policyDataVO.getCommonVO().getPolicyId()).get( 0 );

		//   if previous endt effective date is more than entered endt effective date then do validate
			if( !Utils.isEmpty( endtEffectiveDate ) && (endtEffectiveDate.after( policyDataVO.getEndEffectiveDate() ))){
				data[ 1 ] = "effectiveDate";
				LOGGER.info( "Amend::if previous endt effective date is more than entered endt effective date then set false." );
			}

		}
		
	}
	
	private PASStoredProcedure getDelPendingProc( PolicyDataVO polData ){
		PASStoredProcedure sp;
		String procName = "";
		if(polData.getCommonVO().getLob().equals( LOB.HOME )){
			procName = "delPendingPolicyHome";
		}else if(polData.getCommonVO().getLob().equals( LOB.TRAVEL )){
			procName = "delPendingPolicyTravel";
		}else{
			procName = "delPendingEndorsementMonoline";
		}
		sp = (PASStoredProcedure) Utils.getBean( procName );
		return sp;
	}

	/*Added for Short Term Cancellation - OMAN */
	@Override
	public void checkForEffectiveDateShortTerm( BaseVO baseVO, Object[] data ){


		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		
		/*check for endtEffDate > policy start date in case of Short Term travel - ShortTerm_Cancellation*/
		 
		if(policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE) )
				&& (policyDataVO.getScheme().getEffDate().before( policyDataVO.getEndEffectiveDate() ))){// || policyDataVO.getScheme().getEffDate().before( new Date() ))){
			data[1] = "afterInceptionDate";
			LOGGER.info( "Amend :: Endorsement effective date should not be greater than policy start date" );
		}
		/*else if(policyDataVO.getScheme().getEffDate().after( new Date() ) && policyDataVO.getEndEffectiveDate().after(policyDataVO.getScheme().getEffDate())){
			data[1] = "afterInceptionButPolicyNotStarted";
			LOGGER.info( "Amend :: Endorsement effective date should not be greater than policy start date if Policy has not been started yet." );
		}*/	
	}
	
	/*Added to calculate prorate premium(Rounded to 3 digits) for SBS */
	private double getProRateRefundAmt(PolicyVO policyVO,double currentPremiumAmt) {		
		List <Object> result = null;
		Integer decPoint = 0;
		Integer scale = Currency.getPolicyTypeScaleMap().get(Short.valueOf(SvcConstants.SBS_POL_TYPE));		
		for( decPoint = 0; scale >= 10; decPoint++, scale /= 10 );
		Double refundAmount = 0.0;
		long polExpiryDays = PremiumHelper.getDifference(policyVO.getPolExpiryDate(),policyVO.getEndEffectiveDate());
		int totalDays = (int)PremiumHelper.getDifference(policyVO.getPolExpiryDate(),policyVO.getScheme().getEffDate() );			
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
		//java.sql.Date sqlDate = new java.sql.Date(policyVO.getValidityStartDate().getTime()); 
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();			
		String GET_PRORATE_PREMIUM = "SELECT SUM(ROUND((NVL(PRM_PREMIUM,0) - (NVL(PRM_PREMIUM,0) * :polExpiryDays/:totalDays )),:decPoint)) as P_ANNUALIZED_PREMIUM FROM T_TRN_PREMIUM "+
					" WHERE PRM_POLICY_ID IN (SELECT POL_POLICY_ID FROM T_TRN_POLICY "+
					" where pol_linking_id= :polLinkingId and pol_endt_id<=:polEndId AND pol_issue_hour  = 3)"+
					"and prm_validity_start_date <= "+
					" (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = :polLinkingId AND pol_endt_id=:polEndId AND pol_issue_hour  = 3) "+
					"AND PRM_VALIDITY_EXPIRY_DATE  > (select distinct (pol_validity_start_date) from t_trn_policy where pol_linking_id = :polLinkingId AND pol_endt_id=:polEndId AND pol_issue_hour  = 3) "+
					" and prm_endt_id <= :polEndId";
		Query query = null;
		if(! sdf.format( policyVO.getScheme().getEffDate() ).equalsIgnoreCase( sdf.format( policyVO.getEndEffectiveDate() ) ) ){
			query = session.createSQLQuery(GET_PRORATE_PREMIUM);
			LOGGER.debug("QUERY >> "+query);
			query.setLong("polExpiryDays", polExpiryDays);
			query.setInteger("totalDays", totalDays);
		    query.setLong("polLinkingId", policyVO.getPolLinkingId());
			query.setLong("polEndId", policyVO.getEndtId());
			query.setInteger("decPoint", decPoint);
			//query.setDate("vsd", sqlDate);
			
			result = query.list();
			double totPremium = Double.parseDouble(result.get(0).toString());
			//refundAmount = currentPremiumAmt - totPremium ;
			refundAmount = totPremium;
			LOGGER.debug("refundAmount ="+refundAmount);
		}		
		return   Double.parseDouble(Currency.getUnformttedScaledCurrency(refundAmount));
	}
	
}
