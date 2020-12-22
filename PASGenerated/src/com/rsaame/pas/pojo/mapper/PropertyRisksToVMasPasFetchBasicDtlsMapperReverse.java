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
 * <li>com.rsaame.pas.dao.model.VMasPasFetchBasicDtls</li>
 * <li>com.rsaame.pas.vo.bus.PropertyRiskDetails</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PropertyRisksToVMasPasFetchBasicDtlsMapperReverse.class )</code>.
 */
public class PropertyRisksToVMasPasFetchBasicDtlsMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VMasPasFetchBasicDtls, com.rsaame.pas.vo.bus.PropertyRiskDetails>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PropertyRisksToVMasPasFetchBasicDtlsMapperReverse(){
		super();
	}

	public PropertyRisksToVMasPasFetchBasicDtlsMapperReverse( com.rsaame.pas.dao.model.VMasPasFetchBasicDtls src, com.rsaame.pas.vo.bus.PropertyRiskDetails dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PropertyRiskDetails mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PropertyRiskDetails) Utils.newInstance( "com.rsaame.pas.vo.bus.PropertyRiskDetails" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.VMasPasFetchBasicDtls beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PropertyRiskDetails beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.prSumInsured" -> "cover" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCover( converter.getTypeOfB().cast( converter.getBFromA( beanA.getId().getPrSumInsured() ) ) );
  		}

 		/* Mapping: "id.prCompulsoryExcess" -> "deductibles" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCompulsoryExcess() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setDeductibles( converter.getTypeOfB().cast( converter.getBFromA( beanA.getId().getPrCompulsoryExcess() ) ) );
  		}

 		/* Mapping: "id.prCovCode" -> "coverCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCovCode() )  ){
 			beanB.setCoverCode( Integer.valueOf(beanA.getId().getPrCovCode() )); 
 		}

 		/* Mapping: "id.prCtCode" -> "coverType" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCtCode() )  ){
 			beanB.setCoverType( Integer.valueOf(beanA.getId().getPrCtCode() )); 
 		}

 		/* Mapping: "id.prCstCode" -> "coverSubType" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCstCode() )  ){
 			beanB.setCoverSubType( Integer.valueOf(beanA.getId().getPrCstCode() )); 
 		}

 		/* Mapping: "id.prRskCode" -> "riskCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRskCode() )  ){
 			beanB.setRiskCode( beanA.getId().getPrRskCode() ); 
 		}

 		/* Mapping: "id.prRtCode" -> "riskType" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRtCode() )  ){
 			beanB.setRiskType( beanA.getId().getPrRtCode() ); 
 		}

 		/* Mapping: "id.prRcCode" -> "riskCat" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRcCode() )  ){
 			beanB.setRiskCat( beanA.getId().getPrRcCode() ); 
 		}

 		/* Mapping: "id.prRscCode" -> "riskSubCat" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRscCode() )  ){
 			beanB.setRiskSubCat( beanA.getId().getPrRscCode() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PropertyRiskDetails initializeDeepVO( com.rsaame.pas.dao.model.VMasPasFetchBasicDtls beanA, com.rsaame.pas.vo.bus.PropertyRiskDetails beanB ){
                   		return beanB;
	}
}
