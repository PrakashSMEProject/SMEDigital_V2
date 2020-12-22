package com.rsaame.pas.pl.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TMasOccupancy;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuoId;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PLUWDetails;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class PublicLiabilityDAO extends BaseSectionSaveDAO implements
		IPublicLiabilityDAO {

	private final static Logger LOGGER = Logger.getLogger( PublicLiabilityDAO.class );
	private final static Short PL_CLASS_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "PL_CLASS" ) );
	private final static Short PL_POLICY_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) );
	private final static Long PL_RISK_CODE = Long.valueOf( Utils.getSingleValueAppConfig( "PL_RISK_CODE" ) );
	private final static Long PL_ENDT_ID = Long.valueOf( Utils.getSingleValueAppConfig( "PL_ENDT_ID" ) );
	private final static String WCTPL_SEQ = "seq_wctpl_premise_id";
	private final static Integer PL_RC_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_RC_CODE" ) );
	private final static Integer PL_RSC_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_RSC_CODE" ) );
	private final static Integer PL_COV_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_COV_CODE" ) );
	private final static Integer PL_CT_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_CT_CODE" ) );
	private final static Integer PL_CST_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_CST_CODE" ) );
	private final static Short PL_CLASS = Short.valueOf( Utils.getSingleValueAppConfig( "PL_CLASS" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	private final static Integer PL_CRITERIA_CODE=Integer.valueOf( Utils.getSingleValueAppConfig( "PL_CRITERIA_CODE" ) );
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	
	@Override
	public BaseVO loadPLSection(BaseVO baseVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO savePLSection(BaseVO baseVO) {
		return saveSection(baseVO);
	}

	@Override
	protected int getSectionId() {
		return SvcConstants.SECTION_ID_PL;
	}

	@Override
	protected int getClassCode() {
		return PL_CLASS.intValue();
	}

	@Override
	protected BaseVO saveSection(BaseVO input) {

		/* Saving Public liability data */
		PolicyVO policyVO = (PolicyVO) input;
		
		//SvcUtils.writeObjToFile( policyVO );
		
		/* Let us get the system date right now and use from here on for this transaction. */
		/* Not required to set it again as it is already set within BaseSectionSaveDAO */
		//ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );
		
		SectionVO plSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
		PublicLiabilityVO plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetails( locationDetails, plSection );
	
		/* Handle the Premise data. Premium update for the Premise will be handled inside
		 * this method. */
		/* In this method we should 
		 1)check if record exists in PAR(building) corresponding to this location
		  	a)is PAR building
		  	b)is case of new insert
		 2)insert into Premise quo(new bldId if par not present
		 3) update premium 	
		
		*/
		TTrnWctplPremiseQuo premiseQuo = handlePremise( policyVO, plSection, locationDetails, plDetails);
		
		/* In this method we should 
		 1)check if record exists in PAR(building) corresponding to this location
		  	a)is PAR building
		  	b)is case of new insert
		 2)insert into Premise quo(new bldId if par not present
		
		
		*/
		
		/* Handle the UW questions. */
		
		handleUWQ(policyVO, plSection, locationDetails, plDetails,premiseQuo);
		
		return policyVO;
	}

	private TTrnWctplPremiseQuo handlePremise( PolicyVO policyVO, SectionVO plSection, LocationVO locationDetails, PublicLiabilityVO plDetails){
		/* Check if the location was added through PAR. If yes, we will need the T_TRN_BUILDING(_QUO) record for
		 * further processing. */
		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		Integer basicSectionID = null;
		/* If the riskGroupId is a number, then it must be an already saved location. In that case, it could be a location that was added through
		 * PAR (for which there should be a T_TRN_BUILDING(_QUO) record or it could be a PL-added location. */
		if( Utils.isNumber( locationDetails.getRiskGroupId() ) ){
			/* Let us first check if it is a PAR building. */
			try{
				if( isSectionPresent( PAR_SECTION_ID, policyVO ) ){
					basicSectionID = PAR_SECTION_ID;
				}
				SectionVO basicSection = null;
				if(!Utils.isEmpty( basicSectionID )){
					 basicSection = PolicyUtils.getSectionVO( policyVO, basicSectionID );
				}
									
				if(!Utils.isEmpty( basicSection )){
					//Renewal Multiple Id's handling changes, added policy in the query parameter
					buildingQuo = (TTrnBuildingQuo) DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_BUILDING, policyVO.getAppFlow(), getHibernateTemplate(),
							false, null, basicSection.getPolicyId(),Long.valueOf( locationDetails.getRiskGroupId() ) );
				}
			}
			catch( BusinessException be ){
				/* Not a PAR-added building. Not an exceptional scenario. We have to check for WCTPL Premise now. */
				be.printStackTrace();
			}

			/* a. If building record doesn't exist */
			if( !Utils.isEmpty( buildingQuo ) ){
				try{
					//Renewal Multiple Id's handling changes, added policy in the query parameter
					premiseQuo = (TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID, policyVO.getAppFlow(),
							getHibernateTemplate(), false, null, plSection.getPolicyId(),Long.valueOf( locationDetails.getRiskGroupId() ) );
				}
				catch( BusinessException e ){
					/* Not yet created in T_TRN_WCTPL_PREMISE(_QUO). */
					e.printStackTrace();
				}

			}
			/* If PL is the basic section */
			else{
				try{
					//Renewal Multiple Id's handling changes, added policy in the query parameter
					premiseQuo = (TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE, policyVO.getAppFlow(),
							getHibernateTemplate(), false, null, plSection.getPolicyId(),Long.valueOf( locationDetails.getRiskGroupId() ) );
				}
				catch( BusinessException e ){
					/* Not yet created in T_TRN_WCTPL_PREMISE(_QUO). This should never occur because the riskGroupId is a number. However, this is
					 * being allowed for now to allow other sections to become the base section in future. */
					e.printStackTrace();
				}
			}
		}

		/* Call handleTableRecord() for WCTPL_PREMISE after figuring out the right building Id to be used. */
		Long bldId = null;
		String tableId = null;
		Long policyId = plSection.getPolicyId();
		/* If we have a WCTPL_PREMISE record already, then we have to use PAR building Id, if PAR is present, else Wbd_Id. */
		if( !Utils.isEmpty( premiseQuo ) ){
			if( !Utils.isEmpty( buildingQuo ) ){
				tableId = SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID;
				bldId = premiseQuo.getWbdBldId();
			}
			else{
				tableId = SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE;
				bldId = premiseQuo.getId().getWbdId();
			}
		}
		/* If we don't have a WCTPL_PREMISE record already, then we will pass NULL as building Id, so that it is identified as a CREATE
		 * SaveCase in isToBeCreated(). */
		else{
			tableId = SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE;
		}
		//Renewal Multiple Id's handling changes, added policy in the query parameter
		premiseQuo = handleTableRecord( tableId, policyVO, new POJO[]{ buildingQuo,premiseQuo },new BaseVO[]{locationDetails}, false,policyId, bldId );
		
		/* Setting buildingId into BasicRiskId for a particular section ( or for a particular class code ) into ThreadLevelContext which is stored into the extra column added to 
		 * T_TRN_SECTION_LOCATION / T_TRN_SECTION_LOCATION_QUO  
		 * */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject( premiseQuo.getId().getWbdId() )  );
		
		/*  Handling of Premium insertion */
		handlePremiumInsertion( policyVO,  plSection,  locationDetails,  plDetails , buildingQuo,premiseQuo );
		
		return premiseQuo;
	}
	
	private void handleUWQ( PolicyVO policyVO, SectionVO plSection, LocationVO locationDetails, PublicLiabilityVO plDetails, TTrnWctplPremiseQuo trnWctplPremiseQuo ){


		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );

		POJO[] uwqDeps = { trnWctplPremiseQuo };
		UWQuestionsVO questionsVOs = plDetails.getUwQuestions();
		List<UWQuestionVO> questions = questionsVOs.getQuestions();
		for( UWQuestionVO question : questions ){
			BaseVO[] uwqDepsVO = { locationDetails, question };
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE, policyVO, uwqDeps, uwqDepsVO, false, plSection.getPolicyId(), question.getQId(),
					( Long.valueOf( rg.getRiskGroupId() ) ), Utils.isEmpty( question.getResponse() ) ? null: question.getResponse());
		}
	}

	private void handlePremiumInsertion( PolicyVO policyVO, SectionVO plSection, LocationVO locationDetails, PublicLiabilityVO plDetails, TTrnBuildingQuo buildingQuo,TTrnWctplPremiseQuo premiseQuo ){
		/*TTrnWctplPremiseQuo premisequo = null;
		if( Utils.isNumber( locationDetails.getRiskGroupId() ) ){
			premisequo = 
			(TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE, policyVO.getAppFlow(), 
												  getHibernateTemplate(), false, null,
												  Long.valueOf( locationDetails.getRiskGroupId() ) );
		}*/
		POJO[] depsPOJO = { buildingQuo,premiseQuo };
		BaseVO[] depsVO = { locationDetails,plDetails};
		
		TTrnPremiumQuo premium = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, depsPOJO, depsVO, false, Long.valueOf( plSection.getPolicyId() ),BigDecimal.valueOf( Long.valueOf(premiseQuo.getId().getWbdId()) ),BigDecimal.valueOf(Long.valueOf( premiseQuo.getId().getWbdId() )),Short.valueOf( "1" ),Short.valueOf( "0" ),Short.valueOf( "0" ) );
		if(!Utils.isEmpty(  premium.getPrmPremium())){
			PremiumHelper.logPremiumInfo( "Section Level premium for PL :" +premium.getPrmPremium().doubleValue());
			if(!Utils.isEmpty(plDetails.getPremium())){
			plDetails.getPremium().setPremiumAmt(  premium.getPrmPremium().doubleValue());
			}else {
				PremiumVO premiumVO = new  PremiumVO();
				plDetails.setPremium(premiumVO);
				plDetails.getPremium().setPremiumAmt(  premium.getPrmPremium().doubleValue());
			}
		}else{
			PremiumHelper.logPremiumInfo( "Section Level premium for PL : null - defaulting to 0.0");
			plDetails.getPremium().setPremiumAmt( 0.0 );
		}
		
	}
	
	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;
		TTrnWctplPremiseQuo premiseQuoExisting = null;

		//possible mappings

		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID.equals( tableId ) ){
			SectionVO plSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
			PublicLiabilityVO plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetails( locationDetails, plSection );

			/*premiseQuoExisting = fetchWCTPLRecord( plDetails.getPolicyId(), locationDetails, isPARBuilding );
			if( Utils.isEmpty( premiseQuoExisting ) ){
				isCaseOfInsert = true;
			}
			else{
				isCaseOfInsert = false;
			}*/

			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );
			TTrnWctplPremiseQuo premiseQuo = getPojoPremise( premiseQuoExisting, plDetails, locationDetails, occupancy, plSection.getPolicyId(), (TTrnBuildingQuo) deps[ 0 ],
					policyVO, (PLUWDetails) plDetails.getUwDetails()/*, isCaseOfInsert*/ );

			mappedPOJO = premiseQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			SectionVO plSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
			PublicLiabilityVO plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetails( locationDetails, plSection );

			UWQuestionVO question = (UWQuestionVO) depsVO[ 1 ];
			TTrnWctplPremiseQuo premiseQuo = (TTrnWctplPremiseQuo) deps[ 0 ];
			TTrnUwQuestionsQuo questionsQuo = saveUWAPojo( question, plDetails, plSection.getPolicyId(), premiseQuo,policyVO);

			mappedPOJO = questionsQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			SectionVO plSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
			PublicLiabilityVO plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetails( locationDetails, plSection );

			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );

			TTrnPremiumQuo premiumQuo = getPremiumPojo( plDetails, policyVO, plSection.getPolicyId(), (TTrnWctplPremiseQuo) deps[ 1 ], occupancy );

			mappedPOJO = premiumQuo;
		}
		return mappedPOJO;
	}

	@Override
	protected boolean isToBeCreated(String tableId, PolicyVO policyVO,
			POJO[] depsPOJO, BaseVO[] depsVO) {
		boolean isToBeCreated = false;
	
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, false );
			SectionVO plSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
			/*
			 * Insertion or deletion in premium table depends on the  
			 */
			TTrnWctplPremiseQuo premiseQuo=(TTrnWctplPremiseQuo)depsPOJO[1];
			TTrnPremiumQuo premiumQuo = null;
			if( !Utils.isEmpty( premiseQuo ) ){
					premiumQuo = 
				(TTrnPremiumQuo) DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(), 
													  getHibernateTemplate(), false, null,
													  Long.valueOf( plSection.getPolicyId() ),BigDecimal.valueOf( Long.valueOf(premiseQuo.getId().getWbdId()) ),BigDecimal.valueOf(Long.valueOf( premiseQuo.getId().getWbdId() )),Short.valueOf( "1" ),Short.valueOf( "0" ),Short.valueOf( "0" ) );		
			}
			if( Utils.isEmpty( locationDetails.getRiskGroupId() ) || locationDetails.getRiskGroupId().startsWith( "L" ) ||
					Utils.isEmpty( premiumQuo ) )
			{
						ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, true );
						isToBeCreated = true;
			}
		}	
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			
			isToBeCreated = ( (Boolean)ThreadLevelContext.get( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED ) ) ? true : false; 
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, null );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			return toCreateUWQuestionsRecord( policyVO, depsPOJO, depsVO );
		}
		
		return isToBeCreated;
	}

	@Override
	protected boolean isToBeDeleted(String tableId, PolicyVO policyVO,
			POJO[] depsPOJO, BaseVO[] depsVO) {
		boolean isToBeDeleted = false;
		ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
		
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID.equals( tableId ) ){
			LOGGER.debug( "Inside isToBeDeleted for Premise table" );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			LOGGER.debug( "Inside isToBeDeleted for Premium table" );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			LOGGER.debug( "Inside isToBeDeleted for UW Questions table" );
		}
		
		return isToBeDeleted;
	}

	/**
	 * Updates the identifiers to the VOs for assistance during next SAVE processing.
	 */
	@Override
	protected void updateKeyValuesToVOs(String tableId, POJO mappedRecord,
			PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase) {

		LocationVO locationVO = null;
		if(depsVO[0] instanceof LocationVO)
		{
		 locationVO = (LocationVO)depsVO[0];
		}
		SectionVO plSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
		PublicLiabilityVO plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetails( locationDetails, plSection );

		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID.equals( tableId ) ){
			updateKeyValuesToVOs( (TTrnWctplPremiseQuo) mappedRecord, locationVO, plDetails);
		}
		
		
	}
	
	/**
	 * Updates the key values (BldId) of the building or (WbdId) of the premise to LocationVO and PublicLiabilityVO.
	 * 
	 * @param trnBuildingQuo
	 * @param locationDetails
	 * @param parDetails
	 */
	private void updateKeyValuesToVOs( POJO pojo, LocationVO locationDetails, PublicLiabilityVO plDetailsVO){
		if(pojo instanceof TTrnWctplPremiseQuo && !Utils.isEmpty(locationDetails)){
			if( !Utils.isEmpty( ( (TTrnWctplPremiseQuo) pojo ).getWbdBldId() ) ){
				locationDetails.setRiskGroupId( String.valueOf( ( (TTrnWctplPremiseQuo) pojo ).getWbdBldId() ) );
			}
			else if( !Utils.isEmpty( ( (TTrnWctplPremiseQuo) pojo ).getId().getWbdId() ) ){
				locationDetails.setRiskGroupId( String.valueOf( ( (TTrnWctplPremiseQuo) pojo ).getId().getWbdId() ) );
			}
			plDetailsVO.setBasicRiskId( ( (TTrnWctplPremiseQuo) pojo ).getId().getWbdId() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject( ( (TTrnWctplPremiseQuo) pojo ).getId().getWbdId() ) );
		}
	}

	@Override
	protected void tableRecPostSaveProcessing(String tableId,
			POJO mappedRecord, PolicyVO policyVO) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void tableRecPostProcessing(String tableId, POJO mappedRecord,
			PolicyVO policyVO) {
		// TODO Auto-generated method stub

	}

	@Override
	protected POJOId constructCreateRecordId(String tableId, PolicyVO policyVO,
			POJO mappedRecord) {
		POJOId id = null;
		
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID.equals( tableId ) ){
			TTrnWctplPremiseQuoId tid = new TTrnWctplPremiseQuoId();
			Long cntSequence = NextSequenceValue.getNextSequence( WCTPL_SEQ, null,null,getHibernateTemplate() );
			tid.setWbdId( cntSequence );
			tid.setWbdValidityStartDate(  (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD )  );
			
			id = tid;
		}
		if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			TTrnUwQuestionsQuo questionsQuo = (TTrnUwQuestionsQuo) mappedRecord;
			TTrnUwQuestionsQuoId uId = questionsQuo.getId();
			id = uId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) mappedRecord;
			TTrnPremiumQuoId pId = premiumQuo.getId();
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		
		return id;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId(String tableId,PolicyVO policyVO, T existingId) {
		
		POJOId id = null;
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE_BLDID.equals( tableId ) ){
			TTrnWctplPremiseQuoId existingTId = (TTrnWctplPremiseQuoId) existingId;
			TTrnWctplPremiseQuoId tid = new TTrnWctplPremiseQuoId();
			tid.setWbdId( existingTId.getWbdId() );

			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuoId pId;
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			LOGGER.debug( "Inside constructChangeRecordId for UW_QUesions" );
		}
		return id;
	}

	private TTrnPremiumQuo getPremiumPojo( PublicLiabilityVO plDetails, PolicyVO policyDetails, Long policyId, TTrnWctplPremiseQuo premiseQuo,
			TMasOccupancy occupancy ){
		
		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.parseInt( PL_RISK_CODE.toString() ) );
		
		/* If PAR exists Basic Risk Id will be Bld Id */
		/** SK : Changes */
		/** 
		 *  Data Fix : PrmBasicRskId and PrmRskId will always be wbd_id i.e.
		 *  premise table sequence 
		 */
		/*
		if( buildingQuo != null ){
			premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( buildingQuo.getId().getBldId() ) );
			premiumQuoId.setPrmRskId( BigDecimal.valueOf( buildingQuo.getId().getBldId() ) );
		}
		else{ */
			/* Premise Bld Id will become basic risk id */
			//premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( premiseQuo.getId().getWbdId() ) );
		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );	
		premiumQuoId.setPrmRskId( BigDecimal.valueOf( premiseQuo.getId().getWbdId() ) );
		/*} */

		premiumQuoId.setPrmRskCode( Integer.parseInt( PL_RISK_CODE.toString() ) );
		premiumQuoId.setPrmCovCode( Short.valueOf( PL_COV_CODE.toString() ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( PL_CST_CODE.toString() ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( PL_CT_CODE.toString() ) );
		premiumQuoId.setPrmPolicyId( policyId );
		premiumQuoId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		premiumQuo.setId(premiumQuoId);
		premiumQuo.setPrmEndtId( PL_ENDT_ID );
		premiumQuo.setPrmClCode( Short.valueOf( PL_CLASS_CODE.toString() ) );
		premiumQuo.setPrmPtCode( PL_POLICY_TYPE );
		premiumQuo.setPrmRcCode( PL_RC_CODE );
		premiumQuo.setPrmRscCode( PL_RSC_CODE );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER);
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		/*
		 * FIX: Backend issue, ri risk code for class 7 will be 701 
		 */
		premiumQuo.setPrmRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.PL_RI_RSK_CODE ) )  );
		premiumQuo.setPrmRtCode( occupancy.getOcpRtCode() );
		premiumQuo.setPrmPreparedDt((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
		if(!Utils.isEmpty(plDetails.getSumInsuredDets()) && !Utils.isEmpty(plDetails.getSumInsuredDets().getSumInsured())) {
		
			premiumQuo.setPrmSumInsured(new BigDecimal(plDetails.getSumInsuredDets().getSumInsured()));
		}
		premiumQuo.setPrmCompulsoryExcess(BigDecimal.valueOf(plDetails.getSumInsuredDets().getDeductible().doubleValue()));
		
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		
		SvcUtils.setAuditDetailsforPrm(premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
		if( !Utils.isEmpty( plDetails.getPremium() ) ){
			if( !Utils.isEmpty( plDetails.getPremium().getPremiumAmt() ) ){
				premiumQuo.setPrmPremium( new BigDecimal( String.valueOf(plDetails.getPremium().getPremiumAmt() ) ));
				premiumQuo.setPrmPremiumActual( new BigDecimal(String.valueOf( plDetails.getPremium().getPremiumAmt() )) );
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		else{
			setZeroPrmValue( premiumQuo );
		}
		
		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );
		premiumQuo.setPrmRiLocCode( SvcConstants.APP_PRM_RI_LOC_CODE );
		
		return premiumQuo;
	}
	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
	}
	private TTrnWctplPremiseQuo getPojoPremise( TTrnWctplPremiseQuo premiseQuoExisting, PublicLiabilityVO plDetails, LocationVO locationVO, TMasOccupancy occupancy, Long policyId, TTrnBuildingQuo buildingQuo,
			PolicyVO polVO, PLUWDetails plUWDetails/*, boolean isCaseOfInsert*/ ){
		/* If there was an existing record in T_TRN_WCTPL_PREMISE_QUO, then we should use that Pojo instance and update the values
		 * in it, so that the record can be updated. If the passed premiseQuoExisting is NULL, then it means that there is no 
		 * existing record in the table. Hence, create a new instance of the Pojo. */
		/*TTrnWctplPremiseQuo premiseQuo = Utils.isEmpty( premiseQuoExisting ) ? new TTrnWctplPremiseQuo() : premiseQuoExisting;*/
		TTrnWctplPremiseQuo premiseQuo = new TTrnWctplPremiseQuo();

		premiseQuo = BeanMapper.map( plDetails, premiseQuo );
		premiseQuo = BeanMapper.map( occupancy, premiseQuo );
		premiseQuo.setWbdPolicyId( policyId );

		/* If there is an existing T_TRN_WCTPL_PREMISE_QUO record, then use the Id from the premiseQuoExisting, else
		 * create a new one. */
		/*TTrnWctplPremiseQuoId premiseQuoId = Utils.isEmpty( premiseQuoExisting ) ? new TTrnWctplPremiseQuoId() : 
											 premiseQuoExisting.getId();*/
		
		/*if( isCaseOfInsert ){
			Long cntSequence = NextSequenceValue.getNextSequence( WCTPL_SEQ, getHibernateTemplate() );
			premiseQuoId.setWbdId( cntSequence );
		}*/
		
		/*premiseQuoId.setWbdValidityStartDate( (Date)ThreadLevelContext.get( "VSD" ) );*/
		/*premiseQuo.setId( premiseQuoId );*/
		premiseQuo.setWbdRskCode( PL_RISK_CODE );
		premiseQuo.setWbdValidityExpiryDate( SvcConstants.EXP_DATE );
		premiseQuo.setWbdEndtId( PL_ENDT_ID );//PL_ENDT_ID
		
		premiseQuo.setWbdCbCode( 0 );

		//Check if PAR section entry exists through buildingQuo null check
		if( !Utils.isEmpty( buildingQuo ) ){
			premiseQuo.setWbdBldId( buildingQuo.getId().getBldId() );
			premiseQuo.setWbdDirCode( Long.valueOf( buildingQuo.getBldDirCode().toString() ) );
			premiseQuo.setWbdEName( buildingQuo.getBldEName() );
			premiseQuo.setWbdEStreetName( buildingQuo.getBldEStreetName() );
			premiseQuo.setWbdAAddress1( buildingQuo.getBldAAddress1() );
			premiseQuo.setWbdFreeZone( buildingQuo.getBldFreeZone() );
			premiseQuo.setWbdMunCode( Long.valueOf( buildingQuo.getBldMunCode().toString() ) );
			premiseQuo.setWbdEAddress1( buildingQuo.getBldEAddress1() );
			premiseQuo.setWbdPremiseDesc( buildingQuo.getBldDesc() );
			if( !Utils.isEmpty( buildingQuo.getBldZip()) ){
				premiseQuo.setWbdZip(buildingQuo.getBldZip());
			}
		}
		else{
			premiseQuo = BeanMapper.map( locationVO, premiseQuo );
		}
		
		/*FreeZone and Directorate fields are editables in PL even though fetching them from PAR, 
		 * hence here these fields are fetched from locationVO and PL premise table is updated accordingly*/
		if( !Utils.isEmpty( locationVO.getDirectorate()) ){
		premiseQuo.setWbdDirCode( Long.valueOf(locationVO.getDirectorate().toString())  );
		}
		
		if( !Utils.isEmpty( locationVO.getFreeZone()) ){
		premiseQuo.setWbdFreeZone( locationVO.getFreeZone() );
		}
		
		premiseQuo.setWbdFlatNo( ( !Utils.isEmpty( locationVO.getAddress().getOfficeShopNo() ) ? Long.valueOf(locationVO.getAddress().getOfficeShopNo().trim()) : null ));
		
		premiseQuo.setWbdNo( ( !Utils.isEmpty( locationVO.getAddress().getFloor() ) ? locationVO.getAddress().getFloor() : null));		
		
		if(Utils.isEmpty(premiseQuo.getWbdEName())){
			premiseQuo.setWbdEName(locationVO.getAddress().getBuildingName());
		}
		premiseQuo = BeanMapper.map( plUWDetails, premiseQuo );
		premiseQuo.setWbdStatus( (byte) 1 );//Status to be one during quote creation
		
		if(!Utils.isEmpty(plDetails.getSumInsuredDets())) {
			if(!Utils.isEmpty(plDetails.getSumInsuredDets().getSumInsured())){
				premiseQuo.setWbdSumInsured(new BigDecimal(plDetails.getSumInsuredDets().getSumInsured()));
			}
		}
		
		if(!Utils.isEmpty(plDetails.getSumInsuredBasis())){
			premiseQuo.setWbdAName(plDetails.getSumInsuredBasis().toString());
		}
		
		/*
		 * Start Date and end date to be cascaded from PolicyVO.validityStartDate
		 * which is updated by General Info Save Operation. Hence the same
		 * validityStartDate to be carried accross all DB entries for the
		 * Quote
		 */
		/*
		 * Fix for ePlatform 2.1 release. Correcting start date and end date being set
		 */
		if (!Utils.isEmpty(polVO.getScheme().getEffDate()))
		{
			premiseQuo.setWbdStartDate(polVO.getScheme().getEffDate());
		}
		else
		{
			premiseQuo.setWbdStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		}
		
		if(!Utils.isEmpty(polVO.getPolExpiryDate()))
		{
			premiseQuo.setWbdEndDate( polVO.getPolExpiryDate() );
		}

		
		premiseQuo.setWbdRiRskCode(  Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.PL_RI_RSK_CODE ) ) ); 
		
		setAuditDetailsforpremise(premiseQuo,polVO);
		
		premiseQuo.setWbdTerCode(locationVO.getAddress().getLocOverrideTer().shortValue());

        premiseQuo.setWbdJurCode(locationVO.getAddress().getLocOverrideJur().shortValue());

        premiseQuo.setWbdIndemnityLimitAmt(plDetails.getIndemnityAmtLimit());
        
		/*Defect Fix Start: Setting OccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
        premiseQuo.setWbdGeoLimit(premiseQuo.getWbdWayNo().toString());
		BigDecimal occTradeCode=DAOUtils.getOccupancyTradeCode(getHibernateTemplate(), polVO, getSectionId(),BigDecimal.valueOf(occupancy.getOcpTradeCode()),PL_CRITERIA_CODE) ;
		if(!Utils.isEmpty(occTradeCode))premiseQuo.setWbdWayNo(occTradeCode.longValue() );
		/*Defect Fix End: Setting OccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
        
		return premiseQuo;
	}
	private void setAuditDetailsforpremise(TTrnWctplPremiseQuo premiseQuo,
			PolicyVO polVO) {
		Integer userId = SvcUtils.getUserId(polVO);
		
		premiseQuo.setWbdPreparedBy(userId);
		premiseQuo.setWbdPreparedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
		premiseQuo.setWbdModifiedBy(userId);
		premiseQuo.setWbdModifiedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
	}
	
	private TTrnUwQuestionsQuo saveUWAPojo( UWQuestionVO questionVO,PublicLiabilityVO liabilityVO, long policyId, TTrnWctplPremiseQuo premiseQuo, PolicyVO policyVO ){

		
			TTrnUwQuestionsQuo uwQuestionsQuo = new TTrnUwQuestionsQuo();
			TTrnUwQuestionsQuoId id = new TTrnUwQuestionsQuoId();
			id.setUqtPolPolicyId( policyId );
			id.setUqtPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
			id.setUqtUwqCode( questionVO.getQId() );
			uwQuestionsQuo.setId( id );
			uwQuestionsQuo.setUqtUwqAnswer( questionVO.getResponse() );
			uwQuestionsQuo.setUqtValidityStartDate( (Date)ThreadLevelContext.get( "VSD" ) );
			uwQuestionsQuo.setUqtValidityExpiryDate( SvcConstants.EXP_DATE );
			if(!Utils.isEmpty( premiseQuo.getWbdBldId() )){
				/* Comes from PAR */
				uwQuestionsQuo.getId().setUqtLocId( premiseQuo.getWbdBldId() );
			}else if(!Utils.isEmpty( premiseQuo.getId().getWbdId() )){
				uwQuestionsQuo.getId().setUqtLocId( premiseQuo.getId().getWbdId());
			}
			

		return uwQuestionsQuo;
	}
	
	/*private TTrnPremiumQuo getPremiumQuo( PublicLiabilityVO plDetails, PolicyVO policyDetails, Long policyId, TTrnBuildingQuo buildingQuo,TTrnWctplPremiseQuo premiseQuo,
			TMasOccupancy occupancy ){
		// TODO Auto-generated method stub
		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.parseInt( PL_RISK_CODE.toString() ) );
		
		 If PAR exists Basic Risk Id will be Bld Id 
		*//** SK : Changes *//*
		*//** 
		 *  Data Fix : PrmBasicRskId and PrmRskId will always be wbd_id i.e.
		 *  premise table sequence 
		 *//*
		
		if( buildingQuo != null ){
			premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( buildingQuo.getId().getBldId() ) );
			premiumQuoId.setPrmRskId( BigDecimal.valueOf( buildingQuo.getId().getBldId() ) );
		}
		else{ 
			 Premise Bld Id will become basic risk id 
			premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( premiseQuo.getId().getWbdId() ) );
			premiumQuoId.setPrmRskId( BigDecimal.valueOf( premiseQuo.getId().getWbdId() ) );
		} 

		premiumQuoId.setPrmRskCode( Integer.parseInt( PL_RISK_CODE.toString() ) );
		premiumQuoId.setPrmCovCode( Short.valueOf( PL_COV_CODE.toString() ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( PL_CST_CODE.toString() ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( PL_CT_CODE.toString() ) );
		premiumQuoId.setPrmPolicyId( policyId );
		premiumQuoId.setPrmValidityStartDate( (Date)ThreadLevelContext.get( "VSD" ) );
		premiumQuo.setId(premiumQuoId);
		premiumQuo.setPrmEndtId( PL_ENDT_ID );
		premiumQuo.setPrmClCode( Short.valueOf( PL_CLASS_CODE.toString() ) );
		premiumQuo.setPrmPtCode( PL_POLICY_TYPE );
		premiumQuo.setPrmRcCode( PL_RC_CODE );
		premiumQuo.setPrmRscCode( PL_RSC_CODE );
		premiumQuo.setPrmSitypeCode( (byte) 2 );// default value
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmRiRskCode( occupancy.getOcpRiRskCode() );
		premiumQuo.setPrmRtCode( occupancy.getOcpRtCode() );
		premiumQuo.setPrmPreparedDt((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
		if(!Utils.isEmpty(plDetails.getSumInsuredDets()) && !Utils.isEmpty(plDetails.getSumInsuredDets().getSumInsured())) {
		
			premiumQuo.setPrmSumInsured(BigDecimal.valueOf(plDetails.getSumInsuredDets().getSumInsured()));
		}
		premiumQuo.setPrmCompulsoryExcess(BigDecimal.valueOf(plDetails.getSumInsuredDets().getDeductible().doubleValue()));
		
		premiumQuo.setPrmEffectiveDate( policyDetails.getStartDate() );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		
		SvcUtils.setAuditDetailsforPrm(premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
		Added to enter premium value
		if(!Utils.isEmpty( plDetails.getPremium() ))
		{
			if(!Utils.isEmpty( plDetails.getPremium().getPremiumAmt() )){
				premiumQuo.setPrmPremium( new BigDecimal(plDetails.getPremium().getPremiumAmt()));
				premiumQuo.setPrmPremiumActual( new BigDecimal( plDetails.getPremium().getPremiumAmt() ) );
			}else{
				setZeroPrmValue( premiumQuo );
			}
		}else{
			setZeroPrmValue( premiumQuo );
		}
		
		
		
		return premiumQuo;
	}*/
	
	/**
	 * Sets LocationVO.toSave to false so that this location doesn't get picked up in the next SAVE call.
	 */
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		cascadeUWAnswers( policyVO );
		updateEndtText( policyVO );
		deleteParRecordFromPolicy(policyVO);
		
		super.sectionPostProcessing( policyVO );
	}

	/**
	 * 1. Check if PL is a basic section
	 * 2. IF PL basic section,get the PAR record from T_TRN_POLICY_QUO which got inserted during General Info Save 
	 * 3. Delete the record fetched in step 2
	 * @param policyVO
	 */
	private void deleteParRecordFromPolicy( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.CREATE_QUO ) || ( policyVO.getAppFlow() == Flow.EDIT_QUO )){
			/*
			 * (1)
			 */
			if(  SvcUtils.isSectionPresent( SvcConstants.SECTION_ID_PL, policyVO )   && 
				 !SvcUtils.isSectionPresent( SvcConstants.SECTION_ID_PAR, policyVO ) &&
				 !SvcUtils.isSectionPresent( SvcConstants.SECTION_ID_MB, policyVO )  &&
				 !SvcUtils.isSectionPresent( SvcConstants.SECTION_ID_EE, policyVO )
			  )
			{
				/*
				 * (2)
				 */
				Short ParClassCode = new Short( String.valueOf( SvcConstants.CLASS_ID_PAR ) );
				TTrnPolicyQuo policyQuo = null;
				List<TTrnPolicyQuo> policyQuoList = (List<TTrnPolicyQuo>) DAOUtils.getTableSnapshotQuery( "T_TRN_POLICY", Flow.CREATE_QUO, 
						getHibernateTemplate(), false,policyVO.getEndtId(), policyVO.getPolLinkingId(), ParClassCode );

				if( !Utils.isEmpty( policyQuoList ) ) policyQuo = policyQuoList.get( 0 );

				
				
				/*
				 * Ticket : 164420 Start Added condition for JLT for cumulative
				 * loss quantum
				 */
				
					String schemeCode = "";
					Date preparedDate = new Date();
					if (!Utils.isEmpty(policyVO.getScheme())) {
						schemeCode = policyVO.getScheme().getSchemeCode().toString();
					}
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
							modifiedDate= s3.parse(dateFormat.format(date));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
					boolean isValidSceheme= SvcUtils.isValidSchemeCode(policyVO);
					if (isValidSceheme
							&& (JLTLiveDate.compareTo(preparedDate) <= 0 ||  JLTLiveDate.compareTo(modifiedDate) <= 0)  && policyVO.getIsQuote()
							&& SvcConstants.DUBAI == Integer
									.parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))) {
						if (!Utils.isEmpty(policyQuo)) {
							
							SectionVO plSectionVO = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
							policyVO.setPolicyNo( plSectionVO.getPolicyId() );/* Policy Id which will be used for General Info Screen */
							policyVO.setDefaultClassCode(SvcConstants.CLASS_ID_PL); /*Changing default class code */
							
							String sqlQuery = "update T_Trn_Policy_Comments set Poc_Policy_Id = " + plSectionVO.getPolicyId() +
"  where Poc_Policy_Id = " + policyQuo.getId().getPolPolicyId() + " and poc_endt_id =" + policyVO.getEndtId()
									+" and Poc_Reason_Code = 72 " ;
							Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
							Query query = session.createSQLQuery( sqlQuery );
							query.executeUpdate();
						}
					}

					/*
					 * Ticket : 164420 End  Added condition for JLT for cumulative
					 * loss quantum
					 */
				
				
				
				
				
				
				
				
				
				/*
				 * (3)
				  */
				if( !Utils.isEmpty( policyQuo ) ) { 
					
					/* After deletion of PAR record i.e. class code 2 record from T_TRN_POLICY_QUO update policy Id 
					 * within VO in order to avoid unexpected usage of PAR policy id */
					SectionVO plSectionVO = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
					policyVO.setPolicyNo( plSectionVO.getPolicyId() );/* Policy Id which will be used for General Info Screen */
					policyVO.setDefaultClassCode(SvcConstants.CLASS_ID_PL); /*Changing default class code */
					
					/*
					 * Change the policy id of the UW q's in general info page to PL policy ID. While saving general info class code 2 policy id and location ID is saved 
					 * as 0 is saved 
					 */
					
					String sqlQuery = "update T_TRN_UW_QUESTIONS_QUO set uqt_pol_policy_id = " + plSectionVO.getPolicyId() + "  where uqt_pol_policy_id = " + policyQuo.getId().getPolPolicyId()
							+" and uqt_loc_id = " + SvcConstants.zeroVal;
					Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
					Query query = session.createSQLQuery( sqlQuery );
					query.executeUpdate();
					
					delete( policyQuo );
					
				}
				
				
				
				
			}
		}

	}

	/**
	 * 1. Deletes previous endorsement Text
	 * 2. If Add Location is done in PL, premise add text is updated
	 * 3. If any of the fields on PL screen is changed, premise column change text is updated
	 * @param policyVO
	 */
	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			SectionVO plSection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
			RiskGroupDetails currRGD = PolicyUtils.getRiskGroupDetails( locationDetails, plSection );

			/*
			 * (1).
			 */
			DAOUtils.deletePrevEndtText( plSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), PL_SECTION_ID,
					Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			
			DAOUtils.deletePrevEndtText( plSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), PL_SECTION_ID,
					 (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) );

			/*
			 * (2).
			 */
			LOGGER.debug("call pro_endt_text_wc_prmse_add" );
			DAOUtils.updatePRMADDforendorsementFlow( plSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ) );

			/*
			 * (3).
			 */
			LOGGER.debug( "call pro_endt_text_wc_prmse_col" );
			DAOUtils.updatePRMMODorendorsementFlow( plSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), currRGD.getBasicRiskId() );
//			Commenting as the procedure to generate endt text for delete location is invoked from SectionDao for all sections.		
//			LOGGER.debug( "updated endt text for  Public Liablity delete location " );
//			DAOUtils.deleteEndtTextPublicLiablity( plSection.getPolicyId(), policyVO, currRGD.getBasicRiskId());
			
			
			// Call to procedure for generating endt text for UW Questions changes.
			
			
			LOGGER.debug( "call UW changes change endo SP" );
			DAOUtils.updateUWQuestions( plSection.getPolicyId(), policyVO,plSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()) );
			
			LOGGER.debug( "call deductible change endo SP" );
			DAOUtils.updateDeductibleforendorsementFlow( plSection.getPolicyId(), policyVO,plSection.getSectionId(),  (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ),Long.valueOf( locationDetails.getRiskGroupId()) );
			
			//DAOUtils.updateTotalSITextforendorsementFlow( plSection.getPolicyId(), policyVO,plSection.getSectionId(),  (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ,Long.valueOf( locationDetails.getRiskGroupId()));
			
			// Calls procedure for generating endt Text for New Risk added.
			
			/*LOGGER.debug( "call Risk ADD changes change endo SP" );*/
			//DAOUtils.updateEndTextForRiskAdd( plSection.getPolicyId(), policyVO,plSection.getSectionId());
			
		}

	}

	private void cascadeUWAnswers( PolicyVO policyVO ){
		SectionVO plSection = PolicyUtils.getSectionVO( policyVO,  SvcConstants.SECTION_ID_PL);
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( plSection );
		PublicLiabilityVO plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetails( locationDetails, plSection );
		
		if( !Utils.isEmpty( plDetails.getUwQuestions() ) && !Utils.isEmpty( plDetails.getUwQuestions().isCascaded() ) && plDetails.getUwQuestions().isCascaded() ){
			
			PASStoredProcedure sp = (PASStoredProcedure) ( policyVO.getIsQuote() ? Utils.getBean( "cascadeUwqProc_QUO" ) : Utils.getBean( "cascadeUwqProc_POL" ) );
			
			try{
				getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
				Map results = sp.call( policyVO.getPolLinkingId(), plSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ),
						(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ), plDetails.getBasicRiskId(),  SvcConstants.SECTION_ID_PL );
				if (Utils.isEmpty(results)) {
					LOGGER.debug("The result of the stored procedure is null");
				}
			}
			catch( DataAccessException e ){
				throw new BusinessException( "par.uwqCascade.exception", e, "An exception occured while executing stored proc." );
			}
		}		
		
	}

	/**
	 * 
	 * @param sectionId to set int
	 * @param policyDetails to set PolicyVO
	 * @return the boolean
	 */
	private boolean isSectionPresent(int sectionId, PolicyVO policyDetails){
			SectionVO section = new SectionVO(RiskGroupingLevel.LOCATION);
			section.setSectionId(sectionId);
			return policyDetails.getRiskDetails().contains(section);
	}
	
	@Override
	protected void handleAdditionalCovers( SectionVO sectionVO, PolicyVO policyVO ){
		/* No additional cpvers for Public Liability */
	}
	
	@Override
	public List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails currRgd ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void sectionPreProcessing( PolicyVO policyVO ){
		super.sectionPreProcessing( policyVO );
	}

	@Override
	public List<TTrnPolicyQuo> getQuote(Long quoteNo, Long endtId) {
		return DAOUtils.getPolRecForQuo(getHibernateTemplate(), endtId, quoteNo);
	}

	@Override
	public Date getPreparedDate(Long quoteNo) {
		return DAOUtils.getPreparedDate(getHibernateTemplate(), quoteNo);
	}
}
