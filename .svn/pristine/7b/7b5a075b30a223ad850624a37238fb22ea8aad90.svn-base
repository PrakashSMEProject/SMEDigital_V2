package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1017948
 */

public class ReceiptDetailsVO extends BaseVO{

	private Long rcdPolicyNo;
	private Long rcdPolicyId;
    private Long rcdEndtId;
    private Long rcdReceiptNo;
    private String rcdReceiptDate;

	private static final long serialVersionUID = 1L;

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if("rcdPolicyNo".equals(fieldName)) fieldValue = getRcdPolicyNo();
		if("rcdPolicyId".equals(fieldName)) fieldValue = getRcdPolicyId();
		if("rcdEndtId".equals(fieldName)) fieldValue = getRcdEndtId();
		if("rcdReceiptNo".equals(fieldName)) fieldValue = getRcdReceiptNo();
		if("rcdReceiptDate".equals(fieldName)) fieldValue = getRcdReceiptDate();
		
		return fieldValue;
	}

	
	public Long getRcdPolicyId(){
		return rcdPolicyId;
	}

	public void setRcdPolicyId( Long rcdPolicyId ){
		this.rcdPolicyId = rcdPolicyId;
	}

	public Long getRcdPolicyNo(){
		return rcdPolicyNo;
	}

	public void setRcdPolicyNo( Long rcdPolicyNo ){
		this.rcdPolicyNo = rcdPolicyNo;
	}

	
	public Long getRcdEndtId(){
		return rcdEndtId;
	}

	public void setRcdEndtId( Long rcdEndtId ){
		this.rcdEndtId = rcdEndtId;
	}

	public Long getRcdReceiptNo(){
		return rcdReceiptNo;
	}

	public void setRcdReceiptNo( Long rcdReceiptNo ){
		this.rcdReceiptNo = rcdReceiptNo;
	}

	public String getRcdReceiptDate(){
		return rcdReceiptDate;
	}

	public void setRcdReceiptDate( String rcdReceiptDate ){
		this.rcdReceiptDate = rcdReceiptDate;
	}
	
}
