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
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * <li>com.rsaame.pas.vo.bus.AuthenticationInfoVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyQuoToAuthVoMap.class )</code>.
 */
public class PolicyQuoToAuthVoMap extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPolicyQuo, com.rsaame.pas.vo.bus.AuthenticationInfoVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyQuoToAuthVoMap(){
		super();
	}

	public PolicyQuoToAuthVoMap( com.rsaame.pas.dao.model.TTrnPolicyQuo src, com.rsaame.pas.vo.bus.AuthenticationInfoVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.AuthenticationInfoVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.AuthenticationInfoVO) Utils.newInstance( "com.rsaame.pas.vo.bus.AuthenticationInfoVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.AuthenticationInfoVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "polUserId" -> "licensedBy" */
		if(  !Utils.isEmpty( beanA.getPolUserId() )  ){
 			beanB.setLicensedBy( beanA.getPolUserId() ); 
 		}

 		/* Mapping: "polApprovedBy" -> "approvedBy" */
		if(  !Utils.isEmpty( beanA.getPolApprovedBy() )  ){
 			beanB.setApprovedBy( beanA.getPolApprovedBy() ); 
 		}

 		/* Mapping: "polApprovalDate" -> "approvedDt" */
		if(  !Utils.isEmpty( beanA.getPolApprovalDate() )  ){
 			beanB.setApprovedDt( beanA.getPolApprovalDate() ); 
 		}

 		/* Mapping: "polPrintDate" -> "printedDate" */
		if(  !Utils.isEmpty( beanA.getPolPrintDate() )  ){
 			beanB.setPrintedDate( beanA.getPolPrintDate() ); 
 		}

 		/* Mapping: "polIssueDate" -> "accountingDate" */
		if(  !Utils.isEmpty( beanA.getPolIssueDate() )  ){
 			beanB.setAccountingDate( beanA.getPolIssueDate() ); 
 		}

 		/* Mapping: "polDocumentCode" -> "txnType" */
		if(  !Utils.isEmpty( beanA.getPolDocumentCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setTxnType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolDocumentCode() ) ) );
  		}

 		/* Mapping: "polLocationCode" -> "locationCode" */
		if(  !Utils.isEmpty( beanA.getPolLocationCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setLocationCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolLocationCode() ) ) );
  		}

 		/* Mapping: "polPreparedBy" -> "createdBy" */
		if(  !Utils.isEmpty( beanA.getPolPreparedBy() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCreatedBy( converter.getTypeOfB().cast(converter.getBFromA(beanA.getPolPreparedBy())));
  		}
 		/* Mapping: "polRefPolicyNo" -> "refPolicyNo" */
		if(  !Utils.isEmpty( beanA.getPolRefPolicyNo() )  ){
 			beanB.setRefPolicyNo(beanA.getPolRefPolicyNo() ); 
 		}
 		/* Mapping: "polRefPolicyYear" -> "refPolicyYear" */
		if(  !Utils.isEmpty( beanA.getPolRefPolicyYear() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setRefPolicyYear( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPolRefPolicyYear() ) ) );
 		}
		/* Mapping: "polRenTermTxt" -> "RenewalTerms" */
		if(  !Utils.isEmpty( beanA.getPolRenTermTxt() )  ){
			beanB.setRenewalTerms( beanA.getPolRenTermTxt() );
 		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.AuthenticationInfoVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnPolicyQuo beanA, com.rsaame.pas.vo.bus.AuthenticationInfoVO beanB ){
               		return beanB;
	}
}
