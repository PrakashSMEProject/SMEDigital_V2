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
 * <li>com.rsaame.pas.vo.svc.TTrnPremiumVOHolder</li>
 * <li>com.rsaame.pas.dao.model.TTrnPremiumQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnPremiumVOHolderToTTrnPremiumQuo.class )</code>.
 */
public class TTrnPremiumVOHolderToTTrnPremiumQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnPremiumVOHolder, com.rsaame.pas.dao.model.TTrnPremiumQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnPremiumVOHolderToTTrnPremiumQuo(){
		super();
	}

	public TTrnPremiumVOHolderToTTrnPremiumQuo( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder src, com.rsaame.pas.dao.model.TTrnPremiumQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPremiumQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPremiumQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPremiumQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPremiumQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "prmPolicyId" -> "id.prmPolicyId" */
		if(  !Utils.isEmpty( beanA.getPrmPolicyId() )  ){
 			beanB.getId().setPrmPolicyId( beanA.getPrmPolicyId() ); 
 		}

 		/* Mapping: "prmBasicRskCode" -> "id.prmBasicRskCode" */
		if(  !Utils.isEmpty( beanA.getPrmBasicRskCode() )  ){
 			beanB.getId().setPrmBasicRskCode( beanA.getPrmBasicRskCode() ); 
 		}

 		/* Mapping: "prmBasicRskId" -> "id.prmBasicRskId" */
		if(  !Utils.isEmpty( beanA.getPrmBasicRskId() )  ){
 			beanB.getId().setPrmBasicRskId( beanA.getPrmBasicRskId() ); 
 		}

 		/* Mapping: "prmRskCode" -> "id.prmRskCode" */
		if(  !Utils.isEmpty( beanA.getPrmRskCode() )  ){
 			beanB.getId().setPrmRskCode( beanA.getPrmRskCode() ); 
 		}

 		/* Mapping: "prmRskId" -> "id.prmRskId" */
		if(  !Utils.isEmpty( beanA.getPrmRskId() )  ){
 			beanB.getId().setPrmRskId( beanA.getPrmRskId() ); 
 		}

 		/* Mapping: "prmCovCode" -> "id.prmCovCode" */
		if(  !Utils.isEmpty( beanA.getPrmCovCode() )  ){
 			beanB.getId().setPrmCovCode( beanA.getPrmCovCode() ); 
 		}

 		/* Mapping: "prmCtCode" -> "id.prmCtCode" */
		if(  !Utils.isEmpty( beanA.getPrmCtCode() )  ){
 			beanB.getId().setPrmCtCode( beanA.getPrmCtCode() ); 
 		}

 		/* Mapping: "prmCstCode" -> "id.prmCstCode" */
		if(  !Utils.isEmpty( beanA.getPrmCstCode() )  ){
 			beanB.getId().setPrmCstCode( beanA.getPrmCstCode() ); 
 		}

 		/* Mapping: "prmValidityStartDate" -> "id.prmValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getPrmValidityStartDate() )  ){
 			beanB.getId().setPrmValidityStartDate( beanA.getPrmValidityStartDate() ); 
 		}

 		/* Mapping: "prmClCode" -> "prmClCode" */
		if(  !Utils.isEmpty( beanA.getPrmClCode() )  ){
 			beanB.setPrmClCode( beanA.getPrmClCode() ); 
 		}

 		/* Mapping: "prmPtCode" -> "prmPtCode" */
		if(  !Utils.isEmpty( beanA.getPrmPtCode() )  ){
 			beanB.setPrmPtCode( beanA.getPrmPtCode() ); 
 		}

 		/* Mapping: "prmRtCode" -> "prmRtCode" */
		if(  !Utils.isEmpty( beanA.getPrmRtCode() )  ){
 			beanB.setPrmRtCode( beanA.getPrmRtCode() ); 
 		}

 		/* Mapping: "prmRcCode" -> "prmRcCode" */
		if(  !Utils.isEmpty( beanA.getPrmRcCode() )  ){
 			beanB.setPrmRcCode( beanA.getPrmRcCode() ); 
 		}

 		/* Mapping: "prmRscCode" -> "prmRscCode" */
		if(  !Utils.isEmpty( beanA.getPrmRscCode() )  ){
 			beanB.setPrmRscCode( beanA.getPrmRscCode() ); 
 		}

 		/* Mapping: "prmSumInsured" -> "prmSumInsured" */
		if(  !Utils.isEmpty( beanA.getPrmSumInsured() )  ){
 			beanB.setPrmSumInsured( beanA.getPrmSumInsured() ); 
 		}

 		/* Mapping: "prmRate" -> "prmRate" */
		if(  !Utils.isEmpty( beanA.getPrmRate() )  ){
 			beanB.setPrmRate( beanA.getPrmRate() ); 
 		}

 		/* Mapping: "prmPremium" -> "prmPremium" */
		if(  !Utils.isEmpty( beanA.getPrmPremium() )  ){
 			beanB.setPrmPremium( beanA.getPrmPremium() ); 
 		}

 		/* Mapping: "prmCompulsoryExcess" -> "prmCompulsoryExcess" */
		if(  !Utils.isEmpty( beanA.getPrmCompulsoryExcess() )  ){
 			beanB.setPrmCompulsoryExcess( beanA.getPrmCompulsoryExcess() ); 
 		}

 		/* Mapping: "prmVoluntaryExcess" -> "prmVoluntaryExcess" */
		if(  !Utils.isEmpty( beanA.getPrmVoluntaryExcess() )  ){
 			beanB.setPrmVoluntaryExcess( beanA.getPrmVoluntaryExcess() ); 
 		}

 		/* Mapping: "prmValidityExpiryDate" -> "prmValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPrmValidityExpiryDate() )  ){
 			beanB.setPrmValidityExpiryDate( beanA.getPrmValidityExpiryDate() ); 
 		}

 		/* Mapping: "prmEndtId" -> "prmEndtId" */
		if(  !Utils.isEmpty( beanA.getPrmEndtId() )  ){
 			beanB.setPrmEndtId( beanA.getPrmEndtId() ); 
 		}

 		/* Mapping: "prmExcessRate" -> "prmExcessRate" */
		if(  !Utils.isEmpty( beanA.getPrmExcessRate() )  ){
 			beanB.setPrmExcessRate( beanA.getPrmExcessRate() ); 
 		}

 		/* Mapping: "prmRiRskCode" -> "prmRiRskCode" */
		if(  !Utils.isEmpty( beanA.getPrmRiRskCode() )  ){
 			beanB.setPrmRiRskCode( beanA.getPrmRiRskCode() ); 
 		}

 		/* Mapping: "prmSiInd" -> "prmSiInd" */
		if(  !Utils.isEmpty( beanA.getPrmSiInd() )  ){
 			beanB.setPrmSiInd( beanA.getPrmSiInd() ); 
 		}

 		/* Mapping: "prmStatus" -> "prmStatus" */
		if(  !Utils.isEmpty( beanA.getPrmStatus() )  ){
 			beanB.setPrmStatus( beanA.getPrmStatus() ); 
 		}

 		/* Mapping: "prmEffectiveDate" -> "prmEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getPrmEffectiveDate() )  ){
 			beanB.setPrmEffectiveDate( beanA.getPrmEffectiveDate() ); 
 		}

 		/* Mapping: "prmExpiryDate" -> "prmExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPrmExpiryDate() )  ){
 			beanB.setPrmExpiryDate( beanA.getPrmExpiryDate() ); 
 		}

 		/* Mapping: "prmPolSumInsured" -> "prmPolSumInsured" */
		if(  !Utils.isEmpty( beanA.getPrmPolSumInsured() )  ){
 			beanB.setPrmPolSumInsured( beanA.getPrmPolSumInsured() ); 
 		}

 		/* Mapping: "prmSitypeCode" -> "prmSitypeCode" */
		if(  !Utils.isEmpty( beanA.getPrmSitypeCode() )  ){
 			beanB.setPrmSitypeCode( beanA.getPrmSitypeCode() ); 
 		}

 		/* Mapping: "prmFnCode" -> "prmFnCode" */
		if(  !Utils.isEmpty( beanA.getPrmFnCode() )  ){
 			beanB.setPrmFnCode( beanA.getPrmFnCode() ); 
 		}

 		/* Mapping: "prmSumInsuredCurr" -> "prmSumInsuredCurr" */
		if(  !Utils.isEmpty( beanA.getPrmSumInsuredCurr() )  ){
 			beanB.setPrmSumInsuredCurr( beanA.getPrmSumInsuredCurr() ); 
 		}

 		/* Mapping: "prmPremiumCurr" -> "prmPremiumCurr" */
		if(  !Utils.isEmpty( beanA.getPrmPremiumCurr() )  ){
 			beanB.setPrmPremiumCurr( beanA.getPrmPremiumCurr() ); 
 		}

 		/* Mapping: "prmPreparedBy" -> "prmPreparedBy" */
		if(  !Utils.isEmpty( beanA.getPrmPreparedBy() )  ){
 			beanB.setPrmPreparedBy( beanA.getPrmPreparedBy() ); 
 		}

 		/* Mapping: "prmPreparedDt" -> "prmPreparedDt" */
		if(  !Utils.isEmpty( beanA.getPrmPreparedDt() )  ){
 			beanB.setPrmPreparedDt( beanA.getPrmPreparedDt() ); 
 		}

 		/* Mapping: "prmModifiedBy" -> "prmModifiedBy" */
		if(  !Utils.isEmpty( beanA.getPrmModifiedBy() )  ){
 			beanB.setPrmModifiedBy( beanA.getPrmModifiedBy() ); 
 		}

 		/* Mapping: "prmModifiedDt" -> "prmModifiedDt" */
		if(  !Utils.isEmpty( beanA.getPrmModifiedDt() )  ){
 			beanB.setPrmModifiedDt( beanA.getPrmModifiedDt() ); 
 		}

 		/* Mapping: "prmRiLocCode" -> "prmRiLocCode" */
		if(  !Utils.isEmpty( beanA.getPrmRiLocCode() )  ){
 			beanB.setPrmRiLocCode( beanA.getPrmRiLocCode() ); 
 		}

 		/* Mapping: "prmRateType" -> "prmRateType" */
		if(  !Utils.isEmpty( beanA.getPrmRateType() )  ){
 			beanB.setPrmRateType( beanA.getPrmRateType() ); 
 		}

 		/* Mapping: "prmOldPremium" -> "prmOldPremium" */
		if(  !Utils.isEmpty( beanA.getPrmOldPremium() )  ){
 			beanB.setPrmOldPremium( beanA.getPrmOldPremium() ); 
 		}

 		/* Mapping: "prmOldSumInsured" -> "prmOldSumInsured" */
		if(  !Utils.isEmpty( beanA.getPrmOldSumInsured() )  ){
 			beanB.setPrmOldSumInsured( beanA.getPrmOldSumInsured() ); 
 		}

 		/* Mapping: "prmPremiumActual" -> "prmPremiumActual" */
		if(  !Utils.isEmpty( beanA.getPrmPremiumActual() )  ){
 			beanB.setPrmPremiumActual( beanA.getPrmPremiumActual() ); 
 		}

 		/* Mapping: "prmExpPeriodPrm" -> "prmExpPeriodPrm" */
		if(  !Utils.isEmpty( beanA.getPrmExpPeriodPrm() )  ){
 			beanB.setPrmExpPeriodPrm( beanA.getPrmExpPeriodPrm() ); 
 		}

 		/* Mapping: "prmRenewalLoading" -> "prmRenewalLoading" */
		if(  !Utils.isEmpty( beanA.getPrmRenewalLoading() )  ){
 			beanB.setPrmRenewalLoading( beanA.getPrmRenewalLoading() ); 
 		}
		
 		/* Mapping: "prmValue1" -> "prmValue1" */
		if(  !Utils.isEmpty( beanA.getPrmValue1() )  ){
 			beanB.setPrmValue1( beanA.getPrmValue1() ); 
 		}	
		/* Mapping: "PrmLoadDisc" -> "PrmLoadDisc" */
		if(  !Utils.isEmpty( beanA.getPrmLoadDisc() )  ){
 			beanB.setPrmLoadDisc( beanA.getPrmLoadDisc() ); 
 		}	
		/* Mapping: "PrmValidityStartDate" -> "PrmValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getPrmValidityStartDate() )  ){
 			beanB.getId().setPrmValidityStartDate( beanA.getPrmValidityStartDate() ); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPremiumQuo initializeDeepVO( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA, com.rsaame.pas.dao.model.TTrnPremiumQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
        return beanB;
	}
}
