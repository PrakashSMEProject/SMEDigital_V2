/**
 * 
 */
package com.rsaame.pas.mb.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.mb.dao.IMBSectionDAO;

/**
 * @author m1014644
 *
 */
public class MBSectionSVC extends BaseService {

	
	IMBSectionDAO mbSectionDAO;
	
	public IMBSectionDAO getMbSectionDAO(){
		return mbSectionDAO;
	}



	public void setMbSectionDAO( IMBSectionDAO mbSectionDAO ){
		this.mbSectionDAO = mbSectionDAO;
	}



	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "loadMBSectionSvc".equals( methodName ) ){
			returnValue = loadMBSectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "saveMBSectionSvc".equals( methodName ) ){
 			returnValue = saveMBSectionSvc( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}
	
	
	
	private BaseVO saveMBSectionSvc(BaseVO baseVO) {
		
		return ( (BaseSectionSaveDAO) mbSectionDAO ).save(baseVO);
	}



	private BaseVO loadMBSectionSvc(BaseVO baseVO) {
		
		 return mbSectionDAO.loadMBSection(baseVO);
	}

}
