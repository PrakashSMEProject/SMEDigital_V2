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
 * <li>com.rsaame.pas.vo.app.Contents</li>
 * <li>com.rsaame.pas.vo.bus.PropertyRiskDetails</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PrdToContentsReverse.class )</code>.
 */
public class PrdToContentsReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.app.Contents, com.rsaame.pas.vo.bus.PropertyRiskDetails>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PrdToContentsReverse(){
		super();
	}

	public PrdToContentsReverse( com.rsaame.pas.vo.app.Contents src, com.rsaame.pas.vo.bus.PropertyRiskDetails dest ){
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
		com.rsaame.pas.vo.app.Contents beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PropertyRiskDetails beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "coverCode" -> "coverCode" */
		if(  !Utils.isEmpty( beanA.getCoverCode() )  ){
 			beanB.setCoverCode( beanA.getCoverCode() ); 
 		}

 		/* Mapping: "coverType" -> "coverType" */
		if(  !Utils.isEmpty( beanA.getCoverType() )  ){
 			beanB.setCoverType( beanA.getCoverType() ); 
 		}

 		/* Mapping: "coverSubType" -> "coverSubType" */
		if(  !Utils.isEmpty( beanA.getCoverSubType() )  ){
 			beanB.setCoverSubType( beanA.getCoverSubType() ); 
 		}

 		/* Mapping: "riskType" -> "riskType" */
		if(  !Utils.isEmpty( beanA.getRiskType() )  ){
 			beanB.setRiskType( beanA.getRiskType() ); 
 		}

 		/* Mapping: "riskCat" -> "riskCat" */
		if(  !Utils.isEmpty( beanA.getRiskCat() )  ){
 			beanB.setRiskCat( beanA.getRiskCat() ); 
 		}

 		/* Mapping: "riskSubCat" -> "riskSubCat" */
		if(  !Utils.isEmpty( beanA.getRiskSubCat() )  ){
 			beanB.setRiskSubCat( beanA.getRiskSubCat() ); 
 		}

 		/* Mapping: "cover" -> "cover" */
		if(  !Utils.isEmpty( beanA.getCover() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCover( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCover() ) ) );
  		}

 		/* Mapping: "premium" -> "premium" */
		if(  !Utils.isEmpty( beanA.getPremium() )  ){
 			beanB.setPremium( beanA.getPremium() ); 
 		}

 		/* Mapping: "coverOpted" -> "coverOpted" */
		if(  !Utils.isEmpty( beanA.getCoverOpted() )  ){
 			beanB.setCoverOpted( beanA.getCoverOpted() ); 
 		}

 		/* Mapping: "deductibles" -> "deductibles" */
		if(  !Utils.isEmpty( beanA.getDeductibles() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setDeductibles( converter.getTypeOfB().cast( converter.getBFromA( beanA.getDeductibles() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PropertyRiskDetails initializeDeepVO( com.rsaame.pas.vo.app.Contents beanA, com.rsaame.pas.vo.bus.PropertyRiskDetails beanB ){
                     		return beanB;
	}
}
