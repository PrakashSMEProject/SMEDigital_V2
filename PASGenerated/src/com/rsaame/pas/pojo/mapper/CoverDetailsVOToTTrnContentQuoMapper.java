       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.CoverDetailsVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnContentQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CoverDetailsVOToTTrnContentQuoMapper.class )</code>.
 */
public class CoverDetailsVOToTTrnContentQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.CoverDetailsVO, com.rsaame.pas.dao.model.TTrnContentQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CoverDetailsVOToTTrnContentQuoMapper(){
		super();
	}

	public CoverDetailsVOToTTrnContentQuoMapper( com.rsaame.pas.vo.bus.CoverDetailsVO src, com.rsaame.pas.dao.model.TTrnContentQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnContentQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnContentQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnContentQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.CoverDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnContentQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "coverName" -> "cntDescription" */
		if(  !Utils.isEmpty( beanA.getCoverName() )  ){
 			beanB.setCntDescription( beanA.getCoverName() ); 
 		}

 		/* Mapping: "sumInsured.sumInsured" -> "cntSumInsured" */
		if(  !Utils.isEmpty( beanA.getSumInsured() ) && !Utils.isEmpty( beanA.getSumInsured().getSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCntSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSumInsured().getSumInsured() ) ) );
  		}

 		/* Mapping: "riskCodes.riskCode" -> "cntRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskCode() )  ){
 			beanB.setCntRskCode( beanA.getRiskCodes().getRiskCode() ); 
 		}

 		/* Mapping: "riskCodes.basicRskCode" -> "cntBasicRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getBasicRskCode() )  ){
 			beanB.setCntBasicRskCode( beanA.getRiskCodes().getBasicRskCode() ); 
 		}

 		/* Mapping: "riskCodes.riskType" -> "cntCategory" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskType() )  ){
 			beanB.setCntCategory( beanA.getRiskCodes().getRiskType() ); 
 		}

 		/* Mapping: "riskCodes.riskCat" -> "cntRiskSubDtl" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskCat() )  ){
 			beanB.setCntRiskSubDtl( beanA.getRiskCodes().getRiskCat() ); 
 		}
		/* Mapping: "sumInsured.sumInsured" -> "cntMplFire" */
		if(  !Utils.isEmpty( beanA.getSumInsured() ) && !Utils.isEmpty( beanA.getSumInsured().getSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setCntMplFire( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSumInsured().getSumInsured() ) ) );
 		}
		/* Mapping: "riskCodes.basicRskId" -> "cntBasicRiskId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getBasicRskId() )  ){
			beanB.setCntBasicRiskId( beanA.getRiskCodes().getBasicRskId().longValue() ) ;
 		}
		
		/* Mapping: "riskCodes.rskId" -> "id.CntContentId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRskId() )  ){
			beanB.getId().setCntContentId( beanA.getRiskCodes().getRskId().longValue() );
 		}
		
		/* Mapping: "riskCodes.riskType" -> "cntRiskDtl" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskType() )  ){
			beanB.setCntRiskDtl( beanA.getRiskCodes().getRiskType() .longValue() );
 		}
		
		/* Mapping: "riskCodes.riskCat" -> "cntRiskSubDtl" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskCat() )  ){
			beanB.setCntRiskSubDtl( beanA.getRiskCodes().getRiskCat() );
 		}

		/* Mapping: "vsd" -> "id.cntValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getVsd() ) ){
			beanB.getId().setCntValidityStartDate( beanA.getVsd() );
 		}
		
		/* Mapping : "cntRiRskCode"*/
		if( !Utils.isEmpty( beanA.getRiRskCode() ) ){
			beanB.setCntRiRskCode( beanA.getRiRskCode() );
		}
		
		return dest;
	}	  

	private Integer getRiRskCode( Double sumInsured ){
		Integer cntRiRskCode;
		cntRiRskCode = sumInsured > Double.valueOf( Utils.getSingleValueAppConfig( "HOME_SI_LIMIT" ) ) ?
				 Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_GREATER" ) ):
			 Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_LESSER" ) );
				 return cntRiRskCode;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnContentQuo initializeDeepVO( com.rsaame.pas.vo.bus.CoverDetailsVO beanA, com.rsaame.pas.dao.model.TTrnContentQuo beanB ){
		BeanUtils.initializeBeanField( "id", beanB );     		
		return beanB;
	}
}
