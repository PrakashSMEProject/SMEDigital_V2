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
 * <li>com.rsaame.pas.vo.bus.GeneralInfoVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyQuoToGenVOMap.class )</code>.
 */
public class PolicyQuoToGenVOMap extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPolicyQuo, com.rsaame.pas.vo.bus.GeneralInfoVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyQuoToGenVOMap(){
		super();
	}

	public PolicyQuoToGenVOMap( com.rsaame.pas.dao.model.TTrnPolicyQuo src, com.rsaame.pas.vo.bus.GeneralInfoVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.GeneralInfoVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.GeneralInfoVO) Utils.newInstance( "com.rsaame.pas.vo.bus.GeneralInfoVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GeneralInfoVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "polReasonCode" -> "additionalInfo.specialityType" */
		if(  !Utils.isEmpty( beanA.getPolReasonCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getAdditionalInfo().setSpecialityType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolReasonCode() ) ) );
  		}

 		/* Mapping: "polCoverNoteNo" -> "additionalInfo.rsaSpacialIndicator" */
		if(  !Utils.isEmpty( beanA.getPolCoverNoteNo() )  ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getAdditionalInfo().setRsaSpacialIndicator( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolCoverNoteNo() ) ) );
  		}

 		/* Mapping: "polDistributionChnl" -> "sourceOfBus.distributionChannel" */
		if(  !Utils.isEmpty( beanA.getPolDistributionChnl() )  ){
 			beanB.getSourceOfBus().setDistributionChannel( beanA.getPolDistributionChnl() ); 
 		}

 		/* Mapping: "polSourceOfBusiness" -> "claimsHistory.sourceOfBusiness" */
		if(  !Utils.isEmpty( beanA.getPolSourceOfBusiness() )  ){
			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.getClaimsHistory().setSourceOfBusiness( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolSourceOfBusiness() ) ) );
  		}

 		/* Mapping: "polLinkingId" -> "additionalInfo.policyId" */
		if(  !Utils.isEmpty( beanA.getPolLinkingId() )  ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getAdditionalInfo().setPolicyId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolLinkingId() ) ) );
  		}
		/*VAT*/
		/* Mapping: "polDistributionChnl" -> "sourceOfBus.distributionChannel" */
		if(  !Utils.isEmpty( beanA.getPolvatCode() )  ){
 			beanB.setVatCode(( beanA.getPolDistributionChnl() )); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.GeneralInfoVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnPolicyQuo beanA, com.rsaame.pas.vo.bus.GeneralInfoVO beanB ){
  		BeanUtils.initializeBeanField( "additionalInfo", beanB );
     		BeanUtils.initializeBeanField( "sourceOfBus", beanB );
   		BeanUtils.initializeBeanField( "claimsHistory", beanB );
    		return beanB;
	}
}
