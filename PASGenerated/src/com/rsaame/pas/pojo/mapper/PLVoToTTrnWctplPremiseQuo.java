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
 * <li>com.rsaame.pas.vo.bus.PublicLiabilityVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PLVoToTTrnWctplPremiseQuo.class )</code>.
 */
public class PLVoToTTrnWctplPremiseQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PublicLiabilityVO, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PLVoToTTrnWctplPremiseQuo(){
		super();
	}

	public PLVoToTTrnWctplPremiseQuo( com.rsaame.pas.vo.bus.PublicLiabilityVO src, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnWctplPremiseQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnWctplPremiseQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnWctplPremiseQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PublicLiabilityVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "sumInsuredDets.sumInsured" -> "wbdSumInsured" */
		if(  !Utils.isEmpty( beanA.getSumInsuredDets() ) && !Utils.isEmpty( beanA.getSumInsuredDets().getSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setWbdSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSumInsuredDets().getSumInsured() ) ) );
  		}

 		/* Mapping: "indemnityAmtLimit" -> "wbdIndemnityLimitAmt" */
		if(  !Utils.isEmpty( beanA.getIndemnityAmtLimit() )  ){
 			beanB.setWbdIndemnityLimitAmt( beanA.getIndemnityAmtLimit() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplPremiseQuo initializeDeepVO( com.rsaame.pas.vo.bus.PublicLiabilityVO beanA, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB ){
     		return beanB;
	}
}
