       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.app.TaskListVO</li>
 * <li>com.rsaame.kaizen.taskmanager.model.TTrnTask</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TaskListVOToTtrnTaskMapper.class )</code>.
 */
public class TaskListVOToTtrnTaskMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.app.TaskListVO, com.rsaame.kaizen.taskmanager.model.TTrnTask>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TaskListVOToTtrnTaskMapper(){
		super();
	}

	public TaskListVOToTtrnTaskMapper( com.rsaame.pas.vo.app.TaskListVO src, com.rsaame.kaizen.taskmanager.model.TTrnTask dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.taskmanager.model.TTrnTask mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.taskmanager.model.TTrnTask) Utils.newInstance( "com.rsaame.kaizen.taskmanager.model.TTrnTask" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.app.TaskListVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.taskmanager.model.TTrnTask beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
 		
 		

		/* Mapping: "currentPage" -> "currentPage" */
		if(  !Utils.isEmpty( beanA.getCurrentPage() )  ){
 			beanB.setCurrentPage( beanA.getCurrentPage() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.kaizen.taskmanager.model.TTrnTask initializeDeepVO( com.rsaame.pas.vo.app.TaskListVO beanA, com.rsaame.kaizen.taskmanager.model.TTrnTask beanB ){
   		return beanB;
	}
}
