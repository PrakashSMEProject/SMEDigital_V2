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
 * <li>com.rsaame.pas.dao.model.VTrnTask</li>
 * <li>com.rsaame.pas.vo.bus.TaskVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( VTrnTaskToTaskVOMapper.class )</code>.
 */
public class VTrnTaskToTaskVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VTrnTask, com.rsaame.pas.vo.bus.TaskVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VTrnTaskToTaskVOMapper(){
		super();
	}

	public VTrnTaskToTaskVOMapper( com.rsaame.pas.dao.model.VTrnTask src, com.rsaame.pas.vo.bus.TaskVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TaskVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TaskVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TaskVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.VTrnTask beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TaskVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.tskId" -> "taskID" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getTskId() )  ){
 			beanB.setTaskID( beanA.getId().getTskId() ); 
 		}

 		/* Mapping: "tskShortDesc" -> "taskName" */
		if(  !Utils.isEmpty( beanA.getTskShortDesc() )  ){
 			beanB.setTaskName( beanA.getTskShortDesc() ); 
 		}

 		/* Mapping: "tskCreatedDate" -> "createdDate" */
		if(  !Utils.isEmpty( beanA.getTskCreatedDate() )  ){
 			beanB.setCreatedDate( beanA.getTskCreatedDate() ); 
 		}

 		/* Mapping: "tskPriority" -> "Priority" */
		if(  !Utils.isEmpty( beanA.getTskPriority() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setPriority( converter.getTypeOfA().cast( converter.getAFromB( beanA.getTskPriority() ) ) );
  		}

 		/* Mapping: "tskTargetDate" -> "dueDate" */
		if(  !Utils.isEmpty( beanA.getTskTargetDate() )  ){
 			beanB.setDueDate( beanA.getTskTargetDate() ); 
 		}

 		/* Mapping: "tskDescription" -> "desc" */
		if(  !Utils.isEmpty( beanA.getTskDescription() )  ){
 			beanB.setDesc( beanA.getTskDescription() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TaskVO initializeDeepVO( com.rsaame.pas.dao.model.VTrnTask beanA, com.rsaame.pas.vo.bus.TaskVO beanB ){
             		return beanB;
	}
}
