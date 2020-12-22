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
 * <li>com.mindtree.devtools.b2b.sample.BeanC</li>
 * <li>com.mindtree.devtools.b2b.sample.BeanA</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BeanAToBeanCMapperReverse.class )</code>.
 */
public class BeanAToBeanCMapperReverse extends BaseBeanToBeanMapper<com.mindtree.devtools.b2b.sample.BeanC, com.mindtree.devtools.b2b.sample.BeanA>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BeanAToBeanCMapperReverse(){
		super();
	}

	public BeanAToBeanCMapperReverse( com.mindtree.devtools.b2b.sample.BeanC src, com.mindtree.devtools.b2b.sample.BeanA dest ){
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
		com.mindtree.devtools.b2b.sample.BeanC beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.mindtree.devtools.b2b.sample.BeanA beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "string" -> "date" */
		if(  !Utils.isEmpty( beanA.getString() )  ){
 			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=MM/dd/yyyy" );
			beanB.setDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getString() ) ) );
  		}

 		/* Mapping: "levelTwo.yes" -> "beanABoolean" */
		if(  !Utils.isEmpty( beanA.getLevelTwo() ) && !Utils.isEmpty( beanA.getLevelTwo().getYes() )  ){
 			beanB.setBeanABoolean( beanA.getLevelTwo().isYes() ); 
 		}

 		/* Mapping: "complexObjectList[].date" -> "strArray[]" */
  		int noOfItems = beanA.getComplexObjectList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getStrArray()[ i ] = converter.getTypeOfB().cast( converter.getBFromA( beanA.getComplexObjectList().get( i ).getDate() ) );
 		}

 		/* Mapping: "complexObjectArray[1].string" -> "complexObjectList[0].string" */
		if(  !Utils.isEmpty( beanA.getComplexObjectArray() ) && !Utils.isEmpty( beanA.getComplexObjectArray()[1] ) && !Utils.isEmpty( beanA.getComplexObjectArray()[1].getString() )  ){
 			beanB.getComplexObjectList().get(0).setString( beanA.getComplexObjectArray()[1].getString() ); 
 		}

 		/* Mapping: "complexObjectArray[2].string" -> "complexObjectArray[0].string" */
		if(  !Utils.isEmpty( beanA.getComplexObjectArray() ) && !Utils.isEmpty( beanA.getComplexObjectArray()[2] ) && !Utils.isEmpty( beanA.getComplexObjectArray()[2].getString() )  ){
 			beanB.getComplexObjectArray()[0].setString( beanA.getComplexObjectArray()[2].getString() ); 
 		}

 		/* Mapping: "complexObjectArray[3].string" -> "strLevelTwoMap['KEY'].yesString" */
		if(  !Utils.isEmpty( beanA.getComplexObjectArray() ) && !Utils.isEmpty( beanA.getComplexObjectArray()[3] ) && !Utils.isEmpty( beanA.getComplexObjectArray()[3].getString() )  ){
 			beanB.getStrLevelTwoMap().get( Utils.newInstance( ( (com.mindtree.ruc.cmn.utils.Map) beanB.getStrLevelTwoMap()).getKeyClass(), "KEY" )).setYesString( beanA.getComplexObjectArray()[3].getString() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.mindtree.devtools.b2b.sample.BeanA initializeDeepVO( com.mindtree.devtools.b2b.sample.BeanC beanA, com.mindtree.devtools.b2b.sample.BeanA beanB ){
      		BeanUtils.initializeBeanField( "strArray[]", beanB, beanA.getComplexObjectList().size() );
   		BeanUtils.initializeBeanField( "complexObjectList[0]", beanB );
   		BeanUtils.initializeBeanField( "complexObjectArray[0]", beanB );
   		BeanUtils.initializeBeanField( "strLevelTwoMap['KEY']", beanB );
  		return beanB;
	}
}
