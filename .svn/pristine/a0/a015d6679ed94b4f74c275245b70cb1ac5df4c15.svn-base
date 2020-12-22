package com.rsaame.pas.ui.cmn.fileupload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;

/**
 * @author m1019834
 *
 */

public class GPAFileUploadRH extends GenericFileUploadRH{

	private static final int CIR_EMP_NAME_COL = 0;
	private static final int CIR_EMP_TYPE_COL = 1;
	private static final int CIR_EMP_GENDER_COL = 2;
	private static final int CIR_EMP_DOB_COL = 3;
	private static final int CIR_EMP_DESIGNATION_COL = 4;
	private static final int CIR_EMP_SALARY_COL = 5;
	private static final int CIR_EMP_SUMINSURED_COL = 6;
	private static final HashMap<String, String> gpaCodeDescMap = new Map<String, String>( null, null );
	private static final HashMap<String, String> gpaCodeDescMap1 = new Map<String, String>( null, null );
	boolean isValidationPass = false;

	/**
	 * Here the money section related changes can be done,
	 * here we populate the moneyVO for cash in residence details and return it.
	 *  
	 */
	@Override
	protected BaseVO sectionRelatedChanges( HttpServletRequest request, Response response, String fileName ){

		/*
		 * 1. Get PolicyVO from PolicyContext
		 * 2. Get SectionVO from PolicyVO
		 * 3. Get LocationVO from SectionVO
		 * 4. Get MoneyVO for the locationVO obtained in step 3
		 * 5. SetPageData i.e. AppUtils.setSectionPageDataForJSON
		 * 6. It will always be case of reload hence implement the logic of SectionRHUtils.getLocationReloadJSP blindly
		 * */

		GroupPersonalAccidentVO groupPersonalAccidentVO = new GroupPersonalAccidentVO();

		PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();

		SectionVO sectionVO = PolicyUtils.getSectionVO( policyVO, AppConstants.SECTION_ID_GROUP_PERSONAL_ACCIDENT );
		LocationVO locationVO = (LocationVO) PolicyUtils.getRiskGroupForProcessing( sectionVO );

		if( !Utils.isEmpty( fileName ) ){

			File file = null;
			file = new File( fileName );

			if( file.exists() ){
				getGPATypeDescription( request, policyVO );
				groupPersonalAccidentVO = readFileForEquipmentDetails( fileName, groupPersonalAccidentVO );
			}

			AppUtils.setSectionPageDataForJSON( request, sectionVO, locationVO, groupPersonalAccidentVO, policyVO );
			setRedirectionIfAny( response );

		}
		return groupPersonalAccidentVO;
	}

	protected void getGPATypeDescription( HttpServletRequest request, PolicyVO policyVO ){

		//policyVO.getScheme().getSchemeCode().toString(), policyVO.getScheme().getTariffCode().toString() ;
		LookUpListVO listVO = SvcUtils.getLookUpCodesList( "GP_EMPTYPE", policyVO.getScheme().getTariffCode().toString(), "ALL" );
		for( LookUpVO luVO : listVO.getLookUpList() ){
			gpaCodeDescMap.put( luVO.getDescription().toUpperCase(), luVO.getCode().toString() );
		}
		//policyVO.getScheme().getSchemeCode().toString(), policyVO.getScheme().getTariffCode().toString() );
		LookUpListVO listVO1 = SvcUtils.getLookUpCodesList( "GENDER", "ALL", "ALL" );
		for( LookUpVO luVO1 : listVO1.getLookUpList() ){
			gpaCodeDescMap1.put( luVO1.getDescription().toUpperCase(), luVO1.getCode().toString() );
		}
	}

	/**
	 * Get the file name based on the risk group id
	 * and CIR_ key word to identify the Cash in residence file.
	 * 
	 */
	@Override
	protected String getFileName( String fileName, HttpServletRequest request ){

		String riskGroupId = request.getParameter( "riskGroupId" );

		String extension = fileName.substring( fileName.lastIndexOf( "." ) + 1 );

		return "CIR_" + riskGroupId + "." + extension;
	}

	/**
	 * Set the redirection for the Employee detail file upload. (CASH IN RESIDENCE)
	 * 
	 */
	@Override
	protected void setRedirectionIfAny( Response responseObj ){

		Redirection redirection = new Redirection();
		String redirectionURL = SectionRHUtils.getLocationReloadJSP( Integer.valueOf( Utils.getSingleValueAppConfig( "GROUP_PERSONAL_ACCIDENT_SECTION" ) ), true );
		redirection.setUrl( redirectionURL );
		redirection.setType( Type.TO_JSP );
		responseObj.setRedirection( redirection );
	}

	public GroupPersonalAccidentVO readFileForEquipmentDetails( String fileName, GroupPersonalAccidentVO groupPersonalAccidentVO ){

		List<Map<Integer, Cell>> listOfRows = getFileData( fileName, null );
		int noOfRows = listOfRows.size();

		if( noOfRows > 51 ){
			throw new BusinessException( "pas.cmn.uploadRowExceeded", null, "Uploaded file has more than 50 rows." );
		}

		/**
		 *  Looping starts from the second row because, the first row we defined the headings for columns 
		 * 
		 */
		List<GPANammedEmpVO> gpaNammedEmpVODtls = new ArrayList<GPANammedEmpVO>();
		for( int i = 1; i < noOfRows; i++ ){
			Map<Integer, Cell> row = listOfRows.get( i );

			GPANammedEmpVO gpaNammedEmpVO = new GPANammedEmpVO();
			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			gpaNammedEmpVO.setSumInsuredDetails( sumInsuredVO );
			Cell myCell = null;

			if( row.containsKey( CIR_EMP_TYPE_COL ) ){
				myCell = row.get( CIR_EMP_TYPE_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					String gpaEmpTypeCode = null;
					if( gpaCodeDescMap.containsKey( myCell.getStringCellValue().toUpperCase()) ){
						gpaEmpTypeCode = (String) gpaCodeDescMap.get( myCell.getStringCellValue().toUpperCase() );
						gpaNammedEmpVO.setEmployeeType( Integer.valueOf( gpaEmpTypeCode.toString() ) );
					}

				}
			}
			else{
				gpaNammedEmpVO.setEmployeeType( null );
			}
			if( row.containsKey( CIR_EMP_NAME_COL ) ){
				myCell = row.get( CIR_EMP_NAME_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					gpaNammedEmpVO.setNameOfEmployee( myCell.getStringCellValue());}
				else{
					String nameVal = String.valueOf(myCell.getNumericCellValue());
					if(nameVal.endsWith(".0")){
					gpaNammedEmpVO.setNameOfEmployee( nameVal.substring(0, nameVal.length()-2) );
					}else{
						gpaNammedEmpVO.setNameOfEmployee(nameVal);
					}
					
					
				}

			}
			else{
				gpaNammedEmpVO.setNameOfEmployee( null );
			}
			if( row.containsKey( CIR_EMP_GENDER_COL ) ){
				myCell = row.get( CIR_EMP_GENDER_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){

					String gpaEmpGenderType = null;
					if( gpaCodeDescMap1.containsKey( myCell.getStringCellValue().toUpperCase() ) ){
						gpaEmpGenderType = (String) gpaCodeDescMap1.get( myCell.getStringCellValue().toUpperCase() );
						gpaNammedEmpVO.setNamedEmpGender( gpaEmpGenderType.charAt( 0 ) );
					}

				}
			}
			else{
				gpaNammedEmpVO.setNamedEmpGender( '\0' );
			}
			if( row.containsKey( CIR_EMP_DOB_COL ) ){
				myCell = row.get( CIR_EMP_DOB_COL );
				String gpaEmpDOB = null;
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					gpaEmpDOB = (String)myCell.getStringCellValue();
					gpaNammedEmpVO.setNammedEmpDob( gpaEmpDOB );
				}
			}

			if( row.containsKey( CIR_EMP_DESIGNATION_COL ) ){
				myCell = row.get( CIR_EMP_DESIGNATION_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_STRING ){
					gpaNammedEmpVO.setNammedEmpDesignation( myCell.getStringCellValue() );}
				else{
					String descriptionVal = String.valueOf(myCell.getNumericCellValue());
					if(descriptionVal.endsWith(".0")){
					gpaNammedEmpVO.setNammedEmpDesignation(descriptionVal.substring(0, descriptionVal.length()-2));
					}else{
						gpaNammedEmpVO.setNammedEmpDesignation(descriptionVal);
					}
					
				}

			}
			
			if( row.containsKey( CIR_EMP_SALARY_COL ) ){
				myCell = row.get( CIR_EMP_SALARY_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					gpaNammedEmpVO.setNammedEmpAnnualSalary( myCell.getNumericCellValue() );
				}

			}
			
			if( row.containsKey( CIR_EMP_SUMINSURED_COL ) ){
				myCell = row.get( CIR_EMP_SUMINSURED_COL );
				if( myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ){
					sumInsuredVO.setSumInsured( myCell.getNumericCellValue() );
					gpaNammedEmpVO.setSumInsuredDetails( sumInsuredVO );
				}

			}

			gpaNammedEmpVODtls.add( gpaNammedEmpVO );
		}
		groupPersonalAccidentVO.setGpaNammedEmpVO( gpaNammedEmpVODtls );
		return groupPersonalAccidentVO;
	}

}
