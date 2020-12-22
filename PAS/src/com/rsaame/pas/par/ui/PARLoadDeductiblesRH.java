/**
 * 
 */
package com.rsaame.pas.par.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.app.ParContentHolder;

/**
 * @author m1016303
 *
 */
public class PARLoadDeductiblesRH  implements IRequestHandler {

	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse resp) {
		
		Response response = new Response();
		String identifier = request.getParameter("opType");
		BaseVO baseVo = null;
		try{
			BaseVO baseVO = TaskExecutor.executeTasks(identifier,baseVo);
			ParContentHolder parContents = (ParContentHolder)baseVO;
			request.setAttribute("parContents",parContents);
			Redirection redirection = new Redirection("/jsp/quote/buildingDeductibles.jsp",Redirection.Type.TO_JSP);
			response.setRedirection( redirection );
		}catch(SystemException ex){
			ex.printStackTrace();
			System.out.println("ex-->"+ex);
		}catch(BusinessException be)
		{
			System.out.println("Business Exception encountered-->");
			System.out.println("Get errorkeylist -->");			
			for(String s:be.getErrorKeysList()){
				System.out.println("s-->"+s);
			}
		}
	return response;

	}


}
