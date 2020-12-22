package com.rsaame.pas.ui.cmn.fileupload;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;

public class FidelityFileUploadRH extends GenericFileUploadRH{

	private static final int EMP_NAME_COL = 0;
	private static final int EMP_TYPE_COL = 1;
	private static final int EMP_DESIGNATION_COL = 2;
	private static final int EMP_LMT_PER_PERSON_COL = 3;

	private static final Map fdltyCodeDescMap = new Map<String, String>( null, null );
	private static final String FIDELITY_EMP_TYPE_CATEGORY = "EMPLOYEE_NAMMED_CASH_TYPE";

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
		FidelityVO fidelityVO = new FidelityVO();

		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, AppConstants.SECTION_ID_FIDELITY );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );

		if( !Utils.isEmpty( fileName ) ){

			File file = null;
			file = new File( fileName );
			// MSExcelFileHandler readingExcelFiles = new MSExcelFileHandler();

			if( file.exists() ){
				getFidelityTypeDescription( request, policyVO );
				fidelityVO = readFileForFidelityDetails( fileName, fidelityVO );
			}

			AppUtils.setSectionPageDataForJSON( request, sectionVO, locationVO, fidelityVO, policyVO );
			setRedirectionIfAny( response );

		}
		return fidelityVO;
	}

	private FidelityVO readFileForFidelityDetails( String fileName, FidelityVO fidelityVO ){

		List<Map<Integer, Cell>> listOfRows = getFileData( fileName, null );
		int noOfRows = listOfRows.size();

		/**
		 * Looping starts from the second row because, the first row we defined
		 * the headings for columns
		 * 
		 */
		for( int i = 1; i < noOfRows; i++ ){
			Map<Integer, Cell> row = listOfRows.get( i );
			FidelityNammedEmployeeDetailsVO namedEmployee = new FidelityNammedEmployeeDetailsVO();

			Cell myCell = null;

			if( row.containsKey( EMP_NAME_COL ) ){
				myCell = row.get( EMP_NAME_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					namedEmployee.setEmpName( myCell.getStringCellValue() );
				}else{
					namedEmployee.setEmpName( String.valueOf( myCell.getNumericCellValue()) );
				}

			}
			if( row.containsKey( EMP_TYPE_COL ) ){
				myCell = row.get( EMP_TYPE_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String empTypeCode = null;
					if( fdltyCodeDescMap.containsKey( myCell.getStringCellValue() ) ){
						empTypeCode = (String) fdltyCodeDescMap.get( myCell.getStringCellValue() );
						namedEmployee.setEmpType( Integer.parseInt( empTypeCode ) );
					}
				
				}

			}else{
				namedEmployee.setEmpType( null );
			}
			if( row.containsKey( EMP_DESIGNATION_COL ) ){
				myCell = row.get( EMP_DESIGNATION_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					namedEmployee.setEmpDesignation( myCell.getStringCellValue() );
				}else{
					namedEmployee.setEmpDesignation( String.valueOf( myCell.getNumericCellValue()) );
				}
				

			}

			if( row.containsKey( EMP_LMT_PER_PERSON_COL ) ){
				myCell = row.get( EMP_LMT_PER_PERSON_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					namedEmployee.setLimitPerPerson( myCell.getNumericCellValue() );
				}
			}

			fidelityVO.getFidelityEmployeeDetails().add( namedEmployee );
		}

		return fidelityVO;
	}

	private void getFidelityTypeDescription( HttpServletRequest request, PolicyVO policyVO ){
		/*
		 * LookUpListVO listVO = SvcUtils.getLookUpCodesList(
		 * FIDELITY_EMP_TYPE_CATEGORY,
		 * policyVO.getScheme().getSchemeCode().toString(),
		 * policyVO.getScheme().getTariffCode() .toString() );
		 */
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( FIDELITY_EMP_TYPE_CATEGORY, "ALL", "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			fdltyCodeDescMap.put( luVO.getDescription().trim(), luVO.getCode().toString() );
		}

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

		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "FIDELITY_SECTION" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

}
