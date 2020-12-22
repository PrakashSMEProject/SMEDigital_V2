       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPolicyVOMapper.class )</code>.
 */
public class RequestToPolicyVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PolicyVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPolicyVOMapper(){
		super();
	}

	public RequestToPolicyVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PolicyVO dest ){
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
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PolicyVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "quote_name_jurisdiction" -> "generalInfo.jurisdiction" */
		/*if( !Utils.isEmpty( src.getParameter( "quote_name_jurisdiction" ) ) ){
 			beanB.getGeneralInfo().setJurisdiction( beanA.getParameter( "quote_name_jurisdiction" ) ); 
 		}else{
 			beanB.getGeneralInfo().setJurisdiction( null );
 		}*/

 		/* Mapping: "quote_name_insuredname" -> "generalInfo.insured.name" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_insuredname" ) ) ){
 			beanB.getGeneralInfo().getInsured().setName( beanA.getParameter( "quote_name_insuredname" ) ); 
 		}

		
		/*VAT Reg.*/
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_VAT_REG_NO ) ) ){
 			beanB.setPolVatRegNo( beanA.getParameter( com.Constant.CONST_QUOTE_VAT_REG_NO ) ); 
 			beanB.getGeneralInfo().getInsured().setVatRegNo(( beanA.getParameter( com.Constant.CONST_QUOTE_VAT_REG_NO ) )); 
 		}
		/*VAT Code.*/
		if( !Utils.isEmpty( src.getParameter( "quote_name_polvatcode" ) ) ){
 			beanB.setPolVATCode(( Integer.parseInt(beanA.getParameter( "quote_name_polvatcode" ) ))); 
 		}
		
		
		/* Added for JLT */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_TELEXNO ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setTelexNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_TELEXNO ) ) ) );
			}else {
				beanB.getGeneralInfo().getClaimsHistory().setTelexNo( null );
			}
		
		
		
		
		/* Below mapping is commented as the field is moved to Premium page.*/
 		/* Mapping: "quote_name_araname" -> "generalInfo.insured.arabicName" */
		/*if( !Utils.isEmpty( src.getParameter( "quote_name_araname" ) ) ){
 			beanB.getGeneralInfo().getInsured().setArabicName( beanA.getParameter( "quote_name_araname" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setArabicName( null );
 		}*/

 		/* Mapping: "quote_name_custphno" -> "generalInfo.insured.phoneNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_custphno" ) ) ){
 			beanB.getGeneralInfo().getInsured().setPhoneNo( beanA.getParameter( "quote_name_custphno" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setPhoneNo( null );
 		}

 		/* Mapping: "quote_name_custmobno" -> "generalInfo.insured.mobileNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_custmobno" ) ) ){
 			beanB.getGeneralInfo().getInsured().setMobileNo( beanA.getParameter( "quote_name_custmobno" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setMobileNo( null );
 		}


 		/* Mapping: "quote_name_city" -> "generalInfo.insured.city" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_city" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setCity( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_city" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getInsured().setCity( null );
 		}

 		/* Mapping: "quote_name_emailid" -> "generalInfo.insured.emailId" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_emailid" ) ) ){
 			beanB.getGeneralInfo().getInsured().setEmailId( beanA.getParameter( "quote_name_emailid" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setEmailId( null );
 		}

 		/* Mapping: "quote_name_businessdesc" -> "generalInfo.insured.busDescription" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_businessdesc" ) ) ){
 			beanB.getGeneralInfo().getInsured().setBusDescription( beanA.getParameter( "quote_name_businessdesc" ) ); 
 		}

 		/* Mapping: "quote_name_businesstype" -> "generalInfo.insured.busType" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_businesstype" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setBusType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_businesstype" ) ) ) );
  		}

		/* Below mapping is commented as the field is moved to Premium page.*/
 		/* Mapping: "quote_name_tradelicno" -> "generalInfo.insured.tradeLicenseNo" */
		/*if( !Utils.isEmpty( src.getParameter( "quote_name_tradelicno" ) ) ){
 			beanB.getGeneralInfo().getInsured().setTradeLicenseNo( beanA.getParameter( "quote_name_tradelicno" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setTradeLicenseNo(  null );
 		}*/
		
		/* Mapping: "quote_name_turnover" -> "generalInfo.insured.turnover" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_turnover" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
 			beanB.getGeneralInfo().getInsured().setTurnover( converter.getAFromB( beanA.getParameter( "quote_name_turnover" ) )); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setTurnover(  null );
 		}
		
		/* Mapping: "quote_name_noOfEmployees" -> "generalInfo.insured.noOfEmployees" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_noOfEmployees" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
 			beanB.getGeneralInfo().getInsured().setNoOfEmployees( converter.getAFromB( beanA.getParameter( "quote_name_noOfEmployees" ) )); 
 		}else{
 			beanB.getGeneralInfo().getInsured().setNoOfEmployees(  null );
 		}

 		/* Mapping: "quote_name_nationality" -> "generalInfo.insured.nationality" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_nationality" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setNationality( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_nationality" ) ) ) );
  		}else{
 			beanB.getGeneralInfo().getInsured().setNationality(  null );
 		}


 		/* Mapping: "quote_name_address" -> "generalInfo.insured.address.address" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_address" ) ) ){
 			beanB.getGeneralInfo().getInsured().getAddress().setAddress( beanA.getParameter( "quote_name_address" ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().getAddress().setAddress( null );
 		}


 		/* Mapping: com.Constant.CONST_QUOTE_NAME_POBOX -> "generalInfo.insured.address.poBox" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_POBOX ) ) ){
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_POBOX ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( null );
 		}

 		/* Mapping: com.Constant.CONST_QUOTE_NAME_POBOX -> "generalInfo.insured.address.poBox" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_POBOX ) ) ){
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_POBOX ) ); 
 		}else{
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( null );
 		}

 		/* Mapping: "quote_name_emirates" -> "generalInfo.insured.address.emirates" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_emirates" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().getAddress().setEmirates( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_emirates" ) ) ) );
  		}

 		/* Mapping: "quote_name_country" -> "generalInfo.insured.address.country" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_country" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().getAddress().setCountry( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_country" ) ) ) );
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
		
        /* Added for JLT */
        if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_TELEXNO ) ) ){
               com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
               beanB.getGeneralInfo().getClaimsHistory().setTelexNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_TELEXNO ) ) ) );
               }else {
                     beanB.getGeneralInfo().getClaimsHistory().setTelexNo( null );
               }

 		/* Mapping: "quote_name_remarks" -> "generalInfo.additionalInfo.remarks" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_remarks" ) ) ){
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( beanA.getParameter( "quote_name_remarks" ) ); 
 		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( null );
 		}
		
		/* Mapping: "quote_name_jlt_remarks" -> "generalInfo.additionalInfo.remarks" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_jlt_remarks" ) ) ){
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( beanA.getParameter( "quote_name_jlt_remarks" ) ); 
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

 		/* Mapping: "quote_name_distchnl" -> "generalInfo.sourceOfBus.distributionChannel" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_distchnl" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setDistributionChannel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_distchnl" ) ) ) );
			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( null );
			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( null );
  		}

 		/* Mapping: "quote_name_brokername" -> "generalInfo.sourceOfBus.brokerName" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_brokername" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_brokername" ) ) ) );
  		}

 		/* Mapping: "quote_name_directsubagent" -> "generalInfo.sourceOfBus.directSubAgent" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_directsubagent" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_directsubagent" ) ) ) );
  		}

 		
		/* Mapping: "quote_name_policyno" -> "policyNo" */
		
		if( !Utils.isEmpty( src.getParameter( "quote_name_policyno" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setPolicyNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_policyno" ) ) ) );
  		}

 		/* Mapping: "quote_name_quoteno" -> "quoteNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_quoteno" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setQuoteNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_quoteno" ) ) ) );
  		}

 		/* Mapping: "quote_name_scheme" -> "scheme.schemeCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_scheme" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getScheme().setSchemeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_scheme" ) ) ) );
  		}

 		/* Mapping: "quote_name_tariff" -> "scheme.tariffCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_tariff" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getScheme().setTariffCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_tariff" ) ) ) );
  		}

 		/* Mapping: "quote_name_poltype" -> "scheme.policyCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_poltype" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getScheme().setPolicyCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_poltype" ) ) ) );
  		}

 		/* Mapping: "quote_name_poldesc" -> "scheme.policyType" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_poldesc" ) ) ){
 			beanB.getScheme().setPolicyType( beanA.getParameter( "quote_name_poldesc" ) ); 
 		}

 		/* Mapping: com.Constant.CONST_QUOTE_NAME_EFFDT -> "scheme.effDate" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EFFDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getScheme().setEffDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EFFDT ) ) ) );
  		}
 		/* Mapping: com.Constant.CONST_QUOTE_NAME_EXPDT -> "polEffectiveDate" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EXPDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setPolExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EXPDT ) ) ) );
  		}
		
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EFFDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setPolEffectiveDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EFFDT ) ) ) );
  		}
		
		/* Mapping: com.Constant.CONST_QUOTE_NAME_EXPDT -> "endDate" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EXPDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setEndDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EXPDT ) ) ) );
  		}
 		/* Mapping: com.Constant.CONST_QUOTE_NAME_EXPDT -> "scheme.expiryDate" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_EXPDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getScheme().setExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_EXPDT ) ) ) );
  		}

 		/* Mapping: com.Constant.CONST_QUOTE_NAME_CREATIONDT -> "processedDate" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setProcessedDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ) );
  		}
		
		/* Mapping: com.Constant.CONST_QUOTE_NAME_CREATIONDT -> "Created Date" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setCreated( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_CREATIONDT ) ) ) );
  		}


 		/* Mapping: "quote_name_policyterm" -> "policyTerm" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_policyterm" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setPolicyTerm( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_policyterm" ) ) ) );
  		}

 		/* Mapping: "quote_name_acctdt" -> "authInfoVO.accountingDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_acctdt" ) ) ){
//			START SIT BUG FIX: Unexpected exception Date format exception	
//			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MM/yyyy" );
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
//			END SIT BUG FIX: Unexpected exception Date format exception	
			beanB.getAuthInfoVO().setAccountingDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_acctdt" ) ) ) );
  		}

 		/* Mapping: "quote_name_txntype" -> "authInfoVO.txnType" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_txntype" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setTxnType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_txntype" ) ) ) );
  		}

 		/* Mapping: "quote_name_printeddt" -> "authInfoVO.printedDate" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_printeddt" ) ) ){
//			START SIT BUG FIX: Unexpected exception Date format exception	
//			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MM/yyyy" );
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
//			END SIT BUG FIX: Unexpected exception Date format exception
			
			/* The policy print date should be null for any pending transactions. As the transaction should be in non active state when it is getting 
			 * changed or updated making policy print date as null */
			// beanB.getAuthInfoVO().setPrintedDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_printeddt" ) ) ) );
			beanB.getAuthInfoVO().setPrintedDate( null );
			
  		}

 		/* Mapping: "quote_name_status" -> "status" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_status" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_status" ) ) ) );
  		}

 		/* Mapping: "quote_name_createdBy" -> "authInfoVO.createdBy" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_createdBy" ) ) ){
 			beanB.getAuthInfoVO().setCreatedBy( beanA.getParameter( "quote_name_createdBy" ) ); 
 		}

 		/* Mapping: "quote_name_licensedby" -> "authInfoVO.licensedBy" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_licensedby" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setLicensedBy( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_licensedby" ) ) ) );
  		}

 		/* Mapping: "quote_name_approvedby" -> "authInfoVO.approvedBy" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_approvedby" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setApprovedBy( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_approvedby" ) ) ) );
  		}else{
 			beanB.getAuthInfoVO().setApprovedBy( null );
 		}

 		/* Mapping: "quote_name_approveddt" -> "authInfoVO.approvedDt" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_approveddt" ) ) ){
//			START SIT BUG FIX: Unexpected exception Date format exception			
//			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MM/yyyy" );
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
//			END SIT BUG FIX: Unexpected exception Date format exception
			beanB.getAuthInfoVO().setApprovedDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_approveddt" ) ) ) );
  		}else{
 			beanB.getAuthInfoVO().setApprovedDt( null );
 		}
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

 		/* Mapping: com.Constant.CONST_QUOTE_NAME_LOCCODE -> "authInfoVO.locationCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_LOCCODE ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setLocationCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_LOCCODE ) ) ) );
  		}

 		/* Mapping: "quote_name_intacexe" -> "authInfoVO.intAccExecCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_intacexe" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setIntAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_intacexe" ) ) ) );
  		}else{
 			beanB.getAuthInfoVO().setIntAccExecCode( null );
 		}

 		/* Mapping: "quote_name_extacexec" -> "authInfoVO.extAccExecCode" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_extacexec" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setExtAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_extacexec" ) ) ) );
  		}else{
 			beanB.getAuthInfoVO().setExtAccExecCode(  null );
 		}

 		/* Mapping: com.Constant.CONST_QUOTE_NAME_LOCCODE -> "authInfoVO.extAccExecCode" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_QUOTE_NAME_LOCCODE ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setLocationCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_QUOTE_NAME_LOCCODE ) ) ) );
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
		if( !Utils.isEmpty( src.getParameter( "quote_name_insuredid" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setInsuredId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_insuredid" ) ) ) );
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

 		/* Mapping: "class_code_name" -> "defaultClassCode" */
		if( !Utils.isEmpty( src.getParameter( "class_code_name" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setDefaultClassCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "class_code_name" ) ) ) );
  		}

 		/* Mapping: "quote_txt_endorsmntid" -> "endtId" */
		if( !Utils.isEmpty( src.getParameter( "quote_txt_endorsmntid" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setEndtNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_txt_endorsmntid" ) ) ) );
  		}

		/* Mapping: "quote_name_refpolno" -> "authInfoVO.refPolicyNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_refpolno" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setRefPolicyNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_refpolno" ) ) ) );
  		}else{
 			beanB.getAuthInfoVO().setRefPolicyNo( null );
 		}

 		/* Mapping: "quote_name_refpolyear" -> "authInfoVO.refPolicyYear" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_refpolyear" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setRefPolicyYear( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_refpolyear" ) ) ) );
  		}
		
		//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts
		/* Mapping: "quote_name_renewalbasis" -> "policyVO.renewalBasis" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_renewalbasis" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setRenewalBasis( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_renewalbasis" ) ) ) );
  		}
		//CTS - 29.09.2020 - JLT Renewals UAT Change - JLT Renewal Terms Flag - Starts

		/* Mapping: "quote_name_renewalterms" -> "authInfoVO.renewalTerms" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_renewalterms" ) ) ){
			beanB.getAuthInfoVO().setRenewalTerms( beanA.getParameter( "quote_name_renewalterms" ) );
  		}
		
		/* Mapping: "quote_txt_affinityref" -> "generalInfoVO.additionalInfo.affinityRefNo" */
		if( !Utils.isEmpty( src.getParameter( "quote_txt_affinityref" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo( beanA.getParameter( "quote_txt_affinityref" ) );
  		}else{
 			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo( null );
 		}
		
		/* Mapping: "quote_name_issueBranchCode" -> "generalInfoVO.additionalInfo.issueLoc" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_issueBranchCode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setIssueLoc( converter.getAFromB(beanA.getParameter( "quote_name_issueBranchCode" ) ) );
  		}
		// To avoid empty else block (Sonar defect) 12-9-2017
		else{
			log.debug("Added log to avoid empty else block");
  			//Oman multibrancing impl: Issue location should never be null. When field is disabled it will not be sent in req paramter. So it is getting 
  			//set as null even though issuing branch present. Retain the previous value if issuing branch in such case,don't set it to null
 			//beanB.getGeneralInfo().getAdditionalInfo().setIssueLoc( null );
 		}
		
		/* Mapping: "quote_name_processingBranch" -> "generalInfoVO.additionalInfo.processingLoc" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_processingBranch" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setProcessingLoc( converter.getAFromB(beanA.getParameter( "quote_name_processingBranch" ) ) );
  		}
		// To avoid empty else block (Sonar defect) 12-9-2017
		else{
		  log.debug("Added log to avoid empty else block");
 			//beanB.getGeneralInfo().getAdditionalInfo().setProcessingLoc( null );
 		}
		
		/* Mapping: "policyId" -> "endorsements[].policyId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "policyId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEndorsements().get( i ).setPolicyId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policyId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endtId" -> "endorsements[].endtId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endtId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEndorsements().get( i ).setEndtId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endtId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endtNo" -> "endorsements[].endNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endtNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEndorsements().get( i ).setEndNo ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endtNo[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "slNo" -> "endorsements[].slNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "slNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getEndorsements().get( i ).setSlNo ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "slNo[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "endText" -> "endorsements[].endText" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "endText" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getEndorsements().get( i ).setEndText (  beanA.getParameter( "endText[" + i + "]" ) );
		}



		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PolicyVO beanB ){
  		BeanUtils.initializeBeanField( "generalInfo", beanB );
   		BeanUtils.initializeBeanField( "generalInfo.insured", beanB );
                     		BeanUtils.initializeBeanField( "generalInfo.insured.address", beanB );
             		BeanUtils.initializeBeanField( "generalInfo.additionalInfo", beanB );
                             		BeanUtils.initializeBeanField( "generalInfo.sourceOfBus", beanB );
           		BeanUtils.initializeBeanField( "scheme", beanB );
                 		BeanUtils.initializeBeanField( "authInfoVO", beanB );
                       		BeanUtils.initializeBeanField( "generalInfo.claimsHistory", beanB );
                          		return beanB;
	}
}
