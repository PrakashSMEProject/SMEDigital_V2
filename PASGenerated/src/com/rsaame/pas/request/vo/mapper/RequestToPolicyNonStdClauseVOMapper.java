       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPolicyNonStdClauseVOMapper.class )</code>.
 */
public class RequestToPolicyNonStdClauseVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PolicyVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPolicyNonStdClauseVOMapper(){
		super();
	}

	public RequestToPolicyNonStdClauseVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PolicyVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PolicyVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PolicyVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PolicyVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PolicyVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "desc" -> "nonStandardClause[].description" */
  		int noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "desc" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getNonStandardClause().get( i ).setDescription (  beanA.getParameter( "desc[" + i + "]" ) );
		}

 		/* Mapping: "clauseType" -> "nonStandardClause[].clauseType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "clauseType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getNonStandardClause().get( i ).setClauseType (  beanA.getParameter( "clauseType[" + i + "]" ) );
		}
   		
   		/* Mapping: "item_vsd" -> "coverDetails[].vsd" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vsd" ).size();
  		for( int i = 0; i < noOfItems; i++ ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd HH:mm:ss.SSS" );
			beanB.getNonStandardClause().get( i ).setVsd (converter.getTypeOfA().cast(  converter.getAFromB(  beanA.getParameter( "vsd[" + i + "]" ) )) );
   		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PolicyVO beanB ){
  		BeanUtils.initializeBeanField( "nonStandardClause[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "desc[]" ).size() );
  		BeanUtils.initializeBeanField( "nonStandardClause[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vsd[]" ).size() );
    		return beanB;
	}
}
