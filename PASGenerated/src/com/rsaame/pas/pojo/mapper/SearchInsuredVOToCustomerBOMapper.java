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
 * <li>com.rsaame.pas.vo.app.SearchInsuredVO</li>
 * <li>com.rsaame.kaizen.customer.model.CustomerBO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( SearchInsuredVOToCustomerBOMapper.class )</code>.
 */
public class SearchInsuredVOToCustomerBOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.app.SearchInsuredVO, com.rsaame.kaizen.customer.model.CustomerBO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public SearchInsuredVOToCustomerBOMapper(){
		super();
	}

	public SearchInsuredVOToCustomerBOMapper( com.rsaame.pas.vo.app.SearchInsuredVO src, com.rsaame.kaizen.customer.model.CustomerBO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.customer.model.CustomerBO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.customer.model.CustomerBO) Utils.newInstance( "com.rsaame.kaizen.customer.model.CustomerBO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.app.SearchInsuredVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.customer.model.CustomerBO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "insuredId" -> "customerId" */
		if(  !Utils.isEmpty( beanA.getInsuredId() )  ){
 			beanB.setCustomerId( beanA.getInsuredId() ); 
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

 		/* Mapping: "poBox" -> "poBoxNo" */
		if(  !Utils.isEmpty( beanA.getPoBox() )  ){
 			beanB.setPoBoxNo( beanA.getPoBox() ); 
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
	private static com.rsaame.kaizen.customer.model.CustomerBO initializeDeepVO( com.rsaame.pas.vo.app.SearchInsuredVO beanA, com.rsaame.kaizen.customer.model.CustomerBO beanB ){
                           		return beanB;
	}
}
