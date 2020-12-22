package com.rsaame.pas.reports.ui;

import java.text.Format;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ReportDocRH implements IRequestHandler{

	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		Response responseObj = new Response();
		Format format;
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		request.setAttribute( "polLinkingId", policyVO.getPolLinkingId() );
		request.setAttribute( "endtId",  policyVO.getEndtId());
		format = new SimpleDateFormat("dd-MMM-yyyy");
		String sdate = format.format( policyVO.getValidityStartDate() );
		System.out.println(sdate+"sdate");
		request.setAttribute( "valStrtDtForm", sdate);
		System.out.println("ReportsRH --->"+policyVO.getPolLinkingId()+policyVO.getEndtId());
		return responseObj;
	}
	


}
