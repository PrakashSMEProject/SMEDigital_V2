package com.rsaame.pas.vo.app;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

public class LookUpListVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @return the lookUpList
	 */
	public List<LookUpVO> getLookUpList() {
		return lookUpList;
	}

	/**
	 * @param lookUpList the lookUpList to set
	 */
	public void setLookUpList(List<LookUpVO> lookUpList) {
		this.lookUpList = lookUpList;
	}

	private List<LookUpVO> lookUpList;

	public Object getFieldValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the lookUpList
	 */
	

}
