/**
 * 
 */
package com.rsaame.pas.reports.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.BrReportSearchVO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.ReportsResultsHolder;
import com.rsaame.pas.vo.app.ReportsSearchVO;

/**
 * @author M1014957
 *
 */
public class ReportsSearchRH implements IRequestHandler {
	
	private static final Map<String, String> policyStatusMAP = new Map<String, String>( null, null );
	private static final Map<String, String> policyTypeMAP = new Map<String, String>( null, null );
	private static final Map<String, String> policyStatusDescMAP = new Map<String, String>( null, null );

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse responseObj) {
		// TODO Auto-generated method stub
		Response response = new Response();
		BaseVO baseVO = null;

		// Identifier will be used further in TaskExecutor to identify
		// request processing
		//String identifier = request.getParameter( "opType" ).toString();
		String actionAttr = request.getParameter( AppConstants.ACTION );
		if("SEARCH_LIVE_POL".equals(actionAttr)){
		ReportsSearchVO searchVO = BeanMapper.map( request, ReportsSearchVO.class );
		 baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		else if("SEARCH_BROKER_ACCOUNT".equals(actionAttr)){
			BrReportSearchVO searchVO = BeanMapper.map( request, BrReportSearchVO.class );
			 baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		else if("SEARCH_CLASS_WISE_PRM".equals(actionAttr)){
			ReportsSearchVO searchVO = BeanMapper.map( request, ReportsSearchVO.class );
			 baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		else if("SEARCH_PAYMENT".equalsIgnoreCase(actionAttr)){
			ReportsSearchVO searchVO = BeanMapper.map( request, ReportsSearchVO.class );
			 baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		else if("SEARCH_TRANS_REPORT".equalsIgnoreCase(actionAttr)){
			ReportsSearchVO searchVO = BeanMapper.map( request, ReportsSearchVO.class );
			baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		else if("SEARCH_DETAILED_TRANS_REPORT".equalsIgnoreCase(actionAttr)){
			String insuranceType = request.getParameter("insuranceType");
			String cellIndex = request.getParameter("cellIndex");
			String startDate = request.getParameter(com.Constant.CONST_STARTDATE);
			String endDate = request.getParameter(com.Constant.CONST_ENDDATE);
			String businessLine = request.getParameter( "businessLine" );
			getPolicyStatusMap();
			getPolicyStatusDescMap();
			getPolicyTypeDescMap();
			
			List<String> dateParamList = new  ArrayList<String>();
			dateParamList.add(startDate);
			dateParamList.add(endDate);
			if(policyTypeMAP.containsKey(insuranceType)){
				dateParamList.add(policyTypeMAP.get(insuranceType));
			}
			if(policyStatusMAP.containsKey(cellIndex)){
				dateParamList.add(policyStatusMAP.get(cellIndex));
			}
			if(policyStatusDescMAP.containsKey(cellIndex)){
				dateParamList.add(policyStatusDescMAP.get(cellIndex));
			}
			dateParamList.add(businessLine);
			DataHolderVO<List<String>> dataHolderVO = new DataHolderVO<List<String>>();
			dataHolderVO.setData(dateParamList);
			baseVO = TaskExecutor.executeTasks( actionAttr, dataHolderVO );
			request.setAttribute(com.Constant.CONST_STARTDATE, startDate);
			request.setAttribute(com.Constant.CONST_ENDDATE, endDate);
			responseObj.setHeader(com.Constant.CONST_POLICYTYPE, policyTypeMAP.get(insuranceType));
			responseObj.setHeader(com.Constant.CONST_POLICYSTATUS, policyStatusMAP.get(cellIndex));
			responseObj.setHeader(com.Constant.CONST_STATUSDESCRIPTION, policyStatusDescMAP.get(cellIndex));
			
			request.setAttribute(com.Constant.CONST_POLICYTYPE, policyTypeMAP.get(insuranceType));
			request.setAttribute(com.Constant.CONST_POLICYSTATUS, policyStatusMAP.get(cellIndex));
			request.setAttribute(com.Constant.CONST_STATUSDESCRIPTION, policyStatusDescMAP.get(cellIndex));
			//added for abudhabi/baharain
			request.setAttribute( "locCode", Utils.getSingleValueAppConfig("DEPLOYED_LOCATION") );
			
		}
		else if("TRANS_DETAILS".equalsIgnoreCase(actionAttr)){
			String startDate = request.getParameter(com.Constant.CONST_STARTDATE);
			String endDate = request.getParameter(com.Constant.CONST_ENDDATE);
			request.setAttribute(com.Constant.CONST_STARTDATE, startDate);
			request.setAttribute(com.Constant.CONST_ENDDATE, endDate);
			request.setAttribute(com.Constant.CONST_POLICYTYPE, (String)request.getParameter(com.Constant.CONST_POLICYTYPE));
			request.setAttribute(com.Constant.CONST_POLICYSTATUS, (String)request.getParameter(com.Constant.CONST_POLICYSTATUS));
			request.setAttribute(com.Constant.CONST_STATUSDESCRIPTION, (String)request.getParameter(com.Constant.CONST_STATUSDESCRIPTION));
			//added for abudhabi/baharain
			request.setAttribute( "locCode", Utils.getSingleValueAppConfig("DEPLOYED_LOCATION") );
			
			Redirection redirection = new Redirection();
			redirection.setUrl("/jsp/reports/transactionReportRedirect.jsp");
			redirection.setType(Type.TO_JSP);
			response.setRedirection(redirection);
		}
		else if("SEARCH_PROMOTIONAL_CODE".equalsIgnoreCase(actionAttr)){
			ReportsSearchVO searchVO = BeanMapper.map( request, ReportsSearchVO.class );
			 baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		else if("SEARCH_RENEWAL_PAYMENT".equalsIgnoreCase(actionAttr)){
			ReportsSearchVO searchVO = BeanMapper.map( request, ReportsSearchVO.class );
			 baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		/*
		 * 81120 New report on quotes included as
		 * part of 3.2 release
		 */
		else if("SEARCH_QUOTE".equalsIgnoreCase(actionAttr)){
			ReportsSearchVO searchVO = BeanMapper.map( request, ReportsSearchVO.class );
			 baseVO = TaskExecutor.executeTasks( actionAttr, searchVO );
		}
		
		ReportsResultsHolder contents = (ReportsResultsHolder)baseVO;
		if( !Utils.isEmpty( contents ) ){
			response.setSuccess( true );
			response.setData( contents );
		}

		return response;
	}
	
	protected void getPolicyTypeDescMap(){
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "PAS_LOBMST", "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			policyTypeMAP.put( luVO.getDescription(), luVO.getCode().toString() );
		}
	}
	
	protected void getPolicyStatusDescMap(){
		policyStatusDescMAP.put( "1","TOTAL_QUOTATIONS"  );
		policyStatusDescMAP.put( "2","ABONDONED_QUOTATIONS"  );
		policyStatusDescMAP.put( "3","SAVED_QUOTATIONS"  );
		policyStatusDescMAP.put( "4","PAYMENT_RECEIVED"  );
		policyStatusDescMAP.put( "5","REFERRAL_PENDING"  );
		policyStatusDescMAP.put( "6","ACCEPTED_REFERRAL"  );
		policyStatusDescMAP.put( "7","CONVERTED_REFERRAL"  );
		policyStatusDescMAP.put( "8","REJECTED_REFERRAL"  );
		policyStatusDescMAP.put( "9","CONVERTED_TO_POLICY"  );
		policyStatusDescMAP.put("10", "ASSISTED_QUOTATION");
			
	}
	
	protected void getPolicyStatusMap(){
		policyStatusMAP.put( "1",null);
		policyStatusMAP.put( "2","6");
		policyStatusMAP.put( "3", "1");
		policyStatusMAP.put( "4", "1");
		policyStatusMAP.put( "5", "20");
		policyStatusMAP.put( "6", "23");
		policyStatusMAP.put( "7", "7");
		policyStatusMAP.put( "8", "22");
		policyStatusMAP.put( "9", "1");
		policyStatusMAP.put( "10", "1");
			
	}

}
