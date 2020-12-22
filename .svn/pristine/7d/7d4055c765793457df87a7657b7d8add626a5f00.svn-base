/*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package gen.b2b;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.mindtree.devtools.b2b.sample.BeanA</li>
 * <li>com.mindtree.devtools.b2b.sample.BeanB</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BeanAToBeanBMapper.class )</code>.
 */
public class BeanAToBeanBMapper extends BaseBeanToBeanMapper<com.mindtree.devtools.b2b.sample.BeanA, com.mindtree.devtools.b2b.sample.BeanB>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BeanAToBeanBMapper(){
		super();
	}

	public BeanAToBeanBMapper( com.mindtree.devtools.b2b.sample.BeanA src, com.mindtree.devtools.b2b.sample.BeanB dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.mindtree.devtools.b2b.sample.BeanB mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.mindtree.devtools.b2b.sample.BeanB) Utils.newInstance( "com.mindtree.devtools.b2b.sample.BeanB" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.mindtree.devtools.b2b.sample.BeanA beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.mindtree.devtools.b2b.sample.BeanB beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanB);
		
		/* The following represent the bean copy methods */		
				{
		//This is a mapping for an attribute which is not a collection	
		/* beanB.setIntprimitive( beanA.getIntprimitive()); */
/*   */
 		beanB.setIntprimitive( beanA.getIntprimitive() ); 
 
		}
		

 		{
		//This is a mapping for an attribute which is not a collection	
		/* beanB.getComplexObject().setDate( beanA.getDate()); */
/*   */
 		beanB.getComplexObject().setDate( beanA.getDate() ); 
 
		}
		

 		if(  !Utils.isEmpty( beanA.getComplexObject() )  ){
		//This is a mapping for an attribute which is not a collection	
		/* beanB.setStr( beanA.getComplexObject().getString()); */
/*   */
 		beanB.setStr( beanA.getComplexObject().getString() ); 
 
		}
		

 		{
		//This is a mapping for an attribute which is not a collection	
		/* beanB.setIntegerObject( beanA.getIntegerObject()); */
/*   */
 		beanB.setIntegerObject( beanA.getIntegerObject() ); 
 
		}
		

 		{
		//This is a mapping for an attribute which is not a collection	
		/* beanB.getComplexObject().getBeanB().setStr( beanA.getStr()); */
/*   */
 		beanB.getComplexObject().getBeanB().setStr( beanA.getStr() ); 
 
		}
		

 		
		//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
		beanB.setStringList( CopyUtils.copy( beanA.getStringList(), beanB.getStringList() ));	

 		{
			//This is a mapping for an attribute which is not a collection
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( "ComplexObjectMapper", beanA.getComplexObject() );
			mapper.mapBean( beanA.getComplexObject(), beanB.getComplexObject() );
		}
	   	
 		
		//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
		beanB.setComplexObjectList( CopyUtils.copy( beanA.getComplexObjectList(), beanB.getComplexObjectList() ));	

 		
		//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
		beanB.setComplexObjectSet( CopyUtils.copy( beanA.getComplexObjectSet(), beanB.getComplexObjectSet() ));	

 		
		//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
		beanB.setComplexObjectArray( CopyUtils.copy( beanA.getComplexObjectArray(), beanB.getComplexObjectArray() ));	

   
		return dest;
	}	  

	/**
	 * This method initializes the deepVOs inside the destination bean.
	 * 
	 * @param beanB
	 * @return beanB
	 */
	private static com.mindtree.devtools.b2b.sample.BeanB initializeDeepVO( com.mindtree.devtools.b2b.sample.BeanB beanB ){	
		BeanUtils.initializeBeanField( "complexObject.date", beanB );
		BeanUtils.initializeBeanField( "complexObject.beanB.str", beanB );
		return beanB;
	}
}