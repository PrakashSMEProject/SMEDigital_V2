package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;


public class CommissionVOList  extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	private java.util.List<CommissionVO> commision =  new com.mindtree.ruc.cmn.utils.List<CommissionVO>(CommissionVO.class);

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommissionVOList [commision=" + commision + "]";
	}

	/**
	 * @return the commision
	 */
	public java.util.List<CommissionVO> getCommision() {
		return commision;
	}

	/**
	 * @param commision the commision to set
	 */
	public void setCommision(java.util.List<CommissionVO> commision) {
		this.commision = commision;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;
		if( "commision".equals( fieldName ) ) fieldValue = getCommision();
		return fieldValue;
	}
	
	

}
