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
 * <li>com.mindtree.devtools.b2b.sample.DestBean</li>
 * <li>com.mindtree.devtools.b2b.sample.SourceBean</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( SourceDestBeanMapperReverse.class )</code>.
 */
public class SourceDestBeanMapperReverse extends BaseBeanToBeanMapper<com.mindtree.devtools.b2b.sample.DestBean, com.mindtree.devtools.b2b.sample.SourceBean>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public SourceDestBeanMapperReverse(){
		super();
	}

	public SourceDestBeanMapperReverse( com.mindtree.devtools.b2b.sample.DestBean src, com.mindtree.devtools.b2b.sample.SourceBean dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.mindtree.devtools.b2b.sample.SourceBean mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.mindtree.devtools.b2b.sample.SourceBean) Utils.newInstance( "com.mindtree.devtools.b2b.sample.SourceBean" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.mindtree.devtools.b2b.sample.DestBean beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.mindtree.devtools.b2b.sample.SourceBean beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanB);
		
		/* The following represent the bean copy methods */		
				{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCList() ).get( 0 ) );
			beanB.setBeanAList( CopyUtils.copy( beanA.getBeanCList(), beanB.getBeanAList(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCArray() ).get( 0 ) );
			beanB.setBeanAList( CopyUtils.copy( beanA.getBeanCArray(), beanB.getBeanAList(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCSet() ).get( 0 ) );
			beanB.setBeanAList( CopyUtils.copy( beanA.getBeanCSet(), beanB.getBeanAList(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCArray() ).get( 0 ) );
			beanB.setBeanAArray( CopyUtils.copy( beanA.getBeanCArray(), beanB.getBeanAArray(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCList() ).get( 0 ) );
			beanB.setBeanAArray( CopyUtils.copy( beanA.getBeanCList(), beanB.getBeanAArray(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCSet() ).get( 0 ) );
			beanB.setBeanAArray( CopyUtils.copy( beanA.getBeanCSet(), beanB.getBeanAArray(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCSet() ).get( 0 ) );
			beanB.setBeanASet( CopyUtils.copy( beanA.getBeanCSet(), beanB.getBeanASet(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCList() ).get( 0 ) );
			beanB.setBeanASet( CopyUtils.copy( beanA.getBeanCList(), beanB.getBeanASet(), mapper.getClass() ));
		}
 		{
			//This is a mapping for attribute which is a collection of complex data type or simple data type where the source and destination collection are of same type
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( com.Constant.CONST_BEANATOBEANCMAPPER, CopyUtils.asList( beanA.getBeanCArray() ).get( 0 ) );
			beanB.setBeanASet( CopyUtils.copy( beanA.getBeanCArray(), beanB.getBeanASet(), mapper.getClass() ));
		}
   
		return dest;
	}	  

	/**
	 * This method initializes the deepVOs inside the destination bean.
	 * 
	 * @param beanB
	 * @return beanB
	 */
	private static com.mindtree.devtools.b2b.sample.SourceBean initializeDeepVO( com.mindtree.devtools.b2b.sample.SourceBean beanB ){	
		return beanB;
	}
}
