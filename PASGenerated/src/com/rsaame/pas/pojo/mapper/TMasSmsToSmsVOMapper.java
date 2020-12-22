       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TMasSms</li>
 * <li>com.rsaame.pas.vo.bus.SmsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasSmsToSmsVOMapper.class )</code>.
 */
public class TMasSmsToSmsVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TMasSms, com.rsaame.pas.vo.bus.SmsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasSmsToSmsVOMapper(){
		super();
	}

	public TMasSmsToSmsVOMapper( com.rsaame.pas.dao.model.TMasSms src, com.rsaame.pas.vo.bus.SmsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.SmsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.SmsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.SmsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TMasSms beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.SmsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "smsID" -> "smsID" */
		if(  !Utils.isEmpty( beanA.getSmsID() )  ){
 			beanB.setSmsID( beanA.getSmsID() ); 
 		}

 		/* Mapping: "smsMode" -> "smsMode" */
		if(  !Utils.isEmpty( beanA.getSmsMode() )  ){
 			beanB.setSmsMode( beanA.getSmsMode() ); 
 		}

 		/* Mapping: "smsLevel" -> "smsLevel" */
		if(  !Utils.isEmpty( beanA.getSmsLevel() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			com.rsaame.pas.cmn.converter.ShortStringConverter converter2 = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class,"","");
			beanB.setSmsLevel( converter2.getTypeOfA().cast(converter2.getAFromB(converter.getTypeOfA().cast( converter.getAFromB( beanA.getSmsLevel() ) )) ));
  		}

 		/* Mapping: "smsFrequency" -> "smsFrequency" */
		if(  !Utils.isEmpty( beanA.getSmsFrequency() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			com.rsaame.pas.cmn.converter.ShortStringConverter converter2 = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class,"","");
			beanB.setSmsFrequency( converter2.getTypeOfA().cast(converter2.getAFromB(converter.getTypeOfA().cast( converter.getAFromB( beanA.getSmsFrequency() ) ) )));
  		}

 		/* Mapping: "smsEngBody" -> "smsEngBody" */
		if(  !Utils.isEmpty( beanA.getSmsEngBody() )  ){
 			beanB.setSmsEngBody( beanA.getSmsEngBody() ); 
 		}

 		/* Mapping: "smsArabicBody" -> "smsArabicBody" */
		if(  !Utils.isEmpty( beanA.getSmsArabicBody() )  ){
 			beanB.setSmsArabicBody( beanA.getSmsArabicBody() ); 
 		}

 		/* Mapping: "smsStatus" -> "smsStatus" */
		if(  !Utils.isEmpty( beanA.getSmsStatus() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setSmsStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSmsStatus() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.SmsVO initializeDeepVO( com.rsaame.pas.dao.model.TMasSms beanA, com.rsaame.pas.vo.bus.SmsVO beanB ){
               		return beanB;
	}
}
