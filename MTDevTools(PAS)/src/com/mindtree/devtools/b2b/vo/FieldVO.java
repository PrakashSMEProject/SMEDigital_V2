/**
 * 
 */
package com.mindtree.devtools.b2b.vo;

import java.io.Serializable;

import com.mindtree.devtools.utils.DevToolsUtils;



/**
 * @author SMurthy
 */
public class FieldVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/* This field represents the field-b mapped in beanmapper.xml */
	private String destAttribute;

	/* This field represents the field-b, without the last field in the deep field, mapped in beanmapper.xml.
	 * The primary intent of this field is for initialization of  */
	private String destAttributeInit;

	/* This field represents the getter method to access the field-b mapped in beanmapper.xml */
	private String destAttributeAccessorMethod;

	/* This field represents the setter method for modifying the destination attribute */
	private String destAttributeMutatorMethod;

	/* This field represents the getter method used to access attribute B */
	private String destMutatorMethod;

	/* Thie field tells whether afield-b is a deep attribute in beanmapper.xml */
	private boolean isDeepDestAttribute;

	/* Thie field tells whether afield-b is a deep attribute in beanmapper.xml */
	private boolean isDeepSrcAttribute;

	/* This field represents the mapper type for a field */
	private String mapperType;

	/* This field represents the name of the class which has a mapping for the fields configured */
	private String ref;

	/* This field represents the getter method used to access attribute A */
	private String srcAccessorMethod;

	/* This field represents the field-a mapped in beanmapper.xml */
	private String srcAttribute;

	/* This field represents the getter method for accessing the source attribute */
	private String srcAttributeAccessorMethod;

	/* This field contains the null checks to be carried out before accessing a deep field in the source attribute */
	private String srcAttributeNullCheckChain;
	
	/* The converter class for handling conversions between "a" and "b" fields. */
	private String converterClass;
	
	/* This indicates if the "a" and "b" fields have been set according to the converter class's "A" and "B" generic
	 * variables order. This will lead to the generation of <Converter>.getBFromA() or <Converter>.getAFromB(). */
	private boolean converterDefaultAToB;
	
	/* The comma-separated name-value pair properties set for "a". */
	private String aProperties;
	
	/* The comma-separated name-value pair properties set for "b". */
	private String bProperties;
	
	/* Indicates if "a" is a boolean. This special treatment for boolean is because of the JavaBeans style accessor method
	 * names convention for boolean fields. */
	private boolean srcBooleanType;

	/* Indicates if "b" is a boolean. This special treatment for boolean is because of the JavaBeans style accessor method
	 * names convention for boolean fields. */
	private boolean destBooleanType;

	/* Indicates the Java type or primitive type of the source field. */
	private String srcType;

	/* Indicates the Java type or primitive type of the destination field. */
	private String destType;
	
	/* Indicates if the field is to got as HttpServletRequest.getParameter() or .getAttribute(). Evidently, this is useful only
	 * in the case of Request-Bean mapping. */
	private boolean httpAttr = true;
	
	/* The following fields are for handling deep-collection mappings. That is, mappings where values of deep elements in one 
	 * collection have to be mapped to deep fields within elements of another collection. 
	 * 
	 * Example for multi-element mapping:
	 * <field>
	 *     <a>a.b.c[].d</a>
	 *     <b>x.y[].z</b>
	 * </field>
	 * 
	 * Example for single-element mapping:
	 * <field>
	 *     <a>a.b.c[ 0 ].d</a>
	 *     <b>x.y[ 'KEY' ].z</b>
	 * </field>
	 * 
	 */
	private boolean deepCollMultiMapping;
	private String srcAttrCollType;
	private boolean srcAttrMultiElemMapping;
	private String srcAttrAccessorMethodTillColl; //The part of the 
	private String srcAttrAccessorMethodAfterColl;
	private boolean srcHasFieldsAfterIndex;
	private String srcAttrKeyInColl; //The key inside the collection (if it is a single mapping)
	private String destAttrCollType;
	private boolean destAttrMultiElemMapping;
	private String destAttrMutatorMethodTillColl;
	private String destAttrMutatorMethodAfterColl;
	private boolean destHasFieldsAfterIndex;
	private String destAttrKeyInColl; //The key inside the collection (if it is a single mapping)

	/**
	 * Creates a copy of this FieldVO instance and returns with exactly the same values.
	 * @return A new FieldVO instance with the same values as this one.
	 */
	public FieldVO getCopy(){
		FieldVO copy = new FieldVO();
		copy.destAttribute = this.destAttribute;
		copy.destAttributeAccessorMethod = this.destAttributeAccessorMethod;
		copy.destAttributeMutatorMethod = this.destAttributeMutatorMethod;
		copy.destMutatorMethod = this.destMutatorMethod;
		copy.isDeepDestAttribute = this.isDeepDestAttribute;
		copy.isDeepSrcAttribute = this.isDeepSrcAttribute;
		copy.mapperType = this.mapperType;
		copy.ref = this.ref;
		copy.srcAccessorMethod = this.srcAccessorMethod;
		copy.srcAttribute = this.srcAttribute;
		copy.srcAttributeAccessorMethod = this.srcAttributeAccessorMethod;
		copy.srcAttributeNullCheckChain = this.srcAttributeNullCheckChain;
		copy.converterClass = this.converterClass;
		copy.converterDefaultAToB = this.converterDefaultAToB;
		copy.aProperties = this.aProperties;
		copy.bProperties = this.bProperties;
		copy.srcBooleanType = this.srcBooleanType;
		copy.destBooleanType = this.destBooleanType;
		copy.srcType = this.srcType;
		copy.destType = this.destType;
		copy.httpAttr = false;
		copy.deepCollMultiMapping = this.deepCollMultiMapping;
		copy.srcAttrCollType = this.srcAttrCollType;
		copy.srcAttrMultiElemMapping = this.srcAttrMultiElemMapping;
		copy.srcAttrAccessorMethodTillColl = this.srcAttrAccessorMethodTillColl;
		copy.srcAttrAccessorMethodAfterColl = this.srcAttrAccessorMethodAfterColl;
		copy.srcHasFieldsAfterIndex = this.srcHasFieldsAfterIndex;
		copy.srcAttrKeyInColl = this.srcAttrKeyInColl;
		copy.destAttrCollType = this.destAttrCollType;
		copy.destAttrMultiElemMapping = this.destAttrMultiElemMapping;
		copy.destAttrMutatorMethodTillColl = this.destAttrMutatorMethodTillColl;
		copy.destAttrMutatorMethodAfterColl = this.destAttrMutatorMethodAfterColl;
		copy.destHasFieldsAfterIndex = this.destHasFieldsAfterIndex;
		copy.destAttrKeyInColl = this.destAttrKeyInColl;

		return copy;
	}

	/**
	 * @return the destAttribute
	 */
	public String getDestAttribute(){
		return destAttribute;
	}

	/**
	 * @return the destAttributeAccessorMethod
	 */
	public String getDestAttributeAccessorMethod(){
		return destAttributeAccessorMethod;
	}

	/**
	 * @return the destAttributeMutatorMethod
	 */
	public String getDestAttributeMutatorMethod(){
		return destAttributeMutatorMethod;
	}

	/**
	 * @return the destMutatorMethod
	 */
	public String getDestMutatorMethod(){
		return destMutatorMethod;
	}

	/**
	 * @return the mapperType
	 */
	public String getMapperType(){
		return mapperType;
	}

	/**
	 * @return the ref
	 */
	public String getRef(){
		return ref;
	}

	/**
	 * @return the srcAccessorMethod
	 */
	public String getSrcAccessorMethod(){
		return srcAccessorMethod;
	}

	/**
	 * @return the srcAttribute
	 */
	public String getSrcAttribute(){
		return srcAttribute;
	}

	/**
	 * @return the srcAttributeAccessorMethod
	 */
	public String getSrcAttributeAccessorMethod(){
		return srcAttributeAccessorMethod;
	}

	/**
	 * @return the srcAttributeNullCheckChain
	 */
	public String getSrcAttributeNullCheckChain(){
		return srcAttributeNullCheckChain;
	}

	/**
	 * @return the isDeepDestAttribute
	 */
	public boolean isDeepDestAttribute(){
		return isDeepDestAttribute;
	}

	/**
	 * @return the isDeepSrcAttribute
	 */
	public boolean isDeepSrcAttribute(){
		return isDeepSrcAttribute;
	}

	/**
	 * @return
	 */
	public boolean isMapperTypeConfigured(){
		if( DevToolsUtils.isEmpty( this.getMapperType() ) ){
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public boolean isRefConfigured(){
		if( DevToolsUtils.isEmpty( this.getRef() ) ){
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public boolean isSrcAttributeNullCheckAvailable(){
		if( DevToolsUtils.isEmpty( this.getSrcAttributeNullCheckChain() ) ){
			return false;
		}
		return true;
	}

	/**
	 * @param isDeepDestAttribute the isDeepDestAttribute to set
	 */
	public void setDeepDestAttribute( boolean isDeepDestAttribute ){
		this.isDeepDestAttribute = isDeepDestAttribute;
	}

	/**
	 * @param isDeepSrcAttribute the isDeepSrcAttribute to set
	 */
	public void setDeepSrcAttribute( boolean isDeepSrcAttribute ){
		this.isDeepSrcAttribute = isDeepSrcAttribute;
	}

	/**
	 * @param destAttribute the destAttribute to set
	 */
	public void setDestAttribute( String destAttribute ){
		this.destAttribute = destAttribute;
	}

	/**
	 * @param destAttributeAccessorMethod the destAttributeAccessorMethod to set
	 */
	public void setDestAttributeAccessorMethod(
			String destAttributeAccessorMethod ){
		this.destAttributeAccessorMethod = destAttributeAccessorMethod;
	}

	/**
	 * @param destAttributeMutatorMethod the destAttributeMutatorMethod to set
	 */
	public void setDestAttributeMutatorMethod( String destAttributeMutatorMethod ){
		this.destAttributeMutatorMethod = destAttributeMutatorMethod;
	}

	/**
	 * @param destMutatorMethod the destMutatorMethod to set
	 */
	public void setDestMutatorMethod( String destMutatorMethod ){
		this.destMutatorMethod = destMutatorMethod;
	}

	/**
	 * @param mapperType the mapperType to set
	 */
	public void setMapperType( String mapperType ){
		this.mapperType = mapperType;
	}

	/**
	 * @param ref the ref to set
	 */
	public void setRef( String ref ){
		this.ref = ref;
	}

	/**
	 * @param srcAccessorMethod the srcAccessorMethod to set
	 */
	public void setSrcAccessorMethod( String srcAccessorMethod ){
		this.srcAccessorMethod = srcAccessorMethod;
	}

	/**
	 * @param srcAttribute the srcAttribute to set
	 */
	public void setSrcAttribute( String srcAttribute ){
		this.srcAttribute = srcAttribute;
	}

	/**
	 * @param srcAttributeAccessorMethod the srcAttributeAccessorMethod to set
	 */
	public void setSrcAttributeAccessorMethod( String srcAttributeAccessorMethod ){
		this.srcAttributeAccessorMethod = srcAttributeAccessorMethod;
	}

	/**
	 * @param srcAttributeNullCheckChain the srcAttributeNullCheckChain to set
	 */
	public void setSrcAttributeNullCheckChain( String srcAttributeNullCheckChain ){
		this.srcAttributeNullCheckChain = srcAttributeNullCheckChain;
	}

	/**
	 * This method returns true if the source attribute is explicitly configured with the accessor method.
	 * @return
	 */
	public boolean isSrcAttributeAccessorMethodConfigured(){
		if( DevToolsUtils.isEmpty( this.getSrcAccessorMethod() ) ){
			return false;
		}
		return true;
	}

	/**
	 * This method returns true if the destination attribute is explicitly configured with the mutator method.
	 * @return
	 */
	public boolean isDestAttributeMutatorMethodConfigured(){
		if( DevToolsUtils.isEmpty( this.getDestMutatorMethod() ) ){
			return false;
		}
		return true;
	}

	public String getConverterClass(){
		return converterClass;
	}

	public void setConverterClass( String converterClass ){
		this.converterClass = converterClass;
	}

	public boolean isConverterDefaultAToB(){
		return converterDefaultAToB;
	}

	public void setConverterDefaultAToB( boolean converterDefaultAToB ){
		this.converterDefaultAToB = converterDefaultAToB;
	}

	public String getaProperties(){
		return aProperties;
	}

	public void setaProperties( String aProperties ){
		this.aProperties = aProperties;
	}

	public String getbProperties(){
		return bProperties;
	}

	public void setbProperties( String bProperties ){
		this.bProperties = bProperties;
	}
	
	public boolean isConverterClassSet(){
		if( !DevToolsUtils.isEmpty( this.converterClass ) ){
			return true;
		}
		
		return false;
	}

	public boolean isSrcBooleanType(){
		return srcBooleanType;
	}

	public void setSrcBooleanType( boolean srcBooleanType ){
		this.srcBooleanType = srcBooleanType;
	}

	public boolean isDestBooleanType(){
		return destBooleanType;
	}

	public void setDestBooleanType( boolean destBooleanType ){
		this.destBooleanType = destBooleanType;
	}

	public String getSrcType(){
		return srcType;
	}

	public void setSrcType( String srcType ){
		this.srcType = srcType;
	}

	public String getDestType(){
		return destType;
	}

	public void setDestType( String destType ){
		this.destType = destType;
	}

	public boolean isHttpAttr(){
		return httpAttr;
	}

	public void setHttpAttr( boolean httpAttr ){
		this.httpAttr = httpAttr;
	}

	public String getSrcAttrCollType(){
		return srcAttrCollType;
	}

	public void setSrcAttrCollType( String srcAttrCollType ){
		this.srcAttrCollType = srcAttrCollType;
	}

	public String getSrcAttrAccessorMethodTillColl(){
		return srcAttrAccessorMethodTillColl;
	}

	public void setSrcAttrAccessorMethodTillColl( String srcAttrAccessorMethodTillColl ){
		this.srcAttrAccessorMethodTillColl = srcAttrAccessorMethodTillColl;
	}

	public String getSrcAttrAccessorMethodAfterColl(){
		return srcAttrAccessorMethodAfterColl;
	}

	public void setSrcAttrAccessorMethodAfterColl( String srcAttrAccessorMethodAfterColl ){
		this.srcAttrAccessorMethodAfterColl = srcAttrAccessorMethodAfterColl;
	}

	public String getSrcAttrKeyInColl(){
		return srcAttrKeyInColl;
	}

	public void setSrcAttrKeyInColl( String srcAttrKeyInColl ){
		this.srcAttrKeyInColl = srcAttrKeyInColl;
	}

	public String getDestAttrCollType(){
		return destAttrCollType;
	}

	public void setDestAttrCollType( String destAttrCollType ){
		this.destAttrCollType = destAttrCollType;
	}

	public String getDestAttrMutatorMethodTillColl(){
		return destAttrMutatorMethodTillColl;
	}

	public void setDestAttrMutatorMethodTillColl( String destAttrMutatorMethodTillColl ){
		this.destAttrMutatorMethodTillColl = destAttrMutatorMethodTillColl;
	}

	public String getDestAttrMutatorMethodAfterColl(){
		return destAttrMutatorMethodAfterColl;
	}

	public void setDestAttrMutatorMethodAfterColl( String destAttrMutatorMethodAfterColl ){
		this.destAttrMutatorMethodAfterColl = destAttrMutatorMethodAfterColl;
	}

	public String getDestAttrKeyInColl(){
		return destAttrKeyInColl;
	}

	public void setDestAttrKeyInColl( String destAttrKeyInColl ){
		this.destAttrKeyInColl = destAttrKeyInColl;
	}

	public boolean isSrcAttrMultiElemMapping(){
		return srcAttrMultiElemMapping;
	}

	public void setSrcAttrMultiElemMapping( boolean srcAttrMultiElemMapping ){
		this.srcAttrMultiElemMapping = srcAttrMultiElemMapping;
	}

	public boolean isDestAttrMultiElemMapping(){
		return destAttrMultiElemMapping;
	}

	public void setDestAttrMultiElemMapping( boolean destAttrMultiElemMapping ){
		this.destAttrMultiElemMapping = destAttrMultiElemMapping;
	}

	public boolean isSrcHasFieldsAfterIndex(){
		return srcHasFieldsAfterIndex;
	}

	public void setSrcHasFieldsAfterIndex( boolean srcHasFieldsAfterIndex ){
		this.srcHasFieldsAfterIndex = srcHasFieldsAfterIndex;
	}

	public boolean isDestHasFieldsAfterIndex(){
		return destHasFieldsAfterIndex;
	}

	public void setDestHasFieldsAfterIndex( boolean destHasFieldsAfterIndex ){
		this.destHasFieldsAfterIndex = destHasFieldsAfterIndex;
	}

	public boolean isDeepCollMultiMapping(){
		return deepCollMultiMapping;
	}

	public void setDeepCollMultiMapping( boolean deepCollMultiMapping ){
		this.deepCollMultiMapping = deepCollMultiMapping;
	}

	public String getDestAttributeInit(){
		return destAttributeInit;
	}

	public void setDestAttributeInit( String destAttributeInit ){
		this.destAttributeInit = destAttributeInit;
	}
}
