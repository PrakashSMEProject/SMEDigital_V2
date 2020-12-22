       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnUwQuestionsQuo</li>
 * <li>com.rsaame.pas.vo.bus.UWQuestionVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( UWQuestionsVOToTtrnUWQuestionsQuoMapperReverse.class )</code>.
 */
public class UWQuestionsVOToTtrnUWQuestionsQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnUwQuestionsQuo, com.rsaame.pas.vo.bus.UWQuestionVO>{
	@SuppressWarnings( "unused" )
	private final Logger log = Logger.getLogger( this.getClass() );	

	public UWQuestionsVOToTtrnUWQuestionsQuoMapperReverse(){
		super();
	}

	public UWQuestionsVOToTtrnUWQuestionsQuoMapperReverse( com.rsaame.pas.dao.model.TTrnUwQuestionsQuo src, com.rsaame.pas.vo.bus.UWQuestionVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.UWQuestionVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.UWQuestionVO) Utils.newInstance( "com.rsaame.pas.vo.bus.UWQuestionVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.UWQuestionVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* Mapping: "id.uqtUwqCode" -> "qId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getUqtUwqCode() )  ){
 			beanB.setQId( beanA.getId().getUqtUwqCode() ); 
 		}

 		/* Mapping: "uqtUwqAnswer" -> "response" */
		if(  !Utils.isEmpty( beanA.getUqtUwqAnswer() )  ){
 			beanB.setResponse( beanA.getUqtUwqAnswer() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.UWQuestionVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanA, com.rsaame.pas.vo.bus.UWQuestionVO beanB ){
     		return beanB;
	}
}
