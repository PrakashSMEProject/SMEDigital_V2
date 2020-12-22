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
 * <li>com.rsaame.pas.vo.bus.TaskVO</li>
 * <li>com.rsaame.pas.dao.model.VTrnTask</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( VTrnTaskToTaskVOMapperReverse.class )</code>.
 */
public class VTrnTaskToTaskVOMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.TaskVO, com.rsaame.pas.dao.model.VTrnTask>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VTrnTaskToTaskVOMapperReverse(){
		super();
	}

	public VTrnTaskToTaskVOMapperReverse( com.rsaame.pas.vo.bus.TaskVO src, com.rsaame.pas.dao.model.VTrnTask dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.VTrnTask mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.VTrnTask) Utils.newInstance( "com.rsaame.pas.dao.model.VTrnTask" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.TaskVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.VTrnTask beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "taskID" -> "id.tskId" */
		if(  !Utils.isEmpty( beanA.getTaskID() )  ){
 			beanB.getId().setTskId( beanA.getTaskID() ); 
 		}

 		/* Mapping: "taskName" -> "tskShortDesc" */
		if(  !Utils.isEmpty( beanA.getTaskName() )  ){
 			beanB.setTskShortDesc( beanA.getTaskName() ); 
 		}

 		/* Mapping: "createdDate" -> "tskCreatedDate" */
		if(  !Utils.isEmpty( beanA.getCreatedDate() )  ){
 			beanB.setTskCreatedDate( beanA.getCreatedDate() ); 
 		}

 		/* Mapping: "Priority" -> "tskPriority" */
		if(  !Utils.isEmpty( beanA.getPriority() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setTskPriority( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPriority() ) ) );
  		}

 		/* Mapping: "dueDate" -> "tskTargetDate" */
		if(  !Utils.isEmpty( beanA.getDueDate() )  ){
 			beanB.setTskTargetDate( beanA.getDueDate() ); 
 		}

 		/* Mapping: "desc" -> "tskDescription" */
		if(  !Utils.isEmpty( beanA.getDesc() )  ){
 			beanB.setTskDescription( beanA.getDesc() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.VTrnTask initializeDeepVO( com.rsaame.pas.vo.bus.TaskVO beanA, com.rsaame.pas.dao.model.VTrnTask beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
            		return beanB;
	}
}
