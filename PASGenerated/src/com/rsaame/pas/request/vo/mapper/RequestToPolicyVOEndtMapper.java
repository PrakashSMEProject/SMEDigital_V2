       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.EndorsmentVO;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPolicyVOMapper.class )</code>.
 */
public class RequestToPolicyVOEndtMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PolicyVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPolicyVOEndtMapper(){
		super();
	}

	public RequestToPolicyVOEndtMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PolicyVO dest ){
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

		int noOfItems = 0;
		String endType = null;
		/* Mapping: "policyId" -> "endorsements[].policyId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "policyId" ).size();
  		List<EndorsmentVO> endorsements = beanB.getEndorsements();
  		
  		// bug 1903
		if (!Utils.isEmpty(endorsements) && noOfItems != endorsements.size() && noOfItems != 0) {
			List<EndorsmentVO> temp = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>(
					EndorsmentVO.class);
			for (int i = 0; i < noOfItems; i++) {
				EndorsmentVO endorsmentVO = new EndorsmentVO();
				temp.add(endorsmentVO);
			}
			if (!Utils.isEmpty(src.getParameter("endType"))) {
				endType = src.getParameter("endType");
			}
			temp.get(0).setEndType(endType);
			endorsements = temp;
		}
  	
		
   		for( int i = 0; i < noOfItems; i++ )
   		{
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
        			endorsements.get( i ).setPolicyId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policyId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endtId" -> "endorsements[].endtId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endtId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
        			endorsements.get( i ).setEndtId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endtId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endtNo" -> "endorsements[].endNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endtNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
        			endorsements.get( i ).setEndNo ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endtNo[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "slNo" -> "endorsements[].slNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "slNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
        			endorsements.get( i ).setSlNo ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "slNo[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endText" -> "endorsements[].endText" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endText" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   					endorsements.get( i ).setEndText (  beanA.getParameter( "endText[" + i + "]" ) );
		}

		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PolicyVO beanB ){
  		
        return beanB;
	}
}
