/**
 * 
 */
package com.rsaame.pas.pl.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.pl.dao.IPublicLiabilityDAO;

/**
 * @author m1014644
 *
 */
public class PublicLiabilitySvc extends BaseService {

	
	IPublicLiabilityDAO publicLiabilityDAO;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "loadPLSectionSvc".equals( methodName ) ){
			returnValue = loadPLSectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "savePLSectionSvc".equals( methodName ) ){
 			returnValue = savePLSectionSvc( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}
	
	
	
	private BaseVO savePLSectionSvc(BaseVO baseVO) {
		
		//BaseVO baseVOOutput = publicLiabilityDAO.savePLSection( baseVO );
		
		return ( (BaseSectionSaveDAO) publicLiabilityDAO ).save( baseVO );
		
	}



	private BaseVO loadPLSectionSvc(BaseVO baseVO) {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * @param publicLiabilityDAO the publicLiabilityDAO to set
	 */
	public void setPublicLiabilityDAO(IPublicLiabilityDAO publicLiabilityDAO) {
		this.publicLiabilityDAO = publicLiabilityDAO;
	}

	
	
}
