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
 * <li>com.rsaame.pas.dao.model.TTrnSectionDetailsQuo</li>
 * <li>com.rsaame.pas.vo.bus.SectionDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( SectionDetailsQuoToSectionDetailsVOMapper.class )</code>.
 */
public class SectionDetailsQuoToSectionDetailsVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnSectionDetailsQuo, com.rsaame.pas.vo.bus.SectionDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public SectionDetailsQuoToSectionDetailsVOMapper(){
		super();
	}

	public SectionDetailsQuoToSectionDetailsVOMapper( com.rsaame.pas.dao.model.TTrnSectionDetailsQuo src, com.rsaame.pas.vo.bus.SectionDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.SectionDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.SectionDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.SectionDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnSectionDetailsQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.SectionDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.secPolicyId" -> "secPolicyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getSecPolicyId() )  ){
 			beanB.setSecPolicyId( beanA.getId().getSecPolicyId() ); 
 		}

 		/* Mapping: "id.secSecId" -> "secSecId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getSecSecId() )  ){
 			beanB.setSecSecId( beanA.getId().getSecSecId() ); 
 		}

 		/* Mapping: "id.secValidityStartDate" -> "secValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getSecValidityStartDate() )  ){
 			beanB.setSecValidityStartDate( beanA.getId().getSecValidityStartDate() ); 
 		}

 		/* Mapping: "secEndtId" -> "secEndtId" */
		if(  !Utils.isEmpty( beanA.getSecEndtId() )  ){
 			beanB.setSecEndtId( beanA.getSecEndtId() ); 
 		}

 		/* Mapping: "secClCode" -> "secClCode" */
		if(  !Utils.isEmpty( beanA.getSecClCode() )  ){
 			beanB.setSecClCode( beanA.getSecClCode() ); 
 		}

 		/* Mapping: "secPtCode" -> "secPtCode" */
		if(  !Utils.isEmpty( beanA.getSecPtCode() )  ){
 			beanB.setSecPtCode( beanA.getSecPtCode() ); 
 		}

 		/* Mapping: "secCommVal" -> "secCommVal" */
		if(  !Utils.isEmpty( beanA.getSecCommVal() )  ){
 			beanB.setSecCommVal( beanA.getSecCommVal() ); 
 		}

 		/* Mapping: "secValidityExpiryDate" -> "secValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getSecValidityExpiryDate() )  ){
 			beanB.setSecValidityExpiryDate( beanA.getSecValidityExpiryDate() ); 
 		}

 		/* Mapping: "secNonStdText" -> "secNonStdText" */
		if(  !Utils.isEmpty( beanA.getSecNonStdText() )  ){
 			beanB.setSecNonStdText( beanA.getSecNonStdText() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.SectionDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnSectionDetailsQuo beanA, com.rsaame.pas.vo.bus.SectionDetailsVO beanB ){
                   		return beanB;
	}
}
