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
 * <li>com.rsaame.pas.gen.domain.TMasCashCustomerQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyVOToCashCustomerPOJO.class )</code>.
 */
public class PolicyVOToCashCustomerPOJO extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.pas.gen.domain.TMasCashCustomerQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyVOToCashCustomerPOJO(){
		super();
	}

	public PolicyVOToCashCustomerPOJO( com.rsaame.pas.vo.bus.PolicyVO src, com.rsaame.pas.gen.domain.TMasCashCustomerQuo dest ){
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
		com.rsaame.pas.vo.bus.PolicyVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.gen.domain.TMasCashCustomerQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "generalInfo.claimsHistory.lossExpQuantum" -> "cshLossAmt" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getLossExpQuantum() )  ){
			beanB.setCshLossAmt( beanA.getGeneralInfo().getClaimsHistory().getLossExpQuantum() );
  		}else{
 			beanB.setCshLossAmt( null );
 		}
		
		
		/* Mapping: "generalInfo.claimsHistory.TelexNo" -> "setCshETelexNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getTelexNo() )  ){
			beanB.setCshETelexNo(String.valueOf( beanA.getGeneralInfo().getClaimsHistory().getTelexNo() ));
  		}else{
 			beanB.setCshETelexNo( null );
 		}
		

 		/* Mapping: "generalInfo.claimsHistory.lossExp" -> "cshLossRatio" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getLossExp() )  ){
			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setCshLossRatio( converter.getTypeOfA().cast( converter.getAFromB( beanA.getGeneralInfo().getClaimsHistory().getLossExp() ) ) );
  		}else{
 			beanB.setCshLossRatio( null );
 		}

 		/* Mapping: "policyNo" -> "id.cshPolicyId" 
		if(  !Utils.isEmpty( beanA.getPolicyNo() )  ){
 			beanB.getId().setCshPolicyId( beanA.getPolicyNo() ); 
 		}

 		 Mapping: "validityStartDate" -> "id.cshValidityStartDate" 
		if(  !Utils.isEmpty( beanA.getValidityStartDate() )  ){
 			beanB.getId().setCshValidityStartDate( beanA.getValidityStartDate() ); 
 		}*/

 		/* Mapping: "generalInfo.insured.name" -> "cshEName1" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() )  ){
 			beanB.setCshEName1( beanA.getGeneralInfo().getInsured().getName() ); 
 		}else{
 			beanB.setCshEName1( null );
 		}
		
		/* Mapping: "generalInfo.insured.arabicName" -> "cshAName1" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() )  ){
 			beanB.setCshAName1( beanA.getGeneralInfo().getInsured().getArabicName() ); 
 		}else{
 			beanB.setCshAName1( null );
 		}

 		/* Mapping: "generalInfo.insured.address.poBox" -> "cshPoboxNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() )  ){
 			beanB.setCshPoboxNo( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() ); 
 		}else{
 			beanB.setCshPoboxNo( null );
 		}

 		/* Mapping: "generalInfo.insured.emailId" -> "cshEEmailId" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getEmailId() )  ){
 			beanB.setCshEEmailId( beanA.getGeneralInfo().getInsured().getEmailId() ); 
 		}else{
 			beanB.setCshEEmailId( null );
 		}

 		/* Mapping: "generalInfo.insured.address.territory" -> "cshTerritory" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getTerritory() )  ){
 			beanB.setCshTerritory( beanA.getGeneralInfo().getInsured().getAddress().getTerritory() ); 
 		}else{
 			beanB.setCshTerritory( null );
 		}

 		/* Mapping: "generalInfo.insured.address.country" -> "cshCountry" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getCountry() )  ){
 			beanB.setCshCountry( beanA.getGeneralInfo().getInsured().getAddress().getCountry() ); 
 		}else{
 			beanB.setCshCountry( null );
 		}

 		/* Mapping: "generalInfo.insured.tradeLicenseNo" -> "cshECoRegnNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() )  ){
 			beanB.setCshECoRegnNo( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() ); 
 		}else{
 			beanB.setCshECoRegnNo( null );
 		}

 		/* Mapping: "generalInfo.additionalInfo.laws" -> "cshLaws" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getLaws() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCshLaws( converter.getTypeOfA().cast( converter.getAFromB( beanA.getGeneralInfo().getAdditionalInfo().getLaws() ) ) );
  		}else{
 			beanB.setCshLaws( null );
 		}

 		/* Mapping: "generalInfo.additionalInfo.regulatoryBody" -> "cshRegulatoryBody" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRegulatoryBody() )  ){
 			beanB.setCshRegulatoryBody( beanA.getGeneralInfo().getAdditionalInfo().getRegulatoryBody() ); 
 		}else{
 			beanB.setCshRegulatoryBody( null );
 		}

 		/* Mapping: "generalInfo.additionalInfo.affinityRefNo" -> "cshAffinityRefNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() )  ){
 			beanB.setCshAffinityRefNo( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() ); 
 		}else{
 			beanB.setCshAffinityRefNo( null );
 		}

 		/* Mapping: "authInfoVO.intAccExecCode" -> "cshIntAccExecCode" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getIntAccExecCode() )  ){
 			beanB.setCshIntAccExecCode( beanA.getAuthInfoVO().getIntAccExecCode() ); 
 		}else{
 			beanB.setCshIntAccExecCode( null );
 		}

 		/* Mapping: "authInfoVO.extAccExecCode" -> "cshExtAccExecCode" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getExtAccExecCode() )  ){
 			beanB.setCshExtAccExecCode( beanA.getAuthInfoVO().getExtAccExecCode() ); 
 		}else{
 			beanB.setCshExtAccExecCode( null );
 		}

 		/* Mapping: "generalInfo.insured.phoneNo" -> "cshEPhoneNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getPhoneNo() )  ){
 			beanB.setCshEPhoneNo( beanA.getGeneralInfo().getInsured().getPhoneNo() ); 
 		}else{
 			beanB.setCshEPhoneNo( null );
 		}

 		/* Mapping: "generalInfo.insured.mobileNo" -> "cshEGsmNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getMobileNo() )  ){
 			beanB.setCshEGsmNo( beanA.getGeneralInfo().getInsured().getMobileNo() ); 
 		}else{
 			beanB.setCshEGsmNo( null );
 		}

 		/* Mapping: "generalInfo.insured.address.address" -> "cshEAddress1" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getAddress() )  ){
 			beanB.setCshEAddress1( beanA.getGeneralInfo().getInsured().getAddress().getAddress() ); 
 		}else{
 			beanB.setCshEAddress1( null );
 		}

 		/* Mapping: "generalInfo.insured.city" -> "cshCity" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getCity() )  ){
 			beanB.setCshCity( beanA.getGeneralInfo().getInsured().getCity() ); 
 		}else{
 			beanB.setCshCity( null );
 		}

 		/* Mapping: "generalInfo.insured.busType" -> "cshBusType" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCshBusType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getInsured().getBusType() ) ) );
  		}else{
 			beanB.setCshBusType( null );
 		}

 		/* Mapping: "generalInfo.additionalInfo.faxNumber" -> "cshFaxNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() )  ){
 			beanB.setCshFaxNo( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() ); 
 		}else{
 			beanB.setCshFaxNo( null );
 		}

 		/* Mapping: "generalInfo.additionalInfo.dateOfEst" -> "cshDtEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() )  ){
 			beanB.setCshDtEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() ); 
 		}else{
 			beanB.setCshDtEstablishment( null );
 		}

 		/* Mapping: "generalInfo.additionalInfo.placeOfEst" -> "cshPlaceEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() )  ){
 			beanB.setCshPlaceEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() ); 
 		}else{
 			beanB.setCshPlaceEstablishment( null );
 		}

 		/* Mapping: "generalInfo.additionalInfo.payTerms" -> "cshAIdCardNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPayTerms() )  ){
 			beanB.setCshAIdCardNo( beanA.getGeneralInfo().getAdditionalInfo().getPayTerms() ); 
 		}else{
 			beanB.setCshAIdCardNo( "7" ); // Change made to fix for Broker user (as during broker login pay terms will be null setting to default) 
 		}

 		/* Mapping: "generalInfo.insured.nationality" -> "cshNationality" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNationality() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCshNationality( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getInsured().getNationality() ) ) );
  		}else{
 			beanB.setCshNationality( null );
 		}

 		/* Mapping: "generalInfo.insured.busDescription" -> "cshBusiness" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusDescription() )  ){
 			beanB.setCshBusiness( beanA.getGeneralInfo().getInsured().getBusDescription() ); 
 		}else{
 			beanB.setCshBusiness( null );
 		}

 		/* Mapping: "generalInfo.insured.address.country" -> "cshCountry" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getCountry() )  ){
 			beanB.setCshCountry( beanA.getGeneralInfo().getInsured().getAddress().getCountry() ); 
 		}else{
 			beanB.setCshCountry( null );
 		}
		
		/* Mapping: "generalInfo.additionalInfo.payTerms" -> "cshAIdCardNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo())  ){
 			beanB.setCshAffinityRefNo( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() ); 
 		}else{
 			beanB.setCshAffinityRefNo( null );
 		}

		/* Mapping: "generalInfo.insured.turnover" -> "cshTurnover" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTurnover() )  ){
 			beanB.setCshTurnover( beanA.getGeneralInfo().getInsured().getTurnover() ); 
 		}else{
 			beanB.setCshTurnover( null );
 		}
		
		/* Mapping: "generalInfo.insured.noOfEmp" -> "cshNoOfEmp" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNoOfEmployees() )  ){
 			beanB.setCshNoOfEmp( beanA.getGeneralInfo().getInsured().getNoOfEmployees() ); 
 		}else{
 			beanB.setCshNoOfEmp( null );
 		}
		
		/* Mapping: "generalInfo.claimsHistory.TelexNo" -> "setCshETelexNo" */
        if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getTelexNo() )  ){
               beanB.setCshETelexNo(String.valueOf( beanA.getGeneralInfo().getClaimsHistory().getTelexNo() ));
        }else{
              beanB.setCshETelexNo( null );
       }

		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.gen.domain.TMasCashCustomerQuo initializeDeepVO( com.rsaame.pas.vo.bus.PolicyVO beanA, com.rsaame.pas.gen.domain.TMasCashCustomerQuo beanB ){
      		//BeanUtils.initializeBeanField( "id", beanB );
                                                  		return beanB;
	}
}
