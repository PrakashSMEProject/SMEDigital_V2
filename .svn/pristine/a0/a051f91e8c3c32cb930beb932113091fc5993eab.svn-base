package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

public class VRenwalStatusEmailReportToRenewalStatusRepVO extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VRenewalStatusEmailReportPas, com.rsaame.pas.vo.bus.RenewalEmailReportsVO> {
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VRenwalStatusEmailReportToRenewalStatusRepVO(){
		super();
	}

	public VRenwalStatusEmailReportToRenewalStatusRepVO( com.rsaame.pas.dao.model.VRenewalStatusEmailReportPas src, com.rsaame.pas.vo.bus.RenewalEmailReportsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.RenewalEmailReportsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.RenewalEmailReportsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.RenewalEmailReportsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.VRenewalStatusEmailReportPas beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.RenewalEmailReportsVO beanB = dest;
			
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
			/* Mapping: "clEDesc" -> "classDesc" */
		if(  !Utils.isEmpty( beanA.getClEDesc() )  ){
 			beanB.setClassDesc( beanA.getClEDesc() ); 
 		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.RenewalEmailReportsVO initializeDeepVO( com.rsaame.pas.dao.model.VRenewalStatusEmailReportPas beanA, com.rsaame.pas.vo.bus.RenewalEmailReportsVO beanB ){
                                                                             		return beanB;
	}
}
