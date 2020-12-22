/**
 * 
 */
package com.rsaame.pas.bi.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

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
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnColWorkSheetQuo;
import com.rsaame.pas.dao.model.TTrnColWorkSheetQuoId;
import com.rsaame.pas.dao.model.TTrnConsequentialLossQuo;
import com.rsaame.pas.dao.model.TTrnConsequentialLossQuoId;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuoId;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.BIUWDetailsVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author Anveshan
 *
 */
public class BISaveDAO extends BaseSectionSaveDAO implements IBISectionDAO{

	private final static Logger LOGGER = Logger.getLogger( BISaveDAO.class );
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer BI_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_SECTION" ) );
	private static final short BI_CLASS_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CLASS" ) );
	
	//cover code values
	private static final short BI_PRM_COVER_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_COVER_CODE" ) );
	
	// Cover type code
	private static final short BI_COVER_TYPE_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_COVER_TYPE_CODE" ) );
	private static final short BI_ICW_COVER_TYPE_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_ICW_COVER_TYPE_CODE" ) );
	private static final short BI_RR_COVER_TYPE_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_RR_COVER_TYPE_CODE" ) );
	
	// Cover sub type code
	private static final short BI_PRM_CST_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_CST_CODE" ) );
	
	// Risk code
	private static final Integer BI_PRM_RSK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_RSK_CODE" ) );
	
	private static final short BI_COL_RISK_CLASS = Short.valueOf( Utils.getSingleValueAppConfig( "BI_COL_RISK_CLASS" ) );
	private static final int BI_BASIC_RSK_CODE = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_BASIC_RSK_CODE" ) );
	//private static final short BI_RISK_CATEGORY = Short.valueOf( Utils.getSingleValueAppConfig( "BI_RISK_CATEGORY" ) );
	private static final short BI_CWS_ITEM_CODE_WORK_LIMIT = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_WORK_LIMIT" ) );
	private static final short BI_CWS_ITEM_CODE_RENT_RECIEVABLE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_RENT_RECIEVABLE" ) );
	//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
	//private static final short BI_CWS_ITEM_CODE_ANNUAL_RENT = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_ANNUAL_RENT" ) );
	private static final short BI_CWS_ITEM_CODE_GROSS_INCOME = Short.valueOf( Utils.getSingleValueAppConfig( "BI_CWS_ITEM_CODE_GROSS_INCOME" ) );

	private final static Integer BI_ENDT_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "BI_ENDT_ID" ) );
	private static final short BI_ICW_PRM_RC_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_ICW_PRM_RC_CODE" ) );
	private static final short BI_RR_PRM_RC_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_RR_PRM_RC_CODE" ) );
	private static final short BI_PRM_RC_CODE = Short.valueOf( Utils.getSingleValueAppConfig( "BI_PRM_RC_CODE" ) );
	private long endt ;
	
	public BISaveDAO(){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

		
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.bi.dao.IBISectionDAO#loadBISection(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO loadBISection( BaseVO baseVO ){
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.bi.dao.IBISectionDAO#saveBISection(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO saveBISection( BaseVO baseVO ){
		return null;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getSectionId()
	 */
	@Override
	protected int getSectionId(){
		return BI_SECTION_ID;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getClassCode()
	 */
	@Override
	protected int getClassCode(){
		return BI_CLASS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#saveSection(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	protected BaseVO saveSection( BaseVO input ){
		PolicyVO policyVO = (PolicyVO) input;

		/* Let us get the system date right now and use from here on for this transaction. */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( section );
		BIVO biVO = (BIVO) PolicyUtils.getRiskGroupDetails( locationDetails, section );
		TTrnBuildingQuo trnBuildingQuo = null;

		
		/* Handle the Consequential data. Premium update for the building will be handled inside
		 * this method. */

		trnBuildingQuo = handleConsequentialLoss( policyVO, section, locationDetails, biVO );

		/* Handle the Col Work Sheet Data. */
		handleColWorkSheet( policyVO, section, locationDetails, biVO, trnBuildingQuo );
		
		if(policyVO.getIsQuote() &&   !Utils.isEmpty( biVO.getPolicyId() )  )
		{
			endt = (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID );
			//removeDeletedRecords(endt,policyVO,biVO);
		}
		
		/* Handle the UW questions. */
		handleUWQ( policyVO, section, locationDetails, biVO, trnBuildingQuo );
		
		return policyVO;
	}

	private void removeDeletedRecords(long endt,PolicyVO policyVO,BIVO biVO)
	{
		List<TTrnConsequentialLossQuo> conseqLossQuote = null;
		List<TTrnColWorkSheetQuo> colWorkSheetQuote = null;
		
		/*conseqLossQuote = getHibernateTemplate().find( "from TTrnConsequentialLossQuo tcolLoss where tcolLoss.id.colPolicyId = ? and "
				+ " tcolLoss.id.colEndtId = ? and tcolLoss.colStatus = 4", Long.valueOf( biVO.getPolicyId() ),endt );
		
		if( !Utils.isEmpty( conseqLossQuote ) )
		{
			for( TTrnConsequentialLossQuo colLossItem : conseqLossQuote )
			{
				delete( colLossItem );
			}
		}
		
		colWorkSheetQuote = getHibernateTemplate().find( "from TTrnColWorkSheetQuo tcws where tcws.id.cwsPolicyId = ? and "
				+ " tcws.cwsEndtId = ? and tcws.cwsStatus = 4", Long.valueOf( biVO.getPolicyId() ),endt );
		
		if( !Utils.isEmpty( colWorkSheetQuote ) )
		{
			for( TTrnColWorkSheetQuo colLossItem : colWorkSheetQuote )
			{
				delete( colLossItem );
			}
		}*/
		
		String sqlquery = null;
			sqlquery = "delete from t_trn_consequential_loss_quo where col_policy_id = " + Long.valueOf( biVO.getPolicyId() ) +
					"and col_status = 4 and col_endt_id = " + endt;

		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery( sqlquery );
		query.executeUpdate();
		
		sqlquery = "delete from t_trn_col_work_sheet_quo where cws_policy_id = " + Long.valueOf( biVO.getPolicyId() ) +
				"and cws_status = 4 and cws_endt_id = " + endt;

		query = session.createSQLQuery( sqlquery );
		query.executeUpdate();
		
	}
	
	private void handleColWorkSheet( PolicyVO policyVO, SectionVO biSection, LocationVO locationVO, BIVO biVO, TTrnBuildingQuo trnBuildingQuo ){

		/* Process the T_TRN_CONSEQUENTIAL_LOSS(_QUO) record. */

		SumInsuredVO sumVO = new SumInsuredVO();
		if( isWorkingLimitTobeUpdated( biVO ) ){
			sumVO.setSumInsured( biVO.getWorkingLimit() );
			sumVO.setaDesc( SvcConstants.BI_INCR_COST_WORKING_LIMIT );
			sumVO.setCash_Id( (long) BI_CWS_ITEM_CODE_WORK_LIMIT );
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET, policyVO, new POJO[]{ trnBuildingQuo }, new BaseVO[]{ biVO, sumVO }, false,
					(short) BI_CWS_ITEM_CODE_WORK_LIMIT, biVO.getPolicyId() );
			biVO.setBasicRiskId( trnBuildingQuo.getId().getBldId() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, trnBuildingQuo.getId().getBldId() );
		}
		if( isRentReceivableTobeUpdated( biVO ) ){
			sumVO.setSumInsured( biVO.getRentRecievable() );
			sumVO.setaDesc( SvcConstants.BI_RENT_RECEIVABLE );
			sumVO.setCash_Id( (long) BI_CWS_ITEM_CODE_RENT_RECIEVABLE );
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET, policyVO, new POJO[]{ trnBuildingQuo }, new BaseVO[]{ biVO, sumVO }, false,
					(short) BI_CWS_ITEM_CODE_RENT_RECIEVABLE, biVO.getPolicyId() );
			biVO.setBasicRiskId( trnBuildingQuo.getId().getBldId() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, trnBuildingQuo.getId().getBldId() );
		}
		if( isEstimatedGrossIncomeTobeUpdated(( biVO ))) 
		{
			sumVO.setSumInsured( biVO.getEstimatedGrossIncome());
			sumVO.setaDesc( SvcConstants.BI_ESTIMATED_GROSS_INCOME );
			sumVO.setCash_Id( (long) BI_CWS_ITEM_CODE_GROSS_INCOME );
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET, policyVO, new POJO[]{ trnBuildingQuo }, new BaseVO[]{ biVO, sumVO }, false,
					(short) BI_CWS_ITEM_CODE_GROSS_INCOME, biVO.getPolicyId() );
			biVO.setBasicRiskId( trnBuildingQuo.getId().getBldId() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, trnBuildingQuo.getId().getBldId() );
		}
		// Added for Adventnet Id:103286;To Move BI Section from PAR to BI
		//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
		/*if( isAnnualRentTobeUpdated( biVO ) ){
			sumVO.setSumInsured( biVO.getAnnualRent() );
			sumVO.setaDesc( SvcConstants.BI_ANNUAL_RENT );
			sumVO.setCash_Id( (long) BI_CWS_ITEM_CODE_ANNUAL_RENT );
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET, policyVO, new POJO[]{ trnBuildingQuo }, new BaseVO[]{ biVO, sumVO }, false,
					(short) BI_CWS_ITEM_CODE_ANNUAL_RENT, biVO.getPolicyId() );
			biVO.setBasicRiskId( trnBuildingQuo.getId().getBldId() );
			ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, trnBuildingQuo.getId().getBldId() );
		}*/// Added END for Adventnet Id:103286

	}

	private void handleUWQ( PolicyVO policyVO, SectionVO bisection, LocationVO locationDetails, BIVO biVO, TTrnBuildingQuo trnBuildingQuo ){

		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );

		POJO[] uwqDeps = { trnBuildingQuo };
		UWQuestionsVO questionsVOs = biVO.getUwQuestions();
		List<UWQuestionVO> questions = questionsVOs.getQuestions();

		for( UWQuestionVO question : questions ){
			BaseVO[] uwqDepsVO = { question };
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE, policyVO, uwqDeps, uwqDepsVO, false, bisection.getPolicyId(), question.getQId(),
					( Long.valueOf( rg.getRiskGroupId() ) ), Utils.isEmpty( question.getResponse() ) ? null : question.getResponse() );
		}
	}

	private boolean isSectionPresent( int sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}

	/*
	 * This method is supposed to take care of insertion of data in to :T_TRN_CONSEQUENTIAL_LOSS_QUO
	 */
	private TTrnBuildingQuo handleConsequentialLoss( PolicyVO policyVO, SectionVO biSection, LocationVO locationVO, BIVO biVO ){
		/* Check if par is present, basicSectionID will contain the section id of either par  */
		Integer basicSectionID = null;
		TTrnConsequentialLossQuo conseqLossQuote = null;
		if( isSectionPresent( PAR_SECTION_ID, policyVO ) ){
			basicSectionID = PAR_SECTION_ID;
		}
		else{
			throw new BusinessException( "pas.basicSection.mandatory", null, "Either Par or Pl has to be selected to proceed further" );
		}

		SectionVO basicSection = PolicyUtils.getSectionVO( policyVO, basicSectionID );

		TTrnBuildingQuo buildingQuo = null;

		/* If PAR is the basic section, then we have to get the PAR building record and use it for the 
		 * construction of the T_TRN_CONSEQUENTIAL_LOSS_QUO record POJO.  */
		if( !Utils.isEmpty( basicSection ) )
		{
			if( !Utils.isEmpty( locationVO.getRiskGroupId() ) ){
				try{
					/*
					 * V2.1 - Using policy id in the query coz during renewal the original quote and the renewal quote will have the same building id
					 */
					buildingQuo = (TTrnBuildingQuo) getHibernateTemplate().find( "from TTrnBuildingQuo buldQ where buldQ.id.bldId=? and buldQ.bldPolicyId =? ", Long.valueOf( locationVO.getRiskGroupId() ),biSection.getPolicyId() )
							.get( 0 );
				}
				catch( Exception e ){
					throw new BusinessException( "pas.basicSection.IDMandatory", e, "ID from of the basic section is mandatory, no data in bld table" );
				}

			}
			if( Utils.isEmpty( buildingQuo ) ){
				throw new BusinessException( "pas.basicSection.IDMandatory", null, "ID from of the basic section is mandatory" );
			}
		}

		else{
			throw new BusinessException( "pas.basicSection.detailsMandatory", null, "Details of the basic section is mandatory" );
		}

		/* Process the T_TRN_CONSEQUENTIAL_LOSS(_QUO) record. */
		conseqLossQuote = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_CONSEQUENTIAL_LOSS, policyVO, new POJO[]{ buildingQuo }, new BaseVO[]{ biVO }, false,
				biVO.getBasicRiskId(), biSection.getPolicyId() );//#Renewal Multiple Id's handling changes, use biSection because biVO contains policyId null sometimes

		ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, buildingQuo.getId().getBldId() );
		// check for risk id

		handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, new POJO[]{ buildingQuo, conseqLossQuote }, SvcConstants.NO_DEP_VO, SvcConstants.IS_TABLE_QUERY_HBM,
				biVO.getPolicyId(), BigDecimal.valueOf( biVO.getBasicRiskId() ), BigDecimal.valueOf( buildingQuo.getId().getBldId() ), BI_PRM_COVER_CODE, BI_COVER_TYPE_CODE, BI_PRM_CST_CODE );

		return buildingQuo;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#mapVOToPOJO(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO[], com.mindtree.ruc.cmn.base.BaseVO[])
	 */
	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;
		SectionVO biSection = PolicyUtils.getSectionVO( policyVO, BI_SECTION_ID );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( biSection );
		BIVO biDetails = (BIVO) PolicyUtils.getRiskGroupDetails( locationVO, biSection );
		biDetails.setPolicyId( biSection.getPolicyId() );
		TTrnConsequentialLossQuo conseqLossQuote = null;
		TTrnColWorkSheetQuo colWorkSheetQuote = null;
		POJO pojo = null;
		BigDecimal sumInsured = null;

		if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			UWQuestionVO question = (UWQuestionVO) depsVO[ 0 ];
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[ 0 ];
			TTrnUwQuestionsQuo questionsQuo = getUWAPojo( question, policyVO, buildingQuo );
			mappedPOJO = questionsQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONSEQUENTIAL_LOSS.equals( tableId ) ){
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[ 0 ];
			conseqLossQuote = getConsequentialLossPojo( policyVO, buildingQuo, biDetails );
			mappedPOJO = conseqLossQuote;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET.equals( tableId ) ){
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[ 0 ];
			colWorkSheetQuote = getColWorkSheetPojo( policyVO, buildingQuo, biDetails, depsVO );
			mappedPOJO = colWorkSheetQuote;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[ 0 ];
			pojo = deps[ 1 ];
			mappedPOJO = getPremiumPojo( buildingQuo, biDetails, policyVO );
			if( pojo instanceof TTrnConsequentialLossQuo ){
				conseqLossQuote = (TTrnConsequentialLossQuo) pojo;
				sumInsured = new BigDecimal( conseqLossQuote.getColGpSumInsured() );
				mappedPOJO = fillColDetailsInPremium( sumInsured, (TTrnPremiumQuo) mappedPOJO );
			}
			else if( pojo instanceof TTrnColWorkSheetQuo ){
				colWorkSheetQuote = (TTrnColWorkSheetQuo) pojo;
				Long c = colWorkSheetQuote.getCwsItemAmount();
				if( !Utils.isEmpty( c ) ){
					sumInsured = new BigDecimal( c );
				}
				mappedPOJO = fillCwsDetailsInPremium( sumInsured, (TTrnPremiumQuo) mappedPOJO, biDetails );
			}

		}

		return mappedPOJO;
	}

	private TTrnPremiumQuo fillCwsDetailsInPremium( BigDecimal sumInsured, TTrnPremiumQuo premiumQuo, BIVO biDetails ){
		TTrnPremiumQuoId premiumQuoId = premiumQuo.getId();
		premiumQuo.setPrmSumInsured( sumInsured );

		if( !Utils.isEmpty( biDetails.getWorkingLimit() ) && !biDetails.isWorkingLimitCommited() ){
			 // 1 FOR CWL -- changing it to 8
			premiumQuoId.setPrmCtCode(BI_ICW_COVER_TYPE_CODE); // 0 FOR CWL
			premiumQuo.setPrmRcCode( BI_ICW_PRM_RC_CODE ); // check this

			biDetails.setWorkingLimitCommited( true );
		}
		else if( !Utils.isEmpty( biDetails.getRentRecievable() ) ){
			premiumQuoId.setPrmCtCode(BI_RR_COVER_TYPE_CODE); // 0 FOR RR
			premiumQuo.setPrmRcCode( BI_RR_PRM_RC_CODE ); // check this
		}
		return premiumQuo;
	}

	private TTrnPremiumQuo fillColDetailsInPremium( BigDecimal sumInsured, TTrnPremiumQuo premiumQuo ){
		TTrnPremiumQuoId premiumQuoId = premiumQuo.getId();
		//premiumQuo.setPrmSumInsured( sumInsured );

		premiumQuoId.setPrmCtCode(BI_COVER_TYPE_CODE); // 1 FOR BI
		premiumQuo.setPrmRcCode( BI_PRM_RC_CODE ); // check this
		return premiumQuo;
	}

	private TTrnPremiumQuo getPremiumPojo( TTrnBuildingQuo buildingQuo, BIVO biDetails, PolicyVO policyDetails ){

		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();
		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();

		// setting primary keys -- start
		premiumQuoId.setPrmPolicyId( biDetails.getPolicyId() );
		premiumQuoId.setPrmBasicRskCode( BI_BASIC_RSK_CODE );// Derived from T_TRN_BUILDING's BLD_BASIC_RSK_CODE 
		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );
		// premium risk id and basic risk id are same for BI
		if( !Utils.isEmpty( buildingQuo.getId().getBldId() ) ){
			premiumQuoId.setPrmRskId( BigDecimal.valueOf( buildingQuo.getId().getBldId() ) );
		}
		premiumQuoId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		premiumQuoId.setPrmRskCode(BI_PRM_RSK_CODE);
		premiumQuoId.setPrmCovCode(BI_PRM_COVER_CODE);
		premiumQuoId.setPrmCstCode(BI_PRM_CST_CODE);
		// setting primary keys -- end

		if( Utils.isEmpty( biDetails.getBasicRiskId() ) ){
			throw new BusinessException( "", null, "Basic Risk Id obtained from biDetails is null for [ " + biDetails.getPolicyId() + " ] policy id" );
		}
		premiumQuo.setId( premiumQuoId );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmClCode( BI_CLASS_CODE );
		premiumQuo.setPrmRiRskCode( buildingQuo.getBldRiRskCode() );
		premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( biDetails.getDeductible() ) );
		premiumQuo.setPrmRscCode( buildingQuo.getBldRskCode() );
		premiumQuo.setPrmRtCode(0);
		premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		premiumQuo.setPrmStatus( SvcConstants.POL_STATUS_PENDING.byteValue());
		premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER );
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		SvcUtils.setAuditDetailsforPrm( premiumQuo, policyDetails, (Date) ThreadLevelContext.get( "SYSDATE" ) );
		premiumQuo.setPrmEffectiveDate( policyDetails.getScheme().getEffDate());
		premiumQuo.setPrmExpiryDate( policyDetails.getEndDate() );
		premiumQuo.setPrmSumInsured(new BigDecimal( biDetails.getSumInsured() ) );
		premiumQuo.setPrmRiLocCode( Integer.parseInt( Utils.getSingleValueAppConfig( "BI_PRM_RI_LOC_CODE" ) ) );
		premiumQuo.setPrmSumInsuredCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		/*Added to enter premium value*/
		if( !Utils.isEmpty( biDetails.getPremium() )  && !biDetails.isPremiumCommited())
		{
			if( !Utils.isEmpty( biDetails.getPremium().getPremiumAmt() ) )
			{
				premiumQuo.setPrmPremium( new BigDecimal(String.valueOf(biDetails.getPremium().getPremiumAmt() ) ));
				premiumQuo.setPrmPremiumActual( new BigDecimal( String.valueOf(biDetails.getPremium().getPremiumAmt() ) ));
			}
			else
			{
				setZeroPrmValue( premiumQuo );
			}
			biDetails.setPremiumCommited( true );
		}
		else
		{
			setZeroPrmValue( premiumQuo );
		}
		setRateTypeToPremiumPOJO( policyDetails, premiumQuo );
		return premiumQuo;
	}

	private TTrnColWorkSheetQuo getColWorkSheetPojo( PolicyVO policyVO, TTrnBuildingQuo buildingQuo, BIVO biDetails, BaseVO[] depsVO ){

		TTrnColWorkSheetQuo colWorkSheetQuote = new TTrnColWorkSheetQuo();
		TTrnColWorkSheetQuoId colWorkSheetQuoteId = null;
		SumInsuredVO sumVO = (SumInsuredVO) depsVO[ 1 ];

		colWorkSheetQuote = BeanMapper.map( buildingQuo, colWorkSheetQuote );
		colWorkSheetQuoteId = colWorkSheetQuote.getId();
		// from UI
		if( !Utils.isEmpty( sumVO.getSumInsured() ) ){
			colWorkSheetQuote.setCwsItemAmount( sumVO.getSumInsured().longValue() );
		}
		colWorkSheetQuote.setCwsValidityExpiryDate( SvcConstants.EXP_DATE );
		colWorkSheetQuote.setCwsDescription( sumVO.getaDesc() );
		colWorkSheetQuoteId.setCwsItemCode( sumVO.getCash_Id().shortValue() );
		// Constants
		colWorkSheetQuote.setEndtId( BI_ENDT_ID.longValue() );
		colWorkSheetQuote.setCwsStatus(  SvcConstants.POL_STATUS_PENDING .byteValue() );

		colWorkSheetQuote.setId( colWorkSheetQuoteId );
		return colWorkSheetQuote;
	}

	private TTrnConsequentialLossQuo getConsequentialLossPojo( PolicyVO policyVO, TTrnBuildingQuo buildingQuo, BIVO biDetails ){

		TTrnConsequentialLossQuo conseqLossQuote = new TTrnConsequentialLossQuo();
		TTrnConsequentialLossQuoId conseqLossQuoteId = null;

		conseqLossQuote = BeanMapper.map( buildingQuo, conseqLossQuote );
		conseqLossQuote.setColPreparedDt(( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD )) );
		// Values from sum insured
		conseqLossQuote.setColGpSumInsured( (long) biDetails.getSumInsured());
		conseqLossQuote.setColDescription( biDetails.getIndemnityPeriod().toString() + " MONTHS" );
		conseqLossQuote.setColIndemnityPeriod( biDetails.getIndemnityPeriod().byteValue() ); // byte is not sufficient
		// Values from UW
		BIUWDetailsVO uwDetrails = (BIUWDetailsVO) biDetails.getUwDetails();
		if( !Utils.isEmpty( uwDetrails ) && !Utils.isEmpty( uwDetrails.getEmlPrc() ) ){
			conseqLossQuote.setColEmlPerc( BigDecimal.valueOf( uwDetrails.getEmlPrc() ) );
			conseqLossQuote.setColEmlSi( uwDetrails.getEmlSI().longValue() );
		}
		conseqLossQuoteId = conseqLossQuote.getId();
		// constant values
		conseqLossQuoteId.setColEndtId( BI_ENDT_ID.longValue() );
		conseqLossQuote.setColRiskClass(BI_COL_RISK_CLASS);
		conseqLossQuote.setColStatus(  SvcConstants.POL_STATUS_PENDING .byteValue() );
		conseqLossQuote.setColPremiseADesc( SvcConstants.BI_NULL_VALUE );
		conseqLossQuote.setColPremiseDesc( SvcConstants.BI_NULL_VALUE );
		conseqLossQuote.setId( conseqLossQuoteId );
		conseqLossQuote.setColValidityExpiryDate( SvcConstants.EXP_DATE );
		return conseqLossQuote;
	}

	private TTrnUwQuestionsQuo getUWAPojo( UWQuestionVO question, PolicyVO policyVO, TTrnBuildingQuo buildingQuo ){

		TTrnUwQuestionsQuo uwQuestionsQuo = new TTrnUwQuestionsQuo();
		TTrnUwQuestionsQuoId id = new TTrnUwQuestionsQuoId();
		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, BI_SECTION_ID );
		id.setUqtPolPolicyId( parSection.getPolicyId() );
		id.setUqtPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		id.setUqtUwqCode( question.getQId() );
		id.setUqtLocId( buildingQuo.getId().getBldId() );
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
		SectionVO biSection = PolicyUtils.getSectionVO( policyVO, BI_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( biSection );
		BIVO biVO = (BIVO) PolicyUtils.getRiskGroupDetails( locationDetails, biSection );
		boolean isToBeCreated = false;
		if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			return toCreateUWQuestionsRecord( policyVO, depsPOJO, depsVO );
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONSEQUENTIAL_LOSS.equals( tableId ) ){
			if( Utils.isEmpty( biVO.getBasicRiskId() ) ){
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, true );
				return true;
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET.equals( tableId ) ){
			SumInsuredVO sumVO = (SumInsuredVO) depsVO[ 1 ];
			if( SvcConstants.BI_INCR_COST_WORKING_LIMIT.equals( sumVO.getaDesc() ) && Utils.isEmpty( biVO.getBiCwsAcwlId()) )
			{
				return true;
			}
			else if( SvcConstants.BI_RENT_RECEIVABLE.equals( sumVO.getaDesc() ) && Utils.isEmpty( biVO.getBiCwsRentId() ) )
			{
				return true;
			}
			else if( SvcConstants.BI_ESTIMATED_GROSS_INCOME.equals( sumVO.getaDesc() ) && Utils.isEmpty( biVO.getBiCwsEGIncomeId()) )
			{
				return true;
			}//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
			//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
			/*else if( SvcConstants.BI_ANNUAL_RENT.equals( sumVO.getaDesc() ) && Utils.isEmpty( biVO.getBiCwsAnnualRentId() ) )
			{
				return true;
			}*/
		}
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			Boolean isCreated = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_CREATED );
			ThreadLevelContext.clear( SvcConstants.PRM_TO_BE_CREATED );

			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;
		}
		ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, false );
		return isToBeCreated;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#isToBeDeleted(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO[], com.mindtree.ruc.cmn.base.BaseVO[])
	 */
	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		SectionVO biSection = PolicyUtils.getSectionVO( policyVO, BI_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( biSection );
		BIVO biDetails = (BIVO) PolicyUtils.getRiskGroupDetails( locationDetails, biSection );

		if( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET.equals( tableId ) ){
			SumInsuredVO sumVO = (SumInsuredVO) depsVO[ 1 ];
			if( SvcConstants.BI_INCR_COST_WORKING_LIMIT.equals( sumVO.getaDesc() ) && !Utils.isEmpty( biDetails.getBiCwsAcwlId() ) && Utils.isEmpty( sumVO.getSumInsured() ) ){
				biDetails.setBiCwsAcwlId( null );
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, true );
				return true;
			}
			else if( SvcConstants.BI_RENT_RECEIVABLE.equals( sumVO.getaDesc() ) && !Utils.isEmpty( biDetails.getBiCwsRentId() ) && Utils.isEmpty( sumVO.getSumInsured() ) ){
				biDetails.setBiCwsRentId( null );
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, true );
				return true;
			}
			else if( SvcConstants.BI_ESTIMATED_GROSS_INCOME.equals( sumVO.getaDesc() ) && !Utils.isEmpty( biDetails.getBiCwsEGIncomeId() ) && Utils.isEmpty( sumVO.getSumInsured() ) ){
				biDetails.setBiCwsEGIncomeId( null );
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, true );
				return true;
			}
			//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
			//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
			/*else if( SvcConstants.BI_ANNUAL_RENT.equals( sumVO.getaDesc() ) && !Utils.isEmpty( biDetails.getBiCwsAnnualRentId() ) && Utils.isEmpty( sumVO.getSumInsured() ) ){
				biDetails.setBiCwsAnnualRentId(null);
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, true );
				return true;
			}
*/		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			Boolean isToBeDeleted = null;
			isToBeDeleted = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_DELETED );
			ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, null );

			return ( !Utils.isEmpty( isToBeDeleted ) && isToBeDeleted ) ? true : false;
		}
		ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, false );
		return false;

	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#updateKeyValuesToVOs(java.lang.String, com.rsaame.pas.cmn.pojo.POJO, com.rsaame.pas.vo.bus.PolicyVO, com.mindtree.ruc.cmn.base.BaseVO[], com.rsaame.pas.dao.cmn.SaveCase)
	 */
	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){
		BIVO biDetails = (BIVO) PolicyUtils.getRiskGroupDetailsForProcessing( policyVO, SvcConstants.SECTION_ID_BI );
		if( SvcConstants.TABLE_ID_T_TRN_CONSEQUENTIAL_LOSS.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnConsequentialLossQuo ){
					TTrnConsequentialLossQuo conseqLossQuote = (TTrnConsequentialLossQuo) mappedRecord;
					biDetails.setBasicRiskId( conseqLossQuote.getColBldId() );
					biDetails.setBiColId( conseqLossQuote.getColBldId() );
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET.equals( tableId ) )
		{
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnColWorkSheetQuo ){

					TTrnColWorkSheetQuo colWorkSheet = (TTrnColWorkSheetQuo) mappedRecord;
					if( colWorkSheet.getId().getCwsItemCode() == (short) BI_CWS_ITEM_CODE_WORK_LIMIT && !Utils.isEmpty( biDetails.getWorkingLimit() ) ){
						biDetails.setBiCwsAcwlId( colWorkSheet.getId().getCwsPolicyId() );
					}
					else if( colWorkSheet.getId().getCwsItemCode() == (short) BI_CWS_ITEM_CODE_RENT_RECIEVABLE && !Utils.isEmpty( biDetails.getRentRecievable() ) ){
						biDetails.setBiCwsRentId( colWorkSheet.getId().getCwsPolicyId() );
					}
					else if( colWorkSheet.getId().getCwsItemCode() == (short) BI_CWS_ITEM_CODE_GROSS_INCOME && !Utils.isEmpty( biDetails.getEstimatedGrossIncome() ) ){
						biDetails.setBiCwsEGIncomeId( colWorkSheet.getId().getCwsPolicyId() );
					}// Added for Adventnet Id:103286;To Move BI Section from PAR to BI
					//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
					/*else if( colWorkSheet.getId().getCwsItemCode() == (short) BI_CWS_ITEM_CODE_ANNUAL_RENT && !Utils.isEmpty( biDetails.getAnnualRent() ) ){
						biDetails.setBiCwsAnnualRentId( colWorkSheet.getId().getCwsPolicyId() );
					}*/// Added END for Adventnet Id:103286
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#tableRecPostSaveProcessing(java.lang.String, com.rsaame.pas.cmn.pojo.POJO, com.rsaame.pas.vo.bus.PolicyVO)
	 */
	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#tableRecPostProcessing(java.lang.String, com.rsaame.pas.cmn.pojo.POJO, com.rsaame.pas.vo.bus.PolicyVO)
	 */
	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){

		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructCreateRecordId(java.lang.String, com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.cmn.pojo.POJO)
	 */
	@Override
	protected POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){
		POJOId id = null;
		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnPremiumQuo ){
					TTrnPremiumQuo premiumQuo = (TTrnPremiumQuo) mappedRecord;
					TTrnPremiumQuoId pId = premiumQuo.getId();
					pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = pId;
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONSEQUENTIAL_LOSS.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnConsequentialLossQuo ){
					TTrnConsequentialLossQuo colQuo = (TTrnConsequentialLossQuo) mappedRecord;
					TTrnConsequentialLossQuoId colId = colQuo.getId();
					colId.setColValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = colId;
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET.equals( tableId ) ){
			if( !Utils.isEmpty( mappedRecord ) ){
				if( mappedRecord instanceof TTrnColWorkSheetQuo ){
					TTrnColWorkSheetQuo cwsQuo = (TTrnColWorkSheetQuo) mappedRecord;
					TTrnColWorkSheetQuoId cwdId = cwsQuo.getId();
					cwdId.setCwsValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = cwdId;
				}
				return id;
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
		
		if( SvcConstants.TABLE_ID_T_TRN_CONSEQUENTIAL_LOSS.equalsIgnoreCase( tableId ) ){
			TTrnConsequentialLossQuoId unId = new TTrnConsequentialLossQuoId();
			unId.setColPolicyId(( ( (TTrnConsequentialLossQuoId) existingId ).getColPolicyId() ));
			unId.setColValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = unId;
				 
		} else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			
			TTrnPremiumQuoId pId = new TTrnPremiumQuoId();
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_COL_WORK_SHEET.equalsIgnoreCase( tableId ) ){
			TTrnColWorkSheetQuoId unId = new TTrnColWorkSheetQuoId();
			TTrnColWorkSheetQuoId existingcId = (TTrnColWorkSheetQuoId) existingId;
			unId.setCwsItemCode( existingcId.getCwsItemCode() );
			unId.setCwsPolicyId(( ( (TTrnColWorkSheetQuoId) existingId ).getCwsPolicyId() ));
			unId.setCwsValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = unId;
				 
		}
		
		return id;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructAddtlCoverCntListForCurrRGD(com.rsaame.pas.vo.bus.RiskGroupDetails)
	 */
	@Override
	public List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails currRgd ){
		List<Contents> ADDTL_COVERS_LIST = new com.mindtree.ruc.cmn.utils.List<Contents>( Contents.class );
		return (com.mindtree.ruc.cmn.utils.List<Contents>) ADDTL_COVERS_LIST;
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#getBasicRiskIdFromCurrRGD(com.rsaame.pas.vo.bus.RiskGroupDetails)
	 */
	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){	
		BIVO biVO = (BIVO) rgd;
		Long basicRiskIdFromCurrRGD = biVO.getBasicRiskId();

		/* If basic risk id is not set to ParVO for some reason, throw a exception as basic risk id is one of key to  
		 * query premium table */
		if( Utils.isEmpty( basicRiskIdFromCurrRGD ) ){
			throw new BusinessException( "cmn.basicRiskIdIsNull", null, "Basic Risk Id for RGD [ " + rgd.toString() + " ] is null" );
		}
		return basicRiskIdFromCurrRGD;
		
	}

	/* (non-Javadoc)
	 * @see com.rsaame.pas.dao.cmn.BaseSectionSaveDAO#constructPOJOAForPrmTableMapping(com.rsaame.pas.vo.bus.PolicyVO, java.lang.Long)
	 */
	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){
		return null;
	}

	private boolean isWorkingLimitTobeUpdated( BIVO biVO ){
		boolean isTobeUpdated = false;
		/* If there is some value in working limit then it needs to send for DB call. Later on whether it needs to be updated or not is decided
		 * by forthcoming methods*/
		if( !Utils.isEmpty( biVO.getWorkingLimit() ) )
		{
			isTobeUpdated = true;
		}
		/* This is case when we delete a sum insured value. The hidden variable is not null but actual sum insured is 0*/
		else if( !Utils.isEmpty( biVO.getBiCwsAcwlId() ) )
		{
			isTobeUpdated = true;
		}
		return isTobeUpdated;
	}

	private boolean isRentReceivableTobeUpdated( BIVO biVO ){
		boolean isTobeUpdated = false;
		if( !Utils.isEmpty( biVO.getRentRecievable() ) ){
			isTobeUpdated = true;
		}
		else if( !Utils.isEmpty( biVO.getBiCwsRentId() ) ){
			isTobeUpdated = true;
		}
		return isTobeUpdated;
	}
	
	private boolean isEstimatedGrossIncomeTobeUpdated( BIVO biVO ){
		boolean isTobeUpdated = false;
		if( !Utils.isEmpty( biVO.getEstimatedGrossIncome()) ){
			isTobeUpdated = true;
		}
		else if( !Utils.isEmpty( biVO.getBiCwsEGIncomeId() ) ){
			isTobeUpdated = true;
		}
		return isTobeUpdated;
	}
	
	/**
	 * Sets LocationVO.toSave to false so that this location doesn't get picked up in the next SAVE call.
	 */
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		//updateSectionLevelSIAndPremium( policyVO );
		updateEndtText( policyVO );
		
		super.sectionPostProcessing( policyVO );
	}
	
	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) )
		{
			SectionVO biSection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( biSection );
			BIVO biDetails = (BIVO) PolicyUtils.getRiskGroupDetails( locationDetails, biSection );
			
			DAOUtils.deletePrevEndtText( biSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),BI_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			//TODO check on what conditions these needs to be called
			
			
				LOGGER.debug( "call pro_endt_text_col_work_sheet_add" );
				DAOUtils.addBICWSforendorsementFlow( biSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ) );
			
				LOGGER.debug( "call pro_endt_text_conseq_loss_add" );
				DAOUtils.addBICLSforendorsementFlow( biSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ) );
							
			
				LOGGER.debug( "call pro_endt_text_Cws_Col_del" );
				DAOUtils.deleteBICWSforendorsementFlow( biSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ) );
			
				LOGGER.debug( "call pro_endt_text_conseq_loss_del" );
				DAOUtils.deleteBICLSforendorsementFlow( biSection.getPolicyId(), policyVO,Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			
				LOGGER.debug( "call Pro_Endt_Text_Cws_Col_mod " );
				DAOUtils.updateBICWSforendorsementFlow( biSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), biDetails.getBasicRiskId() );
			
				LOGGER.debug( "call pro_endt_text_conseq_loss_col_mod" );
				DAOUtils.updateBICLSforendorsementFlow( biSection.getPolicyId(), policyVO, Long.valueOf( locationDetails.getRiskGroupId() ), biDetails.getBasicRiskId() );			
				
				LOGGER.debug( "call UW changes change endo SP" );
				DAOUtils.updateUWQuestions( biSection.getPolicyId(), policyVO,biSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()) );
			
				LOGGER.debug( "call deductible change endo SP" );
				DAOUtils.updateDeductibleforendorsementFlow( biSection.getPolicyId(), policyVO,biSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );
				
				//LOGGER.debug( "call Risk Add changes change endo SP" );
				//DAOUtils.updateEndTextForRiskAdd( biSection.getPolicyId(), policyVO,biSection.getSectionId());
				DAOUtils.updateTotalSITextforendorsementFlow( biSection.getPolicyId(), policyVO,biSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()),Long.valueOf(locationDetails.getRiskGroupId())  );
				

			
		}
	}
	//Added for Adventnet Id:103286;To Move BI Section from PAR to BI
	//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
	/*private boolean isAnnualRentTobeUpdated( BIVO biVO ){
		boolean isTobeUpdated = false;
		if( !Utils.isEmpty( biVO.getAnnualRent()) ){
			isTobeUpdated = true;
		}
		else if( !Utils.isEmpty( biVO.getBiCwsAnnualRentId())){
			isTobeUpdated = true;
		}
		return isTobeUpdated;
	}*/
	
	
}
