/*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.vo.voholder.mapper;
//M1015362
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

import com.rsaame.pas.vo.bus.PersonDetailsVO;
import com.rsaame.pas.vo.bus.PersonalAccidentPersonVO;
import com.rsaame.pas.vo.bus.PersonalAccidentVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.PersonalAccidentVO</li>
 * <li>com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PersonalAccidentVOToTTrnGaccPersonVOHolderWrapper.class )</code>.
 */
public class PersonalAccidentVOToTTrnGaccPersonVOHolderWrapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PersonalAccidentVO, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PersonalAccidentVOToTTrnGaccPersonVOHolderWrapper(){
		super();
	}

	public PersonalAccidentVOToTTrnGaccPersonVOHolderWrapper( com.rsaame.pas.vo.bus.PersonalAccidentVO src, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper" );
		}

		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PersonalAccidentVO beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;


		//--beanA is PersonalAccidentVO
		// beanB is TTrnGaccPersonVOHolderWrapper



		//TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
		List<TableData> tTrnGaccPersonVOHolderList=new ArrayList<TableData>();

		List<PersonalAccidentPersonVO> personalAccidentPersonVO = beanA.getPersonalAccidentPersonVO();

		CommonVO commonVO=beanA.getCommonVO();
		
		for( PersonalAccidentPersonVO personalAccidentPersVO : personalAccidentPersonVO ){

			PersonDetailsVO personDetailsVO=personalAccidentPersVO.getPersonDetailsVO();
			TTrnGaccPersonVOHolder trnGaccPersonVOHolder=new TTrnGaccPersonVOHolder();
			TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
			
			/* Mapping: "gprId" -> "GprId" */
			if(  !Utils.isEmpty( personDetailsVO.getGprId())  ){
				trnGaccPersonVOHolder.setGprId( personDetailsVO.getGprId()); 
			}
			

			/* Mapping: "gprEName" -> "name" */
			if(  !Utils.isEmpty( personDetailsVO.getName() )  ){
				trnGaccPersonVOHolder.setGprEName(personDetailsVO.getName()  ); 
			}


			/* Mapping: "Type" -> "gprRtCode" */
			if(  !Utils.isEmpty( personDetailsVO.getType() )  ){

				com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
				trnGaccPersonVOHolder.setGprRtCode(  converter.getBFromA(personDetailsVO.getType() ) );


			}

			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( personDetailsVO.getCategory() )  ){
				com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );

				trnGaccPersonVOHolder.setGprRcCode( converter.getBFromA(personDetailsVO.getCategory() )); 
			}


			/* Mapping: "AnnualIncome" -> "gprSalary" */
			if(  !Utils.isEmpty( personDetailsVO.getAnnualIncome() )  ){
				trnGaccPersonVOHolder.setGprSalary( personDetailsVO.getAnnualIncome() ); 


			}

			/* Mapping: "SumInsuredVO" -> "gprSumInsured" */
			if(  !Utils.isEmpty( personDetailsVO.getSumInsuredVO() )  ){
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

				trnGaccPersonVOHolder.setGprSumInsured(converter.getAFromB(personDetailsVO.getSumInsuredVO().getSumInsured() )); 

			}

			/* Mapping: "Gender" -> "gprGender" */
			if(  !Utils.isEmpty( personDetailsVO.getGender() )  ){
				trnGaccPersonVOHolder.setGprGender(personDetailsVO.getGender() ); 


			}

			/* Mapping: "dateOfBirth" -> "gprDateOfBirth" */
			if(  !Utils.isEmpty( personDetailsVO.getDateOfBirth() )  ){
				trnGaccPersonVOHolder.setGprDateOfBirth(personDetailsVO.getDateOfBirth() ); 


			}

			/* Mapping: "personID" -> "gprPersonId" */
			if(  !Utils.isEmpty( personDetailsVO.getPersonID() )  ){
				trnGaccPersonVOHolder.setGprPersonId(personDetailsVO.getPersonID() ); 


			}
			//Integer to short
			/* Mapping: "age" -> "gprAge" */
			if(  !Utils.isEmpty( personDetailsVO.getAge() )  ){
				com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );

				trnGaccPersonVOHolder.setGprAge(converter.getBFromA(personDetailsVO.getAge() )); 


			}

			//Integer to short
			/* Mapping: "relationship" -> "gprRelation" */
			if(  !Utils.isEmpty( personDetailsVO.getRelationShip() )  ){
				com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );

				trnGaccPersonVOHolder.setGprRelation(converter.getBFromA(personDetailsVO.getRelationShip() )); 


			}


			/* Mapping: "joiningDate" -> "gprDateOfJoining" */
			if(  !Utils.isEmpty( personDetailsVO.getJoiningDate() )  ){
				trnGaccPersonVOHolder.setGprDateOfJoining(personDetailsVO.getJoiningDate() ); 


			}

			/* Mapping: "endDate" -> "gprEndDate" */
			if(  !Utils.isEmpty( personDetailsVO.getEndDate() )  ){
				trnGaccPersonVOHolder.setGprEndDate(personDetailsVO.getEndDate() ); 


			}

			/* Mapping: "nomineeName" -> "gprNName" */
			/*if(  !Utils.isEmpty( beanA.getNomineeName()) ){
 			beanB.setGpr(beanA.getNomineeName() ); 


 		}*/

			/* Mapping: "validityStartDate" -> "GprValidityStartDate" */
			if(  !Utils.isEmpty( personDetailsVO.getValidityStartDate())  ){
				trnGaccPersonVOHolder.setGprValidityStartDate(personDetailsVO.getValidityStartDate() ); 

				
			}

			//set all the comonvo details of personalAccidentVO into  trnGaccPersonVOHolder

			/* Mapping: "endId" -> "gprEndId" */
			if(  !Utils.isEmpty( commonVO.getEndtId() )  ){
				trnGaccPersonVOHolder.setGprEndtId(commonVO.getEndtId() ); 

			}

			/* Mapping: "endDate" -> "gprEndDate" */

			trnGaccPersonVOHolder.setGprRskCode( Long.valueOf( Utils.getSingleValueAppConfig( "GPR_RSK_CODE_TRAVEL" ) )); 

			//tTrnGaccPersonVOHolder.setGprPolicyId( personalAccidentPersVO.getPolicyId() );

			trnGaccPersonVOHolder.setGprRskCode( Long.valueOf( Utils.getSingleValueAppConfig( "GPR_RSK_CODE_TRAVEL" ) ) );
			trnGaccPersonVOHolder.setGprRcCode( Long.valueOf( Utils.getSingleValueAppConfig( "GPR_RC_CODE_TRAVEL" ) ) );

			
		
			trnGaccPersonVOHolder.setGprValidityExpiryDate( getVED() );

			trnGaccPersonVOHolder.setGprRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "GPR_RI_RSK_CODE_TRAVEL" ) ) );
			trnGaccPersonVOHolder.setGprBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "GPR_BASIC_RSK_CODE_TRAVEL") ) );



			//-- in the list
			gaccTableData.setTableData( trnGaccPersonVOHolder );

			tTrnGaccPersonVOHolderList.add( gaccTableData );



		}

		beanB.setTTrnGaccPersonVOHolderList(tTrnGaccPersonVOHolderList);
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper initializeDeepVO( com.rsaame.pas.vo.bus.PersonalAccidentVO beanA, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper beanB ){
		return beanB;
	}



	public  Date getVED(){
		SimpleDateFormat generalDateFormat = new SimpleDateFormat( "MM-dd-yyyy hh:mm:ss" );
		Date vedDate = null;
		try{
			vedDate = generalDateFormat.parse( "12-31-2049 12:00:00" );
		}
		catch( ParseException e ){
			throw new SystemException( "", null, "Error in parsing VED: Critical error" );
		}
		return vedDate;
	}
}
