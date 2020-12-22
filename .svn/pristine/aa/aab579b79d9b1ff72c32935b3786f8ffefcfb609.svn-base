/**
 * 
 */
package com.rsaame.pas.travel.svc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.BaseSaveSvc;
import com.rsaame.pas.home.svc.UWQuestionsLoadSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;
import org.apache.log4j.Logger;

/**
 * @author Karthik Jagadish
 * 
 * This class is a service class for Travel Detail which calls the BaseSaveOperation to perform the save/load operation
 *
 */
public class TravelDetailSvc extends BaseService{
	
	private final static Logger LOGGER = Logger.getLogger(TravelDetailSvc.class);

	private BaseSaveSvc baseSaveSvc;

	private BaseLoadSvc baseLoadSvc;

	private UWQuestionsLoadSvc uwQuestionsLoadSvc;

	/*
	 * (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( SvcConstants.SAVE_GACC_PERSON.equals( methodName ) ){
			returnValue = saveTravelDetailSection( (BaseVO) args[ 0 ] );
		}
		if( SvcConstants.LOAD_GACC_PERSON.equals( methodName ) ){
			returnValue = loadTravelDetailSection( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	/**
	 * This method saves the travel detail section by calling executeSave method of BaseSaveOperation
	 * 
	 * @param travelDetailVo
	 * @return
	 */
	@SuppressWarnings( "rawtypes" )
	private BaseVO saveTravelDetailSection( BaseVO input ){

		if( input != null ){

			/*
			 * Get the required VO from BaseVO
			 */
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
			LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) input;

			TravelDetailsVO travelDetailsVO = travelInsuranceVO.getTravelDetailsVO();

			PolicyDataVO policyDataVO = travelInsuranceVO;

			List<TravelerDetailsVO> travelerDetailsVO = travelDetailsVO.getTravelerDetailsList();

			List<TableData> gaccTableDataList = new ArrayList<TableData>( 0 );
			List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );

			CommonVO commonVO = policyDataVO.getCommonVO();

			/*
			 * Prepare the VOHOlder for T_TRN_GACC_PERSON table and set the vo holder to table data
			 */
			if( travelInsuranceVO != null ){
				SchemeVO schemeVO = travelInsuranceVO.getScheme();

				/*Setting the scheme code, so that customer id can be retrieved */

				//schemeVO.setSchemeCode( DAOUtils.getSchemeCodeForBrokerName( policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) );
				 
				//Commenting the below code, as scheme details are now present in GI page for Travel
				//Double schCode = SvcUtils.getLookUpCodeForLOneLTwo( "SCHEME",  Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName() ) ? SvcConstants.DIRECT_BROKER_CODE.toString()
				//		: policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString(), SvcConstants.TRAVEL_POLICY_TYPE_FOR_LOOKUP.toString() );
			
				// schemeVO.setSchemeCode( schCode.intValue() );
				
				
				// Load the existing data from the database
				TravelInsuranceVO oldTravelDetailsVO = (TravelInsuranceVO) loadTravelDetailSection( CopyUtils.copySerializableObject(travelInsuranceVO)  );
				
				for( TravelerDetailsVO travelerDetailVO : travelerDetailsVO ){
					TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
					TTrnGaccPersonVOHolder tTrnGaccPersonVOHolder = new TTrnGaccPersonVOHolder();
					tTrnGaccPersonVOHolder = BeanMapper.map( travelerDetailVO, tTrnGaccPersonVOHolder );
					tTrnGaccPersonVOHolder = BeanMapper.map( travelDetailsVO, tTrnGaccPersonVOHolder );
					tTrnGaccPersonVOHolder.setGprPolicyId( travelInsuranceVO.getPolicyId() );
					tTrnGaccPersonVOHolder.setGprAAddress1(SvcUtils.getLookUpDescription(SvcConstants.FINAL_DEST_LOOKUP_CAT, "ALL", SvcConstants.WORLDWIDE.equalsIgnoreCase(travelDetailsVO.getTravelLocation())? "1" : "2" , travelDetailsVO.getFinalDestination()));
					tTrnGaccPersonVOHolder.setGprRskCode( Long.valueOf( Utils.getSingleValueAppConfig( SvcConstants.GPR_RSK_CODE_TRAVEL ) ) );
					tTrnGaccPersonVOHolder.setGprRcCode( Long.valueOf( Utils.getSingleValueAppConfig( SvcConstants.GPR_RC_CODE_TRAVEL ) ) );

					tTrnGaccPersonVOHolder.setGprStatus( Byte.valueOf( String.valueOf( travelInsuranceVO.getStatus() ) ) );
					tTrnGaccPersonVOHolder.setGprValidityExpiryDate( SvcConstants.EXP_DATE );
					tTrnGaccPersonVOHolder.setGprEndtId( commonVO.getEndtId() );
					tTrnGaccPersonVOHolder.setGprRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.GPR_RI_RSK_CODE_TRAVEL ) ) );
					tTrnGaccPersonVOHolder.setGprBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.GPR_BASIC_RSK_CODE_TRAVEL ) ) );

					tTrnGaccPersonVOHolder.setGprPreparedBy( SvcUtils.getUserId( travelInsuranceVO ) );
					tTrnGaccPersonVOHolder.setGprPreparedDt( travelInsuranceVO.getCreatedOn() );
					/*
					 * In case of policy extension new records are created for person, to avoid base save from getting 
					 * the old records we update the vsd here so that save works on the latest rec
					 */
					if(travelInsuranceVO.isPolicyExtended()){
					tTrnGaccPersonVOHolder.setGprValidityStartDate(travelInsuranceVO.getCommonVO().getVsd());
					travelerDetailVO.setVsd(travelInsuranceVO.getCommonVO().getVsd());
					}
					if( schemeVO != null ){
						tTrnGaccPersonVOHolder.setGprStartDate( schemeVO.getEffDate() );
						tTrnGaccPersonVOHolder.setGprEndDate( schemeVO.getExpiryDate() );
						tTrnGaccPersonVOHolder.setGprAge( SvcUtils.getAge( travelerDetailVO.getDateOfBirth(), schemeVO.getEffDate() ) );
					}
					
					TravelDetailsVO travelDetails = oldTravelDetailsVO.getTravelDetailsVO();
					List<TravelerDetailsVO> oldTravelDetailsList = travelDetails.getTravelerDetailsList();
					/*
					 * To set the Sum Insured from the existing data from the database
					 */
					for( TravelerDetailsVO travelData : oldTravelDetailsList ){
						if( Utils.isEmpty( tTrnGaccPersonVOHolder.getGprSumInsured() ) && !(Utils.isEmpty( travelData.getGprId() ) ) && travelData.getGprId().equals( travelerDetailVO.getGprId() ) ){
							tTrnGaccPersonVOHolder.setGprSumInsured( travelData.getSumInsured() );
						}
					}
					gaccTableData.setTableData( tTrnGaccPersonVOHolder );

					gaccTableDataList.add( gaccTableData );

				}
				
				TravelDetailsVO travelDetails = oldTravelDetailsVO.getTravelDetailsVO();
				List<TravelerDetailsVO> oldTravelDetailsList = travelDetails.getTravelerDetailsList();
				// Check the mapped list with the list loaded from the database to check for any delete cases
				List<TTrnPremiumVOHolder> tobeDeletedPrmRecs = new ArrayList<TTrnPremiumVOHolder>();
				for( TravelerDetailsVO travelData : oldTravelDetailsList ){
					if( !travelerDetailsVO.contains( travelData ) ){
						
						TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
						TTrnGaccPersonVOHolder tTrnGaccPersonVOHolder = new TTrnGaccPersonVOHolder();
						tTrnGaccPersonVOHolder = BeanMapper.map( travelData, tTrnGaccPersonVOHolder );
						tTrnGaccPersonVOHolder = BeanMapper.map( travelDetailsVO, tTrnGaccPersonVOHolder );
						tTrnGaccPersonVOHolder.setGprAAddress1(SvcUtils.getLookUpDescription(SvcConstants.FINAL_DEST_LOOKUP_CAT, "ALL", SvcConstants.WORLDWIDE.equalsIgnoreCase(travelDetailsVO.getTravelLocation())? "1" : "2" , travelDetailsVO.getFinalDestination()));
						gaccTableData.setToBeDeleted( true );
						gaccTableData.setTableData( tTrnGaccPersonVOHolder );
						gaccTableDataList.add( gaccTableData );
						TTrnPremiumVOHolder tobeDeletedPrmRecHolder = new TTrnPremiumVOHolder();
						tobeDeletedPrmRecHolder.setPrmRskId( travelData.getGprId() );
						tobeDeletedPrmRecHolder.setPrmBasicRskId( travelData.getGprId() );
						tobeDeletedPrmRecs.add( tobeDeletedPrmRecHolder );
					}
				}

				//Preparing the prm records to be deleted

				//1. Get all the prm rec
				List<TableData> premiumQuoTableDataList = new ArrayList<TableData>( 0 );
				if(!Utils.isEmpty( tobeDeletedPrmRecs )){
				LoadCoverSvc coverSvc = (LoadCoverSvc) Utils.getBean( "loadCoverSvc" );
				List<TTrnPremiumVOHolder> travelInsurancePrm = (List<TTrnPremiumVOHolder>) coverSvc.invokeMethod( "getPremiumRecords", commonVO );

				//2. Filter out the records to be deleted	
				if(!Utils.isEmpty( travelInsurancePrm)){
					travelInsurancePrm.retainAll( tobeDeletedPrmRecs );
	
					//Create table data for prm record to be deleted by setting toBeDeleted flag as true

					for( TTrnPremiumVOHolder prmData : travelInsurancePrm ){
						TableData<TTrnPremiumVOHolder> premiumTableData = new TableData<TTrnPremiumVOHolder>();
						premiumTableData.setToBeDeleted( true );
						premiumTableData.setTableData( prmData );
						premiumQuoTableDataList.add( premiumTableData );
					}
				}
			}

				/*
				 * Populate the PolicyDataVO as it is required to insert into T_TRN_POLICY table
				 */
				TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
				policyTableData.setTableData( policyDataVO );

				policyTableDataList.add( policyTableData );

				dataMap.put( SvcConstants.T_TRN_POLICY, policyTableDataList );
				dataMap.put( SvcConstants.T_TRN_GACC_PERSON, gaccTableDataList );
				if( !Utils.isEmpty( premiumQuoTableDataList ) ){
					dataMap.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, premiumQuoTableDataList );
				}
				dataHolder.setData( dataMap );

				baseSaveSvc.invokeMethod( "baseSave", dataHolder, commonVO );
			}
		}

		return input;
	}

	/**
	 * This method is to get the travel section from T_TRN_GACC_PERSON table by calling executeLoad method of BaseLoadOperation
	 * 
	 * @param travelDetailVo
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	private BaseVO loadTravelDetailSection( BaseVO baseVO ){
		LOGGER.info("Entered TravelDetailSvc.loadTravelDetailSection method.");
		
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) baseVO;
		CommonVO commonVO = policyDataVO.getCommonVO();
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		List<TravelerDetailsVO> travelerDetailsList = new ArrayList<TravelerDetailsVO>( 0 );
		TravelDetailsVO travelDetailsVO = new TravelDetailsVO();
		SchemeVO schemeVO = policyDataVO.getScheme();

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
		
		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();

		dataMap.put( SvcConstants.T_TRN_GACC_PERSON, TTrnGaccPersonVOHolder.class );
		
		LOGGER.info("TravelDetailSvc.loadTravelDetailSection method, calling BaseLoadSvc.invokeMethod");
		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", loadDataInputVO, dataMap );
		List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_GACC_PERSON );

		if( !Utils.isEmpty( polTableData ) ){
			for( TableData<BaseVO> tableData : polTableData ){
				TTrnGaccPersonVOHolder trnGaccPersonVOHolder = (TTrnGaccPersonVOHolder) tableData.getTableData();

				TravelerDetailsVO travelerDetailsVO = new TravelerDetailsVO();
				travelerDetailsVO = BeanMapper.map( trnGaccPersonVOHolder, travelerDetailsVO );

				travelerDetailsList.add( travelerDetailsVO );
			}

			if( !Utils.isEmpty( polTableData.get( 0 ) ) && !Utils.isEmpty( polTableData.get( 0 ).getTableData() ) ){
				TTrnGaccPersonVOHolder tTrnGaccPersonVOHolder = (TTrnGaccPersonVOHolder) polTableData.get( 0 ).getTableData();

				travelDetailsVO.setTravelLocation( tTrnGaccPersonVOHolder.getGprDescription() );
				travelDetailsVO.setTravelerDetailsList( travelerDetailsList );
				travelDetailsVO.setFinalDestination(SvcUtils.getLookUpCode(SvcConstants.FINAL_DEST_LOOKUP_CAT, "ALL", SvcConstants.WORLDWIDE.equalsIgnoreCase(travelDetailsVO.getTravelLocation())? "1" : "2" , tTrnGaccPersonVOHolder.getGprAAddress1()));
				schemeVO.setEffDate( tTrnGaccPersonVOHolder.getGprStartDate() );
				schemeVO.setExpiryDate( tTrnGaccPersonVOHolder.getGprEndDate() );

				travelInsuranceVO.setTravelDetailsVO( travelDetailsVO );
				travelInsuranceVO.setScheme( schemeVO );
				travelInsuranceVO.setPolicyId( tTrnGaccPersonVOHolder.getGprPolicyId() );
				travelInsuranceVO.setEndtId( tTrnGaccPersonVOHolder.getGprEndtId() );
				
				LOGGER.info("TravelDetailSvc.loadTravelDetailSection method, calling UWQuestionsLoadSvc.invokeMethod");
				//Retrieving UWQuestions using UWQuesionsSvc
				UWQuestionsVO uwQuestionsVO = (UWQuestionsVO) uwQuestionsLoadSvc.invokeMethod( "uwQuestionsLoadService", loadDataInputVO );
				travelInsuranceVO.setUwQuestions( uwQuestionsVO );

			}
		}
		LOGGER.info("Exiting TravelDetailSvc.loadTravelDetailSection method.");
		return travelInsuranceVO;
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
	 * @param uwQuestionsLoadSvc
	 */
	public void setUwQuestionsLoadSvc( UWQuestionsLoadSvc uwQuestionsLoadSvc ){
		this.uwQuestionsLoadSvc = uwQuestionsLoadSvc;
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

}
