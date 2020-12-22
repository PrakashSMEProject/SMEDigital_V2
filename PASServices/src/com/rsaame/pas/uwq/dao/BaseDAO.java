package com.rsaame.pas.uwq.dao;


import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.utils.Utils;
/**
 *  Class  BaseDAO
 * 
 * @version 1.0  Jan 2012
 * @author m1016303
 *
 */

public class BaseDAO {
	
	static HibernateTemplate hibernateTemplateMislive;

	public  HibernateTemplate getHibernateTemplateMislive()
	{
		//Added class name for static field to fix sonar violation 0n 19-9-2017 
		getHibernateTemplate((HibernateTemplate)Utils.getBean("hibernateTemplate"));
		return hibernateTemplateMislive;
	}
	private synchronized static void setHibernateTemplateMislive(HibernateTemplate hibernateTemplateMislive) {
		BaseDAO.hibernateTemplateMislive = hibernateTemplateMislive;
	}

	public void getHibernateTemplate(HibernateTemplate hibernateTemplateMislive) {
		setHibernateTemplateMislive(hibernateTemplateMislive);
	}

}