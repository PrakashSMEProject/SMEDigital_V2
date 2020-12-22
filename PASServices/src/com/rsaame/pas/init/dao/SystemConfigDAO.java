package com.rsaame.pas.init.dao;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.pas.dao.model.TMasControl;
import com.rsaame.pas.dao.model.TMasCurrency;

/**
 * @author m1020637
 *
 */
public class SystemConfigDAO extends BaseDBDAO{
	Logger logger = Logger.getLogger( SystemConfigDAO.class );

	/**
	 * @return
	 */
	public TMasCurrency fetchCurrency() {
		

		List<TMasControl> controlList;
		List<TMasCurrency> currencyList;
		TMasControl control  = null;
		TMasCurrency tmCurrency = null;

		String[] paramNames={"identifier"};
		Object[] values={1};

		org.springframework.orm.hibernate3.HibernateTemplate ht = getHibernateTemplate();
		controlList = ht.findByNamedQueryAndNamedParam("getControlList", paramNames,values);

		control = controlList.get(0);

		String[] parNames={"identifier"};
		Integer code = Integer.valueOf(control.getCtlCurrency());
		Object[] val={code};

		currencyList = ht.findByNamedQueryAndNamedParam("getCurrencyList", parNames,val);
		tmCurrency = currencyList.get(0);
		
		logger.debug("Currency :"+tmCurrency.getEngShortDesc());

		return tmCurrency;
	}

}
