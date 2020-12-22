/**
 * 
 */
package com.rsaame.pas.renewals.svc;

import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.home.svc.HomeBuildingLoadSvc;
import com.rsaame.pas.home.svc.HomeCoverDetailsLoadSvc;
import com.rsaame.pas.renewals.dao.IRenewalCommonDAO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

/**
 * @author m1019193
 *This service Class is used to call the DAO Layer to display policies for which renewal quotes have not been generated
 *and print the generated renewal policies
 */
public class RenewalCommonSvc extends BaseService {

	private static final Logger LOGGER = Logger.getLogger( RenewalCommonSvc.class );
	private final static String UPDATE_RENEWAL_QUOTE_STATUS_COMMON = "UPDATE_RENEWAL_QUOTE_STATUS_COMMON";
	private HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc;
	private HomeBuildingLoadSvc baseHomeBuildingLoadSvc;
	//private HomeInsuranceSVC homeInsuranceLoadBean;
	private IRenewalCommonDAO renewalCommonDAO;
		
	/**
	 * @param homeInsuranceLoadBean the homeInsuranceLoadBean to set
	 */
	/*public void setHomeInsuranceLoadBean(HomeInsuranceSVC homeInsuranceLoadBean) {
		this.homeInsuranceLoadBean = homeInsuranceLoadBean;
	}*/

	public void setRenewalCommonDAO( IRenewalCommonDAO renewalCommonDAO ){
		this.renewalCommonDAO = renewalCommonDAO;
	}	
	
	/**
	 * @param baseCoverDetailsLoadSvc the baseCoverDetailsLoadSvc to set
	 */
	public void setBaseCoverDetailsLoadSvc(HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc) {
		this.baseCoverDetailsLoadSvc = baseCoverDetailsLoadSvc;
	}


	/**
	 * @param baseHomeBuildingLoadSvc the baseHomeBuildingLoadSvc to set
	 */
	public void setBaseHomeBuildingLoadSvc(
			HomeBuildingLoadSvc baseHomeBuildingLoadSvc) {
		this.baseHomeBuildingLoadSvc = baseHomeBuildingLoadSvc;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		LOGGER.debug("***In RenewalCommonSvc Class***");
		if( "getPoliciesToBeRenewed".equals( methodName ) ){
			returnValue = getPoliciesToBeRenewed( (BaseVO) args[ 0 ] );
		}else if("generateOnlineRenewal".equals(methodName)){
			returnValue = generateOnlineRenewal( (BaseVO) args[ 0 ] );
		}else if("getRenewalQuotations".equals(methodName)){
			returnValue = getRenewalQuotations( (BaseVO) args[ 0 ] );
		}else if( "savePoliciesForBatchRenewal".equals( methodName ) ){
			savePoliciesForBatchRenewal( (BaseVO) args[ 0 ] ); 
		}else if( "savePoliciesForBatchPrint".equals( methodName ) ){
			savePoliciesForBatchPrint( (BaseVO) args[ 0 ] );
		} else if("getClaimCount".equals(methodName)){
			returnValue = getClaimCount( (BaseVO) args[ 0 ] );
		}else if("checkForReprint".equals(methodName ) ){
			returnValue = checkForReprint( (BaseVO) args[ 0 ] );
		}else if("checkForResendMail".equals(methodName ) ){
			returnValue = checkForResendMail( (BaseVO) args[ 0 ] );
		}else if("getRenewalNoticeNotSent".equals(methodName ) ){
			returnValue = getRenewalNoticeNotSent( (BaseVO) args[ 0 ] );
		}else if("saveRenewalNotice".equals(methodName ) ){
			saveRenewalNotice( (BaseVO) args[ 0 ] );
		}else if("loadGenInfoAndCover".equals(methodName)	){
			returnValue = loadGenInfoAndCover( (BaseVO) args[ 0 ] );
		}else if("saveOrUpdateTtrnPrmTable".equals(methodName)	){
			returnValue = saveOrUpdateTtrnPrmTable( (BaseVO) args[ 0 ] );
		} else if("updateQuotationStatus".equals(methodName)){
			updateQuotationStatus( (BaseVO) args[ 0 ] );
		} else if("updatePrmStatusForSoftStop".equals(methodName)){
			updatePrmStatusForSoftStop( (BaseVO) args[ 0 ] );
		} else if("updateGprStatusForSoftStop".equals(methodName)){
			updateGprStatusForSoftStop( (BaseVO) args[ 0 ] );
		} 
		else if("loadSaveTravelDetails".equals(methodName)){
			returnValue = loadSaveTravelDetails( (BaseVO) args[ 0 ] );
		}else if("sendRenewalMessage".equals(methodName)){
			sendRenewalMessage( (BaseVO) args[ 0 ] );
		}else if("getClaimDetails".equals(methodName)){
			returnValue = getClaimDetails( (BaseVO) args[ 0 ] );
		}else if("getFraudClaim".equals(methodName)){
			returnValue = getFraudClaim( (BaseVO) args[ 0 ] );
		}else if("updateRenewalTerm".equals(methodName)){
			updateRenewalTerm( (BaseVO) args[ 0 ] );
		}
		
		
		/*else if("isRenewalPolicyForHomeAndTravel".equals(methodName)){
			returnValue = isRenewalPolicyForHomeAndTravel((BaseVO)args[ 0 ]);
		}*/
		return returnValue;
	}	

	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO getPoliciesToBeRenewed(BaseVO baseVO) {
		return renewalCommonDAO.getPoliciesToBeRenewed( baseVO );
		
	}	
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO generateOnlineRenewal(BaseVO baseVO){
		return renewalCommonDAO.generateOnlineRenewal( baseVO );
	}	
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO getRenewalQuotations(BaseVO baseVO) {
		return renewalCommonDAO.getRenewalQuotations( baseVO );
		
	}
	/**
	 * @param baseVO
	 */
	private void savePoliciesForBatchRenewal(BaseVO baseVO){
		renewalCommonDAO.savePoliciesForBatchRenewal( baseVO );
	}
	
	/**
	 * @param baseVO
	 */
	private void savePoliciesForBatchPrint(BaseVO baseVO){
		renewalCommonDAO.savePoliciesForBatchPrint( baseVO );
	}
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO getClaimCount(BaseVO baseVO){
		return renewalCommonDAO.getClaimCount(baseVO);
	}	
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO checkForReprint ( BaseVO baseVO){
		return renewalCommonDAO.checkForReprint( baseVO );
	}
	
	private BaseVO checkForResendMail ( BaseVO baseVO){
		return renewalCommonDAO.checkForResendMail( baseVO );
	}
	
	private BaseVO getRenewalNoticeNotSent ( BaseVO baseVO){
		return renewalCommonDAO.getRenewalNoticeNotSent( baseVO );
	}
	
	private void saveRenewalNotice ( BaseVO baseVO){
		renewalCommonDAO.saveRenewalNotice( baseVO );
	}
	
	private void updateQuotationStatus(BaseVO baseVO){
		renewalCommonDAO.updateQuotationStatus( baseVO );
	}
	
	private void updateRenewalTerm(BaseVO baseVO){
		renewalCommonDAO.updateRenewalTerm( baseVO );
	}
	/*
	 * updatePrmStatusForSoftStop method is written for travel soft stop condition to 
	 * change the status of entries in t_trn_premium_quo in 
	 * DB for soft stop quote
	 */
	private void updatePrmStatusForSoftStop(BaseVO baseVO){
		renewalCommonDAO.updatePrmStatusForSoftStop( baseVO );
	}
	/*
	 * updateGprStatusForSoftStop method is written for travel soft stop condition to 
	 * change the status of entries in t_trn_gacc_person_quo in 
	 * DB for soft stop quote
	 */
	private void updateGprStatusForSoftStop(BaseVO baseVO){
		renewalCommonDAO.updateGprStatusForSoftStop( baseVO );
	}
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO loadGenInfoAndCover(BaseVO baseVO) {			
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		homeInsuranceVO = (HomeInsuranceVO) TaskExecutor.executeTasks( "HOME_INSURANCE_LOAD", homeInsuranceVO.getCommonVO());
		return homeInsuranceVO;
	}
	
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO saveOrUpdateTtrnPrmTable(BaseVO baseVO) {			
		PremiumSaveCommonSvc premiumSvc = (PremiumSaveCommonSvc) Utils.getBean( "premiumServiceBean" );
		return (HomeInsuranceVO)premiumSvc.invokeMethod( SvcConstants.SAVE_PREMIUM, baseVO);		
	}
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO loadSaveTravelDetails(BaseVO baseVO) {	
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO)TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", baseVO );	
		List<TravelPackageVO> trvlPackList = travelInsuranceVO.getTravelPackageList();			
		for(int i = 0;i<trvlPackList.size();i++ ){			
			if(!trvlPackList.get(i).getIsSelected()){
				trvlPackList.remove(i);	
				i--;
			}
		}
		
		DataHolderVO<Boolean> isMedicalClaim = (DataHolderVO<Boolean>) renewalCommonDAO.isMedicalClaimPolicy(travelInsuranceVO.getAuthenticationInfoVO().getRefPolicyId());
		
		Double travelClaimsAoumt = null;
		
		if(!isMedicalClaim.getData().booleanValue()){
			
			DataHolderVO<Double> claimAmount = (DataHolderVO<Double>) renewalCommonDAO.getTravelClaimAmount(travelInsuranceVO.getAuthenticationInfoVO().getRefPolicyId());
			travelClaimsAoumt = claimAmount.getData();
			
			if( travelClaimsAoumt > SvcConstants.ZERO && travelClaimsAoumt <= SvcConstants.NON_MEDICAL_CLAIM_AMT){
				for(TravelPackageVO travelPackage : travelInsuranceVO.getTravelPackageList()){
					if(travelPackage.getIsSelected()){
						//travelPackage.setDiscOrLoadPerc( 25.0 );
						travelInsuranceVO.getPremiumVO().setDiscOrLoadPerc( SvcConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO);
					}
				}
			}			
		}		
		
		Object[] inpObjects = {travelInsuranceVO,false};
		dataHolder.setData( inpObjects );
		//PremiumSaveCommonSvc premiumSvc = (PremiumSaveCommonSvc) Utils.getBean( "premiumServiceBean" );
		//return (TravelInsuranceVO)premiumSvc.invokeMethod( SvcConstants.SAVE_TRAVEL_PREMIUM, dataHolder);
		travelInsuranceVO =   (TravelInsuranceVO) TaskExecutor.executeTasks( "SAVE_QUOTE_TRAVEL", dataHolder );
		travelInsuranceVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_ACTIVE );
		if( ( !Utils.isEmpty( travelClaimsAoumt ) && travelClaimsAoumt > 5000 ) || isMedicalClaim.getData().booleanValue() ){
			
			DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
			Object claimInputData[] = new Object[2];
			claimInputData[0] = travelInsuranceVO;
			claimInputData[1] = Utils.getSingleValueAppConfig("QUOTE_HARD_STOP");
			input.setData(claimInputData);
			TaskExecutor.executeTasks(UPDATE_RENEWAL_QUOTE_STATUS_COMMON, input);
			travelInsuranceVO.getCommonVO().setStatus( Integer.parseInt( Utils.getSingleValueAppConfig("QUOTE_HARD_STOP") ) );
		}
		// Check age 
		// If age is greater than 75 or less than 16 then generate referral and pol_status = soft-stop
		List<TravelerDetailsVO> travellers =  travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList();
		boolean ageFlag = false;
		boolean softStop = false;
		if(travellers.size() == 1)
		{
			short minAge = Short.parseShort(Utils.getSingleValueAppConfig("CHILD_AGE"));
			short age = SvcUtils.getAge(travellers.get(0).getDateOfBirth(),  travelInsuranceVO.getValidityStartDate());
			if(age < minAge)						// Softstop this quote and  raise referral //CHILD_AGE
				softStop= true;				
			
		}
		
		for(TravelerDetailsVO tr : travellers)
		{
			// check age
			ageFlag = checkMaxAgeOfTraveller(tr, travelInsuranceVO.getValidityStartDate());
			if(!ageFlag)				// Softstop this quote and  raise referral
			{
				softStop = true;
				break;
			}
			
		}
		
		if(softStop)
		{
			renewalCommonDAO.softStopCommon(travelInsuranceVO);
		}
		
		
		return travelInsuranceVO;
		
	}
	
	private boolean checkMaxAgeOfTraveller(TravelerDetailsVO tr, Date d)
	{
		boolean flag = true;
		short maxAge = Short.parseShort(Utils.getSingleValueAppConfig("ADULT_MAX_AGE"));
		short age = SvcUtils.getAge(tr.getDateOfBirth() ,d);
		if(age> maxAge)
			flag = false;
		
		return flag;
	}
	
	
	/**
	 * @param baseVO
	 * @return
	 */
	private void sendRenewalMessage ( BaseVO baseVO){
		renewalCommonDAO.sendRenewalMessage( baseVO );
	}
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO getClaimDetails(BaseVO baseVO){
		return renewalCommonDAO.getClaimDetails(baseVO);
	}
	
	/**
	 * @param baseVO
	 * @return
	 */
	private BaseVO getFraudClaim(BaseVO baseVO){
		return renewalCommonDAO.getFraudClaim(baseVO);
	}	
	
	/*private BaseVO isRenewalPolicyForHomeAndTravel(BaseVO baseVO){
		return renewalCommonDAO.isRenewalPolicyForHomeAndTravel(baseVO);
	}		
	}*/
}
