/*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.beanmap.DateStringConverter;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PersonalAccidentVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPersonalAccidentVOMapper.class )</code>.
 */
public class RequestToPersonalAccidentVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PersonalAccidentVO>{

	public RequestToPersonalAccidentVOMapper(){
		super();
	}

	public RequestToPersonalAccidentVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PersonalAccidentVO dest ){
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
		javax.servlet.http.HttpServletRequest beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PersonalAccidentVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		// int noOfItems = 0;


		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> noOfItems = null;
		int index = 0;
		/* Mapping: "item_name" -> "PersonDetailsVO.Name" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paNameOfPerson" );
		//	index = 0;
		for(String i : noOfItems ){
			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setName(  beanA.getParameter( i ) );
			index++;
		}


		index = 0;
		/* Mapping: "paTypeOfPerson" -> "PersonDetailsVO.Type" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paTypeOfPerson" );
		for(String i : noOfItems ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setType( converter.getAFromB(beanA.getParameter( i ) ));
			index++;
		}

		index = 0;
		/* Mapping: "paCategoryOfPerson" -> "PersonDetailsVO.Category" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paCategoryOfPerson" );
		for(String i : noOfItems ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setCategory(  converter.getAFromB(beanA.getParameter( i )) );
			index++;
		}


		index = 0;
		/* Mapping: "paSI" -> "PersonDetailsVO.SumInsured" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paSI" );
		for(String i : noOfItems ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().getSumInsuredVO().setSumInsured(converter.getAFromB(beanA.getParameter( i ) ));
			index++;
		}



		index = 0;
		/* Mapping: "paAnnualIncome" -> "PersonDetailsVO.AnnualIncome " */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paAnnualIncome" );
		for(String i : noOfItems ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setAnnualIncome(converter.getAFromB(beanA.getParameter( i ) ));
			index++;
		}


		index = 0;
		/* Mapping: "paAnnualIncome" -> "PersonDetailsVO.Gender " */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paGender" );
		for(String i : noOfItems ){
			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setGender(converter.getAFromB(beanA.getParameter( i ) ));
			index++;
		}

		index = 0;
		/* Mapping: "paId" -> "PersonDetailsVO.PersonID */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paId" );
		for(String i : noOfItems ){	
			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setPersonID(beanA.getParameter( i ) );
			index++;
		}


		index = 0;
		/* Mapping: "paAge" -> "PersonDetailsVO.Age */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paAge" );
		for(String i : noOfItems ){	

			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setAge( converter.getAFromB(beanA.getParameter( i )) );
			index++;
		}


		index = 0;
		/* Mapping: "paRelation" -> "PersonDetailsVO.RelationShip*/
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paRelation" );
		for(String i : noOfItems ){	

			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setRelationShip( converter.getAFromB(beanA.getParameter( i )) );
			index++;
		}

		index = 0;
		/* Mapping: "paDOB" -> "PersonDetailsVO.DateOfBirth */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paDOB" );
		for(String i : noOfItems ){	
			
			DateStringConverter dateConverter = ConverterFactory.getInstance(DateStringConverter.class, "", com.Constant.CONST_FORMAT_DD_MM_YYYY );
			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setDateOfBirth(dateConverter.getTypeOfA().cast( dateConverter.getAFromB(beanA.getParameter( i ))));
			index++;
		}
		
		index = 0;
		/* Mapping: "paJoiningDate" -> "PersonDetailsVO.JoiningDate */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paJoiningDate" );
		for(String i : noOfItems ){	

			DateStringConverter dateConverter = ConverterFactory.getInstance(DateStringConverter.class, "", com.Constant.CONST_FORMAT_DD_MM_YYYY );
			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setJoiningDate(dateConverter.getTypeOfA().cast( dateConverter.getAFromB(beanA.getParameter( i ) )));
			index++;
		}


		index = 0;
		/* Mapping: "paEndDate" -> "PersonDetailsVO.JoiningDate */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paEndDate" );
		for(String i : noOfItems ){	

			DateStringConverter dateConverter = ConverterFactory.getInstance(DateStringConverter.class, "", com.Constant.CONST_FORMAT_DD_MM_YYYY );
		
			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setEndDate(dateConverter.getTypeOfA().cast( dateConverter.getAFromB(beanA.getParameter( i ) )));
			index++;
		}


		index = 0;
		/* Mapping: "paNominee" -> "PersonDetailsVO.NomineeName */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paNominee" );
		for(String i : noOfItems ){	
			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setNomineeName(beanA.getParameter( i ) );
			index++;
		}


		index = 0;
		/* Mapping: "GpagprId" -> "PersonDetailsVO.GpagprId */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "GpagprId" );
		for(String i : noOfItems ){	
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getPersonalAccidentPersonVO().get( index ).getPersonDetailsVO().setGprId(converter.getAFromB(beanA.getParameter( i ) ));
			index++;

		}   
		//--setting of cover details

	
		
		/* Mapping: "item_name" -> "PersonDetailsVO.Name" */
		int noOfPersonItems = 0; 
		noOfPersonItems =HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paNameOfPerson" ).size();

		
		int noOfCoverItems = 0;

		/* Mapping: "coverName" -> "covers[].coverName" */
		
		for( int i = 0; i < noOfPersonItems; i++ ){
			
			
			//paCoverDesc

			/* Mapping: "paCoverDesc" -> "Covers.Desc */
		
			System.out.println(beanB.getPersonalAccidentPersonVO().get( i ).getPersonDetailsVO().getName());
			noOfCoverItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paCoverDesc["+i+"][]" ).size();
		
			for( int j = 0; j < noOfCoverItems; j++ ){
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).setCoverDesc(beanA.getParameter( "paCoverDesc[" + i + "][" + j+ "]" )) ;
				
			}

			//paCOverSI

			/* Mapping: "paCOverSI" -> "Covers.SumInsured */
			System.out.println(beanB.getPersonalAccidentPersonVO().get( i ).getPersonDetailsVO().getName());
			noOfCoverItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_PACOVERSI_END+i+"][]" ).size();
			for( int j = 0; j < noOfCoverItems; j++ ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getSumInsured().setSumInsured(converter.getAFromB(beanA.getParameter( com.Constant.CONST_PACOVERSI_END + i + "][" + j+ "]" ) ));
				
														
			}
			
			
			//paCoverExcess

			/* Mapping: "paCoverExcess" -> "Covers.SumInsured.Deductible */
		
			noOfCoverItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paCoverExcess["+i+"][]" ).size();
		
			for( int j = 0; j < noOfCoverItems; j++ ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getSumInsured().setDeductible(converter.getAFromB(beanA.getParameter( "paCoverExcess[" + i + "][" + j+ "]" ))) ;
				
			}
			
			
			
			//paCoverRate

			/* Mapping: "paCoverRate" -> "Covers.Rate */
		
			System.out.println(beanB.getPersonalAccidentPersonVO().get( i ).getPersonDetailsVO().getName());
			noOfCoverItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paCoverRate["+i+"][]" ).size();
		
			for( int j = 0; j < noOfCoverItems; j++ ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).setRate(converter.getAFromB(beanA.getParameter( "paCoverRate[" + i + "][" + j+ "]" ))) ;
				
			}
			

			//pacoverpremium

			/* Mapping: "papaCoverPremium" -> "Covers.PremiumAmt */
		
			System.out.println(beanB.getPersonalAccidentPersonVO().get( i ).getPersonDetailsVO().getName());
			noOfCoverItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_PAPACOVERPREMIUM_END+i+"][]" ).size();
		
			for( int j = 0; j < noOfCoverItems; j++ ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );

				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).setPremiumAmt(converter.getAFromB(beanA.getParameter( com.Constant.CONST_PAPACOVERPREMIUM_END + i + "][" + j+ "]" ))) ;
						
				
			}
			
	
			
			//pacoverpremium

			//setting riskcodes,riskcat,risktype
		
			noOfCoverItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_PAPACOVERPREMIUM_END+i+"][]" ).size();
		
			for( int j = 0; j < noOfCoverItems; j++ ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
				
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getCoverCodes().setCovCode((short)j);
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getCoverCodes().setCovTypeCode((short)j);
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getCoverCodes().setCovSubTypeCode((short)j);

				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getRiskCodes().setRiskCode(j+1);
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getRiskCodes().setBasicRskCode(j+2);
				String str="Cover"+i;
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).setCoverName(str);
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getRiskCodes().setRiskCat(j+3);
				beanB.getPersonalAccidentPersonVO().get( i ).getCovers().get(j).getRiskCodes().setRiskType(j+4);
				

				
			}
			
			
		
		
			
		}

		return dest;

	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PersonalAccidentVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PersonalAccidentVO beanB ){


		int personCount = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "paNameOfPerson[]" ).size();
		BeanUtils.initializeBeanField( "personalAccidentPersonVO[]", beanB, personCount );

		BeanUtils.initializeBeanField( "personalAccidentPersonVO[].personDetailsVO", beanB);
		BeanUtils.initializeBeanField( "personalAccidentPersonVO[].personDetailsVO.sumInsuredVO", beanB);

		for(int i=0; i<personCount; i++){
		
		BeanUtils.initializeBeanField( "personalAccidentPersonVO["+i+"].covers[]", beanB,HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_PACOVERSI_END+i+"][]" ).size());
		}											
		
		BeanUtils.initializeBeanField( "personalAccidentPersonVO[].covers[].sumInsured", beanB);
		BeanUtils.initializeBeanField( "personalAccidentPersonVO[].covers[].coverCodes", beanB);
		BeanUtils.initializeBeanField( "personalAccidentPersonVO[].covers[].riskCodes", beanB);



		return beanB;
	}
}
