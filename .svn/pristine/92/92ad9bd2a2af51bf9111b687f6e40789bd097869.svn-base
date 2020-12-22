package com.rsaame.pas.ui.cmn.fileupload;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.WCVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;

public class WCFileUploadRH extends GenericFileUploadRH{

	private static final int EMP_NAME_COL = 0;
	
	
	@Override
	protected BaseVO sectionRelatedChanges( HttpServletRequest request, Response response, String fileName ){

		/*
		 * 1. Get PolicyVO from PolicyContext 2. Get SectionVO from PolicyVO 3.
		 * Get LocationVO from SectionVO 4. Get FidelityVO for the locationVO
		 * obtained in step 3 5. SetPageData i.e.
		 * AppUtils.setSectionPageDataForJSON 6. It will always be case of
		 * reload hence implement the logic of
		 * SectionRHUtils.getLocationReloadJSP blindly
		 */
		WCVO wcVO = new WCVO();

		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, AppConstants.SECTION_ID_WC);
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );
		WCVO wcVOFromPolContext = (WCVO) com.rsaame.pas.svc.utils.PolicyUtils
				.getRiskGroupDetailsForProcessing(policyVO,
						AppConstants.SECTION_ID_WC);
		if( !Utils.isEmpty( fileName ) ){

			File file = null;
			file = new File( fileName );
			// MSExcelFileHandler readingExcelFiles = new MSExcelFileHandler();

			if( file.exists() ){
				/*Modified to retain the existing employee details
				 [EMPName,WprId] for the employee uploaded through excel - Request ID :117466*/
				wcVO = readFileForWCDetails(fileName, wcVO, wcVOFromPolContext);
			}
			
			request.getSession().setAttribute( com.rsaame.pas.util.AppConstants.REQ_ATTR_CURR_RGD, wcVO );

			AppUtils.setSectionPageDataForJSON( request, sectionVO, locationVO, wcVO, policyVO );
			setRedirectionIfAny( response);

		}
		return wcVO;
	}

	private WCVO readFileForWCDetails( String fileName, WCVO wcVO, WCVO wcVOFromPolContext ){

		List<Map<Integer, Cell>> listOfRows = getFileData( fileName, null );
		int noOfRows = listOfRows.size();
		java.util.List<WCNammedEmployeeVO> wcEmployeeDetails = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>(
				WCNammedEmployeeVO.class );
		/**
		 * Looping starts from the second row because, the first row we defined
		 * the headings for columns
		 * 
		 */
		for( int i = 1; i < noOfRows; i++ ){
			Map<Integer, Cell> row = listOfRows.get( i );
			WCNammedEmployeeVO namedEmployee = new WCNammedEmployeeVO();

			Cell myCell = null;

			if( row.containsKey( EMP_NAME_COL ) ){
				myCell = row.get( EMP_NAME_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					namedEmployee.setEmpName( myCell.getStringCellValue() );
				}else{
					namedEmployee.setEmpName( String.valueOf( myCell.getNumericCellValue()) );
				}
				namedEmployee.setIndex( i-1 );
			}
			/*Modified to retain the existing employee details
			 [EMPName,WprId] for the employee uploaded through excel - Request ID :117466*/
			boolean isEmpAlreadyExist = false;
			if (!Utils.isEmpty(wcVOFromPolContext)) {
				for (WCNammedEmployeeVO namedEmp : wcVOFromPolContext
						.getWcEmployeeDetails()) {
					if (namedEmp.getEmpName()
							.equals(namedEmployee.getEmpName())) {
						isEmpAlreadyExist = true;
						wcEmployeeDetails.add(namedEmp);
						break;
					}
				}
			}
			if (!isEmpAlreadyExist) {
				wcEmployeeDetails.add(namedEmployee);
			}
		}
		wcVO.setWcEmployeeDetails(wcEmployeeDetails);
		return wcVO;
	}

	/**
	 * Get the file name based on the risk group id and CIR_ key word to
	 * identify the Cash in residence file.
	 * 
	 */
	@Override
	protected String getFileName( String fileName, HttpServletRequest request ){

		String riskGroupId = request.getParameter( "riskGroupId" );

		String extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );

		return "CIR_" + riskGroupId + "." + extension;
	}

	@Override
	protected void setRedirectionIfAny( Response responseObj ){

	/*	Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );*/
		
		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_FILE_UPLOAD_ID" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

}
