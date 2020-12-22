/*
* THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
*/
package com.rsaame.pas.vo.voholder.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.PolicyDataVO</li>
 * <li>com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyDataVOToTMasCashCustomerVOHolder.class )</code>.
 */
public class PolicyDataVOToTMasCashCustomerVOHolder extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );

	public PolicyDataVOToTMasCashCustomerVOHolder(){
		super();
	}

	public PolicyDataVOToTMasCashCustomerVOHolder( com.rsaame.pas.vo.bus.PolicyDataVO src, com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		com.rsaame.pas.vo.bus.PolicyDataVO beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

		/* Mapping: " beanA.getPolicyId() " -> "setCshPolicyId" */
		if( !Utils.isEmpty( beanA.getPolicyId() ) ){
			beanB.setCshPolicyId( beanA.getPolicyId() );
		}

		/* Mapping: "polValidityStartDate" -> "id.prmValidityStartDate" */
		if( !Utils.isEmpty( beanA.getValidityStartDate() ) ){
			beanB.setCshValidityStartDate( beanA.getValidityStartDate() );
		}

		/* Mapping: "generalInfo.insured.name" -> "cshEName1" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() ) ){
			beanB.setCshEName1( beanA.getGeneralInfo().getInsured().getName() );
		}
		/* Mapping: "insured.LastName" -> "cshEName2" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getLastName() ) ){
			beanB.setCshEName2( beanA.getGeneralInfo().getInsured().getLastName() );
		}
		/* Mapping: "generalInfo.insured.name" -> "cshEName1" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() ) ){
			beanB.setCshEName1( beanA.getGeneralInfo().getInsured().getName() );
		}

		/* Mapping: "generalInfo.insured.address.country" -> "cshCountry" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getCountry() ) ){
			//com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCshCountry( beanA.getGeneralInfo().getInsured().getAddress().getCountry() );
		}

		/* Mapping: "generalInfo.insured.address.emirates" -> "cshCity" 
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getEmirates() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCshCity( converter.getTypeOfA().cast( converter.getAFromB( beanA.getGeneralInfo().getInsured().getAddress().getEmirates() ) ) );
		}*/
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getCity() ) ){
			beanB.setCshCity( beanA.getGeneralInfo().getInsured().getCity() );
		}
		/* Mapping: "generalInfo.insured.address.poBox" -> "cshPoboxNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() ) ){
			beanB.setCshPoboxNo( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() );
		}

		/* Mapping: "generalInfo.insured.emailId" -> "cshEEmailId" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getEmailId() ) ){
			beanB.setCshEEmailId( beanA.getGeneralInfo().getInsured().getEmailId() );
		}

		/* Mapping: "generalInfo.insured.mobileNo" -> "cshEGsmNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getMobileNo() ) ){
			beanB.setCshEGsmNo( beanA.getGeneralInfo().getInsured().getMobileNo() );
		}

		/* Mapping: "generalInfo.insured.nationality" -> "cshNationality" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNationality() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCshNationality( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getInsured().getNationality() ) ) );
		}

		/* Mapping: "generalInfo.sourceOfBus.customerSource" -> "cshSourceOfCust" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getCustomerSource() ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setCshSourceOfCust( converter.getTypeOfA().cast( converter.getAFromB( beanA.getGeneralInfo().getSourceOfBus().getCustomerSource() ) ) );
		}

		/* Mapping: "generalInfo.intAccExecCode" -> "cshIntAccExecCode" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getIntAccExecCode() ) ){
			beanB.setCshIntAccExecCode( beanA.getGeneralInfo().getIntAccExecCode() );
		}

		/* Mapping: "generalInfo.extAccExecCode" -> "cshExtAccExecCode" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getExtAccExecCode() ) ){
			beanB.setCshExtAccExecCode( beanA.getGeneralInfo().getExtAccExecCode() );
		}

		/* Mapping: "generalInfo.additionalInfo.processingLoc" -> "polProcLocCode" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getProcessingLoc() ) ){

			beanB.setCshLocCode( beanA.getGeneralInfo().getAdditionalInfo().getProcessingLoc() );

		}

		/* Mapping: "generalInfo.insured.royaltyType" -> "cshRoyaltyTypCode" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getRoyaltyType() ) ){
			beanB.setCshRoyaltyTypCode( beanA.getGeneralInfo().getInsured().getRoyaltyType() );
		}

		/* Mapping: "generalInfo.insured.guestCardNo" -> "cshATelexNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getGuestCardNo() ) ){
			beanB.setCshATelexNo( beanA.getGeneralInfo().getInsured().getGuestCardNo() );
		}

		/* Mapping: "claimsHistory.lossExpQuantum" -> "cshLossAmt" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getLossExpQuantum() ) ){
			beanB.setCshLossAmt( beanA.getGeneralInfo().getClaimsHistory().getLossExpQuantum() );
		}

		/* Mapping: "claimsHistory.lossExp" -> "cshLossRatio" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getLossExp() ) ){
			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory
					.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setCshLossRatio( converter.getTypeOfA().cast( converter.getAFromB( beanA.getGeneralInfo().getClaimsHistory().getLossExp() ) ) );
		}

		/* Mapping: "insured.address.address" -> "cshEAddress1" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getAddress() ) ){
			beanB.setCshEAddress1( beanA.getGeneralInfo().getInsured().getAddress().getAddress() );
		}

		/* Mapping: "insured.address.address" -> "cshEAddress1" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getAddress() ) ){
			beanB.setCshEAddress1( beanA.getGeneralInfo().getInsured().getAddress().getAddress() );
		}

		/* Mapping: "additionalInfo.affinityRefNo" -> "cshAffinityRefNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() ) ){
			beanB.setCshAffinityRefNo( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() );
		}

		/* Mapping: "additionalInfo.faxNumber" -> "cshFaxNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() ) ){
			beanB.setCshFaxNo( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() );
		}

		/* Mapping: "additionalInfo.dateOfEst" -> "cshDtEstablishment" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() ) ){
			beanB.setCshDtEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() );
		}

		/* Mapping: "additionalInfo.placeOfEst" -> "cshPlaceEstablishment" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() ) ){
			beanB.setCshPlaceEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() );
		}

		/* Mapping: "additionalInfo.payTerms" -> "cshAIdCardNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPayTerms() ) ){
			beanB.setCshAIdCardNo( beanA.getGeneralInfo().getAdditionalInfo().getPayTerms() );
		}

		/* Mapping: "insured.address.territory" -> "cshTerritory" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() )
				&& !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getTerritory() ) ){
			beanB.setCshTerritory( beanA.getGeneralInfo().getInsured().getAddress().getTerritory() );
		}

		/* Mapping: "additionalInfo.laws" -> "cshLaws" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getLaws() ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCshLaws( converter.getTypeOfA().cast( converter.getAFromB( beanA.getGeneralInfo().getAdditionalInfo().getLaws() ) ) );
		}

		/* Mapping: "insured.tradeLicenseNo" -> "cshECoRegnNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() ) ){
			//com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCshECoRegnNo( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() ) ;
		}

		/* Mapping: "insured.phoneNo" -> "cshEPhoneNo" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getPhoneNo() ) ){
			beanB.setCshEPhoneNo( beanA.getGeneralInfo().getInsured().getPhoneNo() );
		}

		/* Mapping: "insured.busType" -> "cshBusType" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusType() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCshBusType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getInsured().getBusType() ) ) );
		}

		/* Mapping: "insured.busDescription" -> "cshBusiness" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusDescription() ) ){
			beanB.setCshBusiness( beanA.getGeneralInfo().getInsured().getBusDescription() );
		}

		/* Mapping: "additionalInfo.regulatoryBody" -> "cshRegulatoryBody" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRegulatoryBody() ) ){
			beanB.setCshRegulatoryBody( beanA.getGeneralInfo().getAdditionalInfo().getRegulatoryBody() );
		}

		/* Mapping: "generalInfo.insured.turnover" -> "cshTurnover" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTurnover() )  ){
 			beanB.setCshTurnover( beanA.getGeneralInfo().getInsured().getTurnover() ); 
 		}else{
 			beanB.setCshTurnover( null );
 		}
		
		/* Mapping: "generalInfo.insured.noOfEmp" -> "cshNoOfEmp" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNoOfEmployees() ) ){
			beanB.setCshNoOfEmp( beanA.getGeneralInfo().getInsured().getNoOfEmployees() );
		}
		else{
			beanB.setCshNoOfEmp( null );
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder initializeDeepVO( com.rsaame.pas.vo.bus.PolicyDataVO beanA, com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder beanB ){
		return beanB;
	}
}
