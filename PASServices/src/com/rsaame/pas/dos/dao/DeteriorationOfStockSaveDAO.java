package com.rsaame.pas.dos.dao;

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
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockUWDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class DeteriorationOfStockSaveDAO extends BaseSectionSaveDAO implements IDeteriorationOfStockDao{
	private final static String CONTENT_SEQ = "SEQ_CONTENTS_ID";
	@Override
	public BaseVO loadDeteriorationOfStockSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getSectionId(){
		return SvcConstants.SECTION_ID_DOS;
	}

	@Override
	protected int getClassCode(){
		return SvcConstants.CLASS_ID_DOS;
	}

	@Override
	protected BaseVO saveSection( BaseVO input ){
		PolicyVO policyVO = (PolicyVO) input;
		SectionVO dosSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_DOS );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( dosSection );
		DeteriorationOfStockVO dosVO = (DeteriorationOfStockVO) PolicyUtils.getRiskGroupDetails( locationDetails, dosSection );
		/*
		 * Let us get the system date right now and use from here on for this
		 * transaction.
		 */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

		POJO buildingOrPremise = handleContent( policyVO, dosSection, locationDetails, dosVO );
		
		/* Handle the UW questions. */
		handleUWQ( policyVO, dosSection, locationDetails, dosVO, buildingOrPremise );

		return policyVO;
	}

	
	private void handleUWQ( PolicyVO policyVO, SectionVO dosSection, LocationVO locationDetails, DeteriorationOfStockVO dosVO, POJO buildingOrPremise ){


		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else if(buildingOrPremise instanceof TTrnWctplPremiseQuo)
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
	//	RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, section );

		POJO[] uwqDeps = { buildingQuo };
		if( Utils.isEmpty( buildingQuo ) ) uwqDeps[ 0 ] = premiseQuo;
		UWQuestionsVO questionsVOs = dosVO.getUwQuestions();
		List<UWQuestionVO> questions = questionsVOs.getQuestions();

		for( UWQuestionVO question : questions ){
			BaseVO[] uwqDepsVO = {locationDetails, dosVO,question };
			////#Renewal Multiple Id's handling changes, use biSection because biVO contains policyId null sometimes
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE, policyVO, uwqDeps, uwqDepsVO, false, dosSection.getPolicyId(), question.getQId(),
					( Long.valueOf( rg.getRiskGroupId() ) ), Utils.isEmpty( question.getResponse() ) ? null : question.getResponse() );
		}
	
		
	}

	private POJO handleContent( PolicyVO policyVO, SectionVO dosSection, LocationVO locationDetails, DeteriorationOfStockVO dosVO ){


		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TTrnPremiumQuo premiumQuo = null;
		TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );
		POJO buildingOrPremise = getBuildingOrPremiseRecord( policyVO, dosSection, locationDetails, dosVO );
		
		if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else if(buildingOrPremise instanceof TTrnWctplPremiseQuo)
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;
		
		for( DeteriorationOfStockDetailsVO stockDetailVO : dosVO.getDeteriorationOfStockDetails() ){
			TTrnContentQuo contentQuo = null;
			// if stockDetailVO.getContentId() is equal to "-9999" it means the
			// record is new (creation scenario) else the record is already
			// presents in DB (update)
			//Renewal Multiple Id's handling changes, added policy in the query parameter
			
			if( !Utils.isEmpty( stockDetailVO.getContentId() ) && !stockDetailVO.getContentId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
				contentQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_CONTENT, policyVO, new POJO[]{ buildingOrPremise,occupancy },
						new BaseVO[]{ dosSection, stockDetailVO, dosVO.getUwDetails(), locationDetails }, false, dosSection.getPolicyId(), stockDetailVO.getContentId().longValue(),
						Long.valueOf( locationDetails.getRiskGroupId() ) );
			}
			else{
				contentQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_CONTENT, policyVO, new POJO[]{ buildingOrPremise,occupancy },
						new BaseVO[]{ dosSection, stockDetailVO, dosVO.getUwDetails(), locationDetails }, false );
			}
			
			if( !Utils.isEmpty( buildingQuo ) && !Utils.isEmpty( buildingQuo.getId() ) )
				ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, buildingQuo.getId().getBldId() );
			if( Utils.isEmpty( buildingQuo ) ){
				ThreadLevelContext.clear( SvcConstants.TLC_KEY_BASIC_RISK_ID );
				ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, premiseQuo.getId().getWbdId() );
			}
			
			premiumQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{ buildingOrPremise, contentQuo }, new BaseVO[]{ dosSection, stockDetailVO,
					dosVO, locationDetails }, false, Long.valueOf( dosSection.getPolicyId() ), BigDecimal.valueOf( contentQuo.getId().getCntContentId() ),
					BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Short.valueOf( Utils.getSingleValueAppConfig( "DOS_BASIC_COVER" ) ),
					Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_SUB_TYPE" ) ) );
			
			if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
				if( !Utils.isEmpty( stockDetailVO.getPremium() ) ){
					stockDetailVO.getPremium().setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
				}

			
		}
		}

		return buildingOrPremise;
		
	}

	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );	
	}

	private POJO getBuildingOrPremiseRecord( PolicyVO policyVO, SectionVO dosSection, LocationVO locationDetails, DeteriorationOfStockVO dosVO ){

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

	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){

		POJO mappedPOJO = null;
		
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			SectionVO dosSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_DOS );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( dosSection );
			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );
			TTrnPremiumQuo premiumQuo = getPremiumPojo( policyVO, (TTrnContentQuo) deps[ 1 ], depsVO, (TMasOccupancy) occupancy, Long.valueOf( locationDetails.getRiskGroupId() ),
					deps[ 0 ] );
			mappedPOJO = premiumQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			TTrnContentQuo contentQuo = getContentPojo( deps[ 0 ], policyVO, (DeteriorationOfStockDetailsVO) depsVO[ 1 ], deps[ 1 ] );
			mappedPOJO = contentQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){

			UWQuestionVO question = (UWQuestionVO) depsVO[ 2 ];
			TTrnUwQuestionsQuo questionsQuo = getUWAPojo( question, policyVO, deps[ 0 ] );
			mappedPOJO = questionsQuo;
		}
		return mappedPOJO;

	}

	private TTrnPremiumQuo getPremiumPojo( PolicyVO policyDetails, TTrnContentQuo trnContentQuo, BaseVO[] depsVO, TMasOccupancy occupancy, Long buildingId, POJO buildingOrPremise ){

		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		SectionVO dosSection = (SectionVO) depsVO[ 0 ];
		DeteriorationOfStockDetailsVO stockDetailsVO = (DeteriorationOfStockDetailsVO) depsVO[ 1 ];

		if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( "DOS_PRM_BASIC_RISK_CODE" ) ) );

		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );
		premiumQuoId.setPrmRskId( new BigDecimal( trnContentQuo.getId().getCntContentId() ) );

		premiumQuoId.setPrmRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_CODE ) ) );
		premiumQuoId.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COV_CODE" ) ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_SUB_TYPE" ) ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( Utils.getSingleValueAppConfig( "DOS_COVER_TYPE" ) ) );
		premiumQuoId.setPrmPolicyId( dosSection.getPolicyId() );
		premiumQuoId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( "VSD" ) );
		premiumQuo.setId( premiumQuoId );
		//premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		premiumQuo.setPrmClCode( Short.valueOf(Utils.getSingleValueAppConfig( "DETERIORATION_OF_STOCK_CLASS" )) );
		//premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		premiumQuo.setPrmRcCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_CODE ) ) );
		//TODO : RIsk sub category code for dos??
		premiumQuo.setPrmRscCode( Integer.parseInt( Utils.getSingleValueAppConfig( "EE_RISK_SUB_CAT_CODE" ) ) );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
	//	premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		if( !Utils.isEmpty( buildingQuo ) )
			premiumQuo.setPrmRiRskCode( buildingQuo.getBldRiRskCode() );
		else
			premiumQuo.setPrmRiRskCode( premiseQuo.getWbdRiRskCode() );
		premiumQuo.setPrmRtCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) ) );
		premiumQuo.setPrmStatus( (byte) 1 ); // for new quote	
		//premiumQuo.setPrmSumInsuredCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		//premiumQuo.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmRiLocCode( 99 );
		if( !Utils.isEmpty( stockDetailsVO.getSumInsuredDetails() ) && !Utils.isEmpty( stockDetailsVO.getSumInsuredDetails().getSumInsured() ) ){

			premiumQuo.setPrmSumInsured( new BigDecimal( stockDetailsVO.getSumInsuredDetails().getSumInsured() ) );
		}
		if( !Utils.isEmpty( stockDetailsVO.getSumInsuredDetails().getDeductible() ) ){
			premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( stockDetailsVO.getSumInsuredDetails().getDeductible().doubleValue() ) );
		}
		if( !Utils.isEmpty( stockDetailsVO.getPremium() ) ){
			if( !Utils.isEmpty( stockDetailsVO.getPremium().getPremiumAmt() ) ){
				premiumQuo.setPrmPremium( new BigDecimal(String.valueOf( stockDetailsVO.getPremium().getPremiumAmt() ) ));
				premiumQuo.setPrmPremiumActual( new BigDecimal( String.valueOf(stockDetailsVO.getPremium().getPremiumAmt() ) ));
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		else{
			setZeroPrmValue( premiumQuo );
		}
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );
		//premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );

		return premiumQuo;
	
	}

	private TTrnUwQuestionsQuo getUWAPojo( UWQuestionVO question, PolicyVO policyVO, POJO deps ){


		TTrnBuildingQuo ttrnBuildingQuo = null;
		TTrnWctplPremiseQuo ttrnPremiseQuo = null;
		if( deps instanceof TTrnBuildingQuo )
			ttrnBuildingQuo = (TTrnBuildingQuo) deps;
		else if(deps instanceof TTrnWctplPremiseQuo)
			ttrnPremiseQuo = (TTrnWctplPremiseQuo) deps;

		TTrnUwQuestionsQuo uwQuestionsQuo = new TTrnUwQuestionsQuo();
		TTrnUwQuestionsQuoId id = new TTrnUwQuestionsQuoId();
		SectionVO mbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_DOS );
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

	private TTrnContentQuo getContentPojo( POJO deps, PolicyVO policyVO, DeteriorationOfStockDetailsVO depsVO, POJO occupancy ){

		TTrnContentQuo contentQuo = new TTrnContentQuo();
		TTrnBuildingQuo ttrnBuildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TMasOccupancy masOccupancy =null;
		if(!Utils.isEmpty( occupancy )){
			if(occupancy instanceof TMasOccupancy){
				 masOccupancy = (TMasOccupancy) occupancy;
			}	
		}	

		if( deps instanceof TTrnBuildingQuo )
			ttrnBuildingQuo = (TTrnBuildingQuo) deps;
		else if(deps instanceof TTrnWctplPremiseQuo) 
			premiseQuo = (TTrnWctplPremiseQuo) deps;		

		SectionVO dosSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_DOS );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( dosSection );
		DeteriorationOfStockVO dosDetails = (DeteriorationOfStockVO) PolicyUtils.getRiskGroupDetails( locationDetails, dosSection );
		DeteriorationOfStockUWDetailsVO dosuwDetailsVO = (DeteriorationOfStockUWDetailsVO) dosDetails.getDeteriorationOfStockUWDetails();

		if( !Utils.isEmpty( dosuwDetailsVO.getEmlPercentage() ) ) contentQuo.setCntMplFirePerc( BigDecimal.valueOf( dosuwDetailsVO.getEmlPercentage() ) );
		if( !Utils.isEmpty( dosuwDetailsVO.getEmlSI() ) ) contentQuo.setCntMplFire(new BigDecimal(dosuwDetailsVO.getEmlSI()));

		contentQuo.setCntPolicyId( dosSection.getPolicyId() );
		if( !Utils.isEmpty( ttrnBuildingQuo ) ){
			contentQuo.setCntBasicRiskId( ttrnBuildingQuo.getId().getBldId() );
		}
		else{
			contentQuo.setCntBasicRiskId( premiseQuo.getId().getWbdId() );
		}

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
			contentQuo.setCntStartDate( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}

		if( !Utils.isEmpty( policyVO.getEndDate() ) ){
			contentQuo.setCntEndDate( policyVO.getEndDate() );
		}

		setAuditDetailsforcontent( contentQuo, policyVO );
		contentQuo.setCntRskCode(Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_CODE )));
		contentQuo.setCntCategory( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) )  );
		contentQuo.setCntRiskDtl( Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_DETAIL ) )  );
		contentQuo.setCntBasicRskCode(Integer.valueOf( Utils.getSingleValueAppConfig( "DOS_BASIC_RISK_CODE" ) ));
		contentQuo.setCntEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		contentQuo.setCntValidityExpiryDate( SvcConstants.EXP_DATE );
		if(!Utils.isEmpty( masOccupancy )){
			contentQuo.setCntRiRskCode( masOccupancy.getOcpRiRskCode() ); //from tmas occp
			//contentQuo.setCntTradeCode( masOccupancy.getOcpTradeCode() ); //from tmas occp
			contentQuo.setCntTradeCode(masOccupancy.getOcpRtCode());
			
		}	
		contentQuo.setCntSumInsured( new BigDecimal( depsVO.getSumInsuredDetails().getSumInsured() ) );
		contentQuo.setCntRiskSubDtl( Integer.valueOf( depsVO.getDeteriorationOfStockType() ));

		return contentQuo;
	
	}

	private void setAuditDetailsforcontent( TTrnContentQuo contentQuo, PolicyVO policyVO ){

		Integer userId = SvcUtils.getUserId( policyVO );
		// TODO: Need to find a way to conditionally update the prepared by
		// columns. If its an update there is no need to update this column
		contentQuo.setCntPreparedBy( userId );
		contentQuo.setCntPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );

		contentQuo.setCntModifiedBy( userId );
		contentQuo.setCntModifiedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );		
	}

	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		
		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			
			DeteriorationOfStockDetailsVO stockDetailsVO = (DeteriorationOfStockDetailsVO) depsVO[ 1 ];
			Boolean isToBeDeleted = false;
			if( !Utils.isEmpty( stockDetailsVO.getIsToBeDeleted() ) ){
				if( stockDetailsVO.getIsToBeDeleted() ){
					isToBeDeleted = true;
				}
			}
			if(  !Utils.isEmpty( stockDetailsVO.getContentId() )  && stockDetailsVO.getContentId()<0 && !isToBeDeleted ){
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, true );
				return true;
			}
		
		}
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			Boolean isCreated = (Boolean) ThreadLevelContext.get( com.Constant.CONST_PRM_TO_BE_CREATED );
			ThreadLevelContext.clear( com.Constant.CONST_PRM_TO_BE_CREATED );
			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;

		}else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			return toCreateUWQuestionsRecord( policyVO, depsPOJO, depsVO );
		}
		ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, false );
		return false;
	}

	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeDeleted = false;

		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
			DeteriorationOfStockDetailsVO stockDetailsVO = (DeteriorationOfStockDetailsVO) depsVO[ 1 ];
			/*
			 * If CoverId is not null and cover is 0 that means the cover is deleted.
			 */
			if( !Utils.isEmpty( stockDetailsVO.getIsToBeDeleted() ) && stockDetailsVO.getIsToBeDeleted() ){
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

	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){

		SectionVO dosSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_DOS );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( dosSection );
		
		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			if(!Utils.isEmpty( mappedRecord ) && mappedRecord instanceof TTrnContentQuo){
				updateKeyValuesToVOs( (TTrnContentQuo) mappedRecord, locationDetails, depsVO[ 1 ] );
			}	
		}
			
	}

	private void updateKeyValuesToVOs( TTrnContentQuo mappedRecord, LocationVO locationDetails, BaseVO depsVO ){

		DeteriorationOfStockDetailsVO stockDetail = (DeteriorationOfStockDetailsVO) depsVO;
		stockDetail.setContentId( mappedRecord.getId().getCntContentId() );
	}

	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		// TODO Auto-generated method stub
		
	}

	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){

		POJOId id = null;
		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			TTrnContentQuoId cId = null;
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnContentQuo ){
					TTrnContentQuo contentQuo = (TTrnContentQuo) mappedRecord;
					if( Utils.isEmpty( contentQuo.getId() ) ){
						cId = new TTrnContentQuoId();
						cId.setCntContentId( NextSequenceValue.getNextSequence( CONTENT_SEQ,null,null, getHibernateTemplate() ) );
					}
					if( !Utils.isEmpty( cId ) ){
						cId.setCntValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					}
					id = cId;
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
		return id;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){
		POJOId id = null;

		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			//Radar fix
			TTrnPremiumQuoId pId = null;//new TTrnPremiumQuoId();
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
	public BaseVO saveDeteriorationOfStockSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 *  
	 * @param stockDetails
	 */
	private void updateSectionLevelSIAndPremium( DeteriorationOfStockVO stockDetails ){

		stockDetails.setSumInsured( getSectionLevelSumInsured( stockDetails ) );
		setSectionLevelPremium( stockDetails );

	}

	private void setSectionLevelPremium( DeteriorationOfStockVO stockDetails ){
		if( Utils.isEmpty( stockDetails.getPremium() ) ){
			stockDetails.setPremium( new PremiumVO() );
		}
		PremiumHelper.getSectionLevelPremium( stockDetails );
		
	}

	private double getSectionLevelSumInsured( DeteriorationOfStockVO stockDetails ){
		return PremiumHelper.getSectionLevelSumInsured( stockDetails );
	} 
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		cascadeUWAnswers( policyVO );
		SectionVO dosSection = PolicyUtils.getSectionVO( policyVO,SvcConstants.SECTION_ID_DOS );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( dosSection );
		DeteriorationOfStockVO stockDetails = (DeteriorationOfStockVO) PolicyUtils.getRiskGroupDetails( locationDetails, dosSection );
		removeDeletedRowsFromContext(stockDetails);
		updateSectionLevelSIAndPremium( stockDetails );
		updateEndtText( policyVO );
		super.sectionPostProcessing( policyVO );
	}
	
	
	private void cascadeUWAnswers(PolicyVO policyVO) {
		SectionVO dosSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_DOS );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( dosSection );
		DeteriorationOfStockVO dosDetails = (DeteriorationOfStockVO) PolicyUtils.getRiskGroupDetails( locationDetails, dosSection );

		if( !Utils.isEmpty( dosDetails.getUwQuestions() ) && dosDetails.getUwQuestions().isCascaded() ){

			PASStoredProcedure sp = (PASStoredProcedure) ( policyVO.getIsQuote() ? Utils.getBean( "cascadeUwqProc_QUO" ) : Utils.getBean( "cascadeUwqProc_POL" ) );
			try{
				getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
				sp.call( policyVO.getPolLinkingId(), dosSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ),
						(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ), locationDetails.getRiskGroupId(), SvcConstants.SECTION_ID_DOS );
			}
			catch( DataAccessException e ){
				throw new BusinessException( "par.uwqCascade.exception", e, "An exception occured while executing stored proc." );
			}
		}
	}

	protected void removeDeletedRowsFromContext( DeteriorationOfStockVO contextDOSVO )
	{
		boolean deletionflag = false;
		try
		{
			if( Utils.isEmpty( contextDOSVO.getIsToBeDeleted() ) )
			{
				return;
			}
			ArrayList<DeteriorationOfStockDetailsVO> toBeDeletedVOs = new ArrayList<DeteriorationOfStockDetailsVO>();
			for( DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO : contextDOSVO.getDeteriorationOfStockDetails() )
			{
				if( !com.mindtree.ruc.cmn.utils.Utils.isEmpty( deteriorationOfStockDetailsVO.getIsToBeDeleted() ) && 
						deteriorationOfStockDetailsVO.getIsToBeDeleted() )
				{
					toBeDeletedVOs.add( deteriorationOfStockDetailsVO );
					deletionflag = true;
				}
			}
			if( deletionflag )
			{
				for( DeteriorationOfStockDetailsVO toBeDeletedVO : toBeDeletedVOs )
				{
					contextDOSVO.getDeteriorationOfStockDetails().remove( toBeDeletedVO );
				}
			}
		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}
	
	/**
	 * updates the text for content removal , addition and modification
	 * @param policyVO
	 */
	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			SectionVO dosSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_DOS );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( dosSection );

			DAOUtils.deletePrevEndtText( dosSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), getSectionId(),
			Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			DAOUtils.updateEBCforendorsementFlow( dosSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), dosSection.getSectionId(),Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_TYPE_CODE ) ) );
					
			DAOUtils.updateContforendorsementFlow( dosSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), dosSection.getSectionId(), new Integer(
			Utils.getSingleValueAppConfig(com.Constant.CONST_DOS_RISK_TYPE_CODE ) ) );
			DAOUtils.deleteCntforEndorsementFlow( dosSection.getPolicyId(), policyVO, dosSection.getSectionId(), Long.valueOf( locationDetails.getRiskGroupId() ),Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_DOS_RISK_TYPE_CODE ) ) );
			
			// Calls procedure for generating endt Text for New Risk added.
					
			//DAOUtils.updateEndTextForRiskAdd( dosSection.getPolicyId(), policyVO,dosSection.getSectionId());
			DAOUtils.updateUWQuestions( dosSection.getPolicyId(), policyVO,dosSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()) );
			DAOUtils.updateDeductibleforendorsementFlow( dosSection.getPolicyId(), policyVO,dosSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId()) );
			
			DAOUtils.updateTotalSITextforendorsementFlow( dosSection.getPolicyId(), policyVO,dosSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );
		}

	}
	
	
}
