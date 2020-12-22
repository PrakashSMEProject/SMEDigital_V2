package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.app.Contents;

public class MachineDetailsVO extends RiskGroupDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer machineryType;
	private String machineDescription="";
	private Integer yearOfMake;
	private SumInsuredVO sumInsuredVO;
	private Contents contents;
	private PremiumVO premium;
	private Integer index;

	public PremiumVO getPremium(){
		return premium;
	}

	public void setPremium( PremiumVO premium ){
		this.premium = premium;
	}

	public Contents getContents(){
		return contents;
	}

	public void setContents( Contents contents ){
		this.contents = contents;
	}

	public Integer getMachineryType(){
		return machineryType;
	}

	public void setMachineryType( Integer machineryType ){
		this.machineryType = machineryType;
	}

	public String getMachineDescription(){
		return machineDescription;
	}

	public void setMachineDescription( String machineDescription ){
		this.machineDescription = machineDescription;
	}

	public Integer getYearOfMake(){
		return yearOfMake;
	}

	public void setYearOfMake( Integer yearOfMake ){
		this.yearOfMake = yearOfMake;
	}

	public SumInsuredVO getSumInsuredVO(){
		return sumInsuredVO;
	}

	public void setSumInsuredVO( SumInsuredVO sumInsuredVO ){
		this.sumInsuredVO = sumInsuredVO;
	}

	public Integer getIndex(){
		return index;
	}

	public void setIndex( Integer index ){
		this.index = index;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "machineryType".equals( fieldName ) ) fieldValue = getMachineryType();
		if( "machineDescription".equals( fieldName ) ) fieldValue = getMachineDescription();
		if( "yearOfMake".equals( fieldName ) ) fieldValue = getYearOfMake();
		if( "sumInsuredVO".equals( fieldName ) ) fieldValue = getSumInsuredVO();
		if( "contents".equals( fieldName ) ) fieldValue = getContents();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();

		return fieldValue;
	}

	@Override
	public boolean equals( Object obj ){
		boolean equal = false;

		if( Utils.isEmpty( obj ) || !( obj instanceof MachineDetailsVO ) ) return false;

		if( Utils.isEmpty( this.getContents().getCoverId() ) && Utils.isEmpty( ( (MachineDetailsVO) obj ).getContents().getCoverId() ) ) return true;

		if( !Utils.isEmpty( ( (MachineDetailsVO) obj ).getContents().getCoverId() ) ){

			if( this.getContents().getCoverId().toString().equalsIgnoreCase( ( (MachineDetailsVO) obj ).getContents().getCoverId().toString() ) ){
				return true;
			}
		}

		return equal;
	}

	@Override
	public String toString(){
		return "MachineDetailsVO [machineryType=" + machineryType + ", machineDescription=" + machineDescription + ", yearOfMake=" + yearOfMake + ", sumInsuredVO=" + sumInsuredVO
				+ ", contents=" + contents + ", premium=" + premium + "]";
	}

	@Override
	public int hashCode(){

		return 1;
	}

}
