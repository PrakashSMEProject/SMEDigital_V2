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
 * <li>com.rsaame.pas.vo.bus.EmpTypeDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EmpTypeDetailsVOToTTrnWctplUPQuoMapperReverse.class )</code>.
 */
public class EmpTypeDetailsVOToTTrnWctplUPQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo, com.rsaame.pas.vo.bus.EmpTypeDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EmpTypeDetailsVOToTTrnWctplUPQuoMapperReverse(){
		super();
	}

	public EmpTypeDetailsVOToTTrnWctplUPQuoMapperReverse( com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo src, com.rsaame.pas.vo.bus.EmpTypeDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.EmpTypeDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.EmpTypeDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.EmpTypeDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
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

 		/* Mapping: "wupStartDate" -> "vsd" */
		if(  !Utils.isEmpty( beanA.getWupStartDate() )  ){
 			beanB.setVsd( beanA.getWupStartDate() ); 
 		}
		
		/* Mapping: "id.wupId" -> "riskId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getWupId() )  ){
 			beanB.setRiskId( beanA.getId().getWupId() ); 
 		}
   	
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.EmpTypeDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanA, com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanB ){
         		return beanB;
	}
}
