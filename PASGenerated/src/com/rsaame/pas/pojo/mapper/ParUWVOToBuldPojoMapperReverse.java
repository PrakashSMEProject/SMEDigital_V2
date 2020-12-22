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
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * <li>com.rsaame.pas.vo.bus.PARUWDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( ParUWVOToBuldPojoMapperReverse.class )</code>.
 */
public class ParUWVOToBuldPojoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnBuildingQuo, com.rsaame.pas.vo.bus.PARUWDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public ParUWVOToBuldPojoMapperReverse(){
		super();
	}

	public ParUWVOToBuldPojoMapperReverse( com.rsaame.pas.dao.model.TTrnBuildingQuo src, com.rsaame.pas.vo.bus.PARUWDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PARUWDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PARUWDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PARUWDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PARUWDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "bldDirCode" -> "directorate" */
		if(  !Utils.isEmpty( beanA.getBldDirCode() )  ){
 			beanB.setDirectorate( beanA.getBldDirCode() ); 
 		}

 		/* Mapping: "bldStatus" -> "uwMinDetails.status" */
		if(  !Utils.isEmpty( beanA.getBldStatus() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.getUwMinDetails().setStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldStatus() ) ) );
  		}

 		/* Mapping: "bldStartDate" -> "uwMinDetails.startDate" */
		if(  !Utils.isEmpty( beanA.getBldStartDate() )  ){
 			beanB.getUwMinDetails().setStartDate( beanA.getBldStartDate() ); 
 		}

 		/* Mapping: "bldEndDate" -> "uwMinDetails.endDate" */
		if(  !Utils.isEmpty( beanA.getBldEndDate() )  ){
 			beanB.getUwMinDetails().setEndDate( beanA.getBldEndDate() ); 
 		}

 		/* Mapping: "bldMplFirePerc" -> "emlPrc" */
		if(  !Utils.isEmpty( beanA.getBldMplFirePerc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setEmlPrc( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldMplFirePerc() ) ) );
  		}

 		/* Mapping: "bldMplFire" -> "emlSI" */
		if(  !Utils.isEmpty( beanA.getBldMplFire() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setEmlSI( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldMplFire() ) ) );
  		}

 		/* Mapping: "bldRiRskCode" -> "categoryRI" */
		if(  !Utils.isEmpty( beanA.getBldRiRskCode() )  ){
 			beanB.setCategoryRI( beanA.getBldRiRskCode() ); 
 		}

 		/* Mapping: "bldHazardCode" -> "hazardLevel" */
		if(  !Utils.isEmpty( beanA.getBldHazardCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setHazardLevel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldHazardCode() ) ) );
  		}

 		/* Mapping: "bldConstYr" -> "ageOfBuilding" */
		if(  !Utils.isEmpty( beanA.getBldConstYr() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setAgeOfBuilding( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldConstYr() ) ) );
  		}

		/* Mapping: "ConstructionType" -> "ConstructionType" */
		if(  !Utils.isEmpty( beanA.getBldConstructionCode()) ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setConstructionType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldConstructionCode()) ) );
  		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PARUWDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnBuildingQuo beanA, com.rsaame.pas.vo.bus.PARUWDetailsVO beanB ){
    		BeanUtils.initializeBeanField( "uwMinDetails", beanB );
                		return beanB;
	}
}
