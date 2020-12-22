package com.rsaame.pas.quote.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.utils.Utils;

public class QuoteDAO extends BaseDBDAO implements IClaimsDAO {
	
	public  boolean checkClaimsExistForPolicyNumber(long policyNumber) {
		return executeDBFunction("SELECT PKG_PAS_CLAIMS.CLAIMS_EXIST_BY_POLICY_NUM("+ policyNumber + ") FROM DUAL");
	}	
	
	public  boolean checkClaimsExistForInsured(String insuredCode) {
		return executeDBFunction("SELECT PKG_PAS_CLAIMS.CLAIMS_EXIST_BY_INSURED_CODE("+ insuredCode + ") FROM DUAL");
	}

	/**
	 * @param sqlQuery
	 * @return
	 */
	private boolean executeDBFunction(String sqlQuery) {
		boolean exist = false;
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sqlQuery);
		List<Object> results = query.list();
		if(!Utils.isEmpty(results)){
			String result = results.get(0).toString();			
			if(!Utils.isEmpty(result)){
				exist = ("TRUE".equalsIgnoreCase(result))?true:false;
			}
		}
		return exist;
	}
}
