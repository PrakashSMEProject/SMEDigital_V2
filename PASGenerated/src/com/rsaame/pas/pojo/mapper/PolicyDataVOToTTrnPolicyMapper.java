       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.PolicyDataVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyDataVOToTTrnPolicyMapper.class )</code>.
 */
public class PolicyDataVOToTTrnPolicyMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PolicyDataVO, com.rsaame.pas.dao.model.TTrnPolicyQuo>{
	@SuppressWarnings( "unused" )
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyDataVOToTTrnPolicyMapper(){
		super();
	}

	public PolicyDataVOToTTrnPolicyMapper( com.rsaame.pas.vo.bus.PolicyDataVO src, com.rsaame.pas.dao.model.TTrnPolicyQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPolicyQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPolicyQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPolicyQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PolicyDataVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		@SuppressWarnings( "unused" )
		int noOfItems = 0;
		
		if(  !Utils.isEmpty( beanA.getPolicyId() )  ){
			beanB.getId().setPolPolicyId( beanA.getPolicyId() );
		}

		
		if(  !Utils.isEmpty( beanA.getEndtId() )  ){
			beanB.getId().setEndtId( beanA.getEndtId() ); 
		}
		
		if(  !Utils.isEmpty( beanA.getPolicyId() )  ){
			beanB.getId().setPolPolicyId( beanA.getPolicyId() );
		}

		
		if(  !Utils.isEmpty( beanA.getEndtId() )  ){
			beanB.getId().setEndtId( beanA.getEndtId() ); 
		}
		
		/* Mapping: "generalInfo.insured.busType" -> "polBusinessType" */
		//jaya not set
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolBusinessType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getInsured().getBusType() ) ) );
  		}
		
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getPolBusType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolBusinessType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getInsured().getPolBusType() ) ) );
  		}
		
		//jaya not set
 		/* Mapping: "generalInfo.additionalInfo.rsaSpacialIndicator" -> "polCoverNoteMin" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRsaSpacialIndicator() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setPolCoverNoteMin( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getAdditionalInfo().getRsaSpacialIndicator() ) ) );
  		}

 		/* Mapping: "generalInfo.additionalInfo.issueLoc" -> "polProcLocCode" */
		//jaya not set
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getIssueLoc() )  ){
 			beanB.setPolProcLocCode( beanA.getGeneralInfo().getAdditionalInfo().getIssueLoc() ); 
 		}

		/* Mapping: "generalInfo.sourceOfBus.distributionChannel" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() )  ){
 			beanB.setPolDistributionChnl( beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.brokerName" -> "polBrCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolBrCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() ) ) );
  		}

 		/* Mapping: "generalInfo.sourceOfBus.directSubAgent" -> "polAgentId" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() )  ){
 			beanB.setPolAgentId( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ); 
 		}

 		/* Mapping: "quoteNo" -> "polQuotationNo" */
		if(  !Utils.isEmpty( beanA.getQuoteNo() )  ){
 			beanB.setPolQuotationNo( beanA.getQuoteNo() ); 
 		}

 		/* Mapping: "policyNo" -> "polPolicyNo" */
		if(  !Utils.isEmpty( beanA.getPolicyNo() )  ){
 			beanB.setPolPolicyNo( beanA.getPolicyNo() ); 
 		}

 		/* Mapping: "endtId" -> "id.polEndtId" */
		if(  !Utils.isEmpty( beanA.getEndtId() )  ){
 			beanB.getId().setPolEndtId( beanA.getEndtId() ); 
 		}

 		/* Mapping: "scheme.schemeCode" -> "polCoverNoteHour" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getSchemeCode() )  ){
 			beanB.setPolCoverNoteHour( beanA.getScheme().getSchemeCode() ); 
 		}

 		/* Mapping: "scheme.tariffCode" -> "polTarCode" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getTariffCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolTarCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getScheme().getTariffCode() ) ) );
  		}

 		/* Mapping: "policyType" -> "polPolicyType" */
		if(  !Utils.isEmpty( beanA ) && !Utils.isEmpty( beanA.getPolicyType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolPolicyType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolicyType() ) ) );
  		}

 		/* Mapping: "scheme.effDate" -> "polEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getEffDate() )  ){
 			beanB.setPolEffectiveDate( beanA.getScheme().getEffDate() ); 
 		}

 		/* Mapping: "scheme.expiryDate" -> "polExpiryDate" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getExpiryDate() )  ){
 			beanB.setPolExpiryDate( beanA.getScheme().getExpiryDate() ); 
 		}
		
		/* Mapping: "polExpiryDate" -> "polEndtExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPolExpiryDate() )){
 			beanB.setPolEndtExpiryDate( beanA.getPolExpiryDate() ); 
 		}
         //Jaya not set
 		/* Mapping: "policyTerm" -> "polPolicyTerm" */
		if(  !Utils.isEmpty( beanA.getPolicyTerm() )  ){
 			beanB.setPolPolicyTerm( beanA.getPolicyTerm() ); 
 		}

 		/* Mapping: "authenticationInfoVO.licensedBy" -> "polUserId" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getLicensedBy() )  ){
 			beanB.setPolUserId( beanA.getAuthenticationInfoVO().getLicensedBy() ); 
 		}

 		/* Mapping: "authenticationInfoVO.approvedBy" -> "polApprovedBy" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getApprovedBy() )  ){
 			beanB.setPolApprovedBy( beanA.getAuthenticationInfoVO().getApprovedBy() ); 
 		}
		//Jaya not set
 		/* Mapping: "authenticationInfoVO.approvedDt" -> "polApprovalDate" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getApprovedDt() )  ){
 			beanB.setPolApprovalDate( beanA.getAuthenticationInfoVO().getApprovedDt() ); 
 		}

		/* Mapping: "polExchangeRate" -> "polExchangeRate" */
		if(  !Utils.isEmpty( beanA.getPolExchangeRate() )  ){
 			beanB.setPolExchangeRate( beanA.getPolExchangeRate() );
 		}
		
		/* Mapping: "processedDate" -> "polProcessedDate"*/
		if( !Utils.isEmpty( beanA.getProcessedDate() )){
			beanB.setPolProcessedDate( beanA.getProcessedDate() );
		}
		
 		/* Mapping: "generalInfo.insured.insuredCode" -> "polInsuredCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getInsuredCode() )  ){
 			//beanB.setPolInsuredId( beanA.getGeneralInfo().getInsured().getInsuredId() ); 
 			beanB.setPolInsuredCode(beanA.getGeneralInfo().getInsured().getInsuredCode() ); 
 		}
		
		/* Mapping: "generalInfo.insured.vatRegNo" -> "vatRegNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getVatRegNo() )  ){
 			//142244
 			beanB.setPolVatRegNo(beanA.getGeneralInfo().getInsured().getVatRegNo() ); 
 		
 			
 		}
		
 		/* Mapping: "generalInfo.insured.insuredId" -> "polInsuredId" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getInsuredId() )  ){
 			beanB.setPolInsuredId( beanA.getGeneralInfo().getInsured().getInsuredId() ); 
 		}
 		/* Mapping: "policyClassCode" -> "polClassCode" */
		if(  !Utils.isEmpty( beanA.getPolicyClassCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolClassCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolicyClassCode() ) ) );
  		}
         //jaya not set
 		/* Mapping: "generalInfo.claimsHistory.sourceOfBusiness" -> "polSourceOfBusiness" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getSourceOfBusiness() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setPolSourceOfBusiness( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getClaimsHistory().getSourceOfBusiness() ) ) );
  		}

 		/* Mapping: "authenticationInfoVO.createdBy" -> "polPreparedBy" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getCreatedBy() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setPolPreparedBy( converter.getTypeOfA().cast( converter.getAFromB( beanA.getAuthenticationInfoVO().getCreatedBy() ) ) );
  		}

 		/* Mapping: "authenticationInfoVO.createdOn" -> "polPreparedDt" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getCreatedOn() )  ){
 			beanB.setPolPreparedDt( beanA.getAuthenticationInfoVO().getCreatedOn() ); 
 		}

 		/* Mapping: "status" -> "polStatus" */
		if(  !Utils.isEmpty( beanA.getStatus() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setPolStatus( converter.getTypeOfB().cast( converter.getBFromA( beanA.getStatus() ) ) );
  		}

 		/* Mapping: "authenticationInfoVO.refPolicyNo" -> "polRefPolicyNo" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getRefPolicyNo() )  ){
 			beanB.setPolRefPolicyNo( beanA.getAuthenticationInfoVO().getRefPolicyNo() ); 
 		}

 		/* Mapping: "authenticationInfoVO.refPolicyYear" -> "polRefPolicyYear" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getRefPolicyYear() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolRefPolicyYear( converter.getTypeOfB().cast( converter.getBFromA( beanA.getAuthenticationInfoVO().getRefPolicyYear() ) ) );
  		}		
		
		/* Mapping: "authenticationInfoVO.refPolicyId" -> "polRefPolicyId" */
		if(  !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getRefPolicyId() )  ){
 			beanB.setPolRefPolicyId( beanA.getAuthenticationInfoVO().getRefPolicyId() ); 
 		}
		
		/* Mapping: "commonVO.concatPolicyNo" -> "polConcPolicyNo" */
		if(  !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getConcatPolicyNo() )  ){
 			beanB.setPolConcPolicyNo( beanA.getCommonVO().getConcatPolicyNo() ); 
 		}
		
 		/* Mapping: "endEffectiveDate" -> "polEndtEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getEndEffectiveDate() )  ){
 			beanB.setPolEndtEffectiveDate( beanA.getEndEffectiveDate() ); 
 		}

		 
 		/* Mapping: "premiumVO.premiumAmt" -> "polPremium" */
		if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getPremiumAmt() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolPremium( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getPremiumAmt() ) ) );
  		}
		
 		/* Mapping: "premiumVO.govtTax" -> "polGovernmentTax" */
		if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getGovtTax() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolGovernmentTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getGovtTax() ) ) );
  		}
		
		/* Mapping: "premiumVO.vatTax" -> "polvatTax" 142244 */
		if(  !Utils.isEmpty( beanA.getCommonVO().getPremiumVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPremiumVO().getVatTax() )
				           && (beanA.getPolicyClassCode().equals(Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_CLASS_CODE ) )))   ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVatTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCommonVO().getPremiumVO().getVatTax() ) ) );
  		}
		else  if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getVatTax() )  
				&& ((beanA.getPolicyClassCode().equals(Integer.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE)))) || (!Utils
						.isEmpty(beanA.getPolicyType()) && beanA.getPolicyType().toString().equals(Utils.getSingleValueAppConfig(com.Constant.CONST_WC_POLICY_TYPE))))) {
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVatTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getVatTax() ) ) );
  		}
		
		/* Mapping: "premiumVO.vatTaxPerc" -> "polvatTaxPerc" 142244 */
		if(  !Utils.isEmpty( beanA.getCommonVO().getPremiumVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPremiumVO().getVatTaxPerc() )  
				           && (beanA.getPolicyClassCode().equals(Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_CLASS_CODE ) )))   ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVatTaxPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCommonVO().getPremiumVO().getVatTaxPerc() ) ) );
  		}
		else if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getVatTaxPerc() )  
				           && ((beanA.getPolicyClassCode().equals(Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_TRAVEL_CLASS_CODE ) ))) || (!Utils
									.isEmpty(beanA.getPolicyType()) && beanA.getPolicyType().toString().equals(Utils.getSingleValueAppConfig(com.Constant.CONST_WC_POLICY_TYPE)))) ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVatTaxPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getVatTaxPerc() ) ) );
  		}
		
		/* Mapping: "premiumVO.vatCode" -> "polvatCode" 142244 */
		if(  !Utils.isEmpty( beanA.getCommonVO().getPremiumVO()) && !Utils.isEmpty( beanA.getCommonVO().getPremiumVO().getVatCode() )  ){
 			beanB.setPolvatCode((beanA.getCommonVO().getPremiumVO().getVatCode() )); 

  		}else if(  !Utils.isEmpty( beanA.getPremiumVO()) && !Utils.isEmpty( beanA.getPremiumVO().getVatCode() )  ){
 			beanB.setPolvatCode((beanA.getPremiumVO().getVatCode() )); 

  		}
  		
  		 if(!Utils.isEmpty(beanA.getVatCode()) && 
 				(beanA.getPolicyClassCode().equals(Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_CLASS_CODE ) )))) {
  			beanB.setPolvatCode((beanA.getVatCode())); 
  			
  		}
		
		/* Mapping: "policyDataVO.vatCode" -> "" 142244 */
		if(  !Utils.isEmpty( beanA.getVatCode() ) && 
				(beanA.getPolicyClassCode().equals(Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_TRAVEL_CLASS_CODE ) )))   ) {			
			beanB.setPolvatCode(  beanA.getVatCode() );
  		}
		
/*		 Mapping: "premiumVO.vatTax" -> "polvatTax" 142244 
		if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getVatTax() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVatTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getVatTax() ) ) );
  		}
		
		 Mapping: "premiumVO.vatTaxPerc" -> "polvatTaxPerc" 142244 
		if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getVatTaxPerc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVatTaxPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getVatTaxPerc() ) ) );
  		}
		
		 Mapping: "premiumVO.vatCode" -> "polvatCode" 142244 
		if(  !Utils.isEmpty( beanA.getPremiumVO()) && !Utils.isEmpty( beanA.getPremiumVO().getVatCode() )  ){
 			beanB.setPolvatCode((beanA.getPremiumVO().getVatCode() )); 

  		}*/
		
 		/* Mapping: "premiumVO.policyFees" -> "polPolicyFees" */
		if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getPolicyFees() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolPolicyFees( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getPolicyFees() ) ) );
  		}
		
		// Todo : not generated from the mapper
		if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getPromoCode() )){
 			beanB.setPolPromoCode((beanA.getGeneralInfo().getSourceOfBus().getPromoCode() )); 
 		}
		
		/* Mapping: "generalInfo.sourceOfBus.sourceOfBusiness" -> "polSourceOfBusiness" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && 
				!Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getCustomerSource() ) ){
			beanB.setPolSocCode( Short.valueOf( beanA.getGeneralInfo().getSourceOfBus().getCustomerSource() ) );
		}
		
		/* Mapping: "generalInfo.claimsHistory.sourceOfBusiness" -> "polSourceOfBusiness" */
		if( !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getSourceOfBusiness() ) ){
			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setPolSourceOfBusiness( converter.getTypeOfB().cast( converter.getBFromA( beanA.getGeneralInfo().getClaimsHistory().getSourceOfBusiness() ) ) );
		}
		
		/* Mapping: "authenticationInfoVO.accountingDate" -> "setPolIssueDate" */
		if( !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getAccountingDate() ) ){
			beanB.setPolIssueDate( beanA.getAuthenticationInfoVO().getAccountingDate() );
		}

		/* Mapping: "authenticationInfoVO.accountingDate" -> "setPolQuotationDate" */
		if( !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getAccountingDate() ) ){
			beanB.setPolQuotationDate( beanA.getAuthenticationInfoVO().getAccountingDate() );
		}
		
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPolicyId() )){
 			beanB.getId().setPolicyId(beanA.getCommonVO().getPolicyId() ); 
 		}

		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getVsd() )){
 			beanB.getId().setVSD( (beanA.getCommonVO().getVsd())); 
 		}
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getDocCode() )){
 			beanB.setPolDocumentCode(  beanA.getCommonVO().getDocCode() );
 		}
		if( !Utils.isEmpty( beanA.getAuthenticationInfoVO() ) && !Utils.isEmpty( beanA.getAuthenticationInfoVO().getIntAccExecCode()  )){
 			beanB.setPolAccexecCode(beanA.getAuthenticationInfoVO().getIntAccExecCode());
 		}
		// Ticket 152096 : Code added to check and update the Int.Acc Code for Broker login user.
 		/* else if( !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getIntAccExecCode()  )){
           beanB.setPolAccexecCode(beanA.getGeneralInfo().getIntAccExecCode());
       }*/
		if( !Utils.isEmpty( beanA.getCommission() ) ){
 			beanB.setPolCommisionId( BigDecimal.valueOf( beanA.getCommission() ) );
 		}
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getLocCode())){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolLocationCode( converter.getTypeOfB().cast( converter.getBFromA ( beanA.getCommonVO().getLocCode() ) ) );
 		}
		if( !Utils.isEmpty( beanA.getPolRenTermTxt() ) ){
			beanB.setPolRenTermTxt( beanA.getPolRenTermTxt()  );
		}
		
		/* Mapping: "premiumVO.vatableAmt" -> "polvatable" 142244 */
		if(  !Utils.isEmpty( beanA.getCommonVO().getPremiumVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPremiumVO().getVatablePrm() )
				           && (beanA.getPolicyClassCode().equals(Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_HOME_CLASS_CODE ) )))   ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVattableAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCommonVO().getPremiumVO().getVatablePrm() ) ) );
  		}
		else  if(  !Utils.isEmpty( beanA.getPremiumVO() ) && !Utils.isEmpty( beanA.getPremiumVO().getVatablePrm() )  
				&& ((beanA.getPolicyClassCode().equals(Integer.valueOf(Utils.getSingleValueAppConfig(com.Constant.CONST_TRAVEL_CLASS_CODE)))) || (!Utils
						.isEmpty(beanA.getPolicyType()) && beanA.getPolicyType().toString().equals(Utils.getSingleValueAppConfig(com.Constant.CONST_WC_POLICY_TYPE))))) {
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPolVattableAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumVO().getVatablePrm() ) ) );
  		}
		// WC
		else if (!Utils.isEmpty(beanA.getCommonVO().getPremiumVO())
				&& !Utils.isEmpty(beanA.getCommonVO().getPremiumVO()
						.getVatablePrm())
				&& (beanA.getPolicyClassCode().equals(Integer.valueOf(Utils
						.getSingleValueAppConfig("WC_CLASS_CODE"))))) {
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory
					.getInstance(
							com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class,
							"", "");
			beanB.setPolVattableAmt(converter.getTypeOfA().cast(
					converter.getAFromB(beanA.getCommonVO().getPremiumVO()
							.getVatablePrm())));
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPolicyQuo initializeDeepVO( com.rsaame.pas.vo.bus.PolicyDataVO beanA, com.rsaame.pas.dao.model.TTrnPolicyQuo beanB ){
                    		BeanUtils.initializeBeanField( "id", beanB );
                                              		return beanB;
	}
}
