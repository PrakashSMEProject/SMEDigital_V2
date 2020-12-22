package com.rsaame.pas.vo.svc;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.cmn.TableData;

public class TTrnHirePurchaseVOHolderWrapper extends BaseVO {	
	
	private static final long serialVersionUID = -7598807848162336252L;
	
	List<TableData> TTrnHirePurchaseVOHolderList=new ArrayList<TableData>();
	
	public List<TableData> getTTrnHirePurchaseVOHolderList() {
		return TTrnHirePurchaseVOHolderList;
	}
	public void setTTrnHirePurchaseVOHolderList(
			List<TableData> tTrnHirePurchaseVOHolderList) {
		TTrnHirePurchaseVOHolderList = tTrnHirePurchaseVOHolderList;
	}
	
	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

}
