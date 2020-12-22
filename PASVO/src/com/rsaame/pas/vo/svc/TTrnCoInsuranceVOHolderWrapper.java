package com.rsaame.pas.vo.svc;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.cmn.TableData;

public class TTrnCoInsuranceVOHolderWrapper extends BaseVO {

	private static final long serialVersionUID = 54969143511987151L;
	
	List<TableData> TTrnCoInsuranceVOHolderList=new ArrayList<TableData>();

	public List<TableData> getTTrnCoInsuranceVOHolderList() {
		return TTrnCoInsuranceVOHolderList;
	}

	public void setTTrnCoInsuranceVOHolderList(
			List<TableData> tTrnCoInsuranceVOHolderList) {
		TTrnCoInsuranceVOHolderList = tTrnCoInsuranceVOHolderList;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
