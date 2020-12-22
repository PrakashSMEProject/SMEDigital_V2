/**
 * 
 */
package com.rsaame.pas.com.svc;

import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.auth.dao.IAuthorizationDAO;

/**
 * @author m1016303
 *
 */
public class AuthSvc extends BaseService {

	IAuthorizationDAO authDAO;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "getAuthenticationDetails".equals( methodName ) ){
			returnValue = getAuthenticationDetails( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}
	private BaseVO getAuthenticationDetails(BaseVO baseVO) {
		Map<String, Map<String,String>>  roleFuntionMap =  authDAO.getAuthenticationDetails( ); 
		DataHolderVO<Map> result = new DataHolderVO<Map>();
		result.setData(roleFuntionMap);
		return result;
	}
	public void setAuthDAO(IAuthorizationDAO authDAO) {
		this.authDAO = authDAO;
	}

}
