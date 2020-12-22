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
 * <li>com.rsaame.pas.vo.bus.SmsVO</li>
 * <li>com.rsaame.pas.dao.model.TMasSms</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasSmsToSmsVOMapperReverse.class )</code>.
 */
public class TMasSmsToSmsVOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.SmsVO, com.rsaame.pas.dao.model.TMasSms>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasSmsToSmsVOMapperReverse(){
		super();
	}

	public TMasSmsToSmsVOMapperReverse( com.rsaame.pas.vo.bus.SmsVO src, com.rsaame.pas.dao.model.TMasSms dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TMasSms mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TMasSms) Utils.newInstance( "com.rsaame.pas.dao.model.TMasSms" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.SmsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TMasSms beanB = dest;
			
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
			if(beanA.getSmsMode().equals("1")){
				beanB.setSmsMode( "A" );
			}
			else if(beanA.getSmsMode().equals("2")){
				beanB.setSmsMode( "M" );
			}
 			//beanB.setSmsMode( beanA.getSmsMode() ); 
 		}

 		/* Mapping: "smsLevel" -> "smsLevel" */
		if(  !Utils.isEmpty( beanA.getSmsLevel() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setSmsLevel( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSmsLevel() ) ) );
  		}

 		/* Mapping: "smsFrequency" -> "smsFrequency" */
		if(  !Utils.isEmpty( beanA.getSmsFrequency() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setSmsFrequency( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSmsFrequency() ) ) );
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
			if(beanA.getSmsStatus().equalsIgnoreCase("Active")){
				beanB.setSmsStatus( converter.getTypeOfB().cast( converter.getBFromA( "1" ) ) );
			}
			else if(beanA.getSmsStatus().equalsIgnoreCase("Inactive")){
				beanB.setSmsStatus( converter.getTypeOfB().cast( converter.getBFromA( "0" ) ) );
			}
			
			//beanB.setSmsStatus( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSmsStatus() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TMasSms initializeDeepVO( com.rsaame.pas.vo.bus.SmsVO beanA, com.rsaame.pas.dao.model.TMasSms beanB ){
               		return beanB;
	}
}
