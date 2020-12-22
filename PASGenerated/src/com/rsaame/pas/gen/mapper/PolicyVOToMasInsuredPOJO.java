       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.gen.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * <li>com.rsaame.pas.gen.domain.TMasInsured</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyVOToMasInsuredPOJO.class )</code>.
 */
public class PolicyVOToMasInsuredPOJO extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.gen.domain.TMasInsured>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyVOToMasInsuredPOJO(){
		super();
	}

	public PolicyVOToMasInsuredPOJO( com.rsaame.pas.vo.bus.PolicyVO src, com.rsaame.pas.gen.domain.TMasInsured dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.gen.domain.TMasInsured mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.gen.domain.TMasInsured) Utils.newInstance( "com.rsaame.pas.gen.domain.TMasInsured" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PolicyVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.gen.domain.TMasInsured beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "generalInfo.insured.insuredId" -> "insInsuredCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() )  ){
			
			if(beanA.getGeneralInfo().getInsured().getInsuredId() != null){
				beanB.setInsInsuredCode( beanA.getGeneralInfo().getInsured().getInsuredId() );
			}
 		}	

 		/* Mapping: "generalInfo.insured.name" -> "insEFirstName" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() )  ){
 			beanB.setInsEFirstName( beanA.getGeneralInfo().getInsured().getName() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.poBox" -> "insEZipCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() )  ){
 			beanB.setInsEZipCode( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.brokerName" -> "insBrCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() )  ){
 			
			if(beanA.getGeneralInfo().getSourceOfBus().getBrokerName() != null){
				beanB.setInsBrCode( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() );
			}
 		}

 		/* Mapping: "generalInfo.sourceOfBus.directSubAgent" -> "insAgentCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() )  ){
			if(beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() != null) {
				beanB.setInsAgentCode( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() );
			}
 		}

 		/* Mapping: "generalInfo.sourceOfBus.distributionChannel" -> "insDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() )  ){
 			if(beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() != null){
				beanB.setInsDistributionChnl( beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() );
 			}
		}
		
 		/* Mapping: "generalInfo.additionalInfo.remarks" -> "insRemarks" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() )  ){
 			beanB.setInsRemarks( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() ); 
 		}
		
		/* Mapping: 142244"vatRegNo" -> "insured.vatRegNo" */
		if(  !Utils.isEmpty( beanA.getPolVatRegNo() )  ){
 			beanB.setInsVatRegNo( ( beanA.getPolVatRegNo() ) ) ; 
 		}
	
		
		beanB.setInsCcgCode(Short.valueOf("11"));
		
		return dest;
	}	  

	/**
	 * This method initializes the deepVOs inside the destination bean.
	 * 
	 * @param beanB
	 * @return beanB
	 */
	private static com.rsaame.pas.gen.domain.TMasInsured initializeDeepVO( com.rsaame.pas.vo.bus.PolicyVO beanA, com.rsaame.pas.gen.domain.TMasInsured beanB ){
             		return beanB;
	}
}
