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
 * <li>com.rsaame.pas.vo.bus.RenewalReportsVO</li>
 * <li>com.rsaame.pas.dao.model.VRenewalStatusReportPas</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( VRenwalStatusReportToRenewalStatusRepVOReverse.class )</code>.
 */
public class VRenwalStatusReportToRenewalStatusRepVOReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.RenewalReportsVO, com.rsaame.pas.dao.model.VRenewalStatusReportPas>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VRenwalStatusReportToRenewalStatusRepVOReverse(){
		super();
	}

	public VRenwalStatusReportToRenewalStatusRepVOReverse( com.rsaame.pas.vo.bus.RenewalReportsVO src, com.rsaame.pas.dao.model.VRenewalStatusReportPas dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.VRenewalStatusReportPas mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.VRenewalStatusReportPas) Utils.newInstance( "com.rsaame.pas.dao.model.VRenewalStatusReportPas" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.RenewalReportsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.VRenewalStatusReportPas beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "renQuotationNo" -> "id.quotationNo" */
		if(  !Utils.isEmpty( beanA.getRenQuotationNo() )  ){
 			beanB.setQuotationNo( beanA.getRenQuotationNo() ); 
 		}

 		

 		/* Mapping: "classCode" -> "id.polClassCode" */
		if(  !Utils.isEmpty( beanA.getClassCode() )  ){
 			beanB.getId().setPolClassCode( beanA.getClassCode() ); 
 		}

 		
 		/* Mapping: "policyNo" -> "policyNo" */
		if(  !Utils.isEmpty( beanA.getPolicyNo() )  ){
 			beanB.getId().setPolicyNo( beanA.getPolicyNo() ); 
 		}

 		

 		/* Mapping: "policyTypeCode" -> "polPolicyType" */
		if(  !Utils.isEmpty( beanA.getPolicyTypeCode() )  ){
 			beanB.setPolPolicyType( beanA.getPolicyTypeCode() ); 
 		}

 		/* Mapping: "policyTypeDesc" -> "ptEDesc" */
		if(  !Utils.isEmpty( beanA.getPolicyTypeDesc() )  ){
 			beanB.setPtEDesc( beanA.getPolicyTypeDesc() ); 
 		}

 		/* Mapping: "locationCode" -> "polLocationCode" */
		if(  !Utils.isEmpty( beanA.getLocationCode() )  ){
 			beanB.setPolLocationCode( beanA.getLocationCode() ); 
 		}


 		/* Mapping: "insuredName" -> "insuredName" */
		if(  !Utils.isEmpty( beanA.getInsuredName() )  ){
 			beanB.setInsuredName( beanA.getInsuredName() ); 
 		}

 		

 		/* Mapping: "brokerCode" -> "brCode" */
		if(  !Utils.isEmpty( beanA.getBrokerCode() )  ){
 			beanB.setBrCode( beanA.getBrokerCode() ); 
 		}


 		/* Mapping: "oldTotalPremium" -> "totalPremium" */
		if(  !Utils.isEmpty( beanA.getOldTotalPremium() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setTotalPremium( converter.getTypeOfA().cast( converter.getAFromB( beanA.getOldTotalPremium() ) ) );
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
			beanB.setClaimAmount( converter.getTypeOfA().cast( converter.getAFromB( beanA.getClaimAmount() ) ) );
  		}

 		/* Mapping: "polEffectiveDate" -> "effectiveDate" */
		if(  !Utils.isEmpty( beanA.getPolEffectiveDate() )  ){
 			beanB.setEffectiveDate( beanA.getPolEffectiveDate() ); 
 		}

 		/* Mapping: "newTotalPremium" -> "newTotalPremium" */
		if(  !Utils.isEmpty( beanA.getNewTotalPremium() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setNewTotalPremium( converter.getTypeOfA().cast( converter.getAFromB( beanA.getNewTotalPremium() ) ) );
  		}

 		/* Mapping: "brokerName" -> "brName" */
		if(  !Utils.isEmpty( beanA.getBrokerName() )  ){
 			beanB.setBrName( beanA.getBrokerName() ); 
 		}

 		/* Mapping: "osAmount" -> "osAmt" */
		if(  !Utils.isEmpty( beanA.getOsAmount() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setOsAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getOsAmount() ) ) );
  		}

 		/* Mapping: "licencedBy" -> "licensedBy" */
		if(  !Utils.isEmpty( beanA.getLicencedBy() )  ){
 			beanB.setLicensedBy( beanA.getLicencedBy() ); 
 		}

 		

 		/* Mapping: "renewalTermTxt" -> "polRenTermTxt" */
		if(  !Utils.isEmpty( beanA.getRenewalTermTxt() )  ){
 			beanB.setPolRenTermTxt( beanA.getRenewalTermTxt() ); 
 		}

 		/* Mapping: "brokerCode" -> "brokerCode" */
		if(  !Utils.isEmpty( beanA.getBrokerCode() )  ){
 			beanB.setBrokerCode( beanA.getBrokerCode() ); 
 		}

 		/* Mapping: "schemeCode" -> "polSchemeCode" */
		if(  !Utils.isEmpty( beanA.getSchemeCode() )  ){
 			beanB.setPolSchemeCode( beanA.getSchemeCode() ); 
 		}

 		/* Mapping: "statusCode" -> "polStatus" */
		if(  !Utils.isEmpty( beanA.getStatusCode() )  ){
 			beanB.setPolStatus( beanA.getStatusCode() ); 
 		}

		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getPolDistributionChnl() )  ){
 			beanB.setPolDistributionChnl( beanA.getPolDistributionChnl() ); 
 		}
   
		/* Mapping: "polDistributionChnl" -> "polDistributionChnl" */
		if(  !Utils.isEmpty( beanA.getScheme() )  ){
 			beanB.setScheme( beanA.getScheme() ); 
 		}
   
		
		if(  !Utils.isEmpty( beanA.getStatusDesc() )  ){
 			beanB.setPolStatusDesc( beanA.getStatusDesc() ); 
 		}
		if(  !Utils.isEmpty( beanA.getReason() )  ){
 			beanB.setReason( beanA.getReason() ); 
 		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.VRenewalStatusReportPas initializeDeepVO( com.rsaame.pas.vo.bus.RenewalReportsVO beanA, com.rsaame.pas.dao.model.VRenewalStatusReportPas beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                                                                            		return beanB;
	}
}
