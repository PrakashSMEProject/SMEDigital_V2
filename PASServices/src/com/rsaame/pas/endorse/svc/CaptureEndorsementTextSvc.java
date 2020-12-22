/**
 * 
 */
package com.rsaame.pas.endorse.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.endorse.dao.IAmendPolicyDao;
import com.rsaame.pas.endorse.dao.ICaptureEndorsementTextDao;

/**
 * @author m1014241
 *
 */
public class CaptureEndorsementTextSvc extends BaseService {

	
	ICaptureEndorsementTextDao captureEndorsementTextDao;
	
	
	public void setCaptureEndorsementTextDao(ICaptureEndorsementTextDao captureEndorsementTextDao) {
		this.captureEndorsementTextDao = captureEndorsementTextDao;
	}
	


	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		if( "getEndorsementTextService".equals( methodName ) ){
			returnValue = getEndorsementTextService( (BaseVO) args[ 0 ] );
		} else if( "confirmEndtProcessing".equals( methodName ) ){
			returnValue = confirmEndtProcessing( (BaseVO) args[ 0 ] );
		} else if( "getEndorsementTextForCancelPolicy".equals(methodName)){
			returnValue = getEndorsementTextForCancelPolicy((BaseVO) args[ 0 ]);
		} else if( "getCommonEndorsementTextForCancelPolicy".equals(methodName)){
			returnValue = getCommonEndorsementTextForCancelPolicy((BaseVO) args[ 0 ]);
		} else if( "getCommonEndorsementTextForAmendPolicy".equals(methodName)){
			returnValue = getCommonEndorsementTextForAmendPolicy((BaseVO) args[ 0 ]);
		} else if("saveEndtProcessing".equals(methodName)){
			returnValue = saveEndtProcessing((BaseVO) args[ 0 ]);
		}
		else if("saveEndorsementTextForViewPolicy".equals(methodName)){
			returnValue = saveEndtText((BaseVO) args[ 0 ]);
		}
		
			return returnValue;
	}

	private BaseVO saveEndtText(BaseVO baseVO) {
		
		return captureEndorsementTextDao.saveEndtText(baseVO);
	}

	private BaseVO confirmEndtProcessing(BaseVO baseVO) {
		
		return captureEndorsementTextDao.confirmEndtProcessing(baseVO);
	}
	private BaseVO saveEndtProcessing(BaseVO baseVO) {
		
		return captureEndorsementTextDao.saveEndtProcessing(baseVO);
	}

	private BaseVO getEndorsementTextService(BaseVO baseVO) {
		
		return captureEndorsementTextDao.getEndorsementText(baseVO);
	}
	
	private BaseVO getEndorsementTextForCancelPolicy(BaseVO baseVO){
		return captureEndorsementTextDao.getEndorsementTextForCancelPolicy(baseVO);
	}
	private BaseVO getCommonEndorsementTextForCancelPolicy(BaseVO baseVO){
		return captureEndorsementTextDao.getCommonEndorsementTextForCancelPolicy(baseVO);
	}
	private BaseVO getCommonEndorsementTextForAmendPolicy( BaseVO baseVO ){
		return captureEndorsementTextDao.getCommonEndorsementTextForAmendPolicy(baseVO);
	}



}
