/**
 * 
 */
package com.rsaame.pas.com.helper;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapper;
import com.rsaame.pas.dao.cmn.SaveCase;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1014644
 *
 * This Interface contains methods to derive values before data getting saved/updated to database 
 */
public interface IUpdateDerivedData{

	// This method is used before any sequence getting generated
	void updateDerivedValues(String tableInExecution, POJOWrapper mappedPojo, TableData<BaseVO> tableData, HibernateTemplate ht);
	
	// This method is used after any sequence getting generated and just before save/update to database
	void preprocessBeforeSave(String tableInExecution, POJOWrapper mappedPojo, POJOWrapper existingRecord, TableData<BaseVO> tableData, SaveCase saveCase ,  HibernateTemplate ht);
}
