/**
 * 
 */
package com.mindtree.devtools.b2b.sample;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author SMurthy
 *
 */
public class BeanA implements Serializable{
	private static final long serialVersionUID = 1L;

	private Byte byteObject;
	private byte bytePrimitive;
	private Character characterObject;
	private char charPrimitive;
	//Custom object reference
	private ComplexObject complexObject;
	private ComplexObject[] complexObjectArray;

	//List of Complex Object
	private List<ComplexObject> complexObjectList = new com.mindtree.ruc.cmn.utils.List<ComplexObject>( ComplexObject.class);
	
	//Set of complex object
	private Set<ComplexObject> complexObjectSet;

	private Date date;
	private Double doubleObject;

	private double doublePrimitive;

	private Float floatObject;

	private float floatPrimitive;
	private List<Integer> integerList;
	private Integer integerObject;
	private Set<Integer> integerSet;
	//Basic data types
	private int intprimitive;
	private Long longObject;
	private long longPrimitive;
	private String str;

	private String[] strArray;

	//Collections types
	private List<String> stringList;

	private Set<String> stringSet;

	private Map<String, Object> strObjectMap;
	private Map<String, String> strStringMap;
	private Map<Integer, LevelTwo> strLevelTwoMap = new com.mindtree.ruc.cmn.utils.Map<Integer, LevelTwo>( Integer.class, LevelTwo.class );
	
	public Map<Integer, LevelTwo> getStrLevelTwoMap(){
		return strLevelTwoMap;
	}
	public void setStrLevelTwoMap( Map<Integer, LevelTwo> strLevelTwoMap ){
		this.strLevelTwoMap = strLevelTwoMap;
	}
	public Boolean getBeanABoolean(){
		return beanABoolean;
	}
	private Boolean beanABoolean;
	
	/**
	 * @return the byteObject
	 */
	public Byte getByteObject(){
		return byteObject;
	}
	/**
	 * @return the bytePrimitive
	 */
	public byte getBytePrimitive(){
		return bytePrimitive;
	}
	/**
	 * @return the characterObject
	 */
	public Character getCharacterObject(){
		return characterObject;
	}
	/**
	 * @return the charPrimitive
	 */
	public char getCharPrimitive(){
		return charPrimitive;
	}

	/**
	 * @return the complexObject
	 */
	public ComplexObject getComplexObject(){
		return complexObject;
	}

	/**
	 * @return the complexObjectArray
	 */
	public ComplexObject[] getComplexObjectArray(){
		return complexObjectArray;
	}

	/**
	 * @return the complexObjectList
	 */
	public List<ComplexObject> getComplexObjectList(){
		return complexObjectList;
	}

	/**
	 * @return the complexObjectSet
	 */
	public Set<ComplexObject> getComplexObjectSet(){
		return complexObjectSet;
	}

	/**
	 * @return the date
	 */
	public Date getDate(){
		return date;
	}

	/**
	 * @return the doubleObject
	 */
	public Double getDoubleObject(){
		return doubleObject;
	}

	/**
	 * @return the doublePrimitive
	 */
	public double getDoublePrimitive(){
		return doublePrimitive;
	}

	/**
	 * @return the floatObject
	 */
	public Float getFloatObject(){
		return floatObject;
	}

	/**
	 * @return the floatPrimitive
	 */
	public float getFloatPrimitive(){
		return floatPrimitive;
	}

	/**
	 * @return the integerList
	 */
	public List<Integer> getIntegerList(){
		return integerList;
	}

	/**
	 * @return the integerObject
	 */
	public Integer getIntegerObject(){
		return integerObject;
	}

	/**
	 * @return the integerSet
	 */
	public Set<Integer> getIntegerSet(){
		return integerSet;
	}

	/**
	 * @return the intprimitive
	 */
	public int getIntprimitive(){
		return intprimitive;
	}

	/**
	 * @return the longObject
	 */
	public Long getLongObject(){
		return longObject;
	}

	/**
	 * @return the longPrimitive
	 */
	public long getLongPrimitive(){
		return longPrimitive;
	}

	/**
	 * @return the str
	 */
	public String getStr(){
		return str;
	}

	/**
	 * @return the strArray
	 */
	public String[] getStrArray(){
		return strArray;
	}

	/**
	 * @return the stringList
	 */
	public List<String> getStringList(){
		return stringList;
	}

	/**
	 * @return the stringSet
	 */
	public Set<String> getStringSet(){
		return stringSet;
	}

	/**
	 * @return the strObjectMap
	 */
	public Map<String, Object> getStrObjectMap(){
		return strObjectMap;
	}

	/**
	 * @return the strStringMap
	 */
	public Map<String, String> getStrStringMap(){
		return strStringMap;
	}

	/**
	 * @param byteObject the byteObject to set
	 */
	public void setByteObject( Byte byteObject ){
		this.byteObject = byteObject;
	}

	/**
	 * @param bytePrimitive the bytePrimitive to set
	 */
	public void setBytePrimitive( byte bytePrimitive ){
		this.bytePrimitive = bytePrimitive;
	}

	/**
	 * @param characterObject the characterObject to set
	 */
	public void setCharacterObject( Character characterObject ){
		this.characterObject = characterObject;
	}

	/**
	 * @param charPrimitive the charPrimitive to set
	 */
	public void setCharPrimitive( char charPrimitive ){
		this.charPrimitive = charPrimitive;
	}

	/**
	 * @param complexObject the complexObject to set
	 */
	public void setComplexObject( ComplexObject complexObject ){
		this.complexObject = complexObject;
	}

	/**
	 * @param complexObjectArray the complexObjectArray to set
	 */
	public void setComplexObjectArray( ComplexObject[] complexObjectArray ){
		this.complexObjectArray = complexObjectArray;
	}

	/**
	 * @param complexObjectList the complexObjectList to set
	 */
	public void setComplexObjectList( List<ComplexObject> complexObjectList ){
		this.complexObjectList = complexObjectList;
	}

	/**
	 * @param complexObjectSet the complexObjectSet to set
	 */
	public void setComplexObjectSet( Set<ComplexObject> complexObjectSet ){
		this.complexObjectSet = complexObjectSet;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate( Date date ){
		this.date = date;
	}

	/**
	 * @param doubleObject the doubleObject to set
	 */
	public void setDoubleObject( Double doubleObject ){
		this.doubleObject = doubleObject;
	}

	/**
	 * @param doublePrimitive the doublePrimitive to set
	 */
	public void setDoublePrimitive( double doublePrimitive ){
		this.doublePrimitive = doublePrimitive;
	}

	/**
	 * @param floatObject the floatObject to set
	 */
	public void setFloatObject( Float floatObject ){
		this.floatObject = floatObject;
	}

	/**
	 * @param floatPrimitive the floatPrimitive to set
	 */
	public void setFloatPrimitive( float floatPrimitive ){
		this.floatPrimitive = floatPrimitive;
	}

	/**
	 * @param integerList the integerList to set
	 */
	public void setIntegerList( List<Integer> integerList ){
		this.integerList = integerList;
	}

	/**
	 * @param integerObject the integerObject to set
	 */
	public void setIntegerObject( Integer integerObject ){
		this.integerObject = integerObject;
	}

	/**
	 * @param integerSet the integerSet to set
	 */
	public void setIntegerSet( Set<Integer> integerSet ){
		this.integerSet = integerSet;
	}

	/**
	 * @param intprimitive the intprimitive to set
	 */
	public void setIntprimitive( int intprimitive ){
		this.intprimitive = intprimitive;
	}

	/**
	 * @param longObject the longObject to set
	 */
	public void setLongObject( Long longObject ){
		this.longObject = longObject;
	}

	/**
	 * @param longPrimitive the longPrimitive to set
	 */
	public void setLongPrimitive( long longPrimitive ){
		this.longPrimitive = longPrimitive;
	}

	/**
	 * @param str the str to set
	 */
	public void setStr( String str ){
		this.str = str;
	}

	/**
	 * @param strArray the strArray to set
	 */
	public void setStrArray( String[] strArray ){
		this.strArray = strArray;
	}

	/**
	 * @param stringList the stringList to set
	 */
	public void setStringList( List<String> stringList ){
		this.stringList = stringList;
	}

	/**
	 * @param stringSet the stringSet to set
	 */
	public void setStringSet( Set<String> stringSet ){
		this.stringSet = stringSet;
	}

	/**
	 * @param strObjectMap the strObjectMap to set
	 */
	public void setStrObjectMap( Map<String, Object> strObjectMap ){
		this.strObjectMap = strObjectMap;
	}

	/**
	 * @param strStringMap the strStringMap to set
	 */
	public void setStrStringMap( Map<String, String> strStringMap ){
		this.strStringMap = strStringMap;
	}
	public Boolean isBeanABoolean(){
		return beanABoolean;
	}
	public void setBeanABoolean( Boolean beanABoolean ){
		this.beanABoolean = beanABoolean;
	}

}
