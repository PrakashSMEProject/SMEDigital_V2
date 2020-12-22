       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.TaskVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToTaskVOMapper.class )</code>.
 */
public class RequestToTaskVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.TaskVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToTaskVOMapper(){
		super();
	}

	public RequestToTaskVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.TaskVO dest ){
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
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TaskVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "newTaskName" -> "taskName" */
		if( !Utils.isEmpty( src.getParameter( "newTaskName" ) ) ){
 			beanB.setTaskName( beanA.getParameter( "newTaskName" ) ); 
 		}

 		/* Mapping: "newTaskAssignedTo" -> "assignedTo" */
		if( !Utils.isEmpty( src.getParameter( "newTaskAssignedTo" ) ) ){
 			beanB.setAssignedTo( beanA.getParameter( "newTaskAssignedTo" ) ); 
 		}

 		/* Mapping: "newTaskDueDate" -> "dueDate" */
		if( !Utils.isEmpty( src.getParameter( "newTaskDueDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setDueDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "newTaskDueDate" ) ) ) );
  		}

 		/* Mapping: "newTaskPriority" -> "Priority" */
		if( !Utils.isEmpty( src.getParameter( "newTaskPriority" ) ) ){
 			beanB.setPriority( beanA.getParameter( "newTaskPriority" ) ); 
 		}

 		/* Mapping: "newTaskDesc" -> "desc" */
		if( !Utils.isEmpty( src.getParameter( "newTaskDesc" ) ) ){
 			beanB.setDesc( beanA.getParameter( "newTaskDesc" ) ); 
 		}
		
		/* Mapping: "viewTaskId" -> "taskID" */
		if( !Utils.isEmpty( src.getParameter( "viewTaskId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setTaskID( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "viewTaskId" ) ) ) );
  		}
		
		/* Mapping: "task_Status" -> "Status" */
		if( !Utils.isEmpty( src.getParameter( "task_Status" ) ) ){
 			beanB.setStatus( beanA.getParameter( "task_Status" ) ); 
 		}
			
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TaskVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.TaskVO beanB ){
           		return beanB;
	}
}
