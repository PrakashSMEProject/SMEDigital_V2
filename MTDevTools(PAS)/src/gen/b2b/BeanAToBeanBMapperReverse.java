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
 * <li>com.mindtree.devtools.b2b.sample.BeanB</li>
 * <li>com.mindtree.devtools.b2b.sample.BeanA</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BeanAToBeanBMapperReverse.class )</code>.
 */
public class BeanAToBeanBMapperReverse extends BaseBeanToBeanMapper<com.mindtree.devtools.b2b.sample.BeanB, com.mindtree.devtools.b2b.sample.BeanA>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BeanAToBeanBMapperReverse(){
		super();
	}

	public BeanAToBeanBMapperReverse( com.mindtree.devtools.b2b.sample.BeanB src, com.mindtree.devtools.b2b.sample.BeanA dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.mindtree.devtools.b2b.sample.BeanA mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.mindtree.devtools.b2b.sample.BeanA) Utils.newInstance( "com.mindtree.devtools.b2b.sample.BeanA" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.mindtree.devtools.b2b.sample.BeanB beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.mindtree.devtools.b2b.sample.BeanA beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanB);
		
		/* The following represent the bean copy methods */		
				{
		//This is a mapping for an attribute which is not a collection	
		/* beanB.setIntprimitive( beanA.getIntprimitive()); */
/*   */
 		beanB.setIntprimitive( beanA.getIntprimitive() ); 
 
		}
		

 		if(  !Utils.isEmpty( beanA.getComplexObject() )  ){
		//This is a mapping for an attribute which is not a collection	
		/* beanB.setDate( beanA.getComplexObject().getDate()); */
/*   */
 		beanB.setDate( beanA.getComplexObject().getDate() ); 
 
		}
		

 		{
		//This is a mapping for an attribute which is not a collection	
		/* beanB.getComplexObject().setString( beanA.getStr()); */
/*   */
 		beanB.getComplexObject().setString( beanA.getStr() ); 
 
		}
		

 		{
		//This is a mapping for an attribute which is not a collection	
		/* beanB.setIntegerObject( beanA.getIntegerObject()); */
/*   */
 		beanB.setIntegerObject( beanA.getIntegerObject() ); 
 
		}
		

 		if(  !Utils.isEmpty( beanA.getComplexObject() ) && !Utils.isEmpty( beanA.getComplexObject().getBeanB() )  ){
		//This is a mapping for an attribute which is not a collection	
		/* beanB.setStr( beanA.getComplexObject().getBeanB().getStr()); */
/*   */
 		beanB.setStr( beanA.getComplexObject().getBeanB().getStr() ); 
 
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
	private static com.mindtree.devtools.b2b.sample.BeanA initializeDeepVO( com.mindtree.devtools.b2b.sample.BeanA beanB ){	
		BeanUtils.initializeBeanField( "complexObject.string", beanB );
		return beanB;
	}
}