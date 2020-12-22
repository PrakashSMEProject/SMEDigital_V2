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

public class GeneralInfoVOToTTrnCoInsuranceVOHolderWrapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.GeneralInfoVO, com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public GeneralInfoVOToTTrnCoInsuranceVOHolderWrapper(){
		super();
	}

	public GeneralInfoVOToTTrnCoInsuranceVOHolderWrapper( com.rsaame.pas.vo.bus.GeneralInfoVO src, com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper" );
		}

		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.GeneralInfoVO beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);	


		//--beanA is GeneralInfoVO
		// beanB is TTrnCoInsuranceVOHolderWrapper



		//TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
		List<TableData> tTrnCoInsuranceVOHolderList=new ArrayList<TableData>();

		List<InsurerVO> insurerVOList = beanA.getCoInsurer();

		for( InsurerVO insurerVO : insurerVOList ){

			TTrnCoInsuranceVOHolder trnCoInsuranceVOHolder=new TTrnCoInsuranceVOHolder();
			TableData<TTrnCoInsuranceVOHolder> gaccTableData = new TableData<TTrnCoInsuranceVOHolder>();


			/* Mapping: "gprEName" -> "name" */
			if(  !Utils.isEmpty( insurerVO.getId() )  ){
				trnCoInsuranceVOHolder.setCoiPolicyId(insurerVO.getId()  ); 
			}


			/* Mapping: "Type" -> "gprRtCode" */
			if(  !Utils.isEmpty( insurerVO.getCompanyName())  ){

				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				trnCoInsuranceVOHolder.setCoiCoinsuranceCode(converter.getTypeOfA().cast( converter.getAFromB( insurerVO.getCompanyName()) )); 


			}

			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( insurerVO.getAdminCharge())  ){
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

				trnCoInsuranceVOHolder.setCoiCommissionPerc(converter.getTypeOfA().cast( converter.getAFromB(insurerVO.getAdminCharge()))); 
			}
			
			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( insurerVO.getPolicyNo())  ){
				com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );

				trnCoInsuranceVOHolder.setCoiPolicyNo( converter.getBFromA(insurerVO.getPolicyNo())); 
			}


			/* Mapping: "AnnualIncome" -> "gprSalary" */
			if(  !Utils.isEmpty( insurerVO.getPercentage())  ){
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
				trnCoInsuranceVOHolder.setCoiPercentage(converter.getTypeOfA().cast( converter.getAFromB(insurerVO.getPercentage()))); 


			}
			
			/* Mapping: "AnnualIncome" -> "gprSalary" */
			if(  !Utils.isEmpty( insurerVO.getIsLeader())  ){
				trnCoInsuranceVOHolder.setCoiLeadFlag(insurerVO.getIsLeader()); 


			}
			
			/* Mapping: "AnnualIncome" -> "gprSalary" */
			if(  !Utils.isEmpty( insurerVO.getValidityStartDate())  ){
				trnCoInsuranceVOHolder.setCoiValidityStartDate(insurerVO.getValidityStartDate()); 


			}

			//-- in the list
			gaccTableData.setTableData( trnCoInsuranceVOHolder );

			tTrnCoInsuranceVOHolderList.add( gaccTableData );
		}

		beanB.setTTrnCoInsuranceVOHolderList(tTrnCoInsuranceVOHolderList);
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper initializeDeepVO( com.rsaame.pas.vo.bus.GeneralInfoVO beanA, com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolderWrapper beanB ){
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
