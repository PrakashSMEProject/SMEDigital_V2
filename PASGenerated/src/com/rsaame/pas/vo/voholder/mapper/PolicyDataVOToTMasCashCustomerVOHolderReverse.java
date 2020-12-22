       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.vo.voholder.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder</li>
 * <li>com.rsaame.pas.vo.bus.PolicyDataVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyDataVOToTMasCashCustomerVOHolderReverse.class )</code>.
 */
public class PolicyDataVOToTMasCashCustomerVOHolderReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder, com.rsaame.pas.vo.bus.PolicyDataVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyDataVOToTMasCashCustomerVOHolderReverse(){
		super();
	}

	public PolicyDataVOToTMasCashCustomerVOHolderReverse( com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder src, com.rsaame.pas.vo.bus.PolicyDataVO dest ){
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
		com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PolicyDataVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "cshEName1" -> "generalInfo.insured.name" */
		if(  !Utils.isEmpty( beanA.getCshEName1() )  ){
 			beanB.getGeneralInfo().getInsured().setName( beanA.getCshEName1() ); 
 		}

 		/* Mapping: "cshCountry" -> "generalInfo.insured.address.country" */
		if(  !Utils.isEmpty( beanA.getCshCountry() )  ){
 			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().getAddress().setCountry( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCshCountry() ) ) );
  		}

 		/* Mapping: "cshCity" -> "generalInfo.insured.address.emirates" */
		if(  !Utils.isEmpty( beanA.getCshCity() )  ){
 			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().getAddress().setEmirates( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCshCity() ) ) );
  		}

 		/* Mapping: "cshPoboxNo" -> "generalInfo.insured.address.poBox" */
		if(  !Utils.isEmpty( beanA.getCshPoboxNo() )  ){
 			beanB.getGeneralInfo().getInsured().getAddress().setPoBox( beanA.getCshPoboxNo() ); 
 		}

 		/* Mapping: "cshEEmailId" -> "generalInfo.insured.emailId" */
		if(  !Utils.isEmpty( beanA.getCshEEmailId() )  ){
 			beanB.getGeneralInfo().getInsured().setEmailId( beanA.getCshEEmailId() ); 
 		}

 		/* Mapping: "cshEGsmNo" -> "generalInfo.insured.mobileNo" */
		if(  !Utils.isEmpty( beanA.getCshEGsmNo() )  ){
 			beanB.getGeneralInfo().getInsured().setMobileNo( beanA.getCshEGsmNo() ); 
 		}

 		/* Mapping: "cshNationality" -> "generalInfo.insured.nationality" */
		if(  !Utils.isEmpty( beanA.getCshNationality() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getGeneralInfo().getInsured().setNationality( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCshNationality() ) ) );
  		}

 		/* Mapping: "cshSourceOfCust" -> "generalInfo.sourceOfBus.customerSource" */
		if(  !Utils.isEmpty( beanA.getCshSourceOfCust() )  ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.getGeneralInfo().getSourceOfBus().setCustomerSource( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCshSourceOfCust() ) ) );
  		}

 		/* Mapping: "cshIntAccExecCode" -> "authenticationInfoVO.intAccExecCode" */
		if(  !Utils.isEmpty( beanA.getCshIntAccExecCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setIntAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCshIntAccExecCode() ) ) );
  		}

 		/* Mapping: "cshExtAccExecCode" -> "authenticationInfoVO.extAccExecCode" */
		if(  !Utils.isEmpty( beanA.getCshExtAccExecCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAuthenticationInfoVO().setExtAccExecCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCshExtAccExecCode() ) ) );
  		}

		/* Mapping: "cshRoyaltyTypCode" -> "generalInfo.insured.royaltyType" */
		if(  !Utils.isEmpty( beanA.getCshRoyaltyTypCode() )  ){
			beanB.getGeneralInfo().getInsured().setRoyaltyType( beanA.getCshRoyaltyTypCode() );
		}
		
		/* Mapping: "cshATelexNo" -> "generalInfo.insured.guestCardNo" */
		if(  !Utils.isEmpty( beanA.getCshATelexNo() )  ){
			beanB.getGeneralInfo().getInsured().setGuestCardNo( beanA.getCshATelexNo() );
		}
		  
		/* Mapping: "cshTurnover" -> "generalInfo.insured.turnover"   */
		if(  !Utils.isEmpty( beanA.getCshTurnover()) ){
			beanB.getGeneralInfo().getInsured().setTurnover(beanA.getCshTurnover()); 
 		}

		/* Mapping: "cshTurnover" -> "generalInfo.insured.turnover"   */
		if(  !Utils.isEmpty( beanA.getCshAffinityRefNo()) ){
			beanB.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(beanA.getCshAffinityRefNo()); 
 		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PolicyDataVO initializeDeepVO( com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder beanA, com.rsaame.pas.vo.bus.PolicyDataVO beanB ){
  		BeanUtils.initializeBeanField( "generalInfo.insured", beanB );
   		BeanUtils.initializeBeanField( "generalInfo.insured.address", beanB );
        BeanUtils.initializeBeanField( "generalInfo.sourceOfBus", beanB );
       	BeanUtils.initializeBeanField( "authenticationInfoVO", beanB );
       	BeanUtils.initializeBeanField( "generalInfo.additionalInfo", beanB );
    		return beanB;
	}
}
