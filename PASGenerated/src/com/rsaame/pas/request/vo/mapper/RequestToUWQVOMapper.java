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
import com.rsaame.pas.vo.bus.UWQuestionRespType;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.UWQuestionsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToUWQVOMapper.class )</code>.
 */
public class RequestToUWQVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.UWQuestionsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToUWQVOMapper(){
		super();
	}

	public RequestToUWQVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.UWQuestionsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.UWQuestionsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.UWQuestionsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.UWQuestionsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.UWQuestionsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "uwA" -> "questions[].response" */
  		int noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "uwA" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getQuestions().get( i ).setResponse (  beanA.getParameter( "uwA[" + i + "]" ) );
		}

 		/* Mapping: "uwCodes" -> "questions[].qId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "uwCodes" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "$bProps", "$aProps" );
			beanB.getQuestions().get( i ).setQId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "uwCodes[" + i + "]" ) ) ) );
 		}
   		
 		/* Mapping: "uwAnsType" -> "questions[].setResponseType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "uwAnsType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "uwAnsType[" + i + "]" ) )){
				beanB.getQuestions().get( i ).setResponseType( beanA.getParameter( "uwAnsType[" + i + "]" ).equalsIgnoreCase( UWQuestionRespType.RADIO.toString())  ?  UWQuestionRespType.RADIO :   UWQuestionRespType.TEXT);
			}
 		}
   		
   		if( !Utils.isEmpty( src.getParameter( "cascade" ) ) ){
   			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "$bProps", "$aProps" );
			beanB.setCascaded( true );
  		} else {
  			beanB.setCascaded( false );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.UWQuestionsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.UWQuestionsVO beanB ){
  		BeanUtils.initializeBeanField( "questions[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "uwCodes[]" ).size() );
    		return beanB;
	}
}
