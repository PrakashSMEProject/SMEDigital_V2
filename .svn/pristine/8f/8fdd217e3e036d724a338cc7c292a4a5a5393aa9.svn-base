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
 * <li>com.rsaame.pas.gen.domain.TMasInsured</li>
 * <li>com.rsaame.pas.vo.bus.GeneralInfoVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasInsuredQuoToGenVOMapper.class )</code>.
 */
public class TMasInsuredQuoToGenVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.gen.domain.TMasInsured, com.rsaame.pas.vo.bus.GeneralInfoVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasInsuredQuoToGenVOMapper(){
		super();
	}

	public TMasInsuredQuoToGenVOMapper( com.rsaame.pas.gen.domain.TMasInsured src, com.rsaame.pas.vo.bus.GeneralInfoVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.GeneralInfoVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.GeneralInfoVO) Utils.newInstance( "com.rsaame.pas.vo.bus.GeneralInfoVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.gen.domain.TMasInsured beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GeneralInfoVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "insInsuredCode" -> "insured.insuredId" */
		if(  !Utils.isEmpty( beanA.getInsInsuredCode() )  ){
 			beanB.getInsured().setInsuredId( beanA.getInsInsuredCode() ); 
 		}
		
		/* Mapping: "insInsuredCode" -> "insured.insuredId" */
		if(  !Utils.isEmpty( beanA.getInsInsuredCode() )  ){
 			beanB.getInsured().setInsuredCode( beanA.getInsInsuredCode() ); 
 		}
		
		/* Mapping: "insInsuredCode" -> "insured.insuredId" */
		if(  !Utils.isEmpty( beanA.getInsInsuredCode() )  ){
 			beanB.getInsured().setInsuredCode( beanA.getInsInsuredCode() ); 
 		}
		
		/* Mapping: "insAFirstName" -> "insured.firstName" */
		if(  !Utils.isEmpty( beanA.getInsEFirstName() )  ){
 			beanB.getInsured().setFirstName( beanA.getInsEFirstName() ); 
 		}
		
		/* Mapping: "insAFirstName" -> "insured.firstName" */
		if(  !Utils.isEmpty( beanA.getInsELastName() )  ){
 			beanB.getInsured().setLastName( beanA.getInsELastName() ); 
 		}


 		/* Mapping: "insEZipCode" -> "insured.address.poBox" */
		if(  !Utils.isEmpty( beanA.getInsEZipCode() )  ){
 			beanB.getInsured().getAddress().setPoBox( beanA.getInsEZipCode() ); 
 		}

		if(  !Utils.isEmpty( beanA.getInsEEmailId() )  ){
 			beanB.getInsured().setEmailId(  beanA.getInsEEmailId() ); 
 		}
		
 		/* Mapping: "insBrCode" -> "sourceOfBus.brokerName" */
		if(  !Utils.isEmpty( beanA.getInsBrCode() )  ){
 			beanB.getSourceOfBus().setBrokerName( beanA.getInsBrCode() ); 
 		}

 		/* Mapping: "insAgentCode" -> "sourceOfBus.directSubAgent" */
		if(  !Utils.isEmpty( beanA.getInsAgentCode() )  ){
 			beanB.getSourceOfBus().setDirectSubAgent( beanA.getInsAgentCode() ); 
 		}

 		/* Mapping: "insDistributionChnl" -> "sourceOfBus.distributionChannel" */
		if(  !Utils.isEmpty( beanA.getInsDistributionChnl() )  ){
 			beanB.getSourceOfBus().setDistributionChannel( beanA.getInsDistributionChnl() ); 
 		}

 		/* Mapping: "insDtCollectionKyc" -> "additionalInfo.dateOfcollectionOfKYC" */
		if(  !Utils.isEmpty( beanA.getInsDtCollectionKyc() )  ){
 			beanB.getAdditionalInfo().setDateOfcollectionOfKYC( beanA.getInsDtCollectionKyc() ); 
 		}

 		/* Mapping: "insExpiryDate" -> "additionalInfo.tradeLicenseExpDate" */
		if(  !Utils.isEmpty( beanA.getInsExpiryDate() )  ){
 			beanB.getAdditionalInfo().setTradeLicenseExpDate( beanA.getInsExpiryDate() ); 
 		}

 		/* Mapping: "insStatus" -> "additionalInfo.insuredStatus" */
		if(  !Utils.isEmpty( beanA.getInsStatus() )  ){
 			beanB.getAdditionalInfo().setInsuredStatus( beanA.getInsStatus() ); 
 		}

 		/* Mapping: "insRemarks" -> "additionalInfo.remarks" */
		if(  !Utils.isEmpty( beanA.getInsRemarks() )  ){
 			beanB.getAdditionalInfo().setRemarks( beanA.getInsRemarks() ); 
 		}
		
		/* Mapping: "insTurnover" -> "insured.turnover" */
		if(  !Utils.isEmpty( beanA.getInsTurnover() )  ){
 			beanB.getInsured().setTurnover( beanA.getInsTurnover() ); 
 		}
		
		/* Mapping: "insNoOfEmp" -> "insured.noOfEmployees" */
		if(  !Utils.isEmpty( beanA.getInsNoOfEmp() )  ){
 			beanB.getInsured().setNoOfEmployees( beanA.getInsNoOfEmp() ); 
 		}
		
		/* Mapping: "insNoOfEmp" -> "insured.noOfEmployees" */
		if(  !Utils.isEmpty( beanA.getInsECoRegnNo() )  ){
 			beanB.getInsured().setTradeLicenseNo( beanA.getInsECoRegnNo() ) ; 
 		}
		
		/* Mapping: "insCcgCode" -> "insured.CcgCode" */
		if(  !Utils.isEmpty( beanA.getInsCcgCode() )  ){
 			beanB.getInsured().setCcgCode( Integer.valueOf( beanA.getInsCcgCode() ) ) ; 
 		}
		
		/* Mapping: 142244"vatRegNo" -> "insured.vatRegNo" */
		if(  !Utils.isEmpty( beanA.getInsVatRegNo() )  ){
 			beanB.getInsured().setVatRegNo( ( beanA.getInsVatRegNo() ) ) ; 
 		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.GeneralInfoVO initializeDeepVO( com.rsaame.pas.gen.domain.TMasInsured beanA, com.rsaame.pas.vo.bus.GeneralInfoVO beanB ){
  		BeanUtils.initializeBeanField( "insured", beanB );
   		BeanUtils.initializeBeanField( "insured.address", beanB );
   		BeanUtils.initializeBeanField( "sourceOfBus", beanB );
       		BeanUtils.initializeBeanField( "additionalInfo", beanB );
        		return beanB;
	}
}
