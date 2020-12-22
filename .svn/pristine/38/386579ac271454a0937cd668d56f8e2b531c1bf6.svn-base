/**
 * LookUpDAO.java
 * Copyright (c) 2011 MindTree Ltd.
 */
package com.rsaame.pas.tariff.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.cache.CacheManagerFactory;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.cache.PASCache;
import com.rsaame.pas.lookup.dao.ILookUpDAO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;


/**
 * Class LookUpDAO is a dao class which extends BaseDAO
 * 
 * @version 1.0  Jan 2012
 * @author 	M1014594
 */
public class TariffDAO extends BaseDBDAO implements ITariffDAO{

	@Override
	public String getTarLocation( String tarCode ) throws DataAccessException{
		String[] paramNames={"tarrifCode"};
		Object[] values={Short.parseShort(tarCode)};
		
		org.springframework.orm.hibernate3.HibernateTemplate ht = getHibernateTemplate();
		
		
		List tariffLocList= ht.findByNamedQueryAndNamedParam("getTarLocationCode", paramNames,values);
		String tariffLocCode=null;
		
		if(!Utils.isEmpty(tariffLocList)){
			if (!Utils.isEmpty(tariffLocList.get( 0 ))){
				tariffLocCode=tariffLocList.get( 0 ).toString();
				
			}
			
		}
		else{
			// Defaulted to Dubai
			tariffLocCode="20";
			
		}
		return tariffLocCode;
	}
	/**
	 * Method to get the list of values for an identifier
	 * 
	 * @param 
	 * @return  
	 */

	

	
}
