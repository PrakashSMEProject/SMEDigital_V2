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
 * <li>com.rsaame.pas.vo.bus.CommentsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToCommentsVOMapper.class )</code>.
 */
public class RequestToCommentsVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.CommentsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToCommentsVOMapper(){
		super();
	}

	public RequestToCommentsVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.CommentsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CommentsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CommentsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.CommentsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CommentsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "quo_comments" -> "comment" */
		if( !Utils.isEmpty( src.getParameter( "quo_comments" ) ) ){
 			beanB.setComment( beanA.getParameter( "quo_comments" ) ); 
 		}

 		/* Mapping: "quo_status" -> "policyStatus" */
		if( !Utils.isEmpty( src.getParameter( "quo_status" ) ) ){
 			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setPolicyStatus( converter.getTypeOfB().cast( converter.getBFromA( beanA.getParameter( "quo_status" ) ) ) );
  		}

 		/* Mapping: "quo_other_insurer" -> "otherInsCode" */
		if( !Utils.isEmpty( src.getParameter( "quo_other_insurer" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setOtherInsCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quo_other_insurer" ) ) ) );
  		}

 		/* Mapping: "quo_reason" -> "reasonCode" */
		if( !Utils.isEmpty( src.getParameter( "quo_reason" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setReasonCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quo_reason" ) ) ) );
  		}
		
		/* Mapping: "comments" -> "poc_comments" */
		if( !Utils.isEmpty( src.getParameter( "commentsTxt" ) ) ){
			
			beanB.setComment( beanA.getParameter( "commentsTxt" ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.CommentsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.CommentsVO beanB ){
         		return beanB;
	}
}
