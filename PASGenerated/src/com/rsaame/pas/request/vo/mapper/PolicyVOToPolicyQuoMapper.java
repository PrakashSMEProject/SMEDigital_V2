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
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.vo.bus.SectionVO;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.PolicyVO</li>
 * <li>com.rsaame.kaizen.quote.model.PolicyQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyVOToPolicyQuoMapper.class )</code>.
 */
public class PolicyVOToPolicyQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PolicyVO, com.rsaame.kaizen.quote.model.PolicyQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyVOToPolicyQuoMapper(){
		super();
	}

	public PolicyVOToPolicyQuoMapper( com.rsaame.pas.vo.bus.PolicyVO src, com.rsaame.kaizen.quote.model.PolicyQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.quote.model.PolicyQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.quote.model.PolicyQuo) Utils.newInstance( "com.rsaame.kaizen.quote.model.PolicyQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PolicyVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.quote.model.PolicyQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "generalInfo.insured.name" -> "customerInsured.engFirstName" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() )  ){
 			beanB.getCustomerInsured().setEngFirstName( beanA.getGeneralInfo().getInsured().getName() ); 
 		}

 		/* Mapping: "generalInfo.insured.name" -> "cashCustomerQuo.engName1" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getName() )  ){
 			beanB.getCashCustomerQuo().setEngName1( beanA.getGeneralInfo().getInsured().getName() ); 
 		}

 		/* Mapping: "generalInfo.insured.arabicName" -> "cashCustomerQuo.arabicName1" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getArabicName() )  ){
 			beanB.getCashCustomerQuo().setArabicName1( beanA.getGeneralInfo().getInsured().getArabicName() ); 
 		}

 		/* Mapping: "generalInfo.insured.arabicName" -> "customerInsured.arabicFirstName" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getArabicName() )  ){
 			beanB.getCustomerInsured().setArabicFirstName( beanA.getGeneralInfo().getInsured().getArabicName() ); 
 		}

 		/* Mapping: "generalInfo.insured.phoneNo" -> "customerInsured.engPhoneNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getPhoneNo() )  ){
 			beanB.getCustomerInsured().setEngPhoneNo( beanA.getGeneralInfo().getInsured().getPhoneNo() ); 
 		}

 		/* Mapping: "generalInfo.insured.mobileNo" -> "customerInsured.engMobileNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getMobileNo() )  ){
 			beanB.getCustomerInsured().setEngMobileNo( beanA.getGeneralInfo().getInsured().getMobileNo() ); 
 		}

 		/* Mapping: "generalInfo.insured.phoneNo" -> "cashCustomerQuo.engPhoneNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getPhoneNo() )  ){
 			beanB.getCashCustomerQuo().setEngPhoneNo( beanA.getGeneralInfo().getInsured().getPhoneNo() ); 
 		}

 		/* Mapping: "generalInfo.insured.mobileNo" -> "cashCustomerQuo.engGsmNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getMobileNo() )  ){
 			beanB.getCashCustomerQuo().setEngGsmNo( beanA.getGeneralInfo().getInsured().getMobileNo() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.address" -> "cashCustomerQuo.engAddress1" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getAddress() )  ){
 			beanB.getCashCustomerQuo().setEngAddress1( beanA.getGeneralInfo().getInsured().getAddress().getAddress() ); 
 		}

 		/* Mapping: "generalInfo.insured.emailId" -> "customerInsured.engEmailId" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getEmailId() )  ){
 			beanB.getCustomerInsured().setEngEmailId( beanA.getGeneralInfo().getInsured().getEmailId() ); 
 		}

		/* Mapping: "generalInfo.insured.city" -> "cashCustomerQuo.city.cityCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getCity() )  ){
 			beanB.getCashCustomerQuo().getCity().setCityCode( beanA.getGeneralInfo().getInsured().getCity() ); 
 		}
		
		
 		/* Mapping: "generalInfo.insured.busType" -> "businessType" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getBusType() )  ){
 			beanB.setBusinessType( beanA.getGeneralInfo().getInsured().getBusType() ); 
 		}

 		/* Mapping: "generalInfo.insured.tradeLicenseNo" -> "customerInsured.engCoRegnNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() )  ){
 			beanB.getCustomerInsured().setEngCoRegnNo( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() ); 
 		}

 		/* Mapping: "generalInfo.insured.tradeLicenseNo" -> "cashCustomerQuo.engCoRegnNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() )  ){
 			beanB.getCashCustomerQuo().setEngCoRegnNo( beanA.getGeneralInfo().getInsured().getTradeLicenseNo() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.affinityRefNo" -> "customerInsured.affinityRefNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() )  ){
 			beanB.getCustomerInsured().setAffinityRefNo( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.faxNumber" -> "customerInsured.faxNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() )  ){
 			beanB.getCustomerInsured().setFaxNo( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.dateOfEst" -> "customerInsured.dtEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() )  ){
 			beanB.getCustomerInsured().setDtEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.placeOfEst" -> "customerInsured.placeEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() )  ){
 			beanB.getCustomerInsured().setPlaceEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.affinityRefNo" -> "cashCustomerQuo.affinityRefNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() )  ){
 			beanB.getCashCustomerQuo().setAffinityRefNo( beanA.getGeneralInfo().getAdditionalInfo().getAffinityRefNo() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.faxNumber" -> "cashCustomerQuo.faxNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() )  ){
 			beanB.getCashCustomerQuo().setFaxNo( beanA.getGeneralInfo().getAdditionalInfo().getFaxNumber() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.dateOfEst" -> "cashCustomerQuo.dtEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() )  ){
 			beanB.getCashCustomerQuo().setDtEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getDateOfEst() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.placeOfEst" -> "cashCustomerQuo.placeEstablishment" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() )  ){
 			beanB.getCashCustomerQuo().setPlaceEstablishment( beanA.getGeneralInfo().getAdditionalInfo().getPlaceOfEst() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.dateOfcollectionOfKYC" -> "customerInsured.dtCollectionKyc" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getDateOfcollectionOfKYC() )  ){
 			beanB.getCustomerInsured().setDtCollectionKyc( beanA.getGeneralInfo().getAdditionalInfo().getDateOfcollectionOfKYC() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.tradeLicenseExpDate" -> "customerInsured.expiryDate" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getTradeLicenseExpDate() )  ){
 			beanB.getCustomerInsured().setExpiryDate( beanA.getGeneralInfo().getAdditionalInfo().getTradeLicenseExpDate() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.insuredStatus" -> "customerInsured.status" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getInsuredStatus() )  ){
 			beanB.getCustomerInsured().setStatus( beanA.getGeneralInfo().getAdditionalInfo().getInsuredStatus() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.remarks" -> "customerInsured.remarks" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() )  ){
 			beanB.getCustomerInsured().setRemarks( beanA.getGeneralInfo().getAdditionalInfo().getRemarks() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.payTerms" -> "cashCustomerQuo.arabicIdCardNo" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getPayTerms() )  ){
 			beanB.getCashCustomerQuo().setArabicIdCardNo( beanA.getGeneralInfo().getAdditionalInfo().getPayTerms() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.specialityType" -> "reasonCode.code" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getSpecialityType() )  ){
 			beanB.getReasonCode().setCode( beanA.getGeneralInfo().getAdditionalInfo().getSpecialityType() ); 
 		}

 		/* Mapping: "generalInfo.additionalInfo.rsaSpacialIndicator" -> "coverNoteMin" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getRsaSpacialIndicator() )  ){
 			beanB.setCoverNoteMin( beanA.getGeneralInfo().getAdditionalInfo().getRsaSpacialIndicator() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.distributionChannel" -> "distributionChannel.code" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() )  ){
			/* Distribution object is initialized only if distribution channel option is available on the screen. Initialization of Distribution
			 * object leads to hibernate exception in case no value present for distributionChannel is set which is the case with 
			 * broker login */
			BeanUtils.initializeBeanField( "distributionChannel", beanB );
			beanB.getDistributionChannel().setCode( beanA.getGeneralInfo().getSourceOfBus().getDistributionChannel() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.brokerName" -> "customerInsured.brCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) ){
 			beanB.getCustomerInsured().setBrCode( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.brokerName" -> "brCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() )  ){
 			beanB.setBrCode( beanA.getGeneralInfo().getSourceOfBus().getBrokerName() ); 
 		}

 		/* Mapping: "generalInfo.sourceOfBus.directSubAgent" -> "agent.agentCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus() ) && !Utils.isEmpty( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() )  ){
 			//beanB.getAgent().setAgentCode( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() );
				if(beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() != null && beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() != -9999){
						BeanUtils.initializeBeanField( "agent", beanB );
						beanB.getAgent().setAgentCode( beanA.getGeneralInfo().getSourceOfBus().getDirectSubAgent() ); 
				}
 		}

 		/* Mapping: "quoteNo" -> "quotationNo" */
		if(  !Utils.isEmpty( beanA.getQuoteNo() )  ){
 			beanB.setQuotationNo( beanA.getQuoteNo() ); 
 		}

 		/* Mapping: "policyNo" -> "comp_id.policyId" */
		if(  !Utils.isEmpty( beanA.getPolicyNo() )  ){
 			beanB.getComp_id().setPolicyId( beanA.getPolicyNo() ); 
 		}

 		/* Mapping: "endtId" -> "comp_id.endtId" */
		if(  !Utils.isEmpty( beanA.getEndtId() )  ){
 			beanB.getComp_id().setEndtId( beanA.getEndtId() ); 
 		}

 		
		/* Mapping: "policyNo" -> "policyNo" */
		/*
		if(  !Utils.isEmpty( beanA.getPolicyNo() )  ){
 			beanB.setPolicyNo( beanA.getPolicyNo() ); 
 		}*/

 		/* Mapping: "scheme.schemeCode" -> "coverNoteHour" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getSchemeCode() )  ){
 			beanB.setCoverNoteHour( beanA.getScheme().getSchemeCode() ); 
 		}

 		/* Mapping: "scheme.tariffCode" -> "tarCode" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getTariffCode() )  ){
 			beanB.setTarCode( beanA.getScheme().getTariffCode() ); 
 		}

 		/* Mapping: "scheme.tariffName" -> "tariffDesc" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getTariffName() )  ){
 			beanB.setTariffDesc( beanA.getScheme().getTariffName() ); 
 		}

 		/* Mapping: "scheme.policyCode" -> "policyTypeCode" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getPolicyCode() )  ){
 			beanB.setPolicyTypeCode( beanA.getScheme().getPolicyCode() ); 
 		}

 		/* Mapping: "scheme.effDate" -> "effectiveDate" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getEffDate() )  ){
 			beanB.setEffectiveDate( beanA.getScheme().getEffDate() ); 
 		}

 		/* Mapping: "scheme.expiryDate" -> "expiryDate" */
		if(  !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getExpiryDate() )  ){
 			beanB.setExpiryDate( beanA.getScheme().getExpiryDate() ); 
 		}

 		/* Mapping: "policyTerm" -> "policyTerm" */
		if(  !Utils.isEmpty( beanA.getPolicyTerm() )  ){
 			beanB.setPolicyTerm( beanA.getPolicyTerm() ); 
 		}

 		/* Mapping: "authInfoVO.licensedBy" -> "userId" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getLicensedBy() )  ){
 			beanB.setUserId( beanA.getAuthInfoVO().getLicensedBy() ); 
 		}

		
		// To avoid empty if block (Sonar defect) 12-9-2017
 		/* Mapping: "authInfoVO.approvedBy" -> "approvedBy" */
		/*if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getApprovedBy() )  ){
 		//	beanB.setApprovedBy( beanA.getAuthInfoVO().getApprovedBy() ); //Comment for kaizen fgbpm invocation
 		}*/

		// To avoid empty if block (Sonar defect) 12-9-2017
 		/* Mapping: "authInfoVO.approvedDt" -> "polApprovalDate" */
		/*if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getApprovedDt() )  ){
 		//	beanB.setPolApprovalDate( beanA.getAuthInfoVO().getApprovedDt() );  //Comment for kaizen fgbpm invocation
 		} */
		
		

 		/* Mapping: "authInfoVO.locationCode" -> "location.code" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getLocationCode() )  ){
 			beanB.getLocation().setCode( beanA.getAuthInfoVO().getLocationCode() ); 
 		}

 		/* Mapping: "authInfoVO.intAccExecCode" -> "customerInsured.employee" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getIntAccExecCode() )  ){
 			beanB.getCustomerInsured().setEmployee( beanA.getAuthInfoVO().getIntAccExecCode() ); 
 		}

 		/* Mapping: "authInfoVO.intAccExecCode" -> "cashCustomerQuo.intAccExecCode" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getIntAccExecCode() )  ){
 			beanB.getCashCustomerQuo().setIntAccExecCode( beanA.getAuthInfoVO().getIntAccExecCode() ); 
 		}

 		/* Mapping: "authInfoVO.extAccExecCode" -> "customerInsured.extAccExecCode" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getExtAccExecCode() )  ){
 			beanB.getCustomerInsured().setExtAccExecCode( beanA.getAuthInfoVO().getExtAccExecCode() ); 
 		}

 		/* Mapping: "authInfoVO.extAccExecCode" -> "cashCustomerQuo.extAccExecCode" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getExtAccExecCode() )  ){
 			beanB.getCashCustomerQuo().setExtAccExecCode( beanA.getAuthInfoVO().getExtAccExecCode() ); 
 		}

 		/* Mapping: "generalInfo.insured.nationality" -> "cashCustomerQuo.nationality" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getNationality() )  ){
 			beanB.getCashCustomerQuo().setNationality( beanA.getGeneralInfo().getInsured().getNationality() ); 
 		}

 		/* Mapping: "generalInfo.insured.insuredId" -> "insuredId" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getInsuredId() )  ){
 			beanB.setInsuredId( beanA.getGeneralInfo().getInsured().getInsuredId() ); 
 		}
		
		/* Mapping: "generalInfo.insured.insuredCode" -> "insuredCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getInsuredCode() )  ){
 			beanB.setInsuredCode( beanA.getGeneralInfo().getInsured().getInsuredCode() ); 
 		}

 		/* Mapping: "generalInfo.isChannelChanged" -> "isChannelChanged" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getIsChannelChanged() )  ){
 			beanB.setIsChannelChanged( beanA.getGeneralInfo().getIsChannelChanged() ); 
 		}

 		/* Mapping: "generalInfo.newCustomer" -> "newCustomer" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getNewCustomer() )  ){
 			beanB.setNewCustomer( beanA.getGeneralInfo().getNewCustomer() ); 
 		}

 		/* Mapping: "generalInfo.customerSaveReq" -> "customerSaveReq" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getCustomerSaveReq() )  ){
 			beanB.setCustomerSaveReq( beanA.getGeneralInfo().getCustomerSaveReq() ); 
 		}

 		/* Mapping: "generalInfo.insured.matchExists" -> "matchExists" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getMatchExists() )  ){
 			beanB.setMatchExists( beanA.getGeneralInfo().getInsured().getMatchExists() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.poBox" -> "cashCustomerQuo.poBox" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() )  ){
 			beanB.getCashCustomerQuo().setPoBox( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.poBox" -> "customerInsured.engZipCode" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() )  ){
 			beanB.getCustomerInsured().setEngZipCode( beanA.getGeneralInfo().getInsured().getAddress().getPoBox() ); 
 		}

 		/* Mapping: "defaultClassCode" -> "classCode" */
		if(  !Utils.isEmpty( beanA.getDefaultClassCode() )  ){
 			beanB.setClassCode( beanA.getDefaultClassCode() ); 
 		}

 		/* Mapping: "generalInfo.claimsHistory.sourceOfBusiness" -> "sourceOfBusiness" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory() ) && !Utils.isEmpty( beanA.getGeneralInfo().getClaimsHistory().getSourceOfBusiness() )  ){
 			beanB.setSourceOfBusiness( beanA.getGeneralInfo().getClaimsHistory().getSourceOfBusiness() ); 
 		}

 		/* Mapping: "authInfoVO.createdBy" -> "preparedBy" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getCreatedBy() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setPreparedBy( converter.getTypeOfA().cast( converter.getAFromB( beanA.getAuthInfoVO().getCreatedBy() ) ) );
  		}

 		/* Mapping: "authInfoVO.createdOn" -> "preparedDt" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getCreatedOn() )  ){
 			beanB.setPreparedDt( beanA.getAuthInfoVO().getCreatedOn() ); 
 		}

 		/* Mapping: "status" -> "status.code" */
		if(  !Utils.isEmpty( beanA.getStatus() )){
			beanB.getStatus().setCode(beanA.getStatus() );
		}
		
 		/* Mapping: "generalInfo.insured.busDescription" -> "customerInsured.business" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) &&  !Utils.isEmpty( beanA.getGeneralInfo().getInsured()) ){
			beanB.getCustomerInsured().setBusiness(beanA.getGeneralInfo().getInsured().getBusDescription());
		}
		
		/* Mapping: "generalInfo.insured.busDescription" -> "cashCustomerQuo.business" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) &&  !Utils.isEmpty( beanA.getGeneralInfo().getInsured()) ){
			beanB.getCashCustomerQuo().setBusiness(beanA.getGeneralInfo().getInsured().getBusDescription());
		}
		
		/* Mapping: "authInfoVO.refPolicyNo" -> "refPolicyNo" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getRefPolicyNo() )  ){
 			beanB.setRefPolicyNo( beanA.getAuthInfoVO().getRefPolicyNo() ); 
 		}

 		/* Mapping: "authInfoVO.refPolicyYear" -> "refPolicyYear" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getRefPolicyYear() )  ){
 			beanB.setRefPolicyYear( beanA.getAuthInfoVO().getRefPolicyYear() ); 
 		}
		
		/* Mapping: "authInfoVO.renewalTerms" -> "renewalTerms" */
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getRenewalTerms() )  ){
 			beanB.setPolRenTermTxt( beanA.getAuthInfoVO().getRenewalTerms() ); 
 		} 
		
		/* Mapping: "generalInfo.insured.address.country" -> "customerInsured.country.code" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getCountry() )  ){
 			beanB.getCustomerInsured().getCountry().setCode( beanA.getGeneralInfo().getInsured().getAddress().getCountry() ); 
 		}

 		/* Mapping: "generalInfo.insured.address.country" -> "cashCustomerQuo.country" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress() ) && !Utils.isEmpty( beanA.getGeneralInfo().getInsured().getAddress().getCountry() )  ){
 			beanB.getCashCustomerQuo().setCountry( beanA.getGeneralInfo().getInsured().getAddress().getCountry() ); 
 		}
		
 		/* Mapping: "validityStartDate" -> "validityStartDate" */
		if(  !Utils.isEmpty( beanA.getValidityStartDate() )  ){
 			beanB.setValidityStartDate( beanA.getValidityStartDate() ); 
 		}
		
		/* Mapping: "pol.loc.locationCode" -> "generalInfo.additionalInfo.issueLoc" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getIssueLoc() )  ){
			beanB.getLocation().setCode( beanA.getGeneralInfo().getAdditionalInfo().getIssueLoc()  ); 
 		}
		/* Mapping: "pol.branchCode" -> "generalInfo.additionalInfo.processingLoc" */
		if(  !Utils.isEmpty( beanA.getGeneralInfo().getAdditionalInfo().getProcessingLoc() )  ){
 			beanB.setPolicyBranchCode( beanA.getGeneralInfo().getAdditionalInfo().getProcessingLoc() ); 
 		}
		
		beanB.getCashCustomerQuo().setCcgCode(11);
			
		if(  !Utils.isEmpty( beanA.getAuthInfoVO() ) && !Utils.isEmpty( beanA.getAuthInfoVO().getIntAccExecCode() )  ){
 			beanB.setAccexecCode(( beanA.getAuthInfoVO().getIntAccExecCode() )); 
 		}
		
		/* Mapping: "validityStartDate" -> "validityStartDate" */
		if(  !Utils.isEmpty( beanA.getEndEffectiveDate() )  ){
 			beanB.setEndtEffectiveDate(  beanA.getEndEffectiveDate() ); 
 		}
		
		/* Mapping : policyVO.getRiskDetails().getCommission -> policyQuo.setCommissionId   */
		if(  !Utils.isEmpty( beanA.getRiskDetails() )  ) {
			
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = 
					ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			
			if(beanB.getClassCode().equals(beanA.getDefaultClassCode())) {
				for(SectionVO sectionVO : beanA.getRiskDetails()) {
					if(beanB.getClassCode().equals(sectionVO.getClassCode())) {
						beanB.setCommisionId(converter.getAFromB(sectionVO.getCommission())); 
					}
				}
			}
	 	}
		
		
		/** Added it manually as the following value is used within Kaizen and not is being set anywhere currently */
		ServiceContext.setgetSESSION_OMANBORDER_IND( Integer.valueOf( 2 ) );
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.kaizen.quote.model.PolicyQuo initializeDeepVO( com.rsaame.pas.vo.bus.PolicyVO beanA, com.rsaame.kaizen.quote.model.PolicyQuo beanB ){
  		BeanUtils.initializeBeanField( "customerInsured", beanB );
   		BeanUtils.initializeBeanField( "cashCustomerQuo", beanB );
            BeanUtils.initializeBeanField( "reasonCode", beanB );
       		//BeanUtils.initializeBeanField( "agent", beanB );
     		BeanUtils.initializeBeanField( "comp_id", beanB );
            BeanUtils.initializeBeanField( "location", beanB );
            BeanUtils.initializeBeanField( "cashCustomerQuo.city", beanB );  
            BeanUtils.initializeBeanField( "status", beanB );
            BeanUtils.initializeBeanField( "customerInsured.country", beanB );
                              		return beanB;
	}
}
