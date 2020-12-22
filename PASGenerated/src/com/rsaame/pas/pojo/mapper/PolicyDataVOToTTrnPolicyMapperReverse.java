       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import java.sql.Timestamp;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * <li>com.rsaame.pas.vo.bus.PolicyDataVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyDataVOToTTrnPolicyMapperReverse.class )</code>.
 */
public class PolicyDataVOToTTrnPolicyMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPolicyQuo, com.rsaame.pas.vo.bus.PolicyDataVO>{
	@SuppressWarnings( "unused" )
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyDataVOToTTrnPolicyMapperReverse(){
		super();
	}

	public PolicyDataVOToTTrnPolicyMapperReverse( com.rsaame.pas.dao.model.TTrnPolicyQuo src, com.rsaame.pas.vo.bus.PolicyDataVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PolicyDataVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PolicyDataVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PolicyDataVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PolicyDataVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		@SuppressWarnings( "unused" )
		int noOfItems = 0;
		
		/* Mapping: "beanA.getId().getVSD()" -> "polExchangeRate" */
		if(  !Utils.isEmpty( beanA.getPolValidityStartDate() )  ){
 			beanB.setValidityStartDate( beanA.getPolValidityStartDate() );
 		}
		
		/* Mapping: "polBusinessType" -> "generalInfo.insured.busType" */
		if(  !Utils.isEmpty( beanA.getPolBusinessType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setBusType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolBusinessType() ) ) );
  		}
		
		/* Mapping: "polBusinessType" -> "generalInfo.insured.PolbusType" */
		if(  !Utils.isEmpty( beanA.getPolBusinessType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setPolBusType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolBusinessType() ) ) );
  		}

 		/* Mapping: "polCoverNoteMin" -> "generalInfo.additionalInfo.rsaSpacialIndicator" */
		if(  !Utils.isEmpty( beanA.getPolCoverNoteMin() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.getGeneralInfo().getAdditionalInfo().setRsaSpacialIndicator( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolCoverNoteMin() ) ) );
  		}
		
		/* Mapping: "polExchangeRate" -> "polExchangeRate" */
		if(  !Utils.isEmpty( beanA.getPolExchangeRate() )  ){
 			beanB.setPolExchangeRate( beanA.getPolExchangeRate() );
 		}
		
		/* Mapping: "polProcessedDate" -> "processedDate"*/
		if( !Utils.isEmpty( beanA.getPolProcessedDate() )){
			beanB.setProcessedDate( beanA.getPolProcessedDate() );
		}

 		/* Mapping: "polProcLocCode" -> "generalInfo.additionalInfo.issueLoc" */
		if(  !Utils.isEmpty( beanA.getPolProcLocCode() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setIssueLoc( beanA.getPolProcLocCode() ); 
 		}

 		/* Mapping: "polProcLocCode" -> "generalInfo.additionalInfo.processingLoc" */
		if(  !Utils.isEmpty( beanA.getPolProcLocCode() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setProcessingLoc( beanA.getPolProcLocCode() ); 
 		}

 		/* Mapping: "polDistributionChnl" -> "generalInfo.sourceOfBus.distributionChannel" */
		if(  !Utils.isEmpty( beanA.getPolDistributionChnl() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setDistributionChannel( beanA.getPolDistributionChnl() ); 
 		}
		
		
		if(  !Utils.isEmpty( beanA.getPolSocCode() ) ) {
 			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setCustomerSource( converter.getTypeOfB().cast( converter.getBFromA(beanA.getPolSocCode()) ) ); 
		}

 		/* Mapping: "polBrCode" -> "generalInfo.sourceOfBus.brokerName" */
		if(  !Utils.isEmpty( beanA.getPolBrCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolBrCode() ) ) );
  		}

 		/* Mapping: "polAgentId" -> "generalInfo.sourceOfBus.directSubAgent" */
		if(  !Utils.isEmpty( beanA.getPolAgentId() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( beanA.getPolAgentId() ); 
 		}

 		/* Mapping: "polQuotationNo" -> "quoteNo" */
		if(  !Utils.isEmpty( beanA.getPolQuotationNo() )  ){
 			beanB.setQuoteNo( beanA.getPolQuotationNo() ); 
 		}

 		/* Mapping: "polPolicyNo" -> "policyNo" */
		if(  !Utils.isEmpty( beanA.getPolPolicyNo() )  ){
 			beanB.setPolicyNo( beanA.getPolPolicyNo() ); 
 		}

 		/* Mapping: "id.polEndtId" -> "endtId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolEndtId() )  ){
 			beanB.setEndtId( beanA.getId().getPolEndtId() ); 
 		}
		
		/* Mapping: "id.polPolicyId" -> "policyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolPolicyId() )  ){
 			beanB.setPolicyId( beanA.getId().getPolPolicyId() ); 
 		}

 		/* Mapping: "polCoverNoteHour" -> "scheme.schemeCode" */
		if(  !Utils.isEmpty( beanA.getPolCoverNoteHour() )  ){
 			beanB.getScheme().setSchemeCode( beanA.getPolCoverNoteHour() ); 
 		}

 		/* Mapping: "polTarCode" -> "scheme.tariffCode" */
		if(  !Utils.isEmpty( beanA.getPolTarCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getScheme().setTariffCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolTarCode() ) ) );
  		}

 		/* Mapping: "polPolicyType" -> "scheme.policyCode" */
		if(  !Utils.isEmpty( beanA.getPolPolicyType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getScheme().setPolicyCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolPolicyType() ) ) );
  		}
		
		/* Mapping: "polPolicyType" -> "policyType" */
		if(  !Utils.isEmpty( beanA.getPolPolicyType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolicyType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolPolicyType() ) ) );
  		}

 		/* Mapping: "polEffectiveDate" -> "scheme.effDate" */
		if(  !Utils.isEmpty( beanA.getPolEffectiveDate() )  ){
 			beanB.getScheme().setEffDate( beanA.getPolEffectiveDate() ); 
 		}

 		/* Mapping: "polExpiryDate" -> "scheme.expiryDate" */
		if(  !Utils.isEmpty( beanA.getPolExpiryDate() )  ){
 			beanB.getScheme().setExpiryDate( beanA.getPolExpiryDate() ); 
 		}
		
		

 		/* Mapping: "polPolicyTerm" -> "policyTerm" */
		if(  !Utils.isEmpty( beanA.getPolPolicyTerm() )  ){
 			beanB.setPolicyTerm( beanA.getPolPolicyTerm() ); 
 		}

 		/* Mapping: "polUserId" -> "authenticationInfoVO.licensedBy" */
		if(  !Utils.isEmpty( beanA.getPolUserId() )  ){
 			beanB.getAuthenticationInfoVO().setLicensedBy( beanA.getPolUserId() ); 
 		}

 		/* Mapping: "polApprovedBy" -> "authenticationInfoVO.approvedBy" */
		if(  !Utils.isEmpty( beanA.getPolApprovedBy() )  ){
 			beanB.getAuthenticationInfoVO().setApprovedBy( beanA.getPolApprovedBy() ); 
 		}

 		/* Mapping: "polApprovalDate" -> "authenticationInfoVO.approvedDt" */
		if(  !Utils.isEmpty( beanA.getPolApprovalDate() )  ){
 			beanB.getAuthenticationInfoVO().setApprovedDt( beanA.getPolApprovalDate() ); 
 		}
 		/* Mapping: "polLocationCode" -> "authenticationInfoVO.locationCode" */
		if(  !Utils.isEmpty( beanA.getPolLocationCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setLocationCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolLocationCode() ) ) );
  		}

 		/* Mapping: "polInsuredId" -> "generalInfo.insured.insuredId" */
		if( !Utils.isEmpty( beanA.getPolInsuredId() )){
			beanB.getGeneralInfo().getInsured().setInsuredId( beanA.getPolInsuredId() );
		}
		
		/* Mapping: "polInsuredCode -> generalInfo.insured.insuredCode */
		if(  !Utils.isEmpty( beanA.getPolInsuredCode() )  ){
 			beanB.getGeneralInfo().getInsured().setInsuredCode( beanA.getPolInsuredCode()); 
 		}
		
		/* 142244 Mapping: "polVatRegNo -> generalInfo.insured.vatRegNo */
		if(  !Utils.isEmpty( beanA.getPolInsuredCode() )  ){
 			beanB.getGeneralInfo().getInsured().setVatRegNo( beanA.getPolVatRegNo()); 
 		}

		 /* Mapping: "polClassCode" -> "policyClassCode" */
		if(  !Utils.isEmpty( beanA.getPolClassCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolicyClassCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolClassCode() ) ) );
  		}

 		/* Mapping: "polSourceOfBusiness" -> "generalInfo.claimsHistory.sourceOfBusiness" */
		if(  !Utils.isEmpty( beanA.getPolSourceOfBusiness() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setSourceOfBusiness( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolSourceOfBusiness() ) ) );
  		}

 		/* Mapping: "polPreparedBy" -> "authenticationInfoVO.createdBy" */
		if(  !Utils.isEmpty( beanA.getPolPreparedBy() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setCreatedBy( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolPreparedBy() ) ) );
  		}

 		/* Mapping: "polPreparedDt" -> "authenticationInfoVO.createdOn" */
		if(  !Utils.isEmpty( beanA.getPolPreparedDt() )  ){
 			beanB.getAuthenticationInfoVO().setCreatedOn( new Timestamp( beanA.getPolPreparedDt().getTime() )); 
 		}

 		/* Mapping: "polStatus" -> "status" */
		if(  !Utils.isEmpty( beanA.getPolStatus() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolStatus() ) ) );
  		}

 		/* Mapping: "polRefPolicyNo" -> "authenticationInfoVO.refPolicyNo" */
		if(  !Utils.isEmpty( beanA.getPolRefPolicyNo() )  ){
 			beanB.getAuthenticationInfoVO().setRefPolicyNo( beanA.getPolRefPolicyNo() ); 
 		}

		/* Mapping: "polRefPolicyId" -> "authenticationInfoVO.refPolicyId" */
		if(  !Utils.isEmpty( beanA.getPolRefPolicyId() )  ){
 			beanB.getAuthenticationInfoVO().setRefPolicyId( beanA.getPolRefPolicyId() ); 
 		}		

 		/* Mapping: "polRefPolicyYear" -> "authenticationInfoVO.refPolicyYear" */
		if(  !Utils.isEmpty( beanA.getPolRefPolicyYear() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setRefPolicyYear( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolRefPolicyYear() ) ) );
  		}
		
		/* Mapping: "polConcPolicyNo" -> "commonVO.concatPolicyNo" */
		if(  !Utils.isEmpty( beanA.getPolConcPolicyNo() )  ){
			beanB.getCommonVO().setConcatPolicyNo(beanA.getPolConcPolicyNo() ); 
		}

 		/* Mapping: "polEndtEffectiveDate" -> "endEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getPolEndtEffectiveDate() )  ){
 			beanB.setEndEffectiveDate( beanA.getPolEndtEffectiveDate() ); 
 		}
		
		/* Mapping: "polEndtExpiryDate" -> "polExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPolEndtExpiryDate() )  ){
 			beanB.setPolExpiryDate( beanA.getPolEndtExpiryDate() ); 
 		}

 		/* Mapping: "polPremium" -> "premiumVO.premiumAmt" */
		if(  !Utils.isEmpty( beanA.getPolPremium() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremiumVO().setPremiumAmt( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolPremium() ) ) );
  		}

 		/* Mapping: "polGovernmentTax" -> "premiumVO.govtTax" */
		if(  !Utils.isEmpty( beanA.getPolGovernmentTax() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremiumVO().setGovtTax( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolGovernmentTax() ) ) );
  		}
		
		/* Mapping: "polVatTax" -> "premiumVO.vatTax" 142244 */
		if(  !Utils.isEmpty( beanA.getPolVatTax() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremiumVO().setVatTax( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolVatTax() ) ) );
  		}
		
		/* Mapping: "polVatTaxPerc" -> "premiumVO.vatTaxPerc" 142244 */
		if(  !Utils.isEmpty( beanA.getPolVatTaxPerc() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremiumVO().setVatTaxPerc( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolVatTaxPerc() ) ) );
  		}
		
		/* Mapping: "polVatCode" -> "premiumVO.vatCode" 142244 */
		if(  !Utils.isEmpty( beanA.getPolvatCode() )  ){
			beanB.getPremiumVO().setVatCode( beanA.getPolvatCode() ); 
  		}

		if(  !Utils.isEmpty( beanA.getPolvatCode() )  ){
			beanB.setVatCode( beanA.getPolvatCode() ); 
  		}
		
 		/* Mapping: "polPolicyFees" -> "premiumVO.policyFees" */
		if(  !Utils.isEmpty( beanA.getPolPolicyFees() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremiumVO().setPolicyFees( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolPolicyFees() ) ) );
  		}

		// Todo : not generated from the mapper
		if( !Utils.isEmpty( beanA.getPolPromoCode() )){
 			beanB.getGeneralInfo().getSourceOfBus().setPromoCode( beanA.getPolPromoCode() ); 
 		}
		if( !Utils.isEmpty( beanA.getPolValidityStartDate() )){
 			beanB.getCommonVO().setVsd( beanA.getPolValidityStartDate() ); 
 		}
		
		/* Mapping: "polSourceOfBusiness" -> "generalInfo.claimsHistory.sourceOfBusiness" */
		if( !Utils.isEmpty( beanA.getPolSourceOfBusiness() ) ){
			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.getGeneralInfo().getClaimsHistory().setSourceOfBusiness( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolSourceOfBusiness() ) ) );
		}
		
		/* Mapping: "polQuotationDate" -> "authenticationInfoVO.accountingDate" */
		if( !Utils.isEmpty( beanA.getPolQuotationDate() ) ){
			beanB.getAuthenticationInfoVO().setAccountingDate( beanA.getPolQuotationDate() );
		}
		
		/* Mapping: "polIssueDate" -> "authenticationInfoVO.accountingDate" */
		if( !Utils.isEmpty( beanA.getPolIssueDate() ) ){
			beanB.getAuthenticationInfoVO().setAccountingDate( beanA.getPolIssueDate() );
		}

		/* Mapping: "polDocumentCode" -> "authenticationInfoVO.txnType" */
		if( !Utils.isEmpty( beanA.getPolDocumentCode() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setTxnType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolDocumentCode() ) ) );
		}
		/* Mapping: "polIssueDate" -> "authenticationInfoVO.accountingDate" */
		if( !Utils.isEmpty( beanA.getPolPrintDate() ) ){
			beanB.getAuthenticationInfoVO().setPrintedDate( beanA.getPolPrintDate() );
		}
		
		/* Mapping: "polAccexecCode" -> "authenticationInfoVO.intAccExecCode" */
		if( !Utils.isEmpty( beanA.getPolAccexecCode() )){
			beanB.getAuthenticationInfoVO().setIntAccExecCode(beanA.getPolAccexecCode());
		}
		
		if( !Utils.isEmpty( beanA.getPolCommisionId() ) ){
			beanB.setCommission( beanA.getPolCommisionId().doubleValue()  );
		}
		if( !Utils.isEmpty( beanA.getPolRenTermTxt() ) ){
			beanB.setPolRenTermTxt( beanA.getPolRenTermTxt()  );
		}
		
		/* Mapping: "polvatablePremium" -> "premiumVO.vatablePrm" */
		if(  !Utils.isEmpty( beanA.getPolVattableAmt() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremiumVO().setVatablePrm( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolVattableAmt() ) ) );
  		}
		
		/* Mapping: "polviewPremium" -> "premiumVO.ViewAmt" */
		if(  !Utils.isEmpty( beanA.getPolVatTax() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getPremiumVO().setViewVatAmount( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPolVatTax() ) ) );
  		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyDataVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnPolicyQuo beanA, com.rsaame.pas.vo.bus.PolicyDataVO beanB ){
  		BeanUtils.initializeBeanField( "generalInfo.insured", beanB );
   		BeanUtils.initializeBeanField( "generalInfo.additionalInfo", beanB );
       		BeanUtils.initializeBeanField( "generalInfo.sourceOfBus", beanB );
             		BeanUtils.initializeBeanField( "scheme", beanB );
             		BeanUtils.initializeBeanField( "authenticationInfoVO", beanB );
             		BeanUtils.initializeBeanField( "generalInfo.claimsHistory", beanB );
               		BeanUtils.initializeBeanField( "premiumVO", beanB );
               		BeanUtils.initializeBeanField( "commonVO", beanB );
      		return beanB;
	}
}
