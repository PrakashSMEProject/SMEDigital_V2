/**
 * 
 */
package com.rsaame.pas.com.svc;

import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.cmn.IBaseSaveOperation;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1014644
 *
 */
public class BaseSaveSvc extends BaseService{

	private IBaseSaveOperation baseSaveOperation;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public BaseVO invokeMethod( String methodName, Object... args ){
		
		if(methodName.equalsIgnoreCase( "baseSave" )){
			this.baseSaveOperation.executeSave( (DataHolderVO<LinkedHashMap<String, List<TableData>>> )args[0], (CommonVO)args[1] );
		}
		
		/*if(methodName.equals( "savePremium" )){
			this.baseSaveOperation.executePremiumSave( (DataHolderVO<LinkedHashMap<String, List<TableData>>> )args[0], (CommonVO)args[1] );
		}*/
		
		return  (DataHolderVO<LinkedHashMap<String, List<TableData>>> )args[0] ;
	}
	/**
	 * @param baseSaveOperation the baseSaveOperation to set
	 */
	public void setBaseSaveOperation( IBaseSaveOperation baseSaveOperation ){
		this.baseSaveOperation = baseSaveOperation;
	}

}
