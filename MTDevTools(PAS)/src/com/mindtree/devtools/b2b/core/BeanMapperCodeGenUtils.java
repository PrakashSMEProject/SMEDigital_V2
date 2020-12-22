/**
 * 
 */
package com.mindtree.devtools.b2b.core;

import static com.mindtree.devtools.b2b.utils.BeanMapperConstants.*;

import com.mindtree.devtools.b2b.utils.BeanMapperConstants;
import com.mindtree.devtools.b2b.vo.FieldVO;
import com.mindtree.devtools.utils.DevToolsUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author smurthy
 */
public class BeanMapperCodeGenUtils{

	/**
	 * This method constructs the accessor method chain from the field.
	 * 
	 * @param fieldName
	 * @return accessorMethodChain
	 */
	public static String getDestAccessorMethodChain( FieldVO fieldVO ){
		String accessorMethodChain = null;
		StringBuffer methodChain = new StringBuffer();

		// If the mutator method is explicitly configured, return the same by
		// appending the suitable parenthesis
		if( fieldVO.isDestAttributeMutatorMethodConfigured() ){
			return constructDestMutatorMethod( fieldVO );
		}

		String[] fields = DevToolsUtils.tokenize( fieldVO.getDestAttribute(), BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
		if( !DevToolsUtils.isEmpty( fields ) && fields.length > 0 ){
			for( String field : fields ){
				String fieldWithoutIndex = removeCollIndex( field ); /* Remove the index part configured as "[]" */
				methodChain.append( BeanMapperConstants.BEAN_MAPPER_GETTER_METHOD_PREFIX );
				methodChain.append( DevToolsUtils.firstCharToUpperCase( fieldWithoutIndex ) );
				methodChain.append( BeanMapperConstants.BEAN_MAPPER_PARANTHESIS );
				
				appendCollGetter( false, methodChain, fieldVO, field );
				
				methodChain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
			}
			int index = methodChain.length() - 1;
			accessorMethodChain = DevToolsUtils.deleteCharAtIndex( methodChain.toString(), index );
		}
		return accessorMethodChain;
	}

	/**
	 * This method constructs the mutator method chain.
	 * 
	 * @param fieldVO
	 * @return mutatorMethod
	 */
	public static String getDestMutatorMethodChain( FieldVO fieldVO ){
		StringBuffer methodChain = new StringBuffer();

		// If the mutator method is explicitly configured, return the same by
		// appending the suitable parenthesis
		if( fieldVO.isDestAttributeMutatorMethodConfigured() ){
			return constructDestMutatorMethod( fieldVO );
		}

		String[] fields = DevToolsUtils.tokenize( fieldVO.getDestAttribute(), BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
		if( !DevToolsUtils.isEmpty( fields ) ){
			int length = fields.length;
			if( length == 1 ){
				String field = removeCollIndex( fields[ 0 ] ); /* Remove the index part configured as "[]" */
				methodChain.append( BeanMapperConstants.BEAN_MAPPER_SETTER_METHOD_PREFIX );
				methodChain.append( DevToolsUtils.firstCharToUpperCase( field ) );
				return methodChain.toString();
			}
			else if( length > 1 ){
				for( int index = 0; index < length - 1; index++ ){
					String field = removeCollIndex( fields[ index ] ); /* Remove the index part configured as "[]" */
					methodChain.append( BeanMapperConstants.BEAN_MAPPER_GETTER_METHOD_PREFIX );
					methodChain.append( DevToolsUtils.firstCharToUpperCase( field ) );
					methodChain.append( BeanMapperConstants.BEAN_MAPPER_PARANTHESIS );
					
					appendCollGetter( false, methodChain, fieldVO, fields[ index ] ); /* Append the getter from the collection as configured 
																	  			* within "[]" */
					
					methodChain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
				}
				methodChain.append( BeanMapperConstants.BEAN_MAPPER_SETTER_METHOD_PREFIX );
				methodChain.append( DevToolsUtils.firstCharToUpperCase( fields[ length - 1 ] ) );
			}
		}
		return methodChain.toString();
	}

	/**
	 * This method constructs the mutator method chain.
	 * 
	 * @param fieldVO
	 * @return mutatorMethod
	 */
	public static String getSrcMutatorMethodChain( FieldVO fieldVO ){
		StringBuffer methodChain = new StringBuffer();

		// If the mutator method is explicitly configured, return the same by
		// appending the suitable parenthesis
		if( fieldVO.isDestAttributeMutatorMethodConfigured() ){
			return constructDestMutatorMethod( fieldVO ); /* TODO This method call [constructDestMutatorMethod()] is not correct in SrcMutator case. */
		}

		String[] fields = DevToolsUtils.tokenize( fieldVO.getSrcAttribute(), BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
		if( !DevToolsUtils.isEmpty( fields ) ){
			int length = fields.length;
			if( length == 1 ){
				String field = removeCollIndex( fields[ 0 ] ); /* Remove the index part configured as "[]" */
				methodChain.append( BeanMapperConstants.BEAN_MAPPER_SETTER_METHOD_PREFIX );
				methodChain.append( DevToolsUtils.firstCharToUpperCase( field ) );
				return methodChain.toString();
			}
			else if( length > 1 ){
				for( int index = 0; index < length - 1; index++ ){
					String field = removeCollIndex( fields[ index ] ); /* Remove the index part configured as "[]" */
					methodChain.append( BeanMapperConstants.BEAN_MAPPER_GETTER_METHOD_PREFIX );
					methodChain.append( DevToolsUtils.firstCharToUpperCase( field ) );
					methodChain.append( BeanMapperConstants.BEAN_MAPPER_PARANTHESIS );
					
					appendCollGetter( true, methodChain, fieldVO, fields[ index ] ); /* Append the getter from the collection as configured 
					  												  			* within "[]" */

					methodChain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
				}
				methodChain.append( BeanMapperConstants.BEAN_MAPPER_SETTER_METHOD_PREFIX );
				methodChain.append( DevToolsUtils.firstCharToUpperCase( fields[ length - 1 ] ) );
			}
		}
		return methodChain.toString();
	}

	/**
	* This method constructs the chain of methods for each field
	* 
	* @param beanType
	*            beanA or beanB
	 * @param fieldVO 
	* @param attribute
	* @return fields
	*/
	private static String[] getMethodChain( String beanType, String[] fields, FieldVO fieldVO ){
		java.util.List<String> methodChainArray = new java.util.ArrayList<String>();
		boolean sourceBean = beanType.equals( BEAN_MAPPER_SOURCE_BEAN );
		
		if( !DevToolsUtils.isEmpty( fields ) ){
			int noOfFields = fields.length;
			boolean baseBeanAppended = false;
			
			//methodChainArray = new String[ noOfFields - 1 ];
			for( int i = 0; i < noOfFields; i++ ){
				/* Consider the field as "collection" type if the current part of the attribute contains "[". */
				boolean fieldIsCollType = fields[ i ].indexOf( "[" ) >= 0;
				String index = null;
				String field = null;
				String fieldWithIndex = null;
				
				/*StringBuffer methodTrain = new StringBuffer();
				methodTrain.append( beanType );
				methodTrain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
				for( int j = 0; j < i + 1; j++ ){
					fieldIsCollType = fields[ j ].indexOf( "[" ) >= 0;
					fieldWithIndex = fields[ j ];
					
					if( fieldIsCollType ) index = fields[ j ].substring( fields[ j ].indexOf( ARRAY_OPEN_BRACE ) + 1, fields[ j ].indexOf( ARRAY_CLOSE_BRACE ) );
					field = removeCollIndex( fields[ j ] );
					
					methodTrain.append( DevToolsUtils.getMethodName( field ) );
					methodTrain.append( BeanMapperConstants.BEAN_MAPPER_PARANTHESIS );
					methodTrain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
					
				}*/
				
				StringBuffer methodTrain = initStringBufferWithLastListEntry( methodChainArray );
				if( !baseBeanAppended ){
					methodTrain.append( beanType );
					baseBeanAppended = true;
				}
				
				/* Now, we are continuing to append to the previous chain. Hence, first add a '.'. */
				methodTrain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
				
				//for( int j = 0; j < i + 1; j++ ){
					//fieldIsCollType = fields[ i ].indexOf( "[" ) >= 0;
					fieldWithIndex = fields[ i ];
					
					if( fieldIsCollType ) index = fields[ i ].substring( fields[ i ].indexOf( ARRAY_OPEN_BRACE ) + 1, fields[ i ].indexOf( ARRAY_CLOSE_BRACE ) );
					field = removeCollIndex( fields[ i ] );
					
					methodTrain.append( DevToolsUtils.getMethodName( field ) );
					methodTrain.append( BeanMapperConstants.BEAN_MAPPER_PARANTHESIS );
					methodTrain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
					
				//}
				int indexOfLastChar = methodTrain.length() - 1;
				methodChainArray.add( DevToolsUtils.deleteCharAtIndex( methodTrain.toString(), indexOfLastChar ) );

				/* Add the get() from the collection into the method chain. */
				if( fieldIsCollType ){
					methodTrain.deleteCharAt( methodTrain.length() - 1 );
					appendCollGetter( sourceBean, methodTrain, fieldVO, fieldWithIndex );
					methodChainArray.add( methodTrain.toString() );
				}
			}
		}
		
		return methodChainArray.toArray( new String[ 0 ] );
	}
	
	/**
	 * Creates a new StringBuffer instance with the last entry in the passed list or with nothing if the
	 * list is null or empty.
	 * 
	 * @param list
	 * @return
	 */
	private static StringBuffer initStringBufferWithLastListEntry( java.util.List<String> list ){
		String entry = getLastEntry( list );
		StringBuffer buff = entry == null ? new StringBuffer() : new StringBuffer( entry );
		
		return buff;
	}
	
	private static <T> T getLastEntry( java.util.List<T> list ){
		T entry = null;
		if( !DevToolsUtils.isEmpty( list ) ){
			entry = list.get( list.size() - 1 );
		}
		
		return entry;
	}

	/**
	 * This method constructs the accessor method chain from the field.
	 * 
	 * @param fieldName
	 * @return accessorMethodChain
	 */
	public static String getSrcAccessorMethodChain( FieldVO fieldVO ){
		String accessorMethodChain = null;

		// If the accessor method is explicitly configured, return the same by
		// appending the suitable parentheis
		if( fieldVO.isSrcAttributeAccessorMethodConfigured() ){
			return constructSrcAccessorMethod( fieldVO );
		}

		StringBuffer methodChain = new StringBuffer();
		String[] fields = DevToolsUtils.tokenize( fieldVO.getSrcAttribute(), BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
		if( !DevToolsUtils.isEmpty( fields ) && fields.length > 0 ){
			int noOfFields = fields.length;
			int loopIndex = 0;

			/* Start preparing the getter method chain. */
			for( ; loopIndex < noOfFields - 1; loopIndex++ ){ /* We are not going to process the last token because it may be 
																 * a boolean. In that case, we need to use "is" rather than "get". */
				String field = fields[ loopIndex ];
				appendToMethodChain( true, methodChain, fieldVO, field, false );
			}

			/* Now process that last token. If it is a boolean, use "is", else "get". */
			appendToMethodChain( true, methodChain, fieldVO, fields[ loopIndex++ ], fieldVO.isSrcBooleanType() );

			/* appendToMethodChain() adds a '.' at the end for each call. Remove it. */
			int index = methodChain.length() - 1;
			accessorMethodChain = DevToolsUtils.deleteCharAtIndex( methodChain.toString(), index );
		}
		return accessorMethodChain;
	}
	
	/**
	 * Checks if the last property in the passed field attribute (expected to be one configured in the mapping XML) is a 
	 * collection type.
	 * 
	 * @param fieldAttribute
	 * @return
	 */
	public static boolean lastPartOfAttrIsColl( FieldVO fieldVO, boolean src ){
		String attribute = ( src ? fieldVO.getSrcAttribute() : fieldVO.getDestAttribute() );
		String collType = ( src ? fieldVO.getSrcAttrCollType() : fieldVO.getDestAttrCollType() );
		boolean lastPartOfAttrIsColl = false;
		
		/* Assumption: All attributes are set trimmed. No trailing spaces. */
		if( fieldVO.isDestAttrMultiElemMapping() && attribute.endsWith( "[]" ) ){
			lastPartOfAttrIsColl = true;
		}
		
		return lastPartOfAttrIsColl;
	}

	/**
	 * Returns true if the field has been configured to be a collection type, ie, the <code>collType</code> attribute has been
	 * configured to be one of <code>ARRAY</code>, <code>LIST</code>, <code>SET</code> or <code>MAP</code>.
	 * @param collType
	 * @return
	 */
	public static boolean fieldIsCollType( String collType ){
		if( FIELD_COLL_TYPE_ARRAY.equalsIgnoreCase( collType ) || FIELD_COLL_TYPE_LIST.equalsIgnoreCase( collType ) || FIELD_COLL_TYPE_MAP.equalsIgnoreCase( collType )
				|| FIELD_COLL_TYPE_SET.equalsIgnoreCase( collType ) ){
			return true;
		}

		return false;
	}

	private static int getFieldCollTypeAsInt( String collType ){
		int collTypeInt = -1;
		if( FIELD_COLL_TYPE_ARRAY.equalsIgnoreCase( collType ) ) collTypeInt = FIELD_COLL_TYPE_INT_ARRAY;
		if( FIELD_COLL_TYPE_LIST.equalsIgnoreCase( collType ) ) collTypeInt = FIELD_COLL_TYPE_INT_LIST;
		if( FIELD_COLL_TYPE_SET.equalsIgnoreCase( collType ) ) collTypeInt = FIELD_COLL_TYPE_INT_SET;
		if( FIELD_COLL_TYPE_MAP.equalsIgnoreCase( collType ) ) collTypeInt = FIELD_COLL_TYPE_INT_MAP;

		return collTypeInt;
	}

	private static void appendToMethodChain( boolean isSrcAttr, StringBuffer methodChain, FieldVO fieldVO, String field, boolean isFieldBoolean ){
		if( isFieldBoolean )
			methodChain.append( BeanMapperConstants.BEAN_MAPPER_BOOL_GETTER_METHOD_PREFIX );
		else
			methodChain.append( BeanMapperConstants.BEAN_MAPPER_GETTER_METHOD_PREFIX );

		String fieldWithoutIndex = removeCollIndex( field );

		methodChain.append( DevToolsUtils.firstCharToUpperCase( fieldWithoutIndex ) );
		methodChain.append( BeanMapperConstants.BEAN_MAPPER_PARANTHESIS );

		appendCollGetter( isSrcAttr, methodChain, fieldVO, field );

		methodChain.append( BeanMapperConstants.BEAN_MAPPER_DOT_DELIMITER );
	}

	/**
	 * If the field has collection index specified, this method removes it.
	 * 
	 * @param field
	 * @return
	 */
	private static String removeCollIndex( String field ){
		if( field.contains( ARRAY_OPEN_BRACE ) ){
			field = field.substring( 0, field.indexOf( ARRAY_OPEN_BRACE ) );
		}

		return field;
	}

	/**
	 * Add the accessor from the collection or array.
	 * 
	 * @param methodChain
	 * @param fieldVO
	 * @param field
	 */
	private static void appendCollGetter( boolean isSrcAttr, StringBuffer methodChain, FieldVO fieldVO, String field ){
		String attrCollType = ( isSrcAttr ? fieldVO.getSrcAttrCollType() : fieldVO.getDestAttrCollType() );
		boolean singleElemMapping = ( isSrcAttr ? !fieldVO.isSrcAttrMultiElemMapping() : !fieldVO.isDestAttrMultiElemMapping() );
		String attrKeyInColl = ( isSrcAttr ? fieldVO.getSrcAttrKeyInColl() : fieldVO.getDestAttrKeyInColl() );
		String beanReference = ( isSrcAttr ? BEAN_MAPPER_SOURCE_BEAN : BEAN_MAPPER_DESTINATION_BEAN );
		
		/* If it is a single mapping collection type, then add the method to get the element from within the collection
		 * here. */
		if( fieldIsCollType( attrCollType ) && singleElemMapping && field.indexOf( "[" ) >= 0 ){  //SONARQUBE FIX - 20-apr-2018 -- & TO &&
			int collTypeInt = getFieldCollTypeAsInt( attrCollType );

			switch( collTypeInt ){
				case FIELD_COLL_TYPE_INT_ARRAY:
					/* To the "get" method, add "[<key>]". Eg, getArray()[1]. */
					methodChain.append( ARRAY_OPEN_BRACE ).append( attrKeyInColl ).append( ARRAY_CLOSE_BRACE );
					break;
				case FIELD_COLL_TYPE_INT_LIST:
					/* To the "get" method, add ".get(<key>)". Eg, getList().get(1). */
					methodChain.append( LIST_ACCESSOR_BEGIN ).append( attrKeyInColl ).append( METHOD_CLOSE_BRACE );
					break;
				case FIELD_COLL_TYPE_INT_MAP:
					/* INCORRECT IMPLEMENTATION: 30/Jan/2012: Need to get an instance of the correct key class. */
					/* To the "get" method, add ".get(<key>)". Eg, getMap().get( "1" ). */
					String methodChainTillMap = methodChain.toString();
					boolean beanRefAlreadyPrepended = methodChainTillMap.startsWith( BEAN_MAPPER_SOURCE_BEAN + BEAN_MAPPER_DOT_DELIMITER ) ||
													  methodChainTillMap.startsWith( BEAN_MAPPER_DESTINATION_BEAN + BEAN_MAPPER_DOT_DELIMITER );
					methodChain.append( MAP_ACCESSOR_BEGIN )
						.append( 
							" Utils.newInstance( ( (com.mindtree.ruc.cmn.utils.Map) " + 
							( beanRefAlreadyPrepended ? "" : beanReference + BEAN_MAPPER_DOT_DELIMITER ) 
							+ methodChainTillMap + ").getKeyClass(), \"" + removeQuotes( attrKeyInColl ) + "\" )" 
						)
						.append( METHOD_CLOSE_BRACE );
					break;
				case FIELD_COLL_TYPE_INT_SET:
					break;
			}
		}
	}

	/**
	 * Removes first and last quotes from the string specified.
	 * @param str
	 * @return
	 */
	private static String removeQuotes( String str ){
		if( DevToolsUtils.isEmpty( str ) ) return str;
		str.replaceAll( " ", "" );
		
		if( str.startsWith( "'" ) ) str = str.replaceFirst( "'", "" );
		if( str.endsWith( "'" ) ) str = str.substring( 0, str.indexOf( "'" ) );
		
		return str;
	}

	/**
	 * This method adds the parenthesis for a field whose accessor method is
	 * configured.
	 * 
	 * @param fieldName
	 * @return accessorMethodChain
	 */
	public static String constructSrcAccessorMethod( FieldVO fieldVO ){
		StringBuffer methodChain = new StringBuffer();
		methodChain.append( fieldVO.getSrcAccessorMethod() );
		methodChain.append( BeanMapperConstants.BEAN_MAPPER_PARANTHESIS );
		return methodChain.toString();
	}

	/**
	 * This method adds the parenthesis for a field whose mutator method is
	 * configured.
	 * 
	 * @param fieldName
	 * @return mutatorMethodChain
	 */
	public static String constructDestMutatorMethod( FieldVO fieldVO ){
		StringBuffer methodChain = new StringBuffer();
		methodChain.append( fieldVO.getDestMutatorMethod() );
		return methodChain.toString();
	}

	/**
	 * This method constructs the null check chain for the source attribute.
	 * 
	 * @param beanType
	 *            beanA or beanB
	 * @param attribute
	 * @return String
	 */
	public static String getAttributeNullCheckChain( String attribute, FieldVO fieldVO ){
		StringBuffer finalNullCheckChain = new StringBuffer();
		StringBuffer nullCheckChain = new StringBuffer();
		String[] fields = DevToolsUtils.tokenize( attribute, "." );
		String[] methodChain = getMethodChain( BeanMapperConstants.BEAN_MAPPER_SOURCE_BEAN, fields, fieldVO );
		if( !DevToolsUtils.isEmpty( methodChain ) && methodChain.length > 0 ){
			nullCheckChain.append( "if( " );
			for( int i = 0; i < methodChain.length; i++ ){
				nullCheckChain.append( " !Utils.isEmpty( " );
				nullCheckChain.append( methodChain[ i ] );
				nullCheckChain.append( " )" );
				nullCheckChain.append( " &&" );
			}
			if( nullCheckChain.length() > 2 ){
				finalNullCheckChain.append( nullCheckChain.substring( 0, nullCheckChain.length() - 2 ) );
			}
			finalNullCheckChain.append( " )" );
		}
		return finalNullCheckChain.toString();
	}

}
