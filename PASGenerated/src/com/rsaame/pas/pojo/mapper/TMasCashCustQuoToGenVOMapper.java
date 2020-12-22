       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.gen.domain.TMasCashCustomerQuo</li>
 * <li>com.rsaame.pas.vo.bus.GeneralInfoVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasCashCustQuoToGenVOMapper.class )</code>.
 */
public class TMasCashCustQuoToGenVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.gen.domain.TMasCashCustomerQuo, com.rsaame.pas.vo.bus.GeneralInfoVO>{
	@SuppressWarnings( "unused" )
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasCashCustQuoToGenVOMapper(){
		super();
	}

	public TMasCashCustQuoToGenVOMapper( com.rsaame.pas.gen.domain.TMasCashCustomerQuo src, com.rsaame.pas.vo.bus.GeneralInfoVO dest ){
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
		com.rsaame.pas.gen.domain.TMasCashCustomerQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GeneralInfoVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "cshLossAmt" -> "claimsHistory.lossExpQuantum" */
		if(  !Utils.isEmpty( beanA.getCshLossAmt() )  ){
 			beanB.getClaimsHistory().setLossExpQuantum( beanA.getCshLossAmt() ); 
 		}

 		/* Mapping: "cshLossRatio" -> "claimsHistory.lossExp" */
		if(  !Utils.isEmpty( beanA.getCshLossRatio() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.getClaimsHistory().setLossExp( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCshLossRatio() ) ) );
  		}

 		/* Mapping: "cshEName1" -> "insured.name" */
		if(  !Utils.isEmpty( beanA.getCshEName1() )  ){
 			beanB.getInsured().setName( beanA.getCshEName1() ); 
 		}

 		/* Mapping: "cshPoboxNo" -> "insured.address.poBox" */
		if(  !Utils.isEmpty( beanA.getCshPoboxNo() )  ){
 			beanB.getInsured().getAddress().setPoBox( beanA.getCshPoboxNo() ); 
 		}

 		/* Mapping: "cshAName1" -> "insured.arabicName" */
		if(  !Utils.isEmpty( beanA.getCshAName1() )  ){
 			beanB.getInsured().setArabicName( beanA.getCshAName1() ); 
 		}

 		/* Mapping: "cshEAddress1" -> "insured.address.address" */
		if(  !Utils.isEmpty( beanA.getCshEAddress1() )  ){
 			beanB.getInsured().getAddress().setAddress( beanA.getCshEAddress1() ); 
 		}

 		/* Mapping: "cshEAddress1" -> "insured.address.address" */
		if(  !Utils.isEmpty( beanA.getCshEAddress1() )  ){
 			beanB.getInsured().getAddress().setAddress( beanA.getCshEAddress1() ); 
 		}

 		/* Mapping: "cshNationality" -> "insured.nationality" */
		if(  !Utils.isEmpty( beanA.getCshNationality() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getInsured().setNationality( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCshNationality() ) ) );
  		}

 		/* Mapping: "cshCity" -> "insured.city" */
		if(  !Utils.isEmpty( beanA.getCshCity() )  ){
 			beanB.getInsured().setCity( beanA.getCshCity() ); 
 		}

 		/* Mapping: "cshAffinityRefNo" -> "additionalInfo.affinityRefNo" */
		if(  !Utils.isEmpty( beanA.getCshAffinityRefNo() )  ){
 			beanB.getAdditionalInfo().setAffinityRefNo( beanA.getCshAffinityRefNo() ); 
 		}

 		/* Mapping: "cshFaxNo" -> "additionalInfo.faxNumber" */
		if(  !Utils.isEmpty( beanA.getCshFaxNo() )  ){
 			beanB.getAdditionalInfo().setFaxNumber( beanA.getCshFaxNo() ); 
 		}

 		/* Mapping: "cshDtEstablishment" -> "additionalInfo.dateOfEst" */
		if(  !Utils.isEmpty( beanA.getCshDtEstablishment() )  ){
 			beanB.getAdditionalInfo().setDateOfEst( beanA.getCshDtEstablishment() ); 
 		}

 		/* Mapping: "cshPlaceEstablishment" -> "additionalInfo.placeOfEst" */
		if(  !Utils.isEmpty( beanA.getCshPlaceEstablishment() )  ){
 			beanB.getAdditionalInfo().setPlaceOfEst( beanA.getCshPlaceEstablishment() ); 
 		}

 		/* Mapping: "cshAIdCardNo" -> "additionalInfo.payTerms" */
		if(  !Utils.isEmpty( beanA.getCshAIdCardNo() )  ){
 			beanB.getAdditionalInfo().setPayTerms( beanA.getCshAIdCardNo() ); 
 		}

 		/* Mapping: "cshTerritory" -> "insured.address.territory" */
		if(  !Utils.isEmpty( beanA.getCshTerritory() )  ){
 			beanB.getInsured().getAddress().setTerritory( beanA.getCshTerritory() ); 
 		}

 		/* Mapping: "cshCountry" -> "insured.address.country" */
		if(  !Utils.isEmpty( beanA.getCshCountry() )  ){
 			beanB.getInsured().getAddress().setCountry( beanA.getCshCountry() ); 
 		}

 		/* Mapping: "cshLaws" -> "additionalInfo.laws" */
		if(  !Utils.isEmpty( beanA.getCshLaws() )  ){
 			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalInfo().setLaws( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCshLaws() ) ) );
  		}
		
 		/* Mapping: "processingLoc" -> "addtionalInfo.processingLoc" */
		//Manully added - santhosh
		if(  !Utils.isEmpty( beanA.getCshLocCode())  ){
			beanB.getAdditionalInfo().setProcessingLoc(beanA.getCshLocCode());
  		}

 		/* Mapping: "cshECoRegnNo" -> "insured.tradeLicenseNo" */
		if(  !Utils.isEmpty( beanA.getCshECoRegnNo() )  ){
			
			beanB.getInsured().setTradeLicenseNo( beanA.getCshECoRegnNo()  ) ;
  		}

 		/* Mapping: "cshEGsmNo" -> "insured.mobileNo" */
		if(  !Utils.isEmpty( beanA.getCshEGsmNo() )  ){
 			beanB.getInsured().setMobileNo( beanA.getCshEGsmNo() ); 
 		}else{
 			beanB.getInsured().setMobileNo( null );
 		}

 		/* Mapping: "cshEPhoneNo" -> "insured.phoneNo" */
		if(  !Utils.isEmpty( beanA.getCshEPhoneNo() )  ){
 			beanB.getInsured().setPhoneNo( beanA.getCshEPhoneNo() ); 
 		}else{
 			beanB.getInsured().setPhoneNo( null ); 
 		}

 		/* Mapping: "cshEEmailId" -> "insured.emailId" */
		if(  !Utils.isEmpty( beanA.getCshEEmailId() )  ){
 			beanB.getInsured().setEmailId( beanA.getCshEEmailId() ); 
 		}

 		/* Mapping: "cshBusType" -> "insured.busType" */
		if(  !Utils.isEmpty( beanA.getCshBusType() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getInsured().setBusType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCshBusType() ) ) );
  		}

 		/* Mapping: "cshBusiness" -> "insured.busDescription" */
		if(  !Utils.isEmpty( beanA.getCshBusiness() )  ){
 			beanB.getInsured().setBusDescription( beanA.getCshBusiness() ); 
 		}

 		/* Mapping: "cshRegulatoryBody" -> "additionalInfo.regulatoryBody" */
		if(  !Utils.isEmpty( beanA.getCshRegulatoryBody() )  ){
 			beanB.getAdditionalInfo().setRegulatoryBody( beanA.getCshRegulatoryBody() ); 
 		}

		/* Mapping: "cshTurnover" -> "insured.turnover" */
		if(  !Utils.isEmpty( beanA.getCshTurnover() )  ){
 			beanB.getInsured().setTurnover( beanA.getCshTurnover() ); 
 		}else{
 			beanB.getInsured().setTurnover( null );
 		}
		
		/* Mapping: "cshNoOfEmp" -> "insured.noOfEmployees" */
		if(  !Utils.isEmpty( beanA.getCshNoOfEmp() )  ){
 			beanB.getInsured().setNoOfEmployees( beanA.getCshNoOfEmp() ); 
 		}else{
 			beanB.getInsured().setNoOfEmployees( null ); 
 		}
		// Todo : not generated from the mapper
		/*if( !Utils.isEmpty( beanA.getCshPromoCode() )){
 			beanB.getSourceOfBus().setPromoCode( beanA.getCshPromoCode() ); 
 		}*/
		
		/* Mapping: "cshSourceOfCust" -> "sourceOfBus.customerSource" */
		if(  !Utils.isEmpty( beanA.getCshSourceOfCust() )  ){
 			beanB.getSourceOfBus().setCustomerSource(  String.valueOf( beanA.getCshSourceOfCust() ) ); 
 		}
   
		if(  !Utils.isEmpty( beanA.getCshEName1() )){
 			beanB.getInsured().setFirstName( beanA.getCshEName1() ); 
 		}
  		 /* Mapping: "insured.name" -> "cshEName1" */
		if(  !Utils.isEmpty( beanA.getCshEName2() )){
 			beanB.getInsured().setLastName( beanA.getCshEName2() ); 
 		}	
		
		if(  !Utils.isEmpty( beanA.getCshIntAccExecCode() )){
 			beanB.setIntAccExecCode( beanA.getCshIntAccExecCode() ) ;
 		}	
		if(  !Utils.isEmpty( beanA.getCshExtAccExecCode() )){
 			beanB.setExtAccExecCode( beanA.getCshExtAccExecCode()) ;
 		}	
		if( !Utils.isEmpty( beanA.getCshATelexNo() ) ){
			beanB.getInsured().setGuestCardNo( beanA.getCshATelexNo() );
		}
		if( !Utils.isEmpty( beanA.getCshRoyaltyTypCode() ) ){
			beanB.getInsured().setRoyaltyType( beanA.getCshRoyaltyTypCode() );
		}
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		/* Mapping: "cshNational ID" -> "generalInfo.insured.Emirate ID"*/
		if( !Utils.isEmpty( beanA.getCshNationalId() ) ){
			beanB.getInsured().setEmirateID( beanA.getCshNationalId() );
		}
		/* Mapping: "cshEmirateIdExpiryDate" -> "generalInfo.insured.Emirate Expiry Date"*/
		if( !Utils.isEmpty( beanA.getCshEmiratesIdExpiryDate() ) ){
			beanB.getInsured().setEmiratesExpiryDate( beanA.getCshEmiratesIdExpiryDate());
		}
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Ends

		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.GeneralInfoVO initializeDeepVO( com.rsaame.pas.gen.domain.TMasCashCustomerQuo beanA, com.rsaame.pas.vo.bus.GeneralInfoVO beanB ){
  		BeanUtils.initializeBeanField( "claimsHistory", beanB );
     		BeanUtils.initializeBeanField( "insured", beanB );
   		BeanUtils.initializeBeanField( "insured.address", beanB );
             		BeanUtils.initializeBeanField( "additionalInfo", beanB );
             		BeanUtils.initializeBeanField( "sourceOfBus", beanB );
                              		return beanB;
	}
}
