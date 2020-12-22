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
 * <li>com.rsaame.pas.vo.bus.EEUWDetailsVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnContentQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EEUWDetailsVOToContentPojoMapper.class )</code>.
 */
public class EEUWDetailsVOToContentPojoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.EEUWDetailsVO, com.rsaame.pas.dao.model.TTrnContentQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EEUWDetailsVOToContentPojoMapper(){
		super();
	}

	public EEUWDetailsVOToContentPojoMapper( com.rsaame.pas.vo.bus.EEUWDetailsVO src, com.rsaame.pas.dao.model.TTrnContentQuo dest ){
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
		com.rsaame.pas.vo.bus.EEUWDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnContentQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "emlPrc" -> "cntMplFirePerc" */
		if(  !Utils.isEmpty( beanA.getEmlPrc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCntMplFirePerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmlPrc() ) ) );
  		}

 		/* Mapping: "emlSI" -> "cntMplFire" */
		if(  !Utils.isEmpty( beanA.getEmlSI() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCntMplFire( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmlSI() ) ) );
  		}

 		/* Mapping: "emlBIPrc" -> "cntMplFloodPerc" */
		if(  !Utils.isEmpty( beanA.getEmlBIPrc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCntMplFloodPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmlBIPrc() ) ) );
  		}

 		/* Mapping: "emlBI" -> "cntMplFlood" */
		if(  !Utils.isEmpty( beanA.getEmlBI() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCntMplFlood( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmlBI() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnContentQuo initializeDeepVO( com.rsaame.pas.vo.bus.EEUWDetailsVO beanA, com.rsaame.pas.dao.model.TTrnContentQuo beanB ){
         		return beanB;
	}
}
