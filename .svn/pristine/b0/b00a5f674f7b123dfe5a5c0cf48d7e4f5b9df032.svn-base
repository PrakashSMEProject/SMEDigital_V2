/*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.TravelInsuranceVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToTravelInsuranceVOMapper.class )</code>.
 */
public class RequestToTravelInsuranceVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.TravelInsuranceVO>{
	
	public RequestToTravelInsuranceVOMapper(){
		super();
	}

	public RequestToTravelInsuranceVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.TravelInsuranceVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TravelInsuranceVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TravelInsuranceVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TravelInsuranceVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TravelInsuranceVO beanB = dest;
		
	
		boolean isTraveller_name = true;
		boolean isTraveller_dob = true;
		boolean isTraveller_relation = true;
		boolean isTraveller_nationality = true;
		
		
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB,isTraveller_name,isTraveller_dob,isTraveller_relation,isTraveller_nationality);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		// int noOfItems = 0;
		
		/* Mapping: "quote_name_insuredname" -> "generalInfo.insured.name" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_insuredname" ) ) ){
 			beanB.getGeneralInfo().getInsured().setName( beanA.getParameter( "quote_name_insuredname" ) ); 
 		}
		
		/* Mapping: "quote_name_custphno" -> "generalInfo.insured.phoneNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_custphno" ) ) ){
 			beanB.getGeneralInfo().getInsured().setPhoneNo( beanA.getParameter( "quote_name_custphno" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setPhoneNo( null );
 		}
		
		/* Mapping: "firstName" -> "generalInfo.insured.firstName" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_firstname" ) ) ){
 			beanB.getGeneralInfo().getInsured().setFirstName( beanA.getParameter( "quote_name_firstname" ) ); 
 		}

 		/* Mapping: "lastName" -> "generalInfo.insured.lastName" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_lastname" ) ) ){
 			beanB.getGeneralInfo().getInsured().setLastName( beanA.getParameter( "quote_name_lastname" ) ); 
 		}

 		/* Mapping: "country" -> "generalInfo.insured.address.country" */
		/* changed since id for country is quote_country and not quote_name_counrty in jsp*/
		if( !Utils.isEmpty( src.getParameter( "quote_country" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().getAddress().setCountry( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_country" ) ) ) );
  		}
  			
		/* Mapping: "quote_name_territory" -> "generalInfo.insured.address.territory" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_territory" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().getAddress().setTerritory( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_territory" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getInsured().getAddress().setTerritory( null );
 		}
		
		/* Mapping: "quote_name_laws" -> "generalInfo.additionalInfo.laws" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_laws" ) ) ){
 			beanB.getGeneralInfo().getAdditionalInfo().setLaws( beanA.getParameter( "quote_name_laws" ) ); 
 		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setLaws( null );
 		}
		
		/* Mapping: "quote_name_regbody" -> "generalInfo.additionalInfo.regulatoryBody" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_regbody" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setRegulatoryBody( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_regbody" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setRegulatoryBody( null );
 		}
		
		/* Mapping: "quote_name_dtestb" -> "generalInfo.additionalInfo.dateOfEst" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_dtestb" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getGeneralInfo().getAdditionalInfo().setDateOfEst( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_dtestb" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setDateOfEst(null );
 		}
		
		/* Mapping: "quote_name_placeofest" -> "generalInfo.additionalInfo.placeOfEst" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_placeofest" ) ) ){
 			beanB.getGeneralInfo().getAdditionalInfo().setPlaceOfEst( beanA.getParameter( "quote_name_placeofest" ) ); 
 		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setPlaceOfEst(  null );
 		}
		
		/* Mapping: "quote_name_colldtKYC" -> "generalInfo.additionalInfo.dateOfcollectionOfKYC" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_colldtKYC" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getGeneralInfo().getAdditionalInfo().setDateOfcollectionOfKYC( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_colldtKYC" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setDateOfcollectionOfKYC(  null );
 		}
		
		/* Mapping: "quote_name_tradelicexp" -> "generalInfo.additionalInfo.tradeLicenseExpDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_tradelicexp" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getGeneralInfo().getAdditionalInfo().setTradeLicenseExpDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_tradelicexp" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setTradeLicenseExpDate(  null );
 		}
		
		/* Mapping: "quote_name_insuredstatus" -> "generalInfo.additionalInfo.insuredStatus" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_insuredstatus" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setInsuredStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_insuredstatus" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setInsuredStatus( null );
 		}
		
		/* Mapping: "quote_name_remarks" -> "generalInfo.additionalInfo.remarks" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_remarks" ) ) ){
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( beanA.getParameter( "quote_name_remarks" ) ); 
 		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( null );
 		}
		
		/* Mapping: "quote_name_faxnumber" -> "generalInfo.additionalInfo.faxNumber" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_faxnumber" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setFaxNumber( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_faxnumber" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setFaxNumber( null );
 		}

		/* Mapping: "quote_name_website" -> "generalInfo.additionalInfo.website" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_website" ) ) ){
 			beanB.getGeneralInfo().getAdditionalInfo().setWebsite( beanA.getParameter( "quote_name_website" ) ); 
 		}
		
		/* Mapping: "quote_name_payterms" -> "generalInfo.additionalInfo.payTerms" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_payterms" ) ) ){
 			beanB.getGeneralInfo().getAdditionalInfo().setPayTerms( beanA.getParameter( "quote_name_payterms" ) ); 
 		}
		
		/* Mapping: "quote_name_policyid" -> "generalInfo.additionalInfo.policyId" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_policyid" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setPolicyNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_policyid" ) ) ) );
  		}
		
		

 		/* Mapping: "emirates" -> "generalInfo.insured.address.emirates" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_EMIRATE ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().getAddress().setEmirates( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_EMIRATE ) ) ) );
  		}

 		/* Mapping: "poBoxNo" -> "generalInfo.insured.address.poBox" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_pobox" ) ) ){
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( beanA.getParameter( "quote_name_pobox" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( null );
 		}
		
		/* Mapping: "vatRegNo" -> "generalInfo.insured.vatRegNo" */ //142244
		if( !Utils.isEmpty( src.getParameter( "quote_vat_reg_no" ) ) ){
 			beanB.getGeneralInfo().getInsured().setVatRegNo( beanA.getParameter( "quote_vat_reg_no" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setVatRegNo( null );
 		}
		
		/* Mapping: "vatCode" -> "" */ //142244
		if( !Utils.isEmpty( src.getParameter( "quote_type_code" ) ) ) {
 			beanB.setVatCode( Integer.valueOf(beanA.getParameter( "quote_type_code" ) ) ); 
 		} else {
 			beanB.setVatCode( null );
 		}
		
		/* Mapping: "vatCode" -> "" */ //142244
		if( !Utils.isEmpty( src.getParameter( "amendPolVatCodeGI" ) ) ) {
 			beanB.setVatCode( Integer.valueOf(beanA.getParameter( "amendPolVatCodeGI" ) ) ); 
 		}
		
		/* Mapping: "quote_name_turnover" -> "generalInfo.insured.turnover" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_turnover" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
 			beanB.getGeneralInfo().getInsured().setTurnover( converter.getAFromB( beanA.getParameter( "quote_name_turnover" ) )); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setTurnover(  null );
 		}
		
 		/* Mapping: "email" -> "generalInfo.insured.emailId" */
		if( !Utils.isEmpty( src.getParameter( "quote_txt_emailid" ) ) ){
 			beanB.getGeneralInfo().getInsured().setEmailId( beanA.getParameter( "quote_txt_emailid" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setEmailId( null );
 		}

 		/* Mapping: "mobileNo" -> "generalInfo.insured.mobileNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_mobile" ) ) ){
 			beanB.getGeneralInfo().getInsured().setMobileNo( beanA.getParameter( "quote_mobile" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setMobileNo( null );
 		}
		
		/* Mapping: "quote_name_city" -> "generalInfo.insured.city" */
		/* changed since id for emirate is quote_emirate from quote_name_city in GeneralInfoCommon.jsp*/
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_EMIRATE ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setCity( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_EMIRATE ) ) ) );
  		}

 		/* Mapping: "promotionalCode" -> "generalInfo.sourceOfBus.promoCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_PROMOCODE ) ) ){
 			beanB.getGeneralInfo().getSourceOfBus().setPromoCode( beanA.getParameter( com.Constant.CONST_QUOTE_PROMOCODE ) ); 
 		}

 		/* Mapping: "nationality" -> "generalInfo.insured.nationality" */
		if( !Utils.isEmpty( src.getParameter( "quote_nationality" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setNationality( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_nationality" ) ) ) );
  		}

 		/* Mapping: "distributionChannel" -> "generalInfo.sourceOfBus.distributionChannel" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_distchnl" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setDistributionChannel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_distchnl" ) ) ) );
			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( null );
			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( null );
  		}else if( !Utils.isEmpty( src.getParameter( "quote_distribution_channel_saved" ) ) ){
  			//During amend flow, distribution channel is disabled. As lookup tag doesn't have readonly attribute, reading value from hidden field
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setDistributionChannel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_distribution_channel_saved" ) ) ) );
			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( null );
			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( null );
  		}
		
		/* Mapping: "quote_name_address" -> "generalInfo.insured.address.address" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_address" ) ) ){
 			beanB.getGeneralInfo().getInsured().getAddress().setAddress( beanA.getParameter( "quote_name_address" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().getAddress().setAddress( null );
 		}
		
		/* Mapping: "quote_royalty_type" -> "generalInfo.insured.royaltyType" */
		if( !Utils.isEmpty( src.getParameter( "quote_royalty_type" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setRoyaltyType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_royalty_type" ) ) ) );
		}
		else{
			beanB.getGeneralInfo().getInsured().setRoyaltyType( null );
		}
		
		/* Mapping: "quote_name_address" -> "generalInfo.insured.guestCardNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_guest_card_no" ) ) ){
			beanB.getGeneralInfo().getInsured().setGuestCardNo( beanA.getParameter( "quote_guest_card_no" ) );
		}
		else{
			beanB.getGeneralInfo().getInsured().setGuestCardNo( null );
		}
		
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		/* Mapping: "quote_name_emirateid" -> "generalInfo.insured.emirateid"*/
		if( !Utils.isEmpty( src.getParameter( "quote_name_emirateid" ) ) ){
			beanB.getGeneralInfo().getInsured().setEmirateID( beanA.getParameter( "quote_name_emirateid" ) );
		}
		
		/* Mapping: "quote_emirate_expiry_date" -> "generalInfo.insured.emirateExpiryDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_emirate_expiry_date" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getGeneralInfo().getInsured().setEmiratesExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_emirate_expiry_date" ) ) ) );
		}
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Ends
				
		/* Mapping: "quote_name_noOfEmployees" -> "generalInfo.insured.noOfEmployees" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_noOfEmployees" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
 			beanB.getGeneralInfo().getInsured().setNoOfEmployees( converter.getAFromB( beanA.getParameter( "quote_name_noOfEmployees" ) )); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setNoOfEmployees(  null );
 		}

 		/* Mapping: "brokerName" -> "generalInfo.sourceOfBus.brokerName" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_brokername" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_brokername" ) ) ) );
  		}

 		/* Mapping: "directSubAgent" -> "generalInfo.sourceOfBus.directSubAgent" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_directsubagent" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_directsubagent" ) ) ) );
  		}

 		/* Mapping: "customerSource" -> "generalInfo.sourceOfBus.customerSource" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_custsrc" ) ) ){
 			beanB.getGeneralInfo().getSourceOfBus().setCustomerSource( beanA.getParameter( "quote_name_custsrc" ) ); 
 		}

 		/* Mapping: "promotionalCode" -> "generalInfo.sourceOfBus.promoCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_PROMOCODE ) ) ){
 			beanB.getGeneralInfo().getSourceOfBus().setPromoCode( beanA.getParameter( com.Constant.CONST_QUOTE_PROMOCODE ) ); 
 		}

 		/* Mapping: "scheme" -> "scheme.schemeCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_scheme" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getScheme().setSchemeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_scheme" ) ) ) );
  		}  	 else {
  			beanB.getScheme().setSchemeCode(Integer.valueOf( Utils.getSingleValueAppConfig("COPY_QUOTE_DEFAULT_SCHEME_CODE")));
  		}

 		/* Mapping: "tariff" -> "scheme.tariffCode" */
		if( !Utils.isEmpty( src.getParameter( "travel_tar_code" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getScheme().setTariffCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "travel_tar_code" ) ) ) );
  		}
		
		/* Mapping: "quote_name_poltype" -> "scheme.policyCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_poltype" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getScheme().setPolicyCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_poltype" ) ) ) );
  		}
		
		/* Mapping: "policyType" -> "scheme.policyType" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_POLDESC ) ) ){
 			beanB.getScheme().setPolicyType( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_POLDESC ) ); 
 		}

 /*		 Mapping: "policyType" -> "scheme.policyType" 
		if( !Utils.isEmpty( src.getParameter( "policyType" ) ) ){
 			beanB.getScheme().setPolicyType( beanA.getParameter( "policyType" ) ); 
 		}*/

 		/* Mapping: "effectiveDate" -> "scheme.effDate" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EFFDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getScheme().setEffDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EFFDT ) ) ) );
  		}
		// fix for SIT issue Travel defect 46
		if( Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EFFDT ) ) && !Utils.isEmpty( src.getParameter( "pol_eff_date_cpquote" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getScheme().setEffDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "pol_eff_date_cpquote" ) ) ) );
  		}

 		/* Mapping: "expiryDate" -> "scheme.expiryDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_expdt" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getScheme().setExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_expdt" ) ) ) );
  		}
		
		/* Mapping: com.Constant.CONST_QUOTE_NAME_CREATIONDT -> "processedDate" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setProcessedDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ) );
  		}

		/* Mapping: "policyNo" -> "policyNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_policyno" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setPolicyNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_policyno" ) ) ) );
  		}
		
		/* Mapping: "quote_name_rsaspecind" -> "generalInfo.additionalInfo.rsaSpacialIndicator" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_rsaspecind" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setRsaSpacialIndicator( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_rsaspecind" ) ) ) );
  		}
		
		/* Mapping: "quote_name_specialitytype" -> "generalInfo.additionalInfo.specialityType" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_specialitytype" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setSpecialityType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_specialitytype" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setSpecialityType(null );
 		}

 		/* Mapping: "accountingDt" -> "authenticationInfoVO.accountingDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_acctdt" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getAuthenticationInfoVO().setAccountingDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_acctdt" ) ) ) );
  		}

 		/* Mapping: "trnsactionType" -> "authenticationInfoVO.txnType" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_txntype" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setTxnType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_txntype" ) ) ) );
  		}

 		/* Mapping: "quoteNo" -> "quoteNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_quoteno" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setQuoteNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_quoteno" ) ) ) );
  		}

 		/* Mapping: "printedDt" -> "authenticationInfoVO.printedDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_printeddt" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getAuthenticationInfoVO().setPrintedDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_printeddt" ) ) ) );
  		}

 		/* Mapping: "status" -> "status" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_status" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_status" ) ) ) );
  		}

 		/* Mapping: "createdBy" -> "createdBy" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_createdBy" ) ) ){
 			beanB.setCreatedBy( beanA.getParameter( "quote_name_createdBy" ) ); 
 		}

 		/* Mapping: "creationDt" -> "createdOn" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setCreatedOn( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ) );
  		}

 		/* Mapping: "licensedBy" -> "authenticationInfoVO.licensedBy" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_licensedby" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setLicensedBy( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_licensedby" ) ) ) );
  		}

 		/* Mapping: "approvedBy" -> "authenticationInfoVO.approvedBy" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_approvedby" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setApprovedBy( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_approvedby" ) ) ) );
  		}else{
 			beanB.getAuthenticationInfoVO().setApprovedBy( null );
 		}

 		/* Mapping: "approvedDt" -> "authenticationInfoVO.approvedDt" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_approveddt" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getAuthenticationInfoVO().setApprovedDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_approveddt" ) ) ) );
  		}else{
 			beanB.getAuthenticationInfoVO().setApprovedDt( null );
 		}

 		/* Mapping: "location" -> "authenticationInfoVO.locationCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_loccode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setLocationCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_loccode" ) ) ) );
  		}
		
		/* Mapping: "quote_name_businessclient" -> "generalInfo.claimsHistory.otherBusinessClient" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_businessclient" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setOtherBusinessClient( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_businessclient" ) ) ) );
  		}
		
		/* Mapping: "quote_name_lossexpquantum" -> "generalInfo.claimsHistory.lossExpQuantum" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_lossexpquantum" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setLossExpQuantum( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_lossexpquantum" ) ) ) );
  		}
		
		/* Mapping: "quote_name_lossexp" -> "generalInfo.claimsHistory.lossExp" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_lossexp" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setLossExp( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_lossexp" ) ) ) );
  		}
		
		/* Mapping: "quote_name_adjload" -> "generalInfo.claimsHistory.marketAdjLoad" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_adjload" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setMarketAdjLoad( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_adjload" ) ) ) );
  		}
		
		
		/* Mapping: "refPolNumber" -> "authenticationInfoVO.refPolicyNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_refpolno" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setRefPolicyNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_refpolno" ) ) ) );
  		}else{
 			beanB.getAuthenticationInfoVO().setRefPolicyNo( null );
 		}
		
		

 		/* Mapping: "refPolYear" -> "authenticationInfoVO.refPolicyYear" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_refpolyear" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setRefPolicyYear( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_refpolyear" ) ) ) );
  		}
		
		/* Mapping: "quote_txt_affinityref" -> "generalInfoVO.additionalInfo.affinityRefNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_txt_affinityref" ) ) ){
			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo( beanA.getParameter( "quote_txt_affinityref" ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo( null );
 		}

 		/* Mapping: "endEffDate" -> "endEffectiveDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_date_endteffdt" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setEndEffectiveDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_date_endteffdt" ) ) ) );
  		}

 		/* Mapping: "endExpiryDate" -> "polExpiryDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_date_endexpdt" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setPolExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_date_endexpdt" ) ) ) );
  		}

 		/* Mapping: "intAccExecCode" -> "authenticationInfoVO.intAccExecCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_INTACEXE ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setIntAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_INTACEXE ) ) ) );
  		}else{
 			beanB.getAuthenticationInfoVO().setIntAccExecCode( null );
 		}

 		/* Mapping: "intAccExecCode" -> "authenticationInfoVO.intAccExecCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_INTACEXE ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().setIntAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_INTACEXE ) ) ) );
  		}else{
 			beanB.getGeneralInfo().setIntAccExecCode( null );
 		}
		
 		/* Mapping: "extAccExecCode" -> "authenticationInfoVO.extAccExecCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EXTACEXEC ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setExtAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EXTACEXEC ) ) ) );
  		}else{
 			beanB.getAuthenticationInfoVO().setExtAccExecCode(  null );
 		}
 		/* Mapping: "extAccExecCode" -> "authenticationInfoVO.extAccExecCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EXTACEXEC ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().setExtAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EXTACEXEC ) ) ) );
  		}else{
 			beanB.getGeneralInfo().setExtAccExecCode(  null );
 		}
		
        /* Mapping: "quote_name_policyterm" -> "policyTerm" */
        if( !Utils.isEmpty( src.getParameter( "quote_name_policyterm" ) ) ){
              com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
              beanB.setPolicyTerm( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_policyterm" ) ) ) );
        }
        
       /* Mapping: "quote_name_sob" -> "generalInfo.claimsHistory.sourceOfBusiness" */
        if( !Utils.isEmpty( src.getParameter( "quote_name_sob" ) ) ){
              com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
              beanB.getGeneralInfo().getClaimsHistory().setSourceOfBusiness( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_sob" ) ) ) );
        }
        
        /* Mapping: "quote_name_adjdiscount" -> "generalInfo.claimsHistory.marketAdjDiscount" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_adjdiscount" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setMarketAdjDiscount( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_adjdiscount" ) ) ) );
  		}
		
		/* Mapping: "quote_name_insuredid" -> "generalInfo.insured.insuredId" */
		if(Utils.isEmpty( src.getParameter( "isAddToQuote" )) && !Utils.isEmpty( src.getParameter( "quote_name_insuredid" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setInsuredId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_insuredid" ) ) ) );
  		}
		/* Mapping: "quote_name_insuredid" -> "generalInfo.insured.insuredId" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_insuredcode" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			//beanB.getGeneralInfo().getInsured().setInsuredId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_insuredid" ) ) ) );
			//Insured table has the Insured code. quote_name_insuredid should be mapped to InsuredCode
			beanB.getGeneralInfo().getInsured().setInsuredCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_insuredcode" ) ) ) );
		}
		
		/* Mapping: "quote_name_isChannel_changed" -> "generalInfo.isChannelChanged" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_isChannel_changed" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.getGeneralInfo().setIsChannelChanged( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_isChannel_changed" ) ) ) );
  		}
		
		/* Mapping: "quote_name_hidden_new_cust" -> "generalInfo.newCustomer" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_hidden_new_cust" ) ) ){
 			beanB.getGeneralInfo().setNewCustomer( beanA.getParameter( "quote_name_hidden_new_cust" ) ); 
 		}
		
		/* Mapping: "quote_name_hidden_save_req" -> "generalInfo.customerSaveReq" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_hidden_save_req" ) ) ){
 			beanB.getGeneralInfo().setCustomerSaveReq( beanA.getParameter( "quote_name_hidden_save_req" ) ); 
 		}
		
		/* Mapping: "quote_insured_match_exists" -> "generalInfo.insured.matchExists" */
		if( !Utils.isEmpty( src.getParameter( "quote_insured_match_exists" ) ) ){
 			beanB.getGeneralInfo().getInsured().setMatchExists( beanA.getParameter( "quote_insured_match_exists" ) ); 
 		}
		
		/* Mapping: "quote_txt_endorsmntid" -> "endtId" */
		if( !Utils.isEmpty( src.getParameter( "quote_txt_endorsmntid" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setEndtNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_txt_endorsmntid" ) ) ) );
  		}
		
		
        
       /* Mapping: "quote_name_businesstype" -> "generalInfo.insured.busType" */
        if( !Utils.isEmpty( src.getParameter( "quote_name_businesstype" ) ) ){
              com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
              beanB.getGeneralInfo().getInsured().setBusType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_businesstype" ) ) ) );
        }
        
       /* Mapping: "quote_name_businessdesc" -> "generalInfo.insured.busDescription" */
        if( !Utils.isEmpty( src.getParameter( "quote_name_businessdesc" ) ) ){
             beanB.getGeneralInfo().getInsured().setBusDescription( beanA.getParameter( "quote_name_businessdesc" ) ); 
        }

       /* Mapping: com.Constant.CONST_QUOTE_NAME_POLDESC -> "scheme.policyType" */
        if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_POLDESC ) ) ){
             beanB.getScheme().setPolicyType( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_POLDESC ) );

        }
		// Added manually for travel mapping
 		/* Mapping: "effectiveDate" -> "scheme.effDate" */
		if( !Utils.isEmpty( src.getParameter( "travel_quote_name_startDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getScheme().setEffDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "travel_quote_name_startDate" ) ) ) );
  		}

 		/* Mapping: "expiryDate" -> "scheme.expiryDate" */
		if( !Utils.isEmpty( src.getParameter( "travel_quote_name_endDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getScheme().setExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "travel_quote_name_endDate" ) ) ) );
  		}
		        /* Mapping: "quote_name_policyterm" -> "policyTerm" */
        if( !Utils.isEmpty( src.getParameter( "travel_quote_name_period" ) ) ){
              com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
              beanB.setPolicyTerm( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "travel_quote_name_period" ) ) ) );
        }
        
        //todo : Added manully for RequesttoTravelDetailsVO. Hard coded. generate it from mapper
        /* Mapping: "travel_quote_name_location" -> "travelLocation" */
		if( !Utils.isEmpty( src.getParameter( "travel_quote_name_location" ) ) ){
 			beanB.getTravelDetailsVO().setTravelLocation( beanA.getParameter( "travel_quote_name_location" ) ); 
 		}
		
		if( !Utils.isEmpty( src.getParameter( "travel_final_destination" ) ) ){
 			beanB.getTravelDetailsVO().setFinalDestination(Integer.valueOf(beanA.getParameter( "travel_final_destination" ))); 
 		}

		/* Mapping: "source_Of_Business" -> "sourceOfBusiness" */
		if( !Utils.isEmpty( src.getParameter( "source_of_Business" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setSourceOfBusiness( converter.getAFromB( beanA.getParameter( "source_of_Business" ) ) ); 
		}
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> noOfItems = null;
		int index = 0;
		/* Mapping: "traveller_name" -> "travelerDetailsList[].name" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_name" );
  		index = 0;
   		for( String i : noOfItems ){
        			beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setName (  beanA.getParameter( i ) );
        			index++;
		}

 		/* Mapping: "traveller_dob" -> "travelerDetailsList[].dateOfBirth" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_dob" );
  		index = 0;
   		for( String i : noOfItems  ){
        	com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MM/yyyy" );
			beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setDateOfBirth ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			index++;
 		}
   		
   		/* Mapping: "traveller_gender" -> "travelerDetailsList[].gender" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_gender" );
  		index = 0;
   		for(String i : noOfItems ){
   				char gender ='\0';
   				if(!beanA.getParameter( i ).equals("") && !Utils.isEmpty(beanA.getParameter( i ))){
   					gender = beanA.getParameter( i ).charAt(0);
   				}
    			//beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setGender(beanA.getParameter( i ).charAt(0));
   				beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setGender(gender);
    			index++;
		}

 		/* Mapping: "traveller_relation" -> "travelerDetailsList[].relation" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_relation" );
  		index = 0;
   		for(String i : noOfItems ){
    			beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setRelation (Byte.valueOf( beanA.getParameter( i )) );
    			index++;
		}

 		/* Mapping: "traveller_nationality" -> "travelerDetailsList[].nationality" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_nationality" );
  		index = 0;
   		for( String i : noOfItems ){
        	com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setNationality ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			index++;
 		}
   		
 		/* Mapping: "traveller_nationality" -> "travelerDetailsList[].traveller_table_id" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_table_id" );
  		index = 0;
   		for( String i : noOfItems ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setGprId( converter.getTypeOfA().cast( converter.getAFromB(beanA.getParameter( i) ) ) );
			index++;
 		}

 		/* Mapping: "traveller_nationality" -> "travelerDetailsList[].traveller_vsd" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_vsd" );
  		index = 0;
   		for( String i : noOfItems ){
   			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MM/yyyy HH:mm:ss" );
   			beanB.getTravelDetailsVO().getTravelerDetailsList().get( index ).setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
   			index++;
 		}
   		
   		/*OMAN multi-branching branching fix.*/
        if( !Utils.isEmpty( src.getParameter( "quote_name_processingBranch" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setProcessingLoc( converter.getAFromB(beanA.getParameter( "quote_name_processingBranch" ) ) );
  		}
        
		
   		
		return dest; 
		
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TravelInsuranceVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.TravelInsuranceVO beanB,
			boolean isTraveller_name, boolean isTraveller_dob, boolean isTraveller_relation, boolean isTraveller_nationality ){

		BeanUtils.initializeBeanField( "generalInfo.insured", beanB );
		BeanUtils.initializeBeanField( "generalInfo.insured.address", beanB );
		BeanUtils.initializeBeanField( "generalInfo.additionalInfo", beanB );
		BeanUtils.initializeBeanField( "generalInfo.sourceOfBus", beanB );
		BeanUtils.initializeBeanField( "scheme", beanB );
		BeanUtils.initializeBeanField( "authenticationInfoVO", beanB );
		BeanUtils.initializeBeanField( "generalInfo.claimsHistory", beanB );

		BeanUtils.initializeBeanField( "travelDetailsVO.travelerDetailsList[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_name[]" ).size() );

		return beanB;
	}
}
