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
 * <li>com.mindtree.devtools.b2b.sample.BeanC</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BeanAToBeanCMapper.class )</code>.
 */
public class BeanAToBeanCMapper extends BaseBeanToBeanMapper<com.mindtree.devtools.b2b.sample.BeanA, com.mindtree.devtools.b2b.sample.BeanC>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BeanAToBeanCMapper(){
		super();
	}

	public BeanAToBeanCMapper( com.mindtree.devtools.b2b.sample.BeanA src, com.mindtree.devtools.b2b.sample.BeanC dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.mindtree.devtools.b2b.sample.BeanC mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.mindtree.devtools.b2b.sample.BeanC) Utils.newInstance( "com.mindtree.devtools.b2b.sample.BeanC" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.mindtree.devtools.b2b.sample.BeanA beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.mindtree.devtools.b2b.sample.BeanC beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "date" -> "string" */
		if(  !Utils.isEmpty( beanA.getDate() )  ){
 			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=MM/dd/yyyy" );
			beanB.setString( converter.getTypeOfB().cast( converter.getBFromA( beanA.getDate() ) ) );
  		}

 		/* Mapping: "beanABoolean" -> "levelTwo.yes" */
		if(  !Utils.isEmpty( beanA.getBeanABoolean() )  ){
 			beanB.getLevelTwo().setYes( beanA.isBeanABoolean() ); 
 		}

 		/* Mapping: "strArray[]" -> "complexObjectList[].date" */
  		int noOfItems = beanA.getStrArray().length;
   		for( int i = 0; i < noOfItems; i++ ){
          			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getComplexObjectList().get( i ).setDate ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getStrArray()[ i ] ) ) );
 		}

 		/* Mapping: "complexObjectList[0].string" -> "complexObjectArray[1].string" */
		if(  !Utils.isEmpty( beanA.getComplexObjectList() ) && !Utils.isEmpty( beanA.getComplexObjectList().get(0) ) && !Utils.isEmpty( beanA.getComplexObjectList().get(0).getString() )  ){
 			beanB.getComplexObjectArray()[1].setString( beanA.getComplexObjectList().get(0).getString() ); 
 		}

 		/* Mapping: "complexObjectArray[0].string" -> "complexObjectArray[2].string" */
		if(  !Utils.isEmpty( beanA.getComplexObjectArray() ) && !Utils.isEmpty( beanA.getComplexObjectArray()[0] ) && !Utils.isEmpty( beanA.getComplexObjectArray()[0].getString() )  ){
 			beanB.getComplexObjectArray()[2].setString( beanA.getComplexObjectArray()[0].getString() ); 
 		}

 		/* Mapping: "strLevelTwoMap['KEY'].yesString" -> "complexObjectArray[3].string" */
		if(  !Utils.isEmpty( beanA.getStrLevelTwoMap() ) && !Utils.isEmpty( beanA.getStrLevelTwoMap().get( Utils.newInstance( ( (com.mindtree.ruc.cmn.utils.Map) beanA.getStrLevelTwoMap()).getKeyClass(), "KEY" )) ) && !Utils.isEmpty( beanA.getStrLevelTwoMap().get( Utils.newInstance( ( (com.mindtree.ruc.cmn.utils.Map) beanA.getStrLevelTwoMap()).getKeyClass(), "KEY" )).getYesString() )  ){
 			beanB.getComplexObjectArray()[3].setString( beanA.getStrLevelTwoMap().get( Utils.newInstance( ( (com.mindtree.ruc.cmn.utils.Map) beanA.getStrLevelTwoMap()).getKeyClass(), "KEY" )).getYesString() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.mindtree.devtools.b2b.sample.BeanC initializeDeepVO( com.mindtree.devtools.b2b.sample.BeanA beanA, com.mindtree.devtools.b2b.sample.BeanC beanB ){
    		BeanUtils.initializeBeanField( "levelTwo", beanB );
   		BeanUtils.initializeBeanField( "complexObjectList[]", beanB, beanA.getStrArray().length );
   		BeanUtils.initializeBeanField( "complexObjectArray[1]", beanB );
   		BeanUtils.initializeBeanField( "complexObjectArray[2]", beanB );
   		BeanUtils.initializeBeanField( "complexObjectArray[3]", beanB );
  		return beanB;
	}
}
