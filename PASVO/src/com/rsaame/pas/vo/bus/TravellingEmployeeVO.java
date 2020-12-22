package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.utils.Utils;

public class TravellingEmployeeVO extends RiskGroupDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -770538478012593693L;
	/**
	 * 
	 */
	
	private String name;
	private String dateOfBirth;
	private SumInsuredVO sumInsuredDtl;
	private String gprId;
	private Integer index;
	
	public String getName(){
		return name;
	}
	public void setName( String name ){
		this.name = name;
	}
	
	public String getDateOfBirth(){
		return dateOfBirth;
	}
	public void setDateOfBirth( String dateOfBirth ){
		this.dateOfBirth = dateOfBirth;
	}
	public SumInsuredVO getSumInsuredDtl(){
		return sumInsuredDtl;
	}
	public void setSumInsuredDtl( SumInsuredVO sumInsuredDtl ){
		this.sumInsuredDtl = sumInsuredDtl;
	}
	public String getGprId(){
		return gprId;
	}
	public void setGprId( String gprId ){
		this.gprId = gprId;
	}
	
	public Integer getIndex(){
		return index;
	}
	public void setIndex( Integer index ){
		this.index = index;
	}
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "name".equals( fieldName ) ) fieldValue = getName();
		if( "dateOfBirth".equals( fieldName ) ) fieldValue = getDateOfBirth();
		if( "sumInsuredDtl".equals( fieldName ) ) fieldValue = getSumInsuredDtl();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();
		
		return fieldValue;
	}
	
	@Override
	public String toString() {
		return "TravellingEmployeeVO [name=" + name + "dateOfBirth="+dateOfBirth+"sumInsuredDtl="+sumInsuredDtl
		+"gprId="+gprId+"]";
	}	
	
	@Override
	public boolean equals( Object obj ){
		boolean equal = false;

		if(Utils.isEmpty( obj ) || !(obj instanceof TravellingEmployeeVO))
		return false;
		
		if(Utils.isEmpty( this.getGprId() ) && Utils.isEmpty(((TravellingEmployeeVO)obj).getGprId()))
				return true;
		
		if(!Utils.isEmpty( ((TravellingEmployeeVO)obj).getGprId() )){
			
			if(this.getGprId().equalsIgnoreCase( ((TravellingEmployeeVO)obj).getGprId() )){
				return true;
			}
		}
		
		return equal;
	}
	
	@Override
	public int hashCode(){
		
		return 1;
	}
}
