package com.rsaame.pas.ee.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
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
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnContentQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EEUWDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * Saves the EE Section
 * @author m1014438
 *
 */
public class EESaveDAO extends BaseSectionSaveDAO implements IEESectionDAO{
	
	private static final Logger logger = Logger.getLogger( EESaveDAO.class );
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	private final static Integer EE_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "EE_SECTION" ) );

	private final static Integer EE_ENDT_ID = 0;
	private final static Integer EE_CLASS_CODE = 2;
	private final static Short EE_POLICY_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) );
	private final static Byte EE_CONTENT_STATUS = 6;

	private final static String CONTENT_SEQ = "SEQ_CONTENTS_ID";

	@Override
	public BaseVO loadEESection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveEESection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Return the Section Id for Electronic Equipments
	 */
	@Override
	protected int getSectionId(){
		return SvcConstants.SECTION_ID_EE;
	}

	/**
	 * Returns the CLASS ID for Electronic Equipment section
	 */
	@Override
	protected int getClassCode(){
		return SvcConstants.CLASS_ID_EE;
	}

	@Override
	/**
	 * This method invokes handleElectronicEquipments method to save the Electronic Equipments
	 */
	protected BaseVO saveSection( BaseVO input ){
		logger.debug( "Inside getRiskDetails EESaveDao" );
		PolicyVO policyVO = (PolicyVO) input;
		/*
		 * Let us get the system date right now and use from here on for this
		 * transaction.
		 */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

		handleElectronicEquipments( policyVO );

		return policyVO;
	}

	/**
	 * 
	 * Calls handleContent method
	 * @param policyVO
	 */
	private void handleElectronicEquipments( PolicyVO policyVO ){

		SectionVO eeSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_EE );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( eeSection );
		EEVO eeDetails = (EEVO) PolicyUtils.getRiskGroupDetails( locationDetails, eeSection );
		handleContent( policyVO, eeSection, locationDetails, eeDetails );

	}

	/**
	 * This method decides which is the basic section among PAR and PL to
	 * construct T_TRN_CONTENT_QUO
	 * 
	 * @param policyVO
	 * @param eeSection
	 * @param locationDetails
	 * @param eeDetails
	 * @return
	 */
	private POJO getBuildingOrPremiseRecord( PolicyVO policyVO, SectionVO eeSection, LocationVO locationDetails, EEVO eeDetails ){

		Integer basicSectionID = null;
		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		POJO buildingOrPremise = null;

		/*
		 * If PAR is the base section, then we have to use the building details
		 * of PAR.
		 * Check if par or pl is present, basicSectionID will contain the
		 * section id of either par or pl
		 */

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
		/*
		 * If PAR is the basic section, then we have to get the PAR building
		 * record and use it for the construction of the T_TRN_CONTENT_QUO
		 * record POJO. If PL is the basic section, then we have to use the
		 * WCTPL Premise record for this.
		 */
		if( !Utils.isEmpty( basicSection ) && ( basicSectionID.equals( PAR_SECTION_ID ) ) ){
			if( !Utils.isEmpty( locationDetails.getRiskGroupId() ) ){
				try{
					buildingQuo = (TTrnBuildingQuo) getHibernateTemplate().find( "from TTrnBuildingQuo buldQ where buldQ.id.bldId=? and buldQ.bldValidityExpiryDate=?",
							Long.valueOf( locationDetails.getRiskGroupId() ),SvcConstants.EXP_DATE ).get( 0 );
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
		else if( !Utils.isEmpty( basicSection ) && basicSectionID.equals( PL_SECTION_ID ) ){
			PublicLiabilityVO plDetails = (PublicLiabilityVO) basicSection.getRiskGroupDetails().get( locationDetails );
			if( !Utils.isEmpty( plDetails ) ){
				// this pojo may not be required, since the id required in case
				// of par is not selected will be available in publicLiabilityVO
				try{
					premiseQuo = (TTrnWctplPremiseQuo) getHibernateTemplate().find( "from TTrnWctplPremiseQuo preQ where preQ.id.wbdId=?",
							Long.valueOf( locationDetails.getRiskGroupId() ) ).get( 0 );
					buildingOrPremise = premiseQuo;
					/**
					 * LocationVO . RiskGroupId will be same as wbdId as PL is
					 * the basic section
					 **/
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

	private boolean isSectionPresent( int sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}

	/**
	 * This method iterates over the EquipmentVO and calls the handleTableRecord
	 * method for the further saving process.
	 * 
	 * @param policyVO
	 *            - object of PolicyVO
	 * @param eeSection
	 *            - object of SectionVO which holds the EE Section related
	 *            details
	 * @param locationDetails
	 *            - object of LocationVO
	 * @param eeDetails
	 *            - object of EEVO
	 * @param buildingOrPremise
	 *            - object of TTrnBuildingQuo
	 */
	private void handleContent( PolicyVO policyVO, SectionVO eeSection, LocationVO locationDetails, EEVO eeDetails ){

		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TTrnPremiumQuo premiumQuo = null;
		POJO buildingOrPremise = getBuildingOrPremiseRecord( policyVO, eeSection, locationDetails, eeDetails );
		if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else if(buildingOrPremise instanceof TTrnWctplPremiseQuo)
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;
		for( EquipmentVO equipmentVO : eeDetails.getEquipmentDtls() ){
			TTrnContentQuo contentQuo = null;
			// if equipmentVO.getContentId() is equal to "-9999" it means the
			// record is new (creation scenario) else the record is already
			// presents in DB (update)
			//Renewal Multiple Id's handling changes, added policy in the query parameter
			if( !Utils.isEmpty( equipmentVO.getContentId() ) && !equipmentVO.getContentId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
				contentQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_CONTENT, policyVO, new POJO[]{ buildingOrPremise },
						new BaseVO[]{ eeSection, equipmentVO, eeDetails.getUwDetails(), locationDetails }, false, eeSection.getPolicyId(), equipmentVO.getContentId().longValue(),
						Long.valueOf( locationDetails.getRiskGroupId() ), Long.valueOf( Utils.getSingleValueAppConfig( "EE_RISK_DETAIL" ) ) );
			}
			else{
				contentQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_CONTENT, policyVO, new POJO[]{ buildingOrPremise },
						new BaseVO[]{ eeSection, equipmentVO, eeDetails.getUwDetails(), locationDetails }, false );
			}

			if( !Utils.isEmpty( buildingQuo ) && !Utils.isEmpty( buildingQuo.getId() ) )
				ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, buildingQuo.getId().getBldId() );
			if( Utils.isEmpty( buildingQuo ) ){
				ThreadLevelContext.clear( SvcConstants.TLC_KEY_BASIC_RISK_ID );
				ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, premiseQuo.getId().getWbdId() );
			}

			premiumQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{ buildingOrPremise, contentQuo }, new BaseVO[]{ eeSection, equipmentVO,
					eeDetails, locationDetails }, false, Long.valueOf( eeSection.getPolicyId() ), BigDecimal.valueOf( contentQuo.getId().getCntContentId() ),
					BigDecimal.valueOf( Long.valueOf( locationDetails.getRiskGroupId() ) ), Short.valueOf( Utils.getSingleValueAppConfig( "EE_BASIC_COVER" ) ),
					Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_SUB_TYPE" ) ) );

			if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
				if( !Utils.isEmpty( equipmentVO.getPremium() ) ){
					equipmentVO.getPremium().setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
				}

			}

		}

	}

	/**
	 * This methods populates corresponding POJO and returns the same.
	 * 
	 * tableId - 
	 * 			ID of the corresponding table 
	 * policyVO - 
	 * 			object of PolicyVO
	 * deps - 
	 * 			object array of POJO which contains TTrnBuildingQuo , TTrnContentQuo 
	 * depsVO - 
	 * 			object array of BaseVO which contains SectionVO
	 * 	(for EE), EquipmentVO , UWDetails (EEUWDetailsVO)
	 */
	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;
		TTrnContentQuo contentQuo = null;
		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		POJO buildingOrPremise = deps[ 0 ];

		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( (SectionVO) depsVO[ 0 ] );
		TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );

		if( deps[ 0 ] instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;

		Long buildingId = null;
		if( !Utils.isEmpty( buildingQuo ) ){
			buildingId = buildingQuo.getId().getBldId();
		}
		else{
			buildingId = premiseQuo.getId().getWbdId();
		}

		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			contentQuo = getContentPojo( deps[ 0 ], policyVO, depsVO, buildingId, occupancy );
			mappedPOJO = contentQuo;
		}

		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			TTrnPremiumQuo premiumQuo = getPremiumPojo( policyVO, (TTrnContentQuo) deps[ 1 ], depsVO, occupancy, buildingId, buildingOrPremise );
			mappedPOJO = premiumQuo;
		}

		return mappedPOJO;
	}

	/**
	 * Reads and Returns the TMasOccupancy
	 * 
	 * @param ocpCode
	 * @return TMasOccupancy
	 */
	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
	}

	/**
	 * This method populates the TTrnPremiumQuo (T_TRN_PREMIUM_QUO) with all
	 * required details like sum insured , deductible and so for the
	 * given Electronic Equipment (EquipemtnVO)
	 * 
	 * Sets the content id to premium basic risk id
	 * Sets the building id to premium risk id
	 * 
	 * @param policyDetails
	 * @param trnBuildingQuo
	 * @param trnContentQuo
	 * @param depsVO - 
	 * 			object array of BaseVO which contains SectionVO (for EE), EquipmentVO , UWDetails (EEUWDetailsVO)
	 * @param occupancy
	 * @param buildingId 
	 * @param buildingOrPremise 
	 * @return premiumQuo - TTrnPremiumQuo
	 */
	private TTrnPremiumQuo getPremiumPojo( PolicyVO policyDetails, TTrnContentQuo trnContentQuo, BaseVO[] depsVO, TMasOccupancy occupancy, Long buildingId, POJO buildingOrPremise ){
		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		SectionVO eeSection = (SectionVO) depsVO[ 0 ];
		EquipmentVO equipmentVO = (EquipmentVO) depsVO[ 1 ];

		if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( "EE_PRM_BASIC_RISK_CODE" ) ) );

		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );
		premiumQuoId.setPrmRskId( new BigDecimal( trnContentQuo.getId().getCntContentId() ) );

		premiumQuoId.setPrmRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_CODE ) ) );
		premiumQuoId.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "EE_COV_CODE" ) ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_SUB_TYPE" ) ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( Utils.getSingleValueAppConfig( "EE_COVER_TYPE" ) ) );
		premiumQuoId.setPrmPolicyId( eeSection.getPolicyId() );
		premiumQuoId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( "VSD" ) );
		premiumQuo.setId( premiumQuoId );
		premiumQuo.setPrmEndtId( EE_ENDT_ID );
		premiumQuo.setPrmClCode( Short.valueOf( EE_CLASS_CODE.toString() ) );
		premiumQuo.setPrmPtCode( EE_POLICY_TYPE );
		premiumQuo.setPrmRcCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_CODE ) ) );
		premiumQuo.setPrmRscCode( Integer.parseInt( Utils.getSingleValueAppConfig( "EE_RISK_SUB_CAT_CODE" ) ) );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		if( !Utils.isEmpty( buildingQuo ) )
			premiumQuo.setPrmRiRskCode( buildingQuo.getBldRiRskCode() );
		else
			premiumQuo.setPrmRiRskCode( premiseQuo.getWbdRiRskCode() );
		premiumQuo.setPrmRtCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_TYPE_CODE ) ) );
		premiumQuo.setPrmStatus( (byte) 1 ); // for new quote	
		premiumQuo.setPrmSumInsuredCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmRiLocCode( 99 );
		if( !Utils.isEmpty( equipmentVO.getSumInsuredDetails() ) && !Utils.isEmpty( equipmentVO.getSumInsuredDetails().getSumInsured() ) ){

			premiumQuo.setPrmSumInsured(new BigDecimal( equipmentVO.getSumInsuredDetails().getSumInsured() ) );
		}
		if( !Utils.isEmpty( equipmentVO.getSumInsuredDetails().getDeductible() ) ){
			premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( equipmentVO.getSumInsuredDetails().getDeductible().doubleValue() ) );
		}
		if( !Utils.isEmpty( equipmentVO.getPremium() ) ){
			if( !Utils.isEmpty( equipmentVO.getPremium().getPremiumAmt() ) ){
				premiumQuo.setPrmPremium( new BigDecimal( String.valueOf(equipmentVO.getPremium().getPremiumAmt() ) ));
				premiumQuo.setPrmPremiumActual( new BigDecimal( String.valueOf(equipmentVO.getPremium().getPremiumAmt() ) ));
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		else{
			setZeroPrmValue( premiumQuo );
		}
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );
		logger.debug( "EESaveDao :: Premium Pojo -- "+ premiumQuo);
		return premiumQuo;
	}

	/**
	 * Sets PRM_RATE_TYPE field value within TTrnPremiumQuo POJO
	 * 
	 * @param policyVO
	 * @param prmPOJO
	 */
	protected void setRateTypeToPremiumPOJO( PolicyVO policyVO, POJO prmPOJO ){
		if( Utils.isEmpty( policyVO.getScheme().getTariffRateType() ) ) DAOUtils.setRateTypeFromRatingTable( getHibernateTemplate(), policyVO );

		if( !Utils.isEmpty( prmPOJO ) ){
			if( prmPOJO instanceof TTrnPremiumQuo ){
				TTrnPremiumQuo tTrnPrmPOJO = (TTrnPremiumQuo) prmPOJO;
				tTrnPrmPOJO.setPrmRateType( policyVO.getScheme().getTariffRateType() );
			}
		}
	}

	/**
	 * This method populates the TTrnContentQuo (T_TRN_CONTENT_QUO) with all
	 * required details like sum equipment type , equipment description and so for the
	 * for  Electronic Equipment (EquipemtnVO)
	 * 
	 * Sets the building id to content basic risk id
	 * 
	 * @param tTrnBuildingQuo - 
	 * @param policyVO
	 * @param depsVO - 
	 * 			object array of BaseVO which contains SectionVO	(for EE), EquipmentVO , UWDetails (EEUWDetailsVO)
	 * @param occupancy 
	 * @return TTrnContentQuo
	 */
	private TTrnContentQuo getContentPojo( POJO buildingOrPremise, PolicyVO policyVO, BaseVO[] depsVO, Long buildingId, TMasOccupancy occupancy ){
		TTrnContentQuo contentQuo = null;
		TTrnContentQuoId cId = null;
		TTrnBuildingQuo ttrnBuildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;

		if( buildingOrPremise instanceof TTrnBuildingQuo )
			ttrnBuildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else if(buildingOrPremise instanceof TTrnWctplPremiseQuo)
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;

		SectionVO eeSection = (SectionVO) depsVO[ 0 ];
		EquipmentVO equipmentVO = (EquipmentVO) depsVO[ 1 ];
		contentQuo = new TTrnContentQuo();

		EEUWDetailsVO eeuwDetailsVO = (EEUWDetailsVO) depsVO[ 2 ];
		contentQuo = BeanMapper.map( eeuwDetailsVO, contentQuo );

		if( !Utils.isEmpty( equipmentVO.getQuantity() ) && equipmentVO.getQuantity().equals( CommonConstants.DEFAULT_LOW_LONG.intValue() ) ){
			equipmentVO.setQuantity( null );
		}
		if( !Utils.isEmpty( equipmentVO.getContentId() ) && equipmentVO.getContentId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
			equipmentVO.setContentId( null );
		}

		BeanMapper.map( equipmentVO, contentQuo );

		if( !Utils.isEmpty( equipmentVO.getContentId() ) ){
			cId = new TTrnContentQuoId();
			cId.setCntContentId( equipmentVO.getContentId() );
			contentQuo.setId( cId );
		}
		contentQuo.setCntPolicyId( eeSection.getPolicyId() );
		if( !Utils.isEmpty( ttrnBuildingQuo ) ){
			contentQuo.setCntBasicRiskId( ttrnBuildingQuo.getId().getBldId() );
			contentQuo.setCntRiRskCode( ttrnBuildingQuo.getBldRiRskCode() );
			contentQuo.setCntTradeCode( ttrnBuildingQuo.getBldRskType() );
		}
		else{
			contentQuo.setCntBasicRiskId( premiseQuo.getId().getWbdId() );
			contentQuo.setCntRiRskCode( premiseQuo.getWbdRiRskCode() );
		}

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
		contentQuo.setCntRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_CODE ) ) );
		contentQuo.setCntRiskDtl( Long.valueOf( Utils.getSingleValueAppConfig( "EE_RISK_DETAIL" ) ) );
		contentQuo.setCntBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "EE_PRM_BASIC_RISK_CODE" ) ) );
		contentQuo.setCntCategory( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_TYPE_CODE ) ) );
		contentQuo.setCntEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		contentQuo.setCntValidityExpiryDate( SvcConstants.EXP_DATE );
		contentQuo.setCntStatus( EE_CONTENT_STATUS );

		if( !Utils.isEmpty( equipmentVO.getSumInsuredDetails() ) && !Utils.isEmpty( equipmentVO.getSumInsuredDetails().getSumInsured() ) ){

			contentQuo.setCntSumInsured(new BigDecimal( equipmentVO.getSumInsuredDetails().getSumInsured() ) );
		}
		logger.debug( "EESaveDao :: Content Pojo -- "+ contentQuo);
		return contentQuo;

	}

	/**
	 * sets the fields like prepared date , modified date for auditing purpose
	 * @param contentQuo
	 * @param policyVO
	 */
	private void setAuditDetailsforcontent( TTrnContentQuo contentQuo, PolicyVO policyVO ){
		Integer userId = SvcUtils.getUserId( policyVO );
		// TODO: Need to find a way to conditionally update the prepared by
		// columns. If its an update there is no need to update this column
		contentQuo.setCntPreparedBy( userId );
		contentQuo.setCntPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );

		contentQuo.setCntModifiedBy( userId );
		contentQuo.setCntModifiedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );

	}

	/**
	 * This methods decides whether the given content or premium is to created or updated.
	 */
	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		EquipmentVO equipmentVO = (EquipmentVO) depsVO[ 1 ];
		Boolean isToBeDeleted = false;
		if( !Utils.isEmpty( equipmentVO.getIsToBeDeleted() ) ){
			if( equipmentVO.getIsToBeDeleted() ){
				isToBeDeleted = true;
			}
		}

		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			Boolean isCreated = (Boolean) ThreadLevelContext.get( com.Constant.CONST_PRM_TO_BE_CREATED );
			ThreadLevelContext.clear( com.Constant.CONST_PRM_TO_BE_CREATED );
			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;

		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			if( Utils.isEmpty( equipmentVO.getContentId() ) && !isToBeDeleted ){
				ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, true );
				return true;
			}
		}
		ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, false );
		return false;

	}

	/**
	 * This methods decides whether the given content or premium is to deleted or not.
	 */
	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeDeleted = false;

		LocationVO locationDetails = (LocationVO) depsVO[ 3 ];
		SectionVO tbSection = (SectionVO) depsVO[ 0 ];
		EEVO eeDetails = (EEVO) PolicyUtils.getRiskGroupDetails( locationDetails, tbSection );

		if( Utils.isEmpty( eeDetails.getIsToBeDeleted() ) ) return false;
		/* deletion logic if any will come here */
		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
			EquipmentVO equipmentDetail = (EquipmentVO) depsVO[ 1 ];

			if( !Utils.isEmpty( equipmentDetail.getIsToBeDeleted() ) ){
				if( equipmentDetail.getIsToBeDeleted() ){
					isToBeDeleted = true;
					ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, true );
				}
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
	 * 
	 */
	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){
		SectionVO eeSection = PolicyUtils.getSectionVO( policyVO, EE_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( eeSection );
		EEVO eeDetails = (EEVO) PolicyUtils.getRiskGroupDetails( locationDetails, eeSection );
		EquipmentVO equipmentVO = (EquipmentVO) depsVO[ 1 ];
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnPremiumQuo ){
					updateKeyValuesToVOs( (TTrnPremiumQuo) mappedRecord, locationDetails, eeDetails, equipmentVO );
				}
			}
		}

	}

	/**
	 * Sets the premium basic risk id (building id) to LocationVOs risk group id and EEVOs basic risk id
	 * Sets the premium  risk id (content id) to EquipmentVOs content id for UI purpose
	 * 
	 * @param trnPremiumQuo
	 * @param locationDetails
	 * @param eeDetails
	 * @param equipmentVO
	 */
	private void updateKeyValuesToVOs( TTrnPremiumQuo trnPremiumQuo, LocationVO locationDetails, EEVO eeDetails, EquipmentVO equipmentVO ){
		locationDetails.setRiskGroupId( String.valueOf( trnPremiumQuo.getId().getPrmBasicRskId().longValue() ) );
		equipmentVO.setContentId( trnPremiumQuo.getId().getPrmRskId().longValue() );
		eeDetails.setBasicRiskId( trnPremiumQuo.getId().getPrmBasicRskId().longValue() );

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
				return id;
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
	protected void handleAdditionalCovers( SectionVO sectionVO, PolicyVO policyVO ){
		// TODO Auto-generated method stub

	}

	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){
		EEVO eeVO = (EEVO) rgd;
		Long basicRiskIdFromCurrRGD = eeVO.getBasicRiskId();

		/*
		 * If basic risk id is not set to ParVO for some reason, throw a
		 * exception as basic risk id is one of key to query premium table
		 */
		if( Utils.isEmpty( basicRiskIdFromCurrRGD ) ){
			throw new BusinessException( "cmn.basicRiskIdIsNull", null, "Basic Risk Id for RGD [ " + rgd.toString() + " ] is null" );
		}
		return basicRiskIdFromCurrRGD;
	}

	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails currRgd ){
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *  
	 * @param eeDetails
	 */
	private void updateSectionLevelSIAndPremium( EEVO eeDetails ){

		eeDetails.setSumInsured( getSectionLevelSumInsured( eeDetails ) );
		setSectionLevelPremium( eeDetails );

	}

	/**
	 * set the premium
	 * @param eeDetails
	 */
	private void setSectionLevelPremium( EEVO eeDetails ){

		if( Utils.isEmpty( eeDetails.getPremium() ) ){
			eeDetails.setPremium( new PremiumVO() );
		}
		PremiumHelper.getSectionLevelPremium( eeDetails );

	}

	/**
	 * sets sum insured
	 * @param eeDetails
	 * @return
	 */
	private double getSectionLevelSumInsured( EEVO eeDetails ){

		return PremiumHelper.getSectionLevelSumInsured( eeDetails );

	}

	@Override
	protected void sectionPreProcessing( PolicyVO policyVO ){
		// TODO Auto-generated method stub
		super.sectionPreProcessing( policyVO );
	}

	/**
	 * Does necessary actions after data  is persisted in the DB
	 */
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		SectionVO eeSection = PolicyUtils.getSectionVO( policyVO, EE_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( eeSection );
		EEVO eeDetails = (EEVO) PolicyUtils.getRiskGroupDetails( locationDetails, eeSection );
		removeDeletedRowsFromContext( eeDetails );
		updateSectionLevelSIAndPremium( eeDetails );
		updateEndtText( policyVO );
		super.sectionPostProcessing( policyVO );
	}

	/**
	 * updates the text for content removal , addition and modification
	 * @param policyVO
	 */
	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			SectionVO eeSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_EE );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( eeSection );

			DAOUtils.deletePrevEndtText( eeSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), getSectionId(),
					Long.valueOf( locationDetails.getRiskGroupId() ) );
			

			DAOUtils.updateEBCforendorsementFlow( eeSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), eeSection.getSectionId(),Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_TYPE_CODE ) ) );
			
			DAOUtils.updateContforendorsementFlow( eeSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), eeSection.getSectionId(),Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_TYPE_CODE ) ) );
			
			DAOUtils.deleteCntforEndorsementFlow( eeSection.getPolicyId(), policyVO, eeSection.getSectionId(), Long.valueOf( locationDetails.getRiskGroupId() ),Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_EE_RISK_TYPE_CODE ) ) );
			
			DAOUtils.updateDeductibleforendorsementFlow( eeSection.getPolicyId(), policyVO,eeSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId()) );			
			//DAOUtils.updateDeductibleforendorsementFlow( eeSection.getPolicyId(), policyVO,eeSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()));
			//DAOUtils.updateEndTextForRiskAdd( eeSection.getPolicyId(), policyVO,eeSection.getSectionId());
			DAOUtils.updateTotalSITextforendorsementFlow( eeSection.getPolicyId(), policyVO,eeSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );

		}

	}

	/* This method is used to remove the delted rows from context
	* 
	* @return rgd (This will be modified based on whether insertion or deletion
	*/
	protected void removeDeletedRowsFromContext( EEVO eeDetails ){
		try{
			if( Utils.isEmpty( eeDetails.getIsToBeDeleted() ) ) return;

			boolean deletionflag = false;
			ArrayList<EquipmentVO> toBeDeletedVOs = new ArrayList<EquipmentVO>();
			for( EquipmentVO equipmentVO : eeDetails.getEquipmentDtls() ){
				if( !Utils.isEmpty( equipmentVO.getIsToBeDeleted() ) && equipmentVO.getIsToBeDeleted() ){
					toBeDeletedVOs.add( equipmentVO );
					deletionflag = true;
				}
			}
			if( deletionflag ){
				for( EquipmentVO toBeDeletedVO : toBeDeletedVOs ){

					eeDetails.getEquipmentDtls().remove( toBeDeletedVO );
				}

			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}
}
