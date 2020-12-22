/**
 * 
 */
package com.rsaame.pas.com.helper;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1014644
 * 
 * General class used to set values to POJO before updating/saving into database 
 */
public abstract class BaseDervieDetails implements IUpdateDerivedData{

	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.IUpdateDerivedData#updateDerivedValues(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate)
	 * 
	 * Method to update details before the sequence got generated while saving
	 */
	@Override
	public void updateDerivedValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData , HibernateTemplate ht ){
		PolicyDataVO polData = (PolicyDataVO) ThreadLevelContext.get( SvcConstants.POLDATA);
		CommonVO commonVO = (CommonVO)ThreadLevelContext.get( SvcConstants.COMMONDATA );
		updateValues(  tableInExecution,  mappedPojo,  tableData ,  ht , polData, commonVO);
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.rsaame.pas.com.helper.IUpdateDerivedData#preprocessBeforeSave(java.lang.String, com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper, com.rsaame.pas.vo.cmn.TableData, org.springframework.orm.hibernate3.HibernateTemplate)
	 * 
	 * Method to update details after the sequences got generated while saving
	 */
	@Override
	public void preprocessBeforeSave(String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, SaveCase saveCase, HibernateTemplate ht){
		PolicyDataVO polData = (PolicyDataVO) ThreadLevelContext.get( SvcConstants.POLDATA);
		CommonVO commonVO = (CommonVO)ThreadLevelContext.get( SvcConstants.COMMONDATA );
		preprocessRecord(tableInExecution,  mappedPojo, existingRecord , tableData ,  ht , polData, commonVO , saveCase );
	}
	
	protected  abstract void preprocessRecord( String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO, SaveCase saveCase );

	protected abstract void updateValues( String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht, PolicyDataVO polData, CommonVO commonVO );
}
