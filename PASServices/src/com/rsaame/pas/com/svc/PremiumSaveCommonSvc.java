package com.rsaame.pas.com.svc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.clauses.svc.ViewClausesSvc;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.cmn.ICommonOpDAO;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.SsVMasLookup;
import com.rsaame.pas.dao.model.SsVMasLookupId;
import com.rsaame.pas.dao.model.TTrnPolicyCondition;
import com.rsaame.pas.dao.model.TTrnPolicyConditionQuo;
import com.rsaame.pas.dao.premium.IMinPremiumDAO;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.GeneralInfoCommonSvc;
import com.rsaame.pas.policyAction.svc.PolicyActionCommonSvc;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.taglib.dao.ILoadCoverDAO;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PersonalAccidentPersonVO;
import com.rsaame.pas.vo.bus.PersonalAccidentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.StandardClause;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author M1020859
 * 
 * This class is a common service class for Premium Details which calls the BaseSaveOperation to perform the save/load operation
 *
 */

public class PremiumSaveCommonSvc extends BaseService{

	private static final String CALL_TARIFF_CHANGE_PROCEDURE = "callTariffChangeProcedure";

	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger.getLogger( PremiumSaveCommonSvc.class );

	private static List<String> SPECIAL_COVER_CODES = null;
	
	private BaseSaveSvc baseSaveSvc;
	private BaseLoadSvc baseLoadSvc;
	private TravelPackagePremiumSvc travelPackagePremiumSvc;
	private ViewClausesSvc viewClauseSvc;
	private IMinPremiumDAO minPrmHomeDao;
	
	ILoadCoverDAO loadCoverDAO;
	ICommonOpDAO commonOpDAO;

	public ILoadCoverDAO getLoadCoverDAO(){
		return loadCoverDAO;
	}

	public void setLoadCoverDAO( ILoadCoverDAO loadCoverDAO ){
		this.loadCoverDAO = loadCoverDAO;
	}

	public ICommonOpDAO getCommonOpDAO(){
		return commonOpDAO;
	}

	public void setCommonOpDAO( ICommonOpDAO commonOpDao ){
		this.commonOpDAO = commonOpDao;
	}
	
	

	/**
	 * @param minPrmHomeDao the minPrmHomeDao to set
	 */
	public void setMinPrmHomeDao( IMinPremiumDAO minPrmHomeDao ){
		this.minPrmHomeDao = minPrmHomeDao;
	}

	/**
	 * @return the baseSaveSvc
	 */
	public BaseSaveSvc getBaseSaveSvc(){
		return baseSaveSvc;
	}

	/**
	 * @param baseSaveSvc the baseSaveSvc to set
	 */
	public void setBaseSaveSvc( BaseSaveSvc baseSaveSvc ){
		this.baseSaveSvc = baseSaveSvc;
	}

	/**
	 * @return the baseSaveSvc
	 */
	public ViewClausesSvc getViewClauseSvc(){
		return viewClauseSvc;
	}
	
	/**
	 * @param viewClauseSvc the viewClauseSvc to set
	 */
	public void setViewClauseSvc( ViewClausesSvc viewClauseSvc ){
		this.viewClauseSvc = viewClauseSvc;
	}

	/**
	 * @return the baseLoadSvc
	 */
	public BaseLoadSvc getBaseLoadSvc(){
		return baseLoadSvc;
	}

	/**
	 * @param baseLoadSvc the baseLoadSvc to set
	 */
	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}

	/**
	 * @return TravelPackagePremiumSvc
	 */
	public TravelPackagePremiumSvc getTravelPackagePremiumSvc() {
		return travelPackagePremiumSvc;
	}

	/**
	 * @param travelPackagePremiumSvc
	 */
	public void setTravelPackagePremiumSvc( TravelPackagePremiumSvc travelPackagePremiumSvc ) {
		this.travelPackagePremiumSvc = travelPackagePremiumSvc;
	}

	/**
	 * 
	 */
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){

		Object returnValue = null;

		if( SvcConstants.SAVE_PREMIUM.equals( methodName ) ){
			returnValue = savePremiumDetails( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.LOAD_PREMIUM.equals( methodName ) ){
			returnValue = loadPremiumDetails( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.SAVE_TRAVEL_PREMIUM.equals( methodName ) ){
			returnValue = saveTravelPremiumDetails( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.APPROVE_TRAVEL_QUOTE.equals( methodName ) ){
			returnValue = approveTravelQuote( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.APPLY_MIN_PRM_HOME.equals( methodName ) ){
			returnValue = applyMinPrmHome( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.GET_MIN_PRM_TO_APPLY_HOME.equals( methodName ) ){
			returnValue = getMiniumPremiumToApply( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.GET_CONFIG_MIN_PRM.equals( methodName ) ){
			returnValue = getConfiguredMinPremium( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.SAVE_PA_PREMIUM.equals( methodName ) ){
			returnValue = savePersonalAccidentPremiumDetails( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	private BigDecimal getConfiguredMinPremium( BaseVO baseVO ){
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		return minPrmHomeDao.getConfiguredMinPremium( homeInsuranceVO.getScheme().getTariffCode(), Short.valueOf( Utils.getSingleValueAppConfig( "HOME_SEC_ID" ) ),
				Integer.valueOf( homeInsuranceVO.getScheme().getPolicyCode() ), LOB.HOME );
	}

	private BigDecimal getMiniumPremiumToApply( BaseVO baseVO ){
		return minPrmHomeDao.getMiniumPremiumToApply( baseVO, LOB.HOME );
	}

	private BaseVO applyMinPrmHome( BaseVO baseVO ){
		minPrmHomeDao.applyMinPremium( baseVO, LOB.HOME );
		return baseVO;
	}

	/**
	 * This is a common method to be invoked by both home insurance and travel
	 * insurance instances
	 * 
	 * @param baseVO
	 * @return
	 */
	private BaseVO savePremiumDetails( BaseVO baseVO ){

		HomeInsuranceVO homeInsuranceVO = null;
		TravelInsuranceVO travelInsuranceVO = null;
		PolicyDataVO polDataVO = (PolicyDataVO) baseVO;
		GeneralInfoCommonSvc generalInfoLoadSvc = null;
		if( polDataVO.getCommonVO().getIsQuote() ){
			generalInfoLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( com.Constant.CONST_COMMONGENSVCBEAN );
		}
		else{
			generalInfoLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( com.Constant.CONST_COMMONGENSVCBEAN_POL );
		}

		if( polDataVO.getCommonVO().getLob().equals( LOB.HOME ) ){
			homeInsuranceVO = (HomeInsuranceVO) Utils.newInstance( "com.rsaame.pas.vo.bus.HomeInsuranceVO" );
			homeInsuranceVO = (HomeInsuranceVO) baseVO;
			PolicyDataVO polDataFrmBaseVO = (PolicyDataVO) generalInfoLoadSvc.invokeMethod( SvcConstants.LOAD_GEN_INFO, homeInsuranceVO );
			if(!Utils.isEmpty(polDataFrmBaseVO.getGeneralInfo().getAdditionalInfo())){
				if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getAdditionalInfo())){
					homeInsuranceVO.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
				}
				homeInsuranceVO.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(polDataFrmBaseVO.getGeneralInfo()
						.getAdditionalInfo().getAffinityRefNo());
			}
			saveHomePremiumDets( homeInsuranceVO, polDataFrmBaseVO );
		}
		else{
			return null;
		}
		return baseVO;
	}

	/**
	 * Method to call the rating engine for travel and save the Travel premium to ttrnpremium table
	 */
	private BaseVO saveTravelPremiumDetails( BaseVO baseVO ){
		DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) baseVO;

		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) dataHolder.getData()[ 0 ];
		Boolean isPopulateOperation = (Boolean) dataHolder.getData()[ 1 ];
		GeneralInfoCommonSvc generalInfoLoadSvc = null;

		if( travelInsuranceVO.getCommonVO().getIsQuote() ){
			generalInfoLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( com.Constant.CONST_COMMONGENSVCBEAN );
		}
		else{
			generalInfoLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( com.Constant.CONST_COMMONGENSVCBEAN_POL );
		}

		PolicyDataVO polDataFrmBaseVO = (PolicyDataVO) generalInfoLoadSvc.invokeMethod( SvcConstants.LOAD_GEN_INFO, travelInsuranceVO );
		if(!Utils.isEmpty(polDataFrmBaseVO.getGeneralInfo().getAdditionalInfo())){
			if(Utils.isEmpty(travelInsuranceVO.getGeneralInfo())){
				travelInsuranceVO.setGeneralInfo(new GeneralInfoVO());
				travelInsuranceVO.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
			}
			if(Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getAdditionalInfo())){
				travelInsuranceVO.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
			}
			travelInsuranceVO.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(polDataFrmBaseVO.getGeneralInfo()
					.getAdditionalInfo().getAffinityRefNo());
		}
		List<TableData> premiumList = getExistingPremiumRecs( travelInsuranceVO.getCommonVO() );
		if( !Flow.CREATE_QUO.equals( travelInsuranceVO.getCommonVO().getAppFlow() ) && !isPopulateOperation ){
			if( !Utils.isEmpty( polDataFrmBaseVO.getScheme().getTariffCode() )&& !travelInsuranceVO.getScheme().getTariffCode().equals( polDataFrmBaseVO.getScheme().getTariffCode() ) ){
				CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
				// Added equals() instead of == to avoid sonar violation on 25-9-2017
				if( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_ACTIVE ) ).equals( travelInsuranceVO.getCommonVO().getStatus() )){
					travelInsuranceVO = (TravelInsuranceVO) commonOpSvc.invokeMethod( "getNextEndorsementId", travelInsuranceVO );
				}
				commonOpSvc.invokeMethod( CALL_TARIFF_CHANGE_PROCEDURE, travelInsuranceVO );
				travelInsuranceVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_PENDING );
				travelInsuranceVO.setStatus( SvcConstants.POL_STATUS_PENDING );
			}
		}
		polDataFrmBaseVO.getScheme().setTariffCode( travelInsuranceVO.getScheme().getTariffCode() );
		//polDataFrmBaseVO.getScheme().setSchemeCode( travelInsuranceVO.getScheme().getSchemeCode() );

		/*Start : Changes for B2C. Map Reward programme details from the submitted travelInsuranceVO.*/
		if( !Utils.isEmpty( travelInsuranceVO.getGeneralInfo() ) && !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getInsured() ) ){
			
			if( !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType() ) ){
				polDataFrmBaseVO.getGeneralInfo().getInsured().setRoyaltyType( travelInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType() );
			}
			
			if( !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo() ) ){
				polDataFrmBaseVO.getGeneralInfo().getInsured().setGuestCardNo( travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo() );
			}
			
		}
		/*Start : Changes for B2C. Map Reward programme details from the submitted travelInsuranceVO.*/
		saveTravelPremiumDets( travelInsuranceVO, polDataFrmBaseVO, isPopulateOperation , premiumList);

		return travelInsuranceVO;
	}

	
	
	/**
	 * Method to save the PersonalAccident premium to ttrnpremium table
	 */
	private BaseVO savePersonalAccidentPremiumDetails( BaseVO baseVO ){

		LOGGER.info( "Entering savePersonalAccidentPremiumDetails() " );

		PersonalAccidentVO personalAccidentVO = (PersonalAccidentVO) baseVO;
		//	Boolean isPopulateOperation = (Boolean) dataHolder.getData()[ 1 ];
		GeneralInfoCommonSvc generalInfoLoadSvc = null;

		//--check if we have a quote or policy and get corresponding service bean
		if( personalAccidentVO.getCommonVO().getIsQuote() ){
			generalInfoLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( com.Constant.CONST_COMMONGENSVCBEAN );
		}
		else{
			generalInfoLoadSvc = (GeneralInfoCommonSvc) Utils.getBean( com.Constant.CONST_COMMONGENSVCBEAN_POL );
		}

		//-- All the related info is got from Db so that all the data can be stored at once by calling base save.
		PolicyDataVO polDataFrmBaseVO = (PolicyDataVO) generalInfoLoadSvc.invokeMethod( SvcConstants.LOAD_GEN_INFO, personalAccidentVO );

		
		List<TableData> premiumList = getExistingPremiumRecs( personalAccidentVO.getCommonVO() );

		//-- If flow is not createquote and  not populateoperation then get next endorsement id and invoke tariffchange procedure. 
		//--- also set the status as POL_STATUS_PENDING (pending or incomplete)
		if( !Flow.CREATE_QUO.equals( personalAccidentVO.getCommonVO().getAppFlow() )  ){
			if( !Utils.isEmpty( polDataFrmBaseVO.getScheme().getTariffCode() )&& !personalAccidentVO.getScheme().getTariffCode().equals( polDataFrmBaseVO.getScheme().getTariffCode() ) ){
				CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
				// Added equals() instead of == to avoid sonar violation on 25-9-2017
				if( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_ACTIVE ) ).equals( personalAccidentVO.getCommonVO().getStatus())){
					personalAccidentVO = (PersonalAccidentVO) commonOpSvc.invokeMethod( "getNextEndorsementId", personalAccidentVO );
				}
				commonOpSvc.invokeMethod( CALL_TARIFF_CHANGE_PROCEDURE, personalAccidentVO );
				personalAccidentVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_PENDING );
				personalAccidentVO.setStatus( SvcConstants.POL_STATUS_PENDING );
			}
		}
		polDataFrmBaseVO.getScheme().setTariffCode( personalAccidentVO.getScheme().getTariffCode() );


		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder1 = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();
	//----
		List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> premiumQuoTableDataList = new ArrayList<TableData>( 0 );
		TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
		//----


		polDataFrmBaseVO.setPremiumVO( personalAccidentVO.getPremiumVO() );
		polDataFrmBaseVO.setCommission( personalAccidentVO.getCommission() );
		
		/*
		 * Call rating and get the premium
		 */
		//-- no call to rating engine as it is PA . VO already has premium manually entered in it.
		//	personalAccidentVO = (PersonalAccidentVO) travelPackagePremiumSvc.invokeMethod( SvcConstants.GET_PACKAGE_PREMIUM, personalAccidentVO );

		if(!Utils.isEmpty(personalAccidentVO.getPremiumVO()) && !Utils.isEmpty(personalAccidentVO.getPremiumVO().getPolicyFees())){
			polDataFrmBaseVO.getPremiumVO().setPolicyFees( personalAccidentVO.getPremiumVO().getPolicyFees() );
		}
		
		if(!Utils.isEmpty(personalAccidentVO.getPremiumVO()) && !Utils.isEmpty(personalAccidentVO.getPremiumVO().getGovtTax())){
			polDataFrmBaseVO.getPremiumVO().setGovtTax( personalAccidentVO.getPremiumVO().getGovtTax() );
		}
		
		CommonVO commonVO = personalAccidentVO.getCommonVO();
		if(!Utils.isEmpty( commonVO )){
			personalAccidentVO.getCommonVO().setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ));
		}
		
		
		TTrnPremiumVOHolder trnPremiumQuoVOHolder;
		TableData<TTrnPremiumVOHolder> premiumTableData;
		/*BigDecimal sumInsured;
		BigDecimal zeroVal = new BigDecimal( 0 );
		BigDecimal oneVal = new BigDecimal( 1 );

		Object[] splCodesForAddtlCovers = null;
		Object[] splCodesForBasicCovers = null;
		boolean fetchedCodesForBasicCovers = false;
		boolean fetchedCodesForAddtlCovers = false;
		Double packageLevelProratedPrm = null;*/
		//Commented the variable isProrated to avoid Dead store to local variable sonar violation on 19-9-2017
	//	Boolean isProrated = Boolean.FALSE;
		//String[] basicCheckBoxCovers = Utils.getMultiValueAppConfig( "MANDATORY_COVERS_TRAVEL" );


		
		
		
		//get the list from PersonalAccidentVo... iterate thro every person. get the cover details for every person.

		List<PersonalAccidentPersonVO> personalAccidentPersonList;
		personalAccidentPersonList=personalAccidentVO.getPersonalAccidentPersonVO();


		for( PersonalAccidentPersonVO personalAccidentPersonVO : personalAccidentPersonList )	{


			List<CoverDetailsVO> covers = personalAccidentPersonVO.getCovers();

			/*Sort the covers so that the premium and sum insured details can be set to the cover with least cover code.*/
			//Collections.sort( covers );

			for( CoverDetailsVO coverDetailsVO : covers ){
				LOGGER.info(personalAccidentPersonVO.getPersonDetailsVO().getName()+" "+coverDetailsVO.getCoverName());

				premiumTableData = new TableData<TTrnPremiumVOHolder>();
				trnPremiumQuoVOHolder = new TTrnPremiumVOHolder();

				trnPremiumQuoVOHolder.setPrmClCode( polDataFrmBaseVO.getPolicyClassCode().shortValue() );
				trnPremiumQuoVOHolder.setPrmPtCode( new Short( polDataFrmBaseVO.getPolicyType().toString() ) );
				trnPremiumQuoVOHolder.setPrmPolicyId( polDataFrmBaseVO.getPolicyId() );
				trnPremiumQuoVOHolder.setPrmEndtId( polDataFrmBaseVO.getCommonVO().getEndtId() );
				//trnPremiumQuoVOHolder.setPrmEffectiveDate( gaccPersonVOHolder.getGprStartDate() );
				trnPremiumQuoVOHolder.setPrmExpiryDate( polDataFrmBaseVO.getScheme().getExpiryDate() );
				
				if(!Utils.isEmpty(coverDetailsVO) && !Utils.isEmpty(coverDetailsVO.getCoverCodes()) ){
					trnPremiumQuoVOHolder.setPrmCstCode( coverDetailsVO.getCoverCodes().getCovSubTypeCode() );
					trnPremiumQuoVOHolder.setPrmCovCode( coverDetailsVO.getCoverCodes().getCovCode() );
					trnPremiumQuoVOHolder.setPrmCtCode( coverDetailsVO.getCoverCodes().getCovTypeCode() );
				}
				
				trnPremiumQuoVOHolder.setPrmValidityStartDate( coverDetailsVO.getVsd() );

				trnPremiumQuoVOHolder.setPrmSumInsured( new BigDecimal( coverDetailsVO.getSumInsured().getSumInsured() ) );
				
				
				trnPremiumQuoVOHolder.setPrmPremium( new BigDecimal( personalAccidentPersonVO.getPersonDetailsVO().getPremiumAmt() ) );
				trnPremiumQuoVOHolder.setPrmPremiumActual( new BigDecimal(personalAccidentPersonVO.getPersonDetailsVO().getPremiumAmtActual() ) );

				if(!Utils.isEmpty(personalAccidentPersonVO.getPersonDetailsVO().getGprId()) ){
				coverDetailsVO.getRiskCodes().setRskId( new BigDecimal( personalAccidentPersonVO.getPersonDetailsVO().getGprId()) );
				coverDetailsVO.getRiskCodes().setBasicRskId( new BigDecimal( personalAccidentPersonVO.getPersonDetailsVO().getGprId() ) );

				trnPremiumQuoVOHolder.setPrmRskId( new BigDecimal( personalAccidentPersonVO.getPersonDetailsVO().getGprId() ) );
				trnPremiumQuoVOHolder.setPrmBasicRskId( new BigDecimal( personalAccidentPersonVO.getPersonDetailsVO().getGprId() ) );

				}
				
			
				trnPremiumQuoVOHolder.setPrmRscCode( 0 ); //TODO To confirm on risk code
				trnPremiumQuoVOHolder.setPrmRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "GPR_RI_RSK_CODE_TRAVEL" ) ));
				
				if(!Utils.isEmpty(coverDetailsVO) && !Utils.isEmpty(coverDetailsVO.getRiskCodes()) ){
					trnPremiumQuoVOHolder.setPrmRskCode( coverDetailsVO.getRiskCodes().getRiskCode() );
					trnPremiumQuoVOHolder.setPrmBasicRskCode( coverDetailsVO.getRiskCodes().getBasicRskCode() );
					
					trnPremiumQuoVOHolder.setPrmRcCode( coverDetailsVO.getRiskCodes().getRiskCat() );
					trnPremiumQuoVOHolder.setPrmRtCode( coverDetailsVO.getRiskCodes().getRiskType() );
				}
				
				
				//				splCodesForBasicCovers = commonOpDAO.getSpecialCodes( trnPremiumQuoVOHolder );
				//
				//				if( !Utils.isEmpty( splCodesForBasicCovers ) ){
				//					setValuesToPojo( trnPremiumQuoVOHolder, splCodesForBasicCovers );
				//				}					


				TTrnPremiumVOHolder savedRelatedRec = trnPremiumQuoVOHolder.getRelatedPrmRec( premiumList );
				if( !Utils.isEmpty( savedRelatedRec )  ){
					trnPremiumQuoVOHolder.setPrmValidityStartDate( savedRelatedRec.getPrmValidityStartDate() );
				}

				premiumTableData.setTableData( trnPremiumQuoVOHolder );
				premiumQuoTableDataList.add( premiumTableData );

			}

			/*if( isProrated ){	*/ //Commented empty if condition check (Not content inside if) - sonar violation fix
				/*
				double deletedPrm = 0.0;
				java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPAS( "select PKG_PAS_QUO_POL_TRAVEL.GET_CANCEL_TVL_DETAIL(" + commonVO.getPolicyId() + ","
						+ commonVO.getEndtId() + " ) from dual" );
				if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
					deletedPrm = ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue();
				}
				if( !Utils.isEmpty( packageLevelProratedPrm ) && packageLevelProratedPrm.compareTo( 0.0 ) != 0 ){
					//	TravelPackageVO selectedPackage = travelInsuranceVO.getSelectedPackage();
					//	selectedPackage.setPremiumAmt( packageLevelProratedPrm.doubleValue() + deletedPrm );
				}else{
					if( !Utils.isEmpty( deletedPrm ) && deletedPrm != 0 ){
						TravelPackageVO selectedPackage = travelInsuranceVO.getSelectedPackage();
						//	selectedPackage.setPremiumAmt( deletedPrm );
					}
				}
				 */
			//}




		}

		
		//---Setting the policy data

		policyTableData.setTableData( polDataFrmBaseVO );
		policyTableDataList.add( policyTableData );
		
		dataMap.put( SvcConstants.T_TRN_POLICY, policyTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, premiumQuoTableDataList );
		dataHolder1.setData( dataMap );

		baseSaveSvc.invokeMethod( com.Constant.CONST_BASESAVE, dataHolder1, commonVO );
		polDataFrmBaseVO.setEndtId(commonVO.getEndtId());
		personalAccidentVO.setPolicyId( polDataFrmBaseVO.getPolicyId() );
		return personalAccidentVO;

	}
	
	
	private BaseVO approveTravelQuote(BaseVO baseVO){
		LOGGER.info( "Entering Home Insurance Approve Quote" );
		TravelInsuranceVO travelInsuranceVO  = (TravelInsuranceVO) saveTravelPremiumDetails(baseVO);
		PolicyCommentsHolder polCommHoler= new PolicyCommentsHolder();
		polCommHoler.setCommonDetails(travelInsuranceVO.getCommonVO());
		approveQuote(polCommHoler);
		return travelInsuranceVO;
	}
	
	
	private void approveQuote(BaseVO baseVo){
		PolicyActionCommonSvc polComnSvc = null;
		if(((PolicyCommentsHolder)baseVo).getCommonDetails().getIsQuote()){
			 polComnSvc =  (PolicyActionCommonSvc) Utils.getBean( "polActCommonSvc" );
		} else {
			 polComnSvc =  (PolicyActionCommonSvc) Utils.getBean( "polActCommonSvc_POL" );			
		}
		polComnSvc.invokeMethod( "approveQuote", baseVo);
	}
	
	/**
	 * This method saves the travel insurance related premium details (Call to BaseSaveOpertaion)
	 * 
	 * 
	 * @param travelInsuranceVO has travel insurance details that are selected.
	 * @param policyDataVO
	 * @param premiumList 
	 */
	@SuppressWarnings( "rawtypes" )
	private void saveTravelPremiumDets( TravelInsuranceVO travelInsuranceVO, PolicyDataVO policyDataVO, boolean isPopulateOperation, List<TableData> premiumList ){

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		List<TravelPackageVO> travelPackageList = travelInsuranceVO.getTravelPackageList();
		// contains only 1 
		TravelPackageVO travelPackageVO = null;
		if(travelPackageList.size() > 1 ){
			travelPackageVO = travelInsuranceVO.getSelectedPackage();
		}else{
			travelPackageVO = travelPackageList.get( 0 );
			travelPackageVO.setIsSelected( true );
		}
		List<CoverDetailsVO> covers = travelPackageVO.getCovers();

		TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
		TableData<TMasCashCustomerVOHolder> cashCustTableData = new TableData<TMasCashCustomerVOHolder>();

		List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> premiumQuoTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> gaccPersonQuoTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> cashCustTableDataList = new ArrayList<TableData>( 0 );

		CommonVO commonVO = travelInsuranceVO.getCommonVO();
		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		inputVO.setQuoteNo( commonVO.getQuoteNo() );
		inputVO.setPolicyNo( commonVO.getPolicyNo() );
		inputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		inputVO.setDocCode( commonVO.getDocCode() );
		String loggenInLoc = Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION);
		Map<String, Class<? extends BaseVO>> dataMapForLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataMapForLoad.put( SvcConstants.T_TRN_GACC_PERSON, TTrnGaccPersonVOHolder.class );

		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolderForLoad = (DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>>) baseLoadSvc.invokeMethod(
				com.Constant.CONST_BASELOAD, inputVO, dataMapForLoad );

		List<TableData<BaseVO>> gaccTableData = dataHolderForLoad.getData().get( SvcConstants.T_TRN_GACC_PERSON );

		policyDataVO.setPremiumVO( travelInsuranceVO.getPremiumVO() );
		/**
		 * Modified for mirror site.
		 */
		
		// To avoid empty if block (Sonar defect) 12-9-2017
		/*if(!Utils.isEmpty( travelInsuranceVO.getCommission()));
		{*/
			policyDataVO.setCommission( travelInsuranceVO.getCommission() );
		/*}*/
		double policyFees = travelInsuranceVO.getPremiumVO().getPolicyFees();
		double govtTax = travelInsuranceVO.getPremiumVO().getGovtTax();
		double vatTax = travelInsuranceVO.getPremiumVO().getVatTax();
		
		/*
		 * Call rating and get the premium
		 */
		travelInsuranceVO = (TravelInsuranceVO) travelPackagePremiumSvc.invokeMethod( SvcConstants.GET_PACKAGE_PREMIUM, travelInsuranceVO );
		policyDataVO.getPremiumVO().setPolicyFees( policyFees );
		policyDataVO.getPremiumVO().setGovtTax( govtTax );
		
		policyDataVO.getPremiumVO().setVatTax( vatTax );
		policyDataVO.getPremiumVO().setVatTaxPerc( travelInsuranceVO.getPremiumVO().getVatTaxPerc() );
		
		/**
		 * Modified for mirror site.
		 */
		// To avoid empty if block (Sonar defect) 12-9-2017
		/*if(Utils.isEmpty( policyDataVO.getCommission()));
		{*/
			policyDataVO.setCommission( travelInsuranceVO.getCommission() );
		/*}*/
		TTrnPremiumVOHolder trnPremiumQuoVOHolder;
		TableData<TTrnPremiumVOHolder> premiumTableData;
		BigDecimal sumInsured;
		BigDecimal zeroVal = new BigDecimal( 0 );
		/*BigDecimal oneVal = new BigDecimal( 1 );*/

		int prmCounter = 0;

		Object[] splCodesForAddtlCovers = null;
		Object[] splCodesForBasicCovers = null;
		boolean fetchedCodesForBasicCovers = false;
		boolean fetchedCodesForAddtlCovers = false;
		Double packageLevelProratedPrm = null;
		Boolean isProrated = Boolean.FALSE;
		String[] basicCheckBoxCovers = Utils.getMultiValueAppConfig( "MANDATORY_COVERS_TRAVEL" );

		/*Sort the covers so that the premium and sum insured details can be set to the cover with least cover code.*/
		Collections.sort( covers );
		for( CoverDetailsVO coverDetailsVO : covers ){

			sumInsured = new BigDecimal( coverDetailsVO.getSumInsured().getSumInsured() );

			if( zeroVal.compareTo( sumInsured ) == -1 || SvcConstants.STATUS_ON.equalsIgnoreCase( coverDetailsVO.getIsCovered() ) ){
				// for every traveler a cover is created

				for( TableData<BaseVO> travelerTableData : gaccTableData ){

					TTrnGaccPersonVOHolder gaccPersonVOHolder = (TTrnGaccPersonVOHolder) travelerTableData.getTableData();

					premiumTableData = new TableData<TTrnPremiumVOHolder>();
					trnPremiumQuoVOHolder = new TTrnPremiumVOHolder();
					trnPremiumQuoVOHolder.setPrmClCode( policyDataVO.getPolicyClassCode().shortValue() );
					trnPremiumQuoVOHolder.setPrmPtCode( new Short( policyDataVO.getPolicyType().toString() ) );
					trnPremiumQuoVOHolder.setPrmPolicyId( policyDataVO.getPolicyId() );
					trnPremiumQuoVOHolder.setPrmEndtId( policyDataVO.getCommonVO().getEndtId() );
					trnPremiumQuoVOHolder.setPrmEffectiveDate( gaccPersonVOHolder.getGprStartDate() );
					trnPremiumQuoVOHolder.setPrmExpiryDate( policyDataVO.getScheme().getExpiryDate() );
					trnPremiumQuoVOHolder.setPrmCstCode( coverDetailsVO.getCoverCodes().getCovSubTypeCode() );
					trnPremiumQuoVOHolder.setPrmValidityStartDate( coverDetailsVO.getVsd() );

					trnPremiumQuoVOHolder.setPrmCovCode( coverDetailsVO.getCoverCodes().getCovCode() );
					trnPremiumQuoVOHolder.setPrmCtCode( coverDetailsVO.getCoverCodes().getCovTypeCode() );
					trnPremiumQuoVOHolder.setPrmRskId( new BigDecimal( gaccPersonVOHolder.getGprId() ) );
					trnPremiumQuoVOHolder.setPrmRscCode( 0 ); //TODO To confirm on risk code
					trnPremiumQuoVOHolder.setPrmRskCode( coverDetailsVO.getRiskCodes().getRiskCode() );
					trnPremiumQuoVOHolder.setPrmBasicRskCode( coverDetailsVO.getRiskCodes().getBasicRskCode() );
					trnPremiumQuoVOHolder.setPrmRiRskCode( gaccPersonVOHolder.getGprRiRskCode() );
					trnPremiumQuoVOHolder.setPrmBasicRskId( new BigDecimal( gaccPersonVOHolder.getGprId() ) );
					trnPremiumQuoVOHolder.setPrmRcCode( coverDetailsVO.getRiskCodes().getRiskCat() );
					trnPremiumQuoVOHolder.setPrmRtCode( coverDetailsVO.getRiskCodes().getRiskType() );

					coverDetailsVO.getRiskCodes().setRskId( new BigDecimal( gaccPersonVOHolder.getGprId() ) );
					coverDetailsVO.getRiskCodes().setBasicRskId( new BigDecimal( gaccPersonVOHolder.getGprId() ) );

					/*if( oneVal.compareTo( sumInsured ) != 0 ){
						trnPremiumQuoVOHolder.setPrmSumInsured( sumInsured );
					}else{
						trnPremiumQuoVOHolder.setPrmSumInsured( new BigDecimal( SvcConstants.zeroVal ) );
					}*/
					/*Set the SI value for check-boxes as whatever is fetched from configuration.*/
					if( CopyUtils.asList( basicCheckBoxCovers ).contains( String.valueOf( coverDetailsVO.getCoverCodes().getCovCode() ) ) && 
							zeroVal.compareTo( sumInsured ) == 0 ){
						sumInsured = BigDecimal.valueOf( Long.valueOf( coverDetailsVO.getSumInsured().getaDesc() ) );
					}
					trnPremiumQuoVOHolder.setPrmSumInsured( sumInsured );

					if(!Utils.isEmpty(coverDetailsVO.getSumInsured()) && !Utils.isEmpty(coverDetailsVO.getSumInsured().getDeductible())){
						trnPremiumQuoVOHolder.setPrmCompulsoryExcess(new BigDecimal(coverDetailsVO.getSumInsured().getDeductible()));
					}
					
					if( prmCounter == 0 ){ //for first cover

						TravelerDetailsVO finderPerson = new TravelerDetailsVO();
						finderPerson.setGprId( new BigDecimal( gaccPersonVOHolder.getGprId() ) );

						if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getTravelDetailsVO() )
								&& !Utils.isEmpty( travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList() ) ){

							TravelerDetailsVO insuredPerson = travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList()
									.get( travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList().indexOf( finderPerson ) );

							if( !Utils.isEmpty( insuredPerson ) && !Utils.isEmpty( insuredPerson.getPremiumAmt() ) ){
								trnPremiumQuoVOHolder.setPrmPremium( new BigDecimal( insuredPerson.getPremiumAmt() ) );
								trnPremiumQuoVOHolder.setPrmPremiumActual( new BigDecimal( insuredPerson.getPremiumAmtActual() ) );
							}
							else{
								trnPremiumQuoVOHolder.setPrmPremium( new BigDecimal( SvcConstants.zeroVal ) );
								trnPremiumQuoVOHolder.setPrmPremiumActual( new BigDecimal( SvcConstants.zeroVal ) );
							}
						}

						if( doProrate( commonVO, travelInsuranceVO ) && isPopulateOperation ){
							if( Utils.isEmpty( packageLevelProratedPrm ) ){
								packageLevelProratedPrm = 0.0;
							}
							packageLevelProratedPrm += proratePremium( travelInsuranceVO, policyDataVO, trnPremiumQuoVOHolder );
							if(loggenInLoc.equals("30"))
							{
								DecimalFormat df2 = new DecimalFormat("0.0");
								df2.setRoundingMode(RoundingMode.CEILING);
								DecimalFormat df3 = new DecimalFormat("0.000");
								packageLevelProratedPrm = new Double(df2.format(packageLevelProratedPrm)).doubleValue();
								packageLevelProratedPrm = new Double(df3.format(packageLevelProratedPrm)).doubleValue();
								packageLevelProratedPrm = Double.parseDouble(Currency.getUnformattedScaledCurrency(new BigDecimal(packageLevelProratedPrm),travelInsuranceVO.getCommonVO().getLob().name()));
							
							}
							isProrated = Boolean.TRUE;
						}
					}
					else{
						trnPremiumQuoVOHolder.setPrmPremium( new BigDecimal( SvcConstants.zeroVal ) );
						trnPremiumQuoVOHolder.setPrmPremiumActual( new BigDecimal( SvcConstants.zeroVal ) );
					}

					/*Set sitype, fnCode and rateType.*/
/*					Changes for Ticket 76367 - SI_TYPE issue
 					if( coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.oneVal ){

						if( !fetchedCodesForBasicCovers ){
							splCodesForBasicCovers = commonOpDAO.getSpecialCodes( trnPremiumQuoVOHolder );
							fetchedCodesForBasicCovers = true;
						}

						if( !Utils.isEmpty( splCodesForBasicCovers ) ){
							setValuesToPojo( trnPremiumQuoVOHolder, splCodesForBasicCovers );
						}

					}
					else{

						if( !fetchedCodesForAddtlCovers ){
							splCodesForAddtlCovers = commonOpDAO.getSpecialCodes( trnPremiumQuoVOHolder );
							fetchedCodesForAddtlCovers = true;
						}

						if( !Utils.isEmpty( splCodesForAddtlCovers ) ){
							setValuesToPojo( trnPremiumQuoVOHolder, splCodesForAddtlCovers );
						}

					}*/

					/* Changes for Ticket 76367 - SI_TYPE issue*/
					splCodesForBasicCovers = commonOpDAO.getSpecialCodes( trnPremiumQuoVOHolder );
										
					if( !Utils.isEmpty( splCodesForBasicCovers ) ){
						setValuesToPojo( trnPremiumQuoVOHolder, splCodesForBasicCovers );
					}					
					
					
					TTrnPremiumVOHolder savedRelatedRec = trnPremiumQuoVOHolder.getRelatedPrmRec( premiumList );
					if( !Utils.isEmpty( savedRelatedRec )  ){
						trnPremiumQuoVOHolder.setPrmValidityStartDate( savedRelatedRec.getPrmValidityStartDate() );
					}
					
					premiumTableData.setTableData( trnPremiumQuoVOHolder );
					premiumQuoTableDataList.add( premiumTableData );
					
				}
				prmCounter++;
			}

		}
		
		
		policyDataVO.getPremiumVO().setPromoDiscPerc( travelInsuranceVO.getSelectedPackage().getPromoDiscPerc() );

		/* Add special cover records. */
		premiumQuoTableDataList.addAll( getPremiumSplCovers( policyDataVO, splCodesForBasicCovers, splCodesForAddtlCovers ) );
		
		premiumQuoTableDataList = updateToBeDeletedPrmRecsTravel( commonVO, premiumQuoTableDataList );

		for( TableData<BaseVO> travelerTableData : gaccTableData ){

			TTrnGaccPersonVOHolder gaccPersonHolder;
			TableData<TTrnGaccPersonVOHolder> gaccHolderTableData = new TableData<TTrnGaccPersonVOHolder>();
			gaccPersonHolder = (TTrnGaccPersonVOHolder) travelerTableData.getTableData();

			for( int i = 0; i < covers.size(); i++ ){
				sumInsured = new BigDecimal( covers.get( i ).getSumInsured().getSumInsured() );

				/* Set the values for gaccPersonQuo fields to be updated during travel save. */
				if( !Utils.isEmpty( sumInsured ) && sumInsured.compareTo( BigDecimal.ZERO ) > 0 ){

					gaccPersonHolder.setGprSumInsured( sumInsured );
					gaccPersonHolder.setGprRtCode( covers.get( i ).getRiskCodes().getRiskType().longValue() );
					//gaccPersonHolder.setGprGender( null ); // TODO : set the value after confirmation as there is no UI field for this. 
					gaccHolderTableData.setTableData( gaccPersonHolder );
					gaccPersonQuoTableDataList.add( gaccHolderTableData );
					break; /* Exit the loop after setting SI from 1st cover. */

				}

			}
		}
		//populate cash customer
		TMasCashCustomerVOHolder cashCustomerQuo = (TMasCashCustomerVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder" );
		cashCustomerQuo = mapPolicyVOToTMasCashCustomer( cashCustomerQuo, policyDataVO );
		cashCustTableData.setTableData( cashCustomerQuo );
		cashCustTableDataList.add( cashCustTableData );

		policyTableData.setTableData( policyDataVO );
		policyTableDataList.add( policyTableData );
		dataMap.put( SvcConstants.T_TRN_POLICY, policyTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_MAS_CASH_CUSTOMER_QUO, cashCustTableDataList );
		dataMap.put( SvcConstants.T_TRN_GACC_PERSON, gaccPersonQuoTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, premiumQuoTableDataList );
		dataHolder.setData( dataMap );


		if( !isPopulateOperation ){
			baseSaveSvc.invokeMethod( com.Constant.CONST_BASESAVE, dataHolder, commonVO );
			travelInsuranceVO.setEndtId(commonVO.getEndtId());
			travelInsuranceVO.setPolicyId( policyDataVO.getPolicyId() );
			//set endt id after save to updated value.
			/*policyDataVO.setEndtId( commonVO.getEndtId() );
			if( commonVO.getIsQuote() ){
				LOGGER.info( "Travel Issue Quote Procedure called" );
				DAOUtils.callUpdateStatusProcedureForHomeTravel( policyDataVO );
				LOGGER.info( "Travel Issue Quote Procedure executed successfully" );
				commonVO.setStatus( SvcConstants.POL_STATUS_ACTIVE );
			}*/
		}
		
		if( isProrated && isPopulateOperation ){
			double deletedPrm = 0.0;
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPASFor( "select PKG_PAS_QUO_POL_TRAVEL.GET_CANCEL_TVL_DETAIL(" + commonVO.getPolicyId() + ","
					+ commonVO.getEndtId() + " ) from dual" );
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				deletedPrm = ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue();
			}
			if( !Utils.isEmpty( packageLevelProratedPrm ) && packageLevelProratedPrm.compareTo( 0.0 ) != 0 ){
				TravelPackageVO selectedPackage = travelInsuranceVO.getSelectedPackage();
				selectedPackage.setPremiumAmt( packageLevelProratedPrm.doubleValue() + deletedPrm );
			}else{
				if( !Utils.isEmpty( deletedPrm ) && deletedPrm != 0 ){
					TravelPackageVO selectedPackage = travelInsuranceVO.getSelectedPackage();
					selectedPackage.setPremiumAmt( deletedPrm );
				}
			}

		}

	}
	
	private boolean doProrate( CommonVO commonVO,TravelInsuranceVO travelInsuranceVO ){
		Boolean diffpolTypeChange = true;
		Boolean isShortTerm = false;
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( "select PKG_PAS_QUO_POL_TRAVEL.FN_GET_TRAVEL_EXT_TYP(" + commonVO.getPolicyId() + ","
					+ commonVO.getEndtId() + ") from dual", (HibernateTemplate) Utils.getBean( "hibernateTemplate" ) );
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				String value = (String) valueHolder.get( 0 );
				diffpolTypeChange = Boolean.valueOf( value );
			}

			isShortTerm = travelInsuranceVO.getPolicyType().equals( Integer.valueOf( SvcConstants.SHORT_TRAVEL_POL_TYPE ) );

			return diffpolTypeChange && !isShortTerm && !commonVO.getIsQuote();
	}
	

	/**
	 * @param commonVO
	 * @return
	 */
	private List<TableData> getExistingPremiumRecs( CommonVO commonVO ){
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
		loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		loadDataInputVO.setEndtId( commonVO.getEndtId() );
		loadDataInputVO.setLocCode( commonVO.getLocCode() );
		loadDataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		//loadDataInputVO.setDocCode( commonVO.getDocCode() );
		//loadDataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		//loadDataInputVO.setStatus( commonVO.getStatus() );
		
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
		@SuppressWarnings( "unchecked" )
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) 
					baseLoadSvc.invokeMethod( com.Constant.CONST_BASELOAD, loadDataInputVO, dataToLoad );
		List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );
		return premiumList;
	}

	private Double proratePremium( TravelInsuranceVO travelInsuranceVO, PolicyDataVO policyDataVO, TTrnPremiumVOHolder trnPremiumQuoVOHolder ){
		
		return getProratedPremium( travelInsuranceVO, policyDataVO, trnPremiumQuoVOHolder.getPrmCovCode(), trnPremiumQuoVOHolder.getPrmRskId(), trnPremiumQuoVOHolder.getPrmPremiumActual() );
		
	}

	private Double getProratedPremium( TravelInsuranceVO travelInsuranceVO, PolicyDataVO policyDataVO, short coverCode, BigDecimal rskId, BigDecimal currentPremium ){
		CommonVO commonVO = travelInsuranceVO.getCommonVO();
		List<Object[]> previousData = null;
		Double proratedPremium = null;
		BigDecimal previousPremium = BigDecimal.ZERO;
		BigDecimal prevAnnualPrm = BigDecimal.ZERO;

		Date polStartDate = policyDataVO.getScheme().getEffDate();
		Long newPolExpiryDays = 0L;
		Long oldPolExpiryDays = 0L;
		
		Date prevStartDate = polStartDate;
		Date prevEndDate = policyDataVO.getScheme().getExpiryDate();

		if( !Utils.isEmpty( policyDataVO.getEndEffectiveDate() ) ){
			newPolExpiryDays = PremiumHelper.getDifference( policyDataVO.getPolExpiryDate(), policyDataVO.getEndEffectiveDate() );
		}
		else{
			newPolExpiryDays = PremiumHelper.getDifference( policyDataVO.getScheme().getExpiryDate(), polStartDate );
		}
		
		List<Object[]>  result = DAOUtils.getSqlResultForPas (  QueryConstants.POLICY_EXT,commonVO.getPolicyId(),
				commonVO.getEndtId());

		int policyExtDays = 0;
		if(!Utils.isEmpty( result ) && result.size() > 0 ){
			policyExtDays = ( Integer.valueOf( result.get( 0 )[0].toString() ) ).intValue() ;
			policyExtDays = policyExtDays < 0 ? policyExtDays * -1 : policyExtDays;
			/*if(policyExtDays > 0){
				policyExtDays=policyExtDays+1;
			}*/
		}
		
		//Assuming policy extension is not done
		oldPolExpiryDays = newPolExpiryDays;
		
		if(policyExtDays>0){
			oldPolExpiryDays = oldPolExpiryDays - policyExtDays;
		}

		if( !Utils.isEmpty( coverCode ) && !Utils.isEmpty( rskId ) ){
			previousData = DAOUtils.getPreviousDataTravel( commonVO.getPolicyId(), commonVO.getEndtId(), coverCode, rskId );
		}

		if( !Utils.isEmpty( previousData ) && !Utils.isEmpty( previousData.get( 0 ) ) ){
			
			BigDecimal prevPremium = (BigDecimal) previousData.get( 0 )[ 0 ];
			
			if(!Utils.isEmpty( prevPremium )){
				previousPremium = prevPremium;
				prevAnnualPrm = (BigDecimal) previousData.get( 0 )[ 2 ];
			}
			
			//While re adding the deleted covers, pro rated premium should be calculated without considering previous premium
			BigDecimal status = (BigDecimal) previousData.get( 0 )[ 3 ];
			if(status.equals( BigDecimal.valueOf( 4 ) )){
				prevPremium = BigDecimal.ZERO;
				prevAnnualPrm = BigDecimal.ZERO;
				previousPremium = BigDecimal.ZERO;
			}
			prevStartDate = (Date) previousData.get( 0 )[ 1 ];
			prevEndDate = (Date) previousData.get( 0 )[ 4];
		}
		
		long newPolicyDays = PremiumHelper.getDifference( policyDataVO.getScheme().getExpiryDate(), polStartDate );
		long oldPolicyDays = PremiumHelper.getDifference( prevEndDate, prevStartDate );
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String prevStartDateStr = sdf.format(prevStartDate);
		String polStartDateStr = sdf.format(polStartDate);
		String prevEndDateStr = sdf.format(prevEndDate);
		String polEndDateStr = sdf.format( policyDataVO.getScheme().getExpiryDate() );
		
		//Resetting the old and new policy expiry days if the travel date is changed and the travel period still remains the same
		if( ( !prevStartDateStr.equals( polStartDateStr ) || !prevEndDateStr.equals( polEndDateStr ) ) && ( newPolicyDays == oldPolicyDays ) ){
			oldPolExpiryDays = oldPolicyDays;
			newPolExpiryDays = oldPolExpiryDays;
		}
		proratedPremium = PremiumHelper.getProratedPrm( oldPolExpiryDays, newPolExpiryDays, previousPremium, prevAnnualPrm ,currentPremium, polStartDate ).doubleValue();
		proratedPremium = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( proratedPremium ), commonVO.getLob().name() ) );
		return proratedPremium;
	}

	private void setValuesToPojo( TTrnPremiumVOHolder trnPremiumQuoVOHolder, Object[] splCodes ){

		if( !Utils.isEmpty( splCodes[ 0 ] ) ) trnPremiumQuoVOHolder.setPrmSitypeCode( ( (BigDecimal) splCodes[ 0 ] ).byteValue() );
		if( !Utils.isEmpty( splCodes[ 1 ] ) ) trnPremiumQuoVOHolder.setPrmFnCode( ( (BigDecimal) splCodes[ 1 ] ).byteValue() );
		if( !Utils.isEmpty( splCodes[ 2 ] ) ) trnPremiumQuoVOHolder.setPrmRateType( ( (BigDecimal) splCodes[ 2 ] ).byteValue() );

	}

	/**
	 * @param commonVO
	 * @param splCodesForBasicCovers 
	 * @param splCodesForAddtlCovers 
	 * @return
	 */
	public List<TableData> getPremiumSplCovers( PolicyDataVO policyDataVo, Object[] splCodesForBasicCovers, Object[] splCodesForAddtlCovers ){

		List<TableData> premSplCoverRecs = new ArrayList<TableData>();
		TableData<TTrnPremiumVOHolder> premSplCovTableData = null;
		
		

		
		TTrnPremiumVOHolder basePrmHolder = commonOpDAO.getPremiumSpecialCoverRecs( policyDataVo.getCommonVO() );

		/* Changes for Ticket 76367 - SI_TYPE issue
		if( !Utils.isEmpty( splCodesForBasicCovers ) ){
			setValuesToPojo( basePrmHolder, splCodesForBasicCovers );
		}else if( !Utils.isEmpty( splCodesForAddtlCovers )){
			setValuesToPojo( basePrmHolder, splCodesForAddtlCovers );				
		}*/
		/*
		 * Validation to check if the loading/discount is changed by lower license level.
		 * 
		 */
		
		validateUserAuthForDiscLoad(policyDataVo);
		
		for( String coverCode : getSpecialCovers() ){
			
			TTrnPremiumVOHolder coverToSave =  CopyUtils.copySerializableObject( basePrmHolder ) ;
			
			//If coverCode - 51 ==>  discount, if coverCode - 20 ==> load, if coverCode - 100 ==> policyFees, if coverCode - 101 ==> govtTax 151 ==> vatTax 
			if(Short.valueOf( coverCode ).equals( SvcConstants.SC_PRM_COVER_DISCOUNT ) && (policyDataVo.getPremiumVO().getDiscOrLoadPerc() < 0 )){
				coverToSave.setPrmLoadDisc( new BigDecimal(policyDataVo.getPremiumVO().getDiscOrLoadPerc().toString()) );
			}
			if(Short.valueOf( coverCode ).equals( SvcConstants.SC_PRM_COVER_LOADING ) && (policyDataVo.getPremiumVO().getDiscOrLoadPerc() >= 0 ) ){
				coverToSave.setPrmLoadDisc( new BigDecimal(policyDataVo.getPremiumVO().getDiscOrLoadPerc().toString()) );
			}
			if( coverCode.equals( SvcConstants.SC_PRM_COVER_PROMO_DISC ) && !Utils.isEmpty( policyDataVo.getPremiumVO().getPromoDiscPerc() ) ){
				coverToSave.setPrmLoadDisc( new BigDecimal( policyDataVo.getPremiumVO().getPromoDiscPerc() ) );
			}
			// Set the Policy Fees Premium
			if( Short.valueOf( coverCode ).equals( SvcConstants.SC_PRM_COVER_POLICY_FEE ) && !Utils.isEmpty( policyDataVo.getPremiumVO().getPolicyFees() ) ){
				coverToSave.setPrmPremium( new BigDecimal( policyDataVo.getPremiumVO().getPolicyFees() ) );
			}
			// Set the Govt Tax Premium
			if( Short.valueOf( coverCode ).equals( SvcConstants.SC_PRM_COVER_GOVT_TAX ) && !Utils.isEmpty( policyDataVo.getPremiumVO().getGovtTax() ) ){
				coverToSave.setPrmPremium( new BigDecimal( policyDataVo.getPremiumVO().getGovtTax() ) );
			}
			//142244 Vat
			if( Short.valueOf( coverCode ).equals( SvcConstants.SC_PRM_COVER_VAT_TAX ) && !Utils.isEmpty( policyDataVo.getPremiumVO().getVatTax() ) ){
				coverToSave.setPrmPremium( new BigDecimal( policyDataVo.getPremiumVO().getVatTax() ) );
				//coverToSave.setPrmLoadDisc( new BigDecimal( policyDataVo.getPremiumVO().getVatTaxPerc() ) );
				coverToSave.setPrmRate(new BigDecimal(policyDataVo.getPremiumVO().getVatTaxPerc()));
			}
			
			coverToSave.setPrmCovCode( Short.valueOf( coverCode ) );
			premSplCovTableData = new TableData<TTrnPremiumVOHolder>();
			premSplCovTableData.setTableData( coverToSave );
			premSplCoverRecs.add( premSplCovTableData );
		}
		premSplCoverRecs = checkSpecialCovers(premSplCoverRecs);
		return premSplCoverRecs;
	}

	/*
	 * Ticket 154656 :: Method used to check if coverCode has 151 from appConfig , isVatEnabled
	 * do not add/insert to the list premSplCovTableData,premSplCoverRecs
	 * @param premSplCoverRecs - get list of covers
	 * @param policyDataVo
	 */
	public List<TableData> checkSpecialCovers(List<TableData> premSplCoverRecs) {
		LOGGER.info("Entering checkSpecialCovers() : PremiumSaveCommonSvc ");
		boolean coverExists = false;
		Boolean vatEnabledFlag = DAOUtils.isVatEnabled();
		//Boolean vatApplicableFlag = DAOUtils.isVatApplicable();
        for(String data : getSpecialCovers()){
        	if(Short.valueOf( data ).equals( SvcConstants.SC_PRM_COVER_VAT_TAX )){
        		coverExists = true;
        	}
        }
        Iterator<TableData> iterator = premSplCoverRecs.iterator();
        while(iterator.hasNext()){
			TTrnPremiumVOHolder premiumVOHolder = (TTrnPremiumVOHolder) iterator.next().getTableData();
			/*if coverCode has 151 from appConfig , isVatEnabled : 0(true), 
			do not add/insert to the list premSplCovTableData,premSplCoverRecs*/		
			if((premiumVOHolder.getPrmCovCode() == 151) && vatEnabledFlag.equals(Boolean.TRUE) && (coverExists==true)){
				LOGGER.debug("Removed 151 from the list premSplCoverRecs");
				iterator.remove();
			}
        }
        LOGGER.debug("After removal " +iterator.toString());
		return premSplCoverRecs;
	}
	private void validateUserAuthForDiscLoad( PolicyDataVO policyDataVo ){
		/*
		 * get the previous record for loading
		 */
		boolean validationPassed = true;
		Long endtId = policyDataVo.getCommonVO().getEndtId();
		/*
		 * if the endt is 0 then the query to fetch the previous erc will not work. Hence for endt 0 we are setting temp endt id to 1 to get the curr rec
		 */
		if(endtId.compareTo( 0L ) == 0){
			endtId = 1L;
		}
		List<Object[]> resultSet = DAOUtils.getSqlResult( policyDataVo.getCommonVO().getIsQuote() ? QueryConstants.GET_COVER_PRM_RATE_QUOTE
				: QueryConstants.GET_COVER_PRM_RATE_POLICY, policyDataVo.getCommonVO().getPolicyId(), endtId, SvcConstants.SC_PRM_COVER_LOADING,
				SvcConstants.SC_PRM_COVER_DISCOUNT );

		if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
			BigDecimal discLoadToCompare = null;
			for( Object[] result : resultSet ){
				Integer modifiedUser = !Utils.isEmpty( result[ 2 ] ) ? (  (BigDecimal) result[ 2 ] ).intValue() : ( (BigDecimal) result[ 3 ] ).intValue();
				Integer currentUser = SvcUtils.getUserId( policyDataVo.getCommonVO() );

				List<String> modifiedUserRoles = (List<String>) (List<?>) DAOUtils.getSqlResultSingleColumnPASFor( QueryConstants.GET_USER_ROLE, modifiedUser );
				List<String> currentUserRoles = (List<String>) (List<?>) DAOUtils.getSqlResultSingleColumnPASFor( QueryConstants.GET_USER_ROLE, currentUser );

				Integer modifiedUserRank = 0;
				
				if (SvcUtils.getHighestRole(policyDataVo.getCommonVO().getLob().toString(),
						CopyUtils.toArray(modifiedUserRoles)) != null) {
					modifiedUserRank = Integer.valueOf(Utils.getSingleValueAppConfig(SvcUtils.getHighestRole(
							policyDataVo.getCommonVO().getLob().toString(), CopyUtils.toArray(modifiedUserRoles))));
				}
						
				Integer currentUserRank = 0;

				if (SvcUtils.getHighestRole(policyDataVo.getCommonVO().getLob().toString(),
						CopyUtils.toArray(currentUserRoles)) != null) {
					currentUserRank = Integer.valueOf(Utils.getSingleValueAppConfig(SvcUtils.getHighestRole(
							policyDataVo.getCommonVO().getLob().toString(), CopyUtils.toArray(currentUserRoles))));
				}
				
				BigDecimal discLoad = (BigDecimal) result[ 1 ];
				if( !Utils.isEmpty( discLoadToCompare ) && discLoad.compareTo( discLoadToCompare ) == 0 ){
					discLoadToCompare = (BigDecimal) result[ 1 ];
					BigDecimal currentDiscLoad = new BigDecimal( policyDataVo.getPremiumVO().getDiscOrLoadPerc() );
					// Added by Anveshan: To compare big decimals after they are rounded off
					discLoad = SvcUtils.premiumRoundOff(discLoad);
					currentDiscLoad = SvcUtils.premiumRoundOff(currentDiscLoad);
					
					if( discLoad.compareTo( currentDiscLoad ) > 0 && ( modifiedUserRank.compareTo( currentUserRank ) < 0 ) ){
						validationPassed = false;
						break;
					}
				}
				else if( !Utils.isEmpty( discLoadToCompare ) && discLoad.compareTo( discLoadToCompare ) != 0 ){
					break;
				}

				if( Utils.isEmpty( discLoadToCompare ) && ( modifiedUserRank.compareTo( currentUserRank ) < 0 ) ){
					discLoadToCompare = (BigDecimal) result[ 1 ];
					BigDecimal currentDiscLoad = BigDecimal.valueOf( policyDataVo.getPremiumVO().getDiscOrLoadPerc() );
					//Added new local variables with the scaled values as comparison was failing because of mismatch in scale. 
					BigDecimal currDiscLoadForComparison = currentDiscLoad.setScale( 3, RoundingMode.UP );
					BigDecimal discountLoadForComparison = discLoad.setScale( 3, RoundingMode.UP );
					if( discountLoadForComparison.compareTo( currDiscLoadForComparison ) > 0 ){
						validationPassed = false;
						break;

					}
				}
				discLoadToCompare = (BigDecimal) result[ 1 ];
			}
		}
/*			
			Integer modifiedUser = !Utils.isEmpty( resultSet.get( 0 )[ 2 ] ) ? (Integer) resultSet.get( 0 )[ 2 ] : ( (BigDecimal) resultSet.get( 0 )[ 3 ] ).intValue();
			Integer currentUser = SvcUtils.getUserId( policyDataVo.getCommonVO() );

			List<String> modifiedUserRoles = (List<String>) (List<?>) DAOUtils.getSqlResultSingleColumnPAS( QueryConstants.GET_USER_ROLE, modifiedUser );
			List<String> currentUserRoles = (List<String>) (List<?>) DAOUtils.getSqlResultSingleColumnPAS( QueryConstants.GET_USER_ROLE, currentUser );

			Integer modifiedUserRank = Integer.valueOf( Utils.getSingleValueAppConfig( SvcUtils.getHighestRole( policyDataVo.getCommonVO().getLob().toString(),
					CopyUtils.toArray( modifiedUserRoles ) ) ) );
			Integer currentUserRank = Integer.valueOf( Utils.getSingleValueAppConfig( SvcUtils.getHighestRole( policyDataVo.getCommonVO().getLob().toString(),
					CopyUtils.toArray( currentUserRoles ) ) ) );
			if( modifiedUserRank.compareTo( currentUserRank ) < 0 ){
				BigDecimal discLoad = (BigDecimal) resultSet.get( 0 )[ 1 ];
				BigDecimal currentDiscLoad = new BigDecimal( policyDataVo.getPremiumVO().getDiscOrLoadPerc() );
				if( discLoad.compareTo( currentDiscLoad ) > 0 ){
					validationPassed = false;
				}
			}
		}*/

		if( !validationPassed ){
			throw new BusinessException( "err.discload.auth", null, "Disc/Loading was reduced by lower auth user" );
		}
	}

	private List<String> getSpecialCovers(){
		if( SPECIAL_COVER_CODES == null ){
			// SPECIAL_COVER_CODES is used by SBS, hence the configaration not chnaged
			SPECIAL_COVER_CODES = CopyUtils.asList( Utils.getMultiValueAppConfig( "SPECIAL_COVER_CODES", "," ) );
			/*
			 * This configuration is used for ph3 as SBS currrenty does not have promo discount
			 */
			SPECIAL_COVER_CODES.add(  SvcConstants.SC_PRM_COVER_PROMO_DISC  );
			SPECIAL_COVER_CODES.add( SvcConstants.SPECIAL_COVER_MIN_PRM  );
		}
		return SPECIAL_COVER_CODES;
	}

	/**
	 * This method saves the home insurance related premium details (Call to BaseSaveOpertaion)
	 * 
	 * @param homeInsuranceVO
	 * @param policyDataVO
	 */
	@SuppressWarnings( "rawtypes" )
	private void saveHomePremiumDets( HomeInsuranceVO homeInsuranceVO, PolicyDataVO policyDataVO ){

		boolean fetchedCodesForBasicCovers = false;
		boolean fetchedCodesForAddtlCovers = false;
		Object[] splCodesForBasicCovers = null;
		Object[] splCodesForAddtlCovers = null;
		int cntRcCode = 0;
		Integer cntRiRskCode = null;
		BigDecimal compExcess = null;
		boolean buildingSelected = false;
		boolean contentSelected = false;
		boolean personalPosSelected = false;
		

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		TableData<TMasCashCustomerVOHolder> cashCustTableData = new TableData<TMasCashCustomerVOHolder>();
		TableData<TTrnPremiumVOHolder> premiumTableData = new TableData<TTrnPremiumVOHolder>();
		TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();

		List<TableData> cashCustTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> premiumTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );
		TTrnPremiumVOHolder premiumVOHolder = new TTrnPremiumVOHolder();

		if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) &&
			!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured() ) &&
				!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() ) &&
					homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() > 0 ){
			buildingSelected=true;
			premiumVOHolder = BeanMapper.map( homeInsuranceVO.getBuildingDetails(), TTrnPremiumVOHolder.class );
			if( !Utils.isEmpty( policyDataVO.getPolicyId() ) ){
				premiumVOHolder.setPrmPolicyId( policyDataVO.getPolicyId() );
			}
			premiumVOHolder.setPrmClCode( policyDataVO.getPolicyClassCode().shortValue() );
			premiumVOHolder.setPrmPtCode( new Short( policyDataVO.getPolicyType().toString() ) );
			
			compExcess = getPrmCompulsoryExcess(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskType(),policyDataVO.getScheme().getTariffCode());
		
			premiumVOHolder.setPrmCompulsoryExcess( compExcess);
			premiumTableData.setTableData( premiumVOHolder );
			premiumVOHolder.setPrmEffectiveDate( policyDataVO.getScheme().getEffDate() );
			premiumVOHolder.setPrmExpiryDate( policyDataVO.getScheme().getExpiryDate() );

			/*Set sitype, fnCode and rateType to building record.*/
			if( !fetchedCodesForBasicCovers ){
				splCodesForBasicCovers = commonOpDAO.getSpecialCodes( premiumVOHolder );
				fetchedCodesForBasicCovers = true;
			}

			if( !Utils.isEmpty( splCodesForBasicCovers ) ){
				setValuesToPojo( premiumVOHolder, splCodesForBasicCovers );
			}
			
		//	premiumVOHolder.setPrmRiRskCode(  homeInsuranceVO.getBuildingDetails().getRiRskCode() );
			premiumTableDataList.add( premiumTableData );
		}
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
			premiumVOHolder = new TTrnPremiumVOHolder();
			premiumVOHolder = mapCovDetsVOToTTrnPrmHldr( coverDetailsVO, premiumVOHolder );
			premiumTableData = new TableData<TTrnPremiumVOHolder>();
			if( !Utils.isEmpty( policyDataVO.getPolicyId() ) ){
				premiumVOHolder.setPrmPolicyId( policyDataVO.getPolicyId() );
			}
			
			String[] addtlCovers = Utils.getMultiValueAppConfig( "HOME_ADDTL_COVERS" );
			if( !Utils.isEmpty( cntRiRskCode ) && !Utils.isEmpty( addtlCovers ) && CopyUtils.asList( addtlCovers ).contains( Short.valueOf( premiumVOHolder.getPrmCovCode() ).toString() ) ){
//				premiumVOHolder.setPrmRcCode( cntRcCode );		TODO : As per DB comment it was done. But is wrong. Needs to be corrected  
				premiumVOHolder.setPrmRiRskCode( cntRiRskCode );
			}
			premiumVOHolder.setPrmEffectiveDate( policyDataVO.getScheme().getEffDate() );
			premiumVOHolder.setPrmExpiryDate( policyDataVO.getScheme().getExpiryDate() );
			premiumVOHolder.setPrmClCode( policyDataVO.getPolicyClassCode().shortValue() );
			premiumVOHolder.setPrmPtCode( new Short( policyDataVO.getPolicyType().toString() ) );
			premiumVOHolder.setPrmValidityStartDate( coverDetailsVO.getVsd() );
			
			/*Set sitype, fnCode and rateType.*/
/*			Changes for Ticket 76367 - SI_TYPE issue
			if( coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.oneVal ){

				if( !fetchedCodesForBasicCovers ){
					splCodesForBasicCovers = commonOpDAO.getSpecialCodes( premiumVOHolder );
					fetchedCodesForBasicCovers = true;
				}

				if( !Utils.isEmpty( splCodesForBasicCovers ) ){
					setValuesToPojo( premiumVOHolder, splCodesForBasicCovers );
				}

			}
			else{

				if( !fetchedCodesForAddtlCovers ){
					splCodesForAddtlCovers = commonOpDAO.getSpecialCodes( premiumVOHolder );
					fetchedCodesForAddtlCovers = true;
				}

				if( !Utils.isEmpty( splCodesForAddtlCovers ) ){
					setValuesToPojo( premiumVOHolder, splCodesForAddtlCovers );
				}

			}*/
			
			/* Changes for Ticket 76367 - SI_TYPE issue*/
			splCodesForBasicCovers = commonOpDAO.getSpecialCodes( premiumVOHolder );

			if( !Utils.isEmpty( splCodesForBasicCovers ) ){
				setValuesToPojo( premiumVOHolder, splCodesForBasicCovers );
			}
			
			/*
			 * Below code added for storing the premium compulsary excess
			 */
			if( ( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) || coverDetailsVO.getRiskCodes().getRiskType()
					.equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE ) )
					&& coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.DEFAULT_COVER_RSK_CAT ) 
					&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE ){
				compExcess = getPrmCompulsoryExcess( coverDetailsVO.getRiskCodes().getRiskType(), policyDataVO.getScheme().getTariffCode() ) ;
				
				if( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) ){
					contentSelected = true;
				}
				else if( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE) ){
					personalPosSelected = true;
				}
				
				
				premiumVOHolder.setPrmCompulsoryExcess( compExcess );
			}

			/*Set prmRcCode and prmRiRskCode for employee liability.*/
			if( premiumVOHolder.getPrmCovCode() == SvcConstants.oneVal && premiumVOHolder.getPrmRcCode() == SvcConstants.oneVal 
					&& premiumVOHolder.getPrmRtCode() == SvcConstants.RT_CODE_HOME_CONTENTS ){
				cntRcCode = premiumVOHolder.getPrmRcCode();
				cntRiRskCode = premiumVOHolder.getPrmRiRskCode();
			}
			if( Utils.isEmpty( premiumVOHolder.getPrmSumInsured() ) ) premiumVOHolder.setPrmSumInsured( new BigDecimal( SvcConstants.zeroVal ) );
		//	premiumVOHolder.setPrmRiRskCode( coverDetailsVO.getRiRskCode() );

			premiumTableData.setTableData( premiumVOHolder );
			premiumTableDataList.add( premiumTableData );
		}
		// Prepare special cover records
		premiumTableDataList.addAll( getPremiumSplCovers( homeInsuranceVO, splCodesForBasicCovers, splCodesForAddtlCovers ) );

		premiumTableDataList = updateToBeDeletedPrmRecs( homeInsuranceVO.getCommonVO(), premiumTableDataList );
		
		for(TableData<TTrnPremiumVOHolder> premiumTable  : premiumTableDataList){
			premiumTable.getTableData().setPrmRiRskCode(   homeInsuranceVO.getBuildingDetails().getRiRskCode() );
		}
		// CTS 25.08.2020 Home Rewamp 6.1 - Start
		/*BigDecimal prmRskId = null;
		BigDecimal prmBasicRskId = null;
		byte fnCode = 0, siTypeCode = 0;
		Long quotationNumber = homeInsuranceVO.getCommonVO().getQuoteNo();
		String cutoffDate = SvcUtils.populateAEDDt();
		String populatePolEDt = SvcUtils.populatePolEDt(quotationNumber);
		int ownershiptstatus = homeInsuranceVO.getBuildingDetails().getOwnershipStatus();

		SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
		Date cutoffDates = null, polPrepareDates = null;

		try {
			cutoffDates = ft.parse(cutoffDate);
			polPrepareDates = ft.parse(populatePolEDt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String flagCutoffDate = "lessThan";
		if (cutoffDates.compareTo(polPrepareDates) > 0) {
			flagCutoffDate = "lessThan";
		} else if (cutoffDates.compareTo(polPrepareDates) < 0) {
			flagCutoffDate = "greaterThan";
		} else if (polPrepareDates.compareTo(cutoffDates) == 0) {
			flagCutoffDate = "greaterThan";
		}

		if (("20".equals(Utils.getSingleValueAppConfig("DEPLOYED_LOCATION"))
				|| "21".equals(Utils.getSingleValueAppConfig("DEPLOYED_LOCATION")))) {
			if (policyDataVO.getCommonVO().getLob().toString().equals("HOME")) {

				if (ownershiptstatus == 1) {
					for (TableData<TTrnPremiumVOHolder> premiumTable : premiumTableDataList) {
						if ("greaterThan".equals(flagCutoffDate)) {
							if (premiumTable.getTableData().getPrmBasicRskCode() == 1
									&& premiumTable.getTableData().getPrmRskCode() == 1
									&& premiumTable.getTableData().getPrmCovCode() == 1
									&& !Utils.isEmpty(premiumTable.getTableData().getPrmRskId())) {
								prmRskId = premiumTable.getTableData().getPrmRskId();
								prmBasicRskId = premiumTable.getTableData().getPrmBasicRskId();
								fnCode = premiumTable.getTableData().getPrmSitypeCode();
								siTypeCode = premiumTable.getTableData().getPrmFnCode();
							}
							if (premiumTable.getTableData().getPrmBasicRskCode() == 1
									&& premiumTable.getTableData().getPrmRskCode() == 1
									&& premiumTable.getTableData().getPrmCovCode() == 6
									&& prmBasicRskId.equals(premiumTable.getTableData().getPrmBasicRskId())
									&& Utils.isEmpty(premiumTable.getTableData().getPrmRskId())) {
								premiumTable.getTableData().setPrmRskId(prmRskId);
								premiumTable.getTableData().setPrmSitypeCode(fnCode);
								premiumTable.getTableData().setPrmFnCode(siTypeCode);
							}
							if (premiumTable.getTableData().getPrmBasicRskCode() == 1
									&& premiumTable.getTableData().getPrmRskCode() == 1
									&& premiumTable.getTableData().getPrmCovCode() == 6
									&& prmBasicRskId.equals(premiumTable.getTableData().getPrmBasicRskId())
									&& !Utils.isEmpty(premiumTable.getTableData().getPrmRskId())
									&& !premiumTable.getTableData().getPrmRskId().equals(prmRskId)) {
								premiumTable.getTableData().setPrmRskId(premiumTable.getTableData().getPrmBasicRskId());
								premiumTable.getTableData().setPrmSitypeCode(fnCode);
								premiumTable.getTableData().setPrmFnCode(siTypeCode);
							}

							if (premiumTable.getTableData().getPrmBasicRskCode() == 1
									&& premiumTable.getTableData().getPrmRskCode() == 2
									&& premiumTable.getTableData().getPrmCovCode() == 3) {
								fnCode = premiumTable.getTableData().getPrmSitypeCode();
								siTypeCode = premiumTable.getTableData().getPrmFnCode();
							}
							if (premiumTable.getTableData().getPrmBasicRskCode() == 1
									&& premiumTable.getTableData().getPrmRskCode() == 2
									&& (premiumTable.getTableData().getPrmCovCode() == 5
											|| premiumTable.getTableData().getPrmCovCode() == 7
											|| premiumTable.getTableData().getPrmCovCode() == 8
											|| premiumTable.getTableData().getPrmCovCode() == 9)) {

								premiumTable.getTableData().setPrmSitypeCode(fnCode);
								premiumTable.getTableData().setPrmFnCode(siTypeCode);
							}

						}
					}
				}
				if (ownershiptstatus == 2) {
					byte fncodeContent = 0, siTypeCodeContent = 0;
					for (TableData<TTrnPremiumVOHolder> premiumTable : premiumTableDataList) {
						if ("greaterThan".equals(flagCutoffDate)) {
							if (premiumTable.getTableData().getPrmBasicRskCode() == 1
									&& premiumTable.getTableData().getPrmRskCode() == 2
									&& premiumTable.getTableData().getPrmCovCode() == 3) {
								fnCode = premiumTable.getTableData().getPrmSitypeCode();
								siTypeCode = premiumTable.getTableData().getPrmFnCode();
							}
							if (premiumTable.getTableData().getPrmBasicRskCode() == 1
									&& premiumTable.getTableData().getPrmRskCode() == 2
									&& (premiumTable.getTableData().getPrmCovCode() == 5
											|| premiumTable.getTableData().getPrmCovCode() == 7
											|| premiumTable.getTableData().getPrmCovCode() == 8
											|| premiumTable.getTableData().getPrmCovCode() == 9)) {

								premiumTable.getTableData().setPrmSitypeCode(fnCode);
								premiumTable.getTableData().setPrmFnCode(siTypeCode);
							}
						}
					}
				}

				int staffDetSize = homeInsuranceVO.getStaffDetails().size();
				if ("greaterThan".equals(flagCutoffDate)) {
					if (staffDetSize == 0 && ownershiptstatus == 1) {
						for (Iterator<TableData> removeCovers = premiumTableDataList.iterator(); removeCovers
								.hasNext();) {
							TableData<TTrnPremiumVOHolder> object = removeCovers.next();
							if (object.getTableData().getPrmBasicRskCode() == 1
									&& object.getTableData().getPrmRskCode() == 2
									&& (object.getTableData().getPrmCovCode() == 5
											|| object.getTableData().getPrmCovCode() == 7
											|| object.getTableData().getPrmCovCode() == 8
											|| object.getTableData().getPrmCovCode() == 9)) {
								removeCovers.remove();
							}
						}
					}
					if (staffDetSize == 0 && ownershiptstatus == 2) {
						for (Iterator<TableData> removeStaffCovers = premiumTableDataList.iterator(); removeStaffCovers
								.hasNext();) {
							TableData<TTrnPremiumVOHolder> object = removeStaffCovers.next();
							if (object.getTableData().getPrmBasicRskCode() == 1
									&& object.getTableData().getPrmRskCode() == 2
									&& (object.getTableData().getPrmCovCode() == 5
											|| object.getTableData().getPrmCovCode() == 7
											|| object.getTableData().getPrmCovCode() == 8
											|| object.getTableData().getPrmCovCode() == 9)) {
								removeStaffCovers.remove();
							}
						}
					}
				}

				String appFlow = homeInsuranceVO.getCommonVO().getAppFlow().toString();

				if ("greaterThan".equals(flagCutoffDate) && appFlow.equals("AMEND_POL") && ownershiptstatus == 2) {
					if (staffDetSize == 0) {
						for (Iterator<TableData> removeCovers = premiumTableDataList.iterator(); removeCovers
								.hasNext();) {
							TableData<TTrnPremiumVOHolder> object = removeCovers.next();
							if (object.getTableData().getPrmBasicRskCode() == 1
									&& object.getTableData().getPrmRskCode() == 2
									&& (object.getTableData().getPrmCovCode() == 5
											|| object.getTableData().getPrmCovCode() == 7
											|| object.getTableData().getPrmCovCode() == 8
											|| object.getTableData().getPrmCovCode() == 9)) {
								removeCovers.remove();
							}
						}
					}
				}

				if ("greaterThan".equals(flagCutoffDate) && appFlow.equals("AMEND_POL") && ownershiptstatus == 1) {
					int count = 0;
					for (Iterator<TableData> removeDup = premiumTableDataList.iterator(); removeDup.hasNext();) {
						TableData<TTrnPremiumVOHolder> object = removeDup.next();
						if (object.getTableData().getPrmBasicRskCode() == 1
								&& object.getTableData().getPrmRskCode() == 1
								&& object.getTableData().getPrmCovCode() == 6) {

							if (count == 1)
								removeDup.remove();

							count++;
						}
						if (staffDetSize == 0) {
							if (object.getTableData().getPrmBasicRskCode() == 1
									&& object.getTableData().getPrmRskCode() == 2
									&& (object.getTableData().getPrmCovCode() == 5
											|| object.getTableData().getPrmCovCode() == 7
											|| object.getTableData().getPrmCovCode() == 8
											|| object.getTableData().getPrmCovCode() == 9)) {
								removeDup.remove();
							}
						}
					}
				}

				if ("greaterThan".equals(flagCutoffDate) && appFlow.equals("EDIT_QUO") && ownershiptstatus == 1) {
					int count = 0;
					for (Iterator<TableData> removeDup = premiumTableDataList.iterator(); removeDup.hasNext();) {
						TableData<TTrnPremiumVOHolder> object = removeDup.next();
						if (object.getTableData().getPrmBasicRskCode() == 1
								&& object.getTableData().getPrmRskCode() == 1
								&& object.getTableData().getPrmCovCode() == 6) {

							if (count == 1)
								removeDup.remove();

							count++;
						}
						if (staffDetSize == 0) {
							if (object.getTableData().getPrmBasicRskCode() == 1
									&& object.getTableData().getPrmRskCode() == 2
									&& (object.getTableData().getPrmCovCode() == 5
											|| object.getTableData().getPrmCovCode() == 7
											|| object.getTableData().getPrmCovCode() == 8
											|| object.getTableData().getPrmCovCode() == 9)) {
								removeDup.remove();
							}
						}
					}
				}

				if ("greaterThan".equals(flagCutoffDate) && appFlow.equals("EDIT_QUO") && ownershiptstatus == 2) {
					if (staffDetSize == 0) {
						for (Iterator<TableData> removeCovers = premiumTableDataList.iterator(); removeCovers
								.hasNext();) {
							TableData<TTrnPremiumVOHolder> object = removeCovers.next();
							if (object.getTableData().getPrmBasicRskCode() == 1
									&& object.getTableData().getPrmRskCode() == 2
									&& (object.getTableData().getPrmCovCode() == 5
											|| object.getTableData().getPrmCovCode() == 7
											|| object.getTableData().getPrmCovCode() == 8
											|| object.getTableData().getPrmCovCode() == 9)) {
								removeCovers.remove();
							}
						}
					}
				}

				if ("lessThan".equals(flagCutoffDate)) {
					for (Iterator<TableData> iterator = premiumTableDataList.iterator(); iterator.hasNext();) {
						TableData<TTrnPremiumVOHolder> object = iterator.next();
						if (object.getTableData().getPrmBasicRskCode() == 1
								&& object.getTableData().getPrmRskCode() == 1
								&& object.getTableData().getPrmCovCode() == 6) {

							iterator.remove();

						}
					}
				}

				if ("lessThan".equals(flagCutoffDate)) {
					for (Iterator<TableData> removeContent = premiumTableDataList.iterator(); removeContent
							.hasNext();) {
						TableData<TTrnPremiumVOHolder> object = removeContent.next();
						if (object.getTableData().getPrmBasicRskCode() == 1
								&& object.getTableData().getPrmRskCode() == 2
								&& (object.getTableData().getPrmCovCode() == 5
										|| object.getTableData().getPrmCovCode() == 7
										|| object.getTableData().getPrmCovCode() == 8
										|| object.getTableData().getPrmCovCode() == 9)) {

							removeContent.remove();

						}
					}
				}
			}
		}*/
		// CTS 25.08.2020 Home Rewamp 6.1 - End

		/*
		 * Setting Policy Fees and Govt Tax from homeInsuranceVO
		 */
		if(!Utils.isEmpty( policyDataVO.getPremiumVO() )){
			policyDataVO.getPremiumVO().setGovtTax( homeInsuranceVO.getPremiumVO().getGovtTax() );
			policyDataVO.getPremiumVO().setPolicyFees( homeInsuranceVO.getPremiumVO().getPolicyFees() );
		}
		policyTableData.setTableData( policyDataVO );
		policyTableDataList.add( policyTableData );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_POLICY, policyTableDataList );
		
		
		//Setting Domestic Staff Details to tTrnGaccPerson
		List<TableData> gaccTableDataList = new ArrayList<TableData>( 0 );
		for( StaffDetailsVO staffDetailsVO : homeInsuranceVO.getStaffDetails() ){
			if(!Utils.isEmpty( staffDetailsVO.getEmpName()) && !Utils.isEmpty( staffDetailsVO.getEmpDob() )){
				Integer basicRskCode = null;
				BigDecimal basicRskId = null;
				Long rtCode = null;
				Integer rskCode = null;
				Integer riRskCode = null;
				Integer rcCode = null;
				for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
					if( coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.EL_COVER_CODE && coverDetailsVO.getIsCovered().equals("on") 
							&& coverDetailsVO.getRiskCodes().getRiskType() == SvcConstants.RT_CODE_HOME_CONTENTS ){
						basicRskId = coverDetailsVO.getRiskCodes().getBasicRskId();
						basicRskCode = coverDetailsVO.getRiskCodes().getBasicRskCode().intValue();
						rtCode = coverDetailsVO.getRiskCodes().getRiskType().longValue();
						rskCode = coverDetailsVO.getRiskCodes().getRiskCode();
						rcCode = coverDetailsVO.getRiskCodes().getRiskCat();
						riRskCode = coverDetailsVO.getRiRskCode();
					}
				}
				TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
				TTrnGaccPersonVOHolder tTrnGaccPersonVOHolder = new TTrnGaccPersonVOHolder();
				tTrnGaccPersonVOHolder.setGprPolicyId( homeInsuranceVO.getCommonVO().getPolicyId() );
				tTrnGaccPersonVOHolder.setGprId((long) staffDetailsVO.getEmpId());
				
				tTrnGaccPersonVOHolder.setGprEName( staffDetailsVO.getEmpName() );
				tTrnGaccPersonVOHolder.setGprDateOfBirth( staffDetailsVO.getEmpDob() );
				
				if(!Utils.isEmpty( rskCode )){
					tTrnGaccPersonVOHolder.setGprRskCode( Long.valueOf( rskCode ) );
				}
				
				if(!Utils.isEmpty( rcCode )){
					tTrnGaccPersonVOHolder.setGprRcCode( Long.valueOf( rcCode ) );
				}
				
				if(!Utils.isEmpty( rtCode )){
					tTrnGaccPersonVOHolder.setGprRtCode( rtCode );
				}
				tTrnGaccPersonVOHolder.setGprStatus( Byte.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
				tTrnGaccPersonVOHolder.setGprValidityExpiryDate( SvcConstants.EXP_DATE );
				tTrnGaccPersonVOHolder.setGprEndtId( homeInsuranceVO.getCommonVO().getEndtId() );
				
				if(!Utils.isEmpty( riRskCode )){
					tTrnGaccPersonVOHolder.setGprRiRskCode( riRskCode );
				}
				
				if(!Utils.isEmpty( basicRskCode )){
					tTrnGaccPersonVOHolder.setGprBasicRskCode( basicRskCode );
				}
				
				if(!Utils.isEmpty( basicRskId )){
					tTrnGaccPersonVOHolder.setGprBasicRiskId( basicRskId.longValue() );
				}
				tTrnGaccPersonVOHolder.setGprPreparedBy( SvcUtils.getUserId( homeInsuranceVO ) );
				tTrnGaccPersonVOHolder.setGprPreparedDt( homeInsuranceVO.getCreatedOn() );
				tTrnGaccPersonVOHolder.setGprStartDate( homeInsuranceVO.getScheme().getEffDate() );
				tTrnGaccPersonVOHolder.setGprEndDate( homeInsuranceVO.getScheme().getExpiryDate() );
				tTrnGaccPersonVOHolder.setGprValidityStartDate(staffDetailsVO.getEmpVsd());
				gaccTableData.setTableData( tTrnGaccPersonVOHolder );
	
				gaccTableDataList.add( gaccTableData );
			}

		}
		/*
		 * Fetch the old gacc Person details 
		 */
		CommonVO commonVO = policyDataVO.getCommonVO();
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();

		loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
		if(commonVO.getIsQuote()){
			loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		}
		else{
			loadDataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		}
		loadDataInputVO.setEndtId( commonVO.getEndtId() );
		loadDataInputVO.setLocCode( commonVO.getLocCode() );
		loadDataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		loadDataInputVO.setDocCode( commonVO.getDocCode() );
		
		Map<String, Class<? extends BaseVO>> OldDataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();

		OldDataMap.put( SvcConstants.T_TRN_GACC_PERSON, TTrnGaccPersonVOHolder.class );

		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> oldDataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( com.Constant.CONST_BASELOAD, loadDataInputVO, OldDataMap );
		List<TableData<BaseVO>> polTableData = oldDataHolder.getData().get( SvcConstants.T_TRN_GACC_PERSON );

		if( !Utils.isEmpty( polTableData ) ){
			for( TableData<BaseVO> tableData : polTableData ){
				TTrnGaccPersonVOHolder trnGaccPersonVOHolder = (TTrnGaccPersonVOHolder) tableData.getTableData();
				boolean toDelete = true;
				for( StaffDetailsVO staffDetailsVO : homeInsuranceVO.getStaffDetails() ){
					if( staffDetailsVO.getEmpId() == trnGaccPersonVOHolder.getGprId() ){
						toDelete = false;
						break;
					}
				}
				if( toDelete ){
					TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
					gaccTableData.setToBeDeleted( toDelete );
					gaccTableData.setTableData( trnGaccPersonVOHolder );
					gaccTableDataList.add( gaccTableData );
				}
			}

		}

		TMasCashCustomerVOHolder cashCustomerQuo = (TMasCashCustomerVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder" );
		cashCustomerQuo = mapPolicyVOToTMasCashCustomer( cashCustomerQuo, policyDataVO );
		cashCustTableData.setTableData( cashCustomerQuo );
		cashCustTableDataList.add( cashCustTableData );
		dataMap.put( SvcConstants.TABLE_ID_T_MAS_CASH_CUSTOMER_QUO, cashCustTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, premiumTableDataList );
		dataMap.put( SvcConstants.T_TRN_GACC_PERSON, gaccTableDataList );
		dataHolder.setData( dataMap );
		baseSaveSvc.invokeMethod( com.Constant.CONST_BASESAVE, dataHolder, homeInsuranceVO.getCommonVO() );
		

		/* Below code is added to store the risk dependent conditions, default conditions and conditions for compulsory excess
		 */

		Map<Integer, Boolean> risksSelected = new HashMap<Integer, Boolean>();
		risksSelected.put(SvcConstants.HOME_BUILDING_RISK_TYPE, buildingSelected);
		risksSelected.put(SvcConstants.HOME_CONTENT_RISK_TYPE,contentSelected);
		risksSelected.put(SvcConstants.HOME_PERSONAL_POS_RISK_TYPE,personalPosSelected );
		
		saveDefaultConditions(policyDataVO,risksSelected);
	}

	/**
	 * saveDefaultConditions method will store the conditions into T_TRN_POLICY_CONDITION/QUO table.
	 * @param policyDataVO , contains all the risk related information for current transaction
	 * @param risksSelected, contains (key,value) pairs, where key=risk type and value(boolean)= whether that risk is selected or not.
	 * 
	 * On successful execution, all the default/'risk related'/'excess related' conditions will be stored into the database
	 */
	@SuppressWarnings("unchecked")
	private void saveDefaultConditions( PolicyDataVO policyDataVO, Map<Integer, Boolean> risksSelected ){

		List<StandardClause> excess ;
		List<StandardClause> defaultClauses;
		DataHolderVO<Object[]> inputDataForLoad = new DataHolderVO<Object[]>();
		CommonVO commonVO = policyDataVO.getCommonVO();
		Integer sectionId = null;
		boolean isExcess = true;

		excess = getExcessObjects( policyDataVO, risksSelected );
		defaultClauses = getClausesObjects( risksSelected,policyDataVO.getScheme().getSchemeCode(), policyDataVO.getCommonVO().isOldContentPPCode());

		if( !Utils.isEmpty( commonVO.getLob() ) ){
			sectionId = Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_SEC_ID" ) );
		}

		Object[] inputForLoad = { commonVO, sectionId, isExcess, false };
		inputDataForLoad.setData( inputForLoad );

		DataHolderVO<List<StandardClause>> holderVO = (DataHolderVO<List<StandardClause>>) viewClauseSvc.invokeMethod( "getClauses", inputDataForLoad );

		SectionVO sectionVo = new SectionVO( null );
		sectionVo.setStandardClauses( excess );

		/*Added the below loop for Excess to be made edited for RSA user - for building/content/personal possesions
		* To Save the edited amount on click of SAVEQUOTE in generalInfo page
		* Ticket ID : 80398
		*/

		String userProfile = ( (UserProfile) ( commonVO ).getLoggedInUser() ).getRsaUser().getProfile();
		if( !Utils.isEmpty( sectionVo ) && !Utils.isEmpty( sectionVo.getStandardClauses() ) && ( commonVO.getAppFlow() == Flow.EDIT_QUO || commonVO.getAppFlow() == Flow.AMEND_POL )
				&& !Utils.isEmpty( userProfile ) && userProfile.equalsIgnoreCase( "employee" ) ){
			for( StandardClause sectionVOStandard : sectionVo.getStandardClauses() ){
				for( StandardClause standardClauses : holderVO.getData() ){
					// Added equals() instead of == to avoid sonar violation on 25-9-2017
					if( sectionVOStandard.getClauseCode() .equals(( standardClauses.getClauseCode())) && !Utils.isEmpty( standardClauses.getAmount() ) )
						sectionVOStandard.setAmount( standardClauses.getAmount() );
				}
			}
		}

		Object[] inputForSave = { sectionVo, commonVO, holderVO.getData(), isExcess };
		DataHolderVO<Object[]> inputDataForSave = new DataHolderVO<Object[]>();
		inputDataForSave.setData( inputForSave );
		viewClauseSvc.invokeMethod( "saveClauses", inputDataForSave );

		isExcess = false;
		Object[] inputForLoad2 = { commonVO, sectionId, isExcess, false };
		inputDataForLoad.setData( inputForLoad2 );
		holderVO = (DataHolderVO<List<StandardClause>>) viewClauseSvc.invokeMethod( "getClauses", inputDataForLoad );

		/*
		 * Saving the default conditions
		 */
		DataHolderVO<List<TTrnPolicyConditionQuo>> dataHolderVOQuo = null;
		DataHolderVO<List<TTrnPolicyCondition>> dataHolderVOPol = null;

		List<StandardClause> standardClauses = new ArrayList<StandardClause>();
		//standardClauses.addAll(holderVO.getData());
		SectionVO sectionVo1 = new SectionVO( null );
		List<StandardClause> clauses = holderVO.getData();

		for( StandardClause clause : clauses ){
			StandardClause copyClause = CopyUtils.copySerializableObject( clause );

			if( !Utils.isEmpty( defaultClauses ) ){
				for( StandardClause defaultClause : defaultClauses ){
					if( copyClause.getClauseCode().longValue() == defaultClause.getClauseCode().longValue() ){
						copyClause.setSelected( defaultClause.isSelected() );
					}
				}
			}
			standardClauses.add( copyClause );
		}

		sectionVo1.setStandardClauses( standardClauses );
		
		if( commonVO.getIsQuote() ){

			dataHolderVOQuo = (DataHolderVO<List<TTrnPolicyConditionQuo>>) commonOpDAO.getClauseForCurrentEndtId( commonVO, policyDataVO );
			List<TTrnPolicyConditionQuo> conditionQuos = !Utils.isEmpty( dataHolderVOQuo.getData() ) ? dataHolderVOQuo.getData() : null;

			if( !Utils.isEmpty( conditionQuos ) ){
				for( TTrnPolicyConditionQuo tTrnPolicyConditionQuo : conditionQuos ){
					for( StandardClause clause : clauses ){
						if( clause.getClauseCode().intValue() == tTrnPolicyConditionQuo.getId().getTpcCode() ){
							clause.setSelected( true );
						}
					}
				}
			}
		}
		else{
			dataHolderVOPol = (DataHolderVO<List<TTrnPolicyCondition>>) commonOpDAO.getClauseForCurrentEndtId( commonVO, policyDataVO );
			List<TTrnPolicyCondition> conditionPols = !Utils.isEmpty( dataHolderVOPol.getData() ) ? dataHolderVOPol.getData() : null;
			
			if( !Utils.isEmpty( conditionPols ) ){
				for( TTrnPolicyCondition tTrnPolicyCondition : conditionPols ){
					for( StandardClause clause : clauses ){
						if( clause.getClauseCode().intValue() == tTrnPolicyCondition.getId().getTpcCode() ){
							clause.setSelected( true );
						}
					}
				}
			}
		}

		if( !Utils.isEmpty( holderVO.getData() ) ){

			isExcess = false;

			Object[] inputForSave2 = { sectionVo1, commonVO, holderVO.getData(), isExcess, false };
			DataHolderVO<Object[]> inputDataForSave1 = new DataHolderVO<Object[]>();
			inputDataForSave1.setData( inputForSave2 );
			viewClauseSvc.invokeMethod( "saveClauses", inputDataForSave1 );

			//if( ( commonVO.getAppFlow() == Flow.AMEND_POL ) ){	/* commented if condition (Not content inside if) - sonar violation fix */
				//generateEndtText(commonVO);
			//}
		}
	}

	private List<StandardClause> getClausesObjects(Map<Integer, Boolean> risksSelected , Integer schemeCode,boolean isOldContentPP) {
		
		List<StandardClause> clauses = new ArrayList<StandardClause>();
		Iterator<Entry<Integer, Boolean>> it = risksSelected.entrySet().iterator();
		
		while (it.hasNext()) {
			String[] pcCodes=null;
	        Map.Entry<Integer,Boolean> mapEntry = (Map.Entry<Integer,Boolean>)it.next();
	        /**
	         * Modified for the Advent Net : 104174 and 104175 - Special Condition - Changed the limit of content and PP to 40,000 and 10,000 respectively. 
	         */
	       if(schemeCode.toString().equals(Utils.getSingleValueAppConfig("EMIRATES_SCH_CODE")) || isOldContentPP)
	        {
	        	pcCodes =!Utils.isEmpty(Utils.getMultiValueAppConfig("EMIRATES_PC_CODE_COND_"+mapEntry.getKey(), ","))? Utils.getMultiValueAppConfig("EMIRATES_PC_CODE_COND_"+mapEntry.getKey(), ","):null;
	        }
	        else
	        {
	        	pcCodes =!Utils.isEmpty(Utils.getMultiValueAppConfig("PC_CODE_COND_"+mapEntry.getKey(), ","))? Utils.getMultiValueAppConfig("PC_CODE_COND_"+mapEntry.getKey(), ","):null;
	        	
	        	if(!Utils.isEmpty(Utils.getSingleValueAppConfig("DEPLOYED_LOCATION")) && 
						(Utils.getSingleValueAppConfig("DEPLOYED_LOCATION")).toString().equalsIgnoreCase("50")) { // Request Id:162993, 167469 : Default conditions not populated for Home quotes
	    			pcCodes =!Utils.isEmpty(Utils.getMultiValueAppConfig("BAH_PC_CODE_COND_"+mapEntry.getKey(), ","))? Utils.getMultiValueAppConfig("BAH_PC_CODE_COND_"+mapEntry.getKey(), ","):null;
	    		}	        	
	        }
	        	
	        if(!Utils.isEmpty(pcCodes)){
	        	for (String pcCode : pcCodes) {
	        		StandardClause standardClause = new StandardClause();
					standardClause.setSectionID( SvcConstants.SECTION_ID_HOME);
					standardClause.setClauseCode( Long.valueOf(pcCode) );
					standardClause.setClauseType( "C" );
					if(mapEntry.getValue() != null && mapEntry.getValue()){
						standardClause.setSelected( true );
					}
					else{
						standardClause.setSelected( false );
					}
					clauses.add( standardClause );
				}
	        }
		}

		return clauses;
	}

	/**
	 * 
	 * This method will construct and return the list of @StandardClause objects, objects are created based on the risk selected.
	 * 
	 * @param policyDataVO
	 * @param risksSelected
	 * @return
	 */
	private List<StandardClause> getExcessObjects(PolicyDataVO policyDataVO, Map<Integer, Boolean> risksSelected) {
		
		List<StandardClause> excess = new ArrayList<StandardClause>();
		Iterator<Entry<Integer, Boolean>> it = risksSelected.entrySet().iterator();
		
		while (it.hasNext()) {
	        Map.Entry<Integer,Boolean> mapEntry = (Map.Entry<Integer,Boolean>)it.next();
	        String[] pcCodes =!Utils.isEmpty(Utils.getMultiValueAppConfig("PC_CODE_EXCESS_"+mapEntry.getKey(), ","))? Utils.getMultiValueAppConfig("PC_CODE_EXCESS_"+mapEntry.getKey(), ","):null; 
	        
	        if(pcCodes!=null){
	        	
	        	//List<String> pcCodeList = new ArrayList<String>();
	        	List<String> pcCodeList=new ArrayList<String>(Arrays.asList(pcCodes));
		        
		        if(mapEntry.getKey().equals(SvcConstants.HOME_CONTENT_RISK_TYPE) && mapEntry.getValue()){
	        		pcCodes =!Utils.isEmpty(Utils.getMultiValueAppConfig(com.Constant.CONST_PC_CODE_EXCESS_LIST_ITEM, ","))? Utils.getMultiValueAppConfig(com.Constant.CONST_PC_CODE_EXCESS_LIST_ITEM, ","):null;
	        		if(!Utils.isEmpty(pcCodes)){		/* Added if condition for null check of pcCodes - sonar violation fix */
	        		Collections.addAll(pcCodeList, pcCodes);
	        		}
		        }
		        
		        if(mapEntry.getKey().equals(SvcConstants.HOME_PERSONAL_POS_RISK_TYPE) && !risksSelected.get(SvcConstants.HOME_CONTENT_RISK_TYPE) && mapEntry.getValue()){
		        	pcCodes =!Utils.isEmpty(Utils.getMultiValueAppConfig(com.Constant.CONST_PC_CODE_EXCESS_LIST_ITEM, ","))? Utils.getMultiValueAppConfig(com.Constant.CONST_PC_CODE_EXCESS_LIST_ITEM, ","):null;
		        	if(!Utils.isEmpty(pcCodes)){	   /* Added if condition for null check of pcCodes - sonar violation fix */
		        	Collections.addAll(pcCodeList, pcCodes);
		        	}
		        }
	        	
	        	for (String pcCode : pcCodeList) {
	        		StandardClause standardClause = new StandardClause();
					standardClause.setSectionID( SvcConstants.SECTION_ID_HOME);
					standardClause.setClauseCode( Long.valueOf(pcCode) );
					standardClause.setClauseType( "C" );
					if(mapEntry.getValue() != null && mapEntry.getValue()){
						standardClause.setSelected( true );
					}
					else{
						standardClause.setSelected( false );
					}
					standardClause.setAmount(getPrmCompulsoryExcess( mapEntry.getKey(), policyDataVO.getScheme().getTariffCode() ).toString());
					excess.add( standardClause );
				}
	        	
	        	if( !Utils.isEmpty( policyDataVO.getScheme() )){
	        		Integer schemeCode,tariffCode;
	        		schemeCode = policyDataVO.getScheme().getSchemeCode();
	        		tariffCode = policyDataVO.getScheme().getTariffCode();
	        	
	        		/*Start of code change to fix of excess conditions amount related issue - identified after 3.4 Release */
	        		
	        		/*if(schemeCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( "EMIRATES_SCH_CODE" ) ) ) || tariffCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_ANA_TAR_CODE ) ) )){
	    	        	Iterator<StandardClause> iterator = excess.iterator();
	        			while (iterator.hasNext()) {
			        		StandardClause clause = iterator.next();
			        		if((schemeCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( "EMIRATES_SCH_CODE" ) ) ) && clause.getClauseCode().equals(4L)) ){
			        			excess.remove(clause);
			        		}
			        		if((tariffCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_ANA_TAR_CODE ) ) ) )){
			        			if(clause.getClauseCode().equals(4L)){
			        				clause.setAmount(null);
			        			}else if(clause.getClauseCode().equals(5L)){
			        				excess.remove(clause);
			        			}
			        		}
						}
	        		}*/	        		
	        		
					Iterator<StandardClause> iterator = excess.iterator();
	        		while(iterator.hasNext()){
	        			StandardClause clause = iterator.next();
	        			if(schemeCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( "EMIRATES_SCH_CODE" ) ) ))
	        			{
	        				if(clause.getClauseCode().equals(4L) ){
	        					//excess.remove(clause);
		        				iterator.remove();
	        				}	        				
		        		}
	        			else if(!tariffCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_ANA_TAR_CODE ) ) ) && clause.getClauseCode().equals(4L)){
	        				clause.setAmount(null);
	        			}
	        			if(!tariffCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_ANA_TAR_CODE ) ) ) && clause.getClauseCode().equals(3L)){
	        				clause.setAmount(null);
	        			}	        			
	        			
	        			if((tariffCode.equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_ANA_TAR_CODE ) ) ) ) && clause.getClauseCode().equals(5L)){
	        				iterator.remove();
	        			}
	        		}
	        		
	        		/*End of code change for excess conditions amount related issue*/
	        	}
	        }
	    }
		Collections.sort(excess,StandardClause.StdClauseCode);
		return excess;
	}
	
	private void generateEndtText(CommonVO commonVO){
		
		//getHibernateTemplate().flush();
		Integer userId = ( (UserProfile) ( commonVO ).getLoggedInUser() ).getRsaUser().getUserId();
		PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean( "commonEndtTxtGen" );
		String tableNames = "T_TRN_POLICY_CONDITION";
		
		sp.call( commonVO.getPolicyId(), commonVO.getEndtId(), tableNames, userId,
				Integer.valueOf( Utils.getSingleValueAppConfig(commonVO.getLob()+"_SEC_ID") ), commonVO.getLocCode() );

	}

	/*
	 * This method will return the premium compulsory excess, based on the  risk category and tariff code.
	 * Method will be used only for HOME Insurance.
	 * 
	 */
	private BigDecimal getPrmCompulsoryExcess( Integer riskType, Integer tariffCode ){

		String category;

		if( riskType.equals( SvcConstants.HOME_BUILDING_RISK_TYPE ) ){
			category = "BLD_DEDUCTIBLES";
		}
		else if( riskType.equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) ){
			category = "CNT_DEDUCTIBLES";
		}
		else if( riskType.equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE ) ){
			category = "PB_DEDUCTIBLES";
		}
		else{
			throw new BusinessException( "", null, "Premium Compulsory Excess is not configured for this Home risk category" );
		}

		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "DEDUCTIBLES", category, tariffCode.toString() );

		return listVO.getLookUpList().get( SvcConstants.zeroVal ).getCode();
	}

	/**
	 * @param homeInsuranceVO
	 * @param premiumTableDataList
	 * @return
	 */
	public List<TableData> updateToBeDeletedPrmRecsTravel( CommonVO commonVO, List<TableData> premiumTableDataList ){

		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		inputVO.setQuoteNo( commonVO.getQuoteNo() );
		inputVO.setPolicyNo( commonVO.getPolicyNo() );
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( com.Constant.CONST_BASELOAD, inputVO,
				dataToLoad );
		 List<TableData> finalListToBeSaved = new ArrayList<TableData>();
		 finalListToBeSaved.addAll( premiumTableDataList );
		if( !Utils.isEmpty( dataHolderVO ) ){
			List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );
			if( !Utils.isEmpty( premiumList ) ){
				
				//premiumTableDataList = SvcUtils.updateToBeDeletedPremiums( premiumList, premiumTableDataList );
				
				for( TableData<TTrnPremiumVOHolder> tData : premiumList ){
					TTrnPremiumVOHolder premiumVOHolder = tData.getTableData();
					if( !premiumVOHolder.isPresent( premiumTableDataList ) ){
						tData.setToBeDeleted( true );
						finalListToBeSaved.add( tData );
					}
				}
			}

		}
		
		
		return finalListToBeSaved;
	}
	
	public List<TableData> updateToBeDeletedPrmRecs( CommonVO commonVO, List<TableData> premiumTableDataList ){

		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		inputVO.setQuoteNo( commonVO.getQuoteNo() );
		inputVO.setPolicyNo( commonVO.getPolicyNo() );
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( com.Constant.CONST_BASELOAD, inputVO,
				dataToLoad );
		 //List<TableData> finalListToBeSaved = new ArrayList<TableData>();
		if( !Utils.isEmpty( dataHolderVO ) ){
			List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );
			if( !Utils.isEmpty( premiumList ) ){
				premiumTableDataList = SvcUtils.updateToBeDeletedPremiums( premiumList, premiumTableDataList );
			}

		}
		
		
		return premiumTableDataList;
	}

	/**
	 * Method to map the coverage details to TTrnPremiumVOHolder
	 * 
	 * @param coverDetailsVO
	 * @param premiumVOHolder
	 * @return
	 */
	private TTrnPremiumVOHolder mapCovDetsVOToTTrnPrmHldr( CoverDetailsVO coverDetailsVO, TTrnPremiumVOHolder premiumVOHolder ){
		return BeanMapper.map( coverDetailsVO, premiumVOHolder );
	}

	/**
	 * Method to map the PolicyVO to TMasCashCustomerVOHolder
	 * 
	 * @param cashCustomerQuo
	 * @param policyDataVO
	 * @return
	 */
	private TMasCashCustomerVOHolder mapPolicyVOToTMasCashCustomer( TMasCashCustomerVOHolder cashCustomerQuo, PolicyDataVO policyDataVO ){
		return BeanMapper.map( policyDataVO, cashCustomerQuo );
	}

	/**
	 * Method to load the premium details from DB (Call to the DAO Layer)
	 * 
	 * @param baseVO
	 * @return
	 */
	private BaseVO loadPremiumDetails( BaseVO baseVO ){
		return baseVO;
	}

}
