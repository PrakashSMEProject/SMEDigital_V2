/**
 * 
 */
package com.rsaame.pas.rsadirect.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.TTrnMakeClaim;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.rsadirect.dao.IRSADirectDao;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.MotorClaimVO;

/**
 * @author Sarath Varier
 * @since Phase 3 - Make a Claim migration
 */
public class MakeClaimSvc extends BaseService {
	
	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger.getLogger( MakeClaimSvc.class );
	IRSADirectDao makeClaimDao;

	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		if(methodName.equals("submitClaimDetails")){
			return submitClaimDetails((BaseVO) args[ 0 ]);
		}
		if(methodName.equals("submitMotorRenewalPolicy")){
			return submitMotorRenewalPolicy((BaseVO)args[0]);
		}		
		return null;
	}
	
	private BaseVO submitClaimDetails(BaseVO baseVO){
		
		LOGGER.info("Save claim details started");
		MotorClaimVO claimVO = (MotorClaimVO)baseVO;

		TTrnMakeClaim ttrnMakeClaim = BeanMapper.map(claimVO, TTrnMakeClaim.class);
		
		makeClaimDao.submitClaim(ttrnMakeClaim);
		
		claimVO.setClaimId(ttrnMakeClaim.getMacClaimId());
		claimVO.setPreparedDate(ttrnMakeClaim.getMacPreparedDate());

		if(null != ttrnMakeClaim.getMacDriverLicense()) {
			claimVO.getVehicleVO().setDriverLicenceFilePath(ttrnMakeClaim.getMacDriverLicense());
			SvcUtils.saveFileToDisk(getFilePath(ttrnMakeClaim.getMacDriverLicense()), 
					getFileName(ttrnMakeClaim.getMacDriverLicense()), claimVO.getVehicleVO().getDriverLicence());
		}
		if(null != ttrnMakeClaim.getMacRegcardCopy()) {
			claimVO.getVehicleVO().setRegistartionCardFilePath(ttrnMakeClaim.getMacRegcardCopy());
			SvcUtils.saveFileToDisk(getFilePath(ttrnMakeClaim.getMacRegcardCopy()), 
					getFileName(ttrnMakeClaim.getMacRegcardCopy()), claimVO.getVehicleVO().getRegistartionCard());
		}
		
		//Added for Oman D2C
		LoginLocation location = (LoginLocation) Utils.getBean("location");
		String deployedLocation = location.getLocation();
		if (null != deployedLocation && deployedLocation.equals(QueryConstants.LOCATION_CODE)) {
			if(null != ttrnMakeClaim.getMacClaimForm()) {
				claimVO.setClaimFormFilePath(ttrnMakeClaim.getMacClaimForm());
				SvcUtils.saveFileToDisk(getFilePath(ttrnMakeClaim.getMacClaimForm()), 
						getFileName(ttrnMakeClaim.getMacClaimForm()), claimVO.getClaimForm());
			}
			if(ttrnMakeClaim.getMacAccidentType().equalsIgnoreCase(QueryConstants.ACCIDENT_TYPE_MRTA_FORM) && null != ttrnMakeClaim.getMacMRTAForm()) {
				claimVO.setMrtaFormFilePath(ttrnMakeClaim.getMacMRTAForm());
				SvcUtils.saveFileToDisk(getFilePath(ttrnMakeClaim.getMacMRTAForm()), 
						getFileName(ttrnMakeClaim.getMacMRTAForm()), claimVO.getMrtaForm());
			}else if(ttrnMakeClaim.getMacAccidentType().equalsIgnoreCase(QueryConstants.ACCIDENT_TYPE_POLICE_REPORT) && null != ttrnMakeClaim.getMacPoliceReport()) {
				claimVO.setPoliceReportFilePath(ttrnMakeClaim.getMacPoliceReport());
				SvcUtils.saveFileToDisk(getFilePath(ttrnMakeClaim.getMacPoliceReport()), 
						getFileName(ttrnMakeClaim.getMacPoliceReport()), claimVO.getPoliceReport());
			}
		}else {
			claimVO.setPoliceReportFilePath(ttrnMakeClaim.getMacPoliceReport());
			SvcUtils.saveFileToDisk(getFilePath(ttrnMakeClaim.getMacPoliceReport()), 
					getFileName(ttrnMakeClaim.getMacPoliceReport()), claimVO.getPoliceReport());
		}
		//End D2C
		
		LOGGER.info("Save claim details completed");
		return baseVO;
	}
	
	private String getFilePath(String path){
		
		return path.substring( 0, path.lastIndexOf( "/" ) );
	}
	
	private String getFileName(String path){
		
		return path.substring( path.lastIndexOf( "/" ) + 1 );
	}

	/**
	 * @param makeClaimDao the makeClaimDao to set
	 */
	public void setMakeClaimDao(IRSADirectDao makeClaimDao) {
		this.makeClaimDao = makeClaimDao;
	}
	
	private BaseVO submitMotorRenewalPolicy(BaseVO baseVO) {
		
		LOGGER.debug("In submitMotorRenewalPolicy method.....");
		return makeClaimDao.getLocation(baseVO);		
	}	

}
