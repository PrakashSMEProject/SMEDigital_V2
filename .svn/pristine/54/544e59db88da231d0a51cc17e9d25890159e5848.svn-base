/**
 * 
 */
package com.mindtree.devtools.b2b.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mindtree.devtools.b2b.utils.BeanMapperConstants;
import com.mindtree.devtools.utils.DevToolsUtils;

/**
 * @author SMurthy
 * 
 */
public class BeanMappingVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String clazzA;

	private String clazzB;

	private String clazzPackage;

	private List<FieldVO> fields;
	
	/** This field represents the set of deep fields that need to be initialised in the mapper class. */
	private Set<String> initFields;
	
	private String id;
	
	/* This indicates if this VO mapping is for generating the reverse mapping. */
	private boolean reverse = false;
	
	/* Indicates the type of mapping. Allowed values are "B2B" (for bean-to-bean) and "R2B" (for request-to-bean). */
	private String mappingType = BeanMapperConstants.MAPPING_TYPE_BEAN_TO_BEAN;
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof BeanMappingVO ) ) return false;
		BeanMappingVO other = (BeanMappingVO) obj;
		if( id == null ){
			if( other.id != null ) return false;
		}
		else if( !id.equals( other.id ) ) return false;
		return true;
	}

	/**
	 * @return the clazzA
	 */
	public String getClazzA(){
		return clazzA;
	}

	/**
	 * @return the clazzB
	 */
	public String getClazzB(){
		return clazzB;
	}

	public String getClazzPackage(){
		return clazzPackage;
	}

	/**
	 * @return the fields
	 */
	public List<FieldVO> getFields(){
		return fields;
	}

	/**
	 * @return the id
	 */
	public String getId(){
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	/**
	 * @param clazzA
	 *            the clazzA to set
	 */
	public void setClazzA( String clazzA ){
		this.clazzA = clazzA;
	}

	/**
	 * @param clazzB
	 *            the clazzB to set
	 */
	public void setClazzB( String clazzB ){
		this.clazzB = clazzB;
	}

	public void setClazzPackage( String clazzPackage ){
		this.clazzPackage = clazzPackage;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields( List<FieldVO> fields ){
		this.fields = fields;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId( String id ){
		this.id = id;
	}

	public boolean isReverse(){
		return reverse;
	}

	public void setReverse( boolean reverse ){
		this.reverse = reverse;
	}
	
	public Set<String> getInitFields(){
		return this.initFields;
	}

	public void addInitField( String field ){
		if( initFields == null ){
			initFields = new HashSet<String>();
		}
		
		initFields.add( field );
	}

	public String getMappingType(){
		return mappingType;
	}

	public void setMappingType( String mappingType ){
		this.mappingType = mappingType;
	}
}
