       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.PropertyRiskDetails</li>
 * <li>com.rsaame.pas.dao.model.VMasPasFetchBasicDtls</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PropertyRisksToVMasPasFetchBasicDtlsMapper.class )</code>.
 */
public class PropertyRisksToVMasPasFetchBasicDtlsMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PropertyRiskDetails, com.rsaame.pas.dao.model.VMasPasFetchBasicDtls>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PropertyRisksToVMasPasFetchBasicDtlsMapper(){
		super();
	}

	public PropertyRisksToVMasPasFetchBasicDtlsMapper( com.rsaame.pas.vo.bus.PropertyRiskDetails src, com.rsaame.pas.dao.model.VMasPasFetchBasicDtls dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.VMasPasFetchBasicDtls mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.VMasPasFetchBasicDtls) Utils.newInstance( "com.rsaame.pas.dao.model.VMasPasFetchBasicDtls" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PropertyRiskDetails beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.VMasPasFetchBasicDtls beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "cover" -> "id.prSumInsured" */
		if(  !Utils.isEmpty( beanA.getCover() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getId().setPrSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCover() ) ) );
  		}

 		/* Mapping: "deductibles" -> "id.prCompulsoryExcess" */
		if(  !Utils.isEmpty( beanA.getDeductibles() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getId().setPrCompulsoryExcess( converter.getTypeOfA().cast( converter.getAFromB( beanA.getDeductibles() ) ) );
  		}

 		/* Mapping: "coverCode" -> "id.prCovCode" */
		if(  !Utils.isEmpty( beanA.getCoverCode() )  ){
 			beanB.getId().setPrCovCode( beanA.getCoverCode().shortValue() ); 
 		}

 		/* Mapping: "coverType" -> "id.prCtCode" */
		if(  !Utils.isEmpty( beanA.getCoverType() )  ){
 			beanB.getId().setPrCtCode( beanA.getCoverType().shortValue() ); 
 		}

 		/* Mapping: "coverSubType" -> "id.prCstCode" */
		if(  !Utils.isEmpty( beanA.getCoverSubType() )  ){
 			beanB.getId().setPrCstCode( beanA.getCoverSubType().shortValue() ); 
 		}

 		/* Mapping: "riskCode" -> "id.prRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCode() )  ){
 			beanB.getId().setPrRskCode( beanA.getRiskCode() ); 
 		}

 		/* Mapping: "riskType" -> "id.prRtCode" */
		if(  !Utils.isEmpty( beanA.getRiskType() )  ){
 			beanB.getId().setPrRtCode( beanA.getRiskType() ); 
 		}

 		/* Mapping: "riskCat" -> "id.prRcCode" */
		if(  !Utils.isEmpty( beanA.getRiskCat() )  ){
 			beanB.getId().setPrRcCode( beanA.getRiskCat() ); 
 		}

 		/* Mapping: "riskSubCat" -> "id.prRscCode" */
		if(  !Utils.isEmpty( beanA.getRiskSubCat() )  ){
 			beanB.getId().setPrRscCode( beanA.getRiskSubCat() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.VMasPasFetchBasicDtls initializeDeepVO( com.rsaame.pas.vo.bus.PropertyRiskDetails beanA, com.rsaame.pas.dao.model.VMasPasFetchBasicDtls beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                  		return beanB;
	}
}
