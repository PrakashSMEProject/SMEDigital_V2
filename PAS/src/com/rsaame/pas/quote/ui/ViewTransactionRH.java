/**
 * 
 */
package com.rsaame.pas.quote.ui;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.bus.SectionDetailsVO;
import com.rsaame.pas.vo.bus.TransactionVO;
import com.rsaame.pas.vo.bus.ViewTransactionRequestVO;
import com.rsaame.pas.vo.bus.ViewTransactionResponseVO;

/**
 * @author M1016284
 *
 */
public class ViewTransactionRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( ViewTransactionRH.class );

	@Override
	public Response execute( HttpServletRequest requestObj, HttpServletResponse responseObj ){
		List<SectionDetailsVO> sectionDetailsVOList = null;
		Iterator<SectionDetailsVO> sectionDetailsVOListItr = null;
		SectionDetailsVO sectionDetailsVO = null;
		long secSecId = 0L;
		BaseVO policyBaseVO = null;

		Response response = new Response();

		String identifier = requestObj.getParameter( "opType" );
		logger.debug( "********** Inside ViewTransactionRH ***********" );

		ViewTransactionRequestVO viewTransReqVO = new ViewTransactionRequestVO();
		TransactionVO transVO = new TransactionVO();
		transVO.setTransactionPolicyNumber( "2400004" );//479711
		transVO.setTransactionEndtId( "0" );
		transVO.setPolicyTariffCode( "41" );//41
		transVO.setTransactionType( "new_quotation" );
		viewTransReqVO.setTransaction( transVO );

		System.out.println( "Trans no--->" + viewTransReqVO.getTransaction().getTransactionNo() );
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, viewTransReqVO );
		logger.debug( "Executed taskExecutor." );
		logger.debug( "baseVO -->" + baseVO );

		if( null != baseVO ){
			ViewTransactionResponseVO viewTransRespVO = (ViewTransactionResponseVO) baseVO;

			sectionDetailsVOList = viewTransRespVO.getSectionDetailsVOList();
			sectionDetailsVOListItr = sectionDetailsVOList.iterator();

			while( sectionDetailsVOListItr.hasNext() ){
				sectionDetailsVO = sectionDetailsVOListItr.next();

				secSecId = sectionDetailsVO.getSecSecId();
				// TODO:
				if( new Integer( Utils.getSingleValueAppConfig( "PAR_SECTION" ) ).intValue() == secSecId ){
					identifier = "PAR_FETCH";
					policyBaseVO = TaskExecutor.executeTasks( identifier, sectionDetailsVO );
				}
			}
		}
		if( !Utils.isEmpty( policyBaseVO ) ){
			response.setSuccess( true );
			response.setData( policyBaseVO );
		}

		return response;

	}

}
