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
 * <li>com.rsaame.pas.vo.bus.SchemeVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyQuoToSchemVOMapReverse.class )</code>.
 */
public class PolicyQuoToSchemVOMapReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.SchemeVO, com.rsaame.pas.dao.model.TTrnPolicyQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyQuoToSchemVOMapReverse(){
		super();
	}

	public PolicyQuoToSchemVOMapReverse( com.rsaame.pas.vo.bus.SchemeVO src, com.rsaame.pas.dao.model.TTrnPolicyQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPolicyQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPolicyQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPolicyQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.SchemeVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "schemeCode" -> "polCoverNoteHour" */
		if(  !Utils.isEmpty( beanA.getSchemeCode() )  ){
 			beanB.setPolCoverNoteHour( beanA.getSchemeCode() ); 
 		}

 		/* Mapping: "tariffCode" -> "polTarCode" */
		if(  !Utils.isEmpty( beanA.getTariffCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolTarCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getTariffCode() ) ) );
  		}

 		/* Mapping: "policyType" -> "polPolicyType" */
		if(  !Utils.isEmpty( beanA.getPolicyType() )  ){
 			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setPolPolicyType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolicyType() ) ) );
  		}

 		/* Mapping: "policyCode" -> "polPolicyType" */
		if(  !Utils.isEmpty( beanA.getPolicyCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolPolicyType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolicyCode() ) ) );
  		}

 		/* Mapping: "effDate" -> "polEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getEffDate() )  ){
 			beanB.setPolEffectiveDate( beanA.getEffDate() ); 
 		}

 		/* Mapping: "expiryDate" -> "polExpiryDate" */
		if(  !Utils.isEmpty( beanA.getExpiryDate() )  ){
 			beanB.setPolExpiryDate( beanA.getExpiryDate() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPolicyQuo initializeDeepVO( com.rsaame.pas.vo.bus.SchemeVO beanA, com.rsaame.pas.dao.model.TTrnPolicyQuo beanB ){
             		return beanB;
	}
}
