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
 * <li>com.rsaame.pas.dao.model.TTrnPremiumQuo</li>
 * <li>com.rsaame.pas.vo.bus.EmpTypeDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EmpTypeDetailsVOToTTrnPremiumQuoMapperReverse.class )</code>.
 */
public class EmpTypeDetailsVOToTTrnPremiumQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPremiumQuo, com.rsaame.pas.vo.bus.EmpTypeDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EmpTypeDetailsVOToTTrnPremiumQuoMapperReverse(){
		super();
	}

	public EmpTypeDetailsVOToTTrnPremiumQuoMapperReverse( com.rsaame.pas.dao.model.TTrnPremiumQuo src, com.rsaame.pas.vo.bus.EmpTypeDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.EmpTypeDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.EmpTypeDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.EmpTypeDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPremiumQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "prmCompulsoryExcess" -> "deductibles" */
		if(  !Utils.isEmpty( beanA.getPrmCompulsoryExcess() )  ){
 			beanB.setDeductibles( beanA.getPrmCompulsoryExcess() ); 
 		}

 		/* Mapping: "prmSumInsured" -> "wageroll" */
		if(  !Utils.isEmpty( beanA.getPrmSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setWageroll( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmSumInsured() ) ) );
  		}

 		/* Mapping: "id.prmBasicRskCode" -> "basicRiskCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrmBasicRskCode() )  ){
 			beanB.setBasicRiskCode( beanA.getId().getPrmBasicRskCode() ); 
 		}

 		/* Mapping: "prmClCode" -> "classCode" */
		if(  !Utils.isEmpty( beanA.getPrmClCode() )  ){
 			beanB.setClassCode( beanA.getPrmClCode() ); 
 		}

 		/* Mapping: "prmRcCode" -> "riskCat" */
		if(  !Utils.isEmpty( beanA.getPrmRcCode() )  ){
 			beanB.setRiskCat( beanA.getPrmRcCode() ); 
 		}

 		/* Mapping: "prmRscCode" -> "riskSubCat" */
		if(  !Utils.isEmpty( beanA.getPrmRscCode() )  ){
 			beanB.setRiskSubCat( beanA.getPrmRscCode() ); 
 		}

 		/* Mapping: "id.prmRskCode" -> "riskCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrmRskCode() )  ){
 			beanB.setRiskCode( beanA.getId().getPrmRskCode() ); 
 		}

 		/* Mapping: "prmRtCode" -> "riskType" */
		if(  !Utils.isEmpty( beanA.getPrmRtCode() )  ){
 			beanB.setRiskType( beanA.getPrmRtCode() ); 
 		}

 		/* Mapping: "prmPremium" -> "premium.premiumAmt" */
		if(  !Utils.isEmpty( beanA.getPrmPremium() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremium().setPremiumAmt( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmPremium() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.EmpTypeDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnPremiumQuo beanA, com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanB ){
                  		BeanUtils.initializeBeanField( "premium", beanB );
  		return beanB;
	}
}
