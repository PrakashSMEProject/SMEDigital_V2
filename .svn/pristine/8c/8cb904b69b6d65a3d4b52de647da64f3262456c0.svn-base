package com.rsaame.pas.dao.cmn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPolicySectionsQuo;
import com.rsaame.pas.dao.model.TTrnPolicySectionsQuoId;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuo;
import com.rsaame.pas.dao.model.TTrnSectionLocationQuo;
import com.rsaame.pas.dao.model.VTrnPasPremSumLoc;
import com.rsaame.pas.dao.model.VTrnPasPremSumLocId;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.DelLocationInputVO;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.PolicyDetailsHolder;
import com.rsaame.pas.vo.app.PolicyDetailsVO;
import com.rsaame.pas.vo.app.PremiumSummary;
import com.rsaame.pas.vo.app.PremiumSummarySectionVO;
import com.rsaame.pas.vo.app.PremiumSummaryVO;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.cmn.CommonVO;


public class SectionDAO extends BaseDBDAO implements ISectionDAO{

	
	private static final char SUMINSURED = 'S';
	private static final char PREMIUM = 'P';
	private static final Logger LOGGER = Logger.getLogger(SectionDAO.class);
	
	@Override
	public BaseVO deleteLocation( BaseVO input ){

		if( !Utils.isEmpty( input ) ){
			if( input instanceof DelLocationInputVO ){
				DelLocationInputVO delLocationInputVO = (DelLocationInputVO) input;

				clearThreadLevelContext();
				/* Let us get the system date right now and use from here on for this transaction. This date is not sysdate directly rather
				 * obtained by executing function get_revised_pol_issuedate to retrieve right issue_date/VSD for the transaction. */
				ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE,DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(), delLocationInputVO.getPolicy().getAppFlow() ) );

				DAOUtils.fetchEndtId( delLocationInputVO.getPolicy() , getHibernateTemplate() );
				PolicyVO policyVO;
				try{
					getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
					policyVO = delLocationInputVO.getPolicy();
					List<SectionVO> sectionData = policyVO.getRiskDetails();
					Long currentEndtId = policyVO.getNewEndtId();
					Integer isNewForProc = 1;/*its not being used within proc but kept for future usage */
					if( !Utils.isEmpty( sectionData ) ){

						if( delLocationInputVO.isCascade() ){

							for( SectionVO sectionVO : sectionData ){

								setPolicyId( sectionVO, policyVO );
						
								// Added to fix the issue for bug id - 522 - Bugzilla 
								DAOUtils.deletePrevEndtText( sectionVO.getPolicyId(), currentEndtId,sectionVO.getSectionId(), Long.valueOf(delLocationInputVO.getBuildingId()) );
						
								delLocationProcCall( policyVO, sectionVO, delLocationInputVO, currentEndtId, isNewForProc );
								/*if(policyVO.getAppFlow()==Flow.AMEND_POL){
									ins_rows_csh_pol_endt procedure will just create a copy of existing record, hence we need to update
									 * remaining user entered fields such as endt effective date using below method 
									DAOUtils.updatePolEndtDate( sectionVO, policyVO, getHibernateTemplate() );
								}*/
								
										
								DAOUtils.updatePolEndtDate( sectionVO, policyVO, getHibernateTemplate() );

								DAOUtils.updateVED( delLocationInputVO.getPolicy(), sectionVO.getClassCode(), sectionVO.getSectionId() );
								/*
								 * Updates the cash customer table with the auth details , other customer info can be updated here
								 */
								//DAOUtils.updateCustomerDetails(policyVO, sectionVO, getHibernateTemplate());
								DAOUtils.updateCustomerAuthDetails( policyVO, sectionVO, getHibernateTemplate() );
							}
						}
						else{

							setPolicyId( PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ), policyVO );
							
							// Added to fix the issue for bug id - 522 - Bugzilla 
							DAOUtils.deletePrevEndtText( PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ).getPolicyId(), currentEndtId,delLocationInputVO.getSectionId(), Long.valueOf(delLocationInputVO.getBuildingId()) );
							
							delLocationProcCall( policyVO, PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ), delLocationInputVO, currentEndtId,
									isNewForProc );
							/*if(policyVO.getAppFlow()==Flow.AMEND_POL){
								ins_rows_csh_pol_endt procedure will just create a copy of existing record, hence we need to update
								 * remaining user entered fields such as endt effective date using below method 
								DAOUtils.updatePolEndtDate( PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ), policyVO, getHibernateTemplate() );
							}*/
							
							
							DAOUtils.updatePolEndtDate( PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ), policyVO, getHibernateTemplate() );

							if( !Utils.isEmpty( PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ) ) ){
								DAOUtils.updateVED( delLocationInputVO.getPolicy(), PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() )
										.getClassCode(), PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ).getSectionId() );
							}

							/*
							 * Updates the cash customer table with the auth details , other customer info can be updated here
							 */
							//DAOUtils.updateCustomerDetails(policyVO, PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ), getHibernateTemplate());
							DAOUtils.updateCustomerAuthDetails( policyVO, PolicyUtils.getSectionVO( policyVO, delLocationInputVO.getSectionId().intValue() ),
									getHibernateTemplate() );
						}

						/* Updating status in policyVO to 6 ( Pending ) to make endorsement pending */
						/* Adding status check so that we do not update status when quote/policy is referred. */
						if( !( policyVO.getStatus().equals( SvcConstants.POL_STATUS_REFERRED ) ) ){
							policyVO.setStatus( SvcConstants.POL_STATUS_PENDING );
						}
					}

				}
				catch( DataAccessException e ){
					throw new BusinessException( "cmn.unknownError", e, "An exception occured while executing stored proc." );
				}
				clearThreadLevelContext();
				return null;
			}
		}
		return input;
	}
	

	@Override
	public BaseVO getSectionListForPolicy( BaseVO input ){

		if( !Utils.isEmpty( input ) ){
			if( input instanceof LoadExistingInputVO ){
				LoadExistingInputVO lei = (LoadExistingInputVO) input;
				List<Integer> sections = new com.mindtree.ruc.cmn.utils.List<Integer>( Integer.class );

				if( lei.isQuote() ){
					List<TTrnPolicySectionsQuo> sectionList = getHibernateTemplate().find(
							"from TTrnPolicySectionsQuo section where section.id.tpsLinkingId=? ", lei.getPolLinkingId() );
					for( TTrnPolicySectionsQuo policySectionsQuo : sectionList ){
						if( !Utils.isEmpty( policySectionsQuo ) ){
							if( !Utils.isEmpty( policySectionsQuo.getId().getTpsSecId() ) ){
								Short sectionString = policySectionsQuo.getId().getTpsSecId();
								sections.add( Integer.valueOf( sectionString.toString() ) );
							}
						}
					}
				}
				else{
					List<TTrnSectionDetailsQuo> sectionDetailsQuoList = null;
					/* Fetches records from TTrnSectionDetails(_Quo) table with VED as '31-DEC-2049' and status <> 4 (deleted location or
					 * section will have status as SvcConstants.DEL_SEC_LOC_STATUS value)*/
					if( !Utils.isEmpty( lei.getPolicyStatus() ) && lei.getPolicyStatus().byteValue() != SvcConstants.DEL_SEC_LOC_STATUS ){
						//				sectionDetailsQuoList = (List<TTrnSectionDetailsQuo>) getHibernateTemplate()
						//				.find( "from TTrnSectionDetailsQuo section where (section.id.secPolicyId,section.secEndtId,section.secClCode)IN (select max(sec.id.secPolicyId) , max(sec.secEndtId), sec.secClCode from TTrnSectionDetailsQuo sec where sec.secEndtId<=? and sec.id.secPolicyId in(select p.id.polPolicyId from TTrnPolicyQuo p where p.polLinkingId = ?) group by sec.secClCode) and section.secStatus <> ? ",
						//						lei.getEndtId(),lei.getPolLinkingId(), SvcConstants.DEL_SEC_LOC_STATUS );
						sectionDetailsQuoList = getHibernateTemplate()
								.find( "from TTrnSectionDetailsQuo section where (section.id.secPolicyId,section.secEndtId,section.id.secSecId)IN (select max(sec.id.secPolicyId) , max(sec.secEndtId), sec.id.secSecId from TTrnSectionDetailsQuo sec where sec.secEndtId<=? and sec.id.secPolicyId in(select p.id.polPolicyId from TTrnPolicyQuo p where p.polLinkingId = ?) group by sec.id.secSecId) and section.secStatus <> ? ",
										lei.getEndtId(), lei.getPolLinkingId(), SvcConstants.DEL_SEC_LOC_STATUS );
					}
					else{
						/*
						 * In case the policy is canceled,to view that policy status 4 check is not required. 
						 * TODO: Combine the queries
						 */
						sectionDetailsQuoList = getHibernateTemplate()
								.find( "from TTrnSectionDetailsQuo section where section.id.secPolicyId in (select p.id.polPolicyId from TTrnPolicyQuo p where p.polLinkingId = ?) and section.secValidityExpiryDate = ? ",
										lei.getPolLinkingId(), SvcConstants.EXP_DATE );
					}

					for( TTrnSectionDetailsQuo detailsQuo : sectionDetailsQuoList ){
						sections.add( (int) detailsQuo.getId().getSecSecId() );
					}
				}
				SectionList secList = new SectionList();
				secList.setSelectedSec( sections );
				return secList;
			}
		}
		return input;
	}
	
	
	/** This method is to fetch the policy details for print policy docs */
	@Override
	public BaseVO fetchPolicyInfoFromPolicyNo(BaseVO basevo){
		
		PolicyDetailsVO policyVO = (PolicyDetailsVO)basevo;
		Long policyNo = policyVO.getPolicyNo();
		String endtId   = policyVO.getEndtId();
		String concPolNo = policyVO.getPolConcPolicyNo();
		String policySql = " SELECT POL_POLICY_ID, POL_LINKING_ID, max(POL_ENDT_ID) as POL_ENDT_ID, POL_CLASS_CODE, SEC_SEC_ID, max(POL_DOCUMENT_CODE), TO_CHAR(POL_VALIDITY_START_DATE,'dd-MON-yyyy')," 
							  +" POL_BR_CODE,POL_AGENT_ID "
							  +" FROM T_TRN_POLICY,T_TRN_SECTION_DETAILS "
							  +" WHERE POL_STATUS       in (1,4) "
							  +" AND POL_ENDT_ID <=  " +endtId
							  +" AND POL_POLICY_NO = '" +policyNo+"'"
							  +" AND POL_CONC_POLICY_NO = '"+concPolNo+"'"
							  +" AND POL_POLICY_ID      = SEC_POLICY_ID "
							  +" AND POL_ENDT_ID      >= SEC_ENDT_ID "
							  +" group by POL_POLICY_ID,POL_CLASS_CODE,POL_LINKING_ID,SEC_SEC_ID,POL_VALIDITY_START_DATE,POL_BR_CODE,POL_AGENT_ID order by POL_ENDT_ID"; //Fix for 131278-added order by POL_ENDT_ID 
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(policySql);
		List<Object>result = query.list();
		com.mindtree.ruc.cmn.utils.List<PolicyDetailsVO> policyResult =  new com.mindtree.ruc.cmn.utils.List<PolicyDetailsVO>(PolicyDetailsVO.class);
		Iterator <Object>itr =null;
		itr = result.iterator();
		Object[] row =null;
		PolicyDetailsHolder policyDtls =  new PolicyDetailsHolder();
		while(itr.hasNext()){
			row = (Object[])itr.next();
			PolicyDetailsVO polDtlsVO = new PolicyDetailsVO();
			polDtlsVO.setPolicyId(String.valueOf(row[0]));
			polDtlsVO.setPolicyLinkingId(String.valueOf(row[1]));
			polDtlsVO.setEndtId(String.valueOf(row[2]));
			polDtlsVO.setPolicyClassCode(String.valueOf(row[3]));
			polDtlsVO.setSectionId(String.valueOf(row[4]));
			polDtlsVO.setPolDocumentId(String.valueOf(row[5]));
			polDtlsVO.setStartDate(String.valueOf(row[6]));
			polDtlsVO.setPolBrCode( String.valueOf( row[7] ) );
			polDtlsVO.setPolAgentId( String.valueOf( row[8] ) );
			policyResult.add(polDtlsVO);
		}
		
		policyDtls.setPolicyDtlList(policyResult);
		return policyDtls;
	}
	
	
	/**
	 *  This method is to fetch the policy details for print policy docs.
	 *  Written separately for HOME and TRAVEl (B2B) because in B2B we dont use T_TRN_SECTION_DETAILS table.
	 */
	@Override
	public BaseVO fetchPolicyInfoFromPolicyNoHomeTravel(BaseVO basevo){
		
		String policySql = null;
		Long policyNo = null;
		String endtId   = null;
		
		PolicyDetailsVO policyVO = (PolicyDetailsVO)basevo;
		policyNo = policyVO.getPolicyNo();
		endtId   = policyVO.getEndtId();
		String concPolNo = policyVO.getPolConcPolicyNo();

		policySql = " SELECT POL_POLICY_ID, POL_LINKING_ID, max(POL_ENDT_ID), POL_CLASS_CODE, max(POL_DOCUMENT_CODE), TO_CHAR(POL_VALIDITY_START_DATE,'dd-MON-yyyy')," 
					  +" POL_BR_CODE,POL_AGENT_ID "
					  +" FROM T_TRN_POLICY"
					  +" WHERE POL_STATUS in (1,4) "
					  +" AND POL_POLICY_NO = '" +policyNo+"'"
					  +" AND POL_ENDT_ID =  " +endtId
					  +" AND POL_CONC_POLICY_NO = '"+concPolNo+"'"
					  +" group by POL_POLICY_ID,POL_CLASS_CODE,POL_LINKING_ID,POL_VALIDITY_START_DATE,POL_BR_CODE,POL_AGENT_ID";

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(policySql);
		List<Object>result = query.list();
		com.mindtree.ruc.cmn.utils.List<PolicyDetailsVO> policyResult =  new com.mindtree.ruc.cmn.utils.List<PolicyDetailsVO>(PolicyDetailsVO.class);
		Iterator <Object>itr =null;
		itr = result.iterator();
		Object[] row =null;
		PolicyDetailsHolder policyDtls =  new PolicyDetailsHolder();
		while(itr.hasNext()){
			row = (Object[])itr.next();
			PolicyDetailsVO polDtlsVO = new PolicyDetailsVO();
			polDtlsVO.setPolicyId(String.valueOf(row[0]));
			polDtlsVO.setPolicyLinkingId(String.valueOf(row[1]));
			polDtlsVO.setEndtId(String.valueOf(row[2]));
			polDtlsVO.setPolicyClassCode(String.valueOf(row[3]));
			polDtlsVO.setPolDocumentId(String.valueOf(row[4]));
			polDtlsVO.setStartDate(String.valueOf(row[5]));
			polDtlsVO.setPolBrCode( String.valueOf( row[6] ) );
			polDtlsVO.setPolAgentId( String.valueOf( row[7] ) );
			policyResult.add(polDtlsVO);
		}
		
		policyDtls.setPolicyDtlList(policyResult);
		return policyDtls;
	}
	
	/** This method is to fetch the Quotation details for proposalform */
	@Override
	public BaseVO fetchQuotationInfoFromQuoteNo( BaseVO basevo ){

		if( !Utils.isEmpty( basevo ) ){
			
			Long quoteNo = null;
			Long policyId= null;
			LOB lob = null;
			if( basevo instanceof PolicyVO ){
				PolicyVO policyVO = (PolicyVO) basevo;
				quoteNo = policyVO.getQuoteNo();
			}else if( basevo instanceof CommonVO ){
				CommonVO  commonVO = (CommonVO) basevo;
				quoteNo = commonVO.getQuoteNo();
				if(!Utils.isEmpty( commonVO) && !Utils.isEmpty( commonVO.getLob() ))
				lob = commonVO.getLob();
			}
			
			if(!Utils.isEmpty( quoteNo )){
					
				//Long endtId = policyVO.getEndtId();
				String quoteSql = "SELECT Q.POL_LINKING_ID, Q.POL_ENDT_ID,Q.POL_VALIDITY_START_DATE ,Q.POL_POLICY_ID"
						+ " FROM T_TRN_POLICY_QUO Q WHERE  Q.POL_ENDT_ID =  (SELECT MAX(POL_ENDT_ID) FROM T_TRN_POLICY_QUO WHERE POL_QUOTATION_NO= " + quoteNo
						+ " AND trunc(POL_validity_expiry_date) = '31-DEC-2049' AND POl_issue_hour = 3 and POL_STATUS in (1,7,10))" + " and q.POl_issue_hour = 3 AND Q.POL_QUOTATION_NO = " + quoteNo;
				//Added to fix the case where WC Quote no generated matches with old Travel Quotes and thus to prevent wrong data population added policyId check.
				if(!Utils.isEmpty(lob) && lob.toString().equalsIgnoreCase("WC")){
					quoteSql+= "and Q.pol_policy_type ="+Utils.getSingleValueAppConfig("POLICY_TYPE_WC");
				}
				Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery( quoteSql );
				List<Object> result = query.list();
				
				Iterator<Object> itr = null;
				itr = result.iterator();
				Object[] row = null;
				Object quoteDetails[] = new Object[ 4 ];
				DataHolderVO<Object[]> data = new DataHolderVO<Object[]>();
				while( itr.hasNext() ){
					row = (Object[]) itr.next();
					quoteDetails[ 0 ] = row[ 0 ];
					quoteDetails[ 1 ] = row[ 1 ];
					quoteDetails[ 2 ] = row[ 2 ];
					quoteDetails[ 3 ] = row[ 3 ];
					
				}
				data.setData( quoteDetails );
				return data;
			}
			
		}
		return basevo;
	}
	
	
	/**
	 * This function is used to save selected  risk selections list
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO saveSelectedRiskSections( BaseVO baseVO ){

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof SectionList ){

				SectionList sectionList = (SectionList) baseVO;
				List<Integer> sectionIds = null;
				if( !Utils.isEmpty( sectionList ) ) sectionIds = sectionList.getSelectedSec();

				/*Preparing to save sectionIDs for a policy */
				if( !Utils.isEmpty( sectionIds ) ){
					for( Integer sectionId : sectionIds ){
						if( !Utils.isEmpty( sectionId ) ){
							TTrnPolicySectionsQuoId trnPolicySectionsQuoId = new TTrnPolicySectionsQuoId();
							trnPolicySectionsQuoId.setTpsSecId( sectionId.shortValue() );
							trnPolicySectionsQuoId.setTpsLinkingId( sectionList.getPolicyLinkingId() );
							trnPolicySectionsQuoId.setTpsEndtNo( sectionList.getEndorsementNo() );
							TTrnPolicySectionsQuo policySectionsQuo = new TTrnPolicySectionsQuo();
							policySectionsQuo.setId( trnPolicySectionsQuoId );
							/*Saving sectionIds for a policy*/
							this.getHibernateTemplate().merge( policySectionsQuo );
							this.getHibernateTemplate().flush();
							this.getHibernateTemplate().clear();
						}

					}
				}
				//return null;   //SONARFIX -- 24-apr-2018 -- anyway function returns null in both the paths hence removed this one.
			}
		}
		return null;
	}
	
	
	/**
	 * This function is used to delete previously selected  risk section when section is unchecked from Left navigation
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO deleteSelectedRiskSection( BaseVO baseVO ){
		
		clearThreadLevelContext();
	
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();

		PolicyVO policyVO = (PolicyVO) data[ 0 ];
		
		List<SectionVO> sectionDataHolder=policyVO.getRiskDetails();
		
		
		
		
		/* Let us get the system date right now and use from here on for this transaction. This date is not sysdate directly rather
		 * obtained by executing function get_revised_pol_issuedate to retrieve right issue_date/VSD for the transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, DAOUtils.getValidityStartDate( getHibernateTemplate(), getSysDate(),policyVO.getAppFlow() ) );
		Integer sectionId = (Integer) data[ 1 ];
		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, sectionId );
		
		/* If sectionVO is returned as null from PolicyUtils, set the sectionId so that Null value is not passed to Procedure in the method setPolicyId(),
		 * which eventually used to throw NullPointerException.*/
		if( Utils.isEmpty( sectionVO ) ){
			sectionVO = new SectionVO( RiskGroupingLevel.LOCATION );
			sectionVO.setSectionId( sectionId );
		}
		
		/* Policy Id within sectionVO obtained using policyVO could be empty in cases where in user without navigating to 
		 * particular section de-selects it from left menu */
		
		/*
		 * TODO: The function returns 0 if section is not available. Need to correct it since risk de-selection of non saved section will throw unexpected error
		 */
		setPolicyId(sectionVO,policyVO);
		
		/* Update endtId, endtNo and VSD to policyVO using DAOUtils.fetchEndtId method */
		DAOUtils.fetchEndtId( policyVO ,getHibernateTemplate() );
		
		PASStoredProcedure sp = null;// (PASStoredProcedure) ( policyVO.getIsQuote() ? Utils.getBean( "delSectionStProc" ) : Utils.getBean( "delSectionStProc_POL" ) );
		try{

			getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
			double premium = 0.0;
			if( !Utils.isEmpty( policyVO.getPremiumVO() ) ){
				premium = policyVO.getPremiumVO().getPremiumAmt();
			}
			/*
			 * For endt the signature is changed to take in endt start date. 
			 * TODO: same change is required for quote too, but end eff date should be 
			 * null in case of quote
			 */
			if( policyVO.getIsQuote() ){
				sp = (PASStoredProcedure) Utils.getBean( "delSectionStProc" );
				//TODO : the below fix is temp for 25 aug release
				if( !sectionVO.getPolicyId().equals( Long.valueOf( SvcConstants.zeroVal ) ) ){
					Map results = sp.call( sectionVO.getPolicyId(), sectionId, premium, policyVO.getCreatedBy(), policyVO.getNewValidityStartDate(), policyVO.getNewEndtId() );
					if (Utils.isEmpty(results)) {
						LOGGER.debug("The result of the stored procedure is nul_1");
					}
				}
				//AMS Defect no:144 - if a section is selected and deselected in the same SESSION, only entry in t_trn_policy_sections_quo is deleted and return
				else{
					/*
					 *  Added for Ticket Id :83464 Policy Number blank issue
					 *  if a section is selected and deselected in the same SESSION, only entry in t_trn_policy_sections_quo is deleted and return
					 */
					int count=0;
					
					for(SectionVO sectionDetails: sectionDataHolder){
					if(!Utils.isEmpty(sectionDetails.getClassCode()) && !Utils.isEmpty(sectionVO.getClassCode()) && sectionDetails.getClassCode().equals(sectionVO.getClassCode())){
						count++; 
					}
					}
					if(count==1){
					for(SectionVO sectionDetails: sectionDataHolder){
					if((!Utils.isEmpty(sectionDetails.getSectionId()) && !Utils.isEmpty(sectionVO.getSectionId()) && sectionDetails.getSectionId().equals(sectionVO.getSectionId()))){
						DAOUtils.deletePolicyRiskEntry( policyVO.getPolLinkingId(), sectionVO, getHibernateTemplate() );
						}
					}
					}else{
						DAOUtils.deleteOnlyRiskEntry( policyVO.getPolLinkingId(), sectionVO, getHibernateTemplate() );
					}
					clearThreadLevelContext();					
					return null;
				}
				
			}
			else{
				sp = (PASStoredProcedure) Utils.getBean( "delSectionStProc_POL" );
				//AMS Defect no:144 - if a section is selected and deselected in the same SESSION,then no DB entries are done.
				if(sectionVO.getPolicyId().equals( Long.valueOf( SvcConstants.zeroVal ) )){
					clearThreadLevelContext();					
					return null;
				}
				else{
				Map results = sp.call( sectionVO.getPolicyId(), sectionId, premium, policyVO.getCreatedBy(), policyVO.getNewValidityStartDate(), policyVO.getNewEndtId(),
						policyVO.getEndEffectiveDate() );
				//in the same endt, if a section is selected and deselected without saving the section
				if (Utils.isEmpty(results)) {
					LOGGER.debug("The result of the stored procedure is nul_2");
				}
				}
			}
			
			/* Updating status in policyVO to 6 ( Pending ) to make endorsement pending */
			/* Adding the below if condition so that during resolving referral, status does not change after section/location de-selection.*/
			if (  !Utils.isEmpty( policyVO.getStatus() ) && !policyVO.getStatus().equals( SvcConstants.POL_STATUS_REFERRED ) ){
				policyVO.setStatus(SvcConstants.POL_STATUS_PENDING);
			}
			
		}
		catch( DataAccessException e ){
			throw new BusinessException( "cmn.unknownError", e, "An exception occured while executing stored proc." );
		}
			
		/* Update VED of the original section record within T_TRN_SECTION_DETAILS(_QUO) table (expire the record with VED
		 * as VSD of new record record inserted by delSectionStProc(_POL) procedure call */
		/*
		 * Update VED is now called in proc of risk de selection. Hence update ved is not required
		 */
		//
		
		/*
		 *  Calling the procedure to update the endorsement text for Deletion of Risk.
		 */
		if(policyVO.getAppFlow()==Flow.AMEND_POL){
		DAOUtils.updateEndTextForRiskDel(sectionVO.getPolicyId(), policyVO,sectionVO.getSectionId());
		}
		if(policyVO.getAppFlow()==Flow.AMEND_POL){
			DAOUtils.updatePolEndtDate( sectionVO, policyVO, getHibernateTemplate()  );
		}
		
		//DAOUtils.updatePolEndtDate( sectionVO, policyVO, getHibernateTemplate()  );
		DAOUtils.updateVED( policyVO, sectionVO.getClassCode(), sectionId );
		
		//DAOUtils.updateCustomerDetails(policyVO, sectionVO, getHibernateTemplate());
		DAOUtils.updateCustomerAuthDetails( policyVO, sectionVO, getHibernateTemplate() );
		clearThreadLevelContext();
		
		return null;
	}



	private void setPolicyId( SectionVO sectionVO, PolicyVO policyVO ){
		Long policyId =  DAOUtils.getSectionPolicyId( policyVO, sectionVO.getSectionId(), getHibernateTemplate() );
		sectionVO.setPolicyId( policyId );
	}


    /**
    * This function is used to fetch premium summary details
    * @param input
    * @return
    */
	@Override
	public BaseVO getPremiumSummaryDetails( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){

				PolicyVO policyVO = (PolicyVO) baseVO;
				PremiumSummary premiumSummary = new PremiumSummary();
				if( !Utils.isEmpty( policyVO ) ){

					/*    pasPremSumLoc = getHibernateTemplate().find(
					                  "from  VTrnPasPremSumLoc where id.linkingId=? and " + "( id.endtId = ? or ( id.endtId < ? and trunc(id.valExpDate) = ? ) )  ", policyVO.getPolLinkingId(),
					                  SvcUtils.getLatestEndtId( policyVO ), SvcUtils.getLatestEndtId( policyVO ), SvcConstants.EXP_DATE );*/
					/*
					* Special handling for cancel policy since endt id is generated and after all process the endt is inserted into DB hence getNewEndtId is not present in any polict table
					*/

					Long endtIdToProcess = null;
					if(policyVO.getIsQuote() && policyVO.getStatus().equals( 7 ))
						{
							endtIdToProcess = DAOUtils.getMaxPolicyEndtIdFromLinkingId( getHibernateTemplate(), policyVO.getPolLinkingId() );
						}
					else
						{
							endtIdToProcess = DAOUtils.getEndtToProcess( getHibernateTemplate(), policyVO );
						}

					/*
					* TODO:// Use the view once it is fixed
					*/
					/*pasPremSumLoc = getHibernateTemplate().find(
					            "from VTrnPasPremSumLoc  where id.linkingId=? "
					                        + " and  id.valStartDate <= (select  distinct ( polValidityStartDate ) from  TTrnPolicyQuo p where p.polLinkingId = ? and p.id.polEndtId = ? )"
					                        + " and  id.valExpDate > (select distinct ( polValidityStartDate ) from  TTrnPolicyQuo p where p.polLinkingId = ? and p.id.polEndtId = ? )"
					                        + " and  id.endtId <= ? and id.polQuoFlag = ? ", policyVO.getPolLinkingId(), policyVO.getPolLinkingId(), endtIdToProcess, policyVO.getPolLinkingId(),
					            endtIdToProcess, endtIdToProcess, policyVO.getIsQuote() ? 'Q' : 'P' );*/

					List<TTrnSectionLocationQuo> locations = null;

					/*Date vsd = DAOUtils.getValidityStartDate( getHibernateTemplate(), endtIdToProcess, policyVO.getPolLinkingId(), policyVO.getIsQuote() );*/

					//TODO:// use vsd instead of subquery
					if( !policyVO.getIsQuote() ){
						locations = getHibernateTemplate()
								.find( "from TTrnSectionLocationQuo  where id.tslPolLinkingId=? "
										+ " and  tslValidityStartDate <= ( select distinct(pol.polValidityStartDate) from TTrnPolicyQuo pol where pol.polLinkingId= ? and pol.id.polEndtId = ? ) "
										+ " and  tslValidityExpiryDate > ( select distinct(pol.polValidityStartDate) from TTrnPolicyQuo pol where pol.polLinkingId = ? and pol.id.polEndtId = ? ) "
										+ " and  id.tslPolEndtNo <= ?", policyVO.getPolLinkingId(), policyVO.getPolLinkingId(), endtIdToProcess, policyVO.getPolLinkingId(),
										endtIdToProcess, endtIdToProcess );
						/*
						* In amend flow get all the location irrespective of the status
						*/
						/*locations = getHibernateTemplate().find(
						            "from TTrnSectionLocationQuo  where id.tslPolLinkingId=?  and  id.tslPolEndtNo <= ?", policyVO.getPolLinkingId(),  endtIdToProcess );*/
					}
					else{
						locations = getHibernateTemplate()
								.find( "from TTrnSectionLocationQuo  where id.tslPolLinkingId=? "
										+ " and  tslValidityStartDate <= ( select distinct(pol.polValidityStartDate) from TTrnPolicyQuo pol where pol.polLinkingId = ? and pol.id.polEndtId = ? ) "
										+ " and  tslValidityExpiryDate > ( select distinct(pol.polValidityStartDate) from TTrnPolicyQuo pol where pol.polLinkingId = ? and pol.id.polEndtId = ? ) "
										+ " and  id.tslPolEndtNo <= ? " + " and  tslActiveFlag <> ? ", policyVO.getPolLinkingId(), policyVO.getPolLinkingId(), endtIdToProcess,
										policyVO.getPolLinkingId(), endtIdToProcess, endtIdToProcess, SvcConstants.STATUS_INACTIVE );
					}

					//For each location in TTrnSectionLocation loop and create VTrnPasPremSumLoc instance
					List<VTrnPasPremSumLoc> pasPremSumLoc = new ArrayList<VTrnPasPremSumLoc>();
					for( TTrnSectionLocationQuo location : locations ){
						VTrnPasPremSumLoc pasPremSumPerLoc = new VTrnPasPremSumLoc();
						VTrnPasPremSumLocId pasPremSumPerLocId = new VTrnPasPremSumLocId();
						pasPremSumPerLoc.setId( pasPremSumPerLocId );
						Long policyId = DAOUtils.getSectionPolicyId( policyVO.getPolLinkingId(), location.getId().getTslSecId(), policyVO.getIsQuote(), getHibernateTemplate() );
						if( !Utils.isEmpty( policyId ) && !policyId.equals( Long.valueOf( SvcConstants.zeroVal ) ) ){
							Double commission = DAOUtils.getCommission( policyId, location.getId().getTslPolEndtNo(), Integer.valueOf( location.getId().getTslSecId() ),
									policyVO.getAppFlow(), getHibernateTemplate() );
							pasPremSumPerLoc.getId().setCommission( Utils.isEmpty( commission ) ? BigDecimal.valueOf( SvcConstants.zeroVal ) : BigDecimal.valueOf( commission ) );
							pasPremSumPerLoc.getId().setLinkingId( policyVO.getPolLinkingId() );
							pasPremSumPerLoc.getId().setLocationId( location.getId().getTslLocId() );
							pasPremSumPerLoc.getId().setEndtId( location.getId().getTslPolEndtNo() );
							pasPremSumPerLoc.getId().setSecId( location.getId().getTslSecId() );
							pasPremSumPerLoc.getId().setPrmBasicRskId( BigDecimal.valueOf( location.getTslBasicRiskId() ) );
						
							//Start Modified by Mindtree on 01/10/2015 for Bugzilla:5156 � Error in Premium summary when Travel baggage present and amendment done on policy.
							
							//							pasPremSumPerLoc.getId().setPrmBasicRskCode(
							//									DAOUtils.getBasicRskCode( policyId, endtIdToProcess, BigDecimal.valueOf( location.getTslBasicRiskId() ), policyVO.getIsQuote(),
							//											getHibernateTemplate() ) );

							pasPremSumPerLoc.getId().setPrmBasicRskCode(DAOUtils.getRisckCodeFromSectionId( (int) location.getId().getTslSecId() ));

							//End Modified by Mindtree on 01/10/2015 for Bugzilla:5156 � Error in Premium summary when Travel baggage present and amendment done on policy.
							
							pasPremSumPerLoc.getId().setPolQuoFlag( SvcUtils.getQuoteOrPloicyFlag( policyVO.getIsQuote() ) );
							//fetch building name 
							String bldName = DAOUtils.getBuildingName( policyVO.getPolLinkingId(), endtIdToProcess, location.getId().getTslLocId(), policyVO.getIsQuote(),
									getHibernateTemplate() );
							pasPremSumPerLoc.getId().setLocationName( bldName );

							BigDecimal status = location.getTslActiveFlag().equalsIgnoreCase( SvcConstants.STATUS_INACTIVE ) ? BigDecimal.valueOf( SvcConstants.POL_STATUS_DELETED )
									: BigDecimal.valueOf( SvcConstants.POL_STATUS_ACTIVE );
							pasPremSumPerLoc.getId().setStatus( status );
							pasPremSumLoc.add( pasPremSumPerLoc );
						}

					}
					/*
					 * 
					 * VAT Percent Added in Below Method
					 * 
					 */
					/*VAT Tax*/
					premiumSummary.setVatPerc(DAOUtils.getVATRateSBS(policyVO.getPolVATCode(),policyVO.getStartDate()));
				
					mapPremium( pasPremSumLoc, premiumSummary, policyVO, endtIdToProcess );

				
					
					//PremiumHelper.updateSpecialPremium(policyVO,getHibernateTemplate());
					premiumSummary.setDiscLoad( policyVO.getPremiumVO().getDiscOrLoadPerc() );
					premiumSummary.setDiscLoadAmt( policyVO.getPremiumVO().getDiscOrLoadAmt() );
					premiumSummary.setNonTaxcomm( DAOUtils.getNonTaxPrm( policyVO.getPolLinkingId(), endtIdToProcess, policyVO.getIsQuote() ) );
					
					
					
					
					
					
					
					String policyFee = null;
					if( SvcConstants.POL_STATUS_PENDING.equals( policyVO.getStatus() )
							&& ( Utils.isEmpty( policyVO.getNewEndtId() ) || SvcConstants.ZERO.equals( policyVO.getNewEndtId() ) ) ){
						policyFee = SvcUtils.getLookUpDescription( RulesConstants.POLICY_FEE, RulesConstants.LOOKUP_LEVEL1, RulesConstants.LOOKUP_LEVEL2, policyVO.getScheme()
								.getSchemeCode() );
						if( !Utils.isEmpty( policyFee ) ){
							premiumSummary.setPolicyFee( Double.valueOf( policyFee ) );
						}
					}
					else if( !Utils.isEmpty( policyVO.getPremiumVO() ) ){
						premiumSummary.setPolicyFee( policyVO.getPremiumVO().getPolicyFees() );
					}
					
					
				
				}
				return premiumSummary;
			}
		}
		return baseVO;
	}



	private BigDecimal getGovtTax(Integer secId, Long endtIdToProcess, PolicyVO policyVO ){

		StringBuilder queryString = new StringBuilder();
		/*queryString.append( "select avg(code) from ss_v_mas_lookup lkup where lkup.category = 'GOVTTAX' and lkup.level_1 in (select distinct sec.sec_cl_code from " )
				.append( policyVO.getIsQuote() ? "t_trn_section_details_quo" : "t_trn_section_details " ).append( " sec," )
				.append( policyVO.getIsQuote() ? "t_trn_policy_quo" : "t_trn_policy " ).append( " pol " )
				.append( "where sec.sec_policy_id = pol.pol_policy_id  and pol_issue_hour = " ).append( Utils.getSingleValueAppConfig( "E_PLATFORM_APP_CODE" ) )
				.append( " and pol.pol_policy_type=" ).append( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ).append( " and sec.sec_status <> " )
				.append( SvcConstants.DEL_SEC_LOC_STATUS ).append( " and sec.sec_pt_code= " ).append( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ).append( " and " )
				.append( "pol.pol_linking_id = " ).append( policyVO.getPolLinkingId() ).append( ")" );*/
		// Oman Change
		// In case of policy pick the rate from existing rates saved in premium table
		if (!policyVO.getIsQuote()) {

			queryString
					.append("select DISTINCT(pr.prm_rate) from t_trn_premium pr, t_trn_policy pol where pr.prm_policy_id = pol.pol_policy_id and pol.pol_issue_hour = ")
					.append(Utils.getSingleValueAppConfig("SBS_POLICY_ISSUE_HOUR"))
					.append(" and pr.prm_cov_code = ")
					.append(SvcConstants.SC_PRM_COVER_GOVT_TAX)
					.append(" and pr.prm_endt_id  = ")
					.append(SvcConstants.zeroVal)
					.append(" and pol.pol_linking_id = ")
					.append(policyVO.getPolLinkingId())
					.append(" and pr.prm_cl_code = ")
					.append(Utils.getSingleValueAppConfig("SEC_" + secId));

		}else{
			queryString.append( "select distinct code from ss_v_mas_lookup lkup where lkup.category = 'GOVTTAX' and lkup.level_1 = '")
			.append( Utils.getSingleValueAppConfig( "SEC_"+secId ) ).append( "'" );
		}
		
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( queryString.toString() );
		List<Object> result = query.list();
		BigDecimal govtTax = new BigDecimal( 0 );
		if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
			govtTax = (BigDecimal) result.get( 0 );
		}

		return govtTax;
	}
	
	
	private BigDecimal getVatTax(Integer secId,Long endtIdToProcess, PolicyVO policyVO ){

		StringBuilder queryString = new StringBuilder();
		/*queryString.append( "select avg(code) from ss_v_mas_lookup lkup where lkup.category = 'GOVTTAX' and lkup.level_1 in (select distinct sec.sec_cl_code from " )
				.append( policyVO.getIsQuote() ? "t_trn_section_details_quo" : "t_trn_section_details " ).append( " sec," )
				.append( policyVO.getIsQuote() ? "t_trn_policy_quo" : "t_trn_policy " ).append( " pol " )
				.append( "where sec.sec_policy_id = pol.pol_policy_id  and pol_issue_hour = " ).append( Utils.getSingleValueAppConfig( "E_PLATFORM_APP_CODE" ) )
				.append( " and pol.pol_policy_type=" ).append( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ).append( " and sec.sec_status <> " )
				.append( SvcConstants.DEL_SEC_LOC_STATUS ).append( " and sec.sec_pt_code= " ).append( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ).append( " and " )
				.append( "pol.pol_linking_id = " ).append( policyVO.getPolLinkingId() ).append( ")" );*/
		// In case of policy pick the rate from existing rates saved in premium table
		BigDecimal vatTax = new BigDecimal( 0 );
		if (!policyVO.getIsQuote() && secId!=151) {

			queryString
					.append("select DISTINCT(pr.prm_rate) from t_trn_premium pr, t_trn_policy pol where pr.prm_policy_id = pol.pol_policy_id and pol.pol_issue_hour = ")
					.append(Utils.getSingleValueAppConfig("SBS_POLICY_ISSUE_HOUR"))
					.append(" and pr.prm_cov_code = ")
					.append(SvcConstants.SC_PRM_COVER_VAT_TAX)
					.append(" and pr.prm_endt_id  = ")
					.append(SvcConstants.zeroVal)
					.append(" and pol.pol_linking_id = ")
					.append(policyVO.getPolLinkingId())
					.append(" and pr.prm_cl_code = ")
					.append(Utils.getSingleValueAppConfig("SEC_" + secId));
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( queryString.toString() );
			List<Object> result = query.list();
			
			if( !Utils.isEmpty( result ) && !Utils.isEmpty( result.get( 0 ) ) ){
				vatTax = (BigDecimal) result.get( 0 );
			}
		}else{
			/*VAT*/
			//queryString.append( "select distinct code from ss_v_mas_lookup lkup where lkup.category = 'VATTAX' and lkup.level_1 = '50'");
			vatTax = DAOUtils.getVATRateSBS(policyVO.getPolVATCode(), policyVO.getStartDate());
		}
		
		

		return vatTax;
	}


	/*
	 * Map the result from VTrnPasPremSumLoc view to PremiumSummary vo
	 */
	private void mapPremium( List<VTrnPasPremSumLoc> pasPremSumLocList, PremiumSummary premiumSummary, PolicyVO policyVO, Long endtIdToProcess ){

		LoadExistingInputVO lei = new LoadExistingInputVO();
		lei.setQuote( policyVO.getIsQuote() );
		lei.setPolicyStatus( policyVO.getStatus() );
		lei.setPolLinkingId( policyVO.getPolLinkingId() );
		lei.setEndtId( endtIdToProcess );
		
		// Get the sections that needs to be displayed on premium summary page
		SectionList secList = getSectionList( lei );
		
		List<PremiumSummarySectionVO> prmSummarySec = new com.mindtree.ruc.cmn.utils.List<PremiumSummarySectionVO>( PremiumSummarySectionVO.class );
		// for each section get the location and premium details
		for( Integer secId : secList.getSelectedSec() ){
			//From pasPremSumLocList which contains unordered list of location map the location belonging 
			//section id for the current loop to premiumSummarySectionVO
			PremiumSummarySectionVO premiumSummarySectionVO = getSecList( secId, pasPremSumLocList, endtIdToProcess );
			premiumSummarySectionVO.setSecId( secId.shortValue() );
			//Populate the govt tax for each section id
			premiumSummarySectionVO.setGovtTax( getGovtTax(secId, endtIdToProcess, policyVO ).doubleValue() );
			premiumSummarySectionVO.setVattTax((getVatTax( secId, endtIdToProcess, policyVO ).doubleValue() ));
			
			
			prmSummarySec.add( premiumSummarySectionVO );
		}
	
		premiumSummary.setPrmSummarySec( prmSummarySec );
		premiumSummary.setLinkingId( policyVO.getPolLinkingId() );
		premiumSummary.setEndtId( SvcUtils.getLatestEndtId( policyVO ) );
	}
	

	/*
	 * For each section ID get the location details for VTrnPasPremSumLoc result
	 */
	private PremiumSummarySectionVO getSecList( Integer secId, List<VTrnPasPremSumLoc> pasPremSumLocList, Long endtId ){
		PremiumSummarySectionVO premiumSummarySec = new PremiumSummarySectionVO();
		List<VTrnPasPremSumLoc> pasPremSecList = getSecPrmList( pasPremSumLocList, secId );
		List<PremiumSummaryVO> prmSummarySec = new com.mindtree.ruc.cmn.utils.List<PremiumSummaryVO>( PremiumSummaryVO.class );
		for( VTrnPasPremSumLoc pasPremSec : pasPremSecList ){

			PremiumSummaryVO premiumSummaryVO = new PremiumSummaryVO();
			premiumSummaryVO.setCommission( pasPremSec.getId().getCommission() );
			/*premiumSummaryVO.setCoverSiAmt( pasPremSec.getId().getCoverSiAmt() );
			premiumSummaryVO.setCoverPrmAmt( pasPremSec.getId().getCoverPrmAmt() );*/
			premiumSummaryVO.setCoverSiAmt( SvcUtils.getNonNullPrmSI( PremiumHelper.getPrmOrSI( pasPremSec, SUMINSURED, endtId, pasPremSec.getId().getPolQuoFlag(),getHibernateTemplate(),secId ) ) );
			premiumSummaryVO.setCoverPrmAmt( SvcUtils.getNonNullPrmSI( PremiumHelper.getPrmOrSI( pasPremSec, PREMIUM, endtId, pasPremSec.getId().getPolQuoFlag() ,getHibernateTemplate(),secId) ) );
			premiumSummaryVO.setLocationName( pasPremSec.getId().getLocationName() );
			premiumSummaryVO.setStatus( pasPremSec.getId().getStatus().byteValue() );
			prmSummarySec.add( premiumSummaryVO );
		}
		premiumSummarySec.setPrmSummarySec( prmSummarySec );
		return premiumSummarySec;
	}
	
	

	/*
	 * 
	 */
	private List<VTrnPasPremSumLoc> getSecPrmList( List<VTrnPasPremSumLoc> pasPremSumLocList, Integer secId ){
		List<VTrnPasPremSumLoc> pasPremSumSecList = new ArrayList<VTrnPasPremSumLoc>();
		for( VTrnPasPremSumLoc pasPremSumLoc : pasPremSumLocList ){
			if( !Utils.isEmpty( pasPremSumLoc ) ){
				if( Integer.valueOf( pasPremSumLoc.getId().getSecId() ).equals( secId ) ){
					pasPremSumSecList.add( pasPremSumLoc );
				}
			}
		}
		return pasPremSumSecList;
	}
	

	private SectionList getSectionList( BaseVO input ){

		LoadExistingInputVO lei = (LoadExistingInputVO) input;
		List<Integer> sections = new com.mindtree.ruc.cmn.utils.List<Integer>( Integer.class );

		if( lei.isQuote() ){
			List<TTrnPolicySectionsQuo> sectionList = getHibernateTemplate().find(
					"from TTrnPolicySectionsQuo section where section.id.tpsLinkingId=? ", lei.getPolLinkingId() );
			for( TTrnPolicySectionsQuo policySectionsQuo : sectionList ){
				if( !Utils.isEmpty( policySectionsQuo ) ){
					if( !Utils.isEmpty( policySectionsQuo.getId().getTpsSecId() ) ){
						Short sectionString = policySectionsQuo.getId().getTpsSecId();
						sections.add( Integer.valueOf( sectionString.toString() ) );
					}
				}
			}
		}
		else{
			List<TTrnSectionDetailsQuo> sectionDetailsQuoList = null;

			String sqlQuery = "select max(s.sec_endt_id) from t_trn_policy p , t_trn_section_details s where p.pol_policy_id = s.sec_policy_id and s.sec_endt_id <= "+lei.getEndtId()+" and p. pol_linking_id = "+lei.getPolLinkingId();
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery( sqlQuery );
			List<Object> result = query.list();
			if(Utils.isEmpty( result )){
				
				throw new BusinessException( "cmn.unknownError", null, "For linking id "+lei.getPolLinkingId()+" Endorsment id is unavilable in section details table" );
			}
			Long endtSecId = ( (BigDecimal) result.get( 0 ) ).longValue();
			
			sectionDetailsQuoList = getHibernateTemplate()
					.find( "from TTrnSectionDetailsQuo section where section.id.secPolicyId in (select p.id.polPolicyId from TTrnPolicyQuo p where p.polLinkingId = ? and p.id.polEndtId <= ?)"
							+ " and  section.id.secValidityStartDate <= (select  distinct ( polValidityStartDate ) from  TTrnPolicyQuo p where p.polLinkingId = ? and p.id.polEndtId = ? )"
							+ " and section.secValidityExpiryDate > (select distinct ( polValidityStartDate ) from  TTrnPolicyQuo p where p.polLinkingId = ? and p.id.polEndtId = ? )"
							+ "and secEndtId <= ? ", lei.getPolLinkingId(),  endtSecId , lei.getPolLinkingId(),  endtSecId  , lei.getPolLinkingId(),  endtSecId ,
							endtSecId );

			for( TTrnSectionDetailsQuo detailsQuo : sectionDetailsQuoList ){
				sections.add( (int) detailsQuo.getId().getSecSecId() );
			}
		}
		
		
        Set<Integer> uniqueSecList = CopyUtils.asSet( sections );
        sections = CopyUtils.asList( uniqueSecList );
		
		Collections.sort(sections);
		sortAllSections( sections );
		SectionList secList = new SectionList();
		secList.setSelectedSec( sections );
		return secList;
	}

	/**
	 * Call's Delete Location procedure for the section and location combination.
	 * @param policyVO
	 * @param sectionVO
	 * @param delLocationInputVO
	 * @param currentEndtId
	 * @param isNew
	 */
	public void delLocationProcCall( PolicyVO policyVO, SectionVO sectionVO, DelLocationInputVO delLocationInputVO, Long currentEndtId, Integer isNewForProc ){

		PASStoredProcedure sp = null;// (PASStoredProcedure) (policyVO.getIsQuote().booleanValue() ? Utils.getBean( "delLocationProc" ) : Utils.getBean( "endtDeleteLocation" ) );
		
		//If there is no policy id for a section, it means there is no record for that class code in t_trn_policy/quo.
		//In such cases do not call Delete Location procedures. 
		if( !Utils.isEmpty( sectionVO.getPolicyId() ) && !SvcConstants.ZERO.equals( sectionVO.getPolicyId() ) ){
		
		Integer classCode = sectionVO.getClassCode();
		if( policyVO.getIsQuote().booleanValue() ){
			sp = (PASStoredProcedure) Utils.getBean( "delLocationProc" );
			Map results = sp.call( policyVO.getPolLinkingId(), currentEndtId, isNewForProc, delLocationInputVO.getBuildingId(), sectionVO.getSectionId(), policyVO.getPremiumVO()
					.getPremiumAmt(), policyVO.getCreatedBy(), policyVO.getNewValidityStartDate() );
			if (Utils.isEmpty(results)) {
				LOGGER.debug("The result of the stored procedure is nul_3");
			}
		}
		else{
			sp = (PASStoredProcedure) Utils.getBean( "endtDeleteLocation" );
			Map results = sp.call( policyVO.getPolLinkingId(), currentEndtId, isNewForProc, delLocationInputVO.getBuildingId(), sectionVO.getSectionId(), policyVO.getPremiumVO()
					.getPremiumAmt(), policyVO.getCreatedBy(), policyVO.getNewValidityStartDate(), policyVO.getEndEffectiveDate() );
			if (Utils.isEmpty(results)) {
				LOGGER.debug("The result of the stored procedure is nul_4");
			}
		}
		
		//System.out.print( results );
		DAOUtils.updateVED( delLocationInputVO.getPolicy(), classCode, sectionVO.getSectionId() );
		
		//updating endorsement text for deleting location
		//DAOUtils.deleteCntforEndorsementFlow( sectionVO.getPolicyId(), policyVO, sectionVO.getSectionId(),  Long.valueOf(delLocationInputVO.getBuildingId()) );
		/*
         * Endorsed data will not be available to procedure to generate endorsement text using old and new data
         * before the hibernate session is commited and hence flushing the session.
         */
        getHibernateTemplate().flush();
      //updating endorsement text for deleting location
        /*
         * Endt text neesd to be generated only in endorsment flow
         */
		if(!policyVO.getIsQuote().booleanValue()){
			Long policyId =  DAOUtils.getSectionPolicyId( policyVO, sectionVO.getSectionId(), getHibernateTemplate() );
			Integer cntCategory =  DAOUtils.getContentCategory(sectionVO.getSectionId());
			DAOUtils.deleteLocationEndtText( policyId, policyVO, sectionVO.getSectionId(),  Long.valueOf(delLocationInputVO.getBuildingId()),cntCategory );
		}
		
	}
			
	}
	
	/**
	 * This function is used to fetch premium summary details
	 * Its updated the discount premium in amend flow
	 * @param input
	 * @return
	 */
	@Override
	public BaseVO updateSpecialPremium( BaseVO baseVO ){
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				PolicyVO policyVO = (PolicyVO) baseVO;
				if( policyVO.getAppFlow().equals( Flow.AMEND_POL ) || policyVO.getAppFlow().equals( Flow.EDIT_QUO ) ){
					List<TTrnPolicyQuo> policyQuoList = getHibernateTemplate()
							.find( "from TTrnPolicyQuo policyQuo where policyQuo.polLinkingId=? and policyQuo.id.polEndtId=? and policyQuo.polValidityExpiryDate=? and policyQuo.polPolicyType=?",
									policyVO.getPolLinkingId(), SvcUtils.getLatestEndtId( policyVO ), SvcConstants.EXP_DATE,
									Short.valueOf( Utils.getSingleValueAppConfig( "POLICY_TYPES" ) ) );
					short baseClassCode = getBaseClassCode( policyQuoList);
					for( TTrnPolicyQuo policyQuo : policyQuoList ){
						PremiumHelper.updateSpecialCovers( policyVO, policyQuo, getHibernateTemplate() );
						if(policyQuo.getPolClassCode() == baseClassCode)
						{
							PremiumHelper.updatePolicyFee(policyVO,policyQuo,getHibernateTemplate());
						}
						PremiumHelper.updateGovtTax( policyVO, policyQuo, getHibernateTemplate() );
						/*VAT*/
						PremiumHelper.updateVatTax(policyVO, policyQuo, getHibernateTemplate());
					}
				}

				return baseVO;
			}
		}
		return baseVO;
			
	}
	
	/**
	 * Clear's contextMap values from ThreadLevelContext
	 */
	public void clearThreadLevelContext(){
		ThreadLevelContext.clearAll();
	}
	
	public static void sortAllSections(List<Integer> parameterNames)
	{
		int indxPL;
		int indxPAR = parameterNames.indexOf(1);
		if (indxPAR != -1)
		{
			indxPL = parameterNames.indexOf(6);
			if (indxPL != -1)
			{
				parameterNames.remove( indxPL );
				parameterNames.add( 1, 6 );
			}
		}
		else 
		{
			indxPL = parameterNames.indexOf(6);
			parameterNames.remove( indxPL );
			parameterNames.add( 0, 6 );
		}
	}
	
	@Override
	public BaseVO handleRulesForAllSections( BaseVO baseVO ){
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] data = holderVO.getData();
		PolicyVO policyVO = (PolicyVO) data[ 0 ];
		Integer sectionId = (Integer) data[ 1 ];
		String locId = (String) data[ 2 ];
		Integer locationId = new Integer( locId );

		/*Start of ticket 137704 */
		int userId=0;
		String role=null;
		if(!Utils.isEmpty( policyVO)) {
			UserProfile userProfileVO = (UserProfile) policyVO.getLoggedInUser();
			if (!Utils.isEmpty(userProfileVO)) {
			userId= userProfileVO.getRsaUser().getUserId();       
			role=DAOUtils.getUserRole(policyVO);
			}
		}
		/*End of ticket 137704 */
		
		if( !Utils.isEmpty( policyVO.getPolLinkingId() ) ){
			
			/* Deleting the entries related only to the specific location and section for which the rules were called. */
			String sqlQuery = "Delete from t_trn_temp_pas_referral where tpr_pol_linking_id = "+policyVO.getPolLinkingId()+" and tpr_sec_id = "+sectionId+" and tpr_location_id = "+locationId;
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(sqlQuery);
			//int result = query.executeUpdate();
			query.executeUpdate();
		
			if( !Utils.isEmpty( policyVO.getMapReferralVO() ) ){
				
				ReferralVO referralVO;
				TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean("tempPasReferralDAO");
				
				for( Entry<ReferralLocKey, ReferralVO> mapRefEntry : policyVO.getMapReferralVO().entrySet() ){
					
					referralVO = mapRefEntry.getValue();
					referralVO.setPolLinkingId( policyVO.getPolLinkingId() );
					/*Start of ticket 137704 */
						referralVO.setTprUserId(userId);
						referralVO.setTprUserRole(role);
					/*End of ticket 137704 */
					
					insertTempPasReferalDao.insertReferal(referralVO);
					
				}
			}
		}
		
		return policyVO;
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
	
	/*Added by Anveshan */
	@Override
	public BaseVO getSavedSectionListForQuote( BaseVO input ){
		
		PolicyVO policy = (PolicyVO) input;
		List<Integer> savedSections = new com.mindtree.ruc.cmn.utils.List<Integer>( Integer.class );
		List<TTrnSectionDetailsQuo> sectionDetailsQuoList = null;
		
		sectionDetailsQuoList = (List<TTrnSectionDetailsQuo>) getHibernateTemplate()
		.find( "from TTrnSectionDetailsQuo section where (section.id.secPolicyId,section.secEndtId,section.id.secSecId)IN (select max(sec.id.secPolicyId) , max(sec.secEndtId), sec.id.secSecId from TTrnSectionDetailsQuo sec where sec.secEndtId<=? and sec.id.secPolicyId in(select p.id.polPolicyId from TTrnPolicyQuo p where p.polLinkingId = ?) group by sec.id.secSecId) and section.secStatus <> ? ",
				policy.getEndtId(), policy.getPolLinkingId(), SvcConstants.DEL_SEC_LOC_STATUS );
		
		for( TTrnSectionDetailsQuo detailsQuo : sectionDetailsQuoList ){
			savedSections.add( (int) detailsQuo.getId().getSecSecId() );
		}
		
		SectionList secList = new SectionList();
		secList.setSelectedSec( savedSections );
		return secList;
	}
}
