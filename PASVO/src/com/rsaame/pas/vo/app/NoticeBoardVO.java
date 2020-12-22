/**
 * 
 */
package com.rsaame.pas.vo.app;

import java.util.HashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.mindtree.ruc.cmn.utils.Map;

/**
 * @author M1016284
 *
 */
public class NoticeBoardVO extends BaseVO implements IFieldValue{

	private HashMap<String, List<String>> noticeBoardItems; 
	
	public HashMap<String, List<String>> getNoticeBoardItems() {
		return noticeBoardItems;
	}
	
	public void setNoticeBoardItems(HashMap<String, List<String>> noticeBoardItems) {
		this.noticeBoardItems = noticeBoardItems;
	}
	
			@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;
	
		if( "noticeBoardItems".equals(fieldName) ) fieldValue = getNoticeBoardItems();
		
		return fieldValue;
	}

	
}
