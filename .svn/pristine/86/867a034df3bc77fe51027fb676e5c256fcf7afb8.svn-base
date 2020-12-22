package com.rsaame.pas.wc.ui;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WCVO;
/**
 * Servlet implementation class WCEmployeeDetailUploadRH
 */
public class WCEmployeeDetailUploadRH implements IRequestHandler {
	@Override
	public Response execute( HttpServletRequest httpRequest, HttpServletResponse httpdRresponse ){
		Response response = new Response();
		String action=httpRequest.getParameter( com.Constant.CONST_ACTION );
		
		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( httpRequest ).getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, AppConstants.SECTION_ID_WC );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );
		OutputStream out = null;
		/*
		 * Submit the employee details
		 */
		if(action.equalsIgnoreCase("WC_EMP_SAVE")){
			
			WCVO wcVO = (WCVO)httpRequest.getSession().getAttribute("currRGD");
			WCVO mappedwcVO = new WCVO();
			/*
			if(Utils.isEmpty(wcVO)){
				wcVO = new WCVO();
			}else{*/
			if( !Utils.isEmpty( mappedwcVO ) ){
				List<WCNammedEmployeeVO> empList = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>( WCNammedEmployeeVO.class );
				mappedwcVO.setWcEmployeeDetails( empList );
			}
			mappedwcVO = (WCVO) mapRiskGroupDetails( httpRequest , mappedwcVO );
			httpRequest.setAttribute(com.Constant.CONST_ACTION, action);
			httpRequest.setAttribute("currRGD", wcVO );
			httpRequest.setAttribute("namedEmpList", mappedwcVO.getWcEmployeeDetails() );
			httpRequest.getSession().setAttribute("mappedwcVOSession", mappedwcVO );
			AppUtils.setSectionPageDataForJSON( httpRequest, sectionVO, locationVO, mappedwcVO, policyVO );
			setRedirectionIfAny( response);
			
		}else if(action.equalsIgnoreCase("WC_EMP_DOWNLOAD")){ 
			//Added for Adventnet Id:96090;To support Download function of Employee Details
			try {
				WCVO mappedwcVO = new WCVO();
				if( !Utils.isEmpty( mappedwcVO ) ){
					List<WCNammedEmployeeVO> empList = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>( WCNammedEmployeeVO.class );
					mappedwcVO.setWcEmployeeDetails( empList );
				}
				mappedwcVO = (WCVO) mapRiskGroupDetails( httpRequest , mappedwcVO );
				
				//WCVO wcVO = (WCVO) httpRequest.getSession().getAttribute(
				//		"currRGD");
				writeContentToStream(httpdRresponse, mappedwcVO);
				response.setSuccess(true);
				response.setData("File downloaded successfully");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			httpRequest.setAttribute(com.Constant.CONST_ACTION, action);
			Redirection redirection = new Redirection(Utils.getSingleValueAppConfig("WC_EMP_UPLOAD"),Redirection.Type.TO_JSP);

			response.setRedirection(redirection);
		}
		return response;
	}
	protected void setRedirectionIfAny( Response responseObj ){

			Redirection redirection = new Redirection();
			String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION" ) ), true );
			redirection.setUrl( redirectionURL );
			redirection.setType( Type.TO_JSP );
			responseObj.setRedirection( redirection );
	}
	
	public  RiskGroupDetails mapRiskGroupDetails( HttpServletRequest request,WCVO wcVO  ){
	List<String> noOfItemsGpr = null;
	List<String> noOfItemsWpr = null;
	Integer index = null;
			/* Mapping: "wcEmpName" -> "wcEmployeeDetails[].empName" */
		noOfItemsGpr = HTTPUtils.getMatchingMultiReqParamKeys( request, "wcEmpName" );
		 Collections.sort(noOfItemsGpr, new Comparator<String>() {
		        public int compare(String o1, String o2) {
		            return extractInt(o1) - extractInt(o2);
		        }

		        int extractInt(String s) {
		            String num = s.replaceAll("\\D", "").replace("[", "").replace("]", "");
		            // return 0 if no digits found
		            return num.isEmpty() ? 0 : Integer.parseInt(num);
		        }
		    });
		BeanUtils.initializeBeanField( "wcEmployeeDetails[]", wcVO, 
				noOfItemsGpr.size() );
		index = 0;
		for( String i : noOfItemsGpr ){
			wcVO.getWcEmployeeDetails().get( index++ ).setEmpName( request.getParameter( i ) );
		}
		
		noOfItemsWpr = HTTPUtils.getMatchingMultiReqParamKeys( request, "wcPrId" );
		Collections.sort(noOfItemsWpr, new Comparator<String>() {
	        public int compare(String o1, String o2) {
	            return extractInt(o1) - extractInt(o2);
	        }

	        int extractInt(String s) {
	            String num = s.replaceAll("\\D", "").replace("[", "").replace("]", "");
	            // return 0 if no digits found
	            return num.isEmpty() ? 0 : Integer.parseInt(num);
	        }
	    });
		index = 0;
		for( String i : noOfItemsWpr ){
			if(!Utils.isEmpty(request.getParameter( i )) &&  !request.getParameter( i ).equalsIgnoreCase("null")){
				//Modified the index as WprWCId was incorrectly assigned in WprWCId- Request ID : 117466
				wcVO.getWcEmployeeDetails().get( index ).setWprWCId( Long.valueOf(request.getParameter( i )) );
				index++;
			}else{
				index++;
			}
		
		}
		
		noOfItemsWpr = HTTPUtils.getMatchingMultiReqParamKeys( request, "empIndex" );
		Collections.sort(noOfItemsWpr, new Comparator<String>() {
	        public int compare(String o1, String o2) {
	            return extractInt(o1) - extractInt(o2);
	        }

	        int extractInt(String s) {
	            String num = s.replaceAll("\\D", "").replace("[", "").replace("]", "");
	            // return 0 if no digits found
	            return num.isEmpty() ? 0 : Integer.parseInt(num);
	        }
	    });
		index = 0;
		for( String i : noOfItemsWpr ){
			if(!Utils.isEmpty(request.getParameter( i ))){
				//Modified the index as it was incorrectly assigned(duplicate)- Request ID : 117466	
				wcVO.getWcEmployeeDetails().get( index ).setIndex(index );
				index++;
			}
		}
		Collections.sort( wcVO.getWcEmployeeDetails() );
	return wcVO;
	}
	
	/**
	 * Write Employee detail in xls format.
	 * @param response
	 * @param wcVO
	 * @throws IOException
	 * Added for Adventnet Id:96090;
	 * To support Download function of Employee Details in xls format.
	 */

	private static void writeContentToStream(HttpServletResponse response,
			WCVO wcVO) throws IOException {
		OutputStream out = null;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = (HSSFSheet) workbook.createSheet("wcdownload");
			HSSFRow headerRow = sheet.createRow(0);
			HSSFCell rowCell = headerRow.createCell(0);
			rowCell.setCellValue("EmpName");
			if (!com.mindtree.ruc.cmn.utils.Utils.isEmpty(wcVO)
					&& !com.mindtree.ruc.cmn.utils.Utils.isEmpty(wcVO
							.getWcEmployeeDetails())
					&& wcVO.getWcEmployeeDetails().size() > 0) {
				List empList = wcVO.getWcEmployeeDetails();
				int noOfItems = empList.size();
				for (int i = 0; i < noOfItems; i++) {
					com.rsaame.pas.vo.bus.WCNammedEmployeeVO namdEmp = (com.rsaame.pas.vo.bus.WCNammedEmployeeVO) empList
							.get(i);
					if (com.mindtree.ruc.cmn.utils.Utils.isEmpty(namdEmp
							.getIndex())) {
						namdEmp.setIndex(i);
					}
					headerRow = sheet.createRow(i + 1);
					rowCell = headerRow.createCell(0);
					rowCell.setCellValue(namdEmp.getEmpName());

				}
			}
			response.setHeader("Content-Disposition",
					"attachment; filename=EmployeeDetails.xls");
			response.setContentType("application/vnd.ms-excel");
			out = response.getOutputStream();
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw new SystemException("cmn.unknownError", null,
					"File cannot be downloaded");
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
