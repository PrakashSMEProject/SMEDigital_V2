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
 * <li>com.rsaame.pas.vo.bus.EquipmentVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnContentQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EquipmentVOToContentPojoMapper.class )</code>.
 */
public class EquipmentVOToContentPojoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.EquipmentVO, com.rsaame.pas.dao.model.TTrnContentQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EquipmentVOToContentPojoMapper(){
		super();
	}

	public EquipmentVOToContentPojoMapper( com.rsaame.pas.vo.bus.EquipmentVO src, com.rsaame.pas.dao.model.TTrnContentQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnContentQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnContentQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnContentQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.EquipmentVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnContentQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "equipmentType[]" -> "cntRiskSubDtl" */
		if(  !Utils.isEmpty( beanA.getEquipmentType() ) && !Utils.isEmpty( beanA.getEquipmentType() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCntRiskSubDtl( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEquipmentType() ) ) );
  		}

 		/* Mapping: "equipmentDesc[]" -> "cntDescription" */
		if(  !Utils.isEmpty( beanA.getEquipmentDesc() ) && !Utils.isEmpty( beanA.getEquipmentDesc() )  ){
 			beanB.setCntDescription( beanA.getEquipmentDesc() ); 
 		}

 		/* Mapping: "yearOfMake[]" -> "cntYearOfMan" 
		if(  !Utils.isEmpty( beanA.getYearOfMake() ) && !Utils.isEmpty( beanA.getYearOfMake() )  ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setCntYearOfMan( converter.getTypeOfA().cast( converter.getAFromB( beanA.getYearOfMake() ) ) );
  		}

 		 Mapping: "serialNumber[]" -> "cntEqptSerialNo" 
		if(  !Utils.isEmpty( beanA.getSerialNumber() ) && !Utils.isEmpty( beanA.getSerialNumber() )  ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setCntEqptSerialNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSerialNumber() ) ) );
  		}

 		/* Mapping: "quantity[]" -> "cntQty"
		if(  !Utils.isEmpty( beanA.getQuantity() ) && !Utils.isEmpty( beanA.getQuantity() )  ){
			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setCntQty( converter.getTypeOfA().cast( converter.getAFromB( beanA.getQuantity() ) ) );
  		}
*/ 
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnContentQuo initializeDeepVO( com.rsaame.pas.vo.bus.EquipmentVO beanA, com.rsaame.pas.dao.model.TTrnContentQuo beanB ){
           		return beanB;
	}
}
