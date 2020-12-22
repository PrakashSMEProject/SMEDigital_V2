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
 * <li>com.rsaame.pas.vo.bus.EmpTypeDetailsVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EmpTypeDetailsVOToTTrnWctplUPQuoMapper.class )</code>.
 */
public class EmpTypeDetailsVOToTTrnWctplUPQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.EmpTypeDetailsVO, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EmpTypeDetailsVOToTTrnWctplUPQuoMapper(){
		super();
	}

	public EmpTypeDetailsVOToTTrnWctplUPQuoMapper( com.rsaame.pas.vo.bus.EmpTypeDetailsVO src, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "empType" -> "wupEmploymentType" */
		if(  !Utils.isEmpty( beanA.getEmpType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setWupEmploymentType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getEmpType() ) ) );
  		}

 		/* Mapping: "noOfEmp" -> "wupNoOfPerson" */
		if(  !Utils.isEmpty( beanA.getNoOfEmp() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWupNoOfPerson( converter.getTypeOfB().cast( converter.getBFromA( beanA.getNoOfEmp() ) ) );
  		}

 		/* Mapping: "limit" -> "wupEmpLiabLmt" */
		if(  !Utils.isEmpty( beanA.getLimit() )  ){
 			beanB.setWupEmpLiabLmt( beanA.getLimit() ); 
 		}
		
		/* Mapping: "riskId" -> "id.wupId" */
		if(  !Utils.isEmpty( beanA.getRiskId() )  ){
 			beanB.getId().setWupId( beanA.getRiskId() ); 
 		}
		
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo initializeDeepVO( com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanA, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanB ){
         		return beanB;
	}
}
