       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.VRenewalStatusReportPasHT</li>
 * <li>com.rsaame.pas.vo.bus.RenewalReportsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( VRenwalStatusReportToRenewalStatusRepVO.class )</code>.
 */
public class VRenwalStatusReportHTToRenewalStatusRepVO extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VRenewalStatusReportPasHT, com.rsaame.pas.vo.bus.RenewalReportsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VRenwalStatusReportHTToRenewalStatusRepVO(){
		super();
	}

	public VRenwalStatusReportHTToRenewalStatusRepVO( com.rsaame.pas.dao.model.VRenewalStatusReportPasHT src, com.rsaame.pas.vo.bus.RenewalReportsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.RenewalReportsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.RenewalReportsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.RenewalReportsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.VRenewalStatusReportPasHT beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.RenewalReportsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.quotationNo" -> "renQuotationNo" */
		if(   !Utils.isEmpty( beanA.getQuotationNo() )  ){
 			beanB.setRenQuotationNo( beanA.getQuotationNo() ); 
 		}

 		/* Mapping: "id.policyId" -> "policyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolicyId() )  ){
 			beanB.setPolicyId( beanA.getId().getPolicyId() ); 
 		}

 		/* Mapping: "id.polEndtId" -> "polEndtId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolEndtId() )  ){
 			beanB.setPolEndtId( beanA.getId().getPolEndtId() ); 
 		}

 		/* Mapping: "id.polClassCode" -> "classCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolClassCode() )  ){
 			beanB.setClassCode( beanA.getId().getPolClassCode() ); 
 		}

 		/* Mapping: "id.polRefPolicyId" -> "refPolicyId" */
		if(  !Utils.isEmpty( beanA.getPolRefPolicyId() )  ){
 			beanB.setRefPolicyId( beanA.getPolRefPolicyId() ); 
 		}

 		/* Mapping: "policyNo" -> "policyNo" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolicyNo() )  ){
 			beanB.setPolicyNo( beanA.getId().getPolicyNo() ); 
 		}

 		/* Mapping: "clEDesc" -> "classDesc" */
		if(  !Utils.isEmpty( beanA.getClEDesc() )  ){
 			beanB.setClassDesc( beanA.getClEDesc() ); 
 		}

 		/* Mapping: "polPolicyType" -> "policyTypeCode" */
		if(  !Utils.isEmpty( beanA.getPolPolicyType() )  ){
 			beanB.setPolicyTypeCode( beanA.getPolPolicyType() ); 
 		}

 		/* Mapping: "ptEDesc" -> "policyTypeDesc" */
		if(  !Utils.isEmpty( beanA.getPtEDesc() )  ){
 			beanB.setPolicyTypeDesc( beanA.getPtEDesc() ); 
 		}

 		/* Mapping: "polLocationCode" -> "locationCode" */
		if(  !Utils.isEmpty( beanA.getPolLocationCode() )  ){
 			beanB.setLocationCode( beanA.getPolLocationCode() ); 
 		}

 		/* Mapping: "locEDesc" -> "locationDesc" */
		if(  !Utils.isEmpty( beanA.getLocEDesc() )  ){
 			beanB.setLocationDesc( beanA.getLocEDesc() ); 
 		}

 		/* Mapping: "specialty" -> "specialty" */
		if(  !Utils.isEmpty( beanA.getSpecialty() )  ){
 			beanB.setSpecialty( beanA.getSpecialty() ); 
 		}

 		/* Mapping: "facRi" -> "facRI" */
		if(  !Utils.isEmpty( beanA.getFacRi() )  ){
 			beanB.setFacRI( beanA.getFacRi() ); 
 		}

 		/* Mapping: "policyYear" -> "policyYear" */
		if(  !Utils.isEmpty( beanA.getPolicyYear() )  ){
 			beanB.setPolicyYear( beanA.getPolicyYear() ); 
 		}

 		/* Mapping: "insuredName" -> "insuredName" */
		if(  !Utils.isEmpty( beanA.getInsuredName() )  ){
 			beanB.setInsuredName( beanA.getInsuredName() ); 
 		}

 		/* Mapping: "customerId" -> "customerId" */
		if(  !Utils.isEmpty( beanA.getCustomerId() )  ){
 			beanB.setCustomerId( beanA.getCustomerId() ); 
 		}

 		/* Mapping: "brCode" -> "brokerCode" */
		if(  !Utils.isEmpty( beanA.getBrCode() )  ){
 			beanB.setBrokerCode( beanA.getBrCode() ); 
 		}

 		/* Mapping: "polAccexecCode" -> "polAccexecCode" */
		if(  !Utils.isEmpty( beanA.getPolAccexecCode() )  ){
 			beanB.setPolAccexecCode( beanA.getPolAccexecCode() ); 
 		}

 		/* Mapping: "expiryDate" -> "polExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPolExpiryDate())  ){
 			beanB.setPolExpiryDate( beanA.getPolExpiryDate() ); 
 		}

 		/* Mapping: "totalPremium" -> "oldTotalPremium" */
		if(  !Utils.isEmpty( beanA.getTotalPremium() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setOldTotalPremium( converter.getTypeOfB().cast( converter.getBFromA( beanA.getTotalPremium() ) ) );
  		}

 		/* Mapping: "polValidityExpiryDate" -> "polValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPolValidityExpiryDate() )  ){
 			beanB.setPolValidityExpiryDate( beanA.getPolValidityExpiryDate() ); 
 		}

 		/* Mapping: "claimCount" -> "claimCount" */
		if(  !Utils.isEmpty( beanA.getClaimCount() )  ){
 			beanB.setClaimCount( beanA.getClaimCount() ); 
 		}

 		/* Mapping: "claimCount2" -> "claimCount2" */
		if(  !Utils.isEmpty( beanA.getClaimCount2() )  ){
 			beanB.setClaimCount2( beanA.getClaimCount2() ); 
 		}

 		/* Mapping: "claimCount3" -> "claimCount3" */
		if(  !Utils.isEmpty( beanA.getClaimCount3() )  ){
 			beanB.setClaimCount3( beanA.getClaimCount3() ); 
 		}

 		/* Mapping: "claimCount4" -> "claimCount4" */
		if(  !Utils.isEmpty( beanA.getClaimCount4() )  ){
 			beanB.setClaimCount4( beanA.getClaimCount4() ); 
 		}

 		/* Mapping: "claimCount5" -> "claimCount5" */
		if(  !Utils.isEmpty( beanA.getClaimCount5() )  ){
 			beanB.setClaimCount5( beanA.getClaimCount5() ); 
 		}

 		/* Mapping: "claimAmount" -> "claimAmount" */
		if(  !Utils.isEmpty( beanA.getClaimAmount() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setClaimAmount( converter.getTypeOfB().cast( converter.getBFromA( beanA.getClaimAmount() ) ) );
  		}

 		/* Mapping: "effectiveDate" -> "polEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getEffectiveDate() )  ){
 			beanB.setPolEffectiveDate( beanA.getEffectiveDate() ); 
 		}

 		/* Mapping: "newTotalPremium" -> "newTotalPremium" */
		if(  !Utils.isEmpty( beanA.getNewTotalPremium() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setNewTotalPremium( converter.getTypeOfB().cast( converter.getBFromA( beanA.getNewTotalPremium() ) ) );
  		}

 		/* Mapping: "brName" -> "brokerName" */
		if(  !Utils.isEmpty( beanA.getBrName() )  ){
 			beanB.setBrokerName( beanA.getBrName() ); 
 		}

 		/* Mapping: "osAmt" -> "osAmount" */
		if(  !Utils.isEmpty( beanA.getOsAmt() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setOsAmount( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOsAmt() ) ) );
  		}

 		/* Mapping: "ppRegn" -> "ppRegion" */
		if(  !Utils.isEmpty( beanA.getPpRegn() )  ){
 			beanB.setPpRegion( beanA.getPpRegn() ); 
 		}

 		/* Mapping: "licensedBy" -> "licencedBy" */
		if(  !Utils.isEmpty( beanA.getLicensedBy() )  ){
 			beanB.setLicencedBy( beanA.getLicensedBy() ); 
 		}

 		/* Mapping: "dob" -> "dateOfBirth" */
		if(  !Utils.isEmpty( beanA.getDob() )  ){
 			beanB.setDateOfBirth( beanA.getDob() ); 
 		}

 		/* Mapping: "polRenTermTxt" -> "renewalTermTxt" */
		if(  !Utils.isEmpty( beanA.getPolRenTermTxt() )  ){
 			beanB.setRenewalTermTxt( beanA.getPolRenTermTxt() ); 
 		}

 		/* Mapping: "brokerCode" -> "brokerCode" */
		if(  !Utils.isEmpty( beanA.getBrokerCode() )  ){
 			beanB.setBrokerCode( beanA.getBrokerCode() ); 
 		}

 		/* Mapping: "polSchemeCode" -> "schemeCode" */
		if(  !Utils.isEmpty( beanA.getPolSchemeCode() )  ){
 			beanB.setSchemeCode( beanA.getPolSchemeCode() ); 
 		}

 		/* Mapping: "polStatus" -> "statusCode" */
		if(  !Utils.isEmpty( beanA.getPolStatus() )  ){
 			beanB.setStatusCode( beanA.getPolStatus() ); 
 		}

		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getPolDistributionChnl() )  ){
 			beanB.setPolDistributionChnl( beanA.getPolDistributionChnl() ); 
 		}
   
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getScheme() )  ){
 			beanB.setScheme( beanA.getScheme() ); 
 		}
   
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getCustAddr() )  ){
 			beanB.setCustAddr( beanA.getCustAddr() ); 
 		}
 		
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getPoBox() )  ){
 			beanB.setPoBox( beanA.getPoBox() ); 
 		}
 		
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getMobileNo() )  ){
 			beanB.setMobileNo( beanA.getMobileNo() ); 
 		}
 		
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getNationality() )  ){
 			beanB.setNationality( beanA.getNationality() ); 
 		}
 		
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getPrmRate() )  ){
 			beanB.setPrmRate( beanA.getPrmRate() ); 
 		}
 		
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getNoOfNonRecoverableClmAmt() )  ){
 			beanB.setNoOfNonRecoverableClmAmt( beanA.getNoOfNonRecoverableClmAmt() ); 
 		}
		
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getNoOfNonRecoverableClm() )  ){
 			beanB.setNoOfNonRecoverableClm( beanA.getNoOfNonRecoverableClm() ); 
 		}
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getClaimType() )  ){
 			beanB.setClaimType( beanA.getClaimType() ); 
 		}
		if(  !Utils.isEmpty( beanA.getPolStatusDesc() )  ){
 			beanB.setStatusDesc( beanA.getPolStatusDesc() ); 
 		}
		if(  !Utils.isEmpty( beanA.getPolStatusDesc() )  ){
 			beanB.setStatusDesc( beanA.getPolStatusDesc() ); 
 		}
		if(  !Utils.isEmpty( beanA.getReason() )  ){
 			beanB.setReason( beanA.getReason() ); 
 		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.RenewalReportsVO initializeDeepVO( com.rsaame.pas.dao.model.VRenewalStatusReportPasHT beanA, com.rsaame.pas.vo.bus.RenewalReportsVO beanB ){
                                                                             		return beanB;
	}
}
