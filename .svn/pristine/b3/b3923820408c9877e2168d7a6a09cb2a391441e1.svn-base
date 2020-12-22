package com.rsaame.pas.com.svc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.pa.svc.PersonalDetailsSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolder;
import com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper;

public class CoInsuranceSvc extends BaseService{


	private DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
	
	private final static com.mindtree.ruc.cmn.log.Logger logger = com.mindtree.ruc.cmn.log.Logger.getLogger( PersonalDetailsSvc.class );
	
	private BaseSaveSvc baseSaveSvc;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		
		BaseVO returnValue = null;
		if( SvcConstants.SAVE_CO_INSURANCE_INFO.equals( methodName ) ){
			PolicyDataVO polData = (PolicyDataVO) (BaseVO) args[ 0 ];
			
				returnValue = saveCoInsuranceDetails( polData);
			}
		
		return returnValue;
	}
	

	/**
	 * 
	 * @param baseVo
	 * @return
	 */
	 private BaseVO saveCoInsuranceDetails( BaseVO baseVo ){
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();
		PolicyDataVO polDataVo = (PolicyDataVO) baseVo;
		
		
		
	/*
			 * Get the required VO from BaseVO
			 */
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
			
			if(!Utils.isEmpty(polDataVo.getGeneralInfo()))
			{

				if(!Utils.isEmpty(polDataVo.getGeneralInfo().getCoInsurer()) 
						&& !Utils.isEmpty(polDataVo.getGeneralInfo().getCoInsurer().get(0).getCompanyName()))
				{					
					CommonVO commonVO = polDataVo.getCommonVO();
					GeneralInfoVO generalInfoVO = (GeneralInfoVO) polDataVo.getGeneralInfo();		
			 
					TTrnCoInsuranceVOHolderWrapper tTrnCoInsuranceVOHolderWrapper=new TTrnCoInsuranceVOHolderWrapper();
			
					List<TableData> policyTableDataList = new ArrayList<TableData>( );
					//-- Values of policyDataVO is mapped to TTrnCoInsuranceVOHolderWrapper
					tTrnCoInsuranceVOHolderWrapper = BeanMapper.map( generalInfoVO, tTrnCoInsuranceVOHolderWrapper );
		

					/*
					 * Prepare the VOHOlder for T_TRN_COINSURANCE_QUO table and set the vo holder to table data
					 */
			
					
					TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
					policyTableData.setTableData( polDataVo );

					policyTableDataList.add( policyTableData );

					dataMap.put( SvcConstants.T_TRN_POLICY, policyTableDataList );
					List<TableData> tTrnCoinsuranceVoHolderList = tTrnCoInsuranceVOHolderWrapper.getTTrnCoInsuranceVOHolderList();
					List<TableData> tTrnCoinsuranceVoHolderListPolicyId = new ArrayList<TableData>();
					TTrnCoInsuranceVOHolder trnCoInsuranceVOHolder;
					for(TableData tableData:tTrnCoinsuranceVoHolderList)
					{
						trnCoInsuranceVOHolder = (TTrnCoInsuranceVOHolder)tableData.getTableData();
						trnCoInsuranceVOHolder.setCoiPolicyId(commonVO.getPolicyId());
						tableData.setTableData(trnCoInsuranceVOHolder);
						tTrnCoinsuranceVoHolderListPolicyId.add(tableData);
					}
					dataMap.put( SvcConstants.T_TRN_POLICY, policyTableDataList );
					dataMap.put( SvcConstants.T_TRN_COINSURANCE_QUO, tTrnCoinsuranceVoHolderListPolicyId );
				
					dataHolder.setData( dataMap );

					baseSaveSvc.invokeMethod( "baseSave", dataHolder, commonVO );
				}
			}

		
		
		return polDataVo;
	}

	 /**
		 * This method is to get the travel section from T_TRN_GACC_PERSON table by calling executeLoad method of BaseLoadOperation
		 * 
		 * @param travelDetailVo
		 * @return
		 */
		/*@SuppressWarnings( { "unchecked", "rawtypes" } )
		private BaseVO loadCoinsurance( BaseVO baseVO ){
			
			 * 
			 
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

					schemeVO.setEffDate( tTrnGaccPersonVOHolder.getGprStartDate() );
					schemeVO.setExpiryDate( tTrnGaccPersonVOHolder.getGprEndDate() );

					travelInsuranceVO.setTravelDetailsVO( travelDetailsVO );
					travelInsuranceVO.setScheme( schemeVO );
					travelInsuranceVO.setPolicyId( tTrnGaccPersonVOHolder.getGprPolicyId() );
					travelInsuranceVO.setEndtId( tTrnGaccPersonVOHolder.getGprEndtId() );

					//Retrieving UWQuestions using UWQuesionsSvc
					UWQuestionsVO uwQuestionsVO = (UWQuestionsVO) uwQuestionsLoadSvc.invokeMethod( "uwQuestionsLoadService", loadDataInputVO );
					travelInsuranceVO.setUwQuestions( uwQuestionsVO );

				}
			}

			return travelInsuranceVO;
		}*/

	public BaseSaveSvc getBaseSaveSvc() {
		return baseSaveSvc;
	}


	public void setBaseSaveSvc(BaseSaveSvc baseSaveSvc) {
		this.baseSaveSvc = baseSaveSvc;
	}
}


