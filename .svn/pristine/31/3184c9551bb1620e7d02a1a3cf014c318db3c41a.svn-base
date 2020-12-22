/**
 * 
 */
package com.rsaame.pas.uwq.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.rsaame.pas.uwq.dao.UnderwriterQDAO;
import com.rsaame.pas.vo.app.UWQInputsVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author m1016303
 *
 */
public class UWQService extends BaseService{

	@Override
	public Object invokeMethod(String methodName, Object... args) {
		Object returnValue = null;
		if(methodName.equals("getListOfDescription")){
			returnValue=getListOfDescription((BaseVO)args[ 0 ]);
		}
		return returnValue;
	}
static UnderwriterQDAO uwqDAO;
	
	
	/**
	 * @param baseVO
	 * @return
	 * @throws SystemException
	 */
	public static BaseVO getListOfDescription(BaseVO baseVO) throws SystemException
	{
		uwqDAO=new UnderwriterQDAO();
		UWQuestionsVO questionListVO =(UWQuestionsVO)uwqDAO.getListOfUWQuestionDescription(baseVO);
		return  questionListVO;
	}

	public static void main(String[] args) {
		UWQInputsVO uwqVO = new UWQInputsVO();
		uwqVO.setSectionId(1);
		uwqVO.setTarCode(1);
		UWQuestionsVO questionListVO =(UWQuestionsVO)getListOfDescription(uwqVO);
		for(UWQuestionVO quest:questionListVO.getQuestions()){
			System.out.println("question: "+quest.getQDesc()+" answer: "+quest.getResponse()+" responsetype: "+quest.getResponseType());
			
		}
		
	}

	}
