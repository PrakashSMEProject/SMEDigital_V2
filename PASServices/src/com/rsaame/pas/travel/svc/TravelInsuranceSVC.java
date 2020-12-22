/**
 * 
 */
package com.rsaame.pas.travel.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.LocationHandler;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TMasPartnerMgmt;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.tasks.svc.TaskSvc;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1012799
 *
 */
public class TravelInsuranceSVC extends BaseService{

	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger.getLogger( TravelInsuranceSVC.class );
	private PremiumSaveCommonSvc premiumSvc;
	private PasReferralSaveCommonSvc pasReferralSaveCmnSvc; /* Referral Service */
	private TaskSvc taskSvc; /* Task service */
	private CommonOpSvc commonOpSvc;
	
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;

		if (SvcConstants.SAVE_TRAVEL_INSURANCE.equals(methodName)) {
			returnValue = saveTravelInsurance((BaseVO) args[0]);
		}else if( SvcConstants.SAVE_RENEWAL_REFERRAL.equals( methodName ) ){
			returnValue = saveRenewalReferal( (BaseVO) args[ 0 ] );
		}
		else if(SvcConstants.LOAD_PARTNER_MGMT.equals(methodName)){
			returnValue =  loadPartnerMgmt((BaseVO) args[ 0 ] );
	
		} else if( SvcConstants.LOAD_TRAVEL_VAT_RATE_AND_CODE.equals( methodName ) ){
			returnValue = fetchVatRateForVatCode( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	/**
	 * Method to call the premium service
	 * @param baseVO
	 * @return
	 */
	private BaseVO saveTravelInsurance( BaseVO baseVO ){
		
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO)premiumSvc.invokeMethod( SvcConstants.SAVE_TRAVEL_PREMIUM, baseVO );
		
		DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) baseVO;

		TravelInsuranceVO taskTravelInsuranceVO = (TravelInsuranceVO) dataHolder.getData()[ 0 ];
		Boolean isPopulateOperation = (Boolean) dataHolder.getData()[ 1 ];
		CommonVO commonVO = taskTravelInsuranceVO.getCommonVO();
		
		
		if((!Utils.isEmpty( taskTravelInsuranceVO ) && !Utils.isEmpty( taskTravelInsuranceVO.getReferralVOList()))){
			saveReferralMessage( taskTravelInsuranceVO);
			taskTravelInsuranceVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_REFERRED );
		}
		
		
		if( !isPopulateOperation ){
			if( commonVO.getIsQuote() ){
				PASStoredProcedure sp = null;
				sp = (PASStoredProcedure) Utils.getBean( "commonUpdPrmQuoEndt" );
				if( LOGGER.isInfo() ) LOGGER.info( "Invoking COMMON_UPDATE_PREMIUM_QUOTE_ENDT procedure with inputs {[" );
				sp.call( commonVO.getPolicyId(), commonVO.getEndtId(), Integer.valueOf(Utils.getSingleValueAppConfig( commonVO.getLob()+"_CLASS_CODE" ) ) );
			}
			else{
				PASStoredProcedure sp = null;
				sp = (PASStoredProcedure) Utils.getBean( "commonUpdPrmPolEndt" );
				if( LOGGER.isInfo() ) LOGGER.info( "Invoking COMMON_UPDATE_PREMIUM_POILCY_ENDT procedure with inputs {[" );
				sp.call( commonVO.getPolicyId(), commonVO.getEndtId(), Integer.valueOf(Utils.getSingleValueAppConfig( commonVO.getLob()+"_CLASS_CODE" ) ) );
			}
		}
		if(!isPopulateOperation){
			if( commonVO.getIsQuote() ){
				LOGGER.info( "Travel Issue Quote Procedure called" );
				DAOUtils.callUpdateStatusProcedureForHomeTravel( travelInsuranceVO );
				LOGGER.info( "Travel Issue Quote Procedure executed successfully" );
				commonVO.setStatus( SvcConstants.POL_STATUS_ACTIVE );
				
				if(!Utils.isEmpty( travelInsuranceVO.getCommonVO() ) && !Utils.isEmpty(  travelInsuranceVO.getCommonVO().getVsd()  )){
					commonVO.setVsd( travelInsuranceVO.getCommonVO().getVsd() );
				}
			}
		}
		
		// For updating vattax for renewal quote
		int distributionChannel = travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().intValue();
		
		
		/* Added for Batch Renewal scopedTarget.location Exception*/
		 
		String deployedLocation = ServiceContext.getLocation();
		//Added to fix Bug 15270
				if(Utils.isEmpty(deployedLocation)) {
					deployedLocation=Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION);
				}
		LOGGER.info("Deployed location for Travel LOB is : "
				+ deployedLocation);

	
		/*try{
			deployedLocation = location.getLocation();
		}catch (IllegalStateException ile) {
			// TODO: handle exception
			//ile.printStackTrace();
			deployedLocation = ServiceContext.getLocation();
			LOGGER.debug("This is the IllegalStateException exception lock in the Utils class. The location is "+deployedLocation);
		}
		catch (Exception e) {			
			deployedLocation = ServiceContext.getLocation();
			LOGGER.debug("This is the common exception lock in the Utils class. The location is "+deployedLocation +"   -:"+e.getMessage());
		}*/
			
			
		/*LocationHandler locationHandler = (LocationHandler) Utils.getBean("locationHandler");
		if(!Utils.isEmpty(locationHandler.getLocation())){
			deployedLocation = locationHandler.getLocation();	
		}
		else if (!Utils.isEmpty( ServiceContext.getLocation() )){
			deployedLocation =  ServiceContext.getLocation() ;
			System.out.println("The location coming from service context"+deployedLocation);
		}*/
	
		
		
		if( (distributionChannel == SvcConstants.DIST_CHANNEL_DIRECT || distributionChannel == SvcConstants.DIST_CHANNEL_DIRECT_CALL_CENTER
				|| distributionChannel == SvcConstants.DIST_CHANNEL_DIRECT_WEB || distributionChannel == SvcConstants.DIST_CHANNEL_AGENT 
				|| distributionChannel == SvcConstants.DIST_CHANNEL_AFFINITY_DIRECT_AGENT || distributionChannel == SvcConstants.DIST_CHANNEL_AFFINITY_AGENT)
				&& (null != deployedLocation && !deployedLocation.equals(SvcConstants.OMAN))&& 
				(SvcConstants.RENEWAL_POL_DOC_CODE.equals(new Integer(travelInsuranceVO.getCommonVO().getDocCode())))){
			double vatatx =SvcUtils.getVatTaxForTravel(travelInsuranceVO);
			/*
			 *  TICKET 154070
			 */
			Long endtId =0L;
			if(!Utils.isEmpty(travelInsuranceVO.getEndtId())){
			 endtId = travelInsuranceVO.getEndtId();
			}
			
			DAOUtils.updatePolVATPremium(vatatx, travelInsuranceVO.getCommonVO().getPolicyId(), endtId);
			LOGGER.info("Updated Policy VAT Premium for Travle Renewal quote");
		}
		
		if( !isPopulateOperation ){
			//Generate Endt Text
			SvcUtils.generateEndtText( travelInsuranceVO );
		}
		return travelInsuranceVO;
	}
	
	
	/**
	 * Method to store the referral Message
	 * 
	 * @param homeInsuranceVO
	 * @param referralMessage 
	 * @param request 
	 */
	private void saveReferralMessage( TravelInsuranceVO travelInsuranceVO){
	
		travelInsuranceVO.getReferralVOList().getReferrals().get( 0 ).setConsolidated( true );

		TaskVO taskVO = travelInsuranceVO.getReferralVOList().getTaskVO();

		//TTrnPasReferralDetails pasReferralDetails = (TTrnPasReferralDetails) pasReferralSaveCmnSvc.invokeMethod("saveReferralData", travelInsuranceVO);
		pasReferralSaveCmnSvc.invokeMethod("saveReferralData", travelInsuranceVO);
		taskVO.setDesc( DAOUtils.getTaskDescription(travelInsuranceVO.getCommonVO().getPolicyId(),travelInsuranceVO.getCommonVO().getEndtId()));
		taskSvc.invokeMethod("saveRefferalTask", taskVO);
		
		DataHolderVO<Object[]> dataHolderVO = new DataHolderVO<Object[]>();
		Object[] data = {travelInsuranceVO,"REFERRAL_MAIL_TRIGGER"};
		dataHolderVO.setData( data );
		commonOpSvc.invokeMethod( "sendReferralMail", dataHolderVO );
	}
	
	private BaseVO saveRenewalReferal(BaseVO baseVO) {
		DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) baseVO;

		TravelInsuranceVO taskTravelInsuranceVO = (TravelInsuranceVO) dataHolder.getData()[ 0 ];
		CommonVO commonVO = taskTravelInsuranceVO.getCommonVO();
		if((!Utils.isEmpty( taskTravelInsuranceVO ) && !Utils.isEmpty( taskTravelInsuranceVO.getReferralVOList()))){
			saveReferralMessage( taskTravelInsuranceVO);
			taskTravelInsuranceVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_REFERRED );
		}
		if(!Utils.isEmpty( commonVO ) && commonVO.getDocCode().shortValue() == SvcConstants.RENEWAL_POL_DOC_CODE){
			PASStoredProcedure sp = (PASStoredProcedure)Utils.getBean( "renewalReferalStatusUpdateTravel" );
			sp.call( commonVO.getPolicyId(),commonVO.getEndtId(),commonVO.getStatus(),SvcConstants.POL_STATUS_PENDING );
		}
		return taskTravelInsuranceVO;
	}


	/**
	 * @return the premiumSvc
	 */
	public PremiumSaveCommonSvc getPremiumSvc(){
		return premiumSvc;
	}

	/**
	 * @param premiumSvc the premiumSvc to set
	 */
	public void setPremiumSvc( PremiumSaveCommonSvc premiumSvc ){
		this.premiumSvc = premiumSvc;
	}

	/**
	 * @return the pasReferralSaveCmnSvc
	 */
	public PasReferralSaveCommonSvc getPasReferralSaveCmnSvc(){
		return pasReferralSaveCmnSvc;
	}

	/**
	 * @param pasReferralSaveCmnSvc the pasReferralSaveCmnSvc to set
	 */
	public void setPasReferralSaveCmnSvc( PasReferralSaveCommonSvc pasReferralSaveCmnSvc ){
		this.pasReferralSaveCmnSvc = pasReferralSaveCmnSvc;
	}

	/**
	 * @return the taskSvc
	 */
	public TaskSvc getTaskSvc(){
		return taskSvc;
	}

	/**
	 * @param taskSvc the taskSvc to set
	 */
	public void setTaskSvc( TaskSvc taskSvc ){
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
	
	private BaseVO loadPartnerMgmt(BaseVO holder) {
		LOGGER.info("Entered TravelInsuranceSVC.loadPartnerMgmt method.");
		//PolicyDataVO policyVO= (PolicyDataVO) holder;
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) holder;
		Integer tarCode = travelInsuranceVO.getScheme().getTariffCode();
		
		LOGGER.info("Calling DAOUtils.getPartnerMgmtDetail method.");
		DAOUtils.getPartnerMgmtDetail(travelInsuranceVO.getCommonVO(), travelInsuranceVO.getGeneralInfo(), 
				travelInsuranceVO.getScheme());
		/*if (!Utils.isEmpty(partnerMgmtList)) {
			travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnerMgmtList.getPmmName());
			travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId(partnerMgmtList.getPmmId().toString());
			travelInsuranceVO.getScheme().setSchemeCode(partnerMgmtList.getPmmSchCode());
			travelInsuranceVO.getScheme().setTariffCode(partnerMgmtList.getPmmTarCode());
		}*/
		travelInsuranceVO.getScheme().setTariffCode( tarCode );
		LOGGER.info("Exiting TravelInsuranceSVC.loadPartnerMgmt method.");
		return travelInsuranceVO;
	}
	
	
	/**
	 * Fetch VAT Rate for a VAT Code - 142244 Vat implementation
	 * @param baseVO
	 * @return
	 */
	@SuppressWarnings("unused")
	private BaseVO fetchVatRateForVatCode( BaseVO baseVO ) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();

		double vatTaxPerc = 0.0;
		
		if(!Utils.isEmpty(travelInsuranceVO.getVatCode())) {			
			vatTaxPerc =  DAOUtils.VatCodeAndVatRateFromCodeMaster(travelInsuranceVO.getVatCode(), 
													travelInsuranceVO.getAuthenticationInfoVO().getAccountingDate());
		} 
			
		if( !Utils.isEmpty(vatTaxPerc)) {
			result.put( "vatRate", vatTaxPerc ); // Vat Percent			
			LOGGER.debug( "**In fetchVatRateForVatCode()**  vat Rate" + vatTaxPerc);
		} else{			
			result.put( "vatRate", 0.0 );
		}
	
		Object[] inpObjects = { result };
		dataHolder.setData( inpObjects );
		return dataHolder;			
	}
}
