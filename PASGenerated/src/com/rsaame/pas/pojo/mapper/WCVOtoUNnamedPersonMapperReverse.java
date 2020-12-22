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
 * <li>com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo</li>
 * <li>com.rsaame.pas.vo.bus.WCVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WCVOtoUNnamedPersonMapperReverse.class )</code>.
 */
public class WCVOtoUNnamedPersonMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo, com.rsaame.pas.vo.bus.WCVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public WCVOtoUNnamedPersonMapperReverse(){
		super();
	}

	public WCVOtoUNnamedPersonMapperReverse( com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo src, com.rsaame.pas.vo.bus.WCVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.WCVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.WCVO) Utils.newInstance( "com.rsaame.pas.vo.bus.WCVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.WCVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "wupEmploymentType" -> "empType" */
		if(  !Utils.isEmpty( beanA.getWupEmploymentType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setEmpType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupEmploymentType() ) ) );
  		}

 		/* Mapping: "wupNoOfPerson" -> "noOfEmp" */
		if(  !Utils.isEmpty( beanA.getWupNoOfPerson() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setNoOfEmp( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupNoOfPerson() ) ) );
  		}

 		/* Mapping: "wupEmpLiabLmt" -> "limit" */
		if(  !Utils.isEmpty( beanA.getWupEmpLiabLmt() )  ){
 			beanB.setLimit( beanA.getWupEmpLiabLmt() ); 
 		}

 		/* Mapping: "wupStartDate" -> "validityStartDate" */
		if(  !Utils.isEmpty( beanA.getWupStartDate() )  ){
 			beanB.setValidityStartDate( beanA.getWupStartDate() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.WCVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanA, com.rsaame.pas.vo.bus.WCVO beanB ){
         		return beanB;
	}
}
