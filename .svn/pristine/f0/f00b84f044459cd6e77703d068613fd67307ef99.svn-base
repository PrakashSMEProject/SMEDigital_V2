package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.IReferralDetailsDAO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ReferralDetailsSvc extends BaseService {
	IReferralDetailsDAO referralDAO;
	
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "insertReferalData".equals( methodName ) ){
			 insertReferalData( (BaseVO) args[ 0 ] );
		} else if( "isReferralNeeded".equals( methodName ) ){
			returnValue = isReferralNeeded((BaseVO) args[ 0 ] );
		} else if( "storeRenewalReferrals".equals( methodName ) ){
			storeRenewalReferrals((BaseVO) args[ 0 ] );
		}else if( "deleteRenewalReferral".equals( methodName ) ){
			deleteRenewalReferral((BaseVO) args[ 0 ] );
		}else if( "getEndorsementText".equals( methodName ) ){
			returnValue = getEndorsementText();
		}else if( "isReferralNeededForHomeAndTravel".equals( methodName ) ){
			returnValue = isReferralNeededForHomeAndTravel((BaseVO) args[ 0 ] );
		}else if( "insertReferalDataDisc".equals( methodName ) ){
			insertReferalDataDisc( (PolicyVO) args[ 0 ] );
		}else if( "deleteReferral".equals( methodName ) ){
			deleteReferral( (PolicyVO) args[ 0 ] );
		}
		return returnValue;
	}
	
	private void insertReferalDataDisc(PolicyVO policyVO) {
		referralDAO.insertReferalDataDisc( policyVO );
		
	}
	public void setReferralDAO( IReferralDetailsDAO referralDAO ){
		this.referralDAO = referralDAO;
	}

	private void insertReferalData ( BaseVO baseVO){
		 referralDAO.insertReferalData( baseVO );
	}
	
	private void storeRenewalReferrals(BaseVO baseVO){
		referralDAO.storeRenewalReferrals( baseVO );
	}
	
	private void deleteRenewalReferral(BaseVO baseVO){
		referralDAO.deleteRenewalReferral( baseVO );
	}
	private BaseVO getEndorsementText(){
		return referralDAO.getEndorsementText();
	}
	
	private BaseVO isReferralNeeded(BaseVO baseVO){
		return referralDAO.isReferralNeeded( baseVO );
	}	
	
	private BaseVO isReferralNeededForHomeAndTravel(BaseVO baseVO){
		return referralDAO.isReferralNeededForHomeAndTravel( baseVO );
	}	
	private BaseVO deleteReferral(BaseVO baseVO){
		return referralDAO.deleteReferral( baseVO );
	}
}
