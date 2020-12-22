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
 * <li>com.rsaame.pas.vo.bus.WCVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnPremiumQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WCVOtoTTrnPremiumQuoMapper.class )</code>.
 */
public class WCVOtoTTrnPremiumQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.WCVO, com.rsaame.pas.dao.model.TTrnPremiumQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public WCVOtoTTrnPremiumQuoMapper(){
		super();
	}

	public WCVOtoTTrnPremiumQuoMapper( com.rsaame.pas.vo.bus.WCVO src, com.rsaame.pas.dao.model.TTrnPremiumQuo dest ){
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
		com.rsaame.pas.vo.bus.WCVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPremiumQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "deductibles" -> "prmCompulsoryExcess" */
		if(  !Utils.isEmpty( beanA.getDeductibles() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmCompulsoryExcess( converter.getTypeOfA().cast( converter.getAFromB( beanA.getDeductibles() ) ) );
  		}

 		/* Mapping: "wageroll" -> "prmSumInsured" */
		if(  !Utils.isEmpty( beanA.getWageroll() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWageroll() ) ) );
  		}

 		/* Mapping: "basicRiskcode" -> "id.prmBasicRskCode" */
		if(  !Utils.isEmpty( beanA.getBasicRiskcode() )  ){
 			beanB.getId().setPrmBasicRskCode( beanA.getBasicRiskcode() ); 
 		}

 		/* Mapping: "classCode" -> "prmClCode" */
		if(  !Utils.isEmpty( beanA.getClassCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPrmClCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getClassCode() ) ) );
  		}

 		/* Mapping: "riskCategory" -> "prmRcCode" */
		if(  !Utils.isEmpty( beanA.getRiskCategory() )  ){
 			beanB.setPrmRcCode( beanA.getRiskCategory() ); 
 		}

 		/* Mapping: "riskSubCategory" -> "prmRscCode" */
		if(  !Utils.isEmpty( beanA.getRiskSubCategory() )  ){
 			beanB.setPrmRscCode( beanA.getRiskSubCategory() ); 
 		}

 		/* Mapping: "riskCode" -> "id.prmRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCode() )  ){
 			beanB.getId().setPrmRskCode( beanA.getRiskCode() ); 
 		}

 		/* Mapping: "riskType" -> "prmRtCode" */
		if(  !Utils.isEmpty( beanA.getRiskType() )  ){
 			beanB.setPrmRtCode( beanA.getRiskType() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPremiumQuo initializeDeepVO( com.rsaame.pas.vo.bus.WCVO beanA, com.rsaame.pas.dao.model.TTrnPremiumQuo beanB ){
      		BeanUtils.initializeBeanField( "id", beanB );
            		return beanB;
	}
}
