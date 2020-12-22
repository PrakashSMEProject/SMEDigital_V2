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
 * <li>com.rsaame.pas.vo.bus.CreditNoteDetailsVO</li>
 * <li>com.rsaame.kaizen.policy.model.CreditNoteDetails</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CrNoteDetsToCrNoteDetsVOMapper.class )</code>.
 */
public class CrNoteDetsToCrNoteDetsVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.CreditNoteDetailsVO, com.rsaame.kaizen.policy.model.CreditNoteDetails>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CrNoteDetsToCrNoteDetsVOMapper(){
		super();
	}

	public CrNoteDetsToCrNoteDetsVOMapper( com.rsaame.pas.vo.bus.CreditNoteDetailsVO src, com.rsaame.kaizen.policy.model.CreditNoteDetails dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.policy.model.CreditNoteDetails mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.policy.model.CreditNoteDetails) Utils.newInstance( "com.rsaame.kaizen.policy.model.CreditNoteDetails" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.CreditNoteDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.policy.model.CreditNoteDetails beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "cndPolicyId" -> "cndPolicyId" */
		if(  !Utils.isEmpty( beanA.getCndPolicyNo() )  ){
 			beanB.setCndPolicyNo( beanA.getCndPolicyNo() ); 
 		}

 		/* Mapping: "cndEndtId" -> "cndEndtId" */
		if(  !Utils.isEmpty( beanA.getCndEndtId() )  ){
 			beanB.setCndEndtId( beanA.getCndEndtId() ); 
 		}
 		/* Mapping: "cndPolicyYear" -> "cndPolicyYear" */
		if(  !Utils.isEmpty( beanA.getCndPolicyYear() )  ){
 			beanB.setCndPolicyYear( beanA.getCndPolicyYear() ); 
 		}
 		/* Mapping: "cndCreditNoteNo" -> "comp_id.cndCreditNoteNo" */
		if(  !Utils.isEmpty( beanA.getCndCreditNoteNo() )  ){
 			beanB.getComp_id().setCndCreditNoteNo( beanA.getCndCreditNoteNo() ); 
 		}

 		/* Mapping: "cndCreditNoteDate" -> "comp_id.cndCreditNoteDate" */
		if(  !Utils.isEmpty( beanA.getCndCreditNoteDate() )  ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MMM/yyyy" );
			beanB.getComp_id().setCndCreditNoteDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getCndCreditNoteDate() ) ) );
  		}

		/* Mapping: "cndPolicyId" -> "cndPolicyId" */
		if(  !Utils.isEmpty( beanA.getCndPolicyId() )  ){
 			beanB.setCndPolicyId( beanA.getCndPolicyId() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.kaizen.policy.model.CreditNoteDetails initializeDeepVO( com.rsaame.pas.vo.bus.CreditNoteDetailsVO beanA, com.rsaame.kaizen.policy.model.CreditNoteDetails beanB ){
      		BeanUtils.initializeBeanField( "comp_id", beanB );
    		return beanB;
	}
}
