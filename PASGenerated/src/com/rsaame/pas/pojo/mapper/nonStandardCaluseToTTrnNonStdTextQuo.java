/*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.NonStandardClause;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.NonStandardClause</li>
 * <li>com.rsaame.pas.dao.model.TTrnNonStdTextQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( nonStandardCaluseToTTrnNonStdTextQuo.class )</code>.
 */
public class nonStandardCaluseToTTrnNonStdTextQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.NonStandardClause, com.rsaame.pas.dao.model.TTrnNonStdTextQuo>{
	public nonStandardCaluseToTTrnNonStdTextQuo(){
		super();
	}

	public nonStandardCaluseToTTrnNonStdTextQuo( com.rsaame.pas.vo.bus.NonStandardClause src, com.rsaame.pas.dao.model.TTrnNonStdTextQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnNonStdTextQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnNonStdTextQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnNonStdTextQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.NonStandardClause beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnNonStdTextQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* Mapping: "description" -> "nstEText" */
		beanB.setNstEText( getDescription(beanA)); 
 		
		if(  !Utils.isEmpty( beanA.getClauseType() )  ){
 			beanB.getId().setNstTypeCode( getClauseType(beanA.getClauseType()) ); 
 		}
		
		/*Mapping: "policyId" ->  "id.policyId" */
		if( beanA.getPolicyId() != 0){
			beanB.getId().setPolicyId( beanA.getPolicyId() );
		}
		
		if( !Utils.isEmpty( beanA.getVsd())){
			beanB.setNstValidityStartDate(beanA.getVsd());
		}
   
		return dest;
	}	 

	private String getDescription(NonStandardClause nonClause) {
		
		if( Utils.isEmpty(nonClause.getDescription()) ){
			String ONE_SPACE_STRING = " ";
			nonClause.setDescription(ONE_SPACE_STRING );
		}
		
		return nonClause.getDescription();
	}

	private int getClauseType(String clauseType) {
		int typeCode = 0;
		
		if(clauseType.equalsIgnoreCase( "C" ) )
		{
			typeCode =1;
		}
		else if( clauseType.equalsIgnoreCase( "W" ) ){
			typeCode = 2;
		}
		else if( clauseType.equalsIgnoreCase( "E" ) ){
			typeCode = 3;
		}
		return typeCode;
	}


	/**
	 * This method initializes all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnNonStdTextQuo initializeDeepVO( com.rsaame.pas.vo.bus.NonStandardClause beanA, com.rsaame.pas.dao.model.TTrnNonStdTextQuo beanB ){
   		
		BeanUtils.initializeBeanField( "id", beanB );
		
		return beanB;
	}
}
