package com.rsaame.pas.vo.app;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

public class InsuredCommentListVO extends BaseVO implements IFieldValue{

	private static final long serialVersionUID = 1L;

	private List<InsuredCommentVO> insuredComments;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "insuredComments".equals( fieldName ) ) fieldValue = getInsuredComments();

		return fieldValue;
	}

	/**
	 * @return the insuredComments
	 */
	public List<InsuredCommentVO> getInsuredComments(){
		return insuredComments;
	}

	/**
	 * @param insuredComments the insuredComments to set
	 */
	public void setInsuredComments( List<InsuredCommentVO> insuredComments ){
		this.insuredComments = insuredComments;
	}

}
