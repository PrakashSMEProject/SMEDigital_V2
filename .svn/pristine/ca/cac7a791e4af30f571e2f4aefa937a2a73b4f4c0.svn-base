/**
 * LookUpDAO.java
 * Copyright (c) 2011 MindTree Ltd.
 */
package com.rsaame.pas.uwq.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.model.VMasUwQuestions;
import com.rsaame.pas.vo.app.UWQInputsVO;
import com.rsaame.pas.vo.bus.UWQuestionRespType;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 *  Class UnderwriterQDAO is a dao class which extends BaseDAO
 * 
 * @version 1.0  Jan 2012
 * @author m1016303
 *
 */
public class UnderwriterQDAO extends BaseDAO{
	/**
	 * Method to get the list of values for an section
	 * 
	 * @param 
	 * @return  
	 */
	public    BaseVO getListOfUWQuestionDescription(BaseVO baseVO) throws DataAccessException
	{
		//Sonar fix
		List<VMasUwQuestions> uwqList = null; //new ArrayList<VMasUwQuestions>();
		UWQuestionsVO questionListVO = new UWQuestionsVO();
		UWQInputsVO inputVO=(UWQInputsVO)baseVO;
		String[] paramNames={"sectId","tarCode"};
		Object[] values={inputVO.getSectionId(),inputVO.getTarCode()};

		/*To get the list of questions for a given section Name and tariff code*/
		
		uwqList = getHibernateTemplateMislive().findByNamedQueryAndNamedParam("getQuestionsList",paramNames,values);
		
		if(!Utils.isEmpty(uwqList))
			{
				List<UWQuestionVO> uwQuestVOList= new ArrayList<UWQuestionVO>();
				UWQuestionRespType responseType = null;
				for(VMasUwQuestions uwqView:uwqList )
				{	
					/*Preparing VO object for setting the response*/
					if(!Utils.isEmpty(uwqView))
					{
						UWQuestionVO questVO = new UWQuestionVO(null);
						if(!Utils.isEmpty(uwqView.getUwqDefAnswer())){
							questVO.setResponse(uwqView.getUwqDefAnswer());
						}
							if(uwqView.getUwqResponseType().equalsIgnoreCase("BOOLEAN")){
								questVO.setResponseType(responseType.RADIO);
							} else {
								questVO.setResponseType(responseType.TEXT);
							}
						
						if(!Utils.isEmpty(uwqView.getUwqEDescription()))
							questVO.setQDesc(uwqView.getUwqEDescription());
						if(!Utils.isEmpty(uwqView.getUwqCode()))
							questVO.setQId(uwqView.getUwqCode());
						if(!Utils.isEmpty(uwqView.getUwqPreparedDt()))
							questVO.setPreparedDate(uwqView.getUwqPreparedDt());
						
						uwQuestVOList.add(questVO);
					}
				}
				questionListVO.setQuestions(uwQuestVOList);
				
			}
		Logger.getLogger( UnderwriterQDAO.class ).debug( "Underwriter List size "+uwqList.size()  );
		return questionListVO;

	}

	
	
	
}
