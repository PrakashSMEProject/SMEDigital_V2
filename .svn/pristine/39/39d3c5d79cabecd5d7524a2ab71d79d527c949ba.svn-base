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
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.mindtree.devtools.b2b.sample.FormVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToFormVOMapper.class )</code>.
 */
public class RequestToFormVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.mindtree.devtools.b2b.sample.FormVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToFormVOMapper(){
		super();
	}

	public RequestToFormVOMapper( javax.servlet.http.HttpServletRequest src, com.mindtree.devtools.b2b.sample.FormVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.mindtree.devtools.b2b.sample.FormVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.mindtree.devtools.b2b.sample.FormVO) Utils.newInstance( "com.mindtree.devtools.b2b.sample.FormVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.mindtree.devtools.b2b.sample.FormVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "date" -> "effDate" */
		if( !Utils.isEmpty( src.getParameter( "date" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=MM/dd/yyyy" );
			beanB.setEffDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "date" ) ) ) );
  		}

 		/* Mapping: "yesso" -> "levelTwo.yes" */
		if( !Utils.isEmpty( src.getParameter( "yesso" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.getLevelTwo().setYes( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "yesso" ) ) ) );
  		}

 		/* Mapping: "uwq" -> "uwq[]" */
  		int noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "uwq" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			Utils.setValueIntoList( beanB.getUwq(), i,   beanA.getParameter( "uwq[" + i + "]" ) );
		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.mindtree.devtools.b2b.sample.FormVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.mindtree.devtools.b2b.sample.FormVO beanB ){
    		BeanUtils.initializeBeanField( "levelTwo", beanB );
   		BeanUtils.initializeBeanField( "uwq[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "uwq[]" ).size() );
  		return beanB;
	}
}
