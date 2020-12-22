package com.rsaame.pas.vo.app;

import java.math.BigDecimal;
import java.util.Comparator;

import com.mindtree.ruc.cmn.base.BaseVO;


public class LookUpVO extends BaseVO implements Comparable<LookUpVO>{

	private static final long serialVersionUID = 1L;

	public String category;

	public String name;

	public BigDecimal code;

	public String level1;

	public String level2;

	
		/**
	 * @return the level1
	 */
	public String getLevel1(){
		return level1;
	}

	/**
	 * @return the level2
	 */
	public String getLevel2(){
		return level2;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1( String level1 ){
		this.level1 = level1;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2( String level2 ){
		this.level2 = level2;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the decsription
	 */
	

	

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param decsription the decsription to set
	 */
	


	/**
	 * @return the description
	 */
	public String getDescription() {
		return name;
	}

	/**
	 * @return the code
	 */
	public BigDecimal getCode(){
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode( BigDecimal code ){
		this.code = code;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.name = description;
	}

	public Object getFieldValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(LookUpVO obj) {
		 int descCmp;
		if(obj.getCategory().equals("DEDUCTIBLES")){
			descCmp=code.compareTo(obj.getCode());
		}else if(obj.getCategory().equals("PAS_LIMIND")){
			descCmp=code.compareTo(obj.getCode());
		}
		else if(obj.getCategory().equals("SINGLE_TRANSIT_LIMIT")){
			descCmp=code.compareTo(obj.getCode());
		}
		else if(obj.getCategory().startsWith("PAS_HOME_")){
			descCmp=code.compareTo(obj.getCode());
		}
		else if(obj.getCategory().equals("PAS_TR_PRD")){
			descCmp=code.compareTo(obj.getCode());
		}
		else if(obj.getCategory().equals("PAS_GI_EMP")){
			descCmp=code.compareTo(obj.getCode());
		}
		else if(obj.getCategory().equals("PAS_AN_TRN")){
			descCmp=code.compareTo(obj.getCode());
		}else if(obj.getCategory().equals("PAR_CONTENT_SI")){
			descCmp=code.compareTo(obj.getCode());
		}else if(obj.getCategory().equals("PAS_WC_EMP")){
			descCmp=code.compareTo(obj.getCode());
		}
		else{
			descCmp= name.compareTo(obj.getDescription());
		}
		return descCmp;
}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((level1 == null) ? 0 : level1.hashCode());
		result = prime * result + ((level2 == null) ? 0 : level2.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		LookUpVO other = (LookUpVO) obj;
		if( category == null ){
			if( other.category != null ) return false;
		}
		else if( !category.equals( other.category ) ) return false;
		if( code == null ){
			if( other.code != null ) return false;
		}
		else if( !code.equals( other.code ) ) return false;
		if( level1 == null ){
			if( other.level1 != null ) return false;
		}
		else if( !level1.equals( other.level1 ) ) return false;
		if( level2 == null ){
			if( other.level2 != null ) return false;
		}
		else if( !level2.equals( other.level2 ) ) return false;
		if( name == null ){
			if( other.name != null ) return false;
		}
		else if( !name.equals( other.name ) ) return false;
		return true;
	}
	
	

	

}
