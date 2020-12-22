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
 * <li>com.rsaame.pas.vo.bus.GeneralInfoVO</li>
 * <li>com.rsaame.pas.gen.domain.TMasCashCustomerQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasCashCustQuoToGenVOMapperReverse.class )</code>.
 */
public class TMasCashCustQuoToGenVOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.GeneralInfoVO, com.rsaame.pas.gen.domain.TMasCashCustomerQuo>{
	@SuppressWarnings( "unused" )
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasCashCustQuoToGenVOMapperReverse(){
		super();
	}

	public TMasCashCustQuoToGenVOMapperReverse( com.rsaame.pas.vo.bus.GeneralInfoVO src, com.rsaame.pas.gen.domain.TMasCashCustomerQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.gen.domain.TMasCashCustomerQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.gen.domain.TMasCashCustomerQuo) Utils.newInstance( "com.rsaame.pas.gen.domain.TMasCashCustomerQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.GeneralInfoVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.gen.domain.TMasCashCustomerQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "claimsHistory.lossExpQuantum" -> "cshLossAmt" */
		if(  !Utils.isEmpty( beanA.getClaimsHistory() ) && !Utils.isEmpty( beanA.getClaimsHistory().getLossExpQuantum() )  ){
 			beanB.setCshLossAmt( beanA.getClaimsHistory().getLossExpQuantum() ); 
 		}

 		/* Mapping: "claimsHistory.lossExp" -> "cshLossRatio" */
		if(  !Utils.isEmpty( beanA.getClaimsHistory() ) && !Utils.isEmpty( beanA.getClaimsHistory().getLossExp() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setCshLossRatio( converter.getTypeOfA().cast( converter.getAFromB( beanA.getClaimsHistory().getLossExp() ) ) );
  		}

 		/* Mapping: "insured.name" -> "cshEName1" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getName() )  ){
 			beanB.setCshEName1( beanA.getInsured().getName() ); 
 		}

		// For home and travel
		 /* Mapping: "insured.name" -> "cshEName1" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getFirstName() )  ){
 			beanB.setCshEName1( beanA.getInsured().getFirstName() ); 
 		}
  		 /* Mapping: "insured.name" -> "cshEName1" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getLastName() )  ){
 			beanB.setCshEName2( beanA.getInsured().getLastName() ); 
 		}	
		
 		/* Mapping: "insured.address.poBox" -> "cshPoboxNo" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getAddress() ) && !Utils.isEmpty( beanA.getInsured().getAddress().getPoBox() )  ){
 			beanB.setCshPoboxNo( beanA.getInsured().getAddress().getPoBox() ); 
 		}

 		/* Mapping: "insured.arabicName" -> "cshAName1" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getArabicName() )  ){
 			beanB.setCshAName1( beanA.getInsured().getArabicName() ); 
 		}

 		/* Mapping: "insured.address.address" -> "cshEAddress1" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getAddress() ) && !Utils.isEmpty( beanA.getInsured().getAddress().getAddress() )  ){
 			beanB.setCshEAddress1( beanA.getInsured().getAddress().getAddress() ); 
 		}

 		/* Mapping: "insured.address.address" -> "cshEAddress1" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getAddress() ) && !Utils.isEmpty( beanA.getInsured().getAddress().getAddress() )  ){
 			beanB.setCshEAddress1( beanA.getInsured().getAddress().getAddress() ); 
 		}

 		/* Mapping: "insured.nationality" -> "cshNationality" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getNationality() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCshNationality( converter.getTypeOfB().cast( converter.getBFromA( beanA.getInsured().getNationality() ) ) );
  		}

 		/* Mapping: "insured.city" -> "cshCity" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getCity() )  ){
 			beanB.setCshCity( beanA.getInsured().getCity() ); 
 		}

 		/* Mapping: "additionalInfo.affinityRefNo" -> "cshAffinityRefNo" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getAffinityRefNo() )  ){
 			beanB.setCshAffinityRefNo( beanA.getAdditionalInfo().getAffinityRefNo() ); 
 		}

 		/* Mapping: "additionalInfo.faxNumber" -> "cshFaxNo" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getFaxNumber() )  ){
 			beanB.setCshFaxNo( beanA.getAdditionalInfo().getFaxNumber() ); 
 		}

 		/* Mapping: "additionalInfo.dateOfEst" -> "cshDtEstablishment" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getDateOfEst() )  ){
 			beanB.setCshDtEstablishment( beanA.getAdditionalInfo().getDateOfEst() ); 
 		}

 		/* Mapping: "additionalInfo.placeOfEst" -> "cshPlaceEstablishment" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getPlaceOfEst() )  ){
 			beanB.setCshPlaceEstablishment( beanA.getAdditionalInfo().getPlaceOfEst() ); 
 		}

 		/* Mapping: "additionalInfo.payTerms" -> "cshAIdCardNo" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getPayTerms() )  ){
 			beanB.setCshAIdCardNo(beanA.getAdditionalInfo().getPayTerms() ); 
 		}

 		/* Mapping: "insured.address.territory" -> "cshTerritory" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getAddress() ) && !Utils.isEmpty( beanA.getInsured().getAddress().getTerritory() )  ){
 			beanB.setCshTerritory( beanA.getInsured().getAddress().getTerritory() ); 
 		}

 		/* Mapping: "insured.address.country" -> "cshCountry" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getAddress() ) && !Utils.isEmpty( beanA.getInsured().getAddress().getCountry() )  ){
 			beanB.setCshCountry( beanA.getInsured().getAddress().getCountry() ); 
 		}

 		/* Mapping: "additionalInfo.laws" -> "cshLaws" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getLaws() )  ){
 			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCshLaws( converter.getTypeOfA().cast( converter.getAFromB( beanA.getAdditionalInfo().getLaws() ) ) );
  		}

 		/* Mapping: "insured.tradeLicenseNo" -> "cshECoRegnNo" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getTradeLicenseNo() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCshECoRegnNo( converter.getTypeOfB().cast( converter.getBFromA( beanA.getInsured().getTradeLicenseNo() ) ) );
  		}

 		/* Mapping: "insured.mobileNo" -> "cshEGsmNo" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getMobileNo() )  ){
 			beanB.setCshEGsmNo( beanA.getInsured().getMobileNo() ); 
 		}

 		/* Mapping: "insured.phoneNo" -> "cshEPhoneNo" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getPhoneNo() )  ){
 			beanB.setCshEPhoneNo( beanA.getInsured().getPhoneNo() ); 
 		}

 		/* Mapping: "insured.emailId" -> "cshEEmailId" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getEmailId() )  ){
 			beanB.setCshEEmailId( beanA.getInsured().getEmailId() ); 
 		}

 		/* Mapping: "insured.busType" -> "cshBusType" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getBusType() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCshBusType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getInsured().getBusType() ) ) );
  		}

 		/* Mapping: "insured.busDescription" -> "cshBusiness" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getBusDescription() )  ){
 			beanB.setCshBusiness( beanA.getInsured().getBusDescription() ); 
 		}

 		/* Mapping: "additionalInfo.regulatoryBody" -> "cshRegulatoryBody" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getRegulatoryBody() )  ){
 			beanB.setCshRegulatoryBody( beanA.getAdditionalInfo().getRegulatoryBody() ); 
 		}

		// Todo : not generated from the mapper
		/*if( !Utils.isEmpty( beanA.getSourceOfBus().getPromoCode() )){
 			beanB.setCshPromoCode((beanA.getSourceOfBus().getPromoCode() )); 
 		}*/
   
		/* Mapping: "sourceOfBus.customerSource" -> "cshSourceOfCust" */
		// Todo : not generated from the mapper
		if(  !Utils.isEmpty( beanA.getSourceOfBus().getCustomerSource() )  ){
 			beanB.setCshSourceOfCust( Short.valueOf( beanA.getSourceOfBus().getCustomerSource() ) ); 
 		}
		
 		/* Mapping: "generalInfo.additionalInfo.processingLoc" -> "polProcLocCode" */
		if(  !Utils.isEmpty( beanA.getAdditionalInfo() ) && !Utils.isEmpty( beanA.getAdditionalInfo().getProcessingLoc() )  ){
			beanB.setCshLocCode( beanA.getAdditionalInfo().getProcessingLoc() );
 		}
 		/* Mapping: "IntAccExecCode" -> "cshIntAccExecCode" */
		if(  !Utils.isEmpty( beanA.getIntAccExecCode()) ){
			beanB.setCshIntAccExecCode( beanA.getIntAccExecCode() );
 		}
		/* Mapping: "ExtAccExecCode" -> "cshExtAccExecCode" */
		if(  !Utils.isEmpty( beanA.getExtAccExecCode()) ){
			beanB.setCshExtAccExecCode(beanA.getExtAccExecCode() );
 		}
		
		/* Mapping: "insured.royaltyType" -> "cshRoyaltyTypCode" */
		if(  !Utils.isEmpty( beanA.getInsured().getRoyaltyType() ) ){
			beanB.setCshRoyaltyTypCode( beanA.getInsured().getRoyaltyType() );
		}
		
		/* Mapping: "insured.guestCardNo" -> "cshATelexNo" */
		if(  !Utils.isEmpty( beanA.getInsured().getGuestCardNo() ) ){
			beanB.setCshATelexNo( beanA.getInsured().getGuestCardNo() );
		}
		/* Mapping: "generalInfo.insured.noOfEmp" -> "cshNoOfEmp" */
		if(  !Utils.isEmpty( beanA.getInsured() ) && !Utils.isEmpty( beanA.getInsured().getNoOfEmployees() )  ){
 			beanB.setCshNoOfEmp( beanA.getInsured().getNoOfEmployees() ); 
 		}else{
 			beanB.setCshNoOfEmp( null );
 		}
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		/* Mapping: "generalInfo.insured.Emirate ID" -> "cshNational ID" */
		if(  !Utils.isEmpty( beanA.getInsured().getEmirateID() ) ){
			beanB.setCshNationalId( beanA.getInsured().getEmirateID() );
		}
		/* Mapping: "generalInfo.insured.Emirate Expiry Date" -> "cshEmirateIdExpiryDate" */
		if(  !Utils.isEmpty( beanA.getInsured().getEmiratesExpiryDate() ) ){
			beanB.setCshEmiratesIdExpiryDate( beanA.getInsured().getEmiratesExpiryDate() );
		}
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Ends
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.gen.domain.TMasCashCustomerQuo initializeDeepVO( com.rsaame.pas.vo.bus.GeneralInfoVO beanA, com.rsaame.pas.gen.domain.TMasCashCustomerQuo beanB ){
                                                 		return beanB;
	}
}
