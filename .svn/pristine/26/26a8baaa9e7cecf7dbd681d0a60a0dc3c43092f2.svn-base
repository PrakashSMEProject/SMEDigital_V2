/**
 * 
 */
package com.rsaame.pas.endorse.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.com.svc.UWQASaveCommonSvc;
import com.rsaame.pas.dao.cmn.IBaseSaveOperation;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.insured.svc.InsuredDetailsSvc;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.tasks.svc.TaskSvc;
import com.rsaame.pas.travel.svc.TravelDetailSvc;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.RuleResponseType;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;
/**
 * @author m1014241
 *
 */
/**
 * @author m1006438
 *
 */
public class GeneralInfoCommonSvc extends BaseService{
	
	private final static com.mindtree.ruc.cmn.log.Logger logger = com.mindtree.ruc.cmn.log.Logger.getLogger( GeneralInfoCommonSvc.class );
	private IBaseSaveOperation baseOperationDao;
	private BaseLoadSvc baseLoadSvc;
	private TravelDetailSvc travelServiceBean;
	private UWQASaveCommonSvc uwqaSaveCommonSvc;
	private PasReferralSaveCommonSvc pasReferralSaveCmnSvc; /* Referral Service */
	private TaskSvc taskSvc; /* Task service */
	private CommonOpSvc commonOpSvc;

	public UWQASaveCommonSvc getUwqaSaveCommonSvc(){
		return uwqaSaveCommonSvc;
	}

	public void setUwqaSaveCommonSvc( UWQASaveCommonSvc uwqaSaveCommonSvc ){
		this.uwqaSaveCommonSvc = uwqaSaveCommonSvc;
	}

	public IBaseSaveOperation getBaseOperationDao(){
		return baseOperationDao;
	}

	public void setBaseOperationDao( IBaseSaveOperation baseOperationDao ){
		this.baseOperationDao = baseOperationDao;
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
	 * @return the travelServiceBean
	 */
	public TravelDetailSvc getTravelServiceBean(){
		return travelServiceBean;
	}

	/**
	 * @param travelServiceBean the travelServiceBean to set
	 */
	public void setTravelServiceBean( TravelDetailSvc travelServiceBean ){
		this.travelServiceBean = travelServiceBean;
	}

	private DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();

//	private CommonVO commonVO = new CommonVO();

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		CommonVO commonVO = null;
		if( args[ 0 ] instanceof PolicyDataVO ){
			commonVO = ( (PolicyDataVO) args[ 0 ] ).getCommonVO();
		}
		BaseVO returnValue = null;
		if( SvcConstants.SAVE_GEN_INFO.equals( methodName ) ){
			PolicyDataVO polData = (PolicyDataVO) (BaseVO) args[ 0 ];
			/** Capture referral related data if present */
			if (!Utils.isEmpty(polData.getReferralVOList())) {
				/* NOTE :::: 
				 * referralListVO and taskVO will be set to null
				 * in case if there is any exception occurred in 
				 * base save while saving policyData
				 */
				ReferralListVO referralListVO = polData.getReferralVOList();
				TaskVO taskVO = polData.getReferralVOList().getTaskVO();
				
				/** First general info related details will be saved */
				returnValue = saveGeneralInfo(polData, commonVO);
				
				/** Save referral related data now */
				polData = (PolicyDataVO) returnValue;
				/* Checking empty if some exception happened during BaseSave operation*/
				if (( !Utils.isEmpty(referralListVO) && 
						!RuleResponseType.Pass.toString().equalsIgnoreCase(referralListVO.getReferalType()) ) ) {
					savePasReferralDetails(polData, referralListVO, taskVO);
				}
			} else {
				returnValue = saveGeneralInfo( polData, commonVO);
			}
		}
		else if( SvcConstants.SAVE_INSURED.equals( methodName ) ){	// If any one wants to save insured alone	

			returnValue = saveInsured( (BaseVO) args[ 0 ] );

		}
		else if( SvcConstants.LOAD_GEN_INFO.equals( methodName ) ){
			returnValue = loadGeneralInfo( (BaseVO) args[ 0 ] );
		}else if( SvcConstants.SAVE_POLICY_DATA.equals(methodName)){
			returnValue = savePolicyData( (BaseVO) args[ 0 ], commonVO);
		}

		return returnValue;
	}

	/**
	 * @param baseVO
	 * @return BaseVO
	 * This method is use to save insured,Policy and customer details
	 */
	private BaseVO saveGeneralInfo( BaseVO baseVO, CommonVO commonVO){
		PolicyDataVO polData = (PolicyDataVO) baseVO;
		
		GeneralInfoCommonSvc generalInfoLoadSvc = null;
		
		
		/*
		 * Call the procedure for quote to update the effective date in all the tables.
		 */
		if(commonVO.getIsQuote()){
			if(!Utils.isEmpty( commonVO.getPolicyId() )){
				generalInfoLoadSvc = (GeneralInfoCommonSvc) Utils.getBean("commonGenSvcBean");
				PolicyDataVO existingPolicyData = (PolicyDataVO) generalInfoLoadSvc.invokeMethod(SvcConstants.LOAD_GEN_INFO, polData);
				
				if(!Utils.isEmpty( existingPolicyData ) && !Utils.isEmpty( existingPolicyData.getScheme()) 
						&& !Utils.isEmpty( existingPolicyData.getScheme().getEffDate() ) && !Utils.isEmpty( existingPolicyData.getScheme().getExpiryDate() ) ){
					if( ( !polData.getScheme().getEffDate().equals( existingPolicyData.getScheme().getEffDate() ) )
							|| ( !polData.getScheme().getExpiryDate().equals( existingPolicyData.getScheme().getExpiryDate() ) ) ){

						//if policy status is active,then create new endt id
						if( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_ACTIVE ) ) == polData.getCommonVO().getStatus() ){
							CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVC );
							polData = (PolicyDataVO) commonOpSvc.invokeMethod( com.Constant.CONST_GETNEXTENDORSEMENTID, polData );
						}
						
						if(commonVO.getLob().equals(LOB.HOME) || commonVO.getLob().equals(LOB.TRAVEL)){
							DAOUtils.callPolicyEffectiveDateUpdateProc( polData );
						}else{
							DAOUtils.callPolicyEffectiveDateUpdateProcMonoline( polData );
						}
						
						if( !polData.getCommonVO().getStatus().equals( SvcConstants.POL_STATUS_REFERRED ) ){
							polData.getCommonVO().setStatus( SvcConstants.POL_STATUS_PENDING );
							polData.setStatus( SvcConstants.POL_STATUS_PENDING );
						}
						
					}
					polData.setPolExpiryDate(polData.getScheme().getExpiryDate());
				}
				if(!Utils.isEmpty(existingPolicyData.getGeneralInfo().getAdditionalInfo())){
					if(Utils.isEmpty(polData.getGeneralInfo().getAdditionalInfo())){
						polData.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
					}
					polData.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(existingPolicyData.getGeneralInfo()
							.getAdditionalInfo().getAffinityRefNo());
				}
				if(commonVO.getLob().equals(LOB.TRAVEL) && BusinessChannel.B2C.equals(commonVO.getChannel())){
					polData.getGeneralInfo().setIntAccExecCode(existingPolicyData.getGeneralInfo().getIntAccExecCode());
					polData.getAuthenticationInfoVO().setIntAccExecCode(existingPolicyData.getAuthenticationInfoVO().getIntAccExecCode());
				}
			}
		}
		
		if(!Flow.CREATE_QUO.equals(polData.getCommonVO().getAppFlow())){
			generalInfoLoadSvc = polData.getCommonVO().getIsQuote()?(GeneralInfoCommonSvc) Utils.getBean("commonGenSvcBean"):(GeneralInfoCommonSvc) Utils.getBean("commonGenSvcBean_POL");
			PolicyDataVO existingPolicyData = (PolicyDataVO) generalInfoLoadSvc.invokeMethod(SvcConstants.LOAD_GEN_INFO, polData);
			if(LOB.HOME.equals(polData.getCommonVO().getLob()) || !LOB.TRAVEL.equals(polData.getCommonVO().getLob())){
				if((LOB.HOME.equals(polData.getCommonVO().getLob()) && !polData.getScheme().getTariffCode().equals(existingPolicyData.getScheme().getTariffCode())) && !polData.getScheme().getExpiryDate().equals(existingPolicyData.getScheme().getExpiryDate())){
					throw new BusinessException("pas.gi.exception", null, "Policy Entension and Tariff change not allowed at the same time");
				}
				else if(( !polData.getScheme().getTariffCode().equals(existingPolicyData.getScheme().getTariffCode()))
					&& ( !polData.getScheme().getExpiryDate().equals(existingPolicyData.getScheme().getExpiryDate())
					|| !polData.getScheme().getEffDate().equals(existingPolicyData.getScheme().getEffDate()))){
					throw new BusinessException("pas.gi.exception", null, "Policy Entension and Tariff change not allowed at the same time");
				}
				if(!polData.getScheme().getTariffCode().equals(existingPolicyData.getScheme().getTariffCode())){
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean(com.Constant.CONST_GECOMSVC);
					// Added equals() instead of == to avoid sonar violation on 25-9-2017
					if(Integer.valueOf(Utils.getSingleValueAppConfig(SvcConstants.QUOTE_ACTIVE)).equals( polData.getCommonVO().getStatus())){				
						polData = (PolicyDataVO) commonOpSvc.invokeMethod(com.Constant.CONST_GETNEXTENDORSEMENTID, polData);
					}			
					commonOpSvc.invokeMethod("callTariffChangeProcedure", polData);

					if( !commonVO.getStatus().equals( SvcConstants.POL_STATUS_ACCEPT ) && !commonVO.getStatus().equals( SvcConstants.POL_STATUS_REFERRED )
							&& !commonVO.getStatus().equals( SvcConstants.POL_STATUS_DECLINED ) ){
						polData.getCommonVO().setStatus(SvcConstants.POL_STATUS_PENDING);
						polData.setStatus(SvcConstants.POL_STATUS_PENDING);	
					}

				}
			}
			/**
			 * Start code change for  adventnet ID - 104073 
			 * Added the below code and condition for the adventnet ID - 104073 
			 */
			String userRoleType = ((UserProfile)polData.getCommonVO().getLoggedInUser()).getRsaUser().getProfile();
			if(!Utils.isEmpty(userRoleType) && userRoleType.equalsIgnoreCase( "BROKER" ))
			{
				polData.getGeneralInfo().setIntAccExecCode(existingPolicyData.getGeneralInfo().getIntAccExecCode());
				polData.getAuthenticationInfoVO().setIntAccExecCode(existingPolicyData.getAuthenticationInfoVO().getIntAccExecCode());
			}		
			/**
			 * End of code change for  adventnet ID - 104073
			 */
		
		// Policy Extension changes
		if (Flow.AMEND_POL.equals(polData.getAppFlow())) {
			if (!polData.getScheme().getExpiryDate().equals(existingPolicyData.getScheme().getExpiryDate())) {
				/// call policy extension procedure
				
				//if policy status is active,then create new endt id
				// Added equals() instead of == to avoid sonar violation on 26-9-2017
				if(Integer.valueOf(Utils.getSingleValueAppConfig(SvcConstants.QUOTE_ACTIVE)).equals( polData.getCommonVO().getStatus())){
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean(com.Constant.CONST_GECOMSVC);
					polData = (PolicyDataVO) commonOpSvc.invokeMethod(com.Constant.CONST_GETNEXTENDORSEMENTID, polData);
					}				
					DAOUtils.callPolicyExtensionProcedure(polData);
					polData.getCommonVO().setStatus(SvcConstants.POL_STATUS_PENDING);
					polData.setStatus(SvcConstants.POL_STATUS_PENDING);
					polData.setPolicyExtended(true);
					
				}
			polData.setPolExpiryDate(polData.getScheme().getExpiryDate());
			}
		
		//CTS 29.07.2020 CR#1886 - VAT not applied for the travel policies - Start
		
		
		String LOB =commonVO.getLob().toString();
		String appFlows = commonVO.getAppFlow().toString();
				
		if(LOB.equals("TRAVEL") && 
				appFlows.equalsIgnoreCase("EDIT_QUO") 
				&& commonVO.getIsQuote() 
				&& polData.getPremiumVO()==null && 
				existingPolicyData.getPremiumVO()!=null)
		{
		PremiumVO premiumObj = existingPolicyData.getPremiumVO();
		polData.setPremiumVO(premiumObj);
		polData.getPremiumVO().setPremiumAmt(SvcConstants.zeroVal);
		}
		}
		
		//CTS 29.07.2020 CR#1886 - VAT not applied for the travel policies - End
		
		
		
		if( polData.getGeneralInfo().getInsured().getUpdateMaster() ){
			polData = (PolicyDataVO) saveInsured( polData );
		}
		return savePolicyData( polData, commonVO );
	}

	/**
	 * @param baseVo
	 * BaseVO
	 * Call the common service and save/update the insured details
	 */
	private BaseVO saveInsured( BaseVO baseVo ){
		PolicyDataVO polData = (PolicyDataVO) baseVo;
		InsuredDetailsSvc insuredSvc = (InsuredDetailsSvc) Utils.getBean( "insuredDetailsSvc" );
		PolicyDataVO policyDataVO = (PolicyDataVO) insuredSvc.invokeMethod( SvcConstants.SAVE_INSURED, polData );
		if( !Utils.isEmpty( polData.getGeneralInfo().getInsured().getFirstName() ) ){ // This check is put because first name will be null in B2C.
			CacheManagerFactory.getCacheManager().put( PASCache.INSURED, polData.getGeneralInfo().getInsured().getInsuredCode().toString(),polData.getGeneralInfo().getInsured().getFirstName().toString() );
		}
		return policyDataVO;
	}

	/**
	 * @param baseVo
	 * @return BaseVO
	 * Call the BaseSaveOperation to save the policy and cash customer details for Home/Travel LOB
	 * If Travel LOB also store the travel details also to GACC person table
	 */
	private BaseVO savePolicyData( BaseVO baseVo, CommonVO commonVO ){
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();
		PolicyDataVO polDataVo = (PolicyDataVO) baseVo;

		polDataVo.setCommonVO( commonVO );
		// For Policy data save
		TableData<PolicyDataVO> polTableData = new TableData<PolicyDataVO>();
		polTableData.setTableData( (PolicyDataVO) baseVo );
		List<TableData> polList = new ArrayList<TableData>();
		polList.add( polTableData );

		// For Cash customer save
		TableData<GeneralInfoVO> genInfoData = new TableData<GeneralInfoVO>();
		genInfoData.setTableData( polDataVo.getGeneralInfo() );
		List<TableData> genInfo = new ArrayList<TableData>();
		genInfo.add( genInfoData );
		dataMap.put( SvcConstants.T_TRN_POLICY, polList );
		dataMap.put( SvcConstants.SAVE_CASH_CUST_DATA, genInfo );
		toBeSaved.setData( dataMap );
		BaseVO polResultVo = baseOperationDao.executeSave( toBeSaved, commonVO );

		/*
		 * Policy Id and and Endorsement Id need to be set to Policy Data VO to avoid redundant policy Id generation
		 */

		//polDataVo.setPolicyId( polResultVo. )

		DataHolderVO<LinkedHashMap<String, List<TableData>>> result = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) polResultVo;

		LinkedHashMap<String, List<TableData>> tableData = result.getData();
		List<TableData> polListRetrieved = tableData.get( SvcConstants.T_TRN_POLICY );

		TableData<PolicyDataVO> policyTableDataVORetrieved = polListRetrieved.get( 0 );

		PolicyDataVO policyDataRetrieved = policyTableDataVORetrieved.getTableData();
		//commonVO.setPolicyId( policyDataRetrieved.getPolicyId() );
		//commonVO.setVsd( policyDataRetrieved )
		policyDataRetrieved.setCommonVO( commonVO );
		
		if( policyDataRetrieved instanceof TravelInsuranceVO ){

			travelServiceBean.invokeMethod( SvcConstants.SAVE_GACC_PERSON, policyDataRetrieved );
			uwqaSaveCommonSvc.invokeMethod( SvcConstants.SAVE_UW_QUES_ANS, policyDataRetrieved, policyDataRetrieved );
		}
		/*else{
			//Setting Domestic Staff Details to tTrnGaccPerson
			List<TableData> gaccTableDataList = new ArrayList<TableData>( 0 );
			for( StaffDetailsVO staffDetailsVO : ( ( HomeInsuranceVO)policyDataRetrieved ).getStaffDetails() ){
				
				if(!Utils.isEmpty( staffDetailsVO.getEmpName()) && !Utils.isEmpty( staffDetailsVO.getEmpDob() )){
					Integer basicRskCode = null;
					BigDecimal basicRskId = null;
					Long rtCode = null;
					Integer rskCode = null;
					Integer riRskCode = null;
					Integer rcCode = null;
					for( CoverDetailsVO coverDetailsVO : ( ( HomeInsuranceVO)policyDataRetrieved ).getCovers() ){
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
					tTrnGaccPersonVOHolder.setGprPolicyId( ( ( HomeInsuranceVO)policyDataRetrieved ).getCommonVO().getPolicyId() );
					tTrnGaccPersonVOHolder.setGprId((long) staffDetailsVO.getEmpId());
					
					tTrnGaccPersonVOHolder.setGprEName( staffDetailsVO.getEmpName() );
					tTrnGaccPersonVOHolder.setGprDateOfBirth( staffDetailsVO.getEmpDob() );
					tTrnGaccPersonVOHolder.setGprRskCode( Long.valueOf( rskCode ) );
					tTrnGaccPersonVOHolder.setGprRcCode( Long.valueOf( rcCode ) );
					tTrnGaccPersonVOHolder.setGprRtCode( rtCode );
					tTrnGaccPersonVOHolder.setGprStatus( Byte.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_PENDING ) ) );
					tTrnGaccPersonVOHolder.setGprValidityExpiryDate( SvcConstants.EXP_DATE );
					tTrnGaccPersonVOHolder.setGprEndtId( ( ( HomeInsuranceVO)policyDataRetrieved ).getCommonVO().getEndtId() );
					tTrnGaccPersonVOHolder.setGprRiRskCode( riRskCode );
					tTrnGaccPersonVOHolder.setGprBasicRskCode( basicRskCode );
					tTrnGaccPersonVOHolder.setGprBasicRiskId( Utils.isEmpty( basicRskId )?0:basicRskId.longValue() );
					tTrnGaccPersonVOHolder.setGprPreparedBy( SvcUtils.getUserId( ( ( HomeInsuranceVO)policyDataRetrieved ) ) );
					tTrnGaccPersonVOHolder.setGprPreparedDt( ( ( HomeInsuranceVO)policyDataRetrieved ).getCreatedOn() );
					tTrnGaccPersonVOHolder.setGprStartDate( ( ( HomeInsuranceVO)policyDataRetrieved ).getScheme().getEffDate() );
					tTrnGaccPersonVOHolder.setGprEndDate( ( ( HomeInsuranceVO)policyDataRetrieved ).getScheme().getExpiryDate() );
					tTrnGaccPersonVOHolder.setGprValidityStartDate(staffDetailsVO.getEmpVsd());
					gaccTableData.setTableData( tTrnGaccPersonVOHolder );
	
					gaccTableDataList.add( gaccTableData );
				}

			}
			
			 * Fetch the old gacc Person details 
			 
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

			DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> oldDataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", loadDataInputVO, OldDataMap );
			List<TableData<BaseVO>> polGaccData = oldDataHolder.getData().get( SvcConstants.T_TRN_GACC_PERSON );

			if( !Utils.isEmpty( polGaccData ) ){
				for( TableData<BaseVO> gaccData : polGaccData ){
					TTrnGaccPersonVOHolder trnGaccPersonVOHolder = (TTrnGaccPersonVOHolder) gaccData.getTableData();
					boolean toDelete = true;
					for( StaffDetailsVO staffDetailsVO : ( ( HomeInsuranceVO)policyDataRetrieved ).getStaffDetails() ){
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
		}*/
		
		return policyDataRetrieved;
	}

	/**
	 * 
	 * @param baseVO
	 * @return
	 * 
	 * Call BaseLoadOperation to get the data from policy and cash customer details table
	 * If the request is for travel get the details from GACC PERSON table
	 */
	@SuppressWarnings( "rawtypes" )
	private BaseVO loadGeneralInfo( BaseVO baseVO ){
		logger.info("Entered GeneralInfoCommonSvc.loadGeneralInfo method.");
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();

		PolicyDataVO policyDataVO = null;
		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		GeneralInfoVO generalInfoVO = null;

		/* Set the map based on the VO type */
		if( !Utils.isEmpty( baseVO ) ){
			if( baseVO instanceof TravelInsuranceVO ){
				policyDataVO = (TravelInsuranceVO) baseVO;
				dataMap.put( SvcConstants.T_TRN_POLICY, TravelInsuranceVO.class );
			}
			else if( baseVO instanceof HomeInsuranceVO ){
				policyDataVO = (HomeInsuranceVO) baseVO;
				dataMap.put( SvcConstants.T_TRN_POLICY, HomeInsuranceVO.class );
			}
			else
			{
				policyDataVO =  (PolicyDataVO)baseVO;
				dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
			}

			CommonVO commonVO = null;
			if( !Utils.isEmpty( policyDataVO.getCommonVO() ) ){
				commonVO = policyDataVO.getCommonVO();
			}
			if(!Utils.isEmpty( commonVO ))
			loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
			if(!Utils.isEmpty( commonVO )){				/* Added if condition for commonVO null check - sonar violation fix */
			if( commonVO.getIsQuote() ){
				loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
			}
			else{
				loadDataInputVO.setPolicyNo( commonVO.getPolicyNo() );
			}

			loadDataInputVO.setEndtId( commonVO.getEndtId() );
			loadDataInputVO.setLocCode( commonVO.getLocCode() );
			loadDataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
			loadDataInputVO.setDocCode( commonVO.getDocCode() );
			}
			dataMap.put( SvcConstants.SAVE_CASH_CUST_DATA, GeneralInfoVO.class );
			
			logger.info("Before calling baseLoadSvc.invokeMethod method");
			@SuppressWarnings( "unchecked" )
			DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", loadDataInputVO, dataMap );
			List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
			List<TableData<BaseVO>> genTableData = dataHolder.getData().get( SvcConstants.SAVE_CASH_CUST_DATA );

			/* Get the general info vo obtained from the T_MAS_CASH_CUSTOMER Table */
			if( !Utils.isEmpty( genTableData.get( 0 ) ) ){
				generalInfoVO = (GeneralInfoVO) genTableData.get( 0 ).getTableData();
			}

			/* Get the policy data vo obtained from T_TRN_POLICY Table*/
			if( !Utils.isEmpty( polTableData ) && !Utils.isEmpty( polTableData.get( 0 ) ) ){
				if( policyDataVO instanceof TravelInsuranceVO && polTableData.get( 0 ).getTableData() instanceof TravelInsuranceVO){
					policyDataVO = (TravelInsuranceVO) polTableData.get( 0 ).getTableData();

					/* Get the general info vo obtained from T_TRN_POLICY table */
					if( !Utils.isEmpty( policyDataVO ) ){
						GeneralInfoVO genInfo = policyDataVO.getGeneralInfo();

						/* Get the source of business from policy table and set it to source of business from t mas cash customer */
						if( !Utils.isEmpty( generalInfoVO ) && !Utils.isEmpty( genInfo ) && !Utils.isEmpty( genInfo.getSourceOfBus() )
								&& !Utils.isEmpty( generalInfoVO.getSourceOfBus() ) ){
							generalInfoVO.getSourceOfBus().setBrokerName( genInfo.getSourceOfBus().getBrokerName() );
							generalInfoVO.getSourceOfBus().setDistributionChannel( genInfo.getSourceOfBus().getDistributionChannel() );
							generalInfoVO.getSourceOfBus().setDirectSubAgent( genInfo.getSourceOfBus().getDirectSubAgent() );
							generalInfoVO.getSourceOfBus().setPromoCode( genInfo.getSourceOfBus().getPromoCode() );
							generalInfoVO.getSourceOfBus().setPartnerName(genInfo.getAdditionalInfo().getAffinityRefNo());
							generalInfoVO.getInsured().setInsuredCode( genInfo.getInsured().getInsuredCode() );
							generalInfoVO.getInsured().setInsuredId( genInfo.getInsured().getInsuredId() );
							
							if(!Utils.isEmpty(genInfo.getInsured().getVatRegNo())) {
								generalInfoVO.getInsured().setVatRegNo(genInfo.getInsured().getVatRegNo() );	//142244 Vat Regn No
							}
							
							/*Added PolBussiness type - To fix Transaction search for pending versioned quote issue */ 
							generalInfoVO.getInsured().setPolBusType(genInfo.getInsured().getPolBusType());	
							if( Utils.isEmpty( generalInfoVO.getSourceOfBus().getBrokerName() ) && Utils.isEmpty( generalInfoVO.getSourceOfBus().getDirectSubAgent() ) ){
								generalInfoVO.getSourceOfBus().setBrokerName( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_USER_END+generalInfoVO.getSourceOfBus().getDistributionChannel() ) ));

							}
							generalInfoVO.getClaimsHistory().setSourceOfBusiness( genInfo.getClaimsHistory().getSourceOfBusiness() );
							if(SvcConstants.OMAN.toString().equalsIgnoreCase(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION)))
							{
								generalInfoVO.getAdditionalInfo().setProcessingLoc( genInfo.getAdditionalInfo().getProcessingLoc() );
							}
							policyDataVO.setGeneralInfo( generalInfoVO );
						}
						commonVO.setConcatPolicyNo(policyDataVO.getCommonVO().getConcatPolicyNo());
						policyDataVO.setCommonVO( commonVO );
					}
					
					logger.info("Before calling TravelDetailSvc.loadTravelDetailSection method.");
					/* Get the data from T_TRN_GACC_PERSON table for travel */
					policyDataVO = (TravelInsuranceVO) travelServiceBean.invokeMethod( SvcConstants.LOAD_GACC_PERSON, policyDataVO );
				}
				else if( policyDataVO instanceof HomeInsuranceVO && polTableData.get( 0 ).getTableData() instanceof HomeInsuranceVO){
					policyDataVO = (HomeInsuranceVO) polTableData.get( 0 ).getTableData();

					/* Get the general info vo obtained from T_TRN_POLICY table */
					if( !Utils.isEmpty( policyDataVO ) ){
						GeneralInfoVO genInfo = policyDataVO.getGeneralInfo();

						/* Get the source of business from policy table and set it to source of business from t mas cash customer */
						if( !Utils.isEmpty( generalInfoVO ) && !Utils.isEmpty( genInfo ) && !Utils.isEmpty( genInfo.getSourceOfBus() )
								&& !Utils.isEmpty( generalInfoVO.getSourceOfBus() ) ){
							generalInfoVO.getSourceOfBus().setBrokerName( genInfo.getSourceOfBus().getBrokerName() );
							generalInfoVO.getSourceOfBus().setDistributionChannel( genInfo.getSourceOfBus().getDistributionChannel() );
							generalInfoVO.getSourceOfBus().setDirectSubAgent( genInfo.getSourceOfBus().getDirectSubAgent() );
							generalInfoVO.getSourceOfBus().setPromoCode( genInfo.getSourceOfBus().getPromoCode() );
							generalInfoVO.getSourceOfBus().setPartnerName(genInfo.getAdditionalInfo().getAffinityRefNo());
							generalInfoVO.getInsured().setInsuredCode( genInfo.getInsured().getInsuredCode() );
							generalInfoVO.getInsured().setInsuredId( genInfo.getInsured().getInsuredId() );	
							if(!Utils.isEmpty(genInfo.getInsured().getVatRegNo())){
							generalInfoVO.getInsured().setVatRegNo(genInfo.getInsured().getVatRegNo() );	//142244 Vat Regn No
							}
							/*Added PolBussiness type - To fix Transaction search for pending versioned quote issue */ 
							generalInfoVO.getInsured().setPolBusType(genInfo.getInsured().getPolBusType());	
							if( Utils.isEmpty( generalInfoVO.getSourceOfBus().getBrokerName() ) && Utils.isEmpty( generalInfoVO.getSourceOfBus().getDirectSubAgent())){
								generalInfoVO.getSourceOfBus().setBrokerName( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_USER_END+generalInfoVO.getSourceOfBus().getDistributionChannel() ) ));

							}
							generalInfoVO.getClaimsHistory().setSourceOfBusiness( genInfo.getClaimsHistory().getSourceOfBusiness() );
							policyDataVO.setGeneralInfo( generalInfoVO );
						}
						policyDataVO.setCommonVO( commonVO );
						
						logger.info("Before calling GeneralInfoCommonSvc.loadDomesticStaff method.");
						/* Get the data from T_TRN_GACC_PERSON table for travel */
						policyDataVO = (PolicyDataVO) loadDomesticStaff( policyDataVO );
					}
				}
				else
				{
					logger.debug("Inside else of monoline implementation");
					policyDataVO = (PolicyDataVO) polTableData.get( 0 ).getTableData();

					/* Get the general info vo obtained from T_TRN_POLICY table */
					if( !Utils.isEmpty( policyDataVO ) ){
						GeneralInfoVO genInfo = policyDataVO.getGeneralInfo();
						policyDataVO.setCommonVO( commonVO );
						
						
						/* Get the source of business from policy table and set it to source of business from t mas cash customer */
						if( !Utils.isEmpty( generalInfoVO ) && !Utils.isEmpty( genInfo ) && !Utils.isEmpty( genInfo.getSourceOfBus() )
								&& !Utils.isEmpty( generalInfoVO.getSourceOfBus() ) ){
							generalInfoVO.getSourceOfBus().setBrokerName( genInfo.getSourceOfBus().getBrokerName() );
							generalInfoVO.getSourceOfBus().setDistributionChannel( genInfo.getSourceOfBus().getDistributionChannel() );
							generalInfoVO.getSourceOfBus().setDirectSubAgent( genInfo.getSourceOfBus().getDirectSubAgent() );
							generalInfoVO.getSourceOfBus().setPromoCode( genInfo.getSourceOfBus().getPromoCode() );
							generalInfoVO.getInsured().setInsuredCode( genInfo.getInsured().getInsuredCode() );
							generalInfoVO.getInsured().setInsuredId( genInfo.getInsured().getInsuredId() );
							generalInfoVO.getInsured().setPolBusType(genInfo.getInsured().getPolBusType());							
							if( Utils.isEmpty( generalInfoVO.getSourceOfBus().getBrokerName() ) && Utils.isEmpty( generalInfoVO.getSourceOfBus().getDirectSubAgent())){
								generalInfoVO.getSourceOfBus().setBrokerName( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_USER_END+generalInfoVO.getSourceOfBus().getDistributionChannel() ) ));

							}
							generalInfoVO.getClaimsHistory().setSourceOfBusiness( genInfo.getClaimsHistory().getSourceOfBusiness() );
							policyDataVO.setGeneralInfo( generalInfoVO );
							PolicyDataVO policyDataCopy = CopyUtils.copySerializableObject(policyDataVO);
							policyDataCopy.getGeneralInfo().getInsured().setInsuredId(genInfo.getInsured().getInsuredCode());
							if(!Utils.isEmpty(genInfo.getInsured().getVatRegNo())){
								generalInfoVO.getInsured().setVatRegNo(genInfo.getInsured().getVatRegNo() );	//Added for AdventId:142244 - WC
								}
							logger.info("Calling InsuredDetailsSvc.fetchCommonTmasInsured method");
							TaskExecutor.executeTasks("VIEW_INSURED_DETAILS_COMMON",policyDataCopy);
							policyDataVO.getGeneralInfo().getInsured().setTurnover(policyDataCopy.getGeneralInfo().getInsured().getTurnover());
							
							if (commonVO.getLob().equals(LOB.WC)) {
								Integer brkCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
								if (!Utils.isEmpty(brkCode)) {

									java.util.List<Object> valueHolder = DAOUtils
											.getSqlResultSingleColumnPas(QueryConstants.GET_BROKER_ACC_STATUS, brkCode);
									BigDecimal bkrStatus = null;
									if (!Utils.isEmpty(valueHolder) && valueHolder.size() > 0
											&& !Utils.isEmpty(valueHolder.get(0))) {
										bkrStatus = ((BigDecimal) valueHolder.get(0));
									}
									if (!Utils.isEmpty(bkrStatus) && bkrStatus.compareTo(BigDecimal.ZERO) == 0) {
										throw new BusinessException("cmn.brkblocked.cl", null,
												"The Brk account is blocked");
									}
								}
							}
							
						}
						
					}
				}
			}
		}
		
		logger.info("Exiting GeneralInfoCommonSvc.loadGeneralInfo method.");
		//commonVO.setVsd( (Date) ThreadLevelContext.get( SvcConstants.TLC_KEY_VSD ) );
		return policyDataVO;
	}
	
	/**
	 * Method to call PasReferralSaveCommonSvc to save referred fields
	 * and corresponding values along with referral comments generated from rule
	 * engine for the corresponding field
	 * 
	 * @param polData
	 * @param referralListVO
	 */
	private void savePasReferralDetails(PolicyDataVO polData, ReferralListVO referralListVO, TaskVO taskVO) {
		logger.debug("GeneralInfoCommonSvc -----> Going to save referral related data in TTrnPasReferral");
		if (!Utils.isEmpty(polData) && !Utils.isEmpty(referralListVO)) {
			polData.setReferralVOList(referralListVO);
			logger.debug("GeneralInfoCommonSvc -----> Going to call service to save referral data in TTrnPasReferral");
			/** Service call for persist the data in T_TRN_PAS_REFERRAL_DETAILS */
			TTrnPasReferralDetails pasReferralDetails = (TTrnPasReferralDetails) pasReferralSaveCmnSvc.invokeMethod("saveReferralData", polData);
			if (!Utils.isEmpty(pasReferralDetails)) {
				logger.debug("GeneralInfoCommonSvc -----> Refferal details has been saved into TTrnPasReferralDetails table in database with policyLinkingId as "+pasReferralDetails.getId().getPolLinkingId());
				/* Start saving the TTrnTask table related data in case of CONSOLIDATED REFERRAL */
				logger.debug("GeneralInfoCommonSvc -----> Going to make entry in TTrnTask");
				/** If this flag is set save consolidated referral */
				if (referralListVO.getReferrals().get(0).isConsolidated() && !Utils.isEmpty(taskVO.getAssignedTo())) {
					taskVO = populateReferralTaskDets(polData, taskVO);
					logger.debug("GeneralInfoCommonSvc -----> Going to save TTrnTask table related data");
					taskSvc.invokeMethod("saveRefferalTask", taskVO);
					
					DataHolderVO<Object[]> dataHolderVO = new DataHolderVO<Object[]>();
					Object[] data = {polData,"REFERRAL_MAIL_TRIGGER"};
					dataHolderVO.setData( data );
					commonOpSvc.invokeMethod( "sendReferralMail", dataHolderVO );
				}
			}
		}
	}
	
	/**
	 * This method will be always called in case of consolidated
	 * referrals
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private TaskVO populateReferralTaskDets(PolicyDataVO policyDataVO, TaskVO taskVO) {
		/* To populate the messages corresponding to T_TRN_PAS_REFERRAL_DETAILS */
		List<TTrnPasReferralDetails> referralsList = DAOUtils.getReferralDetails( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId() );
		if (!Utils.isEmpty(referralsList)) {
			StringBuilder messageBuilder = new StringBuilder();
			/* Populating the message from T_TRN_PAS_REFERRAL_DETAILS */
			int counter = 0;
			for (TTrnPasReferralDetails pasReferralDetails : referralsList) {
				if (counter == 0) {
					messageBuilder.append(pasReferralDetails.getRefText());
				} else {
					messageBuilder.append("/n").append(pasReferralDetails.getRefText());
				}
			}
			taskVO.setPolicyType(String.valueOf(policyDataVO.getPolicyType()));
			taskVO.setPolLinkingId(policyDataVO.getPolicyId());
			taskVO.setDesc(messageBuilder.toString());
			if (!Utils.isEmpty(policyDataVO.getCommonVO().getEndtId())) {
				taskVO.setPolEndId(policyDataVO.getCommonVO().getEndtId());
			} else {
				taskVO.setPolEndId(Long.valueOf( 0 )); // Radar fix
			}
			if(!Utils.isEmpty(policyDataVO.getCommonVO()) && policyDataVO.getCommonVO().getIsQuote()){
				taskVO.setTaskType(Utils.getSingleValueAppConfig("QUOTE_REFRRAL_TASK_TYPE"));
				taskVO.setQuote(true);
				taskVO.setQuoteNo(policyDataVO.getCommonVO().getQuoteNo());
				taskVO.setTaskName("Transaction "+policyDataVO.getCommonVO().getQuoteNo()+" is referred.");
			} else {
				taskVO.setTaskType(Utils.getSingleValueAppConfig("POLICY_REFRRAL_TASK_TYPE"));
				taskVO.setQuote(false);
				taskVO.setPolicyNo(policyDataVO.getCommonVO().getPolicyNo());
				taskVO.setTaskName("Transaction "+policyDataVO.getCommonVO().getPolicyNo()+" is referred.");
			}
			taskVO.setPriority(Utils.getSingleValueAppConfig("DEFAULT_TASK_PRIORITY"));
			taskVO.setClassCode(Byte.valueOf(Utils.getSingleValueAppConfig("TRAVEL_CLASS_CODE")));
			taskVO.setCategory(String.valueOf(policyDataVO.getPolicyType()));
			taskVO.setIsOpen(true); /* Default value while assigning */
			taskVO.setLob(policyDataVO.getCommonVO().getLob().toString());
		}
		return taskVO;	
	}
	/**
	 * This method is to get the travel section from T_TRN_GACC_PERSON table by calling executeLoad method of BaseLoadOperation
	 * 
	 * @param travelDetailVo
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	private BaseVO loadDomesticStaff( BaseVO baseVO ){
		logger.info("Entered GeneralInfoCommonSvc.loadDomesticStaff method.");
		/*
		 * Start - Fetch the gacc Person details 
		 */
		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		CommonVO commonVO = policyDataVO.getCommonVO();
		LoadDataInputVO dataInputVO = new LoadDataInputVO();
		dataInputVO.setIsQuote( commonVO.getIsQuote() );
		if(commonVO.getIsQuote()){
			dataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		}
		else{
			dataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		}
		dataInputVO.setEndtId( commonVO.getEndtId() );
		dataInputVO.setLocCode( commonVO.getLocCode() );
		dataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		dataInputVO.setDocCode( commonVO.getDocCode() );
		
		Map<String, Class<? extends BaseVO>> OldDataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();

		OldDataMap.put( SvcConstants.T_TRN_GACC_PERSON, TTrnGaccPersonVOHolder.class );
		
		logger.info("GeneralInfoCommonSvc.loadDomesticStaff method, invoking BaseLoadSvc.invokeMethod");
		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> oldDataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", dataInputVO, OldDataMap );
		List<TableData<BaseVO>> polTableData = oldDataHolder.getData().get( SvcConstants.T_TRN_GACC_PERSON );
		List<StaffDetailsVO> staffDetails = new ArrayList<StaffDetailsVO>( 0 );
		if( !Utils.isEmpty( polTableData ) ){
			for( TableData<BaseVO> tableData : polTableData ){
				TTrnGaccPersonVOHolder trnGaccPersonVOHolder = (TTrnGaccPersonVOHolder) tableData.getTableData();
				StaffDetailsVO staffDetailsVO = new StaffDetailsVO();
				staffDetailsVO.setEmpId(trnGaccPersonVOHolder.getGprId());
				staffDetailsVO.setEmpName(trnGaccPersonVOHolder.getGprEName());
				staffDetailsVO.setEmpDob(trnGaccPersonVOHolder.getGprDateOfBirth());
				staffDetailsVO.setEmpVsd(trnGaccPersonVOHolder.getGprValidityStartDate());
				staffDetails.add(staffDetailsVO);
				
			}

		}
		homeInsuranceVO.setStaffDetails(staffDetails);
		/*
		 * End - Fetch the gacc Person details 
		 */
		logger.info("Exiting GeneralInfoCommonSvc.loadDomesticStaff method.");
		return homeInsuranceVO;
	}

	/**
	 * @return the pasReferralSaveCmnSvc
	 */
	public PasReferralSaveCommonSvc getPasReferralSaveCmnSvc() {
		return pasReferralSaveCmnSvc;
	}

	/**
	 * @param pasReferralSaveCmnSvc the pasReferralSaveCmnSvc to set
	 */
	public void setPasReferralSaveCmnSvc(
			PasReferralSaveCommonSvc pasReferralSaveCmnSvc) {
		this.pasReferralSaveCmnSvc = pasReferralSaveCmnSvc;
	}

	/**
	 * @return the taskSvc
	 */
	public TaskSvc getTaskSvc() {
		return taskSvc;
	}

	/**
	 * @param taskSvc the taskSvc to set
	 */
	public void setTaskSvc(TaskSvc taskSvc) {
		this.taskSvc = taskSvc;
	}

	/**
	 * @return the commonOpSvc
	 */
	public CommonOpSvc getCommonOpSvc(){
		return commonOpSvc;
	}

	/**
	 * @param commonOpSvc the commonOpSvc to set
	 */
	public void setCommonOpSvc( CommonOpSvc commonOpSvc ){
		this.commonOpSvc = commonOpSvc;
	}

}
