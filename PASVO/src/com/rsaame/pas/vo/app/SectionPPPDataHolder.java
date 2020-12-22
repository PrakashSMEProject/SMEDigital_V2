package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.mindtree.ruc.cmn.utils.List;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.SectionVO;

public class SectionPPPDataHolder extends BaseVO implements IFieldValue{

	private SectionVO sectionVO;
	java.util.List<Contents> contentsList =  new com.mindtree.ruc.cmn.utils.List<Contents>(Contents.class);
	

	public SectionVO getSectionVO() {
		return sectionVO;
	}

	public void setSectionVO(SectionVO sectionVO) {
		this.sectionVO = sectionVO;
	}

	public java.util.List<Contents> getContentsList() {
		return contentsList;
	}

	public void setContentsList(java.util.List<Contents> contentsList) {
		this.contentsList = contentsList;
	}
	
	@Override
	public Object getFieldValue(String fieldName) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Contents [contentsList=" + contentsList
				+ "]";
	}


}
