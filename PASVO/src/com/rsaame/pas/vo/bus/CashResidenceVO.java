package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;

public class CashResidenceVO extends BaseVO implements ICashDetails{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Date vsd;
	
	private String empName;
	private String occupation;
	private SumInsuredVO sumInsuredDets;
	private boolean toBeDeleted;
	private Integer index;
	
	public Integer getIndex(){
		return index;
	}

	public void setIndex( Integer index ){
		this.index = index;
	}

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "empName".equals( fieldName ) ) fieldValue = getEmpName();
		if( "occupation".equals( fieldName ) ) fieldValue = getOccupation();
		if( "sumInsuredDets".equals( fieldName ) ) fieldValue = getSumInsuredDets();
		if( "id".equals( fieldName ) ) fieldValue = getId();
		if( "vsd".equals( fieldName ) ) fieldValue = getVsd();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();

		return fieldValue;
	}
	
	public String getEmpName(){
		return empName;
	}
	public void setEmpName( String empName ){
		this.empName = empName;
	}
	public String getOccupation(){
		return occupation;
	}
	public void setOccupation( String occupation ){
		this.occupation = occupation;
	}

	/**
	 * @return the sumInsuredDets
	 */
	public SumInsuredVO getSumInsuredDets() {
		return sumInsuredDets;
	}

	/**
	 * @param sumInsuredDets the sumInsuredDets to set
	 */
	public void setSumInsuredDets(SumInsuredVO sumInsuredDets) {
		this.sumInsuredDets = sumInsuredDets;
	}

	@Override
	public Long getId(){
		return id;
	}

	@Override
	public void setId( Object id ){
		this.id = (Long) id;
	}

	public Date getVsd(){
		return vsd;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}
	
	@Override
	public boolean isToBeDeleted(){
		return toBeDeleted;
	}

	@Override
	public void setToBeDeleted( boolean toBeDeleted ){
		this.toBeDeleted = toBeDeleted;
	}


	@Override
	public boolean compareId( Long id ){
		boolean isSame = true;
		if( this.id == null && id != null ) isSame = false;
		else if( this.id != null && id == null ) isSame = false;
		else isSame = this.id.equals( id );
		
		return isSame;
	}
	
		@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( empName == null ) ? 0 : empName.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( occupation == null ) ? 0 : occupation.hashCode() );
		result = prime * result + ( ( sumInsuredDets == null ) ? 0 : sumInsuredDets.hashCode() );
		result = prime * result + ( toBeDeleted ? 1231 : 1237 );
		result = prime * result + ( ( vsd == null ) ? 0 : vsd.hashCode() );
		return result;
	}

	
	@Override
	public boolean equals( Object otherVO ){
		//Added Utils.isEmpty() to avoid sonar violation on 27-9-2017
		if (getClass() != otherVO.getClass()||  Utils.isEmpty(otherVO))     return false;
		//if( otherVO == null ) return false;

		ICashDetails other = (ICashDetails) otherVO;
		return this.compareId( (Long) other.getId() );
	}
}

