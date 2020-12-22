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
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * <li>com.rsaame.pas.vo.bus.PublicLiabilityVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PLVoToTTrnWctplPremiseQuoReverse.class )</code>.
 */
public class PLVoToTTrnWctplPremiseQuoReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnWctplPremiseQuo, com.rsaame.pas.vo.bus.PublicLiabilityVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PLVoToTTrnWctplPremiseQuoReverse(){
		super();
	}

	public PLVoToTTrnWctplPremiseQuoReverse( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo src, com.rsaame.pas.vo.bus.PublicLiabilityVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PublicLiabilityVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PublicLiabilityVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PublicLiabilityVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PublicLiabilityVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "wbdSumInsured" -> "sumInsuredDets.sumInsured" */
		if(  !Utils.isEmpty( beanA.getWbdSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getSumInsuredDets().setSumInsured( converter.getTypeOfB().cast( converter.getBFromA( beanA.getWbdSumInsured() ) ) );
  		}

 		/* Mapping: "wbdIndemnityLimitAmt" -> "indemnityAmtLimit" */
		if(  !Utils.isEmpty( beanA.getWbdIndemnityLimitAmt() )  ){
 			beanB.setIndemnityAmtLimit( beanA.getWbdIndemnityLimitAmt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PublicLiabilityVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA, com.rsaame.pas.vo.bus.PublicLiabilityVO beanB ){
  		BeanUtils.initializeBeanField( "sumInsuredDets", beanB );
    		return beanB;
	}
}
