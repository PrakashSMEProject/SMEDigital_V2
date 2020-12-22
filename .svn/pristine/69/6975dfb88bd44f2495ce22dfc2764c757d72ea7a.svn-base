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
 * <li>com.rsaame.pas.vo.bus.GeneralInfoVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyQuoToGenVOMapReverse.class )</code>.
 */
public class PolicyQuoToGenVOMapReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.GeneralInfoVO, com.rsaame.pas.dao.model.TTrnPolicyQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyQuoToGenVOMapReverse(){
		super();
	}

	public PolicyQuoToGenVOMapReverse( com.rsaame.pas.vo.bus.GeneralInfoVO src, com.rsaame.pas.dao.model.TTrnPolicyQuo dest ){
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
		com.rsaame.pas.vo.bus.GeneralInfoVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "additionalInfo.specialityType" -> "polReasonCode" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getSpecialityType() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolReasonCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getAdditionalInfo().getSpecialityType() ) ) );
  		}

 		/* Mapping: "additionalInfo.rsaSpacialIndicator" -> "polCoverNoteNo" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getRsaSpacialIndicator() )  ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setPolCoverNoteNo( converter.getTypeOfB().cast( converter.getBFromA( beanA.getAdditionalInfo().getRsaSpacialIndicator() ) ) );
  		}

 		/* Mapping: "sourceOfBus.distributionChannel" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getSourceOfBus() ) && !Utils.isEmpty( beanA.getSourceOfBus().getDistributionChannel() )  ){
 			beanB.setPolDistributionChnl( beanA.getSourceOfBus().getDistributionChannel() ); 
 		}

 		/* Mapping: "claimsHistory.sourceOfBusiness" -> "polSourceOfBusiness" */
		if(  !Utils.isEmpty( beanA.getClaimsHistory() ) && !Utils.isEmpty( beanA.getClaimsHistory().getSourceOfBusiness() )  ){
			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setPolSourceOfBusiness( converter.getTypeOfB().cast( converter.getBFromA( beanA.getClaimsHistory().getSourceOfBusiness() ) ) );
  		}

 		/* Mapping: "additionalInfo.policyId" -> "polLinkingId" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getPolicyId() )  ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setPolLinkingId( converter.getTypeOfB().cast( converter.getBFromA( beanA.getAdditionalInfo().getPolicyId() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPolicyQuo initializeDeepVO( com.rsaame.pas.vo.bus.GeneralInfoVO beanA, com.rsaame.pas.dao.model.TTrnPolicyQuo beanB ){
           		return beanB;
	}
}
