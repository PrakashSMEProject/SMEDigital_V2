package com.rsaame.pas.com.svc;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder;

/**
 * @author M1020859
 * 
 * This class is a common service class for saving Underwriting Questions 
 * which calls the BaseSaveOperation to perform the save operation
 * 
 *
 */

public class UWQASaveCommonSvc extends BaseService{

	private BaseSaveSvc baseSaveSvc;

	@Override
	public Object invokeMethod( String methodName, Object... args ){

		BaseVO returnValue = null;
		if( SvcConstants.SAVE_UW_QUES_ANS.equals( methodName ) ){
			saveUWQuestionsAnswers( (BaseVO) args[ 0 ], (BaseVO) args[ 1 ] );
		}
		return returnValue;
	}

	/**
	 * 
	 * @param baseVO
	 * @param policyDataVO
	 * @return
	 */
	private BaseVO saveUWQuestionsAnswers( BaseVO baseVO, BaseVO policyDataVO ){

		PolicyDataVO dataVO = (PolicyDataVO) baseVO;
		PolicyDataVO polDataVo = (PolicyDataVO) policyDataVO;
		if( dataVO.getCommonVO().getLob().equals( LOB.HOME ) ){
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
			if( !Utils.isEmpty( dataVO.getUwQuestions() ) && !Utils.isEmpty( dataVO.getUwQuestions().getQuestions() ) ){
				mapHomeUWQuesToVOHolder( homeInsuranceVO, polDataVo );
			}

		}
		else if( dataVO.getCommonVO().getLob().equals( LOB.TRAVEL ) ){
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) baseVO;
			if( !Utils.isEmpty( dataVO.getUwQuestions() ) && !Utils.isEmpty( dataVO.getUwQuestions().getQuestions() ) ){
				mapTravelUWQuesToVOHolder( travelInsuranceVO, polDataVo );
			}
		}
		else{
			return null;
		}
		return baseVO;
	}

	/**
	 * This method will map the UWQuestionVO list to TTrnUwQuestionsVOHolder
	 * and will pass the data to BaseSaveDAO for persistence
	 * 
	 * @param homeInsuranceVO
	 * @param policyDataVO
	 */
	@SuppressWarnings( "rawtypes" )
	private void mapTravelUWQuesToVOHolder( TravelInsuranceVO travelInsuranceVO, PolicyDataVO policyDataVO ){

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();

		List<TableData> uwQuestionsTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );

		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
		TTrnUwQuestionsVOHolder trnUwQuestionsVOHolder = null;
		TableData<TTrnUwQuestionsVOHolder> uwQuesTableData = null;
		com.rsaame.pas.cmn.converter.IntegerByteConverter byteConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );

		for( UWQuestionVO questionVO : policyDataVO.getUwQuestions().getQuestions() ){
			uwQuesTableData = new TableData<TTrnUwQuestionsVOHolder>();
			trnUwQuestionsVOHolder = new TTrnUwQuestionsVOHolder();
			trnUwQuestionsVOHolder.setUqtPolPolicyId( policyDataVO.getPolicyId() );
			//trnUwQuestionsVOHolder.setUqtPolEndtId( policyDataVO.getCommonVO().getEndtId() );
			trnUwQuestionsVOHolder.setUqtUwqCode( questionVO.getQId() );
			//For travel UW question is in General Info page. So set the LocId as Zero
			trnUwQuestionsVOHolder.setUqtLocId( Long.valueOf( String.valueOf( SvcConstants.zeroVal ) ) );
			trnUwQuestionsVOHolder.setUqtUwqAnswer( questionVO.getResponse() );
			trnUwQuestionsVOHolder.setUqtValidityStartDate( policyDataVO.getCommonVO().getVsd() );
			trnUwQuestionsVOHolder.setUqtPreparedDt( new Date() );
			trnUwQuestionsVOHolder.setUqtStatus( byteConverter.getBFromA( policyDataVO.getStatus() ) );
			uwQuesTableData.setTableData( trnUwQuestionsVOHolder );
			uwQuestionsTableDataList.add( uwQuesTableData );
		}

		policyTableData.setTableData( policyDataVO );
		policyTableDataList.add( policyTableData );

		dataMap.put( SvcConstants.TABLE_ID_T_TRN_POLICY, policyTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_QUO, uwQuestionsTableDataList );

		dataHolder.setData( dataMap );
		baseSaveSvc.invokeMethod( "baseSave", dataHolder, travelInsuranceVO.getCommonVO() );
	}

	/**
	 * This method will map the UWQuestionVO list to TTrnUwQuestionsVOHolder
	 * and will pass the data to BaseSaveDAO for persistence
	 * 
	 * @param homeInsuranceVO
	 * @param policyDataVO
	 */
	@SuppressWarnings( "rawtypes" )
	private void mapHomeUWQuesToVOHolder( HomeInsuranceVO homeInsuranceVO, PolicyDataVO policyDataVO ){

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		List<TableData> uwQuestionsTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );

		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
		TTrnUwQuestionsVOHolder trnUwQuestionsVOHolder = null;
		TableData<TTrnUwQuestionsVOHolder> uwQuesTableData = null;
		com.rsaame.pas.cmn.converter.IntegerByteConverter byteConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );

		for( UWQuestionVO questionVO : homeInsuranceVO.getUwQuestions().getQuestions() ){
			uwQuesTableData = new TableData<TTrnUwQuestionsVOHolder>();
			trnUwQuestionsVOHolder = new TTrnUwQuestionsVOHolder();
			trnUwQuestionsVOHolder.setUqtPolPolicyId( policyDataVO.getPolicyId() );
			/*
			 * Setting endt id as part of fix given
			 * in 3.2 release for underwriting questions. 
			 */
			trnUwQuestionsVOHolder.setUqtPolEndtId(  policyDataVO.getCommonVO().getEndtId() );
			trnUwQuestionsVOHolder.setUqtUwqCode( questionVO.getQId() );
			trnUwQuestionsVOHolder.setUqtLocId( homeInsuranceVO.getBuildingDetails().getRiskCodes().getBasicRskId().longValue() );
			trnUwQuestionsVOHolder.setUqtUwqAnswer( questionVO.getResponse() );
			trnUwQuestionsVOHolder.setUqtValidityStartDate( policyDataVO.getCommonVO().getVsd() );
			trnUwQuestionsVOHolder.setUqtPreparedDt( new Date() );
			trnUwQuestionsVOHolder.setUqtStatus( byteConverter.getBFromA( policyDataVO.getStatus() ) );
			uwQuesTableData.setTableData( trnUwQuestionsVOHolder );
			uwQuestionsTableDataList.add( uwQuesTableData );
		}

		policyTableData.setTableData( policyDataVO );
		policyTableDataList.add( policyTableData );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_POLICY, policyTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_UW_QUESTIONS_QUO, uwQuestionsTableDataList );
		dataHolder.setData( dataMap );
		baseSaveSvc.invokeMethod( "baseSave", dataHolder, homeInsuranceVO.getCommonVO() );
	}

	/**
	 * @return the baseSaveSvc
	 */
	public BaseSaveSvc getBaseSaveSvc(){
		return baseSaveSvc;
	}

	/**
	 * @param baseSaveSvc the baseSaveSvc to set
	 */
	public void setBaseSaveSvc( BaseSaveSvc baseSaveSvc ){
		this.baseSaveSvc = baseSaveSvc;
	}

}
