package com.rsaame.pas.quote.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.BaseSectionLoadDAO;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPolicySectionsQuo;
import com.rsaame.pas.dao.model.TTrnSectionDetailsQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.gen.domain.TMasCashCustomerQuo;
import com.rsaame.pas.gen.domain.TMasInsured;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class GeneralInfoLoadDAO extends BaseSectionLoadDAO{

	public PolicyVO loadGeneralInfo( BaseVO input ){
		PolicyVO policyVO = new PolicyVO();

		LoadExistingInputVO lei = (LoadExistingInputVO) input;
		Long endId = lei.getEndtId();
		lei.setSectionId( SvcConstants.SECTION_ID_GEN_INFO );
		Integer distChannel = null;
		Integer brokerCode = null;
		Integer tarCode = null;
		Integer schemeCode = null;
		Long agentCode = null;

		//Long policyId = lei.getPolicyId();

		/*Fetch TTrnPolicyQuo based on  policyId and endId*/
		TTrnPolicyQuo tTrnPoLQuo = getTtrnPolQuo( lei );
		
		lei.setPolicyStatus( Integer.valueOf( tTrnPoLQuo.getPolStatus() ) );

		GeneralInfoVO generalInfoVO = BeanMapper.map( tTrnPoLQuo, GeneralInfoVO.class );
		if( !Utils.isEmpty( tTrnPoLQuo ) && !Utils.isEmpty( tTrnPoLQuo.getPolDistributionChnl() ) ){
			distChannel = tTrnPoLQuo.getPolDistributionChnl();
		}
		
		if( !Utils.isEmpty( tTrnPoLQuo ) && !Utils.isEmpty( tTrnPoLQuo.getPolAgentId() ) ){
			agentCode = tTrnPoLQuo.getPolAgentId();
		}
		
		if( !Utils.isEmpty( tTrnPoLQuo ) && !Utils.isEmpty( tTrnPoLQuo.getPolBrCode() )){
			brokerCode = Integer.valueOf( tTrnPoLQuo.getPolBrCode().intValue() );
		}

		SchemeVO schemeVO = BeanMapper.map( tTrnPoLQuo, SchemeVO.class );
		if( !Utils.isEmpty( tTrnPoLQuo ) && !Utils.isEmpty( tTrnPoLQuo.getPolTarCode() ) ){
			tarCode = Integer.valueOf( tTrnPoLQuo.getPolTarCode() );
		}
		
		if( !Utils.isEmpty( tTrnPoLQuo ) && !Utils.isEmpty( tTrnPoLQuo.getPolCoverNoteHour() ) ){
			schemeCode = tTrnPoLQuo.getPolCoverNoteHour();
		}
		
		/*Fetch TMasInsured based on PolInsuredCode() */
		TMasInsured tMasInsured = fetchTmasInsured( tTrnPoLQuo.getPolInsuredCode() );
		generalInfoVO = BeanMapper.map( tMasInsured, generalInfoVO );
		
		if( !Utils.isEmpty( tMasInsured ) ){
			if( !Utils.isEmpty( tMasInsured.getInsCountry() ) ) generalInfoVO.getInsured().getAddress().setCountry( tMasInsured.getInsCountry().intValue() );
		}
		TMasCashCustomerQuo tMasCashCustomerQuo = fetchCashCustomerId( tTrnPoLQuo.getId().getPolPolicyId(), endId, lei );
		generalInfoVO = BeanMapper.map( tMasCashCustomerQuo, generalInfoVO );

		//generalInfoVO.getAdditionalInfo().setInsuredStatus( Integer.valueOf( tTrnPoLQuo.getPolStatus()) );
		generalInfoVO.getInsured().setCity( tMasCashCustomerQuo.getCshCity() );
		/* This check is not needed here*/
		/*long dateDiff = tMasInsured.getInsModifiedDt().getTime() - tMasCashCustomerQuo.getCshModifiedDt().getTime();
		if(dateDiff > 0)policyVO.setInsuredChanged( true );
		else if(dateDiff < 0)policyVO.setInsuredChanged( false );*/
		
		// Added for JLT
		if (tMasCashCustomerQuo.getCshETelexNo() != null) {
			generalInfoVO.getClaimsHistory().setTelexNo(Integer.valueOf(tMasCashCustomerQuo.getCshETelexNo()));
		}
		
		resetSourceOfBusValues(generalInfoVO,schemeVO,distChannel,brokerCode,agentCode,schemeCode,tarCode);
		
		if( !Utils.isEmpty( tTrnPoLQuo ) ){
			if( !Utils.isEmpty( tTrnPoLQuo.getPolPolicyTerm() ) ) policyVO.setPolicyTerm( tTrnPoLQuo.getPolPolicyTerm() );
			if( !Utils.isEmpty( tTrnPoLQuo.getPolPolicyNo() ) ) policyVO.setPolicyNo( tTrnPoLQuo.getPolPolicyNo() );
			if( !Utils.isEmpty( tTrnPoLQuo.getPolQuotationNo() ) ) policyVO.setQuoteNo( tTrnPoLQuo.getPolQuotationNo() );
			if( !Utils.isEmpty( tTrnPoLQuo.getPolPreparedBy() ) ) policyVO.setCreatedBy( new String( tTrnPoLQuo.getPolPreparedBy().toString() ) );
			if( !Utils.isEmpty( tTrnPoLQuo.getPolProcessedDate() ) ) policyVO.setProcessedDate( tTrnPoLQuo.getPolProcessedDate() );
			if( !Utils.isEmpty( tTrnPoLQuo.getPolEndtNo() ) ) policyVO.setEndtNo(tTrnPoLQuo.getPolEndtNo() );
			if( !Utils.isEmpty( tTrnPoLQuo.getPolRefPolicyNo() ) ) policyVO.setPolRefPolicyNo( tTrnPoLQuo.getPolRefPolicyNo());
			
			/*VAT*/
			if( !Utils.isEmpty( tTrnPoLQuo.getPolVatRegNo() ) ) policyVO.setPolVatRegNo( tTrnPoLQuo.getPolVatRegNo());
			if( !Utils.isEmpty( tTrnPoLQuo.getPolvatCode() ) ) policyVO.setPolVATCode( tTrnPoLQuo.getPolvatCode());

			
			if( Utils.isEmpty( tTrnPoLQuo.getPolModifiedBy())){
				if(tTrnPoLQuo.getPolEndtNo()==0) policyVO.setUpdatedBy(tTrnPoLQuo.getPolPreparedBy().toString());
			} else {
				policyVO.setUpdatedBy(tTrnPoLQuo.getModifiedBy().toString());
			}
						

			/* Bug Fix SIT 14: insuredStatus( in additionalInfoVO) is to be set from the status in T_MAS_INSURED which is already done above 
			while mapping TMASINSURED to GeneralInfoVo and status ( in PolicyVO)is to be set from T_TRN_POLICY_QUO
			Hence commenting the below line for necessary corrections. */
			//if(!Utils.isEmpty( tTrnPoLQuo.getPolStatus()))generalInfoVO.getAdditionalInfo().setInsuredStatus( Integer.valueOf( tTrnPoLQuo.getPolStatus()) );
		}
		AuthenticationInfoVO authenticationInfoVO = BeanMapper.map( tTrnPoLQuo, AuthenticationInfoVO.class );

		authenticationInfoVO.setIntAccExecCode( tMasCashCustomerQuo.getCshIntAccExecCode() );
		authenticationInfoVO.setExtAccExecCode( tMasCashCustomerQuo.getCshExtAccExecCode() );
		authenticationInfoVO.setAccountingDate( tTrnPoLQuo.getPolIssueDate() );
		//Added as created Date is overridden by Current date on Click of Next from General Info Page in SBS
		authenticationInfoVO.setCreatedOn((Timestamp) tTrnPoLQuo.getPolPreparedDt());
		/*
		 * Loads the general uw questions in the general info vo if configured for that section
		 */
		policyVO.setIsQuote( lei.isQuote() );
		policyVO.setPolLinkingId( lei.getPolLinkingId() );
		loadUWQs( lei, DAOUtils.getBaseSectionPolicyId( policyVO, getHibernateTemplate()), tTrnPoLQuo.getPolValidityStartDate() , generalInfoVO );
		

		policyVO.setGeneralInfo( generalInfoVO );
		/*Bug Fix Start : SIT 14 setting Status */
		if( !Utils.isEmpty( tTrnPoLQuo.getPolStatus() ) ) policyVO.setStatus( Integer.valueOf( tTrnPoLQuo.getPolStatus() ) );
		/*Bug Fix End : SIT 14 setting Status */
		
		/* Commenting the below line as it was over-riding the Business type fetched from tMasCashCustomerQuo.
		 * Need to make sure that the Business type is updated correctly in tTrnPolicy/Quo.
		 * */
		//if( !Utils.isEmpty( tTrnPoLQuo.getPolBusinessType() ) ) policyVO.getGeneralInfo().getInsured().setBusType( ( tTrnPoLQuo.getPolBusinessType().intValue() ) );
		policyVO.setScheme( schemeVO );
		policyVO.setAuthInfoVO( authenticationInfoVO );

		/*
		* Set class code of the policy id which is fetched so that the same is updated
		* to hidden class_code field on GeneralInfoContent.jsp
		*/
		policyVO.setDefaultClassCode( Integer.valueOf( Short.valueOf( tTrnPoLQuo.getPolClassCode() ).toString() ) );

		/* TODO : Change the field to fetch Policy Id (currently it is policyNo) */
		if( !Utils.isEmpty( policyVO.getGeneralInfo().getAdditionalInfo() ) ){
			if( !Utils.isEmpty( tTrnPoLQuo.getId() ) )
				policyVO.getGeneralInfo().getAdditionalInfo().setPolicyId( Integer.valueOf( Long.valueOf( tTrnPoLQuo.getId().getPolPolicyId() ).toString() ) );
			/* 
			 * set issuing location and processing location of the branch.
			 */
			if( !Utils.isEmpty( tTrnPoLQuo.getPolLocationCode() ) ){
				policyVO.getGeneralInfo().getAdditionalInfo().setIssueLoc(Integer.valueOf(tTrnPoLQuo.getPolLocationCode()));
			}
			if( !Utils.isEmpty( tTrnPoLQuo.getPolProcLocCode() ) ){
				policyVO.getGeneralInfo().getAdditionalInfo().setProcessingLoc(tTrnPoLQuo.getPolProcLocCode());
			}
		}

		/*
		 * Fetch the ValidityStartDate which is important while re-saving the date across all the tables.
		 * This will help in updating the records instead of inserting new records to the tables 
		 */
		
		policyVO.setPolCustomerId(tTrnPoLQuo.getPolCustomerId());
		policyVO.setValidityStartDate( tTrnPoLQuo.getPolValidityStartDate() );
		policyVO.setNewValidityStartDate( tTrnPoLQuo.getPolValidityStartDate() );
		policyVO.setCreated( tTrnPoLQuo.getPolPreparedDt() );
		policyVO.setConcatPolicyNo( tTrnPoLQuo.getPolConcPolicyNo() );
		policyVO.setEndDate( tTrnPoLQuo.getPolExpiryDate() );
		policyVO.setStartDate( tTrnPoLQuo.getPolIssueDate() );
		policyVO.setPolEffectiveDate( tTrnPoLQuo.getPolEffectiveDate() );
		policyVO.setPolExpiryDate( tTrnPoLQuo.getPolExpiryDate() );
		policyVO.setEndEffectiveDate( tTrnPoLQuo.getPolEndtEffectiveDate() );
        policyVO.setEndtExpiryDate( tTrnPoLQuo.getPolEndtExpiryDate());
        
		//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
		if(!Utils.isEmpty(tTrnPoLQuo.getPolRenewalBasis())){
        	policyVO.setRenewalBasis(tTrnPoLQuo.getPolRenewalBasis());
        }
		//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Ends
        
        policyVO.setEndtId( endId );
        policyVO.setIsQuote( lei.isQuote() );
        policyVO.setPolLinkingId( lei.getPolLinkingId() );
        policyVO.setAppFlow( lei.getAppFlow() );
        if( lei.getAppFlow().equals( Flow.AMEND_POL )){
        	policyVO.getAuthInfoVO().setPrintedDate( null );
        }
        PremiumHelper.updateSpecialPremium(policyVO,getHibernateTemplate());
        PremiumHelper.updatePolicyFee(policyVO,getHibernateTemplate());
        
        
		/* Added for JLT */

		Date preparedDate = new Date();
		if (!Utils.isEmpty(policyVO.getCreated())) {
			preparedDate = policyVO.getCreated();
		}
		SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_HYPHEN);
		String d2 = Utils.getSingleValueAppConfig("JLT_LiveDate");
		Date JLTLiveDate = null;
		try {
			JLTLiveDate = s2.parse(d2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date date = new Date();
		SimpleDateFormat s3 = new SimpleDateFormat(com.Constant.CONST_DATE_FORMAT_HYPHEN);
		Date modifiedDate = new Date();
		String strDateFormat = com.Constant.CONST_DATE_FORMAT_HYPHEN;
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		try {
			modifiedDate = s3.parse(dateFormat.format(date));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean isValidSceheme= SvcUtils.isValidSchemeCode(policyVO);
		if (isValidSceheme
				&& (JLTLiveDate.compareTo(preparedDate) <= 0 || JLTLiveDate.compareTo(modifiedDate) <= 0)
				&& SvcConstants.DUBAI == Integer.parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))
				&& policyVO.getIsQuote()) {
			// to get remarks if exist for cumulative claim ratio
			String comment = DAOUtils.getRemarks(policyVO);
			if (comment != null) {
				policyVO.getGeneralInfo().getAdditionalInfo().setRemarks(comment);

			}
		}

        
        
        
		return policyVO;
	}

	/**
	 * @param lei
	 * @param baseSectionPolicyId
	 * @param polValidityStartDate
	 * @param generalInfoVO
	 */
	private void loadUWQs( LoadExistingInputVO lei, Long baseSectionPolicyId, Date polValidityStartDate, GeneralInfoVO generalInfoVO ){
		/*
		 * For general UW q's the location ID is saved as 0, hence create a locationvo with riskgroup id 0 which represents the location id
		 */
		LocationVO locvo = new LocationVO();
		locvo.setRiskGroupId( String.valueOf( SvcConstants.zeroVal )  );
		
		List<TTrnUwQuestionsQuo> questionsQuo = DAOUtils.getEndtStateUWQ(getHibernateTemplate(),polValidityStartDate,
				baseSectionPolicyId,lei.getEndtId(),locvo,lei);
		
		getHibernateTemplate().getSessionFactory().getCurrentSession().evict( questionsQuo );
		
		if(!Utils.isEmpty( questionsQuo ) && questionsQuo.size() > 0 ){
			List<UWQuestionVO> uwQuestionList= new ArrayList<UWQuestionVO> ();
			UWQuestionsVO uWQuestionsVO = new UWQuestionsVO();
			for(TTrnUwQuestionsQuo questionsVOs : questionsQuo){
				UWQuestionVO uwQuestion=new UWQuestionVO();
				uwQuestion.setQId(questionsVOs.getId().getUqtUwqCode());
				uwQuestion.setResponse(questionsVOs.getUqtUwqAnswer());
				uwQuestionList.add(uwQuestion);
			}
			uWQuestionsVO.setQuestions( uwQuestionList );
			generalInfoVO.setQuestionsVO( uWQuestionsVO );
		}
	}


	/** 
	 * This method is used to re-set the Source of Business details with quote/policy details.
	 *  Added to set the values overridden while mapping generalInfoVO with tMasInsured
	 * @param brokerCode 
	 *  */
	private void resetSourceOfBusValues( GeneralInfoVO generalInfoVO, SchemeVO schemeVO, Integer distChannel, Integer brokerCode, Long agentCode, Integer schemeCode, Integer tarCode ){
		
		if( !Utils.isEmpty( distChannel ) && !Utils.isEmpty( generalInfoVO ) && !Utils.isEmpty( generalInfoVO.getSourceOfBus() )){
			generalInfoVO.getSourceOfBus().setDistributionChannel( distChannel );
		}
		
		if( !Utils.isEmpty( agentCode ) && !Utils.isEmpty( generalInfoVO ) && !Utils.isEmpty( generalInfoVO.getSourceOfBus() ) ){
			generalInfoVO.getSourceOfBus().setDirectSubAgent( agentCode );
		}
		
		if(  !Utils.isEmpty( generalInfoVO ) && !Utils.isEmpty( generalInfoVO.getSourceOfBus() )){
			if(!Utils.isEmpty( brokerCode ) ){
					generalInfoVO.getSourceOfBus().setBrokerName( brokerCode );
		    } else {
			/*Fix for Oman. Direct walkin set broker code as null.*/
			generalInfoVO.getSourceOfBus().setBrokerName( null );
		    }
		}
		
		if( !Utils.isEmpty( schemeCode ) && !Utils.isEmpty( schemeVO ) ){
			schemeVO.setSchemeCode( schemeCode );
		}
		
		if( !Utils.isEmpty( tarCode ) && !Utils.isEmpty( schemeVO ) ){
			schemeVO.setTariffCode( tarCode );
		}
		
	}

	public TTrnPolicyQuo getTtrnPolQuo( LoadExistingInputVO lei ){
		try{

			TTrnPolicyQuo latestPolRecord = null;
			/*short baseClassCode=getBaseClassCode(lei);*/
			if( ( lei.getAppFlow() == Flow.AMEND_POL ) ){
				/*
				 * return ( (TTrnPolicyQuo) getHibernateTemplate().find(
				 * "from TTrnPolicyQuo policyQuo where  polClassCode in (" +
				 * class_code +
				 * ")and policyQuo.polLinkingId=? and policyQuo.polValidityExpiryDate=? order by polClassCode "
				 * , lei.getPolLinkingId(), SvcConstants.EXP_DATE ).get( 0 ) );
				 */
				/*In below query, baseClassCode parameter is added to fetch the result. This is due to the fact that base class code 
				 * record will always have the updated data.*/
				/*
				List<TTrnPolicyQuo> polQuoRecordList= (List<TTrnPolicyQuo> )getHibernateTemplate()
						.find( "from TTrnPolicyQuo polQuo where (polQuo.id.polEndtId, polQuo.polLinkingId ,polQuo.polClassCode ) IN (select max(polQuoInner.id.polEndtId ),polQuoInner.polLinkingId,polQuoInner.polClassCode from "
								+ "TTrnPolicyQuo polQuoInner where polQuoInner.polLinkingId=?  and polQuoInner.polValidityExpiryDate=? and polQuoInner.polStatus<>? and polQuoInner.polClassCode=? group by polQuoInner.polLinkingId,polQuoInner.polClassCode)",
								lei.getPolLinkingId(), SvcConstants.EXP_DATE, SvcConstants.DEL_SEC_LOC_STATUS,baseClassCode );
				*/
				List<TTrnPolicyQuo> polQuoRecordList = (List<TTrnPolicyQuo>) getHibernateTemplate()
						.find("from TTrnPolicyQuo polQuo where polQuo.polLinkingId=? and  polQuo.id.polEndtId=? order by polQuo.id.polEndtId desc ",
								lei.getPolLinkingId(), lei.getEndtId());

				if( !Utils.isEmpty( polQuoRecordList ) ) latestPolRecord = polQuoRecordList.get( SvcConstants.zeroVal );
			
			}else{
				/*return ( (TTrnPolicyQuo) getHibernateTemplate().find(
						"from TTrnPolicyQuo policyQuo where  polClassCode in (" + class_code + ")and policyQuo.polLinkingId=? and policyQuo.id.polEndtId=? order by polClassCode ",
						lei.getPolLinkingId(), lei.getEndtId() ).get( 0 ) );*/

				/* Query considering class code criteria will result in record which might not be for current endorsement id 
				 * for ex - There is no change done in PAR hence there will be no current endorsement id record for class code - 2 , hence below
				 * query will fetch record which is not latest for the policy */
				
				/*In below query, baseClassCode parameter is added to fetch the result. This is due to the fact that base class code 
				 * record will always have the updated data.*/
				/*latestPolRecord = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY_FOR_GI, lei.getAppFlow(), getHibernateTemplate(),
						SvcConstants.IS_TABLE_QUERY_HBM, lei.getEndtId(), lei.getPolLinkingId(),baseClassCode);*/
				
				List<TTrnPolicyQuo> latestPolRecords= (List<TTrnPolicyQuo> )getHibernateTemplate()
						.find( "from TTrnPolicyQuo polQuo where polQuo.polLinkingId=? and  polQuo.id.polEndtId=? order by polQuo.polClassCode desc ",
								lei.getPolLinkingId(), lei.getEndtId() );
				
				if( !Utils.isEmpty( latestPolRecords ) && latestPolRecords.size() > 0 ){
					latestPolRecord = latestPolRecords.get( 0 );
				}
				

				/* Class code criteria is removed hence separate in case of PL as basic section is removed as above query will provide 
				 * latest record for the policy irrespective of the class code (same data is copied accross all policy id entries)
				if( Utils.isEmpty( t ) ){
					t = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_TRN_POLICY_FOR_GI, lei.getAppFlow(), getHibernateTemplate(), 
							   SvcConstants.IS_TABLE_QUERY_HBM, lei.getEndtId(), lei.getPolLinkingId(), (short) SvcConstants.CLASS_ID_PL );
				}*/
			}
			if( Utils.isEmpty( latestPolRecord ) ){
				throw new SystemException( "pas.cmn.noRecordFound", null, "Policy record not found for the base class code." );
			}

			return latestPolRecord;
		}
		catch( HibernateException e ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", e, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_1" );
		}
	}

	private short getBaseClassCode(LoadExistingInputVO lei) {
		Integer classCode=null;
		SectionList sectionList=getSectionListForPolicy(lei);
		if(!Utils.isEmpty(sectionList)){
			List<Integer> selectedSections=sectionList.getSelectedSec();
			if(selectedSections.contains(SvcConstants.SECTION_ID_PAR)){
				classCode=SvcConstants.CLASS_ID_PAR;
			}
			else{
				classCode=SvcConstants.CLASS_ID_PL;
			}
		}
		if(!Utils.isEmpty( classCode ))
			return classCode.shortValue();
		else
			return 0;
	}

	public TMasCashCustomerQuo fetchCashCustomerId( Long policyId, Long endId, LoadExistingInputVO lei ){

		TMasCashCustomerQuo tMasCashCustomerQuo = null;
		try{

			if( ( lei.getAppFlow() == Flow.AMEND_POL ) ){
				tMasCashCustomerQuo = (TMasCashCustomerQuo) getHibernateTemplate().find(
						"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? and cashCustomerQuo.cshValidityExpiryDate=? ", policyId,
						SvcConstants.EXP_DATE ).get( 0 );
				//				 tMasCashCustomerQuo=(TMasCashCustomerQuo) getHibernateTemplate().find(
				//							"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? and cshEndtId=? ",
				//							policyId,endId ).get(0);
			}
			else{
				/*tMasCashCustomerQuo = (TMasCashCustomerQuo) getHibernateTemplate().find(
						"from TMasCashCustomerQuo cashCustomerQuo where  cashCustomerQuo.id.cshPolicyId=? and cshEndtId=? ", policyId, endId ).get( 0 );*/
				tMasCashCustomerQuo = DAOUtils.getExistingEndtIdStateRecord( SvcConstants.TABLE_ID_T_MAS_CASH_CUSTOMER, lei.getAppFlow(), getHibernateTemplate(), 
									  SvcConstants.IS_TABLE_QUERY_HBM, endId, policyId );
			}
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_2" );
		}
		
		if( Utils.isEmpty( tMasCashCustomerQuo ) ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", null, "Cash customer details not found for policy [" + lei.getPolicyId(), "] Endt Id [" + lei.getEndtId(), "]" );
		}

		return tMasCashCustomerQuo;
	}

	public TMasInsured fetchTmasInsured( Long InsuredCode ){

		TMasInsured tMasInsured = null;
		try{
			tMasInsured = (TMasInsured) getHibernateTemplate().find( "from TMasInsured tMasInsured where  tMasInsured.insInsuredCode=? ", InsuredCode ).get( 0 );
		}
		catch( HibernateException hibernateException ){
			throw new BusinessException( "pas.gi.couldNotGetCustDetails", hibernateException, "Error while trying to SELECT customer details from T_MAS_CASH_CUSTOMER_QU_3" );
		}

		return tMasInsured;
	}

	@Override
	protected RiskGroupDetails getRiskDetails( RiskGroup riskGroup, Long policyId, Long endId, LoadExistingInputVO lei ){
		// TODO Auto-generated method stub
		return null;
	}

	private SectionList getSectionListForPolicy( BaseVO input ){

		LoadExistingInputVO lei = (LoadExistingInputVO) input;
		List<Integer> sections = new com.mindtree.ruc.cmn.utils.List<Integer>( Integer.class );

		if( lei.isQuote() ){
			List<TTrnPolicySectionsQuo> sectionList = (List<TTrnPolicySectionsQuo>) getHibernateTemplate().find(
					"from TTrnPolicySectionsQuo section where section.id.tpsLinkingId=? " , lei.getPolLinkingId());
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
				sectionDetailsQuoList = (List<TTrnSectionDetailsQuo>) getHibernateTemplate()
						.find( "from TTrnSectionDetailsQuo section where (section.id.secPolicyId,section.secEndtId,section.id.secSecId)IN (select max(sec.id.secPolicyId) , max(sec.secEndtId), sec.id.secSecId from TTrnSectionDetailsQuo sec where sec.secEndtId<=? and sec.id.secPolicyId in(select p.id.polPolicyId from TTrnPolicyQuo p where p.polLinkingId = ?) group by sec.id.secSecId) and section.secStatus <> ? ",
								lei.getEndtId(), lei.getPolLinkingId(), SvcConstants.DEL_SEC_LOC_STATUS );
			}
			else{
				/*
				 * In case the policy is canceled,to view that policy status 4 check is not required. 
				 * TODO: Combine the queries
				 */
				sectionDetailsQuoList = (List<TTrnSectionDetailsQuo>) getHibernateTemplate()
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
