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
 * <li>com.rsaame.kaizen.customer.model.CustomerBO</li>
 * <li>com.rsaame.pas.vo.app.SearchInsuredVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( SearchInsuredVOToCustomerBOMapperReverse.class )</code>.
 */
public class SearchInsuredVOToCustomerBOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.kaizen.customer.model.CustomerBO, com.rsaame.pas.vo.app.SearchInsuredVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public SearchInsuredVOToCustomerBOMapperReverse(){
		super();
	}

	public SearchInsuredVOToCustomerBOMapperReverse( com.rsaame.kaizen.customer.model.CustomerBO src, com.rsaame.pas.vo.app.SearchInsuredVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.SearchInsuredVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.SearchInsuredVO) Utils.newInstance( "com.rsaame.pas.vo.app.SearchInsuredVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.customer.model.CustomerBO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.SearchInsuredVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "customerId" -> "insuredId" */
		if(  !Utils.isEmpty( beanA.getCustomerId() )  ){
 			beanB.setInsuredId( beanA.getCustomerId() ); 
 		}

 		/* Mapping: "emailId" -> "emailId" */
		if(  !Utils.isEmpty( beanA.getEmailId() )  ){
 			beanB.setEmailId( beanA.getEmailId() ); 
 		}

 		/* Mapping: "phoneNo" -> "phoneNo" */
		if(  !Utils.isEmpty( beanA.getPhoneNo() )  ){
 			beanB.setPhoneNo( beanA.getPhoneNo() ); 
 		}

 		/* Mapping: "mobileNo" -> "mobileNo" */
		if(  !Utils.isEmpty( beanA.getMobileNo() )  ){
 			beanB.setMobileNo( beanA.getMobileNo() ); 
 		}

 		/* Mapping: "policyQuoteNo" -> "policyQuoteNo" */
		if(  !Utils.isEmpty( beanA.getPolicyQuoteNo() )  ){
 			beanB.setPolicyQuoteNo( beanA.getPolicyQuoteNo() ); 
 		}

 		/* Mapping: "poBoxNo" -> "poBox" */
		if(  !Utils.isEmpty( beanA.getPoBoxNo() )  ){
 			beanB.setPoBox( beanA.getPoBoxNo() ); 
 		}

 		/* Mapping: "completeName" -> "completeName" */
		if(  !Utils.isEmpty( beanA.getCompleteName() )  ){
 			beanB.setCompleteName( beanA.getCompleteName() ); 
 		}

 		/* Mapping: "city" -> "city" */
		if(  !Utils.isEmpty( beanA.getCity() )  ){
 			beanB.setCity( beanA.getCity() ); 
 		}

 		/* Mapping: "distributionChannel" -> "distributionChannel" */
		if(  !Utils.isEmpty( beanA.getDistributionChannel() )  ){
 			beanB.setDistributionChannel( beanA.getDistributionChannel() ); 
 		}

 		/* Mapping: "brokerName" -> "brokerName" */
		if(  !Utils.isEmpty( beanA.getBrokerName() )  ){
 			beanB.setBrokerName( beanA.getBrokerName() ); 
 		}

 		/* Mapping: "creationDate" -> "creationDate" */
		if(  !Utils.isEmpty( beanA.getCreationDate() )  ){
 			beanB.setCreationDate( beanA.getCreationDate() ); 
 		}

 		/* Mapping: "locationCode" -> "locationCode" */
		if(  !Utils.isEmpty( beanA.getLocationCode() )  ){
 			beanB.setLocationCode( beanA.getLocationCode() ); 
 		}

 		/* Mapping: "locationName" -> "locationName" */
		if(  !Utils.isEmpty( beanA.getLocationName() )  ){
 			beanB.setLocationName( beanA.getLocationName() ); 
 		}
		/* Mapping: "InsuredCode" -> "InsuredCode" */
		if(  !Utils.isEmpty( beanA.getInsuredCode() )  ){
 			beanB.setInsuredCode( beanA.getInsuredCode() ); 
 		}
		
		/* Mapping: "BrokerId" -> "BrokerId" */
		if(!Utils.isEmpty( beanA.getBrokerId() )){
			beanB.setBrokerId(  beanA.getBrokerId()  );
		}
		
		/* Mapping: "CcgCode" -> "CcgCode" */
		if(!Utils.isEmpty( beanA.getCcgCode() )){
			beanB.setCcgCode(  beanA.getCcgCode()  );
		}
		
		/* Mapping: "exactSearch" -> "exactSearch" */
		if( !Utils.isEmpty( beanA.getExactSearch() ) ){
			beanB.setExactSearch( beanA.getExactSearch() );
  		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.SearchInsuredVO initializeDeepVO( com.rsaame.kaizen.customer.model.CustomerBO beanA, com.rsaame.pas.vo.app.SearchInsuredVO beanB ){
                           		return beanB;
	}
}
