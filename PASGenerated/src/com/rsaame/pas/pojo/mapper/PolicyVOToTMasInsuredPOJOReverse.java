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
 * <li>com.rsaame.pas.gen.domain.TMasInsured</li>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyVOToTMasInsuredPOJOReverse.class )</code>.
 */
public class PolicyVOToTMasInsuredPOJOReverse extends BaseBeanToBeanMapper<com.rsaame.pas.gen.domain.TMasInsured, com.rsaame.pas.vo.bus.PolicyVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyVOToTMasInsuredPOJOReverse(){
		super();
	}

	public PolicyVOToTMasInsuredPOJOReverse( com.rsaame.pas.gen.domain.TMasInsured src, com.rsaame.pas.vo.bus.PolicyVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PolicyVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PolicyVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PolicyVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.gen.domain.TMasInsured beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PolicyVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

		/* Mapping: "insInsuredCode" -> "generalInfo.insured.insuredId" */
		if(  !Utils.isEmpty( beanA.getInsCustomerId() )  ){
 			beanB.setPolCustomerId(beanA.getInsCustomerId()); 
 		}
		
		/* Mapping: "insInsuredCode" -> "generalInfo.insured.insuredId" */
		if(  !Utils.isEmpty( beanA.getInsInsuredCode() )  ){
 			beanB.getGeneralInfo().getInsured().setInsuredId( beanA.getInsInsuredCode() ); 
 		}

 		/* Mapping: "insEFirstName" -> "generalInfo.insured.name" */
		if(  !Utils.isEmpty( beanA.getInsEFirstName() )  ){
 			beanB.getGeneralInfo().getInsured().setName( beanA.getInsEFirstName() ); 
 		}

 		/* Mapping: "insEZipCode" -> "generalInfo.insured.address.poBox" */
		if(  !Utils.isEmpty( beanA.getInsEZipCode() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( beanA.getInsEZipCode() ); 
 		}

 		/* Mapping: "insBrCode" -> "generalInfo.sourceOfBus.brokerName" */
		if(  !Utils.isEmpty( beanA.getInsBrCode() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( beanA.getInsBrCode() ); 
 		}

 		/* Mapping: "insAgentCode" -> "generalInfo.sourceOfBus.directSubAgent" */
		if(  !Utils.isEmpty( beanA.getInsAgentCode() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( beanA.getInsAgentCode() ); 
 		}

 		/* Mapping: "insDistributionChnl" -> "generalInfo.sourceOfBus.distributionChannel" */
		if(  !Utils.isEmpty( beanA.getInsDistributionChnl() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setDistributionChannel( beanA.getInsDistributionChnl() ); 
 		}

 		/* Mapping: "insRemarks" -> "generalInfo.additionalInfo.remarks" */
		if(  !Utils.isEmpty( beanA.getInsRemarks() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( beanA.getInsRemarks() ); 
 		}

 		/* Mapping: "insAFirstName" -> "generalInfo.insured.arabicName" */
		if(  !Utils.isEmpty( beanA.getInsAFirstName() )  ){
 			beanB.getGeneralInfo().getInsured().setArabicName( beanA.getInsAFirstName() ); 
 		}

 		/* Mapping: "insEPhoneNo" -> "generalInfo.insured.phoneNo" */
		if(  !Utils.isEmpty( beanA.getInsEPhoneNo() )  ){
 			beanB.getGeneralInfo().getInsured().setPhoneNo( beanA.getInsEPhoneNo() ); 
 		}

 		/* Mapping: "insBusiness" -> "generalInfo.insured.busDescription" */
		if(  !Utils.isEmpty( beanA.getInsBusiness() )  ){
 			beanB.getGeneralInfo().getInsured().setBusDescription( beanA.getInsBusiness() ); 
 		}

 		/* Mapping: "insEMobileNo" -> "generalInfo.insured.mobileNo" */
		if(  !Utils.isEmpty( beanA.getInsEMobileNo() )  ){
 			beanB.getGeneralInfo().getInsured().setMobileNo( beanA.getInsEMobileNo() ); 
 		}
		
		/* Mapping: "insTurnover" -> "generalInfo.insured.turnover" */
		if(  !Utils.isEmpty( beanA.getInsTurnover() )  ){
 			beanB.getGeneralInfo().getInsured().setTurnover( beanA.getInsTurnover() ); 
 		}
		
		/* Mapping: "insNoOfEmp" -> "generalInfo.insured.noOfEmp" */
		if(  !Utils.isEmpty( beanA.getInsNoOfEmp() )  ){
 			beanB.getGeneralInfo().getInsured().setNoOfEmployees( beanA.getInsNoOfEmp() ); 
 		}

 		/* Mapping: "insEAddress" -> "generalInfo.insured.address.address" */
		if(  !Utils.isEmpty( beanA.getInsEAddress() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setAddress( beanA.getInsEAddress() ); 
 		}

 		/* Mapping: "insCtyCode" -> "generalInfo.insured.emailId" */
		if(  !Utils.isEmpty( beanA.getInsCtyCode() )  ){
 			beanB.getGeneralInfo().getInsured().setEmailId( beanA.getInsEEmailId() ); 
 		}

 		/* Mapping: "insCtyCode" -> "generalInfo.insured.city" */
		if(  !Utils.isEmpty( beanA.getInsCtyCode() )  ){
 			beanB.getGeneralInfo().getInsured().setCity( beanA.getInsCtyCode() ); 
 		}

 		/* Mapping: "insCountry" -> "generalInfo.insured.address.country" */
		if(  !Utils.isEmpty( beanA.getInsCountry() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setCountry( Integer.valueOf( beanA.getInsCountry() ) ); 
 		}

 		/* Mapping: "insAffinityRefNo" -> "generalInfo.additionalInfo.affinityRefNo" */
		if(  !Utils.isEmpty( beanA.getInsAffinityRefNo() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo( beanA.getInsAffinityRefNo() ); 
 		}

 		/* Mapping: "insRemarks" -> "generalInfo.additionalInfo.remarks" */
		if(  !Utils.isEmpty( beanA.getInsRemarks() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( beanA.getInsRemarks() ); 
 		}

 		/* Mapping: "insDtCollectionKyc" -> "generalInfo.additionalInfo.dateOfcollectionOfKYC" */
		if(  !Utils.isEmpty( beanA.getInsDtCollectionKyc() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setDateOfcollectionOfKYC( beanA.getInsDtCollectionKyc() ); 
 		}

 		/* Mapping: "insExpiryDate" -> "generalInfo.additionalInfo.tradeLicenseExpDate" */
		if(  !Utils.isEmpty( beanA.getInsExpiryDate() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setTradeLicenseExpDate( beanA.getInsExpiryDate() ); 
 		}

 		/* Mapping: "insStatus" -> "generalInfo.additionalInfo." */
		if(  !Utils.isEmpty( beanA.getInsStatus() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setInsuredStatus ( beanA.getInsStatus() ); 
 		}

		/* Mapping: 142244"vatRegNo" -> "insured.vatRegNo" */
	       if(  !Utils.isEmpty( beanA.getInsVatRegNo())) {
	            beanB.setPolVatRegNo(beanA.getInsVatRegNo());
	        }

		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyVO initializeDeepVO( com.rsaame.pas.gen.domain.TMasInsured beanA, com.rsaame.pas.vo.bus.PolicyVO beanB ){
  		BeanUtils.initializeBeanField( "generalInfo.insured", beanB );
     		BeanUtils.initializeBeanField( "generalInfo.insured.address", beanB );
   		BeanUtils.initializeBeanField( "generalInfo.sourceOfBus", beanB );
       		BeanUtils.initializeBeanField( "generalInfo.additionalInfo", beanB );
                              		return beanB;
	}
}
