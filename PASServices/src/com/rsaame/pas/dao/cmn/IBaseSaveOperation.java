/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1014644
 *
 */
public interface IBaseSaveOperation{

	/**
	 * Start of the save operation.
	 * @param toBeSaved - Holds a linked hash map with the tables to  be saved as key and the data to be saved as a list corresponding to each table 
	 * @param commonBaseVO - Common attributes that 
	 * @return
	 */
	public abstract BaseVO executeSave( DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved, BaseVO commonBaseVO );

}