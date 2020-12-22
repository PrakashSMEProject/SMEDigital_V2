package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;

public class UWQInputsVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	
	public int sectionId;
	public int tarCode;
	
	
	public int getSectionId() {
		return sectionId;
	}


	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}


	public int getTarCode() {
		return tarCode;
	}


	public void setTarCode(int tarCode) {
		this.tarCode = tarCode;
	}


	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

}
