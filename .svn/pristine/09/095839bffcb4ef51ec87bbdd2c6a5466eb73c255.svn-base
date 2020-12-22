package com.rsaame.pas.vo.voholder.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.InsurerVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolder;

public class GeneralInfoVOToTTrnCoInsuranceVOHolderWrapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper, com.rsaame.pas.vo.bus.GeneralInfoVO>{

	private final Logger log = Logger.getLogger( this.getClass() );	

	public GeneralInfoVOToTTrnCoInsuranceVOHolderWrapperReverse(){
		super();
	}

	public GeneralInfoVOToTTrnCoInsuranceVOHolderWrapperReverse(com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper src, com.rsaame.pas.vo.bus.GeneralInfoVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.GeneralInfoVO mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.GeneralInfoVO) Utils.newInstance( "com.rsaame.pas.vo.bus.GeneralInfoVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GeneralInfoVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);	


		//--beanA is GeneralInfoVO
		// beanB is TTrnCoInsuranceVOHolderWrapper



		//TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
		List<InsurerVO> insurerVOList=new ArrayList<InsurerVO>();

		List<TableData> tTrnCoInsuranceVOHolderList = beanA.getTTrnCoInsuranceVOHolderList();

		for( TableData tTrnCoInsuranceVOHolderWrapperData : tTrnCoInsuranceVOHolderList ){

			InsurerVO insurerVO =new InsurerVO();

			TableData<TTrnCoInsuranceVOHolder> gaccTableData=(TableData<TTrnCoInsuranceVOHolder>) tTrnCoInsuranceVOHolderWrapperData.getTableData(); 

			TTrnCoInsuranceVOHolder tTrnCoInsuranceVOHolder=gaccTableData.getTableData();


			/* Mapping: "gprEName" -> "name" */
			if(  !Utils.isEmpty( tTrnCoInsuranceVOHolder.getCoiPolicyId() )  ){
				insurerVO.setId(insurerVO.getId()  ); 
			}


			/* Mapping: "Type" -> "gprRtCode" */
			if(  !Utils.isEmpty( tTrnCoInsuranceVOHolder.getCoiCoinsuranceCode() ) ){

				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				insurerVO.setCompanyName(converter.getBFromA( tTrnCoInsuranceVOHolder.getCoiCoinsuranceCode())); 


			}

			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty(tTrnCoInsuranceVOHolder.getCoiCommissionPerc())  ){
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

				insurerVO.setAdminCharge(converter.getTypeOfB().cast( converter.getBFromA(tTrnCoInsuranceVOHolder.getCoiCommissionPerc()))); 
			}
			
			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( tTrnCoInsuranceVOHolder.getCoiPercentage())  ){
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

				insurerVO.setPercentage(converter.getTypeOfB().cast( converter.getBFromA(tTrnCoInsuranceVOHolder.getCoiPercentage()))); 
			}


			/* Mapping: "AnnualIncome" -> "gprSalary" */
			if(  !Utils.isEmpty( tTrnCoInsuranceVOHolder.getCoiPolicyNo())  ){
				com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );

				insurerVO.setPolicyNo(converter.getTypeOfA().cast( converter.getAFromB(tTrnCoInsuranceVOHolder.getCoiPolicyNo()) )); 
			}
			
			/* Mapping: "AnnualIncome" -> "gprSalary" */
			if(  !Utils.isEmpty( tTrnCoInsuranceVOHolder.getCoiLeadFlag())  ){
				insurerVO.setIsLeader(tTrnCoInsuranceVOHolder.getCoiLeadFlag()); 


			}
			if(  !Utils.isEmpty( tTrnCoInsuranceVOHolder.getCoiValidityStartDate())  ){
				insurerVO.setValidityStartDate(tTrnCoInsuranceVOHolder.getCoiValidityStartDate()); 


			}
			insurerVOList.add(insurerVO);
			
		}

		beanB.setCoInsurer(insurerVOList);
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.GeneralInfoVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper beanA, com.rsaame.pas.vo.bus.GeneralInfoVO beanB ){
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
