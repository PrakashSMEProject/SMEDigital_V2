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
 * <li>com.rsaame.pas.vo.bus.UWQuestionVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnUwQuestionsQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( UWQuestionsVOToTtrnUWQuestionsQuoMapper.class )</code>.
 */
public class UWQuestionsVOToTtrnUWQuestionsQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.UWQuestionVO, com.rsaame.pas.dao.model.TTrnUwQuestionsQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public UWQuestionsVOToTtrnUWQuestionsQuoMapper(){
		super();
	}

	public UWQuestionsVOToTtrnUWQuestionsQuoMapper( com.rsaame.pas.vo.bus.UWQuestionVO src, com.rsaame.pas.dao.model.TTrnUwQuestionsQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnUwQuestionsQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnUwQuestionsQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnUwQuestionsQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.UWQuestionVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "qId" -> "id.uqtUwqCode" */
		if(  !Utils.isEmpty( beanA.getQId() )  ){
 			beanB.getId().setUqtUwqCode( beanA.getQId() ); 
 		}

 		/* Mapping: "response" -> "uqtUwqAnswer" */
		if(  !Utils.isEmpty( beanA.getResponse() )  ){
 			beanB.setUqtUwqAnswer( beanA.getResponse() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnUwQuestionsQuo initializeDeepVO( com.rsaame.pas.vo.bus.UWQuestionVO beanA, com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
    		return beanB;
	}
}
