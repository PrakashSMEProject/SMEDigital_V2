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
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * <li>com.rsaame.pas.gen.domain.TMasInsured</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyVOToTMasInsuredPOJO.class )</code>.
 */
public class PolicyVOToTMasInsuredPOJO extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.gen.domain.TMasInsured>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyVOToTMasInsuredPOJO(){
		super();
	}

	public PolicyVOToTMasInsuredPOJO( com.rsaame.pas.vo.bus.PolicyVO src, com.rsaame.pas.gen.domain.TMasInsured dest ){
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
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "generalInfo.insured.insuredId" -> "insInsuredCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getInsuredId() )  ){
 			beanB.setInsInsuredCode( beanA.getGeneralInfo().getInsured().getInsuredId() ); 
 		}

 		/* Mapping: "generalInfo.insured.name" -> "insEFirstName" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() )  ){
 			beanB.setInsEFirstName( beanA.getGeneralInfo().getInsured().getName() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.poBox" -> "insEZipCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() )  ){
 			beanB.setInsEZipCode( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.brokerName" -> "insBrCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() )  ){
 			beanB.setInsBrCode( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.directSubAgent" -> "insAgentCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() )  ){
 			beanB.setInsAgentCode( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.distributionChannel" -> "insDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() )  ){
 			beanB.setInsDistributionChnl( beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.remarks" -> "insRemarks" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() )  ){
 			beanB.setInsRemarks( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() ); 
 		}

 		/* Mapping: "generalInfo.insured.arabicName" -> "insAFirstName" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getArabicName() )  ){
 			beanB.setInsAFirstName( beanA.getGeneralInfo().getInsured().getArabicName() ); 
 		}

 		/* Mapping: "generalInfo.insured.phoneNo" -> "insEPhoneNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getPhoneNo() )  ){
 			beanB.setInsEPhoneNo( beanA.getGeneralInfo().getInsured().getPhoneNo() ); 
 		}else{
 			beanB.setInsEPhoneNo( null ); 
 		}

 		/* Mapping: "generalInfo.insured.busDescription" -> "insBusiness" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusDescription() )  ){
 			beanB.setInsBusiness( beanA.getGeneralInfo().getInsured().getBusDescription() ); 
 		}

 		/* Mapping: "generalInfo.insured.mobileNo" -> "insEMobileNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getMobileNo() )  ){
 			beanB.setInsEMobileNo( beanA.getGeneralInfo().getInsured().getMobileNo() ); 
 		}else{
 			beanB.setInsEMobileNo( null ); 
 		}

		/* Mapping: "generalInfo.insured.turnover" -> "insTurnover" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTurnover() )  ){
 			beanB.setInsTurnover( beanA.getGeneralInfo().getInsured().getTurnover() ); 
 		}else{
 			beanB.setInsTurnover( null );
 		}
		
		/* Mapping: "generalInfo.insured.noOfEmp" -> "insNoOfEmp" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNoOfEmployees() )  ){
 			beanB.setInsNoOfEmp( beanA.getGeneralInfo().getInsured().getNoOfEmployees() ); 
 		}else{
 			beanB.setInsNoOfEmp( null ); 
 		}
		
 		/* Mapping: "generalInfo.insured.address.address" -> "insEAddress" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getAddress() )  ){
 			beanB.setInsEAddress( beanA.getGeneralInfo().getInsured().getAddress().getAddress() ); 
 		}

 		/* Mapping: "generalInfo.insured.emailId" -> "insCtyCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getEmailId() )  ){
 			beanB.setInsEEmailId( beanA.getGeneralInfo().getInsured().getEmailId() ); 
 		}

 		/* Mapping: "generalInfo.insured.city" -> "insCtyCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getCity() )  ){
 			beanB.setInsCtyCode( beanA.getGeneralInfo().getInsured().getCity() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.country" -> "insCountry" */
		//todo: Hard coded map it with converter
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getCountry() )  ){
 			beanB.setInsCountry( beanA.getGeneralInfo().getInsured().getAddress().getCountry().shortValue() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.affinityRefNo" -> "insAffinityRefNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() )  ){
 			beanB.setInsAffinityRefNo( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.remarks" -> "insRemarks" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() )  ){
 			beanB.setInsRemarks( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.dateOfcollectionOfKYC" -> "insDtCollectionKyc" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfcollectionOfKYC() )  ){
 			beanB.setInsDtCollectionKyc( beanA.getGeneralInfo().getAdditionalInfo().getDateOfcollectionOfKYC() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.tradeLicenseExpDate" -> "insExpiryDate" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getTradeLicenseExpDate() )  ){
 			beanB.setInsExpiryDate( beanA.getGeneralInfo().getAdditionalInfo().getTradeLicenseExpDate() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo." -> "insStatus" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() )  ){
 			beanB.setInsStatus( beanA.getGeneralInfo().getAdditionalInfo().getInsuredStatus() ); 
 		}
		
		
		/* Mapping: 142244"vatRegNo" -> "insured.vatRegNo" */
       if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getVatRegNo() )  ){
            beanB.setInsVatRegNo( beanA.getGeneralInfo().getInsured().getVatRegNo() ); 
        }

		
		

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.gen.domain.TMasInsured initializeDeepVO( com.rsaame.pas.vo.bus.PolicyVO beanA, com.rsaame.pas.gen.domain.TMasInsured beanB ){
                                           		return beanB;
	}
}
