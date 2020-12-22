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
 * <li>com.rsaame.kaizen.policy.model.DebitNoteDetails</li>
 * <li>com.rsaame.pas.vo.bus.DebitNoteDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( DrNoteDetsToDrNoteDetsVOMapperReverse.class )</code>.
 */
public class DrNoteDetsToDrNoteDetsVOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.kaizen.policy.model.DebitNoteDetails, com.rsaame.pas.vo.bus.DebitNoteDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public DrNoteDetsToDrNoteDetsVOMapperReverse(){
		super();
	}

	public DrNoteDetsToDrNoteDetsVOMapperReverse( com.rsaame.kaizen.policy.model.DebitNoteDetails src, com.rsaame.pas.vo.bus.DebitNoteDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.DebitNoteDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.DebitNoteDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.DebitNoteDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.policy.model.DebitNoteDetails beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.DebitNoteDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "dndPolicyId" -> "dndPolicyId" */
		if(  !Utils.isEmpty( beanA.getDndPolicyNo() )  ){
 			beanB.setDndPolicyNo( beanA.getDndPolicyNo() ); 
 		}

 		/* Mapping: "dndEndtId" -> "dndEndtId" */
		if(  !Utils.isEmpty( beanA.getDndEndtId() )  ){
 			beanB.setDndEndtId( beanA.getDndEndtId() ); 
 		}
 		/* Mapping: "dndPolicyYear" -> "dndPolicyYear" */
		if(  !Utils.isEmpty( beanA.getDndPolicyYear() )  ){
 			beanB.setDndPolicyYear(beanA.getDndPolicyYear() ); 
 		}
 		/* Mapping: "comp_id.dndDebitNoteNo" -> "dndDebitNoteNo" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getDndDebitNoteNo() )  ){
 			beanB.setDndDebitNoteNo( beanA.getComp_id().getDndDebitNoteNo() ); 
 		}

 		/* Mapping: "comp_id.dndDebitNoteDate" -> "dndDebitNoteDate" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getDndDebitNoteDate() )  ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MMM/yyyy" );
			beanB.setDndDebitNoteDate( converter.getTypeOfB().cast( converter.getBFromA( beanA.getComp_id().getDndDebitNoteDate() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.DebitNoteDetailsVO initializeDeepVO( com.rsaame.kaizen.policy.model.DebitNoteDetails beanA, com.rsaame.pas.vo.bus.DebitNoteDetailsVO beanB ){
         		return beanB;
	}
}
