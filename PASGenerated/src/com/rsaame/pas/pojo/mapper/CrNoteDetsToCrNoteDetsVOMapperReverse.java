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
 * <li>com.rsaame.kaizen.policy.model.CreditNoteDetails</li>
 * <li>com.rsaame.pas.vo.bus.CreditNoteDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CrNoteDetsToCrNoteDetsVOMapperReverse.class )</code>.
 */
public class CrNoteDetsToCrNoteDetsVOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.kaizen.policy.model.CreditNoteDetails, com.rsaame.pas.vo.bus.CreditNoteDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CrNoteDetsToCrNoteDetsVOMapperReverse(){
		super();
	}

	public CrNoteDetsToCrNoteDetsVOMapperReverse( com.rsaame.kaizen.policy.model.CreditNoteDetails src, com.rsaame.pas.vo.bus.CreditNoteDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CreditNoteDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CreditNoteDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.CreditNoteDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.policy.model.CreditNoteDetails beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CreditNoteDetailsVO beanB = dest;
			
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
		if(  !Utils.isEmpty( beanA.getCndPolicyYear())){
 			beanB.setCndPolicyYear( beanA.getCndPolicyYear() );
 		}
		
 		/* Mapping: "comp_id.cndCreditNoteNo" -> "cndCreditNoteNo" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getCndCreditNoteNo() )  ){
 			beanB.setCndCreditNoteNo( beanA.getComp_id().getCndCreditNoteNo() ); 
 		}

 		/* Mapping: "comp_id.cndCreditNoteDate" -> "cndCreditNoteDate" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getCndCreditNoteDate() )  ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MMM/yyyy" );
			beanB.setCndCreditNoteDate( converter.getTypeOfB().cast( converter.getBFromA( beanA.getComp_id().getCndCreditNoteDate() ) ) );
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
	private static com.rsaame.pas.vo.bus.CreditNoteDetailsVO initializeDeepVO( com.rsaame.kaizen.policy.model.CreditNoteDetails beanA, com.rsaame.pas.vo.bus.CreditNoteDetailsVO beanB ){
         		return beanB;
	}
}
