/**
 * 
 */
package com.rsaame.pas.money.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.constants.CommonErrorKeys;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnGaccBuildingQuo;
import com.rsaame.pas.dao.model.TTrnGaccBuildingQuoId;
import com.rsaame.pas.dao.model.TTrnGaccCashDetailsQuo;
import com.rsaame.pas.dao.model.TTrnGaccCashDetailsQuoId;
import com.rsaame.pas.dao.model.TTrnGaccCashQuo;
import com.rsaame.pas.dao.model.TTrnGaccCashQuoId;
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
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.IVOId;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SafeVO;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * @author m1014644
 *
 */
public class MoneyDAO extends BaseSectionSaveDAO implements IMoneyDAO{

	private final static Logger LOGGER = Logger.getLogger( MoneyDAO.class );
	private final static Long NEW_QUOTE_ENDID = Long.valueOf( 0 );
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );
	private final static Integer MONEY_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_SECTION" ) );
	private final static Integer MONEY_CLASS_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "MONEY_CLASS" ) );
	private final static String CASH_ID = "SEQ_GACC_CASH_ID";
	private final static String CASH_DETAILS_ID = "SEQ_GACC_CASH_DETAILS_ID";


	/* (non-Javadoc)
	 * @see com.rsaame.pas.money.dao.IMoneyDAO#loadMoneySection(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO loadMoneySection( BaseVO baseVO ){

		return null;
	}

	@Override
	protected BaseVO saveSection( BaseVO input ){
		PolicyVO policyVO = (PolicyVO) input;

		/* Let us get the system date right now and use from here on for this transaction. */
		/* Not required to set it again as it is already set within BaseSectionSaveDAO */
		//ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

		SectionVO section = PolicyUtils.getSectionVO( policyVO, SvcConstants.SECTION_ID_MONEY );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		MoneyVO money = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, section );

		/* Handle the building data. Premium update for the building will be handled inside
		 * this method. */

		TTrnGaccBuildingQuo trnGaccBuildingQuo = handleBuilding( policyVO, section, locationDetails, money );

		/* Handle the building's content. Premium update for the contents will be handled inside
		 * this method. */
		handleCash( policyVO, section, locationDetails, money, trnGaccBuildingQuo );

		return policyVO;
	}
	
	private TTrnGaccBuildingQuo handleBuilding( PolicyVO policyVO, SectionVO moneySection, LocationVO locationVO, MoneyVO moneyVO ){
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
				  buildingQuo = (TTrnBuildingQuo) DAOUtils.getExistingValidStateRecord(SvcConstants.TABLE_ID_T_TRN_BUILDING ,policyVO.getAppFlow(), getHibernateTemplate(), false, ( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), basicSection.getPolicyId(), Long.valueOf( locationVO.getRiskGroupId() ));
					//buildingQuo = (TTrnBuildingQuo) getHibernateTemplate().find( "from TTrnBuildingQuo buldQ where buldQ.id.bldId=?", Long.valueOf( locationVO.getRiskGroupId() ) )
					//		.get( 0 );
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
					premiseQuo = (TTrnWctplPremiseQuo) DAOUtils.getExistingValidStateRecord(SvcConstants.TABLE_ID_T_TRN_WCTPL_PREMISE ,policyVO.getAppFlow(), getHibernateTemplate(), false, ( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), basicSection.getPolicyId(), Long.valueOf( locationVO.getRiskGroupId() ));
					//premiseQuo = (TTrnWctplPremiseQuo) getHibernateTemplate().find( "from TTrnWctplPremiseQuo preQ where preQ.id.wbdId=?",
					//		Long.valueOf( locationVO.getRiskGroupId() ) ).get( 0 ); /* LocationVO.RiskGroupId will be same as wbdId as PL is the basic section */
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

		/* Process the T_TRN_GACC_BUILDING(_QUO) record. */
		//Renewal Multiple Id's handling changes, added policy in the query parameter
		TTrnGaccBuildingQuo gaccBuildingQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_BUILDING, policyVO, new POJO[]{ buildingQuo, premiseQuo }, new BaseVO[]{ moneyVO }, false, moneySection.getPolicyId(),
				moneyVO.getBasicRiskId() ); 
		
		/* Setting buildingId into BasicRiskId for a particular section ( or for a particular class code ) into ThreadLevelContext which is stored into the extra column added to 
		 * T_TRN_SECTION_LOCATION / T_TRN_SECTION_LOCATION_QUO  
		 * */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject( gaccBuildingQuo.getId().getGbdId() ) );
		
		return gaccBuildingQuo;

	}

	private void handleCash( PolicyVO policyVO, SectionVO moneySection, LocationVO locationDetails, MoneyVO moneyVO, TTrnGaccBuildingQuo trnGaccBuildingQuo ){

		java.util.List<Contents> contentsList = moneyVO.getContentsList();
		DataHolderVO<Double> NULL_CIR_HOLDER = new DataHolderVO<Double>();
		NULL_CIR_HOLDER.setData( null );

		for( Contents content : contentsList ){
			
			/* Check if the content is not added then skip the execution process for that content */
			if(  !Utils.isEmpty( content.getRiskId() ) && content.getRiskId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
				content.setRiskId( null );
			}
			if( Utils.isEmpty( content.getRiskId() ) && isSumInsuredZero( content )){
				continue;
			}
			
			/* First, process the GACC_CASH record. */
			//Renewal Multiple Id's handling changes, added policy in the query parameter
			TTrnGaccCashQuo cashQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_CASH, policyVO, new POJO[]{ trnGaccBuildingQuo }, new BaseVO[]{ content }, false,moneySection.getPolicyId(),
					content.getRiskId() );

			/* Next, process the premium record for the GACC_CASH record. */
			TTrnPremiumQuo premium = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{ cashQuo }, new BaseVO[]{ content, NULL_CIR_HOLDER }, false, moneySection.getPolicyId(),
					BigDecimal.valueOf( cashQuo.getId().getGchId() ), BigDecimal.valueOf( ( (TTrnGaccBuildingQuoId) trnGaccBuildingQuo.getId() ).getGbdId() ),
					Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(), Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_COVER_TYPE" ) ),
					Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_COVER_SUB_TYPE" ) ) );

			if(!Utils.isEmpty( premium.getPrmPremium() ) )			{
				if(!Utils.isEmpty(content.getPremium())){
					content.getPremium().setPremiumAmt( premium.getPrmPremium().doubleValue() );	
				}
				
			}
			
			handleSafeDetails(cashQuo,moneyVO,policyVO,moneySection.getPolicyId());
						
			handleCIR(cashQuo,moneyVO,policyVO, moneySection.getPolicyId());
		}	
			
	}

	

	//Renewal Multiple Id's handling changes :Changed method signature to pass policy Id
	private void handleCIR( TTrnGaccCashQuo cashQuo, MoneyVO moneyVO, PolicyVO policyVO , Long policyId){
		
		/* If this content is "Cash in Safe", then we have to process even the Safe Details. */
		if( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_RISK_TYPES_7 ).equalsIgnoreCase( cashQuo.getGchRtCode() + "-" + cashQuo.getGchRcCode() + "-" + cashQuo.getGchRscCode() ) ){
			/* Now, if the user has provided safe details, process them one-by-one. */
			if( !Utils.isEmpty( moneyVO.getCashResDetails() ) ){
				for( CashResidenceVO cashResidenceDetails : moneyVO.getCashResDetails() ){	
					//Renewal Multiple Id's handling changes, added policy in the query parameter
						handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS, policyVO, new POJO[]{ cashQuo }, new BaseVO[]{ cashResidenceDetails }, false, policyId, cashResidenceDetails.getId() );
				}
			}
		}
		
	}

	private TTrnGaccBuildingQuo getgaccBuildingQuoPojo( SectionVO moneySection, TTrnBuildingQuo buildingQuo , MoneyVO moneyVO){
		TTrnGaccBuildingQuo gaccBldQuo = null;
	/*	try{
			 Check if the GACC_BUILDING record is already available for this location. 
			List gaccBlds = getHibernateTemplate().find( "from TTrnGaccBuildingQuo t where t.gbdBldId=? and t.gbdPolicyId=?", buildingQuo.getId().getBldId(),
					moneySection.getPolicyId() );

			if( !Utils.isEmpty( gaccBlds ) ){
				gaccBldQuo = (TTrnGaccBuildingQuo) gaccBlds.get( 0 );
			}
		}
		catch( DataAccessException e ){
			throw new SystemException( CommonErrorKeys.UNKNOWN_ERROR, e, "Error while trying to fetch existing T_TRN_GACC_BUILDING record" );
		}*/

		/* If the GACC_BUILDING record was not found, map the values into a new instance and set the Gbd_Id from a new sequence. Else,
		 * map into the fetched GACC_BUILDING record (pojo instance). */
		//if( Utils.isEmpty( gaccBldQuo ) ){
			gaccBldQuo = BeanMapper.map( buildingQuo, TTrnGaccBuildingQuo.class );

			/*TTrnGaccBuildingQuoId id = new TTrnGaccBuildingQuoId();
			id.setGbdId( NextSequenceValue.getNextSequence( "SEQ_GACC_BUILDING_ID", getHibernateTemplate() ) );
			id.setGbdValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );

			gaccBldQuo.setId( id );*/
		//}
		/*else{
			gaccBldQuo = BeanMapper.map( buildingQuo, gaccBldQuo );
			getHibernateTemplate().evict( TTrnGaccBuildingQuo.class );
		}*/
		/* Setting section level sum insured */
			
		/*
		 * FIX: Backend fix: this field should be blank in case of sbs policy - 22-Jul_2012
		 */
		//gaccBldQuo.setGbdSumInsured( BigDecimal.valueOf( getSectionLevelSumInsured(moneyVO) ) );
			
		gaccBldQuo.setGbdPolicyId( moneySection.getPolicyId() ); /* Set the policy Id for Money here. We shouldn't map it from TTrnBuildingQuo
																	* the policyId in that will be that of Class Code 2. */
		gaccBldQuo.setGbdFlatNo( null ); // same as that of mississipi value
		gaccBldQuo.setGbdBusinessCode( null ); // same as that of mississipi value
		gaccBldQuo.setGbdPremiseType( false ); //In Mississipi set as 0 so its set to false  in PAS
		gaccBldQuo.setGbdCbCode( null ); // same as that of mississipi value
		gaccBldQuo.setGbdRetroactiveDate( null ); // same as that of mississipi value

		gaccBldQuo.setGbdValidityExpiryDate( SvcConstants.EXP_DATE );
		gaccBldQuo.setGbdRiRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_BASIC_RI_RSK_CODE ) ) );
		//gaccBldQuo.getId().setGbdValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		/** Overriding risk code as it should be money basic risk code */
		gaccBldQuo.setGbdRskCode( Long.parseLong( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_BASIC_RISK_CODE ) ) );

		return gaccBldQuo;
	}

	private TTrnGaccCashDetailsQuo getBasicCashDetailsPojo( TTrnGaccCashQuoId tTrnGaccCashQuoId, long policyId, PolicyVO policyVO ){
		TTrnGaccCashDetailsQuo cashDetails = new TTrnGaccCashDetailsQuo();

		cashDetails.setGcdGchId( tTrnGaccCashQuoId.getGchId() );
		System.out.println( "inside getcashDetailsPojo setting end it and policy id -->" + NEW_QUOTE_ENDID + " " + policyId );
		cashDetails.setGcdPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		cashDetails.setGcdPolPolicyId( policyId );
		cashDetails.setGcdValidityExpiryDate( SvcConstants.EXP_DATE );

		Integer userID = SvcUtils.getUserId( policyVO );
		cashDetails.setGcdPreparedBy( userID );
		cashDetails.setGcdPreparedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD) );
		cashDetails.setGcdModifiedBy( userID );
		cashDetails.setGcdModifiedDt( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_SYSDATE ) );

		return cashDetails;
	}

	private TTrnGaccCashDetailsQuo getcashDetailsPojo( TTrnGaccCashQuoId tTrnGaccCashQuoId, long policyId, CashResidenceVO cashResidense, PolicyVO policyVO ){
		TTrnGaccCashDetailsQuo cashDetails = getBasicCashDetailsPojo( tTrnGaccCashQuoId, policyId, policyVO );
		//TTrnGaccCashDetailsQuoId id = constructGaccCashDetailsQuoId( cashResidense );

		//	cashDetails.setId( id );

		if( !Utils.isEmpty( cashResidense ) ){
			if( !Utils.isEmpty( cashResidense.getEmpName() ) ) cashDetails.setGcdCashResEmpEName( cashResidense.getEmpName() );
			cashDetails.setGcdCashResEmpAName( null );
			if( !Utils.isEmpty( cashResidense.getOccupation() ) ) cashDetails.setGcdCashResOccupation( cashResidense.getOccupation() );
			if( !Utils.isEmpty( cashResidense.getSumInsuredDets() ) && !Utils.isEmpty( cashResidense.getSumInsuredDets().getSumInsured() ) ){
				cashDetails.setGcdCashResAmt( BigDecimal.valueOf( cashResidense.getSumInsuredDets().getSumInsured() ) );
			}
		}
		return cashDetails;
	}

	private TTrnGaccCashDetailsQuo getcashDetailsPojo( TTrnGaccCashQuoId tTrnGaccCashQuoId, long policyId, SafeVO safeDetails, PolicyVO policyVO ){

		TTrnGaccCashDetailsQuo cashDetails = getBasicCashDetailsPojo( tTrnGaccCashQuoId, policyId, policyVO );
		//TTrnGaccCashDetailsQuoId id = constructGaccCashDetailsQuoId( safeDetails );
		//cashDetails.setId( id );

		if( !Utils.isEmpty( safeDetails ) ){
			if( !Utils.isEmpty( safeDetails.getMake() ) ) cashDetails.setGcdSafeDwrMake( safeDetails.getMake() );
			if( !Utils.isEmpty( safeDetails.getWeight() ) ) cashDetails.setGcdSafeDwrWeightKg( BigDecimal.valueOf( safeDetails.getWeight() ) );
			if( !Utils.isEmpty( safeDetails.getHeight() ) ) cashDetails.setGcdSafeDwrHeightCms( BigDecimal.valueOf( safeDetails.getHeight() ) );
			if( !Utils.isEmpty( safeDetails.getWidth() ) ) cashDetails.setGcdSafeDwrWidthCms( BigDecimal.valueOf( safeDetails.getWidth() ) );
			if( !Utils.isEmpty( safeDetails.getAnchored() ) ) cashDetails.setGcdSafeDwrAnchoredFlag( safeDetails.getAnchored() );
		}

		return cashDetails;
	}


	private TTrnPremiumQuo getPremiumPojo( Contents contentVO, PolicyVO policyDetails, TTrnGaccCashQuo cashQuo, MoneyVO moneyVO, Double cashResidenceSumInsured ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		TTrnPremiumQuoId id = new TTrnPremiumQuoId();

		id.setPrmPolicyId( cashQuo.getGchPolicyId() );
		id.setPrmBasicRskCode( cashQuo.getGchBasicRskCode() );

		id.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_COVER" ) ) );
		id.setPrmCtCode( Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_COVER_TYPE" ) ) );
		id.setPrmCstCode( Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_COVER_SUB_TYPE" ) ) );

		/** SK : Changes */
		/**
		 * Premium Basic Risk Id will 
		 */
		//id.setPrmBasicRskId( BigDecimal.valueOf( cashQuo.getGchBasicRiskId() ) );
		id.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );
		id.setPrmRskCode( cashQuo.getGchRskCode().intValue() );

		/* SK : Changes /
		/* Premium Risk Id should always linked to Contents Id
		 * and Premium Basic Risk Id should be linked to basic risk id
		 * i.e gbd_id from T_TRN_GACC_BUILDING/QUO */
		id.setPrmRskId( BigDecimal.valueOf( cashQuo.getId().getGchId() ) );

		/*
		 * ValidityStartDate to be cascaded from PolicyVO.validityStartDate
		 * which is updated by General Info Save Operation. Hence the same
		 * validityStartDate to be carried across all DB entries for the
		 * Quote
		 */
		id.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );

		premiumQuo.setId( id );
		premiumQuo.setPrmClCode( Short.valueOf( Utils.getSingleValueAppConfig( "MONEY_CLASS" ) ) );
		premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		if( !Utils.isEmpty( contentVO ) && !Utils.isEmpty( contentVO.getCover() ) )
			premiumQuo.setPrmSumInsured( contentVO.getCover() );
		else
			// If cashResidenceSumInsured is not provided then cashResidenceSumInsured is null so setting null value directly.
			premiumQuo.setPrmSumInsured( (!Utils.isEmpty( cashResidenceSumInsured ) ? new BigDecimal( cashResidenceSumInsured ) : null ));
		premiumQuo.setPrmRate( null );// Has to  be set.... comes from UI
		if( !Utils.isEmpty( contentVO ) && !Utils.isEmpty( contentVO.getDeductibles() ) ) premiumQuo.setPrmCompulsoryExcess( contentVO.getDeductibles() );
		premiumQuo.setPrmVoluntaryExcess( null );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		premiumQuo.setPrmExcessRate( null );// confirm
		premiumQuo.setPrmRiRskCode( cashQuo.getGchRiRskCode() );
		premiumQuo.setPrmSiInd( null );
		premiumQuo.setPrmStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate() );// Not specified.
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE);
		premiumQuo.setPrmSumInsuredCurr( null );
		premiumQuo.setPrmPremiumCurr( null );

		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyDetails, (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		premiumQuo.setPrmRiLocCode( SvcConstants.APP_PRM_RI_LOC_CODE );
		premiumQuo.setPrmRateType( Byte.valueOf( Utils.getSingleValueAppConfig( "MONEY_PRM_RATE_TYPE" ) ) );
		premiumQuo.setPrmOldPremium( BigDecimal.valueOf( 0 ) );
		premiumQuo.setPrmOldSumInsured( BigDecimal.valueOf( 0 ) );
		premiumQuo.setPrmExpPeriodPrm( null );
		premiumQuo.setPrmRenewalLoading( null );
		premiumQuo.setPrmPreparedDt((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
		/** SK : Changes */
		if( !Utils.isEmpty( cashQuo.getGchRtCode() ) ) premiumQuo.setPrmRtCode( cashQuo.getGchRtCode().intValue() );
		if( !Utils.isEmpty( cashQuo.getGchRcCode() ) ) premiumQuo.setPrmRcCode( cashQuo.getGchRcCode().intValue() );
		if( !Utils.isEmpty( cashQuo.getGchRscCode() ) ) premiumQuo.setPrmRscCode( cashQuo.getGchRscCode().intValue() );

		/*Added to enter premium value*/
		if( !Utils.isEmpty( contentVO ) ){
			if( !Utils.isEmpty( contentVO.getPremium() ) ){
				if( !Utils.isEmpty( contentVO.getPremium().getPremiumAmt() ) ){
					premiumQuo.setPrmPremium( new BigDecimal(String.valueOf(contentVO.getPremium().getPremiumAmt() ) ));
					premiumQuo.setPrmPremiumActual( new BigDecimal(String.valueOf( contentVO.getPremium().getPremiumAmt() ) ));
				}
				else{
					setZeroPrmValue( premiumQuo );
				}
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		else{
			if( !Utils.isEmpty( moneyVO.getCashResidencePremium() ) ){
				if( !Utils.isEmpty( moneyVO.getCashResidencePremium().getPremiumAmt() ) ){
					premiumQuo.setPrmPremium( new BigDecimal( moneyVO.getCashResidencePremium().getPremiumAmt() ) );
					premiumQuo.setPrmPremiumActual( new BigDecimal( moneyVO.getCashResidencePremium().getPremiumAmt() ) );
				}else{
					setZeroPrmValue( premiumQuo );
				}
			}
			else{
				setZeroPrmValue( premiumQuo );
			}
		}
		
		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );
		
		return premiumQuo;
	}

	/**
	 * 
	 * @param gbdId
	 * @param counter
	 * @param contentVO
	 * @param policyDetails
	 * @param newPolicyId
	 * @param parDetails
	 * @param plDetails
	 * @param forCashInResidence
	 * @return
	 */

	private TTrnGaccCashQuo getcashQuoPojo( Long gbdId, Contents contentVO, PolicyVO policyDetails, Long newPolicyId ){
		TTrnGaccCashQuo cashQuo = new TTrnGaccCashQuo();
		/*TTrnGaccCashQuoId id = constructGaccCashQuoId( contentVO );
		cashQuo.setId( id );*/

		cashQuo.setGchPolicyId( newPolicyId );
		cashQuo.setGchBasicRiskId( gbdId );
		cashQuo.setGchRskCode( Long.valueOf( Utils.getSingleValueAppConfig( "MONEY_RISK_CODE" ) ) );

		cashQuo.setGchBasicRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_BASIC_RISK_CODE ) ) );
		cashQuo.setGchRiRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_BASIC_RI_RSK_CODE ) ) );
		if( !Utils.isEmpty( contentVO ) ){

			if( !Utils.isEmpty( contentVO.getRiskType() ) ) cashQuo.setGchRtCode( contentVO.getRiskType().longValue() );
			if( !Utils.isEmpty( contentVO.getRiskCat() ) ) cashQuo.setGchRcCode( contentVO.getRiskCat().longValue() );
			if( !Utils.isEmpty( contentVO.getRiskSubCat() ) ) cashQuo.setGchRscCode( contentVO.getRiskSubCat().longValue() );
		}

		if( !Utils.isEmpty( contentVO ) && !Utils.isEmpty( contentVO.getCover() ) ) cashQuo.setGchSumInsured( contentVO.getCover() );

		/*
		* Below are common values irrespective of the flow i.e. Prepack/Flexi
		*/

		cashQuo.setGchMaxAmount( null );

		if( !Utils.isEmpty( policyDetails.getScheme().getEffDate() ) ){
			cashQuo.setGchStartDate( policyDetails.getScheme().getEffDate() );
		}
		else{
			cashQuo.setGchStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		}

		if( !Utils.isEmpty( policyDetails.getEndDate() ) ){
			cashQuo.setGchEndDate( policyDetails.getEndDate() );
		}

		cashQuo.setGchEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		cashQuo.setGchValidityExpiryDate( SvcConstants.EXP_DATE );
		cashQuo.setGchStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );

		fillAuditDetails( cashQuo, policyDetails );

		return cashQuo;
	}


	private void fillAuditDetails( TTrnGaccCashQuo cashQuo, PolicyVO policyDetails ){
		Timestamp sysdate = (Timestamp) ThreadLevelContext.get( com.Constant.CONST_SYSDATE );

		cashQuo.setGchPreparedBy( SvcUtils.getUserId( policyDetails ) ); //User id has to be passed.
		cashQuo.setGchPreparedDt( sysdate );
		cashQuo.setGchModifiedBy( SvcUtils.getUserId( policyDetails ) ); //User id has to be passed.
		cashQuo.setGchModifiedDt( sysdate );
	}


	private boolean isSectionPresent( int sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}

	/**
	 * Overloaded method with Premise Object. This method will be invoked in case PL is the basic section
	 * @param moneySection
	 * @param premiseQuo
	 * @return
	 */
	private TTrnGaccBuildingQuo getgaccBuildingQuoPojo( SectionVO moneySection, TTrnWctplPremiseQuo premiseQuo , MoneyVO moneyVO ){
		TTrnGaccBuildingQuo gaccBldQuo = null;
		try{
			/* Check if the GACC_BUILDING record is already available for this location. */
			List gaccBlds = getHibernateTemplate().find( "from TTrnGaccBuildingQuo t where t.gbdBldId=? and t.gbdPolicyId=?", premiseQuo.getId().getWbdId(),
					moneySection.getPolicyId() );

			if( !Utils.isEmpty( gaccBlds ) ){
				gaccBldQuo = (TTrnGaccBuildingQuo) gaccBlds.get( 0 );
			}
		}
		catch( DataAccessException e ){
			throw new SystemException( CommonErrorKeys.UNKNOWN_ERROR, e, "Error while trying to fetch existing T_TRN_GACC_BUILDING record" );
		}

		/* If the GACC_BUILDING record was not found, map the values into a new instance and set the Gbd_Id from a new sequence. Else,
		 * map into the fetched GACC_BUILDING record (pojo instance). */
		if( Utils.isEmpty( gaccBldQuo ) ){
			gaccBldQuo = BeanMapper.map( premiseQuo, TTrnGaccBuildingQuo.class );

/*			TTrnGaccBuildingQuoId id = new TTrnGaccBuildingQuoId();
			id.setGbdId( NextSequenceValue.getNextSequence( "SEQ_GACC_BUILDING_ID", getHibernateTemplate() ) );
			id.setGbdValidityStartDate( (Date) ThreadLevelContext.get( "VSD" ) );

			gaccBldQuo.setId( id );*/
		}
		else{
			gaccBldQuo = BeanMapper.map( premiseQuo, gaccBldQuo );
		}

		gaccBldQuo.setGbdPolicyId( moneySection.getPolicyId() ); /* Set the policy Id for Money here. We shouldn't map it from TTrnBuildingQuo
																	 * the policyId in that will be that of Class Code 2. */
		/* SK : Changes 
		 * These columns will hold value when entry from Premise table is being made i.e. when PL is the basic section
		gaccBldQuo.setGbdFlatNo( null ); // same as that of mississipi value
		gaccBldQuo.setGbdBusinessCode( null ); // same as that of mississipi value
		*/
		gaccBldQuo.setGbdPremiseType( true ); //In Mississipi set as 0 so its set to false  in PAS
		/*
		gaccBldQuo.setGbdCbCode( null ); // same as that of mississipi value
		gaccBldQuo.setGbdRetroactiveDate( null ); // same as that of mississipi value
		*/
		/** Overriding risk code as it should be money basic risk code */
		gaccBldQuo.setGbdRskCode( Long.parseLong( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_BASIC_RISK_CODE ) ) );		
		gaccBldQuo.setGbdRiRskCode( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_BASIC_RI_RSK_CODE ) ) );
		/* Setting section level sum insured */
		/*
		 * FIX: Backend fix: this field should be blank in case of sbs policy - 22-Jul_2012
		 */
		//gaccBldQuo.setGbdSumInsured( BigDecimal.valueOf( getSectionLevelSumInsured(moneyVO) ) );		
		return gaccBldQuo;
	}

	@Override
	public BaseVO saveMoneySection( BaseVO baseVO ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getSectionId(){
		return MONEY_SECTION_ID;
	}

	@Override
	protected int getClassCode(){
		return MONEY_CLASS_CODE.intValue();
	}
	
	
	//Renewal Multiple Id's handling changes :Changed method signature to pass policy Id
	private void handleSafeDetails( TTrnGaccCashQuo cashQuo, MoneyVO moneyVO, PolicyVO policyVO, Long policyId ){
		
		/* If this content is "Cash in Safe", then we have to process even the Safe Details. */
		if( Utils.getSingleValueAppConfig( "MONEY_RISK_TYPES_5" ).equalsIgnoreCase( cashQuo.getGchRtCode() + "-" + cashQuo.getGchRcCode() + "-" + cashQuo.getGchRscCode() ) ){
			/* Now, if the user has provided safe details, process them one-by-one. */
			if( !Utils.isEmpty( moneyVO.getSafeDetails() ) ){
				for( SafeVO safeDetails : moneyVO.getSafeDetails() ){
					//Renewal Multiple Id's handling changes, added policy in the query parameter
					handleTableRecord( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS, policyVO, new POJO[]{ cashQuo }, new BaseVO[]{ safeDetails }, false, policyId,safeDetails.getId() );
				}
			}
		}
		
	}

	/**
	 * Creates a new ContentVO instance with the cover value and risk codes for Total Cash in Residence. 
	 * @param totalResSI
	 * @return
	 */
	private Contents getContentVOForTotalCashInResidence( Double totalResSI ){
		Contents content = new Contents();
		content.setCover( BigDecimal.valueOf( totalResSI ) );

		String[] riskTypeCodes = Utils.getMultiValueAppConfig( com.Constant.CONST_MONEY_RISK_TYPES_7, "-" );
		if( Utils.isEmpty( riskTypeCodes ) || riskTypeCodes.length < 3 ){
			throw new BusinessException( CommonErrorKeys.INVALID_CONFIGURATION, null, "Code configuration for 'Total Cash in Residence' not found" );
		}

		content.setRiskType( Integer.valueOf( riskTypeCodes[ 0 ] ) );
		content.setRiskCat( Integer.valueOf( riskTypeCodes[ 1 ] ) );
		content.setRiskSubCat( Integer.valueOf( riskTypeCodes[ 2 ] ) );

		return content;
	}

	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;

		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );

		if( SvcConstants.TABLE_ID_T_TRN_GACC_BUILDING.equals( tableId ) ){
			TTrnGaccBuildingQuo gaccBuildingQuo = null;
			if( !Utils.isEmpty( deps[ 0 ] ) )
				gaccBuildingQuo = getgaccBuildingQuoPojo( moneySection, (TTrnBuildingQuo) deps[ 0 ] , moneyVO);
			else if( !Utils.isEmpty( deps[ 1 ] ) ) gaccBuildingQuo = getgaccBuildingQuoPojo( moneySection, (TTrnWctplPremiseQuo) deps[ 1 ] , moneyVO);

			mappedPOJO = gaccBuildingQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_GACC_CASH_CODES.equals( tableId ) ){
			TTrnGaccBuildingQuo gbdQuo = (TTrnGaccBuildingQuo) deps[ 0 ];
			Contents content = (Contents) depsVO[ 0 ];

			mappedPOJO = getcashQuoPojo( gbdQuo.getId().getGbdId(), content, policyVO, moneySection.getPolicyId() );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS.equals( tableId ) ){
			TTrnGaccCashQuo cashQuo = (TTrnGaccCashQuo) deps[ 0 ];
			BaseVO b = depsVO[ 0 ];

			if( b instanceof CashResidenceVO ){
				mappedPOJO = getcashDetailsPojo( cashQuo.getId(), moneySection.getPolicyId(), (CashResidenceVO) b, policyVO );
			}
			else if( b instanceof SafeVO ){
				mappedPOJO = getcashDetailsPojo( cashQuo.getId(), moneySection.getPolicyId(), (SafeVO) b, policyVO );
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnGaccCashQuo cashQuo = (TTrnGaccCashQuo) deps[ 0 ];
			mappedPOJO = getPremiumPojo( (Contents) depsVO[ 0 ], policyVO, cashQuo, moneyVO, ( (DataHolderVO<Double>) depsVO[ 1 ] ).getData() );
		}

		return mappedPOJO;
	}

	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );

		boolean isToBeCreated = false;

		if( SvcConstants.TABLE_ID_T_TRN_GACC_BUILDING.equals( tableId ) ){
			/* Since GACC building record is a derived record and not a user input data, we will not have the Id coming in from the UI. Hence,
			 * the only way to know if this is a create case is to check directly in the table. */
			/*List gaccBlds = getHibernateTemplate().find( "from TTrnGaccBuildingQuo t where t.Id.gbdId=? and t.gbdPolicyId=?", Long.valueOf( locationDetails.getRiskGroupId( )),
					moneySection.getPolicyId() );*/
			if( Utils.isEmpty( moneyVO.getBasicRiskId() ) ){
				isToBeCreated = true;
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_GACC_CASH_CODES.equals( tableId ) ){
			/* In the case of GACC cash records, there is one entry (Total Cash in Residence) that is a derived record and not user-entered.
			 * Hence, we will have to check in the database for the existence of the record to decide if this is a create-case.
			 * 
			 * TODO In future, we must introduce a flag in Contents to indicate this scenario, so that we need to fire the database query
			 * only in this case. */
			Contents content = (Contents) depsVO[ 0 ];
			/*List gaccCashQuos = getHibernateTemplate().find(
					"from TTrnGaccCashQuo t where t.gchBasicRiskId=? and t.gchPolicyId=? and t.gchRskCode=25 and t.gchRtCode=? and t.gchRcCode=? and t.gchRscCode=?",
					moneyVO.getBasicRiskId().longValue(), moneySection.getPolicyId(), content.getRiskType().longValue(), content.getRiskCat().longValue(),
					content.getRiskSubCat().longValue() );*/
			
			/* Make isToBeCreated true if values for content is provided and sum insred for the content is greater than ZERO. */
			if( Utils.isEmpty( content.getRiskId() ) || ( !Utils.isEmpty( content.getRiskId() ) && content.getRiskId().equals( CommonConstants.DEFAULT_LOW_LONG ) ) && !isSumInsuredZero(content) ){
				isToBeCreated = true;
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, true );
			}
			else{
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, false );
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS.equals( tableId ) ){
				if( depsVO[ 0 ] instanceof SafeVO ){
					SafeVO saveDetails = (SafeVO) depsVO[ 0 ];
					if(Utils.isEmpty( saveDetails.getId() ) || 
							(!Utils.isEmpty( saveDetails.getId()) &&  saveDetails.getId().equals( CommonConstants.DEFAULT_LOW_LONG ) && !Utils.isEmpty( saveDetails.getMake()))){
						isToBeCreated = true;
					}
				}else if( depsVO[ 0 ] instanceof CashResidenceVO ){
					CashResidenceVO cashResidenceDetails = (CashResidenceVO) depsVO[ 0 ];
					if(Utils.isEmpty( cashResidenceDetails.getId() ) || 
							(!Utils.isEmpty( cashResidenceDetails.getId()) &&  cashResidenceDetails.getId().equals( CommonConstants.DEFAULT_LOW_LONG ))){
						isToBeCreated = true;
					}
				}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			isToBeCreated = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_CREATED );
			ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, null );
		}

		return isToBeCreated;
	}

	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		boolean isToBeDeleted = false;

		if( SvcConstants.TABLE_ID_T_TRN_GACC_BUILDING.equals( tableId ) ){
			/* We will always need the GACC building record. The only case where this may have to be deleted is when all the Money-related
			 * risks are deleted and that happens only in the case of "Delete Location" which is handled through a stored procedure. */
			isToBeDeleted = false;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_GACC_CASH_CODES.equals( tableId ) ){
			Contents content = (Contents) depsVO[ 0 ];
			/*List gaccCashQuos = getHibernateTemplate().find(
					"from TTrnGaccCashQuo t where t.gchBasicRiskId=? and t.gchPolicyId=? and t.gchRskCode=25 and t.gchRtCode=? and t.gchRcCode=? and t.gchRscCode=?",
					moneyVO.getBasicRiskId().longValue(), moneySection.getPolicyId(), content.getRiskType().longValue(), content.getRiskCat().longValue(),
					content.getRiskSubCat().longValue() );*/

			if( !Utils.isEmpty( content.getRiskId() )
					&& ( Utils.isEmpty( content.getCover() ) || ( !Utils.isEmpty( content.getCover() ) && content.getCover().setScale( 0, BigDecimal.ROUND_DOWN )
							.compareTo( BigDecimal.valueOf( 0.0 ) ) == 0 ) ) ){
				isToBeDeleted = true;
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, true );
			}
			else{
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, false );
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS.equals( tableId ) ){
				if( depsVO[ 0 ] instanceof SafeVO ){
					SafeVO saveDetails = (SafeVO) depsVO[ 0 ];
					if(!Utils.isEmpty( saveDetails.isToBeDeleted()) && saveDetails.isToBeDeleted()){
						isToBeDeleted = true;
						saveDetails.setToBeDeleted( true );
					}
				}
				else if( depsVO[ 0 ] instanceof CashResidenceVO ){
					CashResidenceVO cashResidenceDetails = (CashResidenceVO) depsVO[ 0 ];
						if( !Utils.isEmpty( cashResidenceDetails.isToBeDeleted() ) && cashResidenceDetails.isToBeDeleted() ){
							isToBeDeleted = true;
							/*cashResidenceDetails.setToBeDeleted( true );*/
					}
					
				}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			isToBeDeleted = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_DELETED );
			ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, null );
		}

		return isToBeDeleted;
	}

	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase){

		/* No action required for T_TRN_GACC_BUILDING because there is no VO for it. */
		if( SvcConstants.TABLE_ID_T_TRN_GACC_BUILDING.equals( tableId ) ){
			MoneyVO moneyDetails = (MoneyVO) depsVO[ 0 ];
			moneyDetails.setBasicRiskId( ( (TTrnGaccBuildingQuo) mappedRecord ).getId().getGbdId() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject( ( (TTrnGaccBuildingQuo) mappedRecord ).getId().getGbdId() ) );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_GACC_CASH_CODES.equals( tableId ) ){
			Contents content = (Contents) depsVO[ 0 ];
			
			if( saveCase == SaveCase.DELETE_PENDING_REC || saveCase == SaveCase.DELETE ){
			/* If content is deleted then risk id needs to be reset to bull */
				content.setRiskId( null );
			}else if( saveCase == SaveCase.CHANGE_WITH_NEW_REC && isSumInsuredZero( content )){
			/* If content is deleted then risk id needs to be reset to bull */
				content.setRiskId( null );
			}else{
				content.setRiskId( ( (TTrnGaccCashQuoId) mappedRecord.getPOJOId() ).getGchId() );
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS.equals( tableId ) ){
			if( depsVO[ 0 ] instanceof IVOId ){
				( (IVOId) depsVO[ 0 ] ).setId( ( (TTrnGaccCashDetailsQuoId) mappedRecord.getPOJOId() ).getGcdId() );
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			LOGGER.debug( "In case of Premium " );
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

		if( SvcConstants.TABLE_ID_T_TRN_GACC_BUILDING.equals( tableId ) ){
			TTrnGaccBuildingQuoId tid = new TTrnGaccBuildingQuoId();
			tid.setGbdId( NextSequenceValue.getNextSequence( "SEQ_GACC_BUILDING_ID",null,null, getHibernateTemplate() ) );
			tid.setGbdValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_GACC_CASH_CODES.equals( tableId ) ){
			TTrnGaccCashQuoId tid = new TTrnGaccCashQuoId();
			tid.setGchId( NextSequenceValue.getNextSequence( CASH_ID,null,null, getHibernateTemplate() ) );
			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS.equals( tableId ) ){
			TTrnGaccCashDetailsQuoId tid = new TTrnGaccCashDetailsQuoId();
			tid.setGcdId( NextSequenceValue.getNextSequence( CASH_DETAILS_ID, null,null,getHibernateTemplate() ) );
			tid.setGcdValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = tid;
		}
		/*
		 * The id of premium is derived, hence the id is mapped in the mapVOToPojo method it self.
		 */
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			id = mappedRecord.getPOJOId();
		}

		return id;
	}

	@Override
	protected <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){
		POJOId id = null;

		if( SvcConstants.TABLE_ID_T_TRN_GACC_BUILDING.equals( tableId ) ){
			TTrnGaccBuildingQuoId tid = (TTrnGaccBuildingQuoId) CopyUtils.copySerializableObject( existingId );
			tid.setGbdValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH.equals( tableId ) || SvcConstants.TABLE_ID_T_TRN_GACC_CASH_CODES.equals( tableId ) ){
			TTrnGaccCashQuoId tid = (TTrnGaccCashQuoId) CopyUtils.copySerializableObject( existingId );
			tid.setGchValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_DETAILS.equals( tableId ) ){
			TTrnGaccCashDetailsQuoId tid = (TTrnGaccCashDetailsQuoId) CopyUtils.copySerializableObject( existingId );
			tid.setGcdValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = tid;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuoId tid = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			tid.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = tid;
		}

		return id;
	}

	@Override
	public void sectionPreProcessing( PolicyVO policyVO ){
		
		super.sectionPreProcessing( policyVO );
		
		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );
		
		/*
		 * This code is temporary  
		 */
		if( !Utils.isEmpty( policyVO.getIsPrepackaged() ) && !policyVO.getIsPrepackaged() && !Utils.isEmpty( moneyVO.getContentsList() ) ){
			List<Contents> contents = moneyVO.getContentsList();
			for( int i = 1; i <= contents.size(); i++ ){
				String riskType = "MONEY_RISK_TYPES_" + i;

				String[] riskCodes = Utils.getMultiValueAppConfig( riskType, "-" );
				if( Utils.isEmpty( riskCodes ) || riskCodes.length < 3 ){
					throw new BusinessException( CommonErrorKeys.INVALID_CONFIGURATION, null, "Code configuration for cash content not found" );
				}

				Contents content = contents.get( i - 1 );

				content.setRiskType( Integer.valueOf( riskCodes[ 0 ] ) );
				content.setRiskCat( Integer.valueOf( riskCodes[ 1 ] ) );
				content.setRiskSubCat( Integer.valueOf( riskCodes[ 2 ] ) );
			}
		}
		
		
		/*
		 * Handle cash in cash in residence 
		 * Create a additional content in the content list, so that cash table is handled normally, without special handling for cash in residence
		 * To achieve this we query the cash table with the basic risk id and the risk code along with VED.
		 * If data is found then make an add constructed content to the content list.
		 * If there is no data present in cash table and the user selection is no - DO NOTHING.
		 * If data is fetched from table but user selection is no - Delete case update entry in cash and all the residence.
		 * If no data is fetched from cash table and user selection is yes - its a case of create in cash and all the residence.
		 */
		
		Contents finderContent = getContentVOForTotalCashInResidence( 0.0  );
		List<TTrnGaccCashQuo> gaccCashQuos = null;
		
		Long endtId = null;
		if(!Utils.isEmpty( policyVO.getNewEndtId() )){
			endtId = policyVO.getNewEndtId();
		}else{
			endtId = policyVO.getEndtId();
		}
		if(!Utils.isEmpty( moneyVO.getBasicRiskId() ))	{
			//Renewal Multiple Id's handling changes, added policy in the query parameter
			gaccCashQuos   = (List<TTrnGaccCashQuo>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_GACC_CASH_CODES, policyVO.getAppFlow(), getHibernateTemplate(), false, endtId,moneySection.getPolicyId()
					, moneyVO.getBasicRiskId().longValue(), finderContent.getRiskType().longValue(), finderContent.getRiskCat().longValue(), finderContent.getRiskSubCat().longValue() );

		}

		if(Utils.isEmpty( gaccCashQuos ) && Utils.isEmpty( moneyVO.getCashInResidence() ) ){
			//If there is no data present in cash table and the user selection is no - DO NOTHING.
			LOGGER.debug( "If there is no data present in cash table and the user selection is no - DO NOTHING" );
		}
		else{ 
			if(Utils.isEmpty( gaccCashQuos ) && !moneyVO.getCashInResidence() ){
				//If there is no data present in cash table and the user selection is no - DO NOTHING.
				LOGGER.debug( "If there is no data present in cash table and the user selection is no - DO NOTHING" );
			}
			else{ 
					//If data is fetched from table but user selection is no - Delete case update entry in cash and all the residence.
					if( !Utils.isEmpty( gaccCashQuos ) && !moneyVO.getCashInResidence() ){
						finderContent.setRiskId( gaccCashQuos.get( 0 ).getId().getGchId() );
						finderContent.setPremium( moneyVO.getCashResidencePremium() );
						finderContent.setCover( BigDecimal.valueOf( 0.0 ) );
						/*for(CashResidenceVO cashResidence : moneyVO.getCashResDetails()){
							cashResidence.setToBeDeleted( true );
						}*/
						
					}
					//If no data is fetched from cash table and user selection is yes - its a case of create in cash and all the residence.
					else if(Utils.isEmpty( gaccCashQuos ) && moneyVO.getCashInResidence()){
						Double totalResSI  = getCIRSumInsured(moneyVO);
						finderContent.setPremium( moneyVO.getCashResidencePremium() );
						finderContent.setCover( BigDecimal.valueOf( totalResSI ) );
						
						/*for(CashResidenceVO cashResidence : moneyVO.getCashResDetails()){
							cashResidence.setId( null );
						}*/
					}else if(!Utils.isEmpty( gaccCashQuos ) && moneyVO.getCashInResidence()){
						Double totalResSI  = getCIRSumInsured(moneyVO);
						finderContent.setRiskId( gaccCashQuos.get( 0 ).getId().getGchId() );
						finderContent.setPremium( moneyVO.getCashResidencePremium() );
						finderContent.setCover( BigDecimal.valueOf( totalResSI ) );
					}
					
					moneyVO.getContentsList().add( finderContent );
			}
		
		}
		
	}
	
	

	private Double getCIRSumInsured( MoneyVO moneyVO ){
		Double totalResSI  =  0.0;
		for( CashResidenceVO cashResidence : moneyVO.getCashResDetails() ){
			if( !Utils.isEmpty( cashResidence ) && !Utils.isEmpty( cashResidence.getSumInsuredDets() ) && !Utils.isEmpty( cashResidence.getSumInsuredDets().getSumInsured() ) && !cashResidence.isToBeDeleted() ){
				totalResSI += cashResidence.getSumInsuredDets().getSumInsured();
			}
		}
		return totalResSI;
	}


	/**
	 * Sets LocationVO.toSave to false so that this location doesn't get picked up in the next SAVE call.
	 */
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		
		if( policyVO.getAppFlow() == Flow.AMEND_POL ) updateEndtText( policyVO );
		
		
		removeDeletedSafeDetails(policyVO);
		
		removeDeletedCIRDetails(policyVO);
		
		removeCIRContent(policyVO);
		
		updateSectionLevelSIAndPremium( policyVO );
		
		super.sectionPostProcessing( policyVO );
	}

	
	private void removeDeletedCIRDetails( PolicyVO policyVO ){
		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );
		
		List<CashResidenceVO> cirDetails = moneyVO.getCashResDetails();
		List<CashResidenceVO> finalCirDetails = new ArrayList<CashResidenceVO>();
		if( !Utils.isEmpty( cirDetails ) ){
			for( CashResidenceVO cirDetail : cirDetails ){
				if( !cirDetail.isToBeDeleted() ){
					finalCirDetails.add( cirDetail );
				}
			}
			moneyVO.setCashResDetails(finalCirDetails);
		}
	}

	private void removeDeletedSafeDetails( PolicyVO policyVO ){
		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );

		List<SafeVO> safeDetails = moneyVO.getSafeDetails();
		List<SafeVO> finalSafeDetails = new ArrayList<SafeVO>();
		if( !Utils.isEmpty( safeDetails ) ){
			for( SafeVO safeDetail : safeDetails ){
				if( !safeDetail.isToBeDeleted() ){
					finalSafeDetails.add( safeDetail );
				}
			}
			moneyVO.setSafeDetails(finalSafeDetails);
		}
	}

	private void removeCIRContent( PolicyVO policyVO ){
		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );
		
		/*
		 * The last content is the cash in residence details for cash. 
		 * This is to be removed from the content list.
		 */
		List<Contents> contents = moneyVO.getContentsList();
		int indexOfCIRContent = 0;
		boolean flag = false;
		for(Contents content: contents ){
			indexOfCIRContent++;
			if(Utils.concat( content.getRiskType().toString(),"-", content.getRiskCat().toString(),
					"-",content.getRiskSubCat().toString()).equalsIgnoreCase( Utils.getSingleValueAppConfig( com.Constant.CONST_MONEY_RISK_TYPES_7 ) )){
				moneyVO.setPremium( content.getPremium() );
				flag = true;
				break;
			}
		}
		if(flag){
			--indexOfCIRContent;
			contents.set( indexOfCIRContent, null );
			contents.removeAll(Collections.singleton(null));  
		}
	}

	private void updateEndtText( PolicyVO policyVO ){
		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );
		
		LOGGER.debug( "Executing proc to delete previous endt text " );
		DAOUtils.deletePrevEndtText( moneySection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ),MONEY_SECTION_ID, moneyVO.getBasicRiskId() );
		LOGGER.debug( "Previous endt text deleted " );

		LOGGER.debug( "Generate endt text for including new location - pro_endt_text_gacc_bld_add" );
		DAOUtils.genEndtTextAddMoneyLocation( moneySection.getPolicyId(), policyVO,  moneyVO.getBasicRiskId() );			
		
		LOGGER.debug( "SATRT : Executing proc to add endt text for adding cash content and adding cash details  - pro_endt_text_gacc_cash_add" );
		DAOUtils.addEndtTextCshAndCshDetails( moneySection.getPolicyId(), policyVO, moneyVO.getBasicRiskId() );
		LOGGER.debug( "END : Executing proc to add endt text for adding cash content and adding cash details  - pro_endt_text_gacc_cash_add" );
	
		LOGGER.debug( "updated endt text for deleting cash content and cash details" );
		DAOUtils.deleteEndtTextCshAndCshDetails( moneySection.getPolicyId(), policyVO,  moneyVO.getBasicRiskId() );
		
		LOGGER.debug( "Executing proc to update endt text for cash  and cash details" );
		for( Contents content : moneyVO.getContentsList() ){
			if( !Utils.isEmpty( content ) && !Utils.isEmpty( content.getRiskId() ) ){
				LOGGER.debug( "updating  endt text for gcd id: " + content.getRiskId() );
				DAOUtils.updateEndtTextCshAndCshDetails( moneySection.getPolicyId(), policyVO, content.getRiskId(), moneyVO.getBasicRiskId() );
				LOGGER.debug( "updated  endt text for gcd id: " + content.getRiskId() );
			}

		}

		LOGGER.debug( "call deductible change endo SP" );
		DAOUtils.updateDeductibleforendorsementFlow( moneySection.getPolicyId(), policyVO,moneySection.getSectionId(), moneyVO.getBasicRiskId(), Long.valueOf(locationDetails.getRiskGroupId()) );
			
		DAOUtils.updateTotalSITextforendorsementFlow( moneySection.getPolicyId(), policyVO,moneySection.getSectionId(),  moneyVO.getBasicRiskId(), Long.valueOf(locationDetails.getRiskGroupId())  );

		// Calls procedure for generating endt Text for New Risk added.
			
		//LOGGER.debug( "call Risk Add changes change endo SP" );
		//DAOUtils.updateEndTextForRiskAdd( moneySection.getPolicyId(), policyVO,moneySection.getSectionId());
		
	}
	/**
	 *Updates the section level premium to money v0
	 * @param policyVO
	 */
	private void updateSectionLevelSIAndPremium( PolicyVO policyVO ){
		SectionVO moneySection = PolicyUtils.getSectionVO( policyVO, MONEY_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( moneySection );
		MoneyVO moneyVO = (MoneyVO) PolicyUtils.getRiskGroupDetails( locationDetails, moneySection );
		moneyVO.setSumInsured( getSectionLevelSumInsured( moneyVO ) );
		if(Utils.isEmpty( moneyVO.getPremium() )){
			moneyVO.setPremium( new PremiumVO() );
		}
		
		/*
		 * In pre - pack, the premium is set at section level, hence no need to consolidate cover level premium
		 */
		if( !Utils.isEmpty( policyVO.getIsPrepackaged() ) && !policyVO.getIsPrepackaged()){
			if( Utils.isEmpty( moneyVO.getPremium() ) ){
				moneyVO.setPremium( new PremiumVO() );
			}
			moneyVO.getPremium().setPremiumAmt(getSectionLevelPremium(moneyVO));
		}

	}

	private double getSectionLevelPremium( MoneyVO moneyVO ){
		return PremiumHelper.getSectionLevelPremium( moneyVO );
	}

	private double getSectionLevelSumInsured( MoneyVO moneyVO ){
		
		return PremiumHelper.getSectionLevelSumInsured( moneyVO );
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#handleAdditionalCovers(com.rsaame.pas.vo.bus.SectionVO, com.rsaame.pas.vo.bus.PolicyVO)
	 * Currently there are no additional covers in money, if covers is added in future, then the overriding 
	 * method is to be removed as the BaseSectionSaviDAO will take care of handling additional covers
	 * we the vo's are populated correctly
	 */
	@Override
	protected void handleAdditionalCovers( SectionVO sectionVO, PolicyVO policyVO ){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

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
	
	
	/**
	 * Checks if suminsured value is null or greater than 0
	 * @param content
	 * @return
	 */
	private boolean isSumInsuredZero(Contents content){
		if( Utils.isEmpty( content.getCover() ) || content.getCover().compareTo( BigDecimal.valueOf( 0.0 ) ) ==  0){
			return true;
		}
		return false;
	}
}
