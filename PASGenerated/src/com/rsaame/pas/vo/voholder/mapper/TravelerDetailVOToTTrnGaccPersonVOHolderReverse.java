       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.vo.voholder.mapper;


import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder</li>
 * <li>com.rsaame.pas.vo.bus.TravelerDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TravelerDetailVOToTTrnGaccPersonVOHolderReverse.class )</code>.
 */
public class TravelerDetailVOToTTrnGaccPersonVOHolderReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder, com.rsaame.pas.vo.bus.TravelerDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TravelerDetailVOToTTrnGaccPersonVOHolderReverse(){
		super();
	}

	public TravelerDetailVOToTTrnGaccPersonVOHolderReverse( com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder src, com.rsaame.pas.vo.bus.TravelerDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TravelerDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TravelerDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TravelerDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TravelerDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "gprEName" -> "name" */
		if(  !Utils.isEmpty( beanA.getGprEName() )  ){
 			beanB.setName( beanA.getGprEName() ); 
 		}

 		/* Mapping: "gprDateOfBirth" -> "dateOfBirth" */
		if(  !Utils.isEmpty( beanA.getGprDateOfBirth() )  ){
 			beanB.setDateOfBirth( beanA.getGprDateOfBirth() ); 
 		}

 		/* Mapping: "gprRelation" -> "relation" */
		if(  !Utils.isEmpty( beanA.getGprRelation() )  ){
 			beanB.setRelation( beanA.getGprRelation() ); 
 		}
		
		/* Mapping: "gprGender" -> "gender" */
		if(  !Utils.isEmpty( beanA.getGprGender() )  ){
 			beanB.setGender( beanA.getGprGender() ); 
 		}

 		/* Mapping: "gprNtyEDesc" -> "nationality" */
		if(  !Utils.isEmpty( beanA.getGprNtyEDesc() )  ){
 			beanB.setNationality( beanA.getGprNtyEDesc() ); 
 		}
		if(  !Utils.isEmpty( beanA.getGprId())  ){
			com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
 			beanB.setGprId(converter.getTypeOfA().cast( converter.getAFromB( beanA.getGprId() ) )  ); 
 		}
		if(  !Utils.isEmpty( beanA.getGprValidityStartDate())  ){
 			beanB.setVsd(beanA.getGprValidityStartDate()); 
 		}
		/* Mapping: "getGprSumInsured" -> "sumInsured" */
		if(  !Utils.isEmpty( beanA.getGprSumInsured() )  ){
 			beanB.setSumInsured( beanA.getGprSumInsured() ); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TravelerDetailsVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanA, com.rsaame.pas.vo.bus.TravelerDetailsVO beanB ){
         		return beanB;
	}
}
