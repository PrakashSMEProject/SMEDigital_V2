package com.rsaame.pas.reports.ui;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class ProposalFormDocRH implements IRequestHandler{

	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		Response responseObj = new Response();
		Format format;
		String identifier = request.getParameter( "opType" );
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		CommonVO commonVO = policyContext.getCommonDetails();
		BaseVO baseVO = null;
		String currentLob = null;
		DataHolderVO<Object[]> data = null;
		
		if(!Utils.isEmpty(policyVO)){
			baseVO = TaskExecutor.executeTasks(identifier, policyVO);
			currentLob = "SBS";
		}else if(!Utils.isEmpty(commonVO)){
			baseVO = TaskExecutor.executeTasks(identifier, commonVO);
			currentLob = commonVO.getLob().toString();
		}
		if(!Utils.isEmpty( baseVO )){
			data = (DataHolderVO<Object[]>) baseVO;
			Object quoteDetails[] = data.getData();
	
			request.setAttribute( "polLinkingId", quoteDetails[ 0 ].toString() );
			request.setAttribute( "endtId", quoteDetails[ 1 ].toString() );
			request.setAttribute( "policyId",  quoteDetails[3].toString() );
			request.setAttribute( "currentLob",  currentLob );
			
			format = new SimpleDateFormat( "dd-MMM-yyyy" );
			Date date = (Date) quoteDetails[ 2 ];
			System.out.println( date + "date" );
			String sdate = format.format( date );
	
			System.out.println( sdate + "sdate" );
			request.setAttribute( "valStrtDtForm", sdate );
			request.setAttribute( "locCode", Utils.getSingleValueAppConfig("DEPLOYED_LOCATION") );
		}

		String reportTemplatesType = null;
		
		if(!Utils.isEmpty(policyVO)){
			
			reportTemplatesType = ReportTemplateSet._SBS.toString();
			
			if( (!Utils.isEmpty( policyVO.getGeneralInfo().getSourceOfBus().getBrokerName() )
					&& policyVO.getGeneralInfo().getSourceOfBus().getBrokerName().toString().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_CODE" ) ) )
					||  (!Utils.isEmpty( policyVO.getScheme()  )  && policyVO.getScheme().getSchemeCode().toString().equals( Utils.getSingleValueAppConfig( "FGB_BROKER_DIRECT_SCHEME_CODE" )))){
				reportTemplatesType = ReportTemplateSet._FGB_SBS.toString();
			}
			request.setAttribute( "reportTemplatesType", reportTemplatesType );
		}else if(!Utils.isEmpty(commonVO)){
			
			ReportTemplateSet reportTemplatesType1 = ReportTemplateSet.valueOf("_"+commonVO.getLob().toString());    //_HOME.getProposalFormTemplate();
			
			/*if( !Utils.isEmpty( commonVO.getLob() ) && commonVO.getLob().equals(LOB.TRAVEL)){
				reportTemplatesType = ReportTemplateSet._TRAVEL.toString();
			}*/
			request.setAttribute( "reportTemplatesType", reportTemplatesType1.getProposalFormTemplate() );
		}
		
		
		return responseObj;
	}
	

}
