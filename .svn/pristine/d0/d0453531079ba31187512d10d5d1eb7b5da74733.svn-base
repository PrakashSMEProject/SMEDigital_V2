package com.rsaame.pas.insured.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.app.InsuredCommentListVO;
import com.rsaame.pas.vo.bus.InsuredVO;

public class ViewInsuredCommentsRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( ViewInsuredCommentsRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.info( "*****Inside ViewInsuredCommentsRH*****" );
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		String insuredId = request.getParameter( "insuredId" );

		InsuredVO insuredVO = new InsuredVO();
		insuredVO.setInsuredId( Long.valueOf( insuredId ) );

		BaseVO baseVO = TaskExecutor.executeTasks( identifier, insuredVO );
		logger.debug( "*****Executed taskExecutor in ViewInsuredCommentsRH*****" );

		InsuredCommentListVO commentsListVO = (InsuredCommentListVO) baseVO;

		if( Utils.isEmpty( commentsListVO ) || ( !Utils.isEmpty( commentsListVO ) && Utils.isEmpty( commentsListVO.getInsuredComments() ) ) ){
			throw new BusinessException( "", null, "No records found." );
		}

		request.setAttribute( "insuredComments", baseVO );
		response.setData( baseVO );
		return response;
	}

}
