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
 * <li>com.rsaame.pas.vo.bus.EmpTypeDetailsVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnPremiumQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EmpTypeDetailsVOToTTrnPremiumQuoMapper.class )</code>.
 */
public class EmpTypeDetailsVOToTTrnPremiumQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.EmpTypeDetailsVO, com.rsaame.pas.dao.model.TTrnPremiumQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EmpTypeDetailsVOToTTrnPremiumQuoMapper(){
		super();
	}

	public EmpTypeDetailsVOToTTrnPremiumQuoMapper( com.rsaame.pas.vo.bus.EmpTypeDetailsVO src, com.rsaame.pas.dao.model.TTrnPremiumQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPremiumQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPremiumQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPremiumQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPremiumQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "deductibles" -> "prmCompulsoryExcess" */
		if(  !Utils.isEmpty( beanA.getDeductibles() )  ){
 			beanB.setPrmCompulsoryExcess( beanA.getDeductibles() ); 
 		}

 		/* Mapping: "wageroll" -> "prmSumInsured" */
		if(  !Utils.isEmpty( beanA.getWageroll() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWageroll() ) ) );
  		}

 		/* Mapping: "basicRiskCode" -> "id.prmBasicRskCode" */
		if(  !Utils.isEmpty( beanA.getBasicRiskCode() )  ){
 			beanB.getId().setPrmBasicRskCode( beanA.getBasicRiskCode() ); 
 		}

 		/* Mapping: "classCode" -> "prmClCode" */
		if(  !Utils.isEmpty( beanA.getClassCode() )  ){
 			beanB.setPrmClCode( beanA.getClassCode() ); 
 		}

 		/* Mapping: "riskCat" -> "prmRcCode" */
		if(  !Utils.isEmpty( beanA.getRiskCat() )  ){
 			beanB.setPrmRcCode( beanA.getRiskCat() ); 
 		}

 		/* Mapping: "riskSubCat" -> "prmRscCode" */
		if(  !Utils.isEmpty( beanA.getRiskSubCat() )  ){
 			beanB.setPrmRscCode( beanA.getRiskSubCat() ); 
 		}

 		/* Mapping: "riskCode" -> "id.prmRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCode() )  ){
 			beanB.getId().setPrmRskCode( beanA.getRiskCode() ); 
 		}

 		/* Mapping: "riskType" -> "prmRtCode" */
		if(  !Utils.isEmpty( beanA.getRiskType() )  ){
 			beanB.setPrmRtCode( beanA.getRiskType() ); 
 		}

 		/* Mapping: "premium.premiumAmt" -> "prmPremium" */
		if(  !Utils.isEmpty( beanA.getPremium() ) && !Utils.isEmpty( beanA.getPremium().getPremiumAmt() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmPremium( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremium().getPremiumAmt() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPremiumQuo initializeDeepVO( com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanA, com.rsaame.pas.dao.model.TTrnPremiumQuo beanB ){
      		BeanUtils.initializeBeanField( "id", beanB );
              		return beanB;
	}
}
