package com.rsaame.pas.home.svc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 *  
 * @author Gururaj Hallikeri
 *
 */
public class UWQuestionsLoadSvc extends BaseService{

	private BaseLoadSvc baseLoadSvc;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;

		if( "uwQuestionsLoadService".equals( methodName ) ){
			returnValue = (BaseVO) uwQuestionsLoadService( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	/**
	 * 
	 * @param loadOperation
	 * @return
	 */
	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}

	/**
	 * @param baseVO
	 * @return
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	private UWQuestionsVO uwQuestionsLoadService( BaseVO baseVO ){

		LoadDataInputVO loadInputVO = (LoadDataInputVO) baseVO;

		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();

		dataToLoad.put( "UW_QUESTION", UWQuestionVO.class );

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( "baseLoad",
				loadInputVO, dataToLoad );

		List<TableData> uwQuestionVOList = dataHolderVO.getData().get( "UW_QUESTION" );
		UWQuestionsVO uwQuestionsVO = new UWQuestionsVO();

		List<UWQuestionVO> questionVOs = new ArrayList<UWQuestionVO>();

		if( !Utils.isEmpty( uwQuestionVOList ) ){
			for( TableData tableData : uwQuestionVOList ){
				UWQuestionVO uwQuestionVO = (UWQuestionVO) tableData.getTableData();
				questionVOs.add( uwQuestionVO );
			}
		}

		uwQuestionsVO.setQuestions( questionVOs );

		return uwQuestionsVO;
	}

}
