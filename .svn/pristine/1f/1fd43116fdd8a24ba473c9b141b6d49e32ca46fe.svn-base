/**
 * 
 */
package com.rsaame.pas.com.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.ICommonOpDAO;

/**
 * @author m1014644
 *
 */
public class GetCommisionSvc extends BaseService {

	ICommonOpDAO commonOpDAO;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		if( "getCommisionSvc".equals( methodName ) ){
			returnValue = getCommisionSvc( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	private BaseVO getCommisionSvc(BaseVO baseVO) {
		
		return commonOpDAO.getCommision(baseVO);
		
	}


	/**
	 * @param commonOpDAO the commonOpDAO to set
	 */
	public void setCommonOpDAO(ICommonOpDAO commonOpDAO) {
		this.commonOpDAO = commonOpDAO;
	}

/*	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
		PolicyVO policyVO = new PolicyVO();
		SchemeVO schemeVO = new SchemeVO();
		schemeVO.setSchemeCode(1001);
		policyVO.setScheme(schemeVO);
		GetCommisionSvc commisionSvc = (GetCommisionSvc) applicationContext.getBean("geComSvc");
		
		CommissionVOList commissionVOList = (CommissionVOList) commisionSvc.invokeMethod("getCommisionSvc", policyVO);
		
	}*/
}
