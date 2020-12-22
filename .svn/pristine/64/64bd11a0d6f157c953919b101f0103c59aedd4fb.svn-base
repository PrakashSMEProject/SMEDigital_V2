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
 * <li>com.rsaame.pas.vo.bus.ReceiptDetailsVO</li>
 * <li>com.rsaame.kaizen.policy.model.DetailReceipt</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RcptDetsToRcptVOMapper.class )</code>.
 */
public class RcptDetsToRcptVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.ReceiptDetailsVO, com.rsaame.kaizen.policy.model.DetailReceipt>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RcptDetsToRcptVOMapper(){
		super();
	}

	public RcptDetsToRcptVOMapper( com.rsaame.pas.vo.bus.ReceiptDetailsVO src, com.rsaame.kaizen.policy.model.DetailReceipt dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.policy.model.DetailReceipt mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.policy.model.DetailReceipt) Utils.newInstance( "com.rsaame.kaizen.policy.model.DetailReceipt" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.ReceiptDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.policy.model.DetailReceipt beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "rcdPolicyNo" -> "rcdPolicyId" */
		if(  !Utils.isEmpty( beanA.getRcdPolicyNo() )  ){
 			beanB.setRcdPolicyNo( beanA.getRcdPolicyNo() ); 
 		}

		/* Mapping: "rcdPolicyId" -> "rcdPolicyId" */
		if(  !Utils.isEmpty( beanA.getRcdPolicyId() )  ){
 			beanB.setRcdPolicyId( beanA.getRcdPolicyId() ); 
 		}
		
 		/* Mapping: "rcdEndtId" -> "rcdEndtId" */
		if(  !Utils.isEmpty( beanA.getRcdEndtId() )  ){
 			beanB.setRcdEndtId( beanA.getRcdEndtId() ); 
 		}

 		/* Mapping: "rcdReceiptNo" -> "comp_id.rcdReceiptNo" */
		if(  !Utils.isEmpty( beanA.getRcdReceiptNo() )  ){
 			beanB.getComp_id().setRcdReceiptNo( beanA.getRcdReceiptNo() ); 
 		}

 		/* Mapping: "rcdReceiptDate" -> "comp_id.rcdReceiptDate" */
		if(  !Utils.isEmpty( beanA.getRcdReceiptDate() )  ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MMM/yyyy" );
			beanB.getComp_id().setRcdReceiptDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getRcdReceiptDate() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.kaizen.policy.model.DetailReceipt initializeDeepVO( com.rsaame.pas.vo.bus.ReceiptDetailsVO beanA, com.rsaame.kaizen.policy.model.DetailReceipt beanB ){
      		BeanUtils.initializeBeanField( "comp_id", beanB );
    		return beanB;
	}
}
