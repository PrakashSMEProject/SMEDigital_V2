package com.rsaame.pas.sample.dao;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.rsaame.pas.sample.svc.TestVO;

/**
 * Sample database DAO class
 */
public class TestDAO extends BaseDBDAO implements ITestDAO{
	@Override
	public TestVO getTest( TestVO test ){
		TestVO testVO = new TestVO();
		testVO.setField( test.getField() );
		
		/*int rowsUpdated = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
		.createSQLQuery( "UPDATE CUSTOMER SET Age = 38 WHERE Name = 'SKN'" )
		.executeUpdate();
		
		System.out.println( "[" + rowsUpdated + "] rows updated." );*/
		
		throw new SystemException( "", null, "Simply to rollback" );
		
		//return testVO;
	}
}
