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
 * <li>com.rsaame.kaizen.policy.model.DetailReceipt</li>
 * <li>com.rsaame.pas.vo.bus.ReceiptDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RcptDetsToRcptVOMapperReverse.class )</code>.
 */
public class RcptDetsToRcptVOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.kaizen.policy.model.DetailReceipt, com.rsaame.pas.vo.bus.ReceiptDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RcptDetsToRcptVOMapperReverse(){
		super();
	}

	public RcptDetsToRcptVOMapperReverse( com.rsaame.kaizen.policy.model.DetailReceipt src, com.rsaame.pas.vo.bus.ReceiptDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.ReceiptDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.ReceiptDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.ReceiptDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.policy.model.DetailReceipt beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.ReceiptDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "rcdPolicyNo" -> "rcdPolicyId" */
		if(  !Utils.isEmpty( beanA.getRcdPolicyNo() )  ){
 			beanB.setRcdPolicyNo( beanA.getRcdPolicyNo() ); 
 		}

 		/* Mapping: "rcdEndtId" -> "rcdEndtId" */
		if(  !Utils.isEmpty( beanA.getRcdEndtId() )  ){
 			beanB.setRcdEndtId( beanA.getRcdEndtId() ); 
 		}

 		/* Mapping: "comp_id.rcdReceiptNo" -> "rcdReceiptNo" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getRcdReceiptNo() )  ){
 			beanB.setRcdReceiptNo( beanA.getComp_id().getRcdReceiptNo() ); 
 		}

 		/* Mapping: "comp_id.rcdReceiptDate" -> "rcdReceiptDate" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getRcdReceiptDate() )  ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MMM/yyyy" );
			beanB.setRcdReceiptDate( converter.getTypeOfB().cast( converter.getBFromA( beanA.getComp_id().getRcdReceiptDate() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.ReceiptDetailsVO initializeDeepVO( com.rsaame.kaizen.policy.model.DetailReceipt beanA, com.rsaame.pas.vo.bus.ReceiptDetailsVO beanB ){
         		return beanB;
	}
}
