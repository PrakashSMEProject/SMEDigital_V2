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
 * <li>com.rsaame.pas.vo.bus.EEUWDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EEUWDetailsVOToContentPojoMapperReverse.class )</code>.
 */
public class EEUWDetailsVOToContentPojoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnContentQuo, com.rsaame.pas.vo.bus.EEUWDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EEUWDetailsVOToContentPojoMapperReverse(){
		super();
	}

	public EEUWDetailsVOToContentPojoMapperReverse( com.rsaame.pas.dao.model.TTrnContentQuo src, com.rsaame.pas.vo.bus.EEUWDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.EEUWDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.EEUWDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.EEUWDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnContentQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.EEUWDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "cntMplFirePerc" -> "emlPrc" */
		if(  !Utils.isEmpty( beanA.getCntMplFirePerc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setEmlPrc( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntMplFirePerc() ) ) );
  		}

 		/* Mapping: "cntMplFire" -> "emlSI" */
		if(  !Utils.isEmpty( beanA.getCntMplFire() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setEmlSI( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntMplFire() ) ) );
  		}

 		/* Mapping: "cntMplFloodPerc" -> "emlBIPrc" */
		if(  !Utils.isEmpty( beanA.getCntMplFloodPerc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setEmlBIPrc( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntMplFloodPerc() ) ) );
  		}

 		/* Mapping: "cntMplFlood" -> "emlBI" */
		if(  !Utils.isEmpty( beanA.getCntMplFlood() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setEmlBI( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntMplFlood() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.EEUWDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnContentQuo beanA, com.rsaame.pas.vo.bus.EEUWDetailsVO beanB ){
         		return beanB;
	}
}
