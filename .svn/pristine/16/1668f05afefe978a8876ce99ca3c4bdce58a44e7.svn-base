/**
 * 
 */
package com.mindtree.devtools.b2b.core;

import static org.junit.Assert.assertNotNull;
import static com.mindtree.devtools.b2b.utils.BeanMapperConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mindtree.devtools.b2b.jaxb.ClassAType;
import com.mindtree.devtools.b2b.jaxb.ClassBType;
import com.mindtree.devtools.b2b.jaxb.FieldType;
import com.mindtree.devtools.b2b.jaxb.MappingType;
import com.mindtree.devtools.b2b.jaxb.Mappings;
import com.mindtree.devtools.b2b.utils.BeanMapperConstants;
import com.mindtree.devtools.b2b.vo.BeanMapperContextVO;
import com.mindtree.devtools.b2b.vo.BeanMappingVO;
import com.mindtree.devtools.b2b.vo.FieldVO;
import com.mindtree.devtools.utils.DevToolsUtils;
import com.mindtree.ruc.cmn.utils.Utils;



/**
 * @author smurthy
 */
public class JaxbToBeanMapperConverter{
	private BeanMappingVO currentBeanMappingVO;
	
	/**
	 * This method returns true if a field is a deepField.
	 * @param field
	 * @return isDeepField
	 */
	private static boolean isDeepField( String field ){
		if( DevToolsUtils.isEmpty( field ) ){
			return false;
		}
		String[] fields = DevToolsUtils.tokenize( field, "." );
		if( !DevToolsUtils.isEmpty( fields ) && fields.length > 1 ){
			return true;
		}
		return false;
	}

	/**
	 * This method maps the JAXB elements to BeanMapperContextVO.
	 * @param jaxbElement
	 * @return BeanMapperContextVO
	 */
	public BeanMapperContextVO convertToBeanMapperContext( Object jaxbElement ){

		assertNotNull( jaxbElement );
		/* Cast the JAXB elements to the root element type */
		Mappings mappings = (Mappings) jaxbElement;
		assertNotNull( mappings );

		/* Get the list of mappings from the configuration */
		List<MappingType> mappingList = mappings.getMapping();
		assertNotNull( mappingList );

		BeanMapperContextVO beanMapperContextVO = new BeanMapperContextVO();
		List<BeanMappingVO> mappingVOList = new ArrayList<BeanMappingVO>();
		Properties properties = new Properties();
		
		/* For every mapping, prepare the field mapping VOs - forward and reverse. */
		for( MappingType mapping : mappingList ){
			aToB( mapping, mappingVOList );
			
			/* Generate reverse mapping only if required. */
			if( mapping.isGenReverse() ){
				bToA( mapping, mappingVOList );
			}
		}

		beanMapperContextVO.setBeanMappings( mappingVOList );
		return beanMapperContextVO;
	}

	private boolean aToB( MappingType mapping, List<BeanMappingVO> mappingVOList ){
		String clazzAInXML = mapping.getClassA().getClazz();
		String clazzBInXML = mapping.getClassB().getClazz();
		
		// TODO Auto-generated method stub
		/* Construct the individual mapping configuration */
		BeanMappingVO beanMappingVO = new BeanMappingVO();
		beanMappingVO.setId( mapping.getId() );

		// Added code to handle the package name if already configured.
		beanMappingVO.setClazzPackage( mapping.getPackage() );

		ClassAType clazzA = mapping.getClassA();
		beanMappingVO.setClazzA( resolveToClassName( clazzA.getClazz() ) );

		ClassBType clazzB = mapping.getClassB();
		beanMappingVO.setClazzB( resolveToClassName( clazzB.getClazz() ) );
		
		setMappingType( beanMappingVO );
		
		/* Now that the basic values have been set to the BeanMappingVO instance, set it to the class-level reference. 
		 * This is not a good design since it may not handle cases of concurrent threads. However, since we are not 
		 * using threads, this will work. This will have to borne in mind whenever a multi-threaded code generation is
		 * designed. */
		this.currentBeanMappingVO = beanMappingVO;

		List<FieldType> fields = mapping.getField();
		assertNotNull( fields );

		List<FieldVO> fieldList = new ArrayList<FieldVO>();
		/* Map the attributes of the bean to be copied */
		for( FieldType fieldType : fields ){
			assertNotNull( fieldType );

			FieldVO fieldVO = new FieldVO();

			/* Get the reference if for the field mapping */
			fieldVO.setRef( getRefId( fieldType ) );

			/* Get the mapper-type for the field mapping */
			fieldVO.setMapperType( fieldType.getMapperType() );
			
			/* For Request-Bean mapping (HTTP-REQUEST), set if the field is to be read as getAttribute(). */
			setHttpAttr( fieldVO, fieldType );

			/* Get the source and destination attribute */
			fieldVO.setSrcAttribute( fieldType.getA().getValue() );
			fieldVO.setDestAttribute( fieldType.getB().getValue() );

			/* Check if the source and dest attributes are deep fields */
			fieldVO.setDeepSrcAttribute( isDeepField( fieldType.getA().getValue() ) );
			fieldVO.setDeepDestAttribute( isDeepField( fieldType.getB().getValue() ) );

			/* Start: Boolean field changes */
			String srcType = fieldType.getA().getType();
			String destType = fieldType.getB().getType();

			fieldVO.setSrcBooleanType( isBooleanField( srcType ) );
			fieldVO.setDestBooleanType( isBooleanField( destType ) );
			fieldVO.setSrcType( srcType );
			fieldVO.setDestType( destType );
			/* End: Boolean field changes */

			/* Start: Collection handling changes */
			fieldVO.setSrcAttrCollType( fieldType.getA().getCollType() );
			fieldVO.setDestAttrCollType( fieldType.getB().getCollType() );
			
			setCollectionConfig( beanMappingVO, fieldVO, fieldType );
			/* End: Collection handling changes */
			
			fieldVO.setSrcType( srcType );
			fieldVO.setDestType( destType );
			/* End: Boolean field changes */

			/* Set the source accessor method */
			fieldVO.setSrcAccessorMethod( fieldType.getSrcAccessorMethod() );

			/* Set the destination mutator method */
			fieldVO.setDestMutatorMethod( fieldType.getDestMutatorMethod() );

			/*
			 * Construct the accessor method for the source attribute explicitly using reflection
			 */
			fieldVO.setSrcAttributeAccessorMethod( prepareSrcAccessorMethodChain( clazzAInXML, clazzBInXML, fieldVO ) );

			/*
			 * Construct the accessor method for the dest attribute explicitly using reflection. This is required if
			 * the destination attribute is a nested attribute inside an object. E.g. If the destination attribute
			 * is beanA.beanB.attributeA, then the "DestMutatorMethodChain" would be
			 * beanA.getBeanB().setAttributeA().
			 */
			fieldVO.setDestAttributeAccessorMethod( getDestAccessorMethodChain( fieldVO ) );

			/* Get the mutator method for the destination attribute */
			fieldVO.setDestAttributeMutatorMethod( prepareDestMutatorMethodChain( clazzAInXML, clazzBInXML, fieldVO ) );

			/* Construct the null check chain for the deep source attribute */
			fieldVO.setSrcAttributeNullCheckChain( prepareAttributeNullCheckChain( clazzAInXML, clazzBInXML, fieldVO.getSrcAttribute(), fieldVO ) );
			
			/* Start: Converter fields */
			fieldVO.setaProperties( DevToolsUtils.toNonNullString( fieldType.getA().getProperties() ) );
			fieldVO.setbProperties( DevToolsUtils.toNonNullString( fieldType.getB().getProperties() ) );
			fieldVO.setConverterClass( fieldType.getConverter() );
			
			if( DevToolsUtils.isEmpty( fieldType.getConverterDefaultDir() ) || fieldType.getConverterDefaultDir().equalsIgnoreCase( "a-b" ) ){
				fieldVO.setConverterDefaultAToB( true );
			}
			/* End: Converter fields */
			
			fieldList.add( fieldVO );
			
			/* Add this field to the set of fields that need to be initialised. This set is maintained within BeanMappingVO
			 * and addToInitialisationSet() adds to the set after trimming the last field, if necessary. */
			addToInitialisationSet( beanMappingVO, fieldVO );
			
			finishMapping( beanMappingVO, fieldVO, fieldType );
		}
		beanMappingVO.setFields( fieldList );
		mappingVOList.add( beanMappingVO );
		
		return clazzA.getClazz().equals( clazzB.getClazz() );
	}

	private void bToA( MappingType mapping, List<BeanMappingVO> mappingVOList ){
		String clazzAInXML = mapping.getClassA().getClazz();
		String clazzBInXML = mapping.getClassB().getClazz();
		
		/* Construct the individual mapping configuration */
		BeanMappingVO beanMappingVO = new BeanMappingVO();
		beanMappingVO.setId( mapping.getId() + "Reverse" ); /* Reverse mapping change */
		beanMappingVO.setReverse( true ); /* Reverse mapping change */
		
		// Added code to handle the package name if already configured.
		beanMappingVO.setClazzPackage( mapping.getPackage() );

		ClassAType clazzA = mapping.getClassA();
		beanMappingVO.setClazzB( resolveToClassName( clazzA.getClazz() ) ); /* Reverse mapping change: Notice that B is being set to A. */

		ClassBType clazzB = mapping.getClassB();
		beanMappingVO.setClazzA( resolveToClassName( clazzB.getClazz() ) ); /* Reverse mapping change: Notice that A is being set to B. */
		
		setMappingType( beanMappingVO );

		List<FieldType> fields = mapping.getField();
		assertNotNull( fields );

		List<FieldVO> fieldList = new ArrayList<FieldVO>();
		/* Map the attributes of the bean to be copied */
		for( FieldType fieldType : fields ){
			assertNotNull( fieldType );

			FieldVO fieldVO = new FieldVO();

			/* Get the reference if for the field mapping */
			fieldVO.setRef( getRefId( fieldType ) );
			/*String ref = getRefId( fieldType );
			if( !DevToolsUtils.isEmpty( ref ) ){
				fieldVO.setRef( getRefId( fieldType ) + "Reverse" );  Reverse mapping change: The reverse mapping c. 
			}*/

			/* Get the mapper-type for the field mapping */
			fieldVO.setMapperType( fieldType.getMapperType() );

			/* Get the source and destination attribute */
			fieldVO.setSrcAttribute( fieldType.getB().getValue() ); /* Reverse mapping change: Notice that Src is being set to B. */
			fieldVO.setDestAttribute( fieldType.getA().getValue() ); /* Reverse mapping change: Notice that Dest is being set to A. */

			/* Check if the source and dest attributes are deep fields */
			fieldVO.setDeepSrcAttribute( isDeepField( fieldType.getB().getValue() ) ); /* Reverse mapping change: Notice that DeepSrc is being set to B. */
			fieldVO.setDeepDestAttribute( isDeepField( fieldType.getA().getValue() ) ); /* Reverse mapping change: Notice that DeepDest is being set to A. */

			/* Start: Boolean field changes */
			String srcType = fieldType.getB().getType();
			String destType = fieldType.getA().getType();
			
			fieldVO.setSrcBooleanType( isBooleanField( srcType ) );
			fieldVO.setDestBooleanType( isBooleanField( destType ) );
			fieldVO.setSrcType( srcType );
			fieldVO.setDestType( destType );
			/* End: Boolean field changes */

			/* Start: Collection handling changes */
			fieldVO.setSrcAttrCollType( fieldType.getB().getCollType() ); /* Reverse mapping */
			fieldVO.setDestAttrCollType( fieldType.getA().getCollType() ); /* Reverse mapping */
			
			setCollectionConfig( beanMappingVO, fieldVO, fieldType );
			/* End: Collection handling changes */
			
			/* Set the source accessor method */
			fieldVO.setSrcAccessorMethod( fieldType.getDestAccessorMethod() ); /* Reverse mapping change: Notice that Src is being set to DestAccesor. */

			/* Set the destination mutator method */
			fieldVO.setDestMutatorMethod( fieldType.getSrcMutatorMethod() ); /* Reverse mapping change: Notice that Dest is being set to SrcMutator. */

			/*
			 * Construct the accessor method for the source attribute explicitly using reflection
			 */
			fieldVO.setSrcAttributeAccessorMethod( prepareSrcAccessorMethodChain( clazzBInXML, clazzAInXML, fieldVO ) );

			/*
			 * Construct the accessor method for the dest attribute explicitly using reflection. This is required if
			 * the destination attribute is a nested attribute inside an object. E.g. If the destination attribute
			 * is beanA.beanB.attributeA, then the "DestMutatorMethodChain" would be
			 * beanA.getBeanB().setAttributeA().
			 */
			fieldVO.setDestAttributeAccessorMethod( getDestAccessorMethodChain( fieldVO ) );

			/* Get the mutator method for the destination attribute */
			fieldVO.setDestAttributeMutatorMethod( getDestMutatorMethodChain( fieldVO ) );

			/* Construct the null check chain for the deep source attribute */
			fieldVO.setSrcAttributeNullCheckChain( prepareAttributeNullCheckChain( clazzBInXML, clazzAInXML, fieldVO.getSrcAttribute(), fieldVO ) );

			/* Start: Converter fields */
			fieldVO.setaProperties( DevToolsUtils.toNonNullString( fieldType.getA().getProperties() ) );
			fieldVO.setbProperties( DevToolsUtils.toNonNullString( fieldType.getB().getProperties() ) );
			fieldVO.setConverterClass( fieldType.getConverter() );
			
			if( DevToolsUtils.isEmpty( fieldType.getConverterDefaultDir() ) || fieldType.getConverterDefaultDir().equalsIgnoreCase( "a-b" ) ){
				fieldVO.setConverterDefaultAToB( true );
			}
			/* End: Converter fields */
			
			fieldList.add( fieldVO );
			
			/* Add this field to the set of fields that need to be initialised. This set is maintained within BeanMappingVO
			 * and addToInitialisationSet() adds to the set after trimming the last field, if necessary. */
			addToInitialisationSet( beanMappingVO, fieldVO );
			
			finishMapping( beanMappingVO, fieldVO, fieldType );
		}
		beanMappingVO.setFields( fieldList );
		mappingVOList.add( beanMappingVO );
	}

	/**
	 * Sets the bean mapping type inside BeanMappingVO.
	 * 
	 * @param beanMappingVO
	 */
	private void setMappingType( BeanMappingVO beanMappingVO ){
		if( beanMappingVO.getClazzA().equals( CLASS_HTTP_REQUEST ) ){
			beanMappingVO.setMappingType( MAPPING_TYPE_REQ_TO_BEAN );
		}
	}

	/**
	 * This method sets up some temporary or permanent settings for the mapping of the specified field.
	 * 
	 * @param beanMappingVO
	 * @param fieldVO
	 * @param fieldType
	 */
	private void beginMapping( BeanMappingVO beanMappingVO, FieldVO fieldVO, FieldType fieldType ){
		
		//SONARFIX -- adding a comment to avoid blank method
	}
	
	/**
	 * This method cleans up some temporary settings we made during the mapping.
	 * 
	 * @param beanMappingVO
	 * @param fieldVO
	 * @param fieldType
	 */
	private void finishMapping( BeanMappingVO beanMappingVO, FieldVO fieldVO, FieldType fieldType ){
		if( beanMappingVO.getMappingType().equals( BeanMapperConstants.MAPPING_TYPE_REQ_TO_BEAN ) ){
			fieldVO.setSrcAttribute( DevToolsUtils.trimCollBraces( fieldVO.getSrcAttribute() ) );
		}
		
	}

	/**
	 * Sets the source and destination accessor method till the collection index ("[]" configuration) and after it. This method works
	 * the same for both forward and reverse mapping because it uses FieldVO.getSrcXXX() and FieldVO.getDestXXX() rather than getting
	 * values directly from FieldType instance.
	 * 
	 * @param fieldVO
	 * @param fieldType
	 */
	private void setCollectionConfig( BeanMappingVO beanMappingVO, FieldVO fieldVO, FieldType fieldType ){
		/* Handle HTTP-REQUEST mappings specifically here. */
		if( ( beanMappingVO.getClazzA().equals( CLASS_HTTP_REQUEST ) || 
			  beanMappingVO.getClazzB().equals( CLASS_HTTP_REQUEST ) ) &&
			( BeanMapperCodeGenUtils.fieldIsCollType( fieldVO.getSrcAttrCollType() ) || 
			  BeanMapperCodeGenUtils.fieldIsCollType( fieldVO.getDestAttrCollType() ) )
		)
		{
			fieldVO.setDeepCollMultiMapping( true );
		}
		
		/* SRC fields */
		String attr = fieldVO.getSrcAttribute();
		int openBraceIndex = attr.indexOf( ARRAY_OPEN_BRACE );
		int closeBraceIndex = attr.indexOf( ARRAY_CLOSE_BRACE );
		boolean emptyBraces = closeBraceIndex - openBraceIndex == 1;
		boolean fieldIsCollType = BeanMapperCodeGenUtils.fieldIsCollType( fieldVO.getSrcAttrCollType() );
		
		if( openBraceIndex > 0 || fieldIsCollType ){
			/* If braces have not been added, then the last part of the field is the collection or array, and it is a multi-element
			 * mapping case. Hence, let us add the braces and set the attribute back. */
			if( openBraceIndex == -1 && closeBraceIndex == -1 ) fieldVO.setSrcAttribute( ( attr = attr + "[]" ) );
			
			boolean isDeepCollMapping = attr.length() > closeBraceIndex + 1 && closeBraceIndex > -1;
			fieldVO.setSrcHasFieldsAfterIndex( isDeepCollMapping );
			
			/* Check if it is single mapping or multiple mapping. */
			if( emptyBraces || closeBraceIndex == -1 ){
				fieldVO.setSrcAttrMultiElemMapping( true );
			}
			
			if( !fieldVO.isDeepCollMultiMapping() ){
				if( isDeepCollMapping && ( emptyBraces || closeBraceIndex == -1 ) ) fieldVO.setDeepCollMultiMapping( true );
			}
			
			/* Now prepare the get-method chain for all attributes before the braces. */
			FieldVO copy = fieldVO.getCopy(); 
			copy.setSrcAttribute( openBraceIndex > -1 ? attr.substring( 0, openBraceIndex ) : attr );
			String srcAttrAccessorMethodTillColl = prepareSrcAccessorMethodChain( this.currentBeanMappingVO.getClazzA(), this.currentBeanMappingVO.getClazzB(), copy );
			fieldVO.setSrcAttrAccessorMethodTillColl( srcAttrAccessorMethodTillColl );
			
			/* Now prepare the get-method chain for all attributes after the braces. */
			String srcAttrAfterColl = closeBraceIndex > -1 ? attr.substring( closeBraceIndex + 1 ) : "";
			if( !DevToolsUtils.isEmpty( srcAttrAfterColl ) ){
				copy.setSrcAttribute( srcAttrAfterColl );
				fieldVO.setSrcAttrAccessorMethodAfterColl( prepareSrcAccessorMethodChain( this.currentBeanMappingVO.getClazzA(), this.currentBeanMappingVO.getClazzB(), copy ) );
			}
			
			if( !emptyBraces && closeBraceIndex > -1 && openBraceIndex > -1 ) fieldVO.setSrcAttrKeyInColl( attr.substring( openBraceIndex + 1, closeBraceIndex ) );
		}
		
		/* DEST fields */
		attr = fieldVO.getDestAttribute();
		openBraceIndex = attr.indexOf( ARRAY_OPEN_BRACE );
		closeBraceIndex = attr.indexOf( ARRAY_CLOSE_BRACE );
		emptyBraces = closeBraceIndex - openBraceIndex == 1;
		fieldIsCollType = BeanMapperCodeGenUtils.fieldIsCollType( fieldVO.getDestAttrCollType() );
		if( openBraceIndex > 0 || fieldIsCollType ){
			/* If braces have not been added, then the last part of the field is the collection or array, and it is a multi-element
			 * mapping case. Hence, let us add the braces and set the attribute back. */
			if( openBraceIndex == -1 && closeBraceIndex == -1 ) fieldVO.setDestAttribute( ( attr = attr + "[]" ) );
			
			boolean isDeepCollMapping = ( attr.length() > closeBraceIndex + 1 && closeBraceIndex > -1 ) ;
			fieldVO.setDestHasFieldsAfterIndex( isDeepCollMapping );
			
			/* Check if it is single mapping or multiple mapping. */
			if( emptyBraces || closeBraceIndex == -1 ){
				fieldVO.setDestAttrMultiElemMapping( true );
			}
			
			if( !fieldVO.isDeepCollMultiMapping() ){
				if( isDeepCollMapping && ( emptyBraces || closeBraceIndex == -1 ) ) fieldVO.setDeepCollMultiMapping( true );
			}
			
			/* Now prepare the get-method chain for all attributes before the braces. */
			FieldVO copy = fieldVO.getCopy(); 
			copy.setDestAttribute( openBraceIndex > -1 ? attr.substring( 0, openBraceIndex ) : attr );
			String destAttrAccessorMethodTillColl = getDestAccessorMethodChain( copy );
			fieldVO.setDestAttrMutatorMethodTillColl( destAttrAccessorMethodTillColl );
			
			/* Now prepare the get-method chain for all attributes after the braces. */
			String destAttrAfterColl = closeBraceIndex > -1 ? attr.substring( closeBraceIndex + 1 ) : "";
			if( !DevToolsUtils.isEmpty( destAttrAfterColl ) ){
				copy.setDestAttribute( destAttrAfterColl );
				fieldVO.setDestAttrMutatorMethodAfterColl( prepareDestMutatorMethodChain( this.currentBeanMappingVO.getClazzA(), this.currentBeanMappingVO.getClazzB(), copy ) );
			}
			
			if( !emptyBraces && closeBraceIndex > -1 && openBraceIndex > -1 ) fieldVO.setDestAttrKeyInColl( attr.substring( openBraceIndex + 1, closeBraceIndex ) );
		}
	}
	

	/**
	 * This method checks if the attribute should be initialised when the generated mapper is called and adds it to 
	 * the initialisation set, if yes, and creates the initialisation Java statement.
	 * 
	 * @param beanMappingVO
	 * @param fieldVO
	 */
	private void addToInitialisationSet( BeanMappingVO beanMappingVO, FieldVO fieldVO ){
		String attribute = fieldVO.getDestAttribute();
		if( fieldVO.isDeepDestAttribute() ||
			( !fieldVO.isDeepDestAttribute() && fieldVO.isDestAttrMultiElemMapping() ) )
		{
			if( !BeanMapperCodeGenUtils.lastPartOfAttrIsColl( fieldVO, false ) ){
				attribute = attribute.substring( 0, attribute.lastIndexOf( '.' ) );
			}
			
			/* Create the initialisation statement only if the attribute has not already been added. */
			if( DevToolsUtils.isEmpty( beanMappingVO.getInitFields() ) || 
				( !DevToolsUtils.isEmpty( beanMappingVO.getInitFields() ) && !beanMappingVO.getInitFields().contains( attribute ) ) ){
				StringBuffer sb = new StringBuffer( "BeanUtils.initializeBeanField( " );
				sb.append( "\"" ).append( attribute ).append( "\"" ).append( ", beanB" );
				
				if( beanMappingVO.getClazzA().equals( CLASS_HTTP_REQUEST ) && fieldVO.isSrcAttrMultiElemMapping() ){
					sb.append( ", " ).append( "HTTPUtils.getMatchingMultiReqParamKeys( beanA, \"" ).append( fieldVO.getSrcAttribute() ).append( "\" ).size()" );
				}
				else if( FIELD_COLL_TYPE_ARRAY.equals( fieldVO.getSrcAttrCollType() ) && fieldVO.isDeepCollMultiMapping() ){
					sb.append( ", " ).append( "beanA." ).append( fieldVO.getSrcAttrAccessorMethodTillColl() ).append( ".length" );
				}
				else if( FIELD_COLL_TYPE_LIST.equals( fieldVO.getSrcAttrCollType() ) && fieldVO.isDeepCollMultiMapping() ){
					sb.append( ", " ).append( "beanA." ).append( fieldVO.getSrcAttrAccessorMethodTillColl() ).append( ".size()" );
				}
				
				sb.append( " );" );
				
				fieldVO.setDestAttributeInit( sb.toString() );
				beanMappingVO.addInitField( attribute );
			}
		}
	}

	/**
	 * Figures out if the field is to be read as request.getAttribute(). This is, of course, used only
	 * in the case of HTTP-REQUEST (Request-Bean mapping).
	 * @param fieldVO
	 * @param fieldType
	 */
	private void setHttpAttr( FieldVO fieldVO, FieldType fieldType ){
		/* Check if http-attr has been set in any of "a" and "b". */
		boolean isHttpAttr = fieldType.getA().isHttpAttr() | fieldType.getB().isHttpAttr();
		fieldVO.setHttpAttr( isHttpAttr );
	}

	/**
	 * 
	 * @param clazzAInXML
	 * @param clazzBInXML
	 * @param fieldVO
	 * @return
	 */
	private String prepareDestMutatorMethodChain( String clazzAInXML, String clazzBInXML, FieldVO fieldVO ){
		String destMutatorChain = null;
		
		if( BeanMapperConstants.CLASS_CONFIG_HTTP_REQUEST.equalsIgnoreCase( clazzBInXML ) ){
			destMutatorChain = getDestMutatorMethodChainForHttpRequest( fieldVO );
		}
		else{
			destMutatorChain = getDestMutatorMethodChain( fieldVO );
		}
		return destMutatorChain;
	}

	private String getDestMutatorMethodChainForHttpRequest( FieldVO fieldVO ){
		/* TODO 7/Jan/2012: Usually, HttpServletRequest will not be a destination. Moreover, This way of 
		 * setting attribute may not work for HttpServletRequest. Has to be addressed. */
		return "setAttribute( \"" + fieldVO.getDestAttribute() + "\" ), "; 
	}

	/**
	 * This method will resolve configured name to a class name. In the case of regular bean-to-bean mappings, this may be
	 * a complete class. However, the special cases that Bean Mapper handles are resolved to their classes here.
	 *  
	 * @param clazz
	 * @return
	 */
	private String resolveToClassName( String clazz ){
		if( BeanMapperConstants.CLASS_CONFIG_HTTP_REQUEST.equalsIgnoreCase( clazz ) ){
			return "javax.servlet.http.HttpServletRequest";
		}
		
		return clazz;
	}

	/**
	 * This method will handle the various cases of the source class.
	 * (a) If the source class is a normal bean, the getXXX() method chain will constructed.
	 * (b) If the source class is <code>HttpServletRequest</code>, indicated by the string HTTP-REQUEST, then getAttribute()
	 * will be used. 
	 * 
	 * @param clazzAInXML
	 * @param clazzBInXML
	 * @param fieldVO
	 * @return
	 */
	private String prepareSrcAccessorMethodChain( String clazzAInXML, String clazzBInXML, FieldVO fieldVO ){
		String fieldAccessorChain = null;
		if( BeanMapperConstants.CLASS_CONFIG_HTTP_REQUEST.equalsIgnoreCase( clazzAInXML ) ){
			fieldAccessorChain = getSrcAccessorMethodChainForHttpRequest( fieldVO );
		}
		else{
			fieldAccessorChain = getSrcAccessorMethodChain( fieldVO );
		}
		
		return fieldAccessorChain;
	}

	/**
	 * This method prepares the null check chain based on source class type.
	 * 
	 * @param clazzAInXML
	 * @param clazzBInXML
	 * @param srcAttribute
	 * @param fieldVO 
	 * @return
	 */
	private String prepareAttributeNullCheckChain( String clazzAInXML, String clazzBInXML, String srcAttribute, FieldVO fieldVO ){
		String nullCheckChain = null;
		if( BeanMapperConstants.CLASS_CONFIG_HTTP_REQUEST.equalsIgnoreCase( clazzAInXML ) ){
			nullCheckChain = getAttributeNullCheckChainForHttpRequest( srcAttribute, fieldVO );
		}
		else{
			nullCheckChain = getAttributeNullCheckChain( srcAttribute, fieldVO );
		}
		
		return nullCheckChain;
	}
	
	/**
	 * For HttpServletRequest, we don't need any null check.
	 * 
	 * @param srcAttribute
	 * @param fieldVO 
	 * @return
	 */
	private String getAttributeNullCheckChainForHttpRequest( String srcAttribute, FieldVO fieldVO ){
		String paramOrAttr = resolveToHttpParameterOrAttribute( fieldVO );
		/* "src" is hard-coded because null check will happen only on the source attribute. */
		StringBuilder nullCheckChain = new StringBuilder( "if( !Utils.isEmpty( src.get" + paramOrAttr + "( \"" );
		nullCheckChain.append( srcAttribute ).append( "\" ) ) )" );
		return nullCheckChain.toString();
	}

	private String resolveToHttpParameterOrAttribute( FieldVO fieldVO ){
		return fieldVO.isHttpAttr() ? "Attribute" : "Parameter";
	}

	/**
	 * This method constructs the null check chain for the source attribute.
	 * @param attribute
	 * @param fieldVO 
	 * @return String
	 */
	private String getAttributeNullCheckChain( String attribute, FieldVO fieldVO ){
		return BeanMapperCodeGenUtils.getAttributeNullCheckChain( attribute, fieldVO );
	}

	private boolean isBooleanField( String type ){
		if( DevToolsUtils.isEmpty( type ) ) return false;
		else if( BeanMapperConstants.JAVA_PRIM_TYPE_BOOLEAN.equals( type ) ) return true;
		
		return false;
	}

	/**
	 * This method constructs the access call from an HttpServletRequest instance.
	 * 
	 * @param fieldVO
	 * @return
	 */
	private String getSrcAccessorMethodChainForHttpRequest( FieldVO fieldVO ){
		String httpParamOrAttr = resolveToHttpParameterOrAttribute( fieldVO );
		return "get" + httpParamOrAttr + "( \"" + fieldVO.getSrcAttribute() + "\" )";
	}

	/**
	 * This method constructs the accessor method chain
	 * @param fieldVO
	 * @return method
	 */
	private String getSrcAccessorMethodChain( FieldVO fieldVO ){
		return BeanMapperCodeGenUtils.getSrcAccessorMethodChain( fieldVO );
	}

	/**
	 * This method constructs the mutator method chain.
	 * @param fieldVO
	 * @return mutatorMethod
	 */
	private String getSrcMutatorMethodChain( FieldVO fieldVO ){
		return BeanMapperCodeGenUtils.getSrcMutatorMethodChain( fieldVO );
	}

	/**
	 * This method constructs the accessor method chain
	 * @param fieldVO
	 * @return method
	 */
	private String getDestAccessorMethodChain( FieldVO fieldVO ){
		return BeanMapperCodeGenUtils.getDestAccessorMethodChain( fieldVO );
	}

	/**
	 * This method constructs the mutator method chain.
	 * @param fieldVO
	 * @return mutatorMethod
	 */
	private String getDestMutatorMethodChain( FieldVO fieldVO ){
		return BeanMapperCodeGenUtils.getDestMutatorMethodChain( fieldVO );
	}
	
	/**
	 * This method returns the id of the mapping to which the field has a reference.
	 * @param fieldType
	 * @return idRef
	 */
	private String getRefId( FieldType fieldType ){
		String ref = null;
		Object idRef = fieldType.getRef();
		if( !DevToolsUtils.isEmpty( idRef ) ){
			MappingType mappingType = (MappingType) idRef;
			ref = mappingType.getId();
		}
		return ref;
	}

}
