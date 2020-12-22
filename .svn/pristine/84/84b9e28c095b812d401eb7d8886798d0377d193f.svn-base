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
import com.rsaame.pas.vo.bus.FinancierVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder;

public class GeneralInfoVOToTTrnHirePurchaseVOHolderWrapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper, com.rsaame.pas.vo.bus.GeneralInfoVO>{

	private final Logger log = Logger.getLogger( this.getClass() );	

	public GeneralInfoVOToTTrnHirePurchaseVOHolderWrapperReverse(){
		super();
	}

	public GeneralInfoVOToTTrnHirePurchaseVOHolderWrapperReverse(com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper src, com.rsaame.pas.vo.bus.GeneralInfoVO dest ){
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
		com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GeneralInfoVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);	


		//--beanA is GeneralInfoVO
		// beanB is TTrnCoInsuranceVOHolderWrapper



		//TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
		List<FinancierVO> financierVOList=new ArrayList<FinancierVO>();

		List<TableData> tTrnHirePurchaseVOHolderList = beanA.getTTrnHirePurchaseVOHolderList();

		for( TableData tTrnHirePurchaseVOHolderWrapperData : tTrnHirePurchaseVOHolderList ){

			FinancierVO financierVO =new FinancierVO();

			TableData<TTrnHirePurchaseVOHolder> gaccTableData=(TableData<TTrnHirePurchaseVOHolder>) tTrnHirePurchaseVOHolderWrapperData.getTableData(); 

			TTrnHirePurchaseVOHolder tTrnHirePurchaseVOHolder=gaccTableData.getTableData();


			/* Mapping: "gprEName" -> "name" */
			if(  !Utils.isEmpty( tTrnHirePurchaseVOHolder.getHpPolicyId() )  ){
				com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
				financierVO.setId(converter.getTypeOfA().cast( converter.getAFromB(tTrnHirePurchaseVOHolder.getHpPolicyId()))); 
			}


			/* Mapping: "Type" -> "gprRtCode" */
			if(  !Utils.isEmpty( tTrnHirePurchaseVOHolder.getHpAmount()) ){

				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
				financierVO.setAmount(converter.getTypeOfB().cast( converter.getBFromA(tTrnHirePurchaseVOHolder.getHpAmount()))); 


			}

			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty(tTrnHirePurchaseVOHolder.getHpExpiryDate()) ){
				financierVO.setExpiryDate(tTrnHirePurchaseVOHolder.getHpExpiryDate()); 
			}
			
			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( tTrnHirePurchaseVOHolder.getHpCode())  ){
				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				financierVO.setName(converter.getTypeOfB().cast( converter.getBFromA(tTrnHirePurchaseVOHolder.getHpCode()))); 
			}	
			
			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty(tTrnHirePurchaseVOHolder.getHpValidityStartDate()) ){
				financierVO.setValidityStartDate(tTrnHirePurchaseVOHolder.getHpValidityStartDate()); 
			}
			financierVOList.add(financierVO);
			
		}

		beanB.setFinancier(financierVOList);
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.GeneralInfoVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper beanA, com.rsaame.pas.vo.bus.GeneralInfoVO beanB ){
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
