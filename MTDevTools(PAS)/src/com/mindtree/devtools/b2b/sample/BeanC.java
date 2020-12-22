/**
 * 
 */
package com.mindtree.devtools.b2b.sample;

import java.io.Serializable;

import com.mindtree.ruc.cmn.utils.List;

/**
 * @author smurthy
 *
 */
public class BeanC implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String string;
	private List<ComplexObject> complexObjectList = new List<ComplexObject>( ComplexObject.class );
	private ComplexObject[] complexObjectArray = null;
	private LevelTwo levelTwo;

	/**
	 * @return the string
	 */
	public String getString(){
		return string;
	}

	/**
	 * @param string the string to set
	 */
	public void setString( String string ){
		this.string = string;
	}

	public LevelTwo getLevelTwo(){
		return levelTwo;
	}

	public void setLevelTwo( LevelTwo levelTwo ){
		this.levelTwo = levelTwo;
	}

	public List<ComplexObject> getComplexObjectList(){
		return complexObjectList;
	}

	public void setComplexObjectList( List<ComplexObject> complexObjectList ){
		this.complexObjectList = complexObjectList;
	}

	public ComplexObject[] getComplexObjectArray(){
		return complexObjectArray;
	}

	public void setComplexObjectArray( ComplexObject[] complexObjectArray ){
		this.complexObjectArray = complexObjectArray;
	}

}
