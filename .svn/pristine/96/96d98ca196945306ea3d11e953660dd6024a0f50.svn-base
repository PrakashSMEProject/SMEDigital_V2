package com.rsaame.pas.policyAction.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnTask;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.AmendPolicySvc;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class PolicyActionCommonDAO extends BaseDBDAO implements IPolicyActionCommonDAO {

	private static final Logger logger = Logger.getLogger( PolicyActionDAO.class );
	private final static Byte COMPLETED_STATUS = 2;
	private final static Integer QUOTE_ACCEPT = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACCEPT" ) );
	private final static Integer QUOTE_REJECT = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REJECT" ) );
	private final static Integer QUOTE_DECLINED = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_DECLINED" ) );
	private final static Integer QUOTE_ACTIVE = Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) );
	private AmendPolicySvc amendPolicySvc;
	
	/* 
	 * Update the status in case of DECLINE_QUOTE
	 */
	@Override
	public BaseVO declineQuote( BaseVO baseVO ){
		updatePolicyRecAuthDetails( baseVO , QUOTE_DECLINED );
		updateTask( baseVO );
		referalDetails(baseVO , QUOTE_DECLINED );
		return baseVO;
	}

	/* 
	 * Update the status in case of REJECT_QUOTE
	 */
	@Override
	public BaseVO rejectQuote( BaseVO baseVO ){
		updatePolicyRecAuthDetails( baseVO , QUOTE_REJECT );
		updateTask( baseVO );
		return baseVO;
	}

	/* 
	 * Update the status in case of APPROVE_QUOTE
	 */
	@Override
	public BaseVO approveQuote( BaseVO baseVO ){
		updatePolicyRecAuthDetails( baseVO , QUOTE_ACCEPT  );
		updateTask( baseVO );
		referalDetails(baseVO , QUOTE_ACCEPT );
		
		return baseVO;
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
		CommonVO commonVO = polComHolder.getCommonDetails();

		Long endtId = commonVO.getEndtId();
		String[] statusResReferral = Utils.getMultiValueAppConfig("STATUS_RESOLVE_REFERRAL", ",");
		
		PolicyDataVO polData = (PolicyDataVO)amendPolicySvc.invokeMethod( "retrievePolicyDataVO", commonVO );
		
		List<TTrnPolicyQuo> policyQuoList ;
		
		if( !Utils.isEmpty( statusResReferral ) && statusResReferral.length > 0
				&& CopyUtils.asList( statusResReferral ).contains( polStatus.toString() )){
		
			/* Fetch only the latest endorsementId records to update status */
			policyQuoList = getHibernateTemplate().find(
					"from TTrnPolicyQuo policyQuo where policyQuo.id.polPolicyId=? and policyQuo.id.polEndtId=? and policyQuo.polValidityExpiryDate=?  and policyQuo.polIssueHour = ?", commonVO.getPolicyId(),
					endtId,SvcConstants.EXP_DATE, SvcConstants.POL_ISSUE_HOUR );
		
		}else{
			policyQuoList = getHibernateTemplate().find(
					"from TTrnPolicyQuo policyQuo where policyQuo.id.polPolicyId=? and (policyQuo.id.polEndtId=? or (policyQuo.id.polEndtId<? and policyQuo.polValidityExpiryDate=?)) and policyQuo.polIssueHour = ?", commonVO.getPolicyId(),
					endtId,endtId,SvcConstants.EXP_DATE, SvcConstants.POL_ISSUE_HOUR );

		}
		BigDecimal polPrm = null;
		
		if( commonVO.getIsQuote() ){
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( QueryConstants.FETCH_TOTAL_POLICY_PRM_QUO_TOTAL,(HibernateTemplate) Utils.getBean( "hibernateTemplate" ), commonVO.getPolicyId(), endtId );
			
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				polPrm = ( (BigDecimal) valueHolder.get( 0 ) );
			}
		}else{
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( QueryConstants.FETCH_TOTAL_POLICY_PRM_TOTAL,(HibernateTemplate) Utils.getBean( "hibernateTemplate" ), commonVO.getPolicyId(), endtId );
			
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				polPrm = ( (BigDecimal) valueHolder.get( 0 ) );
			}
		}
		
		
		
		//Double policyFees = ( policyVO.getPremiumVO().getPolicyFees() ) / policyQuoList.size();
		//AuthenticationInfoVO authVO = policyVO.getAuthInfoVO();
		//com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

		for( TTrnPolicyQuo policyQuo : policyQuoList ){
			//policyQuo.setPolGovernmentTax( converter.getAFromB( policyVO.getPremiumVO().getGovtTax() ) );
			//policyQuo.setPolPolicyFees( converter.getAFromB( policyFees ) );
			policyQuo.setPolModifiedBy(SvcUtils.getUserId(commonVO));
			policyQuo.setPolApprovedInd("Y"); // The status of the quote is approved and the indicator is set to Y
			policyQuo.setPolModifiedDt( getSysDate() );
			
			policyQuo.setPolPrintDate( null );
			
			/* If Quote status is active then we need not update Approved By,Licensed By id's and approved date */
			if( !( polStatus == QUOTE_ACTIVE ) ){
				policyQuo.setPolApprovedBy( SvcUtils.getUserId( commonVO ) );
				
				if( !Utils.isEmpty( polData ) && !Utils.isEmpty( polData.getAuthenticationInfoVO() ) && !Utils.isEmpty( polData.getAuthenticationInfoVO().getLicensedBy() ) ){
					policyQuo.setPolUserId(polData.getAuthenticationInfoVO().getLicensedBy());
				}
				else{
					policyQuo.setPolUserId( SvcUtils.getUserId( commonVO ) );
				}
				//policyQuo.setPolApprovalDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );
				policyQuo.setPolApprovalDate( getSysDate() );
			}
			/*
			 * Licensed by /approved by fields should capture the whoever has issued the quote.
			 */
					
			if( (!SvcUtils.getUserId( commonVO).equals( policyQuo.getPolApprovedBy() )) &&  (!commonVO.getStatus().equals(QUOTE_ACCEPT)) ){
				policyQuo.setPolApprovedBy( SvcUtils.getUserId( commonVO) );
			}
			
			if( !SvcUtils.getUserId( commonVO).equals( policyQuo.getPolUserId())){
				if( !Utils.isEmpty( polData ) && !Utils.isEmpty( polData.getAuthenticationInfoVO() ) && !Utils.isEmpty( polData.getAuthenticationInfoVO().getLicensedBy() ) ){
					policyQuo.setPolUserId(polData.getAuthenticationInfoVO().getLicensedBy());
				}
				else{
					policyQuo.setPolUserId( SvcUtils.getUserId( commonVO ) );
				}
			}
					
				
			getHibernateTemplate().flush();
			
			//PremiumHelper.updateSpecialCovers(policyVO,policyQuo,getHibernateTemplate());
			//PremiumHelper.updateGovtTax( policyVO, policyQuo, getHibernateTemplate() );
				
			//Oman Change. Check for minimum premium
			
			//minPrmValidation(policyVO);
			
			/*
			 * POL_PREMIUM needs to be updated with class level premium. This method will fetch the premium for a class from premium table and update the 
			 * policy table 
			 */
			//PremiumHelper.updatePolicyPremium(policyVO,policyQuo,getHibernateTemplate() );
			
			
			com.rsaame.pas.cmn.converter.IntegerByteConverter byteConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			if(!Utils.isEmpty( polComHolder)&& !Utils.isEmpty( polComHolder.getComments())&&!Utils.isEmpty( polComHolder.getComments().getPolicyStatus()) ){
				policyQuo.setPolStatus(polComHolder.getComments().getPolicyStatus());
				if(!Utils.isEmpty(polPrm)){
					policyQuo.setPolPremium(polPrm);
				}
			}
			else{
				policyQuo.setPolStatus( byteConverter.getBFromA( polStatus ));
				if(!Utils.isEmpty(polPrm)){
					policyQuo.setPolPremium(polPrm);
				}
			}
			//policyQuo.setPolValidityStartDate((Date)ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE));
			getHibernateTemplate().merge( policyQuo );
			
			//authVO.setAccountingDate( policyQuo.getPolIssueDate() );
			
			logger.info( "Policy Id :"+policyQuo.getId().getPolPolicyId() );
		}
		
		/* Clear all values that have been added to the ThreadLevelContext */
		//clearThreadLevelContext();

	}

	/**
	 * @param baseVO
	 * This method is used to update the status in t_trn_task table after APPROVE/REJECT/DECLINE quote
	 */
	private void updateTask( BaseVO baseVO ){
		PolicyCommentsHolder polComHolder = (PolicyCommentsHolder) baseVO;
		CommonVO commonVO = polComHolder.getCommonDetails();
		String documentId;
		if(commonVO.getIsQuote()){
			 documentId = commonVO.getPolicyId() + "-" + commonVO.getEndtId()+"-"+commonVO.getQuoteNo();
		} else {
			 documentId = commonVO.getPolicyId() + "-" + commonVO.getEndtId()+"-"+commonVO.getPolicyNo();
		}
		//String documentId = commonVO.getPolicyId() + "-" + commonVO.getEndtId()+"-"+commonVO.getPolicyNo();
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
		CommonVO commonVO = polComHolder.getCommonDetails();
		List<TTrnPasReferralDetails> refList = getHibernateTemplate().find("from TTrnPasReferralDetails where id.refPolicyId=? and id.refEndId=? and refStatus = ? ",commonVO.getPolicyId(),commonVO.getEndtId(), SvcConstants.POL_STATUS_REFERRED.toString() );
		for(TTrnPasReferralDetails ref : refList){
			ref.setRefStatus(status.toString());
			getHibernateTemplate().merge( ref );
		}
	}

	/**
	 * @return the amendPolicySvc
	 */
	public AmendPolicySvc getAmendPolicySvc(){
		return amendPolicySvc;
	}

	/**
	 * @param amendPolicySvc the amendPolicySvc to set
	 */
	public void setAmendPolicySvc( AmendPolicySvc amendPolicySvc ){
		this.amendPolicySvc = amendPolicySvc;
	}
	
	

}
