package com.rsaame.pas.com.svc;

import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.IBaseLoadOperation;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;

public class BaseLoadSvc extends BaseService{

	IBaseLoadOperation baseLoadOperation;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public Object invokeMethod( String methodName, Object... args ){

		if( "baseLoad".equals( methodName )){
			return this.baseLoadOperation.loadData( (LoadDataInputVO)args[0], (Map<String, Class<? extends BaseVO>>)args[1] );
		}
		return null;
	}

	/**
	 * 
	 * @param loadOperation
	 * @return
	 */
	public void setBaseLoadOperation( IBaseLoadOperation loadOperation){
		this.baseLoadOperation = loadOperation;
	}
}
