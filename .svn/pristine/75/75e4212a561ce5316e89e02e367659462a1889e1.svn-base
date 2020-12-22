package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.List;

public class ReminderListVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ReminderVO> reminderListVO = new List<ReminderVO>(ReminderVO.class);
	
	private Integer reminderCount ;
	
	public Integer getReminderCount() {
		return reminderCount;
	}

	public void setReminderCount(Integer reminderCount) {
		this.reminderCount = reminderCount;
	}


	public List<ReminderVO> getReminderListVO() {
		return reminderListVO;
	}


	public void setReminderListVO(List<ReminderVO> reminderListVO) {
		this.reminderListVO = reminderListVO;
	}


	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;

		if( "reminderCount".equals( fieldName ) ) fieldValue = getReminderCount();
		
		return fieldValue;
	}

}
