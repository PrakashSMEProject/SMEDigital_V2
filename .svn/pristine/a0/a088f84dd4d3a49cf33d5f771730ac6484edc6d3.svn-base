package com.rsaame.pas.com.svc;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.endorse.dao.IPasReferralSave;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * Service layer for persisting referral related info.
 * 
 * @author M1020859
 *
 */
public class PasReferralSaveCommonSvc extends BaseService{

	private final static Logger logger = Logger.getLogger( PasReferralSaveCommonSvc.class );
	private IPasReferralSave iPasReferralSave;

	public IPasReferralSave getiPasReferralSave() {
		return iPasReferralSave;
	}

	public void setiPasReferralSave(IPasReferralSave iPasReferralSave) {
		this.iPasReferralSave = iPasReferralSave;
	}

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		//BaseVO returnValue = null;
		TTrnPasReferralDetails pasReferralDetails = null;
		if( methodName.equals( "saveReferralData" ) ){
			logger.debug( "PasReferralSaveCommonSvc ------- > Inside method [" + methodName + "]" );
			pasReferralDetails = saveReferralData( (BaseVO) args[ 0 ] );
		}
		else if (methodName.equals("removeReferralData") ){
			return removeReferralData( (BaseVO) args[ 0 ] );
		}
		return pasReferralDetails;
	}

	/**
	 * 
	 * @param baseVO
	 * @return
	 */
	private TTrnPasReferralDetails saveReferralData( BaseVO baseVO ){
		logger.debug( "PasReferralSaveCommonSvc ------- > Inside method saveReferralData" );
		TTrnPasReferralDetails pasReferralDets = null;
		if( !Utils.isEmpty( baseVO ) ){
			PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
			logger.debug( "PasReferralSaveCommonSvc ------- > Going to make call to TempPasReferralSaveDAO" );
			pasReferralDets = iPasReferralSave.savePasReferralData( policyDataVO );
			logger.debug( "PasReferralSaveCommonSvc ------- > Response from TempPasReferralSaveDAO [" + baseVO + "]" );
		}
		return pasReferralDets;
	}

	private BaseVO removeReferralData( BaseVO baseVO ){
		
		@SuppressWarnings("unchecked")
		DataHolderVO<Object []> dataHolder = (DataHolderVO<Object[]>) baseVO;
		Object [] inputData = dataHolder.getData();
		CommonVO commonVO = (CommonVO) inputData[0];
		@SuppressWarnings("unchecked")
		List<String> factList =  (List<String>) inputData[1];
		//Radar fix
		//Boolean result = 
		iPasReferralSave.removeReferralData( commonVO,  factList);
		return baseVO;
	}

}
