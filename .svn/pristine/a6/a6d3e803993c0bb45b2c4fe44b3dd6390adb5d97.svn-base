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
 * <li>com.rsaame.pas.dao.model.TTrnContentQuo</li>
 * <li>com.rsaame.pas.vo.bus.EquipmentVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EquipmentVOToContentPojoMapperReverse.class )</code>.
 */
public class EquipmentVOToContentPojoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnContentQuo, com.rsaame.pas.vo.bus.EquipmentVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EquipmentVOToContentPojoMapperReverse(){
		super();
	}

	public EquipmentVOToContentPojoMapperReverse( com.rsaame.pas.dao.model.TTrnContentQuo src, com.rsaame.pas.vo.bus.EquipmentVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.EquipmentVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.EquipmentVO) Utils.newInstance( "com.rsaame.pas.vo.bus.EquipmentVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnContentQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.EquipmentVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "cntRiskSubDtl" -> "equipmentType[]" */
		if(  !Utils.isEmpty( beanA.getCntRiskSubDtl() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setEquipmentType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntRiskSubDtl() ) ) );
  		}

 		/* Mapping: "cntDescription" -> "equipmentDesc[]" */
		if(  !Utils.isEmpty( beanA.getCntDescription() )  ){
 			beanB.setEquipmentDesc( beanA.getCntDescription() ); 
 		}

 	/*	 Mapping: "cntYearOfMan" -> "yearOfMake[]" 
		if(  !Utils.isEmpty( beanA.getCntYearOfMan() )  ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setYearOfMake( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntYearOfMan() ) ) );
  		}

 		 Mapping: "cntEqptSerialNo" -> "serialNumber[]" 
		if(  !Utils.isEmpty( beanA.getCntEqptSerialNo() )  ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setSerialNumber( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntEqptSerialNo() ) ) );
  		}

 		/* Mapping: "cntQty" -> "quantity[]" 
		if(  !Utils.isEmpty( beanA.getCntQty() )  ){
			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setQuantity( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntQty() ) ) );
  		}
*/
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.EquipmentVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnContentQuo beanA, com.rsaame.pas.vo.bus.EquipmentVO beanB ){
  		BeanUtils.initializeBeanField( "equipmentType[]", beanB );
   		BeanUtils.initializeBeanField( "equipmentDesc[]", beanB );
   		BeanUtils.initializeBeanField( "yearOfMake[]", beanB );
   		BeanUtils.initializeBeanField( "serialNumber[]", beanB );
   		BeanUtils.initializeBeanField( "quantity[]", beanB );
  		return beanB;
	}
}
