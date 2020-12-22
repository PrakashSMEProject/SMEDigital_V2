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
 * <li>com.rsaame.pas.vo.bus.WCVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WCVOtoTTrnPremiumQuoMapperReverse.class )</code>.
 */
public class WCVOtoTTrnPremiumQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPremiumQuo, com.rsaame.pas.vo.bus.WCVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public WCVOtoTTrnPremiumQuoMapperReverse(){
		super();
	}

	public WCVOtoTTrnPremiumQuoMapperReverse( com.rsaame.pas.dao.model.TTrnPremiumQuo src, com.rsaame.pas.vo.bus.WCVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.WCVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.WCVO) Utils.newInstance( "com.rsaame.pas.vo.bus.WCVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPremiumQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.WCVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "prmCompulsoryExcess" -> "deductibles" */
		if(  !Utils.isEmpty( beanA.getPrmCompulsoryExcess() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setDeductibles( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmCompulsoryExcess() ) ) );
  		}

 		/* Mapping: "prmSumInsured" -> "wageroll" */
		if(  !Utils.isEmpty( beanA.getPrmSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setWageroll( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmSumInsured() ) ) );
  		}

 		/* Mapping: "id.prmBasicRskCode" -> "basicRiskcode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrmBasicRskCode() )  ){
 			beanB.setBasicRiskcode( beanA.getId().getPrmBasicRskCode() ); 
 		}

 		/* Mapping: "prmClCode" -> "classCode" */
		if(  !Utils.isEmpty( beanA.getPrmClCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setClassCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmClCode() ) ) );
  		}

 		/* Mapping: "prmRcCode" -> "riskCategory" */
		if(  !Utils.isEmpty( beanA.getPrmRcCode() )  ){
 			beanB.setRiskCategory( beanA.getPrmRcCode() ); 
 		}

 		/* Mapping: "prmRscCode" -> "riskSubCategory" */
		if(  !Utils.isEmpty( beanA.getPrmRscCode() )  ){
 			beanB.setRiskSubCategory( beanA.getPrmRscCode() ); 
 		}

 		/* Mapping: "id.prmRskCode" -> "riskCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrmRskCode() )  ){
 			beanB.setRiskCode( beanA.getId().getPrmRskCode() ); 
 		}

 		/* Mapping: "prmRtCode" -> "riskType" */
		if(  !Utils.isEmpty( beanA.getPrmRtCode() )  ){
 			beanB.setRiskType( beanA.getPrmRtCode() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.WCVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnPremiumQuo beanA, com.rsaame.pas.vo.bus.WCVO beanB ){
                 		return beanB;
	}
}
