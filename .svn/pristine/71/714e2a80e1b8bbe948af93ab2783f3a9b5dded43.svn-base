/*
 * Holds traveler details
 */
package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * 
 * @author m1017029
 * 
 */
public class TravelerDetailsVO extends PremiumVO implements Comparable<TravelerDetailsVO>{

	private static final long serialVersionUID = -2930086406042387814L;

	private String name;
	private Date dateOfBirth;
	private Byte relation;
	private Short nationality;
	private char gender;
	private BigDecimal gprId;
	private Date vsd;
	private BigDecimal sumInsured;

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if( "name".equals( fieldName ) ) fieldValue = getName();
		if( "dateOfBirth".equals( fieldName ) ) fieldValue = getDateOfBirth();
		if( "relation".equals( fieldName ) ) fieldValue = getRelation();
		if( "nationality".equals( fieldName ) ) fieldValue = getNationality();
		if( "gprId".equals( fieldName ) ) fieldValue = getGprId();
		if( "vsd".equals( fieldName ) ) fieldValue = getVsd();
		if( "gender".equals( fieldName ) ) fieldValue = getGender();
		return fieldValue;
	}

	/**
	 * @return String
	 */
	public String getName(){
		return name;
	}

	/**
	 * @param name
	 */
	public void setName( String name ){
		this.name = name;
	}

	/**
	 * @return Date
	 */
	public Date getDateOfBirth(){
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 */
	public void setDateOfBirth( Date dateOfBirth ){
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return String
	 */
	public Byte getRelation(){
		return relation;
	}

	/**
	 * @param relation
	 */
	public void setRelation( Byte relation ){
		this.relation = relation;
	}

	/**
	 * @return String
	 */
	public Short getNationality(){
		return nationality;
	}

	/**
	 * @param nationality
	 */
	public void setNationality( Short nationality ){
		this.nationality = nationality;
	}

	/**
	 * @return the gprId
	 */
	public BigDecimal getGprId(){
		return gprId;
	}

	/**
	 * @param gprId the gprId to set
	 */
	public void setGprId( BigDecimal gprId ){
		this.gprId = gprId;
	}
	
	public Date getVsd(){
		return vsd;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}

	/**
	 * @return the sumInsured
	 */
	public BigDecimal getSumInsured(){
		return sumInsured;
	}

	/**
	 * @param sumInsured the sumInsured to set
	 */
	public void setSumInsured( BigDecimal sumInsured ){
		this.sumInsured = sumInsured;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( gprId == null ) ? 0 : gprId.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		TravelerDetailsVO other = (TravelerDetailsVO) obj;
		if( gprId == null ){
			if( other.gprId != null ) return false;
		}
		else if( !gprId.equals( other.gprId ) ) return false;
		return true;
	}

	@Override
	public int compareTo( TravelerDetailsVO other ){
		if( !Utils.isEmpty( this ) && !Utils.isEmpty( other ) && !Utils.isEmpty( this.getGprId() ) && !Utils.isEmpty( other.getGprId() ) ){
			return this.getGprId().intValue() - other.getGprId().intValue();
		}
		return 0;
	}
	
}
