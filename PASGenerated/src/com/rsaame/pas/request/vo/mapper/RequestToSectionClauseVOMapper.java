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
 * <li>com.rsaame.pas.vo.bus.SectionVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToSectionClauseVOMapper.class )</code>.
 */
public class RequestToSectionClauseVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.SectionVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToSectionClauseVOMapper(){
		super();
	}

	public RequestToSectionClauseVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.SectionVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.SectionVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.SectionVO) Utils.newInstance( "com.rsaame.pas.vo.bus.SectionVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.SectionVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "clauseCode" -> "standardClauses[].clauseCode" */
  		int noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "clauseCode" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "$bProps", "$aProps" );
			beanB.getStandardClauses().get( i ).setClauseCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "clauseCode[" + i + "]" ) ) ) );
 		}
   		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "desc" ).size();
   		for(int i = 0; i < noOfItems; i++){
   			beanB.getStandardClauses().get( i ).setSelected(  false  );
   		}
 		/* Mapping: "flag" -> "standardClauses[].isSelected" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "flag" ).size();
  		
   		for( int i = 0; i < noOfItems; i++ ){
        	com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "$bProps", "$aProps" );
        	
        	String name = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "flag" ).get(i);
        	String index = name.substring(5,name.lastIndexOf(']'));
        	if(converter.getAFromB( beanA.getParameter( name) )== null ){
            	beanB.getStandardClauses().get( Integer.parseInt( index ) ).setSelected(converter.getTypeOfA().cast( false ) );
            } else {
            	beanB.getStandardClauses().get( Integer.parseInt( index ) ).setSelected(converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( name ) ) ) );
            }
 		}

 		/* Mapping: "desc" -> "standardClauses[].description" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "desc" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getStandardClauses().get( i ).setDescription (  beanA.getParameter( "desc[" + i + "]" ) );
		}

 		/* Mapping: "clauseType" -> "standardClauses[].clauseType" */
  		 noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "clauseType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			System.out.println(beanA.getParameter( "clauseType[" + i + "]" ));
        			beanB.getStandardClauses().get( i ).setClauseType (  beanA.getParameter( "clauseType[" + i + "]" ) );
		}

 		/* Mapping: "amount" -> "standardClauses[].amount" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "amount" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			//com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "$bProps", "$aProps" );
			beanB.getStandardClauses().get( i ).setAmount ( beanA.getParameter( "amount[" + i + "]" ) );
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.SectionVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.SectionVO beanB ){
  		BeanUtils.initializeBeanField( "standardClauses[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "clauseCode[]" ).size() );
          		return beanB;
	}
}
