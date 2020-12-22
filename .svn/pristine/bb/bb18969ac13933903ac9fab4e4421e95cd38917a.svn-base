package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1017948
 */

public class CreditNoteDetailsVO extends BaseVO{

  
	private static final long serialVersionUID = 1L;

	private Long cndCreditNoteNo;
    private String cndCreditNoteDate;
    private Long cndPolicyNo;
    private Long cndPolicyId;
    private Long cndEndtId;
    private Integer cndPolicyYear;


	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if("cndCreditNoteNo".equals(fieldName)) fieldValue = getCndCreditNoteNo();
		if("cndCreditNoteDate".equals(fieldName)) fieldValue = getCndCreditNoteDate();
		if("cndPolicyNo".equals(fieldName)) fieldValue = getCndPolicyNo();
		if("cndEndtId".equals(fieldName)) fieldValue = getCndEndtId();
		if("cndPolicyYear".equals(fieldName)) fieldValue = getCndPolicyYear();
		
		return fieldValue;
	}

	
    public Integer getCndPolicyYear(){
		return cndPolicyYear;
	}


	public void setCndPolicyYear( Integer cndPolicyYear ){
		this.cndPolicyYear = cndPolicyYear;
	}
	
	public Long getCndCreditNoteNo(){
		return cndCreditNoteNo;
	}
	
	public Long getCndPolicyId(){
		return cndPolicyId;
	}

	public void setCndPolicyId( Long cndPolicyId ){
		this.cndPolicyId = cndPolicyId;
	}


	public void setCndCreditNoteNo( Long cndCreditNoteNo ){
		this.cndCreditNoteNo = cndCreditNoteNo;
	}

	public void setCndCreditNoteDate( String cndCreditNoteDate ){
		this.cndCreditNoteDate = cndCreditNoteDate;
	}


	public String getCndCreditNoteDate(){
		return cndCreditNoteDate;
	}


	public Long getCndPolicyNo(){
		return cndPolicyNo;
	}


	public void setCndPolicyNo( Long cndPolicyNo ){
		this.cndPolicyNo = cndPolicyNo;
	}


	public Long getCndEndtId(){
		return cndEndtId;
	}


	public void setCndEndtId( Long cndEndtId ){
		this.cndEndtId = cndEndtId;
	}


}
