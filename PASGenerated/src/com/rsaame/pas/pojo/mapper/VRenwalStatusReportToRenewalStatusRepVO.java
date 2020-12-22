       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.VRenewalStatusReportPas</li>
 * <li>com.rsaame.pas.vo.bus.RenewalReportsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( VRenwalStatusReportToRenewalStatusRepVO.class )</code>.
 */
public class VRenwalStatusReportToRenewalStatusRepVO extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VRenewalStatusReportPas, com.rsaame.pas.vo.bus.RenewalReportsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VRenwalStatusReportToRenewalStatusRepVO(){
		super();
	}

	public VRenwalStatusReportToRenewalStatusRepVO( com.rsaame.pas.dao.model.VRenewalStatusReportPas src, com.rsaame.pas.vo.bus.RenewalReportsVO dest ){
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
		com.rsaame.pas.dao.model.VRenewalStatusReportPas beanA = src;
			
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

 		

 		/* Mapping: "id.polClassCode" -> "classCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolClassCode() )  ){
 			beanB.setClassCode( beanA.getId().getPolClassCode() ); 
 		}

 	

 		/* Mapping: "policyNo" -> "policyNo" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolicyNo() )  ){
 			beanB.setPolicyNo( beanA.getId().getPolicyNo() ); 
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

 		/* Mapping: "insuredName" -> "insuredName" */
		if(  !Utils.isEmpty( beanA.getInsuredName() )  ){
 			beanB.setInsuredName( beanA.getInsuredName() ); 
 		}

 		/* Mapping: "brCode" -> "brokerCode" */
		if(  !Utils.isEmpty( beanA.getBrCode() )  ){
 			beanB.setBrokerCode( beanA.getBrCode() ); 
 		}
		
 		/* Mapping: "totalPremium" -> "oldTotalPremium" */
		if(  !Utils.isEmpty( beanA.getTotalPremium() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setOldTotalPremium( converter.getTypeOfB().cast( converter.getBFromA( beanA.getTotalPremium() ) ) );
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

		/* Mapping: "effectiveDate" -> "polEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getQuoExpiryDate() )  ){
 			beanB.setPolExpiryDate(beanA.getQuoExpiryDate() ); 
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

 		/* Mapping: "licensedBy" -> "licencedBy" */
		if(  !Utils.isEmpty( beanA.getLicensedBy() )  ){
 			beanB.setLicencedBy( beanA.getLicensedBy() ); 
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
   
		
		if(  !Utils.isEmpty( beanA.getPolStatusDesc() )  ){
 			beanB.setStatusDesc( beanA.getPolStatusDesc() ); 
 		}
		if(  !Utils.isEmpty( beanA.getPolStatusDesc() )  ){
 			beanB.setStatusDesc( beanA.getPolStatusDesc() ); 
 		}
		if(  !Utils.isEmpty( beanA.getReason() )  ){
 			beanB.setReason( beanA.getReason() ); 
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
		
			/* Mapping: "clEDesc" -> "classDesc" */
		if(  !Utils.isEmpty( beanA.getClEDesc() )  ){
 			beanB.setClassDesc( beanA.getClEDesc() ); 
 		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.RenewalReportsVO initializeDeepVO( com.rsaame.pas.dao.model.VRenewalStatusReportPas beanA, com.rsaame.pas.vo.bus.RenewalReportsVO beanB ){
                                                                             		return beanB;
	}
}
