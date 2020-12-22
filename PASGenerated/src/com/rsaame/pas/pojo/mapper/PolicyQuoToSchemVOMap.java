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
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * <li>com.rsaame.pas.vo.bus.SchemeVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyQuoToSchemVOMap.class )</code>.
 */
public class PolicyQuoToSchemVOMap extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPolicyQuo, com.rsaame.pas.vo.bus.SchemeVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyQuoToSchemVOMap(){
		super();
	}

	public PolicyQuoToSchemVOMap( com.rsaame.pas.dao.model.TTrnPolicyQuo src, com.rsaame.pas.vo.bus.SchemeVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.SchemeVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.SchemeVO) Utils.newInstance( "com.rsaame.pas.vo.bus.SchemeVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.SchemeVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "polCoverNoteHour" -> "schemeCode" */
		if(  !Utils.isEmpty( beanA.getPolCoverNoteHour() )  ){
 			beanB.setSchemeCode( beanA.getPolCoverNoteHour() ); 
 		}

 		/* Mapping: "polTarCode" -> "tariffCode" */
		if(  !Utils.isEmpty( beanA.getPolTarCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setTariffCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolTarCode() ) ) );
  		}

 		/* Mapping: "polPolicyType" -> "policyType" */
		if(  !Utils.isEmpty( beanA.getPolPolicyType() )  ){
 			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setPolicyType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolPolicyType() ) ) );
  		}

 		/* Mapping: "polPolicyType" -> "policyCode" */
		if(  !Utils.isEmpty( beanA.getPolPolicyType() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolicyCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolPolicyType() ) ) );
  		}

 		/* Mapping: "polEffectiveDate" -> "effDate" */
		if(  !Utils.isEmpty( beanA.getPolEffectiveDate() )  ){
 			beanB.setEffDate( beanA.getPolEffectiveDate() ); 
 		}

 		/* Mapping: "polExpiryDate" -> "expiryDate" */
		if(  !Utils.isEmpty( beanA.getPolExpiryDate() )  ){
 			beanB.setExpiryDate( beanA.getPolExpiryDate() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.SchemeVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnPolicyQuo beanA, com.rsaame.pas.vo.bus.SchemeVO beanB ){
             		return beanB;
	}
}
