package com.rsaame.pas.wc.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TMasOccupancy;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuo;
import com.rsaame.pas.dao.model.TTrnGaccPersonQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnWctplPersonQuo;
import com.rsaame.pas.dao.model.TTrnWctplPersonQuoId;
import com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo;
import com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuoId;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WCVO;

public class WCDAO   extends BaseSectionSaveDAO  implements IWCSectionDAO {

	@Override
	protected void handleAdditionalCovers( SectionVO sectionVO, PolicyVO policyVO ){
		/* As per present functionality implementation of this method is not required. */
	}
	private final static Logger LOGGER = Logger.getLogger( WCDAO.class );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	private final static Integer WC_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" ) );
	private final static String WCTPLUP_SEQ = "seq_wctpl_person_id";
	private final static String WCTPL_SEQ = "SEQ_WCTPL_PERSON_ID";
	private static final int WC_BASIC_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_RISK_CODE" ) );
	private static final int WC_RISK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CODE" ) );
	private static final short WC_BASIC_COVER = Short.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) );
	private static final short WC_COVER_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_TYPE" ) );
	private static final short WC_COVER_SUB_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_SUB_TYPE" ) );
	private static final short WC_CLASS = Short.valueOf( Utils.getSingleValueAppConfig( "WC_CLASS" ) );
	private static final short WC_RISK_CATEGORY = Short.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CATEGORY" ) );
	private final static Integer WC_CRITERIA_CODE=Integer.valueOf( Utils.getSingleValueAppConfig( "WC_CRITERIA_CODE" ) );

	
	@Override
	protected int getSectionId() {
		return SvcConstants.SECTION_ID_WC;
	}

	@Override
	protected int getClassCode() {
		return SvcConstants.CLASS_ID_WC;
	}

	@Override
	protected BaseVO saveSection(BaseVO input) {
		PolicyVO policyVO = (PolicyVO) input;
		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_WC );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );
		WCVO wcVO = (WCVO) PolicyUtils.getRiskGroupDetails( locationDetails, sectionVO );
		/* Let us get the system date right now and use from here on for this transaction. */
		/* Not required to set it again as it is already set within BaseSectionSaveDAO */
		//ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );
		
		handleUnnamedPerson( policyVO );
		if( !Utils.isEmpty( wcVO ) )
		{
			if( !Utils.isEmpty( wcVO.getWcEmployeeDetails() ) )
			{
				if( !Utils.isEmpty( wcVO.getWcEmployeeDetails().get( 0 ) ) && 
						!Utils.isEmpty( wcVO.getWcEmployeeDetails().get( 0 ).getEmpName() ) )
				{
						handleNammedPerson(policyVO);
				}
			}
		}
		return policyVO;
	}

	private void handleNammedPerson(PolicyVO policyVO) {
		/* (a) Handle T_TRN_WCTPL_PERSON(_QUO) record.
		 * 		(i)  For this we need T_MAS_OCCUPANCY record and T_TRN_BUILDING record, if PAR is present. 
		 *  */
		SectionVO section = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_WC );
		LocationVO location = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		WCVO wcDetails = (WCVO) PolicyUtils.getRiskGroupDetails( location, section );
	
		java.util.List<WCNammedEmployeeVO> nammedEmployeeDetailsVO = wcDetails.getWcEmployeeDetails();
		TTrnWctplPersonQuo tTrnWctplPersonQuo = null;

		/* T_MAS_OCCUPANCY record */
		TMasOccupancy occupancy = getOccDetails( Short.valueOf( location.getOccTradeGroup().toString() ) );
		
		/* If PAR is the base section, then we have to use the building details of PAR. */		
		TTrnBuildingQuo buildingQuo = null;
		{ /* Block for fetching PAR building record */
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PAR );
			
			if( !Utils.isEmpty( parSection ) ){
				//LocationVO parLocation = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );		
				buildingQuo = (TTrnBuildingQuo) getHibernateTemplate().find( "from TTrnBuildingQuo buldQ where buldQ.id.bldId=? and buldQ.bldValidityExpiryDate=?", Long.valueOf( location.getRiskGroupId() ) ,SvcConstants.EXP_DATE ).get(
							0 );	
			}
		}
		
		for( WCNammedEmployeeVO employeeDetailsVO : nammedEmployeeDetailsVO ){
			
			if( !Utils.isEmpty( employeeDetailsVO.getWprWCId() ) ){
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnWctplPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON, policyVO, new POJO[]{ buildingQuo, occupancy }, new BaseVO[]{ location, wcDetails, section,
						employeeDetailsVO }, false, section.getPolicyId(), Long.valueOf( location.getRiskGroupId() ), employeeDetailsVO.getWprWCId() );
			}
			else{
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				tTrnWctplPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON, policyVO, new POJO[]{ buildingQuo, occupancy }, new BaseVO[]{ location, wcDetails, section,
						employeeDetailsVO }, false, section.getPolicyId(), Long.valueOf( location.getRiskGroupId() ) );
			}	
		}
		
	}

	
	/**
	 * Handle T_TRN_WCTPL_UNNAMED_PERSON(_QUO) record and corresponding T_TRN_PREMIUM(_QUO) record.
	 * @param policyVO
	 */
	private void handleUnnamedPerson(PolicyVO policyVO) {
		
		/* (a) Handle T_TRN_WCTPL_UNNAMED_PERSON(_QUO) record.
		 * 		(i)  For this we need T_MAS_OCCUPANCY record and T_TRN_BUILDING record, if PAR is present. 
		 * (b) Handle the corresponding T_TRN_PREMIUM(_QUO) record. */
		SectionVO section = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_WC );
		LocationVO location = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		WCVO wcDetails = (WCVO) PolicyUtils.getRiskGroupDetails( location, section );
	
		/* If PAR is the base section, then we have to use the building details of PAR. */		
		TTrnBuildingQuo buildingQuo = null;
		{ /* Block for fetching PAR building record */
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PAR );
			
			if( !Utils.isEmpty( parSection ) ){
				//LocationVO parLocation = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );		
				buildingQuo = (TTrnBuildingQuo) getHibernateTemplate().find( "from TTrnBuildingQuo buldQ where buldQ.id.bldId=? and buldQ.bldValidityExpiryDate=?", Long.valueOf( location.getRiskGroupId() ) ,SvcConstants.EXP_DATE ).get(
							0 );	
			}
		}
		
		/* T_MAS_OCCUPANCY record */
		TMasOccupancy occupancy = getOccDetails( Short.valueOf( location.getOccTradeGroup().toString() ) );
		
		List<EmpTypeDetailsVO> empTypeDetsList = wcDetails.getEmpTypeDetails();

		/* Process the Unnamed Person data for all configured contents */
		for( EmpTypeDetailsVO empTypeDetailsVO : empTypeDetsList ){	
			
			/* Check if it is first time save then RiskId would be DEFAULT_LOW_LONG, then set RiskId = null, Based on this Save case is decided */
			if( !Utils.isEmpty( empTypeDetailsVO.getRiskId() ) && empTypeDetailsVO.getRiskId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
				empTypeDetailsVO.setRiskId(null);
			}
			/* If Risk id not null and sum insured is zero or null, its a case of deleted content or non mandatory content for which inputs not provided */
			if( Utils.isEmpty( empTypeDetailsVO.getRiskId() ) && isSumInsuredZero( empTypeDetailsVO )){
				continue;
			}
			//Renewal Multiple Id's handling changes, added policy in the query parameter
			TTrnWctplUnnamedPersonQuo wupQuo = 
				handleTableRecord( SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON, policyVO, new POJO[]{ buildingQuo, occupancy }, 
						new BaseVO[]{ empTypeDetailsVO }, SvcConstants.IS_TABLE_QUERY_HBM, section.getPolicyId(),empTypeDetailsVO.getRiskId() );
		
			/* Setting buildingId into BasicRiskId for a particular section ( or for a particular class code ) into ThreadLevelContext which is stored into the extra column added to 
			 * T_TRN_SECTION_LOCATION / T_TRN_SECTION_LOCATION_QUO  
			 * */
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID , CopyUtils.copySerializableObject( wupQuo.getId().getWupId() ) );
			
			// Process Premium record
			TTrnPremiumQuo premium =  handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{ buildingQuo, occupancy, wupQuo }, 
					new BaseVO[]{ empTypeDetailsVO }, SvcConstants.IS_TABLE_QUERY_HBM, wcDetails.getPolicyId(),BigDecimal.valueOf( wupQuo.getWupBasicRiskId()), BigDecimal.valueOf( wupQuo.getId().getWupId() ),
					   WC_BASIC_COVER,WC_COVER_TYPE,WC_COVER_SUB_TYPE);
			
			PremiumVO premiumVO = new PremiumVO();
			if(Utils.isEmpty( empTypeDetailsVO.getPremium() )){
				empTypeDetailsVO.setPremium( premiumVO );
			}
			/* Set the premium amount back to content from premium POJO */ 
			if(!Utils.isEmpty(  premium.getPrmPremium()) && ! (premium.getPrmPremium().compareTo( BigDecimal.valueOf( 0.0 )) ==  0) ){					
					empTypeDetailsVO.getPremium().setPremiumAmt(  premium.getPrmPremium().doubleValue());
			 }else{						 	
					empTypeDetailsVO.getPremium().setPremiumAmt( 0.0 );
			 }
		}
	}

	@Override
	protected POJO mapVOToPOJO(String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO) {
		
		POJO mappedPOJO = null;
		PublicLiabilityVO plDetails = null;
		
		if(PolicyUtils.getBasicSectionId(policyVO)  ==  PL_SECTION_ID )  {
			plDetails = (PublicLiabilityVO) PolicyUtils.getRiskGroupDetailsForProcessing( policyVO, SvcConstants.SECTION_ID_PL );
		}
		SectionVO wcSection = PolicyUtils.getSectionVO( policyVO, WC_SECTION_ID );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( wcSection );
		WCVO wcDetails = (WCVO) PolicyUtils.getRiskGroupDetails( locationVO, wcSection );
		wcDetails.setPolicyId( wcSection.getPolicyId() );		
		
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON.equals( tableId ) ){
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[ 0 ];
			TMasOccupancy occupancy = (TMasOccupancy) deps[ 1 ];
			EmpTypeDetailsVO empTypeDetsVO = ( EmpTypeDetailsVO ) depsVO[0];			
			mappedPOJO = getwcDetailsQuo( buildingQuo, plDetails, empTypeDetsVO, occupancy, locationVO, policyVO ,wcDetails );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON.equals( tableId ) ){
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[ 0 ];
			TMasOccupancy occupancy = (TMasOccupancy) deps[ 1 ];
			WCNammedEmployeeVO empDetsVO = ( WCNammedEmployeeVO ) depsVO[3];			
			mappedPOJO = getwcEmpDetailsQuo( buildingQuo, plDetails, empDetsVO, occupancy, locationVO, policyVO ,wcDetails );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[ 0 ];
			TMasOccupancy occupancy = (TMasOccupancy) deps[ 1 ];
			TTrnWctplUnnamedPersonQuo wupQuo = (TTrnWctplUnnamedPersonQuo) deps[ 2 ];
			mappedPOJO = getPremiumPojo( wupQuo, buildingQuo, plDetails, wcDetails, occupancy, policyVO, ( EmpTypeDetailsVO ) depsVO[0]);
		}
		return mappedPOJO;
	}

	private POJO getwcEmpDetailsQuo(TTrnBuildingQuo buildingQuo,
			PublicLiabilityVO plDetails, WCNammedEmployeeVO empDetsVO,
			TMasOccupancy occupancy, LocationVO locationVO, PolicyVO policyDetails,
			WCVO wcDetails) {
	
		TTrnWctplPersonQuo personQuo = new TTrnWctplPersonQuo();
		TTrnWctplPersonQuoId personQuoId = null;
		
		if(!Utils.isEmpty(policyDetails.getScheme().getEffDate()))
		{
			personQuo.setWprStartDate( policyDetails.getScheme().getEffDate());
		}
		else
		{
			personQuo.setWprStartDate( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}
		
		if(!Utils.isEmpty(policyDetails.getEndDate()))
		{
			personQuo.setWprEndDate( policyDetails.getEndDate());
		}
		else
		{
			personQuo.setWprEndDate( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}
		
		/** SK : Changes */
		personQuo.setWprValidityExpiryDate( SvcConstants.EXP_DATE );
		
		/** SK : Changes */
		/*
		 * Check if PAR is selected i.e. when buildingQuo will not be null.
		 * If not then set it using plDetails.wbdid
		 */
		/*if(!Utils.isEmpty( buildingQuo )){
			unnamedPersonQuo.setWupBldId( buildingQuo.getId().getBldId() );
		}else{
			unnamedPersonQuo.setWupBldId( plDetails.getWbdId() );
		}*/
		
		/*if(!Utils.isEmpty( locationVO.getRiskGroupId() )){
			unnamedPersonQuo.setWupBldId(Long.parseLong( locationVO.getRiskGroupId()) );
		}else{
			unnamedPersonQuo.setWupBldId( plDetails.getWbdId() );
		}*/
		/*if( ( !Utils.isEmpty( empDetsVO.getWprWCId() ) && empDetsVO.getWprWCId().equals( CommonConstants.DEFAULT_LOW_LONG ) )
				|| Utils.isEmpty( empDetsVO.getWprWCId() ) ){
			empDetsVO.setWprWCId( null );
			personQuoId = new TTrnWctplPersonQuoId();
			personQuoId.setWprId( 0L );
			personQuo.setId( personQuoId );
			Long cntSequence = NextSequenceValue.getNextSequence( WCTPL_SEQ , null,null, getHibernateTemplate() );
			personQuoId.setWprId( cntSequence );
			personQuoId.setWprValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		}
		else{
			personQuo.setWprBasicRiskId(  empDetsVO.getWprWCId() );
		}
*/
		
		personQuo.setWprBldId(Long.parseLong( locationVO.getRiskGroupId()) );
		personQuo.setWprEndtId(  (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID) );
		personQuo.setWprStatus( (byte) 6);
		personQuo = BeanMapper.map( occupancy, personQuo );
		personQuo.setWprTradeGroup( Long.valueOf( occupancy.getOcpTradeCode()) );
		personQuo.setWprRtCode(Long.valueOf( occupancy.getOcpRskCode()) );
		personQuo.setWprRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.WC_RI_RSK_CODE ) ) );
		
		personQuo.setWprRskCode( Long.valueOf( WC_BASIC_RISK_CODE ) );
		
		/* Setting values for WupBasicRskCode and WupHazard as a fix for back-end defect. */
        Long hazardCode = null;
        LookUpListVO lookUpVOList = SvcUtils.getLookUpCodesList( "HAZARD_CODE", locationVO.getOccTradeGroup().toString(), "ALL" );
        if( !Utils.isEmpty( lookUpVOList ) && !Utils.isEmpty( lookUpVOList.getLookUpList() ) &&  
                    !Utils.isEmpty( lookUpVOList.getLookUpList().get( 0 ) ) && !Utils.isEmpty( lookUpVOList.getLookUpList().get( 0 ).getCode() )){
              hazardCode = Long.valueOf( lookUpVOList.getLookUpList().get( 0 ).getCode().toString() ); 
        }
        personQuo.setWprBasicRskCode( Integer.valueOf( WC_BASIC_RISK_CODE ) );
        personQuo.setWprBasicRiskId( DAOUtils.getWcWUPBasicRskId( policyDetails , getHibernateTemplate() ) );
        personQuo.setWprHazard( hazardCode );
     
        //personQuo.setWprEmpLiabLmt( empTypeDetsVO.getLimit() );
      //  personQuo.setWprEmploymentType( empTypeDetsVO.getEmpType().shortValue() );
      //  personQuo.setWprSumInsured(new BigDecimal( empTypeDetsVO.getWageroll() ) );
		
       // if(!Utils.isEmpty(  empDetsVO.getRiskId()) ) personQuo.setWprBasicRiskId( Long.valueOf( empDetsVO.getRiskId()) );
        
        if(!Utils.isEmpty(  empDetsVO.getEmpName()) ) personQuo.setWprEName( empDetsVO.getEmpName() );
        
        personQuo.setWprPolicyId( wcDetails.getPolicyId() );
		
		
		if( !Utils.isEmpty( locationVO.getAddress().getLocOverrideTer() ) ){
			personQuo.setWprTerCode( locationVO.getAddress().getLocOverrideTer().shortValue() );
		}
		if( !Utils.isEmpty( locationVO.getAddress().getLocOverrideJur() ) ){
			personQuo.setWprJurCode( locationVO.getAddress().getLocOverrideJur().shortValue() );
		}
		
		personQuo.setWprRcCode(Long.valueOf(WC_RISK_CATEGORY));
		
		if(!Utils.isEmpty( buildingQuo ))
		{
			personQuo.setWprFreeZone(buildingQuo.getBldFreeZone());
			
			if(!Utils.isEmpty( buildingQuo )&&!Utils.isEmpty( policyDetails.getGeneralInfo() )&&
					!Utils.isEmpty( policyDetails.getGeneralInfo().getInsured() )&&
					!Utils.isEmpty( policyDetails.getGeneralInfo().getInsured().getAddress() )&&
					!Utils.isEmpty( policyDetails.getGeneralInfo().getInsured().getAddress().getTerritory() ))
				personQuo.setWprTerCode(Short.valueOf(policyDetails.getGeneralInfo().getInsured().getAddress().getTerritory().toString()));
			if(!Utils.isEmpty( buildingQuo )&&!Utils.isEmpty( policyDetails.getGeneralInfo() )&&!Utils.isEmpty( policyDetails.getGeneralInfo().getJurisdiction() ))
				personQuo.setWprJurCode(Short.valueOf(policyDetails.getGeneralInfo().getJurisdiction()));
		}
		else if(!Utils.isEmpty( locationVO ))
		{
			personQuo.setWprFreeZone(locationVO.getFreeZone());
			if(!Utils.isEmpty( locationVO )&&!Utils.isEmpty( locationVO.getAddress())&&!Utils.isEmpty( locationVO.getAddress().getLocOverrideTer() ))
			personQuo.setWprTerCode(Short.valueOf(locationVO.getAddress().getLocOverrideTer().toString()));
			if(!Utils.isEmpty( locationVO )&&!Utils.isEmpty( locationVO.getAddress())&&!Utils.isEmpty( locationVO.getAddress().getLocOverrideJur() ))
			personQuo.setWprJurCode(Short.valueOf(locationVO.getAddress().getLocOverrideJur().toString()));
		}
		setTerJurCodeToWpr( personQuo, policyDetails, locationVO );
		setAuditDetailsforWpr( personQuo, policyDetails);
		
		/*Defect Fix Start: Setting OccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
		//BigDecimal occTradeCode=DAOUtils.getOccupancyTradeCode(getHibernateTemplate(), policyDetails, getSectionId(),BigDecimal.valueOf(empTypeDetsVO.getEmpType()),wcCriteriaCode) ;
		//if(!Utils.isEmpty(occTradeCode))personQuo.setWprTradeGroup(occTradeCode.longValue() );
		/*Defect Fix End: Setting OccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
		
		return personQuo;
		
	}

	private void setAuditDetailsforWpr(TTrnWctplPersonQuo personQuo,
			PolicyVO policyDetails) {
		Integer userId = SvcUtils.getUserId(policyDetails);
		//TODO: Need to find a way to conditionally update the prepared by columns. If its an update there is no need to update this column
		personQuo.setWprPreparedBy(userId);
		personQuo.setWprPreparedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		personQuo.setWprModifiedBy(userId);
		personQuo.setWprModifiedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ));
		
		
	}

	private void setTerJurCodeToWpr(TTrnWctplPersonQuo personQuo,
			PolicyVO policyDetails, LocationVO locationVO) {
		SectionVO plSectionVO = PolicyUtils.getSectionVO( policyDetails, SvcConstants.SECTION_ID_PL );
		if( !Utils.isEmpty( plSectionVO ) ){
			if( !Utils.isEmpty( plSectionVO.getRiskGroupDetails() ) ){
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : plSectionVO.getRiskGroupDetails().entrySet() ){
					LocationVO locationDetails = (LocationVO) locationEntry.getKey();
					if( locationDetails.getRiskGroupId().equalsIgnoreCase( locationVO.getRiskGroupId() ) ){
						if( !Utils.isEmpty( locationDetails.getAddress().getLocOverrideTer() ) )
							personQuo.setWprTerCode( locationDetails.getAddress().getLocOverrideTer().shortValue() );
						if( !Utils.isEmpty( locationDetails.getAddress().getLocOverrideJur() ) )
							personQuo.setWprJurCode( locationDetails.getAddress().getLocOverrideJur().shortValue() );
					}
				}
			}
		}
		
	}

	/**
	 * Manipulate TTrnPremiumQuo to persist premium data for WC content
	 * @param wcDetailsQuo
	 * @param buildingQuo
	 * @param plDetails
	 * @param wcDetails
	 * @param occupancy
	 * @param policyDetails
	 * @param empTypeDetailsVO
	 * @return
	 */
	private TTrnPremiumQuo getPremiumPojo( TTrnWctplUnnamedPersonQuo wcDetailsQuo, TTrnBuildingQuo buildingQuo, PublicLiabilityVO plDetails, WCVO wcDetails,
			TMasOccupancy occupancy, PolicyVO policyDetails, EmpTypeDetailsVO empTypeDetailsVO ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		if( Boolean.TRUE.equals( wcDetails.getWcCovers().getPACover() ) ){
			//premiumQuoId.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "WC_ADDITIONAL_10_COVER" ) ) );
			premiumQuoId.setPrmCovCode( Short.valueOf( String.valueOf( 1 ) ) );
		}
		else{
			premiumQuoId.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) ) );
		}
		
		premiumQuoId.setPrmValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD));		
		premiumQuoId.setPrmPolicyId( wcDetailsQuo.getWupPolicyId() );
		premiumQuoId.setPrmBasicRskCode( WC_BASIC_RISK_CODE );
		premiumQuoId.setPrmRskCode( WC_RISK_CODE );
		premiumQuoId.setPrmCtCode( WC_COVER_TYPE );
		premiumQuoId.setPrmCstCode( WC_COVER_SUB_TYPE );
		
		/** SK : Changes */
		/** Throw business exception in case basic risk id obtained from wcDetailsQuo is null */
		if( Utils.isEmpty( wcDetailsQuo.getWupBasicRiskId() ) ){
			throw new BusinessException( "", null, "Basic Risk Id obtained from wcDetails is null for [ " + wcDetailsQuo.getWupPolicyId() + " ] policy id" );
		}

		/* Basic risk id should be WupId from unnamed person table T_TRN_WCTPL_UNNAMED_PERSON */
		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( wcDetailsQuo.getId().getWupId() ) ); 

		/* We should be setting wcDetailsQuo.wupid as premium risk id */
		if(!Utils.isEmpty( wcDetailsQuo.getId().getWupId() ) ) premiumQuoId.setPrmRskId( BigDecimal.valueOf( wcDetailsQuo.getId().getWupId() ) );
		
		premiumQuo.setId( premiumQuoId );

		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmClCode( WC_CLASS );
		premiumQuo.setPrmRiRskCode(  Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.WC_RI_RSK_CODE ) ) );
		premiumQuo.setPrmCompulsoryExcess(  empTypeDetailsVO.getDeductibles() );
		premiumQuo.setPrmRcCode( occupancy.getOcpRcCode() );
		premiumQuo.setPrmRscCode( occupancy.getOcpRskCode() );
		premiumQuo.setPrmRtCode( occupancy.getOcpRtCode() );
		premiumQuo.setPrmPtCode( (short) 50 );
		premiumQuo.setPrmEndtId(  (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID) );
		premiumQuo.setPrmStatus( (byte) 1 );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER);
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmSumInsured(new BigDecimal( empTypeDetailsVO.getWageroll() ) );
		SvcUtils.setAuditDetailsforPrm(premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		premiumQuo.setPrmPreparedDt((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
		/*Added to enter premium value*/
		if(!Utils.isEmpty( empTypeDetailsVO.getPremium() ))
		{
			if(!Utils.isEmpty( empTypeDetailsVO.getPremium().getPremiumAmt() )){
				premiumQuo.setPrmPremium( new BigDecimal(String.valueOf(empTypeDetailsVO.getPremium().getPremiumAmt())));
				premiumQuo.setPrmPremiumActual( new BigDecimal(String.valueOf( empTypeDetailsVO.getPremium().getPremiumAmt() ) ));
			}else{
				setZeroPrmValue( premiumQuo );
			}
		}else{
			setZeroPrmValue( premiumQuo );
		}
		
		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );
		premiumQuo.setPrmRiLocCode( SvcConstants.APP_PRM_RI_LOC_CODE );
		return premiumQuo;
		
	}
	
	/**
	 * Manipulate TTrnWctplUnnamedPersonQuo to persist premium data for WC content
	 * @param buildingQuo
	 * @param plDetails
	 * @param empTypeDetsVO
	 * @param occupancy
	 * @param locationVO
	 * @param policyDetails
	 * @param wcDetails
	 * @return
	 */
	private TTrnWctplUnnamedPersonQuo getwcDetailsQuo( TTrnBuildingQuo buildingQuo, PublicLiabilityVO plDetails, EmpTypeDetailsVO empTypeDetsVO, TMasOccupancy occupancy,
			LocationVO locationVO, PolicyVO policyDetails, WCVO wcDetails ){

		TTrnWctplUnnamedPersonQuo unnamedPersonQuo = new TTrnWctplUnnamedPersonQuo();
		
		if(!Utils.isEmpty(policyDetails.getScheme().getEffDate()))
		{
			unnamedPersonQuo.setWupStartDate( policyDetails.getScheme().getEffDate());
		}
		else
		{
			unnamedPersonQuo.setWupStartDate( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}
		
		if(!Utils.isEmpty(policyDetails.getEndDate()))
		{
			unnamedPersonQuo.setWupEndDate( policyDetails.getEndDate());
		}
		else
		{
			unnamedPersonQuo.setWupEndDate( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}
		
		/** SK : Changes */
		unnamedPersonQuo.setWupValidityExpiryDate( SvcConstants.EXP_DATE );
		
		/** SK : Changes */
		/*
		 * Check if PAR is selected i.e. when buildingQuo will not be null.
		 * If not then set it using plDetails.wbdid
		 */
		/*if(!Utils.isEmpty( buildingQuo )){
			unnamedPersonQuo.setWupBldId( buildingQuo.getId().getBldId() );
		}else{
			unnamedPersonQuo.setWupBldId( plDetails.getWbdId() );
		}*/
		
		/*if(!Utils.isEmpty( locationVO.getRiskGroupId() )){
			unnamedPersonQuo.setWupBldId(Long.parseLong( locationVO.getRiskGroupId()) );
		}else{
			unnamedPersonQuo.setWupBldId( plDetails.getWbdId() );
		}*/
		
		unnamedPersonQuo.setWupBldId(Long.parseLong( locationVO.getRiskGroupId()) );
		
		unnamedPersonQuo.setWupStatus( (byte) 1 );
		unnamedPersonQuo = BeanMapper.map( occupancy, unnamedPersonQuo );
		unnamedPersonQuo.setWupTradeGroup( Long.valueOf( occupancy.getOcpTradeCode()) );
		unnamedPersonQuo.setWupRtCode( occupancy.getOcpRskCode().shortValue() );
		unnamedPersonQuo.setWupRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.WC_RI_RSK_CODE ) ) );
		
		unnamedPersonQuo.setWupRskCode( Long.valueOf( WC_BASIC_RISK_CODE ) );
		
		/* Setting values for WupBasicRskCode and WupHazard as a fix for back-end defect. */
        Long hazardCode = null;
        LookUpListVO lookUpVOList = SvcUtils.getLookUpCodesList( "HAZARD_CODE", locationVO.getOccTradeGroup().toString(), "ALL" );
        if( !Utils.isEmpty( lookUpVOList ) && !Utils.isEmpty( lookUpVOList.getLookUpList() ) &&  
                    !Utils.isEmpty( lookUpVOList.getLookUpList().get( 0 ) ) && !Utils.isEmpty( lookUpVOList.getLookUpList().get( 0 ).getCode() )){
              hazardCode = Long.valueOf( lookUpVOList.getLookUpList().get( 0 ).getCode().toString() ); 
        }
        unnamedPersonQuo.setWupBasicRskCode( Integer.valueOf( WC_BASIC_RISK_CODE ) );
        unnamedPersonQuo.setWupHazard( hazardCode );
        
        unnamedPersonQuo.setWupNoOfPerson( Long.valueOf( empTypeDetsVO.getNoOfEmp() ) );
		unnamedPersonQuo.setWupEmpLiabLmt( empTypeDetsVO.getLimit() );
		unnamedPersonQuo.setWupEmploymentType( empTypeDetsVO.getEmpType().shortValue() );
		unnamedPersonQuo.setWupSumInsured(new BigDecimal( empTypeDetsVO.getWageroll() ) );
		if(!Utils.isEmpty(  empTypeDetsVO.getRiskId()) ) unnamedPersonQuo.setWupBasicRiskId( Long.valueOf( empTypeDetsVO.getRiskId()) );		
		unnamedPersonQuo.setWupPolicyId( wcDetails.getPolicyId() );
		
		
		if( !Utils.isEmpty( locationVO.getAddress().getLocOverrideTer() ) ){
			unnamedPersonQuo.setWupTerCode( locationVO.getAddress().getLocOverrideTer().shortValue() );
		}
		if( !Utils.isEmpty( locationVO.getAddress().getLocOverrideJur() ) ){
			unnamedPersonQuo.setWupJurCode( locationVO.getAddress().getLocOverrideJur().shortValue() );
		}
		
		unnamedPersonQuo.setWupRcCode(WC_RISK_CATEGORY);
		
		if(!Utils.isEmpty( buildingQuo ))
		{
			unnamedPersonQuo.setWupFreeZone(buildingQuo.getBldFreeZone());
			
			if(!Utils.isEmpty( buildingQuo )&&!Utils.isEmpty( policyDetails.getGeneralInfo() )&&
					!Utils.isEmpty( policyDetails.getGeneralInfo().getInsured() )&&
					!Utils.isEmpty( policyDetails.getGeneralInfo().getInsured().getAddress() )&&
					!Utils.isEmpty( policyDetails.getGeneralInfo().getInsured().getAddress().getTerritory() ))
			unnamedPersonQuo.setWupTerCode(Short.valueOf(policyDetails.getGeneralInfo().getInsured().getAddress().getTerritory().toString()));
			if(!Utils.isEmpty( buildingQuo )&&!Utils.isEmpty( policyDetails.getGeneralInfo() )&&!Utils.isEmpty( policyDetails.getGeneralInfo().getJurisdiction() ))
			unnamedPersonQuo.setWupJurCode(Short.valueOf(policyDetails.getGeneralInfo().getJurisdiction()));
		}
		else if(!Utils.isEmpty( locationVO ))
		{
			unnamedPersonQuo.setWupFreeZone(locationVO.getFreeZone());
			if(!Utils.isEmpty( locationVO )&&!Utils.isEmpty( locationVO.getAddress())&&!Utils.isEmpty( locationVO.getAddress().getLocOverrideTer() ))
			unnamedPersonQuo.setWupTerCode(Short.valueOf(locationVO.getAddress().getLocOverrideTer().toString()));
			if(!Utils.isEmpty( locationVO )&&!Utils.isEmpty( locationVO.getAddress())&&!Utils.isEmpty( locationVO.getAddress().getLocOverrideJur() ))
			unnamedPersonQuo.setWupJurCode(Short.valueOf(locationVO.getAddress().getLocOverrideJur().toString()));
		}
		setTerJurCodeToWup( unnamedPersonQuo, policyDetails, locationVO );
		setAuditDetailsforWup( unnamedPersonQuo, policyDetails);
		
		/*Defect Fix Start: Setting OccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
		BigDecimal occTradeCode=DAOUtils.getOccupancyTradeCode(getHibernateTemplate(), policyDetails, getSectionId(),BigDecimal.valueOf(empTypeDetsVO.getEmpType()),WC_CRITERIA_CODE) ;
		if(!Utils.isEmpty(occTradeCode))unnamedPersonQuo.setWupTradeGroup(occTradeCode.longValue() );
		/*Defect Fix End: Setting OccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
		
		return unnamedPersonQuo;
	}
	
	/**
	 * Sets audit related table column values like PreparedBy, ModifiedBy and ModifiedDt  to persist the object 
	 * @param unnamedPersonQuo
	 * @param policyDetails
	 */
	private void setAuditDetailsforWup(
			TTrnWctplUnnamedPersonQuo unnamedPersonQuo, PolicyVO policyDetails) {
		Integer userId = SvcUtils.getUserId(policyDetails);
		//TODO: Need to find a way to conditionally update the prepared by columns. If its an update there is no need to update this column
		unnamedPersonQuo.setWupPreparedBy(userId);
		unnamedPersonQuo.setWupPreparedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		unnamedPersonQuo.setWupModifiedBy(userId);
		unnamedPersonQuo.setWupModifiedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ));
		
	}

/*	Commenting as this method is no where used.
	private TTrnWctplUnnamedPersonQuoId constructTTrnWctplUnamedPersonQuoId(WCVO wcDets){
		TTrnWctplUnnamedPersonQuoId id = new TTrnWctplUnnamedPersonQuoId();
		/*
		 * To check if riskId is already present which signifies
		 * that the operation is for saved location
		 */
/*
		if(Utils.isEmpty( wcDets.getRiskId() )){
			Long cntSequence = NextSequenceValue.getNextSequence( WCTPLUP_SEQ, getHibernateTemplate() );
			id.setWupId( cntSequence );
		}else{
			id.setWupId( wcDets.getRiskId() );
		}
		id.setWupValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD) );
		
		return id;
	}
*/
	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeCreated = false;
		/* For premium table is to created decision from what is done in case of TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON*/
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			Boolean isCreated = (Boolean) ThreadLevelContext.get( com.Constant.CONST_PRM_TO_BE_CREATED );
			ThreadLevelContext.clear( com.Constant.CONST_PRM_TO_BE_CREATED );
			
			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;
		}else if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON.equals( tableId ) ){
			//Boolean isCreated = (Boolean) ThreadLevelContext.get( com.Constant.CONST_PRM_TO_BE_CREATED );
			ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, false );
			
			WCNammedEmployeeVO namedEmployee = (WCNammedEmployeeVO) depsVO[ 3 ];
			if( !Utils.isEmpty( namedEmployee ) ){
				//Get named employee details
				if( Utils.isEmpty( namedEmployee.getWprWCId() ) ){
					ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, true );
					isToBeCreated = true;
				}
			}
			return isToBeCreated;
		}else if( SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON.equals( tableId ) ){
			EmpTypeDetailsVO empTypeDetailsVO = (EmpTypeDetailsVO) depsVO[0];
			/* If Risk id of a content is null or it is default value then its case CREATE case */
			if( Utils.isEmpty( empTypeDetailsVO.getRiskId() ) || empTypeDetailsVO.getRiskId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
				ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, true );
				return true;
			}
		}

		ThreadLevelContext.set( com.Constant.CONST_PRM_TO_BE_CREATED, false );
		return false;
	}

	@Override
	protected boolean isToBeDeleted(String tableId, PolicyVO policyVO,
			POJO[] depsPOJO, BaseVO[] depsVO) {
		boolean isToBeDeleted = false;
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON.equals( tableId ) ){
			EmpTypeDetailsVO empTypeDetailsVO = (EmpTypeDetailsVO) depsVO[0];
	
			if( Utils.isEmpty( empTypeDetailsVO.getRiskId() ) || isSumInsuredZero( empTypeDetailsVO ) ){
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, true );
				isToBeDeleted = true;
			}else{
				isToBeDeleted = false;
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, false );
			}
		}else if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON.equals( tableId ) ){
			WCNammedEmployeeVO namedEmployee = (WCNammedEmployeeVO) depsVO[ 3 ];
			if( !Utils.isEmpty( namedEmployee.getIsToBeDeleted() ) ){
				if( namedEmployee.getIsToBeDeleted() ){
					ThreadLevelContext.set( "PRM_TO_BE_DELETED", true );
					isToBeDeleted = true;
					return true;
				}
			}

		}else if(SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId )){
			isToBeDeleted = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_DELETED );
			ThreadLevelContext.clear( SvcConstants.PRM_TO_BE_DELETED );
			
			return ( !Utils.isEmpty( isToBeDeleted ) && isToBeDeleted ) ? true : false;
		}
		return isToBeDeleted;
	}

	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase){
		
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON.equals( tableId ) ){
			
			TTrnWctplUnnamedPersonQuo unnamedPersonQuo =	(TTrnWctplUnnamedPersonQuo) mappedRecord;
			EmpTypeDetailsVO empTypeDetsVO = ( EmpTypeDetailsVO ) depsVO[0];
			
			if( saveCase == SaveCase.DELETE_PENDING_REC || saveCase == SaveCase.DELETE ){
				/* If content is deleted then risk id needs to be reset to bull */
				empTypeDetsVO.setRiskId( null );
			}else if( saveCase == SaveCase.CHANGE_WITH_NEW_REC && isSumInsuredZero( empTypeDetsVO )){
				/* If content is deleted then risk id needs to be reset to bull */
				empTypeDetsVO.setRiskId( null );
			}else{
				empTypeDetsVO.setRiskId( unnamedPersonQuo.getId().getWupId() );
			}
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject(  unnamedPersonQuo.getId().getWupId() ) );
		}
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON.equals( tableId ) ){
			
			TTrnWctplPersonQuo personQuo =	(TTrnWctplPersonQuo) mappedRecord;
			WCNammedEmployeeVO empDetsVO = ( WCNammedEmployeeVO ) depsVO[3];
			
			if( saveCase == SaveCase.DELETE_PENDING_REC || saveCase == SaveCase.DELETE ){
				/* If content is deleted then risk id needs to be reset to null */
				empDetsVO.setWprWCId( null );
			}else if( saveCase == SaveCase.CHANGE_WITH_NEW_REC && isEmployeeNameNull( empDetsVO )){
				/* If content is deleted then risk id needs to be reset to null */
				empDetsVO.setWprWCId( null );
			}else{
				empDetsVO.setWprWCId( personQuo.getId().getWprId() );
			}
			//ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject(  personQuo.getId().getWprId() ) );
		}
	}

	private boolean isEmployeeNameNull(WCNammedEmployeeVO empDetsVO) {
		if( Utils.isEmpty( empDetsVO.getEmpName()) ){
			return true;
		}
		return false;
	}

	/**
	 * Checks if suminsured value is null or greater than 0
	 * @param content
	 * @return
	 */
	private boolean isSumInsuredZero(EmpTypeDetailsVO empTypeDetsVO){
		if( Utils.isEmpty( empTypeDetsVO.getWageroll() ) || empTypeDetsVO.getWageroll() == 0.0 ){
			return true;
		}
		return false;
	}

	@Override
	protected void tableRecPostSaveProcessing(String tableId,
			POJO mappedRecord, PolicyVO policyVO) {
				
				//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
				
				
	}

	@Override
	protected void tableRecPostProcessing(String tableId, POJO mappedRecord,
			PolicyVO policyVO) {
				
				//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
	}
	
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		updateSectionLevelSIAndPremium(policyVO);		
		updateEndtText( policyVO );
		super.sectionPostProcessing( policyVO );
	}
	
	/**
	 * (1). Deletes previous endorsement text for T_TRN_WCTPL_UNNAMED_PERSON_QUO column changes
	 * (2). Updates endorsement text for T_TRN_WCTPL_UNNAMED_PERSON_QUO column changes
	 * @param policyVO
	 */
	private void updateEndtText( PolicyVO policyVO ){
		
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			SectionVO wcSection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( wcSection );
			WCVO wcDetails = (WCVO) PolicyUtils.getRiskGroupDetails( locationDetails, wcSection );
			/*
			 * (1).
			 */
			DAOUtils.deletePrevEndtText( wcSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ), WC_SECTION_ID,
					Long.valueOf( locationDetails.getRiskGroupId() ) );

			
				/*
				 * (1).For add location
				 */
				LOGGER.debug( "call pro_endt_text_wc_un_per_add" );
				DAOUtils.addWCforendorsementFlow( wcSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ) );
				
				LOGGER.debug( "SATRT : Executing proc to add endt text for adding  content - pro_endt_text_wup_emp_add" );
				DAOUtils.genEndtTextWCContentAdd( wcDetails.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ) );
				LOGGER.debug( "END : Executing proc to add endt text for adding content - pro_endt_text_wup_emp_add" );
			
				/*
				 * (2).For fields endorsement
				 */
				LOGGER.debug( "call pro_endt_text_wc_un_per_col" );
				DAOUtils.updateWCforendorsementFlow( wcSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), wcDetails.getBasicRiskId() );
				
				
				/*
				 * (3).For delete location
				 */
				LOGGER.debug( "call pro_endt_text_wc_un_per_del" );
				
				LOGGER.debug( "updated endt text for deleting content - pro_endt_text_wup_emp_del" );
				DAOUtils.genEndtTextWCContentDel( wcDetails.getPolicyId(), policyVO,  Long.valueOf( locationDetails.getRiskGroupId() ) );
				// Bug 4179 - Endorsement Text Issue - Deletion of employee in the Workmen Compensation
				//DAOUtils.deleteWCforendorsementFlow( wcSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ) );
				
				
				for( EmpTypeDetailsVO empTypeDets : wcDetails.getEmpTypeDetails() ){
					if(!Utils.isEmpty( empTypeDets ) && !Utils.isEmpty( empTypeDets.getRiskId() )){
						LOGGER.debug( "call deductible change endo SP" );
						DAOUtils.updateDeductibleforendorsementFlow( wcSection.getPolicyId(), policyVO,wcSection.getSectionId(),  empTypeDets.getRiskId(),Long.valueOf(locationDetails.getRiskGroupId()) );
					}
				}
			
			//LOGGER.debug( "call Risk Add changes change endo SP" );
			//DAOUtils.updateEndTextForRiskAdd( wcSection.getPolicyId(), policyVO,wcSection.getSectionId());
				
			//Moving call WC person after WC unnamed person	
			DAOUtils.addWCPersonforendorsementFlow(wcSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ));
			DAOUtils.deleteWCPersonforendorsementFlow(wcSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ));
			LOGGER.debug("call pro_endt_text_wc_per_mod ");
			for(WCNammedEmployeeVO nammedEmpVO : wcDetails.getWcEmployeeDetails()){
				if(!Utils.isEmpty(nammedEmpVO.getWprWCId())){
					DAOUtils.updateWCPersonforendorsementFlow(wcSection.getPolicyId(), policyVO, Long.valueOf(locationDetails.getRiskGroupId()), nammedEmpVO.getWprWCId());
				}
			} 
			
			DAOUtils.updateTotalSITextforendorsementFlow( wcSection.getPolicyId(), policyVO,wcSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );

		}
		
	}

	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){
		POJOId id = null;
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON.equalsIgnoreCase( tableId ) ){
			
			TTrnWctplUnnamedPersonQuo unnamedPersonQuo = (TTrnWctplUnnamedPersonQuo) mappedRecord;
			
			TTrnWctplUnnamedPersonQuoId unId = new TTrnWctplUnnamedPersonQuoId();
			Long cntSequence = NextSequenceValue.getNextSequence( WCTPLUP_SEQ, null,null, getHibernateTemplate() );
			unId.setWupId( cntSequence );
			unId.setWupValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			unnamedPersonQuo.setWupBasicRiskId( unId.getWupId() );
			//mappedRecord = unnamedPersonQuo;
			id = unId;

		} else if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON.equalsIgnoreCase( tableId ) ){
			
			TTrnWctplPersonQuo personQuo = (TTrnWctplPersonQuo) mappedRecord;
			
			TTrnWctplPersonQuoId prId = new TTrnWctplPersonQuoId();
			Long cntSequence = NextSequenceValue.getNextSequence( WCTPL_SEQ, null,null, getHibernateTemplate() );
			prId.setWprId( cntSequence );
			prId.setWprValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			personQuo.setWprBasicRiskId( DAOUtils.getWcWUPBasicRskId(policyVO,getHibernateTemplate()) );
			//mappedRecord = unnamedPersonQuo;
			id = prId;

		} else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) mappedRecord;
			TTrnPremiumQuoId pId = premiumQuo.getId();
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		return id;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){
	
		POJOId id = null;
		
		if( SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON.equalsIgnoreCase( tableId ) ){
			TTrnWctplUnnamedPersonQuoId unId = new TTrnWctplUnnamedPersonQuoId();
			unId.setWupId( ( (TTrnWctplUnnamedPersonQuoId) existingId ).getWupId() );
			unId.setWupValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = unId;
				 
		}else if( SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON.equalsIgnoreCase( tableId ) ){
			TTrnWctplPersonQuoId prId = new TTrnWctplPersonQuoId();
			prId.setWprId( ( (TTrnWctplPersonQuoId) existingId ).getWprId() );
			prId.setWprValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = prId;
				 
		} else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			
			TTrnPremiumQuoId pId;
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		
		return id;
		
	}

	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
	}

	@Override
	public BaseVO loadWCSection( BaseVO baseVO ){
		LOGGER.info( "Entering loadWCSection method" );
		DataHolderVO<List<EmpTypeDetailsVO>> empTypeDets = new DataHolderVO<List<EmpTypeDetailsVO>>();
		empTypeDets.setData( getEmployeeTypeDetsList(baseVO) );
		return empTypeDets;
	}

	@Override
	public BaseVO saveWCSection( BaseVO baseVO ){
		return saveSection(baseVO);
	}

	@Override
	public List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails currRgd ){
		
		return null;
	}

	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){
		
		return null;
	}

	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){
		
		return null;
	}
	
	@Override
	protected void sectionPreProcessing( PolicyVO policyVO ){
		super.sectionPreProcessing( policyVO );
	}
	
	/**
	 * 
	 * @param baseVO
	 * @param vMasPasFetchBasicDtlsLst
	 * @param nullBasicInfoList
	 * @return
	 */
	private List<EmpTypeDetailsVO> getEmployeeTypeDetsList( BaseVO baseVO){
		
		int tarCode = ( (PolicyVO) baseVO ).getScheme().getTariffCode();
		/* Fetch master content list for the tariff from V_MAS_PAS_FETCH_BASIC_DTLS view */
		List<VMasPasFetchBasicDtls>  NULL_BASIC_DTLS_LST = null;
		
		List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsLst = DAOUtils.getDataFromVMasPasFetchBasicDtls( getHibernateTemplate(), baseVO, NULL_BASIC_DTLS_LST , DAOUtils.constructPPPCriteriaVOForPPPDataFetch(Integer.valueOf( WC_CLASS ),WC_SECTION_ID,Integer.valueOf( tarCode )), true, WC_RISK_CODE );
		
		List<EmpTypeDetailsVO> empTypeDetsList = getEmpTypeDetsListFromVMasPasFetchBasicDtls( baseVO, vMasPasFetchBasicDtlsLst );

		return empTypeDetsList;
	}
	
	private double getSectionLevelPremium( WCVO wcVO ){
		return PremiumHelper.getSectionLevelPremium( wcVO );
	}
	
	private double getSectionLevelSumInsured(WCVO wcVO){
		return PremiumHelper.getSectionLevelSumInsured( wcVO );
	}
	
	/**
	 * Construct List<EmpTypeDetailsVO> from List<VMasPasFetchBasicDtls>
	 * @param input
	 * @param vMasPasFetchBasicDtlsList
	 * @param vMasPasFetchBasicInfoList
	 * @return
	 */
	public static List<EmpTypeDetailsVO> getEmpTypeDetsListFromVMasPasFetchBasicDtls(BaseVO input,List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsList){
		
		List<EmpTypeDetailsVO> empTypeDetsList = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>(EmpTypeDetailsVO.class);
		
		/*
		 * Use BeanMapper to form different Contents which is finally set to List of contents
		 */
		for(VMasPasFetchBasicDtls vMasPasFetchBasicDtls : vMasPasFetchBasicDtlsList){
			
			EmpTypeDetailsVO empTypeDetVO;
			empTypeDetVO = BeanMapper.map(vMasPasFetchBasicDtls,EmpTypeDetailsVO.class);
			empTypeDetsList.add(empTypeDetVO);
		}
		
		return empTypeDetsList;
	}
	
	
	/**
	 * Sets the section level Sum insured and Premium, calculated as sum of SI's and Premium's of each content
	 * @param policyVO
	 */
	private void updateSectionLevelSIAndPremium( PolicyVO policyVO ){
		
		SectionVO section = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_WC );
		LocationVO location = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		WCVO wcDetails = (WCVO) PolicyUtils.getRiskGroupDetails( location, section );
		/* Update Section level premium and sum insured into wcvo */
		if( Utils.isEmpty( wcDetails.getPremium() ) ){
			wcDetails.setPremium( new PremiumVO() );
		}
		wcDetails.getPremium().setPremiumAmt( getSectionLevelPremium(wcDetails) );
		wcDetails.setSumInsured( getSectionLevelSumInsured(wcDetails) );
		
	}
	/**
	 * Sets Teritory and Jurisdiction code from corresponding PL location selected on UI
	 * @param unnamedpersonQuo
	 * @param policyVO
	 */
	private void setTerJurCodeToWup( TTrnWctplUnnamedPersonQuo unnamedpersonQuo, PolicyVO policyVO, LocationVO locationVO ){
		SectionVO plSectionVO = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_PL );
		if( !Utils.isEmpty( plSectionVO ) ){
			if( !Utils.isEmpty( plSectionVO.getRiskGroupDetails() ) ){
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : plSectionVO.getRiskGroupDetails().entrySet() ){
					LocationVO locationDetails = (LocationVO) locationEntry.getKey();
					if( locationDetails.getRiskGroupId().equalsIgnoreCase( locationVO.getRiskGroupId() ) ){
						if( !Utils.isEmpty( locationDetails.getAddress().getLocOverrideTer() ) )
						unnamedpersonQuo.setWupTerCode( locationDetails.getAddress().getLocOverrideTer().shortValue() );
						if( !Utils.isEmpty( locationDetails.getAddress().getLocOverrideJur() ) )
						unnamedpersonQuo.setWupJurCode( locationDetails.getAddress().getLocOverrideJur().shortValue() );
					}
				}
			}
		}
	}
	
}
