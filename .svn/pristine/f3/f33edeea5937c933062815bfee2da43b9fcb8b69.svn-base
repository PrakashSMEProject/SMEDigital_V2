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
 * <li>com.rsaame.pas.vo.bus.AuthenticationInfoVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PolicyQuoToAuthVoMapReverse.class )</code>.
 */
public class PolicyQuoToAuthVoMapReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.AuthenticationInfoVO, com.rsaame.pas.dao.model.TTrnPolicyQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PolicyQuoToAuthVoMapReverse(){
		super();
	}

	public PolicyQuoToAuthVoMapReverse( com.rsaame.pas.vo.bus.AuthenticationInfoVO src, com.rsaame.pas.dao.model.TTrnPolicyQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPolicyQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPolicyQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPolicyQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.AuthenticationInfoVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "licensedBy" -> "polUserId" */
		if(  !Utils.isEmpty( beanA.getLicensedBy() )  ){
 			beanB.setPolUserId( beanA.getLicensedBy() ); 
 		}

 		/* Mapping: "approvedBy" -> "polApprovedBy" */
		if(  !Utils.isEmpty( beanA.getApprovedBy() )  ){
 			beanB.setPolApprovedBy( beanA.getApprovedBy() ); 
 		}

 		/* Mapping: "approvedDt" -> "polApprovalDate" */
		if(  !Utils.isEmpty( beanA.getApprovedDt() )  ){
 			beanB.setPolApprovalDate( beanA.getApprovedDt() ); 
 		}

 		/* Mapping: "printedDate" -> "polPrintDate" */
		if(  !Utils.isEmpty( beanA.getPrintedDate() )  ){
 			beanB.setPolPrintDate( beanA.getPrintedDate() ); 
 		}

 		/* Mapping: "accountingDate" -> "polIssueDate" */
		if(  !Utils.isEmpty( beanA.getAccountingDate() )  ){
 			beanB.setPolIssueDate( beanA.getAccountingDate() ); 
 		}

 		/* Mapping: "txnType" -> "polDocumentCode" */
		if(  !Utils.isEmpty( beanA.getTxnType() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolDocumentCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getTxnType() ) ) );
  		}

 		/* Mapping: "locationCode" -> "polLocationCode" */
		if(  !Utils.isEmpty( beanA.getLocationCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolLocationCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getLocationCode() ) ) );
  		}

 		/* Mapping: "createdBy" -> "polPreparedBy" */
		if(  !Utils.isEmpty( beanA.getCreatedBy() )  ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setPolPreparedBy( converter.getTypeOfA().cast(converter.getAFromB(beanA.getCreatedBy())));
  		}
		if(  !Utils.isEmpty( beanA.getRefPolicyNo() )  ){
			beanB.setPolRefPolicyNo( beanA.getRefPolicyNo() );
  		}
		if(  !Utils.isEmpty( beanA.getRefPolicyYear() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setPolRefPolicyYear( converter.getTypeOfB().cast( converter.getBFromA( beanA.getRefPolicyYear() ) ) );
  		}
		/* Mapping: "renewalTerms" -> "polrenTermTxt" */
		if(  !Utils.isEmpty(  beanA.getRenewalTerms() )  ){
			beanB.setPolRenTermTxt(  beanA.getRenewalTerms() );
  		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPolicyQuo initializeDeepVO( com.rsaame.pas.vo.bus.AuthenticationInfoVO beanA, com.rsaame.pas.dao.model.TTrnPolicyQuo beanB ){
               		return beanB;
	}
}
