package com.rsaame.pas.sample.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.sample.dao.ITestDAO;

public class TestSvc extends BaseService{
	ITestDAO testDAO = null;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "serviceMethod".equals( methodName ) ){
			returnValue = serviceMethod( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}
	
	private BaseVO serviceMethod( BaseVO input ){
		System.out.println( "Yes, called TestSvc.serviceMethod() with [" + input + "]" );
		System.out.println( "TestSvc: TestDAO instance [" + testDAO + "] Calling it..." );
		BaseVO response = testDAO.getTest( (TestVO) input );
		
		System.out.println( "TestSvc: Get this response from TestDAO.getTest() [" + response + "]" );
		return response;
	}

	public ITestDAO getTestDAO(){
		return testDAO;
	}

	public void setTestDAO( ITestDAO testDAO ){
		this.testDAO = testDAO;
	}
	
}
