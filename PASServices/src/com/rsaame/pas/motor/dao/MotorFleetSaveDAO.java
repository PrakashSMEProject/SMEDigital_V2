package com.rsaame.pas.motor.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnVehicleQuo;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MotorFleetVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.VehicleDetailsVO;

/**
 * 
 * @author m1014438
 * 
 */

public class MotorFleetSaveDAO extends BaseSectionSaveDAO implements IMotorFleetSectionDAO{
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	/*private final static Integer MOTOR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "MOTORFLEET_SECTION" ) );*/

	@Override
	protected int getSectionId(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getClassCode(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected BaseVO saveSection( BaseVO input ){
		PolicyVO policyVO = (PolicyVO) input;
		/*
		 * Let us get the system date right now and use from here on for this
		 * transaction.
		 */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

		handleMotorFleet( policyVO );

		return policyVO;
	}

	private void handleMotorFleet( PolicyVO policyVO ){

		SectionVO motorSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MOTOR );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( motorSection );
		MotorFleetVO fleetVO = (MotorFleetVO) PolicyUtils.getRiskGroupDetails( locationDetails, motorSection );
		handleVehicle( policyVO, motorSection, locationDetails, fleetVO );

	}

	private void handleVehicle( PolicyVO policyVO, SectionVO motorSection, LocationVO locationDetails, MotorFleetVO fleetVO ){

		/*TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TTrnPremiumQuo premiumQuo = null;*/
		POJO buildingOrPremise = getBuildingOrPremiseRecord( policyVO, motorSection, locationDetails, fleetVO );
		/*if( buildingOrPremise instanceof TTrnBuildingQuo )
			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;*/

		for( VehicleDetailsVO detailsVO : fleetVO.getVehicleDetails() ){

			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_VEHICLE, policyVO, new POJO[]{ buildingOrPremise }, new BaseVO[]{ motorSection, fleetVO, locationDetails }, false );
		}

	}

	private POJO getBuildingOrPremiseRecord( PolicyVO policyVO, SectionVO motorSection, LocationVO locationDetails, MotorFleetVO fleetVO ){

		Integer basicSectionID = null;
		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		POJO buildingOrPremise = null;

		/*
		 * If PAR is the base section, then we have to use the building details
		 * of PAR. Check if par or pl is present, basicSectionID will contain
		 * the section id of either par or pl
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

	private boolean isSectionPresent( Integer sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}

	protected TTrnVehicleQuo getVehiclePOJO( POJO buildingOrPremise, PolicyVO policyVO, BaseVO[] depsVO, Long buildingId ){
		TTrnVehicleQuo trnVehicleQuo = null;
		/*TTrnVehicleQuoId quoId = null;
		TTrnBuildingQuo ttrnBuildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;

		if( buildingOrPremise instanceof TTrnBuildingQuo )
			ttrnBuildingQuo = (TTrnBuildingQuo) buildingOrPremise;
		else
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;
*/
		/*SectionVO motorSection = (SectionVO) depsVO[ 0 ];*/
		VehicleDetailsVO vehicleDetailVO = (VehicleDetailsVO) depsVO[ 1 ];
		trnVehicleQuo = new TTrnVehicleQuo();
		trnVehicleQuo.setVehBldId( buildingId );
		trnVehicleQuo.setVehModelEDesc( vehicleDetailVO.getVehicleModel() );
		trnVehicleQuo.setVehRskCode( vehicleDetailVO.getVehicleCategory() );
		//trnVehicleQuo.setVehYearOfMan(vehicleDetailVO.getVehicleYearofMfg()); // String/Date??
		trnVehicleQuo.setVehIev( BigDecimal.valueOf( ( vehicleDetailVO.getVehicleIEV() ) ) );
		trnVehicleQuo.setVehSeats( Short.valueOf( vehicleDetailVO.getVehicleSeatCapacity().toString() ) );

		return trnVehicleQuo;

	}

	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){

		POJO mappedPOJO = null;
		TTrnVehicleQuo trnVehicleQuo = null;
		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		POJO buildingOrPremise = deps[ 0 ];

		/*LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( (SectionVO) depsVO[ 0 ] );*/

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
		if( SvcConstants.TABLE_ID_T_TRN_VEHICLE.equals( tableId ) ){
			trnVehicleQuo = getVehiclePOJO( deps[ 0 ], policyVO, depsVO, buildingId );
			mappedPOJO = trnVehicleQuo;
		}
		return mappedPOJO;

	}

	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){
		// TODO Auto-generated method stub
		return null;
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
	public BaseVO loadMotorSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveMotorSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

}
