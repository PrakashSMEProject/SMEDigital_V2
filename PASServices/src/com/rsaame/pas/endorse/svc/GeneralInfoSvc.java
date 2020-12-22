/**
 * 
 */
package com.rsaame.pas.endorse.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.endorse.dao.IGeneralInfoSaveDao;

/**
 * @author m1014241
 *
 */
public class GeneralInfoSvc extends BaseService {

	IGeneralInfoSaveDao generalInfoDao;
	
	


	public IGeneralInfoSaveDao getGeneralInfoDao() {
		return generalInfoDao;
	}



	public void setGeneralInfoDao(IGeneralInfoSaveDao generalInfoDao) {
		this.generalInfoDao = generalInfoDao;
	}



	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		
		BaseVO returnValue = null;
		
		
		if( "saveGeneralInfo".equals( methodName ) ){
			returnValue = saveGeneralInfo( (BaseVO) args[ 0 ] );
		}else if( "updateTmasInsured".equals( methodName ) ){
			returnValue = updateTmasInsured( (BaseVO) args[ 0 ] );
		}else if( "compareTmasInsured".equals( methodName ) ){
			returnValue = compareTmasInsured( (BaseVO) args[ 0 ] );
		}
		

		return returnValue;
	}
	
	
	
	private BaseVO saveGeneralInfo(BaseVO baseVO) {
			
		return generalInfoDao.saveGeneralInfoDetails(baseVO);
	}

	private BaseVO updateTmasInsured(BaseVO baseVO) {
		
		return generalInfoDao.updateTmasInsured(baseVO);
	}
	
	private BaseVO compareTmasInsured(BaseVO baseVO) {
		
		return generalInfoDao.compareTmasInsured(baseVO);
	}
	

}
