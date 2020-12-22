       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnContentQuo</li>
 * <li>com.rsaame.pas.vo.bus.CoverDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CoverDetailsVOToTTrnContentQuoMapperReverse.class )</code>.
 */
public class CoverDetailsVOToTTrnContentQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnContentQuo, com.rsaame.pas.vo.bus.CoverDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CoverDetailsVOToTTrnContentQuoMapperReverse(){
		super();
	}

	public CoverDetailsVOToTTrnContentQuoMapperReverse( com.rsaame.pas.dao.model.TTrnContentQuo src, com.rsaame.pas.vo.bus.CoverDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CoverDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CoverDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.CoverDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnContentQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CoverDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "cntDescription" -> "coverName" */
		if(  !Utils.isEmpty( beanA.getCntDescription() )  ){
 			beanB.setCoverName( beanA.getCntDescription() ); 
 		}

 		/* Mapping: "cntSumInsured" -> "sumInsured.sumInsured" */
		if(  !Utils.isEmpty( beanA.getCntSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getSumInsured().setSumInsured( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCntSumInsured() ) ) );
  		}

 		/* Mapping: "cntRskCode" -> "riskCodes.riskCode" */
		if(  !Utils.isEmpty( beanA.getCntRskCode() )  ){
 			beanB.getRiskCodes().setRiskCode( beanA.getCntRskCode() ); 
 		}

 		/* Mapping: "cntBasicRskCode" -> "riskCodes.basicRskCode" */
		if(  !Utils.isEmpty( beanA.getCntBasicRskCode() )  ){
 			beanB.getRiskCodes().setBasicRskCode( beanA.getCntBasicRskCode() ); 
 		}

 		/* Mapping: "cntCategory" -> "riskCodes.riskType" */
		if(  !Utils.isEmpty( beanA.getCntCategory() )  ){
 			beanB.getRiskCodes().setRiskType( beanA.getCntCategory() ); 
 		}

 		/* Mapping: "cntRiskSubDtl" -> "riskCodes.riskCat" */
		if(  !Utils.isEmpty( beanA.getCntRiskSubDtl() )  ){
 			beanB.getRiskCodes().setRiskCat( beanA.getCntRiskSubDtl() ); 
 		}

		/* Mapping: "cntRiskDtl" -> "riskCodes.riskType" */
		if(  !Utils.isEmpty( beanA.getCntRiskDtl() )){
			beanB.getRiskCodes().setRiskType(Integer.parseInt( beanA.getCntRiskDtl().toString() ));
 		}
		
		/* Mapping: "cntRiskSubDtl" -> "riskCodes.riskCat" */
		if(  !Utils.isEmpty( beanA.getCntRiskSubDtl() ) ){
			beanB.getRiskCodes().setRiskCat( beanA.getCntRiskSubDtl() );
 		}
		
		/* Mapping: "cntRiskSubDtl" -> "riskCodes.riskCat" */
		if(  !Utils.isEmpty( beanA.getId().getCntContentId() ) ){
			beanB.getRiskCodes().setRskId( BigDecimal.valueOf(  beanA.getId().getCntContentId() ));
		}
		/* Mapping: "ValidityStartDate -> "vsd" */
		if( !Utils.isEmpty( beanA.getId().getVSD() )){
			beanB.setVsd( beanA.getId().getVSD() );
		}
		
		if( !Utils.isEmpty( beanA.getCntRiRskCode() ) ){
			beanB.setRiRskCode( beanA.getCntRiRskCode() );
		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.CoverDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnContentQuo beanA, com.rsaame.pas.vo.bus.CoverDetailsVO beanB ){
    		BeanUtils.initializeBeanField( "sumInsured", beanB );
   		BeanUtils.initializeBeanField( "riskCodes", beanB );
        		return beanB;
	}
}
