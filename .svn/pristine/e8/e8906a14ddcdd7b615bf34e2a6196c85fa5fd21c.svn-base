       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPolicyVOMapper.class )</code>.
 */
public class RequestToPolicyDataVOEndtMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PolicyDataVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPolicyDataVOEndtMapper(){
		super();
	}

	public RequestToPolicyDataVOEndtMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PolicyDataVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PolicyDataVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PolicyDataVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PolicyDataVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PolicyDataVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		int noOfItems = 0;
		/* Mapping: "policyId" -> "endorsements[].policyId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "policyId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEndorsmentVO().get( i ).setPolicyId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policyId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endtId" -> "endorsements[].endtId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endtId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEndorsmentVO().get( i ).setEndtId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endtId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endtNo" -> "endorsements[].endNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endtNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEndorsmentVO().get( i ).setEndNo ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endtNo[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "slNo" -> "endorsements[].slNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "slNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getEndorsmentVO().get( i ).setSlNo ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "slNo[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endText" -> "endorsements[].endText" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endText" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getEndorsmentVO().get( i ).setEndText (  beanA.getParameter( "endText[" + i + "]" ) );
		}

		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyDataVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PolicyDataVO beanB ){
  		
        return beanB;
	}
}
