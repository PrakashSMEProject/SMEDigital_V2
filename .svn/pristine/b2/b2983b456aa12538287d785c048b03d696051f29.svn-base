package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1017948
*/

public class DebitNoteDetailsVO extends BaseVO{

    private Long dndPolicyNo;
    private Long dndPolicyId;
    private Long dndEndtId;
    private Long dndDebitNoteNo;
    private String dndDebitNoteDate;
    private Integer dndPolicyYear;


	private static final long serialVersionUID = 1L;

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if("dndDebitNoteNo".equals(fieldName)) fieldValue = getDndDebitNoteNo();
		if("dndDebitNoteDate".equals(fieldName)) fieldValue = getDndDebitNoteDate();
		if("dndPolicyNo".equals(fieldName)) fieldValue = getDndPolicyNo();
		if("dndEndtId".equals(fieldName)) fieldValue = getDndEndtId();
		if("dndPolicyYear".equals(fieldName)) fieldValue = getDndPolicyYear();
		if("dndPolicyId".equals(fieldName)) fieldValue = getDndPolicyId();
		
		return fieldValue;
	}
	

	

	public Long getDndPolicyId(){
		return dndPolicyId;
	}
	
	public void setDndPolicyId( Long dndPolicyId ){
		this.dndPolicyId = dndPolicyId;
	}
	
	public Integer getDndPolicyYear(){
		return dndPolicyYear;
	}

	public void setDndPolicyYear( Integer dndPolicyYear ){
		this.dndPolicyYear = dndPolicyYear;
	}

	public Long getDndPolicyNo(){
		return dndPolicyNo;
	}

	public void setDndPolicyNo( Long dndPolicyNo ){
		this.dndPolicyNo = dndPolicyNo;
	}

	public void setDndEndtId( Long dndEndtId ){
		this.dndEndtId = dndEndtId;
	}

	public Long getDndEndtId(){
		return dndEndtId;
	}

	public Long getDndDebitNoteNo(){
		return dndDebitNoteNo;
	}

	public void setDndDebitNoteNo( Long dndDebitNoteNo ){
		this.dndDebitNoteNo = dndDebitNoteNo;
	}

	public String getDndDebitNoteDate(){
		return dndDebitNoteDate;
	}

	public void setDndDebitNoteDate( String dndDebitNoteDate ){
		this.dndDebitNoteDate = dndDebitNoteDate;
	}

}
