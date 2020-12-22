/**
 * 
 */
package com.rsaame.pas.fidelity.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TMasOccupancy;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuoId;
import com.rsaame.pas.dao.model.TTrnGaccUnnamedPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccUnnamedPersonQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1016303
 *
 */
public class FidelitySaveDAO extends BaseSectionSaveDAO implements IFidelitysectionDAO{
	private final static Logger LOGGER = Logger.getLogger( FidelitySaveDAO.class );
	private final static Integer FIDELITY_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION" ) );
	private final static Integer FIDELITY_CLASS_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_CLASS" ) );
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	private final static Integer FIDELITY_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_RISK_CODE" ) );
	private final static Integer FIDELITY_RC_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_RC_CODE" ) );
	private final static Integer FIDELITY_RI_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_RI_RISK_CODE" ) );
	private final static Integer FIDELITY_COV_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_COV_CODE" ) );
	private final static Integer FIDELITY_CT_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_CT_CODE" ) );
	private final static Integer FIDELITY_CST_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_CST_CODE" ) );
	private final static Integer FIDELITY_RSC_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_RSC_CODE" ) );
	private final static Integer FIDELITY_COVER_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_COVER_TYPE" ) );
	private final static Integer FIDELITY_COVER_SUB_TYPE = Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_COVER_SUB_TYPE" ) );
	private final static Double FIDELITY_LIMIT = Double.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_LIMIT" ) );

	private final static String GACC_UNNAMED_PERSON_SEQ = "SEQ_GACC_UNNAMED_PERSON_ID";
	private final static String GACC_PERSON_SEQ = "SEQ_GACC_PERSON_ID";

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getSectionId()
	 */
	@Override
	protected int getSectionId(){
		return FIDELITY_SECTION_ID;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getClassCode()
	 */
	@Override
	protected int getClassCode(){
		return FIDELITY_CLASS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#saveSection(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	protected BaseVO saveSection( BaseVO input ){
		LOGGER.info( "Fidelity : Inside Save Section" );
		/**
		 * Saving the Fidelity data
		 */

		PolicyVO policyVO = (PolicyVO) input;
		/** Tables involved 
		 * 1.T_TRN_GACC_PERSON/QUO
		 * 2.T_TRN_GACC_UNNAMED_PERSON/QUO
		 * 3.T_TRN_PREMIUM/QUO
		 * 4.T_TRN_UW_QUESTIONS/QUO 
		 */

		/* Let us get the system date right now and use from here on for this transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_FIDELITY );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );
		FidelityVO fidelityVO = (FidelityVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );

		/**
		 *  Fetch building details of basic risk section.
		 */
		POJO[] buildingPojo = handleBuilding( policyVO, locationDetails );

		/**
		 *  Handle the Gacc_Person data if insuring for named employees else handle gacc_unammed person .
		 *  Premium update for the  will be handled inside
		 *  this method. 
		 */

		if( !Utils.isEmpty( fidelityVO ) )
		{

			if( !Utils.isEmpty( fidelityVO.getUnnammedEmployeeDetails() ) )
			{
				handleUnnamedPerson( policyVO, sectionVO, locationDetails, fidelityVO, buildingPojo );
				//UWQs are only applicable for Un Named Employess
				/*if( Utils.isEmpty( fidelityVO.getUnnammedEmployeeDetails().get( 0 ).getIsToBeDeleted() ))
				{
					handleUWQ( policyVO, sectionVO, locationDetails, fidelityVO, buildingPojo );
				}*/
			}
			if( !Utils.isEmpty( fidelityVO.getFidelityEmployeeDetails() ) )
			{
				if( !Utils.isEmpty( fidelityVO.getFidelityEmployeeDetails().get( 0 ) ) && 
						!Utils.isEmpty( fidelityVO.getFidelityEmployeeDetails().get( 0 ).getEmpName() ) )
				{
					handleNamedPerson( policyVO, sectionVO, locationDetails, fidelityVO, buildingPojo );
					//handleUWQ( policyVO, sectionVO, locationDetails, fidelityVO, buildingPojo );
				}
			}
			//AMS Defect no:141 - Saving uwquestions when aggregate limit is > 250000
			if(!Utils.isEmpty( fidelityVO.getAggregateLimit() ) && (fidelityVO.getAggregateLimit()*2)> FIDELITY_LIMIT ){
					handleUWQ( policyVO, sectionVO, locationDetails, fidelityVO, buildingPojo );
			}
		} 
	handleAggregatePremium(policyVO, sectionVO, locationDetails, fidelityVO, buildingPojo );
		/* Handle other details is any like UW Questions*/
		//handleUWQ( policyVO, sectionVO, locationDetails, fidelityVO, buildingPojo );

	return policyVO;
}

	private void handleUWQ( PolicyVO policyVO, SectionVO sectionVO, LocationVO locationDetails, FidelityVO fidelityVO, POJO[] buildingPojo ){

		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
		//RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, section );

		UWQuestionsVO questionsVOs = fidelityVO.getUwQuestions();
		List<UWQuestionVO> questions = questionsVOs.getQuestions();

		for( UWQuestionVO question : questions ){
			BaseVO[] uwqDepsVO = { question };
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE, policyVO, buildingPojo, uwqDepsVO, false, sectionVO.getPolicyId(), question.getQId(),
					( Long.valueOf( rg.getRiskGroupId() ) ), Utils.isEmpty( question.getResponse() ) ? null : question.getResponse() );
		}

	}

	private POJO[] handleBuilding( PolicyVO policyVO, LocationVO locationVO ){
		/* Check if par or pl is present, basicSectionID will contain the section id of either par or pl */
		Integer basicSectionID = null;
		if( isSectionPresent( PAR_SECTION_ID, policyVO ) ){
			basicSectionID = PAR_SECTION_ID;
		}
		else if( isSectionPresent( PL_SECTION_ID, policyVO ) ){
			basicSectionID = PL_SECTION_ID;
		}
		else{
			throw new BusinessException( "pas.basicSection.mandatory", null, "Either Par or Pl has to be selected to proceed further" );
		}

		SectionVO basicSection = PolicyUtils.getSectionVO( policyVO, basicSectionID );

		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;

		/* If PAR is the basic section, then we have to get the PAR building record and use it for the 
		 * construction of the T_TRN_GACC_BUILDING record POJO. If PL is the basic section, then we 
		 * have to use the WCTPL Premise record for this. */
		if( !Utils.isEmpty( basicSection ) && ( basicSectionID.equals( PAR_SECTION_ID ) ) ){
			if( !Utils.isEmpty( locationVO.getRiskGroupId() ) ){
				try{
					//Renewal Multiple Id's handling changes, added policy in the query parameter
					buildingQuo = (TTrnBuildingQuo) DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_BUILDING, policyVO.getAppFlow(), getHibernateTemplate(),
							false, ( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), basicSection.getPolicyId(), Long.valueOf( locationVO.getRiskGroupId() ) );
					ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, buildingQuo.getId().getBldId() );
				}
				catch( Exception e ){
					throw new BusinessException( "pas.basicSection.IDMandatory", e, "ID from of the basic section is mandatory, no data in bld table" );
				}

			}
			if( Utils.isEmpty( buildingQuo ) ){
				throw new BusinessException( "pas.basicSection.IDMandatory", null, "ID from of the basic section is mandatory" );
			}
		}
		else if( !Utils.isEmpty( basicSection ) && basicSectionID.equals( PL_SECTION_ID ) ){
			PublicLiabilityVO plDetails = (PublicLiabilityVO) basicSection.getRiskGroupDetails().get( locationVO );
			if( !Utils.isEmpty( plDetails ) ){
				// this pojo may not be required, since the id required in case of par is not selected will be available in publicLiabilityVO
				try{
					//Renewal Multiple Id's handling changes, added policy in the query parameter
					premiseQuo = (TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE, policyVO.getAppFlow(),
							getHibernateTemplate(), false, ( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), basicSection.getPolicyId(),
							Long.valueOf( locationVO.getRiskGroupId() ) );
					ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, premiseQuo.getId().getWbdId() );
				}
				catch( Exception e ){
					throw new BusinessException( "pas.basicSection.IDMandatory", e, "ID from of the basic section is mandatory, no data in premise table" );
				}
			}
			if( Utils.isEmpty( premiseQuo ) ){
				throw new BusinessException( "pas.basicSection.IDMandatory", null, "ID from of the basic section is mandatory" );
			}
		}
		else{
			throw new BusinessException( "pas.basicSection.detailsMandatory", null, "Details of the basic section is mandatory" );
		}

		return new POJO[]{ buildingQuo, premiseQuo };

	}

	private boolean isSectionPresent( int sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}

	/**
	 * 
	 * @param policyVO
	 * @param sectionVO
	 * @param locationDetails
	 * @param fidelityVO
	 * @param buildingPojo
	 * This method is used to update the table T_TRN_GACC_PERSON and T_TRN_PREMIUM if 
	 * named employees are being insured.
	 * 
	 */
	private void handleNamedPerson( PolicyVO policyVO, SectionVO sectionVO, LocationVO locationDetails, FidelityVO fidelityVO, POJO[] buildingPojo ){

		java.util.List<FidelityNammedEmployeeDetailsVO> nammedEmployeeDetailsVO = fidelityVO.getFidelityEmployeeDetails();
		FidelityUnnammedEmployeeVO unnammedEmployeeVO = null;
		TTrnGaccPersonQuo tTrnGaccPersonQuo = null;

		for( FidelityNammedEmployeeDetailsVO employeeDetailsVO : nammedEmployeeDetailsVO ){
			if( !Utils.isEmpty( employeeDetailsVO.getGprFidelityId() ) ){
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnGaccPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON, policyVO, buildingPojo, new BaseVO[]{ locationDetails, fidelityVO, sectionVO,
						employeeDetailsVO }, false, sectionVO.getPolicyId(), Long.valueOf( locationDetails.getRiskGroupId() ), employeeDetailsVO.getGprFidelityId() );
			}
			else{
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnGaccPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON, policyVO, buildingPojo, new BaseVO[]{ locationDetails, fidelityVO, sectionVO,
						employeeDetailsVO }, false, sectionVO.getPolicyId(), Long.valueOf( locationDetails.getRiskGroupId() ) );
			}
			/* Next, process the premium record for the GACC_UNNAMED_PERSON record. */
			TTrnPremiumQuo premium = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{}, new BaseVO[]{ locationDetails, fidelityVO, sectionVO,
					employeeDetailsVO, unnammedEmployeeVO }, false, sectionVO.getPolicyId(), BigDecimal.valueOf( tTrnGaccPersonQuo.getId().getGprId() ),
					BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
					FIDELITY_COVER_TYPE.shortValue(), FIDELITY_COVER_SUB_TYPE.shortValue() );

			if( !Utils.isEmpty( premium.getPrmPremium() ) ){
				if( !Utils.isEmpty( fidelityVO.getPremium() ) ){
					fidelityVO.getPremium().setPremiumAmt( premium.getPrmPremium().doubleValue() );
				}

			}
		}

	}
	private void handleAggregatePremium( PolicyVO policyVO, SectionVO sectionVO, LocationVO locationDetails, FidelityVO fidelityVO, POJO[] buildingPojo ){
			/* Next, process the premium record for the Aggregate record. */			
		//TTrnPremiumQuo premium = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE, policyVO, new POJO[]{}, new BaseVO[]{ locationDetails, fidelityVO, sectionVO},
					handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE, policyVO, new POJO[]{}, new BaseVO[]{ locationDetails, fidelityVO, sectionVO},
					false, sectionVO.getPolicyId(),BigDecimal.valueOf( SvcConstants.FID_BASIC_RISK_CODE_FOR_AGGREGATE ),
					BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
					FIDELITY_COVER_TYPE.shortValue(), FIDELITY_COVER_SUB_TYPE.shortValue() );
	}

	/**
	 * 
	 * @param policyVO
	 * @param sectionVO
	 * @param locationDetails
	 * @param fidelityVO
	 * @param buildingPojo
	 * This method is used to update the table T_TRN_GACC_UNNAMED_PERSON and T_TRN_PREMIUM if 
	 * unnamed employees are being insured.
	 */
	private void handleUnnamedPerson( PolicyVO policyVO, SectionVO sectionVO, LocationVO locationDetails, FidelityVO fidelityVO, POJO[] buildingPojo ){

		List<FidelityUnnammedEmployeeVO> unnammedEmployeeList = fidelityVO.getUnnammedEmployeeDetails();
		FidelityNammedEmployeeDetailsVO nammedEmployeeDetailsVO = null;
		TTrnGaccUnnamedPersonQuo tTrnGaccUnnamedPersonQuo = null;

		for( FidelityUnnammedEmployeeVO unnammedEmployeeVO : unnammedEmployeeList ){
			if( !Utils.isEmpty( unnammedEmployeeVO.getGupFidelityId() ) ){
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnGaccUnnamedPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON, policyVO, buildingPojo, new BaseVO[]{ locationDetails, fidelityVO,
						sectionVO, unnammedEmployeeVO }, false, sectionVO.getPolicyId(), Long.valueOf( locationDetails.getRiskGroupId() ), unnammedEmployeeVO.getGupFidelityId() );
			}
			else{
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnGaccUnnamedPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON, policyVO, buildingPojo, new BaseVO[]{ locationDetails, fidelityVO,
						sectionVO, unnammedEmployeeVO }, false, sectionVO.getPolicyId(), Long.valueOf( locationDetails.getRiskGroupId() ) );

			}
			/* Next, process the premium record for the GACC_UNNAMED_PERSON record. */
			TTrnPremiumQuo premium = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{}, new BaseVO[]{ locationDetails, fidelityVO, sectionVO,
					nammedEmployeeDetailsVO, unnammedEmployeeVO }, false, sectionVO.getPolicyId(), BigDecimal.valueOf( tTrnGaccUnnamedPersonQuo.getId().getGupId() ),
					BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
					FIDELITY_COVER_TYPE.shortValue(), FIDELITY_COVER_SUB_TYPE.shortValue() );

			if( !Utils.isEmpty( premium.getPrmPremium() ) ){
				if( !Utils.isEmpty( fidelityVO.getPremium() ) ){
					fidelityVO.getPremium().setPremiumAmt( premium.getPrmPremium().doubleValue() );
				}

			}
		}
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#mapVOToPOJO(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO[], com.mindtree.ruc.cmn.base.BaseVO[])
	 */
	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){

		POJO mappedPOJO = null;
		/**
		 * map to the values if saving the values to GACC_PERSON table.
		 */

		if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			UWQuestionVO question = (UWQuestionVO) depsVO[ 0 ];
			TTrnUwQuestionsQuo questionsQuo = getUWAPojo( question, policyVO, deps );
			mappedPOJO = questionsQuo;
		}
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){

			LocationVO locationDetails = (LocationVO) depsVO[ 0 ];
			FidelityVO fidelityDetails = (FidelityVO) depsVO[ 1 ];
			SectionVO fidelitySection = (SectionVO) depsVO[ 2 ];
			FidelityNammedEmployeeDetailsVO namedEmployee = (FidelityNammedEmployeeDetailsVO) depsVO[ 3 ];

			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );

			TTrnGaccPersonQuo tTrnGaccPersonQuo = getGaccPersonPOJO( policyVO, locationDetails, fidelityDetails, fidelitySection, deps, occupancy, namedEmployee );
			mappedPOJO = tTrnGaccPersonQuo;

		}
		/**
		 * map to the values if saving the values to GACC_UNNAMED_PERSON table.
		 */
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals( tableId ) ){

			//TODO for Unnamed Person
			LocationVO locationDetails = (LocationVO) depsVO[ 0 ];
			FidelityVO fidelityDetails = (FidelityVO) depsVO[ 1 ];
			SectionVO fidelitySection = (SectionVO) depsVO[ 2 ];
			FidelityUnnammedEmployeeVO unnamedEmployeeDetails = (FidelityUnnammedEmployeeVO) depsVO[ 3 ];

			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );

			TTrnGaccUnnamedPersonQuo tTrnGaccUnnamedPersonQuo = getGaccUnnamedPersonPOJO( policyVO, locationDetails, fidelityDetails, fidelitySection, deps, occupancy,
					unnamedEmployeeDetails );
			mappedPOJO = tTrnGaccUnnamedPersonQuo;

		}
		/**
		 * map to the values if saving the values to PREMIUM table.
		 */
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			LocationVO locationDetails = (LocationVO) depsVO[ 0 ];

			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );

			TTrnPremiumQuo premiumQuo = getPremiumPojo( depsVO, policyVO, occupancy );

			mappedPOJO = premiumQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE.equals( tableId ) ){
			LocationVO locationDetails = (LocationVO) depsVO[ 0 ];
			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );
			TTrnPremiumQuo premiumQuo = getAggregatePremiumPojo( depsVO, policyVO, occupancy );
			mappedPOJO = premiumQuo;
		}
		return mappedPOJO;
	}

	private TTrnUwQuestionsQuo getUWAPojo( UWQuestionVO question, PolicyVO policyVO, POJO[] buildingQuo ){
		TTrnUwQuestionsQuo uwQuestionsQuo = new TTrnUwQuestionsQuo();
		TTrnUwQuestionsQuoId id = new TTrnUwQuestionsQuoId();
		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, FIDELITY_SECTION_ID );
		id.setUqtPolPolicyId( parSection.getPolicyId() );
		id.setUqtPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		id.setUqtUwqCode( question.getQId() );
		if( buildingQuo[ 0 ] instanceof TTrnBuildingQuo ){
			id.setUqtLocId( ( (TTrnBuildingQuo) buildingQuo[ 0 ] ).getId().getBldId() );
		}
		else{
			id.setUqtLocId( ( (TTrnWctplPremiseQuo) buildingQuo[ 1 ] ).getId().getWbdId() );
		}
		uwQuestionsQuo.setId( id );
		uwQuestionsQuo.setStatus( SvcConstants.POL_STATUS_PENDING );
		uwQuestionsQuo.setUqtUwqAnswer( question.getResponse() );
		uwQuestionsQuo.setUqtValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		uwQuestionsQuo.setUqtValidityExpiryDate( SvcConstants.EXP_DATE );

		return uwQuestionsQuo;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#isToBeCreated(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO[], com.mindtree.ruc.cmn.base.BaseVO[])
	 */
	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, FIDELITY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );
		//FidelityVO fidelityVO = (FidelityVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
		/**
		 * Need to change isToBeCreated = false TODO
		 */
		boolean isToBeCreated = false;
		if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			return toCreateUWQuestionsRecord( policyVO, depsPOJO, depsVO );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, false );
			FidelityNammedEmployeeDetailsVO namedEmployee = (FidelityNammedEmployeeDetailsVO) depsVO[ 3 ];
			if( !Utils.isEmpty( namedEmployee ) ){
				//Get named employee details
				if( Utils.isEmpty( namedEmployee.getGprFidelityId() ) ){
					ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, true );
					isToBeCreated = true;

				}

			}

		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals( tableId ) ){

			ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, false );
			FidelityUnnammedEmployeeVO unNamedEmployee = (FidelityUnnammedEmployeeVO) depsVO[ 3 ];
			if( !Utils.isEmpty( unNamedEmployee ) ){
				//Get named employee details
				if( Utils.isEmpty( unNamedEmployee.getGupFidelityId() ) ){
					ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, true );
					isToBeCreated = true;

				}

			}

		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE.equals( tableId ) ){
			
			List<TTrnPremiumQuo> tTrnPremiumQuoList = (List<TTrnPremiumQuo>) getHibernateTemplate().find( "from TTrnPremiumQuo prm where prm.id.prmPolicyId= ? and prm.id.prmBasicRskId = ? and prm.id.prmRskId = ? and prm.prmEndtId <= ? and prm.prmStatus <> 4 and prm.prmValidityExpiryDate = ?", sectionVO.getPolicyId(),
					new BigDecimal(locationDetails.getRiskGroupId()),BigDecimal.valueOf( SvcConstants.FID_BASIC_RISK_CODE_FOR_AGGREGATE ),(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),SvcConstants.EXP_DATE);
			if(Utils.isEmpty( tTrnPremiumQuoList )){
				ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, true );
				isToBeCreated = true;
			}
			else {
				ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, false );
				isToBeCreated = false;
			}
				
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			isToBeCreated = ( (Boolean) ThreadLevelContext.get( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED ) ) ? true : false;
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, null );
		}
		return isToBeCreated;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#isToBeDeleted(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO[], com.mindtree.ruc.cmn.base.BaseVO[])
	 */
	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		if( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals( tableId ) ){
			FidelityUnnammedEmployeeVO unNamedEmployee = (FidelityUnnammedEmployeeVO) depsVO[ 3 ];
			if( !Utils.isEmpty( unNamedEmployee.getIsToBeDeleted() ) ){
				if( unNamedEmployee.getIsToBeDeleted() ){
					ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_DELETED, true );
					return true;
				}
			}

		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){
			FidelityNammedEmployeeDetailsVO namedEmployee = (FidelityNammedEmployeeDetailsVO) depsVO[ 3 ];
			if( !Utils.isEmpty( namedEmployee.getIsToBeDeleted() ) ){
				if( namedEmployee.getIsToBeDeleted() ){
					ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_DELETED, true );
					return true;
				}
			}

		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			Boolean isDeleted = (Boolean) ThreadLevelContext.get( com.Constant.CONST_PRM_TO_BE_DELETED );
			ThreadLevelContext.clear( com.Constant.CONST_PRM_TO_BE_DELETED );
			return ( !Utils.isEmpty( isDeleted ) && isDeleted ) ? true : false;

		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE.equals( tableId ) ){
			Boolean isDeleted = (Boolean) ThreadLevelContext.get( com.Constant.CONST_PRM_TO_BE_DELETED );
			ThreadLevelContext.clear( com.Constant.CONST_PRM_TO_BE_DELETED );
			return ( !Utils.isEmpty( isDeleted ) && isDeleted ) ? true : false;
		}
		ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_DELETED, false );
		return false;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#updateKeyValuesToVOs(java.lang.String, com.rsaame.pas.cmn.pojo.POJO, com.rsaame.pas.vo.bus.PolicyVO, com.mindtree.ruc.cmn.base.BaseVO[], com.rsaame.pas.dao.cmn.SaveCase)
	 */
	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){

			FidelityNammedEmployeeDetailsVO namedEmployee = (FidelityNammedEmployeeDetailsVO) depsVO[ 3 ];
			if( !Utils.isEmpty( namedEmployee ) ){
				if( !Utils.isEmpty( mappedRecord ) ){
					if( mappedRecord instanceof TTrnGaccPersonQuo ){

						TTrnGaccPersonQuo gaccPersonQuo = (TTrnGaccPersonQuo) mappedRecord;
						namedEmployee.setGprFidelityId( gaccPersonQuo.getGprBasicRiskId() );
					}
				}
			}

		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals( tableId ) ){

			FidelityUnnammedEmployeeVO unNamedEmployee = (FidelityUnnammedEmployeeVO) depsVO[ 3 ];
			if( !Utils.isEmpty( unNamedEmployee ) ){
				if( !Utils.isEmpty( mappedRecord ) ){
					if( mappedRecord instanceof TTrnGaccUnnamedPersonQuo ){

						TTrnGaccUnnamedPersonQuo gaccUnnamedPersonQuo = (TTrnGaccUnnamedPersonQuo) mappedRecord;
						unNamedEmployee.setGupFidelityId( gaccUnnamedPersonQuo.getGupBasicRiskId() );
					}
				}
			}

		}
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#tableRecPostSaveProcessing(java.lang.String, com.rsaame.pas.cmn.pojo.POJO, com.rsaame.pas.vo.bus.PolicyVO)
	 */
	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#tableRecPostProcessing(java.lang.String, com.rsaame.pas.cmn.pojo.POJO, com.rsaame.pas.vo.bus.PolicyVO)
	 */
	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructCreateRecordId(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO)
	 */
	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){

		POJOId id = null;
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){
			TTrnGaccPersonQuoId personQuoId = new TTrnGaccPersonQuoId();
			personQuoId.setGprId( ( (TTrnGaccPersonQuo) mappedRecord ).getGprBasicRiskId() );
			personQuoId.setGprValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = personQuoId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals( tableId ) ){
			TTrnGaccUnnamedPersonQuoId unnamedPersonQuoId = new TTrnGaccUnnamedPersonQuoId();
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnGaccUnnamedPersonQuo ){
					unnamedPersonQuoId.setGupId( ( (TTrnGaccUnnamedPersonQuo) mappedRecord ).getGupBasicRiskId() );
					unnamedPersonQuoId.setGupValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = unnamedPersonQuoId;
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnPremiumQuo ){

					TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) mappedRecord;
					TTrnPremiumQuoId pId = premiumQuo.getId();
					pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = pId;
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnUwQuestionsQuo ){

					TTrnUwQuestionsQuo questionsQuo = (TTrnUwQuestionsQuo) mappedRecord;
					TTrnUwQuestionsQuoId uId = questionsQuo.getId();
					id = uId;
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnPremiumQuo ){
					TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) mappedRecord;
					TTrnPremiumQuoId pId = premiumQuo.getId();
					pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = pId;
				}
			}
		}
		
		return id;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructChangeRecordId(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJOId)
	 */
	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){

		POJOId id = null;

		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){
			TTrnGaccPersonQuoId existingTId = (TTrnGaccPersonQuoId) existingId;
			TTrnGaccPersonQuoId tid = new TTrnGaccPersonQuoId();
			tid.setGprId( existingTId.getGprId() );
			tid.setGprValidityStartDate( existingTId.getGprValidityStartDate() );
			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_UNNAMED_PERSON.equals( tableId ) ){
			TTrnGaccUnnamedPersonQuoId existingTId = (TTrnGaccUnnamedPersonQuoId) existingId;
			TTrnGaccUnnamedPersonQuoId unnamedPersonQuoId = new TTrnGaccUnnamedPersonQuoId();
			unnamedPersonQuoId.setGupId( existingTId.getGupId() );
			unnamedPersonQuoId.setGupValidityStartDate( existingTId.getGupValidityStartDate() );
			id = unnamedPersonQuoId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuoId pId = new TTrnPremiumQuoId();
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM_AGGREGATE.equals( tableId ) ){
			TTrnPremiumQuoId pId = new TTrnPremiumQuoId();
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructAddtlCoverCntListForCurrRGD(com.rsaame.pas.vo.bus.RiskGroupDetails)
	 */
	@Override
	public List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails currRgd ){
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getBasicRiskIdFromCurrRGD(com.rsaame.pas.vo.bus.RiskGroupDetails)
	 */
	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructPOJOAForPrmTableMapping(com.rsaame.pas.vo.bus.PolicyVO, java.lang.Long)
	 */
	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO loadFidelitySection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveFidelitySection( BaseVO baseVO ){
		return saveSection( baseVO );
	}

	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
	}

	/**
	 * 
	 * @param policyVO
	 * @param locationdetails
	 * @param fidelityDetails
	 * @param fidelityection
	 * @param deps
	 * @param occupancy
	 * @param namedEmployee
	 * @return TTrnGaccPersonQuo pojo
	 * This method is used to map the values to TTrnGaccPersonQuo pojo so that values can be saved in corresponding table.
	 */
	private TTrnGaccPersonQuo getGaccPersonPOJO( PolicyVO policyVO, LocationVO locationdetails, FidelityVO fidelityDetails, SectionVO fidelityection, POJO[] deps,
			TMasOccupancy occupancy, FidelityNammedEmployeeDetailsVO namedEmployee ){

		TTrnBuildingQuo tTrnBuildingQuo = (TTrnBuildingQuo) deps[ 0 ];
		TTrnWctplPremiseQuo trnWctplPremiseQuo = (TTrnWctplPremiseQuo) deps[ 1 ];
		Integer userId = SvcUtils.getUserId( policyVO );
		TTrnGaccPersonQuo tTrnGaccPersonQuo = new TTrnGaccPersonQuo();
		TTrnGaccPersonQuoId gaccPersonQuoId = null;

		if( ( !Utils.isEmpty( namedEmployee.getGprFidelityId() ) && namedEmployee.getGprFidelityId().equals( CommonConstants.DEFAULT_LOW_LONG ) )
				|| Utils.isEmpty( namedEmployee.getGprFidelityId() ) ){
			namedEmployee.setGprFidelityId( null );
			gaccPersonQuoId = new TTrnGaccPersonQuoId();
			gaccPersonQuoId.setGprId( 0L );
			tTrnGaccPersonQuo.setId( gaccPersonQuoId );
			Long cntSequence = NextSequenceValue.getNextSequence( GACC_PERSON_SEQ, null,null, getHibernateTemplate() );
			tTrnGaccPersonQuo.setGprBasicRiskId( cntSequence );
		}
		else{
			tTrnGaccPersonQuo.setGprBasicRiskId( namedEmployee.getGprFidelityId() );
		}

		tTrnGaccPersonQuo.setGprAAddress1( null );
		tTrnGaccPersonQuo.setGprAAddress2( null );
		tTrnGaccPersonQuo.setGprAAddress3( null );

		tTrnGaccPersonQuo.setGprAgrLmt(new BigDecimal(fidelityDetails.getAggregateLimit() ) );
		tTrnGaccPersonQuo.setGprAName( namedEmployee.getEmpDesignation() );
		tTrnGaccPersonQuo.setGprBasicRskCode( FIDELITY_RISK_CODE );

		if( !Utils.isEmpty( tTrnBuildingQuo ) ){
			tTrnGaccPersonQuo.setGprBldId( tTrnBuildingQuo.getId().getBldId() );
		}
		else if( !Utils.isEmpty( trnWctplPremiseQuo ) ){
			tTrnGaccPersonQuo.setGprBldId( trnWctplPremiseQuo.getId().getWbdId() );
		}

		tTrnGaccPersonQuo.setGprDescription( null );
		tTrnGaccPersonQuo.setGprEAddress1( null );
		tTrnGaccPersonQuo.setGprEAddress2( null );
		tTrnGaccPersonQuo.setGprEAddress3( null );
		tTrnGaccPersonQuo.setGprEmail( null );

		tTrnGaccPersonQuo.setGprEName( namedEmployee.getEmpName() );
		tTrnGaccPersonQuo.setGprEndDate( policyVO.getEndDate() );
		tTrnGaccPersonQuo.setGprEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		tTrnGaccPersonQuo.setGprFax( null );
		tTrnGaccPersonQuo.setGprGsm( null );
		tTrnGaccPersonQuo.setGprModifiedBy( userId );
		tTrnGaccPersonQuo.setGprModifiedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		tTrnGaccPersonQuo.setGprOcCode( occupancy.getOcpCode() );
		tTrnGaccPersonQuo.setGprPersonId( null );
		tTrnGaccPersonQuo.setGprPolicyId( fidelityection.getPolicyId() );
		tTrnGaccPersonQuo.setGprPreparedBy( userId );
		tTrnGaccPersonQuo.setGprPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		tTrnGaccPersonQuo.setGprRcCode( FIDELITY_RC_CODE.longValue() );
		tTrnGaccPersonQuo.setGprRelation( null );
		tTrnGaccPersonQuo.setGprRetroactiveDate( null );
		tTrnGaccPersonQuo.setGprRiRskCode( FIDELITY_RI_RISK_CODE );
		tTrnGaccPersonQuo.setGprRskCode( FIDELITY_RISK_CODE.longValue() );

		tTrnGaccPersonQuo.setGprRtCode( namedEmployee.getEmpType().longValue() );

		tTrnGaccPersonQuo.setGprSalary( null );
		tTrnGaccPersonQuo.setGprStartDate( policyVO.getScheme().getEffDate() );
		tTrnGaccPersonQuo.setGprStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
		tTrnGaccPersonQuo.setGprSumInsured( new BigDecimal( namedEmployee.getLimitPerPerson() ) );
		tTrnGaccPersonQuo.setGprTelephone( null );
		tTrnGaccPersonQuo.setGprTradeGroup( occupancy.getOcpTradeCode().longValue() );
		tTrnGaccPersonQuo.setGprValidityExpiryDate( SvcConstants.EXP_DATE );
		tTrnGaccPersonQuo.setGprZip( null );
		return tTrnGaccPersonQuo;
	}

	/**
	 * 
	 * @param policyVO
	 * @param locationDetails
	 * @param fidelityDetails
	 * @param fidelitySection
	 * @param deps
	 * @param occupancy
	 * @param unnamedEmployeeDetails
	 * @return
	 * 
	 * This method is used to map the values to TTrnGaccUnnamesPersonQuo pojo so that values can be saved in corresponding table.
	 */
	private TTrnGaccUnnamedPersonQuo getGaccUnnamedPersonPOJO( PolicyVO policyVO, LocationVO locationDetails, FidelityVO fidelityDetails, SectionVO fidelitySection, POJO[] deps,
			TMasOccupancy occupancy, FidelityUnnammedEmployeeVO unnamedEmployee ){

		TTrnBuildingQuo tTrnBuildingQuo = (TTrnBuildingQuo) deps[ 0 ];
		TTrnWctplPremiseQuo trnWctplPremiseQuo = (TTrnWctplPremiseQuo) deps[ 1 ];
		Integer userId = SvcUtils.getUserId( policyVO );
		TTrnGaccUnnamedPersonQuo tTrnGaccUnnamedPersonQuo = new TTrnGaccUnnamedPersonQuo();

		TTrnGaccUnnamedPersonQuoId gaccUnnamedPersonQuoId = null;

		if( ( !Utils.isEmpty( unnamedEmployee.getGupFidelityId() ) && unnamedEmployee.getGupFidelityId().equals( CommonConstants.DEFAULT_LOW_LONG ) )
				|| Utils.isEmpty( unnamedEmployee.getGupFidelityId() ) ){
			unnamedEmployee.setGupFidelityId( null );
			gaccUnnamedPersonQuoId = new TTrnGaccUnnamedPersonQuoId();
			gaccUnnamedPersonQuoId.setGupId( 0 );
			tTrnGaccUnnamedPersonQuo.setId( gaccUnnamedPersonQuoId );
			Long cntSequence = NextSequenceValue.getNextSequence( GACC_UNNAMED_PERSON_SEQ,null,null, getHibernateTemplate() );
			tTrnGaccUnnamedPersonQuo.setGupBasicRiskId( cntSequence );
		}
		else{
			tTrnGaccUnnamedPersonQuo.setGupBasicRiskId( unnamedEmployee.getGupFidelityId() );
		}

		tTrnGaccUnnamedPersonQuo.setGupAgrLmt(new BigDecimal( fidelityDetails.getAggregateLimit() ) );

		tTrnGaccUnnamedPersonQuo.setGupBasicRskCode( FIDELITY_RISK_CODE );

		if( !Utils.isEmpty( tTrnBuildingQuo ) ){
			tTrnGaccUnnamedPersonQuo.setGupBldId( tTrnBuildingQuo.getId().getBldId() );
		}
		else if( !Utils.isEmpty( trnWctplPremiseQuo ) ){
			tTrnGaccUnnamedPersonQuo.setGupBldId( trnWctplPremiseQuo.getId().getWbdId() );
		}
		tTrnGaccUnnamedPersonQuo.setGupEndDate( policyVO.getEndDate() );
		tTrnGaccUnnamedPersonQuo.setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );

		tTrnGaccUnnamedPersonQuo.setGupModifiedBy( userId );
		tTrnGaccUnnamedPersonQuo.setGupModifiedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		tTrnGaccUnnamedPersonQuo.setGupOcCode( Integer.parseInt( Short.toString( occupancy.getOcpCode() ) ) );

		tTrnGaccUnnamedPersonQuo.setGupPolicyId( fidelitySection.getPolicyId() );
		tTrnGaccUnnamedPersonQuo.setGupPreparedBy( userId );
		tTrnGaccUnnamedPersonQuo.setGupPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		tTrnGaccUnnamedPersonQuo.setGupRcCode( FIDELITY_RC_CODE );

		tTrnGaccUnnamedPersonQuo.setGupRiRskCode( FIDELITY_RI_RISK_CODE );
		tTrnGaccUnnamedPersonQuo.setGupRskCode( FIDELITY_RISK_CODE );
		tTrnGaccUnnamedPersonQuo.setGupOcCode( unnamedEmployee.getEmpType() );
		tTrnGaccUnnamedPersonQuo.setGupNoOfPerson( unnamedEmployee.getTotalNumberOfEmployee() );
		tTrnGaccUnnamedPersonQuo.setGupSumInsured(new BigDecimal(unnamedEmployee.getLimitPerPerson() ) );
		//tTrnGaccPersonQuo.setGprRtCode( TB_RT_CODE.longValue() );

		tTrnGaccUnnamedPersonQuo.setGupStartDate( policyVO.getScheme().getEffDate() );
		tTrnGaccUnnamedPersonQuo.setGupStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );

		tTrnGaccUnnamedPersonQuo.setValidityExpiryDate( SvcConstants.EXP_DATE );
		return tTrnGaccUnnamedPersonQuo;

	}

	/**
	 * 
	 * @param depsVO
	 * @param policyDetails
	 * @param occupancy
	 * @return premiumVO
	 * 
	 * This method is to map the values to TTrnPremiumQuo pojo so that values can be saved in corresponding table.
	 */
	private TTrnPremiumQuo getPremiumPojo( BaseVO[] depsVO, PolicyVO policyDetails, TMasOccupancy occupancy ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();

		//LocationVO locationDetails = (LocationVO) depsVO[ 0 ];
		FidelityVO fidelityDetails = (FidelityVO) depsVO[ 1 ];
		SectionVO fidelitySection = (SectionVO) depsVO[ 2 ];
		FidelityNammedEmployeeDetailsVO namedEmployee = (FidelityNammedEmployeeDetailsVO) depsVO[ 3 ];
		FidelityUnnammedEmployeeVO unnamedEmployee = (FidelityUnnammedEmployeeVO) depsVO[ 4 ];

		premiumQuoId.setPrmBasicRskCode( FIDELITY_RISK_CODE );

		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );
		if( !Utils.isEmpty( namedEmployee ) ){
			premiumQuoId.setPrmRskId( BigDecimal.valueOf( namedEmployee.getGprFidelityId() ) );
		}
		else{
			premiumQuoId.setPrmRskId( BigDecimal.valueOf( unnamedEmployee.getGupFidelityId() ) );
		}

		premiumQuoId.setPrmRskCode( FIDELITY_RISK_CODE );
		/* TODO */
		premiumQuoId.setPrmCovCode( Short.valueOf( FIDELITY_COV_CODE.toString() ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( FIDELITY_CST_CODE.toString() ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( FIDELITY_CT_CODE.toString() ) );
		premiumQuoId.setPrmPolicyId( fidelitySection.getPolicyId() );
		premiumQuoId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( "VSD" ) );
		premiumQuo.setId( premiumQuoId );
		premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		premiumQuo.setPrmClCode( Short.valueOf( FIDELITY_CLASS_CODE.toString() ) );
		premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		premiumQuo.setPrmRcCode( FIDELITY_RC_CODE );
		premiumQuo.setPrmRscCode( FIDELITY_RSC_CODE );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmRiRskCode( occupancy.getOcpRiRskCode() );
		premiumQuo.setPrmRtCode( occupancy.getOcpRtCode() );

		if( !Utils.isEmpty( namedEmployee ) ){

			premiumQuo.setPrmSumInsured(new BigDecimal( namedEmployee.getLimitPerPerson() ) );
			premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( fidelityDetails.getDeductible() ) );
		}
		else if( !Utils.isEmpty( unnamedEmployee ) ){

			premiumQuo.setPrmSumInsured( new BigDecimal(  unnamedEmployee.getLimitPerPerson() ) );
			premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( fidelityDetails.getDeductible() ) );

		}

		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );

		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );

		if( !Utils.isEmpty( namedEmployee ) ){
			if( !Utils.isEmpty( namedEmployee.getPremium() ) ){
				if( !Utils.isEmpty( namedEmployee.getPremium().getPremiumAmt() ) ){
					premiumQuo.setPrmPremium( new BigDecimal( String.valueOf(namedEmployee.getPremium().getPremiumAmt() ) ));
					premiumQuo.setPrmPremiumActual( new BigDecimal( String.valueOf(namedEmployee.getPremium().getPremiumAmt() ) ));
				}else{
					setZeroPrmValue(premiumQuo);
			}
		}else{
					setZeroPrmValue(premiumQuo);
		}
		}
		else if( !Utils.isEmpty( unnamedEmployee ) ){
			if( !Utils.isEmpty( unnamedEmployee.getPremium() ) ){
				if( !Utils.isEmpty( unnamedEmployee.getPremium().getPremiumAmt() ) ){
					premiumQuo.setPrmPremium( new BigDecimal(String.valueOf( unnamedEmployee.getPremium().getPremiumAmt() ) ));
					premiumQuo.setPrmPremiumActual( new BigDecimal( String.valueOf(unnamedEmployee.getPremium().getPremiumAmt() ) ));
				}else{
					setZeroPrmValue(premiumQuo);
			}
		}else{
					setZeroPrmValue(premiumQuo);
		}
		}
		else{
			setZeroPrmValue( premiumQuo );
		}

		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );

		return premiumQuo;
	}

	
	/**
	 * 
	 * @param depsVO
	 * @param policyDetails
	 * @param occupancy
	 * @return premiumVO
	 * 
	 * This method is to map the values to TTrnPremiumQuo pojo so that values can be saved in corresponding table.
	 */
	private TTrnPremiumQuo getAggregatePremiumPojo( BaseVO[] depsVO, PolicyVO policyDetails, TMasOccupancy occupancy ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();

		//LocationVO locationDetails = (LocationVO) depsVO[ 0 ];
		FidelityVO fidelityDetails = (FidelityVO) depsVO[ 1 ];
		SectionVO fidelitySection = (SectionVO) depsVO[ 2 ];
		
		premiumQuoId.setPrmBasicRskCode( FIDELITY_RISK_CODE );

		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );
		premiumQuoId.setPrmRskId( BigDecimal.valueOf( SvcConstants.FID_BASIC_RISK_CODE_FOR_AGGREGATE ) );
		
		premiumQuoId.setPrmRskCode( FIDELITY_RISK_CODE );
		/* TODO */
		premiumQuoId.setPrmCovCode( Short.valueOf( FIDELITY_COV_CODE.toString() ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( FIDELITY_CST_CODE.toString() ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( FIDELITY_CT_CODE.toString() ) );
		premiumQuoId.setPrmPolicyId( fidelitySection.getPolicyId() );
		premiumQuoId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( "VSD" ) );
		premiumQuo.setId( premiumQuoId );
		premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		premiumQuo.setPrmClCode( Short.valueOf( FIDELITY_CLASS_CODE.toString() ) );
		premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		premiumQuo.setPrmRcCode( FIDELITY_RC_CODE );
		premiumQuo.setPrmRscCode( FIDELITY_RSC_CODE );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmRiRskCode( occupancy.getOcpRiRskCode() );
		premiumQuo.setPrmRtCode( occupancy.getOcpRtCode() );

		premiumQuo.setPrmSumInsured( new BigDecimal(fidelityDetails.getAggregateLimit()));
		
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );

		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		if(!Utils.isEmpty(fidelityDetails.getFidAggregateBasePremium())){
		premiumQuo.setPrmPremium( new BigDecimal(String.valueOf(fidelityDetails.getFidAggregateBasePremium())));
		premiumQuo.setPrmPremiumActual( new BigDecimal( String.valueOf(fidelityDetails.getFidAggregateBasePremium())));
		}else{
			setZeroPrmValue( premiumQuo );
		}

		return premiumQuo;
	}
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		SectionVO fidelitySection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_FIDELITY );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( fidelitySection );
		FidelityVO fidelityDetails = (FidelityVO) PolicyUtils.getRiskGroupDetails( locationDetails, fidelitySection );
		removeDeletedRowsFromContext( fidelityDetails );
		updateSectionLevelSIAndPremium( fidelityDetails );
		updateEndtText( policyVO );
		super.sectionPostProcessing( policyVO );
	}

	private void updateSectionLevelSIAndPremium( FidelityVO fidelityDetails ){
		fidelityDetails.setSumInsured( getSectionLevelSumInsured( fidelityDetails ) );
		getSectionLevelPremium( fidelityDetails );

	}

	/*
	 * This method is used to update the Travel level SumInsured and Premium Amount
	 * @param PolicyVO
	 * 
	 */
	private void getSectionLevelPremium( FidelityVO fidelityDetails ){
		if( Utils.isEmpty( fidelityDetails.getPremium() ) ){
			fidelityDetails.setPremium( new PremiumVO() );
		}
		PremiumHelper.getSectionLevelPremium( fidelityDetails );
	}

	private double getSectionLevelSumInsured( FidelityVO fidelityDetails ){
		return PremiumHelper.getSectionLevelSumInsured( fidelityDetails );

	}

	@Override
	protected void sectionPreProcessing( PolicyVO policyVO ){
		// TODO Auto-generated method stub
		super.sectionPostProcessing( policyVO );
	}

	/* This method is used to remove the delted rows from context
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/
	protected void removeDeletedRowsFromContext( FidelityVO fidelityDetails ){
		try{
			if( Utils.isEmpty( fidelityDetails.getIsToBeDeleted() ) ) return;

			boolean deletionflagNamed = false;
			boolean deletionflagUnnamed = false;
			ArrayList<FidelityNammedEmployeeDetailsVO> toBeDeletedVOs = new ArrayList<FidelityNammedEmployeeDetailsVO>();
			ArrayList<FidelityUnnammedEmployeeVO> toBeDeletedVOs1 = new ArrayList<FidelityUnnammedEmployeeVO>();

			for( FidelityNammedEmployeeDetailsVO nammedEmpVO : fidelityDetails.getFidelityEmployeeDetails() ){
				if( !Utils.isEmpty( nammedEmpVO.getIsToBeDeleted() ) && nammedEmpVO.getIsToBeDeleted() ){
					toBeDeletedVOs.add( nammedEmpVO );
					deletionflagNamed = true;
				}
			}
			for( FidelityUnnammedEmployeeVO unnammedEmpVO : fidelityDetails.getUnnammedEmployeeDetails() ){
				if( !Utils.isEmpty( unnammedEmpVO.getIsToBeDeleted() ) && unnammedEmpVO.getIsToBeDeleted() ){
					toBeDeletedVOs1.add( unnammedEmpVO );
					deletionflagUnnamed = true;
				}
			}
			if( deletionflagNamed ){
				for( FidelityNammedEmployeeDetailsVO toBeDeletedVO : toBeDeletedVOs ){

					fidelityDetails.getFidelityEmployeeDetails().remove( toBeDeletedVO );
				}
			}
			if( deletionflagUnnamed ){
				for( FidelityUnnammedEmployeeVO toBeDeletedVO1 : toBeDeletedVOs1 ){

					fidelityDetails.getUnnammedEmployeeDetails().remove( toBeDeletedVO1 );
				}

			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}

	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			SectionVO fidelitySection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( fidelitySection );
			FidelityVO fidelityDetails = (FidelityVO) PolicyUtils.getRiskGroupDetails( locationDetails, fidelitySection );

			DAOUtils.deletePrevEndtText(fidelitySection.getPolicyId(), (Long) ThreadLevelContext.get(SvcConstants.TLC_KEY_ENDT_ID),SvcConstants.SECTION_ID_FIDELITY, Long.valueOf(locationDetails.getRiskGroupId()));
			
			LOGGER.debug("call pro_endt_text_gacc_unper_add");
			DAOUtils.addGPAUnnamedforendorsementFlow(fidelitySection.getPolicyId(), policyVO, SvcConstants.SECTION_ID_FIDELITY, Long.valueOf(locationDetails.getRiskGroupId()));
		
			LOGGER.debug("call pro_endt_text_gacc_per_add");
			DAOUtils.addGPANamedforendorsementFlow(fidelitySection.getPolicyId(), policyVO, SvcConstants.SECTION_ID_FIDELITY, Long.valueOf(locationDetails.getRiskGroupId()));
		
			LOGGER.debug("call pro_endt_text_gacc_unper_del");
			DAOUtils.deleteGPAUnnamedforendorsementFlow(fidelitySection.getPolicyId(), policyVO, SvcConstants.SECTION_ID_FIDELITY, Long.valueOf(locationDetails.getRiskGroupId()));
		
			LOGGER.debug("call pro_endt_text_gacc_per_del");
			DAOUtils.deleteGPANamedforendorsementFlow(fidelitySection.getPolicyId(), policyVO, SvcConstants.SECTION_ID_FIDELITY, Long.valueOf(locationDetails.getRiskGroupId()));
		
			LOGGER.debug("call pro_endt_text_gacc_unper_mod");
			for(FidelityUnnammedEmployeeVO fidUnnammedEmpVO : fidelityDetails.getUnnammedEmployeeDetails()){
				if(!Utils.isEmpty(fidUnnammedEmpVO.getGupFidelityId())){
					DAOUtils.updateGPAUnnamedforendorsementFlow(fidelitySection.getPolicyId(), policyVO, Long.valueOf(locationDetails.getRiskGroupId()), fidelityDetails.getBasicRiskId(), SvcConstants.SECTION_ID_FIDELITY, Long.valueOf(fidUnnammedEmpVO.getGupFidelityId()));
				}
			}
			
			LOGGER.debug("call pro_endt_text_gacc_per_mod ");
			for(FidelityNammedEmployeeDetailsVO fidNammedEmpVO : fidelityDetails.getFidelityEmployeeDetails()){
				if(!Utils.isEmpty(fidNammedEmpVO.getGprFidelityId())){
			DAOUtils.updateGPANamedforendorsementFlow(fidelitySection.getPolicyId(), policyVO, Long.valueOf(locationDetails.getRiskGroupId()), fidelityDetails.getBasicRiskId(), SvcConstants.SECTION_ID_FIDELITY, Long.valueOf(fidNammedEmpVO.getGprFidelityId()));
				}
			}
		
			LOGGER.debug( "call UW changes change endo SP" );
			DAOUtils.updateUWQuestions( fidelitySection.getPolicyId(), policyVO,fidelitySection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()) );
			
			//LOGGER.debug("call Risk Add changes change endo SP");
			//DAOUtils.updateEndTextForRiskAdd(fidelitySection.getPolicyId(), policyVO, fidelitySection.getSectionId());
			
			DAOUtils.updateDeductibleforendorsementFlow( fidelitySection.getPolicyId(), policyVO,fidelitySection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId()) );
						
			DAOUtils.updateTotalSITextforendorsementFlow( fidelitySection.getPolicyId(), policyVO,fidelitySection.getSectionId(), Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );
			

		}
	}

}
