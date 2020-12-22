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
 * <li>com.rsaame.pas.dao.model.VMasPasFetchBasicDtls</li>
 * <li>com.rsaame.pas.vo.bus.EmpTypeDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( ContentsToVMasPasFetchBasicDtlsReverse.class )</code>.
 */
public class EmpTypeDetailsVOToVMasPasFetchBasicDtlsReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VMasPasFetchBasicDtls, com.rsaame.pas.vo.bus.EmpTypeDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EmpTypeDetailsVOToVMasPasFetchBasicDtlsReverse(){
		super();
	}

	public EmpTypeDetailsVOToVMasPasFetchBasicDtlsReverse( com.rsaame.pas.dao.model.VMasPasFetchBasicDtls src, com.rsaame.pas.vo.bus.EmpTypeDetailsVO dest ){
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
		com.rsaame.pas.dao.model.VMasPasFetchBasicDtls beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.secId" -> "secId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getSecId() )  ){
 			beanB.setSecId( beanA.getId().getSecId() ); 
 		}

 		/* Mapping: "id.prClCode" -> "classCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrClCode() )  ){
 			beanB.setClassCode( beanA.getId().getPrClCode() ); 
 		}

 		/* Mapping: "id.prSumInsured" -> "cover" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrSumInsured() )  ){
 			beanB.setCover( beanA.getId().getPrSumInsured() ); 
 		}

 		/* Mapping: "id.prCompulsoryExcess" -> "deductibles" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCompulsoryExcess() )  ){
 			beanB.setDeductibles( beanA.getId().getPrCompulsoryExcess() ); 
 		}

 		/* Mapping: "id.pcrEDesc" -> "contentDesc" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPcrEDesc() )  ){
 			beanB.setContentDesc( beanA.getId().getPcrEDesc() ); 
 		}

 		/* Mapping: "id.prCovCode" -> "coverCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCovCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCoverCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getId().getPrCovCode()) ) );
  		}

 		/* Mapping: "id.prCtCode" -> "coverType" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCtCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCoverType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getId().getPrCtCode() ) ) );
  		}

 		/* Mapping: "id.prCstCode" -> "coverSubType" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrCstCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setCoverSubType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getId().getPrCstCode() ) ) );
  		}

 		/* Mapping: "id.prRskCode" -> "riskCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRskCode() )  ){
			beanB.setRiskCode(  beanA.getId().getPrRskCode() );
  		}

 		/* Mapping: "id.prRtCode" -> "riskType" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRtCode() )  ){
			beanB.setRiskType( beanA.getId().getPrRtCode() );
  		}

 		/* Mapping: "id.prRcCode" -> "riskCat" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRcCode() )  ){
			beanB.setRiskCat( beanA.getId().getPrRcCode() );
  		}

 		/* Mapping: "id.prRscCode" -> "riskSubCat" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRscCode() )  ){
 			beanB.setRiskSubCat( beanA.getId().getPrRscCode() );
  		}
		
 		/* Mapping: "id.prRscCode" -> "riskSubCat" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrRscCode() )  ){
 			beanB.setRiskSubCat( beanA.getId().getPrRscCode() );
  		}
		
		/* Mapping: "id.covDesc" -> "covDesc" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getCovDesc() )  ){
 			beanB.setCovDesc ( beanA.getId().getCovDesc() );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.EmpTypeDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.VMasPasFetchBasicDtls beanA, com.rsaame.pas.vo.bus.EmpTypeDetailsVO beanB ){
                         		return beanB;
	}
}
