package com.rsaame.pas.wcMonoline.ui;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import com.rsaame.pas.vo.bus.WorkmenCompVO;
/**
 * Servlet implementation class WCMonolineEmployeeDetailRH
 */
public class WCMonolineEmployeeDetailRH implements IRequestHandler {
	@Override
	public Response execute( HttpServletRequest httpRequest, HttpServletResponse httpdRresponse ){
		Response response = new Response();
		String action=httpRequest.getParameter( "action" );
		/*
		 * Download the employee details
		 */
		 if(action.equalsIgnoreCase("WC_EMP_DOWNLOAD")){
			try {
				WorkmenCompVO workmenCompVO = (WorkmenCompVO)httpRequest.getSession().getAttribute("WCMONOLINE_EMPDETAIL");
				writeContentToStream(httpdRresponse, workmenCompVO);
				response.setSuccess(true);
				response.setData("File downloaded successfully");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			httpRequest.setAttribute("action", action);
			Redirection redirection = new Redirection(Utils.getSingleValueAppConfig("WC_EMP_UPLOAD"),Redirection.Type.TO_JSP);

			response.setRedirection(redirection);
		}
		return response;
	}
	
	/**
	 * Write Employee detail in xls format.
	 * @param response
	 * @param wcVO
	 * @throws IOException
	 */

	private static void writeContentToStream(HttpServletResponse response,
			WorkmenCompVO workmenCompVO) throws IOException {
		OutputStream out = null;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = (HSSFSheet) workbook.createSheet("wcdownload");
			HSSFRow headerRow = sheet.createRow(0);
			HSSFCell rowCell = headerRow.createCell(0);
			rowCell.setCellValue("EmpName");
			if (!com.mindtree.ruc.cmn.utils.Utils.isEmpty(workmenCompVO)
					&& workmenCompVO.getWcEmployeeDetails().size() > 0) {
				List empList = workmenCompVO.getWcEmployeeDetails();
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
