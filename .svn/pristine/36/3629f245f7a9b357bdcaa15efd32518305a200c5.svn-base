/**
 * 
 */
package com.rsaame.pas.fidelity.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.fidelity.dao.FidelitySaveDAO;
import com.rsaame.pas.fidelity.dao.IFidelitysectionDAO;
import com.rsaame.pas.money.dao.IMoneyDAO;
import com.rsaame.pas.money.dao.MoneyDAO;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author m1016303
 *
 */
public class FidelitySectionSvc extends BaseService {

	IFidelitysectionDAO fidelityDAO;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		if( "saveFidelitySectionSvc".equals( methodName ) ){
			returnValue = saveFidelitySectionSvc( (BaseVO) args[ 0 ] );
		} 
		return returnValue;
	}

	private BaseVO saveFidelitySectionSvc(BaseVO baseVO) {
		
		return ((FidelitySaveDAO)fidelityDAO ).save( (PolicyVO) baseVO );
	}

	/**
	 * @param fidelityDAO the fidelityDAO to set
	 */
	public void setFidelityDAO(IFidelitysectionDAO fidelityDAO) {
		this.fidelityDAO = fidelityDAO;
	}

}
