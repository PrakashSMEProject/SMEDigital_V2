package com.rsaame.pas.quote.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.TempPasReferralDAO;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.gen.mapper.PolicyVOToCashCustomerPOJO;
import com.rsaame.pas.pojo.mapper.PolicyVOToTMasInsuredPOJO;
import com.rsaame.pas.pojo.mapper.TMasInsuredQuoToGenVOMapperReverse;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

public class SaveClaimsHistoryDAO extends BaseDBDAO implements ISaveClaimsHistDAO{
	private static final Logger logger = Logger.getLogger( SaveClaimsHistoryDAO.class );
	private static final String POLICY_LINKING_ID_SEQ = "SEQ_QUOTATION_ID";
	private static String DEFAULT_ENDT_ID = "DEFAULT_ENDTID_QUOTE";
	private static Integer PAS_POL_ISSUE_HOUR=Integer.valueOf( Utils.getSingleValueAppConfig( "E_PLATFORM_APP_CODE" ) );
	
	public BaseVO saveClaimsHistory( BaseVO baseVO ){

		PolicyVO policyVO = (PolicyVO) baseVO;
		GeneralInfoVO generalInfoVO = policyVO.getGeneralInfo();
		

		String vatRegNo = generalInfoVO.getInsured().getVatRegNo();
		Integer vatCode=policyVO.getPolVATCode();
		
		Long endtId = Long.valueOf((String)Utils.getSingleValueAppConfig(DEFAULT_ENDT_ID));
		
		Long policyId = policyVO.getPolicyNo();
		
		Long insInsuredCode = null;
		
		Date insDateCollectionKyc = null;
		Date insExpDate = null;
		String insEEmailId = null;
		
		Timestamp valStartDate = new Timestamp( policyVO.getValidityStartDate().getTime());
				
		if( logger.isDebug() ) logger.debug( "Validity start date-->" + valStartDate );
		
		TMasCashCustomerQuo tMasCashCustomerQuo = null;
		
		TMasInsured tMasInsured = null;
		TMasCashCustomerQuo tMasCashCustomerQuoSelected = null;
		
		Session session = null;
		SQLQuery sqlQuery = null;
		session = getHibernateTemplate().getSessionFactory().getCurrentSession();

		BaseBeanToBeanMapper<GeneralInfoVO, TMasInsured> tmasinsuredPojoGeneralInfoVoMapper = (BaseBeanToBeanMapper) BeanMapperFactory
		.getMapperInstance( TMasInsuredQuoToGenVOMapperReverse.class );
		
		tMasInsured = (TMasInsured) tmasinsuredPojoGeneralInfoVoMapper.mapBean(generalInfoVO,tMasInsured);
		insInsuredCode = tMasInsured.getInsInsuredCode();
		insDateCollectionKyc = tMasInsured.getInsDtCollectionKyc();
		insExpDate = tMasInsured.getInsExpiryDate();
		insEEmailId = tMasInsured.getInsEEmailId();
		
		/* update t_mas_insured table only if user wants to update the details to master table. 
		 * */
		if(!Utils.isEmpty( policyVO.getGeneralInfo()) && !Utils.isEmpty( policyVO.getGeneralInfo().getInsured()) &&
				!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getUpdateMaster()) &&	policyVO.getGeneralInfo().getInsured().getUpdateMaster().equals( true ) ){
		
			TMasInsured tmasInsured1 = (TMasInsured) getHibernateTemplate().find( "from TMasInsured tMasInsured where tMasInsured.insInsuredCode=?", insInsuredCode ).get( 0 );
			tmasInsured1.setInsDtCollectionKyc( insDateCollectionKyc );
			tmasInsured1.setInsExpiryDate( insExpDate );
			tmasInsured1.setInsEEmailId( insEEmailId );
			/*VAT*/
			tmasInsured1.setInsVatRegNo( vatRegNo );
			
			

			BaseBeanToBeanMapper<PolicyVO, TMasInsured> polVOToIns = BeanMapperFactory.getMapperInstance( PolicyVOToTMasInsuredPOJO.class );
			tmasInsured1 = polVOToIns.mapBean( policyVO, tmasInsured1 );
			getHibernateTemplate().merge( tmasInsured1 );
		}
		
		
		/*
		 * 
		 * Insert / update under writing details. In case there are policy level uw q's those needs to be saved, with location ID as 0
		 */
		
		if( !Utils.isEmpty( policyVO ) && !Utils.isEmpty( policyVO.getGeneralInfo() ) && !Utils.isEmpty( policyVO.getGeneralInfo().getQuestionsVO() ) )
		{
			DAOUtils.saveOrUpdateUWQS(policyVO.getGeneralInfo().getQuestionsVO(),policyId,endtId,policyVO.getValidityStartDate(),getHibernateTemplate());
		}
		
		
		
		/*
		 * Updating T_MAS_CUSTOMER table records for every class entries. 
		 */
		
		List<TTrnPolicyQuo> policy = DAOUtils.getPolRecForQuo( getHibernateTemplate(), policyVO.getEndtId(), policyVO.getQuoteNo() );
		
		for( TTrnPolicyQuo trnPolicy : policy ){

			if( Utils.isEmpty( trnPolicy ) ){
				throw new BusinessException( "cmn.systemError", null, "Could not find basic policy record during pol gen save" );
			}

			try{
				
				if( !Utils.isEmpty( trnPolicy.getId() ) && !Utils.isEmpty( trnPolicy.getId().getPolPolicyId() ) ){

					tMasCashCustomerQuo = (TMasCashCustomerQuo) getHibernateTemplate().find( "from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? and  cashCustomerQuo.id.cshValidityStartDate=?",
							trnPolicy.getId().getPolPolicyId(),trnPolicy.getPolValidityStartDate() ).get( 0 );
					
					/*
					 * tMasCashCustomerQuo.getCshCtyCode() is the country code, the acc code is based on country
					 */
					if( !Utils.isEmpty( tMasCashCustomerQuo.getCshCustomerId() ) && !Utils.isEmpty( tMasCashCustomerQuo.getCshCity() )
							&& !Utils.isEmpty( tMasCashCustomerQuo.getCshRegCode() ) && !Utils.isEmpty( tMasCashCustomerQuo.getCshLocCode() ) ){
						String GET_CU_TOT_ACC_CODE = "select cu_tot_acc_code from T_MAS_CUSTOMER  where CU_CUSTOMER_ID=" + tMasCashCustomerQuo.getCshCustomerId()
								+ " and CU_CTY_CODE=" + tMasCashCustomerQuo.getCshCtyCode() + " and CU_REG_CODE=" + tMasCashCustomerQuo.getCshRegCode() + " and CU_LOC_CODE="
								+ tMasCashCustomerQuo.getCshLocCode();
						session = getHibernateTemplate().getSessionFactory().getCurrentSession();
						Query query = session.createSQLQuery( GET_CU_TOT_ACC_CODE );
						List<Object> result = query.list();
						
						if( result.size() > 0 ){
							tMasCashCustomerQuo.setCshTotAccCode( (BigDecimal) result.get( 0 ) );
						}
					}

				//	Timestamp valStartDateToBeSaved = new Timestamp( tMasCashCustomerQuo.getId().getCshValidityStartDate().getTime() );
					
					// TODO : above query is a temporary fix. Need to search using validityStartDate also as below since policyId is not unique.

					/*BaseBeanToBeanMapper<PolicyVO, TMasCashCustomerQuo> policyVoCashCustomerMapper = (BaseBeanToBeanMapper) BeanMapperFactory
							.getMapperInstance( PolicyVOToCashCustomerPOJO.class );

					tMasCashCustomerQuo = (TMasCashCustomerQuo) policyVoCashCustomerMapper.mapBean( policyVO, tMasCashCustomerQuo );

					tMasCashCustomerQuo.getId().setCshValidityStartDate( valStartDateToBeSaved );
					tMasCashCustomerQuo.getId().setCshPolicyId( policyVO.getPolicyNo() );
					if( logger.isDebug() ) logger.debug( "tMasCashCustomerQuo obtained after changing business type-->" + tMasCashCustomerQuo );
*/
					
			
					tMasCashCustomerQuoSelected = CopyUtils.copySerializableObject( tMasCashCustomerQuo ); //fetchCashCustomerId( tMasCashCustomerQuo, session );
					
					/*VATTTTTT*/
					//tMasCashCustomerQuoSelected.setPolVatTax(new BigDecimal("5"));
					
		//			tMasCashCustomerQuoSelected.setCshVatRegNo("Checking");
			
			//	tMasCashCustomerQuoSelected.setCshAZipCode("10420");
					/* Update Claims History Details to Cash Customer Quo Table  

					 For broker add info section is hidden hence paytems was not getting saved but when assigned to rsa user it should auto populate with default as agreed during resolve referral hence the below fix 
					*/
					if( !Utils.isEmpty( tMasCashCustomerQuoSelected ) ){
						if( tMasCashCustomerQuoSelected.getCshAIdCardNo() == null ){
							tMasCashCustomerQuoSelected.setCshAIdCardNo( "7" );
						}
						/*tMasCashCustomerQuoSelected.setCshLossAmt( tMasCashCustomerQuo.getCshLossAmt() );
						tMasCashCustomerQuoSelected.setCshLossRatio( tMasCashCustomerQuo.getCshLossRatio() );
						tMasCashCustomerQuoSelected.setCshBusType( tMasCashCustomerQuo.getCshBusType() );
						tMasCashCustomerQuoSelected.setCshModifiedBy( null );// Modified by id need not be updated during quote record creation 
						tMasCashCustomerQuoSelected.setCshModifiedDt( null );// Modified date need not be updated during quote record creation 
*/					}
					getHibernateTemplate().evict( tMasCashCustomerQuo );
					getHibernateTemplate().saveOrUpdate( tMasCashCustomerQuoSelected );
					getHibernateTemplate().flush();
					getHibernateTemplate().evict( tMasCashCustomerQuoSelected );
						
					//updateCashCustomerDetails( tMasCashCustomerQuo );
					// TODO : This is a dirty fix. because we are manually setting validity start date and also search to get the row from DB is not based on validity start date 
					//getHibernateTemplate().saveOrUpdate( tMasCashCustomerQuo );
					
					ThreadLevelContext.set( SvcConstants.TLC_KEY_VSD, tMasCashCustomerQuo.getId().getCshValidityStartDate());
					ThreadLevelContext.set( SvcConstants.TLC_KEY_ENDT_ID,tMasCashCustomerQuo.getCshEndtId() );
					
					SectionVO section =  new SectionVO( RiskGroupingLevel.LOCATION );
					section.setPolicyId( trnPolicy.getId().getPolPolicyId() );
					DAOUtils.updateCustomerDetails( policyVO, section, getHibernateTemplate() );
				}
			
				
			}
			catch( HibernateException hibernateException ){
				if( logger.isDebug() ) logger.debug( com.Constant.CONST_MESSAGE + hibernateException.getMessage() );
				throw new BusinessException( "pas.gi.couldNotUpdatePolIssueHour", hibernateException, "Error while trying to UPDATE polIssueHour to T_TRN_POLICY_QUO for ["
						+ policyId + "] policy id" );
			}

		}
		
		ThreadLevelContext.clear( SvcConstants.TLC_KEY_VSD );
		ThreadLevelContext.clear( SvcConstants.TLC_KEY_ENDT_ID );
		/* PolicyLinkingId under T_TRN_POLICY_QUO to be updated for the first entry i.e. class code 2
		 * put by FGBPM service. Updation should be done only if policyLinkingId doesn't exist within PolicyVO
		 */
		
		if(Utils.isEmpty(policyVO.getPolLinkingId())) 
			updatePolRecordForQuote(policyVO);
		
		
		// Code for updating T_TRN_TEMP_PAS_REFERRAL table.
		
		//deleteAll( getHibernateTemplate().find( "from TTrnTempPasReferral tempreferral where tempreferral.id.tprPolLinkingId=?", policyVO.getPolLinkingId()) );
	
		/* To delete the existing records from temp referral table before actually checking if the referral is
		 * triggered for this insert/update operation which is done by checking is policyVO.getMapReferralVO is not
		 * null  */


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

		DAOUtils.deleteReferralRecordsForKey( policyVO.getPolLinkingId(), Long.valueOf(  Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_GENERAL )  ) , Integer.valueOf( Utils.getSingleValueAppConfig( RulesConstants.GENERAL ) ) , getHibernateTemplate()  );
		TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean( "tempPasReferralDAO" );
		ReferralVO referralVO = DAOUtils.getReferralVOForSave( policyVO );		

		/*Start of ticket 137704 */
		if (!Utils.isEmpty(referralVO)) {
			referralVO.setTprUserId(userId);
			referralVO.setTprUserRole(role);
		}
		/*End of ticket 137704 */

		insertTempPasReferalDao.insertReferal( referralVO );
		
		/*
		 * Update Policy Issue Hour i.e Application Id for the record inserted by General Info Save Operation.
		 * This will be done on each click of save operation as FGBPM service doesn't update
		 * POL_ISSUE_HOUR as part of SaveBasicInfo Business Process
		 */
		/*VAT Modification for Updation of Reg Number*/
		try{
			session.flush();
			Query hibernateQuery = null;
			/*
			 * modified code to set issue location and processing location of quote. 
			 */
			if(!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc())) && 
					!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc()))){
				/*VAT*/
				hibernateQuery = session.createQuery( "update TTrnPolicyQuo policyQuo set policyQuo.polvatCode='"+vatCode +  "',policyQuo.polVatRegNo = ' "+vatRegNo+"',policyQuo.polIssueHour=" + PAS_POL_ISSUE_HOUR + "," + 
						" policyQuo.polProcLocCode =:polProcLocCode , "+ "policyQuo.polLocationCode=:polLocCode "
					    + "," + " policyQuo.polModifiedBy = null, policyQuo.polModifiedDt = null, policyQuo.polApprovedBy = null , " +
							"policyQuo.polApprovalDate = null ,   pol_business_type = "+policyVO.getGeneralInfo().getInsured().getPolBusType()+
						"  where policyQuo.id.polPolicyId=:polId and policyQuo.id.polEndtId = :polEndtId" );
			
			} else {
		/*VAT*/
			
			hibernateQuery = session.createQuery( "update TTrnPolicyQuo policyQuo set policyQuo.polvatCode='"+vatCode+ "' ,policyQuo.polVatRegNo = ' "+vatRegNo+"',policyQuo.polIssueHour=" + PAS_POL_ISSUE_HOUR + "," + " policyQuo.polProcLocCode = "
					+ ServiceContext.getLocation() + "," + " policyQuo.polModifiedBy = null, policyQuo.polModifiedDt = null, policyQuo.polApprovedBy = null , " +
							"policyQuo.polApprovalDate = null ,  pol_business_type = "+policyVO.getGeneralInfo().getInsured().getPolBusType()
					+ "  where policyQuo.id.polPolicyId=:polId and policyQuo.id.polEndtId = :polEndtId" );
			
			}
			
			hibernateQuery.setParameter( com.Constant.CONST_POLID, policyId);
			hibernateQuery.setParameter( "polEndtId",endtId);
			
			if(!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc()))){
				hibernateQuery.setParameter( com.Constant.CONST_POLLOCCODE, policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc().shortValue());
			}
			
			if(!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc()))){
				hibernateQuery.setParameter( com.Constant.CONST_POLPROCLOCCODE, policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc());
			} 
			/*
			 * Backend defect fix. Print date is the date when user clicks on convert to policy. No need to update 
			 * the date here
			 */
		//	hibernateQuery.setParameter( "polPrntDate", policyVO.getValidityStartDate() );
			
			hibernateQuery.executeUpdate();
			
		}
		catch( HibernateException hibernateException ){
			if( logger.isDebug() ) logger.debug( com.Constant.CONST_MESSAGE + hibernateException.getMessage() );
			throw new BusinessException( "pas.gi.couldNotUpdatePolIssueHour", hibernateException, "Error while trying to UPDATE polIssueHour to T_TRN_POLICY_QUO for ["+ policyId +"] policy id");
		}
		
		return (BaseVO) policyVO;
	}


	public TMasCashCustomerQuo fetchCashCustomerId( TMasCashCustomerQuo cashCustomerQuo, Session sessionFromFetch ){
		if( logger.isDebug() ) logger.debug( "fetch cash customer details after select changes" );
		TMasCashCustomerQuo selectedCashCustomerQuo = null;
		//Sonar fix
		//Session session = null;
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getId().getCshPolicyId() + "   Policy Id " );
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getId().getCshValidityStartDate() + "   validity Start Date" );
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getCshBusType() + " CSH Business Type entered" );

		try{
			//Radar fix
			//session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query hibernateQuery = null;
		
			List<TMasCashCustomerQuo> cashCustomerQuoDetsList = getHibernateTemplate().find(
					"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? and cashCustomerQuo.id.cshValidityStartDate=? ",
					cashCustomerQuo.getId().getCshPolicyId(), cashCustomerQuo.getId().getCshValidityStartDate() );
			if( logger.isDebug() ) logger.debug( "size of list obtained -->" + cashCustomerQuoDetsList.size() );
			for( TMasCashCustomerQuo cashCustomerQuoDets : cashCustomerQuoDetsList ){
				if( logger.isDebug() ) logger.debug( "inside for loop -->" + cashCustomerQuoDets.getId().getCshPolicyId() );
				if( logger.isDebug() ) logger.debug( "inside for loop -->" + cashCustomerQuoDets.getId().getCshValidityStartDate() );
				selectedCashCustomerQuo = cashCustomerQuoDets;
			}

		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QUO" );
		}
		//selectedCashCustomerQuo.setCshBusType( cashCustomerQuo.getCshBusType() );
		return selectedCashCustomerQuo;
	}

	public void updateCashCustomerDetails( TMasCashCustomerQuo cashCustomerQuo ){
		if( logger.isDebug() ) logger.debug( "after changes in update function with remaining parameters to be updated ---->>" );
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getId().getCshPolicyId() + "   Policy Id " );
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getId().getCshValidityStartDate() + "   validity Start Date" );
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getCshBusType() + " CSH Business Type entered" );
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getCshLossRatio() + " Loss ratio obtained" );
		if( logger.isDebug() ) logger.debug( cashCustomerQuo.getCshLossAmt() + " Loss Amt obtained" );

		Session session = null;
		HibernateTemplate template = null;
		try{
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query hibernateQuery = null;
			hibernateQuery = session
					.createQuery( "update TMasCashCustomerQuo cashCustomerQuo set cashCustomerQuo.cshBusType=:busType,cashCustomerQuo.cshLossRatio=:lossRatio,cashCustomerQuo.cshLossAmt=:lossAmt, "
							+"cashCustomerQuo.cshEEmailId=:emailId,cashCustomerQuo.cshTerritory=:territory,cashCustomerQuo.cshLaws=:laws,cashCustomerQuo.cshRegulatoryBody=:regBody "
							+ "where cashCustomerQuo.id.cshPolicyId=:polId and cashCustomerQuo.id.cshValidityStartDate = :valStartDate" );
			hibernateQuery.setParameter( "emailId", cashCustomerQuo.getCshEEmailId() );
			hibernateQuery.setParameter( "territory", cashCustomerQuo.getCshTerritory());
			hibernateQuery.setParameter( "laws", cashCustomerQuo.getCshLaws() );
			hibernateQuery.setParameter( "regBody", cashCustomerQuo.getCshRegulatoryBody() );
			hibernateQuery.setParameter( "busType", cashCustomerQuo.getCshBusType() );
			hibernateQuery.setParameter( "lossRatio", cashCustomerQuo.getCshLossRatio() );
			hibernateQuery.setParameter( "lossAmt", cashCustomerQuo.getCshLossAmt() );
			hibernateQuery.setParameter( com.Constant.CONST_POLID, cashCustomerQuo.getId().getCshPolicyId() );
			hibernateQuery.setDate( "valStartDate", cashCustomerQuo.getId().getCshValidityStartDate() );
			int i = hibernateQuery.executeUpdate();
			logger.debug( "Result Output of Query" +i );
		}
		catch( HibernateException hibernateException ){
			//if( logger.isDebug() ) logger.debug("Exception encountered during updating cash customer quo table -->"+hibernateException);
			if( logger.isDebug() ) logger.debug( com.Constant.CONST_MESSAGE + hibernateException.getMessage() );
			throw new BusinessException( "pas.gi.couldNotUpdateCustDetails", hibernateException, "Error while trying to UPDATE customer details to T_MAS_CASH_CUSTOMER_QUO" );
		}
	}
	
	
	/*
	 * Update Policy Linking Id On save operation completion i.e. After FGBPM service call
	 * 
	 */
	public void updatePolRecordForQuote(PolicyVO policyVO){
		Long policyId;
		
		if(Utils.isEmpty(policyVO)){
			throw new BusinessException( "cmn.systemError", null, "PolicyVO passed to updatePolicyLinkingId is null" );
		}
		
		// TODO : change logic of obtaining policyId from policyNo field
		// to policyId from SectionVO
		policyId = policyVO.getPolicyNo();
		
		if(Utils.isEmpty(policyId)){
			throw new BusinessException( "cmn.systemError", null, "PolicyId obtained is null" );
		}
		
		
		Long policyLinkingId = NextSequenceValue.getNextSequence(POLICY_LINKING_ID_SEQ, null,null, getHibernateTemplate());
		
		Long endtId = Long.valueOf((String)Utils.getSingleValueAppConfig(DEFAULT_ENDT_ID));
		
		if(Utils.isEmpty(policyLinkingId)){
			throw new BusinessException( "cmn.systemError", null, "PolicyLinkingId obtained is null" );
		}
		
		Session session = null;
		try{
			session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query hibernateQuery = null;
			/*
			 * modified code to set issue location and processing location of quote. 
			 */
			if(!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc())) && 
					!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc()))){
			hibernateQuery = session
					.createQuery( "update TTrnPolicyQuo policyQuo set policyQuo.polLinkingId=:polLinkingId, policyQuo.polLocationCode=:polLocCode " +
							",  policyQuo.polProcLocCode=:polProcLocCode "
							+ "where policyQuo.id.polPolicyId=:polId and policyQuo.id.polEndtId = :polEndtId" );
			
			} else {
				hibernateQuery = session
				.createQuery( "update TTrnPolicyQuo policyQuo set policyQuo.polLinkingId=:polLinkingId  "
						+ "where policyQuo.id.polPolicyId=:polId and policyQuo.id.polEndtId = :polEndtId" );
		
			}
			hibernateQuery.setParameter( "polLinkingId", policyLinkingId);
			hibernateQuery.setParameter( com.Constant.CONST_POLID, policyId);
			if(!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc()))){
				hibernateQuery.setParameter( com.Constant.CONST_POLLOCCODE, policyVO.getGeneralInfo().getAdditionalInfo().getIssueLoc().shortValue());
			} else {
				hibernateQuery.setParameter( com.Constant.CONST_POLLOCCODE,null);
			}
			if(!Utils.isEmpty(policyVO.getGeneralInfo()) && (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo()))
					&& (!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc()))){
				hibernateQuery.setParameter( com.Constant.CONST_POLPROCLOCCODE, policyVO.getGeneralInfo().getAdditionalInfo().getProcessingLoc());
			} else {
				hibernateQuery.setParameter( com.Constant.CONST_POLPROCLOCCODE,null);
			}
			
			
			
			/* If endtId is null then default populate it to 0 during quote process */
			if(Utils.isEmpty(endtId)) endtId = Long.valueOf(0);
			hibernateQuery.setParameter( "polEndtId",endtId);
			
			int i = hibernateQuery.executeUpdate();
			logger.debug( "Result Output of Query" +i );
			/* On successful update set back policy linking id to policy VO 
			 * 
			 */
			policyVO.setPolLinkingId(policyLinkingId);

		}
		catch( HibernateException hibernateException ){
			//if( logger.isDebug() ) logger.debug("Exception encountered during updating cash customer quo table -->"+hibernateException);
			if( logger.isDebug() ) logger.debug( com.Constant.CONST_MESSAGE + hibernateException.getMessage() );
			throw new BusinessException( "pas.gi.couldNotUpdatePolicyLinkingId", hibernateException, "Error while trying to UPDATE policyLinkingId  to T_TRN_POLICY_QUO" );
		}
		
		
	}
	
	
}
