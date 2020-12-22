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

public class GeneralInfoVOToTTrnHirePurchaseVOHolderWrapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.GeneralInfoVO, com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public GeneralInfoVOToTTrnHirePurchaseVOHolderWrapper(){
		super();
	}

	public GeneralInfoVOToTTrnHirePurchaseVOHolderWrapper( com.rsaame.pas.vo.bus.GeneralInfoVO src, com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper" );
		}

		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.GeneralInfoVO beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);	


		//--beanA is GeneralInfoVO
		// beanB is TTrnHirePurchaseVOHolderWrapper



		//TableData<TTrnGaccPersonVOHolder> gaccTableData = new TableData<TTrnGaccPersonVOHolder>();
		List<TableData> tTrnHirePurchaseVOHolderList=new ArrayList<TableData>();

		List<FinancierVO> financierVOList = beanA.getFinancier();

		for( FinancierVO financier : financierVOList ){

			TTrnHirePurchaseVOHolder tTrnHirePurchaseVOHolder=new TTrnHirePurchaseVOHolder();
			TableData<TTrnHirePurchaseVOHolder> gaccTableData = new TableData<TTrnHirePurchaseVOHolder>();


			/* Mapping: "gprEName" -> "name" */
			if(  !Utils.isEmpty( financier.getId() )  ){
				tTrnHirePurchaseVOHolder.setHpPolicyId(financier.getId()  ); 
			}


			/* Mapping: "Type" -> "gprRtCode" */
			if(  !Utils.isEmpty( financier.getName())  ){

				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				tTrnHirePurchaseVOHolder.setHpCode(converter.getTypeOfA().cast( converter.getAFromB( financier.getName()) )); 


			}

			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( financier.getAmount())  ){
				com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );

				tTrnHirePurchaseVOHolder.setHpAmount(converter.getTypeOfA().cast( converter.getAFromB(financier.getAmount()))); 
			}
			
			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( financier.getExpiryDate())  ){
				tTrnHirePurchaseVOHolder.setHpExpiryDate( financier.getExpiryDate()); 
			}	
			
			/* Mapping: "Category" -> "gprRcCode" */
			if(  !Utils.isEmpty( financier.getValidityStartDate())  ){
				tTrnHirePurchaseVOHolder.setHpValidityStartDate( financier.getValidityStartDate()); 
			}	

			//-- in the list
			gaccTableData.setTableData( tTrnHirePurchaseVOHolder );

			tTrnHirePurchaseVOHolderList.add( gaccTableData );
		}

		beanB.setTTrnHirePurchaseVOHolderList(tTrnHirePurchaseVOHolderList);
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper initializeDeepVO( com.rsaame.pas.vo.bus.GeneralInfoVO beanA, com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper beanB ){
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
