/*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.vo.voholder.mapper;

//--M1015362
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.PersonDetailsVO;
import com.rsaame.pas.vo.bus.PersonalAccidentPersonVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper</li>
 * <li>com.rsaame.pas.vo.bus.PersonalAccidentVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TravelerDetailVOToTTrnGaccPersonVOHolderWrapperReverse.class )</code>.
 */
public class PersonalAccidentVOToTTrnGaccPersonVOHolderWrapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper, com.rsaame.pas.vo.bus.PersonalAccidentVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PersonalAccidentVOToTTrnGaccPersonVOHolderWrapperReverse(){
		super();
	}

	public PersonalAccidentVOToTTrnGaccPersonVOHolderWrapperReverse( com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper src, com.rsaame.pas.vo.bus.PersonalAccidentVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PersonalAccidentVO mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PersonalAccidentVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PersonalAccidentVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper beanA = src;

		//Commented to avoid Dead store to local variable , sonar violation on 20-9-2017
		/* Cast the destination bean to a bean of type of BeanB */
		//com.rsaame.pas.vo.bus.PersonalAccidentVO beanB = dest;

		//sonar fix
		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		//beanB = initializeDeepVO(beanA, beanB);

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;


		List<PersonalAccidentPersonVO> personalAccidentPersonVOList =new ArrayList<PersonalAccidentPersonVO>();


		List<TableData>  tTrnGaccPersonVOHolderList= beanA.getTTrnGaccPersonVOHolderList();


		for( TableData TTrnGaccPersonVOHolderWrapperData :tTrnGaccPersonVOHolderList)
		{
			PersonalAccidentPersonVO personalAccidentPersonVO=new PersonalAccidentPersonVO();
			PersonDetailsVO personDetailsVO =new PersonDetailsVO();

			TableData<TTrnGaccPersonVOHolder> gaccTableData=(TableData<TTrnGaccPersonVOHolder>) TTrnGaccPersonVOHolderWrapperData.getTableData(); 

			TTrnGaccPersonVOHolder trnGaccPersonVOHolder=gaccTableData.getTableData();




			/* Mapping:  "name" -> "gprEName"  */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprEName() )  ){
				personDetailsVO.setName( trnGaccPersonVOHolder.getGprEName() ); 
			}




			/* Mapping: "dateOfBirth" -> "gprDateOfBirth"   */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprRtCode() )  ){

				com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );

				personDetailsVO.setType( converter.getAFromB(trnGaccPersonVOHolder.getGprRtCode() )); 
			}

			/* Mapping:  "relation" - >"gprRelation"  */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprRcCode() )  ){
				com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );

				personDetailsVO.setCategory(converter.getAFromB( trnGaccPersonVOHolder.getGprRcCode() )); 
			}

			/* Mapping:  "nationality"  - > "gprNtyEDesc" */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprSalary() )  ){
				personDetailsVO.setAnnualIncome( trnGaccPersonVOHolder.getGprSalary() ); 
			}



			/* Mapping: "gprSumInsured" - >"SumInsuredVO"   */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprSumInsured() )  ){
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

				personDetailsVO.getSumInsuredVO().setSumInsured(converter.getBFromA(trnGaccPersonVOHolder.getGprSumInsured() )); 


			}



			/* Mapping: "gprGender" -> "Gender"  */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprGender() )  ){
				personDetailsVO.setGender(trnGaccPersonVOHolder.getGprGender() ); 


			}

			/* Mapping: "gprDateOfBirth" - >"dateOfBirth"   */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprDateOfBirth() )  ){
				personDetailsVO.setDateOfBirth(trnGaccPersonVOHolder.getGprDateOfBirth() ); 


			}

			/* Mapping:"gprPersonId" ->"personID"  */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprPersonId() )  ){
				personDetailsVO.setPersonID(trnGaccPersonVOHolder.getGprPersonId() ); 


			}
			//Integer to short
			/* Mapping:  "gprAge"  - > "age" */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprAge() )  ){
				com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );

				personDetailsVO.setAge(converter.getAFromB(trnGaccPersonVOHolder.getGprAge() )); 


			}

			//Integer to short
			/* Mapping:"gprRelation" -> "relationship"   */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprRelation() )  ){
				com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );

				personDetailsVO.setRelationShip(converter.getAFromB(trnGaccPersonVOHolder.getGprRelation() )); 


			}


			/* Mapping: "gprDateOfJoining" ->"joiningDate"   */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprDateOfJoining() )  ){
				personDetailsVO.setJoiningDate(trnGaccPersonVOHolder.getGprDateOfJoining() ); 


			}

			/* Mapping: "gprEndDate" -> "endDate">  */
			if(  !Utils.isEmpty( trnGaccPersonVOHolder.getGprEndDate() )  ){
				personDetailsVO.setEndDate(trnGaccPersonVOHolder.getGprEndDate() ); 


			}
			//	
			//		/* Mapping:  "gprNName"  -> "nomineeName" */
			//		/*if(  !Utils.isEmpty( beanA.getGprNName()) ){
			// 			beanB.setNomineeName(beanA.getGprNName() ); 
			// 			
			// 			
			// 		}*/
			//		
			//		

			personalAccidentPersonVO.setPersonDetailsVO(personDetailsVO);
			personalAccidentPersonVOList.add(personalAccidentPersonVO);

		}

		dest.setPersonalAccidentPersonVO(personalAccidentPersonVOList);
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PersonalAccidentVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolderWrapper beanA, com.rsaame.pas.vo.bus.PersonalAccidentVO beanB ){
		return beanB;
	}
}
