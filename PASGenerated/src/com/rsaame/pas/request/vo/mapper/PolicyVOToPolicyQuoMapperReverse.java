       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.sql.Timestamp;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.kaizen.quote.model.PolicyQuo</li>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyVOToPolicyQuoMapperReverse.class )</code>.
 */
public class PolicyVOToPolicyQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.kaizen.quote.model.PolicyQuo, com.rsaame.pas.vo.bus.PolicyVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyVOToPolicyQuoMapperReverse(){
		super();
	}

	public PolicyVOToPolicyQuoMapperReverse( com.rsaame.kaizen.quote.model.PolicyQuo src, com.rsaame.pas.vo.bus.PolicyVO dest ){
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
		com.rsaame.kaizen.quote.model.PolicyQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PolicyVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		


		/* Mapping: "customerInsured.engFirstName" -> "generalInfo.insured.name" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getEngFirstName() )  ){
 			beanB.getGeneralInfo().getInsured().setName( beanA.getCustomerInsured().getEngFirstName() ); 
 		}

 		/* Mapping: "cashCustomerQuo.engName1" -> "generalInfo.insured.name" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getEngName1() )  ){
 			beanB.getGeneralInfo().getInsured().setName( beanA.getCashCustomerQuo().getEngName1() ); 
 		}

 		/* Mapping: "cashCustomerQuo.arabicName1" -> "generalInfo.insured.arabicName" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getArabicName1() )  ){
 			beanB.getGeneralInfo().getInsured().setArabicName( beanA.getCashCustomerQuo().getArabicName1() ); 
 		}

 		/* Mapping: "customerInsured.arabicFirstName" -> "generalInfo.insured.arabicName" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getArabicFirstName() )  ){
 			beanB.getGeneralInfo().getInsured().setArabicName( beanA.getCustomerInsured().getArabicFirstName() ); 
 		}

 		/* Mapping: "customerInsured.engPhoneNo" -> "generalInfo.insured.phoneNo" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getEngPhoneNo() )  ){
 			beanB.getGeneralInfo().getInsured().setPhoneNo( beanA.getCustomerInsured().getEngPhoneNo() ); 
 		}

 		/* Mapping: "customerInsured.engMobileNo" -> "generalInfo.insured.mobileNo" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getEngMobileNo() )  ){
 			beanB.getGeneralInfo().getInsured().setMobileNo( beanA.getCustomerInsured().getEngMobileNo() ); 
 		}

 		/* Mapping: "cashCustomerQuo.engPhoneNo" -> "generalInfo.insured.phoneNo" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getEngPhoneNo() )  ){
 			beanB.getGeneralInfo().getInsured().setPhoneNo( beanA.getCashCustomerQuo().getEngPhoneNo() ); 
 		}

 		/* Mapping: "cashCustomerQuo.engGsmNo" -> "generalInfo.insured.mobileNo" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getEngGsmNo() )  ){
 			beanB.getGeneralInfo().getInsured().setMobileNo( beanA.getCashCustomerQuo().getEngGsmNo() ); 
 		}

 		/* Mapping: "cashCustomerQuo.engAddress1" -> "generalInfo.insured.address.address" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getEngAddress1() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setAddress( beanA.getCashCustomerQuo().getEngAddress1() ); 
 		}

 		/* Mapping: "customerInsured.engEmailId" -> "generalInfo.insured.emailId" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getEngEmailId() )  ){
 			beanB.getGeneralInfo().getInsured().setEmailId( beanA.getCustomerInsured().getEngEmailId() ); 
 		}

 		/* Mapping: "cashCustomerQuo.city.cityCode" -> "generalInfo.insured.city" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getCity() ) 
				&& !Utils.isEmpty( beanA.getCashCustomerQuo().getCity().getCityCode() )){
 			beanB.getGeneralInfo().getInsured().setCity( beanA.getCashCustomerQuo().getCity().getCityCode() ); 
 		}

 		/* Mapping: "businessType" -> "generalInfo.insured.busType" */
		if(  !Utils.isEmpty( beanA.getBusinessType() )  ){
 			beanB.getGeneralInfo().getInsured().setBusType( beanA.getBusinessType() ); 
 		}

 		/* Mapping: "cashCustomerQuo.engCoRegnNo" -> "generalInfo.insured.tradeLicenseNo" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getEngCoRegnNo() )  ){
 			beanB.getGeneralInfo().getInsured().setTradeLicenseNo( beanA.getCashCustomerQuo().getEngCoRegnNo() ); 
 		}

 		/* Mapping: "customerInsured.affinityRefNo" -> "generalInfo.additionalInfo.affinityRefNo" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getAffinityRefNo() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo( beanA.getCustomerInsured().getAffinityRefNo() ); 
 		}

 		/* Mapping: "customerInsured.faxNo" -> "generalInfo.additionalInfo.faxNumber" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getFaxNo() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setFaxNumber( beanA.getCustomerInsured().getFaxNo() ); 
 		}

 		/* Mapping: "customerInsured.dtEstablishment" -> "generalInfo.additionalInfo.dateOfEst" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getDtEstablishment() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setDateOfEst( beanA.getCustomerInsured().getDtEstablishment() ); 
 		}

 		/* Mapping: "customerInsured.placeEstablishment" -> "generalInfo.additionalInfo.placeOfEst" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getPlaceEstablishment() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setPlaceOfEst( beanA.getCustomerInsured().getPlaceEstablishment() ); 
 		}

 		/* Mapping: "cashCustomerQuo.affinityRefNo" -> "generalInfo.additionalInfo.affinityRefNo" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getAffinityRefNo() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo( beanA.getCashCustomerQuo().getAffinityRefNo() ); 
 		}

 		/* Mapping: "cashCustomerQuo.faxNo" -> "generalInfo.additionalInfo.faxNumber" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getFaxNo() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setFaxNumber( beanA.getCashCustomerQuo().getFaxNo() ); 
 		}

 		/* Mapping: "cashCustomerQuo.dtEstablishment" -> "generalInfo.additionalInfo.dateOfEst" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getDtEstablishment() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setDateOfEst( beanA.getCashCustomerQuo().getDtEstablishment() ); 
 		}

 		/* Mapping: "cashCustomerQuo.placeEstablishment" -> "generalInfo.additionalInfo.placeOfEst" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getPlaceEstablishment() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setPlaceOfEst( beanA.getCashCustomerQuo().getPlaceEstablishment() ); 
 		}

 		/* Mapping: "customerInsured.dtCollectionKyc" -> "generalInfo.additionalInfo.dateOfcollectionOfKYC" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getDtCollectionKyc() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setDateOfcollectionOfKYC( beanA.getCustomerInsured().getDtCollectionKyc() ); 
 		}

 		/* Mapping: "customerInsured.expiryDate" -> "generalInfo.additionalInfo.tradeLicenseExpDate" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getExpiryDate() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setTradeLicenseExpDate( beanA.getCustomerInsured().getExpiryDate() ); 
 		}

 		/* Mapping: "customerInsured.status" -> "generalInfo.additionalInfo.insuredStatus" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getStatus() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setInsuredStatus( beanA.getCustomerInsured().getStatus() ); 
 		}

 		/* Mapping: "customerInsured.remarks" -> "generalInfo.additionalInfo.remarks" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getRemarks() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setRemarks( beanA.getCustomerInsured().getRemarks() ); 
 		}

 		/* Mapping: "cashCustomerQuo.arabicIdCardNo" -> "generalInfo.additionalInfo.payTerms" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getArabicIdCardNo() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setPayTerms( beanA.getCashCustomerQuo().getArabicIdCardNo() );
 		}

 		/* Mapping: "reasonCode.code" -> "generalInfo.additionalInfo.specialityType" */
		if(  !Utils.isEmpty( beanA.getReasonCode() ) && !Utils.isEmpty( beanA.getReasonCode().getCode() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setSpecialityType( beanA.getReasonCode().getCode() ); 
 		}

 		/* Mapping: "coverNoteMin" -> "generalInfo.additionalInfo.rsaSpacialIndicator" */
		if(  !Utils.isEmpty( beanA.getCoverNoteMin() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setRsaSpacialIndicator( beanA.getCoverNoteMin() ); 
 		}
		/* Mapping: "pol.loc.locationCode" -> "generalInfo.additionalInfo.issueLoc" */
		if(  !Utils.isEmpty( beanA.getLocation().getCode() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setIssueLoc( beanA.getLocation().getCode() ); 
 		}
		/* Mapping: "pol.branchCode" -> "generalInfo.additionalInfo.processingLoc" */
		if(  !Utils.isEmpty( beanA.getPolicyBranchCode() )  ){
 			beanB.getGeneralInfo().getAdditionalInfo().setProcessingLoc( beanA.getPolicyBranchCode() ); 
 		}

 		/* Mapping: "distributionChannel.code" -> "generalInfo.sourceOfBus.distributionChannel" */
		if(  !Utils.isEmpty( beanA.getDistributionChannel() ) && !Utils.isEmpty( beanA.getDistributionChannel().getCode() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setDistributionChannel( beanA.getDistributionChannel().getCode() ); 
 		}

 		/* Mapping: "customerInsured.brCode" -> "generalInfo.sourceOfBus.brokerName" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getBrCode() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( beanA.getCustomerInsured().getBrCode() ); 
 		}

 		/* Mapping: "brCode" -> "generalInfo.sourceOfBus.brokerName" */
		if(  !Utils.isEmpty( beanA.getBrCode() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setBrokerName( beanA.getBrCode() ); 
 		}

 		/* Mapping: "agent.agentCode" -> "generalInfo.sourceOfBus.directSubAgent" */
		if(  !Utils.isEmpty( beanA.getAgent() ) && !Utils.isEmpty( beanA.getAgent().getAgentCode() )  ){
 			beanB.getGeneralInfo().getSourceOfBus().setDirectSubAgent( beanA.getAgent().getAgentCode() ); 
 		}

 		/* Mapping: "quotationNo" -> "quoteNo" */
		if(  !Utils.isEmpty( beanA.getQuotationNo() )  ){
 			beanB.setQuoteNo( beanA.getQuotationNo() ); 
 		}

 		/* Mapping: "comp_id.policyId" -> "policyNo" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getPolicyId() )  ){
 			beanB.setPolicyNo( beanA.getComp_id().getPolicyId() ); 
 		}

 		/* Mapping: "comp_id.endtId" -> "endtId" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getEndtId() )  ){
 			beanB.setEndtId( beanA.getComp_id().getEndtId() ); 
 		}

 		/* Mapping: "policyNo" -> "policyNo" */
		/*
		if(  !Utils.isEmpty( beanA.getPolicyNo() )  ){
 			beanB.setPolicyNo( beanA.getPolicyNo() ); 
 		}*/

 		/* Mapping: "coverNoteHour" -> "scheme.schemeCode" */
		if(  !Utils.isEmpty( beanA.getCoverNoteHour() )  ){
 			beanB.getScheme().setSchemeCode( beanA.getCoverNoteHour() ); 
 		}

 		/* Mapping: "tarCode" -> "scheme.tariffCode" */
		if(  !Utils.isEmpty( beanA.getTarCode() )  ){
 			beanB.getScheme().setTariffCode( beanA.getTarCode() ); 
 		}

 		/* Mapping: "tariffDesc" -> "scheme.tariffName" */
		if(  !Utils.isEmpty( beanA.getTariffDesc() )  ){
 			beanB.getScheme().setTariffName( beanA.getTariffDesc() ); 
 		}

 		/* Mapping: "policyTypeCode" -> "scheme.policyCode" */
		if(  !Utils.isEmpty( beanA.getPolicyTypeCode() )  ){
 			beanB.getScheme().setPolicyCode( beanA.getPolicyTypeCode() ); 
 		}

 		/* Mapping: "effectiveDate" -> "scheme.effDate" */
		if(  !Utils.isEmpty( beanA.getEffectiveDate() )  ){
 			beanB.getScheme().setEffDate( beanA.getEffectiveDate() ); 
 		}

 		/* Mapping: "expiryDate" -> "scheme.expiryDate" */
		if(  !Utils.isEmpty( beanA.getExpiryDate() )  ){
 			beanB.getScheme().setExpiryDate( beanA.getExpiryDate() ); 
 		}

 		/* Mapping: "policyTerm" -> "policyTerm" */
		if(  !Utils.isEmpty( beanA.getPolicyTerm() )  ){
 			beanB.setPolicyTerm( beanA.getPolicyTerm() ); 
 		}

 		/* Mapping: "userId" -> "authInfoVO.licensedBy" */
		if(  !Utils.isEmpty( beanA.getUserId() )  ){
 			beanB.getAuthInfoVO().setLicensedBy( beanA.getUserId() ); 
 		}

		// To avoid empty if block (Sonar defect) 12-9-2017
 		/* Mapping: "approvedBy" -> "authInfoVO.approvedBy" */
		/*if(  !Utils.isEmpty( beanA.getApprovedBy() )  ){
 			// beanB.getAuthInfoVO().setApprovedBy( beanA.getApprovedBy() ); // Commented for bugzilla no 489
 		} */
		
		// To avoid empty if block (Sonar defect) 12-9-2017
 		/* Mapping: "polApprovalDate" -> "authInfoVO.approvedDt" */
		/*if(  !Utils.isEmpty( beanA.getPolApprovalDate() )  ){
 			// beanB.getAuthInfoVO().setApprovedDt( beanA.getPolApprovalDate() );  // Commented for bugzilla no 489 
 		} */

 		/* Mapping: "location.code" -> "authInfoVO.locationCode" */
		if(  !Utils.isEmpty( beanA.getLocation() ) && !Utils.isEmpty( beanA.getLocation().getCode() )  ){
 			beanB.getAuthInfoVO().setLocationCode( beanA.getLocation().getCode() ); 
 		}

 		/* Mapping: "customerInsured.employee" -> "authInfoVO.intAccExecCode" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getEmployee() )  ){
 			beanB.getAuthInfoVO().setIntAccExecCode( beanA.getCustomerInsured().getEmployee() ); 
 		}

 		/* Mapping: "cashCustomerQuo.intAccExecCode" -> "authInfoVO.intAccExecCode" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getIntAccExecCode() )  ){
 			beanB.getAuthInfoVO().setIntAccExecCode( beanA.getCashCustomerQuo().getIntAccExecCode() ); 
 		}

 		/* Mapping: "customerInsured.extAccExecCode" -> "authInfoVO.extAccExecCode" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getExtAccExecCode() )  ){
 			beanB.getAuthInfoVO().setExtAccExecCode( beanA.getCustomerInsured().getExtAccExecCode() ); 
 		}

 		/* Mapping: "cashCustomerQuo.extAccExecCode" -> "authInfoVO.extAccExecCode" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getExtAccExecCode() )  ){
 			beanB.getAuthInfoVO().setExtAccExecCode( beanA.getCashCustomerQuo().getExtAccExecCode() ); 
 		}

 		/* Mapping: "cashCustomerQuo.nationality" -> "generalInfo.insured.nationality" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getNationality() )  ){
 			beanB.getGeneralInfo().getInsured().setNationality( beanA.getCashCustomerQuo().getNationality() ); 
 		}

 		/* Mapping: "insuredId" -> "generalInfo.insured.insuredId" */
		if(  !Utils.isEmpty( beanA.getInsuredId() )  ){
 			beanB.getGeneralInfo().getInsured().setInsuredId( beanA.getInsuredId() ); 
 		}

 		/* Mapping: "isChannelChanged" -> "generalInfo.isChannelChanged" */
		if(  !Utils.isEmpty( beanA.getIsChannelChanged() )  ){
 			beanB.getGeneralInfo().setIsChannelChanged( beanA.getIsChannelChanged() ); 
 		}

 		/* Mapping: "newCustomer" -> "generalInfo.newCustomer" */
		if(  !Utils.isEmpty( beanA.getNewCustomer() )  ){
 			beanB.getGeneralInfo().setNewCustomer( beanA.getNewCustomer() ); 
 		}

 		/* Mapping: "customerSaveReq" -> "generalInfo.customerSaveReq" */
		if(  !Utils.isEmpty( beanA.getCustomerSaveReq() )  ){
 			beanB.getGeneralInfo().setCustomerSaveReq( beanA.getCustomerSaveReq() ); 
 		}

 		/* Mapping: "matchExists" -> "generalInfo.insured.matchExists" */
		if(  !Utils.isEmpty( beanA.getMatchExists() )  ){
 			beanB.getGeneralInfo().getInsured().setMatchExists( beanA.getMatchExists() ); 
 		}

 		/* Mapping: "cashCustomerQuo.poBox" -> "generalInfo.insured.address.poBox" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getPoBox() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( beanA.getCashCustomerQuo().getPoBox() ); 
 		}

 		/* Mapping: "customerInsured.engZipCode" -> "generalInfo.insured.address.poBox" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getEngZipCode() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( beanA.getCustomerInsured().getEngZipCode() ); 
 		}

 		/* Mapping: "classCode" -> "defaultClassCode" */
		if(  !Utils.isEmpty( beanA.getClassCode() )  ){
 			beanB.setDefaultClassCode( beanA.getClassCode() ); 
 		}

 		/* Mapping: "sourceOfBusiness" -> "generalInfo.claimsHistory.sourceOfBusiness" */
		if(  !Utils.isEmpty( beanA.getSourceOfBusiness() )  ){
 			beanB.getGeneralInfo().getClaimsHistory().setSourceOfBusiness( beanA.getSourceOfBusiness() ); 
 		}
		
		
		/* Manually Added */
		
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty(beanA.getCashCustomerQuo().getComp_id())){
			/** Changed the approach of getting validityStartDate from CashCustomerQuo to PolicyQuo as multiple entries
			 * issue for CashCustomerQuo still persists */
 	 		//beanB.setValidityStartDate(beanA.getCashCustomerQuo().getComp_id().getValidityStartDate());
			beanB.setValidityStartDate(beanA.getValidityStartDate());
			beanB.setNewValidityStartDate( beanA.getValidityStartDate() );
 		}


 		/* Mapping: "preparedBy" -> "authInfoVO.createdBy" */
		if(  !Utils.isEmpty( beanA.getPreparedBy() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthInfoVO().setCreatedBy( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPreparedBy() ) ) );
  		}

 		/* Mapping: "preparedDt" -> "authInfoVO.createdOn" */
		if(  !Utils.isEmpty( beanA.getPreparedDt() )  ){
			
 			beanB.getAuthInfoVO().setCreatedOn( new Timestamp(beanA.getPreparedDt().getTime())); 
 		}

 		/* Mapping: "status.code" -> "status" */
		if(  !Utils.isEmpty( beanA.getStatus() ) && !Utils.isEmpty( beanA.getStatus().getCode() )  ){
 			beanB.setStatus( beanA.getStatus().getCode() ); 
 		}

 		/* Mapping: "customerInsured.business" -> "generalInfo.insured.busDescription" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() )){
			beanB.getGeneralInfo().getInsured().setBusDescription(beanA.getCustomerInsured().getBusiness());
		}
		

 		/* Mapping: "refPolicyNo" -> "authInfoVO.refPolicyNo" */
		if(  !Utils.isEmpty( beanA.getRefPolicyNo() )  ){
 			beanB.getAuthInfoVO().setRefPolicyNo( beanA.getRefPolicyNo() ); 
 		}

 		/* Mapping: "refPolicyYear" -> "authInfoVO.refPolicyYear" */
		if(  !Utils.isEmpty( beanA.getRefPolicyYear() )  ){
 			beanB.getAuthInfoVO().setRefPolicyYear( beanA.getRefPolicyYear() ); 
 		}
		

		/* Mapping: "renewalTerms" -> "authInfoVO.renewalTerms" */
		if(  !Utils.isEmpty( beanA.getPolRenTermTxt() )  ){
 			beanB.getAuthInfoVO().setRenewalTerms( beanA.getPolRenTermTxt() ); 
 		} 
						
		/* Mapping: "customerInsured.country.code" -> "generalInfo.insured.address.country" */
		if(  !Utils.isEmpty( beanA.getCustomerInsured() ) && !Utils.isEmpty( beanA.getCustomerInsured().getCountry() ) && !Utils.isEmpty( beanA.getCustomerInsured().getCountry().getCode() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setCountry( beanA.getCustomerInsured().getCountry().getCode() ); 
 		}

 		/* Mapping: "cashCustomerQuo.country" -> "generalInfo.insured.address.country" */
		if(  !Utils.isEmpty( beanA.getCashCustomerQuo() ) && !Utils.isEmpty( beanA.getCashCustomerQuo().getCountry() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setCountry( beanA.getCashCustomerQuo().getCountry() ); 
 		}
		
		

 		/* Mapping: "validityStartDate" -> "validityStartDate" */
		/*
		if(  !Utils.isEmpty( beanA.getValidityStartDate() )  ){
 			beanB.setValidityStartDate( beanA.getValidityStartDate() ); 
 		}*/
		
		if(  !Utils.isEmpty( beanA.getIssueDate() ) ){
 			beanB.setStartDate(beanA.getIssueDate()); 
 		}
		
		if(  !Utils.isEmpty( beanA.getExpiryDate() ) ){
 			beanB.setEndDate(beanA.getExpiryDate());
 		}
		
		if(  !Utils.isEmpty( beanA.getEndtEffectiveDate() ) ){
 			beanB.setEndEffectiveDate( beanA.getEndtEffectiveDate() );
 		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyVO initializeDeepVO( com.rsaame.kaizen.quote.model.PolicyQuo beanA, com.rsaame.pas.vo.bus.PolicyVO beanB ){
  		BeanUtils.initializeBeanField( "generalInfo.insured", beanB );
                 		BeanUtils.initializeBeanField( "generalInfo.insured.address", beanB );
             		BeanUtils.initializeBeanField( "generalInfo.additionalInfo", beanB );
                               		BeanUtils.initializeBeanField( "generalInfo.sourceOfBus", beanB );
                 		BeanUtils.initializeBeanField( "scheme", beanB );
               		BeanUtils.initializeBeanField( "authInfoVO", beanB );
                     		BeanUtils.initializeBeanField( "generalInfo", beanB );
               		BeanUtils.initializeBeanField( "generalInfo.claimsHistory", beanB );
  		return beanB;
	}
}
