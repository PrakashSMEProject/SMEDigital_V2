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
 * <li>com.rsaame.pas.gen.domain.TMasInsured</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasInsuredQuoToGenVOMapperReverse.class )</code>.
 */
public class TMasInsuredQuoToGenVOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.GeneralInfoVO, com.rsaame.pas.gen.domain.TMasInsured>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasInsuredQuoToGenVOMapperReverse(){
		super();
	}

	public TMasInsuredQuoToGenVOMapperReverse( com.rsaame.pas.vo.bus.GeneralInfoVO src, com.rsaame.pas.gen.domain.TMasInsured dest ){
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
		com.rsaame.pas.vo.bus.GeneralInfoVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.gen.domain.TMasInsured beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "insured.insuredId" -> "insInsuredCode" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getInsuredId() )  ){
 			beanB.setInsInsuredCode( beanA.getInsured().getInsuredId() ); 
 		}

 		/* Mapping: "insured.address.poBox" -> "insEZipCode" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getAddress() ) && !Utils.isEmpty( beanA.getInsured().getAddress().getPoBox() )  ){
 			beanB.setInsEZipCode( beanA.getInsured().getAddress().getPoBox() ); 
 		}
		
		/* Mapping: "insured.address.poBox" -> "insEZipCode" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getEmailId() )  ){
 			beanB.setInsEEmailId(  beanA.getInsured().getEmailId() ); 
 		}

 		/* Mapping: "sourceOfBus.brokerName" -> "insBrCode" */
		if(  !Utils.isEmpty( beanA.getSourceOfBus() ) && !Utils.isEmpty( beanA.getSourceOfBus().getBrokerName() )  ){
 			beanB.setInsBrCode( beanA.getSourceOfBus().getBrokerName() ); 
 		}

 		/* Mapping: "sourceOfBus.directSubAgent" -> "insAgentCode" */
		if(  !Utils.isEmpty( beanA.getSourceOfBus() ) && !Utils.isEmpty( beanA.getSourceOfBus().getDirectSubAgent() )  ){
 			beanB.setInsAgentCode( beanA.getSourceOfBus().getDirectSubAgent() ); 
 		}

 		/* Mapping: "sourceOfBus.distributionChannel" -> "insDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getSourceOfBus() ) && !Utils.isEmpty( beanA.getSourceOfBus().getDistributionChannel() )  ){
 			beanB.setInsDistributionChnl( beanA.getSourceOfBus().getDistributionChannel() ); 
 		}

 		/* Mapping: "additionalInfo.dateOfcollectionOfKYC" -> "insDtCollectionKyc" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getDateOfcollectionOfKYC() )  ){
 			beanB.setInsDtCollectionKyc( beanA.getAdditionalInfo().getDateOfcollectionOfKYC() ); 
 		}

 		/* Mapping: "additionalInfo.tradeLicenseExpDate" -> "insExpiryDate" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getTradeLicenseExpDate() )  ){
 			beanB.setInsExpiryDate( beanA.getAdditionalInfo().getTradeLicenseExpDate() ); 
 		}

 		/* Mapping: "additionalInfo.insuredStatus" -> "insStatus" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getInsuredStatus() )  ){
 			beanB.setInsStatus( beanA.getAdditionalInfo().getInsuredStatus() ); 
 		}

 		/* Mapping: "additionalInfo.remarks" -> "insRemarks" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getRemarks() )  ){
 			beanB.setInsRemarks( beanA.getAdditionalInfo().getRemarks() ); 
 		}

		/* Mapping: "insured.turnover" -> "insTurnover" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getTurnover() )  ){
 			beanB.setInsTurnover( beanA.getInsured().getTurnover() ); 
 		}
		
		/* Mapping: "insured.noOfEmloyees" -> "insNoOfEmp" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getNoOfEmployees() )  ){
 			beanB.setInsNoOfEmp( beanA.getInsured().getNoOfEmployees() ); 
 		}
		

		/*VAT*/
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getVatRegNo() )  ){
 			beanB.setInsVatRegNo( beanA.getInsured().getVatRegNo() ); 
 		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.gen.domain.TMasInsured initializeDeepVO( com.rsaame.pas.vo.bus.GeneralInfoVO beanA, com.rsaame.pas.gen.domain.TMasInsured beanB ){
                   		return beanB;
	}
}
