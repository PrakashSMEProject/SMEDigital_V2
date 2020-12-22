package com.rsaame.pas.mb.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
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
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnContentQuoId;
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
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBUWDetailsVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * Saves the MB Section
 * @author m1014438
 *
 */

public class MBDAO extends BaseSectionSaveDAO implements IMBSectionDAO{

	private final static String CONTENT_SEQ = "SEQ_CONTENTS_ID";
	private static final String MB_COVER_TYPE = "MB_COVER_TYPE";
	private static final String MB_COVER_SUB_TYPE = "MB_COVER_SUB_TYPE";
	private static final String MB_COVER = "MB_COVER";
	private static final String MB_CONTENT_RISK = "MB_CONTENT_RISK";
	private static final String DEFAULT_CURRENCY = "DEFAULT_CURRENCY";
	private static final String SBS_POLICY_TYPE = "SBS_Policy_Type";
	private static final String SYSDATE = "SYSDATE";
	private static final String MB_RISK_TYPE = "MB_RISK_TYPE";
	private static final String MB_BASIC_RISK_CODE = "MB_BASIC_RISK_CODE";

	@Override
	protected int getSectionId(){
		return SvcConstants.SECTION_ID_MB;
	}

	@Override
	protected int getClassCode(){
		return SvcConstants.CLASS_ID_MB;
	}

	/**
	 * This method does all save related operations for MB section.
	 */
	@Override
	protected BaseVO saveSection( BaseVO input ){
		PolicyVO policyVO = (PolicyVO) input;

		/* Let us get the system date right now and use from here on for this transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
		//parSection.setPolicyId( policyVO.getPolicyNo() );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( mbSection );
		MBVO mbDetails = (MBVO) PolicyUtils.getRiskGroupDetails( locationDetails, mbSection );

		/* Handle the machine content. Premium update for the contents will be handled inside
		 * this method. */

		POJO buildingOrPremise = handleContent( policyVO, mbSection, locationDetails, mbDetails );

		/* Handle the UW questions. */
		handleUWQ( policyVO, mbSection, locationDetails, mbDetails, buildingOrPremise );

		return policyVO;
	}

	private void handleUWQ( PolicyVO policyVO, SectionVO mbSection, LocationVO locationDetails, MBVO mbDetails, POJO buildingOrPremise ){

		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else if( buildingOrPremise instanceof TTrnWctplPremiseQuo ){
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;
		}
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );

		POJO[] uwqDeps = { buildingQuo };
		if( Utils.isEmpty( buildingQuo ) ) uwqDeps[ 0 ] = premiseQuo;
		UWQuestionsVO questionsVOs = mbDetails.getUwQuestions();
		List<UWQuestionVO> questions = questionsVOs.getQuestions();

		for( UWQuestionVO question : questions ){
			BaseVO[] uwqDepsVO = { locationDetails, mbDetails, question };
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE, policyVO, uwqDeps, uwqDepsVO, false, mbSection.getPolicyId(), question.getQId(),
					( Long.valueOf( rg.getRiskGroupId() ) ), Utils.isEmpty( question.getResponse() ) ? null : question.getResponse() );
		}
	}

	/**
	 * This method iterates over the MachineDetailsVO and calls the handleTableRecord
	 * method for the further saving process.
	 * 
	 * @param policyVO
	 * @param mbSection
	 * @param locationDetails
	 * @param mbDetails
	 * @return
	 */
	private POJO handleContent( PolicyVO policyVO, SectionVO mbSection, LocationVO locationDetails, MBVO mbDetails ){

		/* (a) Get the building record if PAR is basic section or Premise record in case of PL being a basic section
		 * 
		 * (b) Handle T_TRN_CONTENT(_QUO) record.
		 * 		(i)  For this we need T_TRN_BUILDING record or T_TRN_WCTPL_PREMISE record , based on PAR or PL . 
		 * (c) Handle the corresponding T_TRN_PREMIUM(_QUO) record. */

		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		POJO buildingOrPremise = getBuildingOrPremiseRecord( policyVO, mbSection, locationDetails, mbDetails );
		TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );
		if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;

		POJO[] contentDeps = { buildingQuo, occupancy };

		Long buildingId = null;
		if( !Utils.isEmpty( buildingQuo ) && !Utils.isEmpty( buildingQuo.getId() ) ) buildingId = buildingQuo.getId().getBldId();
		if( Utils.isEmpty( buildingQuo ) ){
			contentDeps[ 0 ] = premiseQuo;
			if( !Utils.isEmpty( premiseQuo ) && !Utils.isEmpty( premiseQuo.getId() ) ) buildingId = premiseQuo.getId().getWbdId();
		}

		if( !Utils.isEmpty( mbDetails ) && !Utils.isEmpty( mbDetails.getMachineryDetails() ) ){
			java.util.List<MachineDetailsVO> machineryDetails = mbDetails.getMachineryDetails();

			for( MachineDetailsVO machineDetails : machineryDetails ){

				Contents content = machineDetails.getContents();
				if( !Utils.isEmpty( content.getCoverCode() ) && content.getCoverCode().intValue() != SvcConstants.APP_BASE_COVER_CODE ) continue;

				if( !Utils.isEmpty( content.getCoverId() ) && content.getCoverId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
					content.setCoverId( null );
				}
				if( Utils.isEmpty( content.getCoverId() ) && content.getCover().doubleValue() == 0.0 ){
					continue;
				}
				BaseVO[] contentDepsVO = { machineDetails };

				//Renewal Multiple Id's handling changes, added policy in the query parameter
				TTrnContentQuo contentQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_CONTENT, policyVO, contentDeps, contentDepsVO, false, mbSection.getPolicyId(),content.getCoverId(), buildingId );
				POJO[] premiumDep = { buildingQuo, contentQuo, occupancy };
				if( !Utils.isEmpty( buildingQuo ) && !Utils.isEmpty( buildingQuo.getId() ) )
					ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, buildingQuo.getId().getBldId() );
				if( Utils.isEmpty( buildingQuo ) ){
					premiumDep[ 0 ] = premiseQuo;
					ThreadLevelContext.clear( SvcConstants.TLC_KEY_BASIC_RISK_ID );
					ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, premiseQuo.getId().getWbdId() );
				}
				Contents NIL_CONTENT_FOR_CNTS = null;
				BaseVO[] contentDepVO = { NIL_CONTENT_FOR_CNTS, machineDetails, machineDetails.getPremium() };

				TTrnPremiumQuo premium = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, premiumDep, contentDepVO, false, mbSection.getPolicyId(),
						BigDecimal.valueOf( contentQuo.getId().getCntContentId() ), BigDecimal.valueOf( buildingId ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE )
								.shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_TYPE ) ), Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_SUB_TYPE ) ) );

				/*This null check is basically added to avoid scenarios where in rating engine is not configured with premium for 
				* particular content.
				* For ex - In case of prepackage tariff, premium will be flat premium hence content wise premium will be NULL */
				if( !Utils.isEmpty( machineDetails.getPremium() ) && !Utils.isEmpty( premium.getPrmPremium() ) ){
					machineDetails.getPremium().setPremiumAmt( premium.getPrmPremium().doubleValue() );
				}
			}
		}
		return buildingOrPremise;
	}

	/**
	 *  This method decides which is the basic section among PAR and PL to
	 *  construct T_TRN_CONTENT_QUO
	 * @param policyVO
	 * @param mbSection
	 * @param locationDetails
	 * @param mbDetails
	 * @return
	 */
	private POJO getBuildingOrPremiseRecord( PolicyVO policyVO, SectionVO mbSection, LocationVO locationDetails, MBVO mbDetails ){
		/* Check if PAR or PL is present, basicSectionID will contain the section id of either PAR or PL */
		Integer basicSectionID = null;
		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		POJO buildingOrPremise = null;
		if( SvcUtils.isSectionPresent( SvcConstants.SECTION_ID_PAR, policyVO ) ){
			basicSectionID = SvcConstants.SECTION_ID_PAR;
		}
		else if( SvcUtils.isSectionPresent( SvcConstants.SECTION_ID_PL, policyVO ) ){
			basicSectionID = SvcConstants.SECTION_ID_PL;
		}
		else{
			throw new BusinessException( "pas.basicSection.mandatory", null, "Either Par or Pl has to be selected to proceed further" );
		}

		SectionVO basicSection = PolicyUtils.getSectionVO( policyVO, basicSectionID );

		/* If PAR is the basic section, then we have to get the PAR building record and use it for the 
		 * construction of the T_TRN_GACC_BUILDING record POJO. If PL is the basic section, then we 
		 * have to use the WCTPL Premise record for this. */
		if( !Utils.isEmpty( basicSection ) && ( basicSectionID.equals( SvcConstants.SECTION_ID_PAR ) ) ){
			if( !Utils.isEmpty( locationDetails.getRiskGroupId() ) ){
				try{
					buildingQuo = (TTrnBuildingQuo) getHibernateTemplate().find( "from TTrnBuildingQuo buldQ where buldQ.id.bldId=?",
							Long.valueOf( locationDetails.getRiskGroupId() ) ).get( 0 );
					buildingOrPremise = buildingQuo;
				}
				catch( Exception e ){
					throw new BusinessException( "pas.basicSection.IDMandatory", e, "ID from of the basic section is mandatory, no data in bld table" );
				}

			}
			if( Utils.isEmpty( buildingQuo ) ){
				throw new BusinessException( "pas.basicSection.IDMandatory", null, "ID from of the basic section is mandatory" );
			}
		}
		else if( !Utils.isEmpty( basicSection ) && basicSectionID.equals( SvcConstants.SECTION_ID_PL ) ){
			PublicLiabilityVO plDetails = (PublicLiabilityVO) basicSection.getRiskGroupDetails().get( locationDetails );
			if( !Utils.isEmpty( plDetails ) ){
				// this pojo may not be required, since the id required in case of par is not selected will be available in publicLiabilityVO
				try{
					premiseQuo = (TTrnWctplPremiseQuo) getHibernateTemplate().find( "from TTrnWctplPremiseQuo preQ where preQ.id.wbdId=?",
							Long.valueOf( locationDetails.getRiskGroupId() ) ).get( 0 ); /* LocationVO.RiskGroupId will be same as wbdId as PL is the basic section */
					buildingOrPremise = premiseQuo;
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
		return buildingOrPremise;
	}

	/**
	 * This methods populates corresponding POJO and returns the same.
	 */
	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;

		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			Contents content = (Contents) depsVO[ 0 ];
			MachineDetailsVO machineDetails = (MachineDetailsVO) depsVO[ 1 ];
			TTrnPremiumQuo premiumQuo = getPremiumPojo( policyVO, deps[ 0 ], (TTrnContentQuo) deps[ 1 ], content, machineDetails, (TMasOccupancy) deps[ 2 ] );
			mappedPOJO = premiumQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){

			TTrnContentQuo contentQuo = getContentPojo( deps[ 0 ], policyVO, (MachineDetailsVO) depsVO[ 0 ], deps[ 1 ] );
			mappedPOJO = contentQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){

			UWQuestionVO question = (UWQuestionVO) depsVO[ 2 ];

			TTrnUwQuestionsQuo questionsQuo = getUWAPojo( question, policyVO, deps[ 0 ] );
			mappedPOJO = questionsQuo;
		}
		return mappedPOJO;

	}

	/**
	 * This method populates the TTrnPremiumQuo (T_TRN_PREMIUM_QUO) with all
	 * required details like sum insured , deductible and so for the
	 * given Machinery
	 * @param policyVO
	 * @param pojo
	 * @param tTrnContentQuo
	 * @param content
	 * @param machineDetails
	 * @param occupancy
	 * @return
	 */
	private TTrnPremiumQuo getPremiumPojo( PolicyVO policyVO, POJO pojo, TTrnContentQuo tTrnContentQuo, Contents content, MachineDetailsVO machineDetails, TMasOccupancy occupancy ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		TTrnBuildingQuo tTrnBuildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		if( pojo instanceof TTrnBuildingQuo )
			tTrnBuildingQuo = (TTrnBuildingQuo) pojo;
		else if( pojo instanceof TTrnWctplPremiseQuo ){
			premiseQuo = (TTrnWctplPremiseQuo) pojo;
		}
		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( MB_BASIC_RISK_CODE ) ) );
		//premiumQuoId.setPrmBasicRskId( new BigDecimal( trnBuildingQuo.getId().getBldId() ) );
		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );

		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
		premiumQuoId.setPrmPolicyId( mbSection.getPolicyId() );

		/* All the contents will now be associated with cover code, cover type, cover sub type , risk type , 
		 * risk category and risk sub category when rendered on the UI */
		if( Utils.isEmpty( content ) ){
			premiumQuoId.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER ) ) );
			premiumQuoId.setPrmCtCode( Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_TYPE ) ) );
			premiumQuoId.setPrmCstCode( Short.valueOf( Utils.getSingleValueAppConfig( MB_COVER_SUB_TYPE ) ) );
		}
		else{
			premiumQuoId.setPrmCovCode( content.getCoverCode().shortValue() );
			premiumQuoId.setPrmCtCode( content.getCoverType().shortValue() );
			premiumQuoId.setPrmCstCode( content.getCoverSubType().shortValue() );
		}

		/* Case of insert for Contents (BASE_COVER) */
		if( !Utils.isEmpty( tTrnContentQuo ) ){
			premiumQuoId.setPrmRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( MB_CONTENT_RISK ) ) );
			premiumQuoId.setPrmRskId( new BigDecimal( tTrnContentQuo.getId().getCntContentId() ) );
			premiumQuoId.setPrmValidityStartDate( tTrnContentQuo.getId().getCntValidityStartDate() );
			premiumQuo.setPrmSumInsured( tTrnContentQuo.getCntSumInsured() );
			premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( machineDetails.getSumInsuredVO().getDeductible() ) );
			premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
			premiumQuo.setPrmRtCode( tTrnContentQuo.getCntCategory() );

			/*premiumQuo.setPrmRcCode( occupancy.getOcpRskCode() );
			premiumQuo.setPrmRscCode( occupancy.getOcpRtCode() );*/

		}

		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyVO, (Date) ThreadLevelContext.get( SYSDATE ) );

		/*
		 * update premium value to PremiumPOJO
		 */
		//setPremiumAmtToPrmPOJO( policyVO, premium, premiumQuo, content, prd);

		premiumQuo.setPrmClCode( new Short( String.valueOf( SvcConstants.CLASS_ID_MB ) ) );
		premiumQuo.setId( premiumQuoId );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		if( !Utils.isEmpty( tTrnBuildingQuo ) )
			premiumQuo.setPrmRiRskCode( tTrnBuildingQuo.getBldRiRskCode() );
		else
			premiumQuo.setPrmRiRskCode( premiseQuo.getWbdRiRskCode() );
		premiumQuo.setPrmStatus( (byte) 1 ); // for new quote	
		premiumQuo.setPrmSumInsuredCurr( Byte.valueOf( Utils.getSingleValueAppConfig( DEFAULT_CURRENCY ) ) );
		premiumQuo.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( DEFAULT_CURRENCY ) ) );
		premiumQuo.setPrmRiLocCode( 99 );
		premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( SBS_POLICY_TYPE ) ) );
		premiumQuo.setPrmEffectiveDate( policyVO.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyVO.getEndDate() );
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmCompulsoryExcess( new BigDecimal( machineDetails.getSumInsuredVO().getDeductible() ) );
		setRateTypeToPremiumPOJO( policyVO, premiumQuo );

		if( !Utils.isEmpty( machineDetails.getPremium() ) ){
			if( !Utils.isEmpty( machineDetails.getPremium().getPremiumAmt() ) ){
				premiumQuo.setPrmPremium( new BigDecimal(String.valueOf(machineDetails.getPremium().getPremiumAmt() ) ));
				premiumQuo.setPrmPremiumActual( new BigDecimal(String.valueOf( machineDetails.getPremium().getPremiumAmt() ) ));
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		else{
			setZeroPrmValue( premiumQuo );
		}

		return premiumQuo;

	}

	/**
	 * This method populates the TTrnUwQuestionsQuo
	 * @param question
	 * @param policyVO
	 * @param deps
	 * @return
	 */
	private TTrnUwQuestionsQuo getUWAPojo( UWQuestionVO question, PolicyVO policyVO, POJO deps ){

		TTrnBuildingQuo ttrnBuildingQuo = null;
		TTrnWctplPremiseQuo ttrnPremiseQuo = null;
		if( deps instanceof TTrnBuildingQuo )
			ttrnBuildingQuo = (TTrnBuildingQuo) deps;
		else if(deps instanceof TTrnWctplPremiseQuo){
			ttrnPremiseQuo = (TTrnWctplPremiseQuo) deps;
		}
		TTrnUwQuestionsQuo uwQuestionsQuo = new TTrnUwQuestionsQuo();
		TTrnUwQuestionsQuoId id = new TTrnUwQuestionsQuoId();
		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
		id.setUqtPolPolicyId( mbSection.getPolicyId() );
		id.setUqtPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		id.setUqtUwqCode( question.getQId() );
		if( !Utils.isEmpty( ttrnBuildingQuo ) )
			id.setUqtLocId( ttrnBuildingQuo.getId().getBldId() );
		else
			id.setUqtLocId( ttrnPremiseQuo.getId().getWbdId() );
		uwQuestionsQuo.setId( id );
		uwQuestionsQuo.setStatus( SvcConstants.POL_STATUS_PENDING );
		uwQuestionsQuo.setUqtUwqAnswer( question.getResponse() );
		uwQuestionsQuo.setUqtValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		uwQuestionsQuo.setUqtValidityExpiryDate( SvcConstants.EXP_DATE );

		return uwQuestionsQuo;
	}

	/**
	 * This method populates the TTrnContentQuo (T_TRN_CONTENT_QUO) with all
	 * required details like sum equipment type , equipment description and so for the
	 * for given Machinery
	 * 
	 * @param deps
	 * @param policyVO
	 * @param depsVO
	 * @param occupancy
	 * @return
	 */
	private TTrnContentQuo getContentPojo( POJO deps, PolicyVO policyVO, MachineDetailsVO depsVO, POJO occupancy ){
		TTrnContentQuo contentQuo = new TTrnContentQuo();
		TTrnBuildingQuo ttrnBuildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TMasOccupancy masOccupancy = null;
		if(occupancy instanceof TMasOccupancy){
			 masOccupancy = (TMasOccupancy) occupancy;
		}	
		if( deps instanceof TTrnBuildingQuo )
			ttrnBuildingQuo = (TTrnBuildingQuo) deps;
		else if(deps instanceof TTrnWctplPremiseQuo)
			premiseQuo = (TTrnWctplPremiseQuo) deps;

		contentQuo.setCntDescription( depsVO.getContents().getDesc() );

		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( mbSection );
		MBVO mbDetails = (MBVO) PolicyUtils.getRiskGroupDetails( locationDetails, mbSection );
		MBUWDetailsVO mbuwDetailsVO = (MBUWDetailsVO) mbDetails.getUwDetails();

		if( !Utils.isEmpty( mbuwDetailsVO.getEmlPrc() ) ) contentQuo.setCntMplFirePerc( BigDecimal.valueOf( mbuwDetailsVO.getEmlPrc() ) );
		if( !Utils.isEmpty( mbuwDetailsVO.getEmlSI() ) ) contentQuo.setCntMplFire(new BigDecimal( mbuwDetailsVO.getEmlSI() ) );

		contentQuo.setCntPolicyId( mbSection.getPolicyId() );
		//contentQuo.setCntTradeCode( masOccupancy.getOcpTradeCode() );
		if(!Utils.isEmpty( masOccupancy ))
		contentQuo.setCntTradeCode( masOccupancy.getOcpRtCode() );
		//contentQuo.setCntTradeCode(ttrnBuildingQuo.getBldRskType());

		if( !Utils.isEmpty( ttrnBuildingQuo ) ){
			contentQuo.setCntBasicRiskId( ttrnBuildingQuo.getId().getBldId() );
			contentQuo.setCntRiRskCode( ttrnBuildingQuo.getBldRiRskCode() );

		}
		else{
			contentQuo.setCntBasicRiskId( premiseQuo.getId().getWbdId() );
			contentQuo.setCntRiRskCode( premiseQuo.getWbdRiRskCode() );

		}

		contentQuo.setCntRskCode( depsVO.getContents().getRiskCode() );
		contentQuo.setCntCategory( depsVO.getContents().getRiskType() );
		contentQuo.setCntRiskDtl( depsVO.getContents().getRiskDtl() );
		/*
		 * Start Date and end date to be cascaded from PolicyVO.validityStartDate
		 * which is updated by General Info Save Operation. Hence the same
		 * validityStartDate to be carried accross all DB entries for the
		 * Quote
		 */

		if( !Utils.isEmpty( policyVO.getScheme().getEffDate() ) ){
			contentQuo.setCntStartDate( policyVO.getScheme().getEffDate() );
		}
		else{
			contentQuo.setCntStartDate( (Date) ThreadLevelContext.get( SYSDATE ) );
		}

		if( !Utils.isEmpty( policyVO.getEndDate() ) ){
			contentQuo.setCntEndDate( policyVO.getEndDate() );
		}

		setAuditDetailsforcontent( contentQuo, policyVO );

		contentQuo.setCntBasicRskCode( depsVO.getContents().getBasicRiskCode() );
		contentQuo.setCntEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		contentQuo.setCntValidityExpiryDate( SvcConstants.EXP_DATE );

		//contentQuo.setCntRiRskCode( occupancy.getOcpRiRskCode() ); //from tmas occp
		contentQuo.setCntStatus( (byte) 6 ); // always one for create quote;

		//contentQuo.setCntTradeCode( occupancy.getOcpTradeCode() ); //from tmas occp

		contentQuo.setCntRiskSubDtl( depsVO.getMachineryType() );
		contentQuo.setCntDescription( depsVO.getMachineDescription() );
		//Commenting as per the CR
		//contentQuo.setCntYearOfMan( new Short( depsVO.getYearOfMake().toString() ) );
		contentQuo.setCntSumInsured( new BigDecimal( depsVO.getSumInsuredVO().getSumInsured() ) );

		return contentQuo;
	}

	/**
	 * This methods decides whether the given content or premium is to created or updated.
	 */
	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			Boolean isCreated = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_CREATED );
			ThreadLevelContext.clear( SvcConstants.PRM_TO_BE_CREATED );

			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			/*
			 * If cover id is null and cover is greater that 0 that means the risk is added and its a case of create 
			 */
			MachineDetailsVO machineDetails = (MachineDetailsVO) depsVO[ 0 ];
			Boolean isToBeDeleted = false;
			if( !Utils.isEmpty( machineDetails.getIsToBeDeleted() ) ){
				if( machineDetails.getIsToBeDeleted() ){
					isToBeDeleted = true;
				}
			}
			if( Utils.isEmpty( machineDetails.getContents().getCoverId() ) && machineDetails.getContents().getCover().doubleValue() > 0.0 && !isToBeDeleted ){
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, true );
				return true;
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			return toCreateUWQuestionsRecord( policyVO, depsPOJO, depsVO );
		}
		ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, false );
		return false;
	}

	/**
	 * This methods decides whether the given content or premium is to deleted or not.
	 */
	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeDeleted = false;

		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
			MachineDetailsVO machineDetailsVO = (MachineDetailsVO) depsVO[ 0 ];
			/*
			 * If CoverId is not null and cover is 0 that means the cover is deleted.
			 */
			if( !Utils.isEmpty( machineDetailsVO.getContents().getCoverId() ) && !Utils.isEmpty( machineDetailsVO.getIsToBeDeleted() ) && machineDetailsVO.getIsToBeDeleted() ){
				isToBeDeleted = true;
				ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, true );
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			isToBeDeleted = ( (Boolean) ThreadLevelContext.get( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED ) ) ? true : false;
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, null );
		}
		return isToBeDeleted;
	}

	/**
	 * This method sets the key values which can be used in further processing.
	 */
	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){
		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( mbSection );

		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			if(mappedRecord instanceof TTrnContentQuo)
				updateKeyValuesToVOs( (TTrnContentQuo) mappedRecord, locationDetails, depsVO[ 0 ] );
		}
	}

	private void updateKeyValuesToVOs( TTrnContentQuo mappedRecord, LocationVO locationDetails, BaseVO depsVO ){

		MachineDetailsVO coverDetail = (MachineDetailsVO) depsVO;
		coverDetail.getContents().setCoverId( mappedRecord.getId().getCntContentId() );
	}

	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		// TODO Auto-generated method stub

	}

	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		// TODO Auto-generated method stub

	}

	/**
	 * Constructs the primary keys for given tables
	 */
	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){
		POJOId id = null;
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuo premiumQuo = null;
			if( mappedRecord instanceof TTrnPremiumQuo ){
				premiumQuo = (TTrnPremiumQuo) mappedRecord;

				TTrnPremiumQuoId pId = premiumQuo.getId();
				pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
				id = pId;
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			TTrnContentQuoId cId = new TTrnContentQuoId();
			cId.setCntContentId( NextSequenceValue.getNextSequence( CONTENT_SEQ, null,null,getHibernateTemplate() ) );
			cId.setCntValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = cId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			if(mappedRecord instanceof TTrnUwQuestionsQuo){
				TTrnUwQuestionsQuo questionsQuo = (TTrnUwQuestionsQuo) mappedRecord;
				TTrnUwQuestionsQuoId uId = questionsQuo.getId();
				id = uId;
			}
		}

		return id;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){
		POJOId id = null;

		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			TTrnPremiumQuoId pId ;
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			TTrnContentQuoId existingcId = (TTrnContentQuoId) existingId;
			TTrnContentQuoId cId = new TTrnContentQuoId();
			cId.setCntContentId( existingcId.getCntContentId() );
			cId.setCntValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = cId;
		}

		return id;
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
	public BaseVO loadMBSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveMBSection( BaseVO baseVO ){
		return saveSection( baseVO );
	}

	@Override
	protected void sectionPreProcessing( PolicyVO policyVO ){
		super.sectionPreProcessing( policyVO );
	}

	private void setAuditDetailsforcontent( TTrnContentQuo contentQuo, PolicyVO policyVO ){
		Integer userId = SvcUtils.getUserId( policyVO );
		//TODO: Need to find a way to conditionally update the prepared by columns. If its an update there is no need to update this column
		contentQuo.setCntPreparedBy( userId );
		contentQuo.setCntPreparedDt( (Date) ThreadLevelContext.get( SYSDATE ) );

		contentQuo.setCntModifiedBy( userId );
		contentQuo.setCntModifiedDt( (Date) ThreadLevelContext.get( SYSDATE ) );

	}

	/**
	 * Does necessary actions after data  is persisted in the DB
	 */
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		cascadeUWAnswers( policyVO );
		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( mbSection );
		MBVO mbDetails = (MBVO) PolicyUtils.getRiskGroupDetails( locationDetails, mbSection );
		removeDeletedRowsFromContext( mbDetails );
		updateSectionLevelSIAndPremium( mbDetails );
		updateEndtText( policyVO );

		super.sectionPostProcessing( policyVO );
	}

	/**
	 * updates the endorsement text for content removal , addition and modification
	 * @param policyVO
	 */
	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( mbSection );
			DAOUtils.deletePrevEndtText( mbSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), getSectionId(),
					Long.valueOf( locationDetails.getRiskGroupId() ) );
			
				// MB_RISK_TYPE = 19 is passed to differentiate MB from other section which are stored in T_TRN_Content/Quo
				DAOUtils.updateEBCforendorsementFlow( mbSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), mbSection.getSectionId(), new Integer(
					Utils.getSingleValueAppConfig( MB_RISK_TYPE ) ) );
			
				DAOUtils.updateContforendorsementFlow( mbSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), mbSection.getSectionId(), new Integer(
					Utils.getSingleValueAppConfig( MB_RISK_TYPE ) ) );
			
				DAOUtils.deleteCntforEndorsementFlow( mbSection.getPolicyId(), policyVO, mbSection.getSectionId(), Long.valueOf( locationDetails.getRiskGroupId() ), new Integer(
						Utils.getSingleValueAppConfig( MB_RISK_TYPE ) ) );
				
				// Call to procedure for generating endt text for UW Questions changes.
				
				DAOUtils.updateUWQuestions( mbSection.getPolicyId(), policyVO,mbSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()) );
				
				DAOUtils.updateDeductibleforendorsementFlow( mbSection.getPolicyId(), policyVO,mbSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );	
				
			DAOUtils.updateTotalSITextforendorsementFlow( mbSection.getPolicyId(), policyVO,mbSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );
			
			//DAOUtils.updateEndTextForRiskAdd( mbSection.getPolicyId(), policyVO,mbSection.getSectionId());
			

		}

	}

	/**
	 * Sets the section level premium and sum insured for MB 
	 * @param mbDetails
	 */
	private void updateSectionLevelSIAndPremium( MBVO mbDetails ){
		mbDetails.setSumInsured( getSectionLevelSumInsured( mbDetails ) );
		getSectionLevelPremium( mbDetails );

	}

	/* This method is used to remove the delted rows from context
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/
	protected void removeDeletedRowsFromContext( MBVO mbDetails ){
		try{
			if( Utils.isEmpty( mbDetails.getIsToBeDeleted() ) ) return;

			boolean deletionflag = false;
			ArrayList<MachineDetailsVO> toBeDeletedVOs = new ArrayList<MachineDetailsVO>();
			for( MachineDetailsVO machineDetailsVO : mbDetails.getMachineryDetails() ){
				if( !Utils.isEmpty( machineDetailsVO.getIsToBeDeleted() ) && machineDetailsVO.getIsToBeDeleted() ){
					toBeDeletedVOs.add( machineDetailsVO );
					deletionflag = true;
				}
			}
			if( deletionflag ){
				for( MachineDetailsVO toBeDeletedVO : toBeDeletedVOs ){

					mbDetails.getMachineryDetails().remove( toBeDeletedVO );
				}

			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}

	/**
	 * Gets the section level sum insured for MB
	 * @param mbDetails
	 */
	private void getSectionLevelPremium( MBVO mbDetails ){
		if( Utils.isEmpty( mbDetails.getPremium() ) ){
			mbDetails.setPremium( new PremiumVO() );
		}
		PremiumHelper.getSectionLevelPremium( mbDetails );
	}

	/**
	 * Gets the section level premium for MB
	 * @param mbDetails
	 * @return
	 */
	private double getSectionLevelSumInsured( MBVO mbDetails ){
		return PremiumHelper.getSectionLevelSumInsured( mbDetails );

	}

	/**
	 * 
	 * @param policyVO
	 */
	private void cascadeUWAnswers( PolicyVO policyVO ){
		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MB );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( mbSection );
		MBVO mbDetails = (MBVO) PolicyUtils.getRiskGroupDetails( locationDetails, mbSection );

		if( !Utils.isEmpty( mbDetails.getUwQuestions() ) && mbDetails.getUwQuestions().isCascaded() ){

			PASStoredProcedure sp = (PASStoredProcedure) ( policyVO.getIsQuote() ? Utils.getBean( "cascadeUwqProc_QUO" ) : Utils.getBean( "cascadeUwqProc_POL" ) );
			try{
				getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
				sp.call( policyVO.getPolLinkingId(), mbSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ),
						(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ), locationDetails.getRiskGroupId(), SvcConstants.SECTION_ID_MB );
			}
			catch( DataAccessException e ){
				throw new BusinessException( "par.uwqCascade.exception", e, "An exception occured while executing stored proc." );
			}
		}
	}

	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
	}
}
