/**
 * 
 */
package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.rsaame.pas.vo.bus.CommentsVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1014644
 *
 */
public class PolicyCommentsHolder extends BaseVO implements IFieldValue {
	
	private static final long serialVersionUID = 1L;
	private PolicyVO policyDetails;
	private CommonVO commonDetails;
	private CommentsVO comments;
	
	@Override
	public Object getFieldValue( String fieldName ){
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the policyDetails
	 */
	public PolicyVO getPolicyDetails(){
		return policyDetails;
	}

	/**
	 * @param policyDetails the policyDetails to set
	 */
	public void setPolicyDetails( PolicyVO policyDetails ){
		this.policyDetails = policyDetails;
	}

	/**
	 * @return the comments
	 */
	public CommentsVO getComments(){
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments( CommentsVO comments ){
		this.comments = comments;
	}

	public CommonVO getCommonDetails() {
		return commonDetails;
	}

	public void setCommonDetails(CommonVO commonDetails) {
		this.commonDetails = commonDetails;
	}
	
}
