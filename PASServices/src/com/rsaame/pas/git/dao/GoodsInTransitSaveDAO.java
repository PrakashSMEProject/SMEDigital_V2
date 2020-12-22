package com.rsaame.pas.git.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnMarineDetailQuo;
import com.rsaame.pas.dao.model.TTrnMarineDetailQuoId;
import com.rsaame.pas.dao.model.TTrnMarineHeaderQuo;
import com.rsaame.pas.dao.model.TTrnMarineHeaderQuoId;
import com.rsaame.pas.dao.model.TTrnMarineTransitQuo;
import com.rsaame.pas.dao.model.TTrnMarineTransitQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPremiseQuo;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.CommodityDetailsVO;
import com.rsaame.pas.vo.bus.GoodsInTransitVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1016996
 * 
 * This Class is a DAO class for Goods In Transit Module.
 * Used to save and manipulate the data into the data base.
 */
public class GoodsInTransitSaveDAO extends BaseSectionSaveDAO implements IGoodsInTransitSaveDAO{

	private final static Logger LOGGER = Logger.getLogger( GoodsInTransitSaveDAO.class );
	private final static Integer GIT_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "GOODS_IN_TRANSIT_SECTION" ) );
	private final static String TRANSIT_SEQ = "SEQ_MARINE_DECLARATION_NO";
	private final static String DETAILS_SEQ = "SEQ_MARINE_COMMODITY_ID";
	private static final int TRANSIT_SERIAL_NO = 1;
	private static final long HEADER_OPEN_ID = 0;
	private static final Integer COMMODITY_GROUP_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "COMMODITY_GROUP_CODE" ) );
	private static final int GIT_CLASS_CODE = 4;
	private static final Integer GIT_RI_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "GIT_RI_RISK_CODE" ) );
	private static final Integer GIT_BASIC_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "GIT_BASIC_RISK_CODE" ) );
	private static final Integer GIT_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "GIT_RISK_CODE" ) );
	private static final Map<String, String> gitVoyageToDescMap = new Map<String, String>( null, null );
	private static final Map<String, String> gitVoyageFromDescMap = new Map<String, String>( null, null );
	

	@Override
	public BaseVO loadGITSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveGITSection( BaseVO baseVO ){

		return saveSection( baseVO );
	}

	@Override
	protected int getSectionId(){
		// TODO Auto-generated method stub
		return GIT_SECTION_ID;
	}

	@Override
	protected int getClassCode(){
		// TODO Auto-generated method stub
		return GIT_CLASS_CODE;
	}

	@Override
	protected BaseVO saveSection( BaseVO input ){

		PolicyVO policyVO = (PolicyVO) input;

		// SvcUtils.writeObjToFile( policyVO );

		/*
		 * Let us get the system date right now and use from here on for this
		 * transaction.
		 */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );
		
		SectionVO section = PolicyUtils.getSectionVO( policyVO, GIT_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) PolicyUtils.getRiskGroupDetails( locationDetails, section );
		getGITLookupMAP(policyVO);
		POJO buildingOrPremise = getBuildingOrPremiseRecord( policyVO, section, locationDetails, goodsInTransitVO );
		handleGITContent( policyVO, section, locationDetails, goodsInTransitVO, buildingOrPremise );

		return policyVO;
	}

	private void handleGITContent( PolicyVO policyVO, SectionVO gitSection, LocationVO locationDetails, GoodsInTransitVO goodsInTransitVO, POJO buildingOrPremise ){

		TTrnBuildingQuo buildingQuo = null;
		TTrnWctplPremiseQuo premiseQuo = null;
		TTrnPremiumQuo premiumQuo = null;
		Long bldId = null;

		if( buildingOrPremise instanceof TTrnBuildingQuo ){

			buildingQuo = (TTrnBuildingQuo) buildingOrPremise;
			bldId = buildingQuo.getId().getBldId();
		}
		else if(buildingOrPremise instanceof TTrnWctplPremiseQuo){
			
			premiseQuo = (TTrnWctplPremiseQuo) buildingOrPremise;
			bldId = premiseQuo.getId().getWbdId();
		}
		
		if(!Utils.isEmpty( bldId )){
			goodsInTransitVO.setBasicRiskId( bldId );
		}
		
		
		TTrnMarineTransitQuo marineTransitQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT, policyVO, new POJO[]{ buildingOrPremise }, new BaseVO[]{
			goodsInTransitVO, locationDetails }, false, gitSection.getPolicyId(),goodsInTransitVO.getDeclarationId() );
		
		handleTableRecord( SvcConstants.TABLE_ID_T_TRN_MARINE_HEADER, policyVO, new POJO[]{ marineTransitQuo }, new BaseVO[]{ goodsInTransitVO,locationDetails }, false, gitSection.getPolicyId(),marineTransitQuo
			.getId().getMtDeclarationId() );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, marineTransitQuo.getId().getMtDeclarationId());
		/*
		 * For every entry in the List, table entry should go into the corresponding tables
		 */
		int listSize = goodsInTransitVO.getCommodityDtls().size();
		int serialNo ;
		List<Integer> serilNoList ;
		for( int i = 0; i < listSize; i++ ){

			CommodityDetailsVO commodityDetailsVO = goodsInTransitVO.getCommodityDtls().get( i );

			/*TTrnMarineTransitQuo marineTransitQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT, policyVO, new POJO[]{ buildingOrPremise }, new BaseVO[]{
					goodsInTransitVO, commodityDetailsVO,locationDetails }, false, gitSection.getPolicyId(),commodityDetailsVO.getDeclarationId() );*/
			
			/*goodsInTransitVO.getCommodityDtls().get( i ).setDeclarationId( commodityDetailsVO.getDeclarationId() );*/

			/*handleTableRecord( SvcConstants.TABLE_ID_T_TRN_MARINE_HEADER, policyVO, new POJO[]{ marineTransitQuo }, new BaseVO[]{ goodsInTransitVO,commodityDetailsVO,locationDetails }, false, gitSection.getPolicyId(),marineTransitQuo
					.getId().getMtDeclarationId() );*/
			
			//serialNo = i + 1;
			
			serilNoList = getHibernateTemplate().find( "select coalesce(max(id.mdSerialNo),0) from TTrnMarineDetailQuo md where id.mdPolicyId = ? and id.mdDeclarationId = ?",gitSection.getPolicyId(),goodsInTransitVO.getDeclarationId());
			serialNo = serilNoList.get(0) + 1;
			
			ThreadLevelContext.set( SvcConstants.GIT_SERIAL_NO, serialNo);
			
			TTrnMarineDetailQuo marineDetailQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL, policyVO, new POJO[]{ marineTransitQuo }, new BaseVO[]{ goodsInTransitVO, commodityDetailsVO,locationDetails }, false,
					gitSection.getPolicyId(),marineTransitQuo.getId().getMtDeclarationId(),commodityDetailsVO.getCommodityId());
			
			goodsInTransitVO.getCommodityDtls().get( i ).setSerialNo( Long.valueOf(serialNo ));
			
			//ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, marineDetailQuo);
			
			premiumQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{ buildingOrPremise,marineTransitQuo,marineDetailQuo }, new BaseVO[]{ commodityDetailsVO,goodsInTransitVO,locationDetails }, false,gitSection.getPolicyId(),BigDecimal.valueOf( commodityDetailsVO.getCommodityId() ),  BigDecimal.valueOf( marineTransitQuo.getId().getMtDeclarationId()), Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER" ) ),
					Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_TYPE" ) ), Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_SUB_TYPE" ) ) );
			
			if( !Utils.isEmpty( premiumQuo ) ){
				if( !Utils.isEmpty( premiumQuo.getPrmPremium() ) ){
					PremiumVO premium = new PremiumVO();
					premium.setPremiumAmt( premiumQuo.getPrmPremium().doubleValue() );
					goodsInTransitVO.getCommodityDtls().get( i ).setPremium( premium );
				}
			}
		
		}

	}
	
	/**
	 * 
	 * @param policyVO PolicyVO object
	 * @param gitSection Current GIT section
	 * @param locationDetails Current Location details which we are processing
	 * @param goodsInTransitVO Risk group details related to above {@link LocationVO} VO.
	 * @return The POJO either of T_TRN_BUILDING instance or T_TRN_WCTPL_PREMISE.
	 */

	private POJO getBuildingOrPremiseRecord( PolicyVO policyVO, SectionVO gitSection, LocationVO locationDetails, GoodsInTransitVO goodsInTransitVO ){
		/*
		 * Check if PAR or PL is present, basicSectionID will contain the
		 * section id of either PAR or PL
		 */
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

		/*
		 * If PAR is the basic section, then we have to get the PAR building
		 * record and use it for the construction of the T_TRN_GACC_BUILDING
		 * record POJO. If PL is the basic section, then we have to use the
		 * WCTPL Premise record for this.
		 */
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
				// this pojo may not be required, since the id required in case
				// of par is not selected will be available in publicLiabilityVO
				try{
					premiseQuo = (TTrnWctplPremiseQuo) getHibernateTemplate().find( "from TTrnWctplPremiseQuo preQ where preQ.id.wbdId=?",
							Long.valueOf( locationDetails.getRiskGroupId() ) ).get( 0 );

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
		
		if( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT.equals( tableId ) ){
			Long bldId = null;

			TTrnBuildingQuo trnBuildingQuo = null;
			TTrnWctplPremiseQuo wctplPremiseQuo = null;

			if( !Utils.isEmpty( deps[ 0 ] ) ){

				if( deps[ 0 ] instanceof TTrnBuildingQuo ){
					trnBuildingQuo = (TTrnBuildingQuo) deps[ 0 ];
					bldId = trnBuildingQuo.getId().getBldId();
				}
				else if( deps[ 0 ] instanceof TTrnWctplPremiseQuo ){
					wctplPremiseQuo = (TTrnWctplPremiseQuo) deps[ 0 ];
					bldId = wctplPremiseQuo.getId().getWbdId();
				}
			}
			else if( !Utils.isEmpty( deps[ 1 ] ) ){

				if( deps[ 1 ] instanceof TTrnWctplPremiseQuo ){
					wctplPremiseQuo = (TTrnWctplPremiseQuo) deps[ 1 ];
					bldId = wctplPremiseQuo.getId().getWbdId();
				}
			}

			mappedPOJO = getMarineTransitPojo( policyVO, depsVO, bldId );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_HEADER.equals( tableId ) ){

			mappedPOJO = getMarineHeaderPojo( policyVO, (TTrnMarineTransitQuo) deps[ 0 ], (GoodsInTransitVO) depsVO[ 0 ] );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL.equals( tableId ) ){

			mappedPOJO = getMarineDetailPojo( policyVO, (TTrnMarineTransitQuo) deps[ 0 ], depsVO );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			mappedPOJO = getPremiumPojo( policyVO, deps, (CommodityDetailsVO) depsVO[ 0 ],(GoodsInTransitVO)depsVO[1] );
		}

		return mappedPOJO;
	}

	/**
	 * @param policyVO
	 * @param tTrnMarineTransitQuo
	 * @param depsVO
	 * @return instance of {@link TTrnMarineDetailQuo} after mapping from above parameters.
	 */
	private TTrnMarineDetailQuo getMarineDetailPojo( PolicyVO policyVO, TTrnMarineTransitQuo tTrnMarineTransitQuo, BaseVO[] depsVO ){
		//SectionVO section = PolicyUtils.getSectionVO( policyVO, GIT_SECTION_ID );

		GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) depsVO[ 0 ];
		CommodityDetailsVO commodityDetailsVO = (CommodityDetailsVO) depsVO[ 1 ];

		TTrnMarineDetailQuo marineDetailQuo = new TTrnMarineDetailQuo();
		TTrnMarineDetailQuoId id = new TTrnMarineDetailQuoId();

		id.setMdDeclarationId( tTrnMarineTransitQuo.getId().getMtDeclarationId() );
		/*id.setMdPolicyId( section.getPolicyId() );
		id.setMdEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		id.setMdSerialNo((Integer)ThreadLevelContext.get( SvcConstants.GIT_SERIAL_NO ));
		id.setMdCommodityId(NextSequenceValue.getNextSequence( DETAILS_SEQ, getHibernateTemplate() ));*/
		marineDetailQuo.setId( id );
		//marineDetailQuo.setMdCommodityId( NextSequenceValue.getNextSequence( DETAILS_SEQ, getHibernateTemplate() ) );
		marineDetailQuo.setMdCommodityGroupCode( COMMODITY_GROUP_CODE );
		marineDetailQuo.setMdCommodityCode( Integer.valueOf( commodityDetailsVO.getCommodityType() ) );
		marineDetailQuo.setValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		marineDetailQuo.setMdECommodityDesc( commodityDetailsVO.getGoodsDescription() );
		marineDetailQuo.setValidityExpiryDate( SvcConstants.EXP_DATE );
		marineDetailQuo.setStatus( SvcConstants.POL_STATUS_PENDING );
		marineDetailQuo.setMdPreparedBy( SvcUtils.getUserId( policyVO ) );
		marineDetailQuo.setMdPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		marineDetailQuo.setMdStartDate( policyVO.getScheme().getEffDate() );
		marineDetailQuo.setMdEndDate( policyVO.getEndDate() );
		
		if(!Utils.isEmpty( commodityDetailsVO.getSiBasis() )){
			marineDetailQuo.setMdSiIndicator( commodityDetailsVO.getSiBasis().intValue());
		}
		
		marineDetailQuo.setMdEstAnnualSi( goodsInTransitVO.getEstimatedAnnualCarryValue());
		marineDetailQuo.setMdRiRskCode(GIT_RI_RISK_CODE);
		marineDetailQuo.setMdBasicRiskCode( GIT_BASIC_RISK_CODE );
		marineDetailQuo.setMdRiskCode( GIT_RISK_CODE );

		return marineDetailQuo;
	}

	/**
	 * 
	 * @param policyVO
	 * @param tTrnMarineTransitQuo
	 * @param goodsInTransitVO
	 * @return instance of {@link TTrnMarineHeaderQuo} after mapping from above parameters.
	 */
	private POJO getMarineHeaderPojo( PolicyVO policyVO, TTrnMarineTransitQuo tTrnMarineTransitQuo, GoodsInTransitVO goodsInTransitVO ){

		SectionVO section = PolicyUtils.getSectionVO( policyVO, GIT_SECTION_ID );
		TTrnMarineHeaderQuo headerQuo = new TTrnMarineHeaderQuo();
		TTrnMarineHeaderQuoId id = new TTrnMarineHeaderQuoId();

		id.setMhDeclarationId( tTrnMarineTransitQuo.getId().getMtDeclarationId() );
		//id.setMhValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );

		headerQuo.setId( id );
		headerQuo.setMhPolicyId( section.getPolicyId() );
		headerQuo.setEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		headerQuo.setMhOpenId( HEADER_OPEN_ID );
		/*headerQuo.setMhSettlementCurrency( goodsInTransitVO.getSettlmentCurrency());
		headerQuo.setMhExchangeRate(  goodsInTransitVO.getExchangeRate()  );*/
		headerQuo.setMhStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
		headerQuo.setValidityExpiryDate( SvcConstants.EXP_DATE );
		/*headerQuo.setMhESettlementLoc( goodsInTransitVO.getSettlingLocation());*/
		headerQuo.setMhPreparedBy( SvcUtils.getUserId( policyVO ) );
		headerQuo.setMhPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		headerQuo.setMhStartDate( policyVO.getScheme().getEffDate() );
		headerQuo.setMhEndDate( policyVO.getPolExpiryDate() );
		headerQuo.setMhSingleTransitLimit( goodsInTransitVO.getSingleTransitLimit());//
		/*headerQuo.setMhSettlingAgent(goodsInTransitVO.getSettlingAgent());*/
		
		return headerQuo;
	}

	/**
	 * 
	 * @param policyVO
	 * @param depsVO
	 * @param bldId
	 * @return instance of {@link TTrnMarineTransitQuo} after mapping from above parameters.
	 */
	private POJO getMarineTransitPojo( PolicyVO policyVO, BaseVO[] depsVO, Long bldId ){
	    	GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) depsVO[ 0 ];
		CommodityDetailsVO commodityDetailsVO = goodsInTransitVO.getCommodityDtls().get(0);
		TTrnMarineTransitQuo marineTransitQuo = new TTrnMarineTransitQuo();
		
		marineTransitQuo.setMtModeOfTransit( Short.valueOf( commodityDetailsVO.getModeOfTransit() ) );
		
		if(gitVoyageFromDescMap.containsKey(goodsInTransitVO.getVoyageFrom())){
		    marineTransitQuo.setMtEStartPlace( gitVoyageFromDescMap.get(goodsInTransitVO.getVoyageFrom()));
		}
		if(gitVoyageToDescMap.containsKey(goodsInTransitVO.getVoyageTo())){
		    marineTransitQuo.setMtEDestinationPlace( gitVoyageToDescMap.get(goodsInTransitVO.getVoyageTo()));
		}
		
		//marineTransitQuo.setMtEStartPlace( goodsInTransitVO.getVoyageFrom() );
		//marineTransitQuo.setMtEDestinationPlace( goodsInTransitVO.getVoyageTo() );
			
		marineTransitQuo.setMtStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
		//marineTransitQuo.setMtValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		marineTransitQuo.setValidityExpiryDate( SvcConstants.EXP_DATE );

		marineTransitQuo.setMtPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		marineTransitQuo.setMtPreparedBy( SvcUtils.getUserId( policyVO ) );
		marineTransitQuo.setMtStartDate( policyVO.getScheme().getEffDate() );
		marineTransitQuo.setMtEndDate( policyVO.getEndDate() );
		marineTransitQuo.setMtBldId( bldId );

		return marineTransitQuo;
	}

	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
				
		
		if( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT.equals( tableId ) ){
			GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) depsVO[0];
			if(Utils.isEmpty( goodsInTransitVO.getDeclarationId()) || goodsInTransitVO.getDeclarationId()<0){
				ThreadLevelContext.set( "TO_BE_CREATED_T_TRN_MARINE_HEADER", true );
				return true;
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_HEADER.equals( tableId ) ){
			Boolean isCreated = (Boolean) ThreadLevelContext.get( "TO_BE_CREATED_T_TRN_MARINE_HEADER" );
			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL.equals( tableId ) ){
		    	CommodityDetailsVO commodityDetailsVO = (CommodityDetailsVO) depsVO[1];
		    	if( Utils.isEmpty(commodityDetailsVO.getCommodityId() ) || commodityDetailsVO.getCommodityId() < 0 ){
		    	    ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, true );
		    	    return true ;
		    	}			
		}		
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			Boolean isCreated = (Boolean) ThreadLevelContext.get( com.Constant.CONST_PRM_TO_BE_CREATED );
			ThreadLevelContext.clear( com.Constant.CONST_PRM_TO_BE_CREATED );			
			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;
		}

		//ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, false );
		return false;
	}

	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeDeleted = false;
		LocationVO locationDetails = null;
		
        	if (SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL.equals(tableId)
        		|| SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals(tableId)) {
        	    locationDetails = (LocationVO) depsVO[2];
        	} else {
        	    locationDetails = (LocationVO) depsVO[1];
        	}
		
		//SectionVO gitSection = (SectionVO) depsVO[ 0 ];
		SectionVO gitSection = PolicyUtils.getSectionVO( policyVO, GIT_SECTION_ID );
		GoodsInTransitVO gitDetails = (GoodsInTransitVO) PolicyUtils.getRiskGroupDetails( locationDetails, gitSection );

		if( Utils.isEmpty( gitDetails.getIsToBeDeleted() ) ) return false;
		
		/*if( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
			CommodityDetailsVO commodityDetailsVO = (CommodityDetailsVO) depsVO[ 1 ];

			if( !Utils.isEmpty( commodityDetailsVO.getIsToBeDeleted() ) ){
				if( commodityDetailsVO.getIsToBeDeleted() ){
					isToBeDeleted = true;
					ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, true );
				}
			}

		}
		else*/ 
		if( SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
			CommodityDetailsVO commodityDetailsVO = (CommodityDetailsVO) depsVO[ 1 ];

			if( !Utils.isEmpty( commodityDetailsVO.getIsToBeDeleted() ) ){
				if( commodityDetailsVO.getIsToBeDeleted() ){
					isToBeDeleted = true;
					ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, true );
				}
			}

		}
		/*else if( SvcConstants.TABLE_ID_T_TRN_MARINE_HEADER.equals( tableId ) ){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
			CommodityDetailsVO commodityDetailsVO = (CommodityDetailsVO) depsVO[ 1 ];

			if( !Utils.isEmpty( commodityDetailsVO.getIsToBeDeleted() ) ){
				if( commodityDetailsVO.getIsToBeDeleted() ){
					isToBeDeleted = true;
					ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, true );
				}
			}

		}*/
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			isToBeDeleted = ( (Boolean) ThreadLevelContext.get( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED ) ) ? true : false;
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, null );
		}
		
		return isToBeDeleted;
	}

	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){

		if( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT.equals( tableId ) ){
			GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) depsVO[0];
			TTrnMarineTransitQuo ttrnMarineTransitQuo = null;
			
			
			if(mappedRecord instanceof TTrnMarineTransitQuo){
				
				ttrnMarineTransitQuo = (TTrnMarineTransitQuo)mappedRecord;
			}
			if(!Utils.isEmpty( ttrnMarineTransitQuo )){
			    goodsInTransitVO.setDeclarationId(ttrnMarineTransitQuo.getId().getMtDeclarationId());
			}	
		}else if(SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL.equals( tableId )){
			CommodityDetailsVO commodityDetailsVO = (CommodityDetailsVO) depsVO[1];
			TTrnMarineDetailQuo trnMarineDetailQuo = null;
			if(mappedRecord instanceof TTrnMarineDetailQuo){
				
			    trnMarineDetailQuo = (TTrnMarineDetailQuo)mappedRecord;
			}
			if(!Utils.isEmpty( trnMarineDetailQuo )){
			    commodityDetailsVO.setCommodityId(trnMarineDetailQuo.getId().getMdCommodityId());
			}
		}
		
	}

	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

	}

	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){

		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
	
	}

	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){
		POJOId id = null;
		SectionVO section = PolicyUtils.getSectionVO( policyVO, GIT_SECTION_ID );
		if( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT.equals( tableId ) ){
			TTrnMarineTransitQuoId transitId = new TTrnMarineTransitQuoId();

			transitId.setMtPolicyId( section.getPolicyId() );
			transitId.setMtEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
			transitId.setMtDeclarationId( NextSequenceValue.getNextSequence( TRANSIT_SEQ, null,null,getHibernateTemplate() ) );
			transitId.setMtSerialNo( TRANSIT_SERIAL_NO );
			transitId.setMtValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ));
			id = transitId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_HEADER.equals( tableId ) ){
			id =(TTrnMarineHeaderQuoId) mappedRecord.getPOJOId();
			((TTrnMarineHeaderQuoId) id).setMhValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ));
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL.equals( tableId ) ){
			id = (TTrnMarineDetailQuoId)mappedRecord.getPOJOId();
			
			
			//TTrnMarineDetailQuoId detailQuoId = new TTrnMarineDetailQuoId();
			
			((TTrnMarineDetailQuoId) id).setMdPolicyId( section.getPolicyId() );
			((TTrnMarineDetailQuoId) id).setMdEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
			((TTrnMarineDetailQuoId) id).setMdSerialNo((Integer)ThreadLevelContext.get( SvcConstants.GIT_SERIAL_NO ));
			((TTrnMarineDetailQuoId) id).setMdCommodityId(NextSequenceValue.getNextSequence( DETAILS_SEQ, null,null,getHibernateTemplate() ));
			((TTrnMarineDetailQuoId) id).setMdValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ));
			
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
		    	TTrnPremiumQuo premiumQuo = null;
			if( mappedRecord instanceof TTrnPremiumQuo ){
				premiumQuo = (TTrnPremiumQuo) mappedRecord;

				TTrnPremiumQuoId pId = premiumQuo.getId();
				pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
				id = pId;
			}
		}

		return id;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){
		// TODO Auto-generated method stub
		POJOId id = null;

		if( SvcConstants.TABLE_ID_T_TRN_MARINE_TRANSIT.equals( tableId ) ){
			TTrnMarineTransitQuoId transitQuoId = (TTrnMarineTransitQuoId)CopyUtils.copySerializableObject( existingId );
			transitQuoId.setMtValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ));
			id = transitQuoId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_HEADER.equals( tableId ) ){
			TTrnMarineHeaderQuoId marineHeaderQuoId = (TTrnMarineHeaderQuoId)CopyUtils.copySerializableObject( existingId );
			marineHeaderQuoId.setMhValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD )  );
			id = marineHeaderQuoId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_MARINE_DETAIL.equals( tableId ) ){
			TTrnMarineDetailQuoId marineDetailQuoId = (TTrnMarineDetailQuoId)CopyUtils.copySerializableObject( existingId );
			marineDetailQuoId.setMdValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = CopyUtils.copySerializableObject( existingId );
		}
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			TTrnPremiumQuoId pId = new TTrnPremiumQuoId();
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
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

	private TTrnPremiumQuo getPremiumPojo( PolicyVO policyVO, POJO[] deps, CommodityDetailsVO commodityDetailsVO,GoodsInTransitVO goodsInTransitVO ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "GIT_BASIC_RISK_CODE" ) ) );
		TTrnMarineDetailQuo detailQuo = null;
		if(deps[ 1 ] instanceof TTrnMarineTransitQuo){
			TTrnMarineTransitQuo marineTransitQuo = (TTrnMarineTransitQuo) deps[1];
			premiumQuoId.setPrmBasicRskId(  BigDecimal.valueOf( marineTransitQuo.getId().getMtDeclarationId()) );
		}
		
		if(deps[ 2 ] instanceof TTrnMarineDetailQuo){
		    	detailQuo = (TTrnMarineDetailQuo) deps[2];
			premiumQuoId.setPrmRskId( BigDecimal.valueOf( detailQuo.getId().getMdCommodityId()));
		}
		
			
		SectionVO gitSection = PolicyUtils.getSectionVO( policyVO, GIT_SECTION_ID );
		premiumQuoId.setPrmPolicyId( gitSection.getPolicyId() );

		/* All the contents will now be associated with cover code, cover type, cover sub type , risk type , 
		 * risk category and risk sub category when rendered on the UI */

		premiumQuoId.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER" ) ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_TYPE" ) ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( Utils.getSingleValueAppConfig( "GIT_COVER_SUB_TYPE" ) ) );

		Long bldId = null;
		Integer riRiskCode = null;

		TTrnBuildingQuo trnBuildingQuo = null;
		TTrnWctplPremiseQuo wctplPremiseQuo = null;

		if( !Utils.isEmpty( deps[ 0 ] ) ){

			if( deps[ 0 ] instanceof TTrnBuildingQuo ){
				trnBuildingQuo = (TTrnBuildingQuo) deps[ 0 ];
				bldId = trnBuildingQuo.getId().getBldId();
				riRiskCode = trnBuildingQuo.getBldRiRskCode();
			}
			else if( deps[ 0 ] instanceof TTrnWctplPremiseQuo ){
				wctplPremiseQuo = (TTrnWctplPremiseQuo) deps[ 0 ];
				bldId = wctplPremiseQuo.getId().getWbdId();
				riRiskCode = wctplPremiseQuo.getWbdRiRskCode();
			}
		}
		else if( !Utils.isEmpty( deps[ 1 ] ) ){

			if( deps[ 1 ] instanceof TTrnWctplPremiseQuo ){
				wctplPremiseQuo = (TTrnWctplPremiseQuo) deps[ 1 ];
				bldId = wctplPremiseQuo.getId().getWbdId();
				riRiskCode = wctplPremiseQuo.getWbdRiRskCode();
			}
		}

		/*if(!Utils.isEmpty( bldId )){
			premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( bldId )  );
		}*/
		
		premiumQuoId.setPrmRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "GIT_RISK_CODE" ) ) );
		if(!Utils.isEmpty( detailQuo ))
		premiumQuoId.setPrmValidityStartDate( detailQuo.getId().getMdValidityStartDate() );
		premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( goodsInTransitVO.getDeductible() ) );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
		premiumQuo.setPrmSumInsured(new BigDecimal( goodsInTransitVO.getEstimatedAnnualCarryValue()));
		premiumQuo.setId( premiumQuoId );
		premiumQuo.setPrmClCode( new Short( String.valueOf( SvcConstants.CLASS_ID_GIT ) ) );
		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyVO, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		premiumQuo.setPrmStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
		premiumQuo.setPrmSumInsuredCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmRiLocCode( Integer.parseInt( Utils.getSingleValueAppConfig( "GIT_PRM_RI_LOC_CODE" ) ) );
		premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		premiumQuo.setPrmEffectiveDate( policyVO.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyVO.getEndDate() );
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmRiRskCode( riRiskCode );
		
		if(!Utils.isEmpty(detailQuo) && !Utils.isEmpty(detailQuo.getMdCommodityCode())){		/* Added additional null check for detailQuo in if condition - sonar violation fix */
		    premiumQuo.setPrmRtCode(detailQuo.getMdCommodityCode());
		}
		
		//premiumQuo.setPrmRtCode( Byte.valueOf( Utils.getSingleValueAppConfig( "GIT_RT_CODE" ) ) );
		
		if( !Utils.isEmpty( commodityDetailsVO ) ){
			if( !Utils.isEmpty( commodityDetailsVO.getPremium() ) ){
				if( !Utils.isEmpty( commodityDetailsVO.getPremium().getPremiumAmt() ) ){
					premiumQuo.setPrmPremium( new BigDecimal(String.valueOf(commodityDetailsVO.getPremium().getPremiumAmt() ) ));
					premiumQuo.setPrmPremiumActual( new BigDecimal(String.valueOf( commodityDetailsVO.getPremium().getPremiumAmt() ) ));
				}
				else{
					setZeroPrmValue( premiumQuo );
				}
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		setRateTypeToPremiumPOJO( policyVO, premiumQuo);

		return premiumQuo;

	}
	
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
			
		updateSectionLevelSIAndPremium( policyVO );
		updateEndtText( policyVO );
		super.sectionPostProcessing( policyVO );
	}
	
	/**
	 * Updating section Level Sum Insured and Premium value.
	 */
	private void updateSectionLevelSIAndPremium( PolicyVO policyVO){

		SectionVO gitSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_GIT );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( gitSection );
		GoodsInTransitVO goodsInTransitVO = (GoodsInTransitVO) PolicyUtils.getRiskGroupDetails( locationDetails, gitSection );
		removeDeletedRowsFromContext( goodsInTransitVO );
		goodsInTransitVO.setSumInsured( getSectionLevelSumInsured( goodsInTransitVO ) );
		setSectionLevelPremium( goodsInTransitVO);

	}
	
	/**
	 *  @param goodsInTransitVO
	 *  Used to remove the deleted rows from the Context. to avoid the duplicate calculation for Premium.
	 */
	protected void removeDeletedRowsFromContext( GoodsInTransitVO goodsInTransitVO ){
		try{
			if( Utils.isEmpty( goodsInTransitVO.getIsToBeDeleted() ) ) return;

			boolean deletionflag = false;
			ArrayList<CommodityDetailsVO> toBeDeletedVOs = new ArrayList<CommodityDetailsVO>();
			for( CommodityDetailsVO commodityDetailsVO : goodsInTransitVO.getCommodityDtls() ){
				if( !Utils.isEmpty( commodityDetailsVO.getIsToBeDeleted() ) && commodityDetailsVO.getIsToBeDeleted() ){
					toBeDeletedVOs.add( commodityDetailsVO );
					deletionflag = true;
				}
			}
			if( deletionflag ){
				for( CommodityDetailsVO toBeDeletedVO : toBeDeletedVOs ){
					goodsInTransitVO.getCommodityDtls().remove( toBeDeletedVO );
				}

			}

		}
		catch( Exception e ){
			throw new BusinessException( "cmn.compareError", null, "Error in compare" );
		}
	}

	/**
	 * @param goodsInTransitVO
	 * Updating section Level premium.
	 */
	private void setSectionLevelPremium( GoodsInTransitVO goodsInTransitVO ){
		Double premiumAmnt = 0.0; 
		
		if(!Utils.isEmpty( goodsInTransitVO.getCommodityDtls() )){
			
			for(CommodityDetailsVO commodityDetailsVO:goodsInTransitVO.getCommodityDtls()){
				premiumAmnt +=commodityDetailsVO.getPremium().getPremiumAmt();
			}
		}
		
		if( Utils.isEmpty( goodsInTransitVO.getPremium() ) ){
			goodsInTransitVO.setPremium( new PremiumVO() );
		}
		
		goodsInTransitVO.getPremium().setPremiumAmt( premiumAmnt );
	}

	/**
	 * @param goodsInTransitVO
	 * @return  the Total Sum Insured for the section.
	 *  
	 */
	private double getSectionLevelSumInsured( GoodsInTransitVO goodsInTransitVO ){
		
		return goodsInTransitVO.getEstimatedAnnualCarryValue();
	}
	
	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) )
		{
			SectionVO gitSection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( gitSection );
			GoodsInTransitVO gitDetails = (GoodsInTransitVO) PolicyUtils.getRiskGroupDetails( locationDetails, gitSection );
			
			DAOUtils.deletePrevEndtText( gitSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),GIT_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			DAOUtils.deletePrevEndtText( gitSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),GIT_SECTION_ID, Long.valueOf(gitDetails.getDeclarationId()) );
			/*
			 * Endorsement text generation for addition.
			 */
			
				LOGGER.debug( "call  Pro_Endt_Text_Md_Add" );
				DAOUtils.addGITMDforendorsementFlow( gitSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ) );
			

				LOGGER.debug( "call  pro_endt_text_mh_add" );
				DAOUtils.addGITMHforendorsementFlow( gitSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ) );
		
			
				LOGGER.debug( "call pro_endt_text_mt_add" );
				DAOUtils.addGITMTforendorsementFlow( gitSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			
			
			/*
			 * Endorsement text generation for deletion.
			 */
			
				LOGGER.debug( "call  pro_endt_text_md_del" );
				DAOUtils.deleteGITMDforendorsementFlow( gitSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ) );
			
				LOGGER.debug( "call  pro_endt_text_mh_del" );
				DAOUtils.deleteGITMHforendorsementFlow( gitSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			
				LOGGER.debug( "call pro_endt_text_mt_del" );
				DAOUtils.deleteGITMTforendorsementFlow( gitSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ) );				
			
			/*
			 * Endorsement text generation for modification.
			 */
			
			for(CommodityDetailsVO commodityDetailsVO : gitDetails.getCommodityDtls()){				
				
					LOGGER.debug( "call  Pro_Endt_Text_md_Col" );
					DAOUtils.updateGITMDforendorsementFlow( gitSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), commodityDetailsVO.getCommodityId() );				
			}
			
			LOGGER.debug( "call Pro_Endt_Text_mh_Col " );
			DAOUtils.updateGITMHforendorsementFlow( gitSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), gitDetails.getDeclarationId() );
			
			LOGGER.debug( "call pro_endt_text_mt_Col" );
			DAOUtils.updateGITMTforendorsementFlow( gitSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ),  gitDetails.getDeclarationId() );
			
			
			//LOGGER.debug( "call Risk Add changes change endo SP" );
			//DAOUtils.updateEndTextForRiskAdd( gitSection.getPolicyId(), policyVO,gitSection.getSectionId());
			
			LOGGER.debug( "call deductible change endo SP" );
			DAOUtils.updateDeductibleforendorsementFlow( gitSection.getPolicyId(), policyVO,gitSection.getSectionId(),  Long.valueOf(gitDetails.getDeclarationId()),Long.valueOf( locationDetails.getRiskGroupId() ) );
						
			DAOUtils.updateTotalSITextforendorsementFlow( gitSection.getPolicyId(), policyVO,gitSection.getSectionId(),   Long.valueOf(gitDetails.getDeclarationId()),Long.valueOf( locationDetails.getRiskGroupId() )  );
		
			
		}
	}
	protected void getGITVoyageFromDescMap( PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "VOYAGE_FROM", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitVoyageFromDescMap.put( luVO.getCode().toString(),luVO.getDescription() );
		}
	}
	
	protected void getGITVoyageToDescMap(PolicyVO policyVO ){
		LookUpListVO listVO = SvcUtils
				.getLookUpCodesList( "VOYAGE_TO", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gitVoyageToDescMap.put( luVO.getCode().toString(),luVO.getDescription() );
		}
	}
	private void getGITLookupMAP(PolicyVO policyVO ){
		
		getGITVoyageFromDescMap(policyVO);
		getGITVoyageToDescMap(policyVO);
		
	}
}
