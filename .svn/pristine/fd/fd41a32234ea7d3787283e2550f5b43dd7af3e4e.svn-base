/**
 * 
 */
package com.rsaame.pas.money.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.money.dao.IMoneyDAO;
import com.rsaame.pas.money.dao.MoneyDAO;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author m1014644
 *
 */
public class MoneySvc extends BaseService {

	IMoneyDAO moneyDAO;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		if( "loadMoneySectionSvc".equals( methodName ) ){
			returnValue = loadMoneySectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "saveMoneySectionSvc".equals( methodName ) ){
			returnValue = saveMoneySectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "newSaveMoneySectionSvc".equals( methodName ) ){
			returnValue = newSaveMoneySectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "getListOfCashDetails".equals( methodName ) ){
			returnValue = getListOfCashDetails( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}

	private BaseVO newSaveMoneySectionSvc( BaseVO baseVO ){
		return ((MoneyDAO) moneyDAO).save( (PolicyVO) baseVO );
	}

	private BaseVO getListOfCashDetails( BaseVO baseVO ){
		((MoneyDAO) moneyDAO).sectionPreProcessing( (PolicyVO) baseVO );
		return null;
	}

	private BaseVO saveMoneySectionSvc(BaseVO baseVO) {
		return moneyDAO.saveMoneySection(baseVO);
	}

	private BaseVO loadMoneySectionSvc(BaseVO baseVO) {
		return moneyDAO.loadMoneySection(baseVO);
	}

	/**
	 * @param moneyDAO the moneyDAO to set
	 */
	public void setMoneyDAO(IMoneyDAO moneyDAO) {
		this.moneyDAO = moneyDAO;
	}
	
	
	
}
