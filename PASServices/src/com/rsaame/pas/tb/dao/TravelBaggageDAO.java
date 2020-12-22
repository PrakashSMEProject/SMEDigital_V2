package com.rsaame.pas.tb.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseException;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
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
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GPAUnnammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;

/**
 * 
 * @author m1017935
 * This Dao load and saves the Travel section
 *
 */
public class TravelBaggageDAO extends BaseSectionSaveDAO implements ITravelBaggageDAO{

	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	private final static Integer TB_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "TB_SECTION" ) );
	private final static String GACC_PERSON_SEQ = "SEQ_GACC_PERSON_ID";
	private static final short TB_BASIC_COVER = Short.valueOf("1");
	private static final short TB_COVER_TYPE = Short.valueOf( "0" );
	private static final short TB_COVER_SUB_TYPE = Short.valueOf("0");
	private final static Integer TB_RC_CODE = 0;
	private final static Integer TB_RSC_CODE = 0;
	private final static Integer TB_RT_CODE = 1;
	private final static Integer TB_COV_CODE = 1;
	private final static Integer TB_CT_CODE = 0;
	private final static Integer TB_CST_CODE =0 ;
	private final static Integer TB_RISK_CODE = 27;
	private final static Integer TB_RI_RISK_CODE = 501;
	private final static Integer TB_ENDT_ID = 0;
	private final static Integer TB_CLASS_CODE = 5;
	private final static Short TB_POLICY_TYPE = Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) );
	private final static Byte GPR_STATUS = 1;
	private final static Long ZERO_CONSTANT = 0L;
	private final static String YEARMONTHDAY = "yyyy-MM-dd";
	private final static String DAYMONTHYEAR = "dd/MM/yyyy";
	private final static String YEAR = "yyyy"; 
	private final static String ERROR_MSG_1 = "pas.basicSection.IDMandatory";
	private final static String ERROR_MSG_2 = "pas.dateFormat.Exception";
	@Override
	public BaseVO loadTBSection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseVO saveTBSection( BaseVO baseVO ){
		
	return saveSection( baseVO );
	}

	@Override
	protected int getSectionId(){
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected int getClassCode(){
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	protected BaseVO saveSection( BaseVO input ){

		// Saving the Travel Baggage data
		
		PolicyVO policyVO = (PolicyVO) input;
		/* Tables involved 
		 * 1)T_TRN_GACC_PERSON/QUO
		*/
		
		/* Let us get the system date right now and use from here on for this transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );
		
		SectionVO tbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_TB );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( tbSection );
		TravelBaggageVO tbDetails = (TravelBaggageVO) PolicyUtils.getRiskGroupDetails( locationDetails, tbSection );
		
		/* Handle the Gacc_Person data. Premium update for the  will be handled inside
		 * this method. */
		/* In TTrnGaccPerson GPR_E_NAME - Name
		 * GPR_DESCRIPTION - Limit required
		 * PRM_COMPULSORY_EXCESS(TTrnPremium) - deductible
		 * 
		 */
		handleGaccPerson( policyVO, tbSection, locationDetails, tbDetails);
		
		
		/* Handle other details is any like UW Questions*/
		return policyVO;
	}
	/*
	 * This method is to handle the insertion of data into the TTrnGaccPerson table
	 * @param PolicyVO,LocationVO,
	 * @param SectionVO,TravelBaggageVO
	 * 
	 * @return TTrnGaccPersonQuo
	 */
	private TTrnGaccPersonQuo handleGaccPerson( PolicyVO policyVO, SectionVO tbSection, LocationVO locationDetails, TravelBaggageVO tbDetails ){
		
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
			if( !Utils.isEmpty( locationDetails.getRiskGroupId() ) ){
				try{
					buildingQuo = (TTrnBuildingQuo) getHibernateTemplate().find( "from TTrnBuildingQuo buldQ where buldQ.id.bldId=?", Long.valueOf( locationDetails.getRiskGroupId() ) )
							.get( 0 );
				}
				catch( BusinessException exception ){
					throw new BusinessException( ERROR_MSG_1, exception, "ID from of the basic section is mandatory, no data in bld table" );
				}

			}
			if( Utils.isEmpty( buildingQuo ) ){
				throw new BusinessException( ERROR_MSG_1, null, "ID from of the basic section is mandatory" );
			}
		}
		else if( !Utils.isEmpty( basicSection ) && basicSectionID.equals( PL_SECTION_ID ) ){
			PublicLiabilityVO plDetails = (PublicLiabilityVO) basicSection.getRiskGroupDetails().get( locationDetails );
			if( !Utils.isEmpty( plDetails ) ){
				// this pojo may not be required, since the id required in case of par is not selected will be available in publicLiabilityVO
				try{
					premiseQuo = (TTrnWctplPremiseQuo) getHibernateTemplate().find( "from TTrnWctplPremiseQuo preQ where preQ.id.wbdId=?",
							Long.valueOf( locationDetails.getRiskGroupId() ) ).get( 0 ); /* LocationVO.RiskGroupId will be same as wbdId as PL is the basic section */
				}
				catch( BusinessException exception ){
					throw new BusinessException( ERROR_MSG_1, exception, "ID from of the basic section is mandatory, no data in premise table" );
				}
			}
			if( Utils.isEmpty( premiseQuo ) ){
				throw new BusinessException( ERROR_MSG_1, null, "ID from of the basic section is mandatory" );
			}
		}
		else{
			throw new BusinessException( ERROR_MSG_1, null, "Details of the basic section is mandatory" );
		}
		TTrnGaccPersonQuo gaccPersonQuo = null;
		Double totalSumInsured = 0d;
		List<TTrnPremiumQuo> tTrnPremiumQuoList = null;
		for(TravellingEmployeeVO travellingEmployee :tbDetails.getTravellingEmpDets())
		{
			
			if(Utils.isEmpty( travellingEmployee.getSumInsuredDtl().getDeductible()))
			{
				if(!Utils.isEmpty( tbDetails.getTravellingEmpDets() ))
				{
					travellingEmployee.getSumInsuredDtl().setDeductible( tbDetails.getTravellingEmpDets().get( 0 ).getSumInsuredDtl().getDeductible() );
				}	
			}
			//if(Utils.isEmpty(travellingEmployee.getIsToBeDeleted()))
			{
				totalSumInsured = totalSumInsured+travellingEmployee.getSumInsuredDtl().getSumInsured();
			}
			/*here since premium is calculated as a whole and added only into travelBaggageVO,we will be using the same
			for each individual employee also*/
			if(!Utils.isEmpty( tbDetails.getPremium() )){
				PremiumVO prmVO = new PremiumVO();
				prmVO.setPremiumAmt( tbDetails.getPremium().getPremiumAmt() );
				travellingEmployee.setPremium( prmVO );
			}	
			if(!Utils.isEmpty( travellingEmployee.getGprId()  )){
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				gaccPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON, policyVO, new POJO[]{buildingQuo, premiseQuo},new BaseVO[]{locationDetails,travellingEmployee,tbSection}, false,tbSection.getPolicyId(),Long.valueOf(locationDetails.getRiskGroupId() ),Long.valueOf( travellingEmployee.getGprId() ));
			}else
			{
				//Renewal Multiple Id's handling changes, added policy in the query parameter
				gaccPersonQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON, policyVO, new POJO[]{buildingQuo, premiseQuo},new BaseVO[]{locationDetails,travellingEmployee,tbSection}, false,Long.valueOf(locationDetails.getRiskGroupId() ),tbSection.getPolicyId(),ZERO_CONSTANT);
			}
			 ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			 
			 if(Utils.isEmpty( tTrnPremiumQuoList)){
				 tTrnPremiumQuoList = (List<TTrnPremiumQuo>) getHibernateTemplate().find( com.Constant.CONST_FROM_TTRNPREMIUMQUO_PRM_WHERE_PRM_ID_PRMPOLICYID_AND_PRM_ID_PRMBASICRSKID_AND_PRM_ID_PRMRSKCODE_AND_PRM_PRMENDTID_AND_PRM_PRMSTATUS_4_AND_PRM_PRMVALIDITYEXPIRYDATE_END, tbSection.getPolicyId(),
							new BigDecimal(locationDetails.getRiskGroupId()),TB_RISK_CODE,(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),SvcConstants.EXP_DATE);
			 } 
					
		}
			/*  Handling of Premium insertion */
		if(!Utils.isEmpty( tTrnPremiumQuoList )){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, tTrnPremiumQuoList.get( 0 ).getId().getPrmBasicRskId().longValue() );
			handlePremiumInsertion( policyVO,  tbSection,  locationDetails,  tbDetails.getTravellingEmpDets().get( 0 ) ,new POJO[]{buildingQuo, premiseQuo},totalSumInsured,tTrnPremiumQuoList.get( 0 ).getId().getPrmRskId());
		}else{
			 tTrnPremiumQuoList = (List<TTrnPremiumQuo>) getHibernateTemplate().find( com.Constant.CONST_FROM_TTRNPREMIUMQUO_PRM_WHERE_PRM_ID_PRMPOLICYID_AND_PRM_ID_PRMBASICRSKID_AND_PRM_ID_PRMRSKCODE_AND_PRM_PRMENDTID_AND_PRM_PRMSTATUS_4_AND_PRM_PRMVALIDITYEXPIRYDATE_END, tbSection.getPolicyId(),
						new BigDecimal(locationDetails.getRiskGroupId()),TB_RISK_CODE,(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),SvcConstants.EXP_DATE);
			 
			 if(!Utils.isEmpty( tTrnPremiumQuoList)){
				 ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, tTrnPremiumQuoList.get( 0 ).getId().getPrmBasicRskId().longValue() );
			 }
			handlePremiumInsertion( policyVO,  tbSection,  locationDetails,  tbDetails.getTravellingEmpDets().get( 0 ) ,new POJO[]{buildingQuo, premiseQuo},totalSumInsured,BigDecimal.valueOf(Long.valueOf( tbDetails.getTravellingEmpDets().get( 0 ).getGprId())));
		}	
		
		return gaccPersonQuo;
	}

	/*
	 * This method handles the insertion into the TTrnPremium table
	 * @param PolicyVO,SectionVO
	 * @param LocationVO,TravellingEmployeeVO
	 * @param POJO[]
	 * 
	 */
	private void handlePremiumInsertion( PolicyVO policyVO, SectionVO tbSection, LocationVO locationDetails, TravellingEmployeeVO teDetails, POJO[] depsPOJO, Double totalSumInsured ,BigDecimal prmRiskId){

		teDetails.setSumInsured( totalSumInsured );
		BaseVO[] depsVO = { locationDetails,teDetails,tbSection};
		
		TTrnPremiumQuo premium = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, depsPOJO, depsVO, false, Long.valueOf( tbSection.getPolicyId() ),prmRiskId,BigDecimal.valueOf( (Long)ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID)),TB_BASIC_COVER,TB_COVER_TYPE,TB_COVER_SUB_TYPE );
		if(!Utils.isEmpty(  premium.getPrmPremium())){
			PremiumHelper.logPremiumInfo( "Section Level premium for TB :" +premium.getPrmPremium().doubleValue());
			if(Utils.isEmpty( teDetails.getPremium() )){
				teDetails.setPremium( new PremiumVO() );
			}	
			teDetails.getPremium().setPremiumAmt(  premium.getPrmPremium().doubleValue());
		}else{
			PremiumHelper.logPremiumInfo( "Section Level premium for TB : null - defaulting to 0.0");
			if(Utils.isEmpty( teDetails.getPremium() )){
				teDetails.setPremium( new PremiumVO() );
			}	
			teDetails.getPremium().setPremiumAmt( 0.0 );
		}
	}

	private boolean isSectionPresent( int sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}
	
	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId )){
			
			LocationVO locationDetails = (LocationVO)depsVO[0];
			TravellingEmployeeVO travelEmployeeDetails = (TravellingEmployeeVO)depsVO[1];
			SectionVO tbSection = (SectionVO)depsVO[2];
		
			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );
			
			TTrnGaccPersonQuo tTrnGaccPersonQuo = getGaccPersonPOJO(policyVO,locationDetails,travelEmployeeDetails,tbSection,deps,occupancy);
			mappedPOJO = tTrnGaccPersonQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			LocationVO locationDetails = (LocationVO)depsVO[0];
			TravellingEmployeeVO travelEmployeeDetails = (TravellingEmployeeVO)depsVO[1];
			SectionVO tbSection = (SectionVO)depsVO[2];

			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );

			TTrnPremiumQuo premiumQuo = getPremiumPojo( travelEmployeeDetails, policyVO, tbSection.getPolicyId(), locationDetails,occupancy );

			mappedPOJO = premiumQuo;
		}
		return mappedPOJO;
	}
	
	/*  
	 * This method is used to construct the Premium record 
	 */
	private TTrnPremiumQuo getPremiumPojo( TravellingEmployeeVO tbDetails, PolicyVO policyDetails, Long policyId,LocationVO locationDetails, TMasOccupancy occupancy ){
		
		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.parseInt( TB_RISK_CODE.toString() ) );
		
		
		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );	
		premiumQuoId.setPrmRskId( BigDecimal.valueOf( Long.valueOf( tbDetails.getGprId() ) ));

		premiumQuoId.setPrmRskCode( Integer.parseInt( TB_RISK_CODE.toString() ) );
		premiumQuoId.setPrmCovCode( Short.valueOf( TB_COV_CODE.toString() ) );
		premiumQuoId.setPrmCstCode( Short.valueOf( TB_CST_CODE.toString() ) );
		premiumQuoId.setPrmCtCode( Short.valueOf( TB_CT_CODE.toString() ) );
		premiumQuoId.setPrmPolicyId( policyId );
		premiumQuoId.setPrmValidityStartDate( (Date)ThreadLevelContext.get( "VSD" ) );
		premiumQuo.setId(premiumQuoId);
		premiumQuo.setPrmEndtId( TB_ENDT_ID );
		premiumQuo.setPrmClCode( Short.valueOf( TB_CLASS_CODE.toString() ) );
		premiumQuo.setPrmPtCode( TB_POLICY_TYPE );
		premiumQuo.setPrmRcCode( TB_RC_CODE );
		premiumQuo.setPrmRscCode( TB_RSC_CODE );
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER);
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		///premiumQuo.setPrmRiRskCode( occupancy.getOcpRiRskCode() );
		premiumQuo.setPrmRiRskCode(TB_RI_RISK_CODE);
		premiumQuo.setPrmRtCode( occupancy.getOcpRtCode() );
		if(!Utils.isEmpty(tbDetails.getSumInsuredDtl()) && !Utils.isEmpty(tbDetails.getSumInsuredDtl().getSumInsured())) {
		
			premiumQuo.setPrmSumInsured(new BigDecimal(tbDetails.getSumInsured()));
		}
		if(!Utils.isEmpty( tbDetails.getSumInsuredDtl().getDeductible() )){
			premiumQuo.setPrmCompulsoryExcess(BigDecimal.valueOf(tbDetails.getSumInsuredDtl().getDeductible().doubleValue()));
		}	
		
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		
		SvcUtils.setAuditDetailsforPrm(premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
		if( !Utils.isEmpty( tbDetails.getPremium() ) ){
			if( !Utils.isEmpty( tbDetails.getPremium().getPremiumAmt() ) ){
				premiumQuo.setPrmPremium( new BigDecimal(String.valueOf(tbDetails.getPremium().getPremiumAmt() ) ));
				premiumQuo.setPrmPremiumActual( new BigDecimal(String.valueOf(tbDetails.getPremium().getPremiumAmt() )));
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		else{
			setZeroPrmValue( premiumQuo );
		}
		
		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );
		
		return premiumQuo;
	}
	
	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
	}

	private TTrnGaccPersonQuo getGaccPersonPOJO( PolicyVO policyVO, LocationVO locationdetails, TravellingEmployeeVO travellingEmployeeDetails, SectionVO tbSection,POJO[] deps,TMasOccupancy occupancy){
		
		TTrnBuildingQuo tTrnBuildingQuo = (TTrnBuildingQuo)deps[0];
		TTrnWctplPremiseQuo trnWctplPremiseQuo = (TTrnWctplPremiseQuo)deps[1];
		Integer userId = SvcUtils.getUserId(policyVO);
		TTrnGaccPersonQuo tTrnGaccPersonQuo = new TTrnGaccPersonQuo(); 
		
		tTrnGaccPersonQuo.setGprAAddress1( null );
		tTrnGaccPersonQuo.setGprAAddress2( null );
		tTrnGaccPersonQuo.setGprAAddress3( null );
		
		/* Calculate age from DAO to PoicyEffective Date */
		
		//Modified by Shreekar for CR:-59310 
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR);
		String policyStartYear = sdf.format( policyVO.getScheme().getEffDate() );
		String dobYear = "";
		Integer gprAge = 0;
		if(travellingEmployeeDetails.getDateOfBirth().contains("-")){
			String age[]= travellingEmployeeDetails.getDateOfBirth().split("-");
			travellingEmployeeDetails.setDateOfBirth(age[2]+"/"+age[1]+"/"+age[0]);
		}
		if (travellingEmployeeDetails.getDateOfBirth().split("/").length > 2) {
			dobYear = travellingEmployeeDetails.getDateOfBirth().split("/")[2];
			gprAge = Integer.valueOf(policyStartYear) - Integer.valueOf(dobYear) ;
			tTrnGaccPersonQuo.setGprAge( gprAge.shortValue() );
		}
		
		//Modified by Shreekar for CR:-59310 END	
	/*	tTrnGaccPersonQuo.setGprAgrLmt( gprAgrLmt );
		tTrnGaccPersonQuo.setGprAName( gprAName ); */
		Long cntSequence = NextSequenceValue.getNextSequence( GACC_PERSON_SEQ,null,null, getHibernateTemplate() );
		tTrnGaccPersonQuo.setGprBasicRiskId(  cntSequence  );
		tTrnGaccPersonQuo.setGprBasicRskCode( TB_RISK_CODE );
		
		if(!Utils.isEmpty( tTrnBuildingQuo )){
			tTrnGaccPersonQuo.setGprBldId( tTrnBuildingQuo.getId().getBldId() );
		}
		else if(!Utils.isEmpty( trnWctplPremiseQuo )){
			tTrnGaccPersonQuo.setGprBldId(trnWctplPremiseQuo.getId().getWbdId());
		}
		Date dateOfBirth = null;
		try{
			sdf = new SimpleDateFormat(DAYMONTHYEAR);
			dateOfBirth =  sdf.parse( travellingEmployeeDetails.getDateOfBirth() );
		}catch(ParseException exception){
			throw new BusinessException( ERROR_MSG_2, exception, "" );
		}
		tTrnGaccPersonQuo.setGprDateOfBirth( dateOfBirth );
		//tTrnGaccPersonQuo.setGprDateOfJoining( gprDateOfJoining );
		tTrnGaccPersonQuo.setGprDescription( null );
		tTrnGaccPersonQuo.setGprEAddress1( null );
		tTrnGaccPersonQuo.setGprEAddress2( null );
		tTrnGaccPersonQuo.setGprEAddress3( null );
		tTrnGaccPersonQuo.setGprEmail(null);
		tTrnGaccPersonQuo.setGprEName( travellingEmployeeDetails.getName() );
		tTrnGaccPersonQuo.setGprEndDate( policyVO.getEndDate() );
		tTrnGaccPersonQuo.setGprEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID) );
		tTrnGaccPersonQuo.setGprFax(null);
		//tTrnGaccPersonQuo.setGprGender( gprGender );
		tTrnGaccPersonQuo.setGprGsm( null ); 
		tTrnGaccPersonQuo.setGprModifiedBy( userId );
		tTrnGaccPersonQuo.setGprModifiedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
	/*	tTrnGaccPersonQuo.setGprNtyEDesc( gprNtyEDesc ); */
		tTrnGaccPersonQuo.setGprOcCode( occupancy.getOcpCode() );
		tTrnGaccPersonQuo.setGprPersonId( null );
		tTrnGaccPersonQuo.setGprPolicyId( tbSection.getPolicyId() );
		tTrnGaccPersonQuo.setGprPreparedBy( userId  );
		tTrnGaccPersonQuo.setGprPreparedDt( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		tTrnGaccPersonQuo.setGprRcCode( TB_RC_CODE.longValue() );
		tTrnGaccPersonQuo.setGprRelation( null );
		tTrnGaccPersonQuo.setGprRetroactiveDate( null );
		tTrnGaccPersonQuo.setGprRiRskCode( TB_RI_RISK_CODE );
		tTrnGaccPersonQuo.setGprRskCode( TB_RISK_CODE.longValue() );
		tTrnGaccPersonQuo.setGprRtCode( TB_RT_CODE.longValue() );
		tTrnGaccPersonQuo.setGprSalary( null ); 
		tTrnGaccPersonQuo.setGprStartDate( policyVO.getStartDate() );
		tTrnGaccPersonQuo.setGprStatus( GPR_STATUS );
		tTrnGaccPersonQuo.setGprSumInsured( new BigDecimal(travellingEmployeeDetails.getSumInsuredDtl().getSumInsured() ));
		tTrnGaccPersonQuo.setGprTelephone(  null );
		tTrnGaccPersonQuo.setGprTradeGroup( occupancy.getOcpTradeCode().longValue() );
		tTrnGaccPersonQuo.setGprValidityExpiryDate( SvcConstants.EXP_DATE);
		tTrnGaccPersonQuo.setGprZip( null );
		return tTrnGaccPersonQuo;
	}

	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeCreated = false;
		
		LocationVO locationDetails = (LocationVO)depsVO[0];
		TravellingEmployeeVO travelEmployeeDetails = (TravellingEmployeeVO)depsVO[1];
		SectionVO tbSection = (SectionVO)depsVO[2];
		
		TTrnGaccPersonQuo gaccPersonQuo = null;
		Boolean isToBeDeleted = false;
		Boolean premiumRecordAdded = true;
		
		List<TTrnPremiumQuo> tTrnPremiumQuoList = null;
			
		tTrnPremiumQuoList = (List<TTrnPremiumQuo>) getHibernateTemplate().find( com.Constant.CONST_FROM_TTRNPREMIUMQUO_PRM_WHERE_PRM_ID_PRMPOLICYID_AND_PRM_ID_PRMBASICRSKID_AND_PRM_ID_PRMRSKCODE_AND_PRM_PRMENDTID_AND_PRM_PRMSTATUS_4_AND_PRM_PRMVALIDITYEXPIRYDATE_END, tbSection.getPolicyId(),
							new BigDecimal(locationDetails.getRiskGroupId()),TB_RISK_CODE,(Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),SvcConstants.EXP_DATE);
		if(Utils.isEmpty( tTrnPremiumQuoList )){
			premiumRecordAdded = false;
		}
		
		if(!Utils.isEmpty(travelEmployeeDetails.getIsToBeDeleted())){
			if(travelEmployeeDetails.getIsToBeDeleted()){
				isToBeDeleted = true;
			}
		}	
		
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId )){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, false );
			
			if( !Utils.isEmpty( travelEmployeeDetails.getGprId() )){
				try{
					gaccPersonQuo = (TTrnGaccPersonQuo)DAOUtils.getExistingValidStateRecord(SvcConstants.TABLE_ID_T_TRN_GACC_PERSON_LOAD,policyVO.getAppFlow(), getHibernateTemplate(), false, null,tbSection.getPolicyId(),Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(travelEmployeeDetails.getGprId()));	
				}catch(BusinessException exception){
					throw new BusinessException( ERROR_MSG_1, exception, "ID from of the basic section is mandatory, no data in table" );
				}
			}
			// NO premium record exists as such for now the tobeCreated
			if( !Utils.isEmpty( locationDetails.getRiskGroupId()) && Utils.isEmpty( gaccPersonQuo ) && !isToBeDeleted )
			{
						isToBeCreated = true;
			}
			if( !Utils.isEmpty( locationDetails.getRiskGroupId()) && Utils.isEmpty( gaccPersonQuo ) && !isToBeDeleted && !premiumRecordAdded)
			{
						ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, true );
			}
			
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			
			isToBeCreated = ( (Boolean)ThreadLevelContext.get( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED ) ) ? true : false; 
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_CREATED, null );
		}
		return isToBeCreated;
	}

	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeDeleted = false;
		
		LocationVO locationDetails = (LocationVO)depsVO[0];
		SectionVO tbSection = (SectionVO)depsVO[2];
		TravelBaggageVO tbDetails = (TravelBaggageVO) PolicyUtils.getRiskGroupDetails( locationDetails, tbSection );
	
		if(Utils.isEmpty( tbDetails.getIsToBeDeleted() )) {
			return false;
		}
		/* deletion logic if any will come here */
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId )){
			ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, false );
			TravellingEmployeeVO travelEmployeeDetails = (TravellingEmployeeVO)depsVO[1];
			
			if(!Utils.isEmpty(travelEmployeeDetails.getIsToBeDeleted())){
				if(travelEmployeeDetails.getIsToBeDeleted()){
					isToBeDeleted = true;
					ThreadLevelContext.set( SvcConstants.TLC_KEY_PRM_TO_BE_DELETED, true );
				}
			}
		
		}
		return isToBeDeleted;
	}

	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){
		
		TravellingEmployeeVO travelEmployeeVO = (TravellingEmployeeVO)depsVO[1];
		
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnGaccPersonQuo ){
					travelEmployeeVO.setBasicRiskId( ( (TTrnGaccPersonQuo) mappedRecord ).getId().getGprId() );
					Long gpridTemp = ( (TTrnGaccPersonQuo) mappedRecord ).getId().getGprId();
					travelEmployeeVO.setGprId( gpridTemp.toString() );
				}
			}
		}
	}
	
	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){

		/* Remove the record from context as deletion is sucessfull */

	}

	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
	}

	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){
		POJOId id = null;
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){
			TTrnGaccPersonQuoId tid = new TTrnGaccPersonQuoId();
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnGaccPersonQuo ){
					tid.setGprId( ( (TTrnGaccPersonQuo) mappedRecord ).getGprBasicRiskId() );
					id = tid;
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
		
		if( SvcConstants.TABLE_ID_T_TRN_GACC_PERSON.equals( tableId ) ){
			TTrnGaccPersonQuoId existingTId = (TTrnGaccPersonQuoId) existingId;
			TTrnGaccPersonQuoId tid = new TTrnGaccPersonQuoId();
			tid.setGprId( existingTId.getGprId() );

			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuoId pId ;
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		return id;
	}

	@Override
	public List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails currRgd ){
		// TODO Auto-generated method stub
		return new ArrayList<Contents>();
	}

	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){
		// TODO Auto-generated method stub
		return new POJO[]{};
	}
	protected void handleAdditionalCovers( SectionVO sectionVO, PolicyVO policyVO ){
		
		/* No Additional Covers as of now for TravellBaggage*/ 
	}
	
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		removeDeletedRowsFromContext(policyVO);
		updateSectionLevelSIANDPremium( policyVO );
		updateEndtText( policyVO );
		super.sectionPostProcessing( policyVO );
	}

	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) )
		{
			SectionVO tbSection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( tbSection );
			TravelBaggageVO  travelBaggageDetails = (TravelBaggageVO) PolicyUtils.getRiskGroupDetails( locationDetails, tbSection );
			DAOUtils.deletePrevEndtText( tbSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),TB_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			
			DAOUtils.addGPAUnnamedforendorsementFlow( tbSection.getPolicyId(), policyVO, TB_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
		
			DAOUtils.addGPANamedforendorsementFlow( tbSection.getPolicyId(), policyVO, TB_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			DAOUtils.deleteGPAUnnamedforendorsementFlow( tbSection.getPolicyId(), policyVO, TB_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			DAOUtils.deleteGPANamedforendorsementFlow( tbSection.getPolicyId(), policyVO, TB_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			//DAOUtils.updateEndTextForRiskAdd( tbSection.getPolicyId(), policyVO,tbSection.getSectionId());
			
			
			for(TravellingEmployeeVO travelNammedEmpVO : travelBaggageDetails.getTravellingEmpDets())
			{
				if(!Utils.isEmpty(travelNammedEmpVO.getGprId()))
				{
					DAOUtils.updateGPANamedforendorsementFlow(tbSection.getPolicyId(),policyVO, 
							Long.valueOf(locationDetails.getRiskGroupId()), Long.valueOf(locationDetails.getRiskGroupId()), 
							TB_SECTION_ID, Long.valueOf(travelNammedEmpVO.getGprId()));
				}
			}
			DAOUtils.updateDeductibleforendorsementFlow( tbSection.getPolicyId(), policyVO,tbSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId()) );
						
			DAOUtils.updateTotalSITextforendorsementFlow( tbSection.getPolicyId(), policyVO,tbSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId()) );
		}
	}
	/*
	 * This method is used to remove the deleted rows fron the context 
	 * after actual deletion from the data base
	 * @param PolicyVOs
	 */
	private void removeDeletedRowsFromContext(PolicyVO policyVO){
		
		SectionVO tbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_TB );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( tbSection );
		TravelBaggageVO travelBaggageVO = (TravelBaggageVO) PolicyUtils.getRiskGroupDetails( locationDetails, tbSection );
		
		boolean deletionflag = false;
		List<TravellingEmployeeVO> toBeDeletedVOs = new ArrayList<TravellingEmployeeVO>();
		for( TravellingEmployeeVO teVO : travelBaggageVO.getTravellingEmpDets() ){
			if( !Utils.isEmpty( teVO.getIsToBeDeleted() ) && teVO.getIsToBeDeleted() ){
				toBeDeletedVOs.add( teVO );
				deletionflag = true;
			}
		}
		if( deletionflag ){
			for( TravellingEmployeeVO toBeDeletedVO : toBeDeletedVOs ){

				( (TravelBaggageVO) travelBaggageVO ).getTravellingEmpDets().remove( toBeDeletedVO );
			}
		}
	}
	
	/*
	 * This method is used to update the Travel level SumInsured and Premium Amount
	 * @param PolicyVO
	 * 
	 */
	private void updateSectionLevelSIANDPremium( PolicyVO policyVO ){

		SectionVO tbSection = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_TB );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( tbSection );
		TravelBaggageVO travelBaggageVO = (TravelBaggageVO) PolicyUtils.getRiskGroupDetails( locationDetails, tbSection );
		
		Double sumInsured = 0.0;
		for(TravellingEmployeeVO travellingEmployee :travelBaggageVO.getTravellingEmpDets()){
			sumInsured = sumInsured +travellingEmployee.getSumInsuredDtl().getSumInsured();
		}
		travelBaggageVO.setSumInsured( sumInsured );
	}
}
