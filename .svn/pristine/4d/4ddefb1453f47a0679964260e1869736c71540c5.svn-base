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
 * <li>com.rsaame.pas.vo.bus.PARUWDetailsVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( ParUWVOToBuldPojoMapper.class )</code>.
 */
public class ParUWVOToBuldPojoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PARUWDetailsVO, com.rsaame.pas.dao.model.TTrnBuildingQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public ParUWVOToBuldPojoMapper(){
		super();
	}

	public ParUWVOToBuldPojoMapper( com.rsaame.pas.vo.bus.PARUWDetailsVO src, com.rsaame.pas.dao.model.TTrnBuildingQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnBuildingQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnBuildingQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnBuildingQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PARUWDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "directorate" -> "bldDirCode" */
		if(  !Utils.isEmpty( beanA.getDirectorate() )  ){
 			beanB.setBldDirCode( beanA.getDirectorate() ); 
 		}

 		/* Mapping: "uwMinDetails.status" -> "bldStatus" */
		if(  !Utils.isEmpty( beanA.getUwMinDetails() ) && !Utils.isEmpty( beanA.getUwMinDetails().getStatus() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setBldStatus( converter.getTypeOfB().cast( converter.getBFromA( beanA.getUwMinDetails().getStatus() ) ) );
  		}

 		/* Mapping: "uwMinDetails.startDate" -> "bldStartDate" */
		if(  !Utils.isEmpty( beanA.getUwMinDetails() ) && !Utils.isEmpty( beanA.getUwMinDetails().getStartDate() )  ){
 			beanB.setBldStartDate( beanA.getUwMinDetails().getStartDate() ); 
 		}

 		/* Mapping: "uwMinDetails.endDate" -> "bldEndDate" */
		if(  !Utils.isEmpty( beanA.getUwMinDetails() ) && !Utils.isEmpty( beanA.getUwMinDetails().getEndDate() )  ){
 			beanB.setBldEndDate( beanA.getUwMinDetails().getEndDate() ); 
 		}

 		/* Mapping: "emlPrc" -> "bldMplFirePerc" */
		if(  !Utils.isEmpty( beanA.getEmlPrc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setBldMplFirePerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmlPrc() ) ) );
  		}

 		/* Mapping: "emlSI" -> "bldMplFire" */
		if(  !Utils.isEmpty( beanA.getEmlSI() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setBldMplFire( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmlSI() ) ) );
  		}

 		/* Mapping: "categoryRI" -> "bldRiRskCode" */
		if(  !Utils.isEmpty( beanA.getCategoryRI() )  ){
 			beanB.setBldRiRskCode( beanA.getCategoryRI() ); 
 		}

 		/* Mapping: "hazardLevel" -> "bldHazardCode" */
		if(  !Utils.isEmpty( beanA.getHazardLevel() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldHazardCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getHazardLevel() ) ) );
  		}

 		/* Mapping: "ageOfBuilding" -> "bldConstYr" */
		if(  !Utils.isEmpty( beanA.getAgeOfBuilding() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldConstYr( converter.getTypeOfB().cast( converter.getBFromA( beanA.getAgeOfBuilding() ) ) );
  		}
		
		/* Mapping: "ConstructionType" -> "ConstructionType" */
		if(  !Utils.isEmpty( beanA.getConstructionType()) ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldConstructionCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getConstructionType()) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnBuildingQuo initializeDeepVO( com.rsaame.pas.vo.bus.PARUWDetailsVO beanA, com.rsaame.pas.dao.model.TTrnBuildingQuo beanB ){
                   		return beanB;
	}
}
