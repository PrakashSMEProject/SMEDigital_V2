package com.rsaame.pas.par.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.dao.model.TMasOccupancy;
import com.rsaame.pas.dao.model.TTrnBuildingQuo;
import com.rsaame.pas.dao.model.TTrnBuildingQuoId;
import com.rsaame.pas.dao.model.TTrnContentQuo;
import com.rsaame.pas.dao.model.TTrnContentQuoId;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuo;
import com.rsaame.pas.dao.model.TTrnPremiumQuoId;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuo;
import com.rsaame.pas.dao.model.TTrnUwQuestionsQuoId;
import com.rsaame.pas.dao.model.VMasPasFetchBasicDtls;
import com.rsaame.pas.dao.model.VMasPasFetchBasicInfo;
import com.rsaame.pas.dao.model.VParBldDeductibles;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.dao.utils.TableSnapshotLevel;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.ParContent;
import com.rsaame.pas.vo.app.ParContentHolder;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * 
 * @author m1014644
 * This Dao load and saves the par section
 *
 */
public class ParDAO extends BaseSectionSaveDAO implements IParDAO{

	private final static Logger LOGGER = Logger.getLogger( ParDAO.class );
	//private final static String COMMA = ",";
	//private final static Long NEW_QUOTE_ENDID = Long.valueOf( 0 );
	private final static String BLD_SEQ = "SEQ_BUILDING_ID";
	private final static String CONTENT_SEQ = "SEQ_CONTENTS_ID";
	
	//private final static String PAR_CRITERIA_Q = "from TMasCriteria criteria where criteria.criSecPtCode=? and criteria.criSecId=? ";
	//private final static String PAR_COMMISSION_Q = "from TMasCommisionType comType where comType.comScheme=?";
	private final static String OCCUPANCY_Q = "from TMasOccupancy where ocpCode=?";
	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Short PAR_CLASS = Short.valueOf( Utils.getSingleValueAppConfig( "PAR_CLASS" ) );
	private final static Integer CNT_RSK_CODE = 2;
	private final static int ZERO_VAL = 0;
	private final static Integer parCriteriaCode=Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_CRITERIA_CODE" ) );;

	
	PASStoredProcedure sp=null;
	
	/* (non-Javadoc)
	 * @see com.rsaame.pas.par.dao.IParDAOq#parLoad(com.mindtree.ruc.cmn.base.BaseVO)
	 */
	@Override
	public BaseVO parLoad( BaseVO baseVO ){
		/*String PAR_CNT_DEDUCTABLE_Q = "from TMasProductCriteria cntDeduct where cntDeduct.id.pcrSchCode=? and " +
		"cntDeduct.id.pcrTarCode=? and cntDeduct.id.pcrCriCode in ";*/
		if( LOGGER.isInfo() ) LOGGER.info( "Entering parLoad menthod" );

		if( LOGGER.isInfo() ) LOGGER.info( " Fetch data from V_PAR_DEDUCATBLES" );

		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof PolicyVO ){
				PolicyVO policyVO = (PolicyVO) baseVO;

				int tarCode = policyVO.getScheme().getTariffCode();

				DataHolderVO<List<Contents>> contentHolderVO = new DataHolderVO();
				/*
				java.util.List<Contents> contentsList =  new com.mindtree.ruc.cmn.utils.List<Contents>(Contents.class);

				 Following block of code is added to fetch criteria code from T_MAS_CRITERIA which will be used up
				 * to fetch records from T_MAS_PRODUCT_CRITERIA table as a additional criteria. This will ensure
				 * that the contents fetched are for the particular section Id and Policy Type combination 
				
				Short policyType=policyVO.getPolicyTypeCode().shortValue();
				Short secId=Short.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
				List<TMasCriteria> tMasCriterias=getHibernateTemplate().find( PAR_CRITERIA_Q,policyType,secId);
				Integer criCodes[]=new Integer[tMasCriterias.size()];
				String criCriteria=" ";
				if(!Utils.isEmpty( tMasCriterias) ){
					int index=0;
					Short criCode=null;
					for(TMasCriteria criteria: tMasCriterias){
						if(!Utils.isEmpty( criteria )){
							if(!Utils.isEmpty( criteria.getCriCode() )) criCode=criteria.getCriCode();
							//criCodes[index]=criCode.intValue();
							if(index==0){
								criCriteria+=" (";
							}
							criCriteria+=criCode.toString();
							if(!(index+1==tMasCriterias.size()))
								criCriteria+=",";
							else{
								criCriteria+=" )";
							}
							index++;
						}
					}
				}
				Adding additional criteria for fetching contents list.
				PAR_CNT_DEDUCTABLE_Q+=criCriteria;
				List<TMasProductCriteria> tMasProductCriterias = getHibernateTemplate().find( PAR_CNT_DEDUCTABLE_Q, schemeCode, Integer.valueOf( tarCode ) );
				//com.mindtree.ruc.cmn.utils.List<ParContent> parContent = new com.mindtree.ruc.cmn.utils.List<ParContent>( ParContent.class );
				for( TMasProductCriteria tMasProductCriteria : tMasProductCriterias ){
					
					if(!Utils.isEmpty(tMasProductCriteria.getPcrEDesc()) && tMasProductCriteria.getPcrEDesc().trim().equalsIgnoreCase("Occupancy"))
					{
						continue;
					}
					Contents content = new Contents();

					content.setContentDesc( tMasProductCriteria.getPcrEDesc() );
					content.setRiskType( tMasProductCriteria.getId().getPcrRtCode() );
					content.setRiskCat( Integer.parseInt( Utils.getSingleValueAppConfig( "Risk_Category" ) ) );
					content.setCoverCode( Integer.parseInt( Utils.getSingleValueAppConfig( "PAR_COVER" ) ) );
					content.setCoverSubType( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_SUB_TYPE ) ) );
					content.setCoverType( Integer.parseInt( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_TYPE ) ) );
					content.setRiskCode( Integer.parseInt( Utils.getSingleValueAppConfig( "PAR_CONTENT_RISK" ) ) );
					content.setRiskSubCat( Integer.parseInt( Utils.getSingleValueAppConfig( "Risk_Sub_Category" ) ) );
					contentsList.add( content );

				}
				contentHolderVO.setData(contentsList);*/

				/* Fetch master content list for the tariff from V_MAS_PAS_FETCH_BASIC_DTLS view */
				List<VMasPasFetchBasicDtls> NULL_BASIC_DTLS_LST = null;

				List<VMasPasFetchBasicDtls> vMasPasFetchBasicDtlsLst = DAOUtils.getDataFromVMasPasFetchBasicDtls( getHibernateTemplate(), baseVO, NULL_BASIC_DTLS_LST,
						DAOUtils.constructPPPCriteriaVOForPPPDataFetch( Integer.valueOf( PAR_CLASS.intValue() ), PAR_SECTION_ID, Integer.valueOf( tarCode ) ), true, CNT_RSK_CODE );

				List<VMasPasFetchBasicInfo> NULL_BASIC_INFO_LST = null;
				List<Contents> contentLst = DAOUtils.getContentsListForSection( baseVO, vMasPasFetchBasicDtlsLst, NULL_BASIC_INFO_LST );
				contentHolderVO.setData( contentLst );
				if( LOGGER.isInfo() ) LOGGER.info( "Exiting from parLoad" );

				return contentHolderVO;
			}
		}
		return baseVO;
	}

	@Override
	public BaseVO parLoadBlds( BaseVO baseVO ){

		//PolicyVO policyVO = (PolicyVO) baseVO;


		List<TMasOccupancy> occupancy = getHibernateTemplate().find( OCCUPANCY_Q, (short) 1 );

		int riskType = occupancy.get( 0 ).getOcpRtCode();
		ParContentHolder parCodeContent = new ParContentHolder();
		List<ParContent> parContent = new com.mindtree.ruc.cmn.utils.List<ParContent>( ParContent.class );

		List<VParBldDeductibles> vParCntDeductibles = getHibernateTemplate().find( "from VParBldDeductibles bldDeduct where bldDeduct.id.rtCode=?", riskType );
		ParContent content = new ParContent();
		com.mindtree.ruc.cmn.utils.List<BigDecimal> duct = new com.mindtree.ruc.cmn.utils.List<BigDecimal>( BigDecimal.class );

		content.setRiskType( 1 );

		for( VParBldDeductibles blds : vParCntDeductibles ){
			duct.add( blds.getId().getCompulsoryExcess() );
		}

		content.setDeductibles( duct );
		parContent.add( content );
		parCodeContent.setParContent( (com.mindtree.ruc.cmn.utils.List<ParContent>) parContent );
		return parCodeContent;

	}
	
	@Override
	public BaseVO parSaveDAO(BaseVO baseVO) {
		return saveSection( baseVO );
	}

	/* (non-Javadoc)
	* @see com.rsaame.pas.par.dao.IParDAOq#parSaveDAO(com.mindtree.ruc.cmn.base.BaseVO)
	*/
	@Override
	/**
	 * 
	 * Saves the par data into DB
	 */
	public BaseVO saveSection( BaseVO input ){
		if( !Utils.isEmpty( input ) ){
			if( input instanceof PolicyVO ){
				PolicyVO policyVO = (PolicyVO) input;

				//SvcUtils.writeObjToFile( policyVO );

				/* Let us get the system date right now and use from here on for this transaction. */
				/* Not required to set it again as it is already set within BaseSectionSaveDAO */
				//ThreadLevelContext.set( SvcConstants.TLC_KEY_SYSDATE, new Timestamp( System.currentTimeMillis() ) );

				SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
				//parSection.setPolicyId( policyVO.getPolicyNo() );
				LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
				ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );

				//parDetails.setPolicyId( policyVO.getPolicyNo() );

				/* Handle the building data. Premium update for the building will be handled inside
				 * this method. */
				TTrnBuildingQuo trnBuildingQuo = handleBuilding( policyVO, parSection, locationDetails, parDetails );

				/* Handle the building's content. Premium update for the contents will be handled inside
				 * this method. */
				handleContent( policyVO, parSection, locationDetails, parDetails, trnBuildingQuo );

				/* Handle the UW questions. */
				handleUWQ( policyVO, parSection, locationDetails, parDetails, trnBuildingQuo );

				return policyVO;
			}
		}
		return input;
	}



	private TTrnBuildingQuo handleBuilding( PolicyVO policyVO, SectionVO parSection, LocationVO locationDetails, ParVO parDetails ){
		//Renewal Multiple Id's handling changes, added policy in the query parameter
		TTrnBuildingQuo buildingQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_BUILDING, policyVO, null,null, false,parSection.getPolicyId(), parDetails.getBasicRiskId() );
		
		/* Setting buildingId into BasicRiskId for a particular section ( or for a particular class code ) into ThreadLevelContext which is stored into the extra column added to 
		 * T_TRN_SECTION_LOCATION / T_TRN_SECTION_LOCATION_QUO  
		 * */
		ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject( buildingQuo.getId().getBldId() ) );
		
		handlePremiumInsertion( policyVO,  parSection,  locationDetails,  parDetails , buildingQuo);
		return buildingQuo;
	}
	
	
	/**
	 * Handle's record creation(_version)/updation/deletion to T_TRN_PREMIUM(_QUO) table for T_TRN_BUILDING(_QUO)  
	 * @param policyVO
	 * @param parSection
	 * @param locationDetails
	 * @param parDetails
	 * @param buildingQuo
	 */
	private void handlePremiumInsertion( PolicyVO policyVO, SectionVO parSection, LocationVO locationDetails, ParVO parDetails, TTrnBuildingQuo buildingQuo ){
		/* The premium pojo mapper is common for both building and content, hence for building pass building pojo as dependent and 
		   content pojo as null
		*/
		
		
		POJO[] premiumDep = { buildingQuo, null };

		Contents NIL_CONTENT_FOR_BUILDING = null;
		
		PropertyRiskDetails NIL_PRD_FOR_BUILDING = null;
		/* Null Contents is passed while inserting a record to premium for basic risk itself */
		BaseVO[] bldDepVO = { NIL_CONTENT_FOR_BUILDING, NIL_PRD_FOR_BUILDING, parDetails.getBldPremium() };

		boolean prmTableToBeHandled = false;

		/*
		 * This check is added here coz in case of bld covered no, the isCreate and isDelete will both
		 * return false and the handleTbale record will try to fetch a data that is not available in 
		 * T_TRN_PREMIUM(_QUO) leading to exception
		 */
		if( ( Utils.isEmpty( locationDetails.getRiskGroupId() ) || locationDetails.getRiskGroupId().startsWith( "L" ) ) ){
			if( parDetails.getBuilCovered().intValue() == ZERO_VAL ){
				/* If its a case of fresh insert and building covered is selected as no, than do nothing in premium table */
				
					LOGGER.debug("Case of fresh insert of building");
			}
			else{
				prmTableToBeHandled = true;
			}
		}
		else if( parDetails.getBuilCovered().intValue() == ZERO_VAL ){
			List<POJO> list = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(), TableSnapshotLevel.CURRENT_VALID_STATE,
					getHibernateTemplate(), false, buildingQuo.getBldEndtId(), parSection.getPolicyId(), BigDecimal.valueOf( parDetails.getBasicRiskId() ),
					BigDecimal.valueOf( parDetails.getBasicRiskId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),
					Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_TYPE ) ), Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_SUB_TYPE ) ) );
			if( list.size() == 0 ){
				/* if the its not a first save and building covered is selected as no. and if there is no entry in the premium table
				  do nothing
				 */
				LOGGER.debug("if the its not a first save and building covered is selected as no. and if there is no entry in the premium table");
			}
			else{
				prmTableToBeHandled = true;
			}
		}
		else{
			prmTableToBeHandled = true;
		}
		if( prmTableToBeHandled ){
			TTrnPremiumQuo premium =  handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, premiumDep, bldDepVO, false, parSection.getPolicyId(), BigDecimal.valueOf( parDetails
					.getBasicRiskId() ), BigDecimal.valueOf( parDetails.getBasicRiskId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(), Short.valueOf( Utils
					.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_TYPE ) ), Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_SUB_TYPE ) ) );
		
			if( !Utils.isEmpty( parDetails.getBldPremium() ) && !Utils.isEmpty( premium.getPrmPremium() ) )
			{
				parDetails.getBldPremium().setPremiumAmt( premium.getPrmPremium().doubleValue() );
				if(!Utils.isEmpty( premium.getPrmPremiumActual()))
				{
					parDetails.getBldPremium().setPremiumAmtActual( premium.getPrmPremiumActual().doubleValue());
				}
			}
			
			
		}

	}

	private void handleContent( PolicyVO policyVO, SectionVO parSection, LocationVO locationDetails, ParVO parDetails, TTrnBuildingQuo trnBuildingQuo ){

		POJO[] contentDeps = { trnBuildingQuo };
		if( !Utils.isEmpty( parDetails.getCovers() ) && !Utils.isEmpty( parDetails.getCovers().getPropertyCoversDetails() ) ){
			java.util.List<PropertyRiskDetails> propertyCoversDetails = parDetails.getCovers().getPropertyCoversDetails();

			for( PropertyRiskDetails riskDetails : propertyCoversDetails ){
				
				if(! Utils.isEmpty( riskDetails.getCoverCode() ) && riskDetails.getCoverCode().intValue() != SvcConstants.APP_BASE_COVER_CODE ) continue;
				
				if( !Utils.isEmpty( riskDetails.getCoverId() ) && riskDetails.getCoverId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
					riskDetails.setCoverId( null );
				}
				if( Utils.isEmpty( riskDetails.getCoverId() ) && riskDetails.getCover() == 0.0 ){
					continue;
				}
				BaseVO[] contentDepsVO = { riskDetails };
				
				//Renewal Multiple Id's handling changes, added policy in the query parameter 
				TTrnContentQuo contentQuo = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_CONTENT, policyVO, contentDeps, contentDepsVO, false, parSection.getPolicyId(), riskDetails.getCoverId(),
				trnBuildingQuo.getId().getBldId() );
				POJO[] premiumDep = { trnBuildingQuo, contentQuo };
				Contents NIL_CONTENT_FOR_CNTS = null;
				BaseVO[] contentDepVO = { NIL_CONTENT_FOR_CNTS, riskDetails, riskDetails.getPremium() };
				
				/* Below additional clause value has been passed in order to differentiate between base cover records from 
				 * additional cover records within PREMIUM(_QUO) table */

				TTrnPremiumQuo premium =  handleTableRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO, premiumDep, contentDepVO, false, parSection.getPolicyId(),
						BigDecimal.valueOf( contentQuo.getId().getCntContentId() ), BigDecimal.valueOf( trnBuildingQuo.getId().getBldId() ),
						Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(),Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_TYPE ) ),
						Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_SUB_TYPE ) ) );
				
				/* This null check is basically added to avoid scenarios where in rating engine is not configured with premium for 
				 * particular content.
				 * For ex - In case of prepackage tariff, premium will be flat premium hence content wise premium will be NULL */
				if( !Utils.isEmpty( riskDetails.getPremium() ) && !Utils.isEmpty( premium.getPrmPremium() ) ){
					riskDetails.getPremium().setPremiumAmt( premium.getPrmPremium().doubleValue() );
				}
			}
		}
	}
	
	private void handleUWQ( PolicyVO policyVO, SectionVO parSection, LocationVO locationDetails, ParVO parDetails, TTrnBuildingQuo trnBuildingQuo ){
		
		SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
		RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
		
		POJO[] uwqDeps = { trnBuildingQuo };
		UWQuestionsVO questionsVOs = parDetails.getUwQuestions();
		List<UWQuestionVO> questions = questionsVOs.getQuestions();
		
		for(UWQuestionVO question: questions)
		{
			BaseVO[] uwqDepsVO = {question};
			handleTableRecord( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE, policyVO, uwqDeps, uwqDepsVO, false, parSection.getPolicyId(), question.getQId(),
					( Long.valueOf( rg.getRiskGroupId() ) ), Utils.isEmpty( question.getResponse() ) ? null : question.getResponse() );
		}
	}

	@Override
	protected POJO mapVOToPOJO( String tableId, PolicyVO policyVO, POJO[] deps, BaseVO[] depsVO ){
		POJO mappedPOJO = null;

		if( SvcConstants.TABLE_ID_T_TRN_BUILDING.equals( tableId ) ){
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
			ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );

			/* Construct the main Pojo instance with the other details. */
			TMasOccupancy occupancy = getOccDetails( Short.valueOf( locationDetails.getOccTradeGroup().toString() ) );
			TTrnBuildingQuo trnBuildingQuo = getBuildingPojo( locationDetails, parDetails, policyVO, occupancy );

			mappedPOJO = trnBuildingQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
			ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );

			Contents content = (Contents) depsVO[0];
			PropertyRiskDetails prd = (PropertyRiskDetails) depsVO[1];
			PremiumVO premiumVOBuilding = (PremiumVO) depsVO[2];
			
			TTrnPremiumQuo premiumQuo = getPremiumPojo( policyVO, (TTrnBuildingQuo) deps[ 0 ], (TTrnContentQuo) deps[ 1 ], parDetails.getBldDeductibles(), premiumVOBuilding, content, prd );

			mappedPOJO = premiumQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){		
			TTrnContentQuo contentQuo = getContentPojo( (TTrnBuildingQuo) deps[0], policyVO,(PropertyRiskDetails)depsVO[0] );
			mappedPOJO = contentQuo;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			
			UWQuestionVO question = (UWQuestionVO) depsVO[0];
			TTrnBuildingQuo buildingQuo = (TTrnBuildingQuo) deps[0];
			TTrnUwQuestionsQuo questionsQuo = getUWAPojo( question, policyVO, buildingQuo );
			mappedPOJO = questionsQuo;
		}
		return mappedPOJO;
	}
	
	/**
	 * Updates the identifiers to the VOs for assistance during next SAVE processing.
	 */
	@Override
	protected void updateKeyValuesToVOs( String tableId, POJO mappedRecord, PolicyVO policyVO, BaseVO[] depsVO, SaveCase saveCase ){
		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
		ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );
		if( SvcConstants.TABLE_ID_T_TRN_BUILDING.equals( tableId ) ){
			if(!Utils.isEmpty( mappedRecord )){
				if(mappedRecord instanceof TTrnBuildingQuo){
					updateKeyValuesToVOs( (TTrnBuildingQuo) mappedRecord, locationDetails, parDetails );
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			if(!Utils.isEmpty( mappedRecord )){
				if(mappedRecord instanceof TTrnContentQuo){
					updateKeyValuesToVOs( (TTrnContentQuo) mappedRecord, locationDetails, depsVO[ 0 ] );
				}
			}	
			/* In case of delete_pending_rec save case we are actually deleting record from T_TRN_CONTENT table hence there
			 * is no need to retain coverId of the content ( ) */
			if( saveCase == SaveCase.DELETE_PENDING_REC || saveCase == SaveCase.DELETE ){
				PropertyRiskDetails coverDetail = (PropertyRiskDetails) depsVO[ 0 ];
				Long CONTENT_COVER_DELETED = null;
				coverDetail.setCoverId( CONTENT_COVER_DELETED );
			}
		}
	}
	
	
	/**
	 * Updates the contentId to the PropertyRiskDetails in ParVO
	 * @param mappedRecord
	 * @param locationDetails
	 * @param depsVO
	 */
	private void updateKeyValuesToVOs( TTrnContentQuo mappedRecord, LocationVO locationDetails, BaseVO depsVO ){
		
		PropertyRiskDetails coverDetail = (PropertyRiskDetails) depsVO;
		coverDetail.setCoverId( mappedRecord.getId().getCntContentId() );
	}

	/**
	 * Updates the key values (BldId) of the building to LocationVO and ParVO.
	 * 
	 * @param trnBuildingQuo
	 * @param locationDetails
	 * @param parDetails
	 */
	private void updateKeyValuesToVOs( TTrnBuildingQuo trnBuildingQuo, LocationVO locationDetails, ParVO parDetails ){
		locationDetails.setRiskGroupId( String.valueOf( trnBuildingQuo.getId().getBldId() ) );
		parDetails.setBasicRiskId( trnBuildingQuo.getId().getBldId() );
		ThreadLevelContext.set( SvcConstants.TLC_KEY_BASIC_RISK_ID, CopyUtils.copySerializableObject( trnBuildingQuo.getId().getBldId() ) );
	}

	
	/**
	 * Constructs an Id for the CREATE SaveCase. This method handles all the tables that are used in PAR.
	 * @return The constructed POJOId instance that represents the primary of the table represented by the Id class
	 */
	@Override
	public POJOId constructCreateRecordId( String tableId, PolicyVO policyVO, POJO mappedRecord ){
		POJOId id = null;

		if( SvcConstants.TABLE_ID_T_TRN_BUILDING.equalsIgnoreCase( tableId ) ){
			TTrnBuildingQuoId tid = new TTrnBuildingQuoId();
			tid.setBldId( NextSequenceValue.getNextSequence( BLD_SEQ, null,null,getHibernateTemplate() ) );

			/* This is important: This record should have the same validity start date (VSD) as its corresponding
			 * policy record. Since we are going to insert into T_TRN_POLICY at the end of the processing 
			 * of this section and we are going to use ThreadLevelContext even there, the VSDs will be the same. */
			tid.setBldValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );

			id = tid;
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
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			TTrnContentQuoId cId = new TTrnContentQuoId();
			cId.setCntContentId( NextSequenceValue.getNextSequence( CONTENT_SEQ, null,null, getHibernateTemplate() ) );
			cId.setCntValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = cId;
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

		return id;
	}
	
	/**
	 * Construct an Id instance for the CHANGE and DELETE case. This method handles all the tables that are used in PAR.
	 * @return The constructed POJOId instance that represents the primary of the table represented by the Id class
	 */
	@Override
	public <T extends POJOId> POJOId constructChangeRecordId( String tableId, PolicyVO policyVO, T existingId ){
		POJOId id = null;
		
		if( SvcConstants.TABLE_ID_T_TRN_BUILDING.equalsIgnoreCase( tableId ) ){
			if( !Utils.isEmpty( existingId ) ){
				if( existingId instanceof TTrnBuildingQuoId ){

					TTrnBuildingQuoId existingTId = (TTrnBuildingQuoId) existingId;
					TTrnBuildingQuoId tid = new TTrnBuildingQuoId();
					tid.setBldId( existingTId.getBldId() );

					/* This is important: This record should have the same validity start date (VSD) as its corresponding
					 * policy record. Since we are going to insert into T_TRN_POLICY at the end of the processing 
					 * of this section and we are going to use ThreadLevelContext even there, the VSDs will be the same. */
					tid.setBldValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );

					id = tid;
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			TTrnPremiumQuoId pId = new TTrnPremiumQuoId();
			pId = (TTrnPremiumQuoId) CopyUtils.copySerializableObject( existingId );
			pId.setPrmValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
			id = pId;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			if( !Utils.isEmpty( existingId ) ){
				if( existingId instanceof TTrnContentQuoId ){

					TTrnContentQuoId existingcId = (TTrnContentQuoId) existingId;
					TTrnContentQuoId cId = new TTrnContentQuoId();
					cId.setCntContentId( existingcId.getCntContentId() );
					cId.setCntValidityStartDate( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
					id = cId;
				}
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_CREATE.equals( tableId ) ){
			LOGGER.debug( "Inside constructChangeRecordId method for UW_QUESTIONS" );
		}
		
		return id;
	}

	/* If there is no building Id, it means that this building has to be created. */
	@Override
	protected boolean isToBeCreated( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){
		if( SvcConstants.TABLE_ID_T_TRN_BUILDING.equals( tableId ) ){
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
			ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );
			/*
			 * Insertion or deletion in premium table depends on the  
			 */
			if( ( Utils.isEmpty( locationDetails.getRiskGroupId() ) || locationDetails.getRiskGroupId().startsWith( "L" ) ) ){
				/*
				 * if the building covered is selected as no then there is no need to make an entry into ttrnpremium
				 * even though an entry into ttrnbuilding table is required
				 */
				if( parDetails.getBuilCovered().equals( 0 ) ){
					ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, false );
				}
				else{
					ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, true );
				}

				return true;
			}
			else{
				/* It is possible that the premium record was deleted because the user had selected Building Covered as "No" in the
				 * previous save. In this save, if Building Covered has been made "Yes" again, we have to create the premium record. */
				if( !parDetails.getBuilCovered().equals( 0 ) ){
					try{
						DAOUtils.getExistingValidStateRecord( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(), getHibernateTemplate(), SvcConstants.IS_TABLE_QUERY_HBM, null, 
															  parSection.getPolicyId(), BigDecimal.valueOf( parDetails.getBasicRiskId() ), 
															  BigDecimal.valueOf( parDetails.getBasicRiskId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(), 
															  Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_TYPE ) ), Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_SUB_TYPE ) ) );
					}
					catch( BusinessException e ){
						ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, true );
					}
				}
				
				return false;
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			Boolean isCreated = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_CREATED );
			ThreadLevelContext.set( SvcConstants.PRM_TO_BE_CREATED, null );
			
			/* Hook has been added to override isCreated for additional cover save/update operations to 
			 * Premium Table. Condition checked signifies that Content object will not be NULL only 
			 * for additional cover cases */
			Contents content = (Contents) depsVO[0];
			if( !Utils.isEmpty( content ) ) isCreated = identifyCaseOfInsertForAddtlCovers(policyVO, depsPOJO, content);
			
			
			return ( !Utils.isEmpty( isCreated ) && isCreated ) ? true : false;
		}
		else if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			/*
			 * If cover id is null and cover is greater that 0 that means the risk is added and its a case of create 
			 */
			PropertyRiskDetails propertyRiskDetails = (PropertyRiskDetails) depsVO[ 0 ];
			if( Utils.isEmpty( propertyRiskDetails.getCoverId() ) && propertyRiskDetails.getCover() > 0.0 ){
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

	/* If there is a building Id and "Building Covered" has been selected as "No" on the UI, it means that 
	 * this building has to be deleted. */
	@Override
	protected boolean isToBeDeleted( String tableId, PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		//SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		//LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );

		/* Building record will always exist hence no handling is added below */
		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			PropertyRiskDetails propertyRiskDetails = (PropertyRiskDetails) depsVO[ 0 ];
			/*
			 * If CoverId is not null and cover is 0 that means the cover is deleted.
			 */
			if( !Utils.isEmpty( propertyRiskDetails.getCoverId() ) && propertyRiskDetails.getCover() == 0.0 ){
				ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, true );
				return true;
			}
		}
		else if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){

			Boolean isToBeDeleted = null;
			isToBeDeleted = (Boolean) ThreadLevelContext.get( SvcConstants.PRM_TO_BE_DELETED );
			ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, null );
			
			/* Hook has been added to override isToBeDeleted for building covered "Yes" or "No"
			 * selection by the user.
			 */
			if( depsPOJO[ 0 ] instanceof TTrnBuildingQuo && Utils.isEmpty( depsPOJO[ 1 ] ) ){
				isToBeDeleted = isToBeDeletedPrmRecordForBld( policyVO, depsPOJO, depsVO );
			}

			/* Hook has been added to override isToBeDeleted for additional cover in
			 * Premium Table. Condition checked signifies that Content object will not be NULL only 
			 * for additional cover cases */
			if( !Utils.isEmpty( depsVO ) ){
				Contents contentsVO = (Contents) depsVO[ 0 ];
				if( !Utils.isEmpty( contentsVO ) && contentsVO.getCoverOpted().intValue() == ZERO_VAL ){
					isToBeDeleted = isToBeDeletedPrmRecordAddtlCovers( policyVO, depsPOJO, depsVO );
				}
			}


			return ( !Utils.isEmpty( isToBeDeleted ) && isToBeDeleted ) ? true : false;
		}
		ThreadLevelContext.set( SvcConstants.PRM_TO_BE_DELETED, false );
		return false;
	}
	
	/**
	 * Handles post table save activities. Eg, summation of SI in the case of building.
	 */
	@Override
	protected void tableRecPostSaveProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		if( SvcConstants.TABLE_ID_T_TRN_BUILDING.equals( tableId ) ){
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
			ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );

			//TTrnBuildingQuo mappedBuildingRec = (TTrnBuildingQuo) mappedRecord;

			/*
			 *  Records insertion to T_TRN_BUILDING_QUO will always be done i.e even in case of building cover
			 *  indicator is chosen or configured as No. This is done as basic risk id will always be building id
			 *  for Contents records. Hence insertion of basic risk will be done out side of below for loop
			 *  and premium table entry will be done basis bld cover indicator value
			 */
			if( !Utils.isEmpty( parDetails.getBldCover() ) && parDetails.getBldCover().doubleValue() > 0 ){
				
				LOGGER.debug( "Inside tableRecPostSaveProcessing for Building"  );
			}

		}
		if( SvcConstants.TABLE_ID_T_TRN_CONTENT.equals( tableId ) ){
			LOGGER.debug( "Inside tableRecPostSaveProcessing for Content"  );
		}
	}
	
	@Override
	protected void tableRecPostProcessing( String tableId, POJO mappedRecord, PolicyVO policyVO ){
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
		
	}
	
	/**
	 * Sets LocationVO.toSave to false so that this location doesn't get picked up in the next SAVE call.
	 */
	@Override
	protected void sectionPostProcessing( PolicyVO policyVO ){
		cascadeUWAnswers( policyVO );
		updateSectionLevelSIAndPremium( policyVO );
		updateEndtText( policyVO );
		
		super.sectionPostProcessing( policyVO );
	}
	
	

	private void updateEndtText( PolicyVO policyVO ){
		if( ( policyVO.getAppFlow() == Flow.AMEND_POL ) ){
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, getSectionId() );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
			
			DAOUtils.deletePrevEndtText( parSection.getPolicyId(), (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID),PAR_SECTION_ID, Long.valueOf( locationDetails.getRiskGroupId() ) );
			
			//TODO check on what conditions these needs to be called
			
				LOGGER.debug( "call pro_endt_text_extra_bld_cnt" );
				DAOUtils.updateEBCforendorsementFlow( parSection.getPolicyId(), policyVO,Long.valueOf(locationDetails.getRiskGroupId()) ,parSection.getSectionId(),null);
			
				LOGGER.debug( "call pro_endt_text_bld_cnt_col" );
				DAOUtils.updateMBCforendorsementFlow( parSection.getPolicyId(), policyVO, Long.valueOf(locationDetails.getRiskGroupId()),parSection.getSectionId(),null );
			
				LOGGER.debug( "call delete endo SP" );
				DAOUtils.deleteCntforEndorsementFlow( parSection.getPolicyId(), policyVO,parSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()) ,null);
			
				LOGGER.debug( "call UW changes change endo SP" );
				DAOUtils.updateUWQuestions( parSection.getPolicyId(), policyVO,parSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()) );
				
				LOGGER.debug( "call deductible change endo SP" );
				DAOUtils.updateDeductibleforendorsementFlow( parSection.getPolicyId(), policyVO,parSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()), Long.valueOf(locationDetails.getRiskGroupId()) );
				
				DAOUtils.updateTotalSITextforendorsementFlow( parSection.getPolicyId(), policyVO,parSection.getSectionId(),  Long.valueOf(locationDetails.getRiskGroupId()), Long.valueOf(locationDetails.getRiskGroupId()) );
				
			
			// Calls procedure for generating endt Text for New Risk added.
			
				//LOGGER.debug( "call Risk Add changes change endo SP" );
				//DAOUtils.updateEndTextForRiskAdd( parSection.getPolicyId(), policyVO,parSection.getSectionId());
		}
	}

	private void cascadeUWAnswers( PolicyVO policyVO ){
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
			LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
			ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );


			if( !Utils.isEmpty( parDetails.getUwQuestions() ) && parDetails.getUwQuestions().isCascaded() ){
				
				PASStoredProcedure sp = (PASStoredProcedure) ( policyVO.getIsQuote() ? Utils.getBean( "cascadeUwqProc_QUO" ) : Utils.getBean( "cascadeUwqProc_POL" ) );
				try{
					getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
					Map results = sp.call( policyVO.getPolLinkingId(), parSection.getPolicyId(), ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ),
							ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_NO ), parDetails.getBasicRiskId(), PAR_SECTION_ID );
					if(Utils.isEmpty( results ))
					{
						LOGGER.debug( "called procedure cascadeUwqProc_QUO" );
					}
						
				}
				catch( DataAccessException e ){
					throw new BusinessException( "par.uwqCascade.exception", e, "An exception occured while executing stored proc." );
				}
			}		
	}

	private void updateSectionLevelSIAndPremium( PolicyVO policyVO ){
		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
		ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );
		parDetails.setSumInsured(getSectionLevelSumInsured(parDetails));
		/*
		 * In pre - pack, the premium is set at section level, hence no need to consolidate cover level premium
		 */
		if( !Utils.isEmpty( policyVO.getIsPrepackaged() ) && !policyVO.getIsPrepackaged()){
			if( Utils.isEmpty( parDetails.getPremium() ) ){
				parDetails.setPremium( new PremiumVO() );
			}
			parDetails.getPremium().setPremiumAmt(getSectionLevelPremium(parDetails));
		}
		
	}

	private double getSectionLevelSumInsured( ParVO parDetails ){
		return PremiumHelper.getSectionLevelSumInsured( parDetails );
	}
	
	private double getSectionLevelPremium( ParVO parDetails ){
		return PremiumHelper.getSectionLevelPremium( parDetails );
	}

	private TTrnUwQuestionsQuo getUWAPojo( UWQuestionVO question, PolicyVO policyVO, TTrnBuildingQuo buildingQuo ){

			TTrnUwQuestionsQuo uwQuestionsQuo = new TTrnUwQuestionsQuo();
			TTrnUwQuestionsQuoId id = new TTrnUwQuestionsQuoId();
			SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
			id.setUqtPolPolicyId( parSection.getPolicyId() );
			id.setUqtPolEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID  ) );
			id.setUqtUwqCode( question.getQId() );
			id.setUqtLocId( buildingQuo.getId().getBldId() );
			uwQuestionsQuo.setId( id );
			uwQuestionsQuo.setStatus( SvcConstants.POL_STATUS_PENDING );
			uwQuestionsQuo.setUqtUwqAnswer( question.getResponse() );
			uwQuestionsQuo.setUqtValidityStartDate((Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD));
			uwQuestionsQuo.setUqtValidityExpiryDate( SvcConstants.EXP_DATE );

		return uwQuestionsQuo;
	}

	private TTrnBuildingQuo getBuildingPojo( LocationVO locationVO, ParVO parVO, PolicyVO policyVO, TMasOccupancy occupancy ){
		TTrnBuildingQuo trnBuildingQuo = BeanMapper.map( locationVO, TTrnBuildingQuo.class );
		
		mapNullFields( trnBuildingQuo,locationVO );
		
		trnBuildingQuo = BeanMapper.map( parVO, trnBuildingQuo );
		trnBuildingQuo = BeanMapper.map( parVO.getUwDetails(), trnBuildingQuo );

		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		trnBuildingQuo.setBldPolicyId( parSection.getPolicyId() );


		/*
		 * Start Date and end date to be cascaded from PolicyVO.validityStartDate
		 * which is updated by General Info Save Operation. Hence the same
		 * validityStartDate to be carried accross all DB entries for the
		 * Quote
		 */
		
		/*
		 * Fix for ePlatform 2.1 release. Correcting start date and end date being set.
		 */
		if( !Utils.isEmpty( policyVO.getScheme().getEffDate() ) ){
			trnBuildingQuo.setBldStartDate( policyVO.getScheme().getEffDate() );
		}
		else{
			trnBuildingQuo.setBldStartDate( (Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}

		if( !Utils.isEmpty(policyVO.getPolExpiryDate()) ){
			trnBuildingQuo.setBldEndDate( policyVO.getPolExpiryDate() );
		}

		setAuditDetailsforBld( trnBuildingQuo, policyVO );

		trnBuildingQuo.setBldEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ); // for new quote its always 0;
		trnBuildingQuo.setBldValidityExpiryDate( SvcConstants.EXP_DATE );
		// trnBuildingQuo.setBldRskCode(occupancy.getOcpRskCode());
		trnBuildingQuo.setBldRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_BASIC_RISK ) ) );
		trnBuildingQuo.setBldZoneCode( Short.valueOf( Utils.getSingleValueAppConfig( "BLD_ZONE_CODE" ) ) ); // TODO confirm with anand
		trnBuildingQuo.setBldStatus( (byte) 6 );
		trnBuildingQuo.setBldRskType( occupancy.getOcpRtCode() );
		trnBuildingQuo.setBldMunCode( 1 ); // one for dubai
		trnBuildingQuo.setBldRiRskCode( occupancy.getOcpRiRskCode() );
		
		/*Defect Fix Start: Setting BldOccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
		BigDecimal occTradeCode=DAOUtils.getOccupancyTradeCode(getHibernateTemplate(), policyVO, getSectionId(),BigDecimal.valueOf(occupancy.getOcpTradeCode()),parCriteriaCode) ;
		if(!Utils.isEmpty(occTradeCode))trnBuildingQuo.setBldOccupancyCode(occTradeCode.shortValue() );
		/*Defect Fix End: Setting BldOccupancyCode as fetched from TMasProductCriteria on the basis of criteria code  */
		
		if( !Utils.isEmpty( parVO.getBldCover() ) ){
		    	trnBuildingQuo.setBldSumInsured(new BigDecimal(parVO.getBldCover()));
		}
		
		if( !Utils.isEmpty( trnBuildingQuo.getBldDirCode() )){		
			 Short lookupCode = SvcUtils.getLookUpCodeForLOneLTwo( SvcConstants.PAR_BLD_GEO_CD_LKP_CATEGORY, trnBuildingQuo.getBldDirCode().toString(), SvcConstants.LOOKUP_LEVEL1 );
			 if(!Utils.isEmpty(lookupCode)){
				 trnBuildingQuo.setBldGeoareaCode( lookupCode.shortValue() );
			 }
		}

		return trnBuildingQuo;
	}
	
	/**
	 * Updating the non mandatory fields from VO to POJO as in case if the field values 
	 * removed then mapper will not update the null value to POJO
	 * @param trnBuildingQuo
	 * @param locationVO
	 */
	private void mapNullFields( TTrnBuildingQuo trnBuildingQuo, LocationVO locationVO ){
		
		trnBuildingQuo.setBldAAddress1( locationVO.getAddress().getOfficeShopNo());
		trnBuildingQuo.setBldAAddress2( locationVO.getAddress().getFloor() );
		trnBuildingQuo.setBldEStreetName( locationVO.getAddress().getStreetName() );
		trnBuildingQuo.setBldZip( locationVO.getAddress().getPoBox() );
	}

	private void setAuditDetailsforBld(TTrnBuildingQuo trnBuildingQuo,PolicyVO policyVO) {
		
		Integer userId = SvcUtils.getUserId(policyVO);
		//TODO: Need to find a way to conditionally update the prepared by columns. If its an update there is no need to update this column
		trnBuildingQuo.setBldPreparedBy(userId);
		trnBuildingQuo.setBldPreparedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
		trnBuildingQuo.setBldModifiedBy(userId);
		trnBuildingQuo.setBldModifiedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
	}

	private TTrnContentQuo getContentPojo( TTrnBuildingQuo trnBuildingQuo, PolicyVO policyVO, PropertyRiskDetails depsVO ){
		TTrnContentQuo contentQuo = new TTrnContentQuo();
		/*Long cntSequence = NextSequenceValue.getNextSequence("CONTENT_SEQ",getHibernateTemplate());
		
		TTrnContentQuoId contentQuoId = new TTrnContentQuoId();
		contentQuoId.setCntContentId(cntSequence);
		contentQuoId.setCntValidityStartDate(new Date());
		contentQuo.setId(contentQuoId);*/
		
		contentQuo.setCntSumInsured( new BigDecimal( depsVO.getCover() ) );
		contentQuo.setCntDescription( depsVO.getDesc() );

		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		contentQuo.setCntPolicyId(parSection.getPolicyId() );
		contentQuo.setCntBasicRiskId( trnBuildingQuo.getId().getBldId() );
		contentQuo.setCntRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_CONTENT_RISK" ) ) );
		contentQuo.setCntCategory(depsVO.getRiskType()); //from tmas occp
		contentQuo.setCntRiskDtl( Long.valueOf(Utils.getSingleValueAppConfig( "PAR_RISK_DTL" )) );
		/*
		 * Start Date and end date to be cascaded from PolicyVO.validityStartDate
		 * which is updated by General Info Save Operation. Hence the same
		 * validityStartDate to be carried accross all DB entries for the
		 * Quote
		 */
		
		/*
		 * Fix for ePlatform 2.1 release. Correcting start date and end date being set
		 */
		if (!Utils.isEmpty(policyVO.getScheme().getEffDate()))
		{
			contentQuo.setCntStartDate(policyVO.getScheme().getEffDate());
		}
		else
		{
			contentQuo.setCntStartDate((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}
		
		if(!Utils.isEmpty(policyVO.getPolExpiryDate()))
		{
			contentQuo.setCntEndDate(policyVO.getPolExpiryDate());
		}
		
		
		setAuditDetailsforcontent(contentQuo,policyVO);
		
		contentQuo.setCntBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_BASIC_RISK ) ) );
		contentQuo.setCntEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID) );
		contentQuo.setCntValidityExpiryDate( SvcConstants.EXP_DATE );
		 
		contentQuo.setCntRiRskCode( trnBuildingQuo.getBldRiRskCode() );
		//contentQuo.setCntRiRskCode( occupancy.getOcpRiRskCode() ); //from tmas occp
		contentQuo.setCntStatus( (byte) 6 ); // always one for create quote;
		contentQuo.setCntTradeCode( trnBuildingQuo.getBldRskType() );
		//contentQuo.setCntTradeCode( occupancy.getOcpTradeCode() ); //from tmas occp

		return contentQuo;
	}
	
	
	/*private boolean riskHasToBeDeleted(TTrnBuildingQuo endorsedBldDetail) {
		if(!Utils.isEmpty( endorsedBldDetail.getBldSumInsured()))
			return endorsedBldDetail.getBldSumInsured().compareTo(BigDecimal.valueOf(0)) == 0;
		return false;
	}*/
	

	private void setAuditDetailsforcontent(TTrnContentQuo contentQuo,
			PolicyVO policyVO) {
		Integer userId = SvcUtils.getUserId(policyVO);
		//TODO: Need to find a way to conditionally update the prepared by columns. If its an update there is no need to update this column
		contentQuo.setCntPreparedBy(userId);
		contentQuo.setCntPreparedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
		contentQuo.setCntModifiedBy(userId);
		contentQuo.setCntModifiedDt((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		
	}

	/*private TTrnSectionDetailsQuo getSection( PolicyVO policyVO ){
		TTrnSectionDetailsQuo tTrnSectionDetailsQuo = new TTrnSectionDetailsQuo();
		TTrnSectionDetailsQuoId detailsQuoId = new TTrnSectionDetailsQuoId();

		detailsQuoId.setSecPolicyId( policyVO.getPolicyNo() );
		detailsQuoId.setSecSecId( Short.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) ) );
		//detailsQuoId.setSecValidityStartDate( new Date() );
		
		 * ValidityStartDate to be cascaded from PolicyVO.validityStartDate
		 * which is updated by General Info Save Operation. Hence the same
		 * validityStartDate to be carried across all DB entries for the
		 * Quote
		 
		if(!Utils.isEmpty(policyVO.getValidityStartDate())){
			detailsQuoId.setSecValidityStartDate(policyVO.getValidityStartDate());
		}else{
			detailsQuoId.setSecValidityStartDate((Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ) );
		}
		tTrnSectionDetailsQuo.setId( detailsQuoId );

		tTrnSectionDetailsQuo.setSecClCode( Short.valueOf( Utils.getSingleValueAppConfig( "PAR_CLASS" ) ) );
		tTrnSectionDetailsQuo.setSecEndtId( NEW_QUOTE_ENDID );
		tTrnSectionDetailsQuo.setSecPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		tTrnSectionDetailsQuo.setSecValidityExpiryDate( SvcConstants.EXP_DATE );
		return tTrnSectionDetailsQuo;
	}*/

	private TTrnPremiumQuo getPremiumPojo( PolicyVO policyVO, TTrnBuildingQuo trnBuildingQuo, TTrnContentQuo trnContentQuo, Double deductible, PremiumVO premium, Contents content, PropertyRiskDetails prd){
		
		TTrnPremiumQuo premiumQuo = new TTrnPremiumQuo();

		TTrnPremiumQuoId premiumQuoId = new TTrnPremiumQuoId();
		premiumQuoId.setPrmBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_BASIC_RISK ) ) );
		//premiumQuoId.setPrmBasicRskId( new BigDecimal( trnBuildingQuo.getId().getBldId() ) );
		premiumQuoId.setPrmBasicRskId( BigDecimal.valueOf( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_BASIC_RISK_ID ) ) );
		
		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		premiumQuoId.setPrmPolicyId(parSection.getPolicyId() );
		
		/* All the contents will now be associated with cover code, cover type, cover sub type , risk type , 
		 * risk category and risk sub category when rendered on the UI */
		if(Utils.isEmpty( content )){
			premiumQuoId.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "PAR_COVER" ) ) );
			premiumQuoId.setPrmCtCode( Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_TYPE ) ) );
			premiumQuoId.setPrmCstCode( Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_SUB_TYPE ) ) );
		}else{
			premiumQuoId.setPrmCovCode(  content.getCoverCode().shortValue());
			premiumQuoId.setPrmCtCode( content.getCoverType().shortValue() );
			premiumQuoId.setPrmCstCode( content.getCoverSubType().shortValue() );
		}
		
		/* Case of insert for Contents (BASE_COVER) */
		if( !Utils.isEmpty( trnContentQuo ) ){
			premiumQuoId.setPrmRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_CONTENT_RISK" ) ) );
			premiumQuoId.setPrmRskId( new BigDecimal( trnContentQuo.getId().getCntContentId() ) );
			premiumQuoId.setPrmValidityStartDate( trnContentQuo.getId().getCntValidityStartDate());
			premiumQuo.setPrmSumInsured( trnContentQuo.getCntSumInsured() );
			premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( prd.getDeductibles() ) );
			premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER);
			premiumQuo.setPrmRtCode( trnContentQuo.getCntCategory() );
			
		}
		/* Case of insert for Contents (ADDITIONAL_COVER) */
		else if( !Utils.isEmpty( content )){
			premiumQuoId.setPrmRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_BASIC_RISK ) ) );
			premiumQuoId.setPrmRskId( new BigDecimal( trnBuildingQuo.getId().getBldId() ) );
			premiumQuoId.setPrmValidityStartDate( trnBuildingQuo.getId().getBldValidityStartDate());
			premiumQuo.setPrmSumInsured( content.getCover() );
			premiumQuo.setPrmCompulsoryExcess( content.getDeductibles() );
			premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_NON_BASE_COVER);
		}
		/* Case of insert for Building (BASIC RISK)*/
		else{
			premiumQuoId.setPrmRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_BASIC_RISK ) ) );
			premiumQuoId.setPrmRskId( new BigDecimal( trnBuildingQuo.getId().getBldId() ) );
			premiumQuoId.setPrmValidityStartDate( trnBuildingQuo.getId().getBldValidityStartDate());
			premiumQuo.setPrmSumInsured( trnBuildingQuo.getBldSumInsured() );
			premiumQuo.setPrmRtCode( trnBuildingQuo.getBldRskType() );
			if( !Utils.isEmpty( deductible ) ) premiumQuo.setPrmCompulsoryExcess( BigDecimal.valueOf( deductible ) );
			premiumQuo.setPrmSitypeCode( SvcConstants.PRM_SITYPE_CODE_BASE_COVER);
		}
		
		SvcUtils.setAuditDetailsforPrm(premiumQuo, policyVO,(Date) ThreadLevelContext.get( com.Constant.CONST_SYSDATE ));
		
		/*
		 * update premium value to PremiumPOJO
		 */
		setPremiumAmtToPrmPOJO( policyVO, premium, premiumQuo, content, prd);

		premiumQuo.setPrmClCode( PAR_CLASS );
		premiumQuo.setId( premiumQuoId );
		premiumQuo.setPrmValidityExpiryDate( SvcConstants.EXP_DATE );
		premiumQuo.setPrmEndtId( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) );
		premiumQuo.setPrmRiRskCode( trnBuildingQuo.getBldRiRskCode() );
		premiumQuo.setPrmStatus( (byte) 1 ); // for new quote	
		premiumQuo.setPrmSumInsuredCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmPremiumCurr( Byte.valueOf( Utils.getSingleValueAppConfig( "DEFAULT_CURRENCY" ) ) );
		premiumQuo.setPrmRiLocCode( SvcConstants.APP_PRM_RI_LOC_CODE );
		premiumQuo.setPrmPtCode( Short.valueOf( Utils.getSingleValueAppConfig( "SBS_Policy_Type" ) ) );
		/*
		 * Fix for ePlatform 2.1 release. Correcting start date and end date being set
		 */
		premiumQuo.setPrmEffectiveDate( policyVO.getScheme().getEffDate() );
		premiumQuo.setPrmExpiryDate( policyVO.getPolExpiryDate());
		
		premiumQuo.setPrmFnCode( SvcConstants.PRM_FN_CODE );
		premiumQuo.setPrmPreparedDt((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
		setRateTypeToPremiumPOJO( policyVO, premiumQuo);
		
		return premiumQuo;

	}
	
	

	private TMasOccupancy getOccDetails( Short ocpCode ){
		return (TMasOccupancy) getHibernateTemplate().find( "from TMasOccupancy occ where occ.ocpCode=?", (short) ocpCode ).get( 0 );
	}

	
	public void updateTtrnPolicyforEndorse(PolicyVO policyVO,int sectionId){
		TTrnPolicyQuo trnPolicy=null;
		List<TTrnPolicyQuo>  listTtrnPolicy =  getHibernateTemplate().find( "from TTrnPolicyQuo tTrnPolicy where  tTrnPolicy.polLinkingId = ?  and tTrnPolicy.polClassCode =" + sectionId +" and tTrnPolicy.id.polEndtId = ? ", policyVO.getPolLinkingId(),policyVO.getNewEndtId() ) ;
		if (Utils.isEmpty(listTtrnPolicy))
		{
			trnPolicy =  (TTrnPolicyQuo) getHibernateTemplate().find( "from TTrnPolicyQuo tTrnPolicy where  tTrnPolicy.polLinkingId = ?  and tTrnPolicy.polClassCode  =" + sectionId +"   ", policyVO.getPolLinkingId() ).get( 0 ) ;
			trnPolicy.getId().setPolEndtId(policyVO.getNewEndtId());
			trnPolicy=	CopyUtils.copySerializableObject(trnPolicy);
			trnPolicy.setPolStatus( SvcConstants.POL_STATUS_PENDING.byteValue() );
			trnPolicy.setPolDocumentCode(Byte.valueOf(SvcConstants.ENDORSE_POL_DOC_CODE));
			trnPolicy.setPolEndtNo(policyVO.getNewEndtNo());
			
			trnPolicy.setPolValidityExpiryDate(SvcConstants.EXP_DATE);
			trnPolicy.setPolValidityStartDate((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
			
			policyVO.setValidityStartDate((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
			
			saveOrUpdate(trnPolicy);
		}else
		{
			trnPolicy=listTtrnPolicy.get(0);
			trnPolicy.setPolStatus(Byte.valueOf(SvcConstants.ENDORSE_STATUS_NO));
			trnPolicy.setPolDocumentCode(Byte.valueOf(SvcConstants.ENDORSE_POL_DOC_CODE));
			trnPolicy.getId().setPolEndtId(policyVO.getNewEndtId());
			trnPolicy.setPolEndtNo(policyVO.getNewEndtNo());
			
			trnPolicy.setPolValidityExpiryDate(SvcConstants.EXP_DATE);
			trnPolicy.setPolValidityStartDate((Date)ThreadLevelContext.get( com.Constant.CONST_SYSDATE));
			saveOrUpdate(trnPolicy);
		}
	}

	@Override
	protected int getSectionId(){
		return PAR_SECTION_ID;
	}
	
	@Override
	protected int getClassCode(){
		return PAR_CLASS.intValue();
	}

	@Override
	public com.mindtree.ruc.cmn.utils.List<Contents> constructAddtlCoverCntListForCurrRGD( RiskGroupDetails rgd ){

		List<Contents> ADDTL_COVERS_LIST = new com.mindtree.ruc.cmn.utils.List<Contents>( Contents.class );
		ParVO rgdOfPAR = (ParVO) rgd;

		if( !Utils.isEmpty( ( rgdOfPAR ) ) && !Utils.isEmpty( rgdOfPAR.getCovers().getPropertyCoversDetails() ) ){
			java.util.List<PropertyRiskDetails> propertyCoversDetails = rgdOfPAR.getCovers().getPropertyCoversDetails();

			for( PropertyRiskDetails riskDetails : propertyCoversDetails ){

				if( !Utils.isEmpty( riskDetails.getCoverCode() ) && riskDetails.getCoverCode().intValue() == SvcConstants.APP_BASE_COVER_CODE ) continue;

				if( !Utils.isEmpty( riskDetails.getCoverId() ) && riskDetails.getCoverId().equals( CommonConstants.DEFAULT_LOW_LONG ) ){
					riskDetails.setCoverId( null );
				}
				Contents content ;
				content = BeanMapper.map( riskDetails, Contents.class );
				ADDTL_COVERS_LIST.add( content );
			}
		}
		return (com.mindtree.ruc.cmn.utils.List<Contents>) ADDTL_COVERS_LIST;
	}

	@Override
	public Long getBasicRiskIdFromCurrRGD( RiskGroupDetails rgd ){
		ParVO parVO = (ParVO) rgd;
		Long basicRiskIdFromCurrRGD = parVO.getBasicRiskId();
		
		/* If basic risk id is not set to ParVO for some reason, throw a exception as basic risk id is one of key to  
		 * query premium table */
		if(Utils.isEmpty( basicRiskIdFromCurrRGD )){
			throw new BusinessException( "cmn.basicRiskIdIsNull", null , "Basic Risk Id for RGD [ "+ rgd.toString()+" ] is null");
		}
		return basicRiskIdFromCurrRGD;
	}

	@Override
	public POJO[] constructPOJOAForPrmTableMapping( PolicyVO policyVO, Long basicRiskIdOfCurrRGD ){

		SectionVO sectionOfPAR = policyVO.getRiskDetails().get(0);
		
		//TTrnBuildingQuo basisRiskPOJO = handleTableRecord( SvcConstants.TABLE_ID_T_TRN_BUILDING, policyVO, null,null, false, basicRiskIdOfCurrRGD );
		List<POJO> list = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_BUILDING, policyVO.getAppFlow(), TableSnapshotLevel.CURRENT_VALID_STATE,
				getHibernateTemplate(), false, ( (Long) ThreadLevelContext.get( SvcConstants.TLC_KEY_ENDT_ID ) ), sectionOfPAR.getPolicyId(), basicRiskIdOfCurrRGD );
		TTrnBuildingQuo basisRiskPOJO  = null;
		if( !Utils.isEmpty( list ) )  basisRiskPOJO = (TTrnBuildingQuo) list.get( 0 );
		TTrnContentQuo NULL_CONTENT_POJO = null;
		POJO[] pojosForPrmTableMapping = { basisRiskPOJO, NULL_CONTENT_POJO }; /* NULL T_TRN_CONTENT(_QUO) POJO for additional covers */
		return pojosForPrmTableMapping;
	}
	
	/**
	 * This method will be invoked from getPremiumPOJO method to set the premium amount depending
	 * Explicit handling has been added for prepackaged tariff as there will be no premium returned
	 * for each of the contents. Hence premium obtained from RiskGroupDetails i.e CurrRGD will be
	 * set against Basic Contents which is configured as RiskType - 999 and it is constant
	 * @param policyVO
	 * @param premiumForTheEntity
	 * @param prmPOJO
	 * @param content
	 */
	public void setPremiumAmtToPrmPOJO( PolicyVO policyVO, PremiumVO premiumForTheEntity, TTrnPremiumQuo prmPOJO, Contents addtlCoverContent, PropertyRiskDetails basicCovercontent ){
		if( !Utils.isEmpty( premiumForTheEntity ) ){
			if( !Utils.isEmpty( premiumForTheEntity.getPremiumAmt() ) ){
				/*The premium obtained from rating engine is not the final premium*/
				prmPOJO.setPrmPremium( new BigDecimal( String.valueOf(premiumForTheEntity.getPremiumAmt() )) );
				prmPOJO.setPrmPremiumActual( new BigDecimal( String.valueOf(premiumForTheEntity.getPremiumAmt()) ) );
			}
		}else{
			setZeroPrmValue( prmPOJO );
		}
		
		if( policyVO.getIsPrepackaged().booleanValue() && !Utils.isEmpty( basicCovercontent ) ){
			if( basicCovercontent.getRiskType().intValue() == SvcConstants.APP_BASIC_CONTENT_CODE ){
				SectionVO section = PolicyUtils.getSectionVO( policyVO, getSectionId() );
				RiskGroup rg = PolicyUtils.getRiskGroupForProcessing( section );
				RiskGroupDetails rgd = PolicyUtils.getRiskGroupDetails( rg, section );
				/*The premium obtained from rating engine is not the final premium*/
				if( !Utils.isEmpty( rgd.getPremium() ) ){
					prmPOJO.setPrmPremium( new BigDecimal( rgd.getPremium().getPremiumAmt() ) );
					prmPOJO.setPrmPremiumActual( new BigDecimal( rgd.getPremium().getPremiumAmt() ) );
				}
			}
			else{
				setZeroPrmValue( prmPOJO );
			}
		}
		if( !Utils.isEmpty( addtlCoverContent ) && Utils.isEmpty( prmPOJO.getPrmPremium() ) ){
			setZeroPrmValue( prmPOJO );
		}
	}
	
	/**
	 * This method will identify if premium(_quo) table record for a building needs to be deleted
	 * @param tableId
	 * @param policyVO
	 * @param depsPOJO
	 * @param depsVO
	 * @return
	 */
	public Boolean isToBeDeletedPrmRecordForBld( PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		Boolean isToBeDeleted = null;
		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
		ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );

		if( !Utils.isEmpty( depsPOJO[ 0 ] ) && parDetails.getBuilCovered().intValue() == ZERO_VAL ) {
			List<POJO> list = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(), TableSnapshotLevel.CURRENT_VALID_STATE,
					getHibernateTemplate(), false, ( (TTrnBuildingQuo) depsPOJO[ 0 ] ).getBldEndtId(), parSection.getPolicyId(), BigDecimal.valueOf( parDetails
							.getBasicRiskId() ), BigDecimal.valueOf( parDetails.getBasicRiskId() ), Integer.valueOf( SvcConstants.APP_BASE_COVER_CODE ).shortValue(), Short
							.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_TYPE ) ), Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_PAR_COVER_SUB_TYPE ) ) );
			if( list.size() >= 1 ){
				isToBeDeleted = true;
			}
		}
		
		return isToBeDeleted;
	}
	
	/**
	 * This method will identify if premium(_quo) table record for additional covers needs to be deleted
	 * @param tableId
	 * @param policyVO
	 * @param depsPOJO
	 * @param depsVO
	 * @return
	 */
	public Boolean isToBeDeletedPrmRecordAddtlCovers( PolicyVO policyVO, POJO[] depsPOJO, BaseVO[] depsVO ){

		Boolean isToBeDeleted = null;
		SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
		LocationVO locationDetails = (LocationVO) PolicyUtils.getRiskGroupForProcessing( parSection );
		ParVO parDetails = (ParVO) PolicyUtils.getRiskGroupDetails( locationDetails, parSection );

		Contents content = (Contents) depsVO[ 0 ];
		List<POJO> list = (List<POJO>) DAOUtils
				.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(), TableSnapshotLevel.CURRENT_VALID_STATE, getHibernateTemplate(), false,
						( (TTrnBuildingQuo) depsPOJO[ 0 ] ).getBldEndtId(), parSection.getPolicyId(), BigDecimal.valueOf( parDetails.getBasicRiskId() ), BigDecimal
								.valueOf( parDetails.getBasicRiskId() ), content.getCoverCode().shortValue(), content.getCoverType().shortValue(), content.getCoverSubType()
								.shortValue() );

		if( list.size() >= 1 ){
			isToBeDeleted = true;
		}
		return isToBeDeleted;
	}

	/**
	 * 
	 * @param tableId
	 * @param policyVO
	 * @param depsPojo
	 * @param depsVO
	 * @return
	 * @override
	 */
	@Override
	public boolean handleTableRecordToBeCalled( String tableId, PolicyVO policyVO, POJO[] depsPojo, BaseVO[] depsVO ){

		if( SvcConstants.TABLE_ID_T_TRN_PREMIUM.equals( tableId ) ){
			if( !Utils.isEmpty( depsVO ) ){
				Contents content = (Contents) depsVO[ 0 ];
				if( !Utils.isEmpty( content ) ){
					/* Check for the scenario where in user has not opted for cover type during quote creation itself
					 * i.e. during fresh case of insert */
					if( content.getCoverOpted().intValue() == SvcConstants.APP_ADDITIONAL_COVER_NOT_OPTED ){
						SectionVO parSection = PolicyUtils.getSectionVO( policyVO, PAR_SECTION_ID );
						TTrnBuildingQuo ttrnBldPOJO = (TTrnBuildingQuo) depsPojo[ 0 ];
						if( !Utils.isEmpty( ttrnBldPOJO ) ){
							List<POJO> list = (List<POJO>) DAOUtils.getTableSnapshotQuery( SvcConstants.TABLE_ID_T_TRN_PREMIUM, policyVO.getAppFlow(),
									TableSnapshotLevel.CURRENT_VALID_STATE, getHibernateTemplate(), false, ttrnBldPOJO.getBldEndtId(), parSection.getPolicyId(),
									BigDecimal.valueOf( ttrnBldPOJO.getId().getBldId() ), BigDecimal.valueOf( ttrnBldPOJO.getId().getBldId() ),
									content.getCoverCode().shortValue(), content.getCoverType().shortValue(), content.getCoverSubType().shortValue() );
							if( Utils.isEmpty( list ) ){
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	protected void sectionPreProcessing( PolicyVO policyVO ){
		super.sectionPreProcessing( policyVO );
	}
	
}
