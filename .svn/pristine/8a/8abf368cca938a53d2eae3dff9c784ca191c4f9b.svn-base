       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.PolicyDataVO</li>
 * <li>com.rsaame.pas.gen.domain.TMasInsured</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyDataVOToMasInsuredPOJO.class )</code>.
 */
public class PolicyDataVOToMasInsuredPOJO extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.gen.domain.TMasInsured>{
	private final Logger LOGGER = Logger.getLogger( this.getClass() );	

	public PolicyDataVOToMasInsuredPOJO(){
		super();
	}

	public PolicyDataVOToMasInsuredPOJO( com.rsaame.pas.vo.bus.PolicyDataVO src, com.rsaame.pas.gen.domain.TMasInsured dest ){
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
		
		LOGGER.info( "Policy Data VO to Mas Insured Mapping Starts" );
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.gen.domain.TMasInsured) Utils.newInstance( "com.rsaame.pas.gen.domain.TMasInsured" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PolicyDataVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.gen.domain.TMasInsured beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		
		/* Mapping: "generalInfo.insured.insuredId" -> "insInsuredCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getInsuredCode() )  ){
 			beanB.setInsInsuredCode( beanA.getGeneralInfo().getInsured().getInsuredCode() ); 
 		}
		
		/* Mapping: "generalInfo.insured.firstName and generalInfo.insured.lastname" -> "insEFullName" */
		String fullName = null;
		
		if(!Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() ) ){
			fullName = beanA.getGeneralInfo().getInsured().getName();
		}
		if (!Utils.isEmpty(beanA.getGeneralInfo().getInsured().getFirstName()) && 
				!Utils.isEmpty(beanA.getGeneralInfo().getInsured().getLastName())) {
			fullName = beanA.getGeneralInfo().getInsured().getFirstName() + " "
					+ beanA.getGeneralInfo().getInsured().getLastName();
		} else if(!Utils.isEmpty(beanA.getGeneralInfo().getInsured().getFirstName())){
			fullName = beanA.getGeneralInfo().getInsured().getFirstName();
		}
		
	
		if(  !Utils.isEmpty( fullName )){
 			beanB.setInsEFullName( fullName ); 
 		}
		/* Mapping: "generalInfo.insured.name" -> "insEFullName" */
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
 		}else{
 			beanB.setInsBrCode( null );
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

 		/* Mapping: "generalInfo.insured.phoneNo" -> "insEPhoneNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getPhoneNo() )  ){
 			beanB.setInsEPhoneNo( beanA.getGeneralInfo().getInsured().getPhoneNo() ); 
 		}

 		/* Mapping: "generalInfo.insured.busDescription" -> "insBusiness" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusDescription() )  ){
 			beanB.setInsBusiness( beanA.getGeneralInfo().getInsured().getBusDescription() ); 
 		}

 		/* Mapping: "generalInfo.insured.firstName" -> "insEFirstName" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getFirstName() )  ){
 			beanB.setInsEFirstName( beanA.getGeneralInfo().getInsured().getFirstName() ); 
 		}

 		/* Mapping: "generalInfo.insured.lastName" -> "insELastName" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getLastName() )  ){
 			beanB.setInsELastName( beanA.getGeneralInfo().getInsured().getLastName() ); 
 		}

 		/* Mapping: "generalInfo.insured.mobileNo" -> "insEMobileNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getMobileNo() )  ){
 			beanB.setInsEMobileNo( beanA.getGeneralInfo().getInsured().getMobileNo() ); 
 		}

 		/* Mapping: "generalInfo.insured.turnover" -> "insTurnover" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTurnover() )  ){
 			beanB.setInsTurnover( beanA.getGeneralInfo().getInsured().getTurnover() ); 
 		}

 		/* Mapping: "generalInfo.insured.noOfEmployees" -> "insNoOfEmp" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNoOfEmployees() )  ){
 			beanB.setInsNoOfEmp( beanA.getGeneralInfo().getInsured().getNoOfEmployees() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.address" -> "insEAddress" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getAddress() )  ){
 			beanB.setInsEAddress( beanA.getGeneralInfo().getInsured().getAddress().getAddress() ); 
 		}

 		/* Mapping: "generalInfo.insured.emailId" -> "insEEmailId" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getEmailId() )  ){
 			beanB.setInsEEmailId( beanA.getGeneralInfo().getInsured().getEmailId() ); 
 		}

 		/* Mapping: "generalInfo.insured.city" -> "insCity" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getCity() )  ){
 			beanB.setInsCity( beanA.getGeneralInfo().getInsured().getCity() ); 
 		}
		String.valueOf( beanA.getGeneralInfo().getInsured().getNationality() );
		/* Mapping: "generalInfo.insured.nationality" -> "INS_NATIONALITY" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNationality())  ){
 			beanB.setInsNationality(Short.valueOf( String.valueOf( beanA.getGeneralInfo().getInsured().getNationality()) )); 
 		}
		
		

 		/* Mapping: "generalInfo.insured.city" -> "insCtyCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getCity() )  ){
 			beanB.setInsCtyCode( beanA.getGeneralInfo().getInsured().getCity() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.country" -> "insCountry" */
		//todo: hard coded converter. Use from mapper
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getCountry() )  ){
 			beanB.setInsCountry( beanA.getGeneralInfo().getInsured().getAddress().getCountry().shortValue() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.affinityRefNo" -> "insAffinityRefNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() )  ){
 			beanB.setInsAffinityRefNo( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.dateOfcollectionOfKYC" -> "insDtCollectionKyc" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfcollectionOfKYC() )  ){
 			beanB.setInsDtCollectionKyc( beanA.getGeneralInfo().getAdditionalInfo().getDateOfcollectionOfKYC() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.tradeLicenseExpDate" -> "insExpiryDate" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getTradeLicenseExpDate() )  ){
 			beanB.setInsExpiryDate( beanA.getGeneralInfo().getAdditionalInfo().getTradeLicenseExpDate() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.insuredStatus" -> "insStatus" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getInsuredStatus() )  ){
 			beanB.setInsStatus( beanA.getGeneralInfo().getAdditionalInfo().getInsuredStatus() ); 
 		}

 		/* Mapping: "generalInfo.insured.tradeLicenseNo" -> "insECoRegnNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() )  ){
 			beanB.setInsECoRegnNo( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.dateOfEst" -> "insDtEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() )  ){
 			beanB.setInsDtEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.placeOfEst" -> "insPlaceEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() )  ){
 			beanB.setInsPlaceEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.faxNumber" -> "insFaxNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() )  ){
 			beanB.setInsFaxNo( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.regulatoryBody" -> "insRegulatoryBody" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRegulatoryBody() )  ){
 			beanB.setInsRegulatoryBody( beanA.getGeneralInfo().getAdditionalInfo().getRegulatoryBody() ); 
 		}

 		/* Mapping: "generalInfo.jurisdiction" -> "insRegCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getJurisdiction() )  ){
			 com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				//commented to fix SONAR  blocker issue - 27-Oct-2017
				//beanB.setInsRegCode( converter.getTypeOfA().cast( beanA.getGeneralInfo().getJurisdiction() ) );

				//Added to fix SONAR blocker issue - 27-Oct-2017
				beanB.setInsRegCode( converter.getAFromB(beanA.getGeneralInfo().getJurisdiction() ) );

			
 		}
		
 		/* Mapping: "generalInfo.jurisdiction" -> "insRegCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getIntAccExecCode())  ){
			//com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setInsIntAccExecCode(  beanA.getGeneralInfo().getIntAccExecCode() );
 		}
		
		/* Mapping: "generalInfo.insured.VatRegNo" -> "vatRegNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getVatRegNo() )  ){
 			beanB.setInsVatRegNo( beanA.getGeneralInfo().getInsured().getVatRegNo() ); 
 		}

		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		/* Mapping: "generalInfo.insured.emirateId" -> "insNationalId" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getEmirateID() )  ){
 			beanB.setInsNationalId( beanA.getGeneralInfo().getInsured().getEmirateID() ); 
 		}
		
		/* Mapping: "generalInfo.insured.emirateexpirydate" -> "insEmiratesIdExpiryDate" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getEmiratesExpiryDate() )  ){
 			beanB.setInsEmiratesIdExpiryDate( beanA.getGeneralInfo().getInsured().getEmiratesExpiryDate() ); 
 		}
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.gen.domain.TMasInsured initializeDeepVO( com.rsaame.pas.vo.bus.PolicyDataVO beanA, com.rsaame.pas.gen.domain.TMasInsured beanB ){
                                                           		return beanB;
	}
}
