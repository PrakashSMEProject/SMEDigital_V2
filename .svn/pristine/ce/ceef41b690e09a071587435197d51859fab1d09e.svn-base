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
 * <li>com.rsaame.pas.vo.bus.DebitNoteDetailsVO</li>
 * <li>com.rsaame.kaizen.policy.model.DebitNoteDetails</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( DrNoteDetsToDrNoteDetsVOMapper.class )</code>.
 */
public class DrNoteDetsToDrNoteDetsVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.DebitNoteDetailsVO, com.rsaame.kaizen.policy.model.DebitNoteDetails>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public DrNoteDetsToDrNoteDetsVOMapper(){
		super();
	}

	public DrNoteDetsToDrNoteDetsVOMapper( com.rsaame.pas.vo.bus.DebitNoteDetailsVO src, com.rsaame.kaizen.policy.model.DebitNoteDetails dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.policy.model.DebitNoteDetails mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.policy.model.DebitNoteDetails) Utils.newInstance( "com.rsaame.kaizen.policy.model.DebitNoteDetails" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.DebitNoteDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.policy.model.DebitNoteDetails beanB = dest;
			
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
		if(  !Utils.isEmpty( beanA.getDndPolicyYear())  ){
 			beanB.setDndPolicyYear(beanA.getDndPolicyYear() ); 
 		}

 		/* Mapping: "dndDebitNoteNo" -> "comp_id.dndDebitNoteNo" */
		if(  !Utils.isEmpty( beanA.getDndDebitNoteNo() )  ){
 			beanB.getComp_id().setDndDebitNoteNo( beanA.getDndDebitNoteNo() ); 
 		}

 		/* Mapping: "dndDebitNoteDate" -> "comp_id.dndDebitNoteDate" */
		if(  !Utils.isEmpty( beanA.getDndDebitNoteDate() )  ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MMM/yyyy" );
			beanB.getComp_id().setDndDebitNoteDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getDndDebitNoteDate() ) ) );
  		}
		
		/* Mapping: "dndPolicyId" -> "dndPolicyId" */
		if(  !Utils.isEmpty( beanA.getDndPolicyId() )  ){
 			beanB.setDndPolicyId( beanA.getDndPolicyId() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.kaizen.policy.model.DebitNoteDetails initializeDeepVO( com.rsaame.pas.vo.bus.DebitNoteDetailsVO beanA, com.rsaame.kaizen.policy.model.DebitNoteDetails beanB ){
      		BeanUtils.initializeBeanField( "comp_id", beanB );
    		return beanB;
	}
}
